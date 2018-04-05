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

function applyLeave()
{
	document.forms[0].action="leave.do?method=applyLeave";
	document.forms[0].submit();
}


function parseDate(str) {
    var mdy = str.split('/');
    return new Date(mdy[2], mdy[1]-1, mdy[0]);
}


function daydiff(first, second) {

//daydiff

var totaldays=(second-first)/(1000*60*60*24);

if(totaldays<0)
{
alert("start date should be less than end date");
    return "";
}
else{

    return ((second-first)/(1000*60*60*24)+1)
    }
}


function uploadDocument()
{


  if(document.forms[0].documentFile.value=="")
	    {
	      alert("Please Select File.");
	      document.forms[0].documentFile.focus();
	      return false;
	    }
	document.forms[0].action="leave.do?method=uploadDocuments";
	document.forms[0].submit();
}


function deleteDocumentsSelected()
{
	document.forms[0].action="leave.do?method=deleteDocuments";
	document.forms[0].submit();
}


function displayTabs(param)
{
	document.forms[0].action="leave.do?method=displayTabs&param="+param;
	document.forms[0].submit();
}
function applyLeave(param)
{

if(param=='Applied')
{
       if(document.forms[0].leaveType.value=="")
	    {
	      alert("Leave Type should not be left blank");
	      document.forms[0].leaveType.focus();
	      return false;
	    }
	   else if(document.forms[0].startDurationType.value=="")
	    {
	      alert("Holiday Type should not be left blank");
	      document.forms[0].startDurationType.focus();
	      return false;
	    }
	     else if(document.forms[0].startDate.value=="")
	    {
	      alert("Start Date should not be left blank");
	      document.forms[0].startDate.focus();
	      return false;
	    }
	     else if(document.forms[0].endDate.value=="")
	    {
	      alert("End Date should not be left blank");
	      document.forms[0].endDate.focus();
	      return false;
	    }
	     else if(document.forms[0].noOfDays.value=="")
	    {
	      alert("Please Select No Of Days.It should not be left blank");
	      document.forms[0].noOfDays.focus();
	      return false;
	    }
	    
	    
 document.forms[0].action="leave.do?method=submit&param="+param;
document.forms[0].submit();
}
else if(param=='Draft')
{
if(document.forms[0].startDate.value=="")
	    {
	      alert("Start Date should not be left blank");
	      document.forms[0].startDate.focus();
	      return false;
	    }
	    else  if(document.forms[0].endDate.value=="")
	    {
	      alert("End Date should not be left blank");
	      document.forms[0].endDate.focus();
	      return false;
	    }
else if((document.forms[0].leaveType.value!="")||(document.forms[0].startDurationType.value!="")||(document.forms[0].startDate.value!="")||(document.forms[0].endDate.value!=""))
{

	document.forms[0].action="leave.do?method=submit&param="+param;
	document.forms[0].submit();
}

}
}
function closeLeave()
{
	document.forms[0].action="leave.do?method=display&sId=ApplyLeave&id=ESS";
	document.forms[0].submit();
}
function displayRequests()
{
	document.forms[0].action="leave.do?method=displayRequests";
	document.forms[0].submit();
}
function submitRequest(param)
{
document.forms[0].action="leave.do?method=submitRequests&param="+param;
document.forms[0].submit();
}

function cancelRequest()
{
document.forms[0].action="leave.do?method=cancelRequest";
document.forms[0].submit();
}
function deleteDraft()
{
document.forms[0].action="leave.do?method=deleteDraft";
document.forms[0].submit();
}
function calculateEndDate()
{

document.forms[0].noOfDays.value=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));

var endDate=document.forms[0].noOfDays.value;
if(endDate=="")
{
document.forms[0].endDate.value="";
}
}
		
function nextRecord()
{
var url="leave.do?method=nextMyRequestRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function previousRecord()
{

var url="leave.do?method=previousMyRequestRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function firstRecord()
{

var url="leave.do?method=firstMyRequestRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastRecord()
{

var url="leave.do?method=lastMyRequestRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function cancelLeave(reqno)
{


var url="leave.do?method=cancelRecord&reqno="+reqno;
			document.forms[0].action=url;
			document.forms[0].submit();

}
		
</script>
</head>

<body >

				<html:form action="leave" enctype="multipart/form-data">
	<table class="bordered" width="90%">
		      	<tr>
					<th colspan="4" style="text-align: center;"><big>My Approved Leave Requests</big></th>
				</tr>
	
</table>
		
				
		<logic:notEmpty name="leavecancel">
		<div align="left" class="bordered">
			<table class="sortable">
			<tr><th colspan="10">Note : Request of Cancellation of leaves can be applied for the previous 2 months only</th></tr>
			
			<tr>
							<th style="text-align:left;"><b>Req&nbsp;No.</b></th>
							<th style="text-align:left;"><b>Leave&nbsp;Type</b></th>
							<th style="text-align:left;"><b>Requested On</b></th>	
							<th style="text-align:left;"><b>From&nbsp;Date</b></th>
							<th style="text-align:left;"><b>Duration</b></th>
							<th style="text-align:left;"><b>To&nbsp;Date</b></th>
						   <th style="text-align:left;"><b>Duration</b></th>
							<th style="text-align:left;"><b>No&nbsp;Of&nbsp;Days</b></th>
							<th style="text-align:left;"><b>Status</b></th>
				           <th style="text-align:left;"><b>View</b></th>
				            <!--
							<th style="text-align:left;"><b>Select</b></th>
						--></tr>
						<%
							int count = 1;
										
						%>
				
			
				<logic:iterate id="mytable1" name="leavecancel">
				
				
									<tr class="tableOddTR">
										<td>
                     <bean:write name="mytable1" property="id"/>
					</td>
					<td>
						<bean:write name="mytable1" property="leaveType"/>
					</td>
					<td>
						<bean:write name="mytable1" property="submitDate"/>
					</td>
					
					<td>
						<bean:write name="mytable1" property="startDate"/>
					</td>
					<td>
						<bean:write name="mytable1" property="startDurationType"/>
					</td>
					<td>
						<bean:write name="mytable1" property="endDate"/>
					</td>
					<td>
						<bean:write name="mytable1" property="endDurationType"/>
					</td>
					<td>
						<bean:write name="mytable1" property="noOfDays"/>
					</td>
					<td>
						<bean:write name="mytable1" property="status"/>
					</td>
					
					<td>
					<logic:notEqual value="Self Cancelled" name="mytable1" property="status">
					<a  href="leave.do?method=selectCancelRequest&requstNo=<bean:write name="mytable1" property="id"/>" ><img src="images/view.gif" width="28" height="28"/></a>
					</logic:notEqual>
					</td>
					
				
				</logic:iterate><!--
				<table align="center">
				<tr>
					<td>
				 		<div align="center">
           					<html:button property="method"  value="Approve" onclick="submitRequest('approve')"/>
         	  				<html:button property="method"  value="Reject" onclick="submitRequest('reject')"/>
           				</div>
           		</td></tr>
				</table>
			</table>
		--></logic:notEmpty>
		
		<logic:notEmpty name="norecords">
		
		<div align="left" class="bordered">
			<table class="sortable">
			
			<tr>
							<th style="text-align:left;"><b>Req&nbsp;No.</b></th>
							<th style="text-align:left;"><b>Leave&nbsp;Type</b></th>
							<th style="text-align:left;"><b>Requested On</b></th>	
							<th style="text-align:left;"><b>From&nbsp;Date</b></th>
							<th style="text-align:left;"><b>Duration</b></th>
							<th style="text-align:left;"><b>To&nbsp;Date</b></th>
						   <th style="text-align:left;"><b>Duration</b></th>
							<th style="text-align:left;"><b>No&nbsp;Of&nbsp;Days</b></th>
							<th style="text-align:left;"><b>Status</b></th>
				           <th style="text-align:left;"><b>View</b></th>
				            </tr>
				            <tr><td colspan="10"><font color="red"><center>No records to display</center></font></td></tr>
		
		</logic:notEmpty>
		</table>
		</div>
		
	</html:form>
</body>
</html>