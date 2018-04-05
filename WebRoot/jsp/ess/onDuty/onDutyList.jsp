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


<!--<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style/content.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
 Theme css 
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />



--><script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<!--
   micro css & js
-->
   <script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />





<script type="text/javascript">
	function popupCalender(param)
	{
	var cal = new Zapatec.Calendar.setup({
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y ", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2" // trigger button
	});
	}
</script>


<title>Home Page</title>

<script language="javascript">


function firstRecord()
{
var year=document.forms[0].year.value;
var url="onDuty.do?method=displayOnDutyRequests&year="+year;
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastRecord()
{
var year=document.forms[0].year.value;
var url="onDuty.do?method=lastMyRequestRecord&year="+year;
			document.forms[0].action=url;
			document.forms[0].submit();

}		
function editMyRequest(requestNo,status)
{

var url="onDuty.do?method=editMyRequest&requestNo="+requestNo+"&status="+status;
			document.forms[0].action=url;
			document.forms[0].submit();



}

function nextRecord()
{
var year=document.forms[0].year.value;
var url="onDuty.do?method=nextMyRequestRecord&year="+year;
			document.forms[0].action=url;
			document.forms[0].submit();

}

function previousRecord()
{
var year=document.forms[0].year.value;
var url="onDuty.do?method=previousMyRequestRecord&year="+year;
			document.forms[0].action=url;
			document.forms[0].submit();

}

function cancelLeave(reqno)
{


var url="onDuty.do?method=cancelRecord&reqno="+reqno;
			document.forms[0].action=url;
			document.forms[0].submit();

}
function dynamiconDutybal()
{
var year=document.forms[0].year.value;
var url="onDuty.do?method=displayOnDutyRequests&year="+year;
			document.forms[0].action=url;
			document.forms[0].submit();
}
</script>
</head>

<body >
<html:form action="leave" enctype="multipart/form-data">


	<tr><td width="50%" height="300" valign="top" style="border: 1px solid #4297d7;">
					<div>

 <logic:notEmpty name="noRecords">
 	
 <table class="bordered">
			<tr>
				<th colspan="10"><center>My OnDuty Requests</center></th>
				</tr>
			<tr>
			<th style="text-align:left;"><b>Req No.</b></th>
							<th style="text-align:left;"><b>Type.</b></th>	
							<th style="text-align:left;"><b>Req Date</b></th>	
							<th style="text-align:left;"><b>Plant.</b></th>	
							<th class="specalt" align="center"><b>From Date</b></th>
							<th class="specalt" align="center"><b>From Time</b></th>			
							<th style="text-align:left;"><b>To Date</b></th>
							<th class="specalt" align="center"><b>To Time</b></th>
							<th style="text-align:left;"><b>Status</b></th>
							<td width="10%" colspan="2">Year <font color="red" size="4">*</font></td>
  							<td width="60%" colspan="2" align="left" colspan="" style="width: 100px; ">
								<html:select name="onDutyForm" property="year" styleClass="content" onchange="dynamiconDutybal()">
								<html:options name="onDutyForm"  property="yearList"/>
								</html:select>
					</td>
					</tr>
						</table>
						</div>
	<div align="center">
	
		<logic:present name="onDutyForm" property="message">
			<font color="red">
				<bean:write name="onDutyForm" property="message" />
			</font>
		</logic:present>
		<br />
		<br />.
		</logic:notEmpty>
			
		
	
		<table align="center" style="width:150px;">
	 	<logic:notEmpty name="displayRecordNo">
	 <tr>
	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()"/>
	
	</td>
	
	<logic:notEmpty name="disablePreviousButton">
	<td>
	
	<img src="images/disableLeft.jpg" />
	</td>
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	<td>
	
	<img src="images/previous1.jpg" onclick="previousRecord()"/>
	</td>
	</logic:notEmpty>
	
	<td>
	<td>
	<bean:write property="startRecord1"  name="onDutyForm"/>-
	</td>
	<td>
	<bean:write property="endRecord1"  name="onDutyForm"/>
	</td>
	<logic:notEmpty name="nextButton">
	<td>
	<img src="images/Next1.jpg" onclick="nextRecord()"/>
	</td>
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	<td>
	
	<img src="images/disableRight.jpg" />
	</td>
	
	</logic:notEmpty>
		<td>
		<img src="images/Last10.jpg" onclick="lastRecord()"/>
	</td>
	</logic:notEmpty>
	<html:hidden property="totalRecords1" name="onDutyForm"/>
	<html:hidden property="startRecord1" name="onDutyForm"/>
	<html:hidden property="endRecord1" name="onDutyForm"/>
	 </tr>
	 </table>			
				
				<logic:notEmpty name="ondutyList">
			<div align="left" class="bordered">
			<table class="bordered">
			
				<tr>
					<th colspan="7" style="text-align: center;"><big>My OnDuty Requests</big></th>
						<td width="10%" colspan="3">Year <font color="red" size="4">*</font></td>
  							<td width="60%" colspan="2" align="left" colspan="" style="width: 100px; ">
								<html:select name="onDutyForm" property="year" styleClass="content" onchange="dynamiconDutybal()">
								<html:options name="onDutyForm"  property="yearList"/>
								</html:select>
					</td>
				<tr>
							<th style="text-align:left;"><b>Req No.</b></th>
							<th style="text-align:left;"><b>Type.</b></th>
										<th style="text-align:left;"><b>Req Date</b></th>	
							<th style="text-align:left;"><b>Plant.</b></th>	
							<th class="specalt" align="center"><b>From Date</b></th>
							<th class="specalt" align="center"><b>From Time</b></th>			
							<th style="text-align:left;"><b>To Date</b></th>
							<th class="specalt" align="center"><b>To Time</b></th>
							<th style="text-align:left;"><b>Status</b></th>
							<th style="text-align:left;"><b>Print</b></th>
							<th>View</th>
							<th>Cancel</th>
						</tr>
				<logic:iterate id="mytable1" name="ondutyList">
									<tr >
										<td>
				<bean:write name="mytable1" property="requestNumber"/>
				
				</td>
				<td>
				<bean:write name="mytable1" property="onDutyType"/>
				</td>
				<td>
				<bean:write name="mytable1" property="submitDate"/>
				</td>
				<td>
				<bean:write name="mytable1" property="locationId"/>
				</td>
				
				<td>
				<bean:write name="mytable1" property="startDate"/>
				</td>
					<td>
				<bean:write name="mytable1" property="startTime"/>
				</td>
				<td>
				<bean:write name="mytable1" property="endDate"/>
				</td>
					<td>
				<bean:write name="mytable1" property="endTime"/>
				</td>
				
				<td>
				<bean:write name="mytable1" property="status"/>
				</td>
				<td>
				<logic:equal value="Approved" property="status" name="mytable1">
				<a href="onDuty.do?method=getReport&RequestNo=${mytable1.requestNumber }">Print</a>
				</logic:equal>
				<logic:notEqual value="Approved" property="status" name="mytable1">
				&nbsp;
				</logic:notEqual>
				</td>
				<td>
				<img  src="images/view.gif" height="28" width="28" onclick="editMyRequest('${mytable1.requestNumber }','${mytable1.status }')"/>
				</td>
				<td>
						<logic:equal value="In Process" name="mytable1" property="status">
						<html:button property="method" styleClass="rounded" style="width: 80px"  value="cancel" onclick="cancelLeave(${mytable1.requestNumber})"></html:button>
						</logic:equal>
						
					</td>
									</tr>
				</logic:iterate>
				</table></div>
		</logic:notEmpty>
		
		<table border="0">
		<tr><td>&nbsp;</td></tr>
		</table>
		</html:form>
		</body>
		</html>