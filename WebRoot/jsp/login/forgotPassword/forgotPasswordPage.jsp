<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="icon" 
      type="image/png" 
      href="images/favicon.ico"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="css/style.css"/>
		<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
		<title>Emicro-Forget Password</title>
		
		<style>
			
			
		input.button {
		background-repeat: no-repeat;
		background-position: <left|right>;
		padding-<left|right>: 78px;
		background-color: white;
		width:100px;
		height:24px;  
		}
		
		</style>
		
		<script type="text/javascript">
		function getSecurityQuest(){
		
		if(document.forms[0].userName.value=="")
	    {
	      alert("Please Enter User Name");
	      document.forms[0].userName.focus();
	      return false;
	    }
	    
		var url="login.do?method=getSecurityQuest";
		document.forms[0].action=url;
		document.forms[0].submit();

		}
		
		
		
		function onChangePaasswordSubmit(){
		
		if(document.forms[0].userName.value=="")
	    {
	      alert("Please Enter User Name");
	      document.forms[0].userName.focus();
	      return false;
	    }
	    
		var url="login.do?method=submitChangePassword";
		document.forms[0].action=url;
		document.forms[0].submit();

		}
		function securityCheck(){
			
	    if(document.forms[0].questionAnswer.value=="")
	    {
	      alert("Please Enter Answer");
	      document.forms[0].questionAnswer.focus();
	      return false;
	    }
	    
		var url="login.do?method=displayNewPassword";
		document.forms[0].action=url;
		document.forms[0].submit();
	
		}
		function saveNewPassword(){
			
	    if(document.forms[0].newP.value=="")
	    {
	      alert("Please Enter Answer");
	      document.forms[0].questionAnswer.focus();
	      return false;
	    }
	    else if(document.forms[0].cofmP.value=="")
	    {
	      alert("Please Enter Answer");
	      document.forms[0].questionAnswer.focus();
	      return false;
	    }
	    
		var url="login.do?method=updateNewPassword";
		document.forms[0].action=url;
		document.forms[0].submit();
	
		}
		
		</script>

	</head>
<body onload="onGoUser()">
<html:form action="/login.do" method="post">
	<div id="wrapper">
		<div class="backwrapper">
			<div class="log1-block">
				<div class="logos"><img src="images/logo1.jpg" /></div>
			</div>
			<div class="log2-block">
				<div class="help"><a href="login.do?method=logout" target="_self">Back to Log In</a></div>
			</div>
			<div class="mainsub-block">
				<div class="loginhelp">
					<table border="0" align="left" style="padding-left:15%;">
						<tr><td align="left" style="font-size: 30px;font-family: 'Trebuchet MS'; letter-spacing: -4px; font-style: italic; font-weight: normal;"><b>EMicro&nbsp;Accounts</b></td></tr>
						<tr></tr>
						<logic:notEmpty name="loginForm" property="message">
						<tr><td><font color="#FF0000"><bean:write name="loginForm" property="message"/></font></td></tr>
						</logic:notEmpty>
						<tr><td align="left"><br/></td></tr>
						<logic:notEmpty name="forgetpassword">
						<tr><td align="left" style="font-size: 20px; padding-left: 10%;"><b>Forgot&nbsp;Password?</b></td></tr>
						
						<tr><td align="left" style="padding-left: 15%;">To&nbsp;reset&nbsp;your&nbsp;password,&nbsp;enter&nbsp;the&nbsp;<b>User&nbsp;Name</b>&nbsp;you&nbsp;use&nbsp;to&nbsp;sign&nbsp;in&nbsp;your&nbsp;account<br/>
						<em>Note:You&nbsp;can't&nbsp;Reuse&nbsp;Your&nbsp;Old&nbsp;Password&nbsp;Once&nbsp;you&nbsp;Change&nbsp;it.</em></td></tr>
						<tr><td align="left"><br/></td></tr>
						<tr><td align="left" style="padding-left: 15%;"><b>User&nbsp;Name</b>&nbsp;&nbsp;<html:text property="userName"  style="width: 200px;height: 24px;"/></td></tr>
						<tr><td align="left"><br/></td></tr>
						<tr><td align="center"><html:button property="method" value="Submit" styleClass="sudmit_btn" style="width:100px;" onclick="getSecurityQuest()" /></td></tr>
						</logic:notEmpty>
						<logic:notEmpty name="security">
						<tr><td align="left" style="padding-left: 15%;">User Name: <bean:write name="loginForm" property="userName" /><html:text property="userName"  style="width: 200px;height: 24px;display:none;"><bean:write name="loginForm" property="userName" /></html:text></td></tr>
						<tr><td align="left" style="padding-left: 15%;">Answer Your Security Question to reset Your Password</td></tr>
						<tr><td style="padding-left: 15%;height: 10px" nowrap><b>Question : </b>
						<html:text property="securityQuestion"></html:text></td></tr>
						<tr><td align="left" style="padding-left: 15%;"><b>Enter&nbsp;Answer</b>&nbsp;&nbsp;<html:text property="questionAnswer" style="width: 200px;height: 24px;"/></td></tr>
						<tr><td align="left"><br/></td></tr>
						<tr><td align="center"><html:button property="method" value="Submit" styleClass="sudmit_btn" style="width:100px;" onclick="securityCheck()" /></td></tr>
						</logic:notEmpty>
						<logic:notEmpty name="newPassword">
						<tr><td align="left" style="padding-left: 20px;">User Name: <bean:write name="loginForm" property="userName" /><html:text property="userName"  style="width: 200px;height: 24px;display:none;"><bean:write name="loginForm" property="userName" /></html:text></td></tr>
						<tr><td align="left" style="padding-left: 20px;"><b>New&nbsp;Password</b>&nbsp;&nbsp;<html:password property="password" style="width: 200px;height: 24px;" styleId="newP"/></td></tr>
						<tr><td align="left" style="padding-left: 20px;"><b>Confirm&nbsp;Password</b>&nbsp;&nbsp;<html:password property="password" style="width: 200px;height: 24px;" styleId="cofmP"/></td></tr>
						<tr><td align="center"><html:button property="method" value="Submit" styleClass="sudmit_btn" style="width:100px;" onclick="saveNewPassword()" /></td></tr>
						</logic:notEmpty>
						
					</table>
				</div>
			</div>
		</div>
		<div class="footers">
			<div class="footer1">
				<div class="bottom">
					<div class="left-blocks"><p>&copy;|2012|Micro Labs Limited|All rights reserved</p></div>
					<div class="right-blocks"><p>...Because health is in small details</p></div>
				</div>
			</div>
		</div>
	</div>		
	</html:form>
	</body>
</html>
