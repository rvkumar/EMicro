package com.microlabs.admin.action;

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
import com.microlabs.admin.form.LinksArchiveForm;

public class LinksArchiveAction extends DispatchAction {

	public ActionForward displayMainLinks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {

		LinksDao ad = new LinksDao();
		System.out.println("displayMainLinks()----");
		LinksArchiveForm linksForm = (LinksArchiveForm) form;// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String linkName = linksForm.getLinkName();
		String subLinkName = linksForm.getSubLinkName();
		String linkPath = linksForm.getLinkPath();
		String methodName = linksForm.getMethodName();
		String priority = linksForm.getPriority();
		try {
		
			ResultSet rs = ad.selectQuery("select * from links where status is null");
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
			
			while(rs.next()) {
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
		
		
		if(id!=null){
		
		
		String sql = "select * from links where module='" + id + "' and status=1 ";
		ResultSet rs = ad.selectQuery(sql);
		try {
			LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();
			
			while(rs.next()) {
				hm.put(rs.getString("link_path")+"?method="+rs.getString("method") + "&sId="+rs.getString("id"),(rs.getString("link_name")+','+rs.getString("icon_name")));
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


public ActionForward displayLinksContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {

		System.out.println("displayLinksContent()----");
		LinksArchiveForm linksForm = (LinksArchiveForm) form;// TODO Auto-generated method stub
		try 
		{
		String module=request.getParameter("MenuIcon2");
		request.setAttribute("MenuIcon",module);
		
		String linksId = request.getParameter("sId");
		String linkName = linksForm.getLinkName();
		System.out.println("Link Name="+linkName);
		
		
		linksForm.setLinkName(linkName);
		LinksDao adlinks = new LinksDao();
		int count=0;
		String arcStatus="";
		linksForm.setArchiveStatus("");
		linksForm.setContentDescriptionAdmin("");
		String getArchievDataCount="select count(*) from archieves where link_name='"+linkName+"'  and  year='"+linksForm.getYear()+"'";
		ResultSet rsArchievDataCount=adlinks.selectQuery(getArchievDataCount);
		while(rsArchievDataCount.next())
		{
			count=rsArchievDataCount.getInt(1);
		}
		String files="";
		String videos="";
		ArrayList fileList = new ArrayList();
		ArrayList videoList = new ArrayList();
		if(count>=1){
		String getArchievData="select * from archieves where link_name='"+linkName+"' and  year='"+linksForm.getYear()+"'  ";
		System.out.println("getArchievData="+getArchievData);
		ResultSet rsArchiveData=adlinks.selectQuery(getArchievData);
		String file;
		
		
		while(rsArchiveData.next())
		{
			linksForm.setArchiveStatus(rsArchiveData.getString("status"));
			linksForm.setContentDescriptionAdmin(rsArchiveData.getString("content_description"));	
			arcStatus=rsArchiveData.getString("status");
			if(arcStatus.equalsIgnoreCase("yes"))
			{
				linksForm.setArchiveStatus(rsArchiveData.getString("status"));	
			}
						
			files=rsArchiveData.getString("file_name");
			 if(files.equalsIgnoreCase("")){
    	   
       }else{
			String v[] = files.split(",");
			int l = v.length;
			for (int i = 0; i < l; i++) 
			{
				LinksArchiveForm linksForm1=new LinksArchiveForm();
			int x=v[i].lastIndexOf("/");
			files=v[i].substring(x+1);				
			linksForm1.setFileList(files);
			
			/*String insertFiles = "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname) values('"
				+linkName+"','','"+files+"','','')";
			System.out.println("insertFiles="+insertFiles);
		 adlinks.SqlExecuteUpdate(insertFiles);*/
			
			fileList.add(linksForm1);
			}
       }
			 
			videos=rsArchiveData.getString("video_name");
			if(videos.equalsIgnoreCase("")){
	        	   
	           }else{
			String v1[] = videos.split(",");
			int l1 = v1.length;
			for (int i = 0; i < l1; i++) 
			{
				LinksArchiveForm linksFormVideo=new LinksArchiveForm();
			int y=v1[i].lastIndexOf("/");
			videos=v1[i].substring(y+1);	
			linksFormVideo.setFileList(videos);
		/*	String insertVideos = "insert into video_list(link_name,video_path,video_name,sub_link_name,sub_sub_linkname) values('"
				+linkName+"','','"+videos+"','','')";
			System.out.println("insertFiles="+insertVideos);
		 adlinks.SqlExecuteUpdate(insertVideos);*/
			videoList.add(linksFormVideo);
			}
	           }
		}
		}
		if(files.equalsIgnoreCase(""))
		{
			
		}else{
		request.setAttribute("listName", fileList);
		
		}
		if(videos.equalsIgnoreCase(""))
		{
			
		}else{
		request.setAttribute("videosList", videoList);
		}
		
		HttpSession session=request.getSession();
		
			LinksDao ad = new LinksDao();
			if(linkName.equalsIgnoreCase("Login Help")){
			
				
				String sql="select * from links where link_name='"+linkName + "' and " +
				"status is null  ";
		System.out.println("sql="+sql);
		ResultSet rs = ad.selectQuery(sql);
		String linkId=null;
		while (rs.next()) {
			linkId=rs.getString("id");
			linksForm.setSubLinkName(rs.getString("link_name"));
			linksForm.setSubLinkId(linksId);
			linksForm.setLinkId(rs.getString("id"));
			linksForm.setLinkPath(rs.getString("link_path"));
			linksForm.setMethodName(rs.getString("method"));
			linksForm.setPriority(rs.getString("priority"));
			//linksForm.setContentDescriptionAdmin(rs.getString("content_description"));	
		}
		session.setAttribute("linkId",linkId);
	
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
				linksForm.setSubLinkName(rs.getString("link_name"));
				linksForm.setSubLinkId(linksId);
				linksForm.setLinkId(rs.getString("id"));
				linksForm.setLinkPath(rs.getString("link_path"));
				linksForm.setMethodName(rs.getString("method"));
				linksForm.setPriority(rs.getString("priority"));
			//	linksForm.setContentDescriptionAdmin(rs.getString("content_description"));	
			}
			session.setAttribute("linkId",linkId);
			
			
		}
		
		displayMainLinks(mapping, form, request, response);
		request.setAttribute("displaySublinkField", "displaySublinkField");
		
		
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayMainLinks");
	}

public ActionForward uploadVideo(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
	try{
		LinksArchiveForm linksForm = (LinksArchiveForm) form;
		LinksDao ad = new LinksDao();
		String link_id=linksForm.getLinkName();
		linksForm.setLinkName(link_id);
		String content_description = "";
		 Enumeration<String> params = request.getParameterNames();
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
		String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/News And Media/Archieves")+"/"+link_id+"/UploadVideos"+"/"+linksForm.getYear();
		
		File destinationDir = new File(filePath);
		if(!destinationDir.exists())
		{
			destinationDir.mkdirs();
		}

			
			
			File fileToCreate = new File(filePath, fileName);
			System.out.println("filePath="+filePath);
			System.out.println("fileName="+fileName);
			System.out.println("fileToCreate="+fileToCreate);
			
			
				//error
				FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
				
				fileOutStream.write(myFile.getFileData());
				fileOutStream.flush();
				fileOutStream.close();
			
			filePath = filePath.replace("\\", "\\");	
			System.out.println("filePath="+filePath);
			String insertsql = "insert into video_list(link_name,video_path,video_name,sub_link_name,sub_sub_linkname,year) values('"
				+link_id
				+"','"	+ filePath
				+"','"+fileName+"','','','"+linksForm.getYear()+"')";
			System.out.println("insertsql="+insertsql);
		int a = ad.SqlExecuteUpdate(insertsql);
		if (a > 0) {
			//ad.setMessage("Documents Uploaded Successfully");
		}else{
			//ad.setMessage("Error While Uploading Files ... Please check Entered Values");
		}
		ArrayList fileList = new ArrayList();
		String getFiles="select * from filelist where link_name='"+link_id+"' and year='"+linksForm.getYear()+"'";
		
		ResultSet rsgetFiles = ad.selectQuery(getFiles);
		while (rsgetFiles.next())
		{
			linksForm = new LinksArchiveForm();
			linksForm.setFileList(rsgetFiles.getString("file_name"));
			fileList.add(linksForm);
		}
		request.setAttribute("listName", fileList);
		ArrayList videoList = new ArrayList();
		String sql3="select * from video_list where link_name='"+link_id+"' ";
		ResultSet rs12 = ad.selectQuery(sql3);
		while (rs12.next())
		{
			linksForm = new LinksArchiveForm();
			linksForm.setFileList(rs12.getString("video_name"));
			videoList.add(linksForm);
		}
		request.setAttribute("videosList", videoList);
		
		
	
	displayMainLinks(mapping, form, request, response);
	//displayLinksContent(mapping, form, request, response);
	request.setAttribute("displaySublinkField", "displaySublinkField");
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("displayMainLinks");
	}

public ActionForward uploadFile(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {


	try{
		LinksArchiveForm linksForm = (LinksArchiveForm) form;
		LinksDao ad = new LinksDao();
		String link_id=linksForm.getLinkName();
		linksForm.setLinkName(link_id);
		String content_description = "";
		 Enumeration<String> params = request.getParameterNames();
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
		//jsp/EMicro Files/News And Media/Archieves/Links Files/UploadFiles
		String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/News And Media/Archieves")+"/"+link_id+"/UploadFiles"+"/"+linksForm.getYear();
		
		File destinationDir = new File(filePath);
		if(!destinationDir.exists())
		{
			destinationDir.mkdirs();
		}

			
			
			File fileToCreate = new File(filePath, fileName);
			System.out.println("filePath="+filePath);
			System.out.println("fileName="+fileName);
			System.out.println("fileToCreate="+fileToCreate);
			
			
				//error
				FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
				
				fileOutStream.write(myFile.getFileData());
				fileOutStream.flush();
				fileOutStream.close();
			
			filePath = filePath.replace("\\", "\\");	
			System.out.println("filePath="+filePath);
			String insertsql = "insert into filelist(link_name,file_path,file_name,sub_link_name,sub_sub_linkname,year) values('"
				+link_id
				+"','"+filePath
				+"','"+fileName+"','','','"+linksForm.getYear()+"')";
			System.out.println("insertsql="+insertsql);
		int a = ad.SqlExecuteUpdate(insertsql);
		if (a > 0) {
			linksForm.setMessage("Documents Uploaded Successfully");
		}else{
			linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
		}
		ArrayList list = new ArrayList();
		String sql3="select * from filelist where link_name='"+link_id+"' and sub_link_name='' and sub_sub_linkname='' and year='"+linksForm.getYear()+"' ";
		ResultSet rs12 = ad.selectQuery(sql3);
		while (rs12.next())
		{
			linksForm = new LinksArchiveForm();
			linksForm.setFileList(rs12.getString("file_name"));
			list.add(linksForm);
		}
		request.setAttribute("listName", list);
		
		ArrayList videoList = new ArrayList();
		int videocount=0;
		String getVideoscount="select count(*) from video_list where link_name='"+link_id+"' and  sub_link_name='' and sub_sub_linkname=''  and year='"+linksForm.getYear()+"'";
		ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
		while(rsgetVideoscount.next())
		{
			videocount=rsgetVideoscount.getInt(1);
		}
		if(videocount>=1){
		String getVideos="select * from video_list where link_name='"+link_id+"' and sub_sub_linkname='' and year='"+linksForm.getYear()+"' ";
		ResultSet rsgetVideos = ad.selectQuery(getVideos);
		while (rsgetVideos.next())
		{
			linksForm = new LinksArchiveForm();
			linksForm.setFileList(rsgetVideos.getString("video_name"));
			videoList.add(linksForm);
		}
		request.setAttribute("videosList", videoList);
		}
		
		
		
		request.setAttribute("displaySublinkField", "displaySublinkField");
	
	displayMainLinks(mapping, form, request, response);
	//displayLinksContent(mapping, form, request, response);
     
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("displayMainLinks");
}
public ActionForward deleteMainLinkUploadedVideo(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
	try{
		LinksArchiveForm linksForm = (LinksArchiveForm) form;
		LinksDao ad = new LinksDao();
		
		String content_description = "";
		 Enumeration<String> params = request.getParameterNames();
			String parameter;
			
			 	while (params.hasMoreElements()) {
	 		parameter = params.nextElement();
	 		if (parameter.equalsIgnoreCase("EditorDefault")) {
	 			content_description += request.getParameter(parameter);
	 		}
	 	}
		
			 	linksForm.setContentDescriptionAdmin(content_description);
		String videoList[]=request.getParameterValues("checkedVideofiles");
		String videos="";
		if(videoList.length==0)
		{
			videos="";
		}else{
		
			for(int i=0;i<videoList.length;i++)
			{
				
				String deleteVideos="delete from video_list where link_name='"+linksForm.getLinkName()+"' and video_name='"+videoList[i]+"' and year='"+linksForm.getYear()+"'";
				int a=0;
				a=ad.SqlExecuteUpdate(deleteVideos);
				if (a > 0) {
					linksForm.setMessage("Videos Deleted Successfully");
				}else{
					linksForm.setMessage("Error While Deleted Videos ... Please check ");
				}
			}
		}
		
		
		//String videoList[]=request.getParameterValues("checkedVideofiles");
		
		int filecount=0;
		String getFilescount="select count(*) from filelist where link_name='"+linksForm.getLinkName()+"' and year='"+linksForm.getYear()+"' ";
		ResultSet rsgetFilescount = ad.selectQuery(getFilescount);
		while(rsgetFilescount.next())
		{
			filecount=rsgetFilescount.getInt(1);
		}
		if(filecount>=1){
		ArrayList list = new ArrayList();
		String sql3="select * from filelist where link_name='"+linksForm.getLinkName()+"' and year='"+linksForm.getYear()+"' ";
		ResultSet rs12 = ad.selectQuery(sql3);
		while (rs12.next())
		{
			linksForm = new LinksArchiveForm();
			linksForm.setFileList(rs12.getString("file_name"));
			list.add(linksForm);
		}
		request.setAttribute("listName", list);
		}
		ArrayList videoList1 = new ArrayList();
		int videocount=0;
		String getVideoscount="select count(*) from video_list where link_name='"+linksForm.getLinkName()+"' and year='"+linksForm.getYear()+"' ";
		ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
		while(rsgetVideoscount.next())
		{
			videocount=rsgetVideoscount.getInt(1);
		}
		if(videocount>=1){
		String getVideos="select * from video_list where link_name='"+linksForm.getLinkName()+"' and year='"+linksForm.getYear()+"' ";
		ResultSet rsgetVideos = ad.selectQuery(getVideos);
		while (rsgetVideos.next())
		{
			linksForm = new LinksArchiveForm();
			linksForm.setFileList(rsgetVideos.getString("video_name"));
			videoList1.add(linksForm);
		}
		request.setAttribute("videosList", videoList1);
		}
		
		
		
		
		request.setAttribute("displaySublinkField", "displaySublinkField");
	
	displayMainLinks(mapping, form, request, response);
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("displayMainLinks");
}
public ActionForward deleteUploadedMainFile(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {

	try{
		LinksArchiveForm linksForm = (LinksArchiveForm) form;
		LinksDao ad = new LinksDao();
		
		String content_description = "";
		 Enumeration<String> params = request.getParameterNames();
			String parameter;
			
			 	while (params.hasMoreElements()) {
	 		parameter = params.nextElement();
	 		if (parameter.equalsIgnoreCase("EditorDefault")) {
	 			content_description += request.getParameter(parameter);
	 		}
	 	}
		
			 	linksForm.setContentDescriptionAdmin(content_description);
		String filelist[]=request.getParameterValues("checkedfiles");
		String files="";
		if(filelist.length==0)
		{
			files="";
		}else{
		
			for(int i=0;i<filelist.length;i++)
			{
				
				String deleteFiels="delete from filelist where link_name='"+linksForm.getLinkName()+"' and file_name='"+filelist[i]+"' and year='"+linksForm.getYear()+"'";
				int a=0;
				a=ad.SqlExecuteUpdate(deleteFiels);
				if (a > 0) {
					linksForm.setMessage("Documents Deleted Successfully");
				}else{
					linksForm.setMessage("Error While Deleted Files ... Please check Entered Values");
				}
			}
		}
		
		
		//String videoList[]=request.getParameterValues("checkedVideofiles");
		ArrayList list = new ArrayList();
		String sql3="select * from filelist where link_name='"+linksForm.getLinkName()+"' and year='"+linksForm.getYear()+"' ";
		ResultSet rs12 = ad.selectQuery(sql3);
		while (rs12.next())
		{
			linksForm = new LinksArchiveForm();
			linksForm.setFileList(rs12.getString("file_name"));
			list.add(linksForm);
		}
		request.setAttribute("listName", list);
		
		ArrayList videoList = new ArrayList();
		int videocount=0;
		String getVideoscount="select count(*) from video_list where link_name='"+linksForm.getLinkName()+"' and year='"+linksForm.getYear()+"'";
		ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
		while(rsgetVideoscount.next())
		{
			videocount=rsgetVideoscount.getInt(1);
		}
		if(videocount>=1){
		String getVideos="select * from video_list where link_name='"+linksForm.getLinkName()+"' and year='"+linksForm.getYear()+"' ";
		ResultSet rsgetVideos = ad.selectQuery(getVideos);
		while (rsgetVideos.next())
		{
			linksForm = new LinksArchiveForm();
			linksForm.setFileList(rsgetVideos.getString("video_name"));
			videoList.add(linksForm);
		}
		request.setAttribute("videosList", videoList);
		}
		
		
		
		
		request.setAttribute("displaySublinkField", "displaySublinkField");
	
	displayMainLinks(mapping, form, request, response);
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("displayMainLinks");
}
public ActionForward saveArchiveData(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {

	try{
		System.out.println("saveArchiveData");
			LinksArchiveForm linksForm = (LinksArchiveForm) form;// TODO Auto-generated method stub
			HttpSession session = request.getSession();
			String linkName=linksForm.getLinkName();
			LinksDao ad = new LinksDao();
			
			String content_description = "";
			String archieveStatus=linksForm.getArchiveStatus();
			
			
	       
	           
	           String	fileNameList="";
	           int filesCount=0;
	           String getFilesCount="select count(*) from filelist where link_name='"+linkName+"' and year='"+linksForm.getYear()+"'";
	          ResultSet rsFilesCount=ad.selectQuery(getFilesCount);
	          while(rsFilesCount.next())
	          {
	        	  filesCount=rsFilesCount.getInt(1);
	          }
	        	if(filesCount>0)
	        	{
	          
	           String getFiles="select * from filelist where link_name='"+linkName+"' and year='"+linksForm.getYear()+"'";
	           ResultSet rsFiles=ad.selectQuery(getFiles);
	          
	           while(rsFiles.next()){
	        	   
	        	 //  cms\HR\UploadVideos\2012
	        	   fileNameList+="jsp/EMicro Files/News And Media/Archieves"+"/"+linkName+"/UploadFiles"+"/"+linksForm.getYear()+"/"+rsFiles.getString("file_name")+",";
	           }
	           fileNameList=fileNameList.substring(0, (fileNameList.length()-1));
	        	}
	     System.out.println("fileNameList="+fileNameList);
	     
	     
	     String	videoNameList="";
	     
	     int videoCount=0;
           String getvideoCount="select count(*) from video_list where link_name='"+linkName+"' and year='"+linksForm.getYear()+"'";
          ResultSet rsvideoCount=ad.selectQuery(getvideoCount);
          while(rsvideoCount.next())
          {
        	  videoCount=rsvideoCount.getInt(1);
          }
          if(videoCount>0)
          {
	     String getVideos="select * from video_list where link_name='"+linkName+"' and year='"+linksForm.getYear()+"'";
	     ResultSet rsVideos=ad.selectQuery(getVideos);
	   
	     while(rsVideos.next())
	     {
	    	 videoNameList+="jsp/EMicro Files/News And Media/Archieves"+"/"+linkName+"/UploadVideos"+"/"+linksForm.getYear()+"/"+rsVideos.getString("video_name")+",";
	   	  
	     }
	     videoNameList=videoNameList.substring(0, (videoNameList.length()-1));
          }
	     
	     System.out.println("videoNameList="+videoNameList);
	    
	     Enumeration<String> params = request.getParameterNames();
			String parameter;
			
			 	while (params.hasMoreElements()) {
	 		parameter = params.nextElement();
	 		if (parameter.equalsIgnoreCase("EditorDefault")) {
	 			content_description += request.getParameter(parameter);
	 		}
	 	}
	   
			 	linksForm.setContentDescriptionAdmin(content_description);
	     
	     
	     String getArchiveCount="select count(*) from archieves where link_name='"+linkName+"'  and year='"+linksForm.getYear()+"'";
	     int archiveDataCount=0;
	     ResultSet rsArchiveCount=ad.selectQuery(getArchiveCount);
	     while(rsArchiveCount.next())
	     {
	    	 archiveDataCount=rsArchiveCount.getInt(1);
	     }
	     if(archiveDataCount>=1)
	     {
	    	 System.out.println("update data");
	    	String updateArchiveData="update archieves set content_description='"+content_description+"',status='"+linksForm.getArchiveStatus()+"', file_name='"+fileNameList+"',video_name='"+videoNameList+"' where link_name='"+linkName+"' and module='Main'  and year='"+linksForm.getYear()+"'"; 
	    	 
	    	ad.SqlExecuteUpdate(updateArchiveData);
	    	 
	     }
	     else{
	    	 System.out.println("insert data");
	    	 String insertlinksContent="insert into archieves(link_name,module,content_description,file_name,video_name,year,status) " +
				" values('"+linkName+"','Main','"+content_description+"','"+fileNameList+"','"+videoNameList+"','"+linksForm.getYear()+"','"+archieveStatus+"')";
	    	 
	    	 ad.SqlExecuteUpdate(insertlinksContent);
	    	 
	     }
			/*String deleteUploadFiles="delete from filelist where link_name='"+linkName+"' ";
			int a=0;
				a=ad.SqlExecuteUpdate(deleteUploadFiles);
			String deleteVideoFiles="delete from video_list where video_name='"+linkName+"' ";
			ad.SqlExecuteUpdate(deleteVideoFiles);*/
			String sql3="select * from archieves where link_name='"+linkName+"'  and   year='"+linksForm.getYear()+"' ";
			ResultSet rs12 = ad.selectQuery(sql3);
			ArrayList list=new ArrayList();
			String files="";
			while (rs12.next())
			{
				 files=rs12.getString("file_name");
				 if(files.equalsIgnoreCase(""))
				 {
					 
				 }else{
					 
				
				String v[] = files.split(",");
				int l = v.length;
				for (int i = 0; i < l; i++) 
				{
					LinksArchiveForm linksForm1=new LinksArchiveForm();
				int x=v[i].lastIndexOf("/");
				files=v[i].substring(x+1);				
				linksForm1.setFileList(files);
				list.add(linksForm1);
				}			
				
			}
			}
			
				 if(files.equalsIgnoreCase(""))
				 {
					 
				 }else{
			request.setAttribute("listName", list);
				 }
			String setVideos="select * from archieves where link_name='"+linkName+"'  and  year='"+linksForm.getYear()+"' ";
			ResultSet rsSetVideos = ad.selectQuery(setVideos);
			ArrayList videoLists=new ArrayList();
			String videos="";
			while (rsSetVideos.next())
			{
				linksForm = new LinksArchiveForm();
				videos=rsSetVideos.getString("video_name");
				
				if(videos.equalsIgnoreCase(""))
				{
					
				}else{
				String v[] = videos.split(",");
				int l = v.length;
				for (int i = 0; i < l; i++) 
				{
					LinksArchiveForm linksForm1=new LinksArchiveForm();
				int x=v[i].lastIndexOf("/");
				videos=v[i].substring(x+1);				
				linksForm1.setFileList(videos);
				videoLists.add(linksForm1);
				}
				}
			}
			if(videos.equalsIgnoreCase(""))
			{
				
			}else{
			request.setAttribute("videosList", videoLists);
			}
			
			String sql="select * from links where link_name='"+linkName + "' and " +
			"status is null and module='Main' ";
	System.out.println("sql="+sql);
	ResultSet rs = ad.selectQuery(sql);
	String linkId=null;
	while (rs.next()) {
		linkId=rs.getString("id");
		linksForm.setSubLinkName(rs.getString("link_name"));
		linksForm.setSubLinkId(linkName);
		linksForm.setLinkId(rs.getString("id"));
		linksForm.setLinkPath(rs.getString("link_path"));
		linksForm.setMethodName(rs.getString("method"));
		linksForm.setPriority(rs.getString("priority"));
		//linksForm.setContentDescriptionAdmin(rs.getString("content_description"));	
	}
	session.setAttribute("linkId",linkId);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			//displayLinksContent(mapping, form, request, response);
			request.setAttribute("displaySublinkField", "displaySublinkField");
			
			displayMainLinks(mapping, form, request, response);
			return mapping.findForward("displayMainLinks");



}


}




