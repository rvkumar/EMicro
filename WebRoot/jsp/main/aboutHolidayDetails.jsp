<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Holiday Details</title>
</head>
<body >
<html:form action="/main.do">

<table align="center" border="1">
<tr><td>
Day</td><td><bean:write name="mainForm" property="reqDay"/></td></tr>

<tr><td>
Details</td><td><bean:write name="mainForm" property="dayDetails"/></td></tr>


</table>
</html:form>


</body>
</html>