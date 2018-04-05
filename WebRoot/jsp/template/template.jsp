
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		
		<title>
		<bean:message key="Application.BuiltFor"/>::<bean:message key="Application.Name"/>::
		</title>
		
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
	
	
		<table width=100%>
			<tr><td colspan="2"><jsp:include page="header.jsp"/></td></tr>

			<tr>
			<td style="vertical-align: top;">
				<jsp:include page="../../template/mainMenu.jsp">
					<jsp:param value="Master" name="module"/>
					<jsp:param value="AdmissionType" name="subModule"/>
				</jsp:include>
			</td>
			<td>
		
			<td >

		
		
		
		
			</td></tr>
			<tr><td colspan="2"><jsp:include page="footer.jsp"/></td></tr>
		</table>
	</body>
</html>
