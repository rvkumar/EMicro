<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>
		<logic:notEmpty name="infoList">
	<table style="text-align:center;width:100%;">
		<logic:iterate name="infoList" id="abc">
			<tr> 
				<td width="30%"><br/><bean:write name="abc" property="empEmailID"/>&nbsp;<b>Designation:</b>&nbsp;<bean:write name="abc" property="empDesignation"/></td>
			</tr>
		</logic:iterate>
	</table>
		</logic:notEmpty>
</body>
</html>