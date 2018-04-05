<%@page import="com.microlabs.utilities.UserInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
		<base href="<%=basePath%>">
		<title>
		  Employee List
		</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
        <!-- <link rel="stylesheet" type="text/css" href="styles.css">-->
		<script type="text/javascript">
		function openPage(param){
		var str1 = "app";
		opener.document.forms[0].employeeNumber.value = document.forms[0].employeeName.value;
		opener.document.forms[0].designation.value = document.forms[0].designation.value;
		var theContents = document.getElementById('empName')[document.getElementById('empName').selectedIndex].innerHTML;
		opener.document.getElementById('app1').value = theContents;
		window.open('','_parent','');
		window.close();
		}
		</script>
	</head>
	<body>
	<%
	UserInfo user=(UserInfo)session.getAttribute("user");
	%>
<html:form action="/leave.do" enctype="multipart/form-data">
					<table border="1" align="center" width="30%" id="mytable">
						<tr><td bgcolor="#51B0F8" colspan="7"><font color="white">
						<center>Employee List</center>
						</font>
						</td></tr>
					    <tr>
							<th class="spec">Designation<img src="images/smallindex.jpg"/></th>
							
							<td>
							<html:select property="designation">
									<html:option value="">--Select--</html:option>
									<html:option value="1">Manager</html:option>
									<html:option value="2">IT Head</html:option>
									<html:option value="3">IT Head1</html:option>
									<html:option value="4">IT Head2</html:option>
									<html:option value="5">IT Head3</html:option>
							</html:select>
							</td>
						</tr>
						
						<tr>
							<th class="spec">Employee Names<img src="images/smallindex.jpg"/></th>
							
							<td>
							<html:select property="employeeName" styleId="empName">
									<html:option value="">--Select--</html:option>
								    <html:option value="7">RENJITH</html:option>
								    <html:option value="8">SUNIL</html:option>
								    <html:option value="9">AFSAL</html:option>
								    <html:option value="10">Akshay</html:option>
								    <html:option value="11">JOBIS</html:option>
							</html:select>
							</td>
						</tr>
						<bean:define id='id'  property='approverNumber'  name="leaveForm"/>
				<tr>
                    <td colspan="8">
                    <div align="center">
                     <html:button property="method" styleClass="button" value="Select" onclick="openPage('${id}')"/></div>
                    </td>
                </tr>
					</table>
					
				</html:form>
	</body>
</html>
