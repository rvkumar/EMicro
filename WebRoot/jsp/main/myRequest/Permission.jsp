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

<style>
 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}
input:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
select:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
</style>

</head>
  
  <body>
<html:form action="/permission.do">
        <logic:iterate id="permissionForm" name="perMission">

			<div align="center">
				<logic:present name="permissionForm" property="message2">
					<font color="red" size="3"><b><bean:write name="permissionForm" property="message2" /></b></font>
				</logic:present>
				<logic:present name="permissionForm" property="message">
					<font color="Green" size="3"><b><bean:write name="permissionForm" property="message" /></b></font>
				</logic:present>
			</div>
			
	<table class="bordered content" width="80%">
		 <tr><th  colspan="4" align="center">
					 Permission Form </th>
  						</tr>
						<tr>
						<td width="10%">Permission Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%" colspan="3">
											<bean:write name="permissionForm" property="date"/>
				
								
							</td>
							</tr>
			<td>				
<p> From Time: <font color="red" size="3">*</font></td><td>	<bean:write name="permissionForm" property="startTime"/>
</td>
<td><p>  To Time: <font color="red" size="3">*</font></td><td><bean:write name="permissionForm" property="endTime"/>
		</td>
							
			
						<tr>
		<td width="15%">Status</td>
					<td align="left" width="34%"><bean:write name="permissionForm" property="approverStatus"/>
								
							</td>
							<td width="15%">Date</td>
						
							<td align="left" width="34%"><bean:write name="permissionForm" property="approveDate"/>
								
							</td>			
									</tr>
															
			 <tr><th  colspan="4" align="center">
					 Enter Your Reason Here<font color="red" size="2">*</font>  </th>
  						<tr>
				</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
              <bean:write name="permissionForm" property="reason"/>						
	</td>
						</tr>
			 
						
						<tr><th colspan="5">Comments </th></tr>
							<tr><td colspan="5"><bean:write property="comments" name="permissionForm"/> </td>
		        	</tr>
						<tr>
							<td colspan="4" align="center">
						
						<html:button property="method"  styleClass="rounded" value="Close" onclick="history.back(-1)" style="align:right;width:100px;"/>
						</td>
								</td>
				</tr></table>
				<br/>
		</logic:iterate>
					
							<logic:notEmpty name="ApproverList">
		 <div align="left" class="bordered ">
			<table width="100%"   class="sortable">
			<tr>
				<th style="text-align:left;"><b>Type</b></th>
				<th style="text-align:left;"><b>Emp Name</b></th>	
				<th style="text-align:left;"><b>Designation</b></th>
				<th style="text-align:left;"><b>Status</b></th>
				<th style="text-align:left;"><b>Date</b></th>
					<th style="text-align:left;"><b>Comments</b></th>
			</tr>
			<logic:iterate id="abc" name="ApproverList">
			<tr>
			
			<td>${abc.appType}</td>
			<td>${abc.approver}</td>
			<td>${abc.appDesig}</td>
			<td>&nbsp;${abc.status }</td>
			<td>&nbsp;${abc.approvedDate }</td>
			<td>&nbsp;${abc.comments }</td>
			</tr>
			</logic:iterate>
			</table>
		</div>
		</logic:notEmpty>
	
	
	
	
	
	</table>








</html:form>
  </body>
</html>
