package com.microlabs.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.main.form.MainForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.IdValuePair;
import com.microlabs.utilities.UserInfo;


public class LoginDao {
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


public int SqlExecuteUpdate(String sql){
	  
    int b=0;
    
    try
	{
    	Connection conn=ConnectionFactory.getConnection();
	Statement st=conn.createStatement();
	b=st.executeUpdate(sql);
	}catch(SQLException se){
		se.printStackTrace();
	}
	return b;
}
	
	
	public ArrayList<String> getAllCentersId(){
		ResultSet rs=null;
		Statement st=null;
		ArrayList<String> cntrIds=null;
		Connection conn=ConnectionFactory.getConnection();
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
			e.printStackTrace();
		}
		finally{
		//	ConnectionFactory.closeResources(conn, st, rs);
		}
	}
		
		return cntrIds;
	}
	
	
	public UserInfo validate(MainForm form,HttpServletRequest request,HttpServletResponse response){
		LoginDao ad=new LoginDao();
		Connection conn=ConnectionFactory.getConnection();
		ResultSet rs=null;
		Statement st=null;
		
		UserInfo bean=null;
		HttpSession session=request.getSession();
		String userName=form.getUserName();
		String password=form.getPassword();
		String userType=form.getLoginType();
		
		if(userName==null||password==null)
		{
			UserInfo user1=(UserInfo)session.getAttribute("user");
		userName=user1.getUserName();
		
			password=user1.getPassword();
		}
		userType="temp";
		
		String query="select emp.staffcat,emp.sdptid,emp.Reporting_Grp,emp.sex,u.emp_photo,u.id,u.username,u.password,user_group.include_links,user_group.incsublinks,user_group.incsubsublinks,u.employeenumber,u.lastlogindate,u.usr_type," +
		"u.userstatus,u.groupname,u.group_id,u.passwordexpirydate,u.loginCount,emp.EMP_FULLNAME,emp.DPTID,emp.DSGID,emp.EMAIL_ID,loc.LOCID,user_group.locations from users as u," +
		"emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg,Location as loc,user_group as user_group where u.username='"+userName+"' and u.password='"+password+"' and u.Activated='On' " +
		"and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID  and emp.LOCID=loc.LOCATION_CODE and u.employeenumber=emp.PERNR and u.group_id=user_group.id";
		
		
	/*	String query="select u.id,u.username,u.password,ugrp.include_links,ugrp.incsublinks,ugrp.incsubsublinks,u.employeenumber,u.lastlogindate,u.usr_type," +
		"u.userstatus,u.groupname,u.group_id,u.passwordexpirydate,u.loginCount,u.exclude_links,emp.EMP_FULLNAME,emp.DPTID,emp.DSGID,emp.EMAIL_ID,loc.LOCID from user_group as ugrp,users as u," +
		"emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg,Location as loc where u.username='"+userName+"' and u.password='"+password+"' and u.Activated='On' " +
		"and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID  and emp.LOCID=loc.LOCATION_CODE and u.employeenumber=emp.PERNR and u.group_id=ugrp.id and ugrp.status=0";*/
		
		if(conn!=null){
			try{
				conn=ConnectionFactory.getConnection();
				st=conn.createStatement();
				rs=st.executeQuery(query);
				if(rs.next()){
					bean=new UserInfo();
					
					String incLink=rs.getString("include_links");
					//String cntrId=rs.getString("center_id");
					ArrayList<String> includeLinks=new ArrayList<String>();
					ArrayList<String> centerIds=new ArrayList<String>();
					ArrayList<String> excludeLinks=new ArrayList<String>();
					
					
					if(incLink!=null && !incLink.equals(""))
						Collections.addAll(includeLinks, incLink.split(","));
					
						
					//Collections.addAll(centerIds, rs.getString("center_id").split(","));
					//Collections.addAll(includeLinks, rs.getString("include_links").split(","));
					//Collections.addAll(excludeLinks, rs.getString("exclude_links").split(","));
					
					bean.setUserName(userName);
					bean.setPassword(password);
					bean.setDepartmentId(rs.getString("DPTID"));
					bean.setEmployeeNo(rs.getString("employeenumber"));
					
					bean.setDesignationId(rs.getString("DSGID"));
					bean.setPlantId(rs.getString("LOCID"));
					//bean.setStateId(rs.getString("state_id"));
					
					//bean.setCountryId(rs.getString("country_id"));
					bean.setId(rs.getInt("id"));
					bean.setUserName(rs.getString("username"));
					bean.setUserType(rs.getString("usr_type"));
					bean.setUserstatus(rs.getString("userstatus"));
				//	bean.setFirstName(rs.getString("firstname"));
				//	bean.setMiddleName(rs.getString("middlename"));
				//	bean.setLastName(rs.getString("lastname"));
					//bean.setS_ID(rs.getString("S_ID"));
					bean.setGroupName(rs.getString("groupname"));
					bean.setMail_id(rs.getString("EMAIL_ID"));
					bean.setGroupId(rs.getInt("group_id"));
					//bean.setPerson(rs.getString("Person"));
					//bean.setCenterIds(centerIds);
					bean.setIncludeLinks1(rs.getString("include_links"));
					bean.setIncludeSubLinks(rs.getString("incsublinks"));
					bean.setIncludeSubSubLinks(rs.getString("incsubsublinks"));
					//bean.setS_ID(rs.getString("S_ID"));
					bean.setGroupName(rs.getString("groupname"));
					bean.setMail_id(rs.getString("EMAIL_ID"));
					bean.setGroupId(rs.getInt("group_id"));
				
			
					bean.setIncludeLinks1(rs.getString("include_links"));
					bean.setIncludeSubLinks(rs.getString("incsublinks"));
					bean.setIncludeSubSubLinks(rs.getString("incsubsublinks"));
					bean.setFullName(rs.getString("EMP_FULLNAME"));
					bean.setLastLoginDate(rs.getString("lastlogindate"));
					bean.setIncludeLinks(includeLinks);
					bean.setExcludeLinks(excludeLinks);
					bean.setPasswordExpiryDate(EMicroUtils.display(rs.getDate("passwordexpirydate")));
					String avaLocations="";
					avaLocations=rs.getString("locations");
					if(avaLocations!=null){
						bean.setAvailableLocations(avaLocations);
					}else{
						bean.setAvailableLocations("");
					}
					bean.setLoginCount(rs.getString("loginCount"));
					
					String getEmpName="select first_name,middle_name,last_name from emp_personal_info where employee_no='"+userName+"'";
					ResultSet rsEmpName=ad.selectQuery(getEmpName);
					while(rsEmpName.next()){
						bean.setFirstName(rsEmpName.getString("first_name"));
						bean.setMiddleName(rsEmpName.getString("middle_name"));
						bean.setLastName(rsEmpName.getString("last_name"));
					}
					
					if(rs.getString("emp_photo").equalsIgnoreCase(""))
					{
						String sex = rs.getString("SEX");
						if (sex.equalsIgnoreCase("M"))
							bean.setEmpPhoto("/EMicro Files/images/EmpPhotos/male.png");
						if (sex.equalsIgnoreCase("F"))
							bean.setEmpPhoto("/EMicro Files/images/EmpPhotos/female.png");
					}
					else {
						bean.setEmpPhoto("/EMicro Files/images/EmpPhotos/"+rs.getString("Emp_Photo"));
					}
					
					bean.setSdptid(rs.getString("sdptid"));
					bean.setRepgrp(rs.getString("Reporting_Grp"));
					bean.setStaffcat(rs.getString("staffcat"));
				}
				else{
					//form.setMessage("User Name Password Doesnot matches");
				}
				
		
				
				

				}
			catch(SQLException e){
				e.printStackTrace();
			}
			finally{
				ConnectionFactory.closeResources(conn, st, rs);
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
				e.printStackTrace();
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
	
		String query="Select id from links where link_path='"+link_path+"' " +
				"and (method='"+method+"')";
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
				e.printStackTrace();
			}
			finally{
			//	ConnectionFactory.closeResources(conn, st, rs);
			}
		}
		
		return id;
	}
	public ArrayList<String> getGroupAllIds(String groupName,String groupID,String moduleName){
		ResultSet rs=null;
		Statement st=null;
		ResultSet rs1=null;
		Statement st1=null;
		ResultSet rs2=null;
		Statement st2=null;
		ArrayList<IdValuePair> modules = null;
		
		Connection conn=ConnectionFactory.getConnection();
		String id="0";
		ArrayList<String> idList=null;
		
		String query="Select links from user_group where group_name='"+groupName+"' and links!=null";
		System.out.println("query in getGroupAllIds--="+query);
		
		
		//String query11="select include_links from user_group where id='"+groupID+"' order by id";
		
		String query1="select id,link_name from links where module='"+moduleName+"' order by id";
		
		if(conn!=null){
			try {
								
				st=conn.createStatement();
				rs=st.executeQuery(query);
				idList=new ArrayList<String>();
				while(rs.next()){
					String v=rs.getString("links");
					if(!v.equals("")) 
						id+=","+v;
				}
				
				
				
				 //st1=conn.createStatement();
				 //rs1=st1.executeQuery(query11);
				 //if(rs1.next())
				 //{
				 //String ar1[]=rs1.getString("include_links").split(",");
				 
				 
				 
				// for (int i=0;i<ar1.length;i++)
				 //{
				 //System.out.println(ar1[i]);
				 
				 //String query1="select id,link_name from links where id='"+ar1[i]+"'";
				 
				 st2=conn.createStatement();
				 rs2=st2.executeQuery(query1);
				 while(rs2.next())
				 {
					 String v=""+rs2.getInt("id");
						if(!v.equals("")) 
							id+=","+v;
					 
				 //}
				// }
				 }
				 
				 	System.out.println("id="+id);
					
					Collections.addAll(idList, id.split(","));
			} catch (SQLException e) {
				System.out.println("SQL exception fetching Sub modules\n query:"+query); 
				e.printStackTrace();
			}
			finally{
				ConnectionFactory.closeResources(conn, st, rs);
				}
			}
						
		
		return idList;
	}
	
	
	public ArrayList<String> getGroupAllIds(String groupName,String groupID){
		ResultSet rs=null;
		Statement st=null;
		ResultSet rs1=null;
		Statement st1=null;
		ResultSet rs2=null;
		Statement st2=null;
		ArrayList<IdValuePair> modules = null;
		Connection conn=ConnectionFactory.getConnection();
		
		String id="0";
		ArrayList<String> idList=null;
		
		String query="Select links from user_group where group_name='"+groupName+"' and links!=null";
		System.out.println("query in getGroupAllIds--="+query);
		
		
		String query11="select include_links from user_group where id='"+groupID+"' order by id";
		
		
		
		if(conn!=null){
			try {
								
				st=conn.createStatement();
				rs=st.executeQuery(query);
				idList=new ArrayList<String>();
				while(rs.next()){
					String v=rs.getString("links");
					if(!v.equals("")) 
						id+=","+v;
				}
				
				
				
				 st1=conn.createStatement();
				 rs1=st1.executeQuery(query11);
				 if(rs1.next())
				 {
				 String ar1[]=rs1.getString("include_links").split(",");
				 
				 
				 
				 for (int i=0;i<ar1.length;i++)
				 {
				 System.out.println(ar1[i]);
				 
				 String query1="select id,link_name from links where id='"+ar1[i]+"'";
				 
				 st2=conn.createStatement();
				 rs2=st2.executeQuery(query1);
				 while(rs2.next())
				 {
					 String v=""+rs2.getInt("id");
						if(!v.equals("")) 
							id+=","+v;
					 
				 }
				 }
				 }
				 
				 	System.out.println("id="+id);
					
					Collections.addAll(idList, id.split(","));
			} catch (SQLException e) {
				System.out.println("SQL exception fetching Sub modules\n query:"+query); 
				e.printStackTrace();
			}
			finally{
			//	ConnectionFactory.closeResources(conn, st, rs);
				}
			}
						
		
		return idList;
	}
	
	
	public ArrayList<String> getGroupAllId(String groupName){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
		String id="0";
		ArrayList<String> idList=null;
		
		String query="Select links from user_group where group_name='"+groupName+"' and status=1";
		System.out.println("query in getGroupAllIds--="+query);
		
		/*
		String query1="Select id from links where " +
				"link_name in(SELECT module_name FROM user_group where group_name='"+groupName+"' and status=1);";
		*/	
		
		
		String query1="Select id from links where link_name in" +
				"(SELECT sub_modulename FROM user_group u where group_name='"+groupName+"'" +
				" and status=1 and sub_modulename is not null )";
		
		System.out.println("query1 in getGroupAllId--="+query1);
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
				System.out.println("id="+id);
				
				Collections.addAll(idList, id.split(","));
				}
			catch(SQLException e){
				e.printStackTrace();
			}
			finally{
			//	ConnectionFactory.closeResources(conn, st, rs);
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
		ArrayList<String> al=getGroupAllIds(groupName,groupName,groupName);
		
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
				e.printStackTrace();
			}
			finally{
			//	ConnectionFactory.closeResources(conn, st, rs);
			}
		}

		return pair;

	}
	
	public void insertLog(String userName,String ip,String url,String method){
		ResultSet rs=null;
		PreparedStatement ps=null;
		Connection conn=ConnectionFactory.getConnection();
		String linkPath="";
		
		String split[]=url.split("/");
		if(split.length>0) 	linkPath=split[split.length-1];
		
		url=url+"?method="+method;
		
		IdValuePair<String, String> pair=getLinkNameModule(linkPath, method);
		
	if(pair!=null){
		String query="insert into log (user_name,ip,access_date,url,link_name,module,date_stamp) values('"+userName+"','"+ip+"',now(),'"+url+"','"+pair.getId()+"' ,'"+pair.getValue()+"',CURRENT_DATE); ";
		if(conn!=null){
			try {
				ps=conn.prepareStatement(query);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				//ConnectionFactory.closeResources(conn, ps, rs);
			}
			}
	}
	}
	
	public boolean isValidUserName(String userName,int userGroupId){
		ResultSet rs=null;
		Statement st=null;
		Connection conn=ConnectionFactory.getConnection();
	
		String query="Select count(username) from users where username='"+userName+"' ";
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
			//	ConnectionFactory.closeResources(conn, st, rs);
			}
		}
		if(count==1) return true;
		
		return false;
	}
	
	public String usersGroupName(String groupID){
		ResultSet rs=null;
		Statement st=null;
		
		Connection conn=ConnectionFactory.getConnection();
		String query="Select group_name from user_group where id='"+groupID+"' ";
		String groupName="";
		if(conn!=null){
			try{
				conn=ConnectionFactory.getConnection();
				st=conn.createStatement();
				rs=st.executeQuery(query);
				if(rs.next())
					groupName=rs.getString("group_name");
				}
			catch(SQLException e){
				e.printStackTrace();
			}
			finally{
			//	ConnectionFactory.closeResources(conn, st, rs);
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
			//	ConnectionFactory.closeResources(conn, st, rs);
			}
		}
		
		return excludeLinks;
	}
}
