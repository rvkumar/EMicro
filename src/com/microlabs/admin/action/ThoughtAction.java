package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
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

import com.microlabs.admin.form.ThoughtForm;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class ThoughtAction extends DispatchAction {

	public ActionForward modifyThoughts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		EssDao ad1 = new EssDao();
		ThoughtForm thought = (ThoughtForm) form;
		LinkedList thoughtdetails = new LinkedList();
		int i = 0;
		String reqPriority = thought.getReqPriority();
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
		String dateNow = ft.format(dNow);
		String thoughtDate = thought.getDisplayDate();
		thought.setDisplayDate(thoughtDate);
		String a[] = thoughtDate.split("/");
		thoughtDate = a[2] + "-" + a[1] + "-" + a[0];

		String update = " UPDATE THOUGHT_FOR_THE_DAY set Description='"
				+ thought.getTextthoughts() + "' ,Status='"
				+ thought.getStatus() + "'," + "Display_date='" + thoughtDate
				+ "',MODIFIED_BY='" + user.getEmployeeNo()
				+ "',MODIFIED_DATE='" + dateNow + "' " + "where Priority='"
				+ reqPriority + "'";
		i = ad1.SqlExecuteUpdate(update);
		if (i > 0) {
			int ij = ad1
					.SqlExecuteUpdate("UPDATE THOUGHT_FOR_THE_DAY set Status='Off' where Priority!='"
							+ reqPriority + "'");
			thought.setMessage("Thought modified successfully");
			request.setAttribute("message", "message");
		} else {
			thought.setMessage2("Error..Please check");
			request.setAttribute("message2", "message2");
		}

		request.setAttribute("ModifyButton", "ModifyButton");
		return mapping.findForward("NewThought");
	}

	public ActionForward Editthoughts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EssDao ad1 = new EssDao();
		ThoughtForm thought = (ThoughtForm) form;
		LinkedList thoughtdetails = new LinkedList();
		String reqPriority = request.getParameter("priority");
		thought.setReqPriority(reqPriority);
		String edit = "  select * from THOUGHT_FOR_THE_DAY where Priority='"
				+ reqPriority + "'";

		ResultSet rs = ad1.selectQuery(edit);
		try {
			if (rs.next()) {

				thought.setTextthoughts(rs.getString("Description"));

				thought.setPriority(rs.getString("Priority"));
				thought.setStatus(rs.getString("Status"));
				thought.setDisplayDate(EMicroUtils.display(rs
						.getDate("Display_date")));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		request.setAttribute("ModifyButton", "ModifyButton");
		return mapping.findForward("NewThought");

	}

	public ActionForward Deletethoughts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EssDao ad1 = new EssDao();
		ThoughtForm thought = (ThoughtForm) form;
		LinkedList thoughtdetails = new LinkedList();
		String Priority = request.getParameter("reqpriority");
		try {
			int maxPriority = 0;
			String getCount = "select count(*) from THOUGHT_FOR_THE_DAY ";
			ResultSet rsCount = ad1.selectQuery(getCount);
			while (rsCount.next()) {
				maxPriority = rsCount.getInt(1);
			}

			int i = 0;
			String delete = " delete THOUGHT_FOR_THE_DAY where Priority='"
					+ Priority + "' ";
			i = ad1.SqlExecuteUpdate(delete);
			if (i > 0) {
				thought.setMessage("Thought Deleted successfully");
				request.setAttribute("message", "message");
			} else {
				thought.setMessage2("Error..Please check");
				request.setAttribute("message2", "message2");
			}

			int priorty = Integer.parseInt(Priority);
			if (priorty < maxPriority) {
				for (int j = priorty; j <= maxPriority; j++) {

					String updatePriority = "update THOUGHT_FOR_THE_DAY set Priority='"
							+ priorty
							+ "' where Priority='"
							+ (priorty + 1)
							+ "'";
					int k = ad1.SqlExecuteUpdate(updatePriority);
					System.out.println(k);
					priorty++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String display = " select Select * From (select ROW_NUMBER() OVER(ORDER BY loc.Description) AS RowNum ,Description"
						 + " from THOUGHT_FOR_THE_DAY order by Priority ";
		ResultSet rs = ad1.selectQuery(display);
		try {
			while (rs.next()) {
				ThoughtForm thought1 = new ThoughtForm();
				thought1.setDescription(rs.getString("Description"));
				thought1.setDisplayDate(EMicroUtils.display(rs
						.getDate("Display_date")));
				thought1.setPriority(rs.getString("Priority"));
				thoughtdetails.add(thought1);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		request.setAttribute("thoughtdetails", thoughtdetails);
		return mapping.findForward("Thought");
	}

	public ActionForward resetthought(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ThoughtForm thought = (ThoughtForm) form;
		thought.setTextthoughts("");
		thought.setPriority("");

		request.setAttribute("saveButton", "saveButton");
		request.setAttribute("rejectButton", "rejectButton");
		return mapping.findForward("NewThought");

	}

	public ActionForward displaythought(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EssDao ad1 = new EssDao();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		ThoughtForm thought = (ThoughtForm) form;
	    LinkedList thoughtdetails = new LinkedList();
	    
		int startRecord = 0;
		int endRecord = 0;
		int totalRecords =0;
		String getCount = "select * from THOUGHT_FOR_THE_DAY where Display_date >= CONVERT(date, getdate())";
		ResultSet rsCount = ad1.selectQuery(getCount);
		
		try {
			while (rsCount.next()) {
				totalRecords = totalRecords + 1;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (totalRecords >= 10) {
			thought.setTotalRecords(totalRecords);
			startRecord = 1;
			endRecord = 10;
			thought.setStartRecord(1);
			thought.setEndRecord(10);
			request.setAttribute("displayRecordNo", "displayRecordNo");
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			request.setAttribute("nextButton", "nextButton");
		} else {
			startRecord = 1;
			endRecord = totalRecords;
			thought.setTotalRecords(totalRecords);
			thought.setStartRecord(1);
			thought.setEndRecord(totalRecords);
		}

		String display =  " select * from (select ROW_NUMBER() OVER(ORDER BY priority) AS RowNum ,Description ,priority , display_date from THOUGHT_FOR_THE_DAY where display_date >= CONVERT(date, getdate()))as  sub Where  sub.RowNum between 1 and 10   ";
		ResultSet rs = ad1.selectQuery(display);
		try {
			while (rs.next()) {
				ThoughtForm thought1 = new ThoughtForm();
				thought1.setDescription(rs.getString("Description"));
				thought1.setPriority(rs.getString("Priority"));
				thought1.setDisplayDate(EMicroUtils.display(rs
						.getDate("Display_date")));
				thoughtdetails.add(thought1);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		request.setAttribute("saveButton", "saveButton");
		request.setAttribute("rejectButton", "rejectButton");
		request.setAttribute("thoughtdetails", thoughtdetails);

		if (thoughtdetails.size() == 0) {
			thought.setMessage3("No Records");
			request.setAttribute("message3", "message3");
		}

		return mapping.findForward("Thought");

	}
	
	
	
	public ActionForward previousRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EssDao ad = new EssDao();
		ThoughtForm thought = (ThoughtForm) form;
		HttpSession session = request.getSession();
		
		UserInfo user = (UserInfo) session.getAttribute("user");
		try {
			int totalRecords = thought.getTotalRecords();// 21
			int endRecord = thought.getStartRecord() - 1;// 20
			int startRecord = thought.getStartRecord() - 10;// 11
			if (startRecord == 1) {
				request.setAttribute("disablePreviousButton",
						"disablePreviousButton");
				endRecord = 10;
			}
			thought.setTotalRecords(totalRecords);
			thought.setStartRecord(1);
			thought.setEndRecord(10);

		    LinkedList thoughtdetails = new LinkedList();

		String getApprovers =  "select * from (select ROW_NUMBER() OVER(ORDER BY priority) AS RowNum ,Description ,priority , display_date from THOUGHT_FOR_THE_DAY where display_date >= CONVERT(date, getdate()) )  as  sub Where  sub.RowNum between'"
				+ startRecord + "' and '" + endRecord + "'";
		ResultSet rs = ad.selectQuery(getApprovers);
		try {
			while (rs.next()) {
				ThoughtForm thought1 = new ThoughtForm();
				thought1.setDescription(rs.getString("Description"));
				thought1.setPriority(rs.getString("Priority"));
				thought1.setDisplayDate(EMicroUtils.display(rs
						.getDate("Display_date")));
				thoughtdetails.add(thought1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		
		request.setAttribute("thoughtdetails", thoughtdetails);
		
		thought.setTotalRecords(totalRecords);
		thought.setStartRecord(startRecord);
		thought.setEndRecord(endRecord);
		request.setAttribute("nextButton", "nextButton");
		if (startRecord != 1)
			request.setAttribute("previousButton", "previousButton");
		request.setAttribute("displayRecordNo", "displayRecordNo");
		if (thoughtdetails.size() < 10) {
			thought.setStartRecord(1);
			request.setAttribute("previousButton", "");
			request.setAttribute("disablePreviousButton",
					"disablePreviousButton");
		}
		


	} catch (Exception e) {
		e.printStackTrace();
	}
	
		return mapping.findForward("Thought");

	}

	
	public ActionForward nextRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ThoughtForm thought = (ThoughtForm) form;
		HttpSession session = request.getSession();
		EssDao ad = new EssDao();
		
		UserInfo user = (UserInfo) session.getAttribute("user");

		
		try {
			int totalRecords = thought.getTotalRecords();// 21
			int startRecord = thought.getStartRecord();// 11
			int endRecord = thought.getEndRecord();

			if (totalRecords > endRecord) {
				if (totalRecords == endRecord) {
					startRecord = startRecord;
					endRecord = totalRecords;
				}
				if (totalRecords > endRecord) {
					startRecord = endRecord + 1;
					endRecord = endRecord + 10;
				}
		

		
			LinkedList thoughtdetails = new LinkedList();

		
		String getApprovers = "select * from (select ROW_NUMBER() OVER(ORDER BY priority) AS RowNum ,Description ,priority , display_date from THOUGHT_FOR_THE_DAY where display_date >= CONVERT(date, getdate()) )  as  sub Where  sub.RowNum between'"
				+ startRecord + "' and '" + endRecord + "'";
		ResultSet rs = ad.selectQuery(getApprovers);
		try {
			while (rs.next()) {
				ThoughtForm thought1 = new ThoughtForm();
				thought1.setDescription(rs.getString("Description"));
				thought1.setPriority(rs.getString("Priority"));
				thought1.setDisplayDate(EMicroUtils.display(rs
						.getDate("Display_date")));
				thoughtdetails.add(thought1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		request.setAttribute("thoughtdetails", thoughtdetails);
		if (thoughtdetails.size() != 0) {
			thought.setTotalRecords(totalRecords);
			thought.setStartRecord(startRecord);
			thought.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("previousButton", "previousButton");
		} else {
			int start = startRecord;
			int end = startRecord;

			thought.setTotalRecords(totalRecords);
			thought.setStartRecord(start);
			thought.setEndRecord(end);

		}
		if (thoughtdetails.size() < 10) {
			thought.setTotalRecords(totalRecords);
			thought.setStartRecord(startRecord);
			thought.setEndRecord(startRecord + thoughtdetails.size() - 1);
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
		return mapping.findForward("Thought");
}
			
			
			
			



	public ActionForward displayNEWthoughts(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EssDao ad1 = new EssDao();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		ThoughtForm thought = (ThoughtForm) form;
		int prio = 0;
		String pri = "select MAX(Priority) from THOUGHT_FOR_THE_DAY";
		ResultSet rs = ad1.selectQuery(pri);
		try {
			while (rs.next()) {

				prio = rs.getInt(1);

			}
			prio = prio + 1;

			String str = Integer.toString(prio);
			thought.setPriority(str);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		request.setAttribute("saveButton", "saveButton");
		request.setAttribute("rejectButton", "rejectButton");
		return mapping.findForward("NewThought");

	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	public ActionForward savethought(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EssDao ad1 = new EssDao();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		ThoughtForm thought = (ThoughtForm) form;
		int i = 0;
		String displayDate = "";
		String getDate = "select Display_date from THOUGHT_FOR_THE_DAY order by Priority desc";
		ResultSet rs = ad1.selectQuery(getDate);
		try {
			if (rs.next()) {
				displayDate = rs.getString("Display_date");
				SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date displayDate1 = formatter.parse(displayDate);
				Format formatter1 = new SimpleDateFormat("yyyy-MM-dd");
				displayDate = formatter.format(addDays(displayDate1, 1));
			}

			// if no record
			if (displayDate.equalsIgnoreCase("")) {
				Date dNow = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
				String dateNow = ft.format(dNow);
				SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
				displayDate = dateNow;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date displayDate1 = formatter.parse(displayDate);
				Format formatter1 = new SimpleDateFormat("yyyy-MM-dd");
				displayDate = formatter.format(addDays(displayDate1, 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateNow = ft.format(dNow);
		String insertQuery = "insert into THOUGHT_FOR_THE_DAY (Description,Priority,CREATED_BY,CREATED_DATE,Display_date,Status) "
				+ "values('"
				+ thought.getTextthoughts()
				+ "','"
				+ thought.getPriority()
				+ "','"
				+ user.getEmployeeNo()
				+ "','"
				+ dateNow + "','" + displayDate + "','Off')";
		i = ad1.SqlExecuteUpdate(insertQuery);

		if (i > 0) {

			thought.setMessage("Thought Saved successfully");

			request.setAttribute("message", "message");
			request.setAttribute("saveButton", "saveButton");
			thought.setTextthoughts("");
			thought.setPriority("");
			displayNEWthoughts(mapping, form, request, response);
		} else {
			thought.setMessage2("Error..Please check");
			request.setAttribute("saveButton", "saveButton");
			request.setAttribute("message2", "message2");
		}

		return mapping.findForward("NewThought");

	}

}