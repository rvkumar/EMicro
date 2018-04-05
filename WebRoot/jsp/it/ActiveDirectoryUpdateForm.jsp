<%@page import="com.microlabs.utilities.UserInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	
		<base href="<%=basePath%>">
		
		<title>
		IT Request Form
		</title>
		
		
		<script type="text/javascript">
function onSubmit(){
var url="activeDirectory.do?method=updateADRequestAction&savedType=Inbox";
	document.forms[0].action=url;
	document.forms[0].submit();
}



function OnRequest(){

var url="activeDirectory.do?method=displayRequriedADForm";
	document.forms[0].action=url;
	document.forms[0].submit();
}
	
</script>
	</head>
	<body onload="document.forms[0].groupName.focus()">
	<%
	UserInfo user=(UserInfo)session.getAttribute("user");

	
	 %>

		<table width=100%>
			<tr><td colspan="2"><jsp:include page="/jsp/template/header.jsp"/></td></tr>
			
			<tr>
			<td style="vertical-align: top; width: 20%">
				<jsp:include page="/jsp/template/mainMenu.jsp">
					<jsp:param value="User Management" name="module"/>
					<jsp:param value="User Management" name="subModule"/>
				</jsp:include>
			</td>
			<td valign="top">
		<% 
		String status=(String)session.getAttribute("status");		
		if(status==""||status==null)
		{
		
		}
		else{
		
		%>
		<b><font color="red"><%=status %></font></b>
		<%
		session.setAttribute("status", " ");
		}
		 %>
<html:form action="/activeDirectory.do" enctype="multipart/form-data">
<html:select property="changeReqForm" name="activeDirectoryForm" style="margin-left:100" onchange="OnRequest()">
		    <html:option value="Select Drafts">Select Drafts</html:option> 			
		    <html:option value="Drafts">Drafts</html:option> 
		
		    
	</html:select>
	<table class=forumline align=center width='100%' border="1"> 
	<tr>
		<td Class="head" align=center colspan=5 bgcolor="4372B7"><font color="white"><center>Ad User Creation (WF)</center>		</font>
		</td>
	</tr>
	<tr>
	<td>
	Employee Number<html:text property="employeeNo" style="margin-left:85"></html:text>
	</td>
	<td>
	Request Id<html:text property="reqADId" style="margin-left:45"></html:text>
	</td>
	</tr>
	<tr>
	<td style="margin-left:100">
	First Name<html:text property="firstName" style="margin-left:137"></html:text>
	</td>
	<td>Last Name<html:text property="lastName" style="margin-left:50"></html:text>
	</td></tr>
	<tr>
	<td>Designation<html:text property="designation" style="margin-left:130"></html:text>
	</td>
	<td>Department<html:text property="department" style="margin-left:40"></html:text>
	</td></tr>
	<tr><td>Location<html:text property="location" style="margin-left:155"></html:text>
	</td>
	<td>Contact Details<html:text property="contactDetails" style="margin-left:20"></html:text>
	</td>
	</tr>	
	<tr>
	<td>Asset Details<html:text property="assetDetails" style="margin-left:120"></html:text></td>
	<td>Host Name<html:text property="hostName" style="margin-left:50"></html:text></td>
	</tr>
	<tr><td>IP Number<html:text property="IPNumber" style="margin-left:140"></html:text>
	</td>
	<td>AD Login Name<html:text property="adLoginName" style="margin-left:15"></html:text>
	</td></tr>
	<tr><td>Required Folder Access<html:text property="requiredFolderAccess" style="margin-left:45"></html:text>
	</td>
	</tr>
</table>
<table class=forumline align=center width='100%' border="1">
	<tr>
	<td align="center">
	
	<html:button property="method" value="Submit" onclick="onSubmit()" ></html:button>
<html:reset value="Reset"></html:reset>
	
	
	</td>
	</tr>
</html:form>	
			</td></tr>
			<tr><td colspan="2"><jsp:include page="/jsp/template/footer.jsp"/></td></tr>
		</table>
	</body>
</html>
