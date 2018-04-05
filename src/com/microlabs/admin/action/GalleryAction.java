package com.microlabs.admin.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.microlabs.admin.dao.LinksDao;
import com.microlabs.admin.form.GalleryForm;
import com.microlabs.admin.form.LinksForm;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.utilities.UserInfo;

public class GalleryAction extends DispatchAction {

	public ActionForward deleteGallery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GalleryForm linksForm = (GalleryForm) form;
		EssDao ad = new EssDao();
		String reqID = request.getParameter("id");
		String galleryTitle = "";
		int year = 0;
		try {
			String getGalleryName = "select * from cms_sublinks where id='"
					+ reqID + "'";
			ResultSet rsGallery = ad.selectQuery(getGalleryName);
			while (rsGallery.next()) {
				galleryTitle = rsGallery.getString("link_name");
				year = rsGallery.getInt("content_year");
			}
			int i = ad.SqlExecuteUpdate("delete from cms_sublinks where id='"
					+ reqID + "'");
			if (i == 1) {
				ad.SqlExecuteUpdate("delete from cms_linksfilelist where link_name='"
						+ galleryTitle + "' and content_year='" + year + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		galleryList(mapping, linksForm, request, response);
		return mapping.findForward("displayGalleryList");
	}

	public ActionForward updateCmsLinksContent1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		GalleryForm linksForm = (GalleryForm) form;
		EssDao ad = new EssDao();
		try {
			String mainLinkName = linksForm.getMenuName();
			String subLinkName = linksForm.getLinkName();

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
			linksForm.setContentDescriptionAdmin(content_description);
			String subLinkTitle = linksForm.getGalleryTitle();
			String contentYear = "0";
			contentYear = linksForm.getContentYear();

			FormFile myFile = linksForm.getIconNames();
			String contentType = myFile.getContentType();
			String iconName = myFile.getFileName();
			byte[] fileData = myFile.getFileData();
			String ext = iconName.substring(iconName.lastIndexOf('.') + 1);
			if (ext.equalsIgnoreCase("jpeg") || (ext.equalsIgnoreCase("png"))
					|| (ext.equalsIgnoreCase("jpg"))
					|| (ext.equalsIgnoreCase("gif"))) {

				String iconPath = getServlet().getServletContext().getRealPath(
						"cms")
						+ "/"
						+ mainLinkName
						+ "/"
						+ subLinkName
						+ "/links/"
						+ ""
						+ subLinkTitle
						+ "/images/"
						+ contentYear
						+ "/icon";

				InputStream in = ConnectionFactory.class.getClassLoader()
						.getResourceAsStream("db.properties");
				Properties props = new Properties();
				props.load(in);
				in.close();
				String uploadFilePath = props
						.getProperty("file.uploadFilePath");
				iconPath = uploadFilePath + "/EMicro Files/cms/" + mainLinkName
						+ "/" + subLinkName + "/links/" + contentYear + "/"
						+ subLinkTitle + "/" + "/icon";

				File destinationDir = new File(iconPath);
				if (!destinationDir.exists()) {
					destinationDir.mkdirs();
				}
				if (!iconName.equals("")) {
					File fileToCreate = new File(iconPath, iconName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);
						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
					}
				}

				request.setAttribute("iconName", iconName);

			}

			String iconList = "";
			if (iconName.equalsIgnoreCase("")) {
			} else {
				InputStream in = ConnectionFactory.class.getClassLoader()
						.getResourceAsStream("db.properties");
				Properties props = new Properties();
				props.load(in);
				in.close();
				String uploadFilePath = props
						.getProperty("file.uploadFilePath");
				iconList = uploadFilePath + "/EMicro Files/cms/" + mainLinkName
						+ "/" + subLinkName + "/links/" + "" + subLinkTitle
						+ "/images/" + contentYear + "/icon";
			}
			String archiveStatus = "";
			String arch = linksForm.getCmsLinkArchive();
			if ("on".equalsIgnoreCase(arch)) {
				archiveStatus = "1";
			}
			if ("off".equalsIgnoreCase(arch)) {
				archiveStatus = "0";
			}
			if (arch == null) {
				archiveStatus = "0";
			}
			InputStream in = ConnectionFactory.class.getClassLoader()
					.getResourceAsStream("db.properties");
			Properties props = new Properties();
			props.load(in);
			in.close();
			String uploadFilePath = props.getProperty("file.uploadFilePath");
			String iconPath = uploadFilePath + "/EMicro Files/cms/"
					+ mainLinkName + "/" + subLinkName + "/links/"
					+ contentYear + "/" + subLinkTitle + "/" + "/icon";
			String fileName2 = iconPath + "/" + iconName;

			fileName2 = fileName2.replace("\\", "\\\\");

			String sql2 = "update cms_sublinks set icon_name='" + fileName2
					+ "',content_description='" + content_description + "',"
					+ "archived_status='" + archiveStatus + "'"
					+ " where main_linkname='" + mainLinkName + "' and "
					+ "sub_linkname='" + subLinkName + "' and link_name='"
					+ subLinkTitle + "' and content_year='" + contentYear + "'";
			if (iconName.equalsIgnoreCase("")) {
				sql2 = "update cms_sublinks set content_description='"
						+ content_description + "'," + "archived_status='"
						+ archiveStatus + "'" + " where main_linkname='"
						+ mainLinkName + "' and " + "sub_linkname='"
						+ subLinkName + "' and link_name='" + subLinkTitle
						+ "' and content_year='" + contentYear + "'";
			}
			int a = ad.SqlExecuteUpdate(sql2);

			String sql3 = "update cms_linksfilelist set " + "archived_status='"
					+ archiveStatus + "'" + " where main_linkname='"
					+ mainLinkName + "' and " + "sub_linkname='" + subLinkName
					+ "' and link_name='" + subLinkTitle
					+ "' and content_year='" + contentYear + "'";

			ad.SqlExecuteUpdate(sql3);

			linksForm.setLinkName(mainLinkName);
			linksForm.setContentYear(contentYear);

			if (a > 0) {

				linksForm.setMessage("Gallery Modified successfully");
			}

			String sql33 = "select * from cms_linksfilelist where sub_linkname='"
					+ subLinkName
					+ "' and "
					+ " main_linkname='"
					+ mainLinkName
					+ "' and link_name='"
					+ subLinkTitle
					+ "' and content_year='" + contentYear + "'";

			ResultSet rs5 = ad.selectQuery(sql33);

			ArrayList list2 = new ArrayList();
			int count = 0;
			GalleryForm linksForm1 = null;
			while (rs5.next()) {
				linksForm1 = new GalleryForm();
				linksForm1.setImageId(rs5.getString("id") + "," + count);

				String imagename = rs5.getString("file_name");
				int x1 = imagename.lastIndexOf("/");
				imagename = imagename.substring(x1 + 1);
				linksForm1.setImageName(imagename);
				linksForm1.setImageTitle(rs5.getString("file_description"));

				list2.add(linksForm1);
				count++;
			}
			request.setAttribute("listName", list2);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("modifyGallery");
	}

	public ActionForward deleteModifyLinkImageFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GalleryForm linksForm = (GalleryForm) form;
		EssDao ad = new EssDao();
		String[] deleteArray = linksForm.getSelect1();
		String[] imageTitle1 = linksForm.getImageTitle1();
		String contentYear = linksForm.getContentYear();
		String param = request.getParameter("param");
		try {
			String link_id = linksForm.getLinkName();
			String sub_link_name = linksForm.getLinkName();
			// String deleteArray[] = linksForm.getSelect();
			String selected = "";
			String id = "";
			int a = 0;
			if (deleteArray != null) {
				for (int i = 0; i < deleteArray.length; i++) {
					id = deleteArray[i];

					selected += "'" + deleteArray[i] + "'";

					if (param.equalsIgnoreCase("Modify")) {
						String deletesql = "update cms_linksfilelist set file_description='"
								+ imageTitle1[Integer
										.parseInt(id.split(",")[1])]
								+ "' where id ='" + id.split(",")[0] + "'";
						a = ad.SqlExecuteUpdate(deletesql);
					}
					if (param.equalsIgnoreCase("Delete")) {
						String deletesql = "delete from cms_linksfilelist where id ='"
								+ id.split(",")[0] + "'";
						a = ad.SqlExecuteUpdate(deletesql);
					}
				}

			}

			if (a > 0) {
                if(param.equalsIgnoreCase("Modify"))
                	linksForm.setMessage("Files  Modified Successfully");
                if(param.equalsIgnoreCase("Delete"))
                	linksForm.setMessage("Files  Deleted Successfully");
			} else {
				 if(param.equalsIgnoreCase("Modify"))
					 linksForm.setMessage("Error While  Modifying Files...");
		                if(param.equalsIgnoreCase("Delete"))
		                	linksForm.setMessage("Error While  Deleting Files...");
				
			}
			ArrayList list = new ArrayList();

			ResultSet rs5 = ad
					.selectQuery("select id,file_name,file_description from cms_linksfilelist where main_linkname='"
							+ linksForm.getMenuName()
							+ "' and "
							+ "sub_linkname='"
							+ linksForm.getLinkName()
							+ "' and link_name='"
							+ linksForm.getGalleryTitle()
							+ "' and  content_year='"
							+ linksForm.getContentYear() + "'");
			int count1 = 0;
			GalleryForm linksForm1 = null;
			while (rs5.next()) {

				linksForm1 = new GalleryForm();
				linksForm1.setImageId(rs5.getString("id") + "," + count1);
				linksForm1.setImageName(rs5.getString("file_name").substring(
						rs5.getString("file_name").lastIndexOf("/") + 1,
						rs5.getString("file_name").length()));
				linksForm1.setImageTitle(rs5.getString("file_description"));
				list.add(linksForm1);
				count1++;
			}
			request.setAttribute("listName", list);
			ArrayList list1 = new ArrayList();

			ResultSet rs6 = ad
					.selectQuery("select * from cms_sublinks where main_linkname='"
							+ linksForm.getMenuName()
							+ "' and "
							+ "sub_linkname='"
							+ linksForm.getLinkName()
							+ "' and link_name='"
							+ linksForm.getGalleryTitle()
							+ "' and  content_year='"
							+ linksForm.getContentYear() + "'");
			while (rs6.next()) {
				String content_description = "";
				content_description = rs6.getString("content_description");
				content_description = content_description.replaceAll("'", "`");
				linksForm.setContentDescriptionAdmin(content_description);
			}
			String sql1 = "select * from cms_linksfilelist where main_linkname='"
					+ linksForm.getMenuName()
					+ "' and "
					+ "sub_linkname='"
					+ linksForm.getLinkName()
					+ "' and link_name='"
					+ linksForm.getGalleryTitle()
					+ "' and  content_year='"
					+ linksForm.getContentYear() + "'";

			ResultSet rs1 = ad.selectQuery(sql1);
			ArrayList a1 = new ArrayList();
			GalleryForm linksForm2 = null;
			int count = 0;
			while (rs1.next()) {
				linksForm2 = new GalleryForm();
				linksForm2.setImageId(rs1.getString("id") + "," + count);
				linksForm2.setImageTitle(rs1.getString("file_description"));
				linksForm2.setImageName(rs1.getString("file_name").substring(
						rs1.getString("file_name").lastIndexOf("/") + 1,
						rs1.getString("file_name").length()));
				a1.add(linksForm2);
				count++;
			}

			linksForm.setContentYear(contentYear);

			request.setAttribute("ImageDetails", a1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("modifyGallery");
	}

	public ActionForward uploadCmsLinkFilesModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GalleryForm linksForm = (GalleryForm) form;
		EssDao ad = new EssDao();
		try {
			System.out.println("UPLOAD FILE MODIFY  METHOD");
			String linkName = linksForm.getMenuName();
			String subLinkName = linksForm.getLinkName();

			String contentYear = linksForm.getContentYear();

			String subLinkTitle = linksForm.getGalleryTitle();

			String fileDescription = linksForm.getImageTitle();
			linksForm.setLinkName(linkName);
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
			Date d = new Date();
			ArrayList years = new ArrayList();
			int pyear = 1900 + d.getYear();

			for (int i = pyear; i >= 1950; i--) {
				years.add(i);
			}
			linksForm.setYears(years);
			String sql6 = "select * from cms_sublinks where sub_linkname='"
					+ subLinkName + "' and " + " main_linkname='" + linkName
					+ "' and link_name='" + subLinkTitle + "'";
			ResultSet rs6 = ad.selectQuery(sql6);
			String linkPath = null;
			String method = null;
			String priority = null;
			String contentDescription = null;
			while (rs6.next()) {
				contentDescription = rs6.getString("content_description");
			}
			if (contentDescription != null)
				contentDescription = contentDescription.replaceAll("'", "`");
			linksForm.setContentDescriptionAdmin(contentDescription);
			FormFile myFile = linksForm.getImageGalleryFile();
			String contentType = myFile.getContentType();
			String fileName = myFile.getFileName();
			byte[] fileData = myFile.getFileData();
			String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			if (linksForm.getImageGalleryFile().getFileSize() == 0) {
				linksForm.setMessage("Please choose a jpg,png file to Upload");
				ArrayList list = new ArrayList();
				String sql3 = "select * from cms_linksfilelist where sub_linkname='"
						+ subLinkName
						+ "' and "
						+ " main_linkname='"
						+ linkName
						+ "' and link_name='"
						+ subLinkTitle
						+ "' and content_year='" + contentYear + "'";
				ResultSet rs5 = ad.selectQuery(sql3);
				GalleryForm linksForm1 = null;
				while (rs5.next()) {
					linksForm1 = new GalleryForm();
					linksForm1.setImageId(rs5.getString("id"));
					linksForm1.setImageName(rs5.getString("file_name"));
					linksForm1.setImageTitle(rs5.getString("file_description"));

					list.add(linksForm1);
				}
				request.setAttribute("listName", list);
				ArrayList list1 = new ArrayList();

				// return mapping.findForward("displayCmsLinksModify");
			}// jpg,jpeg,png,pdf
			if (ext.equalsIgnoreCase("jpg") || (ext.equalsIgnoreCase("jpeg"))
					|| (ext.equalsIgnoreCase("png"))) {
				// String filePath =
				// getServlet().getServletContext().getRealPath("cms") + "/" +
				// linkName+"/"+sub_link_name+"/UploadFiles";
				String filePath = getServlet().getServletContext().getRealPath(
						"jsp/EMicro Files/cms")
						+ "/"
						+ linkName
						+ "/"
						+ subLinkName
						+ "/links/"
						+ ""
						+ subLinkTitle + "/images/" + contentYear + "";

				InputStream in = ConnectionFactory.class.getClassLoader()
						.getResourceAsStream("db.properties");
				Properties props = new Properties();
				props.load(in);
				in.close();
				String uploadFilePath = props
						.getProperty("file.uploadFilePath");

				filePath = uploadFilePath + "/EMicro Files/cms/About Company/"
						+ subLinkName + "/links/" + contentYear + "/"
						+ subLinkTitle + "/images";

				File destinationDir = new File(filePath);
				if (!destinationDir.exists()) {
					destinationDir.mkdirs();
				}
				if (!fileName.equals("")) {
					File fileToCreate = new File(filePath, fileName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);
						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
					}
				}

				request.setAttribute("fileName", fileName);
				filePath = filePath.replace("\\", "\\\\");

				/*
				 * String
				 * sqlselect="select * from links where link_name='"+sub_link_name
				 * + "' and " +
				 * "status=1 and delete_status=1 and module='"+linkName
				 * +"' and file_name not like ''";
				 */

				String sqlselect = "select * from cms_linksfilelist where sub_linkname='"
						+ subLinkName
						+ "' and "
						+ " main_linkname='"
						+ linkName
						+ "' and link_name='"
						+ subLinkTitle
						+ "' and content_year='" + contentYear + "'";

				ResultSet rs15 = ad.selectQuery(sqlselect);
				String FileList = "";
				while (rs15.next()) {
					FileList = rs15.getString("file_name") + ",";
				}
				// String fileNameList="cms" + "/" +
				// linkName+"/"+subLinkName+"/UploadFiles" + "/"+fileName;

				String fileNameList = "jsp/EMicro Files/cms" + "/" + linkName
						+ "/" + subLinkName + "/links/" + subLinkTitle
						+ "/images/" + contentYear + "/" + fileName;

				fileNameList = uploadFilePath + "/EMicro Files/cms/" + linkName
						+ "/" + subLinkName + "/links/" + "" + contentYear
						+ "/" + subLinkTitle + "/images/" + fileName;

				ArrayList list1 = new ArrayList();

				boolean b;
				b = FileList.contains(fileNameList);
				if (b) {
					linksForm
							.setMessage("This file  is  already uploaded..please choose another file");
				} else {
					String fileNames = null;
					if (FileList.equalsIgnoreCase("")) {
						fileNames = fileNameList;
					} else {
						fileNames = FileList + ',' + fileNameList;
					}

					String inserSQl = "insert into cms_linksfilelist(main_linkname,sub_linkname,"
							+ "link_name,file_name,file_description,content_year)values('"
							+ linkName
							+ "','"
							+ subLinkName
							+ "',"
							+ "'"
							+ subLinkTitle
							+ "','"
							+ fileNameList
							+ "','"
							+ fileDescription + "','" + contentYear + "')";

					int a = ad.SqlExecuteUpdate(inserSQl);

					if (a > 0) {
						linksForm.setImageTitle("");
						linksForm.setMessage("Documents Uploaded Successfully");
					} else {
						linksForm
								.setMessage("Error While Uploading Files ... Please check Entered Values");
					}
				}
				ArrayList list = new ArrayList();

				// request.setAttribute("listName", list);
			} else {
				linksForm
						.setMessage("Only File(jpg,jpeg,png) File Format is Allowed");

				String sql20 = "select * from cms_sublinks where sub_linkname='"
						+ subLinkName
						+ "' and "
						+ " main_linkname='"
						+ linkName + "' and link_name='" + subLinkTitle + "'";
				ResultSet rs10 = ad.selectQuery(sql20);
				while (rs10.next()) {
					linksForm.setContentDescriptionAdmin(rs10
							.getString("content_description"));
				}
				// return mapping.findForward("modifyGallery");
			}
			String sql3 = "select * from cms_linksfilelist where sub_linkname='"
					+ subLinkName
					+ "' and "
					+ " main_linkname='"
					+ linkName
					+ "' and link_name='"
					+ subLinkTitle
					+ "' and content_year='" + contentYear + "'";

			ResultSet rs5 = ad.selectQuery(sql3);

			ArrayList list2 = new ArrayList();
			int count = 0;
			GalleryForm linksForm1 = null;
			while (rs5.next()) {
				linksForm1 = new GalleryForm();
				linksForm1.setImageId(rs5.getString("id") + "," + count);

				String imagename = rs5.getString("file_name");
				int x1 = imagename.lastIndexOf("/");
				imagename = imagename.substring(x1 + 1);
				linksForm1.setImageName(imagename);
				linksForm1.setImageTitle(rs5.getString("file_description"));

				list2.add(linksForm1);
				count++;
			}
			request.setAttribute("listName", list2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("modifyGallery");
	}

	public ActionForward selectCmsContent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GalleryForm linksForm = (GalleryForm) form;
		EssDao ad = new EssDao();// TODO Auto-generated method stub

		String id = request.getParameter("id");

		Date d = new Date();

		String pyear = "";
		String sql = "select * from cms_sublinks where id='" + id + "'";

		ArrayList linkIdList = new ArrayList();
		ArrayList linkValueList = new ArrayList();
		try {
			ResultSet rs = ad.selectQuery(sql);

			String linkName = "";
			String sub_link_name = "";
			String mainLinkName = "";
			String subLinkName = "";
			while (rs.next()) {
				linksForm.setMenuName(rs.getString("main_linkname"));
				linksForm.setLinkName(rs.getString("sub_linkname"));
				sub_link_name = rs.getString("sub_linkname");
				linkName = rs.getString("link_name");
				linksForm.setGalleryTitle(rs.getString("link_name"));
				mainLinkName = rs.getString("main_linkname");
				pyear = rs.getString("content_year");
				subLinkName = rs.getString("sub_linkname");

				if (rs.getString("archived_status").equalsIgnoreCase("0")) {
					linksForm.setCmsLinkArchive("off");
				}
				if (rs.getString("archived_status").equalsIgnoreCase("1")) {
					linksForm.setCmsLinkArchive("on");
				}

				linksForm.setContentDescriptionAdmin(rs
						.getString("content_description"));

			}

			String sql2 = "select * from cms_sublinks where main_linkname='"
					+ linkName + "' " + "and sub_linkname='" + sub_link_name
					+ "' ";

			ResultSet rs1 = ad.selectQuery(sql2);

			ArrayList a1 = new ArrayList();
			LinksForm linksForm1 = null;

			while (rs1.next()) {
				linksForm1 = new LinksForm();

				linksForm1.setCmsLinkId(rs1.getString("id"));
				linksForm1.setLinkName(rs1.getString("main_linkname"));
				linksForm1.setSubLinkName(rs1.getString("sub_linkname"));
				linksForm1.setSubLinkTitle(rs1.getString("link_name"));
				a1.add(linksForm1);
			}

			request.setAttribute("CmsLinkDetails", a1);

			ArrayList list = new ArrayList();

			String sql1 = "select * from cms_linksfilelist where main_linkname='"
					+ mainLinkName
					+ "'"
					+ " and sub_linkname='"
					+ subLinkName
					+ "' and link_name='"
					+ linkName
					+ "' and  content_year='"
					+ pyear + "'";
			ResultSet rs2 = ad.selectQuery(sql1);

			GalleryForm linksForm2 = null;
			int count = 0;
			while (rs2.next()) {

				linksForm2 = new GalleryForm();
				linksForm2.setImageId(rs2.getString("id") + "," + count);
				linksForm2.setImageName(rs2.getString("file_name").substring(
						rs2.getString("file_name").lastIndexOf("/") + 1,
						rs2.getString("file_name").length()));

				linksForm2.setImageTitle(rs2.getString("file_description"));
				list.add(linksForm2);
				count++;
			}
			request.setAttribute("listName", list);

			linksForm.setContentYear(String.valueOf(pyear));

		} catch (Exception se) {
			se.printStackTrace();
		}

		return mapping.findForward("modifyGallery");
	}

	public ActionForward submitGallery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GalleryForm gallery = (GalleryForm) form;
		EssDao ad = new EssDao();
		String linkName = gallery.getMenuName();
		String subLinkName = gallery.getLinkName();

		FormFile iconNames = gallery.getIconNames();

		String subLinkTitle = gallery.getGalleryTitle();
		String contentYear = gallery.getContentYear();

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
		try {
			String sql = "select * from temp_filelist where main_linkname='"
					+ linkName + "' and" + " sub_linkname='" + subLinkName
					+ "' and link_name='" + subLinkTitle + "'";
			ResultSet rs = ad.selectQuery(sql);
			String fileName = "";
			String fileDescription = "";
			while (rs.next()) {
				fileName += rs.getString("file_name") + ",";
				fileDescription += rs.getString("file_description") + ",";
				String sql12 = "insert into cms_linksfilelist(main_linkname,sub_linkname,"
						+ "link_name,file_name,file_description,content_year)values('"
						+ linkName
						+ "','"
						+ subLinkName
						+ "',"
						+ "'"
						+ subLinkTitle
						+ "','"
						+ rs.getString("file_name")
						+ "',"
						+ "'"
						+ rs.getString("file_description")
						+ "','"
						+ contentYear + "')";
				ad.SqlExecuteUpdate(sql12);

			}
			String path = iconNames.getFileName();
			String sql1 = "insert into cms_sublinks(main_linkname,sub_linkname,link_name,"
					+ "link_path,method,file_name,icon_name,content_description,file_description,content_year,archived_status)"
					+ "values('"
					+ linkName
					+ "','"
					+ subLinkName
					+ "','"
					+ subLinkTitle
					+ "','newsAndMedia.do','displayLinks',"
					+ "'"
					+ fileName
					+ "',"
					+ "'"
					+ path
					+ "','"
					+ content_description
					+ "','"
					+ fileDescription
					+ "','"
					+ contentYear + "','0')";

			int a = ad.SqlExecuteUpdate(sql1);

			String sql2 = "delete from temp_filelist where main_linkname='"
					+ linkName + "' and" + " sub_linkname='" + subLinkName
					+ "' and link_name='" + subLinkTitle + "'";

			ad.SqlExecuteUpdate(sql2);
			String contentType = iconNames.getContentType();
			byte[] fileData = iconNames.getFileData();
			String filePath = getServlet().getServletContext().getRealPath(
					"cms")
					+ "/"
					+ linkName
					+ "/"
					+ subLinkName
					+ ""
					+ "/links/"
					+ subLinkTitle + "/images/" + contentYear + "/icon";

			InputStream in = ConnectionFactory.class.getClassLoader()
					.getResourceAsStream("db.properties");
			Properties props = new Properties();
			props.load(in);
			in.close();
			String uploadFilePath = props.getProperty("file.uploadFilePath");
			System.out.println("required filepath=" + uploadFilePath
					+ "/EMicro Files/ESS/On Duty/UploadFiles");
			filePath = uploadFilePath + "/EMicro Files/cms/" + linkName + "/"
					+ subLinkName + "/links/" + contentYear + "/"
					+ subLinkTitle + "/" + "/icon";
			File imageGalleryDir = new File(filePath);
			if (!imageGalleryDir.exists()) {
				imageGalleryDir.mkdirs();
			}

			if (!fileName.equals("")) {
				File fileToCreate = new File(filePath, iconNames.getFileName());
				if (!fileToCreate.exists()) {
					FileOutputStream fileOutStream = new FileOutputStream(
							fileToCreate);
					fileOutStream.write(iconNames.getFileData());
					fileOutStream.flush();
					fileOutStream.close();
				}

			}

			filePath = filePath + "/" + iconNames.getFileName();
			filePath = filePath.replace("\\", "\\\\");
			String sql31 = "update cms_sublinks set icon_name='" + filePath
					+ "' where main_linkname='" + linkName + "' and"
					+ " sub_linkname='" + subLinkName + "' and link_name='"
					+ subLinkTitle + "' and content_year='" + contentYear + "'";
			ad.SqlExecuteUpdate(sql31);
			if (a > 0) {
				gallery.setMessage("Submitted Successfully");
			}

		} catch (Exception se) {
			se.printStackTrace();
		}
		return mapping.findForward("newGallery");
	}

	public ActionForward modifyImageFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GalleryForm gallery = (GalleryForm) form;
		String[] deleteArray = gallery.getSelect1();
		String[] imageTitle1 = gallery.getImageTitle1();
		String param = request.getParameter("param");
		String id = "";
		String selected = "";
		EssDao ad = new EssDao();
		int a = 0;
		try {
			for (int i = 0; i < deleteArray.length; i++) {
				id = deleteArray[i];

				selected += "'" + deleteArray[i] + "'";

				if (param.equalsIgnoreCase("Modify")) {
					String deletesql = "update temp_filelist set file_description='"
							+ imageTitle1[Integer.parseInt(id.split(",")[1])]
							+ "' where id ='" + id.split(",")[0] + "'";
					a = ad.SqlExecuteUpdate(deletesql);
				}
				if (param.equalsIgnoreCase("Delete")) {
					String deletesql = "delete from temp_filelist where id ='"
							+ id.split(",")[0] + "'";
					a = ad.SqlExecuteUpdate(deletesql);
				}
				request.setAttribute("editFCKEditor", "editFCKEditor");

			}
			if (a > 0) {
                if(param.equalsIgnoreCase("Modify"))
				gallery.setMessage("Files  Modified Successfully");
                if(param.equalsIgnoreCase("Delete"))
                gallery.setMessage("Files  Deleted Successfully");
			} else {
				 if(param.equalsIgnoreCase("Modify"))
						gallery.setMessage("Error While  Modifying Files...");
		                if(param.equalsIgnoreCase("Delete"))
		                gallery.setMessage("Error While  Deleting Files...");
				
			}
			String sql1 = "select * from temp_filelist where main_linkname='"
					+ gallery.getMenuName() + "' and  sub_linkname='"
					+ gallery.getLinkName() + "' " + "and link_name='"
					+ gallery.getGalleryTitle() + "'";
			ResultSet rs1 = ad.selectQuery(sql1);
			ArrayList a1 = new ArrayList();
			LinksForm linksForm2 = null;
			int count = 0;
			while (rs1.next()) {
				linksForm2 = new LinksForm();
				linksForm2.setImageId(rs1.getString("id") + "," + count);
				linksForm2.setImageTitle(rs1.getString("file_description"));
				linksForm2.setImageName(rs1.getString("file_name").substring(
						rs1.getString("file_name").lastIndexOf("/") + 1,
						rs1.getString("file_name").length()));
				a1.add(linksForm2);
				count++;
			}
			request.setAttribute("ImageDetails", a1);
			request.setAttribute("editDetails", "editDetails");
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
			gallery.setContentDescriptionAdmin(content_description);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("newGallery");
	}

	public ActionForward uploadGalleryImage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GalleryForm gallery = (GalleryForm) form;
		EssDao ad = new EssDao();
		String linkName = gallery.getMenuName();
		String subLinkName = gallery.getLinkName();
		String imageTitle = gallery.getImageTitle();
		String subLinkTitle = gallery.getGalleryTitle();
		String contentYear = gallery.getContentYear();
		FormFile imageGalleryFile = gallery.getImageGalleryFile();
		String contentType = imageGalleryFile.getContentType();
		String fileName = imageGalleryFile.getFileName();
		try {
			byte[] fileData = imageGalleryFile.getFileData();

			String filePath = "";

			InputStream in = ConnectionFactory.class.getClassLoader()
					.getResourceAsStream("db.properties");
			Properties props = new Properties();
			props.load(in);
			in.close();
			String uploadFilePath = props.getProperty("file.uploadFilePath");
			System.out.println("required filepath=" + uploadFilePath
					+ "/EMicro Files/ESS/On Duty/UploadFiles");
			filePath = uploadFilePath + "/EMicro Files/cms/About Company/"
					+ subLinkName + "/links/" + contentYear + "/"
					+ subLinkTitle + "/images";

			File imageGalleryDir = new File(filePath);

			if (!imageGalleryDir.exists()) {
				imageGalleryDir.mkdirs();
			}

			if (!fileName.equals("")) {
				File fileToCreate = new File(filePath, fileName);
				if (!fileToCreate.exists()) {
					FileOutputStream fileOutStream = new FileOutputStream(
							fileToCreate);
					fileOutStream.write(imageGalleryFile.getFileData());
					fileOutStream.flush();
					fileOutStream.close();
				}

			}
			gallery.setContentYear(contentYear);
			filePath = uploadFilePath + "/EMicro Files/cms/About Company/"
					+ subLinkName + "/links/" + contentYear + "/"
					+ subLinkTitle + "/images/";
			String sql = "insert into temp_filelist(main_linkname,sub_linkname,link_name,file_name,file_description)"
					+ "values('"
					+ linkName
					+ "','"
					+ subLinkName
					+ "','"
					+ subLinkTitle
					+ "','"
					+ (filePath + fileName)
					+ "','"
					+ imageTitle + "')";
			int a = ad.SqlExecuteUpdate(sql);

			if (a > 0) {
				gallery.setImageTitle("");
				gallery.setMessage("Image Inserted  Successfully");
				request.setAttribute("editFCKEditor", "editFCKEditor");
			}
			String sql1 = "select * from temp_filelist where main_linkname='"
					+ linkName + "' and " + "sub_linkname='" + subLinkName
					+ "' and link_name='" + subLinkTitle + "'";

			ResultSet rs1 = ad.selectQuery(sql1);

			ArrayList a1 = new ArrayList();
			LinksForm linksForm1 = null;
			int count = 0;
			while (rs1.next()) {
				linksForm1 = new LinksForm();
				linksForm1.setImageId(rs1.getString("id") + "," + count);
				linksForm1.setImageTitle(rs1.getString("file_description"));
				linksForm1.setImageName(rs1.getString("file_name").substring(
						rs1.getString("file_name").lastIndexOf("/") + 1,
						rs1.getString("file_name").length()));
				a1.add(linksForm1);
				count++;
			}
			request.setAttribute("ImageDetails", a1);
			request.setAttribute("editDetails", "editDetails");

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
			gallery.setContentDescriptionAdmin(content_description);
		} catch (FileNotFoundException e) {
			gallery.setMessage("Error While Uploading Image File");
			e.printStackTrace();
		} catch (IOException e) {
			gallery.setMessage("Error While Uploading Image File");
			e.printStackTrace();
		} catch (SecurityException se) {
			gallery.setMessage("Error While Uploading Image File");
			se.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return mapping.findForward("newGallery");
	}

	public ActionForward selectYearCmsContent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GalleryForm gallery = (GalleryForm) form;
		LinksDao ad = new LinksDao();
		gallery.setMenuName("About Company");
		gallery.setLinkName("Gallery");

		// check gallery avaliable or not
		String galleryTitle = gallery.getGalleryTitle();
		String year = gallery.getContentYear();
		int count = 0;
		String checkGallery = "select count(*) from cms_sublinks where content_year='"
				+ year + "' and link_name='" + galleryTitle + "'";
		ResultSet rsCheckGallery = ad.selectQuery(checkGallery);
		try {
			while (rsCheckGallery.next()) {
				count = rsCheckGallery.getInt(1);
			}
			if (count > 0) {
				gallery.setMessage1("Please Change The Album Title.");
				gallery.setGalleryTitle("");
				gallery.setContentYear("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("newGallery");
	}

	public ActionForward newGallery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GalleryForm gallery = (GalleryForm) form;
		gallery.setMenuName("About Company");
		gallery.setLinkName("Gallery");

		return mapping.findForward("newGallery");
	}

	public ActionForward galleryList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GalleryForm linksForm = (GalleryForm) form;

		String mainLinkName = "About Company";
		String subLinkName = "Gallery";
		String id = request.getParameter("id");
		String sql = "select * from cms_sublinks  ";
		LinksDao ad = new LinksDao();
		ArrayList linkIdList = new ArrayList();
		ArrayList linkValueList = new ArrayList();
		try {
			ResultSet rs = ad.selectQuery(sql);
			int totalRecords = 0;
			int startRecord = 0;
			int endRecord = 0;

			String getTotalRecords = "select count(*) from cms_sublinks where main_linkname='"
					+ mainLinkName
					+ "' "
					+ "and sub_linkname='"
					+ subLinkName
					+ "' ";

			ResultSet rsTotalRecods = ad.selectQuery(getTotalRecords);
			while (rsTotalRecods.next()) {
				totalRecords = rsTotalRecods.getInt(1);
			}

			if (totalRecords > 10) {
				linksForm.setTotalRecords(totalRecords);
				startRecord = 1;
				endRecord = 10;
				linksForm.setStartRecord(1);
				linksForm.setEndRecord(10);
				request.setAttribute("displayRecordNo", "displayRecordNo");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				request.setAttribute("nextButton", "nextButton");
			} else {
				startRecord = 1;
				endRecord = totalRecords;
				linksForm.setTotalRecords(totalRecords);
				linksForm.setStartRecord(1);
				linksForm.setEndRecord(totalRecords);
			}

			String sql2 = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID desc) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name,c.archived_status"
					+ "  From  cms_sublinks as c where main_linkname='"
					+ mainLinkName
					+ "' and sub_linkname='"
					+ subLinkName
					+ "' ) as sub Where  sub.RowNum between '"
					+ startRecord
					+ "' and '10' order by content_year desc ";
			ResultSet rs1 = ad.selectQuery(sql2);
			ArrayList a1 = new ArrayList();
			GalleryForm linksForm1 = null;
			while (rs1.next()) {
				linksForm1 = new GalleryForm();
				linksForm1.setRowno(rs1.getInt("RowNum"));
				linksForm1.setCmsLinkId(rs1.getString("id"));
				linksForm1.setMenuName(rs1.getString("main_linkname"));
				linksForm1.setLinkName(rs1.getString("sub_linkname"));
				linksForm1.setGalleryTitle(rs1.getString("link_name"));
				linksForm1.setContentYear(rs1.getString("content_year"));
				linksForm1.setIconName(rs1.getString("icon_name"));
				int archiveStatus = rs1.getInt("archived_status");
				if (archiveStatus == 1) {
					linksForm1.setArchiveStatus("On");
				} else {
					linksForm1.setArchiveStatus("Off");
				}
				a1.add(linksForm1);
			}
			request.setAttribute("CmsLinkDetails", a1);
			String sql1 = "select * from cms_linksfilelist  ";
			ResultSet rs2 = ad.selectQuery(sql1);
			ArrayList list = new ArrayList();
			LinksForm linksForm2 = null;
			int count = 0;
			while (rs2.next()) {

				linksForm2 = new LinksForm();
				linksForm2.setImageId(rs2.getString("id") + "," + count);
				linksForm2.setImageName(rs2.getString("file_name"));
				linksForm2.setImageTitle(rs2.getString("file_description"));
				list.add(linksForm2);
				count++;

			}
			
			if ( list.size() > 0)
				request.setAttribute("listName", list);
			else
				request.setAttribute("NoRecords", "NoRecords");
			
			
			request.setAttribute("displaySublinkField", "displaySublinkField");
			request.setAttribute("submitButton", "submitButton");
			

		} catch (Exception se) {
			se.printStackTrace();
		}
		request.setAttribute("addbutton", "addbutton");

		return mapping.findForward("displayGalleryList");
	}


	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GalleryForm linksForm = (GalleryForm) form;

		String mainLinkName = "About Company";
		String subLinkName = "Gallery";
		String id = request.getParameter("id");
		String sql = "select * from cms_sublinks  ";
		LinksDao ad = new LinksDao();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		ArrayList linkIdList = new ArrayList();
		ArrayList linkValueList = new ArrayList();

			ResultSet rs = ad.selectQuery(sql);
			int totalRecords = linksForm.getTotalRecords();// 21
			int startRecord = linksForm.getStartRecord();// 11
			int endRecord = linksForm.getEndRecord();

			if (totalRecords > endRecord) {
				if (totalRecords == endRecord) {
					startRecord = startRecord;
					endRecord = totalRecords;
				}
				if (totalRecords > endRecord) {
					startRecord = endRecord + 1;
					endRecord = endRecord + 10;
				}
		
			
			String sql2 = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID desc) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name,c.archived_status"
					+ "  From  cms_sublinks as c where main_linkname='"
					+ mainLinkName
					+ "' and sub_linkname='"
					+ subLinkName
					+ "' ) as sub Where  sub.RowNum between '"
					+ startRecord
					+ "' and '"+ endRecord+"' order by content_year desc ";
			ResultSet rs1 = ad.selectQuery(sql2);
			ArrayList a1 = new ArrayList();
			GalleryForm linksForm1 = null;
			while (rs1.next()) {
				linksForm1 = new GalleryForm();
				linksForm1.setRowno(rs1.getInt("RowNum"));
				linksForm1.setCmsLinkId(rs1.getString("id"));
				linksForm1.setMenuName(rs1.getString("main_linkname"));
				linksForm1.setLinkName(rs1.getString("sub_linkname"));
				linksForm1.setGalleryTitle(rs1.getString("link_name"));
				linksForm1.setContentYear(rs1.getString("content_year"));
				linksForm1.setIconName(rs1.getString("icon_name"));
				int archiveStatus = rs1.getInt("archived_status");
				if (archiveStatus == 1) {
					linksForm1.setArchiveStatus("On");
				} else {
					linksForm1.setArchiveStatus("Off");
				}
				a1.add(linksForm1);
			}
			request.setAttribute("CmsLinkDetails", a1);
			String sql1 = "select * from cms_linksfilelist  ";
			ResultSet rs2 = ad.selectQuery(sql1);
			ArrayList list = new ArrayList();
			LinksForm linksForm2 = null;
			int count = 0;
			while (rs2.next()) {

				linksForm2 = new LinksForm();
				linksForm2.setImageId(rs2.getString("id") + "," + count);
				linksForm2.setImageName(rs2.getString("file_name"));
				linksForm2.setImageTitle(rs2.getString("file_description"));
				list.add(linksForm2);
				count++;

			}
			
			if ( a1.size() > 0)
				request.setAttribute("listOfannouncement", a1);
			else
				request.setAttribute("NoRecords", "NoRecords");
			
			
			if (a1.size() != 0) {
				linksForm.setTotalRecords(totalRecords);
				linksForm.setStartRecord(startRecord);
				linksForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			} else {
				int start = startRecord;
				int end = startRecord;

				linksForm.setTotalRecords(totalRecords);
				linksForm.setStartRecord(start);
				linksForm.setEndRecord(end);

			}
			if (a1.size() < 10) {
				linksForm.setTotalRecords(totalRecords);
				linksForm.setStartRecord(startRecord);
				linksForm.setEndRecord(startRecord + a1.size() - 1);
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

		
		request.setAttribute("addbutton", "addbutton");
		request.setAttribute("displayRecordNo", "displayRecordNo");	

			}
		return mapping.findForward("displayGalleryList");
	}

	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GalleryForm linksForm = (GalleryForm) form;

		String mainLinkName = "About Company";
		String subLinkName = "Gallery";
		String id = request.getParameter("id");
		String sql = "select * from cms_sublinks  ";
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		LinksDao ad = new LinksDao();
		ArrayList linkIdList = new ArrayList();
		ArrayList linkValueList = new ArrayList();
		try {
			ResultSet rs = ad.selectQuery(sql);
			int totalRecords = linksForm.getTotalRecords();// 21
			int endRecord = linksForm.getStartRecord() - 1;// 20
			int startRecord = linksForm.getStartRecord() - 10;// 11
			if (startRecord == 1) {
				request.setAttribute("disablePreviousButton",
						"disablePreviousButton");
				endRecord = 10;
			}
			linksForm.setTotalRecords(totalRecords);
			linksForm.setStartRecord(1);
			linksForm.setEndRecord(10);

			String sql2 = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID desc) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name,c.archived_status"
					+ "  From  cms_sublinks as c where main_linkname='"
					+ mainLinkName
					+ "' and sub_linkname='"
					+ subLinkName
					+ "' ) as sub Where  sub.RowNum between '"
					+ startRecord
					+ "' and '"+endRecord+"' order by content_year desc ";
			ResultSet rs1 = ad.selectQuery(sql2);
			ArrayList a1 = new ArrayList();
			GalleryForm linksForm1 = null;
			while (rs1.next()) {
				linksForm1 = new GalleryForm();
				linksForm1.setRowno(rs1.getInt("RowNum"));
				linksForm1.setCmsLinkId(rs1.getString("id"));
				linksForm1.setMenuName(rs1.getString("main_linkname"));
				linksForm1.setLinkName(rs1.getString("sub_linkname"));
				linksForm1.setGalleryTitle(rs1.getString("link_name"));
				linksForm1.setContentYear(rs1.getString("content_year"));
				linksForm1.setIconName(rs1.getString("icon_name"));
				int archiveStatus = rs1.getInt("archived_status");
				if (archiveStatus == 1) {
					linksForm1.setArchiveStatus("On");
				} else {
					linksForm1.setArchiveStatus("Off");
				}
				a1.add(linksForm1);
			}
			request.setAttribute("CmsLinkDetails", a1);
			String sql1 = "select * from cms_linksfilelist  ";
			ResultSet rs2 = ad.selectQuery(sql1);
			ArrayList list = new ArrayList();
			LinksForm linksForm2 = null;
			int count = 0;
			while (rs2.next()) {

				linksForm2 = new LinksForm();
				linksForm2.setImageId(rs2.getString("id") + "," + count);
				linksForm2.setImageName(rs2.getString("file_name"));
				linksForm2.setImageTitle(rs2.getString("file_description"));
				list.add(linksForm2);
				count++;

			}
			
			if ( a1.size() > 0)
				request.setAttribute("listOfannouncement", a1);
			else
				request.setAttribute("NoRecords", "NoRecords");
			
			
			linksForm.setTotalRecords(totalRecords);
			linksForm.setStartRecord(startRecord);
			linksForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			if (startRecord != 1)
				request.setAttribute("previousButton", "previousButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
			if (a1.size() < 10) {
				linksForm.setStartRecord(1);
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton",
						"disablePreviousButton");
			}

			
			request.setAttribute("displaySublinkField", "displaySublinkField");
			request.setAttribute("submitButton", "submitButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");

		} catch (Exception se) {
			se.printStackTrace();
		}
		request.setAttribute("addbutton", "addbutton");

		return mapping.findForward("displayGalleryList");
	}	


}
