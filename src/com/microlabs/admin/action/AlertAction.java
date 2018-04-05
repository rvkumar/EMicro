package com.microlabs.admin.action;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.admin.form.AlertForm;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.utilities.UserInfo;


public class AlertAction extends DispatchAction {
	
	//For report View added

	public ActionForward displayList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AlertForm AlertForm = (AlertForm) form;
		UserDao ad = new UserDao();
		
		int startRecord = 0;
		int endRecord = 0;
		int totalRecords =0;
		String getCount = "select * from ALERT_BOX ";
		ResultSet rsCount = ad.selectQuery(getCount);
		
		try {
			while (rsCount.next()) {
				totalRecords = totalRecords + 1;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (totalRecords >= 10) {
			AlertForm.setTotalRecords(totalRecords);
			startRecord = 1;
			endRecord = 10;
			AlertForm.setStartRecord(1);
			AlertForm.setEndRecord(10);
			request.setAttribute("displayRecordNo", "displayRecordNo");
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			request.setAttribute("nextButton", "nextButton");
		} else {
			startRecord = 1;
			endRecord = totalRecords;
			AlertForm.setTotalRecords(totalRecords);
			AlertForm.setStartRecord(1);
			AlertForm.setEndRecord(totalRecords);
		}
		
		
				List resarvedList = new LinkedList();
				try {
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy hh:mm aa");
		
					String dateNow = formatter.format(currentDate.getTime());
					String a[] = dateNow.split(" ");
		
					AlertForm.setFromDate(a[0]);
					AlertForm.setFromTime(a[1] + " " + a[2]);
					
					AlertForm.setToDate(a[0]);
					AlertForm.setToTime(a[1] + " " + a[2]);
		
					ResultSet rs11 = ad.selectQuery("select * from location ");
					ArrayList locationList = new ArrayList();
					ArrayList locationLabelList = new ArrayList();
					
					while (rs11.next()) {
						locationList.add(rs11.getString("LOCATION_CODE"));
						locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
								+ rs11.getString("LOCNAME"));
					}
					AlertForm.setLocationIdList(locationList);
					AlertForm.setLocationLabelList(locationLabelList);
		
					ResultSet rs12 = ad.selectQuery("select * from DEPARTMENT ");
					ArrayList deptList = new ArrayList();
					ArrayList deptLabelList = new ArrayList();
					while (rs12.next()) {
						deptList.add(rs12.getString("DPTID"));
						deptLabelList.add(rs12.getString("DPTSTXT"));
					}
					
					AlertForm.setDeptIdList(deptList);
					AlertForm.setDeptLabelList(deptLabelList);
					
					String checkAvilbilty = "select * from (select ROW_NUMBER() over (order by start_date desc) AS RowNum , *  from ALERT_BOX )as  sub Where  sub.RowNum between 1 and 10 ";
							
		
		ResultSet rs = ad.selectQuery(checkAvilbilty);
		try {
			while (rs.next()) {
				AlertForm c = new AlertForm();
				c.setReqNo(rs.getString("Id"));
				c.setLocation(rs.getString("Location"));
				c.setCategory(rs.getString("StaffCategory"));
				c.setDep(rs.getString("Department"));
				c.setHeadLines1(rs.getString("Content_Heading"));
				String fromDate = rs.getString("Start_date");
				String b2[] = fromDate.split(" ");
				c.setFromDate1(b2[1].substring(0, 5));
				c.setFromTime1(b2[0]);
				String toDate = rs.getString("End_date");
				String c2[] = toDate.split(" ");
				c.setToDate1(c2[1].substring(0, 5));
				c.setToTime1(c2[0]);
				if(rs.getString("Content_subject").length()>35)
				c.setSubject1(rs.getString("Content_subject").substring(0, 35));
				else
			    c.setSubject1(rs.getString("Content_subject"));
				
				resarvedList.add(c);
		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resarvedList.size() > 0) {
			request.setAttribute("reservedList", resarvedList);
		} else {
			request.setAttribute("noRecords", "noRecords");
		}
		
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		
				return mapping.findForward("alertView");
		
			}

	
	
	public ActionForward nextRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
	
		AlertForm AlertForm = (AlertForm) form;
		HttpSession session = request.getSession();
		EssDao ad = new EssDao();
		
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		try {
			int totalRecords = AlertForm.getTotalRecords();// 21
			int startRecord = AlertForm.getStartRecord();// 11
			int endRecord = AlertForm.getEndRecord();

			if (totalRecords > endRecord) {
				if (totalRecords == endRecord) {
					startRecord = startRecord;
					endRecord = totalRecords;
				}
				if (totalRecords > endRecord) {
					startRecord = endRecord + 1;
					endRecord = endRecord + 10;
				}
		List resarvedList = new LinkedList();
		String checkAvilbilty = "select * from (select ROW_NUMBER() over (order by start_date desc) AS RowNum , *  from ALERT_BOX )as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' ";
		ResultSet rs = ad.selectQuery(checkAvilbilty);
		try {
			while (rs.next()) {
				AlertForm c = new AlertForm();
				c.setReqNo(rs.getString("Id"));
				c.setLocation(rs.getString("Location"));
				c.setCategory(rs.getString("StaffCategory"));
				c.setDep(rs.getString("Department"));
				c.setHeadLines1(rs.getString("Content_Heading"));
				String fromDate = rs.getString("Start_date");
				String b2[] = fromDate.split(" ");
				c.setFromDate1(b2[1].substring(0, 5));
				c.setFromTime1(b2[0]);
				String toDate = rs.getString("End_date");
				String c2[] = toDate.split(" ");
				c.setToDate1(c2[1].substring(0, 5));
				c.setToTime1(c2[0]);
				if(rs.getString("Content_subject").length()>35)
				c.setSubject1(rs.getString("Content_subject").substring(0, 35));
				else
			    c.setSubject1(rs.getString("Content_subject"));
				
				resarvedList.add(c);
		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

			request.setAttribute("reservedList", resarvedList);
			if (resarvedList.size() != 0) {
				AlertForm.setTotalRecords(totalRecords);
				AlertForm.setStartRecord(startRecord);
				AlertForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			} else {
				int start = startRecord;
				int end = startRecord;

				AlertForm.setTotalRecords(totalRecords);
				AlertForm.setStartRecord(start);
				AlertForm.setEndRecord(end);

			}
			if (resarvedList.size() < 10) {
				AlertForm.setTotalRecords(totalRecords);
				AlertForm.setStartRecord(startRecord);
				AlertForm.setEndRecord(startRecord + resarvedList.size() - 1);
				request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton",
						"disableNextButton");
				request.setAttribute("previousButton", "previousButton");

			}
			
			if (endRecord == totalRecords) {
				request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton",
						"disableNextButton");
			}
			request.setAttribute("displayRecordNo", "displayRecordNo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		return mapping.findForward("alertView");
	}
	
	public ActionForward previousRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EssDao ad = new EssDao();
		AlertForm AlertForm = (AlertForm) form;
		HttpSession session = request.getSession();
		
		UserInfo user = (UserInfo) session.getAttribute("user");
		try {
			int totalRecords = AlertForm.getTotalRecords();// 21
			int endRecord = AlertForm.getStartRecord() - 1;// 20
			int startRecord = AlertForm.getStartRecord() - 10;// 11
			if (startRecord == 1) {
				request.setAttribute("disablePreviousButton",
						"disablePreviousButton");
				endRecord = 10;
			}
			AlertForm.setTotalRecords(totalRecords);
			AlertForm.setStartRecord(1);
			AlertForm.setEndRecord(10);
			List resarvedList = new LinkedList();
			String checkAvilbilty = "select * from (select ROW_NUMBER() over (order by start_date desc) AS RowNum , *  from ALERT_BOX )as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' ";
			ResultSet rs = ad.selectQuery(checkAvilbilty);
			try {
				while (rs.next()) {
					AlertForm c = new AlertForm();
					c.setReqNo(rs.getString("Id"));
					c.setLocation(rs.getString("Location"));
					c.setCategory(rs.getString("StaffCategory"));
					c.setDep(rs.getString("Department"));
					c.setHeadLines1(rs.getString("Content_Heading"));
					String fromDate = rs.getString("Start_date");
					String b2[] = fromDate.split(" ");
					c.setFromDate1(b2[1].substring(0, 5));
					c.setFromTime1(b2[0]);
					String toDate = rs.getString("End_date");
					String c2[] = toDate.split(" ");
					c.setToDate1(c2[1].substring(0, 5));
					c.setToTime1(c2[0]);
					if(rs.getString("Content_subject").length()>35)
					c.setSubject1(rs.getString("Content_subject").substring(0, 35));
					else
				    c.setSubject1(rs.getString("Content_subject"));
					
					resarvedList.add(c);
			
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("reservedList", resarvedList);
			AlertForm.setTotalRecords(totalRecords);
			AlertForm.setStartRecord(startRecord);
			AlertForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			if (startRecord != 1)
				request.setAttribute("previousButton", "previousButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
			if (resarvedList.size() < 10) {
				AlertForm.setStartRecord(1);
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton",
						"disablePreviousButton");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			

		return mapping.findForward("alertView");
	}
	
		
	//for add new 
	public ActionForward addnewAlert(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AlertForm AlertForm = (AlertForm) form;
		UserDao ad = new UserDao();
		try {
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"dd/MM/yyyy hh:mm aa");

			String dateNow = formatter.format(currentDate.getTime());
			String a[] = dateNow.split(" ");

			AlertForm.setFromDate(a[0]);
			AlertForm.setFromTime(a[1] + " " + a[2]);
			
			AlertForm.setToDate(a[0]);
			AlertForm.setToTime(a[1] + " " + a[2]);

			ResultSet rs11 = ad.selectQuery("select * from location ");
			ArrayList locationList = new ArrayList();
			ArrayList locationLabelList = new ArrayList();
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
			AlertForm.setLocationIdList(locationList);
			AlertForm.setLocationLabelList(locationLabelList);

			ResultSet rs12 = ad.selectQuery("select * from DEPARTMENT ");
			ArrayList deptList = new ArrayList();
			ArrayList deptLabelList = new ArrayList();
			while (rs12.next()) {
				deptList.add(rs12.getString("DPTID"));
				deptLabelList.add(rs12.getString("DPTSTXT"));
			}
			AlertForm.setDeptIdList(deptList);
			AlertForm.setDeptLabelList(deptLabelList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("saveButton", "saveButton");

		AlertForm.setSaveType("save");

		request.setAttribute("MenuIcon", request.getParameter("id"));
		AlertForm.setSubject("");
		AlertForm.setHeadLines("");

		return mapping.findForward("addnewAlert");

	}
	
//Saving Alert
	public ActionForward saveAlert(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

//		AdminMail mail = new AdminMail();
		HttpSession session = request.getSession();
		AlertForm AlertForm = (AlertForm) form;
	    UserDao ad = new UserDao();
		Enumeration<String> params = request.getParameterNames();
		String parameter;
		String content_description = "";
		String existingEmpo ="";
		while (params.hasMoreElements()) {
			parameter = params.nextElement();
			if (parameter.equalsIgnoreCase("EditorDefault")) {
				content_description += request.getParameter(parameter);
			}
		}
		content_description = content_description.replaceAll("'", "`");
		AlertForm.setContent(content_description);
		String FromDt = AlertForm.getFromDate();
		
		
	    
		/*String a[] = FromDt.split("/");
		FromDt = a[2] + "-" + a[1] + "-" + a[0] +" "+AlertForm.getFromTime();
		
		String ToDt = AlertForm.getToDate();
		String b[] = ToDt.split("/");
		
		ToDt = b[2] + "-" + b[1] + "-" + b[0]+" "+AlertForm.getToTime();*/
		
		String reqFromDate = AlertForm.getFromDate();
		String a1[] = reqFromDate.split("/");
		reqFromDate = a1[2] + "-" + a1[1] + "-" + a1[0];
		String fromtime = AlertForm.getFromTime();
		String pm = "PM";
		if (fromtime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
			fromtime = fromtime.replaceAll("PM", "");
			String a[] = fromtime.split(":");
			if (Integer.parseInt(a[0]) != 12) {
				int hr = Integer.parseInt(a[0]) + 12;
				reqFromDate = reqFromDate + " " + hr + ":" + a[1];
			} else {
				fromtime = fromtime.replaceAll("PM", "");
				reqFromDate = reqFromDate + " " + fromtime;
			}
		} else {
			fromtime = fromtime.replaceAll("AM", "");
			reqFromDate = reqFromDate + " " + fromtime;
		}

		String reqToDate = AlertForm.getToDate();
		String b1[] = reqToDate.split("/");
		reqToDate = b1[2] + "-" + b1[1] + "-" + b1[0];
		String totime = AlertForm.getToTime();
		if (totime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
			totime = totime.replaceAll("PM", "");
			String a[] = totime.split(":");
			if (Integer.parseInt(a[0]) != 12) {
				int hr = Integer.parseInt(a[0]) + 12;
				reqToDate = reqToDate + " " + hr + ":" + a[1];
			} else {
				totime = totime.replaceAll("PM", "");
				reqToDate = reqToDate + " " + totime;
			}
		} else {
			totime = totime.replaceAll("AM", "");
			reqToDate = reqToDate + " " + totime;
		}


		
		
		
		
		
		
		content_description = content_description.replaceAll("'", "''");

		String[] loc = AlertForm.getLocationId();
		String[] cat = AlertForm.getCat();
		String[] dept = AlertForm.getDepartment();

		StringBuffer reqloc = new StringBuffer();
		for (int i = 0; i < loc.length; i++) {
			reqloc.append(loc[i] + ",");
		}
		String ACtualloc = reqloc.substring(0, reqloc.length() - 1).toString();

		StringBuffer reqcat = new StringBuffer();
		for (int i = 0; i < cat.length; i++) {
			reqcat.append(cat[i] + ",");
		}
		String ACtualcat = reqcat.substring(0, reqcat.length() - 1).toString();

		StringBuffer reqdept = new StringBuffer();
		for (int i = 0; i < dept.length; i++) {
			reqdept.append(dept[i] + ",");
		}
		String ACtualdept = reqdept.substring(0, reqdept.length() - 1).toString();
		
		
		StringBuffer reqStringloc = new StringBuffer();
		for (int i = 0; i < loc.length; i++) {
			 reqStringloc.append("'"+loc[i]+"'" + ",");
		}
		String ActStringloc =  reqStringloc.substring(0, reqStringloc.length() - 1).toString();
		
		int present = 0 ;
		String emplist4 = null;
		
		//Existing empno Checking on this time 
		
		String Empno2 = "select  empnos from Alert_box"
         				+" where (Start_date between  '"+reqFromDate+"' and '"+reqToDate+"' ) or " 
		        	    +" (end_date between  '"+reqFromDate+"' and '"+reqToDate+"' ) or " 
			            + " ('"+reqFromDate+"' between start_date and end_date) or "
			            +" ('"+reqToDate+"' between Start_date and end_date) ";

		ResultSet rs15 = ad.selectQuery(Empno2);
		
		while (rs15.next()) {
			
			emplist4 = rs15.getString("empnos");
			
		}
		
		
		//Checking with existing 
		
		if(emplist4!=null)
		
		{	
		String Empno3 = "select  PERNR from emp_official_info"
						+" where LOCID  in ("+ActStringloc+") and STAFFCAT in ("+ACtualcat+") and DPTID in ("+ACtualdept+") and active =1 "; 
		

		
		ResultSet rs16 = ad.selectQuery(Empno3);
		while (rs16.next()) {    
			if(present ==1)
			{
				break;
			}
			
			if(emplist4.contains(rs16.getString("PERNR")))
			{
				
				System.out.println(rs16.getString("PERNR"));
				existingEmpo = rs16.getString("PERNR");
				present = 1;
			    break;
			}
			
		}

		

		}
		
		
		
		if(present == 0)
		
		{
		ArrayList empList = new ArrayList();
		String Empno = "select  PERNR from emp_official_info"
						+" where LOCID  in ("+ActStringloc+") and STAFFCAT in ("+ACtualcat+") and DPTID in ("+ACtualdept+") and active =1"; 
		

		ResultSet rs14 = ad.selectQuery(Empno);
		
		while (rs14.next()) {
			
			empList.add(rs14.getString("PERNR"));
			
		}
		
		StringBuffer empList1 = new StringBuffer();
		for (int i = 0; i < empList.size(); i++) {
			empList1.append(empList.get(i)+ ",");
		}

		String ActualempList ="";
		
		if(empList1.length() >0)
		{	
			
		ActualempList = empList1.substring(0, empList1.length() - 1).toString();
		}
		
	
       
		String insertDetails = "insert into ALERT_BOX(Location,StaffCategory,Department,Content_description,Content_Heading,Start_date,End_date,Status,Content_subject,Archieval,Empnos)"
				               + "values('"+ACtualloc+"','"+ACtualcat+"','"+ACtualdept+"','"+content_description+"','"+AlertForm.getHeadLines()+"','"+reqFromDate+"','"+reqToDate+"',1,'"+AlertForm.getSubject()+"',0,'"+ActualempList+"' )";
		
		
				
		int i = 0;
		i = ad.SqlExecuteUpdate(insertDetails);

		if (i > 0) {

			session.setAttribute("result", "Alert Details Are Saved");
	//		int j = mail.sendMailToUsers(request,announForm.getHeadLinesType(),announForm.getSubject(),ACtualloc, ACtualcat,ACtualdept);
		} else {
			session.setAttribute("result",
					"Error When Inserting alert Details.Please Check.... ");

		}
		
		}
		else
		{
			session.setAttribute("result","Error When Inserting alert Already Exists For this Group Employee.Please Check...");

		}

		ResultSet rs11 = ad.selectQuery("select * from location ");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		while (rs11.next()) {
			locationList.add(rs11.getString("LOCATION_CODE"));
			locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
					+ rs11.getString("LOCNAME"));
		}
		AlertForm.setLocationIdList(locationList);
		AlertForm.setLocationLabelList(locationLabelList);

		ResultSet rs12 = ad.selectQuery("select * from DEPARTMENT ");
		ArrayList deptList = new ArrayList();
		ArrayList deptLabelList = new ArrayList();
		while (rs12.next()) {
			deptList.add(rs12.getString("DPTID"));
			deptLabelList.add(rs12.getString("DPTSTXT"));
		}
		AlertForm.setDeptIdList(deptList);
		AlertForm.setDeptLabelList(deptLabelList);
		request.setAttribute("saveButton", "saveButton");
		return mapping.findForward("addnewAlert");
	}


	public ActionForward displayReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AlertForm AlertForm = (AlertForm) form;
		EssDao ad = new EssDao();
		String[] loc = AlertForm.getLocationId();
		String[] cat = AlertForm.getCat();
		String[] dept = AlertForm.getDepartment();

		/*StringBuffer reqloc = new StringBuffer();
		for (int i = 0; i < loc.length; i++) {
			reqloc.append(loc[i] + ",");
		}
		String ACtualloc = reqloc.substring(0, reqloc.length() - 1).toString();

		StringBuffer reqcat = new StringBuffer();
		for (int i = 0; i < cat.length; i++) {
			reqcat.append(cat[i] + ",");
		}
		String ACtualcat = reqcat.substring(0, reqcat.length() - 1).toString();

		StringBuffer reqdept = new StringBuffer();
		for (int i = 0; i < dept.length; i++) {
			reqdept.append(dept[i] + ",");
		}
		String ACtualdept = reqdept.substring(0, reqdept.length() - 1)
				.toString();
		
		request.setAttribute("ResetSearch", "ResetSearch");*/
		
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		AlertForm.setLocationIdList(locationList);
		AlertForm.setLocationLabelList(locationLabelList);
		String reqFromDate = AlertForm.getFromDate();
		String a1[] = reqFromDate.split("/");
		reqFromDate = a1[2] + "-" + a1[1] + "-" + a1[0];
		String fromtime = AlertForm.getFromTime();
		String pm = "PM";
		if (fromtime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
			fromtime = fromtime.replaceAll("PM", "");
			String a[] = fromtime.split(":");
			if (Integer.parseInt(a[0]) != 12) {
				int hr = Integer.parseInt(a[0]) + 12;
				reqFromDate = reqFromDate + " " + hr + ":" + a[1];
			} else {
				fromtime = fromtime.replaceAll("PM", "");
				reqFromDate = reqFromDate + " " + fromtime;
			}
		} else {
			fromtime = fromtime.replaceAll("AM", "");
			reqFromDate = reqFromDate + " " + fromtime;
		}

		String reqToDate = AlertForm.getToDate();
		String b1[] = reqToDate.split("/");
		reqToDate = b1[2] + "-" + b1[1] + "-" + b1[0];
		String totime = AlertForm.getToTime();
		if (totime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
			totime = totime.replaceAll("PM", "");
			String a[] = totime.split(":");
			if (Integer.parseInt(a[0]) != 12) {
				int hr = Integer.parseInt(a[0]) + 12;
				reqToDate = reqToDate + " " + hr + ":" + a[1];
			} else {
				totime = totime.replaceAll("PM", "");
				reqToDate = reqToDate + " " + totime;
			}
		} else {
			totime = totime.replaceAll("AM", "");
			reqToDate = reqToDate + " " + totime;
		}

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String dateNow = ft.format(dNow);
		List resarvedList = new LinkedList();
		
		
		
	String checkAvilbilty = "select * from ALERT_BOX conf"
							+" where (Start_date between  '"+reqFromDate+"' and '"+reqToDate+"' ) or " 
						    +" (end_date between  '"+reqFromDate+"' and '"+reqToDate+"' ) or " 
					        + " ('"+reqFromDate+"' between start_date and end_date) or "
					        +" ('"+reqToDate+"' between Start_date and end_date) ";
							
	
		ResultSet rs = ad.selectQuery(checkAvilbilty);
		try {
			while (rs.next()) {
				AlertForm c = new AlertForm();
				c.setReqNo(rs.getString("Id"));
				c.setLocation(rs.getString("Location"));
				c.setCategory(rs.getString("StaffCategory"));
				c.setDep(rs.getString("Department"));
				c.setHeadLines1(rs.getString("Content_Heading"));
				String fromDate = rs.getString("Start_date");
				String b2[] = fromDate.split(" ");
				c.setFromDate1(b2[1]);
				c.setFromTime1(b2[0]);
				String toDate = rs.getString("End_date");
				String c2[] = toDate.split(" ");
				c.setToDate1(c2[1]);
				c.setToTime1(c2[0]);
				c.setSubject1(rs.getString("Content_subject"));
				
				resarvedList.add(c);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resarvedList.size() > 0) {
			request.setAttribute("reservedList", resarvedList);
		} else {
			request.setAttribute("reservedList", "");
            request.setAttribute("noRecords", "noRecords");
					}
		
		//displayList(mapping, AlertForm, request, response);
		return mapping.findForward("alertView");
	
		
		
	}


	
	public ActionForward getAlert(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AlertForm AlertForm = (AlertForm) form;
		UserDao ad = new UserDao();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			AlertForm.setId(id);

			String getAnnouncData = "select * from Alert_box where id='"
					+ id + "'";
			String annonDate = "";
			ResultSet rs = ad.selectQuery(getAnnouncData);
			while (rs.next()) {
				AlertForm.setHeadLines(rs.getString("Content_Heading"));
				AlertForm.setContent(rs.getString("Content_description"));
				String FromDt = rs.getString("start_date");
				String a[] = FromDt.split(" ");
				AlertForm.setFromTime(a[1]);
				String b[] = a[0].split("-");
				FromDt = b[2] + "/" + b[1] + "/" + b[0] ;
				AlertForm.setFromDate(FromDt);
				
				String ToDt = rs.getString("End_date");
				String c[] = ToDt.split(" ");
				AlertForm.setToTime(c[1]);
				String d[] = c[0].split("-");
				ToDt = d[2] + "/" + d[1] + "/" + d[0] ;
				AlertForm.setToDate(ToDt);
				
				
				AlertForm.setSubject(rs.getString("Content_subject"));
				AlertForm.setLocationId(rs.getString("Location").split(","));
				AlertForm.setCat(rs.getString("StaffCategory").split(","));
				AlertForm.setDepartment(rs.getString("Department").split(","));
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultSet rs11 = ad.selectQuery("select * from location ");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		while (rs11.next()) {
			locationList.add(rs11.getString("LOCATION_CODE"));
			locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
					+ rs11.getString("LOCNAME"));
		}
		AlertForm.setLocationIdList(locationList);
		AlertForm.setLocationLabelList(locationLabelList);

		ResultSet rs12 = ad.selectQuery("select * from DEPARTMENT ");
		ArrayList deptList = new ArrayList();
		ArrayList deptLabelList = new ArrayList();
		while (rs12.next()) {
			deptList.add(rs12.getString("DPTID"));
			deptLabelList.add(rs12.getString("DPTSTXT"));
		}
		AlertForm.setDeptIdList(deptList);
		AlertForm.setDeptLabelList(deptLabelList);

		AlertForm.setSaveType("Modify");
		request.setAttribute("modifyButton", "modifyButton");
		return mapping.findForward("addnewAlert");
	}
	
	public ActionForward updateAlert(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AlertForm AlertForm = (AlertForm) form;
		try {
			UserDao ad = new UserDao();
			int id = AlertForm.getId();
			Enumeration<String> params = request
					.getParameterNames();
			String parameter;
			String content_description = "";
			String existingEmpo ="";
			int present = 0;
			while (params.hasMoreElements()) {
				parameter = params.nextElement();
				if (parameter.equalsIgnoreCase("EditorDefault")) {
					content_description += request.getParameter(parameter);
				}
			}
			content_description = content_description.replaceAll("'", "`");
			AlertForm.setContent(content_description);
			String reqFromDate = AlertForm.getFromDate();
			String a1[] = reqFromDate.split("/");
			reqFromDate = a1[2] + "-" + a1[1] + "-" + a1[0];
			String fromtime = AlertForm.getFromTime();
			String pm = "PM";
			if (fromtime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
				fromtime = fromtime.replaceAll("PM", "");
				String a[] = fromtime.split(":");
				if (Integer.parseInt(a[0]) != 12) {
					int hr = Integer.parseInt(a[0]) + 12;
					reqFromDate = reqFromDate + " " + hr + ":" + a[1];
				} else {
					fromtime = fromtime.replaceAll("PM", "");
					reqFromDate = reqFromDate + " " + fromtime;
				}
			} else {
				fromtime = fromtime.replaceAll("AM", "");
				reqFromDate = reqFromDate + " " + fromtime;
			}

			String reqToDate = AlertForm.getToDate();
			String b1[] = reqToDate.split("/");
			reqToDate = b1[2] + "-" + b1[1] + "-" + b1[0];
			String totime = AlertForm.getToTime();
			if (totime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
				totime = totime.replaceAll("PM", "");
				String a[] = totime.split(":");
				if (Integer.parseInt(a[0]) != 12) {
					int hr = Integer.parseInt(a[0]) + 12;
					reqToDate = reqToDate + " " + hr + ":" + a[1];
				} else {
					totime = totime.replaceAll("PM", "");
					reqToDate = reqToDate + " " + totime;
				}
			} else {
				totime = totime.replaceAll("AM", "");
				reqToDate = reqToDate + " " + totime;
			}
			
			content_description = content_description.replaceAll("'", "''");

			String[] loc = AlertForm.getLocationId();
			String[] cat = AlertForm.getCat();
			String[] dept = AlertForm.getDepartment();

			StringBuffer reqloc = new StringBuffer();
			for (int i = 0; i < loc.length; i++) {
				reqloc.append(loc[i] + ",");
			}
			String ACtualloc = reqloc.substring(0, reqloc.length() - 1)
					.toString();

			StringBuffer reqcat = new StringBuffer();
			for (int i = 0; i < cat.length; i++) {
				reqcat.append(cat[i] + ",");
			}
			String ACtualcat = reqcat.substring(0, reqcat.length() - 1)
					.toString();

			StringBuffer reqdept = new StringBuffer();
			for (int i = 0; i < dept.length; i++) {
				reqdept.append(dept[i] + ",");
			}
			String ACtualdept = reqdept.substring(0, reqdept.length() - 1)
					.toString();
			
			StringBuffer reqStringloc = new StringBuffer();
			for (int i = 0; i < loc.length; i++) {
				 reqStringloc.append("'"+loc[i]+"'" + ",");
			}
			String ActStringloc =  reqStringloc.substring(0, reqStringloc.length() - 1).toString();
			
			
			
			/////////////////////////
			String emplist5 ="";
			String Empno2 = "select  empnos from Alert_box"
     				+" where ((Start_date between  '"+reqFromDate+"' and '"+reqToDate+"' ) or " 
	        	    +" (end_date between  '"+reqFromDate+"' and '"+reqToDate+"' ) or " 
		            + " ('"+reqFromDate+"' between start_date and end_date) or "
		            +" ('"+reqToDate+"' between Start_date and end_date)) and id <> '"+AlertForm.getId()+"'";

			ResultSet rs15 = ad.selectQuery(Empno2);
	
			while (rs15.next()) {
		
				emplist5 = rs15.getString("empnos");
		
			}
	
	
			//Checking with existing 
	
					String Empno3 = "select  PERNR from emp_official_info"
									+" where LOCID  in ("+ActStringloc+") and STAFFCAT in ("+ACtualcat+") and DPTID in ("+ACtualdept+") and active =1"; 
					
				
					ResultSet rs16 = ad.selectQuery(Empno3);
					while (rs16.next()) {
						if(present ==1)
						{
							break;
						}
						
						if(emplist5.contains(rs16.getString("PERNR")))
						{
							
							System.out.println(rs16.getString("PERNR"));
							existingEmpo = rs16.getString("PERNR");
							present = 1;
						    break;
						}
						
					}
								
							
							
			
			//////////////////////
			
			if(present == 0)
         
			{	
				
				ArrayList empList = new ArrayList();
				String Empno = "select  PERNR from emp_official_info"
								+" where LOCID  in ("+ActStringloc+") and STAFFCAT in ("+ACtualcat+") and DPTID in ("+ACtualdept+") and active =1"; 
				

				ResultSet rs14 = ad.selectQuery(Empno);
				
				while (rs14.next()) {
					
					empList.add(rs14.getString("PERNR"));
					
				}
				
				StringBuffer empList1 = new StringBuffer();
				for (int i = 0; i < empList.size(); i++) {
					empList1.append(empList.get(i)+ ",");
				}

				String ActualempList ="";
				
				if(empList1.length() >0)
				{	
					
				ActualempList = empList1.substring(0, empList1.length() - 1).toString();
				}
				
				
			
				
			String updateData = "update Alert_box set Content_Heading='"
					+ AlertForm.getHeadLines() + "',Content_description='"
					+ content_description + "'," + "Start_date='" + reqFromDate
					+ "',End_date='" + reqToDate
					+ "',Content_subject='" + AlertForm.getSubject() + "',Location='"
					+ ACtualloc + "',StaffCategory='" + ACtualcat + "',Department='"
					+ ACtualdept + "',Empnos='"+ActualempList+"' where id='" + AlertForm.getId() + "'";
			int i = 0;
			i = ad.SqlExecuteUpdate(updateData);
			
			HttpSession session = request.getSession();

			if (i > 0) {

				session.setAttribute("result",
						"alert Details Are Modified");
			} else {
				session.setAttribute("result",
						"Error When Modifying alert Details.Please Check.... ");
			}
			
			}
			else
			{
				HttpSession session = request.getSession();
				session.setAttribute("result","Error When Inserting alert Already Exists For this Group Employee.Please Check....  ");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		getAlert(mapping, AlertForm, request, response);
		return mapping.findForward("addnewAlert");
	}	
	
	public ActionForward deleteAlert(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AlertForm AlertForm = (AlertForm) form;
		try {
			UserDao ad = new UserDao();

			String deletData = "delete Alert_box  where id='"
					+ AlertForm.getId() + "'";
			int i = 0;
			i = ad.SqlExecuteUpdate(deletData);
			HttpSession session = request.getSession();

			if (i > 0) {

				session.setAttribute("result",
						"alert Details Are Deleted");
				AlertForm.setHeadLines("");
			} else {
				session.setAttribute("result",
						"Error When Deleting alert Details.Please Check.... ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		
		addnewAlert(mapping, AlertForm, request, response);
		return mapping.findForward("addnewAlert");
	}
}
