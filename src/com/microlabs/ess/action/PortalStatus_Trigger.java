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

public class PortalStatus_Trigger {
	 static 	AttDao ad=new AttDao();
	 private static String fromAddress;
		private  static String password; 
		public int MAIL_PER_PAGE = 10;
		private String USER_ID;
		

	
		
	
		
		
	
	public static void main(String[] args) {
		
		HRApprovalForm help=new HRApprovalForm();
		  
		
		System.out.println("Started PORTAL STATUS task");
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ");
		String datenw = sdf.format(d);
		String tran="insert into Schedule_Transaction values('PORTAL STATUS','"+datenw+"')";
		int j=ad.SqlExecuteUpdate(tran);
		
	
					
				
			
					
					
					
						
						
				            
				           
					
										       	   
												    //Send mail to head
	   sendMailToHead();
												      
	

			
	
	System.out.println("Ended PORTAL STATUS  Task");
	}
	
	
	public  static int sendMailToHead( ) {
		
		 MailInboxForm mailForm = new MailInboxForm();
		 
			
		 
		 mailForm.setToAddress("shivaji.kapade@microlabs.in,ramakrishnan@microlabs.in");
		 mailForm.setccAddress("hrms@microlabs.in,vasudevan@microlabs.in");
		 mailForm.setbccAddress("emicro@microlabs.in,rvkumar@microlabs.in");
		 mailForm.setSubject("Daily Portal Personal  Information  Status  ");
		 String desc = "";
		 desc=desc+"<html><body><p><b>Dear Sir/Madam</b><br> "
		 		+ "<br>For  Detailed report  follow below path in EMicro Portal <br>"
		 		+ " Attendance Reports -> Portal Status Report </p>"
		 		+ "<img src="+"http://portal.microlabs.co.in/images/logo.png"+" height=50 width=40  /></br>"
		 				+ "<table>";

	
		 

			desc = desc+"<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
		             +"TABLE {border-collapse:collapse;border:1px solid black;}"
		             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
		             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
				 +"</STYLE>";
			
			
			desc=desc+"<tr><th>Location</th><th>Total</th><th>Personal Info</th><th>Personal Info Comp (%)</th>"
					+ "<th>Address Info</th><th>Address Info Comp (%)</th>"
					+ "<th>Family Info</th><th>Family Info Comp (%)</th>"
					+ "<th>Education Info</th><th>Education Info Comp (%)</th>"
					+ "<th>Experience Info</th><th>Experience Info Comp (%)</th>"
					+ "<th>Language Info</th><th>Language Info Comp (%)</th></tr>";
			//leaves
				String query ="select locid,count(distinct emp_official_info.PERNR) as tot,count(distinct emp_personal_info.user_id) as pers,round((cast((count(distinct emp_personal_info.user_id))as float)/ cast((count(distinct emp_official_info.PERNR)) as float) *100),2) as percomp,count( distinct emp_address.user_id) as addr,round((cast((count(distinct emp_address.user_id))as float)/ cast((count(distinct emp_official_info.PERNR)) as float) *100),2) as addcomp,count( distinct emp_family_details.user_id) as fam,round((cast((count(distinct emp_family_details.user_id))as float)/ cast((count(distinct emp_official_info.PERNR)) as float) *100),2) as famcomp,count(distinct emp_education_details.user_id) as edu,round((cast((count(distinct emp_education_details.user_id))as float)/ cast((count(distinct emp_official_info.PERNR)) as float) *100),2) as educomp,count(distinct emp_experience_details.user_id) as exp,round((cast((count(distinct emp_experience_details.user_id))as float)/ cast((count(distinct emp_official_info.PERNR)) as float) *100),2) as expcomp,count( distinct emp_language_details.user_id) as lan ,round((cast((count(distinct emp_language_details.user_id))as float)/ cast((count(distinct emp_official_info.PERNR)) as float) *100),2) as lancomp from emp_official_info left outer join emp_personal_info on   emp_personal_info.user_id=emp_official_info.PERNR left outer join emp_address on   emp_address.user_id=emp_official_info.PERNR left outer join emp_family_details on   emp_family_details.user_id=emp_official_info.PERNR left outer join emp_education_details on   emp_education_details.user_id=emp_official_info.PERNR left outer join emp_experience_details on   emp_experience_details.user_id=emp_official_info.PERNR left outer join emp_language_details on   emp_language_details.user_id=emp_official_info.PERNR  where emp_official_info.ACTIVE=1  and  locid='ML00' and Reporting_Grp=10 group by locid union select locid,count(distinct emp_official_info.PERNR) as tot,count(distinct emp_personal_info.user_id) as pers,round((cast((count(distinct emp_personal_info.user_id))as float)/ cast((count(distinct emp_official_info.PERNR)) as float) *100),2) as percomp,count( distinct emp_address.user_id) as addr,round((cast((count(distinct emp_address.user_id))as float)/ cast((count(distinct emp_official_info.PERNR)) as float) *100),2) as addcomp,count( distinct emp_family_details.user_id) as fam,round((cast((count(distinct emp_family_details.user_id))as float)/ cast((count(distinct emp_official_info.PERNR)) as float) *100),2) as famcomp,count(distinct emp_education_details.user_id) as edu,round((cast((count(distinct emp_education_details.user_id))as float)/ cast((count(distinct emp_official_info.PERNR)) as float) *100),2) as educomp,count(distinct emp_experience_details.user_id) as exp,round((cast((count(distinct emp_experience_details.user_id))as float)/ cast((count(distinct emp_official_info.PERNR)) as float) *100),2) as expcomp,count( distinct emp_language_details.user_id) as lan ,round((cast((count(distinct emp_language_details.user_id))as float)/ cast((count(distinct emp_official_info.PERNR)) as float) *100),2) as lancomp from emp_official_info left outer join emp_personal_info on   emp_personal_info.user_id=emp_official_info.PERNR left outer join emp_address on   emp_address.user_id=emp_official_info.PERNR left outer join emp_family_details on   emp_family_details.user_id=emp_official_info.PERNR left outer join emp_education_details on   emp_education_details.user_id=emp_official_info.PERNR left outer join emp_experience_details on   emp_experience_details.user_id=emp_official_info.PERNR left outer join emp_language_details on   emp_language_details.user_id=emp_official_info.PERNR  where emp_official_info.ACTIVE=1  and    locid!='ML00'  group by locid order by 1" ;
				
			ResultSet af=ad.selectQuery(query);
			try {
				while(af.next())
				{
					desc=desc+"<tr>"
							+ "<td><b>"+af.getString("locid")+"</b></td>"
							+ "<td>"+af.getString("tot")+"</td>"
						    + "<td>"+af.getString("pers")+"</td>"
						    + "<td><b>"+af.getString("percomp")+"</b></td>"
						     + "<td>"+af.getString("addr")+"</td>"
						       + "<td><b>"+af.getString("addcomp")+"</b></td>"
						         + "<td>"+af.getString("fam")+"</td>"
						        + "<td><b>"+af.getString("famcomp")+"</b></td>"
						      + "<td>"+af.getString("edu")+"</td>"
						        + "<td><b>"+af.getString("educomp")+"</b></td>"    
						         + "<td>"+af.getString("exp")+"</td>"
						        + "<td><b>"+af.getString("expcomp")+"</b></td>"
						        + "<td>"+af.getString("lan")+"</td>"
						        + "<td><b>"+af.getString("lancomp")+"</b></td>"
									+ "</tr>";	
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
				
				
			

			
			
		
		desc=desc+"</table><br><br>";
			desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"http://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
			desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This is an Auto generated Email Please do not Reply .</span> ";
		
		
			
		 mailForm.setDescription(desc);
		 
		 try {
			try {
				
				sendattendanceMail( mailForm, "","emicro","","");
				
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
	
	


