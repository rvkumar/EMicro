<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Change Password </title>

<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />

<style>
input:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
select:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
</style>

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />


<!--
/////////////////////////////////////////////////
-->

<script type="text/javascript">
function onSubmit()
{
if(document.forms[0].userName.value=="")
{
alert("Please Enter User Name");
 document.forms[0].userName.focus();
	      return false;
}
if(document.forms[0].dateOfJoin.value=="")
{
alert("Please Enter Date Of Join");
 document.forms[0].dateOfJoin.focus();
	      return false;
}
var url="login.do?method=checkUserDetails";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function onBack()
{

var url="login.do?method=logout";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function onSave(){

var newPwd=document.forms[0].newPassword.value;
var conformPwd=document.forms[0].conformPwd.value;
if(newPwd=="")
{
alert("Please enter New Password");
 document.forms[0].newPassword.focus();
	      return false;
}
if(conformPwd=="")
{
alert("Please enter Confirm Password");
 document.forms[0].conformPwd.focus();
	      return false;
}else if(newPwd==conformPwd){



var regularExpression  = /^[a-zA-Z0-9!@#$%^&*]{6,12}$/;

if(!regularExpression.test(newPwd)) {
        alert("Password should contain atleast one number and one special character");
         document.forms[0].newPassword.focus();
        return false;
    }
    var url="login.do?method=modifyPassword";
			document.forms[0].action=url;
			document.forms[0].submit();
    
    }
 else{
alert("New password and Confirm password not matches");
document.forms[0].newPassword.value="";
document.forms[0].conformPassword.value="";
}			

}

</script>
</head>
<body bgcolor="#2E9AFE">

	<logic:notEmpty name="personalizeArea">
	<img src="images/MLLogo.png"></img>
	<br/><br/>
	<center><big><b><font color="white">Welcome to Change Password wizard...</font></b></big></center>
	<br/><br/>
	
		<div align="center" style="background:#58ACFA;">
						<logic:present name="mainForm" property="statusMessage">
							<font color="red"><bean:write name="mainForm" property="statusMessage" /></font>
						</logic:present>
					</div>
<html:form action="/main.do" method="post" onsubmit="onSubmit(); return false;">
						<div>
						<table class="bordered" bgcolor="#E6E6E6" >
							 			<tr>
							 				<th colspan="2"><big>Change Password</big></th>
							 			</tr>
										
										<tr>
											<td>Old Password</td>
											<td>
												<html:text property="oldPassword"  name="mainForm" styleClass="content"></html:text>
												<html:hidden property="empOldPwd" name="mainForm"/>
	  										</td>	
										</tr>

										<tr>
											<td>New Password</td>
											<td>
												<html:text property="newPassword" name="mainForm"  maxlength="12" styleClass="content" onblur="checkPassFormat(this)"></html:text>
												<br/>
												<small>Minimum password length should be of 6 characters, 1 number & 1 special character. (Ex:1@emicro)</small>
							  				</td>	
										</tr>

										<tr>
											<td>Confirm Password</td>
											<td>
												<html:text property="conformPassword" name="mainForm" maxlength="12" size="12" title="Minimum of 6 and Maximum of 12 characters" styleClass="content"></html:text>
											</td>	
										</tr>
										<tr>
										<td>Favorite question</td>
										<td>
										<html:select property="favoritQues" styleClass="content">
										 <html:option value="What was your childhood nickname?">What was your childhood nickname?</html:option>
										 <html:option value="What is your mother's maiden name?">What is your mother's maiden name?</html:option>
										 <html:option value="What is the name of the first school you attended?">What is the name of the first school you attended?</html:option>
										 <html:option value="What is the name of your first grade teacher?">What is the name of your first grade teacher?</html:option>
										 <html:option value="In what city or town was your first job?">In what city or town was your first job?</html:option>
										 <html:option value="What is your favorite animal?">What is your favorite animal?</html:option>
										 <html:option value="What is your favorite sport?">What was the color of your first car?</html:option>
										 <html:option value="What is your favorite movie?">What was your childhood nickname?</html:option>
										
										</html:select>
										</td>
										</tr>
										<tr>
											<td>Answer</td>
											<td>
												<html:text property="favAns" name="mainForm" maxlength="15" size="15"  styleClass="content"></html:text>
											</td>	
										</tr>	

										<tr>
											<td colspan="2" style="text-align: center;">
						      					<html:button property="method" value=" Save " styleClass="rounded" style="width:100px" onclick="onSave()"></html:button> &nbsp;
												<html:reset value=" Reset " styleClass="rounded" style="width:100px"></html:reset>  &nbsp;
												<html:button property="method" value="Back To Login" onclick="onBack()" styleClass="rounded" style="width:100px"></html:button>
											</td>
										</tr>		
								</table>

								<br/>
								<font color="red"><b>Note: Your new password will be effective after you relogin.</b></font>
				</html:form>
	
	</logic:notEmpty>

</body>
</html>