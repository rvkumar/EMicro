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

import com.microlabs.db.AttConnectionFactory;
import com.microlabs.ess.dao.AttDao;
import com.microlabs.ess.form.BarcodeForm;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class BARCODE_BAPI {
	
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
	
	
	
    static 	AttDao ad=new AttDao();
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
		
		
		BARCODE_BAPI abc=new BARCODE_BAPI();
		
	System.out.println("Started Barcode");
	Date dNow1 = new Date( );
 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	String dateNow1 = ft1.format(dNow1);
	
	String tran="insert into Schedule_Transaction values('Postal_Barcode','"+dateNow1+"')";
	int j=abc.ad.SqlExecuteUpdate(tran);
	
				
		JCoFunction function=null;
		JCoFunction function1=null;
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
	
	 function = destination.getRepository().getFunction("ZBAPI_POSTAL_GRN");
	if (function == null) {
		throw new RuntimeException(" zbapi_postal_grn not found in SAP.");//ZBAPI_HR_PAYSLIP
	}
	
	
	 function1 = destination.getRepository().getFunction("ZBAPI_POSTAL_GRN");
	if (function1 == null) {
		throw new RuntimeException(" zbapi_postal_grn not found in SAP.");//ZBAPI_HR_PAYSLIP
	}
	
	Date dNow = new Date( );
	SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
    String dateNow = ft.format(dNow);
    
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    String today=dateFormat.format(cal.getTime());
    
	 //request for barcode data
	
	LinkedList barcodelist = new LinkedList();
	//JCoTable passtable = function.getTableParameterList().getTable("ZMANUALRR");//PAYDATA_D_H
	function.getImportParameterList().setValue("FBUDAT","20150511");
	function.getImportParameterList().setValue("TBUDAT",today);
	function.getImportParameterList().setValue("RECEIVE","X");
	try {
		
		function.execute(destination);
		
	} catch (JCoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace(); 
	}
	
	// barcode data retreive
	JCoTable returnTable = function.getTableParameterList().getTable("ZMANUALRR");//PAYDATA_D_H
	
	
	if(returnTable.getNumRows() > 0)
	{
		returnTable.firstRow();
		do{
			
		BarcodeForm help = new BarcodeForm();
		
		help.setmBLNR(returnTable.getString("MBLNR"));
		help.setmJAHR(returnTable.getString("MJAHR"));
		help.setlOEKZ(returnTable.getString("LOEKZ"));
		
	    Date DOL=returnTable.getDate("BLDAT");
	    if(DOL!=null)
	    {
		SimpleDateFormat ft3 = new SimpleDateFormat ("yyyy-MM-dd");
	    String dol = ft3.format(DOL);
	    help.setbLDAT(returnTable.getString("BLDAT"));
	    }
	    
	    DOL=returnTable.getDate("BLDAT");
	    if(DOL!=null)
	    {
		SimpleDateFormat ft3 = new SimpleDateFormat ("yyyy-MM-dd");
	    String dol = ft3.format(DOL);
	    help.setbUDAT(returnTable.getString("BUDAT"));
	    }
	    
		help.setwERKS(returnTable.getString("WERKS"));
		help.setmTART(returnTable.getString("MTART"));
		help.seteKGRP(returnTable.getString("EKGRP"));
		help.setxBLNR(returnTable.getString("XBLNR"));
		help.seteBELN(returnTable.getString("EBELN"));
		help.setlIFNR(returnTable.getString("LIFNR"));
		help.setxSTBW(returnTable.getString("XSTBW"));
		help.setbWART(returnTable.getString("BWART"));
		help.setbSART(returnTable.getString("BSART"));
		help.setdDMBTR(returnTable.getString("DMBTR"));
		help.setbBKTXT(returnTable.getString("BKTXT"));
		help.setsENT_DATE(returnTable.getString("SENT_DATE"));
		help.setsSENT_BY(returnTable.getString("SENT_BY"));
		help.setdCKET_NO(returnTable.getString("DOCKET_NO"));
		help.setcNAME(returnTable.getString("COURIER_NAME"));
		help.setpD_DT(returnTable.getString("PRECD_DT"));
		help.setName1(returnTable.getString("NAME1"));
		help.setoORT01(returnTable.getString("ORT01"));

		barcodelist.add(help);	
		}while(returnTable.nextRow());
		

		System.out.println(barcodelist.size());
        updateempoffDetails(barcodelist);
        
        ///delete  flag update of duplicate codes
        String g=" update Barcode set deleted =1 where id in "
        		+ " ( select min(id)  from Barcode where deleted=0 "
        		+ " group by MBLNR having count(MBLNR) >1) ";
        int kk=ad.SqlExecuteUpdate(g);
        		 
	
	}
	
	
	
	
	//sending status 
	
	
    String leave=
   		  "  select PRECD_DT ,MJAHR, MBLNR,convert(nvarchar(10),SAP_accepted_date,112) as SAP_accepted_date"
   		  +" from  barcode where convert(date,getdate()) =convert(date,SAP_accepted_date) and ( sap_sent_flag!=1  or sap_sent_flag is null)";
   		  
   		  ResultSet rs=abc.ad.selectQuery(leave); 
   		  while(rs.next())
   		  { 
    	  JCoTable passTable = function1.getTableParameterList().getTable("ZMBLNR");
   		  passTable.appendRow();
   		  	System.out.println(rs.getString("SAP_accepted_date"));
   		  	
   		  	passTable.setValue("MBLNR",rs.getString("MBLNR"));
   		  	passTable.setValue("MJAHR",rs.getString("MJAHR"));  
   		  	passTable.setValue("PRECD_DT",rs.getString("SAP_accepted_date"));
   		  
   		  }
   		int count1= returnTable.getNumRows();
   		String barcode_count="insert into Barcode_log values('Postal_Barcode','"+dateNow+"','"+count1+"')";
   		int r= abc.ad.SqlExecuteUpdate(barcode_count);
   		
   		function1.getImportParameterList().setValue("SEND","X");
   	    function1.execute(destination);
   		
   	    
   		
   		String update ="Update barcode set sap_sent_flag = 1 ,sap_sent_date=getdate()  where convert(date,getdate()) =convert(date,accepted_date) ";
   		int i = abc.ad.SqlExecuteUpdate(update);

	
		} catch (AbapException e) {
			System.out.println(e.toString());// TODO change to log
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));			
			Date d=new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String dateNow = ft.format(d);
			String error="insert into ERROR_DETAILS values('ESS_Postal','"+dateNow+"','"+errors.toString()+"')";
			int i= abc.ad.SqlExecuteUpdate(error);
		} catch (JCoException e) {
			System.out.println(e.toString());// TODO change to log
			System.out.println(e.getMessageType());
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));			
			Date d=new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String dateNow = ft.format(d);
			String error="insert into ERROR_DETAILS values('ESS_Postal','"+dateNow+"','"+errors.toString()+"')";
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
			String error="insert into ERROR_DETAILS values('ESS_Postal','"+dateNow+"','"+errors.toString()+"')";
			int i= abc.ad.SqlExecuteUpdate(error);
		} finally {
			//rs.close();
		}


		
		


System.out.println("Ended Barcode Task");

	}
	
public static int[] updateempoffDetails(LinkedList insertList){
	
	BARCODE_BAPI abc=new BARCODE_BAPI();
		
		try {
			
			Date dNow = new Date( );
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
		    String dateNow = ft.format(dNow);
			 if(conn==null||conn.isClosed()) 
			 {
				 conn=AttConnectionFactory.getConnection();
			 }
			PreparedStatement st=conn.prepareStatement("insert into Barcode(MBLNR,	MJAHR,	LOEKZ,	BLDAT,	BUDAT,	WERKS,	MTART,	EKGRP,	XBLNR,	EBELN,	LIFNR,	XSTBW,	BWART,	BSART,	DMBTR,	BKTXT,	SENT_DATE,	SENT_BY,	DOCKET_NO,	COURIER_NAME,	PRECD_DT,	NAME1,	ORT01,SAP_accepted_date)"
					+ "values (?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,?)");
			
			
			Iterator attdItr = insertList.iterator();
			
			while(attdItr.hasNext()) {
				BarcodeForm a=(BarcodeForm)attdItr.next();
				
				st.setString(1,a.getmBLNR());
				st.setString(2,a.getmJAHR());
				st.setString(3,a.getlOEKZ());
				st.setString(4,a.getbLDAT());
				st.setString(5,a.getbUDAT());
				st.setString(6,a.getwERKS());
				st.setString(7,a.getmTART());
				st.setString(8,a.geteKGRP());
				st.setString(9,a.getxBLNR());
				st.setString(10,a.geteBELN());
				st.setString(11,a.getlIFNR());
				st.setString(12,a.getxSTBW());
				st.setString(13,a.getbWART());
				st.setString(14,a.getbSART());
				st.setString(15,a.getdDMBTR());
				st.setString(16,a.getbBKTXT());
				st.setString(17,a.getsENT_DATE());
				st.setString(18,a.getsSENT_BY());
				st.setString(19,a.getdCKET_NO());
				st.setString(20,a.getcNAME());
				st.setString(21,a.getpD_DT());
				st.setString(22,a.getName1());
				st.setString(23,a.getoORT01());
				st.setString(24,dateNow);

				st.addBatch();
			}
			
			st.executeBatch();
			
			
				
				
				
		} catch (SQLException e) {
				e.printStackTrace();// TODO Change to log
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));			
				Date d=new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				String dateNow = ft.format(d);
				String error="insert into ERROR_DETAILS values('ESS_Postal','"+dateNow+"','"+errors.toString()+"')";
				int i= abc.ad.SqlExecuteUpdate(error);
			}
			return null; 
		
		
	}
	}


