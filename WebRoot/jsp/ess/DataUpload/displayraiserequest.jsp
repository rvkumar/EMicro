<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
 <style>
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
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

  <style type="text/css">
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>



<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>
<script type="text/javascript">$(function () {


	$('#timeFrom').timeEntry();
});


$(function () {


	$('#timeTo').timeEntry();
});

$('.timeRange').timeEntry({beforeShow: customRange}); 
 
function customRange(input) { 
    return {minTime: (text.styleId == 'timeTo' ? 
        $('#timeFrom').timeEntry('getTime') : null),  
        maxTime: (text.styleId  == 'timeFrom' ? 
        $('#timeTo').timeEntry('getTime') : null)}; 
}

</script>


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
</script>

<script type="text/javascript">




function searchRequest()
{


	document.forms[0].action="vc.do?method=displayraiserequest";
	document.forms[0].submit();

}

function statusMessage(message){
alert(message);
}


function newRequest()
{

	document.forms[0].action="vc.do?method=raiserequestlist";
	document.forms[0].submit();

}



function process(sid,type,group_type)
{
var x=window.showModalDialog("vc.do?method=viewPendingrequest&reqno="+sid+"&type="+type+"&group_type="+group_type,null, "dialogWidth=1000px;dialogHeight=600px; center:yes");
}


function processSubmitted(sid,type,group_type)
{
var x=window.showModalDialog("vc.do?method=viewSubmittedrequest&reqno="+sid+"&type="+type+"&group_type="+group_type,null, "dialogWidth=1000px;dialogHeight=600px; center:yes");
}

function processCompleted(sid,type,group_type)
{
var x=window.showModalDialog("vc.do?method=viewCompletedrequest&reqno="+sid+"&type="+type+"&group_type="+group_type,null, "dialogWidth=1000px;dialogHeight=600px; center:yes");
}





</script>
</head>
<body >

	<html:form action="vc" enctype="multipart/form-data">
	
	<logic:notEmpty name="vcForm" property="message">
			<script language="javascript">
				statusMessage('<bean:write name="vcForm" property="message" />');
			</script>
		</logic:notEmpty>
		
	<table class="bordered">
	<tr>
	<th colspan="8"><center>Document Search</center></th>
	</tr>
	<tr>
	<th>From Date<font color="red">*</font></th><td> <html:text property="fromDate" styleId="popupDatepicker"/></td>
	<th>To Date<font color="red">*</font></th><td> 
	<html:text property="toDate" styleId="popupDatepicker2"/></td>
	</tr>
	<tr>
	<th>
	Req Status
	</th>
	<td>
	<html:select property="reqstatus">
	<html:option value="all">All</html:option>
	<html:option value="Pending">Pending</html:option>
	<html:option value="Submitted">Submitted</html:option>
	</html:select>
	</td>
	<td colspan="8"><center> 
	<html:button property="method" value="Search" onclick="searchRequest()" styleClass="rounded"/> &nbsp;&nbsp;
	<html:button property="method" value="New" onclick="newRequest()" styleClass="rounded"/> &nbsp;&nbsp;
	<input type="reset" value="Close" class="rounded" onclick="clearSearch()"/></center>
	 </td>
	</tr>
	</table>
	<div>&nbsp;</div>
	 &nbsp;&nbsp;&nbsp;
	 <!-- <input type="button" value="VC Rooms List" class="rounded" onclick="roomsList()"/> -->
	<div>&nbsp;</div>
	
	
	
	
				<logic:notEmpty name="emplist">
			<div align="left" class="bordered" style="height:300px;overflow:auto">
			<table class="sortable">
			
				<tr>
				<th colspan="11"><center>Requested Details</center></th>
				</tr>
			<tr>
			<th style="text-align:left;"><b>Req No</b></th>
			<th style="text-align:left;"><b>Assigned BY</b></th>
			<th style="text-align:left;"><b>Description</b></th>
			
			<th style="text-align:left;"><b>File Name</b></th>
			<th style="text-align:left;"><b>Start Date</b></th>
			<th style="text-align:left;"><b>Submission End Date</b></th>
			
			<th style="text-align:left;"><b>Pending</b></th>
			<th style="text-align:left;"><b>Submitted</b></th>
			<th style="text-align:left;"><b>Completed</b></th>
			<th style="text-align:left;"><b>Status</b></th>
			<th style="text-align:left;"><b>Download</b></th>
										
					</tr>
				<logic:iterate id="mytable1" name="emplist">
				<tr>
				<td>
				<bean:write name="mytable1" property="reqNo"/>
					</td>
				<td>
				<bean:write name="mytable1" property="empName"/>
					</td>
					<td>
				<bean:write name="mytable1" property="desc"/>
					</td>
										<td>
				<bean:write name="mytable1" property="filename"/>
				
				</td>
				<td>
				<bean:write name="mytable1" property="fromDate"/>
				</td>
				<td>
				<bean:write name="mytable1" property="toDate"/>
				</td>
				<td>
				<logic:notEqual value="0" name="mytable1" property="pendingApprover">
				<a href="javascript:process('${mytable1.reqNo}','Pending','${mytable1.group_type}')"  ><u>
				<bean:write name="mytable1" property="pendingApprover"/>
				</u>
				</a>
				</logic:notEqual>
				<logic:equal value="0" name="mytable1" property="pendingApprover">
				<bean:write name="mytable1" property="pendingApprover"/>
				</logic:equal>
				</td>
				<td>
				<logic:notEqual value="0" name="mytable1" property="reqstatus">
				<a href="javascript:processSubmitted('${mytable1.reqNo}','Pending','${mytable1.group_type}')"  ><u>
				<bean:write name="mytable1" property="reqstatus"/>
				</u></a>
				</logic:notEqual>
				
				<logic:equal value="0" name="mytable1" property="reqstatus">
				<bean:write name="mytable1" property="reqstatus"/>
				</logic:equal>
				
				</td>
				<td>
				<logic:equal value="0" name="mytable1" property="completed">
				<bean:write name="mytable1" property="completed"/>
				</logic:equal>
				
				<logic:notEqual value="0" name="mytable1" property="completed">
				<a href="javascript:processCompleted(${mytable1.reqNo},'Approved')"  ><u>
				<bean:write name="mytable1" property="completed"/>
				</u></a>
				</logic:notEqual>
				</td>
				
				<td>
				<bean:write name="mytable1" property="status"/>
				</td>
				<td>
				<a href="vc.do?method=getfile&path=${mytable1.path}&reqno=${mytable1.reqNo}&group_type=${mytable1.group_type}"  ><u>Download</u></a>
				</td>
									</tr>
				</logic:iterate>
				</table></div>
		</logic:notEmpty>
		
		
		
		
	
	
	
	</html:form>
	
</body>
	
</html>	