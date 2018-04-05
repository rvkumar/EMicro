package com.microlabs.main.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.TravelRequestForm;
import com.microlabs.main.db.MainDao;
import com.microlabs.main.form.MailInboxForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.UserInfo;


public class MailInboxAction extends DispatchAction{
	private String fromAddress;
	private String password; 
	public int MAIL_PER_PAGE = 10;
	private String USER_ID;
	private int compId=0;
	private long attLimit= ((1024*1024)*10);
	private long overallAttSize=0;
	
	public ActionForward displayInboxMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException{
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		MailInboxForm mailInForm=(MailInboxForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		LinkedList InList=new LinkedList();
		if(user == null)
		{
			mailInForm.setMessage("Session Expried! Try to Login again!");
			request.setAttribute("noRecords","noRecords");
			request.setAttribute("InboxDetails", InList);
			return mapping.findForward("displayMailInbox");
		}
		int uId=user.getId();
		USER_ID = user.getUserName();
		
		//addApproversForForm();
		String tablename = "Inbox";
		int  startMailCount=0;
		int  endMailCount=0;
		String scount = request.getParameter("sCount");
		if(scount == null){
			mailInForm.setMailMessage("Mail Sent Successfully!");
		}
		if(scount != null){
			startMailCount=Integer.parseInt(scount);
			endMailCount=Integer.parseInt(request.getParameter("eCount"));
		}
		if(startMailCount == 0){String msgfrom = getInboxMail(mapping,form,request,response);}
		basicNavigation(request);
		int InboxTotal = getCountForTable(tablename,"");
		if(startMailCount == 0){
			if(InboxTotal == 0){
				mailInForm.setMessage("No mails in Inbox!");
				request.setAttribute("noRecords","noRecords");
			}
			else if(InboxTotal <= MAIL_PER_PAGE)
			{
				startMailCount=1;
				endMailCount=InboxTotal;
			}
			else
			{
				startMailCount=1;
				endMailCount=MAIL_PER_PAGE;
				enableNextNavigation(request);
			}
			/*InList = getLimitedItems(request,tablename,startMailCount,endMailCount);
			setAllCount(mailInForm,InboxTotal,startMailCount,endMailCount);*/
		}
		else{
			String naviType = request.getParameter("fnpl");
			if(naviType.equalsIgnoreCase("next")){
				startMailCount=(startMailCount+MAIL_PER_PAGE);
				endMailCount=(endMailCount+MAIL_PER_PAGE);
				enablePreviousNavigation(request);
				if(InboxTotal <= endMailCount)
				{
					disableNextNavigation(request);
					endMailCount = InboxTotal;
				}
				else
				{
					enableNextNavigation(request);
				}
			}
			else if(naviType.equalsIgnoreCase("priv"))
			{
				startMailCount=(startMailCount-MAIL_PER_PAGE);
				int ecount=0;
				if(endMailCount == InboxTotal)
				{
					int ec = endMailCount/MAIL_PER_PAGE;
					ecount = ec;
					endMailCount=(ecount*MAIL_PER_PAGE);
				}
				else
					endMailCount=(endMailCount-MAIL_PER_PAGE);
				if(endMailCount == MAIL_PER_PAGE)
				{
					disablePreviousNavigation(request);
					enableNextNavigation(request);
				}
				else{
					enablePreviousNavigation(request);
					enableNextNavigation(request);
				}
			}
			else if(naviType.equalsIgnoreCase("alast")){
				int ecount=0;
				endMailCount = InboxTotal;
				int typeStart = endMailCount%MAIL_PER_PAGE;
				System.out.println("mod "+typeStart);
				if(typeStart == 0){
					int ec = endMailCount-MAIL_PER_PAGE;
					ecount = ec;
					startMailCount=ecount+1;
				}
				else{
					int ec = endMailCount/MAIL_PER_PAGE;
					ecount = ec;
					startMailCount=(ecount*MAIL_PER_PAGE)+1;
				}
				enablePreviousNavigation(request);
				disableNextNavigation(request);
			}
			else{
				startMailCount=1;
				endMailCount = MAIL_PER_PAGE;
				disablePreviousNavigation(request);
				enableNextNavigation(request);
			}
			
			
			/*InList = getLimitedItems(request,tablename,startMailCount,endMailCount);
			setAllCount(mailInForm,InboxTotal,startMailCount,endMailCount);*/
		}
		InList = getLimitedItems(request,tablename,startMailCount,endMailCount, "readOrUnread");
		setAllCount(mailInForm,InboxTotal,startMailCount,endMailCount);
		request.setAttribute("InboxDetails", InList);
		mailInForm.setHeading("Mail Inbox");
		return mapping.findForward("displayMailInbox");
	}
	
	/*Display New mail
	 * */
	public String mailSendToRequester(HttpServletRequest request, MailInboxForm mailSentForm, String mailfrom,TravelRequestForm form) throws IOException, ServletException, SQLException, MessagingException
	{

		
		String HOST_NAME = "mail.microlabs.co.in";
		LinkedList  userInfo = getUserLoginDetails(request);
    	final String USER_NAME = userInfo.get(1).toString();
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
		String localhost = (InetAddress.getLocalHost()).getCanonicalHostName();
		System.out.println("local --> "+localhost);
		String bcc = mailSentForm.getbccAddress();
		
		//put host, port properties
		
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
		   
		   
	       InputStream in = ConnectionFactory.class.getClassLoader()
					.getResourceAsStream("db.properties");
			 props = new Properties();
			try {
				props.load(in);
			
			in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EssDao ad=new EssDao();
 			String uploadFilePath = props
					.getProperty("file.uploadFilePath");
			String filePath = uploadFilePath
					+ "/EMicro Files/ESS/Travel Request/ConfirmFiles/"+form.getReqType()+"";
			String sql1 = "select * from Travel_confirm_documents where request_no='"+ form.getRequestNumber()+ "'";
			ResultSet rs = ad.selectQuery(sql1);
			TravelRequestForm leaveForm1 = null;
			ArrayList a1 = new ArrayList();

			try {
				while (rs.next()) {				
					
					MimeBodyPart attachBodyPart = new MimeBodyPart();
	     		    DataSource source = new FileDataSource(filePath + "/"+ rs.getString("file_name"));
	     		    attachBodyPart.setDataHandler(new DataHandler(source));
	     		    attachBodyPart.setFileName((filePath + "/"+ rs.getString("file_name")).substring((filePath + "/"+ rs.getString("file_name")).lastIndexOf("/") + 1));
	                mp.addBodyPart(attachBodyPart);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	       
		
		   message.setContent(mp);
		   session.setDebug(true);
		   //Service sr = new Service(session,)
		   //send message
		   Transport transport = session.getTransport(protocol);
		   
		   transport.connect();
		   if(message.getAllRecipients()!=null)
		   {
			   transport.sendMessage(message, message.getAllRecipients());
		   }
		   transport.close();
		   //Transport.send(message);
		 
		  }
		  catch (NoSuchProviderException e1) {
			  sucMessage = "Mail not Sent";
			  System.out.println("Exception @ mail sending");
		  e1.printStackTrace();}
		  catch (MessagingException e) {
			  sucMessage = "Mail not Sent";
			  System.out.println("Exception @ mail sending");
				if(mailfrom.equalsIgnoreCase("mail")){
		  		deleteUploadAttach(request);
				}
		  		e.printStackTrace();
		 }
		 catch (NullPointerException npe) {
			 sucMessage = "Mail not Sent";
			 System.out.println("Exception @ mail sending");
				if(mailfrom.equalsIgnoreCase("mail")){
		  		deleteUploadAttach(request);
				}
		  		npe.printStackTrace();
		 }
		  /*finally {
	            deleteUploadFiles(uploadedFiles);
	        }*/
		return sucMessage;
	
	}
	
	public ActionForward displayComposeMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		MailInboxForm mailComposeForm=(MailInboxForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String userID=user.getUserName();
		String user_name = user.getUserName();
		USER_ID = user_name;
		password="";
		//getting password
		password = getUserPassword(request);
		if(password != null){
		String compType=request.getParameter("sId");
		if(compType==null){
			compType = "Open";
		}
		String userAgent=request.getHeader("user-agent");
        /*passing data to model class */
        System.out.println("Browser --> "+userAgent);
        mailComposeForm.setMailMessage("Info: You can attach upto 10 MB.");
		if(compType.equalsIgnoreCase("New"))
		{
			String tablename = "Draft";
			int draftCount = getCountForTable(tablename,"");
			int sentCount = getCountForTable("SentItem","");
			compId = 1+(draftCount+sentCount);

			LinkedList a1=new LinkedList();
			LinkedList  userInfo = getUserLoginDetails(request);
	    	
			if(userInfo != null && !userInfo.isEmpty()) {
				final String fromAddress = userInfo.get(1).toString();
		    	//password = userInfo.get(2).toString();
				mailComposeForm.setFromInbox(user_name);
					mailComposeForm.setToAddress("");
					mailComposeForm.setccAddress("");
					mailComposeForm.setbccAddress("");
					mailComposeForm.setSubject("");
					mailComposeForm.setSearchText("");
					request.setAttribute("bdyContent", "");
					//mailComposeForm.setDescription("");
					mailComposeForm.setCompId(compId);
					mailComposeForm.setAttachNames("");
				a1.add(mailComposeForm);
			}
			request.setAttribute("ComposeDetails", a1);

		}
		else if(compType.equalsIgnoreCase("Open"))
		{
			
			if(request.getParameter("mailId")==null){
				compId = Integer.parseInt(request.getAttribute("mailId").toString());
			}
			else{
				compId = Integer.parseInt(request.getParameter("mailId"));
			}
			String sql="select * from Mail_Draft where mail_id="+compId+" and emp_id='"+userID+"'";
			try{
				LinkedList a1=new LinkedList();
				ResultSet rs=ad.selectQuery(sql);
				while (rs.next()) {
					//fromAddress = rs.getString("FromAdd");
					mailComposeForm.setFromInbox(user_name);
					mailComposeForm.setToAddress(rs.getString("ToAdd"));
					mailComposeForm.setccAddress(rs.getString("CcAdd"));
					mailComposeForm.setbccAddress("");
					mailComposeForm.setSearchText("Draft");
					mailComposeForm.setSubject(rs.getString("Subject"));
					request.setAttribute("bdyContent", rs.getString("Body"));
					//mailComposeForm.setDescription(rs.getString("Body"));
					System.out.println(rs.getString("date_AttName"));
					mailComposeForm.setAttachNames(rs.getString("date_AttName"));
					LinkedList attFileList=new LinkedList();
					attFileList = getAttachmentFiles(request,"SentItem");
					request.setAttribute("listName", attFileList);
					mailComposeForm.setCompId(compId);
					a1.add(mailComposeForm);
				}
				request.setAttribute("ComposeDetails", a1);
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}
		else if(compType.equalsIgnoreCase("reply"))
		{
			compId = Integer.parseInt(request.getParameter("mailId"));
			String folder = request.getParameter("folder");
			EMailer email = new EMailer();
			String tName = email.getTableNameForRequest(folder);
			String sql="select * from "+tName+" where mail_id="+compId+" and emp_id='"+userID+"'";
			try{
				LinkedList a1=new LinkedList();
				ResultSet rs=ad.selectQuery(sql);
				while (rs.next()) {
					//fromAddress = rs.getString("FromAdd");
					mailComposeForm.setFromInbox(user_name);
					String TO = rs.getString("ToAdd");
					if(TO.indexOf(",") >= 0)
		            {
		            	TO = TO.substring(0, TO.indexOf(","));
		            }
					mailComposeForm.setToAddress(TO);
					mailComposeForm.setbccAddress("");
					mailComposeForm.setSubject("Re : "+rs.getString("Subject"));
					String desc = "<br> "+rs.getString("FromAdd")+" wrote:<br>";
					request.setAttribute("bdyContent", desc+rs.getString("Body"));
					//mailComposeForm.setDescription(rs.getString("Body"));
					mailComposeForm.setSearchText(folder);
					mailComposeForm.setAttachNames(rs.getString("date_AttName"));
					mailComposeForm.setCompId(compId);
					a1.add(mailComposeForm);
				}
				request.setAttribute("ComposeDetails", a1);
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}
		else if(compType.equalsIgnoreCase("RAll"))
		{
			compId = Integer.parseInt(request.getParameter("mailId"));
			String folder = request.getParameter("folder");
			EMailer email = new EMailer();
			String tName = email.getTableNameForRequest(folder);
			String sql="select * from "+tName+" where mail_id="+compId+" and emp_id='"+userID+"'";
			try{
				LinkedList a1=new LinkedList();
				ResultSet rs=ad.selectQuery(sql);
				while (rs.next()) {
					//fromAddress = rs.getString("FromAdd");
					mailComposeForm.setFromInbox(user_name);
					String TO = rs.getString("ToAdd");
					mailComposeForm.setToAddress(TO);
					mailComposeForm.setccAddress(rs.getString("CcAdd"));
					mailComposeForm.setbccAddress("");
					mailComposeForm.setSubject("Re : "+rs.getString("Subject"));
					String desc = "<br> "+rs.getString("FromAdd")+" wrote:<br>";
					request.setAttribute("bdyContent", desc+rs.getString("Body"));
					//mailComposeForm.setDescription(rs.getString("Body"));
					mailComposeForm.setSearchText(folder);
					mailComposeForm.setAttachNames(rs.getString("date_AttName"));
					mailComposeForm.setCompId(compId);
					a1.add(mailComposeForm);
				}
				request.setAttribute("ComposeDetails", a1);
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}
		else if(compType.equalsIgnoreCase("FW"))
		{
			compId = Integer.parseInt(request.getParameter("mailId"));
			String folder = request.getParameter("folder");
			EMailer email = new EMailer();
			String tName = email.getTableNameForRequest(folder);
			String sql="select * from "+tName+" where mail_id="+compId+" and emp_id='"+userID+"'";
			try{
				LinkedList a1=new LinkedList();
				ResultSet rs=ad.selectQuery(sql);
				while (rs.next()) {
					//fromAddress = rs.getString("FromAdd");
					mailComposeForm.setFromInbox(user_name);
					mailComposeForm.setToAddress("");
					mailComposeForm.setccAddress("");
					mailComposeForm.setbccAddress("");
					mailComposeForm.setSubject("Fwd : "+rs.getString("Subject"));
					String desc = "<br> ---------- Forwarded message ----------<br>";
					desc = desc + "From: "+rs.getString("FromAdd")+"<br>";
					desc = desc + "Date: "+rs.getString("Date")+"<br>";
					desc = desc + "Subject: "+rs.getString("Subject")+"<br>";
					desc = desc + "To: "+rs.getString("ToAdd")+"<br>";
					request.setAttribute("bdyContent", desc+rs.getString("Body"));
					//mailComposeForm.setDescription(rs.getString("Body"));
					mailComposeForm.setSearchText(folder);
					mailComposeForm.setAttachNames(rs.getString("date_AttName"));
					mailComposeForm.setCompId(compId);
					a1.add(mailComposeForm);
				}
				request.setAttribute("ComposeDetails", a1);
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}
		LinkedList searchList = new LinkedList();
		request.setAttribute("SearchUserDetails", "");
		}
		else{
			try{
			displayMailHome(mapping, form, request, response);
			}catch(SQLException se){
				se.printStackTrace();
			}
			return mapping.findForward("displayMailHome");
		}
		return mapping.findForward("displayMailNew");
	}
	
	/* Add the compose mail details into sent
	 * */
	
	public ActionForward displaySendMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, MessagingException {
		HttpSession session=request.getSession();
		MailInboxForm mailSentForm=(MailInboxForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		fromAddress = mailSentForm.getFromInbox();
		Date date = new Date();
		int isAtt = 0;

		int attCount = getCountForTable("UploadFiles_Mails","Attach");
        String downAtt="";
        if(attCount > 0)
        {
        	isAtt = 1;
        }
		
		String sentSuccuss=mailSendToRecipient(request, mailSentForm, "mail");
		
		int insertCount = 0;
		if(sentSuccuss=="succuss")
		{
			//String insertSentQuery="INSERT INTO Mail_SentItem (mail_id, emp_id, FromAdd, ToAdd, CcAdd, Subject, Body, isAttach, Date, date_AttName, isread) values("+compId;
			//insertSentQuery = insertSentQuery + ", '"+ user.getUserName() + "','"+fromAddress+"','" + mailSentForm.getToAddress() +"','"+mailSentForm.getccAddress()+"','"+mailSentForm.getSubject()+"',"+IOUtils.toInputStream(mailSentForm.getDescription())+","+isAtt+", current_timestamp,'"+downAtt+"',0)";
			//insertCount = ad.SqlExecuteUpdate(insertSentQuery);
			LinkedList paramList = new LinkedList();
			PreparedStatement pstmt;
	        Connection conn=ConnectionFactory.getConnection();
	        String query = "INSERT INTO Mail_SentItem VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	        pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, compId);
	        pstmt.setString(2, user.getUserName());
	        
	        pstmt.setString(3, fromAddress);
	        pstmt.setString(4, mailSentForm.getToAddress());
	        pstmt.setString(5, mailSentForm.getccAddress());
	        pstmt.setString(6, mailSentForm.getSubject());
	        InputStream stream = IOUtils.toInputStream(mailSentForm.getDescription());
	        pstmt.setBinaryStream(7, stream);
	        pstmt.setInt(8, isAtt);
	        Date rDate = new Date();
	        long ld = rDate.getTime();
			java.sql.Timestamp ts = new Timestamp(ld);
	        pstmt.setTimestamp(9, ts);
	        pstmt.setString(10, downAtt);
	        pstmt.setInt(11, 0);
	        pstmt.executeUpdate();
		}
		else{
			mailSentForm.setMessage(sentSuccuss);
		}
		String sql="select * from Mail_SentItem";
		try{
			LinkedList a1=new LinkedList();
			if(insertCount != 0)
			{
				ResultSet rs=ad.selectQuery(sql);
				while (rs.next()) {
					sentSuccuss = "Message Sent Succussfully!";
					mailSentForm.setMessage(sentSuccuss);
					a1.add(mailSentForm);
				}
			}
			request.setAttribute("SentDetails", a1);
		}
		
		catch(SQLException se){
			se.printStackTrace();
		}
		
		//displayInboxMail(mapping, form, request,response);
		//return mapping.findForward("displayMailInbox");
		return mapping.findForward("displayMailSent");
	}
	
	/* Display Mail Home and Show the mail access control of the employee.
	 * */
	
	public ActionForward displayMailHome(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		MailInboxForm mailHomeForm=(MailInboxForm)form;
		String openType=request.getParameter("openType");
		HttpSession session=request.getSession();
		NewsandMediaDao ad=new NewsandMediaDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(openType != null){
		if(openType.equalsIgnoreCase("cancelNew")){
			deleteUploadAttach(request);
		}}
		String pwd="";
		String mailMessage ="Communicate with co-worker. Based on the designation, mail access control will vary.";
		mailHomeForm.setMailMessage(mailMessage);
		try{
			String sql="select * from emp_master where emp_id='"+user.getEmployeeNo()+"'";
			ResultSet rs=ad.selectQuery(sql);
			while (rs.next()) {
				pwd = rs.getString("emp_mail_pwd");
			}
			if(pwd == null || pwd.equalsIgnoreCase("null") || pwd.equalsIgnoreCase("")){
				request.setAttribute("EnterMailPwd","show");
			}
		}
		
		catch(SQLException se){
			se.printStackTrace();
		}
		System.out.println("Host = " + request.getServerName());
		System.out.println("Port = " + request.getServerPort());
		return mapping.findForward("displayMailHome");
	}
	
	/* New Mail send to given recipient, and saved into Mail_SentITem Table 
	 * */
	
	public String mailSendNextApprover(HttpServletRequest request, MailInboxForm mailSentForm, String mailfrom,String requesterID) throws IOException, ServletException, SQLException, MessagingException
	{
		
		String HOST_NAME = "mail.microlabs.co.in";
		LinkedList  userInfo = getNextApproverDetails(request,requesterID);
    	final String USER_NAME = userInfo.get(1).toString();
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
		String localhost = (InetAddress.getLocalHost()).getCanonicalHostName();
		System.out.println("local --> "+localhost);
		String bcc = mailSentForm.getbccAddress();
		
		//put host, port properties
		
		String port = "25";//request.getServerPort();
	
		Properties props = new Properties();
		props.put("mail.smtp.host",HOST_NAME);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.socketFactory.port", port);
		
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				   @Override
				protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(DEFAULT_ID,password);//employee company email address and password
				   }
		});
		//compose message
		  try {
		   MimeMessage message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(USER_NAME));//employee company email address
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
	       if(mailfrom.equalsIgnoreCase("mail")){
	    	   LinkedList attFileList=new LinkedList();
	    	   attFileList = getAttachmentFiles(request, "SentItem");
	    	   List<File> uploadedFiles = getFilesToSend(attFileList);
			   if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
		            for (File aFile : uploadedFiles) {
		            	MimeBodyPart attachBodyPart = new MimeBodyPart();
		     		    DataSource source = new FileDataSource(aFile);
		     		    attachBodyPart.setDataHandler(new DataHandler(source));
		     		    attachBodyPart.setFileName(aFile.getName());
		                mp.addBodyPart(attachBodyPart);
		            }
		        }
	       }
		   
		   message.setContent(mp);
		   session.setDebug(true);
		   //Service sr = new Service(session,)
		   //send message
		   Transport transport = session.getTransport(protocol);
		   
		   transport.connect();
		   if(message.getAllRecipients()!=null)
		   {
			   transport.sendMessage(message, message.getAllRecipients());
		   }
		   transport.close();
		   //Transport.send(message);
		 
		  }
		  catch (NoSuchProviderException e1) {
			  sucMessage = "Mail not Sent";
			  System.out.println("Exception @ mail sending");
		  e1.printStackTrace();}
		  catch (MessagingException e) {
			  sucMessage = "Mail not Sent";
			  System.out.println("Exception @ mail sending");
				if(mailfrom.equalsIgnoreCase("mail")){
		  		deleteUploadAttach(request);
				}
		  		e.printStackTrace();
		 }
		 catch (NullPointerException npe) {
			 sucMessage = "Mail not Sent";
			 System.out.println("Exception @ mail sending");
				if(mailfrom.equalsIgnoreCase("mail")){
		  		deleteUploadAttach(request);
				}
		  		npe.printStackTrace();
		 }
		  /*finally {
	            deleteUploadFiles(uploadedFiles);
	        }*/
		return sucMessage;
	
		
	}
	
	public String mailSendToOfficeEmpParallelApprovers(HttpServletRequest request, MailInboxForm mailSentForm, String mailfrom,String toAddress,String empNo) throws IOException, ServletException, SQLException, MessagingException
	{

		
		String HOST_NAME = "mail.microlabs.co.in";
		LinkedList  userInfo = getOfficeEmpDetails(request, empNo);
    	final String USER_NAME = userInfo.get(1).toString();
    	final String DEFAULT_ID = "emicro@microlabs.in";
    	String protocol ="smtp";
    	password = "micro1@";
    	//password = getUserPassword(request);
    	/*if(!mailfrom.equalsIgnoreCase("forgetpass")){
    	password = getUserPassword(request);
    	}*/
		String sucMessage="succuss";
		//for multiple to,cc,bcc address
		String to = toAddress;
		String cc = mailSentForm.getccAddress();
		String localhost = (InetAddress.getLocalHost()).getCanonicalHostName();
		System.out.println("local --> "+localhost);
		String bcc = mailSentForm.getbccAddress();
		
		//put host, port properties
		
		String port = "25";//request.getServerPort();
	
		Properties props = new Properties();
		props.put("mail.smtp.host",HOST_NAME);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.socketFactory.port", port);
		
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				   @Override
				protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(DEFAULT_ID,password);//employee company email address and password
				   }
		});
		//compose message
		  try {
		   MimeMessage message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(USER_NAME));//employee company email address
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
	       if(mailfrom.equalsIgnoreCase("mail")){
	    	   LinkedList attFileList=new LinkedList();
	    	   attFileList = getAttachmentFiles(request, "SentItem");
	    	   List<File> uploadedFiles = getFilesToSend(attFileList);
			   if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
		            for (File aFile : uploadedFiles) {
		            	MimeBodyPart attachBodyPart = new MimeBodyPart();
		     		    DataSource source = new FileDataSource(aFile);
		     		    attachBodyPart.setDataHandler(new DataHandler(source));
		     		    attachBodyPart.setFileName(aFile.getName());
		                mp.addBodyPart(attachBodyPart);
		            }
		        }
	       }
		   
		   message.setContent(mp);
		   session.setDebug(true);
		   //Service sr = new Service(session,)
		   //send message
		   Transport transport = session.getTransport(protocol);
		   
		   transport.connect();
		   if(message.getAllRecipients()!=null)
		   {
			   transport.sendMessage(message, message.getAllRecipients());
		   }
		   transport.close();
		   //Transport.send(message);
		 
		  }
		  catch (NoSuchProviderException e1) {
			  sucMessage = "Mail not Sent";
			  System.out.println("Exception @ mail sending");
		  e1.printStackTrace();}
		  catch (MessagingException e) {
			  sucMessage = "Mail not Sent";
			  System.out.println("Exception @ mail sending");
				if(mailfrom.equalsIgnoreCase("mail")){
		  		deleteUploadAttach(request);
				}
		  		e.printStackTrace();
		 }
		 catch (NullPointerException npe) {
			 sucMessage = "Mail not Sent";
			 System.out.println("Exception @ mail sending");
				if(mailfrom.equalsIgnoreCase("mail")){
		  		deleteUploadAttach(request);
				}
		  		npe.printStackTrace();
		 }
		  /*finally {
	            deleteUploadFiles(uploadedFiles);
	        }*/
		return sucMessage;
	
	}
	
	public String mailSendToParallelApprovers(HttpServletRequest request, MailInboxForm mailSentForm, String mailfrom,String toAddress) throws IOException, ServletException, SQLException, MessagingException
	{
		
		String HOST_NAME = "mail.microlabs.co.in";
		LinkedList  userInfo = getUserLoginDetails(request);
    	final String USER_NAME = userInfo.get(1).toString();
    	final String DEFAULT_ID = "emicro@microlabs.in";
    	String protocol ="smtp";
    	password = "micro1@";
    	//password = getUserPassword(request);
    	/*if(!mailfrom.equalsIgnoreCase("forgetpass")){
    	password = getUserPassword(request);
    	}*/
		String sucMessage="succuss";
		//for multiple to,cc,bcc address
		String to = toAddress;
		String cc = mailSentForm.getccAddress();
		String localhost = (InetAddress.getLocalHost()).getCanonicalHostName();
		System.out.println("local --> "+localhost);
		String bcc = mailSentForm.getbccAddress();
		
		//put host, port properties
		
		String port = "25";//request.getServerPort();
	
		Properties props = new Properties();
		props.put("mail.smtp.host",HOST_NAME);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.socketFactory.port", port);
		
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				   @Override
				protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(DEFAULT_ID,password);//employee company email address and password
				   }
		});
		//compose message
		  try {
		   MimeMessage message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(USER_NAME));//employee company email address
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
	       if(mailfrom.equalsIgnoreCase("mail")){
	    	   LinkedList attFileList=new LinkedList();
	    	   attFileList = getAttachmentFiles(request, "SentItem");
	    	   List<File> uploadedFiles = getFilesToSend(attFileList);
			   if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
		            for (File aFile : uploadedFiles) {
		            	MimeBodyPart attachBodyPart = new MimeBodyPart();
		     		    DataSource source = new FileDataSource(aFile);
		     		    attachBodyPart.setDataHandler(new DataHandler(source));
		     		    attachBodyPart.setFileName(aFile.getName());
		                mp.addBodyPart(attachBodyPart);
		            }
		        }
	       }
		   
		   message.setContent(mp);
		   session.setDebug(true);
		   //Service sr = new Service(session,)
		   //send message
		   Transport transport = session.getTransport(protocol);
		   
		   transport.connect();
		   if(message.getAllRecipients()!=null)
		   {
			   transport.sendMessage(message, message.getAllRecipients());
		   }
		   transport.close();
		   //Transport.send(message);
		 
		  }
		  catch (NoSuchProviderException e1) {
			  sucMessage = "Mail not Sent";
			  System.out.println("Exception @ mail sending");
		  e1.printStackTrace();}
		  catch (MessagingException e) {
			  sucMessage = "Mail not Sent";
			  System.out.println("Exception @ mail sending");
				if(mailfrom.equalsIgnoreCase("mail")){
		  		deleteUploadAttach(request);
				}
		  		e.printStackTrace();
		 }
		 catch (NullPointerException npe) {
			 sucMessage = "Mail not Sent";
			 System.out.println("Exception @ mail sending");
				if(mailfrom.equalsIgnoreCase("mail")){
		  		deleteUploadAttach(request);
				}
		  		npe.printStackTrace();
		 }
		  /*finally {
	            deleteUploadFiles(uploadedFiles);
	        }*/
		return sucMessage;
	}
	public String mailSendToOfficeEmpRecipient(HttpServletRequest request, MailInboxForm mailSentForm, String mailfrom,String empNo) throws IOException, ServletException, SQLException, MessagingException
	{

		
		String HOST_NAME = "mail.microlabs.co.in";
		LinkedList  userInfo = getOfficeEmpDetails(request,empNo);
    	final String USER_NAME = userInfo.get(1).toString();
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
		String localhost = (InetAddress.getLocalHost()).getCanonicalHostName();
		System.out.println("local --> "+localhost);
		String bcc = mailSentForm.getbccAddress();
		
		//put host, port properties
		
		String port = "25";//request.getServerPort();
	
		Properties props = new Properties();
		props.put("mail.smtp.host",HOST_NAME);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.socketFactory.port", port);
		
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				   @Override
				protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(DEFAULT_ID,password);//employee company email address and password
				   }
		});
		//compose message
		  try {
		   MimeMessage message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(USER_NAME));//employee company email address
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
	       if(mailfrom.equalsIgnoreCase("mail")){
	    	   LinkedList attFileList=new LinkedList();
	    	   attFileList = getAttachmentFiles(request, "SentItem");
	    	   List<File> uploadedFiles = getFilesToSend(attFileList);
			   if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
		            for (File aFile : uploadedFiles) {
		            	MimeBodyPart attachBodyPart = new MimeBodyPart();
		     		    DataSource source = new FileDataSource(aFile);
		     		    attachBodyPart.setDataHandler(new DataHandler(source));
		     		    attachBodyPart.setFileName(aFile.getName());
		                mp.addBodyPart(attachBodyPart);
		            }
		        }
	       }
		   
		   message.setContent(mp);
		   session.setDebug(true);
		   //Service sr = new Service(session,)
		   //send message
		   Transport transport = session.getTransport(protocol);
		   
		   transport.connect();
		   if(message.getAllRecipients()!=null)
		   {
			   transport.sendMessage(message, message.getAllRecipients());
		   }
		   transport.close();
		   //Transport.send(message);
		 
		  }
		  catch (NoSuchProviderException e1) {
			  sucMessage = "Mail not Sent";
			  System.out.println("Exception @ mail sending");
		  e1.printStackTrace();}
		  catch (MessagingException e) {
			  sucMessage = "Mail not Sent";
			  System.out.println("Exception @ mail sending");
				if(mailfrom.equalsIgnoreCase("mail")){
		  		deleteUploadAttach(request);
				}
		  		e.printStackTrace();
		 }
		 catch (NullPointerException npe) {
			 sucMessage = "Mail not Sent";
			 System.out.println("Exception @ mail sending");
				if(mailfrom.equalsIgnoreCase("mail")){
		  		deleteUploadAttach(request);
				}
		  		npe.printStackTrace();
		 }
		  /*finally {
	            deleteUploadFiles(uploadedFiles);
	        }*/
		return sucMessage;
	
	}
	public String mailSendToRecipient(HttpServletRequest request, MailInboxForm mailSentForm, String mailfrom) throws IOException, ServletException, SQLException, MessagingException
	{
		
		String HOST_NAME = "mail.microlabs.co.in";
		LinkedList  userInfo = getUserLoginDetails(request);
    	final String USER_NAME = userInfo.get(1).toString();
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
		String localhost = (InetAddress.getLocalHost()).getCanonicalHostName();
		System.out.println("local --> "+localhost);
		String bcc = mailSentForm.getbccAddress();
		
		//put host, port properties
		
		String port = "25";//request.getServerPort();
	
		Properties props = new Properties();
		props.put("mail.smtp.host",HOST_NAME);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.socketFactory.port", port);
		
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				   @Override
				protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(DEFAULT_ID,password);//employee company email address and password
				   }
		});
		//compose message
		  try {
		   MimeMessage message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(USER_NAME));//employee company email address
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
	       if(mailfrom.equalsIgnoreCase("mail")){
	    	   LinkedList attFileList=new LinkedList();
	    	   attFileList = getAttachmentFiles(request, "SentItem");
	    	   List<File> uploadedFiles = getFilesToSend(attFileList);
			   if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
		            for (File aFile : uploadedFiles) {
		            	MimeBodyPart attachBodyPart = new MimeBodyPart();
		     		    DataSource source = new FileDataSource(aFile);
		     		    attachBodyPart.setDataHandler(new DataHandler(source));
		     		    attachBodyPart.setFileName(aFile.getName());
		                mp.addBodyPart(attachBodyPart);
		            }
		        }
	       }
		   
		   message.setContent(mp);
		   session.setDebug(true);
		   //Service sr = new Service(session,)
		   //send message
		   Transport transport = session.getTransport(protocol);
		   
		   transport.connect();
		   if(message.getAllRecipients()!=null)
		   {
			   transport.sendMessage(message, message.getAllRecipients());
		   }
		   transport.close();
		   //Transport.send(message);
		 
		  }
		  catch (NoSuchProviderException e1) {
			  sucMessage = "Mail not Sent";
			  System.out.println("Exception @ mail sending");
		  e1.printStackTrace();}
		  catch (MessagingException e) {
			  sucMessage = "Mail not Sent";
			  System.out.println("Exception @ mail sending");
				if(mailfrom.equalsIgnoreCase("mail")){
		  		deleteUploadAttach(request);
				}
		  		e.printStackTrace();
		 }
		 catch (NullPointerException npe) {
			 sucMessage = "Mail not Sent";
			 System.out.println("Exception @ mail sending");
				if(mailfrom.equalsIgnoreCase("mail")){
		  		deleteUploadAttach(request);
				}
		  		npe.printStackTrace();
		 }
		  /*finally {
	            deleteUploadFiles(uploadedFiles);
	        }*/
		return sucMessage;
	}
	
	/*Display Sent Items based on the employee id.
	 * */
	
	
	public ActionForward displaySentMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ParseException {
		HttpSession session=request.getSession();
		NewsandMediaDao ad=new NewsandMediaDao();
		MailInboxForm mailSForm=(MailInboxForm)form;
		java.util.List mailList = new java.util.ArrayList();

		UserInfo user=(UserInfo)session.getAttribute("user");
		USER_ID=user.getUserName();
		String tablename = "SentItem";
		LinkedList sentList=new LinkedList();
		//try{
			int SentCount=getCountForTable(tablename,"");
			int  startMailCount=0;
			int  endMailCount=0;
			String scount = request.getParameter("sCount");
			if(scount == null){
				System.out.println(mailSForm.getMessage());
				mailSForm.setMailMessage("Mail Sent Successfully!");
			}
			if(scount != null){
				startMailCount=Integer.parseInt(scount);
				endMailCount=Integer.parseInt(request.getParameter("eCount"));
			}
			//int  startMailCount=Integer.parseInt(request.getParameter("sCount"));
			//int  endMailCount=Integer.parseInt(request.getParameter("eCount"));
			basicNavigation(request);
			if(startMailCount == 0)
			{
				if(SentCount == 0)
				{			
					mailSForm.setMessage("No mails in Sent!");
					request.setAttribute("noRecords","noRecords");
				}
				else if(SentCount<=MAIL_PER_PAGE)
				{
					startMailCount=1;
					endMailCount=SentCount;
					sentList = getLimitedItems(request,tablename,startMailCount,endMailCount,"readOrUnread");
					setAllCount(mailSForm,SentCount,startMailCount,endMailCount);
				}
				else{
					startMailCount=1;
					endMailCount=MAIL_PER_PAGE;
					sentList = getLimitedItems(request,tablename,startMailCount,endMailCount,"readOrUnread");
					setAllCount(mailSForm,SentCount,startMailCount,endMailCount);
					enableNextNavigation(request);
				}
			}
			else{
				String naviType = request.getParameter("fnpl");
				if(naviType.equalsIgnoreCase("next")){
					startMailCount=(startMailCount+MAIL_PER_PAGE);
					endMailCount=(endMailCount+MAIL_PER_PAGE);
					enablePreviousNavigation(request);
					if(SentCount <= endMailCount)
					{
						disableNextNavigation(request);
						endMailCount = SentCount;
					}
					else
					{
						enableNextNavigation(request);
					}
				}
				else if(naviType.equalsIgnoreCase("priv"))
				{
					startMailCount=(startMailCount-MAIL_PER_PAGE);
					int ecount=0;
					if(endMailCount == SentCount)
					{
						int ec = endMailCount/MAIL_PER_PAGE;
						ecount = ec;
						endMailCount=(ecount*MAIL_PER_PAGE);
					}
					else
						endMailCount=(endMailCount-MAIL_PER_PAGE);
					if(endMailCount == MAIL_PER_PAGE)
					{
						disablePreviousNavigation(request);
						enableNextNavigation(request);
					}
					else{
						enablePreviousNavigation(request);
						enableNextNavigation(request);
					}

				}
				else if(naviType.equalsIgnoreCase("alast")){
					int ecount=0;
					endMailCount = SentCount;
					int typeStart = endMailCount%MAIL_PER_PAGE;
					System.out.println("mod "+typeStart);
					if(typeStart == 0){
						int ec = endMailCount-MAIL_PER_PAGE;
						ecount = ec;
						startMailCount=ecount+1;
					}
					else{
						int ec = endMailCount/MAIL_PER_PAGE;
						ecount = ec;
						startMailCount=(ecount*MAIL_PER_PAGE)+1;
					}
					enablePreviousNavigation(request);
					disableNextNavigation(request);

				}
				else{
					startMailCount=1;
					endMailCount = MAIL_PER_PAGE;
					disablePreviousNavigation(request);
					enableNextNavigation(request);
				}
				
				
				sentList = getLimitedItems(request,tablename,startMailCount,endMailCount,"readOrUnread");
				setAllCount(mailSForm,SentCount,startMailCount,endMailCount);
			}
		//}
		/*catch(SQLException se){
			System.out.println("Exception @ get mail sent items");
			se.printStackTrace();
			mailSForm.setMessage("Problem in getting sent mail list!");
			request.setAttribute("noRecords","noRecords");
		}*/
		request.setAttribute("SentDetails", sentList);
		mailSForm.setHeading("Mail Sent");
		request.setAttribute("openMail", "");
		return mapping.findForward("displaySentItems");
	}
	
	public ActionForward displayDeletedMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ParseException {
		HttpSession session=request.getSession();
		NewsandMediaDao ad=new NewsandMediaDao();
		MailInboxForm mailSForm=(MailInboxForm)form;
		java.util.List mailList = new java.util.ArrayList();
		String tablename = "Trash";
		UserInfo user=(UserInfo)session.getAttribute("user");
		USER_ID=user.getUserName();
		LinkedList trashedList=new LinkedList();
		int TrashCount=getCountForTable(tablename,"");
		int  startMailCount=0;
		int  endMailCount=0;
		String scount = request.getParameter("sCount");
		if(scount != null){
			startMailCount=Integer.parseInt(scount);
			endMailCount=Integer.parseInt(request.getParameter("eCount"));
		}
		basicNavigation(request);
		if(startMailCount == 0)
		{
			if(TrashCount == 0)
			{			
				mailSForm.setMessage("No mails in Trash!");
				request.setAttribute("noRecords","noRecords");
			}
			else if(TrashCount<=MAIL_PER_PAGE)
			{
				startMailCount=1;
				endMailCount=TrashCount;
				trashedList = getLimitedItems(request,tablename,startMailCount,endMailCount,"readOrUnread");
				setAllCount(mailSForm,TrashCount,startMailCount,endMailCount);
			}
			else{
				startMailCount=1;
				endMailCount=MAIL_PER_PAGE;
				trashedList = getLimitedItems(request,tablename,startMailCount,endMailCount,"readOrUnread");
				setAllCount(mailSForm,TrashCount,startMailCount,endMailCount);
				enableNextNavigation(request);
			}
		}
		else{
			String naviType = request.getParameter("fnpl");
			if(naviType.equalsIgnoreCase("next")){
				startMailCount=(startMailCount+MAIL_PER_PAGE);
				endMailCount=(endMailCount+MAIL_PER_PAGE);
				enablePreviousNavigation(request);
				if(TrashCount <= endMailCount)
				{
					disableNextNavigation(request);
					endMailCount = TrashCount;
				}
				else
				{
					enableNextNavigation(request);
				}
			}
			else if(naviType.equalsIgnoreCase("priv"))
			{
				startMailCount=(startMailCount-MAIL_PER_PAGE);
				int ecount=0;
				if(endMailCount == TrashCount)
				{
					int ec = endMailCount/MAIL_PER_PAGE;
					ecount = ec;
					endMailCount=(ecount*MAIL_PER_PAGE);
				}
				else
					endMailCount=(endMailCount-MAIL_PER_PAGE);
				if(endMailCount == MAIL_PER_PAGE)
				{
					disablePreviousNavigation(request);
					enableNextNavigation(request);
				}
				else{
					enablePreviousNavigation(request);
					enableNextNavigation(request);
				}

			}
			else if(naviType.equalsIgnoreCase("alast")){
				int ecount=0;
				endMailCount = TrashCount;
				int typeStart = endMailCount%MAIL_PER_PAGE;
				System.out.println("mod "+typeStart);
				if(typeStart == 0){
					int ec = endMailCount-MAIL_PER_PAGE;
					ecount = ec;
					startMailCount=ecount+1;
				}
				else{
					int ec = endMailCount/MAIL_PER_PAGE;
					ecount = ec;
					startMailCount=(ecount*MAIL_PER_PAGE)+1;
				}
				enablePreviousNavigation(request);
				disableNextNavigation(request);

			}
			else{
				startMailCount=1;
				endMailCount = MAIL_PER_PAGE;
				disablePreviousNavigation(request);
				enableNextNavigation(request);
			}
			
			
			trashedList = getLimitedItems(request,"Trash",startMailCount,endMailCount,"Deleted");
			setAllCount(mailSForm,TrashCount,startMailCount,endMailCount);
		}

		//get deleted item from Inbox
		
		request.setAttribute("TrashDetails", trashedList);
		request.setAttribute("openMail", "");
		mailSForm.setHeading("Mail Trash");
		return mapping.findForward("displayDeleted");
	}

	
	
	public void enablePreviousNavigation(HttpServletRequest request){
		request.setAttribute("previousButton","previousButton");
		request.setAttribute("veryFirst","veryFirst");
		request.setAttribute("disablePreviousButton","");
		request.setAttribute("disableNextButton", "");
		request.setAttribute("nextButton", "nextButton");
		request.setAttribute("atLast", "atLast");
	}
	
	public void disablePreviousNavigation(HttpServletRequest request){
		request.setAttribute("previousButton","");
		request.setAttribute("veryFirst","");
		request.setAttribute("disablePreviousButton","disablePreviousButton");
	}
	
	public void enableNextNavigation(HttpServletRequest request){
		request.setAttribute("disableNextButton", "");
		request.setAttribute("nextButton", "nextButton");
		request.setAttribute("atLast", "atLast");
	}
	
	public void disableNextNavigation(HttpServletRequest request){
		request.setAttribute("disableNextButton", "disableNextButton");
		request.setAttribute("nextButton", "");
		request.setAttribute("atLast", "");
	}
	
	public void basicNavigation(HttpServletRequest request){
		request.setAttribute("displayRecordNo", "displayRecordNo");
		request.setAttribute("disablePreviousButton","disablePreviousButton");
		request.setAttribute("disableNextButton", "disableNextButton");
	}
	
	public LinkedList getLimitedItems(HttpServletRequest request, String tname, int sCount, int eCount, String mailStatus){
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList limitsentList=new LinkedList();
		NewsandMediaDao ad=new NewsandMediaDao();
		String sql="select * from Mail_"+tname+" where emp_id ='"+user.getUserName()+"' order by Date DESC";
		try{
			ResultSet Sentrs=ad.selectQuery(sql);
			int count = 0;
			String emid= "";
			Date today = new Date();
			while(Sentrs.next()) {
				int startRow = Sentrs.getRow();
				if(startRow >= sCount)
				{
					MailInboxForm mailSentItemForm= new MailInboxForm();
					String MID = Sentrs.getString("mail_id");
					String FM = Sentrs.getString("FromAdd");
		            if(FM.indexOf("<") >= 0)
		            {
		            	FM = FM.substring(0, FM.indexOf("<"));
		            }
					String TO = Sentrs.getString("ToAdd");
					if(TO.indexOf(",") >= 0)
		            {
		            	TO = TO.substring(0, TO.indexOf(","));
		            }
					String CC = Sentrs.getString("CcAdd");
					String SB = Sentrs.getString("Subject");
					InputStream BD = Sentrs.getBinaryStream("Body");//Sentrs.getBinaryStream("Body");
					//BD = RemoveTag(BD);
					String content = IOUtils.toString(BD, "UTF-8");
					int att = Sentrs.getInt("isAttach");
					if(att == 0)
					{
						//request.setAttribute("attach", "");
						mailSentItemForm.setAttach("");
					}
					else{
						//request.setAttribute("attach", "show");
						mailSentItemForm.setAttach("attach");
					}
					int isread = Sentrs.getInt("isread");
					if(isread == 0)
					{
						mailSentItemForm.setRead("unreadTR");
					}
					else{
						mailSentItemForm.setRead("readTR");
					}
					Date mailDT = Sentrs.getDate("Date");
					mailSentItemForm.setFromInbox(FM);
					mailSentItemForm.setMailId(MID);
					mailSentItemForm.setToAddress(TO);
					mailSentItemForm.setccAddress(CC);
					mailSentItemForm.setSubject(SB);
					mailSentItemForm.setDescription(content);
					mailSentItemForm.setDateTime(mailDT.toString());
					limitsentList.add(mailSentItemForm);
					count++;
					if(count == MAIL_PER_PAGE)
					{
						break;
					}
				}
			}
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch (IOException e) { System.out.println("IOException @ getting mail"); e.printStackTrace();}
		return limitsentList;
		
	}
	/*Remove unwanted tag from mail description
	 * */
	public String RemoveTag(String html){
		   html = html.replaceAll("\\<.*?>","");
		   html = html.replaceAll("&nbsp;","");
		   html = html.replaceAll("&amp;","");
		   int len = html.length();
		   if(len != 0 && len >= 20){
		   html = html.substring(0, 20);
		   }
		   return html;
	}
	
	public List<File> saveUploadedFiles(HttpServletRequest request,MailInboxForm mailSentForm) throws IOException, ServletException, SQLException{
        List<File> listFiles = new ArrayList<File>();
        HttpSession session=request.getSession();
		NewsandMediaDao ad=new NewsandMediaDao();
		
		
        //get attachment names
        FormFile documentFile = mailSentForm.getDocumentFile();
        String documentName=documentFile.getFileName();
        UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		Date date = new Date();
		String downAtt="";
        if(documentName != "")
        {
	        try {
	        	downAtt = date+"_"+documentName;
	        	//create temp attachment files in mainDocuments folder.
	        	String filepath = getServlet().getServletContext().getRealPath("/jsp/main/mainDocuments/"+documentName);
	        	File file = new File(filepath);
	        	if (!file.exists()) {
	        		file.createNewFile();
	        	};
	        	FileOutputStream out = new FileOutputStream(file);
	        	byte[] size=documentFile.getFileData();
	        	out.write(size);
	        	out.close();
	            FileInputStream fis = new FileInputStream(file);
	            long len = file.length();
	            String  fileSize = readableSize(len);
	            
	            PreparedStatement pstmt;
	            Connection conn=ConnectionFactory.getConnection();
	            String query = ("INSERT INTO Attachments VALUES(?,?,?,?,?)");
	            pstmt = conn.prepareStatement(query);
	            pstmt.setString(1, ""+userID);
	            pstmt.setString(2,downAtt);
	            pstmt.setString(3,file.getName());
	            pstmt.setString(4, fileSize);
	            pstmt.setBinaryStream(5, fis, len);
	            pstmt.executeUpdate();
	            //method to insert a stream of bytes
	        	/*FileOutputStream out = new FileOutputStream(file);
	        	byte[] size=documentFile.getFileData();
	        	out.write(size);
	        	//String content = size.toString();
	        	System.out.println("content --> "+filepath);
	        	String insertSentQuery="INSERT INTO Attachments (emp_id, date_AttName, Att_Name, Att_Path, Att_Content) values(";
	    		insertSentQuery = insertSentQuery + "'"+ userID + "','"+downAtt+"','"+file.getName()+"','"+fileSize+"',"+fis+")";
	    		int insertCount = ad.SqlExecuteUpdate(insertSentQuery);
	        	
	            out.close();*/
				listFiles.add(file);
			} catch (FileNotFoundException e) {
				System.out.println("Exception @ mail attachments insert ");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return listFiles;
    }
     
    /**
     * Deletes all uploaded files, should be called after the e-mail was sent.
     */
    private void deleteUploadFiles(List<File> listFiles) {
        if (listFiles != null && listFiles.size() > 0) {
            for (File aFile : listFiles) {
            	System.out.println("while delete "+aFile.getName());
                aFile.delete();
            }
        }
    }
    
    public ActionForward displaySelectedMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    	HttpSession session=request.getSession();
		NewsandMediaDao ad=new NewsandMediaDao();
		MailInboxForm mailOpenForm=(MailInboxForm)form;
		java.util.List mailList = new java.util.ArrayList();
		String mId = request.getParameter("mailId");
		compId=Integer.parseInt(mId);
		LinkedList sentList=new LinkedList();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String userID=user.getUserName();
		String openFolder = request.getParameter("folder");
		int mail_Id = Integer.parseInt(mId);
		String fName = request.getParameter("fName");
		//String xxxx = getServlet()
		String sql = "select * from Mail_"+openFolder+" where emp_id ='"+userID+"' and mail_id="+mail_Id;
		/*if(fName != "")
		{
			sql = "select * from Mail_"+openFolder+" inner join UploadFiles_Mails on Mail_SentItem.mail_id = UploadFiles_Mails.id where Mail_SentItem.emp_id ='"+userID+"' and Mail_SentItem.mail_id="+mail_Id;
		}*/
		//String sql="select * from Mail_SentItem where emp_id ='"+userID+"' and mail_id="+mail_Id;
		try{
			ResultSet openMail=ad.selectQuery(sql);
			while(openMail.next()) {
				mailOpenForm.setMailId(mId);
				mailOpenForm.setFromInbox(openMail.getString("FromAdd"));
				mailOpenForm.setToAddress(openMail.getString("ToAdd"));
				mailOpenForm.setSubject(openMail.getString("Subject"));
				InputStream BD = openMail.getBinaryStream("Body");//Sentrs.getBinaryStream("Body");
				//BD = RemoveTag(BD);
				String content = IOUtils.toString(BD, "UTF-8");
				content = StringEscapeUtils.escapeJava(content);
				mailOpenForm.setDescription(content);
				request.setAttribute("bdyContent", content);
				mailOpenForm.setDateTime((openMail.getDate("Date")).toString());
				String attNames = openMail.getString("date_AttName");
				mailOpenForm.setExactAttName(attNames);
				int att = openMail.getInt("isAttach");
				if(att == 0)
				{
					mailOpenForm.setAttach("");
					LinkedList attFileList=new LinkedList();
					request.setAttribute("attachDetails", attFileList);
				}
				else{
					LinkedList attFileList=new LinkedList();
					attFileList = getAttachmentFiles(request,openFolder);
					request.setAttribute("attachDetails", attFileList);
					/*attNames = openMail.getString("file_name");//attNames.substring((attNames.indexOf("_")+1),attNames.length());
					attNames = attNames+"  -  "+openMail.getString("file_size");
					mailOpenForm.setAttach(attNames);*/
				}
				
				//if(fName.equalsIgnoreCase("down"))
				if(!fName.equalsIgnoreCase("") &&!fName.equalsIgnoreCase("display"))
				{
					/*File filepath = FileSystemView.getFileSystemView().getHomeDirectory();
					String fPath = filepath.toString()+"/"+openMail.getString("file_name");
		        	File file = new File(fPath);
		        	if (file.exists()) {
		        		file.createNewFile();
		        	}
		        	FileOutputStream out = new FileOutputStream(file);
		        	byte[] size=openMail.getBytes("Att_Content");
		        	out.write(size);
		        	
		            out.close();*/
					int filterAtt = compId;
					String attSql="select * from UploadFiles_Mails where file_name='"+fName+"' and id="+compId+" and openFolder='"+openFolder+"'";
					ResultSet downFile=ad.selectQuery(attSql);
					while(downFile.next()){
					File sourceFile = new File(downFile.getString("file_path"));
					if (!sourceFile.exists()) {
						request.setAttribute("openMail", "open");
						return mapping.findForward("displaySelectedMail");
					}
					File filepath = FileSystemView.getFileSystemView().getHomeDirectory();
					String fPath = filepath.toString()+"/"+downFile.getString("file_name");
		        	File destFile = new File(fPath);
		        	if (destFile.exists()) {
		        		destFile.createNewFile();
		        	}
					FileChannel source = null;
					FileChannel destination = null;
					source = new FileInputStream(sourceFile).getChannel();
					destination = new FileOutputStream(destFile).getChannel();
					if (destination != null && source != null) {
						destination.transferFrom(source, 0, source.size());
					}
					if (source != null) {
						source.close();
					}
					if (destination != null) {
						destination.close();
					}
					}
				}
				else{
					int mailStatus=1;
					updateMailStatus(request, openFolder, mailStatus,mail_Id);
					
				}
			}
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		request.setAttribute("openMail", "open");
		request.setAttribute("SentDetails", sentList);
		String returnString = "displaySelectedMail";
		if(openFolder.equalsIgnoreCase("Inbox")){
			returnString = "displayMailInbox";
			request.setAttribute("InboxDetails", sentList);
			mailOpenForm.setHeading("Mail Inbox");
		}
		else if(openFolder.equalsIgnoreCase("SentItem")){
			mailOpenForm.setHeading("Mail Sent");
			returnString = "displaySentItems";
		}
		else if(openFolder.equalsIgnoreCase("Trash")){
			returnString = "displayDeleted";
			request.setAttribute("TrashDetails", sentList);
		}
		String openTab = request.getParameter("search");
		if(!openTab.equalsIgnoreCase("")){
			mailOpenForm.setHeading("Search Results");
			mailOpenForm.setSearchText(openTab);
		}
		return mapping.findForward(returnString);
    }
    
    
    public String readableSize(long number) {
        long acutalSize = Math.abs(number);
        double result = number;
        String suffix = "";
        if (acutalSize < 1024) {
        } 
        else if (acutalSize < 1024 * 1024) {
          result = number / 1024.0;
          suffix = "k";
        } 
        else if (acutalSize < 1024 * 1024 * 1024) {
          result = number / (1024.0 * 1024);
          suffix = "m";
        } 
        else {
          result = number / (1024.0 * 1024 * 1024);
          suffix = "g";
        }
        return oneDecimal.format(result) + suffix;
    }
   private static DecimalFormat oneDecimal = new DecimalFormat("0.0");
   
   public double getLimitSize(long number) {
       long acutalSize = Math.abs(number);
       double result = number;
       String suffix = "";
       if (acutalSize < 1024) {
       } 
       else if (acutalSize < 1024 * 1024) {
         result = number / 1024.0;
       } 
       else if (acutalSize < 1024 * 1024 * 1024) {
         result = number / (1024.0 * 1024);
       } 
       else {
         result = number / (1024.0 * 1024 * 1024);
       }
       return result;
   }
    
    public int getCountForTable(String tname, String forAttach){
    	int rowCount = 0;
    	MainDao exeQry = new MainDao();
    	if(!forAttach.equalsIgnoreCase("Attach")){
	    	String sql="select count(*) from Mail_"+tname+" where emp_id ='"+USER_ID+"'";
	    	if(forAttach.equalsIgnoreCase("Leave")){
    			sql="select count(*) from "+tname+" where user_id ='"+USER_ID+"'";
    		}
		try{
			ResultSet countrs=exeQry.selectQuery(sql);
			while(countrs.next()) {
					rowCount=Integer.parseInt(countrs.getString(1));
			}
		}
		catch(SQLException se){
			System.out.println("Exception @ getting count");
			se.printStackTrace();
			}
    	}
    	else{
    		
    		String sql="select count(*) from "+tname+" where userId ='"+USER_ID+"' and id="+compId;
			try{
				ResultSet countrs=exeQry.selectQuery(sql);
				while(countrs.next()) {
					rowCount=Integer.parseInt(countrs.getString(1));
				}
			}
			catch(SQLException se){
				System.out.println("Exception @ getting count");
				se.printStackTrace();
			}
		}
    	return rowCount;
    }
    
    private void setAllCount(MailInboxForm mailForm,int totalCount, int sCount, int eCount){
		mailForm.setTotalMailCount(totalCount);
		mailForm.setStartMailCount(sCount);
		mailForm.setEndMailCount(eCount);
    }
    public String getInboxMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException{
    	String HOST_NAME = "mail.microlabs.co.in";
		HttpSession seson=request.getSession();
		UserInfo user=(UserInfo)seson.getAttribute("user");
    	LinkedList  userInfo = getUserLoginDetails(request);
    	final String USER_NAME = userInfo.get(1).toString();
    	password = getUserPassword(request);
    	String statusmessage="No Error";
    	if(password == null || password == ""){
    		return statusmessage;
    	}
    	//password = userInfo.get(2).toString();
    	String protocol ="pop3";
    	MainDao ad = new MainDao();
    	Properties props = new Properties();
    	Date Indate = new Date();
		props.put("mail.pop3.host",HOST_NAME);
		props.put("mail.store.protocol", protocol);
		try {
			Session session = Session.getInstance(props);
			Store store = null;
			Folder inbox = null;
			store = session.getStore(protocol);
			store.connect(HOST_NAME, USER_NAME, password);
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			System.out.println("... emicro.."+inbox.getMessageCount());
			int Count = 0;
			Date lastUpdate = new Date();
			long lup = lastUpdate.getTime();
			java.sql.Timestamp lts = new Timestamp(lup);
			String sql="select Date from Mail_Inbox where emp_id ='"+USER_ID+"' order by Date";
			ResultSet Sentrs=ad.selectQuery(sql);
			while(Sentrs.next()){
				Count++;
				lts = Sentrs.getTimestamp("Date");
			}
			Flags recent = new Flags(Flags.Flag.RECENT);
		    FlagTerm recentFlagTerm = new FlagTerm(recent, false);
		    Message messages[] = inbox.search(recentFlagTerm);
		    System.out.println("... emicro.."+messages.length);
			//Message[] messages = inbox.getMessages();
			   for (int i = 0; i < messages.length; i++) {
				   
					Message message = messages[i];
					Date mailDt = message.getSentDate();
					long ld1 = mailDt.getTime();
					java.sql.Timestamp ts1 = new Timestamp(ld1);
					System.out.println(lts);
					System.out.println(ts1);
					if(Count == 0){addInboxMail(message, i, user.getUserName());}
					else {
						if(ts1.compareTo(lts) > 0){addInboxMail(message, i, user.getUserName());}
				   }
			   }
			   inbox.close(false);
			   store.close();
		} 
		catch (NoSuchProviderException e) {System.out.println("exception @ get Inbox mail"); e.printStackTrace();} 
		catch (MessagingException e) { 
			System.out.println("exception @ get Inbox mail"); 
			String errormsg = e.getMessage();
			if(errormsg.contains("Connect failed")){
				statusmessage = "No Internet Connection!";
			}
			e.printStackTrace();
		} 
		return statusmessage;
    }
    
    public void addInboxMail(Message msg, int msgCount, String USER_NAME){
    	int isAtt = 0;
    	Message message = msg;
    	int i = msgCount;
    	try{
    		String fromAddresses = "";
			Address[] fromAdd = message.getFrom();
			for (Address address : fromAdd) {
				if(fromAddresses.equalsIgnoreCase("")){
					fromAddresses = address.toString();
				}
				else{
					fromAddresses = fromAddresses+","+address.toString();
				}
			}
			System.out.println(fromAddresses);
			System.out.println("---------------------------------");
			System.out.println("Email Number " + i);
			compId = i+1;
			String toAddresses = "";
			Address[] recipients = message.getRecipients(Message.RecipientType.TO);
			for (Address address : recipients) {
				if(toAddresses.equalsIgnoreCase("")){
					toAddresses = address.toString();
				}
				else{
					toAddresses = toAddresses+","+address.toString();
				}
			}
			System.out.println(toAddresses);
			String ccAddresses = "";
			
			Address[] ccRecipients = message.getRecipients(Message.RecipientType.CC);
			if(ccRecipients != null){
				for (Address ccaddress : ccRecipients) {
					if(ccAddresses.equalsIgnoreCase("")){
						ccAddresses = ccaddress.toString();
					}
					else{
						ccAddresses = ccAddresses+","+ccaddress.toString();
					}
				}
			}
			System.out.println(ccAddresses);
			System.out.println(message.getFlags());
			MainDao ad = new MainDao();
			System.out.println("Subject: " + message.getSubject());
			System.out.println("From: " + message.getFrom()[0]);
			String contentType = message.getContentType();
			//int InboxTotal = getCountForTable("Inbox","");
			int  InboxTotal=0;
			String getRequstNo="select max(mail_id) from Mail_Inbox";
			ResultSet rsRequestNo=ad.selectQuery(getRequstNo);
			while(rsRequestNo.next())
			{
				InboxTotal=rsRequestNo.getInt(1);
			}
			Date rDate= message.getSentDate();
			long ld = rDate.getTime();
			java.sql.Timestamp ts = new Timestamp(ld);
            String messageContent = "";
            
            //For Body and Attachments part
            
            if (contentType.contains("text/html") || contentType.contains("text/plain"))
            {
            	try {
                    Object content = message.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                } catch (Exception ex) {
                    messageContent = "[Error downloading content]";
                    ex.printStackTrace();
                }
                
            }
            else if(contentType.contains("multipart"))
            {
            	Object obj = message.getContent();
            	if (obj instanceof Multipart) {
                    Multipart multipart = (Multipart)obj;
                    System.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");
                    System.out.println(multipart.getContentType());
                    
                    for (int j=0; j<multipart.getCount(); j++) {
                    	BodyPart p = multipart.getBodyPart(j);
                    	String disposition = p.getDisposition();
                    	if (disposition == null)
                        {
                            // Check if plain
                            MimeBodyPart mbp = (MimeBodyPart) p;
                            if(mbp.getContent() instanceof MimeMultipart)
                            {
                                Multipart mp = (Multipart)mbp.getContent();
                    			int count = mp.getCount();
                    			for (int k = 0; k < count; k++){
                    			BodyPart bp = mp.getBodyPart(k);
                    			messageContent = bp.getContent().toString();
                    			}
                                System.out.println("bodyContent " + messageContent);
                            }
                            else
                            {
                                String msgText=  multipart.getBodyPart(0).getContent().toString();
                                messageContent = msgText;
                                System.out.println("Message::::::::::: "+msgText);
                            }
                            
                        }
                 
                    	else { 
                    		if ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))) {
                    			isAtt = 1;
                    			saveAttachment(p,(InboxTotal+1)); 
                    		}
                    	}
                    	/*else if(p.isMimeType("multipart/*") || contentType.contains("multipart/mixed")){
                    		
                    			Multipart mp = (Multipart)p.getContent();
                    			int count = mp.getCount();
                    			for (int k = 0; k < count; k++){
                    			BodyPart bp = mp.getBodyPart(k);
                    			messageContent = bp.getContent().toString();
                    			}
                    		
                    	}
                    	else if(contentType.contains("text/plain") || contentType.contains("text/html"))
                    	{
                    		
                    	}*/
                    	
                    }
                }
            }
                        
   
            //Multipart multipart = (Multipart) message.getContent();
            //System.out.println(multipart.getCount());

           /*if (contentType.contains("text/plain") || contentType.contains("text/html") || contentType.contains("multipart/mixed")) {
                try {
                    Object content = message.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                } catch (Exception ex) {
                    messageContent = "[Error downloading content]";
                    ex.printStackTrace();
                }
           	}*/
            System.out.println("\t Message: " + messageContent);
			/*String insertInboxQuery="INSERT INTO Mail_Inbox (emp_id, FromAdd, ToAdd, CcAdd, Subject, Body, isAttach, Date, date_AttName, isread) values(";
			insertInboxQuery = insertInboxQuery + "'"+ USER_ID + "','"+message.getFrom()[0]+"','" + USER_NAME +"','','"+message.getSubject()+"','"+messageContent+"',0, '"+ts+"','',0)";
			insertCount = ad.SqlExecuteUpdate(insertInboxQuery);*/
			//message.setFlag(Flags.Flag.SEEN, true);
            LinkedList paramList = new LinkedList();
			PreparedStatement pstmt;
            Connection conn=ConnectionFactory.getConnection();
            String query = "INSERT INTO Mail_Inbox VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            String fromName = ""+message.getFrom()[0];
            /*paramList.add(1, ""+USER_ID);
            paramList.add(2, fromName);
            paramList.add(3, USER_NAME);
            paramList.add(4, "");
            paramList.add(5, message.getSubject());
            paramList.add(6, messageContent);
            paramList.add(7, isAtt);
            paramList.add(8, ld);
            paramList.add(9, "");
            paramList.add(10, 0);
            int insertCount = ad.SqlExecuteUpdate(inboxquery, paramList);*/
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, (InboxTotal+1));
            pstmt.setString(2, USER_NAME);
            
            pstmt.setString(3, fromName);
            pstmt.setString(4, toAddresses);
            pstmt.setString(5, ccAddresses);
            pstmt.setString(6, message.getSubject());
            System.out.println(messageContent.length());
            InputStream stream = IOUtils.toInputStream(messageContent);
            //InputStream stream = new ByteArrayInputStream(messageContent.getBytes("UTF-8"));
            pstmt.setBinaryStream(7, stream);
            pstmt.setInt(8, isAtt);
            pstmt.setTimestamp(9, ts);
            pstmt.setString(10, "");
            pstmt.setInt(11, 0);
            pstmt.executeUpdate();
    	}
    	catch (IOException e) { System.out.println("IOException @ get Inbox mail"); e.printStackTrace();}
    	catch (MessagingException e) { System.out.println("MessagingException @ get Inbox mail"); e.printStackTrace();}
		catch (SQLException e) { System.out.println("SQLException @ get Inbox mail"); e.printStackTrace();}
    }
    
    public ActionForward displaySaveMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		MailInboxForm mailSaveForm=(MailInboxForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		Date date = new Date();
		int isAtt = 0;
		String downAtt="";
		try{
			fromAddress = mailSaveForm.getFromInbox();
			if(fromAddress == null){
				fromAddress = user.getUserName();
			}
			String to = request.getParameter("toAdd");
			System.out.println(to);
			String cc = request.getParameter("ccAdd");
			if(cc == null){
				cc = "";
			}
			String bcc = request.getParameter("bccAdd");
			if(bcc == null){
				bcc = "";
			}
			String sub =  request.getParameter("sub");
			String bdy = request.getParameter("content");
			if(bdy == null){
				bcc = "";
			}
			InputStream stream = IOUtils.toInputStream(bdy);
			//String attNames =  request.getParameter("attachNames");
			int attCount = getCountForTable("UploadFiles_Mails","Attach");
	        if(attCount > 0)
	        {
	        	isAtt = 1;
	        }
	        int insertCount = 0;
	        int idInTable=0;
	        System.out.println(compId);
	        String InsertOrUpsql="select * from Mail_Draft where emp_id='"+user.getUserName()+"'";
			try{
				ResultSet rs=ad.selectQuery(InsertOrUpsql);
				while (rs.next()) {
					idInTable= rs.getInt("mail_id");
					if(compId == idInTable)
					{
						String deleteRow = "delete from Mail_Draft where mail_id="+idInTable;
						int dl = ad.SqlExecuteUpdate(deleteRow);
						
					}
				}
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			//String insertSentQuery="INSERT INTO Mail_Draft (mail_id,emp_id, FromAdd, ToAdd, CcAdd, Subject, Body, isAttach, Date, date_AttName, isread) values("+compId;
			//insertSentQuery = insertSentQuery + ",'"+ user.getUserName() + "','"+fromAddress+"','" + to +"','"+cc+"','"+sub+"',"+stream+","+isAtt+", current_timestamp,'"+downAtt+"',0)";
			//insertCount = ad.SqlExecuteUpdate(insertSentQuery);
			LinkedList paramList = new LinkedList();
			PreparedStatement pstmt;
	        Connection conn=ConnectionFactory.getConnection();
	        String query = "INSERT INTO Mail_Draft VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	        pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, compId);
	        pstmt.setString(2, user.getUserName());
	        
	        pstmt.setString(3, fromAddress);
	        pstmt.setString(4, to);
	        pstmt.setString(5, cc);
	        pstmt.setString(6, sub);
	        pstmt.setBinaryStream(7, stream);
	        pstmt.setInt(8, isAtt);
	        Date rDate = new Date();
	        long ld = rDate.getTime();
			java.sql.Timestamp ts = new Timestamp(ld);
	        pstmt.setTimestamp(9, ts);
	        pstmt.setString(10, downAtt);
	        pstmt.setInt(11, 0);
	        pstmt.executeUpdate();
			request.setAttribute("mailId", compId);
			displayComposeMail(mapping, form, request, response);
		}
		catch (SQLException e) { System.out.println("SQLException @ save mail"); e.printStackTrace();}
		return mapping.findForward("displayMailNew");
    }
    public ActionForward deleteUploadedFiles(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
    	MainDao ad = new MainDao();
		MailInboxForm mailInForm=(MailInboxForm)form;
		try{
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			int  userId=user.getId();
			String checkedValues=request.getParameter("cValues");
			LinkedList paramList = new LinkedList();
			String deleteQuery="delete from UploadFiles_Mails where file_name='"+checkedValues+"' and userId='"+userId+"'";
			ad.SqlExecuteUpdate(deleteQuery, paramList);
			
			LinkedList attFileList=new LinkedList();
			attFileList = getAttachmentFiles(request,"SentItem");
			request.setAttribute("listName", attFileList);
			
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("ajaxFileUpload");
	}
	public ActionForward uploadFiles(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	System.out.println("uploadFiles()-----------");
	MainDao ad = new MainDao();
	MailInboxForm mailInForm=(MailInboxForm)form;
	try{
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int  userId=user.getId();
		USER_ID = user.getUserName();
			FormFile myFile = mailInForm.getDocumentFile();
		 //   String contentType = myFile.getContentType();
			String fileName = myFile.getFileName();
			byte[] fileData = myFile.getFileData();	
			//File filepath = FileSystemView.getFileSystemView().getHomeDirectory();
			createDirForAttachments("Sent",compId);
			String filepath = getServlet().getServletContext().getRealPath("/jsp/main/mailAttachments/Sent/user_"+USER_ID+"/"+compId+"/"+fileName);
        	File file = new File(filepath);
        	if (!file.exists()) {
        		file.createNewFile();
        	};
        	FileOutputStream out = new FileOutputStream(file);
        	out.write(fileData);
        	out.close();
            FileInputStream fis = new FileInputStream(file);
            long len = file.length();
            overallAttSize = (overallAttSize + len);
            double uploadSize = getLimitSize(overallAttSize);
            double attLimitString = getLimitSize(attLimit);
            if(overallAttSize > attLimit){
            	mailInForm.setMailMessage("You Exceed the Attachment Limit to Send");
            }
            else{
	            System.out.println(uploadSize+" <- uplodSize - attLimit -> "+attLimitString);
	            String  fileSize = readableSize(len);
				//String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/Home/Mail/UploadFiles");
				/*System.out.println(filePath);
				mailInForm.setUploadFilePath(filePath);
				File destinationDir = new File(filePath);
				long len = (long)destinationDir.length();
	            String  fileSize = readableSize(len);
				if(!destinationDir.exists())
				{
					destinationDir.mkdirs();
				}
				if (!fileName.equals("")) {
					File fileToCreate = new File(filePath, fileName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);
						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
					}
				}*/
				request.setAttribute("fileName", fileName);
				filepath = filepath.replace("\\", "\\");
				String sql9="select count(*) from UploadFiles_Mails where  file_name='"+fileName+"' and id="+compId;
				ResultSet rs15 = ad.selectQuery(sql9);
				int fileCount=0;
				while (rs15.next())
				{
					fileCount=Integer.parseInt(rs15.getString(1));
				}
				if(fileCount>0)
				{
					mailInForm.setMailMessage("File aleardy uploaded please choose another file or change the file name");
				}
				else
				{
					LinkedList paramList = new LinkedList();
				String insertsql = "insert into UploadFiles_Mails(id,userId,file_path,file_name,file_size,openFolder) values('"+compId+"','"+userId+"','"+filepath+"','"+fileName+"','"+fileSize+"','SentItem')";
				int a = ad.SqlExecuteUpdate(insertsql,paramList);
				if (a > 0)
				{
					mailInForm.setMailMessage("Documents uploaded successfully");
					
				}else {
					mailInForm.setMessage("Error while uploading files ... Please check entered Values");
				}
				
				}
			}
			LinkedList attFileList=new LinkedList();
			attFileList = getAttachmentFiles(request,"SentItem");
			request.setAttribute("listName", attFileList);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return mapping.findForward("ajaxFileUpload");
	}
	
	public ActionForward displayDraftMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException{
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		MailInboxForm mailDForm=(MailInboxForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		USER_ID=user.getUserName();
		LinkedList InList=new LinkedList();
		String tablename = "Draft";
		int  startMailCount=0;
		int  endMailCount=0;
		String scount = request.getParameter("sCount");
		if(scount == null){
			System.out.println(mailDForm.getMessage());
			mailDForm.setMailMessage("Mail Sent Successfully!");
		}
		if(scount != null){
			startMailCount=Integer.parseInt(scount);
			endMailCount=Integer.parseInt(request.getParameter("eCount"));
		}
		//int  startMailCount=Integer.parseInt(request.getParameter("sCount"));
		//int  endMailCount=Integer.parseInt(request.getParameter("eCount"));
		basicNavigation(request);
		int InboxTotal = getCountForTable(tablename,"");
		if(startMailCount == 0){
			if(InboxTotal == 0){
				mailDForm.setMessage("No mails in Draft!");
				request.setAttribute("noRecords","noRecords");
			}
			else if(InboxTotal <= MAIL_PER_PAGE)
			{
				startMailCount=1;
				endMailCount=InboxTotal;
			}
			else
			{
				startMailCount=1;
				endMailCount=MAIL_PER_PAGE;
				enableNextNavigation(request);
			}
			/*InList = getLimitedItems(request,tablename,startMailCount,endMailCount);
			setAllCount(mailInForm,InboxTotal,startMailCount,endMailCount);*/
		}
		else{
			String naviType = request.getParameter("fnpl");
			if(naviType.equalsIgnoreCase("next")){
				startMailCount=(startMailCount+MAIL_PER_PAGE);
				endMailCount=(endMailCount+MAIL_PER_PAGE);
				enablePreviousNavigation(request);
				if(InboxTotal <= endMailCount)
				{
					disableNextNavigation(request);
					endMailCount = InboxTotal;
				}
				else
				{
					enableNextNavigation(request);
				}
			}
			else if(naviType.equalsIgnoreCase("priv"))
			{
				startMailCount=(startMailCount-MAIL_PER_PAGE);
				int ecount=0;
				if(endMailCount == InboxTotal)
				{
					int ec = endMailCount/MAIL_PER_PAGE;
					ecount = ec;
					endMailCount=(ecount*MAIL_PER_PAGE);
				}
				else
					endMailCount=(endMailCount-MAIL_PER_PAGE);
				if(endMailCount == MAIL_PER_PAGE)
				{
					disablePreviousNavigation(request);
					enableNextNavigation(request);
				}
				else{
					enablePreviousNavigation(request);
					enableNextNavigation(request);
				}

			}
			else if(naviType.equalsIgnoreCase("alast")){
				int ecount=0;
				endMailCount = InboxTotal;
				int typeStart = endMailCount%MAIL_PER_PAGE;
				System.out.println("mod "+typeStart);
				if(typeStart == 0){
					int ec = endMailCount-MAIL_PER_PAGE;
					ecount = ec;
					startMailCount=ecount+1;
				}
				else{
					int ec = endMailCount/MAIL_PER_PAGE;
					ecount = ec;
					startMailCount=(ecount*MAIL_PER_PAGE)+1;
				}
				enablePreviousNavigation(request);
				disableNextNavigation(request);
			}
			else{
				startMailCount=1;
				endMailCount = MAIL_PER_PAGE;
				disablePreviousNavigation(request);
				enableNextNavigation(request);
			}
			
			
			/*InList = getLimitedItems(request,tablename,startMailCount,endMailCount);
			setAllCount(mailInForm,InboxTotal,startMailCount,endMailCount);*/
		}
		InList = getLimitedItems(request,tablename,startMailCount,endMailCount,"readOrUnread");
		setAllCount(mailDForm,InboxTotal,startMailCount,endMailCount);
		request.setAttribute("DraftDetails", InList);
		mailDForm.setHeading("Mail Draft");
		return mapping.findForward("displayMailDraft");
	}
	
	
	private void saveAttachment(BodyPart part, int attMsgId) {
		 try {
			 MainDao ad = new MainDao();
			 String filename = part.getFileName();
			 InputStream input = part.getInputStream();
			 /*int InboxTotal = getCountForTable("Inbox","");
			 InboxTotal = InboxTotal+1;*/
			 createDirForAttachments("Inbox",attMsgId);
			 String filepath = getServlet().getServletContext().getRealPath("/jsp/main/mailAttachments/Inbox/user_"+USER_ID+"/"+attMsgId+"/"+filename);
     		 File file = new File(filepath);
     		 if (!file.exists()) {
     		 	file.createNewFile();
     		 };
     		 FileOutputStream output = new FileOutputStream(file);
     		 byte[] bytearr = new byte[512];
     		 int len = 0;
     		 try { 
     			 while ((len = input.read(bytearr)) != -1) {
     			 output.write(bytearr, 0, len);
     		 	}
     			
     		 } 
     		 catch (IOException exp) {
     			 throw exp;
     		 } finally {
     			 input.close();
     			 output.close();
     		 }
     		File sourceFile = new File(getServlet().getServletContext().getRealPath("/jsp/main/mailAttachments/Inbox/user_"+USER_ID+"/"+attMsgId+"/"+filename));
     		FileChannel source = new FileInputStream(sourceFile).getChannel();
     		String fileSize = readableSize(source.size());
     		String insertsql = "insert into UploadFiles_Mails(id,userId,file_path,file_name,file_size,openFolder) values('"+attMsgId+"','"+USER_ID+"','"+filepath+"','"+filename+"','"+fileSize+"','Inbox')";
     		LinkedList paramList = new LinkedList();
			int a = ad.SqlExecuteUpdate(insertsql, paramList);
		 } catch (Exception exp) {
			 exp.printStackTrace();
		 }
	}
	
	public LinkedList getAttachmentFiles(HttpServletRequest request, String folder){
		LinkedList attLists=new LinkedList();
		NewsandMediaDao ad=new NewsandMediaDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int  userId=user.getId();
		ResultSet rs5 = ad.selectQuery("select * from UploadFiles_Mails where userId='"+userId+"' and id="+compId+" and openFolder='"+folder+"'");
		
		try {
			while (rs5.next()) {
						
				MailInboxForm custForm1 = new MailInboxForm();
				String s=rs5.getString("file_name");
				custForm1.setFileList1(s);
				//String filePath1 = getServlet().getServletContext().getRealPath("jsp/ess/sapMasterRequest/Raw Materials Files/UploadFiles/"+rs5.getString("file_name")+"");
				//String filePath="jsp/EMicro Files/Home/Mail/UploadFiles"+"/"+rs5.getString("file_name");
				
				custForm1.setUploadFilePath(rs5.getString("file_path"));
				custForm1.setFileSize(rs5.getString("file_size"));
				
				attLists.add(custForm1);
			}
		} catch (SQLException e) {
			System.out.println("Exception @ getAttachments");
			e.printStackTrace();
		}
		return attLists;
		
	}
	
	public List<File> getFilesToSend(LinkedList attList) throws IOException, ServletException, SQLException{
        List<File> listFiles = new ArrayList<File>();
		if (attList != null)
	 	{
			Iterator itr=attList.iterator();
		
	        while(itr.hasNext()){
	        	MailInboxForm fileInfo=(MailInboxForm) itr.next();
	        	String documentName = fileInfo.getFileList1();
	        	System.out.println(documentName);
	        	String filepath = fileInfo.getUploadFilePath();
	        	File file = new File(filepath);
	        	listFiles.add(file);
	        }
	 	}
		else{
			String filepath = getServlet().getServletContext().getRealPath("/jsp/main/mailAttachments/Sent/user_"+USER_ID+"/"+compId);
			File file = new File(filepath);        
	        String[] myFiles;      
	        if(file.isDirectory()){  
	        	myFiles = file.list();  
	        	for (int i=0; i<myFiles.length; i++) {  
	        		File myFile = new File(file, myFiles[i]);
	        		listFiles.add(myFile);
	        		//myFile.delete();  
	        	}  
	        }  
		}
        return listFiles;
    }
	
	
	public void deleteUploadAttach(HttpServletRequest request){
		HttpSession session=request.getSession();
		NewsandMediaDao ad=new NewsandMediaDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		int rowCount = 0;
		String sql="select count(*) from Mail_Draft where emp_id ='"+USER_ID+"' and mail_id="+compId;
		try{
			ResultSet countrs=ad.selectQuery(sql);
			while(countrs.next()) {
					rowCount=Integer.parseInt(countrs.getString(1));
			}
			if(rowCount == 0){
				LinkedList paramList = new LinkedList();
				List<File> uploadedFiles = getFilesToSend(null);
				String deleteQuery="delete from UploadFiles_Mails where id="+compId+" and userId='"+userID+"'";
				ad.SqlExecuteUpdate(deleteQuery);
				deleteUploadFiles(uploadedFiles);
			}
		}
		catch (IOException ioe) { System.out.println("IOException @ Cancel the Draft"); ioe.printStackTrace();}
		catch (ServletException se) { System.out.println("MessagingException @ get Inbox mail"); se.printStackTrace();}
		catch(SQLException sqle){System.out.println("Exception @ Cancel the Draft");sqle.printStackTrace();}
	}
	public void createDirForAttachments(String folderName, int cId){
		System.out.println("user id"+USER_ID);
		File uploadDir = new File(getServlet().getServletContext().getRealPath("/jsp/main/mailAttachments/"+folderName));
		if (!uploadDir.exists()) 
		{
			uploadDir.mkdirs();
		}
		uploadDir = new File(getServlet().getServletContext().getRealPath("/jsp/main/mailAttachments/"+folderName+"/user_"+USER_ID));
		if (!uploadDir.exists()) 
		{
			uploadDir.mkdirs();
		}
		uploadDir = new File(getServlet().getServletContext().getRealPath("/jsp/main/mailAttachments/"+folderName+"/user_"+USER_ID+"/"+cId));
		if (!uploadDir.exists()) 
		{
			uploadDir.mkdirs();
		}
	}
	
	/*public void addApproversForForm(){
		MainDao exeQry = new MainDao();
		String tName = "FormApprovers";
		String formId = "F1";
		int te = exeQry.checkTableExist(tName);
		String crtSQL="";
		if(te == 0)
		{
			crtSQL = "CREATE TABLE "+tName+" (FormAttribute nvarchar(100), "+formId+" nvarchar(200))";
		}
		else{
			crtSQL = "ALTER TABLE "+tName+" ADD F2 nvarchar(200)";
		}
		int crt = exeQry.SqlExecuteUpdate(crtSQL);
		//insert approvers
		if(crt == 0)
		{
			String formName = "Leave";
			int levels = 3;
			
			String inSQL = "INSERT INTO "+tName+" (FormAttribute, F1)";
			inSQL = inSQL + " SELECT 'FormName', '"+formName+"' UNION ALL";
			inSQL = inSQL + " SELECT 'Levels', '"+levels+"'";
			
			for(int i = 1; i <= levels; i++)
			{
				String name = "aaa"+i;
				inSQL = inSQL + " UNION ALL SELECT 'level"+i+"', '"+name+"'";
			}
			LinkedList paramList = new LinkedList();
			int ins = exeQry.SqlExecuteUpdate(inSQL, paramList);
			
			//select from FormApprovers
			
		}

	}*/
	
	public ActionForward removeMsgFromList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		MailInboxForm mailDeleteForm=(MailInboxForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		String mId = request.getParameter("mailId");
		String openFolder = request.getParameter("folder");
		int mail_Id = Integer.parseInt(mId);
		int mailStatus = 2;
		int up = updateMailStatus(request,openFolder, mailStatus,mail_Id);
		String sentSuccuss = "Error While Deleting a Mail!";
		if(up == 1){
			sentSuccuss = "Message Deleted Succussfully!";
			mailDeleteForm.setMailMessage(sentSuccuss);
		}
		String returnMsg="displayInboxMailRemove";
		if(openFolder.equalsIgnoreCase("SentItem")){
			returnMsg="displaySentMailRemove";
		}
		else if(openFolder.equalsIgnoreCase("Draft")){
			returnMsg="displayDraftMailRemove";
		}
		else if(openFolder.equalsIgnoreCase("Trash")){
			returnMsg="displayTrashMailRemove";
		}
		else
		{
			returnMsg="displayInboxMailRemove";
		}
		return mapping.findForward(returnMsg);
	}
	
	private int updateMailStatus(HttpServletRequest request, String folderName, int msgStatus, int msgId){
		MainDao exeQry = new MainDao();
		int upd = 0;
		String updateSql = "";
		LinkedList paramList =  new LinkedList();
		if(msgStatus == 2){
			int insert = 0;
			deleteMailFromMailServer(request, folderName, msgId);
			//If foldername not trash means, then insert into trash table and delete from selected table
			//Else foldername is trash, then directly delete the msg from Trash. No Need to Insert.
			if(!folderName.equalsIgnoreCase("Trash")){
				updateSql = "insert into Mail_Trash select * from Mail_"+folderName+" where mail_id="+msgId;
				insert=exeQry.SqlExecuteUpdate(updateSql, paramList);
			}
			else{
				insert = 1;
			}
			if(insert > 0){
				updateSql = "delete from Mail_"+folderName+" where mail_id="+msgId;
				upd=exeQry.SqlExecuteUpdate(updateSql, paramList);
			}
		}
		else{
			updateSql= "update Mail_"+folderName+" set isread="+msgStatus+" where mail_id="+msgId;
			upd=exeQry.SqlExecuteUpdate(updateSql, paramList);
		}
		return upd;
	}
	
	
	public void deleteMailFromMailServer(HttpServletRequest request, String folderName, int msgId){
		//get msg details from db
		String userID = USER_ID;
		MainDao exeQry = new MainDao();
		String sub = "";
		Date lastUpdate = new Date();
		long lup = lastUpdate.getTime();
		java.sql.Timestamp lts = new Timestamp(lup);
		String sql = "select * from Mail_"+folderName+" where emp_id ='"+userID+"' and mail_id="+msgId;
		try{
			ResultSet openMail=exeQry.selectQuery(sql);
			while(openMail.next()) {
				sub = openMail.getString("Subject");
				lts = openMail.getTimestamp("Date");
			}
			
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		String openfolder = "INBOX";
		/*if(folderName.equalsIgnoreCase("SentItem")){
			openfolder = "SENT";
		}
		else if(folderName.equalsIgnoreCase("TRASH")){
			openfolder = "TRASH";
		}*/
		String HOST_NAME = "mail.microlabs.co.in";
    	String USER_NAME = "emicro@microlabs.in";
    	String protocol ="pop3";
    	MainDao ad = new MainDao();
    	//password = "micro1@";
    	password = getUserPassword(request);
    	Properties props = new Properties();
    	Date Indate = new Date();
		props.put("mail.pop3.host",HOST_NAME);
		props.put("mail.store.protocol", protocol);
		try {
			Session session = Session.getInstance(props);
			Store store = null;
			Folder inbox = null;
			store = session.getStore(protocol);
			store.connect(HOST_NAME, USER_NAME, password);
			inbox = store.getFolder(openfolder);
			inbox.open(Folder.READ_WRITE);
			System.out.println("... emicro.."+inbox.getMessageCount());
			Flags recent = new Flags(Flags.Flag.RECENT);
		    FlagTerm recentFlagTerm = new FlagTerm(recent, false);
		    Message messages[] = inbox.getMessages();;
		    System.out.println("... emicro.."+messages.length);
			for (int i = 0; i < messages.length; i++) {
			   
				Message message = messages[i];
				String msgSub = message.getSubject();
				Date mailDt = message.getSentDate();
				long ld1 = mailDt.getTime();
				java.sql.Timestamp ts1 = new Timestamp(ld1);
				System.out.println(lts);
				System.out.println(ts1);
				if(ts1.compareTo(lts) == 0 && msgSub.equalsIgnoreCase(sub)){
					message.setFlag(Flags.Flag.DELETED, true);
				}
				
				if(message.isSet (Flags.Flag.DELETED)) {
					System.out.println("Deleted message");
				}
			}
			inbox.close(true);
			store.close();
		} 
		catch (NoSuchProviderException e) {System.out.println("exception @ get Inbox mail"); e.printStackTrace();} 
		catch (MessagingException e) { System.out.println("exception @ get Inbox mail"); e.printStackTrace();}
		//set delete flag for msg in mail server
	}
	
	public String getUserPassword(HttpServletRequest request){
		String pwd="";
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String user_company_emiail = user.getMail_id();
		int  USER_ID = user.getId();
		MainDao exeQry = new MainDao();
		
		try{
		String sql1="select emp_mail_pwd from emp_master where emp_id='"+user.getUserName()+"'";
		
		ResultSet rs=exeQry.selectQuery(sql1);
		while (rs.next()) {
			InputStream password = rs.getBinaryStream("emp_mail_pwd");
			pwd = IOUtils.toString(password, "UTF-8");
			System.out.println(pwd);
		}
		}
		catch(Exception se){
			System.out.println("Error While getting password");
			se.printStackTrace();
		}
		return pwd;
	}
	public LinkedList getNextApproverDetails(HttpServletRequest request,String requesterID){

		LinkedList userDetails = new LinkedList();
		HttpSession session=request.getSession();
		MainDao ad = new MainDao(); 
		String userName = "";
		String userMail = "";
		String userPass = "";
		try{
			String getApproverDetails="select PERNR,EMAIL_ID,password,EMP_FULLNAME from emp_official_info AS emp,users as u where PERNR='"+requesterID+"' and u.username=emp.PERNR";
			ResultSet rs=ad.selectQuery(getApproverDetails);
			while(rs.next()){
				userName = rs.getString("PERNR");
				userMail = rs.getString("EMP_FULLNAME");
				userPass = rs.getString("password");
			}
		
			userMail=userMail.trim();
			userMail=userMail.replaceAll("\\s+","");
		
		userDetails.add(0,userName);
		userDetails.add(1,userMail);
		userDetails.add(2,userPass);
		}
		catch (SQLException sqle) { System.out.println("SQLException @ sending User Details"); sqle.printStackTrace();}
		return userDetails;
	
	}
	
	public LinkedList getOfficeEmpDetails(HttpServletRequest request,String empNo){
		LinkedList userDetails = new LinkedList();
		HttpSession session=request.getSession();
		MainDao ad = new MainDao(); 
		String userName = "";
		String userMail = "";
		String userPass = "";
		try{
	
			String getApproverID="select PERNR,EMP_FULLNAME,u.password from emp_official_info as emp,users as u where PERNR='"+empNo+"' and u.username=PERNR";
			
			ResultSet approverRS=ad.selectQuery(getApproverID);
			while(approverRS.next()){
				
				userName = approverRS.getString("PERNR");
				userMail = approverRS.getString("EMP_FULLNAME");
				userPass = approverRS.getString("password");
					
			}
	
	
			userMail=userMail.trim();
			userMail=userMail.replaceAll("\\s+","");
		
		userDetails.add(0,userName);
		userDetails.add(1,userMail);
		userDetails.add(2,userPass);
		}
		catch (SQLException sqle) { System.out.println("SQLException @ sending User Details"); sqle.printStackTrace();}
		return userDetails;
	}
	
	public LinkedList getUserLoginDetails(HttpServletRequest request){
		LinkedList userDetails = new LinkedList();
		HttpSession session=request.getSession();
		MainDao ad = new MainDao(); 
		String userName = "";
		String userMail = "";
		String userPass = "";
		try{
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null){
			String getApproverID="select * from users where username='admin'";
			
			ResultSet approverRS=ad.selectQuery(getApproverID);
			while(approverRS.next()){
				
				userName = approverRS.getString("username");
				userMail = approverRS.getString("mail_id");
				userPass = approverRS.getString("password");
					
			}
		}
		else{
			userName = user.getUserName();
			userMail = user.getFullName();
			userMail=userMail.trim();
			userMail=userMail.replaceAll("\\s+","");
			userPass = user.getPassword();
		}
		userDetails.add(0,userName);
		userDetails.add(1,userMail);
		userDetails.add(2,userPass);
		}
		catch (SQLException sqle) { System.out.println("SQLException @ sending User Details"); sqle.printStackTrace();}
		return userDetails;
	}

	public ActionForward searchInMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		MailInboxForm mailSearchForm=(MailInboxForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		String sTxt = request.getParameter("searchText");
		String openFolder = request.getParameter("folder");
		mailSearchForm.setSearchText(sTxt);
		LinkedList searchList=new LinkedList();
		try{
			int  startMailCount=0;
			int  endMailCount=0;
			String scount = request.getParameter("sCount");
			if(scount == null){
				mailSearchForm.setMailMessage("Mail Sent Successfully!");
			}
			if(scount != null){
				startMailCount=Integer.parseInt(scount);
				endMailCount=Integer.parseInt(request.getParameter("eCount"));
			}
			basicNavigation(request);
			int rowCount=0;
			String searchQueryCount="select count(*) from Mail_"+openFolder+" where emp_id ='"+USER_ID+"' and (ToAdd like '%"+sTxt+"%' or Subject like '%"+sTxt+"%' or Date like '%"+sTxt+"%')";
			if(openFolder.equalsIgnoreCase("Inbox")){
				searchQueryCount="select count(*) from Mail_"+openFolder+" where emp_id ='"+USER_ID+"' and (FromAdd like '%"+sTxt+"%' or Subject like '%"+sTxt+"%' or Date like '%"+sTxt+"%')";
			}
			
			ResultSet countrs=ad.selectQuery(searchQueryCount);
			while(countrs.next()) {
					rowCount=Integer.parseInt(countrs.getString(1));
			}
			int searchTotal = rowCount;
			if(startMailCount == 0){
				if(searchTotal == 0){
					mailSearchForm.setMessage("No Result for Given Text");
					request.setAttribute("noRecords","noRecords");
				}
				else if(searchTotal <= MAIL_PER_PAGE)
				{
					startMailCount=1;
					endMailCount=searchTotal;
				}
				else
				{
					startMailCount=1;
					endMailCount=MAIL_PER_PAGE;
					enableNextNavigation(request);
				}
				/*InList = getLimitedItems(request,tablename,startMailCount,endMailCount);
				setAllCount(mailInForm,InboxTotal,startMailCount,endMailCount);*/
			}
			else{
				String naviType = request.getParameter("fnpl");
				if(naviType.equalsIgnoreCase("next")){
					startMailCount=(startMailCount+MAIL_PER_PAGE);
					endMailCount=(endMailCount+MAIL_PER_PAGE);
					enablePreviousNavigation(request);
					if(searchTotal <= endMailCount)
					{
						disableNextNavigation(request);
						endMailCount = searchTotal;
					}
					else
					{
						enableNextNavigation(request);
					}
				}
				else if(naviType.equalsIgnoreCase("priv"))
				{
					startMailCount=(startMailCount-MAIL_PER_PAGE);
					int ecount=0;
					if(endMailCount == searchTotal)
					{
						int ec = endMailCount/MAIL_PER_PAGE;
						ecount = ec;
						endMailCount=(ecount*MAIL_PER_PAGE);
					}
					else
						endMailCount=(endMailCount-MAIL_PER_PAGE);
					if(endMailCount == MAIL_PER_PAGE)
					{
						disablePreviousNavigation(request);
						enableNextNavigation(request);
					}
					else{
						enablePreviousNavigation(request);
						enableNextNavigation(request);
					}
				}
				else if(naviType.equalsIgnoreCase("alast")){
					int ecount=0;
					endMailCount = searchTotal;
					int typeStart = endMailCount%MAIL_PER_PAGE;
					System.out.println("mod "+typeStart);
					if(typeStart == 0){
						int ec = endMailCount-MAIL_PER_PAGE;
						ecount = ec;
						startMailCount=ecount+1;
					}
					else{
						int ec = endMailCount/MAIL_PER_PAGE;
						ecount = ec;
						startMailCount=(ecount*MAIL_PER_PAGE)+1;
					}
					enablePreviousNavigation(request);
					disableNextNavigation(request);
				}
				else{
					startMailCount=1;
					endMailCount = MAIL_PER_PAGE;
					disablePreviousNavigation(request);
					enableNextNavigation(request);
				}
				
				
				/*InList = getLimitedItems(request,tablename,startMailCount,endMailCount);
				setAllCount(mailInForm,InboxTotal,startMailCount,endMailCount);*/
			}
			setAllCount(mailSearchForm,searchTotal,startMailCount,endMailCount);
			String searchQuery="select * from Mail_"+openFolder+" where emp_id ='"+USER_ID+"' and (ToAdd like '%"+sTxt+"%' or Subject like '%"+sTxt+"%' or Date like '%"+sTxt+"%' or Body like '%"+sTxt+"%') order by Date DESC";
			if(openFolder.equalsIgnoreCase("Inbox")){
				searchQuery="select * from Mail_"+openFolder+" where emp_id ='"+USER_ID+"' and (FromAdd like '%"+sTxt+"%' or Subject like '%"+sTxt+"%' or Date like '%"+sTxt+"%' or Body like '%"+sTxt+"%') order by Date DESC";
			}
			ResultSet srchRS=ad.selectQuery(searchQuery);
			int count = 0;
			String emid= "";
			Date today = new Date();
			while(srchRS.next()) {
				int startRow = srchRS.getRow();
				if(startRow >= startMailCount)
				{
					MailInboxForm mailSForm= new MailInboxForm();
					String MID = srchRS.getString("mail_id");
					String FM = srchRS.getString("FromAdd");
		            if(FM.indexOf("<") >= 0)
		            {
		            	FM = FM.substring(0, FM.indexOf("<"));
		            }
					String TO = srchRS.getString("ToAdd");
					if(TO.indexOf(",") >= 0)
		            {
		            	TO = TO.substring(0, TO.indexOf(","));
		            }
					String CC = srchRS.getString("CcAdd");
					String SB = srchRS.getString("Subject");
					String BD = srchRS.getString("Body");
					BD = RemoveTag(BD);
					int att = srchRS.getInt("isAttach");
					if(att == 0)
					{
						//request.setAttribute("attach", "");
						mailSForm.setAttach("");
					}
					else{
						//request.setAttribute("attach", "show");
						mailSForm.setAttach("attach");
					}
					int isread = srchRS.getInt("isread");
					if(isread == 0)
					{
						mailSForm.setRead("unreadTR");
					}
					else{
						mailSForm.setRead("readTR");
					}
					Date mailDT = srchRS.getDate("Date");
					mailSForm.setFromInbox(FM);
					mailSForm.setMailId(MID);
					mailSForm.setToAddress(TO);
					mailSForm.setccAddress(CC);
					mailSForm.setSubject(SB);
					mailSForm.setDescription(BD);
					mailSForm.setDateTime(mailDT.toString());
					searchList.add(mailSForm);
					count++;
					if(count == MAIL_PER_PAGE)
					{
						break;
					}
				}
			}
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		request.setAttribute("SentDetails", searchList);
		request.setAttribute("InboxDetails", searchList);
		String returnMsg="displayMailInbox";
		if(openFolder.equalsIgnoreCase("SentItem")){
			returnMsg="displaySentItems";
		}
		else if(openFolder.equalsIgnoreCase("Draft")){
			returnMsg="displayMailDraft";
		}
		else if(openFolder.equalsIgnoreCase("Trash")){
			returnMsg="displayDeleted";
		}
		else
		{
			returnMsg="displayMailInbox";
		}
		mailSearchForm.setHeading("Search result for "+sTxt+" in "+openFolder);
		return mapping.findForward(returnMsg);
	}

	public ActionForward searchGivenUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		MailInboxForm mailSearchForm=(MailInboxForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		String sTxt = request.getParameter("searchText");
		mailSearchForm.setSearchText(sTxt);
		LinkedList searchList=new LinkedList();
		try{
			int  startMailCount=0;
			int  endMailCount=0;
			String scount = request.getParameter("sCount");
			if(scount == null){
				mailSearchForm.setMailMessage("Mail Sent Successfully!");
			}
			if(scount != null){
				startMailCount=Integer.parseInt(scount);
				endMailCount=Integer.parseInt(request.getParameter("eCount"));
			}
			basicNavigation(request);
			int rowCount=0;
			String searchQuery="select * from emp_master where emp_name like '%"+sTxt+"%' or emp_email like '%"+sTxt+"%'";
			ResultSet srchRS=ad.selectQuery(searchQuery);
			int count = 0;
			String empmail= "";
			Date today = new Date();
			while(srchRS.next()) {
				MailInboxForm mForm = new MailInboxForm();
				empmail = srchRS.getString("emp_name") +"<"+srchRS.getString("emp_email")+">";
				mForm.setSearchAddress(empmail);
				mForm.setToAddress(srchRS.getString("emp_email"));
				searchList.add(mForm);
				count++;
				if(count == MAIL_PER_PAGE)
				{
					break;
				}
			}
		}
		catch (SQLException sqle) { System.out.println("SQLException @ searching User Details"); sqle.printStackTrace();}
		//displayComposeMail(mapping, form, request, response);
		request.setAttribute("SearchUserDetails", searchList);
		return mapping.findForward("searchUser");
	}
	
	public ActionForward getFileListForDraft(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		LinkedList attFileList=new LinkedList();
		attFileList = getAttachmentFiles(request,"SentItem");
		request.setAttribute("listName", attFileList);
		return mapping.findForward("ajaxFileUpload");
	}
	
	public ActionForward getMailForUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		MailInboxForm mailForm=(MailInboxForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		String returnmsg="displayMailHome";
		try{
			String pwd = mailForm.getPassword();
			String pwdAuth= authenticateUP(user.getMail_id(),pwd);
			InputStream stream = IOUtils.toInputStream(pwd);
			if(pwdAuth.equalsIgnoreCase("Success")){
				LinkedList paramList = new LinkedList();
				PreparedStatement pstmt;
		        Connection conn=ConnectionFactory.getConnection();
		        String query = "update emp_master set emp_mail_pwd=? where emp_id=?";
		        pstmt = conn.prepareStatement(query);
		        pstmt.setBinaryStream(1, stream);
		        pstmt.setString(2, user.getUserName());
		        int a = pstmt.executeUpdate();
		       
			/*String insertsql = "update emp_master set emp_mail_pwd="+pwd+" where emp_id='"+user.getEmployeeNo()+"'";
			int a = ad.SqlExecuteUpdate(insertsql);*/
			if (a > 0)
			{
				displayInboxMail(mapping, form, request, response);	
				returnmsg="displayMailInbox";
			}else {
				mailForm.setMessage("Error While getting mail");
				displayMailHome(mapping, form, request, response);
				returnmsg="displayMailHome";
			}
			}
			else{
				mailForm.setMessage("Your mail password is incorrect!");
				displayMailHome(mapping, form, request, response);
				returnmsg="displayMailHome";
			}
		}
		catch (SQLException sqle) { System.out.println("SQLException @ getting mail"); sqle.printStackTrace();}
		return mapping.findForward(returnmsg);
	}
	
	private String authenticateUP(String uName, String pwd) {
		String statusmessage = "Success";
		String HOST_NAME = "mail.microlabs.co.in";
    	String protocol ="pop3";
    	Properties props = new Properties();
		props.put("mail.pop3.host",HOST_NAME);
		props.put("mail.store.protocol", protocol);
		Session session = Session.getInstance(props);
		try{
		Store store = null;
		Folder inbox = null;
		store = session.getStore(protocol);
		store.connect(HOST_NAME, uName, pwd);
		store.close();
		}catch (MessagingException e) { 
			System.out.println("exception @ get Inbox mail"); 
			String errormsg = e.getMessage();
			if(errormsg.contains("unable to log")){
				statusmessage = "Your mail password is incorrect!";
			}
		} 
		return statusmessage;
	}
	
}
