<%@page import="com.microlabs.utilities.UserInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
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

	<body onload="document.forms[0].group_name.focus()">
	<%
	UserInfo user=(UserInfo)session.getAttribute("user");
	 %>
		<table width=100%>
			<tr><td colspan="2"><jsp:include page="../../template/header.jsp"/></td></tr>

			<tr>
			<td style="vertical-align: top; width: 20%;">
				<jsp:include page="../../template/mainMenu.jsp">
					<jsp:param value="User Management" name="module"/>
					<jsp:param value="Users" name="subModule"/>
				</jsp:include>
			</td>
			<td style="vertical-align: top;">
			
			<div align="center">
				<logic:present name="masterForm" property="message">
						<font color="red">
							<bean:write name="masterForm" property="message" />
						</font>
				</logic:present>
			</div>
					<table border="0" align="center" cellpadding="3" id="mytable" >
					
					<tr><th colspan=2 align=center>Add User Group</th></tr>
					<tr><th class="spec">Group Name</th><td><input type="text" name="group_name" value="" onclick='form_validate()'></td></tr>
					<tr><td colspan=2><font color=red><small><b>Please Select the Main Module First</b></small></font></td></tr>	
					<tr><th class="specalt">Select Module</th>
						<td class="lft">
						<select name="module1">
							<option>---</option>
						</select>
						</td>
					</tr>
					<tr><td colspan=2>
					</td></tr>
					</table>
		
		
		
			</td></tr>
			
			
			
			
			
			
			
			
			
			
			
			<tr><td colspan="2"><jsp:include page="../../template/footer.jsp"/></td></tr>
		</table>
	</body>
</html>
