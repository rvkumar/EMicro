package com.microlabs.ess.payslip.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.attendence.db.ConnectionFactory1;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.payslip.form.PayslipForm;

public class payslipDB {
	private ResultSet rs=null;
	private Statement st=null;
	private Connection conn=null;
	public payslipDB() {
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
	public void updateAPAYSLIP_D(List listPayD,String empNo){
		//INSERT INTO dbo.PAYSLIP_D(PAYGROUP,PERNR,PR_MNTH,SAL_ID,SAL_TYPE,SAL_TAX,INVE_CO,SAL_AMT,ARR_AMT,INP_SLIP,CR_DAT,CPU_TM,CREA_BY_CODE)
		try{
		PreparedStatement st=conn.prepareStatement("INSERT INTO dbo.PAYSLIP_D(PAYGROUP,PERNR,PR_MNTH,SAL_ID,SAL_TYPE,SAL_TAX,INVE_CO,SAL_AMT,ARR_AMT," +
				"INP_SLIP,CR_DAT,CPU_TM,CREA_BY_CODE) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
		Iterator attdItr = listPayD.iterator();
		while(attdItr.hasNext()) {
			
			PayslipForm p = (PayslipForm)attdItr.next();
			st.setString(1,p.getPayGroup());
			st.setString(2,p.getEmpcod());
			st.setString(3,p.getPaymnt());
			st.setInt(4,p.getSAL_ID());
			st.setString(5,p.getINVE_CO());
			st.setDouble(6,p.getSAL_AMT());
			st.setDouble(7,p.getARR_AMT());
			st.setDouble(8,p.getINP_SLIP());
			st.setString(9,p.getCR_DAT());
			st.setString(10,p.getCUR_KEY());
			st.setString(11,p.getENT_USER_NAME());
			st.setString(12,p.getCR_DAT());
			st.setString(13,p.getCPU_TM());
			st.addBatch();
	    }
		if(!listPayD.isEmpty()){			
			 st.executeBatch();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateAPAYSLIP_HDetails(List listPayH,String empNo){
	 try {
			
			PreparedStatement st=conn.prepareStatement("insert into PAYSLIP_H(PR_MNTH,COMP_CODE,PLANT_CODE,STAFF_CAT_CODE," +
			"STATE_ID,LOC_ID,GRD_ID,DSG_ID,DPT_ID,CUR_KEY,PAY_MODE,BANK_ID,BANK_ACC_TYP,BANK_ACC_NO,BANK_BRANCH,IFSC_CODE,MICR_CODE,WKG_DAYS," +
			"WKD_DAYS,LEV_DAYS,LOP_DAYS,TOT_EARN,TOT_DEDN,TOT_AEARN,TOT_ADEDN,NET_EARN,ENT_USER_NAME,SAL_PAY_GROUP,PERNR,CR_DAT,CPU_TM )" +
			" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ") ;//2,CPU_TM
			Iterator attdItr = listPayH.iterator();
			while(attdItr.hasNext()) {
				PayslipForm p = (PayslipForm)attdItr.next();
				
								st.setString(1,p.getPaymnt());
								st.setString(2,p.getComaddr());
								st.setString(3,p.getLocation());
								st.setInt(4,p.getGRD_ID());
								st.setInt(5,p.getSTATE_ID());
								st.setInt(6,p.getLOC_ID());
								st.setInt(7,p.getGRD_ID());
								st.setInt(8,p.getDSG_ID());
								st.setInt(9,p.getDPT_ID());
								st.setString(10,p.getCUR_KEY());
								st.setString(11,p.getPAY_MODE());
								st.setInt(12,p.getBANK_ID());
								st.setString(13,p.getBANK_ACC_TYP());
								st.setString(14,p.getBANK_ACC_NO());
								st.setString(15,p.getBANK_BRANCH());
								st.setString(16,p.getIFSC_CODE());
								st.setString(17,p.getMICR_CODE());
								st.setDouble(18,p.getWKG_DAYS());
								st.setDouble(19,p.getWKD_DAYS());
								st.setDouble(20,p.getLEV_DAYS());
								st.setDouble(21,p.getLOP_DAYS());
								st.setDouble(22,p.getTOT_EARN());
								st.setDouble(23,p.getTOT_DEDN());
								st.setDouble(24,p.getTOT_AEARN());
								st.setDouble(25,p.getTOT_ADEDN());
								st.setDouble(26,p.getNET_EARN());
							
								st.setString(27,p.getENT_USER_NAME());
								st.setString(28,p.getPayGroup());
								st.setString(29,p.getEmpcod());
								st.setString(30,p.getCR_DAT());
								st.setString(31,p.getCPU_TM());
				st.addBatch();
			    }
			if(!listPayH.isEmpty()){			
				 st.executeBatch();
			}
	 } catch (SQLException e) {
			e.printStackTrace();// TODO Change to log
		}
	
	
}

public int submitdb(String empcode,String month,HttpServletRequest request,PayslipForm payslipForm){
	
	Connection con = ConnectionFactory.getConnection();
	
	int i=0;
	try {
		
		
		Statement st1=con.createStatement();
		Statement st2a=con.createStatement();
		Statement st2b=con.createStatement();
		Statement st3=con.createStatement();
		Statement st4=con.createStatement();
		Statement st5=con.createStatement();
		Statement st6=con.createStatement();
		
		ArrayList array1=new ArrayList();
		ArrayList array2=new ArrayList();
		
		String getLeaveBal="select * from lv_type_d where lv_empcode='"+empcode+"'";
		ResultSet rsLeave=st6.executeQuery(getLeaveBal);
		while(rsLeave.next())
		{
			int lv_type=rsLeave.getInt("lv_typeid");
			if(lv_type==1)
			{
				payslipForm.setCl(rsLeave.getString("lv_clbal"));
			}
			if(lv_type==2)
			{
				payslipForm.setSl(rsLeave.getString("lv_clbal"));
			}
			if(lv_type==3)
			{
				payslipForm.setEl(rsLeave.getString("lv_clbal"));
			}
		}
		
		
		
		
		String sql1="SELECT PAYH.PERNR,PAYH.RPT_MNG,PAYH.BANK_ACC_NO,PAYH.PR_MNTH AS PAYMNT," +
		"EMPO.EMP_FULLNAME as emp_name,DESG.DSGSTXT,DEPT.DPTSTXT,PAYH.PR_MNTH,COM.BUTXT as comaddr," +
		"COM.ORT01,BANK.BNAME,EMPO.DOJ,EMPO.ESINO,EMPO.PFNO,EMPO.PANNO,PAYH.WKG_DAYS,PAYH.WKD_DAYS,PAYH.LEV_DAYS," +
		"PAYH.LOP_DAYS,PAYH.TOT_EARN,PAYH.TOT_DEDN,PAYH.TOT_AEARN,PAYH.TOT_ADEDN,PAYH.NET_EARN FROM PAYSLIP_H as PAYH INNER JOIN " +
		"Company AS COM ON PAYH.COMP_CODE=COM.BUKRS INNER JOIN " +
		"Bank AS BANK ON PAYH.BANK_ID=BANK.BANKID INNER JOIN Designation AS DESG ON PAYH.DSG_ID=DESG.DSGID " +
		"INNER JOIN Department AS DEPT ON PAYH.DPT_ID=DEPT.DPTID INNER JOIN emp_official_info AS EMPO " +
		"ON PAYH.PERNR=EMPO.PERNR AND PAYH.PERNR='"+empcode+"' AND PAYH.PR_MNTH='"+month+"'";
		
		
		ResultSet rs1=st1.executeQuery(sql1);
		
		if (rs1.next()) {
			
			i=1;
			
			String rep_mng=rs1.getString("RPT_MNG");
					
		//WRITE CODE
			
			payslipForm.setComaddr(rs1.getString("comaddr"));
			payslipForm.setPaymnt(rs1.getString("PAYMNT"));
			payslipForm.setEmpcod(rs1.getString("PERNR"));
			
			payslipForm.setBnkname(rs1.getString("BNAME"));
			payslipForm.setEmpname(rs1.getString("emp_name"));
			payslipForm.setBaccno(rs1.getString("BANK_ACC_NO"));
			payslipForm.setPfno(rs1.getString("PFNO"));
			payslipForm.setEsino(rs1.getString("ESINO"));
			payslipForm.setDesg(rs1.getString("dsgstxt"));
			payslipForm.setPanno(rs1.getString("PANNO"));
			payslipForm.setDept(rs1.getString("DPTSTXT"));
			
			double noofday = rs1.getDouble("WKG_DAYS");
			double nooflop = rs1.getDouble("LOP_DAYS");
			double paydays=noofday-nooflop;
			
			payslipForm.setDaypayd(Double.toString(paydays));
			String doj=rs1.getString("DOJ");
			if(doj!=null)
			{
				String a[]=doj.split("-");
				doj=a[2]+"/"+a[1]+"/"+a[0];
			}
			
			payslipForm.setDoj(doj); 
			payslipForm.setWorgdays(rs1.getString("WKG_DAYS"));
			payslipForm.setDaywrkd(rs1.getString("WKD_DAYS"));
			payslipForm.setLop(rs1.getString("LOP_DAYS"));
			
			
			
			
			payslipForm.setArrears_totEarn(rs1.getString("TOT_AEARN"));
			payslipForm.setCurrent_totEarn(rs1.getString("TOT_EARN"));
			payslipForm.setArrears_totDeduc(rs1.getString("TOT_ADEDN"));
			payslipForm.setCurrent_totDeduc(rs1.getString("TOT_DEDN"));
			int totalPay=rs1.getInt("TOT_EARN")-rs1.getInt("TOT_DEDN");
			payslipForm.setNetpay(Integer.toString(totalPay));
			
			String sql2a="SELECT ADDR.address_line1,ADDR.address_line2,ADDR.address_line3 from emp_address AS ADDR " +
				"INNER JOIN  PAYSLIP_H AS PAYH ON PAYH.PERNR=ADDR.employee_no AND ADDR.address_type='Permanent' AND PAYH.PERNR='"+empcode+"'";
		
			ResultSet rs2a=st2a.executeQuery(sql2a);
		
			if (rs2a.next()) {
				
				//WRITE CODE
				payslipForm.setEmpaddr(rs2a.getString("address_line1")+" "+ rs2a.getString("address_line2")+" "+rs2a.getString("address_line3"));
			}
			
			else
			{
				String sql2b="SELECT ADDR.address_line1,ADDR.address_line2,ADDR.address_line3 from emp_address AS ADDR " +
				"INNER JOIN  PAYSLIP_H AS PAYH ON PAYH.PERNR=ADDR.employee_no AND ADDR.address_type='Temperory' AND PAYH.PERNR='"+empcode+"'";
		
			ResultSet rs2b=st2b.executeQuery(sql2b);
		
			if (rs2b.next()) {
				
				//WRITE CODE
				payslipForm.setEmpaddr(rs2b.getString("address_line1")+" "+rs2b.getString("address_line2")+" "+rs2b.getString("address_line3"));
			}
			}
			
			
			String sql3="select e.LOCID,l.LOCNAME,ess.ApproverId,emp.EMP_FULLNAME from emp_official_info as e,emp_official_info as emp,ESS_Approvers as ess," +
			"Location as l where e.PERNR='"+empcode+"' and ess.employeeNumber=e.PERNR and ess.essType='Leave' and  l.LOCATION_CODE=e.LOCID and emp.PERNR=ess.ApproverId";
			ResultSet rs3=st3.executeQuery(sql3);
			if (rs3.next()) {
			
			//WRITE CODE
				payslipForm.setRepmng(rs3.getString("EMP_FULLNAME"));
				payslipForm.setLocation(rs3.getString("LOCID")+"-"+rs3.getString("LOCNAME"));
			}
			
			String sql4="SELECT SAL.SAL_SNAME,SAL.SAL_TYPE,PAYD.SAL_AMT,PAYD.ARR_AMT FROM PAYSLIP_D AS PAYD INNER JOIN SALARY_M AS SAL ON PAYD.SAL_ID=SAL.SAL_ID WHERE PAYD.PERNR='"+empcode+"' AND PAYD.PR_MNTH='"+month+"' AND SAL.SAL_TYPE='I'";
	
			ResultSet rs4=st4.executeQuery(sql4);
			
			PayslipForm payslipForm1=null;
			
			if(rs4.next()) {
				payslipForm1=new PayslipForm();
				payslipForm1.setEh(rs4.getString("SAL_SNAME"));
				payslipForm1.setEa(rs4.getString("ARR_AMT"));
				payslipForm1.setEc(rs4.getString("SAL_AMT"));
				array1.add(payslipForm1);
				request.setAttribute("earnings", "earnings");
			}
			
			while (rs4.next()) {
				//WRITE CODE
				payslipForm1=new PayslipForm();
				payslipForm1.setEh(rs4.getString("SAL_SNAME"));
				payslipForm1.setEa(rs4.getString("ARR_AMT"));
				payslipForm1.setEc(rs4.getString("SAL_AMT"));
				array1.add(payslipForm1);
				
			}
			int earning=0;
			
			
			String sql5="SELECT SAL.SAL_SNAME,PAYD.SAL_AMT,PAYD.ARR_AMT FROM PAYSLIP_D AS PAYD INNER JOIN SALARY_M AS SAL ON PAYD.SAL_ID=SAL.SAL_ID WHERE PAYD.PERNR='"+empcode+"' AND PAYD.PR_MNTH='"+month+"' AND SAL.SAL_TYPE='D'";
			
			ResultSet rs5=st5.executeQuery(sql5);
			
			PayslipForm payslipForm2=null;
			if(rs5.next()) {
				payslipForm2=new PayslipForm();
				payslipForm2.setDh(rs5.getString("SAL_SNAME"));
				payslipForm2.setDa(rs5.getString("ARR_AMT"));
				payslipForm2.setDc(rs5.getString("SAL_AMT"));
				array2.add(payslipForm2);
				request.setAttribute("deduction", "deduction");
			}
			while (rs5.next()) {
				//WRITE CODE
				payslipForm2=new PayslipForm();
				payslipForm2.setDh(rs5.getString("SAL_SNAME"));
				payslipForm2.setDa(rs5.getString("ARR_AMT"));
				payslipForm2.setDc(rs5.getString("SAL_AMT"));
				array2.add(payslipForm2);
			
			}
			
			
			
			payslipForm.setMesage("");
			
		}
		
		else
		{
			payslipForm.setMesage("Payslip Details not found");
		}
		
		request.setAttribute("array1", array1);
		request.setAttribute("array2", array2);
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return i;
}

public int monthyear(HttpServletRequest request,PayslipForm payslipForm){
	
	Connection con = ConnectionFactory1.getConnection();
	ArrayList ar_id=new ArrayList();
	ArrayList ar_name=new ArrayList();
	
	int i=0;
	try {
		Statement st=con.createStatement();
		
		String sql="Select * from ATT_MONTH_YEAR order by ID DESC";
		
		ResultSet rs=st.executeQuery(sql);
		
		while (rs.next()) {
			
			ar_id.add(rs.getString("MONTH_SHORT_NAME"));
			ar_name.add(rs.getString("MONTH_LONG_NAME"));
		
		}
		
		payslipForm.setAr_id(ar_id);
		payslipForm.setAr_name(ar_name);
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return i;
}
}