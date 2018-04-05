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
<link rel="shortcut icon" type="image/x-icon" 
      href="images/favicon.ico"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Change Password </title>

<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<style type="text/css">
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
$(function() {
	$('#dateofJoinId').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});
function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>
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
if(document.forms[0].favoritQues.value=="")
{
alert("Please Select Favorite Question");
 document.forms[0].favoritQues.focus();
	      return false;
}
if(document.forms[0].favAns.value=="")
{
alert("Please Enter Answer");
 document.forms[0].favAns.focus();
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
alert("Please Enter New Password");
 document.forms[0].newPassword.focus();
	      return false;
}
if(conformPwd=="")
{
alert("Please Enter Confirm Password");
 document.forms[0].conformPwd.focus();
	      return false;
}
else if(newPwd==conformPwd){
	//var regularExpression  = /^[a-zA-Z0-9!@#$%^&*]{6,12}$/;
	var regularExpression  = /^.*(?=.{6,12})(?=.*\d)(?=.*[a-zA-Z]).*$/;

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
function goToLoginPage()
{
var r=confirm("Your password has been changed successfully.");
if (r==true)
  {
  var url="login.do?method=logout";
			document.forms[0].action=url;
			document.forms[0].submit();
  }
else
  {

  } 

}

</script>
</head>
<body bgcolor="#2E9AFE">
	<html:form action="login" enctype="multipart/form-data">
	
	<img src="images/MLLogo.png"></img>
	<br/><br/>
	
	<br/><br/>
	
	<div align="center">
		<logic:present name="loginForm" property="message">
			<font color="white" ><bean:write name="loginForm" property="message" /></font>
		</logic:present>
		<logic:present name="loginForm" property="message2">
			<font color="white"><bean:write name="loginForm" property="message2" /></font>
		</logic:present>
	</div>
<logic:notEmpty name="showForgetPWD">
<center>
	<div style="width: 70%" align="center">
	
	<table class="bordered" bgcolor="#E6E6E6" width="70%" align="center">
		<tr>
			<th colspan="2"><big>Forgot Password</big></th>
		</tr>				

		<tr>
			<td>User Name <font color="red">*</font></td>
		 	<td>
	 			<html:text property="userName" style="width:100px" name="loginForm" styleClass="content" ></html:text>
	 			
	  		</td>	
		</tr>
		<tr>
			<td>Date Of Join (dd/mm/yyyy) <font color="red">*</font></td>
		 	<td><html:text property="dateOfJoin" styleId="dateofJoinId" name="loginForm" style="width:100px" styleClass="content" title="dd/mm/yyyy" readonly="true"></html:text></td>	
		</tr>
			
		
      		<tr>
				<td>Favorite question <font color="red">*</font></td>
				<td>
				<html:select property="favoritQues" styleClass="content">
				<html:option value="">Select</html:option>
			  <html:option value="What was your childhood nickname?">What was your childhood nickname?</html:option>
			 <html:option value="What is your mothers maiden name?">What is your mother's maiden name?</html:option>
			 <html:option value="What is the name of the first school you attended?">What is the name of the first school you attended?</html:option>
			 <html:option value="In what city or town was your first job?">In what city or town was your first job?</html:option>
			 <html:option value="Which is your place of birth?">Which is your place of birth?</html:option>
				
				</html:select>
				</td>
				</tr>
				<tr>
					<td>Answer <font color="red">*</font></td>
					<td>
						<html:text property="favAns" name="loginForm"   styleClass="content"></html:text>
					</td>	
				</tr>
				<tr>
			<td colspan="2" style="text-align: center;">
		 		<html:button property="method" value="Submit" styleClass="rounded" style="width:100px" onclick="onSubmit()"></html:button>&nbsp;&nbsp;
      		
      			<html:button property="method" value="Close" onclick="onBack()" styleClass="rounded" style="width:100px"></html:button>
      		</td>
      	</tr>
      		
			</table>
			</div>
			</center>
			</logic:notEmpty>
			
	<br/>
	<html:hidden property="reqUserName"/>
	<logic:notEmpty name="changePWD">
<center>
	<div style="width: 70%" align="center">
	
	<table class="bordered" bgcolor="#E6E6E6" width="70%">
		<tr>
			<th colspan="2"><big>Forgot Password</big></th>
		</tr>				

		<tr>
			<td>New Password <font color="red">*</font></td>
		 	<td>
	 			<html:password property="newPassword" style="width:200px"  maxlength="12" size="12" name="loginForm" title="maximum 12 charecters" ></html:password>
	 			
	  		</td>	
		</tr>
		<tr>
			<td>Confirm Password <font color="red">*</font></td>
		 	<td><html:password property="conformPwd" name="loginForm"  maxlength="12" size="12" style="width:200px"  title="maximum 12 charecters"></html:password></td>	
		</tr>
			
		<tr>
			<td colspan="2" style="text-align: center;">
		 		<html:button property="method" value="Save" styleClass="rounded" style="width:100px" onclick="onSave()"></html:button>&nbsp;&nbsp;
      			
      			<html:button property="method" value="Close" onclick="onBack()" styleClass="rounded" style="width:100px"></html:button>
      		</td>
      	</tr>
      		<tr>
      		<td colspan="2" style="text-align: right;"> Minimum Password length : 6 (Combination Of 1 number, 1 special character)</td>
      	</tr>
			</table>
			</div>
			
			</center>
			</logic:notEmpty>
	<logic:notEmpty name="goToLoginPage">
	<script type="text/javascript">
	goToLoginPage();
	</script>
	
	</logic:notEmpty>
	
	</html:form>
</body>
</html>