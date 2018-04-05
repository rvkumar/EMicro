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

<title>Permission Receipt</title>
<style type="">

.printClass td, .printClass th {
  
    
    text-align: left; 
    width: 1000px;
  
    font-family:  'Arial';
    font-size: 13px;
      
}
</style>
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
	<a href="javascript:window.print()"><img src="jsp/admin/print.png" title="Print this Page"/></a>	<br/>	<br/>
	<table class="" style="width: 500px;"  border="1">
		 <tr>
<td ><center>
<img  src="images/logo.png" width='50' height='60'/></center>
</td>

<th width="400px;"><font color="blue"><big><strong>
 MICRO LABS LIMITED</strong></big><br/><center><bean:write name="permissionForm" property="locationId"/>-<bean:write name="permissionForm" property="locname"/></center></font></th>

</tr>
		 </table>
		 <table class="printClass" style="width: 500px;"  border="1">
		 <tr><th  colspan="2" align="center">
					 <BIG>PERMISSION</BIG></th>
  						</tr>
						<tr>
						<td ><b>Permission Date :</b><bean:write name="permissionForm" property="date"/> </td> 
							
							<%-- <td align="left" >
											<bean:write name="permissionForm" property="date"/>
				
								
							</td> --%>
							<td><b>Type : </b><bean:write name="permissionForm" property="type"/></td>
							<%-- <td align="left"  >
											<bean:write name="permissionForm" property="type"/>
				
								
							</td> --%>
							</tr>
							<!-- <tr>
							<th colspan="2">Employee Details</th></tr>
							<tr> -->
							<td colspan="2" class="zebra">
							<b>Name of the Employee :</b>	<bean:write name="permissionForm" property="employeeName"/>
							</td></tr>
							<tr>
							<td>
							<b>Employee Code :</b>	<bean:write name="permissionForm" property="employeeNo"/>
							</td>
							<td><b>Department :</b><bean:write name="permissionForm" property="department"/> </td>
							</tr>
							
							<tr>
			<td>				
 <b> Time:</b>:<bean:write name="permissionForm" property="startTime"/>
<logic:equal value="Early" name="permissionForm" property="type"> <b>to</b> <bean:write name="permissionForm" property="endTime"/></logic:equal>
</td>		<td>				
<b>Swipe Type:  </b>:	<bean:write name="permissionForm" property="swipetype"/>
</td></tr>
<%-- <td><p>  To Time: <font color="red" size="3">*</font></td><td><bean:write name="permissionForm" property="endTime"/>
		</td> --%><tr>
		<td ><b>Req No: </b><bean:write name="permissionForm" property="requestNumber"/>
								
							</td>
							<td ><b>Request Date: </b><bean:write name="permissionForm" property="reqdate"/>
								
							</td>			
									</tr></table>
									
									<table class="printClass" style="width: 500px;" border="1">
			 <tr><th   align="left">
					 Reason: </th>
  						
				</tr>
						<tr>	
						
							<td class="lft style1">
							<html:textarea property="reason" cols="55" rows="3" readonly="true"></html:textarea>
							</td>
						</tr>
						
							<%-- <tr><th >Comments </th></tr>
							<tr><td><bean:write name="permissionForm" property="comments"/>  </td>
		        	</tr> --%>
						<%-- <tr>
							<td align="center">
							 <logic:notEmpty name="approveButton">
						<html:button property="method"  styleClass="rounded" value="Submit" onclick="applyPermission('Applied');" style="align:right;width:100px;"/> &nbsp;
							</logic:notEmpty>
							<logic:notEmpty name="rejectButton">
						<html:reset styleClass="rounded"    value="Reset" style="align:right;width:100px;"/>&nbsp;
					</logic:notEmpty>
						<html:button property="method"  styleClass="rounded" value="Close" onclick="history.back(-1)" style="align:right;width:100px;"/>
						</td>
								</td>
				</tr>< --%></table>
				
		
					
							 <logic:notEmpty name="appList">
	
	
	
    <table class="printClass" style="width: 500px;" border="1"><tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
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
	<table class="printClass" style="width: 500px;" border="1">
	<tr><td align="right">
	Printed By: <bean:write name="permissionForm" property="employeeName"/>
<br/>Printed on: <%= new java.util.Date() %>
</td></tr></table>
	
	
	
	
	








</html:form>
  </body>
</html>
