<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
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
<!--
/////////////////////////////////////////////////
-->
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

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
    
  <script type="text/javascript">
  
  
  function applyReqrequest(){
 
  
 
  if(document.forms[0].empNumber.value==""){
  alert("Please Enter Employee Number ");
  document.forms[0].empNumber.focus();
  return false;
  }
  
  	document.forms[0].action="updateLeaveBal.do?method=displayLeavebal";
	document.forms[0].submit();

}

function checkLeaveCal(){


var startDate=document.forms[0].startDate.value;
var startDateDuration=document.forms[0].startDurationType.value;
var endDate=document.forms[0].endDate.value;
var endDurationType=document.forms[0].endDurationType.value;

if(startDate=="")
   {
     alert("Please Select Start Date");
     document.forms[0].startDate.focus();
     document.forms[0].endDurationType.value="";
     return false;
   }
 if(startDateDuration=="")
   {
     alert("Please Select Start Date Duration");
     document.forms[0].startDateDuration.focus();
     document.forms[0].endDurationType.value="";
     return false;
   }
   if(endDate=="")
   {
     alert("Please Select End Date");
     document.forms[0].endDate.focus();
     document.forms[0].endDurationType.value="";
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
   if(endDurationType=="")
   {
     alert("Please Select End Date Duration");
     document.forms[0].endDurationType.focus();
     return false;
   }
   
   if((startDateDuration=="FD"&&endDurationType=="SH"))
   {
   
   alert("Please Select Valid Duration");
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].endDurationType.value="";
      
     return false;
   }
    if((startDateDuration=="FD"&&endDurationType=="SH"))
   {
   
   alert("Please Select Valid Duration");
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].endDurationType.value="";
      
     return false;
   }
   
   if((startDateDuration=="FH"&&endDurationType=="FD"))
   {
   
   alert("Please Select Valid Duration");
   document.forms[0].startDurationType.focus();
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].startDurationType.value="";
      
     return false;
   }
   
   if((startDateDuration=="FH"&&endDurationType=="SH"))
   {
   
   alert("Please Select Valid Duration");
   document.forms[0].startDurationType.focus();
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].endDurationType.value="";
      
     return false;
   }
  
   if(startDate==endDate && startDateDuration!=endDurationType)
   {
   alert("Please Select Valid Duration ");
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
        document.forms[0].endDurationType.value="";
     return false;
   }
   
var xmlhttp;

if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("noOfDaysDiv").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","updateLeaveBal.do?method=calculatedays&StartDate="+startDate+"&StartDur="+startDateDuration+"&EndDate="+endDate+"&EndDur="+endDurationType,true);
xmlhttp.send();
}



function savedata(){



    
 	document.forms[0].action="updateLeaveBal.do?method=saveleave";
	document.forms[0].submit();
}


 $(document).ready(function() {

        $('.abc').keypress(function (event) {
            
            return isNumber(event, this)

        });

    });
function isNumber(evt,element) {
		 var charCode = (evt.which) ? evt.which : event.keyCode
   
        if (
                 (charCode > 31) &&
            (charCode != 46 || $(element).val().indexOf('.') != -1) &&      
            (charCode < 48 || charCode > 57))
            return false;

        return true;
			}

  function updatedata(){


 	document.forms[0].action="updateLeaveBal.do?method=updatedol";
	document.forms[0].submit();
}
  </script>    
</head>

<body>
<html:form action="updateLeaveBal" enctype="multipart/form-data" method="post" onsubmit="applyReqrequest()"> 
<div align="center" id="messageID" style="visibility: visible;">
			<logic:present name="hrLeaveForm" property="message">
				<font color="green" size="3"><b><bean:write name="updateLeaveBalForm" property="message" /></b></font>
				
			</logic:present>
			<logic:present name="hrLeaveForm" property="message2">
				<font color="red" size="3"><b><bean:write name="updateLeaveBalForm" property="message2" /></b></font>
				
			</logic:present>
		</div>
		
		<logic:notEmpty name="invalid">
			<font color="red" size="3"><b><bean:write name="updateLeaveBalForm" property="message" /></b></font>
		</logic:notEmpty>

<table class="bordered" style="width: 50%; ">
<tr>

<th>Employee Number<font color="red" size="3">*</font></th><td><html:text property="empNumber"/>&nbsp;&nbsp;
<html:button property="method" value="Submit" onclick="applyReqrequest()" styleClass="rounded" style="width: 106px; "></html:button></td>
</tr>
</table>
<br/>
<logic:notEmpty name="LeaveBalenceList">
		 <div style="width: 80%" >
		<table class="bordered" width="80%">
		<tr><th>Employee Name</th><td><bean:write name="updateLeaveBalForm" property="empName" /></td><th>Department</th><td><bean:write name="updateLeaveBalForm" property="empdep" /></td><th>Designation</th><td><bean:write name="updateLeaveBalForm" property="empdesg" /></td></tr>
			<tr><th>Date Of Joining</th><td colspan=""><bean:write name="updateLeaveBalForm" property="doj" /></td><th>Date Of Leaving</th><td colspan="3">
			<logic:notEmpty property="dol" name="updateLeaveBalForm"> 
			<bean:write name="updateLeaveBalForm" property="dol" />
			</logic:notEmpty>
			<logic:empty property="dol" name="updateLeaveBalForm">
			<html:text property="dol" name="updateLeaveBalForm" styleId="popupDatepicker" style="width: 98px; "/>
			&nbsp;
			<%-- <html:button property="method" styleClass="rounded" value="Update"  onclick="updatedata()" style="width:100px;"/> --%></td>
			</logic:empty>
			
			
			
			</tr>
		
			<tr>
				<th colspan="6" align="left">Leave Balances (Days)-<bean:write name="updateLeaveBalForm" property="year" /></th>
			</tr>
		<tr>
			<th>Leave Type</th><th>Opening Balance</th><th>Availed</th><th>Closing Balance</th><th colspan="2">Leaves Awaiting for Approval</th>
			</tr>
			<logic:iterate id="bal" name="LeaveBalenceList">
						<tr>
						<th><bean:write name="bal" property="leaveType" /></th>
						<td><html:text property="openingBalence" name="bal" readonly="true" style="background-color:#E8E8E8;" ></html:text></td>
						<td><html:text property="avalableBalence" name="bal" readonly="true" style="background-color:#E8E8E8;"></html:text></td>
						<td><html:text property="closingBalence" name="bal"  styleClass="abc"></html:text></td>
						<td colspan="2"><html:text property="awaitingBalence" name="bal" readonly="true" style="background-color:#E8E8E8;"></html:text></td>
						</tr>	
			</logic:iterate>	
		</table>
		</div>
		
		<br/>
			<div>
		
		 <center> <%-- <html:button property="method" styleClass="rounded" value="Save"  onclick="savedata()" style="width:100px;"/> --%></center>
		</div>
		</logic:notEmpty>
		
	
		
<%-- 		<logic:notEmpty name="LeaveBalenceList">
		<table class="bordered" width="80%">
		<tr><th colspan="7">COMP-OFF</th></tr>
<tr>
							<td width="15%">Start Date <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							<td align="left" width="34%">
								<html:text property="startDate" styleId="startDate" onfocus="popupCalender('startDate')" styleClass="text_field"
										readonly="true"/>
							</td>
							<td width="15%">Duration <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							<td width="34%">
								<html:select name="updateLeaveBalForm" property="startDurationType" styleClass="content" >
									<html:option value="">--Select--</html:option>
									<html:option value="FD">Full Day</html:option>
									<html:option value="FH">First Half</html:option>
									<html:option value="SH">Second Half</html:option>
								</html:select>
							</td>
						</tr>
						<tr>
							<td width="15%">End Date <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							<td align="left" width="34%">
								<html:text property="endDate" styleId="endDate" onfocus="popupCalender('endDate')" styleClass="text_field" 
										readonly="true" />
							</td>
							<td width="15%">Duration <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							<td width="34%">
								<html:select name="updateLeaveBalForm" property="endDurationType" styleClass="content" onchange="checkLeaveCal()">
									<html:option value="">--Select--</html:option>
									<html:option value="FD">Full Day</html:option>
									<html:option value="FH">First Half</html:option>
									<html:option value="SH">Second Half</html:option>
								</html:select>
							</td>
						</tr>
						
						<tr>
							<td width="15%">No of Days <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							
							<td align="left"  width="30%" colspan="4">
							<div id="noOfDaysDiv">
								<html:text property="totalLeaveDays" readonly="true"  styleClass="text_field" style="border:0;"/>
							</div>
							</td></tr>
							<tr><td>Reason<font color="red" size="4">*</font></td>
							<td colspan="6"><html:textarea property="reason" cols="80" rows="5"></html:textarea></td></tr>
							
							<tr><td colspan="7"> <center> <html:button property="method" styleClass="rounded" value="Save"  onclick="savedata()" style="width:100px;"/></center></td></tr>
		</table></logic:notEmpty> --%>
		<logic:notEmpty name="No LeaveBalence">
		<table class="bordered" width="90%">
			<tr>
				<th colspan="5" align="left">Leave Balances (Days)</th>
			</tr>
		<tr>
			<th>Leave Type(2013)</th><th>Opening Balance</th><th>Availed</th><th>Closing Balance</th><th>Leaves Awaiting for Approval</th>
			</tr>
			<tr>
			<td colspan="5"><center><font color="Green"><bean:write name="updateLeaveBalForm" property="message2" /></font></center> </td>
			</tr>
			</table>
		
			</logic:notEmpty>
		

</html:form>
</body>
</html>