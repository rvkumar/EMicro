package com.microlabs.forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import com.microlabs.admin.form.UserForm;
import com.microlabs.admin.form.UserReportsForm;
import com.microlabs.admin.form.UserRightsForm;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.login.dao.LoginDao;
import com.microlabs.login.form.LoginForm;
import com.microlabs.utilities.IdValuePair;
import com.microlabs.utilities.UserInfo;

public class ForumDao {
	
	
	ResultSet rs=null;
	Statement st=null;
	Connection conn=null;
	
	int a=0;
	public ForumDao() {
		try {
				
			if(conn != null && !(conn.isClosed())) {
				
			} else {
				conn=ConnectionFactory.getConnection();
			}
			
			if(st != null){
				System.out.println("Connection Statement Already Opened ");
			}else{
				st = conn.createStatement(); 
			}
			
			System.out.println("CONNECTION==============> "+conn);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeStatement(){
		try{
			System.out.println("now closing statement -------------");
			st.close();
			st=null;
		}catch(Exception e){}
	}
	
	public void closeResultset(){
		try{
			System.out.println("now closing statement -------------");
			rs.close();
			rs=null;
		}catch(Exception e){}
	}
	
	public void connClose() {
		try {
			if(!conn.isClosed())
				conn.close();
			closeStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int SqlExecuteUpdate(String sql){
		  
	    int b=0;
	    
	    try
		{
		Statement st=conn.createStatement();
		b=st.executeUpdate(sql);
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		return b;
	}
	
	
	
	
	public ResultSet selectQuery(String sql) {
	  		
		ResultSet rs=null;
	    	try
	    	{
	    		
	    		Connection conn=ConnectionFactory.getConnection();
	    		Statement st=conn.createStatement();
	    		rs=st.executeQuery(sql);
	    	}catch(SQLException se){
	    		se.printStackTrace();
	    	}
	    	return rs;
	    }
		
		
public ArrayList<UserReportsForm> searchBackupUsers(UserForm form){
		
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		form.setMessage("search User failed..");
		String query=" select u.fullname,u.username,b.user_id,b.status " +
					" from users u, backup_user b" +
					" where u.id = b.user_id " +
					" order by u.fullname";
		System.out.println("query--"+query); 
		ArrayList<UserReportsForm> userList=null;
		if(conn!=null){
			try {
				int i=0;
				 st=conn.createStatement();
				rs= st.executeQuery(query);
				 userList = new ArrayList<UserReportsForm>();
				 
				 	while(rs.next()){
					 UserReportsForm user = new UserReportsForm();
					
					 user.setUserName(rs.getString("username"));
					 user.setFName(rs.getString("fullname"));
					 user.setEditId(rs.getInt("user_id"));
					 user.setHits(rs.getInt("status"));
					 userList.add(user);
					 i++;
				 	}
				 form.setMessage("Search found "+i+" users.");
				 
			} catch (SQLException e) {
				System.out.println("SQL exception listing backup user \n query:"+query+"\n"+e.getMessage());
				e.printStackTrace();

			}
			finally{
				//ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		return userList;
	}
		
		public ArrayList<String> getAllCentersId(){
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
			ArrayList<String> cntrIds=null;
			String query="select cntr_id from center_m where status=1";
			if(conn!=null){
				try{
					conn=ConnectionFactory.getConnection();
					st=conn.createStatement();
					rs=st.executeQuery(query);
					cntrIds = new ArrayList<String>();
					while(rs.next()){
						cntrIds.add(""+rs.getInt(1));
					}
				}
			catch(SQLException e){
			}
			finally{
				//ConnectionFactory.closeResources(conn, st, rs);
			}
		}
			
			return cntrIds;
		}
		
		
	public void updateStatus(String editId,String status, UserForm form){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		form.setMessage("Error in changing user status...");
		String updateQuery=""; 
			if(status.equalsIgnoreCase("1"))
				updateQuery="UPDATE  backup_user set status=0 where user_id="+editId+"";
			else if(status.equalsIgnoreCase("0"))
				updateQuery="UPDATE  backup_user set status=1 where user_id="+editId+"";
		 try
			{
			 System.out.println("Query..."+updateQuery); 
			 st=conn.createStatement(ResultSet.CONCUR_READ_ONLY,ResultSet.CONCUR_UPDATABLE);
			 st.executeUpdate(updateQuery);
			 form.setMessage("User status changed sucessfully...");
			}catch(SQLException e){
				System.out.println("error in updating User status..."); 
					e.printStackTrace();

			}finally{
				//ConnectionFactory.closeResources(conn, st, rs);
			}
	}		
		

	
		
		
		
		public ArrayList<String> getSelectedModules(String group,String userName
				,String moduleName){
			System.out.println("entered getUserModules method---------"); 
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
            LoginDao loginDao=new LoginDao();
			
			ArrayList<String> modules = null;
			String incExcQuery="";
			
			String incsublinks="";
			
			String query1="select include_links from" +
			" users where username='"+userName+"' and activated='On'";
			
			
			ResultSet rs2= loginDao.selectQuery(query1);
			
			try{
			
			while (rs2.next()) {
				incsublinks=rs2.getString("include_links");
			}
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			String query="select id from links where id in ("+incsublinks+")";
		 	
		 	
		 System.out.println("query--"+query); 
				
		 if(conn!=null){
			 
			 
				try {
					 st=conn.createStatement();
					 rs=st.executeQuery(query);
					 modules=new ArrayList<String>();
					 while(rs.next()){
						 modules.add(rs.getString("id"));
					 }
				} catch (SQLException e) {
					System.out.println("SQL exception fetching Sub modules\n query:"+query); 
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
					}
				}
		
			return modules;
		}
		
		
		
		public ArrayList<String> getSelectedSubSubModules(String group,String userName
				,String moduleName){
			System.out.println("entered getUserModules method---------"); 
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
            LoginDao loginDao=new LoginDao();
			
			ArrayList<String> modules = null;
			String incExcQuery="";
			modules=new ArrayList<String>();
			String incsublinks="";
			
			String query1="select incsubsublinks from" +
			" users where username='"+userName+"' and activated='On'";
			
			
			ResultSet rs2= loginDao.selectQuery(query1);
			
			try{
			
			while (rs2.next()) {
				incsublinks=rs2.getString("incsubsublinks");
			}
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			
			if(incsublinks.equalsIgnoreCase("")){
				modules.add("0");
			}else{
			
			
			String query="select id from links where id in ("+incsublinks+")";
		 	
		 	
		 System.out.println("query--"+query); 
				
		 if(conn!=null){
			 
			 
				try {
					 st=conn.createStatement();
					 rs=st.executeQuery(query);
					 
					 while(rs.next()){
						 modules.add(rs.getString("id"));
					 }
				} catch (SQLException e) {
					System.out.println("SQL exception fetching Sub modules\n query:"+query); 
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
					}
				}
			}
			
			
			return modules;
		}
		
		
		public ArrayList<String> getSelectedSubModules(String group,String userName
				,String moduleName){
			System.out.println("entered getUserModules method---------"); 
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
            LoginDao loginDao=new LoginDao();
			
			ArrayList<String> modules = null;
			String incExcQuery="";
			
			String incsublinks="";
			
			String query1="select incsublinks from" +
			" users where username='"+userName+"' and activated='On'";
			
			
			ResultSet rs2= loginDao.selectQuery(query1);
			
			try{
			
			while (rs2.next()) {
				incsublinks=rs2.getString("incsublinks");
			}
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			String query="select id from links where id in ("+incsublinks+")";
		 	
		 	
		 System.out.println("query--"+query); 
		 		
		 if(conn!=null){
			 
			 
				try {
					 st=conn.createStatement();
					 rs=st.executeQuery(query);
					 modules=new ArrayList<String>();
					 while(rs.next()){
						 modules.add(rs.getString("id"));
					 }
				} catch (SQLException e) {
					System.out.println("SQL exception fetching Sub modules\n query:"+query); 
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
				}
				}
		 	
			return modules;
		}
		
		public ArrayList<IdValuePair> getUserSubModules(String group,String userName
				,String moduleName){
			System.out.println("entered getUserModules method---------"); 
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
            LoginDao loginDao=new LoginDao();
			
			ArrayList<IdValuePair> modules = null;
			String incExcQuery="";
			
			
			/*String query="select id,link_name from links where link_name in (select sub_modulename from" +
			" user_group where group_name='"+group+"' and module_name='"+moduleName+"' and " +
			"sub_modulename is not null and sub_linkname is null) and sub_linkname is" +
			" null and status=1";*/
			
			String query="select id,link_name from links where module='"+moduleName+"' and " +
			"status=1 and sub_linkname=link_name";
			
		 System.out.println("query--"+query); 
				
		 if(conn!=null){
			 
			 
				try {
					 st=conn.createStatement();
					 rs=st.executeQuery(query);
					 modules=new ArrayList<IdValuePair>();
					 while(rs.next()){
						 modules.add(new IdValuePair(rs.getInt(1), rs.getString(2)));
					 }
				} catch (SQLException e) {
					System.out.println("SQL exception fetching Sub modules\n query:"+query); 
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
					}
				}
		 	
			return modules;
		}
		
		
		public ArrayList<IdValuePair> getUserModules(String group,String userName){
			System.out.println("entered getUserModules method---------"); 
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
            LoginDao loginDao=new LoginDao();
			
			ArrayList<IdValuePair> modules = null;
			String incExcQuery="";
			
			
			String query="select id,link_name from links where link_name in (select module_name from" +
			" user_group where group_name='"+group+"' and  " +
			"sub_modulename is null and sub_linkname is null) and sub_linkname is" +
			" null and status is null";
		 	
		 	
		 	
		 	
		 System.out.println("query--"+query); 
		 		
		 if(conn!=null){
			 
			 
				try {
					 st=conn.createStatement();
					 rs=st.executeQuery(query);
					 modules=new ArrayList<IdValuePair>();
					 while(rs.next()){
						 modules.add(new IdValuePair(rs.getInt(1), rs.getString(2)));
					 }
				} catch (SQLException e) {
					System.out.println("SQL exception fetching Sub modules\n query:"+query); 
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
					}
				}
		
			return modules;
		}
		
		
		public void addIncludeExcludeLinks(UserRightsForm form,ArrayList<String> incLinks,ArrayList<String> excLinks){
			ResultSet rs=null;
			PreparedStatement ps=null;
			Connection conn=ConnectionFactory.getConnection();
			String inc="";
			String exc="";
			
			for(String a:incLinks)
				inc+=a+",";
			if(!inc.equals("")) inc=inc.substring(0,inc.length()-1);
			
			for(String a:excLinks)
				exc+=a+",";
			if(!exc.equals("")) exc=exc.substring(0,exc.length()-1);
			
			 form.setMessage("Adding rights failed..");
			 if(conn!=null){
				 String query="update users set include_links='"+inc+"', exclude_links='"+exc+"' where username like '"+form.getUserName()+"';";
					try {
						ps=conn.prepareStatement(query);
						ps.executeUpdate();
						form.setMessage("Added rights successfully..");
					} catch (SQLException e) {
						System.out.println("SQL exception addIncludeExcludeLinks query:"+query); 
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
					finally{
						ConnectionFactory.closeResources(conn, ps, rs);
						}
					}
			 
		}
		
		
		public ArrayList<IdValuePair> getLinksIDvaluePair(String module){
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
			ArrayList<IdValuePair> links = null;
			
			
			
			
		 String query="select id,link_name from links where module " +
		 		"like '"+module+"' order by priority;";
		 System.out.println("query--"+query); 
				
			if(conn!=null){
				try {
					 st=conn.createStatement();
					 rs=st.executeQuery(query);
					 links=new ArrayList<IdValuePair>();
					 while(rs.next()){
						 links.add(new IdValuePair(rs.getInt(1), rs.getString(2)));
					 }
				} catch (SQLException e) {
					System.out.println("SQL exception fetching Sub modules\n query:"+query); 
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
					}
				}
			System.out.println(links.size());
			 return links;
		}
		
		
		public ArrayList<IdValuePair> getUserMainModules(String group,String userName){
			ResultSet rs=null;
			Statement st=null;
			
			LoginDao ad=new LoginDao();
			
			String mainLinks="";
			
			String sql1="select include_links from users where username='"+userName+"'";
			
			ResultSet rs1=ad.selectQuery(sql1);
			
			try{
			while (rs1.next()) {
				mainLinks=rs1.getString("include_links");
			}
			
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			
			Connection conn=ConnectionFactory.getConnection();
			ArrayList<IdValuePair> modules = null;
		 	
			String query="";
			
			
			query="select id,link_name from links where id in("+mainLinks+") " +
					" and sub_linkname is null order by id;";
		 	
		 
		 System.out.println("query--"+query);
		 		
		 if(conn!=null){
				try {
					 st=conn.createStatement();
					 rs=st.executeQuery(query);
					 modules=new ArrayList<IdValuePair>();
					 while(rs.next()){
						 modules.add(new IdValuePair(rs.getInt(1), rs.getString(2)));
					 }
				} catch (SQLException e) {
					System.out.println("SQL exception fetching Sub modules\n query:"+query); 
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
					}
				}
		 	 
			 System.out.println("modules selected="+modules.size()); 
			 return modules;
		}
		
		
		
		public ArrayList<IdValuePair> getUserSubSubModules(String group,String userName,String subModuleName){
			ResultSet rs=null;
			Statement st=null;
			
			LoginDao ad=new LoginDao();
			
			String subLinks="";
			
			String sql1="select incsublinks from users where username='"+userName+"'";
			
			ResultSet rs1=ad.selectQuery(sql1);
			
			try{
			while (rs1.next()) {
				subLinks=rs1.getString("incsublinks");
			}
			
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			
			Connection conn=ConnectionFactory.getConnection();
			ArrayList<IdValuePair> modules = null;
		 	
			String query="";
			
			
			query="select id,link_name from links where " +
					" sub_linkname='"+subModuleName+"' and status=1 order by id;";
		 	
		 
		    System.out.println("query--"+query);
		 		
		 if(conn!=null){
				try {
					 st=conn.createStatement();
					 rs=st.executeQuery(query);
					 modules=new ArrayList<IdValuePair>();
					 while(rs.next()){
						 modules.add(new IdValuePair(rs.getInt(1), rs.getString(2)));
					 }
				} catch (SQLException e) {
					System.out.println("SQL exception fetching Sub modules\n query:"+query); 
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
					}
				}
		 	 
			 System.out.println("modules selected="+modules.size()); 
			 return modules;
		}
		
		
	
		
		
		public ArrayList<IdValuePair> getUserSubModulesDrpDown(String group,String userName,
				String moduleName){
			ResultSet rs=null;
			Statement st=null;
			
			LoginDao ad=new LoginDao();
			
			String subLinks="";
			
			String sql1="select incsublinks from users where username='"+userName+"'";
			
			ResultSet rs1=ad.selectQuery(sql1);
			
			try{
			while (rs1.next()) {
				subLinks=rs1.getString("incsublinks");
			}
			
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			
			Connection conn=ConnectionFactory.getConnection();
			ArrayList<IdValuePair> modules = null;
		 	
			String query="";
			
			
			query="select id,link_name from links where id in("+subLinks+") " +
					" and sub_linkname is null and module='"+moduleName+"' and status=1 order by id;";
		 	
		 
		 System.out.println("query--"+query);
		 		
		 if(conn!=null){
				try {
					 st=conn.createStatement();
					 rs=st.executeQuery(query);
					 modules=new ArrayList<IdValuePair>();
					 while(rs.next()){
						 modules.add(new IdValuePair(rs.getInt(1), rs.getString(2)));
					 }
				} catch (SQLException e) {
					System.out.println("SQL exception fetching Sub modules\n query:"+query); 
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
					}
				}
		 	 
			 System.out.println("modules selected="+modules.size()); 
			 return modules;
		}
		
		
		public ArrayList<IdValuePair> getModules(String group,String gro){
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
			ArrayList<IdValuePair> modules = null;
		 	
			String query="";
			
			
			query="select id,module_name from user_group where group_name " +
					"like '"+group+"' and status=1 and sub_modulename is null and " +
							"sub_linkname is null order by id;";
		 	
		 
		 System.out.println("query--"+query);
		 		
		 if(conn!=null){
				try {
					 st=conn.createStatement();
					 rs=st.executeQuery(query);
					 modules=new ArrayList<IdValuePair>();
					 while(rs.next()){
						 modules.add(new IdValuePair(rs.getInt(1), rs.getString(2)));
					 }
				} catch (SQLException e) {
					System.out.println("SQL exception fetching Sub modules\n query:"+query); 
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
					}
				}
		 	 
			 System.out.println("modules selected="+modules.size()); 
			 return modules;
		}
		
		
		
		public UserInfo validate(LoginForm form){
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
			UserInfo bean=null;
			
			String userName=form.getUserName();
			String password=form.getPassword();
			String userType=form.getLoginType();
			userType="temp";
			
			String query="select * from users where username='"+userName+"' and password='"+password+"' and usr_type='"+userType+"' and count=0 and Activated='On'";
			if(conn!=null){
				try{
					conn=ConnectionFactory.getConnection();
					st=conn.createStatement();
					rs=st.executeQuery(query);
					if(rs.next()){
						bean=new UserInfo();
						
						String incLink=rs.getString("include_links");
						String excLink=rs.getString("exclude_links");
						//String cntrId=rs.getString("center_id");
						
						String userId=rs.getString("id");
						
						
						ArrayList<String> includeLinks=new ArrayList<String>();
						ArrayList<String> centerIds=new ArrayList<String>();
						ArrayList<String> excludeLinks=new ArrayList<String>();
						
						
						if(incLink!=null && !incLink.equals(""))
							Collections.addAll(includeLinks, incLink.split(","));
						if(excLink!=null && !excLink.equals(""))
							Collections.addAll(excludeLinks, excLink.split(","));

						//Collections.addAll(centerIds, rs.getString("center_id").split(","));
						//Collections.addAll(includeLinks, rs.getString("include_links").split(","));
						//Collections.addAll(excludeLinks, rs.getString("exclude_links").split(","));
						
						
						//bean.set
						
						bean.setId(rs.getInt("id"));
						bean.setUserName(rs.getString("username"));
						bean.setUserType(rs.getString("usr_type"));
						
						bean.setFullName(rs.getString("fullname"));
						bean.setShortName(rs.getString("shortname"));
						bean.setS_ID(rs.getString("S_ID"));
						bean.setGroupName(rs.getString("groupname"));
						bean.setMail_id(rs.getString("mail_id"));
						bean.setGroupId(rs.getInt("group_id"));
						bean.setPerson(rs.getString("Person"));
						bean.setCenterIds(centerIds);
						bean.setIncludeLinks(includeLinks);
						bean.setExcludeLinks(excludeLinks);
						}
					else{
						form.setMessage("User Name Password Doesnot matches");
					}

					}
				catch(SQLException e){
					//form.setMessage(e.getMessage());
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
				}
			}
			if(bean==null) System.out.println("invalid entry"); 
			return bean;
		}

		public UserInfo validateAdminUser(String userName,String password){
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
			UserInfo bean=null;
		
			String query="select * from users where username='"+userName+"' and password='"+password+"' and person='admin' and count=0 and Activated='On'";
			if(conn!=null){
				try{
					conn=ConnectionFactory.getConnection();
					st=conn.createStatement();
					rs=st.executeQuery(query);
					if(rs.next()){
						bean=new UserInfo();
						
						bean.setId(rs.getInt("id"));
						bean.setUserName(rs.getString("username"));
						bean.setFullName(rs.getString("fullname"));
						bean.setShortName(rs.getString("shortname"));
						bean.setS_ID(rs.getString("S_ID"));
						bean.setGroupName(rs.getString("groupname"));
						bean.setMail_id(rs.getString("mail_id"));
						bean.setGroupId(rs.getInt("group_id"));
						bean.setPerson(rs.getString("Person"));
						}
					}
				catch(SQLException e){
					
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
				}
			}
			if(bean==null) System.out.println("invalid entry"); 
			return bean;
		}

		
		private String getLinkId(String link_path,String method){
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
			String id="";
		
			String query="Select id from links where link_path='"+link_path+"' and (method='"+method+"' or added_methods like '%,"+method+",%')";
			if(conn!=null){
				try{
					conn=ConnectionFactory.getConnection();
					st=conn.createStatement();
					rs=st.executeQuery(query);
					if(rs.next()){
						id=""+rs.getInt("id");
						}
					}
				catch(SQLException e){
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
				}
			}
			
			return id;
		}
		public ArrayList<String> getGroupAllIds(String groupName){
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
			String id="0";
			ArrayList<String> idList=null;
			
			String query="Select links from user_group where group_name='"+groupName+"' and status=1";
			String query1="Select id from links where " +
					"link_name in(SELECT module_name FROM user_group where group_name='"+groupName+"' and status=1);";
			if(conn!=null){
				try{
					conn=ConnectionFactory.getConnection();
					st=conn.createStatement();
					rs=st.executeQuery(query);
					idList=new ArrayList<String>();
					
					while(rs.next()){
						String v=rs.getString("links");
						if(!v.equals("")) 
							id+=","+v;
						}
					rs=st.executeQuery(query1);
					while(rs.next()){
						String v=""+rs.getInt("id");
						if(!v.equals("")) 
							id+=","+v;
						}
					Collections.addAll(idList, id.split(","));
					}
				catch(SQLException e){
					
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
				}
			}
			return idList;
		}
		public boolean validateUrl(String groupName,ArrayList<String> includeLinks, ArrayList<String> excludeLinks,String url,String method)
		{
			String link_path="";
			String split[]=url.split("\\?");
			String split1[]=split[0].split("/");
			if(split1.length>=0) 	link_path=split1[split1.length-1];
			
			if(link_path.equalsIgnoreCase("home.do") || link_path.equalsIgnoreCase("dashBoard.do")|| link_path.equalsIgnoreCase("appInfo.do"))
				return true;
			
			String id=getLinkId(link_path, method);
			ArrayList<String> al=getGroupAllIds(groupName);
			
			if(includeLinks.contains(id)  || al.contains(id) && !excludeLinks.contains(id))
				return true;
			
			return false;
		}
		
		private IdValuePair<String, String> getLinkNameModule(String link_path,String method){
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
			IdValuePair<String, String> pair=null;
		
			String query="Select link_name,module from links where link_path='"+link_path+"' and method='"+method+"'";
			if(conn!=null){
				try{
					//conn=ConnectionFactory.getConnection();
					st=conn.createStatement();
					rs=st.executeQuery(query);
					if(rs.next()){
						pair=new IdValuePair<String, String>(rs.getString("link_name"),rs.getString("module"));
						}
					}
				catch(SQLException e){
					
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
				}
			}

			return pair;

		}
		
		
		
		public boolean isValidUserName(String userName,int userGroupId){
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
		
			String query="Select count(username) from users where username='"+userName+"' and group_id > "+userGroupId+" ";
			int count=0;
			if(conn!=null){
				try{
					conn=ConnectionFactory.getConnection();
					st=conn.createStatement();
					rs=st.executeQuery(query);
					if(rs.next())
						count=rs.getInt(1);
					}
				catch(SQLException e){
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
				}
			}
			if(count==1) return true;
			
			return false;
		}
		
		public String usersGroupName(String userName){
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
		
			String query="Select groupname from users where username='"+userName+"' ";
			String groupName="";
			if(conn!=null){
				try{
					conn=ConnectionFactory.getConnection();
					st=conn.createStatement();
					rs=st.executeQuery(query);
					if(rs.next())
						groupName=rs.getString("groupname");
					}
				catch(SQLException e){
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
				}
			}
			
			return groupName;
		}
		
		public ArrayList<String> usersIncludeLinks(String userName){
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
		
			String query="Select include_links from users where username='"+userName+"' ";
			ArrayList<String> includeLinks=new ArrayList<String>();
			if(conn!=null){
				try{
					conn=ConnectionFactory.getConnection();
					st=conn.createStatement();
					rs=st.executeQuery(query);
					
					String incLink="";
					if(rs.next())
						incLink=rs.getString("include_links");
					if(incLink.split(",").length>0)
						Collections.addAll(includeLinks, incLink.split(","));
					}
				catch(SQLException e){
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
				}
			}
			
			return includeLinks;
		}
		
		public ArrayList<String> usersExcludeLinks(String userName){
			ResultSet rs=null;
			Statement st=null;
			Connection conn=ConnectionFactory.getConnection();
		
			String query="Select exclude_links from users where username='"+userName+"' ";
			ArrayList<String> excludeLinks=new ArrayList<String>();
			if(conn!=null){
				try{
					conn=ConnectionFactory.getConnection();
					st=conn.createStatement();
					rs=st.executeQuery(query);
					
					String excLink="";
					if(rs.next())
						excLink=rs.getString("exclude_links");
					if(excLink.split(",").length>0)
						Collections.addAll(excludeLinks, excLink.split(","));
					}
				catch(SQLException e){
					e.printStackTrace();
				}
				finally{
					//ConnectionFactory.closeResources(conn, st, rs);
				}
			}
			
			return excludeLinks;
		}
}
