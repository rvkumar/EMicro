<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="CC" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
    .green {
        color: green;
   		
    }

    .red    {
        color: red;
        text-decoration: blink;
}
</style>
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
	function showSelectedFilter(){
	
   if(document.forms[0].reqRequstType.value!="" && document.forms[0].selectedFilter.value!="" && document.forms[0].hrFromDate.value!="" && document.forms[0].hrToDate.value!=""){

	var filter = document.getElementById("filterId").value;
	var url="hrreport.do?method=pendingRecords&sCount=0&eCount=0&searchTxt=";
	document.forms[0].action=url;
	document.forms[0].submit();

	
	
	}
}

function nextRecord(){
var url="hrreport.do?method=nextRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function previousRecord(){
var url="hrreport.do?method=previousRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function lastRecord(){
var url="hrreport.do?method=lastRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function firstRecord(){

var url="hrreport.do?method=pendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();
}

function report()
{

var x=document.forms[0].selectedFilter.value;

var url="hrreport.do?method=getHrLeaveReport&type="+x;
		document.forms[0].action=url;
		document.forms[0].submit();
}


function searchdetails()
{

   if(document.forms[0].reqRequstType.value=="")
   {
   alert("Please select Request Type");
 document.forms[0].reqRequstType.focus();
	      return false;
   
   }
    if(document.forms[0].startDate.value=="")
   {
   alert("Please enter Enter FromDate");
 document.forms[0].startDate.focus();
	      return false;
   
   }
    if(document.forms[0].endDate.value=="")
   {
   alert("Please enter Enter ToDate");
 document.forms[0].endDate.focus();
	      return false;
   
   }
    if(document.forms[0].selectedFilter.value=="")
   {
   alert("Please select Filter By");
 document.forms[0].selectedFilter.focus();
	      return false;
   
   }

var url="hrreport.do?method=pendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();

}
</script>
    
<style type="text/css">
    th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>
  </head>
  
  <body>
<html:form action="hrreport" enctype="multipart/form-data">
<div align="center">
				<logic:present name="hrReportForm" property="message">
					<font color="Green" size="3"><b><bean:write name="hrReportForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="hrReportForm" property="message3">
					<font color="red" size="3"><b><bean:write name="hrReportForm" property="message3" /></b></font>
				</logic:present>
			</div>


<table class="bordered" width="70%">
<tr>
	<th colspan="10"><big><center>Reports</center></big></th>
</tr> 
<tr>
<td>Request Type : <font color="red">*</font>
</td><td>	<html:select property="reqRequstType" styleClass="Content"  styleId="requestSelectId" >
	<html:option value="">Select</html:option>

		<html:option value="Leave">Leave</html:option>	
		<html:option value="On Duty">On Duty</html:option>
		<html:option value="Permission">Permission</html:option>
         <html:option value="Comp-Off">Comp-Off</html:option>
         <html:option value="OT">Over-Time</html:option>
	</html:select>
	</td>
	
	
							<td >From Date <font color="red" size="4">*</font></td>
					
							<td align="left" >
								<html:text property="hrFromDate" styleId="startDate" onfocus="popupCalender('startDate')" styleClass="text_field"
										readonly="true" />
							</td>
							
							<td >To Date <font color="red" size="4">*</font></td>
					
							<td align="left" >
								<html:text property="hrToDate" styleId="endDate" onfocus="popupCalender('endDate')" styleClass="text_field"
										readonly="true" />
							</td>
						
							
<td><b>Filter by</b> <font color="red">*</font></td>
						<td>
						<html:select property="selectedFilter" styleClass="content" styleId="filterId" >
						
							<html:option value="">--Select--</html:option>
							<html:option value="Pending">Pending</html:option>
							<html:option value="Approved">Approved</html:option>
						
							<html:option value="All">All</html:option>
							</html:select>
					
						</td>
						
						</tr><tr>

<td>Dept</td>
		 	<td>
				<html:select property="department" name="hrReportForm" styleClass="rounded">
					<html:option value="">--Select--</html:option>
         			<html:options property="departmentList" labelProperty="departmentLabelList" ></html:options>  
      			</html:select>
      		</td>
      		<td>Employee No.</td>
      		<td colspan="8"> 
      		<html:text property="employeeNo"  styleClass="text_field" title="Enter Employee No."/>
		<a href="#"><img src="images/search.png" align="absmiddle"   onclick="searchdetails()"/></a>
										
										
			</td>								
      			
					
</table>
<br/>
<center>
<logic:notEmpty name="displayRecordNo">

	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="hrReportForm"/>-
	
	<bean:write property="endRecord"  name="hrReportForm"/>
	
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
	
</center>
<logic:notEmpty name="ot">	
<a href="hrreport.do?method=OTApprovedListRecords&hrFromDate=${ hrReportForm.hrFromDate}&hrToDate=${hrReportForm.hrToDate}" target="_blank">
 <html:button  property="method" value="Report" styleClass="rounded"  style="width:100px;" /></a>
</br>
<table class="bordered" width="90%">
			<tr>
				<th colspan="7" align="left"><center>Over time </center></th>
			</tr>
		<tr>
<th>Req&nbsp;No</th><th>Requested by</th><th>Request Date</th><th >&nbsp;&nbsp;&nbsp;&nbsp;Worked Date</th><th>Reason</th><th>Status</th><th>View</th>
</tr>

<logic:iterate id="c" name="ot">
<tr>

<td>${c.requestNumber}</td>

<td><bean:write name="c" property="employeeName"/></td>
<td><bean:write name="c" property="submitDate"/></td>
<td ><bean:write name="c" property="startDate"/></td>

<td ><bean:write name="c" property="reason"/></td>
<td><bean:write name="c" property="status"/></td>
<td>
<a  href="leave.do?method=viewOT&requstNo=<bean:write name="c" property="requestNumber"/>" ><img src="images/view.gif" width="28" height="28"/></a>
</td>

</tr>
		</logic:iterate>	
	
		
		

	
	</table></logic:notEmpty>
	
		<logic:notEmpty name="noot">
		
<table class="bordered" width="90%">
			<tr>
				<th colspan="7" align="left"><center> Oevr time </center></th>
			</tr>
		<tr>
<td colspan="8">
<font color="red" size="3"><center>No Records </center></font>
</td>
</tr>
		</table>
		</logic:notEmpty>

<logic:notEmpty name="comp">	
<a href="hrreport.do?method=CompApprovedListRecords&hrFromDate=${ hrReportForm.hrFromDate}&hrToDate=${hrReportForm.hrToDate}" target="_blank">
 <html:button  property="method" value="Report" styleClass="rounded"  style="width:100px;" /></a>
</br>
<table class="bordered" width="90%">
			<tr>
				<th colspan="8" align="left"><center> Comp-Off </center></th>
			</tr>
		<tr>
<th>Req&nbsp;No</th><th>Requested by</th><th>Request Date</th><th >&nbsp;&nbsp;&nbsp;&nbsp;Worked Date</th><th>Reason</th><th>Status</th><th>Balance_Status</th><th>View</th><th>Update</th>
</tr>

<logic:iterate id="c" name="comp">
<tr>

<td>${c.requestNumber}</td>

<td><bean:write name="c" property="employeeName"/></td>
<td><bean:write name="c" property="submitDate"/></td>
<td ><bean:write name="c" property="startDate"/></td>

<td ><bean:write name="c" property="reason"/></td>
<td><bean:write name="c" property="status"/></td>
<CC:choose>
            <CC:when test="${c.approveStatus =='Pending'}">
           
                <td class="red" align="center"><b><bean:write name="c" property="approveStatus"/></b></td> 
      
            
   </CC:when>
      <CC:otherwise>
           
                <td class="green"  align="center"><b><bean:write name="c" property="approveStatus"/></b></td> 
            
            
  </CC:otherwise> 
        </CC:choose>

<td>
<a  href="leave.do?method=viewcompoff&requstNo=<bean:write name="c" property="requestNumber"/>" ><img src="images/view.gif" width="28" height="28"/></a>
</td>
<td>
<a  href="leave.do?method=viewupdatecompoff&requstNo=<bean:write name="c" property="requestNumber"/>" ><img src="images/edit.png" width="28" height="28"/></a>

</td>
</tr>
		</logic:iterate>	
	
		
		

	
	</table></logic:notEmpty>
	


 <logic:notEmpty name="leaveList">	

<a href="hrreport.do?method=getHrLeaveReport&hrFromDate=${ hrReportForm.hrFromDate}&hrToDate=${hrReportForm.hrToDate}" target="_blank">
 <html:button  property="method" value="Report" styleClass="rounded"  style="width:100px;" /></a>
</br>
		<table class="bordered sortable">
			<tr >
					<th>Req&nbsp;No</th><th style="width:50px;">Leave Type</th><th style="width:185px;">Employee Name</th><th>Req Date</th>
					<th style="width:100px;">Start Date</th><th style="width:50px;">Duration</th><th style="width:100px;">End Date</th><th style="width:50px;">Duration</th>
					<th >Days</th><th>Status</th><th>Approver Name</th>
				</tr>
					
<logic:iterate id="leave" name="leaveList">

<tr>


<td><bean:write name="leave" property="requestNumber"/></td>
<td><bean:write name="leave" property="leaveType"/></td>
<td><bean:write name="leave" property="employeeName"/></td>
<td><bean:write name="leave" property="submitDate"/></td>
<td><bean:write name="leave" property="startDate"/></td>
<td><bean:write name="leave" property="startDurationType"/></td>
<td><bean:write name="leave" property="endDate"/></td>
<td><bean:write name="leave" property="endDurationType"/></td>
<td><bean:write name="leave" property="noOfDays"/></td>
<td><bean:write name="leave" property="approveStatus"/></td>
<td><bean:write name="leave" property="approverName"/></td>
</tr>

</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="ondutyList">
<a href="hrreport.do?method=getHrOndutyReport&hrFromDate=${ hrReportForm.hrFromDate}&hrToDate=${hrReportForm.hrToDate}"" target="_blank">
 <html:button  property="method" value="Report" styleClass="rounded"  style="width:100px;" /></a>
<br/>
<table class="sortable bordered">
<tr >
	<th>Req&nbsp;No</th><th style="width:200px;">Requested By</th><th style="width:100px;">OnDuty&nbsp;Type&nbsp;</th><th style="width:100px;">Plant</th>
	<th style="width:100px;">Start Date</th><th style="width:100px;">From&nbsp;Time</th><th style="width:100px;">End Date</th><th style="width:100px;">To Time</th>
	<th style="width:100px;">Req Date</th><th style="width:100px;">Status</th><th>Approver Name</th>
</tr>
<logic:iterate id="onDuty" name="ondutyList">
<tr>


<td><bean:write name="onDuty" property="requestNumber"/></td>
<td><bean:write name="onDuty" property="employeeName"/></td>
<td><bean:write name="onDuty" property="onDutyType"/></td>

<td><bean:write name="onDuty" property="locationId"/></td>
<td><bean:write name="onDuty" property="startDate"/></td>
<td><bean:write name="onDuty" property="startTime"/></td>
<td><bean:write name="onDuty" property="endDate"/></td>
<td><bean:write name="onDuty" property="endTime"/></td>
<td><bean:write name="onDuty" property="submitDate"/></td>
<td><bean:write name="onDuty" property="status"/></td>
<td><bean:write name="onDuty" property="approver"/></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="Permissionlist">
<a href="hrreport.do?method=getHrPermissionReport&hrFromDate=${ hrReportForm.hrFromDate}&hrToDate=${hrReportForm.hrToDate}"" target="_blank">
 <html:button  property="method" value="Report" styleClass="rounded"  style="width:100px;" /></a>
<br/>
<table class="sortable bordered">
<tr>
	<th>Req&nbsp;No</th><th style="width:200px;">Employee Full Name</th><th>Permission Date</th>
	<th style="width:100px;">Start Time</th><th style="width:100px;">End Time</th><th>Request Date</th><th>Type</th>
	<th style="width:100px;">Status</th><th>Approver Name</th>
</tr>
<logic:iterate id="onDuty" name="Permissionlist">
<tr>


<td><bean:write name="onDuty" property="requestNumber"/></td>
<td><bean:write name="onDuty" property="employeeName"/></td>
<td><bean:write name="onDuty" property="permissiondate"/></td>



<td><bean:write name="onDuty" property="startTime"/></td>
<td><bean:write name="onDuty" property="endTime"/></td>
<td><bean:write name="onDuty" property="createDate"/></td>
<td><bean:write name="onDuty" property="type"/></td>
<td><bean:write name="onDuty" property="status"/></td>
<td><bean:write name="onDuty" property="approver"/></td>

</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="no Leave records"> 
<table class="bordered sortable">
			<tr >
					<th>Req&nbsp;No</th><th style="width:50px;">Leave Type</th><th style="width:185px;">Employee Name</th><th>Req Date</th>
					<th style="width:100px;">Start Date</th><th style="width:50px;">Duration</th><th style="width:100px;">End Date</th><th style="width:50px;">Duration</th>
					<th >Days</th><th>Status</th><th>Approver Name</th>
				</tr>
				<tr><td colspan="12" style="color: red"><center>Search Details Not Found..</center></td></tr>
</table>
</logic:notEmpty>
<logic:notEmpty name="no Permission records"> 
<table class="bordered sortable">
			<tr>
	<th>Req&nbsp;No</th><th style="width:200px;">Employee Full Name</th><th>Permission Date</th>
	<th style="width:100px;">Start Time</th><th style="width:100px;">End Time</th><th>Request Date</th>
	<th style="width:100px;">Status</th><th>Approver Name</th>
</tr>
				<tr><td colspan="12" style="color: red"><center>Search Details Not Found..</center></td></tr>
</table>
</logic:notEmpty>
<logic:notEmpty name="no OnDuty records"> 
<table class="bordered sortable">
			<tr >
	<th>Req&nbsp;No</th><th style="width:200px;">Requested By</th><th style="width:100px;">OnDuty&nbsp;Type&nbsp;</th><th style="width:100px;">Plant</th>
	<th style="width:100px;">Start Date</th><th style="width:100px;">From&nbsp;Time</th><th style="width:100px;">End Date</th><th style="width:100px;">To Time</th>
	<th style="width:100px;">Req Date</th><th style="width:100px;">Status</th><th>Approver Name</th>
</tr>
				<tr><td colspan="12" style="color: red"><center>Search Details Not Found..</center></td></tr>
</table>
</logic:notEmpty>


<html:hidden name="hrReportForm" property="department"/>
<html:hidden name="hrReportForm" property="employeeNo"/>
</html:form>
  </body>
</html>
