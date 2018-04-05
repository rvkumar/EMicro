package com.microlabs.newsandmedia.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
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

import com.microlabs.admin.dao.LinksDao;
import com.microlabs.admin.form.LinksForm;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.newsandmedia.form.ManageCMSForm;
import com.microlabs.utilities.UserInfo;

public class ManageCMSAction extends DispatchAction {

	public ActionForward deleteCMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EssDao ad = new EssDao();
		ManageCMSForm linksForm = (ManageCMSForm) form;
		String archiveStatus = linksForm.getArchiveType();
		int reqId = Integer.parseInt(request.getParameter("reqID"));
		String link = "";
		String subLink = "";
		String subsubLink = "";
		String year = "";
		String month = "";
		String getDetails = "select * from archieves where id='" + reqId + "'";
		ResultSet rsDetails = ad.selectQuery(getDetails);
	
		try {
			while (rsDetails.next()) {
				link = rsDetails.getString("link_name");
				subLink = rsDetails.getString("module");
				subsubLink = rsDetails.getString("sub_link");
				year = rsDetails.getString("year");
				month = rsDetails.getString("month");
			}

			if (subLink.equals("Main")) {

				String deleteFiles = "delete from filelist where link_name ='"
						+ link
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ year + "' and month='" + month + "'";
				ad.SqlExecuteUpdate(deleteFiles);

				String deleteImages = "delete from Imagelist where link_name ='"
						+ link
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ year + "' and month='" + month + "'";
				ad.SqlExecuteUpdate(deleteImages);
				String deleteVideos = "delete from video_list where link_name ='"
						+ link
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ year + "' and month='" + month + "'";
				ad.SqlExecuteUpdate(deleteVideos);
			} else {
				if (subsubLink == null && !subLink.equals("Main")) {
					System.out.println("links");
					String deleteFiles = "delete from filelist where link_name ='"
							+ link
							+ "' and sub_link_name='"
							+ subLink
							+ "' and sub_sub_linkname='' and year='"
							+ year
							+ "' and month='" + month + "'";
					ad.SqlExecuteUpdate(deleteFiles);
					String deleteImages = "delete from Imagelist where link_name ='"
							+ link
							+ "' and sub_link_name='"
							+ subLink
							+ "' and sub_sub_linkname='' and year='"
							+ year
							+ "' and month='" + month + "'";
					ad.SqlExecuteUpdate(deleteImages);
					String deleteVideos = "delete from video_list where link_name ='"
							+ link
							+ "' and sub_link_name='"
							+ subLink
							+ "' and sub_sub_linkname='' and year='"
							+ year
							+ "' and month='" + month + "'";
					ad.SqlExecuteUpdate(deleteVideos);
				} else {
					System.out.println("sub links");
					String deleteFiles = "delete  from filelist where link_name ='"
							+ link
							+ "' and sub_link_name='"
							+ subLink
							+ "' and sub_sub_linkname='"
							+ subsubLink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";
					ad.SqlExecuteUpdate(deleteFiles);
					String deleteImages = "delete from Imagelist where link_name ='"
							+ link
							+ "' and sub_link_name='"
							+ subLink
							+ "' and sub_sub_linkname='"
							+ subsubLink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";
					ad.SqlExecuteUpdate(deleteImages);
					String deleteVideos = "delete from video_list where link_name ='"
							+ link
							+ "' and sub_link_name='"
							+ subLink
							+ "' and sub_sub_linkname='"
							+ subsubLink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";
					ad.SqlExecuteUpdate(deleteVideos);
				}
			}
			String deleteData = "delete from archieves where id='" + reqId
					+ "'";
			ad.SqlExecuteUpdate(deleteData);

			ad.connClose();

			if (archiveStatus.equals("Unarchive"))
				displayUnarchive(mapping, linksForm, request, response);
			else
				displayArchive(mapping, linksForm, request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("menuList");
	}

	public ActionForward newCMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EssDao ad = new EssDao();
		ManageCMSForm linksForm = (ManageCMSForm) form;
		displayMainLinks(mapping, linksForm, request, response);
		return mapping.findForward("displayMainLinks");
	}

	public ActionForward editCMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EssDao ad = new EssDao();
		System.out.println("displayMainLinks()----");
		ManageCMSForm linksForm = (ManageCMSForm) form;
		String menu = "";
		String linkName = "";
		String subLinkName = "";
		String year = linksForm.getYear();
		String month = linksForm.getMonth();
		String reqId = request.getParameter("reqID");
		try {
			String getDetails = "select * from archieves where id='" + reqId
					+ "' ";
			ResultSet rsDetails = ad.selectQuery(getDetails);
			while (rsDetails.next()) {
				menu = rsDetails.getString("module");
				linkName = rsDetails.getString("link_name");
				subLinkName = rsDetails.getString("sub_link");
				year = rsDetails.getString("year");
				month = rsDetails.getString("month");
			}
			linksForm.setLinkName(linkName);
			linksForm.setYear(year);
			linksForm.setMonth(month);
			if (!menu.equals("Main") && subLinkName != null) {
				try {
					String sql = "select * from links where module='"
							+ linkName
							+ "'  and sub_linkname is null and  delete_status=1";
					ResultSet rs = ad
							.selectQuery("select * from links where module='"
									+ linkName + "' and  delete_status=1 ");
					ArrayList subLinkValueList = new ArrayList();
					while (rs.next()) {
						subLinkValueList.add(rs.getString("link_name"));
					}
					linksForm.setSublinkValueList(subLinkValueList);
					request.setAttribute("subLinkValueListID", subLinkValueList);
					request.setAttribute("submitButton", "submitButton");
				} catch (Exception e) {
					e.printStackTrace();
				}

				linksForm.setLinkName(menu);
				linksForm.setSubLinkName(subLinkName);
				linksForm.setSubSubLinkName(linkName);
				LinksDao adlinks = new LinksDao();
				int count = 0;
				String arcStatus = "";
				linksForm.setArchiveStatus("");
				linksForm.setContentDescriptionAdmin("");
				if (year != null) {

					// links content display code

					String getArchievDataCount = "select count(*) from archieves where link_name='"
							+ linkName
							+ "' and module='"
							+ menu
							+ "' and sub_link='"
							+ subLinkName
							+ "'  and  year='"
							+ year
							+ "' and month='"
							+ month
							+ "' ";
					ResultSet rsArchievDataCount = adlinks
							.selectQuery(getArchievDataCount);
					while (rsArchievDataCount.next()) {
						count = rsArchievDataCount.getInt(1);
					}
					String files = "";
					String videos = "";
					String images = "";
					ArrayList fileList = new ArrayList();
					ArrayList imgeList = new ArrayList();

					ArrayList videoList = new ArrayList();
					ArrayList imageList = new ArrayList();
					if (count >= 1) {
						String getArchievData = "select * from archieves where id='"
								+ reqId + "' ";
						System.out.println("getArchievData=" + getArchievData);
						ResultSet rsArchiveData = adlinks
								.selectQuery(getArchievData);
						String file;
						while (rsArchiveData.next()) {
							linksForm.setArchiveStatus(rsArchiveData
									.getString("status"));
							linksForm.setContentDescriptionAdmin(rsArchiveData
									.getString("content_description"));
							arcStatus = rsArchiveData.getString("status");
							if (arcStatus.equalsIgnoreCase("yes")) {
								linksForm.setArchiveStatus(rsArchiveData
										.getString("status"));
							}

							files = rsArchiveData.getString("file_name");
							if (files.equalsIgnoreCase("")) {

							} else {
								String v[] = files.split(",");
								int l = v.length;
								for (int i = 0; i < l; i++) {
									ManageCMSForm linksForm1 = new ManageCMSForm();
									int x = v[i].lastIndexOf("/");
									files = v[i].substring(x + 1);
									linksForm1.setFileList(files);

									/*
									 * String insertFiles =
									 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
									 * +linkName+"','','"+files+"','','')";
									 * System
									 * .out.println("insertFiles="+insertFiles);
									 * adlinks.SqlExecuteUpdate(insertFiles);
									 */

									fileList.add(linksForm1);
								}
							}
							images = rsArchiveData.getString("image_name");
							if (images.equalsIgnoreCase("")) {

							} else {
								String v[] = images.split(",");
								int l = v.length;
								for (int i = 0; i < l; i++) {
									ManageCMSForm linksForm1 = new ManageCMSForm();
									int x = v[i].lastIndexOf("/");
									files = v[i].substring(x + 1);
									linksForm1.setImageList(files);

									/*
									 * String insertFiles =
									 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
									 * +linkName+"','','"+files+"','','')";
									 * System
									 * .out.println("insertFiles="+insertFiles);
									 * adlinks.SqlExecuteUpdate(insertFiles);
									 */

									imgeList.add(linksForm1);
								}
							}

							videos = rsArchiveData.getString("video_name");
							if (videos.equalsIgnoreCase("")) {

							} else {
								String v1[] = videos.split(",");
								int l1 = v1.length;
								for (int i = 0; i < l1; i++) {
									ManageCMSForm linksFormVideo = new ManageCMSForm();
									int y = v1[i].lastIndexOf("/");
									videos = v1[i].substring(y + 1);
									linksFormVideo.setVideoFilesList(videos);
									/*
									 * String insertVideos =
									 * "insert into video_list(link_name,video_path,video_name,sub_link_name,sub_sub_linkname) values('"
									 * +linkName+"','','"+videos+"','','')";
									 * System
									 * .out.println("insertFiles="+insertVideos
									 * );
									 * adlinks.SqlExecuteUpdate(insertVideos);
									 */
									videoList.add(linksFormVideo);
								}
							}
						}
					}
					if (files.equalsIgnoreCase("")) {

					} else {
						request.setAttribute("listName", fileList);

					}
					if (images.equalsIgnoreCase("")) {

					} else {
						request.setAttribute("imageLists", imgeList);

					}
					if (videos.equalsIgnoreCase("")) {

					} else {
						request.setAttribute("videosList", videoList);
					}
					request.setAttribute("displaySublinkField",
							"displaySublinkField");
				}

				String sql = "select * from links where link_name='" + linkName
						+ "' and " + "status is null and module='Main' ";
				System.out.println("sql=" + sql);
				ResultSet rs = ad.selectQuery(sql);
				String linkId = null;
				while (rs.next()) {
					linkId = rs.getString("id");
					linksForm.setSubLinkName(rs.getString("link_name"));

					// linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
				}
				request.setAttribute("linkId", linkId);

				linksForm.setLinkName(menu);
				linksForm.setSubLinkName(subLinkName);
				linksForm.setSubSubLinkName(linkName);
				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);
				linksForm.setLinkName(menu);
				linksForm.setSubLinkName(subLinkName);
				linksForm.setSubSubLinkName(linkName);

			}
			if (!menu.equals("Main") && subLinkName == null) {
				try {
					String linksId = request.getParameter("sId");

					try {
						String sql = "select * from links where module='"
								+ linkName
								+ "'  and sub_linkname is null and  delete_status=1";
						ResultSet rs = ad
								.selectQuery("select * from links where module='"
										+ linkName + "' and  delete_status=1 ");
						ArrayList subLinkValueList = new ArrayList();
						while (rs.next()) {
							subLinkValueList.add(rs.getString("link_name"));
						}
						linksForm.setSublinkValueList(subLinkValueList);
						request.setAttribute("subLinkValueListID",
								subLinkValueList);
						request.setAttribute("submitButton", "submitButton");
					} catch (Exception e) {
						e.printStackTrace();
					}

					String subsublink = linksForm.getSubSubLinkName();
					String sublink = menu;
					linksForm.setLinkName(linkName);
					linksForm.setSubLinkName(menu);
					linksForm.setSubSubLinkName(subsublink);
					LinksDao adlinks = new LinksDao();
					int count = 0;
					String arcStatus = "";
					linksForm.setArchiveStatus("");
					linksForm.setContentDescriptionAdmin("");
					if (year != null) {

						// links content display code

						String getArchievDataCount = "select count(*) from archieves where link_name='"
								+ linkName
								+ "' and module='"
								+ sublink
								+ "'  and  year='"
								+ year
								+ "' and month='"
								+ month + "'";
						ResultSet rsArchievDataCount = adlinks
								.selectQuery(getArchievDataCount);
						while (rsArchievDataCount.next()) {
							count = rsArchievDataCount.getInt(1);
						}
						String files = "";
						String videos = "";
						String images = "";
						ArrayList fileList = new ArrayList();
						ArrayList imgeList = new ArrayList();

						ArrayList videoList = new ArrayList();
						ArrayList imageList = new ArrayList();
						if (count >= 1) {
							String getArchievData = "select * from archieves where id='"
									+ reqId + "' ";
							System.out.println("getArchievData="
									+ getArchievData);
							ResultSet rsArchiveData = adlinks
									.selectQuery(getArchievData);
							String file;
							while (rsArchiveData.next()) {
								linksForm.setArchiveStatus(rsArchiveData
										.getString("status"));
								linksForm
										.setContentDescriptionAdmin(rsArchiveData
												.getString("content_description"));
								arcStatus = rsArchiveData.getString("status");
								if (arcStatus.equalsIgnoreCase("yes")) {
									linksForm.setArchiveStatus(rsArchiveData
											.getString("status"));
								}

								files = rsArchiveData.getString("file_name");
								if (files.equalsIgnoreCase("")) {

								} else {
									String v[] = files.split(",");
									int l = v.length;
									for (int i = 0; i < l; i++) {
										ManageCMSForm linksForm1 = new ManageCMSForm();
										int x = v[i].lastIndexOf("/");
										files = v[i].substring(x + 1);
										linksForm1.setFileList(files);

										/*
										 * String insertFiles =
										 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
										 * +linkName+"','','"+files+"','','')";
										 * System
										 * .out.println("insertFiles="+insertFiles
										 * );
										 * adlinks.SqlExecuteUpdate(insertFiles
										 * );
										 */

										fileList.add(linksForm1);
									}
								}
								images = rsArchiveData.getString("image_name");
								if (images == null
										|| images.equalsIgnoreCase("")) {

								} else {
									String v[] = images.split(",");
									int l = v.length;
									for (int i = 0; i < l; i++) {
										ManageCMSForm linksForm1 = new ManageCMSForm();
										int x = v[i].lastIndexOf("/");
										files = v[i].substring(x + 1);
										linksForm1.setImageList(files);

										/*
										 * String insertFiles =
										 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
										 * +linkName+"','','"+files+"','','')";
										 * System
										 * .out.println("insertFiles="+insertFiles
										 * );
										 * adlinks.SqlExecuteUpdate(insertFiles
										 * );
										 */

										imgeList.add(linksForm1);
									}
								}

								videos = rsArchiveData.getString("video_name");
								if (videos.equalsIgnoreCase("")) {

								} else {
									String v1[] = videos.split(",");
									int l1 = v1.length;
									for (int i = 0; i < l1; i++) {
										ManageCMSForm linksFormVideo = new ManageCMSForm();
										int y = v1[i].lastIndexOf("/");
										videos = v1[i].substring(y + 1);
										linksFormVideo
												.setVideoFilesList(videos);
										/*
										 * String insertVideos =
										 * "insert into video_list(link_name,video_path,video_name,sub_link_name,sub_sub_linkname) values('"
										 * +linkName+"','','"+videos+"','','')";
										 * System
										 * .out.println("insertFiles="+insertVideos
										 * );
										 * adlinks.SqlExecuteUpdate(insertVideos
										 * );
										 */
										videoList.add(linksFormVideo);
									}
								}
							}
						}
						if (files.equalsIgnoreCase("")) {

						} else {
							request.setAttribute("listName", fileList);

						}
						if (images == null || images.equalsIgnoreCase("")) {

						} else {
							request.setAttribute("imageLists", imgeList);

						}
						if (videos.equalsIgnoreCase("")) {

						} else {
							request.setAttribute("videosList", videoList);
						}
						request.setAttribute("displaySublinkField",
								"displaySublinkField");
					}
					String sql = "select * from links where link_name='"
							+ sublink + "' and "
							+ "status is null and module='Main' ";
					System.out.println("sql=" + sql);
					ResultSet rs = ad.selectQuery(sql);
					String linkId = null;
					while (rs.next()) {
						linkId = rs.getString("id");
						linksForm.setSubLinkName(rs.getString("link_name"));
						linksForm.setSubLinkId(linksId);
						linksForm.setLinkId(rs.getString("id"));
						linksForm.setLinkPath(rs.getString("link_path"));
						linksForm.setMethodName(rs.getString("method"));
						linksForm.setPriority(rs.getString("priority"));
						// linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
					}
					request.setAttribute("linkId", linkId);

					linksForm.setLinkName(linkName);
					linksForm.setSubLinkName(sublink);
					try {

						ResultSet rs1 = ad
								.selectQuery("select * from links where status is null");
						ArrayList linkIdList = new ArrayList();
						ArrayList linkValueList = new ArrayList();

						while (rs1.next()) {
							linkIdList.add(rs1.getString("link_name"));
							linkValueList.add(rs1.getString("link_name"));
						}
						linksForm.setLinkIdList(linkIdList);
						linksForm.setLinkValueList(linkValueList);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Exception caught ="
								+ e.getMessage());
					}
					try {
						ResultSet rs1 = ad
								.selectQuery("select * from links where module='"
										+ menu + "' and  delete_status=1 ");
						ArrayList subLinkValueList = new ArrayList();
						while (rs1.next()) {
							subLinkValueList.add(rs1.getString("link_name"));
						}
						linksForm.setSublinkValueList(subLinkValueList);
						request.setAttribute("subLinkValueListID",
								subLinkValueList);
						request.setAttribute("submitButton", "submitButton");
					} catch (Exception e) {
						e.printStackTrace();
					}

					linksForm.setLinkName(sublink);
					linksForm.setSubLinkName(linkName);
					// (mapping, form, request, response);

					// get sub link archive data

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (menu.equals("Main")) {
				try {

					String linksId = request.getParameter("sId");

					System.out.println("Link Name=" + linkName);
					linksForm.setLinkName(linkName);

					try {
						String sql = "select * from links where module='"
								+ linkName
								+ "'  and sub_linkname is null and  delete_status=1";
						ResultSet rs = ad
								.selectQuery("select * from links where module='"
										+ linkName + "' and  delete_status=1 ");
						ArrayList subLinkValueList = new ArrayList();
						while (rs.next()) {
							subLinkValueList.add(rs.getString("link_name"));
						}
						linksForm.setSublinkValueList(subLinkValueList);
						request.setAttribute("subLinkValueListID",
								subLinkValueList);
						request.setAttribute("submitButton", "submitButton");
					} catch (Exception e) {
						e.printStackTrace();
					}
					String sublink = linksForm.getSubLinkName();

					String subsublink = linksForm.getSubSubLinkName();

					linksForm.setLinkName(linkName);
					linksForm.setSubLinkName(sublink);
					linksForm.setSubSubLinkName(subsublink);
					LinksDao adlinks = new LinksDao();
					int count = 0;
					String arcStatus = "";
					linksForm.setArchiveStatus("");
					linksForm.setContentDescriptionAdmin("");

					if (year != null) {

						// links content display code

						String getArchievDataCount = "select count(*) from archieves where link_name='"
								+ linkName
								+ "'  and  year='"
								+ year
								+ "' and month='" + month + "'";
						ResultSet rsArchievDataCount = adlinks
								.selectQuery(getArchievDataCount);
						while (rsArchievDataCount.next()) {
							count = rsArchievDataCount.getInt(1);
						}
						String files = "";
						String videos = "";
						String images = "";

						ArrayList fileList = new ArrayList();
						ArrayList imgeList = new ArrayList();

						ArrayList videoList = new ArrayList();
						ArrayList imageList = new ArrayList();
						if (count >= 1) {
							String getArchievData = "select * from archieves where id='"
									+ reqId + "'  ";
							System.out.println("getArchievData="
									+ getArchievData);
							ResultSet rsArchiveData = adlinks
									.selectQuery(getArchievData);
							String file;
							while (rsArchiveData.next()) {
								linksForm.setArchiveStatus(rsArchiveData
										.getString("status"));
								linksForm
										.setContentDescriptionAdmin(rsArchiveData
												.getString("content_description"));
								arcStatus = rsArchiveData.getString("status");
								if (arcStatus.equalsIgnoreCase("yes")) {
									linksForm.setArchiveStatus(rsArchiveData
											.getString("status"));
								}
								files = rsArchiveData.getString("file_name");
								if (files.equalsIgnoreCase("")) {

								} else {
									String v[] = files.split(",");
									int l = v.length;
									for (int i = 0; i < l; i++) {
										ManageCMSForm linksForm1 = new ManageCMSForm();
										int x = v[i].lastIndexOf("/");
										files = v[i].substring(x + 1);
										linksForm1.setFileList(files);

										/*
										 * String insertFiles =
										 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
										 * +linkName+"','','"+files+"','','')";
										 * System
										 * .out.println("insertFiles="+insertFiles
										 * );
										 * adlinks.SqlExecuteUpdate(insertFiles
										 * );
										 */

										fileList.add(linksForm1);
									}
								}
								images = rsArchiveData.getString("image_name");
								if (images.equalsIgnoreCase("")) {

								} else {
									String v[] = images.split(",");
									int l = v.length;
									for (int i = 0; i < l; i++) {
										ManageCMSForm linksForm1 = new ManageCMSForm();
										int x = v[i].lastIndexOf("/");
										files = v[i].substring(x + 1);
										linksForm1.setImageList(files);

										/*
										 * String insertFiles =
										 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
										 * +linkName+"','','"+files+"','','')";
										 * System
										 * .out.println("insertFiles="+insertFiles
										 * );
										 * adlinks.SqlExecuteUpdate(insertFiles
										 * );
										 */

										imgeList.add(linksForm1);
									}
								}

								videos = rsArchiveData.getString("video_name");
								if (videos.equalsIgnoreCase("")) {

								} else {
									String v1[] = videos.split(",");
									int l1 = v1.length;
									for (int i = 0; i < l1; i++) {
										ManageCMSForm linksFormVideo = new ManageCMSForm();
										int y = v1[i].lastIndexOf("/");
										videos = v1[i].substring(y + 1);
										linksFormVideo
												.setVideoFilesList(videos);
										/*
										 * String insertVideos =
										 * "insert into video_list(link_name,video_path,video_name,sub_link_name,sub_sub_linkname) values('"
										 * +linkName+"','','"+videos+"','','')";
										 * System
										 * .out.println("insertFiles="+insertVideos
										 * );
										 * adlinks.SqlExecuteUpdate(insertVideos
										 * );
										 */
										videoList.add(linksFormVideo);
									}
								}
							}
						}
						if (files.equalsIgnoreCase("")) {

						} else {
							request.setAttribute("listName", fileList);

						}
						if (images.equalsIgnoreCase("")) {

						} else {
							request.setAttribute("imageLists", imgeList);

						}
						if (videos.equalsIgnoreCase("")) {

						} else {
							request.setAttribute("videosList", videoList);
						}
						request.setAttribute("displaySublinkField",
								"displaySublinkField");
					}

					if (linkName.equalsIgnoreCase("Help")) {

						String sql = "select * from links where link_name='"
								+ linkName + "' and " + "status is null  ";
						System.out.println("sql=" + sql);
						ResultSet rs = ad.selectQuery(sql);
						String linkId = null;
						while (rs.next()) {
							linkId = rs.getString("id");
							linksForm.setSubLinkName(rs.getString("link_name"));
							linksForm.setSubLinkId(linksId);
							linksForm.setLinkId(rs.getString("id"));
							linksForm.setLinkPath(rs.getString("link_path"));
							linksForm.setMethodName(rs.getString("method"));
							linksForm.setPriority(rs.getString("priority"));
							// linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
						}
						request.setAttribute("linkId", linkId);
						displayMainLinks(mapping, form, request, response);
						linksForm.setLinkName(linkName);

						return mapping.findForward("displayMainLinks");
					} else {

						String sql = "select * from links where link_name='"
								+ linkName + "' and "
								+ "status is null and module='Main' ";
						System.out.println("sql=" + sql);
						ResultSet rs = ad.selectQuery(sql);
						String linkId = null;
						while (rs.next()) {
							linkId = rs.getString("id");
							linksForm.setSubLinkName(rs.getString("link_name"));
							linksForm.setSubLinkId(linksId);
							linksForm.setLinkId(rs.getString("id"));
							linksForm.setLinkPath(rs.getString("link_path"));
							linksForm.setMethodName(rs.getString("method"));
							linksForm.setPriority(rs.getString("priority"));
							// linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
						}
						request.setAttribute("linkId", linkId);

					}
					displayMainLinks(mapping, form, request, response);
					linksForm.setLinkName(linkName);
					// (mapping, form, request, response);

					// get sub link archive data

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("displayMainLinks");
	}

	public ActionForward displayArchive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EssDao ad = new EssDao();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		ManageCMSForm linksForm = (ManageCMSForm) form;// TODO Auto-generated
														// method stub
		int startRecord = 0;
		int endRecord = 0;
		int totalRecords =0;
		
		String getCount = "select * from archieves where status='yes'   order by module";
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
		
		
		try {
			LinkedList list = new LinkedList();
			String getUnarchive = "select * from archieves where status='yes'  order by module";
			ResultSet rsUnarchive = ad.selectQuery(getUnarchive);

			while (rsUnarchive.next()) {
				// contentDescription=Jsoup.parse(contentDescription).text();
				ManageCMSForm m = new ManageCMSForm();
				m.setId(rsUnarchive.getInt("id"));
				String linkName = rsUnarchive.getString("module");
				if (linkName.equals("Main"))
					m.setLinkName("Menu");
				else
					m.setLinkName(rsUnarchive.getString("module"));
				m.setSubLinkName(rsUnarchive.getString("link_name"));
				String subLink = rsUnarchive.getString("sub_link");
				if (subLink != null) {
					m.setSubSubLinkName(rsUnarchive.getString("link_name"));
					m.setSubLinkName(rsUnarchive.getString("sub_link"));
				} else
					m.setSubSubLinkName("");
				m.setArchiveStatus(rsUnarchive.getString("status"));
				String dateFormate = "";
				int month = rsUnarchive.getInt("month");
				String monthName[] = { "", "Jan", "Feb", "Mar", "Apr", "May",
						"Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec" };
				dateFormate = monthName[month] + "-"
						+ rsUnarchive.getString("year");
				m.setYear(dateFormate);
				list.add(m);

			}
			if (list.size() > 0)
				request.setAttribute("data", list);
			else
				request.setAttribute("NoRecords", "NoRecords");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("menuList");
	}

	public ActionForward displayUnarchive(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		EssDao ad = new EssDao();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		ManageCMSForm linksForm = (ManageCMSForm) form;// TODO Auto-generated
        
		int startRecord = 0;
		int endRecord = 0;
		int totalRecords =0;
		String getCount = "select * from archieves where status='null'  order by module";
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
		
		
		
		

		try {
			LinkedList list = new LinkedList();
			String getUnarchive = "select * from (select ROW_NUMBER() over( order by id ) AS RowNum,* from archieves where status='null')as  sub Where  sub.RowNum between 1 and 10 order by module";
			ResultSet rsUnarchive = ad.selectQuery(getUnarchive);

			while (rsUnarchive.next()) {
				// contentDescription=Jsoup.parse(contentDescription).text();
				ManageCMSForm m = new ManageCMSForm();
				m.setId(rsUnarchive.getInt("id"));
				String linkName = rsUnarchive.getString("module");
				if (linkName.equals("Main"))
					m.setLinkName("Menu");
				else
					m.setLinkName(rsUnarchive.getString("module"));
				m.setSubLinkName(rsUnarchive.getString("link_name"));
				String subLink = rsUnarchive.getString("sub_link");
				if (subLink != null) {
					m.setSubSubLinkName(rsUnarchive.getString("link_name"));
					m.setSubLinkName(rsUnarchive.getString("sub_link"));
				} else
					m.setSubSubLinkName("");
				m.setArchiveStatus(rsUnarchive.getString("status"));
				String dateFormate = "";
				int month = rsUnarchive.getInt("month");
				String monthName[] = { "", "Jan", "Feb", "Mar", "Apr", "May",
						"Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec" };
				dateFormate = monthName[month] + "-"
						+ rsUnarchive.getString("year");
				m.setYear(dateFormate);
				list.add(m);

			}
			request.setAttribute("data", list);
			if (list.size() > 0)
				request.setAttribute("data", list);
			else
				request.setAttribute("NoRecords", "NoRecords");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("menuList");
	}
	
	public ActionForward previousRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EssDao ad = new EssDao();
		ManageCMSForm linksForm = (ManageCMSForm) form;
		HttpSession session = request.getSession();
		
		UserInfo user = (UserInfo) session.getAttribute("user");
		try {
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

			LinkedList list = new LinkedList();
		    
        System.out.println();
		String getApprovers ="";
	if(linksForm.getArchiveType().equalsIgnoreCase("Unarchive"))
	{	
	 getApprovers = "select * from (select ROW_NUMBER() over( order by id ) AS RowNum,* from archieves where status='null')as  sub Where  sub.RowNum between '"+ startRecord +"' and '"+ endRecord+"' order by module";
	}
	else
	{
		getApprovers = "select * from (select ROW_NUMBER() over( order by id ) AS RowNum,* from archieves where status='yes')as  sub Where  sub.RowNum between '"+ startRecord +"' and '"+ endRecord+"' order by module";	
	}
	
		ResultSet rsUnarchive = ad.selectQuery(getApprovers);
		try {
			while (rsUnarchive.next()) {
				ManageCMSForm m = new ManageCMSForm();
				m.setId(rsUnarchive.getInt("id"));
				String linkName = rsUnarchive.getString("module");
				if (linkName.equals("Main"))
					m.setLinkName("Menu");
				else
					m.setLinkName(rsUnarchive.getString("module"));
				m.setSubLinkName(rsUnarchive.getString("link_name"));
				String subLink = rsUnarchive.getString("sub_link");
				if (subLink != null) {
					m.setSubSubLinkName(rsUnarchive.getString("link_name"));
					m.setSubLinkName(rsUnarchive.getString("sub_link"));
				} else
					m.setSubSubLinkName("");
				m.setArchiveStatus(rsUnarchive.getString("status"));
				String dateFormate = "";
				int month = rsUnarchive.getInt("month");
				String monthName[] = { "", "Jan", "Feb", "Mar", "Apr", "May",
						"Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec" };
				dateFormate = monthName[month] + "-"
						+ rsUnarchive.getString("year");
				m.setYear(dateFormate);
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		
		request.setAttribute("data", list);
		
		linksForm.setTotalRecords(totalRecords);
		linksForm.setStartRecord(startRecord);
		linksForm.setEndRecord(endRecord);
		request.setAttribute("nextButton", "nextButton");
		if (startRecord != 1)
			request.setAttribute("previousButton", "previousButton");
		request.setAttribute("displayRecordNo", "displayRecordNo");
		if (list.size() < 10) {
			linksForm.setStartRecord(1);
			request.setAttribute("previousButton", "");
			request.setAttribute("disablePreviousButton",
					"disablePreviousButton");
		}
		


	} catch (Exception e) {
		e.printStackTrace();
	}
	
		return mapping.findForward("menuList");

	}
	
	
	public ActionForward nextRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ManageCMSForm linksForm = (ManageCMSForm) form;
		HttpSession session = request.getSession();
		EssDao ad = new EssDao();
		System.out.println(linksForm.getArchiveType());
		
		UserInfo user = (UserInfo) session.getAttribute("user");

		
		try {
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
		

		
				LinkedList list = new LinkedList();
				String getApprovers ="";
		if(linksForm.getArchiveType().equalsIgnoreCase("Unarchive"))
		{	
		 getApprovers = "select * from (select ROW_NUMBER() over( order by id ) AS RowNum,* from archieves where status='null')as  sub Where  sub.RowNum between '"+ startRecord +"' and '"+ endRecord+"' order by module";
		}
		else
		{
			getApprovers = "select * from (select ROW_NUMBER() over( order by id ) AS RowNum,* from archieves where status='yes')as  sub Where  sub.RowNum between '"+ startRecord +"' and '"+ endRecord+"' order by module";	
		}
		ResultSet rsUnarchive = ad.selectQuery(getApprovers);
		try {
			while (rsUnarchive.next()) {
				ManageCMSForm m = new ManageCMSForm();
				m.setId(rsUnarchive.getInt("id"));
				String linkName = rsUnarchive.getString("module");
				if (linkName.equals("Main"))
					m.setLinkName("Menu");
				else
					m.setLinkName(rsUnarchive.getString("module"));
				m.setSubLinkName(rsUnarchive.getString("link_name"));
				String subLink = rsUnarchive.getString("sub_link");
				if (subLink != null) {
					m.setSubSubLinkName(rsUnarchive.getString("link_name"));
					m.setSubLinkName(rsUnarchive.getString("sub_link"));
				} else
					m.setSubSubLinkName("");
				m.setArchiveStatus(rsUnarchive.getString("status"));
				String dateFormate = "";
				int month = rsUnarchive.getInt("month");
				String monthName[] = { "", "Jan", "Feb", "Mar", "Apr", "May",
						"Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec" };
				dateFormate = monthName[month] + "-"
						+ rsUnarchive.getString("year");
				m.setYear(dateFormate);
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		request.setAttribute("data", list);
		if (list.size() != 0) {
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
		if (list.size() < 10) {
			linksForm.setTotalRecords(totalRecords);
			linksForm.setStartRecord(startRecord);
			linksForm.setEndRecord(startRecord + list.size() - 1);
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
		return mapping.findForward("menuList");
}


	public int storeVideos(String path, int filesize, String linkName,
			String year, String fileName, String sublink, String subSubLink) {
		try {
			File fileToCreate1 = new File(path, fileName);
			FileOutputStream outputStream = new FileOutputStream(fileToCreate1);
			outputStream.write(filesize);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public ActionForward displaySublinks1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ManageCMSForm linksForm = (ManageCMSForm) form;// TODO Auto-generated
														// method stub
		String linkName = request.getParameter("linkName");
		// String id = request.getParameter("id");
		// request.setAttribute("MenuIcon", id);
		try {
			LinksDao ad = new LinksDao();
			String sql = "select * from links where module='" + linkName
					+ "'  and sub_linkname is null and  delete_status=1";
			ResultSet rs = ad.selectQuery("select * from links where module='"
					+ linkName + "' and  delete_status=1 ");
			ArrayList subLinkValueList = new ArrayList();
			while (rs.next()) {
				subLinkValueList.add(rs.getString("link_name"));
			}
			linksForm.setSublinkValueList(subLinkValueList);
			request.setAttribute("subLinkValueListID", subLinkValueList);
			request.setAttribute("submitButton", "submitButton");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// displayMainLinks(mapping, form, request, response);
		return mapping.findForward("sublinkAjax");

	}

	public ActionForward displaySubSublinks1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ManageCMSForm linksForm = (ManageCMSForm) form;// TODO Auto-generated
														// method stub
		/*
		 * String requiredValue=request.getParameter("subLinkname"); String
		 * a[]=requiredValue.split(",");
		 * 
		 * String linkName = a[0]; String subLink=a[1];
		 */

		String linkName = request.getParameter("Linkname");
		String subLink = request.getParameter("SubLinkname");
		String id = request.getParameter("id");
		request.setAttribute("MenuIcon", id);
		try {
			LinksDao ad = new LinksDao();
			String sql = "select * from links where module='" + linkName
					+ "'  and sub_linkname='" + subLink
					+ "' and delete_status is null";
			ResultSet rs = ad.selectQuery(sql);

			ArrayList subsubLinkValueList = new ArrayList();

			while (rs.next()) {

				subsubLinkValueList.add(rs.getString("link_name"));
			}

			linksForm.setSubsublinkValueList(subsubLinkValueList);
			request.setAttribute("subsubLinkValueListID", subsubLinkValueList);
			request.setAttribute("submitButton", "submitButton");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// displayMainLinks(mapping, form, request, response);
		// displaySublinks(mapping, form, request, response);
		return mapping.findForward("subsublinkAjax");

	}

	public ActionForward displaySubSubLinksCMS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("displaySubLinksCMS()----");
		ManageCMSForm linksForm = (ManageCMSForm) form;// TODO Auto-generated
														// method stub
		try {
			String module = request.getParameter("MenuIcon2");
			request.setAttribute("MenuIcon", module);

			String linksId = request.getParameter("sId");
			String linkName = request.getParameter("LinkName");
			String sublink = request.getParameter("SubLink");

			String subsublink = request.getParameter("SubSubLink");

			System.out.println("Link Name=" + linkName);
			linksForm.setLinkName(linkName);

			try {
				LinksDao ad = new LinksDao();
				String sql = "select * from links where module='" + linkName
						+ "'  and sub_linkname is null and  delete_status=1";
				ResultSet rs = ad
						.selectQuery("select * from links where module='"
								+ linkName + "' and  delete_status=1 ");
				ArrayList subLinkValueList = new ArrayList();
				while (rs.next()) {
					subLinkValueList.add(rs.getString("link_name"));
				}
				linksForm.setSublinkValueList(subLinkValueList);
				request.setAttribute("subLinkValueListID", subLinkValueList);
				request.setAttribute("submitButton", "submitButton");
			} catch (Exception e) {
				e.printStackTrace();
			}

			linksForm.setLinkName(linkName);
			linksForm.setSubLinkName(sublink);
			linksForm.setSubSubLinkName(subsublink);
			LinksDao adlinks = new LinksDao();
			int count = 0;
			String arcStatus = "";
			linksForm.setArchiveStatus("");
			linksForm.setContentDescriptionAdmin("");
			String year = linksForm.getYear();
			String month = linksForm.getMonth();
			if (year != null) {

				linkName = request.getParameter("LinkName");
				// links content display code

				String getArchievDataCount = "select count(*) from archieves where link_name='"
						+ subsublink
						+ "' and module='"
						+ linkName
						+ "' and sub_link='"
						+ sublink
						+ "'  and  year='"
						+ year + "' and month='" + month + "' ";
				ResultSet rsArchievDataCount = adlinks
						.selectQuery(getArchievDataCount);
				while (rsArchievDataCount.next()) {
					count = rsArchievDataCount.getInt(1);
				}
				String files = "";
				String videos = "";
				String images = "";
				ArrayList fileList = new ArrayList();
				ArrayList imgeList = new ArrayList();

				ArrayList videoList = new ArrayList();
				ArrayList imageList = new ArrayList();
				if (count >= 1) {
					String getArchievData = "select * from archieves where link_name='"
							+ subsublink
							+ "' and module='"
							+ linkName
							+ "' and sub_link='"
							+ sublink
							+ "' and  year='"
							+ year + "'  and month='" + month + "'";
					System.out.println("getArchievData=" + getArchievData);
					ResultSet rsArchiveData = adlinks
							.selectQuery(getArchievData);
					String file;
					while (rsArchiveData.next()) {
						linksForm.setArchiveStatus(rsArchiveData
								.getString("status"));
						linksForm.setContentDescriptionAdmin(rsArchiveData
								.getString("content_description"));
						arcStatus = rsArchiveData.getString("status");
						if (arcStatus.equalsIgnoreCase("yes")) {
							linksForm.setArchiveStatus(rsArchiveData
									.getString("status"));
						}

						files = rsArchiveData.getString("file_name");
						if (files.equalsIgnoreCase("")) {

						} else {
							String v[] = files.split(",");
							int l = v.length;
							for (int i = 0; i < l; i++) {
								ManageCMSForm linksForm1 = new ManageCMSForm();
								int x = v[i].lastIndexOf("/");
								files = v[i].substring(x + 1);
								linksForm1.setFileList(files);

								/*
								 * String insertFiles =
								 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
								 * +linkName+"','','"+files+"','','')";
								 * System.out
								 * .println("insertFiles="+insertFiles);
								 * adlinks.SqlExecuteUpdate(insertFiles);
								 */

								fileList.add(linksForm1);
							}
						}
						images = rsArchiveData.getString("image_name");
						if (images.equalsIgnoreCase("")) {

						} else {
							String v[] = images.split(",");
							int l = v.length;
							for (int i = 0; i < l; i++) {
								ManageCMSForm linksForm1 = new ManageCMSForm();
								int x = v[i].lastIndexOf("/");
								files = v[i].substring(x + 1);
								linksForm1.setImageList(files);

								/*
								 * String insertFiles =
								 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
								 * +linkName+"','','"+files+"','','')";
								 * System.out
								 * .println("insertFiles="+insertFiles);
								 * adlinks.SqlExecuteUpdate(insertFiles);
								 */

								imgeList.add(linksForm1);
							}
						}

						videos = rsArchiveData.getString("video_name");
						if (videos.equalsIgnoreCase("")) {

						} else {
							String v1[] = videos.split(",");
							int l1 = v1.length;
							for (int i = 0; i < l1; i++) {
								ManageCMSForm linksFormVideo = new ManageCMSForm();
								int y = v1[i].lastIndexOf("/");
								videos = v1[i].substring(y + 1);
								linksFormVideo.setFileList(videos);
								/*
								 * String insertVideos =
								 * "insert into video_list(link_name,video_path,video_name,sub_link_name,sub_sub_linkname) values('"
								 * +linkName+"','','"+videos+"','','')";
								 * System.out
								 * .println("insertFiles="+insertVideos);
								 * adlinks.SqlExecuteUpdate(insertVideos);
								 */
								videoList.add(linksFormVideo);
							}
						}
					}
				}
				if (files.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("listName", fileList);

				}
				if (images.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("imageLists", imgeList);

				}
				if (videos.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("videosList", videoList);
				}
				request.setAttribute("displaySublinkField",
						"displaySublinkField");
			}

			LinksDao ad = new LinksDao();
			if (linkName.equalsIgnoreCase("Help")) {

				String sql = "select * from links where link_name='" + linkName
						+ "' and " + "status is null  ";
				System.out.println("sql=" + sql);
				ResultSet rs = ad.selectQuery(sql);
				String linkId = null;
				while (rs.next()) {
					linkId = rs.getString("id");
					linksForm.setSubLinkName(rs.getString("link_name"));
					linksForm.setSubLinkId(linksId);
					linksForm.setLinkId(rs.getString("id"));
					linksForm.setLinkPath(rs.getString("link_path"));
					linksForm.setMethodName(rs.getString("method"));
					linksForm.setPriority(rs.getString("priority"));
					// linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
				}
				request.setAttribute("linkId", linkId);

				return mapping.findForward("displayMainLinks");
			} else {

				String sql = "select * from links where link_name='" + linkName
						+ "' and " + "status is null and module='Main' ";
				System.out.println("sql=" + sql);
				ResultSet rs = ad.selectQuery(sql);
				String linkId = null;
				while (rs.next()) {
					linkId = rs.getString("id");
					linksForm.setSubLinkName(rs.getString("link_name"));
					linksForm.setSubLinkId(linksId);
					linksForm.setLinkId(rs.getString("id"));
					linksForm.setLinkPath(rs.getString("link_path"));
					linksForm.setMethodName(rs.getString("method"));
					linksForm.setPriority(rs.getString("priority"));
					// linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
				}
				request.setAttribute("linkId", linkId);

			}
			linksForm.setLinkName(linkName);
			linksForm.setSubLinkName(sublink);
			linksForm.setSubSubLinkName(subsublink);
			displayMainLinks(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			displaySubSublinks(mapping, form, request, response);

			// (mapping, form, request, response);

			// get sub link archive data

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("displayMainLinks");
	}

	public ActionForward displaySubLinksCMS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("displaySubLinksCMS()----");
		ManageCMSForm linksForm = (ManageCMSForm) form;// TODO Auto-generated
														// method stub
		try {
			String module = request.getParameter("MenuIcon2");
			request.setAttribute("MenuIcon", module);

			String linksId = request.getParameter("sId");
			String linkName = request.getParameter("LinkName");

			System.out.println("Link Name=" + linkName);
			linksForm.setLinkName(linkName);

			try {
				LinksDao ad = new LinksDao();
				String sql = "select * from links where module='" + linkName
						+ "'  and sub_linkname is null and  delete_status=1";
				ResultSet rs = ad
						.selectQuery("select * from links where module='"
								+ linkName + "' and  delete_status=1 ");
				ArrayList subLinkValueList = new ArrayList();
				while (rs.next()) {
					subLinkValueList.add(rs.getString("link_name"));
				}
				linksForm.setSublinkValueList(subLinkValueList);
				request.setAttribute("subLinkValueListID", subLinkValueList);
				request.setAttribute("submitButton", "submitButton");
			} catch (Exception e) {
				e.printStackTrace();
			}
			String sublink = request.getParameter("SubLink");

			String subsublink = linksForm.getSubSubLinkName();

			linksForm.setLinkName(linkName);
			linksForm.setSubLinkName(sublink);
			linksForm.setSubSubLinkName(subsublink);
			LinksDao adlinks = new LinksDao();
			int count = 0;
			String arcStatus = "";
			linksForm.setArchiveStatus("");
			linksForm.setContentDescriptionAdmin("");
			String year = linksForm.getYear();
			String month = linksForm.getMonth();
			if (year != null) {

				linkName = request.getParameter("LinkName");
				// links content display code

				String getArchievDataCount = "select count(*) from archieves where link_name='"
						+ sublink
						+ "' and module='"
						+ linkName
						+ "'  and  year='"
						+ year
						+ "' and month='"
						+ month
						+ "'";
				ResultSet rsArchievDataCount = adlinks
						.selectQuery(getArchievDataCount);
				while (rsArchievDataCount.next()) {
					count = rsArchievDataCount.getInt(1);
				}
				String files = "";
				String videos = "";
				String images = "";
				ArrayList fileList = new ArrayList();
				ArrayList imgeList = new ArrayList();

				ArrayList videoList = new ArrayList();
				ArrayList imageList = new ArrayList();
				if (count >= 1) {
					String getArchievData = "select * from archieves where link_name='"
							+ sublink
							+ "' and module='"
							+ linkName
							+ "' and  year='"
							+ year
							+ "' and month='"
							+ month
							+ "' ";
					System.out.println("getArchievData=" + getArchievData);
					ResultSet rsArchiveData = adlinks
							.selectQuery(getArchievData);
					String file;
					while (rsArchiveData.next()) {
						linksForm.setArchiveStatus(rsArchiveData
								.getString("status"));
						linksForm.setContentDescriptionAdmin(rsArchiveData
								.getString("content_description"));
						arcStatus = rsArchiveData.getString("status");
						if (arcStatus.equalsIgnoreCase("yes")) {
							linksForm.setArchiveStatus(rsArchiveData
									.getString("status"));
						}

						files = rsArchiveData.getString("file_name");
						if (files.equalsIgnoreCase("")) {

						} else {
							String v[] = files.split(",");
							int l = v.length;
							for (int i = 0; i < l; i++) {
								ManageCMSForm linksForm1 = new ManageCMSForm();
								int x = v[i].lastIndexOf("/");
								files = v[i].substring(x + 1);
								linksForm1.setFileList(files);

								/*
								 * String insertFiles =
								 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
								 * +linkName+"','','"+files+"','','')";
								 * System.out
								 * .println("insertFiles="+insertFiles);
								 * adlinks.SqlExecuteUpdate(insertFiles);
								 */

								fileList.add(linksForm1);
							}
						}
						images = rsArchiveData.getString("image_name");
						if (images == null || images.equalsIgnoreCase("")) {

						} else {
							String v[] = images.split(",");
							int l = v.length;
							for (int i = 0; i < l; i++) {
								ManageCMSForm linksForm1 = new ManageCMSForm();
								int x = v[i].lastIndexOf("/");
								files = v[i].substring(x + 1);
								linksForm1.setImageList(files);

								/*
								 * String insertFiles =
								 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
								 * +linkName+"','','"+files+"','','')";
								 * System.out
								 * .println("insertFiles="+insertFiles);
								 * adlinks.SqlExecuteUpdate(insertFiles);
								 */

								imgeList.add(linksForm1);
							}
						}

						videos = rsArchiveData.getString("video_name");
						if (videos.equalsIgnoreCase("")) {

						} else {
							String v1[] = videos.split(",");
							int l1 = v1.length;
							for (int i = 0; i < l1; i++) {
								ManageCMSForm linksFormVideo = new ManageCMSForm();
								int y = v1[i].lastIndexOf("/");
								videos = v1[i].substring(y + 1);
								linksFormVideo.setFileList(videos);
								/*
								 * String insertVideos =
								 * "insert into video_list(link_name,video_path,video_name,sub_link_name,sub_sub_linkname) values('"
								 * +linkName+"','','"+videos+"','','')";
								 * System.out
								 * .println("insertFiles="+insertVideos);
								 * adlinks.SqlExecuteUpdate(insertVideos);
								 */
								videoList.add(linksFormVideo);
							}
						}
					}
				}
				if (files.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("listName", fileList);

				}
				if (images == null || images.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("imageLists", imgeList);

				}
				if (videos.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("videosList", videoList);
				}
				request.setAttribute("displaySublinkField",
						"displaySublinkField");
			}

			LinksDao ad = new LinksDao();
			if (linkName.equalsIgnoreCase("Help")) {

				String sql = "select * from links where link_name='" + linkName
						+ "' and " + "status is null  ";
				System.out.println("sql=" + sql);
				ResultSet rs = ad.selectQuery(sql);
				String linkId = null;
				while (rs.next()) {
					linkId = rs.getString("id");
					linksForm.setSubLinkName(rs.getString("link_name"));
					linksForm.setSubLinkId(linksId);
					linksForm.setLinkId(rs.getString("id"));
					linksForm.setLinkPath(rs.getString("link_path"));
					linksForm.setMethodName(rs.getString("method"));
					linksForm.setPriority(rs.getString("priority"));
					// linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
				}
				request.setAttribute("linkId", linkId);

				return mapping.findForward("displayMainLinks");
			} else {

				String sql = "select * from links where link_name='" + linkName
						+ "' and " + "status is null and module='Main' ";
				System.out.println("sql=" + sql);
				ResultSet rs = ad.selectQuery(sql);
				String linkId = null;
				while (rs.next()) {
					linkId = rs.getString("id");
					linksForm.setSubLinkName(rs.getString("link_name"));
					linksForm.setSubLinkId(linksId);
					linksForm.setLinkId(rs.getString("id"));
					linksForm.setLinkPath(rs.getString("link_path"));
					linksForm.setMethodName(rs.getString("method"));
					linksForm.setPriority(rs.getString("priority"));
					// linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
				}
				request.setAttribute("linkId", linkId);

			}
			linksForm.setLinkName(linkName);
			linksForm.setSubLinkName(sublink);
			displayMainLinks(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			displaySubSublinks(mapping, form, request, response);

			// (mapping, form, request, response);

			// get sub link archive data

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("displayMainLinks");
	}

	public ActionForward deleteImage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ManageCMSForm linksForm = (ManageCMSForm) form;
		LinksDao ad = new LinksDao();
		String linkName = linksForm.getLinkName();
		System.out.println("Link Name=" + linkName);
		String sublink = linksForm.getSubLinkName();
		String subsublink = linksForm.getSubSubLinkName();
		String year = linksForm.getYear();
		String month = linksForm.getMonth();
		try {

			if (sublink.equalsIgnoreCase("") && subsublink.equalsIgnoreCase("")) {
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);
				String filelist[] = request.getParameterValues("checkedImages");
				String files = "";

				if (filelist.length == 0) {
					files = "";
				} else {

					for (int i = 0; i < filelist.length; i++) {
						String deleteFiels = "delete from Imagelist where link_name='"
								+ linksForm.getLinkName()
								+ "' and file_name='"
								+ filelist[i]
								+ "' and year='"
								+ year
								+ "' and month='" + month + "'";
						int a = 0;
						a = ad.SqlExecuteUpdate(deleteFiels);
						if (a > 0) {
							linksForm
									.setMessage("Documents Deleted Successfully");
							String filePath = getServlet()
									.getServletContext()
									.getRealPath(
											"jsp/EMicro Files/News And Media/Archieves")
									+ "/"
									+ linksForm.getLinkName()
									+ "/Upload Images"
									+ "/"
									+ linksForm.getYear();

							InputStream in = ConnectionFactory.class
									.getClassLoader().getResourceAsStream(
											"db.properties");
							Properties props = new Properties();
							props.load(in);
							in.close();
							String uploadFilePath = props
									.getProperty("file.uploadFilePath");
							filePath = uploadFilePath
									+ "/EMicro Files/News And Media/Archieves/"
									+ linksForm.getLinkName()
									+ "/Upload Images" + "/"
									+ linksForm.getYear();

							File fileToCreate1 = new File(filePath, filelist[i]);
							boolean check = false;
							check = fileToCreate1.delete();
							System.out.println("Image Delete Status=" + check);

						} else {
							linksForm
									.setMessage("Error While Deleted Files ... Please check Entered Values");
						}
					}
				}

				// String
				// videoList[]=request.getParameterValues("checkedVideofiles");
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ linkName
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ year + "' and month='" + month + "' ";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);

				ArrayList videoList = new ArrayList();
				int videocount = 0;
				String getVideoscount = "select count(*) from video_list where link_name='"
						+ linkName
						+ "' and year='"
						+ year
						+ "' and month='"
						+ month + "'";
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					String getVideos = "select * from video_list where link_name='"
							+ linkName
							+ "' and year='"
							+ year
							+ "' and month='" + month + "'";
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}

				request.setAttribute("displaySublinkField",
						"displaySublinkField");

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and  sub_link_name='' and sub_sub_linkname=''  and year='"
						+ year + "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
							+ year + "' and month='" + month + "'";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
			}
			// get sub link uploaded data
			if (!sublink.equalsIgnoreCase("")
					&& subsublink.equalsIgnoreCase("")) {
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);
				String filelist[] = request.getParameterValues("checkedImages");
				String files = "";
				if (filelist.length == 0) {
					files = "";
				} else {

					for (int i = 0; i < filelist.length; i++) {

						String deleteFiels = "delete from Imagelist where link_name='"
								+ linkName
								+ "' and sub_link_name='"
								+ sublink
								+ "'  and file_name='"
								+ filelist[i]
								+ "' and year='"
								+ year
								+ "' and month='"
								+ month + "'";
						int a = 0;
						a = ad.SqlExecuteUpdate(deleteFiels);
						if (a > 0) {
							linksForm
									.setMessage("Documents Deleted Successfully");
							String filePath = getServlet()
									.getServletContext()
									.getRealPath(
											"jsp/EMicro Files/News And Media/Archieves")
									+ "/"
									+ linkName
									+ "/"
									+ sublink
									+ "/Upload Images"
									+ "/"
									+ linksForm.getYear();

							InputStream in = ConnectionFactory.class
									.getClassLoader().getResourceAsStream(
											"db.properties");
							Properties props = new Properties();
							props.load(in);
							in.close();
							String uploadFilePath = props
									.getProperty("file.uploadFilePath");
							filePath = uploadFilePath
									+ "/EMicro Files/News And Media/Archieves/"
									+ linkName + "/" + sublink
									+ "/Upload Images" + "/"
									+ linksForm.getYear();

							File fileToCreate = new File(filePath, filelist[i]);
							boolean check = false;
							check = fileToCreate.delete();

						} else {
							linksForm
									.setMessage("Error While Deleted Files ... Please check Entered Values");
						}
					}
				}

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and  sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname=''  and year='"
						+ year
						+ "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='' and year='"
							+ year
							+ "' and month='" + month + "' ";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}

				// String
				// videoList[]=request.getParameterValues("checkedVideofiles");
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ linkName + "' and sub_link_name='" + sublink
						+ "' and year='" + year + "' and month='" + month
						+ "' and sub_sub_linkname=''";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);

				ArrayList videoList = new ArrayList();
				int videocount = 0;
				String getVideoscount = "select count(*) from video_list where link_name='"
						+ linkName
						+ "'  and sub_link_name='"
						+ sublink
						+ "' and year='"
						+ year
						+ "' and month='"
						+ month
						+ "' and sub_sub_linkname=''";
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					String getVideos = "select * from video_list where link_name='"
							+ linkName
							+ "'  and sub_link_name='"
							+ sublink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'  and sub_sub_linkname=''";
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);
				request.setAttribute("displayFields", "displayFields");
			}
			// get sub sub link uploaded data
			if (!linkName.equalsIgnoreCase("") && !sublink.equalsIgnoreCase("")
					&& !subsublink.equalsIgnoreCase("")) {
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);
				String filelist[] = request.getParameterValues("checkedImages");
				String files = "";
				if (filelist.length == 0) {
					files = "";
				} else {

					for (int i = 0; i < filelist.length; i++) {

						String deleteFiels = "delete from Imagelist where link_name='"
								+ linkName
								+ "' and sub_link_name='"
								+ sublink
								+ "'"
								+ " and sub_sub_linkname='"
								+ linksForm.getSubSubLinkName()
								+ "' and file_name='"
								+ filelist[i]
								+ "' and year='"
								+ year
								+ "' and month='"
								+ month + "'";
						int a = 0;
						a = ad.SqlExecuteUpdate(deleteFiels);
						if (a > 0) {
							linksForm
									.setMessage("Documents Deleted Successfully");

							String filePath = getServlet()
									.getServletContext()
									.getRealPath(
											"jsp/EMicro Files/News And Media/Archieves")
									+ "/"
									+ linkName
									+ "/"
									+ sublink
									+ "/"
									+ linksForm.getSubSubLinkName()
									+ "/Upload Images"
									+ "/"
									+ linksForm.getYear();

							InputStream in = ConnectionFactory.class
									.getClassLoader().getResourceAsStream(
											"db.properties");
							Properties props = new Properties();
							props.load(in);
							in.close();
							String uploadFilePath = props
									.getProperty("file.uploadFilePath");
							filePath = uploadFilePath
									+ "/EMicro Files/News And Media/Archieves/"
									+ linkName + "/" + sublink + "/" + sublink
									+ "/Upload Images" + "/"
									+ linksForm.getYear();

							File fileToCreate = new File(filePath, filelist[i]);
							boolean check = false;
							check = fileToCreate.delete();

						} else {
							linksForm
									.setMessage("Error While Deleted Files ... Please check Entered Values");
						}
					}
				}

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and  sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='"
						+ linksForm.getSubSubLinkName()
						+ "'  and year='"
						+ year + "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='"
							+ linksForm.getSubSubLinkName()
							+ "' and year='"
							+ year + "' and month='" + month + "'";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}
				// String
				// videoList[]=request.getParameterValues("checkedVideofiles");
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ linkName + "' and sub_link_name='" + sublink + "'"
						+ " and sub_sub_linkname='" + subsublink
						+ "' and year='" + year + "'  and month='" + month
						+ "'";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);
				ArrayList videoList = new ArrayList();
				int videocount = 0;
				String getVideoscount = "select count(*) from video_list where link_name='"
						+ linkName
						+ "'  and sub_link_name='"
						+ sublink
						+ "' and "
						+ " sub_sub_linkname='"
						+ subsublink
						+ "' and year='" + year + "' and month='" + month + "'";
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					String getVideos = "select * from video_list where link_name='"
							+ linkName
							+ "'  and sub_link_name='"
							+ sublink
							+ "'"
							+ " and sub_sub_linkname='"
							+ subsublink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "' ";
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);

			}

			int imageCount = 0;
			String getImageCount = "select count(*) from Imagelist where link_name='"
					+ linkName
					+ "' and  sub_link_name='"
					+ sublink
					+ "' and sub_sub_linkname='"
					+ subsublink
					+ "'  and year='"
					+ year + "' and month='" + month + "'";
			ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
			while (rsgetImagecount.next()) {
				imageCount = rsgetImagecount.getInt(1);
			}

			if (imageCount > 0) {
				ArrayList imageList = new ArrayList();
				String getImage = "select * from Imagelist where link_name='"
						+ linkName + "' and sub_link_name='" + sublink
						+ "' and sub_sub_linkname='" + subsublink
						+ "' and year='" + year + "' and month='" + month + "'";

				ResultSet rs13 = ad.selectQuery(getImage);
				while (rs13.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setImageList(rs13.getString("file_name"));
					imageList.add(linksForm);
				}
				request.setAttribute("imageLists", imageList);
			}
			request.setAttribute("displaySublinkField", "displaySublinkField");
			request.setAttribute("displayFields", "displayFields");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("displayMainLinks");

	}

	public ActionForward uploadImage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			ManageCMSForm linksForm = (ManageCMSForm) form;
			LinksDao ad = new LinksDao();
			String linkName = linksForm.getLinkName();
			System.out.println("Link Name=" + linkName);
			String sublink = linksForm.getSubLinkName();
			String subsublink = linksForm.getSubSubLinkName();
			if (sublink.equalsIgnoreCase("") && subsublink.equalsIgnoreCase("")) {
				String link_id = linksForm.getLinkName();
				linksForm.setLinkName(link_id);
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);

				FormFile myFile = linksForm.getImageNames();
				String contentType = myFile.getContentType();

				String fileName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

				if (ext.equalsIgnoreCase("bmp") || ext.equalsIgnoreCase("jpg")
						|| ext.equalsIgnoreCase("gif")
						|| ext.equalsIgnoreCase("png")) {
					int checkImageCount = 0;
					String checkImageExist = "select count(*) from Imagelist where file_name='"
							+ fileName + "'";
					ResultSet rsImageCount = ad.selectQuery(checkImageExist);
					while (rsImageCount.next()) {
						checkImageCount = rsImageCount.getInt(1);
					}
					if (checkImageCount == 0) {
						String filePath = getServlet()
								.getServletContext()
								.getRealPath(
										"jsp/EMicro Files/News And Media/Archieves")
								+ "/"
								+ link_id
								+ "/Upload Images"
								+ "/"
								+ linksForm.getYear();

						InputStream in = ConnectionFactory.class
								.getClassLoader().getResourceAsStream(
										"db.properties");
						Properties props = new Properties();
						props.load(in);
						in.close();
						String uploadFilePath = props
								.getProperty("file.uploadFilePath");
						filePath = uploadFilePath
								+ "/EMicro Files/News And Media/Archieves/"
								+ link_id + "/Upload Images" + "/"
								+ linksForm.getYear();

						File destinationDir = new File(filePath);
						if (!destinationDir.exists()) {
							destinationDir.mkdirs();
						}

						File fileToCreate = new File(filePath, fileName);
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);

						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();

						filePath = filePath.replace("\\", "\\");
						System.out.println("filePath=" + filePath);
						String insertsql = "insert into Imagelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname,year,month) values('"
								+ link_id
								+ "','"
								+ filePath
								+ "','"
								+ fileName
								+ "','','','"
								+ linksForm.getYear()
								+ "','"
								+ linksForm.getMonth() + "')";
						System.out.println("insertsql=" + insertsql);
						int a = ad.SqlExecuteUpdate(insertsql);
						if (a > 0) {
							linksForm.setMessage("Image Uploaded Successfully");
						} else {
							linksForm
									.setMessage("Error While Uploading Image ... Please check. ");
						}
					} else {
						linksForm
								.setMessage("Image name already exist.Please change image name");

					}
				} else {
					linksForm
							.setMessage("Please upload only bmp,jpg,gif,png images ");
				}

				String year = linksForm.getYear();
				String month = linksForm.getMonth();
				String getFileList = "select count(*) from filelist where link_name='"
						+ link_id
						+ "' and  sub_link_name='' and sub_sub_linkname=''  and year='"
						+ year + "' and month='" + month + "'";
				String sql3 = "select * from filelist where link_name='"
						+ link_id
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ year + "' and month='" + month + "'";
				String getVideoscount = "select count(*) from video_list where link_name='"
						+ link_id
						+ "' and  sub_link_name='' and sub_sub_linkname=''  and year='"
						+ year + "' and month='" + month + "'";
				String getVideos = "select * from video_list where link_name='"
						+ link_id
						+ "' and  sub_link_name='' and sub_sub_linkname='' and year='"
						+ year + "' and month='" + month + "' ";

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ link_id
						+ "' and  sub_link_name='' and sub_sub_linkname=''  and year='"
						+ year + "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ link_id
							+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
							+ year + "' and month='" + month + "'";

					ResultSet rs12 = ad.selectQuery(getImage);
					while (rs12.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs12.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}

				int fileCount = 0;
				ResultSet rsgetFilescount = ad.selectQuery(getFileList);
				while (rsgetFilescount.next()) {
					fileCount = rsgetFilescount.getInt(1);
				}

				ArrayList list = new ArrayList();
				if (fileCount > 0) {
					ResultSet rs12 = ad.selectQuery(sql3);
					while (rs12.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setFileList(rs12.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
				}

				ArrayList videoList = new ArrayList();
				int videocount = 0;
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}

				request.setAttribute("displaySublinkField",
						"displaySublinkField");

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				request.setAttribute("displayFields", "displayFields");
			}
			// get sub link uploaded data
			if (!sublink.equalsIgnoreCase("")
					&& subsublink.equalsIgnoreCase("")) {
				String link_id = linksForm.getLinkName();
				linksForm.setLinkName(link_id);
				String content_description = "";
				String year = linksForm.getYear();
				String month = linksForm.getMonth();
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);

				FormFile myFile = linksForm.getImageNames();
				String contentType = myFile.getContentType();
				String fileName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

				int checkImageCount = 0;
				String checkImageExist = "select count(*) from Imagelist where file_name='"
						+ fileName + "'";
				ResultSet rsImageCount = ad.selectQuery(checkImageExist);
				while (rsImageCount.next()) {
					checkImageCount = rsImageCount.getInt(1);
				}
				if (checkImageCount == 0) {

					if (ext.equalsIgnoreCase("bmp")
							|| ext.equalsIgnoreCase("jpg")
							|| ext.equalsIgnoreCase("gif")
							|| ext.equalsIgnoreCase("png")) {
						String filePath = getServlet()
								.getServletContext()
								.getRealPath(
										"jsp/EMicro Files/News And Media/Archieves")
								+ "/"
								+ link_id
								+ "/"
								+ sublink
								+ "/Upload Images" + "/" + linksForm.getYear();

						InputStream in = ConnectionFactory.class
								.getClassLoader().getResourceAsStream(
										"db.properties");
						Properties props = new Properties();
						props.load(in);
						in.close();
						String uploadFilePath = props
								.getProperty("file.uploadFilePath");
						filePath = uploadFilePath
								+ "/EMicro Files/News And Media/Archieves/"
								+ link_id + "/" + sublink + "/Upload Images"
								+ "/" + linksForm.getYear();

						File destinationDir = new File(filePath);
						if (!destinationDir.exists()) {
							destinationDir.mkdirs();
						}

						File fileToCreate = new File(filePath, fileName);

						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);

						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();

						filePath = filePath.replace("\\", "\\");
						System.out.println("filePath=" + filePath);
						String insertsql = "insert into Imagelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname,year,month) values('"
								+ link_id
								+ "','"
								+ filePath
								+ "','"
								+ fileName
								+ "','"
								+ sublink
								+ "','','"
								+ linksForm.getYear()
								+ "','"
								+ linksForm.getMonth() + "')";
						System.out.println("insertsql=" + insertsql);
						int a = ad.SqlExecuteUpdate(insertsql);
						if (a > 0) {
							linksForm
									.setMessage("Images Uploaded Successfully");
						} else {
							linksForm
									.setMessage("Error While Uploading Images ... Please check Entered Values");
						}
					} else {
						linksForm
								.setMessage("Error While Uploading Images ... Please check Entered Values");
					}
				} else {
					linksForm
							.setMessage("Image name already exist.Please change image name");
				}

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ link_id
						+ "' and  sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname=''  and year='"
						+ year
						+ "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ link_id
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='' and year='"
							+ year
							+ "' and month='" + month + "'";

					ResultSet rs12 = ad.selectQuery(getImage);
					while (rs12.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs12.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ link_id + "' and sub_link_name='" + sublink
						+ "' and year='" + year + "' and month='" + month
						+ "'  and sub_sub_linkname=''";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);

				ArrayList videoList = new ArrayList();
				int videocount = 0;
				String getVideoscount = "select count(*) from video_list where  link_name='"
						+ link_id
						+ "' and sub_link_name='"
						+ sublink
						+ "' and year='"
						+ year
						+ "' and month='"
						+ month
						+ "' and sub_sub_linkname=''";
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					String getVideos = "select * from video_list where link_name='"
							+ link_id
							+ "'  and sub_link_name='"
							+ sublink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);
				request.setAttribute("displayFields", "displayFields");
			}
			// get sub sub link uploaded data
			if (!linkName.equalsIgnoreCase("") && !sublink.equalsIgnoreCase("")
					&& !subsublink.equalsIgnoreCase("")) {
				String year = linksForm.getYear();
				String month = linksForm.getMonth();
				String link_id = linksForm.getLinkName();
				String subSubLink = linksForm.getSubSubLinkName();
				linksForm.setLinkName(link_id);
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);

				FormFile myFile = linksForm.getImageNames();
				String contentType = myFile.getContentType();
				String fileName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

				int checkImageCount = 0;
				String checkImageExist = "select count(*) from Imagelist where file_name='"
						+ fileName + "'";
				ResultSet rsImageCount = ad.selectQuery(checkImageExist);
				while (rsImageCount.next()) {
					checkImageCount = rsImageCount.getInt(1);
				}

				if (ext.equalsIgnoreCase("bmp") || ext.equalsIgnoreCase("jpg")
						|| ext.equalsIgnoreCase("gif")
						|| ext.equalsIgnoreCase("png")) {
					String filePath = getServlet()
							.getServletContext()
							.getRealPath(
									"jsp/EMicro Files/News And Media/Archieves")
							+ "/"
							+ link_id
							+ "/"
							+ sublink
							+ "/"
							+ subSubLink
							+ "/Upload Images" + "/" + linksForm.getYear();

					InputStream in = ConnectionFactory.class.getClassLoader()
							.getResourceAsStream("db.properties");
					Properties props = new Properties();
					props.load(in);
					in.close();
					String uploadFilePath = props
							.getProperty("file.uploadFilePath");
					filePath = uploadFilePath
							+ "/EMicro Files/News And Media/Archieves/"
							+ link_id + "/" + sublink + "/" + subSubLink
							+ "/Upload Images" + "/" + linksForm.getYear();

					File destinationDir = new File(filePath);
					if (!destinationDir.exists()) {
						destinationDir.mkdirs();
					}

					if (checkImageCount == 0) {

						File fileToCreate = new File(filePath, fileName);
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);

						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();

						filePath = filePath.replace("\\", "\\");
						System.out.println("filePath=" + filePath);
						String insertsql = "insert into Imagelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname,year,month) values('"
								+ link_id
								+ "','"
								+ filePath
								+ "','"
								+ fileName
								+ "','"
								+ sublink
								+ "','"
								+ subSubLink
								+ "','"
								+ year + "','" + month + "')";
						System.out.println("insertsql=" + insertsql);
						int a = ad.SqlExecuteUpdate(insertsql);
						if (a > 0) {
							linksForm.setMessage("Image Uploaded Successfully");
						} else {
							linksForm
									.setMessage("Error While Image Files ... Please check Entered Values");
						}
					} else {
						linksForm
								.setMessage("Image name alredy exist.Please Change Image name");
					}
				} else {
					linksForm.setMessage("Image Uploaded Successfully");
				}
				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ link_id
						+ "' and  sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='"
						+ subSubLink
						+ "'  and year='"
						+ year
						+ "' and month='"
						+ month
						+ "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ link_id
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='"
							+ subSubLink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";

					ResultSet rs12 = ad.selectQuery(getImage);
					while (rs12.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs12.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ link_id + "' and sub_link_name='" + sublink
						+ "' and sub_sub_linkname='" + subSubLink
						+ "' and year='" + year + "' and month='" + month
						+ "' ";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);

				ArrayList videoList = new ArrayList();
				int videocount = 0;
				String getVideoscount = "select count(*) from video_list where  link_name='"
						+ link_id
						+ "' and sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='"
						+ subSubLink
						+ "' and year='"
						+ year
						+ "' and month='"
						+ month
						+ "' ";
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					String getVideos = "select * from video_list where link_name='"
							+ link_id
							+ "'  and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='"
							+ subSubLink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);

			}
			request.setAttribute("displaySublinkField", "displaySublinkField");
			request.setAttribute("displayFields", "displayFields");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayMainLinks");
	}

	public ActionForward saveArchiveData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ManageCMSForm linksForm = (ManageCMSForm) form;
		LinksDao ad = new LinksDao();
		String linkName = linksForm.getLinkName();
		System.out.println("Link Name=" + linkName);
		String sublink = linksForm.getSubLinkName();
		String subsublink = linksForm.getSubSubLinkName();
		String year = linksForm.getYear();
		String month = linksForm.getMonth();
		String content = request.getParameter("htmldata");
		linksForm.setContentDescriptionAdmin(content);

		int ii = 0;
		try {

			// save link archive data
			if (sublink.equalsIgnoreCase("") && subsublink.equalsIgnoreCase("")) {
				String content_description = "";
				String archieveStatus = linksForm.getArchiveStatus();
				String fileNameList = "";
				int filesCount = 0;
				String getFilesCount = "select count(*) from filelist where link_name='"
						+ linkName
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ linksForm.getYear() + "' and month='" + month + "'";
				ResultSet rsFilesCount = ad.selectQuery(getFilesCount);
				while (rsFilesCount.next()) {
					filesCount = rsFilesCount.getInt(1);
				}
				if (filesCount > 0) {

					String getFiles = "select * from filelist where link_name='"
							+ linkName
							+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
							+ linksForm.getYear()
							+ "' and month='"
							+ month
							+ "'";
					ResultSet rsFiles = ad.selectQuery(getFiles);

					while (rsFiles.next()) {

						// cms\HR\UploadVideos\2012
						fileNameList += "/EMicro Files/News And Media/Archieves"
								+ "/"
								+ linkName
								+ "/UploadFiles"
								+ "/"
								+ linksForm.getYear()
								+ "/"
								+ rsFiles.getString("file_name") + ",";
					}
					fileNameList = fileNameList.substring(0,
							(fileNameList.length() - 1));
				}
				System.out.println("fileNameList=" + fileNameList);

				String imageList = "";
				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ linksForm.getYear() + "' and month='" + month + "'";
				ResultSet rsImageCount = ad.selectQuery(getImageCount);
				while (rsImageCount.next()) {
					imageCount = rsImageCount.getInt(1);
				}
				if (imageCount > 0) {

					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
							+ linksForm.getYear()
							+ "' and month='"
							+ month
							+ "'";
					ResultSet rsImages = ad.selectQuery(getImage);

					while (rsImages.next()) {

						// cms\HR\UploadVideos\2012
						imageList += "/EMicro Files/News And Media/Archieves"
								+ "/" + linkName + "/Upload Images" + "/"
								+ linksForm.getYear() + "/"
								+ rsImages.getString("file_name") + ",";
					}
					imageList = imageList
							.substring(0, (imageList.length() - 1));
				}
				System.out.println("imageList=" + imageList);
				String videoNameList = "";
				int videoCount = 0;
				String getvideoCount = "select count(*) from video_list where link_name='"
						+ linkName
						+ "' and year='"
						+ linksForm.getYear()
						+ "' and month='" + month + "'";
				ResultSet rsvideoCount = ad.selectQuery(getvideoCount);
				while (rsvideoCount.next()) {
					videoCount = rsvideoCount.getInt(1);
				}
				if (videoCount > 0) {
					String getVideos = "select * from video_list where link_name='"
							+ linkName
							+ "' and year='"
							+ linksForm.getYear()
							+ "' and month='" + month + "'";
					ResultSet rsVideos = ad.selectQuery(getVideos);

					while (rsVideos.next()) {
						videoNameList += "/EMicro Files/News And Media/Archieves"
								+ "/"
								+ linkName
								+ "/UploadVideos"
								+ "/"
								+ linksForm.getYear()
								+ "/"
								+ rsVideos.getString("video_name") + ",";

					}
					videoNameList = videoNameList.substring(0,
							(videoNameList.length() - 1));
				}

				System.out.println("videoNameList=" + videoNameList);

				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);

				content = Jsoup.parse(content_description).text();
				int size = 0;
				if (linkName.equalsIgnoreCase("Login CMS")) {
					size = content.length();
					System.out.println("size=" + size);

				}

				content_description = content_description.replaceAll("'", "`");
				linksForm.setContentDescriptionAdmin(content_description);
				String getArchiveCount = "select count(*) from archieves where link_name='"
						+ linkName
						+ "'  and year='"
						+ linksForm.getYear()
						+ "' and month='" + month + "'";
				int archiveDataCount = 0;
				ResultSet rsArchiveCount = ad.selectQuery(getArchiveCount);
				while (rsArchiveCount.next()) {
					archiveDataCount = rsArchiveCount.getInt(1);
				}

				if (archiveDataCount >= 1) {
					System.out.println("update data");
					if (!linkName.equalsIgnoreCase("Login CMS")) {
						String updateArchiveData = "update archieves set content_description='"
								+ content_description
								+ "',status='"
								+ linksForm.getArchiveStatus()
								+ "', file_name='"
								+ fileNameList
								+ "',video_name='"
								+ videoNameList
								+ "',image_name='"
								+ imageList
								+ "' where link_name='"
								+ linkName
								+ "' and module='Main'  and year='"
								+ linksForm.getYear()
								+ "' and month='"
								+ month
								+ "'";

						ii = ad.SqlExecuteUpdate(updateArchiveData);
						if (ii > 0) {
							linksForm
									.setMessage2(" CMS Content has been saved successfully");
						} else {
							linksForm
									.setMessage("Error...CMS Content has been not saved... ");
						}
					} else {
						if (true) {
							String updateArchiveData = "update archieves set content_description='"
									+ content_description
									+ "',status='"
									+ linksForm.getArchiveStatus()
									+ "', file_name='"
									+ fileNameList
									+ "',video_name='"
									+ videoNameList
									+ "',image_name='"
									+ imageList
									+ "'  where link_name='"
									+ linkName
									+ "' and module='Main'  and year='"
									+ linksForm.getYear()
									+ "' and month='"
									+ month + "'";

							ii = ad.SqlExecuteUpdate(updateArchiveData);
							if (ii > 0) {
								linksForm
										.setMessage2(" CMS Content has been saved successfully");
							} else {
								linksForm
										.setMessage("Error...CMS Content has been not saved... ");
							}
						} else {
							request.setAttribute("statusMessage",
									"Error.....Content should be with in 780 charecters only data not saved....");
						}

					}

				} else {
					System.out.println("insert data");

					if (!linkName.equalsIgnoreCase("Login CMS")) {
						String insertlinksContent = "insert into archieves(link_name,module,content_description,file_name,video_name,image_name,year,month,status) "
								+ " values('"
								+ linkName
								+ "','Main','"
								+ content_description
								+ "','"
								+ fileNameList
								+ "','"
								+ videoNameList
								+ "','"
								+ imageList
								+ "','"
								+ linksForm.getYear()
								+ "','"
								+ linksForm.getMonth()
								+ "','"
								+ archieveStatus
								+ "')";

						ii = ad.SqlExecuteUpdate(insertlinksContent);
						if (ii > 0) {
							linksForm
									.setMessage2("CMS Content has been saved successfully");
						} else {
							linksForm
									.setMessage("Error...CMS Content has been not saved... ");
						}
					} else {
						if (size <= 780) {
							String insertlinksContent = "insert into archieves(link_name,module,content_description,file_name,video_name,image_name,year,month,status) "
									+ " values('"
									+ linkName
									+ "','Main','"
									+ content_description
									+ "','"
									+ fileNameList
									+ "','"
									+ videoNameList
									+ "','"
									+ imageList
									+ "','"
									+ linksForm.getYear()
									+ "','"
									+ linksForm.getMonth()
									+ "','"
									+ archieveStatus + "')";

							ii = ad.SqlExecuteUpdate(insertlinksContent);
							if (ii > 0) {
								linksForm
										.setMessage2("CMS Content has been saved successfully");
							} else {
								linksForm
										.setMessage("Error...CMS Content has been not saved... ");
							}
						} else {
							request.setAttribute("statusMessage",
									"Error.....Content should be with in 780 charecters only data not saved....");
						}

					}

				}

				/*
				 * String
				 * deleteUploadFiles="delete from filelist where link_name='"
				 * +linkName+"' "; int a=0;
				 * a=ad.SqlExecuteUpdate(deleteUploadFiles); String
				 * deleteVideoFiles
				 * ="delete from video_list where video_name='"+linkName+"' ";
				 * ad.SqlExecuteUpdate(deleteVideoFiles);
				 */
				String sql3 = "select * from archieves where link_name='"
						+ linkName + "'    and   year='" + linksForm.getYear()
						+ "' and month='" + month + "'";
				ResultSet rs12 = ad.selectQuery(sql3);
				ArrayList list = new ArrayList();
				String files = "";
				while (rs12.next()) {
					files = rs12.getString("file_name");
					if (files.equalsIgnoreCase("")) {

					} else {

						String v[] = files.split(",");
						int l = v.length;
						for (int i = 0; i < l; i++) {
							ManageCMSForm linksForm1 = new ManageCMSForm();
							int x = v[i].lastIndexOf("/");
							files = v[i].substring(x + 1);
							linksForm1.setFileList(files);
							list.add(linksForm1);
						}

					}
				}

				if (files.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("listName", list);
				}
				String setVideos = "select * from archieves where link_name='"
						+ linkName + "'  and  year='" + linksForm.getYear()
						+ "' and month='" + month + "'";
				ResultSet rsSetVideos = ad.selectQuery(setVideos);
				ArrayList videoLists = new ArrayList();
				String videos = "";
				while (rsSetVideos.next()) {
					linksForm = new ManageCMSForm();
					videos = rsSetVideos.getString("video_name");

					if (videos.equalsIgnoreCase("")) {

					} else {
						String v[] = videos.split(",");
						int l = v.length;
						for (int i = 0; i < l; i++) {
							ManageCMSForm linksForm1 = new ManageCMSForm();
							int x = v[i].lastIndexOf("/");
							videos = v[i].substring(x + 1);
							linksForm1.setVideoFilesList(videos);
							videoLists.add(linksForm1);
						}
					}
				}
				if (videos.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("videosList", videoLists);
				}

				String setImage = "select * from archieves where link_name='"
						+ linkName + "'  and  year='" + year + "' and month='"
						+ month + "'";
				ResultSet rsSetImages = ad.selectQuery(setImage);
				ArrayList imageLists = new ArrayList();
				String images = "";
				while (rsSetImages.next()) {
					linksForm = new ManageCMSForm();
					images = rsSetImages.getString("image_name");

					if (images.equalsIgnoreCase("")) {

					} else {
						String v[] = images.split(",");
						int l = v.length;
						for (int i = 0; i < l; i++) {
							ManageCMSForm linksForm1 = new ManageCMSForm();
							int x = v[i].lastIndexOf("/");
							images = v[i].substring(x + 1);
							linksForm1.setImageList(images);
							imageLists.add(linksForm1);
						}
					}
				}
				if (images.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("imageLists", imageLists);
				}

				String sql = "select * from links where link_name='" + linkName
						+ "' and " + "status is null and module='Main' ";
				System.out.println("sql=" + sql);
				ResultSet rs = ad.selectQuery(sql);
				String linkId = null;
				while (rs.next()) {
					linkId = rs.getString("id");
					linksForm.setSubLinkName(rs.getString("link_name"));
					linksForm.setSubLinkId(linkName);
					linksForm.setLinkId(rs.getString("id"));
					linksForm.setLinkPath(rs.getString("link_path"));
					linksForm.setMethodName(rs.getString("method"));
					linksForm.setPriority(rs.getString("priority"));
					// linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
				}
				request.setAttribute("linkId", linkId);
				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
			}
			// save sub link archive data
			if (!sublink.equalsIgnoreCase("")
					&& subsublink.equalsIgnoreCase("")) {

				String content_description = "";

				String fileNameList = "";
				String videoNameList = "";
				int filesCount = 0;
				String getFilesCount = "select count(*) from filelist where link_name='"
						+ linkName
						+ "' and sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='' and year='"
						+ year
						+ "' and month='" + month + "'";
				ResultSet rsFilesCount = ad.selectQuery(getFilesCount);
				while (rsFilesCount.next()) {
					filesCount = rsFilesCount.getInt(1);
				}
				if (filesCount > 0) {
					String getFiles = "select * from filelist where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='' and year='"
							+ year
							+ "' and month='" + month + "'";
					ResultSet rsFiles = ad.selectQuery(getFiles);
					while (rsFiles.next()) {
						fileNameList += "/EMicro Files/News And Media/Archieves"
								+ "/"
								+ linkName
								+ "/"
								+ sublink
								+ "/"
								+ "UploadFiles"
								+ "/"
								+ linksForm.getYear()
								+ "/" + rsFiles.getString("file_name") + ",";

					}
					fileNameList = fileNameList.substring(0,
							(fileNameList.length() - 1));
				}
				System.out.println("fileNameList=" + fileNameList);

				String imageList = "";
				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='' and year='"
						+ linksForm.getYear()
						+ "' and month='"
						+ linksForm.getMonth() + "'";
				ResultSet rsImageCount = ad.selectQuery(getImageCount);
				while (rsImageCount.next()) {
					imageCount = rsImageCount.getInt(1);
				}
				if (imageCount > 0) {

					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='' and year='"
							+ linksForm.getYear()
							+ "' and month='"
							+ linksForm.getMonth() + "'";
					ResultSet rsImages = ad.selectQuery(getImage);

					while (rsImages.next()) {

						// cms\HR\UploadVideos\2012
						imageList += "/EMicro Files/News And Media/Archieves"
								+ "/" + linkName + "/" + sublink
								+ "/Upload Images" + "/" + linksForm.getYear()
								+ "/" + rsImages.getString("file_name") + ",";
					}
					imageList = imageList
							.substring(0, (imageList.length() - 1));
				}
				System.out.println("imageList=" + imageList);
				int videoCount = 0;
				String getVideoCount = "select count(*) from video_list where link_name='"
						+ linkName
						+ "' and sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='' and year='"
						+ year
						+ "' and month='" + month + "'";
				ResultSet rsVideoCount = ad.selectQuery(getVideoCount);
				while (rsVideoCount.next()) {
					videoCount = rsVideoCount.getInt(1);
				}
				if (videoCount > 0) {
					String getVideos = "select * from video_list where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='' and year='"
							+ year
							+ "' and month='" + month + "'";
					ResultSet rsVideos = ad.selectQuery(getVideos);
					while (rsVideos.next()) {
						videoNameList += "/EMicro Files/News And Media/Archieves"
								+ "/"
								+ linkName
								+ "/"
								+ sublink
								+ "/"
								+ "UploadVideos"
								+ "/"
								+ linksForm.getYear()
								+ "/" + rsVideos.getString("video_name") + ",";
					}
					videoNameList = videoNameList.substring(0,
							(videoNameList.length() - 1));
				}
				System.out.println("videoNameList=" + videoNameList);

				Enumeration<String> params = request
						.getParameterNames();
				String parameter;
				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}
				content_description = content_description.replaceAll("'", "`");
				linksForm.setContentDescriptionAdmin(content_description);
				String getArchiveCount = "select count(*) from archieves where link_name='"
						+ sublink
						+ "' and module='"
						+ linkName
						+ "'  and year='"
						+ linksForm.getYear()
						+ "' and month='" + month + "'";
				int archiveDataCount = 0;
				ResultSet rsArchiveCount = ad.selectQuery(getArchiveCount);
				while (rsArchiveCount.next()) {
					archiveDataCount = rsArchiveCount.getInt(1);
				}
				if (archiveDataCount >= 1) {
					System.out.println("update data");

					String updateArchiveData = "update archieves set content_description='"
							+ content_description
							+ "',status='"
							+ linksForm.getArchiveStatus()
							+ "', file_name='"
							+ fileNameList
							+ "',video_name='"
							+ videoNameList
							+ "'"
							+ ",image_name='"
							+ imageList
							+ "' where link_name='"
							+ sublink
							+ "' and module='"
							+ linkName
							+ "' and year='"
							+ linksForm.getYear()
							+ "' and month='"
							+ month
							+ "'";

					ii = ad.SqlExecuteUpdate(updateArchiveData);
					if (ii > 0) {
						linksForm
								.setMessage2(" CMS Content has been saved successfully");
					} else {
						linksForm
								.setMessage("Error...CMS Content has been not saved... ");
					}

				} else {

					String insertlinksContent = "insert into archieves(link_name,module,content_description,file_name,video_name,image_name,year,month,status) "
							+ " values('"
							+ sublink
							+ "','"
							+ linkName
							+ "','"
							+ content_description
							+ "','"
							+ fileNameList
							+ "','"
							+ videoNameList
							+ "','"
							+ imageList
							+ "','"
							+ linksForm.getYear()
							+ "','"
							+ linksForm.getMonth()
							+ "','"
							+ linksForm.getArchiveStatus() + "')";

					ii = ad.SqlExecuteUpdate(insertlinksContent);
					if (ii > 0) {
						linksForm
								.setMessage2(" CMS Content has been saved successfully");
					} else {
						linksForm
								.setMessage("Error...CMS Content has been not saved... ");
					}

				}

				/*
				 * String
				 * deleteUploadFiles="delete from filelist where link_name='"
				 * +linkName+"' and sub_link_name='"+sublink+"'";
				 * ad.SqlExecuteUpdate(deleteUploadFiles); String
				 * deleteVideoFiles
				 * ="delete from video_list where link_name='"+linkName
				 * +"' and sub_link_name='"+sublink+"'";
				 * ad.SqlExecuteUpdate(deleteVideoFiles);
				 */

				String sql3 = "select * from archieves where link_name='"
						+ sublink + "' and module='" + linkName
						+ "'  and  year='" + linksForm.getYear()
						+ "' and month='" + month + "'";
				ResultSet rs12 = ad.selectQuery(sql3);
				ArrayList list = new ArrayList();
				ArrayList imgeList = new ArrayList();
				String files = "";
				String images = "";
				while (rs12.next()) {
					files = rs12.getString("file_name");
					if (files.equalsIgnoreCase("")) {
					} else {
						String v[] = files.split(",");
						int l = v.length;
						for (int j = 0; j < l; j++) {
							ManageCMSForm linksForm1 = new ManageCMSForm();
							int x = v[j].lastIndexOf("/");
							files = v[j].substring(x + 1);
							linksForm1.setFileList(files);
							list.add(linksForm1);
						}
					}

					images = rs12.getString("image_name");

				}

				if (images.equalsIgnoreCase("")) {

				} else {
					String v[] = images.split(",");
					int l = v.length;
					for (int i = 0; i < l; i++) {
						ManageCMSForm linksForm1 = new ManageCMSForm();
						int x = v[i].lastIndexOf("/");
						files = v[i].substring(x + 1);
						linksForm1.setImageList(files);

						/*
						 * String insertFiles =
						 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
						 * +linkName+"','','"+files+"','','')";
						 * System.out.println("insertFiles="+insertFiles);
						 * adlinks.SqlExecuteUpdate(insertFiles);
						 */

						imgeList.add(linksForm1);
					}
				}

				if (files.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("listName", list);
				}
				String setVideos = "select * from archieves where link_name='"
						+ sublink + "' and module='" + linkName
						+ "' and year='" + linksForm.getYear()
						+ "' and month='" + month + "'";
				ResultSet rsSetVideos = ad.selectQuery(setVideos);
				ArrayList videoLists = new ArrayList();
				String videos = "";
				while (rsSetVideos.next()) {
					linksForm = new ManageCMSForm();
					videos = rsSetVideos.getString("video_name");
					if (videos.equalsIgnoreCase("")) {
					} else {
						String v[] = videos.split(",");
						int l = v.length;
						for (int k = 0; k < l; k++) {
							LinksForm linksForm1 = new LinksForm();
							int x = v[k].lastIndexOf("/");
							videos = v[k].substring(x + 1);
							linksForm1.setVideoFilesList(videos);
							videoLists.add(linksForm1);
						}
					}
				}
				if (videos.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("videosList", videoLists);

				}
				if (images.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("imageLists", imgeList);

				}

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);

			}
			// save sub sub link archive data
			if (!linkName.equalsIgnoreCase("") && !sublink.equalsIgnoreCase("")
					&& !subsublink.equalsIgnoreCase("")) {
				String subSubLink = linksForm.getSubSubLinkName();
				String content_description = "";
				String filelist[] = request.getParameterValues("checkedfiles1");
				String fileNameList = "";
				String videoNameList = "";
				int filesCount = 0;
				String getFilesCount = "select * from filelist where link_name='"
						+ linkName
						+ "' and sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='"
						+ subSubLink
						+ "' and year='" + year + "' and month='" + month + "'";
				ResultSet rsFilesCount = ad.selectQuery(getFilesCount);
				while (rsFilesCount.next()) {
					filesCount = rsFilesCount.getInt(1);
				}
				if (filesCount > 0) {
					String getFiles = "select * from filelist where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='"
							+ subSubLink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";
					ResultSet rsFiles = ad.selectQuery(getFiles);
					while (rsFiles.next()) {
						fileNameList += "/EMicro Files/News And Media/Archieves"
								+ "/"
								+ linkName
								+ "/"
								+ sublink
								+ "/"
								+ subSubLink
								+ "/"
								+ "UploadFiles"
								+ "/"
								+ linksForm.getYear()
								+ "/"
								+ rsFiles.getString("file_name") + ",";
					}
					fileNameList = fileNameList.substring(0,
							(fileNameList.length() - 1));
				}

				System.out.println("fileNameList=" + fileNameList);
				String imageList = "";
				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='"
						+ subSubLink
						+ "' and year='"
						+ linksForm.getYear()
						+ "' and month='" + linksForm.getMonth() + "'";
				ResultSet rsImageCount = ad.selectQuery(getImageCount);
				while (rsImageCount.next()) {
					imageCount = rsImageCount.getInt(1);
				}
				if (imageCount > 0) {

					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='"
							+ subSubLink
							+ "' and year='"
							+ linksForm.getYear()
							+ "' and month='" + linksForm.getMonth() + "'";
					ResultSet rsImages = ad.selectQuery(getImage);

					while (rsImages.next()) {

						// cms\HR\UploadVideos\2012
						imageList += "/EMicro Files/News And Media/Archieves"
								+ "/" + linkName + "/Upload Images" + "/"
								+ linksForm.getYear() + "/"
								+ rsImages.getString("file_name") + ",";
					}
					imageList = imageList
							.substring(0, (imageList.length() - 1));

				}
				int videoCount = 0;
				String getVideoCount = "select count(*) from video_list where link_name='"
						+ linkName
						+ "' and sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='"
						+ subSubLink
						+ "' and year='" + year + "' and month='" + month + "'";
				ResultSet rsVideoCount = ad.selectQuery(getVideoCount);
				while (rsVideoCount.next()) {
					videoCount = rsVideoCount.getInt(1);
				}
				if (videoCount > 0) {
					String getVideos = "select * from video_list where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='"
							+ subSubLink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";
					ResultSet rsVideos = ad.selectQuery(getVideos);
					while (rsVideos.next()) {
						videoNameList += "/EMicro Files/News And Media/Archieves"
								+ "/"
								+ linkName
								+ "/"
								+ sublink
								+ "/"
								+ subSubLink
								+ "/"
								+ "UploadVideos"
								+ "/"
								+ linksForm.getYear()
								+ "/"
								+ rsVideos.getString("video_name") + ",";
					}

					videoNameList = videoNameList.substring(0,
							(videoNameList.length() - 1));
				}
				System.out.println("videoNameList=" + videoNameList);

				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}
				content_description = content_description.replaceAll("'", "`");
				linksForm.setContentDescriptionAdmin(content_description);
				String getArchiveCount = "select count(*) from archieves where link_name='"
						+ subSubLink
						+ "' and module='"
						+ linkName
						+ "' and  sub_link='"
						+ sublink
						+ "'  and year='"
						+ linksForm.getYear() + "' and month='" + month + "'";
				int archiveDataCount = 0;
				ResultSet rsArchiveCount = ad.selectQuery(getArchiveCount);
				while (rsArchiveCount.next()) {
					archiveDataCount = rsArchiveCount.getInt(1);
				}
				if (archiveDataCount >= 1) {
					System.out.println("update data");

					String updateArchiveData = "update archieves set content_description='"
							+ content_description
							+ "',status='"
							+ linksForm.getArchiveStatus()
							+ "', file_name='"
							+ fileNameList
							+ "',video_name='"
							+ videoNameList
							+ "',image_name='"
							+ imageList
							+ "'"
							+ " where link_name='"
							+ subSubLink
							+ "' and module='"
							+ linkName
							+ "' and  sub_link='"
							+ sublink
							+ "' and year='"
							+ linksForm.getYear()
							+ "' and month='" + month + "'";

					ii = ad.SqlExecuteUpdate(updateArchiveData);
					if (ii > 0) {
						linksForm
								.setMessage2("CMS Content has been saved successfully");
					} else {
						linksForm
								.setMessage("Error...CMS Content has been not saved... ");
					}

				} else {

					String insertlinksContent = "insert into archieves(link_name,module,sub_link,content_description,file_name,video_name,image_name,year,month,status) "
							+ " values('"
							+ subSubLink
							+ "','"
							+ linkName
							+ "','"
							+ sublink
							+ "','"
							+ content_description
							+ "','"
							+ fileNameList
							+ "','"
							+ videoNameList
							+ "','"
							+ imageList
							+ "','"
							+ linksForm.getYear()
							+ "','"
							+ linksForm.getMonth()
							+ "','"
							+ linksForm.getArchiveStatus() + "')";

					ii = ad.SqlExecuteUpdate(insertlinksContent);

					if (ii > 0) {
						linksForm
								.setMessage2("CMS Content has been saved successfully");
					} else {
						linksForm
								.setMessage("Error...CMS Content has been not saved... ");
					}
				}

				/*
				 * String
				 * deleteUploadFiles="delete from filelist where link_name='"
				 * +linkName
				 * +"' and sub_link_name='"+sublink+"' and sub_sub_linkname='"
				 * +subSubLink+"'"; ad.SqlExecuteUpdate(deleteUploadFiles);
				 * String
				 * deleteVideoFiles="delete from video_list where link_name='"
				 * +linkName
				 * +"' and sub_link_name='"+sublink+"'  and sub_sub_linkname='"
				 * +subSubLink+"'"; ad.SqlExecuteUpdate(deleteVideoFiles);
				 */

				String sql3 = "select * from archieves where link_name='"
						+ subSubLink + "' and module='" + linkName
						+ "' and sub_link='" + sublink + "' and  year='"
						+ linksForm.getYear() + "' and month='" + month + "' ";
				ResultSet rs12 = ad.selectQuery(sql3);
				ArrayList list = new ArrayList();
				String files = "";
				while (rs12.next()) {
					files = rs12.getString("file_name");
					if (files.equalsIgnoreCase("")) {
					} else {
						String v[] = files.split(",");
						int l = v.length;
						for (int j = 0; j < l; j++) {
							ManageCMSForm linksForm1 = new ManageCMSForm();
							int x = v[j].lastIndexOf("/");
							files = v[j].substring(x + 1);
							linksForm1.setFileList(files);
							list.add(linksForm1);
						}
					}

				}
				if (files.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("listName", list);
				}
				String getImageList = "select * from archieves where link_name='"
						+ subSubLink
						+ "' and module='"
						+ linkName
						+ "' and sub_link='"
						+ sublink
						+ "' and  year='"
						+ linksForm.getYear() + "' and month='" + month + "'";
				ResultSet rsgetImageList = ad.selectQuery(getImageList);
				ArrayList listimages = new ArrayList();
				String images = "";
				while (rsgetImageList.next()) {
					images = rsgetImageList.getString("image_name");
					if (images.equalsIgnoreCase("")) {
					} else {
						String v[] = images.split(",");
						int l = v.length;
						for (int j = 0; j < l; j++) {
							ManageCMSForm linksForm1 = new ManageCMSForm();
							int x = v[j].lastIndexOf("/");
							images = v[j].substring(x + 1);
							linksForm1.setImageList(images);
							listimages.add(linksForm1);
						}
					}

				}
				if (images.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("imageLists", listimages);
				}
				String setVideos = "select * from archieves where link_name='"
						+ subSubLink + "' and module='" + linkName
						+ "' and sub_link='" + sublink + "' and year='"
						+ linksForm.getYear() + "' and month='" + month + "' ";
				ResultSet rsSetVideos = ad.selectQuery(setVideos);
				ArrayList videoLists = new ArrayList();
				String videos = "";
				while (rsSetVideos.next()) {
					linksForm = new ManageCMSForm();
					videos = rsSetVideos.getString("video_name");
					if (videos.equalsIgnoreCase("")) {
					} else {
						String v[] = videos.split(",");
						int l = v.length;
						for (int k = 0; k < l; k++) {
							ManageCMSForm linksForm1 = new ManageCMSForm();
							int x = v[k].lastIndexOf("/");
							videos = v[k].substring(x + 1);
							linksForm1.setVideoFilesList(videos);
							videoLists.add(linksForm1);
						}
					}
				}
				if (videos.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("videosList", videoLists);

				}
				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);
			}

			request.setAttribute("displayFields", "displayFields");
			request.setAttribute("displaySublinkField", "displaySublinkField");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayMainLinks");

	}

	public ActionForward deleteUploadedMainFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			ManageCMSForm linksForm = (ManageCMSForm) form;
			LinksDao ad = new LinksDao();
			String linkName = linksForm.getLinkName();
			System.out.println("Link Name=" + linkName);
			String sublink = linksForm.getSubLinkName();
			String subsublink = linksForm.getSubSubLinkName();
			String year = linksForm.getYear();
			String month = linksForm.getMonth();
			if (sublink.equalsIgnoreCase("") && subsublink.equalsIgnoreCase("")) {
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);
				String filelist[] = request.getParameterValues("checkedfiles");
				String files = "";
				String link_id = linksForm.getLinkName();
				if (filelist.length == 0) {
					files = "";
				} else {

					for (int i = 0; i < filelist.length; i++) {

						String deleteFiels = "delete from filelist where link_name='"
								+ linksForm.getLinkName()
								+ "' and file_name='"
								+ filelist[i]
								+ "' and year='"
								+ year
								+ "' and month='" + month + "'";
						int a = 0;
						a = ad.SqlExecuteUpdate(deleteFiels);
						if (a > 0) {
							linksForm
									.setMessage("Documents Deleted Successfully");
							String filePath = getServlet()
									.getServletContext()
									.getRealPath(
											"jsp/EMicro Files/News And Media/Archieves")
									+ "/"
									+ link_id
									+ "/UploadFiles"
									+ "/"
									+ linksForm.getYear();
							InputStream in = ConnectionFactory.class
									.getClassLoader().getResourceAsStream(
											"db.properties");
							Properties props = new Properties();
							props.load(in);
							in.close();
							String uploadFilePath = props
									.getProperty("file.uploadFilePath");
							filePath = uploadFilePath
									+ "/EMicro Files/News And Media/Archieves/"
									+ link_id + "/UploadFiles" + "/"
									+ linksForm.getYear();

							File fileToCreate = new File(filePath, filelist[i]);
							boolean check = false;
							check = fileToCreate.delete();

						} else {
							linksForm
									.setMessage("Error While Deleted Files ... Please check Entered Values");
						}
					}
				}

				// String
				// videoList[]=request.getParameterValues("checkedVideofiles");
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ linkName
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ year + "' and month='" + month + "'";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and  sub_link_name='' and sub_sub_linkname=''  and year='"
						+ year + "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
							+ year + "' and month='" + month + "' ";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}

				ArrayList videoList = new ArrayList();
				int videocount = 0;
				String getVideoscount = "select count(*) from video_list where link_name='"
						+ linkName
						+ "' and sub_link_name='' and sub_sub_linkname=''  and year='"
						+ year + "' and month='" + month + "'";
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					String getVideos = "select * from video_list where link_name='"
							+ linkName
							+ "' and year='"
							+ year
							+ "' and month='" + month + "'";
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}

				request.setAttribute("displaySublinkField",
						"displaySublinkField");

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
			}
			// get sub link uploaded data
			if (!sublink.equalsIgnoreCase("")
					&& subsublink.equalsIgnoreCase("")) {
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);
				String filelist[] = request.getParameterValues("checkedfiles");
				String files = "";
				if (filelist.length == 0) {
					files = "";
				} else {

					for (int i = 0; i < filelist.length; i++) {

						String deleteFiels = "delete from filelist where link_name='"
								+ linkName
								+ "' and sub_link_name='"
								+ sublink
								+ "'  and file_name='"
								+ filelist[i]
								+ "' and year='"
								+ year
								+ "' and month='"
								+ month + "'";
						int a = 0;
						a = ad.SqlExecuteUpdate(deleteFiels);
						if (a > 0) {
							linksForm
									.setMessage("Documents Deleted Successfully");

							String filePath = getServlet()
									.getServletContext()
									.getRealPath(
											"jsp/EMicro Files/News And Media/Archieves")
									+ "/"
									+ linkName
									+ "/"
									+ sublink
									+ "/UploadFiles"
									+ "/"
									+ linksForm.getYear();

							InputStream in = ConnectionFactory.class
									.getClassLoader().getResourceAsStream(
											"db.properties");
							Properties props = new Properties();
							props.load(in);
							in.close();
							String uploadFilePath = props
									.getProperty("file.uploadFilePath");
							filePath = uploadFilePath
									+ "/EMicro Files/News And Media/Archieves/"
									+ linkName + "/" + sublink + "/UploadFiles"
									+ "/" + linksForm.getYear();

							File fileToCreate = new File(filePath, filelist[i]);
							boolean check = false;
							check = fileToCreate.delete();
						} else {
							linksForm
									.setMessage("Error While Deleted Files ... Please check Entered Values");
						}
					}
				}

				// String
				// videoList[]=request.getParameterValues("checkedVideofiles");
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ linkName + "' and sub_link_name='" + sublink
						+ "' and year='" + year + "' and month='" + month
						+ "' and sub_sub_linkname=''";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and  sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname=''  and year='"
						+ year
						+ "' and month='" + month + "' ";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='' and year='"
							+ year
							+ "' and month='" + month + "'";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}

				ArrayList videoList = new ArrayList();
				int videocount = 0;
				String getVideoscount = "select count(*) from video_list where link_name='"
						+ linkName
						+ "'  and sub_link_name='"
						+ sublink
						+ "' and year='"
						+ year
						+ "' and month='"
						+ month
						+ "' and sub_sub_linkname=''";
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					String getVideos = "select * from video_list where link_name='"
							+ linkName
							+ "'  and sub_link_name='"
							+ sublink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'   and sub_sub_linkname=''";
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);
				request.setAttribute("displayFields", "displayFields");
			}
			// get sub sub link uploaded data
			if (!linkName.equalsIgnoreCase("") && !sublink.equalsIgnoreCase("")
					&& !subsublink.equalsIgnoreCase("")) {
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);
				String filelist[] = request.getParameterValues("checkedfiles");
				String files = "";
				if (filelist.length == 0) {
					files = "";
				} else {

					for (int i = 0; i < filelist.length; i++) {

						String deleteFiels = "delete from filelist where link_name='"
								+ linkName
								+ "' and sub_link_name='"
								+ sublink
								+ "'"
								+ " and sub_sub_linkname='"
								+ subsublink
								+ "' and file_name='"
								+ filelist[i]
								+ "' and year='"
								+ year
								+ "' and month='"
								+ month + "'";
						int a = 0;
						a = ad.SqlExecuteUpdate(deleteFiels);
						if (a > 0) {
							linksForm
									.setMessage("Documents Deleted Successfully");

							String filePath = getServlet()
									.getServletContext()
									.getRealPath(
											"jsp/EMicro Files/News And Media/Archieves")
									+ "/"
									+ linkName
									+ "/"
									+ sublink
									+ "/"
									+ subsublink
									+ "/UploadFiles"
									+ "/"
									+ linksForm.getYear();

							InputStream in = ConnectionFactory.class
									.getClassLoader().getResourceAsStream(
											"db.properties");
							Properties props = new Properties();
							props.load(in);
							in.close();
							String uploadFilePath = props
									.getProperty("file.uploadFilePath");
							filePath = uploadFilePath
									+ "/EMicro Files/News And Media/Archieves/"
									+ linkName + "/" + sublink + "/"
									+ subsublink + "/UploadFiles" + "/"
									+ linksForm.getYear();

							File fileToCreate = new File(filePath, filelist[i]);
							boolean check = false;
							check = fileToCreate.delete();
							System.out.println(check);

						} else {
							linksForm
									.setMessage("Error While Deleted Files ... Please check Entered Values");
						}
					}
				}
				// String
				// videoList[]=request.getParameterValues("checkedVideofiles");
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ linkName + "' and sub_link_name='" + sublink + "'"
						+ " and sub_sub_linkname='" + subsublink
						+ "' and year='" + year + "' and month='" + month + "'";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and  sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='"
						+ linksForm.getSubSubLinkName()
						+ "'  and year='"
						+ year + "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='"
							+ subsublink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}
				ArrayList videoList = new ArrayList();
				int videocount = 0;
				String getVideoscount = "select count(*) from video_list where link_name='"
						+ linkName
						+ "'  and sub_link_name='"
						+ sublink
						+ "' and "
						+ " sub_sub_linkname='"
						+ subsublink
						+ "' and year='" + year + "' and month='" + month + "'";
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					String getVideos = "select * from video_list where link_name='"
							+ linkName
							+ "'  and sub_link_name='"
							+ sublink
							+ "'"
							+ " and sub_sub_linkname='"
							+ subsublink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}
				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);

			}
			request.setAttribute("displaySublinkField", "displaySublinkField");
			request.setAttribute("displayFields", "displayFields");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("displayMainLinks");

	}

	public ActionForward deleteMainLinkUploadedVideo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			ManageCMSForm linksForm = (ManageCMSForm) form;
			LinksDao ad = new LinksDao();
			String linkName = linksForm.getLinkName();
			System.out.println("Link Name=" + linkName);
			String sublink = linksForm.getSubLinkName();
			String subsublink = linksForm.getSubSubLinkName();
			String year = linksForm.getYear();
			String month = linksForm.getMonth();
			if (sublink.equalsIgnoreCase("") && subsublink.equalsIgnoreCase("")) {
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);
				String videoList[] = request
						.getParameterValues("checkedVideofiles");
				String videos = "";
				if (videoList.length == 0) {
					videos = "";
				} else {

					for (int i = 0; i < videoList.length; i++) {

						String deleteVideos = "delete from video_list where link_name='"
								+ linkName
								+ "' and video_name='"
								+ videoList[i]
								+ "' and year='"
								+ year
								+ "' and month='" + month + "'";
						int a = 0;
						a = ad.SqlExecuteUpdate(deleteVideos);
						if (a > 0) {
							linksForm.setMessage("Videos Deleted Successfully");
							String filePath = getServlet().getServletContext()
									.getRealPath(
											"jsp/EMicro Files/News And Media/Archieves/"
													+ linkName
													+ "/UploadVideos" + "/"
													+ linksForm.getYear());
							InputStream in = ConnectionFactory.class
									.getClassLoader().getResourceAsStream(
											"db.properties");
							Properties props = new Properties();
							props.load(in);
							in.close();
							String uploadFilePath = props
									.getProperty("file.uploadFilePath");
							filePath = uploadFilePath
									+ "/EMicro Files/News And Media/Archieves/"
									+ linkName + "/UploadVideos" + "/"
									+ linksForm.getYear();

							File fileToCreate1 = new File(filePath,
									videoList[i]);
							boolean check = false;
							check = fileToCreate1.delete();
						} else {
							linksForm
									.setMessage("Error While Deleted Videos ... Please check ");
						}
					}
				}

				// String
				// videoList[]=request.getParameterValues("checkedVideofiles");

				int filecount = 0;
				String getFilescount = "select count(*) from filelist where link_name='"
						+ linkName
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ year + "' and month='" + month + "'";
				ResultSet rsgetFilescount = ad.selectQuery(getFilescount);
				while (rsgetFilescount.next()) {
					filecount = rsgetFilescount.getInt(1);
				}
				String getVideos = "select * from video_list where link_name='"
						+ linkName
						+ "' and sub_link_name='' and sub_sub_linkname=''  and year='"
						+ year + "' and month='" + month + "'";
				String getVideoscount = "select count(*) from video_list where link_name='"
						+ linkName
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ year + "' and month='" + month + "'";

				if (filecount >= 1) {
					ArrayList list = new ArrayList();
					String sql3 = "select * from filelist where link_name='"
							+ linkName
							+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
							+ year + "' and month='" + month + "'";

					ResultSet rs12 = ad.selectQuery(sql3);
					while (rs12.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setFileList(rs12.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
				}

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and  sub_link_name='' and sub_sub_linkname=''  and year='"
						+ year + "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
							+ year + "' and month='" + month + "'";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}
				ArrayList videoList1 = new ArrayList();
				int videocount = 0;
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList1.add(linksForm);
					}
					request.setAttribute("videosList", videoList1);
				}

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
			}
			// get sub link uploaded data
			if (!sublink.equalsIgnoreCase("")
					&& subsublink.equalsIgnoreCase("")) {
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);
				String filelist[] = request
						.getParameterValues("checkedVideofiles");
				String files = "";
				if (filelist.length == 0) {
					files = "";
				} else {

					for (int i = 0; i < filelist.length; i++) {
						String deleteFiels = "delete from video_list where link_name='"
								+ linkName
								+ "' and sub_link_name='"
								+ sublink
								+ "'  and sub_sub_linkname='' and video_name='"
								+ filelist[i]
								+ "' and year='"
								+ year
								+ "' and month='" + month + "'";
						int a = 0;
						a = ad.SqlExecuteUpdate(deleteFiels);
						if (a > 0) {
							linksForm
									.setMessage("Documents Deleted Successfully");
							String p = getServlet().getServletContext()
									.getRealPath(
											"jsp/EMicro Files/News And Media/Archieves/"
													+ linkName + "/" + sublink
													+ "/UploadVideos" + "/"
													+ year);

							InputStream in = ConnectionFactory.class
									.getClassLoader().getResourceAsStream(
											"db.properties");
							Properties props = new Properties();
							props.load(in);
							in.close();
							String uploadFilePath = props
									.getProperty("file.uploadFilePath");
							p = uploadFilePath
									+ "/EMicro Files/News And Media/Archieves/"
									+ linkName + "/" + sublink
									+ "/UploadVideos" + "/" + year;

							File videoFile = new File(p, filelist[i]);
							boolean check = false;
							check = videoFile.delete();

						} else {
							linksForm
									.setMessage("Error While Deleted Files ... Please check Entered Values");
						}
					}
				}

				// String
				// videoList[]=request.getParameterValues("checkedVideofiles");
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ linkName + "' and sub_link_name='" + sublink
						+ "'  and sub_sub_linkname='' and year='" + year
						+ "' and month='" + month + "'";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and  sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname=''  and year='"
						+ year
						+ "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='' and year='"
							+ year
							+ "' and month='" + month + "'";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}

				ArrayList videoList = new ArrayList();
				int videocount = 0;
				String getVideoscount = "select count(*) from video_list where link_name='"
						+ linksForm.getLinkName()
						+ "'  and sub_link_name='"
						+ sublink
						+ "' and year='"
						+ year
						+ "' and month='"
						+ month + "'";
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					String getVideos = "select * from video_list where link_name='"
							+ linksForm.getLinkName()
							+ "'  and sub_link_name='"
							+ sublink
							+ "' and year='"
							+ year
							+ "'   and month='"
							+ month
							+ "'";
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);
			}
			// get sub sub link uploaded data
			if (!linkName.equalsIgnoreCase("") && !sublink.equalsIgnoreCase("")
					&& !subsublink.equalsIgnoreCase("")) {
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);
				String filelist[] = request
						.getParameterValues("checkedVideofiles");
				String files = "";
				if (filelist.length == 0) {
					files = "";
				} else {

					for (int i = 0; i < filelist.length; i++) {

						String deleteFiels = "delete from video_list where link_name='"
								+ linkName
								+ "' and sub_link_name='"
								+ sublink
								+ "'"
								+ " and sub_sub_linkname='"
								+ subsublink
								+ "' and video_name='"
								+ filelist[i]
								+ "' and year='"
								+ year
								+ "' and month='"
								+ month + "'";
						int a = 0;
						a = ad.SqlExecuteUpdate(deleteFiels);
						if (a > 0) {
							linksForm
									.setMessage("Documents Deleted Successfully");
							String p = getServlet().getServletContext()
									.getRealPath(
											"jsp/EMicro Files/News And Media/Archieves/"
													+ linkName + "/" + sublink
													+ "/" + subsublink
													+ "/UploadVideos" + "/"
													+ year);

							InputStream in = ConnectionFactory.class
									.getClassLoader().getResourceAsStream(
											"db.properties");
							Properties props = new Properties();
							props.load(in);
							in.close();
							String uploadFilePath = props
									.getProperty("file.uploadFilePath");
							p = uploadFilePath
									+ "/EMicro Files/News And Media/Archieves/"
									+ linkName + "/" + sublink + "/"
									+ subsublink + "/UploadVideos" + "/" + year;

							File fileToCreate1 = new File(p, filelist[i]);
							boolean check = false;
							check = fileToCreate1.delete();
						} else {
							linksForm
									.setMessage("Error While Deleted Files ... Please check Entered Values");
						}
					}
				}
				// String
				// videoList[]=request.getParameterValues("checkedVideofiles");
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ linkName + "' and sub_link_name='" + sublink + "'"
						+ " and sub_sub_linkname='" + subsublink
						+ "' and year='" + year + "' and month='" + month
						+ "' ";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and  sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='"
						+ subsublink
						+ "'  and year='"
						+ year
						+ "' and month='"
						+ month
						+ "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ linksForm.getLinkName()
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='"
							+ subsublink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}
				ArrayList videoList = new ArrayList();
				int videocount = 0;
				String getVideoscount = "select count(*) from video_list where link_name='"
						+ linkName
						+ "'  and sub_link_name='"
						+ sublink
						+ "' and "
						+ " sub_sub_linkname='"
						+ subsublink
						+ "' and year='" + year + "' and month='" + month + "'";
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					String getVideos = "select * from video_list where link_name='"
							+ linksForm.getLinkName()
							+ "'  and sub_link_name='"
							+ sublink
							+ "'"
							+ " and sub_sub_linkname='"
							+ sublink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}
				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("displaySublinkField", "displaySublinkField");
		request.setAttribute("displayFields", "displayFields");
		return mapping.findForward("displayMainLinks");

	}

	public ActionForward uploadVideo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			ManageCMSForm linksForm = (ManageCMSForm) form;
			LinksDao ad = new LinksDao();
			String linkName = linksForm.getLinkName();
			System.out.println("Link Name=" + linkName);
			String sublink = linksForm.getSubLinkName();
			String subsublink = linksForm.getSubSubLinkName();
			String year = linksForm.getYear();
			String month = linksForm.getMonth();
			if (sublink.equalsIgnoreCase("") && subsublink.equalsIgnoreCase("")) {
				String link_id = linksForm.getLinkName();
				linksForm.setLinkName(link_id);
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);

				FormFile myFile = linksForm.getVideoFileNames();
				String contentType = myFile.getContentType();
				String fileName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				System.out.println("size of the file=" + myFile.getFileSize());
				int filesize = myFile.getFileSize();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

				if (ext.equalsIgnoreCase("avi") || ext.equalsIgnoreCase("wmv")
						|| ext.equalsIgnoreCase("mpeg")
						|| ext.equalsIgnoreCase("mpg")
						|| ext.equalsIgnoreCase("flv")
						|| ext.equalsIgnoreCase("mp4")
						|| ext.equalsIgnoreCase("swf")
						|| ext.equalsIgnoreCase("webm")) {
					if (filesize <= 62914560) {

						int checkVideoCount = 0;
						String checkVideoExist = "select count(*) from video_list where video_name='"
								+ fileName + "'";
						ResultSet rscheckVideoExist = ad
								.selectQuery(checkVideoExist);
						while (rscheckVideoExist.next()) {
							checkVideoCount = rscheckVideoExist.getInt(1);
						}
						if (checkVideoCount == 0) {

							String filePath = getServlet()
									.getServletContext()
									.getRealPath(
											"jsp/EMicro Files/News And Media/Archieves")
									+ "/"
									+ linkName
									+ "/UploadVideos"
									+ "/"
									+ linksForm.getYear();

							InputStream in = ConnectionFactory.class
									.getClassLoader().getResourceAsStream(
											"db.properties");
							Properties props = new Properties();
							props.load(in);
							in.close();
							String uploadFilePath = props
									.getProperty("file.uploadFilePath");
							filePath = uploadFilePath
									+ "/EMicro Files/News And Media/Archieves/"
									+ linkName + "/UploadVideos" + "/"
									+ linksForm.getYear();

							File destinationDir = new File(filePath);
							if (!destinationDir.exists()) {
								destinationDir.mkdirs();
							}
							filePath = filePath.replace("\\", "\\");

							try {
								File fileToCreate = new File(filePath, fileName);

								FileOutputStream fileOutStream = new FileOutputStream(
										fileToCreate);
								fileOutStream.write(fileData);
								fileOutStream.flush();
								fileOutStream.close();
							} catch (Exception e) {
								e.printStackTrace();
							}

							filePath = filePath.replace("\\", "\\");
							System.out.println("filePath=" + filePath);
							String insertsql = "insert into video_list(link_name,video_path,video_name,sub_link_name,sub_sub_linkname,year,month) values('"
									+ linkName
									+ "','"
									+ filePath
									+ "','"
									+ fileName
									+ "','','','"
									+ linksForm.getYear()
									+ "','"
									+ linksForm.getMonth() + "')";
							System.out.println("insertsql=" + insertsql);
							int a = ad.SqlExecuteUpdate(insertsql);
							if (a > 0) {
								linksForm
										.setMessage("Video Uploaded Successfully");
							} else {
								linksForm
										.setMessage("Error While Uploading Video ... Please check Entered Values");
							}
						} else {
							linksForm
									.setMessage("Video name alredy exist.Please change video name. ");
						}
					} else {
						linksForm
								.setMessage("Error..File Size should not exceed 10 MB");
					}

				} else {
					linksForm
							.setMessage("Please upload only avi,wmv,mpeg,flv,mp4,swf,webm videos ");
				}

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and  sub_link_name='' and sub_sub_linkname=''  and year='"
						+ year + "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
							+ year + "' and month='" + month + "'";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}
				ArrayList fileList = new ArrayList();
				String getFiles = "select * from filelist where link_name='"
						+ linkName
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ year + "' and month='" + month + "'";
				String sql3 = "select * from video_list where link_name='"
						+ linkName
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ year + "' and month='" + month + "'";

				ResultSet rsgetFiles = ad.selectQuery(getFiles);
				while (rsgetFiles.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rsgetFiles.getString("file_name"));
					fileList.add(linksForm);
				}
				request.setAttribute("listName", fileList);
				ArrayList videoList = new ArrayList();
				// String
				// sql3="select * from video_list where link_name='"+link_id+"' and sub_link_name='' and sub_sub_linkname='' and year='"+linksForm.getYear()+"'";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setVideoFilesList(rs12.getString("video_name"));
					videoList.add(linksForm);
				}
				request.setAttribute("videosList", videoList);

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);

			}
			// get sub link uploaded data
			if (!sublink.equalsIgnoreCase("")
					&& subsublink.equalsIgnoreCase("")) {
				String link_id = linksForm.getLinkName();
				linksForm.setLinkName(link_id);
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);

				FormFile myFile = linksForm.getVideoFileNames();
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

					int checkVideoCount = 0;
					String checkVideoExist = "select count(*) from video_list where video_name='"
							+ fileName + "'";
					ResultSet rscheckVideoExist = ad
							.selectQuery(checkVideoExist);
					while (rscheckVideoExist.next()) {
						checkVideoCount = rscheckVideoExist.getInt(1);
					}
					if (checkVideoCount == 0) {
						String filePath = getServlet()
								.getServletContext()
								.getRealPath(
										"jsp/EMicro Files/News And Media/Archieves")
								+ "/"
								+ link_id
								+ "/"
								+ sublink
								+ "/UploadVideos" + "/" + year;

						InputStream in = ConnectionFactory.class
								.getClassLoader().getResourceAsStream(
										"db.properties");
						Properties props = new Properties();
						props.load(in);
						in.close();
						String uploadFilePath = props
								.getProperty("file.uploadFilePath");
						filePath = uploadFilePath
								+ "/EMicro Files/News And Media/Archieves/"
								+ link_id + "/" + sublink + "/UploadVideos"
								+ "/" + year;

						File destinationDir = new File(filePath);
						if (!destinationDir.exists()) {
							destinationDir.mkdirs();
						}

						File imageFile = new File(filePath, fileName);
						FileOutputStream outputStream = new FileOutputStream(
								imageFile);
						outputStream.write(myFile.getFileData());
						outputStream.flush();
						outputStream.close();

						filePath = filePath.replace("\\", "\\");
						System.out.println("filePath=" + filePath);
						String insertsql = "insert into video_list(link_name,video_path,video_name,sub_link_name,sub_sub_linkname,year,month) values('"
								+ link_id
								+ "','"
								+ filePath
								+ "','"
								+ fileName
								+ "','"
								+ sublink
								+ "','','"
								+ year
								+ "','"
								+ month + "')";
						System.out.println("insertsql=" + insertsql);
						int a = ad.SqlExecuteUpdate(insertsql);
						if (a > 0) {
							linksForm
									.setMessage2("Videos Uploaded Successfully");
						} else {
							linksForm
									.setMessage("Error While Uploading Videos ... Please check Entered Values");
						}
					} else {
						linksForm
								.setMessage("Error Video name is exist.Please Change video name.");
					}
				} else {
					linksForm
							.setMessage("Please upload only avi,wmv,mpeg,flv,mp4,swf,webm videos");
				}
				ArrayList fileList = new ArrayList();
				String getFiles = "select * from filelist where link_name='"
						+ linkName + "'and sub_link_name='" + sublink
						+ "' and sub_sub_linkname='' and year='" + year
						+ "' and month='" + month + "'";

				ResultSet rsgetFiles = ad.selectQuery(getFiles);
				while (rsgetFiles.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rsgetFiles.getString("file_name"));
					fileList.add(linksForm);
				}
				request.setAttribute("listName", fileList);

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and  sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname=''  and year='"
						+ year
						+ "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='' and year='"
							+ year
							+ "' and month='" + month + "'";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}
				ArrayList videoList = new ArrayList();
				String sql3 = "select * from video_list where  sub_link_name='"
						+ sublink + "' and sub_sub_linkname=' ' and year='"
						+ year + "' and month='" + month + "'";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setVideoFilesList(rs12.getString("video_name"));
					videoList.add(linksForm);
				}
				request.setAttribute("videosList", videoList);
				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
			}
			// get sub sub link uploaded data
			if (!linkName.equalsIgnoreCase("") && !sublink.equalsIgnoreCase("")
					&& !subsublink.equalsIgnoreCase("")) {
				String link_id = linksForm.getLinkName();
				String subSubLink = linksForm.getSubSubLinkName();
				linksForm.setLinkName(link_id);
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);

				FormFile myFile = linksForm.getVideoFileNames();
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

					int checkVideoCount = 0;
					String checkVideoExist = "select count(*) from video_list where video_name='"
							+ fileName + "'";
					ResultSet rscheckVideoExist = ad
							.selectQuery(checkVideoExist);
					while (rscheckVideoExist.next()) {
						checkVideoCount = rscheckVideoExist.getInt(1);
					}
					if (checkVideoCount == 0) {
						String filePath = getServlet()
								.getServletContext()
								.getRealPath(
										"jsp/EMicro Files/News And Media/Archieves")
								+ "/"
								+ linkName
								+ "/"
								+ sublink
								+ "/"
								+ subSubLink + "/UploadVideos" + "/" + year;

						InputStream in = ConnectionFactory.class
								.getClassLoader().getResourceAsStream(
										"db.properties");
						Properties props = new Properties();
						props.load(in);
						in.close();
						String uploadFilePath = props
								.getProperty("file.uploadFilePath");
						filePath = uploadFilePath
								+ "/EMicro Files/News And Media/Archieves/"
								+ linkName + "/" + sublink + "/" + subSubLink
								+ "/UploadVideos" + "/" + year;

						File destinationDir = new File(filePath);
						if (!destinationDir.exists()) {
							destinationDir.mkdirs();
						}

						storeVideos(filePath, filesize, linkName, year,
								fileName, sublink, subSubLink);

						filePath = filePath.replace("\\", "\\");
						System.out.println("filePath=" + filePath);
						String insertsql = "insert into video_list(link_name,video_path,video_name,sub_link_name,sub_sub_linkname,year,month) values('"
								+ linkName
								+ "','"
								+ filePath
								+ "','"
								+ fileName
								+ "','"
								+ sublink
								+ "','"
								+ subSubLink
								+ "','"
								+ year
								+ "','"
								+ month
								+ "')";
						System.out.println("insertsql=" + insertsql);
						int a = ad.SqlExecuteUpdate(insertsql);
						if (a > 0) {
							linksForm
									.setMessage("Videos Uploaded Successfully");
						} else {
							linksForm
									.setMessage("Error While Uploading Videos ... Please check Entered Values");
						}
					} else {
						linksForm
								.setMessage("Video name is exist.Please Change the video file name");
					}
				} else {
					linksForm
							.setMessage("Please upload only avi,wmv,mpeg,flv,mp4,swf,webm videos ");
				}
				ArrayList fileList = new ArrayList();
				String getFiles = "select * from filelist where link_name='"
						+ linkName + "'and sub_link_name='" + sublink
						+ "' and sub_sub_linkname='" + subSubLink
						+ "' and year='" + year + "' and month='" + month + "'";

				ResultSet rsgetFiles = ad.selectQuery(getFiles);
				while (rsgetFiles.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rsgetFiles.getString("file_name"));
					fileList.add(linksForm);
				}
				request.setAttribute("listName", fileList);

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ linkName
						+ "' and  sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='"
						+ subSubLink
						+ "'  and year='"
						+ year
						+ "' and month='"
						+ month
						+ "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='"
							+ subSubLink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}
				ArrayList videoList = new ArrayList();
				String sql3 = "select * from video_list where link_name='"
						+ linkName + "' and sub_link_name='" + sublink
						+ "' and sub_sub_linkname='" + subSubLink
						+ "' and year='" + year + "' and month='" + month
						+ "' ";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setVideoFilesList(rs12.getString("video_name"));
					videoList.add(linksForm);
				}
				request.setAttribute("videosList", videoList);

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);
			}
			request.setAttribute("displaySublinkField", "displaySublinkField");
			linksForm.setLinkName(linkName);
			request.setAttribute("displayFields", "displayFields");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayMainLinks");

	}

	public ActionForward displaySublinks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ManageCMSForm linksForm = (ManageCMSForm) form;// TODO Auto-generated
														// method stub
		String linkName = linksForm.getLinkName();
		String id = request.getParameter("id");
		request.setAttribute("MenuIcon", id);
		try {
			LinksDao ad = new LinksDao();
			String sql = "select * from links where module='" + linkName
					+ "'  and sub_linkname is null and  delete_status=1";
			ResultSet rs = ad.selectQuery("select * from links where module='"
					+ linkName + "' and  delete_status=1 ");
			ArrayList subLinkValueList = new ArrayList();
			while (rs.next()) {
				subLinkValueList.add(rs.getString("link_name"));
			}
			linksForm.setSublinkValueList(subLinkValueList);
			request.setAttribute("subLinkValueListID", subLinkValueList);
			request.setAttribute("submitButton", "submitButton");
		} catch (Exception e) {
			e.printStackTrace();
		}
		displayMainLinks(mapping, form, request, response);
		return mapping.findForward("sublinkAjax");

	}

	public ActionForward displaySubSublinks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ManageCMSForm linksForm = (ManageCMSForm) form;// TODO Auto-generated
														// method stub
		String linkName = linksForm.getLinkName();

		String id = request.getParameter("id");
		request.setAttribute("MenuIcon", id);
		try {
			LinksDao ad = new LinksDao();
			String sql = "select * from links where module='" + linkName
					+ "'  and  delete_status is  null and sub_linkname='"
					+ linksForm.getSubLinkName() + "'  ";
			ResultSet rs = ad.selectQuery(sql);

			ArrayList subsubLinkValueList = new ArrayList();

			while (rs.next()) {

				subsubLinkValueList.add(rs.getString("link_name"));
			}

			linksForm.setSubsublinkValueList(subsubLinkValueList);
			request.setAttribute("subsubLinkValueListID", subsubLinkValueList);
			request.setAttribute("submitButton", "submitButton");
		} catch (Exception e) {
			e.printStackTrace();
		}

		displayMainLinks(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		String subSubLinkName = linksForm.getSubSubLinkName();
		linksForm.setSubSubLinkName(subSubLinkName);
		return mapping.findForward("subsublinkAjax");

	}

	public ActionForward uploadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			ManageCMSForm linksForm = (ManageCMSForm) form;
			LinksDao ad = new LinksDao();
			String linkName = linksForm.getLinkName();
			System.out.println("Link Name=" + linkName);
			String sublink = linksForm.getSubLinkName();
			String subsublink = linksForm.getSubSubLinkName();
			String year = linksForm.getYear();
			String month = linksForm.getMonth();
			// upload links files
			if (sublink.equalsIgnoreCase("") && subsublink.equalsIgnoreCase("")) {
				String link_id = linksForm.getLinkName();
				linksForm.setLinkName(link_id);
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);

				FormFile myFile = linksForm.getFileNames();
				String contentType = myFile.getContentType();
				String fileName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();

				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

				if (ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")
						|| ext.equalsIgnoreCase("xls")
						|| ext.equalsIgnoreCase("xlsx")
						|| ext.equalsIgnoreCase("ppt")
						|| ext.equalsIgnoreCase("pptx")
						|| ext.equalsIgnoreCase("pdf")
						|| ext.equalsIgnoreCase("rtf")
						|| ext.equalsIgnoreCase("txt")) {
					int checkfileCount = 0;
					String checkFileExist = "select count(*) from filelist where file_name='"
							+ fileName + "'";
					ResultSet rscheckFileExist = ad.selectQuery(checkFileExist);
					while (rscheckFileExist.next()) {
						checkfileCount = rscheckFileExist.getInt(1);
					}
					if (checkfileCount == 0) {
						String filePath = getServlet()
								.getServletContext()
								.getRealPath(
										"jsp/EMicro Files/News And Media/Archieves")
								+ "/"
								+ link_id
								+ "/UploadFiles"
								+ "/"
								+ linksForm.getYear();

						InputStream in = ConnectionFactory.class
								.getClassLoader().getResourceAsStream(
										"db.properties");
						Properties props = new Properties();
						props.load(in);
						in.close();
						String uploadFilePath = props
								.getProperty("file.uploadFilePath");
						filePath = uploadFilePath
								+ "/EMicro Files/News And Media/Archieves/"
								+ link_id + "/UploadFiles" + "/"
								+ linksForm.getYear();

						File destinationDir = new File(filePath);
						if (!destinationDir.exists()) {
							destinationDir.mkdirs();
						}
						File fileToCreate = new File(filePath, fileName);
						// error
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);
						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();

						filePath = filePath.replace("\\", "\\");
						System.out.println("filePath=" + filePath);

						String insertsql = "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname,year,month) values('"
								+ link_id
								+ "','"
								+ filePath
								+ "','"
								+ fileName
								+ "','','','"
								+ linksForm.getYear()
								+ "','"
								+ linksForm.getMonth() + "')";
						System.out.println("insertsql=" + insertsql);
						int a = ad.SqlExecuteUpdate(insertsql);
						if (a > 0) {
							linksForm
									.setMessage("Documents Uploaded Successfully");
						} else {
							linksForm
									.setMessage("Error While Uploading Files ... Please check Entered Values");
						}
					} else {
						linksForm
								.setMessage("Error..File Name is exist.Please  change file name");
					}
				} else {
					linksForm
							.setMessage("Please upload only doc,docx,xls,xlsx,ppt,pptx,pdf,rtf,txt");
				}
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ link_id
						+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
						+ linksForm.getYear() + "' and month='" + month + "'";
				String getVideoscount = "select count(*) from video_list where link_name='"
						+ link_id
						+ "' and  sub_link_name='' and sub_sub_linkname=''  and year='"
						+ linksForm.getYear() + "' and month='" + month + "'";
				String getVideos = "select * from video_list where link_name='"
						+ link_id + "' and sub_sub_linkname='' and year='"
						+ linksForm.getYear() + "' and month='" + month + "'";

				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);

				ArrayList videoList = new ArrayList();
				int videocount = 0;
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}

				request.setAttribute("displaySublinkField",
						"displaySublinkField");

				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ link_id
						+ "' and  sub_link_name='' and sub_sub_linkname=''  and year='"
						+ year + "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ link_id
							+ "' and sub_link_name='' and sub_sub_linkname='' and year='"
							+ year + "' and month='" + month + "'  ";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				request.setAttribute("displayFields", "displayFields");
			}
			// get sub link uploaded data
			if (!sublink.equalsIgnoreCase("")
					&& subsublink.equalsIgnoreCase("")) {
				String link_id = linksForm.getLinkName();
				linksForm.setLinkName(link_id);
				String content_description = "";

				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);
				FormFile myFile = linksForm.getFileNames();
				String contentType = myFile.getContentType();
				String fileName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

				if (ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")
						|| ext.equalsIgnoreCase("xls")
						|| ext.equalsIgnoreCase("xlsx")
						|| ext.equalsIgnoreCase("ppt")
						|| ext.equalsIgnoreCase("pptx")
						|| ext.equalsIgnoreCase("pdf")
						|| ext.equalsIgnoreCase("rtf")
						|| ext.equalsIgnoreCase("txt")) {

					int checkfileCount = 0;
					String checkFileExist = "select count(*) from filelist where file_name='"
							+ fileName + "'";
					ResultSet rscheckFileExist = ad.selectQuery(checkFileExist);
					while (rscheckFileExist.next()) {
						checkfileCount = rscheckFileExist.getInt(1);
					}
					if (checkfileCount == 0) {
						String filePath = getServlet()
								.getServletContext()
								.getRealPath(
										"jsp/EMicro Files/News And Media/Archieves")
								+ "/"
								+ link_id
								+ "/"
								+ sublink
								+ "/UploadFiles" + "/" + linksForm.getYear();

						InputStream in = ConnectionFactory.class
								.getClassLoader().getResourceAsStream(
										"db.properties");
						Properties props = new Properties();
						props.load(in);
						in.close();
						String uploadFilePath = props
								.getProperty("file.uploadFilePath");
						filePath = uploadFilePath
								+ "/EMicro Files/News And Media/Archieves/"
								+ link_id + "/" + sublink + "/UploadFiles"
								+ "/" + linksForm.getYear();

						File destinationDir = new File(filePath);
						if (!destinationDir.exists()) {
							destinationDir.mkdirs();
						}

						File fileToCreate = new File(filePath, fileName);
						System.out.println("filePath=" + filePath);
						System.out.println("fileName=" + fileName);
						System.out.println("fileToCreate=" + fileToCreate);

						// error
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);

						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();

						filePath = filePath.replace("\\", "\\");
						System.out.println("filePath=" + filePath);
						String insertsql = "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname,year,month) values('"
								+ link_id
								+ "','"
								+ filePath
								+ "','"
								+ fileName
								+ "','"
								+ sublink
								+ "','','"
								+ linksForm.getYear()
								+ "','"
								+ linksForm.getMonth() + "')";
						System.out.println("insertsql=" + insertsql);
						int a = ad.SqlExecuteUpdate(insertsql);
						if (a > 0) {
							linksForm
									.setMessage("Documents Uploaded Successfully");
						} else {
							linksForm
									.setMessage("Error While Uploading Files ... Please check Entered Values");
						}
					} else {
						linksForm
								.setMessage("Document is already exist.Please Change file name.");
					}
				} else {
					linksForm
							.setMessage("Please upload only doc,docx,xls,xlsx,ppt,pptx,pdf,rtf,txt");
				}
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ link_id + "' and sub_link_name='" + sublink
						+ "' and year='" + year + "' and month='" + month
						+ "'  and sub_sub_linkname=''";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);
				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ link_id
						+ "' and  sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname=''  and year='"
						+ year
						+ "' and month='" + month + "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ link_id
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='' and year='"
							+ year
							+ "' and month='" + month + "'";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}

				ArrayList videoList = new ArrayList();
				int videocount = 0;
				String getVideoscount = "select count(*) from video_list where  link_name='"
						+ link_id
						+ "' and sub_link_name='"
						+ sublink
						+ "' and year='"
						+ year
						+ "' and month='"
						+ month
						+ "' and sub_sub_linkname=''";
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					String getVideos = "select * from video_list where link_name='"
							+ link_id
							+ "'  and sub_link_name='"
							+ sublink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {

						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);
				request.setAttribute("displayFields", "displayFields");
			}
			// get sub sub link uploaded data
			if (!linkName.equalsIgnoreCase("") && !sublink.equalsIgnoreCase("")
					&& !subsublink.equalsIgnoreCase("")) {

				String link_id = linksForm.getLinkName();
				String subSubLink = linksForm.getSubSubLinkName();
				linksForm.setLinkName(link_id);
				String content_description = "";
				Enumeration<String> params = request
						.getParameterNames();
				String parameter;

				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}

				linksForm.setContentDescriptionAdmin(content_description);

				FormFile myFile = linksForm.getFileNames();
				String contentType = myFile.getContentType();
				String fileName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

				if (ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")
						|| ext.equalsIgnoreCase("xls")
						|| ext.equalsIgnoreCase("xlsx")
						|| ext.equalsIgnoreCase("ppt")
						|| ext.equalsIgnoreCase("pptx")
						|| ext.equalsIgnoreCase("pdf")
						|| ext.equalsIgnoreCase("rtf")
						|| ext.equalsIgnoreCase("txt")) {

					int checkfileCount = 0;
					String checkFileExist = "select count(*) from filelist where file_name='"
							+ fileName + "'";
					ResultSet rscheckFileExist = ad.selectQuery(checkFileExist);
					while (rscheckFileExist.next()) {
						checkfileCount = rscheckFileExist.getInt(1);
					}
					if (checkfileCount == 0) {
						String filePath = getServlet()
								.getServletContext()
								.getRealPath(
										"jsp/EMicro Files/News And Media/Archieves")
								+ "/"
								+ link_id
								+ "/"
								+ sublink
								+ "/"
								+ subSubLink
								+ "/UploadFiles"
								+ "/"
								+ linksForm.getYear();

						InputStream in = ConnectionFactory.class
								.getClassLoader().getResourceAsStream(
										"db.properties");
						Properties props = new Properties();
						props.load(in);
						in.close();
						String uploadFilePath = props
								.getProperty("file.uploadFilePath");
						filePath = uploadFilePath
								+ "/EMicro Files/News And Media/Archieves/"
								+ link_id + "/" + sublink + "/" + subSubLink
								+ "/UploadFiles" + "/" + linksForm.getYear();

						File destinationDir = new File(filePath);
						if (!destinationDir.exists()) {
							destinationDir.mkdirs();
						}

						File fileToCreate = new File(filePath, fileName);
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);
						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();

						filePath = filePath.replace("\\", "\\");
						System.out.println("filePath=" + filePath);
						String insertsql = "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname,year,month) values('"
								+ link_id
								+ "','"
								+ filePath
								+ "','"
								+ fileName
								+ "','"
								+ sublink
								+ "','"
								+ subSubLink
								+ "','"
								+ year + "','" + month + "')";
						System.out.println("insertsql=" + insertsql);
						int a = ad.SqlExecuteUpdate(insertsql);
						if (a > 0) {
							linksForm
									.setMessage("Documents Uploaded Successfully");
						} else {
							linksForm
									.setMessage("Error While Uploading Files ... Please check Entered Values");
						}
					} else {
						linksForm
								.setMessage("Document is already exist.Please Change file name.");
					}
				} else {
					linksForm
							.setMessage("Please upload only doc,docx,xls,xlsx,ppt,pptx,pdf,rtf,txt");
				}
				ArrayList list = new ArrayList();
				String sql3 = "select * from filelist where link_name='"
						+ link_id + "' and sub_link_name='" + sublink
						+ "' and sub_sub_linkname='" + subSubLink
						+ "' and year='" + year + "' and month='" + month
						+ "' ";
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					linksForm = new ManageCMSForm();
					linksForm.setFileList(rs12.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);
				int imageCount = 0;
				String getImageCount = "select count(*) from Imagelist where link_name='"
						+ link_id
						+ "' and  sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='"
						+ subSubLink
						+ "'  and year='"
						+ year
						+ "' and month='"
						+ month
						+ "'";
				ResultSet rsgetImagecount = ad.selectQuery(getImageCount);
				while (rsgetImagecount.next()) {
					imageCount = rsgetImagecount.getInt(1);
				}

				if (imageCount > 0) {
					ArrayList imageList = new ArrayList();
					String getImage = "select * from Imagelist where link_name='"
							+ link_id
							+ "' and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='"
							+ subSubLink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";

					ResultSet rs13 = ad.selectQuery(getImage);
					while (rs13.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setImageList(rs13.getString("file_name"));
						imageList.add(linksForm);
					}
					request.setAttribute("imageLists", imageList);
				}

				ArrayList videoList = new ArrayList();
				int videocount = 0;
				String getVideoscount = "select count(*) from video_list where  link_name='"
						+ link_id
						+ "' and sub_link_name='"
						+ sublink
						+ "' and sub_sub_linkname='"
						+ subSubLink
						+ "' and year='" + year + "' and month='" + month + "'";
				ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
				while (rsgetVideoscount.next()) {
					videocount = rsgetVideoscount.getInt(1);
				}
				if (videocount >= 1) {
					String getVideos = "select * from video_list where link_name='"
							+ link_id
							+ "'  and sub_link_name='"
							+ sublink
							+ "' and sub_sub_linkname='"
							+ subSubLink
							+ "' and year='"
							+ year
							+ "' and month='"
							+ month
							+ "'";
					ResultSet rsgetVideos = ad.selectQuery(getVideos);
					while (rsgetVideos.next()) {
						linksForm = new ManageCMSForm();
						linksForm.setVideoFilesList(rsgetVideos
								.getString("video_name"));
						videoList.add(linksForm);
					}
					request.setAttribute("videosList", videoList);
				}

				displayMainLinks(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				displaySubSublinks(mapping, form, request, response);

			}
			request.setAttribute("displaySublinkField", "displaySublinkField");
			request.setAttribute("displayFields", "displayFields");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayMainLinks");
	}

	public ActionForward displayLinksContent1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("displayLinksContent()----");
		ManageCMSForm linksForm = (ManageCMSForm) form;// TODO Auto-generated
														// method stub
		try {
			String module = request.getParameter("MenuIcon2");
			request.setAttribute("MenuIcon", module);

			String linksId = request.getParameter("sId");
			String linkName = request.getParameter("LinkName");

			System.out.println("Link Name=" + linkName);
			linksForm.setLinkName(linkName);

			try {
				LinksDao ad = new LinksDao();
				String sql = "select * from links where module='" + linkName
						+ "'  and sub_linkname is null and  delete_status=1";
				ResultSet rs = ad
						.selectQuery("select * from links where module='"
								+ linkName + "' and  delete_status=1 ");
				ArrayList subLinkValueList = new ArrayList();
				while (rs.next()) {
					subLinkValueList.add(rs.getString("link_name"));
				}
				linksForm.setSublinkValueList(subLinkValueList);
				request.setAttribute("subLinkValueListID", subLinkValueList);
				request.setAttribute("submitButton", "submitButton");
			} catch (Exception e) {
				e.printStackTrace();
			}
			String sublink = linksForm.getSubLinkName();

			String subsublink = linksForm.getSubSubLinkName();

			linksForm.setLinkName(linkName);
			linksForm.setSubLinkName(sublink);
			linksForm.setSubSubLinkName(subsublink);
			LinksDao adlinks = new LinksDao();
			int count = 0;
			String arcStatus = "";
			linksForm.setArchiveStatus("");
			linksForm.setContentDescriptionAdmin("");
			String year = linksForm.getYear();
			String month = linksForm.getMonth();
			if (year != null) {

				linkName = request.getParameter("LinkName");
				// links content display code

				String getArchievDataCount = "select count(*) from archieves where link_name='"
						+ linkName
						+ "'  and  year='"
						+ year
						+ "' and month='"
						+ month + "'";
				ResultSet rsArchievDataCount = adlinks
						.selectQuery(getArchievDataCount);
				while (rsArchievDataCount.next()) {
					count = rsArchievDataCount.getInt(1);
				}
				String files = "";
				String videos = "";
				String images = "";

				ArrayList fileList = new ArrayList();
				ArrayList imgeList = new ArrayList();

				ArrayList videoList = new ArrayList();
				ArrayList imageList = new ArrayList();
				if (count >= 1) {
					String getArchievData = "select * from archieves where link_name='"
							+ linkName
							+ "' and  year='"
							+ year
							+ "' and month='" + month + "'  ";
					System.out.println("getArchievData=" + getArchievData);
					ResultSet rsArchiveData = adlinks
							.selectQuery(getArchievData);
					String file;
					while (rsArchiveData.next()) {
						linksForm.setArchiveStatus(rsArchiveData
								.getString("status"));
						linksForm.setContentDescriptionAdmin(rsArchiveData
								.getString("content_description"));
						arcStatus = rsArchiveData.getString("status");
						if (arcStatus.equalsIgnoreCase("yes")) {
							linksForm.setArchiveStatus(rsArchiveData
									.getString("status"));
						}
						files = rsArchiveData.getString("file_name");
						if (files.equalsIgnoreCase("")) {

						} else {
							String v[] = files.split(",");
							int l = v.length;
							for (int i = 0; i < l; i++) {
								ManageCMSForm linksForm1 = new ManageCMSForm();
								int x = v[i].lastIndexOf("/");
								files = v[i].substring(x + 1);
								linksForm1.setFileList(files);

								/*
								 * String insertFiles =
								 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
								 * +linkName+"','','"+files+"','','')";
								 * System.out
								 * .println("insertFiles="+insertFiles);
								 * adlinks.SqlExecuteUpdate(insertFiles);
								 */

								fileList.add(linksForm1);
							}
						}
						images = rsArchiveData.getString("image_name");
						if (images.equalsIgnoreCase("")) {

						} else {
							String v[] = images.split(",");
							int l = v.length;
							for (int i = 0; i < l; i++) {
								ManageCMSForm linksForm1 = new ManageCMSForm();
								int x = v[i].lastIndexOf("/");
								files = v[i].substring(x + 1);
								linksForm1.setImageList(files);

								/*
								 * String insertFiles =
								 * "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
								 * +linkName+"','','"+files+"','','')";
								 * System.out
								 * .println("insertFiles="+insertFiles);
								 * adlinks.SqlExecuteUpdate(insertFiles);
								 */

								imgeList.add(linksForm1);
							}
						}

						videos = rsArchiveData.getString("video_name");
						if (videos.equalsIgnoreCase("")) {

						} else {
							String v1[] = videos.split(",");
							int l1 = v1.length;
							for (int i = 0; i < l1; i++) {
								ManageCMSForm linksFormVideo = new ManageCMSForm();
								int y = v1[i].lastIndexOf("/");
								videos = v1[i].substring(y + 1);
								linksFormVideo.setVideoFilesList(videos);
								/*
								 * String insertVideos =
								 * "insert into video_list(link_name,video_path,video_name,sub_link_name,sub_sub_linkname) values('"
								 * +linkName+"','','"+videos+"','','')";
								 * System.out
								 * .println("insertFiles="+insertVideos);
								 * adlinks.SqlExecuteUpdate(insertVideos);
								 */
								videoList.add(linksFormVideo);
							}
						}
					}
				}
				if (files.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("listName", fileList);

				}
				if (images.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("imageLists", imgeList);

				}
				if (videos.equalsIgnoreCase("")) {

				} else {
					request.setAttribute("videosList", videoList);
				}
				request.setAttribute("displaySublinkField",
						"displaySublinkField");
			}

			LinksDao ad = new LinksDao();
			if (linkName.equalsIgnoreCase("Help")) {

				String sql = "select * from links where link_name='" + linkName
						+ "' and " + "status is null  ";
				System.out.println("sql=" + sql);
				ResultSet rs = ad.selectQuery(sql);
				String linkId = null;
				while (rs.next()) {
					linkId = rs.getString("id");
					linksForm.setSubLinkName(rs.getString("link_name"));
					linksForm.setSubLinkId(linksId);
					linksForm.setLinkId(rs.getString("id"));
					linksForm.setLinkPath(rs.getString("link_path"));
					linksForm.setMethodName(rs.getString("method"));
					linksForm.setPriority(rs.getString("priority"));
					// linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
				}
				request.setAttribute("linkId", linkId);
				displayMainLinks(mapping, form, request, response);
				linksForm.setLinkName(linkName);

				return mapping.findForward("displayMainLinks");
			} else {

				String sql = "select * from links where link_name='" + linkName
						+ "' and " + "status is null and module='Main' ";
				System.out.println("sql=" + sql);
				ResultSet rs = ad.selectQuery(sql);
				String linkId = null;
				while (rs.next()) {
					linkId = rs.getString("id");
					linksForm.setSubLinkName(rs.getString("link_name"));
					linksForm.setSubLinkId(linksId);
					linksForm.setLinkId(rs.getString("id"));
					linksForm.setLinkPath(rs.getString("link_path"));
					linksForm.setMethodName(rs.getString("method"));
					linksForm.setPriority(rs.getString("priority"));
					// linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
				}
				request.setAttribute("linkId", linkId);

			}
			displayMainLinks(mapping, form, request, response);
			linksForm.setLinkName(linkName);
			// (mapping, form, request, response);

			// get sub link archive data

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("displayMainLinks");
	}

	public ActionForward displayMainLinks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		LinksDao ad = new LinksDao();
		System.out.println("displayMainLinks()----");
		ManageCMSForm linksForm = (ManageCMSForm) form;// TODO Auto-generated
														// method stub

		String linkName = linksForm.getLinkName();
		String subLinkName = linksForm.getSubLinkName();
		String linkPath = linksForm.getLinkPath();
		String methodName = linksForm.getMethodName();
		String priority = linksForm.getPriority();
		try {

			ResultSet rs = ad
					.selectQuery("select * from links where status is null");
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();

			while (rs.next()) {
				linkIdList.add(rs.getString("link_name"));
				linkValueList.add(rs.getString("link_name"));
			}
			linksForm.setLinkName(linkName);
			linksForm.setLinkIdList(linkIdList);
			linksForm.setLinkValueList(linkValueList);
			linksForm.setSubLinkName(subLinkName);
			linksForm.setLinkPath(linkPath);
			linksForm.setMethodName(methodName);
			linksForm.setPriority(priority);
			linksForm.setContentDescription("");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception caught =" + e.getMessage());
		}

		String id = request.getParameter("id");

		if (id != null) {

			String sql = "select * from links where module='" + id
					+ "' and status=1 ";
			ResultSet rs = ad.selectQuery(sql);
			try {
				LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

				while (rs.next()) {
					hm.put(rs.getString("link_path") + "?method="
							+ rs.getString("method") + "&sId="
							+ rs.getString("id"),
							(rs.getString("link_name") + ',' + rs
									.getString("icon_name")));
				}
				request.setAttribute("SUBLINKS", hm);
			} catch (SQLException se) {
				se.printStackTrace();
			}

		}

		LinkedHashMap<String, String> hm = (LinkedHashMap<String, String>) request
				.getAttribute("SUBLINKS");

		request.setAttribute("SUBLINKS", hm);

		String sql = "select * from links where module is null and  status is null ";
		System.out.println("sql=" + sql);
		ResultSet rs = ad.selectQuery(sql);
		try {

			while (rs.next()) {
				linkName = rs.getString("link_name");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (linkName.equalsIgnoreCase("Help")) {

		} else {

		}
		return mapping.findForward("displayMainLinks");

	}

	public ActionForward displayMenuList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		LinksDao ad = new LinksDao();
		System.out.println("displayMainLinks()----");
		ManageCMSForm linksForm = (ManageCMSForm) form;
		linksForm.setArchiveType("Unarchive");
		// TODO Auto-generated
														// method stub
		/*
		 * try { ResultSet rs = ad.selectQuery(
		 * "select * from links where  module='Main'  or module is null and status is null"
		 * ); ArrayList linkValueList = new ArrayList(); while(rs.next()) {
		 * ManageCMSForm form2=new ManageCMSForm();
		 * form2.setLinkName(rs.getString("link_name"));
		 * linkValueList.add(form2); } request.setAttribute("menuList",
		 * linkValueList); rs.close(); ad.connClose(); }catch (Exception e) {
		 * e.printStackTrace(); }
		 */

		displayUnarchive(mapping, linksForm, request, response);
		System.out.println(linksForm.getArchiveType());

		return mapping.findForward("menuList");
	}

}
