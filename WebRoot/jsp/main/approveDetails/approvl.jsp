<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- <script type="text/javascript" src="calender/js/calendar-en.js"></script>
other languages might be available in the lang directory; please check your distribution archive.
ALL demos need these css
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/> -->
<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      <link rel="stylesheet" type="text/css" href="css/jquery.dataTables.min.css" />
<!--    <link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
   <script type="text/javascript" src="js/jquery.datepick.js"></script> -->
<script type="text/javascript">


/* $(function() {
	$('#fromDate').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#toDate1').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
}); */

function showSelectedFilterMat(){

	if(document.forms[0].selectedFilterMat.value!="" && document.forms[0].selectedFilter.value!=""){

		var filter = document.getElementById("filterId2").value;
		var url="approvals.do?method=pendingRecords&sCount=0&eCount=0&searchTxt=";
		document.forms[0].action=url;
		document.forms[0].submit();
		}
	}

$(document).ready(function() {
    $('#example').DataTable( {
        initComplete: function () {
            this.api().columns().every( function () {
                var column = this;
                var select = $('<select><option value=""></option></select>')
                    .appendTo( $(column.footer()).empty() )
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
 
                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );
 
                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        }
    } );
} );

function showSelectedFilter(){
if(document.forms[0].reqRequstType.value!="" && document.forms[0].selectedFilter.value!=""){

	var filter = document.getElementById("filterId").value;
	var url="approvals.do?method=pendingRecords&sCount=0&eCount=0&searchTxt=";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
}

function approve(){
if(document.forms[0].reqRequstType.value=="")
{
alert("Please Select Request Type");
 document.forms[0].reqRequstType.focus();
  return false;
}
if(document.forms[0].selectedFilter.value=="")
{
alert("Please Select Filter By");
 document.forms[0].selectedFilter.focus();
  return false;
}
if(document.forms[0].selectedFilter.value!="Pending")
{
alert("Please Choose Request Type As Pending");
 document.forms[0].reqRequstType.focus();
  return false;
}

var rows=document.getElementsByName("selectedRequestNo");
var checkvalues='';
var uncheckvalues='';
for(var i=0;i<rows.length;i++)
{
if (rows[i].checked)
{
checkvalues+=rows[i].value+',';
}else{
uncheckvalues+=rows[i].value+',';
}
}
if(checkvalues=='')
{
alert('please select atleast one record');
}
else
{

var url="approvals.do?method=approveRequest";
		document.forms[0].action=url;
		document.forms[0].submit();
		}
}
function reject(){


if(document.forms[0].reqRequstType.value=="")
{
alert("Please Select Request Type");
 document.forms[0].reqRequstType.focus();
  return false;
}
if(document.forms[0].selectedFilter.value=="")
{
alert("Please Select Filter By");
 document.forms[0].selectedFilter.focus();
  return false;
}

var rows=document.getElementsByName("selectedRequestNo");
var checkvalues='';
var uncheckvalues='';
for(var i=0;i<rows.length;i++)
{
if (rows[i].checked)
{
checkvalues+=rows[i].value+',';
}else{
uncheckvalues+=rows[i].value+',';
}
}
if(checkvalues=='')
{
alert('please select atleast one record');
}
else
{
var url="approvals.do?method=rejectRequest";
		document.forms[0].action=url;
		document.forms[0].submit();
		
}		

}
function nextRecord(){
var url="approvals.do?method=nextRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function previousRecord(){
var url="approvals.do?method=previousRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function lastRecord(){
var url="approvals.do?method=lastRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function firstRecord(){
var url="approvals.do?method=pendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();
}
function getDetails(url){
var url=url;

		document.forms[0].action=url;
		document.forms[0].submit();
}
function getMaterialDetails(url){
var url=url;
document.forms[0].action=url;
document.forms[0].submit();

}

function cancelReq(reqNo,reqType)
{
document.forms[0].action="approvals.do?method=cancelRequest&ReqNo="+reqNo+"&ReqType="+reqType;
document.forms[0].submit();
}
 function viewperinfo(requestno,empid)
 {

var url="approvals.do?method=viewPersonalInfo&reqno="+requestno+"&empId="+empid;
	document.forms[0].action=url;
		document.forms[0].submit();
 }

 function isNumber(evt) {
	    evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	        return false;
	    }
	    return true;
	}

	
	function displayhelpdeskrecord()
	{
		var req=document.forms[0].chooseKeyword.value;
		if(req=="")
			{
			alert("Please Enter Request No.");
			 document.forms[0].chooseKeyword.focus();
			  return false;
			}
		
		var url="approvals.do?method=ITSelectedRequestToApprove&reqId="+req;
		document.forms[0].action=url;
			document.forms[0].submit();
	}
</script>
 <style>
 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}
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
</head>
<body style="text-transform: uppercase;">
<html:form action="/approvals.do" enctype="multipart/form-data" onsubmit="displayhelpdeskrecord();">
<div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>


<table class="bordered" width="90%">
<tr>
	<th colspan="4"><big>My Approvals</big></th>
</tr> 
<tr>
<td><b>Request Type : <font color="red">*</font></b>
</td><td>	<html:select property="reqRequstType" styleClass="Content"  styleId="requestSelectId" onchange="showSelectedFilter()">
	<html:option value="">Select</html:option>
	    <html:option value="Material Master">Material Master</html:option>
		<html:option value="Customer Master">Customer Master</html:option>
		<html:option value="Vendor Master">Vendor Master</html:option>
		<html:option value="Service Master">Service Master</html:option>
		<html:option value="Code Extention">Code Extention</html:option>
		<html:option value="Leave">Leave</html:option>	
			<%-- <html:option value="Cancel Leave">Cancel Leave</html:option> --%>	
		<html:option value="On Duty">On Duty</html:option>
		<html:option value="Travel">Travel Request</html:option> 
		<html:option value="Permission">Permission</html:option>
		<html:option value="IT Request">IT Request</html:option>
		<html:option value="Conference">Conference Room</html:option>
		<html:option value="VC Booking">VC Room</html:option>
		<html:option value="IT Sap Request">Sap Issues</html:option>
		<html:option value="Comp-Off/OT">Comp-Off</html:option>
		<html:option value="OverTime">OverTime</html:option>
		
		<%-- <html:option value="User Request">User Request</html:option> --%>
	</html:select>
	</td>
	
			<%-- 	<html:text property="fromDate" styleId="fromDate"  title="From Date"></html:text>
		 		 - 
		 		<html:text property="toDate"  styleId="toDate1"   title="To Date"></html:text> --%>
		 		
		 
<td><b>Filter by</b> <font color="red">*</font></td>
						<td>
						<html:select property="selectedFilter" styleClass="content" styleId="filterId" onchange="showSelectedFilter()">
						
							<html:option value="">--Select--</html:option>
							<html:option value="Pending">Pending</html:option>
							<html:option value="Approved">Approved</html:option>
							<html:option value="Completed">Completed</html:option>
							<html:option value="Closed">Closed</html:option>
							<html:option value="Rejected">Rejected</html:option>						
							<%-- <html:option value="All">All</html:option> --%>
							</html:select>
						</td>
</tr>						
</table>
<div>&nbsp;</div>
<table>

<tr>
<logic:notEmpty name="displayButton">

<td>
<html:button  property="method" value="Approve" styleClass="rounded" onclick="approve()" style="width:100px;" ></html:button>&nbsp;
<html:button  property="method" value="Reject" styleClass="rounded" onclick="reject()" style="width:100px;"></html:button>&nbsp;
<logic:equal value="Leave" property="reqRequstType" name="approvalsForm">
	<logic:equal value="Pending" property="selectedFilter" name="approvalsForm">
 <a href="approvals.do?method=getLeaveReport" target="_blank"><html:button  property="method" value="Report" styleClass="rounded" onclick="getLeaveReport()" style="width:100px;"/></a>
	</logic:equal>
 </logic:equal>
 <logic:equal value="On Duty" property="reqRequstType" name="approvalsForm">
	<logic:equal value="Pending" property="selectedFilter" name="approvalsForm">
 <a href="approvals.do?method=getOndutyReport" target="_blank"><html:button  property="method" value="Report" styleClass="rounded" onclick="getLeaveReport()" style="width:100px;"/></a>
	</logic:equal>
 </logic:equal>
 <div>&nbsp;</div>
</logic:notEmpty>

<!--<a href="#"><img  src="images/Approve.png" onclick="approve()" style="width:100px;height:35px"/></a>
<a href="#"><img  src="images/Reject.png" onclick="reject()" style="width:100px;height:35px"/></a>
--></td>


<logic:notEmpty name="displayRecordNo">

	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="approvalsForm"/>-
	
	<bean:write property="endRecord"  name="approvalsForm"/>
	
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
	
<div>&nbsp;</div>
<logic:notEmpty name="Usrreq">
<table class="sortable bordered" >
<tr>
<th>Req&nbsp;No</th><th>Request Date</th><th style="width:100px;">Requested&nbsp;By</th><th>Subject</th><th>Pending Approver</th><th>Last Approver</th><th>Status</th><th>View</th>
</tr>
<logic:iterate id="c" name="Usrreq">
<tr>

<td>${c.requestNumber}</td>
<td><bean:write name="c" property="submitDate"/></td>
<td><bean:write name="c" property="employeeName"/></td>
<td><bean:write name="c" property="reason"/></td>
<td><bean:write name="c" property="papprover"/></td>
<td><bean:write name="c" property="lapprover"/></td>


<td><bean:write name="c" property="status"/></td>
<td><a onclick="javascript:getDetails('approvals.do?method=HRqueryRequestToApprove&reqId=${c.requestNumber}')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="OT">
<table class="sortable bordered" >
<tr>
<th>Req&nbsp;No</th><th>Request Date</th><th style="width:100px;">Requested&nbsp;By</th><th>Pending Approver</th><th>Last Approver</th><th >&nbsp;&nbsp;&nbsp;&nbsp;Date</th><th>Status</th><th>View</th>
</tr>
<logic:iterate id="c" name="OT">
<tr>

<td>${c.requestNumber}</td>
<td><bean:write name="c" property="submitDate"/></td>
<td><bean:write name="c" property="employeeName"/></td>
<td><bean:write name="c" property="papprover"/></td>
<td><bean:write name="c" property="lapprover"/></td>
<td ><bean:write name="c" property="startDate"/></td>


<td><bean:write name="c" property="status"/></td>
<td><a onclick="javascript:getDetails('approvals.do?method=OTSelectedRequestToApprove&reqId=${c.requestNumber}')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="comp">
<table class="sortable bordered" >
<tr>
<th>Req&nbsp;No</th><th>Request Date</th><th style="width:100px;">Requested&nbsp;By</th><th>Pending Approver</th><th>Last Approver</th><th >&nbsp;&nbsp;&nbsp;&nbsp;Date</th><th>Status</th><th>View</th>
</tr>
<logic:iterate id="c" name="comp">
<tr>

<td>${c.requestNumber}</td>
<td><bean:write name="c" property="submitDate"/></td>
<td><bean:write name="c" property="employeeName"/></td>

<td><bean:write name="c" property="papprover"/></td>
<td><bean:write name="c" property="lapprover"/></td>
<td ><bean:write name="c" property="startDate"/></td>


<td><bean:write name="c" property="status"/></td>
<td><a onclick="javascript:getDetails('approvals.do?method=compSelectedRequestToApprove&reqId=${c.requestNumber}')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="vcList">
<table class="sortable bordered" >
<tr>
<th>Select</th>	<th>Req&nbsp;No</th><th>Submit Date</th><th style="width:100px;">Requested&nbsp;By</th><th>Location</th><th>Floor</th><th>VC&nbsp;Room</th><th >&nbsp;&nbsp;&nbsp;From&nbsp;Date</th><th style="width: 300px;">&nbsp;&nbsp;&nbsp;To Date</th><th>&nbsp;&nbsp;Last&nbsp;Approver</th><th>&nbsp;Pending&nbsp;Approver</th><th>Status</th><th>View</th>
</tr>
<logic:iterate id="c" name="vcList">
<tr>
<td><html:checkbox property="selectedRequestNo" name="approvalsForm" value="${c.reqNo}"/></td>
<td>${c.reqNo}</td>
<td><bean:write name="c" property="submitDate"/></td>
<td><bean:write name="c" property="requestername"/></td>

<td><bean:write name="c" property="locationId"/></td>
<td><bean:write name="c" property="floor"/></td>
<td><bean:write name="c" property="roomName"/></td>
<td ><bean:write name="c" property="fromDate"/></td>
<td style="width: 300px;"><bean:write name="c" property="toDate"/></td>
<td><bean:write name="c" property="lastApprover"/></td>
<td><bean:write name="c" property="pendingApprover"/></td>
<td><bean:write name="c" property="status"/></td>
<td><a onclick="javascript:getDetails('approvals.do?method=vcSelectedRequestToApprove&reqId=${c.reqNo}')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="confList">
<table class="sortable bordered" >
<tr>
<th>Select</th>	<th>Req&nbsp;No</th><th>Submit Date</th><th style="width:100px;">Requested&nbsp;By</th><th>Location</th><th>Floor</th><th>Conf.Room</th><th >&nbsp;&nbsp;&nbsp;From&nbsp;Date</th><th style="width: 300px;">&nbsp;&nbsp;&nbsp;To Date</th><th>&nbsp;&nbsp;Last&nbsp;Approver</th><th>&nbsp;Pending&nbsp;Approver</th><th>Status</th><th>View</th>
</tr>
<logic:iterate id="c" name="confList">
<tr>
<td><html:checkbox property="selectedRequestNo" name="approvalsForm" value="${c.reqNo}"/></td>
<td>${c.reqNo}</td>
<td><bean:write name="c" property="submitDate"/></td>
<td><bean:write name="c" property="requestername"/></td>

<td><bean:write name="c" property="locationId"/></td>
<td><bean:write name="c" property="floor"/></td>
<td><bean:write name="c" property="roomName"/></td>
<td ><bean:write name="c" property="fromDate"/></td>
<td style="width: 300px;"><bean:write name="c" property="toDate"/></td>
<td><bean:write name="c" property="lastApprover"/></td>
<td><bean:write name="c" property="pendingApprover"/></td>
<td><bean:write name="c" property="status"/></td>
<td><a onclick="javascript:getDetails('approvals.do?method=confSelectedRequestToApprove&reqId=${c.reqNo}')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="it">
<html:text property="chooseKeyword" title="Enter Request Number" styleClass="rounded" onkeypress="return isNumber(event)"/>&nbsp;&nbsp;&nbsp;
<html:button property="method" value="Go" styleClass="rounded" onclick="displayhelpdeskrecord()" style="width:100px;"/>
<br/><br/>
<table class="sortable bordered" >
<tr >
<th>Req&nbsp;No</th><th style="width:50px;">Type</th><th>Priority</th><th style="width:50px;">Requested By</th>
	<th style="width:50px;">Req Date</th> <th>Last Approver</th> <th>Pending Approver</th><th style="width:50px;">Status</th><th> IT Engineer<br/> Status</th><th style="width:25px;">View</th>
</tr>
<logic:iterate id="onDuty" name="it">
<tr>

<td><bean:write name="onDuty" property="requestNumber"/></td>
<td><bean:write name="onDuty" property="requestType"/></td>
 <td width="10%;">    
			           <logic:equal value="Very High" name="onDuty" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: red;"></canvas>
			           </logic:equal>
			          <logic:equal value="High" name="onDuty" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: #990033;"></canvas>
			           </logic:equal>
			             <logic:equal value="Low" name="onDuty" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: green;"></canvas>
			           </logic:equal>
			             <logic:equal value="Medium" name="onDuty" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: orange;"></canvas>
			           </logic:equal>
			             <logic:equal value="Normal" name="onDuty" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: brown;"></canvas>
			           </logic:equal>
			            <bean:write name="onDuty" property="reqPriority" />
			             </td>
<td><bean:write name="onDuty" property="requestername"/></td>
<td><bean:write name="onDuty" property="requestDate"/></td>
<td><bean:write name="onDuty" property="lastApprover"/></td>
<td><bean:write name="onDuty" property="pendingApprover"/></td>
<td><bean:write name="onDuty" property="requestStatus"/></td>
<td><bean:write name="onDuty" property="itEngStatus"/></td>
<td><a onclick="javascript:getDetails('approvals.do?method=ITSelectedRequestToApprove&reqId=${onDuty.requestNumber}')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>


<logic:notEmpty name="sapit">
<table class="sortable bordered" id="example">
<tr >
<th>Req&nbsp;No</th><th style="width:50px;">Type</th><th style="width:100px;">SAP Request</th><th>Priority</th><th style="width:50px;">Requested By</th>
	<th style="width:50px;">Req Date</th> <th>Last Approver</th> <th>Pending Approver</th><th style="width:50px;">Status</th><th style="width:25px;">View</th>
</tr>
<logic:iterate id="onDuty" name="sapit">
<tr>

<td><bean:write name="onDuty" property="requestNumber"/></td>
<td><bean:write name="onDuty" property="requestType"/></td>
<td><bean:write name="onDuty" property="requestName"/></td>
 <td width="10%;">    
  <logic:equal value="Very High" name="onDuty" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: red;"></canvas>
			           </logic:equal>
			          <logic:equal value="High" name="onDuty" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: #990033;"></canvas>
			           </logic:equal>
			             <logic:equal value="Low" name="onDuty" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: green;"></canvas>
			           </logic:equal>
			             <logic:equal value="Medium" name="onDuty" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: orange;"></canvas>
			           </logic:equal>
			             <logic:equal value="Normal" name="onDuty" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: brown;"></canvas>
			           </logic:equal>
			            <bean:write name="onDuty" property="reqPriority" />
			             </td>
		             
<td><bean:write name="onDuty" property="requestername"/></td>
<td><bean:write name="onDuty" property="requestDate"/></td>
<td><bean:write name="onDuty" property="lastApprover"/></td>
<td><bean:write name="onDuty" property="pendingApprover"/></td>
<td><bean:write name="onDuty" property="requestStatus"/></td>

<td><a onclick="javascript:getDetails('approvals.do?method=SapITSelectedRequestToApprove&reqId=${onDuty.requestNumber}')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="ondutyList">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th style="width:200px;">Requested By</th><th style="width:100px;">OnDuty&nbsp;Type&nbsp;</th><th style="width:100px;">Plant</th>
	<th style="width:100px;">From Date</th><th style="width:100px;">From&nbsp;Time</th><th style="width:100px;">To Date</th><th style="width:100px;">To Time</th>
	<th style="width:100px;">Req Date</th><th style="width:100px;">Status</th><th style="width:50px;">View</th><%-- <logic:equal value="Approved" name="approvalsForm"  property="selectedFilter"><th style="width:100px;">Cancel</th></logic:equal> --%>
</tr>
<logic:iterate id="onDuty" name="ondutyList">
<tr>
<td><html:checkbox property="selectedRequestNo" name="approvalsForm" value="${onDuty.requestNumber}"/></td>

<td><bean:write name="onDuty" property="requestNumber"/></td>
<td><bean:write name="onDuty" property="employeeName"/></td>
<td><bean:write name="onDuty" property="onDutyType"/></td>

<td><bean:write name="onDuty" property="locationId"/></td>
<td><bean:write name="onDuty" property="startDate"/></td>
<td><bean:write name="onDuty" property="startTime"/></td>
<td><bean:write name="onDuty" property="endDate"/></td>
<td><bean:write name="onDuty" property="endTime"/></td>
<td><bean:write name="onDuty" property="submitDate"/></td>
<td><bean:write name="onDuty" property="approver"/></td>
<td><a onclick="javascript:getDetails('approvals.do?method=getSelectedRequestToApprove&reqId=${onDuty.requestNumber}&status=${onDuty.approver}&RequesterNo=${onDuty.employeeNumber} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
<%-- <logic:equal value="Approved" name="approvalsForm"  property="selectedFilter"><td><html:button property="method" value="Cancel" styleClass="rounded" onclick="cancelReq('${onDuty.requestNumber}','Onduty')"/></td></logic:equal> --%>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="Permissionlist">
<table class="sortable bordered">
<tr>
<th>Select</th>	<th>Req&nbsp;No</th><th style="width:100px;"> Type</th><th style="width:100px;"> Emp no.</th><th style="width:200px;">Employee Full Name</th><th>Req Date</th>
	<th style="width:100px;"> Time</th>
	<th style="width:100px;">Status</th><th style="width:50px;">View</th><%-- <logic:equal value="Approved" name="approvalsForm"  property="selectedFilter"><th style="width:100px;">Cancel</th></logic:equal> --%>
</tr>
<logic:iterate id="onDuty" name="Permissionlist">
<tr>
<td><html:checkbox property="selectedRequestNo" name="approvalsForm" value="${onDuty.requestNumber}"/></td>

<td><bean:write name="onDuty" property="requestNumber"/></td>
<td><bean:write name="onDuty" property="requestType"/></td>
<td><bean:write name="onDuty" property="employeeNumber"/></td>
<td><bean:write name="onDuty" property="employeeName"/></td>
<td><bean:write name="onDuty" property="createDate"/></td>
<td><bean:write name="onDuty" property="startTime"/></td>


<td><bean:write name="onDuty" property="approver"/></td>
<td><a onclick="javascript:getDetails('approvals.do?method=getSelectedRequestToApprove&reqId=${onDuty.requestNumber}&status=${onDuty.approver}&RequesterNo=${onDuty.employeeNumber} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
<%-- <logic:equal value="Approved" name="approvalsForm"  property="selectedFilter"><td><html:button property="method" value="Cancel" styleClass="rounded" onclick="cancelReq('${onDuty.requestNumber}','Permission')"/></td></logic:equal> --%>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="perinfoList">
          		   
					<table class="sortable bordered">
						<tr>
						
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Requested By</th>
						   <th>Department</th>
						
							<th>Status</th>
							<th>View</th>
						</tr>
						
						
						<logic:iterate name="perinfoList" id="abc">
						<tr class="tableOddTR">
					
							<td >${abc.reqNo }</td>
							<td><bean:write name="abc" property="reqdate" /></td>
							<td><bean:write name="abc" property="empname"/></td>
							
			                  <td><bean:write name="abc" property="dept"/></td>
			       
      						<td><bean:write name="abc" property="reqStatus"/></td>
      						<td >
      						
      						<a onclick="viewperinfo(${abc.reqNo },${abc.empId})" >
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>					
					</table>


          			<logic:notEmpty name="myRequest">
          		   
					<table class="sortable bordered">
						<tr>
						<th>Select</th>
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Requested By</th>
							<th>Requested Type</th>
							<th>Name</th>
							<th>City</th>
							<th>Customer Type</th>
						    <th>Last Approver</th>
						      <th>Pending Approver</th>
							<th>Status</th>
							<th>View</th>
						</tr>
						
						
						<logic:iterate name="myRequest" id="abc">
						<tr class="tableOddTR">
						<td><html:checkbox property="selectedRequestNo" name="approvalsForm" value="${abc.requestNumber}"/></td>
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="createDate" /></td>
							<td><bean:write name="abc" property="requestType"/></td>
							<td>Customer Master</td>
			            <td><bean:write name="abc" property="name"/></td>
			            <td><bean:write name="abc" property="locationName"/></td>
			            <td><bean:write name="abc" property="vendorType"/></td>
			                 <td><bean:write name="abc" property="lastApprover"/></td>
						    <td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="approvalStatus"/></td>
      						<td id="${abc.requestNumber}">
      						
      						<a onclick="javascript:getMaterialDetails('approvals.do?method=getSelectedRequestToApprove&reqId=${abc.requestNumber}&status=${abc.approvalStatus}&matType=${abc.requestType }&vendorType=${abc.vendorType }')" >
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>					
					</table>

          			<logic:notEmpty name="myRequestList">
          		   
					<table class="sortable bordered">
						<tr>
						<th>Select</th>
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Requested By</th>
							<th>Requested Type</th>
							<th>Name</th>
							<th>City</th>
							<th>Vendor Type</th>
						    <th>Last Approver</th>
						      <th>Pending Approver</th>
							<th>Status</th>
							<th>View</th>
						</tr>
						<logic:notEmpty name="myRequestList" >
						
						<logic:iterate name="myRequestList" id="abc">
						<tr class="tableOddTR">
						<td><html:checkbox property="selectedRequestNo" name="approvalsForm" value="${abc.requestNumber}"/></td>
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="createDate" /></td>
							<td><bean:write name="abc" property="requestType"/></td>
							<td>Vendor Master</td>
			            <td><bean:write name="abc" property="name"/></td>
			            <td><bean:write name="abc" property="locationName"/></td>
			            <td><bean:write name="abc" property="vendorType"/></td>
			                 <td><bean:write name="abc" property="lastApprover"/></td>
						    <td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="approvalStatus"/></td>
      						<td id="${abc.requestNumber}">
      						
      						<a onclick="javascript:getMaterialDetails('approvals.do?method=getSelectedRequestToApprove&reqId=${abc.requestNumber}&status=${abc.approvalStatus}&matType=${abc.requestType }&vendorType=${abc.vendorType }')" >
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
						</logic:notEmpty>
					</logic:notEmpty>					
					</table>

<logic:notEmpty name="travel">
<table class="sortable bordered" >
<tr>
<th>Req&nbsp;No</th><th>Request Date</th><th style="width:100px;">Requested&nbsp;By</th><th>Type of Travel</th><th>Origin</th><th>Destination</th><th>Departure Date</th><th>Return Date</th><th>View</th>
</tr>
<logic:iterate id="c" name="travel">
<tr>

<td>${c.requestNumber}</td>
<td><bean:write name="c" property="submitDate"/></td>
<td><bean:write name="c" property="employeeName"/></td>
<td><bean:write name="c" property="typeOfTravel"/></td>
<td><bean:write name="c" property="fromPlace"/></td>
<td><bean:write name="c" property="toPlace"/></td>
<td><bean:write name="c" property="departOn"/></td>
<%-- 
<td><bean:write name="c" property="reason"/></td> --%>
<td><bean:write name="c" property="returnOn"/></td>
<%-- <td><bean:write name="c" property="lapprover"/></td> --%>


<%-- <td><bean:write name="c" property="status"/></td> --%>
<td><a onclick="javascript:getDetails('approvals.do?method=TravelRequestToApprove&reqId=${c.requestNumber}')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="Code Extention list">
          		   
					<table class="sortable bordered">
						<tr>
						<th>Select</th>
							<th>Req. No</th>
							<th>Request Type</th>
							<th>Requested On</th>
								<th>TYPE</th>
								<th>Material</th>
								<th>Description</th>
							<th>Last Approver</th>
							<th>Pending Approver</th>
							<th>Status</th>
							<th>View</th>
						</tr>
						
						
						<logic:iterate name="Code Extention list" id="abc">
						<tr class="tableOddTR">
						<td><html:checkbox property="selectedRequestNo" name="approvalsForm" value="${abc.requestNumber}"/></td>
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="requestType"/></td>
							<td><bean:write name="abc" property="createDate" /></td>
							<td><bean:write name="abc" property="plantType" /></td>
							<td><bean:write name="abc" property="sapNo" /></td>
							<td><bean:write name="abc" property="description" /></td>
							<td><bean:write name="abc" property="lastApprover"/></td>
							<td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="approvalStatus"/></td>
      						<td id="${abc.requestNumber}">
      							<a onclick="javascript:getMaterialDetails('approvals.do?method=getSelectedRequestToApprove&reqId=${abc.requestNumber}&status=${abc.approvalStatus}&matType=${abc.requestType }&planttype=${abc.plantType }')" >
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>					
					</table>

<logic:notEmpty name="serviceMaster">
          		   
					<table class="sortable bordered">
						<tr>
							<th>Select</th>
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Plant</th>
							<th>Material Type</th>
							<th>Service&nbsp;Description</th>
						    <th>Requester Name</th>
						    <th>Last Approver</th>
						    <th>Pending Approver</th>
							<th>Status</th>
							<th>View</th>
						</tr>
						<logic:iterate name="serviceMaster" id="abc">
						<tr class="tableOddTR">
							<td><html:checkbox property="selectedRequestNo" name="approvalsForm" value="${abc.requestNumber}"/></td>
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="requestDate"/></td>
							<td><bean:write name="abc" property="locationCode" /></td>
							<td><bean:write name="abc" property="materailType" /></td>
						  	<td><bean:write name="abc" property="serviceDescription" /></td>
						    <td><bean:write name="abc" property="employeeName"/></td>
						    <td><bean:write name="abc" property="lastApprover"/></td>
						    <td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="approveStatus"/></td>
      						<td id="${abc.requestNumber}">
      						<a onclick="javascript:getMaterialDetails('approvals.do?method=getSelectedRequestToApprove&reqId=${abc.requestNumber}&status=${abc.approveStatus}&matType=${abc.materailType }')" >
      						<img src="images/view.gif" height="28" width="28"/></a></td>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>

<logic:notEmpty name="Material List">
          		   
          		   <!-- <table class="sortable bordered">
						<tr>
						
							<th>Req. No:</th>
							
							<th>Plant
							
							
							</th>
							<th>Mat.Type
							</th>
							
						</tr></table> -->
          		   
					<table id="example" class="sortable bordered">
						<tr>
							<th>Select</th>
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Plant</th>
							<th>Mat.Type
							</th>
							<!-- <th>Material Type</th> -->
							<th>Material&nbsp;Name</th>
						    <th>Requester Name</th>
						    <th>Last Approver</th>
						    <th>Pending Approver</th>
							<th>Status</th>
							<th>View</th>
						</tr>
						<logic:iterate name="materialList" id="abc">
						<tr class="tableOddTR">
							<td><html:checkbox property="selectedRequestNo" name="approvalsForm" value="${abc.requestNumber}"/></td>
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="requestDate"/></td>
							<td><bean:write name="abc" property="locationCode" /></td>
							<td><bean:write name="abc" property="materialDesc" /></td>
						  	<td> <font style="text-transform:uppercase;"><bean:write name="abc" property="materialName" /></font></td>
						    <td><bean:write name="abc" property="employeeName"/></td>
						    <td><bean:write name="abc" property="lastApprover"/></td>
						    <td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="approveStatus"/></td>
      						<td id="${abc.requestNumber}">
      						<a onclick="javascript:getMaterialDetails('approvals.do?method=getSelectedMatRequestToApprove&reqId=${abc.requestNumber}&status=${abc.approveStatus}&matType=${abc.materailType }')" >
      						<img src="images/view.gif" height="28" width="28"/></a></td>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>					
					</table>
							<logic:notEmpty name="no OT records">
<table class="sortable bordered" >
<tr>
<th>Req&nbsp;No</th><th>Request Date</th><th style="width:100px;">Requested&nbsp;By</th><th >&nbsp;&nbsp;&nbsp;From&nbsp;Date</th><th >&nbsp;&nbsp;&nbsp;To Date</th><th>Status</th><th>View</th>
</tr>

</table>
</logic:notEmpty>
					
								<logic:notEmpty name="no comp records">
<table class="sortable bordered" >
<tr>
<th>Req&nbsp;No</th><th>Request Date</th><th style="width:100px;">Requested&nbsp;By</th><th >&nbsp;&nbsp;&nbsp;From&nbsp;Date</th><th >&nbsp;&nbsp;&nbsp;To Date</th><th>Status</th><th>View</th>
</tr>

</table>
</logic:notEmpty>
<logic:notEmpty name="no VC records">
<table class="sortable bordered" >
<tr>
<th>Select</th>	<th>Req&nbsp;No</th><th>Submit Date</th><th style="width:100px;">Requested&nbsp;By</th><th>Location</th><th>Floor</th><th>VC Room</th><th >&nbsp;&nbsp;&nbsp;From&nbsp;Date</th><th style="width: 300px;">&nbsp;&nbsp;&nbsp;To Date</th><th>&nbsp;&nbsp;Last&nbsp;Approver</th><th>&nbsp;Pending&nbsp;Approver</th><th>Status</th><th>View</th>
</tr>

</table>
</logic:notEmpty>					
<logic:notEmpty name="no Conference records">
<table class="sortable bordered" >
<tr>
<th>Select</th>	<th>Req&nbsp;No</th><th>Submit Date</th><th style="width:100px;">Requested&nbsp;By</th><th>Location</th><th>Floor</th><th>Conf.Room</th><th >&nbsp;&nbsp;&nbsp;From&nbsp;Date</th><th style="width: 300px;">&nbsp;&nbsp;&nbsp;To Date</th><th>&nbsp;&nbsp;Last&nbsp;Approver</th><th>&nbsp;Pending&nbsp;Approver</th><th>Status</th><th>View</th>
</tr>

</table>
</logic:notEmpty>
<logic:notEmpty name="no it records">
<logic:equal value="IT Request" property="reqRequstType" name="approvalsForm">
<table class="sortable bordered" >
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th style="width:50px;">Type</th><th>Priority</th><th style="width:50px;">Requested By</th>
	<th style="width:50px;">Req Date</th> <th>Last Approver</th> <th>Pending Approver</th><th style="width:50px;">Status</th><th> IT Engineer Status</th><th style="width:25px;">View</th>
</tr>

</table>

</logic:equal>	
</logic:notEmpty>	
<logic:notEmpty name="no Material Master records">

<logic:equal value="Code Extention" property="reqRequstType" name="approvalsForm">
<table class="sortable bordered">
<tr><th>Select</th><th>Req. No</th><th>Request Type</th><th>Requested On</th><th>TYPE</th><th>Material</th><th>Description</th><th>Last Approver</th>
<th>Pending Approver</th><th>Status</th><th>View</th></tr>	
</logic:equal>
<logic:equal value="Material Master" property="reqRequstType" name="approvalsForm">


<table class="sortable bordered">
<tr><th>Select</th><th>Req. No</th><th>Requested On</th><th>Plant</th><th>Material Type</th><th>Material&nbsp;Name</th><th>Requester Name</th>
<th>Last Approver</th><th>Pending Approver</th><th>Status</th><th>View</th></tr>
</logic:equal>
<logic:equal value="Service Master" property="reqRequstType" name="approvalsForm">
<table class="sortable bordered">
<tr><th>Select</th><th>Req. No</th><th>Requested On</th><th>Plant</th><th>Material Type</th><th>Service&nbsp;Description</th><th>Requester Name</th><th>Last Approver</th><th>Pending Approver</th>
<th>Status</th>	<th>View</th></tr>
</logic:equal>
<logic:equal value="Vendor Master" property="reqRequstType" name="approvalsForm">
	<table class="sortable bordered">
<tr><th>Select</th><th>Req. No</th><th>Requested On</th><th>Requested By</th><th>Requested Type</th><th>Name</th><th>City</th>
	<th>Vendor Type</th><th>Last Approver</th><th>Pending Approver</th>	<th>Status</th>	<th>View</th></tr>
</logic:equal>

<logic:equal value="Customer Master" property="reqRequstType" name="approvalsForm">
<table class="sortable bordered">
<tr>
<th>Select</th><th>Req. No</th><th>Requested On</th><th>Requested By</th><th>Requested Type</th><th>Name</th><th>City</th><th>Customer Type</th>
<th>Last Approver</th><th>Pending Approver</th><th>Status</th><th>View</th></tr>
</logic:equal>

<logic:equal value="Permission" property="reqRequstType" name="approvalsForm">
<table class="sortable bordered">
<tr>
<th>Select</th>	<th>Req&nbsp;No</th><th style="width:100px;"> Type</th><th style="width:200px;">Employee Full Name</th><th>Req Date</th>
	<th style="width:100px;">Start Time</th><th style="width:100px;">End Time</th>
	<th style="width:100px;">Status</th><th style="width:50px;">View</th><logic:equal value="Approved" name="approvalsForm"  property="selectedFilter"><th style="width:100px;">Cancel</th></logic:equal>
</tr>
</logic:equal>
<logic:equal value="On Duty" property="reqRequstType" name="approvalsForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th style="width:200px;">Requested By</th><th style="width:100px;">OnDuty&nbsp;Type&nbsp;</th><th style="width:100px;">Plant</th>
	<th style="width:100px;">From Date</th><th style="width:100px;">From&nbsp;Time</th><th style="width:100px;">To Date</th><th style="width:100px;">To Time</th>
	<th style="width:100px;">Req Date</th><th style="width:100px;">Status</th><th style="width:50px;">View</th><logic:equal value="Approved" name="approvalsForm"  property="selectedFilter"><th style="width:100px;">Cancel</th></logic:equal>
</tr>
</logic:equal>


<tr>
<td colspan="13">
<div align="center">
<font color="red" size="3">Searched details could not be found.</font>
</div>
</td>
</tr>
</table>
</logic:notEmpty>	


<logic:notEmpty name="leaveList">	



		<table class="bordered sortable">
			<tr>
				<th>Select</th>	<th>Req&nbsp;No</th><th style="width:100px;">Leave Type</th><th style="width:200px;">Employee Name</th><th>Req Date</th>
					<th style="width:100px;">From Date</th><th style="width:100px;">Duration</th><th style="width:100px;">To Date</th><th style="width:100px;">Duration</th>
					<th style="width:100px;">No Of Days</th><th style="width:100px;">Status</th><th style="width:50px;">View</th><%-- <logic:equal value="Approved" name="approvalsForm"  property="selectedFilter"><th style="width:100px;">Cancel</th></logic:equal> --%>
				</tr>
					
<logic:iterate id="leave" name="leaveList">

<tr>
<td><html:checkbox property="selectedRequestNo" name="approvalsForm" value="${leave.requestNumber}"/></td>

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
<td><a onclick="javascript:getDetails('approvals.do?method=getSelectedRequestToApprove&reqId=${leave.requestNumber}&status=${leave.approveStatus}')" ><img src="images/view.gif" height="28" width="28"/></a></td>
<%-- <logic:equal value="Approved" name="approvalsForm"  property="selectedFilter"><logic:notEqual  value="DisableCancel" name="leave"  property="appType"><td><html:button property="method" value="Cancel" styleClass="rounded" onclick="cancelReq('${leave.requestNumber}','Leave')"/></td></logic:notEqual></logic:equal>
<logic:equal value="Approved" name="approvalsForm"  property="selectedFilter"><logic:equal  value="DisableCancel" name="leave"  property="appType"><td>&nbsp;</td></logic:equal></logic:equal>
 --%></tr>

</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="no Leave records">

		<table class="bordered sortable">
		<tr>
				<th>Select</th>	<th>Req&nbsp;No</th><th style="width:100px;">Leave Type</th><th style="width:200px;">Employee Name</th><th>Req Date</th>
					<th style="width:100px;">From Date</th><th style="width:100px;">Duration</th><th style="width:100px;">To Date</th><th style="width:100px;">Duration</th>
					<th style="width:100px;">No Of Days</th><th style="width:100px;">Status</th><th style="width:50px;">View</th><logic:equal value="Approved" name="approvalsForm"  property="selectedFilter"><th style="width:100px;">Cancel</th></logic:equal>
				</tr>
				
<tr>
<td colspan="12">				
<div align="center">
<font color="red" size="3">Searched details could not be found.</font>
</div></td></tr>
</table>
</logic:notEmpty>

<logic:notEmpty name="no OnDuty records">
<div class="bordered">
		<table class="bordered sortable">
			<tr>
<th>Select</th>	<th>Req&nbsp;No</th><th style="width:100px;">OnDuty Type</th><th style="width:200px;">Employee Name</th><th>Req Date</th>
	<th style="width:100px;">Start Date</th><th style="width:100px;">Duration</th><th style="width:100px;">End Date</th><th style="width:100px;">Duration</th>
	<th style="width:100px;">No Of Days</th><th style="width:100px;">Status</th><th style="width:50px;">View</th>
</tr>
</table>
<div align="center">
<font color="red" size="3"><bean:write property="message3" name="approvalsForm"/></font>
</div>

</logic:notEmpty>

</table>
</html:form>
</html>
