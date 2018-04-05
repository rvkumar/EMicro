package com.microlabs.scheduler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.dao.SAPLeaveDao;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.ess.form.SAPLeaveForm;
import com.microlabs.ess.form.SAPOndutyForm;
import com.microlabs.hr.form.EmpOfficalInformationForm;
import com.microlabs.it.form.IssuesForm;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class Scheduler extends HttpServlet {
	
	EssDao ad=EssDao.dBConnection();
	private static final String DESTINATION = "SAP_DESTINATION";
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
			throw new RuntimeException("Unable to create the destination file",e);
		}
	}
	
	
	 
    @Override
	public void init() throws ServletException
    {
          System.out.println("----------");
          System.out.println("---------- SCHEDULER Servlet Initialized successfully ----------");
          System.out.println("----------");
          TimerTask tt = new TimerTask(){
  			@Override
			public void run(){
  				synchronized (this) {
					
				
  				System.out.println("started LEAV AND ONDUTY task");
  				
  				Date dNow1 = new Date( );
				 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					String dateNow1 = ft1.format(dNow1);
 				
 				String tran="insert into Schedule_Transaction values('Leave/Onduty','"+dateNow1+"')";
 				int j=ad.SqlExecuteUpdate(tran);
  				
                SAPLeaveDao dao=new SAPLeaveDao();
				
				JCoFunction function=null;
				JCoTable a=null;
				
				try {
				
					
					connectSAP();
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
								"ZBAPI_HR_LEAVE");
					if (function == null) {
						throw new RuntimeException(" ZBAPI_HR_LEAVE not found in SAP.");//ZBAPI_HR_PAYSLIP
					}
					
					Date dNow = new Date( );
					SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
				    String dateNow = ft.format(dNow);
				    
				     DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				        Calendar cal = Calendar.getInstance();
				        cal.add(Calendar.DATE, -1); 
				        String yestdate=dateFormat.format(cal.getTime());
				    
					
					String leave="select lv.user_id,emp.PAY_GROUP,lv.leave_type,lm.lv_shrt,YEAR(start_date) as calyear,lv.start_date,lv.end_date,lv.start_duration,"
							+ "lv.end_duration,lv.no_of_days,lv.submit_date,lv.reasonType,reason.leavId,lv.reason,lv.Approvel_Status,lv.approved_date,lv.rejected_date"
							+ " from leave_details as lv,lv_type_m  as lm,emp_official_info as emp,lv_reason as reason  where Approvel_Status in ('Approved','Cancelled')"
							+ " and lv.leave_type=lm.lv_typeid and emp.PERNR=lv.user_id and reason.leavType=lv.reasonType and"
							+ " ((approved_date between '"+yestdate+" 18:00:00.000'  and '"+dateNow+"')or (rejected_date between '"+yestdate+" 18:00:00.000'  and '"+dateNow+"')) order by Approvel_Status desc";
					
					ResultSet rs=ad.selectQuery(leave);
					while(rs.next())
					
					{		
						JCoTable passTable = function.getTableParameterList().getTable(
								"ZHEMP_LEAVA_DATA");
						String dd=rs.getString("submit_date");
						String b[]=dd.split("/");
						dd=b[2]+b[1]+b[0];
						passTable.appendRow();
						passTable.setValue("CYEAR",rs.getString("calyear"));
						passTable.setValue("PAYGROUP",rs.getString("PAY_GROUP") );
						passTable.setValue("PERNR", rs.getString("user_id"));
						passTable.setValue("LEAVTYP", rs.getString("lv_shrt"));
						passTable.setValue("BEGDA", display(rs.getDate("start_date")));
						passTable.setValue("LEAVFD", rs.getString("start_duration"));
						passTable.setValue("ENDDA",display(rs.getDate("end_date")));
						passTable.setValue("LEAVTD", rs.getString("end_duration"));
						passTable.setValue("NODAYS", rs.getString("no_of_days"));
						passTable.setValue("SUBDT",dd);
						passTable.setValue("LREASON", rs.getInt("leavId"));
						passTable.setValue("REMARKS", rs.getString("reason"));
						String status=rs.getString("Approvel_Status");
						String Active="";				
						if(status.equalsIgnoreCase("Approved"))
						{
							Active="X";
						}
					    passTable.setValue("STATUS",Active);
						
					}
					
					
					function.execute(destination);
					LinkedList leaveList = new LinkedList();
					JCoTable returnTable = function.getTableParameterList().getTable("RETURN");//PAYDATA_D_H
				
					if(returnTable.getNumRows() > 0){
						returnTable.firstRow();
						do{
							SAPLeaveForm leav=new SAPLeaveForm();
							leav.setYear(returnTable.getString("CYEAR"));
							leav.setEmployeeNo(returnTable.getString("PERNR"));
							leav.setPayGroup(returnTable.getString("PAYGROUP"));
							leav.setLeaveType(returnTable.getString("LEAVTYP"));
							leav.setStartDate(returnTable.getString("BEGDA"));
							leav.setStartDurationType(returnTable.getString("LEAVFD"));
							leav.setEndDate(returnTable.getString("ENDDA"));
							leav.setEndDurationType(returnTable.getString("LEAVTD"));
							leav.setNoOfDays(returnTable.getString("NODAYS"));
							leav.setSubmitDate(returnTable.getString("SUBDT"));	
							String st=Character.toString(returnTable.getChar("STATUS"));
							leav.setAppstatus(st);
							leav.setMessage(returnTable.getString("MESSAGE"));					
							char  c=returnTable.getChar("TYPE");
							if(c=='E'){
								leav.setSapstatus("No");
							}
							if(c=='S')
							{
								leav.setSapstatus("Yes");
							}
							Date d = new Date( );
							SimpleDateFormat f = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
							String dt=f.format(d);
							
							leav.setSapApprovedDate(dt);
						
							leaveList.add(leav);
							
							
						}while(returnTable.nextRow());
				
					dao.updateleaveDetails(leaveList);
					
					}
					
					
				} catch (AbapException e) {
					System.out.println(e.toString());// TODO change to log
					
				} catch (JCoException e) {
					System.out.println(e.toString());// TODO change to log
					System.out.println(e.getMessageType());
					
					//e.printStackTrace();
					//System.out.println(function.getException("");
					
				}catch(Throwable e){
					System.out.println(e.toString());// TODO change to log
					e.printStackTrace();
				} finally {
					//rs.close();
				}
				
				///OnDUTY
try {
				
					
					connectSAP();
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
						"ZBAPI_HR_PERM");
			if (function == null) {
				throw new RuntimeException(" ZBAPI_HR_PERM not found in SAP.");//ZBAPI_HR_PAYSLIP
			}
			
			Date dNow = new Date( );
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
		    String dateNow = ft.format(dNow);
		    
		    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DATE, -1); 
	        String yestdate=dateFormat.format(cal.getTime());
			
	        String leave="select YEAR(start_date) as calyear,ond.request_no,emp.PAY_GROUP,ond.user_id,ond.onDuty_Type,lv.leavId,ond.start_date,ond.startTime,ond.end_date,"
					+ "ond.endTime,ond.submit_date,ond.reason,ond.Approver_Status from OnDuty_details as ond,emp_official_info as emp,lv_reason as lv"
					+ " where  Approver_Status in('Approved','Cancelled') and emp.PERNR=ond.user_id and lv.leavType=ond.onDuty_Type and ((approved_date between '"+yestdate+" 18:00:00.000'  and '"+dateNow+"')or (rejected_date between '"+yestdate+" 18:00:00.000'  and '"+dateNow+"'))";
						
			ResultSet rs=ad.selectQuery(leave);
			while(rs.next())
			{		
				JCoTable passTable = function.getTableParameterList().getTable(
						"ZHEMP_PERM_DATA");
				String dd=rs.getString("submit_date");
				String b[]=dd.split("/");
				dd=b[2]+b[1]+b[0];
				passTable.appendRow();
				passTable.setValue("REQNO",rs.getInt("request_no"));
				passTable.setValue("CYEAR",rs.getString("calyear"));
				passTable.setValue("PAYGROUP",rs.getString("PAY_GROUP") );
				passTable.setValue("PERNR", rs.getString("user_id"));
				passTable.setValue("PERMTYP", "OD");
				passTable.setValue("BEGDA", display(rs.getDate("start_date")));
				passTable.setValue("ENDDA",display(rs.getDate("end_date")));
				//starttime
				String starttime=rs.getString("startTime");		
				String starttimear[]=starttime.split(":");				
				int start=0;		
				String starttimeduration=starttime.substring(5, starttime.length());
				if(starttimeduration.equalsIgnoreCase("PM"))
				{
					 start=Integer.parseInt(starttimear[0]);
					 if(start!=12)
					 start=start+12;
				}
				else
				{
					start=Integer.parseInt(starttimear[0]);
				}
				if(start>10)
				starttime=Integer.toString(start)+":"+starttimear[1];
				else
				starttime="0"+Integer.toString(start)+":"+starttimear[1];
				
				String ActualStarttime=starttime.substring(0, starttime.length()-2)+":00";
				DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date = sdf.parse(ActualStarttime);
					ActualStarttime=sdf.format(date);
				} catch (ParseException e1) {		
					e1.printStackTrace();
				}
				//endtime
				String endtime=rs.getString("endTime");		
				String endtimear[]=endtime.split(":");				
				int end=0;		
				String endtimeduration=endtime.substring(5, endtime.length());
				if(endtimeduration.equalsIgnoreCase("PM"))
				{
					end=Integer.parseInt(endtimear[0]);
					if(end!=12)
					end=end+12;
				}
				else
				{
					end=Integer.parseInt(endtimear[0]);
				}
				
				if(end>10)
				endtime=Integer.toString(end)+":"+endtimear[1];
				else
				endtime="0"+Integer.toString(end)+":"+endtimear[1];
				String ActualEndtime=endtime.substring(0, endtime.length()-2)+":00";
				DateFormat dft = new SimpleDateFormat("HH:mm:ss");
				try {
					Date date = dft.parse(ActualEndtime);
					ActualEndtime=dft.format(date);
				} catch (ParseException e1) {		
					e1.printStackTrace();
				}
				String actst[]=ActualStarttime.split(":");
				ActualStarttime=actst[0]+actst[1]+actst[2];
				String acten[]=ActualEndtime.split(":");
				ActualEndtime=acten[0]+acten[1]+acten[2];
				passTable.setValue("PERMFT", ActualStarttime);				
				passTable.setValue("PERMET", ActualEndtime);				
				passTable.setValue("SUBDT",dd);
				passTable.setValue("PREASON", rs.getInt("leavId"));
				passTable.setValue("REMARKS", rs.getString("reason"));
				String status=rs.getString("Approver_Status");
				String Active="";				
				if(status.equalsIgnoreCase("Approved"))
				{
					Active="X";
				}
			    passTable.setValue("STATUS",Active);
			}
			
			
			function.execute(destination);
			LinkedList leaveList = new LinkedList();
			JCoTable returnTable = function.getTableParameterList().getTable("RETURN");//
		
			if(returnTable.getNumRows() > 0){
				returnTable.firstRow();
				do{
					SAPOndutyForm leav=new SAPOndutyForm();
					leav.setYear(returnTable.getString("CYEAR"));
					leav.setEmployeeNo(returnTable.getString("PERNR"));
					leav.setPayGroup(returnTable.getString("PAYGROUP"));				
					leav.setStartDate(returnTable.getString("BEGDA"));
					leav.setStartTime(returnTable.getString("PERMFT"));
					leav.setEndDate(returnTable.getString("ENDDA"));
					leav.setEndTime(returnTable.getString("PERMET"));					
					leav.setSubmitDate(returnTable.getString("SUBDT"));	
					leav.setReqno(returnTable.getInt("REQNO"));
					String st=Character.toString(returnTable.getChar("STATUS"));
					leav.setAppstatus(st);
					leav.setMessage(returnTable.getString("MESSAGE"));					
					char  c=returnTable.getChar("TYPE");
					if(c=='E'){
						leav.setSapstatus("No");
					}
					if(c=='S')
					{
						leav.setSapstatus("Yes");
					}
					Date d = new Date( );
					SimpleDateFormat f = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
					String dt=f.format(d);
					
					leav.setSapApprovedDate(dt);
				
					leaveList.add(leav);
				}while(returnTable.nextRow());
		
			dao.updateondutyDetails(leaveList);
			
			}
	
			
		} catch (AbapException e) {
			System.out.println(e.toString());// TODO change to log
			
		} catch (JCoException e) {
			System.out.println(e.toString());// TODO change to log
			System.out.println(e.getMessageType());
			
			//e.printStackTrace();
			//System.out.println(function.getException("");
			
		}catch(Throwable e){
			System.out.println(e.toString());// TODO change to log
			e.printStackTrace();
		} finally {
			//rs.close();
		
				
					}	
			
System.out.println("Ended LEAV AND ONDUTY task");	
		}}	
	};
          
	///EMPOFF INFO
	TimerTask tt1 = new TimerTask()
	{

			@Override
			public void run(){
				
				synchronized (this) {
					
				
				System.out.println("Started EMP_OFF_task");
				Date dNow1 = new Date( );
			 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				String dateNow1 = ft1.format(dNow1);
				
				String tran="insert into Schedule_Transaction values('EMPOFFINFO','"+dateNow1+"')";
				int j=ad.SqlExecuteUpdate(tran);
				
            SAPLeaveDao dao=new SAPLeaveDao();				
			JCoFunction function=null;
			JCoTable a=null;
			
			
			try {
				
				
				connectSAP();
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
						ResultSet rs11=ad.selectQuery(emp1);
						if(rs11.next())					
						{		
							update=true;
						}
						rs11.close();
						ad.connClose();
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
						ResultSet rs1=ad.selectQuery(leave1);
						while(rs1.next())					
						{		
							groupcode=rs1.getString("id");
						}
						rs1.close();
						ad.connClose();
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
						ResultSet rs1=ad.selectQuery(leave1);
						while(rs1.next())					
						{		
							groupcode=rs1.getString("id");
						}
						rs1.close();
						ad.closeResultset();
						ad.connClose();
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
	                    
	                    updateempoffList.add(updateemp);
	                    
						}
						
					}while(returnTable.nextRow());
			
					System.out.println(empoffList.size());
					System.out.println(updateempoffList.size());
			dao.updateempoffDetails(empoffList,updateempoffList);
				
				}

				
			} catch (AbapException e) {
				System.out.println(e.toString());// TODO change to log
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));			
				Date d=new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				String dateNow = ft.format(d);
				String error="insert into ERROR_DETAILS values('EMP_OFF','"+dateNow+"','"+errors.toString()+"')";
				int i= ad.SqlExecuteUpdate(error);
			} catch (JCoException e) {
				System.out.println(e.toString());// TODO change to log
				System.out.println(e.getMessageType());
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));			
				Date d=new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				String dateNow = ft.format(d);
				String error="insert into ERROR_DETAILS values('EMP_OFF','"+dateNow+"','"+errors.toString()+"')";
				int i= ad.SqlExecuteUpdate(error);
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
				int i= ad.SqlExecuteUpdate(error);
			} finally {
				//rs.close();
			}
			
			

System.out.println("Ended EMP_OFF_task");
			
				}	
		

			}

	};
	
	
	///PRODAT
		TimerTask tt2 = new TimerTask()
		{
			
			@Override
			public void run(){
				
				synchronized (this) {
					
				
				
			System.out.println("Started PRO_DATA_task");
			Date dNow1 = new Date( );
		 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			String dateNow1 = ft1.format(dNow1);
			
			String tran="insert into Schedule_Transaction values('PRODATA','"+dateNow1+"')";
			int j=ad.SqlExecuteUpdate(tran);
			
        SAPLeaveDao dao=new SAPLeaveDao();				
		JCoFunction function=null;
		JCoTable a=null;
		
		
		try {
			
			String sick="update lv_type_d set lv_clbal=d,lv_opbal=d,prodata=1,SAP_Approved_Date=getdate() from (select pernr,doj,case when day(DOJ)>15 "
					+ "then datediff(MONTH,DOJ,convert(nvarchar(4),year(DOJ))+'-12-01')*0.5 else datediff(MONTH,DOJ,convert(nvarchar(4),year(DOJ))+'-12-01')*0.5+0.5 end as d "
					+ "from emp_official_info where convert(date,created_date) = convert(date,getdate()) and emp_official_info.STAFFCAT!=8 )t where t.PERNR=lv_type_d.lv_empcode and lv_typeid=2 and prodata=0 and "
					+ "lv_calyear=year(DOJ) ";
		 int a2=ad.SqlExecuteUpdate(sick);
		 
		 //CL --- current year and doj year same
		 String casual="update lv_type_d set lv_clbal=d,lv_opbal=d,prodata=1,SAP_Approved_Date=getdate() from (select pernr,doj,case when day(DOJ)>15 "
		 		+ "then datediff(MONTH,DOJ,convert(nvarchar(4),year(DOJ))+'-12-01')*0.5 else datediff(MONTH,DOJ,convert(nvarchar(4),year(DOJ))+'-12-01')*0.5+0.5 end as d"
		 		+ " from emp_official_info where dateadd(month,6,doj) = convert(date,getdate()) and  year(doj)=year(dateadd(month,6,doj)) and emp_official_info.STAFFCAT!=8)t  where t.PERNR=lv_type_d.lv_empcode "
		 		+ "and lv_typeid=1 and prodata=0 and lv_calyear=year(DOJ) and lv_calyear=year(getdate())";
		 int a3=ad.SqlExecuteUpdate(casual);
		 
		 ///CL- current year and doj year diff
		 String casual1="update lv_type_d set lv_clbal=6,lv_opbal=6,prodata=1,SAP_Approved_Date=getdate() from emp_official_info where pernr=lv_type_d.lv_empcode and lv_typeid=1 "
		 		+ "and prodata=0 and lv_calyear=year(getdate()) and dateadd(month,6,doj) = convert(date,getdate()) and  emp_official_info.STAFFCAT!=8";
		 int a9=ad.SqlExecuteUpdate(casual1);
		 
		 
		 //PL
		 String priv="update lv_type_d set lv_clbal=d,lv_opbal=d,prodata=1,SAP_Approved_Date=getdate() from (select pernr,doj,case when day(DOJ)>15"
		 		+ " then round(datediff(MONTH,DOJ,convert(nvarchar(4),year(DOJ))+'-12-01')* 1.25,0) else round(datediff(MONTH,DOJ,convert(nvarchar(4),year(DOJ))+'-12-01')* 1.25 + 1.25,0) end as d"
		 		+ " from emp_official_info where dateadd(month,12,doj) = convert(date,getdate()) and emp_official_info.STAFFCAT!=8 )t  where t.PERNR=lv_type_d.lv_empcode and lv_typeid=3 and prodata=0 and"
		 		+ " lv_calyear=year(getdate())";
		 
		 int a4=ad.SqlExecuteUpdate(priv);
		 
			}catch(Throwable e){
			System.out.println(e.toString());// TODO change to log
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));			
			Date d=new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String dateNow = ft.format(d);
			String error="insert into ERROR_DETAILS values('PRO_DATA','"+dateNow+"','"+errors.toString()+"')";
			int i= ad.SqlExecuteUpdate(error);
		} finally {
			//rs.close();
		}
		
		

System.out.println("Ended PRO_DATA Task");
		
			}
			}
		};

		
		
		
		
		//ESCALATION
				TimerTask tt3 = new TimerTask()
				{
					  SAPLeaveDao dao=new SAPLeaveDao();
				       @Override
					public void run(){
							
							synchronized (this) {
								
								System.out.println("Started ESCALATION task");
						
								
								
								String reqnos = "";
								Date d = new Date();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ");
								String datenw = sdf.format(d);
								String tran="insert into Schedule_Transaction values('ESCALATION','"+datenw+"')";
								int j=ad.SqlExecuteUpdate(tran);
							
								
							
								String a="SELECT iss.*,loc.LOCATION_CODE   FROM IT_ISSUES iss,Location loc where  Req_Status not in('Completed')  and iss.Location=loc.LOCID order by Req_No desc";
								ResultSet es = ad.selectQuery(a);

								try {
								   int actaulminutes=0;
								   int i=0;
								   PreparedStatement pst=null;
									LinkedList esclateList = new LinkedList();
									String reqpriority="";
									while (es.next()) {
										reqpriority=es.getString("Req_Priority");
										if(es.getString("req_status").equalsIgnoreCase("New")||es.getString("req_status").equalsIgnoreCase("In Process"))
										{



												if(es.getString("Req_date").substring(0, 11).equalsIgnoreCase(datenw.substring(0, 11)))
												{
													actaulminutes=getminutes(es.getString("Req_date"), datenw,es.getString("Location"),es.getString("LOCATION_CODE") ,2);
												}

												if(!es.getString("Req_date").substring(0, 11).equalsIgnoreCase(datenw.substring(0, 11)))
												{
													actaulminutes=	getminutes(es.getString("Req_date"), datenw,es.getString("Location"),es.getString("LOCATION_CODE") ,1);
												}

										}

										else
										{



												if(es.getString("approved_date").substring(0, 11).equalsIgnoreCase(datenw.substring(0, 11)))
												{
													actaulminutes=getminutes(es.getString("approved_date"), datenw,es.getString("Location"),es.getString("LOCATION_CODE") ,2);
												}

												if(!es.getString("approved_date").substring(0, 11).equalsIgnoreCase(datenw.substring(0, 11)))
												{
													actaulminutes=	getminutes(es.getString("approved_date"), datenw,es.getString("Location"),es.getString("LOCATION_CODE") ,1);
												}

										}
										
										
										//escalation master
									
										String esc="select top 1 id from Escalation_Master where TimeInHrs*60<"+actaulminutes+" and user_priority='"+reqpriority+"' order by TimeInHrs desc";
										ResultSet escl = ad.selectQuery(esc);	
										
											while (escl.next()) {
											   IssuesForm is=new IssuesForm();
											   is.setId(Integer.toString(escl.getInt(1)));
											   is.setRequestNo(es.getInt("Req_No"));
											   esclateList.add(is);
											}
										
									}
									if(esclateList.size()>0)
									{				
										dao.updateESCALATIONDetails(esclateList);		   
								
									}		
									
								} catch (SQLException e) {	
									e.printStackTrace();
								
								}
								
								
								
								System.out.println("Ended ESCALATION Task");
							}
						
					}
					
				};
				
				
				
				//COMP OFF APPROVALS
				TimerTask tt4 = new TimerTask()
				{
					
				    SAPLeaveDao dao=new SAPLeaveDao();
		       @Override
			public void run(){
					
					synchronized (this) {
						
					
					
				System.out.println("Started COMP OFF APPROVALS task ");
				Date dNow1 = new Date( );
			 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				String dateNow1 = ft1.format(dNow1);
				
				String tran="insert into Schedule_Transaction values('COMP OFF APPROVALS','"+dateNow1+"')";
				int j=ad.SqlExecuteUpdate(tran);
				
		   SAPLeaveDao dao=new SAPLeaveDao();				
			JCoFunction function=null;
			JCoTable a=null;
			
			
			try {

				connectSAP();
				JCoDestination destination = JCoDestinationManager
						.getDestination(DESTINATION);// TODO change to real
														// destination
				if (destination == null) {

					destination = JCoDestinationManager.getDestination(DESTINATION);// TODO
																					// change
																					// to
																					// real
																					// destination
					if (destination == null) {
						throw new RuntimeException(
								"Could not connect to SAP, destination not found.");
					}
				}

				function = destination.getRepository().getFunction(
						"ZBAPI_HR_COMP_OFF_OT");
				if (function == null) {
					throw new RuntimeException(
							" ZBAPI_HR_COMP_OFF_OT not found in SAP.");
				}
				Date dNow = new Date();
				SimpleDateFormat ft = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss.SSS");
				String dateNow = ft.format(dNow);

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
		         cal.add(Calendar.DATE, -1);
				String yestdate = dateFormat.format(cal.getTime());

				function.execute(destination);
				LinkedList comoffList = new LinkedList();

				String aa = "select c.*,e.PAY_GROUP,YEAR(c.FromDate) as calyear from COMP_OT c,emp_official_info e where e.PERNR=c.Pernr and  ApprvrStatus='Approved' and SAP_Approved='0' and Comp_Availed='0' and Comp_Lapsed='0' and (ApprvdDate between '"+yestdate+" 18:00:00.000'  and '"+dateNow+"') order by c.SAP_ReqNo";
				ResultSet com = ad.selectQuery(aa);
				while (com.next()) {
					JCoTable passTable = function.getTableParameterList().getTable(
							"ZHEMP_COMOFF_OT");
					passTable.appendRow();
					passTable.setValue("PAYGROUP", com.getString("PAY_GROUP"));
					passTable.setValue("PERNR", com.getInt("pernr"));
					passTable.setValue("CYEAR", com.getString("calyear"));
					passTable.setValue("CO_FDATE", display(com.getDate("FromDate")));
					passTable.setValue("CO_TDATE", display(com.getDate("ToDate")));
					int hrs = com.getInt("NoHRS");
					String reqhr = "0" + Integer.toString(hrs) + "0000";
					passTable.setValue("NOHRS", Integer.parseInt(reqhr));
					passTable.setValue("STATUS", com.getString("Applicabale"));
					passTable.setValue("REQBY", com.getInt("RequestedBy"));
					passTable.setValue("REQDATE",
							display(com.getDate("RequestedDate")));
					passTable.setValue("REMARKS", com.getString("Reason"));
					passTable.setValue("REQNO", com.getInt("SAP_ReqNo"));
				}

				function.execute(destination);
				LinkedList updatcompff = new LinkedList();
				JCoTable returnTable = function.getTableParameterList().getTable(
						"RETURN");// PAYDATA_D_H

				if (returnTable.getNumRows() > 0) {
					returnTable.firstRow();
					do {
						LeaveForm l = new LeaveForm();
						l.setYear(Integer.parseInt(returnTable.getString("CYEAR")));
						l.setEmployeeNumber(returnTable.getString("PERNR"));
						l.setStartDate(returnTable.getString("CO_FDATE"));
						l.setEndDate(returnTable.getString("CO_TDATE"));
						l.setNofhrs(returnTable.getString("NOHRS"));
						l.setShift(returnTable.getString("STATUS"));
						l.setRequestNumber(Integer.parseInt(returnTable
								.getString("REQNO")));
						l.setMessage(returnTable.getString("MESSAGE"));
						char c = returnTable.getChar("TYPE");
						if (c == 'E') {
							l.setStatus("No");
						}
						if (c == 'S') {
							l.setStatus("Yes");
						}
						Date d = new Date();
						SimpleDateFormat f = new SimpleDateFormat(
								"yyyy-MM-dd hh:mm:ss");
						String dt = f.format(d);

						l.setApprovedDate(dt);

						updatcompff.add(l);
					} while (returnTable.nextRow());

					dao.updateCompffDetails(updatcompff);

				}
					
			} catch (AbapException e) {
				System.out.println(e.toString());// TODO change to log
				
			} catch (JCoException e) {
				System.out.println(e.toString());// TODO change to log
				System.out.println(e.getMessageType());
				
				//e.printStackTrace();
				//System.out.println(function.getException("");
				
			}catch(Throwable e){
				System.out.println(e.toString());// TODO change to log
				e.printStackTrace();
			} finally {
				//rs.close();
			}
			
			

		System.out.println("Ended COMP OFF APPROVALS Task");
			
				}
				}
				};
				
				
			    //leav and onduty
			/*    Calendar date = Calendar.getInstance();
				 date.set(Calendar.HOUR_OF_DAY, 18);
				 date.set(Calendar.MINUTE,0);
				 date.set(Calendar.SECOND, 0);
				 date.set(Calendar.MILLISECOND, 0);
				 Timer timer = new Timer();		    
				 timer.schedule(tt,date.getTime(),24 * 60 * 60 * 1000);*/
				 
				 ///emp _of info
				 
				/* Calendar date1 = Calendar.getInstance();
				 date1.set(Calendar.HOUR_OF_DAY, 18);
				 date1.set(Calendar.MINUTE,2);
				 date1.set(Calendar.SECOND, 0);
				 date1.set(Calendar.MILLISECOND, 0);
				 Timer timer1 = new Timer();		    
				 timer1.schedule(tt1,date1.getTime(),24 * 60 * 60 * 1000);*/
				 
				 
				 ///prodata
				/* Calendar date2 = Calendar.getInstance();
				 date2.set(Calendar.HOUR_OF_DAY, 20);
				 date2.set(Calendar.MINUTE,0);
				 date2.set(Calendar.SECOND, 0);
				 date2.set(Calendar.MILLISECOND, 0);
				 Timer timer2 = new Timer();		    
				 timer2.schedule(tt2,date2.getTime(),24 * 60 * 60 * 1000);*/
				 
				 
				 //Escalation
						
				 /*Timer timer3 = new Timer();		    
				 timer3.schedule(tt3,0,1*60 * 60 * 1000);*/
				 
				 //COMP OFF APPROVALS
				  
				/* Calendar date3 = Calendar.getInstance();
				 date3.set(Calendar.HOUR_OF_DAY, 18);
				 date3.set(Calendar.MINUTE,6);
				 date3.set(Calendar.SECOND, 0);
				 date3.set(Calendar.MILLISECOND, 0);
				 Timer timer4 = new Timer();		    
				 timer4.schedule(tt4,date3.getTime(),24 * 60 * 60 * 1000);*/
		 
		 }
    
	  
		public static String display(Date dbDate)
		{
			String dt="";
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			try{
				dt=dateFormat.format(dbDate);
			}catch (Exception e) {
				dt="";
			}
			return dt;
		}
		
		public  int getminutes(String fromdate,String todate,String locationID ,String locationCODE, int prefix)
		{
			int count=0;
			String query="";
			if(prefix==2)
			{
				//query="select  DATEDIFF( MINUTE,'"+fromdate+"','"+todate+"') minutes from CMP_WRKIN_HRS where Location='"+locationID+"' ";
				query="select  CASE WHEN DATEDIFF( MINUTE,'"+todate+"',left(GETDATE(),11)+' '+End_time)> 1 THEN DATEDIFF( MINUTE,'"+fromdate+"','"+todate+"') "
						+ " ELSE DATEDIFF( MINUTE,'"+fromdate+"','"+todate.substring(0,11)+"'+' '+End_time)"
						+ "END as minutes from CMP_WRKIN_HRS where Location='"+locationID+"' ";
			}
			
			if(prefix==1)
			{
				query="select SUM(minutes)  as mind from (select COUNT(*)*(select DATEDIFF( MINUTE,left(GETDATE(),11)+' '+Start_time,left(GETDATE(),11)+' '+End_time) "
			+ " from CMP_WRKIN_HRS where Location='"+locationID+"') as minutes from ESCALATION_CALENDAR e where DATE>'"+fromdate.substring(0, 11)+"' and DATE<'"+todate.substring(0, 11)+"' and "+locationCODE+"='W' "
			+ "union all select  DATEDIFF( MINUTE,left(GETDATE(),11)+' '+'"+fromdate.substring(11, fromdate.length())+"',left(GETDATE(),11)+' '+End_time) minutes from CMP_WRKIN_HRS where Location='"+locationID+"'"
			+ " union all select  DATEDIFF( MINUTE,left(GETDATE(),11)+' '+Start_time, left(GETDATE(),11)+' '+'"+todate.substring(11, fromdate.length())+"') minutes from CMP_WRKIN_HRS where Location='"+locationID+"' )d ";			
			}
			
			
			
			ResultSet rsCount = ad.selectQuery(query);
			try {
				while (rsCount.next()) {
					count=rsCount.getInt(1);
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			
			
			return count;
		}

}
