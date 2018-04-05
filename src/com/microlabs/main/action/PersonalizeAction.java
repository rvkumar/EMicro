package com.microlabs.main.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

import com.microlabs.main.form.PersonalizeForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.UserInfo;


public class PersonalizeAction extends DispatchAction{
	
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
	
	public ActionForward displayPersonalize(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PersonalizeForm perslizeForm =(PersonalizeForm)form;
		System.out.println("perslizeForm");
		String returnMsg="";
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String uId = request.getParameter("uId");
		String oldpwd = perslizeForm.getPassword();
		NewsandMediaDao ad=new NewsandMediaDao();
		if(user != null){
		String empOldPwd=user.getPassword();
		perslizeForm.setEmpOldPwd(empOldPwd);
		}
		try{
			String sql12="select * from users where username='"+uId+"' and password='"+oldpwd+"'and status='1' and activated='On'";
			ResultSet rs12=ad.selectQuery(sql12);
			if(rs12.next()){
				request.setAttribute("userMessage", "");
				request.setAttribute("changeForm", "show");
				returnMsg="passwordChange";
			}
			else{
				request.setAttribute("personalize", "User Name and Password does not match");
				request.setAttribute("userMessage", "show");
				request.setAttribute("changeForm", "");
				returnMsg="passwordChange";
			}
		}
		catch (SQLException e) {
			System.out.println("exception @ upload request");
			e.printStackTrace();
		}
		return mapping.findForward(returnMsg);
	}
	
	public ActionForward savePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PersonalizeForm perslizeForm =(PersonalizeForm)form;
		HttpSession session=request.getSession();
		NewsandMediaDao ad=new NewsandMediaDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String empNo="";
		String empOldPwd="";
		String enteredPwd=perslizeForm.getOldPassword();
		String newPwd=perslizeForm.getNewPassword();
		try{
		if(user == null){
			System.out.println(enteredPwd);
			String getuserinfo = "select employeenumber from users where password like '%"+enteredPwd+"%'";
			ResultSet RS = ad.selectQuery(getuserinfo);
			while(RS.next()){
				empNo=RS.getString(1);
			}
			empOldPwd=enteredPwd;
		}
		else{
			empNo=user.getEmployeeNo();
			empOldPwd=user.getPassword();
		}
		try {
			if(decrypt(empOldPwd).equalsIgnoreCase(enteredPwd))
			{
				
				Pattern p = Pattern.compile("[@]*");
			    Matcher m = p.matcher(newPwd);
			    boolean b = m.matches();
			    boolean digit = false;
			    char[] specialCh = {'!','@',']','#','$','%','^','&','*'};
			    char[] pwd = newPwd.toCharArray();
			   
			    
			    for(int i=0; i<pwd.length; i++) //checking for length
			   {
			    	for(int j=0;j<specialCh.length;j++){
			    		
			    		
			    		
			    		if(pwd[i]==specialCh[j]){
			    			digit=true;
			    		
			    		}
			    	}
			   }
			    
			    if(digit==true){
			    	perslizeForm.setNewPassword(encrypt(newPwd));
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
			        String updatePassword="update users set password='"+perslizeForm.getNewPassword()+"',passwordexpirydate='"+pwdExpDt+"',squestionid='"+perslizeForm.getFavoritQues()+"',squesanswer='"+perslizeForm.getFavAns()+"' where employeenumber='"+empNo+"'";
					int i=ad.SqlExecuteUpdate(updatePassword);
				if(i>0)
				{
					perslizeForm.setMessage2("Your password has been changed successfully.");
					
					perslizeForm.setOldPassword("");
					perslizeForm.setNewPassword("");
					perslizeForm.setConformPassword("");
					perslizeForm.setFavoritQues("");
					perslizeForm.setFavAns("");
					return mapping.findForward("passwordChange");
				}else{
					perslizeForm.setMessage("Error..Data is not saved. Please try again.");
				}
			}else{
				perslizeForm.setMessage("Error..Password should contain atleast one number and one special character.");
			}
			}else{
				perslizeForm.setMessage("Password not matches..Please try again");
				perslizeForm.setOldPassword("");
				perslizeForm.setNewPassword("");
				perslizeForm.setConformPassword("");
				perslizeForm.setFavoritQues("");
				perslizeForm.setFavAns("");
				return mapping.findForward("passwordChange");
			}
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		catch (SQLException e) {
			System.out.println("exception  request");
			e.printStackTrace();
		}
		return mapping.findForward("passwordChange");
	}

}
