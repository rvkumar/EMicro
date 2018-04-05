package com.microlabs.ess.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
/*import org.springframework.web.multipart.MultipartFile;*/
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.microlabs.admin.form.ESSApproverForm;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.TravelRequestForm;
import com.microlabs.main.db.MainDao;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class TravelRequestAction extends DispatchAction {
	
	//Created by Arun .Date:15-11-2016
	
	EssDao ad = EssDao.dBConnection();
	static Logger log = Logger.getLogger(TravelRequestAction.class);

	public ActionForward deleteDocuments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
		TravelRequestForm ondutyForm = (TravelRequestForm) form;
		HttpSession session = request.getSession();


		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String documentId = "";
		String documentName = "";
		int documentLength = 0;
		int document = 0;
		int reqNo = ondutyForm.getRequestNumber();
		String requestType = request.getParameter("reqType");
		String[] documentCheck = ondutyForm.getDocumentCheck();
		String filePath = "";
		
		
		
	///delete temp travellers 
		
		String g="delete Travel_Desk_Travellers_temp where reqNo='"+reqNo+"'";
		int y=ad.SqlExecuteUpdate(g);
		
		ArrayList emplist=new ArrayList();
		ArrayList guestlist=new ArrayList();
		///emps
    	String mulemps[]=request.getParameterValues("mulemps");
    	String passnos[]=request.getParameterValues("passnos");
    	String passplac[]=request.getParameterValues("passplac");
    	String passissue[]=request.getParameterValues("passissue");
    	String passexpiry[]=request.getParameterValues("passexpiry");
    	
//guests
    	String nameguests[]=request.getParameterValues("nameguests");
    	String ageguests[]=request.getParameterValues("ageguests");
    	String genderguests[]=request.getParameterValues("genderguests");
    	String contactguests[]=request.getParameterValues("contactguests");
    	String emailguests[]=request.getParameterValues("emailguests");
    	String passnosguest[]=request.getParameterValues("passnosguest");
    	String passplacguest[]=request.getParameterValues("passplacguest");
    	String passissueguest[]=request.getParameterValues("passissueguest");
    	String passexpiryguest[]=request.getParameterValues("passexpiryguest");
    	
    	if(mulemps!=null)
    	{	
		    for(int i=0;i<mulemps.length;i++)
		    {
		    
				
					
						
						String mulpassissue="";
						String mulpassexpiry="";
						
						if(requestType.equalsIgnoreCase("international"))
					    {
					
					    String pasi[]=passissue[i].split("/");
					    mulpassissue=pasi[2]+"-"+pasi[1]+"-"+pasi[0];
					    
					    String pase[]=passexpiry[i].split("/");
					    mulpassexpiry=pase[2]+"-"+pase[1]+"-"+pase[0];
					    }
					    String b="insert into Travel_Desk_Travellers_temp (reqNo,PERNR,Req_type,Passport_no,Place_issue,Date_of_issue,Date_of_expiry) values('"+reqNo+"','"+mulemps[i]+"','Employee','"+passnos[i]+"','"+passplac[i]+"','"+mulpassissue+"','"+mulpassexpiry+"')";
				    	int t7=ad.SqlExecuteUpdate(b);
					    
				    
		    	
		    }
    	}
    	if(nameguests!=null)
    	{	
		    for(int i=0;i<nameguests.length;i++)
		    {
		    		
						String guestpassissue="";
						String guestpassexpiry="";
						
						if(requestType.equalsIgnoreCase("international"))
					    {
					
					    String pasi[]=passissueguest[i].split("/");
					    guestpassissue=pasi[2]+"-"+pasi[1]+"-"+pasi[0];
					    
					    String pase[]=passexpiryguest[i].split("/");
					    guestpassexpiry=pase[2]+"-"+pase[1]+"-"+pase[0];
					    }
					    String b="insert into Travel_Desk_Travellers_temp (reqNo,Req_type,Name_of_guest,Contact_guest,Age_guest,Gender_guest,Email_guest,Passport_no,Place_issue,Date_of_issue,Date_of_expiry) "
					    + "values('"+reqNo+"','Guest','"+nameguests[i]+"','"+contactguests[i]+"','"+ageguests[i]+"','"+genderguests[i]+"','"+emailguests[i]+"','"+passnosguest[i]+"','"+passplacguest[i]+"','"+guestpassissue+"','"+guestpassexpiry+"')";
				    	int t7=ad.SqlExecuteUpdate(b);
					    
				    
		    	
		    }
    	}
		
    	
    	
    	//traveller details
        String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1,* from Travel_Desk_Travellers_temp where reqNo='"+reqNo+"'" ;
        ResultSet b=ad.selectQuery(his);
        try {
			while(b.next())
			{
				if(b.getString("req_type").equalsIgnoreCase("Employee"))
				{
					TravelRequestForm emp=new TravelRequestForm();
					String ere[]=empDet(Integer.toString(b.getInt("pernr")));
					emp.setEmployeeno(Integer.toString(b.getInt("pernr")));
					emp.setEmployeeName(ere[0]);
					emp.setLocationId(ere[1]);
					emp.setDepartment(ere[2]);
					emp.setDesignation(ere[3]); 
					emp.setUserAge(ere[5]);
				
					emp.setUserGender(ere[7]);                		
					
					emp.setUsermailId(b.getString("Email_guest"));
					emp.setUsermobno(b.getString("Contact_guest"));
					
					emp.setUserpassportno(b.getString("Passport_no"));
					emp.setUserpassportplace(b.getString("Place_issue"));
					
					if(!b.getString("Date_of_issue1").contains("1900"))
					emp.setUserpassportissuedate(b.getString("Date_of_issue1")); 
					else
					emp.setUserpassportissuedate("");
					
					if(!b.getString("Date_of_expiry1").contains("1900"))
					emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
					else
			    	emp.setUserpassportexpirydate("");
					
					emplist.add(emp);
				}

				if(b.getString("req_type").equalsIgnoreCase("Guest"))
				{
					TravelRequestForm gues=new TravelRequestForm();
					gues.setGuestName(b.getString("Name_of_guest"));
					gues.setGuestAge(b.getString("Age_guest"));
					gues.setGuestContactNo(b.getString("Contact_guest"));
					gues.setGender(b.getString("Gender_guest"));
					gues.setGuestmailId(b.getString("Email_guest"));
					
					gues.setUserpassportno(b.getString("Passport_no"));
					gues.setUserpassportplace(b.getString("Place_issue"));
					if(!b.getString("Date_of_issue1").contains("1900"))
						gues.setUserpassportissuedate(b.getString("Date_of_issue1")); 
			    		else
			    			gues.setUserpassportissuedate("");
			    		
			    		if(!b.getString("Date_of_expiry1").contains("1900"))
			    			gues.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
			    		else
			    			gues.setUserpassportexpirydate("");
					guestlist.add(gues);
				}
				
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        if(emplist.size()>0)
        	request.setAttribute("emplist", emplist);
        if(guestlist.size()>0)
        	request.setAttribute("guestlist", guestlist);
		
		
		try {
			documentLength = documentCheck.length;
			if (documentLength > 0) {
				for (int i = 0; i < documentLength; i++) {
					document++;
					documentId = documentCheck[i];
					String fileName = "";
					String getFilename = "select * from Travel_documents where id="
							+ documentId + " and request_no='" + reqNo + "'";
					ResultSet rsFileName = ad.selectQuery(getFilename);
					while (rsFileName.next()) {
						fileName = rsFileName.getString("file_name");
					}

					String sql = "delete from Travel_documents  where id="
							+ documentId + " and request_no='" + reqNo + "'";

					int c = ad.SqlExecuteUpdate(sql);
					if (c > 0) {
						ondutyForm
								.setMessage("Document Details Deleted sucessfully");
						ondutyForm.setMessage2("");
						InputStream in = ConnectionFactory.class
								.getClassLoader().getResourceAsStream(
										"db.properties");
						Properties props = new Properties();
						props.load(in);
						in.close();
						String uploadFilePath = props
								.getProperty("file.uploadFilePath");
						System.out
								.println("required filepath="
										+ uploadFilePath
										+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+requestType+"");
						filePath = uploadFilePath
								+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+requestType+"";

						File fileToCreate = new File(filePath, fileName);
						boolean test = fileToCreate.delete();
						System.out.println(test);

						// delete file in another path

						File fileToCreate1 = new File(
								"E:/EMicro Files/ESS/Travel Request/UploadFiles/"+requestType+"",
								fileName);
						boolean test1 = fileToCreate1.delete();
					}

				}
			}

			String sql1 = "select * from Travel_documents where request_no='"
					+ ondutyForm.getRequestNumber()
					+ "' and user_id='"
					+ user.getEmployeeNo() + "' ";
			ResultSet rs = ad.selectQuery(sql1);
			TravelRequestForm leaveForm1 = null;
			ArrayList a1 = new ArrayList();

			while (rs.next()) {
				leaveForm1 = new TravelRequestForm();
				leaveForm1.setFileName(rs.getString("file_name"));
				leaveForm1.setId(rs.getInt("id"));
				leaveForm1.setFileFullPath(filePath + "/"
						+ rs.getString("file_name"));
				a1.add(leaveForm1);
			}
			request.setAttribute("documentDetails", a1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return mapping.findForward("domesticformrequest");
	}

	
	public ActionForward deleteConfirmDocuments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
		{
		
		//Created by Arun .Date:15-11-2016
		
		TravelRequestForm ondutyForm = (TravelRequestForm) form;
		HttpSession session = request.getSession();

		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String documentId = "";
		String documentName = "";
		int documentLength = 0;
		int document = 0;
		int reqNo = ondutyForm.getRequestNumber();
		String requestType = request.getParameter("reqType");
		String[] documentCheck = ondutyForm.getDocumentCheck();
		String filePath = "";
		try {
			documentLength = documentCheck.length;
			if (documentLength > 0) {
				for (int i = 0; i < documentLength; i++) {
					document++;
					documentId = documentCheck[i];
					String fileName = "";
					String getFilename = "select * from Travel_confirm_documents where id="
							+ documentId + " and request_no='" + reqNo + "'";
					ResultSet rsFileName = ad.selectQuery(getFilename);
					while (rsFileName.next()) {
						fileName = rsFileName.getString("file_name");
					}

					String sql = "delete from Travel_confirm_documents  where id="
							+ documentId + " and request_no='" + reqNo + "'";

					int c = ad.SqlExecuteUpdate(sql);
					if (c > 0) {
						ondutyForm
								.setMessage("Document Details Deleted sucessfully");
						ondutyForm.setMessage2("");
						InputStream in = ConnectionFactory.class
								.getClassLoader().getResourceAsStream(
										"db.properties");
						Properties props = new Properties();
						props.load(in);
						in.close();
						String uploadFilePath = props
								.getProperty("file.uploadFilePath");
						System.out.println("required filepath="
										+ uploadFilePath
										+ "/EMicro Files/ESS/Travel Request/ConfirmFiles/"+requestType+"");
						filePath = uploadFilePath
								+ "/EMicro Files/ESS/Travel Request/ConfirmFiles/"+requestType+"";

						File fileToCreate = new File(filePath, fileName);
						boolean test = fileToCreate.delete();
						System.out.println(test);

						// delete file in another path

						File fileToCreate1 = new File(
								"E:/EMicro Files/ESS/Travel Request/ConfirmFiles/"+requestType+"",
								fileName);
						boolean test1 = fileToCreate1.delete();
					}

				}
			}

			String sql1 = "select * from Travel_confirm_documents where request_no='"
					+ ondutyForm.getRequestNumber()
					+ "' and user_id='"
					+ user.getEmployeeNo() + "' ";
			ResultSet rs = ad.selectQuery(sql1);
			TravelRequestForm leaveForm1 = null;
			ArrayList a1 = new ArrayList();

			while (rs.next()) {
				leaveForm1 = new TravelRequestForm();
				leaveForm1.setFileName(rs.getString("file_name"));
				leaveForm1.setId(rs.getInt("id"));
				leaveForm1.setFileFullPath(filePath + "/"
						+ rs.getString("file_name"));
				a1.add(leaveForm1);
			}
			request.setAttribute("confirmDetails", a1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		
		ViewConfirmationrequest(mapping, ondutyForm, request, response);
		return mapping.findForward("viewconfirmrequest");
	}
	
	
	public ActionForward deleteInternationDocuments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
		
		TravelRequestForm ondutyForm = (TravelRequestForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String documentId = "";
		String documentName = "";
		int documentLength = 0;
		int document = 0;
		int reqNo = ondutyForm.getRequestNumber();
	
		String[] documentCheck = ondutyForm.getDocumentCheck();
		String filePath = "";
		try {
			documentLength = documentCheck.length;
			if (documentLength > 0) {
				for (int i = 0; i < documentLength; i++) {
					document++;
					documentId = documentCheck[i];
					String fileName = "";
					String getFilename = "select * from international_documents where id="
							+ documentId + " and request_no='" + reqNo + "'";
					ResultSet rsFileName = ad.selectQuery(getFilename);
					while (rsFileName.next()) {
						fileName = rsFileName.getString("file_name");
					}

					String sql = "delete from international_documents  where id="
							+ documentId + " and request_no='" + reqNo + "'";

					int c = ad.SqlExecuteUpdate(sql);
					if (c > 0) {
						ondutyForm
								.setMessage("Document Details Deleted sucessfully");
						ondutyForm.setMessage2("");
						InputStream in = ConnectionFactory.class
								.getClassLoader().getResourceAsStream(
										"db.properties");
						Properties props = new Properties();
						props.load(in);
						in.close();
						String uploadFilePath = props
								.getProperty("file.uploadFilePath");
						System.out
								.println("required filepath="
										+ uploadFilePath
										+ "/EMicro Files/ESS/Travel Request/UploadFiles/International");
						filePath = uploadFilePath
								+ "/EMicro Files/ESS/Travel Request/UploadFiles/International";

						File fileToCreate = new File(filePath, fileName);
						boolean test = fileToCreate.delete();
						System.out.println(test);

						// delete file in another path

						File fileToCreate1 = new File(
								"E:/EMicro Files/ESS/Travel Request/UploadFiles/International",
								fileName);
						boolean test1 = fileToCreate1.delete();
					}

				}
			}

			String sql1 = "select * from international_documents where request_no='"
					+ ondutyForm.getRequestNumber()
					+ "' and user_id='"
					+ user.getEmployeeNo() + "' ";
			ResultSet rs = ad.selectQuery(sql1);
			TravelRequestForm leaveForm1 = null;
			ArrayList a1 = new ArrayList();

			while (rs.next()) {
				leaveForm1 = new TravelRequestForm();
				leaveForm1.setFileName(rs.getString("file_name"));
				leaveForm1.setId(rs.getInt("id"));
				leaveForm1.setFileFullPath(filePath + "/"
						+ rs.getString("file_name"));
				a1.add(leaveForm1);
			}
			request.setAttribute("documentDetails", a1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return mapping.findForward("internationalformrequest");
	}
	

	public ActionForward uploadInternationalDocuments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
		TravelRequestForm ondutyForm = (TravelRequestForm) form;

		FormFile documentFile = ondutyForm.getDocumentFile();
		String documentName = documentFile.getFileName();
		int reqNo = ondutyForm.getRequestNumber();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String filePath = "";

		String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
		int filesize = documentFile.getFileSize();

		if ((ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")
				|| ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("jpg")
				|| ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("xls") || ext
					.equalsIgnoreCase("txt")) && (filesize < 1048576)) {
			try {
				byte[] size = documentFile.getFileData();
				if (!documentName.equalsIgnoreCase("")) {
					int length = documentName.length();
					int dot = documentName.lastIndexOf(".");
					String fileName = documentFile.getFileName();
					String extension = documentName.substring(dot, length);
					filePath = getServlet().getServletContext().getRealPath(
							"jsp/EMicro Files/ESS/Travel Request/UploadFiles");
					InputStream in = ConnectionFactory.class.getClassLoader()
							.getResourceAsStream("db.properties");
					Properties props = new Properties();
					props.load(in);
					in.close();
					String uploadFilePath = props
							.getProperty("file.uploadFilePath");
					System.out.println("required filepath=" + uploadFilePath
							+ "/EMicro Files/ESS/Travel Request/UploadFiles/International");
					filePath = uploadFilePath
							+ "/EMicro Files/ESS/Travel Request/UploadFiles/International";
					File destinationDir = new File(filePath);
					if (!destinationDir.exists()) {
						destinationDir.mkdirs();
					}
					if (!fileName.equals("")) {
						File fileToCreate = new File(filePath, fileName);
						if (!fileToCreate.exists()) {
							FileOutputStream fileOutStream = new FileOutputStream(
									fileToCreate);
							fileOutStream.write(documentFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
						}
					}
					try {
						String filePath1 = "E:/EMicro Files/ESS/Travel Request/UploadFiles/International";
						byte[] fileData1 = documentFile.getFileData();
						InputStream is = new ByteArrayInputStream(fileData1);
						// boolean status=saveDataInJRS(is,fileName);
						// System.out.println("File status="+status);

						File destinationDir1 = new File(filePath1);
						if (!destinationDir1.exists()) {
							destinationDir1.mkdirs();
						}
						if (!fileName.equals("")) {
							File fileToCreate1 = new File(filePath1, fileName);
							if (!fileToCreate1.exists()) {
								FileOutputStream fileOutStream1 = new FileOutputStream(
										fileToCreate1);
								fileOutStream1
										.write(documentFile.getFileData());
								fileOutStream1.flush();
								fileOutStream1.close();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException fe) {
				fe.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}

			String contentType = documentFile.getContentType();
			String fileName = documentFile.getFileName();
			try {

				String sql9 = "select count(*) from International_documents  where  file_name='"
						+ documentFile.getFileName() + "'";
				ResultSet rs15 = ad.selectQuery(sql9);
				int fileCount = 0;
				while (rs15.next()) {
					fileCount = Integer.parseInt(rs15.getString(1));
				}
				if (fileCount > 0) {
					ondutyForm
							.setMessage("Document aleardy uploaded..please choose another file");
					ondutyForm.setMessage2("");
				} else {
					String sql = "insert into International_documents(request_no,user_id,file_name)"
							+ "values('"
							+ reqNo
							+ "','"
							+ user.getEmployeeNo()
							+ "','" + documentFile.getFileName() + "')";
					int a = ad.SqlExecuteUpdate(sql);
					if (a > 0) {
						ondutyForm
								.setMessage("Documents Uploaded Successfully");
						ondutyForm.setMessage2("");
					}
				}
				String sql1 = "select * from International_documents where request_no='"
						+ reqNo
						+ "' and user_id='"
						+ user.getEmployeeNo()
						+ "' ";
				ResultSet rs = ad.selectQuery(sql1);
				TravelRequestForm leaveForm1 = null;
				ArrayList a1 = new ArrayList();
				while (rs.next()) {
					leaveForm1 = new TravelRequestForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getInt("id"));
					leaveForm1.setFileFullPath(filePath + "/"
							+ rs.getString("file_name"));
					a1.add(leaveForm1);
				}
				request.setAttribute("documentDetails", a1);

			} catch (Exception e) {
				e.printStackTrace();

			}

		} else {
			ondutyForm
					.setMessage2("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
			ondutyForm.setMessage("");
			String sql1 = "select * from International_documents where request_no='"
					+ reqNo + "' and user_id='" + user.getEmployeeNo() + "' ";
			ResultSet rs = ad.selectQuery(sql1);
			TravelRequestForm leaveForm1 = null;
			ArrayList a1 = new ArrayList();
			try {
				while (rs.next()) {
					leaveForm1 = new TravelRequestForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getInt("id"));
					leaveForm1.setFileFullPath(filePath + "/"
							+ rs.getString("file_name"));
					a1.add(leaveForm1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("documentDetails", a1);

		}
		return mapping.findForward("internationalformrequest");
	}
	
	
	public ActionForward travellerList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		TravelRequestForm help = (TravelRequestForm) form;
		
		String kid=Integer.toString(help.getId());
		
		System.out.println(help.getId());
		
		if(kid==null)
			kid="";
		
		String his="select CONVERT(varchar(10),Travel_Desk_Travellers.Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),Travel_Desk_Travellers.Date_of_expiry,103)  as Date_of_expiry1,Travel_Desk_Travellers.* from "
				+ " Travel_Desk_Travellers"
				+ " where  req_id='"+kid+"' " ;
        ResultSet b=ad.selectQuery(his);
        ArrayList emplist = new ArrayList();
		try {
			while(b.next())
			{
				
					TravelRequestForm emp=new TravelRequestForm();
					String ere[]=empDet(Integer.toString(b.getInt("PERNR")));
					emp.setGuest_pernr(Integer.toString(b.getInt("PERNR")));
					emp.setEmployeeName(ere[0]);
					emp.setLocationId(ere[1]);
					emp.setDepartment(ere[2]);
					emp.setDesignation(ere[3]); 
					emp.setUserAge(ere[5]);
					emp.setUserGender(ere[7]);                		
					emp.setGuest_Type(b.getString("Req_type"));
					emp.setPassportno(b.getString("Passport_no"));
					emp.setPassportplace(b.getString("Place_issue"));
					emp.setPassportexpirydate(b.getString("Date_of_expiry"));
					emp.setGuestName(b.getString("Name_of_guest"));
					emp.setGuestContactNo(b.getString("Contact_guest"));
					emp.setGuest_Company(b.getString("guest_Company"));
					emp.setGuestAge(b.getString("Age_guest"));
					emp.setGender(b.getString("Gender_guest"));
					emp.setEmail_Guest(b.getString("Email_guest"));
					emp.setFileName(b.getString("file_name"));
					emp.setFileFullPath(b.getString("path"));
					emp.setGuest_Title(b.getString("guest_Title"));
					emp.setGuest_Visano(b.getString("guest_Visano"));
					emp.setGuest_DOB(b.getString("guest_DOB"));
					emp.setGuest_Meal(b.getString("guest_Meal"));
					emp.setId(b.getInt("id"));
					emp.setOn_duty(b.getString("on_duty"));
					
					if(!b.getString("Date_of_expiry1").contains("1900"))
					emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
					else
			    	emp.setUserpassportexpirydate("");
					
					emplist .add(emp);
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        if(emplist.size()>0)
        	request.setAttribute("emplist", emplist);
        else
		request.setAttribute("emplist", "");	
		return mapping.findForward("travellerList");
	}
	
	
	
	public ActionForward travellerListDelete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		TravelRequestForm help = (TravelRequestForm) form;
		String kid=request.getParameter("kid");
		String req_id="";
		String fileName = "";
		String getFilename = "select * from Travel_Desk_Travellers where id='"+kid+"' ";
		ResultSet rsFileName = ad.selectQuery(getFilename);
		try {
			if (rsFileName.next()) {
				fileName = rsFileName.getString("file_name");
				req_id = rsFileName.getString("req_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "delete from Travel_Desk_Travellers  where id="
				+ kid + " ";

		int c = ad.SqlExecuteUpdate(sql);
		if (c > 0) {
			help
			.setMessage("Record Details Deleted sucessfully");	
			
		if(!fileName.equalsIgnoreCase(""))
		{	
			help
					.setMessage("Document Details Deleted sucessfully");
			help.setMessage2("");
			InputStream in = ConnectionFactory.class
					.getClassLoader().getResourceAsStream(
							"db.properties");
			Properties props = new Properties();
			try {
				props.load(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String uploadFilePath = props
					.getProperty("file.uploadFilePath");
			System.out
					.println("required filepath="
							+ uploadFilePath
							+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+help.getTravelmode()+"");
			String filePath = uploadFilePath
					+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+help.getTravelmode()+"";

			File fileToCreate = new File(filePath, fileName);
			boolean test = fileToCreate.delete();
			System.out.println(test);

			// delete file in another path

			File fileToCreate1 = new File(
					"E:/EMicro Files/ESS/Travel Request/UploadFiles/"+help.getTravelmode()+"",
					fileName);
			boolean test1 = fileToCreate1.delete();
		}
		}
		
		
		String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1,* from Travel_Desk_Travellers where req_id='"+req_id+"'" ;
        ResultSet b=ad.selectQuery(his);
        ArrayList emplist = new ArrayList();
		try {
			while(b.next())
			{
				
					TravelRequestForm emp=new TravelRequestForm();
					String ere[]=empDet(Integer.toString(b.getInt("PERNR")));
					emp.setGuest_pernr(Integer.toString(b.getInt("PERNR")));
					emp.setEmployeeName(ere[0]);
					emp.setLocationId(ere[1]);
					emp.setDepartment(ere[2]);
					emp.setDesignation(ere[3]); 
					emp.setUserAge(ere[5]);
					emp.setUserGender(ere[7]);                		
					emp.setGuest_Type(b.getString("Req_type"));
					emp.setPassportno(b.getString("Passport_no"));
					emp.setPassportplace(b.getString("Place_issue"));
					emp.setPassportexpirydate(b.getString("Date_of_expiry"));
					emp.setGuestName(b.getString("Name_of_guest"));
					emp.setGuestContactNo(b.getString("Contact_guest"));
					emp.setGuest_Company(b.getString("guest_Company"));
					emp.setGuestAge(b.getString("Age_guest"));
					emp.setGender(b.getString("Gender_guest"));
					emp.setEmail_Guest(b.getString("Email_guest"));
					emp.setFileName(b.getString("file_name"));
					emp.setFileFullPath(b.getString("path"));
					emp.setGuest_Title(b.getString("guest_Title"));
					emp.setGuest_Visano(b.getString("guest_Visano"));
					emp.setGuest_DOB(b.getString("guest_DOB"));
					emp.setGuest_Meal(b.getString("guest_Meal"));
					emp.setId(b.getInt("id"));
					emp.setOn_duty(b.getString("on_duty"));
					
					if(!b.getString("Date_of_expiry1").contains("1900"))
					emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
					else
			    	emp.setUserpassportexpirydate("");
					
					emplist .add(emp);
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        if(emplist.size()>0)
        	request.setAttribute("emplist", emplist);
		
		
		return mapping.findForward("travellerList");
	}
	
	public ActionForward travellerListUpload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		//Created by ArunPrasad .Date:15-11-2016
		
		HttpSession session=request.getSession();
		TravelRequestForm help = (TravelRequestForm) form;
     
		FormFile documentFile = help.getDocumentFile();
		String documentName = documentFile.getFileName();
		int reqNo = help.getId();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		
		
		String departdate="";
		String returndate="";

		if(help.getDepartOn().contains("/"))
		{	
	    String depart[]=help.getDepartOn().split("/");
		departdate=depart[2]+"-"+depart[1]+"-"+depart[0];
		}

		if(help.getReturnOn().contains("/"))
		{
		String returnd[]=help.getReturnOn().split("/");
		returndate=returnd[2]+"-"+returnd[1]+"-"+returnd[0];
		}
		
		if(help.getGuest_Type()==null)
		{
			help.setGuest_Type("");
			
		}
		
		if(help.getPassportno()==null)
		{
			help.setPassportno("");
			
		}
		
		if(help.getPassportplace()==null)
		{
			help.setPassportplace("");
		}
		
		if(help.getPassportexpirydate()==null)
		{
			help.setPassportexpirydate("");
		}
		
		if(help.getGuestName()==null)
		{
			help.setPassportexpirydate("");
		}
		
		if(help.getGuestContactNo()==null)
		{
			help.setGuestContactNo("");
		}
		
		
		String passportexpirydate="";
		
		if(help.getPassportexpirydate()==null)
		{
			help.setPassportexpirydate("");
		}
		
		
		if(help.getPassportexpirydate().contains("/"))
		{
			String returnd[]=help.getPassportexpirydate().split("/");
			passportexpirydate=returnd[2]+"-"+returnd[1]+"-"+returnd[0];
			
		}
		
		
		if(!help.getEmail_Guest().equalsIgnoreCase(""))
		{
			
			if(help.getEmail_Guest().contains("@"))
			{
				String to[]=help.getEmail_Guest().split("@");
				String todomain=to[1];
				if(TravelRequestAction.getNS(todomain)==false)
				{
					help.setMessage("Invalid  Email Id");
					return displaytravellerList(mapping, form, request, response);
					
				}
			}
				else
				{
					help.setMessage("Invalid  Email Id");
					return displaytravellerList(mapping, form, request, response);
				}
				
			
			
		}
		

		int count=0;
		String sa1 = "select count(*) from Travel_Desk_Travellers where req_id='"+reqNo+"' and PERNR='"+help.getGuest_pernr()+"' and fromPlace='"+help.getFromPlace()+"' and toPlace='"+help.getToPlace()+"'";
		ResultSet rssa1=ad.selectQuery(sa1);
		while(rssa1.next())
		{
		count=rssa1.getInt(1);
		
		}
		
		if(count>0)
		{
			help.setMessage("Employee with same Origin and Destination cannot be added");
			
			String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),departOn,103)  as departOn,CONVERT(varchar(10),returnOn,103)  as returnOn,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1,* from Travel_Desk_Travellers where req_id='"+reqNo+"'" ;
	        ResultSet b=ad.selectQuery(his);
	        ArrayList emplist = new ArrayList();
			try {
				while(b.next())
				{
					
						TravelRequestForm emp=new TravelRequestForm();
						String ere[]=empDet(Integer.toString(b.getInt("PERNR")));
						emp.setGuest_pernr(Integer.toString(b.getInt("PERNR")));
						emp.setEmployeeName(ere[0]);
						emp.setLocationId(ere[1]);
						emp.setDepartment(ere[2]);
						emp.setDesignation(ere[3]); 
						emp.setUserAge(ere[5]);
						emp.setUserGender(ere[7]);                		
						emp.setGuest_Type(b.getString("Req_type"));
						emp.setPassportno(b.getString("Passport_no"));
						emp.setPassportplace(b.getString("Place_issue"));
						emp.setPassportexpirydate(b.getString("Date_of_expiry"));
						emp.setGuestName(b.getString("Name_of_guest"));
						emp.setGuestContactNo(b.getString("Contact_guest"));
						emp.setGuest_Company(b.getString("guest_Company"));
						emp.setGuestAge(b.getString("Age_guest"));
						emp.setGender(b.getString("Gender_guest"));
						emp.setEmail_Guest(b.getString("Email_guest"));
						emp.setFileName(b.getString("file_name"));
						emp.setFileFullPath(b.getString("path"));
						emp.setGuest_Title(b.getString("guest_Title"));
						emp.setGuest_Visano(b.getString("guest_Visano"));
						emp.setGuest_DOB(b.getString("guest_DOB"));
						emp.setGuest_Meal(b.getString("guest_Meal"));
						emp.setOn_duty(b.getString("on_duty"));
						emp.setId(b.getInt("id"));
						emp.setDepartOn(b.getString("departOn"));
						emp.setReturnOn(b.getString("returnOn"));
						emp.setFromPlace(b.getString("fromPlace"));
						emp.setToPlace(b.getString("toPlace"));
						
						if(!b.getString("Date_of_expiry1").contains("1900"))
						emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
						else
				    	emp.setUserpassportexpirydate("");
						
						emplist .add(emp);
					
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
				
	        if(emplist.size()>0)
	        	request.setAttribute("emplist", emplist);
			
			return mapping.findForward("travellerList");
		}
	
		
		if(!help.getGuest_pernr().equalsIgnoreCase(""))
		{
			if(help.getGuest_pernr().equalsIgnoreCase(user.getEmployeeNo()))
			{
				
				
				help.setMessage("Self User Cannot be added in Traveller list");
				//traveller details
		        String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1,* from Travel_Desk_Travellers where req_id='"+reqNo+"'" ;
		        ResultSet b=ad.selectQuery(his);
		        ArrayList emplist = new ArrayList();
				try {
					while(b.next())
					{
						
							TravelRequestForm emp=new TravelRequestForm();
							String ere[]=empDet(Integer.toString(b.getInt("PERNR")));
							emp.setGuest_pernr(Integer.toString(b.getInt("PERNR")));
							emp.setEmployeeName(ere[0]);
							emp.setLocationId(ere[1]);
							emp.setDepartment(ere[2]);
							emp.setDesignation(ere[3]); 
							emp.setUserAge(ere[5]);
							emp.setUserGender(ere[7]);                		
							emp.setGuest_Type(b.getString("Req_type"));
							emp.setPassportno(b.getString("Passport_no"));
							emp.setPassportplace(b.getString("Place_issue"));
							emp.setPassportexpirydate(b.getString("Date_of_expiry"));
							emp.setGuestName(b.getString("Name_of_guest"));
							emp.setGuestContactNo(b.getString("Contact_guest"));
							emp.setGuest_Company(b.getString("guest_Company"));
							emp.setGuestAge(b.getString("Age_guest"));
							emp.setGender(b.getString("Gender_guest"));
							emp.setEmail_Guest(b.getString("Email_guest"));
							emp.setFileName(b.getString("file_name"));
							emp.setFileFullPath(b.getString("path"));
							emp.setGuest_Title(b.getString("guest_Title"));
							emp.setGuest_Visano(b.getString("guest_Visano"));
							emp.setGuest_DOB(b.getString("guest_DOB"));
							emp.setGuest_Meal(b.getString("guest_Meal"));
							emp.setOn_duty(b.getString("on_duty"));
							emp.setId(b.getInt("id"));
							/*emp.setDepartOn(b.getString("departOn"));
							emp.setReturnOn(b.getString("returnOn"));*/
							
							
							if(!b.getString("Date_of_expiry1").contains("1900"))
							emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
							else
					    	emp.setUserpassportexpirydate("");
							
							emplist .add(emp);
						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		        if(emplist.size()>0)
		        	request.setAttribute("emplist", emplist);
				
				return mapping.findForward("travellerList");	
			}
			
			
			if(!help.getGuest_pernr().equalsIgnoreCase(""))
			{
				
					
				
						
				
				
				String sa="select * from travel_desk where reqno is not null  and pernr='"+help.getGuest_pernr()+"' "
						+ " and ((Depart_On >='"+departdate+"' and Depart_On <='"+returndate+"') or (Return_On >='"+departdate+"' and Return_On <='"+returndate+"'))   ";
				ResultSet rssa=ad.selectQuery(sa);
				try {
					if(rssa.next())
					{
						
						
						help.setMessage("Travel Request Already Applied for these Dates");
						
					
				 				
				
				//traveller details
		        String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),departOn,103)  as departOn,CONVERT(varchar(10),returnOn,103)  as returnOn,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1,* from Travel_Desk_Travellers where req_id='"+reqNo+"'" ;
		        ResultSet b=ad.selectQuery(his);
		        ArrayList emplist = new ArrayList();
				try {
					while(b.next())
					{
						
							TravelRequestForm emp=new TravelRequestForm();
							String ere[]=empDet(Integer.toString(b.getInt("PERNR")));
							emp.setGuest_pernr(Integer.toString(b.getInt("PERNR")));
							emp.setEmployeeName(ere[0]);
							emp.setLocationId(ere[1]);
							emp.setDepartment(ere[2]);
							emp.setDesignation(ere[3]); 
							emp.setUserAge(ere[5]);
							emp.setUserGender(ere[7]);                		
							emp.setGuest_Type(b.getString("Req_type"));
							emp.setPassportno(b.getString("Passport_no"));
							emp.setPassportplace(b.getString("Place_issue"));
							emp.setPassportexpirydate(b.getString("Date_of_expiry"));
							emp.setGuestName(b.getString("Name_of_guest"));
							emp.setGuestContactNo(b.getString("Contact_guest"));
							emp.setGuest_Company(b.getString("guest_Company"));
							emp.setGuestAge(b.getString("Age_guest"));
							emp.setGender(b.getString("Gender_guest"));
							emp.setEmail_Guest(b.getString("Email_guest"));
							emp.setFileName(b.getString("file_name"));
							emp.setFileFullPath(b.getString("path"));
							emp.setGuest_Title(b.getString("guest_Title"));
							emp.setGuest_Visano(b.getString("guest_Visano"));
							emp.setGuest_DOB(b.getString("guest_DOB"));
							emp.setGuest_Meal(b.getString("guest_Meal"));
							emp.setOn_duty(b.getString("on_duty"));
							emp.setId(b.getInt("id"));
							emp.setDepartOn(b.getString("departOn"));
							emp.setReturnOn(b.getString("returnOn"));
							emp.setFromPlace(b.getString("fromPlace"));
							emp.setToPlace(b.getString("toPlace"));
							
							
							if(!b.getString("Date_of_expiry1").contains("1900"))
							emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
							else
					    	emp.setUserpassportexpirydate("");
							
							emplist .add(emp);
						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
					
		        if(emplist.size()>0)
		        	request.setAttribute("emplist", emplist);
				
				return mapping.findForward("travellerList");	
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			
				
				}
			
		}
		
		String filePath = "";

		String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
		int filesize = documentFile.getFileSize();
		
		String hod="";
		
		//String parallalHod="";
		/*String checkRep ="select * from ess_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and Priority=1";
		    ResultSet check1 = ad.selectQuery(checkRep);
		    while(check1.next())
		    {
		    	hod=check1.getString("ApproverId");
		    	//parallalHod=check1.getString("Parallel_Approver1");
		    	
		    }*/

		String checkRep ="select dptId from emp_official_INFO where PERNR='"+user.getEmployeeNo()+"'";
	    ResultSet check1 = ad.selectQuery(checkRep);
	    while(check1.next())
	    {
	    	hod=check1.getString("DPTID");
	    	//parallalHod=check1.getString("Parallel_Approver1");
	    	
	    }
		    
		    
		    String CheckHod="";
			//String parallalHod="";
		    
		    if(help.getGuest_Type()!=null)
		    {
		    	if(help.getGuest_Type().equalsIgnoreCase("employee"))
		    	{
		    		String tr ="select dptId from emp_official_INFO where PERNR='"+help.getGuest_pernr()+"'";
				    ResultSet c = ad.selectQuery(tr);
				    while(c.next())
				    {
				    	CheckHod=c.getString("DPTID");
				    	//parallalHod=check1.getString("Parallel_Approver1");
				    	
				    }
		    	}
		    }
		    
			
		    
		if(!documentName.equalsIgnoreCase(""))
		{	
		
		if ((ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")
				|| ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("jpg")
				|| ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("xls") || ext
					.equalsIgnoreCase("txt")) && (filesize < 1048576)) {
			try {
				byte[] size = documentFile.getFileData();
				if (!documentName.equalsIgnoreCase("")) {
					int length = documentName.length();
					int dot = documentName.lastIndexOf(".");
					String fileName = documentFile.getFileName();
					String extension = documentName.substring(dot, length);
					filePath = getServlet().getServletContext().getRealPath(
							"jsp/EMicro Files/ESS/Travel Request/UploadFiles");
					InputStream in = ConnectionFactory.class.getClassLoader()
							.getResourceAsStream("db.properties");
					Properties props = new Properties();
					props.load(in);
					in.close();
					String uploadFilePath = props
							.getProperty("file.uploadFilePath");
					System.out.println("required filepath=" + uploadFilePath
							+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+help.getTravelmode()+"/"+reqNo+"");
					filePath = uploadFilePath
							+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+help.getTravelmode()+"/"+reqNo+"";
					File destinationDir = new File(filePath);
					if (!destinationDir.exists()) {
						destinationDir.mkdirs();
					}
					if (!fileName.equals("")) {
						File fileToCreate = new File(filePath, fileName);
						if (!fileToCreate.exists()) {
							FileOutputStream fileOutStream = new FileOutputStream(
									fileToCreate);
							fileOutStream.write(documentFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
						}
					}
					try {
						String filePath1 = "E:/EMicro Files/ESS/Travel Request/UploadFiles/"+help.getTravelmode()+"/"+reqNo+"";
						byte[] fileData1 = documentFile.getFileData();
						InputStream is = new ByteArrayInputStream(fileData1);
						// boolean status=saveDataInJRS(is,fileName);
						// System.out.println("File status="+status);

						File destinationDir1 = new File(filePath1);
						if (!destinationDir1.exists()) {
							destinationDir1.mkdirs();
						}
						if (!fileName.equals("")) {
							File fileToCreate1 = new File(filePath1, fileName);
							if (!fileToCreate1.exists()) {
								FileOutputStream fileOutStream1 = new FileOutputStream(
										fileToCreate1);
								fileOutStream1
										.write(documentFile.getFileData());
								fileOutStream1.flush();
								fileOutStream1.close();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException fe) {
				fe.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
			
			

			String contentType = documentFile.getContentType();
			String fileName = documentFile.getFileName();
			try {

				String sql9 = "select count(*) from Travel_Desk_Travellers  where  file_name='"
						+ documentFile.getFileName() + "' and req_type='"+help.getTravelmode()+"'";
				ResultSet rs15 = ad.selectQuery(sql9);
				int fileCount = 0;
				while (rs15.next()) {
					fileCount = Integer.parseInt(rs15.getString(1));
				}
				if (fileCount > 0) {
					help
							.setMessage("Document aleardy uploaded..please choose another file");
					help.setMessage2("");
				} else {
					
				String path = "/EMicro Files/ESS/Travel Request/UploadFiles/"+help.getTravelmode()+"/"+reqNo+"/"+documentFile.getFileName();	
				
					if(help.getGuest_Type()!=null)
					{
			
				    if(hod.equalsIgnoreCase(CheckHod) ||(help.getGuest_Type().equalsIgnoreCase("guest"))||(help.getGuest_Type().equalsIgnoreCase("doctor")))
				    {
						String sql = "insert into Travel_Desk_Travellers(req_id,PERNR,Req_type,Passport_no,Place_issue,Date_of_expiry,Name_of_guest,Contact_guest,Age_guest,"
								+ " Gender_guest,Email_guest,file_name,path,guest_Title,guest_Visano,guest_DOB,guest_Meal,guest_Company,travelmode,departOn,departTime,returnOn,"
								+ " returnTime,hotel_Res,hotel_Name,hotel_City,rent_Car,pickup_Details,drop_Details,fromPlace,toPlace,accom_type,accom_name,utravelmode,utraveltype)"
								+ "values('"
								+ reqNo
								+ "','"+help.getGuest_pernr()+""
								+ "','"+help.getGuest_Type()+""
								+ "','"+help.getPassportno()+""
								+ "','"+help.getPassportplace()+""
								+ "','"+passportexpirydate+""
								+ "','"+help.getGuestName()+""
								+ "','"+help.getGuestContactNo()+""
								+ "','"+help.getGuestAge()+""
								+ "','"+help.getGender()+""
								+ "','"+help.getEmail_Guest()+""
								+ "','" +documentFile.getFileName() + "' ,'"+path+"' ,'"+help.getGuest_Title()+"' , "
								+ " '"+help.getGuest_Visano()+"' ,'"+help.getGuest_DOB()+"','"+help.getGuest_Meal()+"','"+help.getGuest_Company()+"','"+help.getTravelmode()+"',"
								+ " '"+departdate+"','09:00AM','"+returndate+"' ,'06:00PM','"+help.getHotel_Res()+"','"+help.getHotel_Name()+"',"
								+ " '"+help.getHotel_City()+"','"+help.getRent_Car()+"','"+help.getPickup_Details()+"','"+help.getDrop_Details()+"','"+help.getFromPlace()+"',"
								+ "'"+help.getToPlace()+"','"+help.getAccom_type()+"','"+help.getAccom_name()+"','"+help.getUtravelmode()+"','"+help.getTraveltype()+"' )";
						int a = ad.SqlExecuteUpdate(sql);
						if (a > 0) {
							help
									.setMessage("");
							help.setMessage2("");
				    }
				 
					
				
		
					}
					
				    else
				    {
				    	
				    	 help.setMessage("Please add Travellers of same Department");
				    	help.setMessage2("");
				    }
					}
				}
			

			} catch (Exception e) {
				e.printStackTrace();

			}

		} else {
			help
					.setMessage2("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
			help.setMessage("");
		}
	}
		else
		{
			if(help.getGuest_Type()!=null)
			{
			
			if(hod.equalsIgnoreCase(CheckHod)||help.getGuest_Type().equalsIgnoreCase("guest")||help.getGuest_Type().equalsIgnoreCase("doctor"))
			    {
				
			
			String sql = "insert into Travel_Desk_Travellers(req_id,PERNR,Req_type,Passport_no,Place_issue,Date_of_expiry,Name_of_guest,Contact_guest,Age_guest,"
					+ " Gender_guest,Email_guest,file_name,path,guest_Title,guest_Visano,guest_DOB,guest_Meal,guest_Company,travelmode,departOn,departTime,returnOn,"
					+ " returnTime,hotel_Res,hotel_Name,hotel_City,rent_Car,pickup_Details,drop_Details,fromPlace,toPlace,accom_type,accom_name,utravelmode,utraveltype)"
					+ "values('"
					+ reqNo
					+ "','"+help.getGuest_pernr()+""
					+ "','"+help.getGuest_Type()+""
					+ "','"+help.getPassportno()+""
					+ "','"+help.getPassportplace()+""
					+ "','"+passportexpirydate+""
					+ "','"+help.getGuestName()+""
					+ "','"+help.getGuestContactNo()+""
					+ "','"+help.getGuestAge()+""
					+ "','"+help.getGender()+""
					+ "','"+help.getEmail_Guest()+""
					+ "','" +documentFile.getFileName() + "' ,'' ,'"+help.getGuest_Title()+"' , "
					+ " '"+help.getGuest_Visano()+"' ,'"+help.getGuest_DOB()+"','"+help.getGuest_Meal()+"','"+help.getGuest_Company()+"','"+help.getTravelmode()+"',"
					+ " '"+departdate+"','09:00AM','"+returndate+"' ,'06:00PM','"+help.getHotel_Res()+"','"+help.getHotel_Name()+"',"
					+ " '"+help.getHotel_City()+"','"+help.getRent_Car()+"','"+help.getPickup_Details()+"','"+help.getDrop_Details()+"','"+help.getFromPlace()+"',"
					+ "'"+help.getToPlace()+"','"+help.getAccom_type()+"','"+help.getAccom_name()+"' ,'"+help.getUtravelmode()+"','"+help.getTraveltype()+"' )";
			int a = ad.SqlExecuteUpdate(sql);
			if (a > 0) {
				
		help.setMessage("");
		help.setMessage2("");
			}
			    }
			
			 else
			 {
				 help.setMessage2("");
				 help.setMessage("Please add Travellers of same Department");
			 }
			
			}
			
		}
		
		//traveller details
        String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),departOn,103)  as departOn,CONVERT(varchar(10),returnOn,103)  as returnOn,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1,* from Travel_Desk_Travellers where req_id='"+reqNo+"'" ;
        ResultSet b=ad.selectQuery(his);
        ArrayList emplist = new ArrayList();
		try {
			while(b.next())
			{
				
					TravelRequestForm emp=new TravelRequestForm();
					String ere[]=empDet(Integer.toString(b.getInt("PERNR")));
					emp.setGuest_pernr(Integer.toString(b.getInt("PERNR")));
					emp.setEmployeeName(ere[0]);
					emp.setLocationId(ere[1]);
					emp.setDepartment(ere[2]);
					emp.setDesignation(ere[3]); 
					emp.setUserAge(ere[5]);
					emp.setUserGender(ere[7]);                		
					emp.setGuest_Type(b.getString("Req_type"));
					emp.setPassportno(b.getString("Passport_no"));
					emp.setPassportplace(b.getString("Place_issue"));
					emp.setPassportexpirydate(b.getString("Date_of_expiry"));
					emp.setGuestName(b.getString("Name_of_guest"));
					emp.setGuestContactNo(b.getString("Contact_guest"));
					emp.setGuest_Company(b.getString("guest_Company"));
					emp.setGuestAge(b.getString("Age_guest"));
					emp.setGender(b.getString("Gender_guest"));
					emp.setEmail_Guest(b.getString("Email_guest"));
					emp.setFileName(b.getString("file_name"));
					emp.setFileFullPath(b.getString("path"));
					emp.setGuest_Title(b.getString("guest_Title"));
					emp.setGuest_Visano(b.getString("guest_Visano"));
					emp.setGuest_DOB(b.getString("guest_DOB"));
					emp.setGuest_Meal(b.getString("guest_Meal"));
					emp.setOn_duty(b.getString("on_duty"));
					emp.setId(b.getInt("id"));
					emp.setDepartOn(b.getString("departOn"));
					emp.setReturnOn(b.getString("returnOn"));
					emp.setFromPlace(b.getString("fromPlace"));
					emp.setToPlace(b.getString("toPlace"));
					
					
					
					if(!b.getString("Date_of_expiry1").contains("1900"))
					emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
					else
			    	emp.setUserpassportexpirydate("");
					
					emplist .add(emp);
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        if(emplist.size()>0)
        	request.setAttribute("emplist", emplist);	
		
		return mapping.findForward("travellerList");	
	}
	
	public ActionForward uploadDocuments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//Created by Arun .Date:15-11-2016
		
		TravelRequestForm ondutyForm = (TravelRequestForm) form;

		FormFile documentFile = ondutyForm.getDocumentFile();
		String documentName = documentFile.getFileName();
		String requestType = request.getParameter("reqType");
		int reqNo = ondutyForm.getRequestNumber();
		if(reqNo==0)
		{
		String getMaxNo = "select max(reqNo) from Travel_Desk";
		ResultSet rst = ad.selectQuery(getMaxNo);
		try {
			while (rst.next()) {
				reqNo = rst.getInt(1) + 1;
			}
			ondutyForm.setRequestNumber(reqNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		
		
		///delete temp travellers 
		
		String g="delete Travel_Desk_Travellers_temp where reqNo='"+reqNo+"'";
		int y=ad.SqlExecuteUpdate(g);
		
		ArrayList emplist=new ArrayList();
		ArrayList guestlist=new ArrayList();
		///emps
    	String mulemps[]=request.getParameterValues("mulemps");
    	String passnos[]=request.getParameterValues("passnos");
    	String passplac[]=request.getParameterValues("passplac");
    	String passissue[]=request.getParameterValues("passissue");
    	String passexpiry[]=request.getParameterValues("passexpiry");
    	
//guests
    	String nameguests[]=request.getParameterValues("nameguests");
    	String ageguests[]=request.getParameterValues("ageguests");
    	String genderguests[]=request.getParameterValues("genderguests");
    	String contactguests[]=request.getParameterValues("contactguests");
    	String emailguests[]=request.getParameterValues("emailguests");
    	String passnosguest[]=request.getParameterValues("passnosguest");
    	String passplacguest[]=request.getParameterValues("passplacguest");
    	String passissueguest[]=request.getParameterValues("passissueguest");
    	String passexpiryguest[]=request.getParameterValues("passexpiryguest");
    	
    	if(mulemps!=null)
    	{	
    for(int i=0;i<mulemps.length;i++)
    {
    
		
			
				
				String mulpassissue="";
				String mulpassexpiry="";
				
				if(requestType.equalsIgnoreCase("international"))
			    {
			
			    String pasi[]=passissue[i].split("/");
			    mulpassissue=pasi[2]+"-"+pasi[1]+"-"+pasi[0];
			    
			    String pase[]=passexpiry[i].split("/");
			    mulpassexpiry=pase[2]+"-"+pase[1]+"-"+pase[0];
			    }
			    String b="insert into Travel_Desk_Travellers_temp (reqNo,PERNR,Req_type,Passport_no,Place_issue,Date_of_issue,Date_of_expiry) values('"+reqNo+"','"+mulemps[i]+"','Employee','"+passnos[i]+"','"+passplac[i]+"','"+mulpassissue+"','"+mulpassexpiry+"')";
		    	int t7=ad.SqlExecuteUpdate(b);
			    
		    
    	
    }
    	}
    	if(nameguests!=null)
    	{	
    for(int i=0;i<nameguests.length;i++)
    {
    		
				String guestpassissue="";
				String guestpassexpiry="";
				
				if(requestType.equalsIgnoreCase("international"))
			    {
			
			    String pasi[]=passissueguest[i].split("/");
			    guestpassissue=pasi[2]+"-"+pasi[1]+"-"+pasi[0];
			    
			    String pase[]=passexpiryguest[i].split("/");
			    guestpassexpiry=pase[2]+"-"+pase[1]+"-"+pase[0];
			    }
			    String b="insert into Travel_Desk_Travellers_temp (reqNo,Req_type,Name_of_guest,Contact_guest,Age_guest,Gender_guest,Email_guest,Passport_no,Place_issue,Date_of_issue,Date_of_expiry) "
			    + "values('"+reqNo+"','Guest','"+nameguests[i]+"','"+contactguests[i]+"','"+ageguests[i]+"','"+genderguests[i]+"','"+emailguests[i]+"','"+passnosguest[i]+"','"+passplacguest[i]+"','"+guestpassissue+"','"+guestpassexpiry+"')";
		    	int t7=ad.SqlExecuteUpdate(b);
			    
		    
    	
    }
    	}
		
    	
    	
    	//traveller details
        String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1,* from Travel_Desk_Travellers_temp where reqNo='"+reqNo+"'" ;
        ResultSet b=ad.selectQuery(his);
        try {
			while(b.next())
			{
				
				
				if(b.getString("req_type").equalsIgnoreCase("Employee"))
				{
					TravelRequestForm emp=new TravelRequestForm();
					String ere[]=empDet(Integer.toString(b.getInt("pernr")));
					emp.setEmployeeno(Integer.toString(b.getInt("pernr")));
					emp.setEmployeeName(ere[0]);
					emp.setLocationId(ere[1]);
					emp.setDepartment(ere[2]);
					emp.setDesignation(ere[3]); 
					emp.setUserAge(ere[5]);
				
					emp.setUserGender(ere[7]);                		
					
					emp.setUsermailId(b.getString("Email_guest"));
					emp.setUsermobno(b.getString("Contact_guest"));
					
					emp.setUserpassportno(b.getString("Passport_no"));
					emp.setUserpassportplace(b.getString("Place_issue"));
					
					if(!b.getString("Date_of_issue1").contains("1900"))
					emp.setUserpassportissuedate(b.getString("Date_of_issue1")); 
					else
					emp.setUserpassportissuedate("");
					
					if(!b.getString("Date_of_expiry1").contains("1900"))
					emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
					else
			    	emp.setUserpassportexpirydate("");
					
					emplist.add(emp);
				}

				if(b.getString("req_type").equalsIgnoreCase("Guest"))
				{
					TravelRequestForm gues=new TravelRequestForm();
					gues.setGuestName(b.getString("Name_of_guest"));
					gues.setGuestAge(b.getString("Age_guest"));
					gues.setGuestContactNo(b.getString("Contact_guest"));
					gues.setGender(b.getString("Gender_guest"));
					gues.setGuestmailId(b.getString("Email_guest"));
					
					gues.setUserpassportno(b.getString("Passport_no"));
					gues.setUserpassportplace(b.getString("Place_issue"));
					if(!b.getString("Date_of_issue1").contains("1900"))
						gues.setUserpassportissuedate(b.getString("Date_of_issue1")); 
			    		else
			    			gues.setUserpassportissuedate("");
			    		
			    		if(!b.getString("Date_of_expiry1").contains("1900"))
			    			gues.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
			    		else
			    			gues.setUserpassportexpirydate("");
					guestlist.add(gues);
				}
				
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        if(emplist.size()>0)
        	request.setAttribute("emplist", emplist);
        if(guestlist.size()>0)
        	request.setAttribute("guestlist", guestlist);
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String filePath = "";

		String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
		int filesize = documentFile.getFileSize();

		if ((ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")
				|| ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("jpg")
				|| ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("xls") || ext
					.equalsIgnoreCase("txt")) && (filesize < 1048576)) {
			try {
				byte[] size = documentFile.getFileData();
				if (!documentName.equalsIgnoreCase("")) {
					int length = documentName.length();
					int dot = documentName.lastIndexOf(".");
					String fileName = documentFile.getFileName();
					String extension = documentName.substring(dot, length);
					filePath = getServlet().getServletContext().getRealPath(
							"jsp/EMicro Files/ESS/Travel Request/UploadFiles");
					InputStream in = ConnectionFactory.class.getClassLoader()
							.getResourceAsStream("db.properties");
					Properties props = new Properties();
					props.load(in);
					in.close();
					String uploadFilePath = props
							.getProperty("file.uploadFilePath");
					System.out.println("required filepath=" + uploadFilePath
							+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+requestType+"");
					filePath = uploadFilePath
							+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+requestType+"";
					File destinationDir = new File(filePath);
					if (!destinationDir.exists()) {
						destinationDir.mkdirs();
					}
					if (!fileName.equals("")) {
						File fileToCreate = new File(filePath, fileName);
						if (!fileToCreate.exists()) {
							FileOutputStream fileOutStream = new FileOutputStream(
									fileToCreate);
							fileOutStream.write(documentFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
						}
					}
					try {
						String filePath1 = "E:/EMicro Files/ESS/Travel Request/UploadFiles/"+requestType+"";
						byte[] fileData1 = documentFile.getFileData();
						InputStream is = new ByteArrayInputStream(fileData1);
						// boolean status=saveDataInJRS(is,fileName);
						// System.out.println("File status="+status);

						File destinationDir1 = new File(filePath1);
						if (!destinationDir1.exists()) {
							destinationDir1.mkdirs();
						}
						if (!fileName.equals("")) {
							File fileToCreate1 = new File(filePath1, fileName);
							if (!fileToCreate1.exists()) {
								FileOutputStream fileOutStream1 = new FileOutputStream(
										fileToCreate1);
								fileOutStream1
										.write(documentFile.getFileData());
								fileOutStream1.flush();
								fileOutStream1.close();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException fe) {
				fe.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}

			String contentType = documentFile.getContentType();
			String fileName = documentFile.getFileName();
			try {

				String sql9 = "select count(*) from Travel_documents  where  file_name='"
						+ documentFile.getFileName() + "' and req_type='"+requestType+"'";
				ResultSet rs15 = ad.selectQuery(sql9);
				int fileCount = 0;
				while (rs15.next()) {
					fileCount = Integer.parseInt(rs15.getString(1));
				}
				if (fileCount > 0) {
					ondutyForm
							.setMessage("Document aleardy uploaded..please choose another file");
					ondutyForm.setMessage2("");
				} else {
					String sql = "insert into Travel_documents(request_no,req_type,user_id,file_name)"
							+ "values('"
							+ reqNo
							+ "','"+requestType+"','"
							+ user.getEmployeeNo()
							+ "','" + documentFile.getFileName() + "')";
					int a = ad.SqlExecuteUpdate(sql);
					if (a > 0) {
						ondutyForm
								.setMessage("Documents Uploaded Successfully");
						ondutyForm.setMessage2("");
					}
				}
				String sql1 = "select * from Travel_documents where request_no='"
						+ reqNo
						+ "' and user_id='"
						+ user.getEmployeeNo()
						+ "' ";
				ResultSet rs = ad.selectQuery(sql1);
				TravelRequestForm leaveForm1 = null;
				ArrayList a1 = new ArrayList();
				while (rs.next()) {
					leaveForm1 = new TravelRequestForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getInt("id"));
					leaveForm1.setFileFullPath(filePath + "/"
							+ rs.getString("file_name"));
					a1.add(leaveForm1);
				}
				request.setAttribute("documentDetails", a1);

			} catch (Exception e) {
				e.printStackTrace();

			}

		} else {
			ondutyForm
					.setMessage2("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
			ondutyForm.setMessage("");
			String sql1 = "select * from Travel_documents where request_no='"
					+ reqNo + "' and user_id='" + user.getEmployeeNo() + "' ";
			ResultSet rs = ad.selectQuery(sql1);
			TravelRequestForm leaveForm1 = null;
			ArrayList a1 = new ArrayList();
			try {
				while (rs.next()) {
					leaveForm1 = new TravelRequestForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getInt("id"));
					leaveForm1.setFileFullPath(filePath + "/"
							+ rs.getString("file_name"));
					a1.add(leaveForm1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("documentDetails", a1);

		}
		return mapping.findForward("domesticformrequest");
	}
	
	
	public ActionForward uploadConfirmDocuments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//Created by Arun .Date:15-11-2016
		
		TravelRequestForm ondutyForm = (TravelRequestForm) form;

		FormFile documentFile = ondutyForm.getDocumentFile();
		String documentName = documentFile.getFileName();
		String requestType = request.getParameter("reqType");
		int reqNo = ondutyForm.getRequestNumber();
		if(reqNo==0)
		{
		String getMaxNo = "select max(reqNo) from Travel_Desk";
		ResultSet rst = ad.selectQuery(getMaxNo);
		try {
			while (rst.next()) {
				reqNo = rst.getInt(1) + 1;
			}
			ondutyForm.setRequestNumber(reqNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String filePath = "";

		String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
		int filesize = documentFile.getFileSize();

		if ((ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")
				|| ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("jpg")
				|| ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("xls") || ext
					.equalsIgnoreCase("txt")) && (filesize < 1048576)) {
			try {
				byte[] size = documentFile.getFileData();
				if (!documentName.equalsIgnoreCase("")) {
					int length = documentName.length();
					int dot = documentName.lastIndexOf(".");
					String fileName = documentFile.getFileName();
					String extension = documentName.substring(dot, length);
					filePath = getServlet().getServletContext().getRealPath(
							"jsp/EMicro Files/ESS/Travel Request/ConfirmFiles");
					InputStream in = ConnectionFactory.class.getClassLoader()
							.getResourceAsStream("db.properties");
					Properties props = new Properties();
					props.load(in);
					in.close();
					String uploadFilePath = props
							.getProperty("file.uploadFilePath");
					System.out.println("required filepath=" + uploadFilePath
							+ "/EMicro Files/ESS/Travel Request/ConfirmFiles/"+requestType+"");
					filePath = uploadFilePath
							+ "/EMicro Files/ESS/Travel Request/ConfirmFiles/"+requestType+"";
					File destinationDir = new File(filePath);
					if (!destinationDir.exists()) {
						destinationDir.mkdirs();
					}
					if (!fileName.equals("")) {
						File fileToCreate = new File(filePath, fileName);
						if (!fileToCreate.exists()) {
							FileOutputStream fileOutStream = new FileOutputStream(
									fileToCreate);
							fileOutStream.write(documentFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
						}
					}
					try {
						String filePath1 = "E:/EMicro Files/ESS/Travel Request/ConfirmFiles/"+requestType+"";
						byte[] fileData1 = documentFile.getFileData();
						InputStream is = new ByteArrayInputStream(fileData1);
						// boolean status=saveDataInJRS(is,fileName);
						// System.out.println("File status="+status);

						File destinationDir1 = new File(filePath1);
						if (!destinationDir1.exists()) {
							destinationDir1.mkdirs();
						}
						if (!fileName.equals("")) {
							File fileToCreate1 = new File(filePath1, fileName);
							if (!fileToCreate1.exists()) {
								FileOutputStream fileOutStream1 = new FileOutputStream(
										fileToCreate1);
								fileOutStream1
										.write(documentFile.getFileData());
								fileOutStream1.flush();
								fileOutStream1.close();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException fe) {
				fe.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}

			String contentType = documentFile.getContentType();
			String fileName = documentFile.getFileName();
			try {

				String sql9 = "select count(*) from Travel_confirm_documents  where  file_name='"
						+ documentFile.getFileName() + "' and req_type='"+requestType+"'";
				ResultSet rs15 = ad.selectQuery(sql9);
				int fileCount = 0;
				while (rs15.next()) {
					fileCount = Integer.parseInt(rs15.getString(1));
				}
				if (fileCount > 0) {
					ondutyForm
							.setMessage("Document aleardy uploaded..please choose another file");
					ondutyForm.setMessage2("");
				} else {
					String sql = "insert into Travel_confirm_documents(request_no,req_type,user_id,file_name)"
							+ "values('"
							+ reqNo
							+ "','"+requestType+"','"
							+ user.getEmployeeNo()
							+ "','" + documentFile.getFileName() + "')";
					int a = ad.SqlExecuteUpdate(sql);
					if (a > 0) {
						ondutyForm
								.setMessage("Documents Uploaded Successfully");
						ondutyForm.setMessage2("");
					}
				}
				String sql1 = "select * from Travel_confirm_documents where request_no='"
						+ reqNo
						+ "' and user_id='"
						+ user.getEmployeeNo()
						+ "' ";
				ResultSet rs = ad.selectQuery(sql1);
				TravelRequestForm leaveForm1 = null;
				ArrayList a1 = new ArrayList();
				while (rs.next()) {
					leaveForm1 = new TravelRequestForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getInt("id"));
					leaveForm1.setFileFullPath(filePath + "/"
							+ rs.getString("file_name"));
					a1.add(leaveForm1);
				}
				request.setAttribute("confirmDetails", a1);

			} catch (Exception e) {
				e.printStackTrace();

			}

		} else {
			ondutyForm
					.setMessage2("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
			ondutyForm.setMessage("");
			String sql1 = "select * from Travel_confirm_documents where request_no='"
					+ reqNo + "' and user_id='" + user.getEmployeeNo() + "' ";
			ResultSet rs = ad.selectQuery(sql1);
			TravelRequestForm leaveForm1 = null;
			ArrayList a1 = new ArrayList();
			try {
				while (rs.next()) {
					leaveForm1 = new TravelRequestForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getInt("id"));
					leaveForm1.setFileFullPath(filePath + "/"
							+ rs.getString("file_name"));
					a1.add(leaveForm1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("confirmDetails", a1);

		}
		ViewConfirmationrequest(mapping, ondutyForm, request, response);
		return mapping.findForward("viewconfirmrequest");
	}

	public ActionForward displayDomestic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//Created by Arun .Date:11-01-2017
		
		
		TravelRequestForm requestForm= (TravelRequestForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		
		ArrayList travel_desk_repList=new ArrayList();
		ArrayList travel_desk_repLabelList=new ArrayList();
		ResultSet rs113 = ad.selectQuery("select * from travel_desk_rep"); 
				try {
					while(rs113.next()) {
						travel_desk_repList.add(rs113.getString("name"));
						travel_desk_repLabelList.add(rs113.getString("name"));
					}
					rs113.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				requestForm.setTravel_desk_repList(travel_desk_repList);
				requestForm.setTravel_desk_repLabelList(travel_desk_repLabelList);							


		
		return mapping.findForward("domesticformrequest");
	}
	
	public ActionForward displayExpense(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//Created by Arun .Date:11-01-2017
		TravelRequestForm requestForm =  new TravelRequestForm();
		requestForm.reset(mapping, request);
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		 
		
		return mapping.findForward("domesticformrequest");
	}
	
	
	public ActionForward domesticNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
		{
		//Created by Arun .Date:11-01-2017
		
		TravelRequestForm requestForm =  (TravelRequestForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int reqid=0;
		requestForm.setMessage("");
		requestForm.setMessage2("");
		
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
		String dateNow = ft.format(dNow);
		
		requestForm.setTravel_Adult("0");
		requestForm.setTravel_Child("0");
		requestForm.setTravel_Infant("0");
		requestForm.setTravelRequestFor("One Way");
		requestForm.setTravelFor("Self");
		requestForm.setTrip_Advance("No");
		requestForm.setOnduty_Req("Yes");
		requestForm.setSpon_div("");
		requestForm.setOldrequestNumber(0);
		requestForm.setLocid("");
		requestForm.setHotel_Res("No");
		requestForm.setAccom_type("");
		requestForm.setDepartOn("");
		requestForm.setReturnOn("");
		requestForm.setDepartTime("");
		requestForm.setReturnTime("");
		requestForm.setTravelmode("");
		requestForm.setFromPlace("");
		requestForm.setToPlace("");
		requestForm.setPurposeOfVisit("");
		requestForm.setPurposetype("");
		requestForm.setPurposetext("");
		requestForm.setBud_code("");
		requestForm.setEst_trip_cose("");
		requestForm.setTrip_Amt("");
		
		
		
		
		requestForm.setReqDate(dateNow);
		
		
		String re[]=empDet(user.getEmployeeNo());
		requestForm.setEmployeeno(user.getEmployeeNo());
		requestForm.setEmployeeName(re[0]);
		requestForm.setP_name(re[0]);
		requestForm.setLocationId(re[1]);
		requestForm.setDepartment(re[2]);
		requestForm.setP_dept(re[2]);
		requestForm.setDesignation(re[3]); 
		requestForm.setUserAge(re[5]);
		requestForm.setUsermailId(re[6]);
		requestForm.setP_email(re[6]);
		requestForm.setUserGender(re[7]);
		
		
		String pe[]=empPERDet(user.getEmployeeNo());

		requestForm.setUsermobno(pe[0]);
		requestForm.setP_cont(pe[0]);
		requestForm.setUserpassportno(pe[1]);
		requestForm.setUserpassportplace(pe[2]);
		requestForm.setUserPersonalmailId(pe[5]);
				
		if(!pe[3].contains("1900"))
		requestForm.setUserpassportissuedate(pe[3]); 
		else
	    requestForm.setUserpassportissuedate(""); 
		
		if(!pe[4].contains("1900"))
		requestForm.setUserpassportexpirydate(pe[4]); 
		else
	    requestForm.setUserpassportexpirydate(""); 
		
		
		
		synchronized (this) {
		
			String getMaxNo = "select isnull(max(id),0) from Travel_Desk";
			ResultSet rs = ad.selectQuery(getMaxNo);
			try {
				if (rs.next()) {
					reqid = rs.getInt(1) + 1;
				}
				requestForm.setId(reqid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		/*String s ="Insert into Travel_Desk(id,prefix,Pernr) "
				+ " values('"+reqid+"','TN','"+user.getEmployeeNo()+"')";
		int i = ad.SqlExecuteUpdate(s);
		if(i>0)
		{
			requestForm.setId(reqid);		
		}	*/
		}
		
		
		
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location "); 
				try {
					while(rs11.next()) {
						locationList.add(rs11.getString("location_code"));
						locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
						
					}
					rs11.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				requestForm.setLocationIdList(locationList);
				requestForm.setLocationLabelList(locationLabelList);
				
				ArrayList TraveldeskList = new ArrayList();
				ArrayList TraveldeskLabelList=new ArrayList();
				ResultSet rs121 = ad.selectQuery("select distinct e.EMP_FULLNAME,t.ApproverId from Travel_desk_Approvers t, emp_official_info e where e.PERNR=t.ApproverId"); 
						try {
							while(rs121.next()) {
								TraveldeskList.add(rs121.getString("ApproverId"));
								TraveldeskLabelList.add(rs121.getString("EMP_FULLNAME"));
								
							}
							rs121.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		
						
						requestForm.setTraveldeskList(TraveldeskList);
						requestForm.setTraveldeskLabelList(TraveldeskLabelList);
						
				ArrayList divIdList=new ArrayList();
				ArrayList divLabelList=new ArrayList();
				ResultSet rs111 = ad.selectQuery("select * from division"); 
						try {
							while(rs111.next()) {
								divIdList.add(rs111.getString("div_code"));
								divLabelList.add(rs111.getString("div_desc"));
								
							}
							rs111.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						requestForm.setDivIdList(divIdList);
						requestForm.setDivLabelList(divLabelList);	
						

						ArrayList currIdList=new ArrayList();
						ArrayList currLabelList=new ArrayList();
						ResultSet rs112 = ad.selectQuery("select * from Currency"); 
								try {
									while(rs112.next()) {
										currIdList.add(rs112.getString("WAERS"));
										currLabelList.add(rs112.getString("isocd"));
										
									}
									rs112.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								requestForm.setCurrIdList(currIdList);
								requestForm.setCurrLabelList(currLabelList);			
			
		requestForm.setMessage("");
	
		return mapping.findForward("domestic");
	}
	
	
	
	public ActionForward domesticReschedule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//Created by Arun .Date:11-01-2017
		
		TravelRequestForm requestForm =  (TravelRequestForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int reqid=0;
		requestForm.setMessage("");
		requestForm.setMessage2("");
		
		String reqNo=request.getParameter("requstNo");
		requestForm.setOldrequestNumber(Integer.parseInt(reqNo));
		
		
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
		String dateNow = ft.format(dNow);
		
		requestForm.setTravel_Adult("0");
		requestForm.setTravel_Child("0");
		requestForm.setTravel_Infant("0");
		requestForm.setTravelRequestFor("One Way");
	
		
		requestForm.setReqDate(dateNow);
		
		synchronized (this) {	
			String getMaxNo = "select isnull(max(id),0) from Travel_Desk";
		ResultSet rs = ad.selectQuery(getMaxNo);
		try {
			if (rs.next()) {
				reqid = rs.getInt(1)+1;
			}
			requestForm.setId(reqid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		String s ="Insert into Travel_Desk(id,prefix,Pernr) "
			+ " values('"+reqid+"','TN','"+user.getEmployeeNo()+"')";
		int i = ad.SqlExecuteUpdate(s);
		if(i>0)
		{
		requestForm.setId(reqid);		
		}
		}
		String lis="select CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1, CONVERT(varchar(10),billdate,103) as billdate1 ,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as  bookeddate1,"
				+ "  CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1   ,CONVERT(varchar(10),Depart_On,103)  as departon,"
				+ "CONVERT(varchar(10),Return_On,103)  as returnon,* from Travel_Desk where reqNo='"+reqNo+"' ";
		ResultSet ff=ad.selectQuery(lis);
		try {
			if(ff.next())
			{
				String re[]=empDet(Integer.toString(ff.getInt("pernr")));
				requestForm.setEmployeeno(user.getEmployeeNo());
				requestForm.setEmployeeName(re[0]);
				requestForm.setP_name(re[0]);
				requestForm.setLocationId(re[1]);
				requestForm.setDepartment(re[2]);
				requestForm.setP_dept(re[2]);
				requestForm.setDesignation(re[3]); 
				requestForm.setUserAge(re[5]);
				requestForm.setUsermailId(re[6]);
				requestForm.setP_email(re[6]);
				requestForm.setUserGender(re[7]);
				
				String pe[]=empPERDet(Integer.toString(ff.getInt("pernr")));
				requestForm.setUsermobno(pe[0]);
				requestForm.setP_cont(pe[0]);
				requestForm.setUserpassportno(pe[1]);
				requestForm.setUserpassportplace(pe[2]);
				requestForm.setUserPersonalmailId(pe[5]);
				if(!pe[3].contains("1900"))
					requestForm.setUserpassportissuedate(pe[3]); 
					else
				    requestForm.setUserpassportissuedate(""); 
			    if(!pe[4].contains("1900"))
					requestForm.setUserpassportexpirydate(pe[4]); 
					else
				    requestForm.setUserpassportexpirydate(""); 
			
				requestForm.setReqType(ff.getString("Req_type"));
				requestForm.setTypeOfTravel(ff.getString("Type_of_travel"));
				requestForm.setTravelFor(ff.getString("Travel_for"));
				requestForm.setTravelRequestFor(ff.getString("Travel_Request_for"));
				requestForm.setModeOfTravel(ff.getString("Mode_of_travel"));
				requestForm.setFromPlace(ff.getString("From_place"));
				requestForm.setDepartOn("");
				requestForm.setDepartTime("");
				requestForm.setReturnTime("");
				requestForm.setReturnOn("");
				requestForm.setDepartRemarks(ff.getString("Depart_Time_remarks"));
				requestForm.setReturnRemarks(ff.getString("Return_Time_remarks"));
				requestForm.setToPlace(ff.getString("To_place"));
			    requestForm.setVia(ff.getString("via"));
			    requestForm.setRemarks(ff.getString("Remarks"));
			    requestForm.setPurposeOfVisit(ff.getString("Purpose"));
			    requestForm.setReturnOn(ff.getString("returnon"));
			    requestForm.setTravel_Adult(ff.getString("Travel_Adult"));
			    requestForm.setTravel_Child(ff.getString("Travel_Child"));
			    requestForm.setTravel_Infant(ff.getString("travel_Infant"));
			    requestForm.setPurposetext(ff.getString("purposetext"));
			    requestForm.setTravel_Days(ff.getString("travel_Days"));
			    requestForm.setAirline_Pref(ff.getString("airline_Pref"));
			    requestForm.setAirline_Just(ff.getString("airline_Just"));
			    requestForm.setSpon_div(ff.getString("spon_div"));
			    requestForm.setBud_code(ff.getString("bud_code"));
			    requestForm.setEst_trip_cose(ff.getString("est_trip_cose"));
			    requestForm.setHotel_Res(ff.getString("hotel_Res"));
			    requestForm.setOnduty_Req(ff.getString("onduty_Req"));
			    requestForm.setRent_Car(ff.getString("rent_Car"));
			    requestForm.setPickup_Details(ff.getString("pickup_Details"));
			    requestForm.setDrop_Details(ff.getString("drop_Details"));
			    requestForm.setTrip_Advance(ff.getString("trip_Advance"));
			    requestForm.setTrip_Amt(ff.getString("trip_Amt"));
			    requestForm.setTrip_Currency(ff.getString("trip_Currency"));
			    requestForm.setTrip_From_Date(ff.getString("trip_From_Date"));
			    requestForm.setTrip_To_Date(ff.getString("trip_To_Date"));
			    requestForm.setTrip_From_Time(ff.getString("trip_From_Time"));
			    requestForm.setTrip_To_time(ff.getString("trip_To_time"));
			    requestForm.setTrip_Priority(ff.getString("trip_Priority"));
			    requestForm.setPurposetype(ff.getString("purposetype"));
			    requestForm.setTravelmode(ff.getString("travelmode"));
			    requestForm.setTraveltype(ff.getString("Type_of_travel"));
			    requestForm.setService_class(ff.getString("service_class"));
			    requestForm.setTravelagentname(ff.getString("travelagentname"));
			    requestForm.setBillno(ff.getString("billno"));
			    requestForm.setBilldate(ff.getString("billdate1"));
			    requestForm.setBillamount(ff.getString("billamount"));
			    requestForm.setBookingstatus(ff.getString("bookingstatus"));
			    requestForm.setBookeddate(ff.getString("bookeddate1"));
			    requestForm.setReqDate(ff.getString("Created_date1"));
			    requestForm.setBookedby(Empname(ff.getString("bookedby")));
			    requestForm.setConfirmdate(ff.getString("confirmdate1"));
			    requestForm.setConfirmby(Empname(ff.getString("confirmby")));
			    requestForm.setConfirmstatus(ff.getString("confirmstatus"));
			    requestForm.setLocid(ff.getString("locid"));
			    requestForm.setTravel_desk_type(Empname(ff.getString("travel_desk_type")));
			    requestForm.setFilepath(ff.getString("path"));
			   // requestForm.setTravel_desk_type(ff.getString("travel_desk_type"));
			    
			    requestForm.setOldid(ff.getInt("id"));
			    requestForm.setOldrequestNumber(ff.getInt("reqno"));
			    
			    String ins="insert into Travel_Desk_Travellers (req_id,PERNR,Req_type,Passport_no,Place_issue,Date_of_issue,Date_of_expiry,Name_of_guest,Contact_guest,Age_guest,Gender_guest,Email_guest,file_name,path,guest_Title,guest_Visano,guest_DOB,guest_Meal,guest_Company,guest_Type,travelmode,on_duty,departOn,departTime,returnOn,returnTime,hotel_Res,hotel_Name,hotel_City,rent_Car,pickup_Details,drop_Details) "
			    		+ "select '"+requestForm.getId()+"',PERNR,Req_type,Passport_no,Place_issue,Date_of_issue,Date_of_expiry,Name_of_guest,Contact_guest,Age_guest,Gender_guest,Email_guest,file_name,path,guest_Title,guest_Visano,guest_DOB,guest_Meal,guest_Company,guest_Type,travelmode,on_duty,departOn,departTime,returnOn,returnTime,hotel_Res,hotel_Name,hotel_City,rent_Car,pickup_Details,drop_Details "
			    		+ "from Travel_Desk_Travellers where req_id='"+requestForm.getOldid()+"' ";
			    int t = ad.SqlExecuteUpdate(ins);
			    
        		ArrayList city = new ArrayList();
			    String his1="select isnull(convert(nvarchar(10),Travel_Multiple_City.trav_id)+'-'+emp_official_info.EMP_FULLNAME,0) as id,Travel_Multiple_City.Location,"
			    		+ " Travel_Multiple_City.mpassportexpirydate,mguest_Visano, Travel_Multiple_City.mpassportexpirydate, Travel_Multiple_City.mpassportno, Travel_Multiple_City.mtraveltype,Travel_Multiple_City.mtravelmode,Travel_Multiple_City.mdeparture,Travel_Multiple_City.morigin,Travel_Multiple_City.maccom_name,Travel_Multiple_City.maccom_type,Travel_Multiple_City.Air_preference,convert(nvarchar(10),Travel_Multiple_City.Arrival_date,103) as Arrival_date,"
			    		+ " Travel_Multiple_City.Arrival_time,convert(nvarchar(10),Travel_Multiple_City.Departure_date,103) as Departure_date,"
			    		+ " Travel_Multiple_City.Departure_time ,Travel_Multiple_City.mhotel_Res,Travel_Multiple_City.mrent_Car,Travel_Multiple_City.mhotel_Name,"
			    		+ " Travel_Multiple_City.mhotel_City,Travel_Multiple_City.mpickup_details,Travel_Multiple_City.mdrop_Details"
			    		+ " from Travel_Multiple_City left outer join Travel_Desk_Travellers on Travel_Multiple_City.trav_id=Travel_Desk_Travellers.id and Travel_Desk_Travellers.id!=0"
			    		+ " left outer join emp_official_info on emp_official_info.PERNR=Travel_Desk_Travellers.PERNR"
			    		+ " where Travel_Multiple_City.req_id='"+requestForm.getOldid()+"'" ;
                ResultSet b1=ad.selectQuery(his1);
        		try {
        			while(b1.next())
        			{
        					TravelRequestForm emp=new TravelRequestForm();
        					emp.setMultiemployeeno(b1.getString("id"));
        					emp.setLocationId(b1.getString("location"));
        					emp.setAirline_Pref(b1.getString("air_preference"));
        					emp.setTrip_From_Date(b1.getString("Arrival_date"));
        					emp.setTrip_To_time(b1.getString("Arrival_Time"));
        					emp.setTrip_To_Date(b1.getString("Departure_date"));
        					emp.setTrip_To_time(b1.getString("Departure_time"));
        					emp.setHotel_Res(b1.getString("mhotel_Res"));
        					emp.setRent_Car(b1.getString("mrent_Car"));
        					emp.setHotel_Name(b1.getString("mhotel_Name"));
        					emp.setHotel_City(b1.getString("mhotel_City"));
        					emp.setPickup_Details(b1.getString("mpickup_Details"));
        					emp.setDrop_Details(b1.getString("mdrop_Details"));
        					emp.setAccom_type(b1.getString("maccom_type"));
        					emp.setAccom_name(b1.getString("maccom_name"));
        					emp.setOrigin(b1.getString("morigin"));
        					emp.setDeparture(b1.getString("mdeparture"));
        					emp.setTravelmode(b1.getString("mtravelmode"));
        					emp.setTraveltype(b1.getString("mtraveltype"));
        					emp.setPassportno(b1.getString("mpassportno"));
        					emp.setPassportexpirydate(b1.getString("mpassportexpirydate"));
        					emp.setVisano(b1.getString("mguest_Visano"));
        					
        					city.add(emp);
        				
        			}
        		} catch (SQLException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
        		
        		if(city.size()>0)
                	request.setAttribute("city", city);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location "); 
				try {
					while(rs11.next()) {
						locationList.add(rs11.getString("location_code"));
						locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
						
					}
					rs11.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				requestForm.setLocationIdList(locationList);
				requestForm.setLocationLabelList(locationLabelList);
				
		
		
				ArrayList divIdList=new ArrayList();
				ArrayList divLabelList=new ArrayList();
				ResultSet rs111 = ad.selectQuery("select * from division"); 
						try {
							while(rs111.next()) {
								divIdList.add(rs111.getString("div_code"));
								divLabelList.add(rs111.getString("div_desc"));
								
							}
							rs111.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						requestForm.setDivIdList(divIdList);
						requestForm.setDivLabelList(divLabelList);	
						
			
						
						String monthplan="select tm.plan_date,tm.morigin,tm.mdestination,tm.mtravelmode,tm.mtraveltype,tm.mremarks from Travel_MTP as tm,Travel_Desk as td where td.reqNo='"+ reqNo+"' and td.id=tm.req_id order by plan_date";
						List appList1 = new LinkedList();
				ResultSet rsAppr1 = ad.selectQuery(monthplan);
				try {
					while (rsAppr1.next()) {
						TravelRequestForm appr=new TravelRequestForm();
						
						String d[]=rsAppr1.getString("plan_date").split("-");
						String dateString = d[2]+"/"+d[1]+"/"+d[0];;
						
						appr.setPlandate(dateString);
						
						
						
						//appr.setFromdate(rsAppr1.getString("plan_date"));
						appr.setPlanorigin(rsAppr1.getString("morigin"));
						appr.setPlandestination(rsAppr1.getString("mdestination"));
						appr.setTravelmode(rsAppr1.getString("mtravelmode"));
						appr.setTraveltype(rsAppr1.getString("mtraveltype"));
						appr.setPlanremarks(rsAppr1.getString("mremarks"));
						appList1.add(appr);
					}
					if(appList1.size()>0)
					request.setAttribute("city1", appList1);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
		requestForm.setMessage("");
		
		
	
		return mapping.findForward("domestic");
	}
	
	
	public ActionForward domesticRevert(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		

		//Created by Arun .Date:11-01-2017
		
		TravelRequestForm requestForm =  (TravelRequestForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int reqid=0;
		requestForm.setMessage("");
		requestForm.setMessage2("");
		
		String reqNo=request.getParameter("requstNo");
		requestForm.setOldrequestNumber(Integer.parseInt(reqNo));
		
		
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
		String dateNow = ft.format(dNow);
		
		requestForm.setTravel_Adult("0");
		requestForm.setTravel_Child("0");
		requestForm.setTravel_Infant("0");
		requestForm.setTravelRequestFor("One Way");
	
		
		requestForm.setReqDate(dateNow);
		
		synchronized (this) {	
			String getMaxNo = "select isnull(max(id),0) from Travel_Desk";
		ResultSet rs = ad.selectQuery(getMaxNo);
		try {
			if (rs.next()) {
				reqid = rs.getInt(1)+1;
			}
			requestForm.setId(reqid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList TraveldeskList = new ArrayList();
		ArrayList TraveldeskLabelList=new ArrayList();
		ResultSet rs121 = ad.selectQuery("select distinct e.EMP_FULLNAME,t.ApproverId from Travel_desk_Approvers t, emp_official_info e where e.PERNR=t.ApproverId"); 
				try {
					while(rs121.next()) {
						TraveldeskList.add(rs121.getString("ApproverId"));
						TraveldeskLabelList.add(rs121.getString("EMP_FULLNAME"));
						
					}
					rs121.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				requestForm.setTraveldeskList(TraveldeskList);
				requestForm.setTraveldeskLabelList(TraveldeskLabelList);

		String s ="Insert into Travel_Desk(id,prefix,Pernr) "
			+ " values('"+reqid+"','TN','"+user.getEmployeeNo()+"')";
		int i = ad.SqlExecuteUpdate(s);
		if(i>0)
		{
		requestForm.setId(reqid);		
		}
		}
		String lis="select CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1, CONVERT(varchar(10),billdate,103) as billdate1 ,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as  bookeddate1,"
				+ "  CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1   ,CONVERT(varchar(10),Depart_On,103)  as departon,"
				+ "CONVERT(varchar(10),Return_On,103)  as returnon,* from Travel_Desk where reqNo='"+reqNo+"' ";
		ResultSet ff=ad.selectQuery(lis);
		try {
			if(ff.next())
			{
				String re[]=empDet(Integer.toString(ff.getInt("pernr")));
				requestForm.setEmployeeno(user.getEmployeeNo());
				requestForm.setEmployeeName(re[0]);
				requestForm.setP_name(re[0]);
				requestForm.setLocationId(re[1]);
				requestForm.setDepartment(re[2]);
				requestForm.setP_dept(re[2]);
				requestForm.setDesignation(re[3]); 
				requestForm.setUserAge(re[5]);
				requestForm.setUsermailId(re[6]);
				requestForm.setP_email(re[6]);
				requestForm.setUserGender(re[7]);
				
				String pe[]=empPERDet(Integer.toString(ff.getInt("pernr")));
				requestForm.setUsermobno(pe[0]);
				requestForm.setP_cont(pe[0]);
				requestForm.setUserpassportno(pe[1]);
				requestForm.setUserpassportplace(pe[2]);
				requestForm.setUserPersonalmailId(pe[5]);
				if(!pe[3].contains("1900"))
					requestForm.setUserpassportissuedate(pe[3]); 
					else
				    requestForm.setUserpassportissuedate(""); 
			    if(!pe[4].contains("1900"))
					requestForm.setUserpassportexpirydate(pe[4]); 
					else
				    requestForm.setUserpassportexpirydate(""); 
			
				requestForm.setReqType(ff.getString("Req_type"));
				requestForm.setTravel_desk_type(ff.getString("travel_desk_type"));		
				requestForm.setService_class(ff.getString("service_class"));
				requestForm.setTraveltype(ff.getString("Type_of_travel"));
				requestForm.setTypeOfTravel(ff.getString("Type_of_travel"));
				requestForm.setTravelFor(ff.getString("Travel_for"));
				requestForm.setTravelRequestFor(ff.getString("Travel_Request_for"));
				requestForm.setModeOfTravel(ff.getString("Mode_of_travel"));
				requestForm.setFromPlace(ff.getString("From_place"));
				requestForm.setDepartOn(ff.getString("departon"));
				requestForm.setDepartTime("");
				requestForm.setReturnTime("");
				requestForm.setReturnOn(ff.getString("returnon"));
				requestForm.setDepartRemarks(ff.getString("Depart_Time_remarks"));
				requestForm.setReturnRemarks(ff.getString("Return_Time_remarks"));
				requestForm.setToPlace(ff.getString("To_place"));
			    requestForm.setVia(ff.getString("via"));
			    requestForm.setRemarks(ff.getString("Remarks"));
			    requestForm.setPurposeOfVisit(ff.getString("Purpose"));
			    requestForm.setReturnOn(ff.getString("returnon"));
			    requestForm.setTravel_Adult(ff.getString("Travel_Adult"));
			    requestForm.setTravel_Child(ff.getString("Travel_Child"));
			    requestForm.setTravel_Infant(ff.getString("travel_Infant"));
			    requestForm.setPurposetext(ff.getString("purposetext"));
			    requestForm.setTravel_Days(ff.getString("travel_Days"));
			    requestForm.setAirline_Pref(ff.getString("airline_Pref"));
			    requestForm.setAirline_Just(ff.getString("airline_Just"));
			    requestForm.setSpon_div(ff.getString("spon_div"));
			    requestForm.setBud_code(ff.getString("bud_code"));
			    requestForm.setEst_trip_cose(ff.getString("est_trip_cose"));
			    requestForm.setHotel_Res(ff.getString("hotel_Res"));
			    requestForm.setOnduty_Req(ff.getString("onduty_Req"));
			    requestForm.setRent_Car(ff.getString("rent_Car"));
			    requestForm.setPickup_Details(ff.getString("pickup_Details"));
			    requestForm.setDrop_Details(ff.getString("drop_Details"));
			    requestForm.setTrip_Advance(ff.getString("trip_Advance"));
			    requestForm.setTrip_Amt(ff.getString("trip_Amt"));
			    requestForm.setTrip_Currency(ff.getString("trip_Currency"));
			    requestForm.setTrip_From_Date(ff.getString("trip_From_Date"));
			    requestForm.setTrip_To_Date(ff.getString("trip_To_Date"));
			    requestForm.setTrip_From_Time(ff.getString("trip_From_Time"));
			    requestForm.setTrip_To_time(ff.getString("trip_To_time"));
			    requestForm.setTrip_Priority(ff.getString("trip_Priority"));
			    requestForm.setPurposetype(ff.getString("purposetype"));
			    requestForm.setTravelmode(ff.getString("travelmode"));
			    requestForm.setTraveltype(ff.getString("Type_of_travel"));
			    requestForm.setService_class(ff.getString("service_class"));
			    requestForm.setTravelagentname(ff.getString("travelagentname"));
			    requestForm.setBillno(ff.getString("billno"));
			    requestForm.setBilldate(ff.getString("billdate1"));
			    requestForm.setBillamount(ff.getString("billamount"));
			    requestForm.setBookingstatus(ff.getString("bookingstatus"));
			    requestForm.setBookeddate(ff.getString("bookeddate1"));
			    requestForm.setReqDate(ff.getString("Created_date1"));
			    requestForm.setBookedby(Empname(ff.getString("bookedby")));
			    requestForm.setConfirmdate(ff.getString("confirmdate1"));
			    requestForm.setConfirmby(Empname(ff.getString("confirmby")));
			    requestForm.setConfirmstatus(ff.getString("confirmstatus"));
			    requestForm.setLocid(ff.getString("locid"));
			    requestForm.setTravel_desk_type(ff.getString("travel_desk_type"));
			    requestForm.setFilepath(ff.getString("path"));
			  /*  requestForm.setTravel_desk_type(ff.getString("travel_desk_type"));*/
			    
			    requestForm.setOldid(ff.getInt("id"));
			    requestForm.setOldrequestNumber(ff.getInt("reqno"));
			    
			    String ins="insert into Travel_Desk_Travellers (req_id,PERNR,Req_type,Passport_no,Place_issue,Date_of_issue,Date_of_expiry,Name_of_guest,Contact_guest,Age_guest,Gender_guest,Email_guest,file_name,path,guest_Title,guest_Visano,guest_DOB,guest_Meal,guest_Company,guest_Type,travelmode,on_duty,departOn,departTime,returnOn,returnTime,hotel_Res,hotel_Name,hotel_City,rent_Car,pickup_Details,drop_Details) "
			    		+ "select '"+requestForm.getId()+"',PERNR,Req_type,Passport_no,Place_issue,Date_of_issue,Date_of_expiry,Name_of_guest,Contact_guest,Age_guest,Gender_guest,Email_guest,file_name,path,guest_Title,guest_Visano,guest_DOB,guest_Meal,guest_Company,guest_Type,travelmode,on_duty,departOn,departTime,returnOn,returnTime,hotel_Res,hotel_Name,hotel_City,rent_Car,pickup_Details,drop_Details "
			    		+ "from Travel_Desk_Travellers where req_id='"+requestForm.getOldid()+"' ";
			    int t = ad.SqlExecuteUpdate(ins);
			    
        		ArrayList city = new ArrayList();
			    String his1="select Travel_Multiple_City.id,isnull(convert(nvarchar(10),Travel_Multiple_City.trav_id)+'-'+emp_official_info.EMP_FULLNAME,0) as id,Travel_Multiple_City.Location,"
			    		+ " Travel_Multiple_City.mpassportexpirydate,mguest_Visano, Travel_Multiple_City.mpassportexpirydate, Travel_Multiple_City.mpassportno, Travel_Multiple_City.mtraveltype,Travel_Multiple_City.mtravelmode,Travel_Multiple_City.mdeparture,Travel_Multiple_City.morigin,Travel_Multiple_City.maccom_name,Travel_Multiple_City.maccom_type,Travel_Multiple_City.Air_preference,convert(nvarchar(10),Travel_Multiple_City.Arrival_date,103) as Arrival_date,"
			    		+ " Travel_Multiple_City.Arrival_time,convert(nvarchar(10),Travel_Multiple_City.Departure_date,103) as Departure_date,"
			    		+ " Travel_Multiple_City.Departure_time ,Travel_Multiple_City.mhotel_Res,Travel_Multiple_City.mrent_Car,Travel_Multiple_City.mhotel_Name,"
			    		+ " Travel_Multiple_City.mhotel_City,Travel_Multiple_City.mpickup_details,Travel_Multiple_City.mdrop_Details"
			    		+ " from Travel_Multiple_City left outer join Travel_Desk_Travellers on Travel_Multiple_City.trav_id=Travel_Desk_Travellers.id and Travel_Desk_Travellers.id!=0"
			    		+ " left outer join emp_official_info on emp_official_info.PERNR=Travel_Desk_Travellers.PERNR"
			    		+ " where Travel_Multiple_City.req_id='"+requestForm.getOldid()+"'" ;
                ResultSet b1=ad.selectQuery(his1);
        		try {
        			while(b1.next())
        			{
        					TravelRequestForm emp=new TravelRequestForm();
        					emp.setMultiemployeeno(b1.getString("id"));
        					emp.setMultiCityId(Integer.parseInt(b1.getString("id")));
        					emp.setLocationId(b1.getString("location"));
        					emp.setAirline_Pref(b1.getString("air_preference"));
        					emp.setTrip_From_Date(b1.getString("Arrival_date"));
        					emp.setTrip_To_time(b1.getString("Arrival_Time"));
        					emp.setTrip_To_Date(b1.getString("Departure_date"));
        					emp.setTrip_To_time(b1.getString("Departure_time"));
        					emp.setHotel_Res(b1.getString("mhotel_Res"));
        					emp.setRent_Car(b1.getString("mrent_Car"));
        					emp.setHotel_Name(b1.getString("mhotel_Name"));
        					emp.setHotel_City(b1.getString("mhotel_City"));
        					emp.setPickup_Details(b1.getString("mpickup_Details"));
        					emp.setDrop_Details(b1.getString("mdrop_Details"));
        					emp.setAccom_type(b1.getString("maccom_type"));
        					emp.setAccom_name(b1.getString("maccom_name"));
        					emp.setOrigin(b1.getString("morigin"));
        					emp.setDeparture(b1.getString("mdeparture"));
        					emp.setTravelmode(b1.getString("mtravelmode"));
        					emp.setTraveltype(b1.getString("mtraveltype"));
        					emp.setPassportno(b1.getString("mpassportno"));
        					emp.setPassportexpirydate(b1.getString("mpassportexpirydate"));
        					emp.setVisano(b1.getString("mguest_Visano"));
        					
        					city.add(emp);
        				
        			}
        		} catch (SQLException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
        		
        		if(city.size()>0)
                	request.setAttribute("city", city);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location "); 
				try {
					while(rs11.next()) {
						locationList.add(rs11.getString("location_code"));
						locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
						
					}
					rs11.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				requestForm.setLocationIdList(locationList);
				requestForm.setLocationLabelList(locationLabelList);
				
		
		
				ArrayList divIdList=new ArrayList();
				ArrayList divLabelList=new ArrayList();
				ResultSet rs111 = ad.selectQuery("select * from division"); 
						try {
							while(rs111.next()) {
								divIdList.add(rs111.getString("div_code"));
								divLabelList.add(rs111.getString("div_desc"));
								
							}
							rs111.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						requestForm.setDivIdList(divIdList);
						requestForm.setDivLabelList(divLabelList);	
						
			
						
						String monthplan="select tm.id,tm.plan_date,tm.morigin,tm.mdestination,tm.mtravelmode,tm.mtraveldesk,tm.mtraveltype,tm.mremarks from Travel_MTP as tm,Travel_Desk as td where td.reqNo='"+ reqNo+"' and td.id=tm.req_id order by plan_date";
						List appList1 = new LinkedList();
				ResultSet rsAppr1 = ad.selectQuery(monthplan);
				try {
					while (rsAppr1.next()) {
						TravelRequestForm appr=new TravelRequestForm();
						
						String d[]=rsAppr1.getString("plan_date").split("-");
						String dateString = d[2]+"/"+d[1]+"/"+d[0];;
						
						appr.setPlandate(dateString);
						
						appr.setMtpId(rsAppr1.getInt("id"));
						
						//appr.setFromdate(rsAppr1.getString("plan_date"));
						appr.setPlanorigin(rsAppr1.getString("morigin"));
						appr.setPlandestination(rsAppr1.getString("mdestination"));
						appr.setTravelmode(rsAppr1.getString("mtravelmode"));
						appr.setTraveltype(rsAppr1.getString("mtraveltype"));
						appr.setTravel_desk_type(rsAppr1.getString("mtraveldesk"));
						appr.setPlanremarks(rsAppr1.getString("mremarks"));
						appList1.add(appr);
					}
					if(appList1.size()>0)
					request.setAttribute("city1", appList1);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
		requestForm.setMessage("");
		
	/*
			String app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from travel_request_history a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Travel' and a.Req_Id='"
					+ reqNo
					+ "' and e.PERNR=a.Requester_Name and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID and a.req_status='Pending'"
					+ "union "
					 +"select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from travel_request_history a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Travel' and a.Req_Id='"
						+ reqNo
						+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";*/

		/*} else {
		 app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from travel_request_history a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where  Req_Type='Travel' and a.Req_Id='"
				+ reqNo
				+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";*/
		String getdet="select CONVERT(varchar(10),submitted_date,103)+' '+ CONVERT(varchar(5),submitted_date,108) as submitted_date,CONVERT(varchar(10),approved_date,103)+' '+ CONVERT(varchar(5),approved_date,108) as approved_date,CONVERT(varchar(10),Reverted_on,103)+' '+ CONVERT(varchar(5),Reverted_on,108) as Reverted_on,CONVERT(varchar(10),rejected_date,103)+' '+ CONVERT(varchar(5),rejected_date,108) as rejected_date,* from travel_request_history where req_id='"+reqNo+"'";

	/*	String getdet="select * from travel_request_history where req_id='"+reqNo+"'";*/
		LinkedList appList = new LinkedList();
		ResultSet rsAppr = ad.selectQuery(getdet);
		String duplicate="";
		try {
			while (rsAppr.next()) {
				ApprovalsForm appr = new ApprovalsForm();
			/*	appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
				appr.setDesignation(rsAppr.getString("DSGSTXT"));*/
				appr.setRole(rsAppr.getString("Role"));
				
				String approvaStatus = rsAppr.getString("Req_Status");
				if (approvaStatus.equals("Pending")) {
					
					int emp =Integer.parseInt(rsAppr.getString("Requester_Name"));
					appr.setApproveDate("");
					appr.setComments("");
					appr.setApproveStatus("Submitted");
					
					String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
					ResultSet rr  =ad.selectQuery(empx);
					while(rr.next())
					{
						appr.setApprover(rr.getString("EMP_FULLNAME"));
						appr.setDesignation(rr.getString("DSGSTXT"));
						
						appr.setApproveDate(rsAppr.getString("submitted_date"));
					}
					
					
				}
				
					if (approvaStatus.equalsIgnoreCase("Travel Review")) {
					
					int emp =Integer.parseInt(rsAppr.getString("Requester_Name"));
					appr.setApproveDate("");
					appr.setComments("");
					appr.setApproveStatus(rsAppr.getString("Req_Status"));
					
					String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
					ResultSet rr  =ad.selectQuery(empx);
					while(rr.next())
					{
						appr.setApprover(rr.getString("EMP_FULLNAME"));
						appr.setDesignation(rr.getString("DSGSTXT"));
						appr.setApproveDate(rsAppr.getString("submitted_date"));
						appr.setComments(rsAppr.getString("Comments"));
					}
					
					
				}
				if (approvaStatus.equals("Approved")||approvaStatus.equalsIgnoreCase("Booking Accepted")||approvaStatus.equalsIgnoreCase("Bills Updated")||approvaStatus.equalsIgnoreCase("Travel Review")
						||approvaStatus.equalsIgnoreCase("Submitted To Finance")|| approvaStatus.equalsIgnoreCase("Received By finance")|| approvaStatus.equalsIgnoreCase("Verified By finance")||approvaStatus.equals("Completed")) {
					appr.setApproveStatus(rsAppr.getString("Req_Status"));
					
					int emp =Integer.parseInt(rsAppr.getString("Actual_Approver"));
					appr.setApproveDate(rsAppr.getString("approved_date"));
					appr.setComments(rsAppr.getString("Comments"));
					String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
					ResultSet rr  =ad.selectQuery(empx);
					while(rr.next())
					{
						appr.setApprover(rr.getString("EMP_FULLNAME"));
						appr.setDesignation(rr.getString("DSGSTXT"));
						
					}
				}
				
				if (approvaStatus.equals("Reverted")
						|| approvaStatus.equals("Completed")) {
					appr.setApproveStatus(rsAppr.getString("Req_Status"));
					int emp =Integer.parseInt(rsAppr.getString("Actual_Approver"));
					appr.setApproveDate(rsAppr.getString("Reverted_on"));
					appr.setComments(rsAppr.getString("Comments"));
					
					String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
					ResultSet rr  =ad.selectQuery(empx);
					while(rr.next())
					{
						appr.setApprover(rr.getString("EMP_FULLNAME"));
						appr.setDesignation(rr.getString("DSGSTXT"));
						
					}
				}
				if (approvaStatus.equals("Rejected")
						|| approvaStatus.equals("Cancelled")) {
					appr.setApproveStatus(rsAppr.getString("Req_Status"));
					int emp =Integer.parseInt(rsAppr.getString("Actual_Approver"));
					appr.setApproveDate(rsAppr.getString("rejected_date"));
					appr.setComments(rsAppr.getString("Comments"));
					String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"'";
					ResultSet rr  =ad.selectQuery(empx);
					while(rr.next())
					{
						appr.setApprover(rr.getString("EMP_FULLNAME"));
						appr.setDesignation(rr.getString("DSGSTXT"));
						
					}
				}
				/*if(duplicate!=null)
				if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))*/
					appList.add(appr);
					duplicate=rsAppr.getString("approved_date");
			}
			request.setAttribute("appList", appList);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
	
		return mapping.findForward("revertdomestic");
	
	
	}
	
	public ActionForward editMyRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws SQLException {

		//Created by Arun .Date:11-01-2017
		
		TravelRequestForm requestForm =  (TravelRequestForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int reqid=0;
		requestForm.setMessage("");
		requestForm.setMessage2("");
		
		String reqNo=request.getParameter("requstNo");
		requestForm.setOldrequestNumber(Integer.parseInt(reqNo));
		
		
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
		String dateNow = ft.format(dNow);
		
		requestForm.setTravel_Adult("0");
		requestForm.setTravel_Child("0");
		requestForm.setTravel_Infant("0");
		requestForm.setTravelRequestFor("One Way");
	
		
		requestForm.setReqDate(dateNow);
		
		synchronized (this) {	
			String getMaxNo = "select isnull(max(id),0) from Travel_Desk";
		ResultSet rs = ad.selectQuery(getMaxNo);
		try {
			if (rs.next()) {
				reqid = rs.getInt(1)+1;
			}
			requestForm.setId(reqid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		String s ="Insert into Travel_Desk(id,prefix,Pernr) "
			+ " values('"+reqid+"','TN','"+user.getEmployeeNo()+"')";
		int i = ad.SqlExecuteUpdate(s);
		if(i>0)
		{
		requestForm.setId(reqid);		
		}
		}
		String lis="select CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1, CONVERT(varchar(10),billdate,103) as billdate1 ,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as  bookeddate1,"
				+ "  CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1   ,CONVERT(varchar(10),Depart_On,103)  as departon,"
				+ "CONVERT(varchar(10),Return_On,103)  as returnon,* from Travel_Desk where reqNo='"+reqNo+"' ";
		ResultSet ff=ad.selectQuery(lis);
		try {
			if(ff.next())
			{
				String re[]=empDet(Integer.toString(ff.getInt("pernr")));
				requestForm.setEmployeeno(user.getEmployeeNo());
				requestForm.setEmployeeName(re[0]);
				requestForm.setP_name(re[0]);
				requestForm.setLocationId(re[1]);
				requestForm.setDepartment(re[2]);
				requestForm.setP_dept(re[2]);
				requestForm.setDesignation(re[3]); 
				requestForm.setUserAge(re[5]);
				requestForm.setUsermailId(re[6]);
				requestForm.setP_email(re[6]);
				requestForm.setUserGender(re[7]);
				
				String pe[]=empPERDet(Integer.toString(ff.getInt("pernr")));
				requestForm.setUsermobno(pe[0]);
				requestForm.setP_cont(pe[0]);
				requestForm.setUserpassportno(pe[1]);
				requestForm.setUserpassportplace(pe[2]);
				requestForm.setUserPersonalmailId(pe[5]);
				if(!pe[3].contains("1900"))
					requestForm.setUserpassportissuedate(pe[3]); 
					else
				    requestForm.setUserpassportissuedate(""); 
			    if(!pe[4].contains("1900"))
					requestForm.setUserpassportexpirydate(pe[4]); 
					else
				    requestForm.setUserpassportexpirydate(""); 
			
				requestForm.setReqType(ff.getString("Req_type"));
				requestForm.setTypeOfTravel(ff.getString("Type_of_travel"));
				requestForm.setTravelFor(ff.getString("Travel_for"));
				requestForm.setTravelRequestFor(ff.getString("Travel_Request_for"));
				requestForm.setModeOfTravel(ff.getString("Mode_of_travel"));
				requestForm.setFromPlace(ff.getString("From_place"));
				requestForm.setDepartOn("");
				requestForm.setDepartTime("");
				requestForm.setReturnTime("");
				requestForm.setReturnOn("");
				requestForm.setDepartRemarks(ff.getString("Depart_Time_remarks"));
				requestForm.setReturnRemarks(ff.getString("Return_Time_remarks"));
				requestForm.setToPlace(ff.getString("To_place"));
			    requestForm.setVia(ff.getString("via"));
			    requestForm.setRemarks(ff.getString("Remarks"));
			    requestForm.setPurposeOfVisit(ff.getString("Purpose"));
			    requestForm.setReturnOn(ff.getString("returnon"));
			    requestForm.setTravel_Adult(ff.getString("Travel_Adult"));
			    requestForm.setTravel_Child(ff.getString("Travel_Child"));
			    requestForm.setTravel_Infant(ff.getString("travel_Infant"));
			    requestForm.setPurposetext(ff.getString("purposetext"));
			    requestForm.setTravel_Days(ff.getString("travel_Days"));
			    requestForm.setAirline_Pref(ff.getString("airline_Pref"));
			    requestForm.setAirline_Just(ff.getString("airline_Just"));
			    requestForm.setSpon_div(ff.getString("spon_div"));
			    requestForm.setBud_code(ff.getString("bud_code"));
			    requestForm.setEst_trip_cose(ff.getString("est_trip_cose"));
			    requestForm.setHotel_Res(ff.getString("hotel_Res"));
			    requestForm.setOnduty_Req(ff.getString("onduty_Req"));
			    requestForm.setRent_Car(ff.getString("rent_Car"));
			    requestForm.setPickup_Details(ff.getString("pickup_Details"));
			    requestForm.setDrop_Details(ff.getString("drop_Details"));
			    requestForm.setTrip_Advance(ff.getString("trip_Advance"));
			    requestForm.setTrip_Amt(ff.getString("trip_Amt"));
			    requestForm.setTrip_Currency(ff.getString("trip_Currency"));
			    requestForm.setTrip_From_Date(ff.getString("trip_From_Date"));
			    requestForm.setTrip_To_Date(ff.getString("trip_To_Date"));
			    requestForm.setTrip_From_Time(ff.getString("trip_From_Time"));
			    requestForm.setTrip_To_time(ff.getString("trip_To_time"));
			    requestForm.setTrip_Priority(ff.getString("trip_Priority"));
			    requestForm.setPurposetype(ff.getString("purposetype"));
			    requestForm.setTravelmode(ff.getString("travelmode"));
			    requestForm.setTraveltype(ff.getString("Type_of_travel"));
			    requestForm.setService_class(ff.getString("service_class"));
			    requestForm.setTravelagentname(ff.getString("travelagentname"));
			    requestForm.setBillno(ff.getString("billno"));
			    requestForm.setBilldate(ff.getString("billdate1"));
			    requestForm.setBillamount(ff.getString("billamount"));
			    requestForm.setBookingstatus(ff.getString("bookingstatus"));
			    requestForm.setBookeddate(ff.getString("bookeddate1"));
			    requestForm.setReqDate(ff.getString("Created_date1"));
			    requestForm.setBookedby(Empname(ff.getString("bookedby")));
			    requestForm.setConfirmdate(ff.getString("confirmdate1"));
			    requestForm.setConfirmby(Empname(ff.getString("confirmby")));
			    requestForm.setConfirmstatus(ff.getString("confirmstatus"));
			    requestForm.setLocid(ff.getString("locid"));
			    requestForm.setTravel_desk_type(ff.getString("travel_desk_type"));
			    requestForm.setFilepath(ff.getString("path"));
			    requestForm.setTravel_desk_type(ff.getString("travel_desk_type"));
			    
			    requestForm.setOldid(ff.getInt("id"));
			    requestForm.setOldrequestNumber(ff.getInt("reqno"));
			    
			    String ins="insert into Travel_Desk_Travellers (req_id,PERNR,Req_type,Passport_no,Place_issue,Date_of_issue,Date_of_expiry,Name_of_guest,Contact_guest,Age_guest,Gender_guest,Email_guest,file_name,path,guest_Title,guest_Visano,guest_DOB,guest_Meal,guest_Company,guest_Type,travelmode,on_duty,departOn,departTime,returnOn,returnTime,hotel_Res,hotel_Name,hotel_City,rent_Car,pickup_Details,drop_Details) "
			    		+ "select '"+requestForm.getId()+"',PERNR,Req_type,Passport_no,Place_issue,Date_of_issue,Date_of_expiry,Name_of_guest,Contact_guest,Age_guest,Gender_guest,Email_guest,file_name,path,guest_Title,guest_Visano,guest_DOB,guest_Meal,guest_Company,guest_Type,travelmode,on_duty,departOn,departTime,returnOn,returnTime,hotel_Res,hotel_Name,hotel_City,rent_Car,pickup_Details,drop_Details "
			    		+ "from Travel_Desk_Travellers where req_id='"+requestForm.getOldid()+"' ";
			    int t = ad.SqlExecuteUpdate(ins);
			    
        		ArrayList city = new ArrayList();
			    String his1="select isnull(convert(nvarchar(10),Travel_Multiple_City.trav_id)+'-'+emp_official_info.EMP_FULLNAME,0) as id,Travel_Multiple_City.Location,"
			    		+ " Travel_Multiple_City.mpassportexpirydate,mguest_Visano, Travel_Multiple_City.mpassportexpirydate, Travel_Multiple_City.mpassportno, Travel_Multiple_City.mtraveltype,Travel_Multiple_City.mtravelmode,Travel_Multiple_City.mdeparture,Travel_Multiple_City.morigin,Travel_Multiple_City.maccom_name,Travel_Multiple_City.maccom_type,Travel_Multiple_City.Air_preference,convert(nvarchar(10),Travel_Multiple_City.Arrival_date,103) as Arrival_date,"
			    		+ " Travel_Multiple_City.Arrival_time,convert(nvarchar(10),Travel_Multiple_City.Departure_date,103) as Departure_date,"
			    		+ " Travel_Multiple_City.Departure_time ,Travel_Multiple_City.mhotel_Res,Travel_Multiple_City.mrent_Car,Travel_Multiple_City.mhotel_Name,"
			    		+ " Travel_Multiple_City.mhotel_City,Travel_Multiple_City.mpickup_details,Travel_Multiple_City.mdrop_Details"
			    		+ " from Travel_Multiple_City left outer join Travel_Desk_Travellers on Travel_Multiple_City.trav_id=Travel_Desk_Travellers.id and Travel_Desk_Travellers.id!=0"
			    		+ " left outer join emp_official_info on emp_official_info.PERNR=Travel_Desk_Travellers.PERNR"
			    		+ " where Travel_Multiple_City.req_id='"+requestForm.getOldid()+"'" ;
                ResultSet b1=ad.selectQuery(his1);
        		try {
        			while(b1.next())
        			{
        					TravelRequestForm emp=new TravelRequestForm();
        					emp.setMultiemployeeno(b1.getString("id"));
        					emp.setLocationId(b1.getString("location"));
        					emp.setAirline_Pref(b1.getString("air_preference"));
        					emp.setTrip_From_Date(b1.getString("Arrival_date"));
        					emp.setTrip_To_time(b1.getString("Arrival_Time"));
        					emp.setTrip_To_Date(b1.getString("Departure_date"));
        					emp.setTrip_To_time(b1.getString("Departure_time"));
        					emp.setHotel_Res(b1.getString("mhotel_Res"));
        					emp.setRent_Car(b1.getString("mrent_Car"));
        					emp.setHotel_Name(b1.getString("mhotel_Name"));
        					emp.setHotel_City(b1.getString("mhotel_City"));
        					emp.setPickup_Details(b1.getString("mpickup_Details"));
        					emp.setDrop_Details(b1.getString("mdrop_Details"));
        					emp.setAccom_type(b1.getString("maccom_type"));
        					emp.setAccom_name(b1.getString("maccom_name"));
        					emp.setOrigin(b1.getString("morigin"));
        					emp.setDeparture(b1.getString("mdeparture"));
        					emp.setTravelmode(b1.getString("mtravelmode"));
        					emp.setTraveltype(b1.getString("mtraveltype"));
        					emp.setPassportno(b1.getString("mpassportno"));
        					emp.setPassportexpirydate(b1.getString("mpassportexpirydate"));
        					emp.setVisano(b1.getString("mguest_Visano"));
        					
        					city.add(emp);
        				
        			}
        		} catch (SQLException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
        		
        		if(city.size()>0)
                	request.setAttribute("city", city);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location "); 
				try {
					while(rs11.next()) {
						locationList.add(rs11.getString("location_code"));
						locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
						
					}
					rs11.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				requestForm.setLocationIdList(locationList);
				requestForm.setLocationLabelList(locationLabelList);
				
				
				ArrayList travelTypeList = new ArrayList();
				ArrayList travelModeList  =  new ArrayList();
				
				
				String xyz = "select * from travel_mode";
				
			ResultSet getIt = ad.selectQuery(xyz);
			while(getIt.next())
			{
				travelTypeList.add(getIt.getString("mode"));
			}
			
			requestForm.setTraveltypeList(travelTypeList);
			requestForm.setTraveltypeLabelList(travelModeList);
						
		
		
				ArrayList divIdList=new ArrayList();
				ArrayList divLabelList=new ArrayList();
				ResultSet rs111 = ad.selectQuery("select * from division"); 
						try {
							while(rs111.next()) {
								divIdList.add(rs111.getString("div_code"));
								divLabelList.add(rs111.getString("div_desc"));
								
							}
							rs111.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						requestForm.setDivIdList(divIdList);
						requestForm.setDivLabelList(divLabelList);	
						
			
						
						String monthplan="select tm.id,tm.plan_date,tm.morigin,tm.mdestination,tm.mtravelmode,tm.mtraveltype,tm.mremarks from Travel_MTP as tm,Travel_Desk as td where td.reqNo='"+ reqNo+"' and td.id=tm.req_id order by plan_date";
						List appList1 = new LinkedList();
				ResultSet rsAppr1 = ad.selectQuery(monthplan);
				try {
					while (rsAppr1.next()) {
						TravelRequestForm appr=new TravelRequestForm();
						
						String d[]=rsAppr1.getString("plan_date").split("-");
						String dateString = d[2]+"/"+d[1]+"/"+d[0];;
						
						appr.setPlandate(dateString);
						
						
						
						//appr.setFromdate(rsAppr1.getString("plan_date"));
						appr.setId(rsAppr1.getInt("id"));
						appr.setPlanorigin(rsAppr1.getString("morigin"));
						appr.setPlandestination(rsAppr1.getString("mdestination"));
						appr.setTravelmode(rsAppr1.getString("mtravelmode"));
						appr.setTraveltype(rsAppr1.getString("mtraveltype"));
						appr.setPlanremarks(rsAppr1.getString("mremarks"));
						appr.setMtravelmode1(rsAppr1.getString("mtravelmode"));
						appr.setMtraveltype1(rsAppr1.getString("mtraveltype"));
						appList1.add(appr);
					}
					if(appList1.size()>0)
					request.setAttribute("city1", appList1);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
		requestForm.setMessage("");
		
		
	
		return mapping.findForward("editdomestic");
	
	}	
	
	public ActionForward editMyMonthlyPlanrequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		TravelRequestForm requestForm =  (TravelRequestForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		String id = request.getParameter("reqId");
		String mtp = "select * from travel_mtp where id="+id+"";
		ResultSet tp = ad.selectQuery(mtp);
		while(tp.next())
		{
			requestForm.setPlandate(tp.getString("plan_date"));
			requestForm.setPlanorigin(tp.getString("morigin"));
			requestForm.setPlandestination(tp.getString("mdestination"));
			requestForm.setTravelmode(tp.getString("mtravelmode"));
			requestForm.setTraveltype(tp.getString("mtraveltype"));
			requestForm.setPlanremarks(tp.getString("mremarks"));
			requestForm.setId(tp.getInt("id"));
			
			
			
		}
		
		
		return mapping.findForward("editmypage");
	}
	
	public ActionForward updateMonthlyTravelPlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		
		TravelRequestForm requestForm =  (TravelRequestForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		String id =request.getParameter("reqId");
		 String[] mdate1=requestForm.getPlandate().split("/");
		   String datep1=mdate1[2]+"-"+mdate1[1]+"-"+mdate1[0];
		String upd = "update travel_mtp set plan_date='"+datep1+"',morigin='"+requestForm.getPlanorigin()+"',mdestination='"+requestForm.getPlandestination()+"',mtravelmode='"+requestForm.getTravelmode()+"',mtraveltype='"+requestForm.getTraveltype()+"',mremarks='"+requestForm.getPlanremarks()+"' where id="+id+"";
		int i  =ad.SqlExecuteUpdate(upd);
		if(i>0)
		{
			requestForm.setMessage("Successfully Updated");
		}
		else
		{
			requestForm.setMessage2("Error while updating");
		}
		return mapping.findForward("editmypage");
		
	}
	
	public String Empname(String a)
	{
		//Created by Arun .Date:15-11-2016
		
		String b = "";
		if(a==null)
		{
		  return b;	
		}
		
		if(!a.equalsIgnoreCase(""))
		{	
			
		
		String emp = "select EMP_FULLNAME from emp_official_info where pernr = '"+a+"'";
		ResultSet rs = ad.selectQuery(emp);
		try {
			if(rs.next())
			{
				b=rs.getString("emp_fullname"); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
		return b;
		
	}
	
	private String[] empDet( String empno) {
		//Created by Arun .Date:15-11-2016
		
 		EssDao ad = new EssDao();
		String emp=" select emp.eMP_FULLNAME,emp.LOCID,dep.DPTSTXT,desg.DSGSTXT,convert(nvarchar(10),emp.doj,103) as doj,datediff(year,dob,getdate()) as age,emp.Email_id, case when emp.sex='M' then 'Male' else 'Female' end as gender from Emp_official_info emp "
				+ " , Department dep ,Designation desg  where emp.dPTID = dep.DPTID and emp.pERNR='"+empno+"' "
						+ " and desg.DSGID= emp.dSGID  ";
		ResultSet ae=ad.selectQuery(emp);

		String[] a = new String[10];
	
		try {
			while(ae.next())
			{
			
			

			    a[0]=ae.getString("EMP_FULLNAME");
			    a[1]=ae.getString("LOCID");
			    a[2]=ae.getString("DPTSTXT");
			    a[3]=ae.getString("DSGSTXT");
			    a[4]=ae.getString("doj");
			    a[5]=ae.getString("age");
			    a[6]=ae.getString("EMAIL_ID");
			    a[7]=ae.getString("gender");
      


			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
		}
	
	
	private String[] empPERDet( String empno) {

		//Created by Arun .Date:15-11-2016
		
 		EssDao ad = new EssDao();
		String emp=" select mobile_no,passport_no,place_of_issue_of_passport,convert(nvarchar(10),convert(date,date_of_issue_of_passp),103) as issue,"
				+ "convert(nvarchar(10),convert(date,date_of_expiry_of_passport),103) as exp,email_address from emp_personal_info where user_id='"+empno+"'";
		ResultSet ae=ad.selectQuery(emp);

		String[] a = new String[10];
	
		try {
			while(ae.next())
			{
			   a[0]=ae.getString("mobile_no");
			    a[1]=ae.getString("passport_no");
			    a[2]=ae.getString("place_of_issue_of_passport");
			    a[3]=ae.getString("issue");
			    a[4]=ae.getString("exp");
			    a[5]=ae.getString("email_address");
			 
      


			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
		}
	
	
	public ActionForward saveDomestic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		//Created by Arun .Date:15-11-2016
		
		TravelRequestForm travel = (TravelRequestForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		synchronized (this) {
		
		
		//check requstno exist or not
		 int newReqestnumber=0;
		 int reqestNo=travel.getRequestNumber();
		 
		 String path="";
		 String travel_Desk=request.getParameter("travel_desk");
		 boolean checkReqno=false;
		 String checkReqNo="select max(reqNo) from Travel_Desk";
       ResultSet rsCheck=ad.selectQuery(checkReqNo);
       try{
       while(rsCheck.next()){
    	   newReqestnumber=rsCheck.getInt(1);
		 newReqestnumber=newReqestnumber+1;
		travel.setRequestNumber(newReqestnumber);
		}
       }catch (Exception e) {
			e.printStackTrace();
		}
       
       

		FormFile documentFile = travel.getDocumentFile();
		String documentName = documentFile.getFileName();
		String filePath = "";
       
   	String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
	int filesize = documentFile.getFileSize();
	if(!documentName.equalsIgnoreCase(""))
	{	
	
	if ((ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")
			|| ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("jpg")
			|| ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("xls") || ext
				.equalsIgnoreCase("txt")) && (filesize < 1048576)) {
		try {
			byte[] size = documentFile.getFileData();
			if (!documentName.equalsIgnoreCase("")) {
				int length = documentName.length();
				int dot = documentName.lastIndexOf(".");
				String fileName = documentFile.getFileName();
				String extension = documentName.substring(dot, length);
				filePath = getServlet().getServletContext().getRealPath(
						"jsp/EMicro Files/ESS/Travel Request/UploadFiles");
				InputStream in = ConnectionFactory.class.getClassLoader()
						.getResourceAsStream("db.properties");
				Properties props = new Properties();
				props.load(in);
				in.close();
				String uploadFilePath = props
						.getProperty("file.uploadFilePath");
				System.out.println("required filepath=" + uploadFilePath
						+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+travel.getRequestNumber());
				filePath = uploadFilePath
						+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+travel.getRequestNumber();
				File destinationDir = new File(filePath);
				if (!destinationDir.exists()) {
					destinationDir.mkdirs();
				}
				if (!fileName.equals("")) {
					File fileToCreate = new File(filePath, fileName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);
						fileOutStream.write(documentFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
					}
				}
				try {
					String filePath1 = "E:/EMicro Files/ESS/Travel Request/UploadFiles/"+travel.getRequestNumber();
					byte[] fileData1 = documentFile.getFileData();
					InputStream is = new ByteArrayInputStream(fileData1);
					// boolean status=saveDataInJRS(is,fileName);
					// System.out.println("File status="+status);

					File destinationDir1 = new File(filePath1);
					if (!destinationDir1.exists()) {
						destinationDir1.mkdirs();
					}
					if (!fileName.equals("")) {
						File fileToCreate1 = new File(filePath1, fileName);
						if (!fileToCreate1.exists()) {
							FileOutputStream fileOutStream1 = new FileOutputStream(
									fileToCreate1);
							fileOutStream1
									.write(documentFile.getFileData());
							fileOutStream1.flush();
							fileOutStream1.close();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		
		
		
	path = "/EMicro Files/ESS/Travel Request/UploadFiles/"+travel.getRequestNumber()+"/"+documentFile.getFileName();

	} else {
		travel.setMessage2("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
		travel.setMessage("");
	}
	}
		
		String departdate="";
		String returndate="";
		String domestic="";
		String trip_start="";
		String trip_end="";
		
		
		if(travel.getDepartOn().contains("/"))
		{	
	    String depart[]=travel.getDepartOn().split("/");
		departdate=depart[2]+"-"+depart[1]+"-"+depart[0];
		}
		

		if(travel.getReturnOn().contains("/"))
		{
		String returnd[]=travel.getReturnOn().split("/");
		returndate=returnd[2]+"-"+returnd[1]+"-"+returnd[0];
		}
	/*	else
		{
			returndate=null;
		}*/
		
		if(returndate!="")
		{
		    
		domestic="insert into Travel_Desk(accom_type,accom_name,id,reqNo,PERNR,Req_type,"
		 				+ "Type_of_travel,Travel_for,Mode_of_travel,Travel_Request_for,"
		 				+ "From_place,Depart_On,Preferred_depart_time,Depart_Time_remarks,To_place,Return_On,Preferred_return_time,Return_Time_remarks,Via,"
		 				+ "Purpose,Remarks,Req_date,Req_status,travel_Adult,travel_Child,travel_Infant,purposetext,travel_Days,airline_Pref,airline_Just"
		 				+ " ,spon_div,bud_code,est_trip_cose,hotel_Res,onduty_Req,rent_Car,pickup_Details,drop_Details,trip_Advance"
		 				+ " ,trip_Amt,trip_Currency,trip_From_Date,trip_From_Time,trip_To_Date,trip_To_time,trip_Priority,purposetype,travelmode,service_class,hotel_Name,hotel_City,locid,old_reqNo,travel_desk_type,p_name,p_dept,p_cont,p_email,path)"
		 				+ " values('"+travel.getAccom_type()+"','"+travel.getAccom_name()+"','"+travel.getId()+"' ,'"+travel.getRequestNumber()+"','"+user.getEmployeeNo()+"','Travel',"
		 				+ " '"+travel.getTraveltype()+"','"+travel.getTravelFor()+"',"
				+ "'"+travel.getTraveltype()+"','"+travel.getTravelRequestFor()+"','"+travel.getFromPlace()+"','"+departdate+"','"+travel.getDepartTime()+"','"+travel.getDepartRemarks()+"','"+travel.getToPlace()+"','"+returndate+"',"
				+ "'"+travel.getReturnTime()+"','"+travel.getReturnRemarks()+"','"+travel.getVia()+"','"+travel.getPurposeOfVisit()+"','"+travel.getRemarks()+"',getdate(),'Pending','"+travel.getTravel_Adult()+"','"+travel.getTravel_Child()+"',"
						+ "'"+travel.getTravel_Infant()+"','"+travel.getPurposetext()+"','"+travel.getTravel_Days()+"' ,'"+travel.getAirline_Pref()+"','"+travel.getAirline_Just()+"'"
					+ ",'"+travel.getSpon_div()+"','"+travel.getBud_code()+"','"+travel.getEst_trip_cose()+"','"+travel.getHotel_Res()+"','"+travel.getOnduty_Req()+"','"+travel.getRent_Car()+"','"+travel.getPickup_Details()+"' "
					+ ",'"+travel.getDrop_Details()+"' ,'"+travel.getTrip_Advance()+"',"
							+ "'"+travel.getTrip_Amt()+"','"+travel.getTrip_Currency()+"',"
							+ "'"+trip_start+"','09:00AM','"+trip_end+"','06:00PM','"+travel.getTrip_Priority()+"' ,'"+travel.getPurposetype()+"' ,'"+travel.getTravelmode()+"','"+travel.getService_class()+"','"+travel.getHotel_Name()+"','"+travel.getHotel_City()+"','"+travel.getLocid()+"','"+travel.getOldrequestNumber()+"','"+travel.getTravel_desk_type()+"', "
							+ "'"+travel.getP_name()+"','"+travel.getP_dept()+"','"+travel.getP_cont()+"','"+travel.getP_email()+"','"+path+"' " 
					+ " )";
		}
		else
		{
			domestic="insert into Travel_Desk(accom_type,accom_name,id,reqNo,PERNR,Req_type,"
	 				+ "Type_of_travel,Travel_for,Mode_of_travel,Travel_Request_for,"
	 				+ "From_place,Depart_On,Preferred_depart_time,Depart_Time_remarks,To_place,Return_On,Preferred_return_time,Return_Time_remarks,Via,"
	 				+ "Purpose,Remarks,Req_date,Req_status,travel_Adult,travel_Child,travel_Infant,purposetext,travel_Days,airline_Pref,airline_Just"
	 				+ " ,spon_div,bud_code,est_trip_cose,hotel_Res,onduty_Req,rent_Car,pickup_Details,drop_Details,trip_Advance"
	 				+ " ,trip_Amt,trip_Currency,trip_From_Date,trip_From_Time,trip_To_Date,trip_To_time,trip_Priority,purposetype,travelmode,service_class,hotel_Name,hotel_City,locid,old_reqNo,travel_desk_type,p_name,p_dept,p_cont,p_email,path)"
	 				+ " values('"+travel.getAccom_type()+"','"+travel.getAccom_name()+"','"+travel.getId()+"' ,'"+travel.getRequestNumber()+"','"+user.getEmployeeNo()+"','Travel',"
	 				+ " '"+travel.getTraveltype()+"','"+travel.getTravelFor()+"',"
			+ "'"+travel.getTraveltype()+"','"+travel.getTravelRequestFor()+"','"+travel.getFromPlace()+"','"+departdate+"','"+travel.getDepartTime()+"','"+travel.getDepartRemarks()+"','"+travel.getToPlace()+"',null,"
			+ "'"+travel.getReturnTime()+"','"+travel.getReturnRemarks()+"','"+travel.getVia()+"','"+travel.getPurposeOfVisit()+"','"+travel.getRemarks()+"',getdate(),'Pending','"+travel.getTravel_Adult()+"','"+travel.getTravel_Child()+"',"
					+ "'"+travel.getTravel_Infant()+"','"+travel.getPurposetext()+"','"+travel.getTravel_Days()+"' ,'"+travel.getAirline_Pref()+"','"+travel.getAirline_Just()+"'"
				+ ",'"+travel.getSpon_div()+"','"+travel.getBud_code()+"','"+travel.getEst_trip_cose()+"','"+travel.getHotel_Res()+"','"+travel.getOnduty_Req()+"','"+travel.getRent_Car()+"','"+travel.getPickup_Details()+"' "
				+ ",'"+travel.getDrop_Details()+"' ,'"+travel.getTrip_Advance()+"',"
						+ "'"+travel.getTrip_Amt()+"','"+travel.getTrip_Currency()+"',"
						+ "'"+trip_start+"','09:00AM','"+trip_end+"','06:00PM','"+travel.getTrip_Priority()+"' ,'"+travel.getPurposetype()+"' ,'"+travel.getTravelmode()+"','"+travel.getService_class()+"','"+travel.getHotel_Name()+"','"+travel.getHotel_City()+"','"+travel.getLocid()+"','"+travel.getOldrequestNumber()+"','"+travel.getTravel_desk_type()+"', "
						+ "'"+travel.getP_name()+"','"+travel.getP_dept()+"','"+travel.getP_cont()+"','"+travel.getP_email()+"','"+path+"' " 
				+ " )";
		}
		int t=ad.SqlExecuteUpdate(domestic);
		
		
			
		
		
		
		
		if(travel.getOldrequestNumber()!=0)
		{
		 String  up = "update travel_desk set req_status='Rescheduled' where reqNo='"+travel.getOldrequestNumber()+"' ";
		 int k = ad.SqlExecuteUpdate(up);
		 
		 String updateSql= "update OnDuty_details set Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',OnDuty_status='2',Approver_Status='Self Cancelled',approved_date='',rejected_date=getdate(),comments='rescheduled' "
		 		+ "where travel_no='"+travel.getOldrequestNumber()+"'";
			int recordChange=ad.SqlExecuteUpdate(updateSql);
		 
		 
		 String up1=" delete from all_request  where Req_Type='Travel' and Req_Id='"+travel.getOldrequestNumber()+"' and req_status='Pending'";
		 int k1 = ad.SqlExecuteUpdate(up1);		 
		}
		
		
		
		String ab ="delete from travel_desk where id='"+travel.getId()+"' and reqno is null ";
		int ib = ad.SqlExecuteUpdate(ab);
		
		String[] mlocl=request.getParameterValues("mlocl");
		String[] mairpl=request.getParameterValues("mairpl");
		String[] marrvdatel=request.getParameterValues("marrvdatel");
		String[] marrtimel=request.getParameterValues("marrtimel");
		String[] mdeptdatel=request.getParameterValues("mdeptdatel");
		String[] mdepttimel=request.getParameterValues("mdepttimel");
		
		
		String[] userlistIdl=request.getParameterValues("userlistIdl");
		String[] mhotel_Resl=request.getParameterValues("mhotel_Resl");
		String[] mrent_Carl=request.getParameterValues("mrent_Carl");
		String[] mhotel_Namel=request.getParameterValues("mhotel_Namel");
		String[] mhotel_Cityl=request.getParameterValues("mhotel_Cityl");
		String[] mpickup_Detailsl=request.getParameterValues("mpickup_Detailsl");
		String[] mdrop_Detailsl=request.getParameterValues("mdrop_Detailsl");
		
		String[] maccom_typel=request.getParameterValues("maccom_typel");
		String[] maccom_namel=request.getParameterValues("maccom_namel");
		String[] moriginl=request.getParameterValues("moriginl");
		String[] mdeparturel=request.getParameterValues("mdeparturel");
		
		String[] mtravelmodel=request.getParameterValues("mtravelmodel");
		String[] mtraveltypel=request.getParameterValues("mtraveltypel");
		
		

		String[] mpassportnol=request.getParameterValues("mpassportnol");
		String[] mpassportexpirydatel=request.getParameterValues("mpassportexpirydatel");
		String[] mguest_Visanol=request.getParameterValues("mguest_Visanol");
		
		
		
			
		String date1;
		String date2;
		
		if(mlocl!=null)
		{	
		for(int i=0;i<mlocl.length;i++)
		{

		    String marrvdatel1[]=marrvdatel[i].split("/");
		    date1=marrvdatel1[2]+"-"+marrvdatel1[1]+"-"+marrvdatel1[0];
			

		    String marrvdatel2[]=mdeptdatel[i].split("/");
		    date2=marrvdatel2[2]+"-"+marrvdatel2[1]+"-"+marrvdatel2[0];
		    
		    
		    if(userlistIdl[i].contains("-"))
		    {
		    String[] a=userlistIdl[i].split("-");
		    userlistIdl[i]=a[0];
		    }
		    
		    if(mpassportexpirydatel[i]!=null && !mpassportexpirydatel[i].equalsIgnoreCase("null"))
		    {	
		    if(mpassportexpirydatel[i].contains("/"))
		    {	
		    String pase[]=mpassportexpirydatel[i].split("/");
		    mpassportexpirydatel[i]=pase[2]+"-"+pase[1]+"-"+pase[0];
		    }
		    }
		    
		    
			String a="insert into Travel_Multiple_City(req_id,Location,Air_preference,Arrival_date,Arrival_time,Departure_date,Departure_time,Created_by,"
					+ " trav_id,mhotel_Res,mrent_Car,mhotel_Name,mhotel_City,mpickup_Details,mdrop_Details,maccom_type,maccom_name,morigin,mdeparture,mtravelmode,mtraveltype"
					+ " ,mpassportno,mpassportexpirydate,mguest_Visano) values"
					+ "( '"+travel.getId()+"','"+mlocl[i]+"','"+mairpl[i]+"','"+date1+"','"+marrtimel[i]+"'"
					+ ", '"+date2+"','"+mdepttimel[i]+"','"+user.getEmployeeNo()+"', '"+userlistIdl[i]+"','"+mhotel_Resl[i]+"','"+mrent_Carl[i]+"',"
					+ " '"+mhotel_Namel[i]+"','"+mhotel_Cityl[i]+"','"+mpickup_Detailsl[i]+"','"+mdrop_Detailsl[i]+"','"+maccom_typel[i]+"','"+maccom_namel[i]+"','"+moriginl[i]+"','"+mdeparturel[i]+"',"
							+ " '"+mtravelmodel[i]+"','"+mtraveltypel[i]+"','"+mpassportnol[i]+"','"+mpassportexpirydatel[i]+"','"+mguest_Visanol[i]+"' )";
			int ia = ad.SqlExecuteUpdate(a);
			
		}
		}
		
		
		String[] plandate=request.getParameterValues("mdate");
		String[] morigin11=request.getParameterValues("morigin11");
		String[] mdestination1=request.getParameterValues("mdestination1");
		
		String[] mtravelmode12=request.getParameterValues("mtravelmode1");
		String[] mtraveltype12=request.getParameterValues("mtraveltype1");
		String[] mtraveldesk12=request.getParameterValues("mtraveldesk1");
		String[] mremarks1=request.getParameterValues("mremarks1");
		
		String datep1;
		
		if(plandate!=null)
		{	
		for(int i=0;i<plandate.length;i++)
		{

		    String mdate1[]=plandate[i].split("/");
		    datep1=mdate1[2]+"-"+mdate1[1]+"-"+mdate1[0];
				    
		    
			String pa="insert into Travel_MTP(req_id,plan_date,morigin,mdestination,mtravelmode,mtraveltype,mtraveldesk,mremarks)values"
					+ "( '"+travel.getId()+"','"+datep1+"','"+morigin11[i]+"','"+mdestination1[i]+"','"+mtravelmode12[i]+"','"+mtraveltype12[i]+"','"+mtraveldesk12[i]+"','"+mremarks1[i]+"')";
			int pa1 = ad.SqlExecuteUpdate(pa);
			
		}
		}
		   
		
		
		if(t>0)
		{
			travel.setMessage("Request no. "+travel.getRequestNumber()+" submitted successfully");
			//insert into all request
			
			EMailer email = new EMailer();
			String approver="";
			String lApprover="";
			String pApprover="";
			String approvermail="";
			String getApproverID="";
			if(travel.getTravelFor()!=null)
			{
				if(travel.getTravelFor().equalsIgnoreCase("On_behalf"))
				{
					 getApproverID="select top 1 ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where "
							+ "ess.employeeNumber='"+user.getEmployeeNo()+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId order by ess.Priority desc";
					
				}else
				{
					 getApproverID="select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where "
								+ "ess.employeeNumber='"+user.getEmployeeNo()+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId";
				}
			}
			
			/*String getApproverID="select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where "
					+ "ess.employeeNumber='"+user.getEmployeeNo()+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId";*/
			ResultSet approverRS=ad.selectQuery(getApproverID);
			try {
				if(approverRS.next()){
					
						approver = approverRS.getString("ApproverId");
						approver = email.getApproverName(approver);
						lApprover = "";
						pApprover = approver;
						approvermail=approverRS.getString("EMAIL_ID");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			String addApprover="update Travel_Desk set Last_approver='No',Pending_approver='"+pApprover+"' where reqNo='"+travel.getRequestNumber()+"' ";
			int p=ad.SqlExecuteUpdate(addApprover);
			
			
			
	
				
				boolean existStatus=false;
				String reqType="";
				String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' group by reqType";
				ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
				try {
					while(rsCheckInEss.next())
					{
						int number=rsCheckInEss.getInt(1);
						reqType=rsCheckInEss.getString("reqType");
						if(number==0)
						{
							existStatus=false;
						}else{
							existStatus=true;
						}
					}
				
				if(existStatus==true){
					if(reqType.equalsIgnoreCase("sequential"))
					{
					/*String getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
					*/
						String getApproverDesig="";
						if(travel.getTravelFor()!=null)
						{
							if(travel.getTravelFor().equalsIgnoreCase("On_behalf"))
							{
								 getApproverDesig="select top 1 ApproverId   from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='sequential' order by Priority desc";
								
							}else
							{
								 getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
							}
						}
						ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
						while(rsApproverDsg.next())
						{
							pApprover=rsApproverDsg.getString("ApproverId");
						}
						int i=0;
						String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
						saveRecReq = saveRecReq + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
						 i=ad.SqlExecuteUpdate(saveRecReq);
						 
						 String saveRecReqTemp="insert into Travel_Request_history (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,submitted_date,Role) values (";
						 saveRecReqTemp = saveRecReqTemp + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','',getdate(),'User')";
						 int i11=ad.SqlExecuteUpdate(saveRecReqTemp);
							 
						 int j = email.sendMailToApprover(request, approvermail,Integer.toString(travel.getRequestNumber()),"Travel Desk");
					
					}
					if(reqType.equalsIgnoreCase("parallel"))
					{	String parallelApprover1="";
					String parallelApprover2="";
					String getApproverDesig="";
						if(travel.getTravelFor()!=null)
						{
							if(travel.getTravelFor().equalsIgnoreCase("On_behalf"))
							{
								 getApproverDesig="select top 1 * from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='parallel'  order by Priority desc";
								
							}else
							{
								 getApproverDesig="select * from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
							}
						}
						/*String parallelApprover1="";
						String parallelApprover2="";
						String getApproverDesig="select * from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
						*/
						ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
							while(rsApproverDsg.next())
							{
								pApprover=rsApproverDsg.getString("ApproverId");
								parallelApprover1=rsApproverDsg.getString("Parallel_Approver1");
								parallelApprover2=rsApproverDsg.getString("Parallel_Approver2");
								
							}
							int i=0;
							String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
							saveRecReq = saveRecReq + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
							 i=ad.SqlExecuteUpdate(saveRecReq);
							 
							 String saveRecReq_Temp="insert into Travel_Request_history (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,submitted_date,Role) values (";
							 saveRecReq_Temp = saveRecReq_Temp + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','',getdate(),'User')";
								int i11=ad.SqlExecuteUpdate(saveRecReq_Temp);
								 
								 
							 int j = email.sendMailToApprover(request, approvermail,Integer.toString(travel.getRequestNumber()),"Travel Desk");
						
							if(!(parallelApprover1.equalsIgnoreCase("")))
							{
								saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
								saveRecReq = saveRecReq + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+parallelApprover1+"','No','"+user.getEmployeeNo()+"','')";
								 i=ad.SqlExecuteUpdate(saveRecReq);
								 
								 saveRecReq_Temp="insert into Travel_Request_history (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,submitted_date,Role) values (";
								 saveRecReq_Temp = saveRecReq_Temp + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+parallelApprover1+"','No','"+user.getEmployeeNo()+"','',getdate(),'User')";
									 i11=ad.SqlExecuteUpdate(saveRecReq_Temp);
									 
									 
								  j = email.sendMailToApprover(request, approvermail,Integer.toString(travel.getRequestNumber()),"Travel Desk");
							}
							if(!(parallelApprover2.equalsIgnoreCase("")))
							{
								saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
								saveRecReq = saveRecReq + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+parallelApprover2+"','No','"+user.getEmployeeNo()+"','')";
								 i=ad.SqlExecuteUpdate(saveRecReq);
								 
								 saveRecReq_Temp="insert into Travel_Request_history (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,submitted_date,Role) values (";
								 saveRecReq_Temp = saveRecReq_Temp + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+parallelApprover2+"','No','"+user.getEmployeeNo()+"','',getdate(),'User')";
								 i11=ad.SqlExecuteUpdate(saveRecReq_Temp);
									 
									 
								  j = email.sendMailToApprover(request, approvermail,Integer.toString(travel.getRequestNumber()),"Travel Desk");
							}
							
					}
				}
				int i=0;
				int j=0;
				if(existStatus==false){
			
				String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
				saveRecReq = saveRecReq + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
				 i=ad.SqlExecuteUpdate(saveRecReq);
				 

					String saveRecReq_Temp="insert into Travel_Request_history (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,submitted_date,Role) values (";
					saveRecReq_Temp = saveRecReq_Temp + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','',getdate(),'User')";
					 j=ad.SqlExecuteUpdate(saveRecReq_Temp);
					 
				
				
				}
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				StringBuilder mess=new StringBuilder();
				mess.append("Travel Request No "+travel.getRequestNumber()+" Submitted Successfully");
				
				
				
/*				if(travel.getTravelFor().equalsIgnoreCase("Self")||travel.getTravelFor().equalsIgnoreCase("Multiple"))
				{	
				
				Date dNow = new Date( );
				 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
				 String dateNow = ft.format(dNow);
				 
				 if(returndate!="")
				 {
				
				 String saveOnDuty="insert into OnDuty_details(request_no,user_id,onDuty_Type,start_date,end_date,OnDuty_status," +
							"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,location,startTime,endTime,InTime,OutTime,travel_no) " +
							"values((select max(request_no)+1 from onduty_details) ,'"+user.getEmployeeNo()+"','Tour','"+departdate+"','"+returndate+"','0'," +
									"'Submited','.','"+dateNow+"','"+travel.getPurposetext()+"','"+user.getFirstName()+"','','In Process','','No','','','"+travel.getDepartTime()+"','"+travel.getReturnTime()+"','','','"+travel.getRequestNumber()+"')";
							int i=0;
							i=ad.SqlExecuteUpdate(saveOnDuty);
				 }
				 else
				 {
					 String saveOnDuty="insert into OnDuty_details(request_no,user_id,onDuty_Type,start_date,end_date,OnDuty_status," +
								"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,location,startTime,endTime,InTime,OutTime,travel_no) " +
								"values((select max(request_no)+1 from onduty_details) ,'"+user.getEmployeeNo()+"','Tour','"+departdate+"',null,'0'," +
										"'Submited','.','"+dateNow+"','"+travel.getPurposetext()+"','"+user.getFirstName()+"','','In Process','','No','','','"+travel.getDepartTime()+"','"+travel.getReturnTime()+"','','','"+travel.getRequestNumber()+"')";
								int i=0;
								i=ad.SqlExecuteUpdate(saveOnDuty);
								
				 }
					
				
				}*/
				
				if(travel.getOnduty_Req().equalsIgnoreCase("Yes"))
				{

					Date dNow = new Date( );
					 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
					 String dateNow = ft.format(dNow);
							
					String tdt ="Select * from Travel_Desk_Travellers where req_id='"+travel.getId()+"' and Pernr!='0' ";
					ResultSet rstdt = ad.selectQuery(tdt);
					try {
						while(rstdt.next())
						{
							
							String s=" select *  from leave_details where Approvel_Status in ('Pending','Approved') and user_id='"+rstdt.getString("pernr")+"' and start_date>='"+departdate+"' and start_date<='"+returndate+"'  and end_date>='"+departdate+"' and end_date<='"+returndate+"' ";	
							ResultSet rs = ad.selectQuery(s);
							try {
								if(rs.next())
								{
								travel.setMessage(" "+rstdt.getString("pernr")+" :Leave Request already exist  in this Particular Days ");
								mess=mess.append(travel.getMessage()); 
									
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							String s1=" select * from onduty_details where Approver_Status in ('In Process','Approved') and user_id='"+rstdt.getString("pernr")+"' and start_date>='"+departdate+"' and start_date<='"+returndate+"'  and end_date>='"+departdate+"' and end_date<='"+returndate+"' ";	
							ResultSet rs1 = ad.selectQuery(s1);
							try {
								if(rs1.next())
								{
									
									travel.setMessage(" "+rstdt.getString("pernr")+":Onduty Request already exist in  this Particular Days ");
									mess=mess.append(travel.getMessage()); 
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						/*	if(returndate!="")
							{
								String saveOnDuty1="insert into OnDuty_details(request_no,user_id,onDuty_Type,start_date,end_date,OnDuty_status," +
										"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,location,startTime,endTime,InTime,OutTime,travel_no) " +
										"values((select max(request_no)+1 from onduty_details) ,'"+rstdt.getString("Pernr")+"','Tour','"+departdate+"','"+returndate+"','0'," +
												"'Submited','.','"+dateNow+"','"+travel.getPurposetext()+"','"+user.getFirstName()+"','','In Process','','No','','','"+travel.getDepartTime()+"','"+travel.getReturnTime()+"','','','"+travel.getRequestNumber()+"')";
										int i1=0;
										i1=ad.SqlExecuteUpdate(saveOnDuty1);
							}
							else
							{
								String saveOnDuty1="insert into OnDuty_details(request_no,user_id,onDuty_Type,start_date,end_date,OnDuty_status," +
										"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,location,startTime,endTime,InTime,OutTime,travel_no) " +
										"values((select max(request_no)+1 from onduty_details) ,'"+rstdt.getString("Pernr")+"','Tour','"+departdate+"',null,'0'," +
												"'Submited','.','"+dateNow+"','"+travel.getPurposetext()+"','"+user.getFirstName()+"','','In Process','','No','','','"+travel.getDepartTime()+"','"+travel.getReturnTime()+"','','','"+travel.getRequestNumber()+"')";
										int i1=0;
										i1=ad.SqlExecuteUpdate(saveOnDuty1);
							}*/
						
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				travel.setMessage(mess.toString());
				String app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from travel_request_history a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where  Req_Type='Travel' and a.Req_Id='"
						+ reqestNo
						+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";
				
				
				List appList = new LinkedList();
				ResultSet rsAppr = ad.selectQuery(app);
				String duplicate="";
				try {
					while (rsAppr.next()) {
						ApprovalsForm appr = new ApprovalsForm();
						appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
						appr.setDesignation(rsAppr.getString("DSGSTXT"));
						appr.setApproveStatus(rsAppr.getString("Req_Status"));
						String approvaStatus = rsAppr.getString("Req_Status");
						if (approvaStatus.equals("Pending")) {
							appr.setApproveDate("");
							appr.setComments("");
						}
						if (approvaStatus.equals("Approved")
								|| approvaStatus.equals("Completed")) {
							appr.setApproveDate(rsAppr.getString("approved_date"));
							appr.setComments(rsAppr.getString("Comments"));
						}
						
						if (approvaStatus.equals("Reverted")
								|| approvaStatus.equals("Completed")) {
							appr.setApproveDate(rsAppr.getString("Reverted_on"));
							appr.setComments(rsAppr.getString("Comments"));
						}
						if (approvaStatus.equals("Rejected")
								|| approvaStatus.equals("Cancelled")) {
							appr.setApproveDate(rsAppr.getString("rejected_date"));
							appr.setComments(rsAppr.getString("Comments"));
						}
						if(duplicate!=null)
						if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))
							appList.add(appr);
							duplicate=rsAppr.getString("approved_date");
					}
					
					
					request.setAttribute("userdetails", appList);
				} catch (SQLException e) {

					e.printStackTrace();
				}
				
		}
		
		else
		{
			travel.setMessage("Error Submitting request ..please check");
		}
		}
		
	displayMyrequest(mapping, form, request, response);
		
		
		
		travel.setReqStatus("");
		
		
		
		return mapping.findForward("mytravellist");
	}
	
	
	public ActionForward updateDomestic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		//Created by Arun .Date:15-11-2016
		
		TravelRequestForm travel = (TravelRequestForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		synchronized (this) {
		
		
		//check requstno exist or not
		 int mtpId=0;
		 int reqestNo=travel.getOldrequestNumber();
		 
		 String path="";
		 boolean checkReqno=false;
		 String checkReqNo="select id from Travel_Desk where reqNo='"+reqestNo+"'";
       ResultSet rsCheck=ad.selectQuery(checkReqNo);
       try{
       while(rsCheck.next()){
    	   mtpId=rsCheck.getInt(1);
		
		
		}
       }catch (Exception e) {
			e.printStackTrace();
		}
       
       

		FormFile documentFile = travel.getDocumentFile();
		String documentName = documentFile.getFileName();
		String filePath = "";
       
   	String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
	int filesize = documentFile.getFileSize();
	if(!documentName.equalsIgnoreCase(""))
	{	
	
	if ((ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")
			|| ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("jpg")
			|| ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("xls") || ext
				.equalsIgnoreCase("txt")) && (filesize < 1048576)) {
		try {
			byte[] size = documentFile.getFileData();
			if (!documentName.equalsIgnoreCase("")) {
				int length = documentName.length();
				int dot = documentName.lastIndexOf(".");
				String fileName = documentFile.getFileName();
				String extension = documentName.substring(dot, length);
				filePath = getServlet().getServletContext().getRealPath(
						"jsp/EMicro Files/ESS/Travel Request/UploadFiles");
				InputStream in = ConnectionFactory.class.getClassLoader()
						.getResourceAsStream("db.properties");
				Properties props = new Properties();
				props.load(in);
				in.close();
				String uploadFilePath = props
						.getProperty("file.uploadFilePath");
				System.out.println("required filepath=" + uploadFilePath
						+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+reqestNo);
				filePath = uploadFilePath
						+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+reqestNo;
				File destinationDir = new File(filePath);
				if (!destinationDir.exists()) {
					destinationDir.mkdirs();
				}
				if (!fileName.equals("")) {
					File fileToCreate = new File(filePath, fileName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);
						fileOutStream.write(documentFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
					}
				}
				try {
					String filePath1 = "E:/EMicro Files/ESS/Travel Request/UploadFiles/"+reqestNo;
					byte[] fileData1 = documentFile.getFileData();
					InputStream is = new ByteArrayInputStream(fileData1);
					// boolean status=saveDataInJRS(is,fileName);
					// System.out.println("File status="+status);

					File destinationDir1 = new File(filePath1);
					if (!destinationDir1.exists()) {
						destinationDir1.mkdirs();
					}
					if (!fileName.equals("")) {
						File fileToCreate1 = new File(filePath1, fileName);
						if (!fileToCreate1.exists()) {
							FileOutputStream fileOutStream1 = new FileOutputStream(
									fileToCreate1);
							fileOutStream1
									.write(documentFile.getFileData());
							fileOutStream1.flush();
							fileOutStream1.close();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		
		
		
	path = "/EMicro Files/ESS/Travel Request/UploadFiles/"+reqestNo+"/"+documentFile.getFileName();

	} else {
		travel.setMessage2("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
		travel.setMessage("");
	}
	}
		
		String departdate="";
		String returndate="";
		String domestic="";
		String trip_start="";
		String trip_end="";
		
		
		if(travel.getDepartOn().contains("/"))
		{	
	    String depart[]=travel.getDepartOn().split("/");
		departdate=depart[2]+"-"+depart[1]+"-"+depart[0];
		}
		

		if(travel.getReturnOn().contains("/"))
		{
		String returnd[]=travel.getReturnOn().split("/");
		returndate=returnd[2]+"-"+returnd[1]+"-"+returnd[0];
		}
	/*	else
		{
			returndate=null;
		}*/
		
		if(returndate!="")
		{
			
			domestic="update Travel_Desk set accom_type='"+travel.getAccom_type()+"',accom_name='"+travel.getAccom_name()+"',"
		 				+ "Type_of_travel='"+travel.getTraveltype()+"',Travel_for='"+travel.getTravelFor()+"',Mode_of_travel='"+travel.getTraveltype()+"',Travel_Request_for='"+travel.getTravelRequestFor()+"',"
		 				+ "From_place='"+travel.getFromPlace()+"',Depart_On='"+departdate+"',Preferred_depart_time='"+travel.getDepartTime()+"',Depart_Time_remarks='"+travel.getDepartRemarks()+"',To_place='"+travel.getToPlace()+"',Return_On='"+returndate+"',Preferred_return_time='"+travel.getReturnTime()+"',Return_Time_remarks='"+travel.getReturnRemarks()+"',Via='"+travel.getVia()+"',"
		 				+ "Purpose='"+travel.getPurposeOfVisit()+"',Remarks='"+travel.getRemarks()+"',Req_status='Pending',travel_Adult='"+travel.getTravel_Adult()+"',travel_Child='"+travel.getTravel_Child()+"',travel_Infant='"+travel.getTravel_Infant()+"',purposetext='"+travel.getPurposetext()+"',travel_Days='"+travel.getTravel_Days()+"',airline_Pref='"+travel.getAirline_Pref()+"',airline_Just='"+travel.getAirline_Just()+"'"
		 				+ " ,spon_div='"+travel.getSpon_div()+"',bud_code='"+travel.getBud_code()+"',est_trip_cose='"+travel.getEst_trip_cose()+"',hotel_Res='"+travel.getHotel_Res()+"',onduty_Req='"+travel.getOnduty_Req()+"',rent_Car='"+travel.getRent_Car()+"',pickup_Details='"+travel.getPickup_Details()+"',drop_Details='"+travel.getDrop_Details()+"',trip_Advance='"+travel.getTrip_Advance()+"'"
		 				+ " ,trip_Amt='"+travel.getTrip_Amt()+"',trip_Currency='"+travel.getTrip_Currency()+"',trip_From_Date='"+trip_start+"',trip_From_Time='"+travel.getTrip_From_Time()+"',trip_To_Date='"+trip_end+"',trip_To_time='"+travel.getTrip_To_time()+"',trip_Priority='"+travel.getTrip_Priority()+"',purposetype='"+travel.getPurposetype()+"',travelmode='"+travel.getTravelmode()+"',service_class='"+travel.getService_class()+"',hotel_Name='"+travel.getHotel_Name()+"',hotel_City='"+travel.getHotel_City()+"',locid='"+travel.getLocid()+"',travel_desk_type='"+travel.getTravel_desk_type()+"',p_name='"+travel.getP_name()+"',p_dept='"+travel.getP_dept()+"',p_cont='"+travel.getP_cont()+"',p_email='"+travel.getP_email()+"',path='"+path+"' where reqNo='"+reqestNo+"'";
		    
		/*domestic="insert into Travel_Desk(accom_type,accom_name,id,reqNo,PERNR,Req_type,"
		 				+ "Type_of_travel,Travel_for,Mode_of_travel,Travel_Request_for,"
		 				+ "From_place,Depart_On,Preferred_depart_time,Depart_Time_remarks,To_place,Return_On,Preferred_return_time,Return_Time_remarks,Via,"
		 				+ "Purpose,Remarks,Req_date,Req_status,travel_Adult,travel_Child,travel_Infant,purposetext,travel_Days,airline_Pref,airline_Just"
		 				+ " ,spon_div,bud_code,est_trip_cose,hotel_Res,onduty_Req,rent_Car,pickup_Details,drop_Details,trip_Advance"
		 				+ " ,trip_Amt,trip_Currency,trip_From_Date,trip_From_Time,trip_To_Date,trip_To_time,trip_Priority,purposetype,travelmode,service_class,hotel_Name,hotel_City,locid,old_reqNo,travel_desk_type,p_name,p_dept,p_cont,p_email,path)"
		 				+ " values('"+travel.getAccom_type()+"','"+travel.getAccom_name()+"','"+travel.getId()+"' ,'"+travel.getRequestNumber()+"','"+user.getEmployeeNo()+"','Travel',"
		 				+ " '"+travel.getTraveltype()+"','"+travel.getTravelFor()+"',"
				+ "'"+travel.getTraveltype()+"','"+travel.getTravelRequestFor()+"','"+travel.getFromPlace()+"','"+departdate+"','"+travel.getDepartTime()+"','"+travel.getDepartRemarks()+"','"+travel.getToPlace()+"','"+returndate+"',"
				+ "'"+travel.getReturnTime()+"','"+travel.getReturnRemarks()+"','"+travel.getVia()+"','"+travel.getPurposeOfVisit()+"','"+travel.getRemarks()+"',getdate(),'Pending','"+travel.getTravel_Adult()+"','"+travel.getTravel_Child()+"',"
						+ "'"+travel.getTravel_Infant()+"','"+travel.getPurposetext()+"','"+travel.getTravel_Days()+"' ,'"+travel.getAirline_Pref()+"','"+travel.getAirline_Just()+"'"
					+ ",'"+travel.getSpon_div()+"','"+travel.getBud_code()+"','"+travel.getEst_trip_cose()+"','"+travel.getHotel_Res()+"','"+travel.getOnduty_Req()+"','"+travel.getRent_Car()+"','"+travel.getPickup_Details()+"' "
					+ ",'"+travel.getDrop_Details()+"' ,'"+travel.getTrip_Advance()+"',"
							+ "'"+travel.getTrip_Amt()+"','"+travel.getTrip_Currency()+"',"
							+ "'"+trip_start+"','"+travel.getTrip_From_Time()+"','"+trip_end+"','"+travel.getTrip_To_time()+"','"+travel.getTrip_Priority()+"' ,'"+travel.getPurposetype()+"' ,'"+travel.getTravelmode()+"','"+travel.getService_class()+"','"+travel.getHotel_Name()+"','"+travel.getHotel_City()+"','"+travel.getLocid()+"','"+travel.getOldrequestNumber()+"','"+travel.getTravel_desk_type()+"', "
							+ "'"+travel.getP_name()+"','"+travel.getP_dept()+"','"+travel.getP_cont()+"','"+travel.getP_email()+"','"+path+"' " 
					+ " )";*/
		}
		else
		{
			
			
			domestic="update Travel_Desk set accom_type='"+travel.getAccom_type()+"',accom_name='"+travel.getAccom_name()+"',"
	 				+ "Type_of_travel='"+travel.getTraveltype()+"',Travel_for='"+travel.getTravelFor()+"',Mode_of_travel='"+travel.getTraveltype()+"',Travel_Request_for='"+travel.getTravelRequestFor()+"',"
	 				+ "From_place='"+travel.getFromPlace()+"',Depart_On='"+departdate+"',Preferred_depart_time='"+travel.getDepartTime()+"',Depart_Time_remarks='"+travel.getDepartRemarks()+"',To_place='"+travel.getToPlace()+"',Return_On='null',Preferred_return_time='"+travel.getReturnTime()+"',Return_Time_remarks='"+travel.getReturnRemarks()+"',Via='"+travel.getVia()+"',"
	 				+ "Purpose='"+travel.getPurposeOfVisit()+"',Remarks='"+travel.getRemarks()+"',Req_status='Pending',travel_Adult='"+travel.getTravel_Adult()+"',travel_Child='"+travel.getTravel_Child()+"',travel_Infant='"+travel.getTravel_Infant()+"',purposetext='"+travel.getPurposetext()+"',travel_Days='"+travel.getTravel_Days()+"',airline_Pref='"+travel.getAirline_Pref()+"',airline_Just='"+travel.getAirline_Just()+"'"
	 				+ " ,spon_div='"+travel.getSpon_div()+"',bud_code='"+travel.getBud_code()+"',est_trip_cose='"+travel.getEst_trip_cose()+"',hotel_Res='"+travel.getHotel_Res()+"',onduty_Req='"+travel.getOnduty_Req()+"',rent_Car='"+travel.getRent_Car()+"',pickup_Details='"+travel.getPickup_Details()+"',drop_Details='"+travel.getDrop_Details()+"',trip_Advance='"+travel.getTrip_Advance()+"'"
	 				+ " ,trip_Amt='"+travel.getTrip_Amt()+"',trip_Currency='"+travel.getTrip_Currency()+"',trip_From_Date='"+trip_start+"',trip_From_Time='"+travel.getTrip_From_Time()+"',trip_To_Date='"+trip_end+"',trip_To_time='"+travel.getTrip_To_time()+"',trip_Priority='"+travel.getTrip_Priority()+"',purposetype='"+travel.getPurposetype()+"',travelmode='"+travel.getTravelmode()+"',service_class='"+travel.getService_class()+"',hotel_Name='"+travel.getHotel_Name()+"',hotel_City='"+travel.getHotel_City()+"',locid='"+travel.getLocid()+"',travel_desk_type='"+travel.getTravel_desk_type()+"',p_name='"+travel.getP_name()+"',p_dept='"+travel.getP_dept()+"',p_cont='"+travel.getP_cont()+"',p_email=,'"+travel.getP_email()+"',path='"+path+"' where reqNo='"+reqestNo+"'";
			
			/*domestic="insert into Travel_Desk(accom_type,accom_name,id,reqNo,PERNR,Req_type,"
	 				+ "Type_of_travel,Travel_for,Mode_of_travel,Travel_Request_for,"
	 				+ "From_place,Depart_On,Preferred_depart_time,Depart_Time_remarks,To_place,Return_On,Preferred_return_time,Return_Time_remarks,Via,"
	 				+ "Purpose,Remarks,Req_date,Req_status,travel_Adult,travel_Child,travel_Infant,purposetext,travel_Days,airline_Pref,airline_Just"
	 				+ " ,spon_div,bud_code,est_trip_cose,hotel_Res,onduty_Req,rent_Car,pickup_Details,drop_Details,trip_Advance"
	 				+ " ,trip_Amt,trip_Currency,trip_From_Date,trip_From_Time,trip_To_Date,trip_To_time,trip_Priority,purposetype,travelmode,service_class,hotel_Name,hotel_City,locid,old_reqNo,travel_desk_type,p_name,p_dept,p_cont,p_email,path)"
	 				+ " values('"+travel.getAccom_type()+"','"+travel.getAccom_name()+"','"+travel.getId()+"' ,'"+travel.getRequestNumber()+"','"+user.getEmployeeNo()+"','Travel',"
	 				+ " '"+travel.getTraveltype()+"','"+travel.getTravelFor()+"',"
			+ "'"+travel.getTraveltype()+"','"+travel.getTravelRequestFor()+"','"+travel.getFromPlace()+"','"+departdate+"','"+travel.getDepartTime()+"','"+travel.getDepartRemarks()+"','"+travel.getToPlace()+"',null,"
			+ "'"+travel.getReturnTime()+"','"+travel.getReturnRemarks()+"','"+travel.getVia()+"','"+travel.getPurposeOfVisit()+"','"+travel.getRemarks()+"',getdate(),'Pending','"+travel.getTravel_Adult()+"','"+travel.getTravel_Child()+"',"
					+ "'"+travel.getTravel_Infant()+"','"+travel.getPurposetext()+"','"+travel.getTravel_Days()+"' ,'"+travel.getAirline_Pref()+"','"+travel.getAirline_Just()+"'"
				+ ",'"+travel.getSpon_div()+"','"+travel.getBud_code()+"','"+travel.getEst_trip_cose()+"','"+travel.getHotel_Res()+"','"+travel.getOnduty_Req()+"','"+travel.getRent_Car()+"','"+travel.getPickup_Details()+"' "
				+ ",'"+travel.getDrop_Details()+"' ,'"+travel.getTrip_Advance()+"',"
						+ "'"+travel.getTrip_Amt()+"','"+travel.getTrip_Currency()+"',"
						+ "'"+trip_start+"','"+travel.getTrip_From_Time()+"','"+trip_end+"','"+travel.getTrip_To_time()+"','"+travel.getTrip_Priority()+"' ,'"+travel.getPurposetype()+"' ,'"+travel.getTravelmode()+"','"+travel.getService_class()+"','"+travel.getHotel_Name()+"','"+travel.getHotel_City()+"','"+travel.getLocid()+"','"+travel.getOldrequestNumber()+"','"+travel.getTravel_desk_type()+"', "
						+ "'"+travel.getP_name()+"','"+travel.getP_dept()+"','"+travel.getP_cont()+"','"+travel.getP_email()+"','"+path+"' " 
				+ " )";*/
		}
		int t=ad.SqlExecuteUpdate(domestic);
		
		
			
		
		
		
		
		/*if(travel.getOldrequestNumber()!=0)
		{
		 String  up = "update travel_desk set req_status='Reverted' where reqNo='"+travel.getOldrequestNumber()+"' ";
		 int k = ad.SqlExecuteUpdate(up);
		 
		 String updateSql= "update OnDuty_details set Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',OnDuty_status='2',Approver_Status='Rejected',approved_date='',rejected_date=getdate(),comments='rescheduled' "
		 		+ "where travel_no='"+travel.getOldrequestNumber()+"'";
			int recordChange=ad.SqlExecuteUpdate(updateSql);
		 
		 
		 String up1=" update all_request set req_status='Pending' where Req_Type='Travel' and Req_Id='"+travel.getOldrequestNumber()+"' and req_status='Pending'";
		 int k1 = ad.SqlExecuteUpdate(up1);		 
		}*/
		
		
		
		String ab ="delete from travel_desk where id='"+travel.getId()+"' and reqno is null ";
		int ib = ad.SqlExecuteUpdate(ab);
		
		String[] mlocl=request.getParameterValues("mlocl");
		String[] mairpl=request.getParameterValues("mairpl");
		String[] marrvdatel=request.getParameterValues("marrvdatel");
		String[] marrtimel=request.getParameterValues("marrtimel");
		String[] mdeptdatel=request.getParameterValues("mdeptdatel");
		String[] mdepttimel=request.getParameterValues("mdepttimel");
		
		
		String[] userlistIdl=request.getParameterValues("userlistIdl");
		String[] mhotel_Resl=request.getParameterValues("mhotel_Resl");
		String[] mrent_Carl=request.getParameterValues("mrent_Carl");
		String[] mhotel_Namel=request.getParameterValues("mhotel_Namel");
		String[] mhotel_Cityl=request.getParameterValues("mhotel_Cityl");
		String[] mpickup_Detailsl=request.getParameterValues("mpickup_Detailsl");
		String[] mdrop_Detailsl=request.getParameterValues("mdrop_Detailsl");
		
		String[] maccom_typel=request.getParameterValues("maccom_typel");
		String[] maccom_namel=request.getParameterValues("maccom_namel");
		String[] moriginl=request.getParameterValues("moriginl");
		String[] mdeparturel=request.getParameterValues("mdeparturel");
		
		String[] mtravelmodel=request.getParameterValues("mtravelmodel");
		String[] mtraveltypel=request.getParameterValues("mtraveltypel");
		
		

		String[] mpassportnol=request.getParameterValues("mpassportnol");
		String[] mpassportexpirydatel=request.getParameterValues("mpassportexpirydatel");
		String[] mguest_Visanol=request.getParameterValues("mguest_Visanol");
		
		
		
			
		String date1;
		String date2;
		
		if(mlocl!=null)
		{	
		for(int i=0;i<mlocl.length;i++)
		{

		    String marrvdatel1[]=marrvdatel[i].split("/");
		    date1=marrvdatel1[2]+"-"+marrvdatel1[1]+"-"+marrvdatel1[0];
			

		    String marrvdatel2[]=mdeptdatel[i].split("/");
		    date2=marrvdatel2[2]+"-"+marrvdatel2[1]+"-"+marrvdatel2[0];
		    
		    
		    if(userlistIdl[i].contains("-"))
		    {
		    String[] a=userlistIdl[i].split("-");
		    userlistIdl[i]=a[0];
		    }
		    
		    if(mpassportexpirydatel[i]!=null && !mpassportexpirydatel[i].equalsIgnoreCase("null"))
		    {	
		    if(mpassportexpirydatel[i].contains("/"))
		    {	
		    String pase[]=mpassportexpirydatel[i].split("/");
		    mpassportexpirydatel[i]=pase[2]+"-"+pase[1]+"-"+pase[0];
		    }
		    }
		    
		    
			String a="update Travel_Multiple_City set Location='"+mlocl[i]+"',Air_preference='"+mairpl[i]+"',Arrival_date='"+date1+"',Arrival_time='"+marrtimel[i]+"',"
					+ "Departure_date='"+date2+"',Departure_time='"+mdepttimel[i]+"',Created_by='"+user.getEmployeeNo()+"', trav_id='"+userlistIdl[i]+"',mhotel_Res='"+mhotel_Resl[i]+"',mrent_Car='"+mrent_Carl[i]+"',"
					+ " mhotel_Name='"+mhotel_Namel[i]+"',mhotel_City='"+mhotel_Cityl[i]+"',mpickup_Details='"+mpickup_Detailsl[i]+"',mdrop_Details='"+mdrop_Detailsl[i]+"',maccom_type='"+maccom_typel[i]+"',maccom_name='"+maccom_namel[i]+"',morigin='"+moriginl[i]+"',mdeparture='"+mdeparturel[i]+"',"
							+ "mtravelmode='"+mtravelmodel[i]+"',mtraveltype='"+mtraveltypel[i]+"',mpassportno='"+mpassportnol[i]+"',mpassportexpirydate='"+mpassportexpirydatel[i]+"',mguest_Visano='"+mguest_Visanol[i]+"' where id='"+travel.getMultiCityId()+"'";
			int ia = ad.SqlExecuteUpdate(a);
			
		}
		}
		
		
		String[] plandate=request.getParameterValues("mdate");
		String[] morigin11=request.getParameterValues("morigin11");
		String[] mdestination1=request.getParameterValues("mdestination1");
		
		String[] mtravelmode12=request.getParameterValues("mtravelmode1");
		String[] mtraveltype12=request.getParameterValues("mtraveltype1");
		String[] mtraveldesk12=request.getParameterValues("mtraveldesk1");
		String[] mremarks1=request.getParameterValues("mremarks1");
		
		String datep1;
		 String del  = "delete from Travel_MTP where req_id='"+mtpId+"'";
		    int delll =ad.SqlExecuteUpdate(del);
		if(plandate!=null)
		{	
		for(int i=0;i<plandate.length;i++)
		{

		    String mdate1[]=plandate[i].split("/");
		    datep1=mdate1[2]+"-"+mdate1[1]+"-"+mdate1[0];
				    
		    
		   
		  
			String pa="insert into Travel_MTP(req_id,plan_date,morigin,mdestination,mtravelmode,mtraveltype,mtraveldesk,mremarks)values"
					+ "( '"+mtpId+"','"+datep1+"','"+morigin11[i]+"','"+mdestination1[i]+"','"+mtravelmode12[i]+"','"+mtraveltype12[i]+"','"+mtraveldesk12[i]+"','"+mremarks1[i]+"')";
			int pa1 = ad.SqlExecuteUpdate(pa);
		    	
		    
		    
		 			
		}
		}
		   
		
		
		if(t>0)
		{
			travel.setMessage("Reverted Travel Request no. "+reqestNo+" submitted successfully");
			//insert into all request
			
			EMailer email = new EMailer();
			String approver="";
			String lApprover="";
			String pApprover="";
			String approvermail="";
			
			String getApproverID="select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where "
					+ "ess.employeeNumber='"+user.getEmployeeNo()+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId";
			ResultSet approverRS=ad.selectQuery(getApproverID);
			try {
				if(approverRS.next()){
					
						approver = approverRS.getString("ApproverId");
						approver = email.getApproverName(approver);
						lApprover = "";
						pApprover = approver;
						approvermail=approverRS.getString("EMAIL_ID");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			String addApprover="update Travel_Desk set Last_approver='No',Pending_approver='"+pApprover+"' where reqNo='"+reqestNo+"' ";
			int p=ad.SqlExecuteUpdate(addApprover);
			
			
			
	
				
				boolean existStatus=false;
				String reqType="";
				String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' group by reqType";
				ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
				try {
					while(rsCheckInEss.next())
					{
						int number=rsCheckInEss.getInt(1);
						reqType=rsCheckInEss.getString("reqType");
						if(number==0)
						{
							existStatus=false;
						}else{
							existStatus=true;
						}
					}
				
				if(existStatus==true){
					if(reqType.equalsIgnoreCase("sequential"))
					{
					String getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
					ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
						while(rsApproverDsg.next())
						{
							pApprover=rsApproverDsg.getString("ApproverId");
						}
						int i=0;
						String saveRecReq="update  All_Request set Req_Status='Pending', Last_Approver='No', Pending_Approver='"+pApprover+"' where req_id='"+reqestNo+"'";
						 i=ad.SqlExecuteUpdate(saveRecReq);
						 
						 String saveRecReqTemp="insert into Travel_Request_history (Req_Id, Req_Type, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,submitted_date,Role) values (";
						 saveRecReqTemp = saveRecReqTemp + "'"+reqestNo+"','Travel','"+user.getEmployeeNo()+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','',getdate(),'User')";
						 int i11=ad.SqlExecuteUpdate(saveRecReqTemp);
							 
						 int j = email.sendMailToApprover(request, approvermail,Integer.toString(travel.getRequestNumber()),"Travel Desk");
					
					}
					if(reqType.equalsIgnoreCase("parallel"))
					{
						String parallelApprover1="";
						String parallelApprover2="";
						String getApproverDesig="select * from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
						ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
							while(rsApproverDsg.next())
							{
								pApprover=rsApproverDsg.getString("ApproverId");
								parallelApprover1=rsApproverDsg.getString("Parallel_Approver1");
								parallelApprover2=rsApproverDsg.getString("Parallel_Approver2");
								
							}
							int i=0;
							/*String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
							saveRecReq = saveRecReq + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
							 i=ad.SqlExecuteUpdate(saveRecReq);*/
							
							String saveRecReq="update  All_Request set Req_Status='Pending', Last_Approver='No', Pending_Approver='"+pApprover+"' where req_id='"+reqestNo+"'";
							 i=ad.SqlExecuteUpdate(saveRecReq);
							 
							 String saveRecReq_Temp="insert into Travel_Request_history (Req_Id, Req_Type, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,submitted_date,Role) values (";
							 saveRecReq_Temp = saveRecReq_Temp + "'"+reqestNo+"','Travel','"+user.getEmployeeNo()+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','',getdate(),'User')";
								int i11=ad.SqlExecuteUpdate(saveRecReq_Temp);
								 
								 
							 int j = email.sendMailToApprover(request, approvermail,Integer.toString(travel.getRequestNumber()),"Travel Desk");
						
							if(!(parallelApprover1.equalsIgnoreCase("")))
							{
								/*saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
								saveRecReq = saveRecReq + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+parallelApprover1+"','No','"+user.getEmployeeNo()+"','')";
								 i=ad.SqlExecuteUpdate(saveRecReq);*/
								 saveRecReq="update  All_Request set Req_Status='Pending', Last_Approver='No', Pending_Approver='"+parallelApprover1+"' where req_id='"+reqestNo+"'";
								 i=ad.SqlExecuteUpdate(saveRecReq);
								 
								 saveRecReq_Temp="insert into Travel_Request_history (Req_Id, Req_Type, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,submitted_date,Role) values (";
								 saveRecReq_Temp = saveRecReq_Temp + "'"+reqestNo+"','Travel','"+user.getEmployeeNo()+"','Pending','','"+parallelApprover1+"','No','"+user.getEmployeeNo()+"','',getdate(),'User')";
									 i11=ad.SqlExecuteUpdate(saveRecReq_Temp);
									 
									 
								  j = email.sendMailToApprover(request, approvermail,Integer.toString(travel.getRequestNumber()),"Travel Desk");
							}
							if(!(parallelApprover2.equalsIgnoreCase("")))
							{
								/*saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
								saveRecReq = saveRecReq + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+parallelApprover2+"','No','"+user.getEmployeeNo()+"','')";
								 i=ad.SqlExecuteUpdate(saveRecReq);*/
								
								 saveRecReq="update  All_Request set Req_Status='Pending', Last_Approver='No', Pending_Approver='"+parallelApprover2+"' where req_id='"+reqestNo+"'";
								 i=ad.SqlExecuteUpdate(saveRecReq);
								 
								 saveRecReq_Temp="insert into Travel_Request_history (Req_Id, Req_Type, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,submitted_date,Role) values (";
								 saveRecReq_Temp = saveRecReq_Temp + "'"+reqestNo+"','Travel','"+user.getEmployeeNo()+"','Pending','','"+parallelApprover2+"','No','"+user.getEmployeeNo()+"','',getdate(),'User')";
								 i11=ad.SqlExecuteUpdate(saveRecReq_Temp);
									 
									 
								  j = email.sendMailToApprover(request, approvermail,Integer.toString(travel.getRequestNumber()),"Travel Desk");
							}
							
					}
				}
				int i=0;
				int j=0;
				if(existStatus==false){
			
				/*String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
				saveRecReq = saveRecReq + "'"+travel.getRequestNumber()+"','Travel','"+user.getEmployeeNo()+"',(select convert(nvarchar(10),getdate(),103)),'Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
				 i=ad.SqlExecuteUpdate(saveRecReq);*/
				 
				 
					String saveRecReq="update  All_Request set Req_Status='Pending', Last_Approver='No', Pending_Approver='"+pApprover+"' where req_id='"+reqestNo+"'";
				 i=ad.SqlExecuteUpdate(saveRecReq);
				 

					String saveRecReq_Temp="insert into Travel_Request_history (Req_Id, Req_Type, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,submitted_date,Role) values (";
					saveRecReq_Temp = saveRecReq_Temp + "'"+reqestNo+"','Travel','"+user.getEmployeeNo()+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','',getdate(),'User')";
					 j=ad.SqlExecuteUpdate(saveRecReq_Temp);
					 
				
				
				}
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				StringBuilder mess=new StringBuilder();
				mess.append("Reverted Travel Request no. "+reqestNo+" submitted successfully");
				
				
				
				if(travel.getTravelFor().equalsIgnoreCase("Self")||travel.getTravelFor().equalsIgnoreCase("Multiple"))
				{	
				
				Date dNow = new Date( );
				 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
				 String dateNow = ft.format(dNow);
				 
				/* if(returndate!="")
				 {
				
				 String saveOnDuty="insert into OnDuty_details(request_no,user_id,onDuty_Type,start_date,end_date,OnDuty_status," +
							"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,location,startTime,endTime,InTime,OutTime,travel_no) " +
							"values((select max(request_no)+1 from onduty_details) ,'"+user.getEmployeeNo()+"','Tour','"+departdate+"','"+returndate+"','0'," +
									"'Submited','.','"+dateNow+"','"+travel.getPurposetext()+"','"+user.getFirstName()+"','','In Process','','No','','','"+travel.getDepartTime()+"','"+travel.getReturnTime()+"','','','"+travel.getRequestNumber()+"')";
							int i=0;
							i=ad.SqlExecuteUpdate(saveOnDuty);
				 }
				 else
				 {
					 String saveOnDuty="insert into OnDuty_details(request_no,user_id,onDuty_Type,start_date,end_date,OnDuty_status," +
								"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,location,startTime,endTime,InTime,OutTime,travel_no) " +
								"values((select max(request_no)+1 from onduty_details) ,'"+user.getEmployeeNo()+"','Tour','"+departdate+"',null,'0'," +
										"'Submited','.','"+dateNow+"','"+travel.getPurposetext()+"','"+user.getFirstName()+"','','In Process','','No','','','"+travel.getDepartTime()+"','"+travel.getReturnTime()+"','','','"+travel.getRequestNumber()+"')";
								int i=0;
								i=ad.SqlExecuteUpdate(saveOnDuty);
								
				 }*/
					
				
				}
				
				if(travel.getOnduty_Req().equalsIgnoreCase("Yes"))
				{

					Date dNow = new Date( );
					 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
					 String dateNow = ft.format(dNow);
							
					/*String tdt ="Select * from Travel_Desk_Travellers where req_id='"+travel.getId()+"' and Pernr!='0' ";
					ResultSet rstdt = ad.selectQuery(tdt);
					try {
						while(rstdt.next())
						{
							
							String s=" select *  from leave_details where Approvel_Status in ('Pending','Approved') and user_id='"+rstdt.getString("pernr")+"' and start_date>='"+departdate+"' and start_date<='"+returndate+"'  and end_date>='"+departdate+"' and end_date<='"+returndate+"' ";	
							ResultSet rs = ad.selectQuery(s);
							try {
								if(rs.next())
								{
								travel.setMessage(" "+rstdt.getString("pernr")+" :Leave Request already exist  in this Particular Days ");
								mess=mess.append(travel.getMessage()); 
									
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							String s1=" select * from onduty_details where Approver_Status in ('In Process','Approved') and user_id='"+rstdt.getString("pernr")+"' and start_date>='"+departdate+"' and start_date<='"+returndate+"'  and end_date>='"+departdate+"' and end_date<='"+returndate+"' ";	
							ResultSet rs1 = ad.selectQuery(s1);
							try {
								if(rs1.next())
								{
									
									travel.setMessage(" "+rstdt.getString("pernr")+":Onduty Request already exist in  this Particular Days ");
									mess=mess.append(travel.getMessage()); 
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							if(returndate!="")
							{
								String saveOnDuty1="insert into OnDuty_details(request_no,user_id,onDuty_Type,start_date,end_date,OnDuty_status," +
										"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,location,startTime,endTime,InTime,OutTime,travel_no) " +
										"values((select max(request_no)+1 from onduty_details) ,'"+rstdt.getString("Pernr")+"','Tour','"+departdate+"','"+returndate+"','0'," +
												"'Submited','.','"+dateNow+"','"+travel.getPurposetext()+"','"+user.getFirstName()+"','','In Process','','No','','','"+travel.getDepartTime()+"','"+travel.getReturnTime()+"','','','"+travel.getRequestNumber()+"')";
										int i1=0;
										i1=ad.SqlExecuteUpdate(saveOnDuty1);
							}
							else
							{
								String saveOnDuty1="insert into OnDuty_details(request_no,user_id,onDuty_Type,start_date,end_date,OnDuty_status," +
										"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,location,startTime,endTime,InTime,OutTime,travel_no) " +
										"values((select max(request_no)+1 from onduty_details) ,'"+rstdt.getString("Pernr")+"','Tour','"+departdate+"',null,'0'," +
												"'Submited','.','"+dateNow+"','"+travel.getPurposetext()+"','"+user.getFirstName()+"','','In Process','','No','','','"+travel.getDepartTime()+"','"+travel.getReturnTime()+"','','','"+travel.getRequestNumber()+"')";
										int i1=0;
										i1=ad.SqlExecuteUpdate(saveOnDuty1);
							}
						
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
				}
				travel.setMessage(mess.toString());
				String app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from travel_request_history a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where  Req_Type='Travel' and a.Req_Id='"
						+ reqestNo
						+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";
				
				
				List appList = new LinkedList();
				ResultSet rsAppr = ad.selectQuery(app);
				String duplicate="";
				try {
					while (rsAppr.next()) {
						ApprovalsForm appr = new ApprovalsForm();
						appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
						appr.setDesignation(rsAppr.getString("DSGSTXT"));
						appr.setApproveStatus(rsAppr.getString("Req_Status"));
						String approvaStatus = rsAppr.getString("Req_Status");
						if (approvaStatus.equals("Pending")) {
							appr.setApproveDate("");
							appr.setComments("");
						}
						if (approvaStatus.equals("Approved")
								|| approvaStatus.equals("Completed")) {
							appr.setApproveDate(rsAppr.getString("approved_date"));
							appr.setComments(rsAppr.getString("Comments"));
						}
						
						if (approvaStatus.equals("Reverted")
								|| approvaStatus.equals("Completed")) {
							appr.setApproveDate(rsAppr.getString("Reverted_on"));
							appr.setComments(rsAppr.getString("Comments"));
						}
						if (approvaStatus.equals("Rejected")
								|| approvaStatus.equals("Cancelled")) {
							appr.setApproveDate(rsAppr.getString("rejected_date"));
							appr.setComments(rsAppr.getString("Comments"));
						}
						if(duplicate!=null)
						if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))
							appList.add(appr);
							duplicate=rsAppr.getString("approved_date");
					}
					
					
					request.setAttribute("appList", appList);
				} catch (SQLException e) {

					e.printStackTrace();
				}
				
		}
		
		else
		{
			travel.setMessage("Error Submitting request ..please check");
		}
		}
		
		displayMyrequest(mapping, form, request, response);
		
		
		
		travel.setReqStatus("");
		
		
		
		
		return mapping.findForward("mytravellist");
	}

	//checking onduty for travellers
	/*public ActionForward ondutychecking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int i=1;
		
		String departdate=request.getParameter("departdate");
		String returndate=request.getParameter("returndate");
		String tid=request.getParameter("tid");
		
		
		TravelRequestForm travel =(TravelRequestForm)form;
		String req_id="";
		String pernr="";
		
		String getFilename = "select * from Travel_Desk_Travellers where id='"+tid+"' ";
		ResultSet rsFileName = ad.selectQuery(getFilename);
		try {
			if (rsFileName.next()) {
		
				req_id = rsFileName.getString("req_id");
				pernr=rsFileName.getString("Pernr");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		if(departdate.contains("/"))
		{	
	    String depart[]=departdate.split("/");
		departdate=depart[2]+"-"+depart[1]+"-"+depart[0];
		}
		

		if(returndate.contains("/"))
		{
		String returnd[]=returndate.split("/");
		returndate=returnd[2]+"-"+returnd[1]+"-"+returnd[0];
		}

			
		
		String s=" select *  from leave_details where Approvel_Status in ('Pending','Approved') and user_id='"+pernr
				+"' and  start_date>='"+departdate+"' and end_date<='"+returndate+"' ";	
		ResultSet rs = ad.selectQuery(s);
		try {
			if(rs.next())
			{
			travel.setMessage("You Applied Leave in this Particular Days , PLease select On Duty Required No");	
				i=2;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		String s1=" select * from onduty_details where Approver_Status in ('In Process','Approved') and  user_id='"+pernr+"' "
				+ " start_date>='"+departdate+"' and end_date<='"+returndate+"' ";	
		ResultSet rs1 = ad.selectQuery(s1);
		try {
			if(rs1.next())
			{
				
				travel.setMessage("You Applied On Duty in  this Particular Days , PLease select On Duty Required No");
				i=2;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
		 String dateNow = ft.format(dNow);
		
		   String saveOnDuty =" update Travel_Desk_Travellers set on_duty ='"+i+"' where id='"+tid+"' ";
			int	k=ad.SqlExecuteUpdate(saveOnDuty);		
		
		
		
		
		
		//traveller details
        String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1,* "
        		+ " from Travel_Desk_Travellers where req_id='"+req_id+"'" ;
        ResultSet b=ad.selectQuery(his);
        ArrayList emplist = new ArrayList();
		try {
			while(b.next())
			{
				
					TravelRequestForm emp=new TravelRequestForm();
					String ere[]=empDet(Integer.toString(b.getInt("PERNR")));
					emp.setGuest_pernr(Integer.toString(b.getInt("PERNR")));
					emp.setEmployeeName(ere[0]);
					emp.setLocationId(ere[1]);
					emp.setDepartment(ere[2]);
					emp.setDesignation(ere[3]); 
					emp.setUserAge(ere[5]);
					emp.setUserGender(ere[7]);                		
					emp.setGuest_Type(b.getString("Req_type"));
					emp.setPassportno(b.getString("Passport_no"));
					emp.setPassportplace(b.getString("Place_issue"));
					emp.setPassportexpirydate(b.getString("Date_of_expiry"));
					emp.setGuestName(b.getString("Name_of_guest"));
					emp.setGuestContactNo(b.getString("Contact_guest"));
					emp.setGuest_Company(b.getString("guest_Company"));
					emp.setGuestAge(b.getString("Age_guest"));
					emp.setGender(b.getString("Gender_guest"));
					emp.setEmail_Guest(b.getString("Email_guest"));
					emp.setFileName(b.getString("file_name"));
					emp.setFileFullPath(b.getString("path"));
					emp.setGuest_Title(b.getString("guest_Title"));
					emp.setGuest_Visano(b.getString("guest_Visano"));
					emp.setGuest_DOB(b.getString("guest_DOB"));
					emp.setGuest_Meal(b.getString("guest_Meal"));
					emp.setOn_duty(b.getString("on_duty"));
					emp.setId(b.getInt("id"));
					
					
					if(!b.getString("Date_of_expiry1").contains("1900"))
					emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
					else
			    	emp.setUserpassportexpirydate("");
					
					emplist .add(emp);
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        if(emplist.size()>0)
        	request.setAttribute("emplist", emplist);	
	
		return mapping.findForward("travellerList");
	}*/
	
	public ActionForward displayInternational(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//Created by Arun .Date:15-11-2016
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		return mapping.findForward("internationalformrequest");
	}
	
	public ActionForward displayApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		TravelRequestForm help=(TravelRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		LinkedList apprList=new LinkedList();
		
		int appr=Integer.parseInt(request.getParameter("traveldesk"));
		String emp="";
		String dsg ="";
		String dpt = "";
		
		String getName="select e.EMP_FULLNAME,dp.DPTSTXT,dsg.DSGSTXT from emp_official_info e , DEPARTMENT dp,DESIGNATION dsg where e.pernr="+appr+" and e.DPTID=dp.DPTID and e.DSGID=dsg.DSGID";
		ResultSet rs=ad.selectQuery(getName);
		try{
		while(rs.next()){
			emp=rs.getString("EMP_FULLNAME");
			help.setTravel_desk_type(emp);
			dsg=rs.getString("DSGSTXT");
			help.setDesignation(dsg);
			dpt=rs.getString("DPTSTXT");
			help.setDepartment(dpt);
			help.setStatus("Pending");
			
			apprList.add(help);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		/*apprList=dao.ItemApproversList(location, "1", matGroup);*/
		
		
		if(apprList.size()>0)
			request.setAttribute("apprList", apprList);
		if(apprList.size()==0)
			request.setAttribute("noapprList", "noapprList");
		return mapping.findForward("apprList");
	}

	
	public ActionForward ViewMyrequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
		
		TravelRequestForm typ =(TravelRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String filterBy = request.getParameter("requststatus");
		String reqno=request.getParameter("requstNo");
		String review=request.getParameter("review");
		
		if(review==null)
			review="0";
		
		typ.setRequestNumber(Integer.parseInt(reqno));
		ArrayList data=new ArrayList();
		ArrayList emplist=new ArrayList();
		ArrayList travel=new ArrayList();
		ArrayList tra=new ArrayList();
		ArrayList city=new ArrayList();
		String TravelFor="";
		String lis="select CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1, CONVERT(varchar(10),billdate,103) as billdate1 ,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as  bookeddate1,"
				+ "  CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1   ,CONVERT(varchar(10),Depart_On,103)  as departon,"
				+ "CONVERT(varchar(10),Return_On,103)  as returnon,* from Travel_Desk,division where reqNo='"+typ.getRequestNumber()+"'";
		ResultSet ff=ad.selectQuery(lis);
		try {
			if(ff.next())
			{
				TravelRequestForm t=new TravelRequestForm();
				String re[]=empDet(Integer.toString(ff.getInt("pernr")));
				t.setEmployeeno(user.getEmployeeNo());
				t.setEmployeeName(re[0]);
				t.setP_name(re[0]);
				t.setLocationId(re[1]);
				t.setDepartment(re[2]);
				t.setP_dept(re[2]);
				t.setDesignation(re[3]); 
				t.setUserAge(re[5]);
				t.setUsermailId(re[6]);
				t.setP_email(re[6]);
				t.setUserGender(re[7]);
				
				String pe[]=empPERDet(Integer.toString(ff.getInt("pernr")));

				t.setUsermobno(pe[0]);
				t.setP_cont(pe[0]);
				t.setUserpassportno(pe[1]);
				t.setUserpassportplace(pe[2]);
				t.setUserPersonalmailId(pe[5]);
				
				if(!pe[3].contains("1900"))
					t.setUserpassportissuedate(pe[3]); 
					else
				    t.setUserpassportissuedate(""); 
					
					if(!pe[4].contains("1900"))
					t.setUserpassportexpirydate(pe[4]); 
					else
				    t.setUserpassportexpirydate(""); 
			
				t.setRequestNumber(ff.getInt("reqNo"));
				t.setReqType(ff.getString("Req_type"));
				t.setTypeOfTravel(ff.getString("Type_of_travel"));
				t.setTravelFor(ff.getString("Travel_for"));
				t.setTravelRequestFor(ff.getString("Travel_Request_for"));
				String Travel_Request_for= ff.getString("Travel_Request_for");
				t.setModeOfTravel(ff.getString("Mode_of_travel"));
				t.setFromPlace(ff.getString("From_place"));
				t.setDepartOn(ff.getString("departon"));
				t.setDepartTime(ff.getString("Preferred_depart_time"));
				t.setReturnTime(ff.getString("Preferred_return_time"));	
				t.setDepartRemarks(ff.getString("Depart_Time_remarks"));
				t.setReturnRemarks(ff.getString("Return_Time_remarks"));
				t.setToPlace(ff.getString("To_place"));
                t.setVia(ff.getString("via"));
                t.setRemarks(ff.getString("Remarks"));
                t.setPurposeOfVisit(ff.getString("Purpose"));
                t.setReturnOn(ff.getString("returnon"));
                t.setTravel_Adult(ff.getString("Travel_Adult"));
                t.setTravel_Child(ff.getString("Travel_Child"));
                t.setTravel_Infant(ff.getString("travel_Infant"));
                t.setPurposetext(ff.getString("purposetext"));
                t.setTravel_Days(ff.getString("travel_Days"));
                if(ff.getString("airline_Pref")==null||ff.getString("airline_Pref").equalsIgnoreCase("null"))
                {
                	 t.setAirline_Pref("");
                     
                	
                }
                else
                {
                t.setAirline_Pref(ff.getString("airline_Pref"));
             
                }
                
                if(ff.getString("airline_Just")==null||ff.getString("airline_Just").equalsIgnoreCase("null"))
                {
                
                     t.setAirline_Just("");
                	
                }
                else
                {
            
                t.setAirline_Just(ff.getString("airline_Just"));
                }
                
                //t.setSpon_div(ff.getString("spon_div"));
                t.setBud_code(ff.getString("bud_code"));
                t.setEst_trip_cose(ff.getString("est_trip_cose"));
                t.setHotel_Res(ff.getString("hotel_Res"));
                t.setOnduty_Req(ff.getString("onduty_Req"));
                t.setRent_Car(ff.getString("rent_Car"));
                t.setPickup_Details(ff.getString("pickup_Details"));
                t.setDrop_Details(ff.getString("drop_Details"));
                t.setTrip_Advance(ff.getString("trip_Advance"));
                t.setTrip_Amt(ff.getString("trip_Amt"));
                t.setTrip_Currency(ff.getString("trip_Currency"));
                t.setTrip_From_Date(ff.getString("trip_From_Date"));
                t.setTrip_To_Date(ff.getString("trip_To_Date"));
                t.setTrip_From_Time(ff.getString("trip_From_Time"));
                t.setTrip_To_time(ff.getString("trip_To_time"));
                if(ff.getString("trip_Priority")==null||ff.getString("trip_Priority").equalsIgnoreCase("null"))
                		{
                	  t.setTrip_Priority("");
                	
                		}
                else
                {
                	 t.setTrip_Priority(ff.getString("trip_Priority"));
                }
               
                t.setPurposetype(ff.getString("purposetype"));
                t.setTravelmode(ff.getString("travelmode"));
                t.setTraveltype(ff.getString("Type_of_travel"));
                if(ff.getString("service_class")==null||ff.getString("service_class").equalsIgnoreCase("null"))
        		{
                	t.setTrip_Priority("");
        	
        		}
                else
                {
                	t.setTrip_Priority(ff.getString("service_class"));
                }
                //t.setService_class(ff.getString("service_class"));
			    t.setTravelagentname(ff.getString("travelagentname"));
			    t.setService_class(ff.getString("service_class"));
                t.setBillno(ff.getString("billno"));
                t.setBilldate(ff.getString("billdate1"));
                t.setBillamount(ff.getString("billamount"));
                t.setBookingstatus(ff.getString("bookingstatus"));
                t.setBookeddate(ff.getString("bookeddate1"));
                t.setReqDate(ff.getString("Created_date1"));
                t.setBookedby(Empname(ff.getString("bookedby")));
                t.setConfirmdate(ff.getString("confirmdate1"));
                t.setConfirmby(Empname(ff.getString("confirmby")));
                t.setConfirmstatus(ff.getString("confirmstatus"));
                t.setReview_Trip(ff.getString("review_Trip"));
                t.setHotel_Name(ff.getString("hotel_Name"));
                t.setHotel_City(ff.getString("hotel_City"));
                t.setReview_Trip(ff.getString("review_Trip"));
                t.setReview_Rating(ff.getString("review_Rating"));
                t.setOldrequestNumber(ff.getInt("old_reqNo"));
                t.setTravel_desk_type(Empname(ff.getString("travel_desk_type")));
                t.setFilepath(ff.getString("path"));
                t.setLocid(ff.getString("locid"));
                t.setFilepath(ff.getString("path"));
               
		        	
                
                String sp = "select division.DIV_DESC as spon from travel_desk ,division where reqNo='"+typ.getRequestNumber()+"' and division.DIV_CODE=travel_Desk.spon_div";
                ResultSet rs1 = ad.selectQuery(sp);
                String spon="";
                while(rs1.next())
                {
              		t.setSpon_div(rs1.getString("spon"));
                }
                  
                
               
                
                //for self and multiple travel for
                TravelFor=ff.getString("Travel_for");
                if(((TravelFor.equalsIgnoreCase("Self") ||TravelFor.equalsIgnoreCase("Multiple"))&&(Travel_Request_for.equalsIgnoreCase("one way")||Travel_Request_for.equalsIgnoreCase("Round Trip"))))
                	
                {
                	  t.setAccom_type(ff.getString("accom_type"));
                      t.setAccom_name(ff.getString("accom_name"));
                      t.setHotel_Res(ff.getString("hotel_Res"));
                      t.setHotel_City(ff.getString("hotel_City"));
                      t.setHotel_Name(ff.getString("hotel_Name"));
                     
                      t.setRent_Car(ff.getString("rent_Car"));
                      t.setPickup_Details(ff.getString("pickup_Details"));
                      t.setDrop_Details(ff.getString("drop_Details"));
                      tra.add(t);
                     
                      request.setAttribute("tra", tra);
                      
                }
                
                if(filterBy==null)
                {
                	filterBy=ff.getString("Req_status");
                }
				
                
                t.setId(ff.getInt("id"));
                if(ff.getString("review_Trip")==null)
                {	
                if(review.equalsIgnoreCase("1"))
                {
                   request.setAttribute("review", "review");
                }
                }
                
                
                
                String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1,CONVERT(varchar(10),departOn,103)  as departOn,CONVERT(varchar(10),returnOn,103)  as returnOn,* from"
                		+ " Travel_Desk_Travellers where req_id='"+t.getId()+"' order by id" ;
                ResultSet b=ad.selectQuery(his);
             
        		try {
        			while(b.next())
        			{
        					TravelRequestForm emp=new TravelRequestForm();
        					String ere[]=empDet(Integer.toString(b.getInt("PERNR")));
        					emp.setGuest_pernr(Integer.toString(b.getInt("PERNR")));
        					emp.setEmployeeName(ere[0]);
        					emp.setLocationId(ere[1]);
        					emp.setDepartment(ere[2]);
        					emp.setDesignation(ere[3]); 
        					emp.setUserAge(ere[5]);
        					emp.setUserGender(ere[7]);                		
        					emp.setGuest_Type(b.getString("Req_type"));
        					emp.setPassportno(b.getString("Passport_no"));
        					emp.setPassportplace(b.getString("Place_issue"));
        					emp.setPassportexpirydate(b.getString("Date_of_expiry"));
        					emp.setGuestName(b.getString("Name_of_guest"));
        					emp.setGuestContactNo(b.getString("Contact_guest"));
        					emp.setGuest_Company(b.getString("guest_Company"));
        					emp.setGuestAge(b.getString("Age_guest"));
        					emp.setGender(b.getString("Gender_guest"));
        					emp.setEmail_Guest(b.getString("Email_guest"));
        					emp.setFileName(b.getString("file_name"));
        					emp.setFileFullPath(b.getString("path"));
        					emp.setGuest_Title(b.getString("guest_Title"));
        					emp.setGuest_Visano(b.getString("guest_Visano"));
        					emp.setGuest_DOB(b.getString("guest_DOB"));
        					emp.setGuest_Meal(b.getString("guest_Meal"));
        					emp.setId(b.getInt("id"));
        					emp.setAccom_type(b.getString("accom_type"));
        					emp.setAccom_name(b.getString("accom_name"));
        					emp.setHotel_Res(b.getString("hotel_Res"));
        					emp.setHotel_City(b.getString("hotel_City"));
        					emp.setHotel_Name(b.getString("hotel_Name"));
        					 emp.setRent_Car(b.getString("rent_Car"));
        					  emp.setPickup_Details(b.getString("pickup_Details"));
        		                emp.setDrop_Details(b.getString("drop_Details"));
        					if(!b.getString("Date_of_expiry1").contains("1900"))
        					emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
        					else
        			    	emp.setUserpassportexpirydate("");
        					emp.setFilepath(ff.getString("path"));
        					emp.setTravel_desk_type(Empname(ff.getString("travel_desk_type")));
        					emp.setFromPlace(b.getString("fromPlace"));
        					emp.setToPlace(b.getString("toPlace"));
        					emp.setDepartOn(b.getString("departOn"));
    						emp.setReturnOn(b.getString("returnOn"));
        		            PDFTravelgenerate(mapping, emp, request, response);
        		            emp.setFileFullPath("/EMicro Files/ESS/Travel Request/PDF/TR_"+reqno+".pdf");
        		        	emplist.add(emp);
        				
        			}
        		} catch (SQLException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
        		
        	
        		if(emplist.size()>0)
                	request.setAttribute("emplist", emplist);
                else
        		request.setAttribute("emplist", "");
             
                
			    String his1="select Travel_Multiple_City.id as id1 ,isnull(convert(nvarchar(10),Travel_Multiple_City.trav_id)+'-'+emp_official_info.EMP_FULLNAME,0) as id,Travel_Multiple_City.Location,"
			    		+ " Travel_Multiple_City.Air_preference,convert(nvarchar(10),Travel_Multiple_City.Arrival_date,103) as Arrival_date,"
			    		+ " Travel_Multiple_City.Arrival_time,convert(nvarchar(10),Travel_Multiple_City.Departure_date,103) as Departure_date,"
			    		+ " Travel_Multiple_City.Departure_time ,Travel_Desk_Travellers.Name_of_guest,Travel_Multiple_City.mhotel_Res,Travel_Multiple_City.mrent_Car,Travel_Multiple_City.mhotel_Name,"
			    		+ " Travel_Multiple_City.mhotel_City,Travel_Desk_Travellers.Req_type,Travel_Multiple_City.mpickup_details,Travel_Multiple_City.mdrop_Details"
			    		+ " from Travel_Multiple_City left outer join Travel_Desk_Travellers on Travel_Multiple_City.trav_id=Travel_Desk_Travellers.id and Travel_Desk_Travellers.id!=0"
			    		+ " left outer join emp_official_info on emp_official_info.PERNR=Travel_Desk_Travellers.PERNR"
			    		+ " where Travel_Multiple_City.req_id='"+t.getId()+"'" ; ;
                ResultSet b1=ad.selectQuery(his1);
        		try {
        			while(b1.next())
        			{
        					TravelRequestForm emp=new TravelRequestForm();
        					emp.setMultiemployeeno(b1.getString("id"));
        					emp.setId(b1.getInt("id1"));
        					emp.setLocationId(b1.getString("location"));
        					emp.setAirline_Pref(b1.getString("air_preference"));
        					emp.setTrip_From_Date(b1.getString("Arrival_date"));
        					emp.setTrip_To_time(b1.getString("Arrival_Time"));
        					emp.setTrip_To_Date(b1.getString("Departure_date"));
        					emp.setTrip_To_time(b1.getString("Departure_time"));
        					
        					
        					emp.setGuest_Type(b1.getString("Req_type"));
        					emp.setGuestName(b1.getString("Name_of_guest"));
        					emp.setHotel_Res(b1.getString("mhotel_Res"));
        					emp.setRent_Car(b1.getString("mrent_Car"));
        					emp.setHotel_Name(b1.getString("mhotel_Name"));
        					emp.setHotel_City(b1.getString("mhotel_City"));
        					emp.setPickup_Details(b1.getString("mpickup_Details"));
        					emp.setDrop_Details(b1.getString("mdrop_Details"));
        					city.add(emp);
        				
        			}
        		} catch (SQLException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
        		
        		if(city.size()>0)
                	request.setAttribute("city", city);
                
        		String monthplan="select tm.plan_date,tm.morigin,tm.mdestination,tm.mtravelmode,tm.mtraveltype,tm.mtraveldesk,tm.mremarks from Travel_MTP as tm,Travel_Desk as td where td.reqNo='"+ reqno+"' and td.id=tm.req_id order by plan_date";
				List appList1 = new LinkedList();
		ResultSet rsAppr1 = ad.selectQuery(monthplan);
		try {
			while (rsAppr1.next()) {
				TravelRequestForm appr=new TravelRequestForm();
				
				String d[]=rsAppr1.getString("plan_date").split("-");
				String dateString = d[2]+"/"+d[1]+"/"+d[0];;
				
				appr.setFromdate(dateString);
				
				
				//appr.setFromdate(rsAppr1.getString("plan_date"));
				appr.setOrigin(rsAppr1.getString("morigin"));
				appr.setDeparture(rsAppr1.getString("mdestination"));
				appr.setTravelmode(rsAppr1.getString("mtravelmode"));
				appr.setTraveltype(rsAppr1.getString("mtraveltype"));
				appr.setTravel_desk_type(rsAppr1.getString("mtraveldesk"));
				
				appr.setRemarks(rsAppr1.getString("mremarks"));
				appList1.add(appr);
			}
			if(appList1.size()>0)
			request.setAttribute("appList1", appList1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	
               
        		travel.add(t);
        		 PDFTravelgenerate(mapping, t, request, response);
		            t.setFileFullPath("/EMicro Files/ESS/Travel Request/PDF/TR_"+reqno+".pdf");
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		request.setAttribute("travel", travel);
		
		String app = "";

		/*if (filterBy.equalsIgnoreCase("Pending")) {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Travel' and a.Req_Id='"
					+ reqno
					+ "' and e.PERNR=a.Pending_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID "
					+ "union "
					 +"select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Travel' and a.Req_Id='"
						+ reqno
						+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";

		} else {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where  Req_Type='Travel' and a.Req_Id='"
					+ reqno
					+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";
				

		}
		List appList = new LinkedList();
		ResultSet rsAppr = ad.selectQuery(app);
		String duplicate="";
		try {
			while (rsAppr.next()) {
				ApprovalsForm appr = new ApprovalsForm();
				appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
				appr.setDesignation(rsAppr.getString("DSGSTXT"));
				appr.setApproveStatus(rsAppr.getString("Req_Status"));
				String approvaStatus = rsAppr.getString("Req_Status");
				if (approvaStatus.equals("Pending")) {
					appr.setApproveDate("");
					appr.setComments("");
				}
				if (approvaStatus.equals("Approved")
						|| approvaStatus.equals("Completed")) {
					appr.setApproveDate(rsAppr.getString("approved_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				if (approvaStatus.equals("Rejected")
						|| approvaStatus.equals("Cancelled")) {
					appr.setApproveDate(rsAppr.getString("rejected_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				
				if(duplicate==null)
				{
					duplicate="";
				}
				if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))
					appList.add(appr);
					duplicate=rsAppr.getString("approved_date");
			}
			request.setAttribute("appList", appList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String getdet="select CONVERT(varchar(10),submitted_date,103)+' '+ CONVERT(varchar(5),submitted_date,108) as submitted_date,CONVERT(varchar(10),approved_date,103)+' '+ CONVERT(varchar(5),approved_date,108) as approved_date,CONVERT(varchar(10),Reverted_on,103)+' '+ CONVERT(varchar(5),Reverted_on,108) as Reverted_on,CONVERT(varchar(10),rejected_date,103)+' '+ CONVERT(varchar(5),rejected_date,108) as rejected_date,* from travel_request_history where req_id='"+reqno+"'";
		LinkedList appList = new LinkedList();
		ResultSet rsAppr = ad.selectQuery(getdet);
		String duplicate="";
		try {
			while (rsAppr.next()) {
				ApprovalsForm appr = new ApprovalsForm();
			/*	appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
				appr.setDesignation(rsAppr.getString("DSGSTXT"));*/
				appr.setRole(rsAppr.getString("Role"));
				
				String approvaStatus = rsAppr.getString("Req_Status");
				if (approvaStatus.equals("Pending")) {
					
					int emp =Integer.parseInt(rsAppr.getString("Requester_Name"));
					appr.setApproveDate("");
					appr.setComments("");
					appr.setApproveStatus("Submitted");
					
					String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
					ResultSet rr  =ad.selectQuery(empx);
					while(rr.next())
					{
						appr.setApprover(rr.getString("EMP_FULLNAME"));
						appr.setDesignation(rr.getString("DSGSTXT"));
						appr.setApproveDate(rsAppr.getString("submitted_date"));
					}
					
					
				}
				
					if (approvaStatus.equalsIgnoreCase("Travel Review")) {
					
					int emp =Integer.parseInt(rsAppr.getString("Requester_Name"));
					appr.setApproveDate("");
					appr.setComments("");
					appr.setApproveStatus(rsAppr.getString("Req_Status"));
					
					String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
					ResultSet rr  =ad.selectQuery(empx);
					while(rr.next())
					{
						appr.setApprover(rr.getString("EMP_FULLNAME"));
						appr.setDesignation(rr.getString("DSGSTXT"));
						appr.setApproveDate(rsAppr.getString("submitted_date"));
						appr.setComments(rsAppr.getString("Comments"));
					}
					
					
				}
				if (approvaStatus.equals("Approved")||approvaStatus.equalsIgnoreCase("Booking Accepted")||approvaStatus.equalsIgnoreCase("Bills Updated")
						||approvaStatus.equalsIgnoreCase("Submitted To Finance")|| approvaStatus.equalsIgnoreCase("Received By finance")|| approvaStatus.equalsIgnoreCase("Verified By finance")||approvaStatus.equals("Completed")) {
					appr.setApproveStatus(rsAppr.getString("Req_Status"));
					
					int emp =Integer.parseInt(rsAppr.getString("Actual_Approver"));
					appr.setApproveDate(rsAppr.getString("approved_date"));
					appr.setComments(rsAppr.getString("Comments"));
					String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
					ResultSet rr  =ad.selectQuery(empx);
					while(rr.next())
					{
						appr.setApprover(rr.getString("EMP_FULLNAME"));
						appr.setDesignation(rr.getString("DSGSTXT"));
						
					}
				}
				
				if (approvaStatus.equals("Reverted")
						|| approvaStatus.equals("Completed")) {
					appr.setApproveStatus(rsAppr.getString("Req_Status"));
					int emp =Integer.parseInt(rsAppr.getString("Actual_Approver"));
					appr.setApproveDate(rsAppr.getString("Reverted_on"));
					appr.setComments(rsAppr.getString("Comments"));
					
					String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
					ResultSet rr  =ad.selectQuery(empx);
					while(rr.next())
					{
						appr.setApprover(rr.getString("EMP_FULLNAME"));
						appr.setDesignation(rr.getString("DSGSTXT"));
						
					}
				}
				if (approvaStatus.equals("Rejected")
						|| approvaStatus.equals("Cancelled")) {
					appr.setApproveStatus(rsAppr.getString("Req_Status"));
					int emp =Integer.parseInt(rsAppr.getString("Actual_Approver"));
					appr.setApproveDate(rsAppr.getString("rejected_date"));
					appr.setComments(rsAppr.getString("Comments"));
					String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"'";
					ResultSet rr  =ad.selectQuery(empx);
					while(rr.next())
					{
						appr.setApprover(rr.getString("EMP_FULLNAME"));
						appr.setDesignation(rr.getString("DSGSTXT"));
						
					}
				}
				/*if(duplicate!=null)
				if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))*/
					appList.add(appr);
					duplicate=rsAppr.getString("approved_date");
			}
			request.setAttribute("appList", appList);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		InputStream in = ConnectionFactory.class.getClassLoader()
				.getResourceAsStream("db.properties");
		Properties props = new Properties();
		try {
			props.load(in);
		
		in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mapping.findForward("viewmyrequest");
	}
		
	public ActionForward ViewBookingrequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//Created by Arun .Date:15-11-2016
		
		TravelRequestForm typ =(TravelRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String filterBy = request.getParameter("requststatus");
		String reqno=request.getParameter("requstNo");
		typ.setRequestNumber(Integer.parseInt(reqno));
		ArrayList data=new ArrayList();
		ArrayList tra=new ArrayList();
		ArrayList emplist=new ArrayList();
		ArrayList guestlist=new ArrayList();
		String reqType="";
		ArrayList travel=new ArrayList();
		ArrayList city=new ArrayList();

		String lis="select CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1, CONVERT(varchar(10),billdate,103) as billdate1 ,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as  bookeddate1,"
				+ "  CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1   ,CONVERT(varchar(10),Depart_On,103)  as departon,"
				+ "CONVERT(varchar(10),Return_On,103)  as returnon,* from Travel_Desk,DIVISION where reqNo='"+typ.getRequestNumber()+"'";
		ResultSet ff=ad.selectQuery(lis);
		try {
			if(ff.next())
			{
				TravelRequestForm t=new TravelRequestForm();
				
				String re[]=empDet(Integer.toString(ff.getInt("pernr")));
	
				
				t.setEmployeeno(user.getEmployeeNo());
				t.setEmployeeName(re[0]);
				t.setLocationId(re[1]);
				t.setDepartment(re[2]);
				t.setDesignation(re[3]); 
				t.setUserAge(re[5]);
				t.setUsermailId(re[6]);
				t.setUserGender(re[7]);
				
				

String pe[]=empPERDet(Integer.toString(ff.getInt("pernr")));

				t.setUsermobno(pe[0]);
				t.setUserpassportno(pe[1]);
				t.setUserpassportplace(pe[2]);
				t.setUserPersonalmailId(pe[5]);
				
				
				if(!pe[3].contains("1900"))
					t.setUserpassportissuedate(pe[3]); 
					else
				    t.setUserpassportissuedate(""); 
					
					if(!pe[4].contains("1900"))
					t.setUserpassportexpirydate(pe[4]); 
					else
				    t.setUserpassportexpirydate(""); 
			
				typ.setTravelmode(ff.getString("travelmode"));	
				t.setRequestNumber(ff.getInt("reqNo"));
				typ.setRequestNumber(ff.getInt("reqNo"));
				
				t.setReqType(ff.getString("Req_type"));
				t.setTypeOfTravel(ff.getString("Type_of_travel"));
				t.setTravelFor(ff.getString("Travel_for"));
				t.setTravelRequestFor(ff.getString("Travel_Request_for"));
				
				String Travel_Request_for=ff.getString("Travel_Request_for");
				t.setModeOfTravel(ff.getString("Mode_of_travel"));
				t.setLocid(ff.getString("locid"));
				t.setFromPlace(ff.getString("From_place"));
				t.setDepartOn(ff.getString("departon"));
				t.setDepartTime(ff.getString("Preferred_depart_time"));
				t.setReturnTime(ff.getString("Preferred_return_time"));	
				t.setDepartRemarks(ff.getString("Depart_Time_remarks"));
				t.setReturnRemarks(ff.getString("Return_Time_remarks"));
				t.setToPlace(ff.getString("To_place"));
                t.setVia(ff.getString("via"));
                t.setRemarks(ff.getString("Remarks"));
                t.setPurposeOfVisit(ff.getString("Purpose"));
                t.setReturnOn(ff.getString("returnon"));
                t.setTravel_Adult(ff.getString("Travel_Adult"));
                t.setTravel_Child(ff.getString("Travel_Child"));
                t.setTravel_Infant(ff.getString("travel_Infant"));
                t.setPurposetext(ff.getString("purposetext"));
                t.setTravel_Days(ff.getString("travel_Days"));
                t.setAirline_Pref(ff.getString("airline_Pref"));
                t.setAirline_Just(ff.getString("airline_Just"));
              
                
                
                String sp = "select division.DIV_DESC as spon from travel_desk ,division where reqNo='"+typ.getRequestNumber()+"' and division.DIV_CODE=travel_Desk.spon_div";
                ResultSet rs1 = ad.selectQuery(sp);
                String spon="";
                while(rs1.next())
                {
              		t.setSpon_div(rs1.getString("spon"));
                }
                  
              //  t.setSpon_div(ff.getString("spon"));
                t.setBud_code(ff.getString("bud_code"));
                t.setEst_trip_cose(ff.getString("est_trip_cose"));
              //  t.setHotel_Res(ff.getString("hotel_Res"));
                t.setOnduty_Req(ff.getString("onduty_Req"));
               // t.setRent_Car(ff.getString("rent_Car"));
              //  t.setPickup_Details(ff.getString("pickup_Details"));
               // t.setDrop_Details(ff.getString("drop_Details"));
                t.setTrip_Advance(ff.getString("trip_Advance"));
                t.setTrip_Amt(ff.getString("trip_Amt"));
                t.setTrip_Currency(ff.getString("trip_Currency"));
                t.setTrip_From_Date(ff.getString("trip_From_Date"));
                t.setTrip_To_Date(ff.getString("trip_To_Date"));
                t.setTrip_From_Time(ff.getString("trip_From_Time"));
                t.setTrip_To_time(ff.getString("trip_To_time"));
                t.setTrip_Priority(ff.getString("trip_Priority"));
                t.setPurposetype(ff.getString("purposetype"));
                t.setTravelmode(ff.getString("travelmode"));
                t.setTraveltype(ff.getString("Type_of_travel"));
                t.setService_class(ff.getString("service_class"));
                t.setReqDate(ff.getString("Created_date1"));
				
				
				t.setTravelagentname(ff.getString("travelagentname"));
               t.setBillno(ff.getString("billno"));
               t.setBilldate(ff.getString("billdate1"));
               typ.setBilldate(ff.getString("billdate1"));
               t.setBillamount(ff.getString("billamount"));
               t.setBookingstatus(ff.getString("bookingstatus"));
               t.setBookeddate(ff.getString("bookeddate1"));
               t.setReqDate(ff.getString("Created_date1"));
               t.setBookedby(Empname(ff.getString("bookedby")));
               typ.setBookedby(Empname(ff.getString("bookedby")));
               t.setConfirmdate(ff.getString("confirmdate1"));
               t.setConfirmby(Empname(ff.getString("confirmby")));
               t.setConfirmstatus(ff.getString("confirmstatus"));
               t.setLocid(ff.getString("locid"));
               t.setFilepath(ff.getString("path"));
               
               String TravelFor=ff.getString("Travel_for");
               if(((TravelFor.equalsIgnoreCase("Self") ||TravelFor.equalsIgnoreCase("Multiple"))&&(Travel_Request_for.equalsIgnoreCase("one way")||Travel_Request_for.equalsIgnoreCase("Round Trip"))))
                   
               	
               {
               	  t.setAccom_type(ff.getString("accom_type"));
                     t.setAccom_name(ff.getString("accom_name"));
                     t.setHotel_Res(ff.getString("hotel_Res"));
                     t.setHotel_City(ff.getString("hotel_City"));
                     t.setHotel_Name(ff.getString("hotel_Name"));
                    
                     t.setRent_Car(ff.getString("rent_Car"));
                     t.setPickup_Details(ff.getString("pickup_Details"));
                     t.setDrop_Details(ff.getString("drop_Details"));
                     tra.add(t);
                     request.setAttribute("tra", tra);
                     
               }
				
                
                t.setId(ff.getInt("id"));
                
                
                
                String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1 ,CONVERT(varchar(10),departon,103)  as departon,"
				+ "CONVERT(varchar(10),returnon,103)  as returnon,* from"
                		+ " Travel_Desk_Travellers where req_id='"+t.getId()+"'" ;
                ResultSet b=ad.selectQuery(his);
             
        		try {
        			while(b.next())
        			{
        					TravelRequestForm emp=new TravelRequestForm();
        					String ere[]=empDet(Integer.toString(b.getInt("PERNR")));
        					emp.setGuest_pernr(Integer.toString(b.getInt("PERNR")));
        					emp.setEmployeeName(ere[0]);
        					emp.setLocationId(ere[1]);
        					emp.setDepartment(ere[2]);
        					emp.setDesignation(ere[3]); 
        					emp.setUserAge(ere[5]);
        					emp.setUserGender(ere[7]);                		
        					emp.setGuest_Type(b.getString("Req_type"));
        					emp.setPassportno(b.getString("Passport_no"));
        					emp.setPassportplace(b.getString("Place_issue"));
        					emp.setPassportexpirydate(b.getString("Date_of_expiry"));
        					emp.setGuestName(b.getString("Name_of_guest"));
        					emp.setGuestContactNo(b.getString("Contact_guest"));
        					emp.setGuest_Company(b.getString("guest_Company"));
        					emp.setGuestAge(b.getString("Age_guest"));
        					emp.setGender(b.getString("Gender_guest"));
        					emp.setEmail_Guest(b.getString("Email_guest"));
        					emp.setFileName(b.getString("file_name"));
        					emp.setFileFullPath(b.getString("path"));
        					emp.setGuest_Title(b.getString("guest_Title"));
        					emp.setGuest_Visano(b.getString("guest_Visano"));
        					emp.setGuest_DOB(b.getString("guest_DOB"));
        					emp.setGuest_Meal(b.getString("guest_Meal"));
        					emp.setFromPlace(b.getString("fromPlace"));
        					emp.setToPlace(b.getString("toPlace"));
        					emp.setDepartOn(b.getString("departOn"));
    						emp.setReturnOn(b.getString("returnOn"));
        					emp.setAccom_type(b.getString("accom_type"));
        					emp.setAccom_name(b.getString("accom_name"));
        					emp.setHotel_Res(b.getString("hotel_Res"));
        					emp.setHotel_City(b.getString("hotel_City"));
        					emp.setHotel_Name(b.getString("hotel_Name"));
        					 emp.setRent_Car(b.getString("rent_Car"));
        					  emp.setPickup_Details(b.getString("pickup_Details"));
        		                emp.setDrop_Details(b.getString("drop_Details"));
        		                
        					emp.setId(b.getInt("id"));
        					if(!b.getString("Date_of_expiry1").contains("1900"))
        					emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
        					else
        			    	emp.setUserpassportexpirydate("");
        					emplist.add(emp);
        					
        					
        					emp.setFilepath(ff.getString("path"));
        					emp.setTravel_desk_type(Empname(ff.getString("travel_desk_type")));
        		                PDFTravelgenerate(mapping, t, request, response);
        		        		
        		                emp.setFileFullPath("/EMicro Files/ESS/Travel Request/PDF/TR_"+reqno+".pdf");
        				
        			}
        		} catch (SQLException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
        		
        		
        		/*String his1="select * from"
                		+ " Travel_Multiple_City where req_id='"+t.getId()+"'" ;
                ResultSet b1=ad.selectQuery(his1);*/

        		String his1 ="select trav.Name_of_guest,trav.Req_type,* from Travel_Multiple_City as multi,Travel_Desk_Travellers as trav where trav.req_id=multi.req_id and multi.req_id='"+t.getId()+"' and trav.id=multi.trav_id";
                ResultSet b1=ad.selectQuery(his1);
                
        		try {
        			while(b1.next())
        			{
        					TravelRequestForm emp=new TravelRequestForm();
        					emp.setLocationId(b1.getString("location"));
        					emp.setAirline_Pref(b1.getString("air_preference"));
        					emp.setTrip_From_Date(b1.getString("Arrival_date"));
        					emp.setTrip_To_time(b1.getString("Arrival_Time"));
        					emp.setTrip_To_Date(b1.getString("Departure_date"));
        					emp.setTrip_To_time(b1.getString("Departure_time"));
        					
        					emp.setGuest_Type(b1.getString("Req_type"));
        					emp.setGuestName(b1.getString("Name_of_guest"));
        					emp.setHotel_Res(b1.getString("mhotel_Res"));
        					emp.setRent_Car(b1.getString("mrent_Car"));
        					emp.setHotel_Name(b1.getString("mhotel_Name"));
        					emp.setHotel_City(b1.getString("mhotel_City"));
        					emp.setPickup_Details(b1.getString("mpickup_Details"));
        					emp.setDrop_Details(b1.getString("mdrop_Details"));
        					
        					city.add(emp);
        				
        			}
        		} catch (SQLException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
                
        		
        		if(city.size()>0)
                	request.setAttribute("city", city);
        		
        		if(emplist.size()>0)
                	request.setAttribute("emplist", emplist);
               
        		String monthplan="select tm.plan_date,tm.morigin,tm.mdestination,tm.mtravelmode,tm.mtraveltype,tm.mremarks from Travel_MTP as tm,Travel_Desk as td where td.reqNo='"+ reqno+"' and td.id=tm.req_id order by plan_date";
				List appList1 = new LinkedList();
		ResultSet rsAppr1 = ad.selectQuery(monthplan);
		try {
			while (rsAppr1.next()) {
				TravelRequestForm appr=new TravelRequestForm();
				
				String d[]=rsAppr1.getString("plan_date").split("-");
				String dateString = d[2]+"/"+d[1]+"/"+d[0];;
				
				appr.setFromdate(dateString);
				
				
				//appr.setFromdate(rsAppr1.getString("plan_date"));
				appr.setOrigin(rsAppr1.getString("morigin"));
				appr.setDeparture(rsAppr1.getString("mdestination"));
				appr.setTravelmode(rsAppr1.getString("mtravelmode"));
				appr.setTraveltype(rsAppr1.getString("mtraveltype"));
				appr.setRemarks(rsAppr1.getString("mremarks"));
				appList1.add(appr);
			}
			if(appList1.size()>0)
			request.setAttribute("appList1", appList1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        		
        		travel.add(t);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		request.setAttribute("travel", travel);
		String app = "";

		/*if (filterBy.equalsIgnoreCase("Pending")) {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Travel' and a.Req_Id='"
					+ reqno
					+ "' and e.PERNR=a.Pending_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID "
					+ "union "
					 +"select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Travel' and a.Req_Id='"
						+ reqno
						+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";

		} else {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where  Req_Type='Travel' and a.Req_Id='"
					+ reqno
					+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";
				

		}
		List appList = new LinkedList();
		ResultSet rsAppr = ad.selectQuery(app);
		String duplicate="";
		try {
			while (rsAppr.next()) {
				ApprovalsForm appr = new ApprovalsForm();
				appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
				appr.setDesignation(rsAppr.getString("DSGSTXT"));
				appr.setApproveStatus(rsAppr.getString("Req_Status"));
				String approvaStatus = rsAppr.getString("Req_Status");
				if (approvaStatus.equals("Pending")) {
					appr.setApproveDate("");
					appr.setComments("");
				}
				if (approvaStatus.equals("Approved")
						|| approvaStatus.equals("Completed")) {
					appr.setApproveDate(rsAppr.getString("approved_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				if (approvaStatus.equals("Rejected")
						|| approvaStatus.equals("Cancelled")) {
					appr.setApproveDate(rsAppr.getString("rejected_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				
				if(duplicate==null)
				{
					duplicate="";
				}
				if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))
					appList.add(appr);
					duplicate=rsAppr.getString("approved_date");
			}
			request.setAttribute("appList", appList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		String getdet="select CONVERT(varchar(10),submitted_date,103)+' '+ CONVERT(varchar(5),submitted_date,108) as submitted_date,CONVERT(varchar(10),approved_date,103)+' '+ CONVERT(varchar(5),approved_date,108) as approved_date,CONVERT(varchar(10),Reverted_on,103)+' '+ CONVERT(varchar(5),Reverted_on,108) as Reverted_on,CONVERT(varchar(10),rejected_date,103)+' '+ CONVERT(varchar(5),rejected_date,108) as rejected_date,* from travel_request_history where req_id='"+reqno+"'";
		/*	String getdet="select * from travel_request_history where req_id='"+reqId+"'";*/
			LinkedList appList = new LinkedList();
			ResultSet rsAppr = ad.selectQuery(getdet);
			String duplicate="";
			try {
				while (rsAppr.next()) {
					ApprovalsForm appr = new ApprovalsForm();
				/*	appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
					appr.setDesignation(rsAppr.getString("DSGSTXT"));*/
					appr.setRole(rsAppr.getString("Role"));
					
					String approvaStatus = rsAppr.getString("Req_Status");
					if (approvaStatus.equals("Pending")) {
						
						int emp =Integer.parseInt(rsAppr.getString("Requester_Name"));
						appr.setApproveDate("");
						appr.setComments("");
						appr.setApproveStatus("Submitted");
						
						String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
						ResultSet rr  =ad.selectQuery(empx);
						while(rr.next())
						{
							appr.setApprover(rr.getString("EMP_FULLNAME"));
							appr.setDesignation(rr.getString("DSGSTXT"));
							appr.setApproveDate(rsAppr.getString("submitted_date"));
						}
						
						
					}
					
					if (approvaStatus.equalsIgnoreCase("Travel Review")) {
						
						int emp =Integer.parseInt(rsAppr.getString("Requester_Name"));
						appr.setApproveDate("");
						appr.setComments("");
						appr.setApproveStatus(rsAppr.getString("Req_Status"));
						
						String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
						ResultSet rr  =ad.selectQuery(empx);
						while(rr.next())
						{
							appr.setApprover(rr.getString("EMP_FULLNAME"));
							appr.setDesignation(rr.getString("DSGSTXT"));
							appr.setApproveDate(rsAppr.getString("submitted_date"));
							appr.setComments(rsAppr.getString("Comments"));
						}
						
						
					}
					
					if (approvaStatus.equals("Approved")||approvaStatus.equalsIgnoreCase("Booking Accepted")||approvaStatus.equalsIgnoreCase("Bills Updated")||approvaStatus.equalsIgnoreCase("Travel Review")
							||approvaStatus.equalsIgnoreCase("Submitted To Finance")|| approvaStatus.equalsIgnoreCase("Received By finance")|| approvaStatus.equalsIgnoreCase("Verified By finance")||approvaStatus.equals("Completed")) {
						appr.setApproveStatus(rsAppr.getString("Req_Status"));
						
						int emp =Integer.parseInt(rsAppr.getString("Actual_Approver"));
						appr.setApproveDate(rsAppr.getString("approved_date"));
						appr.setComments(rsAppr.getString("Comments"));
						String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
						ResultSet rr  =ad.selectQuery(empx);
						while(rr.next())
						{
							appr.setApprover(rr.getString("EMP_FULLNAME"));
							appr.setDesignation(rr.getString("DSGSTXT"));
							
						}
					}
					
					if (approvaStatus.equals("Reverted")
							|| approvaStatus.equals("Completed")) {
						appr.setApproveStatus(rsAppr.getString("Req_Status"));
						int emp =Integer.parseInt(rsAppr.getString("Actual_Approver"));
						appr.setApproveDate(rsAppr.getString("Reverted_on"));
						appr.setComments(rsAppr.getString("Comments"));
						
						String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
						ResultSet rr  =ad.selectQuery(empx);
						while(rr.next())
						{
							appr.setApprover(rr.getString("EMP_FULLNAME"));
							appr.setDesignation(rr.getString("DSGSTXT"));
							
						}
					}
					if (approvaStatus.equals("Rejected")
							|| approvaStatus.equals("Cancelled")) {
						appr.setApproveStatus(rsAppr.getString("Req_Status"));
						int emp =Integer.parseInt(rsAppr.getString("Actual_Approver"));
						appr.setApproveDate(rsAppr.getString("rejected_date"));
						appr.setComments(rsAppr.getString("Comments"));
						String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"'";
						ResultSet rr  =ad.selectQuery(empx);
						while(rr.next())
						{
							appr.setApprover(rr.getString("EMP_FULLNAME"));
							appr.setDesignation(rr.getString("DSGSTXT"));
							
						}
					}
					/*if(duplicate!=null)
					if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))*/
						appList.add(appr);
						duplicate=rsAppr.getString("approved_date");
				}
				request.setAttribute("appList", appList);
			} catch (SQLException e) {

				e.printStackTrace();
			}
		
		InputStream in = ConnectionFactory.class.getClassLoader()
				.getResourceAsStream("db.properties");
		Properties props = new Properties();
		try {
			props.load(in);
		
		in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String uploadFilePath = props
				.getProperty("file.uploadFilePath");
		String filePath = uploadFilePath
				+ "/EMicro Files/ESS/Travel Request/UploadFiles/"+reqType+"";
		String sql1 = "select * from Travel_documents where request_no='"
				+ reqno
				+ "' and user_id='"
				+ user.getEmployeeNo() + "' ";
		ResultSet rs = ad.selectQuery(sql1);
		TravelRequestForm leaveForm1 = null;
		ArrayList a1 = new ArrayList();

		try {
			while (rs.next()) {
				leaveForm1 = new TravelRequestForm();
				leaveForm1.setFileName(rs.getString("file_name"));
				leaveForm1.setId(rs.getInt("id"));
				leaveForm1.setFileFullPath(filePath + "/"
						+ rs.getString("file_name"));
				a1.add(leaveForm1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("documentDetails", a1);
		
		return mapping.findForward("viewbookingrequest");
	}
	
	
	public ActionForward ViewConfirmationrequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
		TravelRequestForm typ =(TravelRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String filterBy = request.getParameter("requststatus");
		String reqno=request.getParameter("requstNo");
		typ.setRequestNumber(Integer.parseInt(reqno));
		typ.setReqStatus(filterBy);
		ArrayList data=new ArrayList();
		ArrayList emplist=new ArrayList();
		ArrayList guestlist=new ArrayList();
		String reqType="";
		ArrayList travel=new ArrayList();
		ArrayList tra=new ArrayList();
		ArrayList city=new ArrayList();

		String lis="select CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1,"
				+ "CONVERT(varchar(10),Depart_On,103)  as departon,"
				+ "CONVERT(varchar(10),Return_On,103)  as returnon,* from Travel_Desk,division where reqNo='"+reqno+"'";
		ResultSet ff=ad.selectQuery(lis);
		try {
			if(ff.next())
			{
				TravelRequestForm t=new TravelRequestForm();
				
				String re[]=empDet(Integer.toString(ff.getInt("pernr")));
				
				
				
				
				t.setEmployeeno(user.getEmployeeNo());
				t.setEmployeeName(re[0]);
				t.setLocationId(re[1]);
				t.setDepartment(re[2]);
				t.setDesignation(re[3]); 
				t.setUserAge(re[5]);
				t.setUsermailId(re[6]);
				t.setUserGender(re[7]);
				
				String pe[]=empPERDet(Integer.toString(ff.getInt("pernr")));

				t.setUsermobno(pe[0]);
				t.setUserpassportno(pe[1]);
				t.setUserpassportplace(pe[2]);
				t.setUserPersonalmailId(pe[5]);
				
				if(!pe[3].contains("1900"))
					t.setUserpassportissuedate(pe[3]); 
					else
				    t.setUserpassportissuedate(""); 
					
					if(!pe[4].contains("1900"))
					t.setUserpassportexpirydate(pe[4]); 
					else
				    t.setUserpassportexpirydate(""); 
			
			
				t.setRequestNumber(ff.getInt("reqNo"));
				t.setReqType(ff.getString("Req_type"));
				t.setTypeOfTravel(ff.getString("Type_of_travel"));
				t.setTravelFor(ff.getString("Travel_for"));
				t.setTravelRequestFor(ff.getString("Travel_Request_for"));
				String Travel_Request_for=ff.getString("Travel_Request_for");
				t.setModeOfTravel(ff.getString("Mode_of_travel"));
				t.setFromPlace(ff.getString("From_place"));
				t.setDepartOn(ff.getString("departon"));
				t.setDepartTime(ff.getString("Preferred_depart_time"));
				t.setReturnTime(ff.getString("Preferred_return_time"));	
				t.setDepartRemarks(ff.getString("Depart_Time_remarks"));
				t.setReturnRemarks(ff.getString("Return_Time_remarks"));
				t.setToPlace(ff.getString("To_place"));
                t.setVia(ff.getString("via"));
                t.setRemarks(ff.getString("Remarks"));
                t.setPurposeOfVisit(ff.getString("Purpose"));
                t.setReturnOn(ff.getString("returnon"));
                t.setTravel_Adult(ff.getString("Travel_Adult"));
                t.setTravel_Child(ff.getString("Travel_Child"));
                t.setTravel_Infant(ff.getString("travel_Infant"));
                t.setPurposetext(ff.getString("purposetext"));
                t.setTravel_Days(ff.getString("travel_Days"));
                t.setAirline_Pref(ff.getString("airline_Pref"));
                t.setAirline_Just(ff.getString("airline_Just"));
                
                String sp = "select division.DIV_DESC as spon from travel_desk ,division where reqNo='"+typ.getRequestNumber()+"' and division.DIV_CODE=travel_Desk.spon_div";
                ResultSet rs1 = ad.selectQuery(sp);
                String spon="";
                while(rs1.next())
                {
              		t.setSpon_div(rs1.getString("spon"));
                }
                  
                //t.setSpon_div(ff.getString("spon"));
                t.setBud_code(ff.getString("bud_code"));
                t.setEst_trip_cose(ff.getString("est_trip_cose"));
               // t.setHotel_Res(ff.getString("hotel_Res"));
                t.setOnduty_Req(ff.getString("onduty_Req"));
                //t.setRent_Car(ff.getString("rent_Car"));
               // t.setPickup_Details(ff.getString("pickup_Details"));
               // t.setDrop_Details(ff.getString("drop_Details"));
                t.setTrip_Advance(ff.getString("trip_Advance"));
                t.setTrip_Amt(ff.getString("trip_Amt"));
                t.setTrip_Currency(ff.getString("trip_Currency"));
                t.setTrip_From_Date(ff.getString("trip_From_Date"));
                t.setTrip_To_Date(ff.getString("trip_To_Date"));
                t.setTrip_From_Time(ff.getString("trip_From_Time"));
                t.setTrip_To_time(ff.getString("trip_To_time"));
                t.setTrip_Priority(ff.getString("trip_Priority"));
                t.setPurposetype(ff.getString("purposetype"));
                t.setTravelmode(ff.getString("travelmode"));
                t.setTraveltype(ff.getString("Type_of_travel"));
                t.setService_class(ff.getString("service_class"));
                t.setReqDate(ff.getString("Created_date1"));
                t.setLocid(ff.getString("locid"));
                t.setFilepath(ff.getString("path"));
                
               String TravelFor=ff.getString("Travel_for");
               if(((TravelFor.equalsIgnoreCase("Self") ||TravelFor.equalsIgnoreCase("Multiple"))&&(Travel_Request_for.equalsIgnoreCase("one way")||Travel_Request_for.equalsIgnoreCase("Round Trip"))))
                   
                	
                {
                	  t.setAccom_type(ff.getString("accom_type"));
                      t.setAccom_name(ff.getString("accom_name"));
                      t.setHotel_Res(ff.getString("hotel_Res"));
                      t.setHotel_City(ff.getString("hotel_City"));
                      t.setHotel_Name(ff.getString("hotel_Name"));
                     
                      t.setRent_Car(ff.getString("rent_Car"));
                      t.setPickup_Details(ff.getString("pickup_Details"));
                      t.setDrop_Details(ff.getString("drop_Details"));
                      tra.add(t);
                      request.setAttribute("tra", tra);
                      
                }
                
                
                t.setConfirmstatus(ff.getString("confirmstatus"));
                typ.setConfirmstatus(ff.getString("confirmstatus"));
                
                t.setId(ff.getInt("id"));
                
                t.setFilepath(ff.getString("path"));
                t.setTravel_desk_type(Empname(ff.getString("travel_desk_type")));
                PDFTravelgenerate(mapping, t, request, response);
        		
        		t.setFileFullPath("/EMicro Files/ESS/Travel Request/PDF/TR_"+reqno+".pdf");
                
                
                
                String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1,CONVERT(varchar(10),departon,103)  as departon,"
				+ "CONVERT(varchar(10),returnon,103)  as returnon,* from"
                		+ " Travel_Desk_Travellers where req_id='"+t.getId()+"' order by id" ;
                ResultSet b=ad.selectQuery(his);
             
        		try {
        			while(b.next())
        			{
        					TravelRequestForm emp=new TravelRequestForm();
        					String ere[]=empDet(Integer.toString(b.getInt("PERNR")));
        					emp.setGuest_pernr(Integer.toString(b.getInt("PERNR")));
        					emp.setEmployeeName(ere[0]);
        					emp.setLocationId(ere[1]);
        					emp.setDepartment(ere[2]);
        					emp.setDesignation(ere[3]); 
        					emp.setUserAge(ere[5]);
        					emp.setUserGender(ere[7]);                		
        					emp.setGuest_Type(b.getString("Req_type"));
        					emp.setPassportno(b.getString("Passport_no"));
        					emp.setPassportplace(b.getString("Place_issue"));
        					emp.setPassportexpirydate(b.getString("Date_of_expiry"));
        					emp.setGuestName(b.getString("Name_of_guest"));
        					emp.setGuestContactNo(b.getString("Contact_guest"));
        					emp.setGuest_Company(b.getString("guest_Company"));
        					emp.setGuestAge(b.getString("Age_guest"));
        					emp.setGender(b.getString("Gender_guest"));
        					emp.setEmail_Guest(b.getString("Email_guest"));
        					emp.setFileName(b.getString("file_name"));
        					emp.setFileFullPath(b.getString("path"));
        					emp.setGuest_Title(b.getString("guest_Title"));
        					emp.setGuest_Visano(b.getString("guest_Visano"));
        					emp.setGuest_DOB(b.getString("guest_DOB"));
        					emp.setGuest_Meal(b.getString("guest_Meal"));
        					emp.setId(b.getInt("id"));
        					
        					emp.setAccom_type(b.getString("accom_type"));
        					emp.setAccom_name(b.getString("accom_name"));
        					emp.setHotel_Res(b.getString("hotel_Res"));
        					emp.setHotel_City(b.getString("hotel_City"));
        					emp.setHotel_Name(b.getString("hotel_Name"));
        					 emp.setRent_Car(b.getString("rent_Car"));
        					  emp.setPickup_Details(b.getString("pickup_Details"));
        					  emp.setDrop_Details(b.getString("drop_Details"));
        					  emp.setFromPlace(b.getString("fromPlace"));
          					emp.setToPlace(b.getString("toPlace"));
          					emp.setDepartOn(b.getString("departOn"));
      						emp.setReturnOn(b.getString("returnOn"));
        					if(!b.getString("Date_of_expiry1").contains("1900"))
        					emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
        					else
        			    	emp.setUserpassportexpirydate("");
        					emplist.add(emp);
        				
        			}
        		} catch (SQLException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
        		
        		
        		/*String his1="select * from"
                		+ " Travel_Multiple_City where req_id='"+t.getId()+"'" ;
                ResultSet b1=ad.selectQuery(his1);*/

        		String his1 ="select trav.Name_of_guest,trav.Req_type,* from Travel_Multiple_City as multi,Travel_Desk_Travellers as trav where trav.req_id=multi.req_id and multi.req_id='"+t.getId()+"' and trav.id=multi.trav_id";
                ResultSet b1=ad.selectQuery(his1);
        		try {
        			while(b1.next())
        			{
        					TravelRequestForm emp=new TravelRequestForm();
        					emp.setLocationId(b1.getString("location"));
        					emp.setAirline_Pref(b1.getString("air_preference"));
        					emp.setTrip_From_Date(b1.getString("Arrival_date"));
        					emp.setTrip_To_time(b1.getString("Arrival_Time"));
        					emp.setTrip_To_Date(b1.getString("Departure_date"));
        					emp.setTrip_To_time(b1.getString("Departure_time"));
        					
        					
        					emp.setGuest_Type(b1.getString("Req_type"));
        					emp.setGuestName(b1.getString("Name_of_guest"));
        					emp.setHotel_Res(b1.getString("mhotel_Res"));
        					emp.setRent_Car(b1.getString("mrent_Car"));
        					emp.setHotel_Name(b1.getString("mhotel_Name"));
        					emp.setHotel_City(b1.getString("mhotel_City"));
        					emp.setPickup_Details(b1.getString("mpickup_Details"));
        					emp.setDrop_Details(b1.getString("mdrop_Details"));
        					
        					city.add(emp);
        				
        			}
        		} catch (SQLException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
                
        		
        		if(city.size()>0)
                	request.setAttribute("city", city);
        		
        		if(emplist.size()>0)
                	request.setAttribute("emplist", emplist);
               
        		String monthplan="select tm.plan_date,tm.morigin,tm.mdestination,tm.mtravelmode,tm.mtraveltype,tm.mremarks from Travel_MTP as tm,Travel_Desk as td where td.reqNo='"+ reqno+"' and td.id=tm.req_id order by plan_date";
				List appList1 = new LinkedList();
		ResultSet rsAppr1 = ad.selectQuery(monthplan);
		try {
			while (rsAppr1.next()) {
				TravelRequestForm appr=new TravelRequestForm();
				
				String d[]=rsAppr1.getString("plan_date").split("-");
				String dateString = d[2]+"/"+d[1]+"/"+d[0];;
				
				appr.setFromdate(dateString);
				
				
				//appr.setFromdate(rsAppr1.getString("plan_date"));
				appr.setOrigin(rsAppr1.getString("morigin"));
				appr.setDeparture(rsAppr1.getString("mdestination"));
				appr.setTravelmode(rsAppr1.getString("mtravelmode"));
				appr.setTraveltype(rsAppr1.getString("mtraveltype"));
				appr.setRemarks(rsAppr1.getString("mremarks"));
				appList1.add(appr);
			}
			if(appList1.size()>0)
			request.setAttribute("appList1", appList1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        		
        		
        		travel.add(t);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		request.setAttribute("travel", travel);
		String app = "";

	/*	if (filterBy.equalsIgnoreCase("Pending")) {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Travel' and a.Req_Id='"
					+ reqno
					+ "' and e.PERNR=a.Pending_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID "
					+ "union "
					 +"select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Travel' and a.Req_Id='"
						+ reqno
						+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";

		} else {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where  Req_Type='Travel' and a.Req_Id='"
					+ reqno
					+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";
				

		}
		List appList = new LinkedList();
		ResultSet rsAppr = ad.selectQuery(app);
		String duplicate="";
		try {
			while (rsAppr.next()) {
				ApprovalsForm appr = new ApprovalsForm();
				appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
				appr.setDesignation(rsAppr.getString("DSGSTXT"));
				appr.setApproveStatus(rsAppr.getString("Req_Status"));
				String approvaStatus = rsAppr.getString("Req_Status");
				if (approvaStatus.equals("Pending")) {
					appr.setApproveDate("");
					appr.setComments("");
				}
				if (approvaStatus.equals("Approved")
						|| approvaStatus.equals("Completed")) {
					appr.setApproveDate(rsAppr.getString("approved_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				if (approvaStatus.equals("Rejected")
						|| approvaStatus.equals("Cancelled")) {
					appr.setApproveDate(rsAppr.getString("rejected_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				
				if(duplicate==null)
				{
					duplicate="";
				}
				if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))
					appList.add(appr);
					duplicate=rsAppr.getString("approved_date");
			}
			request.setAttribute("appList", appList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream in = ConnectionFactory.class.getClassLoader()
				.getResourceAsStream("db.properties");
		Properties props = new Properties();
		try {
			props.load(in);
		
		in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String getdet="select CONVERT(varchar(10),submitted_date,103)+' '+ CONVERT(varchar(5),submitted_date,108) as submitted_date,CONVERT(varchar(10),approved_date,103)+' '+ CONVERT(varchar(5),approved_date,108) as approved_date,CONVERT(varchar(10),Reverted_on,103)+' '+ CONVERT(varchar(5),Reverted_on,108) as Reverted_on,CONVERT(varchar(10),rejected_date,103)+' '+ CONVERT(varchar(5),rejected_date,108) as rejected_date,* from travel_request_history where req_id='"+reqno+"'";
		/*	String getdet="select * from travel_request_history where req_id='"+reqId+"'";*/
			LinkedList appList = new LinkedList();
			ResultSet rsAppr = ad.selectQuery(getdet);
			String duplicate="";
			try {
				while (rsAppr.next()) {
					ApprovalsForm appr = new ApprovalsForm();
				/*	appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
					appr.setDesignation(rsAppr.getString("DSGSTXT"));*/
					appr.setRole(rsAppr.getString("Role"));
					
					String approvaStatus = rsAppr.getString("Req_Status");
					if (approvaStatus.equals("Pending")) {
						
						int emp =Integer.parseInt(rsAppr.getString("Requester_Name"));
						appr.setApproveDate("");
						appr.setComments("");
						appr.setApproveStatus("Submitted");
						
						String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
						ResultSet rr  =ad.selectQuery(empx);
						while(rr.next())
						{
							appr.setApprover(rr.getString("EMP_FULLNAME"));
							appr.setDesignation(rr.getString("DSGSTXT"));
							appr.setApproveDate(rsAppr.getString("submitted_date"));
						}
						
						
					}
					
					if (approvaStatus.equalsIgnoreCase("Travel Review")) {
						
						int emp =Integer.parseInt(rsAppr.getString("Requester_Name"));
						appr.setApproveDate("");
						appr.setComments("");
						appr.setApproveStatus(rsAppr.getString("Req_Status"));
						
						String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
						ResultSet rr  =ad.selectQuery(empx);
						while(rr.next())
						{
							appr.setApprover(rr.getString("EMP_FULLNAME"));
							appr.setDesignation(rr.getString("DSGSTXT"));
							appr.setApproveDate(rsAppr.getString("submitted_date"));
							appr.setComments(rsAppr.getString("Comments"));
						}
						
						
					}
					
					if (approvaStatus.equals("Approved")||approvaStatus.equalsIgnoreCase("Booking Accepted")||approvaStatus.equalsIgnoreCase("Bills Updated")||approvaStatus.equalsIgnoreCase("Travel Review")
							||approvaStatus.equalsIgnoreCase("Submitted To Finance")|| approvaStatus.equalsIgnoreCase("Received By finance")|| approvaStatus.equalsIgnoreCase("Verified By finance")||approvaStatus.equals("Completed")) {
						appr.setApproveStatus(rsAppr.getString("Req_Status"));
						
						int emp =Integer.parseInt(rsAppr.getString("Actual_Approver"));
						appr.setApproveDate(rsAppr.getString("approved_date"));
						appr.setComments(rsAppr.getString("Comments"));
						String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
						ResultSet rr  =ad.selectQuery(empx);
						while(rr.next())
						{
							appr.setApprover(rr.getString("EMP_FULLNAME"));
							appr.setDesignation(rr.getString("DSGSTXT"));
							
						}
					}
					
					if (approvaStatus.equals("Reverted")
							|| approvaStatus.equals("Completed")) {
						appr.setApproveStatus(rsAppr.getString("Req_Status"));
						int emp =Integer.parseInt(rsAppr.getString("Actual_Approver"));
						appr.setApproveDate(rsAppr.getString("Reverted_on"));
						appr.setComments(rsAppr.getString("Comments"));
						
						String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"' and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID";
						ResultSet rr  =ad.selectQuery(empx);
						while(rr.next())
						{
							appr.setApprover(rr.getString("EMP_FULLNAME"));
							appr.setDesignation(rr.getString("DSGSTXT"));
							
						}
					}
					if (approvaStatus.equals("Rejected")
							|| approvaStatus.equals("Cancelled")) {
						appr.setApproveStatus(rsAppr.getString("Req_Status"));
						int emp =Integer.parseInt(rsAppr.getString("Actual_Approver"));
						appr.setApproveDate(rsAppr.getString("rejected_date"));
						appr.setComments(rsAppr.getString("Comments"));
						String empx="select e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info e,DEPARTMENT dep,DESIGNATION desg where e.PERNR='"+emp+"'";
						ResultSet rr  =ad.selectQuery(empx);
						while(rr.next())
						{
							appr.setApprover(rr.getString("EMP_FULLNAME"));
							appr.setDesignation(rr.getString("DSGSTXT"));
							
						}
					}
					/*if(duplicate!=null)
					if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))*/
						appList.add(appr);
						duplicate=rsAppr.getString("approved_date");
				}
				request.setAttribute("appList", appList);
			} catch (SQLException e) {

				e.printStackTrace();
			}
		
		
		return mapping.findForward("viewconfirmrequest");
	}
	
	public ActionForward PDFTravelgenerate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException {
		//Created by Arun .Date:15-11-2016
		TravelRequestForm requestForm = (TravelRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String exportdate=ft.format(dNow);
		String reqno=request.getParameter("requstNo");
		
		if(reqno==null)
		{
			reqno= Integer.toString(requestForm.getRequestNumber());
		}
		
		String forward = null;
		ArrayList workFlow1  = new  ArrayList();
		
		
		 Document document = new Document();
			InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		Properties props = null;
		try {
			props = PropertiesLoaderUtils.loadAllProperties("db.properties");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		PropertyPlaceholderConfigurer props2 = new PropertyPlaceholderConfigurer();
		props2.setProperties(props);
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
		
	 	 
	 	  BaseFont base = null;
			try {
			
					try {
						base = BaseFont.createFont("\\Windows\\fonts\\wingding_0.ttf", BaseFont.IDENTITY_H, false);
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	 
			Font font = new Font(base, 16f, Font.BOLD);
		  char checked='\u00FE';
		  char unchecked='\u00A8';
	 	
	 	String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	String filePath=uploadFilePath+"/EMicro Files/ESS/Travel Request/PDF/";
	 	
		File destinationDir = new File(filePath);
		if(!destinationDir.exists())
		{
			destinationDir.mkdirs();
			
			
			
		}
		String empfullnm="";
		PdfWriter writer;
		
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(filePath.replace("/", "\\")+"TR_"+reqno+".pdf"));
	
		
        Rectangle rect = new Rectangle(30, 30, 550, 800);
        writer.setBoxSize("art", rect);
        
		
        Font largeBold = new Font(Font.FontFamily.COURIER, 10,
                Font.BOLD);
       
       Font columnheader = new Font(Font.FontFamily.UNDEFINED, 8,
                Font.BOLD);
       
       Font smallfont = new Font(Font.FontFamily.COURIER,8);
       smallfont.setColor(BaseColor.BLUE);
       Font mediumfont = new Font(Font.FontFamily.UNDEFINED,8,Font.BOLD);
       String TravelFor="";
       String Travel_Request_for="";
       TravelRequestForm t =new TravelRequestForm();
	  	ArrayList data=new ArrayList();
		ArrayList emplist=new ArrayList();
		ArrayList guestlist=new ArrayList();
		String reqType="";
		String lis=" select division.div_desc as spon_div,  emp.email_id,per.email_address ,emp.dob, per.mobile_no, per.passport_no  "
				+ " ,CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1,CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as bookeddate1,"
				+ " CONVERT(varchar(10),Depart_On,103)  as departon,"
				+ " CONVERT(varchar(10),Return_On,103)  as returnon,CONVERT(varchar(10),Return_On,103)  as returnon,CONVERT(varchar(10),billdate,103)  as billdate1,* from Travel_Desk left outer join  division on division.div_code=spon_div  , "
				+ " emp_official_info as emp, emp_personal_info as per "
				+ "  where reqNo='"+reqno+"' and travel_Desk.PERNR=emp.PERNR  and per.user_id=emp.pernr ";
		ResultSet ff=ad.selectQuery(lis);
		try {
			if(ff.next())
			{
				
				///////
			String ere[]=empDet(Integer.toString(ff.getInt("PERNR")));	
			t.setRequestNumber(Integer.parseInt(reqno));
			t.setId(ff.getInt("id"));	
			t.setEmployeeno(Integer.toString(ff.getInt("PERNR")));
			t.setLocationId(ere[1]);
			t.setDepartment(ere[2]);
			t.setEmployeeName(ere[0]);
			t.setDesignation(ere[3]);
			t.setGuest_DOB(ff.getString("dob"));
			t.setP_email(ff.getString("p_email"));
			t.setTravel_desk_type(Empname(ff.getString("travel_desk_type")));
			t.setTraveltype(ff.getString("Type_of_travel"));
			t.setTravelmode(ff.getString("travelmode"));
			t.setPassportno(ff.getString("passport_no"));
			t.setPassportplace(ff.getString("place_of_issue_of_passport"));
			t.setPassportexpirydate(ff.getString("date_of_expiry_of_passport"));
			t.setPassportissuedate(ff.getString("date_of_issue_of_passp"));
			t.setP_cont(ff.getString("p_cont"));
			t.setP_name(ff.getString("p_name"));
			t.setP_dept(ff.getString("p_dept"));
			t.setUserPersonalmailId(ff.getString("email_address"));
			t.setGender(ff.getString("sex"));
			t.setTraveltype(ff.getString("Mode_of_travel"));
			t.setService_class(ff.getString("service_class"));
			t.setTravelFor(ff.getString("travel_For"));
              			t.setPurposetype(ff.getString("purposetype"));
			t.setPurposetext(ff.getString("purposetext"));
			t.setTravel_Adult(ff.getString("travel_Adult"));
			t.setTravel_Child(ff.getString("travel_Child"));
			t.setTravel_Infant(ff.getString("travel_Infant"));
			t.setOrigin(ff.getString("from_place"));
			t.setDepartOn(ff.getString("departOn"));
			t.setDeparture(ff.getString("to_place"));
			t.setReturnfrom(ff.getString("to_place"));
			t.setReturnOn(ff.getString("returnOn"));
			t.setTravel_Days(ff.getString("travel_Days"));
			t.setBud_code(ff.getString("bud_code"));
			t.setEst_trip_cose(ff.getString("est_trip_cose"));
			t.setTrip_Amt(ff.getString("trip_Amt"));
			t.setTravelRequestFor(ff.getString("travel_request_for"));
			t.setReqDate(ff.getString("Created_date1"));
			t.setSpon_div(ff.getString("spon_div"));
			t.setTravelFor(ff.getString("Travel_For"));
			
			Travel_Request_for=ff.getString("Travel_Request_for");
			TravelFor = ff.getString("Travel_For");
			  if(((TravelFor.equalsIgnoreCase("Self") ||TravelFor.equalsIgnoreCase("Multiple"))&&(Travel_Request_for.equalsIgnoreCase("one way")||Travel_Request_for.equalsIgnoreCase("Round Trip"))))
            	
            {
            	  t.setAccom_type(ff.getString("accom_type"));
                  t.setAccom_name(ff.getString("accom_name"));
                  t.setHotel_Res(ff.getString("hotel_Res"));
                  t.setHotel_City(ff.getString("hotel_City"));
                  t.setHotel_Name(ff.getString("hotel_Name"));
                 
                  t.setRent_Car(ff.getString("rent_Car"));
                  t.setPickup_Details(ff.getString("pickup_Details"));
                  t.setDrop_Details(ff.getString("drop_Details"));
                 
                  
                  
            }
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String locname="select loc.LAND1 from location as loc where loc.LOCATION_CODE='"+t.getLocationId()+"'";
		ResultSet rs1 = ad.selectQuery(locname);
		String locationName="";
		while(rs1.next())
		{
			locationName=rs1.getString("LAND1");
			
		}
       
       HeaderFooterPageEventTravelDeskReport event = new HeaderFooterPageEventTravelDeskReport(user.getEmployeeNo(), user.getFullName(),t.getLocationId(),t.getReqDate(),t.getRequestNumber(),locationName,t.getTraveltype());
        writer.setPageEvent(event);
    
       
        document.open();         
        
    	   PdfPTable table1 = new PdfPTable(4); // 3 columns.
           table1.setWidthPercentage(100); //Width 100%
           table1.setSpacingBefore(10f); //Space before table
           table1.setSpacingAfter(10f); //Space after table
           //Set Column widths
           float[] columnWidths = {6f,1f,3f,3f};;
		  table1.setWidths(columnWidths);
		  
		  if(t.getGender().equals("M"))
		  {
			  t.setGender("Male");
		  }
		  if(t.getGender().equals("F"))
		  {
			  t.setGender("Female");
		  }
			document.add(table1);
			
			
			request.setAttribute("data", data);
		  
		  // Requester Details start
		 PdfPTable tablereq = new PdfPTable(6); // No. of columns
		  tablereq.setWidthPercentage(100); //Width 100%
          tablereq.setSpacingBefore(10f); //Space before table
          tablereq.setSpacingAfter(10f);  // Space after table
          float[] columnWidthreqs = {2.5f,2.5f,2.5f,3f,2.5f,3f};; //Set Column widths
          tablereq.setWidths(columnWidthreqs);
          
          PdfPCell pere = new PdfPCell(new Paragraph("Requester Details",columnheader));			 
          pere.setBackgroundColor(new BaseColor(219, 214, 214));
          pere.setColspan(6);		  
		  tablereq.addCell(pere);

		  PdfPCell perpernr1 = new PdfPCell(new Paragraph("Emp.No:",columnheader));			  
		  tablereq.addCell(perpernr1);
		  PdfPCell perpernrv = new PdfPCell(new Paragraph(t.getEmployeeno(),smallfont));			  
		  tablereq.addCell(perpernrv);
		  /*PdfPCell perpernr = new PdfPCell(new Paragraph("Department:",columnheader));			  
		  tablereq.addCell(perpernr);		  
		  perpernrv = new PdfPCell(new Paragraph(t.getDepartment(),smallfont));			  
		  tablereq.addCell(perpernrv);*/
		  PdfPCell perpernr = new PdfPCell(new Paragraph("Phone:",columnheader));			  
		  tablereq.addCell(perpernr);		  
		  perpernrv = new PdfPCell(new Paragraph(t.getP_cont(),smallfont));			  
		  tablereq.addCell(perpernrv);
		  
		  /*PdfPCell perdep = new PdfPCell(new Paragraph("Gender:",columnheader));			  
		  tablereq.addCell(perdep);
		  perpernrv = new PdfPCell(new Paragraph(t.getGender(),smallfont));			  
		  tablereq.addCell(perpernrv);*/
		  PdfPCell perdep = new PdfPCell(new Paragraph("DOB:",columnheader));			  
		  tablereq.addCell(perdep);
		  perpernrv = new PdfPCell(new Paragraph(t.getGuest_DOB(),smallfont));			  
		  tablereq.addCell(perpernrv);
		  
		  
		   perpernr1 = new PdfPCell(new Paragraph("Name:",columnheader));			  
		  tablereq.addCell(perpernr1);
		   perpernrv = new PdfPCell(new Paragraph(t.getEmployeeName(),smallfont));			  
		  tablereq.addCell(perpernrv);
		 /*  perpernr = new PdfPCell(new Paragraph("Designation:",columnheader));			  
		  tablereq.addCell(perpernr);		  
		  perpernrv = new PdfPCell(new Paragraph(t.getDesignation(),smallfont));			  
		  tablereq.addCell(perpernrv);*/
		  perpernr = new PdfPCell(new Paragraph("Email(Personal)",columnheader));			  
		  tablereq.addCell(perpernr);		  
		  perpernrv = new PdfPCell(new Paragraph(t.getUserPersonalmailId(),smallfont));			  
		  tablereq.addCell(perpernrv);
		   perdep = new PdfPCell(new Paragraph("Mode:",columnheader));			  
		  tablereq.addCell(perdep);
		  perpernrv = new PdfPCell(new Paragraph(t.getTravelmode(),smallfont));			  
		  tablereq.addCell(perpernrv);
		  		  
		  
/*
		   perpernr1 = new PdfPCell(new Paragraph("Phone:",columnheader));			  
		  tablereq.addCell(perpernr1);
		   perpernrv = new PdfPCell(new Paragraph(t.getP_cont(),smallfont));			  
		  tablereq.addCell(perpernrv);*/

		   perpernr1 = new PdfPCell(new Paragraph("Department:",columnheader));			  
		  tablereq.addCell(perpernr1);
		   perpernrv = new PdfPCell(new Paragraph(t.getDepartment(),smallfont));			  
		  tablereq.addCell(perpernrv);
		   perpernr = new PdfPCell(new Paragraph("Email(Official):",columnheader));			  
		  tablereq.addCell(perpernr);		  
		  perpernrv = new PdfPCell(new Paragraph(t.getP_email(),smallfont));			  
		  tablereq.addCell(perpernrv);
		 perdep = new PdfPCell(new Paragraph("Type:",columnheader));			  
		  tablereq.addCell(perdep);
		  perpernrv = new PdfPCell(new Paragraph(t.getTraveltype(),smallfont));			  
		  tablereq.addCell(perpernrv);
		  // Requester Details start
		  

		  /* perpernr1 = new PdfPCell(new Paragraph("Travel Desk:",columnheader));			  
		  tablereq.addCell(perpernr1);
		   perpernrv = new PdfPCell(new Paragraph(t.getTravel_desk_type(),smallfont));			  
		  tablereq.addCell(perpernrv);*/
		  perpernr1 = new PdfPCell(new Paragraph("Designation:",columnheader));			  
		  tablereq.addCell(perpernr1);
		   perpernrv = new PdfPCell(new Paragraph(t.getDesignation(),smallfont));			  
		  tablereq.addCell(perpernrv);
//		   perpernr = new PdfPCell(new Paragraph("Mode:",columnheader));			  
//		  tablereq.addCell(perpernr);		  
//		  perpernrv = new PdfPCell(new Paragraph(t.getTravelmode(),smallfont));			  
//		  tablereq.addCell(perpernrv);
		  perpernr = new PdfPCell(new Paragraph("Gender",columnheader));			  
		  tablereq.addCell(perpernr);		  
		  perpernrv = new PdfPCell(new Paragraph(t.getGender(),smallfont));			  
		  tablereq.addCell(perpernrv);
		
		  
		  perdep = new PdfPCell(new Paragraph("Travel Desk:",columnheader));			  
		  tablereq.addCell(perdep);
		  perpernrv = new PdfPCell(new Paragraph(t.getTravel_desk_type(),smallfont));			  
		  tablereq.addCell(perpernrv);
		  
		  
		  
		  document.add(tablereq);
        		
		  
		 /* if(!t.getTravelFor().equalsIgnoreCase("Self"))*/
		  if(true)
		  {
		  
		  // Travel Details start
		  PdfPTable tablereq2 = new PdfPTable(6); // No. of columns
		  tablereq2.setWidthPercentage(100); //Width 100%
          tablereq2.setSpacingBefore(10f); //Space before table
          tablereq2.setSpacingAfter(10f);  // Space after table
          float[] columnWidthreqs2 = {2.5f,2.5f,2.5f,2.5f,2.5f,2.5f}; //Set Column widths
          tablereq2.setWidths(columnWidthreqs2);
		  
     /*     PdfPCell pere1 = new PdfPCell(new Paragraph("Requester Details ",columnheader));
          pere1.setBackgroundColor(new BaseColor(219, 214, 214));
          pere1.setColspan(6);		  
		  tablereq2.addCell(pere1);
		   
		  PdfPCell perpernr3 = new PdfPCell(new Paragraph("Contact Name:",columnheader));			  
		  tablereq2.addCell(perpernr3);
		  PdfPCell perpernr4 = new PdfPCell(new Paragraph(t.getP_name(),smallfont));
		  tablereq2.addCell(perpernr4);
		  PdfPCell perpernr5 = new PdfPCell(new Paragraph("Phone:",columnheader));			  
		  tablereq2.addCell(perpernr5);
		  PdfPCell perpernr6 = new PdfPCell(new Paragraph(t.getP_cont(),smallfont));			  
		  tablereq2.addCell(perpernr6);
		  
		  PdfPCell perpernr7 = new PdfPCell(new Paragraph("official Id:",columnheader));			  
		  tablereq2.addCell(perpernr7);
		  PdfPCell perpernr8 = new PdfPCell(new Paragraph(t.getP_email(),smallfont));			  
		  tablereq2.addCell(perpernr8);
		  
		  
		  document.add(tablereq2);*/
		  
		  }
		   
	
		  if(t.getTraveltype().equalsIgnoreCase("International"))
		  {
			  
		  
		  // Travel Details start
		  PdfPTable tablereq2 = new PdfPTable(4); // No. of columns
		  tablereq2.setWidthPercentage(100); //Width 100%
          tablereq2.setSpacingBefore(10f); //Space before table
          tablereq2.setSpacingAfter(10f);  // Space after table
          float[] columnWidthreqs2 = {2.5f,2.5f,2.5f,3f}; //Set Column widths
          tablereq2.setWidths(columnWidthreqs2);
		  
          PdfPCell pere1 = new PdfPCell(new Paragraph("Passport Details ",columnheader));			  
          pere1.setColspan(4);		  
          pere1.setBackgroundColor(new BaseColor(219, 214, 214));
		  tablereq2.addCell(pere1);
		   
		  PdfPCell perpernr3 = new PdfPCell(new Paragraph("Passport No:",columnheader));			  
		  tablereq2.addCell(perpernr3);
		  PdfPCell perpernr4 = new PdfPCell(new Paragraph(t.getPassportno(),smallfont));
		  tablereq2.addCell(perpernr4);
		  PdfPCell perpernr5 = new PdfPCell(new Paragraph("Place of Issue:",columnheader));			  
		  tablereq2.addCell(perpernr5);
		  PdfPCell perpernr6 = new PdfPCell(new Paragraph(t.getPassportplace(),smallfont));			  
		  tablereq2.addCell(perpernr6);
		  
		  PdfPCell perpernr7 = new PdfPCell(new Paragraph("Issue Date:",columnheader));			  
		  tablereq2.addCell(perpernr7);
		  PdfPCell perpernr8 = new PdfPCell(new Paragraph(t.getPassportissuedate(),smallfont));			  
		  tablereq2.addCell(perpernr8);
		  PdfPCell perpernr9 = new PdfPCell(new Paragraph("Expiry Date:",columnheader));			  
		  tablereq2.addCell(perpernr9);
		  PdfPCell perpernr10 = new PdfPCell(new Paragraph(t.getPassportexpirydate(),smallfont));			  
		  tablereq2.addCell(perpernr10);
		  
		  
		  
		  document.add(tablereq2);
		  
		  }
		  
		  
	  
		  
		  // Travel Details start
		  PdfPTable tablereq3 = new PdfPTable(4); // No. of columns
		  tablereq3.setWidthPercentage(100); //Width 100%
          tablereq3.setSpacingBefore(10f); //Space before table
          tablereq3.setSpacingAfter(10f);  // Space after table
          float[] columnWidthreqs3 = {2.5f,2.5f,2.5f,3f}; //Set Column widths
          tablereq3.setWidths(columnWidthreqs3);
		  
           PdfPCell pere1 = new PdfPCell(new Paragraph("Trip Details ",columnheader));			  
          pere1.setColspan(4);
          pere1.setBackgroundColor(new BaseColor(219, 214, 214));
		  tablereq3.addCell(pere1);
		   
		  PdfPCell perpernr3 = new PdfPCell(new Paragraph("Class:",columnheader));			  
		  tablereq3.addCell(perpernr3);
		  PdfPCell perpernr4 = new PdfPCell(new Paragraph(t.getService_class(),smallfont));
		  tablereq3.addCell(perpernr4);
		  PdfPCell perpernr5 = new PdfPCell(new Paragraph("Request For:",columnheader));			  
		  tablereq3.addCell(perpernr5);
		  PdfPCell perpernr6 = new PdfPCell(new Paragraph(t.getTravelRequestFor(),smallfont));			  
		  tablereq3.addCell(perpernr6);
		  
		  PdfPCell perpernr7 = new PdfPCell(new Paragraph("Purpose:",columnheader));			  
		  tablereq3.addCell(perpernr7);
		  PdfPCell perpernr8 = new PdfPCell(new Paragraph(t.getPurposetype(),smallfont));			  
		  tablereq3.addCell(perpernr8);
		  PdfPCell perpernr9 = new PdfPCell(new Paragraph("Location:",columnheader));			  
		  tablereq3.addCell(perpernr9);
		  PdfPCell perpernr10 = new PdfPCell(new Paragraph(t.getLocationId(),smallfont));			  
		  tablereq3.addCell(perpernr10);
		  
		  
		  

		  perpernr7 = new PdfPCell(new Paragraph("Purpose of Travel:",columnheader));	
		  tablereq3.addCell(perpernr7);
		  perpernr8 = new PdfPCell(new Paragraph(t.getPurposetext(),smallfont));
		  perpernr8.setColspan(3);
		  tablereq3.addCell(perpernr8);
		  
		  
		  

		  perpernr7 = new PdfPCell(new Paragraph("Travel For:",columnheader));			  
		  tablereq3.addCell(perpernr7);
		  perpernr8 = new PdfPCell(new Paragraph(t.getTravelFor(),smallfont));			  
		  tablereq3.addCell(perpernr8);
		  perpernr9 = new PdfPCell(new Paragraph("No. of Travellers:",columnheader));			  
		  tablereq3.addCell(perpernr9);
		  if(t.getTravel_Adult().equalsIgnoreCase("0"))
			  t.setTravel_Adult("NA");
		  if(t.getTravel_Child().equalsIgnoreCase("0"))
			  t.setTravel_Child("NA");
		  if(t.getTravel_Infant().equalsIgnoreCase("0"))
			  t.setTravel_Infant("NA");
		  
		  perpernr10 = new PdfPCell(new Paragraph("Adult- "+ t.getTravel_Adult() +" Children-"+ t.getTravel_Child()+" Infant- "+ t.getTravel_Infant() ,smallfont));			  
		  tablereq3.addCell(perpernr10);
		  
		  
		  perpernr7 = new PdfPCell(new Paragraph("Origin:",columnheader));			  
		  tablereq3.addCell(perpernr7);
		  perpernr8 = new PdfPCell(new Paragraph(t.getOrigin(),smallfont));			  
		  tablereq3.addCell(perpernr8);
		  perpernr9 = new PdfPCell(new Paragraph("Destination:",columnheader));			  
		  tablereq3.addCell(perpernr9);
		  perpernr10 = new PdfPCell(new Paragraph(t.getReturnfrom(),smallfont));			  
		  tablereq3.addCell(perpernr10);
		  
		  
		  

		  perpernr7 = new PdfPCell(new Paragraph("Departure Date:",columnheader));			  
		  tablereq3.addCell(perpernr7);
		  perpernr8 = new PdfPCell(new Paragraph(t.getDepartOn(),smallfont));			  
		  tablereq3.addCell(perpernr8);
		  perpernr9 = new PdfPCell(new Paragraph("Return Date:",columnheader));			  
		  tablereq3.addCell(perpernr9);
		  perpernr10 = new PdfPCell(new Paragraph(t.getReturnOn(),smallfont));			  
		  tablereq3.addCell(perpernr10);
		  
		  
		  

		  perpernr7 = new PdfPCell(new Paragraph("No Of Days:",columnheader));			  
		  tablereq3.addCell(perpernr7);
		  perpernr8 = new PdfPCell(new Paragraph(t.getTravel_Days(),smallfont));			  
		  tablereq3.addCell(perpernr8);
		  perpernr9 = new PdfPCell(new Paragraph("Expense Billed to: ",columnheader));			  
		  tablereq3.addCell(perpernr9);
		  perpernr10 = new PdfPCell(new Paragraph(t.getSpon_div(),smallfont));			  
		  tablereq3.addCell(perpernr10);
		  
		  

		  perpernr7 = new PdfPCell(new Paragraph("Budget Code:",columnheader));			  
		  tablereq3.addCell(perpernr7);
		  perpernr8 = new PdfPCell(new Paragraph(t.getBud_code(),smallfont));			  
		  tablereq3.addCell(perpernr8);
		  perpernr9 = new PdfPCell(new Paragraph("Estimation Trip Cost:",columnheader));			  
		  tablereq3.addCell(perpernr9);
		  perpernr10 = new PdfPCell(new Paragraph(t.getEst_trip_cose(),smallfont));			  
		  tablereq3.addCell(perpernr10);
		  
		  
		  perpernr9 = new PdfPCell(new Paragraph("Trip Advance Amount:",columnheader));			  
		  tablereq3.addCell(perpernr9);
		  perpernr10 = new PdfPCell(new Paragraph(t.getTrip_Amt(),smallfont));
		  perpernr10.setColspan(3);
		  tablereq3.addCell(perpernr10);
		  
		  
		  PdfPTable tablereq5 = new PdfPTable(4); // No. of columns
		  tablereq5.setWidthPercentage(100); //Width 100%
          tablereq5.setSpacingBefore(10f); //Space before table
          tablereq5.setSpacingAfter(10f);  // Space after table
          float[] columnWidthreqs5 = {2.5f,2.5f,2.5f,3f}; //Set Column widths
          tablereq5.setWidths(columnWidthreqs5);
		  
          if(((TravelFor.equalsIgnoreCase("Self") ||TravelFor.equalsIgnoreCase("Multiple"))&&(Travel_Request_for.equalsIgnoreCase("one way")||Travel_Request_for.equalsIgnoreCase("Round Trip")) &&(!(t.getHotel_Res().equalsIgnoreCase("no")&&(t.getRent_Car().equalsIgnoreCase("no"))))))
		  {
			  
             /* tra.add(t);
              request.setAttribute("tra", tra);*/
			  
			
	          
			   PdfPCell pere11 = new PdfPCell(new Paragraph("Other Details ",columnheader));
		          pere11.setBackgroundColor(new BaseColor(219, 214, 214));
		          pere11.setColspan(6);		  
				  tablereq5.addCell(pere11); 
				  
				  PdfPCell perpernr11 = new PdfPCell(new Paragraph("Accomation type:",columnheader));			  
    		  tablereq5.addCell(perpernr11);
    		  PdfPCell perpernrv12 = new PdfPCell(new Paragraph(t.getAccom_type(),smallfont));			  
    		  tablereq5.addCell(perpernrv12);
    		  PdfPCell  perpernr13 = new PdfPCell(new Paragraph("Accomation Name:",columnheader));			  
    		  tablereq5.addCell(perpernr13);		  
    		  PdfPCell perpernrv14 = new PdfPCell(new Paragraph(t.getAccom_name(),smallfont));			  
    		  tablereq5.addCell(perpernrv14);
    		  PdfPCell   perdep15 = new PdfPCell(new Paragraph("Rent Car:",columnheader));			  
    		  tablereq5.addCell(perdep15);
    		  PdfPCell perpernrv16 = new PdfPCell(new Paragraph(t.getRent_Car(),smallfont));			  
    		  tablereq5.addCell(perpernrv16);
    		  
    		  PdfPCell  perpernr17 = new PdfPCell(new Paragraph("Hotel Residence:",columnheader));			  
    		  tablereq5.addCell(perpernr17);
    		  PdfPCell  perpernrv18 = new PdfPCell(new Paragraph(t.getHotel_Res(),smallfont));			  
    		  tablereq5.addCell(perpernrv18);
    		  PdfPCell  perpernr19 = new PdfPCell(new Paragraph("Hotel City:",columnheader));			  
    		  tablereq5.addCell(perpernr19);		  
    		  PdfPCell perpernrv20 = new PdfPCell(new Paragraph(t.getHotel_City(),smallfont));			  
    		  tablereq5.addCell(perpernrv20);
    		  PdfPCell  perdep21 = new PdfPCell(new Paragraph("Hotel Name:",columnheader));			  
    		  tablereq5.addCell(perdep21);
    		  PdfPCell perpernrv22 = new PdfPCell(new Paragraph(t.getHotel_Name(),smallfont));			  
    		  tablereq5.addCell(perpernrv22);
    		  
    		  PdfPCell perpernr23 = new PdfPCell(new Paragraph("Pickup Details:",columnheader));			  
    		  tablereq5.addCell(perpernr23);
    		  PdfPCell perpernrv24 = new PdfPCell(new Paragraph(t.getPickup_Details(),smallfont));			  
    		  tablereq5.addCell(perpernrv24);
    		  PdfPCell perpernr25 = new PdfPCell(new Paragraph("Drop Details:",columnheader));			  
    		  tablereq5.addCell(perpernr25);		  
    		  PdfPCell perpernrv26 = new PdfPCell(new Paragraph(t.getDrop_Details(),smallfont));			  
    		  tablereq5.addCell(perpernrv26);
    		 
		  }
		  
		  
		  document.add(tablereq3);
		  document.add(tablereq5);
		  
		  
		  
		//traveller details
		  PdfPTable tabletrav = new PdfPTable(10); // 3 columns.
		  tabletrav.setWidthPercentage(100); //Width 100%
		  
		  tabletrav.setSpacingBefore(10f); //Space before table
		  tabletrav.setSpacingAfter(10f);  
		  
         
          //Set Column widths
          float[] columnWidttrav = {2f,2.5f,2f,2f,2f,2f,2f,2f,2f,2f};
          tabletrav.setWidths(columnWidttrav);
          
          PdfPTable tablereq6 = new PdfPTable(7); // No. of columns
		  tablereq6.setWidthPercentage(100); //Width 100%
          tablereq6.setSpacingBefore(10f); //Space before table
          tablereq6.setSpacingAfter(10f);  // Space after table
         // float[] columnWidthreqs6 = {2.5f,2.5f,2.5f,3f};
          float[] columnWidthreqs6 = {3f,3f,3f,3f,3f,3f,3f};//Set Column widths
          tablereq6.setWidths(columnWidthreqs6);
          
          String query1="select * from Travel_Desk_Travellers where req_id='"+t.getId()+"' ";
          ResultSet rs = ad.selectQuery(query1);
          try {
			if(rs.next())
			  {
	
	                
			  PdfPCell pertrav = new PdfPCell(new Paragraph("Travellers Details",columnheader));	
			  pertrav.setBackgroundColor(new BaseColor(219, 214, 214));
			  pertrav.setColspan(10);		  
			  tabletrav.addCell(pertrav);
  
			  
			  PdfPCell pertravempno = new PdfPCell(new Paragraph("Type",columnheader));	
			  pertravempno.setBackgroundColor(new BaseColor(219, 214, 214));
			  tabletrav.addCell(pertravempno);
			  PdfPCell pertravempnname = new PdfPCell(new Paragraph("Name",columnheader));	
			  pertravempnname.setBackgroundColor(new BaseColor(219, 214, 214));
			  tabletrav.addCell(pertravempnname);
			  PdfPCell pertravempdep = new PdfPCell(new Paragraph("M/F",columnheader));		
			  pertravempdep.setBackgroundColor(new BaseColor(219, 214, 214));
			  tabletrav.addCell(pertravempdep);
			  PdfPCell pertravempdes = new PdfPCell(new Paragraph("Age",columnheader));		
			  pertravempdes.setBackgroundColor(new BaseColor(219, 214, 214));
			  tabletrav.addCell(pertravempdes);
			  PdfPCell pertravemploc = new PdfPCell(new Paragraph("Contact No.",columnheader));		
			  pertravemploc.setBackgroundColor(new BaseColor(219, 214, 214));
			  tabletrav.addCell(pertravemploc);
			  PdfPCell pertravempfgeb = new PdfPCell(new Paragraph("email",columnheader));		
			  pertravempfgeb.setBackgroundColor(new BaseColor(219, 214, 214));
			  tabletrav.addCell(pertravempfgeb);
			  PdfPCell pertravempads = new PdfPCell(new Paragraph("Origin",columnheader));		
			  pertravempads.setBackgroundColor(new BaseColor(219, 214, 214));
			  tabletrav.addCell(pertravempads);
			  PdfPCell pertravempcoo = new PdfPCell(new Paragraph("Destination",columnheader));		
			  pertravempcoo.setBackgroundColor(new BaseColor(219, 214, 214));
			  tabletrav.addCell(pertravempcoo);
			  PdfPCell pertravempemi = new PdfPCell(new Paragraph("Departure Date",columnheader));	
			  pertravempemi.setBackgroundColor(new BaseColor(219, 214, 214));
			  tabletrav.addCell(pertravempemi);
			  PdfPCell pertravempas = new PdfPCell(new Paragraph("Return Date",columnheader));	
			  pertravempas.setBackgroundColor(new BaseColor(219, 214, 214));
			  tabletrav.addCell(pertravempas);
			  
			  
			  if(rs.getString("hotel_Res")!=null)
			  {
				  if(rs.getString("hotel_Res").equalsIgnoreCase("Yes"))
						  {
			     PdfPCell pere11 = new PdfPCell(new Paragraph("Other Details ",columnheader));
		          pere11.setBackgroundColor(new BaseColor(219, 214, 214));
		          pere11.setColspan(7);		  
		          tablereq6.addCell(pere11); 
				  
		          
		          PdfPCell pertravempnname12 = new PdfPCell(new Paragraph("Guest Name",columnheader));	
				  pertravempnname12.setBackgroundColor(new BaseColor(219, 214, 214));
				  tablereq6.addCell(pertravempnname12);
				  
				  PdfPCell perpernr11 = new PdfPCell(new Paragraph("Accomodation Type",columnheader));
				  perpernr11.setBackgroundColor(new BaseColor(219, 214, 214));
				  tablereq6.addCell(perpernr11);
   		
   		  PdfPCell  perpernr13 = new PdfPCell(new Paragraph("Accomodation Name",columnheader));	
   		perpernr13.setBackgroundColor(new BaseColor(219, 214, 214));
   		  tablereq6.addCell(perpernr13);		  
   		
   		/*  PdfPCell   perdep15 = new PdfPCell(new Paragraph("Rent_Car",columnheader));			  
   		  tablereq6.addCell(perdep15);
   		
   		  
   		  PdfPCell  perpernr17 = new PdfPCell(new Paragraph("Hotel_Res",columnheader));			  
   		  tablereq6.addCell(perpernr17);*/
   		
   		  PdfPCell  perpernr19 = new PdfPCell(new Paragraph("Hotel City",columnheader));
   		perpernr19.setBackgroundColor(new BaseColor(219, 214, 214));
   		  tablereq6.addCell(perpernr19);		  
   		
   		  PdfPCell  perdep21 = new PdfPCell(new Paragraph("Hotel Name",columnheader));
   		perdep21.setBackgroundColor(new BaseColor(219, 214, 214));
   		  tablereq6.addCell(perdep21);
   		  
   		  PdfPCell perpernr23 = new PdfPCell(new Paragraph("Pickup Details",columnheader));		
   		perpernr23.setBackgroundColor(new BaseColor(219, 214, 214));
   		  tablereq6.addCell(perpernr23);
   		 
   		  PdfPCell perpernr25 = new PdfPCell(new Paragraph("Drop Details",columnheader));	
   		perpernr25.setBackgroundColor(new BaseColor(219, 214, 214));
   		  tablereq6.addCell(perpernr25);	
			  
						  }}
		
			  rs.beforeFirst();
			  
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
          
          try {
			while(rs.next())
			  {
				  
			  PdfPCell pertravempnov = new PdfPCell(new Paragraph(rs.getString("req_type"),smallfont));			  
			  tabletrav.addCell(pertravempnov);
			  PdfPCell pertravempnnamev = new PdfPCell(new Paragraph(rs.getString("name_of_guest"),smallfont));			  
			  tabletrav.addCell(pertravempnnamev);
			  PdfPCell pertravempdepv = new PdfPCell(new Paragraph(rs.getString("Gender_guest"),smallfont));			  
			  tabletrav.addCell(pertravempdepv);
			  PdfPCell pertravempdesv = new PdfPCell(new Paragraph(rs.getString("Guest_DOB"),smallfont));			  
			  tabletrav.addCell(pertravempdesv);
			  PdfPCell pertravemplocv = new PdfPCell(new Paragraph(rs.getString("contact_guest"),smallfont));			  
			  tabletrav.addCell(pertravemplocv);
			  PdfPCell pertravempfgebv = new PdfPCell(new Paragraph(rs.getString("email_guest"),smallfont));			  
			  tabletrav.addCell(pertravempfgebv);
			  PdfPCell pertravempadsv = new PdfPCell(new Paragraph(rs.getString("fromplace"),smallfont));			  
			  tabletrav.addCell(pertravempadsv);
			  PdfPCell pertravempcoov = new PdfPCell(new Paragraph(rs.getString("toplace"),smallfont));			  
			  tabletrav.addCell(pertravempcoov);
			  PdfPCell pertravempemiv = new PdfPCell(new Paragraph(rs.getString("departOn"),smallfont));			  
			  tabletrav.addCell(pertravempemiv);
			  PdfPCell pertravempasv = new PdfPCell(new Paragraph(rs.getString("returnon"),smallfont));			  
			  tabletrav.addCell(pertravempasv);
			  
			  
			  
			  if(rs.getString("hotel_Res")!=null)
			  {
				  if(rs.getString("hotel_Res").equalsIgnoreCase("Yes"))
						  {
			  PdfPCell pertravempnnamev13 = new PdfPCell(new Paragraph(rs.getString("name_of_guest"),smallfont));			  
			  tablereq6.addCell(pertravempnnamev13);
			  
			  PdfPCell perpernrv12 = new PdfPCell(new Paragraph(rs.getString("accom_type"),smallfont));			  
			  tablereq6.addCell(perpernrv12);
			 	  
			  PdfPCell perpernrv14 = new PdfPCell(new Paragraph(rs.getString("accom_name"),smallfont));			  
			  tablereq6.addCell(perpernrv14);
			  
			/*  PdfPCell perpernrv16 = new PdfPCell(new Paragraph(rs.getString("rent_Car"),smallfont));			  
			  tablereq6.addCell(perpernrv16);
			  
			 
			  PdfPCell  perpernrv18 = new PdfPCell(new Paragraph(rs.getString("hotel_Res"),smallfont));			  
			  tablereq6.addCell(perpernrv18);*/
			
			  PdfPCell perpernrv20 = new PdfPCell(new Paragraph(rs.getString("hotel_City"),smallfont));			  
			  tablereq6.addCell(perpernrv20);
			 
			  PdfPCell perpernrv22 = new PdfPCell(new Paragraph(rs.getString("hotel_Name"),smallfont));			  
			  tablereq6.addCell(perpernrv22);
			  
			 
			  PdfPCell perpernrv24 = new PdfPCell(new Paragraph(rs.getString("pickup_Details"),smallfont));			  
			  tablereq6.addCell(perpernrv24);
			 	  
			  PdfPCell perpernrv26 = new PdfPCell(new Paragraph(rs.getString("drop_Details"),smallfont));			  
			  tablereq6.addCell(perpernrv26);
						  }}
			  
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
          document.add(tabletrav);
          document.add(tablereq6);
         PdfPTable tableguest = new PdfPTable(7); // 3 columns.
		  tableguest.setWidthPercentage(100); //Width 100%
       
        //Set Column widths
        float[] columnWidttgues = {2f,2f,2f,2f,2f,2f,2f};
        tableguest.setWidths(columnWidttgues);
       
        PdfPTable tablereq7 = new PdfPTable(7); // No. of columns
		  tablereq7.setWidthPercentage(100); //Width 100%
        tablereq7.setSpacingBefore(10f); //Space before table
        tablereq7.setSpacingAfter(10f);  // Space after table
       // float[] columnWidthreqs6 = {2.5f,2.5f,2.5f,3f};
        float[] columnWidthreqs7 = {3f,3f,3f,3f,3f,3f,3f};//Set Column widths
        tablereq6.setWidths(columnWidthreqs7);
          
          String query1a="select Travel_Desk_Travellers.Pernr ,Travel_Desk_Travellers.Name_of_guest,Travel_Multiple_City.* from Travel_Multiple_City"
          		+ " left outer join Travel_Desk_Travellers "
          		+ " on Travel_Multiple_City.trav_id=Travel_Desk_Travellers.id "
          		+ "  where Travel_Multiple_City.req_id='"+t.getId()+"' ";
          ResultSet rsa = ad.selectQuery(query1a);
          
			try {
				if(rsa.next())
				  {
					
        
      //treraveller detals
		   
        PdfPCell pertravgues= new PdfPCell(new Paragraph("Multi - City Details",columnheader));		
        pertravgues.setBackgroundColor(new BaseColor(219, 214, 214));
        pertravgues.setColspan(7);		  
        tableguest.addCell(pertravgues);
        
        PdfPCell perguest = new PdfPCell(new Paragraph("Travel List Name",columnheader));		
        perguest.setBackgroundColor(new BaseColor(219, 214, 214));
        tableguest.addCell(perguest);
        PdfPCell perguestc = new PdfPCell(new Paragraph("Location",columnheader));			  
        perguestc.setBackgroundColor(new BaseColor(219, 214, 214));
        tableguest.addCell(perguestc);
        PdfPCell perguestg = new PdfPCell(new Paragraph("Preference",columnheader));			  
        perguestg.setBackgroundColor(new BaseColor(219, 214, 214));
        tableguest.addCell(perguestg);
        PdfPCell perguestca = new PdfPCell(new Paragraph("Origin",columnheader));			  
        perguestca.setBackgroundColor(new BaseColor(219, 214, 214));
        tableguest.addCell(perguestca);      
        PdfPCell perguestcaem = new PdfPCell(new Paragraph("Destination",columnheader));			  
        perguestcaem.setBackgroundColor(new BaseColor(219, 214, 214));
        tableguest.addCell(perguestcaem);
        PdfPCell perguestcaempas = new PdfPCell(new Paragraph("Arrival Date",columnheader));			  
        perguestcaempas.setBackgroundColor(new BaseColor(219, 214, 214));
        tableguest.addCell(perguestcaempas);
        PdfPCell perguestcaempasis = new PdfPCell(new Paragraph("Departure Date",columnheader));			  
        perguestcaempasis.setBackgroundColor(new BaseColor(219, 214, 214));
        tableguest.addCell(perguestcaempasis);
        
        
        PdfPCell pere11 = new PdfPCell(new Paragraph("Other Details ",columnheader));
        pere11.setBackgroundColor(new BaseColor(219, 214, 214));
        pere11.setColspan(7);		  
        tablereq7.addCell(pere11); 
		  
        
        PdfPCell pertravempnname12 = new PdfPCell(new Paragraph("Guest Name",columnheader));	
		  pertravempnname12.setBackgroundColor(new BaseColor(219, 214, 214));
		  tablereq7.addCell(pertravempnname12);
		  
		  PdfPCell perpernr11 = new PdfPCell(new Paragraph("Accomodation Type",columnheader));
		  perpernr11.setBackgroundColor(new BaseColor(219, 214, 214));
		  tablereq7.addCell(perpernr11);
	
	  PdfPCell  perpernr13 = new PdfPCell(new Paragraph("Accomodation Name",columnheader));	
	perpernr13.setBackgroundColor(new BaseColor(219, 214, 214));
	  tablereq7.addCell(perpernr13);		  
	
	/*  PdfPCell   perdep15 = new PdfPCell(new Paragraph("Rent_Car",columnheader));			  
	  tablereq6.addCell(perdep15);
	
	  
	  PdfPCell  perpernr17 = new PdfPCell(new Paragraph("Hotel_Res",columnheader));			  
	  tablereq6.addCell(perpernr17);*/
	
	  PdfPCell  perpernr19 = new PdfPCell(new Paragraph("Hotel City",columnheader));
	perpernr19.setBackgroundColor(new BaseColor(219, 214, 214));
	  tablereq7.addCell(perpernr19);		  
	
	  PdfPCell  perdep21 = new PdfPCell(new Paragraph("Hotel Name",columnheader));
	perdep21.setBackgroundColor(new BaseColor(219, 214, 214));
	  tablereq7.addCell(perdep21);
	  
	  PdfPCell perpernr23 = new PdfPCell(new Paragraph("Pickup Details",columnheader));		
	perpernr23.setBackgroundColor(new BaseColor(219, 214, 214));
	  tablereq7.addCell(perpernr23);
	 
	  PdfPCell perpernr25 = new PdfPCell(new Paragraph("Drop Details",columnheader));	
	perpernr25.setBackgroundColor(new BaseColor(219, 214, 214));
	  tablereq7.addCell(perpernr25);	
        
        rsa.beforeFirst();
        
        
        
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          try {
			while(rsa.next())
			  {
			  PdfPCell perguestv = new PdfPCell(new Paragraph(rsa.getString("name_of_guest"),smallfont));			  
			  tableguest.addCell(perguestv);
			  PdfPCell perguestcv = new PdfPCell(new Paragraph(rsa.getString("location"),smallfont));			  
			  tableguest.addCell(perguestcv);
			  PdfPCell perguestgv = new PdfPCell(new Paragraph(rsa.getString("air_preference"),smallfont));			  
			  tableguest.addCell(perguestgv);
			  PdfPCell perguestcav = new PdfPCell(new Paragraph(rsa.getString("morigin"),smallfont));			  
			  tableguest.addCell(perguestcav);      
			  PdfPCell perguestcaemv = new PdfPCell(new Paragraph(rsa.getString("mdeparture"),smallfont));			  
			  tableguest.addCell(perguestcaemv);
			  PdfPCell perguestcaempasv = new PdfPCell(new Paragraph(rsa.getString("arrival_date"),smallfont));			  
			  tableguest.addCell(perguestcaempasv);
			  PdfPCell perguestcaempasisv = new PdfPCell(new Paragraph(rsa.getString("departure_date"),smallfont));			  
			  tableguest.addCell(perguestcaempasisv);
			  
			  PdfPCell pertravempnnamev13 = new PdfPCell(new Paragraph(rsa.getString("name_of_guest"),smallfont));			  
			  tablereq7.addCell(pertravempnnamev13);
			  
			  PdfPCell perpernrv12 = new PdfPCell(new Paragraph(rsa.getString("maccom_type"),smallfont));			  
			  tablereq7.addCell(perpernrv12);
			 	  
			  PdfPCell perpernrv14 = new PdfPCell(new Paragraph(rsa.getString("maccom_name"),smallfont));			  
			  tablereq7.addCell(perpernrv14);
			  
			/*  PdfPCell perpernrv16 = new PdfPCell(new Paragraph(rs.getString("rent_Car"),smallfont));			  
			  tablereq6.addCell(perpernrv16);
			  
			 
			  PdfPCell  perpernrv18 = new PdfPCell(new Paragraph(rs.getString("hotel_Res"),smallfont));			  
			  tablereq6.addCell(perpernrv18);*/
			
			  PdfPCell perpernrv20 = new PdfPCell(new Paragraph(rsa.getString("mhotel_City"),smallfont));			  
			  tablereq7.addCell(perpernrv20);
			 
			  PdfPCell perpernrv22 = new PdfPCell(new Paragraph(rsa.getString("mhotel_Name"),smallfont));			  
			  tablereq7.addCell(perpernrv22);
			  
			 
			  PdfPCell perpernrv24 = new PdfPCell(new Paragraph(rsa.getString("mpickup_Details"),smallfont));			  
			  tablereq7.addCell(perpernrv24);
			 	  
			  PdfPCell perpernrv26 = new PdfPCell(new Paragraph(rsa.getString("mdrop_Details"),smallfont));			  
			  tablereq7.addCell(perpernrv26);
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
          document.add(tableguest);
          document.add(tablereq7);
			
          PdfPTable tablereq8 = new PdfPTable(6); // No. of columns
		  tablereq8.setWidthPercentage(100); //Width 100%
  
        float[] columnWidthreqss = {2f,2f,2f,2f,2f,2f};//Set Column widths
        tablereq8.setWidths(columnWidthreqss);
         if(Travel_Request_for!=null)
         {
        	 if(Travel_Request_for.equalsIgnoreCase("Multi-City1"))
        	 {
        		  
        	        PdfPCell pertravgues1= new PdfPCell(new Paragraph("Monthly Tour Plan",columnheader));		
        	        pertravgues1.setBackgroundColor(new BaseColor(219, 214, 214));
        	        pertravgues1.setColspan(6);		  
        	        tablereq8.addCell(pertravgues1);
        	        
        	        PdfPCell perguest1 = new PdfPCell(new Paragraph("Date",columnheader));		
        	        perguest1.setBackgroundColor(new BaseColor(219, 214, 214));
        	        tablereq8.addCell(perguest1);
        	        PdfPCell perguestc1 = new PdfPCell(new Paragraph("Origin",columnheader));			  
        	        perguestc1.setBackgroundColor(new BaseColor(219, 214, 214));
        	        tablereq8.addCell(perguestc1);
        	        PdfPCell perguestg1 = new PdfPCell(new Paragraph("Destination",columnheader));			  
        	        perguestg1.setBackgroundColor(new BaseColor(219, 214, 214));
        	        tablereq8.addCell(perguestg1);
        	        PdfPCell perguestca1 = new PdfPCell(new Paragraph("Travel Mode",columnheader));			  
        	        perguestca1.setBackgroundColor(new BaseColor(219, 214, 214));
        	        tablereq8.addCell(perguestca1);  
        	        
        	        PdfPCell perguestcaem1 = new PdfPCell(new Paragraph("Travel Type",columnheader));			  
        	        perguestcaem1.setBackgroundColor(new BaseColor(219, 214, 214));
        	        tablereq8.addCell(perguestcaem1);
        	        PdfPCell perguestcaempas1 = new PdfPCell(new Paragraph("Remarks",columnheader));			  
        	        perguestcaempas1.setBackgroundColor(new BaseColor(219, 214, 214));
        	        tablereq8.addCell(perguestcaempas1); 
        	 }
         }
       
   
        String monthplan="select tm.plan_date,tm.morigin,tm.mdestination,tm.mtravelmode,tm.mtraveltype,tm.mremarks from Travel_MTP as tm,Travel_Desk as td where td.reqNo='"+ reqno+"' and td.id=tm.req_id order by plan_date";
		List appList1 = new LinkedList();
		ResultSet rsAppr1 = ad.selectQuery(monthplan);
/*try {
	while (rsAppr1.next()) {
		TravelRequestForm appr=new TravelRequestForm();
		
		String d[]=rsAppr1.getString("plan_date").split("-");
		String dateString = d[2]+"/"+d[1]+"/"+d[0];;
		
		appr.setFromdate(dateString);
		
		
		//appr.setFromdate(rsAppr1.getString("plan_date"));
		appr.setOrigin(rsAppr1.getString("morigin"));
		appr.setDeparture(rsAppr1.getString("mdestination"));
		appr.setTravelmode(rsAppr1.getString("mtravelmode"));
		appr.setTraveltype(rsAppr1.getString("mtraveltype"));
		appr.setRemarks(rsAppr1.getString("mremarks"));
		appList1.add(appr);
		
		
		 PdfPCell pertravgues1= new PdfPCell(new Paragraph("Monthly Tour Plan",columnheader));		
	        pertravgues1.setBackgroundColor(new BaseColor(219, 214, 214));
	        pertravgues1.setColspan(6);		  
	        tablereq8.addCell(pertravgues1);
	        
	        PdfPCell perguest1 = new PdfPCell(new Paragraph("Date",columnheader));		
	        perguest1.setBackgroundColor(new BaseColor(219, 214, 214));
	        tablereq8.addCell(perguest1);
	        PdfPCell perguestc1 = new PdfPCell(new Paragraph("Origin",columnheader));			  
	        perguestc1.setBackgroundColor(new BaseColor(219, 214, 214));
	        tablereq8.addCell(perguestc1);
	        PdfPCell perguestg1 = new PdfPCell(new Paragraph("Destination",columnheader));			  
	        perguestg1.setBackgroundColor(new BaseColor(219, 214, 214));
	        tablereq8.addCell(perguestg1);
	        PdfPCell perguestca1 = new PdfPCell(new Paragraph("Travel Mode",columnheader));			  
	        perguestca1.setBackgroundColor(new BaseColor(219, 214, 214));
	        tablereq8.addCell(perguestca1);  
	        
	        PdfPCell perguestcaem1 = new PdfPCell(new Paragraph("Travel Type",columnheader));			  
	        perguestcaem1.setBackgroundColor(new BaseColor(219, 214, 214));
	        tablereq8.addCell(perguestcaem1);
	        PdfPCell perguestcaempas1 = new PdfPCell(new Paragraph("Remarks",columnheader));			  
	        perguestcaempas1.setBackgroundColor(new BaseColor(219, 214, 214));
	        tablereq8.addCell(perguestcaempas1);
	        
	     
	}

	   rsAppr1.beforeFirst();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}*/

try {
	while (rsAppr1.next()) {
		/*TravelRequestForm appr=new TravelRequestForm();
		
		String d[]=rsAppr1.getString("plan_date").split("-");
		String dateString = d[2]+"/"+d[1]+"/"+d[0];;
		
		appr.setFromdate(dateString);
		
		
		//appr.setFromdate(rsAppr1.getString("plan_date"));
		appr.setOrigin(rsAppr1.getString("morigin"));
		appr.setDeparture(rsAppr1.getString("mdestination"));
		appr.setTravelmode(rsAppr1.getString("mtravelmode"));
		appr.setTraveltype(rsAppr1.getString("mtraveltype"));
		appr.setRemarks(rsAppr1.getString("mremarks"));
		appList1.add(appr);*/
		String d[]=rsAppr1.getString("plan_date").split("-");
		String dateString = d[2]+"/"+d[1]+"/"+d[0];
		
		
		 PdfPCell perguestv = new PdfPCell(new Paragraph(dateString,smallfont));			  
		 tablereq8.addCell(perguestv);
		  PdfPCell perguestcv = new PdfPCell(new Paragraph(rsAppr1.getString("morigin"),smallfont));			  
		  tablereq8.addCell(perguestcv);
		  PdfPCell perguestgv = new PdfPCell(new Paragraph(rsAppr1.getString("mdestination"),smallfont));			  
		  tablereq8.addCell(perguestgv);
		  PdfPCell perguestcav = new PdfPCell(new Paragraph(rsAppr1.getString("mtravelmode"),smallfont));			  
		  tablereq8.addCell(perguestcav);      
		  PdfPCell perguestcaemv = new PdfPCell(new Paragraph(rsAppr1.getString("mtraveltype"),smallfont));			  
		  tablereq8.addCell(perguestcaemv);
		  PdfPCell perguestcaempasv = new PdfPCell(new Paragraph(rsAppr1.getString("mremarks"),smallfont));			  
		  tablereq8.addCell(perguestcaempasv);
		  
		 	  
	       
	}
}
	 catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
         document.add(tablereq8);
			 document.close();
		      writer.close();
			
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		requestForm.setFileFullPath("/EMicro Files/ESS/Travel Request/PDF/TR_"+reqno+".pdf");
		
		displayMyrequestList(mapping, requestForm, request, response);
		requestForm.setReqStatus("pdf");
		return mapping.findForward("mytravellist");
		}
	
	
	
	
	public ActionForward confirmBookingDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//Created by Arun .Date:15-11-2016
		
TravelRequestForm requestForm = (TravelRequestForm)form;


int req_id=requestForm.getRequestNumber();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		///update

		
		String yp="update Travel_Desk set confirmstatus='Booked',confirmdate=getdate(),confirmby='"+user.getEmployeeNo()+"' ,confirm_Comments='"+requestForm.getConfirm_Comments()+"'  "
				+ " where reqNo='"+requestForm.getRequestNumber()+"'";

		int h=ad.SqlExecuteUpdate(yp);
		
		String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,Approved_date,Role) values (";
		tempinsert = tempinsert + "'"+req_id+"','Travel','','','Booking Accepted','','','','','"+requestForm.getConfirm_Comments()+"','"+user.getEmployeeNo()+"',getdate(),'Travel Desk')";
		 int i11=ad.SqlExecuteUpdate(tempinsert);
		if(h>0)
		{
			EMailer email = new EMailer();
			email.sendMailToRequester(request, "", Integer.toString(requestForm.getRequestNumber()), "Travel Desk");
		}
		displayConfirmationDetails(mapping, requestForm, request, response);
		requestForm.setMessage("Travel Request Accepted Successfully");
		return mapping.findForward("mytravelConfirmationlist");
	}
	
	public ActionForward submitBookingDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
		TravelRequestForm requestForm = (TravelRequestForm)form;
		int req_id=requestForm.getRequestNumber();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		///update
		
		
		String yp="update Travel_Desk set bookingstatus='Bills Updated',bookeddate=getdate(),bookedby='"+user.getEmployeeNo()+"' where reqNo='"+requestForm.getRequestNumber()+"'";
		int h=ad.SqlExecuteUpdate(yp);
		
		String sql = "update Bill_Upload_List set prefix='F' where reqno='"+requestForm.getRequestNumber()+"' ";
		int c = ad.SqlExecuteUpdate(sql);
		
		String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,Approved_date,Role) values (";
		tempinsert = tempinsert + "'"+req_id+"','Travel','','','Bills Updated','','','','','','"+user.getEmployeeNo()+"',getdate(),'Travel Desk')";
		 int i11=ad.SqlExecuteUpdate(tempinsert);
		 
		displayupdateBookingDetails(mapping, requestForm, request, response);
		return mapping.findForward("mytravelBookinglist");
	}
	
	public ActionForward displayupdateBookingDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
       TravelRequestForm requestForm = (TravelRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		ArrayList g=new ArrayList();
		String lis="";
		
		String fromdate="";
		String todate="";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

        Date date = new Date();
        String today = dateFormat.format(date);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date todate1 = cal.getTime();    
        String befordate = dateFormat.format(todate1);
        
        if(requestForm.getFromdate()==null)
        {
        	requestForm.setFromdate("");
        }
        if(requestForm.getTodate()==null)
        {
        	requestForm.setTodate("");
        }
	
		if(requestForm.getFromdate().equalsIgnoreCase(""))
		{
			requestForm.setFromdate(befordate);
		}
		if(requestForm.getTodate().equalsIgnoreCase(""))
		{
			requestForm.setTodate(today);
		}
		
		  String from[]=requestForm.getFromdate().split("/");
		  fromdate=from[2]+"-"+from[1]+"-"+from[0];
		    
		    String to[]=requestForm.getTodate().split("/");
		    todate=to[2]+"-"+to[1]+"-"+to[0];
		    
		    if(requestForm.getReqStatus()==null)
		    {
		    	requestForm.setReqStatus("");
		    }
		    
		    if(requestForm.getReqStatus().equalsIgnoreCase(""))
		    {
		    	requestForm.setReqStatus("Approved");
		    }
		    
		    if(requestForm.getPernr()==null)
		    {
		    	requestForm.setPernr("");
		    }
		    
		if(!requestForm.getPernr().equalsIgnoreCase(""))
		
		{
		 lis="select CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1,CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as bookeddate1,"
				+ "CONVERT(varchar(10),Depart_On,103)+' '+ Preferred_depart_time as departon,"
				+ "CONVERT(varchar(10),Return_On,103)+' '+ Preferred_return_time as returnon,* from Travel_Desk"
				+ "  where Req_status='"+requestForm.getReqStatus()+"' and pernr='"+requestForm.getPernr()+"' and ( CONVERT(date,Req_date) between '"+fromdate+"' and '"+todate+"') and confirmstatus='Booked' order by reqNo desc";
		}
		
		else
		{
			 lis="select CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1,CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as bookeddate1,"
		+ "CONVERT(varchar(10),Depart_On,103)+' '+ Preferred_depart_time as departon,"
		+ "CONVERT(varchar(10),Return_On,103)+' '+ Preferred_return_time as returnon,* from Travel_Desk "
		+ " where Req_status='"+requestForm.getReqStatus()+"'  and (  CONVERT(date,Req_date) between '"+fromdate+"' and '"+todate+"') and confirmstatus='Booked' order by reqNo desc";

			
		}
		
		
		ResultSet ff=ad.selectQuery(lis);
		try {
			while(ff.next())
			{
				TravelRequestForm t = new TravelRequestForm();
				t.setRequestNumber(ff.getInt("reqNo"));
				t.setEmployeeName(Empname(ff.getString("pernr")));
				t.setReqType(ff.getString("Req_type"));
				t.setTypeOfTravel(ff.getString("Type_of_travel"));
				t.setFromPlace(ff.getString("From_place"));
				t.setDepartOn(ff.getString("departon"));
               t.setToPlace(ff.getString("To_place"));
               t.setReturnOn(ff.getString("returnon"));
               t.setReqDate(ff.getString("Created_date1"));
               t.setReqStatus(ff.getString("Req_status"));
               t.setBookingstatus(ff.getString("bookingstatus"));
               t.setBookeddate(ff.getString("bookeddate1"));
               t.setConfirmstatus(ff.getString("confirmstatus"));
               t.setConfirmdate(ff.getString("confirmdate1"));
				g.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(g.size()>0)
		request.setAttribute("travellist", g);
		
		
		
		
		
		requestForm.setMessage("");
		requestForm.setPernr("");
		
		return mapping.findForward("mytravelBookinglist");
	}
	
	
	public ActionForward displayReportDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	{
		//Created by Arun .Date:15-11-2016
		
       TravelRequestForm requestForm = (TravelRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		ArrayList g=new ArrayList();
		String lis="";
		
		String fromdate="";
		String todate="";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

        Date date = new Date();
        String today = dateFormat.format(date);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date todate1 = cal.getTime();    
        String befordate = dateFormat.format(todate1);
        
        if(requestForm.getFromdate()==null)
        {
        	requestForm.setFromdate("");
        }
        if(requestForm.getTodate()==null)
        {
        	requestForm.setTodate("");
        }
	
		if(requestForm.getFromdate().equalsIgnoreCase(""))
		{
			requestForm.setFromdate(befordate);
		}
		if(requestForm.getTodate().equalsIgnoreCase(""))
		{
			requestForm.setTodate(today);
		}
		
		  String from[]=requestForm.getFromdate().split("/");
		  fromdate=from[2]+"-"+from[1]+"-"+from[0];
		    
		    String to[]=requestForm.getTodate().split("/");
		    todate=to[2]+"-"+to[1]+"-"+to[0];
		    
		    if(requestForm.getReqStatus()==null)
		    {
		    	requestForm.setReqStatus("");
		    }
		    
		    if(requestForm.getReqStatus().equalsIgnoreCase(""))
		    {
		    	requestForm.setReqStatus("Department");
		    }
		    
		    if(requestForm.getPernr()==null)
		    {
		    	requestForm.setPernr("");
		    }
		    
		    if(requestForm.getP_dept()==null)
			 {
		    	requestForm.setP_dept(""); 
			 }
		    
		   
		    ArrayList deptlist = new ArrayList();
		    ArrayList userlist = new ArrayList();
		    ArrayList travellist = new ArrayList();
		    
		   if(requestForm.getReqStatus().equalsIgnoreCase("Department"))
		   {
			   
			   lis="select '0' as ord ,emp_official_info.emp_fullname,emp_official_info.pernr,  travel_desk.reqno,sum(bill_upload_list.billamount) as billamount, "
			   	+ " CONVERT(varchar(10), travel_desk.req_date,103)+' '+ CONVERT(varchar(5), "
			   	+ " travel_desk.req_date,108)  as req_date  "
			   	+ " from travel_desk , emp_official_info,Bill_Upload_List "
			   	+ " where travel_desk.PERNR= emp_official_info.PERNR and "
			   	+ "  bill_upload_list.reqNo=travel_desk.reqno ";
			   
				 if(!fromdate.equalsIgnoreCase(""))
				 {
					 lis=lis+" and (CONVERT(date,travel_desk.Req_date) between '"+fromdate+"' and '"+todate+"')";
				 }
				 
				 if(!requestForm.getP_dept().equalsIgnoreCase(""))
				 {
					 lis=lis+" and emp_official_info.DPTID ='"+requestForm.getP_dept()+"' ";
				 }
				 
				 lis=lis+" group by travel_desk.reqno, travel_desk.billdate,travel_desk.Req_date,"
				 		+ " emp_official_info.pernr,emp_official_info.emp_fullname";
				 
				 lis=lis+" union "
				 		+ "select '1' as ord,'','','0',sum(billamount),'' from ("+lis+")t";
				 
			
				ResultSet rs = ad.selectQuery(lis);
				try {
					while(rs.next())
					{
						TravelRequestForm t = new TravelRequestForm();
						t.setRequestNumber(rs.getInt("reqno"));
						t.setBillamount(rs.getString("billamount"));
						t.setPernr(rs.getString("emp_fullname"));
						t.setReqDate(rs.getString("req_date"));
						deptlist.add(t);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				if(deptlist.size()>0)
					request.setAttribute("deptlist", deptlist);
				
				 
		   }
		   
		   if(requestForm.getReqStatus().equalsIgnoreCase("User_Wise"))
		   {

			   
			   lis="select '0' as ord , emp_official_info.emp_fullname,emp_official_info.pernr,  travel_desk.reqno,sum(bill_upload_list.billamount) as billamount, "
			   	+ " CONVERT(varchar(10), travel_desk.req_date,103)+' '+ CONVERT(varchar(5), "
			   	+ " travel_desk.req_date,108)  as req_date  "
			   	+ " from travel_desk , emp_official_info,Bill_Upload_List "
			   	+ " where travel_desk.PERNR= emp_official_info.PERNR and "
			   	+ "  bill_upload_list.reqNo=travel_desk.reqno ";
			   
				 if(!fromdate.equalsIgnoreCase(""))
				 {
					 lis=lis+" and (CONVERT(date,travel_desk.Req_date) between '"+fromdate+"' and '"+todate+"')";
				 }
				 
				 if(!requestForm.getP_dept().equalsIgnoreCase(""))
				 {
					 lis=lis+" and emp_official_info.pernr ='"+requestForm.getGuest_pernr()+"' ";
				 }
				 
				 lis=lis+" group by travel_desk.reqno, travel_desk.billdate,travel_desk.Req_date,"
				 		+ " emp_official_info.pernr,emp_official_info.emp_fullname";
				 
				 lis=lis+" union "
					 		+ "select '1' as ord,'','','0',sum(billamount),'' from ("+lis+")t";
			
				ResultSet rs = ad.selectQuery(lis);
				try {
					while(rs.next())
					{
						TravelRequestForm t = new TravelRequestForm();
						t.setRequestNumber(rs.getInt("reqno"));
						t.setBillamount(rs.getString("billamount"));
						t.setPernr(rs.getString("emp_fullname"));
						t.setReqDate(rs.getString("req_date"));
						userlist.add(t);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				if(userlist.size()>0)
					request.setAttribute("userlist", userlist);
				
				 
		   
			   
			   
		   }
		   
		   
		   if(requestForm.getReqStatus().equalsIgnoreCase("Travel_Agent_Wise"))
		   {

			   
			   lis="select '0' as ord ,emp_official_info.emp_fullname,emp_official_info.pernr,  travel_desk.reqno,bill_upload_list.billamount, "
			   	+ " CONVERT(varchar(10), travel_desk.req_date,103)+' '+ CONVERT(varchar(5), "
			   	+ " travel_desk.req_date,108)  as req_date  "
			   	+ " from travel_desk , emp_official_info,Bill_Upload_List "
			   	+ " where travel_desk.PERNR= emp_official_info.PERNR and "
			   	+ "  bill_upload_list.reqNo=travel_desk.reqno ";
			   
				 if(!fromdate.equalsIgnoreCase(""))
				 {
					 lis=lis+" and (CONVERT(date,travel_desk.Req_date) between '"+fromdate+"' and '"+todate+"')";
				 }
				 
				 if(!requestForm.getP_dept().equalsIgnoreCase(""))
				 {
					 lis=lis+" and Bill_Upload_List.travelagentname ='"+requestForm.getTravelagentname()+"' ";
				 }
				 
				 lis=lis+" union "
					 		+ "select '1' as ord,'','','0',sum(billamount),'' from ("+lis+")t";
				ResultSet rs = ad.selectQuery(lis);
				try {
					while(rs.next())
					{
						TravelRequestForm t = new TravelRequestForm();
						t.setRequestNumber(rs.getInt("reqno"));
						t.setBillamount(rs.getString("billamount"));
						t.setPernr(rs.getString("emp_fullname"));
						t.setReqDate(rs.getString("req_date"));
						travellist.add(t);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				if(travellist.size()>0)
					request.setAttribute("travellist", travellist);
			   
			   
		   }
		    
		requestForm.setMessage("");
		requestForm.setPernr("");
		
		
		ArrayList deptList=new ArrayList();
		ArrayList deptLabelList=new ArrayList();
		ResultSet rs15 = ad.selectQuery("select dptid," +
				"DPTLTXT from DEPARTMENT order by DPTLTXT "); 
		try {
					while(rs15.next()) {
						deptList.add(rs15.getString("dptid"));
						deptLabelList.add(rs15.getString("DPTLTXT"));
					}
					rs15.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		requestForm.setDeptList(deptList);
		requestForm.setDeptLabelList(deptLabelList);
		
		
		 ArrayList travIdList=new ArrayList();
			ArrayList travLabelList=new ArrayList();
			ResultSet rs11 = ad.selectQuery("select * from Travel_Master "); 
					try {
						while(rs11.next()) {
							travIdList.add(rs11.getString("trav_id"));
							travLabelList.add(rs11.getString("trav_name"));
						}
						rs11.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					requestForm.setTravIdList(travIdList);
					requestForm.setTravLabelList(travLabelList);			
					
		
		
		return mapping.findForward("displayReportDetails");
	}
	
	
	public ActionForward displayConfirmationDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//Created by Arun .Date:15-11-2016
		
       TravelRequestForm requestForm = (TravelRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		ArrayList g=new ArrayList();
		String lis="";
		
		String fromdate="";
		String todate="";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

        Date date = new Date();
        String today = dateFormat.format(date);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date todate1 = cal.getTime();    
        String befordate = dateFormat.format(todate1);
        
        if(requestForm.getFromdate()==null)
        {
        	requestForm.setFromdate("");
        }
        if(requestForm.getTodate()==null)
        {
        	requestForm.setTodate("");
        }
	
		if(requestForm.getFromdate().equalsIgnoreCase(""))
		{
			requestForm.setFromdate(befordate);
		}
		if(requestForm.getTodate().equalsIgnoreCase(""))
		{
			requestForm.setTodate(today);
		}
		
		  String from[]=requestForm.getFromdate().split("/");
		  fromdate=from[2]+"-"+from[1]+"-"+from[0];
		    
		    String to[]=requestForm.getTodate().split("/");
		    todate=to[2]+"-"+to[1]+"-"+to[0];
		    
		    if(requestForm.getReqStatus()==null)
		    {
		    	requestForm.setReqStatus("");
		    }
		    
		    if(requestForm.getPernr()==null)
		    {
		    	requestForm.setPernr("");
		    }
		    
		
		
		lis="select CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1,CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as bookeddate1,"
		+ " CONVERT(varchar(10),Depart_On,103)+' '+ Preferred_depart_time as departon,"
		+ " CONVERT(varchar(10),Return_On,103)+' '+ Preferred_return_time as returnon,* from Travel_Desk,Travel_desk_Approvers "
		+ " where travel_desk.travel_desk_type=Travel_desk_Approvers.ApproverId"
		+ " and Travel_desk_Approvers.Travel_Type=travel_desk.travelmode"
		+ " and Travel_desk_Approvers.ApproverId='"+user.getEmployeeNo()+"' "
		+ " and isnull(confirmstatus,'')='"+requestForm.getReqStatus()+"' "
		+ " and (  CONVERT(date,Req_date) between '"+fromdate+"' and '"+todate+"')";
		if(!requestForm.getReqStatus().equalsIgnoreCase("Rejected"))	 
		lis=lis+ " and req_status ='Approved'";
		else
		lis=lis+ " and req_status ='Rejected'";
		
		if(!requestForm.getPernr().equalsIgnoreCase(""))
		{
			lis=lis+"and pernr='"+requestForm.getPernr()+"'";
		}
		lis=lis	+ " order by reqNo desc";
		
		ResultSet ff=ad.selectQuery(lis);
		try {
			while(ff.next())
			{
				TravelRequestForm t = new TravelRequestForm();
				t.setRequestNumber(ff.getInt("reqNo"));
				t.setEmployeeName(Empname(ff.getString("pernr")));
				t.setReqType(ff.getString("Req_type"));
				t.setTypeOfTravel(ff.getString("Type_of_travel"));
				t.setFromPlace(ff.getString("From_place"));
				t.setDepartOn(ff.getString("departon"));
               t.setToPlace(ff.getString("To_place"));
               t.setReturnOn(ff.getString("returnon"));
               t.setReqDate(ff.getString("Created_date1"));
               t.setReqStatus(ff.getString("Req_status"));
               t.setBookingstatus(ff.getString("bookingstatus"));
               t.setBookeddate(ff.getString("bookeddate1"));
               t.setConfirmstatus(ff.getString("confirmstatus"));
               t.setConfirmdate(ff.getString("confirmdate1"));
				g.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(g.size()>0)
		request.setAttribute("travellist", g);
		
		requestForm.setMessage("");
		requestForm.setPernr("");
		
		
		
		
		
		
		return mapping.findForward("mytravelConfirmationlist");
	}
	
	public ActionForward displaySubmitFinanceDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
		TravelRequestForm help = (TravelRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		
		  ArrayList travIdList=new ArrayList();
			ArrayList travLabelList=new ArrayList();
			ResultSet rs11 = ad.selectQuery("select * from Travel_Master "); 
					try {
						while(rs11.next()) {
							travIdList.add(rs11.getString("trav_id"));
							travLabelList.add(rs11.getString("trav_name"));
						}
						rs11.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					help.setTravIdList(travIdList);
					help.setTravLabelList(travLabelList);
					String fromdate="";
					String todate="";
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

			        Date date = new Date();
			        String today = dateFormat.format(date);

			        Calendar cal = Calendar.getInstance();
			        cal.add(Calendar.DATE, -7);
			        Date todate1 = cal.getTime();    
			        String befordate = dateFormat.format(todate1);
			        
			        if(help.getFromdate()==null)
			        {
			        	help.setFromdate("");
			        }
			        if(help.getTodate()==null)
			        {
			        	help.setTodate("");
			        }
				
					if(help.getFromdate().equalsIgnoreCase(""))
					{
						help.setFromdate(befordate);
					}
					if(help.getTodate().equalsIgnoreCase(""))
					{
						help.setTodate(today);
					}
					
					  String from[]=help.getFromdate().split("/");
					  fromdate=from[2]+"-"+from[1]+"-"+from[0];
					    
					    String to[]=help.getTodate().split("/");
					    todate=to[2]+"-"+to[1]+"-"+to[0];			
					
					
		
		return mapping.findForward("MyFinanceList");
	}
	
	
	public ActionForward displayReceieveFinanceDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
		TravelRequestForm help = (TravelRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		
		  ArrayList travIdList=new ArrayList();
			ArrayList travLabelList=new ArrayList();
			ResultSet rs11 = ad.selectQuery("select * from Travel_Master "); 
					try {
						while(rs11.next()) {
							travIdList.add(rs11.getString("trav_id"));
							travLabelList.add(rs11.getString("trav_name"));
						}
						rs11.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					help.setTravIdList(travIdList);
					help.setTravLabelList(travLabelList);
					
					String fromdate="";
					String todate="";
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

			        Date date = new Date();
			        String today = dateFormat.format(date);

			        Calendar cal = Calendar.getInstance();
			        cal.add(Calendar.DATE, -7);
			        Date todate1 = cal.getTime();    
			        String befordate = dateFormat.format(todate1);
			        
			        if(help.getFromdate()==null)
			        {
			        	help.setFromdate("");
			        }
			        if(help.getTodate()==null)
			        {
			        	help.setTodate("");
			        }
				
					if(help.getFromdate().equalsIgnoreCase(""))
					{
						help.setFromdate(befordate);
					}
					if(help.getTodate().equalsIgnoreCase(""))
					{
						help.setTodate(today);
					}
					
					  String from[]=help.getFromdate().split("/");
					  fromdate=from[2]+"-"+from[1]+"-"+from[0];
					    
					    String to[]=help.getTodate().split("/");
					    todate=to[2]+"-"+to[1]+"-"+to[0];			
		
		return mapping.findForward("MyFinanceReceieveList");
	}
	
	
	public ActionForward searchSubmitFinanceDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
		TravelRequestForm help = (TravelRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		
		
		  ArrayList travIdList=new ArrayList();
			ArrayList travLabelList=new ArrayList();
			ResultSet rs11 = ad.selectQuery("select * from Travel_Master "); 
					try {
						while(rs11.next()) {
							travIdList.add(rs11.getString("trav_id"));
							travLabelList.add(rs11.getString("trav_name"));
						}
						rs11.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					help.setTravIdList(travIdList);
					help.setTravLabelList(travLabelList);
					
				String fromdate="";
				String todate="";
					
				String from[]=help.getFromdate().split("/");
				fromdate=from[2]+"-"+from[1]+"-"+from[0];
					    
				String to[]=help.getTodate().split("/");
				todate=to[2]+"-"+to[1]+"-"+to[0];	
			
		
		
				
		if(help.getReqStatus().equalsIgnoreCase("Booked"))
		{
			
		
		
		ArrayList finlist = new ArrayList();		
		String  a=" select Bill_Upload_List.* ,isnull(review_Rating,'')as review_Rating,isnull(travel_desk.review_rating,'') as review from travel_desk,Bill_Upload_List"
				+ " where Travel_Desk.reqNo=Bill_Upload_List.reqNo and "
				+ " convert(date,req_date) >='"+fromdate+"' and convert(date,req_date)<='"+todate+"' "
				+ " and req_status='Approved' and travel_desk.bookedby!='' and Bill_Upload_List.travelagentname='"+help.getTravelagentname()+"'"
				+ " and isnull(Bill_Upload_List.finance_status,'')!='Submitted'  ";
		ResultSet rsa=ad.selectQuery(a);
		try {
			while(rsa.next())
			{

				TravelRequestForm emp=new TravelRequestForm();
				emp.setId(rsa.getInt("id"));
				emp.setRequestNumber(rsa.getInt("reqno"));
				emp.setBilltype(rsa.getString("billtype"));
				emp.setTravelagentname(rsa.getString("travelagentname"));
				emp.setBillno(rsa.getString("billno"));
				emp.setBilldate(rsa.getString("billdate"));
				emp.setBillamount(rsa.getString("billamount"));
				emp.setFileName(rsa.getString("file_Name"));
				emp.setFileFullPath(rsa.getString("path"));
				if(rsa.getString("review_Rating").equalsIgnoreCase(""))
				{
					emp.setReview_Rating("No");
				}
				else
				{
					emp.setReview_Rating("Yes");
				}
				
				
				
				
				
				finlist.add(emp);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("finlist", finlist);
		
		}
		
		if(help.getReqStatus().equalsIgnoreCase("Submitted to Finance"))
		{
			
			
			ArrayList finsublist = new ArrayList();		
			String  a=" select * , convert(nvarchar(103),creation_date,103) as billdate from travel_finance"
					+ " where  convert(date,creation_date) >='"+fromdate+"' and convert(date,creation_date)<='"+todate+"' ";
			
			if(!help.getTravelagentname().equalsIgnoreCase(""))
			{
				a=a+" and travelagentname= '"+help.getTravelagentname()+"' ";
			}
					
			ResultSet rsa=ad.selectQuery(a);
			try {
				while(rsa.next())
				{

					TravelRequestForm emp=new TravelRequestForm();
					emp.setId(rsa.getInt("id"));
					emp.setRequestNumber(rsa.getInt("reqno"));
					emp.setTravelagentname(rsa.getString("travelagentname"));
					emp.setBilldate(rsa.getString("billdate"));
					emp.setBillamount(rsa.getString("total_amount"));
					finsublist.add(emp);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("finsublist", finsublist);
			
			
		}
			
			
					
					
		
		return mapping.findForward("MyFinanceList");
	}
	
	
	
	public ActionForward nextdisplayMyrequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//Created by Arun .Date:15-11-2016
		
		TravelRequestForm requestForm = (TravelRequestForm)form;
		
		int nextval=requestForm.getNext();
		int i=0;
		i=nextval;
		requestForm.setStartRecord(i+1);
		requestForm.setPrev(nextval);
		int end=nextval+10;
		int start=0;
		int row=0;

		//sform.setEndRecord(end);
		 ArrayList alldata=new ArrayList();
		try{
			
			alldata=displayMyrequest(mapping, requestForm, request, response);
		}
			 
		 
		 catch(Exception e){
			 e.printStackTrace();
		 }
		//HttpSession session=request.getSession();
		
		ArrayList NewList=new ArrayList();
		Iterator it=alldata.iterator();
		try{
		while(start<end){
			if(it.hasNext()&&start==nextval){
				NewList.add(it.next());
				nextval++;
				
			}
			else
			it.next();
			start++;
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(nextval);
		requestForm.setEndRecord(nextval);
		if(it.hasNext()){
			request.setAttribute("nextButton", "yes");
		request.setAttribute("previousButton", "ok");
		}
		else{
			request.setAttribute("disableNextButton","yes");
		request.setAttribute("previousButton", "ok");
		}
		request.setAttribute("displayRecordNo","ok");	
		
		requestForm.setNext(nextval);
		//myReqForm.setRow(row);
		request.setAttribute("travellist", NewList);
		requestForm.setMessage("");
		
		requestForm.setReqStatus("");
		return mapping.findForward("mytravellist");
	}
	public ActionForward prevdisplayMyrequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//Created by Arun .Date:15-11-2016
		
		TravelRequestForm requestForm = (TravelRequestForm)form;
		
		
	
	
		
		int prev=requestForm.getPrev();
		int start=prev-10;
		//start--;
		//prev--;
		if(start<0)
			start=0;
		requestForm.setStartRecord(start+1);
		requestForm.setEndRecord(prev);
		int i=0;
		int row=0;
		EssDao ad=new EssDao();
		//sform.setEndRecord(end);
		ArrayList alldata=new ArrayList();
		
		try{

			
				    	 alldata=displayMyrequest(mapping, requestForm, request, response);
			 }
			 
		 
		 catch(Exception e){
			 e.printStackTrace();
		 }
		
		
		ArrayList NewList=new ArrayList();
		
		
		Iterator it=alldata.iterator();
		while(i<prev){
			if(it.hasNext()&&i==start){
				NewList.add(it.next());
				start++;
			}
			else
			it.next();
			i++;
		}
		if((prev-10)<=0){
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("nextButton", "ok");
	          }
	    
		else{
			request.setAttribute("previousButton", "ok");
		request.setAttribute("nextButton", "ok");
		}
		requestForm.setPrev(prev-10);
		requestForm.setNext(prev);
	
		request.setAttribute("displayRecordNo","ok");
		
	
		
		
		request.setAttribute("travellist", NewList);
		
		requestForm.setMessage("");
		
		requestForm.setReqStatus("");
		return mapping.findForward("mytravellist");
	}
	
	public ArrayList displayMyrequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
		
		TravelRequestForm requestForm = (TravelRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		ArrayList g=new ArrayList();
		
		if(requestForm.getReqStatus()==null)
			requestForm.setReqStatus("");
		
		if(!requestForm.getReqStatus().equalsIgnoreCase("Reverted"))
		{
		
		String lis="select CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1,CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as bookeddate1,"
				+ "CONVERT(varchar(10),Depart_On,103)+' '+ Preferred_depart_time as departon,"
				+ "CONVERT(varchar(10),Return_On,103)+' '+ Preferred_return_time as returnon,* from Travel_Desk where pernr='"+user.getEmployeeNo()+"' and reqno is not null ";
				
		
			
		
			if(!requestForm.getReqStatus().equalsIgnoreCase("all"))
			lis=lis+ " and req_status='"+requestForm.getReqStatus()+"'";
			lis=lis+ " and reqno is not null order by reqNo desc";
		ResultSet ff=ad.selectQuery(lis);
		try {
			while(ff.next())
			{
				TravelRequestForm t = new TravelRequestForm();
				t.setRequestNumber(ff.getInt("reqNo"));
				t.setReqType(ff.getString("Req_type"));
				if(ff.getString("Type_of_travel")==null||ff.getString("Type_of_travel").equalsIgnoreCase("null"))
				{
					t.setTypeOfTravel("");
				}
				else
				{
					t.setTypeOfTravel(ff.getString("Type_of_travel"));
				}
			
				t.setFromPlace(ff.getString("From_place"));
				t.setDepartOn(ff.getString("departon"));
                t.setToPlace(ff.getString("To_place"));
                t.setReturnOn(ff.getString("returnon"));
                t.setReqDate(ff.getString("Created_date1"));
                t.setReqStatus(ff.getString("Req_status"));
                t.setBookingstatus(ff.getString("bookingstatus"));
                t.setBookeddate(ff.getString("bookeddate1"));
                t.setConfirmstatus(ff.getString("confirmstatus"));
                t.setConfirmdate(ff.getString("confirmdate1"));
                t.setTravel_Days(ff.getString("travel_Days"));
                t.setReview_Rating(ff.getString("review_Rating"));
				g.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(g.size()>0)
		request.setAttribute("travellist", g);
		
		
		}
		if(requestForm.getReqStatus().equalsIgnoreCase("Reverted"))
		{

			
			String lis="select CONVERT(varchar(10),confirmdate,103)+' '+ CONVERT(varchar(5),confirmdate,108) as confirmdate1,CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Created_date1,CONVERT(varchar(10),bookeddate,103)+' '+ CONVERT(varchar(5),bookeddate,108) as bookeddate1,"
					+ "CONVERT(varchar(10),Depart_On,103)+' '+ Preferred_depart_time as departon,"
					+ "CONVERT(varchar(10),Return_On,103)+' '+ Preferred_return_time as returnon,* from Travel_Desk where pernr='"+user.getEmployeeNo()+"' and reqno is not null ";
					
			
				
			
				
				lis=lis+ " and req_status='Reverted'";
				lis=lis+ " and reqno is not null order by reqNo desc";
			ResultSet ff=ad.selectQuery(lis);
			try {
				while(ff.next())
				{
					TravelRequestForm t = new TravelRequestForm();
					t.setRequestNumber(ff.getInt("reqNo"));
					t.setReqType(ff.getString("Req_type"));
					if(ff.getString("Type_of_travel")==null||ff.getString("Type_of_travel").equalsIgnoreCase("null"))
					{
						t.setTypeOfTravel("");
					}
					else
					{
						t.setTypeOfTravel(ff.getString("Type_of_travel"));
					}
					/*t.setTypeOfTravel(ff.getString("Type_of_travel"));*/
					t.setFromPlace(ff.getString("From_place"));
					t.setDepartOn(ff.getString("departon"));
	                t.setToPlace(ff.getString("To_place"));
	                t.setReturnOn(ff.getString("returnon"));
	                t.setReqDate(ff.getString("Created_date1"));
	                t.setReqStatus(ff.getString("Req_status"));
	                t.setBookingstatus(ff.getString("bookingstatus"));
	                t.setBookeddate(ff.getString("bookeddate1"));
	                t.setConfirmstatus(ff.getString("confirmstatus"));
	                t.setConfirmdate(ff.getString("confirmdate1"));
	                t.setTravel_Days(ff.getString("travel_Days"));
	                t.setReview_Rating(ff.getString("review_Rating"));
					g.add(t);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(g.size()>0)
			request.setAttribute("travellist1", g);
			
		
			
		}
		return g;
	
	}

	
	
	public ActionForward displayMyrequestList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
		
		TravelRequestForm requestForm = (TravelRequestForm)form;
		
	
		ArrayList listOfMyRequest=new ArrayList();
		try{
					
			if(requestForm.getReqStatus()==null)
			requestForm.setReqStatus("");	
			if(requestForm.getReqStatus().equalsIgnoreCase(""))
						requestForm.setReqStatus("Pending");	
		    	listOfMyRequest=displayMyrequest(mapping, requestForm, request, response);

      
		
		ArrayList newList=new ArrayList();
		
		Iterator it=listOfMyRequest.iterator();
		
		int i=0;
		while(i<10){
			if(it.hasNext()){
				newList.add(it.next());
			i++;
			}
			else
				break;
		}
		requestForm.setStartRecord(1);
		requestForm.setEndRecord(i);
		requestForm.setNext(i);
	if(i>=10){
		
		request.setAttribute("displayRecordNo","ok");
		if(it.hasNext()){
		request.setAttribute("nextButton", "yes");
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("displayRecordNo","ok");
		}}
		else{
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("disableNextButton","yes");
		
		}
		  //myReqForm.setMyRequestRecords(listOfMyRequest);
	if(!requestForm.getReqStatus().equalsIgnoreCase("Reverted"))	
	{
		request.setAttribute("travellist", newList);
	}
	else
	{
		request.setAttribute("travellist1", newList);
	}
	
		//request.setAttribute("myRequestList1", listOfMyRequest);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(listOfMyRequest.size()==0)
		{
			
			request.setAttribute("noRecords", "noRecords");
		}
		
		
		
		String fromdate="";
		String todate="";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

        Date date = new Date();
        String today = dateFormat.format(date);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date todate1 = cal.getTime();    
        String befordate = dateFormat.format(todate1);
        
        if(requestForm.getFromdate()==null)
        {
        	requestForm.setFromdate("");
        }
        if(requestForm.getTodate()==null)
        {
        	requestForm.setTodate("");
        }
	
		if(requestForm.getFromdate().equalsIgnoreCase(""))
		{
			requestForm.setFromdate(befordate);
		}
		if(requestForm.getTodate().equalsIgnoreCase(""))
		{
			requestForm.setTodate(today);
		}
		
		  String from[]=requestForm.getFromdate().split("/");
		  fromdate=from[2]+"-"+from[1]+"-"+from[0];
		    
		  String to[]=requestForm.getTodate().split("/");
		  todate=to[2]+"-"+to[1]+"-"+to[0];
		
		  requestForm.setReqStatus("");
		
		return mapping.findForward("mytravellist");
	}
	
	public ActionForward searchForApprovers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");

		String sTxt = request.getParameter("searchText");
		String reqFieldName = request.getParameter("reqFieldName");
		TravelRequestForm f = (TravelRequestForm) form;

		f.setSearchText(sTxt);

		LinkedList searchList = new LinkedList();
		try {
			String searchQuery = "select  DATEDIFF(hour,DOB,GETDATE())/8766 as Dob1 ,dpt.DPTSTXT,*,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dpt,DESIGNATION desg where dpt.DPTID=emp.DPTID and desg.DSGID=emp.DSGID and (EMP_FULLNAME like '%"
					+ sTxt
					+ "%' or EMAIL_ID like '%"
					+ sTxt
					+ "%' or PERNR like '%" + sTxt + "%')";
			ResultSet rs = ad.selectQuery(searchQuery);
			int i = 1;
			while (rs.next()) {

				ESSApproverForm emp = new ESSApproverForm();
				emp.setEmp(rs.getString("EMP_FULLNAME") + "-"
						+ rs.getString("PERNR") + "-" + rs.getString("DPTSTXT")
						+ "-" + rs.getString("DSGSTXT") + "-"
						+ EMicroUtils.display(rs.getDate("DOJ"))+"-"+rs.getString("sex")+"-"+rs.getString("title")+"-"+rs.getString("email_id")+"-"+rs.getString("Dob1"));
				emp.setEmpname(rs.getString("EMP_FULLNAME") + "-"
						+ rs.getString("PERNR") + "-" + rs.getString("DPTSTXT"));

				emp.setReqFieldName(reqFieldName);
				searchList.add(emp);
				if (i == 8) {
					break;
				}
				i++;
			}

		}

		catch (SQLException sqle) {
			System.out.println("SQLException @ searching User Details");
			sqle.printStackTrace();
		}

		request.setAttribute("SearchUserDetails", searchList);

		return mapping.findForward("searchemployee");
	}
	
	
	public ActionForward cancelRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		TravelRequestForm help=(TravelRequestForm)form;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String requestno=request.getParameter("requstNo");
		
		String modifyRequest="update All_Request set Req_Status='Self Cancelled' ,Approved_date=getdate()  where Req_Type='Travel' and Req_Id='"+requestno+"'";	
		int statsChange=ad.SqlExecuteUpdate(modifyRequest);	
		if(statsChange>0){
		help.setMessage2("Request has been cancelled successfully");
		String updateSql= "update Travel_Desk set Req_status='Self Cancelled'  where reqno='"+requestno+"'";
		int ii=ad.SqlExecuteUpdate(updateSql);
		
		String updateSql1= "update OnDuty_details set Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',OnDuty_status='2',Approver_Status='Rejected',approved_date='',rejected_date=getdate(),comments=''"
				+ " where travel_no='"+requestno+"'";
		int recordChange=ad.SqlExecuteUpdate(updateSql1);
		
		help.setMessage("Travel Request No TN"+requestno+" Successfully Cancelled");

		
		
		System.out.print(ii);
		}
		
		return displayMyrequestList(mapping, form, request, response);
		
		
	}
	
	
	public ActionForward rejectRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		TravelRequestForm help=(TravelRequestForm)form;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String requestno=request.getParameter("requstNo");
		/**/	
		
		help.setMessage2("Request has been cancelled successfully");
		String updateSql= "update Travel_Desk set Req_status='Rejected' ,confirmstatus='Rejected',"
				+ "  confirmdate=getdate(),confirmby='"+user.getEmployeeNo()+"',Approved_date=getdate() ,confirm_Comments='"+help.getConfirm_Comments()+"'    where reqno='"+requestno+"'";
		int ii=ad.SqlExecuteUpdate(updateSql);
		
		
		String updateSql1= "update OnDuty_details set Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',OnDuty_status='2',Approver_Status='Rejected',approved_date='',rejected_date=getdate(),comments='' "
				+ "where travel_no='"+requestno+"'";
		int recordChange=ad.SqlExecuteUpdate(updateSql1);

		
		
		return displayConfirmationDetails(mapping, form, request, response);
		
		
	}
	
	
	public ActionForward displaytravellerList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		TravelRequestForm help=(TravelRequestForm)form;
		String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1,* from Travel_Desk_Travellers where req_id='"+help.getId()+"'" ;
        ResultSet b=ad.selectQuery(his);
        ArrayList emplist = new ArrayList();
		try {
			while(b.next())
			{
				
					TravelRequestForm emp=new TravelRequestForm();
					String ere[]=empDet(Integer.toString(b.getInt("PERNR")));
					emp.setGuest_pernr(Integer.toString(b.getInt("PERNR")));
					emp.setEmployeeName(ere[0]);
					emp.setLocationId(ere[1]);
					emp.setDepartment(ere[2]);
					emp.setDesignation(ere[3]); 
					emp.setUserAge(ere[5]);
					emp.setUserGender(ere[7]);                		
					emp.setGuest_Type(b.getString("Req_type"));
					emp.setPassportno(b.getString("Passport_no"));
					emp.setPassportplace(b.getString("Place_issue"));
					emp.setPassportexpirydate(b.getString("Date_of_expiry"));
					emp.setGuestName(b.getString("Name_of_guest"));
					emp.setGuestContactNo(b.getString("Contact_guest"));
					emp.setGuest_Company(b.getString("guest_Company"));
					emp.setGuestAge(b.getString("Age_guest"));
					emp.setGender(b.getString("Gender_guest"));
					emp.setEmail_Guest(b.getString("Email_guest"));
					emp.setFileName(b.getString("file_name"));
					emp.setFileFullPath(b.getString("path"));
					emp.setGuest_Title(b.getString("guest_Title"));
					emp.setGuest_Visano(b.getString("guest_Visano"));
					emp.setGuest_DOB(b.getString("guest_DOB"));
					emp.setGuest_Meal(b.getString("guest_Meal"));
					emp.setId(b.getInt("id"));
					emp.setOn_duty(b.getString("on_duty"));
					
					if(!b.getString("Date_of_expiry1").contains("1900"))
					emp.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
					else
			    	emp.setUserpassportexpirydate("");
					
					emplist .add(emp);
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        if(emplist.size()>0)
        	request.setAttribute("emplist", emplist);	
		return mapping.findForward("travellerList");
	}
	
	
	public static  boolean getNS(String hostName)
	{
	// Perform a DNS lookup for NS records in the domain
	Hashtable env = new Hashtable();

	boolean flag ;
	env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
	DirContext ictx;



	try
	{
	ictx = new InitialDirContext(env);
	Attributes attrs = ictx.getAttributes(hostName, new String[] { "NS" });
	Attribute attr = attrs.get("NS");

	ArrayList res = new ArrayList();
	NamingEnumeration en = attr.getAll();

	while (en.hasMore())
	{
	String x = (String) en.next();
	if (x.endsWith(".")) x = x.substring(0, x.length() - 1);
	res.add(x);
	}



	if(res.size()>0)
	{
	flag=true;
	}
	else
	{
	flag=false;

	}

	}



	catch (Exception e) {
	// TODO Auto-generated catch block
	flag=false;
	e.printStackTrace();
	}

	return flag;



	}
	
	
	public ActionForward gettravellerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		TravelRequestForm help=(TravelRequestForm)form;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		String req_id=request.getParameter("dt");
		List techList=new LinkedList();
		try{
		String getTecnicansList=" select id,req_Type,pernr,name_of_guest from Travel_Desk_Travellers where req_id='"+req_id+"' order by id";
		ResultSet rs=ad.selectQuery(getTecnicansList);
		while(rs.next()){
			TravelRequestForm issue=new TravelRequestForm();
			issue.setId(rs.getInt("id"));
			issue.setReqType(rs.getString("req_Type"));
			issue.setPernr(rs.getString("pernr"));
			issue.setGuestName(rs.getString("name_of_guest"));
			
		
		techList.add(issue);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("techList", techList);
	
		
		
		return mapping.findForward("travellerlistAjax");
		
		
	}
	
	
	
	public ActionForward billerList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TravelRequestForm help = (TravelRequestForm) form;
		
		String his="select id,billtype,travelagentname,convert(nvarchar(10),billdate,103) as billdate ,"
		 		+ "billno, billamount,file_Name,path from Bill_Upload_List where reqNo='"+help.getRequestNumber()+"'"
		 		+ "union "
		 		+ "select 0,'Total','','' ,'',sum(  case when billtype='Credit Note' then -1*billamount else billamount end  ),'','' from Bill_Upload_List  where reqNo='"+help.getRequestNumber()+"' "
		 		+ "group by reqNo order by 1 desc " ;
	        ResultSet b=ad.selectQuery(his);
	        ArrayList emplist = new ArrayList();
			try {
				while(b.next())
				{
					
						TravelRequestForm emp=new TravelRequestForm();
						emp.setId(b.getInt("id"));
						emp.setBilltype(b.getString("billtype"));
						emp.setTravelagentname(b.getString("travelagentname"));
						emp.setBillno(b.getString("billno"));
						emp.setBilldate(b.getString("billdate"));
						emp.setBillamount(b.getString("billamount"));
						emp.setFileName(b.getString("file_Name"));
						emp.setFileFullPath(b.getString("path"));
						emplist.add(emp);
				
					
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        if(emplist.size()>0)
	        	request.setAttribute("emplist", emplist);	
	        
	        
	        
	        ArrayList travIdList=new ArrayList();
			ArrayList travLabelList=new ArrayList();
			ResultSet rs11 = ad.selectQuery("select * from Travel_Master "); 
					try {
						while(rs11.next()) {
							travIdList.add(rs11.getString("trav_id"));
							travLabelList.add(rs11.getString("trav_name"));
						}
						rs11.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			help.setTravIdList(travIdList);
			help.setTravLabelList(travLabelList);
			
	        
		
		return mapping.findForward("billerList");
	}	
	
	
	public ActionForward billerListDelete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		TravelRequestForm help = (TravelRequestForm) form;
		String bid=request.getParameter("bid");
		String req_id="";
		String fileName = "";
		String getFilename = "select * from Bill_Upload_List where id='"+bid+"' ";
		ResultSet rsFileName = ad.selectQuery(getFilename);
		try {
			if (rsFileName.next()) {
				fileName = rsFileName.getString("file_name");
				req_id = rsFileName.getString("req_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "delete from Bill_Upload_List  where id="
				+ bid + " ";

		int c = ad.SqlExecuteUpdate(sql);
		if (c > 0) {
			
		if(!fileName.equalsIgnoreCase(""))
		{	
			help
					.setMessage("Document Details Deleted sucessfully");
			help.setMessage2("");
			InputStream in = ConnectionFactory.class
					.getClassLoader().getResourceAsStream(
							"db.properties");
			Properties props = new Properties();
			try {
				props.load(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String uploadFilePath = props
					.getProperty("file.uploadFilePath");
			System.out
					.println("required filepath="
							+ uploadFilePath
							+ "/EMicro Files/ESS/Travel Request/UploadFiles/bill/"+help.getTravelmode()+"");
			String filePath = uploadFilePath
					+ "/EMicro Files/ESS/Travel Request/UploadFiles/bill/"+help.getTravelmode()+"";

			File fileToCreate = new File(filePath, fileName);
			boolean test = fileToCreate.delete();
			System.out.println(test);

			// delete file in another path

			File fileToCreate1 = new File(
					"E:/EMicro Files/ESS/Travel Request/UploadFiles/bill/"+help.getTravelmode()+"",
					fileName);
			boolean test1 = fileToCreate1.delete();
		}
		}
		
		
		String his="select id,billtype,travelagentname,convert(nvarchar(10),billdate,103) as billdate ,"
		 		+ "billno, billamount,file_Name,path from Bill_Upload_List where reqNo='"+help.getRequestNumber()+"'"
		 		+ "union "
		 		+ "select 0,'Total','','' ,'',sum(  case when billtype='Credit Note' then -1*billamount else billamount end  ),'','' from Bill_Upload_List  where reqNo='"+help.getRequestNumber()+"' "
		 		+ "group by reqNo order by 1 desc " ;
	        ResultSet b=ad.selectQuery(his);
	        ArrayList emplist = new ArrayList();
			try {
				while(b.next())
				{
					
						TravelRequestForm emp=new TravelRequestForm();
						emp.setId(b.getInt("id"));
						emp.setBilltype(b.getString("billtype"));
						emp.setTravelagentname(b.getString("travelagentname"));
						emp.setBillno(b.getString("billno"));
						emp.setBilldate(b.getString("billdate"));
						emp.setBillamount(b.getString("billamount"));
						emp.setFileName(b.getString("file_Name"));
						emp.setFileFullPath(b.getString("path"));
						emplist.add(emp);
				
					
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        if(emplist.size()>0)
	        	request.setAttribute("emplist", emplist);
	        

	        ArrayList travIdList=new ArrayList();
			ArrayList travLabelList=new ArrayList();
			ResultSet rs11 = ad.selectQuery("select * from Travel_Master "); 
					try {
						while(rs11.next()) {
							travIdList.add(rs11.getString("trav_id"));
							travLabelList.add(rs11.getString("trav_name"));
						}
						rs11.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					help.setTravIdList(travIdList);
					help.setTravLabelList(travLabelList);
		
		
		return mapping.findForward("billerList");
	}
	
	
	public ActionForward billerListUpload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//Created by ArunPrasad .Date:15-11-2016
		
		HttpSession session=request.getSession();
		TravelRequestForm help = (TravelRequestForm) form;
     
		FormFile documentFile = help.getDocumentFile();
		String documentName = documentFile.getFileName();
		int reqNo = help.getRequestNumber();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
			
		
		String billdate="";

		if(help.getBilldate().contains("/"))
		{	
	    String depart[]=help.getBilldate().split("/");
	    billdate=depart[2]+"-"+depart[1]+"-"+depart[0];
		}
		
		help.setBilldate(billdate);
		
		  String hisa="select * from Bill_Upload_List where reqNo ='"+help.getRequestNumber()+"' and billno='"+help.getBillno()+"' and billdate ='"+help.getBilldate()+"' and  travelagentname='"+help.getTravelagentname()+"' " ;
		  ResultSet rsaa=ad.selectQuery(hisa);
		 try {
			if(rsaa.next())
			 {
				 
				help.setMessage("Duplicate Bill No");
				//traveller details
				String his="select id,billtype,travelagentname,convert(nvarchar(10),billdate,103) as billdate ,"
				 		+ "billno, billamount,file_Name,path from Bill_Upload_List where reqNo='"+reqNo+"'"
				 		+ "union "
				 		+ "select 0,'Total','','' ,'',sum(  case when billtype='Credit Note' then -1*billamount else billamount end  ),'','' from Bill_Upload_List  where reqNo='"+reqNo+"' "
				 		+ "group by reqNo order by 1 desc " ;
			        ResultSet b=ad.selectQuery(his);
			        ArrayList emplist = new ArrayList();
					try {
						while(b.next())
						{
							
								TravelRequestForm emp=new TravelRequestForm();
								emp.setId(b.getInt("id"));
								emp.setBilltype(b.getString("billtype"));
								emp.setTravelagentname(b.getString("travelagentname"));
								emp.setBillno(b.getString("billno"));
								emp.setBilldate(b.getString("billdate"));
								emp.setBillamount(b.getString("billamount"));
								emp.setFileName(b.getString("file_Name"));
								emp.setFileFullPath(b.getString("path"));
								emplist.add(emp);
						
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    
			    if(emplist.size()>0)
			    	request.setAttribute("emplist", emplist);
				
				return mapping.findForward("billerList");	
			 }
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
		
		
		
	
		
		
		String filePath = "";

		String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
		int filesize = documentFile.getFileSize();
		if(!documentName.equalsIgnoreCase(""))
		{	
		
		if ((ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")
				|| ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("jpg")
				|| ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("xls") || ext
					.equalsIgnoreCase("txt")) && (filesize < 1048576)) {
			try {
				byte[] size = documentFile.getFileData();
				if (!documentName.equalsIgnoreCase("")) {
					int length = documentName.length();
					int dot = documentName.lastIndexOf(".");
					String fileName = documentFile.getFileName();
					String extension = documentName.substring(dot, length);
					filePath = getServlet().getServletContext().getRealPath(
							"jsp/EMicro Files/ESS/Travel Request/UploadFiles/bill");
					InputStream in = ConnectionFactory.class.getClassLoader()
							.getResourceAsStream("db.properties");
					Properties props = new Properties();
					props.load(in);
					in.close();
					String uploadFilePath = props
							.getProperty("file.uploadFilePath");
					System.out.println("required filepath=" + uploadFilePath
							+ "/EMicro Files/ESS/Travel Request/UploadFiles/bill/"+reqNo+"");
					filePath = uploadFilePath
							+ "/EMicro Files/ESS/Travel Request/UploadFiles/bill/"+reqNo+"";
					File destinationDir = new File(filePath);
					if (!destinationDir.exists()) {
						destinationDir.mkdirs();
					}
					if (!fileName.equals("")) {
						File fileToCreate = new File(filePath, fileName);
						if (!fileToCreate.exists()) {
							FileOutputStream fileOutStream = new FileOutputStream(
									fileToCreate);
							fileOutStream.write(documentFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
						}
					}
					try {
						String filePath1 = "E:/EMicro Files/ESS/Travel Request/UploadFiles/bill/"+reqNo+"";
						byte[] fileData1 = documentFile.getFileData();
						InputStream is = new ByteArrayInputStream(fileData1);
						// boolean status=saveDataInJRS(is,fileName);
						// System.out.println("File status="+status);

						File destinationDir1 = new File(filePath1);
						if (!destinationDir1.exists()) {
							destinationDir1.mkdirs();
						}
						if (!fileName.equals("")) {
							File fileToCreate1 = new File(filePath1, fileName);
							if (!fileToCreate1.exists()) {
								FileOutputStream fileOutStream1 = new FileOutputStream(
										fileToCreate1);
								fileOutStream1
										.write(documentFile.getFileData());
								fileOutStream1.flush();
								fileOutStream1.close();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException fe) {
				fe.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
			
			

			String contentType = documentFile.getContentType();
			String fileName = documentFile.getFileName();
			help.setFileName(documentFile.getFileName());
			try {

				String sql9 = "select count(*) from Bill_Upload_List  where  file_name='"
						+ documentFile.getFileName() + "' and reqNo='"+reqNo+"'";
				ResultSet rs15 = ad.selectQuery(sql9);
				int fileCount = 0;
				while (rs15.next()) {
					fileCount = Integer.parseInt(rs15.getString(1));
				}
				if (fileCount > 0) {
					
					help.setMessage("Document already uploaded..please choose another file");
					help.setMessage2("");
				} else {
					
				String path = "/EMicro Files/ESS/Travel Request/UploadFiles/bill/"+reqNo+""+"/"+documentFile.getFileName();	
					
				
				String sql = "insert into Bill_Upload_List(reqNo,travelagentname,billno,billdate,billamount,bookedby,bookeddate,created_date,created_by,"
							+ " billtype,path,file_name) "
							+ "values('"
							+ reqNo
							+ "','"+help.getTravelagentname()+""
							+ "','"+help.getBillno()+""
							+ "','"+help.getBilldate()+""
							+ "','"+help.getBillamount()+""
							+ "','"+user.getEmployeeNo()+"',getdate(),getdate()"
							+ ",'"+user.getEmployeeNo()+""
							+ "','"+help.getBilltype()+""
							+ "','"+path+""
							+ "','"+help.getFileName()+""
							+" ')";
					int a = ad.SqlExecuteUpdate(sql);
					if (a > 0) {
						help
								.setMessage("Documents Uploaded Successfully");
						help.setMessage2("");
					}
				}
			

			} catch (Exception e) {
				e.printStackTrace();

			}

		} else {
			help
					.setMessage2("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
			help.setMessage("");
		}
	}
		else
		{
			help.setFileFullPath("");
			help.setFileName("");
			
			String sql = "insert into Bill_Upload_List(reqNo,travelagentname,billno,billdate,billamount,bookedby,bookeddate,created_date,created_by,"
					+ " billtype,path,file_name) "
					+ "values('"
					+ reqNo
					+ "','"+help.getTravelagentname()+""
					+ "','"+help.getBillno()+""
					+ "','"+help.getBilldate()+""
					+ "','"+help.getBillamount()+""
					+ "','"+user.getEmployeeNo()+"',getdate(),getdate()"
					+ ",'"+user.getEmployeeNo()+""
					+ "','"+help.getBilltype()+""
					+ "','"+help.getFileFullPath()+""
					+ "','"+help.getFileName()+""
					
					+" ')";
			int a = ad.SqlExecuteUpdate(sql);
			if (a > 0) {
				help
						.setMessage("Documents Uploaded Successfully");
				help.setMessage2("");
			}
		}
		//traveller details
		
		String his="select id,billtype,travelagentname,convert(nvarchar(10),billdate,103) as billdate ,"
		 		+ "billno, billamount,file_Name,path from Bill_Upload_List where reqNo='"+reqNo+"'"
		 		+ "union "
		 		+ "select 0,'Total','','' ,'',sum(  case when billtype='Credit Note' then -1*billamount else billamount end  ),'','' from Bill_Upload_List  where reqNo='"+reqNo+"' "
		 		+ "group by reqNo order by 1 desc " ;
        ResultSet b=ad.selectQuery(his);
        ArrayList emplist = new ArrayList();
		try {
			while(b.next())
			{
				
					TravelRequestForm emp=new TravelRequestForm();
					emp.setId(b.getInt("id"));
					emp.setBilltype(b.getString("billtype"));
					emp.setTravelagentname(b.getString("travelagentname"));
					emp.setBillno(b.getString("billno"));
					emp.setBilldate(b.getString("billdate"));
					emp.setBillamount(b.getString("billamount"));
					emp.setFileName(b.getString("file_Name"));
					emp.setFileFullPath(b.getString("path"));
					emplist.add(emp);
			
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        if(emplist.size()>0)
        	request.setAttribute("emplist", emplist);	
        

        ArrayList travIdList=new ArrayList();
		ArrayList travLabelList=new ArrayList();
		ResultSet rs11 = ad.selectQuery("select * from Travel_Master "); 
				try {
					while(rs11.next()) {
						travIdList.add(rs11.getString("trav_id"));
						travLabelList.add(rs11.getString("trav_name"));
					}
					rs11.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				help.setTravIdList(travIdList);
				help.setTravLabelList(travLabelList);
		
		return mapping.findForward("billerList");	
	}

	//travell checking date
	public ActionForward traveldate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		
		TravelRequestForm help = (TravelRequestForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int i=1;
		
		String departdate=request.getParameter("dt");
		String returndate=request.getParameter("dt2");
		String pernr= request.getParameter("dt3");
		if(pernr==null)
		{
			pernr=user.getEmployeeNo();
		}
		
		
		if(departdate.contains("/"))
		{	
	    String depart[]=departdate.split("/");
		departdate=depart[2]+"-"+depart[1]+"-"+depart[0];
		}
		

		if(returndate.contains("/"))
		{
		String returnd[]=returndate.split("/");
		returndate=returnd[2]+"-"+returnd[1]+"-"+returnd[0];
		}

		String sa="select travel_desk.pernr from travel_desk where reqno is not null  and pernr='"+pernr+"' "
				+ " and ((Depart_On >='"+departdate+"' and Depart_On <='"+returndate+"') or (Return_On >='"+departdate+"' and Return_On <='"+returndate+"')) "
				+ " and Req_status in( 'Approved','Pending') "
				+ " union "
				+ " select Travel_Desk_Travellers.pernr from Travel_Desk_Travellers,Travel_desk where Travel_Desk_Travellers.req_id = Travel_Desk.id and  "
				+ " ((Travel_Desk_Travellers.DepartOn >='"+departdate+"' and Travel_Desk_Travellers.DepartOn <='"+returndate+"') or (ReturnOn >='"+departdate+"' and ReturnOn <='"+returndate+"')) "
				+ " and Travel_Desk_Travellers.pernr='"+pernr+"' and "
				+ " Travel_Desk.reqNo is not null and Travel_Desk.Req_status in( 'Approved','Pending') and Travel_Desk_Travellers.pernr!=0 ";
		ResultSet rssa=ad.selectQuery(sa);
		try {
			if(rssa.next())
			{
				help.setTravel_Days1("0");
			}
			else
			{
				help.setTravel_Days1("1");
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return mapping.findForward("traveldate");
	}
	

	//travell checking date
	public ActionForward submitofFinance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		
		

		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		TravelRequestForm help = (TravelRequestForm) form;
		
		String requestType = request.getParameter("reqType");
		String requestNumber= request.getParameter("requestNumber");
		
		int requestNo=0;
		
		/*int hel=Integer.parseInt(requestNumber);*/
		int reqNo=0;
		if(reqNo==0)
		{
		String getMaxNo = "select isnull(max(reqNo),0) from Travel_finance";
		ResultSet rst = ad.selectQuery(getMaxNo);
		try {
			while (rst.next()) {
				reqNo = rst.getInt(1) + 1;
			}
		help.setRequestNumber(reqNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		String a[] = request.getParameterValues("selectedRequestNo");
		String ACtualreq="";
		if(a!=null)
		{
	     
		if(a.length!=0)
		{
		StringBuffer reqdept = new StringBuffer();
		for (int i = 0; i < a.length; i++) {
			reqdept.append("'"+a[i]+"'" + ",");
		}
		  ACtualreq = reqdept.substring(0, reqdept.length() - 1)
				.toString();
		}
		}
		//updated by Ravi on 15/07/2017
		
/*		String checkUserreview="select * from travel_desk where reqNo="+requestNumber+"";
		String tripReview="";
		String tripComments="";
		 ResultSet check=ad.selectQuery(checkUserreview);
		 while(check.next())
		 {
			tripReview=check.getString("review_Trip"); 
			tripComments=check.getString("review_Rating");
		 }
		LinkedList alt =new LinkedList();
		if(tripReview==null||tripComments==null)
		{
			help.setMessage("User havent Reviewed Travel Request");
		
		
		}
		
		else
		{*/
		String upd="update Bill_Upload_List set finance_no='"+reqNo+"' "
				+ " ,finance_submitted_date=getdate() ,finance_status='Submitted' "
				+ " where id in ("+ACtualreq+")";
		int adp= ad.SqlExecuteUpdate(upd);
		
		if(reqNo!=0)
		{
			String cc ="Select distinct(t.ReqNo) from travel_desk t,bill_upload_list b where t.reqNo=b.reqNo and b.finance_no='"+reqNo+"'";
			ResultSet rss  = ad.selectQuery(cc);
					while(rss.next())
					{
						requestNo = rss.getInt("ReqNo");
					}
		}
		
		
		String ins="Insert into travel_finance(reqNo,req_billno,travelagentname,total_amount,Req_Status,Creation_BY,Creation_Date)"
				+ " values('"+reqNo+"','','"+help.getTravelagentname()+"','"+help.getTotal()+"','Submitted','"+user.getEmployeeNo()+"',getdate())";
		int inss = ad.SqlExecuteUpdate(ins);
	/*	
		}*/
		
		String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,Approved_date,Role) values (";
		tempinsert = tempinsert + "'"+requestNo+"','Travel','','','Submitted to Finance','','','','','','"+user.getEmployeeNo()+"',getdate(),'Travel Desk')";
		 int i11=ad.SqlExecuteUpdate(tempinsert);
		
		return mapping.findForward("MyFinanceList");
	}
	
	
	public ActionForward viewbillupload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		

		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		String reqNo=request.getParameter("reqNo");
		
		
		
		TravelRequestForm help = (TravelRequestForm) form;
		help.setRequestNumber(Integer.parseInt(reqNo));
		
		String his="select id,billtype,travelagentname,convert(nvarchar(10),billdate,103) as billdate ,"
		 		+ "billno, billamount,file_Name,path from Bill_Upload_List where finance_no='"+reqNo+"'"
		 		+ "union "
		 		+ "select 0,'Total','','' ,'',sum(  case when billtype='Credit Note' then -1*billamount else billamount end  ),'','' from Bill_Upload_List  where finance_no='"+reqNo+"' "
		 		+ "group by finance_no order by 1 desc " ;
        ResultSet b=ad.selectQuery(his);
        ArrayList emplist = new ArrayList();
		try {
			while(b.next())
			{
				
					TravelRequestForm emp=new TravelRequestForm();
					emp.setId(b.getInt("id"));
					emp.setBilltype(b.getString("billtype"));
					emp.setTravelagentname(b.getString("travelagentname"));
					emp.setBillno(b.getString("billno"));
					emp.setBilldate(b.getString("billdate"));
					emp.setBillamount(b.getString("billamount"));
					emp.setFileName(b.getString("file_Name"));
					emp.setFileFullPath(b.getString("path"));
					emplist.add(emp);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		request.setAttribute("emplist", emplist);
		
		
		return mapping.findForward("viewbillerList");
		}
	
	public ActionForward viewreceivebillupload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		

		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		String reqNo=request.getParameter("reqNo");
		
		
		
		TravelRequestForm help = (TravelRequestForm) form;
		help.setRequestNumber(Integer.parseInt(reqNo));
		
		
		String  a=" select * , convert(nvarchar(103),creation_date,103) as billdate from travel_finance"
				+ " where reqno='"+reqNo+"' ";
		
				
		ResultSet rsa=ad.selectQuery(a);
		try {
			while(rsa.next())
			{

				help.setRequestNumber(rsa.getInt("reqno"));
				help.setTravelagentname(rsa.getString("travelagentname"));
				help.setBilldate(rsa.getString("billdate"));
				help.setBillamount(rsa.getString("total_amount"));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		String his="select id,billtype,travelagentname,convert(nvarchar(10),billdate,103) as billdate ,"
		 		+ "billno, billamount,file_Name,path,reqno from Bill_Upload_List where finance_no='"+reqNo+"'"
		 		+ "union "
		 		+ "select 0,'Total','','' ,'',sum(  case when billtype='Credit Note' then -1*billamount else billamount end  ),'','','' from Bill_Upload_List  where finance_no='"+reqNo+"' "
		 		+ "group by finance_no order by 1 desc " ;
        ResultSet b=ad.selectQuery(his);
        ArrayList emplist = new ArrayList();
		try {
			while(b.next())
			{
				
					TravelRequestForm emp=new TravelRequestForm();
					emp.setId(b.getInt("id"));
					emp.setBilltype(b.getString("billtype"));
					emp.setRequestNumber(b.getInt("reqno"));
					emp.setTravelagentname(b.getString("travelagentname"));
					emp.setBillno(b.getString("billno"));
					emp.setBilldate(b.getString("billdate"));
					emp.setBillamount(b.getString("billamount"));
					emp.setFileName(b.getString("file_Name"));
					emp.setFileFullPath(b.getString("path"));
					emplist.add(emp);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		request.setAttribute("emplist", emplist);
		
		
		return mapping.findForward("viewrecievebillerList");
		}
	
	public ActionForward viewverifybillupload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		

		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		String reqNo=request.getParameter("reqNo");
		
		
		
		TravelRequestForm help = (TravelRequestForm) form;
		help.setRequestNumber(Integer.parseInt(reqNo));
		
		
		String  a=" select * , convert(nvarchar(103),creation_date,103) as billdate from travel_finance"
				+ " where reqno='"+reqNo+"' ";
		
				
		ResultSet rsa=ad.selectQuery(a);
		try {
			while(rsa.next())
			{

				help.setRequestNumber(rsa.getInt("reqno"));
				help.setTravelagentname(rsa.getString("travelagentname"));
				help.setBilldate(rsa.getString("billdate"));
				help.setBillamount(rsa.getString("total_amount"));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		String his="select id,billtype,travelagentname,convert(nvarchar(10),billdate,103) as billdate ,"
		 		+ "billno, billamount,file_Name,path,reqno from Bill_Upload_List where finance_no='"+reqNo+"'"
		 		+ "union "
		 		+ "select 0,'Total','','' ,'',sum(  case when billtype='Credit Note' then -1*billamount else billamount end  ),'','','' from Bill_Upload_List  where finance_no='"+reqNo+"' "
		 		+ "group by finance_no order by 1 desc " ;
        ResultSet b=ad.selectQuery(his);
        ArrayList emplist = new ArrayList();
		try {
			while(b.next())
			{
				
					TravelRequestForm emp=new TravelRequestForm();
					emp.setId(b.getInt("id"));
					emp.setBilltype(b.getString("billtype"));
					emp.setRequestNumber(b.getInt("reqno"));
					emp.setTravelagentname(b.getString("travelagentname"));
					emp.setBillno(b.getString("billno"));
					emp.setBilldate(b.getString("billdate"));
					emp.setBillamount(b.getString("billamount"));
					emp.setFileName(b.getString("file_Name"));
					emp.setFileFullPath(b.getString("path"));
					emplist.add(emp);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		request.setAttribute("emplist", emplist);
		
		
		return mapping.findForward("viewverifybillupload");
		}
	
	public ActionForward updatetravelReview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		TravelRequestForm help=(TravelRequestForm)form;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String requestno=request.getParameter("requstNo");
		
		
		help.setMessage2("Review Updated  successfully");
		String updateSql= "update Travel_Desk set review_Trip='"+help.getReview_Trip()+"' ,review_Rating='"+help.getReview_Rating()+"',t_confirm_date=getdate() "
				+ " where reqno='"+help.getRequestNumber()+"'";
		int ii=ad.SqlExecuteUpdate(updateSql);
		
		String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,rejected_date,Role,submitted_date) values (";
		tempinsert = tempinsert + "'"+help.getRequestNumber()+"','Travel','','"+user.getEmployeeNo()+"','Travel Review','','','','"+user.getEmployeeNo()+"','"+help.getReview_Trip()+"Rating:"+help.getReview_Rating()+"','','','User',getdate())";
		 int i11=ad.SqlExecuteUpdate(tempinsert);
		
		
		
		
		
		return displayMyrequestList(mapping, form, request, response);
		
		
	}
	
	
	public ActionForward searchRecieveFinanceDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		//Created by Arun .Date:15-11-2016
		TravelRequestForm help = (TravelRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		
		
		  ArrayList travIdList=new ArrayList();
			ArrayList travLabelList=new ArrayList();
			ResultSet rs11 = ad.selectQuery("select * from Travel_Master "); 
					try {
						while(rs11.next()) {
							travIdList.add(rs11.getString("trav_id"));
							travLabelList.add(rs11.getString("trav_name"));
						}
						rs11.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					help.setTravIdList(travIdList);
					help.setTravLabelList(travLabelList);
					
				String fromdate="";
				String todate="";
					
				String from[]=help.getFromdate().split("/");
				fromdate=from[2]+"-"+from[1]+"-"+from[0];
					    
				String to[]=help.getTodate().split("/");
				todate=to[2]+"-"+to[1]+"-"+to[0];	
			
		
		
			
		
		if(help.getReqStatus().equalsIgnoreCase("Recieved to Finance"))
		{
			
			
			ArrayList finsublist = new ArrayList();		
			String  a=" select * , convert(nvarchar(103),creation_date,103) as billdate from travel_finance"
					+ " where  convert(date,creation_date) >='"+fromdate+"' and convert(date,creation_date)<='"+todate+"'"
					+ " and  isnull(Reviewed_date,'')='' ";
			
			if(!help.getTravelagentname().equalsIgnoreCase(""))
			{
				a=a+" and travelagentname= '"+help.getTravelagentname()+"' ";
			}
					
			ResultSet rsa=ad.selectQuery(a);
			try {
				while(rsa.next())
				{

					TravelRequestForm emp=new TravelRequestForm();
					emp.setId(rsa.getInt("id"));
					emp.setRequestNumber(rsa.getInt("reqno"));
					emp.setTravelagentname(rsa.getString("travelagentname"));
					emp.setBilldate(rsa.getString("billdate"));
					emp.setBillamount(rsa.getString("total_amount"));
					finsublist.add(emp);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("finsublist", finsublist);
			
			
		}
		
		if(help.getReqStatus().equalsIgnoreCase("Verified to Finance"))
		{
			
			
			ArrayList finsublist = new ArrayList();		
			String  a=" select * , convert(nvarchar(103),creation_date,103) as billdate from travel_finance"
					+ " where  convert(date,reviewed_date) >='"+fromdate+"' and convert(date,reviewed_date)<='"+todate+"'"
					+ "  and reviewed_status='Received' and isnull(Verified_date,'')='' ";
			
			if(!help.getTravelagentname().equalsIgnoreCase(""))
			{
				a=a+" and travelagentname= '"+help.getTravelagentname()+"' ";
			}
					
			ResultSet rsa=ad.selectQuery(a);
			try {
				while(rsa.next())
				{

					TravelRequestForm emp=new TravelRequestForm();
					emp.setId(rsa.getInt("id"));
					emp.setRequestNumber(rsa.getInt("reqno"));
					emp.setTravelagentname(rsa.getString("travelagentname"));
					emp.setBilldate(rsa.getString("billdate"));
					emp.setBillamount(rsa.getString("total_amount"));
					finsublist.add(emp);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("finsublist", finsublist);
			
			
		}
			
		
		return mapping.findForward("MyFinanceReceieveList");
	}
	
	
	public ActionForward updateFinanceDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		TravelRequestForm help=(TravelRequestForm)form;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String status=request.getParameter("status");
		int requestNo=0;
		String cc ="Select distinct(t.ReqNo) from travel_desk t,bill_upload_list b where t.reqNo=b.reqNo and b.finance_no='"+help.getRequestNumber()+"'";
		ResultSet rss  = ad.selectQuery(cc);
				try {
					while(rss.next())
					{
						requestNo = rss.getInt("ReqNo");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		String up="update travel_finance set Reviewed_status='"+status+"' ,Reviewed_date=getdate() ,Reviewed_by='"+user.getEmployeeNo()+"',"
				+ " Reviewed_Comments='"+help.getComments()+"' "
				+ "    where reqNo='"+help.getRequestNumber()+"'";
		int i=ad.SqlExecuteUpdate(up);
		
		String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,Approved_date,Role) values (";
		tempinsert = tempinsert + "'"+requestNo+"','Travel','','','Received By finance','','','','','"+help.getComments()+"','"+user.getEmployeeNo()+"',getdate(),'Travel Desk')";
		 int i11=ad.SqlExecuteUpdate(tempinsert);
		if(status.equalsIgnoreCase("Rejected"))
		{	
		String ins="insert into Bill_Upload_List_history"
				+ " select id,req_id,reqNo,travelagentname,billno,billdate,billamount,bookedby,bookeddate,created_date,created_by,billtype,path,file_name,prefix,finance_no,finance_submitted_date,finance_status"
				+ " from Bill_Upload_List where finance_no='"+help.getRequestNumber()+"' ";
		int k = ad.SqlExecuteUpdate(ins);
		
		String up1="update bill_upload_list set finance_no=null,finance_submitted_date=null,finance_status=null where finance_no='"+help.getRequestNumber()+"'";
		int i2=ad.SqlExecuteUpdate(up1);
		String tempinsert1="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,Approved_date,Role) values (";
		tempinsert1 = tempinsert1 + "'"+requestNo+"','Travel','','','Rejected-Received to Finance','','','','','"+help.getComments()+"','"+user.getEmployeeNo()+"',getdate(),'Travel Desk')";
		 int i111=ad.SqlExecuteUpdate(tempinsert1);
		
		}
		
		/**/	
		/*
		help.setMessage2("Request has been cancelled successfully");
		String updateSql= "update Travel_Desk set Req_status='Rejected' ,Approved_date=getdate()    where reqno='"+requestno+"'";
		int ii=ad.SqlExecuteUpdate(updateSql);*/
		
		
		return mapping.findForward("MyFinanceReceieveList");
		
		
	}
	
	
	
	public ActionForward updateFinanceveirfyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		TravelRequestForm help=(TravelRequestForm)form;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String status=request.getParameter("status");
		
		int requestNo=0;
		String cc ="Select distinct(t.ReqNo) from travel_desk t,bill_upload_list b where t.reqNo=b.reqNo and b.finance_no='"+help.getRequestNumber()+"'";
		ResultSet rss  = ad.selectQuery(cc);
				try {
					while(rss.next())
					{
						requestNo = rss.getInt("ReqNo");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		String up="update travel_finance set Verified_status='"+status+"' ,Verified_date=getdate() ,Verified_by='"+user.getEmployeeNo()+"',"
				+ " Verified_Comments='"+help.getComments()+"' "
				+ "    where reqNo='"+help.getRequestNumber()+"'";
		int i=ad.SqlExecuteUpdate(up);
		
		
		String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,Approved_date,Role) values (";
		tempinsert = tempinsert + "'"+requestNo+"','Travel','','','Verified By finance','','','','','"+help.getComments()+"','"+user.getEmployeeNo()+"',getdate(),'Travel Desk')";
		 int i11=ad.SqlExecuteUpdate(tempinsert);
		
		if(status.equalsIgnoreCase("Rejected"))
		{	
		String ins="insert into Bill_Upload_List_history"
				+ " select id,req_id,reqNo,travelagentname,billno,billdate,billamount,bookedby,bookeddate,created_date,created_by,billtype,path,file_name,prefix,finance_no,finance_submitted_date,finance_status"
				+ " from Bill_Upload_List where finance_no='"+help.getRequestNumber()+"' ";
		int k = ad.SqlExecuteUpdate(ins);
		
		String up1="update bill_upload_list set finance_no=null,finance_submitted_date=null,finance_status=null where finance_no='"+help.getRequestNumber()+"'";
		int i2=ad.SqlExecuteUpdate(up1);
		
		String tempinsert1="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,Approved_date,Role) values (";
		tempinsert1 = tempinsert1 + "'"+requestNo+"','Travel','','','Rejected-Verified to Finance','','','','','"+help.getComments()+"','"+user.getEmployeeNo()+"',getdate(),'Travel Desk')";
		 int i111=ad.SqlExecuteUpdate(tempinsert1);
		}
		

		
		
		return mapping.findForward("MyFinanceReceieveList");
		
		
	}
	
	
	//travell checking date
	public ActionForward travelmode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		
		TravelRequestForm help = (TravelRequestForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int i=1;
		
		String type=request.getParameter("type");
		ArrayList travelList=new ArrayList();
		ArrayList travelLabelList=new ArrayList();
		String xyz = "select * from travel_mode";
		ResultSet rs212 = ad.selectQuery(xyz);
		TravelRequestForm issue1=new TravelRequestForm();	
		try{
			while(rs212.next()) {
				
			
				travelList.add(rs212.getString("mode"));
				travelLabelList.add(rs212.getString("mode"));
			}
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		issue1.setTraveltypeList(travelList);
		issue1.setTraveltypeLabelList(travelLabelList);
		ArrayList travelist = new ArrayList();
		ResultSet rs113 = ad.selectQuery("select * from travel_mode where type='"+type+"'"); 
				try {
					while(rs113.next()) {
						TravelRequestForm issue=new TravelRequestForm();	
						issue.setTravelmode(rs113.getString("mode"));
						travelist.add(issue);
					}
					rs113.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			request.setAttribute("travelist", travelist);	

		
		return mapping.findForward("travelmode");
	}
	
	public ActionForward travelDesk(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		
		TravelRequestForm help = (TravelRequestForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int i=1;
		
		String type=request.getParameter("type");
		
		
		ArrayList travelist = new ArrayList();
		ArrayList travelLabelList = new ArrayList();
		
		String q = "Select t.ApproverId,e.EMP_FULLNAME from Travel_desk_Approvers t,emp_official_info e where t.ApproverId=e.PERNR and  t.Travel_Type='"+type+"' and t.locid='"+user.getPlantId()+"' and t.Priority=1";
		ResultSet rs113 = ad.selectQuery(q); 
		
		
		try {
					while(rs113.next()) {
						TravelRequestForm issue=new TravelRequestForm();
						
						issue.setTravel_desk_type(rs113.getString("ApproverId"));
						issue.setTraveldeskname(rs113.getString("EMP_FULLNAME"));
						
						
						/*travelist.add(issue);
						travelLabelList.add(rs113.getString("EMP_FULLNAME"));*/
						travelist.add(issue);
					
					}
					
					rs113.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*issue.setTravelist(travelist);
				issue.setTravelLabelList(travelLabelList);*/
	request.setAttribute("travelist", travelist);
	
			

		
		return mapping.findForward("travelDesk");
	}
	
	public ActionForward travelDesk2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		
		
		
		TravelRequestForm help = (TravelRequestForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int i=1;
		
		String type=request.getParameter("type");
		
		
		ArrayList travelist = new ArrayList();
		ArrayList travelLabelList = new ArrayList();
		
		String q = "Select t.ApproverId,e.EMP_FULLNAME from Travel_desk_Approvers t,emp_official_info e where t.ApproverId=e.PERNR and  t.Travel_Type='"+type+"' and t.locid='"+user.getPlantId()+"' and t.Priority=1";
		ResultSet rs113 = ad.selectQuery(q); 
		
		
		try {
					while(rs113.next()) {
						TravelRequestForm issue=new TravelRequestForm();
						
						issue.setTravel_desk_type1(rs113.getString("ApproverId"));
						issue.setTraveldeskname1(rs113.getString("EMP_FULLNAME"));
						
						
						/*travelist.add(issue);
						travelLabelList.add(rs113.getString("EMP_FULLNAME"));*/
						travelist.add(issue);
					
					}
					
					rs113.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*issue.setTravelist(travelist);
				issue.setTravelLabelList(travelLabelList);*/
	request.setAttribute("travelist", travelist);
	
			

		
		return mapping.findForward("travelAjax2");
	
		
	}
	
	public ActionForward serviceclass(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		
		TravelRequestForm help = (TravelRequestForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int i=1;
		
		String type=request.getParameter("type");
		
		
		ArrayList travelist = new ArrayList();
		ResultSet rs113 = ad.selectQuery("Select *  from travel_Service_master  where type='"+type+"'"); 
				try {
					while(rs113.next()) {
						TravelRequestForm issue=new TravelRequestForm();	
						issue.setService_class(rs113.getString("id"));
						travelist.add(issue);
					}
					rs113.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			request.setAttribute("travelist", travelist);	

		
		return mapping.findForward("serviceclass");
	}
		
	
		
	
	

public ActionForward approveMailRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
	
	String userID=request.getParameter("userID");
	String reqId=request.getParameter("reqId");
	String status=request.getParameter("status");
	

		TravelRequestForm approvalsForm = (TravelRequestForm) form;
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
			String dateNow = ft.format(dNow);
			String empID="";
			
   boolean leaveStatus=true;
   
	int year=0;
   
   String checkRecordStatus="select * from All_Request where Req_Type='Travel' and Req_Id='"+reqId+"' and (Pending_Approver='"+userID+"' or Approved_Persons='"+userID+"')";
	ResultSet rsLvStatus=ad.selectQuery(checkRecordStatus);
	try{
	while(rsLvStatus.next())
	{
		String lvStatus=rsLvStatus.getString("Req_Status");
		if(lvStatus.equalsIgnoreCase("Approved"))
		{
			status="Approve";
			leaveStatus=false;
			approvalsForm.setMessage2("");
	   		approvalsForm.setMessage("This request has already been approved.");
		}
		if(lvStatus.equalsIgnoreCase("Rejected"))
		{
			status="Reject";
			leaveStatus=false;
			approvalsForm.setMessage2("");
	   		approvalsForm.setMessage("This request has already been rejected.");
		}
		if(lvStatus.equalsIgnoreCase("Self Cancelled"))
		{
			status="Self Cancelled";
			leaveStatus=false;
			approvalsForm.setMessage2("");
	   		approvalsForm.setMessage("This request has already been self cancelld.");
		}
		
		if(lvStatus.equalsIgnoreCase("Rescheduled"))
		{
			status="Self Cancelled";
			leaveStatus=false;
			approvalsForm.setMessage2("");
	   		approvalsForm.setMessage("This request has already been self cancelld.");
		}
	}
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	String rqDate="";
	String rqName="";
		   String checkRecord1="select top 1 * from All_Request where Req_Type='Travel' and Req_Id='"+reqId+"'";
		   	ResultSet rsChcek11=ad.selectQuery(checkRecord1);
		   	try {
				while(rsChcek11.next())
				{
					rqDate=rsChcek11.getString("Req_Date");
							rqName=rsChcek11.getString("Requester_Name");
							empID=rsChcek11.getString("Requester_Name");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		   	
		   	
	if(leaveStatus==true){		
   	if(status.equalsIgnoreCase("Approve"))
   	{
   		
   		

   		int recordCount=0;
   		try{
   	String checkRecord="select count(*) from All_Request where Req_Type='Travel' and Req_Id='"+reqId+"' and Req_Status='Approved' and Approved_Persons='"+userID+"'";
   	ResultSet rsChcek=ad.selectQuery(checkRecord);
   	while(rsChcek.next())
   	{
   		recordCount=rsChcek.getInt(1);
   		approvalsForm.setMessage("");
   		if(recordCount>0)
   		approvalsForm.setMessage2("This request has already been approved.");
   	}
   	if(recordCount!=1)
   	{
	   	checkRecord="select count(*) from All_Request where Req_Type='Travel' and Req_Id='"+reqId+"' and Req_Status='Self Cancelled'";
	   	ResultSet rsChcek1=ad.selectQuery(checkRecord);
	   	while(rsChcek1.next())
	   	{
	   		recordCount=rsChcek1.getInt(1);
	   		approvalsForm.setMessage2("");
	   		if(recordCount>0)
	   		approvalsForm.setMessage("This request has already been self cancelled.");
	   		status="Self Cancelled";
	   	}
   	}
   	if(recordCount==0)
   	{
   		float noOfDays=0;
   		boolean existStatus=false;
   		String leaveType="";
   		String submit_date="";
   		String stdate="";
   		
   		
		int curyear = Calendar.getInstance().get(Calendar.YEAR);
	
		int priorityCout=0;
		int totalApproverCount=0;
		String dataFlowType=""; 
		String checkESSApproverList="select count(*) from ESS_Approvers where employeeNumber='"+rqName+"' and essType='leave'";
		ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
		while(rsCheckInEss.next())
		{
			int number=rsCheckInEss.getInt(1);
			totalApproverCount=rsCheckInEss.getInt(1);
			if(number==0)
			{
				existStatus=false;
			}
			else
			{
				existStatus=true;
			}
		}
	if(existStatus==true){
		String getDataFlowType="select * from ESS_Approvers where employeeNumber='"+rqName+"' and essType='leave' ";
		ResultSet rsDataFlowType=ad.selectQuery(getDataFlowType);
		while(rsDataFlowType.next())
		{
			dataFlowType=rsDataFlowType.getString("reqType");
		}
		if(dataFlowType.equalsIgnoreCase("sequential"))
		{
			int approverPriority=0;
			String getApprPriority="select Priority from ESS_Approvers where employeeNumber='"+rqName+"' and ApproverId='"+userID+"' and essType='leave'";
			ResultSet rsApprPriority=ad.selectQuery(getApprPriority);
			while(rsApprPriority.next())
			{
				approverPriority=rsApprPriority.getInt("Priority");
			}
			if(totalApproverCount==approverPriority)
			{
				//last approver
				
				existStatus=false;
				
				//check cancelled Record
                int cancelledCount=0;
                String getCheckCancelled="select COUNT(*) from All_Request where Req_Id='"+reqId+"' and Req_Type='Travel' and Req_Status='Cancelled'";
                ResultSet rsCheckCancel=ad.selectQuery(getCheckCancelled);
                while(rsCheckCancel.next())
                {
                	cancelledCount=rsCheckCancel.getInt(1);
                	if(cancelledCount>0)
                	{
                		existStatus=true;
                		 String updateSql= "update All_Request set Approved_Persons='"+userID+"', Last_approver='"+userID+"',Pending_approver='',Comments='Status Change From mail'," +
                         "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+userID+"' and Req_Type='Travel'";
						 int upd=ad.SqlExecuteUpdate(updateSql);
							
							String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
							tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Approved','','','','"+rqName+"','Status Change From mail','"+userID+"','"+dateNow+"','Approver')";
							 int i11=ad.SqlExecuteUpdate(tempinsert);
						 
						 if(upd>0)
							 {
								request.setAttribute("statusMessage2", "statusMessage2");
							approvalsForm.setMessage2("This Request already has been Cancelled");
							 }
                	}
                }
			}else{
				
			String nextApprover="";
			String getNextApprDetails="select ApproverId from ESS_Approvers where employeeNumber='"+rqName+"' and Priority='"+(approverPriority+1)+"' and essType='leave'";
            ResultSet rsNextApprDetails=ad.selectQuery(getNextApprDetails);
            while(rsNextApprDetails.next())
            {
            	nextApprover=rsNextApprDetails.getString("ApproverId");
            }
          //check cancelled Record
            int cancelledCount=0;
            String getCheckCancelled="select COUNT(*) from All_Request where Req_Id='"+reqId+"' and Req_Type='Travel' and Req_Status='Cancelled'";
            ResultSet rsCheckCancel=ad.selectQuery(getCheckCancelled);
            while(rsCheckCancel.next())
            {
            	cancelledCount=rsCheckCancel.getInt(1);
            	if(cancelledCount>0)
            	{
            		existStatus=true;
            		 String updateSql= "update All_Request set Approved_Persons='"+userID+"', Last_approver='"+userID+"',Pending_approver='',Comments='Status Change From mail'," +
                     "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+userID+"' and Req_Type='Travel'";
					 int upd=ad.SqlExecuteUpdate(updateSql);
					 
					 String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
						tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Approved','','','','"+rqName+"','Status Change From mail','"+userID+"','"+dateNow+"','Approver')";
						 int i11=ad.SqlExecuteUpdate(tempinsert);
						 
						 
							 if(upd>0)
							 {
								request.setAttribute("statusMessage2", "statusMessage2");
						approvalsForm.setMessage2("This Request already has been Cancelled");
							 }
            	}
            }
            if(cancelledCount==0){
            String updateSql= "update All_Request set Approved_Persons='"+userID+"', Last_approver='"+userID+"',Pending_approver='',Comments='Status Change From mail'," +
            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+userID+"' and Req_Type='Travel'";
			 int upd=ad.SqlExecuteUpdate(updateSql);
			 approvalsForm.setMessage2("Request has been approved.");
			 String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
				tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Approved','','','','"+rqName+"','Status Change From mail','"+userID+"','"+dateNow+"','Approver')";
				 int i11=ad.SqlExecuteUpdate(tempinsert);
				 if(upd>0)
				 {
					String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
					saveRecReq = saveRecReq + "'"+reqId+"','Travel','"+empID+"','"+submit_date+"','Pending','','"+nextApprover+"','No','"+empID+"','')";
					 int i1=ad.SqlExecuteUpdate(saveRecReq);
					 
					 
					 ad.connClose();
					 approvalsForm.setMessage("");
					 approvalsForm.setMessage2("");
					 approvalsForm.setMessage2("Request has been approved.");
						status="Approve";
					 EMailer email = new EMailer();
					 int j=email.sendMailToNextSequentialApprover(request, "", reqId, "Travel Desk",nextApprover,rqName);
					 
				 }
		  }
			}
		
	}
		if(dataFlowType.equalsIgnoreCase("parallel"))
		{
			//check total Approve count
			
			int totalApproveCount=0;
			String getTotApprCount="select count(*) from All_Request where Req_Id='"+reqId+"' and Requester_Name='"+empID+"' and Req_Status='Approved' and Req_Type='Travel'";
			ResultSet rsTotApprCount=ad.selectQuery(getTotApprCount);
			while(rsTotApprCount.next())
			{
				totalApproveCount=rsTotApprCount.getInt(1);
			}
			if(totalApproveCount==0)
			{
				 String updateSql11= "update All_Request set Approved_Persons='"+userID+"', Last_approver='"+userID+"',Pending_approver='',Comments='Status Change From mail'," +
		            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved',Actual_Approver='"+userID+"',Actual_Approver_status='Approved',Actual_Approved_Date='"+dateNow+"',Actual_Approved_Comments='Status Change From mail' where Req_Id='"+reqId+"' and Pending_approver='"+userID+"' and Req_Type='Travel'";
						 int upd1=ad.SqlExecuteUpdate(updateSql11);
						 status="Approve";
						 approvalsForm.setMessage2("Request has been approved.");
						 String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
							tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Approved','','','','"+rqName+"','Status Change From mail','"+userID+"','"+dateNow+"','Approver')";
							 int i11=ad.SqlExecuteUpdate(tempinsert);
						 
						 
						 String frstapp="";
							String par1app="";
							String par2app="";
							
							//Approvers
							String getNextApprDetails="select * from ESS_Approvers where employeeNumber='"+empID+"'  and essType='leave'";
                         ResultSet rsNextApprDetails=ad.selectQuery(getNextApprDetails);
                         while(rsNextApprDetails.next())
                         {
                         	frstapp=rsNextApprDetails.getString("ApproverId");
                         	par1app=rsNextApprDetails.getString("Parallel_Approver1");
                         	par2app=rsNextApprDetails.getString("Parallel_Approver2");
                         }
                         
                         if(!frstapp.equalsIgnoreCase(""))
							{
								String updateSql1= "update All_Request set Approved_Persons='"+frstapp+"', Last_approver='"+frstapp+"',Pending_approver='',Comments='Status Change From mail'," +
		                                "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved',Actual_Approver='"+userID+"',Actual_Approver_status='Approved',Actual_Approved_Date='"+dateNow+"',Actual_Approved_Comments='Status Change From mail' where Req_Id='"+reqId+"' and Pending_Approver='"+frstapp+"' and Req_Type='Travel'";
		   							 int upd11=ad.SqlExecuteUpdate(updateSql1);
		   							 
		   							 
		   							 tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
									tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Approved','','','','"+rqName+"','Status Change From mail','"+frstapp+"','"+dateNow+"','Approver')";
									  i11=ad.SqlExecuteUpdate(tempinsert);
							}
                         
                         if(par1app==null)
							{
								par1app="";
							}
							
							if(par2app==null)
							{
								par2app="";
							}
                         
                         if(!par1app.equalsIgnoreCase(""))
							{
								String updateSql1= "update All_Request set Approved_Persons='"+par1app+"', Last_approver='"+par1app+"',Pending_approver='',Comments='Status Change From mail'," +
		                                "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved',Actual_Approver='"+userID+"',Actual_Approver_status='Approved',Actual_Approved_Date='"+dateNow+"',Actual_Approved_Comments='Status Change From mail' where Req_Id='"+reqId+"' and Pending_Approver='"+par1app+"' and Req_Type='Travel'";
		   							 int upd11=ad.SqlExecuteUpdate(updateSql1);
		   							
		   							 
		   							 tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
										tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Approved','','','','"+rqName+"','Status Change From mail','"+par1app+"','"+dateNow+"','Approver')";
										  i11=ad.SqlExecuteUpdate(tempinsert);
							}
							
							if(!par2app.equalsIgnoreCase(""))
							{
								String updateSql1= "update All_Request set Approved_Persons='"+par2app+"', Last_approver='"+par2app+"',Pending_approver='',Comments='Status Change From mail'," +
		                                "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved',Actual_Approver='"+userID+"',Actual_Approver_status='Approved',Actual_Approved_Date='"+dateNow+"',Actual_Approved_Comments='Status Change From mail' where Req_Id='"+reqId+"' and Pending_Approver='"+par2app+"' and Req_Type='Travel'";
		   							 int upd11=ad.SqlExecuteUpdate(updateSql1);
		   							 
		   							 tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
										tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Approved','','','','"+rqName+"','Status Change From mail','"+par2app+"','"+dateNow+"','Approver')";
										  i11=ad.SqlExecuteUpdate(tempinsert);
							}
						 
						 
							existStatus=false;
				//check reject count 
				int totalRejectCount=0;
				String getTotRejectCount="select count(*) from All_Request where Req_Id='"+reqId+"' and Requester_Name='"+empID+"' and Req_Status='Rejected' and Req_Type='Travel'";
				ResultSet rsTotRejCount=ad.selectQuery(getTotRejectCount);
				while(rsTotRejCount.next())
				{
					totalRejectCount=rsTotRejCount.getInt(1);
				}
				if(totalRejectCount==0)
				{
				
				 String updateSql= "update All_Request set Approved_Persons='"+userID+"', Last_approver='"+userID+"',Pending_approver='',Comments='Status Change From mail'," +
                   "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+userID+"' and Req_Type='Travel'";
					 int upd=ad.SqlExecuteUpdate(updateSql);
					 
					 tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
						tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Approved','','','','"+rqName+"','Status Change From mail','"+userID+"','"+dateNow+"','Approver')";
						  i11=ad.SqlExecuteUpdate(tempinsert);	 
						 
				}
				if(totalRejectCount==2)
				{
					String updateSql1= "update All_Request set Req_Status='Approved', Comments='Status Change From mail',approved_date='"+dateNow+"',rejected_date='',Last_Approver='"+userID+"',Pending_Approver='',Approved_Persons='"+userID+"' where Req_Id='"+reqId+"' and Pending_Approver='"+userID+"'  and Req_Type='Travel'";;
					int upd2=ad.SqlExecuteUpdate(updateSql1);
					
					tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
					tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Approved','','','','"+rqName+"','Status Change From mail','"+userID+"','"+dateNow+"','Approver')";
					  i11=ad.SqlExecuteUpdate(tempinsert);	 
					
				}
			}if(totalApproveCount==1){
				
				//no one leave request is approved
				existStatus=false;
				
				//check cancelled Record
                int cancelledCount=0;
                String getCheckCancelled="select COUNT(*) from All_Request where Req_Id='"+reqId+"' and Req_Type='Travel' and Req_Status='Cancelled'";
                ResultSet rsCheckCancel=ad.selectQuery(getCheckCancelled);
                while(rsCheckCancel.next())
                {
                	cancelledCount=rsCheckCancel.getInt(1);
                	if(cancelledCount>0)
                	{
								request.setAttribute("statusMessage2", "statusMessage2");
								approvalsForm.setMessage2("This Request already has been Cancelled");
								existStatus=true;
                	}
                }
				
				
			}
			if(totalApproveCount==2)
			{
				//show message Approve
				String updateSql= "update All_Request set Approved_Persons='"+userID+"', Last_approver='"+userID+"',Pending_approver='',Comments='Status Change From mail'," +
               "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+userID+"' and Req_Type='Travel'";
				 int upd=ad.SqlExecuteUpdate(updateSql);
				 
				String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
					tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Approved','','','','"+rqName+"','Status Change From mail','"+userID+"','"+dateNow+"','Approver')";
					int i11=ad.SqlExecuteUpdate(tempinsert);	 
					  
				 	request.setAttribute("statusMessage2", "statusMessage2");
					approvalsForm.setMessage2("This Request already has been approved");
					existStatus=true;
			}
		}
		
	}
   	if(existStatus==false){	
	String modifyRequest="update  All_Request set Last_Approver='"+userID+"',Approved_Persons='"+userID+"',Req_Status='Approved',Pending_Approver='',approved_date='"+dateNow+"',rejected_date='' where Req_Type='Travel' and Req_Id='"+reqId+"' and Pending_Approver='"+userID+"'";	
	int statusChange=ad.SqlExecuteUpdate(modifyRequest);
	
	String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
	tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Approved','','','','"+rqName+"','Status Change From mail','"+userID+"','"+dateNow+"','Approver')";
	  int i11=ad.SqlExecuteUpdate(tempinsert);	 
    if(statusChange>0)
    {
    	approvalsForm.setMessage("");
    	approvalsForm.setMessage2("");
    	approvalsForm.setMessage2("Request has been approved.");
    	status="Approve";
    }
	String updateSql= "update travel_Desk set Last_approver='"+userID+"',Pending_approver='',Req_status='Approved',approved_date='"+dateNow+"' where reqno='"+reqId+"'";
	ad.SqlExecuteUpdate(updateSql);
	
	String updateSql1= "update OnDuty_details set Last_approver='"+userID+"',Pending_approver='',OnDuty_status='1',Approver_Status='Approved',approved_date='"+dateNow+"',rejected_date='',comments='' "
			+ "where travel_no='"+reqId+"'";
	int recordChange=ad.SqlExecuteUpdate(updateSql1);
	
	EMailer email = new EMailer();
	int k = email.sendMailToAccepter(request, "",reqId,"Travel Desk");
	  
	  
   	}
   	}
	}catch (Exception e) {
		e.printStackTrace();
	}
   	}
   	if(status.equalsIgnoreCase("Reject")){
   		
   		int recordCount=0;
   		String getLeaveDetails="select * from travel_desk where reqno='"+reqId+"'"; 
		ResultSet rsLeave=ad.selectQuery(getLeaveDetails);
	   	try{
		while(rsLeave.next())
		{
			empID=rsLeave.getString("pending_approver");
		}
   	
   	String checkRecord="select count(*) from All_Request where Req_Type='Travel' and Req_Id='"+reqId+"' and Req_Status='Rejected'  and (Pending_Approver='"+userID+"' or Approved_Persons='"+userID+"')";
   	ResultSet rsChcek=ad.selectQuery(checkRecord);

   	while(rsChcek.next())
   	{
   		recordCount=rsChcek.getInt(1);
   		approvalsForm.setMessage("");
   		if(recordCount>0)
   		approvalsForm.setMessage("This request has already been rejected.");
   	}
   	if(recordCount!=1)
   	{
	checkRecord="select count(*) from All_Request where Req_Type='Travel' and Req_Id='"+reqId+"' and Req_Status='Self Cancelled'";
   	ResultSet rsChcek1=ad.selectQuery(checkRecord);
   	while(rsChcek1.next())
   	{
   		recordCount=rsChcek1.getInt(1);
   		approvalsForm.setMessage2("");
   		if(recordCount>0){
   		approvalsForm.setMessage("This request has already been self cancelld.");
   		status="Self Cancelled";
   		}
   	}
   	}
   	}catch (Exception e) {
		e.printStackTrace();
	}
   	
   	boolean existStatus=false;
   	try{
	String dataFlowType=""; 
	String checkESSApproverList="select count(*) from ESS_Approvers where employeeNumber='"+rqName+"' and essType='leave'";
	ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
	while(rsCheckInEss.next())
	{
		int number=rsCheckInEss.getInt(1);
		if(number==0)
		{
			existStatus=false;
		}else{
			existStatus=true;
		}
	}
	if(existStatus==true)
	{
		String getDataFlowType="select * from ESS_Approvers where employeeNumber='"+rqName+"' and essType='leave' ";
		ResultSet rsDataFlowType=ad.selectQuery(getDataFlowType);
		while(rsDataFlowType.next())
		{
			dataFlowType=rsDataFlowType.getString("reqType");
		}
		if(dataFlowType.equalsIgnoreCase("sequential")){
			existStatus=true;
		}
		if(dataFlowType.equalsIgnoreCase("parallel")){
			
			String frstapp="";
			String par1app="";
			String par2app="";
			
			//Approvers
			String getNextApprDetails="select * from ESS_Approvers where employeeNumber='"+rqName+"'  and essType='leave'";
            ResultSet rsNextApprDetails=ad.selectQuery(getNextApprDetails);
            while(rsNextApprDetails.next())
            {
            	frstapp=rsNextApprDetails.getString("ApproverId");
            	par1app=rsNextApprDetails.getString("Parallel_Approver1");
            	par2app=rsNextApprDetails.getString("Parallel_Approver2");
            }
		//check reject count
			String comments="Status Change From mail";
			int totalRejectCount=0;
			String getTotApprCount="select count(*) from All_Request where Req_Id='"+reqId+"' and Requester_Name='"+empID+"' and Req_Status='Rejected' and Req_Type='Travel'";
			ResultSet rsTotApprCount=ad.selectQuery(getTotApprCount);
			while(rsTotApprCount.next())
			{
				totalRejectCount=rsTotApprCount.getInt(1);
			}
			if(totalRejectCount==0)
			{
				MainDao exeQry = new MainDao();
				LinkedList paramList =  new LinkedList();
				//check Approve Count
				int totalApproveCount=0;
				 getTotApprCount="select count(*) from All_Request where Req_Id='"+reqId+"' and Requester_Name='"+empID+"' and Req_Status='Approved' and Req_Type='Travel'";
				 rsTotApprCount=ad.selectQuery(getTotApprCount);
				while(rsTotApprCount.next())
				{
					totalApproveCount=rsTotApprCount.getInt(1);
				}
				if(totalApproveCount==1)
				{
					String updateSql= "update All_Request set Req_Status='Rejected', Comments='"+comments+"',approved_date='',rejected_date='"+dateNow+"',Last_Approver='"+userID+"',Pending_Approver='',Approved_Persons='"+userID+"' where Req_Id='"+reqId+"' and Pending_Approver='"+userID+"'  and Req_Type='Travel'";
					int upd1=exeQry.SqlExecuteUpdate(updateSql, paramList);
					approvalsForm.setMessage("");
			   		approvalsForm.setMessage2("Request has been rejected.");
			   		status="Reject";
			   		String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,rejected_date,Role) values (";
			   		tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Rejected','','','','"+rqName+"','"+comments+"','"+userID+"','"+dateNow+"','Approver')";
			   		  int i11=ad.SqlExecuteUpdate(tempinsert);
				}
				if(totalApproveCount==2)
				{
					String updateSql= "update All_Request set Req_Status='Rejected', Comments='"+comments+"',approved_date='',rejected_date='"+dateNow+"',Last_Approver='"+userID+"',Pending_Approver='',Approved_Persons='"+userID+"' where Req_Id='"+reqId+"' and Pending_Approver='"+userID+"'  and Req_Type='Travel'";
					int upd1=exeQry.SqlExecuteUpdate(updateSql, paramList);
					approvalsForm.setMessage("");
					approvalsForm.setMessage2("Request has been rejected.");
			   		status="Reject";
			   		
			   		String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,rejected_date,Role) values (";
			   		tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Rejected','','','','"+rqName+"','"+comments+"','"+userID+"','"+dateNow+"','Approver')";
			   		  int i11=ad.SqlExecuteUpdate(tempinsert);
					
				}
				if(totalApproveCount==0)
				{
					String	updateSql= "update All_Request set Req_Status='Rejected', Comments='"+comments+"',approved_date='',rejected_date='"+dateNow+"',Last_Approver='"+userID+"',Pending_Approver='',Approved_Persons='"+userID+"',Actual_Approver='"+userID+"' where Req_Id='"+reqId+"' and Pending_Approver='"+userID+"'  and Req_Type='Travel'";
				int upd1=exeQry.SqlExecuteUpdate(updateSql, paramList);
				
				String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,rejected_date,Role) values (";
		   		tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Rejected','','','','"+rqName+"','"+comments+"','"+userID+"','"+dateNow+"','Approver')";
		   		  int i11=ad.SqlExecuteUpdate(tempinsert);
				approvalsForm.setMessage("");
		   		approvalsForm.setMessage2("Request has been rejected.");
		   		status="Reject";
		   		
		   		if(par1app==null)
				{
					par1app="";
				}
				
				if(par2app==null)
				{
					par2app="";
				}
		   		

		   		if(!frstapp.equalsIgnoreCase(""))
				{
					String updateSql1= "update All_Request set Approved_Persons='"+frstapp+"', Last_approver='"+frstapp+"',Pending_approver='',Comments='"+comments+"'," +
                            "approved_date='',rejected_date='"+dateNow+"',Req_Status='Rejected',Actual_Approver='"+userID+"',Actual_Approver_status='Rejected',Actual_Approved_Date='"+dateNow+"',Actual_Approved_Comments='Status Change From mail' where Req_Id='"+reqId+"' and Pending_Approver='"+frstapp+"' and Req_Type='Travel'";
							 int upd11=ad.SqlExecuteUpdate(updateSql1);
							 
							  tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,rejected_date,Role) values (";
						   		tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Rejected','','','','"+rqName+"','"+comments+"','"+frstapp+"','"+dateNow+"','Approver')";
						   		   i11=ad.SqlExecuteUpdate(tempinsert);
				}
								


             if(!par1app.equalsIgnoreCase(""))
						{
							String updateSql1= "update All_Request set Approved_Persons='"+par1app+"', Last_approver='"+par1app+"',Pending_approver='',Comments='"+comments+"'," +
	                                "approved_date='',rejected_date='"+dateNow+"',Req_Status='Rejected',Actual_Approver='"+userID+"',Actual_Approver_status='Rejected',Actual_Approved_Date='"+dateNow+"',Actual_Approved_Comments='' where Req_Id='"+reqId+"' and Pending_Approver='"+par1app+"' and Req_Type='Travel'";
	   							 int upd11=ad.SqlExecuteUpdate(updateSql1);
	   							 tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,rejected_date,Role) values (";
							   		tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Rejected','','','','"+rqName+"','"+comments+"','"+par1app+"','"+dateNow+"','Approver')";
							   		   i11=ad.SqlExecuteUpdate(tempinsert);
						}
						
						if(!par2app.equalsIgnoreCase(""))
						{
							String updateSql1= "update All_Request set Approved_Persons='"+par2app+"', Last_approver='"+par2app+"',Pending_approver='',Comments='"+comments+"'," +
	                                "approved_date='',rejected_date='"+dateNow+"',Req_Status='Rejected',Actual_Approver='"+userID+"',Actual_Approver_status='Rejected',Actual_Approved_Date='"+dateNow+"',Actual_Approved_Comments='' where Req_Id='"+reqId+"' and Pending_Approver='"+par2app+"' and Req_Type='Travel'";
	   							 int upd11=ad.SqlExecuteUpdate(updateSql1);
	   							 
	   							 tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,rejected_date,Role) values (";
							   		tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Rejected','','','','"+rqName+"','"+comments+"','"+par2app+"','"+dateNow+"','Approver')";
							   		   i11=ad.SqlExecuteUpdate(tempinsert);
	   							 
	   							 
						}
		   		
					
				    
					String updateSql1= "update travel_desk set Req_status='Rejected',Last_Approver='"+userID+"',Pending_Approver='',approved_date=getdate()  where reqno='"+reqId+"'";
					int ii=ad.SqlExecuteUpdate(updateSql1);
					
					String updateSql2= "update OnDuty_details set Last_approver='"+userID+"',Pending_approver='',OnDuty_status='2',Approver_Status='Rejected',approved_date='',rejected_date='"+dateNow+"',comments='' where travel_no='"+reqId+"'";
					int recordChange=ad.SqlExecuteUpdate(updateSql2);

					
			
				}
			}
			if(totalRejectCount==1)
			{
				existStatus=true;
			}
			if(totalRejectCount==2)
			{
				MainDao exeQry = new MainDao();
				LinkedList paramList =  new LinkedList();
				String	updateSql= "update All_Request set Req_Status='Rejected', Comments='"+comments+"',approved_date='',rejected_date='"+dateNow+"',Last_Approver='"+userID+"',Pending_Approver='',Approved_Persons='"+userID+"' where Req_Id='"+reqId+"' and Pending_Approver='"+userID+"'  and Req_Type='Travel'";
				int upd1=exeQry.SqlExecuteUpdate(updateSql, paramList);
				
				 String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,rejected_date,Role) values (";
			   		 tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Rejected','','','','"+rqName+"','"+comments+"','"+par2app+"','"+dateNow+"','Approver')";
			   		   int i11=ad.SqlExecuteUpdate(tempinsert);
				
				approvalsForm.setMessage("");
		   		approvalsForm.setMessage2("Request has been rejected.");
		   		status="Reject";
			}
			
			
		}
	}
   	}catch (Exception e) {
		e.printStackTrace();
	}
   	
	if(existStatus==true)
	{
   	if(recordCount==0)
   	{
   		String modifyRequest="update  All_Request set Last_Approver='"+userID+"',Approved_Persons='"+userID+"',Req_Status='Rejected',Pending_Approver='',approved_date='',rejected_date='"+dateNow+"' where Req_Type='Travel' and Req_Id='"+reqId+"' and  Pending_Approver='"+userID+"'";	
		int statsChange=ad.SqlExecuteUpdate(modifyRequest);	
		
		 String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,rejected_date,Role) values (";
   		 tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Rejected','','','','"+rqName+"','','"+userID+"','"+dateNow+"','Approver')";
   		   int i11=ad.SqlExecuteUpdate(tempinsert);
		if(statsChange>0){
			
		    	approvalsForm.setMessage("");
		   		approvalsForm.setMessage2("Request has been rejected.");
		   		status="Reject";
		    
			String updateSql= "update travel_Desk set req_status='Rejected',Last_Approver='"+userID+"',Pending_Approver='',approved_date='',rejected_date='"+dateNow+"'  where Req_Id='"+reqId+"'";
			int ii=ad.SqlExecuteUpdate(updateSql);
			
			String updateSql1= "update OnDuty_details set Last_approver='"+userID+"',Pending_approver='',OnDuty_status='2',Approver_Status='Rejected',approved_date='',rejected_date='"+dateNow+"',comments='' "
					+ "where travel_no='"+reqId+"'";
			int recordChange=ad.SqlExecuteUpdate(updateSql1);

			
			
			}
		}
   	}
   	}
	}
		String getLeaveRecords="";
		if(status.equalsIgnoreCase("Approve")){}
		if(status.equalsIgnoreCase("Reject")){
		}
		
	 return mapping.findForward("approvePage");
	}

		public ActionForward mailconfirmBookingDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) {
			//Created by Arun .Date:15-11-2016
			
			
			
			String userID=request.getParameter("userID");
			String reqId=request.getParameter("reqId");
			String status=request.getParameter("status");
			
			
			String rqDate="";
			String rqName="";
				   String checkRecord1="select top 1 * from All_Request where Req_Type='Travel' and Req_Id='"+reqId+"'";
				   	ResultSet rsChcek11=ad.selectQuery(checkRecord1);
				   	try {
						while(rsChcek11.next())
						{
							rqDate=rsChcek11.getString("Req_Date");
									rqName=rsChcek11.getString("Requester_Name");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
		   TravelRequestForm requestForm = (TravelRequestForm)form;
		   
		
		   	String getLeaveDetails="select  isnull(confirmstatus,'') as confirmstatus1,* from travel_desk where reqno='"+reqId+"' and isnull(confirmstatus,'')!='' "; 
			ResultSet rsLeave=ad.selectQuery(getLeaveDetails);
			try {
				if(rsLeave.next())
				{
					if(rsLeave.getString("confirmstatus1").equalsIgnoreCase("Booked"))
					{
						
						requestForm.setMessage2("Traved Request already Confirmed");
						return mapping.findForward("approvePage");
						
					}
					
					if(rsLeave.getString("confirmstatus1").equalsIgnoreCase("Rejected"))
					{
						
						requestForm.setMessage2("Travel Request already Rejected");
						return mapping.findForward("approvePage");
						
					}
					
					
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			///update
		
			if(status.equalsIgnoreCase("Approve"))
			{
			String yp="update Travel_Desk set confirmstatus='Booked',confirmdate=getdate(),confirmby='"+userID+"' ,confirm_Comments='Status Change From mail'  "
					+ " where reqNo='"+reqId+"'";
			int h=ad.SqlExecuteUpdate(yp);
			
			String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
			tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Booked','','','','"+rqName+"','Booked','"+userID+"',getdate(),'Travel Desk')";
			 int i11=ad.SqlExecuteUpdate(tempinsert);
			if(h>0)
			{
				EMailer email = new EMailer();
				email.sendMailToRequester(request, "", reqId, "Travel Desk");
		}
			
			requestForm.setMessage2("Request has been Accepted successfully");
			
			}
			
			if(status.equalsIgnoreCase("Reject"))
			{
			
				requestForm.setMessage2("Request has been cancelled successfully");
			String updateSql= "update Travel_Desk set Req_status='Rejected' ,confirmstatus='Rejected',"
					+ "  confirmdate=getdate(),confirmby='"+userID+"',Approved_date=getdate() ,confirm_Comments='Status Change From mail'   "
					+ "  where reqno='"+reqId+"' ";
			int ii=ad.SqlExecuteUpdate(updateSql);
			
			String tempinsert="insert into Travel_Request_History (Req_Id, Req_Type,Req_date, Requester_Name, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,Actual_Approver,approved_date,Role) values (";
			tempinsert = tempinsert + "'"+reqId+"','Travel','"+rqDate+"','"+rqName+"','Rejected','','','','"+rqName+"','Booked','"+userID+"',getdate(),'Travel Desk')";
			 int i11=ad.SqlExecuteUpdate(tempinsert);
			
			
			String updateSql1= "update OnDuty_details set Last_approver='"+userID+"',Pending_approver='',OnDuty_status='2',Approver_Status='Rejected',approved_date='',rejected_date=getdate(),comments='' where travel_no='"+reqId+"'";
			int recordChange=ad.SqlExecuteUpdate(updateSql1);

			EMailer email = new EMailer();
			email.sendMailToRequester(request, "", reqId, "Travel Desk");  
			
			}
			return mapping.findForward("approvePage");
		}
		
		public ActionForward ViewDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) 
		{
			
			//Created by Arun .Date:15-11-2016
			
			TravelRequestForm typ =(TravelRequestForm)form;
			
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			if(user==null){
				request.setAttribute("message","Session Expried! Try to Login again!");
				return mapping.findForward("displayiFrameSession");
			}
			String filterBy = request.getParameter("requststatus");
			String reqno=request.getParameter("reqno");
			String sid=request.getParameter("sid");
			String travelRequestFor=request.getParameter("travelRequestFor");
			String type=request.getParameter("type");
			typ.setTravelRequestFor(travelRequestFor);
			typ.setId(Integer.parseInt(sid));
			typ.setRequestNumber(Integer.parseInt(reqno));

				   if(type.equalsIgnoreCase("travellist"))
				   {
					   
				  
	                
	                String his="select CONVERT(varchar(10),Date_of_issue,103)  as Date_of_issue1,CONVERT(varchar(10),Date_of_expiry,103)  as Date_of_expiry1,* from"
	                		+ " Travel_Desk_Travellers where id='"+typ.getId()+"'" ;
	                ResultSet b=ad.selectQuery(his);
	             
	        		try {
	        			while(b.next())
	        			{
	        					String ere[]=empDet(Integer.toString(b.getInt("PERNR")));
	        					typ.setGuest_pernr(Integer.toString(b.getInt("PERNR")));
	        					typ.setEmployeeName(ere[0]);
	        					typ.setLocationId(ere[1]);
	        					typ.setDepartment(ere[2]);
	        					typ.setDesignation(ere[3]); 
	        					typ.setUserAge(ere[5]);
	        					typ.setUserGender(ere[7]);                		
	        					typ.setGuest_Type(b.getString("Req_type"));
	        					typ.setUtravelmode(b.getString("utravelmode"));
	        					typ.setTraveltype(b.getString("utraveltype"));
	        					typ.setPassportno(b.getString("Passport_no"));
	        					typ.setPassportplace(b.getString("Place_issue"));
	        					typ.setPassportexpirydate(b.getString("Date_of_expiry"));
	        					typ.setGuestName(b.getString("Name_of_guest"));
	        					typ.setGuestContactNo(b.getString("Contact_guest"));
	        					typ.setGuest_Company(b.getString("guest_Company"));
	        					typ.setGuestAge(b.getString("Age_guest"));
	        					typ.setGender(b.getString("Gender_guest"));
	        					typ.setEmail_Guest(b.getString("Email_guest"));
	        					typ.setFileName(b.getString("file_name"));
	        					typ.setFileFullPath(b.getString("path"));
	        					typ.setGuest_Title(b.getString("guest_Title"));
	        					typ.setGuest_Visano(b.getString("guest_Visano"));
	        					typ.setGuest_DOB(b.getString("guest_DOB"));
	        					typ.setGuest_Meal(b.getString("guest_Meal"));
	        					typ.setFromPlace(b.getString("fromPlace"));
	        					typ.setToPlace(b.getString("toPlace"));
	        					typ.setDepartOn(b.getString("departOn"));
	        					typ.setDepartTime(b.getString("departTime"));
	        					typ.setReturnOn(b.getString("returnOn"));
	        					typ.setReturnTime(b.getString("returnTime"));
	        					typ.setGuest_Meal(b.getString("guest_Meal"));
	        					typ.setHotel_Res(b.getString("hotel_Res"));
	        					typ.setRent_Car(b.getString("rent_Car"));
	        					typ.setAccom_type(b.getString("accom_type"));
	        					typ.setAccom_name(b.getString("accom_name"));
	        					typ.setHotel_Name(b.getString("hotel_Name"));
	        					typ.setHotel_City(b.getString("hotel_City"));
	        					typ.setPickup_Details(b.getString("pickup_Details"));
	        					typ.setDrop_Details(b.getString("drop_Details"));
	        					
	        					typ.setId(b.getInt("id"));
	        					if(!b.getString("Date_of_expiry1").contains("1900"))
	        					typ.setUserpassportexpirydate(b.getString("Date_of_expiry1")); 
	        					else
	        			    	typ.setUserpassportexpirydate("");
	        			}
	        		} catch (SQLException e1) {
	        			// TODO Auto-generated catch block
	        			e1.printStackTrace();
	        		}
	                	request.setAttribute("travellist", "done");
				   }	
				   
				   if(type.equalsIgnoreCase("multicity"))
				   {
					   
				   
				    String his1="select Travel_Multiple_City.trav_id as id1, isnull(convert(nvarchar(10),Travel_Multiple_City.trav_id)+'-'+emp_official_info.EMP_FULLNAME,0) as id,Travel_Multiple_City.Location,"
				    		+ " Travel_Multiple_City.mtravelmode ,Travel_Multiple_City.Air_preference,convert(nvarchar(10),Travel_Multiple_City.Arrival_date,103) as Arrival_date,"
				    		+ " Travel_Multiple_City.Arrival_time,convert(nvarchar(10),Travel_Multiple_City.Departure_date,103) as Departure_date,"
				    		+ " Travel_Multiple_City.Departure_time ,Travel_Multiple_City.mhotel_Res,Travel_Multiple_City.mrent_Car,Travel_Multiple_City.mhotel_Name,"
				    		+ " Travel_Multiple_City.mhotel_City,Travel_Multiple_City.mpickup_details,Travel_Multiple_City.mdrop_Details"
				    		+ " from Travel_Multiple_City left outer join Travel_Desk_Travellers on Travel_Multiple_City.trav_id=Travel_Desk_Travellers.id and Travel_Desk_Travellers.id!=0"
				    		+ " left outer join emp_official_info on emp_official_info.PERNR=Travel_Desk_Travellers.PERNR"
				    		+ " where Travel_Multiple_City.id='"+typ.getId()+"'" ; ;
	                ResultSet b1=ad.selectQuery(his1);
	        		try {
	        			while(b1.next())
	        			{
	        					typ.setMultiemployeeno(b1.getString("id"));
	        					typ.setId(b1.getInt("id1"));
	        					typ.setLocationId(b1.getString("location"));
	        					typ.setAirline_Pref(b1.getString("air_preference"));
	        					typ.setTrip_From_Date(b1.getString("Arrival_date"));
	        					typ.setTrip_To_time(b1.getString("Arrival_Time"));
	        					typ.setTrip_To_Date(b1.getString("Departure_date"));
	        					typ.setTrip_To_time(b1.getString("Departure_time"));
	        					typ.setHotel_Res(b1.getString("mhotel_Res"));
	        					typ.setRent_Car(b1.getString("mrent_Car"));
	        					typ.setHotel_Name(b1.getString("mhotel_Name"));
	        					typ.setHotel_City(b1.getString("mhotel_City"));
	        					typ.setPickup_Details(b1.getString("mpickup_Details"));
	        					typ.setDrop_Details(b1.getString("mdrop_Details"));
	        					typ.setTravelmode(b1.getString("mtravelmode"));
	        					typ.setTraveltype(b1.getString("mtravelmode"));
	        					typ.setPassportno(b1.getString("mpassportno"));
	        					typ.setPassportexpirydate(b1.getString("mpassportexpirydate"));
	        					typ.setVisano(b1.getString("mguest_visano"));
	        					typ.setLocationId(b1.getString("locationId"));
	        					typ.setAirline_Pref(b1.getString("air_preference"));
	        					typ.setOrigin(b1.getString("morigin"));
	        					typ.setDeparture(b1.getString("mdeparture"));
	        					typ.setDepartOn( b1.getString("Arrival_date"));
	        					typ.setDepartTime(b1.getString("Arrival_time") );
	        					typ.setReturnOn(b1.getString("Departure_date"));
	        					typ.setReturnTime(b1.getString("Departure_time"));
	        					typ.setHotel_Res(b1.getString("mhotel_res"));
	        					typ.setRent_Car(b1.getString("mrent_Car"));
	        					typ.setAccom_type(b1.getString("maccom_type"));
	        					typ.setAccom_name(b1.getString("maccom_name"));
	        					typ.setHotel_Name(b1.getString("mhotel_Name"));
	        					typ.setHotel_City(b1.getString("mhotel_city"));
	        					typ.setPickup_Details(b1.getString("mpickup_Details"));
	        					typ.setDrop_Details(b1.getString("mdrop_Details"));
	        					
	        			}
	        		} catch (SQLException e1) {
	        			// TODO Auto-generated catch block
	        			e1.printStackTrace();
	        		}
	                	request.setAttribute("multicity", "done");
				
				   }
			
			return mapping.findForward("viewDetails");
		}	
	
}
