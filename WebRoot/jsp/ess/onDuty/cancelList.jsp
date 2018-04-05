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

var url="onDuty.do?method=displayOnDutyRequests";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastRecord()
{

var url="onDuty.do?method=lastMyRequestRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}		
function editMyRequest(requestNo)
{

var url="onDuty.do?method=editCancelRequest&requestNo="+requestNo;
			document.forms[0].action=url;
			document.forms[0].submit();



}

function nextRecord()
{

var url="onDuty.do?method=nextMyRequestRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function previousRecord()
{

var url="onDuty.do?method=previousMyRequestRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function cancelLeave(reqno)
{


var url="onDuty.do?method=cancelRecord&reqno="+reqno;
			document.forms[0].action=url;
			document.forms[0].submit();

}

</script>
</head>

<body >



	<tr><td width="50%" height="300" valign="top" style="border: 1px solid #4297d7;">
					<div>

 <logic:notEmpty name="noRecords">
 	
 <table class="sortable bordered">
			<tr>
				<th colspan="10"><center>My Approved OnDuty Requests</center></th>
				</tr>
				
			<tr>
			<th style="text-align:left;"><b>Req No.</b></th>
							<th style="text-align:left;"><b>Type.</b></th>	
							<th style="text-align:left;"><b>Plant.</b></th>	
							<th class="specalt" align="center"><b>From Date</b></th>
							<th class="specalt" align="center"><b>From Time</b></th>			
							<th style="text-align:left;"><b>To Date</b></th>
							<th class="specalt" align="center"><b>To Time</b></th>
							<th style="text-align:left;"><b>Status</b></th>
							
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
			
		
	<html:form action="leave" enctype="multipart/form-data">
		
	
		
				
				<logic:notEmpty name="ondutyList">
			<div align="left" class="bordered">
			<table class="sortable">
			
				<tr>
				<th colspan="11"><center>My Approved OnDuty Requests</center></th>
				</tr>
				<tr><th colspan="10">Note : Request of Cancellation of On Duty can be applied for the previous 2 months only</th></tr>
				<tr>
							<th style="text-align:left;"><b>Req No.</b></th>
							<th style="text-align:left;"><b>Type.</b></th>	
							<th style="text-align:left;"><b>Plant.</b></th>	
							<th class="specalt" align="center"><b>From Date</b></th>
							<th class="specalt" align="center"><b>From Time</b></th>			
							<th style="text-align:left;"><b>To Date</b></th>
							<th class="specalt" align="center"><b>To Time</b></th>
							<th style="text-align:left;"><b>Status</b></th>
							
							<th>View</th>
							
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
				<img  src="images/view.gif" height="28" width="28" onclick="editMyRequest(${mytable1.requestNumber })"/>
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