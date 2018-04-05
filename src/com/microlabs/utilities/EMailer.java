package com.microlabs.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.action.LeaveAction;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.ess.form.MaterialCodeRequestForm;
import com.microlabs.ess.form.TravelRequestForm;
import com.microlabs.it.form.HelpDeskForm;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.main.db.MainDao;
import com.microlabs.main.form.MailInboxForm;

public class EMailer {
	EssDao ad = new EssDao();

	/*
	 * public static void main(String[] args) throws IOException, ParseException
	 * { // 0. Specify the analyzer for tokenizing text. // The same analyzer
	 * should be used for indexing and searching StandardAnalyzer analyzer = new
	 * StandardAnalyzer(Version.LUCENE_20);
	 * 
	 * // 1. create the indexOnduty Details Directory index = new
	 * RAMDirectory();
	 * 
	 * IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_20,
	 * analyzer);
	 * 
	 * IndexWriter w = new IndexWriter(index, config); addDoc(w,
	 * "Lucene in Action", "193398817"); addDoc(w, "Lucene for Dummies",
	 * "55320055Z"); addDoc(w, "Managing Gigabytes", "55063554A"); addDoc(w,
	 * "The Art of Computer Science", "9900333X"); w.close();
	 * 
	 * // 2. query String querystr = args.length > 0 ? args[0] : "lucene";
	 * 
	 * // the "title" arg specifies the default field to use // when no field is
	 * explicitly specified in the query. Query q = new
	 * org.apache.lucene.queryParser.QueryParser(Version.LUCENE_20, "title",
	 * analyzer).parse(querystr);
	 * 
	 * // 3. search int hitsPerPage = 10; IndexReader reader =
	 * DirectoryReader.open(index); IndexSearcher searcher = new
	 * IndexSearcher(reader); TopScoreDocCollector collector =
	 * TopScoreDocCollector.create(hitsPerPage, true); searcher.search(q,
	 * collector); ScoreDoc[] hits = collector.topDocs().scoreDocs;
	 * 
	 * // 4. display results System.out.println("Found " + hits.length +
	 * " hits."); for(int i=0;i<hits.length;++i) { int docId = hits[i].doc;
	 * Document d = searcher.doc(docId); System.out.println((i + 1) + ". " +
	 * d.get("isbn") + "\t" + d.get("title")); }
	 * 
	 * // reader can only be closed when there // is no need to access the
	 * documents any more. reader.close(); }
	 * 
	 * private static void addDoc(IndexWriter w, String title, String isbn)
	 * throws IOException { Document doc = new Document(); doc.add(new
	 * TextField("title", title, Field.Store.YES));
	 * 
	 * // use a string field for isbn because we don't want it tokenized
	 * doc.add(new StringField("isbn", isbn, Field.Store.YES));
	 * w.addDocument(doc); }
	 */
	public int sendMailToApproverForCancel(HttpServletRequest request,
			String approvermail, String Req_Id, String requestFrom) {
		MailInboxForm mailForm = new MailInboxForm();
		MailInboxAction mailAction = new MailInboxAction();
		HttpSession session = request.getSession();

		UserInfo user = (UserInfo) session.getAttribute("user");
		String approverID = "";
		String approverEmailID = "";
		String startDur = "";
		String endDur = "";
		String leaveType = "";
		String startDate = "";
		String endDate = "";
		String reasonType = "", pApprover = "", noOfDays = "", submitDate = "";
		String getApproverDetails = "select emp.APPMGR,appr.EMAIL_ID from emp_official_info as emp,emp_official_info as appr where "
				+ "emp.PERNR='"
				+ user.getEmployeeNo()
				+ "' and emp.APPMGR=appr.PERNR ";
		try {
			ResultSet rsAppr = ad.selectQuery(getApproverDetails);
			while (rsAppr.next()) {
				approverID = rsAppr.getString("APPMGR");
				approverEmailID = rsAppr.getString("EMAIL_ID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			String leavedetails = "select * from leave_details where user_id="
					+ user.getEmployeeNo() + " and Req_Id='" + Req_Id + "'";
			ResultSet ldRS = ad.selectQuery(leavedetails);
			while (ldRS.next()) {
				leaveType = ldRS.getString("leave_type");
				if (leaveType.equalsIgnoreCase("1")) {
					leaveType = "Casual";
				}
				if (leaveType.equalsIgnoreCase("2")) {
					leaveType = "Sick";
				}
				if (leaveType.equalsIgnoreCase("3")) {
					leaveType = "Privilege";
				}
				if (leaveType.equalsIgnoreCase("4")) {
					leaveType = "Loss Of Pay";
				}
				if (leaveType.equalsIgnoreCase("5")) {
					leaveType = "Maternity";
				}
				startDate = EMicroUtils.display1(ldRS.getDate("start_date"));
				startDur = ldRS.getString("start_duration");
				if (startDur.equalsIgnoreCase("FD")) {
					startDur = "Full Day";
				}
				if (startDur.equalsIgnoreCase("FH")) {
					startDur = "First Half";
				}
				if (startDur.equalsIgnoreCase("SH")) {
					startDur = "Second Half";
				}
				endDate = EMicroUtils.display1(ldRS.getDate("end_date"));
				endDur = ldRS.getString("end_duration");
				if (endDur.equalsIgnoreCase("FD")) {
					endDur = "Full Day";
				}
				if (endDur.equalsIgnoreCase("FH")) {
					endDur = "First Half";
				}
				if (endDur.equalsIgnoreCase("SH")) {
					endDur = "Second Half";
				}
				reasonType = ldRS.getString("reasonType");

				pApprover = ldRS.getString("Pending_Approver");

				noOfDays = ldRS.getString("no_of_days");

				submitDate = ldRS.getString("submit_date");
			}

			mailForm.setToAddress(approverEmailID);
			System.out.println("m -> " + approverEmailID);
			mailForm.setccAddress("");
			mailForm.setbccAddress("");

			boolean existStatus = false;
			String dataFlowType = "";
			String checkESSApproverList = "select count(*) from ESS_Approvers where employeeNumber='"
					+ user.getEmployeeNo() + "' and essType='Leave'";
			ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
			while (rsCheckInEss.next()) {
				int number = rsCheckInEss.getInt(1);
				if (number == 0) {
					existStatus = false;
				} else {
					existStatus = true;
				}
			}
			if (existStatus == true) {
				String getDataFlowType = "select * from ESS_Approvers where employeeNumber='"
						+ user.getEmployeeNo() + "' and essType='Leave'";
				ResultSet rsDataFlowType = ad.selectQuery(getDataFlowType);
				while (rsDataFlowType.next()) {
					dataFlowType = rsDataFlowType.getString("reqType");
				}
				if (dataFlowType.equalsIgnoreCase("sequential")) {
					existStatus = false;
				}
				if (dataFlowType.equalsIgnoreCase("parallel")) {
					String toAddress = "";
					String approver1 = "";
					String parallelApprover1 = "";
					String parallelApprover2 = "";
					String empIDS = "";
					String empEmail = "";

					String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
							+ user.getEmployeeNo()
							+ "' and essType='Leave' and reqType='parallel'  and  Priority=1";
					ResultSet rsApproverDsg = ad.selectQuery(getApproverDesig);
					while (rsApproverDsg.next()) {
						approver1 = rsApproverDsg.getString("ApproverId");
						if (!(approver1.equalsIgnoreCase(""))) {
							empIDS = rsApproverDsg.getString("ApproverId");
						}
						parallelApprover1 = rsApproverDsg
								.getString("Parallel_Approver1");
						if (!(parallelApprover1.equalsIgnoreCase(""))) {
							empIDS = empIDS
									+ ","
									+ rsApproverDsg
											.getString("Parallel_Approver1");
						}
						parallelApprover2 = rsApproverDsg
								.getString("Parallel_Approver2");
						if (!(parallelApprover2.equalsIgnoreCase(""))) {
							empIDS = empIDS
									+ ","
									+ rsApproverDsg
											.getString("Parallel_Approver2");
						}
					}
					empIDS = empIDS.substring(0, (empIDS.length() - 0));
					String getEmpIDs = "select EMAIL_ID from emp_official_info where PERNR in ("
							+ empIDS + ")";
					ResultSet rsEMPIDS = ad.selectQuery(getEmpIDs);
					int ii = 0;
					while (rsEMPIDS.next()) {
						empEmail = empEmail + ","
								+ rsEMPIDS.getString("EMAIL_ID");
					}
					empEmail = empEmail.substring(1, empEmail.length());
					mailForm.setSubject("Leave Request Self Cancelled  From "
							+ user.getFullName());
					String desc = "Requested By : " + user.getFullName()
							+ "</br>";
					desc = desc + "I Cancelled My Leave Request.</br></br>";
					desc = desc
							+ "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";

					desc = desc
							+ "<html><body><table><tr><th colspan=4 ><center>Leave Details</center></th></tr>";
					desc = desc + "<tr><th align=left>Leave Type</th><td>"
							+ leaveType + "</td><th>No.Of Days</th><td>"
							+ noOfDays + "</tr>";
					desc = desc + "<tr><th align=left>From</th><td>"
							+ startDate
							+ "</td><th align=left>Duration</th><td>"
							+ startDur + "</td></tr>";
					desc = desc + "<tr><th align=left>To</th><td>" + endDate
							+ "<th align=left>Duration</th><td>" + endDur
							+ "</td></tr>";
					desc = desc
							+ "<tr><th align=left>Status</th><td colspan=4>Self Cancelled</td></tr>";
					desc = desc + "<tr><th>Reason Type</th><td colspan=3>"
							+ reasonType + "</tr></table></br></br></br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
					mailForm.setDescription(desc);

					mailAction.mailSendToParallelApprovers(request, mailForm,
							"request", empEmail);
				}
			}
			if (existStatus == false) {
				mailForm.setSubject("Leave Request Self Cancelled  From "
						+ user.getFullName());
				String desc = "Requested By : " + user.getFullName() + "</br>";
				desc = desc + "I Cancelled My Leave Request.</br></br>";
				desc = desc
						+ "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";

				desc = desc
						+ "<html><body><table><tr><th colspan=4 ><center>Leave Details</center></th></tr>";
				desc = desc + "<tr><th align=left>Leave Type</th><td>"
						+ leaveType + "</td><th>No.Of Days</th><td>" + noOfDays
						+ "</tr>";
				desc = desc + "<tr><th align=left>From</th><td>" + startDate
						+ "</td><th align=left>Duration</th><td>" + startDur
						+ "</td></tr>";
				desc = desc + "<tr><th align=left>To</th><td>" + endDate
						+ "<th align=left>Duration</th><td>" + endDur
						+ "</td></tr>";
				desc = desc
						+ "<tr><th align=left>Status</th><td colspan=4>Self Cancelled</td></tr>";
				desc = desc + "<tr><th>Reason Type</th><td colspan=3>"
						+ reasonType + "</tr></table></br></br></br>";
				desc = desc
						+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
						+ "https://portal.microlabs.co.in"
						+ ">EMicro Portal</a> .</span> ";
				desc = desc + "</body></html>";
				mailForm.setDescription(desc);
				mailAction.mailSendToRecipient(request, mailForm, "request");
				int success = 1;
			}
		} catch (ServletException se) {
			System.out.println("ServletException @ sending leave request..");
			se.printStackTrace();
		} catch (MessagingException se) {
			System.out.println("MessagingException @ sending leave request..");
			se.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("IOException @ sending leave request..");
			ioe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("SQLException @ sending  request..");
			sqle.printStackTrace();
		}
		return 1;
	}
	
	//travel request
	 public int sendMailToRequester(HttpServletRequest request, String approvermail, String Req_Id, String requestFrom)
	  {int success=0;
		HttpSession session=request.getSession();
		/*
		UserInfo user=(UserInfo)session.getAttribute("user");
		String fullName = user.getFullName();*/
		MailInboxForm mailForm = new MailInboxForm();
		MailInboxForm mailForm1 = new MailInboxForm();
		MailInboxAction mailAction = new MailInboxAction();
		TravelRequestForm typ = new TravelRequestForm();
		/*int userId=user.getId();*/
		
		
		//getting details from corresponding table
		String swipetype="";
		String type="";
		String submitDate="";
		String location="";
		String requesterName="";
		String matGroup="";
		String pApprover="";
		String vcmasterName = "";
		String vcmasterPAN = "";
		String vcmasterEMAIL = "";
		String typeofvc = "";
		String noOfDays = "";
		String ondutyType = ""; 
		String plant="";
		String fromTime="";
		String toTime="";
		String duration = "";
		String startDate = "";
		String endDate="";
		String leaveType = ""; //getLeaveType(leaveType);
		String holidayType = "";
		String reqEmp="";
		String empQual="";
		String empExp="";
		String feedComment="";
		String feedFrom="";
		String feedSub="";
		//for material
		String matcode="";
		String materialType="";
		String reqMatType="";
		String matsName="";
		String matgName="";
		String pharmaName="";
		//for service master
		String mcname="";
		String serCat="";
		String serDes="";
		String serPur="";
		String requestTime="";
		String reason="";
		String reasonType="";
		//for permission
		String perDate = "";
		String perStartTime = "";
		String perEndTime = "";
		String perReason = "";
		String startDur="";
		String endDur="";
		double casualOpeningBal=0;
		double casualAvailedBal=0;
		double casualClosingBal=0;
		double casualAwatingBal=0;
		
		double sickOpeningBal=0;
		double sickAvailedBal=0;
		double sickClosingBal=0;
		double sickAwatingBal=0;
		
		double previlegeOpeningBal=0;
		double previlegeAvailedBal=0;
		double previlegeClosingBal=0;
		double previlegeAwatingBal=0;
		
		double maternityOpeningBal=0;
		double maternityAvailedBal=0;
		double maternityClosingBal=0;
		double maternityAwatingBal=0;
		String approverDesc="";
		String approver1="";
		String parallelApp1="";
		String parallelApp2="";
		
		//vendor master
		
		String vendorName="";
		String vendorCity="";	
		String vedorType="";
		String vendorType="";
		//customer master
		String customerName="";
		String customerCity="";	
		String customerType="";
		
		//code extension
		String sapNo="";
		String fromPlant="";
		String toPlant="";
		String fromStorage="";
		String toStorage="";
		String reqCodeType="";
		String parallelApprover1Desc="";
		String parallelApprover2Desc="";
		String tName = "leave_details";
		
		String purchase_group_desc="";

		String Storage_location_Name="";
		String ltxt="";
		String Shortname="";
		String longname="";
		String matType = "";
		String manufactured_at="";
		String brand_desc="";
		String div_desc="";
		String detailed_Justification="";
		String detailed_Specification="";
		
		
		String account_group_id="";
		String title="";
		String address1="";
		String address2="";
		String address3="";
		String address4="";
		String pinCode="";
		String landx="";
		String state="";
		String bezei="";
		String landLine_No="";
		String mobile_no="";
		String faxNo="";
		String email_Id="";
		String account_group_name=""; 
		
		String valuation_desc="";
		String service_Description="";
		String detailed_desc="";
		String machine_name="";
		String app_value="";
		String stxt="";
		String requesterId= typ.getEmployeeno();
		String customer_code=""; 
		String category=""; 
		
		if(requestFrom.equalsIgnoreCase("Travel Desk")){
			TravelRequestForm t =new TravelRequestForm();
			ArrayList data=new ArrayList();
			ArrayList emplist=new ArrayList();
			ArrayList guestlist=new ArrayList();
			String reqType="";
			String lis="select CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1, CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as bookeddate1,"
					+ "CONVERT(varchar(10),Depart_On,103)  as departon,"
					+ "CONVERT(varchar(10),Return_On,103)  as returnon,CONVERT(varchar(10),billdate,103)  as billdate1,* from Travel_Desk where reqNo='"+Req_Id+"' ";
			ResultSet ff=ad.selectQuery(lis);
			try {
				if(ff.next())
				{
					
					t.setRequestNumber(ff.getInt("reqNo"));
					t.setReqType(ff.getString("Req_type"));
					reqType=ff.getString("Req_type");
					t.setTypeOfTravel(ff.getString("Type_of_travel"));
					t.setTravelFor(ff.getString("Travel_for"));
					t.setTravelRequestFor(ff.getString("Travel_Request_for"));
					reqEmp=Integer.toString(ff.getInt("PERNR"));
					
					
					t.setModeOfTravel(ff.getString("Mode_of_travel"));
					t.setFromPlace(ff.getString("From_place"));
					t.setDepartOn(ff.getString("departon"));
					t.setDepartTime(ff.getString("Preferred_depart_time"));
					//t.setReturnTime(ff.getString("Preferred_return_time"));	
					if(!ff.getString("Preferred_return_time").equalsIgnoreCase("null"))
					{
						t.setReturnTime(ff.getString("Preferred_return_time"));	
					}
					else
					{
						t.setReturnTime(ff.getString(""));	
					}
					/*t.setDepartRemarks(ff.getString("Depart_Time_remarks"));
				
					if(!ff.getString("Return_Time_remarks").equalsIgnoreCase("null"))
					{
						t.setReturnRemarks(ff.getString("Return_Time_remarks"));
					}
					else
					{
						t.setReturnRemarks(ff.getString(""));	
					}*/
					
	               t.setToPlace(ff.getString("To_place"));
	              
	               
	              
	            	   t.setVia(ff.getString("Via"));
					
					
	               
	               
	             /*  if(!ff.getString("Remarks").equalsIgnoreCase("null"))
					{
	            	   t.setRemarks(ff.getString("Remarks"));
					}
					else
					{
						 t.setRemarks(ff.getString(""));	
					}
	             */
	             
	               if(!ff.getString("purposetext").equalsIgnoreCase("NULL"))
	               {
	            	   t.setPurposeOfVisit(ff.getString("purposetext")); 
	               }
	               else
	               {
	            	   t.setPurposeOfVisit("");  
	               }
	            	
	            	   if(ff.getString("returnon")!=null)
		               {
	            		   t.setReturnOn(ff.getString("returnon"));
		               }
		               else
		               {
		            	   t.setReturnOn("");  
		               }
	              
	             t.setReqDate(ff.getString("Created_date1"));
	              t.setTravelagentname(ff.getString("travelagentname"));
	              t.setBillno(ff.getString("billno"));
	              t.setBilldate(ff.getString("billdate1"));
	              t.setBillamount(ff.getString("billamount"));
	              t.setBookingstatus(ff.getString("bookingstatus"));
	              t.setBookeddate(ff.getString("bookeddate1"));
	              t.setBookedby(Empname(ff.getString("bookedby")));
	              t.setConfirmdate(ff.getString("confirmdate1"));
	              t.setConfirmby(Empname(ff.getString("confirmby")));
	              t.setConfirmstatus(ff.getString("confirmstatus"));
	              data.add(t);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("data", data);
			
			
			
			//requester 
			
			 HelpDeskForm help=new HelpDeskForm();
			String data1="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+reqEmp+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data1);
			try {
				while(rs.next())
				{
					requesterName=rs.getString("EMP_FULLNAME");
					
					
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
			
			
			mailForm.setEmpname(requesterName);
			mailForm.setSendermail("");
			
			
			//Approver
			String getAppEmailID="select EMAIL_ID from emp_official_info where PERNR='"+reqEmp+"'";
			ResultSet approverRS=ad.selectQuery(getAppEmailID);
			try {
				while(approverRS.next()){
					approvermail = approverRS.getString(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			/*//userMail
			String usermail="";
			String getUserMail= "select email_id from emp_official_info where pernr='"+requesterId+"'";
			ResultSet approverRS1=ad.selectQuery(getUserMail);
			try {
				while(approverRS1.next()){
					usermail = approverRS1.getString(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			mailForm.setToAddress(approvermail);
			
			System.out.println("m -> "+approvermail);
			mailForm.setccAddress("");
			mailForm.setbccAddress("");
			
			String desc = "";
			mailForm.setSubject("Travel Request Booking Confirmation by '"+t.getConfirmby()+"' ");
			//mailForm1.setSubject("Travel Request Confirmed from '"+t.getConfirmby()+"'");
			desc=desc+"";
			desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
	             +"TABLE {border-collapse:collapse;border:1px solid black;}"
	             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
	             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
			 +"</STYLE>";
			desc=desc+"<html><body><img src="+"http://portal.microlabs.co.in/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=5 ><center><big>Travel Request Details</big></center></th></tr>";

			
			desc=desc+"	<tr><td>Request Type</td><td colpsan=3><big><b>"+t.getReqType()+"</b></big></td></tr>"
			+ " <tr><td>Request No:</td><td>"+Req_Id+"</td><td>Request Date:</td><td>"+t.getReqDate()+"</td></tr>"
			+ "	<tr><td>Type of Travel:</td><td>"+t.getTypeOfTravel()+"</td>	<td>Travel For:</td><td>"+t.getTravelFor()+"</td>	</tr>"					
			+ "<tr><td>Mode Of Travel:</td><td>"+t.getModeOfTravel()+"</td><td>Travel Request For:</td><td>"+t.getTravelRequestFor()+"</td>	</tr>"
			+ "<tr><td>From:</td><td colspan=3>"+t.getFromPlace()+"</td></tr>"
	        + " <tr><td>Depart On:</td><td colspan=3>"+t.getDepartOn()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Preferred Time:</b>&nbsp;&nbsp;"+t.getDepartTime()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</tr>"
		    + "	<tr><td>To:</td><td colspan=3>"+t.getToPlace()+"</td></tr>"
		    + " <tr><td>Return On:</td><td colspan=3>"+t.getReturnOn()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Preferred Time:</b>&nbsp;&nbsp;"+t.getReturnTime()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</tr>"
          + "	<tr><td>Purpose of Visit:</td><td>"+t.getPurposeOfVisit()+"</td></tr>"
		/*	+ "<tr>	<td>Remarks:</td><td colspan=6 >"+t.getRemarks()+"</td></tr>"*/
          +"<tr><th colspan=8>Booking Details</th></tr>"
          + "	<tr><td>Confirmed By:</td><td colspan=1>"+t.getConfirmby()+"</td><td>Confirmed Date.:</td><td>"+t.getConfirmdate()+"</td></tr>"
       
          ;
						    														  
		
			desc=desc+"</table><br><br>";
		   
		    desc=desc+"</br></br>";
			desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"http://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
		    desc=desc+"</body></html>";
		
		    mailForm.setDescription(desc);
		 
			try {
				mailAction.mailSendToRequester(request, mailForm,"request",t);
				//mailAction.mailSendToRequester(request, mailForm1, "request",t);
			} catch (IOException | ServletException | SQLException
					| MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return success;}
	 
	 
	 public int sendMailToAccepter(HttpServletRequest request, String approver, String Req_Id, String requestFrom)
	  {
			

			int success=0;
			HttpSession session=request.getSession();
			
			MailInboxForm mailForm = new MailInboxForm();
			MailInboxAction mailAction = new MailInboxAction();
			
			
			String ty="select * from travel_desk, Travel_desk_Approvers "
					+ "where travel_desk.travel_desk_type= Travel_desk_Approvers.ApproverId and travel_desk.reqno='"+Req_Id+"'  ";
			ResultSet n=ad.selectQuery(ty);
			try {
				if(n.next())
				approver= n.getString("ApproverId");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			
			
			
			try{
				
				//getting details from corresponding table
				String requesterName="";
				String pApprover="";
				String reqEmp="";
				String approver1="";
				String parallelApp1="";
				String parallelApp2="";
				
				
				if(requestFrom.equalsIgnoreCase("Travel Desk")){
					TravelRequestForm t =new TravelRequestForm();
					ArrayList data=new ArrayList();
					ArrayList emplist=new ArrayList();
					ArrayList guestlist=new ArrayList();
					String reqType="";
					String lis="select CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1, CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as bookeddate1,"
							+ "CONVERT(varchar(10),Depart_On,103)  as departon,"
							+ "CONVERT(varchar(10),Return_On,103)  as returnon,CONVERT(varchar(10),billdate,103)  as billdate1,* from Travel_Desk where reqNo='"+Req_Id+"' ";
					ResultSet ff=ad.selectQuery(lis);
					try {
						if(ff.next())
						{
							
							t.setRequestNumber(ff.getInt("reqNo"));
							t.setReqType(ff.getString("Req_type"));
							reqType=ff.getString("Req_type");
							t.setTypeOfTravel(ff.getString("Type_of_travel"));
							t.setTravelFor(ff.getString("Travel_for"));
							t.setTravelRequestFor(ff.getString("Travel_Request_for"));
							reqEmp=Integer.toString(ff.getInt("PERNR"));
							t.setModeOfTravel(ff.getString("Mode_of_travel"));
							t.setFromPlace(ff.getString("From_place"));
							t.setDepartOn(ff.getString("departon"));
							t.setDepartTime(ff.getString("Preferred_depart_time"));
							//t.setReturnTime(ff.getString("Preferred_return_time"));	
							t.setDepartRemarks(ff.getString("Depart_Time_remarks"));
						//	t.setReturnRemarks(ff.getString("Return_Time_remarks"));
			               t.setToPlace(ff.getString("To_place"));
			               t.setVia(ff.getString("Via"));
			               t.setRemarks(ff.getString("purposetext"));
			               t.setPurposeOfVisit(ff.getString("purposetype"));
			            //   t.setReturnOn(ff.getString("returnon"));
			              
			           	if(!ff.getString("Preferred_return_time").equalsIgnoreCase("null"))
						{
							t.setReturnTime(ff.getString("Preferred_return_time"));	
						}
						else
						{
							t.setReturnTime(ff.getString(""));	
						}
						/*t.setDepartRemarks(ff.getString("Depart_Time_remarks"));
					
						if(!ff.getString("Return_Time_remarks").equalsIgnoreCase("null"))
						{
							t.setReturnRemarks(ff.getString("Return_Time_remarks"));
						}
						else
						{
							t.setReturnRemarks(ff.getString(""));	
						}*/
						
						
		               if(!ff.getString("purposetext").equalsIgnoreCase("null"))
		               {
		            	   t.setPurposeOfVisit(ff.getString("purposetext")); 
		               }
		               else
		               {
		            	   t.setPurposeOfVisit("");  
		               }
		            	
		            	   if(ff.getString("returnon")!=null)
			               {
		            		   t.setReturnOn(ff.getString("returnon"));
			               }
			               else
			               {
			            	   t.setReturnOn("");  
			               }
			               
			              t.setReqDate(ff.getString("Created_date1"));
			              t.setTravelagentname(ff.getString("travelagentname"));
			              t.setBillno(ff.getString("billno"));
			              t.setBilldate(ff.getString("billdate1"));
			              t.setBillamount(ff.getString("billamount"));
			              t.setBookingstatus(ff.getString("bookingstatus"));
			              t.setBookeddate(ff.getString("bookeddate1"));
			              t.setBookedby(Empname(ff.getString("bookedby")));
			              t.setConfirmdate(ff.getString("confirmdate1"));
			              t.setConfirmby(Empname(ff.getString("confirmby")));
			              t.setConfirmstatus(ff.getString("confirmstatus"));
			              
			              approver1= approver;
			              data.add(t);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("data", data);
					
					
					//requester 
					
					 HelpDeskForm help=new HelpDeskForm();
					String data1="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+reqEmp+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data1);
					try {
						while(rs.next())
						{
							requesterName=rs.getString("EMP_FULLNAME");
							
							
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
					
					
					mailForm.setEmpname(requesterName);
					mailForm.setSendermail("");
					
					
					
					
					mailForm.setAttachNames("TP_"+Req_Id);
					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
					Properties props = null;
					try {
						props = PropertiesLoaderUtils.loadAllProperties("db.properties");
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					
								
								
					String uploadFilePath=props.getProperty("file.uploadFilePath");
					String filePath=uploadFilePath+"/EMicro Files/ESS/Travel Request/PDF/";
					filePath=filePath.replace("/", "\\")+""+Req_Id+"_Travel_desk_report.pdf";
					
					mailForm.setAttach(filePath);
					
					
					
					//Approver
					String getAppEmailID="select EMAIL_ID from emp_official_info where PERNR='"+approver+"'";
					ResultSet approverRS=ad.selectQuery(getAppEmailID);
					while(approverRS.next()){
						approver = approverRS.getString(1);
					}
					
					//User email
					MailInboxForm mailForm1 = new MailInboxForm();
					String useremailId="";
					String Usermail= "select email_id from emp_official_info where PERNR='"+reqEmp+"'";
					ResultSet getEMail= ad.selectQuery(Usermail);
					while(getEMail.next())
					{
						useremailId=getEMail.getString(1);
					}
					mailForm.setToAddress(approver);
					mailForm.setToAddress(useremailId);
					
					System.out.println("m -> "+approver);
					mailForm.setccAddress("");
					mailForm1.setccAddress("");
					mailForm1.setbccAddress("");
					mailForm.setbccAddress("");
					String desc = ""; 
					mailForm.setSubject("Travel  Acceptance Request from "+requesterName);
					mailForm1.setSubject("Travel Booking Confirmed from "+approver);
					desc=desc+"";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
			             +"TABLE {border-collapse:collapse;border:1px solid black;}"
			             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
			             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
					 +"</STYLE>";
					desc=desc+"<html><body><img src="+"http://portal.microlabs.co.in/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=5 ><center><big>Travel Request Details</big></center></th></tr>";

					desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
					+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
					+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
					+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
					
					desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
							+ "	 <tr><td>Request Type</td><td colpsan=3><big><b>"+t.getReqType()+"</b></big></td></tr>"
					+ " <tr><td>Request No:</td><td>"+Req_Id+"</td><td>Request Date:</td><td>"+t.getReqDate()+"</td></tr>"
					+ "	<tr><td>Type of Travel:</td><td>"+t.getTypeOfTravel()+"</td>	<td>Travel For:</td><td>"+t.getTravelFor()+"</td>	</tr>"					
					+ "<tr><td>Mode Of Travel:</td><td>"+t.getModeOfTravel()+"</td><td>Travel Request For:</td><td>"+t.getTravelRequestFor()+"</td>	</tr>"
					+ "<tr><td>From:</td><td colspan=3>"+t.getFromPlace()+"</td></tr>"
			        + " <tr><td>Depart On:</td><td colspan=3>"+t.getDepartOn()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Preferred Time:</b>&nbsp;&nbsp;"+t.getDepartTime()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>"
				    + "	<tr><td>To:</td><td colspan=3>"+t.getToPlace()+"</td></tr>"
				    + " <tr><td>Return On:</td><td colspan=3>"+t.getReturnOn()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Preferred Time:</b>&nbsp;&nbsp;"+t.getReturnTime()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>"
		            + "	<tr><td>Purpose of Visit:</td><td colspan=3>"+t.getPurposeOfVisit()+"</td></tr>"
					/*+ "<tr>	<td>Remarks:</td><td colspan=6 >"+t.getRemarks()+"</td></tr>"*/
		            
		            ;
								    														  
				
					desc=desc+"</table><br><br>";
					
					desc=desc+"<a href="+"https://portal.microlabs.co.in/travelrequest.do?method=mailconfirmBookingDetails&userID="+approver1+"&reqId="+Req_Id+"&status=Approve>"
							+ "<img src="+"https://portal.microlabs.co.in/images/Approve.png"+" height=20 width=80 title=Approve border=0/></a>";
									/*+ "<a href="+"http://localhost:8090/EMicro_Main/travelrequest.do?method=mailconfirmBookingDetails&userID="+approver1+"&reqId="+Req_Id+"&status=Reject>"
									+ "<img src="+"http://localhost:8090/EMicro_Main/images/Reject.png"+" height=20 width=80 title=Reject border=0 /></a></br></br>";*/
					
				   
				    desc=desc+"</br></br>";
					desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"http://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
				    desc=desc+"</body></html>";
				
				    mailForm.setDescription(desc);
				    mailForm1.setDescription(desc);
				    mailForm.setuSER_NAME(requesterName.replace(" ", ""));
				    //mailForm.setuSER_NAME(requesterName);
				    mailForm1.setuSER_NAME(requesterName);
					try {
						mailAction.mailSendToRecipient(request, mailForm,"request");
						//mailAction.mailSendToRecipient(request, mailForm1,"request");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return success;
			
	  }
	
	 public int sendMailToMaterialMASSCodeRequester(HttpServletRequest request, String approvermail, String datenow, String requestFrom,String creatorID,String requesterID,String status){
		 
		 


		 int success=0;
		 MailInboxForm mailForm = new MailInboxForm();
			MailInboxAction mailAction = new MailInboxAction();
		String submitDate="";
		String sapCodeExist="";
		String sapCodeNo="";
		String location="";
		String materialType="";
		String matsName="";
		String requesterName="";
		String matGroup="";
		String desc="";
		String vendorName="";
		String vendorCity="";	
		String vedorType="";
		String vendorType="";
		//customer master
		String customerName="";
		String customerCity="";	
		String customerType="";
		String mcname="";
		String serCat="";
		String serDes="";
		String pApprover="";
		String serPur="";
		String statusType="";
		//code extension
		String sapNo="";
		String fromPlant="";
		String toPlant="";
		String fromStorage="";
		String toStorage="";
		String reqCodeType="";
		
	ArrayList de=new ArrayList();

		 if(requestFrom.equalsIgnoreCase("Material Code Request")){
			 if(status.equalsIgnoreCase("Approve")){
				String newmatcode = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,loc.LOCATION_CODE,Type,MATERIAL_SHORT_NAME,"
						+ "emp.EMP_FULLNAME,emp.EMAIL_ID,MATERIAL_GROUP_ID,mat.SAP_CODE_EXISTS,mat.SAP_CODE_NO from  material_code_request as mat,Location as loc,"
						+ "emp_official_info as emp where mat.SAP_Approved_Date='"+datenow+"' and Approve_Type='Completed' and CREATED_BY='"+requesterID+"'"
						+ "  and loc.LOCID=mat.LOCATION_ID and emp.PERNR=mat.CREATED_BY";
				ResultSet ldRS = ad.selectQuery(newmatcode);
				try{
				while(ldRS.next()){
					MaterialCodeRequestForm m = new MaterialCodeRequestForm();
					m.setRequestDate( ldRS.getString("REQUEST_DATE"));
					m.setLocationCode(ldRS.getString("LOCATION_CODE"));
					m.setMaterailType(ldRS.getString("Type"));
				    m.setMaterialShortName(ldRS.getString("MATERIAL_SHORT_NAME"));
				    m.setRequesterName(ldRS.getString("EMP_FULLNAME"));
					m.setMaterialGroup(ldRS.getString("MATERIAL_GROUP_ID"));
					m.setRequestNumber(ldRS.getString("REQUEST_NO"));		
		
				
					sapCodeExist=ldRS.getString("SAP_CODE_EXISTS");
					if(sapCodeExist.equalsIgnoreCase("1"))
						sapCodeExist="Yes";
					if(sapCodeExist.equalsIgnoreCase("0"))
						sapCodeExist="No";
					
				    m.setCodeNo(sapCodeExist);
				    m.setSapCodeNo(ldRS.getString("SAP_CODE_NO"));					
					de.add(m);
					
					//history data
					
					String ahis=" select top 1 MATERIAL_SHORT_NAME from  material_code_request_history where REQUEST_NO="+ldRS.getString("REQUEST_NO")+" "
							+ "order by Transaction_time desc";
					ResultSet l=ad.selectQuery(ahis);
					if(l.next())

					{
						m.setMaterialName(l.getString("MATERIAL_SHORT_NAME"));
					}
					else
					{
						m.setMaterialName(ldRS.getString("MATERIAL_SHORT_NAME"));
					}
				}
				
				String getAppEmailID="select EMAIL_ID from emp_official_info where PERNR='"+requesterID+"'";
			 	ResultSet approverRS=ad.selectQuery(getAppEmailID);
				while(approverRS.next()){
					approvermail = approverRS.getString(1);
				}
				
			
				
				desc=desc+"";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
		             +"TABLE {border-collapse:collapse;border:1px solid black;}"
		             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
		             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
				 +"</STYLE>";
				desc=desc+"<html><body><img src="+"https://localhost:8090/EMicro_Main/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=9 ><center>Material Code Request Details</center></th></tr>";
				desc=desc+"<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Plant</th><th align=left>Material Type</th><th align=left>Material Name as per Request</th><th align=left>Material Name as per Code Created</th><th>Status</th><th>SAP Code Exist</th><th>SAP Code No</th></tr>";
				Iterator i=de.iterator();
				int h=1;
				while(i.hasNext())
				{
					
					MaterialCodeRequestForm mat =(MaterialCodeRequestForm) i.next();
				desc=desc+"<tr><td>"+mat.getRequestNumber()+"</td><td>"+mat.getRequestDate()+"</td><td>"+mat.getLocationCode()+"</td><td>"+mat.getMaterailType()+"</td>"
						+ "<td>"+mat.getMaterialName()+"</td><td>"+mat.getMaterialShortName()+"</td><td>Created</td><td>"+mat.getCodeNo()+"</td><td>"+mat.getSapCodeNo()+"</td></tr>";
				
				
				if(h==1)
				{
					
				mailForm.setSubject("Code "+mat.getSapCodeNo()+" created against  request no. "+mat.getRequestNumber()+" ");
				}
				else
				{
					mailForm.setSubject("Code creation details ");
		
				}
				h++;
				}
				
				
				
				desc=desc	+ "</table><br><br>";
				desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"https://localhost:8090/EMicro_Main/"+">EMicro Portal</a> .</span> ";
			    desc=desc+"</body></html>";
			    mailForm.setToAddress(approvermail);
				System.out.println("m -> "+approvermail);
				mailForm.setccAddress("");
				mailForm.setbccAddress("");
				
				mailForm.setDescription(desc);
				mailAction.mailSendNextApprover(request, mailForm,"request",creatorID );
					success=1;
				}catch (ServletException se) { System.out.println("ServletException @ sending leave request.."); se.printStackTrace();}
				catch (MessagingException se) { System.out.println("MessagingException @ sending leave request.."); se.printStackTrace();}
				catch (IOException ioe) { System.out.println("IOException @ sending leave request.."); ioe.printStackTrace();}
				catch (SQLException sqle) { 
					System.out.println("SQLException @ sending  request..");
					sqle.printStackTrace();
					}
		 }
			
		 }
		 return success;
	 
	 
	 }
	public int sendMailToMaterialCodeRequester(HttpServletRequest request,
			String approvermail, String Req_Id, String requestFrom,
			String creatorID, String requesterID, String status) {
		int success = 0;
		MailInboxForm mailForm = new MailInboxForm();
		MailInboxAction mailAction = new MailInboxAction();
		String submitDate = "";
		String sapCodeExist = "";
		String sapCodeNo = "";
		String location = "";
		String materialType = "";
		String matsName = "";
		String requesterName = "";
		String matGroup = "";
		String desc = "";
		String vendorName = "";
		String vendorCity = "";
		String vedorType = "";
		String vendorType = "";
		// customer master
		String customerName = "";
		String customerCity = "";
		String customerType = "";
		String mcname = "";
		String serCat = "";
		String serDes = "";
		String pApprover = "";
		String serPur = "";
		String statusType = "";
		// code extension
		String sapNo = "";
		String fromPlant = "";
		String toPlant = "";
		String fromStorage = "";
		String toStorage = "";
		String reqCodeType = "";
		if (requestFrom.equalsIgnoreCase("Code Extension")) {
			if (status.equalsIgnoreCase("Approved")) {
				statusType = "Created";
				mailForm.setSubject("Code Extension created against request no. "
						+ Req_Id + " successfull");
			} else {
				statusType = "Rejected";
				mailForm.setSubject("Code Extension created against request no. "
						+ Req_Id + " Rejected");
			}
			try {
				String getdetails = "select REQUEST_NO,REQUEST_DATE,CREATED_BY,TYPE,MATERIAL_CODE_1,PLANT_1 fromPlant,EXTENDED_TO_PLANT_1 toPlant,"
						+ "STORAGE_LOCATION_ID_1 fromStorage,EXTENDED_STORAGE_LOCATION_1 toStorage,PLANT_2 fromPlant1,MATERIAL_CODE_2,FROM_STORAGE_LOCATION,"
						+ "TO_STORAGE_LOCATION from code_extenstion_request where REQUEST_NO='"
						+ Req_Id + "'";
				ResultSet rsDetails = ad.selectQuery(getdetails);
				while (rsDetails.next()) {
					submitDate = EMicroUtils.display(rsDetails
							.getDate("REQUEST_DATE"));
					reqCodeType = rsDetails.getString("TYPE");
					if (reqCodeType.equals("Plant")) {
						sapNo = rsDetails.getString("MATERIAL_CODE_1");
						fromPlant = rsDetails.getString("fromPlant");
						toPlant = rsDetails.getString("toPlant");
						fromStorage = rsDetails.getString("fromStorage");
						toStorage = rsDetails.getString("toStorage");
						location = rsDetails.getString("toPlant");
					} else {
						sapNo = rsDetails.getString("MATERIAL_CODE_2");
						fromPlant = rsDetails.getString("fromPlant1");
						fromStorage = rsDetails
								.getString("FROM_STORAGE_LOCATION");
						toStorage = rsDetails.getString("TO_STORAGE_LOCATION");
						location = rsDetails.getString("fromPlant1");
					}

				}

				desc = desc + "";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";

				if (reqCodeType.equals("Plant")) {
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=9 ><center>Code Extention Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Type</th><th align=left>Material Code</th><th>From Plant</th><th>Storage Location</th><th>To Plant</th><th>Storage Location</th><th>Status</th></tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + reqCodeType
							+ "</td><td>" + sapNo + "</td><td>" + fromPlant
							+ "</td><td>" + fromStorage + "</td><td>" + toPlant
							+ "</td><td>" + toStorage + "</td><td><b>"
							+ statusType + "</b></td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
				} else {
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=8 ><center>Code Extention Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Type</th><th align=left>Material Code</th><th>From Plant</th><th>From Storage Location</th><th>To Storage Location</th><th>Status</th></tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + reqCodeType
							+ "</td><td>" + sapNo + "</td><td>" + fromPlant
							+ "</td><td>" + fromStorage + "</td><td>"
							+ toStorage + "</td><td><b>" + statusType
							+ "</b></td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
				}

				String getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
						+ requesterID + "'";
				ResultSet approverRS = ad.selectQuery(getAppEmailID);
				while (approverRS.next()) {
					approvermail = approverRS.getString(1);
				}
				mailForm.setToAddress(approvermail);
				System.out.println("m -> " + approvermail);
				mailForm.setccAddress("");
				mailForm.setbccAddress("");

				mailForm.setDescription(desc);
				mailAction.mailSendNextApprover(request, mailForm, "request",
						creatorID);
				success = 1;
			} catch (ServletException se) {
				System.out
						.println("ServletException @ sending leave request..");
				se.printStackTrace();
			} catch (MessagingException se) {
				System.out
						.println("MessagingException @ sending leave request..");
				se.printStackTrace();
			} catch (IOException ioe) {
				System.out.println("IOException @ sending leave request..");
				ioe.printStackTrace();
			} catch (SQLException sqle) {
				System.out.println("SQLException @ sending  request..");
				sqle.printStackTrace();
			}
		}
		if (requestFrom.equalsIgnoreCase("Service Master")) {
			String service_code="";
			if (status.equalsIgnoreCase("Approved")) {
				statusType = "Created";
				mailForm.setSubject("Service Code created against request no. "
						+ Req_Id + " successfull");
			} else {
				statusType = "Rejected";
				mailForm.setSubject("Service Code created against request no. "
						+ Req_Id + " Rejected");
			}
			String getdetails = "select s.SAP_CODE_NO,s.request_date,s.machine_name,s.service_catagory,s.service_description,s.pending_approver,"
					+ "s.purpose,loc.LOCATION_CODE from SERVICE_MASTER s,Location loc where  request_no='"
					+ Req_Id + "' and " + "loc.LOCID=s.plant_code";
			ResultSet detailRS = ad.selectQuery(getdetails);
			try {
				while (detailRS.next()) {
					submitDate = detailRS.getString("request_date");
					mcname = detailRS.getString("machine_name");
					serCat = detailRS.getString("service_catagory");
					serDes = detailRS.getString("service_description");
					pApprover = detailRS.getString("pending_approver");
					serPur = detailRS.getString("purpose");
					location = detailRS.getString("LOCATION_CODE");
					service_code=detailRS.getString("SAP_CODE_NO");
					
				}

				desc = desc + "";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";
				desc = desc
						+ "<html><body><img src="
						+ "https://portal.microlabs.co.in/images/logo.png"
						+ " height=60 width=50  /></br> <table><tr><th colspan=6 ><center>Service Code Request Details</center></th></tr>";
				desc = desc
						+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Service Category</th><th align=left>Service Description</th><th align=left>Service Code</th><th>Status</th></tr>";
				desc = desc + "<tr><td>" + Req_Id + "</td><td>" + submitDate
						+ "</td><td>" + serCat + "</td><td>" + serDes
						+ "</td><td>" + service_code + "</td><td>" + statusType
						+ "</td></tr></table><br><br>";
				desc = desc
						+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
						+ "https://portal.microlabs.co.in/"
						+ ">EMicro Portal</a> .</span> ";
				desc = desc + "</body></html>";
				String getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
						+ requesterID + "'";
				ResultSet approverRS = ad.selectQuery(getAppEmailID);
				while (approverRS.next()) {
					approvermail = approverRS.getString(1);
				}
				mailForm.setToAddress(approvermail);
				System.out.println("m -> " + approvermail);
				mailForm.setccAddress("");
				mailForm.setbccAddress("");

				mailForm.setDescription(desc);
				mailAction.mailSendNextApprover(request, mailForm, "request",
						creatorID);
				success = 1;
			} catch (ServletException se) {
				System.out
						.println("ServletException @ sending leave request..");
				se.printStackTrace();
			} catch (MessagingException se) {
				System.out
						.println("MessagingException @ sending leave request..");
				se.printStackTrace();
			} catch (IOException ioe) {
				System.out.println("IOException @ sending leave request..");
				ioe.printStackTrace();
			} catch (SQLException sqle) {
				System.out.println("SQLException @ sending  request..");
				sqle.printStackTrace();
			}
		}

		if (requestFrom.equalsIgnoreCase("Customer Master")) {
			if (status.equalsIgnoreCase("Approve")) {
				String getCustDet = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,NAME,CITY,v.Customer_Type from "
						+ "CUSTOMER_MASTER_M as v,emp_official_info as emp where  REQUEST_NO='"
						+ Req_Id + "'  and emp.PERNR=v.CREATED_BY";
				ResultSet rsCust = ad.selectQuery(getCustDet);
				try {
					while (rsCust.next()) {
						submitDate = rsCust.getString("REQUEST_DATE");
						customerName = rsCust.getString("NAME");
						customerCity = rsCust.getString("CITY");
						customerType = rsCust.getString("Customer_Type");

					}
					mailForm.setSubject("Customer Code created against request no. '"
							+ Req_Id + "' successfull");
					desc = desc + "";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=7 ><center>Customer Code Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Customer Name</th><th align=left>City</th><th align=left>Customer Type</th><th>Status</tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + customerName
							+ "</td><td>" + customerCity + "</td><td>"
							+ customerType
							+ "</td><td>Created</td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
					String getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ requesterID + "'";
					ResultSet approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					System.out.println("m -> " + approvermail);
					mailForm.setccAddress("");
					mailForm.setbccAddress("");

					mailForm.setDescription(desc);
					mailAction.mailSendNextApprover(request, mailForm,
							"request", creatorID);
					success = 1;
				} catch (ServletException se) {
					System.out
							.println("ServletException @ sending leave request..");
					se.printStackTrace();
				} catch (MessagingException se) {
					System.out
							.println("MessagingException @ sending leave request..");
					se.printStackTrace();
				} catch (IOException ioe) {
					System.out.println("IOException @ sending leave request..");
					ioe.printStackTrace();
				} catch (SQLException sqle) {
					System.out.println("SQLException @ sending  request..");
					sqle.printStackTrace();
				}
			}
			if (status.equalsIgnoreCase("Rejected")) {
				String getCustDet = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,NAME,CITY,v.Customer_Type from "
						+ "CUSTOMER_MASTER_M as v,emp_official_info as emp where  REQUEST_NO='"
						+ Req_Id + "'  and emp.PERNR=v.CREATED_BY";
				ResultSet rsCust = ad.selectQuery(getCustDet);
				try {
					while (rsCust.next()) {
						submitDate = rsCust.getString("REQUEST_DATE");
						customerName = rsCust.getString("NAME");
						customerCity = rsCust.getString("CITY");
						customerType = rsCust.getString("Customer_Type");

					}
					mailForm.setSubject("Customer Code Request No " + Req_Id
							+ " has been rejected");
					desc = desc + "";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=7 ><center>Customer Code Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Customer Name</th><th align=left>City</th><th align=left>Customer Type</th><th>Status</tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + customerName
							+ "</td><td>" + customerCity + "</td><td>"
							+ customerType
							+ "</td><td>Rejected</td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
					String getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ requesterID + "'";
					ResultSet approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					System.out.println("m -> " + approvermail);
					mailForm.setccAddress("");
					mailForm.setbccAddress("");

					mailForm.setDescription(desc);
					mailAction.mailSendNextApprover(request, mailForm,
							"request", creatorID);
					success = 1;
				} catch (ServletException se) {
					System.out
							.println("ServletException @ sending leave request..");
					se.printStackTrace();
				} catch (MessagingException se) {
					System.out
							.println("MessagingException @ sending leave request..");
					se.printStackTrace();
				} catch (IOException ioe) {
					System.out.println("IOException @ sending leave request..");
					ioe.printStackTrace();
				} catch (SQLException sqle) {
					System.out.println("SQLException @ sending  request..");
					sqle.printStackTrace();
				}
			}
		}
		if (requestFrom.equalsIgnoreCase("Vendor Master")) {
			if (status.equalsIgnoreCase("Approve")) {
				String getVendorDet = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,NAME,CITY,v.Type_Of_Vendor,emp.EMP_FULLNAME from "
						+ "vendor_master_m as v, emp_official_info as emp where  REQUEST_NO='"
						+ Req_Id + "'  and emp.PERNR=v.CREATED_BY";
				ResultSet rsVendor = ad.selectQuery(getVendorDet);
				try {
					while (rsVendor.next()) {
						submitDate = rsVendor.getString("REQUEST_DATE");
						vendorName = rsVendor.getString("NAME");
						vendorCity = rsVendor.getString("CITY");
						requesterName = rsVendor.getString("EMP_FULLNAME");
						vendorType = rsVendor.getString("Type_Of_Vendor");
						if (vendorType.equalsIgnoreCase("I"))
							vendorType = "IMPORTER";
						if (vendorType.equalsIgnoreCase("M"))
							vendorType = "MANUFACTURER";
						if (vendorType.equalsIgnoreCase("D"))
							vendorType = "DEALER";
						if (vendorType.equalsIgnoreCase("FD"))
							vendorType = "FIRST STAGE DEALER OF INDIGENOUS";
						if (vendorType.equalsIgnoreCase("SD"))
							vendorType = "SECOND STAGE DEALER OF INDIGENOUS";
						if (vendorType.equalsIgnoreCase("MD"))
							vendorType = "DEPOT MANUFACTURER";
					}

					mailForm.setSubject("Vendor Code created against request no. '"
							+ Req_Id + "' successfull");
					desc = desc + "";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=7 ><center>Vendor Code Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Vendor Name</th><th align=left>City</th><th align=left>Vendor Type</th><th>Status</tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + vendorName
							+ "</td><td>" + vendorCity + "</td><td>"
							+ vendorType
							+ "</td><td>Created</td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
					String getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ requesterID + "'";
					ResultSet approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					System.out.println("m -> " + approvermail);
					mailForm.setccAddress("");
					mailForm.setbccAddress("");

					mailForm.setDescription(desc);
					mailAction.mailSendNextApprover(request, mailForm,
							"request", creatorID);
					success = 1;
				} catch (ServletException se) {
					System.out
							.println("ServletException @ sending leave request..");
					se.printStackTrace();
				} catch (MessagingException se) {
					System.out
							.println("MessagingException @ sending leave request..");
					se.printStackTrace();
				} catch (IOException ioe) {
					System.out.println("IOException @ sending leave request..");
					ioe.printStackTrace();
				} catch (SQLException sqle) {
					System.out.println("SQLException @ sending  request..");
					sqle.printStackTrace();
				}
			}
			if (status.equalsIgnoreCase("Rejected")) {
				String getVendorDet = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,NAME,CITY,v.Type_Of_Vendor,emp.EMP_FULLNAME from "
						+ "vendor_master_m as v, emp_official_info as emp where  REQUEST_NO='"
						+ Req_Id + "'  and emp.PERNR=v.CREATED_BY";
				ResultSet rsVendor = ad.selectQuery(getVendorDet);
				try {
					while (rsVendor.next()) {
						submitDate = rsVendor.getString("REQUEST_DATE");
						vendorName = rsVendor.getString("NAME");
						vendorCity = rsVendor.getString("CITY");
						requesterName = rsVendor.getString("EMP_FULLNAME");
						vendorType = rsVendor.getString("Type_Of_Vendor");
						if (vendorType.equalsIgnoreCase("I"))
							vendorType = "IMPORTER";
						if (vendorType.equalsIgnoreCase("M"))
							vendorType = "MANUFACTURER";
						if (vendorType.equalsIgnoreCase("D"))
							vendorType = "DEALER";
						if (vendorType.equalsIgnoreCase("FD"))
							vendorType = "FIRST STAGE DEALER OF INDIGENOUS";
						if (vendorType.equalsIgnoreCase("SD"))
							vendorType = "SECOND STAGE DEALER OF INDIGENOUS";
						if (vendorType.equalsIgnoreCase("MD"))
							vendorType = "DEPOT MANUFACTURER";
					}

					mailForm.setSubject("Vendor Code Request No " + Req_Id
							+ " has been rejected");
					desc = desc + "";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=7 ><center>Vendor Code Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Vendor Name</th><th align=left>City</th><th align=left>Vendor Type</th><th>Status</tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + vendorName
							+ "</td><td>" + vendorCity + "</td><td>"
							+ vendorType
							+ "</td><td>Rejected</td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
					String getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ requesterID + "'";
					ResultSet approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					System.out.println("m -> " + approvermail);
					mailForm.setccAddress("");
					mailForm.setbccAddress("");

					mailForm.setDescription(desc);
					mailAction.mailSendNextApprover(request, mailForm,
							"request", creatorID);
					success = 1;
				} catch (ServletException se) {
					System.out
							.println("ServletException @ sending leave request..");
					se.printStackTrace();
				} catch (MessagingException se) {
					System.out
							.println("MessagingException @ sending leave request..");
					se.printStackTrace();
				} catch (IOException ioe) {
					System.out.println("IOException @ sending leave request..");
					ioe.printStackTrace();
				} catch (SQLException sqle) {
					System.out.println("SQLException @ sending  request..");
					sqle.printStackTrace();
				}
			}
		}
		if (requestFrom.equalsIgnoreCase("Material Code Request")) {
			if (status.equalsIgnoreCase("Approve")) {
				String newmatcode = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,loc.LOCATION_CODE,Type,"
						+ "MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,emp.EMAIL_ID,MATERIAL_GROUP_ID,mat.SAP_CODE_EXISTS,mat.SAP_CODE_NO from material_code_request as mat,Location as loc,emp_official_info as emp where  "
						+ "REQUEST_NO='"
						+ Req_Id
						+ "' and loc.LOCID=mat.LOCATION_ID and emp.PERNR=mat.CREATED_BY";
				ResultSet ldRS = ad.selectQuery(newmatcode);
				try {
					while (ldRS.next()) {

						submitDate = ldRS.getString("REQUEST_DATE");
						location = ldRS.getString("LOCATION_CODE");
						materialType = ldRS.getString("Type");
						matsName = ldRS.getString("MATERIAL_SHORT_NAME");
						requesterName = ldRS.getString("EMP_FULLNAME");
						matGroup = ldRS.getString("MATERIAL_GROUP_ID");
						approvermail = ldRS.getString("EMAIL_ID");
						sapCodeExist = ldRS.getString("SAP_CODE_EXISTS");
						if (sapCodeExist.equalsIgnoreCase("1"))
							sapCodeExist = "Yes";
						if (sapCodeExist.equalsIgnoreCase("0"))
							sapCodeExist = "No";
						sapCodeNo = ldRS.getString("SAP_CODE_NO");

					}

					String getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ requesterID + "'";
					ResultSet approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setSubject("Code "+sapCodeNo+" created against  request no. "+Req_Id+" ");
					desc = desc + "";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=8 ><center>Material Code Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Plant</th><th align=left>Material Type</th><th align=left>Material Name</th><th>Status</th><th>SAP Code Exist</th><th>SAP Code No</th></tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + location + "</td><td>"
							+ materialType + "</td><td>" + matsName
							+ "</td><td>Created</td><td>" + sapCodeExist
							+ "</td><td>" + sapCodeNo
							+ "</td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
					mailForm.setToAddress(approvermail);
					System.out.println("m -> " + approvermail);
					mailForm.setccAddress("");
					mailForm.setbccAddress("");

					mailForm.setDescription(desc);
					mailAction.mailSendNextApprover(request, mailForm,
							"request", creatorID);
					success = 1;
				} catch (ServletException se) {
					System.out
							.println("ServletException @ sending leave request..");
					se.printStackTrace();
				} catch (MessagingException se) {
					System.out
							.println("MessagingException @ sending leave request..");
					se.printStackTrace();
				} catch (IOException ioe) {
					System.out.println("IOException @ sending leave request..");
					ioe.printStackTrace();
				} catch (SQLException sqle) {
					System.out.println("SQLException @ sending  request..");
					sqle.printStackTrace();
				}
			}
			if (status.equalsIgnoreCase("Rejected")) {
				String newmatcode = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,loc.LOCATION_CODE,Type,"
						+ "MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,emp.EMAIL_ID,MATERIAL_GROUP_ID from material_code_request as mat,Location as loc,emp_official_info as emp where  "
						+ "REQUEST_NO='"
						+ Req_Id
						+ "' and loc.LOCID=mat.LOCATION_ID and emp.PERNR=mat.CREATED_BY";
				ResultSet ldRS = ad.selectQuery(newmatcode);
				try {
					while (ldRS.next()) {

						submitDate = ldRS.getString("REQUEST_DATE");
						location = ldRS.getString("LOCATION_CODE");
						materialType = ldRS.getString("Type");
						matsName = ldRS.getString("MATERIAL_SHORT_NAME");
						requesterName = ldRS.getString("EMP_FULLNAME");
						matGroup = ldRS.getString("MATERIAL_GROUP_ID");
						approvermail = ldRS.getString("EMAIL_ID");
					}

					String getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ requesterID + "'";
					ResultSet approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setSubject("Material Code Request No " + Req_Id
							+ " has been rejected");
					desc = desc + "";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=6 ><center>Material Code Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Plant</th><th align=left>Material Type</th><th align=left>Material Name</th><th>Status</tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + location + "</td><td>"
							+ materialType + "</td><td>" + matsName
							+ "</td><td>Rejected</td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
					mailForm.setToAddress(approvermail);
					System.out.println("m -> " + approvermail);
					mailForm.setccAddress("");
					mailForm.setbccAddress("");

					mailForm.setDescription(desc);
					mailAction.mailSendNextApprover(request, mailForm,
							"request", creatorID);
					success = 1;
				} catch (ServletException se) {
					System.out
							.println("ServletException @ sending leave request..");
					se.printStackTrace();
				} catch (MessagingException se) {
					System.out
							.println("MessagingException @ sending leave request..");
					se.printStackTrace();
				} catch (IOException ioe) {
					System.out.println("IOException @ sending leave request..");
					ioe.printStackTrace();
				} catch (SQLException sqle) {
					System.out.println("SQLException @ sending  request..");
					sqle.printStackTrace();
				}
			}
		}
		return success;
	}

	public int sendMailToNextSequentialApprover(HttpServletRequest request,
			String approvermail, String Req_Id, String requestFrom,
			String nextApproverID, String requesterID) {
		int success = 0;
		MailInboxForm mailForm = new MailInboxForm();
		MailInboxAction mailAction = new MailInboxAction();

		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try {
			// getting details from corresponding table
			String swipetype = "";
			String type = "";
			String submitDate = "";
			String pApprover = "";
			String vcmasterName = "";
			String vcmasterPAN = "";
			String vcmasterEMAIL = "";
			String typeofvc = "";
			String noOfDays = "";
			String ondutyType = "";
			String plant = "";
			String fromTime = "";
			String toTime = "";
			String duration = "";
			String startDate = "";
			String endDate = "";
			String leaveType = ""; // getLeaveType(leaveType);
			String holidayType = "";
			String reqEmp = "";
			String empQual = "";
			String empExp = "";
			String feedComment = "";
			String feedFrom = "";
			String feedSub = "";
			// for material
			String matcode = "";
			String matsName = "";
			String matgName = "";
			String pharmaName = "";
			// for service master
			String mcname = "";
			String serCat = "";
			String serDes = "";
			String serPur = "";
			String requestTime = "";
			String reason = "";
			String reasonType = "";
			// for permission
			String perDate = "";
			String perStartTime = "";
			String perEndTime = "";
			String perReason = "";
			String startDur = "";
			String endDur = "";
			String fullName = "";
			double casualOpeningBal = 0;
			double casualAvailedBal = 0;
			double casualClosingBal = 0;
			double casualAwatingBal = 0;

			double sickOpeningBal = 0;
			double sickAvailedBal = 0;
			double sickClosingBal = 0;
			double sickAwatingBal = 0;

			double previlegeOpeningBal = 0;
			double previlegeAvailedBal = 0;
			double previlegeClosingBal = 0;
			double previlegeAwatingBal = 0;

			double maternityOpeningBal = 0;
			double maternityAvailedBal = 0;
			double maternityClosingBal = 0;
			double maternityAwatingBal = 0;
			
			
			double prevcasualAvailedBal=0;
			double prevsickAvailedBal=0;
			double prevprevilegeAvailedBal=0;
			// material code request

			String location = "";
			String materialType = "", requesterName = "", matGroup = "";
			String vendorName = "";
			String vendorCity = "";
			String vedorType = "";
			String vendorType = "";

			// customer master
			String customerName = "";
			String customerCity = "";
			String customerType = "";
			// code extension
			String sapNo = "";
			String fromPlant = "";
			String toPlant = "";
			String fromStorage = "";
			String toStorage = "";
			String reqCodeType = "";

			String desc = "";
			if (requestFrom.equalsIgnoreCase("Permission")) {

				String feeddetails = "select p.swipe_type,p.type,p.request_no,CONVERT(varchar(11),p.[date],103) as date,p.startTime,p.endTime,p.reason,p.Approver_id,emp.EMAIL_ID from "
						+ "Permission_details p,emp_official_info  as emp where p.user_id='"
						+ requesterID
						+ "' and p.request_no='"
						+ Req_Id
						+ "' and  " + "p.Approver_id=emp.PERNR";
				ResultSet ldRS = ad.selectQuery(feeddetails);
				while (ldRS.next()) {
					perDate = ldRS.getString("date");
					perStartTime = ldRS.getString("startTime");
					perEndTime = ldRS.getString("endTime");
					perReason = ldRS.getString("reason");
					pApprover = ldRS.getString("Approver_id");
					approvermail = ldRS.getString("EMAIL_ID");
					type = ldRS.getString("type");
					swipetype = ldRS.getString("swipe_type");
				}
				String checkESSApproverList = "select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"
						+ requesterID
						+ "' and essType='Leave' and ApproverId='"
						+ nextApproverID + "' and ess.ApproverId=emp.PERNR";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					approvermail = rsCheckInEss.getString("EMAIL_ID");
				}
				String getGender = "select * from emp_official_info where PERNR='"
						+ requesterID + "'";
				ResultSet rsGender = ad.selectQuery(getGender);
				while (rsGender.next()) {
					fullName = rsGender.getString("EMP_FULLNAME");
				}
				mailForm.setSubject("Permission Request From " + fullName);
				desc = desc + "";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";

				desc = desc
						+ "<html><body><img src="
						+ "https://portal.microlabs.co.in/images/logo.png"
						+ " height=60 width=50  /> <table><tr><th colspan=4 ><center>Permission Details</center></th></tr>";
				desc = desc
						+ "<tr><th align=left>Permission Date</th><td colspan=3>"
						+ perDate + "</td></tr>";
				desc = desc + "<tr><th align=left> Time</th><td >"
						+ perStartTime
						+ "</td><th align=left> Swipe Type</th><td >"
						+ swipetype + "</td></tr>";
				desc = desc + "<tr><th>Reason</th><td colspan=3>" + perReason
						+ "</tr></table></br></br>";
				desc = desc
						+ "<a href="
						+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
						+ nextApproverID
						+ "&reqId="
						+ Req_Id
						+ "&status=Approve><img src="
						+ "https://portal.microlabs.co.in/images/Approve.png"
						+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
						+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
						+ nextApproverID
						+ "&reqId="
						+ Req_Id
						+ "&status=Reject><img src="
						+ "https://portal.microlabs.co.in/images/Reject.png"
						+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
				desc = desc
						+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
						+ "https://portal.microlabs.co.in/"
						+ ">EMicro Portal</a> .</span> ";
				desc = desc + "</body></html>";

			}
			if (requestFrom.equalsIgnoreCase("On Duty")) {

				String ondutydetails = "select onduty.onDuty_Type,onduty.location,CONVERT(varchar(11),onduty.[start_date],103) as start_date,"
						+ "onduty.startTime,CONVERT(varchar(11),onduty.[end_date],103) as end_date,onduty.endTime,onduty.reason,CONVERT(varchar(11),onduty.[submit_date],103) as submit_date,onduty.Approver_id,emp.EMAIL_ID "
						+ "from OnDuty_details as onduty,emp_official_info as emp where onduty.user_id='"
						+ requesterID
						+ "' and emp.PERNR=onduty.Approver_id and "
						+ "user_id="
						+ requesterID
						+ " and request_no='"
						+ Req_Id + "'";
				ResultSet ldRS = ad.selectQuery(ondutydetails);
				while (ldRS.next()) {
					ondutyType = ldRS.getString("onDuty_Type");
					plant = ldRS.getString("location");
					startDate = ldRS.getString("start_date");
					fromTime = ldRS.getString("startTime");
					endDate = ldRS.getString("end_date");
					toTime = ldRS.getString("endTime");
					reason = ldRS.getString("reason");
					submitDate = ldRS.getString("submit_date");
					pApprover = ldRS.getString("Approver_id");
					approvermail = ldRS.getString("EMAIL_ID");
				}
				String checkESSApproverList = "select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"
						+ requesterID
						+ "' and essType='Leave' and ApproverId='"
						+ nextApproverID + "' and ess.ApproverId=emp.PERNR";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					approvermail = rsCheckInEss.getString("EMAIL_ID");
				}

				String gender = "";
				String pernr = "";
				String name = "";
				String doj = "";
				String dep = "";
				String desg = "";
				String getGender = "select emp.PERNR,emp.EMP_FULLNAME,emp.SEX,emp.DOJ,dep.DPTSTXT,desg.DSGSTXT from emp_official_info emp,DEPARTMENT as dep,DESIGNATION as desg where emp.PERNR='"
						+ requesterID
						+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
				ResultSet rsGender = ad.selectQuery(getGender);
				while (rsGender.next()) {
					gender = rsGender.getString("SEX");
					pernr = rsGender.getString("PERNR");
					name = rsGender.getString("EMP_FULLNAME");
					doj = rsGender.getString("DOJ");
					dep = rsGender.getString("DPTSTXT");
					desg = rsGender.getString("DSGSTXT");

				}
				desc = desc + "";
				desc = "Requested By : " + name + "</br>";
				desc = desc + "Requested Date : " + submitDate + "</br>";
				mailForm.setSubject("OnDuty Request From " + name);

				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";
				if (plant.equalsIgnoreCase("")) {
					desc = desc
							+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></center></td></tr> ";
					desc = desc
							+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
					desc = desc + "<tr><th align=left>Emp No.</th><td>" + pernr
							+ "</td><th align=left>Name</th><td>" + name
							+ "</td></tr>";
					desc = desc + "<tr><th align=left>Dept</th><td>" + dep
							+ "</td><th align=left>Desg</th><td>" + desg
							+ "</td></tr>";
					desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td >"+Req_Id+"</td></tr>";

					desc = desc
							+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=3>"
							+ ondutyType + "</td></tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;Start Date</th><td>"
							+ startDate
							+ "</td><th align=left>From Time</th><td>"
							+ fromTime + "</td></tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
							+ endDate + "<th align=left>End Time</th><td>"
							+ toTime + "</td></tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
							+ reason + "</td></tr></table></br></br>";

					desc = desc
							+ "<a href="
							+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
							+ nextApproverID
							+ "&reqId="
							+ Req_Id
							+ "&status=Approve><img src="
							+ "https://portal.microlabs.co.in/images/Approve.png"
							+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
							+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
							+ nextApproverID
							+ "&reqId="
							+ Req_Id
							+ "&status=Reject><img src="
							+ "https://portal.microlabs.co.in/images/Reject.png"
							+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
				} else {
					desc = desc
							+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></center></td></tr> ";
					desc = desc
							+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
					desc = desc + "<tr><th align=left>Emp No.</th><td>" + pernr
							+ "</td><th align=left>Name</th><td>" + name
							+ "</td></tr>";
					desc = desc + "<tr><th align=left>Dept</th><td>" + dep
							+ "</td><th align=left>Desg</th><td>" + desg
							+ "</td></tr>";
					desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td >"+Req_Id+"</td></tr>";

					desc = desc
							+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=1>"
							+ ondutyType + "</td><th>Plant</th><td>" + plant
							+ "</td></tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;Start Date</th><td>"
							+ startDate
							+ "</td><th align=left>From Time</th><td>"
							+ fromTime + "</td></tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
							+ endDate + "<th align=left>End Time</th><td>"
							+ toTime + "</td></tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
							+ reason + "</td></tr></table></br></br>";

					desc = desc
							+ "<a href="
							+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
							+ nextApproverID
							+ "&reqId="
							+ Req_Id
							+ "&status=Approve><img src="
							+ "https://portal.microlabs.co.in/images/Approve.png"
							+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
							+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
							+ nextApproverID
							+ "&reqId="
							+ Req_Id
							+ "&status=Reject><img src="
							+ "https://portal.microlabs.co.in/images/Reject.png"
							+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
				}

			}
			
if(requestFrom.equalsIgnoreCase("Travel Desk")){
				
				TravelRequestForm t =new TravelRequestForm();
				ArrayList data=new ArrayList();
				ArrayList emplist=new ArrayList();
				ArrayList guestlist=new ArrayList();
				String reqType="";
				String lis="select CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1, CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as bookeddate1,"
						+ "CONVERT(varchar(10),Depart_On,103)  as departon,"
						+ "CONVERT(varchar(10),Return_On,103)  as returnon,CONVERT(varchar(10),billdate,103)  as billdate1,* from Travel_Desk where reqNo='"+Req_Id+"' ";
				ResultSet ff=ad.selectQuery(lis);
				try {
					if(ff.next())
					{
						
						t.setRequestNumber(ff.getInt("reqNo"));
						t.setReqType(ff.getString("Req_type"));
						reqType=ff.getString("Req_type");
						t.setTypeOfTravel(ff.getString("Type_of_travel"));
						t.setTravelFor(ff.getString("Travel_for"));
						t.setTravelRequestFor(ff.getString("Travel_Request_for"));
						reqEmp=Integer.toString(ff.getInt("PERNR"));
						
						
					
						
						
						
						t.setModeOfTravel(ff.getString("Mode_of_travel"));
						t.setFromPlace(ff.getString("From_place"));
						t.setDepartOn(ff.getString("departon"));
						t.setDepartTime(ff.getString("Preferred_depart_time"));
						//t.setReturnTime(ff.getString("Preferred_return_time"));	
					//	t.setDepartRemarks(ff.getString("Depart_Time_remarks"));
						//t.setReturnRemarks(ff.getString("Return_Time_remarks"));
		               t.setToPlace(ff.getString("To_place"));
		             //  t.setVia(ff.getString("via"));
		              // t.setRemarks(ff.getString("purposetext"));
		               t.setPurposeOfVisit(ff.getString("purposetype"));
		            //   t.setReturnOn(ff.getString("returnon"));
						if(!ff.getString("Preferred_return_time").equalsIgnoreCase("null"))
						{
							t.setReturnTime(ff.getString("Preferred_return_time"));	
						}
						else
						{
							t.setReturnTime(ff.getString(""));	
						}
					/*	if(!ff.getString("Depart_Time_remarks").equalsIgnoreCase("null"))
						{
							t.setDepartRemarks(ff.getString("Depart_Time_remarks"));	
						}
						else
						{
							t.setDepartRemarks(ff.getString(""));	
						}
					
						if(!ff.getString("Return_Time_remarks").equalsIgnoreCase("null"))
						{
							t.setReturnRemarks(ff.getString("Return_Time_remarks"));
						}
						else
						{
							t.setReturnRemarks(ff.getString(""));	
						}*/
						
		               t.setToPlace(ff.getString("To_place"));
		              
		               
		              
		            	   t.setVia(ff.getString("Via"));
				
		               
		               /*if(!ff.getString("Remarks").equalsIgnoreCase("null"))
						{
		            	   t.setRemarks(ff.getString("Remarks"));
						}
						else
						{
							 t.setRemarks(ff.getString(""));	
						}*/
		             
		             
		               if(!ff.getString("purposetext").equalsIgnoreCase("NULL"))
		               {
		            	   t.setPurposeOfVisit(ff.getString("purposetext")); 
		               }
		               else
		               {
		            	   t.setPurposeOfVisit("");  
		               }
		            	
		            	   if(ff.getString("returnon")!=null)
			               {
		            		   t.setReturnOn(ff.getString("returnon"));
			               }
			               else
			               {
			            	   t.setReturnOn("");  
			               }
		             t.setReqDate(ff.getString("Created_date1"));
		              t.setTravelagentname(ff.getString("travelagentname"));
		              t.setBillno(ff.getString("billno"));
		              t.setBilldate(ff.getString("billdate1"));
		              t.setBillamount(ff.getString("billamount"));
		              t.setBookingstatus(ff.getString("bookingstatus"));
		              t.setBookeddate(ff.getString("bookeddate1"));
		              t.setBookedby(Empname(ff.getString("bookedby")));
		              t.setConfirmdate(ff.getString("confirmdate1"));
		              t.setConfirmby(Empname(ff.getString("confirmby")));
		              t.setConfirmstatus(ff.getString("confirmstatus"));
		              
		              
		              approvermail= ff.getString("Pending_Approver");
		              data.add(t);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("data", data);
				
				
				//requester 
				
				 HelpDeskForm help=new HelpDeskForm();
				String data1="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+reqEmp+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data1);
				try {
					while(rs.next())
					{
						requesterName=rs.getString("EMP_FULLNAME");
						
						
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
				
				
				mailForm.setEmpname(requesterName);
				mailForm.setSendermail("");
				
				mailForm.setAttachNames("TP_"+Req_Id);
				InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				Properties props = null;
				try {
					props = PropertiesLoaderUtils.loadAllProperties("db.properties");
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
							
							
				String uploadFilePath=props.getProperty("file.uploadFilePath");
				String filePath=uploadFilePath+"/EMicro Files/ESS/Travel Request/PDF/";
				filePath=filePath.replace("/", "\\")+""+Req_Id+"_Travel_desk_report.pdf";
				
				mailForm.setAttach(filePath);
				
							
							
							
				//Approver
				String getAppEmailID="select EMAIL_ID from emp_official_info where PERNR='"+nextApproverID+"'";
				ResultSet approverRS=ad.selectQuery(getAppEmailID);
				while(approverRS.next()){
					approvermail = approverRS.getString(1);
				}
				mailForm.setToAddress(approvermail);
				System.out.println("m -> "+approvermail);
				mailForm.setccAddress("");
				mailForm.setbccAddress("");
				 desc = "";
				mailForm.setSubject("Travel Request From "+requesterName);
				desc=desc+"";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
		             +"TABLE {border-collapse:collapse;border:1px solid black;}"
		             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
		             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
				 +"</STYLE>";
				desc=desc+"<html><body><img src="+"http://portal.microlabs.co.in/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=5 ><center><big>Travel Request Details</big></center></th></tr>";

				desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
				+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
				+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
				+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
				
				desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
						+ "	 <tr><td>Request Type</td><td colpsan=3><big><b>"+t.getReqType()+"</b></big></td></tr>"
				+ " <tr><td>Request No:</td><td>"+Req_Id+"</td><td>Request Date:</td><td>"+t.getReqDate()+"</td></tr>"
				+ "	<tr><td>Type of Travel:</td><td>"+t.getTypeOfTravel()+"</td>	<td>Travel For:</td><td>"+t.getTravelFor()+"</td>	</tr>"					
				+ " <tr><td>Mode Of Travel:</td><td>"+t.getModeOfTravel()+"</td><td>Travel Request For:</td><td>"+t.getTravelRequestFor()+"</td>	</tr>"
				+ " <tr><td>From:</td><td colspan=3>"+t.getFromPlace()+"</td></tr>"
		        + " <tr><td>Depart On:</td><td colspan=3>"+t.getDepartOn()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Preferred Time:</b>&nbsp;&nbsp;"+t.getDepartTime()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>"
			    + "	<tr><td>To:</td><td colspan=3>"+t.getToPlace()+"</td></tr>"
			    + " <tr><td>Return On:</td><td colspan=3>"+t.getReturnOn()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Preferred Time:</b>&nbsp;&nbsp;"+t.getReturnTime()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>"
			    + "<tr><td>Purpose of Visit:</td><td colspan=3>"+t.getPurposeOfVisit()+"</td></tr>";
				/*+ "<tr>	<td>Remarks:</td><td colspan=6 >"+t.getRemarks()+"</td></tr>";*/
			
				desc=desc+"</table><br><br>";
				
				
				desc=desc+"<a href="+"https://portal.microlabs.co.in/travelrequest.do?method=approveMailRequest&userID="+nextApproverID+"&reqId="+Req_Id+"&status=Approve>"
						+ "<img src="+"https://portal.microlabs.co.in/images/Approve.png"+" height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;"
								+ "<a href="+"https://portal.microlabs.co.in/travelrequest.do?method=approveMailRequest&userID="+nextApproverID+"&reqId="+Req_Id+"&status=Reject>"
								+ "<img src="+"https://portal.microlabs.co.in/images/Reject.png"+" height=20 width=80 title=Reject border=0 /></a></br></br>";
				
				/*desc=desc+"<a href="+"http://localhost:8090/EMicro_Main/travelrequest.do?method=approveMailRequest&userID="+nextApproverID+"&reqId="+Req_Id+"&status=Approve>"
						+ "<img src="+"http://localhost:8090/EMicro_Main/images/Approve.png"+" height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;"
								+ "<a href="+"http://localhost:8090/EMicro_Main/travelrequest.do?method=approveMailRequest&userID="+nextApproverID+"&reqId="+Req_Id+"&status=Reject>"
								+ "<img src="+"http://localhost:8090/EMicro_Main/images/Reject.png"+" height=20 width=80 title=Reject border=0 /></a></br></br>";
				*/
			   
			    desc=desc+"</br></br>";
				desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"http://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
			    desc=desc+"</body></html>";
			
			    mailForm.setDescription(desc);
				//mailAction.mailSendToRecipient(request, mailForm,"request");
			}
			if (requestFrom.equalsIgnoreCase("Leave")) {

				String tName = "leave_details";
				String leavedetails = "select * from " + tName
						+ " where Req_Id='" + Req_Id + "'";
				ResultSet ldRS = ad.selectQuery(leavedetails);
				while (ldRS.next()) {
					leaveType = ldRS.getString("leave_type");
					if (leaveType.equalsIgnoreCase("1")) {
						leaveType = "Casual";
					}
					if (leaveType.equalsIgnoreCase("2")) {
						leaveType = "Sick";
					}
					if (leaveType.equalsIgnoreCase("3")) {
						leaveType = "Privilege";
					}
					if (leaveType.equalsIgnoreCase("4")) {
						leaveType = "Loss Of Pay";
					}
					if (leaveType.equalsIgnoreCase("5")) {
						leaveType = "Maternity";
					}
					if (leaveType.equalsIgnoreCase("6")) {
						leaveType = "Comp-Off";
					}
					startDate = EMicroUtils
							.display1(ldRS.getDate("start_date"));
					startDur = ldRS.getString("start_duration");
					if (startDur.equalsIgnoreCase("FD")) {
						startDur = "Full Day";
					}
					if (startDur.equalsIgnoreCase("FH")) {
						startDur = "First Half";
					}
					if (startDur.equalsIgnoreCase("SH")) {
						startDur = "Second Half";
					}
					endDate = EMicroUtils.display1(ldRS.getDate("end_date"));
					endDur = ldRS.getString("end_duration");
					if (endDur.equalsIgnoreCase("FD")) {
						endDur = "Full Day";
					}
					if (endDur.equalsIgnoreCase("FH")) {
						endDur = "First Half";
					}
					if (endDur.equalsIgnoreCase("SH")) {
						endDur = "Second Half";
					}
					reasonType = ldRS.getString("reasonType");
					reason = ldRS.getString("reason");
					pApprover = ldRS.getString("Pending_Approver");

					noOfDays = ldRS.getString("no_of_days");

					submitDate = ldRS.getString("submit_date");
				}

				String checkESSApproverList = "select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"
						+ requesterID
						+ "' and essType='Leave' and ApproverId='"
						+ nextApproverID + "' and ess.ApproverId=emp.PERNR";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					approvermail = rsCheckInEss.getString("EMAIL_ID");
				}
				try {
					int year = Calendar.getInstance().get(Calendar.YEAR);

					String getleaveBalence = "select t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
							+ requesterID
							+ "' and t.lv_calyear='"
							+ year
							+ "' and t.lv_typeid=m.lv_typeid";
					ResultSet rsBalance = ad.selectQuery(getleaveBalence);
					ArrayList balList = new ArrayList();
					while (rsBalance.next()) {
						String lvType = rsBalance.getString("lv_type");
						if (lvType.equalsIgnoreCase("CASUAL")) {
							casualOpeningBal = rsBalance.getFloat("lv_opbal");
							casualAvailedBal = rsBalance.getFloat("lv_availed");
							casualClosingBal = rsBalance.getFloat("lv_clbal");
							casualAwatingBal = rsBalance.getFloat("lv_awtBal");
						}
						if (lvType.equalsIgnoreCase("SICK")) {
							sickOpeningBal = rsBalance.getFloat("lv_opbal");
							sickAvailedBal = rsBalance.getFloat("lv_availed");
							sickClosingBal = rsBalance.getFloat("lv_clbal");
							sickAwatingBal = rsBalance.getFloat("lv_awtBal");
						}
						if (lvType.equalsIgnoreCase("PRIVILEGE")) {
							previlegeOpeningBal = rsBalance
									.getFloat("lv_opbal");
							previlegeAvailedBal = rsBalance
									.getFloat("lv_availed");
							previlegeClosingBal = rsBalance
									.getFloat("lv_clbal");
							previlegeAwatingBal = rsBalance
									.getFloat("lv_awtBal");
						}
						if (lvType.equalsIgnoreCase("MATERNITY")) {
							maternityOpeningBal = rsBalance
									.getFloat("lv_opbal");
							maternityAvailedBal = rsBalance
									.getFloat("lv_availed");
							maternityClosingBal = rsBalance
									.getFloat("lv_clbal");
							maternityAwatingBal = rsBalance
									.getFloat("lv_awtBal");
						}
						LeaveForm form2 = new LeaveForm();
						form2.setLeaveType(rsBalance.getString("lv_type"));

						form2.setOpeningBalence(rsBalance.getInt("lv_opbal"));
						form2.setAvalableBalence(rsBalance
								.getFloat("lv_availed"));
						form2.setClosingBalence(rsBalance.getFloat("lv_clbal"));
						form2.setAwaitingBalence(rsBalance
								.getFloat("lv_awtBal"));
						balList.add(form2);
					}
					request.setAttribute("LeaveBalenceList", balList);
					
///prev year availed leavs
					
					String getleave="select t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"+user.getEmployeeNo()+"' and t.lv_calyear='"+(year-1)+"' and t.lv_typeid=m.lv_typeid";
					ResultSet rsBalanc=ad.selectQuery(getleave);
				
					while(rsBalanc.next())
					{
						String lvType=rsBalanc.getString("lv_type");
						if(lvType.equalsIgnoreCase("CASUAL"))
						{
					
							prevcasualAvailedBal=rsBalanc.getFloat("lv_availed");
							
						}
						if(lvType.equalsIgnoreCase("SICK"))
						{
							
							prevsickAvailedBal=rsBalanc.getFloat("lv_availed");
							
						}
						if(lvType.equalsIgnoreCase("PRIVILEGE"))
						{
							
							prevprevilegeAvailedBal=rsBalanc.getFloat("lv_availed");
						
						}
						
						
					}

					if (requestFrom.equalsIgnoreCase("Leave")) {
						 ArrayList lastleaves=new ArrayList();
						   int recLv_record=0;
							String getLastLeaveRecord="select id,user_id,leave_type,m.lv_type,CONVERT(CHAR(10), [start_date], 103) as start_date,CONVERT(CHAR(10),[end_date], 103) as end_date," +
							"no_of_days,CONVERT(CHAR(10), [approved_date], 103) as approved_date from leave_details as l,lv_type_m as m where user_id='"+requesterID+"' and l.leave_type=m.lv_typeid " +
									"and Approvel_Status='Approved' order by id desc";
							ResultSet rsRecentrecord=ad.selectQuery(getLastLeaveRecord);
							while(rsRecentrecord.next()){
								LeaveForm a=new LeaveForm();
								recLv_record=1;
								a.setLeaveType(rsRecentrecord.getString("lv_type"));
								a.setStartDate(rsRecentrecord.getString("start_date"));
							    a.setEndDate(rsRecentrecord.getString("end_date"));
								a.setNoOfDays(rsRecentrecord.getString("no_of_days"));
								a.setApprovedDate(rsRecentrecord.getString("approved_date"));
								
								lastleaves.add(a);
							}
						
						String gender = "";
						String pernr = "";
						String name = "";
						String doj = "";
						String dep = "";
						String desg = "";
						String getGender = "select emp.PERNR,emp.EMP_FULLNAME,emp.SEX,emp.DOJ,dep.DPTSTXT,desg.DSGSTXT from emp_official_info emp,DEPARTMENT as dep,DESIGNATION as desg where emp.PERNR='"
								+ requesterID
								+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
						ResultSet rsGender = ad.selectQuery(getGender);
						while (rsGender.next()) {
							gender = rsGender.getString("SEX");
							pernr = rsGender.getString("PERNR");
							name = rsGender.getString("EMP_FULLNAME");
							doj = rsGender.getString("DOJ");
							dep = rsGender.getString("DPTSTXT");
							desg = rsGender.getString("DSGSTXT");

						}

						desc = "Requested By : " + name + "</br>";
						desc = desc + "Requested Date : " + submitDate
								+ "</br>";
						mailForm.setSubject("Leave Request From " + name);
						desc = desc + "";
						desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
								+ "TABLE {border-collapse:collapse;border:1px solid black;}"
								+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
								+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
								+ "</STYLE>";

						desc=desc+"<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="+"http://localhost:8090/EMicro_Main/images/logo.png"+" height=60 width=50  /></center></td></tr> ";
						desc=desc+"<tr><th colspan=5 ><center>Requester Details</center></th></tr>";
						desc=desc+"<tr><th align=left>Emp No.</th><td>"+pernr+"</td><th align=left>Name</th><td colspan=2>"+name+"</td></tr>";
						desc=desc+"<tr><th align=left>Dept</th><td>"+dep+"</td><th align=left>Desg</th><td colspan=2>"+desg+"</td></tr>";			
						desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td  colspan=2>"+Req_Id+"</td></tr>";
						
						desc=desc+"<tr><th colspan=6><center>Leave Details</center></th></tr>";
						desc=desc+"<tr><th align=left colspan=2>&nbsp;Leave Type</th><td>"+leaveType+"</td><th>No.Of Days</th><td colspan=2>"+noOfDays+"</tr>";
						desc=desc+"<tr><th align=left colspan=2>&nbsp;From</th><td>"+startDate+"</td><th align=left>Duration</th><td colspan=2>"+startDur+"</td></tr>";
						desc=desc+"<tr><th align=left colspan=2>&nbsp;To</th><td>"+endDate+"<th align=left>Duration</th><td colspan=2>"+endDur+"</td></tr>";
						desc=desc+"<tr><th align=left colspan=2>&nbsp;Reason Type</th><td colspan=4>"+reasonType+"</td></tr>";
						desc=desc+"<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=4>"+reason+"</td></tr>";							   
						
						 desc=desc+"<tr><th colspan=6>Leave Balances</th></tr>";
						    desc=desc+"<tr><th>Type</th><th>Opening</th><th>Availed</th><th>Closing</th><th>Awaiting For Approval</th><th>Previous Year Availed("+(year-1)+")</th></tr>";
						    desc=desc+"<tr><th align=left>CASUAL</TH><TD align=right>"+casualOpeningBal+"</TD><TD align=right>"+casualAvailedBal+"</TD><TD align=right>"+casualClosingBal+"</TD><TD align=right>"+casualAwatingBal+"</TD><TD align=right>"+prevcasualAvailedBal+"</TD></TR>";
						    desc=desc+"<tr><th align=left>SICK</TH><TD align=right>"+sickOpeningBal+"</TD><TD align=right>"+sickAvailedBal+"</TD><TD align=right>"+sickClosingBal+"</TD><TD align=right>"+sickAwatingBal+"</TD><TD align=right>"+prevsickAvailedBal+"</TD></TR>";
						    if(gender.equalsIgnoreCase("F")){
						    	desc=desc+"<tr><th align=left>MATERNITY</TH><TD align=right>"+maternityOpeningBal+"</TD><TD align=right>"+maternityAvailedBal+"</TD><TD align=right>"+maternityClosingBal+"</TD><TD align=right>"+maternityAwatingBal+"</TD><TD align=right>&nbsp;</TD></TR>";
						    }
						    desc=desc+"<tr><th align=left>PRIVILEGE</TH><TD align=right>"+previlegeOpeningBal+"</TD><TD align=right>"+previlegeAvailedBal+"</TD><TD align=right>"+previlegeClosingBal+"</TD><TD align=right>"+previlegeAwatingBal+"</TD><TD align=right>"+prevprevilegeAvailedBal+"</TD></TR>";
						    desc=desc+"<tr><th align=left>Total</TH><TD align=right>"+(casualOpeningBal+sickOpeningBal+maternityOpeningBal+previlegeOpeningBal)+"</TD><TD align=right>"+(casualAvailedBal+sickAvailedBal+maternityAvailedBal+previlegeAvailedBal)+"</TD>"
						    		+ "<TD align=right>"+(casualClosingBal+sickClosingBal+maternityClosingBal+previlegeClosingBal)+"</TD><TD align=right>"+(casualAwatingBal+sickAwatingBal+maternityAwatingBal+previlegeAwatingBal)+"</TD><TD align=right>"+(prevcasualAvailedBal+prevsickAvailedBal+prevprevilegeAvailedBal)+"</TD></TR>";
						    
						    desc=desc+ "</table>";
						desc = desc + "</br></br></br>";

						desc = desc
								+ "<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
								+ nextApproverID
								+ "&reqId="
								+ Req_Id
								+ "&status=Approve><img src="
								+ "https://portal.microlabs.co.in/images/Approve.png"
								+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
								+ nextApproverID
								+ "&reqId="
								+ Req_Id
								+ "&status=Reject><img src="
								+ "https://portal.microlabs.co.in/images/Reject.png"
								+ " height=20 width=80 title=Reject border=0 /></a></br></br>";
					    desc=desc+"</br></br>";
					    desc=desc+"<u><b>Last Leave Details :-</b></u></br></br>";
					    desc=desc+"<table><tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No Of Days</th><th>Approve Date</th></tr>";
					    if(recLv_record==1){
					    	Iterator r=lastleaves.iterator();
					    	while(r.hasNext()) {
								
								LeaveForm leave=(LeaveForm)r.next();
								 desc=desc+"<tr><td>"+leave.getLeaveType()+"</td><td>"+leave.getStartDate()+"</td><td>"+leave.getEndDate()+"</td><td>"+leave.getNoOfDays()+"</td><td>"+leave.getApprovedDate()+"</td></tr>";
					    	}
					   
					    	desc=desc+"</table>";
					    }else{
					    	desc=desc+"<tr><td colspan=5>No Leave Details</tr></table>";
					    }
						desc = desc + "</br></br></br></br>";
						desc = desc
								+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
								+ "https://portal.microlabs.co.in/"
								+ ">EMicro Portal</a> .</span> ";
						desc = desc + "</body></html>";
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (requestFrom.equalsIgnoreCase("Material Code Request")) {
				 
				 String tName = "";
					
					String purchase_group_desc="";

					String Storage_location_Name="";
					String ltxt="";
					String Shortname="";
					String longname="";
					String matType = "";
					String manufactured_at="";
					String brand_desc="";
					String div_desc="";
					String detailed_Justification="";
					String detailed_Specification="";
				 tName = "material_code_request";
					
					String reqMatType="";
				 String approver1="";
					String parallelApp1="";
					String parallelApp2="";

				String newmatcode = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,loc.LOCATION_CODE,Type,"
						+ "MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.MATERIAL_GROUP_ID,mType.M_DESC,purchase_group_desc ,mat.storage_location_id,Storage_location_Name,UNIT_MESUREMENT.ltxt "
						+ " ,material_group.ltxt as matgroup"
						+ " , material_short_name, Material_long_name,manufactured_at,brand_desc,div_desc,detailed_Justification,detailed_Specification from material_code_request as mat "
						+ " left outer join storage_location on storage_location.storage_location_id=mat.storage_location_id and storage_location.mat_type=mat.type "
						+ " left outer join UNIT_MESUREMENT on UNIT_MESUREMENT.Unit_of_meas_id=mat.unit_of_meas_id"
						+ " left outer join material_group on material_group.material_group_id=mat.MATERIAL_GROUP_ID"
						+ " left outer join purchase_group on mat.purchase_group_id =purchase_group.purchase_group_id "
						+ " left outer join brand on mat.brand_id =brand.brand_code "
						+ " left outer join division on mat.DIVISION_ID =division.div_code , "
						+ "Location as loc,emp_official_info as emp,MATERIAL_TYPE mType where "
						+ "REQUEST_NO='"
						+ Req_Id
						+ "' and loc.LOCID=mat.LOCATION_ID and emp.PERNR=mat.CREATED_BY and mType.MATERIAL_GROUP_ID=mat.Type ";


				ResultSet ldRS = ad.selectQuery(newmatcode);
				while (ldRS.next()) {
					submitDate = ldRS.getString("REQUEST_DATE");
					location = ldRS.getString("LOCATION_CODE");
					materialType = ldRS.getString("Type") + "-"
							+ ldRS.getString("M_DESC");
					reqMatType = ldRS.getString("Type");
					matType = ldRS.getString("Type");
					matsName = ldRS.getString("MATERIAL_SHORT_NAME");
					requesterName = ldRS.getString("EMP_FULLNAME");
					matGroup = ldRS.getString("matgroup");
					purchase_group_desc = ldRS.getString("purchase_group_desc");
					Storage_location_Name=ldRS.getString("Storage_location_Name");
					ltxt=ldRS.getString("ltxt");
					Shortname=ldRS.getString("material_short_name");
					longname=ldRS.getString("Material_long_name");
					manufactured_at=ldRS.getString("manufactured_at");
					brand_desc=ldRS.getString("brand_desc");
					div_desc=ldRS.getString("div_desc");
					detailed_Justification=ldRS.getString("detailed_Justification");
					detailed_Specification=ldRS.getString("detailed_Specification");



				}
				if (matType.equals("FG") || matType.equals("HAWA")) {
					String matDetails = "select loc.LOCATION_CODE,mat.Type,mat.CREATED_BY,mat.DOMESTIC_OR_EXPORTS from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"
							+ Req_Id + "' and mat.LOCATION_ID=loc.LOCID";
					ResultSet rsDetails = ad.selectQuery(matDetails);
					try {
						while (rsDetails.next()) {
							matGroup = rsDetails
									.getString("DOMESTIC_OR_EXPORTS");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (matGroup.equals("D"))
						matGroup = "Domestic";
					if (matGroup.equals("E"))
						matGroup = "Export";
					if (matGroup.equals("V"))
						matGroup = "V";
				}
				int checkApprover = 0;
				String getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
						+ "Material_Approvers as app,emp_official_info AS emp where app.Location='"
						+ location
						+ "' and app.Material_Type='"
						+ reqMatType
						+ "' and app.Material_Group='"
						+ matGroup
						+ "' "
						+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
				ResultSet rsApp = ad.selectQuery(getMatApprovers);
				while (rsApp.next()) {
					checkApprover = 1;
					approver1 = rsApp.getString("Approver_Id");
					parallelApp1 = rsApp.getString("Parllel_Approver_1");
					parallelApp2 = rsApp.getString("Parllel_Approver_2");
				}
				if (checkApprover == 0) {
					getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
							+ "Material_Approvers as app,emp_official_info AS emp where app.Location='"
							+ location
							+ "' and app.Material_Type='"
							+ reqMatType
							+ "' and app.Material_Group='' "
							+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
					rsApp = ad.selectQuery(getMatApprovers);
					while (rsApp.next()) {
						approver1 = rsApp.getString("Approver_Id");
						parallelApp1 = rsApp.getString("Parllel_Approver_1");
						parallelApp2 = rsApp.getString("Parllel_Approver_2");
					}
				}
				

				mailForm.setSubject("Material Code Request From "
					+ requesterName);
			desc = desc + "";
			desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
					+ "TABLE {border-collapse:collapse;border:1px solid black;}"
					+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
					+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
					+ "</STYLE>";
			desc = desc
					+ "<html><body>"
					+ "<table><tr ><td rowspan=2><img src="
					+ "https://portal.microlabs.co.in/images/logo.png" 								
					+ " height=60 width=50  /></br></td>"
					+ " <td colspan=3><b><center> MICRO LABS LIMITED</center></b></td></tr>"
					+ " <tr><td colspan=3><b><center> eMicro Portal Notification</center></b></td></tr>"
					+ "<tr><th colspan=4><center>Material Code Request Details</center></th></tr>";
			if(matType.equalsIgnoreCase("RM"))
			{	
			desc = desc+""
					+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
					+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
					+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
					+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
					+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
					+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
					+ "<tr><th align=left>Mat.Group</th><td>"+matGroup+"</td><th align=left>UOM</th><td>"+ltxt+"</td></tr> "
					+ "<tr> <th align=left>Purchasing Group</th><td colspan=3>"+purchase_group_desc+"</td> </tr>"

					+ "</table><br><br>";
			}
			if(matType.equalsIgnoreCase("LC"))
			{	
			desc = desc+""
					+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
					+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
					+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
					+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
					+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
					+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
					+ "<tr><th align=left>Mat.Group</th><td>"+matGroup+"</td><th align=left>UOM</th><td>"+ltxt+"</td></tr> "
					+ "<tr> <th align=left>Purchasing Group</th><td colspan=3>"+purchase_group_desc+"</td> </tr>"
					+ "<tr> <th align=left>Justification</th><td colspan=3>"+detailed_Justification+"</td> </tr>"
					+ "<tr> <th align=left>Specification</th><td colspan=3>"+detailed_Specification+"</td> </tr>"
					+ "</table><br><br>";
			}

			if(matType.equalsIgnoreCase("BULK"))
			{	
			desc = desc+""
					+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
					+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
					+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
					+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
					+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
					+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
					+ "<tr><th align=left>Mat.Group</th><td>"+matGroup+"</td><th align=left>UOM</th><td>"+ltxt+"</td></tr> "
					+ "</table><br><br>";
			}

			if(matType.equalsIgnoreCase("OSE"))
			{	
			desc = desc+""
					+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
					+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
					+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
					+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
					+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
					+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
					+ "<tr><th align=left>Mat.Group</th><td>"+matGroup+"</td><th align=left>UOM</th><td>"+ltxt+"</td></tr> "
					+ "<tr> <th align=left>Purchasing Group</th><td colspan=3>"+purchase_group_desc+"</td> </tr>"
					+ "<tr> <th align=left>Justification</th><td colspan=3>"+detailed_Justification+"</td> </tr>"
					+ "<tr> <th align=left>Specification</th><td colspan=3>"+detailed_Specification+"</td> </tr>"
					+ "</table><br><br>";
			}




			if(matType.equalsIgnoreCase("PM"))
			{

				desc = desc+""
						+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
						+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
						+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
						+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
						+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
						+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
						+ "<tr><th align=left>Mat.Group</th><td colspan=3>"+matGroup+"</td></tr> "
						+ "<tr> <th align=left>Purchasing Group</th><td colspan=3>"+purchase_group_desc+"</td> </tr>"
						
						+ "</table><br><br>";


			}

			if(matType.equalsIgnoreCase("FG"))
			{

				desc = desc+""
						+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
						+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
						+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
						+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
						+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
						+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
						+ "<tr><th align=left>Mat.Group</th><td>"+matGroup+"</td><th align=left>Manufacture At</th><td>"+manufactured_at+"</td></tr> "
						+ "<tr><th align=left >UOM</th><td colspan=3>"+ltxt+"</td></tr>"
						+ "</table><br><br>";


			}

			if(matType.equalsIgnoreCase("PPC"))
			{

				desc = desc+""
					+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
					+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
					+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
					+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
					+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
					+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
					+ "<tr><th align=left>Mat.Group</th><td>"+matGroup+"</td><th align=left>UOM</th><td>"+ltxt+"</td></tr> "
					+ "<tr><th align=left>Brand</th><td>"+brand_desc+"</td><th align=left>Division</th><td>"+div_desc+"</td></tr> "
					+ "<tr> <th align=left>Purchasing Group</th><td colspan=3>"+purchase_group_desc+"</td> </tr>"
					+ "<tr> <th align=left>Justification</th><td colspan=3>"+detailed_Justification+"</td> </tr>"
						+ "<tr> <th align=left>Specification</th><td colspan=3>"+detailed_Specification+"</td> </tr>"

					+ "</table><br><br>";
		   }



			desc = desc
					+ "<i> <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information is being provided by  <a href=https://portal.microlabs.co.in>EMicro Portal</a> and it is an auto generated mail notification, Pl. do not reply.</span></i> ";
			desc = desc + "</body></html>";

} else if (requestFrom.equalsIgnoreCase("Vendor Master")) {
	 
	 String account_group_id="";
		String title="";
		String address1="";
		String address2="";
		String address3="";
		String address4="";
		String pinCode="";
		String landx="";
		String state="";
		String bezei="";
		String landLine_No="";
		String mobile_no="";
		String faxNo="";
		String email_Id="";
		String account_group_name=""; 
		
		 String approver1="";
			String parallelApp1="";
			String parallelApp2="";
	 
	 String getVendorDet = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,NAME,CITY,v.Type_Of_Vendor,emp.EMP_FULLNAME "
			+ " ,landx,fax_No,v.title,address1,address2,address3,address4,pinCode,landLine_No,mobile_no,v.email_Id,bezei,account_group_name from "
			+ " vendor_master_m as v"
			+ " left outer join Country on Country.land1=v.COUNTRY_ID "
			+ " left outer join state on state.id=v.state "
			+ " left outer join account_group_m on account_group_m.Account_group_id=v.Account_group_id "
			+ " ,emp_official_info as emp where  REQUEST_NO='"
			+ Req_Id + "'  and emp.PERNR=v.CREATED_BY";
	ResultSet rsVendor = ad.selectQuery(getVendorDet);
	while (rsVendor.next()) {
		submitDate = rsVendor.getString("REQUEST_DATE");
		vendorName = rsVendor.getString("NAME");
		vendorCity = rsVendor.getString("CITY");
		vendorType = rsVendor.getString("Type_Of_Vendor");
		title = rsVendor.getString("title");
		address1=rsVendor.getString("address1");
		address2=rsVendor.getString("address2");
		address3=rsVendor.getString("address3");
		address4=rsVendor.getString("address4");
		pinCode=rsVendor.getString("pinCode");
		landx=rsVendor.getString("landx");
		bezei=rsVendor.getString("bezei");
		landLine_No=rsVendor.getString("landLine_No");
		mobile_no=rsVendor.getString("mobile_no");
		faxNo=rsVendor.getString("fax_No");
		email_Id=rsVendor.getString("email_Id");
		account_group_name=rsVendor.getString("account_group_name");







		if (vendorType.equalsIgnoreCase("I"))
			vendorType = "IMPORTER";
		if (vendorType.equalsIgnoreCase("M"))
			vendorType = "MANUFACTURER";
		if (vendorType.equalsIgnoreCase("D"))
			vendorType = "DEALER";
		if (vendorType.equalsIgnoreCase("FD"))
			vendorType = "FIRST STAGE DEALER OF INDIGENOUS";
		if (vendorType.equalsIgnoreCase("SD"))
			vendorType = "SECOND STAGE DEALER OF INDIGENOUS";
		if (vendorType.equalsIgnoreCase("MD"))
			vendorType = "DEPOT MANUFACTURER";


		requesterName = rsVendor.getString("EMP_FULLNAME");
	}

	int checkApprover = 0;
	String getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
			+ "Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='Import' "
			+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
	ResultSet rsApp = ad.selectQuery(getMatApprovers);
	while (rsApp.next()) {
		checkApprover = 1;
		approver1 = rsApp.getString("Approver_Id");
		parallelApp1 = rsApp.getString("Parllel_Approver_1");
		parallelApp2 = rsApp.getString("Parllel_Approver_2");
	}
	if (checkApprover == 0) {
		getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
				+ "Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='Local' "
				+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
		rsApp = ad.selectQuery(getMatApprovers);
		while (rsApp.next()) {
			approver1 = rsApp.getString("Approver_Id");
			parallelApp1 = rsApp.getString("Parllel_Approver_1");
			parallelApp2 = rsApp.getString("Parllel_Approver_2");
		}
	}
	mailForm.setSubject("Vendor Code Request From " + requesterName);
	desc = desc + "";
	desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
			+ "TABLE {border-collapse:collapse;border:1px solid black;}"
			+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
			+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
			+ "</STYLE>";
	desc = desc
			+ " <html><body>"
			+ " <table><tr ><td rowspan=2><img src="
			+ " https://portal.microlabs.co.in/images/logo.png" 
			+ " height=60 width=50  /></br></td>"
			+ " <td colspan=3><b><center> MICRO LABS LIMITED</center></b></td></tr>"
			+ " <tr><td colspan=3><b><center> eMicro Portal Notification</center></b></td></tr>"
			+ "<tr><th colspan=4><center>Vendor Code Request Details</center></th></tr>";

	desc = desc+""
		    + "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
		    + "<tr><th align=left>Req. by</th><td>" + user.getFullName() + "</td> <th align=left>Account Group</th><td>"+account_group_name+"</td> </tr>"
		    + "<tr><th align=left>Vendor Type</th><td  colspan=3>" + vendorType + "</td></tr>  "
			+ "<tr><th align=left>Tittle</th><td>" + title + "</td><th align=left>Name</th><td>" + vendorName + "</td></tr>  "
			+ "<tr><th align=left>Address1</th><td>" + address1 + "</td><th align=left>Address2</th><td>" + address2 + "</td></tr>  "
			+ "<tr><th align=left>Address3</th><td>" + address3 + "</td><th align=left>Address4</th><td>" + address4 + "</td></tr>  "
			+ "<tr><th align=left>City</th><td>" + vendorCity + "</td><th align=left>Pin Code</th><td>" + pinCode + "</td></tr>  "
			+ "<tr><th align=left>Country</th><td>" + landx + "</td><th align=left>State</th><td>" + bezei + "</td></tr> "
			+ "<tr><th align=left>Landline No</th><td>" + landLine_No + "</td><th align=left>Mobile No</th><td>" + mobile_no + "</td></tr> "
			+ "<tr><th align=left>Fax No</th><td>" + faxNo + "</td><th align=left>e-Mail</th><td>" + email_Id + "</td></tr> "
			+ "</table><br><br>";
	desc = desc
			+ "<i> <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information is being provided by  <a href=https://portal.microlabs.co.in>EMicro Portal</a> and it is an auto generated mail notification, Pl. do not reply.</span></i> ";
	desc = desc + "</body></html>";

} else if (requestFrom.equalsIgnoreCase("Customer Master")) {
	 
		String account_group_id="";
		String title="";
		String address1="";
		String address2="";
		String address3="";
		String address4="";
		String pinCode="";
		String landx="";
		String state="";
		String bezei="";
		String landLine_No="";
		String mobile_no="";
		String faxNo="";
		String email_Id="";
		String account_group_name=""; 
		
		String approver1="";
		String parallelApp1="";
		String parallelApp2="";
		String customer_code=""; 
	 
	 String getCustDet="select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,NAME,CITY,v.Customer_Type,emp.EMP_FULLNAME"
			+ " ,landx,fax_No,address1,address2,address3,address4,pinCode,landLine_No,mobile_no,v.email_Id,bezei,account_group_name,cutomer_code from "
			+ "  " +
			" CUSTOMER_MASTER_M as v"
			+ " left outer join Country on Country.land1=v.COUNTRY_ID "
			+ " left outer join state on state.id=v.state "
			+ " left outer join account_group_m on account_group_m.MATERIAL_TYPE_ID=v.Account_group_id "
			+ ",emp_official_info as emp where  REQUEST_NO='"+Req_Id+"'  and emp.PERNR=v.CREATED_BY";
	ResultSet rsCust = ad.selectQuery(getCustDet);
	while(rsCust.next()){
		submitDate = rsCust.getString("REQUEST_DATE");
		customerName=rsCust.getString("NAME");
		customerCity=rsCust.getString("CITY");
		customerType=rsCust.getString("Customer_Type");
		requesterName=rsCust.getString("EMP_FULLNAME");

		address1=rsCust.getString("address1");
		address2=rsCust.getString("address2");
		address3=rsCust.getString("address3");
		address4=rsCust.getString("address4");
		pinCode=rsCust.getString("pinCode");
		landx=rsCust.getString("landx");
		bezei=rsCust.getString("bezei");
		landLine_No=rsCust.getString("landLine_No");
		mobile_no=rsCust.getString("mobile_no");
		faxNo=rsCust.getString("fax_No");
		email_Id=rsCust.getString("email_Id");
		account_group_name=rsCust.getString("account_group_name");
		customer_code=rsCust.getString("cutomer_code");



	}

	int checkApprover=0;	
	String getMatApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from " +
	"Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='Export' " +
	"AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";	
	ResultSet rsApp=ad.selectQuery(getMatApprovers);
	while(rsApp.next()){
		checkApprover=1;
		approver1=rsApp.getString("Approver_Id");
		parallelApp1=rsApp.getString("Parllel_Approver_1");
		parallelApp2=rsApp.getString("Parllel_Approver_2");
	}
	if(checkApprover==0){
		getMatApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from " +
		"Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='Domestic' " +
		"AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";	
		 rsApp=ad.selectQuery(getMatApprovers);
		while(rsApp.next()){
			approver1=rsApp.getString("Approver_Id");
			parallelApp1=rsApp.getString("Parllel_Approver_1");
			parallelApp2=rsApp.getString("Parllel_Approver_2");
		}
	}
	

	mailForm.setSubject("Customer Code Request From "+requesterName);
	desc = desc + "";
	desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
			+ "TABLE {border-collapse:collapse;border:1px solid black;}"
			+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
			+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
			+ "</STYLE>";
	desc = desc
			+ " <html><body>"
			+ " <table><tr ><td rowspan=2><img src="
			+ " https://portal.microlabs.co.in/images/logo.png" 
			+ " height=60 width=50  /></br></td>"
			+ " <td colspan=3><b><center> MICRO LABS LIMITED</center></b></td></tr>"
			+ " <tr><td colspan=3><b><center> eMicro Portal Notification</center></b></td></tr>"
			+ "<tr><th colspan=4><center>Vendor Code Request Details</center></th></tr>";

	desc = desc+""
		    + "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
		    + "<tr><th align=left>Req. by</th><td>" + user.getFullName() + "</td> <th align=left>Account Group</th><td>"+account_group_name+"</td> </tr>"
		    + "<tr><th align=left>Customer Type Type</th><td>" + customerType + "</td> <th align=left>Employee Code</th><td>"+customer_code+"</td> </tr> "
			+ "<tr><th align=left colspan=3>Name</th><td>" + customerName + "</td></tr>  "
			+ "<tr><th align=left>Address1</th><td>" + address1 + "</td><th align=left>Address2</th><td>" + address2 + "</td></tr>  "
			+ "<tr><th align=left>Address3</th><td>" + address3 + "</td><th align=left>Address4</th><td>" + address4 + "</td></tr>  "
			+ "<tr><th align=left>City</th><td>" + customerCity + "</td><th align=left>Pin Code</th><td>" + pinCode + "</td></tr>  "
			+ "<tr><th align=left>Country</th><td>" + landx + "</td><th align=left>State</th><td>" + bezei + "</td></tr> "
			+ "<tr><th align=left>Landline No</th><td>" + landLine_No + "</td><th align=left>Mobile No</th><td>" + mobile_no + "</td></tr> "
			+ "<tr><th align=left>Fax No</th><td>" + faxNo + "</td><th align=left>e-Mail</th><td>" + email_Id + "</td></tr> "
			+ "</table><br><br>";
	desc = desc
			+ "<i> <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information is being provided by  <a href=https://portal.microlabs.co.in>EMicro Portal</a> and it is an auto generated mail notification, Pl. do not reply.</span></i> ";
	desc = desc + "</body></html>";
}
			if (requestFrom.equalsIgnoreCase("Service Master")) {
				 String valuation_desc="";
					String service_Description="";
					String detailed_desc="";
					String machine_name="";
					String app_value="";
					String stxt="";
					
					String customer_code=""; 
					String ltxt="";
					String purchase_group_desc="";
					String detailed_Justification="";
					String detailed_Specification="";
					
					String approver1="";
					String parallelApp1="";
					String parallelApp2="";
				 
				 
				String tName = "SERVICE_MASTER";

				String getdetails = "select emp.EMP_FULLNAME,s.request_date,s.machine_name,s.service_catagory,s.service_description,s.pending_approver,"
						+ "s.purpose,loc.LOCATION_CODE ,UNIT_MESUREMENT.ltxt,purchase_group.purchase_group_desc, service_group.STXT,VALUATION_CLASS.valuation_desc"
						+ " ,service_Description,detailed_desc,machine_name,app_value,Justification "
						+ " from SERVICE_MASTER s "
						+ " left outer join UNIT_MESUREMENT on UNIT_MESUREMENT.Unit_of_meas_id=s.uom"
						+ " left outer join purchase_group on s.purchase_group =purchase_group.purchase_group_id "
						+ " left outer join service_group on s.service_group =service_group.group_id "
						+ " left outer join VALUATION_CLASS on s.VALUATION_CLASS =VALUATION_CLASS.valuation_id "
						+ ",Location loc,emp_official_info as emp where  request_no='"
						+ Req_Id + "' and " + "loc.LOCID=s.plant_code and emp.PERNR=s.CREATED_BY";
				ResultSet detailRS = ad.selectQuery(getdetails);
				while (detailRS.next()) {
					submitDate = detailRS.getString("request_date");
					mcname = detailRS.getString("machine_name");
					serCat = detailRS.getString("service_catagory");
					serDes = detailRS.getString("service_description");
					pApprover = detailRS.getString("pending_approver");
					serPur = detailRS.getString("purpose");
					location = detailRS.getString("LOCATION_CODE");
					ltxt= detailRS.getString("ltxt");
					purchase_group_desc=detailRS.getString("purchase_group_desc");
					stxt=detailRS.getString("stxt");
					valuation_desc=detailRS.getString("valuation_desc");
					service_Description=detailRS.getString("service_Description");
					detailed_desc=detailRS.getString("detailed_desc");
					machine_name=detailRS.getString("machine_name");
					app_value=detailRS.getString("app_value");
					detailed_Justification=detailRS.getString("Justification");

					requesterName = detailRS.getString("EMP_FULLNAME");
					}

				int checkApprover = 0;
				String getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
						+ "Material_Approvers as app,emp_official_info AS emp where app.Location='"
						+ location
						+ "' and app.Material_Type='Service Master' and app.Material_Group='' "
						+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
				ResultSet rsApp = ad.selectQuery(getMatApprovers);
				while (rsApp.next()) {
					checkApprover = 1;
					approver1 = rsApp.getString("Approver_Id");
					parallelApp1 = rsApp.getString("Parllel_Approver_1");
					parallelApp2 = rsApp.getString("Parllel_Approver_2");
				}
				
				

				mailForm.setSubject("Service Code Request From "
					+ requesterName);
			desc = desc + "";
			desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
					+ "TABLE {border-collapse:collapse;border:1px solid black;}"
					+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
					+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
					+ "</STYLE>";
			desc = desc
					+ " <html><body>"
					+ " <table><tr ><td rowspan=2><img src="
					+ " https://portal.microlabs.co.in/images/logo.png"  
					+ " height=60 width=50  /></br></td>"
					+ " <td colspan=3><b><center> MICRO LABS LIMITED</center></b></td></tr>"
					+ " <tr><td colspan=3><b><center> eMicro Portal Notification</center></b></td></tr>"
					+ "<tr><th colspan=4><center>Service Code Request Details</center></th></tr>";

			desc = desc+""
				    + "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
				    + "<tr><th align=left>Req. by</th><td>" + user.getFullName() + "</td> <th align=left>Service Description</th><td>"+serDes+"</td> </tr>"
					+ "<tr><th align=left>Detailed Service Description</th><td  colspan=3>" + detailed_desc + "</td></tr>"
					+ "<tr><th align=left>UOM</th><td>" + ltxt + "</td> <th align=left>Purchase Group</th><td>"+purchase_group_desc+"</td> </tr>"
					+ "<tr><th align=left>Service Category</th><td>" +serCat+ "</td> <th align=left>Service Group</th><td>"+stxt+"</td> </tr>"
					+ "<tr><th align=left>Equipment/Machine Name</th><td>" + machine_name + "</td> <th align=left>Approximate Value </th><td>"+app_value+"</td> </tr>"
					+ "<tr><th align=left>Justification</th><td>" + detailed_Justification + "</td> <th align=left>Valuation Class </th><td>"+valuation_desc+"</td> </tr>";
			desc = desc
					+ "</table><br><br>";


			desc = desc
					+ "<i> <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information is being provided by  <a href=https://portal.microlabs.co.in>EMicro Portal</a> and it is an auto generated mail notification, Pl. do not reply.</span></i> ";
			desc = desc + "</body></html>";
				}
			if (requestFrom.contains("Code Extension")) {

				String getdetails = "select REQUEST_NO,REQUEST_DATE,CREATED_BY,TYPE,MATERIAL_CODE_1,PLANT_1 fromPlant,EXTENDED_TO_PLANT_1 toPlant,"
						+ "STORAGE_LOCATION_ID_1 fromStorage,EXTENDED_STORAGE_LOCATION_1 toStorage,PLANT_2 fromPlant1,MATERIAL_CODE_2,FROM_STORAGE_LOCATION,"
						+ "TO_STORAGE_LOCATION from code_extenstion_request where REQUEST_NO='"
						+ Req_Id + "'";
				ResultSet rsDetails = ad.selectQuery(getdetails);
				while (rsDetails.next()) {
					submitDate = EMicroUtils.display(rsDetails
							.getDate("REQUEST_DATE"));
					reqCodeType = rsDetails.getString("TYPE");
					if (reqCodeType.equals("Plant")) {
						sapNo = rsDetails.getString("MATERIAL_CODE_1");
						fromPlant = rsDetails.getString("fromPlant");
						toPlant = rsDetails.getString("toPlant");
						fromStorage = rsDetails.getString("fromStorage");
						toStorage = rsDetails.getString("toStorage");
						location = rsDetails.getString("toPlant");
					} else {
						sapNo = rsDetails.getString("MATERIAL_CODE_2");
						fromPlant = rsDetails.getString("fromPlant1");
						fromStorage = rsDetails
								.getString("FROM_STORAGE_LOCATION");
						toStorage = rsDetails.getString("TO_STORAGE_LOCATION");
						location = rsDetails.getString("fromPlant1");
					}

				}
				String getCustDet = "select emp.EMP_FULLNAME from code_extenstion_request as v,emp_official_info as emp where  REQUEST_NO='"
						+ Req_Id + "'  and emp.PERNR=v.CREATED_BY";
				ResultSet rsCust = ad.selectQuery(getCustDet);
				while (rsCust.next()) {

					requesterName = rsCust.getString("EMP_FULLNAME");
				}
				mailForm.setSubject("Code Extention Request From "
						+ requesterName);
				desc = desc + "";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";
				if (reqCodeType.equals("Plant")) {
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=8 ><center>Code Extention Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Type</th><th align=left>Material Code</th><th>From Plant</th><th>Storage Location</th><th>To Plant</th><th>Storage Location</th></tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + reqCodeType
							+ "</td><td>" + sapNo + "</td><td>" + fromPlant
							+ "</td><td>" + fromStorage + "</td><td>" + toPlant
							+ "</td><td>" + toStorage
							+ "</td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
				} else {
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=8 ><center>Code Extention Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Type</th><th align=left>Material Code</th><th>From Plant</th><th>From Storage Location</th><th>To Storage Location</th></tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + reqCodeType
							+ "</td><td>" + sapNo + "</td><td>" + fromPlant
							+ "</td><td>" + fromStorage + "</td><td>"
							+ toStorage + "</td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
				}
			}

			String getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
					+ nextApproverID + "'";
			ResultSet approverRS = ad.selectQuery(getAppEmailID);
			while (approverRS.next()) {
				approvermail = approverRS.getString(1);
			}
			mailForm.setToAddress(approvermail);
			System.out.println("m -> " + approvermail);
			mailForm.setccAddress("");
			mailForm.setbccAddress("");

			mailForm.setDescription(desc);
			mailAction.mailSendNextApprover(request, mailForm, "request",
					requesterID);
			success = 1;
		} catch (ServletException se) {
			System.out.println("ServletException @ sending leave request..");
			se.printStackTrace();
		} catch (MessagingException se) {
			System.out.println("MessagingException @ sending leave request..");
			se.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("IOException @ sending leave request..");
			ioe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("SQLException @ sending  request..");
			sqle.printStackTrace();
		}
		return success;
	}

	public String Empname(String a) {

		String b = "";
		if (a == null) {
			return b;
		}

		if (!a.equalsIgnoreCase("")) {

			String emp = "select EMP_FULLNAME from emp_official_info where pernr = '"
					+ a + "'";
			ResultSet rs = ad.selectQuery(emp);
			try {
				if (rs.next()) {
					b = rs.getString("emp_fullname");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return b;

	}

	public int sendMailToOffice_EmpApprover(HttpServletRequest request,
			String approvermail, String Req_Id, String requestFrom, String empNo) {
		int success = 0;
		HttpSession session = request.getSession();

		UserInfo user = (UserInfo) session.getAttribute("user");
		String fullName = Empname(empNo);
		MailInboxForm mailForm = new MailInboxForm();
		MailInboxAction mailAction = new MailInboxAction();
		int userId = user.getId();

		try {

			// getting details from corresponding table
			String swipetype = "";
			String type = "";
			String submitDate = "";
			String location = "";
			String requesterName = "";
			String matGroup = "";
			String pApprover = "";
			String vcmasterName = "";
			String vcmasterPAN = "";
			String vcmasterEMAIL = "";
			String typeofvc = "";
			String noOfDays = "";
			String ondutyType = "";
			String plant = "";
			String fromTime = "";
			String toTime = "";
			String duration = "";
			String startDate = "";
			String endDate = "";
			String leaveType = ""; // getLeaveType(leaveType);
			String holidayType = "";
			String reqEmp = "";
			String empQual = "";
			String empExp = "";
			String feedComment = "";
			String feedFrom = "";
			String feedSub = "";
			// for material
			String matcode = "";
			String materialType = "";
			String reqMatType = "";
			String matsName = "";
			String matgName = "";
			String pharmaName = "";
			// for service master
			String mcname = "";
			String serCat = "";
			String serDes = "";
			String serPur = "";
			String requestTime = "";
			String reason = "";
			String reasonType = "";
			// for permission
			String perDate = "";
			String perStartTime = "";
			String perEndTime = "";
			String perReason = "";
			String startDur = "";
			String endDur = "";
			double casualOpeningBal = 0;
			double casualAvailedBal = 0;
			double casualClosingBal = 0;
			double casualAwatingBal = 0;

			double sickOpeningBal = 0;
			double sickAvailedBal = 0;
			double sickClosingBal = 0;
			double sickAwatingBal = 0;

			double previlegeOpeningBal = 0;
			double previlegeAvailedBal = 0;
			double previlegeClosingBal = 0;
			double previlegeAwatingBal = 0;

			double maternityOpeningBal = 0;
			double maternityAvailedBal = 0;
			double maternityClosingBal = 0;
			double maternityAwatingBal = 0;
			String approverDesc = "";
			String approver1 = "";
			String parallelApp1 = "";
			String parallelApp2 = "";

			// vendor master

			String vendorName = "";
			String vendorCity = "";
			String vedorType = "";
			String vendorType = "";
			// customer master
			String customerName = "";
			String customerCity = "";
			String customerType = "";
			// code extension
			String sapNo = "";
			String fromPlant = "";
			String toPlant = "";
			String fromStorage = "";
			String toStorage = "";
			String reqCodeType = "";
			String parallelApprover1Desc = "";
			String parallelApprover2Desc = "";
			String tName = "leave_details";
			LeaveAction lAction = new LeaveAction();
			if (requestFrom.equalsIgnoreCase("Recruitment Request")) {
				tName = "Recruitment_Request";
				String recdetails = "select * from " + tName
						+ " where User_Id='" + userId + "' and Req_Id='"
						+ Req_Id + "'";
				ResultSet ldRS = ad.selectQuery(recdetails);
				while (ldRS.next()) {
					submitDate = ldRS.getString("Request_Date");
					pApprover = ldRS.getString("Pending_Approver");
					reqEmp = ldRS.getString("TotalEmp");
					empQual = ldRS.getString("Qualification");
					empExp = ldRS.getString("Experience");
				}

			} else if (requestFrom.equalsIgnoreCase("Add Man Power")) {
				/*
				 * tName = "Man_Power_Matrix"; String leavedetails =
				 * "select * from "
				 * +tName+" where user_id="+userId+" and Req_Id='"
				 * +Req_Id+"' and Status='Pending'"; ResultSet ldRS =
				 * ad.selectQuery(leavedetails); while(ldRS.next()){ submitDate
				 * = ldRS.getString("submit_date"); startDate =
				 * EMicroUtils.display1(ldRS.getDate("start_date")); pApprover =
				 * ldRS.getString("Pending_Approver"); leaveType =
				 * ldRS.getString("leave_type"); noOfDays =
				 * ldRS.getString("no_of_days"); holidayType =
				 * ldRS.getString("holiday_type"); }
				 */
			} else if (requestFrom.equalsIgnoreCase("Leave")) {
				tName = "leave_details";
				String leavedetails = "select * from " + tName
						+ " where user_id=" + empNo + " and Req_Id='" + Req_Id
						+ "'";
				ResultSet ldRS = ad.selectQuery(leavedetails);
				while (ldRS.next()) {
					leaveType = ldRS.getString("leave_type");
					if (leaveType.equalsIgnoreCase("1")) {
						leaveType = "Casual";
					}
					if (leaveType.equalsIgnoreCase("2")) {
						leaveType = "Sick";
					}
					if (leaveType.equalsIgnoreCase("3")) {
						leaveType = "Privilege";
					}
					if (leaveType.equalsIgnoreCase("4")) {
						leaveType = "Loss Of Pay";
					}
					if (leaveType.equalsIgnoreCase("5")) {
						leaveType = "Maternity";
					}
					startDate = EMicroUtils
							.display1(ldRS.getDate("start_date"));
					startDur = ldRS.getString("start_duration");
					if (startDur.equalsIgnoreCase("FD")) {
						startDur = "Full Day";
					}
					if (startDur.equalsIgnoreCase("FH")) {
						startDur = "First Half";
					}
					if (startDur.equalsIgnoreCase("SH")) {
						startDur = "Second Half";
					}
					endDate = EMicroUtils.display1(ldRS.getDate("end_date"));
					endDur = ldRS.getString("end_duration");
					if (endDur.equalsIgnoreCase("FD")) {
						endDur = "Full Day";
					}
					if (endDur.equalsIgnoreCase("FH")) {
						endDur = "First Half";
					}
					if (endDur.equalsIgnoreCase("SH")) {
						endDur = "Second Half";
					}
					reasonType = ldRS.getString("reasonType");
					reason = ldRS.getString("reason");
					pApprover = ldRS.getString("Pending_Approver");

					noOfDays = ldRS.getString("no_of_days");

					submitDate = ldRS.getString("submit_date");
				}
				String getApproverID = "select * from emp_official_info where PERNR='"
						+ pApprover + "'";
				ResultSet approverRS = ad.selectQuery(getApproverID);
				while (approverRS.next()) {
					approvermail = approverRS.getString("EMAIL_ID");

				}
				String checkESSApproverList = "select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"
						+ empNo
						+ "' and essType='Leave' and Priority=1 and ess.ApproverId=emp.PERNR";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					approvermail = rsCheckInEss.getString("EMAIL_ID");
				}

				try {
					int year = Calendar.getInstance().get(Calendar.YEAR);
					String getleaveBalence = "select t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
							+ empNo
							+ "' and t.lv_calyear='"
							+ year
							+ "' and t.lv_typeid=m.lv_typeid";
					ResultSet rsBalance = ad.selectQuery(getleaveBalence);
					ArrayList balList = new ArrayList();
					while (rsBalance.next()) {
						String lvType = rsBalance.getString("lv_type");
						if (lvType.equalsIgnoreCase("CASUAL")) {
							casualOpeningBal = rsBalance.getFloat("lv_opbal");
							casualAvailedBal = rsBalance.getFloat("lv_availed");
							casualClosingBal = rsBalance.getFloat("lv_clbal");
							casualAwatingBal = rsBalance.getFloat("lv_awtBal");
						}
						if (lvType.equalsIgnoreCase("SICK")) {
							sickOpeningBal = rsBalance.getFloat("lv_opbal");
							sickAvailedBal = rsBalance.getFloat("lv_availed");
							sickClosingBal = rsBalance.getFloat("lv_clbal");
							sickAwatingBal = rsBalance.getFloat("lv_awtBal");
						}
						if (lvType.equalsIgnoreCase("PRIVILEGE")) {
							previlegeOpeningBal = rsBalance
									.getFloat("lv_opbal");
							previlegeAvailedBal = rsBalance
									.getFloat("lv_availed");
							previlegeClosingBal = rsBalance
									.getFloat("lv_clbal");
							previlegeAwatingBal = rsBalance
									.getFloat("lv_awtBal");
						}
						if (lvType.equalsIgnoreCase("MATERNITY")) {
							maternityOpeningBal = rsBalance
									.getFloat("lv_opbal");
							maternityAvailedBal = rsBalance
									.getFloat("lv_availed");
							maternityClosingBal = rsBalance
									.getFloat("lv_clbal");
							maternityAwatingBal = rsBalance
									.getFloat("lv_awtBal");
						}
						LeaveForm form2 = new LeaveForm();
						form2.setLeaveType(rsBalance.getString("lv_type"));

						form2.setOpeningBalence(rsBalance.getInt("lv_opbal"));
						form2.setAvalableBalence(rsBalance
								.getFloat("lv_availed"));
						form2.setClosingBalence(rsBalance.getFloat("lv_clbal"));
						form2.setAwaitingBalence(rsBalance
								.getFloat("lv_awtBal"));
						balList.add(form2);
					}
					request.setAttribute("LeaveBalenceList", balList);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (requestFrom.equalsIgnoreCase("On Duty")) {
				String ondutydetails = "select onduty.onDuty_Type,onduty.location,CONVERT(varchar(11),onduty.[start_date],103) as start_date,"
						+ "onduty.startTime,CONVERT(varchar(11),onduty.[end_date],103) as end_date,onduty.endTime,onduty.reason,CONVERT(varchar(11),onduty.[submit_date],103) as submit_date,onduty.Approver_id,emp.EMAIL_ID "
						+ "from OnDuty_details as onduty,emp_official_info as emp where onduty.user_id='"
						+ empNo
						+ "' and emp.PERNR=onduty.Approver_id and "
						+ "user_id="
						+ empNo
						+ " and request_no='"
						+ Req_Id
						+ "'";
				ResultSet ldRS = ad.selectQuery(ondutydetails);
				while (ldRS.next()) {
					ondutyType = ldRS.getString("onDuty_Type");
					plant = ldRS.getString("location");
					startDate = ldRS.getString("start_date");
					fromTime = ldRS.getString("startTime");
					endDate = ldRS.getString("end_date");
					toTime = ldRS.getString("endTime");
					reason = ldRS.getString("reason");
					submitDate = ldRS.getString("submit_date");
					pApprover = ldRS.getString("Approver_id");
					approvermail = ldRS.getString("EMAIL_ID");
				}

			} else if (requestFrom.equalsIgnoreCase("Permission")) {
				tName = "Permission_details";
				String feeddetails = "select p.swipe_type,p.type,p.request_no,CONVERT(varchar(11),p.[date],103) as date,p.startTime,p.endTime,p.reason,p.Approver_id,emp.EMAIL_ID from "
						+ "Permission_details p,emp_official_info  as emp where p.user_id='"
						+ empNo
						+ "' and p.request_no='"
						+ Req_Id
						+ "' and  "
						+ "p.Approver_id=emp.PERNR";
				ResultSet ldRS = ad.selectQuery(feeddetails);
				while (ldRS.next()) {
					perDate = ldRS.getString("date");
					perStartTime = ldRS.getString("startTime");
					perEndTime = ldRS.getString("endTime");
					perReason = ldRS.getString("reason");
					pApprover = ldRS.getString("Approver_id");
					approvermail = ldRS.getString("EMAIL_ID");
					type = ldRS.getString("type");
					swipetype = ldRS.getString("swipe_type");
				}

			}

			else if (requestFrom.equalsIgnoreCase("Feedback")) {
				tName = "feedBack_details";
				String feeddetails = "select * from " + tName
						+ " where user_id=" + userId + " and Req_Id='" + Req_Id
						+ "'";
				ResultSet ldRS = ad.selectQuery(feeddetails);
				while (ldRS.next()) {
					feedSub = ldRS.getString("subject");
					feedComment = ldRS.getString("comments");
					feedFrom = ldRS.getString("user_name") + "<"
							+ ldRS.getString("email_id") + ">";
				}

			} else if (requestFrom.equalsIgnoreCase("Customer Master")) {
				String getCustDet = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,NAME,CITY,v.Customer_Type,emp.EMP_FULLNAME from "
						+ "CUSTOMER_MASTER_M as v,emp_official_info as emp where  REQUEST_NO='"
						+ Req_Id + "'  and emp.PERNR=v.CREATED_BY";
				ResultSet rsCust = ad.selectQuery(getCustDet);
				while (rsCust.next()) {
					submitDate = rsCust.getString("REQUEST_DATE");
					customerName = rsCust.getString("NAME");
					customerCity = rsCust.getString("CITY");
					customerType = rsCust.getString("Customer_Type");
					requesterName = rsCust.getString("EMP_FULLNAME");

				}

				int checkApprover = 0;
				String getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
						+ "Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='Export' "
						+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
				ResultSet rsApp = ad.selectQuery(getMatApprovers);
				while (rsApp.next()) {
					checkApprover = 1;
					approver1 = rsApp.getString("Approver_Id");
					parallelApp1 = rsApp.getString("Parllel_Approver_1");
					parallelApp2 = rsApp.getString("Parllel_Approver_2");
				}
				if (checkApprover == 0) {
					getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
							+ "Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='Domestic' "
							+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
					rsApp = ad.selectQuery(getMatApprovers);
					while (rsApp.next()) {
						approver1 = rsApp.getString("Approver_Id");
						parallelApp1 = rsApp.getString("Parllel_Approver_1");
						parallelApp2 = rsApp.getString("Parllel_Approver_2");
					}
				}
			} else if (requestFrom.equalsIgnoreCase("Vendor Master")) {
				String getVendorDet = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,NAME,CITY,v.Type_Of_Vendor,emp.EMP_FULLNAME from "
						+ "vendor_master_m as v,emp_official_info as emp where  REQUEST_NO='"
						+ Req_Id + "'  and emp.PERNR=v.CREATED_BY";
				ResultSet rsVendor = ad.selectQuery(getVendorDet);
				while (rsVendor.next()) {
					submitDate = rsVendor.getString("REQUEST_DATE");
					vendorName = rsVendor.getString("NAME");
					vendorCity = rsVendor.getString("CITY");
					vendorType = rsVendor.getString("Type_Of_Vendor");
					if (vendorType.equalsIgnoreCase("I"))
						vendorType = "IMPORTER";
					if (vendorType.equalsIgnoreCase("M"))
						vendorType = "MANUFACTURER";
					if (vendorType.equalsIgnoreCase("D"))
						vendorType = "DEALER";
					if (vendorType.equalsIgnoreCase("FD"))
						vendorType = "FIRST STAGE DEALER OF INDIGENOUS";
					if (vendorType.equalsIgnoreCase("SD"))
						vendorType = "SECOND STAGE DEALER OF INDIGENOUS";
					if (vendorType.equalsIgnoreCase("MD"))
						vendorType = "DEPOT MANUFACTURER";

					requesterName = rsVendor.getString("EMP_FULLNAME");
				}

				int checkApprover = 0;
				String getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
						+ "Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='Import' "
						+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
				ResultSet rsApp = ad.selectQuery(getMatApprovers);
				while (rsApp.next()) {
					checkApprover = 1;
					approver1 = rsApp.getString("Approver_Id");
					parallelApp1 = rsApp.getString("Parllel_Approver_1");
					parallelApp2 = rsApp.getString("Parllel_Approver_2");
				}
				if (checkApprover == 0) {
					getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
							+ "Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='Local' "
							+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
					rsApp = ad.selectQuery(getMatApprovers);
					while (rsApp.next()) {
						approver1 = rsApp.getString("Approver_Id");
						parallelApp1 = rsApp.getString("Parllel_Approver_1");
						parallelApp2 = rsApp.getString("Parllel_Approver_2");
					}
				}
			} else if (requestFrom.equalsIgnoreCase("Material Code Request")) {
				tName = "material_code_request";
				String matType = "";
				String newmatcode = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,loc.LOCATION_CODE,Type,"
						+ "MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.MATERIAL_GROUP_ID,mType.M_DESC from material_code_request as mat,Location as loc,emp_official_info as emp,MATERIAL_TYPE mType where  "
						+ "REQUEST_NO='"
						+ Req_Id
						+ "' and loc.LOCID=mat.LOCATION_ID and emp.PERNR=mat.CREATED_BY and mType.MATERIAL_GROUP_ID=mat.Type";
				ResultSet ldRS = ad.selectQuery(newmatcode);
				while (ldRS.next()) {
					submitDate = ldRS.getString("REQUEST_DATE");
					location = ldRS.getString("LOCATION_CODE");
					materialType = ldRS.getString("Type") + "-"
							+ ldRS.getString("M_DESC");
					reqMatType = ldRS.getString("Type");
					matType = ldRS.getString("Type");
					matsName = ldRS.getString("MATERIAL_SHORT_NAME");
					requesterName = ldRS.getString("EMP_FULLNAME");
					matGroup = ldRS.getString("MATERIAL_GROUP_ID");
				}
				if (matType.equals("FG") || matType.equals("HAWA")) {
					String matDetails = "select loc.LOCATION_CODE,mat.Type,mat.CREATED_BY,mat.DOMESTIC_OR_EXPORTS from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"
							+ Req_Id + "' and mat.LOCATION_ID=loc.LOCID";
					ResultSet rsDetails = ad.selectQuery(matDetails);
					try {
						while (rsDetails.next()) {
							matGroup = rsDetails
									.getString("DOMESTIC_OR_EXPORTS");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (matGroup.equals("D"))
						matGroup = "Domestic";
					if (matGroup.equals("E"))
						matGroup = "Export";
					if (matGroup.equals("V"))
						matGroup = "V";
				}
				int checkApprover = 0;
				String getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
						+ "Material_Approvers as app,emp_official_info AS emp where app.Location='"
						+ location
						+ "' and app.Material_Type='"
						+ reqMatType
						+ "' and app.Material_Group='"
						+ matGroup
						+ "' "
						+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
				ResultSet rsApp = ad.selectQuery(getMatApprovers);
				while (rsApp.next()) {
					checkApprover = 1;
					approver1 = rsApp.getString("Approver_Id");
					parallelApp1 = rsApp.getString("Parllel_Approver_1");
					parallelApp2 = rsApp.getString("Parllel_Approver_2");
				}
				if (checkApprover == 0) {
					getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
							+ "Material_Approvers as app,emp_official_info AS emp where app.Location='"
							+ location
							+ "' and app.Material_Type='"
							+ reqMatType
							+ "' and app.Material_Group='' "
							+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
					rsApp = ad.selectQuery(getMatApprovers);
					while (rsApp.next()) {
						approver1 = rsApp.getString("Approver_Id");
						parallelApp1 = rsApp.getString("Parllel_Approver_1");
						parallelApp2 = rsApp.getString("Parllel_Approver_2");
					}
				}

			} else if (requestFrom.contains("Service Master")) {
				tName = "SERVICE_MASTER";

				String getdetails = "select s.request_date,s.machine_name,s.service_catagory,s.service_description,s.pending_approver,"
						+ "s.purpose,loc.LOCATION_CODE from SERVICE_MASTER s,Location loc where  request_no='"
						+ Req_Id + "' and " + "loc.LOCID=s.plant_code";
				ResultSet detailRS = ad.selectQuery(getdetails);
				while (detailRS.next()) {
					submitDate = detailRS.getString("request_date");
					mcname = detailRS.getString("machine_name");
					serCat = detailRS.getString("service_catagory");
					serDes = detailRS.getString("service_description");
					pApprover = detailRS.getString("pending_approver");
					serPur = detailRS.getString("purpose");
					location = detailRS.getString("LOCATION_CODE");
				}

				int checkApprover = 0;
				String getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
						+ "Material_Approvers as app,emp_official_info AS emp where app.Location='"
						+ location
						+ "' and app.Material_Type='Service Master' and app.Material_Group='' "
						+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
				ResultSet rsApp = ad.selectQuery(getMatApprovers);
				while (rsApp.next()) {
					checkApprover = 1;
					approver1 = rsApp.getString("Approver_Id");
					parallelApp1 = rsApp.getString("Parllel_Approver_1");
					parallelApp2 = rsApp.getString("Parllel_Approver_2");
				}

			}
			if (requestFrom.contains("Code Extension")) {

				String getdetails = "select REQUEST_NO,REQUEST_DATE,CREATED_BY,TYPE,MATERIAL_CODE_1,PLANT_1 fromPlant,EXTENDED_TO_PLANT_1 toPlant,"
						+ "STORAGE_LOCATION_ID_1 fromStorage,EXTENDED_STORAGE_LOCATION_1 toStorage,PLANT_2 fromPlant1,MATERIAL_CODE_2,FROM_STORAGE_LOCATION,"
						+ "TO_STORAGE_LOCATION from code_extenstion_request where REQUEST_NO='"
						+ Req_Id + "'";
				ResultSet rsDetails = ad.selectQuery(getdetails);
				while (rsDetails.next()) {
					submitDate = EMicroUtils.display(rsDetails
							.getDate("REQUEST_DATE"));
					reqCodeType = rsDetails.getString("TYPE");
					if (reqCodeType.equals("Plant")) {
						sapNo = rsDetails.getString("MATERIAL_CODE_1");
						fromPlant = rsDetails.getString("fromPlant");
						toPlant = rsDetails.getString("toPlant");
						fromStorage = rsDetails.getString("fromStorage");
						toStorage = rsDetails.getString("toStorage");
						location = rsDetails.getString("toPlant");
					} else {
						sapNo = rsDetails.getString("MATERIAL_CODE_2");
						fromPlant = rsDetails.getString("fromPlant1");
						fromStorage = rsDetails
								.getString("FROM_STORAGE_LOCATION");
						toStorage = rsDetails.getString("TO_STORAGE_LOCATION");
						location = rsDetails.getString("fromPlant1");
					}

				}

				String getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
						+ "Material_Approvers as app,emp_official_info AS emp where app.Location='"
						+ location
						+ "' and app.Material_Type='Code Extention' and app.Material_Group='' "
						+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
				ResultSet rsApp = ad.selectQuery(getMatApprovers);
				while (rsApp.next()) {
					approver1 = rsApp.getString("Approver_Id");
					parallelApp1 = rsApp.getString("Parllel_Approver_1");
					parallelApp2 = rsApp.getString("Parllel_Approver_2");
				}
			}

			String approver = "";

			String getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
					+ approver1 + "'";
			ResultSet approverRS = ad.selectQuery(getAppEmailID);
			while (approverRS.next()) {
				approvermail = approverRS.getString(1);
			}
			mailForm.setToAddress(approvermail);
			System.out.println("m -> " + approvermail);
			mailForm.setccAddress("");
			mailForm.setbccAddress("");

			// constructing mail description for each request
			String desc = "Requested By : " + fullName + "</br>";
			desc = desc + "Requested Date : " + submitDate + "</br>";
			if (requestFrom.equalsIgnoreCase("Recruitment Request")) {
				desc = desc + "Required Employee : " + reqEmp + "</br>";
				desc = desc + "Required Qualification : " + empQual + "</br>";
				desc = desc + "empExp : " + empExp + "</br>";
			} else if (requestFrom.equalsIgnoreCase("Add Man Power")) {
				/*
				 * desc = desc +
				 * "Dpartment : "+hrMPMForm.getDepartment()+"</br>"; desc = desc
				 * + "Required Employee : "+hrMPMForm.getReqEmp()+"</br>";
				 */
			}
			if (requestFrom.equalsIgnoreCase("Material Code Request")) {
				mailForm.setSubject("Material Code Request From "
						+ requesterName);
				desc = desc + "";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";
				desc = desc
						+ "<html><body><img src="
						+ "https://portal.microlabs.co.in/images/logo.png"
						+ " height=60 width=50  /></br> <table><tr><th colspan=5 ><center>Material Code Request Details</center></th></tr>";
				desc = desc
						+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Plant</th><th align=left>Material Type</th><th align=left>Material Name</th></tr>";
				desc = desc + "<tr><td>" + Req_Id + "</td><td>" + submitDate
						+ "</td><td>" + location + "</td><td>" + materialType
						+ "</td><td>" + matsName + "</td></tr></table><br><br>";
				desc = desc
						+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
						+ "https://portal.microlabs.co.in/"
						+ ">EMicro Portal</a> .</span> ";
				desc = desc + "</body></html>";
			}
			if (requestFrom.equalsIgnoreCase("Customer Master")) {
				mailForm.setSubject("Customer Code Request From "
						+ requesterName);
				desc = desc + "";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";
				desc = desc
						+ "<html><body><img src="
						+ "https://portal.microlabs.co.in/images/logo.png"
						+ " height=60 width=50  /></br> <table><tr><th colspan=6 ><center>Customer Code Request Details</center></th></tr>";
				desc = desc
						+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Customer Name</th><th align=left>City</th><th align=left>Customer Type</th></tr>";
				desc = desc + "<tr><td>" + Req_Id + "</td><td>" + submitDate
						+ "</td><td>" + customerName + "</td><td>"
						+ customerCity + "</td><td>" + customerType
						+ "</td></tr></table><br><br>";
				desc = desc
						+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
						+ "https://portal.microlabs.co.in/"
						+ ">EMicro Portal</a> .</span> ";
				desc = desc + "</body></html>";
			}
			if (requestFrom.equalsIgnoreCase("Vendor Master")) {
				mailForm.setSubject("Vendor Code Request From " + requesterName);
				desc = desc + "";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";
				desc = desc
						+ "<html><body><img src="
						+ "https://portal.microlabs.co.in/images/logo.png"
						+ " height=60 width=50  /></br> <table><tr><th colspan=6 ><center>Vendor Code Request Details</center></th></tr>";
				desc = desc
						+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Vendor Name</th><th align=left>City</th><th align=left>Vendor Type</th></tr>";
				desc = desc + "<tr><td>" + Req_Id + "</td><td>" + submitDate
						+ "</td><td>" + vendorName + "</td><td>" + vendorCity
						+ "</td><td>" + vendorType
						+ "</td></tr></table><br><br>";
				desc = desc
						+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
						+ "https://portal.microlabs.co.in/"
						+ ">EMicro Portal</a> .</span> ";
				desc = desc + "</body></html>";
			}
			if (requestFrom.equalsIgnoreCase("Service Master")) {
				mailForm.setSubject("Service Code Request From "
						+ requesterName);
				desc = desc + "";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";
				desc = desc
						+ "<html><body><img src="
						+ "https://portal.microlabs.co.in/images/logo.png"
						+ " height=60 width=50  /></br> <table><tr><th colspan=6 ><center>Service Code Request Details</center></th></tr>";
				desc = desc
						+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Service Category</th><th align=left>Service Description</th></tr>";
				desc = desc + "<tr><td>" + Req_Id + "</td><td>" + submitDate
						+ "</td><td>" + serCat + "</td><td>" + serDes
						+ "</td></tr></table><br><br>";
				desc = desc
						+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
						+ "https://portal.microlabs.co.in/"
						+ ">EMicro Portal</a> .</span> ";
				desc = desc + "</body></html>";
			}
			if (requestFrom.equalsIgnoreCase("Code Extension")) {
				mailForm.setSubject("Code Extention Request From "
						+ user.getFullName());
				desc = desc + "";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";

				if (reqCodeType.equals("Plant")) {
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=8 ><center>Code Extention Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Type</th><th align=left>Material Code</th><th>From Plant</th><th>Storage Location</th><th>To Plant</th><th>Storage Location</th></tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + reqCodeType
							+ "</td><td>" + sapNo + "</td><td>" + fromPlant
							+ "</td><td>" + fromStorage + "</td><td>" + toPlant
							+ "</td><td>" + toStorage
							+ "</td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
				} else {
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=8 ><center>Code Extention Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Type</th><th align=left>Material Code</th><th>From Plant</th><th>From Storage Location</th><th>To Storage Location</th></tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + reqCodeType
							+ "</td><td>" + sapNo + "</td><td>" + fromPlant
							+ "</td><td>" + fromStorage + "</td><td>"
							+ toStorage + "</td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
				}
			} else if (requestFrom.equalsIgnoreCase("Leave")) {
				String gender = "";
				String pernr = "";
				String name = "";
				String doj = "";
				String dep = "";
				String desg = "";
				String getGender = "select emp.PERNR,emp.EMP_FULLNAME,emp.SEX,emp.DOJ,dep.DPTSTXT,desg.DSGSTXT from emp_official_info emp,DEPARTMENT as dep,DESIGNATION as desg where emp.PERNR='"
						+ empNo
						+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
				ResultSet rsGender = ad.selectQuery(getGender);
				while (rsGender.next()) {
					gender = rsGender.getString("SEX");
					pernr = rsGender.getString("PERNR");
					name = rsGender.getString("EMP_FULLNAME");
					doj = rsGender.getString("DOJ");
					dep = rsGender.getString("DPTSTXT");
					desg = rsGender.getString("DSGSTXT");

				}
				String abc[] = doj.split("-");
				doj = abc[2] + "/" + abc[1] + "/" + abc[0];

				String recLeaveType = "";
				String reclv_StartDate = "";
				String reclv_EndDt = "";
				String reclv_Days = "";
				String reclv_ApprDt = "";
				int recLv_record = 0;
				String getLastLeaveRecord = "select id,user_id,leave_type,m.lv_type,CONVERT(CHAR(10), [start_date], 103) as start_date,CONVERT(CHAR(10),[end_date], 103) as end_date,"
						+ "no_of_days,CONVERT(CHAR(10), [approved_date], 103) as approved_date from leave_details as l,lv_type_m as m where user_id='"
						+ empNo
						+ "' and l.leave_type=m.lv_typeid "
						+ "and Approvel_Status='Approved' order by id desc";
				ResultSet rsRecentrecord = ad.selectQuery(getLastLeaveRecord);
				if (rsRecentrecord.next()) {
					recLv_record = 1;
					recLeaveType = rsRecentrecord.getString("lv_type");
					reclv_StartDate = rsRecentrecord.getString("start_date");
					reclv_EndDt = rsRecentrecord.getString("end_date");
					reclv_Days = rsRecentrecord.getString("no_of_days");
					reclv_ApprDt = rsRecentrecord.getString("approved_date");
				}

				mailForm.setSubject("Leave Request From " + Empname(empNo));

				boolean existStatus = false;
				String dataFlowType = "";
				String checkESSApproverList = "select count(*) from ESS_Approvers where employeeNumber='"
						+ empNo + "' and essType='Leave'";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					int number = rsCheckInEss.getInt(1);
					if (number == 0) {
						existStatus = false;
					} else {
						existStatus = true;
					}
				}
				if (existStatus == true) {

					String getDataFlowType = "select * from ESS_Approvers where employeeNumber='"
							+ empNo + "' and essType='Leave'";
					ResultSet rsDataFlowType = ad.selectQuery(getDataFlowType);
					while (rsDataFlowType.next()) {
						dataFlowType = rsDataFlowType.getString("reqType");
					}
					if (dataFlowType.equalsIgnoreCase("sequential")) {
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ empNo
								+ "' and essType='Leave' and reqType='sequential'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");
						}
						desc = desc + "";
						desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
								+ "TABLE {border-collapse:collapse;border:1px solid black;}"
								+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
								+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
								+ "</STYLE>";

						desc = desc
								+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
								+ "https://portal.microlabs.co.in/images/logo.png"
								+ " height=60 width=50  /></center></td></tr> ";
						desc = desc
								+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
						desc = desc + "<tr><th align=left>Emp No.</th><td>"
								+ pernr + "</td><th align=left>Name</th><td>"
								+ name + "</td></tr>";
						desc = desc + "<tr><th align=left>Dept</th><td>" + dep
								+ "</td><th align=left>Desg</th><td>" + desg
								+ "</td></tr>";
						desc = desc
								+ "<tr><th>Date Of Joining</th><td colspan=3>"
								+ doj + "</td></tr>";

						desc = desc
								+ "<tr><th colspan=5><center>Leave Details</center></th></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;Leave Type</th><td>"
								+ lAction.getLeaveType(leaveType)
								+ "</td><th>No.Of Days</th><td>" + noOfDays
								+ "</tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;From</th><td>"
								+ startDate
								+ "</td><th align=left>Duration</th><td>"
								+ startDur + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
								+ endDate + "<th align=left>Duration</th><td>"
								+ endDur + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;Reason Type</th><td colspan=4>"
								+ reasonType + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=4>"
								+ reason + "</td></tr>";

						desc = desc
								+ "<tr><th colspan=6>Leave Balances</th></tr>";
						desc = desc
								+ "<tr><th>Type</th><th>Opening</th><th>Availed</th><th>Closing</th><th>Awaiting For Approval</th></tr>";
						desc = desc
								+ "<tr><th align=left>CASUAL</TH><TD align=right>"
								+ casualOpeningBal + "</TD><TD align=right>"
								+ casualAvailedBal + "</TD><TD align=right>"
								+ casualClosingBal + "</TD><TD align=right>"
								+ casualAwatingBal + "</TD></TR>";
						desc = desc
								+ "<tr><th align=left>SICK</TH><TD align=right>"
								+ sickOpeningBal + "</TD><TD align=right>"
								+ sickAvailedBal + "</TD><TD align=right>"
								+ sickClosingBal + "</TD><TD align=right>"
								+ sickAwatingBal + "</TD></TR>";
						if (gender.equalsIgnoreCase("F")) {
							desc = desc
									+ "<tr><th align=left>MATERNITY</TH><TD align=right>"
									+ maternityOpeningBal
									+ "</TD><TD align=right>"
									+ maternityAvailedBal
									+ "</TD><TD align=right>"
									+ maternityClosingBal
									+ "</TD><TD align=right>"
									+ maternityAwatingBal + "</TD></TR>";
						}
						desc = desc
								+ "<tr><th align=left>PRIVILEGE</TH><TD align=right>"
								+ previlegeOpeningBal + "</TD><TD align=right>"
								+ previlegeAvailedBal + "</TD><TD align=right>"
								+ previlegeClosingBal + "</TD><TD align=right>"
								+ previlegeAwatingBal + "</TD></TR></table>";
						desc = desc + "</br></br></br>";

						desc = desc
								+ "<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
								+ approver1
								+ "&reqId="
								+ Req_Id
								+ "&status=Approve><img src="
								+ "https://portal.microlabs.co.in/images/Approve.png"
								+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
								+ approver1
								+ "&reqId="
								+ Req_Id
								+ "&status=Reject><img src="
								+ "https://portal.microlabs.co.in/images/Reject.png"
								+ " height=20 width=80 title=Reject border=0 /></a></br></br>";

						desc = desc + "</br></br>";
						desc = desc
								+ "<u><b>Last Leave Details :-</b></u></br></br>";
						desc = desc
								+ "<table><tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No Of Days</th><th>Approve Date</th></tr>";
						if (recLv_record == 1) {
							desc = desc + "<tr><td>" + recLeaveType
									+ "</td><td>" + reclv_StartDate
									+ "</td><td>" + reclv_EndDt + "</td><td>"
									+ reclv_Days + "</td><td>" + reclv_ApprDt
									+ "</td></tr></table>";
						} else {
							desc = desc
									+ "<tr><td colspan=5>No Leave Details</tr></table>";
						}
						desc = desc + "</br></br></br></br>";
						desc = desc
								+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
								+ "https://portal.microlabs.co.in/"
								+ ">EMicro Portal</a> .</span> ";
						desc = desc + "</body></html>";
						approverDesc = desc;

					}
					if (dataFlowType.equalsIgnoreCase("parallel")) {
						String toAddress = "";
						approver1 = "";
						String parallelApprover1 = "";
						String parallelApprover2 = "";
						String empIDS = "";
						String empEmail = "";
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ empNo
								+ "' and essType='Leave' and reqType='parallel'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");
							parallelApprover1 = rsApproverDsg
									.getString("Parallel_Approver1");
							parallelApprover2 = rsApproverDsg
									.getString("Parallel_Approver2");
						}
						if (!(approver1.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";

							desc = desc
									+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /></center></td></tr> ";
							desc = desc
									+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
							desc = desc + "<tr><th align=left>Emp No.</th><td>"
									+ pernr
									+ "</td><th align=left>Name</th><td>"
									+ name + "</td></tr>";
							desc = desc + "<tr><th align=left>Dept</th><td>"
									+ dep + "</td><th align=left>Desg</th><td>"
									+ desg + "</td></tr>";
							desc = desc
									+ "<tr><th>Date Of Joining</th><td colspan=3>"
									+ doj + "</td></tr>";

							desc = desc
									+ "<tr><th colspan=5><center>Leave Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Leave Type</th><td>"
									+ lAction.getLeaveType(leaveType)
									+ "</td><th>No.Of Days</th><td>" + noOfDays
									+ "</tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;From</th><td>"
									+ startDate
									+ "</td><th align=left>Duration</th><td>"
									+ startDur + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
									+ endDate
									+ "<th align=left>Duration</th><td>"
									+ endDur + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Reason Type</th><td colspan=4>"
									+ reasonType + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=4>"
									+ reason + "</tr>";

							desc = desc
									+ "<tr><th colspan=6>Leave Balances</th></tr>";
							desc = desc
									+ "<tr><th>Type</th><th>Opening</th><th>Availed</th><th>Closing</th><th>Awaiting For Approval</th></tr>";
							desc = desc
									+ "<tr><th align=left>CASUAL</TH><TD align=right>"
									+ casualOpeningBal
									+ "</TD><TD align=right>"
									+ casualAvailedBal
									+ "</TD><TD align=right>"
									+ casualClosingBal
									+ "</TD><TD align=right>"
									+ casualAwatingBal + "</TD></TR>";
							desc = desc
									+ "<tr><th align=left>SICK</TH><TD align=right>"
									+ sickOpeningBal + "</TD><TD align=right>"
									+ sickAvailedBal + "</TD><TD align=right>"
									+ sickClosingBal + "</TD><TD align=right>"
									+ sickAwatingBal + "</TD></TR>";
							if (gender.equalsIgnoreCase("F")) {
								desc = desc
										+ "<tr><th align=left>MATERNITY</TH><TD align=right>"
										+ maternityOpeningBal
										+ "</TD><TD align=right>"
										+ maternityAvailedBal
										+ "</TD><TD align=right>"
										+ maternityClosingBal
										+ "</TD><TD align=right>"
										+ maternityAwatingBal + "</TD></TR>";
							}
							desc = desc
									+ "<tr><th align=left>PRIVILEGE</TH><TD align=right>"
									+ previlegeOpeningBal
									+ "</TD><TD align=right>"
									+ previlegeAvailedBal
									+ "</TD><TD align=right>"
									+ previlegeClosingBal
									+ "</TD><TD align=right>"
									+ previlegeAwatingBal
									+ "</TD></TR></table>";
							desc = desc + "</br></br></br>";

							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br>";

							desc = desc + "</br></br></br></br>";
							desc = desc
									+ "<u><b>Last Leave Details :-</b></u></br></br>";
							desc = desc
									+ "<table><tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No Of Days</th><th>Approve Date</th></tr>";
							if (recLv_record == 1) {
								desc = desc + "<tr><td>" + recLeaveType
										+ "</td><td>" + reclv_StartDate
										+ "</td><td>" + reclv_EndDt
										+ "</td><td>" + reclv_Days
										+ "</td><td>" + reclv_ApprDt
										+ "</td></tr></table>";
							} else {
								desc = desc
										+ "<tr><td colspan=5>No Leave Details</tr></table>";
							}
							desc = desc + "</br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
							approverDesc = desc;
						}
						if (!(parallelApprover1.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";

							desc = desc
									+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /></center></td></tr> ";
							desc = desc
									+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
							desc = desc + "<tr><th align=left>Emp No.</th><td>"
									+ pernr
									+ "</td><th align=left>Name</th><td>"
									+ name + "</td></tr>";
							desc = desc + "<tr><th align=left>Dept</th><td>"
									+ dep + "</td><th align=left>Desg</th><td>"
									+ desg + "</td></tr>";
							desc = desc
									+ "<tr><th>Date Of Joining</th><td colspan=3>"
									+ doj + "</td></tr>";

							desc = desc
									+ "<tr><th colspan=5><center>Leave Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Leave Type</th><td>"
									+ lAction.getLeaveType(leaveType)
									+ "</td><th>No.Of Days</th><td>" + noOfDays
									+ "</tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;From</th><td>"
									+ startDate
									+ "</td><th align=left>Duration</th><td>"
									+ startDur + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
									+ endDate
									+ "<th align=left>Duration</th><td>"
									+ endDur + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Reason Type</th><td colspan=4>"
									+ reasonType + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=4>"
									+ reason + "</tr>";

							desc = desc
									+ "<tr><th colspan=6>Leave Balances</th></tr>";
							desc = desc
									+ "<tr><th>Type</th><th>Opening</th><th>Availed</th><th>Closing</th><th>Awaiting For Approval</th></tr>";
							desc = desc
									+ "<tr><th align=left>CASUAL</TH><TD align=right>"
									+ casualOpeningBal
									+ "</TD><TD align=right>"
									+ casualAvailedBal
									+ "</TD><TD align=right>"
									+ casualClosingBal
									+ "</TD><TD align=right>"
									+ casualAwatingBal + "</TD></TR>";
							desc = desc
									+ "<tr><th align=left>SICK</TH><TD align=right>"
									+ sickOpeningBal + "</TD><TD align=right>"
									+ sickAvailedBal + "</TD><TD align=right>"
									+ sickClosingBal + "</TD><TD align=right>"
									+ sickAwatingBal + "</TD></TR>";
							if (gender.equalsIgnoreCase("F")) {
								desc = desc
										+ "<tr><th align=left>MATERNITY</TH><TD align=right>"
										+ maternityOpeningBal
										+ "</TD><TD align=right>"
										+ maternityAvailedBal
										+ "</TD><TD align=right>"
										+ maternityClosingBal
										+ "</TD><TD align=right>"
										+ maternityAwatingBal + "</TD></TR>";
							}
							desc = desc
									+ "<tr><th align=left>PRIVILEGE</TH><TD align=right>"
									+ previlegeOpeningBal
									+ "</TD><TD align=right>"
									+ previlegeAvailedBal
									+ "</TD><TD align=right>"
									+ previlegeClosingBal
									+ "</TD><TD align=right>"
									+ previlegeAwatingBal
									+ "</TD></TR></table>";
							desc = desc + "</br></br></br>";

							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
									+ parallelApprover1
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
									+ parallelApprover1
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br>";

							desc = desc + "</br></br></br></br>";
							desc = desc
									+ "<u><b>Last Leave Details :-</b></u></br></br>";
							desc = desc
									+ "<table><tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No Of Days</th><th>Approve Date</th></tr>";
							if (recLv_record == 1) {
								desc = desc + "<tr><td>" + recLeaveType
										+ "</td><td>" + reclv_StartDate
										+ "</td><td>" + reclv_EndDt
										+ "</td><td>" + reclv_Days
										+ "</td><td>" + reclv_ApprDt
										+ "</td></tr></table>";
							} else {
								desc = desc
										+ "<tr><td colspan=5>No Leave Details</tr></table>";
							}
							desc = desc + "</br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
							parallelApprover1Desc = desc;
						}
						if (!(parallelApprover2.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";

							desc = desc
									+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /></center></td></tr> ";
							desc = desc
									+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
							desc = desc + "<tr><th align=left>Emp No.</th><td>"
									+ pernr
									+ "</td><th align=left>Name</th><td>"
									+ name + "</td></tr>";
							desc = desc + "<tr><th align=left>Dept</th><td>"
									+ dep + "</td><th align=left>Desg</th><td>"
									+ desg + "</td></tr>";
							desc = desc
									+ "<tr><th>Date Of Joining</th><td colspan=3>"
									+ doj + "</td></tr>";

							desc = desc
									+ "<tr><th colspan=5><center>Leave Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Leave Type</th><td>"
									+ lAction.getLeaveType(leaveType)
									+ "</td><th>No.Of Days</th><td>" + noOfDays
									+ "</tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;From</th><td>"
									+ startDate
									+ "</td><th align=left>Duration</th><td>"
									+ startDur + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
									+ endDate
									+ "<th align=left>Duration</th><td>"
									+ endDur + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Reason Type</th><td colspan=4>"
									+ reasonType + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=4>"
									+ reason + "</tr>";

							desc = desc
									+ "<tr><th colspan=6>Leave Balances</th></tr>";
							desc = desc
									+ "<tr><th>Type</th><th>Opening</th><th>Availed</th><th>Closing</th><th>Awaiting For Approval</th></tr>";
							desc = desc
									+ "<tr><th align=left>CASUAL</TH><TD align=right>"
									+ casualOpeningBal
									+ "</TD><TD align=right>"
									+ casualAvailedBal
									+ "</TD><TD align=right>"
									+ casualClosingBal
									+ "</TD><TD align=right>"
									+ casualAwatingBal + "</TD></TR>";
							desc = desc
									+ "<tr><th align=left>SICK</TH><TD align=right>"
									+ sickOpeningBal + "</TD><TD align=right>"
									+ sickAvailedBal + "</TD><TD align=right>"
									+ sickClosingBal + "</TD><TD align=right>"
									+ sickAwatingBal + "</TD></TR>";
							if (gender.equalsIgnoreCase("F")) {
								desc = desc
										+ "<tr><th align=left>MATERNITY</TH><TD align=right>"
										+ maternityOpeningBal
										+ "</TD><TD align=right>"
										+ maternityAvailedBal
										+ "</TD><TD align=right>"
										+ maternityClosingBal
										+ "</TD><TD align=right>"
										+ maternityAwatingBal + "</TD></TR>";
							}
							desc = desc
									+ "<tr><th align=left>PRIVILEGE</TH><TD align=right>"
									+ previlegeOpeningBal
									+ "</TD><TD align=right>"
									+ previlegeAvailedBal
									+ "</TD><TD align=right>"
									+ previlegeClosingBal
									+ "</TD><TD align=right>"
									+ previlegeAwatingBal
									+ "</TD></TR></table>";
							desc = desc + "</br></br></br>";

							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
									+ parallelApprover2
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
									+ parallelApprover2
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br>";

							desc = desc + "</br></br></br></br>";
							desc = desc
									+ "<u><b>Last Leave Details :-</b></u></br></br>";
							desc = desc
									+ "<table><tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No Of Days</th><th>Approve Date</th></tr>";
							if (recLv_record == 1) {
								desc = desc + "<tr><td>" + recLeaveType
										+ "</td><td>" + reclv_StartDate
										+ "</td><td>" + reclv_EndDt
										+ "</td><td>" + reclv_Days
										+ "</td><td>" + reclv_ApprDt
										+ "</td></tr></table>";
							} else {
								desc = desc
										+ "<tr><td colspan=5>No Leave Details</tr></table>";
							}
							desc = desc + "</br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
							parallelApprover2Desc = desc;
						}
					}

				}
				if (existStatus == false) {
					desc = desc + "";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";

					desc = desc
							+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></center></td></tr> ";
					desc = desc
							+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
					desc = desc + "<tr><th align=left>Emp No.</th><td>" + pernr
							+ "</td><th align=left>Name</th><td>" + name
							+ "</td></tr>";
					desc = desc + "<tr><th align=left>Dept</th><td>" + dep
							+ "</td><th align=left>Desg</th><td>" + desg
							+ "</td></tr>";
					desc = desc + "<tr><th>Date Of Joining</th><td colspan=3>"
							+ doj + "</td></tr>";

					desc = desc
							+ "<tr><th colspan=5><center>Leave Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;Leave Type</th><td>"
							+ lAction.getLeaveType(leaveType)
							+ "</td><th>No.Of Days</th><td>" + noOfDays
							+ "</tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;From</th><td>"
							+ startDate
							+ "</td><th align=left>Duration</th><td>"
							+ startDur + "</td></tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
							+ endDate + "<th align=left>Duration</th><td>"
							+ endDur + "</td></tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;Reason Type</th><td colspan=4>"
							+ reasonType + "</td></tr>";
					desc = desc
							+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=4>"
							+ reason + "</td></tr>";

					desc = desc + "<tr><th colspan=6>Leave Balances</th></tr>";
					desc = desc
							+ "<tr><th>Type</th><th>Opening</th><th>Availed</th><th>Closing</th><th>Awaiting For Approval</th></tr>";
					desc = desc
							+ "<tr><th align=left>CASUAL</TH><TD align=right>"
							+ casualOpeningBal + "</TD><TD align=right>"
							+ casualAvailedBal + "</TD><TD align=right>"
							+ casualClosingBal + "</TD><TD align=right>"
							+ casualAwatingBal + "</TD></TR>";
					desc = desc
							+ "<tr><th align=left>SICK</TH><TD align=right>"
							+ sickOpeningBal + "</TD><TD align=right>"
							+ sickAvailedBal + "</TD><TD align=right>"
							+ sickClosingBal + "</TD><TD align=right>"
							+ sickAwatingBal + "</TD></TR>";
					if (gender.equalsIgnoreCase("F")) {
						desc = desc
								+ "<tr><th align=left>MATERNITY</TH><TD align=right>"
								+ maternityOpeningBal + "</TD><TD align=right>"
								+ maternityAvailedBal + "</TD><TD align=right>"
								+ maternityClosingBal + "</TD><TD align=right>"
								+ maternityAwatingBal + "</TD></TR>";
					}
					desc = desc
							+ "<tr><th align=left>PRIVILEGE</TH><TD align=right>"
							+ previlegeOpeningBal + "</TD><TD align=right>"
							+ previlegeAvailedBal + "</TD><TD align=right>"
							+ previlegeClosingBal + "</TD><TD align=right>"
							+ previlegeAwatingBal + "</TD></TR></table>";
					desc = desc + "</br></br></br>";

					desc = desc
							+ "<a href="
							+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
							+ pApprover
							+ "&reqId="
							+ Req_Id
							+ "&status=Approve><img src="
							+ "https://portal.microlabs.co.in/images/Approve.png"
							+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
							+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
							+ pApprover
							+ "&reqId="
							+ Req_Id
							+ "&status=Reject><img src="
							+ "https://portal.microlabs.co.in/images/Reject.png"
							+ " height=20 width=80 title=Reject border=0 /></a></br></br>";
					desc = desc + "</br></br></br></br>";
					desc = desc
							+ "<u><b>Last Leave Details :-</b></u></br></br>";
					desc = desc
							+ "<table><tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No Of Days</th><th>Approve Date</th></tr>";
					if (recLv_record == 1) {
						desc = desc + "<tr><td>" + recLeaveType + "</td><td>"
								+ reclv_StartDate + "</td><td>" + reclv_EndDt
								+ "</td><td>" + reclv_Days + "</td><td>"
								+ reclv_ApprDt + "</td></tr></table>";
					} else {
						desc = desc
								+ "<tr><td colspan=5>No Leave Details</tr></table>";
					}
					desc = desc + "</br></br></br></br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
					approverDesc = desc;
				}

			} else if (requestFrom.equalsIgnoreCase("On Duty")) {
				String gender = "";
				String pernr = "";
				String name = "";
				String doj = "";
				String dep = "";
				String desg = "";
				String getGender = "select emp.PERNR,emp.EMP_FULLNAME,emp.SEX,emp.DOJ,dep.DPTSTXT,desg.DSGSTXT from emp_official_info emp,DEPARTMENT as dep,DESIGNATION as desg where emp.PERNR='"
						+ empNo
						+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
				ResultSet rsGender = ad.selectQuery(getGender);
				while (rsGender.next()) {
					gender = rsGender.getString("SEX");
					pernr = rsGender.getString("PERNR");
					name = rsGender.getString("EMP_FULLNAME");
					doj = rsGender.getString("DOJ");
					dep = rsGender.getString("DPTSTXT");
					desg = rsGender.getString("DSGSTXT");

				}

				String abc[] = doj.split("-");
				doj = abc[2] + "/" + abc[1] + "/" + abc[0];

				mailForm.setSubject("OnDuty Request From " + Empname(empNo));
				boolean existStatus = false;
				String dataFlowType = "";
				String checkESSApproverList = "select count(*) from ESS_Approvers where employeeNumber='"
						+ empNo + "' and essType='Leave'";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					int number = rsCheckInEss.getInt(1);
					if (number == 0) {
						existStatus = false;
					} else {
						existStatus = true;
					}
				}
				if (existStatus == true) {
					String getDataFlowType = "select * from ESS_Approvers where employeeNumber='"
							+ empNo + "' and essType='Leave'";
					ResultSet rsDataFlowType = ad.selectQuery(getDataFlowType);
					while (rsDataFlowType.next()) {
						dataFlowType = rsDataFlowType.getString("reqType");
					}
					if (dataFlowType.equalsIgnoreCase("sequential")) {
						approver1 = "";
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ empNo
								+ "' and essType='Leave' and reqType='sequential'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");

						}

						desc = desc + "";
						desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
								+ "TABLE {border-collapse:collapse;border:1px solid black;}"
								+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
								+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
								+ "</STYLE>";
						if (plant.equalsIgnoreCase("")) {
							desc = desc
									+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /></center></td></tr> ";
							desc = desc
									+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
							desc = desc + "<tr><th align=left>Emp No.</th><td>"
									+ pernr
									+ "</td><th align=left>Name</th><td>"
									+ name + "</td></tr>";
							desc = desc + "<tr><th align=left>Dept</th><td>"
									+ dep + "</td><th align=left>Desg</th><td>"
									+ desg + "</td></tr>";
							desc = desc
									+ "<tr><th>Date Of Joining</th><td colspan=3>"
									+ doj + "</td></tr>";

							desc = desc
									+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=3>"
									+ ondutyType + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
									+ startDate
									+ "</td><th align=left>From Time</th><td>"
									+ fromTime + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
									+ endDate
									+ "<th align=left>End Time</th><td>"
									+ toTime + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
									+ reason + "</td></tr></table></br></br>";

							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
						} else {
							desc = desc
									+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /></center></td></tr> ";
							desc = desc
									+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
							desc = desc + "<tr><th align=left>Emp No.</th><td>"
									+ pernr + "</td><th>Name</th><td>" + name
									+ "</td></tr>";
							desc = desc + "<tr><th align=left>Dept</th><td>"
									+ dep + "</td><th align=left>Desg</th><td>"
									+ desg + "</td></tr>";
							desc = desc
									+ "<tr><th>Date Of Joining</th><td colspan=3>"
									+ doj + "</td></tr>";

							desc = desc
									+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=1>"
									+ ondutyType + "</td><th>Plant</th><td>"
									+ plant + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
									+ startDate
									+ "</td><th align=left>From Time</th><td>"
									+ fromTime + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
									+ endDate
									+ "<th align=left>End Time</th><td>"
									+ toTime + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
									+ reason + "</td></tr></table></br></br>";

							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
						}
						approverDesc = desc;

					}
					if (dataFlowType.equalsIgnoreCase("parallel")) {
						String toAddress = "";

						String parallelApprover1 = "";
						String parallelApprover2 = "";
						String empIDS = "";
						String empEmail = "";
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ empNo
								+ "' and essType='Leave' and reqType='parallel'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");
							parallelApprover1 = rsApproverDsg
									.getString("Parallel_Approver1");
							parallelApprover2 = rsApproverDsg
									.getString("Parallel_Approver2");
						}
						if (!(approver1.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";
							if (plant.equalsIgnoreCase("")) {
								desc = desc
										+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
										+ "https://portal.microlabs.co.in/images/logo.png"
										+ " height=60 width=50  /></center></td></tr> ";
								desc = desc
										+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left>Emp No.</th><td>"
										+ pernr
										+ "</td><th align=left>Name</th><td>"
										+ name + "</td></tr>";
								desc = desc
										+ "<tr><th align=left>Dept</th><td>"
										+ dep
										+ "</td><th align=left>Desg</th><td>"
										+ desg + "</td></tr>";
								desc = desc
										+ "<tr><th>Date Of Joining</th><td colspan=3>"
										+ doj + "</td></tr>";

								desc = desc
										+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=3>"
										+ ondutyType + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
										+ startDate
										+ "</td><th align=left>From Time</th><td>"
										+ fromTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
										+ endDate
										+ "<th align=left>End Time</th><td>"
										+ toTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
										+ reason
										+ "</td></tr></table></br></br>";

								desc = desc
										+ "<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ approver1
										+ "&reqId="
										+ Req_Id
										+ "&status=Approve><img src="
										+ "https://portal.microlabs.co.in/images/Approve.png"
										+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ approver1
										+ "&reqId="
										+ Req_Id
										+ "&status=Reject><img src="
										+ "https://portal.microlabs.co.in/images/Reject.png"
										+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
								desc = desc
										+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
										+ "https://portal.microlabs.co.in/"
										+ ">EMicro Portal</a> .</span> ";
								desc = desc + "</body></html>";
							} else {
								desc = desc
										+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
										+ "https://portal.microlabs.co.in/images/logo.png"
										+ " height=60 width=50  /></center></td></tr> ";
								desc = desc
										+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left>Emp No.</th><td>"
										+ pernr + "</td><th>Name</th><td>"
										+ name + "</td></tr>";
								desc = desc
										+ "<tr><th align=left>Dept</th><td>"
										+ dep
										+ "</td><th align=left>Desg</th><td>"
										+ desg + "</td></tr>";
								desc = desc
										+ "<tr><th>Date Of Joining</th><td colspan=3>"
										+ doj + "</td></tr>";

								desc = desc
										+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=1>"
										+ ondutyType
										+ "</td><th>Plant</th><td>" + plant
										+ "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
										+ startDate
										+ "</td><th align=left>From Time</th><td>"
										+ fromTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
										+ endDate
										+ "<th align=left>End Time</th><td>"
										+ toTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
										+ reason
										+ "</td></tr></table></br></br>";

								desc = desc
										+ "<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ approver1
										+ "&reqId="
										+ Req_Id
										+ "&status=Approve><img src="
										+ "https://portal.microlabs.co.in/images/Approve.png"
										+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ approver1
										+ "&reqId="
										+ Req_Id
										+ "&status=Reject><img src="
										+ "https://portal.microlabs.co.in/images/Reject.png"
										+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
								desc = desc
										+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
										+ "https://portal.microlabs.co.in/"
										+ ">EMicro Portal</a> .</span> ";
								desc = desc + "</body></html>";
							}
							approverDesc = desc;
						}
						if (!(parallelApprover1.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";
							if (plant.equalsIgnoreCase("")) {
								desc = desc
										+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
										+ "https://portal.microlabs.co.in/images/logo.png"
										+ " height=60 width=50  /></center></td></tr> ";
								desc = desc
										+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left>Emp No.</th><td>"
										+ pernr
										+ "</td><th align=left>Name</th><td>"
										+ name + "</td></tr>";
								desc = desc
										+ "<tr><th align=left>Dept</th><td>"
										+ dep
										+ "</td><th align=left>Desg</th><td>"
										+ desg + "</td></tr>";
								desc = desc
										+ "<tr><th>Date Of Joining</th><td colspan=3>"
										+ doj + "</td></tr>";

								desc = desc
										+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=3>"
										+ ondutyType + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
										+ startDate
										+ "</td><th align=left>From Time</th><td>"
										+ fromTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
										+ endDate
										+ "<th align=left>End Time</th><td>"
										+ toTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
										+ reason
										+ "</td></tr></table></br></br>";

								desc = desc
										+ "<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover1
										+ "&reqId="
										+ Req_Id
										+ "&status=Approve><img src="
										+ "https://portal.microlabs.co.in/images/Approve.png"
										+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover1
										+ "&reqId="
										+ Req_Id
										+ "&status=Reject><img src="
										+ "https://portal.microlabs.co.in/images/Reject.png"
										+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
								desc = desc
										+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
										+ "https://portal.microlabs.co.in/"
										+ ">EMicro Portal</a> .</span> ";
								desc = desc + "</body></html>";
							} else {
								desc = desc
										+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
										+ "https://portal.microlabs.co.in/images/logo.png"
										+ " height=60 width=50  /></center></td></tr> ";
								desc = desc
										+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left>Emp No.</th><td>"
										+ pernr + "</td><th>Name</th><td>"
										+ name + "</td></tr>";
								desc = desc
										+ "<tr><th align=left>Dept</th><td>"
										+ dep
										+ "</td><th align=left>Desg</th><td>"
										+ desg + "</td></tr>";
								desc = desc
										+ "<tr><th>Date Of Joining</th><td colspan=3>"
										+ doj + "</td></tr>";

								desc = desc
										+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=1>"
										+ ondutyType
										+ "</td><th>Plant</th><td>" + plant
										+ "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
										+ startDate
										+ "</td><th align=left>From Time</th><td>"
										+ fromTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
										+ endDate
										+ "<th align=left>End Time</th><td>"
										+ toTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
										+ reason
										+ "</td></tr></table></br></br>";

								desc = desc
										+ "<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover1
										+ "&reqId="
										+ Req_Id
										+ "&status=Approve><img src="
										+ "https://portal.microlabs.co.in/images/Approve.png"
										+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover1
										+ "&reqId="
										+ Req_Id
										+ "&status=Reject><img src="
										+ "https://portal.microlabs.co.in/images/Reject.png"
										+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
								desc = desc
										+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
										+ "https://portal.microlabs.co.in/"
										+ ">EMicro Portal</a> .</span> ";
								desc = desc + "</body></html>";
							}
							parallelApprover1Desc = desc;
						}
						if (!(parallelApprover2.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";
							if (plant.equalsIgnoreCase("")) {
								desc = desc
										+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
										+ "https://portal.microlabs.co.in/images/logo.png"
										+ " height=60 width=50  /></center></td></tr> ";
								desc = desc
										+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left>Emp No.</th><td>"
										+ pernr
										+ "</td><th align=left>Name</th><td>"
										+ name + "</td></tr>";
								desc = desc
										+ "<tr><th align=left>Dept</th><td>"
										+ dep
										+ "</td><th align=left>Desg</th><td>"
										+ desg + "</td></tr>";
								desc = desc
										+ "<tr><th>Date Of Joining</th><td colspan=3>"
										+ doj + "</td></tr>";

								desc = desc
										+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=3>"
										+ ondutyType + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
										+ startDate
										+ "</td><th align=left>From Time</th><td>"
										+ fromTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
										+ endDate
										+ "<th align=left>End Time</th><td>"
										+ toTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
										+ reason
										+ "</td></tr></table></br></br>";

								desc = desc
										+ "<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover2
										+ "&reqId="
										+ Req_Id
										+ "&status=Approve><img src="
										+ "https://portal.microlabs.co.in/images/Approve.png"
										+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover2
										+ "&reqId="
										+ Req_Id
										+ "&status=Reject><img src="
										+ "https://portal.microlabs.co.in/images/Reject.png"
										+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
								desc = desc
										+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
										+ "https://portal.microlabs.co.in/"
										+ ">EMicro Portal</a> .</span> ";
								desc = desc + "</body></html>";
							} else {
								desc = desc
										+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
										+ "https://portal.microlabs.co.in/images/logo.png"
										+ " height=60 width=50  /></center></td></tr> ";
								desc = desc
										+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left>Emp No.</th><td>"
										+ pernr + "</td><th>Name</th><td>"
										+ name + "</td></tr>";
								desc = desc
										+ "<tr><th align=left>Dept</th><td>"
										+ dep
										+ "</td><th align=left>Desg</th><td>"
										+ desg + "</td></tr>";
								desc = desc
										+ "<tr><th>Date Of Joining</th><td colspan=3>"
										+ doj + "</td></tr>";

								desc = desc
										+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=1>"
										+ ondutyType
										+ "</td><th>Plant</th><td>" + plant
										+ "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
										+ startDate
										+ "</td><th align=left>From Time</th><td>"
										+ fromTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
										+ endDate
										+ "<th align=left>End Time</th><td>"
										+ toTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
										+ reason
										+ "</td></tr></table></br></br>";

								desc = desc
										+ "<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover2
										+ "&reqId="
										+ Req_Id
										+ "&status=Approve><img src="
										+ "https://portal.microlabs.co.in/images/Approve.png"
										+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover2
										+ "&reqId="
										+ Req_Id
										+ "&status=Reject><img src="
										+ "https://portal.microlabs.co.in/images/Reject.png"
										+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
								desc = desc
										+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
										+ "https://portal.microlabs.co.in/"
										+ ">EMicro Portal</a> .</span> ";
								desc = desc + "</body></html>";
							}
							parallelApprover2Desc = desc;
						}
					}

				}
				if (existStatus == false) {

					desc = desc + "";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";
					if (plant.equalsIgnoreCase("")) {
						desc = desc
								+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
								+ "https://portal.microlabs.co.in/images/logo.png"
								+ " height=60 width=50  /></center></td></tr> ";
						desc = desc
								+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
						desc = desc + "<tr><th align=left>Emp No.</th><td>"
								+ pernr + "</td><th align=left>Name</th><td>"
								+ name + "</td></tr>";
						desc = desc + "<tr><th align=left>Dept</th><td>" + dep
								+ "</td><th align=left>Desg</th><td>" + desg
								+ "</td></tr>";
						desc = desc
								+ "<tr><th>Date Of Joining</th><td colspan=3>"
								+ doj + "</td></tr>";

						desc = desc
								+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=3>"
								+ ondutyType + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
								+ startDate
								+ "</td><th align=left>From Time</th><td>"
								+ fromTime + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
								+ endDate + "<th align=left>End Time</th><td>"
								+ toTime + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
								+ reason + "</td></tr></table></br></br>";

						desc = desc
								+ "<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
								+ pApprover
								+ "&reqId="
								+ Req_Id
								+ "&status=Approve><img src="
								+ "https://portal.microlabs.co.in/images/Approve.png"
								+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
								+ pApprover
								+ "&reqId="
								+ Req_Id
								+ "&status=Reject><img src="
								+ "https://portal.microlabs.co.in/images/Reject.png"
								+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
						desc = desc
								+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
								+ "https://portal.microlabs.co.in/"
								+ ">EMicro Portal</a> .</span> ";
						desc = desc + "</body></html>";
					} else {
						desc = desc
								+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
								+ "https://portal.microlabs.co.in/images/logo.png"
								+ " height=60 width=50  /></center></td></tr> ";
						desc = desc
								+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
						desc = desc + "<tr><th align=left>Emp No.</th><td>"
								+ pernr + "</td><th>Name</th><td>" + name
								+ "</td></tr>";
						desc = desc + "<tr><th align=left>Dept</th><td>" + dep
								+ "</td><th align=left>Desg</th><td>" + desg
								+ "</td></tr>";
						desc = desc
								+ "<tr><th>Date Of Joining</th><td colspan=3>"
								+ doj + "</td></tr>";

						desc = desc
								+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=1>"
								+ ondutyType + "</td><th>Plant</th><td>"
								+ plant + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
								+ startDate
								+ "</td><th align=left>From Time</th><td>"
								+ fromTime + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
								+ endDate + "<th align=left>End Time</th><td>"
								+ toTime + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
								+ reason + "</td></tr></table></br></br>";

						desc = desc
								+ "<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
								+ pApprover
								+ "&reqId="
								+ Req_Id
								+ "&status=Approve><img src="
								+ "https://portal.microlabs.co.in/images/Approve.png"
								+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
								+ pApprover
								+ "&reqId="
								+ Req_Id
								+ "&status=Reject><img src="
								+ "https://portal.microlabs.co.in/images/Reject.png"
								+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
						desc = desc
								+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
								+ "https://portal.microlabs.co.in/"
								+ ">EMicro Portal</a> .</span> ";
						desc = desc + "</body></html>";
					}
				}

			} else if (requestFrom.equalsIgnoreCase("Permission")) {
				if(empNo.equalsIgnoreCase(user.getEmployeeNo()))
					mailForm.setSubject("Permission Request From "+user.getFullName());
					else
					mailForm.setSubject("Permission Request From "+Empname(empNo));
				boolean existStatus = false;
				String dataFlowType = "";
				String checkESSApproverList = "select count(*) from ESS_Approvers where employeeNumber='"
						+ empNo + "' and essType='Leave'";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					int number = rsCheckInEss.getInt(1);
					if (number == 0) {
						existStatus = false;
					} else {
						existStatus = true;
					}
				}
				if (existStatus == true) {
					String getDataFlowType = "select * from ESS_Approvers where employeeNumber='"
							+ empNo + "' and essType='Leave'";
					ResultSet rsDataFlowType = ad.selectQuery(getDataFlowType);
					while (rsDataFlowType.next()) {
						dataFlowType = rsDataFlowType.getString("reqType");
					}
					if (dataFlowType.equalsIgnoreCase("sequential")) {
						approver1 = "";
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ empNo
								+ "' and essType='Leave' and reqType='sequential'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");

						}

						desc = desc + "";
						desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
								+ "TABLE {border-collapse:collapse;border:1px solid black;}"
								+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
								+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
								+ "</STYLE>";

						desc = desc
								+ "<html><body><img src="
								+ "https://portal.microlabs.co.in/images/logo.png"
								+ " height=60 width=50  /> <table><tr><th colspan=4 ><center>Permission Details</center></th></tr>";
						desc = desc
								+ "<tr><th align=left>Permission Date</th><td>"
								+ perDate + "</td><th>Permission Type</th><td>"
								+ type + "</td></tr>";
						desc = desc + "<tr><th align=left> Time</th><td >"
								+ perStartTime
								+ "</td><th align=left> Swipe Type</th><td >"
								+ swipetype + "</td></tr>";
						desc = desc + "<tr><th>Reason</th><td colspan=3>"
								+ perReason + "</tr></table></br></br>";
						desc = desc
								+ "<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
								+ approver1
								+ "&reqId="
								+ Req_Id
								+ "&status=Approve><img src="
								+ "https://portal.microlabs.co.in/images/Approve.png"
								+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
								+ approver1
								+ "&reqId="
								+ Req_Id
								+ "&status=Reject><img src="
								+ "https://portal.microlabs.co.in/images/Reject.png"
								+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
						desc = desc
								+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
								+ "https://portal.microlabs.co.in/"
								+ ">EMicro Portal</a> .</span> ";
						desc = desc + "</body></html>";
						approverDesc = desc;

					}
					if (dataFlowType.equalsIgnoreCase("parallel")) {
						String toAddress = "";

						String parallelApprover1 = "";
						String parallelApprover2 = "";
						String empIDS = "";
						String empEmail = "";
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ empNo
								+ "' and essType='Leave' and reqType='parallel'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");
							parallelApprover1 = rsApproverDsg
									.getString("Parallel_Approver1");
							parallelApprover2 = rsApproverDsg
									.getString("Parallel_Approver2");
						}
						if (!(approver1.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";

							desc = desc
									+ "<html><body><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /> <table><tr><th colspan=4 ><center>Permission Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left>Permission Date</th><td>"
									+ perDate
									+ "</td><th>Permission Type</th><td>"
									+ type + "</td></tr>";
							desc = desc
									+ "<tr><th align=left> Time</th><td >"
									+ perStartTime
									+ "</td><th align=left> Swipe Type</th><td >"
									+ swipetype + "</td></tr>";
							desc = desc + "<tr><th>Reason</th><td colspan=3>"
									+ perReason + "</tr></table></br></br>";
							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
							approverDesc = desc;
						}
						if (!(parallelApprover1.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";

							desc = desc
									+ "<html><body><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /> <table><tr><th colspan=4 ><center>Permission Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left>Permission Date</th><td>"
									+ perDate
									+ "</td><th>Permission Type</th><td>"
									+ type + "</td></tr>";
							desc = desc
									+ "<tr><th align=left> Time</th><td >"
									+ perStartTime
									+ "</td><th align=left> Swipe Type</th><td >"
									+ swipetype + "</td></tr>";
							desc = desc + "<tr><th>Reason</th><td colspan=3>"
									+ perReason + "</tr></table></br></br>";
							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
									+ parallelApprover1
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
									+ parallelApprover1
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
							parallelApprover1Desc = desc;
						}
						if (!(parallelApprover2.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";

							desc = desc
									+ "<html><body><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /> <table><tr><th colspan=4 ><center>Permission Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left>Permission Date</th><td>"
									+ perDate
									+ "</td><th>Permission Type</th><td>"
									+ type + "</td></tr>";
							desc = desc
									+ "<tr><th align=left> Time</th><td >"
									+ perStartTime
									+ "</td><th align=left> Swipe Type</th><td >"
									+ swipetype + "</td></tr>";
							desc = desc + "<tr><th>Reason</th><td colspan=3>"
									+ perReason + "</tr></table></br></br>";
							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
									+ parallelApprover2
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
									+ parallelApprover2
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
							parallelApprover2Desc = desc;
						}
					}

				}
				if (existStatus == false) {
					desc = desc + "";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";

					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /> <table><tr><th colspan=4 ><center>Permission Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Permission Date</th><td colspan=3>"
							+ perDate + "</td><th>Permission Type</th><td>"
							+ type + "</td></tr>";
					desc = desc + "<tr><th align=left> Time</th><td >"
							+ perStartTime
							+ "</td><th align=left> Swipe Type</th><td >"
							+ swipetype + "</td></tr>";
					desc = desc + "<tr><th>Reason</th><td colspan=3>"
							+ perReason + "</tr></table></br></br>";
					desc = desc
							+ "<a href="
							+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
							+ pApprover
							+ "&reqId="
							+ Req_Id
							+ "&status=Approve><img src="
							+ "https://portal.microlabs.co.in/images/Approve.png"
							+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
							+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
							+ pApprover
							+ "&reqId="
							+ Req_Id
							+ "&status=Reject><img src="
							+ "https://portal.microlabs.co.in/images/Reject.png"
							+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
				}
			}

			else if (requestFrom.equalsIgnoreCase("Feedback")) {
				desc = desc + "Subject : " + feedSub + "</br>";
				desc = desc + "Comments : " + feedComment + "</br>";
			}

			mailForm.setDescription(desc);

			if (requestFrom.equalsIgnoreCase("Leave")
					|| requestFrom.equalsIgnoreCase("On Duty")
					|| requestFrom.equalsIgnoreCase("Permission")) {
				String essType = "";
				if (requestFrom.equalsIgnoreCase("Leave"))
					essType = "Leave";

				if (requestFrom.equalsIgnoreCase("On Duty"))
					essType = "Leave";

				if (requestFrom.equalsIgnoreCase("Permission"))
					essType = "Leave";

				boolean existStatus = false;
				String dataFlowType = "";
				String checkESSApproverList = "select count(*) from ESS_Approvers where employeeNumber='"
						+ empNo + "' and essType='" + essType + "'";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					int number = rsCheckInEss.getInt(1);
					if (number == 0) {
						existStatus = false;
					} else {
						existStatus = true;
					}
				}
				if (existStatus == true) {
					String getDataFlowType = "select * from ESS_Approvers where employeeNumber='"
							+ empNo + "' and essType='" + essType + "'";
					ResultSet rsDataFlowType = ad.selectQuery(getDataFlowType);
					while (rsDataFlowType.next()) {
						dataFlowType = rsDataFlowType.getString("reqType");
					}
					if (dataFlowType.equalsIgnoreCase("sequential")) {

						String getEmpIDs = "select EMAIL_ID from emp_official_info where PERNR in ("
								+ approver1 + ")";
						ResultSet rsEMPIDS = ad.selectQuery(getEmpIDs);
						int ii = 0;
						String approverEmail1 = "";
						while (rsEMPIDS.next()) {
							approverEmail1 = rsEMPIDS.getString("EMAIL_ID");
						}
						mailForm.setDescription(approverDesc);
						mailAction.mailSendToParallelApprovers(request,
								mailForm, "request", approverEmail1);
					}
					if (dataFlowType.equalsIgnoreCase("parallel")) {
						String toAddress = "";

						String empIDS = "";
						String parallelApprover1 = "";
						String parallelApprover2 = "";
						String approverEmail1 = "";
						String parallelAppr1Email = "";
						String parallelAppr2Email = "";
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ empNo
								+ "' and essType='Leave' and reqType='parallel'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");
							parallelApprover1 = rsApproverDsg
									.getString("Parallel_Approver1");
							parallelApprover2 = rsApproverDsg
									.getString("Parallel_Approver2");
						}
						getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ empNo
								+ "' and essType='Leave' and reqType='parallel'  and  Priority=1";
						rsApproverDsg = ad.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");
							if (!(approver1.equalsIgnoreCase(""))) {
								empIDS = rsApproverDsg.getString("ApproverId");
							}
							parallelApprover1 = rsApproverDsg
									.getString("Parallel_Approver1");
							if (!(parallelApprover1.equalsIgnoreCase(""))) {
								empIDS = empIDS
										+ ","
										+ rsApproverDsg
												.getString("Parallel_Approver1");
							}
							parallelApprover2 = rsApproverDsg
									.getString("Parallel_Approver2");
							if (!(parallelApprover2.equalsIgnoreCase(""))) {
								empIDS = empIDS
										+ ","
										+ rsApproverDsg
												.getString("Parallel_Approver2");
							}

						}

						empIDS = empIDS.substring(0, (empIDS.length() - 0));
						String getEmpIDs = "select EMAIL_ID,PERNR from emp_official_info where PERNR in ("
								+ empIDS + ")";
						ResultSet rsEMPIDS = ad.selectQuery(getEmpIDs);
						int ii = 0;
						while (rsEMPIDS.next()) {
							if (approver1.equalsIgnoreCase(rsEMPIDS
									.getString("PERNR"))) {
								approverEmail1 = rsEMPIDS.getString("EMAIL_ID");
								mailForm.setDescription(approverDesc);
								mailAction.mailSendToParallelApprovers(request,
										mailForm, "request", approverEmail1);
							}
							if (parallelApprover1.equalsIgnoreCase(rsEMPIDS
									.getString("PERNR"))) {
								parallelAppr1Email = rsEMPIDS
										.getString("EMAIL_ID");
								mailForm.setDescription(parallelApprover1Desc);
								mailAction
										.mailSendToParallelApprovers(request,
												mailForm, "request",
												parallelAppr1Email);
							}
							if (parallelApprover2.equalsIgnoreCase(rsEMPIDS
									.getString("PERNR"))) {
								parallelAppr2Email = rsEMPIDS
										.getString("EMAIL_ID");
								mailForm.setDescription(parallelApprover2Desc);
								mailAction
										.mailSendToParallelApprovers(request,
												mailForm, "request",
												parallelAppr2Email);
							}
							ii++;
						}
					}
				} else {
					mailAction
							.mailSendToRecipient(request, mailForm, "request");
				}

			}
			if (requestFrom.equalsIgnoreCase("Material Code Request")) {
				mailAction.mailSendToRecipient(request, mailForm, "request");
				if (!(parallelApp1.equalsIgnoreCase(""))) {
					getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ parallelApp1 + "'";
					approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					mailAction
							.mailSendToRecipient(request, mailForm, "request");
				}
				if (!(parallelApp2.equalsIgnoreCase(""))) {
					getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ parallelApp2 + "'";
					approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					mailAction
							.mailSendToRecipient(request, mailForm, "request");
				}
			}
			if (requestFrom.equalsIgnoreCase("Vendor Master")
					|| requestFrom.equalsIgnoreCase("Customer Master")
					|| requestFrom.equalsIgnoreCase("Service Master")
					|| requestFrom.equalsIgnoreCase("Code Extension")) {
				mailAction.mailSendToRecipient(request, mailForm, "request");
				if (!(parallelApp1.equalsIgnoreCase(""))) {
					getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ parallelApp1 + "'";
					approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					mailAction
							.mailSendToRecipient(request, mailForm, "request");
				}
				if (!(parallelApp2.equalsIgnoreCase(""))) {
					getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ parallelApp2 + "'";
					approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					mailAction
							.mailSendToRecipient(request, mailForm, "request");
				}
			}
			success = 1;
		} catch (ServletException se) {
			System.out.println("ServletException @ sending leave request..");
			se.printStackTrace();
		} catch (MessagingException se) {
			System.out.println("MessagingException @ sending leave request..");
			se.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("IOException @ sending leave request..");
			ioe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("SQLException @ sending  request..");
			sqle.printStackTrace();
		}
		return success;
	}

	public int sendMailToApprover(HttpServletRequest request,
			String approvermail, String Req_Id, String requestFrom) {
		int success = 0;
		HttpSession session = request.getSession();

		UserInfo user = (UserInfo) session.getAttribute("user");
		String fullName = user.getFullName();
		MailInboxForm mailForm = new MailInboxForm();
		MailInboxAction mailAction = new MailInboxAction();
		int userId = user.getId();

		try {

			// getting details from corresponding table
			String swipetype = "";
			String type = "";
			String submitDate = "";
			String location = "";
			String requesterName = "";
			String matGroup = "";
			String pApprover = "";
			String vcmasterName = "";
			String vcmasterPAN = "";
			String vcmasterEMAIL = "";
			String typeofvc = "";
			String noOfDays = "";
			String ondutyType = "";
			String plant = "";
			String fromTime = "";
			String toTime = "";
			String duration = "";
			String startDate = "";
			String endDate = "";
			String leaveType = ""; // getLeaveType(leaveType);
			String holidayType = "";
			String reqEmp = "";
			String empQual = "";
			String empExp = "";
			String feedComment = "";
			String feedFrom = "";
			String feedSub = "";
			// for material
			String matcode = "";
			String materialType = "";
			String reqMatType = "";
			String matsName = "";
			String matgName = "";
			String pharmaName = "";
			// for service master
			String mcname = "";
			String serCat = "";
			String serDes = "";
			String serPur = "";
			String requestTime = "";
			String reason = "";
			String reasonType = "";
			// for permission
			String perDate = "";
			String perStartTime = "";
			String perEndTime = "";
			String perReason = "";
			String startDur = "";
			String endDur = "";
			double casualOpeningBal = 0;
			double casualAvailedBal = 0;
			double casualClosingBal = 0;
			double casualAwatingBal = 0;

			double sickOpeningBal = 0;
			double sickAvailedBal = 0;
			double sickClosingBal = 0;
			double sickAwatingBal = 0;

			double previlegeOpeningBal = 0;
			double previlegeAvailedBal = 0;
			double previlegeClosingBal = 0;
			double previlegeAwatingBal = 0;

			double maternityOpeningBal = 0;
			double maternityAvailedBal = 0;
			double maternityClosingBal = 0;
			double maternityAwatingBal = 0;
			
			double prevcasualAvailedBal=0;
			double prevsickAvailedBal=0;
			double prevprevilegeAvailedBal=0;
			
			String approverDesc = "";
			String approver1 = "";
			String parallelApp1 = "";
			String parallelApp2 = "";

			// vendor master

			String vendorName = "";
			String vendorCity = "";
			String vedorType = "";
			String vendorType = "";
			// customer master
			String customerName = "";
			String customerCity = "";
			String customerType = "";
			// code extension
			String sapNo = "";
			String fromPlant = "";
			String toPlant = "";
			String fromStorage = "";
			String toStorage = "";
			String reqCodeType = "";
			String parallelApprover1Desc = "";
			String parallelApprover2Desc = "";
			String tName = "leave_details";
			
			String purchase_group_desc="";
			
			String Storage_location_Name="";
			String ltxt="";
			String Shortname="";
			String longname="";
			String matType = "";
			String manufactured_at="";
			String brand_desc="";
			String div_desc="";
			String detailed_Justification="";
			String detailed_Specification="";
			
			
			String account_group_id="";
			String title="";
			String address1="";
			String address2="";
			String address3="";
			String address4="";
			String pinCode="";
			String landx="";
			String state="";
			String bezei="";
			String landLine_No="";
			String mobile_no="";
			String faxNo="";
			String email_Id="";
			String account_group_name=""; 
			
		 
			
			String valuation_desc="";
			String service_Description="";
			String detailed_desc="";
			String machine_name="";
			String app_value="";
			String stxt="";
			
			String customer_code="";
			String category=""; 
			
			if(requestFrom.equalsIgnoreCase("HR Query")){
				
				String getcomoff = "select * from HR_QUERIES where id='"+Req_Id+"'";
				ResultSet ff = ad.selectQuery(getcomoff);
				try {
					while (ff.next()) {
							

						reason=ff.getString("subject");
						detailed_desc=ff.getString("description");
						reqEmp=ff.getString("Req_by");
						category=ff.getString("category");
				
					}
				} catch (SQLException e) {

					e.printStackTrace();
				}
				
				
				//requester 
				
				 HelpDeskForm help=new HelpDeskForm();
				String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+reqEmp+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{
						requesterName=rs.getString("EMP_FULLNAME");
						
						
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
				
				
				mailForm.setEmpname(requesterName);
				mailForm.setSendermail("");
				
				//Approver
				String getAppEmailID="select EMAIL_ID from emp_official_info where PERNR='"+approvermail+"'";
				ResultSet approverRS=ad.selectQuery(getAppEmailID);
				while(approverRS.next()){
					approvermail = approverRS.getString(1);
				}
				mailForm.setToAddress(approvermail);
				System.out.println("m -> "+approvermail);
				mailForm.setccAddress("");
				mailForm.setbccAddress("");
				String desc = "";
				mailForm.setSubject("HR Query Request From "+requesterName);
				desc=desc+"";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
		             +"TABLE {border-collapse:collapse;border:1px solid black;}"
		             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
		             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
				 +"</STYLE>";
				desc=desc+"<html><body><img src="+"https://portal.microlabs.co.in/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=5 ><center><big>HR Query Request Details</big></center></th></tr>";

				desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
				+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
				+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
				+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
				
				desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
						+ "<tr><td><b>Request No</td><td colspan=3>"+Req_Id+"</td></tr>"
							+ "<tr><td><b>Category</td><td colspan=3>"+category+"</td></tr>"
						+ "<tr><td><b>Subject</td><td colspan=3>"+reason+"</td></tr>"
				+ "<tr><td><b>Description</b></td><td colspan=3>"+detailed_desc+"</td></tr>"
			
				;
				
				desc=desc+"</table><br><br>";
			   
			    desc=desc+"</br></br>";
				desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"https://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
			    desc=desc+"</body></html>";
			
			    mailForm.setDescription(desc);
				mailAction.mailSendToRecipient(request, mailForm,"request");
			}
			
			
			
			if(requestFrom.equalsIgnoreCase("Travel Desk")){
				
				TravelRequestForm t =new TravelRequestForm();
				ArrayList data=new ArrayList();
				ArrayList emplist=new ArrayList();
				ArrayList guestlist=new ArrayList();
				String reqType="";
				String lis="select CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1, CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as bookeddate1,"
						+ "CONVERT(varchar(10),Depart_On,103)  as departon,"
						+ "CONVERT(varchar(10),Return_On,103)  as returnon,CONVERT(varchar(10),billdate,103)  as billdate1,* from Travel_Desk where reqNo='"+Req_Id+"' ";
				ResultSet ff=ad.selectQuery(lis);
				try {
					if(ff.next())
					{
						
						t.setRequestNumber(ff.getInt("reqNo"));
						t.setReqType(ff.getString("Req_type"));
						reqType=ff.getString("Req_type");
						t.setTypeOfTravel(ff.getString("Type_of_travel"));
						t.setTravelFor(ff.getString("Travel_for"));
						t.setTravelRequestFor(ff.getString("Travel_Request_for"));
						reqEmp=Integer.toString(ff.getInt("PERNR"));
						
						
					
						
						
						
						t.setModeOfTravel(ff.getString("travelmode"));
						t.setFromPlace(ff.getString("From_place"));
						t.setDepartOn(ff.getString("departon"));
						t.setDepartTime(ff.getString("Preferred_depart_time"));
						//t.setReturnTime(ff.getString("Preferred_return_time"));	
					//	t.setDepartRemarks(ff.getString("Depart_Time_remarks"));
						//t.setReturnRemarks(ff.getString("Return_Time_remarks"));
		               t.setToPlace(ff.getString("To_place"));
		             //  t.setVia(ff.getString("via"));
		              // t.setRemarks(ff.getString("purposetext"));
		               t.setPurposeOfVisit(ff.getString("purposetype"));
		            //   t.setReturnOn(ff.getString("returnon"));
		               
						if(!ff.getString("Preferred_return_time").equalsIgnoreCase("null"))
						{
							t.setReturnTime(ff.getString("Preferred_return_time"));	
						}
						else
						{
							t.setReturnTime(ff.getString(""));	
						}
					/*	if(!ff.getString("Depart_Time_remarks").equalsIgnoreCase("null"))
						{
							t.setDepartRemarks(ff.getString("Depart_Time_remarks"));	
						}
						else
						{
							t.setDepartRemarks(ff.getString(""));	
						}
					
						if(!ff.getString("Return_Time_remarks").equalsIgnoreCase("null"))
						{
							t.setReturnRemarks(ff.getString("Return_Time_remarks"));
						}
						else
						{
							t.setReturnRemarks(ff.getString(""));	
						}*/
						
		               t.setToPlace(ff.getString("To_place"));
		              
		               
		              
		            	   t.setVia(ff.getString("Via"));
				
		               
		               /*if(!ff.getString("Remarks").equalsIgnoreCase("null"))
						{
		            	   t.setRemarks(ff.getString("Remarks"));
						}
						else
						{
							 t.setRemarks(ff.getString(""));	
						}*/
		             
		             
		               if(!ff.getString("purposetext").equalsIgnoreCase("NULL"))
		               {
		            	   t.setPurposeOfVisit(ff.getString("purposetext")); 
		               }
		               else
		               {
		            	   t.setPurposeOfVisit("");  
		               }
		            	
		            	   if(ff.getString("returnon")!=null)
			               {  
		            		   t.setReturnOn(ff.getString("returnon"));
			               }
			               else
			               {
			            	   t.setReturnOn("");  
			               }
		             t.setReqDate(ff.getString("Created_date1"));
		              t.setTravelagentname(ff.getString("travelagentname"));
		              t.setBillno(ff.getString("billno"));
		              t.setBilldate(ff.getString("billdate1"));
		              t.setBillamount(ff.getString("billamount"));
		              t.setBookingstatus(ff.getString("bookingstatus"));
		              t.setBookeddate(ff.getString("bookeddate1"));
		              t.setBookedby(Empname(ff.getString("bookedby")));
		              t.setConfirmdate(ff.getString("confirmdate1"));
		              t.setConfirmby(Empname(ff.getString("confirmby")));
		              t.setConfirmstatus(ff.getString("confirmstatus"));
		              
		              
		              approver1= ff.getString("Pending_Approver");
		              data.add(t);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("data", data);
				
				
				//requester 
				
				 HelpDeskForm help=new HelpDeskForm();
				String data1="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
						"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+reqEmp+"' and " +
						"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
				ResultSet rs= ad.selectQuery(data1);
				try {
					while(rs.next())
					{
						requesterName=rs.getString("EMP_FULLNAME");
						
						
						help.setRequestername(rs.getString("EMP_FULLNAME"));
						help.setRequesterdepartment(rs.getString("DPTSTXT"));
						help.setRequesterdesignation(rs.getString("DSGSTXT"));
						
						if(rs.getString("TEL_EXTENS")!=null)
						{
							if(rs.getString("TEL_EXTENS").equalsIgnoreCase("null"))
							{
								help.setExtno("");
							}
							else
							{
								help.setExtno(rs.getString("TEL_EXTENS"));
							}
							
						}
						//help.setExtno(rs.getString("TEL_EXTENS"));
						help.setIpPhoneno(rs.getString("IP_PHONE"));
						help.setLocation(rs.getString("LOCATION_CODE"));
						help.setEmpno(rs.getString("PERNR"));
						
						
					}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				
				
				mailForm.setEmpname(requesterName);
				mailForm.setSendermail("");
				
				mailForm.setAttachNames("TP_"+Req_Id);
				InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				Properties props = null;
				try {
					props = PropertiesLoaderUtils.loadAllProperties("db.properties");
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
							
							
				String uploadFilePath=props.getProperty("file.uploadFilePath");
				String filePath=uploadFilePath+"/EMicro Files/ESS/Travel Request/PDF/";
				filePath=filePath.replace("/", "\\")+""+Req_Id+"_Travel_desk_report.pdf";
				
				mailForm.setAttach(filePath);
				
							
							
							
				//Approver
				String getAppEmailID="select EMAIL_ID from emp_official_info where PERNR='"+approvermail+"'";
				ResultSet approverRS=ad.selectQuery(getAppEmailID);
				while(approverRS.next()){
					approvermail = approverRS.getString(1);
				}
				mailForm.setToAddress(approvermail);
				System.out.println("m -> "+approvermail);
				mailForm.setccAddress("");
				mailForm.setbccAddress("");
				String desc = "";
				mailForm.setSubject("Travel Request From "+requesterName);
				desc=desc+"";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
		             +"TABLE {border-collapse:collapse;border:1px solid black;}"
		             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
		             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
				 +"</STYLE>";
				desc=desc+"<html><body><img src="+"http://portal.microlabs.co.in/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=5 ><center><big>Travel Request Details</big></center></th></tr>";

				desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
				+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
				+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
				+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
				
				desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
						+ "	 <tr><td>Request Type</td><td colpsan=3><big><b>"+t.getReqType()+"</b></big></td></tr>"
				+ " <tr><td>Request No:</td><td>"+Req_Id+"</td><td>Request Date:</td><td>"+t.getReqDate()+"</td></tr>"
				+ "	<tr><td>Type of Travel:</td><td>"+t.getTypeOfTravel()+"</td>	<td>Travel For:</td><td>"+t.getTravelFor()+"</td>	</tr>"					
				+ " <tr><td>Mode Of Travel:</td><td>"+t.getModeOfTravel()+"</td><td>Travel Request For:</td><td>"+t.getTravelRequestFor()+"</td>	</tr>"
				+ " <tr><td>From:</td><td colspan=3>"+t.getFromPlace()+"</td></tr>"
		        + " <tr><td>Depart On:</td><td colspan=3>"+t.getDepartOn()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Preferred Time:</b>&nbsp;&nbsp;"+t.getDepartTime()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>"
			    + "	<tr><td>To:</td><td colspan=3>"+t.getToPlace()+"</td></tr>"
			    + " <tr><td>Return On:</td><td colspan=3>"+t.getReturnOn()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Preferred Time:</b>&nbsp;&nbsp;"+t.getReturnTime()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>"
			    + "<tr><td>Purpose of Visit:</td><td colspan=3>"+t.getPurposeOfVisit()+"</td></tr>";
				/*+ "<tr>	<td>Remarks:</td><td colspan=6 >"+t.getRemarks()+"</td></tr>";*/
			
				desc=desc+"</table><br><br>";
				
				desc=desc+"<a href="+"https://portal.microlabs.co.in/travelrequest.do?method=approveMailRequest&userID="+approver1+"&reqId="+Req_Id+"&status=Approve>"
						+ "<img src="+"https://portal.microlabs.co.in/images/Approve.png"+" height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;"
								+ "<a href="+"https://portal.microlabs.co.in/travelrequest.do?method=approveMailRequest&userID="+approver1+"&reqId="+Req_Id+"&status=Reject>"
								+ "<img src="+"https://portal.microlabs.co.in/images/Reject.png"+" height=20 width=80 title=Reject border=0 /></a></br></br>";
				
			   
			    desc=desc+"</br></br>";
				desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"http://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
			    desc=desc+"</body></html>";
			
			    mailForm.setDescription(desc);
				mailAction.mailSendToRecipient(request, mailForm,"request");
			}
			
			LeaveAction lAction = new LeaveAction();
			if (requestFrom.equalsIgnoreCase("Recruitment Request")) {
				tName = "Recruitment_Request";
				String recdetails = "select * from " + tName
						+ " where User_Id='" + userId + "' and Req_Id='"
						+ Req_Id + "'";
				ResultSet ldRS = ad.selectQuery(recdetails);
				while (ldRS.next()) {
					submitDate = ldRS.getString("Request_Date");
					pApprover = ldRS.getString("Pending_Approver");
					reqEmp = ldRS.getString("TotalEmp");
					empQual = ldRS.getString("Qualification");
					empExp = ldRS.getString("Experience");
				}

			} else if (requestFrom.equalsIgnoreCase("Add Man Power")) {
				/*
				 * tName = "Man_Power_Matrix"; String leavedetails =
				 * "select * from "
				 * +tName+" where user_id="+userId+" and Req_Id='"
				 * +Req_Id+"' and Status='Pending'"; ResultSet ldRS =
				 * ad.selectQuery(leavedetails); while(ldRS.next()){ submitDate
				 * = ldRS.getString("submit_date"); startDate =
				 * EMicroUtils.display1(ldRS.getDate("start_date")); pApprover =
				 * ldRS.getString("Pending_Approver"); leaveType =
				 * ldRS.getString("leave_type"); noOfDays =
				 * ldRS.getString("no_of_days"); holidayType =
				 * ldRS.getString("holiday_type"); }
				 */
			} else if (requestFrom.equalsIgnoreCase("Leave")) {
				tName = "leave_details";
				String leavedetails = "select * from " + tName
						+ " where user_id=" + user.getEmployeeNo()
						+ " and Req_Id='" + Req_Id + "'";
				ResultSet ldRS = ad.selectQuery(leavedetails);
				while (ldRS.next()) {
					leaveType = ldRS.getString("leave_type");
					if (leaveType.equalsIgnoreCase("1")) {
						leaveType = "Casual";
					}
					if (leaveType.equalsIgnoreCase("2")) {
						leaveType = "Sick";
					}
					if (leaveType.equalsIgnoreCase("3")) {
						leaveType = "Privilege";
					}
					if (leaveType.equalsIgnoreCase("4")) {
						leaveType = "Loss Of Pay";
					}
					if (leaveType.equalsIgnoreCase("5")) {
						leaveType = "Maternity";
					}
					startDate = EMicroUtils
							.display1(ldRS.getDate("start_date"));
					startDur = ldRS.getString("start_duration");
					if (startDur.equalsIgnoreCase("FD")) {
						startDur = "Full Day";
					}
					if (startDur.equalsIgnoreCase("FH")) {
						startDur = "First Half";
					}
					if (startDur.equalsIgnoreCase("SH")) {
						startDur = "Second Half";
					}
					endDate = EMicroUtils.display1(ldRS.getDate("end_date"));
					endDur = ldRS.getString("end_duration");
					if (endDur.equalsIgnoreCase("FD")) {
						endDur = "Full Day";
					}
					if (endDur.equalsIgnoreCase("FH")) {
						endDur = "First Half";
					}
					if (endDur.equalsIgnoreCase("SH")) {
						endDur = "Second Half";
					}
					reasonType = ldRS.getString("reasonType");
					reason = ldRS.getString("reason");
					pApprover = ldRS.getString("Pending_Approver");

					noOfDays = ldRS.getString("no_of_days");

					submitDate = ldRS.getString("submit_date");
				}
				String getApproverID = "select * from emp_official_info where PERNR='"
						+ pApprover + "'";
				ResultSet approverRS = ad.selectQuery(getApproverID);
				while (approverRS.next()) {
					approvermail = approverRS.getString("EMAIL_ID");

				}
				String checkESSApproverList = "select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"
						+ user.getEmployeeNo()
						+ "' and essType='Leave' and Priority=1 and ess.ApproverId=emp.PERNR";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					approvermail = rsCheckInEss.getString("EMAIL_ID");
				}

				try {
					int year = Calendar.getInstance().get(Calendar.YEAR);
					String getleaveBalence = "select t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
							+ user.getEmployeeNo()
							+ "' and t.lv_calyear='"
							+ year + "' and t.lv_typeid=m.lv_typeid";
					ResultSet rsBalance = ad.selectQuery(getleaveBalence);
					ArrayList balList = new ArrayList();
					while (rsBalance.next()) {
						String lvType = rsBalance.getString("lv_type");
						if (lvType.equalsIgnoreCase("CASUAL")) {
							casualOpeningBal = rsBalance.getFloat("lv_opbal");
							casualAvailedBal = rsBalance.getFloat("lv_availed");
							casualClosingBal = rsBalance.getFloat("lv_clbal");
							casualAwatingBal = rsBalance.getFloat("lv_awtBal");
						}
						if (lvType.equalsIgnoreCase("SICK")) {
							sickOpeningBal = rsBalance.getFloat("lv_opbal");
							sickAvailedBal = rsBalance.getFloat("lv_availed");
							sickClosingBal = rsBalance.getFloat("lv_clbal");
							sickAwatingBal = rsBalance.getFloat("lv_awtBal");
						}
						if (lvType.equalsIgnoreCase("PRIVILEGE")) {
							previlegeOpeningBal = rsBalance
									.getFloat("lv_opbal");
							previlegeAvailedBal = rsBalance
									.getFloat("lv_availed");
							previlegeClosingBal = rsBalance
									.getFloat("lv_clbal");
							previlegeAwatingBal = rsBalance
									.getFloat("lv_awtBal");
						}
						if (lvType.equalsIgnoreCase("MATERNITY")) {
							maternityOpeningBal = rsBalance
									.getFloat("lv_opbal");
							maternityAvailedBal = rsBalance
									.getFloat("lv_availed");
							maternityClosingBal = rsBalance
									.getFloat("lv_clbal");
							maternityAwatingBal = rsBalance
									.getFloat("lv_awtBal");
						}
						LeaveForm form2 = new LeaveForm();
						form2.setLeaveType(rsBalance.getString("lv_type"));

						form2.setOpeningBalence(rsBalance.getInt("lv_opbal"));
						form2.setAvalableBalence(rsBalance
								.getFloat("lv_availed"));
						form2.setClosingBalence(rsBalance.getFloat("lv_clbal"));
						form2.setAwaitingBalence(rsBalance
								.getFloat("lv_awtBal"));
						balList.add(form2);
					}
					request.setAttribute("LeaveBalenceList", balList);
					
					
					//prev year availed leavs
					
					String getleave="select t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"+user.getEmployeeNo()+"' and t.lv_calyear='"+(year-1)+"' and t.lv_typeid=m.lv_typeid";
					ResultSet rsBalanc=ad.selectQuery(getleave);
				
					while(rsBalanc.next())
					{
						String lvType=rsBalanc.getString("lv_type");
						if(lvType.equalsIgnoreCase("CASUAL"))
						{
					
							prevcasualAvailedBal=rsBalanc.getFloat("lv_availed");
							
						}
						if(lvType.equalsIgnoreCase("SICK"))
						{
							
							prevsickAvailedBal=rsBalanc.getFloat("lv_availed");
							
						}
						if(lvType.equalsIgnoreCase("PRIVILEGE"))
						{
							
							prevprevilegeAvailedBal=rsBalanc.getFloat("lv_availed");
						
						}
						
						
					}
				

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (requestFrom.equalsIgnoreCase("On Duty")) {
				String ondutydetails = "select onduty.onDuty_Type,onduty.location,CONVERT(varchar(11),onduty.[start_date],103) as start_date,"
						+ "onduty.startTime,CONVERT(varchar(11),onduty.[end_date],103) as end_date,onduty.endTime,onduty.reason,CONVERT(varchar(11),onduty.[submit_date],103) as submit_date,onduty.Approver_id,emp.EMAIL_ID "
						+ "from OnDuty_details as onduty,emp_official_info as emp where onduty.user_id='"
						+ user.getEmployeeNo()
						+ "' and emp.PERNR=onduty.Approver_id and "
						+ "user_id="
						+ user.getEmployeeNo()
						+ " and request_no='" + Req_Id + "'";
				ResultSet ldRS = ad.selectQuery(ondutydetails);
				while (ldRS.next()) {
					ondutyType = ldRS.getString("onDuty_Type");
					plant = ldRS.getString("location");
					startDate = ldRS.getString("start_date");
					fromTime = ldRS.getString("startTime");
					endDate = ldRS.getString("end_date");
					toTime = ldRS.getString("endTime");
					reason = ldRS.getString("reason");
					submitDate = ldRS.getString("submit_date");
					pApprover = ldRS.getString("Approver_id");
					approvermail = ldRS.getString("EMAIL_ID");
				}

			} else if (requestFrom.equalsIgnoreCase("Permission")) {
				tName = "Permission_details";
				String feeddetails = "select p.swipe_type,p.type,p.request_no,CONVERT(varchar(11),p.[date],103) as date,p.startTime,p.endTime,p.reason,p.Approver_id,emp.EMAIL_ID from "
						+ "Permission_details p,emp_official_info  as emp where p.user_id='"
						+ user.getEmployeeNo()
						+ "' and p.request_no='"
						+ Req_Id + "' and  " + "p.Approver_id=emp.PERNR";
				ResultSet ldRS = ad.selectQuery(feeddetails);
				while (ldRS.next()) {
					perDate = ldRS.getString("date");
					perStartTime = ldRS.getString("startTime");
					perEndTime = ldRS.getString("endTime");
					perReason = ldRS.getString("reason");
					pApprover = ldRS.getString("Approver_id");
					approvermail = ldRS.getString("EMAIL_ID");
					type = ldRS.getString("type");
					swipetype = ldRS.getString("swipe_type");
				}

			}

			else if (requestFrom.equalsIgnoreCase("Feedback")) {
				tName = "feedBack_details";
				String feeddetails = "select * from " + tName
						+ " where user_id=" + userId + " and Req_Id='" + Req_Id
						+ "'";
				ResultSet ldRS = ad.selectQuery(feeddetails);
				while (ldRS.next()) {
					feedSub = ldRS.getString("subject");
					feedComment = ldRS.getString("comments");
					feedFrom = ldRS.getString("user_name") + "<"
							+ ldRS.getString("email_id") + ">";
				}

			} else if (requestFrom.equalsIgnoreCase("Customer Master")) {String getCustDet="select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,NAME,CITY,v.Customer_Type,emp.EMP_FULLNAME"
					+ " ,landx,fax_No,address1,address2,address3,address4,pinCode,landLine_No,mobile_no,v.email_Id,bezei,account_group_name,cutomer_code from "
					+ "  " +
					" CUSTOMER_MASTER_M as v"
					+ " left outer join Country on Country.land1=v.COUNTRY_ID "
					+ " left outer join state on state.id=v.state "
					+ " left outer join account_group_m on account_group_m.MATERIAL_TYPE_ID=v.Account_group_id "
					+ ",emp_official_info as emp where  REQUEST_NO='"+Req_Id+"'  and emp.PERNR=v.CREATED_BY";
			ResultSet rsCust = ad.selectQuery(getCustDet);
			while(rsCust.next()){
				submitDate = rsCust.getString("REQUEST_DATE");
				customerName=rsCust.getString("NAME");
				customerCity=rsCust.getString("CITY");
				customerType=rsCust.getString("Customer_Type");
				requesterName=rsCust.getString("EMP_FULLNAME");

				address1=rsCust.getString("address1");
				address2=rsCust.getString("address2");
				address3=rsCust.getString("address3");
				address4=rsCust.getString("address4");
				pinCode=rsCust.getString("pinCode");
				landx=rsCust.getString("landx");
				bezei=rsCust.getString("bezei");
				landLine_No=rsCust.getString("landLine_No");
				mobile_no=rsCust.getString("mobile_no");
				faxNo=rsCust.getString("fax_No");
				email_Id=rsCust.getString("email_Id");
				account_group_name=rsCust.getString("account_group_name");
				customer_code=rsCust.getString("cutomer_code");



			}

			int checkApprover=0;	
			String getMatApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from " +
			"Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='Export' " +
			"AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";	
			ResultSet rsApp=ad.selectQuery(getMatApprovers);
			while(rsApp.next()){
				checkApprover=1;
				approver1=rsApp.getString("Approver_Id");
				parallelApp1=rsApp.getString("Parllel_Approver_1");
				parallelApp2=rsApp.getString("Parllel_Approver_2");
			}
			if(checkApprover==0){
				getMatApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from " +
				"Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='Domestic' " +
				"AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";	
				 rsApp=ad.selectQuery(getMatApprovers);
				while(rsApp.next()){
					approver1=rsApp.getString("Approver_Id");
					parallelApp1=rsApp.getString("Parllel_Approver_1");
					parallelApp2=rsApp.getString("Parllel_Approver_2");
				}
			}} else if (requestFrom.equalsIgnoreCase("Vendor Master")) {String getVendorDet = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,NAME,CITY,v.Type_Of_Vendor,emp.EMP_FULLNAME "
					+ " ,landx,fax_No,v.title,address1,address2,address3,address4,pinCode,landLine_No,mobile_no,v.email_Id,bezei,account_group_name from "
					+ " vendor_master_m as v"
					+ " left outer join Country on Country.land1=v.COUNTRY_ID "
					+ " left outer join state on state.id=v.state "
					+ " left outer join account_group_m on account_group_m.Account_group_id=v.Account_group_id "
					+ " ,emp_official_info as emp where  REQUEST_NO='"
					+ Req_Id + "'  and emp.PERNR=v.CREATED_BY";
			ResultSet rsVendor = ad.selectQuery(getVendorDet);
			while (rsVendor.next()) {
				submitDate = rsVendor.getString("REQUEST_DATE");
				vendorName = rsVendor.getString("NAME");
				vendorCity = rsVendor.getString("CITY");
				vendorType = rsVendor.getString("Type_Of_Vendor");
				title = rsVendor.getString("title");
				address1=rsVendor.getString("address1");
				address2=rsVendor.getString("address2");
				address3=rsVendor.getString("address3");
				address4=rsVendor.getString("address4");
				pinCode=rsVendor.getString("pinCode");
				landx=rsVendor.getString("landx");
				bezei=rsVendor.getString("bezei");
				landLine_No=rsVendor.getString("landLine_No");
				mobile_no=rsVendor.getString("mobile_no");
				faxNo=rsVendor.getString("fax_No");
				email_Id=rsVendor.getString("email_Id");
				account_group_name=rsVendor.getString("account_group_name");







				if (vendorType.equalsIgnoreCase("I"))
					vendorType = "IMPORTER";
				if (vendorType.equalsIgnoreCase("M"))
					vendorType = "MANUFACTURER";
				if (vendorType.equalsIgnoreCase("D"))
					vendorType = "DEALER";
				if (vendorType.equalsIgnoreCase("FD"))
					vendorType = "FIRST STAGE DEALER OF INDIGENOUS";
				if (vendorType.equalsIgnoreCase("SD"))
					vendorType = "SECOND STAGE DEALER OF INDIGENOUS";
				if (vendorType.equalsIgnoreCase("MD"))
					vendorType = "DEPOT MANUFACTURER";


				requesterName = rsVendor.getString("EMP_FULLNAME");
			}

			int checkApprover = 0;
			String getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
					+ "Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='Import' "
					+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
			ResultSet rsApp = ad.selectQuery(getMatApprovers);
			while (rsApp.next()) {
				checkApprover = 1;
				approver1 = rsApp.getString("Approver_Id");
				parallelApp1 = rsApp.getString("Parllel_Approver_1");
				parallelApp2 = rsApp.getString("Parllel_Approver_2");
			}
			if (checkApprover == 0) {
				getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
						+ "Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='Local' "
						+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
				rsApp = ad.selectQuery(getMatApprovers);
				while (rsApp.next()) {
					approver1 = rsApp.getString("Approver_Id");
					parallelApp1 = rsApp.getString("Parllel_Approver_1");
					parallelApp2 = rsApp.getString("Parllel_Approver_2");
				}
			}
} else if (requestFrom.equalsIgnoreCase("Material Code Request")) {tName = "material_code_request";

			String newmatcode = "select REQUEST_NO,CONVERT(varchar(10),REQUEST_DATE,103) as REQUEST_DATE,loc.LOCATION_CODE,Type,"
					+ "MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.MATERIAL_GROUP_ID,mType.M_DESC,purchase_group_desc ,mat.storage_location_id,Storage_location_Name,UNIT_MESUREMENT.ltxt "
					+ " ,material_group.ltxt as matgroup"
					+ " , material_short_name, Material_long_name,manufactured_at,brand_desc,div_desc,detailed_Justification,detailed_Specification from material_code_request as mat "
					+ " left outer join storage_location on storage_location.storage_location_id=mat.storage_location_id and storage_location.mat_type=mat.type "
					+ " left outer join UNIT_MESUREMENT on UNIT_MESUREMENT.Unit_of_meas_id=mat.unit_of_meas_id"
					+ " left outer join material_group on material_group.material_group_id=mat.MATERIAL_GROUP_ID"
					+ " left outer join purchase_group on mat.purchase_group_id =purchase_group.purchase_group_id "
					+ " left outer join brand on mat.brand_id =brand.brand_code "
					+ " left outer join division on mat.DIVISION_ID =division.div_code , "
					+ "Location as loc,emp_official_info as emp,MATERIAL_TYPE mType where "
					+ "REQUEST_NO='"
					+ Req_Id
					+ "' and loc.LOCID=mat.LOCATION_ID and emp.PERNR=mat.CREATED_BY and mType.MATERIAL_GROUP_ID=mat.Type ";


			ResultSet ldRS = ad.selectQuery(newmatcode);
			while (ldRS.next()) {
				submitDate = ldRS.getString("REQUEST_DATE");
				location = ldRS.getString("LOCATION_CODE");
				materialType = ldRS.getString("Type") + "-"
						+ ldRS.getString("M_DESC");
				reqMatType = ldRS.getString("Type");
				matType = ldRS.getString("Type");
				matsName = ldRS.getString("MATERIAL_SHORT_NAME");
				requesterName = ldRS.getString("EMP_FULLNAME");
				matGroup = ldRS.getString("matgroup");
				purchase_group_desc = ldRS.getString("purchase_group_desc");
				Storage_location_Name=ldRS.getString("Storage_location_Name");
				ltxt=ldRS.getString("ltxt");
				Shortname=ldRS.getString("material_short_name");
				longname=ldRS.getString("Material_long_name");
				manufactured_at=ldRS.getString("manufactured_at");
				brand_desc=ldRS.getString("brand_desc");
				div_desc=ldRS.getString("div_desc");
				detailed_Justification=ldRS.getString("detailed_Justification");
				detailed_Specification=ldRS.getString("detailed_Specification");



			}
			if (matType.equals("FG") || matType.equals("HAWA")) {
				String matDetails = "select loc.LOCATION_CODE,mat.Type,mat.CREATED_BY,mat.DOMESTIC_OR_EXPORTS from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"
						+ Req_Id + "' and mat.LOCATION_ID=loc.LOCID";
				ResultSet rsDetails = ad.selectQuery(matDetails);
				try {
					while (rsDetails.next()) {
						matGroup = rsDetails
								.getString("DOMESTIC_OR_EXPORTS");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (matGroup.equals("D"))
					matGroup = "Domestic";
				if (matGroup.equals("E"))
					matGroup = "Export";
				if (matGroup.equals("V"))
					matGroup = "V";
			}
			int checkApprover = 0;
			String getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
					+ "Material_Approvers as app,emp_official_info AS emp where app.Location='"
					+ location
					+ "' and app.Material_Type='"
					+ reqMatType
					+ "' and app.Material_Group='"
					+ matGroup
					+ "' "
					+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
			ResultSet rsApp = ad.selectQuery(getMatApprovers);
			while (rsApp.next()) {
				checkApprover = 1;
				approver1 = rsApp.getString("Approver_Id");
				parallelApp1 = rsApp.getString("Parllel_Approver_1");
				parallelApp2 = rsApp.getString("Parllel_Approver_2");
			}
			if (checkApprover == 0) {
				getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
						+ "Material_Approvers as app,emp_official_info AS emp where app.Location='"
						+ location
						+ "' and app.Material_Type='"
						+ reqMatType
						+ "' and app.Material_Group='' "
						+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
				rsApp = ad.selectQuery(getMatApprovers);
				while (rsApp.next()) {
					approver1 = rsApp.getString("Approver_Id");
					parallelApp1 = rsApp.getString("Parllel_Approver_1");
					parallelApp2 = rsApp.getString("Parllel_Approver_2");
				}
			}

} else if (requestFrom.contains("Service Master")) {tName = "SERVICE_MASTER";

String getdetails = "select emp.EMP_FULLNAME,s.request_date,s.machine_name,s.service_catagory,s.service_description,s.pending_approver,"
		+ "s.purpose,loc.LOCATION_CODE ,UNIT_MESUREMENT.ltxt,purchase_group.purchase_group_desc, service_group.STXT,VALUATION_CLASS.valuation_desc"
		+ " ,service_Description,detailed_desc,machine_name,app_value,Justification "
		+ " from SERVICE_MASTER s "
		+ " left outer join UNIT_MESUREMENT on UNIT_MESUREMENT.Unit_of_meas_id=s.uom"
		+ " left outer join purchase_group on s.purchase_group =purchase_group.purchase_group_id "
		+ " left outer join service_group on s.service_group =service_group.group_id "
		+ " left outer join VALUATION_CLASS on s.VALUATION_CLASS =VALUATION_CLASS.valuation_id "
		+ ",Location loc,emp_official_info as emp where  request_no='"
		+ Req_Id + "' and " + "loc.LOCID=s.plant_code and emp.PERNR=s.CREATED_BY";
ResultSet detailRS = ad.selectQuery(getdetails);
while (detailRS.next()) {
	submitDate = detailRS.getString("request_date");
	mcname = detailRS.getString("machine_name");
	serCat = detailRS.getString("service_catagory");
	serDes = detailRS.getString("service_description");
	pApprover = detailRS.getString("pending_approver");
	serPur = detailRS.getString("purpose");
	location = detailRS.getString("LOCATION_CODE");
	ltxt= detailRS.getString("ltxt");
	purchase_group_desc=detailRS.getString("purchase_group_desc");
	stxt=detailRS.getString("stxt");
	valuation_desc=detailRS.getString("valuation_desc");
	service_Description=detailRS.getString("service_Description");
	detailed_desc=detailRS.getString("detailed_desc");
	machine_name=detailRS.getString("machine_name");
	app_value=detailRS.getString("app_value");
	detailed_Justification=detailRS.getString("Justification");

	requesterName = detailRS.getString("EMP_FULLNAME");



}

int checkApprover = 0;
String getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
		+ "Material_Approvers as app,emp_official_info AS emp where app.Location='"
		+ location
		+ "' and app.Material_Type='Service Master' and app.Material_Group='' "
		+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
ResultSet rsApp = ad.selectQuery(getMatApprovers);
while (rsApp.next()) {
	checkApprover = 1;
	approver1 = rsApp.getString("Approver_Id");
	parallelApp1 = rsApp.getString("Parllel_Approver_1");
	parallelApp2 = rsApp.getString("Parllel_Approver_2");
}

}
			if (requestFrom.contains("Code Extension")) {

				String getdetails = "select REQUEST_NO,REQUEST_DATE,CREATED_BY,TYPE,MATERIAL_CODE_1,PLANT_1 fromPlant,EXTENDED_TO_PLANT_1 toPlant,"
						+ "STORAGE_LOCATION_ID_1 fromStorage,EXTENDED_STORAGE_LOCATION_1 toStorage,PLANT_2 fromPlant1,MATERIAL_CODE_2,FROM_STORAGE_LOCATION,"
						+ "TO_STORAGE_LOCATION from code_extenstion_request where REQUEST_NO='"
						+ Req_Id + "'";
				ResultSet rsDetails = ad.selectQuery(getdetails);
				while (rsDetails.next()) {
					submitDate = EMicroUtils.display(rsDetails
							.getDate("REQUEST_DATE"));
					reqCodeType = rsDetails.getString("TYPE");
					if (reqCodeType.equals("Plant")) {
						sapNo = rsDetails.getString("MATERIAL_CODE_1");
						fromPlant = rsDetails.getString("fromPlant");
						toPlant = rsDetails.getString("toPlant");
						fromStorage = rsDetails.getString("fromStorage");
						toStorage = rsDetails.getString("toStorage");
						location = rsDetails.getString("toPlant");
					} else {
						sapNo = rsDetails.getString("MATERIAL_CODE_2");
						fromPlant = rsDetails.getString("fromPlant1");
						fromStorage = rsDetails
								.getString("FROM_STORAGE_LOCATION");
						toStorage = rsDetails.getString("TO_STORAGE_LOCATION");
						location = rsDetails.getString("fromPlant1");
					}

				}

				String getMatApprovers = "select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from "
						+ "Material_Approvers as app,emp_official_info AS emp where app.Location='"
						+ location
						+ "' and app.Material_Type='Code Extention' and app.Material_Group='' "
						+ "AND app.Approver_Id=emp.PERNR   AND  app.Priority=1";
				ResultSet rsApp = ad.selectQuery(getMatApprovers);
				while (rsApp.next()) {
					approver1 = rsApp.getString("Approver_Id");
					parallelApp1 = rsApp.getString("Parllel_Approver_1");
					parallelApp2 = rsApp.getString("Parllel_Approver_2");
				}
			}

			String approver = "";

			String getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
					+ approver1 + "'";
			ResultSet approverRS = ad.selectQuery(getAppEmailID);
			while (approverRS.next()) {
				approvermail = approverRS.getString(1);
			}
			mailForm.setToAddress(approvermail);
			System.out.println("m -> " + approvermail);
			mailForm.setccAddress("");
			mailForm.setbccAddress("");

			// constructing mail description for each request
			String desc = "Requested By : " + fullName + "</br>";
			desc = desc + "Requested Date : " + submitDate + "</br>";
			if (requestFrom.equalsIgnoreCase("Recruitment Request")) {
				desc = desc + "Required Employee : " + reqEmp + "</br>";
				desc = desc + "Required Qualification : " + empQual + "</br>";
				desc = desc + "empExp : " + empExp + "</br>";
			} else if (requestFrom.equalsIgnoreCase("Add Man Power")) {
				/*
				 * desc = desc +
				 * "Dpartment : "+hrMPMForm.getDepartment()+"</br>"; desc = desc
				 * + "Required Employee : "+hrMPMForm.getReqEmp()+"</br>";
				 */
			}
			if (requestFrom.equalsIgnoreCase("Material Code Request")) {
				mailForm.setSubject("Material Code Request From "
						+ requesterName);
				desc = desc + "";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";
				desc = desc
						+ "<html><body>"
						+ "<table><tr ><td rowspan=2><img src="
						+ "https://portal.microlabs.co.in/images/logo.png" 								
						+ " height=60 width=50  /></br></td>"
						+ " <td colspan=3><b><center> MICRO LABS LIMITED</center></b></td></tr>"
						+ " <tr><td colspan=3><b><center> eMicro Portal Notification</center></b></td></tr>"
						+ "<tr><th colspan=4><center>Material Code Request Details</center></th></tr>";
				if(matType.equalsIgnoreCase("RM"))
				{	
				desc = desc+""
						+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
						+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
						+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
						+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
						+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
						+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
						+ "<tr><th align=left>Mat.Group</th><td>"+matGroup+"</td><th align=left>UOM</th><td>"+ltxt+"</td></tr> "
						+ "<tr> <th align=left>Purchasing Group</th><td colspan=3>"+purchase_group_desc+"</td> </tr>"

						+ "</table><br><br>";
				}
				if(matType.equalsIgnoreCase("LC"))
				{	
				desc = desc+""
						+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
						+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
						+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
						+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
						+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
						+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
						+ "<tr><th align=left>Mat.Group</th><td>"+matGroup+"</td><th align=left>UOM</th><td>"+ltxt+"</td></tr> "
						+ "<tr> <th align=left>Purchasing Group</th><td colspan=3>"+purchase_group_desc+"</td> </tr>"
						+ "<tr> <th align=left>Justification</th><td colspan=3>"+detailed_Justification+"</td> </tr>"
						+ "<tr> <th align=left>Specification</th><td colspan=3>"+detailed_Specification+"</td> </tr>"
						+ "</table><br><br>";
				}

				if(matType.equalsIgnoreCase("BULK"))
				{	
				desc = desc+""
						+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
						+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
						+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
						+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
						+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
						+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
						+ "<tr><th align=left>Mat.Group</th><td>"+matGroup+"</td><th align=left>UOM</th><td>"+ltxt+"</td></tr> "
						+ "</table><br><br>";
				}

				if(matType.equalsIgnoreCase("OSE"))
				{	
				desc = desc+""
						+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
						+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
						+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
						+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
						+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
						+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
						+ "<tr><th align=left>Mat.Group</th><td>"+matGroup+"</td><th align=left>UOM</th><td>"+ltxt+"</td></tr> "
						+ "<tr> <th align=left>Purchasing Group</th><td colspan=3>"+purchase_group_desc+"</td> </tr>"
						+ "<tr> <th align=left>Justification</th><td colspan=3>"+detailed_Justification+"</td> </tr>"
						+ "<tr> <th align=left>Specification</th><td colspan=3>"+detailed_Specification+"</td> </tr>"
						+ "</table><br><br>";
				}




				if(matType.equalsIgnoreCase("PM"))
				{

					desc = desc+""
							+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
							+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
							+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
							+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
							+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
							+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
							+ "<tr><th align=left>Mat.Group</th><td colspan=3>"+matGroup+"</td></tr> "
							+ "<tr> <th align=left>Purchasing Group</th><td colspan=3>"+purchase_group_desc+"</td> </tr>"
			
							+ "</table><br><br>";


				}

				if(matType.equalsIgnoreCase("FG"))
				{

					desc = desc+""
							+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
							+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
							+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
							+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
							+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
							+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
							+ "<tr><th align=left>Mat.Group</th><td>"+matGroup+"</td><th align=left>Manufacture At</th><td>"+manufactured_at+"</td></tr> "
							+ "<tr><th align=left >UOM</th><td colspan=3>"+ltxt+"</td></tr>"
							+ "</table><br><br>";


				}

				if(matType.equalsIgnoreCase("PPC"))
				{

					desc = desc+""
						+ "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
						+ "<tr><th align=left>Location</th><td>"+location+"</td><th align=left>Storage Location</th><td>"+Storage_location_Name+"</td></tr> "
						+"<tr><th align=left>Requested By </th><td colspan=3>"+user.getFullName()+"</td> </tr>"
						+ "<tr> <th align=left>Short Name</th><td colspan=3>"+Shortname+"</td> </tr>"
						+ "<tr> <th align=left>Long Name</th><td colspan=3>"+longname+"</td> </tr>"
						+"<tr><th align=left>Material Type</th><td colspan=3>"+materialType+"</td> </tr>"
						+ "<tr><th align=left>Mat.Group</th><td>"+matGroup+"</td><th align=left>UOM</th><td>"+ltxt+"</td></tr> "
						+ "<tr><th align=left>Brand</th><td>"+brand_desc+"</td><th align=left>Division</th><td>"+div_desc+"</td></tr> "
						+ "<tr> <th align=left>Purchasing Group</th><td colspan=3>"+purchase_group_desc+"</td> </tr>"
										+ "<tr> <th align=left>Justification</th><td colspan=3>"+detailed_Justification+"</td> </tr>"
							+ "<tr> <th align=left>Specification</th><td colspan=3>"+detailed_Specification+"</td> </tr>"

						+ "</table><br><br>";
			   }



				desc = desc
						+ "<i> <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information is being provided by  <a href=https://portal.microlabs.co.in>EMicro Portal</a> and it is an auto generated mail notification, Pl. do not reply.</span></i> ";
				desc = desc + "</body></html>";}
			if (requestFrom.equalsIgnoreCase("Customer Master")) {mailForm.setSubject("Customer Code Request From "+requesterName);
			desc = desc + "";
			desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
					+ "TABLE {border-collapse:collapse;border:1px solid black;}"
					+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
					+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
					+ "</STYLE>";
			desc = desc
					+ " <html><body>"
					+ " <table><tr ><td rowspan=2><img src="
					+ " https://portal.microlabs.co.in/images/logo.png" 
					+ " height=60 width=50  /></br></td>"
					+ " <td colspan=3><b><center> MICRO LABS LIMITED</center></b></td></tr>"
					+ " <tr><td colspan=3><b><center> eMicro Portal Notification</center></b></td></tr>"
					+ "<tr><th colspan=4><center>Vendor Code Request Details</center></th></tr>";

			desc = desc+""
				    + "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
				    + "<tr><th align=left>Req. by</th><td>" + user.getFullName() + "</td> <th align=left>Account Group</th><td>"+account_group_name+"</td> </tr>"
				    + "<tr><th align=left>Customer Type Type</th><td>" + customerType + "</td> <th align=left>Employee Code</th><td>"+customer_code+"</td> </tr> "
					+ "<tr><th align=left colspan=3>Name</th><td>" + customerName + "</td></tr>  "
					+ "<tr><th align=left>Address1</th><td>" + address1 + "</td><th align=left>Address2</th><td>" + address2 + "</td></tr>  "
					+ "<tr><th align=left>Address3</th><td>" + address3 + "</td><th align=left>Address4</th><td>" + address4 + "</td></tr>  "
					+ "<tr><th align=left>City</th><td>" + customerCity + "</td><th align=left>Pin Code</th><td>" + pinCode + "</td></tr>  "
					+ "<tr><th align=left>Country</th><td>" + landx + "</td><th align=left>State</th><td>" + bezei + "</td></tr> "
					+ "<tr><th align=left>Landline No</th><td>" + landLine_No + "</td><th align=left>Mobile No</th><td>" + mobile_no + "</td></tr> "
					+ "<tr><th align=left>Fax No</th><td>" + faxNo + "</td><th align=left>e-Mail</th><td>" + email_Id + "</td></tr> "
					+ "</table><br><br>";
			desc = desc
					+ "<i> <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information is being provided by  <a href=https://portal.microlabs.co.in>EMicro Portal</a> and it is an auto generated mail notification, Pl. do not reply.</span></i> ";
			desc = desc + "</body></html>";}
			if (requestFrom.equalsIgnoreCase("Vendor Master")) {mailForm.setSubject("Vendor Code Request From " + requesterName);
			desc = desc + "";
			desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
					+ "TABLE {border-collapse:collapse;border:1px solid black;}"
					+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
					+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
					+ "</STYLE>";
			desc = desc
					+ " <html><body>"
					+ " <table><tr ><td rowspan=2><img src="
					+ " https://portal.microlabs.co.in/images/logo.png" 
					+ " height=60 width=50  /></br></td>"
					+ " <td colspan=3><b><center> MICRO LABS LIMITED</center></b></td></tr>"
					+ " <tr><td colspan=3><b><center> eMicro Portal Notification</center></b></td></tr>"
					+ "<tr><th colspan=4><center>Vendor Code Request Details</center></th></tr>";

			desc = desc+""
				    + "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
				    + "<tr><th align=left>Req. by</th><td>" + user.getFullName() + "</td> <th align=left>Account Group</th><td>"+account_group_name+"</td> </tr>"
				    + "<tr><th align=left>Vendor Type</th><td  colspan=3>" + vendorType + "</td></tr>  "
					+ "<tr><th align=left>Tittle</th><td>" + title + "</td><th align=left>Name</th><td>" + vendorName + "</td></tr>  "
					+ "<tr><th align=left>Address1</th><td>" + address1 + "</td><th align=left>Address2</th><td>" + address2 + "</td></tr>  "
					+ "<tr><th align=left>Address3</th><td>" + address3 + "</td><th align=left>Address4</th><td>" + address4 + "</td></tr>  "
					+ "<tr><th align=left>City</th><td>" + vendorCity + "</td><th align=left>Pin Code</th><td>" + pinCode + "</td></tr>  "
					+ "<tr><th align=left>Country</th><td>" + landx + "</td><th align=left>State</th><td>" + bezei + "</td></tr> "
					+ "<tr><th align=left>Landline No</th><td>" + landLine_No + "</td><th align=left>Mobile No</th><td>" + mobile_no + "</td></tr> "
					+ "<tr><th align=left>Fax No</th><td>" + faxNo + "</td><th align=left>e-Mail</th><td>" + email_Id + "</td></tr> "
					+ "</table><br><br>";
			desc = desc
					+ "<i> <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information is being provided by  <a href=https://portal.microlabs.co.in>EMicro Portal</a> and it is an auto generated mail notification, Pl. do not reply.</span></i> ";
			desc = desc + "</body></html>";}
			if (requestFrom.equalsIgnoreCase("Service Master")) {
				mailForm.setSubject("Service Code Request From "+ requesterName);
			desc = desc + "";
			desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
					+ "TABLE {border-collapse:collapse;border:1px solid black;}"
					+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
					+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
					+ "</STYLE>";
			desc = desc
					+ " <html><body>"
					+ " <table><tr ><td rowspan=2><img src="
					+ " https://portal.microlabs.co.in/images/logo.png"  
					+ " height=60 width=50  /></br></td>"
					+ " <td colspan=3><b><center> MICRO LABS LIMITED</center></b></td></tr>"
					+ " <tr><td colspan=3><b><center> eMicro Portal Notification</center></b></td></tr>"
					+ "<tr><th colspan=4><center>Service Code Request Details</center></th></tr>";

			desc = desc+""
				    + "<tr><th align=left>Req. No</th><td>" + Req_Id + "</td><th align=left>Req.Date</th><td>" + submitDate + "</td></tr>  "
				    + "<tr><th align=left>Req. by</th><td>" + user.getFullName() + "</td> <th align=left>Service Description</th><td>"+serDes+"</td> </tr>"
					+ "<tr><th align=left>Detailed Service Description</th><td  colspan=3>" + detailed_desc + "</td></tr>"
					+ "<tr><th align=left>UOM</th><td>" + ltxt + "</td> <th align=left>Purchase Group</th><td>"+purchase_group_desc+"</td> </tr>"
					+ "<tr><th align=left>Service Category</th><td>" +serCat+ "</td> <th align=left>Service Group</th><td>"+stxt+"</td> </tr>"
					+ "<tr><th align=left>Equipment/Machine Name</th><td>" + machine_name + "</td> <th align=left>Approximate Value </th><td>"+app_value+"</td> </tr>"
					+ "<tr><th align=left>Justification</th><td>" + detailed_Justification + "</td> <th align=left>Valuation Class </th><td>"+valuation_desc+"</td> </tr>";
			desc = desc
					+ "</table><br><br>";


			desc = desc
					+ "<i> <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information is being provided by  <a href=https://portal.microlabs.co.in>EMicro Portal</a> and it is an auto generated mail notification, Pl. do not reply.</span></i> ";
			desc = desc + "</body></html>";}
			if (requestFrom.equalsIgnoreCase("Code Extension")) {
				mailForm.setSubject("Code Extention Request From "
						+ user.getFullName());
				desc = desc + "";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
						+ "TABLE {border-collapse:collapse;border:1px solid black;}"
						+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
						+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
						+ "</STYLE>";

				if (reqCodeType.equals("Plant")) {
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=8 ><center>Code Extention Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Type</th><th align=left>Material Code</th><th>From Plant</th><th>Storage Location</th><th>To Plant</th><th>Storage Location</th></tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + reqCodeType
							+ "</td><td>" + sapNo + "</td><td>" + fromPlant
							+ "</td><td>" + fromStorage + "</td><td>" + toPlant
							+ "</td><td>" + toStorage
							+ "</td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
				} else {
					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></br> <table><tr><th colspan=8 ><center>Code Extention Request Details</center></th></tr>";
					desc = desc
							+ "<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Type</th><th align=left>Material Code</th><th>From Plant</th><th>From Storage Location</th><th>To Storage Location</th></tr>";
					desc = desc + "<tr><td>" + Req_Id + "</td><td>"
							+ submitDate + "</td><td>" + reqCodeType
							+ "</td><td>" + sapNo + "</td><td>" + fromPlant
							+ "</td><td>" + fromStorage + "</td><td>"
							+ toStorage + "</td></tr></table><br><br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
				}
			} else if (requestFrom.equalsIgnoreCase("Leave")) {
				String gender = "";
				String pernr = "";
				String name = "";
				String doj = "";
				String dep = "";
				String desg = "";
				String getGender = "select emp.PERNR,emp.EMP_FULLNAME,emp.SEX,emp.DOJ,dep.DPTSTXT,desg.DSGSTXT from emp_official_info emp,DEPARTMENT as dep,DESIGNATION as desg where emp.PERNR='"
						+ user.getEmployeeNo()
						+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
				ResultSet rsGender = ad.selectQuery(getGender);
				while (rsGender.next()) {
					gender = rsGender.getString("SEX");
					pernr = rsGender.getString("PERNR");
					name = rsGender.getString("EMP_FULLNAME");
					doj = rsGender.getString("DOJ");
					dep = rsGender.getString("DPTSTXT");
					desg = rsGender.getString("DSGSTXT");

				}
				String abc[] = doj.split("-");
				doj = abc[2] + "/" + abc[1] + "/" + abc[0];
				
				int year = Calendar.getInstance().get(Calendar.YEAR);

				   ArrayList lastleaves=new ArrayList();
				   int recLv_record=0;
					String getLastLeaveRecord="select id,user_id,leave_type,m.lv_type,CONVERT(CHAR(10), [start_date], 103) as start_date,CONVERT(CHAR(10),[end_date], 103) as end_date," +
					"no_of_days,CONVERT(CHAR(10), [approved_date], 103) as approved_date from leave_details as l,lv_type_m as m where user_id='"+user.getEmployeeNo()+"' and l.leave_type=m.lv_typeid " +
							"and Approvel_Status='Approved' and year(start_date)='"+year+"' order by id desc";
					ResultSet rsRecentrecord=ad.selectQuery(getLastLeaveRecord);
					while(rsRecentrecord.next()){
						LeaveForm a=new LeaveForm();
						recLv_record=1;
						a.setLeaveType(rsRecentrecord.getString("lv_type"));
						a.setStartDate(rsRecentrecord.getString("start_date"));
					    a.setEndDate(rsRecentrecord.getString("end_date"));
						a.setNoOfDays(rsRecentrecord.getString("no_of_days"));
						a.setApprovedDate(rsRecentrecord.getString("approved_date"));
						
						lastleaves.add(a);
					}

				mailForm.setSubject("Leave Request From " + user.getFullName());

				boolean existStatus = false;
				String dataFlowType = "";
				String checkESSApproverList = "select count(*) from ESS_Approvers where employeeNumber='"
						+ user.getEmployeeNo() + "' and essType='Leave'";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					int number = rsCheckInEss.getInt(1);
					if (number == 0) {
						existStatus = false;
					} else {
						existStatus = true;
					}
				}
				if (existStatus == true) {

					String getDataFlowType = "select * from ESS_Approvers where employeeNumber='"
							+ user.getEmployeeNo() + "' and essType='Leave'";
					ResultSet rsDataFlowType = ad.selectQuery(getDataFlowType);
					while (rsDataFlowType.next()) {
						dataFlowType = rsDataFlowType.getString("reqType");
					}
					if (dataFlowType.equalsIgnoreCase("sequential")) {
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ user.getEmployeeNo()
								+ "' and essType='Leave' and reqType='sequential'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");
						}
						desc = desc + "";
						desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
								+ "TABLE {border-collapse:collapse;border:1px solid black;}"
								+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
								+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
								+ "</STYLE>";

						desc = desc
								+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
								+ "https://portal.microlabs.co.in/images/logo.png"
								+ " height=60 width=50  /></center></td></tr> ";
						desc=desc+"<tr><th colspan=5 ><center>Requester Details</center></th></tr>";
						desc=desc+"<tr><th align=left>Emp No.</th><td>"+pernr+"</td><th align=left>Name</th><td colspan=2>"+name+"</td></tr>";
						desc=desc+"<tr><th align=left>Dept</th><td>"+dep+"</td><th align=left>Desg</th><td colspan=2>"+desg+"</td></tr>";			
						desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td  colspan=2>"+Req_Id+"</td></tr>";
						
						desc=desc+"<tr><th colspan=6><center>Leave Details</center></th></tr>";
						desc=desc+"<tr><th align=left colspan=2>&nbsp;Leave Type</th><td>"+lAction.getLeaveType(leaveType)+"</td><th>No.Of Days</th><td colspan=2>"+noOfDays+"</tr>";
						desc=desc+"<tr><th align=left colspan=2>&nbsp;From</th><td>"+startDate+"</td><th align=left>Duration</th><td colspan=2>"+startDur+"</td></tr>";
						desc=desc+"<tr><th align=left colspan=2>&nbsp;To</th><td>"+endDate+"<th align=left>Duration</th><td colspan=2>"+endDur+"</td></tr>";
						desc=desc+"<tr><th align=left colspan=2>&nbsp;Reason Type</th><td colspan=4>"+reasonType+"</td></tr>";
						desc=desc+"<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=4>"+reason+"</td></tr>";							   
						
					    desc=desc+"<tr><th colspan=6>Leave Balances</th></tr>";
					    desc=desc+"<tr><th>Type</th><th>Opening</th><th>Availed</th><th>Closing</th><th>Awaiting For Approval</th><th>Previous Year Availed("+(year-1)+")</th></tr>";
					    desc=desc+"<tr><th align=left>CASUAL</TH><TD align=right>"+casualOpeningBal+"</TD><TD align=right>"+casualAvailedBal+"</TD><TD align=right>"+casualClosingBal+"</TD><TD align=right>"+casualAwatingBal+"</TD><TD align=right>"+prevcasualAvailedBal+"</TD></TR>";
					    desc=desc+"<tr><th align=left>SICK</TH><TD align=right>"+sickOpeningBal+"</TD><TD align=right>"+sickAvailedBal+"</TD><TD align=right>"+sickClosingBal+"</TD><TD align=right>"+sickAwatingBal+"</TD><TD align=right>"+prevsickAvailedBal+"</TD></TR>";
					    if(gender.equalsIgnoreCase("F")){
					    	desc=desc+"<tr><th align=left>MATERNITY</TH><TD align=right>"+maternityOpeningBal+"</TD><TD align=right>"+maternityAvailedBal+"</TD><TD align=right>"+maternityClosingBal+"</TD><TD align=right>"+maternityAwatingBal+"</TD><TD align=right>&nbsp;</TD></TR>";
					    }
					    desc=desc+"<tr><th align=left>PRIVILEGE</TH><TD align=right>"+previlegeOpeningBal+"</TD><TD align=right>"+previlegeAvailedBal+"</TD><TD align=right>"+previlegeClosingBal+"</TD><TD align=right>"+previlegeAwatingBal+"</TD><TD align=right>"+prevprevilegeAvailedBal+"</TD></TR>";
					    desc=desc+"<tr><th align=left>Total</TH><TD align=right>"+(casualOpeningBal+sickOpeningBal+maternityOpeningBal+previlegeOpeningBal)+"</TD><TD align=right>"+(casualAvailedBal+sickAvailedBal+maternityAvailedBal+previlegeAvailedBal)+"</TD>"
					    		+ "<TD align=right>"+(casualClosingBal+sickClosingBal+maternityClosingBal+previlegeClosingBal)+"</TD><TD align=right>"+(casualAwatingBal+sickAwatingBal+maternityAwatingBal+previlegeAwatingBal)+"</TD><TD align=right>"+(prevcasualAvailedBal+prevsickAvailedBal+prevprevilegeAvailedBal)+"</TD></TR>";
					    
					    desc=desc+ "</table>";
						desc = desc + "</br></br></br>";

						desc = desc
								+ "<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
								+ approver1
								+ "&reqId="
								+ Req_Id
								+ "&status=Approve><img src="
								+ "https://portal.microlabs.co.in/images/Approve.png"
								+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
								+ approver1
								+ "&reqId="
								+ Req_Id
								+ "&status=Reject><img src="
								+ "https://portal.microlabs.co.in/images/Reject.png"
								+ " height=20 width=80 title=Reject border=0 /></a></br></br>";

						desc = desc + "</br></br>";
						desc = desc
								+ "<u><b>Last Leave Details :-</b></u></br></br>";
						desc = desc
								+ "<table><tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No Of Days</th><th>Approve Date</th></tr>";
						if (recLv_record == 1) {
					    	Iterator r=lastleaves.iterator();
					    	while(r.hasNext()) {
								
								LeaveForm leave=(LeaveForm)r.next();
								 desc=desc+"<tr><td>"+leave.getLeaveType()+"</td><td>"+leave.getStartDate()+"</td><td>"+leave.getEndDate()+"</td><td>"+leave.getNoOfDays()+"</td><td>"+leave.getApprovedDate()+"</td></tr>";
					    	}
					   
					    	desc=desc+"</table>";
					    } else {
							desc = desc
									+ "<tr><td colspan=5>No Leave Details</tr></table>";
						}
						desc = desc + "</br></br></br></br>";
						desc = desc
								+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
								+ "https://portal.microlabs.co.in/"
								+ ">EMicro Portal</a> .</span> ";
						desc = desc + "</body></html>";
						approverDesc = desc;

					}
					if (dataFlowType.equalsIgnoreCase("parallel")) {
						String toAddress = "";
						approver1 = "";
						String parallelApprover1 = "";
						String parallelApprover2 = "";
						String empIDS = "";
						String empEmail = "";
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ user.getEmployeeNo()
								+ "' and essType='Leave' and reqType='parallel'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");
							parallelApprover1 = rsApproverDsg
									.getString("Parallel_Approver1");
							parallelApprover2 = rsApproverDsg
									.getString("Parallel_Approver2");
						}
						if (!(approver1.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";

							desc = desc
									+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /></center></td></tr> ";
							desc=desc+"<tr><th colspan=5 ><center>Requester Details</center></th></tr>";
							desc=desc+"<tr><th align=left>Emp No.</th><td>"+pernr+"</td><th align=left>Name</th><td colspan=2>"+name+"</td></tr>";
							desc=desc+"<tr><th align=left>Dept</th><td>"+dep+"</td><th align=left>Desg</th><td colspan=2>"+desg+"</td></tr>";			
							desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td  colspan=2>"+Req_Id+"</td></tr>";
							
							desc=desc+"<tr><th colspan=6><center>Leave Details</center></th></tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;Leave Type</th><td>"+lAction.getLeaveType(leaveType)+"</td><th>No.Of Days</th><td colspan=2>"+noOfDays+"</tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;From</th><td>"+startDate+"</td><th align=left>Duration</th><td colspan=2>"+startDur+"</td></tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;To</th><td>"+endDate+"<th align=left>Duration</th><td colspan=2>"+endDur+"</td></tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;Reason Type</th><td colspan=4>"+reasonType+"</td></tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=4>"+reason+"</td></tr>";							   
							
						    desc=desc+"<tr><th colspan=6>Leave Balances</th></tr>";
						    desc=desc+"<tr><th>Type</th><th>Opening</th><th>Availed</th><th>Closing</th><th>Awaiting For Approval</th><th>Previous Year Availed("+(year-1)+")</th></tr>";
						    desc=desc+"<tr><th align=left>CASUAL</TH><TD align=right>"+casualOpeningBal+"</TD><TD align=right>"+casualAvailedBal+"</TD><TD align=right>"+casualClosingBal+"</TD><TD align=right>"+casualAwatingBal+"</TD><TD align=right>"+prevcasualAvailedBal+"</TD></TR>";
						    desc=desc+"<tr><th align=left>SICK</TH><TD align=right>"+sickOpeningBal+"</TD><TD align=right>"+sickAvailedBal+"</TD><TD align=right>"+sickClosingBal+"</TD><TD align=right>"+sickAwatingBal+"</TD><TD align=right>"+prevsickAvailedBal+"</TD></TR>";
						    if(gender.equalsIgnoreCase("F")){
						    	desc=desc+"<tr><th align=left>MATERNITY</TH><TD align=right>"+maternityOpeningBal+"</TD><TD align=right>"+maternityAvailedBal+"</TD><TD align=right>"+maternityClosingBal+"</TD><TD align=right>"+maternityAwatingBal+"</TD><TD align=right>&nbsp;</TD></TR>";
						    }
						    desc=desc+"<tr><th align=left>PRIVILEGE</TH><TD align=right>"+previlegeOpeningBal+"</TD><TD align=right>"+previlegeAvailedBal+"</TD><TD align=right>"+previlegeClosingBal+"</TD><TD align=right>"+previlegeAwatingBal+"</TD><TD align=right>"+prevprevilegeAvailedBal+"</TD></TR>";
						    desc=desc+"<tr><th align=left>Total</TH><TD align=right>"+(casualOpeningBal+sickOpeningBal+maternityOpeningBal+previlegeOpeningBal)+"</TD><TD align=right>"+(casualAvailedBal+sickAvailedBal+maternityAvailedBal+previlegeAvailedBal)+"</TD>"
						    		+ "<TD align=right>"+(casualClosingBal+sickClosingBal+maternityClosingBal+previlegeClosingBal)+"</TD><TD align=right>"+(casualAwatingBal+sickAwatingBal+maternityAwatingBal+previlegeAwatingBal)+"</TD><TD align=right>"+(prevcasualAvailedBal+prevsickAvailedBal+prevprevilegeAvailedBal)+"</TD></TR>";
						    
						    desc=desc+ "</table>";
							desc = desc + "</br></br></br>";

							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br>";

							desc = desc + "</br></br></br></br>";
							desc = desc
									+ "<u><b>Last Leave Details :-</b></u></br></br>";
							desc = desc
									+ "<table><tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No Of Days</th><th>Approve Date</th></tr>";
							if (recLv_record == 1) {
						    	Iterator r=lastleaves.iterator();
						    	while(r.hasNext()) {
									
									LeaveForm leave=(LeaveForm)r.next();
									 desc=desc+"<tr><td>"+leave.getLeaveType()+"</td><td>"+leave.getStartDate()+"</td><td>"+leave.getEndDate()+"</td><td>"+leave.getNoOfDays()+"</td><td>"+leave.getApprovedDate()+"</td></tr>";
						    	}
						   
						    	desc=desc+"</table>";
						    } else {
								desc = desc
										+ "<tr><td colspan=5>No Leave Details</tr></table>";
							}
							desc = desc + "</br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
							approverDesc = desc;
						}
						if (!(parallelApprover1.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";

							desc = desc
									+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /></center></td></tr> ";
							desc=desc+"<tr><th colspan=5 ><center>Requester Details</center></th></tr>";
							desc=desc+"<tr><th align=left>Emp No.</th><td>"+pernr+"</td><th align=left>Name</th><td colspan=2>"+name+"</td></tr>";
							desc=desc+"<tr><th align=left>Dept</th><td>"+dep+"</td><th align=left>Desg</th><td colspan=2>"+desg+"</td></tr>";			
							desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td  colspan=2>"+Req_Id+"</td></tr>";
							
							desc=desc+"<tr><th colspan=6><center>Leave Details</center></th></tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;Leave Type</th><td>"+lAction.getLeaveType(leaveType)+"</td><th>No.Of Days</th><td colspan=2>"+noOfDays+"</tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;From</th><td>"+startDate+"</td><th align=left>Duration</th><td colspan=2>"+startDur+"</td></tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;To</th><td>"+endDate+"<th align=left>Duration</th><td colspan=2>"+endDur+"</td></tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;Reason Type</th><td colspan=4>"+reasonType+"</td></tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=4>"+reason+"</td></tr>";							   
							
						    desc=desc+"<tr><th colspan=6>Leave Balances</th></tr>";
						    desc=desc+"<tr><th>Type</th><th>Opening</th><th>Availed</th><th>Closing</th><th>Awaiting For Approval</th><th>Previous Year Availed("+(year-1)+")</th></tr>";
						    desc=desc+"<tr><th align=left>CASUAL</TH><TD align=right>"+casualOpeningBal+"</TD><TD align=right>"+casualAvailedBal+"</TD><TD align=right>"+casualClosingBal+"</TD><TD align=right>"+casualAwatingBal+"</TD><TD align=right>"+prevcasualAvailedBal+"</TD></TR>";
						    desc=desc+"<tr><th align=left>SICK</TH><TD align=right>"+sickOpeningBal+"</TD><TD align=right>"+sickAvailedBal+"</TD><TD align=right>"+sickClosingBal+"</TD><TD align=right>"+sickAwatingBal+"</TD><TD align=right>"+prevsickAvailedBal+"</TD></TR>";
						    if(gender.equalsIgnoreCase("F")){
						    	desc=desc+"<tr><th align=left>MATERNITY</TH><TD align=right>"+maternityOpeningBal+"</TD><TD align=right>"+maternityAvailedBal+"</TD><TD align=right>"+maternityClosingBal+"</TD><TD align=right>"+maternityAwatingBal+"</TD><TD align=right>&nbsp;</TD></TR>";
						    }
						    desc=desc+"<tr><th align=left>PRIVILEGE</TH><TD align=right>"+previlegeOpeningBal+"</TD><TD align=right>"+previlegeAvailedBal+"</TD><TD align=right>"+previlegeClosingBal+"</TD><TD align=right>"+previlegeAwatingBal+"</TD><TD align=right>"+prevprevilegeAvailedBal+"</TD></TR>";
						    desc=desc+"<tr><th align=left>Total</TH><TD align=right>"+(casualOpeningBal+sickOpeningBal+maternityOpeningBal+previlegeOpeningBal)+"</TD><TD align=right>"+(casualAvailedBal+sickAvailedBal+maternityAvailedBal+previlegeAvailedBal)+"</TD>"
						    		+ "<TD align=right>"+(casualClosingBal+sickClosingBal+maternityClosingBal+previlegeClosingBal)+"</TD><TD align=right>"+(casualAwatingBal+sickAwatingBal+maternityAwatingBal+previlegeAwatingBal)+"</TD><TD align=right>"+(prevcasualAvailedBal+prevsickAvailedBal+prevprevilegeAvailedBal)+"</TD></TR>";
						    
						    desc=desc+ "</table>";
							desc = desc + "</br></br></br>";

							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
									+ parallelApprover1
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
									+ parallelApprover1
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br>";

							desc = desc + "</br></br></br></br>";
							desc = desc
									+ "<u><b>Last Leave Details :-</b></u></br></br>";
							desc = desc
									+ "<table><tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No Of Days</th><th>Approve Date</th></tr>";
							if (recLv_record == 1) {
						    	Iterator r=lastleaves.iterator();
						    	while(r.hasNext()) {
									
									LeaveForm leave=(LeaveForm)r.next();
									 desc=desc+"<tr><td>"+leave.getLeaveType()+"</td><td>"+leave.getStartDate()+"</td><td>"+leave.getEndDate()+"</td><td>"+leave.getNoOfDays()+"</td><td>"+leave.getApprovedDate()+"</td></tr>";
						    	}
						   
						    	desc=desc+"</table>";
						    } else {
								desc = desc
										+ "<tr><td colspan=5>No Leave Details</tr></table>";
							}
							desc = desc + "</br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
							parallelApprover1Desc = desc;
						}
						if (!(parallelApprover2.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";

							desc = desc
									+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /></center></td></tr> ";
							desc=desc+"<tr><th colspan=5 ><center>Requester Details</center></th></tr>";
							desc=desc+"<tr><th align=left>Emp No.</th><td>"+pernr+"</td><th align=left>Name</th><td colspan=2>"+name+"</td></tr>";
							desc=desc+"<tr><th align=left>Dept</th><td>"+dep+"</td><th align=left>Desg</th><td colspan=2>"+desg+"</td></tr>";			
							desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td  colspan=2>"+Req_Id+"</td></tr>";
							
							desc=desc+"<tr><th colspan=6><center>Leave Details</center></th></tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;Leave Type</th><td>"+lAction.getLeaveType(leaveType)+"</td><th>No.Of Days</th><td colspan=2>"+noOfDays+"</tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;From</th><td>"+startDate+"</td><th align=left>Duration</th><td colspan=2>"+startDur+"</td></tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;To</th><td>"+endDate+"<th align=left>Duration</th><td colspan=2>"+endDur+"</td></tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;Reason Type</th><td colspan=4>"+reasonType+"</td></tr>";
							desc=desc+"<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=4>"+reason+"</td></tr>";							   
							
						    desc=desc+"<tr><th colspan=6>Leave Balances</th></tr>";
						    desc=desc+"<tr><th>Type</th><th>Opening</th><th>Availed</th><th>Closing</th><th>Awaiting For Approval</th><th>Previous Year Availed("+(year-1)+")</th></tr>";
						    desc=desc+"<tr><th align=left>CASUAL</TH><TD align=right>"+casualOpeningBal+"</TD><TD align=right>"+casualAvailedBal+"</TD><TD align=right>"+casualClosingBal+"</TD><TD align=right>"+casualAwatingBal+"</TD><TD align=right>"+prevcasualAvailedBal+"</TD></TR>";
						    desc=desc+"<tr><th align=left>SICK</TH><TD align=right>"+sickOpeningBal+"</TD><TD align=right>"+sickAvailedBal+"</TD><TD align=right>"+sickClosingBal+"</TD><TD align=right>"+sickAwatingBal+"</TD><TD align=right>"+prevsickAvailedBal+"</TD></TR>";
						    if(gender.equalsIgnoreCase("F")){
						    	desc=desc+"<tr><th align=left>MATERNITY</TH><TD align=right>"+maternityOpeningBal+"</TD><TD align=right>"+maternityAvailedBal+"</TD><TD align=right>"+maternityClosingBal+"</TD><TD align=right>"+maternityAwatingBal+"</TD><TD align=right>&nbsp;</TD></TR>";
						    }
						    desc=desc+"<tr><th align=left>PRIVILEGE</TH><TD align=right>"+previlegeOpeningBal+"</TD><TD align=right>"+previlegeAvailedBal+"</TD><TD align=right>"+previlegeClosingBal+"</TD><TD align=right>"+previlegeAwatingBal+"</TD><TD align=right>"+prevprevilegeAvailedBal+"</TD></TR>";
						    desc=desc+"<tr><th align=left>Total</TH><TD align=right>"+(casualOpeningBal+sickOpeningBal+maternityOpeningBal+previlegeOpeningBal)+"</TD><TD align=right>"+(casualAvailedBal+sickAvailedBal+maternityAvailedBal+previlegeAvailedBal)+"</TD>"
						    		+ "<TD align=right>"+(casualClosingBal+sickClosingBal+maternityClosingBal+previlegeClosingBal)+"</TD><TD align=right>"+(casualAwatingBal+sickAwatingBal+maternityAwatingBal+previlegeAwatingBal)+"</TD><TD align=right>"+(prevcasualAvailedBal+prevsickAvailedBal+prevprevilegeAvailedBal)+"</TD></TR>";
						    
						    desc=desc+ "</table>";
							desc = desc + "</br></br></br>";

							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
									+ parallelApprover2
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
									+ parallelApprover2
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br>";

							desc = desc + "</br></br></br></br>";
							desc = desc
									+ "<u><b>Last Leave Details :-</b></u></br></br>";
							desc = desc
									+ "<table><tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No Of Days</th><th>Approve Date</th></tr>";
							if (recLv_record == 1) {
						    	Iterator r=lastleaves.iterator();
						    	while(r.hasNext()) {
									
									LeaveForm leave=(LeaveForm)r.next();
									 desc=desc+"<tr><td>"+leave.getLeaveType()+"</td><td>"+leave.getStartDate()+"</td><td>"+leave.getEndDate()+"</td><td>"+leave.getNoOfDays()+"</td><td>"+leave.getApprovedDate()+"</td></tr>";
						    	}
						   
						    	desc=desc+"</table>";
						    } else {
								desc = desc
										+ "<tr><td colspan=5>No Leave Details</tr></table>";
							}
							desc = desc + "</br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
							parallelApprover2Desc = desc;
						}
					}

				}
				if (existStatus == false) {
					desc = desc + "";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";

					desc = desc
							+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /></center></td></tr> ";
					desc=desc+"<tr><th colspan=5 ><center>Requester Details</center></th></tr>";
					desc=desc+"<tr><th align=left>Emp No.</th><td>"+pernr+"</td><th align=left>Name</th><td colspan=2>"+name+"</td></tr>";
					desc=desc+"<tr><th align=left>Dept</th><td>"+dep+"</td><th align=left>Desg</th><td colspan=2>"+desg+"</td></tr>";			
					desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td  colspan=2>"+Req_Id+"</td></tr>";
					
					desc=desc+"<tr><th colspan=6><center>Leave Details</center></th></tr>";
					desc=desc+"<tr><th align=left colspan=2>&nbsp;Leave Type</th><td>"+lAction.getLeaveType(leaveType)+"</td><th>No.Of Days</th><td colspan=2>"+noOfDays+"</tr>";
					desc=desc+"<tr><th align=left colspan=2>&nbsp;From</th><td>"+startDate+"</td><th align=left>Duration</th><td colspan=2>"+startDur+"</td></tr>";
					desc=desc+"<tr><th align=left colspan=2>&nbsp;To</th><td>"+endDate+"<th align=left>Duration</th><td colspan=2>"+endDur+"</td></tr>";
					desc=desc+"<tr><th align=left colspan=2>&nbsp;Reason Type</th><td colspan=4>"+reasonType+"</td></tr>";
					desc=desc+"<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=4>"+reason+"</td></tr>";							   
					
				    desc=desc+"<tr><th colspan=6>Leave Balances</th></tr>";
				    desc=desc+"<tr><th>Type</th><th>Opening</th><th>Availed</th><th>Closing</th><th>Awaiting For Approval</th><th>Previous Year Availed("+(year-1)+")</th></tr>";
				    desc=desc+"<tr><th align=left>CASUAL</TH><TD align=right>"+casualOpeningBal+"</TD><TD align=right>"+casualAvailedBal+"</TD><TD align=right>"+casualClosingBal+"</TD><TD align=right>"+casualAwatingBal+"</TD><TD align=right>"+prevcasualAvailedBal+"</TD></TR>";
				    desc=desc+"<tr><th align=left>SICK</TH><TD align=right>"+sickOpeningBal+"</TD><TD align=right>"+sickAvailedBal+"</TD><TD align=right>"+sickClosingBal+"</TD><TD align=right>"+sickAwatingBal+"</TD><TD align=right>"+prevsickAvailedBal+"</TD></TR>";
				    if(gender.equalsIgnoreCase("F")){
				    	desc=desc+"<tr><th align=left>MATERNITY</TH><TD align=right>"+maternityOpeningBal+"</TD><TD align=right>"+maternityAvailedBal+"</TD><TD align=right>"+maternityClosingBal+"</TD><TD align=right>"+maternityAwatingBal+"</TD><TD align=right>&nbsp;</TD></TR>";
				    }
				    desc=desc+"<tr><th align=left>PRIVILEGE</TH><TD align=right>"+previlegeOpeningBal+"</TD><TD align=right>"+previlegeAvailedBal+"</TD><TD align=right>"+previlegeClosingBal+"</TD><TD align=right>"+previlegeAwatingBal+"</TD><TD align=right>"+prevprevilegeAvailedBal+"</TD></TR>";
				    desc=desc+"<tr><th align=left>Total</TH><TD align=right>"+(casualOpeningBal+sickOpeningBal+maternityOpeningBal+previlegeOpeningBal)+"</TD><TD align=right>"+(casualAvailedBal+sickAvailedBal+maternityAvailedBal+previlegeAvailedBal)+"</TD>"
				    		+ "<TD align=right>"+(casualClosingBal+sickClosingBal+maternityClosingBal+previlegeClosingBal)+"</TD><TD align=right>"+(casualAwatingBal+sickAwatingBal+maternityAwatingBal+previlegeAwatingBal)+"</TD><TD align=right>"+(prevcasualAvailedBal+prevsickAvailedBal+prevprevilegeAvailedBal)+"</TD></TR>";
				    
				    desc=desc+ "</table>";
					desc = desc + "</br></br></br>";

					desc = desc
							+ "<a href="
							+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
							+ pApprover
							+ "&reqId="
							+ Req_Id
							+ "&status=Approve><img src="
							+ "https://portal.microlabs.co.in/images/Approve.png"
							+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
							+ "https://portal.microlabs.co.in/approvals.do?method=approveMailRequest&userID="
							+ pApprover
							+ "&reqId="
							+ Req_Id
							+ "&status=Reject><img src="
							+ "https://portal.microlabs.co.in/images/Reject.png"
							+ " height=20 width=80 title=Reject border=0 /></a></br></br>";
					desc = desc + "</br></br></br></br>";
					desc = desc
							+ "<u><b>Last Leave Details :-</b></u></br></br>";
					desc = desc
							+ "<table><tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No Of Days</th><th>Approve Date</th></tr>";
					if (recLv_record == 1) {
				    	Iterator r=lastleaves.iterator();
				    	while(r.hasNext()) {
							
							LeaveForm leave=(LeaveForm)r.next();
							 desc=desc+"<tr><td>"+leave.getLeaveType()+"</td><td>"+leave.getStartDate()+"</td><td>"+leave.getEndDate()+"</td><td>"+leave.getNoOfDays()+"</td><td>"+leave.getApprovedDate()+"</td></tr>";
				    	}
				   
				    	desc=desc+"</table>";
				    } else {
						desc = desc
								+ "<tr><td colspan=5>No Leave Details</tr></table>";
					}
					desc = desc + "</br></br></br></br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
					approverDesc = desc;
				}

			} else if (requestFrom.equalsIgnoreCase("On Duty")) {
				String gender = "";
				String pernr = "";
				String name = "";
				String doj = "";
				String dep = "";
				String desg = "";
				String getGender = "select emp.PERNR,emp.EMP_FULLNAME,emp.SEX,emp.DOJ,dep.DPTSTXT,desg.DSGSTXT from emp_official_info emp,DEPARTMENT as dep,DESIGNATION as desg where emp.PERNR='"
						+ user.getEmployeeNo()
						+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
				ResultSet rsGender = ad.selectQuery(getGender);
				while (rsGender.next()) {
					gender = rsGender.getString("SEX");
					pernr = rsGender.getString("PERNR");
					name = rsGender.getString("EMP_FULLNAME");
					doj = rsGender.getString("DOJ");
					dep = rsGender.getString("DPTSTXT");
					desg = rsGender.getString("DSGSTXT");

				}

				String abc[] = doj.split("-");
				doj = abc[2] + "/" + abc[1] + "/" + abc[0];

				mailForm.setSubject("OnDuty Request From " + user.getFullName());
				boolean existStatus = false;
				String dataFlowType = "";
				String checkESSApproverList = "select count(*) from ESS_Approvers where employeeNumber='"
						+ user.getEmployeeNo() + "' and essType='Leave'";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					int number = rsCheckInEss.getInt(1);
					if (number == 0) {
						existStatus = false;
					} else {
						existStatus = true;
					}
				}
				if (existStatus == true) {
					String getDataFlowType = "select * from ESS_Approvers where employeeNumber='"
							+ user.getEmployeeNo() + "' and essType='Leave'";
					ResultSet rsDataFlowType = ad.selectQuery(getDataFlowType);
					while (rsDataFlowType.next()) {
						dataFlowType = rsDataFlowType.getString("reqType");
					}
					if (dataFlowType.equalsIgnoreCase("sequential")) {
						approver1 = "";
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ user.getEmployeeNo()
								+ "' and essType='Leave' and reqType='sequential'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");

						}

						desc = desc + "";
						desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
								+ "TABLE {border-collapse:collapse;border:1px solid black;}"
								+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
								+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
								+ "</STYLE>";
						if (plant.equalsIgnoreCase("")) {
							desc = desc
									+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /></center></td></tr> ";
							desc = desc
									+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
							desc = desc + "<tr><th align=left>Emp No.</th><td>"
									+ pernr
									+ "</td><th align=left>Name</th><td>"
									+ name + "</td></tr>";
							desc = desc + "<tr><th align=left>Dept</th><td>"
									+ dep + "</td><th align=left>Desg</th><td>"
									+ desg + "</td></tr>";
							desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td >"+Req_Id+"</td></tr>";

							desc = desc
									+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=3>"
									+ ondutyType + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
									+ startDate
									+ "</td><th align=left>From Time</th><td>"
									+ fromTime + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
									+ endDate
									+ "<th align=left>End Time</th><td>"
									+ toTime + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
									+ reason + "</td></tr></table></br></br>";

							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
						} else {
							desc = desc
									+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /></center></td></tr> ";
							desc = desc
									+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
							desc = desc + "<tr><th align=left>Emp No.</th><td>"
									+ pernr + "</td><th>Name</th><td>" + name
									+ "</td></tr>";
							desc = desc + "<tr><th align=left>Dept</th><td>"
									+ dep + "</td><th align=left>Desg</th><td>"
									+ desg + "</td></tr>";
							desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td >"+Req_Id+"</td></tr>";

							desc = desc
									+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=1>"
									+ ondutyType + "</td><th>Plant</th><td>"
									+ plant + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
									+ startDate
									+ "</td><th align=left>From Time</th><td>"
									+ fromTime + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
									+ endDate
									+ "<th align=left>End Time</th><td>"
									+ toTime + "</td></tr>";
							desc = desc
									+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
									+ reason + "</td></tr></table></br></br>";

							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
						}
						approverDesc = desc;

					}
					if (dataFlowType.equalsIgnoreCase("parallel")) {
						String toAddress = "";

						String parallelApprover1 = "";
						String parallelApprover2 = "";
						String empIDS = "";
						String empEmail = "";
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ user.getEmployeeNo()
								+ "' and essType='Leave' and reqType='parallel'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");
							parallelApprover1 = rsApproverDsg
									.getString("Parallel_Approver1");
							parallelApprover2 = rsApproverDsg
									.getString("Parallel_Approver2");
						}
						if (!(approver1.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";
							if (plant.equalsIgnoreCase("")) {
								desc = desc
										+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
										+ "https://portal.microlabs.co.in/images/logo.png"
										+ " height=60 width=50  /></center></td></tr> ";
								desc = desc
										+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left>Emp No.</th><td>"
										+ pernr
										+ "</td><th align=left>Name</th><td>"
										+ name + "</td></tr>";
								desc = desc
										+ "<tr><th align=left>Dept</th><td>"
										+ dep
										+ "</td><th align=left>Desg</th><td>"
										+ desg + "</td></tr>";
								desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td >"+Req_Id+"</td></tr>";

								desc = desc
										+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=3>"
										+ ondutyType + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
										+ startDate
										+ "</td><th align=left>From Time</th><td>"
										+ fromTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
										+ endDate
										+ "<th align=left>End Time</th><td>"
										+ toTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
										+ reason
										+ "</td></tr></table></br></br>";

								desc = desc
										+ "<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ approver1
										+ "&reqId="
										+ Req_Id
										+ "&status=Approve><img src="
										+ "https://portal.microlabs.co.in/images/Approve.png"
										+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ approver1
										+ "&reqId="
										+ Req_Id
										+ "&status=Reject><img src="
										+ "https://portal.microlabs.co.in/images/Reject.png"
										+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
								desc = desc
										+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
										+ "https://portal.microlabs.co.in/"
										+ ">EMicro Portal</a> .</span> ";
								desc = desc + "</body></html>";
							} else {
								desc = desc
										+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
										+ "https://portal.microlabs.co.in/images/logo.png"
										+ " height=60 width=50  /></center></td></tr> ";
								desc = desc
										+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left>Emp No.</th><td>"
										+ pernr + "</td><th>Name</th><td>"
										+ name + "</td></tr>";
								desc = desc
										+ "<tr><th align=left>Dept</th><td>"
										+ dep
										+ "</td><th align=left>Desg</th><td>"
										+ desg + "</td></tr>";
								desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td >"+Req_Id+"</td></tr>";
								desc = desc
										+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=1>"
										+ ondutyType
										+ "</td><th>Plant</th><td>" + plant
										+ "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
										+ startDate
										+ "</td><th align=left>From Time</th><td>"
										+ fromTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
										+ endDate
										+ "<th align=left>End Time</th><td>"
										+ toTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
										+ reason
										+ "</td></tr></table></br></br>";

								desc = desc
										+ "<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ approver1
										+ "&reqId="
										+ Req_Id
										+ "&status=Approve><img src="
										+ "https://portal.microlabs.co.in/images/Approve.png"
										+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ approver1
										+ "&reqId="
										+ Req_Id
										+ "&status=Reject><img src="
										+ "https://portal.microlabs.co.in/images/Reject.png"
										+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
								desc = desc
										+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
										+ "https://portal.microlabs.co.in/"
										+ ">EMicro Portal</a> .</span> ";
								desc = desc + "</body></html>";
							}
							approverDesc = desc;
						}
						if (!(parallelApprover1.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";
							if (plant.equalsIgnoreCase("")) {
								desc = desc
										+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
										+ "https://portal.microlabs.co.in/images/logo.png"
										+ " height=60 width=50  /></center></td></tr> ";
								desc = desc
										+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left>Emp No.</th><td>"
										+ pernr
										+ "</td><th align=left>Name</th><td>"
										+ name + "</td></tr>";
								desc = desc
										+ "<tr><th align=left>Dept</th><td>"
										+ dep
										+ "</td><th align=left>Desg</th><td>"
										+ desg + "</td></tr>";
								desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td >"+Req_Id+"</td></tr>";

								desc = desc
										+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=3>"
										+ ondutyType + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
										+ startDate
										+ "</td><th align=left>From Time</th><td>"
										+ fromTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
										+ endDate
										+ "<th align=left>End Time</th><td>"
										+ toTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
										+ reason
										+ "</td></tr></table></br></br>";

								desc = desc
										+ "<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover1
										+ "&reqId="
										+ Req_Id
										+ "&status=Approve><img src="
										+ "https://portal.microlabs.co.in/images/Approve.png"
										+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover1
										+ "&reqId="
										+ Req_Id
										+ "&status=Reject><img src="
										+ "https://portal.microlabs.co.in/images/Reject.png"
										+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
								desc = desc
										+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
										+ "https://portal.microlabs.co.in/"
										+ ">EMicro Portal</a> .</span> ";
								desc = desc + "</body></html>";
							} else {
								desc = desc
										+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
										+ "https://portal.microlabs.co.in/images/logo.png"
										+ " height=60 width=50  /></center></td></tr> ";
								desc = desc
										+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left>Emp No.</th><td>"
										+ pernr + "</td><th>Name</th><td>"
										+ name + "</td></tr>";
								desc = desc
										+ "<tr><th align=left>Dept</th><td>"
										+ dep
										+ "</td><th align=left>Desg</th><td>"
										+ desg + "</td></tr>";
								desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td >"+Req_Id+"</td></tr>";

								desc = desc
										+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=1>"
										+ ondutyType
										+ "</td><th>Plant</th><td>" + plant
										+ "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
										+ startDate
										+ "</td><th align=left>From Time</th><td>"
										+ fromTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
										+ endDate
										+ "<th align=left>End Time</th><td>"
										+ toTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
										+ reason
										+ "</td></tr></table></br></br>";

								desc = desc
										+ "<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover1
										+ "&reqId="
										+ Req_Id
										+ "&status=Approve><img src="
										+ "https://portal.microlabs.co.in/images/Approve.png"
										+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover1
										+ "&reqId="
										+ Req_Id
										+ "&status=Reject><img src="
										+ "https://portal.microlabs.co.in/images/Reject.png"
										+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
								desc = desc
										+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
										+ "https://portal.microlabs.co.in/"
										+ ">EMicro Portal</a> .</span> ";
								desc = desc + "</body></html>";
							}
							parallelApprover1Desc = desc;
						}
						if (!(parallelApprover2.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";
							if (plant.equalsIgnoreCase("")) {
								desc = desc
										+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
										+ "https://portal.microlabs.co.in/images/logo.png"
										+ " height=60 width=50  /></center></td></tr> ";
								desc = desc
										+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left>Emp No.</th><td>"
										+ pernr
										+ "</td><th align=left>Name</th><td>"
										+ name + "</td></tr>";
								desc = desc
										+ "<tr><th align=left>Dept</th><td>"
										+ dep
										+ "</td><th align=left>Desg</th><td>"
										+ desg + "</td></tr>";
								desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td >"+Req_Id+"</td></tr>";

								desc = desc
										+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=3>"
										+ ondutyType + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
										+ startDate
										+ "</td><th align=left>From Time</th><td>"
										+ fromTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
										+ endDate
										+ "<th align=left>End Time</th><td>"
										+ toTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
										+ reason
										+ "</td></tr></table></br></br>";

								desc = desc
										+ "<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover2
										+ "&reqId="
										+ Req_Id
										+ "&status=Approve><img src="
										+ "https://portal.microlabs.co.in/images/Approve.png"
										+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover2
										+ "&reqId="
										+ Req_Id
										+ "&status=Reject><img src="
										+ "https://portal.microlabs.co.in/images/Reject.png"
										+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
								desc = desc
										+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
										+ "https://portal.microlabs.co.in/"
										+ ">EMicro Portal</a> .</span> ";
								desc = desc + "</body></html>";
							} else {
								desc = desc
										+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
										+ "https://portal.microlabs.co.in/images/logo.png"
										+ " height=60 width=50  /></center></td></tr> ";
								desc = desc
										+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left>Emp No.</th><td>"
										+ pernr + "</td><th>Name</th><td>"
										+ name + "</td></tr>";
								desc = desc
										+ "<tr><th align=left>Dept</th><td>"
										+ dep
										+ "</td><th align=left>Desg</th><td>"
										+ desg + "</td></tr>";
								desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td >"+Req_Id+"</td></tr>";

								desc = desc
										+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=1>"
										+ ondutyType
										+ "</td><th>Plant</th><td>" + plant
										+ "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
										+ startDate
										+ "</td><th align=left>From Time</th><td>"
										+ fromTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
										+ endDate
										+ "<th align=left>End Time</th><td>"
										+ toTime + "</td></tr>";
								desc = desc
										+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
										+ reason
										+ "</td></tr></table></br></br>";

								desc = desc
										+ "<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover2
										+ "&reqId="
										+ Req_Id
										+ "&status=Approve><img src="
										+ "https://portal.microlabs.co.in/images/Approve.png"
										+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
										+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
										+ parallelApprover2
										+ "&reqId="
										+ Req_Id
										+ "&status=Reject><img src="
										+ "https://portal.microlabs.co.in/images/Reject.png"
										+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
								desc = desc
										+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
										+ "https://portal.microlabs.co.in/"
										+ ">EMicro Portal</a> .</span> ";
								desc = desc + "</body></html>";
							}
							parallelApprover2Desc = desc;
						}
					}

				}
				if (existStatus == false) {

					desc = desc + "";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";
					if (plant.equalsIgnoreCase("")) {
						desc = desc
								+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
								+ "https://portal.microlabs.co.in/images/logo.png"
								+ " height=60 width=50  /></center></td></tr> ";
						desc = desc
								+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
						desc = desc + "<tr><th align=left>Emp No.</th><td>"
								+ pernr + "</td><th align=left>Name</th><td>"
								+ name + "</td></tr>";
						desc = desc + "<tr><th align=left>Dept</th><td>" + dep
								+ "</td><th align=left>Desg</th><td>" + desg
								+ "</td></tr>";
						desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td >"+Req_Id+"</td></tr>";

						desc = desc
								+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=3>"
								+ ondutyType + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
								+ startDate
								+ "</td><th align=left>From Time</th><td>"
								+ fromTime + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
								+ endDate + "<th align=left>End Time</th><td>"
								+ toTime + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
								+ reason + "</td></tr></table></br></br>";

						desc = desc
								+ "<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
								+ pApprover
								+ "&reqId="
								+ Req_Id
								+ "&status=Approve><img src="
								+ "https://portal.microlabs.co.in/images/Approve.png"
								+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
								+ pApprover
								+ "&reqId="
								+ Req_Id
								+ "&status=Reject><img src="
								+ "https://portal.microlabs.co.in/images/Reject.png"
								+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
						desc = desc
								+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
								+ "https://portal.microlabs.co.in/"
								+ ">EMicro Portal</a> .</span> ";
						desc = desc + "</body></html>";
					} else {
						desc = desc
								+ "<html><body></br><big><p><font color=blue>Note&nbsp;:&nbsp;Please do not reply to this mail..<br/>Give your decision by clicking on Approve or Reject button in the below mail.</font></p></big><table><tr ><td rowspan=5><center><img src="
								+ "https://portal.microlabs.co.in/images/logo.png"
								+ " height=60 width=50  /></center></td></tr> ";
						desc = desc
								+ "<tr><th colspan=4 ><center>Requester Details</center></th></tr>";
						desc = desc + "<tr><th align=left>Emp No.</th><td>"
								+ pernr + "</td><th>Name</th><td>" + name
								+ "</td></tr>";
						desc = desc + "<tr><th align=left>Dept</th><td>" + dep
								+ "</td><th align=left>Desg</th><td>" + desg
								+ "</td></tr>";
						desc=desc+"<tr><th>Date Of Joining</th><td >"+doj+"</td><th>Request No</th><td >"+Req_Id+"</td></tr>";

						desc = desc
								+ "<tr><th colspan=5 ><center>Onduty Details</center></th></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;OnDuty Type</th><td colspan=1>"
								+ ondutyType + "</td><th>Plant</th><td>"
								+ plant + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;From Date</th><td>"
								+ startDate
								+ "</td><th align=left>From Time</th><td>"
								+ fromTime + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;To</th><td>"
								+ endDate + "<th align=left>End Time</th><td>"
								+ toTime + "</td></tr>";
						desc = desc
								+ "<tr><th align=left colspan=2>&nbsp;Reason</th><td colspan=3>"
								+ reason + "</td></tr></table></br></br>";

						desc = desc
								+ "<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
								+ pApprover
								+ "&reqId="
								+ Req_Id
								+ "&status=Approve><img src="
								+ "https://portal.microlabs.co.in/images/Approve.png"
								+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailOndutyRequest&userID="
								+ pApprover
								+ "&reqId="
								+ Req_Id
								+ "&status=Reject><img src="
								+ "https://portal.microlabs.co.in/images/Reject.png"
								+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
						desc = desc
								+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
								+ "https://portal.microlabs.co.in/"
								+ ">EMicro Portal</a> .</span> ";
						desc = desc + "</body></html>";
					}
				}

			} else if (requestFrom.equalsIgnoreCase("Permission")) {
				mailForm.setSubject("Permission Request From "
						+ user.getFullName());
				boolean existStatus = false;
				String dataFlowType = "";
				String checkESSApproverList = "select count(*) from ESS_Approvers where employeeNumber='"
						+ user.getEmployeeNo() + "' and essType='Leave'";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					int number = rsCheckInEss.getInt(1);
					if (number == 0) {
						existStatus = false;
					} else {
						existStatus = true;
					}
				}
				if (existStatus == true) {
					String getDataFlowType = "select * from ESS_Approvers where employeeNumber='"
							+ user.getEmployeeNo() + "' and essType='Leave'";
					ResultSet rsDataFlowType = ad.selectQuery(getDataFlowType);
					while (rsDataFlowType.next()) {
						dataFlowType = rsDataFlowType.getString("reqType");
					}
					if (dataFlowType.equalsIgnoreCase("sequential")) {
						approver1 = "";
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ user.getEmployeeNo()
								+ "' and essType='Leave' and reqType='sequential'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");

						}

						desc = desc + "";
						desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
								+ "TABLE {border-collapse:collapse;border:1px solid black;}"
								+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
								+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
								+ "</STYLE>";

						desc = desc
								+ "<html><body><img src="
								+ "https://portal.microlabs.co.in/images/logo.png"
								+ " height=60 width=50  /> <table><tr><th colspan=4 ><center>Permission Details</center></th></tr>";
						desc = desc
								+ "<tr><th align=left>Permission Date</th><td>"
								+ perDate + "</td><th>Permission Type</th><td>"
								+ type + "</td></tr>";
						desc = desc + "<tr><th align=left> Time</th><td >"
								+ perStartTime
								+ "</td><th align=left> Swipe Type</th><td >"
								+ swipetype + "</td></tr>";
						desc = desc + "<tr><th>Reason</th><td colspan=3>"
								+ perReason + "</tr></table></br></br>";
						desc = desc
								+ "<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
								+ approver1
								+ "&reqId="
								+ Req_Id
								+ "&status=Approve><img src="
								+ "https://portal.microlabs.co.in/images/Approve.png"
								+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
								+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
								+ approver1
								+ "&reqId="
								+ Req_Id
								+ "&status=Reject><img src="
								+ "https://portal.microlabs.co.in/images/Reject.png"
								+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
						desc = desc
								+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
								+ "https://portal.microlabs.co.in/"
								+ ">EMicro Portal</a> .</span> ";
						desc = desc + "</body></html>";
						approverDesc = desc;

					}
					if (dataFlowType.equalsIgnoreCase("parallel")) {
						String toAddress = "";

						String parallelApprover1 = "";
						String parallelApprover2 = "";
						String empIDS = "";
						String empEmail = "";
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ user.getEmployeeNo()
								+ "' and essType='Leave' and reqType='parallel'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");
							parallelApprover1 = rsApproverDsg
									.getString("Parallel_Approver1");
							parallelApprover2 = rsApproverDsg
									.getString("Parallel_Approver2");
						}
						if (!(approver1.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";

							desc = desc
									+ "<html><body><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /> <table><tr><th colspan=4 ><center>Permission Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left>Permission Date</th><td>"
									+ perDate
									+ "</td><th>Permission Type</th><td>"
									+ type + "</td></tr>";
							desc = desc
									+ "<tr><th align=left> Time</th><td >"
									+ perStartTime
									+ "</td><th align=left> Swipe Type</th><td >"
									+ swipetype + "</td></tr>";
							desc = desc + "<tr><th>Reason</th><td colspan=3>"
									+ perReason + "</tr></table></br></br>";
							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
									+ approver1
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
							approverDesc = desc;
						}
						if (!(parallelApprover1.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";

							desc = desc
									+ "<html><body><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /> <table><tr><th colspan=4 ><center>Permission Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left>Permission Date</th><td>"
									+ perDate
									+ "</td><th>Permission Type</th><td>"
									+ type + "</td></tr>";
							desc = desc
									+ "<tr><th align=left> Time</th><td >"
									+ perStartTime
									+ "</td><th align=left> Swipe Type</th><td >"
									+ swipetype + "</td></tr>";
							desc = desc + "<tr><th>Reason</th><td colspan=3>"
									+ perReason + "</tr></table></br></br>";
							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
									+ parallelApprover1
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
									+ parallelApprover1
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
							parallelApprover1Desc = desc;
						}
						if (!(parallelApprover2.equalsIgnoreCase(""))) {
							desc = desc + "";
							desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
									+ "TABLE {border-collapse:collapse;border:1px solid black;}"
									+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
									+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
									+ "</STYLE>";

							desc = desc
									+ "<html><body><img src="
									+ "https://portal.microlabs.co.in/images/logo.png"
									+ " height=60 width=50  /> <table><tr><th colspan=4 ><center>Permission Details</center></th></tr>";
							desc = desc
									+ "<tr><th align=left>Permission Date</th><td>"
									+ perDate
									+ "</td><th>Permission Type</th><td>"
									+ type + "</td></tr>";
							desc = desc
									+ "<tr><th align=left> Time</th><td >"
									+ perStartTime
									+ "</td><th align=left> Swipe Type</th><td >"
									+ swipetype + "</td></tr>";
							desc = desc + "<tr><th>Reason</th><td colspan=3>"
									+ perReason + "</tr></table></br></br>";
							desc = desc
									+ "<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
									+ parallelApprover2
									+ "&reqId="
									+ Req_Id
									+ "&status=Approve><img src="
									+ "https://portal.microlabs.co.in/images/Approve.png"
									+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
									+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
									+ parallelApprover2
									+ "&reqId="
									+ Req_Id
									+ "&status=Reject><img src="
									+ "https://portal.microlabs.co.in/images/Reject.png"
									+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
							desc = desc
									+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
									+ "https://portal.microlabs.co.in/"
									+ ">EMicro Portal</a> .</span> ";
							desc = desc + "</body></html>";
							parallelApprover2Desc = desc;
						}
					}

				}
				if (existStatus == false) {
					desc = desc + "";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
							+ "TABLE {border-collapse:collapse;border:1px solid black;}"
							+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
							+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
							+ "</STYLE>";

					desc = desc
							+ "<html><body><img src="
							+ "https://portal.microlabs.co.in/images/logo.png"
							+ " height=60 width=50  /> <table><tr><th colspan=4 ><center>Permission Details</center></th></tr>";
					desc = desc + "<tr><th align=left>Permission Date</th><td>"
							+ perDate + "</td><th>Permission Type</th><td>"
							+ type + "</td></tr>";
					desc = desc + "<tr><th align=left> Time</th><td >"
							+ perStartTime
							+ "</td><th align=left> Swipe Type</th><td >"
							+ swipetype + "</td></tr>";
					desc = desc + "<tr><th>Reason</th><td colspan=3>"
							+ perReason + "</tr></table></br></br>";
					desc = desc
							+ "<a href="
							+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
							+ pApprover
							+ "&reqId="
							+ Req_Id
							+ "&status=Approve><img src="
							+ "https://portal.microlabs.co.in/images/Approve.png"
							+ " height=20 width=80 title=Approve border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="
							+ "https://portal.microlabs.co.in/approvals.do?method=approveMailPermissionRequest&userID="
							+ pApprover
							+ "&reqId="
							+ Req_Id
							+ "&status=Reject><img src="
							+ "https://portal.microlabs.co.in/images/Reject.png"
							+ " height=20 width=80 title=Reject border=0 /></a></br></br></br></br>";
					desc = desc
							+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
							+ "https://portal.microlabs.co.in/"
							+ ">EMicro Portal</a> .</span> ";
					desc = desc + "</body></html>";
				}
			}

			else if (requestFrom.equalsIgnoreCase("Feedback")) {
				desc = desc + "Subject : " + feedSub + "</br>";
				desc = desc + "Comments : " + feedComment + "</br>";
			}

			mailForm.setDescription(desc);

			if (requestFrom.equalsIgnoreCase("Leave")
					|| requestFrom.equalsIgnoreCase("On Duty")
					|| requestFrom.equalsIgnoreCase("Permission")) {
				String essType = "";
				if (requestFrom.equalsIgnoreCase("Leave"))
					essType = "Leave";

				if (requestFrom.equalsIgnoreCase("On Duty"))
					essType = "Leave";

				if (requestFrom.equalsIgnoreCase("Permission"))
					essType = "Leave";

				boolean existStatus = false;
				String dataFlowType = "";
				String checkESSApproverList = "select count(*) from ESS_Approvers where employeeNumber='"
						+ user.getEmployeeNo()
						+ "' and essType='"
						+ essType
						+ "'";
				ResultSet rsCheckInEss = ad.selectQuery(checkESSApproverList);
				while (rsCheckInEss.next()) {
					int number = rsCheckInEss.getInt(1);
					if (number == 0) {
						existStatus = false;
					} else {
						existStatus = true;
					}
				}
				if (existStatus == true) {
					String getDataFlowType = "select * from ESS_Approvers where employeeNumber='"
							+ user.getEmployeeNo()
							+ "' and essType='"
							+ essType + "'";
					ResultSet rsDataFlowType = ad.selectQuery(getDataFlowType);
					while (rsDataFlowType.next()) {
						dataFlowType = rsDataFlowType.getString("reqType");
					}
					if (dataFlowType.equalsIgnoreCase("sequential")) {

						String getEmpIDs = "select EMAIL_ID from emp_official_info where PERNR in ("
								+ approver1 + ")";
						ResultSet rsEMPIDS = ad.selectQuery(getEmpIDs);
						int ii = 0;
						String approverEmail1 = "";
						while (rsEMPIDS.next()) {
							approverEmail1 = rsEMPIDS.getString("EMAIL_ID");
						}
						mailForm.setDescription(approverDesc);
						mailAction.mailSendToParallelApprovers(request,
								mailForm, "request", approverEmail1);
					}
					if (dataFlowType.equalsIgnoreCase("parallel")) {
						String toAddress = "";

						String empIDS = "";
						String parallelApprover1 = "";
						String parallelApprover2 = "";
						String approverEmail1 = "";
						String parallelAppr1Email = "";
						String parallelAppr2Email = "";
						String getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ user.getEmployeeNo()
								+ "' and essType='Leave' and reqType='parallel'  and  Priority=1";
						ResultSet rsApproverDsg = ad
								.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");
							parallelApprover1 = rsApproverDsg
									.getString("Parallel_Approver1");
							parallelApprover2 = rsApproverDsg
									.getString("Parallel_Approver2");
						}
						getApproverDesig = "select * from ESS_Approvers where employeeNumber='"
								+ user.getEmployeeNo()
								+ "' and essType='Leave' and reqType='parallel'  and  Priority=1";
						rsApproverDsg = ad.selectQuery(getApproverDesig);
						while (rsApproverDsg.next()) {
							approver1 = rsApproverDsg.getString("ApproverId");
							if (!(approver1.equalsIgnoreCase(""))) {
								empIDS = rsApproverDsg.getString("ApproverId");
							}
							parallelApprover1 = rsApproverDsg
									.getString("Parallel_Approver1");
							if (!(parallelApprover1.equalsIgnoreCase(""))) {
								empIDS = empIDS
										+ ","
										+ rsApproverDsg
												.getString("Parallel_Approver1");
							}
							parallelApprover2 = rsApproverDsg
									.getString("Parallel_Approver2");
							if (!(parallelApprover2.equalsIgnoreCase(""))) {
								empIDS = empIDS
										+ ","
										+ rsApproverDsg
												.getString("Parallel_Approver2");
							}

						}

						empIDS = empIDS.substring(0, (empIDS.length() - 0));
						String getEmpIDs = "select EMAIL_ID,PERNR from emp_official_info where PERNR in ("
								+ empIDS + ")";
						ResultSet rsEMPIDS = ad.selectQuery(getEmpIDs);
						int ii = 0;
						while (rsEMPIDS.next()) {
							if (approver1.equalsIgnoreCase(rsEMPIDS
									.getString("PERNR"))) {
								approverEmail1 = rsEMPIDS.getString("EMAIL_ID");
								mailForm.setDescription(approverDesc);
								mailAction.mailSendToParallelApprovers(request,
										mailForm, "request", approverEmail1);
							}
							if (parallelApprover1.equalsIgnoreCase(rsEMPIDS
									.getString("PERNR"))) {
								parallelAppr1Email = rsEMPIDS
										.getString("EMAIL_ID");
								mailForm.setDescription(parallelApprover1Desc);
								mailAction
										.mailSendToParallelApprovers(request,
												mailForm, "request",
												parallelAppr1Email);
							}
							if (parallelApprover2.equalsIgnoreCase(rsEMPIDS
									.getString("PERNR"))) {
								parallelAppr2Email = rsEMPIDS
										.getString("EMAIL_ID");
								mailForm.setDescription(parallelApprover2Desc);
								mailAction
										.mailSendToParallelApprovers(request,
												mailForm, "request",
												parallelAppr2Email);
							}
							ii++;
						}
					}
				} else {
					mailAction
							.mailSendToRecipient(request, mailForm, "request");
				}

			}
			if (requestFrom.equalsIgnoreCase("Material Code Request")) {
				mailAction.mailSendToRecipient(request, mailForm, "request");
				if (!(parallelApp1.equalsIgnoreCase(""))) {
					getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ parallelApp1 + "'";
					approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					mailAction
							.mailSendToRecipient(request, mailForm, "request");
				}
				if (!(parallelApp2.equalsIgnoreCase(""))) {
					getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ parallelApp2 + "'";
					approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					mailAction
							.mailSendToRecipient(request, mailForm, "request");
				}
			}
			if (requestFrom.equalsIgnoreCase("Vendor Master")
					|| requestFrom.equalsIgnoreCase("Customer Master")
					|| requestFrom.equalsIgnoreCase("Service Master")
					|| requestFrom.equalsIgnoreCase("Code Extension")) {
				mailAction.mailSendToRecipient(request, mailForm, "request");
				if (!(parallelApp1.equalsIgnoreCase(""))) {
					getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ parallelApp1 + "'";
					approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					mailAction
							.mailSendToRecipient(request, mailForm, "request");
				}
				if (!(parallelApp2.equalsIgnoreCase(""))) {
					getAppEmailID = "select EMAIL_ID from emp_official_info where PERNR='"
							+ parallelApp2 + "'";
					approverRS = ad.selectQuery(getAppEmailID);
					while (approverRS.next()) {
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					mailAction
							.mailSendToRecipient(request, mailForm, "request");
				}
			}
			success = 1;
		} catch (ServletException se) {
			System.out.println("ServletException @ sending leave request..");
			se.printStackTrace();
		} catch (MessagingException se) {
			System.out.println("MessagingException @ sending leave request..");
			se.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("IOException @ sending leave request..");
			ioe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("SQLException @ sending  request..");
			sqle.printStackTrace();
		}
		return success;
	}

	public int sendNewPasswordToMail(HttpServletRequest request,
			String userName, String passWord) {
		String fullName = "";
		int success = 0;

		String approver = "";

		MailInboxForm mailForm = new MailInboxForm();
		MailInboxAction mailAction = new MailInboxAction();
		try {
			String personalEmail = "";

			String getApproverID = "select * from emp_personal_info where employee_no='"
					+ userName + "'";

			ResultSet approverRS = ad.selectQuery(getApproverID);
			while (approverRS.next()) {

				personalEmail = approverRS.getString("email_address");

			}
			fullName = userName;
			String password = passWord;
			mailForm.setToAddress(personalEmail);
			System.out.println("m -> " + personalEmail);
			mailForm.setccAddress("");
			mailForm.setbccAddress("");
			mailForm.setSubject("Login Information");
			// constructing mail description for each request
			String desc = "Your Login Details" + "</br>";
			desc = "User Name : " + fullName + "</br>";
			desc = desc + "Password : " + password + "</br>";
			mailForm.setDescription(desc);
			// MailMessage mailMessage = new MailMessage(from, to, subject,
			// body);
			mailAction.mailSendToRecipient(request, mailForm, "forgetpass");
			success = 1;
		} catch (ServletException se) {
			System.out.println("ServletException @ sending leave request..");
			se.printStackTrace();
		} catch (MessagingException se) {
			System.out.println("MessagingException @ sending leave request..");
			se.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("IOException @ sending leave request..");
			ioe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("SQLException @ sending leave request..");
			sqle.printStackTrace();
		}
		return success;
	}

	public String getTableNameForRequest(String reqName) {
		String tableName = "";
		if (reqName.equalsIgnoreCase("Recruitment Request")) {
			tableName = "Recruitment_Request";

		} else if (reqName.equalsIgnoreCase("Add Man Power")) {
			tableName = "Man_Power_Matrix";
		} else if (reqName.equalsIgnoreCase("Leave")) {
			tableName = "leave_details";
		} else if (reqName.equalsIgnoreCase("On Duty")) {
			tableName = "OnDuty_details";
		} else if (reqName.contains("Vendor Master")) {
			tableName = "vendor_master_m";
		} else if (reqName.contains("Customer Master")) {
			tableName = "CUSTOMER_MASTER_M";
		} else if (reqName.contains("Service Master")) {
			tableName = "SERVICE_MASTER";
		} else if (reqName.equalsIgnoreCase("Feedback")) {
			tableName = "feedBack_details";
		} else if (reqName.equalsIgnoreCase("Material Code Request")) {
			tableName = "material_code_request";
		} else if (reqName.equalsIgnoreCase("Inbox")) {
			tableName = "Mail_Inbox";
		} else if (reqName.equalsIgnoreCase("SentItem")) {
			tableName = "Mail_SentItem";
		} else if (reqName.equalsIgnoreCase("Draft")) {
			tableName = "Mail_Draft";
		} else if (reqName.equalsIgnoreCase("Trash")) {
			tableName = "Mail_Trash";
		} else if (reqName.equalsIgnoreCase("Permission")) {
			tableName = "Permission_details";
		}
		return tableName;
	}

	public String getApproverName(String appmgr) {
		MainDao exeQry = new MainDao();
		int count = 0;
		try {
			// get count
			String todayDate = EMicroUtils.dateConvert(EMicroUtils
					.getCurrentSysDate());
			String sql = "select count(*) from Delegation_Details where delegate_from='"
					+ appmgr + "'";
			ResultSet countrs = exeQry.selectQuery(sql);
			while (countrs.next()) {
				count = countrs.getInt(1);
			}
			if (count > 0) {
				todayDate = EMicroUtils.dateConvert(EMicroUtils
						.getCurrentSysDate());
				sql = "select * from Delegation_Details where delegate_from='"
						+ appmgr + "' and delegate_dates like '%" + todayDate
						+ "%'";
				countrs = exeQry.selectQuery(sql);
				while (countrs.next()) {
					appmgr = countrs.getString("delegate_to");
				}
			}
		} catch (SQLException se) {
			System.out.println("Exception @ getting count");
			se.printStackTrace();
		}
		return appmgr;
	}

}
