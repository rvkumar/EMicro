package com.microlabs.admin.dao;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.struts.actions.DispatchAction;

import com.microlabs.main.form.MailInboxForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.UserInfo;

public class AdminMail extends DispatchAction {
	private String password;

	public int sendMailToUsers(HttpServletRequest request, String headlinestype,String subject,
			String Loc, String cat, String dept) {

		HttpSession session = request.getSession();
		MailInboxForm mailForm = new MailInboxForm();
		AdminMail am = new AdminMail();
		UserInfo user = (UserInfo) session.getAttribute("user");
		NewsandMediaDao ad = new NewsandMediaDao();
		String email = "select  distinct(EMAIL_ID) from emp_official_info where DPTID  in ('"
				+ dept
				+ "') and LOCID in ('"
				+ Loc
				+ "') and STAFFCAT in ('"
				+ cat + "') and EMAIL_ID!=''";
		ResultSet a = ad.selectQuery(email);
		String MAilID = "";
		try {
			while (a.next()) {
				MAilID = MAilID + "," + a.getString("EMAIL_ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MAilID = MAilID.substring(1, MAilID.length());
		mailForm.setSendermail("emicro.microlabs.in");
		mailForm.setToAddress(MAilID);
		mailForm.setccAddress("");
		mailForm.setbccAddress("");
		mailForm.setSubject(headlinestype+" "+"Updates");
		String desc = "";
		desc = desc + "";
		desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
				+ "TABLE {border-collapse:collapse;border:1px solid black;}"
				+ "TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
				+ "TD {padding:5px;border:1px solid black;font-size:9pt;}"
				+ "</STYLE>";
		desc = desc
				+ "<html><body><img src="
				+ "https://portal.microlabs.co.in/images/logo.png"
				+ " height=60 width=50  /><br><br> <table><tr><th colspan=2 ><center>"+headlinestype+"</center></th></tr><tr><th>Subject</th><td>"+subject+"</td></tr></table><br><br>";
		desc = desc
				+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>Note: For more information log on to portal. </span><br> ";
		desc = desc
				+ " <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="
				+ "https://portal.microlabs.co.in/"
				+ ">EMicro Portal</a> .</span> ";
		desc = desc + "</body></html>";
		mailForm.setDescription(desc);

		try {
			am.sendMail(request, mailForm, "request");
		} catch (IOException | ServletException | SQLException
				| MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 1;
	}

	public String sendMail(HttpServletRequest request,
			MailInboxForm mailSentForm, String mailfrom) throws IOException,
			ServletException, SQLException, MessagingException {

		String HOST_NAME = "mail.microlabs.co.in";
		final String DEFAULT_ID = "emicro@microlabs.in";
		String protocol = "smtp";
		password = "micro1@";
		String sucMessage = "success";
		String to = mailSentForm.getToAddress();
		String cc = mailSentForm.getccAddress();
		String localhost = (InetAddress.getLocalHost()).getCanonicalHostName();
		System.out.println("local --> " + localhost);
		String bcc = mailSentForm.getbccAddress();
		String port = "25";// request.getServerPort();

		Properties props = new Properties();
		props.put("mail.smtp.host", HOST_NAME);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.socketFactory.port", port);

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(DEFAULT_ID, password);// employee
																				// company
																				// email
																				// address
																				// and
																				// password
					}
				});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(DEFAULT_ID));// employee company
																// email address
			message.addRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.addRecipients(Message.RecipientType.CC,
					InternetAddress.parse(cc));
			message.addRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(bcc));
			message.setSubject(mailSentForm.getSubject());

			// Description
			Multipart mp = new MimeMultipart();
			MimeBodyPart descripPart = new MimeBodyPart();
			System.out.println(mailSentForm.getDescription());
			descripPart.setContent(mailSentForm.getDescription(), "text/html");
			mp.addBodyPart(descripPart);

			// For Attachment
			if (mailfrom.equalsIgnoreCase("mail")) {
			}

			message.setContent(mp);
			session.setDebug(true);
			// Service sr = new Service(session,)
			// send message
			Transport transport = session.getTransport(protocol);

			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			// Transport.send(message);

		} catch (NoSuchProviderException e1) {
			sucMessage = "Mail not Sent";
			System.out.println("Exception @ mail sending");
			e1.printStackTrace();
		} catch (MessagingException e) {
			sucMessage = "Mail not Sent";
			System.out.println("Exception @ mail sending");
			if (mailfrom.equalsIgnoreCase("mail")) {
			}
			e.printStackTrace();
		} catch (NullPointerException npe) {
			sucMessage = "Mail not Sent";
			System.out.println("Exception @ mail sending");
			if (mailfrom.equalsIgnoreCase("mail")) {
			}
			npe.printStackTrace();
		}
		/*
		 * finally { deleteUploadFiles(uploadedFiles); }
		 */
		return sucMessage;

	}
}
