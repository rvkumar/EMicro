<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
<div>&nbsp;</div>
<center>
<logic:notEmpty name="apprList">

		 <table class="bordered">
		 <tr><th colspan="6"><center>Approvers List</center> </th></tr>
		 <tr>
		   <th>Priority</th><th>Location</th><th>Employee Number</th><th>Name</th><th>Role</th>
		 </tr>
		 <logic:iterate id="a" name="apprList">
		 	<tr>
		 		<td>${a.priority1 }</td>
		 		<td>${a.locationId }</td>
		 		<td>${a.employeeNumber }</td>
		 		<td>${a.empName }</td>
		 		<td>${a.role1 }</td>
		 	</tr>
		 </logic:iterate>
		 </table>
		</logic:notEmpty>
		
		
		<logic:notEmpty name="noapprList">
		 <table class="bordered">
		 <tr><th colspan="6"><center>Approvers List</center> </th></tr>
		 <tr>
		   <th>Priority</th><th>Location</th><th>Employee Number</th><th>Name</th><th>Role</th>
		 </tr>
		 	<tr>
		 		<td colspan="6">
		 		<center><font color="red" size="2">No Approvers Assigned</font>  </center></td>
		 	</tr>
		
		 </table>
		</logic:notEmpty>
		</center>
</body>
</html>		