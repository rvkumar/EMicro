package com.microlabs.ess.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.AttDao;
import com.microlabs.hr.form.HRApprovalForm;
import com.microlabs.main.form.MailInboxForm;

public class ATTENDANCE_SUMMARY {
	 static 	AttDao ad=new AttDao();
	 private static String fromAddress;
		private  static String password; 
		public int MAIL_PER_PAGE = 10;
		private String USER_ID;
		
		public void onStartPage(PdfWriter writer,Document document) {
			
			
			
	    	
		   	 PdfPTable table1 = new PdfPTable(2); // 3 columns.
		        table1.setWidthPercentage(100); //Width 100%
		        table1.setSpacingBefore(10f); //Space before table
		        table1.setSpacingAfter(10f); //Space after table
		        
		        float[] columnWidths1 = {1.0f, 5f};
			      
					try {
						table1.setWidths(columnWidths1);
						 Image image1;
							try {
								
								InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
							 	 Properties props = new Properties();
							 	 try {
									props.load(in);	
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							 	 
							 	try {
									in.close();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								String uploadFilePath=props.getProperty("file.uploadFilePath");
								
								String filePath=uploadFilePath+"/Joining Report/";
								image1 = Image.getInstance(filePath.replace("/", "\\")+"logo.png");
							
					           //Fixed Positioning
					     
					           //Scale to new height and new width of image
					           image1.scaleAbsolute(40, 40);
					           image1.setAlignment(Element.ALIGN_CENTER);
					           //Add to document
					           PdfPCell t1 = new PdfPCell(image1);
					           t1.setHorizontalAlignment(Element.ALIGN_CENTER);
					           t1.setVerticalAlignment(Element.ALIGN_CENTER);
					         
					   	
					           table1.addCell(t1);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							Font columnheader = new Font(Font.FontFamily.UNDEFINED, 10,
				                    Font.BOLD);
							Font largeBold = new Font(Font.FontFamily.UNDEFINED, 12,Font.BOLD);
							
							
							 Paragraph preface1 = new Paragraph("MICRO LABS LIMITED                                                                                                             "
							 		+ " #27, Race Course Road, Bangalore - 560 001",largeBold); 
				      	    preface1.setAlignment(Element.ALIGN_RIGHT);
							PdfPCell t2 = new PdfPCell(preface1);
						
							t2.setHorizontalAlignment(Element.ALIGN_CENTER);
							t2.setVerticalAlignment(Element.ALIGN_MIDDLE);
							
					
							
						    table1.addCell(t2);
						    
						    
							 
					          
					         
					           
					         
					           
					       	   document.add(table1);
					       	 
					       	  
							
								
							
					       	
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		   	
		   	/*
		   	Rectangle rect = writer.getBoxSize("art");
		       ColumnText.showTextAligned(
		       		writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Left"), rect.getLeft(), rect.getTop(), 0);
		       ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Right"), rect.getRight(), rect.getTop(), 0);*/
		   }
	
		
		public static String getTableName(String location)
		{
			String tableName="";
			if(location.equalsIgnoreCase("CBWH"))
				tableName="CBWHCAL";
			if(location.equalsIgnoreCase("ML00"))
					tableName="CmpCal2013";
			if(location.equalsIgnoreCase("ML01"))
				tableName="ML01CAL";
			if(location.equalsIgnoreCase("ML02"))
				tableName="ML02CAL";
			if(location.equalsIgnoreCase("ML03"))
				tableName="ML03CAL";
			if(location.equalsIgnoreCase("ML04"))
				tableName="ML04CAL";
			if(location.equalsIgnoreCase("ML05"))
				tableName="ML05CAL";
			if(location.equalsIgnoreCase("ML06"))
				tableName="ML06CAL";
			if(location.equalsIgnoreCase("ML07"))
				tableName="ML07CAL";
			if(location.equalsIgnoreCase("ML08"))
				tableName="ML08CAL";
			if(location.equalsIgnoreCase("ML09"))
				tableName="ML09CAL";
			if(location.equalsIgnoreCase("ML10"))
				tableName="ML10CAL";
			if(location.equalsIgnoreCase("ML11"))
				tableName="ML11CAL";
			if(location.equalsIgnoreCase("ML12"))
				tableName="ML12CAL";
			if(location.equalsIgnoreCase("ML13"))
				tableName="ML13CAL";
			if(location.equalsIgnoreCase("ML14"))
				tableName="ML14CAL";
			if(location.equalsIgnoreCase("ML15"))
				tableName="ML15CAL";
			if(location.equalsIgnoreCase("ML16"))
				tableName="ML16CAL";
			if(location.equalsIgnoreCase("ML17"))
				tableName="ML17CAL";
			if(location.equalsIgnoreCase("ML18"))
	       		tableName="ML18CAL";
			if(location.equalsIgnoreCase("ML19"))
				tableName="ML19CAL";
			if(location.equalsIgnoreCase("ML20"))
				tableName="ML20CAL";
			if(location.equalsIgnoreCase("ML21"))
				tableName="ML21CAL";
			if(location.equalsIgnoreCase("ML22"))
	       		tableName="ML22CAL";
			if(location.equalsIgnoreCase("ML23"))
				tableName="ML23CAL";
			if(location.equalsIgnoreCase("ML24"))
				tableName="ML24CAL";
			if(location.equalsIgnoreCase("ML25"))
				tableName="ML25CAL";
			if(location.equalsIgnoreCase("ML26"))
	       		tableName="ML26CAL";
			if(location.equalsIgnoreCase("ML51"))
				tableName="ML51CAL";
			if(location.equalsIgnoreCase("ML52"))
				tableName="ML52CAL";
			if(location.equalsIgnoreCase("ML53"))
				tableName="ML53CAL";
			if(location.equalsIgnoreCase("ML54"))
				tableName="ML54CAL";
			if(location.equalsIgnoreCase("ML55"))
				tableName="ML55CAL";
			if(location.equalsIgnoreCase("ML56"))
				tableName="ML56CAL";
			if(location.equalsIgnoreCase("ML57"))
				tableName="ML57CAL";
			if(location.equalsIgnoreCase("ML58"))
				tableName="ML58CAL";
			if(location.equalsIgnoreCase("ML59"))
				tableName="ML59CAL";
			if(location.equalsIgnoreCase("ML60"))
				tableName="ML60CAL";
			if(location.equalsIgnoreCase("ML61"))
				tableName="ML61CAL";
			if(location.equalsIgnoreCase("ML62"))
				tableName="ML62CAL";
			if(location.equalsIgnoreCase("ML63"))
				tableName="ML63CAL";
			if(location.equalsIgnoreCase("ML64"))
				tableName="ML64CAL";
			if(location.equalsIgnoreCase("ML65"))
				tableName="ML65CAL";
			if(location.equalsIgnoreCase("ML66"))
				tableName="ML66CAL";
			if(location.equalsIgnoreCase("ML67"))
				tableName="ML67CAL";
			if(location.equalsIgnoreCase("ML68"))
				tableName="ML68CAL";
			if(location.equalsIgnoreCase("ML90"))
				tableName="ML90CAL";
			if(location.equalsIgnoreCase("ML91"))
				tableName="ML91CAL";
			if(location.equalsIgnoreCase("ML27"))
				tableName="ML27CAL";
			if(location.equalsIgnoreCase("ML92"))
				tableName="ML92CAL";
		
			return tableName;
		}
		
		
	
	public static void main(String[] args) {
		
		HRApprovalForm help=new HRApprovalForm();
		  
		
		System.out.println("Started Attendance Summary task");
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ");
		String datenw = sdf.format(d);
		String tran="insert into Schedule_Transaction values('Attendance Summary','"+datenw+"')";
		int j=ad.SqlExecuteUpdate(tran);
		
		  
			String dept=" select  *  from Attendance_Summary_Mail ";
            ResultSet dd=ad.selectQuery(dept);
            try {
				while(dd.next())
				{
					
					Document document = new Document();
					  PdfWriter writer = null;
						
				
				    
				    
					String sqlfromdate="";					
					String exportdate="";

					
					Date processdate = new Date();
					SimpleDateFormat tyt = new SimpleDateFormat("yyyy-MM-dd");
				/*sqlfromdate = "2017-01-03";*/
					
					SimpleDateFormat display = new SimpleDateFormat("dd/MM/yyyy");
				/*exportdate = "03/01/2017";*/
					
				sqlfromdate = tyt.format(d);
				exportdate = display.format(d);
				
				
				
					
				    int day = Integer.parseInt(sqlfromdate.substring(8));
					
					ArrayList aleave = new ArrayList();
					ArrayList aonduty = new ArrayList();
					ArrayList alop = new ArrayList();
					ArrayList absence = new ArrayList();
					ArrayList absenceint = new ArrayList();
					ArrayList expemted = new ArrayList();
					ArrayList expemtedDrivers = new ArrayList();
					
					 String[] monthName = {"","JAN", "FEB",
							  "MAR", "APR", "MAY", "JUN", "JUL",
							  "AUG", "SEP", "OCT", "NOV",
							  "DEC"
							  };
					//leaves
						String query ="select convert(varchar(10),start_date,104) as startdate ,convert(varchar(10),end_date,104) as enddate ,* from EMP_IN_OUT_Status , "
								+ "emp_official_info,Paygroup_Master ,Category ,department ,DESIGNATION,leave_details,lv_type_m  where year= year('"+sqlfromdate+"') and month = month('"+sqlfromdate+"')"
								+ " and (left(day"+day+",2) like '%CL%' or left(day"+day+",2) like '%SL%' or left(day"+day+",2) like '%EL%' or left(day"+day+",2) like '%ML%' or left(day"+day+",2) like '%CO%')  "
								+ " and   emp_official_info.PERNR=EMP_IN_OUT_Status.PERNR and Paygroup_Master.Paygroup = emp_official_info.PAY_GROUP and "
								+ "Category.staffcat = emp_official_info.STAFFCAT and   DEPARTMENT.DPTID=emp_official_info.DPTID and DESIGNATION.dsgid=emp_official_info.dsgid  "
								+ "and Active='1'   and leave_details.user_id=emp_official_info.PERNR  and '"+sqlfromdate+"' "
								+ "between start_date and end_date AND lv_type_m.lv_typeid=leave_details.leave_type and leave_details.Approvel_Status='Approved'" ;

						
					
						
							query =query+ " and emp_official_info.LOCID = '"+dd.getString("location")+"' ";
							
							if(dd.getString("location").equalsIgnoreCase("ML00"))
							query=query+" and emp_official_info.Reporting_Grp ='10' ";
						
						
						ResultSet rs = ad.selectQuery(query);
						try {
							while(rs.next())
							{
								HRApprovalForm help1 = new HRApprovalForm();
								help1.setEmployeeno(rs.getString("Pernr") );
								help1.setEmployeeName(rs.getString("EMP_FULLNAME"));
								help1.setDepartment(rs.getString("dptstxt"));
								help1.setPaygrp(rs.getString("Short_desc"));
								help1.setFromDate(rs.getString("startdate"));
								help1.setToDate(rs.getString("enddate"));
								help1.setLeavetype(rs.getString("lv_type"));
								help1.setNoOfDays(rs.getString("no_of_days"));
								aleave.add(help1);
							

							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
				            
				           
						//lOP
						String querylop ="select convert(varchar(10),start_date,104) as startdate ,convert(varchar(10),end_date,104) as enddate ,* from EMP_IN_OUT_Status , "
								+ "emp_official_info,Paygroup_Master ,Category ,department ,DESIGNATION,leave_details,lv_type_m  where year= year('"+sqlfromdate+"') and month = month('"+sqlfromdate+"')"
								+ " and left(day"+day+",2) like '%NL%'  "
								+ " and   emp_official_info.PERNR=EMP_IN_OUT_Status.PERNR and Paygroup_Master.Paygroup = emp_official_info.PAY_GROUP and "
								+ "Category.staffcat = emp_official_info.STAFFCAT and   DEPARTMENT.DPTID=emp_official_info.DPTID and DESIGNATION.dsgid=emp_official_info.dsgid  "
								+ "and Active='1'   and leave_details.user_id=emp_official_info.PERNR  and '"+sqlfromdate+"' "
								+ "between start_date and end_date AND lv_type_m.lv_typeid=leave_details.leave_type and leave_details.Approvel_Status='Approved'" ;
					
						querylop =querylop+ " and emp_official_info.LOCID = '"+dd.getString("location")+"' ";
						
						if(dd.getString("location").equalsIgnoreCase("ML00"))
							querylop=querylop+" and emp_official_info.Reporting_Grp ='10' ";
						
						
						ResultSet rslop = ad.selectQuery(querylop);
						try {
							while(rslop.next())
							{
								HRApprovalForm help1 = new HRApprovalForm();
								help1.setEmployeeno(rslop.getString("Pernr") );
								help1.setEmployeeName(rslop.getString("EMP_FULLNAME"));
								help1.setDepartment(rslop.getString("dptstxt"));
								help1.setPaygrp(rslop.getString("Short_desc"));
								help1.setFromDate(rslop.getString("startdate"));
								help1.setToDate(rslop.getString("enddate"));
								help1.setLeavetype(rslop.getString("lv_type"));
								help1.setNoOfDays(rslop.getString("no_of_days"));
								alop.add(help1);
							

							}
							
							
							   
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						///onduty
								String query1 ="select convert(varchar(10),start_date,104) as startdate ,convert(varchar(10),end_date,104) as enddate ,* from EMP_IN_OUT_Status , "
										+ "emp_official_info,Paygroup_Master ,Category ,department ,DESIGNATION,OnDuty_details  where year= year('"+sqlfromdate+"') and month = month('"+sqlfromdate+"')"
										+ " and left(day"+day+",2) like '%OD%'  "
										+ " and   emp_official_info.PERNR=EMP_IN_OUT_Status.PERNR and Paygroup_Master.Paygroup = emp_official_info.PAY_GROUP and "
										+ "Category.staffcat = emp_official_info.STAFFCAT and   DEPARTMENT.DPTID=emp_official_info.DPTID and DESIGNATION.dsgid=emp_official_info.dsgid  "
										+ "and Active='1'   and OnDuty_details.user_id=emp_official_info.PERNR  and '"+sqlfromdate+"' "
										+ "between start_date and end_date and OnDuty_details.Approver_Status='Approved'";

								
								query1 =query1+ " and emp_official_info.LOCID = '"+dd.getString("location")+"' ";
								
								if(dd.getString("location").equalsIgnoreCase("ML00"))
									query1=query1+" and emp_official_info.Reporting_Grp ='10' ";
								
							ResultSet rs1 = ad.selectQuery(query1);
							try {
								while(rs1.next())
								{

									HRApprovalForm help1 = new HRApprovalForm();
									help1.setEmployeeno(rs1.getString("Pernr") );
									help1.setEmployeeName(rs1.getString("EMP_FULLNAME"));
									help1.setDepartment(rs1.getString("dptstxt"));
									help1.setPaygrp(rs1.getString("Short_desc"));
									help1.setFromDate(rs1.getString("startdate"));
									help1.setToDate(rs1.getString("enddate"));
									help1.setStartTime(rs1.getString("startTime"));
									help1.setEndTime(rs1.getString("endTime"));
									help1.setLeavetype("OD");
									help1.setNoOfDays(rs1.getString("no_of_days"));
									aonduty.add(help1);

									    
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							Calendar now = Calendar.getInstance();




							///Absenteees

							String query2="select * from EMP_IN_OUT_Status , emp_official_info,Paygroup_Master ,Category ,department ,DESIGNATION" 
										+ "  where year= year('"+sqlfromdate+"') and month = month('"+sqlfromdate+"') and left(day"+day+",2) like '%AA%' and "
										+ "  emp_official_info.PERNR=EMP_IN_OUT_Status.PERNR and Paygroup_Master.Paygroup = emp_official_info.PAY_GROUP and Category.staffcat = emp_official_info.STAFFCAT and "
										+ "  DEPARTMENT.DPTID=emp_official_info.DPTID and DESIGNATION.dsgid=emp_official_info.dsgid and Swipe_Count !=0 and Active='1' ";

							query2 =query2+ " and emp_official_info.LOCID = '"+dd.getString("location")+"' ";
							
							if(dd.getString("location").equalsIgnoreCase("ML00"))
								query2=query2+" and emp_official_info.Reporting_Grp ='10' ";
							
							ResultSet rs2 = ad.selectQuery(query2);
							try {
								while(rs2.next())
								{

									HRApprovalForm help2 = new HRApprovalForm();
								
										help2.setEmployeeno(rs2.getString("Pernr") );
										help2.setEmployeeName(rs2.getString("EMP_FULLNAME"));
										help2.setDepartment(rs2.getString("dptstxt"));
										help2.setDesignation(rs2.getString("DSGSTXT"));
										help2.setPaygrp(rs2.getString("Short_desc"));
										help2.setLeavetype("LOP");
										absence.add(help2);
									

										
								}
								
								
								///expemted

								String queryexe="select * from EMP_IN_OUT_Status , emp_official_info,Paygroup_Master ,Category ,department ,DESIGNATION" 
											+ "  where year= year('"+sqlfromdate+"') and month = month('"+sqlfromdate+"') and "
											+ "  emp_official_info.PERNR=EMP_IN_OUT_Status.PERNR and Paygroup_Master.Paygroup = emp_official_info.PAY_GROUP and Category.staffcat = emp_official_info.STAFFCAT and "
											+ "  DEPARTMENT.DPTID=emp_official_info.DPTID and DESIGNATION.dsgid=emp_official_info.dsgid and Swipe_Count =0 and Active='1' ";

								queryexe =queryexe+ " and emp_official_info.LOCID = '"+dd.getString("location")+"' ";
								
								if(dd.getString("location").equalsIgnoreCase("ML00"))
									queryexe=queryexe+" and emp_official_info.Reporting_Grp ='10' ";
								
								ResultSet rsexe = ad.selectQuery(queryexe);
								
									while(rsexe.next())
									{

										HRApprovalForm help2 = new HRApprovalForm();
									
											help2.setEmployeeno(rsexe.getString("Pernr") );
											help2.setEmployeeName(rsexe.getString("EMP_FULLNAME"));
											help2.setDepartment(rsexe.getString("dptstxt"));
											help2.setDesignation(rsexe.getString("DSGSTXT"));
											help2.setPaygrp(rsexe.getString("Short_desc"));
											help2.setLeavetype("EXE");
											if(rsexe.getString("DSGSTXT").equalsIgnoreCase("Driver"))
												expemtedDrivers.add(help2);
											else
												expemted.add(help2);
										

											
											
									}
									
								
							   
							
								
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							
							
							String present = query2.replace("%AA%", "%PP%");
							present= present.replace("select *", "select count(*) ");
							ResultSet rs4 = ad.selectQuery(present);
							try {
								if(rs4.next())
								{
									help.setPresize(rs4.getInt(1));
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							//Permission
							String permission = query2.replace("%AA%", "%PM%");
							permission= permission.replace("select * ", "select count(*) ");
							ResultSet rs5 = ad.selectQuery(permission);
							try {
								if(rs5.next())
								{
									help.setPmsize(rs5.getInt(1));
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					 
					 
					 
					//check working day or not
					 String a[]=sqlfromdate.split("-");	
				
					
						String startMonth=a[1];
					
						int staMont=Integer.parseInt(startMonth);
						
					 
		             String   table="select "+monthName[staMont]+" as m from "+getTableName(dd.getString("Location"))+" where DAY=day('"+sqlfromdate+"') and CYEAR=year('"+sqlfromdate+"') ";
		             
		             String value="";
		               
						  ResultSet rstable=ad.selectQuery(table);
						  while(rstable.next())
							  
							{
							  
							  value=rstable.getString("m");
							}
					
					
						  if(value.equalsIgnoreCase("W"))
						  {
							
						       try {
						    	   
						    		String filePath="E:/Attendance_Summary/"+dd.getString("location")+"/";
						    	 	
						    		File destinationDir = new File(filePath);
						    		if(!destinationDir.exists())
						    		{
						    			destinationDir.mkdirs();
						    			
						    			
						    			
						    		}
								 writer = PdfWriter.getInstance(document, new FileOutputStream("E:\\Attendance_Summary\\"+dd.getString("location")+"\\"+dd.getString("location")+"_"+sqlfromdate.replace("/", "-")+".pdf"));
							} catch (FileNotFoundException | DocumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						           document.open();
						           
						           Font largeBold = new Font(Font.FontFamily.COURIER, 10,
						                    Font.BOLD);
						           
						           Font columnheader = new Font(Font.FontFamily.UNDEFINED, 8,
						                    Font.BOLD);
						           Font smallfont = new Font(Font.FontFamily.UNDEFINED,8);
						           
						           Font mediumfont = new Font(Font.FontFamily.UNDEFINED,10);
							  
						           PdfPTable table1 = new PdfPTable(2); // 3 columns.
							        table1.setWidthPercentage(100); //Width 100%
							        table1.setSpacingBefore(10f); //Space before table
							        table1.setSpacingAfter(10f); //Space after table
							        
							        float[] columnWidths1 = {1.0f, 5f};
								      
										try {
											table1.setWidths(columnWidths1);
											 Image image1;
												try {
													
												
													
													String filePath="E:/Attendance_Summary/";
													image1 = Image.getInstance(filePath.replace("/", "\\")+"logo.png");
												
										           //Fixed Positioning
										     
										           //Scale to new height and new width of image
										           image1.scaleAbsolute(40, 40);
										           image1.setAlignment(Element.ALIGN_CENTER);
										           //Add to document
										           PdfPCell t1 = new PdfPCell(image1);
										           t1.setHorizontalAlignment(Element.ALIGN_CENTER);
										           t1.setVerticalAlignment(Element.ALIGN_CENTER);
										         
										   	
										           table1.addCell(t1);
												} catch (IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
												
												 columnheader = new Font(Font.FontFamily.UNDEFINED, 10,
									                    Font.BOLD);
												 largeBold = new Font(Font.FontFamily.UNDEFINED, 12,Font.BOLD);
												
												
												 Paragraph preface1 = new Paragraph("MICRO LABS LIMITED                                                                                                             "
												 		+ " #27, Race Course Road, Bangalore - 560 001",largeBold); 
									      	    preface1.setAlignment(Element.ALIGN_RIGHT);
												PdfPCell t2 = new PdfPCell(preface1);
											
												t2.setHorizontalAlignment(Element.ALIGN_CENTER);
												t2.setVerticalAlignment(Element.ALIGN_MIDDLE);
												
										
												
											    table1.addCell(t2);
											   	   document.add(table1);
										       	     	   
										       	   Paragraph jo=new Paragraph();
										       	   jo.add("Daily Attendance  Report for the Date "+exportdate+"");
										       	jo.setAlignment(Element.ALIGN_CENTER);
										       	jo.setFont(largeBold);
										       	   document.add(jo);
										       	   
							    					
										       	   
										       	   //
										       	   
										       	   table1 = new PdfPTable(2); // 3 columns.
											        table1.setWidthPercentage(30); //Width 100%
											        table1.setSpacingBefore(10f); //Space before table
											        table1.setSpacingAfter(10f); //Space after table
											        
											        float[]   columnWidths2 = {1.0f, 1.0f};
											        table1.setWidths(columnWidths2);
											        PdfPCell p4b = new PdfPCell(new Paragraph("Summary Of Attendance",columnheader));		
											        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
											        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);											        
													p4b.setColspan(2); 
													table1.addCell(p4b);
											    	PdfPCell p4e1 = new PdfPCell(new Paragraph("Present:",smallfont));		
													p4e1.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p4e1);
											    	PdfPCell p4e2 = new PdfPCell(new Paragraph(Integer.toString(help.getPresize()),smallfont));		
											    	p4e2.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p4e2);
													
										       	   
													PdfPCell p4e3 = new PdfPCell(new Paragraph("Absent:",smallfont));		
													p4e3.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p4e3);
											    	PdfPCell p4e4 = new PdfPCell(new Paragraph(Integer.toString(absence.size()),smallfont));		
											    	p4e4.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p4e4);
										       	   
													PdfPCell p4e5 = new PdfPCell(new Paragraph("On Duty:",smallfont));		
													p4e5.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p4e5);
											    	PdfPCell p4e6 = new PdfPCell(new Paragraph(Integer.toString(aonduty.size()),smallfont));		
											    	p4e6.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p4e6);
													
													
													PdfPCell p4e7 = new PdfPCell(new Paragraph("Leave:",smallfont));		
													p4e7.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p4e7);
											    	PdfPCell p4e8 = new PdfPCell(new Paragraph(Integer.toString(aleave.size()),smallfont));		
											    	p4e8.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p4e8);
													
													
													PdfPCell p4e9= new PdfPCell(new Paragraph("Loss Of Pay:",smallfont));		
													p4e9.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p4e9);
											    	PdfPCell p1 = new PdfPCell(new Paragraph(Integer.toString(alop.size()),smallfont));		
											    	p1.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p1);
													
													PdfPCell p2 = new PdfPCell(new Paragraph("Exempted :",smallfont));		
													p2.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p2);
											    	PdfPCell p3 = new PdfPCell(new Paragraph(Integer.toString(expemted.size()+expemtedDrivers.size()),smallfont));		
											    	p3.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p3);
										       	   
													PdfPCell p4 = new PdfPCell(new Paragraph("Total :",smallfont));		
													p4.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p4);
											    	PdfPCell p5 = new PdfPCell(new Paragraph(Integer.toString(help.getPresize()+absence.size()+aonduty.size()+aleave.size()+alop.size()+expemted.size()+expemtedDrivers.size()),smallfont));		
											    	p5.setBorder(com.itextpdf.text.Rectangle.BOX);
													table1.addCell(p5);
													
													 document.add(table1);
													
													 
													 //leave
													 
													   table1 = new PdfPTable(9); // 3 columns.
												        table1.setWidthPercentage(100); //Width 100%
												        table1.setSpacingBefore(10f); //Space before table
												        table1.setSpacingAfter(10f); //Space after table
												        
												        float[]   columnWidths3 = {0.5f, 0.5f, 1.0f, 0.8f,0.7f, 0.7f, 0.7f, 0.7f, 0.7f};
												        table1.setWidths(columnWidths3);
												        
												        p4b = new PdfPCell(new Paragraph("Employee On Leave",columnheader));	
												        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
												        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);
														p4b.setColspan(9); 
														table1.addCell(p4b);
												        
														p5 = new PdfPCell(new Paragraph("#",columnheader));			  
													 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
														table1.addCell(p5);
													
														p5 = new PdfPCell(new Paragraph("Emp No",columnheader));			  
													 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
														table1.addCell(p5);
														
														p5 = new PdfPCell(new Paragraph("Emp Name",columnheader));			  
													 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
														table1.addCell(p5);
													
													
														p5 = new PdfPCell(new Paragraph("Department",columnheader));			  
													 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
														table1.addCell(p5);
													
														p5 = new PdfPCell(new Paragraph("Division",columnheader));			  
													 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
														table1.addCell(p5);
														
														p5 = new PdfPCell(new Paragraph("From Date",columnheader));			  
													 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
														table1.addCell(p5);
														
														
														p5 = new PdfPCell(new Paragraph("To Date",columnheader));			  
													 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
														table1.addCell(p5);
														
														
														p4b = new PdfPCell(new Paragraph("Leave Type",columnheader));			  
													 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
														table1.addCell(p4b);
														
														
														p5 = new PdfPCell(new Paragraph("No Of Days",columnheader));			  
													 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
														table1.addCell(p5);
														
														
														
														 Iterator fr=aleave.iterator();
															for(int k=0;k<aleave.size();k++)
															{
																if(fr.hasNext())
																{
																	HRApprovalForm n=(HRApprovalForm)fr.next();	
																	p5 = new PdfPCell(new Paragraph(Integer.toString(k+1),smallfont));			  
																	 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																		table1.addCell(p5);
																	
																		p5 = new PdfPCell(new Paragraph(n.getEmployeeno(),smallfont));			  
																	 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																		table1.addCell(p5);
																		
																		p5 = new PdfPCell(new Paragraph(n.getEmployeeName(),smallfont));			  
																	 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																		table1.addCell(p5);
																	
																	
																		p5 = new PdfPCell(new Paragraph(n.getDepartment(),smallfont));			  
																	 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																		table1.addCell(p5);
																	
																		p5 = new PdfPCell(new Paragraph(n.getPaygrp(),smallfont));			  
																	 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																		table1.addCell(p5);
																		
																		p5 = new PdfPCell(new Paragraph(n.getFromDate(),smallfont));			  
																	 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																		table1.addCell(p5);
																		
																		
																		p5 = new PdfPCell(new Paragraph(n.getToDate(),smallfont));			  
																	 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																		table1.addCell(p5);
																		
																		
																		p4b = new PdfPCell(new Paragraph(n.getLeavetype(),smallfont));			  
																	 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																		table1.addCell(p4b);
																		
																		
																		p5 = new PdfPCell(new Paragraph(n.getNoOfDays(),smallfont));			  
																	 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																		table1.addCell(p5);
																	
																}
															}
															
															if(aleave.size()==0)
															{
																  p4b = new PdfPCell(new Paragraph("NIL",columnheader));	
															        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
															        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);
																	p4b.setColspan(9); 
																	table1.addCell(p4b);
															}

															 document.add(table1);
															
															 
																//lop
															 
															   table1 = new PdfPTable(9); // 3 columns.
														        table1.setWidthPercentage(100); //Width 100%
														        table1.setSpacingBefore(10f); //Space before table
														        table1.setSpacingAfter(10f); //Space after table
														        
														        float[]   columnWidths4 = {0.5f, 0.5f, 1.0f, 0.8f,0.7f, 0.7f, 0.7f, 0.7f, 0.7f};
														        table1.setWidths(columnWidths4);
														        
														        p4b = new PdfPCell(new Paragraph("Employee On Loss Of Pay",columnheader));	
														        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
														        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);
																p4b.setColspan(9); 
																table1.addCell(p4b);
														        
																p5 = new PdfPCell(new Paragraph("#",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
															
																p5 = new PdfPCell(new Paragraph("Emp No",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
																
																p5 = new PdfPCell(new Paragraph("Emp Name",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
															
															
																p5 = new PdfPCell(new Paragraph("Department",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
															
																p5 = new PdfPCell(new Paragraph("Division",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
																
																p5 = new PdfPCell(new Paragraph("From Date",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
																
																
																p5 = new PdfPCell(new Paragraph("To Date",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
																
																
																p4b = new PdfPCell(new Paragraph("From Time",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p4b);
																
																
																p5 = new PdfPCell(new Paragraph("To Time",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
																
																
																
																 Iterator frq=alop.iterator();
																	for(int k=0;k<alop.size();k++)
																	{
																		if(frq.hasNext())
																		{
																			HRApprovalForm n=(HRApprovalForm)frq.next();	
																			p5 = new PdfPCell(new Paragraph(Integer.toString(k+1),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																			
																				p5 = new PdfPCell(new Paragraph(n.getEmployeeno(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																				
																				p5 = new PdfPCell(new Paragraph(n.getEmployeeName(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																			
																			
																				p5 = new PdfPCell(new Paragraph(n.getDepartment(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																			
																				p5 = new PdfPCell(new Paragraph(n.getPaygrp(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																				
																				p5 = new PdfPCell(new Paragraph(n.getFromDate(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																				
																				
																				p5 = new PdfPCell(new Paragraph(n.getToDate(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																				
																				
																				p4b = new PdfPCell(new Paragraph(n.getStartTime(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p4b);
																				
																				
																				p5 = new PdfPCell(new Paragraph(n.getEndTime(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																			
																		}
																	}
																	
																	if(alop.size()==0)
																	{
																		  p4b = new PdfPCell(new Paragraph("NIL",columnheader));	
																	        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
																	        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);
																			p4b.setColspan(9); 
																			table1.addCell(p4b);
																	}

																	 document.add(table1);
																	
															
															 
															//onduty
															 
															   table1 = new PdfPTable(9); // 3 columns.
														        table1.setWidthPercentage(100); //Width 100%
														        table1.setSpacingBefore(10f); //Space before table
														        table1.setSpacingAfter(10f); //Space after table
														        
														        float[]   columnWidths5 = {0.5f, 0.5f, 1.0f, 0.8f,0.7f, 0.7f, 0.7f, 0.7f, 0.7f};
														        table1.setWidths(columnWidths5);
														        
														        p4b = new PdfPCell(new Paragraph("Employee On Onduty",columnheader));	
														        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
														        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);
																p4b.setColspan(9); 
																table1.addCell(p4b);
														        
																p5 = new PdfPCell(new Paragraph("#",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
															
																p5 = new PdfPCell(new Paragraph("Emp No",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
																
																p5 = new PdfPCell(new Paragraph("Emp Name",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
															
															
																p5 = new PdfPCell(new Paragraph("Department",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
															
																p5 = new PdfPCell(new Paragraph("Division",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
																
																p5 = new PdfPCell(new Paragraph("From Date",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
																
																
																p5 = new PdfPCell(new Paragraph("To Date",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
																
																
																p4b = new PdfPCell(new Paragraph("From Time",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p4b);
																
																
																p5 = new PdfPCell(new Paragraph("To Time",columnheader));			  
															 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																table1.addCell(p5);
																
																
																
																 Iterator fro=aonduty.iterator();
																	for(int k=0;k<aonduty.size();k++)
																	{
																		if(fro.hasNext())
																		{
																			HRApprovalForm n=(HRApprovalForm)fro.next();	
																			p5 = new PdfPCell(new Paragraph(Integer.toString(k+1),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																			
																				p5 = new PdfPCell(new Paragraph(n.getEmployeeno(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																				
																				p5 = new PdfPCell(new Paragraph(n.getEmployeeName(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																			
																			
																				p5 = new PdfPCell(new Paragraph(n.getDepartment(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																			
																				p5 = new PdfPCell(new Paragraph(n.getPaygrp(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																				
																				p5 = new PdfPCell(new Paragraph(n.getFromDate(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																				
																				
																				p5 = new PdfPCell(new Paragraph(n.getToDate(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																				
																				
																				p4b = new PdfPCell(new Paragraph(n.getStartTime(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p4b);
																				
																				
																				p5 = new PdfPCell(new Paragraph(n.getEndTime(),smallfont));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																			
																		}
																	}

																	if(aonduty.size()==0)
																	{
																		  p4b = new PdfPCell(new Paragraph("NIL",columnheader));	
																	        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
																	        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);
																			p4b.setColspan(9); 
																			table1.addCell(p4b);
																	}
																	
																	 document.add(table1);
																	 
																	 
																		//absentees
																		 
																		   table1 = new PdfPTable(6); // 3 columns.
																	        table1.setWidthPercentage(100); //Width 100%
																	        table1.setSpacingBefore(10f); //Space before table
																	        table1.setSpacingAfter(10f); //Space after table
																	        
																	        float[]   columnWidths6 = {0.5f, 0.5f, 1.0f, 1.0f,1.0f, 1.0f };
																	        table1.setWidths(columnWidths6);
																	        
																	        p4b = new PdfPCell(new Paragraph("Absent Employees",columnheader));	
																	        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
																	        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);
																			p4b.setColspan(9); 
																			table1.addCell(p4b);
																	        
																			p5 = new PdfPCell(new Paragraph("#",columnheader));			  
																		 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																			table1.addCell(p5);
																		
																			p5 = new PdfPCell(new Paragraph("Emp No",columnheader));			  
																		 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																			table1.addCell(p5);
																			
																			p5 = new PdfPCell(new Paragraph("Emp Name",columnheader));			  
																		 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																			table1.addCell(p5);
																		
																		
																			p5 = new PdfPCell(new Paragraph("Department",columnheader));			  
																		 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																			table1.addCell(p5);
																		
																			p5 = new PdfPCell(new Paragraph("Designation",columnheader));			  
																			 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																				table1.addCell(p5);
																			
																			p5 = new PdfPCell(new Paragraph("Division",columnheader));			  
																		 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																			table1.addCell(p5);
																			
																		
																			
																			
																			
																			 Iterator frab=absence.iterator();
																				for(int k=0;k<absence.size();k++)
																				{
																					if(frab.hasNext())
																					{
																						HRApprovalForm n=(HRApprovalForm)frab.next();	
																						p5 = new PdfPCell(new Paragraph(Integer.toString(k+1),smallfont));			  
																						 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																							table1.addCell(p5);
																						
																							p5 = new PdfPCell(new Paragraph(n.getEmployeeno(),smallfont));			  
																						 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																							table1.addCell(p5);
																							
																							p5 = new PdfPCell(new Paragraph(n.getEmployeeName(),smallfont));			  
																						 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																							table1.addCell(p5);
																							
																							p5 = new PdfPCell(new Paragraph(n.getDepartment(),smallfont));			  
																							 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																								table1.addCell(p5);
																						
																							p5 = new PdfPCell(new Paragraph(n.getDesignation(),smallfont));			  
																							 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																								table1.addCell(p5);
																							
																						
																							p5 = new PdfPCell(new Paragraph(n.getPaygrp(),smallfont));			  
																						 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																							table1.addCell(p5);
																						
																						
																						
																					}
																				}
																				
																				if(absence.size()==0)
																				{
																					  p4b = new PdfPCell(new Paragraph("NIL",columnheader));	
																				        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
																				        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);
																						p4b.setColspan(9); 
																						table1.addCell(p4b);
																				}

																				 document.add(table1);										 
																	 
																	 
																				 if(dd.getString("location").equalsIgnoreCase("ML00"))
																				 
																				 {	//Exempted
																					 
																					   table1 = new PdfPTable(6); // 3 columns.
																				        table1.setWidthPercentage(100); //Width 100%
																				        table1.setSpacingBefore(10f); //Space before table
																				        table1.setSpacingAfter(10f); //Space after table
																				        
																				        float[]   columnWidths7 = {0.5f, 0.5f, 1.0f, 1.0f,1.0f, 1.0f };
																				        table1.setWidths(columnWidths7);
																				        
																				        p4b = new PdfPCell(new Paragraph("Exempted Employees",columnheader));	
																				        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
																				        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);
																						p4b.setColspan(9); 
																						table1.addCell(p4b);
																				        
																						p5 = new PdfPCell(new Paragraph("#",columnheader));			  
																					 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																						table1.addCell(p5);
																					
																						p5 = new PdfPCell(new Paragraph("Emp No",columnheader));			  
																					 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																						table1.addCell(p5);
																						
																						p5 = new PdfPCell(new Paragraph("Emp Name",columnheader));			  
																					 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																						table1.addCell(p5);
																					
																					
																						p5 = new PdfPCell(new Paragraph("Department",columnheader));			  
																					 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																						table1.addCell(p5);
																					
																						p5 = new PdfPCell(new Paragraph("Designation",columnheader));			  
																						 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																							table1.addCell(p5);
																						
																						p5 = new PdfPCell(new Paragraph("Division",columnheader));			  
																					 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																						table1.addCell(p5);
																						
																					
																						
																						
																						
																						 Iterator frex=expemted.iterator();
																							for(int k=0;k<expemted.size();k++)
																							{
																								if(frex.hasNext())
																								{
																									HRApprovalForm n=(HRApprovalForm)frex.next();	
																									p5 = new PdfPCell(new Paragraph(Integer.toString(k+1),smallfont));			  
																									 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																										table1.addCell(p5);
																									
																										p5 = new PdfPCell(new Paragraph(n.getEmployeeno(),smallfont));			  
																									 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																										table1.addCell(p5);
																										
																										p5 = new PdfPCell(new Paragraph(n.getEmployeeName(),smallfont));			  
																									 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																										table1.addCell(p5);
																										
																										p5 = new PdfPCell(new Paragraph(n.getDepartment(),smallfont));			  
																										 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																											table1.addCell(p5);
																									
																										p5 = new PdfPCell(new Paragraph(n.getDesignation(),smallfont));			  
																										 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																											table1.addCell(p5);
																										
																									
																									
																										
																										p5 = new PdfPCell(new Paragraph(n.getPaygrp(),smallfont));			  
																										 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																											table1.addCell(p5);
																									
																									
																								}
																							}

																							if(expemted.size()==0)
																							{
																								  p4b = new PdfPCell(new Paragraph("NIL",columnheader));	
																							        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
																							        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);
																									p4b.setColspan(9); 
																									table1.addCell(p4b);
																							}
																							
																							 document.add(table1);		
																							 
																							//Exempted Drivers
																							 
																							   table1 = new PdfPTable(6); // 3 columns.
																						        table1.setWidthPercentage(100); //Width 100%
																						        table1.setSpacingBefore(10f); //Space before table
																						        table1.setSpacingAfter(10f); //Space after table
																						        
																						        float[]   columnWidths8 = {0.5f, 0.5f, 1.0f, 1.0f,1.0f, 1.0f };
																						        table1.setWidths(columnWidths8);
																						        
																						        p4b = new PdfPCell(new Paragraph("Exempted Employees - Drivers",columnheader));	
																						        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
																						        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);
																								p4b.setColspan(9); 
																								table1.addCell(p4b);
																						        
																								p5 = new PdfPCell(new Paragraph("#",columnheader));			  
																							 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																								table1.addCell(p5);
																							
																								p5 = new PdfPCell(new Paragraph("Emp No",columnheader));			  
																							 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																								table1.addCell(p5);
																								
																								p5 = new PdfPCell(new Paragraph("Emp Name",columnheader));			  
																							 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																								table1.addCell(p5);
																							
																							
																								p5 = new PdfPCell(new Paragraph("Department",columnheader));			  
																							 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																								table1.addCell(p5);
																							
																								p5 = new PdfPCell(new Paragraph("Designation",columnheader));			  
																								 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																									table1.addCell(p5);
																								
																								p5 = new PdfPCell(new Paragraph("Division",columnheader));			  
																							 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																								table1.addCell(p5);
																								
																							
																								
																								
																								
																								 Iterator frexd=expemtedDrivers.iterator();
																									for(int k=0;k<expemtedDrivers.size();k++)
																									{
																										if(frexd.hasNext())
																										{
																											HRApprovalForm n=(HRApprovalForm)frexd.next();	
																											p5 = new PdfPCell(new Paragraph(Integer.toString(k+1),smallfont));			  
																											 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																												table1.addCell(p5);
																											
																												p5 = new PdfPCell(new Paragraph(n.getEmployeeno(),smallfont));			  
																											 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																												table1.addCell(p5);
																												
																												p5 = new PdfPCell(new Paragraph(n.getEmployeeName(),smallfont));			  
																											 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																												table1.addCell(p5);
																												
																												p5 = new PdfPCell(new Paragraph(n.getDepartment(),smallfont));			  
																												 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																													table1.addCell(p5);
																											
																												p5 = new PdfPCell(new Paragraph(n.getDesignation(),smallfont));			  
																												 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																													table1.addCell(p5);
																												
																											
																											
																												
																												p5 = new PdfPCell(new Paragraph(n.getPaygrp(),smallfont));			  
																												 p5.setBorder(com.itextpdf.text.Rectangle.BOX);
																													table1.addCell(p5);
																											
																											
																										}
																									}

																									if(expemted.size()==0)
																									{
																										  p4b = new PdfPCell(new Paragraph("NIL",columnheader));	
																									        p4b.setHorizontalAlignment(Element.ALIGN_CENTER);
																									        p4b.setVerticalAlignment(Element.ALIGN_MIDDLE);
																											p4b.setColspan(9); 
																											table1.addCell(p4b);
																									}
																									
																									 document.add(table1);			
																							 
																				 }
															 
													  document.close();
												      writer.close();
										       	   
												    //Send mail to head
												       sendMailToHead(dd.getString("Mail_To"),dd.getString("Mail_To_CC"),dd.getString("Mail_To_BCC"), "E:\\Attendance_Summary\\"+dd.getString("location")+"\\"+dd.getString("location")+"_"+sqlfromdate.replace("/", "-")+".pdf", dd.getString("Displayname"),help.getPresize(),absence.size(),aonduty.size(),aleave.size(),alop.size(),expemted.size(),expemtedDrivers.size(),exportdate);
												      
							       
							    
							  
						  } catch (DocumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				   	
				}}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
				
			
	
	System.out.println("Ended Attendance Summary Task");
	}
	
	
	public  static int sendMailToHead( String approvermail,String ccapprovermail,String bccapprovermail, String filepath, String plant,int present,int absent,int onduty,int leave,int lop,int exempted,int exempteddriver,String exactdate) {
		
		 MailInboxForm mailForm = new MailInboxForm();
		 
			
		 
		 mailForm.setToAddress(approvermail);
		 System.out.println("m -> "+approvermail);
		 mailForm.setccAddress(ccapprovermail);
		 mailForm.setbccAddress(bccapprovermail);
		 mailForm.setSubject("Daily Attendance Information of "+plant+"  as  on "+exactdate+"  ");
		 String desc = "";
		 desc=desc+"<html><body><p><b>Dear Sir/Madam</b><br> <br>Please find the attachment for Detailed Attendance  information for Plant: "+plant+" on "+exactdate+" </p>"
		 		+ "<img src="+"http://portal.microlabs.co.in/images/logo.png"+" height=50 width=40  /></br><table><tr><th colspan=2 ><center>Summary Of Attendance</center></th></tr>";

	
		 

			desc = desc+"<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
		             +"TABLE {border-collapse:collapse;border:1px solid black;}"
		             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
		             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
				 +"</STYLE>";
			
			
			desc=desc+"<tr><td>Present:</td><td>"+present+"</td></tr>";
			desc=desc+"<tr><td>Absent:</td><td>"+absent+"</td></tr>";
			desc=desc+"<tr><td>On Duty:</td><td>"+onduty+"</td></tr>";
			desc=desc+"<tr><td>Leave:</td><td>"+leave+"</td></tr>";
			desc=desc+"<tr><td>Loss Of Pay:</td><td>"+lop+"</td></tr>";
			desc=desc+"<tr><td>Exempted:</td><td>"+(exempted+exempteddriver)+"</td></tr>";
			desc=desc+"<tr><td><b>Total:</b></td><td>"+(present+absent+onduty+leave+lop+exempted+exempteddriver)+"</td></tr>";
		
		desc=desc+"</table><br><br>";
			desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"http://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
			desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This is an Auto generated Email Please do not Reply .</span> ";
		
		
			
		 mailForm.setDescription(desc);
		 
		 try {
			try {
				
				sendattendanceMail( mailForm, filepath,"emicro",plant,exactdate);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 return 1;
	}

	
	 public static String sendattendanceMail( MailInboxForm mailSentForm, String filepath, String USER_NAME,String loc,String date) throws IOException, ServletException, SQLException, MessagingException
		{
			String HOST_NAME = "mail.microlabs.co.in";	
	    	final String DEFAULT_ID = "emicro@microlabs.in";
	    	String protocol ="smtp";
	    	password = "micro1@";
	    	//password = getUserPassword(request);
	    	/*if(!mailfrom.equalsIgnoreCase("forgetpass")){
	    	password = getUserPassword(request);
	    	}*/
			String sucMessage="succuss";
			//for multiple to,cc,bcc address
			String to = mailSentForm.getToAddress();
			String cc = mailSentForm.getccAddress();
			String bcc= mailSentForm.getbccAddress();
			
			
			String port = "25";//request.getServerPort();
		
			Properties props = new Properties();
			props.put("mail.smtp.host",HOST_NAME);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.socketFactory.port", port);
			
			
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					   protected PasswordAuthentication getPasswordAuthentication() {
					   return new PasswordAuthentication(DEFAULT_ID,password);//employee company email address and password
					   }
			});
			//compose message
			  try {
			   MimeMessage message = new MimeMessage(session);
			   message.setFrom(new InternetAddress(DEFAULT_ID));//employee company email address
			   message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			   message.addRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));
			   message.addRecipients(Message.RecipientType.BCC,InternetAddress.parse(bcc));
			   message.setSubject(mailSentForm.getSubject());
			   
			   //Description
			   Multipart mp = new MimeMultipart();
			   MimeBodyPart descripPart = new MimeBodyPart();
			   System.out.println(mailSentForm.getDescription());
			   descripPart.setContent(mailSentForm.getDescription(), "text/html");
		       mp.addBodyPart(descripPart);
			   
			   
			   //For Attachment
		       File fgf = new File(filepath);
		       
				   if (filepath != null ) {
			  {
			            	MimeBodyPart attachBodyPart = new MimeBodyPart();
			     		    DataSource source = new FileDataSource(fgf);
			     		    attachBodyPart.setDataHandler(new DataHandler(source));
			     		    attachBodyPart.setFileName("Attendance_info_"+loc+"_"+date+""+".pdf");
			                mp.addBodyPart(attachBodyPart);
			            
			        }
		       
			   
			   message.setContent(mp);
			   session.setDebug(true);
			   //Service sr = new Service(session,)
			   //send message
			   Transport transport = session.getTransport(protocol);
			   
			   transport.connect();
			   transport.sendMessage(message, message.getAllRecipients());
			   transport.close();
			   
			   ///delete attAHCMENETS IN SERVER
			   
			  /* File fileToCreate = new File(filepath);
					 if(fileToCreate.delete()){
			    			System.out.println(" is deleted!");
			    		}else{
			    			System.out.println("Delete operation is failed.");
			    		}*/
			   
			   //Transport.send(message);
			 
				   }}
			  catch (NoSuchProviderException e1) {
				  sucMessage = "Mail not Sent";
				  System.out.println("Exception @ mail sending");
			  e1.printStackTrace();}
			  catch (MessagingException e) {
				  sucMessage = "Mail not Sent";
				  System.out.println("Exception @ mail sending");
					
			  		e.printStackTrace();
			 }
			 catch (NullPointerException npe) {
				 sucMessage = "Mail not Sent";
				 System.out.println("Exception @ mail sending");
					
			  		npe.printStackTrace();
			 }
			  /*finally {
		            deleteUploadFiles(uploadedFiles);
		        }*/
			return null;
		}
	
	}
	
	


