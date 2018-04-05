package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.admin.form.UserRightsForm;
import com.microlabs.login.dao.LoginDao;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.main.form.MailInboxForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.UserInfo;


public class UserRightsAction extends DispatchAction{
	
	
	public String replace(String str)
	{
		String replaced_value=str.replace("'", "");
		return replaced_value;
	}
	
	public String Empname(String a)
	{
		UserDao grouplist=new UserDao();
		String b = "";
		if(a==null)
		{
		  return b;	
		}
		
		if(!a.equalsIgnoreCase(""))
		{	
			
		
		String emp = "select EMP_FULLNAME from emp_official_info where pernr = '"+a+"'";
		ResultSet rs = grouplist.selectQuery(emp);
		try {
			if(rs.next())
			{
				b=rs.getString("emp_fullname"); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		return b;
		
	}
	
	public ActionForward getAuditreport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserRightsForm userRightsForm=(UserRightsForm)form;
		
		UserDao grouplist=new UserDao();
		
		ArrayList mList=new ArrayList();
		ArrayList mLabelList=new ArrayList();
		
		String ba="select * from links where  module ='HR' order by link_name";
		ResultSet ad=grouplist.selectQuery(ba);
		try {
			while(ad.next())
			{
				mList.add(ad.getString("id"));
				mLabelList.add(ad.getString("link_name"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userRightsForm.setModuleIdList(mList);
		userRightsForm.setModuleLabelList(mLabelList);
		
		
		
		//
		
		ArrayList gija=new ArrayList();
		String bbc="select replace(user_group.attendance_loc,',',' ') as attendance_loc,emp_official_info.EMP_FULLNAME,emp_official_info.locid,emp_official_info.pernr,DEPARTMENT.DPTSTXT,DESIGNATION.DSGSTXT,users.*,user_group.* "
				+ "from user_group,users,emp_official_info,department,designation where user_group.incsublinks like '%"+userRightsForm.getModuleId()+"%'  and user_group.id=users.group_id and"
				+ " emp_official_info.pernr=users.employeenumber and emp_official_info.dptid=department.dptid and emp_official_info.dsgid=designation.dsgid";
		ResultSet n=grouplist.selectQuery(bbc);
		try {
			while(n.next())
			{
				UserRightsForm u=new UserRightsForm();
				u.setPernr(n.getString("pernr"));
				u.setLoc(n.getString("locid"));
				u.setEmpname(n.getString("Emp_fullname"));
				u.setGroupName(n.getString("group_name"));
				u.setDpt(n.getString("dptstxt"));
				u.setDesg(n.getString("dsgstxt"));
				u.setAtten_loc(n.getString("Attendance_loc"));
				u.setCreated_by(Empname(n.getString("Created_by")));
				u.setCreated_date(n.getString("Created_date"));
				u.setModified_by(Empname(n.getString("Modified_by")));
				u.setModified_date(n.getString("Modified_date"));

				gija.add(u);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(gija.size()>0)
		request.setAttribute("gija", gija);
		
		
		return mapping.findForward("displayAudit");
	}
	
	public ActionForward displayAuditReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserRightsForm userRightsForm=(UserRightsForm)form;
		
		UserDao grouplist=new UserDao();
		
		ArrayList mList=new ArrayList();
		ArrayList mLabelList=new ArrayList();
		
		String ba="select * from links where  module ='HR' order by link_name";
		ResultSet ad=grouplist.selectQuery(ba);
		try {
			while(ad.next())
			{
				mList.add(ad.getString("id"));
				mLabelList.add(ad.getString("link_name"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userRightsForm.setModuleIdList(mList);
		userRightsForm.setModuleLabelList(mLabelList);
		
		
		return mapping.findForward("displayAudit");
	}
	
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserRightsForm userRightsForm=(UserRightsForm)form;
		
		try{
			
			UserDao grouplist=new UserDao();
			String sql="Select id,group_name from user_group order by id";
			ResultSet rs=grouplist.selectQuery(sql);
			
			
			ArrayList ar_id=new ArrayList();
			ArrayList ar_name=new ArrayList();
				
			while (rs.next()) {
					
					ar_id.add(rs.getString("id"));
					ar_name.add(rs.getString("group_name"));
				
				}
				
				userRightsForm.setAr_id(ar_id);
				userRightsForm.setAr_name(ar_name);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		String module=request.getParameter("id");
		
		
		request.setAttribute("MenuIcon",module);
		//userRightsForm.setMessage("");

		return mapping.findForward("display");
	}
	
	
	public ActionForward addSubLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserRightsForm userRightsForm=(UserRightsForm)form;
		
		return mapping.findForward("display");
	}
	
	
	public ActionForward displayListUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserRightsForm userRightsForm=(UserRightsForm)form;
		
		String userName=request.getParameter("uId");
		
		UserDao ad=new UserDao();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String plantID=user.getPlantId();
		
		String sql="select * from users where Activated='On'";
		try{
			
			ResultSet rs=ad.selectQuery(sql);
			UserRightsForm userRightsForm1=null;
			
			ArrayList a1=new ArrayList();
			
			while(rs.next()) {
				userRightsForm1=new UserRightsForm();
				userRightsForm1.setUserName(rs.getString("username"));
				userRightsForm1.setFullName(rs.getString("fullname"));
				
				userRightsForm1.setGroupName(rs.getString("groupname"));
				
				a1.add(userRightsForm1);
			}
			
			request.setAttribute("userDetails", a1);
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		return mapping.findForward("displayUsers");
	}
	
	
	public ActionForward getLinks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserRightsForm userRightsForm=(UserRightsForm)form;
		
		String groupID=userRightsForm.getGrouplistid();
		
		UserDao ad=new UserDao();
		
	
		
		System.out.println("Getting a Group ID is **************"+groupID);
		
		
		String sql="select include_links,group_name from user_group where id='"+groupID+"'";
		
		ResultSet rs=ad.selectQuery(sql);
		String groupName="";
		try 
		{
			if(rs.next()) {
				groupName=rs.getString("group_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		userRightsForm.setModules(ad.getModules(groupName, groupID));
		
		userRightsForm.setSelectedModules(ad.getSelectedModules(userRightsForm.getGroupName(),groupID,userRightsForm.getModuleName()));
		
		
		String menuIcon=request.getParameter("MenuIcon2");
		request.setAttribute("MenuIcon", menuIcon);
		return display(mapping, form, request, response);
		//return mapping.findForward("display");
	}
	
	
	public ActionForward userRight(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserRightsForm userRightsForm=(UserRightsForm)form;
		UserDao dao=new UserDao();
		LoginDao loginDao=new LoginDao();
		HttpSession session= request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		boolean validUserName=false;
		
		System.out.println("");
		
		String groupID=userRightsForm.getGrouplistid();
		
		String moduleName=userRightsForm.getModuleName();
		
		String sql="select group_name from user_group where id='"+groupID+"'";
		
		ResultSet rs=dao.selectQuery(sql);
		try 
		{
			if(rs.next()) {
				String groupName=rs.getString("group_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//System.out.println("GROUP ID USER LOGGED IN@@@@@@@@@@@@@@@@@@@@@@@"+user.getGroupId()); log.info("GROUP ID USER LOGGED IN@@@@@@@@@@@@@@@@@@@@@@@   "+user.getGroupId());
		
		if(!groupID.equals("-Select-")){
			if(!moduleName.equals("-Select-")){
				String usersGroupName=loginDao.usersGroupName(groupID);
				      
				userRightsForm.setModules(dao.getUserModules(usersGroupName,groupID));
				ArrayList<String> groupIds=new ArrayList<String>();
				
				if(!userRightsForm.getModuleName().equalsIgnoreCase("Main")){
				userRightsForm.setSubLinks(dao.getUserSubModules(usersGroupName,groupID,
				userRightsForm.getModuleName()));
				userRightsForm.setSelectedSubLinks(dao.getSelectedSubModules(usersGroupName,groupID,
				userRightsForm.getModuleName()));
				//userRightsForm.setSubModules(dao.getUserSubModulesDrpDown(groupName, userRightsForm.getUserName(), userRightsForm.getModuleName()));
				}else{
					groupIds=loginDao.getGroupAllIds(usersGroupName,groupID,moduleName);
					
					userRightsForm.setLinks(dao.getUserModulesM(usersGroupName,groupID,moduleName));
					userRightsForm.setSelectedModules(dao.getSelectedModules(usersGroupName,groupID,
					userRightsForm.getModuleName()));
					
					userRightsForm.setLocations(dao.getAllLocations(usersGroupName,groupID,moduleName));
					userRightsForm.setSelectedLocations(dao.getSelectedLocations(usersGroupName,groupID,
							userRightsForm.getModuleName()));
					
					
					
					//userRightsForm.setSubModules(dao.getUserSubModulesDrpDown(groupName, userRightsForm.getUserName(), userRightsForm.getModuleName()));
					//userRightsForm.setSubModules(dao.getUserSubModules(groupName, userRightsForm.getUserName()));
					
					userRightsForm.setAllGroupIds(groupIds);
				}
				
			}
			else{
				userRightsForm.setMessage("Select Module name");
				
			}
		}
		else{
			userRightsForm.setMessage("Please select Group Name");
		}
		
		
		request.setAttribute("MenuIcon", request.getParameter("MenuIcon2"));
		
		//userRightsForm.setModules(dao.getUserMainModules(groupName, userRightsForm.getUserName()));
		return display(mapping, form, request, response);
		//return mapping.findForward("display");
	}
	
	
	public ActionForward addUserRight(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		UserRightsForm userRightsForm=(UserRightsForm)form;
		
		String groupName=userRightsForm.getGrouplistid();
		HttpSession session= request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		UserDao ad=new UserDao();
		
		String[] mainLinks=request.getParameterValues("selectedMainModulesArr");
		
		
		String checkValues=request.getParameter("cValues");
		String uncheckValues=request.getParameter("unValues");
		
		
		String userMainLinks="";
		String inSubLinks="";
		String inSubSubLinks="";
		
		
		String inLinks="";
		
		String availableSubLinks="";
		String sql12="select include_links,incsublinks,incsubsublinks from user_group where id='"+userRightsForm.getGrouplistid()+"'";
		
		try{
			ResultSet rs=ad.selectQuery(sql12);
			if (rs.next()) {
				inLinks=rs.getString("include_links");
				inSubLinks=rs.getString("incsublinks")+",";
				availableSubLinks=rs.getString("incsublinks");
				inSubSubLinks = rs.getString("incsubsublinks");
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		if(userRightsForm.getModuleName().equalsIgnoreCase("Main"))
		{
			String locCheckValues=request.getParameter("locCheckvalues");
			
			if(!(locCheckValues.equalsIgnoreCase("")))
				try{
					locCheckValues=locCheckValues.substring(0,(locCheckValues.length()-1));
				}catch (NullPointerException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				} 
			System.out.println("locCheckValues="+locCheckValues);
			
		for(int i=1;i<mainLinks.length;i++){
			System.out.println("Getting a User Link is "+mainLinks[i]);
			userMainLinks+=mainLinks[i]+",";
		}
		
		
		inLinks+=","+userMainLinks.substring(0, userMainLinks.lastIndexOf(","));
		String[] array = new HashSet<String>(Arrays.asList(inLinks.split(","))).toArray(new String[0]);
		
		List<String> list = new ArrayList<String>(array.length);  
		for (String s : array) {  
		    list.add(s);  
		}  
		
		
		String[] a1=uncheckValues.split(",");
		
		List<String> list1 = new ArrayList<String>(a1.length);  
		for (String s : a1) {  
		    list1.add(s);  
		}  
	    
		list.removeAll(list1);
		
		String inkLinks="";
		
		for(String s : list) {  
			inkLinks+=s+",";
		}  
		
		String sql="update user_group set include_links='"+inkLinks.substring(0, inkLinks.lastIndexOf(","))+"',locations='"+locCheckValues+"',Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate()" +
				" where id='"+userRightsForm.getGrouplistid()+"'";
		
		int a= ad.SqlExecuteUpdate(sql);
		
		if(a>0){
			userRightsForm.setMessage("User Details Modified Successfully");
		}
		}else{
			
			String[] subLinks=request.getParameterValues("selectedSubModulesArr");
			
			String userSubLinks="";
			
			if(subLinks!=null)
			{
			for(int i=1;i<subLinks.length;i++){
				userSubLinks+=subLinks[i]+",";
			}
		
			
			
			System.out.println("inSubLinks+userSubLinks.split"+(inSubLinks+userSubLinks.split(",")));
			
			String inclLinks="";
			
			if(checkValues.equalsIgnoreCase(""))
			{
				String[] array = new HashSet<String>(Arrays.asList(availableSubLinks.split(","))).toArray(new String[0]);
				List<String> list = new ArrayList<String>(array.length);
				List<String> list3 = new ArrayList<String>();
				for(int i=0;i<array.length;i++){
					inclLinks+=array[i]+",";
				}
				
				String[] a2=inclLinks.split(",");
				for (String s : a2) {  
					list3.add(s);  
				} 
				String[] a1=uncheckValues.split(",");
				/*get user unchecked sub links and sub links of unchecked sub links*/
				String unSubValues="";
				List<String> list1 = new ArrayList<String>(a1.length);  
				for (String s : a1) {
					System.out.println(s);
					try{
						ResultSet unRS = ad.selectQuery("select id from links where sub_linkname=(select link_name from links where id = "+s+")");
						while(unRS.next()){
							unSubValues += unRS.getString(1)+",";
						}
					}
					catch(SQLException sqle){
						sqle.printStackTrace();
					}
				    list1.add(s);  
				} 
				list3.removeAll(list1);
				String inkLinks="";
				
				for(String s : list3) {  
					inkLinks+=s+",";
				}  
				System.out.println("availableSubLinks="+inkLinks.substring(0, inkLinks.lastIndexOf(",")));
				String sql="update user_group set incsublinks='"+inkLinks.substring(0, inkLinks.lastIndexOf(","))+"',Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate()" +
				" where id='"+userRightsForm.getGrouplistid()+"'";
				
				int a= ad.SqlExecuteUpdate(sql);
				
				/*delete from incsubsub links*/
				String[] subsubDBArray = new HashSet<String>(Arrays.asList(inSubSubLinks.split(","))).toArray(new String[0]);
				
				List<String> subsubDBlist = new ArrayList<String>(array.length);  
				for (String s : subsubDBArray) {  
				    subsubDBlist.add(s);  
				}
				if(!unSubValues.equalsIgnoreCase("")){
					unSubValues = unSubValues.substring(0, unSubValues.lastIndexOf(","));
					String[] subsubSelectedArray = new HashSet<String>(Arrays.asList(unSubValues.split(","))).toArray(new String[0]);
					
					List<String> subsubUnSelectedlist = new ArrayList<String>(array.length);  
					for (String s : subsubSelectedArray) {
						subsubUnSelectedlist.add(s);
					}
					subsubDBlist.removeAll(subsubUnSelectedlist);
				}
				
				String inSSLinks="";
				
				for(String ss : subsubDBlist) {  
					inSSLinks+=ss+",";
				}
				//------------------------------
				
				
				ad.SqlExecuteUpdate("update user_group set incsubsublinks='"+inSSLinks.substring(0, inSSLinks.lastIndexOf(","))+"',Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where id='"+userRightsForm.getGrouplistid()+"'");
				
				if(a>0){
					userRightsForm.setMessage("User Details Modified Successfully");
				}
				
			}else{
			
				
				
			/*add user selected sub links and available sub links in db */
			String inc1=inSubLinks+userSubLinks.substring(0, userSubLinks.lastIndexOf(","));
			
			
			String[] array = new HashSet<String>(Arrays.asList(inc1.split(","))).toArray(new String[0]);
			
			for(int i=0;i<array.length;i++){
				inclLinks+=array[i]+",";
			}
			
			
			List<String> list = new ArrayList<String>(array.length);  
			for (String s : array) {  
			    list.add(s);  
			}  
			
			/*get user unchecked sub links and sub links of unchecked sub links*/
			String unSubValues="";
			if(!uncheckValues.equalsIgnoreCase("")){
			String[] a1=uncheckValues.split(",");
			
			
			List<String> list1 = new ArrayList<String>(a1.length);  
			for (String s : a1) {
				System.out.println(s);
				try{
					ResultSet unRS = ad.selectQuery("select id from links where sub_linkname=(select link_name from links where id = "+s+")");
					while(unRS.next()){
						unSubValues += unRS.getString(1)+",";
					}
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
				}
			    list1.add(s);  
			}  
		    
			list.removeAll(list1);
			}
			
			String inkLinks="";
			
			for(String s : list) {  
				inkLinks+=s+",";
			}  
			
			
			String sql="update user_group set incsublinks='"+inkLinks.substring(0, inkLinks.lastIndexOf(","))+"',Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where id='"+userRightsForm.getGrouplistid()+"'";
			int a= ad.SqlExecuteUpdate(sql);
			/*delete from incsubsub links*/
			//String[] subsubDBArray = new HashSet<String>(Arrays.asList(inSubSubLinks.split(","))).toArray(new String[0]);
			
			List<String> subsubDBlist = new ArrayList<String>(array.length);  
			//for (String s : subsubDBArray) {  
			  //  subsubDBlist.add(s);  
			//}
			if(!unSubValues.equalsIgnoreCase("")){
				unSubValues = unSubValues.substring(0, unSubValues.lastIndexOf(","));
				String[] subsubSelectedArray = new HashSet<String>(Arrays.asList(unSubValues.split(","))).toArray(new String[0]);
				
				List<String> subsubUnSelectedlist = new ArrayList<String>(array.length);  
				for (String s : subsubSelectedArray) {
					subsubUnSelectedlist.add(s);
				}
				subsubDBlist.removeAll(subsubUnSelectedlist);
			}
			
			String inSSLinks="";
			
			for(String ss : subsubDBlist) {  
				inSSLinks+=ss+",";
			}
			//------------------------------
			
			
			//ad.SqlExecuteUpdate("update user_group set incsubsublinks='"+inSSLinks.substring(0, inSSLinks.lastIndexOf(","))+"' where id='"+userRightsForm.getGrouplistid()+"'");
			if(a>0){
				userRightsForm.setMessage("User Details Modified Successfully");
				userRight(mapping, form, request, response);
			}
			}
			}
			//userRightsForm.setSubModules(ad.getUserSubModules(groupName, userRightsForm.getUserName(),userRightsForm.getModuleName()));
		}
		
		//request.setAttribute("MenuIcon", request.getParameter("MenuIcon2"));
		//userRightsForm.setModules(ad.getUserMainModules(groupName, userRightsForm.getGrouplistid()));
		//return mapping.findForward("display");
		
		//}
		request.setAttribute("MenuIcon", request.getParameter("MenuIcon2"));
		userRightsForm.setModules(ad.getUserMainModules(groupName, userRightsForm.getGrouplistid()));
		return userRight(mapping, form, request, response);
	//	return display(mapping, form, request, response);
	
	}
	
	
	public ActionForward addSubUserRight(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		UserRightsForm userRightsForm=(UserRightsForm)form;
		
		String groupID=userRightsForm.getGrouplistid();
		
		UserDao ad=new UserDao();
		
		String[] mainLinks=request.getParameterValues("selectedMainModulesArr");
		
		HttpSession session= request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String checkValues=request.getParameter("cValues");
		
		String uncheckValues=request.getParameter("unValues");
		
		String userMainLinks="";
		String inSubSubLinks="";
		String inSubLinks="";
		String availableSublinks="";
		
		
		String inLinks="";
		
		
		String sql12="select include_links,incsublinks,incsubsublinks from user_group where " +
				"id='"+groupID+"'";
		
		try{
			ResultSet rs=ad.selectQuery(sql12);
			while (rs.next()) {
				inLinks=rs.getString("include_links");
				inSubLinks=rs.getString("incsublinks")+",";
				inSubSubLinks=rs.getString("incsubsublinks")+",";
				availableSublinks=rs.getString("incsubsublinks");
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		if(userRightsForm.getModuleName().equalsIgnoreCase("Main"))
		{
		for(int i=1;i<mainLinks.length;i++){
			System.out.println("Getting a User Link is "+mainLinks[i]);
			userMainLinks+=mainLinks[i]+",";
		}
		
		
		inLinks+=","+userMainLinks.substring(0, userMainLinks.lastIndexOf(","));
		String[] array = new HashSet<String>(Arrays.asList(inLinks.split(","))).toArray(new String[0]);
		
		List<String> list = new ArrayList<String>(array.length);  
		for(String s : array) {  
		    list.add(s);  
		}  
		
		
		String[] a1=uncheckValues.split(",");
		
		List<String> list1 = new ArrayList<String>(a1.length);  
		for (String s : a1) {  
		    list1.add(s);  
		}  
	    
		list.removeAll(list1);
		
		String inkLinks="";
		
		for(String s : list) {  
			inkLinks+=s+",";
		}  
		
		
		String sql="update user_group set include_links='"+inkLinks.substring(0, inkLinks.lastIndexOf(","))+"',Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() " +
				" where id='"+groupID+"'";
		
		int a= ad.SqlExecuteUpdate(sql);
		
		if(a>0){
			userRightsForm.setMessage("User Details Modified Successfully");
		}
		}else{
			String inclLinks="";
			
			
		//	String inSubLink="";
			
			if(inSubSubLinks.equalsIgnoreCase("") || inSubSubLinks.equalsIgnoreCase(",")){
				inSubSubLinks=checkValues;
			}else{
				inSubSubLinks=inSubSubLinks+checkValues;
			}
			if(inSubSubLinks.equalsIgnoreCase(""))
			{
				String i="1";
				if(availableSublinks.equalsIgnoreCase(""))
				{
					i="1";
				}
				else{
					i=availableSublinks;
				}
				String sql="update user_group set incsubsublinks='"+i+"',Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() " +
				" where id='"+groupID+"'";
				
				int a= ad.SqlExecuteUpdate(sql);
			
				if(a>0){
					userRightsForm.setMessage("User Details Modified Successfully");
				}
			}else{
				inclLinks = inSubLinks;
				String inc=inSubSubLinks.substring(0, inSubSubLinks.lastIndexOf(","));
			
				String[] array = new HashSet<String>(Arrays.asList(inc.split(","))).toArray(new String[0]);

				List<String> list = new ArrayList<String>(array.length);  
				for (String s : array) {  
					System.out.println("Getting a S is ***********"+s);
					try{
						ResultSet unRS = ad.selectQuery("select id from links where link_name=(select sub_linkname from links where id = "+s+")");
						while(unRS.next()){
							if(!inclLinks.contains(unRS.getString(1))){
								inclLinks += unRS.getString(1)+",";
							}
						}
					}
					catch(SQLException sqle){
						sqle.printStackTrace();
					}
				    list.add(s);  
				}  
				String[] a1=uncheckValues.split(",");
				
				List<String> list1 = new ArrayList<String>(a1.length);  
				for (String s : a1) {  
					
					System.out.println("Getting a S is ***********"+s);
				    list1.add(s);  
				}  
			    
				list.removeAll(list1);
				
				String inkLinks="";
				
				for(String s : list) {  
					inkLinks+=s+",";
				}  
			
			
				String sql="update user_group set incsubsublinks='"+inkLinks.substring(0, inkLinks.lastIndexOf(","))+"',Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where id='"+groupID+"'";
				
				int a= ad.SqlExecuteUpdate(sql);
				
				sql="update user_group set incsublinks='"+inclLinks.substring(0, inclLinks.lastIndexOf(","))+"',Modified_By='"+user.getEmployeeNo()+"',Modified_Date=getdate() where id='"+groupID+"'";
				
				int a11= ad.SqlExecuteUpdate(sql);
			
				if(a>0){
					userRightsForm.setMessage("User Details Modified Successfully");
					userRight(mapping, form, request, response);
				}
			}
			//userRightsForm.setSubModules(ad.getUserSubModules(groupName, userRightsForm.getUserName(),userRightsForm.getModuleName()));
		}
		
		request.setAttribute("MenuIcon", request.getParameter("MenuIcon2"));
		userRightsForm.setModules(ad.getUserMainModules(groupID, groupID));
		//userRightSubModule(mapping, form, request, response);
		userRight(mapping, form, request, response);
		return mapping.findForward("display");
	}
	
	
	//for sub link sub module insertion in db
	public ActionForward addSubUserRight1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		UserRightsForm userRightsForm=(UserRightsForm)form;
		String groupName=userRightsForm.getGrouplistid();
		UserDao ad=new UserDao();
		String[] mainLinks=request.getParameterValues("selectedMainModulesArr");
		String checkValues=request.getParameter("cValues");
		String uncheckValues=request.getParameter("unValues");
		String userMainLinks="";
		String inSubLinks="";
		String inSubSubLinks="";
		
		
		String inLinks="";
		
		
		String sql12="select include_links,incsublinks,incsubsublinks from users where " +
				"username='"+userRightsForm.getUserName()+"'";
		
		try{
			ResultSet rs=ad.selectQuery(sql12);
			while (rs.next()) {
				inLinks=rs.getString("include_links");
				inSubLinks=rs.getString("incsublinks")+",";
				inSubSubLinks=rs.getString("incsubsublinks")+",";
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		if(userRightsForm.getModuleName().equalsIgnoreCase("Main"))
		{
			for(int i=1;i<mainLinks.length;i++){
				System.out.println("Getting a User Link is "+mainLinks[i]);
				userMainLinks+=mainLinks[i]+",";
			}
			inLinks+=","+userMainLinks.substring(0, userMainLinks.lastIndexOf(","));
			String[] array = new HashSet<String>(Arrays.asList(inLinks.split(","))).toArray(new String[0]);
		
			List<String> list = new ArrayList<String>(array.length);  
			for(String s : array) {  
				list.add(s);  
			} 
			String[] a1=uncheckValues.split(",");
			List<String> list1 = new ArrayList<String>(a1.length);  
			for (String s : a1) {  
				list1.add(s);  
			}  
			list.removeAll(list1);
			String inkLinks="";
			for(String s : list) {  
				inkLinks+=s+",";
			}  
		
		
			String sql="update users set include_links='"+inkLinks.substring(0, inkLinks.lastIndexOf(","))+"'" +
				" where username='"+userRightsForm.getUserName()+"'";
		
			int a= ad.SqlExecuteUpdate(sql);
		
			if(a>0){
				userRightsForm.setMessage("User Details Modified Successfully");
			}
		}
		else{
			String inclLinks=inSubLinks;
			String inSubLink="";
			
			if(inSubSubLinks.equalsIgnoreCase("") || inSubSubLinks.equalsIgnoreCase(",")){
				inSubLink=checkValues;
			}else{
				inSubLink=inSubSubLinks+checkValues;
			}
			
			String inc=inSubLink.substring(0, inSubLink.lastIndexOf(","));
			
			String[] array = new HashSet<String>(Arrays.asList(inc.split(","))).toArray(new String[0]);
			
			List<String> list = new ArrayList<String>(array.length);  
			for (String s : array) {  
				System.out.println("Getting a S is ***********"+s);
				try{
					ResultSet unRS = ad.selectQuery("select id from links where link_name=(select sub_linkname from links where id = "+s+")");
					while(unRS.next()){
						if(!inclLinks.contains(unRS.getString(1))){
							inclLinks += unRS.getString(1)+",";
						}
					}
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
				}
			    list.add(s);  
			}  
			
			
			String[] a1=uncheckValues.split(",");
			
			List<String> list1 = new ArrayList<String>(a1.length);  
			for (String s : a1) {  
				
				System.out.println("Getting a S is ***********"+s);
			    list1.add(s);  
			}  
		    
			list.removeAll(list1);
			
			String inkLinks="";
			
			for(String s : list) {  
				inkLinks+=s+",";
			}  
			
			
			String sql="update users set incsubsublinks='"+inkLinks.substring(0, inkLinks.lastIndexOf(","))+"' where username='"+userRightsForm.getUserName()+"'";
			
			int a= ad.SqlExecuteUpdate(sql);
			sql="update users set incsublinks='"+inclLinks.substring(0, inclLinks.lastIndexOf(","))+"' where username='"+userRightsForm.getUserName()+"'";
			
			int a11= ad.SqlExecuteUpdate(sql);
			
			if(a>0){
				userRightsForm.setMessage("User Details Modified Successfully");
				userRight(mapping, form, request, response);
			}
			
			userRightsForm.setSubModules(ad.getUserSubModules(groupName, groupName,userRightsForm.getModuleName()));
		}
		
		request.setAttribute("MenuIcon", request.getParameter("MenuIcon2"));
		userRightsForm.setModules(ad.getUserMainModules(groupName, userRightsForm.getUserName()));
		
		return mapping.findForward("display");
	}
	
	/*public ActionForward addSubUserRight1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		UserRightsForm userRightsForm=(UserRightsForm)form;
		
		String groupName=userRightsForm.getGroupName();
		
		UserDao ad=new UserDao();
		
		String[] mainLinks=request.getParameterValues("selectedMainModulesArr");
		
		
		String checkValues=request.getParameter("cValues");
		
		String uncheckValues=request.getParameter("unValues");
		
		String userMainLinks="";
		String inSubLinks="";
		
		
		String inLinks="";
		
		
		String sql12="select include_links,incsublinks,incsubsublinks from users where " +
				"username='"+userRightsForm.getUserName()+"'";
		
		try{
			ResultSet rs=ad.selectQuery(sql12);
			while (rs.next()) {
				inLinks=rs.getString("include_links");
				inSubLinks=rs.getString("incsublinks")+",";
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		if(userRightsForm.getModuleName().equalsIgnoreCase("Main"))
		{
		for(int i=1;i<mainLinks.length;i++){
			System.out.println("Getting a User Link is "+mainLinks[i]);
			userMainLinks+=mainLinks[i]+",";
		}
		
		
		inLinks+=","+userMainLinks.substring(0, userMainLinks.lastIndexOf(","));
		String[] array = new HashSet<String>(Arrays.asList(inLinks.split(","))).toArray(new String[0]);
		
		List<String> list = new ArrayList<String>(array.length);  
		for(String s : array) {  
		    list.add(s);  
		}  
		
		
		String[] a1=uncheckValues.split(",");
		
		List<String> list1 = new ArrayList<String>(a1.length);  
		for (String s : a1) {  
		    list1.add(s);  
		}  
	    
		list.removeAll(list1);
		
		String inkLinks="";
		
		for(String s : list) {  
			inkLinks+=s+",";
		}  
		
		
		String sql="update users set include_links='"+inkLinks.substring(0, inkLinks.lastIndexOf(","))+"'" +
				" where username='"+userRightsForm.getUserName()+"'";
		
		int a= ad.SqlExecuteUpdate(sql);
		
		if(a>0){
			userRightsForm.setMessage("User Details Modified Successfully");
		}
		}else{
			
			String[] subLinks=request.getParameterValues("selectedSubSubModulesArr");
			
			String userSubLinks="";
			
			
			System.out.println("Getting a subLinks is ************"+subLinks);
			
			
			if(subLinks!=null){
			
			for(int i=1;i<subLinks.length;i++){
				userSubLinks+=subLinks[i]+",";
			}
			
			}
			
			System.out.println("inSubLinks+userSubLinks.split"+(inSubLinks+userSubLinks.split(",")));
			
			String inclLinks="";
			
			
			String inSubLink="";
			
			if(inSubLinks.equalsIgnoreCase("") || inSubLinks.equalsIgnoreCase(",")){
				inSubLink=checkValues;
			}else{
				inSubLink=inSubLinks+checkValues;
			}
			
			String inc=inSubLink.substring(0, inSubLink.lastIndexOf(","));
			
			String[] array = new HashSet<String>(Arrays.asList(inc.split(","))).toArray(new String[0]);
			
			for(int i=0;i<array.length;i++){
				inclLinks+=array[i]+",";
			}
			
			
			
			List<String> list = new ArrayList<String>(array.length);  
			for (String s : array) {  
				System.out.println("Getting a S is ***********"+s);
			    list.add(s);  
			}  
			
			
			String[] a1=uncheckValues.split(",");
			
			List<String> list1 = new ArrayList<String>(a1.length);  
			for (String s : a1) {  
				
				System.out.println("Getting a S is ***********"+s);
			    list1.add(s);  
			}  
		    
			list.removeAll(list1);
			
			String inkLinks="";
			
			for(String s : list) {  
				inkLinks+=s+",";
			}  
			
			
			String sql="update users set incsubsublinks='"+inkLinks.substring(0, inkLinks.lastIndexOf(","))+"'" +
			" where username='"+userRightsForm.getUserName()+"'";
			
			int a= ad.SqlExecuteUpdate(sql);
		sql="update users set incsublinks='"+inkLinks.substring(0, inkLinks.lastIndexOf(","))+"'" +
			" where username='"+userRightsForm.getUserName()+"'";
			
			int a11= ad.SqlExecuteUpdate(sql);
			
			if(a>0){
				userRightsForm.setMessage("User Details Modified Successfully");
			}
			
			userRightsForm.setSubModules(ad.getUserSubModules(groupName, userRightsForm.getUserName(),
					userRightsForm.getModuleName()));
		}
		
		request.setAttribute("MenuIcon", request.getParameter("MenuIcon2"));
		userRightsForm.setModules(ad.getUserMainModules(groupName, userRightsForm.getUserName()));
		
		return mapping.findForward("display");
	}*/
	
	public ActionForward userRightSubModule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		UserRightsForm userRightsForm=(UserRightsForm)form;
		
		UserDao ad=new UserDao();
		
		String groupName=userRightsForm.getGrouplistid();
		String uName = userRightsForm.getUserName();
		String moduleName = userRightsForm.getModuleName();
		if(uName == null){
			uName = request.getParameter("uName");
		}
		
		String sql="select group_name from user_group where " +
				"id='"+uName+"'";
		
		ResultSet rs=ad.selectQuery(sql);
		try 
		{
			while(rs.next()) {
				groupName=rs.getString("group_name");
			}
			rs=ad.selectQuery("select * from links where id = "+request.getParameter("mId"));
			while(rs.next()) {
				moduleName=rs.getString("link_name");
			}
		
		userRightsForm.setSelectedSubSubModules(ad.getSelectedSubSubModules(groupName, uName,moduleName));
		userRightsForm.setSubModules(ad.getUserSubModulesDrpDown(groupName, uName,moduleName));
		userRightsForm.setSubSubLinks(ad.getUserSubSubModules(groupName, uName,moduleName));
		request.setAttribute("subLinks", ad.getUserSubSubModules(groupName, uName,moduleName));
		userRightsForm.setModules(ad.getUserMainModules(groupName, uName));
		
		
		
		
		
	 	
	/*	ArrayList<IdValuePair> modules = new ArrayList<IdValuePair>();

				
					 modules.add(new IdValuePair(1, userRightsForm.getSubModuleName()));
					 userRightsForm.setSubModules(modules);	
		
		String getSubSubModules="select id,link_name from links where  sub_linkname='"+userRightsForm.getSubModuleName()+"' and status=1 and link_name!=sub_linkname" +
				" order by id";
		ResultSet rsSubSubModules=ad.selectQuery(getSubSubModules);
		ArrayList<IdValuePair> modules1 = new ArrayList<IdValuePair>();
		while(rsSubSubModules.next())
		{
			 modules1.add(new IdValuePair(rsSubSubModules.getInt(1), rsSubSubModules.getString(2)));
		}
		userRightsForm.setSubSubLinks(modules1);
		
		
		
		request.setAttribute("MenuIcon", request.getParameter("MenuIcon2"));
		request.setAttribute("subSubLinks", "subSubLinks");
		request.setAttribute("subModules1", "subModules1");*/
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapping.findForward("searchSubLink");
	}
	
	public ActionForward searchGivenUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		UserRightsForm userRightsForm=(UserRightsForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		String sTxt = request.getParameter("searchText");
		userRightsForm.setSearchText(sTxt);
		MailInboxAction mAction = new MailInboxAction();
		LinkedList searchList=new LinkedList();
		try{
			String searchQuery="select * from emp_master where emp_name like '%"+sTxt+"%' or emp_email like '%"+sTxt+"%' or emp_id like'%"+sTxt+"%'";
			ResultSet srchRS=ad.selectQuery(searchQuery);
			int count = 0;
			String empmail= "";
			while(srchRS.next()) {
				MailInboxForm mForm = new MailInboxForm();
				empmail = srchRS.getString("emp_id");
				mForm.setSearchAddress(empmail);
				searchList.add(mForm);
				count++;
				if(count == mAction.MAIL_PER_PAGE)
				{
					break;
				}
			}
			if(count == 0){
				searchQuery="select * from emp_master where emp_designation like'%"+sTxt+"%'";
				srchRS=ad.selectQuery(searchQuery);
				count = 0;
				String empdes= "";
				while(srchRS.next()) {
					MailInboxForm mForm = new MailInboxForm();
					empdes = srchRS.getString("emp_designation");
					mForm.setSearchAddress(empdes);
					searchList.add(mForm);
					break;
				}
			}
		}
		catch (SQLException sqle) { System.out.println("SQLException @ searching User Details"); sqle.printStackTrace();}
		//displayComposeMail(mapping, form, request, response);
		request.setAttribute("SearchUserDetails", searchList);
		return mapping.findForward("searchUser");
	}
	
}
