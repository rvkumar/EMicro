package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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

import com.microlabs.admin.dao.GroupDao;
import com.microlabs.admin.dao.UserGroupDao;
import com.microlabs.admin.form.LinksForm;
import com.microlabs.admin.form.UserGroupForm;
import com.microlabs.login.dao.LoginDao;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.IdValuePair;
import com.microlabs.utilities.UserInfo;


public class UserGroupAction extends DispatchAction{
	
	public String replace(String str)
	{
		String replaced_value=str.replace("'", "");
		return replaced_value;
	}
	
	public ActionForward displaySubLinks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserGroupForm userGroupForm=(UserGroupForm) form;
		
		
		String id=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		
		GroupDao ad=new GroupDao();
		
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
				
			}
			 
			 session.setAttribute("SUBLINKS", hm);
			 
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("listName", list);
		
		
		return mapping.findForward("display");
	}
	
	
	
	public ActionForward displayCMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserGroupForm userGroupForm=(UserGroupForm) form;
		String id=request.getParameter("id"); 	
		
	
		
		HttpSession session=request.getSession();
		
		NewsandMediaDao ad=new NewsandMediaDao();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		
		String sql="select * from links where id in("+user.getIncludeSubLinks()+")";
		
		
		
		
		ResultSet rs=ad.selectQuery(sql);
		
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
					" where module ='Admin'" +
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
			 	
			 	
		 	
		 	
		 	
		 	session.setAttribute("SUBLINKS", finalLnkdList);
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("listName", list);
		
		
		
		request.setAttribute("MenuIcon", id);
		
		
		
		
		
		//LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
		
		
		
		String sql1="select * from links where link_name='"+id+"' and module='Main' and" +
				" status is null";
		
		ResultSet rs1=ad.selectQuery(sql1);
		
		try{
			
			while (rs1.next()) {
				userGroupForm.setContentDescription(rs1.getString("content_description"));
				userGroupForm.setFileFullPath(rs1.getString("file_name"));
				userGroupForm.setVideoFullPath(rs1.getString("video_name"));
			}
			
			
			
	
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		

		return mapping.findForward("displayCMS");
	}
	
	public ActionForward displayCMS1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserGroupForm userGroupForm=(UserGroupForm) form;
		GroupDao dao=new GroupDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		//String sql1="select * from links where link_name='Admin' and module='Main' and status is null";
		String contentDescription="";
		String uplodedfiles="";
		String uplodedvideos="";
		String sql1="select * from archieves where link_name='Admin' and module='Main' and sub_link is null and status='null' order by month desc";
ResultSet rs1=dao.selectQuery(sql1);
try{
	while (rs1.next()) {
		contentDescription+=rs1.getString("content_description");
		 if(!rs1.getString("file_name").equals(""))
		 uplodedfiles+=rs1.getString("file_name")+",";
		 if(!rs1.getString("video_name").equals(""))
		 uplodedvideos+=rs1.getString("video_name")+",";
		 System.out.println("uploded videos="+uplodedvideos);
		userGroupForm.setContentDescription(contentDescription);
		userGroupForm.setFileFullPath(uplodedfiles);
		userGroupForm.setVideoFullPath(uplodedvideos);
	}
	if(contentDescription.equalsIgnoreCase(""))
	{
		userGroupForm.setContentDescription("No Content is available");
	}
	if(uplodedfiles.equalsIgnoreCase(""))
	{
		userGroupForm.setFileFullPath("");
	}
	if(uplodedvideos.equalsIgnoreCase(""))
	{
		userGroupForm.setVideoFullPath("");
	}
}catch(SQLException se){
	se.printStackTrace();
	
}
request.setAttribute("linkName", "Admin");
		return mapping.findForward("displayCMS1");
	}
	
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserGroupForm userGroupForm=(UserGroupForm) form;
		GroupDao dao=new GroupDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		String module=request.getParameter("id");
		userGroupForm.setLinks(dao.getLinksIDvaluePair("main"));
		int gId=1;
		request.setAttribute("MenuIcon", module);
		UserGroupDao ugd=new UserGroupDao();
		
		
		userGroupForm.setLocations(dao.getLocationsIDvaluePair());
	
		
		userGroupForm.setMessage("");
		request.setAttribute("addGroupButton", "addGroupButton");
		return mapping.findForward("display");
	}
	
	
	public ActionForward addGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String madhu="";
		UserGroupForm userGroupForm=(UserGroupForm) form;

		String menuIcon=(String)request.getAttribute("MenuIcon");
		
		
		try {
		GroupDao ad=new GroupDao();
		HttpSession session= request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		                                                                                                                                                                     
		String groupName=userGroupForm.getGroupName();
		groupName=replace(groupName);
		groupName=groupName.trim();
		if(groupName.equalsIgnoreCase(""))
		{
			userGroupForm.setStatusMessage("Please enter proper group name");
			
		}
		
		else
		{
			
		String moduleName=userGroupForm.getModuleName();
		
		// rupesh code
		// get selected location from declareGroup.jsp and storing in user_group table
		//end
		
		String[] ls=request.getParameterValues("selectedMainModulesArr");
		String[] selectedLocations=request.getParameterValues("selectedLocationsArr");
		
		String checkGroupQuery="SELECT count(group_name) FROM user_group u where group_name='"+groupName+"'";
		System.out.println("HI RUPESH");
		ResultSet rs=ad.selectQuery(checkGroupQuery);
		int totalGroupCount=0;
		while(rs.next())
		{
			totalGroupCount=rs.getInt(1);
			System.out.println("Problemaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+totalGroupCount);
			System.out.println(totalGroupCount);
		}
		if(totalGroupCount>=1){
			System.out.println("ENTERED IF");
			request.setAttribute("MenuIcon",menuIcon);
			GroupDao dao=new GroupDao();
			HttpSession session1=request.getSession();
			UserInfo user1=(UserInfo)session.getAttribute("user");
			
			String module=request.getParameter("id");
			
			userGroupForm.setLinks(dao.getLinksIDvaluePair("main"));
			
			int gId=1;
			request.setAttribute("MenuIcon", module);
			
			//rupesh code
			//get all location from location table
			UserGroupDao ugd=new UserGroupDao();
		//	ArrayList<String> locationlist=ugd.getLocation();
			//System.out.println("listttttttttttttttttttttttttttttttttttttttttt"+locationlist);
		//	userGroupForm.setLocationlist(locationlist);
			//end
			
			request.setAttribute("addGroupButton", "addGroupButton");
			userGroupForm.setStatusMessage("Group Name Already Exists.");
		return mapping.findForward("display");
		}
		else{
			 for(int i=1;i<ls.length;i++){
				 String main="main";
			System.out.println("Selected Links="+ls[i]);
			String mm=ls[i];
			
			String query="select id,link_name from links where module like '"+main+"' and link_name like '"+mm+"'order by id";
			
			ResultSet rs11=ad.selectQuery(query);
			 if(rs11.next()){
				madhu+=rs11.getString("id")+",";
			 }
		System.out.println("id no in link table" +madhu);
			
			 }
			
			System.out.println("Total Selected Links="+ls.length);
		int groupCount=0;
		 
			
			 
					 String ab=madhu;
					 int len=ab.length()-1;
				     madhu=ab.substring(0, len);
	                  System.out.println("Madhu***********************"+madhu);
	                  
	      String locations="";
	      if(selectedLocations!=null)
	      if(selectedLocations.length>0){
	    	  for(int i=0;i<selectedLocations.length;i++){
	    		  locations+=selectedLocations[i]+",";
	    	  }
	    	  locations=locations.substring(0,(locations.length()-1));
	      }
	                  
				   try{
					 String sql="insert into user_group(group_name,include_links,module_name,status,incsubsublinks,incsublinks,locations,Created_By,Created_Date)" +
				 		" values('"+groupName+"','"+madhu+"','',0,'1','1','"+locations+"','"+user.getEmployeeNo()+"',getdate())";
						System.out.println(sql);
				        int insert=ad.SqlExecuteUpdate(sql);
				        
				        if(insert>0)
				        {
				        	userGroupForm.setStatusMessage("Group Name Added Successfully.");
				        }
				        else
				        {
				        	userGroupForm.setStatusMessage("Not Inserted.");
				        }
				        
				   }catch(Exception e){
					   UserGroupDao ugd=new UserGroupDao();
						//ArrayList<String> locationlist=ugd.getLocation();
						System.out.println("Adding group errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
						//userGroupForm.setLocationlist(locationlist);
					   
						return mapping.findForward("display");
				   }
					 }
			 
					 /*for(int i=1;i<ls.length;i++){
					 String sql="update user_group set status=1 where group_name='"+groupName+"' and " +
					 		"module_name='"+ls[i]+"'";
					 ad.SqlExecuteUpdate(sql);
					 }*/
		}		
		userGroupForm.setGroupName("");
		}
			
					 catch(SQLException e) {
					       e.printStackTrace();
				     }
						//userGroupForm.setStatusMessage("Group Name Added Successfully.");
						GroupDao dao=new GroupDao();
						HttpSession session=request.getSession();
						UserInfo user1=(UserInfo)session.getAttribute("user");
						
						String module=request.getParameter("id");
						
						userGroupForm.setLinks(dao.getLinksIDvaluePair("main"));
						userGroupForm.setLocations(dao.getLocationsIDvaluePair());
						int gId=1;
						request.setAttribute("MenuIcon", module);
						request.setAttribute("addGroupButton", "addGroupButton");

		return mapping.findForward("display");
	}
	
	public ActionForward modifyGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("modifyGroup() in UserGroupAction-----");
		UserGroupForm userGroupForm=(UserGroupForm)form;
		GroupDao ad=new GroupDao();
		HttpSession session= request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		String groupName=userGroupForm.getGroupName();
	
		String moduleName=userGroupForm.getModuleName();

		String[] ls=request.getParameterValues("selectedMainModulesArr");
		for(int i=1;i<ls.length;i++){
			System.out.println("selected main modules="+ls[i]);
			 }
		for(int i=1;i<ls.length;i++){
			 String sql="update user_group set status=1,Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where group_name='"+groupName+"' and " +
			 		"sub_modulename='"+ls[i]+"' and module_name='"+moduleName+"'";
			 ad.SqlExecuteUpdate(sql);
			 }
		
		
		if(moduleName.equalsIgnoreCase("main"))
		{
			String updateGroupStatus="update user_group set status=0,Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where group_name='"+groupName+"' and sub_modulename is null";
			ad.SqlExecuteUpdate(updateGroupStatus);
			for(int i=1;i<ls.length;i++){
				String updateGroupStatus1="update user_group set status=1,Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where module_name='"+ls[i]+"' and sub_modulename is null ";
				ad.SqlExecuteUpdate(updateGroupStatus1);
				}
		}
		else{
			
	
		
			String updateSubGroupStatus="update user_group set status=0,Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where group_name='"+groupName+"' and module_name='"+moduleName+"'" +
					"and sub_modulename is not null";
			 ad.SqlExecuteUpdate(updateSubGroupStatus);
			 for(int i=1;i<ls.length;i++){
				 String updateSubGroupStatus1="update user_group set status=1,Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where group_name='"+groupName+"' and module_name='"+moduleName+"'" +
					"and sub_modulename ='"+ls[i]+"'";
			 ad.SqlExecuteUpdate(updateSubGroupStatus1);
					 }
			}
		
		
		
	
		reFresh(mapping, form, request, response);
		return mapping.findForward("declareGroup1");
	}
	
	
	public ActionForward addSubGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("addSubGroup()----");
		UserGroupForm userGroupForm=(UserGroupForm)form;
		GroupDao ad=new GroupDao();
		HttpSession session= request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		                                                                                                                                                                     
		String groupName=userGroupForm.getGroupName();
		String moduleName=userGroupForm.getModuleName();
		
		String[] ls=request.getParameterValues("selectedMainModulesArr");
		
		
		String groupCount="";
		 	 
			 
			 String countQuery="select group_id from user_group where group_name like '"+groupName+"'";
			 	
				try {
					ResultSet rs1=ad.selectQuery(countQuery);
					 if(rs1.next()){
						 groupCount=""+rs1.getInt(1);
					 }
					 
					 
					 ArrayList<IdValuePair> a1=ad.getSubLinksIDvaluePair(moduleName);
					 
					 Iterator itr= a1.iterator();
					 
					 while(itr.hasNext()) {
						
						IdValuePair id=(IdValuePair)itr.next();
						String sql="insert into user_group(group_name,module_name,links,status,group_id,sub_modulename,Created_By,Created_Date)" +
				 		" values('"+groupName+"','"+moduleName+"','',0,'"+groupCount+"','"+id.getValue()+"','"+user.getEmployeeNo()+"',getdate())";
				        ad.SqlExecuteUpdate(sql);
					 }
					 
					 for(int i=1;i<ls.length;i++){
					 String sql="update user_group set status=1,Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where group_name='"+groupName+"' and " +
					 		"sub_modulename='"+ls[i]+"' and module_name='"+moduleName+"'";
					 ad.SqlExecuteUpdate(sql);
					 }
					 
					 
					 String query="Select distinct group_id,group_name from " +
						"user_group where group_id <= "+groupCount+"  order by group_id;";
					 System.out.println("query--"+query); 
					 
					 rs1=ad.selectQuery(query);
				     ArrayList<String> groups=new ArrayList<String>();
					 	 
					 while(rs1.next()){
					  		 groups.add(rs1.getString(2));
					 } 
					 
					 userGroupForm.setModules(ad.getModules(groupName, groupName));
					 userGroupForm.setSubModules(ad.getSubModules(groupName, moduleName));
					 userGroupForm.setGroups(groups);
					 String id="0";
					 
						if(userGroupForm.getModuleName()!=null &&
								!userGroupForm.getModuleName().equals("") ){
							LoginDao loginDao=new LoginDao();
							
							
							String query1="Select links from user_group where group_name='"+groupName+"' and status=1";
							String query2="Select id from links where " +
									"link_name in(SELECT sub_modulename FROM " +
									"user_group where group_name='"+groupName+"' " +
											"and module_name='"+moduleName+"' and status=1);";
									ResultSet rs=ad.selectQuery(query1);
									ArrayList<String> idList=new ArrayList<String>();
									
									while(rs.next()){
										String v=rs.getString("links");
										if(!v.equals("")) 
											id+=","+v;
										}
									rs=ad.selectQuery(query2);
									while(rs.next()){
										String v=""+rs.getInt("id");
										if(!v.equals("")) 
											id+=","+v;
										}
									Collections.addAll(idList, id.split(","));
							
							userGroupForm.setAllGroupIds(idList);
							System.out.println("-allGroupIds-"+userGroupForm.getAllGroupIds()); 
						}
						
					 userGroupForm.setLinks(ad.getSubLinksIDvaluePair(moduleName));
					 
				     } catch (SQLException e) {
					    e.printStackTrace();
				     }
		
		request.setAttribute("modifySubGroup", "modifySubGroup");
		return mapping.findForward("declareGroup1");
	}
	public ActionForward modifySubGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("modifySubGroup()-----");
		UserGroupForm userGroupForm=(UserGroupForm)form;
		GroupDao ad=new GroupDao();
		HttpSession session= request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		String groupName=userGroupForm.getGroupName();
	
		String moduleName=userGroupForm.getModuleName();

		String[] ls=request.getParameterValues("selectedMainModulesArr");
		for(int i=1;i<ls.length;i++){
			System.out.println("selected main modules="+ls[i]);
			 }
		for(int i=1;i<ls.length;i++){
			 String sql="update user_group set status=1,Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where group_name='"+groupName+"' and " +
			 		"sub_modulename='"+ls[i]+"' and module_name='"+moduleName+"'";
			 ad.SqlExecuteUpdate(sql);
			 }
		
		
		if(moduleName.equalsIgnoreCase("main"))
		{
			String updateGroupStatus="update user_group set status=0,Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where group_name='"+groupName+"' and sub_modulename is null";
			ad.SqlExecuteUpdate(updateGroupStatus);
			for(int i=1;i<ls.length;i++){
				String updateGroupStatus1="update user_group set status=1,Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where module_name='"+ls[i]+"' and sub_modulename is null ";
				ad.SqlExecuteUpdate(updateGroupStatus1);
				}
		}
		else{
			
	
		
			String updateSubGroupStatus="update user_group set status=0,Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where group_name='"+groupName+"' and module_name='"+moduleName+"'" +
					"and sub_modulename is not null";
			 ad.SqlExecuteUpdate(updateSubGroupStatus);
			 for(int i=1;i<ls.length;i++){
				 String updateSubGroupStatus1="update user_group set status=1,Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where group_name='"+groupName+"' and module_name='"+moduleName+"'" +
					"and sub_modulename ='"+ls[i]+"'";
			 ad.SqlExecuteUpdate(updateSubGroupStatus1);
					 }
			}
		
		
		reFresh(mapping, form, request, response);
		return mapping.findForward("declareGroup1");
	}
	
	
	public ActionForward addSubSubGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("addSubSubGroup()-----");
		System.out.println("addSubLinkGroup()----");
		   try{
			   UserGroupForm userGroupForm=(UserGroupForm)form;
				GroupDao ad=new GroupDao();
				HttpSession session= request.getSession();
				UserInfo user=(UserInfo)session.getAttribute("user");
			
				String groupName=userGroupForm.getGroupName();
			    System.out.println("groupName="+groupName);
				String moduleName=userGroupForm.getModuleName();
				System.out.println("moduleName="+moduleName);

				String selectedSubModule=userGroupForm.getSubModuleName();
				System.out.println("selectedSubModule="+selectedSubModule);

				String[] listLinkModule=request.getParameterValues("selectedMainModulesArr");
				for(int i=1;i<listLinkModule.length;i++){
					System.out.println("selectedMainModulesArr="+listLinkModule[i]);
					 }
				
			
				
				String getLinkNameQuery="SELECT link_name FROM links where module='"+moduleName+"' and sub_linkname='"+selectedSubModule+"'";
				ResultSet rs=ad.selectQuery(getLinkNameQuery);
				while(rs.next()){
					String insertSubSubLinkGroupQuery="insert into user_group(group_name,module_name,status,group_id,sub_modulename,sub_linkname,links)" +
					" values('"+groupName+"','"+moduleName+"',0,0,'"+selectedSubModule+"','"+rs.getString("link_name")+"',' ')";
						 ad.SqlExecuteUpdate(insertSubSubLinkGroupQuery);
					
				}	
				
				
				for(int i=1;i<listLinkModule.length;i++){
					String updateSubSubModuleQuery="update user_group set status=1 where group_name='"+groupName+"' and " +
					"module_name='"+moduleName+"' and sub_modulename='"+selectedSubModule+"' and sub_linkname='"+listLinkModule[i]+"'";
			
				System.out.println("updateSubSubModuleQuery="+updateSubSubModuleQuery);
				ad.SqlExecuteUpdate(updateSubSubModuleQuery);
					 
					 }
				}catch(SQLException se){
					se.printStackTrace();
				}
				
				reFreshSubmodule(mapping, form, request, response);
		
		return mapping.findForward("declareGroup2");
	}
	
	public ActionForward modifySubSubGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("modifySubGroup()-----");
		UserGroupForm userGroupForm=(UserGroupForm)form;
		GroupDao ad=new GroupDao();
		HttpSession session= request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		String groupName=userGroupForm.getGroupName();
	
		String moduleName=userGroupForm.getModuleName();

		String selectedSubModule=userGroupForm.getSubModuleName();

		String[] listLinkModule=request.getParameterValues("selectedMainModulesArr");
		for(int i=1;i<listLinkModule.length;i++){
			System.out.println("selectedMainModulesArr="+listLinkModule[i]);
			 }
		
		String updateStatusSubSubModule="update user_group set status=0 where group_name='"+groupName+"' and " +
				"module_name='"+moduleName+"' and sub_modulename='"+selectedSubModule+"' and sub_linkname is not null";
		ad.SqlExecuteUpdate(updateStatusSubSubModule);
		System.out.println("status is changed to 0");
		
		for(int i=1;i<listLinkModule.length;i++){
		
			String updateSubSubModuleQuery="update user_group set status=1 where group_name='"+groupName+"' and " +
				"module_name='"+moduleName+"' and sub_modulename='"+selectedSubModule+"' and sub_linkname='"+listLinkModule[i]+"'";
		
			System.out.println("updateSubSubModuleQuery="+updateSubSubModuleQuery);
			ad.SqlExecuteUpdate(updateSubSubModuleQuery);
			 }
		System.out.println("status is changed to 1");
		reFreshSubmodule(mapping, form, request, response);
		
		
		return mapping.findForward("declareGroup2");
		
	}
	
	
	
	
	
	public ActionForward reFresh(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("reFresh()-----");
		UserGroupForm userGroupForm=(UserGroupForm) form;
		GroupDao ad=new GroupDao();
		HttpSession session= request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		
		String parameter=request.getParameter("str");
		
		
		String groupName=userGroupForm.getGroupName();
		
		String moduleName=userGroupForm.getModuleName();
		
		
		 String countQuery="select group_id from user_group where group_name like '"+groupName+"'";
			System.out.println("countQuery="+countQuery);
			try {
				
				String groupCount="0";
				
			
				ResultSet rs1=ad.selectQuery(countQuery);
				 if(rs1.next()){
					 groupCount=""+rs1.getInt(1);
				 }
				 
		 String query="Select  group_id,group_name from user_group  group by group_id,group_name order by group_id";
		 System.out.println("query in reFresh--"+query); 
		
		 
		 rs1=ad.selectQuery(query);
	     ArrayList<String> groups=new ArrayList<String>();
		 	 
		 while(rs1.next()){
		  		 groups.add(rs1.getString(2));
		 } 
		 userGroupForm.setGroups(groups);
			}catch(SQLException se){
				se.printStackTrace();
			}
		
			
			
			
		
			userGroupForm.setSubModules(ad.getSubModules(userGroupForm.getGroupName(), userGroupForm.getModuleName()));
			userGroupForm.setModules(ad.getModules(userGroupForm.getGroupName(),userGroupForm.getGroupName()));
		
		if(userGroupForm.getModuleName()!=null && !userGroupForm.getModuleName().equals("") ){
			LoginDao loginDao=new LoginDao();
			System.out.println("Module Name****="+userGroupForm.getModuleName());
			ArrayList<String> groupIds=new ArrayList<String>();
			if(userGroupForm.getModuleName().equals("main")){
			 groupIds=loginDao.getGroupAllIds(userGroupForm.getGroupName(),userGroupForm.getGroupName());
			 if(groupIds.size()>1){
				 
				 request.setAttribute("modifyGroupButton", "modifyGroupButton");
				 
			 }
			 else{
				 request.setAttribute("addGroupButton", "addGroupButton");
			 }
			 
			 
			}else{
				 groupIds=loginDao.getGroupAllId(userGroupForm.getGroupName());
				 if(groupIds.size()>1){
					 
					 request.setAttribute("modifySubGroup", "modifySubGroup");
					 
				 }
				 else{
					 request.setAttribute("addSubGroup", "addSubGroup");
				 }
				 
				 
			}
			System.out.println("Getting ArrayList"+groupIds);
			
			
			
			
			userGroupForm.setAllGroupIds(groupIds);
			System.out.println("-allGroupIds-"+userGroupForm.getAllGroupIds());
			log.info("-allGroupIds-"+userGroupForm.getAllGroupIds());
		}
		
		//modifyUserGroupForm.setSubLinks(ad.getSubSubLinksIDvaluePair(modifyUserGroupForm.getModuleName(),modifyUserGroupForm.getSubModuleName()));
		
		userGroupForm.setLinks(ad.getSubLinksIDvaluePair(userGroupForm.getModuleName()));		
		
		return mapping.findForward("declareGroup1");
	}
	
	
	public ActionForward reFreshSubmodule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("reFreshSubmodule()-----");
		UserGroupForm userGroupForm=(UserGroupForm) form;
		GroupDao dao=new GroupDao();
		HttpSession session= request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		String groupName=userGroupForm.getGroupName();
		
		String moduleName=userGroupForm.getModuleName();
		
		
		userGroupForm.setGroups(dao.getGroups(1));
		userGroupForm.setSubModules(dao.getSubModules(userGroupForm.getGroupName(), userGroupForm.getModuleName()));
		
		userGroupForm.setModules(dao.getModules(userGroupForm.getGroupName(),userGroupForm.getGroupName()));
		
		if(userGroupForm.getModuleName()!=null && !userGroupForm.getModuleName().equals("") ){
			LoginDao loginDao=new LoginDao();
			ArrayList<String> groupIds=loginDao.getGroupAllIds(userGroupForm.getGroupName(),userGroupForm.getGroupName());
			userGroupForm.setAllGroupIds(groupIds);
			System.out.println("-allGroupIds-"+userGroupForm.getAllGroupIds()); log.info("-allGroupIds-"+userGroupForm.getAllGroupIds());
		}
		
		
		String sql12="select * from user_group where sub_modulename is not null and sub_linkname is not null " +
		"and group_name='"+groupName+"'";
		
		ResultSet rs=dao.selectQuery(sql12);
		
		try{
		if(rs.next()) {
			userGroupForm.setSubLinks(dao.getSubSubLinksIDvaluePair(userGroupForm.getModuleName()));
			String id="";
			
			String query1="Select links from user_group where group_name='"+groupName+"' and status=1";
			String query2="Select id from links where " +
					"link_name in(SELECT sub_linkname FROM " +
					"user_group where group_name='"+groupName+"' " +
							"and module_name='"+moduleName+"' and status=1);";
					 rs=dao.selectQuery(query1);
					ArrayList<String> idList=new ArrayList<String>();
					
					while(rs.next()){
						String v=rs.getString("links");
						if(!v.equals("")) 
							id+=","+v;
						}
					rs=dao.selectQuery(query2);
					while(rs.next()){
						String v=""+rs.getInt("id");
						if(!v.equals("")) 
							id+=","+v;
						}
					Collections.addAll(idList, id.split(","));
			
					userGroupForm.setAllGroupIds(idList);
			
		}
			
			
		}catch (SQLException se) {
			se.printStackTrace();
		}
		
		
		ArrayList<IdValuePair> a1= dao.getSubSubLinksIDvaluePair(userGroupForm.getModuleName(),userGroupForm.getSubModuleName());
		userGroupForm.setSubLinks(a1);
		
		userGroupForm.setLinks(dao.getLinksIDvaluePair(userGroupForm.getModuleName()));
		return mapping.findForward("declareGroup2");
		}
}
