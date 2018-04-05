<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

</head>
<body>

<div style="width:300px; position: absolute;text-align: left; border-radius:5px;background-color: #fff;border: 1px solid #ccc;border-top-color: #d9d9d9;box-shadow: 0 2px 4px rgba(0,0,0,0.2);">
	<table width="100%">
	<logic:notEmpty name="SearchUserDetails">
	<logic:iterate name="SearchUserDetails" id="abc">

	<tr>
		
		
		<td id="<bean:write name="abc" property="employeeNo"/>" onmouseover="this.className = 'hover'" onmouseout="this.className = ''" onclick="selectUser('<bean:write name="abc" property="emp"/>','<bean:write name="abc" property="reqFieldName"/>');"><bean:write name="abc" property="emp"/><input id="selectuser" style="display:none;" value="<bean:write name="abc" property="employeeNo"/>"/></td>
	</tr>
	</logic:iterate>
	</logic:notEmpty>
	</table>
</div>
</body>
</html>
