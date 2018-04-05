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

function newfloorroom()
{

	document.forms[0].action="vcAppr.do?method=addnewconference";
 	document.forms[0].submit();

}

function editRoom(loct,floor,room){
	
	document.forms[0].action="vcAppr.do?method=editRoomDetails&loc="+loct+"&Floor="+floor+"&room="+room+"";
 	document.forms[0].submit();
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
</head>
  
  <body>
  <html:form action="vcAppr.do">
  <html:button property="method" onclick="newfloorroom()" value="Add New Floor/Room" styleClass="rounded"></html:button>
  <br><br/>
<table class="bordered">
<tr>
<th>Sl.no</th><th>Location</th><th>Floor</th><th>RoomName</th><th>Status</th><th>Edit</th><th>Delete</th>

</tr>
<%int i=1; %>
<logic:iterate id="a" name="conflist">
<tr>
<td><%=i %></td><td>${a.locationId}</td><td>${a.floor}</td><td>${a.roomName}</td><td>${a.status}</td>
<td><a href="#">
<img src="images/edit.png"  title="Edit Record" onclick="editRoom('${a.locationId}','${a.floor}','${a.roomName}')"/></a></td>
<td><a href="#">
<img src="images/delete.png"  title="Delete Record" onclick="deleteRoom('${a.locationId}','${a.floor}','${a.roomName}')"/></a></td>

</tr>
<%i++; %>
</logic:iterate>
</table>
</html:form>
  </body>
</html>
