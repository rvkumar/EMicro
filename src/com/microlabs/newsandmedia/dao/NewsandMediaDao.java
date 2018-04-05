package com.microlabs.newsandmedia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microlabs.db.ConnectionFactory;

public class NewsandMediaDao {
	
	ResultSet rs=null;
	Statement st=null;
	Connection conn=null;
	
	int a=0;
	public NewsandMediaDao() {
		try {
				
			if(conn == null || !(conn.isClosed())) {
				conn=ConnectionFactory.getConnection();
			} else {
				
			}
			if(st != null){
				System.out.println("Connection Statement Already Opened ");
			}else{
				st = conn.createStatement(); 
			}
			

			
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
	    	Connection	conn=ConnectionFactory.getConnection1();
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
    		Connection	conn=ConnectionFactory.getConnection1();
    		Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    		rs=st.executeQuery(sql);
    	}catch(SQLException se){
    		se.printStackTrace();
    	}
    			return rs;
    }
  
  
}
