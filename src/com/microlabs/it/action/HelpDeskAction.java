package com.microlabs.it.action;
 
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

import com.microlabs.admin.form.ESSApproverForm;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.main.db.MainDao;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.myrequest.form.MyRequestForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;
import com.microlabs.it.Dao.ITMail;
import com.microlabs.it.DaoImpl.IssueImpl;
import com.microlabs.it.form.HelpDeskForm;
import com.microlabs.it.form.IssuesForm;

public class HelpDeskAction extends DispatchAction
{
	EssDao ad=new EssDao();
	
	public ActionForward getsubcategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		HelpDeskForm help = (HelpDeskForm) form;
		// TODO Auto-generated method stub
		String linkName = request.getParameter("linkName");		
	
		try {
			
			ArrayList subCategoryList = new ArrayList();
			if(linkName.equalsIgnoreCase("Hardware"))
			{
				subCategoryList.add("Desktop");
				subCategoryList.add("Monitor");
				subCategoryList.add("Keyboard");
				subCategoryList.add("Mouse");
				subCategoryList.add("RAM");
				subCategoryList.add("Hardisk");
				subCategoryList.add("Laptop");
				subCategoryList.add("Scanner");
				subCategoryList.add("Data Card");
				subCategoryList.add("Wyse");
				subCategoryList.add("Server");
			}
			
			if(linkName.equalsIgnoreCase("Internet"))
			{
				subCategoryList.add("Browser");
				subCategoryList.add("Web Site Link");
			}			
			
			if(linkName.equalsIgnoreCase("Network"))
			{
				subCategoryList.add("LAN");
				subCategoryList.add("VOIP PHONE");
				subCategoryList.add("WI-FI");
			}
			
			if(linkName.equalsIgnoreCase("Operating System"))
			{
				subCategoryList.add("WINDOWS XP");
				subCategoryList.add("WINDOWS 7");
				subCategoryList.add("WINDOWS 8");
			}
			if(linkName.equalsIgnoreCase("Printers"))
			{
				subCategoryList.add("Connectivity");
				subCategoryList.add("Breakdown");
				subCategoryList.add("Tonner");
				subCategoryList.add("Cartridge");
			}
		
			if(linkName.equalsIgnoreCase("Software"))
			{
				subCategoryList.add("EMICRO");
				subCategoryList.add("MLLITAMS");
				subCategoryList.add("MLLPMS");
				subCategoryList.add("MLLQMS");
				subCategoryList.add("MLLDLS");
				subCategoryList.add("NAVADISHA");
				subCategoryList.add("LOTUS MAIL");
				subCategoryList.add("MS OFFICE");
				subCategoryList.add("Adobe");
				subCategoryList.add("Legacy Application ");
				subCategoryList.add("Backup");
				subCategoryList.add("Citrix");
			
			}
			
			if(linkName.equalsIgnoreCase("SAP"))
			{
				subCategoryList.add("SAP MM ");
				subCategoryList.add("SAP RELATED");
				subCategoryList.add("SAP SD ");
				subCategoryList.add("SAP PP & QM");
				subCategoryList.add("SAP WM ");
				subCategoryList.add("SAP GENERAL");
				subCategoryList.add("Authorization");
			
			}
			
			if(linkName.equalsIgnoreCase("IT SERVICES"))
			{
				subCategoryList.add("Active Directory User Creation");
				subCategoryList.add("Active Directory User Deletion");
				subCategoryList.add("Active Directory User Transfer");
				subCategoryList.add("Email ID Create Request");
				subCategoryList.add("Email ID Change Request");
				subCategoryList.add("Email ID Delete Request");
				subCategoryList.add("Internet Access Request");
				subCategoryList.add("External Drives Access Request");
				subCategoryList.add("FTP Access Request");
				subCategoryList.add("File Server Access Request");
				subCategoryList.add("New IT Asset Request");
			
			}
			
			if(linkName.equalsIgnoreCase("Telephone"))
			{
				subCategoryList.add("Land Line");
				subCategoryList.add("Black Berry");
				subCategoryList.add("Android");
				subCategoryList.add("Windows");
				subCategoryList.add("Iphone");
			
			
			}
			
		
			
	
			help.setSubcatList(subCategoryList);
			

			help.setUsage(help.getUsage());
			help.setEmployeeno("");
			request.setAttribute("subCategoryList", subCategoryList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//displayMainLinks(mapping, form, request, response);
		return mapping.findForward("subcategoryajax");
	
	}

	
		public ActionForward reopenmyrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		String chkdate1="";
		String trans="";
		String chkdate="";
		HelpDeskForm help = (HelpDeskForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		String forwardtype="";
		String type=help.getRequestType();
		help.setRequestType(type);
		String reqno=help.getRequestNumber();
	    int i=0;
		String engcode="";
	    Date dNow1 = new Date( );
		 SimpleDateFormat ft1 = new SimpleDateFormat ("dd/MM/yyyy ");
	    String dateNow1 = ft1.format(dNow1);
	    
		ITMail email = new ITMail();
	    Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
	    String dateNow = ft.format(dNow);
	
	    //Delete last record of IT ENGINEER DETAIlS
		
		
		String getAppr="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
		ResultSet rsAppr=ad.selectQuery(getAppr);
		
			try {
				while(rsAppr.next())
				{    
					
					ApprovalsForm apprvers=new ApprovalsForm();
					
					
					apprvers.setEmployeeCode(rsAppr.getString("Approver_Id"));
					engcode=rsAppr.getString("Approver_Id");
					
					String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+engcode+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
					if(rsAppInfo1.next())
					{
						
						String approveStatus=rsAppInfo1.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved"))
						{
							recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
							ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
							if(rsAppInfo2.next())
							{
								String Deletedate=rsAppInfo2.getString("approved_date");
								
								String deleterecord="delete from All_Request where Req_Type='IT Request' and Req_Id='"+reqno+"'  and  Last_Approver='"+engcode+"' and approved_date='"+Deletedate+"'";
								   i=ad.SqlExecuteUpdate(deleterecord);
							}
						}
					}
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch blocko
				e.printStackTrace();
			}
			
				if(i>0)
				{
				
				
				    String insertAduser="update IT_HELPDESK set ITEng_Status='ReOpen',Remarks='"+help.getRemarks()+"' where Req_No="+reqno+" ";
					 i=ad.SqlExecuteUpdate(insertAduser);
					 
					help.setMessage("Request has been Reopened");
				
				     String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Pending_approver," +
					"Requester_Id,type) values('"+reqno+"','IT Request','"+user.getEmployeeNo()+"'," +
					"'"+dateNow1+"','Pending','"+engcode+"','"+user.getEmployeeNo()+"','"+type+"')";
					 i=ad.SqlExecuteUpdate(insert);
					 
					 int j = email.sendMailToApprover(request, Integer.parseInt(reqno),type,engcode,"");

					 
					//transaction table
						
					  trans="insert into IT_TRANSACTION_DETAILS(Req_No,Emp_No,Req_type,Approver_ID,Req_Status,Transaction_Date,ITEng_Status,Remarks)" +
						" values('"+reqno+"','"+user.getEmployeeNo()+"','"+type+"','"+engcode+"','Pending','"+dateNow+"','ReOpen','"+help.getRemarks()+"')";
					 	 i=ad.SqlExecuteUpdate(trans);
				
				
				
			}
				if(type.equalsIgnoreCase("New IT Spares Request"))
				{
		
					LinkedList Adusercreation=new LinkedList();
					String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					try {
						while(rs00.next())
						{
						if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
						{
							empno=rs00.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String locID="";
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setServer(rs1.getString("Server_Address"));
					help.setReqdfolderacces(rs1.getString("Req_Folder_access"));
					help.setReason(rs1.getString("Reason"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setAssetcategory(rs1.getString("Category"));
					help.setReqbydate(rs1.getString("reqbydate"));
					help.setSuggestmodelname(rs1.getString("suggmodel"));
					
					help.setGxpyes(rs1.getString("gxp"));
					
					help.setTypeofprinter(rs1.getString("typeofprinter"));
					help.setUsers(rs1.getString("noofusers"));
					help.setPrintvolpermonth(rs1.getString("printvolpm"));
					help.setSuggestmodelno(rs1.getString("suggmodelno"));
					help.setApprxvalue(rs1.getString("Apprxvalue"));
			
					help.setLanyes(rs1.getString("lan"));
					help.setAnysplreq(rs1.getString("anysplreq"));
					help.setPurpose(rs1.getString("purpose"));
	                locID=rs1.getString("Location");
			
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			//Approver Details
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			int jk=0;
			
			//HOD
		
			int hodpresent=0;
			int hodpresentinIt=0;
			String HODPresentId="";
			String HODPresentname="";
			String itengineerid="";
			String itengineername="";
			String HOD="";
			String	pendingApprovers="";
			
			 //chk if he has Hod
			 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
			 ResultSet rs10= ad.selectQuery(chkhod);
			 try {
				while(rs10.next())
				 {
					 hodpresent=rs10.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			//get hod details
			
		
			if(hodpresent>0){
			String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
					" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
			 ResultSet rs2= ad.selectQuery(gethod);
			 
			 try {
				if(rs2.next())
				 {
				
					HODPresentId=rs2.getString("ApproverId");
					HODPresentname=rs2.getString("EMP_FULLNAME");
				 }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
			
			//chk if he is present in It approver
			 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
			 ResultSet rs11= ad.selectQuery(chkhodinit);
			 try {
				while(rs11.next())
				 {
					hodpresentinIt=rs11.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			 
			 
		
			 
			 
					
				if(hodpresent>0 && hodpresentinIt==0 )
				{
					
					
					try {
						
							checkStatus=1;
							ApprovalsForm apprvershod=new ApprovalsForm();
							apprvershod.setPriority("1");
							
							apprvershod.setEmployeeCode(HODPresentId);
							apprvershod.setEmployeeName(HODPresentname);
							
							String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
						+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
						+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
						+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									apprvershod.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvershod.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvershod.setComments("");
								}else{
									apprvershod.setComments(rsAppInfo.getString("Comments"));
								}
							}
							
							
							listApprers.add(apprvershod);
						 jk=1;
						}
					
					catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
				}
			
					
			
			
			
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					
						
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
							 chkdate1=rsAppInfo.getString("rejected_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
						{
							
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
					
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
								
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus("Closed");
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
										{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										}
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
					
					forwardtype="viewNewITSparesform";
				
					
				}
				if(type.equalsIgnoreCase("New IT Asset Request"))
				{

					LinkedList Adusercreation=new LinkedList();
					String empno=user.getEmployeeNo();
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setServer(rs1.getString("Server_Address"));
					help.setReqdfolderacces(rs1.getString("Req_Folder_access"));
					help.setReason(rs1.getString("Reason"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setAssetcategory(rs1.getString("Category"));
					help.setReqbydate(rs1.getString("reqbydate"));
					help.setSuggestmodelname(rs1.getString("suggmodel"));
					
					help.setGxpyes(rs1.getString("gxp"));
					
					help.setTypeofprinter(rs1.getString("typeofprinter"));
					help.setUsers(rs1.getString("noofusers"));
					help.setPrintvolpermonth(rs1.getString("printvolpm"));
					help.setSuggestmodelno(rs1.getString("suggmodelno"));
					help.setApprxvalue(rs1.getString("Apprxvalue"));
			
					help.setLanyes(rs1.getString("lan"));
					help.setAnysplreq(rs1.getString("anysplreq"));
					help.setPurpose(rs1.getString("purpose"));
			
			
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			//Approver Details
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			int jk=0;
			
			//HOD

			int hodpresent=0;
			int hodpresentinIt=0;
			String HODPresentId="";
			String HODPresentname="";
			String itengineerid="";
			String itengineername="";
			String HOD="";
			String	pendingApprovers="";
			
			 //chk if he has Hod
			 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
			 ResultSet rs10= ad.selectQuery(chkhod);
			 try {
				while(rs10.next())
				 {
					 hodpresent=rs10.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			//get hod details
			

			if(hodpresent>0){
			String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
					" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
			 ResultSet rs2= ad.selectQuery(gethod);
			 
			 try {
				if(rs2.next())
				 {
				
					HODPresentId=rs2.getString("ApproverId");
					HODPresentname=rs2.getString("EMP_FULLNAME");
				 }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
			
			//chk if he is present in It approver
			 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
			 ResultSet rs11= ad.selectQuery(chkhodinit);
			 try {
				while(rs11.next())
				 {
					hodpresentinIt=rs11.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			 
				if(hodpresent>0 && hodpresentinIt==0 )
				{
					
					
					try {
						
							checkStatus=1;
							ApprovalsForm apprvershod=new ApprovalsForm();
							apprvershod.setPriority("1");
							
							apprvershod.setEmployeeCode(HODPresentId);
							apprvershod.setEmployeeName(HODPresentname);
							
							String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
						+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
						+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
						+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									apprvershod.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvershod.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvershod.setComments("");
								}else{
									apprvershod.setComments(rsAppInfo.getString("Comments"));
								}
							}
							
							
							listApprers.add(apprvershod);
						 jk=1;
						}
					
					catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
				}
			
			

			
			
			
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					
						
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
							 chkdate1=rsAppInfo.getString("rejected_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
						{
							
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
					
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
								
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus("Closed");
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
										{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										}
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
					
					forwardtype="viewNewITAssetform";
				
					
				}
			
			if(type.equalsIgnoreCase("Active Directory User Creation"))
			{
				LinkedList Adusercreation=new LinkedList();
				String empno=user.getEmployeeNo();
				
				String data="select emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
					
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						
						
					
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
		ResultSet rs1= ad.selectQuery(data1);
		try {
			while(rs1.next())
			{
				help.setAssetvalue(rs1.getString("Asset_Details"));
				help.setAdloginname(rs1.getString("ADLoginName"));
				help.setServer(rs1.getString("Server_Address"));
				help.setReqdfolderacces(rs1.getString("Req_Folder_access"));
				help.setReason(rs1.getString("Reason"));
				help.setHostname(rs1.getString("HostName"));
				help.setIPNumber(rs1.getString("IPAddress"));
				help.setReqPriority(rs1.getString("Req_Priority"));
				
				help.setInitials(rs1.getString("Initials"));
				help.setFirstname(rs1.getString("Frst_Name"));
				help.setLastname(rs1.getString("Last_Name"));
				
				
			}
		} catch (SQLException e) {
			  e.printStackTrace();
			}
		
		//Approver Details
		int  checkStatus=0;
		LinkedList listApprers=new LinkedList();
		String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
		ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
		try {
			while(rsApprDetails.next())
			{
				int jk=0;
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				
				if(rsApprDetails.getInt("Priority")==2)
				{
					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					
				
					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
						if(hodpresent>0 && hodpresentinIt==0 )
						{
							
							
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("2"); 
									
									apprvershod.setEmployeeCode(HODPresentId);
									apprvershod.setEmployeeName(HODPresentname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
								+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";   
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					
				}
			
				
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				if(jk==1){
					
					apprvers.setPriority(Integer.toString(listApprers.size()+1));
				}
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				
				String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
						+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
						+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
						+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
						+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
				
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				if(rsAppInfo.next())
				{
					if(rsAppInfo.getInt("Priority")==1 )
					{
						 chkdate=rsAppInfo.getString("approved_date");
						 chkdate1=rsAppInfo.getString("rejected_date");
					}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
					{
					apprvers.setDate(rsAppInfo.getString("approved_date"));
					}else{
						apprvers.setDate(rsAppInfo.getString("rejected_date"));
					}
					String comments="";
					comments=rsAppInfo.getString("Comments");
					if(comments=="null" )
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
				
				listApprers.add(apprvers);
				
			}
				//IT ENGINEER DETAIlS
				
				
				String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
				
					while(rsApprDetails1.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
						
						apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
						String empCode1=rsApprDetails1.getString("Approver_Id");
						
						String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
						if(rsAppInfo1.next())
						{
							
							String approveStatus=rsAppInfo1.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved"))
							{
								recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
								ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
								if(rsAppInfo2.next())
								{
									if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
									{
										apprvers.setApproveStatus(rsAppInfo1.getString("Req_Status"));
									apprvers.setDate(rsAppInfo2.getString("approved_date"));
									
								
								
								String comments="";
								comments=rsAppInfo2.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo2.getString("Comments"));
								}
								}
								}
							}else{
								
								recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
								ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
								if(rsAppInfo3.next())
								{
									if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
									{
									apprvers.setDate(rsAppInfo3.getString("rejected_date"));
									}
									
								
								String comments="";
								comments=rsAppInfo3.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo3.getString("Comments"));
								}
								}
							}
							
						}
						apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
				
					listApprers.add(apprvers);
					}
				
				request.setAttribute("approverDetails", listApprers);
			
		} 
		
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
				
				forwardtype="viewAduserCreationform";
			}
			
			if(type.equalsIgnoreCase("Active Directory User Deletion"))
			{

				LinkedList AduserDeletion=new LinkedList();
				String empno=user.getEmployeeNo();
				
				String data="select emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
					
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						
						
					
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
		ResultSet rs1= ad.selectQuery(data1);
		try {
			while(rs1.next())
			{
				help.setAssetvalue(rs1.getString("Asset_Details"));
				help.setAdloginname(rs1.getString("ADLoginName"));
				help.setServer(rs1.getString("Server_Address"));
				help.setReqdfolderdeletion(rs1.getString("Req_Folder_Deletion"));
				help.setDeletefrom(rs1.getString("Delete_From"));
				help.setReason(rs1.getString("Reason"));
				help.setHostname(rs1.getString("HostName"));
				help.setIPNumber(rs1.getString("IPAddress"));
				help.setReqPriority(rs1.getString("Req_Priority"));
				
				
			}
		} catch (SQLException e) {
			  e.printStackTrace();
			}
		
		//Approver Details
		int  checkStatus=0;
		LinkedList listApprers=new LinkedList();
		String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
		ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
		try {
			while(rsApprDetails.next())
			{
				int jk=0;
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				
				if(rsApprDetails.getInt("Priority")==2)
				{
					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					
				
					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
						if(hodpresent>0 && hodpresentinIt==0 )
						{
							
							
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("2");
									
									apprvershod.setEmployeeCode(HODPresentId);
									apprvershod.setEmployeeName(HODPresentname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
								+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					
				}
			
				
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				if(jk==1){
					
					apprvers.setPriority(Integer.toString(listApprers.size()+1));
				}
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				
				String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
						+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
						+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
						+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
						+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
				
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				if(rsAppInfo.next())
				{
					if(rsAppInfo.getInt("Priority")==1 )
					{
						 chkdate=rsAppInfo.getString("approved_date");
						 chkdate1=rsAppInfo.getString("rejected_date");
					}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
					{
						
					apprvers.setDate(rsAppInfo.getString("approved_date"));
					
					}else{
						apprvers.setDate(rsAppInfo.getString("rejected_date"));
					}
					String comments="";
					comments=rsAppInfo.getString("Comments");
					if(comments=="null" )
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
				
				listApprers.add(apprvers);
				
			}
				//IT ENGINEER DETAIlS
				
				
				String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
				
					while(rsApprDetails1.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
						
						apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
						String empCode1=rsApprDetails1.getString("Approver_Id");
						
						String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
						if(rsAppInfo1.next())
						{
							
							String approveStatus=rsAppInfo1.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved"))
							{
								recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
								ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
								if(rsAppInfo2.next())
								{
									if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
									{
										apprvers.setApproveStatus("Closed");
									apprvers.setDate(rsAppInfo2.getString("approved_date"));
									
								
								
								String comments="";
								comments=rsAppInfo2.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo2.getString("Comments"));
								}
								}
								}
							}else{
								
								recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
								ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
								if(rsAppInfo3.next())
								{
									if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
									{
									apprvers.setDate(rsAppInfo3.getString("rejected_date"));
									}
								
								String comments="";
								comments=rsAppInfo3.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo3.getString("Comments"));
								}
								}
							}
							
						}
						apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
				
					listApprers.add(apprvers);
					}
				
				request.setAttribute("approverDetails", listApprers);
			
		} 
		
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
				
				forwardtype="viewAduserDeletionform";
			
				
			}
			
			
			if(type.equalsIgnoreCase("Active Directory User Transfer"))
			{

				LinkedList Adusertransfer=new LinkedList();
				String empno=user.getEmployeeNo();
				
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setEmpno(rs.getString("PERNR"));
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
		ResultSet rs1= ad.selectQuery(data1);
		try {
			while(rs1.next())
			{
				help.setAssetvalue(rs1.getString("Asset_Details"));
				help.setAdloginname(rs1.getString("ADLoginName"));
				help.setReason(rs1.getString("Reason"));
				help.setIPNumber(rs1.getString("IPAddress"));
				help.setReqPriority(rs1.getString("Req_Priority"));
				help.setFromlocationId(rs1.getString("From_Loc"));
				help.setTolocationId(rs1.getString("To_Loc"));
				help.setFromdepartment(rs1.getString("From_Dep"));
				help.setTodepartment(rs1.getString("To_Dep"));
				
			}
		} catch (SQLException e) {
			  e.printStackTrace();
			}
		
		//Approver Details
		int  checkStatus=0;
		LinkedList listApprers=new LinkedList();
		int jk=0;
		
		//HOD

		int hodpresent=0;
		int hodpresentinIt=0;
		String HODPresentId="";
		String HODPresentname="";
		String itengineerid="";
		String itengineername="";
		String HOD="";
		String	pendingApprovers="";
		
		 //chk if he has Hod
		 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
		 ResultSet rs10= ad.selectQuery(chkhod);
		 try {
			while(rs10.next())
			 {
				 hodpresent=rs10.getInt(1);
			 }
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		//get hod details
		

		if(hodpresent>0){
		String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
				" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
		 ResultSet rs2= ad.selectQuery(gethod);
		 
		 try {
			if(rs2.next())
			 {
			
				HODPresentId=rs2.getString("ApproverId");
				HODPresentname=rs2.getString("EMP_FULLNAME");
			 }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		}
		
		//chk if he is present in It approver
		 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
		 ResultSet rs11= ad.selectQuery(chkhodinit);
		 try {
			while(rs11.next())
			 {
				hodpresentinIt=rs11.getInt(1);
			 }
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		 
			if(hodpresent>0 && hodpresentinIt==0 )
			{
				
				
				try {
					
						checkStatus=1;
						ApprovalsForm apprvershod=new ApprovalsForm();
						apprvershod.setPriority("1");
						
						apprvershod.setEmployeeCode(HODPresentId);
						apprvershod.setEmployeeName(HODPresentname);
						
						String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
					+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
					+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
					+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
							{
								apprvershod.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvershod.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvershod.setComments("");
							}else{
								apprvershod.setComments(rsAppInfo.getString("Comments"));
							}
						}
						
						
						listApprers.add(apprvershod);
					 jk=1;
					}
				
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		
		

		
		
		
		String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
		ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
		try {
			while(rsApprDetails.next())
			{
				
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				
					
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				if(jk==1){
					
					apprvers.setPriority(Integer.toString(listApprers.size()+1));
				}
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				
				String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
						+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
						+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
						+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
						+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
				
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				if(rsAppInfo.next())
				{
					if(rsAppInfo.getInt("Priority")==1 )
					{
						 chkdate=rsAppInfo.getString("approved_date");
						 chkdate1=rsAppInfo.getString("rejected_date");
					}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
					{
						
					apprvers.setDate(rsAppInfo.getString("approved_date"));
					
					}else{
						apprvers.setDate(rsAppInfo.getString("rejected_date"));
					}
					String comments="";
					comments=rsAppInfo.getString("Comments");
					if(comments=="null" )
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
				
				listApprers.add(apprvers);
				
			}
				//IT ENGINEER DETAIlS
				
				
				String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
				
					while(rsApprDetails1.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
						
						apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
						String empCode1=rsApprDetails1.getString("Approver_Id");
						
						String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
						if(rsAppInfo1.next())
						{
							
							String approveStatus=rsAppInfo1.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved"))
							{
								recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
								ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
								if(rsAppInfo2.next())
								{
									if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
									{
										apprvers.setApproveStatus("Closed");
									apprvers.setDate(rsAppInfo2.getString("approved_date"));
									
								
								
								String comments="";
								comments=rsAppInfo2.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo2.getString("Comments"));
								}
								}
								}
							}else{
								
								recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
								ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
								if(rsAppInfo3.next())
								{
									if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
									{
									apprvers.setDate(rsAppInfo3.getString("rejected_date"));
									}
								
								String comments="";
								comments=rsAppInfo3.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo3.getString("Comments"));
								}
								}
							}
							
						}
						apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
				
					listApprers.add(apprvers);
					}
				
				request.setAttribute("approverDetails", listApprers);
			
		} 
		
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
				
				forwardtype="viewAduserTransferform";
			
				
			
				
			}
			
			if(type.equalsIgnoreCase("Email ID Create Request"))
			{
				LinkedList LotusIDCreation=new LinkedList();
				String empno=user.getEmployeeNo();
				
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
						
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setEmpno(rs.getString("PERNR"));
						
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
				ResultSet rs1= ad.selectQuery(data1);
				try {
					while(rs1.next())
					{
						help.setAdloginname(rs1.getString("ADLoginName"));
						help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
						String x=rs1.getString("comm_outside");
						if(x.equalsIgnoreCase("Yes"))
						{
							help.setOutsidecomyes("Yes");
						}
						if(x.equalsIgnoreCase("No"))
						{
							help.setOutsidecomyes("No");
						}
						help.setIsHeHod(rs1.getString("Is_He_Hod"));
						help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
						help.setEnablewebAccess(rs1.getString("Enable_Web_access"));
						help.setReason(rs1.getString("Reason"));
						help.setReqPriority(rs1.getString("Req_Priority"));
						help.setHostname(rs1.getString("HostName"));
						help.setIPNumber(rs1.getString("IPAddress"));
						help.setFirstname(rs1.getString("Frst_Name"));
						help.setLastname(rs1.getString("Last_Name"));
						help.setMiddname(rs1.getString("Middle_Name"));
						//newly_Joinied,joining_date,Total_Mailids
						String a=rs1.getString("newly_Joinied");
						if(a.equalsIgnoreCase("Yes"))
						{
							help.setNewlyJoinedYes("Yes");
						}
						if(a.equalsIgnoreCase("No"))
						{
							help.setNewlyJoinedYes("No");
						}
						help.setJoiningDate(rs1.getString("joining_date"));
						help.setTotalIDs(rs1.getString("Total_Mailids"));
					}
				} catch (SQLException e) {
					  e.printStackTrace();
					}
				
				//Approver Details
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				int jk=0;
				
				//HOD

				int hodpresent=0;
				int hodpresentinIt=0;
				String HODPresentId="";
				String HODPresentname="";
				String itengineerid="";
				String itengineername="";
				String HOD="";
				String	pendingApprovers="";
				
				 //chk if he has Hod
				 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
				 ResultSet rs10= ad.selectQuery(chkhod);
				 try {
					while(rs10.next())
					 {
						 hodpresent=rs10.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				//get hod details
				

				if(hodpresent>0){
				String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
						" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
				 ResultSet rs2= ad.selectQuery(gethod);
				 
				 try {
					if(rs2.next())
					 {
					
						HODPresentId=rs2.getString("ApproverId");
						HODPresentname=rs2.getString("EMP_FULLNAME");
					 }
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				}
				
				//chk if he is present in It approver
				 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
				 ResultSet rs11= ad.selectQuery(chkhodinit);
				 try {
					while(rs11.next())
					 {
						hodpresentinIt=rs11.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				 
					if(hodpresent>0 && hodpresentinIt==0 )
					{
						
						
						try {
							
								checkStatus=1;
								ApprovalsForm apprvershod=new ApprovalsForm();
								apprvershod.setPriority("1");
								
								apprvershod.setEmployeeCode(HODPresentId);
								apprvershod.setEmployeeName(HODPresentname);
								
								String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
							+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
							+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
							+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								if(rsAppInfo.next())
								{
									apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
									String approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
									{
										apprvershod.setDate(rsAppInfo.getString("approved_date"));
									}else{
										apprvershod.setDate(rsAppInfo.getString("rejected_date"));
									}
									String comments="";
									comments=rsAppInfo.getString("Comments");
									if(comments=="null" )
									{
										apprvershod.setComments("");
									}else{
										apprvershod.setComments(rsAppInfo.getString("Comments"));
									}
								}
								
								
								listApprers.add(apprvershod);
							 jk=1;
							}
						
						catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
					}
				
				

				
				
				
				String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						
							
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						if(jk==1){
							
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
						}
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						
						String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							if(rsAppInfo.getInt("Priority")==1 )
							{
								 chkdate=rsAppInfo.getString("approved_date");
								 chkdate1=rsAppInfo.getString("rejected_date");
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
							{
								
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						
						listApprers.add(apprvers);
						
					}
						//IT ENGINEER DETAIlS
						
						
						String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
						ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
						
							while(rsApprDetails1.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
								
								apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
								String empCode1=rsApprDetails1.getString("Approver_Id");
								
								String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
										+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
										+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
										+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
										+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
								if(rsAppInfo1.next())
								{
									
									String approveStatus=rsAppInfo1.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved"))
									{
										recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
										ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
										if(rsAppInfo2.next())
										{
											if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
											{
												apprvers.setApproveStatus("Closed");
											apprvers.setDate(rsAppInfo2.getString("approved_date"));
											
										
										
										String comments="";
										comments=rsAppInfo2.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo2.getString("Comments"));
										}
										}
										}
									}else{
										
										recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
										ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
										if(rsAppInfo3.next())
										{
											if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
											{
											apprvers.setDate(rsAppInfo3.getString("rejected_date"));
											}
										
										String comments="";
										comments=rsAppInfo3.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo3.getString("Comments"));
										}
										}
									}
									
								}
								apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
						
							listApprers.add(apprvers);
							}
						
						request.setAttribute("approverDetails", listApprers);
					
				} 
				
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				request.setAttribute("LotusIDCreation", LotusIDCreation);
				forwardtype="viewLotusIDCreationform";
			}
			
			if(type.equalsIgnoreCase("Email ID Change Request"))
			{
				LinkedList LotusIDChange=new LinkedList();
				String empno=user.getEmployeeNo();
				
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setEmpno(rs.getString("PERNR"));
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
				ResultSet rs1= ad.selectQuery(data1);
				try {
					while(rs1.next())
					{
						help.setAdloginname(rs1.getString("ADLoginName"));
						help.setFromemailId(rs1.getString("From_EmailID"));
						String x=rs1.getString("comm_outside");
						if(x.equalsIgnoreCase("Yes"))
						{
							help.setOutsidecomyes("Yes");
						}
						if(x.equalsIgnoreCase("No"))
						{
							help.setOutsidecomyes("No");
						}
						help.setDeleteoldmails(rs1.getString("delete_old_email"));
						help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
						help.setIsHeHod(rs1.getString("Is_He_Hod"));
						help.setTransferoldmail(rs1.getString("Transfer_Old_Emails"));
						help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
						help.setEnablewebAccess(rs1.getString("Enable_Web_access"));
						help.setReason(rs1.getString("Reason"));
						help.setReqPriority(rs1.getString("Req_Priority"));
						help.setHostname(rs1.getString("HostName"));
						help.setIPNumber(rs1.getString("IPAddress"));
						String a=rs1.getString("newly_Joinied");
						if(a.equalsIgnoreCase("Yes"))
						{
							help.setNewlyJoinedYes("Yes");
						}
						if(a.equalsIgnoreCase("No"))
						{
							help.setNewlyJoinedYes("No");
						}
						help.setJoiningDate(rs1.getString("joining_date"));
						help.setTotalIDs(rs1.getString("Total_Mailids"));
						
						help.setFirstname(rs1.getString("Frst_Name"));
						help.setLastname(rs1.getString("Last_Name"));
						help.setMiddname(rs1.getString("Middle_Name")); 
					}
					
					
				} catch (SQLException e) {
					  e.printStackTrace();
					}
				
				//Approver Details
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				int jk=0;
				
				//HOD

				int hodpresent=0;
				int hodpresentinIt=0;
				String HODPresentId="";
				String HODPresentname="";
				String itengineerid="";
				String itengineername="";
				String HOD="";
				String	pendingApprovers="";
				
				 //chk if he has Hod
				 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
				 ResultSet rs10= ad.selectQuery(chkhod);
				 try {
					while(rs10.next())
					 {
						 hodpresent=rs10.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				//get hod details
				

				if(hodpresent>0){
				String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
						" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
				 ResultSet rs2= ad.selectQuery(gethod);
				 
				 try {
					if(rs2.next())
					 {
					
						HODPresentId=rs2.getString("ApproverId");
						HODPresentname=rs2.getString("EMP_FULLNAME");
					 }
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				}
				
				//chk if he is present in It approver
				 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
				 ResultSet rs11= ad.selectQuery(chkhodinit);
				 try {
					while(rs11.next())
					 {
						hodpresentinIt=rs11.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				 
					if(hodpresent>0 && hodpresentinIt==0 )
					{
						
						
						try {
							
								checkStatus=1;
								ApprovalsForm apprvershod=new ApprovalsForm();
								apprvershod.setPriority("1");
								
								apprvershod.setEmployeeCode(HODPresentId);
								apprvershod.setEmployeeName(HODPresentname);
								
								String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
							+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
							+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
							+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								if(rsAppInfo.next())
								{
									apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
									String approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
									{
										apprvershod.setDate(rsAppInfo.getString("approved_date"));
									}else{
										apprvershod.setDate(rsAppInfo.getString("rejected_date"));
									}
									String comments="";
									comments=rsAppInfo.getString("Comments");
									if(comments=="null" )
									{
										apprvershod.setComments("");
									}else{
										apprvershod.setComments(rsAppInfo.getString("Comments"));
									}
								}
								
								
								listApprers.add(apprvershod);
							 jk=1;
							}
						
						catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
					}
				
				

				
				
				
				String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						
							
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						if(jk==1){
							
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
						}
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						
						String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							if(rsAppInfo.getInt("Priority")==1 )
							{
								 chkdate=rsAppInfo.getString("approved_date");
								 chkdate1=rsAppInfo.getString("rejected_date");
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
							{
								
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						
						listApprers.add(apprvers);
						
					}
						//IT ENGINEER DETAIlS
						
						
						String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
						ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
						
							while(rsApprDetails1.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
								
								apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
								String empCode1=rsApprDetails1.getString("Approver_Id");
								
								String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
										+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
										+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
										+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
										+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
								if(rsAppInfo1.next())
								{
									
									String approveStatus=rsAppInfo1.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved"))
									{
										recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
										ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
										if(rsAppInfo2.next())
										{
											if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
											{
												apprvers.setApproveStatus("Closed");
											apprvers.setDate(rsAppInfo2.getString("approved_date"));
											
										
										
										String comments="";
										comments=rsAppInfo2.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo2.getString("Comments"));
										}
										}
										}
									}else{
										
										recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
										ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
										if(rsAppInfo3.next())
										{
											if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
											{
											apprvers.setDate(rsAppInfo3.getString("rejected_date"));
											}
										
										String comments="";
										comments=rsAppInfo3.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo3.getString("Comments"));
										}
										}
									}
									
								}
								apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
						
							listApprers.add(apprvers);
							}
						
						request.setAttribute("approverDetails", listApprers);
					
				} 
				
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				request.setAttribute("LotusIDChange", LotusIDChange);
				forwardtype="viewLotusIDChangeform";
			}
			
			if(type.equalsIgnoreCase("Email ID Delete Request"))
			{

				LinkedList LotusIDDeletion=new LinkedList();
				String empno=user.getEmployeeNo();
				
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
						
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setEmpno(rs.getString("PERNR"));
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
				ResultSet rs1= ad.selectQuery(data1);
				try {
					while(rs1.next())
					{
						help.setAdloginname(rs1.getString("ADLoginName"));
						help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
				        help.setIsHeHod(rs1.getString("Is_He_Hod"));
						help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
						help.setEmailidDelete(rs1.getString("EmailID_Deleted"));
						help.setDeletefrom(rs1.getString("Delete_From"));
					    help.setReason(rs1.getString("Reason"));
						help.setReqPriority(rs1.getString("Req_Priority"));
						help.setHostname(rs1.getString("HostName"));
						help.setIPNumber(rs1.getString("IPAddress"));
						help.setMailIDPath(rs1.getString("path"));
						help.setDeletedDBYes(rs1.getString("deletedDB"));
						help.setForward_MailID(rs1.getString("forward_MailID"));
					}
				} catch (SQLException e) {
					  e.printStackTrace();
					}
				
				//Approver Details
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				int jk=0;
				
				//HOD

				int hodpresent=0;
				int hodpresentinIt=0;
				String HODPresentId="";
				String HODPresentname="";
				String itengineerid="";
				String itengineername="";
				String HOD="";
				String	pendingApprovers="";
				
				 //chk if he has Hod
				 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
				 ResultSet rs10= ad.selectQuery(chkhod);
				 try {
					while(rs10.next())
					 {
						 hodpresent=rs10.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				//get hod details
				

				if(hodpresent>0){
				String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
						" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
				 ResultSet rs2= ad.selectQuery(gethod);
				 
				 try {
					if(rs2.next())
					 {
					
						HODPresentId=rs2.getString("ApproverId");
						HODPresentname=rs2.getString("EMP_FULLNAME");
					 }
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				}
				
				//chk if he is present in It approver
				 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
				 ResultSet rs11= ad.selectQuery(chkhodinit);
				 try {
					while(rs11.next())
					 {
						hodpresentinIt=rs11.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				 
					if(hodpresent>0 && hodpresentinIt==0 )
					{
						
						
						try {
							
								checkStatus=1;
								ApprovalsForm apprvershod=new ApprovalsForm();
								apprvershod.setPriority("1");
								
								apprvershod.setEmployeeCode(HODPresentId);
								apprvershod.setEmployeeName(HODPresentname);
								
								String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
							+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
							+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
							+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								if(rsAppInfo.next())
								{
									apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
									String approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
									{
										apprvershod.setDate(rsAppInfo.getString("approved_date"));
									}else{
										apprvershod.setDate(rsAppInfo.getString("rejected_date"));
									}
									String comments="";
									comments=rsAppInfo.getString("Comments");
									if(comments=="null" )
									{
										apprvershod.setComments("");
									}else{
										apprvershod.setComments(rsAppInfo.getString("Comments"));
									}
								}
								
								
								listApprers.add(apprvershod);
							 jk=1;
							}
						
						catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
					}
				
				

				
				
				
				String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						
							
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						if(jk==1){
							
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
						}
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						
						String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							if(rsAppInfo.getInt("Priority")==1 )
							{
								 chkdate=rsAppInfo.getString("approved_date");
								 chkdate1=rsAppInfo.getString("rejected_date");
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
							{
								
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						
						listApprers.add(apprvers);
						
					}
						//IT ENGINEER DETAIlS
						
						
						String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
						ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
						
							while(rsApprDetails1.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
								
								apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
								String empCode1=rsApprDetails1.getString("Approver_Id");
								
								String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
										+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
										+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
										+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
										+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
								if(rsAppInfo1.next())
								{
									
									String approveStatus=rsAppInfo1.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved"))
									{
										recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
										ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
										if(rsAppInfo2.next())
										{
											if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
											{
												apprvers.setApproveStatus("Closed");
											apprvers.setDate(rsAppInfo2.getString("approved_date"));
											
										
										
										String comments="";
										comments=rsAppInfo2.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo2.getString("Comments"));
										}
										}
										}
									}else{
										
										recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
										ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
										if(rsAppInfo3.next())
										{
											if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
											{
											apprvers.setDate(rsAppInfo3.getString("rejected_date"));
											}
										
										String comments="";
										comments=rsAppInfo3.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo3.getString("Comments"));
										}
										}
									}
									
								}
								apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
						
							listApprers.add(apprvers);
							}
						
						request.setAttribute("approverDetails", listApprers);
					
				} 
				
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				request.setAttribute("LotusIDDeletion", LotusIDDeletion);
				forwardtype="viewLotusIDDeletionform";
			
				
			}
			
			
			
			if(type.equalsIgnoreCase("Internet Access Request"))
			{
				LinkedList internet=new LinkedList();
				String empno=user.getEmployeeNo();
				
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
					
						help.setEmpno(rs.getString("PERNR"));
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
				ResultSet rs1= ad.selectQuery(data1);
				try {
					while(rs1.next())
					{
						help.setAssetvalue(rs1.getString("Asset_Details"));
						help.setAdloginname(rs1.getString("ADLoginName"));
						help.setReason(rs1.getString("Reason"));
						help.setReqPriority(rs1.getString("Req_Priority"));
						help.setHostname(rs1.getString("HostName"));
						help.setIPNumber(rs1.getString("IPAddress"));
					}
				} catch (SQLException e) {
					  e.printStackTrace();
					}
						
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						int jk=0;
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						
						if(rsApprDetails.getInt("Priority")==2)
						{
							int hodpresent=0;
							int hodpresentinIt=0;
							String HODPresentId="";
							String HODPresentname="";
							String itengineerid="";
							String itengineername="";
							String HOD="";
							String	pendingApprovers="";
							
							 //chk if he has Hod
							 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
							 ResultSet rs10= ad.selectQuery(chkhod);
							 try {
								while(rs10.next())
								 {
									 hodpresent=rs10.getInt(1);
								 }
							} catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
							//get hod details
							
						
							if(hodpresent>0){
							String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
									" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
							 ResultSet rs2= ad.selectQuery(gethod);
							 
							 try {
								if(rs2.next())
								 {
								
									HODPresentId=rs2.getString("ApproverId");
									HODPresentname=rs2.getString("EMP_FULLNAME");
								 }
							} catch (SQLException e) {
								
								e.printStackTrace();
							}
							}
							
							//chk if he is present in It approver
							 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
							 ResultSet rs11= ad.selectQuery(chkhodinit);
							 try {
								while(rs11.next())
								 {
									hodpresentinIt=rs11.getInt(1);
								 }
							} catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							 
								if(hodpresent>0 && hodpresentinIt==0 )
								{
									
									
									try {
										
											checkStatus=1;
											ApprovalsForm apprvershod=new ApprovalsForm();
											apprvershod.setPriority("2");
											
											apprvershod.setEmployeeCode(HODPresentId);
											apprvershod.setEmployeeName(HODPresentname);
											
											String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
													+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
													+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+HODPresentId+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
													+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
													+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
											
											ResultSet rsAppInfo=ad.selectQuery(recordStatus);
											if(rsAppInfo.next())
											{
												apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
												String approveStatus=rsAppInfo.getString("Req_Status");
												if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
												{
													apprvershod.setDate(rsAppInfo.getString("approved_date"));
												}else{
													apprvershod.setDate(rsAppInfo.getString("rejected_date"));
												}
												String comments="";
												comments=rsAppInfo.getString("Comments");
												if(comments=="null" )
												{
													apprvershod.setComments("");
												}else{
													apprvershod.setComments(rsAppInfo.getString("Comments"));
												}
											}
											
											
											listApprers.add(apprvershod);
										 jk=1;
										}
									
									catch (SQLException e1) {
										
										e1.printStackTrace();
									}
									
								}
							
							
						}
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						if(jk==1){
							
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
						}
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						
						String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and  "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							if(rsAppInfo.getInt("Priority")==1 )
							{
								 chkdate=rsAppInfo.getString("approved_date");
								 chkdate1=rsAppInfo.getString("rejected_date");
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						
						listApprers.add(apprvers);
					}
						//IT ENGINEER DETAIlS
						
						
						String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
						ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
						
							while(rsApprDetails1.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
								apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
								String empCode1=rsApprDetails1.getString("Approver_Id");
								
								String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
										+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
										+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and  "
										+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
										+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
								if(rsAppInfo1.next())
								{
								
									String approveStatus=rsAppInfo1.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved"))
									{
										recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
										ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
										if(rsAppInfo2.next())
										{
											if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
											{
												apprvers.setApproveStatus(rsAppInfo1.getString("Req_Status"));
											apprvers.setDate(rsAppInfo2.getString("approved_date"));
											
										
										
										String comments="";
										comments=rsAppInfo2.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo2.getString("Comments"));
										}
										}
										}
									}else{
										
										recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
										ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
										if(rsAppInfo3.next())
										{
											if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
											{
											apprvers.setDate(rsAppInfo3.getString("rejected_date"));
											}
											
										
										String comments="";
										comments=rsAppInfo3.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo3.getString("Comments"));
										}
										}
									}
									
								}
								apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
						
							listApprers.add(apprvers);
							}
						
						request.setAttribute("approverDetails", listApprers);
					
				} 
				
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				request.setAttribute("Internet", internet);
				forwardtype="viewInternetAccessform";
			}
			
			if(type.equalsIgnoreCase("External Drives Access Request"))
			{
				LinkedList externaldrives=new LinkedList();
				String empno=user.getEmployeeNo();
				
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						
						help.setEmpno(rs.getString("PERNR"));
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
				ResultSet rs1= ad.selectQuery(data1);
				try {
					while(rs1.next())
					{
						help.setAdloginname(rs1.getString("ADLoginName"));
						help.setAssetvalue(rs1.getString("Asset_Details"));
						help.setAccesstype(rs1.getString("Access_type"));
						help.setReason(rs1.getString("Reason"));
						help.setReqPriority(rs1.getString("Req_Priority"));
						help.setHostname(rs1.getString("HostName"));
						help.setIPNumber(rs1.getString("IPAddress"));
						String device=rs1.getString("Device_type");
						if(device.equalsIgnoreCase("3"))
						{
							help.setCdrom("CDROM");
							help.setUsb("USB");
						}
						if(device.equalsIgnoreCase("1"))
						{
							help.setCdrom("CDROM");
							
						}
						if(device.equalsIgnoreCase("2"))
						{
							
							help.setUsb("USB");
						}
						
					}
				} catch (SQLException e) {
					  e.printStackTrace();
					}
				
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						int jk=0;
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						if(rsApprDetails.getInt("Priority")==2)
						{
							int hodpresent=0;
							int hodpresentinIt=0;
							String HODPresentId="";
							String HODPresentname="";
							String itengineerid="";
							String itengineername="";
							String HOD="";
							String	pendingApprovers="";
							
							 //chk if he has Hod
							 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
							 ResultSet rs10= ad.selectQuery(chkhod);
							 try {
								while(rs10.next())
								 {
									 hodpresent=rs10.getInt(1);
								 }
							} catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
							//get hod details
							
						
							if(hodpresent>0){
							String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
									" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
							 ResultSet rs2= ad.selectQuery(gethod);
							 
							 try {
								if(rs2.next())
								 {
								
									HODPresentId=rs2.getString("ApproverId");
									HODPresentname=rs2.getString("EMP_FULLNAME");
								 }
							} catch (SQLException e) {
								
								e.printStackTrace();
							}
							}
							
							//chk if he is present in It approver
							 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
							 ResultSet rs11= ad.selectQuery(chkhodinit);
							 try {
								while(rs11.next())
								 {
									hodpresentinIt=rs11.getInt(1);
								 }
							} catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							 
								if(hodpresent>0 && hodpresentinIt==0 )
								{
									
									
									try {
										
											checkStatus=1;
											ApprovalsForm apprvershod=new ApprovalsForm();
											apprvershod.setPriority("2");
											
											apprvershod.setEmployeeCode(HODPresentId);
											apprvershod.setEmployeeName(HODPresentname);
											
											String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
													+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
													+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+HODPresentId+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
													+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
													+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
											
											ResultSet rsAppInfo=ad.selectQuery(recordStatus);
											if(rsAppInfo.next())
											{
												apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
												String approveStatus=rsAppInfo.getString("Req_Status");
												if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
												{
													apprvershod.setDate(rsAppInfo.getString("approved_date"));
												}else{
													apprvershod.setDate(rsAppInfo.getString("rejected_date"));
												}
												String comments="";
												comments=rsAppInfo.getString("Comments");
												if(comments=="null" )
												{
													apprvershod.setComments("");
												}else{
													apprvershod.setComments(rsAppInfo.getString("Comments"));
												}
											}
											
											
											listApprers.add(apprvershod);
										 jk=1;
										}
									
									catch (SQLException e1) {
										
										e1.printStackTrace();
									}
									
								}
							
							
						}
					
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						if(jk==1){
							
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
						}
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						
						String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							if(rsAppInfo.getInt("Priority")==1 )
							{
								 chkdate=rsAppInfo.getString("approved_date");
								 chkdate1=rsAppInfo.getString("rejected_date");
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						
						listApprers.add(apprvers);
					}
						//IT ENGINEER DETAIlS
						
						
						String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
						ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
						
							while(rsApprDetails1.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
								apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
								String empCode1=rsApprDetails1.getString("Approver_Id");
								
								String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
										+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
										+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
										+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
										+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
								if(rsAppInfo1.next())
								{
									
									String approveStatus=rsAppInfo1.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved"))
									{
										recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
										ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
										if(rsAppInfo2.next())
										{
											if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
											{
												apprvers.setApproveStatus(rsAppInfo1.getString("Req_Status"));
											apprvers.setDate(rsAppInfo2.getString("approved_date"));
											
										
										
										String comments="";
										comments=rsAppInfo2.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo2.getString("Comments"));
										}
										}
										}
									}else{
										
										recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
										ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
										if(rsAppInfo3.next())
										{
											if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
											{
											apprvers.setDate(rsAppInfo3.getString("rejected_date"));
											}
											
										
										String comments="";
										comments=rsAppInfo3.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo3.getString("Comments"));
										}
										}
									}
									
								}
								apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
						
							listApprers.add(apprvers);
							}
						
						request.setAttribute("approverDetails", listApprers);
					
				} 
				
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				request.setAttribute("externaldrives", externaldrives);
				forwardtype="viewexternaldrivesform";
			}
			
			if(type.equalsIgnoreCase("FTP Access Request"))
			{
				LinkedList ftp=new LinkedList();
				String empno=user.getEmployeeNo();
				
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
					
						help.setEmpno(rs.getString("PERNR"));
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
				ResultSet rs1= ad.selectQuery(data1); 
				try {
					while(rs1.next())
					{
						help.setAdloginname(rs1.getString("ADLoginName"));
						help.setAssetvalue(rs1.getString("Asset_Details"));
						help.setRequiredfoldername(rs1.getString("Req_Folder_access"));
						help.setServer(rs1.getString("Server_Address"));
						help.setAccesstype(rs1.getString("Access_type"));
						help.setFrmdurationofaccess(rs1.getString("FrmRequiredDurationofAccess"));
						help.setTodurationofaccess(rs1.getString("ToRequiredDurationofAccess"));
						help.setReason(rs1.getString("Reason"));
						help.setReqPriority(rs1.getString("Req_Priority"));
						help.setHostname(rs1.getString("HostName"));
						help.setIPNumber(rs1.getString("IPAddress"));
			
						help.setDurationofaccess(rs1.getString("RequiredDurationofAccess"));
						String fileusername=rs1.getString("UserName");
						 if(fileusername!=null)
							 help.setUsername(fileusername);
						 else
							 help.setUsername("");
						 
						   String filePwd=rs1.getString("Password");
						   if(filePwd!=null)
							   help.setPassword(filePwd);
						   else
							   help.setPassword("");
					}
				} catch (SQLException e) {
					  e.printStackTrace();
					}
				
				//Approver Details
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				int jk=0;
				
				//HOD

				int hodpresent=0;
				int hodpresentinIt=0;
				String HODPresentId="";
				String HODPresentname="";
				String itengineerid="";
				String itengineername="";
				String HOD="";
				String	pendingApprovers="";
				
				 //chk if he has Hod
				 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
				 ResultSet rs10= ad.selectQuery(chkhod);
				 try {
					while(rs10.next())
					 {
						 hodpresent=rs10.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				//get hod details
				

				if(hodpresent>0){
				String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
						" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
				 ResultSet rs2= ad.selectQuery(gethod);
				 
				 try {
					if(rs2.next())
					 {
					
						HODPresentId=rs2.getString("ApproverId");
						HODPresentname=rs2.getString("EMP_FULLNAME");
					 }
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				}
				
				//chk if he is present in It approver
				 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
				 ResultSet rs11= ad.selectQuery(chkhodinit);
				 try {
					while(rs11.next())
					 {
						hodpresentinIt=rs11.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				 
					if(hodpresent>0 && hodpresentinIt==0 )
					{
						
						
						try {
							
								checkStatus=1;
								ApprovalsForm apprvershod=new ApprovalsForm();
								apprvershod.setPriority("1");
								
								apprvershod.setEmployeeCode(HODPresentId);
								apprvershod.setEmployeeName(HODPresentname);
								
								String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
							+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
							+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
							+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								if(rsAppInfo.next())
								{
									apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
									String approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
									{
										apprvershod.setDate(rsAppInfo.getString("approved_date"));
									}else{
										apprvershod.setDate(rsAppInfo.getString("rejected_date"));
									}
									String comments="";
									comments=rsAppInfo.getString("Comments");
									if(comments=="null" )
									{
										apprvershod.setComments("");
									}else{
										apprvershod.setComments(rsAppInfo.getString("Comments"));
									}
								}
								
								
								listApprers.add(apprvershod);
							 jk=1;
							}
						
						catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
					}
				
				

				
				
				
				String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						
							
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						if(jk==1){
							
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
						}
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						
						String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							if(rsAppInfo.getInt("Priority")==1 )
							{
								 chkdate=rsAppInfo.getString("approved_date");
								 chkdate1=rsAppInfo.getString("rejected_date");
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
							{
								
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						
						listApprers.add(apprvers);
						
					}
						//IT ENGINEER DETAIlS
						
						
						String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
						ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
						
							while(rsApprDetails1.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
								
								apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
								String empCode1=rsApprDetails1.getString("Approver_Id");
								
								String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
										+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
										+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
										+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
										+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
								if(rsAppInfo1.next())
								{
									
									String approveStatus=rsAppInfo1.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved"))
									{
										recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
										ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
										if(rsAppInfo2.next())
										{
											if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
											{
												apprvers.setApproveStatus("Closed");
											apprvers.setDate(rsAppInfo2.getString("approved_date"));
											
										
										
										String comments="";
										comments=rsAppInfo2.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo2.getString("Comments"));
										}
										}
										}
									}else{
										
										recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
										ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
										if(rsAppInfo3.next())
										{
											if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
											{  
											apprvers.setDate(rsAppInfo3.getString("rejected_date"));
											}
										
										String comments="";
										comments=rsAppInfo3.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo3.getString("Comments"));
										}
										}
									}
									
								}
								apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
						
							listApprers.add(apprvers);
							}
						
						request.setAttribute("approverDetails", listApprers);
					
				} 
				
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				request.setAttribute("ftp", ftp);
				forwardtype="viewFtpAccessform";
			}
			
			if(type.equalsIgnoreCase("File Server Access Request"))
			{
				LinkedList folder=new LinkedList();
				String empno=user.getEmployeeNo();
				
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
						help.setRequestername(rs.getString("EMP_FULLNAME"));
					 	help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
					    help.setEmpno(rs.getString("PERNR"));
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
				ResultSet rs1= ad.selectQuery(data1);
				try {
					while(rs1.next())
					{
						help.setAdloginname(rs1.getString("ADLoginName"));
						help.setAssetvalue(rs1.getString("Asset_Details"));
						help.setRequiredfoldername(rs1.getString("Req_Folder_access"));
						help.setServer(rs1.getString("Server_Address"));
						help.setAccesstype(rs1.getString("Access_type"));
						help.setReason(rs1.getString("Reason"));
						help.setReqPriority(rs1.getString("Req_Priority"));
						help.setHostname(rs1.getString("HostName"));
						help.setIPNumber(rs1.getString("IPAddress"));
					}
				} catch (SQLException e) {
					  e.printStackTrace();
					}
				
				
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						int jk=0;
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						if(rsApprDetails.getInt("Priority")==2)
						{
							int hodpresent=0;
							int hodpresentinIt=0;
							String HODPresentId="";
							String HODPresentname="";
							String itengineerid="";
							String itengineername="";
							String HOD="";
							String	pendingApprovers="";
							
							 //chk if he has Hod
							 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
							 ResultSet rs10= ad.selectQuery(chkhod);
							 try {
								while(rs10.next())
								 {
									 hodpresent=rs10.getInt(1);
								 }
							} catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
							//get hod details
							
						
							if(hodpresent>0){
							String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
									" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
							 ResultSet rs2= ad.selectQuery(gethod);
							 
							 try {
								if(rs2.next())
								 {
								
									HODPresentId=rs2.getString("ApproverId");
									HODPresentname=rs2.getString("EMP_FULLNAME");
								 }
							} catch (SQLException e) {
								
								e.printStackTrace();
							}
							}
							
							//chk if he is present in It approver
							 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
							 ResultSet rs11= ad.selectQuery(chkhodinit);
							 try {
								while(rs11.next())
								 {
									hodpresentinIt=rs11.getInt(1);
								 }
							} catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							 
								if(hodpresent>0 && hodpresentinIt==0 )
								{
									
									
									try {
										
											checkStatus=1;
											ApprovalsForm apprvershod=new ApprovalsForm();
											apprvershod.setPriority("2");
											
											apprvershod.setEmployeeCode(HODPresentId);
											apprvershod.setEmployeeName(HODPresentname);
											
											String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
													+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
													+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+HODPresentId+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
													+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
													+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
											
											ResultSet rsAppInfo=ad.selectQuery(recordStatus);
											if(rsAppInfo.next())
											{
												apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
												String approveStatus=rsAppInfo.getString("Req_Status");
												if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
												{
													apprvershod.setDate(rsAppInfo.getString("approved_date"));
												}else{
													apprvershod.setDate(rsAppInfo.getString("rejected_date"));
												}
												String comments="";
												comments=rsAppInfo.getString("Comments");
												if(comments=="null" )
												{
													apprvershod.setComments("");
												}else{
													apprvershod.setComments(rsAppInfo.getString("Comments"));
												}
											}
											
											
											listApprers.add(apprvershod);
										 jk=1;
										}
									
									catch (SQLException e1) {
										
										e1.printStackTrace();
									}
									
								}
							
							
						}
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						if(jk==1){
							
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
						}
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						
						String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							if(rsAppInfo.getInt("Priority")==1 )
							{
								 chkdate=rsAppInfo.getString("approved_date");
								 chkdate1=rsAppInfo.getString("rejected_date");
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						
						listApprers.add(apprvers);
					}
						//IT ENGINEER DETAIlS
						
						
						String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
						ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
						
							while(rsApprDetails1.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
								apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
								String empCode1=rsApprDetails1.getString("Approver_Id");
								
								String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
										+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
										+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
										+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
										+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
								if(rsAppInfo1.next())
								{
									
									String approveStatus=rsAppInfo1.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved"))
									{
										recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
										ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
										if(rsAppInfo2.next())
										{
											if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
											{
												apprvers.setApproveStatus(rsAppInfo1.getString("Req_Status"));
											apprvers.setDate(rsAppInfo2.getString("approved_date"));
											
										
										
										String comments="";
										comments=rsAppInfo2.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo2.getString("Comments"));
										}
										}
										}
									}else{
										
										recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
										ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
										if(rsAppInfo3.next())
										{
											if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
											{
											apprvers.setDate(rsAppInfo3.getString("rejected_date"));
											}
										
										String comments="";
										comments=rsAppInfo3.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo3.getString("Comments"));
										}
										}
									}
									
								}
								apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
						
							listApprers.add(apprvers);
							}
						
						request.setAttribute("approverDetails", listApprers);
					
				} 
				
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				request.setAttribute("folder", folder);
				forwardtype="viewFolderAccessform";
			}
		 
	    
		
		return mapping.findForward(forwardtype);
	}
	
	public ActionForward closemyrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		String chkdate="";
		HelpDeskForm help = (HelpDeskForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		String forwardtype="";
		String type=help.getRequestType();
		help.setRequestType(type);
		String reqno=help.getRequestNumber();
		
		 int i=0;
		
		if(type.equalsIgnoreCase("Active Directory User Creation"))
		{
			
			String insertAduser="update IT_HELPDESK set ITEng_Status='Closed' where Req_No="+reqno+"";
			
		    i=ad.SqlExecuteUpdate(insertAduser);
		    
		}
		
		if(type.equalsIgnoreCase("Email ID Create Request"))
		{
          String insertAduser="update IT_HELPDESK set ITEng_Status='Closed' where Req_No="+reqno+"";
			
		    i=ad.SqlExecuteUpdate(insertAduser);
		
		}
		
		if(type.equalsIgnoreCase("Email ID Change Request"))
		{
          String insertAduser="update IT_HELPDESK set ITEng_Status='Closed' where Req_No="+reqno+"";
			
		    i=ad.SqlExecuteUpdate(insertAduser);
		   
		}
		
		if(type.equalsIgnoreCase("Internet Access Request"))
		{
			
             String insertAduser="update IT_HELPDESK set ITEng_Status='Closed' where Req_No="+reqno+"";
			
		    i=ad.SqlExecuteUpdate(insertAduser);
		   
		}
		
		
		if(type.equalsIgnoreCase("External Drives Access Request"))
		{
			
          String insertAduser="update IT_HELPDESK set ITEng_Status='Closed' where Req_No="+reqno+"";
			
		    i=ad.SqlExecuteUpdate(insertAduser);
		    
		}
		
		if(type.equalsIgnoreCase("FTP Access Request"))
		{
			
        String insertAduser="update IT_HELPDESK set ITEng_Status='Closed' where Req_No="+reqno+"";
			
		    i=ad.SqlExecuteUpdate(insertAduser);
		}
		
		if(type.equalsIgnoreCase("File Server Access Request"))
		{
			
           String insertAduser="update IT_HELPDESK set ITEng_Status='Closed' where Req_No="+reqno+"";
			
		    i=ad.SqlExecuteUpdate(insertAduser);
			
		}
		
		
		if(type.equalsIgnoreCase("Active Directory User Creation"))
		{
			LinkedList Adusercreation=new LinkedList();
			String empno=user.getEmployeeNo();
			
			String data="select emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					
					
				
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
	ResultSet rs1= ad.selectQuery(data1);
	try {
		while(rs1.next())
		{
			help.setAssetvalue(rs1.getString("Asset_Details"));
			help.setAdloginname(rs1.getString("ADLoginName"));
			help.setServer(rs1.getString("Server_Address"));
			help.setReqdfolderacces(rs1.getString("Req_Folder_access"));
			help.setReason(rs1.getString("Reason"));
			help.setHostname(rs1.getString("HostName"));
			help.setIPNumber(rs1.getString("IPAddress"));
			help.setReqPriority(rs1.getString("Req_Priority"));
			
			
		}
	} catch (SQLException e) {
		  e.printStackTrace();
		}
	
	//Approver Details
	int  checkStatus=0;
	LinkedList listApprers=new LinkedList();
	String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
	ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
	try {
		while(rsApprDetails.next())
		{
			int jk=0;
			checkStatus=1;
			ApprovalsForm apprvers=new ApprovalsForm();
			apprvers.setPriority(rsApprDetails.getString("Priority"));
			
			if(rsApprDetails.getInt("Priority")==2)
			{
				int hodpresent=0;
				int hodpresentinIt=0;
				String HODPresentId="";
				String HODPresentname="";
				String itengineerid="";
				String itengineername="";
				String HOD="";
				String	pendingApprovers="";
				
				 //chk if he has Hod
				 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
				 ResultSet rs10= ad.selectQuery(chkhod);
				 try {
					while(rs10.next())
					 {
						 hodpresent=rs10.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				//get hod details
				
			
				if(hodpresent>0){
				String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
						" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
				 ResultSet rs2= ad.selectQuery(gethod);
				 
				 try {
					if(rs2.next())
					 {
					
						HODPresentId=rs2.getString("ApproverId");
						HODPresentname=rs2.getString("EMP_FULLNAME");
					 }
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				}
				
				//chk if he is present in It approver
				 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
				 ResultSet rs11= ad.selectQuery(chkhodinit);
				 try {
					while(rs11.next())
					 {
						hodpresentinIt=rs11.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				 
					if(hodpresent>0 && hodpresentinIt==0 )
					{
						
						
						try {
							
								checkStatus=1;
								ApprovalsForm apprvershod=new ApprovalsForm();
								apprvershod.setPriority("2");
								
								apprvershod.setEmployeeCode(HODPresentId);
								apprvershod.setEmployeeName(HODPresentname);
								
								String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
							+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
							+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
							+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								if(rsAppInfo.next())
								{
									apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
									String approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
									{
										apprvershod.setDate(rsAppInfo.getString("approved_date"));
									}else{
										apprvershod.setDate(rsAppInfo.getString("rejected_date"));
									}
									String comments="";
									comments=rsAppInfo.getString("Comments");
									if(comments=="null" )
									{
										apprvershod.setComments("");
									}else{
										apprvershod.setComments(rsAppInfo.getString("Comments"));
									}
								}
								
								
								listApprers.add(apprvershod);
							 jk=1;
							}
						
						catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
					}
				
				
			}
		
			
			apprvers.setPriority(rsApprDetails.getString("Priority"));
			if(jk==1){
				
				apprvers.setPriority(Integer.toString(listApprers.size()+1));
			}
			apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
			String empCode=rsApprDetails.getString("Approver_Id");
			
			String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
					+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
					+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
					+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
					+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
			
			ResultSet rsAppInfo=ad.selectQuery(recordStatus);
			if(rsAppInfo.next())
			{
				if(rsAppInfo.getInt("Priority")==1 )
				{
					 chkdate=rsAppInfo.getString("approved_date");
				}
				apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
				String approveStatus=rsAppInfo.getString("Req_Status");
				if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
				{
				apprvers.setDate(rsAppInfo.getString("approved_date"));
				}else{
					apprvers.setDate(rsAppInfo.getString("rejected_date"));
				}
				String comments="";
				comments=rsAppInfo.getString("Comments");
				if(comments=="null" )
				{
					apprvers.setComments("");
				}else{
					apprvers.setComments(rsAppInfo.getString("Comments"));
				}
			}
			apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
			
			listApprers.add(apprvers);
			
		}
			//IT ENGINEER DETAIlS
			
			
			String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
			
				while(rsApprDetails1.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(Integer.toString(listApprers.size()+1));
					
					apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
					String empCode1=rsApprDetails1.getString("Approver_Id");
					
					String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
					if(rsAppInfo1.next())
					{
						
						String approveStatus=rsAppInfo1.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved"))
						{
							recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
							ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
							if(rsAppInfo2.next())
							{
								if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
								{
									apprvers.setApproveStatus(rsAppInfo1.getString("Req_Status"));
								apprvers.setDate(rsAppInfo2.getString("approved_date"));
								
							
							
							String comments="";
							comments=rsAppInfo2.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo2.getString("Comments"));
							}
							}
							}
						}else{
							
							recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
							ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
							if(rsAppInfo3.next())
							{
								apprvers.setDate(rsAppInfo3.getString("rejected_date"));
								
							
							String comments="";
							comments=rsAppInfo3.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo3.getString("Comments"));
							}
							}
						}
						
					}
					apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
			
				listApprers.add(apprvers);
				}
			
			request.setAttribute("approverDetails", listApprers);
		
	} 
	
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
			
			
			forwardtype="viewAduserCreationform";
		}
		
		if(type.equalsIgnoreCase("Email ID Create Request"))
		{
			LinkedList LotusIDCreation=new LinkedList();
			String empno=user.getEmployeeNo();
			
			String data="select emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
				
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
					help.setOutsidecom(rs1.getString("comm_outside"));
					help.setIsHeHod(rs1.getString("Is_He_Hod"));
					help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
					help.setEnablewebAccess(rs1.getString("Enable_Web_access"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					int jk=0;
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(rsApprDetails.getInt("Priority")==2)
					{
						int hodpresent=0;
						int hodpresentinIt=0;
						String HODPresentId="";
						String HODPresentname="";
						String itengineerid="";
						String itengineername="";
						String HOD="";
						String	pendingApprovers="";
						
						 //chk if he has Hod
						 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
						 ResultSet rs10= ad.selectQuery(chkhod);
						 try {
							while(rs10.next())
							 {
								 hodpresent=rs10.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
						//get hod details
						
					
						if(hodpresent>0){
						String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
								" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
						 ResultSet rs2= ad.selectQuery(gethod);
						 
						 try {
							if(rs2.next())
							 {
							
								HODPresentId=rs2.getString("ApproverId");
								HODPresentname=rs2.getString("EMP_FULLNAME");
							 }
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
						}
						
						//chk if he is present in It approver
						 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
						 ResultSet rs11= ad.selectQuery(chkhodinit);
						 try {
							while(rs11.next())
							 {
								hodpresentinIt=rs11.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						 
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("2");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
												+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
												+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+HODPresentId+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
												+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
												+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
						
						
					}
				
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					
					String empCode=rsApprDetails.getString("Approver_Id");
					
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							int jk=0;
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
								
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus(rsAppInfo1.getString("Req_Status"));
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			request.setAttribute("LotusIDCreation", LotusIDCreation);
			forwardtype="viewLotusIDCreationform";
		}
		
		if(type.equalsIgnoreCase("Email ID Change Request"))
		{
			LinkedList LotusIDChange=new LinkedList();
			String empno=user.getEmployeeNo();
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					
					help.setEmpno(rs.getString("PERNR"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setFromemailId(rs1.getString("From_EmailID"));
					help.setOutsidecom(rs1.getString("comm_outside"));
					help.setDeleteoldmails(rs1.getString("delete_old_email"));
					help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
					help.setIsHeHod(rs1.getString("Is_He_Hod"));
					help.setTransferoldmail(rs1.getString("Transfer_Old_Emails"));
					help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
					help.setEnablewebAccess(rs1.getString("Enable_Web_access"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
				}
				
				
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					int jk=0;
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					
					if(rsApprDetails.getInt("Priority")==2)
					{
						int hodpresent=0;
						int hodpresentinIt=0;
						String HODPresentId="";
						String HODPresentname="";
						String itengineerid="";
						String itengineername="";
						String HOD="";
						String	pendingApprovers="";
						
						 //chk if he has Hod
						 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
						 ResultSet rs10= ad.selectQuery(chkhod);
						 try {
							while(rs10.next())
							 {
								 hodpresent=rs10.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
						//get hod details
						
					
						if(hodpresent>0){
						String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
								" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
						 ResultSet rs2= ad.selectQuery(gethod);
						 
						 try {
							if(rs2.next())
							 {
							
								HODPresentId=rs2.getString("ApproverId");
								HODPresentname=rs2.getString("EMP_FULLNAME");
							 }
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
						}
						
						//chk if he is present in It approver
						 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
						 ResultSet rs11= ad.selectQuery(chkhodinit);
						 try {
							while(rs11.next())
							 {
								hodpresentinIt=rs11.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						 
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("2");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
												+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
												+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+HODPresentId+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
												+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
												+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
						
						
					}
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
								
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus(rsAppInfo1.getString("Req_Status"));
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			request.setAttribute("LotusIDChange", LotusIDChange);
			forwardtype="viewLotusIDChangeform";
		}
		
		if(type.equalsIgnoreCase("Internet Access Request"))
		{
			LinkedList internet=new LinkedList();
			String empno=user.getEmployeeNo();
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
				
					help.setEmpno(rs.getString("PERNR"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
					
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					int jk=0;
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					
					if(rsApprDetails.getInt("Priority")==2)
					{
						int hodpresent=0;
						int hodpresentinIt=0;
						String HODPresentId="";
						String HODPresentname="";
						String itengineerid="";
						String itengineername="";
						String HOD="";
						String	pendingApprovers="";
						
						 //chk if he has Hod
						 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
						 ResultSet rs10= ad.selectQuery(chkhod);
						 try {
							while(rs10.next())
							 {
								 hodpresent=rs10.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
						//get hod details
						
					
						if(hodpresent>0){
						String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
								" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
						 ResultSet rs2= ad.selectQuery(gethod);
						 
						 try {
							if(rs2.next())
							 {
							
								HODPresentId=rs2.getString("ApproverId");
								HODPresentname=rs2.getString("EMP_FULLNAME");
							 }
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
						}
						
						//chk if he is present in It approver
						 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
						 ResultSet rs11= ad.selectQuery(chkhodinit);
						 try {
							while(rs11.next())
							 {
								hodpresentinIt=rs11.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						 
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("2");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
												+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
												+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+HODPresentId+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
												+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
												+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
						
						
					}
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and  "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and  "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
							
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus(rsAppInfo1.getString("Req_Status"));
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			request.setAttribute("Internet", internet);
			forwardtype="viewInternetAccessform";
		}
		
		if(type.equalsIgnoreCase("External Drives Access Request"))
		{
			LinkedList externaldrives=new LinkedList();
			String empno=user.getEmployeeNo();
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					
					help.setEmpno(rs.getString("PERNR"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAccesstype(rs1.getString("Access_type"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
					String device=rs1.getString("Device_type");
					if(device.equalsIgnoreCase("3"))
					{
						help.setCdrom("CDROM");
						help.setUsb("USB");
					}
					if(device.equalsIgnoreCase("1"))
					{
						help.setCdrom("CDROM");
						
					}
					if(device.equalsIgnoreCase("2"))
					{
						
						help.setUsb("USB");
					}
					
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					int jk=0;
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(rsApprDetails.getInt("Priority")==2)
					{
						int hodpresent=0;
						int hodpresentinIt=0;
						String HODPresentId="";
						String HODPresentname="";
						String itengineerid="";
						String itengineername="";
						String HOD="";
						String	pendingApprovers="";
						
						 //chk if he has Hod
						 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
						 ResultSet rs10= ad.selectQuery(chkhod);
						 try {
							while(rs10.next())
							 {
								 hodpresent=rs10.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
						//get hod details
						
					
						if(hodpresent>0){
						String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
								" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
						 ResultSet rs2= ad.selectQuery(gethod);
						 
						 try {
							if(rs2.next())
							 {
							
								HODPresentId=rs2.getString("ApproverId");
								HODPresentname=rs2.getString("EMP_FULLNAME");
							 }
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
						}
						
						//chk if he is present in It approver
						 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
						 ResultSet rs11= ad.selectQuery(chkhodinit);
						 try {
							while(rs11.next())
							 {
								hodpresentinIt=rs11.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						 
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("2");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
												+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
												+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+HODPresentId+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
												+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
												+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
						
						
					}
				
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
								
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus(rsAppInfo1.getString("Req_Status"));
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("externaldrives", externaldrives);
			forwardtype="viewexternaldrivesform";
		}
		
		if(type.equalsIgnoreCase("FTP Access Request"))
		{
			LinkedList ftp=new LinkedList();
			String empno=user.getEmployeeNo();
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					
					help.setEmpno(rs.getString("PERNR"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setRequiredfoldername(rs1.getString("Req_Folder_access"));
					help.setServer(rs1.getString("Server_Address"));
					help.setAccesstype(rs1.getString("Access_type"));
					help.setFrmdurationofaccess(rs1.getString("FrmRequiredDurationofAccess"));
					help.setTodurationofaccess(rs1.getString("ToRequiredDurationofAccess"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					int jk=0;
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(rsApprDetails.getInt("Priority")==2)
					{
						int hodpresent=0;
						int hodpresentinIt=0;
						String HODPresentId="";
						String HODPresentname="";
						String itengineerid="";
						String itengineername="";
						String HOD="";
						String	pendingApprovers="";
						
						 //chk if he has Hod
						 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
						 ResultSet rs10= ad.selectQuery(chkhod);
						 try {
							while(rs10.next())
							 {
								 hodpresent=rs10.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
						//get hod details
						
					
						if(hodpresent>0){
						String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
								" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
						 ResultSet rs2= ad.selectQuery(gethod);
						 
						 try {
							if(rs2.next())
							 {
							
								HODPresentId=rs2.getString("ApproverId");
								HODPresentname=rs2.getString("EMP_FULLNAME");
							 }
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
						}
						
						//chk if he is present in It approver
						 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
						 ResultSet rs11= ad.selectQuery(chkhodinit);
						 try {
							while(rs11.next())
							 {
								hodpresentinIt=rs11.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						 
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("2");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
												+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
												+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+HODPresentId+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
												+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
												+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
						
						
					}
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
							
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
										apprvers.setApproveStatus(rsAppInfo1.getString("Req_Status"));
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			request.setAttribute("ftp", ftp);
			forwardtype="viewFtpAccessform";
		}
		
		if(type.equalsIgnoreCase("File Server Access Request"))
		{
			LinkedList folder=new LinkedList();
			String empno=user.getEmployeeNo();
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
				
					help.setEmpno(rs.getString("PERNR"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setRequiredfoldername(rs1.getString("Req_Folder_access"));
					help.setServer(rs1.getString("Server_Address"));
					help.setAccesstype(rs1.getString("Access_type"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					int jk=0;
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(rsApprDetails.getInt("Priority")==2)
					{
						int hodpresent=0;
						int hodpresentinIt=0;
						String HODPresentId="";
						String HODPresentname="";
						String itengineerid="";
						String itengineername="";
						String HOD="";
						String	pendingApprovers="";
						
						 //chk if he has Hod
						 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
						 ResultSet rs10= ad.selectQuery(chkhod);
						 try {
							while(rs10.next())
							 {
								 hodpresent=rs10.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
						//get hod details
						
					
						if(hodpresent>0){
						String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
								" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
						 ResultSet rs2= ad.selectQuery(gethod);
						 
						 try {
							if(rs2.next())
							 {
							
								HODPresentId=rs2.getString("ApproverId");
								HODPresentname=rs2.getString("EMP_FULLNAME");
							 }
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
						}
						
						//chk if he is present in It approver
						 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
						 ResultSet rs11= ad.selectQuery(chkhodinit);
						 try {
							while(rs11.next())
							 {
								hodpresentinIt=rs11.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						 
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("2");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
												+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
												+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+HODPresentId+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
												+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
												+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
						
						
					}
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
								
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus(rsAppInfo1.getString("Req_Status"));
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			request.setAttribute("folder", folder);
			forwardtype="viewFolderAccessform";
		}
		
		
		
		
		
		return mapping.findForward(forwardtype);
		
	}
	
	
	public ActionForward modifyItrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HelpDeskForm help = (HelpDeskForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		String forwardtype="";
		String type=help.getRequestType();
		help.setRequestType(type);
		String requstNo=help.getRequestNumber();
		
		String empno="";
		 empno=user.getEmployeeNo();
		 help.setEmpno(empno);
		
		String localhostname="";
		String ipaddress="";
		String username="";
		try {
			localhostname = (InetAddress.getLocalHost()).getCanonicalHostName();
			ipaddress = (InetAddress.getLocalHost()).getHostAddress();
			username=(InetAddress.getLocalHost()).getHostName();
			
		} catch (UnknownHostException e1) {
			
			e1.printStackTrace();
		}
		
		ipaddress = request.getHeader("X-FORWARDED-FOR");  // proxy
		if(ipaddress==null)
		{
			ipaddress = request.getRemoteAddr();
		}

		 int i=0;
		 
		 if(type.equalsIgnoreCase("Active Directory User Creation"))
			{
				 
				
				String insertAduser="update IT_HELPDESK set Asset_Details='"+help.getAssetvalue()+"',ADLoginName='"+help.getAdloginname()+"', " +
				"Server_Address='"+help.getServer()+"' ,Req_Folder_access='"+help.getReqdfolderacces()+"',Reason='"+help.getReason()+"',Req_Priority='"+help.getReqPriority()+"' where Req_No="+requstNo+"";
				
				
				 i=ad.SqlExecuteUpdate(insertAduser);
				 if(i>0){
					 
					 help.setMessage("Request updated successfully ");
					 
					 String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
					
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setHostname(username);
						help.setIPNumber(ipaddress);
						
					  
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
			
				 }
				 else
				 {
					 help.setMessage1("Error...When request Updation.Please check");
				 }
				 
				
					request.setAttribute("modify", "modify");
				 forwardtype="displayAduserCreationform";
			}
		 
		 if(type.equalsIgnoreCase("Active Directory User Deletion"))
			{

			 
				
				String insertAduser="update IT_HELPDESK set Asset_Details='"+help.getAssetvalue()+"',ADLoginName='"+help.getAdloginname()+"', " +
				"Server_Address='"+help.getServer()+"' ,Req_Folder_Deletion='"+help.getReqdfolderdeletion()+"',Delete_From='"+help.getDeletefrom()+"',Reason='"+help.getReason()+"',Req_Priority='"+help.getReqPriority()+"' where Req_No="+requstNo+"";
				
				
				 i=ad.SqlExecuteUpdate(insertAduser);
				 if(i>0){
					 
					 help.setMessage("Request updated successfully ");
					 
					 String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
					
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setHostname(username);
						help.setIPNumber(ipaddress);
						
					  
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
			
				 }
				 else
				 {
					 help.setMessage1("Error...When request Updation.Please check");
				 }
				 
				
					request.setAttribute("modify", "modify");
				 forwardtype="displayAduserDeletionform";
			
			 
			}
			
			if(type.equalsIgnoreCase("Email ID Create Request"))
			{
				//newly_Joinied,joining_date,Total_Mailids
				String insertAduser="update IT_HELPDESK set ADLoginName='"+help.getAdloginname()+"',SuggestedEmailId='"+help.getSuggestedEmailId()+"', " +
						"comm_outside='"+help.getOutsidecom()+"',Is_He_Hod='"+help.getIsHeHod()+"',Enable_Web_access='"+help.getEnablewebAccess()+"'," +
						" Is_This_common_ID='"+help.getIsthisCommonId()+"',Reason='"+help.getReason()+"',Req_Priority='"+help.getReqPriority()+"' where Req_No="+requstNo+" ";
				
				
				
				 i=ad.SqlExecuteUpdate(insertAduser);
                 if(i>0){
					 
					 help.setMessage("Request updated successfully ");
					 String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
					
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setHostname(username);
						help.setIPNumber(ipaddress);
						
					  
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
			
				 }
				 else
				 {
					 help.setMessage1("Error...When request Updation.Please check");
				 }
             	request.setAttribute("modify", "modify");
				 forwardtype="displayLotusIDCreationform";
			}
			
			if(type.equalsIgnoreCase("Email ID Change Request"))
			{
				String insertAduser="update IT_HELPDESK set ADLoginName='"+help.getAdloginname()+"',From_EmailID='"+help.getFromemailId()+"',SuggestedEmailId='"+help.getSuggestedEmailId()+"', " +
				"comm_outside='"+help.getOutsidecom()+"',Transfer_Old_Emails='"+help.getTransferoldmail()+"',delete_old_email='"+help.getDeleteoldmails()+"',Is_He_Hod='"+help.getIsHeHod()+"',Enable_Web_access='"+help.getEnablewebAccess()+"'," +
				" Is_This_common_ID='"+help.getIsthisCommonId()+"',Reason='"+help.getReason()+"',Req_Priority='"+help.getReqPriority()+"' where Req_No="+requstNo+" ";
		
				
				
				 i=ad.SqlExecuteUpdate(insertAduser);
                  if(i>0){
					 
					 help.setMessage("Request updated successfully ");
					 String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
					
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setHostname(username);
						help.setIPNumber(ipaddress);
						
					  
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
			
				 }
				 else
				 {
					 help.setMessage1("Error...When request Updation.Please check");
				 }
              	request.setAttribute("modify", "modify");
				 forwardtype="displayLotusIDChangeform";
			}
			
			if(type.equalsIgnoreCase("Email ID Delete Request"))
			{

				String insertAduser="update IT_HELPDESK set ADLoginName='"+help.getAdloginname()+"',EmailID_Deleted='"+help.getEmailidDelete()+"', " +
						"Is_He_Hod='"+help.getIsHeHod()+"',Delete_From='"+help.getDeletefrom()+"',Is_This_common_ID='"+help.getIsthisCommonId()+"',Reason='"+help.getReason()+"',Req_Priority='"+help.getReqPriority()+"' where Req_No="+requstNo+" ";
				
				
				
				 i=ad.SqlExecuteUpdate(insertAduser);
                 if(i>0){
					 
					 help.setMessage("Request updated successfully ");
					 String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
					
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setHostname(username);
						help.setIPNumber(ipaddress);
						
					  
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
			
				 }
				 else
				 {
					 help.setMessage1("Error...When request Updation.Please check");
				 }
             	request.setAttribute("modify", "modify");
				 forwardtype="displayLotusIDDeletionform";
			
				
			}
			
			if(type.equalsIgnoreCase("Internet Access Request"))
			{
				String insertAduser="update IT_HELPDESK set Asset_Details='"+help.getAssetvalue()+"',ADLoginName='"+help.getAdloginname()+"', " +
				"Reason='"+help.getReason()+"',Req_Priority='"+help.getReqPriority()+"' where Req_No="+requstNo+"";
				
				
				 i=ad.SqlExecuteUpdate(insertAduser); 
				  
                  if(i>0){
					 
					 help.setMessage("Request updated successfully ");
					 String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
					
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setHostname(username);
						help.setIPNumber(ipaddress);
						
					  
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
			
				 }
				 else
				 {
					 help.setMessage1("Error...When request Updation.Please check");
				 }
              	request.setAttribute("modify", "modify");
					forwardtype="displayInternetAccessform";
			}
			
			if(type.equalsIgnoreCase("External Drives Access Request"))
			{
				String devicetype="";
				String a=help.getCdrom();
				String b=help.getUsb();
				
				if(a==null)
				{
				a="";
				}
				if(b==null)
				{
				b="";
				}
				
				
				 if(a.equalsIgnoreCase("CDROM") && b.equalsIgnoreCase("USB")){
					 devicetype="3";
				 }else{
					 if(a.equalsIgnoreCase("CDROM")){
						 devicetype="1";
					 }
					 if(b.equalsIgnoreCase("USB")){
						 devicetype="2";
					 }
				 }
				
				String insertAduser="update IT_HELPDESK set Asset_Details='"+help.getAssetvalue()+"',ADLoginName='"+help.getAdloginname()+"', " +
				"Device_type='"+devicetype+"',Access_type='"+help.getAccesstype()+"',Reason='"+help.getReason()+"',Req_Priority='"+help.getReqPriority()+"' where Req_No="+requstNo+"";
				
				
				 i=ad.SqlExecuteUpdate(insertAduser);
                 if(i>0){
					 
					 help.setMessage("Request updated successfully ");
					 
					 String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
					
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setHostname(username);
						help.setIPNumber(ipaddress);
						
					  
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
			
				 }
				 else
				 {
					 help.setMessage1("Error...When request Updation.Please check");
				 }
             	request.setAttribute("modify", "modify");
				 forwardtype="displayexternaldrivesform";
			}
			
			if(type.equalsIgnoreCase("FTP Access Request"))
			{
				String insertAduser="update IT_HELPDESK set Asset_Details='"+help.getAssetvalue()+"',ADLoginName='"+help.getAdloginname()+"', " +
				" Server_Address='"+help.getServer()+"',Req_Folder_access='"+help.getRequiredfoldername()+"',Access_type='"+help.getAccesstype()+"'," +
				"FrmRequiredDurationofAccess='"+help.getFrmdurationofaccess()+"',ToRequiredDurationofAccess='"+help.getTodurationofaccess()+"',Reason='"+help.getReason()+"',Req_Priority='"+help.getReqPriority()+"' where Req_No="+requstNo+"";
				
				
				 i=ad.SqlExecuteUpdate(insertAduser);
               if(i>0){
					 
					 help.setMessage("Request updated successfully ");
					 String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
					
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setHostname(username);
						help.setIPNumber(ipaddress);
						
					  
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
			
				 }
				 else
				 {
					 help.setMessage1("Error...When request Updation.Please check");
				 }
				 
           	request.setAttribute("modify", "modify");
				 forwardtype="displayFtpAccessform";
			}
			
			if(type.equalsIgnoreCase("File Server Access Request"))
			{
				String insertAduser="update IT_HELPDESK set Asset_Details='"+help.getAssetvalue()+"',ADLoginName='"+help.getAdloginname()+"', " +
				"Server_Address='"+help.getServer()+"' ,Req_Folder_access='"+help.getRequiredfoldername()+"',Access_type='"+help.getAccesstype()+"',Reason='"+help.getReason()+"',Req_Priority='"+help.getReqPriority()+"' where Req_No="+requstNo+"";
				
				 i=ad.SqlExecuteUpdate(insertAduser);
                  if(i>0){
					 
					 help.setMessage("Request updated successfully ");
					 String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
					
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setHostname(username);
						help.setIPNumber(ipaddress);
						
					  
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
			
				 }
				 else
				 {
					 help.setMessage1("Error...When request Updation.Please check");
				 }
                  
              	request.setAttribute("modify", "modify");
				 forwardtype="displayFolderAccessform";
			}
			
			return mapping.findForward(forwardtype);
			}
		
	    
		 
		
	
	
	public ActionForward editITRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HelpDeskForm help = (HelpDeskForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		String forwardType="";
		String type=request.getParameter("type");
		help.setRequestType(type);
		String requstNo=request.getParameter("requstNo");
		help.setRequestNumber(requstNo);
		
		 String empno="";
		 empno=user.getEmployeeNo();
		
		String localhostname="";
		String ipaddress="";
		String username="";
		try {
			localhostname = (InetAddress.getLocalHost()).getCanonicalHostName();
			ipaddress = (InetAddress.getLocalHost()).getHostAddress();
			username=(InetAddress.getLocalHost()).getHostName();
			
		} catch (UnknownHostException e1) {
			
			e1.printStackTrace();
		}

		ipaddress = request.getHeader("X-FORWARDED-FOR");   // proxy
		if(ipaddress==null)
		{
			ipaddress = request.getRemoteAddr();
		}
		
		if(type.equalsIgnoreCase("Active Directory User Creation"))
		{
			LinkedList Adusercreation=new LinkedList();
         
			
				
			String data="select emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
				
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String data1=" select * from IT_HELPDESK where Req_No="+requstNo+"";	
	ResultSet rs1= ad.selectQuery(data1);
	try {
		while(rs1.next())
		{
			help.setAssetvalue(rs1.getString("Asset_Details"));
			help.setAdloginname(rs1.getString("ADLoginName"));
			help.setServer(rs1.getString("Server_Address"));
			help.setReqdfolderacces(rs1.getString("Req_Folder_access"));
			help.setReason(rs1.getString("Reason"));
			help.setReqPriority(rs1.getString("Req_Priority"));
			
		}
	} catch (SQLException e) {
		  e.printStackTrace();
		}
			request.setAttribute("modify", "modify");
	request.setAttribute("Adusercreation", Adusercreation);
	forwardType="displayAduserCreationform";
		}
		
		if(type.equalsIgnoreCase("Active Directory User Deletion"))
		{
			LinkedList AduserDeletion=new LinkedList();
         
			
				
			String data="select emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
				
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String data1=" select * from IT_HELPDESK where Req_No="+requstNo+"";	
	ResultSet rs1= ad.selectQuery(data1);
	try {
		while(rs1.next())
		{
			help.setAssetvalue(rs1.getString("Asset_Details"));
			help.setAdloginname(rs1.getString("ADLoginName"));
			help.setServer(rs1.getString("Server_Address"));
			help.setReqdfolderdeletion(rs1.getString("Req_Folder_Deletion"));
			help.setDeletefrom(rs1.getString("Delete_From"));
			help.setReason(rs1.getString("Reason"));
			help.setReqPriority(rs1.getString("Req_Priority"));
			
		}
	} catch (SQLException e) {
		  e.printStackTrace();
		}
			request.setAttribute("modify", "modify");
	request.setAttribute("AduserDeletion", AduserDeletion);
	forwardType="displayAduserDeletionform";
		}
		
		if(type.equalsIgnoreCase("Email ID Create Request"))
		{
			LinkedList LotusIDCreation=new LinkedList();
          
			
			
			
			String data="select emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String data1=" select * from IT_HELPDESK where Req_No="+requstNo+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
					help.setOutsidecom(rs1.getString("comm_outside"));
					help.setIsHeHod(rs1.getString("Is_He_Hod"));
					help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
					help.setEnablewebAccess(rs1.getString("Enable_Web_access"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					String  newlyJoinined=rs1.getString("newly_Joinied");
					if(newlyJoinined.equals("Yes")){
						help.setNewlyJoinedYes("true");
					}else{
						help.setNewlyJoinedNo("true");
					}
					help.setJoiningDate(rs1.getString("joining_date"));
					help.setTotalIDs(rs1.getString("Total_Mailids"));
					help.setMailIDRetained(rs1.getString("OldMailID_Retained"));
					String allowOutSideComm=rs1.getString("comm_outside");
					if(allowOutSideComm.equals("Yes"))
						help.setOutsidecomyes("true");
					else
						help.setOutsidecomno("true");
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			request.setAttribute("modify", "modify");
			request.setAttribute("LotusIDCreation", LotusIDCreation);
			forwardType="displayLotusIDCreationform";
		}
		
		if(type.equalsIgnoreCase("Email ID Change Request"))
		{
			LinkedList LotusIDChange=new LinkedList();
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String data1=" select * from IT_HELPDESK where Req_No="+requstNo+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setFromemailId(rs1.getString("From_EmailID"));
					help.setOutsidecom(rs1.getString("comm_outside"));
					help.setDeleteoldmails(rs1.getString("delete_old_email"));
					help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
					help.setIsHeHod(rs1.getString("Is_He_Hod"));
					help.setTransferoldmail(rs1.getString("Transfer_Old_Emails"));
					help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
					help.setEnablewebAccess(rs1.getString("Enable_Web_access"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					String  newlyJoinined=rs1.getString("newly_Joinied");
					if(newlyJoinined.equals("Yes")){
						help.setNewlyJoinedYes("true");
					}else{
						help.setNewlyJoinedNo("true");
					}
					help.setJoiningDate(rs1.getString("joining_date"));
					help.setTotalIDs(rs1.getString("Total_Mailids"));
					help.setMailIDRetained(rs1.getString("OldMailID_Retained"));
					String allowOutSideComm=rs1.getString("comm_outside");
					if(allowOutSideComm.equals("Yes"))
						help.setOutsidecomyes("true");
					else
						help.setOutsidecomno("true");
					
					
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			request.setAttribute("modify", "modify");
			request.setAttribute("LotusIDChange", LotusIDChange);
			forwardType="displayLotusIDChangeform";
		}
		
		
		
		if(type.equalsIgnoreCase("Email ID Delete Request"))
		{

			LinkedList LotusIDDeletion=new LinkedList();
          
			
			
			
			String data="select emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String data1=" select * from IT_HELPDESK where Req_No="+requstNo+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
				    help.setEmailidDelete(rs1.getString("EmailID_Deleted"));
					help.setIsHeHod(rs1.getString("Is_He_Hod"));
					help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
					help.setDeletefrom(rs1.getString("Delete_From"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority(rs1.getString("Req_Priority"));
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			request.setAttribute("modify", "modify");
			request.setAttribute("LotusIDDeletion", LotusIDDeletion);
			forwardType="displayLotusIDDeletionform";
		
			
		}
		
		
		if(type.equalsIgnoreCase("Internet Access Request"))
		{
			LinkedList internet=new LinkedList();
      
			
		
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			String data1=" select * from IT_HELPDESK where Req_No="+requstNo+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					
					
					
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
					
			request.setAttribute("modify", "modify");
			request.setAttribute("Internet", internet);
			forwardType="displayInternetAccessform";
		}
		
		if(type.equalsIgnoreCase("External Drives Access Request"))
		{
			LinkedList externaldrives=new LinkedList();

			
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			String data1=" select * from IT_HELPDESK where Req_No="+requstNo+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAccesstype(rs1.getString("Access_type"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					String device=rs1.getString("Device_type");
					if(device.equalsIgnoreCase("3"))
					{
						help.setCdrom("CDROM");
						help.setUsb("USB");
					}
					if(device.equalsIgnoreCase("1"))
					{
						help.setCdrom("CDROM");
						
					}
					if(device.equalsIgnoreCase("2"))
					{
						
						help.setUsb("USB");
					}
					
					
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			request.setAttribute("modify", "modify");
			request.setAttribute("externaldrives", externaldrives);
			forwardType="displayexternaldrivesform";
		}
		
		if(type.equalsIgnoreCase("FTP Access Request"))
		{
			LinkedList ftp=new LinkedList();
           
			
			
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String data1=" select * from IT_HELPDESK where Req_No="+requstNo+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setRequiredfoldername(rs1.getString("Req_Folder_access"));
					help.setServer(rs1.getString("Server_Address")); 
					help.setAccesstype(rs1.getString("Access_type"));
					help.setFrmdurationofaccess(rs1.getString("FrmRequiredDurationofAccess"));
					help.setTodurationofaccess(rs1.getString("ToRequiredDurationofAccess"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority (rs1.getString("Req_Priority"));
					String fileusername=rs1.getString("UserName");
					 if(fileusername!=null)
						 help.setUsername(fileusername);
					 else
						 help.setUsername("");
					 
					   String filePwd=rs1.getString("Password");
					   if(filePwd!=null)
						   help.setPassword(filePwd);
					   else
						   help.setPassword("");
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			request.setAttribute("modify", "modify");
			request.setAttribute("ftp", ftp);
			forwardType="displayFtpAccessform";
		}
		
		if(type.equalsIgnoreCase("File Server Access Request"))
		{
			LinkedList folder=new LinkedList();
        
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String data1=" select * from IT_HELPDESK where Req_No="+requstNo+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setAccesstype(rs1.getString("Access_type"));
					help.setRequiredfoldername(rs1.getString("Req_Folder_access"));
					help.setServer(rs1.getString("Server_Address"));
					help.setReason(rs1.getString("Reason"));
					help.setReqPriority(rs1.getString("Req_Priority"));
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			request.setAttribute("modify", "modify");
			request.setAttribute("folder", folder);
			forwardType="displayFolderAccessform";
		}
		
		
		
		return mapping.findForward(forwardType);
	}
	
	public ActionForward deleteItRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HelpDeskForm help1 = (HelpDeskForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		String forwardtype="";
		String type=request.getParameter("type");
		help1.setRequestType(type);
		String reqno=request.getParameter("requstNo");
		String reqType = request.getParameter("reqType");
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
	    String dateNow = ft.format(dNow);
		
		int i=0;
		
		if(reqType.equalsIgnoreCase("Helpdesk Request"))
		{
			
		
			
		String dletetAduser="delete from IT_HELPDESK where Req_No="+reqno+"";
		
		 i=ad.SqlExecuteUpdate(dletetAduser);
		 
		 String dleteinallrequst="delete from All_Request where Req_Id="+reqno+" and Req_Type='IT Request'";
		 i=ad.SqlExecuteUpdate(dleteinallrequst);
		 
		 //transactiom
		 
		 String trans="insert into IT_TRANSACTION_DETAILS(Req_No,Emp_No,Req_type,Approver_ID,Req_Status,Transaction_Date,ITEng_Status)" +
				" values('"+reqno+"','"+user.getEmployeeNo()+"','"+type+"','','Deleted','"+dateNow+"','')";
				 	 i=ad.SqlExecuteUpdate(trans);
				 	 
		}
		
		if(reqType.equalsIgnoreCase("Sap issues"))
		{
			String dletetAduser="delete from IT_SAP_ISSUES where Req_No="+reqno+"";
			
			 i=ad.SqlExecuteUpdate(dletetAduser);
			 
			 String dleteinallrequst="delete from All_Request where Req_Id="+reqno+" and Req_Type='IT Sap Request'";
			 i=ad.SqlExecuteUpdate(dleteinallrequst);
			 
			 //transactiom
			 
			 String trans="insert into IT_TRANSACTION_DETAILS(Req_No,Emp_No,Req_type,Approver_ID,Req_Status,Transaction_Date,ITEng_Status)" +
					" values('"+reqno+"','"+user.getEmployeeNo()+"','"+type+"','','Deleted','"+dateNow+"','')";
					 	 i=ad.SqlExecuteUpdate(trans);
		}
		 
		 displayMyRequestList(mapping, form, request, response);

		return mapping.findForward("myrequest");
	}
	
	public ActionForward viewrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException 
	{
		String chkdate1="";
 		String chkdate="";
 		String cate="";
 		String filepath2="";
		HelpDeskForm help = (HelpDeskForm) form;
		MyRequestForm myReqForm = new MyRequestForm(); 
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		String forwardtype="";
		String localhostname="";
		String ipaddress="";
		String username="";
		try {
			localhostname = (InetAddress.getLocalHost()).getCanonicalHostName();
			ipaddress = (InetAddress.getLocalHost()).getHostAddress();
			username=(InetAddress.getLocalHost()).getHostName();
			
		} catch (UnknownHostException e1) {
			
			e1.printStackTrace();
		}
		
		String type=request.getParameter("type");
		help.setRequestType(type);
		String reqno=request.getParameter("requstNo");
		help.setRequestNumber(reqno);
		String status=request.getParameter("status");
		help.setItEngStatus(status);
		String issreqtype =request.getParameter("issrequestype");
		
		help.setIssrequestype(issreqtype );
	//	System.out.println(help.getIssrequestype());
		if(issreqtype.equalsIgnoreCase("Helpdesk Request"))
		  {	
			
			
			if(type.equalsIgnoreCase("Qms Application Access"))
			{
	
				LinkedList Adusercreation=new LinkedList();
				String empno="";
				
				String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
				ResultSet rs00= ad.selectQuery(data00);
				try {
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setEmpno(rs.getString("PERNR"));
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				String locID="";
				String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
		ResultSet rs1= ad.selectQuery(data1);
		try {
			while(rs1.next())
			{
				help.setAssetvalue(rs1.getString("Asset_Details"));
				help.setAdloginname(rs1.getString("ADLoginName"));
				help.setServer(rs1.getString("Server_Address"));
				help.setReqdfolderacces(rs1.getString("Req_Folder_access"));
				help.setReason(rs1.getString("Reason"));
				help.setHostname(rs1.getString("HostName"));
				help.setIPNumber(rs1.getString("IPAddress"));
				help.setReqPriority(rs1.getString("Req_Priority"));
				help.setLocationId(rs1.getString("QMS_LocId"));
				help.setUser(rs1.getString("QMS_GrpId"));
				help.setView(rs1.getString("QMS_View"));
                locID=rs1.getString("Location");
		
			}
		} catch (SQLException e) {
			  e.printStackTrace();
			}
		
		//Approver Details
		int  checkStatus=0;
		LinkedList listApprers=new LinkedList();
		int jk=0;
		
		//HOD
	
		int hodpresent=0;
		int hodpresentinIt=0;
		String HODPresentId="";
		String HODPresentname="";
		String itengineerid="";
		String itengineername="";
		String HOD="";
		String	pendingApprovers="";
		
		 //chk if he has Hod
		 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
		 ResultSet rs10= ad.selectQuery(chkhod);
		 try {
			while(rs10.next())
			 {
				 hodpresent=rs10.getInt(1);
			 }
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		//get hod details
		
	
		if(hodpresent>0){
		String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
				" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
		 ResultSet rs2= ad.selectQuery(gethod);
		 
		 try {
			if(rs2.next())
			 {
			
				HODPresentId=rs2.getString("ApproverId");
				HODPresentname=rs2.getString("EMP_FULLNAME");
			 }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		}
		
		//chk if he is present in It approver
		 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
		 ResultSet rs11= ad.selectQuery(chkhodinit);
		 try {
			while(rs11.next())
			 {
				hodpresentinIt=rs11.getInt(1);
			 }
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		 
		 
	
		 
		 
				
			if(hodpresent>0 && hodpresentinIt==0 )
			{
				
				
				try {
					
						checkStatus=1;
						ApprovalsForm apprvershod=new ApprovalsForm();
						apprvershod.setPriority("1");
						
						apprvershod.setEmployeeCode(HODPresentId);
						apprvershod.setEmployeeName(HODPresentname);
						
						String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
					+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
					+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
					+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
							{
								apprvershod.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvershod.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvershod.setComments("");
							}else{
								apprvershod.setComments(rsAppInfo.getString("Comments"));
							}
						}
						
						
						listApprers.add(apprvershod);
					 jk=1;
					}
				
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		
		String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
		ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
		try {
			while(rsApprDetails.next())
			{
				
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				
					
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				if(jk==1){
					
					apprvers.setPriority(Integer.toString(listApprers.size()+1));
				}
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				
				String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
						+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
						+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
						+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
						+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
				
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				if(rsAppInfo.next())
				{
					if(rsAppInfo.getInt("Priority")==1 )
					{
						 chkdate=rsAppInfo.getString("approved_date");
						 chkdate1=rsAppInfo.getString("rejected_date");
					}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
					{
						
					apprvers.setDate(rsAppInfo.getString("approved_date"));
					
					}else{
						apprvers.setDate(rsAppInfo.getString("rejected_date"));
					}
					String comments="";
					comments=rsAppInfo.getString("Comments");
					if(comments=="null" )
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
				
				listApprers.add(apprvers);
				
			}
				//IT ENGINEER DETAIlS
				
				
				String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
				
					while(rsApprDetails1.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
						
						apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
						String empCode1=rsApprDetails1.getString("Approver_Id");
						
						String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
						if(rsAppInfo1.next())
						{
							
							String approveStatus=rsAppInfo1.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved"))
							{
								recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
								ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
								if(rsAppInfo2.next())
								{
									if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
									{
										apprvers.setApproveStatus("Closed");
									apprvers.setDate(rsAppInfo2.getString("approved_date"));
									
								
								
								String comments="";
								comments=rsAppInfo2.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo2.getString("Comments"));
								}
								}
								}
							}else{
								
								recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
								ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
								if(rsAppInfo3.next())
								{
									if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
									{
									apprvers.setDate(rsAppInfo3.getString("rejected_date"));
									}
								
								String comments="";
								comments=rsAppInfo3.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo3.getString("Comments"));
								}
								}
							}
							
						}
						apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
				
					listApprers.add(apprvers);
					}
				
				request.setAttribute("approverDetails", listApprers);
			
		} 
		
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
				
				forwardtype="viewQmsform";
			
				
			}
			
			if(type.equalsIgnoreCase("New IT Spares Request"))
			{
	
				LinkedList Adusercreation=new LinkedList();
				String empno="";
				
				String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
				ResultSet rs00= ad.selectQuery(data00);
				try {
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setEmpno(rs.getString("PERNR"));
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				String locID="";
				String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
		ResultSet rs1= ad.selectQuery(data1);
		try {
			while(rs1.next())
			{
				help.setAssetvalue(rs1.getString("Asset_Details"));
				help.setAdloginname(rs1.getString("ADLoginName"));
				help.setServer(rs1.getString("Server_Address"));
				help.setReqdfolderacces(rs1.getString("Req_Folder_access"));
				help.setReason(rs1.getString("Reason"));
				help.setHostname(rs1.getString("HostName"));
				help.setIPNumber(rs1.getString("IPAddress"));
				help.setReqPriority(rs1.getString("Req_Priority"));
				help.setAssetcategory(rs1.getString("Category"));
				help.setReqbydate(rs1.getString("reqbydate"));
				help.setSuggestmodelname(rs1.getString("suggmodel"));
				
				help.setGxpyes(rs1.getString("gxp"));
				
				help.setTypeofprinter(rs1.getString("typeofprinter"));
				help.setUsers(rs1.getString("noofusers"));
				help.setPrintvolpermonth(rs1.getString("printvolpm"));
				help.setSuggestmodelno(rs1.getString("suggmodelno"));
				help.setApprxvalue(rs1.getString("Apprxvalue"));
		
				help.setLanyes(rs1.getString("lan"));
				help.setAnysplreq(rs1.getString("anysplreq"));
				help.setPurpose(rs1.getString("purpose"));
                locID=rs1.getString("Location");
		
			}
		} catch (SQLException e) {
			  e.printStackTrace();
			}
		
		//Approver Details
		int  checkStatus=0;
		LinkedList listApprers=new LinkedList();
		int jk=0;
		
		//HOD
	
		int hodpresent=0;
		int hodpresentinIt=0;
		String HODPresentId="";
		String HODPresentname="";
		String itengineerid="";
		String itengineername="";
		String HOD="";
		String	pendingApprovers="";
		
		 //chk if he has Hod
		 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
		 ResultSet rs10= ad.selectQuery(chkhod);
		 try {
			while(rs10.next())
			 {
				 hodpresent=rs10.getInt(1);
			 }
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		//get hod details
		
	
		if(hodpresent>0){
		String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
				" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
		 ResultSet rs2= ad.selectQuery(gethod);
		 
		 try {
			if(rs2.next())
			 {
			
				HODPresentId=rs2.getString("ApproverId");
				HODPresentname=rs2.getString("EMP_FULLNAME");
			 }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		}
		
		//chk if he is present in It approver
		 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
		 ResultSet rs11= ad.selectQuery(chkhodinit);
		 try {
			while(rs11.next())
			 {
				hodpresentinIt=rs11.getInt(1);
			 }
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		 
		 
	
		 
		 
				
		//Plant head
			String plantheadid="";
			String plantheadname="";
			String plantheaddep="";
			String plantheaddesg="";
			String a=locID;
			if(!a.equalsIgnoreCase("16"))
			{
				String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
				ResultSet pl=ad.selectQuery(plant);
				try {
					while(pl.next())
					 {
						plantheadid=pl.getString("pernr");
						plantheadname=pl.getString("EMP_FULLNAME");
						plantheaddep=pl.getString("DPTSTXT");
						plantheaddesg=pl.getString("DSGSTXT");
					 }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		 //Email_ID Approver
			String emailApprvrid="";
			String emailApprvrname="";
			String emailApprvrdep="";
			String emailApprvrdesg="";
			
			String plant1 ="select el.Plant,el.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from Emial_IdApprvr el,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=el.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
			ResultSet el=ad.selectQuery(plant1);
			try {
				while(el.next())
				 {
					emailApprvrid=el.getString("pernr");
					emailApprvrname=el.getString("EMP_FULLNAME");
					emailApprvrdep=el.getString("DPTSTXT");
					emailApprvrdesg=el.getString("DSGSTXT");
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(plantheadid.equalsIgnoreCase(""))
			{
				if(hodpresent>0 && hodpresentinIt==0 )
				{
					
					
					try {
						
							checkStatus=1;
							ApprovalsForm apprvershod=new ApprovalsForm();
							apprvershod.setPriority("1");
							
							apprvershod.setEmployeeCode(HODPresentId);
							apprvershod.setEmployeeName(HODPresentname);
							
							String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
						+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
						+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
						+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									apprvershod.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvershod.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvershod.setComments("");
								}else{
									apprvershod.setComments(rsAppInfo.getString("Comments"));
								}
							}
							
							
							listApprers.add(apprvershod);
						 jk=1;
						}
					
					catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
				}
				
				
			}
			
		 
		 if(!plantheadid.equalsIgnoreCase(""))
			{
			 
									
				try {
					
						checkStatus=1;
						ApprovalsForm apprvershod=new ApprovalsForm();
						apprvershod.setPriority("1");
						
						apprvershod.setEmployeeCode(plantheadid);
						apprvershod.setEmployeeName(plantheadname);
						
						String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
					+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
					+ "all_r.Last_Approver='"+plantheadid+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
					+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
							{
								apprvershod.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvershod.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvershod.setComments("");
							}else{
								apprvershod.setComments(rsAppInfo.getString("Comments"));
							}
						}
						
						
						listApprers.add(apprvershod);
					 jk=1;
					}
				
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		
		

		
		
		
		String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
		ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
		try {
			while(rsApprDetails.next())
			{
				
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				
					
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				if(jk==1){
					
					apprvers.setPriority(Integer.toString(listApprers.size()+1));
				}
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				
				String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
						+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
						+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
						+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
						+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
				
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				if(rsAppInfo.next())
				{
					if(rsAppInfo.getInt("Priority")==1 )
					{
						 chkdate=rsAppInfo.getString("approved_date");
						 chkdate1=rsAppInfo.getString("rejected_date");
					}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
					{
						
					apprvers.setDate(rsAppInfo.getString("approved_date"));
					
					}else{
						apprvers.setDate(rsAppInfo.getString("rejected_date"));
					}
					String comments="";
					comments=rsAppInfo.getString("Comments");
					if(comments=="null" )
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
				
				listApprers.add(apprvers);
				
			}
				//IT ENGINEER DETAIlS
				
				
				String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
				
					while(rsApprDetails1.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
						
						apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
						String empCode1=rsApprDetails1.getString("Approver_Id");
						
						String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
						if(rsAppInfo1.next())
						{
							
							String approveStatus=rsAppInfo1.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved"))
							{
								recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
								ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
								if(rsAppInfo2.next())
								{
									if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
									{
										apprvers.setApproveStatus("Closed");
									apprvers.setDate(rsAppInfo2.getString("approved_date"));
									
								
								
								String comments="";
								comments=rsAppInfo2.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo2.getString("Comments"));
								}
								}
								}
							}else{
								
								recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
								ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
								if(rsAppInfo3.next())
								{
									if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
									{
									apprvers.setDate(rsAppInfo3.getString("rejected_date"));
									}
								
								String comments="";
								comments=rsAppInfo3.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo3.getString("Comments"));
								}
								}
							}
							
						}
						apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
				
					listApprers.add(apprvers);
					}
				
				request.setAttribute("approverDetails", listApprers);
			
		} 
		
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
				
				forwardtype="viewNewITSparesform";
			
				
			}
					
				if(type.equalsIgnoreCase("New IT Asset Request"))
				{
		
					LinkedList Adusercreation=new LinkedList();
					String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					try {
						while(rs00.next())
						{
						if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
						{
							empno=rs00.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String locID="";
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setServer(rs1.getString("Server_Address"));
					help.setReqdfolderacces(rs1.getString("Req_Folder_access"));
					help.setReason(rs1.getString("Reason"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setAssetcategory(rs1.getString("Category"));
					help.setReqbydate(rs1.getString("reqbydate"));
					help.setSuggestmodelname(rs1.getString("suggmodel"));
					
					help.setGxpyes(rs1.getString("gxp"));
					
					help.setTypeofprinter(rs1.getString("typeofprinter"));
					help.setUsers(rs1.getString("noofusers"));
					help.setPrintvolpermonth(rs1.getString("printvolpm"));
					help.setSuggestmodelno(rs1.getString("suggmodelno"));
					help.setApprxvalue(rs1.getString("Apprxvalue"));
			
					help.setLanyes(rs1.getString("lan"));
					help.setAnysplreq(rs1.getString("anysplreq"));
					help.setPurpose(rs1.getString("purpose"));
                    locID=rs1.getString("Location");
			
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			//Approver Details
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			int jk=0;
			
			//HOD
		
			int hodpresent=0;
			int hodpresentinIt=0;
			String HODPresentId="";
			String HODPresentname="";
			String itengineerid="";
			String itengineername="";
			String HOD="";
			String	pendingApprovers="";
			
			 //chk if he has Hod
			 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
			 ResultSet rs10= ad.selectQuery(chkhod);
			 try {
				while(rs10.next())
				 {
					 hodpresent=rs10.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			//get hod details
			
		
			if(hodpresent>0){
			String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
					" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
			 ResultSet rs2= ad.selectQuery(gethod);
			 
			 try {
				if(rs2.next())
				 {
				
					HODPresentId=rs2.getString("ApproverId");
					HODPresentname=rs2.getString("EMP_FULLNAME");
				 }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
			
			//chk if he is present in It approver
			 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
			 ResultSet rs11= ad.selectQuery(chkhodinit);
			 try {
				while(rs11.next())
				 {
					hodpresentinIt=rs11.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			 
			 
		
			 
			 
					
			//Plant head
				String plantheadid="";
				String plantheadname="";
				String plantheaddep="";
				String plantheaddesg="";
				String a=locID;
				if(!a.equalsIgnoreCase("16"))
				{
					String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
					ResultSet pl=ad.selectQuery(plant);
					try {
						while(pl.next())
						 {
							plantheadid=pl.getString("pernr");
							plantheadname=pl.getString("EMP_FULLNAME");
							plantheaddep=pl.getString("DPTSTXT");
							plantheaddesg=pl.getString("DSGSTXT");
						 }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			 
				if(plantheadid.equalsIgnoreCase(""))
				{
					if(hodpresent>0 && hodpresentinIt==0 )
					{
						
						
						try {
							
								checkStatus=1;
								ApprovalsForm apprvershod=new ApprovalsForm();
								apprvershod.setPriority("1");
								
								apprvershod.setEmployeeCode(HODPresentId);
								apprvershod.setEmployeeName(HODPresentname);
								
								String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
							+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
							+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
							+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								if(rsAppInfo.next())
								{
									apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
									String approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
									{
										apprvershod.setDate(rsAppInfo.getString("approved_date"));
									}else{
										apprvershod.setDate(rsAppInfo.getString("rejected_date"));
									}
									String comments="";
									comments=rsAppInfo.getString("Comments");
									if(comments=="null" )
									{
										apprvershod.setComments("");
									}else{
										apprvershod.setComments(rsAppInfo.getString("Comments"));
									}
								}
								
								
								listApprers.add(apprvershod);
							 jk=1;
							}
						
						catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
					}
					
					
				}
				
			 
			 if(!plantheadid.equalsIgnoreCase(""))
				{
				 
										
					try {
						
							checkStatus=1;
							ApprovalsForm apprvershod=new ApprovalsForm();
							apprvershod.setPriority("1");
							
							apprvershod.setEmployeeCode(plantheadid);
							apprvershod.setEmployeeName(plantheadname);
							
							String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
						+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
						+ "all_r.Last_Approver='"+plantheadid+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
						+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									apprvershod.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvershod.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvershod.setComments("");
								}else{
									apprvershod.setComments(rsAppInfo.getString("Comments"));
								}
							}
							
							
							listApprers.add(apprvershod);
						 jk=1;
						}
					
					catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
				}
			
			

			
			
			
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					
						
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
							 chkdate1=rsAppInfo.getString("rejected_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
						{
							
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
					
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
								
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus("Closed");
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
										{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										}
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
					
					forwardtype="viewNewITAssetform";
				
					
				}
				
				
				if(type.equalsIgnoreCase("Active Directory User Creation"))
				{
					LinkedList Adusercreation=new LinkedList();
			String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					try {
						while(rs00.next())
						{
						if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
						{
							empno=rs00.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					String locID="";
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setServer(rs1.getString("Server_Address"));
					help.setReqdfolderacces(rs1.getString("Req_Folder_access"));
					help.setReason(rs1.getString("Reason"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setInitials(rs1.getString("Initials"));
					help.setFirstname(rs1.getString("Frst_Name"));
					help.setLastname(rs1.getString("Last_Name"));
					locID=rs1.getString("Location");
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			//Approver Details
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			int jk=0;
			
			//HOD
		
			int hodpresent=0;
			int hodpresentinIt=0;
			String HODPresentId="";
			String HODPresentname="";
			String itengineerid="";
			String itengineername="";
			String HOD="";
			String	pendingApprovers="";
			
			 //chk if he has Hod
			 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
			 ResultSet rs10= ad.selectQuery(chkhod);
			 try {
				while(rs10.next())
				 {
					 hodpresent=rs10.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			//get hod details
			
		
			if(hodpresent>0){
			String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
					" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
			 ResultSet rs2= ad.selectQuery(gethod);
			 
			 try {
				if(rs2.next())
				 {
				
					HODPresentId=rs2.getString("ApproverId");
					HODPresentname=rs2.getString("EMP_FULLNAME");
				 }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
			
			//chk if he is present in It approver
			 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
			 ResultSet rs11= ad.selectQuery(chkhodinit);
			 try {
				while(rs11.next())
				 {
					hodpresentinIt=rs11.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			 
				if(hodpresent>0 && hodpresentinIt==0 )
				{
					
					
					try {
						
							checkStatus=1;
							ApprovalsForm apprvershod=new ApprovalsForm();
							apprvershod.setPriority("1");
							
							apprvershod.setEmployeeCode(HODPresentId);
							apprvershod.setEmployeeName(HODPresentname);
							
							String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
						+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
						+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
						+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									apprvershod.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvershod.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvershod.setComments("");
								}else{
									apprvershod.setComments(rsAppInfo.getString("Comments"));
								}
							}
							
							
							listApprers.add(apprvershod);
						 jk=1;
						}
					
					catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
				}
			
			
		
			
			
			
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					
						
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
							 chkdate1=rsAppInfo.getString("rejected_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
						{
							
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
					
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
								
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus("Closed");
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
										{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										}
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
					
					forwardtype="viewAduserCreationform";
				}
				
				if(type.equalsIgnoreCase("Active Directory User Deletion"))
				{
					LinkedList AduserDeletion=new LinkedList();
			String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					try {
						while(rs00.next())
						{
						if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
						{
							empno=rs00.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
						
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							
							help.setEmpno(rs.getString("PERNR"));
						
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					String locID="";
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setServer(rs1.getString("Server_Address"));
					help.setReqdfolderdeletion(rs1.getString("Req_Folder_Deletion"));
					help.setDeletefrom(rs1.getString("Delete_From"));
					help.setReason(rs1.getString("Reason"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					locID=rs1.getString("Location");
					
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			//Approver Details
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				int jk=0;
				
				//HOD
		
				int hodpresent=0;
				int hodpresentinIt=0;
				String HODPresentId="";
				String HODPresentname="";
				String itengineerid="";
				String itengineername="";
				String HOD="";
				String	pendingApprovers="";
				
				 //chk if he has Hod
				 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
				 ResultSet rs10= ad.selectQuery(chkhod);
				 try {
					while(rs10.next())
					 {
						 hodpresent=rs10.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				//get hod details
				
		
				if(hodpresent>0){
				String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
						" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
				 ResultSet rs2= ad.selectQuery(gethod);
				 
				 try {
					if(rs2.next())
					 {
					
						HODPresentId=rs2.getString("ApproverId");
						HODPresentname=rs2.getString("EMP_FULLNAME");
					 }
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				}
				
				//chk if he is present in It approver
				 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
				 ResultSet rs11= ad.selectQuery(chkhodinit);
				 try {
					while(rs11.next())
					 {
						hodpresentinIt=rs11.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				 
					if(hodpresent>0 && hodpresentinIt==0 )
					{
						
						
						try {
							
								checkStatus=1;
								ApprovalsForm apprvershod=new ApprovalsForm();
								apprvershod.setPriority("1");
								
								apprvershod.setEmployeeCode(HODPresentId);
								apprvershod.setEmployeeName(HODPresentname);
								
								String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
							+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
							+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
							+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								if(rsAppInfo.next())
								{
									apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
									String approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
									{
										apprvershod.setDate(rsAppInfo.getString("approved_date"));
									}else{
										apprvershod.setDate(rsAppInfo.getString("rejected_date"));
									}
									String comments="";
									comments=rsAppInfo.getString("Comments");
									if(comments=="null" )
									{
										apprvershod.setComments("");
									}else{
										apprvershod.setComments(rsAppInfo.getString("Comments"));
									}
								}
								
								
								listApprers.add(apprvershod);
							 jk=1;
							}
						
						catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
					}
				
				
		
				
				
				
				String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						
							
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						if(jk==1){
							
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
						}
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						
						String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							if(rsAppInfo.getInt("Priority")==1 )
							{
								 chkdate=rsAppInfo.getString("approved_date");
								 chkdate1=rsAppInfo.getString("rejected_date");
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
							{
								
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						
						listApprers.add(apprvers);
						
					}
						//IT ENGINEER DETAIlS
						
						
						String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
						ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
						
							while(rsApprDetails1.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
								
								apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
								String empCode1=rsApprDetails1.getString("Approver_Id");
								
								String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
										+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
										+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
										+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
										+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
								if(rsAppInfo1.next())
								{
									
									String approveStatus=rsAppInfo1.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved"))
									{
										recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
										ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
										if(rsAppInfo2.next())
										{
											if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
											{
												apprvers.setApproveStatus("Closed");
											apprvers.setDate(rsAppInfo2.getString("approved_date"));
											
										
										
										String comments="";
										comments=rsAppInfo2.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo2.getString("Comments"));
										}
										}
										}
									}else{
										
										recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
										ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
										if(rsAppInfo3.next())
										{
											if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
											{
											apprvers.setDate(rsAppInfo3.getString("rejected_date"));
											}
										
										String comments="";
										comments=rsAppInfo3.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo3.getString("Comments"));
										}
										}
									}
									
								}
								apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
						
							listApprers.add(apprvers);
							}
						
						request.setAttribute("approverDetails", listApprers);
				}
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
					
					forwardtype="viewAduserDeletionform";
				}
				if(type.equalsIgnoreCase("Active Directory User Transfer"))
				{
		
					LinkedList Adusertransfer=new LinkedList();
			String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					try {
						while(rs00.next())
						{
						if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
						{
							empno=rs00.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					String locID="";
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setReason(rs1.getString("Reason"));
					help.setIPNumber(rs1.getString("IPAddress"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setFromlocationId(rs1.getString("From_Loc"));
					help.setTolocationId(rs1.getString("To_Loc"));
					help.setFromdepartment(rs1.getString("From_Dep"));
					help.setTodepartment(rs1.getString("To_Dep"));
					locID=rs1.getString("Location");
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			//Approver Details
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			int jk=0;
			
			//HOD
		
			int hodpresent=0;
			int hodpresentinIt=0;
			String HODPresentId="";
			String HODPresentname="";
			String itengineerid="";
			String itengineername="";
			String HOD="";
			String	pendingApprovers="";
			
			 //chk if he has Hod
			 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
			 ResultSet rs10= ad.selectQuery(chkhod);
			 try {
				while(rs10.next())
				 {
					 hodpresent=rs10.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			//get hod details
			
		
			if(hodpresent>0){
			String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
					" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
			 ResultSet rs2= ad.selectQuery(gethod);
			 
			 try {
				if(rs2.next())
				 {
				
					HODPresentId=rs2.getString("ApproverId");
					HODPresentname=rs2.getString("EMP_FULLNAME");
				 }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
			
			//chk if he is present in It approver
			 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
			 ResultSet rs11= ad.selectQuery(chkhodinit);
			 try {
				while(rs11.next())
				 {
					hodpresentinIt=rs11.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			 
				if(hodpresent>0 && hodpresentinIt==0 )
				{
					
					
					try {
						
							checkStatus=1;
							ApprovalsForm apprvershod=new ApprovalsForm();
							apprvershod.setPriority("1");
							
							apprvershod.setEmployeeCode(HODPresentId);
							apprvershod.setEmployeeName(HODPresentname);
							
							String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
						+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
						+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
						+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									apprvershod.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvershod.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvershod.setComments("");
								}else{
									apprvershod.setComments(rsAppInfo.getString("Comments"));
								}
							}
							
							
							listApprers.add(apprvershod);
						 jk=1;
						}
					
					catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
				}
			
			
		
			
			
			
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					
						
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
							 chkdate1=rsAppInfo.getString("rejected_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
						{
							
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
					
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
								
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus("Closed");
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
										{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										}
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
					
					forwardtype="viewAduserTransferform";
				
					
				}
				
				if(type.equalsIgnoreCase("Email ID Create Request"))
				{
					LinkedList LotusIDCreation=new LinkedList();
			String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					try {
						while(rs00.next())
						{
						if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
						{
							empno=rs00.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					String locID="";
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{
							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
							String x=rs1.getString("comm_outside");
							if(x.equalsIgnoreCase("Yes"))
							{
								help.setOutsidecomyes("Yes");
							}
							if(x.equalsIgnoreCase("No"))
							{
								help.setOutsidecomyes("No");
							}
							help.setIsHeHod(rs1.getString("Is_He_Hod"));
							help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
							help.setEnablewebAccess(rs1.getString("Enable_Web_access"));
							help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
							help.setFirstname(rs1.getString("Frst_Name"));
							help.setLastname(rs1.getString("Last_Name"));
							help.setMiddname(rs1.getString("Middle_Name"));
							help.setDepartment(rs1.getString("department"));
							help.setDesignation(rs1.getString("designation"));
							//newly_Joinied,joining_date,Total_Mailids
							String a=rs1.getString("newly_Joinied");
							if(a.equalsIgnoreCase("Yes"))
							{
								help.setNewlyJoinedYes("Yes");
							}
							if(a.equalsIgnoreCase("No"))
							{
								help.setNewlyJoinedYes("No");
							}
							help.setJoiningDate(rs1.getString("joining_date"));
							help.setTotalIDs(rs1.getString("Total_Mailids"));
							
							
							locID=rs1.getString("Location");
						}
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD
		
					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					
		
					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
						//Plant head
						String plantheadid="";
						String plantheadname="";
						String plantheaddep="";
						String plantheaddesg="";
						String a=locID;
						if(!a.equalsIgnoreCase("16"))
						{
							String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
							ResultSet pl=ad.selectQuery(plant);
							try {
								while(pl.next())
								 {
									plantheadid=pl.getString("pernr");
									plantheadname=pl.getString("EMP_FULLNAME");
									plantheaddep=pl.getString("DPTSTXT");
									plantheaddesg=pl.getString("DSGSTXT");
								 }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					 
						if(plantheadid.equalsIgnoreCase(""))
						{
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("1");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
									+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
									+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
									+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
							
							
						}
						
					 
					 if(!plantheadid.equalsIgnoreCase(""))
						{
						 
												
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(plantheadid);
									apprvershod.setEmployeeName(plantheadname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
								+ "all_r.Last_Approver='"+plantheadid+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					
		
					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					



					ArrayList desgList=new ArrayList();
					ArrayList desgLabelList=new ArrayList();
					ResultSet rs16 = ad.selectQuery("select dsgid," +
							"DSGLTXT from DESIGNATION order by DSGLTXT"); 
							try {
								while(rs16.next()) {
									desgList.add(rs16.getString("dsgid"));
									desgLabelList.add(rs16.getString("DSGLTXT"));
								}
								rs16.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								
								e.printStackTrace();
							}
							
							help.setDesgList(desgList);
							help.setDesgLabelList(desgLabelList);
			
			
							ArrayList deptList=new ArrayList();
							ArrayList deptLabelList=new ArrayList();
							ResultSet rs161 = ad.selectQuery("select dptid," +
									"DPTLTXT from DEPARTMENT order by DPTLTXT "); 
									try {
										while(rs161.next()) {
											deptList.add(rs161.getString("dptid"));
											deptLabelList.add(rs161.getString("DPTLTXT"));
										}
										rs16.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									help.setDeptList(deptList);
									help.setDeptLabelList(deptLabelList);
					
					
					request.setAttribute("LotusIDCreation", LotusIDCreation);
					forwardtype="viewLotusIDCreationform";
				}
				
				if(type.equalsIgnoreCase("Email ID Change Request"))
				{
					LinkedList LotusIDChange=new LinkedList();
			String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					try {
						while(rs00.next())
						{
						if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
						{
							empno=rs00.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					String locID="";
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{
							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setFromemailId(rs1.getString("From_EmailID"));
							String x=rs1.getString("comm_outside");
							if(x.equalsIgnoreCase("Yes"))
							{
								help.setOutsidecomyes("Yes");
							}
							if(x.equalsIgnoreCase("No"))
							{
								help.setOutsidecomyes("No");
							}
							help.setDeleteoldmails(rs1.getString("delete_old_email"));
							help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
							help.setIsHeHod(rs1.getString("Is_He_Hod"));
							help.setTransferoldmail(rs1.getString("Transfer_Old_Emails"));
							help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
							help.setEnablewebAccess(rs1.getString("Enable_Web_access"));
							help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
							String a=rs1.getString("newly_Joinied");
							if(a.equalsIgnoreCase("Yes"))
							{
								help.setNewlyJoinedYes("Yes");
							}
							if(a.equalsIgnoreCase("No"))
							{
								help.setNewlyJoinedYes("No");
							}
							help.setJoiningDate(rs1.getString("joining_date"));
							help.setTotalIDs(rs1.getString("Total_Mailids"));
							
							help.setFirstname(rs1.getString("Frst_Name"));
							help.setLastname(rs1.getString("Last_Name"));
							help.setMiddname(rs1.getString("Middle_Name")); 
							locID=rs1.getString("Location");
						}
						
						
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD
		
					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					
		
					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
					//Plant head
						String plantheadid="";
						String plantheadname="";
						String plantheaddep="";
						String plantheaddesg="";
						String a=locID;
						if(!a.equalsIgnoreCase("16"))
						{
							String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
							ResultSet pl=ad.selectQuery(plant);
							try {
								while(pl.next())
								 {
									plantheadid=pl.getString("pernr");
									plantheadname=pl.getString("EMP_FULLNAME");
									plantheaddep=pl.getString("DPTSTXT");
									plantheaddesg=pl.getString("DSGSTXT");
								 }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					 
						if(plantheadid.equalsIgnoreCase(""))
						{
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("1");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
									+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
									+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
									+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
							
							
						}
						
					 
					 if(!plantheadid.equalsIgnoreCase(""))
						{
							
							
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(plantheadid);
									apprvershod.setEmployeeName(plantheadname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
								+ "all_r.Last_Approver='"+plantheadid+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					
		
					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					request.setAttribute("LotusIDChange", LotusIDChange);
					forwardtype="viewLotusIDChangeform";
				}
				
				if(type.equalsIgnoreCase("Email ID Delete Request"))
				{
		
					LinkedList LotusIDDeletion=new LinkedList();
			String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					try {
						while(rs00.next())
						{
						if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
						{
							empno=rs00.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					String locID="";
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{
							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
					        help.setIsHeHod(rs1.getString("Is_He_Hod"));
							help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
							help.setEmailidDelete(rs1.getString("EmailID_Deleted"));
							help.setDeletefrom(rs1.getString("Delete_From"));
						    help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
							help.setMailIDPath(rs1.getString("path"));
							help.setDeletedDBYes(rs1.getString("deletedDB"));
							help.setForward_MailID(rs1.getString("forward_MailID"));
							locID=rs1.getString("Location");
						}
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD
		
					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					
		
					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
					//Plant head
						String plantheadid="";
						String plantheadname="";
						String plantheaddep="";
						String plantheaddesg="";
						String a=locID;
						if(!a.equalsIgnoreCase("16"))
						{
							String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
							ResultSet pl=ad.selectQuery(plant);
							try {
								while(pl.next())
								 {
									plantheadid=pl.getString("pernr");
									plantheadname=pl.getString("EMP_FULLNAME");
									plantheaddep=pl.getString("DPTSTXT");
									plantheaddesg=pl.getString("DSGSTXT");
								 }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						if(plantheadid.equalsIgnoreCase(""))
						{
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("1");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
									+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
									+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
									+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
							
							
						}
					 
					 
					 if(!plantheadid.equalsIgnoreCase(""))
						{
							
							
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(plantheadid);
									apprvershod.setEmployeeName(plantheadname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
								+ "all_r.Last_Approver='"+plantheadid+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					
		
					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					request.setAttribute("LotusIDDeletion", LotusIDDeletion);
					forwardtype="viewLotusIDDeletionform";
				
					
				}
				
				if(type.equalsIgnoreCase("Internet Access Request"))
				{
					LinkedList internet=new LinkedList();
			String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					try {
						while(rs00.next())
						{
						if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
						{
							empno=rs00.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
						
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					String locID="";
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{
							help.setAssetvalue(rs1.getString("Asset_Details"));
							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
							help.setIsHeHod(rs1.getString("Is_He_Hod"));
							locID=rs1.getString("Location");
						}
						
					} catch (SQLException e) {
						  e.printStackTrace();
						}
							
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD
		
					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					
		
					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
					//Plant head
						String plantheadid="";
						String plantheadname="";
						String plantheaddep="";
						String plantheaddesg="";
						String a=locID;
						if(!a.equalsIgnoreCase("16"))
						{
							String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
							ResultSet pl=ad.selectQuery(plant);
							try {
								while(pl.next())
								 {
									plantheadid=pl.getString("pernr");
									plantheadname=pl.getString("EMP_FULLNAME");
									plantheaddep=pl.getString("DPTSTXT");
									plantheaddesg=pl.getString("DSGSTXT");
								 }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					 
						if(plantheadid.equalsIgnoreCase(""))
						{
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("1");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
									+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
									+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
									+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
							
							
						}
						
					 
					 if(!plantheadid.equalsIgnoreCase(""))
						{
						 
												
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(plantheadid);
									apprvershod.setEmployeeName(plantheadname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
								+ "all_r.Last_Approver='"+plantheadid+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					
		
					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					request.setAttribute("Internet", internet);
					forwardtype="viewInternetAccessform";
				}
				
				if(type.equalsIgnoreCase("External Drives Access Request"))
				{
					LinkedList externaldrives=new LinkedList();
			String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					try {
						while(rs00.next())
						{
						if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
						{
							empno=rs00.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
						
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}	
					String locID="";
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{
							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setAssetvalue(rs1.getString("Asset_Details"));
							help.setAccesstype(rs1.getString("Access_type"));
							help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
						
							String device=rs1.getString("Device_type");
							if(device.equalsIgnoreCase("3"))
							{
								help.setCdrom("CD/DVD_ROM");
								help.setUsb("USB");
							}
							if(device.equalsIgnoreCase("1"))
							{
								help.setCdrom("CD/DVD_ROM");
								
							}
							if(device.equalsIgnoreCase("2"))
							{
								
								help.setUsb("USB");
							}
							locID=rs1.getString("Location");
							
						}
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD
		
					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					
		
					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
					//Plant head
						String plantheadid="";
						String plantheadname="";
						String plantheaddep="";
						String plantheaddesg="";
						String a=locID;
						if(!a.equalsIgnoreCase("16"))
						{
							String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
							ResultSet pl=ad.selectQuery(plant);
							try {
								while(pl.next())
								 {
									plantheadid=pl.getString("pernr");
									plantheadname=pl.getString("EMP_FULLNAME");
									plantheaddep=pl.getString("DPTSTXT");
									plantheaddesg=pl.getString("DSGSTXT");
								 }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					 
						if(plantheadid.equalsIgnoreCase(""))
						{
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("1");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
									+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
									+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
									+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
							
							
						}
						
					 
					 if(!plantheadid.equalsIgnoreCase(""))
						{
						 
												
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(plantheadid);
									apprvershod.setEmployeeName(plantheadname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
								+ "all_r.Last_Approver='"+plantheadid+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					
		
					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					request.setAttribute("externaldrives", externaldrives);
					forwardtype="viewexternaldrivesform";
				}
				
				if(type.equalsIgnoreCase("FTP Access Request"))
				{
					LinkedList ftp=new LinkedList();
			String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					try {
						while(rs00.next())
						{
						if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
						{
							empno=rs00.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
						
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					String locID="";
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1); 
					try {
						while(rs1.next())
						{
							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setAssetvalue(rs1.getString("Asset_Details"));
							help.setRequiredfoldername(rs1.getString("Req_Folder_access"));
							help.setServer(rs1.getString("Server_Address"));
							help.setAccesstype(rs1.getString("Access_type"));
							help.setFrmdurationofaccess(rs1.getString("FrmRequiredDurationofAccess"));
							help.setTodurationofaccess(rs1.getString("ToRequiredDurationofAccess"));
							help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
				
							help.setDurationofaccess(rs1.getString("RequiredDurationofAccess"));
							String fileusername=rs1.getString("UserName");
							 if(fileusername!=null)
								 help.setUsername(fileusername);
							 else
								 help.setUsername("");
							 
							   String filePwd=rs1.getString("Password");
							   if(filePwd!=null)
								   help.setPassword(filePwd);
							   else
								   help.setPassword("");
							   
							   locID=rs1.getString("Location");
						}
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD
		
					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					
		
					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
					//Plant head
						String plantheadid="";
						String plantheadname="";
						String plantheaddep="";
						String plantheaddesg="";
						String a=locID;
						if(!a.equalsIgnoreCase("16"))
						{
							String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
							ResultSet pl=ad.selectQuery(plant);
							try {
								while(pl.next())
								 {
									plantheadid=pl.getString("pernr");
									plantheadname=pl.getString("EMP_FULLNAME");
									plantheaddep=pl.getString("DPTSTXT");
									plantheaddesg=pl.getString("DSGSTXT");
								 }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					 
						if(plantheadid.equalsIgnoreCase(""))
						{
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("1");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
									+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
									+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
									+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
							
							
						}
						
					 
					 if(!plantheadid.equalsIgnoreCase(""))
						{
						 
												
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(plantheadid);
									apprvershod.setEmployeeName(plantheadname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
								+ "all_r.Last_Approver='"+plantheadid+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					
		
					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{  
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					request.setAttribute("ftp", ftp);
					forwardtype="viewFtpAccessform";
				}
				
				if(type.equalsIgnoreCase("File Server Access Request"))
				{
					LinkedList folder=new LinkedList();
			        String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					try {
						while(rs00.next())
						{
						if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
						{
							empno=rs00.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
						    help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String locID="";
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{
							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setAssetvalue(rs1.getString("Asset_Details"));
							help.setRequiredfoldername(rs1.getString("Req_Folder_access"));
							help.setServer(rs1.getString("Server_Address"));
							help.setAccesstype(rs1.getString("Access_type"));
							help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
							help.setFromlocationId(rs1.getString("From_Loc"));
							locID=rs1.getString("Location");
						}
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD
		
					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD=""; 
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try { 
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					
		
					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
					//Plant head
						String plantheadid="";
						String plantheadname="";
						String plantheaddep="";
						String plantheaddesg="";
						String a=locID;
						if(!a.equalsIgnoreCase("16"))
						{
							String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
							ResultSet pl=ad.selectQuery(plant);
							try {
								while(pl.next())
								 {
									plantheadid=pl.getString("pernr");
									plantheadname=pl.getString("EMP_FULLNAME");
									plantheaddep=pl.getString("DPTSTXT");
									plantheaddesg=pl.getString("DSGSTXT");
								 }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					 
						if(plantheadid.equalsIgnoreCase(""))
						{
							if(hodpresent>0 && hodpresentinIt==0 )
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("1");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										
										String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
									+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
									+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
									+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
							
							
						}
						
					 
					 if(!plantheadid.equalsIgnoreCase(""))
						{
						 
												
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(plantheadid);
									apprvershod.setEmployeeName(plantheadname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
								+ "all_r.Last_Approver='"+plantheadid+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					
		
					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					request.setAttribute("folder", folder);
					forwardtype="viewFolderAccessform";
				}
			}	
		if(issreqtype.equalsIgnoreCase("Sap issues"))
		{  LinkedList empList=new LinkedList();

		String empno="";
		
		String data00=" select * from IT_SAP_ISSUES where Req_No="+reqno+"";	
		ResultSet rs00= ad.selectQuery(data00);
		while(rs00.next())
		{
		if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
		{
			empno=rs00.getString("Emp_No");
		}
		else
		{
			empno=user.getEmployeeNo();
		}
		}
		
		String data="select Emp.EMP_FULLNAME ,Emp.PERNR,dep.DPTSTXT,des.DSGSTXT,emp.IP_PHONE,emp.TEL_EXTENS,emp.IP_PHONE,IT.Req_No,"
	            +" It.read_Status,It.Req_Priority,It.mode,It.Req_type,It.Sub_Category,It.AssignTo,loc.LOCATION_CODE,It.IPAddress,"
			    +" IT.Req_Status,It.Subject,It.Reason,it.*"
	            +" from IT_SAP_ISSUES It,emp_official_info emp ,DEPARTMENT as dep ,Location as loc ,"
	          +"DESIGNATION as des"
	           +" where IT.Emp_No =emp.PERNR and dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID and"
	          +" IT.Req_No = '"+reqno+"'";
		ResultSet rs= ad.selectQuery(data);
		try {
			while(rs.next())
			{   myReqForm.setEmpno(rs.getString("PERNR"));
		//	System.out.println(myReqForm.getEmpno());
			myReqForm.setRequestername(rs.getString("EMP_FULLNAME"));
			myReqForm.setRequesterdepartment(rs.getString("DPTSTXT"));
			myReqForm.setRequesterdesignation(rs.getString("DSGSTXT"));
			myReqForm.setExtno(rs.getString("TEL_EXTENS"));
			myReqForm.setIpPhoneno(rs.getString("IP_PHONE"));
			myReqForm.setLocation(rs.getString("LOCATION_CODE"));
			myReqForm.setIPNumber(rs.getString("IPAddress"));
			myReqForm.setRequestNo(reqno);
			myReqForm.setRequestStatus(rs.getString("req_status"));
			myReqForm.setPriority(rs.getString("Req_Priority"));
			myReqForm.setMode(rs.getString("mode"));
			myReqForm.setCategory(rs.getString("Req_type"));
			myReqForm.setCategory1(rs.getString("Req_type"));						
			myReqForm.setSubcategory(rs.getString("Sub_Category"));
			myReqForm.setSubcategory1(rs.getString("Sub_Category"));
			myReqForm.setTechniciansID(rs.getString("AssignTo"));
			myReqForm.setReason(rs.getString("reason"));
			myReqForm.setSubject(rs.getString("subject"));
			myReqForm.setRequestName(rs.getString("sap_request_type"));
			if(rs.getString("sap_request_type")!=null)
			{
				if(!rs.getString("sap_request_type").equalsIgnoreCase("Document Cancellation"))
				{
					String rol="select rol.role as role_manage from IT_SAP_ISSUES as sap,Role_SAP as rol where sap.ROLE=rol.id and sap.location=rol.location_id and sap.role='"+rs.getString("ROLE")+"' and  sap.Req_No='"+reqno+"'";
						
					 ResultSet role1=ad.selectQuery(rol);
						while(role1.next())
						{
							myReqForm.setRoleId(role1.getString("role_manage"));
						}
				}
				
			}
			
			if(rs.getString("Sub_Category")!=null)
			{
				if(rs.getString("Sub_Category").equalsIgnoreCase("Rename ID"))	
				{
					request.setAttribute("renameId", "renameId");
					myReqForm.setOldId(rs.getString("old_id"));
					myReqForm.setNewId(rs.getString("new_id"));
				}
				
			}
			if(rs.getString("sap_request_type")!=null)
			{
				if(rs.getString("sap_request_type").equalsIgnoreCase("SAP Roles Management"))	
				{
					cate="'ROLES'";
					request.setAttribute("role", "role");
				//	pendAppForm.setOldId(rsLeave.getString("old_id"));
				//	pendAppForm.setNewId(rsLeave.getString("new_id"));
				}
				if(rs.getString("sap_request_type").equalsIgnoreCase("SAP Authorization Management"))	
				{
					 cate="'AUTH'";
					request.setAttribute("role", "role");
				//	pendAppForm.setOldId(rsLeave.getString("old_id"));
				//	pendAppForm.setNewId(rsLeave.getString("new_id"));
				}
				if(rs.getString("sap_request_type").equalsIgnoreCase("SAP User Id Management"))	
				{
					 
					 cate="'PSAP','SSAP'";
					 request.setAttribute("role", "role");
				//	pendAppForm.setOldId(rsLeave.getString("old_id"));
				//	pendAppForm.setNewId(rsLeave.getString("new_id"));
				}
				if(rs.getString("sap_request_type").equalsIgnoreCase("Document Cancellation"))	
				{
					 
					 cate="'DC'";
					// request.setAttribute("role", "role");
				//	pendAppForm.setOldId(rsLeave.getString("old_id"));
				//	pendAppForm.setNewId(rsLeave.getString("new_id"));
				}
				}
			empList.add(myReqForm);
					}
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		request.setAttribute("empList", empList);
		String getReqestNumber="select MAX(Priority) as priority"
 		    	+" from Material_Approvers app,"
				+"emp_official_info as emp"
			+" where Material_Type='"+myReqForm.getCategory()+"' and " 
			+"Material_Sub_Category='"+myReqForm.getSubcategory()+"' and "    
			+"LOCATION='"+myReqForm.getLocation()+"'";
	int maxPriority=0;
	ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
	try {
		if(rsReqestNumber.next())
		{
			maxPriority=rsReqestNumber.getInt(1);
		}
	} catch (SQLException e) {
	
		e.printStackTrace();
	}
	//maxPriority+=1;
	
	
	int hodreq =0;
	
	String chkreq=" select c_hod_req from CAT_SUBCAT_MASTER where c_prefix = 'SAP' and c_cat_name = '"+myReqForm.getCategory()+"' and c_sub_cat_name ='"+myReqForm.getSubcategory()+"'";
	 ResultSet chk= ad.selectQuery(chkreq);
	 try {
		while(chk.next())
		 {
			 hodreq=chk.getInt(1);
		 }
	} catch (SQLException e1) {
		
		e1.printStackTrace();
	}
	 
	 String Sqlstring ="";
	 
	if(hodreq ==1)
	{	
	
	 Sqlstring ="select 0 as priority ,"  
	         +"PERNR as employeeCode ," 
	         +"EMP_FULLNAME as employeeName ,"
	         +"DEPARTMENT.DPTLTXT as department,"
	         +"DESIGNATION.DSGLTXT as designation,"
	         +"All_Request.Req_Status ,"
	         +"All_Request.Comments ,"
	         +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
	         +" from  DEPARTMENT,DESIGNATION ,ESS_Approvers ,emp_official_info "
			  +"left outer join All_Request on Req_type ='IT Sap Request' and "
             +"Req_Id = '"+reqno+"' and "
             +"emp_official_info.PERNR = All_Request.Last_Approver"
	         +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
	         +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
	         +"emp_official_info.PERNR =  ESS_Approvers.ApproverId and "
	        +"ESS_Approvers.employeeNumber = '"+myReqForm.getEmpno()+"'"
	        +" union " 
           +"select app.priority as priority,"
		    +"PERNR as employeeCode ," 
			+"EMP_FULLNAME as employeeName ,"
		    +"DEPARTMENT.DPTLTXT as department,"
			+"DESIGNATION.DSGLTXT as designation ,"
			+"All_Request.Req_Status ,"
	         +"All_Request.Comments ,"
	         +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
			+" from Material_Approvers app,"
				+" DEPARTMENT,DESIGNATION ,emp_official_info as emp  left outer join All_Request on Req_type ='IT Sap Request' and "
			 +"Req_Id = '"+reqno+"' and "
            +"emp.PERNR = All_Request.Last_Approver "
				+" where Material_Type='"+myReqForm.getCategory()+"' and " 
			+"Material_Sub_Category='"+myReqForm.getSubcategory()+"' and "  
			+"emp.PERNR=app.Approver_Id and " 
			+"LOCATION='"+myReqForm.getLocation()+"' and role ='USER' and "
			+"emp.DPTID  = DEPARTMENT.DPTID and "
			+"emp.DSGID  = DESIGNATION.DSGID" 
			+" Union "
			+"select '"+maxPriority+"' as priority ," 
	         +"PERNR as employeeCode ," 
	         +"EMP_FULLNAME as employeeName ,"
	         +"DEPARTMENT.DPTLTXT as department,"
	         +"DESIGNATION.DSGLTXT as designation ,"
	         +"All_Request.Req_Status ,"
	         +"All_Request.Comments ,"
	         +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
	         +" from DEPARTMENT,DESIGNATION ,emp_official_info "
			 +"Left Outer Join  All_Request on Req_type ='IT Sap Request' and" 
                      +" Req_Id = '"+reqno+"' and" 
                      +" emp_official_info.PERNR = All_Request.Last_Approver"   

	        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
	        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
	        +"emp_official_info.PERNR = '"+myReqForm.getTechniciansID()+"'" 
			+" Order by 1";
	}
	else
	{
		Sqlstring=  "select app.priority as priority,"
	    		    +"PERNR as employeeCode ," 
	    			+"EMP_FULLNAME as employeeName ,"
	    		    +"DEPARTMENT.DPTLTXT as department,"
	    			+"DESIGNATION.DSGLTXT as designation ,"
	    			+"All_Request.Req_Status ,"
			         +"All_Request.Comments ,"
			         +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
					+" from Material_Approvers app,"
	 				+" DEPARTMENT,DESIGNATION ,emp_official_info as emp  left outer join All_Request on Req_type ='IT Sap Request' and "
					 +"Req_Id = '"+reqno+"' and "
	                +"emp.PERNR = All_Request.Last_Approver "
						+" where Material_Type='"+myReqForm.getCategory()+"' and " 
	   			+"Material_Sub_Category='"+myReqForm.getSubcategory()+"' and "  
	   			+"emp.PERNR=app.Approver_Id and " 
					+"LOCATION='"+myReqForm.getLocation()+"' and role ='USER' and "
					+"emp.DPTID  = DEPARTMENT.DPTID and "
	   			+"emp.DSGID  = DESIGNATION.DSGID" 
					+" Union "
					+"select '"+maxPriority+"' as priority ," 
			         +"PERNR as employeeCode ," 
			         +"EMP_FULLNAME as employeeName ,"
			         +"DEPARTMENT.DPTLTXT as department,"
			         +"DESIGNATION.DSGLTXT as designation ,"
			         +"All_Request.Req_Status ,"
			         +"All_Request.Comments ,"
			         +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
			         +" from DEPARTMENT,DESIGNATION ,emp_official_info "
					 +"Left Outer Join  All_Request on Req_type ='IT Sap Request' and" 
	                          +" Req_Id = '"+reqno+"' and" 
	                          +" emp_official_info.PERNR = All_Request.Last_Approver"   
	  
			        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
			        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
			        +"emp_official_info.PERNR = '"+myReqForm.getTechniciansID()+"'" 
	   			+" Order by 1";
	}
	
    ResultSet rs10=ad.selectQuery(Sqlstring);
    int i =0;
    LinkedList apprList=new LinkedList();
	
	try{
		while(rs10.next()){
			//IssuesForm form1=new IssuesForm();
			MyRequestForm form1 = new MyRequestForm();
			i = i+1;
			//form1.setPriority(rs.getInt("priority"));
			form1.setPriority(Integer.toString(i));
			//pendAppForm.setEmployeeCode(rs.getString("employeeCode"));
			form1.setEmployeeCode(rs10.getString("employeeCode"));
			form1.setEmployeeName(rs10.getString("employeeName"));
			
			//form1.setEmployeename(rs.getString("employeeName"));
			//pendAppForm.setDepartment(rs.getString("department"));
			form1.setDepartment(rs10.getString("department"));
			//form1.setDesignation(rs.getString("designation"));
			form1.setDesignation(rs10.getString("designation"));
			form1.setStatus(rs10.getString("req_status"));
			form1.setComments(rs10.getString("Comments"));
			form1.setDate(rs10.getString("date"));
			
			apprList.add(form1);	
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	
	request.setAttribute("apprList", apprList);
	//file upload list

	 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
 	 Properties props = new Properties();
 	 try {
		props.load(in);
	
	 in.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/SapIssues/UploadFiles");
 	String filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/SapIssues/UploadFiles/"+cate+"";
 	 filepath2="/EMicro Files/IT/Help Desk/SapIssues/UploadFiles/"+cate+"";
	String sql1="select * from Issues_document where request_no='"+reqno+"' and user_id='"+empno+"'";
	ResultSet rs1=ad.selectQuery(sql1);
	IssuesForm leaveForm1=null;
		ArrayList a1=new ArrayList();
		try {
			while(rs1.next()) {
				leaveForm1=new IssuesForm();
				String start = rs1.getString("file_name").substring(rs1.getString("file_name").indexOf("_")+1, rs1.getString("file_name").length()-1);
				leaveForm1.setFileName(start);
				leaveForm1.setId(rs1.getString("id"));
				leaveForm1.setFileFullPath(filepath2+"/"+rs1.getString("file_name"));
				a1.add(leaveForm1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("requesterDetails", a1);
	//----
	
		forwardtype="viewNewSapRequestform";
	
	
}
		
		if(issreqtype.equalsIgnoreCase("It issues"))
		  {	

		
			IssueImpl impl=new IssueImpl();
			int reqNo=Integer.parseInt(request.getParameter("requstNo"));
			String empType="";
			//check emp type
			String checkEmp="select it.Emp_No,all_r.Technician_Id from IT_ISSUES as it,IT_All_ISSUES as all_r "
			+ "where Req_No='"+reqNo+"' and it.Req_No=all_r.Req_Id";
			ResultSet rs=ad.selectQuery(checkEmp);
			try{
			if(rs.next()){	
				if(user.getEmployeeNo().equals(rs.getString("Emp_No"))){
					empType="Requester";
					request.setAttribute("showReqReply", "showReqReply");
				}else if(user.getEmployeeNo().equals(rs.getString("Technician_Id"))){
					empType="Technician";
					request.setAttribute("showTechnReply", "showTechnReply");
				}else{
					empType="Other";
				}
				help.setEmpType(empType);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		    if(empType.equals("Other"))
		    	request.setAttribute("showBackButton", "showBackButton");
			
			getRequesterDetails(reqNo,form,request);
			getApprovalStatus(reqNo, form, request);
			getConversationList(reqNo, form, request);
			return mapping.findForward("pickupSelectedIssue");
		
		  }
		
		return mapping.findForward(forwardtype);
	  }
	public List getRequesterDetails(int reqNo,ActionForm form,HttpServletRequest request){
		//IssuesForm help = (IssuesForm) form;
	    HelpDeskForm help = (HelpDeskForm) form; 
		//IssuesForm help = new IssuesForm();
		List requseterDetails=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String getDetails="select emp.PERNR,emp.EMP_FULLNAME,emp.LOCID,emp.EMAIL_ID,it.Ext_No,it.Req_No,it.IP_Phone,"
		+ "it.IPAddress,it.Req_type,it.Req_Status,it.Req_Priority,it.Subject,it.Reason,it.Sub_Category,it.mode,dep.DPTSTXT,"
		+ "desg.DSGSTXT from IT_ISSUES as it,emp_official_info as emp,DEPARTMENT dep,DESIGNATION desg where it.Req_No='"+reqNo+"' and "
		+ "emp.PERNR=it.Emp_No and emp.DPTID=dep.DPTID and emp.DSGID=desg.DSGID";
		ResultSet rs=ad.selectQuery(getDetails);
		try{
			String requesterNo="";
		while(rs.next()){
			IssuesForm form1=new IssuesForm();
			form1.setEmployeeno(rs.getString("PERNR"));
			requesterNo=rs.getString("PERNR");
			form1.setEmployeename(rs.getString("EMP_FULLNAME"));
			form1.setLocation(rs.getString("LOCID"));
			form1.setEmpEmailID(rs.getString("EMAIL_ID"));
			form1.setExtno(rs.getString("Ext_No"));
			form1.setRequestNo(rs.getInt("Req_No"));
			help.setRequestNo(rs.getInt("Req_No"));
			form1.setIPNumber(rs.getString("IPAddress"));
			form1.setIpPhoneno(rs.getString("IP_Phone"));
			form1.setCategory(rs.getString("Req_type"));
			form1.setSubcategory(rs.getString("Sub_Category"));
			help.setCategory(rs.getString("Req_type"));
			help.setSubcategory(rs.getString("Sub_Category"));
			form1.setIssuePriority(rs.getString("Req_Priority"));
			help.setRequestStatus(rs.getString("Req_Status"));
			form1.setSubject(rs.getString("Subject"));
			form1.setReason(rs.getString("Reason"));
			form1.setMode(rs.getString("mode"));
			form1.setRequesterdepartment(rs.getString("DPTSTXT"));
			form1.setRequesterdesignation(rs.getString("DSGSTXT"));
			requseterDetails.add(form1);
		}
		ad.closeResultset();
		 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	 	 Properties props = new Properties();
	 	 props.load(in);
		 in.close();
	 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles");
	 	String filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
		String sql1="select * from Issues_document where request_no='"+reqNo+"' and user_id='"+requesterNo+"'";
		ResultSet rs1=ad.selectQuery(sql1);
		IssuesForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			while(rs1.next()) {
				leaveForm1=new IssuesForm();
				leaveForm1.setFileName(rs1.getString("file_name"));
				leaveForm1.setId(rs1.getString("id"));
				leaveForm1.setFileFullPath(filePath+"/"+rs1.getString("file_name"));
				a1.add(leaveForm1);
			}
			
			request.setAttribute("requesterDetails", a1);
		System.out.println("Emp Type="+help.getEmpType());
		
		if(help.getEmpType().equals("Technician") && help.getRequestStatus().equals("New")){
			String updateStatus="update IT_ISSUES set Req_Status='In Process' where Req_No='"+reqNo+"' and Req_Status='New' ";
			ad.SqlExecuteUpdate(updateStatus);
			 updateStatus="update IT_All_ISSUES set Req_Status='In Process',readStatus=1 where Req_Id='"+reqNo+"' and Req_Status='New' and "
			 		+ "Technician_Id='"+user.getEmployeeNo()+"'";
			ad.SqlExecuteUpdate(updateStatus);
			help.setRequestStatus("In Process");
		}if(help.getEmpType().equals("Technician")){
			String updateStatus="update IT_All_ISSUES set readStatus=1 where Req_Id='"+reqNo+"' ";
			ad.SqlExecuteUpdate(updateStatus);
		}
		if(help.getEmpType().equals("Requester")){
			String updateStatus="update IT_ISSUES set read_Status=1 where Req_No='"+reqNo+"' ";
			ad.SqlExecuteUpdate(updateStatus);
		}
		
		request.setAttribute("requseterDetails", requseterDetails);
		}catch(Exception e){
			e.printStackTrace();
		}
		return requseterDetails;
	}
	
	public List getApprovalStatus(int reqNo,ActionForm form,HttpServletRequest request){
		//IssuesForm help = (IssuesForm) form;
		
		List approvalDetails=new LinkedList();
		String getAppDet="select all_r.Technician_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.Comments,all_r.Sequence "
		+ "from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp where it.Req_No='"+reqNo+"' and it.Req_No=all_r.Req_Id and "
		+ "emp.PERNR=all_r.Technician_Id";
		ResultSet rs=ad.selectQuery(getAppDet);
		try{
		while(rs.next()){
			IssuesForm appDet=new IssuesForm();
			appDet.setTechnicianID(rs.getString("Technician_Id"));
			appDet.setEmployeename(rs.getString("EMP_FULLNAME"));
			String approveDate=rs.getString("approved_date");
			if(approveDate!=null){
			String a[]=approveDate.split(" "); 
			approveDate=a[0];
			String b[]=approveDate.split("-");
			approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
			}
			appDet.setApprovedDate(approveDate);
			appDet.setRequestStatus(rs.getString("Req_Status"));
			appDet.setComments(rs.getString("Comments"));
			approvalDetails.add(appDet);
			
		}
		request.setAttribute("approverDetails", approvalDetails);
		}catch(Exception e){
			e.printStackTrace();
		}
		return approvalDetails;
	}
	
	public List getConversationList(int reqNo,ActionForm form,HttpServletRequest request){
		List convList=new LinkedList();
		InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		try{
			Properties props = new Properties();
	 	 props.load(in);
		 in.close();
	 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles");
	 	String filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
		String getDetails="select emp.EMP_FULLNAME,it.Remarks,it.Transaction_Date,it.Sequence,it.Status from IT_Issue_Trans as it,emp_official_info"
		+ " as emp where it.Req_No='"+reqNo+"' and it.Emp_No=emp.PERNR ORDER BY it.Transaction_Date";
		ResultSet rs=ad.selectQuery(getDetails);
	
		while(rs.next()){
			IssuesForm convDet=new IssuesForm();
			convDet.setEmployeename(rs.getString("EMP_FULLNAME"));
			convDet.setComments(rs.getString("Remarks"));
			String approveDate=rs.getString("Transaction_Date");
			String a[]=approveDate.split(" ");
			approveDate=a[0];
			String b[]=approveDate.split("-");
			approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
			convDet.setReqDate(approveDate);
			convDet.setRequestStatus(rs.getString("Status"));
			int transNo=rs.getInt("Sequence");
			String getFiles="select * from Issues_solution_document where request_no='"+reqNo+"' and sequence_no='"+transNo+"'";
			ResultSet rsFiles=ad.selectQuery(getFiles);
			String fileName="";
			while(rsFiles.next()) {
				fileName=fileName+rsFiles.getString("file_name")+",";
			}
			System.out.println("fileName="+fileName);
			convDet.setFileName(fileName);
			convList.add(convDet);
		}
		if(convList.size()>0){
			request.setAttribute("convList", convList);
			
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return convList;
	}
	public ActionForward prev(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HelpDeskForm myReqForm = (HelpDeskForm) form;
		HttpSession session=request.getSession();

		UserInfo user=(UserInfo)session.getAttribute("user");
		
		int totalRecords=myReqForm.getTotalRecords();//21
		int endRecord=myReqForm.getStartRecord()-1;//20
		int startRecord=myReqForm.getStartRecord()-10;//11
		myReqForm.setChooseType(myReqForm.getSubfilter());
		
		getsubfilter(mapping, myReqForm, request, response);
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		
		
		myReqForm.setTotalRecords(totalRecords);
	
		
		
		request.setAttribute("displayRecordNo","ok");
		
	
		ArrayList listOfMyRequest=new ArrayList();
		String getITRecords="";
		
		
		if(myReqForm.getIssrequestype().equalsIgnoreCase("Helpdesk Request"))
			  
		  {
			  
		  
		  
			 String status="";
			  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
			   status ="select count(*) from IT_HELPDESK where Req_Status = '"+myReqForm.getSubfilter()+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";
			  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
				  status ="select count(*) from IT_HELPDESK where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') "; 
						 ResultSet rs10= ad.selectQuery(status);
			 try {
				while(rs10.next())
				 {
					totalRecords=rs10.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		
			 if(totalRecords>10)
			 {
			 myReqForm.setTotalRecords(totalRecords);
			 startRecord=1;
			 endRecord=10;
			 myReqForm.setStartRecord(1);
			 myReqForm.setEndRecord(10);
			  request.setAttribute("disablePreviousButton", "disablePreviousButton");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("nextButton", "nextButton");
			 }else
			 {
				  startRecord=1;
				  endRecord=totalRecords;
				  myReqForm.setTotalRecords(totalRecords);
				  myReqForm.setStartRecord(1);
				  myReqForm.setEndRecord(totalRecords); 
			 }
		
			 
			 if(totalRecords>0)
			 {
				 
					
					
					
				 if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
					{
		            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_HELPDESK as it where Req_Status='"+myReqForm.getSubfilter()+"' and   "
		            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";		            	 
					}
					if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
					{
						  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_HELPDESK as it where    "
			            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";		            	 
				
						
					}		ResultSet rs=ad.selectQuery(getITRecords);
						
						try {
							while(rs.next())
							{
								MyRequestForm help=new MyRequestForm();
							
								help.setItReqNo(rs.getInt("Req_No"));
								help.setItReqDate((EMicroUtils.display(rs.getDate("Req_date"))));
								help.setLastApprover(rs.getString("Last_approver"));
								help.setPendingApprover(rs.getString("Pending_approver"));
								help.setReqStatus(rs.getString("Req_Status"));
								help.setRequestType(rs.getString("Req_type"));
								help.setReqPriority(rs.getString("Req_Priority"));
								help.setItEngStatus(rs.getString("ITEng_Status"));
								
								listOfMyRequest.add(help);	
									
							
							}
							
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(listOfMyRequest.size()>0)
						request.setAttribute("it", listOfMyRequest);
						
						
						
						
			 }
			 if(listOfMyRequest.size()==0)
				{
					request.setAttribute("noit", "noit");	
				}
			
			}
if(myReqForm.getIssrequestype().equalsIgnoreCase("Sap issues"))
		  {
			  
		
			  
	 String status="";		  
	  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
		   status ="select count(*) from IT_SAP_ISSUES where Req_Status = '"+myReqForm.getSubfilter()+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";
		  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
			  status ="select count(*) from IT_SAP_ISSUES where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";  
		 				 ResultSet rs10= ad.selectQuery(status);
					 try {
					while(rs10.next())
					 {
						totalRecords=rs10.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
	
			
			 
			 
			 if(totalRecords>0)
			 {
				 
				
					
					
				 if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
				  {
	            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_SAP_ISSUES as it where Req_Status='"+myReqForm.getSubfilter()+"' and "
	            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";		            	 
				  }
				  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
				  {
	            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_SAP_ISSUES as it where  "
		            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";		            	 
					  
					  
				  }
				  ResultSet rs=ad.selectQuery(getITRecords);
						
						try {
							while(rs.next())
							{
								MyRequestForm help=new MyRequestForm();
							
								help.setItReqNo(rs.getInt("Req_No"));
								help.setItReqDate((EMicroUtils.display(rs.getDate("Req_date"))));
								help.setLastApprover(rs.getString("Last_approver"));
								help.setPendingApprover(rs.getString("Pending_approver"));
								help.setReqStatus(rs.getString("Req_Status"));
								help.setRequestType(rs.getString("Req_type"));
								help.setReqPriority(rs.getString("Req_Priority"));
								help.setItEngStatus(rs.getString("ITEng_Status"));
								
								listOfMyRequest.add(help);	
									
							
							}
							
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(listOfMyRequest.size()>0)
						request.setAttribute("sapit", listOfMyRequest);
						
						
						
						
						
			 }
			 if(listOfMyRequest.size()==0)
				{
					request.setAttribute("noit", "noit");	
				}
		
			}
			  
		  
if(myReqForm.getIssrequestype().equalsIgnoreCase("It issues"))
			  {
				  
		
			  
	 String status="";
	  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
	   status ="select count(*) from IT_ISSUES where Req_Status = '"+myReqForm.getSubfilter()+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";
	  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))			  
		  status ="select count(*) from IT_ISSUES where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";  
						 ResultSet rs10= ad.selectQuery(status);
				 try {
					while(rs10.next())
					 {
						totalRecords=rs10.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			
				
				
				 
				 if(totalRecords>0)
				 {
					 
			
						
					 if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))	
						{
			            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,"
			          				+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status from IT_ISSUES as it,emp_official_info as emp "
			          				+ "where  (it.Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') and emp.PERNR=it.Emp_No and  Req_Status='"+myReqForm.getSubfilter()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";
						}
						
						if(myReqForm.getSubfilter().equalsIgnoreCase("All"))	
						{
			            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,"
			          				+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status from IT_ISSUES as it,emp_official_info as emp "
			          				+ "where  (it.Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') and emp.PERNR=it.Emp_No ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";
						}  
			            	  ResultSet rs=ad.selectQuery(getITRecords);
							
							try {
								while(rs.next())
								{
									IssuesForm form1=new IssuesForm();
									form1.setRequestNo(rs.getInt("Req_No"));
									form1.setLocation(rs.getString("LOCID"));
									form1.setEmployeename(rs.getString("EMP_FULLNAME"));
									form1.setCategory(rs.getString("Req_Type"));
									form1.setSubcategory(rs.getString("Sub_Category"));
									form1.setSubject(rs.getString("Subject"));
									String ReqDate=rs.getString("Req_date");
									String a[]=ReqDate.split(" "); 
									ReqDate=a[0];
									String b[]=ReqDate.split("-");
									ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									form1.setReqDate(ReqDate);
									form1.setRequestStatus(rs.getString("Req_Status"));
									form1.setReqPriority(rs.getString("Req_Priority"));
									String assignTo=rs.getString("AssignTo");
									String techNames="";
									String getAssignTechnName="select EMP_FULLNAME from emp_official_info where PERNR in ("+assignTo+") order by PERNR desc";
									ResultSet rsTechns=ad.selectQuery(getAssignTechnName);
									while(rsTechns.next()){
										techNames=techNames+rsTechns.getString("EMP_FULLNAME")+" , ";
									}
									if(!(techNames.equalsIgnoreCase(""))){
										techNames=techNames.substring(0, (techNames.length()-2));	
									}
									form1.setAssignTo(techNames);
									
									form1.setSlaStatus(rs.getString("SLA_Status"));
								
									
									listOfMyRequest.add(form1);	
										
								
								}
								
								
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(listOfMyRequest.size()>0)
							request.setAttribute("ithelp", listOfMyRequest);
							
							
							
							
				 }
				 if(listOfMyRequest.size()==0)
					{
						request.setAttribute("noit", "noit");	
					}

			  
		  }
		  
			 
	

			
			
			
			if(listOfMyRequest.size()==0)
			{
				request.setAttribute("noit", "noit");	
			}
			
			request.setAttribute("displayRecordNo", "displayRecordNo");
			myReqForm.setTotalRecords(totalRecords);
			myReqForm.setStartRecord(startRecord);
			myReqForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				if(startRecord!=1)
				request.setAttribute("previousButton", "previousButton");
				request.setAttribute("displayRecordNo",    "displayRecordNo");
				if(listOfMyRequest.size()<10)
				{
					myReqForm.setStartRecord(1);
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
		
		
		return mapping.findForward("myrequest");
	}
	
	public ActionForward last(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		HelpDeskForm myReqForm = (HelpDeskForm) form;
		HttpSession session=request.getSession();

		UserInfo user=(UserInfo)session.getAttribute("user");
		
		int totalRecords=myReqForm.getTotalRecords();//21
		int endRecord=myReqForm.getStartRecord()-1;//20
		int startRecord=myReqForm.getStartRecord()-10;//11
		
		startRecord=totalRecords-9;
		 endRecord=totalRecords;
		 myReqForm.setTotalRecords(totalRecords);
		 myReqForm.setStartRecord(startRecord);
		 myReqForm.setEndRecord(totalRecords);
		 myReqForm.setChooseType(myReqForm.getSubfilter());
		 
		 
		 
		 ArrayList listOfMyRequest=new ArrayList();
			String getITRecords="";
			
			
			if(myReqForm.getIssrequestype().equalsIgnoreCase("Helpdesk Request"))
				  
			  {
				  
			  
			  
				 String status="";
				  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
				   status ="select count(*) from IT_HELPDESK where Req_Status = '"+myReqForm.getSubfilter()+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";
				  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
					  status ="select count(*) from IT_HELPDESK where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') "; 
								 ResultSet rs10= ad.selectQuery(status);
				 try {
					while(rs10.next())
					 {
						totalRecords=rs10.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			
				 
			
				 
				 if(totalRecords>0)
				 {
					 
						
						
						
					 if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
						{
			            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_HELPDESK as it where Req_Status='"+myReqForm.getSubfilter()+"' and   "
			            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";		            	 
						}
						if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
						{
							  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_HELPDESK as it where    "
				            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";		            	 
					
							
						}
						ResultSet rs=ad.selectQuery(getITRecords);
							
							try {
								while(rs.next())
								{
									MyRequestForm help=new MyRequestForm();
								
									help.setItReqNo(rs.getInt("Req_No"));
									help.setItReqDate((EMicroUtils.display(rs.getDate("Req_date"))));
									help.setLastApprover(rs.getString("Last_approver"));
									help.setPendingApprover(rs.getString("Pending_approver"));
									help.setReqStatus(rs.getString("Req_Status"));
									help.setRequestType(rs.getString("Req_type"));
									help.setReqPriority(rs.getString("Req_Priority"));
									help.setItEngStatus(rs.getString("ITEng_Status"));
									
									listOfMyRequest.add(help);	
										
								
								}
								
								
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(listOfMyRequest.size()>0)
							request.setAttribute("it", listOfMyRequest);
							
							
							
							
				 }
				 if(listOfMyRequest.size()==0)
					{
						request.setAttribute("noit", "noit");	
					}
				
				}
	if(myReqForm.getIssrequestype().equalsIgnoreCase("Sap issues"))
			  {
				  
			
				  
		String status="";		  
		  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
			   status ="select count(*) from IT_SAP_ISSUES where Req_Status = '"+myReqForm.getSubfilter()+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";
			  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
				  status ="select count(*) from IT_SAP_ISSUES where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";  
			 						 ResultSet rs10= ad.selectQuery(status);
						 try {
						while(rs10.next())
						 {
							totalRecords=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
		
				
				 
				 
				 if(totalRecords>0)
				 {
					 
					 if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
					  {
		            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_SAP_ISSUES as it where Req_Status='"+myReqForm.getSubfilter()+"' and "
		            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";		            	 
					  }
					  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
					  {
		            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_SAP_ISSUES as it where  "
			            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";		            	 
						  
						  
					  }
					  ResultSet rs=ad.selectQuery(getITRecords);
							
							try {
								while(rs.next())
								{
									MyRequestForm help=new MyRequestForm();
								
									help.setItReqNo(rs.getInt("Req_No"));
									help.setItReqDate((EMicroUtils.display(rs.getDate("Req_date"))));
									help.setLastApprover(rs.getString("Last_approver"));
									help.setPendingApprover(rs.getString("Pending_approver"));
									help.setReqStatus(rs.getString("Req_Status"));
									help.setRequestType(rs.getString("Req_type"));
									help.setReqPriority(rs.getString("Req_Priority"));
									help.setItEngStatus(rs.getString("ITEng_Status"));
									
									listOfMyRequest.add(help);	
										
								
								}
								
								
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(listOfMyRequest.size()>0)
							request.setAttribute("sapit", listOfMyRequest);
							
							
							
							
							
				 }
				 if(listOfMyRequest.size()==0)
					{
						request.setAttribute("noit", "noit");	
					}
			
				}
				  
			  
	if(myReqForm.getIssrequestype().equalsIgnoreCase("It issues"))
				  {
					  
			
				  
		 String status="";
		  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
		   status ="select count(*) from IT_ISSUES where Req_Status = '"+myReqForm.getSubfilter()+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";
		  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))			  
			  status ="select count(*) from IT_ISSUES where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";  
							 ResultSet rs10= ad.selectQuery(status);
					 try {
						while(rs10.next())
						 {
							totalRecords=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
				
					
					
					 
					 if(totalRecords>0)
					 {
						 
				
							
						 if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))	
							{
				            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,"
				          				+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status from IT_ISSUES as it,emp_official_info as emp "
				          				+ "where  (it.Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') and emp.PERNR=it.Emp_No and  Req_Status='"+myReqForm.getSubfilter()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";
							}
							
							if(myReqForm.getSubfilter().equalsIgnoreCase("All"))	
							{
				            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,"
				          				+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status from IT_ISSUES as it,emp_official_info as emp "
				          				+ "where  (it.Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') and emp.PERNR=it.Emp_No ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";
							}  
				            	  ResultSet rs=ad.selectQuery(getITRecords);
								
								try {
									while(rs.next())
									{
										IssuesForm form1=new IssuesForm();
										form1.setRequestNo(rs.getInt("Req_No"));
										form1.setLocation(rs.getString("LOCID"));
										form1.setEmployeename(rs.getString("EMP_FULLNAME"));
										form1.setCategory(rs.getString("Req_Type"));
										form1.setSubcategory(rs.getString("Sub_Category"));
										form1.setSubject(rs.getString("Subject"));
										String ReqDate=rs.getString("Req_date");
										String a[]=ReqDate.split(" "); 
										ReqDate=a[0];
										String b[]=ReqDate.split("-");
										ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
										form1.setReqDate(ReqDate);
										form1.setRequestStatus(rs.getString("Req_Status"));
										form1.setReqPriority(rs.getString("Req_Priority"));
										String assignTo=rs.getString("AssignTo");
										String techNames="";
										String getAssignTechnName="select EMP_FULLNAME from emp_official_info where PERNR in ("+assignTo+") order by PERNR desc";
										ResultSet rsTechns=ad.selectQuery(getAssignTechnName);
										while(rsTechns.next()){
											techNames=techNames+rsTechns.getString("EMP_FULLNAME")+" , ";
										}
										if(!(techNames.equalsIgnoreCase(""))){
											techNames=techNames.substring(0, (techNames.length()-2));	
										}
										form1.setAssignTo(techNames);
										
										form1.setSlaStatus(rs.getString("SLA_Status"));
									
										
										listOfMyRequest.add(form1);	
											
									
									}
									
									
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(listOfMyRequest.size()>0)
								request.setAttribute("ithelp", listOfMyRequest);
								
								
								
								
					 }
					 if(listOfMyRequest.size()==0)
						{
							request.setAttribute("noit", "noit");	
						}

				  
			  }
			  
				 
		

				
				
	
				if(listOfMyRequest.size()==0)
				{
					request.setAttribute("noit", "noit");	
				}
				request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton");
				if(listOfMyRequest.size()<10)
				{
					
					request.setAttribute("previousButton", "previousButton");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
				request.setAttribute("displayRecordNo", "displayRecordNo");
		
		
		return mapping.findForward("myrequest");
	}
	
	
	public ActionForward next(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HelpDeskForm myReqForm = (HelpDeskForm) form;
		
		String type=myReqForm.getIssrequestype();
		myReqForm.setIssrequestype(type);
		String  filter =myReqForm.getSubfilter();
		myReqForm.setSubfilter(filter);
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		myReqForm.setChooseType(myReqForm.getSubfilter());
		
	
			request.setAttribute("displayButton", "displayButton");
			
		try{
			int totalRecords=myReqForm.getTotalRecords();//21
			int startRecord=myReqForm.getStartRecord();//11
			int endRecord=myReqForm.getEndRecord();	
			
			
			
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
			}
			

			 ArrayList listOfMyRequest=new ArrayList();
				String getITRecords="";
				
				
				if(myReqForm.getIssrequestype().equalsIgnoreCase("Helpdesk Request"))
					  
				  {
					  			  
				  
					 String status="";
					  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
					   status ="select count(*) from IT_HELPDESK where Req_Status = '"+myReqForm.getSubfilter()+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";
					  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
						  status ="select count(*) from IT_HELPDESK where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') "; 
					 ResultSet rs10= ad.selectQuery(status);
					 try {
						while(rs10.next())
						 {
							totalRecords=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
				
					 
				
					 
					 if(totalRecords>0)
					 {
						 
							
							
						 if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
							{
				            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_HELPDESK as it where Req_Status='"+myReqForm.getSubfilter()+"' and   "
				            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";		            	 
							}
							if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
							{
								  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_HELPDESK as it where    "
					            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";		            	 
						
								
							}	ResultSet rs=ad.selectQuery(getITRecords);
								
								try {
									while(rs.next())
									{
										MyRequestForm help=new MyRequestForm();
									
										help.setItReqNo(rs.getInt("Req_No"));
										help.setItReqDate((EMicroUtils.display(rs.getDate("Req_date"))));
										help.setLastApprover(rs.getString("Last_approver"));
										help.setPendingApprover(rs.getString("Pending_approver"));
										help.setReqStatus(rs.getString("Req_Status"));
										help.setRequestType(rs.getString("Req_type"));
										help.setReqPriority(rs.getString("Req_Priority"));
										help.setItEngStatus(rs.getString("ITEng_Status"));
										
										listOfMyRequest.add(help);	
											
									
									}
									
									
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(listOfMyRequest.size()>0)
								request.setAttribute("it", listOfMyRequest);
								
								
								
								
					 }
					 if(listOfMyRequest.size()==0)
						{
							request.setAttribute("noit", "noit");	
						}
					
					}
		if(myReqForm.getIssrequestype().equalsIgnoreCase("Sap issues"))
				  {
					  
				
			 String status="";		  
			  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
				   status ="select count(*) from IT_SAP_ISSUES where Req_Status = '"+myReqForm.getSubfilter()+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";
				  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
					  status ="select count(*) from IT_SAP_ISSUES where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";  
				 						 ResultSet rs10= ad.selectQuery(status);
							 try {
							while(rs10.next())
							 {
								totalRecords=rs10.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
			
					
					 
					 
					 if(totalRecords>0)
					 {
						 
						
							
							
						 if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
						  {
			            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_SAP_ISSUES as it where Req_Status='"+myReqForm.getSubfilter()+"' and "
			            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";		            	 
						  }
						  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
						  {
			            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_SAP_ISSUES as it where  "
				            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";		            	 
							  
							  
						  }
						  
						  ResultSet rs=ad.selectQuery(getITRecords);
								
								try {
									while(rs.next())
									{
										MyRequestForm help=new MyRequestForm();
									
										help.setItReqNo(rs.getInt("Req_No"));
										help.setItReqDate((EMicroUtils.display(rs.getDate("Req_date"))));
										help.setLastApprover(rs.getString("Last_approver"));
										help.setPendingApprover(rs.getString("Pending_approver"));
										help.setReqStatus(rs.getString("Req_Status"));
										help.setRequestType(rs.getString("Req_type"));
										help.setReqPriority(rs.getString("Req_Priority"));
										help.setItEngStatus(rs.getString("ITEng_Status"));
										
										listOfMyRequest.add(help);	
											
									
									}
									
									
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(listOfMyRequest.size()>0)
								request.setAttribute("sapit", listOfMyRequest);
								
								
								
								
								
					 }
					 if(listOfMyRequest.size()==0)
						{
							request.setAttribute("noit", "noit");	
						}
				
					}
					  
				  
		if(myReqForm.getIssrequestype().equalsIgnoreCase("It issues"))
					  {
						  
				
					  
			 String status="";
			  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
			   status ="select count(*) from IT_ISSUES where Req_Status = '"+myReqForm.getSubfilter()+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";
			  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))			  
				  status ="select count(*) from IT_ISSUES where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";  
						 ResultSet rs10= ad.selectQuery(status);
						 try {
							while(rs10.next())
							 {
								totalRecords=rs10.getInt(1);
							 }
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
					
						
						
						 
						 if(totalRecords>0)
						 {
							 
					
								
							 if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))	
								{
					            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,"
					          				+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status from IT_ISSUES as it,emp_official_info as emp "
					          				+ "where  (it.Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') and emp.PERNR=it.Emp_No and  Req_Status='"+myReqForm.getSubfilter()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";
								}
								
								if(myReqForm.getSubfilter().equalsIgnoreCase("All"))	
								{
					            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,"
					          				+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status from IT_ISSUES as it,emp_official_info as emp "
					          				+ "where  (it.Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') and emp.PERNR=it.Emp_No ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_No desc";
								}
									  ResultSet rs=ad.selectQuery(getITRecords);
									
									try {
										while(rs.next())
										{
											IssuesForm form1=new IssuesForm();
											form1.setRequestNo(rs.getInt("Req_No"));
											form1.setLocation(rs.getString("LOCID"));
											form1.setEmployeename(rs.getString("EMP_FULLNAME"));
											form1.setCategory(rs.getString("Req_Type"));
											form1.setSubcategory(rs.getString("Sub_Category"));
											form1.setSubject(rs.getString("Subject"));
											String ReqDate=rs.getString("Req_date");
											String a[]=ReqDate.split(" "); 
											ReqDate=a[0];
											String b[]=ReqDate.split("-");
											ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
											form1.setReqDate(ReqDate);
											form1.setRequestStatus(rs.getString("Req_Status"));
											form1.setReqPriority(rs.getString("Req_Priority"));
											String assignTo=rs.getString("AssignTo");
											String techNames="";
											String getAssignTechnName="select EMP_FULLNAME from emp_official_info where PERNR in ("+assignTo+") order by PERNR desc";
											ResultSet rsTechns=ad.selectQuery(getAssignTechnName);
											while(rsTechns.next()){
												techNames=techNames+rsTechns.getString("EMP_FULLNAME")+" , ";
											}
											if(!(techNames.equalsIgnoreCase(""))){
												techNames=techNames.substring(0, (techNames.length()-2));	
											}
											form1.setAssignTo(techNames);
											
											form1.setSlaStatus(rs.getString("SLA_Status"));
										
											
											listOfMyRequest.add(form1);	
												
										
										}
										
										
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									if(listOfMyRequest.size()>0)
									request.setAttribute("ithelp", listOfMyRequest);
									
									
									
									
						 }
						 if(listOfMyRequest.size()==0)
							{
								request.setAttribute("noit", "noit");	
							}

					  
				  }
				  
					 
			

					
					

					
					if(listOfMyRequest.size()==0)
					{
						request.setAttribute("noit", "noit");	
					}
					
					
					if(listOfMyRequest.size()!=0)
					{
						myReqForm.setTotalRecords(totalRecords);
						myReqForm.setStartRecord(startRecord);
						myReqForm.setEndRecord(endRecord);
						request.setAttribute("nextButton", "nextButton");
						request.setAttribute("previousButton", "previousButton");
					}
					else
					{
						int start=startRecord;
						int end=startRecord;
						
						myReqForm.setTotalRecords(totalRecords);
						myReqForm.setStartRecord(start);
						myReqForm.setEndRecord(end);
						
					}
				 if(listOfMyRequest.size()<10)
				 {
					 myReqForm.setTotalRecords(totalRecords);
					 myReqForm.setStartRecord(startRecord);
					 myReqForm.setEndRecord(startRecord+listOfMyRequest.size()-1);
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
				
		
			 
		 
	  
		return mapping.findForward("myrequest");
	}
	
	
	public int getCountForTable(String empno , String type ){
    	int rowCount = 0;
    	MainDao exeQry = new MainDao();
    	String reqList ="";
    	if(type.equalsIgnoreCase("Sap issues"))
    	{	
    	 reqList="select count(*) from IT_ISSUES where Emp_No='"+empno+"' ";
    	}
    	if(type.equalsIgnoreCase("Helpdesk Request"))
    	{	
    	 reqList="select count(*) from IT_HELPDESK where Emp_No='"+empno+"' ";
    	}
    	if(type.equalsIgnoreCase("It issues"))
    	{	
    	 reqList="select count(*) from IT_SAP_ISSUES where Emp_No='"+empno+"' ";
    	}
    	
    	
    	try
    	{
    		
    		ResultSet countrs=exeQry.selectQuery(reqList);
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
	
	
	public ActionForward displaynewmyrequestform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		HelpDeskForm help = (HelpDeskForm) form;
		help.setRequestType(help.getRequestType());

		help.setEmployeeno("");
		

		
		return mapping.findForward("myrequest");
	}
	public ActionForward getsubfilter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HelpDeskForm help = (HelpDeskForm) form;
		String linkName = request.getParameter("linkName");		
		try {
			
			ArrayList subfilter = new ArrayList();
			if(linkName.equalsIgnoreCase("It issues"))
			{
				subfilter.add("New");
				subfilter.add("ON_Behalf");
				subfilter.add("ON HOLD");			
				subfilter.add("User Action");
				subfilter.add("Forwarded");
				subfilter.add("Completed");			
				subfilter.add("In Process");
				subfilter.add("All");
							
			}
			
			if(linkName.equalsIgnoreCase("Helpdesk Request"))
			{
				subfilter.add("Approved");
				subfilter.add("Completed");
				subfilter.add("In Process");
				subfilter.add("Rejected");
				subfilter.add("All");
			}			
			
			if(linkName.equalsIgnoreCase("Sap issues"))
			{
				subfilter.add("Approved");
				subfilter.add("Completed");
				subfilter.add("In Process");
				subfilter.add("Pending");
				subfilter.add("Rejected");
				subfilter.add("All");
			}
			
				
			
		
			//help.setSubfilterList(subfilter);
			
			help.setSubfilter(linkName);
			help.setUsage(help.getUsage());
			help.setEmployeeno("");
			request.setAttribute("subfilterlist", subfilter);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//displayMainLinks(mapping, form, request, response);
		return mapping.findForward("subfilterAjax");
	
	}
	public ActionForward displayMyRequestList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HelpDeskForm myReqForm = (HelpDeskForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		NewsandMediaDao ad=new NewsandMediaDao();
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		  
		  System.out.println(myReqForm.getIssrequestype());
		  System.out.println(myReqForm.getSubfilter());
		  
		  String subfiltertype = myReqForm.getSubfilter();
		  
		  
		  
if(myReqForm.getIssrequestype().equalsIgnoreCase("Helpdesk Request"))
			  
		  {
			  
		  int  totalRecords = 0;
		  String status="";
		  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
		   status ="select count(*) from IT_HELPDESK where Req_Status = '"+myReqForm.getSubfilter()+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";
		  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
			  status ="select count(*) from IT_HELPDESK where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";  
			 ResultSet rs10= ad.selectQuery(status);
			 try {
				while(rs10.next())
				 {
					totalRecords=rs10.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		  int  startRecord=0;
		  int  endRecord=0;
			 if(totalRecords>10)
			 {
			 myReqForm.setTotalRecords(totalRecords);
			 startRecord=1;
			 endRecord=10;
			 myReqForm.setStartRecord(1);
			 myReqForm.setEndRecord(10);
			  request.setAttribute("disablePreviousButton", "disablePreviousButton");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("nextButton", "nextButton");
			 }else
			 {
				  startRecord=1;
				  endRecord=totalRecords;
				  myReqForm.setTotalRecords(totalRecords);
				  myReqForm.setStartRecord(1);
				  myReqForm.setEndRecord(totalRecords); 
			 }
			 ArrayList listOfMyRequest=new ArrayList();
			 
			 if(totalRecords>0)
			 {
				 
					String getITRecords="";
					
					if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
					{
		            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_HELPDESK as it where Req_Status='"+myReqForm.getSubfilter()+"' and   "
		            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between 1 and 10 order by Req_No desc";		            	 
					}
					if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
					{
						  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_HELPDESK as it where    "
			            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between 1 and 10 order by Req_No desc";		            	 
				
						
					}
		            	  
		            	  ResultSet rs=ad.selectQuery(getITRecords);
						
						try {
							while(rs.next())
							{
								MyRequestForm help=new MyRequestForm();
							
								help.setItReqNo(rs.getInt("Req_No"));
								help.setItReqDate((EMicroUtils.display(rs.getDate("Req_date"))));
								help.setLastApprover(rs.getString("Last_approver"));
								help.setPendingApprover(rs.getString("Pending_approver"));
								help.setReqStatus(rs.getString("Req_Status"));
								help.setRequestType(rs.getString("Req_type"));
								help.setReqPriority(rs.getString("Req_Priority"));
								help.setItEngStatus(rs.getString("ITEng_Status"));
								
								listOfMyRequest.add(help);	
									
							
							}
							
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(listOfMyRequest.size()>0)
						request.setAttribute("it", listOfMyRequest);
						
						
						
						
			 }
			 if(listOfMyRequest.size()==0)
				{
					request.setAttribute("noit", "noit");	
				}
			 myReqForm.setSubfilter(subfiltertype);
			}
 if(myReqForm.getIssrequestype().equalsIgnoreCase("Sap issues"))
		  {
			  
			  int  totalRecords = 0;
			  
			  String status="";
			  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
			   status ="select count(*) from IT_SAP_ISSUES where Req_Status = '"+myReqForm.getSubfilter()+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";
			  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
				  status ="select count(*) from IT_SAP_ISSUES where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";  
			 
				 ResultSet rs10= ad.selectQuery(status);
					 try {
					while(rs10.next())
					 {
						totalRecords=rs10.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				 int  startRecord=0;
		  int  endRecord=0;
			 if(totalRecords>10)
			 {
			 myReqForm.setTotalRecords(totalRecords);
			 startRecord=1;
			 endRecord=10;
			 myReqForm.setStartRecord(1);
			 myReqForm.setEndRecord(10);
			  request.setAttribute("disablePreviousButton", "disablePreviousButton");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("nextButton", "nextButton");
			 }else
			 {
				  startRecord=1;
				  endRecord=totalRecords;
				  myReqForm.setTotalRecords(totalRecords);
				  myReqForm.setStartRecord(1);
				  myReqForm.setEndRecord(totalRecords); 
			 }
			 ArrayList listOfMyRequest=new ArrayList();
			 
			 if(totalRecords>0)
			 {
				 
					String getITRecords="";
					
					  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
					  {
		            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_SAP_ISSUES as it where Req_Status='"+myReqForm.getSubfilter()+"' and "
		            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between 1 and 10 order by Req_No desc";		            	 
					  }
					  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))
					  {
		            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  it.Req_No desc) AS  RowNum,* from IT_SAP_ISSUES as it where  "
			            	  		+ "(Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"')) as  sub Where   sub.RowNum between 1 and 10 order by Req_No desc";		            	 
						  
						  
					  }
					  
					  
					  ResultSet rs=ad.selectQuery(getITRecords);
						
						try {
							while(rs.next())
							{
								MyRequestForm help=new MyRequestForm();
							
								help.setItReqNo(rs.getInt("Req_No"));
								help.setItReqDate((EMicroUtils.display(rs.getDate("Req_date"))));
								help.setLastApprover(rs.getString("Last_approver"));
								help.setPendingApprover(rs.getString("Pending_approver"));
								help.setReqStatus(rs.getString("Req_Status"));
								help.setRequestType(rs.getString("Req_type"));
								help.setReqPriority(rs.getString("Req_Priority"));
								help.setItEngStatus(rs.getString("ITEng_Status"));
								
								listOfMyRequest.add(help);	
									
							
							}
							
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(listOfMyRequest.size()>0)
						request.setAttribute("sapit", listOfMyRequest);
						
						
						
						
						
			 }
			 if(listOfMyRequest.size()==0)
				{
					request.setAttribute("noit", "noit");	
				}
			 myReqForm.setSubfilter(subfiltertype);
			}
			  
		  
 if(myReqForm.getIssrequestype().equalsIgnoreCase("It issues"))
			  {
				  
			  int  totalRecords = 0;
			  
			  String status="";
			  if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))
			   status ="select count(*) from IT_ISSUES where Req_Status = '"+myReqForm.getSubfilter()+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";
			  if(myReqForm.getSubfilter().equalsIgnoreCase("All"))			  
				  status ="select count(*) from IT_ISSUES where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') ";  

				 ResultSet rs10= ad.selectQuery(status);
				 try {
					while(rs10.next())
					 {
						totalRecords=rs10.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			  int  startRecord=0;
			  int  endRecord=0;
				 if(totalRecords>10)
				 {
				 myReqForm.setTotalRecords(totalRecords);
				 startRecord=1;
				 endRecord=10;
				 myReqForm.setStartRecord(1);
				 myReqForm.setEndRecord(10);
				  request.setAttribute("disablePreviousButton", "disablePreviousButton");
				 request.setAttribute("displayRecordNo", "displayRecordNo");
				 request.setAttribute("nextButton", "nextButton");
				 }else
				 {
					  startRecord=1;
					  endRecord=totalRecords;
					  myReqForm.setTotalRecords(totalRecords);
					  myReqForm.setStartRecord(1);
					  myReqForm.setEndRecord(totalRecords); 
				 }
				 ArrayList listOfMyRequest=new ArrayList();
				 
				 if(totalRecords>0)
				 {
					 
						String getITRecords="";
						
						if(!myReqForm.getSubfilter().equalsIgnoreCase("All"))	
						{
			            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,"
			          				+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status from IT_ISSUES as it,emp_official_info as emp "
			          				+ "where  (it.Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') and emp.PERNR=it.Emp_No and  Req_Status='"+myReqForm.getSubfilter()+"') as  sub Where  sub.RowNum between 1 and 10";
						}
						
						if(myReqForm.getSubfilter().equalsIgnoreCase("All"))	
						{
			            	  getITRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,"
			          				+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status from IT_ISSUES as it,emp_official_info as emp "
			          				+ "where  (it.Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') and emp.PERNR=it.Emp_No ) as  sub Where  sub.RowNum between 1 and 10";
						}
						
			            	  ResultSet rs=ad.selectQuery(getITRecords);
							
							try {
								while(rs.next())
								{
									IssuesForm form1=new IssuesForm();
									form1.setRequestNo(rs.getInt("Req_No"));
									form1.setLocation(rs.getString("LOCID"));
									form1.setEmployeename(rs.getString("EMP_FULLNAME"));
									form1.setCategory(rs.getString("Req_Type"));
									form1.setSubcategory(rs.getString("Sub_Category"));
									form1.setSubject(rs.getString("Subject"));
									String ReqDate=rs.getString("Req_date");
									String a[]=ReqDate.split(" "); 
									ReqDate=a[0];
									String b[]=ReqDate.split("-");
									ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									form1.setReqDate(ReqDate);
									form1.setRequestStatus(rs.getString("Req_Status"));
									form1.setReqPriority(rs.getString("Req_Priority"));
									String assignTo=rs.getString("AssignTo");
									String techNames="";
									String getAssignTechnName="select EMP_FULLNAME from emp_official_info where PERNR in ("+assignTo+") order by PERNR desc";
									ResultSet rsTechns=ad.selectQuery(getAssignTechnName);
									while(rsTechns.next()){
										techNames=techNames+rsTechns.getString("EMP_FULLNAME")+" , ";
									}
									if(!(techNames.equalsIgnoreCase(""))){
										techNames=techNames.substring(0, (techNames.length()-2));	
									}
									form1.setAssignTo(techNames);
									
									form1.setSlaStatus(rs.getString("SLA_Status"));
								
									
									listOfMyRequest.add(form1);	
										
								
								}
								
								
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(listOfMyRequest.size()>0)
							request.setAttribute("ithelp", listOfMyRequest);
							myReqForm.setSubfilter(subfiltertype);
							
							
							
				 }
				 if(listOfMyRequest.size()==0)
					{
						request.setAttribute("noit", "noit");	
					}

			  
		  }
		  
			 
 
	   request.setAttribute("setSubfilter", "setSubfilter");  
		return mapping.findForward("myrequest");
		
	}
	
	
	public ArrayList requestlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HelpDeskForm help1 = (HelpDeskForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		NewsandMediaDao ad=new NewsandMediaDao();
		ArrayList listOfMyRequest=new ArrayList();
		
			
			LinkedList it=new LinkedList();
			String getITRecords="";
			
			
            	  getITRecords="select * from IT_HELPDESK where  Emp_No='"+user.getEmployeeNo()+"' order by Req_No desc";
            	 
				ResultSet rs=ad.selectQuery(getITRecords);
				
				try {
					while(rs.next())
					{
						MyRequestForm help=new MyRequestForm();
					
						help.setItReqNo(rs.getInt("Req_No"));
						help.setItReqDate((EMicroUtils.display(rs.getDate("Req_date"))));
						help.setLastApprover(rs.getString("Last_approver"));
						help.setPendingApprover(rs.getString("Pending_approver"));
						help.setReqStatus(rs.getString("Req_Status"));
						help.setRequestType(rs.getString("Req_type"));
						help.setReqPriority(rs.getString("Req_Priority"));
						help.setItEngStatus(rs.getString("ITEng_Status"));
						
						listOfMyRequest.add(help);	
							
					
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
		
		
		 return listOfMyRequest;
	}
	
	
	public ActionForward resetvalues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		HelpDeskForm help = (HelpDeskForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		String forwardtype="";
		String localhostname="";
		String ipaddress="";
		String username="";
		try {
			localhostname = (InetAddress.getLocalHost()).getCanonicalHostName();
			ipaddress = (InetAddress.getLocalHost()).getHostAddress();
			username=(InetAddress.getLocalHost()).getHostName();
			
		} catch (UnknownHostException e1) {
			
			e1.printStackTrace();
		}
		
		String type=help.getRequestType();
		help.setRequestType(type);
		
		String empno="";
		empno=help.getEmployeeno();
		if(empno.equalsIgnoreCase(""))
		{
		 empno=user.getEmployeeNo();
		}
		help.setEmployeeno(empno);
		
		if(type.equalsIgnoreCase("Qms Application Access"))
		{
			
			ArrayList locationID = new ArrayList();
			ArrayList locationName = new ArrayList();
			try {

				String getLocations = "select * from Location";
				ResultSet rsGetLocation = ad.selectQuery(getLocations);
				while (rsGetLocation.next()) {
					locationID.add(rsGetLocation.getString("LOCATION_CODE"));
					locationName.add(rsGetLocation.getString("LOCATION_CODE") + "-"
							+ rsGetLocation.getString("LOCNAME"));
				}
			   }catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			help.setLocationIdList(locationID);
			help.setLocationLabelList(locationName);			
			help.setEmployeeno(empno);
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
				    help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayNewQmsform";
		}
		
		if(type.equalsIgnoreCase("New IT Spares Request"))
		{
	        
				
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
						help.setEmpno(rs.getString("PERNR"));
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setEmpEmailID(rs.getString("EMAIL_ID"));
						help.setHostname(username);
						help.setIPNumber(ipaddress);
			            help.setServer("");
						help.setAssetvalue("");
						help.setAdloginname("");
						help.setReqdfolderacces("");
						help.setReason("");
						help.setReqPriority("");
						help.setAssetcategory("");
						help.setReqbydate("");
						help.setSuggestmodelname("");
						help.setGxpno("");
						help.setGxpyes("");
						help.setTypeofprinter("");
						help.setUsers("");
						help.setPrintvolpermonth("");
						help.setSuggestmodelno("");
						help.setApprxvalue("");
						help.setLanno("");
						help.setLanyes("");
						help.setAnysplreq("");
						help.setPurpose("");
				
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				 ArrayList spareLabelList=new ArrayList();
				 
					String as="select * from it_spares where type='SPARES'";
					ResultSet rssd= ad.selectQuery(as);
					
					try {
						while(rssd.next())
						{
							spareLabelList.add(rssd.getString("Description"));
						}
					
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					
					help.setSpareLabelList(spareLabelList);
				
				request.setAttribute("save", "save");
				forwardtype="displayNewITSpareform";
			
			
		}
		
		if(type.equalsIgnoreCase("New IT Asset Request"))
		{
	        
				
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						
						help.setEmpno(rs.getString("PERNR"));
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setEmpEmailID(rs.getString("EMAIL_ID"));
						help.setHostname(username);
						help.setIPNumber(ipaddress);
			            help.setServer("");
						help.setAssetvalue("");
						help.setAdloginname("");
						help.setReqdfolderacces("");
						help.setReason("");
						help.setReqPriority("");
						help.setAssetcategory("");
						help.setReqbydate("");
						help.setSuggestmodelname("");
						help.setGxpno("");
						help.setGxpyes("");
						help.setTypeofprinter("");
						help.setUsers("");
						help.setPrintvolpermonth("");
						help.setSuggestmodelno("");
						help.setApprxvalue("");
						help.setLanno("");
						help.setLanyes("");
						help.setAnysplreq("");
						help.setPurpose("");
				
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				request.setAttribute("save", "save");
				forwardtype="displayNewITAssetform";
			
			
		}
	
		
		if(type.equalsIgnoreCase("Active Directory User Creation"))
		{        
			LinkedList Adusercreation=new LinkedList();
		
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				    help.setEmpno(rs.getString("PERNR"));
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
		            help.setServer("");
					help.setAssetvalue("");
					help.setAdloginname("");
					help.setReqdfolderacces("");
					help.setReason("");
					help.setReqPriority("");
					help.setFirstname("");
					help.setLastname("");
					help.setInitials("");
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayAduserCreationform";
		}
		
		if(type.equalsIgnoreCase("Active Directory User Deletion"))
		{
			LinkedList Adusercreation=new LinkedList();
		
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setEmpno(rs.getString("PERNR"));
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					 help.setServer("");
					help.setAssetvalue("");
					help.setAdloginname("");
					help.setReqdfolderdeletion("");
					help.setDeletefrom("");
					help.setReason("");
					help.setReqPriority("");
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayAduserDeletionform";
		}
		
		
		if(type.equalsIgnoreCase("Active Directory User Transfer"))
		{
			LinkedList Adusercreation=new LinkedList();
		
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setEmpno(rs.getString("PERNR"));
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setServer("");
					help.setAssetvalue("");
					help.setAdloginname("");
					help.setReqdfolderdeletion("");
					help.setDeletefrom("");
					help.setReason("");
					help.setReqPriority("");
					help.setFromlocationId("");
					help.setTolocationId("");
					help.setFromdepartment("");
					help.setTodepartment("");
			
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			
			ResultSet rs11 = ad.selectQuery("select LOCID," +
					"LOCNAME,location_code from location");
					ArrayList locationList=new ArrayList();
					ArrayList locationLabelList=new ArrayList();
						
					try {
						while(rs11.next()) {
							locationList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
							locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					help.setLocationIdList(locationList);
					help.setLocationLabelList(locationLabelList);
					
					ArrayList deparmentList=new ArrayList();
					ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
					try {
						while(rs12.next()){
							
							deparmentList.add(rs12.getString("DPTSTXT"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					help.setDepartmentList(deparmentList);
			
			request.setAttribute("save", "save");
			forwardtype="displayAduserTransferform";
		}
		
		
		if(type.equalsIgnoreCase("Email ID Create Request"))
		{
			LinkedList LotusIDCreation=new LinkedList();
			
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setEmpno(rs.getString("PERNR"));
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setIsHeHod("");
					help.setSuggestedEmailId("");
					help.setIsthisCommonId("");
					help.setEnablewebAccess("");
					help.setReason("");
					help.setReqPriority("");
					help.setAdloginname("");
					help.setOutsidecom("");
					help.setNewlyJoinedYes("");
					help.setNewlyJoinedNo("");
					help.setTotalIDs("");
					help.setMailIDRetained("");
					help.setFirstname("");
					help.setLastname("");
					help.setMiddname("");
					help.setJoiningDate("");
					help.setOutsidecomno("");
					help.setOutsidecomyes("");
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayLotusIDCreationform";
		}
		if(type.equalsIgnoreCase("Email ID Change Request"))
		{
			LinkedList LotusIDChange=new LinkedList();
			
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					help.setEmpno(rs.getString("PERNR"));
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					help.setIsHeHod("");
					help.setSuggestedEmailId("");
					help.setIsthisCommonId("");
					help.setEnablewebAccess("");
					help.setReason("");
					help.setReasonforchange("");
					help.setFromemailId("");
					help.setTransferoldmail("");
					help.setReqPriority("");
					help.setAdloginname("");
					help.setDeleteoldmails("");
					help.setOutsidecom("");
					help.setNewlyJoinedYes("");
					help.setNewlyJoinedNo("");
					help.setTotalIDs("");
					help.setMailIDRetained("");
					help.setFirstname("");
					help.setLastname("");
					help.setMiddname("");
					help.setJoiningDate("");
					help.setOutsidecomno("");
					help.setOutsidecomyes("");
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayLotusIDChangeform";
		}
		
		if(type.equalsIgnoreCase("Email ID Delete Request"))
		{
			

			LinkedList LotusIDCreation=new LinkedList();
			
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setEmpno(rs.getString("PERNR"));
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setIsHeHod("");
					help.setEmailidDelete("");
					help.setDeletefrom("");
					help.setIsthisCommonId("");
					help.setReqPriority("");
					help.setReason("");
					help.setAdloginname("");
					help.setForward_MailID("");
					help.setDeletedDBYes("");
					help.setDeletedDBNo("");
					help.setMailIDPath("");
					
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayLotusIDDeletionform";
		
			
		}
		if(type.equalsIgnoreCase("Internet Access Request"))
		{
			LinkedList internet=new LinkedList();
			
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					help.setEmpno(rs.getString("PERNR"));
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					help.setAssetvalue("");
					help.setAdloginname("");
					help.setInternetgrp("");
					help.setReqPriority("");
					help.setIsHeHod("");
					help.setReason("");
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayInternetAccessform";
		}
		
		
		if(type.equalsIgnoreCase("External Drives Access Request"))
		{
			LinkedList externaldrives=new LinkedList();
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					help.setEmpno(rs.getString("PERNR"));
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					help.setAssetvalue("");
					help.setCdrom("");
					help.setUsb("");
					help.setFloppy("");
					help.setAccesstype("");
					help.setReason("");
					help.setReqPriority("");
					help.setAdloginname("");
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayexternaldrivesform";
		}
		
		
		if(type.equalsIgnoreCase("FTP Access Request"))
		{
			LinkedList ftp=new LinkedList();
			
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					help.setEmpno(rs.getString("PERNR"));
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					help.setAssetvalue("");
					help.setReason("");
					help.setRequiredfoldername("");
					help.setDurationofaccess("");
					help.setUsername("");
					help.setPassword("");
					help.setReqPriority("");
					help.setAdloginname("");
				    help.setServer("");
					help.setAccesstype("");
					help.setFrmdurationofaccess("");
					help.setTodurationofaccess("");
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayFtpAccessform";
		}
		
		if(type.equalsIgnoreCase("File Server Access Request"))
		{
			LinkedList folder=new LinkedList();
			
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{   
					help.setEmpno(rs.getString("PERNR"));
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));  
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setEmpEmailID(rs.getString("EMAIL_ID"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					help.setAssetvalue("");
					help.setAdloginname("");
					help.setReqPriority("");
					help.setReason("");
					help.setAccesstype("");
					help.setServer("");
					help.setRequiredfoldername("");
					help.setReqdfolderacces("");
					help.setFromlocationId("");
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayFolderAccessform";
		}
		 return mapping.findForward(forwardtype);
	}

	public ActionForward submitrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		HelpDeskForm help = (HelpDeskForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		NewsandMediaDao ad=new NewsandMediaDao();
		String forwardtype="";
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
	    String dateNow = ft.format(dNow);
	    
	    Date dNow1 = new Date( );
		 SimpleDateFormat ft1 = new SimpleDateFormat ("dd/MM/yyyy");
	    String dateNow1 = ft1.format(dNow1);
	    
	    String empno="";
		empno=help.getEmployeeno();
		if((empno.equalsIgnoreCase("") )|| empno==null)
		{
		 empno=user.getEmployeeNo();
		}
	    
		//check Approvar status
				String locID="";
				int checkApprCount=0;
				String type=help.getRequestType();
				try{
				String getEmpLocid="select loc.LOCID from emp_official_info e,Location as loc where PERNR='"+empno+"' and e.LOCID=loc.LOCATION_CODE";
				ResultSet rsLoc=ad.selectQuery(getEmpLocid);
				while(rsLoc.next()){
					locID=rsLoc.getString(1);
				}
				String checkAppr="select count(*) from IT_Approvers it,Location as loc where  loc.LOCID=it.LOCATION_ID and "
						+ "Req_Type='"+type+"' and it.LOCATION_ID='"+locID+"'";
				ResultSet rsCheckAppr=ad.selectQuery(checkAppr);
				
					while(rsCheckAppr.next()){
						checkApprCount=rsCheckAppr.getInt(1);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				if(checkApprCount==0){
					request.setAttribute("save", "save");
					help.setMessage("No Technicians Assigned...");
					if(type.equals("Active Directory User Creation"))
						return mapping.findForward("displayAduserCreationform");
					if(type.equals("Active Directory User Deletion"))
						return mapping.findForward("displayAduserDeletionform");
					if(type.equals("Active Directory User Transfer"))
						return mapping.findForward("displayAduserTransferform");
					if(type.equals("Email ID Change Request"))
						return mapping.findForward("displayLotusIDChangeform");
					if(type.equals("Email ID Create Request"))
						return mapping.findForward("displayLotusIDCreationform");
					if(type.equals("Email ID Delete Request"))
						return mapping.findForward("displayLotusIDDeletionform");
					if(type.equals("External Drives Access Request"))
						return mapping.findForward("displayexternaldrivesform");
					if(type.equals("File Server Access Request"))
						return mapping.findForward("displayFtpAccessform");
					if(type.equals("FTP Access Request"))
						return mapping.findForward("displayFolderAccessform");
					if(type.equals("New IT Asset Request"))
						return mapping.findForward("displayNewITAssetform");
					if(type.equals("New IT Spares Request"))
						return mapping.findForward("displayNewITSpareform");
					if(type.equals("Qms Application Access"))
						return mapping.findForward("displayNewQmsform");
				}
		
		
		
		
		
		
		
		
		ITMail email = new ITMail();
		
		
	    
	  synchronized (this) {
		
	
	        int i=0;
	    	int old_RequestNo=help.getRequestNo();
			String getReqestNumber="select max(Req_No)  from IT_HELPDESK";
			int maxReqno=0;
			ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
			try {
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			maxReqno+=1;
			help.setRequestNo(maxReqno);
			
			
			//String type=help.getRequestType();
			
			int hodpresent=0;
			int hodpresentinIt=0;
			String HODPresentId="";
			String HODPresentname="";
			String itengineerid="";
			String itengineername="";
			String itengineeridpar1="";
			String itengineeridpar2="";
			
			
			String HOD="";
            boolean HODAvailable=false;
            boolean PlantheadAvailable=false;
			
			 //chk if he has Hod
			 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
			 ResultSet rs1= ad.selectQuery(chkhod);
			 try {
				while(rs1.next())
				 {
					 hodpresent=rs1.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			//get hod details
			
		
			if(hodpresent>0){
			String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
					" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
			 ResultSet rs2= ad.selectQuery(gethod);
			 
			 try {
				if(rs2.next())
				 {
				
					HODPresentId=rs2.getString("ApproverId");
					HODPresentname=rs2.getString("EMP_FULLNAME");
				 }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
			
			//Plant head
			String plantheadid="";
			String plantheadname="";
			String plantheaddep="";
			String plantheaddesg="";
			String a1=locID;
			
			if(!a1.equalsIgnoreCase("16"))
			{
				
				if(!(type.equalsIgnoreCase("Email ID Create Request")||type.equalsIgnoreCase("Email ID Change Request")||type.equalsIgnoreCase("Email ID Delete Request")))					
				{	
				String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a1+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
				ResultSet pl=ad.selectQuery(plant);
				try {
					while(pl.next())
					 {
						plantheadid=pl.getString("pernr");
						plantheadname=pl.getString("EMP_FULLNAME");
						plantheaddep=pl.getString("DPTSTXT");
						plantheaddesg=pl.getString("DSGSTXT");
					 }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from Emial_IdApprvr pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where pl.Plant=(select LOCATION_CODE from Location where LOCID='"+a1+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
				ResultSet pl=ad.selectQuery(plant);
				try {
					while(pl.next())
					 {
						plantheadid=pl.getString("pernr");
						plantheadname=pl.getString("EMP_FULLNAME");
						plantheaddep=pl.getString("DPTSTXT");
						plantheaddesg=pl.getString("DSGSTXT");
					 }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				
				
			}
			
			
			//chk if he is present in It approver
			 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
			 ResultSet rs11= ad.selectQuery(chkhodinit);
			 try {
				while(rs11.next())
				 {
					hodpresentinIt=rs11.getInt(1);
					
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			//IT Engineer details
			 String chkIt="select it.Approver_ID,,it.Parallel_Approver1,Parallel_Approver2,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp where Req_Type = '"+type+"' " +
			 "and Priority=1 and emp.PERNR=it.Approver_ID and it.LOCATION_ID='"+locID+"'";
			 ResultSet rs3= ad.selectQuery(chkIt);
			 try {
				if(rs3.next())
				 {
					itengineerid=rs3.getString("Approver_ID");
					itengineername=rs3.getString("EMP_FULLNAME");
					itengineeridpar1=rs3.getString("Parallel_Approver1");
					itengineeridpar2=rs3.getString("Parallel_Approver2");
					
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			
			String approver="";
			String lApprover="";
			String approvermail="";
			String pendingApprovers="";
			
			if(plantheadid.equalsIgnoreCase(""))
			{
			
			if(hodpresent==0 || hodpresentinIt>0){
			
			String getApproverID="select Priority,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp where Req_Type='"+type+"'" +
					" and emp.PERNR=it.Approver_ID  and it.LOCATION_ID='"+locID+"' order by Priority  ";				
			ResultSet approverRS=ad.selectQuery(getApproverID);
			try {
				while(approverRS.next()){
						int priority = approverRS.getInt("Priority");
						
						if(pendingApprovers.equalsIgnoreCase("")){
						pendingApprovers = approverRS.getString("EMP_FULLNAME");
						}
						else{
							pendingApprovers = pendingApprovers + " ," +approverRS.getString("EMP_FULLNAME");
						}
						
				}
				pendingApprovers=pendingApprovers+","+itengineername;
				
			} catch (SQLException e2) {
				
				e2.printStackTrace();
			}
			
			}
			
			
			
			if(hodpresent>0 && hodpresentinIt==0 )
			{
				
				String getApproverID="select empHod.EMP_FULLNAME as hod,Priority,emp.EMP_FULLNAME as appr from IT_Approvers as it,emp_official_info as emp,emp_official_info as empHod where Req_Type='"+type+"'" +
						" and emp.PERNR=it.Approver_ID and empHod.PERNR='"+HODPresentId+"' and it.LOCATION_ID='"+locID+"' order by Priority";				
				ResultSet approverRS=ad.selectQuery(getApproverID);
				try {
					while(approverRS.next()){
							int priority = approverRS.getInt("Priority");
							
							if(pendingApprovers.equalsIgnoreCase("")){
							pendingApprovers = approverRS.getString("hod")+","+approverRS.getString("appr");
							}
							else{
								pendingApprovers =  pendingApprovers + " ," +approverRS.getString("appr");
							}
						
					}
					
					pendingApprovers=pendingApprovers+","+itengineername;
					
				} catch (SQLException e2) {
					
					e2.printStackTrace();
				}
				
				HODAvailable=true;
				
			}
			}
			
			if(!plantheadid.equalsIgnoreCase(""))
			{
			if(!(type.equalsIgnoreCase("Active Directory User Creation")||type.equalsIgnoreCase("Active Directory User Deletion")||type.equalsIgnoreCase("Active Directory User Transfer")))
					
			//if(!(type.equalsIgnoreCase("Active Directory User Creation")||type.equalsIgnoreCase("Active Directory User Deletion")||type.equalsIgnoreCase("Active Directory User Transfer")||type.equalsIgnoreCase("Email ID Create Request")))
						
				/*if(!(type.equalsIgnoreCase("Active Directory User Creation")||type.equalsIgnoreCase("Active Directory User Deletion")||type.equalsIgnoreCase("Active Directory User Transfer")||type.equalsIgnoreCase("Email ID Create Request")))
			*/		
				{
					String getApproverID="select empHod.EMP_FULLNAME as hod,Priority,emp.EMP_FULLNAME as appr from IT_Approvers as it,emp_official_info as emp,emp_official_info as empHod where Req_Type='"+type+"'" +
							" and emp.PERNR=it.Approver_ID and empHod.PERNR='"+plantheadid+"' and it.LOCATION_ID='"+locID+"' order by Priority";				
					ResultSet approverRS=ad.selectQuery(getApproverID);
					try {
						while(approverRS.next()){
								int priority = approverRS.getInt("Priority");
								
								if(pendingApprovers.equalsIgnoreCase("")){
								pendingApprovers = approverRS.getString("hod")+","+approverRS.getString("appr");
								}
								else{
									pendingApprovers =  pendingApprovers + " ," +approverRS.getString("appr");
								}
							
						}
						
						pendingApprovers=pendingApprovers+","+itengineername;
						
					} catch (SQLException e2) {
						
						e2.printStackTrace();
					}
					
					PlantheadAvailable=true;
					
				}
				else
				{

					
					if(hodpresent==0 || hodpresentinIt>0){
					
					String getApproverID="select Priority,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp where Req_Type='"+type+"'" +
							" and emp.PERNR=it.Approver_ID  and it.LOCATION_ID='"+locID+"' order by Priority  ";				
					ResultSet approverRS=ad.selectQuery(getApproverID);
					try {
						while(approverRS.next()){
								int priority = approverRS.getInt("Priority");
								
								if(pendingApprovers.equalsIgnoreCase("")){
								pendingApprovers = approverRS.getString("EMP_FULLNAME");
								}
								else{
									pendingApprovers = pendingApprovers + " ," +approverRS.getString("EMP_FULLNAME");
								}
								
						}
						pendingApprovers=pendingApprovers+","+itengineername;
						
					} catch (SQLException e2) {
						
						e2.printStackTrace();
					}
					
					}
					
					
					
					if(hodpresent>0 && hodpresentinIt==0 )
					{
						
						String getApproverID="select empHod.EMP_FULLNAME as hod,Priority,emp.EMP_FULLNAME as appr from IT_Approvers as it,emp_official_info as emp,emp_official_info as empHod where Req_Type='"+type+"'" +
								" and emp.PERNR=it.Approver_ID and empHod.PERNR='"+HODPresentId+"' and it.LOCATION_ID='"+locID+"' order by Priority";				
						ResultSet approverRS=ad.selectQuery(getApproverID);
						try {
							while(approverRS.next()){
									int priority = approverRS.getInt("Priority");
									
									if(pendingApprovers.equalsIgnoreCase("")){
									pendingApprovers = approverRS.getString("hod")+","+approverRS.getString("appr");
									}
									else{
										pendingApprovers =  pendingApprovers + " ," +approverRS.getString("appr");
									}
								
							}
							
							pendingApprovers=pendingApprovers+","+itengineername;
							
						} catch (SQLException e2) {
							
							e2.printStackTrace();
						}
						
						HODAvailable=true;
						
					}
					
					
					
					
				}
			}
			
			
			
			 //transaction table
			
			if(HODAvailable==false)
			{
			 String trans="insert into IT_TRANSACTION_DETAILS(Req_No,Emp_No,Req_type,Approver_ID,Req_Status,Transaction_Date,ITEng_Status)" +
				" values('"+maxReqno+"','"+empno+"','"+type+"','"+itengineerid+"','Pending','"+dateNow+"','Open')";
			 	 i=ad.SqlExecuteUpdate(trans);
			}
			if(HODAvailable==true)
			{
			 String trans="insert into IT_TRANSACTION_DETAILS(Req_No,Emp_No,Req_type,Approver_ID,Req_Status,Transaction_Date,ITEng_Status)" +
				" values('"+maxReqno+"','"+empno+"','"+type+"','"+HODPresentId+"','Pending','"+dateNow+"','Open')";
			 	 i=ad.SqlExecuteUpdate(trans);
			}
			
			if(type.equalsIgnoreCase("Qms Application Access"))
			{
				
				String update="";
				String rights[]=request.getParameterValues("rights");
				if(rights==null)
				{
					update="";
				}
				
			
				if(rights!=null)
				{
				for(int j=0;j<rights.length;j++)
				{
					if(!rights[j].equalsIgnoreCase(""))
					{
						update=update+rights[j];
					}
					
				}
				}
				
				String loc[] = help.getMultilocation();
				String ACtualloc ="";
				if(loc!=null)
				{
			
				if(loc.length!=0)
				{
				StringBuffer reqloc = new StringBuffer();
				for (int k = 0; k< loc.length; k++) {
					reqloc.append(loc[k] + ",");
				}
				 ACtualloc = reqloc.substring(0, reqloc.length() - 1)
						.toString();
				}
				}
				
				String view=request.getParameter("view");
				
				
				 String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,HostName,IPAddress,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,QMS_GrpId,QMS_LocId,QMS_View,raisedBy,approved_date,AssignTo,Location) "
				 		+ "values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getHostname()+"','"+help.getIPNumber()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open','"+update+"','"+ACtualloc+"','"+view+"','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
							
							 i=ad.SqlExecuteUpdate(insertAduser);
							 
							 if(i>0){
								 
								 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
								 resetvalues(mapping, form, request, response);
								
								
								if(HODAvailable==false)
								{
								String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
								"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
										"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
										 i=ad.SqlExecuteUpdate(insert);
										 
										 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,"");
								}
								
								
					 			if(HODAvailable==true)
								{
								String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
								"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
										"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
										 i=ad.SqlExecuteUpdate(insert);
										 
										int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,"");
								}
								
							 }
							 else
							 {
								 help.setMessage1("Error...When request Submittion.Please check");
							 }
				
							 request.setAttribute("save", "save");
							 forwardtype="displayNewQmsform";
			}
			
			if(type.equalsIgnoreCase("New IT Spares Request"))
			{
		 		String cat=help.getAssetcategory();
		 	
				
				
				
				
				
		 		String loc=help.getLocation();
		 		
				 String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,HostName,IPAddress,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,Category,reqbydate,suggmodel,Apprxvalue,purpose,raisedBy,approved_date,AssignTo,Location) values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getHostname()+"','"+help.getIPNumber()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open',"
				 	+ "'"+help.getAssetcategory()+"','"+help.getReqbydate()+"','"+help.getSuggestmodelname()+"','"+help.getApprxvalue()+"','"+help.getPurpose()+"','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
					
					 i=ad.SqlExecuteUpdate(insertAduser);
		 		
					 
					 
					 if(i>0){
						 
						 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
						 resetvalues(mapping, form, request, response);
						
						
						 if(PlantheadAvailable==false)
						 {
						 if(HODAvailable==false)
							{
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
							"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
									"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
									 i=ad.SqlExecuteUpdate(insert);
									 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,"");
							}
						 
						 if(HODAvailable==true)
							{
							 
							 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
										"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
												"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
												 i=ad.SqlExecuteUpdate(insert);
												 int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,"");
							 
							}
						 
						 }
						 if(PlantheadAvailable==true)
						 {
							 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
										"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
												"'"+dateNow1+"','Pending','','"+plantheadid+"','','"+user.getEmployeeNo()+"','"+type+"')";
												 i=ad.SqlExecuteUpdate(insert);
												 int j = email.sendMailToApprover(request, maxReqno,type,plantheadid,"");
						 }
						
					 }
					 else
					 {
						 help.setMessage1("Error...When request Submittion.Please check");
					 }
					 
					
					
					 
					 forwardtype="displayNewITSpareform";
				
		 		
			}
			 	 
			 	if(type.equalsIgnoreCase("New IT Asset Request"))
				{
			 		String cat=help.getAssetcategory();
			 		String gxp="";
					String x=help.getGxpyes();
					String y=help.getGxpno();
					
					if(x==null)
					{
						gxp=help.getGxpno();
					}
					
					if(y==null)
					{
						gxp=help.getGxpyes();
					}
					
					
					String lan="";
					String a=help.getLanyes();
					String b=help.getLanno();
					
					if(a==null)
					{
						lan=help.getLanno();
					}
					
					if(b==null)
					{
						lan=help.getLanyes();
					}
					
			 		String loc=help.getLocation();
			 		if(cat.equalsIgnoreCase("Desktop") ||cat.equalsIgnoreCase("Laptop"))
			 		{
					 String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,HostName,IPAddress,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,Category,reqbydate,suggmodel,gxp"
					 	+ ",Apprxvalue,lan,anysplreq,purpose,raisedBy,approved_date,AssignTo,Location) values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getHostname()+"','"+help.getIPNumber()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open',"
					 	+ "'"+help.getAssetcategory()+"','"+help.getReqbydate()+"','"+help.getSuggestmodelname()+"','"+gxp+"','"+help.getApprxvalue()+"','"+lan+"','"+help.getAnysplreq()+"','"+help.getPurpose()+"','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
						
						 i=ad.SqlExecuteUpdate(insertAduser);
			 		}
			 		
			 		if(cat.equalsIgnoreCase("Printer"))
			 		{
			 			String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,Asset_Details,HostName,IPAddress,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,Category,reqbydate,suggmodel,gxp"
						+ ",Apprxvalue,lan,anysplreq,purpose,typeofprinter,printvolpm,noofusers,suggmodelno,raisedBy,approved_date,AssignTo,Location) values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getAssetvalue()+"','"+help.getHostname()+"','"+help.getIPNumber()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open',"
						+ "'"+help.getAssetcategory()+"','"+help.getReqbydate()+"','"+help.getSuggestmodelname()+"','"+gxp+"','"+help.getApprxvalue()+"','"+lan+"','"+help.getAnysplreq()+"','"+help.getPurpose()+"','"+help.getTypeofprinter()+"','"+help.getPrintvolpermonth()+"','"+help.getUsers()+"','"+help.getSuggestmodelno()+"','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
								
						 i=ad.SqlExecuteUpdate(insertAduser);
			 		}
						 
						 
						 if(i>0){
							 
							 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
							 resetvalues(mapping, form, request, response);
							
							
							 if(PlantheadAvailable==false)
							 {
							 if(HODAvailable==false)
								{
							 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
								"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
										"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
										 i=ad.SqlExecuteUpdate(insert);
										 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,"");
								}
							 
							 if(HODAvailable==true)
								{
								 
								 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
											"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
													"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
													 i=ad.SqlExecuteUpdate(insert);
													 int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,"");
								 
								}
							 
							 }
							 if(PlantheadAvailable==true)
							 {
								 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
											"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
													"'"+dateNow1+"','Pending','','"+plantheadid+"','','"+user.getEmployeeNo()+"','"+type+"')";
													 i=ad.SqlExecuteUpdate(insert);
													 int j = email.sendMailToApprover(request, maxReqno,type,plantheadid,"");
							 }
							
						 }
						 else
						 {
							 help.setMessage1("Error...When request Submittion.Please check");
						 }
						 
						
						
						 
						 
						 forwardtype="displayNewITAssetform";
					
			 		
				}
			
			if(type.equalsIgnoreCase("Active Directory User Creation"))
			{
			 String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,Asset_Details,HostName,IPAddress,ADLoginName,Server_Address,Req_Folder_access,Reason,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,Frst_Name,Last_Name,Initials,raisedBy,approved_date,AssignTo,Location) " +
				"values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getAssetvalue()+"','"+help.getHostname()+"'," +
				"'"+help.getIPNumber()+"','"+help.getAdloginname()+"','"+help.getServer()+"','"+help.getReqdfolderacces()+"','"+help.getReason()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open','"+help.getFirstname()+"','"+help.getLastname()+"','"+help.getInitials()+"','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
				
				
				 i=ad.SqlExecuteUpdate(insertAduser);
				 if(i>0){
					 
					 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
					 resetvalues(mapping, form, request, response)	 ;
					
					
					if(HODAvailable==false)
					{
					String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
					"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
							"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
							 i=ad.SqlExecuteUpdate(insert);
							 
							 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,"");
							 
					if(itengineeridpar1!=null)
					{
						if(!itengineeridpar1.equalsIgnoreCase(""))
						{
							String insert1="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+itengineeridpar1+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert1);
											 
											 int j1 = email.sendMailToApprover(request, maxReqno,type,itengineeridpar1,"");
						}
					}
					
					if(itengineeridpar2!=null)
					{
						if(!itengineeridpar2.equalsIgnoreCase(""))
						{
							String insert1="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+itengineeridpar2+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert1);
											 
											 int j1 = email.sendMailToApprover(request, maxReqno,type,itengineeridpar2,"");
						}
					}
					}
					
					
		 			if(HODAvailable==true)
					{
					String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
					"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
							"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
							 i=ad.SqlExecuteUpdate(insert);
							 
							 int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,"");
					}
					
				 }
				 else
				 {
					 help.setMessage1("Error...When request Submittion.Please check");
				 }
				 
				
				
				 
				 
				 forwardtype="displayAduserCreationform";
			}
			
			
			if(type.equalsIgnoreCase("Active Directory User Deletion"))
			{
	         	 String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,Asset_Details,HostName,IPAddress,ADLoginName,Server_Address,Req_Folder_Deletion,Delete_From,Reason,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,raisedBy,approved_date,AssignTo,Location) " +
					"values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getAssetvalue()+"','"+help.getHostname()+"'," +
					"'"+help.getIPNumber()+"','"+help.getAdloginname()+"','"+help.getServer()+"','"+help.getReqdfolderdeletion()+"','"+help.getDeletefrom()+"','"+help.getReason()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
					
					
					 i=ad.SqlExecuteUpdate(insertAduser);
					 if(i>0){
						 
						 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
						 resetvalues(mapping, form, request, response)	 ;
						
						
						 if(HODAvailable==false)
							{
						 
						String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
								"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
								 i=ad.SqlExecuteUpdate(insert);
								 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,"");
								 
								 if(itengineeridpar1!=null)
									{
										if(!itengineeridpar1.equalsIgnoreCase(""))
										{
											String insert1="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
													"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
															"'"+dateNow1+"','Pending','','"+itengineeridpar1+"','','"+user.getEmployeeNo()+"','"+type+"')";
															 i=ad.SqlExecuteUpdate(insert1);
															 
															 int j1 = email.sendMailToApprover(request, maxReqno,type,itengineeridpar1,"");
										}
									}
									
									if(itengineeridpar2!=null)
									{
										if(!itengineeridpar2.equalsIgnoreCase(""))
										{
											String insert1="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
													"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
															"'"+dateNow1+"','Pending','','"+itengineeridpar2+"','','"+user.getEmployeeNo()+"','"+type+"')";
															 i=ad.SqlExecuteUpdate(insert1);
															 
															 int j1 = email.sendMailToApprover(request, maxReqno,type,itengineeridpar2,"");
										}
									}
									
							
							
							}
						 
						 if(HODAvailable==true)
							{
						 
						String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
								"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
								 i=ad.SqlExecuteUpdate(insert);
								 
								 int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,"");
								 
							}
						 
								
							 }
							 else
							 {
								 help.setMessage1("Error...When request Submittion.Please check");
							 }
							 
							
							 
							 forwardtype="displayAduserDeletionform";
						}
			
			
			if(type.equalsIgnoreCase("Active Directory User Transfer"))
			{
			 String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,Asset_Details,HostName,IPAddress,ADLoginName,Reason,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,From_Loc,To_Loc,From_Dep,To_Dep,raisedBy,approved_date,AssignTo,Location) " +
				"values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getAssetvalue()+"','"+help.getHostname()+"','"+help.getIPNumber()+"','"+help.getAdloginname()+"','"+help.getReason()+"','In Process','No',"
				+ "'"+pendingApprovers+"','"+help.getReqPriority()+"','Open','"+help.getFromlocationId()+"','"+help.getTolocationId()+"','"+help.getFromdepartment()+"','"+help.getTodepartment()+"','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
				
				
				 i=ad.SqlExecuteUpdate(insertAduser);
				 if(i>0){
					 
					 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
					 resetvalues(mapping, form, request, response)	 ;
					
					
					if(HODAvailable==false)
					{
					String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
					"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
							"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
							 i=ad.SqlExecuteUpdate(insert);
							 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,"");

							 if(itengineeridpar1!=null)
								{
									if(!itengineeridpar1.equalsIgnoreCase(""))
									{
										String insert1="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
												"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
														"'"+dateNow1+"','Pending','','"+itengineeridpar1+"','','"+user.getEmployeeNo()+"','"+type+"')";
														 i=ad.SqlExecuteUpdate(insert1);
														 
														 int j1 = email.sendMailToApprover(request, maxReqno,type,itengineeridpar1,"");
									}
								}
								
								if(itengineeridpar2!=null)
								{
									if(!itengineeridpar2.equalsIgnoreCase(""))
									{
										String insert1="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
												"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
														"'"+dateNow1+"','Pending','','"+itengineeridpar2+"','','"+user.getEmployeeNo()+"','"+type+"')";
														 i=ad.SqlExecuteUpdate(insert1);
														 
														 int j1 = email.sendMailToApprover(request, maxReqno,type,itengineeridpar2,"");
									}
								}
					
					
					}
					
					
					if(HODAvailable==true)
					{
					String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
					"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
							"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
							 i=ad.SqlExecuteUpdate(insert);
							 int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,"");

					}
					
				 }
				 else
				 {
					 help.setMessage1("Error...When request Submittion.Please check");
				 }
				 
				
				 
				 forwardtype="displayAduserTransferform";
			}
			
			if(type.equalsIgnoreCase("Email ID Create Request"))
			{
				String outside="";
				String x=help.getOutsidecomyes();
				String y=help.getOutsidecomno();
				
				if(x==null)
				{
					outside=help.getOutsidecomno();
				}
				
				if(y==null)
				{
					outside=help.getOutsidecomyes();
				}
				
				String joiningDt=help.getJoiningDate();
				String newlyJoinined="";
				String a=help.getNewlyJoinedYes();
				String b=help.getNewlyJoinedNo();
				if(a==null)
					newlyJoinined=help.getNewlyJoinedNo();
				else{
					newlyJoinined=help.getNewlyJoinedYes();
				}
				
				//newly_Joinied,joining_date,Total_Mailids
				String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,HostName,IPAddress,Emp_EmailID,ADLoginName,Is_He_Hod,SuggestedEmailId,comm_outside,newly_Joinied,joining_date,Is_This_common_ID,Enable_Web_access,Reason,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,Frst_Name,Last_Name,Middle_Name,Total_Mailids,raisedBy,approved_date,AssignTo,Location,Designation,Department) " +
				"values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getHostname()+"','"+help.getIPNumber()+"','"+help.getEmpEmailID()+"','"+help.getAdloginname()+"','"+help.getIsHeHod()+"','"+help.getSuggestedEmailId()+"','"+outside+"','"+newlyJoinined+"','"+joiningDt+"','"+help.getIsthisCommonId()+"','"+help.getEnablewebAccess()+"'," +
				"'"+help.getReason()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open','"+help.getFirstname()+"','"+help.getLastname()+"','"+help.getMiddname()+"','"+help.getTotalIDs()+"','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"', '"+help.getDesignation()+"' ,'"+help.getDepartment()+"')";
				 i=ad.SqlExecuteUpdate(insertAduser);
				 if(i>0){
					 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
					 resetvalues(mapping, form, request, response)	 ;
					 if(PlantheadAvailable==false)
					 {
					 if(HODAvailable==false)
						{
					 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
								"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
								 i=ad.SqlExecuteUpdate(insert);
								 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,"");
						}
					 
					 if(HODAvailable==true)
						{
						 
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert);
											 int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,"");
						 
						}
					 
					 }
					 if(PlantheadAvailable==true)
					 {
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+plantheadid+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert);
											 int j = email.sendMailToApprover(request, maxReqno,type,plantheadid,"");
					 }
					 
				 }
				 else
				 {
					 help.setMessage1("Error...When request Submittion.Please check");
				 }
				 
				 forwardtype="displayLotusIDCreationform";
			}
			
			if(type.equalsIgnoreCase("Email ID Change Request"))
			{
				
				String outside="";
				String x=help.getOutsidecomyes();
				String y=help.getOutsidecomno();
				
				if(x==null)
				{
					outside=help.getOutsidecomno();
				}
				
				if(y==null)
				{
					outside=help.getOutsidecomyes();
				}
				String joiningDt=help.getJoiningDate();
				String newlyJoinined="";
				String a=help.getNewlyJoinedYes();
				String b=help.getNewlyJoinedNo();
				if(a==null)
					newlyJoinined=help.getNewlyJoinedNo();
				else{
					newlyJoinined=help.getNewlyJoinedYes();
				}
				// newly_Joinied,joining_date,Total_Mailids,OldMailID_Retained
				String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,HostName,IPAddress,Emp_EmailID,ADLoginName,From_EmailID,Is_He_Hod,Transfer_Old_Emails," +
				"SuggestedEmailId,delete_old_email,comm_outside,newly_Joinied,joining_date,Is_This_common_ID,Enable_Web_access,Reason,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,Total_Mailids,OldMailID_Retained,Frst_Name,Last_Name,Middle_Name,raisedBy,approved_date,AssignTo,Location)" +
				"values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getHostname()+"','"+help.getIPNumber()+"','"+help.getEmpEmailID()+"','"+help.getAdloginname()+"','"+help.getFromemailId()+"'," +
				"'"+help.getIsHeHod()+"','"+help.getTransferoldmail()+"','"+help.getSuggestedEmailId()+"','"+help.getDeleteoldmails()+"','"+outside+"','"+newlyJoinined+"','"+joiningDt+"','"+help.getIsthisCommonId()+"','"+help.getEnablewebAccess()+"'," +
				"'"+help.getReason()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open','"+help.getTotalIDs()+"','"+help.getMailIDRetained()+"','"+help.getFirstname()+"','"+help.getLastname()+"','"+help.getMiddname()+"','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
				 i=ad.SqlExecuteUpdate(insertAduser);
				 if(i>0){
					 
					 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
					 resetvalues(mapping, form, request, response)	 ;
					 
					 if(PlantheadAvailable==false)
					 {
					 if(HODAvailable==false)                                                                                                                                                             
						{
					 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
						"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
								 i=ad.SqlExecuteUpdate(insert);
								 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,"");
								 
						}
					 
					 if(HODAvailable==true)
						{
					 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
								"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
								 i=ad.SqlExecuteUpdate(insert);
								 int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,""); 
						}
					 }
					 
					 if(PlantheadAvailable==true)
					 {
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+plantheadid+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert);
											 int j = email.sendMailToApprover(request, maxReqno,type,plantheadid,""); 
						 
					 }
				 }
				 else
				 {
					 help.setMessage1("Error...When request Submittion.Please check");
				 }
				 
				 forwardtype="displayLotusIDChangeform";
			}
			
			if(type.equalsIgnoreCase("Email ID Delete Request"))
			{
                 String deleteDBStatus="";
                 String a=help.getDeletedDBYes();
                 String b=help.getDeletedDBNo();
                 if(a==null)
                	 deleteDBStatus=help.getDeletedDBNo();
 				else{
 					deleteDBStatus=help.getDeletedDBYes();
 				}
				String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,HostName,IPAddress,ADLoginName,Is_He_Hod,EmailID_Deleted,Delete_From,Is_This_common_ID,Reason,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,deletedDB,path,forward_MailID,raisedBy,approved_date,AssignTo,Location) " +
				"values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getHostname()+"','"+help.getIPNumber()+"','"+help.getAdloginname()+"','"+help.getIsHeHod()+"','"+help.getEmailidDelete()+"','"+help.getDeletefrom()+"','"+help.getIsthisCommonId()+"'," +
				"'"+help.getReason()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open','"+deleteDBStatus+"','"+help.getMailIDPath()+"','"+help.getForward_MailID()+"','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
				 i=ad.SqlExecuteUpdate(insertAduser);
				 if(i>0){
					 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
					 resetvalues(mapping, form, request, response);
					 
					 if(PlantheadAvailable==false)
					 {
					 if(HODAvailable==false)
						{
					 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
								"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
								 i=ad.SqlExecuteUpdate(insert);
								 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,""); 
						}
					 
					 if(HODAvailable==true)
						{
					 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
								"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
								 i=ad.SqlExecuteUpdate(insert);
								 int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,""); 
						}
					 }
					 if(PlantheadAvailable==true)
					 {
						 
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+plantheadid+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert);
											 int j = email.sendMailToApprover(request, maxReqno,type,plantheadid,""); 
						 
					 }
					 
					 
				 }
				 else
				 {
					 help.setMessage1("Error...When request Submittion.Please check");
				 }
				 forwardtype="displayLotusIDDeletionform";
			}
			if(type.equalsIgnoreCase("Internet Access Request"))
			{
				String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,Asset_Details,HostName,IPAddress,ADLoginName,Reason,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,Is_He_Hod,raisedBy,approved_date,AssignTo,Location) " +
				"values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getAssetvalue()+"','"+help.getHostname()+"'," +
				"'"+help.getIPNumber()+"','"+help.getAdloginname()+"','"+help.getReason()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open','"+help.getIsHeHod()+"','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
				
				
				 i=ad.SqlExecuteUpdate(insertAduser);
				 if(i>0){
					 
					 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
					 resetvalues(mapping, form, request, response)	 ;
					 
					 if(PlantheadAvailable==false)
					 {
					 if(HODAvailable==false)
						{
					 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
								"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
								 i=ad.SqlExecuteUpdate(insert);
								 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,"");
						}
					 
					 if(HODAvailable==true)
						{
						 
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert);
											 int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,"");
						 
						}
					 
					 }
					 if(PlantheadAvailable==true)
					 {
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+plantheadid+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert);
											 int j = email.sendMailToApprover(request, maxReqno,type,plantheadid,"");
					 }
				 }
				 else
				 {
					 help.setMessage1("Error...When request Submittion.Please check");
				 }
				 
					forwardtype="displayInternetAccessform";
			}
			
			if(type.equalsIgnoreCase("External Drives Access Request"))
			{
				String devicetype="";
				String a=help.getCdrom();
				String b=help.getUsb();
				
				if(a==null)
				{
				a="";
				}
				if(b==null)
				{
				b="";
				}
				
				
				 if(a.equalsIgnoreCase("CD/DVD_ROM") && b.equalsIgnoreCase("USB")){
					 devicetype="3";
				 }else{
					 if(a.equalsIgnoreCase("CD/DVD_ROM")){
						 devicetype="1";
					 }
					 if(b.equalsIgnoreCase("USB")){
						 devicetype="2";
					 }
				 }
				
				String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,Asset_Details,HostName,IPAddress,ADLoginName,Device_type,Access_type,Reason,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,raisedBy,approved_date,AssignTo,Location) " +
				"values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getAssetvalue()+"','"+help.getHostname()+"'," +
				"'"+help.getIPNumber()+"','"+help.getAdloginname()+"','"+devicetype+"','"+help.getAccesstype()+"','"+help.getReason()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
				
				
				 i=ad.SqlExecuteUpdate(insertAduser);
				 if(i>0){
					 
					 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
					 resetvalues(mapping, form, request, response)	 ;
					 
					 
					 if(PlantheadAvailable==false)
					 {
					 if(HODAvailable==false)
						{
					 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
								"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
								 i=ad.SqlExecuteUpdate(insert);
								 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,"");
						}
					 
					 if(HODAvailable==true)
						{
						 
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert);
											 int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,"");
						 
						}
					 
					 }
					 if(PlantheadAvailable==true)
					 {
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+plantheadid+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert);
											 int j = email.sendMailToApprover(request, maxReqno,type,plantheadid,"");
					 }
				 }
				 else
				 {
					 help.setMessage1("Error...When request Submittion.Please check");
				 }
				 
				 forwardtype="displayexternaldrivesform";
			}
			
			if(type.equalsIgnoreCase("FTP Access Request"))
			{
				
				String a[]=help.getAccesstypes();
				StringBuffer result = new StringBuffer();
				for (int j = 0; j < a.length; j++) {
				   result.append( a[j]+"," );
				}
				String ans = result.toString();
				ans=ans.substring(0, ans.length()-1);
				
				String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,Asset_Details,HostName,IPAddress,ADLoginName,Server_Address,Req_Folder_access,Access_type,FrmRequiredDurationofAccess,ToRequiredDurationofAccess,Reason,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,RequiredDurationofAccess,raisedBy,approved_date,AssignTo,Location) " +
				"values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getAssetvalue()+"','"+help.getHostname()+"'," +
				"'"+help.getIPNumber()+"','"+help.getAdloginname()+"','"+help.getServer()+"','"+help.getRequiredfoldername()+"','"+ans+"','"+help.getFrmdurationofaccess()+"','"+help.getTodurationofaccess()+"','"+help.getReason()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open','"+help.getDurationofaccess()+"','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
				
				
				 i=ad.SqlExecuteUpdate(insertAduser);
				 if(i>0){
					 
					 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
					 resetvalues(mapping, form, request, response)	 ;
					 
					 if(PlantheadAvailable==false)
					 {
					 if(HODAvailable==false)
						{
					 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
								"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
								 i=ad.SqlExecuteUpdate(insert);
								 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,"");
						}
					 
					 if(HODAvailable==true)
						{
						 
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert);
											 int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,"");
						 
						}
					 
					 }
					 if(PlantheadAvailable==true)
					 {
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+plantheadid+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert);
											 int j = email.sendMailToApprover(request, maxReqno,type,plantheadid,"");
					 }
					 
				 }
				 else
				 {
					 help.setMessage1("Error...When request Submittion.Please check");
				 }
				 
				 forwardtype="displayFtpAccessform";
			}
			
			if(type.equalsIgnoreCase("File Server Access Request"))
			{
				String insertAduser="insert into IT_HELPDESK(Req_No,Req_date,Req_type,Emp_No,Asset_Details,HostName,IPAddress,ADLoginName,Server_Address,Req_Folder_access,Access_type,Reason,Req_Status,Last_approver,Pending_approver,Req_Priority,ITEng_Status,From_Loc,raisedBy,approved_date,AssignTo,Location) " +
				"values('"+maxReqno+"','"+dateNow+"','"+type+"','"+empno+"','"+help.getAssetvalue()+"','"+help.getHostname()+"'," +
				"'"+help.getIPNumber()+"','"+help.getAdloginname()+"','"+help.getServer()+"','"+help.getRequiredfoldername()+"','"+help.getAccesstype()+"','"+help.getReason()+"','In Process','No','"+pendingApprovers+"','"+help.getReqPriority()+"','Open','"+help.getFromlocationId()+"','"+user.getEmployeeNo()+"','','"+itengineerid+"','"+locID+"')";
				
				 i=ad.SqlExecuteUpdate(insertAduser);
				 if(i>0){
					 help.setMessage("Request has been submitted successfully with Request No="+maxReqno);
					 resetvalues(mapping, form, request, response);
					 
					 if(PlantheadAvailable==false)
					 {
					 if(HODAvailable==false)
						{
					 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
								"'"+dateNow1+"','Pending','','"+itengineerid+"','','"+user.getEmployeeNo()+"','"+type+"')";
								 i=ad.SqlExecuteUpdate(insert);
								 int j = email.sendMailToApprover(request, maxReqno,type,itengineerid,"");
						}
					 
					 if(HODAvailable==true)
						{
						 
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert);
											 int j = email.sendMailToApprover(request, maxReqno,type,HODPresentId,"");
						 
						}
					 
					 }
					 if(PlantheadAvailable==true)
					 {
						 String insert="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
									"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+maxReqno+"','IT Request','"+empno+"'," +
											"'"+dateNow1+"','Pending','','"+plantheadid+"','','"+user.getEmployeeNo()+"','"+type+"')";
											 i=ad.SqlExecuteUpdate(insert);
											 int j = email.sendMailToApprover(request, maxReqno,type,plantheadid,"");
					 }
					 
							
				 }
				 else
				 {
					 help.setMessage1("Error...When request Submittion.Please check");
				 }
				 forwardtype="displayFolderAccessform";
			}
			
			
			String chkdate1="";
			String chkdate="";
			
			//Approver Details
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			int jk=0;
			
			
			//Approvers List
			
			 hodpresent=0;
			 hodpresentinIt=0;
			 HODPresentId="";
			 HODPresentname="";
			
			String HODPresentdept="";
			String HODPresentdesg="";
			
			 itengineerid="";
			 itengineername="";
			 HOD="";
	         HODAvailable=false;
			
			 //chk if he has Hod
			  chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
			 ResultSet rs33= ad.selectQuery(chkhod);
			 try {
				while(rs33.next())
				 {
					 hodpresent=rs33.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			//get hod details
			
		/*	 if(hodpresent==0)
			 {
				 help.setMessage1("Approver Manager Is Not Assigned For The Employee ..Contact Admin");
				 return mapping.findForward("displayform");
			 }*/
			 
			 
			
			 
		
			if(hodpresent>0){
			String gethod="select ess.ApproverId,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from ESS_Approvers  as ess,emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg  " +
					" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId and emp.DPTID=dep.DPTID and emp.DSGID=desg.DSGID";
			 ResultSet rs23= ad.selectQuery(gethod);
			 
			 try {
				if(rs23.next())
				 {
				
					HODPresentId=rs23.getString("ApproverId");
					HODPresentname=rs23.getString("EMP_FULLNAME");
					
					HODPresentdept=rs23.getString("DPTSTXT");
					HODPresentdesg=rs23.getString("DSGSTXT");
					
				
				 }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
			
			//chk if he is present in It approver
			  chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
			 ResultSet rs113= ad.selectQuery(chkhodinit);
			 try {
				while(rs113.next())
				 {
					hodpresentinIt=rs113.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			 
			     approver="";
				 lApprover="";
				 approvermail="";
				 pendingApprovers="";
				
				 if(plantheadid.equalsIgnoreCase(""))
					{		
					if(hodpresent>0 && hodpresentinIt==0 )
					{
								checkStatus=1;
								ApprovalsForm apprvershod=new ApprovalsForm();
								apprvershod.setPriority("1");
								
								apprvershod.setEmployeeCode(HODPresentId);
								apprvershod.setEmployeeName(HODPresentname);
								apprvershod.setDepartment(HODPresentdept);
								apprvershod.setDesignation(HODPresentdesg);
							
								listApprers.add(apprvershod);
							 jk=1;
						
						
					}
					}
					
					
					if(!plantheadid.equalsIgnoreCase(""))
					{
						if(!(type.equalsIgnoreCase("Active Directory User Creation")||type.equalsIgnoreCase("Active Directory User Deletion")||type.equalsIgnoreCase("Active Directory User Transfer")))
					//	if(!(type.equalsIgnoreCase("Active Directory User Creation")||type.equalsIgnoreCase("Active Directory User Deletion")||type.equalsIgnoreCase("Active Directory User Transfer")||type.equalsIgnoreCase("Email ID Create Request")))
							
						{
						checkStatus=1;
						ApprovalsForm apprvershod=new ApprovalsForm();
						apprvershod.setPriority("1");
						
						apprvershod.setEmployeeCode(plantheadid);
						apprvershod.setEmployeeName(plantheadname);
						apprvershod.setDepartment(plantheaddep);
						apprvershod.setDesignation(plantheaddesg);
					
						listApprers.add(apprvershod);
					 jk=1;
						}else
						{
							if(hodpresent>0 && hodpresentinIt==0 )
							{
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("1");
										
										apprvershod.setEmployeeCode(HODPresentId);
										apprvershod.setEmployeeName(HODPresentname);
										apprvershod.setDepartment(HODPresentdept);
										apprvershod.setDesignation(HODPresentdesg);
									
										listApprers.add(apprvershod);
									 jk=1;
								
								
							}
							
						}
						
					}
					
					
					if(!type.equalsIgnoreCase("Active Directory User Creation"))
				//	if(!(type.equalsIgnoreCase("Active Directory User Creation")||type.equalsIgnoreCase("Email ID Create Request")))
							{
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from IT_Approvers as it,emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg  	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  "
					+ "and it.Req_Type='"+type+"' and emp.DPTID=dep.DPTID and emp.DSGID=desg.DSGID group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					
						
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
				
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					apprvers.setDepartment(rsApprDetails.getString("DPTSTXT"));
					apprvers.setDesignation(rsApprDetails.getString("DSGSTXT"));
					
					listApprers.add(apprvers);
					
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from IT_Approvers as it,emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg"
					+ "  where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1 and emp.DPTID=dep.DPTID and emp.DSGID=desg.DSGID"
					+ "   group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
						
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							apprvers.setDepartment(rsApprDetails1.getString("DPTSTXT"));
							apprvers.setDesignation(rsApprDetails1.getString("DSGSTXT"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			
					}
					else
					{
						
						//IT ENGINEER DETAIlS
						
						
						String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from IT_Approvers as it,emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg"
						+ "  where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1 and emp.DPTID=dep.DPTID and emp.DSGID=desg.DSGID"
						+ "   group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT order by Priority";
						ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
						
							try {
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
								
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
									apprvers.setDepartment(rsApprDetails1.getString("DPTSTXT"));
									apprvers.setDesignation(rsApprDetails1.getString("DSGSTXT"));

								listApprers.add(apprvers);
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						request.setAttribute("approverDetails", listApprers);
					
				
				
						
					}
			
			
		}
	  return mapping.findForward(forwardtype);
	}
	
	public ActionForward searchForApprovers(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	
	
	String sTxt = request.getParameter("searchText").trim();
	String reqFieldName=request.getParameter("reqFieldName");
	HelpDeskForm  f=(HelpDeskForm)form;
	
	f.setSearchText(sTxt);
	MailInboxAction mAction = new MailInboxAction();
	LinkedList searchList=new LinkedList();
	try{
		
		

		String searchQuery="select dpt.DPTSTXT,* from emp_official_info as emp,DEPARTMENT as dpt where dpt.DPTID=emp.DPTID and (EMP_FULLNAME like '%"+sTxt+"%' or EMAIL_ID like '%"+sTxt+"%' or PERNR like'%"+sTxt+"%') and Active=1";
		ResultSet rs=ad.selectQuery(searchQuery);
	 int i=1;
		while(rs.next()) {
			
			ESSApproverForm emp=new ESSApproverForm();
			emp.setEmp(rs.getString("EMP_FULLNAME"));
			emp.setEmployeeNo(rs.getString("PERNR"));
			emp.setDepartment(rs.getString("DPTSTXT"));
			emp.setReqFieldName(reqFieldName);
		   searchList.add(emp);
		   if(i==100)
			  {
				  break;
			  }
		i++;
		}
		
	
		if(searchList.size()==0)
		{
		String a[]=null;		
		String actualtext="";
        if(sTxt.contains(" "))
        {
        	a=sTxt.split(" ");
        
		 for(int h=0;h<a.length;h++)
        {
             if(!a[h].equalsIgnoreCase(""))
			 actualtext=actualtext+"EMP_FULLNAME like '%"+a[h]+"%' or EMAIL_ID like '%"+a[h]+"%' or PERNR like'%"+a[h]+"%' or ";
        }
		 
		 
		 actualtext=actualtext.substring(0, actualtext.length()-3);
		
		 
        }
        
        else
        {
        	actualtext="EMP_FULLNAME like '%"+sTxt+"%' or EMAIL_ID like '%"+sTxt+"%' or PERNR like'%"+sTxt+"%'";
        }
        
       
      
		String searchQuery1="select dpt.DPTSTXT,* from emp_official_info as emp,DEPARTMENT as dpt where dpt.DPTID=emp.DPTID and"
				+ " ("+actualtext+") "
						+ "and Active=1";
		ResultSet rs1=ad.selectQuery(searchQuery1);
	 int i1=1;
		while(rs1.next()) {
			
			ESSApproverForm emp=new ESSApproverForm();
			emp.setEmp(rs1.getString("EMP_FULLNAME"));
			emp.setEmployeeNo(rs1.getString("PERNR"));
			emp.setDepartment(rs1.getString("DPTSTXT"));
			emp.setReqFieldName(reqFieldName);
		   searchList.add(emp);
		   if(i1==100)
			  {
				  break;
			  }
		i1++;
		}
		
		}
	}
	
	catch (SQLException sqle) { System.out.println("SQLException @ searching User Details"); sqle.printStackTrace();}
	
	request.setAttribute("SearchUserDetails", searchList);
	
	return mapping.findForward("searchemployee");
	}
	
	public ActionForward displaynewrequestform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		
		 /*com.sun.security.auth.module.NTSystem nsys = new com.sun.security.auth.module.NTSystem(); 
	       System.out.println(nsys.getName()); */
	HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		HelpDeskForm help = (HelpDeskForm) form;
		help.setRequestType(help.getRequestType());
		

		help.setUsage(help.getUsage());
		help.setEmployeeno("");
		help.setUsername(user.getDepartmentId());
		
		return mapping.findForward("displayform");
	}
	
	public ActionForward newIncidentform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		return mapping.findForward("newincidentform");
	}
	
	public ActionForward newrequestform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		
		HelpDeskForm help = (HelpDeskForm) form;
		HttpSession session=request.getSession();
		
		
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		String forwardtype="";
		String localhostname="";
		String ipaddress="";
		String username="";
		try {
			localhostname = (InetAddress.getLocalHost()).getCanonicalHostName();
			ipaddress = (InetAddress.getLocalHost()).getHostAddress();
			username=(InetAddress.getLocalHost()).getHostName();
			
		} catch (UnknownHostException e1) {
			
			e1.printStackTrace();
		}
		
		ipaddress = request.getHeader("X-FORWARDED-FOR");   // proxy
		if(ipaddress==null)
		{
			ipaddress = request.getRemoteAddr();
		}
		
		String type=help.getRequestType();
		help.setRequestType(type);
		
		String empno="";
		int k=0;
		
	
		empno=help.getEmployeeno();
		if(empno.equalsIgnoreCase(""))
		{
		 empno=user.getEmployeeNo();
		}
		
		//chk valid employe no.
		
		String emp="Select Count(*) from emp_official_info where PERNR='"+empno+"'";
		ResultSet rsss= ad.selectQuery(emp);
		try {
			while(rsss.next())
			{
				 k=rsss.getInt(1);
			}
		} catch (SQLException e1) {
			
			
			e1.printStackTrace();
		}
		
		if(k==0)
		{
			help.setMessage1("Please Enter Valid Employee Number");
			return mapping.findForward("displayform");
		}
		
		String chkdate1="";
		String chkdate="";
		
		//Approver Details
		int  checkStatus=0;
		LinkedList listApprers=new LinkedList();
		int jk=0;
		
		
		//Approvers List
		
		int hodpresent=0;
		int hodpresentinIt=0;
		String HODPresentId="";
		String HODPresentname="";
		
		String HODPresentdept="";
		String HODPresentdesg="";
		
		String itengineerid="";
		String itengineername="";
		String HOD="";
        boolean HODAvailable=false;
		
		 //chk if he has Hod
		 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
		 ResultSet rs33= ad.selectQuery(chkhod);
		 try {
			while(rs33.next())
			 {
				 hodpresent=rs33.getInt(1);
			 }
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}

			ArrayList desgList=new ArrayList();
			ArrayList desgLabelList=new ArrayList();
			ResultSet rs16 = ad.selectQuery("select dsgid," +
					"DSGLTXT from DESIGNATION order by DSGLTXT"); 
					try {
						while(rs16.next()) {
							desgList.add(rs16.getString("dsgid"));
							desgLabelList.add(rs16.getString("DSGLTXT"));
						}
						rs16.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						
						e.printStackTrace();
					}
					
					help.setDesgList(desgList);
					help.setDesgLabelList(desgLabelList);
					
					
					ArrayList deptList=new ArrayList();
					ArrayList deptLabelList=new ArrayList();
					ResultSet rs161 = ad.selectQuery("select dptid," +
							"DPTLTXT from DEPARTMENT order by DPTLTXT "); 
							try {
								while(rs161.next()) {
									deptList.add(rs161.getString("dptid"));
									deptLabelList.add(rs161.getString("DPTLTXT"));
								}
								rs16.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							help.setDeptList(deptList);
							help.setDeptLabelList(deptLabelList);
	
		if(hodpresent>0){
		String gethod="select ess.ApproverId,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from ESS_Approvers  as ess,emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg  " +
				" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId and emp.DPTID=dep.DPTID and emp.DSGID=desg.DSGID and ess.Priority=1";
		 ResultSet rs23= ad.selectQuery(gethod);
		 
		 try {
			if(rs23.next())
			 {
			
				HODPresentId=rs23.getString("ApproverId");
				
				if(HODPresentId.equalsIgnoreCase("106213"))
				{
					
				}
				HODPresentname=rs23.getString("EMP_FULLNAME");
				
				HODPresentdept=rs23.getString("DPTSTXT");
				HODPresentdesg=rs23.getString("DSGSTXT");
				
			
			 }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		}
		
		//Plant head
		String plantheadid="";
		String plantheadname="";
		String plantheaddep="";
		String plantheaddesg="";
		
		String a="";
		String userx="select loc.LOCID from Location as loc,emp_official_info as emp where loc.LOCATION_CODE=emp.LOCID and emp.PERNR='"+empno+"' ";
		ResultSet userrs=ad.selectQuery(userx);
		try {
			while(userrs.next())
			 {				
				a=userrs.getString("LOCID");
			 }
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		if(!a.equalsIgnoreCase("16"))
		{
			if(!(type.equalsIgnoreCase("Email ID Create Request")||type.equalsIgnoreCase("Email ID Change Request")||type.equalsIgnoreCase("Email ID Delete Request")))					
			{
			String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.active=1 and  emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
			ResultSet pl=ad.selectQuery(plant);
			try {
				while(pl.next())
				 {
					plantheadid=pl.getString("pernr");
					plantheadname=pl.getString("EMP_FULLNAME");
					plantheaddep=pl.getString("DPTSTXT");
					plantheaddesg=pl.getString("DSGSTXT");
				 
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}else{
				String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from Emial_IdApprvr pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where pl.Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
				ResultSet pl=ad.selectQuery(plant);
				
				try {
					while(pl.next())
					 {
						if(pl.getString("pernr")==""){
							request.setAttribute("message","Plant Head Notavailable!");
							return mapping.findForward("displayiFrameSession");
						}
						plantheadid=pl.getString("pernr");
						plantheadname=pl.getString("EMP_FULLNAME");
						plantheaddep=pl.getString("DPTSTXT");
						plantheaddesg=pl.getString("DSGSTXT");
					 }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
		
		
		//chk if he is present in It approver
		 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+a+"'";
		 ResultSet rs113= ad.selectQuery(chkhodinit);
		 try {
			while(rs113.next())
			 {
				hodpresentinIt=rs113.getInt(1);
			 }
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		 
		    String approver="";
			String lApprover="";
			String approvermail="";
			String pendingApprovers="";
			
			if(plantheadid.equalsIgnoreCase(""))
			{		
			if(hodpresent>0 && hodpresentinIt==0 )
			{
						checkStatus=1;
						ApprovalsForm apprvershod=new ApprovalsForm();
						apprvershod.setPriority("1");
						
						apprvershod.setEmployeeCode(HODPresentId);
						apprvershod.setEmployeeName(HODPresentname);
						apprvershod.setDepartment(HODPresentdept);
						apprvershod.setDesignation(HODPresentdesg);
					
						listApprers.add(apprvershod);
					 jk=1;
				
				
			}
			}
			
			
			if(!plantheadid.equalsIgnoreCase(""))
			{
				
		//if(!(type.equalsIgnoreCase("Active Directory User Creation")||type.equalsIgnoreCase("Active Directory User Deletion")||type.equalsIgnoreCase("Active Directory User Transfer")))
		//if(!(type.equalsIgnoreCase("Active Directory User Creation")||type.equalsIgnoreCase("Active Directory User Deletion")||type.equalsIgnoreCase("Active Directory User Transfer")||type.equalsIgnoreCase("Email ID Create Request")))
		//if(!(type.equalsIgnoreCase("Active Directory User Creation")||type.equalsIgnoreCase("Active Directory User Deletion")||type.equalsIgnoreCase("Active Directory User Transfer")||type.equalsIgnoreCase("Email ID Create Request")||type.equalsIgnoreCase("Email ID Change Request")||type.equalsIgnoreCase("Email ID Delete Request")))					
				if(!(type.equalsIgnoreCase("Active Directory User Creation")||type.equalsIgnoreCase("Active Directory User Deletion")||type.equalsIgnoreCase("Active Directory User Transfer")))
		
		{
				checkStatus=1;
				ApprovalsForm apprvershod=new ApprovalsForm();
				apprvershod.setPriority("1");
				
				apprvershod.setEmployeeCode(plantheadid);
				apprvershod.setEmployeeName(plantheadname);
				apprvershod.setDepartment(plantheaddep);
				apprvershod.setDesignation(plantheaddesg);
			
				listApprers.add(apprvershod);
			 jk=1;
				}else
				{
					if(hodpresent>0 && hodpresentinIt==0 )
					{
								checkStatus=1;
								ApprovalsForm apprvershod=new ApprovalsForm();
								apprvershod.setPriority("1");
								
								apprvershod.setEmployeeCode(HODPresentId);
								apprvershod.setEmployeeName(HODPresentname);
								apprvershod.setDepartment(HODPresentdept);
								apprvershod.setDesignation(HODPresentdesg);
							
								listApprers.add(apprvershod);
							 jk=1;
						
						
					}
					
				}
				
			}
			
			if(!type.equalsIgnoreCase("Active Directory User Creation"))
			//if(!(type.equalsIgnoreCase("Active Directory User Creation")||type.equalsIgnoreCase("Email ID Create Request")))
					
			{
		String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from IT_Approvers as it,emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg  	where it.LOCATION_ID='"+a+"'  and it.Approver_Id=emp.PERNR  "
				+ "and it.Req_Type='"+type+"' and emp.DPTID=dep.DPTID and emp.DSGID=desg.DSGID group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT order by Priority";
		ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
		try {
			while(rsApprDetails.next())
			{
				
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				
					
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				if(jk==1){
					
					apprvers.setPriority(Integer.toString(listApprers.size()+1));
				}
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				
			
				apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
				
				apprvers.setDepartment(rsApprDetails.getString("DPTSTXT"));
				apprvers.setDesignation(rsApprDetails.getString("DSGSTXT"));
				
				listApprers.add(apprvers);
				
			}
			
				//IT ENGINEER DETAIlS
				
			
				
				String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from IT_Approvers as it,emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg"
				+ "  where it.LOCATION_ID='"+a+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1 and emp.DPTID=dep.DPTID and emp.DSGID=desg.DSGID"
				+ "   group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT order by Priority";
				ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
				
					while(rsApprDetails1.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
						
						apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
						String empCode1=rsApprDetails1.getString("Approver_Id");
						
					
						apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
						apprvers.setDepartment(rsApprDetails1.getString("DPTSTXT"));
						apprvers.setDesignation(rsApprDetails1.getString("DSGSTXT"));
				
					listApprers.add(apprvers);
					}
			
				request.setAttribute("approverDetails", listApprers);
			
		} 
		
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
			}
			
			else
			{
//IT ENGINEER DETAIlS
				String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from IT_Approvers as it,emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg"
				+ "  where it.LOCATION_ID='"+a+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1 and emp.DPTID=dep.DPTID and emp.DSGID=desg.DSGID"
				+ "   group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT order by Priority";
				ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
				
					try {
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
						
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							apprvers.setDepartment(rsApprDetails1.getString("DPTSTXT"));
							apprvers.setDesignation(rsApprDetails1.getString("DSGSTXT"));

						listApprers.add(apprvers);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
				request.setAttribute("approverDetails", listApprers);
			}
		
		
		
		///data Entry Form
		if(type.equalsIgnoreCase("Issues"))
		{
		
			 
		
			
			help.setEmployeeno(empno);
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
				    help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayIssues";
		}
		
		/*if(type.equalsIgnoreCase("Transport Request"))
		{
			
			help.setEmployeeno(empno);
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
				    help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displaytransportRequestform";
		}*/
		
		
		if(type.equalsIgnoreCase("Qms Application Access"))
		{
			
			ArrayList locationID = new ArrayList();
			ArrayList locationName = new ArrayList();
			try {

				String getLocations = "select * from Location";
				ResultSet rsGetLocation = ad.selectQuery(getLocations);
				while (rsGetLocation.next()) {
					locationID.add(rsGetLocation.getString("LOCATION_CODE"));
					locationName.add(rsGetLocation.getString("LOCATION_CODE") + "-"
							+ rsGetLocation.getString("LOCNAME"));
				}
			   }catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			help.setLocationIdList(locationID);
			help.setLocationLabelList(locationName);			
			help.setEmployeeno(empno);
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
				    help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayNewQmsform";
		}
		
		if(type.equalsIgnoreCase("New IT Spares Request"))
		{
			
			help.setEmployeeno(empno);
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
				    help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			
			 ArrayList spareLabelList=new ArrayList();
			 
			String as="select * from it_spares where type='SPARES'";
			ResultSet rssd= ad.selectQuery(as);
			
			try {
				while(rssd.next())
				{
					spareLabelList.add(rssd.getString("Description"));
				}
			
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			
			help.setSpareLabelList(spareLabelList);
			request.setAttribute("save", "save");
			forwardtype="displayNewITSpareform";
		}
		
		if(type.equalsIgnoreCase("New IT Asset Request"))
		{
			
			help.setEmployeeno(empno);
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
				    help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayNewITAssetform";
		}
		
		
		if(type.equalsIgnoreCase("Active Directory User Creation"))
		{
			LinkedList Adusercreation=new LinkedList();
			
		
			
		
			
			help.setEmployeeno(empno);
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
				    help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayAduserCreationform";
		}
		
		if(type.equalsIgnoreCase("Active Directory User Deletion"))
		{
			LinkedList Aduserdeletion=new LinkedList();

         	help.setEmployeeno(empno);
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					 help.setEmpEmailID(rs.getString("EMAIL_ID"));
				  
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			request.setAttribute("save", "save");
			forwardtype="displayAduserDeletionform";
		}
		
		if(type.equalsIgnoreCase("Active Directory User Transfer"))
		{
			LinkedList Adusertransfer=new LinkedList();
			
			
			help.setEmployeeno(empno);
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
				
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					 help.setEmpEmailID(rs.getString("EMAIL_ID"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
				
			try {
				while(rs11.next()) {
					locationList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
					locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);
			
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
			try {
				while(rs12.next()){
					
					deparmentList.add(rs12.getString("DPTSTXT"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			help.setDepartmentList(deparmentList);
	
			
			
			request.setAttribute("save", "save");
			forwardtype="displayAduserTransferform";
		}
		
		
		if(type.equalsIgnoreCase("Email ID Create Request"))
		{
			LinkedList LotusIDCreation=new LinkedList();
		
			help.setEmployeeno(empno);
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE,emp.DOJ " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setJoiningDate(EMicroUtils.display(rs.getDate("DOJ")));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					 help.setEmpEmailID(rs.getString("EMAIL_ID"));
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			request.setAttribute("save", "save");
			request.setAttribute("LotusIDCreation", LotusIDCreation);
			if(!(empno.equalsIgnoreCase(user.getEmployeeNo())))
			{
				request.setAttribute("othr", "othr");
				help.setAnysplreq("ok");
			}
			
			forwardtype="displayLotusIDCreationform";
		}
		
		if(type.equalsIgnoreCase("Email ID Change Request"))
		{
			LinkedList LotusIDChange=new LinkedList();
			
			help.setEmployeeno(empno);
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					 help.setEmpEmailID(rs.getString("EMAIL_ID"));
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			request.setAttribute("save", "save");
			request.setAttribute("LotusIDChange", LotusIDChange);
			forwardtype="displayLotusIDChangeform";
		}
		
		if(type.equalsIgnoreCase("Email ID Delete Request"))
		{

			LinkedList LotusIDDeletion=new LinkedList();
		
			help.setEmployeeno(empno);
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					 help.setEmpEmailID(rs.getString("EMAIL_ID"));
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			request.setAttribute("save", "save");
			request.setAttribute("LotusIDDeletion", LotusIDDeletion);
			forwardtype="displayLotusIDDeletionform";
		
			
		}
		
		if(type.equalsIgnoreCase("Internet Access Request"))
		{
			LinkedList internet=new LinkedList();
		
			help.setEmployeeno(empno);
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					 help.setEmpEmailID(rs.getString("EMAIL_ID"));
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			request.setAttribute("save", "save");
			request.setAttribute("Internet", internet);
			forwardtype="displayInternetAccessform";
		}
		
		if(type.equalsIgnoreCase("External Drives Access Request"))
		{
			LinkedList externaldrives=new LinkedList();
			
			help.setEmployeeno(empno);
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					 help.setEmpEmailID(rs.getString("EMAIL_ID"));
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			request.setAttribute("save", "save");
			request.setAttribute("externaldrives", externaldrives);
			forwardtype="displayexternaldrivesform";
		}
		
		if(type.equalsIgnoreCase("FTP Access Request"))
		{
			LinkedList ftp=new LinkedList();
			
			help.setEmployeeno(empno);
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					 help.setEmpEmailID(rs.getString("EMAIL_ID"));
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			request.setAttribute("save", "save");
			request.setAttribute("ftp", ftp);
			forwardtype="displayFtpAccessform";
		}
		
		if(type.equalsIgnoreCase("File Server Access Request"))
		{
			LinkedList folder=new LinkedList();
			
			help.setEmployeeno(empno);
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
					 help.setEmpEmailID(rs.getString("EMAIL_ID"));
				}
			
			
				ResultSet rs11 = ad.selectQuery("select LOCID," +
						"LOCNAME,location_code from location");
						ArrayList locationList=new ArrayList();
						ArrayList locationLabelList=new ArrayList();
							
						try {
							while(rs11.next()) {
								locationList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
								locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						help.setLocationIdList(locationList);
						help.setLocationLabelList(locationLabelList);
			
			
			
			
			
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			
			
			
			request.setAttribute("save", "save");
			request.setAttribute("folder", folder);
			forwardtype="displayFolderAccessform";
		}
		
		
		
		return mapping.findForward(forwardtype);
	}
	
	
	
		
	

}
