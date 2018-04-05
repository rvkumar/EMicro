package com.microlabs.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;



public class DishaConnectionFactory {
	
	static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String url="jdbc:sqlserver://192.168.1.192:1433;databaseName=SBSCentral";
	
	static String username="sa";
	static String password="micro123#";
	ResultSet rs=null;
	Statement st=null;
	Connection conn=null;
	public static Connection con=null;
	static DishaConnectionFactory factory=null;
	/*public ConnectionFactory(){
		
		try{
			 DataSource dataSource;
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			 if(con==null || con.isClosed()){
			InitialContext context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/emicroPortal");
			con = dataSource.getConnection();
			//System.out.println("Conn="+con);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL exception while creating connection\n"+e.getMessage()); 
		}catch (Exception e) {
			e.printStackTrace();
		}
		 }*/
	
/*	static{
		try {
			InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream(propertyFile);
			Properties props = new Properties();
			props.load(in);
			in.close();
			
			 driver = props.getProperty("jdbc.driver");
			 url = props.getProperty("jdbc.url");
			 username = props.getProperty("jdbc.username");
			 password = props.getProperty("jdbc.password");
			 
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File Not Found "+propertyFile+" file not found \n"); 
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException "+propertyFile+" could not be read..\n");
		} 
	}*/
	
	public static Connection getConnection1() {
		try {
			if(con==null || con.isClosed()){
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL exception while creating connection\n"+e.getMessage()); 
		}catch (Exception e) {
			e.printStackTrace();
		}
	return con;
}
	
	public static Connection getConnection() {
		try {
			if(con==null || con.isClosed()){
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL exception while creating connection\n"+e.getMessage()); 
		}catch (Exception e) {
			e.printStackTrace();
		}
	return con;
}
	public ResultSet selectQuery(String sql) {
  		
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
	public static void closeResources(Connection conn, Statement st, ResultSet rs) {
		try{
			
			st.close();
			st=null;
			
			rs.close();
			rs=null;
			
			conn.close();
			conn=null;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Some exception closing resources ---------\n"+e.getMessage()); 
		}
		
	}
	
	
	public static void closeResources(Connection conn, PreparedStatement ps,ResultSet rs) {
		try{
			System.out.print("now closing PreparedStatement -------------"); 
			ps.close();
			ps=null;
			System.out.print("now closing ResultSet -------------"); 
			rs.close();
			rs=null;
			System.out.println("now closing connection ------------"); 
			conn.close();
			conn=null;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Some exception closing resources ---------\n"+e.getMessage());  
		}
		
		
	}
 }
