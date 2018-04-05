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
 <script type="text/javascript">
  function myFunction()
{
window.print();
}
</script>    
</head>
<body>
<font face="Cambria">
<html:form action="/approvals.do" enctype="multipart/form-data">


<table >
<tr>
<td>
<table width="100%">
<tr>
<td rowspan="3"><img src="images/logo.png" height="50" width="50"/>
</td>
<td><big><b>MICRO LABS LIMITED</b></big></td> 

<td align="right">Printed On:<bean:write property="date" name="approvalsForm"></bean:write></td>
<td> <a href="javascript:window.print();"><img src="images/print.gif" align="absmiddle" title="Print" ></a></td>
</tr>

</table>

<br/>
<logic:notEmpty name="leaveList">	



		<table class="bordered sortable" >
		
		<tr>
		<th colspan="11"></th><th colspan="3">Leave&nbsp;Balance</th><th></th>
		</tr>
		<tr>
		<th>Sl. No</th><th>Emp No.</th><th>Emp Loc.</th><th>Name</th><th>Department</th><th>Designation</th><th>Leave Type</th><th>Requested On</th><th>From</th><th>To</th><th>No Of Days</th>
		<th>Reason</th><th>CL</th><th>SL</th><th>EL</th><th>Status</th>
			   <!--<tr>
			
				<th rowspan="2">Sl. No</th><th rowspan="2">Emp No.</th><th rowspan="2">Name</th><th rowspan="2">Department</th><th rowspan="2">Designation</th><th rowspan="2">Leave Type</th><th rowspan="2">Requested On</th>
					<th rowspan="2">From</th><th rowspan="2">To</th>
					<th rowspan="2">No Of Days</th><th rowspan="2">Reason</th>
					<th colspan="3" rowspan="2"><table><tr><th rowspan="1">Leave Balance</th></tr><tr>
					<th rowspan="2"  style="width: 1px;">CL</th><th rowspan="2">SL</th><th rowspan="2">EL</th>
					</tr>
					</table></th>
					
					
				
					<th rowspan="2">Approved</th>
		
				--></tr>
<%
int i=1;
%>					
<logic:iterate id="leave" name="leaveList">

<tr>
<td colspan="1"><%=i%> </td>
<td colspan="1"><bean:write name="leave" property="employeeNumber"/></td>
<td colspan="1"><bean:write name="leave" property="locationId"/></td>
<td colspan="1"><bean:write name="leave" property="employeeName"/></td>
<td colspan="1"><bean:write name="leave" property="department"/></td>
<td colspan="1"><bean:write name="leave" property="designation"/></td>
<td colspan="1"><bean:write name="leave" property="leaveType"/></td>
<td colspan="1"><bean:write name="leave" property="submitDate"/></td>
<td colspan="1"><bean:write name="leave" property="startDate"/></td>
<td colspan="1"><bean:write name="leave" property="endDate"/></td>
<td colspan="1"><bean:write name="leave" property="noOfDays"/></td>
<td colspan="1"><bean:write name="leave" property="reason"/></td>
<td colspan="1"><bean:write name="leave" property="cl"/></td>
<td colspan="1"><bean:write name="leave" property="sl"/></td>
<td colspan="1"><bean:write name="leave" property="pl"/></td>
<td colspan="1"><bean:write name="leave" property="approveStatus"/></td>

</tr>
<%
i++;
%>
</logic:iterate>
</table>

</logic:notEmpty>

<logic:notEmpty name="ondutyList">

<table class="sortable bordered">
<tr >
	<th>Sl. No</th><th>Req&nbsp;No</th><th>Emp No.</th><th style="width:200px;">Name</th><th>Department</th><th>Designation</th><th style="width:100px;">OnDuty&nbsp;Type&nbsp;</th><th style="width:100px;">Plant</th>
	<th style="width:100px;">Start Date</th><th style="width:100px;">From&nbsp;Time</th><th style="width:100px;">End Date</th><th style="width:100px;">To Time</th>
	<th style="width:100px;">Req Date</th><th style="width:100px;">Status</th><th>Approver Name</th>
</tr>
<%
int i=1;
%>
<logic:iterate id="onDuty" name="ondutyList">
<tr>

<td colspan="1"><%=i%> </td>
<td><bean:write name="onDuty" property="requestNumber"/></td>
<td><bean:write name="onDuty" property="employeeNumber"/></td>
<td><bean:write name="onDuty" property="employeeName"/></td>
<td><bean:write name="onDuty" property="department"/></td>
<td><bean:write name="onDuty" property="designation"/></td>

<td><bean:write name="onDuty" property="onDutyType"/></td>

<td><bean:write name="onDuty" property="locationId"/></td>
<td><bean:write name="onDuty" property="startDate"/></td>
<td><bean:write name="onDuty" property="startTime"/></td>
<td><bean:write name="onDuty" property="endDate"/></td>
<td><bean:write name="onDuty" property="endTime"/></td>
<td><bean:write name="onDuty" property="submitDate"/></td>
<td><bean:write name="onDuty" property="status"/></td>
<td><bean:write name="onDuty" property="approver"/></td>
</tr>
<%
i++;
%>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="Permissionlist">
<table class="sortable bordered">

<tr>
	<th>Sl. No</th><th>Req&nbsp;No</th><th >Employee No.</th><th style="width:200px;"> Name</th><th>Department</th><th>Designation</th><th>Permission Date</th>
	<th style="width:100px;">Start Time</th><th style="width:100px;">End Time</th><th>Request Date</th><th>Type</th>
	<th style="width:100px;">Status</th><th>Approver Name</th>
</tr>
<%
int i=1;
%>
<logic:iterate id="onDuty" name="Permissionlist">
<tr>

<td colspan="1"><%=i%> </td>
<td><bean:write name="onDuty" property="requestNumber"/></td>
<td><bean:write name="onDuty" property="employeeNumber"/></td>
<td><bean:write name="onDuty" property="employeeName"/></td>
<td><bean:write name="onDuty" property="department"/></td>
<td><bean:write name="onDuty" property="designation"/></td>
<td><bean:write name="onDuty" property="permissiondate"/></td>



<td><bean:write name="onDuty" property="startTime"/></td>
<td><bean:write name="onDuty" property="endTime"/></td>
<td><bean:write name="onDuty" property="createDate"/></td>
<td><bean:write name="onDuty" property="type"/></td>
<td><bean:write name="onDuty" property="status"/></td>
<td><bean:write name="onDuty" property="approver"/></td>

</tr>
<%
i++;
%>
</logic:iterate>
</table>
</logic:notEmpty>
<br/>
</td></tr></table>
</html:form>
</font>
</body>
</html>      