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
import com.microlabs.admin.form.LinksForm;
import com.microlabs.admin.form.SubLinkArchiveForm;
import com.microlabs.hr.dao.HRDao;

public class SubLinkArchiveAction extends DispatchAction{

	public ActionForward deleteUploadedVideo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try{
			SubLinkArchiveForm linksForm = (SubLinkArchiveForm) form;
			LinksDao ad = new LinksDao();
			String year=linksForm.getYear();
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
			String videoList[]=request.getParameterValues("checkedVideofiles1");
			String videos="";
			if(videoList.length==0)
			{
				videos="";
			}else{
			
				for(int i=0;i<videoList.length;i++)
				{
					
					String deleteVideos="delete from video_list where link_name='"+linksForm.getLinkName()+"' and sub_link_name='"+linksForm.getSubLinkName()+"' and video_name='"+videoList[i]+"' and year='"+year+"'";
					int a=0;
					a=ad.SqlExecuteUpdate(deleteVideos);
					if (a > 0) {
						linksForm.setMessage("Videos Deleted Successfully");
					}else{
						linksForm.setMessage("Error While Deleted Videos ... Please check ");
					}
				}
			}
			
			

			
			int filecount=0;
			String getFilescount="select count(*) from filelist where link_name='"+linksForm.getLinkName()+"' and sub_link_name='"+linksForm.getSubLinkName()+"' and year='"+year+"'";
			ResultSet rsgetFilescount = ad.selectQuery(getFilescount);
			while(rsgetFilescount.next())
			{
				filecount=rsgetFilescount.getInt(1);
			}
			if(filecount>=1){
			ArrayList list = new ArrayList();
			String sql3="select * from filelist where link_name='"+linksForm.getLinkName()+"' and sub_link_name='"+linksForm.getSubLinkName()+"' and year='"+year+"' ";
			ResultSet rs12 = ad.selectQuery(sql3);
			while (rs12.next())
			{
				linksForm = new SubLinkArchiveForm();
				linksForm.setFileList(rs12.getString("file_name"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			}
			ArrayList videoList1 = new ArrayList();
			int videocount=0;
			String getVideoscount="select count(*) from video_list where link_name='"+linksForm.getLinkName()+"' and sub_link_name='"+linksForm.getSubLinkName()+"' and year='"+year+"'";
			ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
			while(rsgetVideoscount.next())
			{
				videocount=rsgetVideoscount.getInt(1);
			}
			if(videocount>=1){
			String getVideos="select * from video_list where link_name='"+linksForm.getLinkName()+"' and sub_link_name='"+linksForm.getSubLinkName()+"' and year='"+year+"'";
			ResultSet rsgetVideos = ad.selectQuery(getVideos);
			while (rsgetVideos.next())
			{
				linksForm = new SubLinkArchiveForm();
				linksForm.setVideoFilesList(rsgetVideos.getString("video_name"));
				videoList1.add(linksForm);
			}
			request.setAttribute("listName1", videoList1);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		request.setAttribute("displayFields", "displayFields");
		return mapping.findForward("display");	
	
		
	}
	
	
	public ActionForward deleteUploadedMainFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try{
			SubLinkArchiveForm linksForm = (SubLinkArchiveForm) form;
			LinksDao ad = new LinksDao();
			String year=linksForm.getYear();
			
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
			String filelist[]=request.getParameterValues("checkedfiles1");
			String files="";
			if(filelist.length==0)
			{
				files="";
			}else{
			
				for(int i=0;i<filelist.length;i++)
				{
					
					String deleteFiels="delete from filelist where link_name='"+linksForm.getLinkName()+"' and sub_link_name='"+linksForm.getSubLinkName()+"'  and file_name='"+filelist[i]+"' and year='"+year+"'";
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
			String sql3="select * from filelist where link_name='"+linksForm.getLinkName()+"' and year='"+year+"'";
			ResultSet rs12 = ad.selectQuery(sql3);
			while (rs12.next())
			{
				linksForm = new SubLinkArchiveForm();
				linksForm.setFileList(rs12.getString("file_name"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			
			ArrayList videoList = new ArrayList();
			int videocount=0;
			String getVideoscount="select count(*) from video_list where link_name='"+linksForm.getLinkName()+"'  and sub_link_name='"+linksForm.getSubLinkName()+"' and year='"+year+"' ";
			ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
			while(rsgetVideoscount.next())
			{
				videocount=rsgetVideoscount.getInt(1);
			}
			if(videocount>=1){
			String getVideos="select * from video_list where link_name='"+linksForm.getLinkName()+"'  and sub_link_name='"+linksForm.getSubLinkName()+"' and year='"+year+"'   ";
			ResultSet rsgetVideos = ad.selectQuery(getVideos);
			while (rsgetVideos.next())
			{
				linksForm = new SubLinkArchiveForm();
				linksForm.setVideoFilesList(rsgetVideos.getString("video_name"));
				videoList.add(linksForm);
			}
			request.setAttribute("listName1", videoList);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		request.setAttribute("displayFields", "displayFields");
		return mapping.findForward("display");	
	}
	
	
	public ActionForward saveArchiveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try{
			System.out.println("saveArchiveData");
		SubLinkArchiveForm linksForm = (SubLinkArchiveForm) form;
		LinksDao ad = new LinksDao();
			String linkName=linksForm.getLinkName();
			String sublink=linksForm.getSubLinkName();
			String year=linksForm.getYear();

		
		String content_description ="";
          
           String	fileNameList="";
           String	videoNameList="";
           int filesCount=0;
           String getFilesCount="select count(*) from filelist where link_name='"+linkName+"' and sub_link_name='"+sublink+"' and sub_sub_linkname='' and year='"+year+"'";
           ResultSet rsFilesCount=ad.selectQuery(getFilesCount);
           while(rsFilesCount.next())
           {
        	   filesCount=rsFilesCount.getInt(1);
           }
           if(filesCount>0)
           {
           String getFiles="select * from filelist where link_name='"+linkName+"' and sub_link_name='"+sublink+"' and sub_sub_linkname='' and year='"+year+"'";
           ResultSet rsFiles=ad.selectQuery(getFiles);
           while(rsFiles.next())
           {
        	   fileNameList+="jsp/EMicro Files/News And Media/Archieves"+"/"+linkName+"/"+sublink+"/"+"UploadFiles"+"/"+linksForm.getYear()+"/"+rsFiles.getString("file_name")+",";
        	   
           }
           fileNameList=fileNameList.substring(0, (fileNameList.length()-1));
           }
     System.out.println("fileNameList="+fileNameList);
     int videoCount=0;
     String getVideoCount="select count(*) from video_list where link_name='"+linkName+"' and sub_link_name='"+sublink+"' and sub_sub_linkname='' and year='"+year+"'";
     ResultSet rsVideoCount=ad.selectQuery(getVideoCount);
     while(rsVideoCount.next())
     {
    	 videoCount=rsVideoCount.getInt(1);
     }
     if(videoCount>0)
     {
     String getVideos="select * from video_list where link_name='"+linkName+"' and sub_link_name='"+sublink+"' and sub_sub_linkname='' and year='"+year+"'";
     ResultSet rsVideos=ad.selectQuery(getVideos);
     while(rsVideos.next()){
    	 videoNameList+="jsp/EMicro Files/News And Media/Archieves"+"/"+linkName+"/"+sublink+"/"+"UploadVideos"+"/"+linksForm.getYear()+"/"+rsVideos.getString("video_name")+",";
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
    
 
     String getArchiveCount="select count(*) from archieves where link_name='"+sublink+"' and module='"+linkName+"'  and year='"+linksForm.getYear()+"'";
     int archiveDataCount=0;
     ResultSet rsArchiveCount=ad.selectQuery(getArchiveCount);
     while(rsArchiveCount.next())
     {
    	 archiveDataCount=rsArchiveCount.getInt(1);
     }
     if(archiveDataCount>=1)
     {
    	 System.out.println("update data");
    	 
    	 String updateArchiveData="update archieves set content_description='"+content_description+"',status='"+linksForm.getArchiveStatus()+"', file_name='"+fileNameList+"',video_name='"+videoNameList+"'" +
    	 		" where link_name='"+sublink+"' and module='"+linkName+"' and year='"+linksForm.getYear()+"'"; 
    	 
     	ad.SqlExecuteUpdate(updateArchiveData);
    	 
    	 
     }else{
			
			String insertlinksContent="insert into archieves(link_name,module,content_description,file_name,video_name,year,status) " +
					" values('"+sublink+"','"+linkName+"','"+content_description+"','"+fileNameList+"','"+videoNameList+"','"+linksForm.getYear()+"','"+linksForm.getArchiveStatus()+"')";
			
			int i=ad.SqlExecuteUpdate(insertlinksContent);
		
			if (i > 0) {
				linksForm.setMessage("Selected Data Saved In Archives  Successfully");
			}else{
				linksForm.setMessage("Error While Saving ... Please check Entered Values");
			}
     }
			
	/*	String deleteUploadFiles="delete from filelist where link_name='"+linkName+"' and sub_link_name='"+sublink+"'";
		ad.SqlExecuteUpdate(deleteUploadFiles);
		String deleteVideoFiles="delete from video_list where link_name='"+linkName+"' and sub_link_name='"+sublink+"'";
		ad.SqlExecuteUpdate(deleteVideoFiles);*/
		
		
		String sql3="select * from archieves where link_name='"+sublink+"' and module='"+linkName+"'  and  year='"+linksForm.getYear()+"' ";
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
			for (int j = 0; j < l; j++) 
			{
				SubLinkArchiveForm linksForm1=new SubLinkArchiveForm();
			int x=v[j].lastIndexOf("/");
			files=v[j].substring(x+1);				
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
		String setVideos="select * from archieves where link_name='"+sublink+"' and module='"+linkName+"' and year='"+linksForm.getYear()+"' ";
		ResultSet rsSetVideos = ad.selectQuery(setVideos);
		ArrayList videoLists=new ArrayList();
		String videos="";
		while (rsSetVideos.next())
		{
			linksForm = new SubLinkArchiveForm();
			 videos=rsSetVideos.getString("video_name");
			if(videos.equalsIgnoreCase(""))
			{
			}
			else{
			 String v[] = videos.split(",");
			int l = v.length;
			for (int k = 0; k < l; k++) 
			{
				LinksForm linksForm1=new LinksForm();
			int x=v[k].lastIndexOf("/");
			videos=v[k].substring(x+1);				
			linksForm1.setVideoFilesList(videos);
			videoLists.add(linksForm1);
			}
			}
		}
		if(videos.equalsIgnoreCase(""))
		{
			
		}else{
		request.setAttribute("listName1", videoLists);
		
		
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		request.setAttribute("displayFields", "displayFields");
		return mapping.findForward("display");
	
		
	}
	
	
	public ActionForward uploadVideo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try{
			SubLinkArchiveForm linksForm = (SubLinkArchiveForm) form;
			LinksDao ad = new LinksDao();
			String link_id=linksForm.getLinkName();
			String sublink=linksForm.getSubLinkName();
			linksForm.setLinkName(link_id);
			String content_description = "";
			String year=linksForm.getYear();
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
			String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/News And Media/Archieves")+"/"+link_id+"/"+sublink+"/UploadVideos"+"/"+linksForm.getYear();
			
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
					+"','"+fileName+"','"+sublink+"','','"+year+"')";
				System.out.println("insertsql="+insertsql);
			int a = ad.SqlExecuteUpdate(insertsql);
			if (a > 0) {
				linksForm.setMessage("Videos Uploaded Successfully");
			}else{
				linksForm.setMessage("Error While Uploading Videos ... Please check Entered Values");
			}
			ArrayList fileList = new ArrayList();
			String getFiles="select * from filelist where link_name='"+link_id+"'and sub_link_name='"+sublink+"' and sub_sub_linkname='' and year='"+year+"'";
			
			ResultSet rsgetFiles = ad.selectQuery(getFiles);
			while (rsgetFiles.next())
			{
				linksForm = new SubLinkArchiveForm();
				linksForm.setFileList(rsgetFiles.getString("file_name"));
				fileList.add(linksForm);
			}
			request.setAttribute("listName", fileList);
			ArrayList videoList = new ArrayList();
			String sql3="select * from video_list where link_name='"+link_id+"' and sub_link_name='"+sublink+"' and sub_sub_linkname='' and year='"+year+"' ";
			ResultSet rs12 = ad.selectQuery(sql3);
			while (rs12.next())
			{
				linksForm = new SubLinkArchiveForm();
				linksForm.setVideoFilesList(rs12.getString("video_name"));
				videoList.add(linksForm);
			}
			request.setAttribute("listName1", videoList);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		request.setAttribute("displayFields", "displayFields");
		return mapping.findForward("display");
	
		
	}
	
		
	
	public ActionForward uploadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try{
			SubLinkArchiveForm linksForm = (SubLinkArchiveForm) form;
			LinksDao ad = new LinksDao();
			String link_id=linksForm.getLinkName();
			String sublink=linksForm.getSubLinkName();
			linksForm.setLinkName(link_id);
			String content_description = "";
			String year=linksForm.getYear();
			
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
			
			String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/News And Media/Archieves")+"/"+link_id+"/"+sublink+"/UploadFiles"+"/"+linksForm.getYear();
			
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
					+"','"	+ filePath
					+"','"+fileName+"','"+sublink+"','','"+linksForm.getYear()+"')";
				System.out.println("insertsql="+insertsql);
			int a = ad.SqlExecuteUpdate(insertsql);
			if (a > 0) {
				linksForm.setMessage("Documents Uploaded Successfully");
			}else{
				linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
			}
			ArrayList list = new ArrayList();
			String sql3="select * from filelist where link_name='"+link_id+"' and sub_link_name='"+sublink+"' and year='"+year+"'  ";
			ResultSet rs12 = ad.selectQuery(sql3);
			while (rs12.next())
			{
				linksForm = new SubLinkArchiveForm();
				linksForm.setFileList(rs12.getString("file_name"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			
			ArrayList videoList = new ArrayList();
			int videocount=0;
			String getVideoscount="select count(*) from video_list where  link_name='"+link_id+"' and sub_link_name='"+sublink+"' and year='"+year+"' ";
			ResultSet rsgetVideoscount = ad.selectQuery(getVideoscount);
			while(rsgetVideoscount.next())
			{
				videocount=rsgetVideoscount.getInt(1);
			}
			if(videocount>=1){
			String getVideos="select * from video_list where link_name='"+link_id+"'  and sub_link_name='"+sublink+"' and year='"+year+"'";
			ResultSet rsgetVideos = ad.selectQuery(getVideos);
			while (rsgetVideos.next())
			{
				linksForm = new SubLinkArchiveForm();
				linksForm.setVideoFilesList(rsgetVideos.getString("video_name"));
				videoList.add(linksForm);
			}
			request.setAttribute("listName1", videoList);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		request.setAttribute("displayFields", "displayFields");
		return mapping.findForward("display");
	
	}
		
		
	
	
	public ActionForward displaySubLinksArchiveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
{
		
		SubLinkArchiveForm linksForm = (SubLinkArchiveForm) form;
		LinksDao adlinks = new LinksDao();
		try{
			String content_description ="";
			Enumeration<String> params = request.getParameterNames();
			String parameter;
			
			 	while (params.hasMoreElements()) {
	 		parameter = params.nextElement();
	 		if (parameter.equalsIgnoreCase("EditorDefault")) {
	 			content_description += request.getParameter(parameter);
	 		}
	 	}
			linksForm.setContentDescriptionAdmin(content_description);
			
			
		int count=0;
		String arcStatus="";
		linksForm.setArchiveStatus("");
		linksForm.setContentDescriptionAdmin("");
		String getArchievDataCount="select count(*) from archieves where link_name='"+linksForm.getSubLinkName()+"'   and module='"+linksForm.getLinkName()+"' and  year='"+linksForm.getYear()+"'";
		ResultSet rsArchievDataCount=adlinks.selectQuery(getArchievDataCount);
		while(rsArchievDataCount.next())
		{
			count=rsArchievDataCount.getInt(1);
		}
		if(count>=1){
			String getArchievData="select * from archieves where link_name='"+linksForm.getSubLinkName()+"'  and module='"+linksForm.getLinkName()+"' and  year='"+linksForm.getYear()+"'";
			System.out.println("getArchievData="+getArchievData);
			ResultSet rsArchiveData=adlinks.selectQuery(getArchievData);
			String file;
			ArrayList fileList = new ArrayList();
			ArrayList videoList = new ArrayList();
			String files="";
			String videos="";
			while(rsArchiveData.next())
			{
				arcStatus=rsArchiveData.getString("status");
				if(arcStatus.equalsIgnoreCase("yes"))
				{
				linksForm.setArchiveStatus(rsArchiveData.getString("status"));
				}
				linksForm.setContentDescriptionAdmin(rsArchiveData.getString("content_description"));
				
		
			files=rsArchiveData.getString("file_name");
			if(files.equalsIgnoreCase(""))
			{
			}
			else{
				String v[] = files.split(",");
				int l = v.length;
				for (int i = 0; i < l; i++) 
				{
					SubLinkArchiveForm linksForm1=new SubLinkArchiveForm();
				int x=v[i].lastIndexOf("/");
				files=v[i].substring(x+1);				
				linksForm1.setFileList(files);
				fileList.add(linksForm1);
				}
			}	
				videos=rsArchiveData.getString("video_name");
			if(videos.equalsIgnoreCase(""))
			{
			}else{
				String v1[] = videos.split(",");
				int l1 = v1.length;
				for (int i = 0; i < l1; i++) 
				{
					SubLinkArchiveForm linksFormVideo=new SubLinkArchiveForm();
				int y=v1[i].lastIndexOf("/");
				videos=v1[i].substring(y+1);	
				linksFormVideo.setVideoFilesList(videos);
				videoList.add(linksFormVideo);
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
			request.setAttribute("listName1", videoList);
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		request.setAttribute("displayFields", "displayFields");
		return mapping.findForward("display");
}
	
	
	
	
	
	public ActionForward displaySublinks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		SubLinkArchiveForm linksForm = (SubLinkArchiveForm) form;// TODO Auto-generated method stub
		String linkName = linksForm.getLinkName();
		String id = request.getParameter("id");			
		request.setAttribute("MenuIcon", id);
		try {
			LinksDao ad = new LinksDao();
			String sql="select * from links where module='"
				+linkName+"'  and sub_linkname is null and  delete_status=1";
			ResultSet rs = ad.selectQuery("select * from links where module='"+linkName+"'   and  delete_status=1 " );
	
			ArrayList subLinkValueList = new ArrayList();
			
			while(rs.next()) {
				
				subLinkValueList.add(rs.getString("link_name"));
			}
			
			linksForm.setSublinkValueList(subLinkValueList);
			
			request.setAttribute("submitButton", "submitButton");
		} catch (Exception e) {
			e.printStackTrace();
		}
		display(mapping, form, request, response);
		return mapping.findForward("display");
	
	}
	
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("SubLinkArchiveAction--display()");
		SubLinkArchiveForm linksForm = (SubLinkArchiveForm) form;// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String linkName = linksForm.getLinkName();
		String subLinkName = linksForm.getSubLinkName();
		String linkPath = linksForm.getLinkPath();
		String methodName = linksForm.getMethodName();
		String priority = linksForm.getPriority();
		
		try {
			LinksDao ad = new LinksDao();
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
		request.setAttribute("MenuIcon", id);
		if(id!=null){
		
		HRDao ad = new HRDao();
		String sql = "select * from links where module='" + id + "' and status=1 ";
		ResultSet rs = ad.selectQuery(sql);
		try {
			LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();
			
			while(rs.next()) {
				hm.put(rs.getString("link_path")+"?method="+rs.getString("method") + "&sId="+rs.getString("id"), (rs.getString("link_name")+','+rs.getString("icon_name")));
			}
			session.setAttribute("SUBLINKS", hm);
		} catch (SQLException se) {
			se.printStackTrace();
		}
		
		}

		
		return mapping.findForward("display");
	}
}
