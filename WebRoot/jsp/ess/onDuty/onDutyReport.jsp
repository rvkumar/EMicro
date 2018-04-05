<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>

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
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
   <script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<script type="text/javascript">
		function popupCalender(param)
	{
		var toD = new Date();
		if(param == "endDate"){
			var sD = document.forms[0].startDate.value;
			var sDate = sD.split("/");
			toD = new Date(parseInt(sDate[2]),parseInt(sDate[1]),parseInt(sDate[0]));
		}
		var cal = new Zapatec.Calendar.setup({
		inputField : param, // id of the input field
		singleClick : true, // require two clicks to submit
		ifFormat : "%d/%m/%Y", // format of the input field
		showsTime : false, // show time as well as date
		button : "button2", // trigger button
		});
	}
	
		function popupCalender(param)
	{
		var toD = new Date();
		if(param == "endDate"){
			var sD = document.forms[0].startDate.value;
			var sDate = sD.split("/");
			toD = new Date(parseInt(sDate[2]),parseInt(sDate[1]),parseInt(sDate[0]));
		}
		var cal = new Zapatec.Calendar.setup({
		inputField : param, // id of the input field
		singleClick : true, // require two clicks to submit
		ifFormat : "%d/%m/%Y", // format of the input field
		showsTime : false, // show time as well as date
		button : "button2", // trigger button
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
function editMyRequest(requestNo,status)
{

var url="onDuty.do?method=editMyRequest&requestNo="+requestNo+"&status="+status;
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
function showform(param)
{
var startDate=document.forms[0].startDate.value;
var endDate=document.forms[0].endDate.value;

 if(document.forms[0].startDate.value=="")
	    {
	      alert("Start Date should not be left blank");
	      document.forms[0].startDate.focus();
	      return false;
	    }
	       if(document.forms[0].endDate.value=="")
	    {
	      alert("End Date should not be left blank");
	      document.forms[0].endDate.focus();
	      return false;
	    }
	    
	       if(startDate!=""&&endDate!=""){
   
     var str1 = document.forms[0].startDate.value;
var str2 = document.forms[0].endDate.value;
var dt1  = parseInt(str1.substring(0,2),10); 
var mon1 = parseInt(str1.substring(3,5),10); 
var yr1  = parseInt(str1.substring(6,10),10); 
var dt2  = parseInt(str2.substring(0,2),10); 
var mon2 = parseInt(str2.substring(3,5),10); 
var yr2  = parseInt(str2.substring(6,10),10); 
var date1 = new Date(yr1, mon1, dt1); 
var date2 = new Date(yr2, mon2, dt2); 

if(date2 < date1) 
{ 
    alert("Please Select Valid Date Range");
    document.forms[0].endDate.value="";
     document.forms[0].endDate.focus();
     return false;
}


   }

var url="onDuty.do?method=displayOthersondutyReport&param="+param;
document.forms[0].action=url;
document.forms[0].submit();
}
</script>
</head>

<body >



		<html:form action="onDuty" enctype="multipart/form-data">
		<table  align="center">
<tr>
<td ><center>
<img  src="images/logo.png" width='80' height='90'></center>
</td>
<th><font color="blue"><big><strong>
 MICRO LABS LIMITED</strong></big><br >EMPLOYEES ONDUTY APPLICATION CUM RECORD</font></th>
</td>
</tr>
</table>
</br>

<table class="bordered " width="130%">
<tr>
<logic:notEmpty name="Approver">
<th><b>Employee selection</b></th>
<td>
<html:select property="empno" styleClass="content" styleId="filterId" >
<html:option value="">--Self--</html:option>
<html:option value="All">--All--</html:option>
<html:options name="onDutyForm"  property="emplIdList" labelProperty="empLabelList"/>
</html:select>
</td></logic:notEmpty>
<th >From Date <font color="red" size="4">*</font></th>
					
							<td align="left" >
								<html:text property="startDate" styleId="startDate" onfocus="popupCalender('startDate')" styleClass="text_field"
										readonly="true"  />
							</td>
							
							<th>To Date <font color="red" size="4">*</font></th>
					
							<td align="left" >
								<html:text property="endDate" styleId="endDate" onfocus="popupCalender('endDate')" styleClass="text_field"
										readonly="true" />
							</td>
							<td><html:button property="method" value="Generate" onclick="showform('Display')" styleClass="rounded"/>&nbsp;<html:button property="method" value="Excel" onclick="showform('Excel')" styleClass="rounded"/>
</td>
</tr>
</table>

</br>

<logic:notEqual value="All" name="onDutyForm" property="employeeNumber">
<table  class="bordered">


<tr ><td ><b>Employee No.</b></td><td><bean:write name="onDutyForm" property="employeeNumber"></bean:write></td>
<td ><b>Name</b></td><td><bean:write name="onDutyForm" property="employeeName"></bean:write></td>

</tr>

<tr>
<td ><b>Designation</b></td><td><bean:write name="onDutyForm" property="designation"></bean:write></td>
<td ><b>Department</b></td><td><bean:write name="onDutyForm" property="department"></bean:write></td>

</tr>

<tr>
<td   ><b>Date Of Joining</b></td><td><bean:write name="onDutyForm" property="doj"></bean:write></td>
<td  ><b>Year</b></td><td><bean:write name="onDutyForm" property="year"></bean:write></td>
</tr>

</table>

</logic:notEqual>
<br>
	<logic:notEmpty name="ondutyList">
			<div align="left" class="bordered">
			<table class="sortable">
			
				<tr>
				<th colspan="12"><center>OnDuty Requests</center></th>
				</tr>
				<tr><th style="text-align:left;"><b>Sl No.</b></th>
							<th style="text-align:left;"><b>Req No.</b></th>
								<th style="text-align:left;"><b>Requester</b></th>
							<th style="text-align:left;"><b>Type</b></th>
										<th style="text-align:left;"><b>Req Date</b></th>	
							<th style="text-align:left;"><b>Plant</b></th>	
							<th class="specalt" align="center"><b>From Date</b></th>
							<th class="specalt" align="center"><b>From Time</b></th>			
							<th style="text-align:left;"><b>To Date</b></th>
							<th class="specalt" align="center"><b>To Time</b></th>
								<th class="specalt" align="center"><b>Reason</b></th>
							
						</tr>
						<%int h=0; %>
				<logic:iterate id="mytable1" name="ondutyList">
				<%h++; %>
									<tr >
									<td><%=h %></td>
										<td>
				<bean:write name="mytable1" property="requestNumber"/>
				
				</td>
			
										<td>
				<bean:write name="mytable1" property="employeeName"/>
				
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
				<bean:write name="mytable1" property="reason"/>
				</td>
				
				
									</tr>
				</logic:iterate>
				</table></div>
		</logic:notEmpty>
		
		 <logic:notEmpty name="noRecords">
 	
 <table class="sortable bordered">
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
						
					</tr>
					<tr><td colspan="10"><center>No Records to display </center></td></tr>
						</table>
					
		</logic:notEmpty>
		</html:form>
		</body>
		</html>