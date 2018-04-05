package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.form.ESSApproverForm;
import com.microlabs.admin.form.ITApproverForm;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.UserInfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.HttpURLConnection;

public class ITApproverAction extends DispatchAction {

	NewsandMediaDao ad = new NewsandMediaDao();

	public ActionForward searchEmpRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ITApproverForm help = (ITApproverForm) form;
		LinkedList appr = new LinkedList();
		EssDao ad = new EssDao();
		String subcategory = request.getParameter("subCategory");
		help.setEssType(subcategory);
		request.setAttribute("getSubCategory", "getSubCategory");

		String getAvalableApprovers = "select loc.LOCATION_CODE,it.Category,it.Req_Type,count(*) as total from IT_Approvers as  it ,"
				+ "emp_official_info as emp,Location as loc where it.Approver_ID=emp.PERNR and loc.LOCID=it.LOCATION_ID and LOCATION_CODE='"
				+ help.getLocationId()
				+ "'"
				+ " and Category='"
				+ help.getRequestType()
				+ "' and Req_Type='"
				+ subcategory
				+ "' group by loc.LOCATION_CODE,it.Req_Type,it.Category";
		ResultSet rs = ad.selectQuery(getAvalableApprovers);
		try {
			while (rs.next()) {
				ITApproverForm masterForm1 = new ITApproverForm();
				masterForm1.setLocation(rs.getString("LOCATION_CODE"));
				masterForm1.setCategory(rs.getString("Category"));
				masterForm1.setRequestType(rs.getString("Req_Type"));
				masterForm1.setTotalRecords(rs.getInt("total"));
				appr.add(masterForm1);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		if (appr.size() > 0)
		{
			request.setAttribute("Approvers", appr);
			
		}
		
		else
			request.setAttribute("noApprovers", "noApprovers");
		
			//request.setAttribute("displayRecordNo", "displayRecordNo");
		ResultSet rs11 = ad.selectQuery("select * from location ");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		help.setLocationIdList(locationList);
		help.setLocationLabelList(locationLabelList);
		return mapping.findForward("newapprover");
	}

	public ActionForward getsubcategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ITApproverForm help = (ITApproverForm) form;
		// TODO Auto-generated method stub
		String linkName = request.getParameter("linkName");

		try {

			ArrayList subCategoryList = new ArrayList();
			if (linkName.equalsIgnoreCase("Hardware")) {
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

			}

			if (linkName.equalsIgnoreCase("Internet")) {
				subCategoryList.add("Browser");
				subCategoryList.add("Web Site Link");
			}

			if (linkName.equalsIgnoreCase("Network")) {
				subCategoryList.add("LAN");
				subCategoryList.add("VOIP PHONE");
				subCategoryList.add("WI-FI");
			}

			if (linkName.equalsIgnoreCase("Operating System")) {
				subCategoryList.add("WINDOWS XP");
				subCategoryList.add("WINDOWS 7");
				subCategoryList.add("WINDOWS 8");
			}
			if (linkName.equalsIgnoreCase("Printers")) {
				subCategoryList.add("Connectivity");
				subCategoryList.add("Breakdown");
				subCategoryList.add("Tonner");
				subCategoryList.add("Cartridge");
			}

			if (linkName.equalsIgnoreCase("Software")) {
				subCategoryList.add("EMICRO");
				subCategoryList.add("NAVADISHA");
				subCategoryList.add("LOTUS MAIL");
				subCategoryList.add("MS OFFICE");
				subCategoryList.add("Adobe");
				subCategoryList.add("Legacy Application ");
				subCategoryList.add("Backup");
				subCategoryList.add("Citrix");

			}

			if (linkName.equalsIgnoreCase("SAP")) {
				subCategoryList.add("Purchase");
				subCategoryList.add("RM Stores");
				subCategoryList.add("PM Stores");
				subCategoryList.add("FG Stores");
				subCategoryList.add("General Stores");
				subCategoryList.add("Customs");
				subCategoryList.add("QC");
				subCategoryList.add("QA");
				subCategoryList.add("Production");
				subCategoryList.add("Packing");
				subCategoryList.add("Planning / SCM");
				subCategoryList.add("Engineering");
				subCategoryList.add("Depot");
				subCategoryList.add("Accounts");
				subCategoryList.add("Distribution");
				subCategoryList.add("Costing");
				subCategoryList.add("CQA");
				subCategoryList.add("HR");
				subCategoryList.add("Marketing");
				subCategoryList.add("Logistics");
				subCategoryList.add("Regulatory");
				subCategoryList.add("R&D");
				subCategoryList.add("Excise");
				subCategoryList.add("IT");
				subCategoryList.add("BASIS");

			}

			if (linkName.equalsIgnoreCase("IT SERVICES")) {
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

			if (linkName.equalsIgnoreCase("Telephone")) {
				subCategoryList.add("Land Line");
				subCategoryList.add("Black Berry");
				subCategoryList.add("Android");
				subCategoryList.add("Windows");
				subCategoryList.add("Iphone");

			}

			help.setSubcatList(subCategoryList);

			request.setAttribute("subCategoryList", subCategoryList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// displayMainLinks(mapping, form, request, response);
		return mapping.findForward("adminsubcategoryajax");

	}

	public ActionForward searchForApprovers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		ITApproverForm masterForm = (ITApproverForm) form;

		String sTxt = request.getParameter("searchText");
		String reqFieldName = request.getParameter("reqFieldName");

		String Loc = request.getParameter("loc");

		masterForm.setSearchText(sTxt);
		MailInboxAction mAction = new MailInboxAction();
		LinkedList searchList = new LinkedList();
		try {
			String searchQuery = "select * from emp_official_info as emp where (EMP_FULLNAME like '%"
					+ sTxt
					+ "%' or  EMAIL_ID like '%"
					+ sTxt
					+ "%' or PERNR like '%" + sTxt + "%')  ";
			ResultSet rs = ad.selectQuery(searchQuery);
			int i = 1;
			while (rs.next()) {

				ESSApproverForm emp = new ESSApproverForm();
				emp.setEmp(rs.getString("EMP_FULLNAME") + "-"
						+ rs.getString("PERNR"));
				emp.setReqFieldName(reqFieldName);
				searchList.add(emp);
				if (i == 4) {
					break;
				}
				i++;
			}

		}

		catch (SQLException sqle) {
			System.out.println("SQLException @ searching User Details");
			sqle.printStackTrace();
		}

		request.setAttribute("SearchUserDetails", searchList);

		return mapping.findForward("searchITApprovers");
	}

	public ActionForward modifyApprovers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ITApproverForm masterForm = (ITApproverForm) form;
		String subCategory = request.getParameter("subCategory");
		String approver1 = masterForm.getApprover1();
		String approver2 = masterForm.getApprover2();
		String approver3 = masterForm.getApprover3();
		String approver4 = masterForm.getApprover4();
		String approver5 = masterForm.getApprover5();
		String approver6 = masterForm.getApprover6();

		String loc = masterForm.getLocationId();
		int i = 0;

		String deleteEssApprovers = "delete from IT_Approvers where Req_Type='"
				+ subCategory + "' and Category='" + masterForm.getCategory()
				+ "' and LOCATION_ID='" + loc + "'";
		i = ad.SqlExecuteUpdate(deleteEssApprovers);

		if (!(approver1.equalsIgnoreCase(""))) {
			String saveEssApprovers = "insert into IT_Approvers(Req_Type,Priority,Approver_ID,LOCATION_ID,Category)"
					+ " values('"
					+ subCategory
					+ "','"
					+ masterForm.getPriority1()
					+ "','"
					+ masterForm.getApprover1()
					+ "','"
					+ loc
					+ "','"
					+ masterForm.getCategory() + "')";
			i = ad.SqlExecuteUpdate(saveEssApprovers);
		}
		if (!(approver2.equalsIgnoreCase(""))) {
			String saveEssApprovers = "insert into IT_Approvers(Req_Type,Priority,Approver_ID,LOCATION_ID,Category)"
					+ " values('"
					+ subCategory
					+ "','"
					+ masterForm.getPriority2()
					+ "','"
					+ masterForm.getApprover2()
					+ "','"
					+ loc
					+ "','"
					+ masterForm.getCategory() + "')";
			i = ad.SqlExecuteUpdate(saveEssApprovers);
		}
		if (!(approver3.equalsIgnoreCase(""))) {
			String saveEssApprovers = "insert into IT_Approvers(Req_Type,Priority,Approver_ID,LOCATION_ID,Category)"
					+ " values('"
					+ subCategory
					+ "','"
					+ masterForm.getPriority3()
					+ "','"
					+ masterForm.getApprover3()
					+ "','"
					+ loc
					+ "','"
					+ masterForm.getCategory() + "')";
			i = ad.SqlExecuteUpdate(saveEssApprovers);
		}

		if (!(approver4.equalsIgnoreCase(""))) {
			String saveEssApprovers = "insert into IT_Approvers(Req_Type,Priority,Approver_ID,LOCATION_ID,Category)"
					+ " values('"
					+ subCategory
					+ "','"
					+ masterForm.getPriority4()
					+ "','"
					+ masterForm.getApprover4()
					+ "','"
					+ loc
					+ "','"
					+ masterForm.getCategory() + "')";
			i = ad.SqlExecuteUpdate(saveEssApprovers);
		}
		if (!(approver5.equalsIgnoreCase(""))) {
			String saveEssApprovers = "insert into IT_Approvers(Req_Type,Priority,Approver_ID,LOCATION_ID,Category)"
					+ " values('"
					+ subCategory
					+ "','"
					+ masterForm.getPriority5()
					+ "','"
					+ masterForm.getApprover5()
					+ "','"
					+ loc
					+ "','"
					+ masterForm.getCategory() + "')";
			i = ad.SqlExecuteUpdate(saveEssApprovers);
		}
		if (!(approver6.equalsIgnoreCase(""))) {
			String saveEssApprovers = "insert into IT_Approvers(Req_Type,Priority,Approver_ID,LOCATION_ID,Category)"
					+ " values('"
					+ subCategory
					+ "','"
					+ masterForm.getPriority6()
					+ "','"
					+ masterForm.getApprover6()
					+ "','"
					+ loc
					+ "','"
					+ masterForm.getCategory() + "')";
			i = ad.SqlExecuteUpdate(saveEssApprovers);
		}
		if (i > 0) {
			masterForm.setMessage("Data Updated Successfully");
		} else {
			masterForm.setMessage("Error..Data Not Updated");
		}

		request.setAttribute("modifyButton", "modifyButton");
		return mapping.findForward("addApprover");
	}

	public ActionForward deleteApprovers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ITApproverForm masterForm = (ITApproverForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		String loc = request.getParameter("loc");
		String reqtype = request.getParameter("reqtype");

		String deleteApprovers = "delete IT_Approvers where  Req_Type='"
				+ reqtype
				+ "' and LOCATION_ID=(select LOCID from Location where "
				+ "LOCATION_CODE='" + loc + "')";
		int i = ad.SqlExecuteUpdate(deleteApprovers);
		if (i > 0) {
			masterForm.setMessage2("Approvers has been deleted.");
		} else {
			masterForm.setMessage("Error..While deleting approvers");
		}
		displayItApprover(mapping, masterForm, request, response);
		return mapping.findForward("newapprover");
	}

	public ActionForward editApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ITApproverForm masterForm = (ITApproverForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		String loc = request.getParameter("loc");

		String approver1 = masterForm.getApprover1();
		String approver2 = masterForm.getApprover2();
		String approver3 = masterForm.getApprover3();
		String approver4 = masterForm.getApprover4();
		String approver5 = masterForm.getApprover5();
		String approver6 = masterForm.getApprover6();

		ResultSet rs11 = ad.selectQuery("select * from location ");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (locationList.size() == 0) {
			locationList.add("");
			locationLabelList.add("");
		}

		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);

		String reqtype = request.getParameter("reqtype");

		masterForm.setLocation(loc);

		String data = "select * from IT_Approvers where  Req_Type='"
				+ reqtype
				+ "' and LOCATION_ID=(select LOCID from Location where LOCATION_CODE='"
				+ loc + "')";
		ResultSet rs = ad.selectQuery(data);
		try {
			while (rs.next()) {
				masterForm.setCategory(rs.getString("Category"));

				request.setAttribute("subCategory", reqtype);
				int priority = rs.getInt("Priority");
				if (priority == 1) {
					masterForm.setApprover1(rs.getString("Approver_ID"));
				}
				if (priority == 2) {
					masterForm.setApprover2(rs.getString("Approver_ID"));
				}
				if (priority == 3) {
					masterForm.setApprover3(rs.getString("Approver_ID"));
				}
				if (priority == 4) {
					masterForm.setApprover4(rs.getString("Approver_ID"));
				}
				if (priority == 5) {
					masterForm.setApprover5(rs.getString("Approver_ID"));
				}
				if (priority == 6) {
					masterForm.setApprover6(rs.getString("Approver_ID"));
				}

				masterForm.setLocationId(rs.getString("LOCATION_ID"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		request.setAttribute("getSubCategory", "getSubCategory");
		masterForm.setEssType(reqtype);
		request.setAttribute("modifyButton", "modifyButton");
		return mapping.findForward("addApprover");

	}

	public ActionForward displayItApprover(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ITApproverForm masterForm = (ITApproverForm) form;
		HttpSession session = request.getSession();
		
	/*	
		 String sender = "MLLSFA";
         String to = "9738886815";
         String message = "Hello";


         String requestUrl;
		try {
			requestUrl = "http://alerts.sinfini.com/api/web2sms.php?workingkey=A9c2daeb20cef1aa1ac36fdf9acf6ffd7&"  +        
"sender=" + URLEncoder.encode(sender, "UTF-8") +
"&to=" + URLEncoder.encode(to, "UTF-8") +
"&message=" + URLEncoder.encode(message, "UTF-8");
		




         URL url1;
		try {
			url1 = new URL(requestUrl);
	
			HttpURLConnection uc = (HttpURLConnection)url1.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		
		UserInfo user = (UserInfo) session.getAttribute("user");

		LinkedList appr = new LinkedList();
		
		int startRecord = 0;
		int endRecord = 0;
		int totalRecords =0;
		String getCount = "select req_type ,approver_id from IT_Approvers  group by req_type ,approver_id";
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
			masterForm.setTotalRecords(totalRecords);
			startRecord = 1;
			endRecord = 10;
			masterForm.setStartRecord(1);
			masterForm.setEndRecord(10);
			request.setAttribute("displayRecordNo", "displayRecordNo");
			request.setAttribute("nextButton", "nextButton");
		} else {
			startRecord = 1;
			endRecord = totalRecords;
			masterForm.setTotalRecords(totalRecords);
			masterForm.setStartRecord(1);
			masterForm.setEndRecord(totalRecords);
		}
		
		int adTotApprs = 0;
		int lotuscreation = 0;
		String adUserApprs = "";
		String lotusIDCreateApprs = "";
		String lotusIDChangeApprs = "";
		String internetAccessApprs = "";
		String ExternalDriverApprs = "";
		String ftpAccessApprs = "";
		String folderAccess = "";

		
		
		String getApprovers = "Select * From (select ROW_NUMBER() OVER(ORDER BY loc.LOCATION_CODE) AS RowNum ,loc.LOCATION_CODE,it.Category,it.Req_Type,count(*) as total from IT_Approvers as  it ,emp_official_info as emp,Location as loc where "
				+ "it.Approver_ID=emp.PERNR and loc.LOCID=it.LOCATION_ID group by loc.LOCATION_CODE,it.Req_Type,it.Category)  as  sub Where  sub.RowNum between 1 and 10   ";
		ResultSet rs = ad.selectQuery(getApprovers);
		try {
			while (rs.next()) {
				ITApproverForm masterForm1 = new ITApproverForm();
				masterForm1.setLocation(rs.getString("LOCATION_CODE"));
				masterForm1.setCategory(rs.getString("Category"));
				masterForm1.setRequestType(rs.getString("Req_Type"));
				masterForm1.setTotalRecords(rs.getInt("total"));
				appr.add(masterForm1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		if (appr.size() == 0) {
			request.setAttribute("noRecords", "noRecords");
			masterForm.setMessage1("No Records Found");
			masterForm.setMessage2("");
		}
		request.setAttribute("Approvers", appr);
		request.setAttribute("disablePreviousButton",
				"disablePreviousButton");

		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);

			
		return mapping.findForward("newapprover");

	}


	public ActionForward previousRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ITApproverForm masterForm = (ITApproverForm) form;
		HttpSession session = request.getSession();
		
		UserInfo user = (UserInfo) session.getAttribute("user");
		try {
			int totalRecords = masterForm.getTotalRecords();// 21
			int endRecord = masterForm.getStartRecord() - 1;// 20
			int startRecord = masterForm.getStartRecord() - 10;// 11
			if (startRecord == 1) {
				request.setAttribute("disablePreviousButton",
						"disablePreviousButton");
				endRecord = 10;
			}
			masterForm.setTotalRecords(totalRecords);
			masterForm.setStartRecord(1);
			masterForm.setEndRecord(10);

		LinkedList appr = new LinkedList();
		


		
		
		String getApprovers = "Select * From (select ROW_NUMBER() OVER(ORDER BY loc.LOCATION_CODE) AS RowNum ,loc.LOCATION_CODE,it.Category,it.Req_Type,count(*) as total from IT_Approvers as  it ,emp_official_info as emp,Location as loc where "
				+ "it.Approver_ID=emp.PERNR and loc.LOCID=it.LOCATION_ID group by loc.LOCATION_CODE,it.Req_Type,it.Category)  as  sub Where  sub.RowNum between '"
					+ startRecord + "' and '" + endRecord + "'";
		ResultSet rs = ad.selectQuery(getApprovers);
		try {
			while (rs.next()) {
				ITApproverForm masterForm1 = new ITApproverForm();
				masterForm1.setLocation(rs.getString("LOCATION_CODE"));
				masterForm1.setCategory(rs.getString("Category"));
				masterForm1.setRequestType(rs.getString("Req_Type"));
				masterForm1.setTotalRecords(rs.getInt("total"));
				appr.add(masterForm1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		
		request.setAttribute("Approvers", appr);
		
		masterForm.setTotalRecords(totalRecords);
		masterForm.setStartRecord(startRecord);
		masterForm.setEndRecord(endRecord);
		request.setAttribute("nextButton", "nextButton");
		if (startRecord != 1)
			request.setAttribute("previousButton", "previousButton");
		request.setAttribute("displayRecordNo", "displayRecordNo");
		if (appr.size() < 10) {
			masterForm.setStartRecord(1);
			request.setAttribute("previousButton", "");
			request.setAttribute("disablePreviousButton",
					"disablePreviousButton");
		}
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);


	} catch (Exception e) {
		e.printStackTrace();
	}
	
		return mapping.findForward("newapprover");

	}

	
	public ActionForward nextRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ITApproverForm masterForm = (ITApproverForm) form;
		HttpSession session = request.getSession();
		
		UserInfo user = (UserInfo) session.getAttribute("user");

		
		try {
			int totalRecords = masterForm.getTotalRecords();// 21
			int startRecord = masterForm.getStartRecord();// 11
			int endRecord = masterForm.getEndRecord();

			if (totalRecords > endRecord) {
				if (totalRecords == endRecord) {
					startRecord = startRecord;
					endRecord = totalRecords;
				}
				if (totalRecords > endRecord) {
					startRecord = endRecord + 1;
					endRecord = endRecord + 10;
				}
		

		
		LinkedList appr = new LinkedList();
		
		
		
		int adTotApprs = 0;
		int lotuscreation = 0;
		String adUserApprs = "";
		String lotusIDCreateApprs = "";
		String lotusIDChangeApprs = "";
		String internetAccessApprs = "";
		String ExternalDriverApprs = "";
		String ftpAccessApprs = "";
		String folderAccess = "";

		
		
		String getApprovers = "Select * From (select ROW_NUMBER() OVER(ORDER BY loc.LOCATION_CODE) AS RowNum ,loc.LOCATION_CODE,it.Category,it.Req_Type,count(*) as total from IT_Approvers as  it ,emp_official_info as emp,Location as loc where "
				+ "it.Approver_ID=emp.PERNR and loc.LOCID=it.LOCATION_ID group by loc.LOCATION_CODE,it.Req_Type,it.Category)  as  sub Where  sub.RowNum between '"
						+ startRecord + "' and '" + endRecord + "'";
		ResultSet rs = ad.selectQuery(getApprovers);
		try {
			while (rs.next()) {
				ITApproverForm masterForm1 = new ITApproverForm();
				masterForm1.setLocation(rs.getString("LOCATION_CODE"));
				masterForm1.setCategory(rs.getString("Category"));
				masterForm1.setRequestType(rs.getString("Req_Type"));
				masterForm1.setTotalRecords(rs.getInt("total"));
				appr.add(masterForm1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		request.setAttribute("Approvers", appr);
		if (appr.size() != 0) {
			masterForm.setTotalRecords(totalRecords);
			masterForm.setStartRecord(startRecord);
			masterForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("previousButton", "previousButton");
		} else {
			int start = startRecord;
			int end = startRecord;

			masterForm.setTotalRecords(totalRecords);
			masterForm.setStartRecord(start);
			masterForm.setEndRecord(end);

		}
		if (appr.size() < 10) {
			masterForm.setTotalRecords(totalRecords);
			masterForm.setStartRecord(startRecord);
			masterForm.setEndRecord(startRecord + appr.size() - 1);
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

		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		

	}
	
	} catch (Exception e) {
		e.printStackTrace();
	}
		return mapping.findForward("newapprover");
}
			
			
			
			
		
				
			

	
	public ActionForward newApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("saveButton", "saveButton");
		ITApproverForm masterForm = (ITApproverForm) form;

		ResultSet rs11 = ad.selectQuery("select * from location ");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (locationList.size() == 0) {
			locationList.add("");
			locationLabelList.add("");
		}
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);

		masterForm.setCategory("");
		masterForm.setEssType("");
		masterForm.setLocationId("");

		return mapping.findForward("addApprover");
	}

	public ActionForward addApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ITApproverForm masterForm = (ITApproverForm) form;
		String subCategory = request.getParameter("subCategory");
		String approver1 = masterForm.getApprover1();
		String approver2 = masterForm.getApprover2();
		String approver3 = masterForm.getApprover3();
		String approver4 = masterForm.getApprover4();
		String approver5 = masterForm.getApprover5();
		String approver6 = masterForm.getApprover6();

		String loc = masterForm.getLocationId();

		int i = 0;
		int count = 0;

		// chk approvers already present

		String checkAppr = "select count(*) from IT_Approvers  where LOCATION_ID ='"
				+ loc
				+ "' and Req_Type='"
				+ subCategory
				+ "' and Category='"
				+ masterForm.getCategory() + "'";
		ResultSet rsCheckAppr = ad.selectQuery(checkAppr);

		try {
			while (rsCheckAppr.next()) {
				count = rsCheckAppr.getInt(1);
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		if (count == 0)

		{
			if (!(approver1.equalsIgnoreCase(""))) {
				String saveEssApprovers = "insert into IT_Approvers(Req_Type,Priority,Approver_ID,LOCATION_ID,Category)"
						+ " values('"
						+ subCategory
						+ "','"
						+ masterForm.getPriority1()
						+ "','"
						+ masterForm.getApprover1()
						+ "','"
						+ loc
						+ "','"
						+ masterForm.getCategory() + "')";
				i = ad.SqlExecuteUpdate(saveEssApprovers);
			}
			if (!(approver2.equalsIgnoreCase(""))) {
				String saveEssApprovers = "insert into IT_Approvers(Req_Type,Priority,Approver_ID,LOCATION_ID,Category)"
						+ " values('"
						+ subCategory
						+ "','"
						+ masterForm.getPriority2()
						+ "','"
						+ masterForm.getApprover2()
						+ "','"
						+ loc
						+ "','"
						+ masterForm.getCategory() + "')";
				i = ad.SqlExecuteUpdate(saveEssApprovers);
			}
			if (!(approver3.equalsIgnoreCase(""))) {
				String saveEssApprovers = "insert into IT_Approvers(Req_Type,Priority,Approver_ID,LOCATION_ID,Category)"
						+ " values('"
						+ subCategory
						+ "','"
						+ masterForm.getPriority3()
						+ "','"
						+ masterForm.getApprover3()
						+ "','"
						+ loc
						+ "','"
						+ masterForm.getCategory() + "')";
				i = ad.SqlExecuteUpdate(saveEssApprovers);
			}

			if (!(approver4.equalsIgnoreCase(""))) {
				String saveEssApprovers = "insert into IT_Approvers(Req_Type,Priority,Approver_ID,LOCATION_ID,Category)"
						+ " values('"
						+ subCategory
						+ "','"
						+ masterForm.getPriority4()
						+ "','"
						+ masterForm.getApprover4()
						+ "','"
						+ loc
						+ "','"
						+ masterForm.getCategory() + "')";
				i = ad.SqlExecuteUpdate(saveEssApprovers);
			}
			if (!(approver5.equalsIgnoreCase(""))) {
				String saveEssApprovers = "insert into IT_Approvers(Req_Type,Priority,Approver_ID,LOCATION_ID,Category)"
						+ " values('"
						+ subCategory
						+ "','"
						+ masterForm.getPriority5()
						+ "','"
						+ masterForm.getApprover5()
						+ "','"
						+ loc
						+ "','"
						+ masterForm.getCategory() + "')";
				i = ad.SqlExecuteUpdate(saveEssApprovers);
			}
			if (!(approver6.equalsIgnoreCase(""))) {
				String saveEssApprovers = "insert into IT_Approvers(Req_Type,Priority,Approver_ID,LOCATION_ID,Category)"
						+ " values('"
						+ subCategory
						+ "','"
						+ masterForm.getPriority6()
						+ "','"
						+ masterForm.getApprover6()
						+ "','"
						+ loc
						+ "','"
						+ masterForm.getCategory() + "')";
				i = ad.SqlExecuteUpdate(saveEssApprovers);
			}

			if (i > 0) {
				masterForm.setMessage("Data Saved Successfully");
				masterForm.setCategory("");
				masterForm.setEssType("");
				masterForm.setEmployeeNo("");
				masterForm.setApprover1("");
				masterForm.setApprover2("");
				masterForm.setApprover3("");
				masterForm.setApprover4("");
				masterForm.setApprover5("");
				masterForm.setApprover6("");

			} else {
				masterForm.setMessage("Error..Data Not Saved");
			}

		} else {
			masterForm
					.setMessage("Approvers already Assigned For This  Selection");
		}
		ResultSet rs11 = ad.selectQuery("select * from location ");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (locationList.size() == 0) {
			locationList.add("");
			locationLabelList.add("");
		}
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);

		request.setAttribute("saveButton", "saveButton");
		return mapping.findForward("addApprover");

	}

}
