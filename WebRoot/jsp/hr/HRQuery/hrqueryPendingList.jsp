
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
$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy',onSelect: showDate});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function showDate(date) {
	var stDate=document.forms[0].startDate.value;
var cudate=document.forms[0].submitDate.value;

if(stDate!="")
{

if(cudate!=stDate)
{
document.forms[0].startTime.value="09:00AM";
}

}


var applyafter=document.forms[0].applyAfterDate.value;


var sdate=stDate.split("/");
sdate=sdate[2]+"-"+sdate[1]+"-"+sdate[0];


if(sdate<applyafter){
	alert("Start date should be greater than or equal to "+applyafter);
	document.forms[0].startDate.value="";
	return false;
	}

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

<script>

function firstRecord()
{

var url="barcode.do?method=firstpostalreport";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastRecord()
{

var url="barcode.do?method=lastpostalreport";
			document.forms[0].action=url;
			document.forms[0].submit();

}		

function nextRecord()
{

var url="barcode.do?method=nextpostalreport";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function previousRecord()
{

var url="barcode.do?method=previouspostalreport";
			document.forms[0].action=url;
			document.forms[0].submit();

}


function search()
{

var url="barcode.do?method=postalreport";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function exportdata()
{

var url="barcode.do?method=exportpostalreport";
			document.forms[0].action=url;
			document.forms[0].submit();

}


function searchEmployee(fieldName){

var reqFieldName=fieldName

	var toadd = document.getElementById(reqFieldName).value;
	if(toadd.indexOf(",") >= -1){
		toadd = toadd.substring((toadd.lastIndexOf(",")+1),toadd.length);
	}
	document.getElementById(reqFieldName).focus();
	if(toadd == ""){
		document.getElementById(reqFieldName).focus();
		document.getElementById("sU").style.display ="none";
		return false;
	}
	var xmlhttp;
    if (window.XMLHttpRequest){
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else{
        // code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        if (xmlhttp.readyState==4 && xmlhttp.status==200){
        if(reqFieldName=="employeeno"){
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        	}
       
        	       			
        }
    }
     xmlhttp.open("POST","itHelpdesk.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
    xmlhttp.send();
}


function selectUser(input,reqFieldName){
document.getElementById(reqFieldName).value=input;
	disableSearch(reqFieldName);
}

function disableSearch(reqFieldName){
  if(reqFieldName=="employeeno"){
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		}
	}
	}


	function newreq()
	{
	
	document.forms[0].action="paysliprequest.do?method=newquery";
	document.forms[0].submit();
	}
	
	function statusMessage(message){

alert(message);
}

function showSelectedFilter(){
if(document.forms[0].selectedFilter.value!=""){


	var url="paysliprequest.do?method=pendingRecords&sCount=0&eCount=0&searchTxt=";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
}

function getDetails(url){
var url=url;

		document.forms[0].action=url;
		document.forms[0].submit();
}


function nextRecord(){
var url="paysliprequest.do?method=nextRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function previousRecord(){
var url="paysliprequest.do?method=previousRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function lastRecord(){
var url="paysliprequest.do?method=lastRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function firstRecord(){
var url="paysliprequest.do?method=pendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();
}

function displayhelpdeskrecord()
	{
		var req=document.forms[0].chooseKeyword.value;
		if(req==""||req=="Req No:")
			{
			alert("Please Enter Request No.");
			 document.forms[0].chooseKeyword.focus();
			  return false;
			}
		
		var url="paysliprequest.do?method=HRqueryRequestToApprove&reqId="+req;
		document.forms[0].action=url;
			document.forms[0].submit();
	}
	
	    function isNumber(evt) {
	        if(document.getElementById("reqno").value=="Req No:")
document.getElementById("reqno").value="";
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
    }
    
    
    function exportdata(){
    
    
    var filter=document.forms[0].selectedFilter.value;
    	var start=document.forms[0].datefrom.value;
    	var end=document.forms[0].dateto.value;
		if(start=="")
			{
			alert("Please Select From date");
			 document.forms[0].datefrom.focus();
			  return false;
			}
			if(end=="")
			{
			alert("Please Select To date");
			 document.forms[0].dateto.focus();
			  return false;
			}
			if(filter=="")
			{
			alert("Please Select Filter by");
			 document.forms[0].selectedFilter.focus();
			  return false;
			}
var url="paysliprequest.do?method=ExportHRquerylist";
		document.forms[0].action=url;
		document.forms[0].submit();
}

</script>
</script>


<title>Home Page</title>

<script language="javascript">


</script>
</head>

<body >
<html:form action="paysliprequest" onsubmit="return processrequest();return false;" enctype="multipart/form-data" method="post">
<div align="center">
				<logic:notEmpty name="paysliprequestForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="paysliprequestForm" property="message" />');
					</script>
				</logic:notEmpty>
				
			</div>
<table class="bordered"><tr><th><b>Filter by</b> <font color="red">*</font></th>
						<td>
						<html:select property="selectedFilter" styleClass="content" styleId="filterId" onchange="showSelectedFilter()">
						
							<html:option value="">--Select--</html:option>
							<html:option value="New">New</html:option>
							<html:option value="Open">Open</html:option>	
							<html:option value="Closed">Closed</html:option>		
						    <html:option value="ReOpen">ReOpen</html:option>	
							<html:option value="All">All</html:option>
							</html:select>
						</td>
						<td colspan="3">
						<html:text property="chooseKeyword" title="Enter Request Number" styleClass="rounded" onkeypress="return isNumber(event)"  value="Req No:" styleId="reqno"/>&nbsp;&nbsp;&nbsp;
<html:button property="method" value="Go" styleClass="rounded" onclick="displayhelpdeskrecord()" style="width:100px;"/></td>
						</tr>
						
						<tr><th>From Date&nbsp;</th><td>
	<html:text property="datefrom" styleId="popupDatepicker"  readonly="true" />&nbsp;&nbsp;
	</td>
	
	<th>To Date&nbsp;</th>
	<td>
	<html:text property="dateto" styleId="popupDatepicker2"  readonly="true"/>&nbsp;&nbsp;

	<html:button property="method" value="Export to Excel" styleClass="rounded" onclick="exportdata()" style="width:100px;"/></td></tr>
						</table>
<br/>


<logic:notEmpty name="displayRecordNo">

	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="paysliprequestForm"/>-
	
	<bean:write property="endRecord"  name="paysliprequestForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
	</td>

	</table>
	</logic:notEmpty>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	
	<br/>
<logic:notEmpty name="Usrreq">
<table class="sortable bordered" >
<tr>
<th>Req&nbsp;No</th><th>Request Date</th><th style="width:100px;">Requested&nbsp;By</th><th>Subject</th><th>Category</th><th>Status</th><th>View</th>
</tr>
<logic:iterate id="c" name="Usrreq">
<tr>

<td>${c.requestNumber}</td>
<td><bean:write name="c" property="submitDate"/></td>
<td><bean:write name="c" property="employeeName"/></td>
<td><bean:write name="c" property="reason"/></td>
<td><bean:write name="c" property="leaveType"/></td>
<td><bean:write name="c" property="status"/></td>
<td><a onclick="javascript:getDetails('paysliprequest.do?method=HRqueryRequestToApprove&reqId=${c.requestNumber}')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<br/>
<logic:notEmpty name="no Usrreq">
<table class="sortable bordered" >
<tr>
<th>Req&nbsp;No</th><th>Request Date</th><th style="width:100px;">Requested&nbsp;By</th><th>Subject</th><th>Category</th><th>Status</th><th>View</th>
</tr>
<tr><td colspan="8"> <center>No records to display</center></td></tr>


</logic:notEmpty>
		</html:form>

	</body>
</html>
					
			