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
	 th{font-family: Arial; font-size: 14;}
     td{font-family: Arial; font-size: 10;}
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
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<script type="text/javascript">
function onSave()
{


var newPwd=document.forms[0].newPassword.value;
var conformPwd=document.forms[0].conformPassword.value;

if(document.forms[0].oldPassword.value=="")
{
alert("Please enter Old Password");
 document.forms[0].oldPassword.focus();
	      return false;
}
if(newPwd=="")
{
alert("Please enter New Password");
 document.forms[0].newPassword.focus();
	      return false;
}
if(conformPwd=="")
{
alert("Please enter Confirm Password");
 document.forms[0].conformPassword.focus();
	      return false;
}



if(newPwd==conformPwd)
{
//var regularExpression  = /^[a-zA-Z0-9!@#$%^&*]{6,12}$/;
var regularExpression  = /^.*(?=.{6,12})(?=.*\d)(?=.*[a-zA-Z]).*$/;

if(!regularExpression.test(newPwd)) {
        alert("Password should contain atleast one number and one special character");
        document.forms[0].newPassword.value="";
         document.forms[0].newPassword.focus();
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
var url="personalize.do?method=savePassword";
			document.forms[0].action=url;
			document.forms[0].submit();
}
else{
alert("New password and Confirm password not matches");
document.forms[0].newPassword.value="";
document.forms[0].conformPassword.value="";
}			
}

function onBack()
{

var url="main.do?method=displayAnnouncement";
			document.forms[0].action=url;
			document.forms[0].submit();

}


</script>
</head>
<body>
	<html:form action="personalize" enctype="multipart/form-data">
	<div align="center">
		<logic:present name="personalizeForm" property="message">
			<font color="red"><bean:write name="personalizeForm" property="message" /></font>
		</logic:present>
		<logic:present name="personalizeForm" property="message2">
			<font color="Green"><bean:write name="personalizeForm" property="message2" /></font>
		</logic:present>
	</div>

	<div style="width: 70%">
	<table class="bordered" width="70%">
		<tr>
			<th colspan="2"><big>Change Password</big></th>
		</tr>				

		<tr>
			<td>Old Password <font color="red"><b>*</b></td>
		 	<td>
	 			<html:password property="oldPassword" style="width:200px" maxlength="12" name="personalizeForm" styleClass="rounded"  title="Old Password"></html:password>
	 			<html:hidden property="empOldPwd" name="personalizeForm"/>
	  		</td>	
		</tr>
		<tr>
			<td>New Password <font color="red"><b>*</b></td>
		 	<td><html:password property="newPassword" name="personalizeForm" maxlength="12" style="width:200px" styleClass="rounded"  title="New Password"></html:password></td>	
		</tr>
		<tr>
			<td>Confirm Password <font color="red"><b>*</b></td>
			<td><html:password property="conformPassword" name="personalizeForm" maxlength="12" style="width:200px" styleClass="rounded"  title="Confirm Password"></html:password></td>	
		</tr>	
		<tr>
										<td>Favorite question <font color="red">*</font> </td>
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
											<td>Answer <font color="red">*</font> </td> 
											<td>
												<html:text property="favAns" name="personalizeForm" maxlength="15" size="15" style="width:200px;" styleClass="rounded"  ></html:text>
											</td>	
										</tr>
		<tr>
			<td colspan="2" style="text-align: center;">
		 		<html:button property="method" value="Save" styleClass="rounded" style="width:100px" onclick="onSave()"></html:button>&nbsp;&nbsp;
      			<html:reset value="Reset" styleClass="rounded" style="width:100px"></html:reset>&nbsp;&nbsp;
      			<html:button property="method" value="Close" onclick="onBack()" styleClass="rounded" style="width:100px"></html:button>
      		</td>
      	</tr>
      	
      	<tr>
      		<td colspan="2" style="text-align: right;"><small>Your new password will be effective after you relogin.</small></td>
      	</tr>		
			</table>
			</div>
	<br/>
	
	
	
	</html:form>
</body>
</html>