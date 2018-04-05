package com.microlabs.ess.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microlabs.db.ConnectionFactory;

public class EmployeeOfficialInfoDao {
	ResultSet rs=null;
	Statement st=null;
	Connection conn=null;
	
	int a=0;
	public EmployeeOfficialInfoDao() {
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
  
  
}
