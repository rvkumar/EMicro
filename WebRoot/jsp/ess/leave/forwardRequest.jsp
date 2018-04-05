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
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />



<title>Home Page</title>

</head>
<body >

				<div align="center">
					<logic:present name="leaveForm" property="message">
						<font color="red">
							<bean:write name="leaveForm" property="message" />
						</font>
					</logic:present>
				</div>
				<html:form action="leave" enctype="multipart/form-data">

	<logic:notEmpty name="submitDetails">
	<html:hidden property="id"/>
					
						<table class="bordered content" width="80%">
							<tr>
								<th colspan="6" style="text-align: center;"><big>Leave Record Display</big></th> 
  							</tr>
						
  							<td >Type <font color="red" size="4">*</font></td>
  							
							<td >
								<bean:write name="leaveForm" property="leaveType"/>
							</td>
							<td >Applied&nbsp;Date<font color="red" size="4">*</font></td>
  							
							<td >
								<bean:write name="leaveForm" property="submitDate"/>
							</td>
						</tr>
						<tr>
							<td >From&nbsp;Date<font color="red" size="4">*</font></td>
							
							<td >
								<bean:write name="leaveForm" property="startDate"/>
							</td>
							<td >Duration <font color="red" size="4">*</font></td>
							
							<td >
							<bean:write name="leaveForm" property="startDurationType"/>
							</td>
						</tr>
						<tr>
							<td >To&nbsp;Date<font color="red" size="4">*</font></td>
							
							<td >
								<bean:write name="leaveForm" property="endDate"/>
							</td>
							<td >Duration<font color="red" size="4">*</font></td>
							
							<td >
								<bean:write name="leaveForm" property="endDurationType"/>
							</td>
						</tr>
						
						<tr>
							<td >No of Days <font color="red" size="4">*</font></td>
							
							
							<td align="left"  width="30%">
							<div id="noOfDaysDiv">
								<bean:write name="leaveForm" property="noOfDays"/>
							</div>
							</td>
							
						
							<td > Reason   Type<font color="red" size="4">*</font></td>
							
							<td >
								<html:select name="leaveForm" property="reasonType" styleClass="content" disabled="true">
									<html:option value="">--Select--</html:option>
							<html:options name="leaveForm" property="leaveReason" labelProperty="leaveDetReason"/>
								</html:select>
							</td>
						</tr>	
					<tr>
							<th colspan="6"> Detailed Reason<font color="red" size="3">*</font> : </th>
						</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<bean:define id="content" name="leaveForm"
									property="reason" />
							<%out.println(content.toString());%>
						
							</td>
						</tr>

				<logic:notEmpty name="documentDetails">
						<tr >
							<th colspan="6">
								Uploaded Documents: 
							</th>
						</tr>
						
						<logic:notEmpty name="documentDetails">
						    <tr>
							
							<td align="left" class="lft style1" colspan="4">
									<bean:define id="file" name="leaveForm"
										property="documentName" />
								    <%
										String s = file.toString();
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) 
										{
									%>
									<a href="/EMicro Files/ESS/Leave/<%=v[i]%>" target="_blank"><%=v[i]%></a>
									<%
									}
									%>	
									</td>
									</tr>	
				      </logic:notEmpty>
				      
				   
							
							
				</logic:notEmpty>
						
						
					<tr>
					<td colspan="4" align="center"><html:button property="method" value="Close" styleClass="rounded" style="width: 100px" onclick="history.back()"/></td>
					</tr>
					    
		                </table>

</br>
<logic:notEmpty name="leave">
    <table class="bordered content"><tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="abc" name="parallelapprovers">
	<tr>
	<td>${abc.approver }&nbsp;</td>
	<td>${abc.appDesig }&nbsp;</td>
	<td>${abc.status }&nbsp;</td>
	<td>${abc.approvedDate }&nbsp;</td>
	<td>${abc.comments }&nbsp;</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
			
					
					
					
					</logic:notEmpty>
            </html:form>
</body>
</html>