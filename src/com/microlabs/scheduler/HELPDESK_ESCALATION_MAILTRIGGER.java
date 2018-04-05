package com.microlabs.scheduler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

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

import com.microlabs.ess.dao.EssDao;
import com.microlabs.it.form.IssuesForm;
import com.microlabs.main.form.MailInboxForm;

public class HELPDESK_ESCALATION_MAILTRIGGER {
	static EssDao ad = EssDao.dBConnection();
	private static Connection conn=null;
	private  static String password; 
	public static void main(String[] args) {
		
		

		HELPDESK_ESCALATION_MAILTRIGGER dao=new HELPDESK_ESCALATION_MAILTRIGGER();
	    
					
					System.out.println("Started ESCALATION MAIL TRIGGER task");
				
					
					LinkedList l1=new LinkedList();
					LinkedList l2=new LinkedList();
					LinkedList l3=new LinkedList();
					
					String reqnos = "";
					Date d = new Date();
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
					String exactDate=sdf1.format(d);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ");
					String datenw = sdf.format(d);
					String tran="insert into Schedule_Transaction values('ESCALATION MAIL TRIGGER','"+datenw+"')";
					int j=ad.SqlExecuteUpdate(tran);
				
					
					String emailid="";
					String emailid1="";
					String emailid2="";
					int chek=0;
					int chek1=0;
					int chek2=0;
					
					
					///1 level technicians
				
					String a="SELECT IT_ISSUES.Subject,IT_ISSUES.Req_Priority,e2.LOCID,e2.EMP_FULLNAME as username,e1.EMAIL_ID,(CASE WHEN charindex(',', AssignTo)> 0 "
							+ "THEN reverse(left(reverse(AssignTo), charindex(',', reverse(AssignTo)) -1))ELSE AssignTo END) as techid,e1.EMP_FULLNAME as tech,"
							+ "Req_No,Sub_Category,Req_date,Req_type,Req_Status,approved_date from IT_ISSUES,emp_official_info  as e1,emp_official_info as e2 "
							+ "where Escalated_Level=3 and Req_Status!='Completed' and e2.PERNR=IT_ISSUES.Emp_No and e1.PERNR=(CASE WHEN charindex(',', AssignTo)> 0 "
							+ "THEN reverse(left(reverse(AssignTo), charindex(',', reverse(AssignTo)) -1))ELSE AssignTo END) and e1.EMAIL_ID!='' group by e1.EMAIL_ID,"
							+ "(CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo), charindex(',', reverse(AssignTo)) -1))ELSE AssignTo END),"
							+ "e1.EMP_FULLNAME ,Req_No,Sub_Category,Req_date,Req_type,Req_Status,approved_date,e2.LOCID,e2.EMP_FULLNAME,IT_ISSUES.Subject,"
							+ "IT_ISSUES.Req_Priority";
					
					ResultSet rsIssues = ad.selectQuery(a);
                    try {
						while(rsIssues.next())
						{
							IssuesForm form1=new IssuesForm();
					    	form1.setRequestNo(rsIssues.getInt("Req_No"));
					    	form1.setLocation(rsIssues.getString("LOCID"));
					    	
					    	String ReqDate=rsIssues.getString("Req_date");
					    	String a1[]=ReqDate.split(" "); 
							ReqDate=a1[0];
							String b[]=ReqDate.split("-");
							ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a1[1].substring(0, 5);
							form1.setReqDate(ReqDate);
							form1.setEmployeename(rsIssues.getString("username"));
							form1.setTechnicianName(rsIssues.getString("tech"));
							form1.setCategory(rsIssues.getString("Req_Type"));
							form1.setSubcategory(rsIssues.getString("Sub_Category"));
							form1.setSubject(rsIssues.getString("Subject"));
							form1.setRequestStatus(rsIssues.getString("Req_Status"));
							
							form1.setReqPriority(rsIssues.getString("Req_Priority"));
							String appDate="";
							if(!rsIssues.getString("approved_date").contains("1900"))
							{	
							 appDate=rsIssues.getString("approved_date");
							String c[]=appDate.split(" "); 
							appDate=c[0];
							String d1[]=appDate.split("-");
							appDate=d1[2]+"/"+d1[1]+"/"+d1[0]+" "+c[1].substring(0, 5);
					        form1.setApprovedDate(appDate);
							}else{
								  form1.setApprovedDate("");
							}
					        
					        form1.setEmpEmailID(rsIssues.getString("EMAIL_ID"));
					        
					        if(!rsIssues.getString("EMAIL_ID").equalsIgnoreCase(emailid)&&chek==1)
					        {
					        	sendMailTosalesHead(emailid, l1, exactDate,"1");					        	
					        	l1.clear();
					        }
					        emailid=rsIssues.getString("EMAIL_ID");
					        chek=1;
							
							
							l1.add(form1);
						}
						
						if(l1.size()>0)
						sendMailTosalesHead(emailid, l1, exactDate,"1");
						
			        	l1.clear();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
					
                    
                  ///2 level ESCALATION
    				
					String a1="SELECT IT_ISSUES.Subject,IT_ISSUES.Req_Priority,e2.LOCID,e2.EMP_FULLNAME as username,Escalation_MAIL_MAPPING.Escalation1,"
							+ "(CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo), charindex(',', reverse(AssignTo)) -1))ELSE AssignTo END)"
							+ " as techid,e1.EMP_FULLNAME as tech,Req_No,Sub_Category,Req_date,Req_type,Req_Status,approved_date from IT_ISSUES,emp_official_info "
							+ " as e1,emp_official_info as e2,Escalation_MAIL_MAPPING where Escalation_MAIL_MAPPING.Category=IT_ISSUES.Req_type and Escalated_Level=2 "
							+ "and Req_Status!='Completed' and e2.PERNR=IT_ISSUES.Emp_No and e1.PERNR=(CASE WHEN charindex(',', AssignTo)> 0 THEN"
							+ " reverse(left(reverse(AssignTo), charindex(',', reverse(AssignTo)) -1))ELSE AssignTo END) and e1.EMAIL_ID!='' group by "
							+ "Escalation_MAIL_MAPPING.Escalation1,(CASE WHEN charindex(',', AssignTo)> 0 "
							+ "THEN reverse(left(reverse(AssignTo), charindex(',', reverse(AssignTo)) -1))ELSE AssignTo END),e1.EMP_FULLNAME ,Req_No,"
							+ "Sub_Category,Req_date,Req_type,Req_Status,approved_date,e2.LOCID,e2.EMP_FULLNAME,IT_ISSUES.Subject,IT_ISSUES.Req_Priority";
					
					ResultSet rsIssues1 = ad.selectQuery(a1);
                    try {
						while(rsIssues1.next())
						{
							IssuesForm form1=new IssuesForm();
					    	form1.setRequestNo(rsIssues1.getInt("Req_No"));
					    	form1.setLocation(rsIssues1.getString("LOCID"));
					    	
					    	String ReqDate=rsIssues1.getString("Req_date");
					    	String aw1[]=ReqDate.split(" "); 
							ReqDate=aw1[0];
							String b[]=ReqDate.split("-");
							ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+aw1[1].substring(0, 5);
							form1.setReqDate(ReqDate);
							form1.setEmployeename(rsIssues1.getString("username"));
							form1.setTechnicianName(rsIssues1.getString("tech"));
							form1.setCategory(rsIssues1.getString("Req_Type"));
							form1.setSubcategory(rsIssues1.getString("Sub_Category"));
							form1.setSubject(rsIssues1.getString("Subject"));
							form1.setRequestStatus(rsIssues1.getString("Req_Status"));
							
							form1.setReqPriority(rsIssues1.getString("Req_Priority"));
							String appDate="";
							if(!rsIssues1.getString("approved_date").contains("1900"))
							{	
							 appDate=rsIssues1.getString("approved_date");
							String c[]=appDate.split(" "); 
							appDate=c[0];
							String d1[]=appDate.split("-");
							appDate=d1[2]+"/"+d1[1]+"/"+d1[0]+" "+c[1].substring(0, 5);
					        form1.setApprovedDate(appDate);
							}else{
								  form1.setApprovedDate("");
							}
					        
					        form1.setEmpEmailID(rsIssues1.getString("Escalation1"));
					        
					        if(!rsIssues1.getString("Escalation1").equalsIgnoreCase(emailid1)&&chek1==1)
					        {
					        	sendMailTosalesHead(emailid1, l2, exactDate,"2");					        	
					        	l2.clear();
					        }
					        emailid1=rsIssues1.getString("Escalation1");
					        chek1=1;
							
							
					        l2.add(form1);
						}
						
						if(l2.size()>0)
						sendMailTosalesHead(emailid1, l2, exactDate,"2");	
						
			        	l2.clear();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
                    
                    
                    
                    
 ///3 level ESCALATION
    				
					String a2="SELECT IT_ISSUES.Subject,IT_ISSUES.Req_Priority,e2.LOCID,e2.EMP_FULLNAME as username,Escalation_MAIL_MAPPING.Escalation2,"
							+ "(CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo), charindex(',', reverse(AssignTo)) -1))ELSE AssignTo END) "
							+ "as techid,e1.EMP_FULLNAME as tech,Req_No,Sub_Category,Req_date,Req_type,Req_Status,approved_date from IT_ISSUES,emp_official_info  as e1"
							+ ",emp_official_info as e2,Escalation_MAIL_MAPPING where Escalation_MAIL_MAPPING.Category=IT_ISSUES.Req_type and Escalated_Level=1 and"
							+ " Req_Status!='Completed' and e2.PERNR=IT_ISSUES.Emp_No and e1.PERNR=(CASE WHEN charindex(',', AssignTo)> 0 "
							+ "THEN reverse(left(reverse(AssignTo), charindex(',', reverse(AssignTo)) -1))ELSE AssignTo END) and e1.EMAIL_ID!='' "
							+ "group by Escalation_MAIL_MAPPING.Escalation2,(CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo),"
							+ " charindex(',', reverse(AssignTo)) -1))ELSE AssignTo END),e1.EMP_FULLNAME ,Req_No,Sub_Category,Req_date,Req_type,Req_Status,"
							+ "approved_date,e2.LOCID,e2.EMP_FULLNAME,IT_ISSUES.Subject,IT_ISSUES.Req_Priority";
					
					ResultSet rsIssues2 = ad.selectQuery(a2);
                    try {
						while(rsIssues2.next())
						{
							IssuesForm form1=new IssuesForm();
					    	form1.setRequestNo(rsIssues2.getInt("Req_No"));
					    	form1.setLocation(rsIssues2.getString("LOCID"));
					    	
					    	String ReqDate=rsIssues2.getString("Req_date");
					    	String aw1[]=ReqDate.split(" "); 
							ReqDate=aw1[0];
							String b[]=ReqDate.split("-");
							ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+aw1[1].substring(0, 5);
							form1.setReqDate(ReqDate);
							form1.setEmployeename(rsIssues2.getString("username"));
							form1.setTechnicianName(rsIssues2.getString("tech"));
							form1.setCategory(rsIssues2.getString("Req_Type"));
							form1.setSubcategory(rsIssues2.getString("Sub_Category"));
							form1.setSubject(rsIssues2.getString("Subject"));
							form1.setRequestStatus(rsIssues2.getString("Req_Status"));
							
							form1.setReqPriority(rsIssues2.getString("Req_Priority"));
							String appDate="";
							if(!rsIssues2.getString("approved_date").contains("1900"))
							{	
							 appDate=rsIssues2.getString("approved_date");
							String c[]=appDate.split(" "); 
							appDate=c[0];
							String d1[]=appDate.split("-");
							appDate=d1[2]+"/"+d1[1]+"/"+d1[0]+" "+c[1].substring(0, 5);
					        form1.setApprovedDate(appDate);
							}else{
								  form1.setApprovedDate("");
							}
					        
					        form1.setEmpEmailID(rsIssues2.getString("Escalation2"));
					        
					        if(!rsIssues2.getString("Escalation2").equalsIgnoreCase(emailid2)&&chek2==1)
					        {
					        	sendMailTosalesHead(emailid2, l3, exactDate,"3");					        	
					        	l3.clear();
					        }
					        emailid2=rsIssues2.getString("Escalation2");
					        chek2=1;
							
							
					        l3.add(form1);
						}
						
						if(l3.size()>0)
						sendMailTosalesHead(emailid2, l3, exactDate,"3");		
						
			        	l3.clear();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
					
					System.out.println("Ended ESCALATION MAIL TRIGGER Task");
		
		
	
	}
	
	
	public  static int sendMailTosalesHead( String approvermail,LinkedList all,String exactdate,String level) {
		
		 MailInboxForm mailForm = new MailInboxForm();
		 
		 
			
		 
		 mailForm.setToAddress(approvermail);
		 System.out.println("m -> "+approvermail);
		 mailForm.setccAddress("");
		 mailForm.setbccAddress("emicro@microlabs.in");
		 
		 if(level.equalsIgnoreCase("1"))
		 mailForm.setSubject("Helpdesk SLA violated Notification  List as on "+exactdate+"  ");
		 if(level.equalsIgnoreCase("2"))
		 mailForm.setSubject("Helpdesk SLA violated Escalation - 1 List as on "+exactdate+"  ");
		 if(level.equalsIgnoreCase("3"))
		 mailForm.setSubject("Helpdesk SLA violated Escalation - 2 List as on "+exactdate+"  ");
		 
		 String desc = "";
		 desc=desc+"<html><body>";
		 
		

		desc=desc+"<table><tr><th ><b>Req.No</b></th><th ><b>Req.Date</b></th><th ><b>Location</b></th><th ><b>Requester Name</b></th><th ><b>Technician</b></th><th ><b>Category</b></th>"
				+ "<th ><b>Sub Category</b></th><th ><b>Subject</b></th><th ><b> 	Priority</b></th><th ><b>Status</b></th><th ><b>Approval Date</b></th></tr>";
			
		
		 desc = desc+"<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
		             +"TABLE {border-collapse:collapse;border:1px solid black;}"
		             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
		             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
				 +"</STYLE>";
		 Iterator fr=all.iterator();
		for(int k=0;k<all.size();k++)
		{
			IssuesForm n=(IssuesForm)fr.next();
			desc=desc+"<tr><td>"+n.getRequestNo()+"</td><td>"+n.getReqDate()+"</td><td>"+n.getLocation()+"</td><td>"+n.getEmployeename()+"</td>"
					+ "<td>"+n.getTechnicianName()+"</td><td>"+n.getCategory()+"</td><td>"+n.getSubcategory()+"</td><td>"+n.getSubject()+"</td>"
							+ "<td>"+n.getReqPriority()+"</td><td>"+n.getRequestStatus()+"</td><td>"+n.getApprovedDate()+"</td></tr> ";
		}
		desc=desc+"</table>";
	
	
				 mailForm.setDescription(desc);
		 
		 try {
			try {
				
				sendSalesMail( mailForm,"emicro");
				
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

	
	 public static String sendSalesMail( MailInboxForm mailSentForm, String USER_NAME) throws IOException, ServletException, SQLException, MessagingException
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
			 
				   }
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


