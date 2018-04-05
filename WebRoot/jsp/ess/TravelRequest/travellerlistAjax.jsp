<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
	<select name="userlist" id="userlistId" >
		<option value="0">All</option>
		<logic:notEmpty name="techList">
		<logic:iterate id="a" name="techList">
		<option value='<bean:write name="a" property="id"/>-<bean:write name="a" property="guestName"/>'>
		<bean:write name="a" property="guestName"/></option>
		</logic:iterate>
		</logic:notEmpty>
	</select>
</body>
</html>	