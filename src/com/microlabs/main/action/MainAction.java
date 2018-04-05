package com.microlabs.main.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jsoup.Jsoup;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.login.dao.LoginDao;
import com.microlabs.main.form.MainForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.imageio.ImageIO;

public class MainAction extends DispatchAction{
	LoginDao ad=new LoginDao();
	   String captchaString = "";

	    // Function to generate random captcha image and returns the BufferedImage
	    public BufferedImage getCaptchaImage() {
	        try {
	            Color backgroundColor = Color.white;
	            Color borderColor =  Color.white;
	            Color textColor =  Color.black;
	            Color circleColor = new Color(190, 160, 150);
	            Font textFont = new Font("Verdana", Font.BOLD,25);
	            int charsToPrint = 4;
	            int width = 160;
	            int height = 50;
	            int circlesToDraw = 30;
	            float horizMargin = 10.0f;
	            double rotationRange = 0.7; 
	            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	            Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
	            g.setColor(backgroundColor);
	            g.fillRect(0, 0, width, height);

	            // lets make some noisey circles
	            g.setColor(circleColor);
	            for (int i = 0; i < circlesToDraw; i++) {
	                int L = (int) (Math.random() * height / 2.0);
	                int X = (int) (Math.random() * width - L);
	                int Y = (int) (Math.random() * height - L);
	                g.draw3DRect(X, Y, L * 2, L * 2, true);
	            }
	            g.setColor(textColor);
	            g.setFont(textFont);
	            FontMetrics fontMetrics = g.getFontMetrics();
	            int maxAdvance = fontMetrics.getMaxAdvance();
	            int fontHeight = fontMetrics.getHeight();

	            // i removed 1 and l and i because there are confusing to users...
	            // Z, z, and N also get confusing when rotated
	            // this should ideally be done for every language...
	            // 0, O and o removed because there are confusing to users...
	            // i like controlling the characters though because it helps prevent confusion
	            String elegibleChars = "ABCDEFGHJKLMNPQRSTUVWXYabcdefghjkmnpqrstuvwxy23456789";
	            char[] chars = elegibleChars.toCharArray();
	            float spaceForLetters = -horizMargin * 2 + width;
	            float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);
	            StringBuffer finalString = new StringBuffer();
	            for (int i = 0; i < charsToPrint; i++) {
	                double randomValue = Math.random();
	                int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
	                char characterToShow = chars[randomIndex];
	                finalString.append(characterToShow);

	                // this is a separate canvas used for the character so that
	                // we can rotate it independently
	                int charWidth = fontMetrics.charWidth(characterToShow);
	                int charDim = Math.max(maxAdvance, fontHeight);
	                int halfCharDim = charDim / 2;
	                BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
	                Graphics2D charGraphics = charImage.createGraphics();
	                charGraphics.translate(halfCharDim, halfCharDim);
	                double angle = (Math.random() - 0.5) * rotationRange;
	                charGraphics.transform(AffineTransform.getRotateInstance(angle));
	                charGraphics.translate(-halfCharDim, -halfCharDim);
	                charGraphics.setColor(textColor);
	                charGraphics.setFont(textFont);
	                int charX = (int) (0.5 * charDim - 0.5 * charWidth);
	                charGraphics.drawString("" + characterToShow, charX, (charDim - fontMetrics.getAscent()) / 2 + fontMetrics.getAscent());
	                float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
	                int y = (height - charDim) / 2;
	                g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
	                charGraphics.dispose();
	            }
	            g.setColor(borderColor);
	            g.drawRect(0, 0, width - 1, height - 1);
	            g.dispose();
	            captchaString = finalString.toString();
	            System.out.println(captchaString);
	            File outputfile = new File("C://Tomcat 8.0/webapps/EMicro Files/images/EmpPhotos/image2.jpg");
	            ImageIO.write(bufferedImage, "jpg", outputfile);
	            
	            return bufferedImage;
	        } catch (Exception ioe) {
	            throw new RuntimeException("Unable to build image", ioe);
	        }
	    }

	    // Function to return the Captcha string
	    public String getCaptchaString() {
	        return captchaString;
	    }
		
	
	 private static final char[] PASSWORD = "enfldsgbnlfghfghsngdlkddddsgm".toCharArray();
	    private static final byte[] SALT = {
	        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
	        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
	    };

	
	 private static String encrypt(String property) throws GeneralSecurityException, UnsupportedEncodingException {
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
	        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
	        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
	        return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
	    }

	    private static String base64Encode(byte[] bytes) {
	        // NB: This class is internal, and you probably should use another impl
	        return new BASE64Encoder().encode(bytes);
	    }

	    private static String decrypt(String property) throws GeneralSecurityException, IOException {
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
	        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
	        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
	        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
	    }

	    private static byte[] base64Decode(String property) throws IOException {
	        // NB: This class is internal, and you probably should use another impl
	        return new BASE64Decoder().decodeBuffer(property);
	    }

	
	public ActionForward alertmessage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		

		MainForm mainForm = (MainForm) form;// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		EssDao ad = new EssDao();
		UserInfo user = (UserInfo) session.getAttribute("user");


		String loc = "";
		int cat = 0;
		int dept = 0;
		String a = "select * from emp_official_info where pernr='"
				+ user.getEmployeeNo() + "'";
		ResultSet rs = ad.selectQuery(a);
		try {
			while (rs.next()) {
				loc = rs.getString("LOCID");
				cat = rs.getInt("STAFFCAT");
				dept = rs.getInt("DPTID");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String getAnnouncemtnData = "select * from ALERT_BOX a,emp_official_info e where convert (nvarchar(16),GETDATE(),20) between Start_date and End_date and Status='1' and e.LOCID like '%"+loc+"%' and  e.STAFFCAT like '%"+cat+"%' and e.DPTID like '%"+dept+"%' and a.Empnos like '%"+user.getEmployeeNo()+"%' and e.PERNR='"+user.getEmployeeNo()+"'";

		ResultSet rsAnnouncemtnData = ad.selectQuery(getAnnouncemtnData);
		LinkedList listOfData = new LinkedList();

		try {
			while (rsAnnouncemtnData.next()) {
				
				mainForm.setId(rsAnnouncemtnData.getInt("id"));
				mainForm.setHeadLines(rsAnnouncemtnData.getString("Content_Heading"));
				mainForm.setAlertheader(rsAnnouncemtnData.getString("Content_subject"));
				String contentDescription = rsAnnouncemtnData.getString("Content_description");													
				mainForm.setLinkDescription(contentDescription);
				request.setAttribute("ContentData", contentDescription);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	
		
		return mapping.findForward("alertmessage");
	}
	
	public static String addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        Date test=cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		 String dateNow = ft.format(test);
        return dateNow;
    }
	public ActionForward displayThoughts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		MainForm mainForm=(MainForm)form;
		
		LinkedList listOfOrganisationData=new LinkedList();	
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
		//thought of the day
		
		//check Status On/Off
		String status="";
		int count=0;
		Date dNow1 = new Date( );
		 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd");
		 String dateNow1 = ft1.format(dNow1);
		String getStatus="select count(*) from THOUGHT_FOR_THE_DAY where Status='On' and Display_date='"+dateNow1+"'";
		ResultSet rsStatus=ad.selectQuery(getStatus);
		while(rsStatus.next())
		{
			count=rsStatus.getInt(1);
		}
		if(count==0)
		{
			count=0;
			String checkCurrenDate="select count(*) from THOUGHT_FOR_THE_DAY where  Display_date='"+dateNow1+"'";
			ResultSet rscheckCurrenDate=ad.selectQuery(checkCurrenDate);
			while(rscheckCurrenDate.next())
			{
				count=rscheckCurrenDate.getInt(1);
			}
			if(count==0)
			{
				//increament dates
				int total=0;
				String getCount="select count(*) from THOUGHT_FOR_THE_DAY ";
				ResultSet rsCount=ad.selectQuery(getCount);
				while(rsCount.next())
				{
					total=rsCount.getInt(1);
				}
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date	displayDate1=formatter.parse(dateNow1);
				String	displayDate12="";
				for(int i=1;i<=total;i++)
				{ 
					if(i==1)
					{
					Format formatter1 = new SimpleDateFormat("yyyy-MM-dd");
						displayDate12 = formatter.format(displayDate1);
					}
					String updateDate="update THOUGHT_FOR_THE_DAY set Display_date='"+displayDate12+"' where  Priority='"+i+"'";
					int j1=ad.SqlExecuteUpdate(updateDate);
					
					 formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date  displayDate123=formatter.parse(displayDate12);
						Format formatter12 = new SimpleDateFormat("yyyy-MM-dd");
						displayDate12 = addDays(displayDate123, 1);
				} 
				
			}
			//check 
			
			String updateCurrentDisplayMsg="update THOUGHT_FOR_THE_DAY set Status='On' where Display_date='"+dateNow1+"'";
			int i=ad.SqlExecuteUpdate(updateCurrentDisplayMsg);
			if(i>0)
			{
				String updateNextDayDisplayMsg="update THOUGHT_FOR_THE_DAY set Status='Off' where Display_date!='"+dateNow1+"'";
				ad.SqlExecuteUpdate(updateNextDayDisplayMsg);
			}
		}
		String getThoughtMsg="select * from THOUGHT_FOR_THE_DAY where Status='On'";
		ResultSet rsThoughtMsg=ad.selectQuery(getThoughtMsg);
		while(rsThoughtMsg.next())
		{
			mainForm.setThoughtMsg(rsThoughtMsg.getString("Description"));
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		//emp photos
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("MM/dd");
		String dateNow = ft.format(dNow);
		System.out.println("Current Date ="+dateNow);
LinkedList<MainForm> empBirthDayList=new LinkedList<MainForm>();
String empNameDept="";
String getEmpDetails="select e.PERNR,CONVERT(VARCHAR(5), DOB, 101)as  date,u.Emp_Photo,e.SEX,e.EMP_FULLNAME,dept.DPTSTXT  from " +
" users as u,emp_official_info as e,DEPARTMENT as dept where  '"+dateNow+"'=CONVERT(VARCHAR(5), e.DOB, 101)   and " +
		" u.employeenumber=e.PERNR  and u.employeenumber=e.PERNR and  e.DPTID=dept.DPTID and e.Active='1'";
ResultSet rs=ad.selectQuery(getEmpDetails);
try{
while(rs.next())
{
MainForm mainForm2=new MainForm();
mainForm2.setEmployeeNo(rs.getString("PERNR"));

empNameDept=empNameDept+rs.getString("EMP_FULLNAME")+" - "+rs.getString("DPTSTXT")+" , ";

empBirthDayList.add(mainForm2);
}
}catch (Exception e) {
	e.printStackTrace();
}


String userID=user.getEmployeeNo();

if(empBirthDayList.size()>0)
{
	int j=empNameDept.length();
	empNameDept=empNameDept.substring(0,(empNameDept.length()-2));
	mainForm.setEmpName(empNameDept);
request.setAttribute("empBirthDayList", empBirthDayList);	
}

boolean check=false;
for(MainForm form2:empBirthDayList)
{
 String empNo=form2.getEmployeeNo();
 if(userID.equalsIgnoreCase(empNo))
 {
	 check=true;
 }
}




if(check==true){
	request.setAttribute("birthdayImg", "birthdayImg");
	return mapping.findForward("thoughtsAndBirthday");
}else{
	request.setAttribute("onlyThoughtmsg", "onlyThoughtmsg");
}
		return mapping.findForward("displaythoughts");
	}
	
	
	

	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		MainForm mainForm=(MainForm)form;
		LinkedList listOfOrganisationData=new LinkedList();
		try{	
			int totalRecords=mainForm.getTotalRecords();//21
			int startRecord=mainForm.getStartRecord();//11
			int endRecord=mainForm.getEndRecord();	
			
			
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 mainForm.setTotalRecords(totalRecords);
			 mainForm.setStartRecord(startRecord);
			 mainForm.setEndRecord(totalRecords);
		
			 String getAnnouncemtnData="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY a.ID desc) AS RowNum,a.id,a.Head_Lines,a.Link_Description," +
				"a.Annoucement_Date,a.Annoucement_Time from announcement1 as a where Head_Lines='"+mainForm.getRequiredType()+"') as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
					
					ResultSet rsAnnouncemtnData=ad.selectQuery(getAnnouncemtnData);
					
					String headLinesType="";
					
					while(rsAnnouncemtnData.next()){
						MainForm announcentForm=new MainForm();	
						
						announcentForm.setId(rsAnnouncemtnData.getInt("id"));
						announcentForm.setHeadLines(rsAnnouncemtnData.getString("Head_Lines"));	
						
						String contentDescription=rsAnnouncemtnData.getString("Link_Description");
					
						contentDescription=Jsoup.parse(contentDescription).text();
						if(contentDescription.length()>200)
						contentDescription=contentDescription.substring(0, 200);
						announcentForm.setLinkDescription(contentDescription);
						announcentForm.setAnnoucementDate(rsAnnouncemtnData.getString("Annoucement_Date"));
						announcentForm.setAnnouncementTime(rsAnnouncemtnData.getString("Annoucement_Time"));
						listOfOrganisationData.add(announcentForm);
						
					}		 
					 request.setAttribute("disableNextButton", "disableNextButton");
						request.setAttribute("previousButton", "previousButton");
						if(listOfOrganisationData.size()<10)
						{
							
							request.setAttribute("previousButton", "");
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						}
						request.setAttribute("displayRecordNo", "displayRecordNo");	 
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		String requiredType=mainForm.getRequiredType();
		if(requiredType.equalsIgnoreCase("HEADLINES")){
			request.setAttribute("HeadLinesData",listOfOrganisationData);
		}
		
		else{
			request.setAttribute("OrganisationData",listOfOrganisationData);
		}
		mainForm.setRequiredType(requiredType);
		return mapping.findForward("displayMoreHeadLines");
	}
	public ActionForward firstRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MainForm mainForm=(MainForm)form;
		LinkedList listOfOrganisationData=new LinkedList();
		try{

			int totalRecords=mainForm.getTotalRecords();//21
			int startRecord=mainForm.getStartRecord();//11
			int endRecord=mainForm.getEndRecord();	
			
			
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  mainForm.setTotalRecords(totalRecords);
				  mainForm.setStartRecord(startRecord);
				  mainForm.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  mainForm.setTotalRecords(totalRecords);
					  mainForm.setStartRecord(startRecord);
					  mainForm.setEndRecord(totalRecords);  
				  }

			String getAnnouncemtnData="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY a.ID desc) AS RowNum,a.id,a.Head_Lines,a.Link_Description," +
			"a.Annoucement_Date,a.Annoucement_Time from announcement1 as a where Head_Lines='"+mainForm.getRequiredType()+"') as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				
				ResultSet rsAnnouncemtnData=ad.selectQuery(getAnnouncemtnData);
				
				String headLinesType="";
				
				while(rsAnnouncemtnData.next()){
					MainForm announcentForm=new MainForm();	
					
					announcentForm.setId(rsAnnouncemtnData.getInt("id"));
					announcentForm.setHeadLines(rsAnnouncemtnData.getString("Head_Lines"));	
					
					String contentDescription=rsAnnouncemtnData.getString("Link_Description");
				
					contentDescription=Jsoup.parse(contentDescription).text();
					if(contentDescription.length()>200)
					contentDescription=contentDescription.substring(0, 200);
					announcentForm.setLinkDescription(contentDescription);
					announcentForm.setAnnoucementDate(rsAnnouncemtnData.getString("Annoucement_Date"));
					announcentForm.setAnnouncementTime(rsAnnouncemtnData.getString("Annoucement_Time"));
					listOfOrganisationData.add(announcentForm);
					
				}
			 	
			
			 if(totalRecords>10)
				{
					request.setAttribute("nextButton", "nextButton");
				}
			
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				
				request.setAttribute("displayRecordNo", "displayRecordNo");
				request.setAttribute("materialCodeList","materialCodeList");
					
		
				
		
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		String requiredType=mainForm.getRequiredType();
		if(requiredType.equalsIgnoreCase("HEADLINES")){
			request.setAttribute("HeadLinesData",listOfOrganisationData);
		}
		
		else{
			request.setAttribute("OrganisationData",listOfOrganisationData);
		}
		mainForm.setRequiredType(requiredType);
		return mapping.findForward("displayMoreHeadLines");
	}
		
		
		
	
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MainForm mainForm=(MainForm)form;
		LinkedList listOfOrganisationData=new LinkedList();
		try{
			
			
			int totalRecords=mainForm.getTotalRecords();//21
			int endRecord=mainForm.getStartRecord()-1;//20
			int startRecord=mainForm.getStartRecord()-10;//11
			
			if(startRecord==1)
			{
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				endRecord=10;
			}
			
			
			mainForm.setTotalRecords(totalRecords);
			mainForm.setStartRecord(1);
			mainForm.setEndRecord(10);
			  
			String getAnnouncemtnData="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY a.ID desc) AS RowNum,a.id,a.Head_Lines,a.Link_Description," +
			"a.Annoucement_Date,a.Annoucement_Time from announcement1 as a where Head_Lines='"+mainForm.getRequiredType()+"') as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				
				ResultSet rsAnnouncemtnData=ad.selectQuery(getAnnouncemtnData);
				
				String headLinesType="";
				
				while(rsAnnouncemtnData.next()){
					MainForm announcentForm=new MainForm();	
					
					announcentForm.setId(rsAnnouncemtnData.getInt("id"));
					announcentForm.setHeadLines(rsAnnouncemtnData.getString("Head_Lines"));	
					
					String contentDescription=rsAnnouncemtnData.getString("Link_Description");
				
					contentDescription=Jsoup.parse(contentDescription).text();
					if(contentDescription.length()>200)
					contentDescription=contentDescription.substring(0, 200);
					announcentForm.setLinkDescription(contentDescription);
					announcentForm.setAnnoucementDate(rsAnnouncemtnData.getString("Annoucement_Date"));
					announcentForm.setAnnouncementTime(rsAnnouncemtnData.getString("Annoucement_Time"));
					listOfOrganisationData.add(announcentForm);
					
				}
			 
		
			mainForm.setTotalRecords(totalRecords);
			mainForm.setStartRecord(startRecord);
			mainForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				if(startRecord!=1)
				request.setAttribute("previousButton", "previousButton");
				request.setAttribute("displayRecordNo", "displayRecordNo");
				if(listOfOrganisationData.size()<10)
				{
					mainForm.setStartRecord(1);
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
			
			
		}catch (Exception e) {
		e.printStackTrace();
		}
		String requiredType=mainForm.getRequiredType();
		if(requiredType.equalsIgnoreCase("HEADLINES")){
			request.setAttribute("HeadLinesData",listOfOrganisationData);
		}
		
		else{
			request.setAttribute("OrganisationData",listOfOrganisationData);
		}
		mainForm.setRequiredType(requiredType);
		return mapping.findForward("displayMoreHeadLines");
	}
	
	
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MainForm mainForm=(MainForm)form;
		LinkedList listOfOrganisationData=new LinkedList();
		try{
			int totalRecords=mainForm.getTotalRecords();//21
			int startRecord=mainForm.getStartRecord();//11
			int endRecord=mainForm.getEndRecord();
			
			if(totalRecords>endRecord)
			{
				if(totalRecords==endRecord)
				{
					startRecord=startRecord;
					endRecord=totalRecords;
				}
				if(totalRecords>endRecord)
				{
				startRecord=endRecord+1;
				endRecord=endRecord+10;
				}
				String getAnnouncemtnData="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY a.ID desc) AS RowNum,a.id,a.Head_Lines,a.Link_Description," +
				"a.Annoucement_Date,a.Annoucement_Time from announcement1 as a where Head_Lines='"+mainForm.getRequiredType()+"') as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
					
					ResultSet rsAnnouncemtnData=ad.selectQuery(getAnnouncemtnData);
					
					String headLinesType="";
					
					while(rsAnnouncemtnData.next()){
						MainForm announcentForm=new MainForm();	
						
						announcentForm.setId(rsAnnouncemtnData.getInt("id"));
						announcentForm.setHeadLines(rsAnnouncemtnData.getString("Head_Lines"));	
						
						String contentDescription=rsAnnouncemtnData.getString("Link_Description");
					
						contentDescription=Jsoup.parse(contentDescription).text();
						if(contentDescription.length()>200)
						contentDescription=contentDescription.substring(0, 200);
						announcentForm.setLinkDescription(contentDescription);
						announcentForm.setAnnoucementDate(rsAnnouncemtnData.getString("Annoucement_Date"));
						announcentForm.setAnnouncementTime(rsAnnouncemtnData.getString("Annoucement_Time"));
						listOfOrganisationData.add(announcentForm);
						
					}
			
			}
			
			if(listOfOrganisationData.size()!=0)
			{
			 mainForm.setTotalRecords(totalRecords);
			 mainForm.setStartRecord(startRecord);
			 mainForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;
				
				mainForm.setTotalRecords(totalRecords);
				mainForm.setStartRecord(start);
				mainForm.setEndRecord(end);
				
			}
		 if(listOfOrganisationData.size()<10)
		 {
			 mainForm.setTotalRecords(totalRecords);
			 mainForm.setStartRecord(startRecord);
			 mainForm.setEndRecord(startRecord+listOfOrganisationData.size()-1);
				request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton"); 
			 
		 }
		 
		 if(endRecord==totalRecords)
		 {
			 request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
		 }
		 request.setAttribute("displayRecordNo", "displayRecordNo");	
			
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		String requiredType=mainForm.getRequiredType();
		if(requiredType.equalsIgnoreCase("HEADLINES")){
			request.setAttribute("HeadLinesData",listOfOrganisationData);
		}
		
		else{
			request.setAttribute("OrganisationData",listOfOrganisationData);
		}
		mainForm.setRequiredType(requiredType);
		return mapping.findForward("displayMoreHeadLines");
	}
	
	
	
	public ActionForward getManagmentMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MainForm mainForm=(MainForm)form;
		try{
			String sql11="select * from archieves where link_name='Message From Managment' and module='Main' and status='null'";
			
			ResultSet rs11=ad.selectQuery(sql11);
			
			try{
				while(rs11.next()) {
					 
					mainForm.setContentDescription(rs11.getString("content_description"));
					String videoName=rs11.getString("video_name");
					if(!(videoName.equalsIgnoreCase("")))
					{
					String a[]=videoName.split("/");
					videoName=a[a.length-1];
					mainForm.setManagementVideo(videoName);
					request.setAttribute("managementVideo", "managementVideo");
					}
				}
			
			}catch(SQLException se){
				se.printStackTrace();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("messageFromManagment");
	}
	
	
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MainForm mainForm=(MainForm)form;
		
		HttpSession session=request.getSession();
		session.invalidate();
		
		//display(mapping, form, request, response);
		return mapping.findForward("logout");
	}
	
	
	public ActionForward getAboutDay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	
	System.out.println("getAboutDay()------");
	MainForm mainForm=(MainForm)form;
	String reqDate=request.getParameter("Date");
	System.out.println("reqDate="+reqDate);
	try{
		
		String getDayDetails="select * from holidays where Holiday_Date='"+reqDate+"'";
		ResultSet rsDayDetails=ad.selectQuery(getDayDetails);
		while(rsDayDetails.next())
		{
			mainForm.setReqDay(rsDayDetails.getString("Holiday_Date"));
			mainForm.setDayDetails(rsDayDetails.getString("Holiday_Name"));
		}
		
	//displayCal(mapping, form, request, response);
	}catch (Exception e) {
		e.printStackTrace();
	}
	
		return mapping.findForward("aboutHolidayDetails");
	}
	
	public ActionForward displayCal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("displayCalendar()------");
		MainForm mainForm=(MainForm)form;
		
		
		return mapping.findForward("calendar");
	}
	
	
	public ActionForward displayAnnouncement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MainForm mainForm = (MainForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		EssDao ad = new EssDao();
		try {
			System.out.println("displayAnnouncement()---");

			String loc = "";
			int cat = 0;
			int dept = 0;
			String a = "select * from emp_official_info where pernr='"
					+ user.getEmployeeNo() + "'";
			ResultSet rs = ad.selectQuery(a);
			while (rs.next()) {
				loc = rs.getString("LOCID");
				cat = rs.getInt("STAFFCAT");
				dept = rs.getInt("DPTID");

			}

			String getAnnouncemtnData = "select an.id,an.Head_Lines,an.Link_Description,an.Annoucement_Date,an.Annoucement_Time,an.Subject,an.Location,an.Category,an.Department from emp_official_info as e,announcement1 as an where Head_Lines='HEADLINES' and  an.Location like '%"
					+ loc
					+ "%' and an.Category like '%"
					+ cat
					+ "%' and an.Department like '%"
					+ dept
					+ "%' group by an.id,an.Head_Lines,an.Link_Description,an.Annoucement_Date,an.Annoucement_Time,an.Subject,an.Location,an.Category,an.Department order by id desc ";

			ResultSet rsAnnouncemtnData = ad.selectQuery(getAnnouncemtnData);
			LinkedList listOfData = new LinkedList();
			int count = 0;
			while (rsAnnouncemtnData.next()) {

				MainForm announcentForm = new MainForm();
				announcentForm.setId(rsAnnouncemtnData.getInt("id"));
				announcentForm.setHeadLines(rsAnnouncemtnData
						.getString("Head_Lines"));
				String contentDescription = rsAnnouncemtnData
						.getString("Subject");
				contentDescription = contentDescription.replaceAll("\\<.*?\\>",
						"");
				int contentlength = contentDescription.length();
				if (contentlength > 280)
					contentDescription = contentDescription.substring(0, 280);
				announcentForm.setLinkDescription(contentDescription);
				announcentForm.setAnnoucementDate(rsAnnouncemtnData
						.getString("Annoucement_Date"));
				announcentForm.setAnnouncementTime(rsAnnouncemtnData
						.getString("Annoucement_Time"));
				listOfData.add(announcentForm);
				count++;
				if (count == 2)
					break;

			}

			request.setAttribute("HeadLinesData", listOfData);

			String getOrganisationData = "select an.id,an.Head_Lines,an.Link_Description,an.Annoucement_Date,an.Annoucement_Time,an.Subject,an.Location,an.Category,an.Department from emp_official_info as e,announcement1 as an where Head_Lines='ORGANIZATION ANNOUNCEMENTS' and  an.Location like '%"
					+ loc
					+ "%' and an.Category like '%"
					+ cat
					+ "%' and an.Department like '%"
					+ dept
					+ "%' group by an.id,an.Head_Lines,an.Link_Description,an.Annoucement_Date,an.Annoucement_Time,an.Subject,an.Location,an.Category,an.Department order by id desc ";

			ResultSet rsgetOrganisationData = ad
					.selectQuery(getOrganisationData);
			LinkedList listOfOrganisationData = new LinkedList();
			int count1 = 0;
			while (rsgetOrganisationData.next()) {
				MainForm announcentForm = new MainForm();

				announcentForm.setId(rsgetOrganisationData.getInt("id"));
				announcentForm.setHeadLines(rsgetOrganisationData
						.getString("Head_Lines"));

				String contentDescription = rsgetOrganisationData
						.getString("Subject");

				contentDescription = contentDescription.replaceAll("\\<.*?\\>",
						"");
				if (contentDescription.length() > 280)
					contentDescription = contentDescription.substring(0, 280);
				announcentForm.setLinkDescription(contentDescription);
				announcentForm.setAnnoucementDate(rsgetOrganisationData
						.getString("Annoucement_Date"));
				announcentForm.setAnnouncementTime(rsgetOrganisationData
						.getString("Annoucement_Time"));
				listOfOrganisationData.add(announcentForm);
				count1++;
				if (count1 == 2)
					break;
			}

			request.setAttribute("OrganisationData", listOfOrganisationData);
			request.setAttribute("OrganisationData", listOfOrganisationData);

			String sql3 = "select * from product_list  ";
			ResultSet rs12 = ad.selectQuery(sql3);
			while (rs12.next()) {
				mainForm.setGifFile(rs12.getString("products_path"));
			}

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//all Pending request display in modal window updated on 25/12/2017
		/* <html:option value="Material Master">Material Master</html:option>
			<html:option value="Customer Master">Customer Master</html:option>
			<html:option value="Vendor Master">Vendor Master</html:option>
			<html:option value="Service Master">Service Master</html:option>
			<html:option value="Code Extention">Code Extention</html:option>
			<html:option value="Leave">Leave</html:option>	
			<html:option value="On Duty">On Duty</html:option>
			<html:option value="Travel">Travel Request</html:option> 
			<html:option value="Permission">Permission</html:option>
			<html:option value="IT Request">IT Request</html:option>
			<html:option value="Conference">Conference Room</html:option>
			<html:option value="VC Booking">VC Room</html:option>
			<html:option value="IT Sap Request">Sap Issues</html:option>
			<html:option value="Comp-Off/OT">Comp-Off</html:option>
			<html:option value="OverTime">OverTime</html:option>*/
		ArrayList applist = new ArrayList();
		String pend="select count(Req_type) as id,req_type from all_Request where Pending_Approver='"+user.getEmployeeNo()+"' and req_status='Pending' AND REQ_TYPE NOT IN('Cancel Leave','Cancel Comp-Off','Cancel On Duty','Cancel OT','Cancel OT','Cancel Permission') group by Req_Type";
		ResultSet dd=ad.selectQuery(pend);
		try {
			while(dd.next())
			{
				MainForm app=new MainForm();	
				/*app.setReq_id(dd.getString("Req_Id"));*/
				app.setReq_type(dd.getString("Req_Type"));
				app.setId(dd.getInt("id"));
				/*app.setRequest_name(dd.getString("Requester_Name"));
				app.setReq_date(dd.getString("Req_Date"));
				app.setLast_approver(dd.getString("Last_Approver"));*/
				applist.add(app);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(applist.size()>0)
		{
			request.setAttribute("getlist", applist);
			
		}


		return mapping.findForward("displayAnnouncement");
	}
	
	public ActionForward forcelogout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpServletRequest httpServletRequest=(HttpServletRequest)request;
		HttpServletResponse httpServletResponse=(HttpServletResponse)response;
		HttpSession session = httpServletRequest.getSession();

		 
		 String empNo=request.getParameter("empno");
		 
		 MainForm loginForm=(MainForm)form;
		///update logout time
		
		String b="update users set EMicro_Login_Flag=0 where employeenumber='"+empNo+"' ";
		
		int yd=ad.SqlExecuteUpdate(b);
		Date dNow1 = new Date( );
		 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
		String dateNow1 = ft1.format(dNow1);
		
		
		String updateUseStatus1="update Login_History set Logout_Time='"+dateNow1+"',Logout_Flag='Force Logout' where  employeenumber='"+empNo+"' and prefix='EMicro' and Logout_Time is null ";
		int j = ad.SqlExecuteUpdate(updateUseStatus1);
		System.out.println(session.getId());
		
		loginForm.setStatusMessage("Previous Session Cleared");
		if(j>0)
		session.invalidate();	
		return mapping.findForward("displayLoginPage");
	}
	
	public ActionForward forcelogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		 HttpSession session=request.getSession();		
		 String username = (String)session.getAttribute("UserName");
		 UserInfo user=(UserInfo)session.getAttribute("user");
		 

		 
	
		///update logout time
		
		String b="update users set EMicro_Login_Flag=0 where employeenumber='"+user.getEmployeeNo()+"' ";
		int yd=ad.SqlExecuteUpdate(b);
		
		
		Date dNow1 = new Date( );
		 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
		String dateNow1 = ft1.format(dNow1);
		String id="";
		String data="select * from Login_History where employeenumber='"+user.getEmployeeNo()+"' and prefix='EMicro' order by id desc";
		ResultSet rs22=ad.selectQuery(data);
		
			try {
				if(rs22.next()) {
				id=rs22.getString("id");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		String updateUseStatus1="update Login_History set Logout_Time='"+dateNow1+"' where id='"+id+"' and employeenumber='"+user.getEmployeeNo()+"' ";
		ad.SqlExecuteUpdate(updateUseStatus1);
	
		System.out.println(session.getId());
		session.invalidate();
		
		
		

		
		return mapping.findForward("displayLoginPage");
		
	}
	
	private String encryptdisha1(String password) {
		 int p = 11, q = 13;
      double n, z, d, e;
      d = e = 0;
      String encryptpassword = "";

      n = p * q;
      z = (p - 1) * (q - 1);

      //find value of d
      for (int i = 2; i < z; i++)
      {
          if ((z % i) != 0)
          {
              d = i;
              break;
          }
      }

      //find value of e
      for (int i = 1; i < z; i++)
      {
          if (((d * i) - 1) % z == 0)
          {
              e = i;
              break;
          }
      }

      //encrypt given string
      char[] chararray = password.toCharArray();
      for (int i = 0; i < chararray.length; i++)
      {
          double ascii = (byte)chararray[i];
          short ciphertext = (short) (Math.pow(ascii, e) % n);

          encryptpassword +=ciphertext;

          System.out.println(ciphertext+"-"+chararray[i]);
      }
      return (encryptpassword);

	} 
	
	private String encryptdisha(String password) {
		 int p = 11, q = 13;
       double n, z, d, e;
       d = e = 0;
       String encryptpassword = "";

       n = p * q;
       z = (p - 1) * (q - 1);

       //find value of d
       for (int i = 2; i < z; i++)
       {
           if ((z % i) != 0)
           {
               d = i;
               break;
           }
       }

       //find value of e
       for (int i = 1; i < z; i++)
       {
           if (((d * i) - 1) % z == 0)
           {
               e = i;
               break;
           }
       }

       //encrypt given string
       char[] chararray = password.toCharArray();
       for (int i = 0; i < chararray.length; i++)
       {
           double ascii = (byte)chararray[i];
           short ciphertext = (short) (Math.pow(ascii, e) % n);
           //encryptpassword += Convert.ToChar(ciphertext).ToString();
           if(chararray[i]=='1')
           encryptpassword += 77;
           else
           encryptpassword +=ciphertext;
               
           System.out.println(ciphertext+"-"+chararray[i]);
       }
       return (encryptpassword);
		
	}
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MainForm mainForm = (MainForm) form;// TODO Auto-generated method stub
		
		HttpSession session=request.getSession();
		synchronized (this) {
			
		
		try{
			
			/*///Mass update 
			LinkedList list=new LinkedList();
			String a="Select * from users order by id";
			ResultSet s=ad.selectQuery(a);
			while(s.next())
			{
				LoginForm log=new LoginForm();
				log.setUserName(s.getString("employeenumber"));
				log.setPassword(s.getString("password"));
				list.add(log);
			}
			 Iterator updatepwd=list.iterator();
	
				
		int i=0;
			Connection con=null;
			con=ConnectionFactory.getConnection();
			PreparedStatement	pst=con.prepareStatement("update users set password=? where employeenumber =? ");
			while(updatepwd.hasNext())
			{
				LoginForm pwd=(LoginForm)updatepwd.next();
				
				
				System.out.println(pwd.getUserName()+" "+encrypt(pwd.getPassword().trim()));		
				
			
			i++;
			}
			*/
			
			int pwdExpDays=mainForm.getPwdExpDays();
			mainForm.setPwdExpDays(pwdExpDays);
			
			ResultSet rs=ad.selectQuery("select * from links where status is null");
			LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			
			ArrayList a1=new ArrayList();
			
			 while(rs.next()){
				 
				 hm.put(rs.getString("link_path")+"?method="+rs.getString("method")+"&id="+rs.getString("link_name"), rs.getString("link_name"));	 
			}
			 
			 session.setAttribute("URL", hm);
			 session.setAttribute("LINKNAME", a1);
			 }catch (Exception e){
				 e.printStackTrace();
		  System.out.println("Exception caught ="+e.getMessage());
		  }
			 	
				
				String id=request.getParameter("id"); 
				String userName=mainForm.getUserName();
				String password=mainForm.getPassword();
			if(userName==null||password==null)
			{
				UserInfo user1=(UserInfo)session.getAttribute("user");
			userName=user1.getUserName();
			
				password=user1.getPassword();
			}
			
			
			String sql13 = "select staffcat from emp_official_info  where pernr='"+userName+"'" ;
			ResultSet rs12a=ad.selectQuery(sql13);
			try {
				if(rs12a.next())
				{
					if(!rs12a.getString(1).equalsIgnoreCase("2"))
					{
						try {
							mainForm.setStaffCat(rs12a.getString(1));
							mainForm.setPassword(encrypt(password));
						} catch (UnsupportedEncodingException
								| GeneralSecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
						{
						    mainForm.setStaffCat(rs12a.getString(1));
							mainForm.setPassword(encryptdisha(password));
						
							//mainForm.setPassword(encryptdisha(password));
						}
					
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
				
				
				
				
				  /*if(request.getParameter("captcha") != null && session.getAttribute("captchaStr") != null){
					  if(!session.getAttribute("captchaStr").equals(request.getParameter("captcha"))){

							
							mainForm.setStatusMessage("CAPTCHA was incorrect. Try again");
							
							mainForm.setMessage2("/EMicro Files/images/EmpPhotos/image2.jpg?time="+new Date().getTime());
							request.setAttribute("loginArea", "show");
							request.setAttribute("personalizeArea", "");
							
							String sql11="select * from archieves where link_name='Login CMS' and module='Main' and status='null'";
							
							ResultSet rs11=ad.selectQuery(sql11);
							
							try{
								while(rs11.next()) {
									 
									mainForm.setContentDescription(rs11.getString("content_description"));
								
								}
							}catch(SQLException se){
								se.printStackTrace();
							}
							return mapping.findForward("displayLoginPage");
						
					  }
					 }*/
				
				String userGroup="";
				UserInfo user=ad.validate(mainForm,request,response);
				if(user==null)
				{	
					if(mainForm.getStaffCat().equalsIgnoreCase("2"))
					{
					mainForm.setPassword(encryptdisha1(password));
					user=ad.validate(mainForm,request,response);
					}
				} 
				
				String sql12="";
				
				sql12 = "select * from users where username='"+userName+"'" +
						" and password='"+mainForm.getPassword()+"' and status='1' and activated='On'";
			
			
				ResultSet rs12=ad.selectQuery(sql12);
				int loc_count=0;
				int loc_Minutes=0;
				//get lockcount and mins from table
				
				String lo="select * from LOCKOUT_MASTER where Application='EMicro'";
				ResultSet loc1=ad.selectQuery(lo);
				try {
					if(loc1.next())
					{
						loc_count=loc1.getInt("Lockout_Count");
						loc_Minutes=loc1.getInt("Lockout_Release_time_minutes");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
		 		String yb="update users set EMicro_Locked_Time=null,EMicro_Locked_Flag=0 where employeenumber='"+userName+"' and EMicro_locked_flag=1 and datediff(mi,EMicro_Locked_Time,getdate())>="+loc_Minutes+"";
				int yud=ad.SqlExecuteUpdate(yb);
				
				if(user!=null)
				{
					
					
					  String locked="";
					  String logged_in="";
					  String logged_in_time="";
					  String logged_in_ip="";
						String check="select *,CONVERT(varchar(10),EMicro_Login_Time,103)+' '+ CONVERT(varchar(5),EMicro_Login_Time,108) as EMicro_Login_Time1 from users where employeenumber='"+mainForm.getUserName()+"'";
						ResultSet qqqq=ad.selectQuery(check);
						try {
							if(qqqq.next())
							{
								
								
								locked=qqqq.getString("EMicro_Locked_Flag");
								logged_in=qqqq.getString("EMicro_Login_Flag");
								logged_in_time=qqqq.getString("EMicro_Login_time1");
								logged_in_ip=qqqq.getString("EMicro_IP_address");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						if(locked.equalsIgnoreCase("0"))
						{
						
					int cou=0;
					String ipaddress="";
					
					
					 ipaddress = request.getHeader("X-FORWARDED-FOR");  
					if (ipaddress == null) {  
						ipaddress = request.getRemoteAddr();  
					}
					
					
					try {
						System.out.println(InetAddress.getLocalHost().getHostName());
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					
					
					String qo="select count(*) as a from Login_History where employeenumber='"+mainForm.getUserName()+"' and Attempt_time>DateADD(mi, -"+loc_Minutes+", getdate()) and Attempt_time>(select max( Login_Time) from Login_History where employeenumber='"+mainForm.getUserName()+"' and convert(date,Login_Time)=convert(date,getdate())) and prefix='EMicro' and convert(date,Login_Time)=convert(date,getdate())";
					ResultSet qq=ad.selectQuery(qo);
					try {
						if(qq.next())
						{
						cou=qq.getInt(1);	
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(cou<(loc_count-1))
					
					{
						Date dNow1 = new Date( );
						
						
						String b="update users set EMicro_Login_Time=getdate(),EMicro_Login_Flag=1,EMicro_IP_address='"+ipaddress+"' where employeenumber='"+mainForm.getUserName()+"' ";
						int yd=ad.SqlExecuteUpdate(b);
						
						SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm ");
						String dateNow1 = ft1.format(dNow1);
						String insertUserHistory="insert into Login_History(employeenumber,Login_Time,Ip_Address,prefix,Session_ID)values('"+mainForm.getUserName()+"','"+dateNow1+"','"+ipaddress+"','EMicro','"+session.getId()+"')";
						int y=ad.SqlExecuteUpdate(insertUserHistory);
						
						
						
						

						
						///update login flag after successfull login
						
						request.setAttribute("loginArea", "show");
						request.setAttribute("personalizeArea", "");
						
						/*String sql11="select * from archieves where link_name='Login CMS' and module='Main' and status='null'";
						
						ResultSet rs11=ad.selectQuery(sql11);
						
						try{
							while(rs11.next()) {
								 
								mainForm.setContentDescription(rs11.getString("content_description"));
							
							}
						}catch(SQLException se){
							se.printStackTrace();
						}*/
						
						
		
		
					}
					else
					{
						
						
						//update user locked time and flag
						
						String b="update users set EMicro_Locked_Time=getdate(),EMicro_Locked_Flag=1 where employeenumber='"+mainForm.getUserName()+"' and EMicro_Locked_Flag=0";
						int yd=ad.SqlExecuteUpdate(b);
						
						int time=0;
						String qqo="select datediff(mi,getdate(),dateadd(mi,"+loc_Minutes+",max(EMicro_Locked_Time))) as timeleft from users where employeenumber='"+mainForm.getUserName()+"' ";
						ResultSet qqq=ad.selectQuery(qqo);
						try {
							if(qqq.next())
							{
								time=qqq.getInt(1);	
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						request.setAttribute("loginArea", "show");
						request.setAttribute("personalizeArea", "");
						
						/*String sql11="select * from archieves where link_name='Login CMS' and module='Main' and status='null'";
						
						ResultSet rs11=ad.selectQuery(sql11);
						
						try{
							while(rs11.next()) {
								 
								mainForm.setContentDescription(rs11.getString("content_description"));
							
							}
						}catch(SQLException se){
							se.printStackTrace();
						}
						*/
						mainForm.setStatusMessage("Locked Out..Please Try After "+time+" Minutes");
						return mapping.findForward("displayLoginPage");
					}
						}
						else
						{
							int time=0;
							String qqo="select datediff(mi,getdate(),dateadd(mi,"+loc_Minutes+",max(EMicro_Locked_Time))) as timeleft from users where employeenumber='"+mainForm.getUserName()+"' ";
							ResultSet gd=ad.selectQuery(qqo);
							try {
								if(gd.next())
								{
									time=gd.getInt(1);	
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						
							request.setAttribute("loginArea", "show");
							request.setAttribute("personalizeArea", "");
							
							/*String sql11="select * from archieves where link_name='Login CMS' and module='Main' and status='null'";
							
							ResultSet rs11=ad.selectQuery(sql11);
							
							try{
								while(rs11.next()) {
									 
									mainForm.setContentDescription(rs11.getString("content_description"));
								
								}
							}catch(SQLException se){
								se.printStackTrace();
							}*/
							
							mainForm.setStatusMessage("Locked Out..Please Try After "+time+" Minutes");
							return mapping.findForward("displayLoginPage");
						}
					
					
				
				}
				if(user==null)
				
				{
					
					
					
					
				    String locked="";
					String check="select * from users where employeenumber='"+mainForm.getUserName()+"'";
					ResultSet qqq=ad.selectQuery(check);
					try {
						while(qqq.next())
						{
							locked=qqq.getString("EMicro_Locked_Flag");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					if(locked.equalsIgnoreCase("0"))
					{
						
						
						int cou=0;
						String qo="select count(*) as a from Login_History where employeenumber='"+mainForm.getUserName()+"' and Attempt_time>DateADD(mi, -"+loc_Minutes+", getdate()) and Attempt_time>(select max( Login_Time) from Login_History where employeenumber='"+mainForm.getUserName()+"') and prefix='EMicro'";
						ResultSet qq=ad.selectQuery(qo);
						try {
							if(qq.next())
							{
							cou=qq.getInt(1);	
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(cou<(loc_count-1))
						
						{
							
						}
						else
						{
							String b="update users set EMicro_Locked_Time=getdate(),EMicro_Locked_Flag=1 where employeenumber='"+mainForm.getUserName()+"' and EMicro_Locked_Flag=0";
							int yd=ad.SqlExecuteUpdate(b);
						}
						
					String ipaddress="";
						 ipaddress = request.getHeader("X-FORWARDED-FOR");  
					if (ipaddress == null) {  
						ipaddress = request.getRemoteAddr();  
					}
					
					
					Date dNow1 = new Date( );
					 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm ");
					String dateNow1 = ft1.format(dNow1);
					
					
					
					String insertUserHistory="insert into Login_History(employeenumber,Ip_Address,Attempt_time,prefix)values('"+mainForm.getUserName()+"','"+ipaddress+"','"+dateNow1+"','EMicro')";
					int y=ad.SqlExecuteUpdate(insertUserHistory);
					request.setAttribute("loginArea", "show");
					request.setAttribute("personalizeArea", "");
					
					/*String sql11="select * from archieves where link_name='Login CMS' and module='Main' and status='null'";
					
					ResultSet rs11=ad.selectQuery(sql11);
					
					try{
						while(rs11.next()) {
							 
							mainForm.setContentDescription(rs11.getString("content_description"));
						
						}
					}catch(SQLException se){
						se.printStackTrace();
					}*/
					
					mainForm.setStatusMessage("Username or Password not Matched");
					return mapping.findForward("displayLoginPage");
					}
					else
					{
						int time=0;
						String qqo="select datediff(mi,getdate(),dateadd(mi,"+loc_Minutes+",max(EMicro_Locked_Time))) as timeleft from users where employeenumber='"+mainForm.getUserName()+"' ";
						ResultSet gd=ad.selectQuery(qqo);
						try {
							if(gd.next())
							{
								time=gd.getInt(1);	
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					
						
						request.setAttribute("loginArea", "show");
						request.setAttribute("personalizeArea", "");
						
						/*String sql11="select * from archieves where link_name='Login CMS' and module='Main' and status='null'";
						
						ResultSet rs11=ad.selectQuery(sql11);
						
						try{
							while(rs11.next()) {
								 
								mainForm.setContentDescription(rs11.getString("content_description"));
							
							}
						}catch(SQLException se){
							se.printStackTrace();
						}*/
						mainForm.setStatusMessage("Locked Out..Please Try After "+time+" Minutes");
						return mapping.findForward("displayLoginPage");
					}
					
				
				}
				
				try{
					
					if(rs12.next()){
						userGroup=rs12.getString("groupname");
						String employeenumber = rs12.getString("employeenumber");
						session.setAttribute("employeenumber", employeenumber);
						int loginCount=rs12.getInt("loginCount");
						//first time login code
						if(loginCount==0){
							mainForm.setUserName(userName);
							mainForm.setOldPassword("");
							request.setAttribute("loginArea", "");
							request.setAttribute("saveButton", "saveButton");
							request.setAttribute("personalizeArea", "show");
							return mapping.findForward("displayLoginPage");
						}
						String pwdExpDt=rs12.getString("passwordexpirydate");
						
						//update user status
						try{
							Date dNow = new Date( );
							 SimpleDateFormat ft = new SimpleDateFormat ("HH:mm:ss ");
							String dateNow = ft.format(dNow);
							String ipaddress="";
							
							
							 ipaddress = request.getHeader("X-FORWARDED-FOR");  
							if (ipaddress == null) {  
								ipaddress = request.getRemoteAddr();  
							}
							
							Date dNow1 = new Date( );
							 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm ");
							String dateNow1 = ft1.format(dNow1);
							String insertUserHistory="insert into Login_History(employeenumber,Login_Time,Ip_Address)values('"+user.getEmployeeNo()+"','"+dateNow1+"','"+ipaddress+"')";
							
							int i=ad.SqlExecuteUpdate(insertUserHistory);
						String updateUseStatus="update users set use_status=1,Ip_Address='"+ipaddress+"',Login_Time='"+dateNow+"' where employeenumber='"+user.getEmployeeNo()+"'";
						ad.SqlExecuteUpdate(updateUseStatus);
						}catch(Exception e){
							e.printStackTrace();
						}
						//Display the date now:
						 GregorianCalendar calendar = new GregorianCalendar();
			            Date now = calendar.getTime();
			            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			            String formattedDate = sdf.format(now);
			            String currenDt=formattedDate;
			           
			        	int noofDays=0;
						try{
							String getNoOfDays="select COUNT(*) from ExplodeDates('"+currenDt+"','"+pwdExpDt+"')";
							ResultSet rsNofDays=ad.selectQuery(getNoOfDays);
							while(rsNofDays.next())
							{
								noofDays=rsNofDays.getInt(1);
								if(noofDays==1)
								noofDays=noofDays-1;
								
								
								user.setPwdExpDays(noofDays);
							
							}
							
						}catch (Exception e) {
						e.printStackTrace();
						}
			        	
			        	
						String pwdExpiryStatus=user.getPasswordExpiryDate();
						if(noofDays==0)
						{
							mainForm.setMessage("Password expired");
							request.setAttribute("loginArea", "show");
							request.setAttribute("personalizeArea", "");
							
							/*String sql11="select * from archieves where link_name='Login CMS' and module='Main' and status='null'";
							
							ResultSet rs11=ad.selectQuery(sql11);
							
							try{
								while(rs11.next()) {
									mainForm.setContentDescription(rs11.getString("content_description"));
								}
							}catch(SQLException se){
								se.printStackTrace();
							}*/
							return mapping.findForward("displayLoginPage");
						}
						
					}else{
						
						mainForm.setMessage("Username and Password are incorrect please check");
						request.setAttribute("loginArea", "show");
						request.setAttribute("personalizeArea", "");
						
						/*String sql11="select * from archieves where link_name='Login CMS' and module='Main' and status='null'";
						
						ResultSet rs11=ad.selectQuery(sql11);
						
						try{
							while(rs11.next()) {
								 
								mainForm.setContentDescription(rs11.getString("content_description"));
							
							}
						}catch(SQLException se){
							se.printStackTrace();
						}*/
						return mapping.findForward("displayLoginPage");
					}
				}catch(SQLException se){
					se.printStackTrace();
				}
				
				String sql01="select count(*) from ESS_Approvers where employeeNumber='"+userName+"'";
				int count11=0;
				ResultSet rs00=ad.selectQuery(sql01);
				
				try {
					while(rs00.next())
					{
						count11=rs00.getInt(1);
						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(count11==0)
				{
					mainForm.setMessage("Approvers not defined for you,Please contact HR");
					request.setAttribute("loginArea", "show");
					/*session.invalidate();*/
					return mapping.findForward("displayLoginPage");
				}
				session.setAttribute("user", user);
				
				UserInfo user1=(UserInfo)session.getAttribute("user");
				request.setAttribute("MenuIcon", "Home");
				if(userName==null){
					return mapping.findForward("display");
				}else{
					String userType=user1.getUserType(); 
					
				
					
					// display Announcemnt
						try{
							//emp photos
							Date dNow = new Date( );
							 SimpleDateFormat ft = new SimpleDateFormat ("MM/dd");
							String dateNow = ft.format(dNow);
							System.out.println("Current Date ="+dateNow);
							String empNameDept="";
					LinkedList empBirthDayList=new LinkedList();
					
					String getEmpDetails="select e.PERNR,CONVERT(VARCHAR(5), DOB, 101)as  date,u.Emp_Photo,e.SEX,(e.title+'. '+e.EMP_FULLNAME) as EMP_FULLNAME,dept.DPTSTXT,dsg.DSGSTXT,e.SEX  from " +
							" users as u,emp_official_info as e,DEPARTMENT as dept,Location as l,DESIGNATION  as dsg where  '"+dateNow+"'=CONVERT(VARCHAR(5), e.DOB, 101)   and " +
									" u.employeenumber=e.PERNR and l.LOCID='"+user.getPlantId()+"' and e.STAFFCAT!=2 and dsg.DSGSTXT !='Driver' and  e.LOCID=l.LOCATION_CODE and u.employeenumber=e.PERNR and  e.DPTID=dept.DPTID and e.DSGID=dsg.DSGID and e.Active='1' order by e.PERNR";
						ResultSet rs=ad.selectQuery(getEmpDetails);
					
					while(rs.next())
					{
						MainForm mainForm2=new MainForm();
						mainForm2.setEmployeeNo(rs.getString("PERNR"));
						//set birthday image
						if(user.getEmployeeNo().equalsIgnoreCase(rs.getString("PERNR"))){
							request.setAttribute("birthdayImage", "birthdayImage");
                        }
						empNameDept=empNameDept+rs.getString("EMP_FULLNAME")+" - "+rs.getString("DSGSTXT")+" , ";
						empBirthDayList.add(mainForm2);
					}
					if(empBirthDayList.size()>0)	
					{
						int j=empNameDept.length();
					empNameDept=empNameDept.substring(0,(empNameDept.length()-2));
					mainForm.setEmpName(empNameDept);
				request.setAttribute("empBirthDayList", empBirthDayList);
					}
						
					
					
					//all Pending request display in modal window updated on 25/12/2017
					ArrayList applist = new ArrayList();
					String pend="select * from all_Request where pending_approver='"+user.getEmployeeNo()+"'";
					ResultSet dd=ad.selectQuery(pend);
					while(dd.next())
					{
						MainForm app=new MainForm();	
						app.setReq_id(dd.getString("Req_Id"));
						app.setReq_type(dd.getString("Req_Type"));
						app.setRequest_name(dd.getString("Requester_Name"));
						app.setReq_date(dd.getString("Req_Date"));
						app.setLast_approver(dd.getString("Last_Approver"));
						applist.add(app);
					}
					
					if(applist.size()>0)
					{
						request.setAttribute("getlist", applist);
						
					}
					
					
						
						String getAnnouncemtnData="select * from announcement1 where Head_Lines='HEADLINES'  ";
						
						ResultSet rsAnnouncemtnData=ad.selectQuery(getAnnouncemtnData);
						LinkedList listOfData=new LinkedList();
						int count=0;
						while(rsAnnouncemtnData.next()){
							
							MainForm announcentForm=new MainForm();	
							
							announcentForm.setId(rsAnnouncemtnData.getInt("id"));
							announcentForm.setHeadLines(rsAnnouncemtnData.getString("Head_Lines"));		
							String contentDescription=rsAnnouncemtnData.getString("Link_Description");
							contentDescription=contentDescription.substring(0,140);
							
							announcentForm.setLinkDescription(contentDescription);
							announcentForm.setAnnoucementDate(rsAnnouncemtnData.getString("Annoucement_Date"));
							announcentForm.setAnnouncementTime(rsAnnouncemtnData.getString("Annoucement_Time"));
							listOfData.add(announcentForm);
							count++;
							if(count==2)	break;
						}	
							request.setAttribute("HeadLinesData",listOfData);
							String getOrganisationData="select * from announcement1 where Head_Lines='ORGANIZATION ANNOUNCEMENTS' ";
							
							ResultSet rsgetOrganisationData=ad.selectQuery(getOrganisationData);
							LinkedList listOfOrganisationData=new LinkedList();
							int count1=0;
							while(rsgetOrganisationData.next()){
								MainForm announcentForm=new MainForm();	
								announcentForm.setId(rsgetOrganisationData.getInt("id"));
								announcentForm.setHeadLines(rsgetOrganisationData.getString("Head_Lines"));	
								String contentDescription=rsgetOrganisationData.getString("Link_Description");
								contentDescription=contentDescription.substring(0,140);
								announcentForm.setLinkDescription(contentDescription);
								announcentForm.setAnnoucementDate(rsgetOrganisationData.getString("Annoucement_Date"));
								announcentForm.setAnnouncementTime(rsgetOrganisationData.getString("Annoucement_Time"));
								listOfOrganisationData.add(announcentForm);
								count1++;
								if(count1==2)	break;
							}	
					       request.setAttribute("OrganisationData",listOfOrganisationData);
					}catch (Exception e) {
						e.printStackTrace();
					}
					if(userName==null){
						
						return mapping.findForward("display");
					}else{
						
					
					if(userType.equalsIgnoreCase("temp"))
					{		 
						String sql="";
					
							sql = "update users set lastlogindate='"+EMicroUtils.getCurrentDate()+"' " +
									"where username='"+userName+"'" +
									" and password='"+mainForm.getPassword()+"'";
						
						
						ad.SqlExecuteUpdate(sql);
						
					return mapping.findForward("home");
					}
					else
					{
						String sql="";
						
							sql = "update users set lastlogindate='"+EMicroUtils.getCurrentDate()+"' " +
							"where username='"+userName+"'" +
							" and password='"+mainForm.getPassword()+"'";
						
						ad.SqlExecuteUpdate(sql);
						
						
						//ALERT
						
						String loc = "";
						int cat = 0;
						int dept = 0;
						String a = "select * from emp_official_info where pernr='"
								+ user.getEmployeeNo() + "'";
						ResultSet rs = ad.selectQuery(a);
						try {
							while (rs.next()) {
								loc = rs.getString("LOCID");
								cat = rs.getInt("STAFFCAT");
								dept = rs.getInt("DPTID");

							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						String getAnnouncemtnData = "select * from ALERT_BOX a,emp_official_info e where convert (nvarchar(16),GETDATE(),20) between Start_date and End_date and Status='1' and e.LOCID like '%"+loc+"%' and  e.STAFFCAT like '%"+cat+"%' and e.DPTID like '%"+dept+"%' and a.Empnos like '%"+user.getEmployeeNo()+"%' and e.PERNR='"+user.getEmployeeNo()+"'";

						ResultSet rsAnnouncemtnData = ad.selectQuery(getAnnouncemtnData);
						try {
							while(rsAnnouncemtnData.next())
							{
					
								mainForm.setId(rsAnnouncemtnData.getInt("id"));
								mainForm.setHeadLines(rsAnnouncemtnData.getString("Content_Heading"));
								mainForm.setAlertheader(rsAnnouncemtnData.getString("Content_subject"));
								String contentDescription = rsAnnouncemtnData.getString("Content_description");																		
								mainForm.setLinkDescription(contentDescription);
								request.setAttribute("ContentData", contentDescription);
								request.setAttribute("Alert", "Alert");
								
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						return mapping.findForward("home");
				    }
					
					}
		
	}
		}
	
}
	
	
	public ActionForward display1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MainForm mainForm = (MainForm) form;// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		String id=request.getParameter("id");
		user.setPwdExpDays(0);
		
		
		try{
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		    SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
		    Date date = format1.parse("1999-01-05");
		    System.out.println(format2.format(date));
		    
			ResultSet rs=ad.selectQuery("select * from links where status is null");
			LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			
			ArrayList a1=new ArrayList();
			
			while(rs.next()){
				 hm.put(rs.getString("link_path")+"?method="+rs.getString("method")+"&id="+rs.getString("link_name"), rs.getString("link_name"));	 
			}
			 
			 session.setAttribute("URL", hm);
			 session.setAttribute("LINKNAME", a1);
			 }catch (Exception e){
		  System.out.println("Exception caught ="+e.getMessage());
		  }
				
				
				String userName=mainForm.getUserName();
				String password=mainForm.getPassword();
				
				
				String sql12="select * from users where username='"+userName+"'" +
						" and password='"+password+"' and status='1' and activated='On'";
				
				ResultSet rs12=ad.selectQuery(sql12);
					
					try{
						
						
						
						
						String getAnnouncemtnData="select * from announcement1 where Head_Lines='HEADLINES' ";
						
						ResultSet rsAnnouncemtnData=ad.selectQuery(getAnnouncemtnData);
						LinkedList listOfData=new LinkedList();
						int count=0;
						while(rsAnnouncemtnData.next()){
							MainForm announcentForm=new MainForm();	
							
							announcentForm.setId(rsAnnouncemtnData.getInt("id"));
							announcentForm.setHeadLines(rsAnnouncemtnData.getString("Head_Lines"));		
							String contentDescription=rsAnnouncemtnData.getString("Link_Description");
							contentDescription=contentDescription.substring(3,140);
							
							announcentForm.setLinkDescription(contentDescription);
							announcentForm.setAnnoucementDate(rsAnnouncemtnData.getString("Annoucement_Date"));
							announcentForm.setAnnouncementTime(rsAnnouncemtnData.getString("Annoucement_Time"));
							listOfData.add(announcentForm);
							count++;
							if(count==2)	break;
						}	
								
							request.setAttribute("HeadLinesData",listOfData);
				       		
				       		
							String getOrganisationData="select * from announcement1 where Head_Lines='ORGANIZATION ANNOUNCEMENTS'";
							
							ResultSet rsgetOrganisationData=ad.selectQuery(getOrganisationData);
							LinkedList listOfOrganisationData=new LinkedList();
							int count1=0;
							while(rsgetOrganisationData.next()){
								MainForm announcentForm=new MainForm();	
								
								announcentForm.setId(rsgetOrganisationData.getInt("id"));
								announcentForm.setHeadLines(rsgetOrganisationData.getString("Head_Lines"));	
								
								String contentDescription=rsgetOrganisationData.getString("Link_Description");
								contentDescription=contentDescription.substring(3,140);
								announcentForm.setLinkDescription(contentDescription);
								announcentForm.setAnnoucementDate(rsgetOrganisationData.getString("Annoucement_Date"));
								announcentForm.setAnnouncementTime(rsgetOrganisationData.getString("Annoucement_Time"));
								listOfOrganisationData.add(announcentForm);
								count1++;
								if(count1==2)	break;
							}	
						   	
					       request.setAttribute("OrganisationData",listOfOrganisationData);
					       
					       
					       
					       Date dNow = new Date( );
							 SimpleDateFormat ft = new SimpleDateFormat ("MM/dd");
							String dateNow = ft.format(dNow);
							System.out.println("Current Date ="+dateNow);
							LinkedList<MainForm> empBirthDayList=new LinkedList<MainForm>();
							String empNameDept="";
							String getEmpDetails="select e.PERNR,CONVERT(VARCHAR(5), DOB, 101)as  date,u.Emp_Photo,e.SEX,(e.title+'. '+e.EMP_FULLNAME) as EMP_FULLNAME,dept.DPTSTXT,dsg.DSGSTXT,e.SEX  from " +
									" users as u,emp_official_info as e,DEPARTMENT as dept,Location as l,DESIGNATION  as dsg where  '"+dateNow+"'=CONVERT(VARCHAR(5), e.DOB, 101)   and " +
											" u.employeenumber=e.PERNR and l.LOCID='"+user.getPlantId()+"' and e.STAFFCAT!=2 and dsg.DSGSTXT !='Driver' and  e.LOCID=l.LOCATION_CODE and u.employeenumber=e.PERNR and  e.DPTID=dept.DPTID and e.DSGID=dsg.DSGID and e.Active='1' order by e.PERNR";
								ResultSet rs=ad.selectQuery(getEmpDetails);
					try{
					while(rs.next())
					{
						MainForm mainForm2=new MainForm();
						mainForm2.setEmployeeNo(rs.getString("PERNR"));
					
						empNameDept=empNameDept+rs.getString("EMP_FULLNAME")+" - "+rs.getString("DSGSTXT")+" , ";
						empBirthDayList.add(mainForm2);
					}
						}catch (Exception e) {
								e.printStackTrace();
							}
							
							
							String userID=user.getEmployeeNo();
							
							if(empBirthDayList.size()>0)
							{
								int j=empNameDept.length();
								empNameDept=empNameDept.substring(0,(empNameDept.length()-2));
								mainForm.setEmpName(empNameDept);
							request.setAttribute("empBirthDayList", empBirthDayList);	
							}
					       
					
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					request.setAttribute("MenuIcon", id);
					return mapping.findForward("display");
			}
	
	public ActionForward getContentDescription(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MainForm mainForm = (MainForm) form;// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		    
		        
				String id=request.getParameter("id"); 
				String userName=mainForm.getUserName();
				String password=mainForm.getPassword();
					
					// display Announcemnt
					
					try{
						
						String date="";
						String contentId ="";
						contentId=request.getParameter("ContentId");
						String getAnnouncemtnData="select * from announcement1 where id='"+contentId+"'";
						
						ResultSet rsAnnouncemtnData=ad.selectQuery(getAnnouncemtnData);
						String contentDescription="";
						String headLinesType="";
						while(rsAnnouncemtnData.next()){
							
							
							mainForm.setId(rsAnnouncemtnData.getInt("id"));
							headLinesType=rsAnnouncemtnData.getString("Head_Lines");
							mainForm.setHeadLines(rsAnnouncemtnData.getString("Head_Lines"));		
						    contentDescription=rsAnnouncemtnData.getString("Link_Description");
							
							mainForm.setLinkDescription(contentDescription);
							mainForm.setAnnoucementDate(EMicroUtils.display1(rsAnnouncemtnData.getDate("Annoucement_Date")));
							
							mainForm.setAnnouncementTime(rsAnnouncemtnData.getString("Annoucement_Time"));
							
							
							
							String video="Select * from announcment_videos where HeadLines_Type='"+headLinesType+"' and HeadLines_Date='"+rsAnnouncemtnData.getDate("Annoucement_Date")+"' and HeadLines_Time='"+rsAnnouncemtnData.getString("Annoucement_Time")+"' ";
							ResultSet rs1=ad.selectQuery(video);
							
							while(rs1.next())
							{
								String videoName=rs1.getString("Video_Name");
								if(!(videoName.equalsIgnoreCase("")))
								{
								String a[]=videoName.split("/");
								videoName=a[a.length-1];
								mainForm.setManagementVideo(videoName);
								request.setAttribute("managementVideo", "managementVideo");
								}
								
							}
							
						}	
						request.setAttribute("ContentData", contentDescription);
						
						//all Pending request display in modal window updated on 25/12/2017
						ArrayList applist = new ArrayList();
						String pend="select * from all_Request where pending_approver='"+user.getEmployeeNo()+"'";
						ResultSet dd=ad.selectQuery(pend);
						while(dd.next())
						{
							MainForm app=new MainForm();	
							app.setReq_id(dd.getString("Req_Id"));
							app.setReq_type(dd.getString("Req_Type"));
							app.setRequest_name(dd.getString("Requester_Name"));
							app.setReq_date(dd.getString("Req_Date"));
							app.setLast_approver(dd.getString("Last_Approver"));
							applist.add(app);
						}
						
						if(applist.size()>0)
						{
							request.setAttribute("getlist", applist);
							
						}
						
						
						}catch (Exception e) {
						e.printStackTrace();
					}
					
					
					
	                return mapping.findForward("displayContentDescription");
	                }
	
	public ActionForward getMoreDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MainForm mainForm = (MainForm) form;// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		EssDao ad = new EssDao();
		try {
			ResultSet rs = ad
					.selectQuery("select * from links where status is null");
			LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

			ArrayList a1 = new ArrayList();

			while (rs.next()) {

				hm.put(rs.getString("link_path") + "?method="
						+ rs.getString("method") + "&id="
						+ rs.getString("link_name"), rs.getString("link_name"));
			}

			session.setAttribute("URL", hm);
			session.setAttribute("LINKNAME", a1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception caught =" + e.getMessage());
		}

		String id = request.getParameter("id");
		String userName = mainForm.getUserName();
		String password = mainForm.getPassword();

		String sql12 = "select * from users where username='" + userName + "'"
				+ " and password='" + password
				+ "' and status='1' and activated='On'";

		ResultSet rs12 = ad.selectQuery(sql12);

		// display Announcemnt

		try {
			int totalRecords = 0;
			int startRecord = 0;
			int endRecord = 0;
			String type = "";
			type = request.getParameter("Type");

			String loc = "";
			int cat = 0;
			int dept = 0;
			String a1 = "select * from emp_official_info where pernr='"
					+ user.getEmployeeNo() + "'";
			ResultSet rs = ad.selectQuery(a1);
			while (rs.next()) {
				loc = rs.getString("LOCID");
				cat = rs.getInt("STAFFCAT");
				dept = rs.getInt("DPTID");

			}

			String getCount = "select count(*) from announcement1 as an where Head_Lines='"
					+ type
					+ "' and  an.Location like '%"
					+ loc
					+ "%' and an.Category like '%"
					+ cat
					+ "%' and an.Department like '%" + dept + "%'";
			ResultSet rsCount = ad.selectQuery(getCount);
			while (rsCount.next()) {
				totalRecords = rsCount.getInt(1);
			}
			if (totalRecords >= 10) {
				mainForm.setTotalRecords(totalRecords);
				startRecord = 1;
				endRecord = 10;
				mainForm.setStartRecord(1);
				mainForm.setEndRecord(10);
				request.setAttribute("displayRecordNo", "displayRecordNo");
				request.setAttribute("nextButton", "nextButton");
			} else {
				startRecord = 1;
				endRecord = totalRecords;
				mainForm.setTotalRecords(totalRecords);
				mainForm.setStartRecord(1);
				mainForm.setEndRecord(totalRecords);
			}

			String getAnnouncemtnData = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY a.ID desc) AS RowNum,a.id,a.Head_Lines,a.Link_Description,"
					+ " convert(varchar,a.Annoucement_Date,6) as Annoucement_Date,a.Annoucement_Time,a.Subject from announcement1 as a where Head_Lines='"
					+ type
					+ "' and  a.Location like '%"
					+ loc
					+ "%' and a.Category like '%"
					+ cat
					+ "%' and a.Department like '%"
					+ dept
					+ "%') as  sub Where  sub.RowNum between 1 and 10";

			ResultSet rsAnnouncemtnData = ad.selectQuery(getAnnouncemtnData);

			String headLinesType = "";
			LinkedList listOfOrganisationData = new LinkedList();

			while (rsAnnouncemtnData.next()) {
				MainForm announcentForm = new MainForm();

				announcentForm.setId(rsAnnouncemtnData.getInt("id"));
				announcentForm.setHeadLines(rsAnnouncemtnData
						.getString("Head_Lines"));

				String contentDescription = rsAnnouncemtnData
						.getString("Subject");
				String date = rsAnnouncemtnData.getString("Annoucement_Date");
				String a[] = date.split(" ");
				announcentForm.setAnnouncementMonth(a[1]);
				announcentForm.setAnnouncementDay(a[0]);

				if (contentDescription.length() > 200)
					contentDescription = contentDescription.substring(0, 200);
				announcentForm.setLinkDescription(contentDescription);
				announcentForm.setAnnoucementDate(rsAnnouncemtnData
						.getString("Annoucement_Date"));
				announcentForm.setAnnouncementTime(rsAnnouncemtnData
						.getString("Annoucement_Time"));
				listOfOrganisationData.add(announcentForm);

			}

			if (type.equalsIgnoreCase("HEADLINES")) {
				request.setAttribute("HeadLinesData", listOfOrganisationData);
			}

			else {
				request.setAttribute("OrganisationData", listOfOrganisationData);
			}

			request.setAttribute("disablePreviousButton",
					"disablePreviousButton");

			mainForm.setRequiredType(type);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("displayMoreHeadLines");
	}
	public ActionForward savePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MainForm perslizeForm =(MainForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String empNo=perslizeForm.getUserName();
		String empOldPwd="";
		String enteredPwd=perslizeForm.getOldPassword();
		System.out.println(perslizeForm.getUserName());
		try{
			int credential=0;
			String checkUserCredentials="select count(*) from users where username='"+empNo+"' and password='"+encrypt(perslizeForm.getOldPassword())+"'";
			ResultSet rsUserCredential=ad.selectQuery(checkUserCredentials);
			while(rsUserCredential.next()){
				credential=rsUserCredential.getInt(1);
			}
			if(credential>0){
				String newpwd=perslizeForm.getNewPassword();
				Pattern p = Pattern.compile("[@]*");
		        Matcher m = p.matcher(newpwd);
		        boolean b = m.matches();
		        boolean digit = false;
		        char[] specialCh = {'!','@',']','#','$','%','^','&','*'};
		        char[] pwd = newpwd.toCharArray();
		        for(int i=0; i<pwd.length; i++) //checking for length
	           {
		        	for(int j=0;j<specialCh.length;j++){
		        		if(pwd[i]==specialCh[j]){
		        			digit=true;
		        		}
		        	}
	           }
		        if(digit==true){
		            GregorianCalendar calendar = new GregorianCalendar();

		            //Display the date now:
		            Date now = calendar.getTime();
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            String formattedDate = sdf.format(now);
		            System.out.println(formattedDate);

		            //Advance the calendar one day:
		            calendar.add(Calendar.DAY_OF_MONTH, 44);
		            Date tomorrow = calendar.getTime();
		            formattedDate = sdf.format(tomorrow);
		            String pwdExpDt=formattedDate;
		      String currentDate="";  	
		        	
		        	
			String updatePassword="update users set password='"+encrypt(perslizeForm.getNewPassword())+"',squestionid='"+perslizeForm.getFavoritQues()+"',squesanswer='"+perslizeForm.getFavAns()+"',loginCount='1',passwordexpirydate='"+pwdExpDt+"' where employeenumber='"+empNo+"'";
			int i=ad.SqlExecuteUpdate(updatePassword);
			if(i>0)
			{
				perslizeForm.setStatusMessage("Your password has been changed successfully.");
				perslizeForm.setMessage("");
				perslizeForm.setOldPassword("");
				perslizeForm.setNewPassword("");
				perslizeForm.setConformPassword("");
				perslizeForm.setFavAns("");
				perslizeForm.setFavoritQues("");
				
				request.setAttribute("backToLogin", "backToLogin");
				
			}else{
				perslizeForm.setMessage("Error..Data is not saved. Please try again.");
				perslizeForm.setUserName(empNo);
				request.setAttribute("saveButton", "saveButton");
				
			}
		}else{
			perslizeForm.setStatusMessage("Password not matches or Check New Password..Please try again");
			perslizeForm.setMessage("");
			perslizeForm.setOldPassword("");
			perslizeForm.setNewPassword("");
			perslizeForm.setConformPassword("");
			perslizeForm.setFavAns("");
			perslizeForm.setFavoritQues("");
			perslizeForm.setUserName(empNo);
			request.setAttribute("saveButton", "saveButton");
		}
			}else{
				perslizeForm.setStatusMessage("Old Password not matches..Try again");
				perslizeForm.setMessage("");
				perslizeForm.setOldPassword("");
				perslizeForm.setNewPassword("");
				perslizeForm.setConformPassword("");
				perslizeForm.setFavAns("");
				perslizeForm.setFavoritQues("");
				perslizeForm.setUserName(empNo);
				request.setAttribute("saveButton", "saveButton");
			}
		}
		catch (Exception e) {
			System.out.println("exception @ upload request");
			e.printStackTrace();
		}
		request.setAttribute("loginArea", "");
		request.setAttribute("personalizeArea", "show");
		return mapping.findForward("displayLoginPage");
	}
	
	
	
}