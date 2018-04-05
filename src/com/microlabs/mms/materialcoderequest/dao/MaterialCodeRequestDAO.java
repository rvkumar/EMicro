package com.microlabs.mms.materialcoderequest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;

import com.microlabs.db.ConnectionFactory;


public class MaterialCodeRequestDAO {
	private ResultSet rs=null;
	private Statement st=null;
	private Connection conn=null;
	
	public MaterialCodeRequestDAO() {
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
    		Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    		rs=st.executeQuery(sql);
    	}catch(SQLException se){
    		se.printStackTrace();
    	}
    	return rs;
    }
  
 public int[] updateMaterialRequestWithSAPDetails(Map<Integer,Character> result){
	 try {
		PreparedStatement st=conn.prepareStatement("Update material_code_request set Message_Type = ? where REQUEST_NO = ?");
		for(Entry<Integer,Character> entry :  result.entrySet()){			
			st.setString(0, entry.getValue().toString());
			st.setInt(1, entry.getKey());
			st.addBatch();
		}
		if(!result.isEmpty()){			
			return st.executeBatch();
		}
	} catch (SQLException e) {
		e.printStackTrace();// TODO Change to log
	}
	return null; 
 }

}

