package com.microlabs.newsandmedia.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.newsandmedia.dao.ContentDescriptionDao;
import com.microlabs.newsandmedia.form.ContentDescriptionForm;
import com.mysql.jdbc.ResultSet;

public class ContentDescriptionAction extends DispatchAction {

	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ContentDescriptionForm cDForm = (ContentDescriptionForm) form;
		HttpSession session = request.getSession();
		ArrayList list = null;
		ContentDescriptionForm contentForm = null;
		try {

			ContentDescriptionDao ad = new ContentDescriptionDao();

			ResultSet rs = (ResultSet) ad
					.selectQuery("select * from  sub_links");

			list = new ArrayList();
			contentForm = new ContentDescriptionForm();
			while (rs.next()) {
				contentForm.setContentDescription(rs
						.getString("content_description"));

				list.add(contentForm);
			}
			request.setAttribute("listName", list);
		} catch (Exception e) {
			System.out.println("Exception caught =" + e.getMessage());
		}

		return mapping.findForward("display");
	}
}
