package com.microlabs.login.action;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.microlabs.admin.dao.LinksDao;
import com.microlabs.admin.form.LinksForm;
import com.microlabs.login.dao.LoginDao;
import com.microlabs.login.form.LoginForm;
import com.microlabs.main.action.MainAction;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;



public class LoginAction extends DispatchAction{
	
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
	
	    
	    public ActionForward captchareload(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
			
			HttpSession session=request.getSession();
			MainAction obj = new MainAction();
			 BufferedImage ima = obj.getCaptchaImage();
			/*  File outputfile = new File("C://Tomcat 8.0/webapps/EMicro Files/images/EmpPhotos/image2.jpg");
			 ImageIO.write(ima, "jpg", outputfile); */
			 String captchaStr = obj.getCaptchaString();
			 
			 session.setAttribute("captchaStr", captchaStr);
			
			loginForm.setMessage2("/EMicro Files/images/EmpPhotos/image2.jpg?time="+new Date().getTime());
			
			
			return mapping.findForward("displaycaptchaimage");
		}
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
		
		HttpSession session=request.getSession();
		
		
		LoginDao ad=new LoginDao();
		
		String id=request.getParameter("id"); 
		String userName=loginForm.getUserName();
		String password=loginForm.getPassword();
		
		
		String sql12="select * from users where username='"+userName+"'" +
				" and password='"+password+"' and status='1' and activated='On'";
		
		ResultSet rs12=ad.selectQuery(sql12);
		
		
		loginForm.setUserName(userName);
		//loginForm.setPassword(password);
		
		String userGroup="";
		UserInfo user=ad.validate(loginForm);
		try{
			
			if(rs12.next()){
				userGroup=rs12.getString("groupname");
			}else{
				loginForm.setMessage("User Name and Password does not Matches Please Check Entered Values");
				return mapping.findForward("displayLoginPage");
			}
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		System.out.println("Getting a Date is ****************"+EMicroUtils.getCurrentDate());
		
		session.setAttribute("user", user);
		
		UserInfo user1=(UserInfo)session.getAttribute("user");
		
		
		if(userName==null){
			
			return mapping.findForward("display");
		}else{
			String userType=user1.getUserType(); 
		
		if(userType.equalsIgnoreCase("temp"))
		{		 
			
			String sql="update users set lastlogindate='"+EMicroUtils.getCurrentDate()+"' " +
					"where username='"+userName+"'" +
					" and password='"+password+"'";
			
			ad.SqlExecuteUpdate(sql);
			
		return mapping.findForward("home");
		}
		else
		{
			String sql="update users set lastlogindate='"+EMicroUtils.getCurrentDate()+"' " +
			"where username='"+userName+"'" +
			" and password='"+password+"'";
			
			ad.SqlExecuteUpdate(sql);
			
			
			return mapping.findForward("home");
	    }
		
		}
	}
	
	
	public ActionForward displayHelpContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;
		LoginDao ad=new LoginDao();
		
		String sql1="select * from archieves where link_name='Login Help'";

		ResultSet rs1=ad.selectQuery(sql1);
		
		try{
			
			while (rs1.next()) {
				loginForm.setContentDescription(rs1.getString("content_description"));
				System.out.println("content="+rs1.getString("content_description"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	
		return mapping.findForward("helpContetDescription");
	
	}
	

	
	public ActionForward submitChangePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
		
		String userName=loginForm.getUserName();
		
		LinksDao ad=new LinksDao();
		
		
		try {
			 ArrayList questionIdList = new ArrayList();
			 ArrayList questionValueList = new ArrayList();
		
		String sql="select distinct id,question_name from security_questions where status='1'";
		
		ResultSet rs1=ad.selectQuery(sql);
		
		
		while (rs1.next()) {
			questionIdList.add(rs1.getString("id"));
			questionValueList.add(rs1.getString("question_name"));
		}
		
		
		loginForm.setQuestionIdList(questionIdList);
		loginForm.setQuestionValueList(questionValueList);
		
		
		String sql1="select * from users where username='"+userName+"'";
		
		ResultSet rs=ad.selectQuery(sql1);
		
			
			if(rs.next()) {
				
				
				String sql2="select question_name from security_questions where id='"+rs.getString("squestionid")+"'";
				
				ResultSet rs2=ad.selectQuery(sql2);
				
				
				while (rs2.next()) {
					loginForm.setQuestionId(rs2.getString("question_name"));
				}
				
				return mapping.findForward("displayForgotPage1");
			}else{
				loginForm.setMessage("You Have Entered Wrong Username.. Please Check UserName");
				return mapping.findForward("displayForgotPage");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return mapping.findForward("displayForgotPage1");
	}
	
	
	
	
	public ActionForward sendChangePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
		
		String userName=loginForm.getUserName();
		
		String questionAnswer=loginForm.getQuestionAnswer();
		String questionId=loginForm.getQuestionId();
		
		LinksDao ad=new LinksDao();
		
		
		try {
		
		
		String sql1="select * from users where username='"+userName+"' and " +
				"squestionid='"+questionId+"' and squesanswer='"+questionAnswer+"'";
		
		ResultSet rs=ad.selectQuery(sql1);
			
			if(rs.next()) {
				 ArrayList questionIdList = new ArrayList();
				 ArrayList questionValueList = new ArrayList();
			
			String sql="select distinct id,question_name from security_questions where status='1'";
			
			ResultSet rs1=ad.selectQuery(sql);
			
			
			while (rs1.next()) {
				questionIdList.add(rs1.getString("id"));
				questionValueList.add(rs1.getString("question_name"));
			}
			
			
			loginForm.setQuestionIdList(questionIdList);
			loginForm.setQuestionValueList(questionValueList);
			
			
				loginForm.setMessage("Password has sent ur mail Id.. Please check your mail");
				loginForm.setQuestionAnswer("");
			}else{
				 
				 
				 ArrayList questionIdList = new ArrayList();
				 ArrayList questionValueList = new ArrayList();
			
			String sql="select distinct id,question_name from security_questions where status='1'";
			
			ResultSet rs1=ad.selectQuery(sql);
			
			
			while (rs1.next()) {
				questionIdList.add(rs1.getString("id"));
				questionValueList.add(rs1.getString("question_name"));
			}
			
			
			loginForm.setQuestionIdList(questionIdList);
			loginForm.setQuestionValueList(questionValueList);
				
				
				loginForm.setMessage("You Have Selected Wrong Question.. ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return mapping.findForward("displayForgotPage1");
	}
	
	
	
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException {
		LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
		LoginDao ad=new LoginDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user!=null){
		try{
			String updateUseStatus="update users set use_status=0,lastlogoutdate='"+EMicroUtils.getCurrentDate()+"' where employeenumber='"+user.getEmployeeNo()+"'";
			ad.SqlExecuteUpdate(updateUseStatus);
			
			Date dNow1 = new Date( );
			 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
			String dateNow1 = ft1.format(dNow1);
			String id="";
			String data="select max(id) as id from Login_History where employeenumber='"+user.getEmployeeNo()+"'";
			ResultSet rs22=ad.selectQuery(data);
			
				if(rs22.next()) {
				id=rs22.getString("id");
				}
			String updateUseStatus1="update Login_History set Logout_Time='"+dateNow1+"' where id='"+id+"' and employeenumber='"+user.getEmployeeNo()+"' ";
			ad.SqlExecuteUpdate(updateUseStatus1);
			
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
				//con.close();
			}
		}
		session.invalidate();
		String sql11="select * from archieves where link_name='Login CMS' and module='Main' and status='null'";
		ResultSet rs11=ad.selectQuery(sql11);
		try{
			while(rs11.next()) {
				loginForm.setContentDescription(rs11.getString("content_description"));
			}
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		//display(mapping, form, request, response);
		return mapping.findForward("display");
	}
	
	
	
	public ActionForward displaySublinks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
		
		
		display(mapping, form, request, response);	 
		return mapping.findForward("display");
	}
	
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
		
		//String linkName=linksForm.getLinkName();
		
		String linksId=request.getParameter("sId");
		String linkName=request.getParameter("lId");
		
		try{
			
			LinksDao ad=new LinksDao();
			
			ResultSet rs=ad.selectQuery("select * from sub_links where id="+linksId+"");
			
			ArrayList a1=new ArrayList();
			
			
			LinksForm linksForm1=null;
			 while(rs.next()){
				 linksForm1=new LinksForm();
				 
				 linksForm.setSubLinkName(rs.getString("link_name"));
				 linksForm.setSubLinkId(linksId);
				
			 }
			 
			 linksForm.setLinkName(linkName);
			 request.setAttribute("listDetails", a1);
			 request.setAttribute("displaySublinkField", "displaySublinkField");
			 request.setAttribute("modifyButton", "modifyButton"); 
			 
			 ResultSet rs1=ad.selectQuery("select * from sub_links where link_id="+linkName+"");
				
				 while(rs1.next()){
					 linksForm1=new LinksForm();
					 linksForm1.setLinkName(linkName);
					 linksForm1.setSubLinkName(rs1.getString("link_name"));
					 linksForm1.setSubLinkId(rs1.getString("id"));
					 a1.add(linksForm1);
				 }
				 
				 linksForm.setLinkName(linkName);
				 request.setAttribute("listDetails", a1);
				 request.setAttribute("displaySublinkField", "displaySublinkField");

			 }catch (Exception e){
		      e.printStackTrace();
		  }
		
		
		
			 
		display(mapping, form, request, response);	 
		return mapping.findForward("display");
	}
	
	
	
	public ActionForward displayContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
		
		//String linkName=linksForm.getLinkName();
		
		
		try{
			
			LinksDao ad=new LinksDao();
			
			ResultSet rs=ad.selectQuery("select * from links where link_name='LoginHelp'");
			
			ArrayList a1=new ArrayList();
			
			
			 while(rs.next()){
				 
				 linksForm.setSubLinkName(rs.getString("link_name"));
			 }
			 
			 }catch (Exception e){
		      e.printStackTrace();
		  }
		
		
		
			 
		displaySublinks(mapping, form, request, response);	 
		return mapping.findForward("display");
	}
	
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
		
		//String linkName=linksForm.getLinkName();
		
		String linksId=request.getParameter("sId");
		String linkName=request.getParameter("lId");
		
		try{
			
			LinksDao ad=new LinksDao();
			
			ResultSet rs=ad.selectQuery("select * from sub_links where id="+linksId+"");
			
			ArrayList a1=new ArrayList();
			
			
			LinksForm linksForm1=null;
			 while(rs.next()){
				 linksForm1=new LinksForm();
				 
				 linksForm.setSubLinkName(rs.getString("link_name"));
				 linksForm.setSubLinkId(linksId);
				 a1.add(linksForm1);
			 }
			 
			 linksForm.setLinkName(linkName);
			 request.setAttribute("listDetails", a1);
			 request.setAttribute("displaySublinkField", "displaySublinkField");
			 }catch (Exception e){
		      e.printStackTrace();
		  }
		
		
		
			 
		displaySublinks(mapping, form, request, response);	 
		return mapping.findForward("display");
	}
	
	public ActionForward changePasswordExp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
		loginForm.setUserName("");
		loginForm.setNewPassword("");
		loginForm.setConformPwd("");
		loginForm.setOldPassword("");
		request.setAttribute("forgetpassword", "show");
		request.setAttribute("security", "");
		request.setAttribute("newPassword", "");
		request.setAttribute("showForgetPWD", "showForgetPWD");
		return mapping.findForward("displayPasswordChange");
    }
	public ActionForward displayForgotPasswordPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
		
		request.setAttribute("forgetpassword", "show");
		request.setAttribute("security", "");
		request.setAttribute("newPassword", "");
		request.setAttribute("showForgetPWD", "showForgetPWD");
		return mapping.findForward("displayForgotPage");
    }
	public ActionForward checkUserDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
		LoginDao ad=new LoginDao();
		try{
			int check=0;
			String dateOfJoin=loginForm.getDateOfJoin();
			String a[]=dateOfJoin.split("/");
			dateOfJoin=a[2]+"-"+a[1]+"-"+a[0];
			String checkUser="select count(*) from emp_official_info where PERNR='"+loginForm.getUserName()+"' and DOJ='"+dateOfJoin+"'";
			ResultSet rsCheckUser=ad.selectQuery(checkUser);
			while(rsCheckUser.next()){
				check=rsCheckUser.getInt(1);
			}
			String checkFavorateQues="select count(*) from users where username='"+loginForm.getUserName()+"' and squestionid='"+loginForm.getFavoritQues()+"' and squesanswer='"+loginForm.getFavAns()+"'";
			ResultSet rscheckFavorateQues=ad.selectQuery(checkFavorateQues);
			while(rscheckFavorateQues.next()){
				check=check+rscheckFavorateQues.getInt(1);
			}
			
			if(check==2){
				String reqUserName=loginForm.getUserName();
				request.setAttribute("changePWD", "changePWD");
				loginForm.setReqUserName(reqUserName);
				loginForm.setMessage(" User Name And Date Of Join Correct ");
			}
			if(check<2){
				request.setAttribute("showForgetPWD", "showForgetPWD");
				loginForm.setMessage2("Invalid Credentials..Please Check..");
			}
			
		}catch (SQLException e) {
			request.setAttribute("showForgetPWD", "showForgetPWD");
			loginForm.setMessage2("Please Check User Name or Date Of Join...");
		}catch (ArrayIndexOutOfBoundsException e) {
			request.setAttribute("showForgetPWD", "showForgetPWD");
			loginForm.setMessage2("Please Check User Name or Date Of Join...");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayForgotPage");
    }
	
	
	public ActionForward modifyExpPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
	
		NewsandMediaDao ad=new NewsandMediaDao();
	
		
		String empOldPwd="";
		String enteredPwd=loginForm.getOldPassword();
		try{
			
			String newpwd=loginForm.getNewPassword();
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
	        	
	        	
	        	loginForm.setNewPassword(encrypt(newpwd));
				loginForm.setOldPassword(encrypt(enteredPwd));
	        //check user credentials 
	        int check=0;	
	        String checkUser="select count(*) from users where username='"+loginForm.getUserName()+"' and password='"+loginForm.getOldPassword()+"'";
			ResultSet rsCheckUser=ad.selectQuery(checkUser);
			while(rsCheckUser.next()){
				check=rsCheckUser.getInt(1);
			}
		    if(check==1)
		    {
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
				String updatePassword="update users set password='"+loginForm.getNewPassword()+"',passwordexpirydate='"+pwdExpDt+"' where employeenumber='"+loginForm.getUserName()+"'";
				int i=ad.SqlExecuteUpdate(updatePassword);
				if(i>0)
				{
					
					logout(mapping, form, request, response);
					request.setAttribute("changePWD", "changePWD");
					request.setAttribute("goToLoginPage", "goToLoginPage");
					request.setAttribute("showForgetPWD", "showForgetPWD");
					return mapping.findForward("displayPasswordChange");
				}else{
					loginForm.setMessage2("Invalid User Credentials.");
		        	loginForm.setUserName("");
		    		loginForm.setNewPassword("");
		    		loginForm.setConformPwd("");
		    		loginForm.setOldPassword("");
		    		request.setAttribute("forgetpassword", "show");
		    		request.setAttribute("security", "");
		    		request.setAttribute("newPassword", "");
		    		request.setAttribute("showForgetPWD", "showForgetPWD");
		    		return mapping.findForward("displayPasswordChange");
				}
		    }else{
	        	loginForm.setMessage2("Invalid User Credentials.");
	        	loginForm.setUserName("");
	    		loginForm.setNewPassword("");
	    		loginForm.setConformPwd("");
	    		loginForm.setOldPassword("");
	    		request.setAttribute("forgetpassword", "show");
	    		request.setAttribute("security", "");
	    		request.setAttribute("newPassword", "");
	    		request.setAttribute("showForgetPWD", "showForgetPWD");
	    		return mapping.findForward("displayPasswordChange");
	        }
	        
	        }else{
	        	loginForm.setMessage2("Error..Password should contain atleast one number and one special character.");
	        	loginForm.setUserName("");
	    		loginForm.setNewPassword("");
	    		loginForm.setConformPwd("");
	    		loginForm.setOldPassword("");
	    		request.setAttribute("forgetpassword", "show");
	    		request.setAttribute("security", "");
	    		request.setAttribute("newPassword", "");
	    		request.setAttribute("showForgetPWD", "showForgetPWD");
	    		return mapping.findForward("displayPasswordChange");
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("displayPasswordChange");
    }
	public ActionForward modifyPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
	
		NewsandMediaDao ad=new NewsandMediaDao();
	
		
		String empOldPwd="";
		String enteredPwd=loginForm.getOldPassword();
		try{
			String reqUserName=loginForm.getReqUserName();
			
			String newpwd=loginForm.getNewPassword();
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
	        	
	        	loginForm.setNewPassword(encrypt(newpwd));
	        	 //Display the date now:
	        	 GregorianCalendar calendar = new GregorianCalendar();
	            Date now = calendar.getTime();
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            String formattedDate = sdf.format(now);
	            System.out.println(formattedDate);

	            //Advance the calendar one day:
	            calendar.add(Calendar.DAY_OF_MONTH, 44);
	            Date tomorrow = calendar.getTime();
	            formattedDate = sdf.format(tomorrow);
	            String pwdExpDt=formattedDate;
			String updatePassword="update users set password='"+loginForm.getNewPassword()+"',passwordexpirydate='"+pwdExpDt+"' where employeenumber='"+loginForm.getReqUserName()+"'";
			int i=ad.SqlExecuteUpdate(updatePassword);
			if(i>0)
			{
				//loginForm.setMessage("Your password has been changed successfully.");
				logout(mapping, form, request, response);
				request.setAttribute("changePWD", "changePWD");
				request.setAttribute("goToLoginPage", "goToLoginPage");
			}else{
				loginForm.setMessage2("Error..Data is not saved. Please try again.");
				request.setAttribute("changePWD", "changePWD");
				loginForm.setReqUserName(reqUserName);
			}
	        
	        }else{
	        	loginForm.setMessage2("Error..Password should contain atleast one number and one special character.");
	        	request.setAttribute("changePWD", "changePWD");
				loginForm.setReqUserName(reqUserName);
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("displayForgotPage");
    }
	
	public ActionForward displayNewPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
		
		String userName=loginForm.getUserName();
		
		String questionAnswer=loginForm.getQuestionAnswer();
		String questionId=loginForm.getSecurityQuestion();
		
		LinksDao ad=new LinksDao();
		
		
		try {
		
		
		String sql1="select u.password from emp_master as emp,users as u where emp.emp_id='"+userName+"' and emp.favorate_Question='"+questionId+"'" +
				" and emp.answer='"+questionAnswer+"' and u.username=emp.emp_id";
		
		ResultSet rs=ad.selectQuery(sql1);
			
			if(rs.next()) {
				 ArrayList questionIdList = new ArrayList();
				 ArrayList questionValueList = new ArrayList();
			
			String sql="select distinct id,question_name from security_questions where status='1'";
			
			ResultSet rs1=ad.selectQuery(sql);
			
			
			while (rs1.next()) {
				questionIdList.add(rs1.getString("id"));
				questionValueList.add(rs1.getString("question_name"));
			}
			
			String passWord = rs.getString("password");
			loginForm.setQuestionIdList(questionIdList);
			loginForm.setQuestionValueList(questionValueList);
			loginForm.setUserName(userName);
			EMailer email = new EMailer();
			int i = email.sendNewPasswordToMail(request, userName, passWord);
			if(i >0){
				loginForm.setMessage("Login Information send to user personal mail id");
			}
			/*request.setAttribute("newPassword","new");
			request.setAttribute("forgetpassword","");
			request.setAttribute("security","");*/
			
				loginForm.setQuestionAnswer("");
			}else{
				 
				 
				 ArrayList questionIdList = new ArrayList();
				 ArrayList questionValueList = new ArrayList();
			
			String sql="select distinct id,question_name from security_questions where status='1'";
			
			ResultSet rs1=ad.selectQuery(sql);
			
			
			while (rs1.next()) {
				questionIdList.add(rs1.getString("id"));
				questionValueList.add(rs1.getString("question_name"));
			}
			
			
			loginForm.setQuestionIdList(questionIdList);
			loginForm.setQuestionValueList(questionValueList);
				
			/*request.setAttribute("newPassword","");
			request.setAttribute("forgetpassword","");
			request.setAttribute("security","show");*/
				loginForm.setMessage("You have entered wrong Question/Password.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return mapping.findForward("displayForgotPage1");
	}
//get security question
	
	public ActionForward getSecurityQuest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;
		
		String userName=loginForm.getUserName();
		LinksDao ad=new LinksDao();
		
		
		try {
			 ArrayList questionIdList = new ArrayList();
			 ArrayList questionValueList = new ArrayList();
		
		String sql="select squestionid,squesanswer from users where employeenumber='"+userName+"'";
		
		ResultSet rs1=ad.selectQuery(sql);
		
		
		while (rs1.next()) {
			questionIdList.add(rs1.getString("squestionid"));
			questionValueList.add(rs1.getString("squesanswer"));
		}
		
		
		loginForm.setQuestionIdList(questionIdList);
		loginForm.setQuestionValueList(questionValueList);
		
		
		String sql1="select * from  users where employeenumber='"+userName+"'";
		
		ResultSet rs=ad.selectQuery(sql1);
		
			
			if(rs.next()) {
				
				
				String sql2="select squestionid from users where employeenumber='"+userName+"'";
				
				ResultSet rs2=ad.selectQuery(sql2);
				
				
				while (rs2.next()) {
					loginForm.setSecurityQuestion(rs2.getString("squestionid"));
				}
				request.setAttribute("security","show");
				request.setAttribute("forgetpassword", "");
				request.setAttribute("newPassword", "");
				loginForm.setUserName(userName);
				//return mapping.findForward("displayForgotPage1");
			}else{
				loginForm.setMessage("User Name not registred with microlabs");
				request.setAttribute("forgetpassword", "show");
				request.setAttribute("security","");
				request.setAttribute("newPassword", "");
			//	return mapping.findForward("displayForgotPage");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayForgotPage1");
	}
		
	//update password and send mail
//get security question
	
	public ActionForward updateNewPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;
		
		String userName=loginForm.getUserName();
		String newPssword=loginForm.getPassword();
		
		LinksDao ad=new LinksDao();
		
		
		try {
			 ArrayList questionIdList = new ArrayList();
			 ArrayList questionValueList = new ArrayList();
		
		String sql="update users set password='"+newPssword+"' where username='"+userName+"'";
		
		int upd=ad.SqlExecuteUpdate(sql);
		if(upd > 0){
			/*String approvermail="";
			String sql1="select mail_id from users where username='"+userName+"'";
			
			ResultSet rs=ad.selectQuery(sql1);
			while (rs.next()) {
				approvermail = rs.getString("mail_id");
			}
			EMailer email = new EMailer();
			int i = email.sendNewPasswordToMail(request,approvermail,userName,newPssword);
			if(i > 0){
				loginForm.setMessage("You Login Details Sent Your Mail Id.. Please Check Your Mail");
			}*/
			loginForm.setMessage("Your has been changed!");
		}
		else{
			loginForm.setMessage("Error While Updating the New Passord");
		}
		request.setAttribute("security","");
		request.setAttribute("forgetpassword", "");
		request.setAttribute("newPassword", "");
		loginForm.setUserName(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayForgotPage1");
	}
	
	
}
