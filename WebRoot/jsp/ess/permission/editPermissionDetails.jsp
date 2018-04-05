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

<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<title>Home Page</title>

<script language="javascript">


</script>

</head>
  
  <body>
<html:form action="permission">

			<div align="center">
				<logic:present name="permissionForm" property="message2">
					<font color="red" size="3"><b><bean:write name="permissionForm" property="message2" /></b></font>
				</logic:present>
				<logic:present name="permissionForm" property="message">
					<font color="Green" size="3"><b><bean:write name="permissionForm" property="message" /></b></font>
				</logic:present>
			</div>
			
	<table class="bordered content" width="80%">
		 <tr><th  colspan="6" align="center">
					 Permission Form </th>
  						</tr>
						<tr>
						<td width="10%">Permission Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%" >
											<bean:write name="permissionForm" property="date"/>
				
								
							</td>
							<td>Type</td>
							<td align="left" width="34%" >
											<bean:write name="permissionForm" property="type"/>
				
								
							</td>
							</tr>
							<tr>
			<td>				
<p>  Time: <font color="red" size="3">*</font></td><td >	<bean:write name="permissionForm" property="startTime"/>
<logic:equal value="Early" name="permissionForm" property="type"> to <bean:write name="permissionForm" property="endTime"/></logic:equal>
</td>		<td>				
<p>  Swipe Type: <font color="red" size="3">*</font></td><td >	<bean:write name="permissionForm" property="swipetype"/>
</td>
<%-- <td><p>  To Time: <font color="red" size="3">*</font></td><td><bean:write name="permissionForm" property="endTime"/>
		</td> --%><tr>
		<td width="15%">Status</td>
					<td align="left" width="34%"><bean:write name="permissionForm" property="approverStatus"/>
								
							</td>
							<td width="15%">Request Date</td>
						
							<td align="left" width="34%"><bean:write name="permissionForm" property="reqdate"/>
								
							</td>			
									</tr>
			 <tr><th  colspan="6" align="center">
					 Enter Your Reason Here<font color="red" size="2">*</font>  </th>
  						
				</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="109" rows="9" readonly="true"></html:textarea>
							</td>
						</tr>
						
							<tr><th colspan="5">Comments </th></tr>
							<tr><td colspan="5"><bean:write name="permissionForm" property="comments"/>  </td>
		        	</tr>
						<tr>
							<td colspan="6" align="center">
							 <logic:notEmpty name="approveButton">
						<html:button property="method"  styleClass="rounded" value="Submit" onclick="applyPermission('Applied');" style="align:right;width:100px;"/> &nbsp;
							</logic:notEmpty>
							<logic:notEmpty name="rejectButton">
						<html:reset styleClass="rounded"    value="Reset" style="align:right;width:100px;"/>&nbsp;
					</logic:notEmpty>
						<html:button property="method"  styleClass="rounded" value="Close" onclick="history.back(-1)" style="align:right;width:100px;"/>
						</td>
								</td>
				</tr></table>
				<br/>
		
					
							 <logic:notEmpty name="appList">
	
	
	
    <table class="bordered"><tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="abc" name="appList">
	<tr>
	<td>${abc.approver }</td>
	<td>${abc.appDesig }</td>
	<td>${abc.status }</td>
	<td>${abc.approvedDate }</td>
	<td>${abc.comments }</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
	
	
	
	
	
	</table>








</html:form>
  </body>
</html>
