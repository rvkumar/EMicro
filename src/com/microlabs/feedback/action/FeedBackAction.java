package com.microlabs.feedback.action;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

import com.microlabs.feedback.dao.FeedBackDao;
import com.microlabs.feedback.form.FeedBackForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.UserInfo;
import com.microlabs.ess.action.LeaveAction;
import com.microlabs.ess.dao.EssDao;

public class FeedBackAction extends DispatchAction {

	public ActionForward sendReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("displayFeedBack");
			FeedBackForm feedBackForm = (FeedBackForm) form;
			FeedBackDao ad = new FeedBackDao();

			String saveStatus = "update feedBack_details set status='"
					+ feedBackForm.getApproveStatus() + "' where id='"
					+ feedBackForm.getSno() + "'";
			int i = 0;
			i = ad.SqlExecuteUpdate(saveStatus);

			if (i == 1) {
				feedBackForm
						.setMessage("Feedback Details Are Updated Successfully");
				getEmpFeedBack(mapping, form, request, response);
			} else {
				feedBackForm
						.setMessage("Error..While Updating FeedBack.Please Check... ");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		modifyFeedback(mapping, form, request, response);
		return mapping.findForward("modifyFeedbackForm");
	}

	public ActionForward getEmpFeedBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("displayFeedBack");
			FeedBackForm feedBackForm = (FeedBackForm) form;
			FeedBackDao ad = new FeedBackDao();
			int sno = Integer.parseInt(request.getParameter("sno"));
			String getEmpFeedback = "select * from feedBack_details where id='"
					+ sno + "'";
			ResultSet rsEmpFeedback = ad.selectQuery(getEmpFeedback);

			while (rsEmpFeedback.next()) {
				feedBackForm.setSno(rsEmpFeedback.getInt("id"));
				feedBackForm.setUserId(rsEmpFeedback.getString("user_id"));
				feedBackForm.setUserName(rsEmpFeedback.getString("user_name"));
				feedBackForm.setSubject(rsEmpFeedback.getString("subject"));
				feedBackForm.setComment(rsEmpFeedback.getString("comments"));
				feedBackForm.setEmailId(rsEmpFeedback.getString("email_id"));
				feedBackForm
						.setApproveStatus(rsEmpFeedback.getString("status"));
				feedBackForm.setHostname(rsEmpFeedback.getString("Host_Name"));
				feedBackForm.setIPNumber(rsEmpFeedback.getString("IP_Address"));
				feedBackForm.setAdloginname(rsEmpFeedback
						.getString("AdLoginname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("empfeedback", "empfeedback");
		String id = request.getParameter("id");
		request.setAttribute("MenuIcon", id);
		return mapping.findForward("modifyFeedbackForm");
	}

	public ActionForward modifyFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		System.out.println("displayFeedBack");
		FeedBackForm feedBackForm = (FeedBackForm) form;
		FeedBackDao ad = new FeedBackDao();
		int startRecord = 0;
		int endRecord = 0;
		int totalRecords =0;
		String getCount = "select * from feedBack_details order by id desc";
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
			feedBackForm.setTotalRecords(totalRecords);
			startRecord = 1;
			endRecord = 10;
			feedBackForm.setStartRecord(1);
			feedBackForm.setEndRecord(10);
			request.setAttribute("displayRecordNo", "displayRecordNo");
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			request.setAttribute("nextButton", "nextButton");
		} else {
			startRecord = 1;
			endRecord = totalRecords;
			feedBackForm.setTotalRecords(totalRecords);
			feedBackForm.setStartRecord(1);
			feedBackForm.setEndRecord(totalRecords);
		}

		
		
		
		try {
			String getFeedbackList = "select * from (select ROW_NUMBER() over (order by id) as rownum, *  from feedBack_details ) as sub where sub.RowNum between 1 and 10 order by id desc";
			ResultSet rsFeedbackList = ad.selectQuery(getFeedbackList);
			LinkedList listOfFeedList = new LinkedList();
			while (rsFeedbackList.next()) {
				FeedBackForm feedback = new FeedBackForm();
				feedback.setSno(rsFeedbackList.getInt("id"));
				feedback.setUserId(rsFeedbackList.getString("user_id"));
				feedback.setUserName(rsFeedbackList.getString("user_name"));
				feedback.setSubject(rsFeedbackList.getString("subject"));
				feedback.setComment(rsFeedbackList.getString("comments"));
				feedback.setEmailId(rsFeedbackList.getString("email_id"));
				feedback.setApproveStatus(rsFeedbackList.getString("status"));
				feedback.setHostname(rsFeedbackList.getString("Host_Name"));
				feedback.setIPNumber(rsFeedbackList.getString("IP_Address"));
				feedback.setAdloginname(rsFeedbackList.getString("AdLoginname"));

				listOfFeedList.add(feedback);
			}
			request.setAttribute("feedBackList", listOfFeedList);
			if (listOfFeedList.size() == 0) {
				request.setAttribute("noRecords", "noRecords");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String id = request.getParameter("id");
		request.setAttribute("MenuIcon", id);

		return mapping.findForward("modifyFeedbackForm");
	}
	
	
	/////////////
	
	public ActionForward previousRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		FeedBackDao ad = new FeedBackDao();
		FeedBackForm feedBackForm = (FeedBackForm) form;
		HttpSession session = request.getSession();
		
		UserInfo user = (UserInfo) session.getAttribute("user");
		try {
			int totalRecords = feedBackForm.getTotalRecords();// 21
			int endRecord = feedBackForm.getStartRecord() - 1;// 20
			int startRecord = feedBackForm.getStartRecord() - 10;// 11
			if (startRecord == 1) {
				request.setAttribute("disablePreviousButton",
						"disablePreviousButton");
				endRecord = 10;
			}
			feedBackForm.setTotalRecords(totalRecords);
			feedBackForm.setStartRecord(1);
			feedBackForm.setEndRecord(10);

		    
		    LinkedList listOfFeedList = new LinkedList();
       String getApprovers =  "select * from (select ROW_NUMBER() over (order by id) as rownum, *  from feedBack_details ) as sub where sub.RowNum between '"+startRecord+"' and '"+ endRecord +"' order by id desc";
				
		ResultSet rs = ad.selectQuery(getApprovers);
		try {
			while (rs.next()) {
				FeedBackForm feedback = new FeedBackForm();
				feedback.setSno(rs.getInt("id"));
				feedback.setUserId(rs.getString("user_id"));
				feedback.setUserName(rs.getString("user_name"));
				feedback.setSubject(rs.getString("subject"));
				feedback.setComment(rs.getString("comments"));
				feedback.setEmailId(rs.getString("email_id"));
				feedback.setApproveStatus(rs.getString("status"));
				feedback.setHostname(rs.getString("Host_Name"));
				feedback.setIPNumber(rs.getString("IP_Address"));
				feedback.setAdloginname(rs.getString("AdLoginname"));
				listOfFeedList.add(feedback);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		
		request.setAttribute("feedBackList", listOfFeedList);
		
		feedBackForm.setTotalRecords(totalRecords);
		feedBackForm.setStartRecord(startRecord);
		feedBackForm.setEndRecord(endRecord);
		request.setAttribute("nextButton", "nextButton");
		if (startRecord != 1)
			request.setAttribute("previousButton", "previousButton");
		request.setAttribute("displayRecordNo", "displayRecordNo");
		if (listOfFeedList.size() < 10) {
			feedBackForm.setStartRecord(1);
			request.setAttribute("previousButton", "");
			request.setAttribute("disablePreviousButton",
					"disablePreviousButton");
		}
		


	} catch (Exception e) {
		e.printStackTrace();
	}
	
		return mapping.findForward("modifyFeedbackForm");

	}


	////////

	///////////
	public ActionForward nextRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
	
		HttpSession session = request.getSession();
		EssDao ad = new EssDao();
		FeedBackForm feedBackForm = (FeedBackForm) form;
		LinkedList listOfFeedList = new LinkedList();
		
		UserInfo user = (UserInfo) session.getAttribute("user");

		
		try {
			int totalRecords = feedBackForm.getTotalRecords();// 21
			int startRecord = feedBackForm.getStartRecord();// 11
			int endRecord = feedBackForm.getEndRecord();

			if (totalRecords > endRecord) {
				if (totalRecords == endRecord) {
					startRecord = startRecord;
					endRecord = totalRecords;
				}
				if (totalRecords > endRecord) {
					startRecord = endRecord + 1;
					endRecord = endRecord + 10;
				}
		

		
			

		
		String getApprovers =  "select * from (select ROW_NUMBER() over (order by id) as rownum, *  from feedBack_details ) as sub where sub.RowNum between '"+startRecord+"' and '"+ endRecord +"' order by id desc";
		ResultSet rs = ad.selectQuery(getApprovers);
		try {
			while (rs.next()) {
				FeedBackForm feedback = new FeedBackForm();
				feedback.setSno(rs.getInt("id"));
				feedback.setUserId(rs.getString("user_id"));
				feedback.setUserName(rs.getString("user_name"));
				feedback.setSubject(rs.getString("subject"));
				feedback.setComment(rs.getString("comments"));
				feedback.setEmailId(rs.getString("email_id"));
				feedback.setApproveStatus(rs.getString("status"));
				feedback.setHostname(rs.getString("Host_Name"));
				feedback.setIPNumber(rs.getString("IP_Address"));
				feedback.setAdloginname(rs.getString("AdLoginname"));
				listOfFeedList.add(feedback);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		request.setAttribute("feedBackList", listOfFeedList);
		if (listOfFeedList.size() != 0) {
			feedBackForm.setTotalRecords(totalRecords);
			feedBackForm.setStartRecord(startRecord);
			feedBackForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("previousButton", "previousButton");
		} else {
			int start = startRecord;
			int end = startRecord;

			feedBackForm.setTotalRecords(totalRecords);
			feedBackForm.setStartRecord(start);
			feedBackForm.setEndRecord(end);

		}
		if (listOfFeedList.size() < 10) {
			feedBackForm.setTotalRecords(totalRecords);
			feedBackForm.setStartRecord(startRecord);
			feedBackForm.setEndRecord(startRecord + listOfFeedList.size() - 1);
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
		return mapping.findForward("modifyFeedbackForm");
}
			
	///////////
	public ActionForward displayFeedBack(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("displayFeedBack");
		FeedBackForm feedBackForm = (FeedBackForm) form;
		FeedBackDao ad = new FeedBackDao();
		HttpSession session = request.getSession();
		LinkedList feedBackList = new LinkedList();

		try {

			UserInfo user = (UserInfo) session.getAttribute("user");
			String getFeedBackDetails = "select * from feedBack_details where user_id='"
					+ user.getEmployeeNo() + "' and status='Approved'";
			ResultSet rsFeedBack = ad.selectQuery(getFeedBackDetails);
			while (rsFeedBack.next()) {
				FeedBackForm formDetails = new FeedBackForm();
				formDetails.setUserName(rsFeedBack.getString("user_name"));
				formDetails.setEmailId(rsFeedBack.getString("email_id"));
				formDetails.setSubject(rsFeedBack.getString("subject"));
				formDetails.setComment(rsFeedBack.getString("comments"));
				formDetails.setApproveStatus(rsFeedBack.getString("status"));
				feedBackList.add(formDetails);
			}

			if (rsFeedBack.next()) {
				request.setAttribute("feedBackList", feedBackList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("feedBackList", feedBackList);
		return mapping.findForward("displayFeedBackForm");

	}

	public ActionForward saveFeedBackDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		LeaveAction lAction = new LeaveAction();
		UserInfo user = (UserInfo) session.getAttribute("user");
		System.out.println("displayFeedBack");
		String Req_Id = user.getEmployeeNo();
		FeedBackForm feedBackForm = (FeedBackForm) form;
		FeedBackDao ad = new FeedBackDao();
		String localhostname = "";
		String ipaddress = "";
		String pcname = "";
		String Adname = "";

		ipaddress = request.getHeader("X-FORWARDED-FOR");
		if (ipaddress == null) {
			ipaddress = request.getRemoteAddr();
		}

		InetAddress inaHost = null;
		try {
			inaHost = InetAddress.getByName(request.getRemoteAddr());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hostname = inaHost.getHostName();
		System.out.println("[[ Hostname = " + hostname + " ]]");

		String userName = user.getFullName();

		String saveFeedBackQuery = "insert into feedBack_details(user_id,user_name,email_id,subject,comments,status,Req_Id,Host_Name,IP_Address,AdLoginname)"
				+ " values('"
				+ user.getEmployeeNo()
				+ "','"
				+ userName
				+ "','"
				+ user.getMail_id()
				+ "','"
				+ feedBackForm.getSubject()
				+ "',"
				+ "'"
				+ feedBackForm.getComment()
				+ "','Pending','"
				+ Req_Id
				+ "','" + pcname + "','" + ipaddress + "','" + Adname + "')";

		System.out.println("saveFeedBackQuery=" + saveFeedBackQuery);
		int i = ad.SqlExecuteUpdate(saveFeedBackQuery);

		if (i == 1) {
			feedBackForm.setMessage("FeedBack Successfully Sent");
			feedBackForm.setSubject("");
			feedBackForm.setComment("");
			EMailer email = new EMailer();
			// i = email.sendMailToApprover(request, "",Req_Id,"Feedback");
			if (i > 0) {
				displayFeedBack(mapping, form, request, response);
			} else {
				feedBackForm
						.setMessage("Error..While Sending FeedBack.Please Check... ");
			}
		} else {
			feedBackForm
					.setMessage("Error..While Saving FeedBack.Please Check... ");

		}
		return mapping.findForward("displayFeedBackForm");
	}

}
