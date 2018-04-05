package com.microlabs.ess.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import com.microlabs.db.AttConnectionFactory;
import com.microlabs.ess.dao.AttDao;
import com.microlabs.ess.form.PayslipRequestForm;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class PAYSLIP_REQUEST_BAPI {
	 static 	AttDao ad=new AttDao();
	private static final String DESTINATION = "SAP_DESTINATION";
	private static Connection conn=null;
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
		
		//EMP_DOL recors
		PAYSLIP_REQUEST_BAPI abc=new PAYSLIP_REQUEST_BAPI();
				try {
					
					
					
					
					System.out.println("Started PAYLSIP_REQUEST_TASK");
					Date dNow1 = new Date( );
				 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					String dateNow1 = ft1.format(dNow1);
					
					String tran="insert into Schedule_Transaction values('PAYLSIP_REQUEST_TASK','"+dateNow1+"')";
					int j=abc.ad.SqlExecuteUpdate(tran);
					
			   			
				JCoFunction function=null;
				JCoFunction function1=null;
				JCoTable a=null;
				
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
								"ZRFC_HR_PAYSLIP");
					if (function == null) {
						throw new RuntimeException("ZRFC_HR_PAYSLIP not found in SAP.");//ZBAPI_HR_PAYSLIP
					}
						LinkedList empoffList = new LinkedList();
					

				String bpd=" select locid,PAYSLIP_REQUEST.*, replace(convert(date,DATEADD(s,-1,DATEADD(mm, DATEDIFF(m,0,convert(nvarchar(10),year)+'-'+convert(nvarchar(10),Month)+'-'+'01')+1,0))),'-','')LastDay_CurrentMonth from PAYSLIP_REQUEST,emp_official_info where  PAYSLIP_REQUEST.Req_By=pernr and  sap_sent_flag=0 and req_type='Payslip' " ;
				ResultSet dd=abc.ad.selectQuery(bpd);
				while(dd.next()) { 
					
					//update statusflag
					String b="update PAYSLIP_REQUEST set sap_sent_flag=1 where Req_id='"+dd.getString("Req_id")+"'";
					int o=ad.SqlExecuteUpdate(b);
					 JCoTable passTable = function.getTableParameterList().getTable(
						 "SAL_SLIP"); 
				 
						 passTable.appendRow();
						 passTable.setValue("PERNR",dd.getString("Req_By"));
						 passTable.setValue("REQNO",dd.getString("Req_id"));
						 passTable.setValue("PRMNTH",dd.getString("LastDay_CurrentMonth"));
						 passTable.setValue("PLANT",dd.getString("locid"));
						
				}
				function.execute(destination);
					
					JCoTable returnTable = function.getTableParameterList().getTable("SAL_RETURN");
					System.out.println("SAP LIST :" +returnTable.getNumRows());
					if(returnTable.getNumRows() > 0){
						returnTable.firstRow();
						do{
							
							PayslipRequestForm emp=new PayslipRequestForm();					
							emp.setReq_by(returnTable.getInt("PERNR"));		
							emp.setMessage(returnTable.getString("MESSAGE"));
							emp.setReq_id(returnTable.getInt("REQNO"));		
		                    
		                	empoffList.add(emp);
				
							//returnMap.put(returnTable.getInt("REQ_NO"),returnTable.getChar("TYPE"));
						}while(returnTable.nextRow());
					}
					
					
					
					updatePaylsipDetails(empoffList);
					
					
					///form 16
					
					 function1 = destination.getRepository().getFunction(
								"ZRFC_HR_FORM16");
					if (function1 == null) {
						throw new RuntimeException("ZRFC_HR_FORM16 not found in SAP.");//ZBAPI_HR_PAYSLIP
					}
					
					LinkedList formList = new LinkedList();
					

					String bpd1=" select locid,PAYSLIP_REQUEST.* from PAYSLIP_REQUEST,emp_official_info where  PAYSLIP_REQUEST.Req_By=pernr and  sap_sent_flag=0 and req_type='Form16'  " ;
					ResultSet dd1=abc.ad.selectQuery(bpd1);
					while(dd1.next()) { 
						
						//update statusflag
						String b="update PAYSLIP_REQUEST set sap_sent_flag=1 where Req_id='"+dd1.getString("Req_id")+"'";
						int o=ad.SqlExecuteUpdate(b);
						
						 JCoTable passTable = function1.getTableParameterList().getTable(
							 "EMP_FORM16"); 
					 
							 passTable.appendRow();
							 passTable.setValue("PERNR",dd1.getString("Req_By"));
							 passTable.setValue("REQNO",dd1.getString("Req_id"));
							 passTable.setValue("FYEAR",dd1.getString("Year"));
							 passTable.setValue("PLANT",dd1.getString("locid"));
							
					}
								
				      
				
						
						function1.execute(destination);
						
						JCoTable returnTable1 = function1.getTableParameterList().getTable("FORM16_RET");
						System.out.println("SAP LIST :" +returnTable1.getNumRows());
						if(returnTable1.getNumRows() > 0){
							returnTable1.firstRow();
							do{
								
								PayslipRequestForm emp=new PayslipRequestForm();					
								emp.setReq_by(returnTable1.getInt("PERNR"));		
								emp.setMessage(returnTable1.getString("MESSAGE"));
								emp.setReq_id(returnTable1.getInt("REQNO"));		
			                    
			                	formList.add(emp);
					
								//returnMap.put(returnTable.getInt("REQ_NO"),returnTable.getChar("TYPE"));
							}while(returnTable1.nextRow());
						}
						
						
						
						updateFormDetails(formList);
					
					
					
				} catch (AbapException e) {
					System.out.println(e.toString());// TODO change to log
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));			
					Date d=new Date();
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					String dateNow = ft.format(d);
					String error="insert into ERROR_DETAILS values('PAYLSIP_REQUEST_TASK','"+dateNow+"','"+errors.toString()+"')";
					int i= abc.ad.SqlExecuteUpdate(error);
				} catch (JCoException e) {
					System.out.println(e.toString());// TODO change to log
					System.out.println(e.getMessageType());
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));			
					Date d=new Date();
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					String dateNow = ft.format(d);
					String error="insert into ERROR_DETAILS values('PAYLSIP_REQUEST_TASK','"+dateNow+"','"+errors.toString()+"')";
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
					String error="insert into ERROR_DETAILS values('PAYLSIP_REQUEST_TASK','"+dateNow+"','"+errors.toString()+"')";
					int i= abc.ad.SqlExecuteUpdate(error);
				} finally {
					System.out.println("ended PAYLSIP_REQUEST_TASK");
				}
	}
	
	public static int[] updateFormDetails(LinkedList attdList){
		  System.out.println("Update LIST :"+attdList.size());
		PAYSLIP_REQUEST_BAPI abc=new PAYSLIP_REQUEST_BAPI();
		 try {
			 if(conn==null||conn.isClosed())
			 {
				 conn=AttConnectionFactory.getConnection();
			 }
			
						
				PreparedStatement stusers=conn.prepareStatement("update PAYSLIP_REQUEST set sap_sent_flag='1',SAP_Status=?,status=?,SAP_Approved_Date=getdate() Where req_id=? ");
		
			Iterator attdItr = attdList.iterator();
			while(attdItr.hasNext()) {

				PayslipRequestForm a=(PayslipRequestForm)attdItr.next();
				stusers.setString(1,a.getMessage());
				if(a.getMessage().equalsIgnoreCase("Success"))
				stusers.setString(2,"Mail Sent");
				else
				stusers.setString(2,"Mail Not Sent");	
				stusers.setInt(3,a.getReq_id());
				stusers.addBatch();
			}
			if(!attdList.isEmpty()){	
				
				return stusers.executeBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();// TODO Change to log
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));			
			Date d=new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String dateNow = ft.format(d);
			String error="insert into ERROR_DETAILS values('FORM16','"+dateNow+"','"+errors.toString()+"')";
			int i=abc.ad.SqlExecuteUpdate(error);
		}
		return null; 
	}
	
	
	public static int[] updatePaylsipDetails(LinkedList attdList){
		  System.out.println("Update LIST :"+attdList.size());
		PAYSLIP_REQUEST_BAPI abc=new PAYSLIP_REQUEST_BAPI();
		 try {
			 if(conn==null||conn.isClosed())
			 {
				 conn=AttConnectionFactory.getConnection();
			 }
			
						
				PreparedStatement stusers=conn.prepareStatement("update PAYSLIP_REQUEST set sap_sent_flag='1',SAP_Status=?,status=?,SAP_Approved_Date=getdate() Where req_id=? ");
		
			Iterator attdItr = attdList.iterator();
			while(attdItr.hasNext()) {

				PayslipRequestForm a=(PayslipRequestForm)attdItr.next();
				stusers.setString(1,a.getMessage());
				if(a.getMessage().equalsIgnoreCase("Success"))
				stusers.setString(2,"Mail Sent");
				else
				stusers.setString(2,"Mail Not Sent");	
				stusers.setInt(3,a.getReq_id());
				stusers.addBatch();
			}
			if(!attdList.isEmpty()){	
				
				return stusers.executeBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();// TODO Change to log
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));			
			Date d=new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String dateNow = ft.format(d);
			String error="insert into ERROR_DETAILS values('PAYSLIP','"+dateNow+"','"+errors.toString()+"')";
			int i=abc.ad.SqlExecuteUpdate(error);
		}
		return null; 
	}
	}


