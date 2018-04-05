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
<tr>
<td>OnDuty for approval of CMD / MD / Director</td>
<td align="right">Page No.</td>

</tr>
</table>

<br/>
<logic:notEmpty name="ondutyList">	



		<table class="bordered sortable" >
		
		<tr>
		<th colspan="14"><center><big>OnDuty List</big></center></th>
		</tr>
		<tr>
		<th>Sl. No</th><th>Emp No.</th><th>Name</th><th>Department</th><th>Designation</th><th>Onduty Type</th><th>Location </th><th>Requested On</th><th>From Date</th><th>From Time</th><th>To Date</th><th>To Time</th>
		<th>Reason</th><th>Approved</th>
			  </tr>
<%
int i=1;
%>					
<logic:iterate id="leave" name="ondutyList">

<tr>
<td colspan="1"><%=i%> </td>
<td colspan="1"><bean:write name="leave" property="employeeNumber"/></td>
<td colspan="1"><bean:write name="leave" property="employeeName"/></td>
<td colspan="1"><bean:write name="leave" property="department"/></td>
<td colspan="1"><bean:write name="leave" property="designation"/></td>
<td colspan="1"><bean:write name="leave" property="onDutyType"/></td>
<td colspan="1"><bean:write name="leave" property="locationId"/></td>
<td colspan="1"><bean:write name="leave" property="submitDate"/></td>
<td colspan="1"><bean:write name="leave" property="startDate"/></td>
<td colspan="1"><bean:write name="leave" property="startTime"/></td>
<td colspan="1"><bean:write name="leave" property="endDate"/></td>
<td colspan="1"><bean:write name="leave" property="endTime"/></td>
<td colspan="1"><bean:write name="leave" property="reason"/></td>

<td colspan="1" style="width: 114px; "><bean:write name="leave" property="approver"/></td>

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
</body>
</html>      