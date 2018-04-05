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
<div>
		 <table class="bordered" style="position: relative;left: -2%;width: 85%;">
		 <tr><th colspan="3"><center><big>Travel Desk Approver Details</big> </center> </th></tr>
		 <tr>
		   <th>Travel desk Name</th><th>Department</th><th>Designation</th>
		 </tr>
		 <logic:iterate id="a" name="apprList">
		 	<tr>
		 		
		 		<td>${a.travel_desk_type }</td>
		 		<td>${a.department }</td>
		 		<td>${a.designation }</td>
		 	</tr>
		 </logic:iterate>
		 </table>
		 </div>
		</logic:notEmpty>
		
		
		<logic:notEmpty name="noapprList">
		 <table class="bordered" >
		 <tr ><th colspan="3"><center>Approvers List</center> </th></tr>
		 <tr>
		  <th>Travel desk Name</th><th>Department</th><th>Designation</th>
		 </tr>
		 	<tr>
		 		<td colspan="3">
		 		<center><font color="red" size="2">No Approvers Assigned</font>  </center></td>
		 	</tr>
		
		 </table>
		</logic:notEmpty>
		</center>
</body>
</html>		