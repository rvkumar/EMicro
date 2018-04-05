package com.microlabs.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import com.microlabs.admin.form.ModifyUserGroupForm;
import com.microlabs.admin.form.UserGroupForm;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.login.dao.LoginDao;
import com.microlabs.utilities.IdValuePair;

public class GroupDao {
	
	
	ResultSet rs=null;
	Statement st=null;
	Connection conn=null;
	
	int a=0;
	public GroupDao() {
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
  		
    	try
    	{
    		Statement st=conn.createStatement();
    		rs=st.executeQuery(sql);
    	}catch(SQLException se){
    		se.printStackTrace();
    	}
    	return rs;
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
	 	
	 
	 System.out.println("query in getModules--"+query);
	 		
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
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
	 	 
		 System.out.println("modules selected="+modules.size()); 
		 return modules;
	}
	
	
	public ArrayList<IdValuePair> getSubModules(String group,String module){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		ArrayList<IdValuePair> modules = null;
	 	
		String query="";
		
		
		query="select id,sub_modulename from user_group where group_name " +
				"like '"+group+"' and module_name like '"+module+"' and status=1 and sub_modulename is" +
						" not null and sub_linkname is null order by id;";
	 	
	 
	 System.out.println("query in getSubModules******--"+query);
	 		
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
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
	 	
		 System.out.println("modules selected="+modules.size()); 
		 
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
		
		ArrayList<String> include=loginDao.usersIncludeLinks(userName);
		ArrayList<String> exclude=loginDao.usersExcludeLinks(userName);
		String inc="";
		String exc="";
		
		for(String s:include)
			inc+=s+",";
		if(!inc.equals(""))
			inc=inc.substring(0,inc.length()-1);
		for(String s:exclude)
			exc+=s+",";
		if(!exc.equals(""))
			exc=exc.substring(0,exc.length()-1);
		
		if(!inc.equals("") || !exc.equals("")){
			if(!inc.equals(""))
				inc="and id in("+inc+")";
			if(!exc.equals(""))
				exc="and id not in("+exc+")";
			incExcQuery=" module_name in (select link_name from links where module like 'Main' "+inc+" "+exc+")";
		}
		else{
			incExcQuery="status=1";
		}
		//String query="select id,module_name from user_group where group_name='"+group+"' and status=1;";
		String query="select id,module_name from user_group where group_name like '"+group+"' and ("+incExcQuery+") order by module_name;";
		
		if(!inc.equals("") || !exc.equals(""))
		 query="select id,link_name from links where module like 'Main' "+exc+"";

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
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		 System.out.println("modules selected="+modules.size()); 
		 return modules;
	}
	
	public ArrayList<ArrayList<String>> getLinks(String module){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		ArrayList<ArrayList<String>> links = null;
	 	
		String query="select id,link_name from links where module like '"+module+"';";
		System.out.println("query--"+query); 
			
		if(conn!=null){
			try {
				 st=conn.createStatement();
				 rs=st.executeQuery(query);
				 ArrayList<String> id=new ArrayList<String>();
				 ArrayList<String> value=new ArrayList<String>();
				 
				 while(rs.next()){
					 id.add(""+rs.getInt(1));
					 value.add( rs.getString(2));
				 }
				 links=new ArrayList<ArrayList<String>>();
				 links.add(id);
				 links.add(value);
			} catch (SQLException e) {
				System.out.println("SQL exception fetching Sub modules\n query:"+query); 
				e.printStackTrace();
			}
			finally{
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		 
		 System.out.println(links.size()); 
		 return links;
	}
	
	
	public ArrayList<IdValuePair> getSubSubLinksIDvaluePair(String module,String subModule){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		ArrayList<IdValuePair> links = null;
	 	
		String query="select id,link_name from links where " +
				"module like '"+module+"' and sub_linkname like '"+subModule+"'" +
				"order by id";
		
		System.out.println("query in getSubSubLinksIDvaluePair--"+query); 
			
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
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		 
		 System.out.println(links.size());
		 return links;
	}
	
	
	public ArrayList<IdValuePair> getSubSubLinksIDvaluePair(String module){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		ArrayList<IdValuePair> links = null;
	 	
		String query="select id,link_name from links where " +
				"module like '"+module+"' and sub_linkname is not null " +
				"order by id";
		
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
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		System.out.println(links.size());
		 return links;
	}
	
	
	public ArrayList<IdValuePair> getSubLinksIDvaluePair(String module){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		ArrayList<IdValuePair> links = null;
	 	
		String query="select id,link_name from links where " +
				"module like '"+module+"' and sub_linkname is null " +
				"order by id";
		//select id,link_name from links where module like 'News and Media' and sub_linkname like 'Holidays'order by id
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
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		System.out.println(links.size());
		 return links;
	}
	
	public ArrayList<IdValuePair> getLocationsIDvaluePair(){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		ArrayList<IdValuePair> locations = null;
		
		String getLocations="select LOCID," +
		"LOCNAME,location_code from location";
		if(conn!=null){
			try {
				 st=conn.createStatement();
				 rs=st.executeQuery(getLocations);
				 locations=new ArrayList<IdValuePair>();
				 while(rs.next()){
					 locations.add(new IdValuePair(rs.getInt(1), (rs.getString(3)+"-"+rs.getString(2))));
				 }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			finally{
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		 return locations;
	}
	
	
	public ArrayList<IdValuePair> getLinksIDvaluePair(String module){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		ArrayList<IdValuePair> links = null;
	 	
	 String query="select id,link_name from links where module like '"+module+"' " +
	 		"and sub_linkname is null" +
	 		" order by id";
	 
	 System.out.println("query in getLinksIDvaluePair main--"+query); 
			
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
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		System.out.println(links.size());
		 return links;
	}
	
	public ArrayList<IdValuePair<Integer, String>> getParFraIdValuePair(String CenterType){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		ArrayList<IdValuePair<Integer, String>> links = null;
		String table="";
		
		 if(CenterType.equals("fra"))
			 	table="franchise_det";
		 if(CenterType.equals("par"))
			 	table="parental_det";
		 
	 String query="select id,name from "+table+" where status=1;";
	 
	 System.out.println("query--"+query);
			
		if(conn!=null){
			try {
				 st=conn.createStatement();
				 rs=st.executeQuery(query);
				 links=new ArrayList<IdValuePair<Integer, String>>();
				 while(rs.next()){
					 links.add(new IdValuePair<Integer, String>(rs.getInt(1), rs.getString(2)));
				 }
				System.out.println("Selected "+links.size()); 
			} catch (SQLException e) {
				System.out.println("SQL exception fetching parantal or franchises list\n query:"+query);
				
				e.printStackTrace();
			}
			finally{
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		 return links;
	}
	public ArrayList<String> getGroups(int groupId){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		ArrayList<String> groups = null;
		
		String query="select group_id,group_name from user_group  group by group_id,group_name order by group_id";
		System.out.println("query in getGroups--"+query); 
			
		if(conn!=null){
			try {
				 st=conn.createStatement();
				 rs=st.executeQuery(query);
				 groups=new ArrayList<String>();
				 
				 while(rs.next())
					 groups.add( rs.getString(2));
			} catch (SQLException e) {
				System.out.println("SQL exception fetching Groups \n query:"+query); 
				e.printStackTrace();
			}
			finally{
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		 return groups;
	}
	
	public ArrayList<IdValuePair<Integer,String>> getGroupsIDValuePair(int userGroupId){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		ArrayList<IdValuePair<Integer,String>> groups = null;
		
		String query="Select distinct group_name,group_id from user_group where group_id > "+userGroupId+" group by id order by group_id;";
		System.out.println("query--"+query); 
			
		if(conn!=null){
			try {
				 st=conn.createStatement();
				 rs=st.executeQuery(query);
				 groups=new ArrayList<IdValuePair<Integer,String>>();
				 
				 while(rs.next())
					 groups.add( new IdValuePair<Integer,String>( rs.getInt(2),rs.getString(1)));
			} catch (SQLException e) {
				System.out.println("SQL exception fetching Groups id value pair \n query:"+query); 
				e.printStackTrace();
			}
			finally{
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		 return groups;
	}
	
	
	public boolean addGroup(UserGroupForm form){
		boolean res=false;
		ResultSet rs=null;
		PreparedStatement ps=null;
		Connection conn=ConnectionFactory.getConnection();
		 form.setMessage("Creating Group failed..");
		 String groupCount="";
		 boolean valid=false;
		 
		 if(conn!=null){
			// String countQuery="select count(distinct group_name) from user_group;";
			
			 String countQuery="select count(distinct group_name),(select count(*) from user_group where group_name like '"+form.getGroupName()+"')as valid from user_group;";
			 ResultSet rs1=null;
			 Statement st=null;
				try {
					 st=conn.createStatement();
					 rs1=st.executeQuery(countQuery);
					 if(rs1.next()){
						 groupCount=""+rs1.getInt(1);
						 if(rs1.getInt(2)==0) valid=true;
					 }
				} catch (SQLException e) {
					System.out.println("SQL exception creating Group \n query:"+countQuery+"\n"+e.getMessage()); 
					String[] a=e.getMessage().split(" ");
					if(a.length>1 && a[1].equalsIgnoreCase("Duplicate")){
						 form.setMessage("Group Name has already been assigned.");
					 }
					form.setGroupName(null);
					res= false;
					e.printStackTrace();
				}
				finally{
					ConnectionFactory.closeResources(conn, ps, rs);
					}
				}
		 
		conn=ConnectionFactory.getConnection();
		 ArrayList<String> selectedLinks=new ArrayList<String>();
		 Collections.addAll(selectedLinks,form.getSelectedLinksArr());
			 System.out.println("Selected links--"+selectedLinks); 
		if(!groupCount.equals("") && valid){
			for(IdValuePair modules:form.getLinks()){
				 int status=0;
				//System.out.println("checking--"+modules.getValue()+"--"+selectedLinks.contains(modules.getValue())); log.info("checking--"+modules.getValue()+"--"+selectedLinks.contains(modules.getValue()));
				 
				if(selectedLinks.contains(modules.getValue()))status=1;
				//TODO:add group id remaining
				 String query="insert into user_group(group_name,module_name,links,status,group_id)" +
				 		" values('"+form.getGroupName()+"','"+modules.getValue()+"','',"+status+","+groupCount+")";
				 System.out.println("query--"+query); 
			 form.setMessage("Creating Group failed..");
				if(conn!=null){
					try {
						 ps=conn.prepareStatement(query);
						 ps.executeUpdate();
						 res=true;
						 form.setMessage("Group Created Succesfully..");
					} catch (SQLException e) {
						System.out.println("SQL exception creating Group \n query:"+query+"\n"+e.getMessage()); 
						String[] a=e.getMessage().split(" ");
						if(a.length>1 && a[1].equalsIgnoreCase("Duplicate")){
							 form.setMessage("Group Name has already been assigned.");
						 }
						form.setGroupName(null);
						res= false;
						e.printStackTrace();
						break;
					}
					finally{
						ConnectionFactory.closeResources(conn, ps, rs);
						}
					}
			}
		}
		return res;
	}
	
	
	public void modifyUpdateLinks(ModifyUserGroupForm form){
		ResultSet rs=null;
		PreparedStatement ps=null;
		Connection conn=ConnectionFactory.getConnection();
		 form.setMessage("Modifying Groups Failed..");
		 
		 
		//System.out.println("checking--"+modules.getValue()+"--"+selectedLinks.contains(modules.getValue())); log.info("checking--"+modules.getValue()+"--"+selectedLinks.contains(modules.getValue()));
		if(form.getModuleName().equalsIgnoreCase("Main")){
				ArrayList<String> selectedLinks=new ArrayList<String>();
			 Collections.addAll(selectedLinks,form.getSelectedLinksArr());
			 System.out.println("selected links-----"+selectedLinks); 
			 String query="";
				 if(conn!=null){
						try {
							for(IdValuePair l:form.getLinks()){
								int status=0;
								if(selectedLinks.contains(""+l.getId()))status=1;
									
								 query="insert into user_group(group_id,group_name,module_name,links,status)" +
								 		" select distinct (group_id),'"+form.getGroupName()+"','"+l.getValue()+"','',"+status+""+
						 				" from user_group" +
						 				" where group_name='"+form.getGroupName()+"' " +
				 						" and not exists(select * from user_group  where group_name='"+form.getGroupName()+"' and module_name='"+l.getValue()+"'); ";

								 query+=" update user_group set status="+status+" where group_name like '"+form.getGroupName()+"' and module_name like '"+l.getValue()+"';";
								
								 System.out.println("query--"+query); 
								 ps=conn.prepareStatement(query);
								 ps.executeUpdate();
							}
							 form.setMessage("Group Modified Succesfully...");
						} catch (SQLException e) {
							System.out.println("SQL exception creating Group \n query:"+query); 
							e.printStackTrace();
						}
						finally{
							ConnectionFactory.closeResources(conn, ps, rs);
							}
						}
		}
		else{
			String links="";
			 for(String l:form.getSelectedLinksArr())
				 links+=l+",";
			 links=links.substring(0,links.length()-1);

		 String query="update user_group " +
		 				" set links='"+links+"' " +
		 				" where group_name like '"+form.getGroupName()+"' and module_name like '"+form.getModuleName()+"'";
		 System.out.println("query--"+query); 
		 form.setMessage("Links Updated sucessfully..");
		if(conn!=null){
			try {
				 ps=conn.prepareStatement(query);
				 ps.executeUpdate();
				 form.setMessage("Links Updated sucessfully..");
			} catch (SQLException e) {
				System.out.println("SQL exception Updating links \n query:"+query); 
				e.printStackTrace();
			}
			finally{
				ConnectionFactory.closeResources(conn, ps, rs);
				}
			}
		}
	}
	
	
	public void updateLinks(UserGroupForm form){
		ResultSet rs=null;
		PreparedStatement ps=null;
		Connection conn=ConnectionFactory.getConnection();
		form.setMessage("Modifying Groups Failed..");
		 
		 
		//System.out.println("checking--"+modules.getValue()+"--"+selectedLinks.contains(modules.getValue())); log.info("checking--"+modules.getValue()+"--"+selectedLinks.contains(modules.getValue()));
		if(form.getModuleName().equalsIgnoreCase("Main")){
			 ArrayList<String> selectedLinks=new ArrayList<String>();
			 Collections.addAll(selectedLinks,form.getSelectedLinksArr());
			 System.out.println("selected links-----"+selectedLinks); 
			 String query="";
				 if(conn!=null){
						try {
							for(IdValuePair l:form.getLinks()){
								int status=0;
								if(selectedLinks.contains(""+l.getId()))status=1;
								 	
								 query="insert into user_group(group_id,group_name,module_name,links,status)" +
								 		" select distinct (group_id),'"+form.getGroupName()+"','"+l.getValue()+"','',"+status+""+
						 				" from user_group" +
						 				" where group_name='"+form.getGroupName()+"' " +
				 						" and not exists(select * from user_group  where group_name='"+form.getGroupName()+"' and module_name='"+l.getValue()+"'); ";
								 
								 query+=" update user_group set status="+status+" where group_name like '"+form.getGroupName()+"' and module_name like '"+l.getValue()+"';";
								 
								 System.out.println("query--"+query);
								 ps=conn.prepareStatement(query);
								 ps.executeUpdate();
							}
							 form.setMessage("Group Modified Succesfully...");
						} catch (SQLException e) {
							System.out.println("SQL exception creating Group \n query:"+query); 
							e.printStackTrace();
						}
						finally{
							ConnectionFactory.closeResources(conn, ps, rs);
							}
						}
		}
		else{
			String links="";
			 for(String l:form.getSelectedLinksArr())
				 links+=l+",";
			 links=links.substring(0,links.length()-1);

		 String query="update user_group " +
		 				" set links='"+links+"' " +
		 				" where group_name like '"+form.getGroupName()+"' and module_name like '"+form.getModuleName()+"'";
		 System.out.println("query--"+query);
		 form.setMessage("Links Updated sucessfully..");
		if(conn!=null){
			try {
				 ps=conn.prepareStatement(query);
				 ps.executeUpdate();
				 form.setMessage("Links Updated sucessfully..");
			} catch (SQLException e) {
				System.out.println("SQL exception Updating links \n query:"+query); 
				e.printStackTrace();
			}
			finally{
				ConnectionFactory.closeResources(conn, ps, rs);
				}
			}
		}
	}

	
	
	public ArrayList<IdValuePair<String,String>> getUserDetails(String userName,int userGroupID){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		ArrayList<IdValuePair<String,String>> user = null;
	 String query="SELECT username, fullname FROM users WHERE username like '"+userName+"%' AND Activated  like 'on' AND group_id > "+userGroupID+" ORDER BY username ASC";
	 System.out.println("query--"+query); 
			
		if(conn!=null){
			try {
				 st=conn.createStatement();
				 rs=st.executeQuery(query);
				 user=new ArrayList<IdValuePair<String,String>>();
				 while(rs.next()){
					 user.add(new IdValuePair<String,String>(rs.getString("username"), rs.getString("fullname")));
				 }
			} catch (SQLException e) {
				System.out.println("SQL exception fetching users \n query:"+query+"\n"+e.getMessage()); 
				e.printStackTrace();
			}
			finally{
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		System.out.println("selected "+user.size()+" users"); 
		 return user;
	}
	
	public String getGroupName(int groupId){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		String res=null;
	 String query="SELECT distinct group_name FROM user_group where group_id="+groupId+"";
	 System.out.println("query--"+query); 
			
		if(conn!=null){
			try {
				 st=conn.createStatement();
				 rs=st.executeQuery(query);
				 if(rs.next()){
					 res=rs.getString("group_name");
				 }
				System.out.println("selected group name-- "+res); 
			} catch (SQLException e) {
				System.out.println("SQL exception fetching users \n query:"+query+"\n"+e.getMessage()); 
				e.printStackTrace();
			}
			finally{
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
		return res;
	}
	
	
	public void addModifyIncludeExcludeLinks(ModifyUserGroupForm form,ArrayList<String> incLinks,ArrayList<String> excLinks){
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
	
	
	
	public void addIncludeExcludeLinks(UserGroupForm form,ArrayList<String> incLinks,ArrayList<String> excLinks){
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
	
}
