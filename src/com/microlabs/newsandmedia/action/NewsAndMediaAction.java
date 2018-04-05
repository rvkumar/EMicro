package com.microlabs.newsandmedia.action;

import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.util.StringUtils;

import com.microlabs.admin.form.LinksForm;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.main.db.MainDao;
import com.microlabs.newsandmedia.form.NewsAndMediaForm;
import com.microlabs.utilities.UserInfo;

public class NewsAndMediaAction extends DispatchAction{
	EssDao ad=EssDao.dBConnection();
	public ActionForward displayplantlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("plantlist");
	}
	
	public ActionForward displayR_Dlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("r_Dlist");
	}
	
	
	public ActionForward displaytemplates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;// TODO Auto-generated method stub
		String id=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		
		
		return mapping.findForward("displayTemplates");
	}
	
	public ActionForward displayabtcmpny(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;
		String reqLink=request.getParameter("sId");
		EssDao ad =new EssDao();
		if(reqLink.equalsIgnoreCase("Corporate Presentation"))
		{
			String uplodedfiles="";
			String sql1="select * from archieves where link_name='Corporate Presentation' and module='About Company' and sub_link is null and status='null' order by month desc";
			ResultSet rs1=ad.selectQuery(sql1);
			String contentDesc="";
			try{
				while(rs1.next()) {
					 contentDesc+=rs1.getString("content_description");
					 if(!rs1.getString("file_name").equals(""))
					 uplodedfiles+=rs1.getString("file_name")+",";
					 newsAndMediaForm.setFileFullPath(uplodedfiles);
				}
				if(uplodedfiles.equalsIgnoreCase(""))
				{
					newsAndMediaForm.setFileFullPath("");
				}
			}catch(SQLException se){
				se.printStackTrace();
			}
			request.setAttribute("CorporatePPT", "CorporatePPT");
			return mapping.findForward("Corporate Presentation");
		}
		if(reqLink.equalsIgnoreCase("In-House Magzine"))
		{
			
			String uplodedfiles="";
			String sql1="select * from archieves where link_name='In-House Magzine' and module='About Company' and sub_link is null and status='null' order by month desc";
			ResultSet rs1=ad.selectQuery(sql1);
			String contentDesc="";
			try{
				while(rs1.next()) {
					 contentDesc+=rs1.getString("content_description");
					 if(!rs1.getString("file_name").equals(""))
					 uplodedfiles+=rs1.getString("file_name")+",";
					 newsAndMediaForm.setFileFullPath(uplodedfiles);
				}
				if(uplodedfiles.equalsIgnoreCase(""))
				{
					newsAndMediaForm.setFileFullPath("");
				}
			}catch(SQLException se){
				se.printStackTrace();
			}
			request.setAttribute("magzinePdf", "magzinePdf");
			return mapping.findForward("Corporate Presentation");
		}
		if(reqLink.equalsIgnoreCase("Vision And Aspiration"))
		{
			return mapping.findForward("Vision And Aspiration");
		}
		
		if(reqLink.equalsIgnoreCase("Awards and Achievements"))
		{
			return mapping.findForward("Awards and Achievements");
		}
		
		if(reqLink.equalsIgnoreCase("Depot Information"))
		{
			return mapping.findForward("Depot Information");
		}
		if(reqLink.equalsIgnoreCase("Global Presence"))
		{
			return mapping.findForward("Global Presence");
		}
		
		if(reqLink.equalsIgnoreCase("CSR Activities"))
		{
			return mapping.findForward("CSR Activities");
		}
		if(reqLink.equalsIgnoreCase("Research And Development"))
		{
			return mapping.findForward("Research And Development");
		}
		if(reqLink.equalsIgnoreCase("Research And Development2"))
		{
			return mapping.findForward("Research And Development2");
		}
		
		
	return mapping.findForward("ML03 HOSUR");
		
		
	}
	
	
	
	
	public ActionForward displayPlantInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;
		String reqLink=request.getParameter("sId");
		System.out.println("Link="+reqLink);
		if(reqLink.equalsIgnoreCase("ML01 HOSUR"))
		{
			return mapping.findForward("ML01 HOSUR");
		}
		if(reqLink.equalsIgnoreCase("ML14 EYE DROPS"))
		{
			return mapping.findForward("ML14 EYE DROPS");
		}
		if(reqLink.equalsIgnoreCase("ML15 API"))
		{
			return mapping.findForward("ML15 API");
		}
		if(reqLink.equalsIgnoreCase("ML25 VEERSANDRA"))
		{
			return mapping.findForward("ML25 VEERSANDRA");
		}
		if(reqLink.equalsIgnoreCase("ML03 HOSUR"))
		{
			return mapping.findForward("ML03 HOSUR");
		}
		if(reqLink.equalsIgnoreCase("ML05 PONDICHERRY"))
		{
			return mapping.findForward("ML05 PONDICHERRY");
		}
		if(reqLink.equalsIgnoreCase("ML06 GOA"))
		{
			return mapping.findForward("ML06 GOA");
		}
		if(reqLink.equalsIgnoreCase("ML07 DRY POWDER"))
		{
			return mapping.findForward("ML07 DRY POWDER");
		}
		if(reqLink.equalsIgnoreCase("ML08 KUMBALGODU"))
		{
			return mapping.findForward("ML08 KUMBALGODU");
		}
		if(reqLink.equalsIgnoreCase("ML11 VEERSANDRA"))
		{
			return mapping.findForward("ML11 VEERSANDRA");
		}
		if(reqLink.equalsIgnoreCase("ML12 PEENYA_2"))
		{
			return mapping.findForward("ML12 PEENYA_2");
		}
		if(reqLink.equalsIgnoreCase("ML13 BADDI"))
		{
			return mapping.findForward("ML13 BADDI");
		}
		return mapping.findForward("ML03 HOSUR");
	}
	
	public ActionForward displayLinkContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;// TODO Auto-generated method stub
		
		String id=request.getParameter("id");
		
		
		
		String sql="select * from cms_sublinks where id='"+id+"'";
		
		ResultSet rs=ad.selectQuery(sql);
		try{
			
			while (rs.next()) {
				newsAndMediaForm.setContentDescription(rs.getString("content_description"));
				newsAndMediaForm.setImageName(rs.getString("file_name"));
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("displayGallery");
	}
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;// TODO Auto-generated method stub
		String id=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
	
		String sql="select * from links where id in("+user.getIncludeSubLinks()+") order by id";
		
		
		ResultSet rs=ad.selectQuery(sql);
		try{
		InetAddress ownIP=InetAddress.getLocalHost();
		}catch (Exception e) {
			e.printStackTrace();
		}
		try{
			String sql3="select id from links where module='"+id+"'";
			
			ResultSet rs2=ad.selectQuery(sql3);
			
			String inLinks="";
			while (rs2.next()) {
				inLinks+=rs2.getString("id")+",";
			}
			
			
			inLinks=inLinks.substring(0, inLinks.lastIndexOf(","));
			String[] array = new HashSet<String>(Arrays.asList(inLinks.split(","))).toArray(new String[0]);
			
			List<String> list = new ArrayList<String>(array.length);  
			for (String s : array) {  
			    list.add(s);  
			}  
			
			
			
			String userLinks=user.getIncludeSubLinks()+","+user.getIncludeSubSubLinks();
			
			String[] a1=userLinks.split(",");
			
			List<String> list1 = new ArrayList<String>(a1.length);  
			for (String s : a1) {  
			    list1.add(s);  
			}  
		    
			
			
			Collection listOne = list;
	        Collection listTwo = list1;
	        
	        listTwo.retainAll(listOne);
			
			//list.removeAll(list1);
			
			String inkLinks="";
			String commaDelimitedString = StringUtils.collectionToCommaDelimitedString(list1);
			 commaDelimitedString=commaDelimitedString;
			
			String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname,icon_name " +
					" from links  " +
					" where module ='About Company'" +
					" and id in("+commaDelimitedString+") order by priority";
			
			ResultSet rs1=ad.selectQuery(sql1);
				
			
			LinkedHashSet<String> mainMenuSet=new LinkedHashSet<String>();
			LinkedList<LinksForm> allLinkList=new LinkedList<LinksForm>();
		 	LinkedHashMap<LinksForm,LinkedList<LinksForm>> finalLnkdList= new LinkedHashMap<LinksForm,LinkedList<LinksForm>>();
		 	
			while(rs1.next()){
				
				mainMenuSet.add(rs1.getString("sub_linkname"));
				LinksForm l=new LinksForm();
					l.setLinkPath(rs1.getString("url"));
					l.setPriority(""+rs1.getString("priority"));
					l.setSubLinkName(rs1.getString("sub_linkname"));
					l.setLinkName(rs1.getString("link_name"));
					String iconName="";
					 iconName=rs1.getString("icon_name");
					System.out.println("icon name="+iconName);
					if(iconName!=null)
					if(!(iconName.equalsIgnoreCase(""))){
						String a[]=iconName.split("/");
						iconName=a[(a.length-1)];
					}
					l.setIconName(iconName);
				allLinkList.add(l);
			}	
		 		
				
			 	for(String main:mainMenuSet){
			 		LinkedList<LinksForm> links=new LinkedList<LinksForm>();
			 		LinksForm temp=null;
			 		for(LinksForm aa:allLinkList){
			 				for(LinksForm sl:allLinkList)
			 				{
			 						
			 					if(!sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main))
			 						links.add(sl);
			 					else if(sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main) )
			 						temp=sl;
			 				}
			 		}
			 		
			 		for(LinksForm g:links) System.out.println(g.getLinkName());
	 				
	 				finalLnkdList.put(temp, links);
			 	}
			 	
			 	
			 	System.out.println("finalLnkdList.size--"+finalLnkdList.size()+"\n\n\n\n\n");
		 	
		 /*	//LinkedHashMap<LinksForm,ArrayList<LinksForm>> map = new LinkedHashMap<LinksForm,ArrayList<LinksForm>>();
		 	for (Map.Entry<LinksForm,LinkedList<LinksForm>> entry : finalLnkdList.entrySet()) {
		 	    System.out.println("Key = " + entry.getKey().getLinkName() + ", Value = " + entry.getValue().size()+"");
		 	    for (LinksForm lin : entry.getValue()) {
					System.out.println("--->"+lin.getLinkName());
				}
		 	}*/
		 	
		 	session.setAttribute("SUBLINKS", finalLnkdList);
			 
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("listName", list);
		
		LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
		
		String sql1="select * from links where link_name='"+id+"' and module='Main' and" +
				" status is null";
		
		ResultSet rs1=ad.selectQuery(sql1);
		
		try{
			while(rs1.next()) {
				 
				 newsAndMediaForm.setContentDescription(rs1.getString("content_description"));
				 newsAndMediaForm.setFileFullPath(rs1.getString("file_name"));
				 newsAndMediaForm.setVideoFullPath(rs1.getString("video_name"));
			}
		
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		request.setAttribute("MenuIcon", id);
		return mapping.findForward("display");
	}
	
	
	public ActionForward displayCMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;// TODO Auto-generated method stub
		String id=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		
		/*UserInfo user=(UserInfo)session.getAttribute("user");
		
		String sql="select * from links where id in("+user.getIncludeSubLinks()+") order by id";
		
		
		ResultSet rs=ad.selectQuery(sql);
		
		try{
			
			LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			
			
			while(rs.next()){
				 
				 String sql1="select * from links where module='"+id+"' " +
				 		"and id ='"+rs.getString("id")+"'";
				 ResultSet rs1=ad.selectQuery(sql1);
				 
				 if(rs1.next()){
				 hm.put(rs1.getString("link_path")+"?method="+rs1.getString("method")+"&sId="+rs1.getString("link_name")+"&id="+rs1.getString("module"), (rs1.getString("link_name")+','+rs1.getString("icon_name")));
				 }
				 newsAndMediaForm.setContentDescription("  ");
			}
			 
			 session.setAttribute("SUBLINKS", hm);
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("listName", list);
		
		LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");*/
		
		//String sql1="select * from links where link_name='News and Media' and module='Main' and status is null";
		String contentDescription="";
		String uplodedfiles="";
		String uplodedvideos="";
		String sql1="select * from archieves where link_name='About Company' and module='Main' and sub_link is null and status='null' order by month desc";
		ResultSet rs1=ad.selectQuery(sql1);
		String contentDesc="";
		try{
			while(rs1.next()) {
				 contentDesc+=rs1.getString("content_description");
				 if(!rs1.getString("file_name").equals(""))
				 uplodedfiles+=rs1.getString("file_name")+",";
				 if(!rs1.getString("video_name").equals(""))
				 uplodedvideos+=rs1.getString("video_name")+",";
				 newsAndMediaForm.setContentDescription(contentDesc);
				 newsAndMediaForm.setFileFullPath(uplodedfiles);
				 newsAndMediaForm.setVideoFullPath(uplodedvideos);
					
			}
			if(contentDesc.equalsIgnoreCase(""))
			{
				newsAndMediaForm.setContentDescription("No Content is available");
			}
			if(uplodedfiles.equalsIgnoreCase(""))
			{
				newsAndMediaForm.setFileFullPath("");
			}
			if(uplodedvideos.equalsIgnoreCase(""))
			{
				newsAndMediaForm.setVideoFullPath("");
			}
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		request.setAttribute("MenuIcon", id);
		request.setAttribute("linkname", "About Company");
		return mapping.findForward("displayCMS");
	}
	
	
	public ActionForward display1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;// TODO Auto-generated method stub
		
		String linkName=request.getParameter("sId"); 
		newsAndMediaForm.setLinkName(linkName);
		String module=request.getParameter("id"); 		
		newsAndMediaForm.setModule(module);
		HttpSession session=request.getSession();
		
		//LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
		
		String divStatus=newsAndMediaForm.getDivStatus();
		
		
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null)
		{
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displaySession");
		}
		
		try{
		/*String sql="select * from links where link_name='"+linkName+"' " +
				"and module='"+module+"'";
		
		ResultSet rs=ad.selectQuery(sql);
		
		
			
			//LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			 
			 while(rs.next()){
			 	 newsAndMediaForm.setContentDescription(rs.getString("content_description"));
			
			 	 newsAndMediaForm.setFileFullPath(rs.getString("file_name"));
			 	 newsAndMediaForm.setVideoFullPath(rs.getString("video_name"));
			 	 
			 }
			 
			String sql4="select * from filelist where link_name='"+module+"' " +
		 	 	"and sub_link_name='"+linkName+"' ";
			 
			ResultSet rs4=ad.selectQuery(sql4);
			
			while(rs4.next()){
			 	// newsAndMediaForm.setFileFullPath(rs4.getString("file_path"));
			}
			
			
			if(rs4.first()==true){
				newsAndMediaForm.setFileFullPath("  ");
			}
			
			
			String sql5="select * from video_list " +
			 		"where link_name='"+module+"' and sub_link_name='"+linkName+"'";
				
				ResultSet rs5=ad.selectQuery(sql5);
			    
				while(rs5.next()){
				 	// newsAndMediaForm.setVideoFullPath(rs5.getString("video_name"));
				}
			    
				if(rs5.first()==true){
					newsAndMediaForm.setVideoFullPath("  ");
				}
				*/
			
			String contentDescription="";
			String uplodedfiles="";
			String uplodedvideos="";
			String getCMSContent="select * from archieves where link_name='"+linkName+"' and module='"+module+"' and sub_link is null and status='null' order by month desc";
			ResultSet rsCMSContent=ad.selectQuery(getCMSContent);
			
			
				while(rsCMSContent.next()) {
					contentDescription+=rsCMSContent.getString("content_description");
					 if(!rsCMSContent.getString("file_name").equals(""))
					 uplodedfiles+=rsCMSContent.getString("file_name")+",";
					 if(!rsCMSContent.getString("video_name").equals(""))
					 uplodedvideos+=rsCMSContent.getString("video_name")+",";
					 newsAndMediaForm.setContentDescription(contentDescription);
					 newsAndMediaForm.setFileFullPath(uplodedfiles);
					 newsAndMediaForm.setVideoFullPath(uplodedvideos);
						
				}
				if(contentDescription.equalsIgnoreCase(""))
				{
					newsAndMediaForm.setContentDescription("No Content is available");
					
				}
				
				if(uplodedfiles.equalsIgnoreCase(""))
				{
					newsAndMediaForm.setFileFullPath("");
				}
				if(uplodedvideos.equalsIgnoreCase(""))
				{
					newsAndMediaForm.setVideoFullPath("");
				}
				
				
				String sql3="select id from links where module='"+module+"'";
				
				ResultSet rs2=ad.selectQuery(sql3);
				
				String inLinks="";
				while (rs2.next()) {
					inLinks+=rs2.getString("id")+",";
				}
				
				
				inLinks=inLinks.substring(0, inLinks.lastIndexOf(","));
				String[] array = new HashSet<String>(Arrays.asList(inLinks.split(","))).toArray(new String[0]);
				
				List<String> list = new ArrayList<String>(array.length);  
				for (String s : array) {  
				    list.add(s);  
				}  
				
				System.out.println("Getting a list is ***********************"+list);
				
				
				String userLinks=user.getIncludeSubLinks()+","+user.getIncludeSubSubLinks();
				
				String[] a1=userLinks.split(",");
				
				List<String> list1 = new ArrayList<String>(a1.length);  
				for (String s : a1) {  
				    list1.add(s);  
				}  
			    
				
				
				Collection listOne = list;
		        Collection listTwo = list1;
		        
		        listTwo.retainAll(listOne);
				
				//list.removeAll(list1);
				
				String inkLinks="";
				
				
				String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname,icon_name " +
						" from links  " +
						" where module ='About Company'" +
						" and id in(38,39,58,57,56,55,45,54,46) order by priority";
				
				ResultSet rs1=ad.selectQuery(sql1);
					
					
				//select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as 
				// url,module,link_name,sub_linkname  from links   where module ='News And Media' and id 
				//in(38,39,40,45,46) order by id	
				
				//LinkedHashMap hm=new LinkedHashMap();	
					
				
				LinkedHashSet<String> mainMenuSet=new LinkedHashSet<String>();
				LinkedList<LinksForm> allLinkList=new LinkedList<LinksForm>();
			 	LinkedHashMap<LinksForm,LinkedList<LinksForm>> finalLnkdList= new LinkedHashMap<LinksForm,LinkedList<LinksForm>>();
			 	
				while(rs1.next()){
					
					mainMenuSet.add(rs1.getString("sub_linkname"));
					LinksForm l=new LinksForm();
						l.setLinkPath(rs1.getString("url"));
						l.setPriority(""+rs1.getString("priority"));
						l.setSubLinkName(rs1.getString("sub_linkname"));
						l.setLinkName(rs1.getString("link_name"));
						l.setIconName(rs1.getString("icon_name"));
					allLinkList.add(l);
				}	
			 		
					
				 	for(String main:mainMenuSet){
				 		LinkedList<LinksForm> links=new LinkedList<LinksForm>();
				 		LinksForm temp=null;
				 		System.out.println(" -- "+main+"");
				 		for(LinksForm aa:allLinkList){
				 				for(LinksForm sl:allLinkList)
				 				{
				 						
				 					if(!sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main))
				 						links.add(sl);
				 					else if(sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main) )
				 						temp=sl;
				 				}
				 		}
				 		
				 		for(LinksForm g:links) System.out.println(g.getLinkName());
		 				
		 				finalLnkdList.put(temp, links);
				 	}
				 	
				 	
			 	
			 	//LinkedHashMap<LinksForm,ArrayList<LinksForm>> map = new LinkedHashMap<LinksForm,ArrayList<LinksForm>>();
				 	int totalRecords = getCountForTable(linkName,module);
					  int  startRecord=0;
					  int  endRecord=0;
						 if(totalRecords>10)
						 {
							 newsAndMediaForm.setTotalRecords(totalRecords);
						 startRecord=1;
						 endRecord=10;
						 newsAndMediaForm.setStartRecord(1);
						 newsAndMediaForm.setEndRecord(10);
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						 request.setAttribute("displayRecordNo", "displayRecordNo");
						 request.setAttribute("nextButton", "nextButton");
						 }else
						 {
							  startRecord=1;
							  endRecord=totalRecords;
							  newsAndMediaForm.setTotalRecords(totalRecords);
							  newsAndMediaForm.setStartRecord(1);
							  newsAndMediaForm.setEndRecord(totalRecords); 
						 }
						 
			 if(totalRecords>0)
			 {
			 	
			 	String sql2="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  link_name asc) AS  RowNum,* from cms_sublinks where main_linkname='"+module+"' " +
			 			"and sub_linkname='"+linkName+"' and archived_status='0')as  sub Where   sub.RowNum between 1 and 100 order by id desc";
			 	
			 	
			 	ResultSet rs3=ad.selectQuery(sql2);
			 	
			 	NewsAndMediaForm newsMediaForm1=null;
			 	
			 	ArrayList a2=new ArrayList();
			 	while (rs3.next()) {
			 		newsMediaForm1=new NewsAndMediaForm();
			 		newsMediaForm1.setLinkId(rs3.getString("id"));
			 		newsMediaForm1.setLinkTitle(rs3.getString("link_name"));
			 		newsMediaForm1.setContentYear(rs3.getString("content_year"));
			 		//changes
			 		String path[]=rs3.getString("icon_name").split("/");
			 		System.out.println(path.length);
			 		String imagePath="";
			 		for(int i=3;i<path.length;i++)
			 		{
			 			imagePath=imagePath+path[i]+"/";
			 		}
			 		imagePath=imagePath.substring(0,(imagePath.length()-1));
			 		newsMediaForm1.setImageName("/"+imagePath);
			 		a2.add(newsMediaForm1);
				}
			 
			 	
			 	
			 	session.setAttribute("SUBLINKS", finalLnkdList);
			 	request.setAttribute("subLinkDetails", a2);
			 	
			 }
			 	if(linkName.equalsIgnoreCase("Archives")){
			 		
			 		String sql6="select distinct link_name,id from links where module='News and Media' " +
			 				"and link_name=sub_linkname";
			 		
			 		ResultSet rs6=ad.selectQuery(sql6);
			 		
			 		ArrayList a4=new ArrayList();
			 		NewsAndMediaForm newsMediaForm2=null;
			 		while (rs6.next()) {
			 			newsMediaForm2=new NewsAndMediaForm();
			 			newsMediaForm2.setLinkId(rs6.getString("id"));
			 			newsMediaForm2.setLinkName(rs6.getString("link_name"));
			 			a4.add(newsMediaForm2);
					}
			 		
			 		request.setAttribute("linksDetails", a4);
			 		
			 		return mapping.findForward("displayArchivesCms");
			 		
			 	}
			 	
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("MenuIcon", module);
		request.setAttribute("linkname", linkName);
		request.setAttribute("module", module);
		
		newsAndMediaForm.setLinkName(linkName);
		
		return mapping.findForward("display1");
	}
	
	
	public ActionForward previous(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		
	 
		
		
		String contentDescription="";
		String uplodedfiles="";
		String uplodedvideos="";
		String getCMSContent="select * from archieves where link_name='"+newsAndMediaForm.getLinkName()+"' and module='"+newsAndMediaForm.getModule()+"' and sub_link is null and status='null' order by month desc";
		ResultSet rsCMSContent=ad.selectQuery(getCMSContent);
		
		
			try {
				while(rsCMSContent.next()) {
					contentDescription+=rsCMSContent.getString("content_description");
					 if(!rsCMSContent.getString("file_name").equals(""))
					 uplodedfiles+=rsCMSContent.getString("file_name")+",";
					 if(!rsCMSContent.getString("video_name").equals(""))
					 uplodedvideos+=rsCMSContent.getString("video_name")+",";
					 newsAndMediaForm.setContentDescription(contentDescription);
					 newsAndMediaForm.setFileFullPath(uplodedfiles);
					 newsAndMediaForm.setVideoFullPath(uplodedvideos);
						
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(contentDescription.equalsIgnoreCase(""))
			{
				newsAndMediaForm.setContentDescription("No Content is available");
				
			}
			
			if(uplodedfiles.equalsIgnoreCase(""))
			{
				newsAndMediaForm.setFileFullPath("");
			}
			if(uplodedvideos.equalsIgnoreCase(""))
			{
				newsAndMediaForm.setVideoFullPath("");
			}
		
		
	  
		try{
			int totalRecords=newsAndMediaForm.getTotalRecords();//21
			int startRecord=newsAndMediaForm.getStartRecord();//11
			int endRecord=newsAndMediaForm.getEndRecord();	
			
			 if(totalRecords>10)
			 {
				 newsAndMediaForm.setTotalRecords(totalRecords);
			 startRecord=1;
			 endRecord=10;
			 newsAndMediaForm.setStartRecord(1);
			 newsAndMediaForm.setEndRecord(10);
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("nextButton", "nextButton");
			 }else
			 {
				  startRecord=1;
				  endRecord=totalRecords;
				  newsAndMediaForm.setTotalRecords(totalRecords);
				  newsAndMediaForm.setStartRecord(1);
				  newsAndMediaForm.setEndRecord(totalRecords); 
			 }
			
			if(totalRecords>0)
			{
		

		 	
		 	String sql2="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  link_name asc) AS  RowNum,* from cms_sublinks where main_linkname='"+newsAndMediaForm.getModule()+"' " +
		 			"and sub_linkname='"+newsAndMediaForm.getLinkName()+"' and archived_status='0')as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by link_name";
		 	
		 	
		 	ResultSet rs3=ad.selectQuery(sql2);
		 	
		 	NewsAndMediaForm newsMediaForm1=null;
		 	
		 	ArrayList a2=new ArrayList();
		 	while (rs3.next()) {
		 		newsMediaForm1=new NewsAndMediaForm();
		 		newsMediaForm1.setLinkId(rs3.getString("id"));
		 		newsMediaForm1.setLinkTitle(rs3.getString("link_name"));
		 		newsMediaForm1.setContentYear(rs3.getString("content_year"));
		 		//changes
		 		String path[]=rs3.getString("icon_name").split("/");
		 		System.out.println(path.length);
		 		String imagePath="";
		 		for(int i=3;i<path.length;i++)
		 		{
		 			imagePath=imagePath+path[i]+"/";
		 		}
		 		imagePath=imagePath.substring(0,(imagePath.length()-1));
		 		newsMediaForm1.setImageName("/"+imagePath);
		 		a2.add(newsMediaForm1);
			}
		 
		 	
		 	
		 
		 	request.setAttribute("subLinkDetails", a2);
		 	
		 
			}
			
		
		}catch (Exception e) {
				e.printStackTrace();
			}
		 request.setAttribute("displayRecordNo", "displayRecordNo");
		return mapping.findForward("display1");
		
	
		
		
		
		
	}
	
	public ActionForward next(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		
	 
		
		
		String contentDescription="";
		String uplodedfiles="";
		String uplodedvideos="";
		String getCMSContent="select * from archieves where link_name='"+newsAndMediaForm.getLinkName()+"' and module='"+newsAndMediaForm.getModule()+"' and sub_link is null and status='null' order by month desc";
		ResultSet rsCMSContent=ad.selectQuery(getCMSContent);
		
		
			try {
				while(rsCMSContent.next()) {
					contentDescription+=rsCMSContent.getString("content_description");
					 if(!rsCMSContent.getString("file_name").equals(""))
					 uplodedfiles+=rsCMSContent.getString("file_name")+",";
					 if(!rsCMSContent.getString("video_name").equals(""))
					 uplodedvideos+=rsCMSContent.getString("video_name")+",";
					 newsAndMediaForm.setContentDescription(contentDescription);
					 newsAndMediaForm.setFileFullPath(uplodedfiles);
					 newsAndMediaForm.setVideoFullPath(uplodedvideos);
						
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(contentDescription.equalsIgnoreCase(""))
			{
				newsAndMediaForm.setContentDescription("No Content is available");
				
			}
			
			if(uplodedfiles.equalsIgnoreCase(""))
			{
				newsAndMediaForm.setFileFullPath("");
			}
			if(uplodedvideos.equalsIgnoreCase(""))
			{
				newsAndMediaForm.setVideoFullPath("");
			}
		
		
	  
		try{
			int totalRecords=newsAndMediaForm.getTotalRecords();//21
			int startRecord=newsAndMediaForm.getStartRecord();//11
			int endRecord=newsAndMediaForm.getEndRecord();	
			
			
			if(totalRecords>endRecord)
			{
				if(totalRecords==endRecord)
				{
					startRecord=startRecord;
					endRecord=totalRecords;
				}
				if(totalRecords>endRecord)
				{
				startRecord=endRecord+1;
				endRecord=endRecord+10;
				}
				
			

		 	
		 	String sql2="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  link_name asc) AS  RowNum,* from cms_sublinks where main_linkname='"+newsAndMediaForm.getModule()+"' " +
		 			"and sub_linkname='"+newsAndMediaForm.getLinkName()+"' and archived_status='0')as  sub Where   sub.RowNum between "+startRecord+" and "+endRecord+" order by link_name";
		 	
		 	
		 	ResultSet rs3=ad.selectQuery(sql2);
		 	
		 	NewsAndMediaForm newsMediaForm1=null;
		 	
		 	ArrayList a2=new ArrayList();
		 	while (rs3.next()) {
		 		newsMediaForm1=new NewsAndMediaForm();
		 		newsMediaForm1.setLinkId(rs3.getString("id"));
		 		newsMediaForm1.setLinkTitle(rs3.getString("link_name"));
		 		newsMediaForm1.setContentYear(rs3.getString("content_year"));
		 		//changes
		 		String path[]=rs3.getString("icon_name").split("/");
		 		System.out.println(path.length);
		 		String imagePath="";
		 		for(int i=3;i<path.length;i++)
		 		{
		 			imagePath=imagePath+path[i]+"/";
		 		}
		 		imagePath=imagePath.substring(0,(imagePath.length()-1));
		 		newsMediaForm1.setImageName("/"+imagePath);
		 		a2.add(newsMediaForm1);
			}
		 
		 	
		 	
		 
		 	request.setAttribute("subLinkDetails", a2);
		 	
		 
			
			
			
			if(a2.size()!=0)
			{
				newsAndMediaForm.setTotalRecords(totalRecords);
				newsAndMediaForm.setStartRecord(startRecord);
				newsAndMediaForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;
				
				newsAndMediaForm.setTotalRecords(totalRecords);
				newsAndMediaForm.setStartRecord(start);
				newsAndMediaForm.setEndRecord(end);
				
			}
		 if(a2.size()<10)
		 {
			 newsAndMediaForm.setTotalRecords(totalRecords);
			 newsAndMediaForm.setStartRecord(startRecord);
			 newsAndMediaForm.setEndRecord(startRecord+a2.size()-1);
				request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton"); 
			 
		 }
			}
			 if(endRecord==totalRecords)
			 {
				 request.setAttribute("nextButton", "");
					request.setAttribute("disableNextButton", "disableNextButton");
			 }
			
		
		}catch (Exception e) {
				e.printStackTrace();
			}
		 request.setAttribute("displayRecordNo", "displayRecordNo");
		return mapping.findForward("display1");
		
	}
	
	
	public int getCountForTable(String linkName,String module){
    	int rowCount = 0;
    	MainDao exeQry = new MainDao();
    	String reqList="select *  from cms_sublinks where main_linkname='"+module+"' " +
			 			"and sub_linkname='"+linkName+"' and archived_status='0' order by link_name";
    	
    	try
    	{
    		
    		ResultSet countrs=exeQry.selectQuery(reqList);
    		while(countrs.next()) {
    			rowCount=Integer.parseInt(countrs.getString(1));
    		}
    	}
    	catch(SQLException se){
    		System.out.println("Exception @ getting count");
    		se.printStackTrace();
    	}
    	return rowCount;
    }
	
	public ActionForward displayContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;
		 
		 
		String subLinkName=request.getParameter("subLink"); 	
		String module=request.getParameter("id"); 	
		String linkName=request.getParameter("sId"); 		
		HttpSession session=request.getSession();
		
		LinkedHashMap<LinksForm,LinkedList<LinksForm>> hm=(LinkedHashMap<LinksForm,LinkedList<LinksForm>>)session.getAttribute("SUBLINKS");
		String contentDescription="";
		String uplodedfiles="";
		String uplodedvideos="";
		
		String sql="select * from archieves where link_name='"+linkName+"' and" +
		" sub_link='"+subLinkName+"'  and module='"+module+"' and status ='null' order by month desc";
	ResultSet rs=ad.selectQuery(sql);
	try{
	while (rs.next()) {
		contentDescription+=rs.getString("content_description");
		 uplodedfiles+=rs.getString("file_name")+",";
		 uplodedvideos+=rs.getString("video_name")+",";
		 newsAndMediaForm.setContentDescription(contentDescription);
		 newsAndMediaForm.setFileFullPath(uplodedfiles);
		 newsAndMediaForm.setVideoFullPath(uplodedvideos);
	}
	if(contentDescription.equalsIgnoreCase(""))
	{
		newsAndMediaForm.setContentDescription("No Content is available");
	}
	if(uplodedfiles.equalsIgnoreCase(""))
	{
		newsAndMediaForm.setFileFullPath("");
	}
	if(uplodedvideos.equalsIgnoreCase(""))
	{
		newsAndMediaForm.setVideoFullPath("");
	}
			
		}catch(Exception se){
			se.printStackTrace();
		}
		
		
		newsAndMediaForm.setLinkName(subLinkName);
		session.setAttribute("SUBLINKS", hm);
		
		request.setAttribute("MenuIcon", module);
		
		return mapping.findForward("displayArchivesCms");
	}
	
	
	public ActionForward displayArchiveYears(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;// TODO Auto-generated method stub
		 
		
		String linkName=request.getParameter("id"); 	
		HttpSession session=request.getSession();
		
		
		
		
		
		String sql="select distinct content_year,id,sub_linkname from cms_sublinks where " +
				"link_name='"+linkName+"' and main_linkname='News and Media' and archived_status='1'";
		ResultSet rs=ad.selectQuery(sql);
		String subLinkName="";
		ArrayList a1=new ArrayList();
		try{
			NewsAndMediaForm newsAndMediaForm1=null;
			while (rs.next()) {
				newsAndMediaForm1=new NewsAndMediaForm();
				
				newsAndMediaForm1.setContentYear(rs.getString("content_year"));
				newsAndMediaForm1.setLinkId(rs.getString("id"));
				subLinkName=rs.getString("sub_linkname");
				a1.add(newsAndMediaForm1);
			}
			
			newsAndMediaForm.setLinkName(subLinkName);
			
			request.setAttribute("subLinksYearDetails", a1);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return mapping.findForward("displayArchivesCms");
	}
	
	public ActionForward displayArchiveLinks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;// TODO Auto-generated method stub
		 
		
		String linkName=request.getParameter("id"); 	
		HttpSession session=request.getSession();
		
		
		
		
		
		String sql="select distinct link_name,id,sub_linkname from cms_sublinks where " +
				"sub_linkname='"+linkName+"' and main_linkname='News and Media' and archived_status='1'";
		ResultSet rs=ad.selectQuery(sql);
		
		ArrayList a1=new ArrayList();
		
		String subLinkName="";
		try{
			NewsAndMediaForm newsAndMediaForm1=null;
			while (rs.next()) {
				newsAndMediaForm1=new NewsAndMediaForm();
				
				newsAndMediaForm1.setLinkName(rs.getString("link_name"));
				newsAndMediaForm1.setLinkId(rs.getString("id"));
				subLinkName=rs.getString("sub_linkname");
				a1.add(newsAndMediaForm1);
			}
			
			newsAndMediaForm.setLinkName(subLinkName);
			request.setAttribute("subLinksDetails", a1);
			
			session.setAttribute("MainLink", linkName);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return mapping.findForward("displayArchivesCms");
	}
	
	
	
	
	public ActionForward displayCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsAndMediaForm newsAndMediaForm = (NewsAndMediaForm) form;// TODO Auto-generated method stub
		
		HttpSession session=request.getSession();
		
		LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
		
		
		
		
		
		session.setAttribute("SUBLINKS", hm);
		

		return mapping.findForward("displayCalendar");
	}
	
	
	
}
