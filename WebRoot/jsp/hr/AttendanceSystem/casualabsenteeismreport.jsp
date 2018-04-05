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
<link rel="stylesheet" type="text/css" href="css/microlabs1.css" />

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
<!--
/////////////////////////////////////////////////
-->
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script src="js/sumo1.js"></script>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script> -->
    <script src="js/sumo/jquery.sumoselect.js"></script>
    <link href="js/sumo/sumoselect.css" rel="stylesheet" />

    <script type="text/javascript">
        $(document).ready(function () {
            window.asd = $('.SlectBox').SumoSelect({ csvDispCount: 3 });
            window.test = $('.testsel').SumoSelect({okCancelInMulti:true });
            window.testSelAll = $('.testSelAll').SumoSelect({okCancelInMulti:true, selectAll:true });
            window.testSelAll2 = $('.testSelAll2').SumoSelect({selectAll:true });

        });
        
         $(document).ready(function () {
           $('.testselect1').SumoSelect();

        });
    </script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">

$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'mm/dd/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'mm/dd/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>


<title>Home Page</title>

<script language="javascript">

function process()
{
var k =document.forms[0].toDate.value;

var date= new Date(k);
var newdate=new Date(date);
newdate.setDate(newdate.getDate()-3);

/* function convertdate(inputformat)
{
function pad(s)
{
return(s<10) ? '0' + s : s;
}   
return [pad(newdate.getDate()),pad(newdate.getMonth()+1), newdate.getFullYear()].join('/');
}
var someformat = convertDate(newdate); */
 var dd = newdate.getDate();
    var mm = newdate.getMonth() + 1;
    return mm<10 ? '0'+ mm: '' + mm;
    var y = newdate.getFullYear();

    var someformat = dd + '/' + mm + '/' + y;


var fromdate = document.forms[0].fromdate.value=someformat;
 var todate =  document.forms[0].toDate.value; 
var loc=document.forms[0].locationId.value;

var dep=document.forms[0].deptArray.value;
var subdep=document.forms[0].subdeptArray.value;
var repgrp=document.forms[0].repgrpArray.value;
/* var view=document.forms[0].summbrkup.value; */
var view="Absent";

if(someformat=="")
{
alert("Please Select date");
return false;
}
if(dep=="")
{
alert("Please Select Department");
return false;
}
if(subdep!="")
{

if(repgrp=="")
{
alert("Please Select Reporting Group");
return false;
}
}
 /* if(view=="All")
{
document.forms[0].action="hrApprove.do?method=dailyPerformancereportsearch";
document.forms[0].submit();
 var a = new Array;
  a[0] = 1;
  a[1] = 4;
var x=window.showModalDialog("hrApprove.do?method=dailyPerformancereportexe&loc="+loc+"&date="+k+"&todate="+todate+"&summbrkup="+view,a, "dialogwidth=800;dialogheight=600; center:yes;toolbar=no");
}
if(view=="Arrival")
{

document.forms[0].action="hrApprove.do?method=dailyArrivalreportsearch";
document.forms[0].submit();
 var a = new Array;
  a[0] = 1;
  a[1] = 4;
var x=window.showModalDialog("hrApprove.do?method=dailyArrivalreportexe&loc="+loc+"&date="+k,a, "dialogwidth=800;dialogheight=600; center:yes;toolbar=no");
} */
if(view=="Absent")
{

document.forms[0].action="hrApprove.do?method=casualabsenteeismreportsearch";
document.forms[0].submit();
 var a = new Array;
  a[0] = 1;
  a[1] = 4;
var x=window.showModalDialog("hrApprove.do?method=casualabsenteeismexe&loc="+loc+"&date="+someformat+"&todate="+todate+"&summbrkup="+view,a, "dialogwidth=800;dialogheight=600; center:yes;toolbar=no");
}
}

function processexcel()
{
var k = document.forms[0].toDate.value;
var dep=document.forms[0].deptArray.value;
var subdep=document.forms[0].subdeptArray.value;
var repgrp=document.forms[0].repgrpArray.value;
/* var view=document.forms[0].summbrkup.value="Absent"; */
 var view="Absent";
alert(view);

if(k=="")
{
alert("Please Select date ");
return false;
}

if(dep=="")
{
alert("Please Select Department");
return false;
}
/* if(subdep=="")
{
alert("Please Select SubDepartment");
return false;
}
if(repgrp=="")
{
alert("Please Select Reporting Group");
return false;
} */

/* if(view=="All")
{
document.forms[0].action="hrApprove.do?method=exportdailyPerformancereport";
document.forms[0].submit();
}
if(view=="Arrival")
{

document.forms[0].action="hrApprove.do?method=exportdailyArrivalreport";
document.forms[0].submit();

}
 */if(view=="Absent")
{

document.forms[0].action="hrApprove.do?method=exportcasualabsenteeismreport";
document.forms[0].submit();
}
}

function clearAllFields(){
document.forms[0].endDurationType.value="";
document.forms[0].totalLeaveDays.value="";
}




function statusMessage(message){
alert(message);
}

function back()
{

document.forms[0].action="hrApprove.do?method=attendanceReport";
document.forms[0].submit();

}
</script>
</head>

<body>

				
	<html:form action="hrApprove" enctype="multipart/form-data">
	
			<div align="center">
				<logic:notEmpty name="hrApprovalForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="hrApprovalForm" property="message" />');
					</script>
				</logic:notEmpty>
				<logic:notEmpty name="hrApprovalForm" property="message2">
					
					<script language="javascript">
					statusMessage('<bean:write name="hrApprovalForm" property="message" />');
					</script>
				</logic:notEmpty>
			</div>


		 <div style="width: 90%" >
		<table class="bordered" >
		
		<tr>
		<th colspan="5">Casual_Absenteeism Report</th>
		</tr>
		
		<%-- <tr>
		<th colspan="5">Type
		</th>
		</tr>
		<tr>
		<td colspan="6">  --%><%-- 	<input type="hidden"   property="summbrkup" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1" >
<html:option value="Absent">Continous Absent</html:option>
</input> --%>
		
		<tr>
		<td>Location</td>
		<td>
		
			<html:select  property="locationId" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
			<html:options name="hrApprovalForm"  property="locationIdList" labelProperty="locationLabelList"/>
		</html:select>
		</td>
		</tr>
		<tr>
		<td>Date<font color="red">*</font></td>
		<td colspan="1">
		<html:text property="toDate" name="hrApprovalForm" styleId="popupDatepicker2" style="width: 98px; "/>	
			<input type="hidden" name="fromDate"  id="popupDatepicker" style="width: 98px;"/></td>
		
		<%-- <td>Location</td>
		<td>
		
			<html:select  property="locationId" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
			<html:options name="hrApprovalForm"  property="locationIdList" labelProperty="locationLabelList"/>
		</html:select>
		</td> --%>
		</tr>
		
	<tr>
<td>Department<font color="red">*</font></td>
<td colspan="">
<html:select  property="deptArray" name="hrApprovalForm" multiple="true" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">

<html:options name="hrApprovalForm"  property="deptList" labelProperty="deptLabelList"/>
</html:select>


</td> 
<td>Sub Department</td>
<td colspan="3">
<html:select  property="subdeptArray" name="hrApprovalForm" multiple="true" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">

<html:options name="hrApprovalForm"  property="subdeptList" labelProperty="subdeptLabelList"/>
</html:select>


</td>
</tr>
		
		
		
		<tr>
	
<td>Reporting Group</td>
<td colspan="3">
<html:select  property="repgrpArray" name="hrApprovalForm" multiple="true" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">

<html:options name="hrApprovalForm"  property="repgrpList" labelProperty="repgrpLabelList"/>
</html:select>


</td>
</tr>
			
		
		
		
</table>
<br/>
<center>

<div>

<html:button property="method" value="Execute" onclick="process()" styleClass="rounded"/>&nbsp;<html:button property="method" value="Export To Excel" onclick="processexcel()" styleClass="rounded"/>
<html:button property="method" value="Close" onclick="back()" styleClass="rounded"/>  &nbsp; 





</div>
&nbsp;&nbsp;




</div>
</html:form>
</body>
</html>
