package com.microlabs.ess.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.ess.form.SAPLeaveForm;
import com.microlabs.ess.form.SAPOndutyForm;
import com.microlabs.hr.form.EmpOfficalInformationForm;
import com.microlabs.it.form.IssuesForm;


public class SAPLeaveDao {
	
	private static final char[] PASSWORD = "enfldsgbnlfghfghsngdlkddddsgm".toCharArray();
    private static final byte[] SALT = {
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
    };


 private static String encrypt(String property) throws GeneralSecurityException, UnsupportedEncodingException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
    }

    private static String base64Encode(byte[] bytes) {
        // NB: This class is internal, and you probably should use another impl
        return new BASE64Encoder().encode(bytes);
    }

    private static String decrypt(String property) throws GeneralSecurityException, IOException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        // NB: This class is internal, and you probably should use another impl
        return new BASE64Decoder().decodeBuffer(property);
    }
	
    public int[] updateCompffDetails(LinkedList compList) {
		try {
			PreparedStatement st = conn
					.prepareStatement("update COMP_OT set SAP_Updated=?,SAP_Message=?,SAP_Updated_Date=? where SAP_ReqNo=? ");
			Iterator compItr = compList.iterator();
			while (compItr.hasNext()) {
				LeaveForm leavDao = (LeaveForm) compItr.next();
				st.setString(1, leavDao.getStatus());
				st.setString(2, leavDao.getMessage());
				st.setString(3, leavDao.getApprovedDate());
				st.setInt(4, leavDao.getRequestNumber());

				st.addBatch();
			}
			if (!compList.isEmpty()) {
				return st.executeBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();// TODO Change to log
		}
		return null;
	}
	
	public int[] updateESCALATIONDetails(LinkedList esclateList)
	{
		try {
		 Iterator  updateitr=esclateList.iterator();
		 
		 if(conn==null||conn.isClosed())
		 {
			 conn=ConnectionFactory.getConnection();
		 }
		 PreparedStatement	pst=conn.prepareStatement("update IT_ISSUES set Escalated='1',Escalated_date=?,Escalated_Level=? where Req_No =? ");
		while(updateitr.hasNext())
		{
			IssuesForm issues=(IssuesForm)updateitr.next();
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ");
			String datenw = sdf.format(d);
			
			pst.setString(1, datenw);			
			pst.setString(2, issues.getId());
			pst.setInt(3, issues.getRequestNo());
			pst.addBatch();
		
		
		
		}
		
		if(!esclateList.isEmpty())
		{
			pst.executeBatch();
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));			
			Date d=new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String dateNow = ft.format(d);
			String error="insert into ERROR_DETAILS values('ESCALATION','"+dateNow+"','"+errors.toString()+"')";
			int i= ad.SqlExecuteUpdate(error);
		}
		
		return null; 
	}
	
	
	public int[] updateProdataDetails(LinkedList prodata){
		
		 try {
			 if(conn==null||conn.isClosed())
			 {
				 conn=ConnectionFactory.getConnection();
			 }
				PreparedStatement st=conn.prepareStatement("update lv_type_d set lv_opbal=?,lv_clbal=?,SAP_Status=?,SAP_Approved_Date=?,prodata=1 where lv_empcode=? and lv_calyear=? and lv_typeid =(select lv_typeid from lv_type_m where lv_shrt=?)") ;
				Iterator attdItr = prodata.iterator();
				while(attdItr.hasNext()) {
					LeaveForm leavDao = (LeaveForm)attdItr.next();
					st.setFloat(1, leavDao.getOpeningBalence());
					st.setFloat(2, leavDao.getClosingBalence());
					st.setString(3,"Yes");
					Date dNow = new Date();
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String submitDate = ft.format(dNow);
					st.setString(4, submitDate);					
					st.setString(5, leavDao.getEmployeeNumber());	
					st.setInt(6, leavDao.getYear());
					st.setString(7, leavDao.getLeaveType());
					
					st.addBatch();
				    }
				if(!prodata.isEmpty()){			
					return st.executeBatch();
				}
			} catch (SQLException e) {
				e.printStackTrace();// TODO Change to log
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));			
				Date d=new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				String dateNow = ft.format(d);
				String error="insert into ERROR_DETAILS values('PRODATA','"+dateNow+"','"+errors.toString()+"')";
				int i= ad.SqlExecuteUpdate(error);
			}
		return null; 
	}
	
	
	EssDao ad=EssDao.dBConnection();
	int ID=0;
	private ResultSet rs=null;
	private Statement st=null;
	private Connection conn=null;
	public  SAPLeaveDao() {
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
	public int[] updateleaveDetails(LinkedList attdList){
		 try {
			 
			 if(conn==null||conn.isClosed())
			 {
				 conn=ConnectionFactory.getConnection();
			 }
			PreparedStatement st=conn.prepareStatement("update leave_details set SAP_Status=?,SAP_Approved_Date=?,SAP_Message=? where CONVERT(varchar(8), start_date, 112)=? and CONVERT(varchar(8), end_date, 112)=? and start_duration=? and end_duration=? and user_id=? and Approvel_Status in ('Approved','Cancelled') ") ;
			Iterator attdItr = attdList.iterator();
			while(attdItr.hasNext()) {
				SAPLeaveForm leavDao = (SAPLeaveForm)attdItr.next();
				st.setString(1, leavDao.getSapstatus() );
				st.setString(2, leavDao.getSapApprovedDate());
				st.setString(3, leavDao.getMessage());				
				st.setString(4, leavDao.getStartDate());
				st.setString(5, leavDao.getEndDate());
				st.setString(6, leavDao.getStartDurationType());
				st.setString(7, leavDao.getEndDurationType());
				String emp=leavDao.getEmployeeNo();
				//emp=emp.substring(3, emp.length());
				st.setString(8, Integer.toString(Integer.parseInt(emp)));					
				st.addBatch();
			    }
			if(!attdList.isEmpty()){			
				return st.executeBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();// TODO Change to log
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));			
			Date d=new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String dateNow = ft.format(d);
			String error="insert into ERROR_DETAILS values('LEAVE','"+dateNow+"','"+errors.toString()+"')";
			int i= ad.SqlExecuteUpdate(error);
		}
		return null; 
	 }
	
public int[] updateempoffDetails(LinkedList insertList,LinkedList updateList){
		
		try {
			 if(conn==null||conn.isClosed())
			 {
				 conn=ConnectionFactory.getConnection();
			 }
			PreparedStatement st=conn.prepareStatement("insert into emp_official_info( ID,PERNR,Emp_FullName,Sex,WERKS,BUKRS,DPTID,DSGID,APPMGR,RPTMGR,LOCID,LAND1,STATE,DOJ,DOC,DOL,"
					+ "ESI,ESINO,PF,PFNO,PANNO,pay_group,STAFFCAT,GRDID,payment_method,WAERS,BACCTYP,BACCNO,BANKID,BRANCH,IFSC_CODE,MICR_CODE,DOB,TITLE,UANNO,ACTIVE,PT,IT,BONUS,LEAVES,EMAIL_ID,SAP_Status,SAP_Approved_Date,Shift_Code,Rule_Code,Work_ID,Reporting_Grp,SDPTID)"
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			PreparedStatement st1=conn.prepareStatement("insert into users( username,password,count,activated,usr_type,group_id,status,employeenumber,loginCount,Emp_Photo,SAP_Status,SAP_Approved_Date)"
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?)");
			
			PreparedStatement st2=conn.prepareStatement("update emp_official_info set WERKS=?,pay_group=?,STAFFCAT=?, RPTMGR=?,APPMGR=?,LAND1=?,STATE=?,LOCID=?,GRDID=?,DSGID=?,DPTID=?,"
			+ "ESI=?,ESINO=?,PF=?,PFNO=?,PT=?,IT=?,PANNO=?,BONUS =?,LEAVES =?,WAERS =?,payment_method =?,BANKID =?,BACCTYP =?,BACCNO =?,BRANCH =?,IFSC_CODE =?,MICR_CODE  =?,UANNO=?,SAP_Status=?,SAP_Approved_Date=?,DOL=?,ACTIVE=?,Shift_Code=?,Rule_Code=?,Work_ID=?,Reporting_Grp=?,SDPTID=? Where pernr=? ");
			
			
			PreparedStatement st3=conn.prepareStatement("update users set SAP_Status=?,SAP_Approved_Date=?,activated=? where employeenumber=?");   
					
			PreparedStatement st4=conn.prepareStatement("insert into ESS_Approvers(essType,employeeNumber,Priority,ApproverId,reqType) values (?,?,?,?,?)");
			
			PreparedStatement st5=conn.prepareStatement("insert into lv_type_d values (1,?,?,0,0,0,0,?,?,0,0),(2,?,?,0,0,0,0,?,?,0,0),(3,?,?,0,0,0,0,?,?,0,0)");
			
	        PreparedStatement ps1=conn.prepareStatement(" insert into EMP_IN_OUT(Pernr,Month,Year,In_Out) select ?,stateno,?,state from EMP_SHIFT_REFERENCE , number where  Shift_code='0001' and no<=24");
			
			PreparedStatement ps2=conn.prepareStatement(" insert into EMP_IN_OUT_Status(Pernr,Month,Year) select ?,no,? from EMP_SHIFT_REFERENCE , number where  Shift_code='0001' and no<=12");
			
			PreparedStatement ps3=conn.prepareStatement("insert into EMP_Shift_Register(Pernr,Month,Year) select ?,no,? from EMP_SHIFT_REFERENCE , number where  Shift_code='0001' and no<=12");
			
			PreparedStatement ps4=conn.prepareStatement("insert into EMP_Shift_Register select '',?,no,?,Day1,	Day2,	Day3,	Day4,	Day5,	Day6,	Day7,	Day8,	Day9,	Day10,	Day11,	Day12,	Day13,	Day14,	Day15,	Day16,	Day17,	Day18,	Day19,	Day20,	Day21,	Day22,	Day23,	Day24,	Day25,	Day26,	Day27,	Day28,	Day29,	Day30,	Day31,getdate(),getdate(),'','',''  from EMP_SHIFT_REFERENCE,number where Shift_code=? and no<=12 ");
			
			
			Iterator  updateitr=updateList.iterator();
			Iterator attdItr = insertList.iterator();
			while(updateitr.hasNext()) {

				EmpOfficalInformationForm a=(EmpOfficalInformationForm)updateitr.next();
				System.out.println(a.getEmployeeName());
				//st2.setString(1,a.getEmployeeName());
				st2.setString(1,a.getLocation());
				st2.setString(2,a.getPayGroup());
				st2.setString(3,a.getEmployeeCategory());
				st2.setString(4,a.getReportingManger());
				st2.setString(5,a.getApprovalManger());
				st2.setString(6,a.getCounID());
				st2.setString(7,a.getState());
				st2.setString(8,a.getPlant());
				st2.setString(9,a.getGradeID());
				st2.setString(10,a.getDesignation());
				st2.setString(11,a.getDepartment());
				st2.setString(12,a.getEligibleforESIDeduction());
				st2.setString(13,a.getEsiNumber());
				st2.setString(14,a.getEligibleforPFDeduction());
				st2.setString(15,a.getPfNumber());
				st2.setString(16,a.getEligibleforPTDeduction());
				st2.setString(17,a.getEligibleforITDeduction());
				st2.setString(18,a.getPanNo());
				st2.setString(19,a.getBonus());
				st2.setString(20,a.getLeaves());
				st2.setString(21,a.getSalaryCurrency());
				st2.setString(22,a.getPaymentMethod());
				st2.setString(23,a.getBankId());
				st2.setString(24,a.getAccountType());
				st2.setString(25,a.getAccountNumber());
				st2.setString(26,a.getBranchName());
				st2.setString(27,a.getIfsCCode());
				st2.setString(28,a.getMicrCode());
				st2.setString(29,a.getUanno());
				st2.setString(30,"Yes");
				Date dNow = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String submitDate = ft.format(dNow);
				st2.setString(31, submitDate);					
				st2.setString(32,a.getDateofLeaving());
				
				if(!a.getDateofLeaving().equalsIgnoreCase(""))
				{
					st2.setString(33,"False");
				}
				else
				{
					st2.setString(33,"True");
				}
				
				st2.setString(34,a.getShift_Code());	
				st2.setString(35,a.getRule_Code());	
				
				st2.setString(36,a.getWork_id());
				
				st2.setString(37,a.getRepgrp());	
				st2.setString(38,a.getSdptid());
				
				st2.setString(39,a.getEmployeeNumber());
				
			
				st2.addBatch();
				
			
				st3.setString(1,"Yes");
				st3.setString(2,submitDate);
				if(!a.getDateofLeaving().equalsIgnoreCase(""))
				{
					st3.setString(3,"Off");
				}
				else
				{
					st3.setString(3,"On");
				}
				
				st3.setString(4,a.getEmployeeNumber());
				st3.addBatch();
				
			
				
			}
			
			
			while(attdItr.hasNext()) {
				EmpOfficalInformationForm a=(EmpOfficalInformationForm)attdItr.next();
				
				if(ID==0)
				{
				String leave="select MAX(id) from emp_official_info";					
				ResultSet rs=ad.selectQuery(leave);
				while(rs.next())					
				{		
					ID=rs.getInt(1);
				}			
				}
				ID=ID+1;
				st.setInt(1,ID);
				st.setString(2,a.getEmployeeNumber());
				st.setString(3,a.getEmployeeName());
				st.setString(4,a.getGender());
				st.setString(5,a.getLocation());
				st.setString(6,a.getCompanyName());
				st.setString(7,a.getDepartment());
				st.setString(8,a.getDesignation());
				st.setString(9,a.getApprovalManger());
				st.setString(10,a.getReportingManger());
				st.setString(11,a.getPlant());
				st.setString(12,a.getCounID());
				st.setString(13,a.getState());
				st.setString(14,a.getDateofJoining());
				st.setString(15,a.getDateofConformation());
				st.setString(16,a.getDateofLeaving());
				st.setString(17,a.getEligibleforESIDeduction());
				st.setString(18,a.getEsiNumber());
				st.setString(19,a.getEligibleforPFDeduction());
				st.setString(20,a.getPfNumber());
				st.setString(21,a.getPanNo());
				st.setString(22,a.getPayGroup());
				st.setString(23,a.getEmployeeCategory());
				st.setString(24,a.getGradeID());
				st.setString(25,a.getPaymentMethod());
				st.setString(26,a.getSalaryCurrency());
				st.setString(27,a.getAccountType());
				st.setString(28,a.getAccountNumber());
				st.setString(29,a.getBankId());
				st.setString(30,a.getBranchName());
				st.setString(31,a.getIfsCCode());
				st.setString(32,a.getMicrCode());
				st.setString(33,a.getDob());
				st.setString(34,a.getTitle());
				st.setString(35,a.getUanno());				
				st.setString(36,"True");				
				st.setString(37, a.getEligibleforPTDeduction());
				st.setString(38, a.getEligibleforITDeduction());
				st.setString(39, a.getBonus());
				st.setString(40,a.getLeaves());
				st.setString(41," ");
				st.setString(42,"Yes");
				Date dNow = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String submitDate = ft.format(dNow);
				st.setString(43,submitDate);	
				st.setString(44,a.getShift_Code());	
				st.setString(45,a.getRule_Code());	
			
				st.setString(46,a.getWork_id());	
				st.setString(47,a.getRepgrp());	
				st.setString(48,a.getSdptid());
				st.addBatch();
				
				
				
				String v[]=(a.getDateofJoining()).split("-");
				String pwd=v[2]+v[1]+v[0];
				st1.setString(1,a.getEmployeeNumber());
				try {
					st1.setString(2,encrypt(pwd.trim()));
				} catch (UnsupportedEncodingException
						| GeneralSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				st1.setString(3,"0");
				st1.setString(4,"On");
				st1.setString(5,"per");
				st1.setString(6,a.getUsergrp());
				st1.setString(7,"1");
				st1.setString(8,a.getEmployeeNumber());
				st1.setString(9,"0");
				st1.setString(10,"");
				st1.setString(11,"Yes");			
				st1.setString(12,submitDate);
				st1.addBatch();
				
				
				st4.setString(1, "Leave");
				st4.setString(2, a.getEmployeeNumber());
				st4.setString(3, "1");
				st4.setString(4, a.getApprovalManger());
				st4.setString(5, "sequential");
				st4.addBatch();
				
				int year = Calendar.getInstance().get(Calendar.YEAR);
				
				st5.setInt(1, year);
				st5.setString(2, a.getEmployeeNumber());
				st5.setString(3,"Yes");			
				st5.setString(4,submitDate);
				st5.setInt(5, year);
				st5.setString(6, a.getEmployeeNumber());
				st5.setString(7,"Yes");			
				st5.setString(8,submitDate);
				st5.setInt(9, year);
				st5.setString(10, a.getEmployeeNumber());
				st5.setString(11,"Yes");			
				st5.setString(12,submitDate);
				st5.addBatch();
				
				
				ps1.setString(1, a.getEmployeeNumber());
				ps1.setString(2,Integer.toString(year));
				ps1.addBatch();
				
				
				ps2.setString(1, a.getEmployeeNumber());
				ps2.setString(2,Integer.toString(year));
				ps2.addBatch();
             	
             	if(a.getShift_Code()==null)
             	{
             		a.setShift_Code("");
             	}

                if(a.getShift_Code().equalsIgnoreCase("")) 
                {
             
        		ps3.setString(1, a.getEmployeeNumber());
				ps3.setString(2,Integer.toString(year));		
				ps3.addBatch();
                
			 }
                else
                {
				
				ps4.setString(1, a.getEmployeeNumber());
				ps4.setString(2,Integer.toString(year));
				ps4.setString(3, a.getShift_Code());
				ps4.addBatch();
                }
				
			}
			if(!insertList.isEmpty()){
				System.out.println("insert LIST--------------------");
				st.executeBatch();
				st1.executeBatch();
				st4.executeBatch();
				st5.executeBatch();				 
				 ps1.executeBatch();
					ps2.executeBatch();
					ps3.executeBatch();
					ps4.executeBatch();
				}
			
			if(!updateList.isEmpty()){
				System.out.println("update LIST--------------------");
				st3.executeBatch();
				return st2.executeBatch();
				}
		
		} catch (SQLException e) {
				e.printStackTrace();// TODO Change to log
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));			
				Date d=new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				String dateNow = ft.format(d);
				String error="insert into ERROR_DETAILS values('EMP_OFF','"+dateNow+"','"+errors.toString()+"')";
				int i= ad.SqlExecuteUpdate(error);
			}
			return null; 
		
		
	}
	
	
	
	public int[] updateondutyDetails(LinkedList attdList){
		 try {
			 if(conn==null||conn.isClosed())
			 {
				 conn=ConnectionFactory.getConnection();
			 }
			PreparedStatement st=conn.prepareStatement("update OnDuty_details set SAP_Status=?,SAP_Approved_Date=?,SAP_Message=? where  user_id=? and request_no=? ") ;
			Iterator attdItr = attdList.iterator();
			while(attdItr.hasNext()) {
				SAPOndutyForm leavDao = (SAPOndutyForm)attdItr.next();
				st.setString(1, leavDao.getSapstatus() );
				st.setString(2, leavDao.getSapApprovedDate());
				st.setString(3, leavDao.getMessage());			
				
				String emp=leavDao.getEmployeeNo();
				emp=emp.substring(3, emp.length());
				st.setString(4, emp);				
				st.setInt(5, leavDao.getReqno());
				st.addBatch();
			    }
			if(!attdList.isEmpty()){			
				return st.executeBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();// TODO Change to log
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));			
			Date d=new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String dateNow = ft.format(d);
			String error="insert into ERROR_DETAILS values('ONDUTY','"+dateNow+"','"+errors.toString()+"')";
			int i= ad.SqlExecuteUpdate(error);
		}
		return null; 
	 }
	public static String display(String dbDate)
	{
		String dt="";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss");
		try{
			
		}catch (Exception e) {
			dt="";
		}
		return dt;
	}


}
