package com.microlabs.ess.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.dao.SAPLeaveDao;
import com.microlabs.hr.form.EmpOfficalInformationForm;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SAP_EMP_OFF_BAPI {
	
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
	
	
	
	EssDao ad = EssDao.dBConnection();
	private static final String DESTINATION = "SAP_DESTINATION";
	private static Connection conn=null;
	static int ID=0;
	private void connectSAP() {
		try {
		
			Properties connectProperties = new Properties();// TODO change the
													// details
			connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,
					"192.168.1.2");
			connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");
			connectProperties
					.setProperty(DestinationDataProvider.JCO_CLIENT, "900");
			connectProperties.setProperty(DestinationDataProvider.JCO_USER,
					"91524");
			connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,
					"Micro@1");
			connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "EN");
			File destCfg = new File(DESTINATION + ".jcoDestination");
			
				FileOutputStream fos = new FileOutputStream(destCfg, false);
				connectProperties.store(fos, "SAP_DESTINATION config");
				fos.close();
		} catch (Exception e) {
			throw new RuntimeException("Unable to create the destination file",
					e);
		}
	}
	
	public static void main(String[] args) {
		
		
		SAP_EMP_OFF_BAPI abc=new SAP_EMP_OFF_BAPI();
		
	System.out.println("Started EMP_OFF_task");
	Date dNow1 = new Date( );
 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	String dateNow1 = ft1.format(dNow1);
	
	String tran="insert into Schedule_Transaction values('EMPOFFINFO','"+dateNow1+"')";
	int j=abc.ad.SqlExecuteUpdate(tran);
	
SAPLeaveDao dao=new SAPLeaveDao();				
JCoFunction function=null;
JCoTable a=null;


try {
	
	
	abc.connectSAP();
	JCoDestination destination = JCoDestinationManager
			.getDestination(DESTINATION);// TODO change to real
											// destination
	if (destination == null) {
		
		destination = JCoDestinationManager
				.getDestination(DESTINATION);// TODO change to
													// real
													// destination
		if (destination == null) {
			throw new RuntimeException(
					"Could not connect to SAP, destination not found.");
		}
	}
	
	 function = destination.getRepository().getFunction(
				"ZBAPI_HR_EMP_INFO");
	if (function == null) {
		throw new RuntimeException(" ZBAPI_HR_EMP_INFO not found in SAP.");//ZBAPI_HR_PAYSLIP
	}
	
	Date dNow = new Date( );
	SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
    String dateNow = ft.format(dNow);
    
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1); 
    String yestdate=dateFormat.format(cal.getTime());
	
	 
	
	function.execute(destination);
	LinkedList empoffList = new LinkedList();
	LinkedList updateempoffList = new LinkedList();
	JCoTable returnTable = function.getTableParameterList().getTable("ZHEMP_OFF_DATA");//PAYDATA_D_H
	
	if(returnTable.getNumRows() > 0){
		returnTable.firstRow();
		do{
			EmpOfficalInformationForm emp=new EmpOfficalInformationForm();
			EmpOfficalInformationForm updateemp=new EmpOfficalInformationForm();
			
			String empno=Integer.toString(returnTable.getInt("PERNR"));
			boolean update=false;
			String emp1="select * from emp_official_info where pernr='"+empno+"'";
			ResultSet rs11=abc.ad.selectQuery(emp1);
			if(rs11.next())					
			{		
				update=true;
			}
			rs11.close();
			abc.ad.connClose();
			if(update==false)
			{
			int id=0;
			emp.setEmployeeNumber(empno);
			JCoTable nametable = function.getTableParameterList().getTable("ZHEMP_PERS_DATA");
			if(nametable.getNumRows() > 0){
				nametable.firstRow();
				do{
				if(empno.equalsIgnoreCase(Integer.toString(nametable.getInt("PERNR"))))
						{
					      emp.setEmployeeName(nametable.getString("FNAME")+" "+nametable.getString("MNAME")+" "+nametable.getString("LNAME"));
					      String gender=nametable.getString("GESCH");
							if(gender.equalsIgnoreCase("1"))
							{
								emp.setGender("M");
							}
							else
							{
								emp.setGender("F");
							}
							//dob
							Date DOb=nametable.getDate("DOB");
							 ft1 = new SimpleDateFormat ("yyyy-MM-dd");
						    String dob = ft1.format(DOb);
						    emp.setDob(dob);
						    //title
						    String title=nametable.getString("TITLE");
						    if(title.equalsIgnoreCase("1"))
						    {
						    	emp.setTitle("Mr");
						    }
						    if(title.equalsIgnoreCase("2"))
						    {
						    	emp.setTitle("Mrs");
						    }
						    if(title.equalsIgnoreCase("3"))
						    {
						    	emp.setTitle("Miss");
						    }
						    if(title.equalsIgnoreCase("4"))
						    {
						    	emp.setTitle("Ms");
						    }
						    if(title.equalsIgnoreCase("5"))
						    {
						    	emp.setTitle("Late");
						    }
						    if(title.equalsIgnoreCase("6"))
						    {
						    	emp.setTitle("Dr");
						    }
						    
					      break;
						}
				
			    
				
			}while(nametable.nextRow());
				
			}
			
			String groupcode="";
			String leave1="select * from user_group where group_code='"+returnTable.getString("WERKS")+"'";					
			ResultSet rs1=abc.ad.selectQuery(leave1);
			while(rs1.next())					
			{		
				groupcode=rs1.getString("id");
			}
			rs1.close();
			abc.ad.connClose();
			emp.setUsergrp(groupcode);
			emp.setLocation(returnTable.getString("WERKS"));
			emp.setCompanyName("1000");
			emp.setDepartment(Integer.toString(returnTable.getInt("DPTID")));
			emp.setDesignation(Integer.toString(returnTable.getInt("DSGID")));
			emp.setApprovalManger(Integer.toString(returnTable.getInt("APPMGR")));
			emp.setReportingManger(Integer.toString(returnTable.getInt("RPTMGR")));
			emp.setPlant(returnTable.getString("WERKS"));
			emp.setCounID("IN");
			emp.setState(returnTable.getString("STATE"));
			
			Date DOJ=returnTable.getDate("DOJ");
			 ft1 = new SimpleDateFormat ("yyyy-MM-dd");
		    String doj = ft1.format(DOJ);
		    emp.setDateofJoining(doj);
		    
		    Date DOC=returnTable.getDate("DOC");
		    if(DOC!=null)
		    {
			SimpleDateFormat ft2 = new SimpleDateFormat ("yyyy-MM-dd");
		    String doc = ft2.format(DOC);
		    emp.setDateofConformation(doc);
		    }
		    
		    Date DOL=returnTable.getDate("DOL");
		    if(DOL!=null)
		    {
			SimpleDateFormat ft3 = new SimpleDateFormat ("yyyy-MM-dd");
		    String dol = ft3.format(DOL);
		    emp.setDateofLeaving(dol);
		    }
		    else
		    {
		    	 emp.setDateofLeaving("");
		    }
		    
		    String esi=returnTable.getString("ESI");
		    if(esi.equalsIgnoreCase("X"))
		    {
		    	emp.setEligibleforESIDeduction("Y");
		    	emp.setEsiNumber(returnTable.getString("ESINO"));
		    }
		    else
		    {
		    	emp.setEligibleforESIDeduction("N");
		    	emp.setEsiNumber("");				    	
		    }
		    
		    String pf=returnTable.getString("PF");
		    if(pf.equalsIgnoreCase("X"))
		    {
		    	emp.setEligibleforPFDeduction("Y");
		    	emp.setPfNumber(returnTable.getString("PFNO"));
		    }
		    else
		    {
		    	emp.setEligibleforPFDeduction("N");
		    	emp.setPfNumber("");				    	
		    }
		    String pt=returnTable.getString("PT");
		    if(pt.equalsIgnoreCase("X"))
		    {
		    	emp.setEligibleforPTDeduction("Y");
		    }
		    else
		    {
		    	emp.setEligibleforPTDeduction("N");
		    }
		    
		    String it=returnTable.getString("IT");
		    if(it.equalsIgnoreCase("X"))
		    {
		    	emp.setEligibleforITDeduction("Y");
		    }
		    else
		    {
		    	emp.setEligibleforITDeduction("N");
		    }
		    
		    String bonus=returnTable.getString("BONUS");
		    if(bonus.equalsIgnoreCase("X"))
		    {
		    	emp.setBonus("Y");
		    }
		    else
		    {
		    	emp.setBonus("N");
		    }
		    
		    
		    String leav=returnTable.getString("BONUS");
		    if(leav.equalsIgnoreCase("X"))
		    {
		    	emp.setLeaves("Y");
		    }
		    else
		    {
		    	emp.setLeaves("N");
		    }
		    
		    
		    emp.setPanNo(returnTable.getString("PANNO"));
		    emp.setPayGroup(Integer.toString(returnTable.getInt("PAYGROUP")));
		    emp.setEmployeeCategory(Integer.toString(returnTable.getInt("STAFFCAT")));
		    int grade=returnTable.getInt("GRDID");
		    if(grade==1)
		    {
		    	  emp.setGradeID("g1");
		    }
		    if(grade==2)
		    {
		    	  emp.setGradeID("g2");
		    }
		    if(grade==3)
		    {
		    	  emp.setGradeID("g3");
		    }
		    if(grade==4)
		    {
		    	  emp.setGradeID("g4");
		    }
		    if(grade==5)
		    {
		    	  emp.setGradeID("g5");
		    }				    


		  
		    emp.setPaymentMethod(returnTable.getString("PAYMODE"));
		    emp.setAccountType(returnTable.getString("BACCTYP"));
            emp.setAccountNumber(returnTable.getString("BACCNO"));	
            emp.setBankId(Integer.toString(returnTable.getInt("BANKID")));
            emp.setUanno(returnTable.getString("UAN"));
            emp.setBankName(returnTable.getString("BRANCH"));
            emp.setIfsCCode(returnTable.getString("IFSC_CODE"));
            emp.setMicrCode(returnTable.getString("MICR_CODE"));
            emp.setSalaryCurrency(returnTable.getString("WAERS"));
            
            System.out.println(returnTable.getString("SHTCODE"));
            
            emp.setShift_Code(returnTable.getString("SHTCODE"));
            emp.setRule_Code(returnTable.getString("RULECD"));
            emp.setSwipe_Count(Integer.toString(returnTable.getInt("SWIPECNT")));
            emp.setWork_id(Integer.toString(returnTable.getInt("LOCID")));
            
            emp.setRepgrp(Integer.toString(returnTable.getInt("REPGRP")));
            emp.setSdptid(Integer.toString(returnTable.getInt("SDPTID")));
            
            emp.setConId(Integer.toString(returnTable.getInt("LIFNR")));
            emp.setConSal(Integer.toString(returnTable.getInt("SALAMT")));
		
			empoffList.add(emp);
			}
			else
			{
				
			updateemp.setEmployeeNumber(empno);
			 System.out.println(empno);
			JCoTable nametable = function.getTableParameterList().getTable("ZHEMP_PERS_DATA");
			
			if(nametable.getNumRows() > 0){
				nametable.firstRow();
				do{
				if(empno.equalsIgnoreCase(Integer.toString(nametable.getInt("PERNR"))))
						{
					      updateemp.setEmployeeName(nametable.getString("FNAME")+" "+nametable.getString("MNAME")+" "+nametable.getString("LNAME"));
					      String gender=nametable.getString("GESCH");
							if(gender.equalsIgnoreCase("1"))
							{
								updateemp.setGender("M");
							}
							else
							{
								updateemp.setGender("F");
							}
							//dob
							Date DOb=nametable.getDate("DOB");
							 ft1 = new SimpleDateFormat ("yyyy-MM-dd");
						    String dob = ft1.format(DOb);
						    updateemp.setDob(dob);
						    //title
						    String title=nametable.getString("TITLE");
						    if(title.equalsIgnoreCase("1"))
						    {
						    	updateemp.setTitle("Mr");
						    }
						    if(title.equalsIgnoreCase("2"))
						    {
						    	updateemp.setTitle("Mrs");
						    }
						    if(title.equalsIgnoreCase("3"))
						    {
						    	updateemp.setTitle("Miss");
						    }
						    if(title.equalsIgnoreCase("4"))
						    {
						    	updateemp.setTitle("Ms");
						    }
						    if(title.equalsIgnoreCase("5"))
						    {
						    	updateemp.setTitle("Late");
						    }
						    if(title.equalsIgnoreCase("6"))
						    {
						    	updateemp.setTitle("Dr");
						    }
					      
						}
				
			    
				
			}while(nametable.nextRow());
				
			}
			
			String groupcode="";
			String leave1="select * from user_group where group_code='"+returnTable.getString("WERKS")+"'";					
			ResultSet rs1=abc.ad.selectQuery(leave1);
			while(rs1.next())					
			{		
				groupcode=rs1.getString("id");
			}
			rs1.close();
			abc.ad.closeResultset();
			abc.ad.connClose();
			updateemp.setUsergrp(groupcode);
			updateemp.setLocation(returnTable.getString("WERKS"));
			updateemp.setCompanyName("1000");
			updateemp.setDepartment(Integer.toString(returnTable.getInt("DPTID")));
			updateemp.setDesignation(Integer.toString(returnTable.getInt("DSGID")));
			updateemp.setApprovalManger(Integer.toString(returnTable.getInt("APPMGR")));
			updateemp.setReportingManger(Integer.toString(returnTable.getInt("RPTMGR")));
			updateemp.setPlant(returnTable.getString("WERKS"));
			updateemp.setCounID("IN");
			updateemp.setState(returnTable.getString("STATE"));
			
			Date DOJ=returnTable.getDate("DOJ");
			 ft1 = new SimpleDateFormat ("yyyy-MM-dd");
		    String doj = ft1.format(DOJ);
		    updateemp.setDateofJoining(doj);
		    
		    Date DOC=returnTable.getDate("DOC");
		    if(DOC!=null)
		    {
			SimpleDateFormat ft2 = new SimpleDateFormat ("yyyy-MM-dd");
		    String doc = ft2.format(DOC);
		    updateemp.setDateofConformation(doc);
		    }
		    
		    Date DOL=returnTable.getDate("DOL");
		    if(DOL!=null)
		    {
			SimpleDateFormat ft3 = new SimpleDateFormat ("yyyy-MM-dd");
		    String dol = ft3.format(DOL);
		    updateemp.setDateofLeaving(dol);
		    }
		    else
		    {
		    	 updateemp.setDateofLeaving("");
		    }
		    
		    String esi=returnTable.getString("ESI");
		    if(esi.equalsIgnoreCase("X"))
		    {
		    	updateemp.setEligibleforESIDeduction("Y");
		    	updateemp.setEsiNumber(returnTable.getString("ESINO"));
		    }
		    else
		    {
		    	updateemp.setEligibleforESIDeduction("N");
		    	updateemp.setEsiNumber("");				    	
		    }
		    
		    String pf=returnTable.getString("PF");
		    if(pf.equalsIgnoreCase("X"))
		    {
		    	updateemp.setEligibleforPFDeduction("Y");
		    	updateemp.setPfNumber(returnTable.getString("PFNO"));
		    }
		    else
		    {
		    	updateemp.setEligibleforPFDeduction("N");
		    	updateemp.setPfNumber("");				    	
		    }
		    String pt=returnTable.getString("PT");
		    if(pt.equalsIgnoreCase("X"))
		    {
		    	updateemp.setEligibleforPTDeduction("Y");
		    }
		    else
		    {
		    	updateemp.setEligibleforPTDeduction("N");
		    }
		    
		    String it=returnTable.getString("IT");
		    if(it.equalsIgnoreCase("X"))
		    {
		    	updateemp.setEligibleforITDeduction("Y");
		    }
		    else
		    {
		    	updateemp.setEligibleforITDeduction("N");
		    }
		    
		    String bonus=returnTable.getString("BONUS");
		    if(bonus.equalsIgnoreCase("X"))
		    {
		    	updateemp.setBonus("Y");
		    }
		    else
		    {
		    	updateemp.setBonus("N");
		    }
		    
		    
		    String leav=returnTable.getString("BONUS");
		    if(leav.equalsIgnoreCase("X"))
		    {
		    	updateemp.setLeaves("Y");
		    }
		    else
		    {
		    	updateemp.setLeaves("N");
		    }
		    
		    
		    updateemp.setPanNo(returnTable.getString("PANNO"));
		    updateemp.setPayGroup(Integer.toString(returnTable.getInt("PAYGROUP")));
		    updateemp.setEmployeeCategory(Integer.toString(returnTable.getInt("STAFFCAT")));
		    int grade=returnTable.getInt("GRDID");
		    if(grade==1||grade==0)
		    {
		    	  updateemp.setGradeID("g1");
		    }
		    if(grade==2)
		    {
		    	  updateemp.setGradeID("g2");
		    }
		    if(grade==3)
		    {
		    	  updateemp.setGradeID("g3");
		    }
		    if(grade==4)
		    {
		    	  updateemp.setGradeID("g4");
		    }
		    if(grade==5)
		    {
		    	  updateemp.setGradeID("g5");
		    }				    


		  
		    updateemp.setPaymentMethod(returnTable.getString("PAYMODE"));
		    updateemp.setAccountType(returnTable.getString("BACCTYP"));
            updateemp.setAccountNumber(returnTable.getString("BACCNO"));	
            updateemp.setBankId(Integer.toString(returnTable.getInt("BANKID")));
            updateemp.setUanno(returnTable.getString("UAN"));
            updateemp.setBankName(returnTable.getString("BRANCH"));
            updateemp.setIfsCCode(returnTable.getString("IFSC_CODE"));
            updateemp.setMicrCode(returnTable.getString("MICR_CODE"));
            updateemp.setSalaryCurrency(returnTable.getString("WAERS"));
            
            System.out.println(returnTable.getString("SHTCODE"));
            
            updateemp.setShift_Code(returnTable.getString("SHTCODE"));
            updateemp.setRule_Code(returnTable.getString("RULECD"));
            updateemp.setSwipe_Count(Integer.toString(returnTable.getInt("SWIPECNT")));
            updateemp.setWork_id(Integer.toString(returnTable.getInt("LOCID")));
            
            updateemp.setRepgrp(Integer.toString(returnTable.getInt("REPGRP")));
            updateemp.setSdptid(Integer.toString(returnTable.getInt("SDPTID")));
            
            updateemp.setConId(Integer.toString(returnTable.getInt("LIFNR")));
            updateemp.setConSal(Integer.toString(returnTable.getInt("SALAMT")));
            
            updateempoffList.add(updateemp);
            
			}
			
		}while(returnTable.nextRow());

		System.out.println(empoffList.size());
		System.out.println(updateempoffList.size());
updateempoffDetails(empoffList,updateempoffList);
	
	}

	
} catch (AbapException e) {
	System.out.println(e.toString());// TODO change to log
	StringWriter errors = new StringWriter();
	e.printStackTrace(new PrintWriter(errors));			
	Date d=new Date();
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	String dateNow = ft.format(d);
	String error="insert into ERROR_DETAILS values('EMP_OFF','"+dateNow+"','"+errors.toString()+"')";
	int i= abc.ad.SqlExecuteUpdate(error);
} catch (JCoException e) {
	System.out.println(e.toString());// TODO change to log
	System.out.println(e.getMessageType());
	StringWriter errors = new StringWriter();
	e.printStackTrace(new PrintWriter(errors));			
	Date d=new Date();
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	String dateNow = ft.format(d);
	String error="insert into ERROR_DETAILS values('EMP_OFF','"+dateNow+"','"+errors.toString()+"')";
	int i= abc.ad.SqlExecuteUpdate(error);
	//e.printStackTrace();
	//System.out.println(function.getException("");
	
}catch(Throwable e){
	System.out.println(e.toString());// TODO change to log
	e.printStackTrace();
	StringWriter errors = new StringWriter();
	e.printStackTrace(new PrintWriter(errors));			
	Date d=new Date();
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	String dateNow = ft.format(d);
	String error="insert into ERROR_DETAILS values('EMP_OFF','"+dateNow+"','"+errors.toString()+"')";
	int i= abc.ad.SqlExecuteUpdate(error);
} finally {
	//rs.close();
}



System.out.println("Ended EMP_OFF_task");

	}
	
public static int[] updateempoffDetails(LinkedList insertList,LinkedList updateList){
	
	SAP_EMP_OFF_BAPI abc=new SAP_EMP_OFF_BAPI();
		
		try {
			 if(conn==null||conn.isClosed())
			 {
				 conn=ConnectionFactory.getConnection();
			 }
			PreparedStatement st=conn.prepareStatement("insert into emp_official_info( ID,PERNR,Emp_FullName,Sex,WERKS,BUKRS,DPTID,DSGID,APPMGR,RPTMGR,LOCID,LAND1,STATE,DOJ,DOC,DOL,"
					+ "ESI,ESINO,PF,PFNO,PANNO,pay_group,STAFFCAT,GRDID,payment_method,WAERS,BACCTYP,BACCNO,BANKID,BRANCH,IFSC_CODE,MICR_CODE,DOB,TITLE,UANNO,ACTIVE,PT,IT,BONUS,LEAVES,EMAIL_ID,SAP_Status,SAP_Approved_Date,Shift_Code,Rule_Code,Work_ID,Reporting_Grp,SDPTID,Con_ID,Con_SAL)"
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			PreparedStatement st1=conn.prepareStatement("insert into users( username,password,count,activated,usr_type,group_id,status,employeenumber,loginCount,Emp_Photo,SAP_Status,SAP_Approved_Date)"
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?)");
			
			PreparedStatement st2=conn.prepareStatement("update emp_official_info set WERKS=?,pay_group=?,STAFFCAT=?, RPTMGR=?,APPMGR=?,LAND1=?,STATE=?,LOCID=?,GRDID=?,DSGID=?,DPTID=?,"
			+ "ESI=?,ESINO=?,PF=?,PFNO=?,PT=?,IT=?,PANNO=?,BONUS =?,LEAVES =?,WAERS =?,payment_method =?,BANKID =?,BACCTYP =?,BACCNO =?,BRANCH =?,IFSC_CODE =?,MICR_CODE  =?,UANNO=?,SAP_Status=?,SAP_Approved_Date=?,DOL=?,ACTIVE=?,Work_ID=?,Reporting_Grp=?,SDPTID=?,Con_ID=?,Con_SAL=? Where pernr=? ");
			
			
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
				
			/*	st2.setString(34,a.getShift_Code());	
				st2.setString(35,a.getRule_Code());*/	
				
				st2.setString(34,a.getWork_id());
				
				st2.setString(35,a.getRepgrp());	
				st2.setString(36,a.getSdptid());
				st2.setString(37,a.getConId());	
				st2.setString(38,a.getConSal());
				
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
				ResultSet rs=abc.ad.selectQuery(leave);
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
				st.setString(49,a.getConId());	
				st.setString(50,a.getConSal());
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
				int i= abc.ad.SqlExecuteUpdate(error);
			}
			return null; 
		
		
	}
	}


