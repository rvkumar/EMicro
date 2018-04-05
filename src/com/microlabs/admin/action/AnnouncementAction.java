package com.microlabs.admin.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.jsoup.Jsoup;

import com.microlabs.admin.dao.AdminMail;
import com.microlabs.admin.dao.UserDao;
import com.microlabs.admin.form.AnnouncementForm;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class AnnouncementAction extends DispatchAction {

	/*
	 * public ActionForward getrecipients(ActionMapping mapping, ActionForm
	 * form, HttpServletRequest request, HttpServletResponse response) throws
	 * Exception { EssDao ad = new EssDao(); AnnouncementForm announForm =
	 * (AnnouncementForm) form; ResultSet rs11 =
	 * ad.selectQuery("select * from location "); ArrayList locationList=new
	 * ArrayList(); ArrayList locationLabelList=new ArrayList();
	 * while(rs11.next()) { locationList.add(rs11.getString("LOCID"));
	 * locationLabelList
	 * .add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME")); }
	 * announForm.setLocationIdList(locationList);
	 * announForm.setLocationLabelList(locationLabelList);
	 * 
	 * 
	 * ResultSet rs12 = ad.selectQuery("select * from DEPARTMENT "); ArrayList
	 * deptList=new ArrayList(); ArrayList deptLabelList=new ArrayList();
	 * while(rs12.next()) { deptList.add(rs12.getString("DPTID"));
	 * deptLabelList.add(rs12.getString("DPTSTXT")); }
	 * announForm.setDeptIdList(deptList);
	 * announForm.setDeptLabelList(deptLabelList);
	 * 
	 * announForm.setHeadLinesType(request.getParameter("head"));
	 * announForm.setAnnoucementDate(request.getParameter("andate"));
	 * announForm.setAnnouncementTime(request.getParameter("antime"));
	 * announForm.setSubject(request.getParameter("subject")); return
	 * mapping.findForward("enterreciepent"); }
	 * 
	 * public ActionForward savrecipient(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) throws
	 * Exception {
	 * 
	 * EssDao ad = new EssDao(); AnnouncementForm announForm =
	 * (AnnouncementForm) form;
	 * 
	 * String[] loc=announForm.getLocationId(); String[]
	 * cat=announForm.getCat(); String[] dept=announForm.getDepartment();
	 * 
	 * StringBuffer reqloc = new StringBuffer(); for (int i = 0; i < loc.length;
	 * i++) { reqloc.append( loc[i]+"," ); } String ACtualloc =
	 * reqloc.substring(0, reqloc.length()-1).toString();
	 * 
	 * 
	 * StringBuffer reqcat = new StringBuffer(); for (int i = 0; i < cat.length;
	 * i++) { reqcat.append( cat[i]+"," ); } String ACtualcat =
	 * reqcat.substring(0, reqcat.length()-1).toString();
	 * 
	 * 
	 * StringBuffer reqdept = new StringBuffer(); for (int i = 0; i <
	 * dept.length; i++) { reqdept.append( dept[i]+"," ); } String ACtualdept =
	 * reqdept.substring(0, reqdept.length()-1).toString();
	 * 
	 * ResultSet rs11 = ad.selectQuery("select * from location "); ArrayList
	 * locationList=new ArrayList(); ArrayList locationLabelList=new
	 * ArrayList(); while(rs11.next()) {
	 * locationList.add(rs11.getString("LOCID"));
	 * locationLabelList.add(rs11.getString
	 * ("LOCATION_CODE")+" - "+rs11.getString("LOCNAME")); }
	 * announForm.setLocationIdList(locationList);
	 * announForm.setLocationLabelList(locationLabelList);
	 * 
	 * 
	 * ResultSet rs12 = ad.selectQuery("select * from DEPARTMENT "); ArrayList
	 * deptList=new ArrayList(); ArrayList deptLabelList=new ArrayList();
	 * while(rs12.next()) { deptList.add(rs12.getString("DPTID"));
	 * deptLabelList.add(rs12.getString("DPTSTXT")); }
	 * announForm.setDeptIdList(deptList);
	 * announForm.setDeptLabelList(deptLabelList);
	 * 
	 * String annDt=announForm.getAnnoucementDate(); String
	 * a[]=annDt.split("/"); annDt=a[2]+"-"+a[1]+"-"+a[0]; String insertDetails=
	 * "insert into announcement1(Head_Lines,Annoucement_Date,Annoucement_Time,Subject,Location,Category,Department)"
	 * + " values('"+announForm.getHeadLinesType()+"','"+annDt+"','"+announForm.
	 * getAnnouncementTime
	 * ()+"','"+announForm.getSubject()+"','"+ACtualloc+"','"+
	 * ACtualcat+"','"+ACtualdept+"')";
	 * 
	 * int i = 0; i = ad.SqlExecuteUpdate(insertDetails); if(i>0) {
	 * announForm.setMessage("Recipients Added Successfully"); } else {
	 * announForm.setMessage2("Error While Adding Recipients..."); }
	 * 
	 * 
	 * 
	 * return mapping.findForward("enterreciepent"); }
	 */

	public ActionForward deleteVideo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AnnouncementForm announForm = (AnnouncementForm) form;
		EssDao ad = new EssDao();
		Enumeration<String> params = request
				.getParameterNames();
		String parameter;
		String content_description = "";
		while (params.hasMoreElements()) {
			parameter = params.nextElement();
			if (parameter.equalsIgnoreCase("EditorDefault")) {
				content_description += request.getParameter(parameter);
			}
		}
		announForm.setContent(content_description);
		String saveType = announForm.getSaveType();
		announForm.setSaveType(saveType);
		if (saveType.equalsIgnoreCase("Save"))
			request.setAttribute("saveButton", "saveButton");
		if (saveType.equalsIgnoreCase("Modify"))
			request.setAttribute("modifyButton", "modifyButton");
		try {
			String annDt = announForm.getAnnoucementDate();
			String a[] = annDt.split("/");
			annDt = a[2] + "-" + a[1] + "-" + a[0];

			String videoList[] = request
					.getParameterValues("checkedVideofiles");
			String videos = "";
			InputStream in = ConnectionFactory.class.getClassLoader()
					.getResourceAsStream("db.properties");
			Properties props = new Properties();
			props.load(in);
			in.close();
			String uploadFilePath = props.getProperty("file.uploadFilePath");
			String filePath = uploadFilePath
					+ "/EMicro Files/Announcments/Videos";
			if (videoList.length == 0) {
				videos = "";
			} else {

				for (int i = 0; i < videoList.length; i++) {
					String deleteVideos = "delete from announcment_videos  where HeadLines_Type='"
							+ announForm.getHeadLinesType()
							+ "' "
							+ "and HeadLines_Date='"
							+ annDt
							+ "' and HeadLines_Time='"
							+ announForm.getAnnouncementTime()
							+ "' and video_name='" + videoList[i] + "' ";
					int b = 0;
					b = ad.SqlExecuteUpdate(deleteVideos);
					if (b > 0) {
						File fileToCreate1 = new File(filePath, videoList[i]);
						boolean check = false;
						check = fileToCreate1.delete();
					} else {
						// linksForm.setMessage("Error While Deleted Videos ... Please check ");
					}
				}
			}

			LinkedList videoList1 = new LinkedList();
			String getVideoList = "select * from announcment_videos where HeadLines_Type='"
					+ announForm.getHeadLinesType()
					+ "' and HeadLines_Date='"
					+ annDt
					+ "' and HeadLines_Time='"
					+ announForm.getAnnouncementTime() + "'";
			ResultSet rsVideoList = ad.selectQuery(getVideoList);
			while (rsVideoList.next()) {
				AnnouncementForm annform = new AnnouncementForm();
				annform.setId(rsVideoList.getInt("id"));
				annform.setVideoFilesList(rsVideoList.getString("Video_Name"));
				videoList1.add(annform);
			}
			request.setAttribute("videosList", videoList1);

			String getAlldata = "select * from announcement1";
			LinkedList listOFData = new LinkedList();
			ResultSet rs = ad.selectQuery(getAlldata);
			while (rs.next()) {
				String contentDescription = "";
				AnnouncementForm announcementForm = new AnnouncementForm();
				announcementForm.setId(rs.getInt("id"));
				announcementForm.setHeadLinesType(rs.getString("Head_Lines"));
				contentDescription = rs.getString("Subject");
				contentDescription = Jsoup.parse(contentDescription).text();

				contentDescription = contentDescription.substring(0, 20);
				announcementForm.setContent(contentDescription);
				announcementForm.setAnnoucementDate(EMicroUtils.display(rs
						.getDate("Annoucement_Date")));
				announcementForm.setAnnouncementTime(rs
						.getString("Annoucement_Time"));
				announcementForm.setSubject(rs.getString("Subject"));
				listOFData.add(announcementForm);

			}
			request.setAttribute("listOfannouncement", listOFData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("addnewAnnouncement");
	}

	public ActionForward uploadVideos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AnnouncementForm announForm = (AnnouncementForm) form;
		EssDao ad = new EssDao();
		Enumeration<String> params = request
				.getParameterNames();
		String parameter;
		String content_description = "";
		while (params.hasMoreElements()) {
			parameter = params.nextElement();
			if (parameter.equalsIgnoreCase("EditorDefault")) {
				content_description += request.getParameter(parameter);
			}
		}
		announForm.setContent(content_description);
		String saveType = announForm.getSaveType();
		announForm.setSaveType(saveType);
		if (saveType.equalsIgnoreCase("Save"))
			request.setAttribute("saveButton", "saveButton");
		if (saveType.equalsIgnoreCase("Modify"))
			request.setAttribute("modifyButton", "modifyButton");

		try {
			FormFile myFile = announForm.getVideoFileNames();
			String contentType = myFile.getContentType();
			String fileName = myFile.getFileName();
			byte[] fileData = myFile.getFileData();
			int filesize = myFile.getFileSize();
			String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

			if (ext.equalsIgnoreCase("avi") || ext.equalsIgnoreCase("wmv")
					|| ext.equalsIgnoreCase("mpeg")
					|| ext.equalsIgnoreCase("flv")
					|| ext.equalsIgnoreCase("mp4")
					|| ext.equalsIgnoreCase("swf")
					|| ext.equalsIgnoreCase("webm")) {

				InputStream in = ConnectionFactory.class.getClassLoader()
						.getResourceAsStream("db.properties");
				Properties props = new Properties();
				props.load(in);
				in.close();
				String uploadFilePath = props
						.getProperty("file.uploadFilePath");
				String filePath = uploadFilePath
						+ "/EMicro Files/Announcments/Videos";

				File destinationDir = new File(filePath);
				if (!destinationDir.exists()) {
					destinationDir.mkdirs();
				}
				String p = uploadFilePath + "/EMicro Files/Announcments/Videos";

				File fileToCreate = new File(p, fileName);

				FileOutputStream fileOutStream = new FileOutputStream(
						fileToCreate);
				fileOutStream.write(myFile.getFileData());
				fileOutStream.flush();
				fileOutStream.close();
				String annDt = announForm.getAnnoucementDate();
				String a[] = annDt.split("/");
				annDt = a[2] + "-" + a[1] + "-" + a[0];
				String saveVideos = "insert into announcment_videos(HeadLines_Type,HeadLines_Date,HeadLines_Time,Video_Name) values('"
						+ announForm.getHeadLinesType()
						+ "','"
						+ annDt
						+ "',"
						+ "'"
						+ announForm.getAnnouncementTime()
						+ "','"
						+ fileName + "') ";
				int i = ad.SqlExecuteUpdate(saveVideos);
				LinkedList videoList = new LinkedList();
				String getVideoList = "select * from announcment_videos where HeadLines_Type='"
						+ announForm.getHeadLinesType()
						+ "' and HeadLines_Date='"
						+ annDt
						+ "' and HeadLines_Time='"
						+ announForm.getAnnouncementTime() + "'";
				ResultSet rsVideoList = ad.selectQuery(getVideoList);
				while (rsVideoList.next()) {
					AnnouncementForm annform = new AnnouncementForm();
					annform.setId(rsVideoList.getInt("id"));
					annform.setVideoFilesList(rsVideoList
							.getString("Video_Name"));
					videoList.add(annform);
				}
				request.setAttribute("videosList", videoList);

				String getAlldata = "select * from announcement1";
				LinkedList listOFData = new LinkedList();
				ResultSet rs = ad.selectQuery(getAlldata);
				while (rs.next()) {
					String contentDescription = "";
					AnnouncementForm announcementForm = new AnnouncementForm();
					announcementForm.setId(rs.getInt("id"));
					announcementForm.setHeadLinesType(rs
							.getString("Head_Lines"));
					contentDescription = rs.getString("Subject");
					contentDescription = Jsoup.parse(contentDescription).text();

					contentDescription = contentDescription.substring(0, 20);
					announcementForm.setContent(contentDescription);
					announcementForm.setAnnoucementDate(EMicroUtils.display(rs
							.getDate("Annoucement_Date")));
					announcementForm.setAnnouncementTime(rs
							.getString("Annoucement_Time"));
					announcementForm.setSubject(rs.getString("Subject"));
					listOFData.add(announcementForm);

				}

				request.setAttribute("listOfannouncement", listOFData);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("result", "Upload Only mp4 formate.... ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("addnewAnnouncement");
	}

	public ActionForward deleteAnnouncemetnts(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AnnouncementForm announForm = (AnnouncementForm) form;
		try {
			UserDao ad = new UserDao();

			String deletData = "delete announcement1  where id='"
					+ announForm.getId() + "'";
			int i = 0;
			i = ad.SqlExecuteUpdate(deletData);
			HttpSession session = request.getSession();

			if (i > 0) {

				session.setAttribute("result",
						"Announcement Details Are Deleted");
				announForm.setHeadLinesType("");
			} else {
				session.setAttribute("result",
						"Error When Deleting Announcement Details.Please Check.... ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		displayAnnouncementForm(mapping, form, request, response);

		return mapping.findForward("announcementForm");
	}

	public ActionForward updateAnnouncemetnts(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AnnouncementForm announForm = (AnnouncementForm) form;
		try {
			UserDao ad = new UserDao();
			int id = announForm.getId();
			Enumeration<String> params = request
					.getParameterNames();
			String parameter;
			String content_description = "";
			while (params.hasMoreElements()) {
				parameter = params.nextElement();
				if (parameter.equalsIgnoreCase("EditorDefault")) {
					content_description += request.getParameter(parameter);
				}
			}
			content_description = content_description.replaceAll("'", "`");
			announForm.setContent(content_description);
			String annDt = announForm.getAnnoucementDate();
			String a[] = annDt.split("/");
			annDt = a[2] + "-" + a[1] + "-" + a[0];
			content_description = content_description.replaceAll("'", "''");

			String[] loc = announForm.getLocationId();
			String[] cat = announForm.getCat();
			String[] dept = announForm.getDepartment();

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

			String updateData = "update announcement1 set Head_Lines='"
					+ announForm.getHeadLinesType() + "',Link_Description='"
					+ content_description + "'," + "Annoucement_Date='" + annDt
					+ "',Annoucement_Time='" + announForm.getAnnouncementTime()
					+ "',Subject='" + announForm.getSubject() + "',Location='"
					+ ACtualloc + "',Category='" + ACtualcat + "',Department='"
					+ ACtualdept + "' where id='" + announForm.getId() + "'";
			int i = 0;
			i = ad.SqlExecuteUpdate(updateData);
			HttpSession session = request.getSession();

			if (i > 0) {

				session.setAttribute("result",
						""+announForm.getHeadLinesType()+" Details Are Modified");
			} else {
				session.setAttribute("result",
						"Error When Modifying "+announForm.getHeadLinesType()+" Details.Please Check.... ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		getAnnouncemetnts(mapping, announForm, request, response);
		return mapping.findForward("addnewAnnouncement");
	}

	public ActionForward getAnnouncemetnts(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AnnouncementForm announForm = (AnnouncementForm) form;
		UserDao ad = new UserDao();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			announForm.setId(id);

			String getAnnouncData = "select * from announcement1 where id='"
					+ id + "'";
			String annonDate = "";
			ResultSet rs = ad.selectQuery(getAnnouncData);
			while (rs.next()) {
				announForm.setHeadLinesType(rs.getString("Head_Lines"));
				announForm.setContent(rs.getString("Link_Description"));
				announForm.setAnnoucementDate(EMicroUtils.display(rs
						.getDate("Annoucement_Date")));
				annonDate = rs.getString("Annoucement_Date");
				announForm
						.setAnnouncementTime(rs.getString("Annoucement_Time"));
				announForm.setSubject(rs.getString("Subject"));
				announForm.setLocationId(rs.getString("Location").split(","));
				announForm.setCat(rs.getString("Category").split(","));
				announForm.setDepartment(rs.getString("Department").split(","));
			}

			LinkedList videoList = new LinkedList();
			String getVideoList = "select * from announcment_videos where HeadLines_Type='"
					+ announForm.getHeadLinesType()
					+ "' and HeadLines_Date='"
					+ annonDate
					+ "' and HeadLines_Time='"
					+ announForm.getAnnouncementTime() + "'";
			ResultSet rsVideoList = ad.selectQuery(getVideoList);
			while (rsVideoList.next()) {
				AnnouncementForm annform = new AnnouncementForm();
				annform.setId(rsVideoList.getInt("id"));
				annform.setVideoFilesList(rsVideoList.getString("Video_Name"));
				videoList.add(annform);
			}
			request.setAttribute("videosList", videoList);
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
		announForm.setLocationIdList(locationList);
		announForm.setLocationLabelList(locationLabelList);

		ResultSet rs12 = ad.selectQuery("select * from DEPARTMENT ");
		ArrayList deptList = new ArrayList();
		ArrayList deptLabelList = new ArrayList();
		while (rs12.next()) {
			deptList.add(rs12.getString("DPTID"));
			deptLabelList.add(rs12.getString("DPTSTXT"));
		}
		announForm.setDeptIdList(deptList);
		announForm.setDeptLabelList(deptLabelList);

		announForm.setSaveType("Modify");
		request.setAttribute("modifyButton", "modifyButton");
		return mapping.findForward("addnewAnnouncement");
	}

	public ActionForward saveAnnouncement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AdminMail mail = new AdminMail();
		HttpSession session = request.getSession();
		AnnouncementForm announForm = (AnnouncementForm) form;
		UserDao ad = new UserDao();
		Enumeration<String> params = request
				.getParameterNames();
		String parameter;
		String content_description = "";
		while (params.hasMoreElements()) {
			parameter = params.nextElement();
			if (parameter.equalsIgnoreCase("EditorDefault")) {
				content_description += request.getParameter(parameter);
			}
		}
		content_description = content_description.replaceAll("'", "`");
		announForm.setContent(content_description);
		String annDt = announForm.getAnnoucementDate();
		String a[] = annDt.split("/");
		annDt = a[2] + "-" + a[1] + "-" + a[0];
		content_description = content_description.replaceAll("'", "''");

		String[] loc = announForm.getLocationId();
		String[] cat = announForm.getCat();
		String[] dept = announForm.getDepartment();

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
		String ACtualdept = reqdept.substring(0, reqdept.length() - 1)
				.toString();

		String insertDetails = "insert into announcement1(Head_Lines,Link_Description,Annoucement_Date,Annoucement_Time,Subject,Location,Category,Department)"
				+ " values('"
				+ announForm.getHeadLinesType()
				+ "','"
				+ content_description
				+ "','"
				+ annDt
				+ "',"
				+ "'"
				+ announForm.getAnnouncementTime()
				+ "','"
				+ announForm.getSubject()
				+ "','"
				+ ACtualloc
				+ "','"
				+ ACtualcat + "','" + ACtualdept + "')";

		int i = 0;
		i = ad.SqlExecuteUpdate(insertDetails);

		if (i > 0) {

			session.setAttribute("result", ""+announForm.getHeadLinesType()+" Details Are Saved");
			//int j = mail.sendMailToUsers(request,announForm.getHeadLinesType(),announForm.getSubject(),ACtualloc, ACtualcat,ACtualdept);
		} else {
			session.setAttribute("result",
					"Error When Inserting "+announForm.getHeadLinesType()+" Details.Please Check.... ");

		}

		ResultSet rs11 = ad.selectQuery("select * from location ");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		while (rs11.next()) {
			locationList.add(rs11.getString("LOCATION_CODE"));
			locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
					+ rs11.getString("LOCNAME"));
		}
		announForm.setLocationIdList(locationList);
		announForm.setLocationLabelList(locationLabelList);

		ResultSet rs12 = ad.selectQuery("select * from DEPARTMENT ");
		ArrayList deptList = new ArrayList();
		ArrayList deptLabelList = new ArrayList();
		while (rs12.next()) {
			deptList.add(rs12.getString("DPTID"));
			deptLabelList.add(rs12.getString("DPTSTXT"));
		}
		announForm.setDeptIdList(deptList);
		announForm.setDeptLabelList(deptLabelList);
		request.setAttribute("saveButton", "saveButton");
		return mapping.findForward("addnewAnnouncement");
	}

	public ActionForward addnewAnnouncement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AnnouncementForm announForm = (AnnouncementForm) form;
		UserDao ad = new UserDao();
		try {
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"dd/MM/yyyy hh:mm aa");

			String dateNow = formatter.format(currentDate.getTime());
			String a[] = dateNow.split(" ");

			announForm.setAnnoucementDate(a[0]);
			announForm.setAnnouncementTime(a[1] + " " + a[2]);

			ResultSet rs11 = ad.selectQuery("select * from location ");
			ArrayList locationList = new ArrayList();
			ArrayList locationLabelList = new ArrayList();
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
			announForm.setLocationIdList(locationList);
			announForm.setLocationLabelList(locationLabelList);

			ResultSet rs12 = ad.selectQuery("select * from DEPARTMENT ");
			ArrayList deptList = new ArrayList();
			ArrayList deptLabelList = new ArrayList();
			while (rs12.next()) {
				deptList.add(rs12.getString("DPTID"));
				deptLabelList.add(rs12.getString("DPTSTXT"));
			}
			announForm.setDeptIdList(deptList);
			announForm.setDeptLabelList(deptLabelList);

			/*
			 * String getAlldata = "select * from announcement1"; LinkedList
			 * listOFData = new LinkedList(); ResultSet rs =
			 * ad.selectQuery(getAlldata); while (rs.next()) { String
			 * contentDescription = ""; AnnouncementForm announcementForm = new
			 * AnnouncementForm(); announcementForm.setId(rs.getInt("id"));
			 * announcementForm.setHeadLinesType(rs.getString("Head_Lines"));
			 * contentDescription = rs.getString("Subject"); contentDescription
			 * = Jsoup.parse(contentDescription).text();
			 * 
			 * contentDescription = contentDescription.substring(0, 20);
			 * announcementForm.setContent(contentDescription);
			 * announcementForm.
			 * setAnnoucementDate(EMicroUtils.display(rs.getDate
			 * ("Annoucement_Date")));
			 * announcementForm.setAnnouncementTime(rs.getString
			 * ("Annoucement_Time"));
			 * announcementForm.setSubject(rs.getString("Subject"));
			 * listOFData.add(announcementForm);
			 * 
			 * }
			 * 
			 * request.setAttribute("listOfannouncement", listOFData);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("saveButton", "saveButton");

		announForm.setSaveType("save");

		request.setAttribute("MenuIcon", request.getParameter("id"));

		announForm.setHeadLinesType("");
		announForm.setSubject("");

		return mapping.findForward("addnewAnnouncement");

	}

	public ActionForward displayAnnouncementForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AnnouncementForm announForm = (AnnouncementForm) form;
		UserDao ad = new UserDao();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		int startRecord = 0;
		int endRecord = 0;
		int totalRecords =0;
		String getCount = "select * from announcement1";
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
			announForm.setTotalRecords(totalRecords);
			startRecord = 1;
			endRecord = 10;
			announForm.setStartRecord(1);
			announForm.setEndRecord(10);
			request.setAttribute("displayRecordNo", "displayRecordNo");
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			request.setAttribute("nextButton", "nextButton");
		} else {
			startRecord = 1;
			endRecord = totalRecords;
			announForm.setTotalRecords(totalRecords);
			announForm.setStartRecord(1);
			announForm.setEndRecord(totalRecords);
		}
		
		
		try {
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"dd/MM/yyyy hh:mm aa");

			String dateNow = formatter.format(currentDate.getTime());
			String a[] = dateNow.split(" ");

			announForm.setAnnoucementDate(a[0]);
			announForm.setAnnouncementTime(a[1] + " " + a[2]);

			String getAlldata = "select * from (select ROW_NUMBER() over( order by id ) AS RowNum,* from announcement1 )as  sub where sub.RowNum   between 1 and 10 order by Annoucement_Date";
			LinkedList listOFData = new LinkedList();
			ResultSet rs = ad.selectQuery(getAlldata);
			while (rs.next()) {
				String contentDescription = "";
				AnnouncementForm announcementForm = new AnnouncementForm();
				announcementForm.setId(rs.getInt("id"));
				announcementForm.setHeadLinesType(rs.getString("Head_Lines"));
				contentDescription = rs.getString("Subject");
				contentDescription = Jsoup.parse(contentDescription).text();
				int len = contentDescription.length();
				if (len > 60)
					contentDescription = contentDescription.substring(0, 60);
				announcementForm.setContent(contentDescription);
				announcementForm.setAnnoucementDate(EMicroUtils.display(rs
						.getDate("Annoucement_Date")));
				announcementForm.setAnnouncementTime(rs
						.getString("Annoucement_Time"));
				announcementForm.setSubject(rs.getString("Subject"));
				listOFData.add(announcementForm);

			}

			
			if ( listOFData.size() > 0)
				request.setAttribute("listOfannouncement", listOFData);
			else
				request.setAttribute("NoRecords", "NoRecords");
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("saveButton", "saveButton");

		announForm.setSaveType("save");

		request.setAttribute("MenuIcon", request.getParameter("id"));

		announForm.setHeadLinesType("");
		announForm.setSubject("");

		return mapping.findForward("announcementForm");
	}
	
	public ActionForward previousRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AnnouncementForm announForm = (AnnouncementForm) form;
		UserDao ad = new UserDao();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		try {
			int totalRecords = announForm.getTotalRecords();// 21
			int endRecord = announForm.getStartRecord() - 1;// 20
			int startRecord = announForm.getStartRecord() - 10;// 11
			if (startRecord == 1) {
				request.setAttribute("disablePreviousButton",
						"disablePreviousButton");
				endRecord = 10;
			}
			announForm.setTotalRecords(totalRecords);
			announForm.setStartRecord(1);
			announForm.setEndRecord(10);

		
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"dd/MM/yyyy hh:mm aa");

			String dateNow = formatter.format(currentDate.getTime());
			String a[] = dateNow.split(" ");

			announForm.setAnnoucementDate(a[0]);
			announForm.setAnnouncementTime(a[1] + " " + a[2]);

			String getAlldata = "select * from (select ROW_NUMBER() over( order by id ) AS RowNum,* from announcement1 )as  sub where sub.RowNum   between '"+ startRecord +"' and '"+ endRecord+"' order by Annoucement_Date";
			LinkedList listOFData = new LinkedList();
			ResultSet rs = ad.selectQuery(getAlldata);
			while (rs.next()) {
				String contentDescription = "";
				AnnouncementForm announcementForm = new AnnouncementForm();
				announcementForm.setId(rs.getInt("id"));
				announcementForm.setHeadLinesType(rs.getString("Head_Lines"));
				contentDescription = rs.getString("Subject");
				contentDescription = Jsoup.parse(contentDescription).text();
				int len = contentDescription.length();
				if (len > 60)
					contentDescription = contentDescription.substring(0, 60);
				announcementForm.setContent(contentDescription);
				announcementForm.setAnnoucementDate(EMicroUtils.display(rs
						.getDate("Annoucement_Date")));
				announcementForm.setAnnouncementTime(rs
						.getString("Annoucement_Time"));
				announcementForm.setSubject(rs.getString("Subject"));
				listOFData.add(announcementForm);

			}

			
			if ( listOFData.size() > 0)
				request.setAttribute("listOfannouncement", listOFData);
			else
				request.setAttribute("NoRecords", "NoRecords");
			
			
			announForm.setTotalRecords(totalRecords);
			announForm.setStartRecord(startRecord);
			announForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			if (startRecord != 1)
				request.setAttribute("previousButton", "previousButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
			if (listOFData.size() < 10) {
				announForm.setStartRecord(1);
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton",
						"disablePreviousButton");
			}
		
		
		request.setAttribute("saveButton", "saveButton");

		announForm.setSaveType("save");

		request.setAttribute("MenuIcon", request.getParameter("id"));

		announForm.setHeadLinesType("");
		announForm.setSubject("");
		request.setAttribute("displayRecordNo", "displayRecordNo");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("announcementForm");
		
	}
	
	public ActionForward nextRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AnnouncementForm announForm = (AnnouncementForm) form;
		UserDao ad = new UserDao();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		try {
			int totalRecords = announForm.getTotalRecords();// 21
			int startRecord = announForm.getStartRecord();// 11
			int endRecord = announForm.getEndRecord();

			if (totalRecords > endRecord) {
				if (totalRecords == endRecord) {
					startRecord = startRecord;
					endRecord = totalRecords;
				}
				if (totalRecords > endRecord) {
					startRecord = endRecord + 1;
					endRecord = endRecord + 10;
				}
		

				
		
		
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"dd/MM/yyyy hh:mm aa");

			String dateNow = formatter.format(currentDate.getTime());
			String a[] = dateNow.split(" ");

			announForm.setAnnoucementDate(a[0]);
			announForm.setAnnouncementTime(a[1] + " " + a[2]);

			String getAlldata = "select * from (select ROW_NUMBER() over( order by id ) AS RowNum,* from announcement1 )as  sub where sub.RowNum   between '"+ startRecord +"' and '"+ endRecord+"' order by Annoucement_Date";
			LinkedList listOFData = new LinkedList();
			ResultSet rs = ad.selectQuery(getAlldata);
			while (rs.next()) {
				String contentDescription = "";
				AnnouncementForm announcementForm = new AnnouncementForm();
				announcementForm.setId(rs.getInt("id"));
				announcementForm.setHeadLinesType(rs.getString("Head_Lines"));
				contentDescription = rs.getString("Subject");
				contentDescription = Jsoup.parse(contentDescription).text();
				int len = contentDescription.length();
				if (len > 60)
					contentDescription = contentDescription.substring(0, 60);
				announcementForm.setContent(contentDescription);
				announcementForm.setAnnoucementDate(EMicroUtils.display(rs
						.getDate("Annoucement_Date")));
				announcementForm.setAnnouncementTime(rs
						.getString("Annoucement_Time"));
				announcementForm.setSubject(rs.getString("Subject"));
				listOFData.add(announcementForm);

			}

			
			if ( listOFData.size() > 0)
				request.setAttribute("listOfannouncement", listOFData);
			else
				request.setAttribute("NoRecords", "NoRecords");
			
			
			if (listOFData.size() != 0) {
				announForm.setTotalRecords(totalRecords);
				announForm.setStartRecord(startRecord);
				announForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			} else {
				int start = startRecord;
				int end = startRecord;

				announForm.setTotalRecords(totalRecords);
				announForm.setStartRecord(start);
				announForm.setEndRecord(end);

			}
			if (listOFData.size() < 10) {
				announForm.setTotalRecords(totalRecords);
				announForm.setStartRecord(startRecord);
				announForm.setEndRecord(startRecord + listOFData.size() - 1);
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
			
		
		request.setAttribute("saveButton", "saveButton");

		announForm.setSaveType("save");

		request.setAttribute("MenuIcon", request.getParameter("id"));

		announForm.setHeadLinesType("");
		announForm.setSubject("");
	}	
			request.setAttribute("displayRecordNo", "displayRecordNo");		
			
		}		
  catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("announcementForm");
	}	
	
}


