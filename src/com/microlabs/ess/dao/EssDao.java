package com.microlabs.ess.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microlabs.db.ConnectionFactory;

public class EssDao {
	
	ResultSet rs=null;
	Statement st=null;
	
	int a=0;
	
	public EssDao() {
		try {
				
			
				Connection	conn=ConnectionFactory.getConnection1();
			
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
	
	public static EssDao dBConnection(){
		EssDao	ad=new EssDao();
		return ad;
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
			Connection	conn=ConnectionFactory.getConnection1();
			if(!conn.isClosed())
				conn.close();
			closeStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int SqlExecuteUpdate(String sql){
		  
	    int b=0;
	    Connection conn=ConnectionFactory.getConnection();
	    try
		{
		Statement st=conn.createStatement();
		b=st.executeUpdate(sql);
		}catch(SQLException se){
			se.printStackTrace();
		}
		return b;
	}
	
	
	public int SqlExecuteUpdatePrmaryKeys(String sql){
		  
	    int b=0;
	    Connection conn=ConnectionFactory.getConnection();
	    try
		{
		Statement st=conn.createStatement();
		b=st.executeUpdate(sql);
		}catch(SQLException se){
			se.printStackTrace();
		}
		return b;
	}
	  
	  
	  public int insertStudents(String imageName,String studentName,String rollNo
			  ,String email,String dateOfBirth,String phoneNo,String mobileNo,
			  String husName,String fatherName,String studentAvailability,
			  String studentLanguageSkills,String motherTongue,String nationality,
			  String gender, String studentAddress,String studentSkills,
			  String workLocation, String catId,String studentWorkLocation) {
		  Connection conn=ConnectionFactory.getConnection();
		  try
	    	{
	    		Statement st=conn.createStatement();
	    		rs=st.executeQuery("");
	    	}catch(SQLException se){
	    		se.printStackTrace();
	    	}
	    			
		  
		  return 0;
	  }
	  
	  
      public ResultSet selectQuery(String sql) {
    	  Connection conn=ConnectionFactory.getConnection();
    	try
    	{
    		Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
    				ResultSet.CONCUR_READ_ONLY);
    		rs=st.executeQuery(sql);
    	}catch(SQLException se){
    		se.printStackTrace();
    	}
    			return rs;
    }
  
  
  
}
