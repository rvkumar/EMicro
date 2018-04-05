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
<script type="text/javascript">
function showform()
{


var url="leave.do?method=displayOthersLeaveReport";
document.forms[0].action=url;
document.forms[0].submit();
}
</script>
  </head>
 <body>
   
<html:form action="leave" enctype="multipart/form-data">


<link rel="stylesheet" type="text/css" href="Table.css">
</head>
<!-- <table  align="center">
<tr>
<td ><center>
<img  src="images/logo.png" width='80' height='90'></center>
</td>
<th><font color="blue"><big><strong>
 MICRO LABS LIMITED</strong></big><br >EMPLOYEES PERMISSION APPLICATION CUM RECORD</font></th>
</td>
</tr>
</table> -->

<logic:notEmpty name="Approver">
<table class="bordered " width="130%">

<tr>
<th><b>Employee selection</b></th>
<td>
<html:select property="empno" styleClass="content" styleId="filterId" onchange="showform()">
<html:option value="">--Self--</html:option>
<html:options name="permissionForm"  property="emplIdList" labelProperty="empLabelList"/>
</html:select>
</td>

</tr>
</table>
</logic:notEmpty>
</br>
<%-- <table class="bordered">
<tr>
<th><b>Year</b></th>
<td>
<html:select property="empyear" styleClass="content" styleId="filterId" onchange="showform()">
<html:options name="permissionForm"  property="yearList" />
</html:select>
</td>
</tr></table> --%>
<br/>
<table  class="bordered">
<tr>

<th colspan="4"><center><b>EMPLOYEE PERMISSION APPLICATION CUM RECORD</b></center></th>

</tr>
<tr>
<th colspan="4">
EMPLOYEE DETAILS
</th>
</tr>

<tr ><td ><b>Employee No.</b></td><td><bean:write name="permissionForm" property="employeeNo"></bean:write></td>
<td ><b>Name</b></td><td><bean:write name="permissionForm" property="employeeName"></bean:write></td>

</tr>

<tr>
<td ><b>Designation</b></td><td><bean:write name="permissionForm" property="designation"></bean:write></td>
<td ><b>Department</b></td><td><bean:write name="permissionForm" property="department"></bean:write></td>

</tr>

<tr>
<td   ><b>Date Of Joining</b></td><td><bean:write name="permissionForm" property="doj"></bean:write></td>
<td  ><b>Year</b></td><td><bean:write name="permissionForm" property="year"></bean:write></td>
</tr>
<logic:notEmpty name="permissionBalenceList">
		
			<tr>
				<th colspan="4" align="left">Permission Balance in Minutes-<bean:write name="permissionForm" property="year"/></th>
			</tr>
		<tr style="font-size: 12">
			<th>Opening Balance</th><th>Availed</th><th>Closing Balance</th><th>Awaiting for Approval</th>
			</tr>
			
						<tr>
						
						<td><bean:write name="permissionForm" property="opbal"/></td>
						<td><bean:write name="permissionForm" property="availbal"/></td>
						<td><bean:write name="permissionForm" property="closbal"/></td>
						<td><bean:write name="permissionForm" property="awtbal"/></td>
						</tr>	
					
	
		</logic:notEmpty>
<logic:empty name="permissionBalenceList">

<tr >
				<th colspan="4" align="left">Permission Balance in Minutes-<bean:write name="permissionForm" property="year"/></th>
			</tr>
		<tr>
			<th>Opening Balance</th><th>Availed</th><th>Closing Balance</th><th>Awaiting for Approval</th>
			</tr>
			<tr>
						
						<td colspan="4"><center><b>NO BALANCE FOUND</b></center></td>
						</tr>	
</logic:empty>
</table>



<table class="bordered">

<tr style="font-size: 12" align="center">
<th colspan="2"><center>Permission Applied</center></th>


<th rowspan="2"><center> Applied On</center></th>
<th rowspan="2"><center>No.of <br>Minutes</center></th>
<th rowspan="2"><center> Approved On</center></th>
<th rowspan="2"><center>Reason</center></th>


</tr>
<tr style="font-size: 12" >
<th ><center>From Time</center></th>
<th ><center>To Time</center></th>


</tr>
<logic:iterate id="a" name="list">
<tr>

<td><center><bean:write name="a" property="startTime"/></center></td>
<td><center><bean:write name="a" property="endTime"/></center></td>
<td><center><bean:write name="a" property="date"/></center></td>
<td><center><bean:write name="a" property="availbal"/></center></td>
<td><center><bean:write name="a" property="approveDate"/></center></td>
<td><bean:write name="a" property="reason"/></td></tr>
</logic:iterate>
</table>

<logic:notEmpty name="norecords">
<table class="bordered">


<tr><td colspan="6"><font color="red"><center>No Records</center> </font></td></tr>
</table>

</logic:notEmpty> 





</html:form>
  </body>
</html>
