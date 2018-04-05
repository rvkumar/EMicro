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
<table class="bordered" style="font-size: 12" width="30%">
<tr>
<th colspan="2"><center><big>MEDITIMES</big></br><small>Latest trending healthcare news from the Medical Services Team</small></center></th>
<tr><th><center>Month & Year</center></th><th ><center>Description </center></th>
<tr><td><center>November 2017</center></td><td><a href="pdf/Meditimes, Issue 38, Nov 2017.pdf" target="_blank" style="text-decoration: none;color: black;"><u><center>Vol.04, Issue 38</center></u></a></td>

</tr>

</table>
  </body>
</html>
