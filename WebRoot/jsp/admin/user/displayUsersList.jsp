<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<link rel="stylesheet" type="text/css" href="css/styles.css" />

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css2/micro_style.css" type="text/css" rel="stylesheet" />
<title>Microlab</title>
<script type="text/javascript">




function search(){
	var form = document.getElementById('searchSidFrm');
	form.action="user.do?method=searchSid";
	form.submit();
}




function deptSelected(){
	var form = document.getElementById('searchSidFrm');
	form.action="user.do?method=searchSid";
	form.submit();
}


function searchUsers()
{
	document.forms[0].action="addUser.do?method=searchUser";
	document.forms[0].submit();
}

function sendId(uName)
{
	opener.document.forms[0].userName.value = uName;
	
	window.open('addUser.do?method=searchUser','_parent','');
	window.close();
}


	




//-->
</script>
</head>

<body>
		<!--------WRAPER STARTS -------------------->

       
            
        
					
					<html:form action="addUser.do">
					
		<logic:notEmpty name="userDetails">
								<table  align=center width='60%' border="1">
									<tr>
										<td align=center colspan=6 bgcolor="#51B0F8">
											<font color="white">User Lists</font>
										</td>
										
									</tr>
									
									<tr height='20'>
									
										<th class="specalt" align=center>
											<b>Sl. No</b>
										</th>
										
										<th class="specalt" align=center>
											<b>User Name</b>
										</th>
										
										<th class="specalt" align=center>
											<b>Full Name</b>
										</th>
										
										<th class="specalt" align=center>
											<b>Select</b>
										</th>
										
									</tr>
									<%
									int count = 1;
									%>
									<logic:iterate name="userDetails" id="abc">
										<tr>
										
											<td class="lft">
												<a href="#">
													<%=count++%></a>
											</td>
											
											<td>
												<bean:write name="abc" property="userName"/>
											</td>
											
											<td>
												<bean:write name="abc" property="fullName"/>
											</td>
											
											<td class="lft">
												<a href="javascript:sendId('<bean:write name="abc" property="userName"/>')">
													Select</a>
											</td>
											
											
											</tr>
									</logic:iterate>

									</logic:notEmpty>
								</table>


							</html:form>
            
            

<!-------------- FOOTER STARTS ------------------------->
	
</body>
</html>
