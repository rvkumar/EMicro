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
   <br/>
<a href="javascript:window.print()"><img src="jsp/hr/print.png" title="Print this Page"></a>
<br/><br/>
<html:form action="leave" enctype="multipart/form-data">


<link rel="stylesheet" type="text/css" href="Table.css">
</head>
<table  align="center">
<tr>
<td ><center>
<img  src="images/logo.png" width='80' height='90'></center>
</td>
<th><font color="blue"><big><strong>
 MICRO LABS LIMITED</strong></big><br >EMPLOYEES LEAVE APPLICATION CUM RECORD</font></th>
</td>
</tr>
</table>
</br>
<logic:notEmpty name="Approver">
<table class="bordered " width="130%">
<tr>
<th><b>Employee selection</b></th>
<td>
<html:select property="empno" styleClass="content" styleId="filterId" onchange="showform()">
<html:option value="">--Self--</html:option>
<html:options name="leaveForm"  property="emplIdList" labelProperty="empLabelList"/>
</html:select>
</td>

</tr>
</table>
</logic:notEmpty>
</br>
<table class="bordered">
<tr>
<th><b>Year</b></th>
<td>
<html:select property="empyear" styleClass="content" styleId="filterId" onchange="showform()">
<html:options name="leaveForm"  property="yearList" />
</html:select>
</td>
</tr></table>
<br/>
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

<logic:notEmpty name="LeaveBalenceList">
		 <div style="width: 90%" >
		<table class="bordered" width="90%">
			<tr>
				<th colspan="5" align="left">Leave Balances (In Days)-<bean:write name="leaveForm" property="year"/></th>
			</tr>
		<tr>
			<th>Leave Type</th><th>Opening Balance</th><th>Availed</th><th>Closing Balance</th><th>Awaiting for Approval</th>
			</tr>
			<logic:iterate id="bal" name="LeaveBalenceList">
						<tr>
						<th><bean:write name="bal" property="leaveType"/></th>
						<td><bean:write name="bal" property="openingBalence"/></td>
						<td><bean:write name="bal" property="avalableBalence"/></td>
						<td><bean:write name="bal" property="closingBalence"/></td>
						<td><bean:write name="bal" property="awaitingBalence"/></td>
						</tr>	
			</logic:iterate>			
		</table>
		</div>
		</logic:notEmpty>

</br>		


<table class="bordered">

<tr align="center">
<th rowspan="2"><center>Leave Type</center></th>
<th colspan="2"><center>Leave Applied</center></th>
<th rowspan="2"><center>No.of <br>Days</center></th>
<th rowspan="2"><center> Applied On</center></th>
<th rowspan="2"><center> Approved On</center></th>
<th rowspan="2"><center>Reason</center></th>


</tr>
<tr >
<th ><center>From</center></th>
<th ><center>To</center></th>


</tr>
<logic:iterate id="a" name="list">
<tr><td align="left"><bean:write name="a" property="leaveType"/></td><td><center><bean:write name="a" property="startDate"/></center></td><td><center><bean:write name="a" property="endDate"/></center></td><td><center><bean:write name="a" property="noOfDays"/></center></td><td><center><bean:write name="a" property="submitDate"/></center></td><td><center><bean:write name="a" property="approvedDate"/></center></td><td><bean:write name="a" property="reason"/></td></tr>
</logic:iterate>
</table>

<logic:notEmpty name="norecords">
<table class="bordered">


<tr><td colspan="7"><font color="red"><center>No Records</center> </font></td></tr>
</table>

</logic:notEmpty>





</html:form>
  </body>
</html>
