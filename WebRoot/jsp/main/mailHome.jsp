<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("path...................."+basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>EMicro-Mail</title>
	<link href="style1/style.css" rel="stylesheet" type="text/css" />
	<link href="style/content.css" rel="stylesheet" type="text/css" />
	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>

	<script type='text/javascript' src="calender/js/zapatec.js"></script>
	<!-- Custom includes -->
	<!-- import the calendar script -->
	<script type="text/javascript" src="calender/js/calendar.js"></script>
	<!-- import the language module -->
	<script type="text/javascript" src="calender/js/calendar-en.js"></script>
	<!-- other languages might be available in the lang directory; please check your distribution archive. -->
	<!-- ALL demos need these css -->
	<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

	<!-- Theme css -->
	<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript">
		function getMailFolder(){
			var pwd = document.getElementById("pswdInput").value;
			if(pwd == ""){
				alert("Please Enter the Password!");
				document.getElementById("pswdInput").focus();
				return false;
			}
			else{
				document.forms[0].action="mail.do?method=getMailForUser";
				document.forms[0].submit();
			}
		}
	</script>
  </head>
  <body>
  	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-left:15px">
   		<tr>
    		<td width="95%" align="center" valign="top"><div class="middel-blocks-iframe">
    		<html:form action="mail.do" enctype="multipart/form-data">
      			<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
        			<tr>
          				<td align="left"><logic:notEmpty name="mailInboxForm">
      						<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td width="3px"></td><td class="heading">Mail</td></tr>
           						<tr><td width="3px"></td><td class="underline"></td></tr>
           					</table></logic:notEmpty>
          				</td>
          			</tr>
               		<tr>
               			<td align="left" valign="top">
               				<table width="100%" style="padding-left:5%;">
								<tr><td><bean:write name="mailInboxForm" property="mailMessage"/></td></tr>
           					</table>
               			
               			</td>
       				</tr>
       				<tr style="height:25px;"></tr>
       				<logic:notEmpty name="EnterMailPwd">
       				<tr>
               			<td align="left">
               			<table>
               				<tr><td colspan="2" style="text-align:center;color:red;" id="successful"><bean:write name="mailInboxForm" property="message"/></td></tr>
               				<tr style="height:25px;"></tr>
               				<tr>
               				<th style="width:250px;text-align:right;" class="specalt" scope="row">Mail Password <img src="images/star.gif" width="8" height="8" />&nbsp;&nbsp;</th>
							<td><html:password name="mailInboxForm" property="password"  styleId="pswdInput" styleClass="rounded"/>&nbsp;<html:button styleClass="rounded" property="method" onclick="getMailFolder()" value="getMails"></html:button></td>
							</tr>
               			</table>
               			</td>
       				</tr>
       				</logic:notEmpty>
        			
    			</table>
    			</html:form>
    		</td>
   		</tr>
   	</table>
  </body>
</html>
