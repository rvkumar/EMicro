package com.microlabs.it.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
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
import com.microlabs.it.Dao.ITDao;
import com.microlabs.it.form.ITForm;
import com.microlabs.utilities.UserInfo;

public class ITAction extends DispatchAction {
	
	public ActionForward displayITEngineersList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		return mapping.findForward("displayITEngineersList");
	}
	
	public ActionForward displayPoliciesList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	
		return mapping.findForward("displayPolicyList");
	}
	
	
	public ActionForward displayPolicy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		String Id=request.getParameter("sId");
		
		if(Id.equalsIgnoreCase("InternetUsage"))
		{
			return mapping.findForward("InternetUsage");
		}
		if(Id.equalsIgnoreCase("ItProcurement"))
		{
			return mapping.findForward("ItProcurement");
		}
	
		return mapping.findForward("displayPolicyList");
	}
	public ActionForward displayCorporate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	
		return mapping.findForward("displayCorporate");
	}
	public ActionForward displayThirdCMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	
		return mapping.findForward("displayThirdCMS");
	}
	public ActionForward displaySecondCMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	
		return mapping.findForward("displaySecondCMS");
	}
	public ActionForward displayIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("displayIssues()----");
		ITForm itForm=(ITForm)form;
		ArrayList itReqList=new ArrayList();
		try{
			ITDao itdao=new ITDao();
		String getReqQuery="select * from it_request";
		ResultSet rs=itdao.selectQuery(getReqQuery);
	
	
		while (rs.next()) {
			
			ITForm itForm2=new ITForm();
			itForm2.setRequesterId(rs.getString("Request_ID"));
			itForm2.setSubject(rs.getString("Subject"));
			itForm2.setName(rs.getString("Requester_Name"));
			
			itForm2.setAssignedTo(rs.getString("Assigned_To"));
			itForm2.setCategory(rs.getString("Service_Category"));
			itForm2.setDueBy(rs.getString("Due_By"));
			itForm2.setStatus(rs.getString("Status"));
			itForm2.setCreatedDate(rs.getString("Created_Dated"));
			itForm2.setPriority(rs.getString("Priority"));
			itForm2.setCreatedBy(rs.getString("Created_By"));
			itForm2.setCompletedDate(rs.getString("Completed_Date"));
			itForm2.setDepartment(rs.getString("Department"));
			itForm2.setPlant(rs.getString("Plant"));
		itReqList.add(itForm2);	}
					}
			catch (Exception e) {
						
					}
		
		HttpSession session=request.getSession();
		session.setAttribute("itRequestList", itReqList);
		
		return mapping.findForward("displayIssusesForm");
	}

	public ActionForward displayReqIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("displayReqIssues()----");
		ITForm itForm=(ITForm)form;
		String reqStatus=itForm.getStatus();
		
		
		ArrayList itReqList=new ArrayList();
		try{
			ITDao itdao=new ITDao();
			
		String getReqQuery="select * from it_request where Status='"+reqStatus+"'";
		ResultSet rs=itdao.selectQuery(getReqQuery);	
		while (rs.next()) {
			
			ITForm itForm2=new ITForm();
			itForm2.setRequesterId(rs.getString("Request_ID"));
			itForm2.setSubject(rs.getString("Subject"));
			itForm2.setName(rs.getString("Requester_Name"));
			
			itForm2.setAssignedTo(rs.getString("Assigned_To"));
			itForm2.setCategory(rs.getString("Service_Category"));
			itForm2.setDueBy(rs.getString("Due_By"));
			itForm2.setStatus(rs.getString("Status"));
			itForm2.setCreatedDate(rs.getString("Created_Dated"));
			itForm2.setPriority(rs.getString("Priority"));
			itForm2.setCreatedBy(rs.getString("Created_By"));
			itForm2.setCompletedDate(rs.getString("Completed_Date"));
			itForm2.setDepartment(rs.getString("Department"));
			itForm2.setPlant(rs.getString("Plant"));
		itReqList.add(itForm2);	}
					}
			catch (Exception e) {
						
					}
		
		HttpSession session=request.getSession();
		session.setAttribute("itRequestList", itReqList);
		
		return mapping.findForward("displayIssusesForm");
	}
	
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ITForm itForm=(ITForm)form;	
		ITDao ad=new ITDao();
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
			
			String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname,icon_name " +
					" from links  " +
					" where module ='IT'" +
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
				 
				itForm.setContentDescription(rs1.getString("content_description"));
			//	itForm.setFileFullPath(rs1.getString("file_name"));
				// itForm.setVideoFullPath(rs1.getString("video_name"));
			}*/
			
			
			String contentDescription="";
			String uplodedfiles="";
			String uplodedvideos="";
			String getCMSContent="select * from archieves where link_name='"+id+"' and module='Main' and sub_link is null and status='null'";
			ResultSet rsCMSContent=ad.selectQuery(getCMSContent);
			
			
				while(rsCMSContent.next()) {
					 
					itForm.setContentDescription(rsCMSContent.getString("content_description"));
					itForm.setFileFullPath(rsCMSContent.getString("file_name"));
					itForm.setVideoFullPath(rsCMSContent.getString("video_name"));
					 contentDescription=rsCMSContent.getString("content_description");
						uplodedfiles=rsCMSContent.getString("file_name");
						uplodedvideos=rsCMSContent.getString("video_name");
				}
				if(contentDescription.equalsIgnoreCase(""))
				{
					itForm.setContentDescription("No Content is available");
					
				}
				
				if(uplodedfiles.equalsIgnoreCase(""))
				{
					itForm.setFileFullPath("");
				}
				if(uplodedvideos.equalsIgnoreCase(""))
				{
					itForm.setVideoFullPath("");
				}	
		
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		request.setAttribute("MenuIcon", id);
		return mapping.findForward("display");
	}
	
	

	public ActionForward saveITRequstDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ITForm itForm=(ITForm)form;
		ITDao itdao=new ITDao();
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

System.out.println("content_description="+content_description);
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH.mm.ss");
//get current date time with Date()
Date date = new Date();
System.out.println(dateFormat.format(date));

//get current date time with Calendar()
Calendar cal = Calendar.getInstance();
System.out.println("Current Date="+dateFormat.format(cal.getTime()));
String currentDate=dateFormat.format(cal.getTime());

String insertITReqQuery="INSERT INTO `it_request` (`Service_Category`,`Status`,`Mode`,`Priority`,`Request_ID`" +
		",`Requester_Name`,`Assigned_To`,`Asset_Details`,`Contact_Number`,`Department`,`Job_Title`," +
		"`Created_Dated`,`Created_By`,`Plant`,`Location_Details`,`Intercom_Number`," +
		"`System_IP Address`,`Sub_Category`,`Subject`,`Due_By`,`Content_Description`)" +
		" VALUES ('"+itForm.getCategory()+"','"+itForm.getStatus()+"','"+itForm.getMode()+"','"+itForm.getPriority()+"'" +
				",'"+itForm.getRequesterId()+"','"+itForm.getName()+"','"+itForm.getAssignedTo()+"'," +
		"'"+itForm.getAssetDetails()+"','"+itForm.getContactNo()+"','"+itForm.getDepartment()+"','"+itForm.getJobTitle()+"','"+currentDate+"','"+itForm.getCreatedBy()+"'," +
				"'"+itForm.getPlant()+"','"+itForm.getLocationDetails()+"','"+itForm.getIntercomNo()+"','"+itForm.getIPAdress()+"','"+itForm.getSubCategory()+"','"+itForm.getSubject()+"','"+itForm.getDueBy()+"'," +
		"'"+content_description+"')";
System.out.println("insertITReqQuery="+insertITReqQuery);
int i=0;
i=itdao.SqlExecuteUpdate(insertITReqQuery);
HttpSession session=request.getSession();
if(i>0){
	session.setAttribute("status", "Request Added Successfully");
System.out.println("Request Added Successfully");
}
else
{
	session.setAttribute("status", "Request Not Added.Please Check...");
	System.out.println("values are not inserted");	
}

	
		
		return mapping.findForward("newITRequeshForm");
		
	}

	public ActionForward newITRequeshForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("newITRequeshForm()---");
		ITForm itForm = (ITForm) form;
		itForm.setCategory("");
		itForm.setStatus("");
		itForm.setMode("");
		itForm.setPriority("");
		itForm.setName("");
		itForm.setAssignedTo("");
		itForm.setAssetDetails("");
		itForm.setContactNo("");
		itForm.setDepartment("");
		itForm.setJobTitle("");
		itForm.setCreatedBy("");
		itForm.setPlant("");
		itForm.setLocationDetails("");
		itForm.setIntercomNo("");
		itForm.setIPAdress("");
		itForm.setSubCategory("");
		itForm.setRequesterId("");
		itForm.setDueBy("");
		itForm.setSubject("");
		
		 
		String subLinkName=request.getParameter("subLink"); 	
		String module=request.getParameter("module"); 	
		String linkName=request.getParameter("linkName"); 		
		HttpSession session=request.getSession();
		
		LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
		
		
		ITDao ad=new ITDao();
		
		String sql="select * from links where link_name='"+linkName+"' and module='"+module+"' and" +
				" sub_linkname='"+subLinkName+"'";
		
		ResultSet rs=ad.selectQuery(sql);
		
		try{
			
			while (rs.next()) {
				
			}
			
			
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		session.setAttribute("SUBLINKS", hm);
		
		
		
		
		
		
		session.setAttribute("status", " ");
		request.setAttribute("submitButton", "submitButton");
		return mapping.findForward("newITRequeshForm");

	}

	public ActionForward display1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ITForm itForm=(ITForm)form;	
		ITDao ad=new ITDao();
		
		
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
					 
					itForm.setContentDescription(rs11.getString("content_description"));
					itForm.setFileFullPath(rs11.getString("file_name"));
					itForm.setVideoFullPath(rs11.getString("video_name"));
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
			" where module='IT'" +
			" and id in(16,17,77,78,79,80) order by priority";
			
			
			ResultSet rs1=ad.selectQuery(sql1);
				
				
			//select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as 
			// url,module,link_name,sub_linkname  from links   where module ='News And Media' and id 
			//in(38,39,40,45,46) order by id	
			
			//LinkedHashMap hm=new LinkedHashMap();	
				
			
			LinkedHashSet<String> mainMenuSet=new LinkedHashSet<String>();
			LinkedList<ITForm> allLinkList=new LinkedList<ITForm>();
		 	LinkedHashMap<ITForm,LinkedList<ITForm>> finalLnkdList= new LinkedHashMap<ITForm,LinkedList<ITForm>>();
		 	
			while(rs1.next()){
				
				System.out.println("rs1.getString(sub_linkname) **************"+rs1.getString("sub_linkname"));
				mainMenuSet.add(rs1.getString("sub_linkname"));
				ITForm l=new ITForm();
					l.setLinkPath(rs1.getString("url"));
					l.setPriority(""+rs1.getString("priority"));
					l.setSubLinkName(rs1.getString("sub_linkname"));
					l.setLinkName(rs1.getString("link_name"));
					l.setIconName(rs1.getString("icon_name"));
				allLinkList.add(l);
			}	
		 		
				
			 	for(String main:mainMenuSet){
			 		LinkedList<ITForm> links=new LinkedList<ITForm>();
			 		ITForm temp=null;
			 		System.out.println(" -- "+main+"");
			 		for(ITForm aa:allLinkList){
			 				for(ITForm sl:allLinkList)
			 				{
			 				System.out.println("("+sl.getLinkName()+" -- "+sl.getSubLinkName()+") -- "+main+"");
			 						
			 					if(!sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main))
			 						links.add(sl);
			 					else if(sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main) )
			 						temp=sl;
			 				}
			 		}
			 		for(ITForm g:links) System.out.println(g.getLinkName());
	 				
	 				System.out.println(main+"---temp lnk nmae--"+temp.getLinkName());
	 				finalLnkdList.put(temp, links);
			 	}
			 	
			 	itForm.setLinkName(linkName);
			 	
			 	
			 	
			 	String contentDescription="";
				String uplodedfiles="";
				String uplodedvideos="";
				String getCMSContent="select * from archieves where link_name='"+linkName+"' and module='"+module+"' and sub_link is null and status='null'";
				ResultSet rsCMSContent=ad.selectQuery(getCMSContent);
				
				
					while(rsCMSContent.next()) {
						 
						itForm.setContentDescription(rsCMSContent.getString("content_description"));
						itForm.setFileFullPath(rsCMSContent.getString("file_name"));
						itForm.setVideoFullPath(rsCMSContent.getString("video_name"));
						 contentDescription=rsCMSContent.getString("content_description");
							uplodedfiles=rsCMSContent.getString("file_name");
							uplodedvideos=rsCMSContent.getString("video_name");
					}
					if(contentDescription.equalsIgnoreCase(""))
					{
						itForm.setContentDescription("No Content is available");
						
					}
					
					if(uplodedfiles.equalsIgnoreCase(""))
					{
						itForm.setFileFullPath("");
					}
					if(uplodedvideos.equalsIgnoreCase(""))
					{
						itForm.setVideoFullPath("");
					}
			 	
			 	
			 	
			 	
			 	
			 	
			 	
			 	
			 	
			 	System.out.println("finalLnkdList.size--"+finalLnkdList.size()+"\n\n\n\n\n");
		 	
		 	//LinkedHashMap<LinksForm,ArrayList<LinksForm>> map = new LinkedHashMap<LinksForm,ArrayList<LinksForm>>();
		 	for (Map.Entry<ITForm,LinkedList<ITForm>> entry : finalLnkdList.entrySet()) {
		 	    System.out.println("Key = " + entry.getKey().getLinkName() +"" +
		 	    		"Link Path is"+ entry.getKey().getLinkPath()+"Get Image "+ entry.getKey().getIconName()+ ", Value = " + entry.getValue().size()+"");
		 	    for (ITForm lin : entry.getValue()) {

					System.out.println("--->"+lin.getLinkName());
				}
		 	}
		 	
		 	
		 	String sql2="select * from cms_sublinks where main_linkname='"+module+"' " +
		 			"and sub_linkname='"+linkName+"' and archived_status='0'";
		 	
		 	
		 	ResultSet rs3=ad.selectQuery(sql2);
		 	
		 	ITForm newsMediaForm1=null;
		 	
		 	ArrayList a2=new ArrayList();
		 	while (rs3.next()) {
		 		newsMediaForm1=new ITForm();
		 		
		 		
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
		
		ITForm itForm=(ITForm)form;	
		ITDao ad=new ITDao();
		
		
		String linkName="Help Desk"; 
		
		String module="IT"; 		
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
					 uplodedfiles+=rsCMSContent.getString("file_name")+",";
					 uplodedvideos+=rsCMSContent.getString("video_name")+",";
					itForm.setContentDescription(contentDescription);
					itForm.setFileFullPath(uplodedfiles);
					itForm.setVideoFullPath(uplodedvideos);
				}
				if(contentDescription.equalsIgnoreCase(""))
				{
					itForm.setContentDescription("No Content is available");
				}
				if(uplodedfiles.equalsIgnoreCase(""))
				{
					itForm.setFileFullPath("");
				}
				if(uplodedvideos.equalsIgnoreCase(""))
				{
					itForm.setVideoFullPath("");
				}		
			
				itForm.setLinkName(linkName);
		 	
		 	session.setAttribute("SUBLINKS", session.getAttribute("SUBLINKS"));
				
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		request.setAttribute("MenuIcon", module);
		request.setAttribute("linkName", "IT");
		return mapping.findForward("displayCMS1");
	}
	

}


