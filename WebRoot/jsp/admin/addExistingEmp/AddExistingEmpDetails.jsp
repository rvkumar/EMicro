<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>



<jsp:directive.page import="com.microlabs.utilities.UserInfo" />
<jsp:directive.page import="com.microlabs.login.dao.LoginDao" />
<jsp:directive.page import="java.sql.ResultSet" />
<jsp:directive.page import="java.sql.SQLException" />
<jsp:directive.page import="java.util.ArrayList" />
<jsp:directive.page import="java.util.Iterator" />
<jsp:directive.page import="java.util.LinkedHashMap" />
<jsp:directive.page import="java.util.Set" />
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="com.microlabs.utilities.IdValuePair" />


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
		

		<script type='text/javascript' src="calender/js/zapatec.js"></script>
		<!-- Custom includes -->
		<!-- import the calendar script -->
		<script type="text/javascript" src="calender/js/calendar.js"></script>

		<script type="text/javascript" src="js/sorttable.js"></script>
		<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
		<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
		<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
		<script type="text/javascript">

function onModify()
{
var employeeNo=prompt("Please Employee No","");

if (employeeNo!=null)
  {
	var url="addEmp.do?method=getEmployeeDetails&employeeNo="+employeeNo;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
}


function onBack()
{

	var url="addEmp.do?method=displayEmployee";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}

function onModify(){

   if(document.forms[0].loginName.value=="")
  {
    alert("Please Enter Login Name");
    document.forms[0].loginName.focus();
    return false;
  }
  var loginName=document.forms[0].loginName.value;
         
         var splChars = "'";
for (var i = 0; i < loginName.length; i++) {
    if (splChars.indexOf(loginName.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  LOGIN Name!"); 
     document.forms[0].loginName.focus();
 return false;
}
}
  
  if(document.forms[0].employeeNo.value=="")
  {
    alert("Please Enter Employee No");
    document.forms[0].employeeNo.focus();
    return false;
  }
    var employeeNo = document.forms[0].employeeNo.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(employeeNo)) {
             alert("Employee NO.  Should be Integer ");
                document.forms[0].employeeNo.focus();
            return false;
        }
  
  if(document.forms[0].password.value=="")
  {
    alert("Please Enter Password");
    document.forms[0].password.focus();
    return false;
  }
  
    if(document.forms[0].activation.value=="")
  {
    alert("Please Select Activation");
    document.forms[0].activation.focus();
    return false;
  }
    if(document.forms[0].empStatus.value=="")
  {
    alert("Please Select Employee Type");
    document.forms[0].empStatus.focus();
    return false;
  }
   
  if(document.forms[0].favoriteQuest.value=="")
  {
    alert("Please Enter Favorite Question");
    document.forms[0].favoriteQuest.focus();
    return false;
  }
   var favoriteQuest=document.forms[0].favoriteQuest.value;
         
         var splChars = "'";
for (var i = 0; i < favoriteQuest.length; i++) {
    if (splChars.indexOf(favoriteQuest.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Favourite Question!"); 
     document.forms[0].favoriteQuest.focus();
 return false;
}
}
  if(document.forms[0].answer.value=="")
  {
    alert("Please Enter Answer");
    document.forms[0].answer.focus();
    return false;
  }
       var answer=document.forms[0].answer.value;
         
         var splChars = "'";
for (var i = 0; i < answer.length; i++) {
    if (splChars.indexOf(answer.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Answer"); 
     document.forms[0].answer.focus();
 return false;
}
}
	var url="addEmp.do?method=modifyEmployeeDetails";
	document.forms[0].action=url;
	document.forms[0].submit();
}



function onSave(){

  if(document.forms[0].loginName.value=="")
  {
    alert("Please Enter Login Name");
    document.forms[0].loginName.focus();
    return false;
  }
   var loginName=document.forms[0].loginName.value;
         
         var splChars = "'";
for (var i = 0; i < loginName.length; i++) {
    if (splChars.indexOf(loginName.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  LOGIN Name!"); 
     document.forms[0].loginName.focus();
 return false;
}
}
  
  if(document.forms[0].employeeNo.value=="")
  {
    alert("Please Enter Employee No");
    document.forms[0].employeeNo.focus();
    return false;
  }
   var employeeNo = document.forms[0].employeeNo.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(employeeNo)) {
             alert("Employee NO.  Should be Integer ");
                document.forms[0].employeeNo.focus();
            return false;
        }
  
     if(document.forms[0].password.value=="")
  {
    alert("Please Enter Password");
    document.forms[0].password.focus();
    return false;
  }
    if(document.forms[0].activation.value=="")
  {
    alert("Please Select Activation");
    document.forms[0].activation.focus();
    return false;
  }
    if(document.forms[0].empStatus.value=="")
  {
    alert("Please Select Employee Type");
    document.forms[0].empStatus.focus();
    return false;
  }
   
  if(document.forms[0].favoriteQuest.value=="")
  {
    alert("Please Enter Favorite Question");
    document.forms[0].favoriteQuest.focus();
    return false;
  }
   var favoriteQuest=document.forms[0].favoriteQuest.value;
         
         var splChars = "'";
for (var i = 0; i < favoriteQuest.length; i++) {
    if (splChars.indexOf(favoriteQuest.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Favourite Question!"); 
     document.forms[0].favoriteQuest.focus();
 return false;
}
}
  if(document.forms[0].answer.value=="")
  {
    alert("Please Enter Answer");
    document.forms[0].answer.focus();
    return false;
  }
           var answer=document.forms[0].answer.value;
         
         var splChars = "'";
for (var i = 0; i < answer.length; i++) {
    if (splChars.indexOf(answer.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Answer"); 
     document.forms[0].answer.focus();
 return false;
}
}
	var url="addEmp.do?method=saveEmployee";
	document.forms[0].action=url;
	document.forms[0].submit();
}
</script>
	</head>

	<body>

		<div align="center">
			<logic:present name="addExistingEMPForm" property="message">
				<font color="red"> <bean:write name="addExistingEMPForm"
						property="message" /> </font>
			</logic:present>
		</div>
		<html:form action="addEmp.do" enctype="multipart/form-data">




			<input type="hidden" name="MenuIcon2"
				value="<%=request.getAttribute("MenuIcon")%>" />

			<table class="bordered">

<tr>
				<th colspan="5">
					ADD EMPLOYEE
				</th>
				</tr>
				<tr>
				<td>
					Login User Name
					<font color="red" size="3">*</font>
				</td>

				<td>
					<html:text property="loginName" styleClass="rounded"
						style="width:200px;"></html:text>
				</td>

				<td>
					Employee No
					<font color="red" size="3">*</font>
				<td>
					<html:text property="employeeNo" style="width:200px;"
						styleClass="rounded"></html:text>
					<html:hidden property="reqEmpNo" />
				</td>
				</tr>
				<tr>
					<td>
						Password
						<font color="red" size="3">*</font>
				</td>
				<td>
					<html:text property="password" style="width:200px;"
					styleClass="rounded"></html:text>
				</td>
					<td>
					Activation
					<font color="red" size="3">*</font>
		</td>
		<td>
					<html:select property="activation"
						style="background-color:#f6f6f6; width:150px; height:20px; border:#38abff 1px solid">
						<html:option value="">--SELECT--</html:option>
						<html:option value="On">On</html:option>
						<html:option value="Off">Off</html:option>
					</html:select>
				</td>
				
				</tr>
				<tr>
				
				<td>
					Employee Type
					<font color="red" size="3">*</font>
					</td>
				<td>
					<html:select property="empStatus"
						style="background-color:#f6f6f6; width:150px; height:20px; border:#38abff 1px solid">
						<html:option value="">--SELECT--</html:option>
						<html:option value="per">Permanent</html:option>
						<html:option value="temp">Temporary</html:option>
					</html:select>
				</td>
				
				<td>
					Favorite Question
					<font color="red" size="3">*</font>
					</td>
				<td>
					
					<html:select property="favoriteQuest" styleClass="content">
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
				<td>
					Answer
					<font color="red" size="3">*</font>
				<td colspan="4">
					<html:text property="answer" style="width:200px;"
						styleClass="rounded"></html:text>
						
	<html:hidden property="title"/>	
	<html:hidden property="fullName"/>		
	<html:hidden property="firstName"/>		
	<html:hidden property="middleName"/>		
	<html:hidden property="lastName"/>		
	<html:hidden property="location"/>		
	<html:hidden property="department"/>		
	<html:hidden property="designation"/>		
	<html:hidden property="contactNo"/>		
	<html:hidden property="emailID"/>		
	<html:hidden property="employeeGroup"/>		
				</td>


				</tr>
					

				<logic:notEmpty name="saveDetails">

					<td colspan=6 align="center" style="background-color: #F2F0F1;">
					

						<html:button property="method" value="Save" onclick="onSave()"
							styleClass="rounded"></html:button>
						<html:reset value="Reset" styleClass="rounded"></html:reset>
						<html:button property="method" value="Back" onclick="onBack()"
							styleClass="rounded"></html:button>
					</td>
					</tr>
				</logic:notEmpty>
				<logic:notEmpty name="modifyDetails">

					<td colspan="6" align="center" style="background-color: #F2F0F1;"
						styleClass="rounded">
					
						<html:button property="method" value=" Modify "
							onclick="onModify()" styleClass="rounded"></html:button>
						<html:button property="method" value=" Back " onclick="onBack()"
							styleClass="rounded"></html:button>
					</td>
					</tr>
				</logic:notEmpty>
			</table>

			</td>
			</tr>
			</table>
		</html:form>
	</body>

</html>