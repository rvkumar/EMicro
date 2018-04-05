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
      </head>
<body>
<html:form action="/approvals.do" enctype="multipart/form-data">
<div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>

<logic:notEmpty name="permissionList">	
<logic:iterate id="permission" name="permissionList">
<div style="width: 75%;">
		<table class="bordered sortable" width="75%">
			<tr><th colspan="4"><center>Permission Details</center></th></tr>
			<tr>
					<th style="width:200px;">Employee Name</th><td colspan="3"><bean:write name="permission" property="employeeName"/></td>
			</tr>
			<tr>		
					<th style="width:100px;">Permission Date</th><td colspan="3"><bean:write name="permission" property="startDate"/></td>
					
			</tr>
			<tr>		
					<th style="width:100px;"> Time</th><td colspan="3"><bean:write name="permission" property="startTime"/></td>
					
			</tr>		
							
					<th>Req Date</th><td ><bean:write name="permission" property="createDate"/></td>
					<th style="width:100px;">Status</th><td colspan="3"><bean:write name="permission" property="approver"/></td>
			</tr>
			<tr>		
					<th>Reason</th><td colspan="3"><bean:write name="permission" property="requestType"/> </td>
				</tr>
					
</table>
</div>
</logic:iterate>
</logic:notEmpty>
</html:form>
</body>
</html>