package com.microlabs.admin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.microlabs.admin.form.UserGroupForm;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.utilities.IdValuePair;

public class UserGroupDao {
	
	
	public ArrayList<String> getGroups(String groupid){
		  
		
		ArrayList<String> a1=null;
		try {
			Connection connection=ConnectionFactory.getConnection();
			
			Statement statement=connection.createStatement();
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		return a1;
	}
	
	public int getGroupId(){
		
		int groupId=0;
		try {
			Connection connection=ConnectionFactory.getConnection();
			
			Statement statement=connection.createStatement();
			
			String query1="select count(distinct group_id) as groupId from user_group";
			statement=connection.createStatement();
			
			ResultSet rs1=statement.executeQuery(query1);
			
			if(rs1.next()){
				groupId=rs1.getInt("groupId");
			}rs1.close();
			statement.close();
			connection.close();
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		return groupId;
	}
	
	public int display(int groupId){
		
		
		return 0;
	}
	
	public int insertUserGroup(UserGroupForm userGroupForm,String[] ls){
		
		int a=0;
		
		
		try {
			
			int	groupId=getGroupId();
			
			String query="select id,link_name from links";
			System.out.println("query--"+query); 
				
			Connection connection=ConnectionFactory.getConnection();
			Statement statement1=connection.createStatement();
			ResultSet rs=statement1.executeQuery(query);
			 while(rs.next()){
				 
				 String sql="insert into user_group(group_name,module_name,links,status,group_id)" +
					"values('"+userGroupForm.getGroupName()+"','"+rs.getString(2)+"'," +
							"' ',"+0+","+groupId+")";
			System.out.println("Insert Query is ************"+sql);
			a=statement1.executeUpdate(sql);
			 }
			// rs.close();
			 statement1.close();
			 
		
		
		for(int i=0;i<ls.length;i++){
		
		String sql="update user_group set status=1 where group_name='"+userGroupForm.getGroupName()+"'" +
				" and module_name='"+ls[i]+"'";
		
		System.out.println("Insert Query is ************"+sql);
		Statement statement2=connection.createStatement();
		a=statement2.executeUpdate(sql);
		
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return a;
	}
	
	
	public ArrayList<IdValuePair> getLinkIdValuePair(String module){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		ArrayList<IdValuePair> links = null;
		
	 String query="select id,link_name from links";
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
}
