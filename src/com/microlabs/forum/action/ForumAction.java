package com.microlabs.forum.action;

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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.util.StringUtils;

import com.microlabs.admin.form.LinksForm;
import com.microlabs.forum.dao.ForumDao;
import com.microlabs.forum.form.ForumForm;
import com.microlabs.utilities.UserInfo;

public class ForumAction extends DispatchAction{
	
	public ActionForward displayMEDITIMES(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward("displayMEDITIMES");
	}
	
	
	public ActionForward displayFirstAid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward("displayFirstAid");
	}
	
	public ActionForward displayEmergencyNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward("displayEmergency");
	}
	
	public ActionForward displayHealth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward("displayHealth");
	}
	public ActionForward displayGreenTips(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward("displayGreenTips");
	}
	public ActionForward displayRecipe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward("displayRecipe");
	}
	
	public ActionForward displayQuickFacts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward("displayQuickFacts");
	}
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ForumForm ForumForm=(ForumForm)form;	
		ForumDao ad=new ForumDao();
		String id=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		String sql="select * from links where id in("+user.getIncludeSubLinks()+") order by id";
		
		System.out.println("SQL is *********************************"+sql);
		
		ResultSet rs=ad.selectQuery(sql);
		
		try{
			
			System.out.println("SQL is *********************************"+sql);
			
			System.out.println("SQL is *********************************"+sql);
			
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
			
			System.out.println("Getting a list is ***********************"+list);
			
			
			String userLinks=user.getIncludeSubLinks()+","+user.getIncludeSubSubLinks();
			
			String[] a1=userLinks.split(",");
			
			List<String> list1 = new ArrayList<String>(a1.length);  
			for (String s : a1) {  
			    list1.add(s);  
			}  
		    
			System.out.println("Getting a list1 is ***********************"+list1);
			
			
			Collection listOne = list;
	        Collection listTwo = list1;
	        
	        listTwo.retainAll(listOne);
	        System.out.println("Getting a listTwo is ***********************"+listTwo);
			
			//list.removeAll(list1);
			
			String inkLinks="";
			String commaDelimitedString = StringUtils.collectionToCommaDelimitedString(list1);
			 commaDelimitedString=commaDelimitedString;
			 if(commaDelimitedString.equalsIgnoreCase(""))
				 commaDelimitedString="1";
			String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname,icon_name " +
					" from links  " +
					" where module ='"+id+"'" +
					" and id in("+commaDelimitedString+") order by priority";
			
			ResultSet rs1=ad.selectQuery(sql1);
				
				
			//select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as 
			// url,module,link_name,sub_linkname  from links   where module ='News And Media' and id 
			//in(38,39,40,45,46) order by id	
			
			//LinkedHashMap hm=new LinkedHashMap();	
				
			
			LinkedHashSet<String> mainMenuSet=new LinkedHashSet<String>();
			LinkedList<LinksForm> allLinkList=new LinkedList<LinksForm>();
		 	LinkedHashMap<LinksForm,LinkedList<LinksForm>> finalLnkdList= new LinkedHashMap<LinksForm,LinkedList<LinksForm>>();
		 	
			while(rs1.next()){
				
				System.out.println("rs1.getString(sub_linkname) **************"+rs1.getString("sub_linkname"));
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
			 				System.out.println("("+sl.getLinkName()+" -- "+sl.getSubLinkName()+") -- "+main+"");
			 						
			 					if(!sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main))
			 						links.add(sl);
			 					else if(sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main) )
			 						temp=sl;
			 				}
			 		}
			 		
			 		for(LinksForm g:links) System.out.println(g.getLinkName());
	 				
	 				System.out.println(main+"---temp lnk nmae--"+temp.getLinkName());
	 				finalLnkdList.put(temp, links);
			 	}
			 	
			 	
			 	System.out.println("finalLnkdList.size--"+finalLnkdList.size()+"\n\n\n\n\n");
		 	
		 	//LinkedHashMap<LinksForm,ArrayList<LinksForm>> map = new LinkedHashMap<LinksForm,ArrayList<LinksForm>>();
		 	for (Map.Entry<LinksForm,LinkedList<LinksForm>> entry : finalLnkdList.entrySet()) {
		 	    System.out.println("Key = " + entry.getKey().getLinkName() + ", Value = " + entry.getValue().size()+"");
		 	    for (LinksForm lin : entry.getValue()) {
					System.out.println("--->"+lin.getLinkName());
				}
		 	}
		 	
		 	session.setAttribute("SUBLINKS", finalLnkdList);
			 
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("listName", list);
		
		LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
		
		/*String sql1="select * from links where link_name='"+id+"' and module='Main' and" +
				" status is null";
		
		ResultSet rs1=ad.selectQuery(sql1);*/
		
		try{
			/*while(rs1.next()) {
				 
				ForumForm.setContentDescription(rs1.getString("content_description"));
			//	ForumForm.setFileFullPath(rs1.getString("file_name"));
				// ForumForm.setVideoFullPath(rs1.getString("video_name"));
			}*/
			
			
			String contentDescription="";
			String uplodedfiles="";
			String uplodedvideos="";
			String getCMSContent="select * from archieves where link_name='"+id+"' and module='Main' and sub_link is null and status='null'";
			ResultSet rsCMSContent=ad.selectQuery(getCMSContent);
			
			
				while(rsCMSContent.next()) {
					 
					ForumForm.setContentDescription(rsCMSContent.getString("content_description"));
					ForumForm.setFileFullPath(rsCMSContent.getString("file_name"));
					ForumForm.setVideoFullPath(rsCMSContent.getString("video_name"));
					 contentDescription=rsCMSContent.getString("content_description");
						uplodedfiles=rsCMSContent.getString("file_name");
						uplodedvideos=rsCMSContent.getString("video_name");
						
						list.add(ForumForm);
				}
				request.setAttribute("list", list);
				if(contentDescription.equalsIgnoreCase(""))
				{
					ForumForm.setContentDescription("No Content is available");
					
				}
				
				if(uplodedfiles.equalsIgnoreCase(""))
				{
					ForumForm.setFileFullPath("");

				}
				if(uplodedvideos.equalsIgnoreCase(""))
				{
					ForumForm.setVideoFullPath("");
		
				}	
		
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		request.setAttribute("MenuIcon", id);
		return mapping.findForward("display");
	}
	
	
	public ActionForward display1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ForumForm ForumForm=(ForumForm)form;	
		ForumDao ad=new ForumDao();
		
		
		String linkName=request.getParameter("sId"); 
		
		String module=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		try{
			
			//LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			 	
			String sql3="select id from links where module='"+module+"'";
			
			ResultSet rs2=ad.selectQuery(sql3);
			
			String inLinks="";
			while (rs2.next()) {
				inLinks+=rs2.getString("id")+",";
			}
			
			
			String sql11="select * from links where link_name='"+linkName+"' and module='"+module+"'";
			
			ResultSet rs11=ad.selectQuery(sql11);
			
			try{
				while(rs11.next()) {
					 
					ForumForm.setContentDescription(rs11.getString("content_description"));
					ForumForm.setFileFullPath(rs11.getString("file_name"));
					ForumForm.setVideoFullPath(rs11.getString("video_name"));
				}
			
			}catch(SQLException se){
				se.printStackTrace();
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
		    
			System.out.println("Getting a list1 is ***********************"+list1);
			
			
			Collection listOne = list;
	        Collection listTwo = list1;
	        
	        listTwo.retainAll(listOne);
	        System.out.println("Getting a listTwo is ***********************"+listTwo);
			
			//list.removeAll(list1);
			
			String inkLinks="";
			
			
			/*String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname,icon_name " +
					" from links  " +
					" where module='ESS'" +
					" and id in(13,14,18,19,59,60,61,62,63,64,65,66,67,68,69,70,71,86) order by priority";
		*/	
			
			String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname," +
					"icon_name " +
			" from links  " +
			" where module='Forum'" +
			" and id in(108,109,110,111) order by priority";
			
			
			ResultSet rs1=ad.selectQuery(sql1);
				
				
			//select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as 
			// url,module,link_name,sub_linkname  from links   where module ='News And Media' and id 
			//in(38,39,40,45,46) order by id	
			
			//LinkedHashMap hm=new LinkedHashMap();	
				
			
			LinkedHashSet<String> mainMenuSet=new LinkedHashSet<String>();
			LinkedList<ForumForm> allLinkList=new LinkedList<ForumForm>();
		 	LinkedHashMap<ForumForm,LinkedList<ForumForm>> finalLnkdList= new LinkedHashMap<ForumForm,LinkedList<ForumForm>>();
		 	
			while(rs1.next()){
				
				System.out.println("rs1.getString(sub_linkname) **************"+rs1.getString("sub_linkname"));
				mainMenuSet.add(rs1.getString("sub_linkname"));
				ForumForm l=new ForumForm();
					l.setLinkPath(rs1.getString("url"));
					l.setPriority(""+rs1.getString("priority"));
					l.setSubLinkName(rs1.getString("sub_linkname"));
					l.setLinkName(rs1.getString("link_name"));
					l.setIconName(rs1.getString("icon_name"));
				allLinkList.add(l);
			}	
		 		
				
			 	for(String main:mainMenuSet){
			 		LinkedList<ForumForm> links=new LinkedList<ForumForm>();
			 		ForumForm temp=null;
			 		System.out.println(" -- "+main+"");
			 		for(ForumForm aa:allLinkList){
			 				for(ForumForm sl:allLinkList)
			 				{
			 				System.out.println("("+sl.getLinkName()+" -- "+sl.getSubLinkName()+") -- "+main+"");
			 						
			 					if(!sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main))
			 						links.add(sl);
			 					else if(sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main) )
			 						temp=sl;
			 				}
			 		}
			 		for(ForumForm g:links) System.out.println(g.getLinkName());
	 				
	 				System.out.println(main+"---temp lnk nmae--"+temp.getLinkName());
	 				finalLnkdList.put(temp, links);
			 	}
			 	
			 	ForumForm.setLinkName(linkName);
			 	
			 	
			 	
			 	String contentDescription="";
				String uplodedfiles="";
				String uplodedvideos="";
				String getCMSContent="select * from archieves where link_name='"+linkName+"' and module='"+module+"' and sub_link is null and status='null'";
				ResultSet rsCMSContent=ad.selectQuery(getCMSContent);
				
				
					while(rsCMSContent.next()) {
						 
						ForumForm.setContentDescription(rsCMSContent.getString("content_description"));
						ForumForm.setFileFullPath(rsCMSContent.getString("file_name"));
						ForumForm.setVideoFullPath(rsCMSContent.getString("video_name"));
						 contentDescription=rsCMSContent.getString("content_description");
							uplodedfiles=rsCMSContent.getString("file_name");
							uplodedvideos=rsCMSContent.getString("video_name");
					}
					if(contentDescription.equalsIgnoreCase(""))
					{
						ForumForm.setContentDescription("No Content is available");
						
					}
					
					if(uplodedfiles.equalsIgnoreCase(""))
					{
						ForumForm.setFileFullPath("");
					}
					if(uplodedvideos.equalsIgnoreCase(""))
					{
						ForumForm.setVideoFullPath("");
					}
			 	
			 	
			 	
			 	
			 	
			 	
			 	
			 	
			 	
			 	System.out.println("finalLnkdList.size--"+finalLnkdList.size()+"\n\n\n\n\n");
		 	
		 	//LinkedHashMap<LinksForm,ArrayList<LinksForm>> map = new LinkedHashMap<LinksForm,ArrayList<LinksForm>>();
		 	for (Map.Entry<ForumForm,LinkedList<ForumForm>> entry : finalLnkdList.entrySet()) {
		 	    System.out.println("Key = " + entry.getKey().getLinkName() +"" +
		 	    		"Link Path is"+ entry.getKey().getLinkPath()+"Get Image "+ entry.getKey().getIconName()+ ", Value = " + entry.getValue().size()+"");
		 	    for (ForumForm lin : entry.getValue()) {

					System.out.println("--->"+lin.getLinkName());
				}
		 	}
		 	
		 	
		 	String sql2="select * from cms_sublinks where main_linkname='"+module+"' " +
		 			"and sub_linkname='"+linkName+"' and archived_status='0'";
		 	
		 	
		 	ResultSet rs3=ad.selectQuery(sql2);
		 	
		 	ForumForm newsMediaForm1=null;
		 	
		 	ArrayList a2=new ArrayList();
		 	while (rs3.next()) {
		 		newsMediaForm1=new ForumForm();
		 		
		 		
		 		System.out.println("Getting Icon Name is ****************"+rs3.getString("icon_name"));
		 		newsMediaForm1.setLinkId(rs3.getString("id"));
		 		newsMediaForm1.setLinkTitle(rs3.getString("link_name"));
		 		newsMediaForm1.setImageName(rs3.getString("icon_name"));
		 		a2.add(newsMediaForm1);
			}
		 	
		 	
		 	session.setAttribute("SUBLINKS", finalLnkdList);
		 	request.setAttribute("subLinkDetails", a2);
				
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("MenuIcon", module);
		
		return mapping.findForward("displayCMS");
	}
	
	
	public ActionForward displayCMS1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ForumForm ForumForm=(ForumForm)form;	
		ForumDao ad=new ForumDao();
		
		
		String linkName="Help Desk"; 
		
		String module="Forum"; 		
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		try{
			
			
			String contentDescription="";
			String uplodedfiles="";
			String uplodedvideos="";
			String getCMSContent="select * from archieves where link_name='"+module+"' and module='Main' and sub_link is null and status='null' order by month desc";
			ResultSet rsCMSContent=ad.selectQuery(getCMSContent);
				while(rsCMSContent.next()) {
					contentDescription+=rsCMSContent.getString("content_description");
					if(!rsCMSContent.getString("file_name").equals(""))
						 uplodedfiles+=rsCMSContent.getString("file_name")+",";
					if(!rsCMSContent.getString("video_name").equals(""))
					 uplodedvideos+=rsCMSContent.getString("video_name")+",";
					ForumForm.setContentDescription(contentDescription);
					ForumForm.setFileFullPath(uplodedfiles);
					ForumForm.setVideoFullPath(uplodedvideos);
				}
				if(contentDescription.equalsIgnoreCase(""))
				{
					ForumForm.setContentDescription("No Content is available");
				}
				if(uplodedfiles.equalsIgnoreCase(""))
				{
					ForumForm.setFileFullPath("");
				}
				if(uplodedvideos.equalsIgnoreCase(""))
				{
					ForumForm.setVideoFullPath("");
				}	
			
				ForumForm.setLinkName(linkName);
		 	
		 	session.setAttribute("SUBLINKS", session.getAttribute("SUBLINKS"));
				
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		request.setAttribute("MenuIcon", module);
		
		return mapping.findForward("displayCMS1");
	}
	

}
