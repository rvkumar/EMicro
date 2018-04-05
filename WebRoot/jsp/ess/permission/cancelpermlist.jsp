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
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/styles.css" />
  <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript">
function nextRecord()
{
var url="permission.do?method=nextRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}
function previousRecord()
{
var url="permission.do?method=previousRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}
function firstRecord()
{
var url="permission.do?method=displayPermissions";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function lastRecord()
{
var url="permission.do?method=lastCustomerRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}
</script>

  </head>
  
  <body>
    
	<tr><td width="50%" height="300" valign="top" style="border: 1px solid #4297d7;">
					<div>

 <logic:notEmpty name="noRecords">
 	<div align="left" class="bordered">
 <table class="sortable">
				<tr><th colspan="9"><center>My Approved Permission Requests</center></th></tr>
						<tr><th colspan="10">Note : Request of Cancellation of Permission can be applied for the previous 2 months only</th></tr>
			<tr>
			<th style="text-align:left;"><b>Req No.</b></th>
			<th style="text-align:left;width: 40px;""><b>Req Date.</b></th>
							<th style="text-align:left;"><b>Permission Date</b></th>	
							<th class="specalt" align="center"><b>Start Time</b></th>
										
							<th style="text-align:left;"><b>End Time</b></th>
							
							<th style="text-align:left;"><b>Status</b></th>
							<th style="text-align:left;"><b>Type</b></th>
								<th>Edit</th>
						</tr>
						</table>
						</div>
	<div align="center">
	
		<logic:present name="permissionForm" property="message">
			<font color="red">
				<bean:write name="permissionForm" property="message" />
			</font>
		</logic:present>
		<br />
		
		</logic:notEmpty>
	</div>		
		
	<html:form action="leave" enctype="multipart/form-data">
		<table align="center">
<%-- 	 	<logic:notEmpty name="displayRecordNo">
	 <tr>
	  	<td>
	  	<img src="images/First10.jpg" align="absmiddle" onclick="firstRecord()"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">

	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">

	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>

	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="permissionForm"/>-

	<bean:write property="endRecord"  name="permissionForm"/>

	<logic:notEmpty name="nextButton">

	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	
	
	<img src="images/disableRight.jpg" align="absmiddle"/>

	
	</logic:notEmpty>
	
		<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
	
	
	<html:hidden property="totalRecords" name="permissionForm"/>
	<html:hidden property="startRecord" name="permissionForm"/>
	<html:hidden property="endRecord" name="permissionForm"/>
	</logic:notEmpty> --%>
	</td>
	 </tr>
	 </table>						
						
						<logic:notEmpty name="permissionList">
				
	<div align="left" class="bordered">
			<table class="sortable">
			
						
						<tr><th colspan="9"><center>My Approved Permission Requests</center></th></tr>
						<tr><th colspan="10">Note : Request of Cancellation of Permission can be applied for the previous 2 months only</th></tr>
						<tr> 
							<th style="text-align:left;width: 40px;"><b>Req No.</b></th>
							<th style="text-align:left;width: 40px;""><b>Req Date.</b></th>
							<th class="specalt" align="center" style="width: 30px;"><b>Permission Date</b></th>
							<th class="specalt" align="center" style="width: 30px;""><b> Out Time</b></th>
								<th class="specalt" align="center" style="width: 30px;""><b> In Time</b></th>
							<th style="text-align:left;width: 30px;""><b>Status</b></th>
								<th style="text-align:left;width: 30px;""><b>Type</b></th>
							<th style="text-align:left;width: 20px;"><b>View</b></th>
						</tr>
				<logic:iterate name="permissionList" id="mytable1">
			<tr >
				<td >
				<bean:write name="mytable1" property="requestNumber"/>
				</td>
					<td>
				<bean:write name="mytable1" property="reqdate"/>
				</td>
				<td>
				<bean:write name="mytable1" property="date"/>
				</td>
				<td>
				<bean:write name="mytable1" property="startTime"/>
				</td>
				<td>
				<bean:write name="mytable1" property="endTime"/>
				</td>
				<td>
				<bean:write name="mytable1" property="approverStatus"/>
				</td>
					<td>
				<bean:write name="mytable1" property="type"/>
				</td>
				<td><a href="permission.do?method=editCancelRequest&requstNo=${mytable1.requestNumber }"><img src="images/view.gif" height="28" width="28""/></a>	
				</td>
										
									</tr>
								</logic:iterate>
						</table>
						</logic:notEmpty>
						</html:form>
  </body>
</html>
