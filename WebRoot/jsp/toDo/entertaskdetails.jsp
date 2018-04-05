
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
	$('#taskdate2').datepick({dateFormat: 'd/m/yyyy'});
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
function saveToDoTask(){	
   var subject = document.forms[0].subject.value;
	var description= document.forms[0].description.value;
	var fromdate= document.forms[0].from_date.value;
	var fromtime= document.forms[0].from_time.value;
	var todate= document.forms[0].to_date.value;
	var totime= document.forms[0].to_time.value;
	if(subject=="")
	{
	alert("Please enter Subject ")
    document.forms[0].subject.focus();
	return false;
	}
	
		if(fromdate=="")
	{
	alert("Please enter From Date ")
	document.forms[0].from_date.focus();
	return false;
	}
	
	if(todate=="")
	{
	alert("Please enter To Date ")
    document.forms[0].to_date.focus();
	return false;
	}
	
			if(fromdate!=todate)
	    {
var a=fromdate.split("/");
var b=todate.split("/");
var dt1  = parseInt(a[0]); 
var mon1 = parseInt(a[1]); 
var yr1  = parseInt(a[2]); 
var dt2  = parseInt(b[0]); 
var mon2 = parseInt(b[1]); 
var yr2  = parseInt(b[2]); 
var date1 = new Date(yr1, mon1, dt1); 
var date2 = new Date(yr2, mon2, dt2); 
if(date2 < date1) 
{ 
    alert("Please Select Valid Date Range");
   document.forms[0].to_date.value="";
     document.forms[0].to_date.focus();
     return false;
}
}
	     	
             if(fromdate==todate)
	    { 
	          
 var startTime=fromtime;
 var endTime=totime;
 var startPeriod=startTime.slice(-2);
 var endPeriod=endTime.slice(-2);
 
 if(startPeriod=='PM' && endPeriod=='AM')
 {
 
 alert("Please enter valid time cycle");
 
 return false;
 
 }  
    
 if(startPeriod=='AM' && endPeriod=='AM' || startPeriod=='PM' && endPeriod=='PM')
 {
 
    var startHR=startTime.substring(0,2);
 var endHR=endTime.substring(0,2);
   var startMin=startTime.substring(3,5);
 var endMin=endTime.substring(3,5);
 

 
 startHR=parseInt(startHR); 
 endHR=parseInt(endHR);
  startMin=parseInt(startMin); 
 endMin=parseInt(endMin);

   if ( startPeriod=='PM' && endPeriod=='PM' )
 {
 
 if(startHR!="12")
 {
  startHR=startHR+12;
 }
 if(endHR!="12")
 {
  endHR=endHR+12;
 }
 }
 
 

 

 if(startHR>endHR)
 {
  alert("To time should be greater than From time");

 return false;
 
 }
  if(startHR==endHR && endMin<startMin )
 {
  alert("To time Minutes should be greater than From time Minutes");

 return false;
 }
 }
 }   	
	   	 
			   
		
	
	
	
	var url="todoTask.do?method=saveToDoTask2&subject="+subject+"&description="+description+"&fromdate="+fromdate+"&fromtime="+fromtime+"&todate="+todate+"&totime="+totime;
	
			document.forms[0].action=url;
			document.forms[0].submit();
}
	</script>
	<script language = "Javascript">

var dtCh= "/";
var minYear=1900;
var maxYear=2100;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strDay=dtStr.substring(0,pos1)
	var strMonth=dtStr.substring(pos1+1,pos2)
	var strYear=dtStr.substring(pos2+1)
	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	if (pos1==-1 || pos2==-1){
		alert("The date format should be : dd/mm/yyyy")
		return false;
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Please enter a valid month")
		return false;
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Please enter a valid day")
		return false;
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false;
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Please enter a valid date")
		return false;
	}
return true
}

function ValidateForm(){
	var dt=document.forms[0].to_date.value;
	alert(dt);
	if (isDate(dt)==false){
		dt.focus();
		return false;
	}
    return true;
 }
 
 function closeTask()
 {
 
 window.close();
 }
 


</script>
<script>
    window.onunload = refreshParent;
    function refreshParent() {
    var x=window.parent.property;
    
    
        window.close();
        
      
         window.opener.location.reload(); 
    }
</script>
	</head>
	<body >
<html:form action="/todoTask.do">
<div align="left" style="padding-right: 70mm" >
						<logic:present name="todoForm" property="statusMessage">
						<font color="green" size="2">
						  <b>  <center><bean:write name="todoForm" property="statusMessage" /></center></b>
						</font>
					</logic:present>
					</div>
		
			&nbsp;&nbsp;&nbsp;<table class="bordered" style="width: 30%;height: 40%;" align="left">
			
			
			<th colspan="5"><center>ADD Task</center></th>
			<tr>
			<td>Subject<font color="red" >*</font></td><td colspan="3"><html:text property="subject" name="todoForm" style="width: 60%;"/></td></tr>
			<tr>
			<td>Description</td><td colspan="3" ><html:textarea property="description" name="todoForm"  rows="3" cols="45" /></td></tr>
			<tr>
			<td>From Date<font color="red" >*</font></td><td><html:text property="from_date" name="todoForm"  readonly="true" /></td>
			
			<td>Time</td><td><html:text property="from_time" name="todoForm"  styleId="timeFrom"/></td></tr>
			<tr>
			<td>To Date<font color="red" >*</font></td><td><html:text property="to_date" name="todoForm"  styleId="taskdate2" readonly="true"/></td>
			
			<td>Time</td><td><html:text property="to_time"  name="todoForm"  styleId="timeTo"/></td></tr>
			<html:hidden property="sno" name="todoForm"/>
		
			</td></tr>
			
			<tr>
			<td colspan="4"><CENTER><html:button property="method"  value="Save" onclick="saveToDoTask()" styleClass="rounded" style="width: 100px"/>
		<html:button property="method"  value="Close" onclick="closeTask()" styleClass="rounded" style="width: 100px"/></CENTER></td>
			</tr>
			
	
		
			</table>
			
			</html:form>
			</body>
			</html>