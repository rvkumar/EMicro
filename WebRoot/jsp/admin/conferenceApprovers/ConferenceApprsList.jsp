<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css2/micro_style.css" type="text/css" rel="stylesheet" />
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<title>Microlab</title>
<script type="text/javascript">
function addApprover(){
var URL="conferenceAppr.do?method=newApprover";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}


function editAppr(location,floor,roomname){
var URL="conferenceAppr.do?method=editApprover&loc="+location+"&floor="+floor+"&roomName="+roomname;
		document.forms[0].action=URL;
 		document.forms[0].submit();
} 

function deleteAppr(location,floor,roomname){

var r = confirm("Are you Sure You want to delete this record");
if (r == true) {
 var URL="conferenceAppr.do?method=deleteApprs&loc="+location+"&floor="+floor+"&roomName="+roomname;
		document.forms[0].action=URL;
 		document.forms[0].submit();
} else {
 return false;
} 


} 


function hideMessage(){
	
	document.getElementById("messageID").style.visibility="hidden";	
}

function manageconf(){
	
	document.forms[0].action="conferenceAppr.do?method=manageConfroom"
	document.forms[0].submit();
}



</script>
</head>
<body>
<html:form action="conferenceAppr.do">

<div align="center" id="messageID" style="visibility: true;">
			<logic:present name="conferenceApprForm" property="message">
			<font color="green">
				<bean:write name="conferenceApprForm" property="message" />
			</font>
			<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
		</logic:present>
		<logic:present name="conferenceApprForm" property="message2">
			<font color="red">
				<bean:write name="conferenceApprForm" property="message2" />
			</font>
			<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
		</logic:present>
		</div>
<html:button property="method" value=" New " styleClass="rounded" onclick="addApprover()" />&nbsp;&nbsp;&nbsp;

<html:button property="method" value=" Manage Conference Room " styleClass="rounded" onclick="manageconf()" />&nbsp;&nbsp;&nbsp;

<div>&nbsp;</div>
<logic:notEmpty  name="Approvers">
<div style="width: 80%">
<table align="center" class="bordered">
<tr><th>Location</th><th>Floor</th><th>Conference Room</th><th>Total Approvers</th><th>Edit</th><th>Delete</th>
</tr>
<logic:iterate id="a" name="Approvers" >

<tr>
<td><bean:write name="a" property="locationId"/></td>
<td><bean:write name="a" property="floor"/></td>
<td><bean:write name="a" property="roomName"/></td>


<td><bean:write name="a" property="totalRecords"/></td>
	<td><a href="#">
<img src="images/edit.png"  title="Edit Record" onclick="editAppr('${a.locationId}','${a.floor}','${a.roomName }')"/></a></td>
<td><a href="#">
<img src="images/delete.png"  title="Delete Record" onclick="deleteAppr('${a.locationId}','${a.floor}','${a.roomName }')"/></a></td>
</tr>
</logic:iterate>
	
	</logic:notEmpty>
	</div>
	</table>
	<logic:notEmpty name="noApprovers">
	<table align="center" class="bordered">
<tr><th>Location</th><th>Category</th><th>Sub Category</th><th>Total Approvers</th><th>Edit</th>
</tr>
<tr>
<td colspan="5"><center> <font color="red" size="3">No approvers assigned</font></center></td>
</tr>
	</logic:notEmpty>
</html:form>
</body>
</html>