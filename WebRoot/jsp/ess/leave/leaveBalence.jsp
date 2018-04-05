<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      
  <script type="text/javascript">
  function statusMessage(message){
alert(message);
}
  
  </script>
</head>


<body>
<html:form action="leave" enctype="multipart/form-data">
<div align="center">
				<logic:notEmpty name="leaveForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="leaveForm" property="message" />');
					</script>
				</logic:notEmpty>
				<logic:notEmpty name="leaveForm" property="message2">
					
					<script language="javascript">
					statusMessage('<bean:write name="leaveForm" property="message2" />');
					</script>
				</logic:notEmpty>
			</div>

<logic:notEmpty name="LeaveBalenceList">
		 <div style="width: 90%" >
		<table class="bordered" width="90%">
			<tr>
				<th colspan="5" align="left">Leave Balances (In Days)-<big><b><bean:write name="leaveForm" property="year"/></b></big></th>
			</tr>
		<tr>
			<th>Leave Type</th><th>Opening Balance</th><th>Availed</th><th>Closing Balance</th><th>Leaves Awaiting for Approval</th>
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
		
		<logic:notEmpty name="No LeaveBalence">
		<table class="bordered" width="90%">
			<tr>
				<th colspan="5" align="left">Leave Balances (In Days)-<bean:write name="leaveForm" property="year"/></th>
			</tr>
		<tr>
			<th>Leave Type</th><th>Opening Balance</th><th>Availed</th><th>Closing Balance</th><th>Leaves Awaiting for Approval</th>
			</tr>
			<tr>
			<td colspan="5"><center><font color="Green"><bean:write name="leaveForm" property="message2" /></font></center> </td>
			</tr>
			</table>
		
			</logic:notEmpty>
		</html:form>
</body>		

</html>