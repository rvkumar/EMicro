package com.microlabs.newsandmedia.action;

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
import com.microlabs.hr.dao.HRDao;
import com.microlabs.newsandmedia.dao.FckEditorDao;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.newsandmedia.form.FckEditorForm;
import com.microlabs.utilities.UserInfo;

public class FckEditorAction extends DispatchAction {	
	
	
	public ActionForward updatePriority(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FckEditorForm fckEditorForm = (FckEditorForm) form;// TODO
		
		String[] modifyPriority=fckEditorForm.getModifyPriority();
		String reqIDs[]=fckEditorForm.getReqIDs();
		System.out.println("modifypriority="+modifyPriority.length);
		NewsandMediaDao ad = new NewsandMediaDao();
		for(int i=0;i<reqIDs.length;i++)
		{
			String updatePriority="update links set priority='"+modifyPriority[i]+"' where id='"+reqIDs[i]+"' ";
			ad.SqlExecuteUpdate(updatePriority);
		}
		displaySubSublinks(mapping, form, request, response);
		return mapping.findForward("display");
	}
	public ActionForward display(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
	FckEditorForm fckEditorForm = (FckEditorForm) form;// TODO
	HttpSession session = request.getSession();
	try {
		NewsandMediaDao ad = new NewsandMediaDao();
		ResultSet rs = ad.selectQuery("select * from links where status is null");
		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();
		ArrayList a1 = new ArrayList();
		while (rs.next()) {
			
			hm.put(rs.getString("link_path") + "?method="
					+ rs.getString("method") + "&id=" + rs.getString("link_name"),
					rs.getString("link_name"));
		}
		session.setAttribute("URL", hm);
		session.setAttribute("LINKNAME", a1);
		ResultSet rs2 = ad.selectQuery("select * from links where status is null");
		ArrayList linkIdList = new ArrayList();
		ArrayList linkValueList = new ArrayList();
		while (rs2.next()) {
			linkIdList.add(rs2.getString("link_name"));
			linkValueList.add(rs2.getString("link_name"));
		}
		
		fckEditorForm.setLinkIdList(linkIdList);
		fckEditorForm.setLinkValueList(linkValueList);

	} catch (Exception e) {
		System.out.println("Exception caught =" + e.getMessage());
	}
	
	
	String id = request.getParameter("id");
	HRDao ad = new HRDao();
	
	UserInfo user=(UserInfo)session.getAttribute("user");
	
	
	String sql="select * from links where id in("+user.getIncludeSubLinks()+")";
	System.out.println("SQL is *********************************"+sql);
	ResultSet rs=ad.selectQuery(sql);
		
	LinkedHashMap<String, String> hm=(LinkedHashMap<String, String>)session.getAttribute("SUBLINKS");
		
		
	session.setAttribute("SUBLINKS", hm);
		
	
	request.setAttribute("submitButton", "submitButton");
	return mapping.findForward("display");
}
	
public ActionForward displaySublinks(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) {
	FckEditorForm fckEditorForm = (FckEditorForm) form;// TODO
	String linkName = fckEditorForm.getLinkName();
	try {
		LinksDao ad = new LinksDao();
		
		String sql="select * from links where module='"+linkName+"' " +
		"and status=1 and sub_linkname is null";
		
		ResultSet rs = ad.selectQuery("select * from links where module='"+linkName+"' " +
				"and status=1 and sub_linkname=link_name");
		ArrayList subLinkIdList = new ArrayList();
		ArrayList subLinkValueList = new ArrayList();
		FckEditorForm fckEditorForm1 = null;
		while(rs.next()) {
			subLinkIdList.add(rs.getString("link_name"));
			subLinkValueList.add(rs.getString("link_name"));
		}
		fckEditorForm.setSubLinkIdList(subLinkIdList);
		fckEditorForm.setSubLinkValueList(subLinkValueList);
		fckEditorForm.setLinkName(linkName);
		request.setAttribute("displaySublinkField", "displaySublinkField");
		request.setAttribute("submitButton", "submitButton");
	} catch (Exception e) {
		e.printStackTrace();
	}
	display(mapping, form, request, response);
	return mapping.findForward("display");
}
public ActionForward displaySubSublinks(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
	FckEditorForm fckEditorForm = (FckEditorForm) form;// TODO
	 try 
	 { 	
		 FckEditorDao ad = new FckEditorDao();
		 String link_id = fckEditorForm.getLinkName();
		 String sub_link_name = fckEditorForm.getSubLinkName();
		 String sql="select * from links where module='"+link_id+"' and " +
	 		"sub_linkname='"+sub_link_name+"' and link_name!=sub_linkname and status=1 order by priority";
		 ResultSet rs=ad.selectQuery(sql);
		 
		 FckEditorForm fckEditorForm1 =null;
		 
		 ArrayList a1=new ArrayList();
		 
		 while (rs.next()) {
			 fckEditorForm1=new FckEditorForm();
			 fckEditorForm1.setLinkId(rs.getString("id"));
			 fckEditorForm1.setLinkName(rs.getString("link_name"));
			 fckEditorForm1.setSubLinkName(rs.getString("sub_linkname"));
			 fckEditorForm1.setMainLinkName(rs.getString("module"));
			 fckEditorForm1.setLinkPath(rs.getString("link_path"));
			 fckEditorForm1.setMethodName(rs.getString("method"));
			 fckEditorForm1.setUnderSubLinks(rs.getString("link_name"));
			 fckEditorForm1.setPriority(rs.getString("priority"));
			 a1.add(fckEditorForm1);
		}
		 request.setAttribute("subLinksDetails", a1);
	 }catch(SQLException se) {
		 se.printStackTrace();
	 }
	displaySublinks(mapping, form, request, response);
	return mapping.findForward("display");
}
public ActionForward submitSubSublinks(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
{
	System.out.println("****************FCK EDITOR SUBMIT METHOD**************");
	FckEditorForm fckEditorForm = (FckEditorForm) form;// TODO
	 try {
	     HttpSession session=request.getSession();
		 FckEditorDao ad = new FckEditorDao();
		 String link_id = fckEditorForm.getLinkName();
		 String sub_link_name = fckEditorForm.getSubLinkName();
		 String underSubLink = fckEditorForm.getUnderSubLinks();
		 String path="newsAndMedia.do";
		 String method="displayContent";
        String priority=fckEditorForm.getPriority();
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
	content_description=content_description.replaceAll("'", "''");
	String underSubLinks = fckEditorForm.getUnderSubLinks(); 
	String fileList="";
	ResultSet rs4 = ad.selectQuery("SELECT * FROM filelist where link_name='"
					+link_id
					+"' and sub_link_name='"
					+ sub_link_name
					+ "' and sub_sub_linkname='"
					+ underSubLinks
					+ "'");
	while (rs4.next()) {	
		fileList+="cms" + "/" + link_id+"/"+sub_link_name+"/"+underSubLink+"/UploadFiles" + "/"+ rs4.getString("file_name")+",";
	}
	String videoList="";
	ResultSet rs5 = ad.selectQuery("SELECT * FROM video_list where link_name='"
					+ link_id
					+ "' and sub_link_name='"
					+ sub_link_name
					+ "' and sub_sub_linkname='"
					+ underSubLinks
					+ "'");
	while (rs5.next()) {
		videoList+="cms" + "/" + link_id+"/"+sub_link_name+"/"+underSubLink+"/UploadVideos" + "/"+ rs5.getString("video_name")+",";
	}	
	if(!fileList.equalsIgnoreCase("")){
		fileList=fileList.substring(0, fileList.lastIndexOf(","));
	}
	if(!videoList.equalsIgnoreCase("")){
		videoList=videoList.substring(0, videoList.lastIndexOf(","));
	}
	
		String sql1 ="insert into links(link_name,module,sub_linkname," +
			 		"link_path,method,content_description,status,file_name,video_name,priority) values('"+underSubLinks+"','"+link_id+"'," +
			 		"'"+sub_link_name+"','"+path+"','"+method+"','"+content_description+"',1,'"+fileList+"','"+videoList+"','"+priority+"')";
		
		
		System.out.println("Getting A SQL is ***************"+sql1);
		
		
		int a= ad.SqlExecuteUpdate(sql1);
		 if (a > 0) {
				fckEditorForm.setMessage("Fck Editor Details Submitted  Successfully");
				String deletesql = "delete from filelist where link_name='"
					+ link_id + "' and sub_link_name='" + sub_link_name+
					"'and sub_sub_linkname='" + underSubLinks
					+ "'";
				
			String deletesql1 = "delete from video_list where link_name='"
					+ link_id + "' and sub_link_name='" + sub_link_name+
					"'and sub_sub_linkname='" + underSubLinks
					+ "'";
			ad.SqlExecuteUpdate(deletesql);
			ad.SqlExecuteUpdate(deletesql1);	
			fckEditorForm.setLinkName("");
			fckEditorForm.setSubLinkName("");
			fckEditorForm.setMainLinkName("");
			fckEditorForm.setUnderSubLinks("");
			fckEditorForm.setLinkPath("");
			fckEditorForm.setMethodName("");
			fckEditorForm.setPriority("");	
		 } else {
				fckEditorForm.setMessage("Error While  Adding Fck Editor Details.. Please check Entered Values");
			} 
	 }catch (SQLException se) {
		 
		 se.printStackTrace();
	}
	 displaySublinks(mapping, form, request, response);
	return mapping.findForward("display");
}
public ActionForward uploadFiles(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	
	FckEditorForm fckEditorForm = (FckEditorForm) form;
	
	try {
		System.out.println("UPLOAD METHOD");
		String link_id = fckEditorForm.getLinkName();
		String sub_link_name = fckEditorForm.getSubLinkName();
		String underSubLink = fckEditorForm.getUnderSubLinks();
		FckEditorDao adFckEditor = new FckEditorDao();
		
		String sql4="select * from links where module='"
			+link_id+"'  and sub_linkname= '"+sub_link_name+"'";
		
		ResultSet rs6 = adFckEditor.selectQuery(sql4);
		
        String sublinks="";
        ArrayList listnew=new ArrayList();
         
		while(rs6.next())
		{
			sublinks=rs6.getString("link_name");
			listnew.add(sublinks);
		}
		boolean b;
	    b =listnew.contains(underSubLink);
		if(b)
		{
			fckEditorForm.setMessage("Sub Link Name already taken.choose another name");
		}
		else
		{
		FormFile myFile = fckEditorForm.getFileNames();
		String contentType = myFile.getContentType();
		String fileName = myFile.getFileName();
		byte[] fileData = myFile.getFileData();
		String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
		 if( fckEditorForm.getFileNames().getFileSize()== 0){
		    	fckEditorForm.setMessage("Please choose a  file to Upload");
		    	
		    	display(mapping, form, request, response);
		    	
				displaySublinks(mapping, form, request, response);
				
				ArrayList list = new ArrayList();
				
				String sql3="select * from filelist where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'  and " +
		 		"sub_sub_linkname='"+underSubLink+"'";
				
				ResultSet rs5 = adFckEditor.selectQuery(sql3);
				
				while (rs5.next()) {
					fckEditorForm = new FckEditorForm();
					fckEditorForm.setFileList(rs5.getString("file_name"));
					list.add(fckEditorForm);
				}
				
				request.setAttribute("listName", list);
				ArrayList list1 = new ArrayList();
				String sql7="select * from video_list where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'  and " +
		 		"sub_sub_linkname='"+underSubLink+"'";
				ResultSet rs16 = adFckEditor.selectQuery(sql7);
				
				while(rs16.next()) {
					fckEditorForm = new FckEditorForm();
					fckEditorForm.setVideoFilesList(rs16.getString("video_name"));
					list1.add(fckEditorForm);
				}
				
				request.setAttribute("listName1", list1);	
		     return mapping.findForward("display");
		    }
		   if(ext.equalsIgnoreCase("txt")||(ext.equalsIgnoreCase("doc"))||(ext.equalsIgnoreCase("docx"))||(ext.equalsIgnoreCase("pdf")))
		   {
		String filePath = getServlet().getServletContext().getRealPath("cms") + "/" + link_id+"/"+sub_link_name+"/"+underSubLink+"/UploadFiles";
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
		String sql9="select count(*) from filelist where link_name='"+link_id+"' and " +
 		"sub_link_name='"+sub_link_name+"'  and " +
 		"sub_sub_linkname='"+underSubLink+"' and file_name='"+fileName+"'";
		ResultSet rs5 = adFckEditor.selectQuery(sql9);
		int fileCount=0;
		while(rs5.next())
		{
			
			fileCount=Integer.parseInt(rs5.getString(1));
			
		}
		System.out.println("fileCount="+fileCount);
		if(fileCount>0)
		{
			fckEditorForm.setMessage("File aleardy uploaded..please choose another file");
		}
		else
		{
		String insertsql = "insert into filelist(link_name,sub_link_name,file_path,file_name,sub_sub_linkname) values('"
				+link_id
				+"','"
				+sub_link_name
				+"','"
				+filePath
				+"','"+fileName+"','"+underSubLink+"')";
		int a = adFckEditor.SqlExecuteUpdate(insertsql);
		if (a > 0) {
			fckEditorForm.setMessage("Documents Uploaded Successfully");
		}else{
			fckEditorForm.setMessage("Error While Uploading Files ... Please check Entered Values");
		}
		}
		ArrayList list = new ArrayList();
		String sql3="select * from filelist where link_name='"+link_id+"' and " +
 		"sub_link_name='"+sub_link_name+"'  and " +
 		"sub_sub_linkname='"+underSubLink+"'";
		ResultSet rs11 = adFckEditor.selectQuery(sql3);
		while (rs11.next()) {
			fckEditorForm = new FckEditorForm();
			fckEditorForm.setFileList(rs11.getString("file_name"));
			list.add(fckEditorForm);
		}
		request.setAttribute("listName", list);
		   }
			else
			{
				fckEditorForm.setMessage("Only File(.TXT,.PDF,.DOC,.DOCX) Format is Allowed");
				display(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				ArrayList list = new ArrayList();
				String sql3="select * from filelist where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'  and " +
		 		"sub_sub_linkname='"+underSubLink+"'";
				ResultSet rs12 = adFckEditor.selectQuery(sql3);
				while (rs12.next())
				{
					fckEditorForm = new FckEditorForm();
					fckEditorForm.setFileList(rs12.getString("file_name"));
					list.add(fckEditorForm);
				}
				request.setAttribute("listName", list);
				ArrayList list1 = new ArrayList();
				String sql14="select * from video_list where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'  and " +
		 		"sub_sub_linkname='"+underSubLink+"'";
				ResultSet rs13 = adFckEditor
						.selectQuery(sql14);
				while (rs13.next()) 
				{
					fckEditorForm = new FckEditorForm();
					fckEditorForm.setVideoFilesList(rs13.getString("video_name"));
					list1.add(fckEditorForm);
				}
				request.setAttribute("listName1", list1);	
		 	   return mapping.findForward("display");
			}
		   ArrayList list1 = new ArrayList();
			String sql13="select * from video_list where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'  and " +
	 		"sub_sub_linkname='"+underSubLink+"'";
			ResultSet rs14 = adFckEditor
					.selectQuery(sql13);
			while (rs14.next()) {
				fckEditorForm = new FckEditorForm();
				fckEditorForm.setVideoFilesList(rs14.getString("video_name"));
				list1.add(fckEditorForm);
			}
			request.setAttribute("listName1", list1);
		   adFckEditor.closeResultset();
			adFckEditor.closeStatement();
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
	
	FckEditorForm fckEditorForm = (FckEditorForm) form;
	try {
		System.out.println("UPLOAD METHOD");
		String link_id = fckEditorForm.getLinkName();
		String sub_link_name = fckEditorForm.getSubLinkName();
		String underSubLink = fckEditorForm.getUnderSubLinks();
		FckEditorDao adFckEditor = new FckEditorDao();
		String sql4="select * from links where module='"
			+link_id+"'  and sub_linkname= '"+sub_link_name+"'";
		ResultSet rs6 = adFckEditor.selectQuery(sql4);
		 
         String sublinks="";
         
         ArrayList listnew=new ArrayList();
         
 		while (rs6.next())
 		{      
 			sublinks=rs6.getString("link_name");
 			listnew.add(sublinks); 
 		}
		boolean b;
	    b =listnew.contains(underSubLink);
		if(b)
		{
			fckEditorForm.setMessage("Sub Link Name already taken.choose another name");
		}
		else
		{
		FormFile myFile = fckEditorForm.getVideoFileNames();
		String contentType = myFile.getContentType();
		String videoName = myFile.getFileName();
		byte[] fileData = myFile.getFileData();
		String ext = videoName.substring(videoName.lastIndexOf('.') + 1);
		 if( fckEditorForm.getVideoFileNames().getFileSize()== 0){
		    	fckEditorForm.setMessage("Please choose a Video file to Upload");
		    	display(mapping, form, request, response);
		    	displaySublinks(mapping, form, request, response);
		    	ArrayList list = new ArrayList();
				String sql3="select * from filelist where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'  and " +
		 		"sub_sub_linkname='"+underSubLink+"'";
				ResultSet rs5 = adFckEditor.selectQuery(sql3);
				while (rs5.next()) {
					fckEditorForm = new FckEditorForm();
					fckEditorForm.setFileList(rs5.getString("file_name"));
					list.add(fckEditorForm);
				}
				request.setAttribute("listName", list);
				ArrayList list1 = new ArrayList();
				String sql14="select * from video_list where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'  and " +
		 		"sub_sub_linkname='"+underSubLink+"'";
				ResultSet rs16 = adFckEditor.selectQuery(sql14);
				while (rs16.next()) {
					fckEditorForm = new FckEditorForm();
					fckEditorForm.setVideoFilesList(rs16.getString("video_name"));
					list1.add(fckEditorForm);
				}
				request.setAttribute("listName1", list1);	
				return mapping.findForward("display");
		    }
		 
		   if(ext.equalsIgnoreCase("mp4")||(ext.equalsIgnoreCase("ogv")))
		   {	
		String videoPath = getServlet().getServletContext().getRealPath("cms") + "/" + link_id+"/"+sub_link_name+"/"+underSubLink+"/UploadVideos";
		File destinationDir = new File(videoPath);
		if(!destinationDir.exists())
		{
			destinationDir.mkdirs();
		}
		
		if(!videoName.equals("")) {
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
 		"sub_link_name='"+sub_link_name+"'  and " +
 		"sub_sub_linkname='"+underSubLink+"' and video_name='"+videoName+"'";
		ResultSet rs5 = adFckEditor.selectQuery(sql9);
		int videoCount=0;
		while (rs5.next())
		{
			videoCount=Integer.parseInt(rs5.getString(1));
		}
		if(videoCount>0)
		{
			fckEditorForm.setMessage("Video aleardy uploaded..please choose another video");
		}
		else
		{
		String insertsql = "insert into video_list(link_name, sub_link_name, video_path, " +
				"video_name,sub_sub_linkname) values('"
				+link_id
				+"','"
				+sub_link_name
				+ "','"
				+videoPath
				+ "','"+videoName+"','"+underSubLink+"')";
		
		int a = adFckEditor.SqlExecuteUpdate(insertsql);
		if (a > 0) {
			fckEditorForm.setMessage("Video Files Uploaded Successfully");
		} else {
			fckEditorForm
					.setMessage("Error While Uploading Files ... Please check Entered Values");
		}
		}
		ArrayList list1 = new ArrayList();
		String sql24="select * from video_list where link_name='"+link_id+"' and " +
 		"sub_link_name='"+sub_link_name+"'  and " +
 		"sub_sub_linkname='"+underSubLink+"'";
		ResultSet rs26 = adFckEditor.selectQuery(sql24);
		while (rs26.next()) {
			fckEditorForm = new FckEditorForm();
			fckEditorForm.setVideoFilesList(rs26.getString("video_name"));
			list1.add(fckEditorForm);
		}
		request.setAttribute("listName1", list1);	
		   }
			else
			{
				fckEditorForm.setMessage("Only Video(MP4,OGV) File Format is Allowed");
				display(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				 ArrayList list = new ArrayList();
					String sql3="select * from filelist where link_name='"+link_id+"' and " +
			 		"sub_link_name='"+sub_link_name+"'  and " +
			 		"sub_sub_linkname='"+underSubLink+"'";
					ResultSet rs5 = adFckEditor.selectQuery(sql3);
					while (rs5.next()) {
							
						fckEditorForm = new FckEditorForm();
						fckEditorForm.setFileList(rs5.getString("file_name"));
						list.add(fckEditorForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql34="select * from video_list where link_name='"+link_id+"' and " +
			 		"sub_link_name='"+sub_link_name+"'  and " +
			 		"sub_sub_linkname='"+underSubLink+"'";
					ResultSet rs36 = adFckEditor
							.selectQuery(sql34);
					while (rs36.next()) {
						fckEditorForm = new FckEditorForm();
						fckEditorForm.setVideoFilesList(rs36.getString("video_name"));
						list1.add(fckEditorForm);
					}
					request.setAttribute("listName1", list1);	
		 	   return mapping.findForward("display");
			}
		   ArrayList list = new ArrayList();
			String sql3="select * from filelist where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'  and " +
	 		"sub_sub_linkname='"+underSubLink+"'";
			ResultSet rs5 = adFckEditor.selectQuery(sql3);
			while (rs5.next()) {
				
				fckEditorForm = new FckEditorForm();
				fckEditorForm.setFileList(rs5.getString("file_name"));
				list.add(fckEditorForm);
			}
			request.setAttribute("listName", list);
			adFckEditor.closeResultset();
			adFckEditor.closeStatement();
			adFckEditor.connClose();
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
	FckEditorForm fckEditorForm = (FckEditorForm) form;// TODO
	try {
	FckEditorDao adFckEditor = new FckEditorDao();	
	String link_id = fckEditorForm.getLinkName();
	String sub_link_name = fckEditorForm.getSubLinkName();
	String underSubLink = fckEditorForm.getUnderSubLinks();
	String linkId = fckEditorForm.getLinkName();
	String subLinkName = request.getParameter("subLinkName");
	NewsandMediaDao ad = new NewsandMediaDao();
		String deleteArray[] = fckEditorForm.getSelect();
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
		int a = adFckEditor.SqlExecuteUpdate(deletesql);
		if (a > 0) {
			fckEditorForm.setMessage("Files Deleted Successfully");
		} else {
			fckEditorForm.setMessage("Error While Deleting Files ...");
		}
		ArrayList list = new ArrayList();
		String sql3="select * from filelist where link_name='"+link_id+"' and " +
 		"sub_link_name='"+sub_link_name+"'  and " +
 		"sub_sub_linkname='"+underSubLink+"'";
		ResultSet rs5 = adFckEditor.selectQuery(sql3);
		while (rs5.next()) {

			fckEditorForm = new FckEditorForm();
			fckEditorForm.setFileList(rs5.getString("file_name"));
			list.add(fckEditorForm);
		}
		request.setAttribute("listName", list);
		ArrayList list1 = new ArrayList();
		String sql4="select * from video_list where link_name='"+link_id+"' and " +
 		"sub_link_name='"+sub_link_name+"'  and " +
 		"sub_sub_linkname='"+underSubLink+"'";
		
		ResultSet rs6 = adFckEditor
				.selectQuery(sql4);
		while (rs6.next()) {
			fckEditorForm = new FckEditorForm();
			fckEditorForm.setVideoFilesList(rs6.getString("video_name"));
			list1.add(fckEditorForm);
		}
		request.setAttribute("listName1", list1);	
		adFckEditor.closeResultset();
		adFckEditor.closeStatement();
		adFckEditor.connClose();
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Exception caught =" + e.getMessage());
	}
	
	
	display(mapping, form, request, response);
	displaySublinks(mapping, form, request, response);
	
	return mapping.findForward("display");
}
public ActionForward deleteVideoList(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) {
	FckEditorForm fckEditorForm = (FckEditorForm) form;// TODO

	try {
		FckEditorDao adFckEditor = new FckEditorDao();	
	String link_id = fckEditorForm.getLinkName();
	String sub_link_name = fckEditorForm.getSubLinkName();
	String underSubLink = fckEditorForm.getUnderSubLinks();
	String linkId = fckEditorForm.getLinkName();
	String subLinkName = request.getParameter("subLinkName");
	NewsandMediaDao ad = new NewsandMediaDao();
		String deleteArray[] = fckEditorForm.getSelect1();
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
		int a = adFckEditor.SqlExecuteUpdate(deletesql);
		if (a > 0) {
			fckEditorForm.setMessage("Video Files Deleted Successfully");
		} else {
			fckEditorForm
					.setMessage("Error While Deleting Video Files ...");
		}
		ArrayList list = new ArrayList();
		String sql3="select * from filelist where link_name='"+link_id+"' and " +
 		"sub_link_name='"+sub_link_name+"'  and " +
 		"sub_sub_linkname='"+underSubLink+"'";
		ResultSet rs5 = adFckEditor.selectQuery(sql3);
		while (rs5.next()) {

			fckEditorForm = new FckEditorForm();
			fckEditorForm.setFileList(rs5.getString("file_name"));
			list.add(fckEditorForm);
		}
		request.setAttribute("listName", list);
		ArrayList list1 = new ArrayList();
		String sql4="select * from video_list where link_name='"+link_id+"' and " +
 		"sub_link_name='"+sub_link_name+"'  and " +
 		"sub_sub_linkname='"+underSubLink+"'";
		ResultSet rs6 = adFckEditor
				.selectQuery(sql4);
		while (rs6.next()) {
			fckEditorForm = new FckEditorForm();
			fckEditorForm.setVideoFilesList(rs6.getString("video_name"));
			list1.add(fckEditorForm);
		}
		request.setAttribute("listName1", list1);	
		adFckEditor.closeResultset();
		adFckEditor.closeStatement();
		adFckEditor.connClose();
	} catch (Exception e) {
		System.out.println("Exception caught =" + e.getMessage());
	}
	display(mapping, form, request, response);
	displaySublinks(mapping, form, request, response);
	return mapping.findForward("display");
}
	
public ActionForward selectContent(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) 
{
	
	
	FckEditorForm fckEditorForm = (FckEditorForm) form;
	String underSubLinks=request.getParameter("usl");
    fckEditorForm.setUnderSubLinks(underSubLinks);	 
    HttpSession session=request.getSession();
	FckEditorDao ad=new FckEditorDao();
	try
	{
		 String sql="select * from links where link_name='"+underSubLinks+"' and status=1";
		 ResultSet rs=ad.selectQuery(sql);
		 String linkName=null;
		 String sub_link_name=null;
		 String linkId=null;
		 while (rs.next()) 
		 {
			 linkId=rs.getString("id");
			 linkName=rs.getString("module");
			 sub_link_name=rs.getString("sub_linkname");
		}
		 session.setAttribute("linkId",linkId);
		 fckEditorForm.setLinkName(linkName);
		 fckEditorForm.setSubLinkName(sub_link_name);
		 fckEditorForm.setUnderSubLinks(underSubLinks);	 
	 String sql6="select * from links where module='"+linkName+"' and " +
 		"sub_linkname='"+sub_link_name+"'  and " +
 		"link_name='"+underSubLinks+"' and status=1";
	 ResultSet rs6=ad.selectQuery(sql);
	 String linkPath=null;
	 String method=null;
	 String priority=null;
	 String contentDescription=null;
	 while (rs6.next()) 
	 {
		 fckEditorForm.setLinkName(linkName);
		 fckEditorForm.setLinkPath(rs6.getString("link_path"));           
		 fckEditorForm.setMethodName(rs6.getString("method"));
		 fckEditorForm.setPriority(rs6.getString("priority"));
		 contentDescription=rs6.getString("content_description");
		 
	}
	 fckEditorForm.setContentDescription(contentDescription);
	 ArrayList list = new ArrayList();
	    String sql2="select * from links where module='"+linkName+"' and " +
		"sub_linkname='"+sub_link_name+"'  and " +
		"link_name='"+underSubLinks+"' and status=1 and file_name not like ''";
			ResultSet rs5 = ad.selectQuery(sql2);
			while (rs5.next()) {

				fckEditorForm = new FckEditorForm();
				fckEditorForm.setFileList(rs5.getString("file_name"));
				list.add(fckEditorForm);
			}
			request.setAttribute("listName", list);
			  ArrayList list1 = new ArrayList();
		      String sql3="select * from links where module='"+linkName+"' and " +
		 		"sub_linkname='"+sub_link_name+"'  and " +
		 		"link_name='"+underSubLinks+"' and status=1 and video_name not like ''";
				ResultSet rs8 = ad.selectQuery(sql3);
				while (rs8.next()) {
					fckEditorForm = new FckEditorForm();
					fckEditorForm.setVideoFilesList(rs8.getString("video_name"));
					list1.add(fckEditorForm);
				}
				request.setAttribute("listName1", list1);
	}catch (Exception e) 
	{
		 e.printStackTrace();
		 System.out.println(e.getMessage());
		 }
	display(mapping, form, request, response);
	displaySubSublinks(mapping, form, request, response);
	return mapping.findForward("displaycontent");
}

public ActionForward uploadFilesModify(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception 
		{
	FckEditorForm fckEditorForm = (FckEditorForm) form;
	try {
		FckEditorDao ad=new FckEditorDao();
		System.out.println("UPLOAD FILE MODIFY  METHOD");
		String linkName = fckEditorForm.getLinkName();
		String sub_link_name = fckEditorForm.getSubLinkName();
		String underSubLinks = fckEditorForm.getUnderSubLinks();
		 fckEditorForm.setLinkName(linkName);
		 fckEditorForm.setSubLinkName(sub_link_name);
		 fckEditorForm.setUnderSubLinks(underSubLinks);	 
	 String sql6="select * from links where module='"+linkName+"' and " +
 		"sub_linkname='"+sub_link_name+"'  and " +
 		"link_name='"+underSubLinks+"' and status=1";
	 ResultSet rs6=ad.selectQuery(sql6);
	 String linkPath=null;
	 String method=null;
	 String priority=null;
	 String contentDescription=null;
	 while (rs6.next()) 
	 {
		 fckEditorForm.setLinkPath(rs6.getString("link_path"));           
		 fckEditorForm.setMethodName(rs6.getString("method"));
		 fckEditorForm.setPriority(rs6.getString("priority")); 
		 contentDescription=rs6.getString("content_description");
	}
	 fckEditorForm.setContentDescription(contentDescription);	 
		FormFile myFile = fckEditorForm.getFileNames();
		String contentType = myFile.getContentType();
		String fileName = myFile.getFileName();
		byte[] fileData = myFile.getFileData();
		String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
		 if( fckEditorForm.getFileNames().getFileSize()== 0){
		    	fckEditorForm.setMessage("Please choose a Text file to Upload");
		    	display(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				ArrayList list = new ArrayList();
				String sql3="select * from links where module='"+linkName+"' and " +
				"sub_linkname='"+sub_link_name+"'  and " +
				"link_name='"+underSubLinks+"' and status=1 and file_name not like ''";
				ResultSet rs5 = ad.selectQuery(sql3);
				while (rs5.next()) 
				{
					fckEditorForm = new FckEditorForm();
					fckEditorForm.setFileList(rs5.getString("file_name"));
					list.add(fckEditorForm);
				}
				request.setAttribute("listName", list);
				ArrayList list1 = new ArrayList();
				String sql4="select * from links where module='"+linkName+"' and " +
				"sub_linkname='"+sub_link_name+"'  and " +
				"link_name='"+underSubLinks+"' and status=1 and video_name not like ''";
				ResultSet rs12 = ad
						.selectQuery(sql4);
				while (rs12.next()) {
					fckEditorForm = new FckEditorForm();
					fckEditorForm.setVideoFilesList(rs12.getString("video_name"));
					list1.add(fckEditorForm);
				}
				request.setAttribute("listName1", list1);	
				 String sql9="select * from links where module='"+linkName+"' and " +
			 		"sub_linkname='"+sub_link_name+"'  and " +
			 		"link_name='"+underSubLinks+"' and status=1";
				 ResultSet rs9=ad.selectQuery(sql9);
				 while (rs9.next()) 
				 {
					 fckEditorForm.setContentDescription(rs9.getString("content_description"));	 
				}
		     return mapping.findForward("displaycontent");
		    }
		   if(ext.equalsIgnoreCase("txt")||(ext.equalsIgnoreCase("doc"))||(ext.equalsIgnoreCase("docx"))||(ext.equalsIgnoreCase("pdf")))
		   {
		String filePath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+sub_link_name+"/"+underSubLinks+"/UploadFiles";
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
		String sqlselect="select * from links where module='"+linkName+"' and " +
		"sub_linkname='"+sub_link_name+"'  and " +
		"link_name='"+underSubLinks+"' and status=1 and file_name not like ''";
		ResultSet rs15 = ad.selectQuery(sqlselect);
		String FileList="";
			while (rs15.next())
			{
				FileList=rs15.getString("file_name");
			}
		String	fileNameList="cms" + "/" + linkName+"/"+sub_link_name+"/"+underSubLinks+"/UploadFiles" + "/"+fileName;
		boolean b;
	    b = FileList.contains(fileNameList);
	    if(b)
	    {
	    	fckEditorForm.setMessage("This file  is  already uploaded..please choose another file");		
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
			String updatesql="update links set file_name='"+fileNames+"' where module='"+linkName+"' and " +
					"sub_linkname='"+sub_link_name+"'  and " +
					"link_name='"+underSubLinks+"' and status=1" ;
			int a = ad.SqlExecuteUpdate(updatesql);
			if (a > 0)
			{
				fckEditorForm.setMessage("Documents Uploaded Successfully");
			} else
			{
				fckEditorForm.setMessage("Error While Uploading Files ... Please check Entered Values");
			}	
	    }
		ArrayList list = new ArrayList();
		String sql3="select * from links where module='"+linkName+"' and " +
		"sub_linkname='"+sub_link_name+"'  and " +
		"link_name='"+underSubLinks+"' and status=1 and file_name not like ''";
		ResultSet rs5 = ad.selectQuery(sql3);
		while (rs5.next()) 
		{
			fckEditorForm = new FckEditorForm();
			fckEditorForm.setFileList(rs5.getString("file_name"));
			list.add(fckEditorForm);
		}
		request.setAttribute("listName", list);
		   }
			else
			{
				fckEditorForm.setMessage("Only File(.TXT,.PDF,.DOC,.DOCX) File Format is Allowed");
				display(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
				ArrayList list = new ArrayList();
				String sql3="select * from links where module='"+linkName+"' and " +
				"sub_linkname='"+sub_link_name+"'  and " +
				"link_name='"+underSubLinks+"' and status=1 and file_name not like ''";
				ResultSet rs5 = ad.selectQuery(sql3);
				while (rs5.next()) 
				{
					fckEditorForm = new FckEditorForm();
					fckEditorForm.setFileList(rs5.getString("file_name"));
					list.add(fckEditorForm);
				}
				request.setAttribute("listName", list);
				ArrayList list1 = new ArrayList();
				String sql4="select * from links where module='"+linkName+"' and " +
				"sub_linkname='"+sub_link_name+"'  and " +
				"link_name='"+underSubLinks+"' and status=1 and video_name not like ''";
				ResultSet rs13 = ad
						.selectQuery(sql4);
				while (rs13.next()) 
				{
					fckEditorForm = new FckEditorForm();
					fckEditorForm.setVideoFilesList(rs13.getString("video_name"));
					list1.add(fckEditorForm);
				}
				String sql20="select * from links where module='"+linkName+"' and " +
		 		"sub_linkname='"+sub_link_name+"'  and " +
		 		"link_name='"+underSubLinks+"' and status=1";
			 ResultSet rs10=ad.selectQuery(sql20);
			 while (rs10.next()) 
			 {
				 fckEditorForm.setContentDescription(rs10.getString("content_description"));
			}
				request.setAttribute("listName1", list1);	
		 	   return mapping.findForward("displaycontent");
			}
		   ArrayList list1 = new ArrayList();
			String sql4="select * from links where module='"+linkName+"' and " +
			"sub_linkname='"+sub_link_name+"'  and " +
			"link_name='"+underSubLinks+"' and status=1 and video_name not like ''";
			ResultSet rs14 = ad
					.selectQuery(sql4);
			while (rs14.next()) {
				fckEditorForm = new FckEditorForm();
				fckEditorForm.setVideoFilesList(rs14.getString("video_name"));
				list1.add(fckEditorForm);
			}
			request.setAttribute("listName1", list1);
		   ad.closeResultset();
		   ad.closeStatement();
			ad.connClose(); 
	}catch (Exception e) {
		e.printStackTrace();
	}
	display(mapping, form, request, response);
	displaySublinks(mapping, form, request, response);
	return mapping.findForward("displaycontent");	
       }


public ActionForward deleteFileListModify(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) 
{
	FckEditorForm fckEditorForm = (FckEditorForm) form;
	try
	{
	System.out.println("DELETE FILE MODIFY METHOD");
	FckEditorDao ad=new FckEditorDao();
	String uncheckValues=request.getParameter("unValues");
	String checkValues=request.getParameter("cValues");
	String linkName = fckEditorForm.getLinkName();
	String sub_link_name = fckEditorForm.getSubLinkName();
	String underSubLinks = fckEditorForm.getUnderSubLinks();
	String sql26="select * from links where module='"+linkName+"' and " +
	"sub_linkname='"+sub_link_name+"'  and " +
	"link_name='"+underSubLinks+"' and status=1";
ResultSet rs16=ad.selectQuery(sql26);
while (rs16.next()) 
{
 fckEditorForm.setContentDescription(rs16.getString("content_description"));
 
}
	 fckEditorForm.setLinkName(linkName);
	 fckEditorForm.setSubLinkName(sub_link_name);
	 fckEditorForm.setUnderSubLinks(underSubLinks);	 
	 uncheckValues = uncheckValues.substring(0, uncheckValues.length());
	 checkValues = checkValues.substring(0, checkValues.length());
		String v[] = uncheckValues.split(",");
	 String upFileName=null;
		String upFileName2="";
		for(int i=0;i<v.length;i++)
		{
		upFileName="cms" + "/" + linkName+"/"+sub_link_name+"/"+underSubLinks+"/UploadFiles" + "/"+v[i];
	    upFileName2+=upFileName+",";
		
		}
		String up2="cms" + "/" + linkName+"/"+sub_link_name+"/"+underSubLinks+"/UploadFiles"+"/";
		upFileName2 = upFileName2.substring(0, upFileName2.length()-1);
		String updatesql=null;
		if(upFileName2.equalsIgnoreCase(up2))
		{
	 updatesql="update links set file_name='' where module='"+linkName+"' and " +
	"sub_linkname='"+sub_link_name+"'  and " +
	"link_name='"+underSubLinks+"' and status=1" ;
		}
		else
		{
		 updatesql="update links set file_name='"+upFileName2+"' where module='"+linkName+"' and " +
			"sub_linkname='"+sub_link_name+"'  and " +
			"link_name='"+underSubLinks+"' and status=1" ;
		}
int a = ad.SqlExecuteUpdate(updatesql);
if (a > 0) {
fckEditorForm.setMessage("Documents deleted Successfully");
ArrayList list = new ArrayList();
String sql3="select * from links where module='"+linkName+"' and " +
"sub_linkname='"+sub_link_name+"'  and " +
"link_name='"+underSubLinks+"' and status=1 and file_name not like ''";
ResultSet rs5 = ad.selectQuery(sql3);
while (rs5.next()) 
{
	fckEditorForm = new FckEditorForm();
	fckEditorForm.setFileList(rs5.getString("file_name"));
	list.add(fckEditorForm);
}
request.setAttribute("listName", list);
ArrayList list1 = new ArrayList();
String sql4="select * from links where module='"+linkName+"' and " +
"sub_linkname='"+sub_link_name+"'  and " +
"link_name='"+underSubLinks+"' and status=1 and video_name not like ''";
ResultSet rs13 = ad
		.selectQuery(sql4);
while (rs13.next()) 
{
	fckEditorForm = new FckEditorForm();
	fckEditorForm.setVideoFilesList(rs13.getString("video_name"));
	list1.add(fckEditorForm);
}
request.setAttribute("listName1", list1);
String sql226="select * from links where module='"+linkName+"' and " +
"sub_linkname='"+sub_link_name+"'  and " +
"link_name='"+underSubLinks+"' and status=1";
ResultSet rs116=ad.selectQuery(sql226);
while (rs116.next()) 
{
fckEditorForm.setContentDescription(rs116.getString("content_description"));
}
display(mapping, form, request, response);
displaySublinks(mapping, form, request, response);
return mapping.findForward("displaycontent");
} 
else {
fckEditorForm.setMessage("Error While deleteing Files ... Please check Entered Values");
display(mapping, form, request, response);
displaySublinks(mapping, form, request, response);

return mapping.findForward("displaycontent");
}
	}catch (Exception e) {
		e.printStackTrace();
	}
   display(mapping, form, request, response);
	displaySublinks(mapping, form, request, response);
	return mapping.findForward("displaycontent");
}

public ActionForward uploadVideosModify(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception 
		{
	FckEditorForm fckEditorForm = (FckEditorForm) form;
	try {
		FckEditorDao ad=new FckEditorDao();
		System.out.println("UPLOAD FILE MODIFY  METHOD");
		String linkName = fckEditorForm.getLinkName();
		String sub_link_name = fckEditorForm.getSubLinkName();
		String underSubLinks = fckEditorForm.getUnderSubLinks();
		 fckEditorForm.setLinkName(linkName);
		 fckEditorForm.setSubLinkName(sub_link_name);
		 fckEditorForm.setUnderSubLinks(underSubLinks);	 
	 String sql6="select * from links where module='"+linkName+"' and " +
 		"sub_linkname='"+sub_link_name+"'  and " +
 		"link_name='"+underSubLinks+"' and status=1";
	 ResultSet rs6=ad.selectQuery(sql6);
	 String linkPath=null;
	 String method=null;
	 String priority=null;
	 String contentDescription=null;
	 while (rs6.next()) 
	 {
		 fckEditorForm.setLinkPath(rs6.getString("link_path"));           
		 fckEditorForm.setMethodName(rs6.getString("method"));
		 fckEditorForm.setPriority(rs6.getString("priority"));
		 contentDescription=rs6.getString("content_description");
	}
	 FormFile myFile = fckEditorForm.getVideoFileNames();
		String contentType = myFile.getContentType();
		String videoName = myFile.getFileName();
		byte[] fileData = myFile.getFileData();
		String ext = videoName.substring(videoName.lastIndexOf('.') + 1);
		 if( fckEditorForm.getVideoFileNames().getFileSize()== 0){
		    	fckEditorForm.setMessage("Please choose a Video file to Upload");
		    	display(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
		    	String sq29="select * from links where module='"+linkName+"' and " +
		 		"sub_linkname='"+sub_link_name+"'  and " +
		 		"link_name='"+underSubLinks+"' and status=1";
			 ResultSet rs19=ad.selectQuery(sq29);
			 while (rs19.next()) 
			 {
			 	 fckEditorForm.setContentDescription(rs19.getString("content_description")); 
			 }
				 ArrayList list = new ArrayList();
					String sql3="select * from links where module='"+linkName+"' and " +
			 		"sub_linkname='"+sub_link_name+"'  and " +
			 		"link_name='"+underSubLinks+"' and status=1 and file_name not like ''";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) {
						fckEditorForm = new FckEditorForm();
						fckEditorForm.setFileList(rs5.getString("file_name"));
						list.add(fckEditorForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where module='"+linkName+"' and " +
			 		"sub_linkname='"+sub_link_name+"'  and " +
			 		"link_name='"+underSubLinks+"' and status=1 and video_name not like ''";
					ResultSet rs15 = ad
							.selectQuery(sql4);
					while (rs15.next()) {
						fckEditorForm = new FckEditorForm();
						fckEditorForm.setVideoFilesList(rs15.getString("video_name"));
						list1.add(fckEditorForm);
					}
					request.setAttribute("listName1", list1);	
					String sql9="select * from links where module='"+linkName+"' and " +
			 		"sub_linkname='"+sub_link_name+"'  and " +
			 		"link_name='"+underSubLinks+"' and status=1";
				 ResultSet rs9=ad.selectQuery(sql9);
				 while (rs9.next()) 
				 {
					 fckEditorForm.setContentDescription(rs9.getString("content_description"));	 
				}
		 	   return mapping.findForward("displaycontent");
		    }
		   if(ext.equalsIgnoreCase("mp4")||(ext.equalsIgnoreCase("ogv")))
		   {
		String videoPath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+sub_link_name+"/"+underSubLinks+"/UploadVideos";
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
		String sqlselect="select * from links where module='"+linkName+"' and " +
		"sub_linkname='"+sub_link_name+"'  and " +
		"link_name='"+underSubLinks+"' and status=1 and video_name not like ''";
		ResultSet rs17 = ad.selectQuery(sqlselect);
		String videoList="";
		while (rs17.next())
		{
			videoList=rs17.getString("video_name");
		}
		String	videoNameList="cms" + "/" + linkName+"/"+sub_link_name+"/"+underSubLinks+"/UploadVideos" + "/"+videoName;
		boolean b;
	    b = videoList.contains(videoNameList);
	    if(b)
	    {
	    	fckEditorForm.setMessage("This video  is  already uploaded..please choose another file");			
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
		String updatesql="update links set video_name='"+videoNames+"' where module='"+linkName+"' and " +
				"sub_linkname='"+sub_link_name+"'  and " +
				"link_name='"+underSubLinks+"' and status=1" ;
		int a = ad.SqlExecuteUpdate(updatesql);
		if (a > 0) 
		{
			fckEditorForm.setMessage("Video Uploaded Successfully");
			String sq22="select * from links where module='"+linkName+"' and " +
	 		"sub_linkname='"+sub_link_name+"'  and " +
	 		"link_name='"+underSubLinks+"' and status=1";
		 ResultSet rs9=ad.selectQuery(sq22);
		 while (rs9.next()) 
		 {
			 fckEditorForm.setContentDescription(rs9.getString("content_description")); 
		}
		} 
		else 
		{
			fckEditorForm.setMessage("Error While Uploading video ... Please check Entered Values");
		}
	    }
		ArrayList list1 = new ArrayList();
		
		String sql4="select * from links where module='"+linkName+"' and " +
 		"sub_linkname='"+sub_link_name+"'  and " +
 		"link_name='"+underSubLinks+"' and status=1 and video_name not like ''";
		ResultSet rs11 = ad
				.selectQuery(sql4);
		while (rs11.next()) {
			fckEditorForm = new FckEditorForm();
			fckEditorForm.setVideoFilesList(rs11.getString("video_name"));
			list1.add(fckEditorForm);
		}
		request.setAttribute("listName1", list1);	
		   }
			else
			{
				fckEditorForm.setMessage("Only Video(MP4,OGV) File Format is Allowed");
				display(mapping, form, request, response);
				displaySublinks(mapping, form, request, response);
		    	String sq29="select * from links where module='"+linkName+"' and " +
		 		"sub_linkname='"+sub_link_name+"'  and " +
		 		"link_name='"+underSubLinks+"' and status=1 ";
			 ResultSet rs19=ad.selectQuery(sq29);
			 while (rs19.next()) 
			 {
				 fckEditorForm.setContentDescription(rs19.getString("content_description"));	 
			}
				 ArrayList list = new ArrayList();
					String sql3="select * from links where module='"+linkName+"' and " +
			 		"sub_linkname='"+sub_link_name+"'  and " +
			 		"link_name='"+underSubLinks+"' and status=1 and file_name not like ''";
					ResultSet rs5 = ad.selectQuery(sql3);
					while (rs5.next()) {
							
						fckEditorForm = new FckEditorForm();
						fckEditorForm.setFileList(rs5.getString("file_name"));
						list.add(fckEditorForm);
					}
					request.setAttribute("listName", list);
					ArrayList list1 = new ArrayList();
					String sql4="select * from links where module='"+linkName+"' and " +
			 		"sub_linkname='"+sub_link_name+"'  and " +
			 		"link_name='"+underSubLinks+"' and status=1 and video_name not like ''";
					ResultSet rs15 = ad
							.selectQuery(sql4);
					while (rs15.next()) {
						fckEditorForm = new FckEditorForm();
						fckEditorForm.setVideoFilesList(rs15.getString("video_name"));
						list1.add(fckEditorForm);
					}
					request.setAttribute("listName1", list1);	
		 	   return mapping.findForward("displaycontent");
			}
		   ArrayList list = new ArrayList();
			String sql3="select * from links where module='"+linkName+"' and " +
	 		"sub_linkname='"+sub_link_name+"'  and " +
	 		"link_name='"+underSubLinks+"' and status=1 and file_name not like ''";
			ResultSet rs5 = ad.selectQuery(sql3);
			while (rs5.next()) {
				fckEditorForm = new FckEditorForm();
				fckEditorForm.setFileList(rs5.getString("file_name"));
				list.add(fckEditorForm);
			}
			request.setAttribute("listName", list);
			String sq29="select * from links where module='"+linkName+"' and " +
	 		"sub_linkname='"+sub_link_name+"'  and " +
	 		"link_name='"+underSubLinks+"' and status=1";
		 ResultSet rs19=ad.selectQuery(sq29);
		 while (rs19.next()) 
		 {
			 fckEditorForm.setContentDescription(rs19.getString("content_description")); 
		}
			ad.closeResultset();
			ad.closeStatement();
			ad.connClose();
	} catch (Exception e) {
		e.printStackTrace();
	}
	display(mapping, form, request, response);
	displaySublinks(mapping, form, request, response);
	return mapping.findForward("displaycontent");
       }

public ActionForward deleteVideoListModify(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) 
{
	FckEditorForm fckEditorForm = (FckEditorForm) form;
	try
	{
		System.out.println("DELETE VIDEO MODIFY METHOD");
		FckEditorDao ad=new FckEditorDao();
	String checkValues=request.getParameter("cValues");
	String uncheckValues=request.getParameter("unValues");
	String linkName = fckEditorForm.getLinkName();
	String sub_link_name = fckEditorForm.getSubLinkName();
	String underSubLinks = fckEditorForm.getUnderSubLinks();
	 fckEditorForm.setLinkName(linkName);
	 fckEditorForm.setSubLinkName(sub_link_name);
	 fckEditorForm.setUnderSubLinks(underSubLinks);	 
	 String sql26="select * from links where module='"+linkName+"' and " +
		"sub_linkname='"+sub_link_name+"'  and " +
		"link_name='"+underSubLinks+"' and status=1";
	ResultSet rs16=ad.selectQuery(sql26);
	while (rs16.next()) 
	{
	 fckEditorForm.setContentDescription(rs16.getString("content_description"));
	}
	 uncheckValues = uncheckValues.substring(0, uncheckValues.length());
		String v[] = uncheckValues.split(",");
	    String upVideoName=null;
		String upVideoName2="";
		for(int i=0;i<v.length;i++)
		{
			upVideoName="cms" + "/" + linkName+"/"+sub_link_name+"/"+underSubLinks+"/UploadVideos" + "/"+v[i];
	    System.out.println("upVideoName="+upVideoName);
	    upVideoName2+=upVideoName+",";
		}
        String up2="cms" + "/" + linkName+"/"+sub_link_name+"/"+underSubLinks+"/UploadVideos"+"/";
        upVideoName2 = upVideoName2.substring(0, upVideoName2.length()-1);
		String updatesql=null;
		if(upVideoName2.equalsIgnoreCase(up2))
		{
	 updatesql="update links set video_name='' where module='"+linkName+"' and " +
	"sub_linkname='"+sub_link_name+"'  and " +
	"link_name='"+underSubLinks+"' and status=1" ;
		}
		else
		{
			 updatesql="update links set video_name='"+upVideoName2+"' where module='"+linkName+"' and " +
			"sub_linkname='"+sub_link_name+"'  and " +
			"link_name='"+underSubLinks+"' and status=1";
		} 
int a = ad.SqlExecuteUpdate(updatesql);
if (a > 0) {
fckEditorForm.setMessage("Video deleted Successfully");
ArrayList list = new ArrayList();
String sql3="select * from links where module='"+linkName+"' and " +
"sub_linkname='"+sub_link_name+"'  and " +
"link_name='"+underSubLinks+"' and status=1 and file_name not like ''";
ResultSet rs5 = ad.selectQuery(sql3);
while (rs5.next()) 
{
	fckEditorForm = new FckEditorForm();
	fckEditorForm.setFileList(rs5.getString("file_name"));
	list.add(fckEditorForm);
 }
request.setAttribute("listName", list);
ArrayList list1 = new ArrayList();
String sql4="select * from links where module='"+linkName+"' and " +
"sub_linkname='"+sub_link_name+"'  and " +
"link_name='"+underSubLinks+"' and status=1  and video_name not like ''";
ResultSet rs13 = ad
		.selectQuery(sql4);
while (rs13.next()) 
{
	fckEditorForm = new FckEditorForm();
	fckEditorForm.setVideoFilesList(rs13.getString("video_name"));
	list1.add(fckEditorForm);
 }
String sq29="select * from links where module='"+linkName+"' and " +
	"sub_linkname='"+sub_link_name+"'  and " +
	"link_name='"+underSubLinks+"' and status=1";
ResultSet rs19=ad.selectQuery(sq29);
while (rs19.next()) 
{
 fckEditorForm.setContentDescription(rs19.getString("content_description"));
}
request.setAttribute("listName1", list1);
display(mapping, form, request, response);
displaySublinks(mapping, form, request, response);
return mapping.findForward("displaycontent");
} else {
fckEditorForm.setMessage("Error While deleteing videos ... Please check Entered Values");
display(mapping, form, request, response);
displaySublinks(mapping, form, request, response);
return mapping.findForward("displaycontent");
}	
 }catch (Exception e) {
		e.printStackTrace();
	}
   display(mapping, form, request, response);
	displaySublinks(mapping, form, request, response);
	return mapping.findForward("displaycontent");
}
public ActionForward updateContent(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception 
		{
	FckEditorForm fckEditorForm = (FckEditorForm) form;
	HttpSession session=request.getSession();
		try
		{
		FckEditorDao ad=new FckEditorDao();
		System.out.println("***UPDATE THE CONTENT***");
		String linkName = fckEditorForm.getLinkName();
		String sub_link_name = fckEditorForm.getSubLinkName();
		String underSubLinks = fckEditorForm.getUnderSubLinks();
		String linkPath="newsAndMedia.do";
		String method="displayContent";
		String priority=fckEditorForm.getPriority();
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
content_description=content_description.replaceAll("'", "''");
		String linkid=(String) session.getAttribute("linkId");
		String updatesql2="update links set link_name='"+underSubLinks+"' where module='"+linkName+"' and " +
 		"sub_linkname='"+sub_link_name+"'  and " +
 		"id='"+linkid+"' and status=1";
		ad.SqlExecuteUpdate(updatesql2);
		String sqlselect="select * from links where module='"+linkName+"' and " +
 		"sub_linkname='"+sub_link_name+"'  and " +
 		"link_name='"+underSubLinks+"' and status=1";
		ResultSet rs17 = ad.selectQuery(sqlselect);
		String fileList=null;
		String videoList=null;
		while (rs17.next()) 
		{   
			fileList=rs17.getString("file_name");
			videoList=rs17.getString("video_name");
		}
		String updatesql="update links set link_path='"+linkPath+"',method='"+method+"',priority='"+priority+"',video_name='"+videoList+"',file_name='"+fileList+"',content_description='"+content_description+"' where module='"+linkName+"' and " +
 		"sub_linkname='"+sub_link_name+"'  and " +
 		"link_name='"+underSubLinks+"' and status=1";
int a = ad.SqlExecuteUpdate(updatesql);
if (a > 0) {
	fckEditorForm.setMessage("Your details have been updated successfully");
	fckEditorForm.setLinkName("");
	fckEditorForm.setSubLinkName("");
	fckEditorForm.setUnderSubLinks("");
	fckEditorForm.setLinkPath("");
	fckEditorForm.setMethodName("");
	fckEditorForm.setPriority("");
} else {
	fckEditorForm.setMessage("Error While updateing your details  ... Please check Entered Values");
}	
		}catch (Exception e) {
			e.printStackTrace();
		}
	display(mapping, form, request, response);
	displaySublinks(mapping, form, request, response);
	return mapping.findForward("displaycontent");	
       }
}