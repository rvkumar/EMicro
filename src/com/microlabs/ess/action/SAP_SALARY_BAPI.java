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
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.AttDao;
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

public class SAP_SALARY_BAPI {
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
		
		//emp_SalaryData
		SAP_SALARY_BAPI abc=new SAP_SALARY_BAPI();
				try {
					
					
				
					
					
					
					System.out.println("Started EMP_Salary_task");
					Date dNow1 = new Date( );
				 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					String dateNow1 = ft1.format(dNow1);
					
					String tran="insert into Schedule_Transaction values('EMP_Salary_task','"+dateNow1+"')";
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
								"ZHRFC_SALARY_HEADER");
					if (function == null) {
						throw new RuntimeException("ZHRFC_SALARY_HEADER not found in SAP.");//ZBAPI_HR_PAYSLIP
					}
					
					
					 
					
				
					LinkedList empoffList = new LinkedList();
					LinkedList updateempoffList = new LinkedList();
					
					Calendar aCalendar = Calendar.getInstance();
					aCalendar.set(Calendar.DATE, 1);
					aCalendar.add(Calendar.DAY_OF_MONTH, -1);
					Date lastDateOfPreviousMonth = aCalendar.getTime();
					SimpleDateFormat gt = new SimpleDateFormat ("yyyyMMdd");
					String prevlastday = gt.format(lastDateOfPreviousMonth);
					 //prevlastday = "20150701";
					

				    function.getImportParameterList().setValue("PRMNTH", prevlastday);
							
			      
			
					
					function.execute(destination);
					
					JCoTable returnTable = function.getTableParameterList().getTable("ZSHSALM_H");
					
					if(returnTable.getNumRows() > 0){
						returnTable.firstRow();
						do{
							
							EmpOfficalInformationForm emp=new EmpOfficalInformationForm();					
							emp.setEmployeeNumber(Integer.toString(returnTable.getInt("PERNR")));					
							emp.setPayGroup(Integer.toString(returnTable.getInt("PAYGROUP")));
							Date DOb=returnTable.getDate("PRMNTH");
							 ft1 = new SimpleDateFormat ("yyyy-MM-dd");
						    String dob = ft1.format(DOb);
						    emp.setDob(dob);
							emp.setCompanyName("1000");
						    emp.setLocation(returnTable.getString("WERKS"));
						    emp.setEmployeeCategory(Integer.toString(returnTable.getInt("STAFFCAT")));		
							emp.setReportingManger(Integer.toString(returnTable.getInt("RPTMGR")));
						 
							emp.setState(returnTable.getString("STATE"));
					         emp.setWork_id(Integer.toString(returnTable.getInt("LOCID")));
						 
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
						    if(grade==6)
						    {
						    	  emp.setGradeID("g6");
						    }
						    if(grade==7)
						    {
						    	  emp.setGradeID("g7");
						    }
						    
							emp.setDepartment(Integer.toString(returnTable.getInt("DPTID")));
							emp.setDesignation(Integer.toString(returnTable.getInt("DSGID")));				  
						  
							emp.setAccountType(returnTable.getString("BACCTYP"));
		                    emp.setAccountNumber(returnTable.getString("BACCNO"));	
		                    emp.setBankId(Integer.toString(returnTable.getInt("BANKID")));
		               
		                    
		                	empoffList.add(emp);
				
							//returnMap.put(returnTable.getInt("REQ_NO"),returnTable.getChar("TYPE"));
						}while(returnTable.nextRow());
					}
					
					
					
					updateSalaryDetails(empoffList);
					
				} catch (AbapException e) {
					System.out.println(e.toString());// TODO change to log
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));			
					Date d=new Date();
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					String dateNow = ft.format(d);
					String error="insert into ERROR_DETAILS values('EMP_Salary_task','"+dateNow+"','"+errors.toString()+"')";
					int i= abc.ad.SqlExecuteUpdate(error);
				} catch (JCoException e) {
					System.out.println(e.toString());// TODO change to log
					System.out.println(e.getMessageType());
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));			
					Date d=new Date();
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					String dateNow = ft.format(d);
					String error="insert into ERROR_DETAILS values('EMP_Salary_task','"+dateNow+"','"+errors.toString()+"')";
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
					String error="insert into ERROR_DETAILS values('EMP_Salary_task','"+dateNow+"','"+errors.toString()+"')";
					int i=abc.ad.SqlExecuteUpdate(error);
				} finally {
					System.out.println("ended EMP_Salary_task");
				}
	}
	
	public static int[] updateSalaryDetails(LinkedList attdList){
		
		 System.out.println(attdList.size());
		SAP_SALARY_BAPI abc=new SAP_SALARY_BAPI();
		 try {
			 if(conn==null||conn.isClosed())
			 {
				 conn=AttConnectionFactory.getConnection();
			 }
				PreparedStatement st2=conn.prepareStatement("update emp_official_info set WERKS=?,pay_group=?,STAFFCAT=?,STATE=?,LOCID=?,Work_ID=?,GRDID=?,DSGID=?,DPTID=?,"
						+ "BANKID =?,BACCTYP =?,BACCNO =?,SAP_Status=?,SAP_Approved_Date=? Where pernr=? ");
						
			 
		
			Iterator attdItr = attdList.iterator();
			while(attdItr.hasNext()) {

				EmpOfficalInformationForm a=(EmpOfficalInformationForm)attdItr.next();
			
				st2.setString(1,a.getLocation());
				st2.setString(2,a.getPayGroup());
				st2.setString(3,a.getEmployeeCategory());
				st2.setString(4,a.getState());
				st2.setString(5,a.getLocation());
				st2.setString(6,a.getWork_id());
				st2.setString(7,a.getGradeID());
				st2.setString(8,a.getDesignation());
				st2.setString(9,a.getDepartment());
				st2.setString(10,a.getBankId());
				st2.setString(11,a.getAccountType());
				st2.setString(12,a.getAccountNumber());
				
				st2.setString(13,"SALARY");
				Date dNow = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String submitDate = ft.format(dNow);
				st2.setString(14, submitDate);				
		
				
				st2.setString(15,a.getEmployeeNumber());
				
			
				st2.addBatch();
				
			
			
				
			
				
			}
			if(!attdList.isEmpty()){			
				return st2.executeBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();// TODO Change to log
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));			
			Date d=new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String dateNow = ft.format(d);
			String error="insert into ERROR_DETAILS values('EMP_SALARY','"+dateNow+"','"+errors.toString()+"')";
			int i= abc.ad.SqlExecuteUpdate(error);
		}
		return null; 
	}
		
	
	}


