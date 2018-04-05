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
<td>Leaves for approval of CMD / MD / Director</td>
<td align="right">Page No.</td>

</tr>
</table>

<br/>
<logic:notEmpty name="leaveList">	



		<table class="bordered sortable" >
		
		<tr>
		<th colspan="11"></th><th colspan="3">Leave&nbsp;Balance</th><th></th>
		</tr>
		<tr>
		<th>Sl. No</th><th>Emp No.</th><th>Name</th><th>Department</th><th>Designation</th><th>Leave Type</th><th>Requested On</th><th>From</th><th>To</th><th>No Of Days</th>
		<th>Reason</th><th>CL</th><th>SL</th><th>EL</th><th>Approved</th>
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
<br/>
</td></tr></table>
</html:form>
</body>
</html>      