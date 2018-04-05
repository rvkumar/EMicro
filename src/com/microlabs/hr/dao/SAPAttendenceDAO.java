package com.microlabs.hr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.hr.form.SAPAttendenceForm;

public class SAPAttendenceDAO {
	private ResultSet rs=null;
	private Statement st=null;
	private Connection conn=null;
	public SAPAttendenceDAO() {
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
	public int[] updateAttendenceDetails(LinkedList attdList,String empNo){
		 try {
			PreparedStatement st=conn.prepareStatement("insert into SAP_Attendence(PERNR,BEGDA,INTIME,INSTATUS,OUTTIME,OUTSTATUS,SHIFT ) values(?,?,?,?,?,?,?) ") ;
			Iterator attdItr = attdList.iterator();
			while(attdItr.hasNext()) {
				SAPAttendenceForm attDao = (SAPAttendenceForm)attdItr.next();
				st.setString(1, empNo);
				st.setString(2, attDao.getDate());
				st.setString(3, attDao.getiNTIME());
				st.setString(4, attDao.getiNSTATUS());
				st.setString(5, attDao.getoUTTIME());
				st.setString(6, attDao.getoUTSTATUS());
				st.setString(7, attDao.getShift());
				st.addBatch();
			    }
			if(!attdList.isEmpty()){			
				return st.executeBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();// TODO Change to log
		}
		return null; 
	 }
}
