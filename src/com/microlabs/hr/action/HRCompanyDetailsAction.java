package com.microlabs.hr.action;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.microlabs.admin.dao.LinksDao;
import com.microlabs.admin.form.LinksForm;
import com.microlabs.hr.dao.HRDao;
import com.microlabs.hr.form.HRCompanyDetailsForm;
import com.microlabs.utilities.UserInfo;

public class HRCompanyDetailsAction  extends DispatchAction{
	
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("HRCompanyDetailsAction----display()");
		
		HRCompanyDetailsForm hrLinksForm = (HRCompanyDetailsForm) form;// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String linkName = hrLinksForm.getLinkName();
		String subLinkName = hrLinksForm.getSubLinkName();
		String linkPath = hrLinksForm.getLinkPath();
		String methodName = hrLinksForm.getMethodName();
		String priority = hrLinksForm.getPriority();
		try {
			HRDao ad = new HRDao();
			ResultSet rs = ad.selectQuery("select * from links where status is null");
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
			
			while(rs.next()) {
				linkIdList.add(rs.getString("link_name"));
				linkValueList.add(rs.getString("link_name"));
			}
			
			hrLinksForm.setLinkName(linkName);
			hrLinksForm.setLinkIdList(linkIdList);
			hrLinksForm.setLinkValueList(linkValueList);
			hrLinksForm.setSubLinkName(subLinkName);
			hrLinksForm.setLinkPath(linkPath);
			hrLinksForm.setMethodName(methodName);
			hrLinksForm.setPriority(priority);
			hrLinksForm.setContentDescription("");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception caught =" + e.getMessage());
		}
		
		
		String id="HR"; 		
	
		
		HRDao ad=new HRDao();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		
		String sql="select * from links where id in("+user.getIncludeSubLinks()+")";
		
		
		System.out.println("SQL is *********************************"+sql);
		
		
		ResultSet rs=ad.selectQuery(sql);
		
		try{
			
			LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			
			
			 while(rs.next()){
				 
				 String sql1="select * from links where module='"+id+"' " +
				 		"and id ='"+rs.getString("id")+"'";
				 	
				 ResultSet rs1=ad.selectQuery(sql1);
				 
				 if(rs1.next()){
					 
				 hm.put(rs1.getString("link_path")+"?method="+rs1.getString("method")+"&sId="+rs1.getString("link_name")+"&id="+rs1.getString("module"), rs1.getString("link_name"));
				 }
				 hrLinksForm.setContentDescription("  ");
			}
			 
			 session.setAttribute("SUBLINKS", hm);
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
		
		
		
		return mapping.findForward("display");
	}
	
	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		HRCompanyDetailsForm hrLinksForm = (HRCompanyDetailsForm) form;// TODO Auto-generated method stub
		String linkName = hrLinksForm.getLinkName();
		String subLinkName = hrLinksForm.getSubLinkName();
		
		
		String linkPath = "hr.do";
		String methodName = "display1";
		
		
		String priority = hrLinksForm.getPriority();
		HttpSession session = request.getSession();
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
		
		try {
			HRDao ad = new HRDao();
			
			FormFile myFile = hrLinksForm.getIconNames();
		    String contentType = myFile.getContentType();
			String iconName = myFile.getFileName();
			byte[] fileData = myFile.getFileData();
			String ext = iconName.substring(iconName.lastIndexOf('.') + 1);
			   if(ext.equalsIgnoreCase("jpeg")||(ext.equalsIgnoreCase("png"))||(ext.equalsIgnoreCase("jpg"))||(ext.equalsIgnoreCase("gif")))
			   {
			String iconPath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+subLinkName+"/UploadIcons";
			File destinationDir = new File(iconPath);
			if(!destinationDir.exists())
			{
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
			iconPath = iconPath.replace("\\", "\\\\");
		  }
			String iconList="cms" + "/" + linkName+"/"+subLinkName+"/UploadIcons" + "/"+ iconName;
			
			String fileList="";
			ResultSet rs4 = ad.selectQuery("SELECT * FROM filelist where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ subLinkName
							+ "'");
			while (rs4.next()) {
				fileList+="cms" + "/" + linkName+"/"+subLinkName+"/UploadFiles" + "/"+ rs4.getString("file_name")+",";
			}
			String videoList="";
			ResultSet rs5 = ad.selectQuery("SELECT * FROM video_list where link_name='"
							+ linkName
							+ "' and sub_link_name='"
							+ subLinkName							
							+ "'");
			while (rs5.next()) {
				videoList+="cms" + "/" + linkName+"/"+subLinkName+"/UploadVideos" + "/"+ rs5.getString("video_name")+",";
			}
			if(!fileList.equalsIgnoreCase("")){
				fileList=fileList.substring(0, fileList.lastIndexOf(","));
			}
			if(!videoList.equalsIgnoreCase("")){
				videoList=videoList.substring(0, videoList.lastIndexOf(","));
			}
				String sql = "insert into links(module,link_name," +
				"link_path,method,priority,status,delete_status,content_description,file_name,video_name,icon_name) values('"
			+ linkName
			+ "','"
			+ subLinkName
			+ "','"
			+ linkPath
			+ "','"
			+ methodName+"',"+priority+",1,1,'"+content_description+"','"+fileList+"','"+videoList+"','"+iconList+"')";
	int a=ad.SqlExecuteUpdate(sql);
	 if (a>0) {
		 hrLinksForm.setMessage("Admin Fck Editor Details Submitted  Successfully");
		 String deletesql = "delete from filelist where link_name='"
				+ linkName + "' and sub_link_name='" + subLinkName+ "'";
		String deletesql1 = "delete from video_list where link_name='"
				+ linkName + "' and sub_link_name='" + subLinkName+ "'";
		
		ad.SqlExecuteUpdate(deletesql);
		ad.SqlExecuteUpdate(deletesql1);
		
		hrLinksForm.setLinkName("");
		hrLinksForm.setSubLinkName("");
		hrLinksForm.setUnderSubLinks("");
		hrLinksForm.setLinkPath("");
		hrLinksForm.setMethodName("");
		hrLinksForm.setPriority("");
	 } 
	 else 
	 {
		 hrLinksForm.setMessage("Error While  Adding Admin Fck Editor Details.. Please check Entered Values");
	 }			
		} catch (Exception e) {
			e.printStackTrace();
		}
		display(mapping, form, request, response);
		return mapping.findForward("display");
	}
	
	
	public ActionForward displaySublinks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HRCompanyDetailsForm hrLinksForm = (HRCompanyDetailsForm) form;// TODO Auto-generated method stub
		String linkName = hrLinksForm.getLinkName();
		try {
			HRDao ad = new HRDao();
			ResultSet rs = ad.selectQuery("select * from links where module='"
							+linkName+"'  and delete_status=1 " );
			ArrayList a1 = new ArrayList();
			LinksForm linksForm1 = null;
			while (rs.next()) {
				linksForm1 = new LinksForm();
				linksForm1.setLinkName(linkName);
				linksForm1.setSubLinkName(rs.getString("link_name"));
				linksForm1.setSubLinkId(rs.getString("link_name"));
				linksForm1.setMethodName(rs.getString("method"));
				linksForm1.setPriority(rs.getString("priority"));
				a1.add(linksForm1);
			}
			hrLinksForm.setLinkName(linkName);
			request.setAttribute("listDetails", a1);
			request.setAttribute("displaySublinkField", "displaySublinkField");
			request.setAttribute("submitButton", "submitButton");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpSession session=request.getSession();
		
		LinkedHashMap<String, String> hm= (LinkedHashMap<String, String>)session.getAttribute("SUBLINKS");
		
		
		session.setAttribute("SUBLINKS", hm);
		
		
		display(mapping, form, request, response);
		return mapping.findForward("display");
	}
	
	
	public ActionForward displayLinksContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
		System.out.println("displayLinksContent()----");
		HRCompanyDetailsForm hrLinksForm = (HRCompanyDetailsForm) form;// TODO Auto-generated method stub
		
		
		String linksId = request.getParameter("sId");
		String linkName = hrLinksForm.getLinkName();
		System.out.println("Link Name="+linkName);
		
		
		hrLinksForm.setLinkName(linkName);
		HRDao ad = new HRDao();
		HttpSession session=request.getSession();
		try 
		{
			LinksDao adlinks = new LinksDao();
			if(linkName.equalsIgnoreCase("Login Help")){
			
				
				String sql="select * from links where link_name='"+linkName + "' and " +
				"status is null  ";
		System.out.println("sql="+sql);
		ResultSet rs = ad.selectQuery(sql);
		String linkId=null;
		while (rs.next()) {
			linkId=rs.getString("id");
			hrLinksForm.setSubLinkName(rs.getString("link_name"));
			hrLinksForm.setSubLinkId(linksId);
			hrLinksForm.setLinkId(rs.getString("id"));
			hrLinksForm.setLinkPath(rs.getString("link_path"));
			hrLinksForm.setMethodName(rs.getString("method"));
			hrLinksForm.setPriority(rs.getString("priority"));
			hrLinksForm.setContentDescriptionAdmin(rs.getString("content_description"));	
		}
		session.setAttribute("linkId",linkId);
		displayMainLinks(mapping, form, request, response);
		return mapping.findForward("displayMainLinks");	
			}
			else{
			
			
			
			String sql="select * from links where link_name='"+linkName + "' and " +
					"status is null and module='Main' ";
			System.out.println("sql="+sql);
			ResultSet rs = ad.selectQuery(sql);
			String linkId=null;
			while (rs.next()) {
				linkId=rs.getString("id");
				hrLinksForm.setSubLinkName(rs.getString("link_name"));
				hrLinksForm.setSubLinkId(linksId);
				hrLinksForm.setLinkId(rs.getString("id"));
				hrLinksForm.setLinkPath(rs.getString("link_path"));
				hrLinksForm.setMethodName(rs.getString("method"));
				hrLinksForm.setPriority(rs.getString("priority"));
				hrLinksForm.setContentDescriptionAdmin(rs.getString("content_description"));	
			}
			session.setAttribute("linkId",linkId);
			ArrayList list = new ArrayList();
			String sql3="select * from links where link_name='"+linkName + "' and " +
					"status is null and module='Main' and file_name not like ''";
			ResultSet rs5 = adlinks.selectQuery(sql3);
			while (rs5.next()){
				hrLinksForm = new HRCompanyDetailsForm();
				hrLinksForm.setFileList(rs5.getString("file_name"));
				list.add(hrLinksForm);
			}
			request.setAttribute("listName", list);
			ArrayList list1 = new ArrayList();
			String sql4="select * from links where link_name='"+linkName + "' and " +
					"status is null and module='Main' and video_name not like ''";
			ResultSet rs6 = adlinks.selectQuery(sql4);
			while (rs6.next()){
				hrLinksForm = new HRCompanyDetailsForm();
				hrLinksForm.setVideoFilesList(rs6.getString("video_name"));
				list1.add(hrLinksForm);
			}
			request.setAttribute("listName1", list1);
			request.setAttribute("displaySublinkField", "displaySublinkField");
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		displaySublinks(mapping, form, request, response);
		display(mapping, form, request, response);
		
		
		
		
		return mapping.findForward("displayMainLinks");
	}
	
	public ActionForward displayMainLinks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
		HRDao ad = new HRDao();
		System.out.println("displayMainLinks()----");
		HRCompanyDetailsForm hrLinksForm = (HRCompanyDetailsForm) form;// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String linkName = hrLinksForm.getLinkName();
		String subLinkName = hrLinksForm.getSubLinkName();
		String linkPath = hrLinksForm.getLinkPath();
		String methodName = hrLinksForm.getMethodName();
		String priority = hrLinksForm.getPriority();
		try {
		
			ResultSet rs = ad.selectQuery("select * from links where status is null");
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
			
			while(rs.next()) {
				linkIdList.add(rs.getString("link_name"));
				linkValueList.add(rs.getString("link_name"));
			}
			
			hrLinksForm.setLinkName(linkName);
			hrLinksForm.setLinkIdList(linkIdList);
			hrLinksForm.setLinkValueList(linkValueList);
			hrLinksForm.setSubLinkName(subLinkName);
			hrLinksForm.setLinkPath(linkPath);
			hrLinksForm.setMethodName(methodName);
			hrLinksForm.setPriority(priority);
			hrLinksForm.setContentDescription("");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception caught =" + e.getMessage());
		}
		
		
		
		String id = request.getParameter("id");
		
		
		if(id!=null){
		
		
		String sql = "select * from links where module='" + id + "' and status=1 ";
		ResultSet rs = ad.selectQuery(sql);
		try {
			LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();
			
			while(rs.next()) {
				hm.put(rs.getString("link_path")+"?method="+rs.getString("method") + "&sId="+rs.getString("id"),rs.getString("link_name"));
			}
			session.setAttribute("SUBLINKS", hm);
		} catch (SQLException se) {
			se.printStackTrace();
		}
		
		}
		
		
		LinkedHashMap<String, String> hm= (LinkedHashMap<String, String>)session.getAttribute("SUBLINKS");
		
		
		session.setAttribute("SUBLINKS", hm);
		
		String sql="select * from links where module is null and  status is null ";
System.out.println("sql="+sql);
ResultSet rs = ad.selectQuery(sql);
try{

while (rs.next()) {
	linkName=rs.getString("link_name");
}
	
}catch (Exception e) {
	e.printStackTrace();
	}
		if(linkName.equalsIgnoreCase("Login Help")){
			
		}else{
		request.setAttribute("displaySublinkField", "displaySublinkField");
		}
		return mapping.findForward("displayMainLinks");
	}
	
	
public ActionForward selectContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
{
	    
	HRCompanyDetailsForm hrLinksForm = (HRCompanyDetailsForm) form;// TODO Auto-generated method stub
		System.out.println("********SELECT METHOD========");
		String linksId = request.getParameter("sId");
		String linkName = request.getParameter("lId");
		hrLinksForm.setLinkName(linkName);
		HRDao ad = new HRDao();
		HttpSession session=request.getSession();
		try 
		{
			LinksDao adlinks = new LinksDao();
			String sql="select * from links where link_name='"+linksId + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'";
			ResultSet rs = ad.selectQuery(sql);
			String linkId=null;
			while (rs.next()) {
				linkId=rs.getString("id");
				hrLinksForm.setSubLinkName(rs.getString("link_name"));
				hrLinksForm.setSubLinkId(linksId);
				hrLinksForm.setLinkId(rs.getString("id"));
				hrLinksForm.setLinkPath(rs.getString("link_path"));
				hrLinksForm.setMethodName(rs.getString("method"));
				hrLinksForm.setPriority(rs.getString("priority"));
				hrLinksForm.setContentDescriptionAdmin(rs.getString("content_description"));	
				hrLinksForm.setIconList(rs.getString("icon_name"));	
			}
			session.setAttribute("linkId",linkId);
			ArrayList list = new ArrayList();
			String sql3="select * from links where link_name='"+linksId + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";
			ResultSet rs5 = adlinks.selectQuery(sql3);
			while (rs5.next()){
				hrLinksForm = new HRCompanyDetailsForm();
				hrLinksForm.setFileList(rs5.getString("file_name"));
				list.add(hrLinksForm);
			}
			request.setAttribute("listName", list);
			ArrayList list1 = new ArrayList();
			String sql4="select * from links where link_name='"+linksId + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"' and video_name not like ''";
			ResultSet rs6 = adlinks.selectQuery(sql4);
			while (rs6.next())
                            {
				hrLinksForm = new HRCompanyDetailsForm();
				hrLinksForm.setVideoFilesList(rs6.getString("video_name"));
				list1.add(hrLinksForm);
			}
			request.setAttribute("listName1", list1);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		displaySublinks(mapping, form, request, response);
		display(mapping, form, request, response);
		return mapping.findForward("display1");
	}
	
	
	
	public ActionForward uploadFiles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HRCompanyDetailsForm hrLinksForm = (HRCompanyDetailsForm) form;
		try {
			System.out.println("uploadFiles()------");
			String link_id = hrLinksForm.getLinkName();
			String sub_link_name = hrLinksForm.getSubLinkName();
			HRDao adlinks = new HRDao();
			String linkId = hrLinksForm.getLinkName();
			String subLinkName = request.getParameter("subLinkName");

			String sql4="select * from links where module='"
				+link_id+"'  and delete_status=1 ";
			ResultSet rs6 = adlinks
					.selectQuery(sql4);
	         String sublinks="";
	         ArrayList listnew=new ArrayList();
			while (rs6.next())
			{
				sublinks=rs6.getString("link_name");
				listnew.add(sublinks);
			}
			if(listnew.contains(subLinkName))
			{
				hrLinksForm.setMessage("Sub Link Name already taken.choose another name");
			}
			else
			{
			FormFile myFile = hrLinksForm.getFileNames();
		    String contentType = myFile.getContentType();
			String fileName = myFile.getFileName();
			byte[] fileData = myFile.getFileData();
			String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			 if( hrLinksForm.getFileNames().getFileSize()== 0){
				 hrLinksForm.setMessage("Please choose a TXT,DOC,DOCX,PDF file to Upload");
				 display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select file_name from filelist where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'";
					ResultSet rs5 = adlinks.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						hrLinksForm = new HRCompanyDetailsForm();
						hrLinksForm.setFileList(rs5.getString("file_name"));
						list.add(hrLinksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql7="select video_name from video_list where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'";
					ResultSet rs16 = adlinks
							.selectQuery(sql7);
					while (rs16.next())
	                                {
						hrLinksForm = new HRCompanyDetailsForm();
						hrLinksForm.setVideoFilesList(rs16.getString("video_name"));
						list1.add(hrLinksForm);
					}
					request.setAttribute("listName1", list1);	
			     return mapping.findForward("display");
			    }
			   if(ext.equalsIgnoreCase("txt")||(ext.equalsIgnoreCase("doc"))||(ext.equalsIgnoreCase("docx"))||(ext.equalsIgnoreCase("pdf")))
			   {
			String filePath = getServlet().getServletContext().getRealPath("cms") + "/" + link_id+"/"+sub_link_name+"/UploadFiles";
			System.out.println("filePath="+filePath);
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
					fileOutStream.write(myFile.getFileData());
					fileOutStream.flush();
					fileOutStream.close();
				}
			}
			request.setAttribute("fileName", fileName);
			filePath = filePath.replace("\\", "\\\\");
			String sql9="select count(*) from filelist where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"' and file_name='"+fileName+"'";
			ResultSet rs15 = adlinks.selectQuery(sql9);
			int fileCount=0;
			while (rs15.next())
			{
				fileCount=Integer.parseInt(rs15.getString(1));
			}
			if(fileCount>0)
			{
				hrLinksForm.setMessage("File aleardy uploaded..please choose another file");
			}
			else
			{
			String insertsql = "insert into filelist(link_name,sub_link_name,file_path,file_name) values('"
					+link_id
					+"','"
					+sub_link_name
					+"','"
					+filePath
					+"','"+fileName+"')";
			int a = adlinks.SqlExecuteUpdate(insertsql);
			if (a > 0)
			{
				hrLinksForm.setMessage("Documents Uploaded Successfully");
				ArrayList list1 = new ArrayList();
				ResultSet rs7 = adlinks.selectQuery("select video_name from video_list where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'");
				while (rs7.next()) {
					hrLinksForm = new HRCompanyDetailsForm();
					hrLinksForm.setVideoFilesList(rs7.getString("video_name"));
					list1.add(hrLinksForm);
				}
				request.setAttribute("listName1", list1);
			} else {
				hrLinksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
			}
			}
			ArrayList list = new ArrayList();
			ResultSet rs5 = adlinks.selectQuery("select file_name from filelist where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'");
			while (rs5.next()) {
				hrLinksForm = new HRCompanyDetailsForm();
				hrLinksForm.setFileList(rs5.getString("file_name"));
				list.add(hrLinksForm);
			}
			request.setAttribute("listName", list);
			   }
				else
				{
					hrLinksForm.setMessage("Only TXT,DOC,DOCX,PDF File Format is Allowed");
					display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select file_name from filelist where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'";
					ResultSet rs5 = adlinks.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						hrLinksForm = new HRCompanyDetailsForm();
						hrLinksForm.setFileList(rs5.getString("file_name"));
						list.add(hrLinksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql8="select video_name from video_list where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'";
					ResultSet rs18 = adlinks
							.selectQuery(sql8);
					while (rs18.next())
	                                {
						hrLinksForm = new HRCompanyDetailsForm();
						hrLinksForm.setVideoFilesList(rs18.getString("video_name"));
						list1.add(hrLinksForm);
					}
					request.setAttribute("listName1", list1);	
			 	   return mapping.findForward("display");
				}
				ArrayList list1 = new ArrayList();
			   String sql8="select video_name from video_list where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'";	
						ResultSet rs18 = adlinks
								.selectQuery(sql8);
						while (rs18.next())
		                                {
							hrLinksForm = new HRCompanyDetailsForm();
							hrLinksForm.setVideoFilesList(rs18.getString("video_name"));
							list1.add(hrLinksForm);
						}
						request.setAttribute("listName1", list1);	
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("display");
	}

	
	public ActionForward uploadVideos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HRCompanyDetailsForm linksForm = (HRCompanyDetailsForm) form;
		try {
			System.out.println("UPLOAD METHOD");
			String link_id = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			HRDao adlinks = new HRDao();
			String linkId = linksForm.getLinkName();
			String subLinkName = request.getParameter("subLinkName");
			String sql4="select * from links where module='"
				+link_id+"'  and delete_status=1 ";
			ResultSet rs6 = adlinks
					.selectQuery(sql4);
             String sublinks="";
             ArrayList listnew=new ArrayList();
				while (rs6.next())
				{
					sublinks=rs6.getString("link_name");
					listnew.add(sublinks);
				}
			if(listnew.contains(subLinkName))
			{
				linksForm.setMessage("Sub Link Name already taken.choose another name");
			}
			else
			{
			FormFile myFile = linksForm.getVideoFileNames();
			String contentType = myFile.getContentType();
			String videoName = myFile.getFileName();
			byte[] fileData = myFile.getFileData();
			String ext = videoName.substring(videoName.lastIndexOf('.') + 1);
			 if( linksForm.getVideoFileNames().getFileSize()== 0){
				 linksForm.setMessage("Please choose a Video file(MP4,OGV)to Upload");
				 display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select file_name from filelist where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'";
					ResultSet rs5 = adlinks.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql5="select video_name from video_list where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'";
					ResultSet rs11 = adlinks
							.selectQuery(sql5);
					while (rs11.next())
	                                {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs11.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);	
			     return mapping.findForward("display");
			    }
			   if(ext.equalsIgnoreCase("mp4")||(ext.equalsIgnoreCase("ogv")))
			   {	
			String videoPath = getServlet().getServletContext().getRealPath("cms") + "/" + link_id+"/"+sub_link_name+"/UploadVideos";
			File destinationDir = new File(videoPath);
			if(!destinationDir.exists())
			{
				destinationDir.mkdirs();
			}
			if (!videoName.equals("")) {
				File fileToCreate = new File(videoPath, videoName);
				if (!fileToCreate.exists()) {
					FileOutputStream fileOutStream = new FileOutputStream(
							fileToCreate);
					fileOutStream.write(myFile.getFileData());
					fileOutStream.flush();
					fileOutStream.close();
				}
			}
			request.setAttribute("videoName", videoName);
			videoPath = videoPath.replace("\\", "\\\\");
			String sql9="select count(*) from video_list where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"' and video_name='"+videoName+"'";
			ResultSet rs5 = adlinks.selectQuery(sql9);
			int videoCount=0;
			while (rs5.next())
			{
				videoCount=Integer.parseInt(rs5.getString(1));
			}
			if(videoCount>0)
			{
				linksForm.setMessage("Video aleardy uploaded..please choose another video");
			}
			else
			{
			String insertsql = "insert into video_list(link_name, sub_link_name, video_path, " +
					"video_name)"
					+" values('"
					+link_id
					+"','"
					+sub_link_name
					+ "','"
					+videoPath
					+ "','"+videoName+"')";
			int a = adlinks.SqlExecuteUpdate(insertsql);
			if (a > 0) 
			{
				linksForm.setMessage("Video Files Uploaded Successfully");	
				ArrayList list = new ArrayList();
				String sql3="select file_name from filelist where link_name='"+link_id+"' and " +
 		"sub_link_name='"+sub_link_name+"'";
				ResultSet rs15 = adlinks.selectQuery(sql3);
				while (rs15.next()) 
                               {
					linksForm = new HRCompanyDetailsForm();
					linksForm.setFileList(rs15.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);
			} 
			else 
			{
				linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
			}
			}
			ArrayList list1 = new ArrayList();
			
			ResultSet rs7 = adlinks.selectQuery("select video_name from video_list where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'");
			while (rs7.next()) {
				linksForm = new HRCompanyDetailsForm();
				linksForm.setVideoFilesList(rs7.getString("video_name"));
				list1.add(linksForm);
			}
			request.setAttribute("listName1", list1);
			   }
				else
				{
					linksForm.setMessage("Only Video(MP4,OGV) File Format is Allowed");
					display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select file_name from filelist where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'";
					ResultSet rs5 = adlinks.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql8="select video_name from video_list where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'";
					ResultSet rs9 = adlinks
							.selectQuery(sql8);
					while (rs9.next())
	                                {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs9.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);	
			     return mapping.findForward("display");
				}
			   ArrayList list = new ArrayList();
				String sql3="select file_name from filelist where link_name='"+link_id+"' and " +
 		"sub_link_name='"+sub_link_name+"'";
				ResultSet rs5 = adlinks.selectQuery(sql3);
				while (rs5.next()) 
                               {
					linksForm = new HRCompanyDetailsForm();
					linksForm.setFileList(rs5.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("display");
	}
	

	
	
	
	public ActionForward deleteFileList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HRCompanyDetailsForm linksForm = (HRCompanyDetailsForm) form;// TODO
		try {
			HRDao adlinks = new HRDao();
		String link_id = linksForm.getLinkName();
		String sub_link_name = linksForm.getSubLinkName();
		String linkId = linksForm.getLinkName();
		String subLinkName = request.getParameter("subLinkName");
			String deleteArray[] = linksForm.getSelect();
			String selected = "";
			if (deleteArray != null) {
				if (deleteArray.length == 1) {
					selected = "'" + deleteArray[0] + "'";
				} else {
					for (int i = 0; i < deleteArray.length; i++) {
						selected += "'" + deleteArray[i] + "'";
						if (i < deleteArray.length - 1) {
							selected += ",";
						}
					}
				}
			}
			String deletesql = "delete from  filelist where file_name IN(" + selected+ ")";
			int a = adlinks.SqlExecuteUpdate(deletesql);
			if (a > 0) {
				linksForm.setMessage("Files Deleted Successfully");
			} else {
				linksForm.setMessage("Error While Deleting Files ...");
			}
			ArrayList list = new ArrayList();
			ResultSet rs5 = adlinks.selectQuery("select file_name from filelist where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'");
			while (rs5.next()) {

				linksForm = new HRCompanyDetailsForm();
				linksForm.setFileList(rs5.getString("file_name"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			ArrayList list1 = new ArrayList();
			String sql4="select video_name from video_list where link_name='"+link_id+"' and " +
 		"sub_link_name='"+sub_link_name+"'";
			ResultSet rs6 = adlinks
					.selectQuery(sql4);
			while (rs6.next())
                            {
				linksForm = new HRCompanyDetailsForm();
				linksForm.setVideoFilesList(rs6.getString("video_name"));
				list1.add(linksForm);
			}
			request.setAttribute("listName1", list1);
		} catch (Exception e) {
			System.out.println("Exception caught =" + e.getMessage());
		}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("display");
	}
	
	
	
	public ActionForward deleteVideoList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HRCompanyDetailsForm linksForm = (HRCompanyDetailsForm) form;// TODO
		try {
			HRDao adlinks = new HRDao();
		String link_id = linksForm.getLinkName();
		String sub_link_name = linksForm.getSubLinkName();
		String linkId = linksForm.getLinkName();
		String subLinkName = request.getParameter("subLinkName");
			String deleteArray[] = linksForm.getSelect1();
			String selected = "";
			if (deleteArray != null) {
				if (deleteArray.length == 1) {
					selected = "'" + deleteArray[0] + "'";
				} else {
					for (int i = 0; i < deleteArray.length; i++) {
						selected += "'" + deleteArray[i] + "'";
						if (i < deleteArray.length - 1) {
							selected += ",";
						}
					}
				}
			}
			String deletesql = "delete from  video_list where video_name IN(" + selected+ ")";
			int a = adlinks.SqlExecuteUpdate(deletesql);
			if (a > 0) {
				linksForm.setMessage("Video Files Deleted Successfully");
			} else {
				linksForm.setMessage("Error While Deleting Video Files ...");
			}
           ArrayList list = new ArrayList();
			ResultSet rs5 = adlinks.selectQuery("select file_name from filelist where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'");
			while (rs5.next()) {

				linksForm = new HRCompanyDetailsForm();
				linksForm.setFileList(rs5.getString("file_name"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			ArrayList list1 = new ArrayList();
			ResultSet rs6 = adlinks.selectQuery("select video_name from video_list where link_name='"+link_id+"' and " +
 		"sub_link_name='"+sub_link_name+"'");
			while (rs6.next()) {
				linksForm = new HRCompanyDetailsForm();
				linksForm.setVideoFilesList(rs6.getString("video_name"));
				list1.add(linksForm);
			}
			request.setAttribute("listName1", list1);
		} catch (Exception e) {
			System.out.println("Exception caught =" + e.getMessage());
		}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("display");
	}
	
	
	
	public ActionForward uploadFilesModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{
		HRCompanyDetailsForm linksForm = (HRCompanyDetailsForm) form;;
		try {
			HRDao ad = new HRDao();
			System.out.println("UPLOAD FILE MODIFY  METHOD");
			String linkName = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			linksForm.setLinkName(linkName);
			linksForm.setSubLinkName(sub_link_name);
		 String sql6="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"'";
		 ResultSet rs6=ad.selectQuery(sql6);
		 String linkPath=null;
		 String method=null;
		 String priority=null;
		 String contentDescription=null;
		 while (rs6.next()) 
		 {
			 linksForm.setLinkPath(rs6.getString("link_path"));           
			 linksForm.setMethodName(rs6.getString("method"));
			 linksForm.setPriority(rs6.getString("priority")); 
			 contentDescription=rs6.getString("content_description");
		}
		 linksForm.setContentDescriptionAdmin(contentDescription);	 
			FormFile myFile = linksForm.getFileNames();
			String contentType = myFile.getContentType();
			String fileName = myFile.getFileName();
			byte[] fileData = myFile.getFileData();
			String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			 if( linksForm.getFileNames().getFileSize()== 0){
				 linksForm.setMessage("Please choose a TXT,DOC,DOCX,PDF file to Upload");
			    	display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) 
					{
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'  and video_name not like ''";
					ResultSet rs12 = ad
							.selectQuery(sql4);
					while (rs12.next()) {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs12.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);	
					 String sql9="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'";
					 ResultSet rs9=ad.selectQuery(sql9);
					 while (rs9.next()) 
					 {
						 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
					}
			     return mapping.findForward("display1");
			    }
			   if(ext.equalsIgnoreCase("txt")||(ext.equalsIgnoreCase("doc"))||(ext.equalsIgnoreCase("docx"))||(ext.equalsIgnoreCase("pdf")))
			   {
			String filePath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+sub_link_name+"/UploadFiles";
			File destinationDir = new File(filePath);
			if(!destinationDir.exists())
			{
				destinationDir.mkdirs();
			}
			if (!fileName.equals("")) {
				File fileToCreate = new File(filePath, fileName);
				if (!fileToCreate.exists()) {
					FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
					fileOutStream.write(myFile.getFileData());
					fileOutStream.flush();
					fileOutStream.close();
				}
			}
			request.setAttribute("fileName", fileName);
			filePath = filePath.replace("\\", "\\\\");
			String sqlselect="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";
			ResultSet rs15 = ad.selectQuery(sqlselect);
			String FileList="";
			while (rs15.next())
			{
				FileList=rs15.getString("file_name");
			}
			String	fileNameList="cms" + "/" + linkName+"/"+sub_link_name+"/UploadFiles" + "/"+fileName;
			boolean b;
		    b = FileList.contains(fileNameList);
		    if(b)
		    {
		    	linksForm.setMessage("This file  is  already uploaded..please choose another file");	
		    }
		    else
		    {
		    	String fileNames=null;
		    	if(FileList.equalsIgnoreCase(""))
		    	{
		    		fileNames=fileNameList;
		    	}
		    	else
		    	{
		    		fileNames=FileList+','+fileNameList;
		    	}
				String updatesql="update links set file_name='"+fileNames+"' where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'" ;
				int a = ad.SqlExecuteUpdate(updatesql);
				if (a > 0)
				{
					linksForm.setMessage("Documents Uploaded Successfully");
				} else
				{
					linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
				}	
		    }
			ArrayList list = new ArrayList();
			String sql3="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";
			ResultSet rs5 = ad.selectQuery(sql3);
			while (rs5.next()) 
			{
				linksForm = new HRCompanyDetailsForm();
				linksForm.setFileList(rs5.getString("file_name"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			   }
				else
				{
					linksForm.setMessage("Only File(.TXT,.PDF,.DOC,.DOCX) File Format is Allowed");
					display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) 
					{
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"' and video_name not like ''";
					ResultSet rs13 = ad
							.selectQuery(sql4);
					while (rs13.next()) 
					{
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs13.getString("video_name"));
						list1.add(linksForm);
					}
					String sql20="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'";
				 ResultSet rs10=ad.selectQuery(sql20);
				 while (rs10.next()) 
				 {
					 linksForm.setContentDescriptionAdmin(rs10.getString("content_description"));
				}
					request.setAttribute("listName1", list1);	
			 	   return mapping.findForward("display1");
				}
			   ArrayList list1 = new ArrayList();
				String sql4="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"'and video_name not like '' ";
				ResultSet rs14 = ad
						.selectQuery(sql4);
				while (rs14.next()) {
					linksForm = new HRCompanyDetailsForm();
					linksForm.setVideoFilesList(rs14.getString("video_name"));
					list1.add(linksForm);
				}
				request.setAttribute("listName1", list1);
		}catch (Exception e) {
			e.printStackTrace();
		}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("display1");	
	}
	
	
	
	
	
	
	public ActionForward uploadLinksFilesModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{
		HRCompanyDetailsForm linksForm = (HRCompanyDetailsForm) form;;
		try {
			HRDao ad = new HRDao();
			System.out.println("UPLOAD FILE MODIFY  METHOD");
			String linkName = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			linksForm.setLinkName(linkName);
			linksForm.setSubLinkName(sub_link_name);
		 String sql6="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main'";
		 ResultSet rs6=ad.selectQuery(sql6);
		 String linkPath=null;
		 String method=null;
		 String priority=null;
		 String contentDescription=null;
		 while (rs6.next()) 
		 {
			 linksForm.setLinkPath(rs6.getString("link_path"));           
			 linksForm.setMethodName(rs6.getString("method"));
			 linksForm.setPriority(rs6.getString("priority")); 
			 contentDescription=rs6.getString("content_description");
		}
		 linksForm.setContentDescriptionAdmin(contentDescription);	 
			FormFile myFile = linksForm.getFileNames();
			String contentType = myFile.getContentType();
			String fileName = myFile.getFileName();
			byte[] fileData = myFile.getFileData();
			String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			 if( linksForm.getFileNames().getFileSize()== 0){
				 linksForm.setMessage("Please choose a TXT,DOC,DOCX,PDF file to Upload");
			    	display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select * from links where link_name='"+linkName + "' and " +
			        "status is null and module='Main' and file_name not like ''";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) 
					{
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+linkName + "' and " +
				   "status is null and module='Main'  and video_name not like ''";
					ResultSet rs12 = ad
							.selectQuery(sql4);
					while (rs12.next()) {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs12.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);	
					 String sql9="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main'";
					 ResultSet rs9=ad.selectQuery(sql9);
					 while (rs9.next()) 
					 {
						 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
					}
			     return mapping.findForward("display1");
			    }
			   if(ext.equalsIgnoreCase("txt")||(ext.equalsIgnoreCase("doc"))||(ext.equalsIgnoreCase("docx"))||(ext.equalsIgnoreCase("pdf")))
			   {
			String filePath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+"UploadFiles";
			File destinationDir = new File(filePath);
			if(!destinationDir.exists())
			{
				destinationDir.mkdirs();
			}
			if (!fileName.equals("")) {
				File fileToCreate = new File(filePath, fileName);
				if (!fileToCreate.exists()) {
					FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
					fileOutStream.write(myFile.getFileData());
					fileOutStream.flush();
					fileOutStream.close();
				}
			}
			request.setAttribute("fileName", fileName);
			filePath = filePath.replace("\\", "\\\\");
			String sqlselect="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' and file_name not like ''";
			ResultSet rs15 = ad.selectQuery(sqlselect);
			String FileList="";
			while (rs15.next())
			{
				FileList=rs15.getString("file_name");
			}
			String	fileNameList="cms" + "/" + linkName+"/"+"UploadFiles" + "/"+fileName;
			boolean b;
		    b = FileList.contains(fileNameList);
		    if(b)
		    {
		    	linksForm.setMessage("This file  is  already uploaded..please choose another file");	
		    }
		    else
		    {
		    	String fileNames=null;
		    	if(FileList.equalsIgnoreCase(""))
		    	{
		    		fileNames=fileNameList;
		    	}
		    	else
		    	{
		    		fileNames=FileList+','+fileNameList;
		    	}
				String updatesql="update links set file_name='"+fileNames+"' where link_name='"+linkName + "' and " +
			    "status is null and module='Main'" ;
				int a = ad.SqlExecuteUpdate(updatesql);
				if (a > 0)
				{
					linksForm.setMessage("Documents Uploaded Successfully");
				} else
				{
					linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
				}	
		    }
			ArrayList list = new ArrayList();
			String sql3="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' and file_name not like ''";
			ResultSet rs5 = ad.selectQuery(sql3);
			while (rs5.next()) 
			{
				linksForm = new HRCompanyDetailsForm();
				linksForm.setFileList(rs5.getString("file_name"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			   }
				else
				{
					linksForm.setMessage("Only File(.TXT,.PDF,.DOC,.DOCX) File Format is Allowed");
					display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select * from links where link_name='"+linkName + "' and " +
					"status is null and module='Main' and file_name not like ''";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) 
					{
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+linkName + "' and " +
					"status is null and module='Main' and video_name not like ''";
					ResultSet rs13 = ad.selectQuery(sql4);
					
					while (rs13.next()) 
					{
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs13.getString("video_name"));
						list1.add(linksForm);
					}
					String sql20="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main'";
				 ResultSet rs10=ad.selectQuery(sql20);
				 while (rs10.next()) 
				 {
					 linksForm.setContentDescriptionAdmin(rs10.getString("content_description"));
				}
					request.setAttribute("listName1", list1);	
			 	   return mapping.findForward("display1");
				}
			   ArrayList list1 = new ArrayList();
				String sql4="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' and video_name not like '' ";
				ResultSet rs14 = ad
						.selectQuery(sql4);
				while (rs14.next()) {
					linksForm = new HRCompanyDetailsForm();
					linksForm.setVideoFilesList(rs14.getString("video_name"));
					list1.add(linksForm);
				}
				request.setAttribute("listName1", list1);
		}catch (Exception e) {
			e.printStackTrace();
		}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("displayMainLinks");	
	}
	
	
	public ActionForward uploadLinksVideosModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HRCompanyDetailsForm linksForm = (HRCompanyDetailsForm) form;;
		try {
			HRDao ad = new HRDao();
			System.out.println("UPLOAD Video MODIFY  METHOD");
			String linkName = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			linksForm.setLinkName(linkName);
			linksForm.setSubLinkName(sub_link_name);
		 String sql6="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main'";
		 ResultSet rs6=ad.selectQuery(sql6);
		 String linkPath=null;
		 String method=null;
		 String priority=null;
		 String contentDescription=null;
		 while (rs6.next()) 
		 {
			 linksForm.setLinkPath(rs6.getString("link_path"));           
			 linksForm.setMethodName(rs6.getString("method"));
			 linksForm.setPriority(rs6.getString("priority")); 
			 contentDescription=rs6.getString("content_description");
		}
		 linksForm.setContentDescriptionAdmin(contentDescription);	 
			FormFile myFile = linksForm.getVideoFileNames();
			String contentType = myFile.getContentType();
			String videoName = myFile.getFileName();
			byte[] fileData = myFile.getFileData();
			String ext = videoName.substring(videoName.lastIndexOf('.') + 1);
			 if( linksForm.getVideoFileNames().getFileSize()== 0){
				 linksForm.setMessage("Please choose a Video file(MP4,OGV)to Upload");
				 display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select * from links where link_name='"+linkName + "' and " +
					"status is null and module='Main' and file_name not like ''";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' and video_name not like ''";
					ResultSet rs16 = ad
							.selectQuery(sql4);
					while (rs16.next())
	                                {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs16.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);	
					 String sql9="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main'";
					 ResultSet rs9=ad.selectQuery(sql9);
					 while (rs9.next()) 
					 {
						 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
					}
			     return mapping.findForward("display1");
			    }
			if(ext.equalsIgnoreCase("mp4")||(ext.equalsIgnoreCase("ogv")))
			   {
			    	
			String videoPath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+"UploadVideos";
			File destinationDir = new File(videoPath);
			if(!destinationDir.exists())
			{
				destinationDir.mkdirs();
			}
			if (!videoName.equals("")) {
				File fileToCreate = new File(videoPath, videoName);
				if (!fileToCreate.exists()) {
					FileOutputStream fileOutStream = new FileOutputStream(
							fileToCreate);
					fileOutStream.write(myFile.getFileData());

					fileOutStream.flush();
					fileOutStream.close();
				}
			}
			request.setAttribute("videoName", videoName);
          videoPath = videoPath.replace("\\", "\\\\");
			String sqlselect="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' and video_name not like ''";
			ResultSet rs17 = ad.selectQuery(sqlselect);
			String videoList="";
			while (rs17.next())
			{
				videoList=rs17.getString("video_name");
			}
			
			String	videoNameList="cms" + "/" + linkName+"/"+""+"UploadVideos" + "/"+videoName;
			boolean b;
		    b = videoList.contains(videoNameList);
		    if(b)
		    {
		    	linksForm.setMessage("This video  is  already uploaded..please choose another file");			
		    }
		    else
		    {
		    	String videoNames=null;
		    	if(videoList.equalsIgnoreCase(""))
		    	{
		    		videoNames=videoNameList;
		    	}
		    	else
		    	{
		    		 videoNames=videoList+','+videoNameList;
		    	}
				String updatesql="update links set video_name='"+videoNames+"' where link_name='"+linkName + "' and " +
			"status is null and module='Main'" ;
				int a = ad.SqlExecuteUpdate(updatesql);
				if (a > 0)
				{
					linksForm.setMessage("Video Files Uploaded Successfully");
					display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' and file_name not like ''";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' and video_name not like ''";
					ResultSet rs16 = ad
							.selectQuery(sql4);
					while (rs16.next())
	                                {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs16.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);	
					 String sql9="select * from links where link_name='"+linkName + "' and " +
					 "status is null and module='Main' ";
					 ResultSet rs9=ad.selectQuery(sql9);
					 while (rs9.next()) 
					 {
						 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
					}
				} 
				else {
					linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
					display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' and file_name not like '' ";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' and video_name not like ''";
					ResultSet rs26 = ad
							.selectQuery(sql4);
					while (rs26.next())
	                                {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs26.getString("video_name"));
						list1.add(linksForm);
					}
					 String sql9="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main'";
					 ResultSet rs9=ad.selectQuery(sql9);
					 while (rs9.next()) 
					 {
						 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
					}
					request.setAttribute("listName1", list1);	
				}
		    }
			ArrayList list1 = new ArrayList();
			ResultSet rs36 = ad.selectQuery("select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' and video_name not like ''");
			while (rs36.next()) {
				linksForm = new HRCompanyDetailsForm();
				linksForm.setVideoFilesList(rs36.getString("video_name"));
				list1.add(linksForm);
			}
			request.setAttribute("listName1", list1);
			   }
				else
				{
					linksForm.setMessage("Only Video(MP4,OGV) File Format is Allowed");
					display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' and file_name not like ''";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' and video_name not like ''";
					ResultSet rs46 = ad
							.selectQuery(sql4);
					while (rs46.next())
	                                {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs46.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);
					 String sql9="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main'";
					 ResultSet rs9=ad.selectQuery(sql9);
					while (rs9.next()) 
					{
						 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
					}
					ad.closeResultset();
					ad.closeStatement();
			     return mapping.findForward("display1");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("displayMainLinks");
	}
	
	
	
	public ActionForward uploadVideosModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HRCompanyDetailsForm linksForm = (HRCompanyDetailsForm) form;;
		try {
			HRDao ad = new HRDao();
			System.out.println("UPLOAD Video MODIFY  METHOD");
			String linkName = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			linksForm.setLinkName(linkName);
			linksForm.setSubLinkName(sub_link_name);
		 String sql6="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"'";
		 ResultSet rs6=ad.selectQuery(sql6);
		 String linkPath=null;
		 String method=null;
		 String priority=null;
		 String contentDescription=null;
		 while (rs6.next()) 
		 {
			 linksForm.setLinkPath(rs6.getString("link_path"));           
			 linksForm.setMethodName(rs6.getString("method"));
			 linksForm.setPriority(rs6.getString("priority")); 
			 contentDescription=rs6.getString("content_description");
		}
		 linksForm.setContentDescriptionAdmin(contentDescription);	 
			FormFile myFile = linksForm.getVideoFileNames();
			String contentType = myFile.getContentType();
			String videoName = myFile.getFileName();
			byte[] fileData = myFile.getFileData();
			String ext = videoName.substring(videoName.lastIndexOf('.') + 1);
			 if( linksForm.getVideoFileNames().getFileSize()== 0){
				 linksForm.setMessage("Please choose a Video file(MP4,OGV)to Upload");
				 display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'and file_name not like ''";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'and video_name not like ''";
					ResultSet rs16 = ad
							.selectQuery(sql4);
					while (rs16.next())
	                                {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs16.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);	
					 String sql9="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'";
					 ResultSet rs9=ad.selectQuery(sql9);
					 while (rs9.next()) 
					 {
						 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
					}
			     return mapping.findForward("display1");
			    }
			if(ext.equalsIgnoreCase("mp4")||(ext.equalsIgnoreCase("ogv")))
			   {
			    	
			String videoPath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+sub_link_name+"/UploadVideos";
			File destinationDir = new File(videoPath);
			if(!destinationDir.exists())
			{
				destinationDir.mkdirs();
			}
			if (!videoName.equals("")) {
				File fileToCreate = new File(videoPath, videoName);
				if (!fileToCreate.exists()) {
					FileOutputStream fileOutStream = new FileOutputStream(
							fileToCreate);
					fileOutStream.write(myFile.getFileData());

					fileOutStream.flush();
					fileOutStream.close();
				}
			}
			request.setAttribute("videoName", videoName);
          videoPath = videoPath.replace("\\", "\\\\");
			String sqlselect="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"' and video_name not like ''";
			ResultSet rs17 = ad.selectQuery(sqlselect);
			String videoList="";
			while (rs17.next())
			{
				videoList=rs17.getString("video_name");
			}
			
			String	videoNameList="cms" + "/" + linkName+"/"+sub_link_name+"/"+"/UploadVideos" + "/"+videoName;
			boolean b;
		    b = videoList.contains(videoNameList);
		    if(b)
		    {
		    	linksForm.setMessage("This video  is  already uploaded..please choose another file");			
		    }
		    else
		    {
		    	String videoNames=null;
		    	if(videoList.equalsIgnoreCase(""))
		    	{
		    		videoNames=videoNameList;
		    	}
		    	else
		    	{
		    		 videoNames=videoList+','+videoNameList;
		    	}
				String updatesql="update links set video_name='"+videoNames+"' where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"'" ;
				int a = ad.SqlExecuteUpdate(updatesql);
				if (a > 0)
				{
					linksForm.setMessage("Video Files Uploaded Successfully");
					display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'and file_name not like ''";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'and video_name not like ''";
					ResultSet rs16 = ad
							.selectQuery(sql4);
					while (rs16.next())
	                                {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs16.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);	
					 String sql9="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'";
					 ResultSet rs9=ad.selectQuery(sql9);
					 while (rs9.next()) 
					 {
						 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
					}
				} 
				else {
					linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
					display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'and file_name not like '' ";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"' and video_name not like ''";
					ResultSet rs26 = ad
							.selectQuery(sql4);
					while (rs26.next())
	                                {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs26.getString("video_name"));
						list1.add(linksForm);
					}
					 String sql9="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'";
					 ResultSet rs9=ad.selectQuery(sql9);
					 while (rs9.next()) 
					 {
						 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
					}
					request.setAttribute("listName1", list1);	
				}
		    }
			ArrayList list1 = new ArrayList();
			ResultSet rs36 = ad.selectQuery("select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"' and video_name not like ''");
			while (rs36.next()) {
				linksForm = new HRCompanyDetailsForm();
				linksForm.setVideoFilesList(rs36.getString("video_name"));
				list1.add(linksForm);
			}
			request.setAttribute("listName1", list1);
			   }
				else
				{
					linksForm.setMessage("Only Video(MP4,OGV) File Format is Allowed");
					display(mapping, form, request, response);
					displaySublinks(mapping, form, request, response);
					ArrayList list = new ArrayList();
					String sql3="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'and video_name not like ''";
					ResultSet rs46 = ad
							.selectQuery(sql4);
					while (rs46.next())
	                                {
						linksForm = new HRCompanyDetailsForm();
						linksForm.setVideoFilesList(rs46.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);
					 String sql9="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'";
					 ResultSet rs9=ad.selectQuery(sql9);
					while (rs9.next()) 
					{
						 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
					}
					ad.closeResultset();
					ad.closeStatement();
					ad.connClose();
			     return mapping.findForward("display1");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("display1");
	}
	public ActionForward deleteFileListModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	{
		HRCompanyDetailsForm linksForm = (HRCompanyDetailsForm) form;
		try
		{
		System.out.println("DELETE FILE MODIFY METHOD");
		HRDao adlinks = new HRDao();
		String link_id = linksForm.getLinkName();
		String sub_link_name = linksForm.getSubLinkName();
		String uncheckValues=request.getParameter("unValues");
		String checkValues=request.getParameter("cValues");
		linksForm.setSubLinkName(sub_link_name);
		String sql9="select * from links where link_name='"+sub_link_name + "' and " +
		"status=1 and delete_status=1 and module='"+link_id+"'";
	 ResultSet rs9=adlinks.selectQuery(sql9);
	 while (rs9.next()) 
	 {
		 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
	}
		 uncheckValues = uncheckValues.substring(0, uncheckValues.length());
		 checkValues = checkValues.substring(0, checkValues.length());
			String v[] = uncheckValues.split(",");
		 String upFileName=null;
			String upFileName2="";
			for(int i=0;i<v.length;i++)
			{
			upFileName="cms" + "/" + link_id+"/"+sub_link_name+"/UploadFiles" + "/"+v[i];
		    
		    upFileName2+=upFileName+",";
			}
			String up2="cms" + "/" + link_id+"/"+sub_link_name+"/UploadFiles" + "/";
			upFileName2 = upFileName2.substring(0, upFileName2.length()-1);
			String updatesql=null;
			if(upFileName2.equalsIgnoreCase(up2))
			{
		 updatesql="update links set file_name='' where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+link_id+"'" ;
			}
			else
			{
				 updatesql="update links set file_name='"+upFileName2+"' where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+link_id+"'" ;
			}
	int a = adlinks.SqlExecuteUpdate(updatesql);
	if (a > 0) {
		linksForm.setMessage("Documents deleted Successfully");
	ArrayList list = new ArrayList();
	String sql3="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+link_id+"'and file_name not like ''";
	ResultSet rs5 = adlinks.selectQuery(sql3);
	while (rs5.next()) 
	{
		linksForm = new HRCompanyDetailsForm();
		linksForm.setFileList(rs5.getString("file_name"));
		list.add(linksForm);
	}
	request.setAttribute("listName", list);
	ArrayList list1 = new ArrayList();
	String sql4="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+link_id+"' and video_name not like ''";
	ResultSet rs13 = adlinks
			.selectQuery(sql4);
	while (rs13.next()) 
	{
		linksForm = new HRCompanyDetailsForm();
		linksForm.setVideoFilesList(rs13.getString("video_name"));
		list1.add(linksForm);
	}
	request.setAttribute("listName1", list1);
	String sql19="select * from links where link_name='"+sub_link_name + "' and " +
	"status=1 and delete_status=1 and module='"+link_id+"'";
 ResultSet rs19=adlinks.selectQuery(sql19);
 while (rs19.next()) 
 {
	 linksForm.setContentDescriptionAdmin(rs19.getString("content_description"));	 
}
	display(mapping, form, request, response);
	displaySublinks(mapping, form, request, response);
	return mapping.findForward("display1");
	} 
	else {
		linksForm.setMessage("Error While deleteing Files ... Please check Entered Values");
	display(mapping, form, request, response);
	displaySublinks(mapping, form, request, response);
	return mapping.findForward("display1");
	}
		}catch (Exception e) {
			e.printStackTrace();
		}
	   display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("display1");
	}
	
	
	public ActionForward updateLinksContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
			{
		System.out.println("updateLinksContent()-----");
		HRCompanyDetailsForm linksForm = (HRCompanyDetailsForm) form;
		HttpSession session=request.getSession();
		HRDao adlinks = new HRDao();
			try
			{
			System.out.println("***UPDATE THE CONTENT***");
			String linkName = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			String linkPath=linksForm.getLinkPath();
			String method=linksForm.getMethodName();
			String priority=linksForm.getPriority();
			String id=linksForm.getLinkId();
			
			String sqlselect="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main'";
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
			ResultSet rs17 = adlinks.selectQuery(sqlselect);
			String fileList=null;
			String videoList=null;
			while (rs17.next()) 
			{   
				fileList=rs17.getString("file_name");
				videoList=rs17.getString("video_name");
			}
	
			
			if(linkName.equalsIgnoreCase("Login Help"));
			{
				
				String sql18="update links set content_description='"+content_description+"' where link_name='"+linkName+"' and " +
				"status is null ";
		
		adlinks.SqlExecuteUpdate(sql18);
		
		
		linksForm.setContentDescription(content_description);
		
		
			}
			
			
			
			
		
	String sql17="update links set content_description='"+content_description+"' where link_name='"+linkName+"' and " +
			"status is null and module='Main'";
	
	adlinks.SqlExecuteUpdate(sql17);
			
		linksForm.setMessage("Your details have been updated successfully");
		ArrayList list = new ArrayList();
		String sql3="select * from links where link_name='"+linkName+"' and " +
		"status is null and module='Main'";
		ResultSet rs5 = adlinks.selectQuery(sql3);
		while (rs5.next()) {

			linksForm = new HRCompanyDetailsForm();
			linksForm.setFileList(rs5.getString("file_name"));
			list.add(linksForm);
		}
		request.setAttribute("listName", list);
		ArrayList list1 = new ArrayList();
		String sql4="select * from links where link_name='"+linkName+"' and " +
		"status is null and module='Main'";
		ResultSet rs15 = adlinks
				.selectQuery(sql4);
		while (rs15.next()) {
			linksForm = new HRCompanyDetailsForm();
			linksForm.setVideoFilesList(rs15.getString("video_name"));
			list1.add(linksForm);
		}
		request.setAttribute("listName1", list1);	
	
			}catch (Exception e) {
				e.printStackTrace();
			}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("displayMainLinks");	
	  }
	
	public ActionForward deleteVideoListModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HRCompanyDetailsForm linksForm = (HRCompanyDetailsForm) form;
		try
		{
		System.out.println("DELETE VIDEO MODIFY METHOD");
		HRDao adlinks = new HRDao();
		String link_id = linksForm.getLinkName();
		String sub_link_name = linksForm.getSubLinkName();
		String uncheckValues=request.getParameter("unValues");
		String checkValues=request.getParameter("cValues");
		linksForm.setSubLinkName(sub_link_name);
		String sql9="select * from links where link_name='"+sub_link_name + "' and " +
		"status=1 and delete_status=1 and module='"+link_id+"'";
	 ResultSet rs9=adlinks.selectQuery(sql9);
	 while (rs9.next()) 
	 {
		 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
	}
		 uncheckValues = uncheckValues.substring(0, uncheckValues.length());
			String v[] = uncheckValues.split(",");
		 String upVideoName=null;
			String upVideoName2="";
			for(int i=0;i<v.length;i++)
			{
				upVideoName="cms" + "/" + link_id+"/"+sub_link_name+"/UploadVideos" + "/"+v[i];
		    			    upVideoName2+=upVideoName+",";
			}
			 String up2="cms" + "/" + link_id+"/"+sub_link_name+"/UploadVideos" +"/";
		        upVideoName2 = upVideoName2.substring(0, upVideoName2.length()-1);
				String updatesql=null;
				if(upVideoName2.equalsIgnoreCase(up2))
				{
					 updatesql="update links set video_name='' where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+link_id+"'";
				}
				else
				{
					 updatesql="update links set video_name='"+upVideoName2+"' where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+link_id+"'";
				}
	int a = adlinks.SqlExecuteUpdate(updatesql);
	if (a > 0) {
		linksForm.setMessage("videos deleted Successfully");
	ArrayList list = new ArrayList();
	String sql3="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+link_id+"' and file_name not like ''";
	ResultSet rs5 = adlinks.selectQuery(sql3);
	while (rs5.next()) 
	{
		linksForm = new HRCompanyDetailsForm();
		linksForm.setFileList(rs5.getString("file_name"));
		list.add(linksForm);
	}
	request.setAttribute("listName", list);
	ArrayList list1 = new ArrayList();
	String sql4="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+link_id+"'and video_name not like ''";
	ResultSet rs13 = adlinks
			.selectQuery(sql4);
	while (rs13.next()) 
	{
		linksForm = new HRCompanyDetailsForm();
		linksForm.setVideoFilesList(rs13.getString("video_name"));
		list1.add(linksForm);
	}
	request.setAttribute("listName1", list1);
	String sql19="select * from links where link_name='"+sub_link_name + "' and " +
	"status=1 and delete_status=1 and module='"+link_id+"'";
 ResultSet rs19=adlinks.selectQuery(sql19);
 while (rs19.next()) 
 {
	 linksForm.setContentDescriptionAdmin(rs19.getString("content_description"));	 
}
	display(mapping, form, request, response);
	displaySublinks(mapping, form, request, response);
	return mapping.findForward("display1");
	} 
	else {
		linksForm.setMessage("Error While deleteing Files ... Please check Entered Values");
	display(mapping, form, request, response);
	displaySublinks(mapping, form, request, response);
	return mapping.findForward("display1");
	}
		}catch (Exception e) {
			e.printStackTrace();
		}
	   display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("display1");
	}
	
	
	public ActionForward updateContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
			{
		
		System.out.println("***UPDATE THE CONTENT***");
		HRCompanyDetailsForm linksForm = (HRCompanyDetailsForm) form;
		HttpSession session=request.getSession();
		LinksDao adlinks = new LinksDao();
			try
			{
				ResultSet rs = adlinks.selectQuery("select * from links where status is null");
				ArrayList linkIdList = new ArrayList();
				ArrayList linkValueList = new ArrayList();
				linksForm.setLinkIdList(linkIdList);
				linksForm.setLinkValueList(linkValueList);
				while(rs.next()) {
					linkIdList.add(rs.getString("link_name"));
					linkValueList.add(rs.getString("link_name"));
				}
				
				
				String linkName = linksForm.getLinkName();
				String sub_link_name = linksForm.getSubLinkName();
				String linkPath="newsAndMedia.do";
				String method="display1";
				String priority=linksForm.getPriority();
				String id=linksForm.getLinkId();
				FormFile myFile = linksForm.getIconNames();
			    String contentType = myFile.getContentType();
				String iconName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = iconName.substring(iconName.lastIndexOf('.') + 1);
				   if(ext.equalsIgnoreCase("jpeg")||(ext.equalsIgnoreCase("png"))||(ext.equalsIgnoreCase("jpg"))||(ext.equalsIgnoreCase("gif")))
				   {
				String iconPath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+sub_link_name+"/UploadIcons";
				File destinationDir = new File(iconPath);
				if(!destinationDir.exists())
				{
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
				iconPath = iconPath.replace("\\", "\\\\");
			  }
				   
		    String iconList="";
			if(iconName.equalsIgnoreCase(""))
			{
				 String sql="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'";
				 System.out.println("******SQL OUT PUT WHEN ICON NOT UPLOADED==="+sql);
			ResultSet rs1 = adlinks.selectQuery(sql);
			while (rs1.next()) 
			{
				  iconList=rs1.getString("icon_name");	
			}
			}
			else
			{
				  iconList="cms" + "/" + linkName+"/"+sub_link_name+"/UploadIcons" + "/"+ iconName;
			}
			
			String updatesql2="update links set link_name='"+sub_link_name+"' where module='"+linkName+"' and " +
		"id='"+id+"' and status=1";
			adlinks.SqlExecuteUpdate(updatesql2);
			String sqlselect="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"'";
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
			ResultSet rs17 = adlinks.selectQuery(sqlselect);
			
			String fileList=null;
			String videoList=null;
			while (rs17.next()) 
			{   
				fileList=rs17.getString("file_name");
				videoList=rs17.getString("video_name");
			}
			String updatesql="update links set link_path='"+linkPath+"',method='"+method+"',priority='"+priority+"',icon_name='"+iconList+"',video_name='"+videoList+"',file_name='"+fileList+"',content_description='"+content_description+"' where  link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"'";
	int a = adlinks.SqlExecuteUpdate(updatesql);
	if (a > 0) {
		linksForm.setMessage("Your details have been updated successfully");
		ArrayList list = new ArrayList();
		String sql3="select * from links where link_name='"+sub_link_name + "' and " +
		"status=1 and delete_status=1 and module='"+linkName+"'";
		ResultSet rs5 = adlinks.selectQuery(sql3);
		while (rs5.next()) {

			linksForm = new HRCompanyDetailsForm();
			linksForm.setFileList(rs5.getString("file_name"));
			list.add(linksForm);
		}
		request.setAttribute("listName", list);
		ArrayList list1 = new ArrayList();
		String sql4="select * from links where link_name='"+sub_link_name + "' and " +
		"status=1 and delete_status=1 and module='"+linkName+"'";
		ResultSet rs15 = adlinks
				.selectQuery(sql4);
		while (rs15.next()) {
			linksForm = new HRCompanyDetailsForm();
			linksForm.setVideoFilesList(rs15.getString("video_name"));
			list1.add(linksForm);
		}
		request.setAttribute("listName1", list1);	
		 String sql="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"'";
	ResultSet rs2 = adlinks.selectQuery(sql);
	while (rs2.next()) 
	{
		linksForm.setIconList(rs2.getString("icon_name"));	
	}
	} else {
		linksForm.setMessage("Error While updateing your details  ... Please check Entered Values");
	}
			}catch (Exception e) {
				e.printStackTrace();
			}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("display1");	
	       }


}
