<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="com.microlabs.ess.form.LeaveForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
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
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<title>Home Page</title>
</head>
<body >
	<div align="center"><logic:present name="onDutyForm" property="message"><font color="red"><bean:write name="onDutyForm" property="message" /></font></logic:present></div>
	<html:form action="onDuty" enctype="multipart/form-data">
		<table border="0" align="center" width="30%" >
			<logic:notEmpty name="submitDetails">
			<table border="1" align="center" width="30%" id="mytable1">
								<html:hidden property="id"/>
						<tr><td colspan=8 bgcolor="#51B0F8">
						<center><font color="white"> REQUEST DETAILS</font></center>
						</td></tr>
						<tr>
						    <td align="center" class="lft style1" colspan="2">
								Generated&nbsp;By
							</td>
							<td  align="center" class="lft style1" colspan="2">
							<bean:write name="onDutyForm" property="generatedBy"/>
							</td>
							<td align="center" class="lft style1" colspan="2">
								Submitted&nbsp;Date
							</td>
							<td align="center" class="lft style1" colspan="2">
							<bean:write name="onDutyForm" property="submitDate"/>
							</td>
						</tr>
						<tr>
						    <td align="center" class="lft style1" colspan="2">
								Leave&nbsp;Type
							</td>
							<td  align="center" class="lft style1" colspan="2">
							<bean:write name="onDutyForm" property="onDutyType"/>
							</td>
							<td align="center" class="lft style1" colspan="2">
								Holiday&nbsp;Type
							</td>
							<td align="center" class="lft style1" colspan="2">
							<bean:write name="onDutyForm" property="duration"/>
							</td>
						</tr>
						<tr>
							<td align="center" class="lft style1" colspan="2">
								Start&nbsp;Date
							</td>
							<td align="center" class="lft style1" colspan="2">
							<bean:write name="onDutyForm" property="startDate"/>
							</td>
							<td align="center" class="lft style1" colspan="2">
								End&nbsp;Date
							</td>
							<td align="center" class="lft style1" colspan="2">
							<bean:write name="onDutyForm" property="endDate"/>
							</td>
						</tr>
						<tr>
							<td align="center" class="lft style1" colspan="2">
								No&nbsp;Days
							</td>
							<td align="center" class="lft style1" colspan="2">
							<bean:write name="onDutyForm" property="noOfDays"/>
							</td>
						    <td align="center" class="lft style1" colspan="2">
								Reason
							</td>
			                <td  align="center" class="lft style1" colspan="2"> 
					        <bean:define id="content" name="onDutyForm"
									property="reason" />
							<%out.println(content.toString());%>
					        </td>
				       </tr>
				       
				       <tr><td colspan="8" align="center" class="lft style1" width="100%">
					<FCK:editor instanceName="EditorDefault" toolbarSet="Basic" width="100%">
								<jsp:attribute name="value">
				           <logic:present name="onDutyForm" property="reason">
                           <bean:define id="content" name="onDutyForm" property="reason" />
							<%out.println(content.toString());%>
                           </logic:present>
							<logic:notPresent name="onDutyForm" property="reason">
							<bean:define id="abc" value="null" />
							</logic:notPresent>
				              </jsp:attribute>
							</FCK:editor>
				</td></tr>
				       
				     <logic:notEmpty name="documentDetails">
						    <tr>
							<td align="center" class="lft style1" colspan="4">
								Documents
							</td>
							<td align="center" class="lft style1" colspan="4">
									<bean:define id="file" name="onDutyForm"
										property="documentName" />
								    <%
										String s = file.toString();
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) 
										{
									%>
									<a href="jsp/ess/onDuty/documents/<%=v[i]%>" target="_blank"><%=v[i]%></a>
									<%
									}
									%>	
									</td>
									</tr>	
				      </logic:notEmpty></table>
					</logic:notEmpty>
            </html:form>
</div>
</body>
</html>