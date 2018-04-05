<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'itPolicyList.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

  </head>
  
  <body>
   <font face="Arial">
<table class="bordered" style="font-size: 12" >
<tr>
<th colspan="5"><center>IT Policies List</center></th>
<tr><th><b><center>Sl.No.</center></b></th><th><center>Policy Name </center></th><th><center>Description</center></th>
<tr><td><center>1</center></td><td><a href="/EMicro Files/IT/Poliices/IT_Help_DESK.pdf" target="_blank" style="text-decoration: none;color: black;"><b><u>IT HelpDesk</u></b></a></td><td>Standard Operating Procedure for IT Helpdesk</td></tr>
<tr><td><center>2</center></td><td><a href="/EMicro Files/IT/Poliices/Internet_Use_Policy.pdf" target="_blank" style="text-decoration: none;color: black;"><b><u>Internet Usage Policy</u></b></a></td><td>Applies to all Internet users (individuals working for the company,including permanent </br> full-time and part-time employees, contract workers, Trainees) who access the Internet </br>within the organization.</td></tr>
<tr><td><center>3</center></td><td><a href="/EMicro Files/IT/Poliices/IT Procurement Process.pdf" target="_blank" style="text-decoration: none;color: black;"><b><u>IT Procurement Policy</u></b></td><td>New Requirement/Replacement Of Desktop/Laptop/Printers and  IT Consumables such </br>as Toners, Cartridges, CD's.</td></tr>

</tr>
</table>
  </body>
</html>
