
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

</script>


<title>Home Page</title>

<script language="javascript">


</script>
</head>

<body >
 <html:form action="/barcode.do">

					<div>

 
 
 <table  class="sortable bordered">

<tr>
<th colspan="6">
Report Details
</th>
</tr>
 <tr>
	<td>Filter Type&nbsp;<font color="red" >*</font></td><td>
	<html:select name="barcodeForm" property="type" styleClass="content" >
									<html:option value="">--Select--</html:option>
									<html:option value="CD">Created Date</html:option>
									<html:option value="RD">Received Date</html:option>
								</html:select>
								&nbsp;&nbsp;
	</td>
	<td>From Date&nbsp;<font color="red" >*</font></td><td>
	<html:text property="datefrom" styleId="popupDatepicker" styleClass="text_field" readonly="true" />&nbsp;&nbsp;
	</td>
	
	<td>To Date&nbsp;<font color="red" >*</font></td>
	<td>
	<html:text property="dateto" styleId="popupDatepicker2" styleClass="text_field"  readonly="true"/>&nbsp;&nbsp;
	</td>
	</tr>
	<tr>
	<td>Location&nbsp;<font color="red" >*</font></td><td>
	<html:select  property="location">
		<html:option value="">--Select--</html:option>
		<html:options  property="locationIdList" labelProperty="locationLabelList"/>
	</html:select>
	</td>
	
	<td>Puchase Group&nbsp;<font color="red" >*</font></td><td>
	<html:select  property="pur">
		<html:option value="">--Select--</html:option>
		<html:options  property="purchaselList" labelProperty="purchaselList"/>
	</html:select>
	</td>
	
	<td>
	<sub>Employee </sub>
	<html:text property="employeeno"  onkeyup="searchEmployee('employeeno')" styleId="employeeno" style="width: 84px; ">
	<bean:write property="employeeno" name="barcodeForm" /></html:text>
	<div id="sU" style="display:none;">
		<div id="sUTD" style="width:80px;">
		<iframe src="jsp/it/HelpDesk/searchemployee.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="60%"></iframe>
		</div>
	</div>
	
	</td>
	
	<td>
	<html:button property="method" value="Search" onclick="search()" styleClass="rounded"/>
	
	<html:button property="method" value="Export" onclick="exportdata()" styleClass="rounded"/>
	</td>
	
	</tr>
	
</table>	

<br/>
<br/>
<br/>
 	<logic:notEmpty name="noRecords">
 <table class="sortable bordered">
			<tr>
				<th colspan="11"><center>Postal Received Requests</center></th>
				</tr>
			<tr>
			<th style="text-align:left;"><b>Location </b></th>
			<th style="text-align:left;"><b>Grn No</b></th>
			<th style="text-align:left;"><b>Grn Date</b></th>
			<th style="text-align:left;"><b>Material Type</b></th>
			<th style="text-align:left;"><b>PG Group</b></th>
			<th style="text-align:left;"><b>Vendor Name</b></th>
			<th style="text-align:left;"><b>City</b></th>
		
			<th style="text-align:left;"><b>Enter By</b></th>	
				<th style="text-align:left;"><b>Enter Date</b></th>						
					</tr>
						</table>
						</div>
	<div align="center">
	
		<logic:present name="barcodeForm" property="message">
			<font color="red">
				<bean:write name="barcodeForm" property="message" />
			</font>
		</logic:present>
		<br />
		</logic:notEmpty>
			
		

 
<logic:notEmpty name="barcodelist">
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
	<bean:write property="startRecord1"  name="barcodeForm"/>-
	</td>
	<td>
	<bean:write property="endRecord1"  name="barcodeForm"/>
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
	
	
	<html:hidden property="totalRecords1" name="barcodeForm"/>
	<html:hidden property="startRecord1" name="barcodeForm"/>
	<html:hidden property="endRecord1" name="barcodeForm"/>
	 </tr>
	 </table>
	 
	 
	 
	 </logic:notEmpty>			
	<div   >			
				<logic:notEmpty name="barcodelist">
			<div align="left" class="bordered">
			<table class="sortable">
			
				<tr>
				<th colspan="11"><center>GRN Details</center></th>
				</tr>
			<tr>
		<th style="text-align:left;"><b>Location </b></th>
			<th style="text-align:left;"><b>Grn No</b></th>
			<th style="text-align:left;"><b>Grn Date</b></th>
			<th style="text-align:left;"><b>Material Type</b></th>
			<th style="text-align:left;"><b>PG Group</b></th>
			<th style="text-align:left;"><b>Vendor Name</b></th>
			<th style="text-align:left;"><b>City</b></th>
			<th style="text-align:left;"><b>Enter By</b></th>
			<th style="text-align:left;"><b>Enter Date</b></th>							
					</tr>
				<logic:iterate id="mytable1" name="barcodelist">
									<tr >
										<td>
				<bean:write name="mytable1" property="wERKS"/>
				
				</td>
				<td>
				<bean:write name="mytable1" property="mBLNR"/>
				</td>
				<td>
				<bean:write name="mytable1" property="bLDAT"/>
				</td>
				<td>
				<bean:write name="mytable1" property="mTART"/>
				</td>
				<td>
				<bean:write name="mytable1" property="eKGRP"/>
				</td>
				<td>
				<bean:write name="mytable1" property="name1"/>
				</td>
				<td>
				<bean:write name="mytable1" property="oORT01"/>
				</td>
		
				<td>
				<bean:write name="mytable1" property="empName"/>
				</td>
				
				<td>
				<bean:write name="mytable1" property="accdate"/>
				</td>
				
				
				
				
									</tr>
				</logic:iterate>
				</table></div>
		</logic:notEmpty>
		</div>
		
		<table border="0">
		<tr><td>&nbsp;</td></tr>
		</table>
		</html:form>

	</body>
</html>
					
			