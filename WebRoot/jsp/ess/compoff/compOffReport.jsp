<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

  <head>
   <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
    <base href="<%=basePath%>">
    
    <title>My JSP 'LeaveReport.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/sorttable.js"></script>
	<link rel="stylesheet" type="text/css" href="css/styles.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
    <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   
<html:form action="leave" enctype="multipart/form-data">


<link rel="stylesheet" type="text/css" href="Table.css">
</head>
<table  align="center">
<tr>
<td ><center>
<img  src="images/logo.png" width='80' height='90'></center>
</td>
<th><font color="blue"><big><strong>
 MICRO LABS LIMITED</strong></big></font></th>


</tr>

</table>
</br>


<table  class="bordered">


<tr ><td ><b>Employee No.</b></td><td><bean:write name="leaveForm" property="employeeNumber"></bean:write></td>
<td ><b>Name</b></td><td><bean:write name="leaveForm" property="employeeName"></bean:write></td>

</tr>

<tr>
<td ><b>Designation</b></td><td><bean:write name="leaveForm" property="designation"></bean:write></td>
<td ><b>Department</b></td><td><bean:write name="leaveForm" property="department"></bean:write></td>

</tr>

<tr>
<td   ><b>Date Of Joining</b></td><td><bean:write name="leaveForm" property="doj"></bean:write></td>
<td  ><b>Year</b></td><td><bean:write name="leaveForm" property="year"></bean:write></td>
</tr>

</table>


<br>


<table class="bordered">

<tr align="center">

<th colspan="1"><center>Comp-Off Applied</center></th>

<th rowspan="2"><center> Applied On</center></th>
<th rowspan="2"><center> Approved On</center></th>
<th rowspan="2"><center>Reason</center></th>


</tr>
<tr >
<th ><center>Date</center></th>



</tr>
<logic:notEmpty name="comp">
<logic:iterate id="a" name="comp">
<tr><td><center><bean:write name="a" property="startDate"/></center></td>

<td><center><bean:write name="a" property="submitDate"/></center></td>
<td><center><bean:write name="a" property="approvedDate"/></center></td>
<td><bean:write name="a" property="reason"/></td></tr>
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="nocomp">
		<tr>
<td colspan="8">
<font color="red" size="3"><center>No Records </center></font>
</td>
</tr>
		
		</logic:notEmpty>

</table>



</html:form>
  </body>
</html>
