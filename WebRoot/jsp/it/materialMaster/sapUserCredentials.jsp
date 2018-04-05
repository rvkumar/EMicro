<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/sorttable.js"></script>
 <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 
 <script type='text/javascript' src="calender/js/zapatec.js"></script>
 
 <script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
 
<script type="text/javascript">


function createInSAP()
{


if(document.forms[0].username.value=="")
{
alert("Please Enter User Name");
document.forms[0].username.focus();
return false;
}

if(document.forms[0].password.value=="")
{
alert("Please Enter Password ");
document.forms[0].password.focus();
return false;
}

var url="materials.do?method=sapSaveCredentials";
		document.forms[0].action=url;
		document.forms[0].submit();

}

</script>
</head>
<body onload="test()">
<html:form action="/materials.do" enctype="multipart/form-data">

<div align="center">
		<logic:notEmpty name="materialsForm" property="message">
		<font color="green" size="3">
			<b><bean:write name="materialsForm" property="message" /></b>
		</font>
	</logic:notEmpty>
	</div>
	<div align="center">
		<logic:present name="materialsForm" property="message2">
		<font color="red" size="3">
			<b><bean:write name="materialsForm" property="message2" /></b>
		</font>
	</logic:present>
           
</div>
<div style="width: 60px">
<table class="bordered sortable" width="60%">
<tr>
	<th colspan="6"><big>Sap Credentials</big></th>
</tr> 
<tr>
<td><b>User Name</b> <font color="red">*</font></td>
<td align="left" width="34%">
<html:text property="username" styleId="username" styleClass="text_field" />
							</td>
</tr>
<tr>
<td><b>Password</b> <font color="red">*</font></td>

<td align="left" width="34%">
								<html:password property="password" styleId="password"  styleClass="text_field"/>
</td>
</tr>			
<tr>
<td colspan="2">
<center>
<html:button property="method" value="Submit" styleClass="rounded" onclick="createInSAP();"/>
</center>
</td>
</tr>

</table>
</div>
					
</html:form>
</body>
</html>