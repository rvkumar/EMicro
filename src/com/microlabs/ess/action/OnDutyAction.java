package com.microlabs.ess.action;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.ess.form.OnDutyForm;
import com.microlabs.hr.dao.SAPAttendenceDAO;
import com.microlabs.hr.form.SAPAttendenceForm;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.main.form.MailInboxForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class OnDutyAction extends DispatchAction{
	EssDao ad=EssDao.dBConnection();
	
	public String EmpLoc(String a)
	{
		
		String b = "";
		if(a==null)
		{
		  return b;	
		}
		
		if(!a.equalsIgnoreCase(""))
		{	
			
			EssDao ad = new EssDao();
		String emp = "select LOCID from emp_official_info where pernr = '"+a+"'";
		ResultSet rs = ad.selectQuery(emp);
		try {
			if(rs.next())
			{
				b=rs.getString("LOCID"); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		return b;
		
	}
	
	public ActionForward submitcancelRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	
	{
		OnDutyForm leaveForm = (OnDutyForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String Req_Id=request.getParameter("requestNo");
		
		  Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
			 String submitDate = ft.format(dNow);
		
		int i=0;
		//GET HR DATA WHO CANCEL

		String rep=user.getRepgrp();
		String loc="";
		
		if(rep.equalsIgnoreCase("12"))
		{
			loc="34";
		}
		else
		{
			loc=user.getPlantId();
		}
		
		
		
		String as="select * from ess_cancel_appr where Location=(select LOCATION_CODE from Location where LOCID="+loc+")";
		ResultSet ads=ad.selectQuery(as);
		try {
			while(ads.next())
			{		
			
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
			saveRecReq = saveRecReq + "'"+Req_Id+"','Cancel On Duty','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+ads.getString("HR_NO")+"','No','"+user.getEmployeeNo()+"','"+leaveForm.getRemark()+"')";
			 i=ad.SqlExecuteUpdate(saveRecReq);
			 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
		 if(i > 0){
				leaveForm.setMessage("Data Submitted Successfully");
				 //update flag
				 String update="update OnDuty_details set cancelflag=1 where request_no='"+Req_Id+"'";
				int j =ad.SqlExecuteUpdate(update);
			}
		 
		 else
			{
				leaveForm.setMessage("Error Please Check");
			}
		

		 editCancelRequest(mapping, leaveForm, request, response);
		return mapping.findForward("cancelonDutydetails");
	}
	
private static final String DESTINATION = "SAP_DESTINATION";
	
	public static String display1(Date dbDate)
	{
	String dt="";
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	try
	{
	dt=dateFormat.format(dbDate);
	}
	catch (Exception e)
	{
	dt="";
	}
	return dt;
	}
	
	public String convertDate(Date date1) {

		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		String formatedDate = "";
		try {
			Date date = formatter.parse(date1.toString());

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			formatedDate = cal.get(Calendar.YEAR) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.DATE);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formatedDate;
	}
	
	private void connectSAP() {
		try {

			Properties connectProperties = new Properties();// TODO change the
			// details
			connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,
					"192.168.1.2");
			connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,
					"00");
			connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,
					"900");
			connectProperties.setProperty(DestinationDataProvider.JCO_USER,
					"rfcprd");
			connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,
					"Test123#");
			connectProperties.setProperty(DestinationDataProvider.JCO_LANG,
					"EN");
			File destCfg = new File(DESTINATION + ".jcoDestination");

			FileOutputStream fos = new FileOutputStream(destCfg, false);
			connectProperties.store(fos, "SAP_DESTINATION config");
			fos.close();

		} catch (Exception e) {
			throw new RuntimeException("Unable to create the destination file",
					e);
		}
	}
	
	public ActionForward editCancelRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		OnDutyForm ondutyForm=(OnDutyForm)form;
	
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	int userID=user.getId();
	int requestNo=Integer.parseInt(request.getParameter("requestNo"));
	ondutyForm.setId(Integer.toString(requestNo));
	String reqmon="";
	String reqyear="";
	String reqstart="";
	String reqend="";
	String reqstart1="";
	String reqend1="";
	String reqmonname="";
	try{
		ResultSet rs11 = ad.selectQuery("select LOCID," +
		"LOCNAME,location_code from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
			String userPlantID=user.getPlantId();
		while(rs11.next()) {
			locationList.add(rs11.getString("location_code"));
			locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
		}
		ondutyForm.setLocationIdList(locationList);
		ondutyForm.setLocationLabelList(locationLabelList);
		
	ondutyForm.setRequestNumber(requestNo);
	
	
	String draftData="select right(left(convert(varchar(11),start_date,106) ,6),3)as monname,day(start_date) as startday,day(end_date) as enday,*,right('0' + rtrim(month(start_date)),2) as mon,YEAR(start_date) as year from OnDuty_details where request_no='"+requestNo+"'";
	ResultSet rs=ad.selectQuery(draftData);
	while(rs.next())
	{
		reqmon=rs.getString("mon");
		reqyear=rs.getString("year");
		reqmonname=rs.getString("monname");
		if(rs.getInt("cancelflag")==0)
		{
			request.setAttribute("cancelbutton", "cancelbutton");
		}
		
		ondutyForm.setOnDutyType(rs.getString("onDuty_Type"));
		ondutyForm.setLocationId(rs.getString("location"));
		ondutyForm.setStartTime(rs.getString("startTime"));
		ondutyForm.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
		reqstart=rs.getString("startday");
		reqend=rs.getString("enday");
		reqstart1=display1(rs.getDate("start_date"));
		reqend1=display1(rs.getDate("end_date"));
		ondutyForm.setEndTime(rs.getString("endTime"));
		ondutyForm.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
		ondutyForm.setNoOfDays(rs.getString("no_of_days"));
		int approveStatus=rs.getInt("OnDuty_status");
		String status="";
		if(approveStatus==0)
		{
			status="In Process";
		}
		if(approveStatus==1)
		{
			status="Approved";
		}
		if(approveStatus==2)
		{
			status="Rejected";
		}
		if(approveStatus==3)
		{
			status="Cancelled";
		}
		ondutyForm.setStatus(status);
		String approveDate="";
		if(status.equalsIgnoreCase("Approved"))
		{
			approveDate=rs.getString("approved_date");
		}
		if(status.equalsIgnoreCase("Rejected")|| status.equalsIgnoreCase("Cancelled"))
		{
			approveDate=rs.getString("rejected_date");
		}
		if(!(approveDate.equalsIgnoreCase("")))
		{
			String a[]=approveDate.split(" ");
			approveDate=a[0];
			String b[]=approveDate.split("-");
			approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
			ondutyForm.setApproveDate(approveDate);
		}
		ondutyForm.setReason(rs.getString("reason"));
		ondutyForm.setRemark(rs.getString("comments"));
	}			
	String sql1="select * from onDuty_emp_documents where request_no='"+ondutyForm.getRequestNumber()+"' and user_id='"+user.getId()+"' ";
	ResultSet rs1=ad.selectQuery(sql1);
	OnDutyForm leaveForm1=null;
		ArrayList a1=new ArrayList();
		while(rs1.next()) {
			leaveForm1=new OnDutyForm();
			leaveForm1.setFileName(rs1.getString("file_name"));
			leaveForm1.setId(rs1.getString("id"));
			a1.add(leaveForm1);
		}
		request.setAttribute("documentDetails", a1);
		OnDutyAction lA = new OnDutyAction();
		LinkedList appList= lA.viewOnduty(mapping, form, request, response);
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	if(true)
	{
		//Attendance

		SAPAttendenceDAO dao = new SAPAttendenceDAO();

		String month=reqyear+reqmon+"01";



		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date datenow = new Date();
		String time = sdf.format(datenow);

		String today[]=time.split("-");

		int datetoday=Integer.parseInt(today[2]);
		String monthtoday=today[1];
		String monName=monthtoday;

		if (monName.equals("01"))
			monName = "Jan";
		if (monName.equals("02"))
			monName = "Feb";
		if (monName.equals("03"))
			monName = "Mar";
		if (monName.equals("04"))
			monName = "Apr";
		if (monName.equals("05"))
			monName = "May";
		if (monName.equals("06"))
			monName = "Jun";
		if (monName.equals("07"))
			monName = "Jul";
		if (monName.equals("08"))
			monName = "Aug";
		if (monName.equals("09"))
			monName = "Sep";
		if (monName.equals("10"))
			monName = "Oct";
		if (monName.equals("11"))
			monName = "Nov";
		if (monName.equals("12"))
			monName = "Dec";



		LinkedList attDataList = new LinkedList();
		Calendar cal = Calendar.getInstance();



		String calendarmon=monName;
		String mon=month.substring(0, 3);
		String year = month.substring(month.length() - 4, month.length());

		/*
		 * String reqyear=month.substring(month.length()-2, month.length());
		 * String reqmonth=monName+reqyear; AttendenceForm attendenceForm = new
		 * AttendenceForm(); attendenceForm.setEmpcode(empNo);
		 * attendenceForm.setDat(reqmonth); AttendenceAction a4=new
		 * AttendenceAction(); if(monName.equalsIgnoreCase("Nov")) {
		 * a4.submit(mapping, attendenceForm, request, response); return
		 * mapping.findForward("display"); }
		 */




		if (monName.equals("Jan"))
			monName = "01";
		if (monName.equals("Feb"))
			monName = "02";
		if (monName.equals("Mar"))
			monName = "03";
		if (monName.equals("Apr"))
			monName = "04";
		if (monName.equals("May"))
			monName = "05";
		if (monName.equals("Jun"))
			monName = "06";
		if (monName.equals("Jul"))
			monName = "07";
		if (monName.equals("Aug"))
			monName = "08";
		if (monName.equals("Sep"))
			monName = "09";
		if (monName.equals("Oct"))
			monName = "10";
		if (monName.equals("Nov"))
			monName = "11";
		if (monName.equals("Dec"))
			monName = "12";

		String date ="";	




		ArrayList att = new ArrayList();

		cal.set(Integer.parseInt(year), Integer.parseInt(monName)-1, 1);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		int i=1;
		/*    String query="EXEC daily_report_status_Att '"+days+"','"+monName+"','"+year+"','"+empNo+"' ";
		int k=ad.SqlExecuteUpdate(query);*/

			String s9 = " select date,convert(varchar(11),date,106) as date1,left(DATENAME(WEEKDAY,date),3) as day,left(In_time,5) as intime,left(out_time,5) as outtime,"
					+ "left(status,2) as instatus,right(status,2) as outstatus,shift from Report_Daily_wise where Pernr='"+user.getEmployeeNo()+"'  and year(date)='"+reqyear+"' "
							+ "and date between '"+reqstart1+"' and '"+reqend1+"' order by date";
			ResultSet rs9 = ad.selectQuery(s9);
			try {
				while(rs9.next())
				{
					
					SAPAttendenceForm help = new SAPAttendenceForm();
					help.setDate(rs9.getString("date1"));
					help.setDay(rs9.getString("day"));
					date=rs9.getString("date");
					
					if(!(rs9.getString("intime").equalsIgnoreCase("00:00") && rs9.getString("instatus").equalsIgnoreCase("AA")))
		    			help.setiNTIME(rs9.getString("intime"));


			    	if(!(rs9.getString("outtime").equalsIgnoreCase("00:00") && rs9.getString("outstatus").equalsIgnoreCase("AA")))
					help.setoUTTIME(rs9.getString("outtime"));

			    	if(monName.equalsIgnoreCase(monthtoday))
			    	{
		            if(datetoday>=i)
		            {
		            	help.setiNSTATUS(rs9.getString("instatus"));
		            	if(datetoday==i)
		            	{
		            		if(rs9.getString("outstatus").equalsIgnoreCase("AA"))
		            		{
		            			help.setoUTSTATUS("");
		            		}
		            		else
		            		{
		            			help.setoUTSTATUS(rs9.getString("outstatus"));
		            		}
		            	}
		            	else
		            	{
		            		help.setoUTSTATUS(rs9.getString("outstatus"));
		            	}
						
						
						switch(rs9.getString("shift")){
						  case "0001": help.setShift("GS");
						  break;
						  case "0002": help.setShift("GS");
						  break;
						  case "0003": help.setShift("FS");
						  break;
						  case "0004": help.setShift("SS");
						  break;
						  case "0005": help.setShift("TS");
						  break;
						  case "0006": help.setShift("SS");
						  break;
						  case "0007": help.setShift("FS");
						  break;
						  case "0008": help.setShift("SS");
						  break;
						  case "0009": help.setShift("TS");
						  break;
						  case "0010": help.setShift("NS");
						  break;
						  case "0011": help.setShift("SS");
						  break;
						  case "0012": help.setShift("Engg B");
						  break;
						  case "0013": help.setShift("Engg C");
						  break;
						  case "0014": help.setShift("Sec A");
						  break;
						  case "0015": help.setShift("Sec B");
						  break;
						  case "0016": help.setShift("Sec C");
						  break;

					}
					


				
		                	
		            }
		            else
			    	{
			    		
		            	help.setiNSTATUS("");
						help.setoUTSTATUS("");	
		                  
			    	}
		            
		    	}
			    	else
			    	{
			    		
			    		help.setiNSTATUS(rs9.getString("instatus"));
						help.setoUTSTATUS(rs9.getString("outstatus"));
						
						switch(rs9.getString("shift")){
						  case "0001": help.setShift("GS");
						  break;
						  case "0002": help.setShift("GS");
						  break;
						  case "0003": help.setShift("FS");
						  break;
						  case "0004": help.setShift("SS");
						  break;
						  case "0005": help.setShift("TS");
						  break;
						  case "0006": help.setShift("SS");
						  break;
						  case "0007": help.setShift("FS");
						  break;
						  case "0008": help.setShift("SS");
						  break;
						  case "0009": help.setShift("TS");
						  break;
						  case "0010": help.setShift("NS");
						  break;
						  case "0011": help.setShift("SS");
						  break;
						  case "0012": help.setShift("Engg B");
						  break;
						  case "0013": help.setShift("Engg B");
						  break;
						  case "0014": help.setShift("Sec A");
						  break;
						  case "0015": help.setShift("Sec B");
						  break;
						  case "0016": help.setShift("Sec C");
						  break;

								

					}
					            
			    	}
				   

			    	String data = "select Approvel_Status,reason from leave_details where  user_id='"
							+ user.getEmployeeNo()
							+ "' and Approvel_Status in('Approved','Pending') and record_status!='Draft'  and '"
							+  date 
							+ "' between start_date and  end_date and YEAR(start_date)='"
							+ year + "'";
					ResultSet rs10 = ad.selectQuery(data);
					while (rs10.next()) {
						if (rs10.getString("Approvel_Status").equalsIgnoreCase(
								"Approved"))
							help.setRemarks(rs10.getString("reason"));
						else
							help.setRemarks("Leave to be Approved");
					}

					String data1 = "select Approver_Status,reason from OnDuty_details where  user_id='"
							+user.getEmployeeNo()
							+ "' and Approver_Status in('Approved','In Process') and '"
							+ date
							+ "' between start_date and  end_date  and YEAR(start_date)='"
							+ year + "'";
					ResultSet rs101 = ad.selectQuery(data1);
					while (rs101.next()) {
						if (rs101.getString("Approver_Status")
								.equalsIgnoreCase("Approved"))
							help.setRemarks(rs101.getString("reason"));
						else
							help.setRemarks("Onduty to be Approved");
					} 


					



					String data11 = "select * from holidays where date = '"+date+"' and location = '"+user.getPlantId()+"' ";

					ResultSet rs1011 = ad.selectQuery(data11);
					try {
						while (rs1011.next()) {

								help.setRemarks(rs1011.getString("Holiday_Name"));
								help.setMessage("HOL");
								

						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				i++;
				ondutyForm.setLinkName("Apply");
				attDataList.add(help);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (attDataList.size() > 0) {
				request.setAttribute("attDataList", attDataList);
			}
}
	else
	{
		//Attendance

		SAPAttendenceDAO dao = new SAPAttendenceDAO();
		try
				{
		connectSAP();
		JCoDestination destination = JCoDestinationManager
				.getDestination(DESTINATION);// TODO change to real
												// destination
		if (destination == null) {

			destination = JCoDestinationManager
					.getDestination(DESTINATION);// TODO change to
													// real
													// destination
			if (destination == null) {
				throw new RuntimeException(
						"Could not connect to SAP, destination not found.");
			}
		}

		String month=reqyear+reqmon+"01";
		JCoFunction function = null;
		String pay="";
		String getPayGroup = "Select PAY_GROUP from emp_official_info where PERNR='"
				+ user.getEmployeeNo() + "'";
		ResultSet rsPayGroup = ad.selectQuery(getPayGroup);
		try {
			while (rsPayGroup.next()) {
				pay=rsPayGroup.getString("PAY_GROUP");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		function = destination.getRepository().getFunction(
				"ZBAPI_HR_ATTENDANCE");
		if (function == null) {
			throw new RuntimeException(
					" ZBAPI_HR_ATTENDANCE not found in SAP.");// ZBAPI_HR_PAYSLIP
		}
		function.getImportParameterList().setValue("PAYGROUP",pay);
		function.getImportParameterList().setValue("PERNR", user.getEmployeeNo());
		function.getImportParameterList().setValue("MONTH", month);

		function.execute(destination);
		LinkedList attdList = new LinkedList();
		JCoTable returnTable = function.getTableParameterList()
				.getTable("ATTNDATA");// PAYDATA_D_H
		Map<Integer, Character> returnMap = new HashMap<Integer, Character>();
		if (returnTable.getNumRows() > 0) {
			returnTable.firstRow();
			do {
				String reqDate = convertDate(returnTable
						.getDate("BEGDA"));
				String inTime = returnTable.getTime("INTIME")
						.toString();
				String a1[] = inTime.split(" ");
				if (a1.length == 6)
					inTime = a1[3];
				String outTime = returnTable.getTime("OUTTIME")
						.toString();
				String b1[] = outTime.split(" ");
				if (b1.length == 6)
					outTime = b1[3];

				SAPAttendenceForm attData = new SAPAttendenceForm();
				attData.setDate(reqDate);
				attData.setiNTIME(returnTable.getString("INTIME"));
				attData.setiNSTATUS(returnTable.getString("INSTATUS"));
				attData.setoUTTIME(returnTable.getString("OUTTIME"));
				attData.setoUTSTATUS(returnTable.getString("OUTSTATUS"));
				attdList.add(attData);

			} while (returnTable.nextRow());

			String deleteRecords = "delete from SAP_Attendence where PERNR='"
					+ user.getEmployeeNo() + "'  ";
			ad.SqlExecuteUpdate(deleteRecords);

			dao.updateAttendenceDetails(attdList, user.getEmployeeNo());

		}

		if (returnTable.getNumRows() == 0) {

			JCoTable returnStatus = function.getTableParameterList()
					.getTable("RETURN");
			if (returnStatus.getNumRows() > 0) {
				char c = returnStatus.getChar("TYPE");
				if (c == 'E') {
					ondutyForm.setMessage(returnStatus
							.getString("MESSAGE"));
				}
			}

		}

				}catch (Exception e) {
					e.printStackTrace();
				}

		//DATA SET

		LinkedList attDataList = new LinkedList();
		String getAttendence = "select convert(varchar(11),s.[BEGDA],106) as date,BEGDA as rdate,DATENAME(WEEKDAY,S.[BEGDA]) as day,convert(varchar(5),s.[INTIME],"
				+ "106) as INTIME,s.INSTATUS,convert(varchar(5),s.[OUTTIME],106) as OUTTIME,s.OUTSTATUS,(SELECT Holiday_Name FROM holidays WHERE Location='"
				+ user.getPlantId()
				+ "' "
				+ "AND convert(varchar(11),s.[BEGDA],103)=convert(varchar(11),Date,103) ) as holiday from SAP_Attendence as s where s.PERNR='"
				+ user.getEmployeeNo()
				+ "' and"
				+ " MONTH(s.[BEGDA])='"
				+ reqmon
				+ "' and BEGDA between '"+reqstart1+"' and '"+reqend1+"' order by date";
		ResultSet rsAttend = ad.selectQuery(getAttendence);

		try {
			while (rsAttend.next()) {	
				SAPAttendenceForm attData = new SAPAttendenceForm();
			Date today = new Date();
			String date = today.toString();
			String a[] = date.split(" ");
			date = a[2] + " " + a[1] + " " + a[5];
			attData.setDate(rsAttend.getString("date"));
			attData.setDay(rsAttend.getString("day").substring(0, 3));
			
			/* if(!rsAttend.getString("INSTATUS").equalsIgnoreCase("")) 
					attData.setiNTIME(rsAttend.getString("INTIME"));
			if (!(rsAttend.getString("INSTATUS").equalsIgnoreCase("PP")|| rsAttend.getString("INSTATUS").equalsIgnoreCase("AA")|| rsAttend.getString("INSTATUS").equalsIgnoreCase("TI") || rsAttend.getString("INSTATUS").equalsIgnoreCase("TO")))
				attData.setiNTIME(rsAttend.getString("INSTATUS"));	
			if((rsAttend.getString("INSTATUS").equalsIgnoreCase("PP")|| rsAttend.getString("INSTATUS").equalsIgnoreCase("AA")|| rsAttend.getString("INSTATUS").equalsIgnoreCase("TI") || rsAttend.getString("INSTATUS").equalsIgnoreCase("TO")))
				attData.setiNTIME(rsAttend.getString("INTIME"));*/
			attData.setiNSTATUS(rsAttend.getString("INSTATUS"));	
			attData.setiNTIME(rsAttend.getString("INTIME"));	
			attData.setoUTTIME(rsAttend.getString("OUTTIME"));
			attData.setoUTSTATUS(rsAttend.getString("OUTSTATUS"));
			
			/* if(!rsAttend.getString("OUTSTATUS").equalsIgnoreCase("")) 
					attData.setoUTTIME(rsAttend.getString("OUTTIME"));
			
			if (!(rsAttend.getString("OUTSTATUS")
					.equalsIgnoreCase("PP")
					|| rsAttend.getString("OUTSTATUS")
							.equalsIgnoreCase("AA")
					|| rsAttend.getString("OUTSTATUS")
							.equalsIgnoreCase("TI") || rsAttend
					.getString("OUTSTATUS").equalsIgnoreCase("TO")))
				attData.setoUTTIME(rsAttend.getString("OUTSTATUS"));
			if((rsAttend.getString("OUTSTATUS")
					.equalsIgnoreCase("PP")
					|| rsAttend.getString("OUTSTATUS")
							.equalsIgnoreCase("AA")
					|| rsAttend.getString("OUTSTATUS")
							.equalsIgnoreCase("TI") || rsAttend
					.getString("OUTSTATUS").equalsIgnoreCase("TO")))
				attData.setoUTTIME(rsAttend.getString("OUTTIME"));
			*/

			String remark = rsAttend.getString("holiday");
			if (remark != null) {
				attData.setRemarks(remark);
				attData.setMessage("HOL");
			}

			String data = "select * from leave_details where  user_id='"
					+ user.getEmployeeNo()
					+ "' and Approvel_Status in('Approved','Pending') and record_status!='Draft'  and '"
					+ rsAttend.getString("rdate")
					+ "' between start_date and  end_date and YEAR(start_date)='"
					+ reqyear + "'";
			ResultSet rs10 = ad.selectQuery(data);
			while (rs10.next()) {
				if (rs10.getString("Approvel_Status").equalsIgnoreCase(
						"Approved"))
					attData.setRemarks(rs10.getString("reason"));
				else
					attData.setRemarks("Leave to be Approved");
			}

			String data1 = "select * from OnDuty_details where  user_id='"
					+  user.getEmployeeNo()
					+ "' and Approver_Status in('Approved','In Process') and '"
					+ rsAttend.getString("rdate")
					+ "' between start_date and  end_date  and YEAR(start_date)='"
					+ reqyear + "'";
			ResultSet rs101 = ad.selectQuery(data1);
			while (rs101.next()) {
				if (rs101.getString("Approver_Status")
						.equalsIgnoreCase("Approved"))
					attData.setRemarks(rs101.getString("reason"));
				else
					attData.setRemarks("Onduty to be Approved");
			}
			
			//chk whether he can apply
			
			
			if(rsAttend.getString("INSTATUS").equalsIgnoreCase("OD")|| rsAttend.getString("INSTATUS").equalsIgnoreCase("LP")|| rsAttend.getString("INSTATUS").equalsIgnoreCase("CL") || rsAttend.getString("INSTATUS").equalsIgnoreCase("SL")  || rsAttend.getString("INSTATUS").equalsIgnoreCase("EL")  || rsAttend.getString("INSTATUS").equalsIgnoreCase("ML") || rsAttend.getString("INSTATUS").equalsIgnoreCase("SH"))
			{
		    ondutyForm.setLinkName("Apply");
			}
			if(rsAttend.getString("OUTSTATUS").equalsIgnoreCase("OD")|| rsAttend.getString("OUTSTATUS").equalsIgnoreCase("LP")|| rsAttend.getString("OUTSTATUS").equalsIgnoreCase("CL") || rsAttend.getString("OUTSTATUS").equalsIgnoreCase("SL")  || rsAttend.getString("OUTSTATUS").equalsIgnoreCase("EL")  || rsAttend.getString("OUTSTATUS").equalsIgnoreCase("ML") || rsAttend.getString("OUTSTATUS").equalsIgnoreCase("SH"))
			{
				ondutyForm.setLinkName("Apply");
			}
			
			attDataList.add(attData);
			}
			if (attDataList.size() > 0) {
				request.setAttribute("attDataList", attDataList);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	return mapping.findForward("cancelonDutydetails");
	}
	
	public ActionForward displayOthersondutyReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		
		String view=request.getParameter("param"); 
		
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
	
		String empno = ondutyForm.getEmpno();
		if(empno==null)
		{
			empno=user.getEmployeeNo();
		}
		if(empno.equalsIgnoreCase(""))
		{
			empno=user.getEmployeeNo();
		}
		
		 String FromDate=ondutyForm.getStartDate();
		 String ToDate=ondutyForm.getEndDate();
		 
		 String a1[]=FromDate.split("/");
		 FromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
		
		String b1[]=ToDate.split("/");
		ToDate=b1[2]+"-"+b1[1]+"-"+b1[0];
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		ondutyForm.setEmployeeNumber(empno);
		ondutyForm.setYear(Integer.toString(year));
		 if(!empno.equalsIgnoreCase("All"))		
		 {
		String emp = "Select dep.DPTSTXT,desg.DSGSTXT,emp.DOJ ,emp.EMP_FULLNAME from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg where PERNR='"
				+ empno
				+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID ";

		ResultSet rs1 = ad.selectQuery(emp);

		try {
			while (rs1.next())

			{
				ondutyForm.setDepartment(rs1.getString("DPTSTXT"));
				ondutyForm.setDesignation(rs1.getString("DSGSTXT"));
				ondutyForm.setDoj(EMicroUtils.display1(rs1.getDate("DOJ")));
				ondutyForm.setEmployeeName(rs1.getString("EMP_FULLNAME"));
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		 }
		ArrayList empIdList=new ArrayList();
		ArrayList empLabelList=new ArrayList();
		ArrayList yearList=new ArrayList();
		String isapprover = "select employeeNumber , employeeNumber+'-'+emp_official_info.EMP_FULLNAME as emplabel from ESS_Approvers , emp_official_info"
							 +" where ESS_Approvers.employeeNumber = emp_official_info.PERNR and (ESS_Approvers.ApproverId = '"+user.getEmployeeNo()+"' or Parallel_Approver1 = '"+user.getEmployeeNo()+"' or Parallel_Approver2 = '"+user.getEmployeeNo()+"') and Active='1' "  ;
		ResultSet rs2 = ad.selectQuery(isapprover);
		
		try {
			while (rs2.next())

			{
				empIdList.add(rs2.getString("employeeNumber"));
				empLabelList.add(rs2.getString("emplabel"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		ondutyForm.setEmplIdList(empIdList);
		ondutyForm.setEmpLabelList(empLabelList);
		if (empIdList.size() > 0) {
			request.setAttribute("Approver", "Approver");
		}
		
		 ArrayList ondutyList=new ArrayList();	
		 String sql="";
		 if(!empno.equalsIgnoreCase("All"))		
		   sql="Select od.*,emp_official_info.emp_fullname From OnDuty_details od,emp_official_info where user_id='"+empno+"' and od.user_id=emp_official_info.pernr and od.approver_status='Approved'  and (od.start_date between '"+FromDate+"' and '"+ToDate+"')  order by start_date desc";
		 else
			 sql="Select od.*,emp_official_info.emp_fullname From OnDuty_details od,emp_official_info where user_id in ((select employeeNumber  from ESS_Approvers , emp_official_info where ESS_Approvers.employeeNumber = emp_official_info.PERNR and (ESS_Approvers.ApproverId = '"+user.getEmployeeNo()+"' or Parallel_Approver1 = '"+user.getEmployeeNo()+"' or Parallel_Approver2 = '"+user.getEmployeeNo()+"') and Active='1' )) and od.user_id=emp_official_info.pernr and od.approver_status='Approved'  and (od.start_date between '"+FromDate+"' and '"+ToDate+"')"
			 		+ "union"
			 		+ " Select od.*,emp_official_info.emp_fullname From OnDuty_details od,emp_official_info where user_id in ('"+user.getEmployeeNo()+"') and od.user_id=emp_official_info.pernr and od.approver_status='Approved'  and (od.start_date between '"+FromDate+"' and '"+ToDate+"')  order by start_date desc";
		 
		 ResultSet rs=ad.selectQuery(sql);
					try {
						while (rs.next()) {
							OnDutyForm	ondutyForm1=new OnDutyForm();
							ondutyForm1.setRequestNumber(rs.getInt("request_no"));
							ondutyForm1.setSubmitDate(rs.getString("submit_date"));
							ondutyForm1.setEmployeeName(rs.getString("emp_fullname"));
							String startDate=rs.getString("start_date");
							String a[]=startDate.split(" ");
							startDate=a[0];
							String b[]=startDate.split("-");
							startDate=b[2]+"/"+b[1]+"/"+b[0];
							ondutyForm1.setStartDate(startDate);
							ondutyForm1.setLocationId(rs.getString("location"));
							ondutyForm1.setStartTime(rs.getString("startTime"));
							ondutyForm1.setEndTime(rs.getString("endTime"));
							ondutyForm1.setRequestType(rs.getString("record_status"));
							String endDate=rs.getString("end_date");
							String c[]=endDate.split(" ");
							endDate=c[0];
							String d[]=endDate.split("-");
							endDate=d[2]+"/"+d[1]+"/"+d[0];
							ondutyForm1.setEndDate(endDate);
						
							ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
							ondutyForm1.setLocationId(rs.getString("location"));
							ondutyForm1.setReason(rs.getString("reason"));
						
							ondutyList.add(ondutyForm1);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("ondutyList", ondutyList);
				
					if(ondutyList.size()==0)
					{
						request.setAttribute("noRecords", "noRecords");
						ondutyForm.setMessage("No records are found");
					}
		
					if(!view.equalsIgnoreCase("Excel"))
		return mapping.findForward("displayondutyreport");
					else
						{
						Date dNow = new Date( );
						SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
						String exportdate=ft.format(dNow);
				
						
						response.setHeader("Content-Disposition", "inline; filename="+exportdate+"_Onduty_Report.xls");
						
						return mapping.findForward("Exportondutyreport");
						}
						
	
		
	}
	
	public ActionForward displayondutyreport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
	
		int year = Calendar.getInstance().get(Calendar.YEAR);
		ondutyForm.setEmployeeName(user.getFullName());
		ondutyForm.setEmployeeNumber(user.getEmployeeNo());
		ondutyForm.setYear(Integer.toString(year));
		String emp = "Select dep.DPTSTXT,desg.DSGSTXT,emp.DOJ from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg where PERNR='"
				+ user.getEmployeeNo()
				+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID ";

		ResultSet rs1 = ad.selectQuery(emp);

		try {
			while (rs1.next())

			{
				ondutyForm.setDepartment(rs1.getString("DPTSTXT"));
				ondutyForm.setDesignation(rs1.getString("DSGSTXT"));
				ondutyForm.setDoj(EMicroUtils.display1(rs1.getDate("DOJ")));
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		ArrayList empIdList=new ArrayList();
		ArrayList empLabelList=new ArrayList();
		ArrayList yearList=new ArrayList();
		String isapprover = "select employeeNumber , employeeNumber+'-'+emp_official_info.EMP_FULLNAME as emplabel from ESS_Approvers , emp_official_info"
							 +" where ESS_Approvers.employeeNumber = emp_official_info.PERNR and (ESS_Approvers.ApproverId = '"+user.getEmployeeNo()+"' or Parallel_Approver1 = '"+user.getEmployeeNo()+"' or Parallel_Approver2 = '"+user.getEmployeeNo()+"') and Active='1' "  ;
		ResultSet rs2 = ad.selectQuery(isapprover);
		
		try {
			while (rs2.next())

			{
				empIdList.add(rs2.getString("employeeNumber"));
				empLabelList.add(rs2.getString("emplabel"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		ondutyForm.setEmplIdList(empIdList);
		ondutyForm.setEmpLabelList(empLabelList);
		if (empIdList.size() > 0) {
			request.setAttribute("Approver", "Approver");
		}
		
		 ArrayList ondutyList=new ArrayList();	
		
		  String sql="Select od.*,emp_official_info.emp_fullname From OnDuty_details od,emp_official_info where od.user_id='"+user.getEmployeeNo()+"' and od.user_id=emp_official_info.pernr  and od.approver_status='Approved'  and year(od.start_date)='"+year+"'  order by start_date desc";
				ResultSet rs=ad.selectQuery(sql);
					try {
						while (rs.next()) {
							OnDutyForm	ondutyForm1=new OnDutyForm();
							ondutyForm1.setRequestNumber(rs.getInt("request_no"));
							ondutyForm1.setSubmitDate(rs.getString("submit_date"));		
							ondutyForm1.setEmployeeName(rs.getString("emp_fullname"));
							
							String startDate=rs.getString("start_date");
							String a[]=startDate.split(" ");
							startDate=a[0];
							String b[]=startDate.split("-");
							startDate=b[2]+"/"+b[1]+"/"+b[0];
							ondutyForm1.setStartDate(startDate);
							ondutyForm1.setLocationId(rs.getString("location"));
							ondutyForm1.setStartTime(rs.getString("startTime"));
							ondutyForm1.setEndTime(rs.getString("endTime"));
							ondutyForm1.setRequestType(rs.getString("record_status"));
							String endDate=rs.getString("end_date");
							String c[]=endDate.split(" ");
							endDate=c[0];
							String d[]=endDate.split("-");
							endDate=d[2]+"/"+d[1]+"/"+d[0];
							ondutyForm1.setEndDate(endDate);
						
							ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
							ondutyForm1.setLocationId(rs.getString("location"));
						
							if(rs.getString("reason").length()>=100)
							ondutyForm1.setReason(rs.getString("reason").substring(0, 100));
							else
								ondutyForm1.setReason(rs.getString("reason"));	
							ondutyList.add(ondutyForm1);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("ondutyList", ondutyList);
				
					if(ondutyList.size()==0)
					{
						request.setAttribute("noRecords", "noRecords");
						ondutyForm.setMessage("No records are found");
					}
		
		return mapping.findForward("displayondutyreport");
	}
	
	
	public ActionForward displayCancelList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 

	{
		
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		  try{
		  String getTotalRecords="select count(*) from OnDuty_details where user_id='"+user.getEmployeeNo()+"' and cancelflag=0 and Approver_Status='Approved'";
		  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
		  while(rsTotalRecods.next())
		  {
			  totalRecords=rsTotalRecods.getInt(1);
		  }
		  if(totalRecords>=10)
		  {
			  ondutyForm.setTotalRecords1(totalRecords);
		  startRecord=1;
		  endRecord=10;
		  ondutyForm.setStartRecord1(1);
		  ondutyForm.setEndRecord1(10);
		  request.setAttribute("displayRecordNo", "displayRecordNo");
		  request.setAttribute("nextButton", "nextButton");
		  }else
		  {
			  startRecord=1;
			  endRecord=totalRecords;
			  ondutyForm.setTotalRecords1(totalRecords);
			  ondutyForm.setStartRecord1(1);
			  ondutyForm.setEndRecord1(totalRecords); 
		  }			
		  ArrayList ondutyList=new ArrayList();	
		  OnDutyForm ondutyForm1=null;
		  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID desc) AS RowNum,request_no,onDuty_Type,location,start_date,startTime,end_date,endTime,no_of_days,OnDuty_status,record_status " +
			"from OnDuty_details where user_id='"+user.getEmployeeNo()+"'  and cancelflag=0 and Approver_Status='Approved' and DATEDIFF(day,end_date,GETDATE())<60 ) as sub Where  sub.RowNum between 1 and 10";
		ResultSet rs=ad.selectQuery(sql);
			while (rs.next()) {
				ondutyForm1=new OnDutyForm();
				ondutyForm1.setRequestNumber(rs.getInt("request_no"));
				
				String startDate=rs.getString("start_date");
				String a[]=startDate.split(" ");
				startDate=a[0];
				String b[]=startDate.split("-");
				startDate=b[2]+"/"+b[1]+"/"+b[0];
				ondutyForm1.setStartDate(startDate);
				ondutyForm1.setLocationId(rs.getString("location"));
				ondutyForm1.setStartTime(rs.getString("startTime"));
				ondutyForm1.setEndTime(rs.getString("endTime"));
				ondutyForm1.setRequestType(rs.getString("record_status"));
				String endDate=rs.getString("end_date");
				String c[]=endDate.split(" ");
				endDate=c[0];
				String d[]=endDate.split("-");
				endDate=d[2]+"/"+d[1]+"/"+d[0];
				ondutyForm1.setEndDate(endDate);
			
				ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
				ondutyForm1.setLocationId(rs.getString("location"));
				
				int OnDuty_status=rs.getInt("OnDuty_status");
				String status="";
				if(OnDuty_status==0)
				{
					status="In Process";
				}
				if(OnDuty_status==1)
				{
					status="Approved";
				}
				if(OnDuty_status==2)
				{
					status="Rejected";
				}
				if(OnDuty_status==3)
				{
					status="Cancelled";
				}
				if(OnDuty_status==4)
				{
					status="Self Cancelled";
				}
				if(OnDuty_status==5)
				{
					status="HR Rejected";
				}
				ondutyForm1.setStatus(status);
				ondutyList.add(ondutyForm1);
			}
			request.setAttribute("ondutyList", ondutyList);
			request.setAttribute("disablePreviousButton","disablePreviousButton");
			if(ondutyList.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				ondutyForm.setMessage("No records are found");
			}
			System.out.println(ondutyForm.getTotalRecords1());
			System.out.println(ondutyForm.getStartRecord1());
			System.out.println(ondutyForm.getEndRecord1());
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		return mapping.findForward("cancelonDutyList");
		
		
		
	}
	
	
	public ActionForward cancelRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		OnDutyForm leaveForm=(OnDutyForm)form;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String requestno=request.getParameter("reqno");
		synchronized (this) {
		String modifyRequest="update All_Request set Req_Status='Self Cancelled'  where Req_Type='On Duty' and Req_Id='"+requestno+"'";	
		int statsChange=ad.SqlExecuteUpdate(modifyRequest);	
		if(statsChange>0){
		leaveForm.setMessage2("Request has been cancelled successfully");
		 
		String updateSql= "update OnDuty_details set Approver_Status='Self Cancelled',OnDuty_status='4'  where request_no='"+requestno+"'";
		int ii=ad.SqlExecuteUpdate(updateSql);
		}
		}
		
		
		return displayOnDutyRequests(mapping, leaveForm, request, response);

		
	}
	
	
	public LinkedList getApproverDetails(String OldrequestNo, ActionForm form, HttpServletRequest request, String pApprover)
	{
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList approverList=new LinkedList();
	
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
		 String dateNow = ft.format(dNow);
		 
		OnDutyForm leaveForm=(OnDutyForm)form;
		
		String saveRecReq="";
		boolean existStatus=false;
		String reqType="";
		String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' group by reqType";
		ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
		try {
			while(rsCheckInEss.next())
			{
				int number=rsCheckInEss.getInt(1);
				reqType=rsCheckInEss.getString("reqType");
				if(number==0)
				{
					existStatus=false;
				}else{
					existStatus=true;
				}
			}
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
		synchronized (this) {
		if(existStatus==true){
			if(reqType.equalsIgnoreCase("sequential"))
			{
			String getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
			ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
				try {
					while(rsApproverDsg.next())
					{
						pApprover=rsApproverDsg.getString("ApproverId");
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				int i=0;
				 saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
				saveRecReq = saveRecReq + "'"+OldrequestNo+"','On Duty','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
				 i=ad.SqlExecuteUpdate(saveRecReq);
				 if(i > 0){
						leaveForm.setMessage("Onduty Details Submitted Successfully");
					}
			}
			if(reqType.equalsIgnoreCase("parallel"))
			{
				String parallelApprover1="";
				String parallelApprover2="";
				String getApproverDesig="select * from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
				ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
					try {
						while(rsApproverDsg.next())
						{
							pApprover=rsApproverDsg.getString("ApproverId");
							parallelApprover1=rsApproverDsg.getString("Parallel_Approver1");
							parallelApprover2=rsApproverDsg.getString("Parallel_Approver2");
							
						}
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					int i=0;
				 saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
					saveRecReq = saveRecReq + "'"+OldrequestNo+"','On Duty','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
					 i=ad.SqlExecuteUpdate(saveRecReq);
					leaveForm.setMessage("Onduty Details Submitted Successfully");
					if(!(parallelApprover1.equalsIgnoreCase("")))
					{
						saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
						saveRecReq = saveRecReq + "'"+OldrequestNo+"','On Duty','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+parallelApprover1+"','No','"+user.getEmployeeNo()+"','')";
						 i=ad.SqlExecuteUpdate(saveRecReq);
					}
					if(!(parallelApprover2.equalsIgnoreCase("")))
					{
						saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
						saveRecReq = saveRecReq + "'"+OldrequestNo+"','On Duty','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+parallelApprover2+"','No','"+user.getEmployeeNo()+"','')";
						 i=ad.SqlExecuteUpdate(saveRecReq);
					}
					
			}
		}
		int i=0;
		if(existStatus==false){
	
		 saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
		saveRecReq = saveRecReq + "'"+OldrequestNo+"','On Duty','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
		 i=ad.SqlExecuteUpdate(saveRecReq);
		leaveForm.setMessage("Onduty Details Submitted Successfully");
		}
		
		
		
		}
		return approverList;
	}
	
	
	
	public LinkedList getApprovers(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList approverList=new LinkedList();
		try{
		
		String approverName="";
		String approverID="";
		String reportingMgrID="";
		String reportingMgrName="";
		String appDesig="";
		String reportMgrDesg="";
		String getApprovers="select APPMGR,RPTMGR  from emp_official_info  emp where " +
				"PERNR='"+user.getEmployeeNo()+"'  ";	
		ResultSet rs=ad.selectQuery(getApprovers);
		while(rs.next())
		{
			approverID=rs.getString("APPMGR");
			reportingMgrID=rs.getString("RPTMGR");
		}
		boolean existStatus=false;
		String reqType="";
		String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' group by reqType";
		ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
		while(rsCheckInEss.next())
		{
			int number=rsCheckInEss.getInt(1);
			reqType=rsCheckInEss.getString("reqType");
			if(number==0)
			{
				existStatus=false;
			}else{
				existStatus=true;
			}
		}
		if(existStatus==true){
			if(reqType.equalsIgnoreCase("sequential"))
			{
			String getApproverDesig="select e.ApproverId,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
					"where employeeNumber='"+user.getEmployeeNo()+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave' order by Priority ";
			ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
				while(rsApproverDsg.next())
				{
					LeaveForm leaveForm1=new LeaveForm();
					leaveForm1.setApptype("Approving Manager");
					leaveForm1.setApproverID(rsApproverDsg.getString("ApproverId"));
					appDesig=rsApproverDsg.getString("DSGSTXT");
					approverName=rsApproverDsg.getString("EMP_FULLNAME");
					leaveForm1.setAppDesig(appDesig);
					leaveForm1.setApproverName(approverName);
					approverList.add(leaveForm1);
				}
			}
			if(reqType.equalsIgnoreCase("parallel"))
			{
			String getApproverDesig="select e.ApproverId,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
					"where employeeNumber='"+user.getEmployeeNo()+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and e.essType='Leave'";
			ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
				while(rsApproverDsg.next())
				{
					LeaveForm leaveForm1=new LeaveForm();
					leaveForm1.setApptype("Approving Manager");
					leaveForm1.setApproverID(rsApproverDsg.getString("ApproverId"));
					appDesig=rsApproverDsg.getString("DSGSTXT");
					approverName=rsApproverDsg.getString("EMP_FULLNAME");
					leaveForm1.setAppDesig(appDesig);
					leaveForm1.setApproverName(approverName);
					approverList.add(leaveForm1);
				}
				String getParallelApprover1="select e.Parallel_Approver1,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp," +
						"DESIGNATION as desg  where employeeNumber='"+user.getEmployeeNo()+"' and  e.Parallel_Approver1=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave'";
				rsApproverDsg=ad.selectQuery(getParallelApprover1);
			while(rsApproverDsg.next())
			{
				LeaveForm leaveForm1=new LeaveForm();
				leaveForm1.setApptype("Approving Manager");
				leaveForm1.setApproverID(rsApproverDsg.getString("Parallel_Approver1"));
				appDesig=rsApproverDsg.getString("DSGSTXT");
				approverName=rsApproverDsg.getString("EMP_FULLNAME");
				leaveForm1.setAppDesig(appDesig);
				leaveForm1.setApproverName(approverName);
				approverList.add(leaveForm1);
			}
			String getParallelApprover2="select e.Parallel_Approver2,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp," +
			"DESIGNATION as desg  where employeeNumber='"+user.getEmployeeNo()+"' and  e.Parallel_Approver2=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave'";
	rsApproverDsg=ad.selectQuery(getParallelApprover2);
	while(rsApproverDsg.next())
	{
		LeaveForm leaveForm1=new LeaveForm();
		leaveForm1.setApptype("Approving Manager");
		leaveForm1.setApproverID(rsApproverDsg.getString("Parallel_Approver2"));
		appDesig=rsApproverDsg.getString("DSGSTXT");
		approverName=rsApproverDsg.getString("EMP_FULLNAME");
		leaveForm1.setAppDesig(appDesig);
		leaveForm1.setApproverName(approverName);
		approverList.add(leaveForm1);
	}	
				
			}
		}
		if(existStatus==false){
		String getApproverDesig="select dept.DPTSTXT,desg.DSGSTXT,emp.EMP_FULLNAME from emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where " +
				" emp.PERNR='"+approverID+"' and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID ";
		ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
		while(rsApproverDsg.next())
		{
			LeaveForm leaveForm1=new LeaveForm();
			leaveForm1.setApptype("Approving Manager");
			leaveForm1.setApproverID(approverID);
			appDesig=rsApproverDsg.getString("DSGSTXT");
			approverName=rsApproverDsg.getString("EMP_FULLNAME");
			leaveForm1.setAppDesig(appDesig);
			leaveForm1.setApproverName(approverName);
			approverList.add(leaveForm1);
		}
		}
		/*String getRepMgrDesig="select dept.DPTSTXT,desg.DSGSTXT,emp.EMP_FULLNAME from emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where " +
				" emp.PERNR='"+reportingMgrID+"' and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID";
		ResultSet rsRepMgrDesig=ad.selectQuery(getRepMgrDesig);
		while(rsRepMgrDesig.next())
		{
			LeaveForm leaveForm2=new LeaveForm();
			leaveForm2.setApptype("Reporting Manager");
			leaveForm2.setApproverID(reportingMgrID);
			reportMgrDesg=rsRepMgrDesig.getString("DSGSTXT");
			reportingMgrName=rsRepMgrDesig.getString("EMP_FULLNAME");
			leaveForm2.setAppDesig(reportMgrDesg);
			leaveForm2.setApproverName(reportingMgrName);
			approverList.add(leaveForm2);
		}*/
		
		/*leaveForm.setApproverID(approverID);
		leaveForm.setApproverName(approverName);
		leaveForm.setAppDesig(appDesig);
		leaveForm.setReportingMgrID(reportingMgrID);
		leaveForm.setReportingMgrName(reportingMgrName);
		leaveForm.setReportMgrDesg(reportMgrDesg);*/
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return approverList;
	}
	
	public ActionForward getReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null)
		{
			ondutyForm.setMessage2("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		
		int requestNo=Integer.parseInt(request.getParameter("RequestNo"));
		String output="OnDuty.pdf";
		String path="onDuty.jrxml";
	    
		String pat3=getServlet().getServletContext().getRealPath("/jasperReports/"+path);
		String pat_out=getServlet().getServletContext().getRealPath("/jasperReports/"+output);
		System.out.println("path of the JRXML File is *********************"+path);
		System.out.println("pat3 of the JRXML File is *********************"+pat3);
		try {
		String path2="onDuty.jasper";
		//String path2="MarksCard.jasper";
		String path1=getServlet().getServletContext().getRealPath("/jasperReports/"+path2);
		System.out.println("Path of the JRXML File is *********************"+path1);
		File reportFile=new File(path1);
		Map parameters = new HashMap();
		
		InputStream input = new FileInputStream(new File(pat3));
		
		JasperDesign design = JRXmlLoader.load(input);
		Map param_map = new HashMap();
		Map param_map2 = new HashMap();
		
		param_map.put("REQUEST_NO", requestNo);
		JasperReport jReport = JasperCompileManager.compileReport(design);
		Connection connection=ConnectionFactory.getConnection();
		JasperPrint jasperPrint = JasperFillManager.fillReport(jReport, param_map, connection);
		 List<JasperPrint> jlist=new ArrayList<JasperPrint>();
		 jlist.add(jasperPrint);
		session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
		 if(jasperPrint!=null)
		  { 
			 //JRHtmlExporterParameter.BETWEEN_PAGES_HTML;
			 FileOutputStream os = new FileOutputStream(pat_out);
			 //JasperExportManager.exportReportToPdfFile(jasperPrint, pat_out);
			 JRPdfExporter exporter = new JRPdfExporter();
			 exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jlist);
			 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
			 //JasperExportManager.exportReportToHtml(jasperPrint,"yy.html");
			   //JasperExportManager.exportReportToPdfFile(jasperPrint,path4 + "/"+"db_stats.pdf"); 
			   // int t=pdfasbytes.length;
			   // System.out.println("Length===="+t);
			 exporter.exportReport();
			 
			 OutputStream servletOutputStream = null;
			 ByteArrayOutputStream htmlStream =new ByteArrayOutputStream();
			 byte[] report=htmlStream.toByteArray();
			 
			 
			 response.reset();
				response.setContentType("application/pdf");
			
			 
			 		int length=0;
					StringBuffer buf=new StringBuffer();
					ServletOutputStream outstream=response.getOutputStream();
					response.setHeader("Content-disposition", "attachment; filename="+pat_out+"");
					//response.setContentLength(pdfasbytes.length);
					FileInputStream input1 = new FileInputStream(pat_out);
		                ServletOutputStream sos = response.getOutputStream();   
		                byte[] bbuf = new byte[4096];   

		              DataInputStream in = new DataInputStream(input1); 
		                
		               while ((in != null) && ((length = in.read(bbuf)) != -1))   
		               {   
		                    sos.write(bbuf,0,length);   
		               }   
		                
		                
		              in.close();   
		                sos.flush();   
					outstream.close();
		  } 
		
	} catch (JRException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	    
	    
	
	
return mapping.findForward("");	

	}
	
	
	public String getTableName(String location)
	{
		String tableName="";
		if(location.equalsIgnoreCase("CBWH"))
			tableName="CBWHCAL";
		if(location.equalsIgnoreCase("ML00"))
				tableName="CmpCal2013";
		if(location.equalsIgnoreCase("ML01"))
			tableName="ML01CAL";
		if(location.equalsIgnoreCase("ML02"))
			tableName="ML02CAL";
		if(location.equalsIgnoreCase("ML03"))
			tableName="ML03CAL";
		if(location.equalsIgnoreCase("ML04"))
			tableName="ML04CAL";
		if(location.equalsIgnoreCase("ML05"))
			tableName="ML05CAL";
		if(location.equalsIgnoreCase("ML06"))
			tableName="ML06CAL";
		if(location.equalsIgnoreCase("ML07"))
			tableName="ML07CAL";
		if(location.equalsIgnoreCase("ML08"))
			tableName="ML08CAL";
		if(location.equalsIgnoreCase("ML09"))
			tableName="ML09CAL";
		if(location.equalsIgnoreCase("ML10"))
			tableName="ML10CAL";
		if(location.equalsIgnoreCase("ML11"))
			tableName="ML11CAL";
		if(location.equalsIgnoreCase("ML12"))
			tableName="ML12CAL";
		if(location.equalsIgnoreCase("ML13"))
			tableName="ML13CAL";
		if(location.equalsIgnoreCase("ML14"))
			tableName="ML14CAL";
		if(location.equalsIgnoreCase("ML15"))
			tableName="ML15CAL";
		if(location.equalsIgnoreCase("ML16"))
			tableName="ML16CAL";
		if(location.equalsIgnoreCase("ML17"))
			tableName="ML17CAL";
		if(location.equalsIgnoreCase("ML18"))
       		tableName="ML18CAL";
		if(location.equalsIgnoreCase("ML19"))
			tableName="ML19CAL";
		if(location.equalsIgnoreCase("ML20"))
			tableName="ML20CAL";
		if(location.equalsIgnoreCase("ML21"))
			tableName="ML21CAL";
		if(location.equalsIgnoreCase("ML22"))
       		tableName="ML22CAL";
		if(location.equalsIgnoreCase("ML23"))
			tableName="ML23CAL";
		if(location.equalsIgnoreCase("ML24"))
			tableName="ML24CAL";
		if(location.equalsIgnoreCase("ML25"))
			tableName="ML25CAL";
		if(location.equalsIgnoreCase("ML26"))
       		tableName="ML26CAL";
		if(location.equalsIgnoreCase("ML51"))
			tableName="ML51CAL";
		if(location.equalsIgnoreCase("ML52"))
			tableName="ML52CAL";
		if(location.equalsIgnoreCase("ML53"))
			tableName="ML53CAL";
		if(location.equalsIgnoreCase("ML54"))
			tableName="ML54CAL";
		if(location.equalsIgnoreCase("ML55"))
			tableName="ML55CAL";
		if(location.equalsIgnoreCase("ML56"))
			tableName="ML56CAL";
		if(location.equalsIgnoreCase("ML57"))
			tableName="ML57CAL";
		if(location.equalsIgnoreCase("ML58"))
			tableName="ML58CAL";
		if(location.equalsIgnoreCase("ML59"))
			tableName="ML59CAL";
		if(location.equalsIgnoreCase("ML60"))
			tableName="ML60CAL";
		if(location.equalsIgnoreCase("ML61"))
			tableName="ML61CAL";
		if(location.equalsIgnoreCase("ML62"))
			tableName="ML62CAL";
		if(location.equalsIgnoreCase("ML63"))
			tableName="ML63CAL";
		if(location.equalsIgnoreCase("ML64"))
			tableName="ML64CAL";
		if(location.equalsIgnoreCase("ML65"))
			tableName="ML65CAL";
		if(location.equalsIgnoreCase("ML66"))
			tableName="ML66CAL";
		if(location.equalsIgnoreCase("ML67"))
			tableName="ML67CAL";
		if(location.equalsIgnoreCase("ML68"))
			tableName="ML68CAL";
		if(location.equalsIgnoreCase("ML90"))
			tableName="ML90CAL";
		if(location.equalsIgnoreCase("ML91"))
			tableName="ML91CAL";
		if(location.equalsIgnoreCase("ML92"))
			tableName="ML92CAL";
		if(location.equalsIgnoreCase("ML27"))
       		tableName="ML27CAL";
	
		return tableName;
	}
	
	public double calculateDays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		String startDate=request.getParameter("StartDate");
		String startDateDuration=request.getParameter("StartDur");
		String endDate=request.getParameter("EndDate");
		String endDurationType=request.getParameter("EndDur");
		String location=request.getParameter("location");
		String tableName=getTableName(location);
		int year = Calendar.getInstance().get(Calendar.YEAR);
	
		 String[] monthName = {"","JAN", "FEB",
				  "MAR", "APR", "MAY", "JUN", "JUL",
				  "AUG", "SEP", "OCT", "NOV",
				  "DEC"
				  };
		 String a[]=startDate.split("/");
		 startDate=a[1];
			
		 String a1[]=endDate.split("/");
		 endDate=a1[1];
		 String startMonth=a[1];
			String EndMonth=a1[1];	
			int staMont=Integer.parseInt(startMonth);
			int endMnth=Integer.parseInt(EndMonth);
		  Calendar cal = Calendar.getInstance();
		  startMonth = monthName[staMont];
		  System.out.println("startMonth name: " + startMonth);
		  EndMonth = monthName[endMnth];
		  System.out.println("EndMonth name: " + EndMonth);
		  double noOfDays=0;
		  try{
			if(startDateDuration.equalsIgnoreCase("FH")||endDurationType.equalsIgnoreCase("SH"))
			{
				String checkDayType="";
				if((startDateDuration.equalsIgnoreCase("FH")&&endDurationType.equalsIgnoreCase("FH"))||(startDateDuration.equalsIgnoreCase("SH")&&endDurationType.equalsIgnoreCase("SH")))
				{
					
					String checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"' and CYEAR='"+year+"'";
					ResultSet rsCheckDay=ad.selectQuery(checkDay);
					while(rsCheckDay.next())
					{
						checkDayType=rsCheckDay.getString(startMonth);
					}
					if((checkDayType.equalsIgnoreCase("W"))&& (startMonth.equalsIgnoreCase(EndMonth)) &&(a[0].equalsIgnoreCase(a1[0])))
						noOfDays=noOfDays+0.5;
				}
			}
			
			if((startDateDuration.equalsIgnoreCase("FD")&&endDurationType.equalsIgnoreCase("FD")))
			{

			    
				Calendar cal1 = new GregorianCalendar();
				 Calendar cal2 = new GregorianCalendar();
				 
				 String startYear=a[2];
				 startYear=startYear.replaceAll(" ", "");
				 String endYear=a1[2];
				 endYear=endYear.replaceAll(" ", "");
				 cal1.set(Integer.parseInt(startYear),staMont, Integer.parseInt(a[0])); 
				 cal2.set(Integer.parseInt(endYear),endMnth, Integer.parseInt(a1[0])); 
				 
					 if(staMont==endMnth)
					 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" ='W' and "+startMonth+" !='' and CYEAR='"+year+"'";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
					 }
					 if(staMont!=endMnth)
					 {
						 
						 
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" ='W' and "+startMonth+" !='' and CYEAR='"+year+"'";
					
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and "+EndMonth+" ='W' and "+startMonth+" !='' and CYEAR='"+year+"'";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
					 } 
					 
				 
			
			}else if(startDateDuration.equalsIgnoreCase("FD")&&(endDurationType.equalsIgnoreCase("FH") )){

				
				 if(staMont==endMnth)
				 {
					String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='WO' and "+startMonth+"!='WS' and "+startMonth+" !='' and CYEAR='"+year+"'";
					ResultSet rsCount=ad.selectQuery(getCountQuery);
					while(rsCount.next())
					{
						noOfDays=rsCount.getInt(1);
					}
					noOfDays=0.5+noOfDays-1;
				
				 }
				 if(staMont!=endMnth)
				 {
					 
					 
					String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" ='W' and "+startMonth+" !='' and CYEAR='"+year+"'";
					ResultSet rsCount=ad.selectQuery(getCountQuery);
					while(rsCount.next())
					{
						noOfDays=rsCount.getInt(1);
					}
					
					String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and "+EndMonth+" ='W' and "+EndMonth+" !='' and CYEAR='"+year+"'";
					ResultSet rsCount1=ad.selectQuery(getCountQuery1);
					while(rsCount1.next())
					{
						noOfDays=noOfDays+rsCount1.getInt(1);
					}
					noOfDays=0.5+noOfDays-1;
					
				 } 
			 
		 
			}else{


				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FH") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=1;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" ='W'  and "+startMonth+" !='' and CYEAR='"+year+"'";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-2;
						
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=1;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" ='W'  and "+startMonth+" !='' and CYEAR='"+year+"'";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and "+EndMonth+"='W'  and "+EndMonth+" !='' and CYEAR='"+year+"'";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays-2;
						
					 } 
					 
				 }
				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FD") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=0.5;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" ='W'  and "+startMonth+" !='' and CYEAR='"+year+"'";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-1;
						
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=0.5;
						 if(Integer.parseInt(a[0])!=31)
						 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" ='W'  and "+startMonth+" !='' and CYEAR='"+year+"'";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1)-1;
						}
						 }
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" ='W'  and "+EndMonth+" !='' and CYEAR='"+year+"'";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays;
						
						
					 } 
					 
				 }
			 
		 
				 
			 
			}
			
			if(a[0].equalsIgnoreCase(a1[0]))
			{
				String checkDayType="";
				String checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"' and CYEAR='"+year+"'";
				ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
					
				}
				if(!(checkDayType.equalsIgnoreCase("W")))
				{
					ondutyForm.setHolidyMessage("Selected date is holiday ");
				}
			}
			
			
			
			
			
		  }catch (Exception e) {
			  e.printStackTrace();
		  }
				  
		ondutyForm.setNoOfDays(Double.toString(noOfDays));
		ArrayList totalDays=new ArrayList();
		LeaveForm leaveDays=new LeaveForm();
		leaveDays.setNoOfDays(Double.toString(noOfDays));
		return noOfDays;
	}
	public LinkedList viewOnduty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String HRid="";
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList approverList=new LinkedList();
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
		 String dateNow = ft.format(dNow);

		int reqId=Integer.parseInt(request.getParameter("requestNo"));
		String rstatus=request.getParameter("status");
		
		String approverName="";
		String approverID="";
		String reportingMgrID="";
		String reportingMgrName="";
		String appDesig="";
		String reportMgrDesg="";
		String getApprovers="select APPMGR,RPTMGR  from emp_official_info  emp where " +
		"PERNR='"+user.getEmployeeNo()+"'  ";	
ResultSet rsApprovers=ad.selectQuery(getApprovers);
try {
	while(rsApprovers.next())
	{
		approverID=rsApprovers.getString("APPMGR");
		reportingMgrID=rsApprovers.getString("RPTMGR");
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
boolean existStatus=false;
String reqType="";

ArrayList paralist=new ArrayList();
String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' group by reqType";
ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
try {
	while(rsCheckInEss.next())
	{
		int number=rsCheckInEss.getInt(1);
		reqType=rsCheckInEss.getString("reqType");
		if(number==0)
		{
			existStatus=false;
		}else{
			existStatus=true;
		}
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
if(existStatus==true){
	if(reqType.equalsIgnoreCase("sequential"))
	{
	String getApproverDesig=" select e.ApproverId,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
"where employeeNumber='"+user.getEmployeeNo()+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave' order by Priority";
	 
	ResultSet rsA=ad.selectQuery(getApproverDesig);
		try {
			while(rsA.next())
			{
				LeaveForm lvform=new LeaveForm();
				 lvform.setApprover(rsA.getString("EMP_FULLNAME"));	
				 lvform.setAppDesig(rsA.getString("DSGSTXT"));	
				String getdetails=" select all_R.Comments,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.approved_date," +
						"all_R.rejected_date from 	All_Request as all_R,emp_official_info as emp, DESIGNATION as desg   " +
						" where all_R.Req_Id='"+reqId+"' and all_R.Req_Type='On Duty' and ((emp.PERNR=all_R.Last_Approver) or " +
						"(emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) and((Last_Approver='"+rsA.getString("ApproverId")+"') or " +
						"(Pending_Approver='"+rsA.getString("ApproverId")+"'))  and Requester_Name='"+user.getEmployeeNo()+"'";
				
				ResultSet rsApproverDsg=ad.selectQuery(getdetails);
				
				while(rsApproverDsg.next())
				{
					HRid=rsApproverDsg.getString("Hr_Id");
					String reqstatus=rsApproverDsg.getString("Req_Status");
					lvform.setStatus(rsApproverDsg.getString("Req_Status"));
					if(reqstatus.equalsIgnoreCase("Pending"))
					{
						
						 lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						 lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
						
					}
					if(reqstatus.equalsIgnoreCase("Approved"))
					{
					lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));
					 lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
				   lvform.setApprovedDate(rsApproverDsg.getString("approved_date"));
					}
					
					if(reqstatus.equalsIgnoreCase("Rejected"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
					}
					if(reqstatus.equalsIgnoreCase("Cancelled"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
					}
					lvform.setComments(rsApproverDsg.getString("Comments"));
					if(HRid!=null)
					{
						if( reqstatus.equalsIgnoreCase("Approved")){
						String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRid+"' and emp.DSGID=des.DSGID";
						
						ResultSet rs=ad.selectQuery(hrdetails);
						
						while(rs.next())
						{
							lvform.setApprover(rs.getString("EMP_FULLNAME"));
							 lvform.setAppDesig(rs.getString("DSGSTXT"));
						}
					}
					}
					
				
				}
				paralist.add(lvform);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		request.setAttribute("onduty", paralist);
		
	}
	if(reqType.equalsIgnoreCase("parallel"))
	{
		
		String getApproverDesig="";
		if(rstatus.equalsIgnoreCase("In process"))
		{
		 getApproverDesig="  select all_R.Comments,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.approved_date,all_R.rejected_date from " +
				"All_Request as all_R,emp_official_info as emp, DESIGNATION as desg    where all_R.Req_Id='"+reqId+"' and all_R.Req_Type='On Duty' and " +
				"((emp.PERNR=all_R.Last_Approver) or (emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) ";
		}else
		{
			 getApproverDesig="  select all_R.Comments,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.approved_date,all_R.rejected_date from " +
						"All_Request as all_R,emp_official_info as emp, DESIGNATION as desg    where all_R.Req_Id='"+reqId+"' and all_R.Req_Type='On Duty' and " +
						"((emp.PERNR=all_R.Last_Approver) or (emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID)  and Actual_Approver=Last_Approver";
		}
		ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
			try {
				while(rsApproverDsg.next())
				{
					LeaveForm lvform=new LeaveForm();
					HRid=rsApproverDsg.getString("Hr_Id");
					String reqstatus=rsApproverDsg.getString("Req_Status");
					lvform.setStatus(rsApproverDsg.getString("Req_Status"));
					if(reqstatus.equalsIgnoreCase("Pending"))
					{
						
					 lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
					 lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));	
						
					}
					if(reqstatus.equalsIgnoreCase("Approved"))
					{
					lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));
					 lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
				   lvform.setApprovedDate(rsApproverDsg.getString("approved_date"));
					}
					
					if(reqstatus.equalsIgnoreCase("Rejected")||reqstatus.equalsIgnoreCase("Cancelled"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
					}
					lvform.setComments(rsApproverDsg.getString("Comments"));
					
					if(HRid!=null)
					{
						if( reqstatus.equalsIgnoreCase("Approved")){
						String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRid+"' and emp.DSGID=des.DSGID";
						
						ResultSet rs=ad.selectQuery(hrdetails);
						
						while(rs.next())
						{
							lvform.setApprover(rs.getString("EMP_FULLNAME"));
							 lvform.setAppDesig(rs.getString("DSGSTXT"));
						}
					}
					}
					
					
					paralist.add(lvform);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	///Actual approver 
	
		String actual="select a.*,e.EMP_FULLNAME,d.DSGSTXT from All_Request a,emp_official_info e,DESIGNATION d where Req_Id='"+reqId+"' and Req_Type='Cancel On Duty'  and e.PERNR=a.Actual_Approver and e.DSGID=d.DSGID  ";
		ResultSet rsact=ad.selectQuery(actual);
		try {
			if(rsact.next())
			{
				LeaveForm l=new LeaveForm();
				l.setApprover(rsact.getString("EMP_FULLNAME"));	
			 l.setAppDesig(rsact.getString("DSGSTXT"));	
				l.setStatus(rsact.getString("Actual_Approver_status"));
				  String approveDate=rsact.getString("Actual_Approved_Date");
					
					String a[]=approveDate.split(" ");
					approveDate=a[0];
					String b[]=approveDate.split("-");
					approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1].substring(0, 5);
					
					 l.setApprovedDate(approveDate);
					 l.setComments(rsact.getString("Actual_Approved_Comments"));
						paralist.add(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	request.setAttribute("onduty", paralist);
}
if(existStatus==false){

String getRepMgrDesig="select dept.DPTSTXT,desg.DSGSTXT,emp.EMP_FULLNAME from emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where " +
" emp.PERNR='"+approverID+"' and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID";
ResultSet rsRepMgrDesig=ad.selectQuery(getRepMgrDesig);
try {
	LeaveForm leaveForm2=null;
	while(rsRepMgrDesig.next())
	{
	 leaveForm2=new LeaveForm();
	leaveForm2.setApptype("Approver Manager");
	leaveForm2.setApproverID(approverID);
	reportMgrDesg=rsRepMgrDesig.getString("DSGSTXT");
	reportingMgrName=rsRepMgrDesig.getString("EMP_FULLNAME");
	leaveForm2.setAppDesig(reportMgrDesg);
	leaveForm2.setApproverName(reportingMgrName);
	
	}
String getApproveTimings="select * from OnDuty_details where request_no='"+reqId+"' and user_id='"+user.getEmployeeNo()+"'";
ResultSet rsTimings=ad.selectQuery(getApproveTimings);
while(rsTimings.next())
{
	String reqstatus=rsTimings.getString("Approver_Status");
	leaveForm2.setStatus(rsTimings.getString("Approver_Status"));
	if(reqstatus.equalsIgnoreCase("Approved"))
	{
		leaveForm2.setApprovedDate(rsTimings.getString("approved_date"));
	}
	
	if(reqstatus.equalsIgnoreCase("Rejected")||reqstatus.equalsIgnoreCase("Cancelled"))
	{
		leaveForm2.setApprovedDate(rsTimings.getString("rejected_date"));
	}
	leaveForm2.setComments(rsTimings.getString("Comments"));
}
approverList.add(leaveForm2);
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
request.setAttribute("appList", approverList);

}


		
		return approverList;
}

	
	public ActionForward editMyRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		OnDutyForm ondutyForm=(OnDutyForm)form;
	
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	int userID=user.getId();
	int requestNo=Integer.parseInt(request.getParameter("requestNo"));
	try{
		ResultSet rs11 = ad.selectQuery("select LOCID," +
		"LOCNAME,location_code from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
			String userPlantID=user.getPlantId();
		while(rs11.next()) {
			locationList.add(rs11.getString("location_code"));
			locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
		}
		ondutyForm.setLocationIdList(locationList);
		ondutyForm.setLocationLabelList(locationLabelList);
		
	ondutyForm.setRequestNumber(requestNo);
	String draftData="select * from OnDuty_details where request_no='"+requestNo+"'";
	ResultSet rs=ad.selectQuery(draftData);
	while(rs.next())
	{
		ondutyForm.setOnDutyType(rs.getString("onDuty_Type"));
		ondutyForm.setLocationId(rs.getString("location"));
		ondutyForm.setStartTime(rs.getString("startTime"));
		ondutyForm.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
		ondutyForm.setSubmitDate(rs.getString("submit_date"));
		ondutyForm.setEndTime(rs.getString("endTime"));
		ondutyForm.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
		ondutyForm.setNoOfDays(rs.getString("no_of_days"));
		int approveStatus=rs.getInt("OnDuty_status");
		String status="";
		if(approveStatus==0)
		{
			status="In Process";
		}
		if(approveStatus==1)
		{
			status="Approved";
		}
		if(approveStatus==2)
		{
			status="Rejected";
		}
		if(approveStatus==3)
		{
			status="Cancelled";
		}
		ondutyForm.setStatus(status);
		String approveDate="";
		if(status.equalsIgnoreCase("Approved"))
		{
			approveDate=rs.getString("approved_date");
		}
		if(status.equalsIgnoreCase("Rejected")|| status.equalsIgnoreCase("Cancelled"))
		{
			approveDate=rs.getString("rejected_date");
		}
		if(!(approveDate.equalsIgnoreCase("")))
		{
			String a[]=approveDate.split(" ");
			approveDate=a[0];
			String b[]=approveDate.split("-");
			approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1].substring(0, 5);
			ondutyForm.setApproveDate(approveDate);
		}
		ondutyForm.setReason(rs.getString("reason"));
		ondutyForm.setRemark(rs.getString("comments"));
	}			
	String sql1="select * from onDuty_emp_documents where request_no='"+ondutyForm.getRequestNumber()+"' and user_id='"+user.getId()+"' ";
	ResultSet rs1=ad.selectQuery(sql1);
	OnDutyForm leaveForm1=null;
		ArrayList a1=new ArrayList();
		while(rs1.next()) {
			leaveForm1=new OnDutyForm();
			leaveForm1.setFileName(rs1.getString("file_name"));
			leaveForm1.setId(rs1.getString("id"));
			a1.add(leaveForm1);
		}
		request.setAttribute("documentDetails", a1);
		OnDutyAction lA = new OnDutyAction();
		LinkedList appList= lA.viewOnduty(mapping, form, request, response);
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("onDutydetails");
	}
	
	public ActionForward firstMyRequestRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList a1=new ArrayList();
		try{
			int totalRecords=ondutyForm.getTotalRecords1();//21
			int startRecord=ondutyForm.getStartRecord1();//11
			int endRecord=ondutyForm.getEndRecord1();	
			
			
			if(totalRecords>10){
			  startRecord=1;
			  endRecord=10;
			  ondutyForm.setTotalRecords1(totalRecords);
			  ondutyForm.setStartRecord1(startRecord);
			  ondutyForm.setEndRecord1(10);
			  }
			  else{
				  startRecord=1;
				  ondutyForm.setTotalRecords1(totalRecords);
				  ondutyForm.setStartRecord1(startRecord);
				  ondutyForm.setEndRecord1(totalRecords);  
			  }
			 String holidayType="";
				String LeaveType="";
				 OnDutyForm ondutyForm1=null;
				 ArrayList ondutyList=new ArrayList();
				 String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID desc) AS RowNum,request_no,onDuty_Type,location,start_date,startTime,end_date,endTime,no_of_days,OnDuty_status,record_status " +
					"from OnDuty_details where user_id='"+user.getEmployeeNo()+"' ) as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rs=ad.selectQuery(sql);
					while (rs.next()) {
						ondutyForm1=new OnDutyForm();
						ondutyForm1.setRequestNumber(rs.getInt("request_no"));
						
						String startDate=rs.getString("start_date");
						String a[]=startDate.split(" ");
						startDate=a[0];
						String b[]=startDate.split("-");
						startDate=b[2]+"/"+b[1]+"/"+b[0];
						ondutyForm1.setStartDate(startDate);
						ondutyForm1.setLocationId(rs.getString("location"));
						ondutyForm1.setStartTime(rs.getString("startTime"));
						ondutyForm1.setEndTime(rs.getString("endTime"));
						ondutyForm1.setRequestType(rs.getString("record_status"));
						String endDate=rs.getString("end_date");
						String c[]=endDate.split(" ");
						endDate=c[0];
						String d[]=endDate.split("-");
						endDate=d[2]+"/"+d[1]+"/"+d[0];
						ondutyForm1.setEndDate(endDate);
					
						ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
						ondutyForm1.setLocationId(rs.getString("location"));
						
						int OnDuty_status=rs.getInt("OnDuty_status");
						String status="";
						if(OnDuty_status==0)
						{
							status="In Process";
						}
						if(OnDuty_status==1)
						{
							status="Approved";
						}
						if(OnDuty_status==2)
						{
							status="Rejected";
						}
						if(OnDuty_status==3)
						{
							status="Cancelled";
						}
						ondutyForm1.setStatus(status);
						ondutyList.add(ondutyForm1);
					}
					request.setAttribute("ondutyList", ondutyList);
				 if(totalRecords>10)
					{
						request.setAttribute("nextButton", "nextButton");
					}
				
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
					
					request.setAttribute("displayRecordNo", "displayRecordNo");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("onDutyList");
	}
	
	public ActionForward lastMyRequestRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		OnDutyForm ondutyForm=(OnDutyForm)form;

		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList a1=new ArrayList();
		try{
			int totalRecords=ondutyForm.getTotalRecords1();//21
			int startRecord=ondutyForm.getStartRecord1();//11
			int endRecord=ondutyForm.getEndRecord1();	
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 ondutyForm.setTotalRecords1(totalRecords);
			 ondutyForm.setStartRecord1(startRecord);
			 ondutyForm.setEndRecord1(totalRecords);
			 String holidayType="";
				String LeaveType="";
				 OnDutyForm ondutyForm1=null;
				 ArrayList ondutyList=new ArrayList();
				 int year=0;
					if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
					{
						year=Calendar.getInstance().get(Calendar.YEAR);
					}
					else
					{
						 year = Integer.parseInt(request.getParameter("year"));
					}
					ondutyForm.setYear(Integer.toString(year));
					ArrayList yearList=new ArrayList();
						ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
							try {
								while(rs17.next()) {
									yearList.add(rs17.getString("lv_calyear"));
								}
								rs17.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							ondutyForm.setYearList(yearList);


				 String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID desc) AS RowNum,request_no,onDuty_Type,location,start_date,startTime,end_date,endTime,no_of_days,OnDuty_status,record_status " +
					"from OnDuty_details where user_id='"+user.getEmployeeNo()+"'and year(start_date)='"+year+"' ) as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rs=ad.selectQuery(sql);
					while (rs.next()) {
						ondutyForm1=new OnDutyForm();
						ondutyForm1.setRequestNumber(rs.getInt("request_no"));

						String startDate=rs.getString("start_date");
						String a[]=startDate.split(" ");
						startDate=a[0];
						String b[]=startDate.split("-");
						startDate=b[2]+"/"+b[1]+"/"+b[0];
						ondutyForm1.setStartDate(startDate);
						ondutyForm1.setLocationId(rs.getString("location"));
						ondutyForm1.setStartTime(rs.getString("startTime"));
						ondutyForm1.setEndTime(rs.getString("endTime"));
						ondutyForm1.setRequestType(rs.getString("record_status"));
						String endDate=rs.getString("end_date");
						String c[]=endDate.split(" ");
						endDate=c[0];
						String d[]=endDate.split("-");
						endDate=d[2]+"/"+d[1]+"/"+d[0];
						ondutyForm1.setEndDate(endDate);

						ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
						ondutyForm1.setLocationId(rs.getString("location"));

						int OnDuty_status=rs.getInt("OnDuty_status");
						String status="";
						if(OnDuty_status==0)
						{
							status="In Process";
						}
						if(OnDuty_status==1)
						{
							status="Approved";
						}
						if(OnDuty_status==2)
						{
							status="Rejected";
						}
						if(OnDuty_status==3)
						{
							status="Cancelled";
						}
						ondutyForm1.setStatus(status);
						ondutyList.add(ondutyForm1);
					}
					request.setAttribute("ondutyList", ondutyList);
				 request.setAttribute("disableNextButton", "disableNextButton");
					request.setAttribute("previousButton", "previousButton");
					if(a1.size()<10)
					{

						request.setAttribute("previousButton", "");
						request.setAttribute("disablePreviousButton", "disablePreviousButton");
					}
					request.setAttribute("displayRecordNo", "displayRecordNo");

	}catch (Exception e) {
		e.printStackTrace();
		}

		return mapping.findForward("onDutyList");
	
	}
	public ActionForward previousMyRequestRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		
		OnDutyForm ondutyForm=(OnDutyForm)form;

		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList a1=new ArrayList();
	try{
		int totalRecords=ondutyForm.getTotalRecords1();//21
		int endRecord=ondutyForm.getStartRecord1()-1;//20
		int startRecord=ondutyForm.getStartRecord1()-10;//11
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		ondutyForm.setTotalRecords1(totalRecords);
		ondutyForm.setStartRecord1(1);
		ondutyForm.setEndRecord1(10);
		String holidayType="";
		String LeaveType="";
		  OnDutyForm ondutyForm1=null;
		  ArrayList ondutyList=new ArrayList();

		  int year=0;
			if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
			{
				year=Calendar.getInstance().get(Calendar.YEAR);
			}
			else
			{
				 year = Integer.parseInt(request.getParameter("year"));
			}
			ondutyForm.setYear(Integer.toString(year));
			ArrayList yearList=new ArrayList();
				ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
					try {
						while(rs17.next()) {
							yearList.add(rs17.getString("lv_calyear"));
						}
						rs17.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ondutyForm.setYearList(yearList);

		  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID desc) AS RowNum,request_no,onDuty_Type,location,start_date,startTime,end_date,endTime,no_of_days,OnDuty_status,record_status " +
			"from OnDuty_details where user_id='"+user.getEmployeeNo()+"'and year(start_date)='"+year+"'  ) as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
		ResultSet rs=ad.selectQuery(sql);
			while (rs.next()) {
				ondutyForm1=new OnDutyForm();
				ondutyForm1.setRequestNumber(rs.getInt("request_no"));

				String startDate=rs.getString("start_date");
				String a[]=startDate.split(" ");
				startDate=a[0];
				String b[]=startDate.split("-");
				startDate=b[2]+"/"+b[1]+"/"+b[0];
				ondutyForm1.setStartDate(startDate);
				ondutyForm1.setLocationId(rs.getString("location"));
				ondutyForm1.setStartTime(rs.getString("startTime"));
				ondutyForm1.setEndTime(rs.getString("endTime"));
				ondutyForm1.setRequestType(rs.getString("record_status"));
				String endDate=rs.getString("end_date");
				String c[]=endDate.split(" ");
				endDate=c[0];
				String d[]=endDate.split("-");
				endDate=d[2]+"/"+d[1]+"/"+d[0];
				ondutyForm1.setEndDate(endDate);

				ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
				ondutyForm1.setLocationId(rs.getString("location"));

				int OnDuty_status=rs.getInt("OnDuty_status");
				String status="";
				if(OnDuty_status==0)
				{
					status="In Process";
				}
				if(OnDuty_status==1)
				{
					status="Approved";
				}
				if(OnDuty_status==2)
				{
					status="Rejected";
				}
				if(OnDuty_status==3)
				{
					status="Cancelled";
				}
				ondutyForm1.setStatus(status);
				ondutyList.add(ondutyForm1);
			}
			request.setAttribute("ondutyList", ondutyList);
			ondutyForm.setTotalRecords1(totalRecords);
		ondutyForm.setStartRecord1(startRecord);
		ondutyForm.setEndRecord1(endRecord);
				request.setAttribute("nextButton", "nextButton");
				if(startRecord!=1)
				request.setAttribute("previousButton", "previousButton");
				request.setAttribute("displayRecordNo", "displayRecordNo");
				if(ondutyList.size()<10)
				{
					ondutyForm.setStartRecord1(1);
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
	}catch (Exception e) {
		e.printStackTrace();
		}

		return mapping.findForward("onDutyList");
	
	}
	public ActionForward nextMyRequestRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		

		
		OnDutyForm ondutyForm=(OnDutyForm)form;
		HttpSession session=request.getSession();

		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			int totalRecords=ondutyForm.getTotalRecords1();//21
			int startRecord=ondutyForm.getStartRecord1();//11
			int endRecord=ondutyForm.getEndRecord1();
			ArrayList ondutyList=new ArrayList();
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
				String holidayType="";
				String LeaveType="";
				LeaveForm leaveForm1=null;
				  OnDutyForm ondutyForm1=null;
				  int year=0;
					if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
					{
						year=Calendar.getInstance().get(Calendar.YEAR);
					}
					else
					{
						 year = Integer.parseInt(request.getParameter("year"));
					}
					ondutyForm.setYear(Integer.toString(year));
					ArrayList yearList=new ArrayList();
						ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
							try {
								while(rs17.next()) {
									yearList.add(rs17.getString("lv_calyear"));
								}
								rs17.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							ondutyForm.setYearList(yearList);

				  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID desc) AS RowNum,request_no,onDuty_Type,location,start_date,startTime,end_date,endTime,no_of_days,OnDuty_status,record_status " +
					"from OnDuty_details where user_id='"+user.getEmployeeNo()+"'and year(start_date)='"+year+"'  ) as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rs=ad.selectQuery(sql);
					while (rs.next()) {
						ondutyForm1=new OnDutyForm();
						ondutyForm1.setRequestNumber(rs.getInt("request_no"));

						String startDate=rs.getString("start_date");
						String a[]=startDate.split(" ");
						startDate=a[0];
						String b[]=startDate.split("-");
						startDate=b[2]+"/"+b[1]+"/"+b[0];
						ondutyForm1.setStartDate(startDate);
						ondutyForm1.setLocationId(rs.getString("location"));
						ondutyForm1.setStartTime(rs.getString("startTime"));
						ondutyForm1.setEndTime(rs.getString("endTime"));
						ondutyForm1.setRequestType(rs.getString("record_status"));
						String endDate=rs.getString("end_date");
						String c[]=endDate.split(" ");
						endDate=c[0];
						String d[]=endDate.split("-");
						endDate=d[2]+"/"+d[1]+"/"+d[0];
						ondutyForm1.setEndDate(endDate);

						ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
						ondutyForm1.setLocationId(rs.getString("location"));

						int OnDuty_status=rs.getInt("OnDuty_status");
						String status="";
						if(OnDuty_status==0)
						{
							status="In Process";
						}
						if(OnDuty_status==1)
						{
							status="Approved";
						}
						if(OnDuty_status==2)
						{
							status="Rejected";
						}
						if(OnDuty_status==3)
						{
							status="Cancelled";
						}
						ondutyForm1.setStatus(status);
						ondutyList.add(ondutyForm1);
					}
					request.setAttribute("ondutyList", ondutyList);


			}

			System.out.println("list length="+ondutyList.size());

			 if(ondutyList.size()!=0)
				{
				 ondutyForm.setTotalRecords1(totalRecords);
				 ondutyForm.setStartRecord1(startRecord);
				 ondutyForm.setEndRecord1(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;

					ondutyForm.setTotalRecords1(totalRecords);
					ondutyForm.setStartRecord1(start);
					ondutyForm.setEndRecord1(end);

				}
			 if(ondutyList.size()<10)
			 {
				 ondutyForm.setTotalRecords1(totalRecords);
				 ondutyForm.setStartRecord1(startRecord);
				 ondutyForm.setEndRecord1(startRecord+ondutyList.size()-1);
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

		int year=0;
		if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
		{
			year=Calendar.getInstance().get(Calendar.YEAR);
		}
		else
		{
			 year = Integer.parseInt(request.getParameter("year"));
		}
		ondutyForm.setYear(Integer.toString(year));
		ArrayList yearList=new ArrayList();
			ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("lv_calyear"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ondutyForm.setYearList(yearList);

		return mapping.findForward("onDutyList");
 
	
	}
	
	
	public ActionForward displayOnDutyRequests(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		

		
		OnDutyForm ondutyForm=(OnDutyForm)form;

		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		  try{			
			  int year=0;
				if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
				{
					year=Calendar.getInstance().get(Calendar.YEAR);
				}
				else
				{
					 year = Integer.parseInt(request.getParameter("year"));
				}
				ondutyForm.setYear(Integer.toString(year));
				ArrayList yearList=new ArrayList();
					ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
						try {
							while(rs17.next()) {
								yearList.add(rs17.getString("lv_calyear"));
							}
							rs17.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						ondutyForm.setYearList(yearList);
		  String getTotalRecords="select count(*) from OnDuty_details where user_id='"+user.getEmployeeNo()+"' and  year(start_date)='"+year+"' ";
		  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
		  while(rsTotalRecods.next())
		  {
			  totalRecords=rsTotalRecods.getInt(1);
		  }
		  if(totalRecords>=10)
		  {
			  ondutyForm.setTotalRecords1(totalRecords);
		  startRecord=1;
		  endRecord=10;
		  ondutyForm.setStartRecord1(1);
		  ondutyForm.setEndRecord1(10);
		  request.setAttribute("displayRecordNo", "displayRecordNo");
		  request.setAttribute("nextButton", "nextButton");
		  }else
		  {
			  startRecord=1;
			  endRecord=totalRecords;
			  ondutyForm.setTotalRecords1(totalRecords);
			  ondutyForm.setStartRecord1(1);
			  ondutyForm.setEndRecord1(totalRecords); 
		  }			

		  ArrayList ondutyList=new ArrayList();	
		  OnDutyForm ondutyForm1=null;
		  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID desc) AS RowNum,"	+ "submit_date,request_no,onDuty_Type,"
		  		+ "location,start_date,startTime,end_date,endTime,no_of_days,OnDuty_status,record_status " +
			"from OnDuty_details where user_id='"+user.getEmployeeNo()+"'and year(start_date)='"+year+"') as sub Where  sub.RowNum between 1 and 10";
		  	ResultSet rs=ad.selectQuery(sql);
			while (rs.next()) {
				ondutyForm1=new OnDutyForm();
				ondutyForm1.setRequestNumber(rs.getInt("request_no"));
				ondutyForm1.setSubmitDate(rs.getString("submit_date"));

				String startDate=rs.getString("start_date");
				String a[]=startDate.split(" ");
				startDate=a[0];
				String b[]=startDate.split("-");
				startDate=b[2]+"/"+b[1]+"/"+b[0];
				ondutyForm1.setStartDate(startDate);
				ondutyForm1.setLocationId(rs.getString("location"));
				ondutyForm1.setStartTime(rs.getString("startTime"));
				ondutyForm1.setEndTime(rs.getString("endTime"));
				ondutyForm1.setRequestType(rs.getString("record_status"));
				String endDate=rs.getString("end_date");
				String c[]=endDate.split(" ");
				endDate=c[0];
				String d[]=endDate.split("-");
				endDate=d[2]+"/"+d[1]+"/"+d[0];
				ondutyForm1.setEndDate(endDate);

				ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
				ondutyForm1.setLocationId(rs.getString("location"));

				int OnDuty_status=rs.getInt("OnDuty_status");
				String status="";
				if(OnDuty_status==0)
				{
					status="In Process";
				}
				if(OnDuty_status==1)
				{
					status="Approved";
				}
				if(OnDuty_status==2)
				{
					status="Rejected";
				}
				if(OnDuty_status==3)
				{
					status="Cancelled";
				}
				if(OnDuty_status==4)
				{
					status="Self Cancelled";
				}
				if(OnDuty_status==5)
				{
					status="HR Rejected";
				}
				ondutyForm1.setStatus(status);
				ondutyList.add(ondutyForm1);
			}
			request.setAttribute("ondutyList", ondutyList);
			request.setAttribute("disablePreviousButton","disablePreviousButton");
			if(ondutyList.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				ondutyForm.setMessage("No records are found");
			}
			System.out.println(ondutyForm.getTotalRecords1());
			System.out.println(ondutyForm.getStartRecord1());
			System.out.println(ondutyForm.getEndRecord1());

		}catch(SQLException se){
			se.printStackTrace();
		}

		return mapping.findForward("onDutyList");
 
	
	}
	
	public ActionForward saveDraftData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		try{
		String type=request.getParameter("param");
		int OldrequestNo=ondutyForm.getRequestNumber();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String content_description = ondutyForm.getReason();
		
	
	String documents="";
	Calendar currentDate = Calendar.getInstance();
	
	  SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
	  Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
		 String dateNow = ft.format(dNow);
	  
	String sql1="select * from onDuty_emp_documents where user_id='"+user.getId()+"' and request_no='"+OldrequestNo+"'";
	ResultSet rs=ad.selectQuery(sql1);

		while(rs.next()) {
			documents+=rs.getString("file_name")+",";
		
		}
		if(documents.equalsIgnoreCase("")){
			documents=",";
		}
		
		EMailer email = new EMailer();
		String approver="";
		String lApprover="";
		String pApprover="";
		String approvermail="";
		
		String getApproverID="select * from emp_official_info where PERNR='"+user.getUserName()+"'";
		ResultSet approverRS=ad.selectQuery(getApproverID);
		while(approverRS.next()){
			
				approver = approverRS.getString("APPMGR");
				approver = email.getApproverName(approver);
				lApprover = "";
				pApprover = approver;
				
		}
		
		 String startDate=ondutyForm.getStartDate();
	 	 
		  String a[]=startDate.split("/");
		 
		  startDate=a[2]+"-"+a[1]+"-"+a[0];
		  String endDate=ondutyForm.getEndDate();
		 	 
		  String b[]=endDate.split("/");
		 
		  endDate=b[2]+"-"+b[1]+"-"+b[0];
		int status=0;
		String getRequestNoStatus="select count(*) from OnDuty_details where request_no='"+OldrequestNo+"'";
		ResultSet rsReqNoStatus=ad.selectQuery(getRequestNoStatus);
		while(rsReqNoStatus.next())
		{
			status=rsReqNoStatus.getInt(1);
		}
		
		/*String saveOnDuty="insert into OnDuty_details(request_no,user_id,onDuty_Type,duration,start_date,end_date,no_of_days,OnDuty_status," +
		"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver) " +
		"values('"+OldrequestNo+"','"+user.getId()+"','"+ondutyForm.getOnDutyType()+"','"+ondutyForm.getDuration()+"','"+startDate+"','"+endDate+"','"+ondutyForm.getNoOfDays()+"','0'," +
				"'Submited','"+documents+"','"+dateNow+"','"+content_description+"','"+user.getFirstName()+"','"+approver1+"','0','','No','"+pendingApprover+"')";
	*/
		
		String updateDraftData="update OnDuty_details set user_id='"+user.getId()+"',onDuty_Type='"+ondutyForm.getOnDutyType()+"',duration='"+ondutyForm.getDuration()+"',start_date='"+startDate+"',end_date='"+endDate+"',no_of_days='"+ondutyForm.getNoOfDays()+"',OnDuty_status='0'," +
		"record_status='Submited',documents='"+documents.substring(0, documents.lastIndexOf(","))+"',submit_date='"+dateNow+"',reason='"+content_description+"',firstname='"+user.getFirstName()+"',Approver_id='"+approver+"',Approver_Status='In Process',Forwarded_emp_id='',Last_approver='No',Pending_approver='"+pApprover+"' where request_no='"+ondutyForm.getRequestNumber()+"'";
		int i=0;
		i=ad.SqlExecuteUpdate(updateDraftData);
		
		if(i>0)
		{
			i = email.sendMailToApprover(request, approvermail,""+OldrequestNo,"On Duty");
			ondutyForm.setMessage("On Duty request submited with  request number='"+OldrequestNo+"' ");
			applyOnduty(mapping, form, request, response);
			
		}else{
			ondutyForm.setMessage("Error...When submit request.Please check");
			ondutyForm.setRequestNumber(OldrequestNo);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayDraftDetails");
	}
	public ActionForward deleteDraftDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OnDutyForm ondutyForm=(OnDutyForm)form;
		String checkedValues=request.getParameter("cValues");
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		int reqNo=ondutyForm.getRequestNumber();
		 int documentLength=0;
		 
 
		 try 
		 {
			 documentLength=documentCheck.length;
		 }catch(Exception e){
			
		 }
			int document=0;
			String documentId="";
			String documentName="";
			String sql="";
			System.out.println("rejectLength Is ********************"+documentLength);
			String[] documentId1=null;
			try{
				if(documentLength>0)
				{
					for(int i=0;i<documentLength;i++)
					{
						document++;
						documentId=documentCheck[i];
	        				sql="delete from onDuty_emp_documents " +
        			 		" where id="+documentId+" and request_no='"+reqNo+"'";
	        				System.out.println("Getting a sql is *************"+sql);
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					ondutyForm.setMessage("Document Details Deleted sucessfully");
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from onDuty_emp_documents where user_id='"+user.getId()+"' and request_no='"+reqNo+"'";
			
			ResultSet rs=ad.selectQuery(sql1);
			
			try{
				OnDutyForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					leaveForm1=new OnDutyForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getString("id"));
					a1.add(leaveForm1);
				}
				request.setAttribute("documentDetails", a1);
			}catch(Exception e){
				e.printStackTrace();
			}
		return mapping.findForward("displayDraftDetails");
	}

	
	public ActionForward uploadDraftDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		FormFile documentFile=ondutyForm.getDocumentFile();
		String documentName=documentFile.getFileName();
		String onDutyType=ondutyForm.getOnDutyType();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			 	
		 byte[] size=documentFile.getFileData();
		 if(!documentName.equalsIgnoreCase("")){
		 int length=documentName.length();
	     int dot=documentName.lastIndexOf(".");
	 	String fileName = documentFile.getFileName();
	     String extension=documentName.substring(dot,length);
	     //jsp/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles
 	     //String filepath = getServlet().getServletContext().getRealPath("/jsp/EMicro Files/ESS/On Duty/UploadFiles/"+documentFile.getFileName());
 	   String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/On Duty/UploadFiles");
		//masterForm.setUploadFilePath(filePath);
		File destinationDir = new File(filePath);
		if(!destinationDir.exists())
		{
			destinationDir.mkdirs();
		}
		if (!fileName.equals("")) {
			File fileToCreate = new File(filePath, fileName);
			if (!fileToCreate.exists()) {
				FileOutputStream fileOutStream = new FileOutputStream(
						fileToCreate);
				fileOutStream.write(documentFile.getFileData());
				fileOutStream.flush();
				fileOutStream.close();
			}
		}
 	     
 	     
 	     
 	     
 	     
		
		 }
		 }catch(FileNotFoundException fe){
			fe.printStackTrace();
		}catch(IOException ie){
			ie.printStackTrace();
		}
		try{
			
			int reqNo=ondutyForm.getRequestNumber();
			
			String sql9="select count(*) from onDuty_emp_documents  where  file_name='"+documentFile.getFileName()+"'";
			ResultSet rs15 = ad.selectQuery(sql9);
			int fileCount=0;
			while (rs15.next())
			{
				fileCount=Integer.parseInt(rs15.getString(1));
			}
			if(fileCount>0)
			{
				ondutyForm.setMessage("Document aleardy uploaded..please choose another file");
			}
			else
			{
				String sql="insert into onDuty_emp_documents(request_no,user_id,file_name,onDuty_Type)" +
				"values('"+reqNo+"','"+user.getId()+"','"+documentFile.getFileName()+"','"+onDutyType+"')";
				int a=ad.SqlExecuteUpdate(sql);
				if(a>0)
					{
					ondutyForm.setMessage("Documents Uploaded Successfully");
					}
			}
		String sql1="select * from onDuty_emp_documents where request_no='"+ondutyForm.getRequestNumber()+"' and user_id='"+user.getId()+"' and onDuty_Type='"+onDutyType+"'";
		ResultSet rs=ad.selectQuery(sql1);
		OnDutyForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			while(rs.next()) {
				leaveForm1=new OnDutyForm();
				leaveForm1.setFileName(rs.getString("file_name"));
				leaveForm1.setId(rs.getString("id"));
				a1.add(leaveForm1);
			}
			request.setAttribute("documentDetails", a1);
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("displayDraftDetails");
	}
	
	
	
	
	
	public ActionForward selectContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		int requestNo=Integer.parseInt(request.getParameter("requestNo"));
		try{
		ondutyForm.setRequestNumber(requestNo);
		String draftData="select * from OnDuty_details where request_no='"+requestNo+"'";
		ResultSet rs=ad.selectQuery(draftData);
		while(rs.next())
		{
			ondutyForm.setOnDutyType(rs.getString("onDuty_Type"));
			ondutyForm.setDuration(rs.getString("duration"));
			ondutyForm.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
			ondutyForm.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
			ondutyForm.setNoOfDays(rs.getString("no_of_days"));
			
			ondutyForm.setReason(rs.getString("reason"));
			
		}			
		String sql1="select * from onDuty_emp_documents where request_no='"+ondutyForm.getRequestNumber()+"' and user_id='"+user.getId()+"' ";
		ResultSet rs1=ad.selectQuery(sql1);
		OnDutyForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			while(rs1.next()) {
				leaveForm1=new OnDutyForm();
				leaveForm1.setFileName(rs1.getString("file_name"));
				leaveForm1.setId(rs1.getString("id"));
				a1.add(leaveForm1);
			}
			request.setAttribute("documentDetails", a1);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayDraftDetails");
	}
	
	
	public ActionForward selectRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		int requestNo=Integer.parseInt(request.getParameter("requstNo"));
		try{
		ondutyForm.setRequestNumber(requestNo);
		String draftData="select * from OnDuty_details where request_no='"+requestNo+"'";
		ResultSet rs=ad.selectQuery(draftData);
		String userId="";
		while(rs.next())
		{
			userId=rs.getString("user_id");
			String sq33l="select * from users where id='"+userId+"'";
			ResultSet rs1=ad.selectQuery(sq33l);
			String userName="";
			while(rs1.next()) 
			{
				userName=rs1.getString("fullname");
			}
			System.out.println("userName===="+userName);
			ondutyForm.setGeneratedBy(userName);
			ondutyForm.setOnDutyType(rs.getString("onDuty_Type"));
			ondutyForm.setDuration(rs.getString("duration"));
			ondutyForm.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
			ondutyForm.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
			ondutyForm.setNoOfDays(rs.getString("no_of_days"));
			
			ondutyForm.setReason(rs.getString("reason"));
			
		}			
		String sql1="select * from onDuty_emp_documents where request_no='"+ondutyForm.getRequestNumber()+"' and user_id='"+user.getId()+"' ";
		ResultSet rs1=ad.selectQuery(sql1);
		OnDutyForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			while(rs1.next()) {
				leaveForm1=new OnDutyForm();
				leaveForm1.setFileName(rs1.getString("file_name"));
				leaveForm1.setId(rs1.getString("id"));
				a1.add(leaveForm1);
			}
			request.setAttribute("documentDetails", a1);
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("submitDetails", "submitDetails");
		return mapping.findForward("displayDetails");
	}
	
	public ActionForward firstRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList a1=new ArrayList();
		try{
			int totalRecords=ondutyForm.getTotalRecords1();//21
			int startRecord=ondutyForm.getStartRecord1();//11
			int endRecord=ondutyForm.getEndRecord1();	
			
			
			if(totalRecords>10){
			  startRecord=1;
			  endRecord=10;
			  ondutyForm.setTotalRecords1(totalRecords);
			  ondutyForm.setStartRecord1(startRecord);
			  ondutyForm.setEndRecord1(10);
			  }
			  else{
				  startRecord=1;
				  ondutyForm.setTotalRecords1(totalRecords);
				  ondutyForm.setStartRecord1(startRecord);
				  ondutyForm.setEndRecord1(totalRecords);  
			  }
			 String holidayType="";
				String LeaveType="";
				 OnDutyForm ondutyForm1=null;
				 String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,request_no,start_date,onDuty_Type,end_date,no_of_days,duration " +
					"from OnDuty_details where user_id='"+user.getId()+"' and record_status='Drafts') as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rs=ad.selectQuery(sql);
					while (rs.next()) {
						ondutyForm1=new OnDutyForm();
						ondutyForm1.setRequestNumber(rs.getInt("request_no"));
						String startDate=rs.getString("start_date");
						String a[]=startDate.split(" ");
						startDate=a[0];
						String b[]=startDate.split("-");
						startDate=b[2]+"/"+b[1]+"/"+b[0];
						ondutyForm1.setStartDate(startDate);
						
						String endDate=rs.getString("end_date");
						String c[]=endDate.split(" ");
						endDate=c[0];
						String d[]=endDate.split("-");
						endDate=d[2]+"/"+d[1]+"/"+d[0];
						ondutyForm1.setEndDate(endDate);
						ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
						ondutyForm1.setNoOfDays(rs.getString("no_of_days"));
						ondutyForm1.setDuration(rs.getString("duration"));
						a1.add(ondutyForm1);
					}
					request.setAttribute("onDutyDraftDetails", a1);
				 if(totalRecords>10)
					{
						request.setAttribute("nextButton", "nextButton");
					}
				
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
					
					request.setAttribute("displayRecordNo", "displayRecordNo");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("draftRecords");
	}
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList a1=new ArrayList();
		try{
			int totalRecords=ondutyForm.getTotalRecords1();//21
			int startRecord=ondutyForm.getStartRecord1();//11
			int endRecord=ondutyForm.getEndRecord1();	
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 ondutyForm.setTotalRecords1(totalRecords);
			 ondutyForm.setStartRecord1(startRecord);
			 ondutyForm.setEndRecord1(totalRecords);
			 String holidayType="";
				String LeaveType="";
				 OnDutyForm ondutyForm1=null;
				 String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,request_no,start_date,onDuty_Type,end_date,no_of_days,duration " +
					"from OnDuty_details where user_id='"+user.getId()+"' and record_status='Drafts') as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rs=ad.selectQuery(sql);
					while (rs.next()) {
						ondutyForm1=new OnDutyForm();
						ondutyForm1.setRequestNumber(rs.getInt("request_no"));
						String startDate=rs.getString("start_date");
						String a[]=startDate.split(" ");
						startDate=a[0];
						String b[]=startDate.split("-");
						startDate=b[2]+"/"+b[1]+"/"+b[0];
						ondutyForm1.setStartDate(startDate);
						
						String endDate=rs.getString("end_date");
						String c[]=endDate.split(" ");
						endDate=c[0];
						String d[]=endDate.split("-");
						endDate=d[2]+"/"+d[1]+"/"+d[0];
						ondutyForm1.setEndDate(endDate);
						ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
						ondutyForm1.setNoOfDays(rs.getString("no_of_days"));
						ondutyForm1.setDuration(rs.getString("duration"));
						a1.add(ondutyForm1);
					}
					request.setAttribute("onDutyDraftDetails", a1);
				 request.setAttribute("disableNextButton", "disableNextButton");
					request.setAttribute("previousButton", "previousButton");
					if(a1.size()<10)
					{
						
						request.setAttribute("previousButton", "");
						request.setAttribute("disablePreviousButton", "disablePreviousButton");
					}
					request.setAttribute("displayRecordNo", "displayRecordNo");
			 
	}catch (Exception e) {
		e.printStackTrace();
		}
		return mapping.findForward("draftRecords");
	}
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList a1=new ArrayList();
	try{
		int totalRecords=ondutyForm.getTotalRecords1();//21
		int endRecord=ondutyForm.getStartRecord1()-1;//20
		int startRecord=ondutyForm.getStartRecord1()-10;//11
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		ondutyForm.setTotalRecords1(totalRecords);
		ondutyForm.setStartRecord1(1);
		ondutyForm.setEndRecord1(10);
		String holidayType="";
		String LeaveType="";
		  OnDutyForm ondutyForm1=null;
		 String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,request_no,start_date,onDuty_Type,end_date,no_of_days,duration " +
			"from OnDuty_details where user_id='"+user.getId()+"' and record_status='Drafts') as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
		ResultSet rs=ad.selectQuery(sql);
			while (rs.next()) {
				ondutyForm1=new OnDutyForm();
				ondutyForm1.setRequestNumber(rs.getInt("request_no"));
				String startDate=rs.getString("start_date");
				String a[]=startDate.split(" ");
				startDate=a[0];
				String b[]=startDate.split("-");
				startDate=b[2]+"/"+b[1]+"/"+b[0];
				ondutyForm1.setStartDate(startDate);
				
				String endDate=rs.getString("end_date");
				String c[]=endDate.split(" ");
				endDate=c[0];
				String d[]=endDate.split("-");
				endDate=d[2]+"/"+d[1]+"/"+d[0];
				ondutyForm1.setEndDate(endDate);
				ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
				ondutyForm1.setNoOfDays(rs.getString("no_of_days"));
				ondutyForm1.setDuration(rs.getString("duration"));
				a1.add(ondutyForm1);
			}
			request.setAttribute("onDutyDraftDetails", a1);
			ondutyForm.setTotalRecords1(totalRecords);
		ondutyForm.setStartRecord1(startRecord);
		ondutyForm.setEndRecord1(endRecord);
				request.setAttribute("nextButton", "nextButton");
				if(startRecord!=1)
				request.setAttribute("previousButton", "previousButton");
				request.setAttribute("displayRecordNo", "displayRecordNo");
				if(a1.size()<10)
				{
					ondutyForm.setStartRecord1(1);
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
	}catch (Exception e) {
		e.printStackTrace();
		}
		return mapping.findForward("draftRecords");
	}
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OnDutyForm ondutyForm=(OnDutyForm)form;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			int totalRecords=ondutyForm.getTotalRecords1();//21
			int startRecord=ondutyForm.getStartRecord1();//11
			int endRecord=ondutyForm.getEndRecord1();
			ArrayList a1=new ArrayList();
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
				String holidayType="";
				String LeaveType="";
				LeaveForm leaveForm1=null;
				  OnDutyForm ondutyForm1=null;
				  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,request_no,start_date,onDuty_Type,end_date,no_of_days,duration " +
					"from OnDuty_details where user_id='"+user.getId()+"' and record_status='Drafts') as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rs=ad.selectQuery(sql);
					while (rs.next()) {
						ondutyForm1=new OnDutyForm();
						ondutyForm1.setRequestNumber(rs.getInt("request_no"));
						String startDate=rs.getString("start_date");
						String a[]=startDate.split(" ");
						startDate=a[0];
						String b[]=startDate.split("-");
						startDate=b[2]+"/"+b[1]+"/"+b[0];
						ondutyForm1.setStartDate(startDate);
						
						String endDate=rs.getString("end_date");
						String c[]=endDate.split(" ");
						endDate=c[0];
						String d[]=endDate.split("-");
						endDate=d[2]+"/"+d[1]+"/"+d[0];
						ondutyForm1.setEndDate(endDate);
						ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
						ondutyForm1.setNoOfDays(rs.getString("no_of_days"));
						ondutyForm1.setDuration(rs.getString("duration"));
						a1.add(ondutyForm1);
					}
					request.setAttribute("onDutyDraftDetails", a1);
				
				
			}
			
			System.out.println("list length="+a1.size());
			
			 if(a1.size()!=0)
				{
				 ondutyForm.setTotalRecords1(totalRecords);
				 ondutyForm.setStartRecord1(startRecord);
				 ondutyForm.setEndRecord1(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;
					
					ondutyForm.setTotalRecords1(totalRecords);
					ondutyForm.setStartRecord1(start);
					ondutyForm.setEndRecord1(end);
					
				}
			 if(a1.size()<10)
			 {
				 ondutyForm.setTotalRecords1(totalRecords);
				 ondutyForm.setStartRecord1(startRecord);
				 ondutyForm.setEndRecord1(startRecord+a1.size()-1);
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
		return mapping.findForward("draftRecords");
	}
	
	public ActionForward cancelRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String[] selectedValue=request.getParameterValues("listid2");
		try
		{
		for(int i=0;i<selectedValue.length;i++)
		{
			String deleteRequest="delete from OnDuty_details where request_no='"+selectedValue[i]+"' and user_id='"+user.getId()+"'";
			int a=0;
			a=ad.SqlExecuteUpdate(deleteRequest);
			
			String deleteDraftRecords="delete from onDuty_emp_documents where request_no='"+selectedValue[i]+"' and user_id='"+user.getId()+"'";
			ad.SqlExecuteUpdate(deleteDraftRecords);
			if(a>0)
			{
				ondutyForm.setMessage("Requests canceled Successfully");
			}
			else
			{
				ondutyForm.setMessage(" Error while Canceling Requests ");
			}
		}
		//display(mapping, form, request, response);
		draftsList(mapping, form, request, response);
		}catch(Exception se)
		{
			se.printStackTrace();
		}
		return mapping.findForward("draftRecords");
	}
	
	public ActionForward draftsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		  try{
		  String getTotalRecords="select count(*) from OnDuty_details where user_id='"+user.getId()+"' and record_status='Drafts'";
		  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
		  while(rsTotalRecods.next())
		  {
			  totalRecords=rsTotalRecods.getInt(1);
		  }
		  if(totalRecords>=10)
		  {
			  ondutyForm.setTotalRecords1(totalRecords);
		  startRecord=1;
		  endRecord=10;
		  ondutyForm.setStartRecord1(1);
		  ondutyForm.setEndRecord1(10);
		  request.setAttribute("displayRecordNo", "displayRecordNo");
		  request.setAttribute("nextButton", "nextButton");
		  }else
		  {
			  startRecord=1;
			  endRecord=totalRecords;
			  ondutyForm.setTotalRecords1(totalRecords);
			  ondutyForm.setStartRecord1(1);
			  ondutyForm.setEndRecord1(totalRecords); 
		  }			
		  ArrayList ondutyList=new ArrayList();	
		  OnDutyForm ondutyForm1=null;
		  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,request_no,start_date,onDuty_Type,end_date,no_of_days,duration " +
			"from OnDuty_details where user_id='"+user.getId()+"' and record_status='Drafts') as sub Where  sub.RowNum between 1 and 10";
		ResultSet rs=ad.selectQuery(sql);
			while (rs.next()) {
				ondutyForm1=new OnDutyForm();
				ondutyForm1.setRequestNumber(rs.getInt("request_no"));
				String startDate=rs.getString("start_date");
				String a[]=startDate.split(" ");
				startDate=a[0];
				String b[]=startDate.split("-");
				startDate=b[2]+"/"+b[1]+"/"+b[0];
				ondutyForm1.setStartDate(startDate);
				
				String endDate=rs.getString("end_date");
				String c[]=endDate.split(" ");
				endDate=c[0];
				String d[]=endDate.split("-");
				endDate=d[2]+"/"+d[1]+"/"+d[0];
				ondutyForm1.setEndDate(endDate);
				ondutyForm1.setOnDutyType(rs.getString("onDuty_Type"));
				ondutyForm1.setNoOfDays(rs.getString("no_of_days"));
				ondutyForm1.setDuration(rs.getString("duration"));
				ondutyList.add(ondutyForm1);
			}
			request.setAttribute("onDutyDraftDetails", ondutyList);
			request.setAttribute("disablePreviousButton","disablePreviousButton");
			if(ondutyList.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				ondutyForm.setMessage("No records are found");
			}
		}catch(SQLException se){
			se.printStackTrace();
		}
		return mapping.findForward("draftRecords");
	}
	
	public ActionForward saveOnduty(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		OnDutyForm ondutyForm=(OnDutyForm)form; 
		
		try{
		String type=request.getParameter("param");
		int OldrequestNo=ondutyForm.getRequestNumber();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		LinkedList ondutyid=new LinkedList();
		LinkedList ondutyreason=new LinkedList();
		
		String reason="select * from Onduty_Type";
		ResultSet a11=ad.selectQuery(reason);
		try {
			while(a11.next())
			{
				ondutyid.add(a11.getString("OnDuty_type"));
				ondutyreason.add(a11.getString("OnDuty_type"));
			}
			ondutyForm.setOndutyvalue(ondutyid);
			ondutyForm.setOndutyreason(ondutyreason);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ResultSet rs11 = ad.selectQuery("select LOCID," +
		"LOCNAME,location_code from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
			String userPlantID=user.getPlantId();
			int userID=user.getId();
		while(rs11.next()) {
			locationList.add(rs11.getString("location_code"));
			locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
		}
		ondutyForm.setLocationIdList(locationList);
		ondutyForm.setLocationLabelList(locationLabelList);
		
		String content_description = ondutyForm.getReason();
		
		OnDutyAction lA1 = new OnDutyAction();
		LinkedList appList1= lA1.getApprovers(request, response);
		if(appList1.size()>0){	
	String documents="";
	Date dNow = new Date( );
	 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
	 String dateNow = ft.format(dNow);
	String sql1="select * from onDuty_emp_documents where user_id='"+user.getEmployeeNo()+"' and request_no='"+OldrequestNo+"'";
	ResultSet rs=ad.selectQuery(sql1);

		while(rs.next()) {
			documents+=rs.getString("file_name")+",";
		}
		if(documents.equalsIgnoreCase("")){
			documents=",";
		}
		EMailer email = new EMailer();
		String approver="";
		String lApprover="";
		String pApprover="";
		String approvermail="";
		
		String getApproverID="select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where "
				+ "ess.employeeNumber='"+user.getEmployeeNo()+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId";
		ResultSet approverRS=ad.selectQuery(getApproverID);
		if(approverRS.next()){
			
				approver = approverRS.getString("ApproverId");
				approver = email.getApproverName(approver);
				lApprover = "";
				pApprover = approver;
				approvermail=approverRS.getString("EMAIL_ID");
		}
		
		 String startDate=ondutyForm.getStartDate();
	 	 
		  String a[]=startDate.split("/");
		 
		  startDate=a[2]+"-"+a[1]+"-"+a[0];
			int year = Integer.parseInt(a[2]);
		  String endDate=ondutyForm.getEndDate();
		 	 
		  String b[]=endDate.split("/");
		 
		  endDate=b[2]+"-"+b[1]+"-"+b[0];
		  String checkDayType="";
		  boolean checkDayStatus=true;
			boolean check1= false;
			String ondutyType=ondutyForm.getOnDutyType();
			
			 //CHEKC leave can be applied after predefined days
            String allowtoapply="";
            int applyafter=0;
            
            String abcdef="select convert(date, dateadd(day,-(select Apply_after_OD from Location where locid='"+user.getPlantId()+"'),getdate()) ) lastdate,Apply_after_OD,case when convert(date, dateadd(day,-(select Apply_after_OD from Location where locid='"+user.getPlantId()+"'),getdate()) )<='"+(b[2]+"-"+b[1]+"-"+b[0])+"' then 1 else 0 end as allowdate  from Location where locid='"+user.getPlantId()+"'";
            ResultSet nn=ad.selectQuery(abcdef);
            while(nn.next())
            {
           	 allowtoapply=nn.getString("allowdate");
           	applyafter=nn.getInt("Apply_after_OD");
           
            }
            
            if(allowtoapply.equalsIgnoreCase("0"))
            {
            	checkDayStatus=false;
				ondutyForm.setMessage2("Request Can be applied  only within "+applyafter+" days after going on duty..Contact HR");
				OnDutyAction lA = new OnDutyAction();
				LinkedList appList= lA.getApprovers(request, response);
				request.setAttribute("appList", appList);
				return mapping.findForward("applyOnduty");
            }
			
		  if(ondutyType.equalsIgnoreCase("Visit Plants"))
		  {
			if(startDate.equalsIgnoreCase(endDate)){
			  String[] monthName = {"","JAN", "FEB",
					  "MAR", "APR", "MAY", "JUN", "JUL",
					  "AUG", "SEP", "OCT", "NOV",
					  "DEC"
					  };
			  String reqdate=ondutyForm.getStartDate();
			  String f[]=reqdate.split("/");
				 reqdate=f[1];
				
				 String startMonth=f[1];
				int staMont=Integer.parseInt(startMonth);
			  startMonth = monthName[staMont];
				String location=ondutyForm.getLocationId();
				String tableName=getTableName(location);
				
					
					String checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"' and CYEAR='"+year+"'";
					ResultSet rsCheckDay=ad.selectQuery(checkDay);
					while(rsCheckDay.next())
					{
						checkDayType=rsCheckDay.getString(startMonth);
						
					}
					if(!(checkDayType.equalsIgnoreCase("W")))
					{
						checkDayStatus=false;
						ondutyForm.setMessage2("Selected date is a holiday for "+ondutyForm.getLocationId()+"");
						OnDutyAction lA = new OnDutyAction();
						LinkedList appList= lA.getApprovers(request, response);
						request.setAttribute("appList", appList);
						return mapping.findForward("applyOnduty");
					}
			}
		  }
		  
		  //check Leave
		  
		  String checkLeaveRecords="Select * from leave_details where user_id='"+user.getEmployeeNo()+"' and (Approvel_Status='Pending' or  Approvel_Status='Approved') and " +
		  		" (('"+startDate+"' between start_date and end_date) or ('"+endDate+"' between start_date and end_date))  ";
		  ResultSet rsLeaveRec=ad.selectQuery(checkLeaveRecords);
		  while(rsLeaveRec.next())
		  {
			  String startDateDuration=rsLeaveRec.getString("start_duration");
			  String endDurationType=rsLeaveRec.getString("end_duration");
				String lvStDate=rsLeaveRec.getString("start_date");
				String x[]=lvStDate.split(" ");
				lvStDate=x[0];
				String lvEndDate=rsLeaveRec.getString("end_date");
				String y[]=lvEndDate.split(" ");
				lvEndDate=y[0];
				
				String draftstatus=rsLeaveRec.getString("record_status");
				
				String reqTime=ondutyForm.getStartTime();
				String reqTimeCycle=reqTime.substring(5, 7);
				reqTime=ondutyForm.getStartTime();
				reqTime=reqTime.substring(0, 5);
				
				String reqT[]=reqTime.split(":");
				int reqHours=Integer.parseInt(reqT[0]);
				int reqMint=Integer.parseInt(reqT[1]);
				if(reqTimeCycle.equalsIgnoreCase("AM"))
				{
					reqHours=reqHours;
				}else{
					if(reqHours!=12)
					reqHours=reqHours+12;	
				}
				reqMint=(reqHours*60)+reqMint;
				
				
				
				String reqToTime=ondutyForm.getEndTime();
				String reqToTimeCycle=reqToTime.substring(5, 7);
				reqToTime=ondutyForm.getEndTime();
				reqToTime=reqToTime.substring(0, 5);
				
				String reqToT[]=reqToTime.split(":");
				int reqToHours=Integer.parseInt(reqToT[0]);
				int reqToMint=Integer.parseInt(reqToT[1]);
				if(reqToTimeCycle.equalsIgnoreCase("AM"))
				{
					reqToHours=reqToHours;
				}else{
					if(reqToHours!=12)
					reqToHours=reqToHours+12;	
				}
				reqToMint=(reqToHours*60)+reqToMint;
				
				reqToTime=Integer.toString(reqHours)+":"+reqT[1]+":"+"00";
				
				System.out.println("from Mint="+reqMint);
				System.out.println("To Mint="+reqToMint);
				
				if(startDate.equalsIgnoreCase(lvStDate) )
				{
					
					if(startDateDuration.equalsIgnoreCase("FD") )
					{
						
						ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						if(draftstatus.equalsIgnoreCase("Draft")){
							ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
						}
						OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						
					
					}
					
					if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
					{
						
						if(reqMint<780)
						{
						 ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						 if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
						 OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						}
						
					
					}
					if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
					{
						if(reqToMint>780)
						{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							  
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						}
					}
					if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
					{
						ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						if(draftstatus.equalsIgnoreCase("Draft")){
							ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
						}
						OnDutyAction lA = new OnDutyAction();
						LinkedList appList= lA.getApprovers(request, response);
						request.setAttribute("appList", appList);
						return mapping.findForward("applyOnduty");
					}
					
				}
				if(startDate.equalsIgnoreCase(lvEndDate))
				{
					if(endDurationType.equalsIgnoreCase("FD") )
					{
						ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						if(draftstatus.equalsIgnoreCase("Draft")){
							ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
						}
						OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
					}
					if(endDurationType.equalsIgnoreCase("FH") )
					{
						if(reqMint<=780)
						{
						ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						if(draftstatus.equalsIgnoreCase("Draft")){
							ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
						}
						OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						}
					}
					if(endDurationType.equalsIgnoreCase("SH") )
					{
						if(reqMint>780)
						{
						ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						if(draftstatus.equalsIgnoreCase("Draft")){
							ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
						}
						OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						}
					}
				}
				if(endDate.equalsIgnoreCase(lvStDate))
				{
					if(startDateDuration.equalsIgnoreCase("FD") )
					{
						ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						if(draftstatus.equalsIgnoreCase("Draft")){
							ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
						}
						OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
					}
					if(startDateDuration.equalsIgnoreCase("FH") )
					{
						if(reqToMint<=780)
						{
						ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						if(draftstatus.equalsIgnoreCase("Draft")){
							ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
						}
						OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						}
					}
					if(startDateDuration.equalsIgnoreCase("SH") )
					{
						
						 
						if(reqToMint>780)
						{
						ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						if(draftstatus.equalsIgnoreCase("Draft")){
							ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
						}
						OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						}
					}
					
				}
				
				if(endDate.equalsIgnoreCase(lvEndDate)  )
				{
					if(endDurationType.equalsIgnoreCase("FD") )
					{
						ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						if(draftstatus.equalsIgnoreCase("Draft")){
							ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
						}
						OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
					}
					if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
					{
						if(reqToMint<=780)
						{
						ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						if(draftstatus.equalsIgnoreCase("Draft")){
							ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
						}
						  
						OnDutyAction lA = new OnDutyAction();
						LinkedList appList= lA.getApprovers(request, response);
						request.setAttribute("appList", appList);
						return mapping.findForward("applyOnduty");
						}
					
					}
					if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FH"))
					{
						if(reqToMint<=780)
						{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							  
							OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						}
					}
					if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
					{
						if(reqToMint<=780)
						{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						}
					}
					if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
					{
						if(reqToMint>780)
						{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						}
					}
					if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
					{
						ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						if(draftstatus.equalsIgnoreCase("Draft")){
							ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
						}
						OnDutyAction lA = new OnDutyAction();
						LinkedList appList= lA.getApprovers(request, response);
						request.setAttribute("appList", appList);
						return mapping.findForward("applyOnduty");
					}
					
				}
				
				
				if(!(startDate.equalsIgnoreCase(lvStDate)) && !(endDate.equalsIgnoreCase(lvEndDate))){
					ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
					if(draftstatus.equalsIgnoreCase("Draft")){
						ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
					}
					OnDutyAction lA = new OnDutyAction();
					LinkedList appList= lA.getApprovers(request, response);
					request.setAttribute("appList", appList);
					return mapping.findForward("applyOnduty");				
					}
				
			}
		  //check Permission
		  
		  String checkPermission="Select * from Permission_details where (Approver_Status=0 or  Approver_Status=1) and  user_id='"+user.getEmployeeNo()+"'" +
		  		" and ((date  between '"+startDate+"' and '"+endDate+"'))";
		  ResultSet rscheckPermi=ad.selectQuery(checkPermission);
		  while(rscheckPermi.next())
		  {/*

				String permissonDt=rscheckPermi.getString("date");
				String fromTime=rscheckPermi.getString("startTime");
				String fromTimeCycle=fromTime.substring(5, 7);

				String toTime=rscheckPermi.getString("endTime");
				String toTimeCycle=toTime.substring(5, 7);
				
				fromTime=rscheckPermi.getString("startTime");
				fromTime=fromTime.substring(0, 5);
				
				String fromT[]=fromTime.split(":");
				int fromHours=Integer.parseInt(fromT[0]);
				int fromMint=Integer.parseInt(fromT[1]);
				if(fromTimeCycle.equalsIgnoreCase("AM"))
				{
					fromHours=fromHours;
				}else{
					if(fromHours!=12)
					{
					fromHours=fromHours+12;
					}
				}
				
				fromMint=(fromHours*60)+fromMint;
				toTime=rscheckPermi.getString("endTime");
				toTime=toTime.substring(0, 5);
				
				String toT[]=toTime.split(":");
				int toHours=Integer.parseInt(toT[0]);
				int toMint=Integer.parseInt(toT[1]);
				if(toTimeCycle.equalsIgnoreCase("AM"))
				{
					toHours=toHours;
				}else{
					if(toHours!=12)
					{
						toHours=toHours+12;
					}
				}
				
				toMint=(toHours*60)+toMint;
				fromTime=Integer.toString(fromHours)+":"+fromT[1]+":"+"00";
				toTime=Integer.toString(toHours)+":"+toT[1]+":"+"00";
				
				Date time1 = new SimpleDateFormat("HH:mm:ss").parse(fromTime);
				Calendar calendar1 = Calendar.getInstance();
				calendar1.setTime(time1);

				Date time2 = new SimpleDateFormat("HH:mm:ss").parse(toTime);
				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(time2);
				calendar2.add(Calendar.DATE, 1);
				
				System.out.println("from Mint="+fromMint);
				System.out.println("To Mint="+toMint);
				
				String reqTime=ondutyForm.getStartTime();
				String reqTimeCycle=reqTime.substring(5, 7);
				reqTime=ondutyForm.getStartTime();
				reqTime=reqTime.substring(0, 5);
				
				String reqT[]=reqTime.split(":");
				int reqHours=Integer.parseInt(reqT[0]);
				int reqMint=Integer.parseInt(reqT[1]);
				if(reqTimeCycle.equalsIgnoreCase("AM"))
				{
					reqHours=reqHours;
				}else{
					if(reqHours!=12)
					{
						reqHours=reqHours+12;
					}
				}
				reqMint=(reqHours*60)+reqMint;
				
				
				
				String reqToTime=ondutyForm.getEndTime();
				String reqToTimeCycle=reqToTime.substring(5, 7);
				reqToTime=ondutyForm.getEndTime();
				reqToTime=reqToTime.substring(0, 5);
				
				String reqToT[]=reqToTime.split(":");
				int reqToHours=Integer.parseInt(reqToT[0]);
				int reqToMint=Integer.parseInt(reqToT[1]);
				if(reqToTimeCycle.equalsIgnoreCase("AM"))
				{
					reqToHours=reqToHours;
				}else{
					if(reqToHours!=12)
					{
						reqToHours=reqToHours+12;
					}
				}
				reqToMint=(reqToHours*60)+reqToMint;
				
				reqToTime=Integer.toString(reqHours)+":"+reqT[1]+":"+"00";
				
				if(permissonDt.equalsIgnoreCase(startDate)  || permissonDt.equalsIgnoreCase(endDate))
				{
					
                  if(permissonDt.equalsIgnoreCase(startDate))
                  {
					
					if(fromMint<reqMint && reqMint<toMint )
					{
						 ondutyForm.setMessage2("Permission already applied for this timings..Please check ");
						  
						 OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
					}
					
					if(reqMint<fromMint && toMint<reqToMint )
					{
						 ondutyForm.setMessage("Permission already applied for this timings..Please check ");
						  
						    LeaveAction lA = new LeaveAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
					}
					
                  }
                  if(permissonDt.equalsIgnoreCase(endDate))
                  {
                	  if(fromMint<reqToMint && reqToMint<toMint )
  					{
  						 ondutyForm.setMessage2("Permission already applied for this timings..Please check ");
  						  
  						OnDutyAction lA = new OnDutyAction();
  							LinkedList appList= lA.getApprovers(request, response);
  							request.setAttribute("appList", appList);
  							return mapping.findForward("applyOnduty");
  					}
                  }
					
				
				
			
					
					
					
					if(permissonDt.equalsIgnoreCase(startDate))
					{
						if(reqMint>fromMint && reqMint <toMint)
						{
							 ondutyForm.setMessage("Permission already applied for this timings..Please check ");
							  
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
						}
						
					}
					
				}else{
				
				
				if(permissonDt.equalsIgnoreCase(startDate))
				{
					if(reqMint>fromMint)
					{
						 ondutyForm.setMessage("Permission already applied for this timings..Please check ");
						  
						    LeaveAction lA = new LeaveAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
					}
				}
				if(permissonDt.equalsIgnoreCase(endDate))
				{
					if(reqMint>toMint)
					{
						 ondutyForm.setMessage("Permission already applied for this timings..Please check ");
						  
						    LeaveAction lA = new LeaveAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
					}
				}
				
					}
			
				
		  */}
		  
		  
		        if(true)
		        {
		        	
		        	if(!ondutyForm.getOnDutyType().equalsIgnoreCase(""))
		        	{
		        	String data="Select * from OnDuty_details where  user_id='"+user.getEmployeeNo()+"' and (('"+startDate+"' between " +
							"start_date and end_date) or ('"+endDate+"' between start_date and end_date)) and onDuty_Type='Visit Plants' and (Approver_Status='Approved' or Approver_Status='In Process')";	        	
		        	ResultSet rsAppDate1=ad.selectQuery(data);
					while(rsAppDate1.next())
					{
						ondutyForm.setMessage("OnDuty already applied for this Date..Please check ");
						  
						OnDutyAction lA = new OnDutyAction();
						LinkedList appList= lA.getApprovers(request, response);
						request.setAttribute("appList", appList);
						return mapping.findForward("applyOnduty");
						
						
					}
		        	}
		        	if(!(ondutyForm.getOnDutyType().equalsIgnoreCase("Visit Plants")))
		        	{
		        	
					String getAppliedDates="Select * from OnDuty_details where  user_id='"+user.getEmployeeNo()+"' and (('"+startDate+"' between " +
					"start_date and end_date) or ('"+endDate+"' between start_date and end_date)) and (Approver_Status='Approved' or Approver_Status='In Process')";
					ResultSet rsAppDate=ad.selectQuery(getAppliedDates);
					while(rsAppDate.next())
					{
						
						String reqStDate=rsAppDate.getString("start_date");
						String[] a1=reqStDate.split(" ");
						reqStDate=a1[0];
						String reqEndDate=rsAppDate.getString("end_date");
						String[] b1=reqEndDate.split(" ");
						reqEndDate=b1[0];
						String fromTime=rsAppDate.getString("startTime");
						String fromTimeCycle=fromTime.substring(5, 7);

						String toTime=rsAppDate.getString("endTime");
						String toTimeCycle=toTime.substring(5, 7);
						
						fromTime=rsAppDate.getString("startTime");
						fromTime=fromTime.substring(0, 5);
						
						String fromT[]=fromTime.split(":");
						int fromHours=Integer.parseInt(fromT[0]);
						int fromMint=Integer.parseInt(fromT[1]);
						if(fromTimeCycle.equalsIgnoreCase("AM"))
						{
							fromHours=fromHours;
						}else{
							if(fromHours!=12)
							fromHours=fromHours+12;	
						}
						
						fromMint=(fromHours*60)+fromMint;
						toTime=rsAppDate.getString("endTime");
						toTime=toTime.substring(0, 5);
						
						String toT[]=toTime.split(":");
						int toHours=Integer.parseInt(toT[0]);
						int toMint=Integer.parseInt(toT[1]);
						if(toTimeCycle.equalsIgnoreCase("AM"))
						{
							toHours=toHours;
						}else{
							if(toHours!=12)
							toHours=toHours+12;	
						}
						
						toMint=(toHours*60)+toMint;
						fromTime=Integer.toString(fromHours)+":"+fromT[1]+":"+"00";
						toTime=Integer.toString(toHours)+":"+toT[1]+":"+"00";
						
						Date time1 = new SimpleDateFormat("HH:mm:ss").parse(fromTime);
						Calendar calendar1 = Calendar.getInstance();
						calendar1.setTime(time1);

						Date time2 = new SimpleDateFormat("HH:mm:ss").parse(toTime);
						Calendar calendar2 = Calendar.getInstance();
						calendar2.setTime(time2);
						calendar2.add(Calendar.DATE, 1);
						
						String reqTime=ondutyForm.getStartTime();
						String reqTimeCycle=reqTime.substring(5, 7);
						reqTime=ondutyForm.getStartTime();
						reqTime=reqTime.substring(0, 5);
						
						String reqT[]=reqTime.split(":");
						int reqHours=Integer.parseInt(reqT[0]);
						int reqMint=Integer.parseInt(reqT[1]);
						if(reqTimeCycle.equalsIgnoreCase("AM"))
						{
							reqHours=reqHours;
						}else{
							if(reqHours!=12)
							reqHours=reqHours+12;	
						}
						reqMint=(reqHours*60)+reqMint;
						
						
						
						String reqToTime=ondutyForm.getEndTime();
						String reqToTimeCycle=reqToTime.substring(5, 7);
						reqToTime=ondutyForm.getEndTime();
						reqToTime=reqToTime.substring(0, 5);
						
						String reqToT[]=reqToTime.split(":");
						int reqToHours=Integer.parseInt(reqToT[0]);
						int reqToMint=Integer.parseInt(reqToT[1]);
						if(reqToTimeCycle.equalsIgnoreCase("AM"))
						{
							reqToHours=reqToHours;
						}else{
							if(reqToHours!=12)
							reqToHours=reqToHours+12;	
						}
						reqToMint=(reqToHours*60)+reqToMint;
						
						reqTime=Integer.toString(reqHours)+":"+reqT[1]+":"+"00";
						reqToTime=Integer.toString(reqToHours)+":"+reqToT[1]+":"+"00";
						
						
						if(startDate.equalsIgnoreCase(reqStDate) && reqEndDate.equalsIgnoreCase(startDate))
						{
							if(reqStDate.equalsIgnoreCase(startDate))
							{
								if(reqMint>fromMint && reqMint <toMint)
								{
									 ondutyForm.setMessage2("OnDuty already applied for this timings..Please check ");
									  
									 OnDutyAction lA = new OnDutyAction();
										LinkedList appList= lA.getApprovers(request, response);
										request.setAttribute("appList", appList);
										return mapping.findForward("applyOnduty");
								}
								
							}
							
						}
						else{
						 if(reqStDate.equalsIgnoreCase(startDate) || startDate.equalsIgnoreCase(reqEndDate))
						{
							
							if(reqStDate.equalsIgnoreCase(startDate))
							{
								if ((fromMint<=reqMint) ) {
									  
								    System.out.println(true);
								    check1=true;
								    ondutyForm.setMessage2("OnDuty already applied for this timings..Please check ");
								  
								    OnDutyAction lA = new OnDutyAction();
									LinkedList appList= lA.getApprovers(request, response);
									request.setAttribute("appList", appList);
									return mapping.findForward("applyOnduty");
								}
							}
							if(startDate.equalsIgnoreCase(reqEndDate))
							{
								if (reqMint<=toMint) {
									  
								    System.out.println(true);
								    check1=true;
								    ondutyForm.setMessage2("OnDuty already applied for this timings..Please check ");
								  
								    OnDutyAction lA = new OnDutyAction();
									LinkedList appList= lA.getApprovers(request, response);
									request.setAttribute("appList", appList);
									return mapping.findForward("applyOnduty");
								}
							}
						
							
						}						
						if(reqEndDate.equalsIgnoreCase(endDate) || endDate.equalsIgnoreCase(reqStDate))
						{
							if(endDate.equalsIgnoreCase(reqStDate))
							{
								if (reqToMint>=fromMint)  {
									  
								    System.out.println(true);
								    check1=true;
								    ondutyForm.setMessage2("OnDuty already applied for this timings..Please check ");
								  
								    OnDutyAction lA = new OnDutyAction();
									LinkedList appList= lA.getApprovers(request, response);
									request.setAttribute("appList", appList);
									return mapping.findForward("applyOnduty");
								}
							}
							if(reqEndDate.equalsIgnoreCase(endDate))
							{
								if (toMint>=reqToMint) {
									System.out.println(true);
								    check1=true;
								    ondutyForm.setMessage2("OnDuty already applied for this timings..Please check ");
								  
								    OnDutyAction lA = new OnDutyAction();
									LinkedList appList= lA.getApprovers(request, response);
									request.setAttribute("appList", appList);
									return mapping.findForward("applyOnduty");
								   
								}else{
									
								}

							}
						 }
						
						}
					}
		        	}
			      }
		  
		  int check=0;
		  if(false)
		  {
		 
		
		  String checkLeave="select start_date,end_date from OnDuty_details where  user_id='"+user.getEmployeeNo()+"'";
			ResultSet rsCheckLeave=ad.selectQuery(checkLeave);
			while(rsCheckLeave.next()){

				try{
					List<Date> dates = new ArrayList<Date>();
					String str_date =rsCheckLeave.getString("start_date");
					String c[]=str_date.split(" ");
					str_date=c[0];
					String end_date =rsCheckLeave.getString("end_date");
		            String d[]=end_date.split(" ");
		            end_date=d[0];
		            String a1[]=str_date.split("-");
		            str_date=a1[2]+"/"+a1[1]+"/"+a1[0];
		            String b1[]=end_date.split("-");
		            end_date=b1[2]+"/"+b1[1]+"/"+b1[0]; 
					DateFormat formatter ; 
					formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date  startDate1 = formatter.parse(str_date); 
					Date  endDate1 = formatter.parse(end_date);
					long interval = 24*1000 * 60 * 60; // 1 hour in millis
					long endTime =endDate1.getTime() ; // create your endtime here, possibly using Calendar or Date
					long curTime = startDate1.getTime();
					while (curTime <= endTime) {
					    dates.add(new Date(curTime));
					    curTime += interval;
					}
					for(int i=0;i<dates.size();i++){
					    Date lDate =dates.get(i);
					    String ds = formatter.format(lDate);    
					 
					    if(ds.equalsIgnoreCase(ondutyForm.getStartDate())){
					    	check++;
					    }
					}
					    for(int j=0;j<dates.size();j++){
						    Date lDate1 =dates.get(j);
						    String ds1 = formatter.format(lDate1);    
						
						    if(ds1.equalsIgnoreCase(ondutyForm.getEndDate())){
						    	check++;
						    }
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		  }
			if(check==0){
		synchronized (this) {
		int status=0;
		String getRequestNoStatus="select count(*) from OnDuty_details where request_no='"+OldrequestNo+"'";
		ResultSet rsReqNoStatus=ad.selectQuery(getRequestNoStatus);
		while(rsReqNoStatus.next())
		{
			status=rsReqNoStatus.getInt(1);
		}
		
		 
		//request no is not available
		if(status==0)
		{
			
	
			OldrequestNo = OldrequestNo ;
			if(type.equalsIgnoreCase("Applied"))
			{
				String saveOnDuty="insert into OnDuty_details(request_no,user_id,onDuty_Type,start_date,end_date,OnDuty_status," +
				"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,location,startTime,endTime,InTime,OutTime) " +
				"values('"+OldrequestNo+"','"+user.getEmployeeNo()+"','"+ondutyForm.getOnDutyType()+"','"+startDate+"','"+endDate+"','0'," +
						"'Submited','"+documents+"','"+dateNow+"','"+content_description+"','"+user.getFirstName()+"','"+pApprover+"','In Process','','No','"+pApprover+"','"+ondutyForm.getLocationId()+"','"+ondutyForm.getStartTime()+"','"+ondutyForm.getEndTime()+"','','')";
				int i=0;
				i=ad.SqlExecuteUpdate(saveOnDuty);
				
				if(i>0)
				{
					ondutyForm.setMessage("On Duty request submited with  request number='"+OldrequestNo+"' ");
					i = email.sendMailToApprover(request, approvermail,Integer.toString(OldrequestNo), "On Duty");
				
					getApproverDetails(Integer.toString(OldrequestNo), form, request, pApprover);
					
					applyOnduty(mapping, form, request, response);
					
				}else{
					ondutyForm.setMessage2("Error...When code saving creation request.Please check");
					ondutyForm.setRequestNumber(OldrequestNo);
				}
			}else{}
		}else{
			int  newReqestNo=0;
			String getRequstNo="select max(request_no) from OnDuty_details";
			ResultSet rsRequestNo=ad.selectQuery(getRequstNo);
			while(rsRequestNo.next())
			{
				newReqestNo=rsRequestNo.getInt(1);
			}
			newReqestNo=newReqestNo+1;
			
			if(type.equalsIgnoreCase("Applied"))
			{
				String saveOnDuty="insert into OnDuty_details(request_no,user_id,onDuty_Type,start_date,end_date,OnDuty_status," +
				"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,location,startTime,endTime,InTime,OutTime) " +
				"values('"+newReqestNo+"','"+user.getEmployeeNo()+"','"+ondutyForm.getOnDutyType()+"','"+startDate+"','"+endDate+"','0'," +
						"'Submited','"+documents+"','"+dateNow+"','"+content_description+"','"+user.getFirstName()+"','"+pApprover+"','In Process','','No','"+pApprover+"','"+ondutyForm.getLocationId()+"','"+ondutyForm.getStartTime()+"','"+ondutyForm.getEndTime()+"','','')";
				int i=0;
				i=ad.SqlExecuteUpdate(saveOnDuty);
				
				if(i>0)
				{
					
					ondutyForm.setMessage("On Duty request submited with request number='"+newReqestNo+"' ");
					
					getApproverDetails(Integer.toString(newReqestNo), form, request, pApprover);
					i = email.sendMailToApprover(request, approvermail,Integer.toString(newReqestNo), "On Duty");
					
				
					
				
				}else{
					ondutyForm.setMessage2("Error...When code saving creation request.Please check");
					ondutyForm.setRequestNumber(newReqestNo);
				}
			}else{}
			
			String updateDocuments="update onDuty_emp_documents set request_no='"+newReqestNo+"' where request_no='"+OldrequestNo+"' and user_id='"+user.getEmployeeNo()+"'";
			ad.SqlExecuteUpdate(updateDocuments);
		}
		}
			}else{
				ondutyForm.setMessage2("OnDuty Already Applied For The Selected Date");
			}
	}else{
		ondutyForm.setMessage2("No Approvers assigned.Please Contact Admin");
	}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("applyOnduty");
	}
	
	public ActionForward applyOnduty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OnDutyForm ondutyForm=(OnDutyForm)form;
		
		LinkedList totalList = new LinkedList();
		try{
			String applyafterdate="";
			String rule="select * from LEAVE_APPLY_RULE";
			ResultSet bbc=ad.selectQuery(rule);
			if(bbc.next())
			{
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -bbc.getInt("days"));
				Date date = cal.getTime();             
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				applyafterdate = format1.format(date);            
				
			}
			
			ondutyForm.setApplyAfterDate(applyafterdate);
			
			
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			 HttpSession session=request.getSession();
				UserInfo user=(UserInfo)session.getAttribute("user");
				String userPlantID=user.getPlantId();
				int userID=user.getId();
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			ondutyForm.setLocationIdList(locationList);
			ondutyForm.setLocationLabelList(locationLabelList);	
		int requestNo=0;
		String getRequstNo="select max(request_no) from OnDuty_details";
		ResultSet rsRequestNo=ad.selectQuery(getRequstNo);
		while(rsRequestNo.next())
		{
			requestNo=rsRequestNo.getInt(1);
		}
		requestNo=requestNo+1;
		ondutyForm.setRequestNumber(requestNo);
		ondutyForm.setOnDutyType("");
		ondutyForm.setDuration("");
		ondutyForm.setStartDate("");
		ondutyForm.setEndDate("");
		ondutyForm.setNoOfDays("");
		ondutyForm.setReason("");
		ondutyForm.setLocationId("");
		
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		String curentdate=sdf.format(d);
		ondutyForm.setSubmitDate(curentdate);
		
		ondutyForm.setStartTime("");
		ondutyForm.setEndTime("");
		//for get total onduty taken 
		int onDutyTotal=0;
		int typeTotal=0;
		OnDutyForm odf = new OnDutyForm();
		odf.setVpTotal(typeTotal);
		odf.setAcTotal(typeTotal);
		odf.setCvTotal(typeTotal);
		odf.setTourTotal(typeTotal);
		String totalQry="select count(*) from OnDuty_details where Approver_Status='Approved' and onDuty_Type like '%Visit%'";
		ResultSet totalRS = ad.selectQuery(totalQry);
		while(totalRS.next()){
			typeTotal = totalRS.getInt(1);
			onDutyTotal = onDutyTotal + typeTotal;
			odf.setVpTotal(typeTotal);
		}
		totalQry="select count(*) from OnDuty_details where Approver_Status='Approved' and onDuty_Type like '%Attend%'";
		totalRS = ad.selectQuery(totalQry);
		while(totalRS.next()){
			typeTotal = totalRS.getInt(1);
			onDutyTotal = onDutyTotal + typeTotal;
			odf.setAcTotal(typeTotal);
		}
		totalQry="select count(*) from OnDuty_details where Approver_Status='Approved' and onDuty_Type like '%Meet%'";
		totalRS = ad.selectQuery(totalQry);
		while(totalRS.next()){
			typeTotal = totalRS.getInt(1);
			onDutyTotal = onDutyTotal + typeTotal;
			odf.setCvTotal(typeTotal);
		}
		totalQry="select count(*) from OnDuty_details where Approver_Status='Approved' and onDuty_Type like '%Tour%'";
		totalRS = ad.selectQuery(totalQry);
		while(totalRS.next()){
			typeTotal = totalRS.getInt(1);
			onDutyTotal = onDutyTotal + typeTotal;
			odf.setTourTotal(typeTotal);
		}
		odf.setOnDTotal(onDutyTotal);
		
		totalList.add(odf);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("OnDutyTakenList",totalList);
		OnDutyAction lA = new OnDutyAction();
		LinkedList appList= lA.getApprovers(request, response);
		request.setAttribute("appList", appList);
		
		if(appList.size()==0)
			ondutyForm.setMessage2("No Approvers assigned.Please Contact Admin");
		
		LinkedList ondutyid=new LinkedList();
		LinkedList ondutyreason=new LinkedList();
		
		String reason="select * from Onduty_Type";
		ResultSet a=ad.selectQuery(reason);
		try {
			while(a.next())
			{
				ondutyid.add(a.getString("OnDuty_type"));
				ondutyreason.add(a.getString("OnDuty_type"));
			}
			ondutyForm.setOndutyvalue(ondutyid);
			ondutyForm.setOndutyreason(ondutyreason);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return mapping.findForward("applyOnduty");
	}
	
	
	public ActionForward uploadDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OnDutyForm ondutyForm=(OnDutyForm)form;
			
			FormFile documentFile=ondutyForm.getDocumentFile();
			String documentName=documentFile.getFileName();
			String onDutyType=ondutyForm.getOnDutyType();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			 String filePath="";
			 
			 String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
				int filesize=documentFile.getFileSize();
				OnDutyAction lA = new OnDutyAction();
				LinkedList appList= lA.getApprovers(request, response);
				request.setAttribute("appList", appList);
				if(appList.size()>0){
				if((ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls")) && (filesize<1048576))
				{
			try{
				ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
					String userPlantID=user.getPlantId();
					int userID=user.getId();
					try{
				while(rs11.next()) {
					locationList.add(rs11.getString("location_code"));
					locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				}
					}catch (Exception e) {
						e.printStackTrace();
					}
				ondutyForm.setLocationIdList(locationList);
				ondutyForm.setLocationLabelList(locationLabelList);
		 	
			 byte[] size=documentFile.getFileData();
			 if(!documentName.equalsIgnoreCase("")){
			 int length=documentName.length();
		     int dot=documentName.lastIndexOf(".");
		 	String fileName = documentFile.getFileName();
		     String extension=documentName.substring(dot,length);
		     
		     

         filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/On Duty/UploadFiles");
	 	 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	 	 Properties props = new Properties();
	 	 props.load(in);
		 in.close();
	 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/On Duty/UploadFiles");
	 	 filePath=uploadFilePath+"/EMicro Files/ESS/On Duty/UploadFiles";
	 	 
	 	 
			//masterForm.setUploadFilePath(filePath);
			File destinationDir = new File(filePath);
			if(!destinationDir.exists())
			{
				destinationDir.mkdirs();
			}
			if (!fileName.equals("")) {
				File fileToCreate = new File(filePath, fileName);
				if (!fileToCreate.exists()) {
					FileOutputStream fileOutStream = new FileOutputStream(
							fileToCreate);
					fileOutStream.write(documentFile.getFileData());
					fileOutStream.flush();
					fileOutStream.close();
				}
			}
			//upload files in another path
			
			try{
				String filePath1 = "E:/EMicro Files/ESS/On Duty/UploadFiles";
				
				byte[] fileData1 = documentFile.getFileData();
				
				
				File destinationDir1 = new File(filePath1);
				if(!destinationDir1.exists())
				{
					destinationDir1.mkdirs();
				}
				if (!fileName.equals("")) {
					File fileToCreate1 = new File(filePath1, fileName);
					if (!fileToCreate1.exists()) {
						FileOutputStream fileOutStream1 = new FileOutputStream(
								fileToCreate1);
						fileOutStream1.write(documentFile.getFileData());
						fileOutStream1.flush();
						fileOutStream1.close();
					}
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
	 	     
	 	     
	 	     
	 	     
			
			 }
			 }catch(FileNotFoundException fe){
				fe.printStackTrace();
			}catch(IOException ie){
				ie.printStackTrace();
			}
		
			 String contentType =documentFile.getContentType();
			  String fileName   =  documentFile.getFileName();
			
		
			try{
				
				
				int reqNo=ondutyForm.getRequestNumber();
				
				String sql9="select count(*) from onDuty_emp_documents  where  file_name='"+documentFile.getFileName()+"'";
				ResultSet rs15 = ad.selectQuery(sql9);
				int fileCount=0;
				while (rs15.next())
				{
					fileCount=Integer.parseInt(rs15.getString(1));
				}
				if(fileCount>0)
				{
					ondutyForm.setMessage("Document already uploaded..please choose another file");
				}
				else
				{
					String sql="insert into onDuty_emp_documents(request_no,user_id,file_name,onDuty_Type)" +
					"values('"+reqNo+"','"+user.getEmployeeNo()+"','"+documentFile.getFileName()+"','"+onDutyType+"')";
					int a=ad.SqlExecuteUpdate(sql);
					if(a>0)
						{
						ondutyForm.setMessage("Documents Uploaded Successfully");
						}
				}
			String sql1="select * from onDuty_emp_documents where request_no='"+ondutyForm.getRequestNumber()+"' and user_id='"+user.getEmployeeNo()+"' and onDuty_Type='"+onDutyType+"'";
			ResultSet rs=ad.selectQuery(sql1);
			OnDutyForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					leaveForm1=new OnDutyForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getString("id"));
					leaveForm1.setFileFullPath(filePath+"/"+rs.getString("file_name"));
					a1.add(leaveForm1);
				}
				request.setAttribute("documentDetails", a1);
				
			
				
				
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
				}else{
					ondutyForm.setMessage2("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
					String sql1="select * from onDuty_emp_documents where request_no='"+ondutyForm.getRequestNumber()+"' and user_id='"+user.getEmployeeNo()+"' and onDuty_Type='"+onDutyType+"'";
					ResultSet rs=ad.selectQuery(sql1);
					OnDutyForm leaveForm1=null;
						ArrayList a1=new ArrayList();
						try {
							while(rs.next()) {
								leaveForm1=new OnDutyForm();
								leaveForm1.setFileName(rs.getString("file_name"));
								leaveForm1.setId(rs.getString("id"));
								leaveForm1.setFileFullPath(filePath+"/"+rs.getString("file_name"));
								a1.add(leaveForm1);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						request.setAttribute("documentDetails", a1);
					
							}
				}else{
					ondutyForm.setMessage2("No Approvers assigned.Please Contact Admin");
				}
				
				
				LinkedList ondutyid=new LinkedList();
				LinkedList ondutyreason=new LinkedList();
				
				String reason="select * from Onduty_Type";
				ResultSet a=ad.selectQuery(reason);
				try {
					while(a.next())
					{
						ondutyid.add(a.getString("OnDuty_type"));
						ondutyreason.add(a.getString("OnDuty_type"));
					}
					ondutyForm.setOndutyvalue(ondutyid);
					ondutyForm.setOndutyreason(ondutyreason);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			return mapping.findForward("applyOnduty");
			}
	
	public ActionForward deleteDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OnDutyForm ondutyForm=(OnDutyForm)form;
		String checkedValues=request.getParameter("cValues");
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		int reqNo=ondutyForm.getRequestNumber();
		 int documentLength=0;
		 
	 
		 try 
		 {
			 documentLength=documentCheck.length;
		 }catch(Exception e){
			
		 }
		 
		 ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
				String userPlantID=user.getPlantId();
				int userID=user.getId();
				try{
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
				}catch (Exception e) {
					e.printStackTrace();
				}
			ondutyForm.setLocationIdList(locationList);
			ondutyForm.setLocationLabelList(locationLabelList);
			int document=0;
			String documentId="";
			String documentName="";
			String sql="";
			System.out.println("rejectLength Is ********************"+documentLength);
			String[] documentId1=null;
			try{
				if(documentLength>0)
				{
					for(int i=0;i<documentLength;i++)
					{
						document++;
						documentId=documentCheck[i];
						String fileName="";
						String getFilename="select * from onDuty_emp_documents where id="+documentId+" and request_no='"+reqNo+"'";
						ResultSet rsFileName=ad.selectQuery(getFilename);
						while(rsFileName.next())
						{
							fileName=rsFileName.getString("file_name");
						}
						
	        				sql="delete from onDuty_emp_documents  where id="+documentId+" and request_no='"+reqNo+"'";
	        			
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					ondutyForm.setMessage("Document Details Deleted sucessfully");
	        					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	        					 Properties props = new Properties();
	        					 	props.load(in);
	        						in.close();
	        					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	        					 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/On Duty/UploadFiles");
	        					 	 String filePath=uploadFilePath+"/EMicro Files/ESS/On Duty/UploadFiles";
	        					 	 
	        					 	File fileToCreate = new File(filePath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	System.out.println(test);
	        					 	
	        					 	//delete file in another path
	        					 	
	        					 	File fileToCreate1 = new File("E:/EMicro Files/ESS/On Duty/UploadFiles",fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from onDuty_emp_documents where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqNo+"'";
			
			ResultSet rs=ad.selectQuery(sql1);
			
			try{
				OnDutyForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					leaveForm1=new OnDutyForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getString("id"));
					a1.add(leaveForm1);
				}
				request.setAttribute("documentDetails", a1);
				
				OnDutyAction lA = new OnDutyAction();
				LinkedList appList= lA.getApprovers(request, response);
				request.setAttribute("appList", appList);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			LinkedList ondutyid=new LinkedList();
			LinkedList ondutyreason=new LinkedList();
			
			String reason="select * from Onduty_Type";
			ResultSet a=ad.selectQuery(reason);
			try {
				while(a.next())
				{
					ondutyid.add(a.getString("OnDuty_type"));
					ondutyreason.add(a.getString("OnDuty_type"));
				}
				ondutyForm.setOndutyvalue(ondutyid);
				ondutyForm.setOndutyreason(ondutyreason);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return mapping.findForward("applyOnduty");
	}
	
	public int sendMailToApprover(HttpServletRequest request, String approvermail, int Req_Id){
		int success=0;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userId=user.getId();
		String ondutyType = ""; //getLeaveType(leaveType);
		String duration = "";
		String fullName = user.getFullName();
		try{
			//getting details from leave_details
			String submitDate="";
			String pApprover="";
			String startDate = "";
			String noOfDays = "";
			String leavedetails = "select * from OnDuty_details where user_id="+user.getEmployeeNo()+" and request_no='"+Req_Id+"'";
			ResultSet ldRS = ad.selectQuery(leavedetails);
			while(ldRS.next()){
				submitDate = ldRS.getString("submit_date");
				startDate = EMicroUtils.display1(ldRS.getDate("start_date"));
				pApprover = ldRS.getString("Pending_Approver");
				pApprover = pApprover.substring(0, pApprover.indexOf(","));
				ondutyType = ldRS.getString("onDuty_type");
				noOfDays = ldRS.getString("no_of_days");
				duration = ldRS.getString("duration");
			}
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
			saveRecReq = saveRecReq + "'"+Req_Id+"','On Duty','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+pApprover+"','No','"+userId+"','')";
			int i=ad.SqlExecuteUpdate(saveRecReq);
			MailInboxForm mailForm = new MailInboxForm();
			MailInboxAction mailAction = new MailInboxAction();
			mailForm.setToAddress(approvermail);
			System.out.println("m -> "+approvermail);
			mailForm.setccAddress("");
			mailForm.setbccAddress("");
			mailForm.setSubject("OnDuty Request");
			String desc = "Requested By : "+fullName+"</br>";
			desc = desc + "Requested Date : "+submitDate+"</br>";
			desc = desc + "OnDuty Type : "+ondutyType+" for "+duration+"</br>";
			desc = desc + "From : "+startDate+"</br>";
			desc = desc + "No.Of Days : "+noOfDays+"</br>";
			mailForm.setDescription(desc);
			mailAction.mailSendToRecipient(request, mailForm,"request");
			success=1;
		}catch (ServletException se) { System.out.println("ServletException @ sending leave request.."); se.printStackTrace();}
		catch (MessagingException se) { System.out.println("MessagingException @ sending leave request.."); se.printStackTrace();}
		catch (IOException ioe) { System.out.println("IOException @ sending leave request.."); ioe.printStackTrace();}
		catch (SQLException sqle) { System.out.println("SQLException @ sending leave request.."); sqle.printStackTrace();}
		return success;
	}
	
	

}
