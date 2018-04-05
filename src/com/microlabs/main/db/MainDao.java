package com.microlabs.main.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;

import com.microlabs.db.ConnectionFactory;

public class MainDao {
	
	ResultSet rs=null;
	Statement st=null;
	Connection conn=null;
	
	int a=0;
	public MainDao() {
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

	public int SqlExecuteUpdate(String sql, LinkedList paramList){
		  
	    int b=0;
	  
	    try
		{
	    	if(paramList.isEmpty()){
	    		Statement st=conn.createStatement();
	    		b=st.executeUpdate(sql);
	    	}
	    	else{
	    		PreparedStatement pstmt;
	    		pstmt = conn.prepareStatement(sql);
	    		pstmt.setString(1, paramList.get(1).toString());
	            
	            pstmt.setString(2, paramList.get(2).toString());
	            pstmt.setString(3, paramList.get(3).toString());
	            pstmt.setString(4, paramList.get(4).toString());
	            pstmt.setString(5, paramList.get(5).toString());
	            pstmt.setString(6, paramList.get(6).toString());
	            pstmt.setInt(7, Integer.parseInt(paramList.get(7).toString()));
	            long itime = Long.parseLong(paramList.get(8).toString());
	            java.sql.Timestamp ts = new Timestamp(itime);
	            pstmt.setTimestamp(8, ts);
	            pstmt.setString(9, paramList.get(9).toString());
	            pstmt.setInt(10, Integer.parseInt(paramList.get(10).toString()));
	            b = pstmt.executeUpdate();
	    	}
			System.out.println("exe  create "+b);
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
  
  public int checkTableExist(String tableName){
	  
	    int b=0;
	  
	    try
		{
	    	DatabaseMetaData dbm = conn.getMetaData();
	    	// check if "employee" table is there
	    	ResultSet tables = dbm.getTables(null, null, tableName, null);
	    	if (tables.next()) {
	    	  b=1;
	    	}
		}catch(SQLException se){
			System.out.println("Exception @ checkTableExist");
			se.printStackTrace();
		}
		return b;
    }
  
  
}
