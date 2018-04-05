<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>


<%@page import="java.sql.Statement"%>
<%@page import="com.microlabs.db.ConnectionFactory"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.awt.*" %>
<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.jfree.chart.plot.*"%>
<%@page import="org.jfree.data.general.*"%>
<%@page import="org.jfree.data.*"%>
<%@page import="org.jfree.data.category.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'displayReport.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

  </head>
  
  <body>
  <html:form action="helpDeskReport.do">
  
  <table><tr><td>
<table class="bordered" style="width: 65%"> 
<tr><th><center>Summary</center></th></tr>
<tr><td>
In-Process Requests  &nbsp; &nbsp;&nbsp;  &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<a href="helpDeskReport.do?method=displayMyRequestList&reqType=In Process"><bean:write name="helpdeskReportForm" property="inprocesscount"/></a>
<br><br>
<hr/>
<br>
  Open Requests&nbsp;&nbsp;&nbsp;  &nbsp;  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<a href="helpDeskReport.do?method=displayMyRequestList&reqType=Open"><bean:write name="helpdeskReportForm" property="opencount"/></a>

<br><br><hr/><br>

Closed Requests&nbsp; &nbsp;&nbsp;  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<a href="helpDeskReport.do?method=displayMyRequestList&reqType=Closed"><bean:write name="helpdeskReportForm" property="closedcount"/></a>
<br><br>
</td>


</tr>

</table>
</td>
<td>

<table class="bordered" style="width: 60%">
<tr>

<th ><center>Graph</center></th></tr>
<tr>
<td>
						
	<% 
	String id=session.getId(); 
	
	%>
				 	
	<img src="<%="images/graph/"+id+".jpg?time=" + new Date() %>" width="150" height="200" style="height: 183px; width: 270px"/>

</td>
</tr>



</table>
</td></tr>
</table>
</html:form>
  </body>
</html>
