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
	<tr><th colspan="5">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Department</th><th>Designation</th></tr>
	<logic:iterate id="abc" name="apprList">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeename }</td><td>${abc.department }</td><td>${abc.designation }</td></tr>
	</logic:iterate>
</table>
	</logic:notEmpty>
		
		
		
		</center>
</body>
</html>		