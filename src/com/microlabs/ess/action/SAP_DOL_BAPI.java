package com.microlabs.ess.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import com.microlabs.db.AttConnectionFactory;
import com.microlabs.ess.dao.AttDao;
import com.microlabs.ess.dao.SAPLeaveDao;
import com.microlabs.hr.form.EmpOfficalInformationForm;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SAP_DOL_BAPI {
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
		SAP_DOL_BAPI abc=new SAP_DOL_BAPI();
				try {
					
					
					
					
					System.out.println("Started EMP_DOL_task");
					Date dNow1 = new Date( );
				 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					String dateNow1 = ft1.format(dNow1);
					
					String tran="insert into Schedule_Transaction values('EMP_DOL_task','"+dateNow1+"')";
					int j=abc.ad.SqlExecuteUpdate(tran);
					
						
				JCoFunction function=null;
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
								"ZHRFC_EMP_DOL");
					if (function == null) {
						throw new RuntimeException("ZHRFC_EMP_DOL not found in SAP.");//ZBAPI_HR_PAYSLIP
					}
					
					
					 
					
				
					LinkedList empoffList = new LinkedList();
					


					Calendar aCalendar = Calendar.getInstance();
					aCalendar.set(Calendar.DATE, 1);
					aCalendar.add(Calendar.DAY_OF_MONTH, -1);
					Date lastDateOfPreviousMonth = aCalendar.getTime();
					aCalendar.set(Calendar.DATE, 1);
					Date firstDateOfPreviousMonth = aCalendar.getTime();
			
					SimpleDateFormat gt = new SimpleDateFormat ("yyyyMMdd");
					
					String prevlastday = gt.format(lastDateOfPreviousMonth);
					String prevfrstday = gt.format(firstDateOfPreviousMonth);
					/*prevfrstday="20160601";
					prevlastday="20160930";*/
					
					function.getImportParameterList().setValue("STARTDT", prevfrstday);
				    function.getImportParameterList().setValue("ENDDT", prevlastday);
							
			      
			
					
					function.execute(destination);
					
					JCoTable returnTable = function.getTableParameterList().getTable("ZHEMP_DOL");
					
					if(returnTable.getNumRows() > 0){
						returnTable.firstRow();
						do{
							
							EmpOfficalInformationForm emp=new EmpOfficalInformationForm();					
							emp.setEmployeeNumber(Integer.toString(returnTable.getInt("PERNR")));					
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
		               
		                    
		                	empoffList.add(emp);
				
							//returnMap.put(returnTable.getInt("REQ_NO"),returnTable.getChar("TYPE"));
						}while(returnTable.nextRow());
					}
					
					
					
					updateDOLDetails(empoffList);
					
				} catch (AbapException e) {
					System.out.println(e.toString());// TODO change to log
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));			
					Date d=new Date();
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					String dateNow = ft.format(d);
					String error="insert into ERROR_DETAILS values('EMP_DOL_task','"+dateNow+"','"+errors.toString()+"')";
					int i= abc.ad.SqlExecuteUpdate(error);
				} catch (JCoException e) {
					System.out.println(e.toString());// TODO change to log
					System.out.println(e.getMessageType());
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));			
					Date d=new Date();
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					String dateNow = ft.format(d);
					String error="insert into ERROR_DETAILS values('EMP_DOL_task','"+dateNow+"','"+errors.toString()+"')";
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
					String error="insert into ERROR_DETAILS values('EMP_DOL_task','"+dateNow+"','"+errors.toString()+"')";
					int i= abc.ad.SqlExecuteUpdate(error);
				} finally {
					System.out.println("ended EMP_DOL_task");
				}
	}
	
	public static int[] updateDOLDetails(LinkedList attdList){
		SAP_DOL_BAPI abc=new SAP_DOL_BAPI();
		 try {
			 if(conn==null||conn.isClosed())
			 {
				 conn=AttConnectionFactory.getConnection();
			 }
				PreparedStatement st2=conn.prepareStatement("update emp_official_info set DOL=?,active=0,SAP_Status=?,SAP_Approved_Date=? Where pernr=? ");
						
				PreparedStatement stusers=conn.prepareStatement("update users set activated='Off',SAP_Status=?,SAP_Approved_Date=? Where employeenumber=? ");
		
			Iterator attdItr = attdList.iterator();
			while(attdItr.hasNext()) {

				EmpOfficalInformationForm a=(EmpOfficalInformationForm)attdItr.next();
			
				st2.setString(1,a.getDateofLeaving());
				st2.setString(2,"DOL");
				Date dNow = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String submitDate = ft.format(dNow);
				st2.setString(3, submitDate);		
				st2.setString(4,a.getEmployeeNumber());				
				st2.addBatch();
				
			
			
				
				stusers.setString(1,"DOL");		
				stusers.setString(2, submitDate);		
				stusers.setString(3,a.getEmployeeNumber());
				stusers.addBatch();
			
				
			}
			if(!attdList.isEmpty()){	
				stusers.executeBatch();
				return st2.executeBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();// TODO Change to log
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));			
			Date d=new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String dateNow = ft.format(d);
			String error="insert into ERROR_DETAILS values('EMP_DOL','"+dateNow+"','"+errors.toString()+"')";
			int i=abc.ad.SqlExecuteUpdate(error);
		}
		return null; 
	}
	}


