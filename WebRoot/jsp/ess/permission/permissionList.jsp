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
var year=document.forms[0].year.value;
var url="permission.do?method=nextRecord&year="+year;
			document.forms[0].action=url;
			document.forms[0].submit();
}
function previousRecord()
{
var year=document.forms[0].year.value;
var url="permission.do?method=previousRecord&year="+year;
			document.forms[0].action=url;
			document.forms[0].submit();
}
function firstRecord()
{
var year=document.forms[0].year.value;
var url="permission.do?method=displayPermissions&year="+year;
			document.forms[0].action=url;
			document.forms[0].submit();
}

function lastRecord()
{
var year=document.forms[0].year.value;
var url="permission.do?method=lastCustomerRecord&year="+year;
			document.forms[0].action=url;
			document.forms[0].submit();
}

function cancelperm(reqno)
{
var url="permission.do?method=selfcancelRecord&reqno="+reqno;
			document.forms[0].action=url;
			document.forms[0].submit();

}
function dynamicpermissionbal(){
var year=document.forms[0].year.value;
var url="permission.do?method=displayPermissions&year="+year;
			document.forms[0].action=url;
			document.forms[0].submit();
}

function printit(req_no)
{
alert(req_no);
var x=window.showModalDialog("permission.do?method=printPermission&requstNo="+req_no, "dialogwidth=100;dialogheight=400; center:yes;toolbar=no");
}
</script>

  </head>
  
  <body>
  <html:form action="leave" enctype="multipart/form-data">
    
	<tr><td width="50%" height="300" valign="top" style="border: 1px solid #4297d7;">
					<div>

 <logic:notEmpty name="noRecords">
 	<div align="left" class="bordered">
 <table class="sortable">
			
			<tr>
			<th style="text-align:left;"><b>Req No.</b></th>
				<th style="text-align:left;"><b>Req Date.</b></th>
							<th style="text-align:left;"><b>Permission Date</b></th>	
							<th class="specalt" align="center"><b>Time</b></th>
										
						<!-- 	<th style="text-align:left;"><b>End Time</b></th> -->
							<th style="text-align:left;"><b>Type</b></th>
							<th style="text-align:left;"><b>Status</b></th>
								<th>Edit</th>
												<th>Cancel</th>
												<td width="10%" colspan="2">Year <font color="red" size="4">*</font></td>
  							<td width="60%" colspan="2" align="left" colspan="" style="width: 100px; ">
								<html:select name="permissionForm" property="year" styleClass="content" onchange="dynamicpermissionbal()">
								<html:options name="permissionForm"  property="yearList"/>
								</html:select>
					</td>
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
		
	
		<table align="center">
	 	<logic:notEmpty name="displayRecordNo">
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
	</logic:notEmpty>
	</td>
	 </tr>
	 </table>						
						
						<logic:notEmpty name="permissionList">
				
	<div align="left" class="bordered">
			<table class="sortable">
			
						<tr>
						<th colspan="4" align="center"><center>My Permission Request List</center></th>
						<td width="10%" colspan="3">Year <font color="red" size="4">*</font></td>
  							<td width="60%" colspan="2" align="left" colspan="" style="width: 100px; ">
								<html:select name="permissionForm" property="year" styleClass="content" onchange="dynamicpermissionbal()">
								<html:options name="permissionForm"  property="yearList"/>
								</html:select>
					</td>
						</tr>
						
						<tr> 
							<th style="text-align:left;width: 40px;"><b>Req No.</b></th>
										<th style="text-align:left;width: 40px;""><b>Applied On</b></th>
							<th class="specalt" align="center" style="width: 30px;"><b>Applied For</b></th>
							<th class="specalt" align="center" style="width: 30px;""><b>Time</b></th>
													<th class="specalt" align="center" style="width: 30px;""><b> Type</b></th>
							<th style="text-align:left;width: 30px;""><b>Status</b></th>
								<th style="text-align:left;width: 30px;""><b>Attendance Status</b></th>
						<!-- 	<th style="text-align:left;width: 30px;""><b>Print</b></th> -->
							<th style="text-align:left;width: 20px;"><b>View</b></th>
							<th style="text-align:left;width: 20px;"><b>Print</b></th>
							<th style="text-align:left;width: 10px;">Cancel</th>
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
				<bean:write name="mytable1" property="type"/>
				</td>
				<td>
				<bean:write name="mytable1" property="approverStatus"/>
				</td>
				<td>
				<logic:equal value="Approved" name="mytable1" property="approverStatus">
				<bean:write name="mytable1" property="attendanceStatus"/>
				</logic:equal>
				<logic:notEqual value="Approved" name="mytable1" property="approverStatus">&nbsp;</logic:notEqual>
				</td>
				<%-- <td>
				<logic:equal value="Approved" name="mytable1" property="approverStatus">
				<a href="permission.do?method=getReport&RequestNo=${mytable1.requestNumber }">Print</a>
				
				</logic:equal>
				</td> --%>
				<td><a href="permission.do?method=editPermission&requstNo=${mytable1.requestNumber }"><img src="images/view.gif" height="28" width="28""/></a>	
				<td><a href="#" onclick="printit('${mytable1.requestNumber }')"><img src="images/icons8-print-50.png" height="28" width="28""/></a>	
				</td>
										<td>
						<logic:equal value="Pending" name="mytable1" property="approverStatus">
						<html:button property="method" styleClass="rounded" style="width: 50px"  value="cancel" onclick="cancelperm(${mytable1.requestNumber})"></html:button>
						</logic:equal>
						
					</td>
									</tr>
								</logic:iterate>
						</table>
						</logic:notEmpty>
						</html:form>
  </body>
</html>
