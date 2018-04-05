<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <head>
    <base href="<%=basePath%>">
    
    <title>Travel Request :: View Request</title>
    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      <link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
      <link rel="stylesheet" type="text/css" href="css/styles.css" />

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



$(function() {
	$('#popupDatepicker3').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker3').datepick({onSelect: showDate});
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

function chkguest()
{

var x=document.forms[0].travelFor.value;

if(x=="Self")
	{
	document.getElementById("guestname1").style.display="none";
	document.getElementById("guestname2").style.display="none";
	}
else
	{
	document.getElementById("guestname1").style.display="";
	document.getElementById("guestname2").style.display="";

	}


}

function uploadDocument()
{
	document.forms[0].action="travelrequest.do?method=uploadDocuments";
	document.forms[0].submit();
}


function deleteDocumentsSelected()
{


var rows=document.getElementsByName("documentCheck");
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
alert('please select atleast one value to delete');
}
else
{
var agree = confirm('Are You Sure To Delete Selected file');
if(agree)
{

	document.forms[0].action="travelrequest.do?method=deleteDocuments";
	document.forms[0].submit();
}
}
}
function downloadFile(fileName){
/*
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
		  
		  
	    }
	  }

	xmlhttp.open("POST","travelrequest.do?method=downloadFileJRS&FileName="+fileName,true);
	  xmlhttp.send();
		*/
	  document.forms[0].action="travelrequest.do?method=downloadFileJRS&FileName="+fileName;
		document.forms[0].submit();
}
function statusMessage(message){
alert(message);
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}



function applyDomastic(){

	
 	var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	document.forms[0].action="travelrequest.do?method=saveDomestic";
	document.forms[0].submit();
	}
	
function closerequest(){

	
 
	document.forms[0].action="travelrequest.do?method=displayMyrequest";
	document.forms[0].submit();
	}
	
function submitfeedback()	
{
if(document.forms[0].review_Trip.value=="")
{
alert("Please Enter review Comments");
return false;
}

if(document.forms[0].review_Rating.value=="")
{
alert("Please Select Ratings");
return false;
}


document.forms[0].action="travelrequest.do?method=updatetravelReview";
	document.forms[0].submit();
}
</script>
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

<style>

.no
{pointer-events: none; 
}
.design

{
	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);
} 


</style>

<style>
      .example-modal .modal {
        position: absolute;
        top: auto;
        bottom: auto;
        right: 90px;
        left: 90px;
        display: block;
        z-index: 1;
      }
      .example-modal .modal {
        background: transparent !important;
      }
      
       /* Style the list */
ul.tab {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    border: 1px solid #ccc;
    background-color: rgb(227,237,250);
    border-radius: 6px 6px 0 0;
    width: 100%;
}

/* Float the list items side by side */
ul.tab li {float: left;}

/* Style the links inside the list items */
ul.tab li a {
    display: inline-block;
    color: black;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    transition: 0.3s;
    font-size: 17px;
}

/* Change background color of links on hover */
ul.tab li a:hover {background-color: #ddd;}

/* Create an active/current tablink class */
ul.tab li a:focus, .active {background-color: #ccc;}

/* Style the tab content */
.tabcontent {
    display: none;
    padding: 6px 12px;
    border: 1px solid #ccc;
    border-top: none;
}

.tabcontent1 {
    display: none;
    padding: 6px 12px;
    border: 1px solid #ccc;
    border-top: none;
}
      
    </style>
    
    <script >
 function openCity(evt, cityName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}


function change()
{

$('#defaultOpen').click();

}

function process(sid,type)
{
alert("hi");
var dt=document.forms[0].requestNumber.value;
var dt1=document.forms[0].travelRequestFor.value;

var x=window.showModalDialog("travelrequest.do?method=ViewDetails&reqno="+dt+"&sid="+sid+"&travelRequestFor="+dt1+"&type="+type,null, "dialogWidth=800px;dialogHeight=600px; center:yes");
}

</script>
  </head>
  
<body onload="change()">
	<html:form action="travelrequest" enctype="multipart/form-data" method="post" >
  	<div id="masterdiv" class="">
		<logic:notEmpty name="travel">
		<logic:iterate id="abc" name="travel">
		<ul class="tab" style="width: 900px; margin-left: 22px" >
			<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'TravelTab')" id="defaultOpen">Requester Detail </a></li>
			<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R1')">Traveller Detail </a></li>
			<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R2')">Traveller List </a></li>
			<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R3')">Multi City List </a></li>
			<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R6')">Monthly Tour Plan </a></li>
			<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R4')">Booking Details </a></li>
			<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R5')">Approval Status </a></li>			
		</ul>

		<div id="TravelTab" class="tabcontent" style="width: 850px; margin-left: 22px" >
			<table class="bordered" style="position: relative;left: 2%;width: 80%;">
			<tr><th colspan="4"><center>Travel Request Form View</center></th></tr>
			<tr>
				<th><center><b>Field Label</b></center></th>
				<th><center><b>Field Value</b></center></th>
				<th><center><b>Field Label</b></center></th>
				<th><center><b>Field Value</b></center></th>
			</tr>
			<tr>
				<td>Request No: </td>
				<td>${abc.requestNumber}</td>
				<td>Request Date: </td>
				<td>${abc.reqDate}</td>
			</tr>
			<tr>
				<td>Travel Mode: </td>
				<td>
					<div class="no">
						${abc.travelmode}
					</div>	
				</td>	
				<td>Travel Type: </td>
				<td>
					<div class="no">
						${abc.traveltype}
					</div>	
				</td>
			</tr>
			<tr>
				<td>Employee No.: <font color="red" size="3">*</font></td>
				<td><bean:write name="abc" property="employeeno" /></td>
				<td>Employee Name: <font color="red" size="3">*</font></td>
				<td><bean:write name="abc" property="employeeName" /></td>
			</tr>
			<tr>
				<td>Department: <font color="red" size="3">*</font></td>
				<td><bean:write name="abc" property="department" /></td>
				<td>Designation: <font color="red" size="3">*</font></td>
				<td><bean:write name="abc" property="designation" /></td>
			</tr>
			<tr>
				<td>Gender: <font color="red" size="3">*</font></td>
				<td>${abc.userGender}</td>
				<td>Age: <font color="red" size="3">*</font></td>
				<td>${abc.userAge}</td>
			</tr>
			
			<tr>
				<td>Contact No.: <font color="red" size="3">*</font></td>
				<td>${travelRequestForm.usermobno}&nbsp;&nbsp; </td>
				<td>Official Email id<br/>Personal Email Id: <font color="red" size="3">*</font></td>
				<td>${travelRequestForm.usermailId}<br/>${travelRequestForm.userPersonalmailId}</td>
			</tr>
			<tr>
				<td>Passport No: <font color="red" size="3">*</font></td>
				<td>${abc.userpassportno}</td>
				<td>Place of Issue: <font color="red" size="3">*</font></td>
				<td>${abc.userpassportplace}</td>
			</tr>
			<tr>
				<td>Date of issue: <font color="red" size="3">*</font></td>
				<td>${abc.userpassportissuedate}</td>
				<td>Date of expiry: <font color="red" size="3">*</font></td>
				<td>${abc.userpassportexpirydate}</td>
			</tr>
			<tr>
			<td colspan="1">Travel Desk: <font color="red" size="3">*</font></td>
			<td colspan="3"><bean:write name="abc" property="travel_desk_type" />  </td>
			</tr>
			<tr style="display:none; " id="passport1">
				<td>Passport No: <font color="red" size="3">*</font></td>
				<td>${abc.passportno}</td>
		        <td>Place of Issue: <font color="red" size="3">*</font></td>
				<td>${abc.passportplace}</td>							
            </tr>
			<tr style="display:none; " id="passport2">
				<td>Date of issue: <font color="red" size="3">*</font></td>
				<td>${abc.passportissuedate}</td>
                <td>Date of expiry: <font color="red" size="3">*</font></td>
		        <td>${abc.passportexpirydate}</td>
            </tr>
        </table>
		<br/>
		
		<!-- rescheduled details -->
		<logic:notEqual name="abc" property="oldrequestNumber" value="0">
		<table  class="bordered" style="position: relative;left: 2%;width: 80%; ">
			<tr>
			<th>Rescheduled Request</th>
			<td><a href="travelrequest.do?method=ViewMyrequest&requstNo=<bean:write name="abc" property="oldrequestNumber"/>" title="View">${abc.oldrequestNumber}</a></td>
			</tr>
			</table>
		
</logic:notEqual>
	</div>

	<!-- // End of R (Traveller Details) Tab -->
	<div id="R1" class="tabcontent" style="width: 890px; margin-left: 22px" >
		<table  class="bordered" style="position: relative;left: 2%;width: 80%; ">
			<tr>
				<th colspan="4"><center>Traveller Details</center></th></tr>
			<tr>
			<tr>
				<th><center><b>Field Label</b></center></th>
				<th><center><b>Field Value</b></center></th>
				<th><center><b>Field Label</b></center></th>
				<th><center><b>Field Value</b></center></th>
			</tr>
				<td>Service Class: <font color="red" size="3">*</font></td>
				<td>${abc.service_class}</td>
				<td>Travel Request For: <font color="red" size="3">*</font></td>
				<td class="no">
					<html:radio property="travelRequestFor" value="One Way" name="abc">
					</html:radio>One Way&nbsp;<html:radio property="travelRequestFor" value="Round Trip" name="abc">
					</html:radio>Round Trip&nbsp;<html:radio property="travelRequestFor" value="Multi-City" name="abc"></html:radio>Multi-City&nbsp;<html:radio property="travelRequestFor" value="Multi-City1" name="abc"></html:radio>Monthly Tour Plan &nbsp;
				</td>					
			</tr>
			<tr>	
				<td>Purpose: </td>
				<td><bean:write name="abc" property="purposetype"/></td>
				<td>Location: </td>
				<td></td>
			</tr>
			<tr>
				<td>Purpose Detail</td>	
				<td colspan="3"><bean:write name="abc" property="purposetext"/></td>
			</tr>
			<tr>
				<td>Travel For: <font color="red" size="3">*</font></td>
				<td><bean:write name="abc" property="travelFor"/></td>	
				<td>Total No of Travellers: </td>
				<td>Adult: ${abc.travel_Adult}
					&nbsp;   Children: ${abc.travel_Child} &nbsp; 
					infant: ${abc.travel_Infant}
				</td>
			</tr>
			
			<tr>
				<td>Contact Name</td>
				<td>${abc.p_name}"</td>
				<td>Contact Department</td>
				<td>
				${abc.p_dept}"  
				</td>
				</tr>
				
				
				<tr>
				<td>Contact No</td>
				<td>
				${abc.p_cont}" 
				</td>
				<td>Contact Email</td>
				<td>
				${abc.p_email}"
				</td>
				</tr>
			<tr>
				<td>Origin: <font color="red" size="3">*</font></td>
				<td>${abc.fromPlace}</td>
			<!-- </tr>			
		    <tr> -->
				<td>Trip Departure Date: <font color="red" size="3">*</font></td>
				<td>${abc.departOn}</td>
				<%-- <td>Preferred Departure Time: <font color="red" size="3">*</font></td>
		        <td>${abc.departTime}</td> --%>
			 </tr>
		    <tr> 
				<td>Final Destination:<font color="red" size="3">*</font></td>
				<td>${abc.toPlace}</td>
			<!-- </tr>			
		    <tr> -->
				<td>Trip Return Date: <font color="red" size="3">*</font></td>
				<td>${abc.returnOn}</td>
				<%-- <td>Preferred Return Time: <font color="red" size="3">*</font></td>
		        <td>${abc.returnTime}</td> --%>
			</tr>
			<tr>
				<td>Travel Days: </td>
				<td>${abc.travel_Days}</td>
				<td>Priority: </td> 
		        <td>${abc.trip_Priority}</td>
			</tr>
		    <tr>
				<td>Travel Company Preference: </td>
				<td>${abc.airline_Pref }</td>
                <td>Justifcation: <font color="red" size="3">*</font></td>
				<td>${abc.airline_Just}</td>
		    </tr>
		    <tr>	
				<td>Sponsoring Division: </td>
				<td>${abc.spon_div} </td>
				<td>Budget Code: </td>
				<td>${abc.bud_code} </td>
			</tr>
			<tr>
		        <td>Estimated Trip Cost: </td>
				<td colspan="3">${abc.est_trip_cose} </td>
			</tr>
			<tr>
				<td>Trip Advance required: <font color="red" size="3">*</font></td>
				<td>${abc.trip_Advance}</td>
				<td>On Duty to be applied: <font color="red" size="3">*</font></td>
				<td>${abc.onduty_Req}</td>
			</tr>
			<tr>
				<td>Amount: <font color="red" size="3">*</font></td>
				<td>${abc.trip_Amt}</td>
				<td>Currency: <font color="red" size="3">*</font></td>
				<td>${abc.trip_Currency}</td>
			</tr>
			
		</table>
		<br/>
		<table class="bordered" style="position: relative;left: 2%;width: 80%;">
			<tr><th colspan="4"><center>Other Details</center></th></tr>
			<tr><td>Accomodation<font color="red" size="3">*</font></td>
			<td>${abc.hotel_Res}</td>		
				<td>Rental Car required: <font color="red" size="3">*</font></td>
				<td>${abc.rent_Car}</td>	
			</tr>
			<tr>
			    <td>Accomodation Type</td>
				<td>
				${abc.accom_type}
				</td>
				<td>Guest House Name</td>
				<td>
				${abc.accom_name}
				</td>
				</tr>
			<tr>
				<td>Hotel Name: </td>
				<td>${abc.hotel_Name}</td>
				<td>Hotel City: </td>
				<td>${abc.hotel_City}</td>
			</tr>
			<tr>
				<td>Pickup Details: </td>
				<td>${abc.pickup_Details}</td>
				<td>Drop Details: </td>
				<td>${abc.drop_Details}</td>
			</tr>
		</table>
	</div>
	<div id="R2" class="tabcontent" style="width: 890px; margin-left: 22px" >
			<%-- <table class="bordered" style="position: relative;left: 2%;width: 80%;">
				<tr>
					<th colspan="12"><center>Traveller List</center></th>
				</tr>
				<tr>
					<th>Traveller Type</th>
					<th>Emp.No.</th>
					<th>Title</th>
					<th>Name</th>
					<th>Gender</th>
					<th>Email</th>
					<th>Contact No.</th>
					<th>Company Name</th>
					<th>Age</th>
					<th>Meal Preference</th>
					<th>Attachment</th>
					<th>View</th>
				</tr>
				<logic:notEmpty name="emplist">
				<logic:iterate id="abc1" name="emplist">
				<tr>
					<td>${abc1.guest_Type}</td>
					<td>${abc1.guest_pernr}</td>
					<td>${abc1.guest_Title}</td>
					<td>${abc1.guestName}</td>
					<td>${abc1.gender}</td>
					<td>${abc1.email_Guest}</td>
					<td>${abc1.guestContactNo}</td>
					<td>${abc1.guest_Company}</td>
					<td>${abc1.guest_DOB}</td>
					<td>${abc1.guest_Meal}</td>
					<td><a href="${abc1.fileFullPath}"  target="_blank">${abc1.fileName}</a></td>
					<td><a href="javascript:process(${abc1.id},'travellist')" 
					 title="View"><img src="images/view.gif" width="16" height="16"/></a></td>
				</tr>
				</logic:iterate>
				</logic:notEmpty>
			</table> --%>
			<table class="bordered" style="position: relative;left: 2%;width: 80%;">
				<tr>
					<th colspan="12"><center>Traveller List</center></th>
				</tr>
				<tr>
					<th>Traveller Type</th>
					<!-- <th>Emp.No.</th>
					<th>Title</th> -->
					<th>Name</th>
				<!-- 	<th>Gender</th> -->
					<th>Email</th>
					<th>Contact No.</th>
					<!-- <th>Company Name</th>
					<th>Age</th> -->
					<th>Origin</th>
					<th>Depart On</th>
					<th>Destination</th>
					<th>Return On</th>
					<th>Meal Preference</th>
					<th>Attachment</th>
					<th>View</th>
				</tr>
				<logic:notEmpty name="emplist">
				<logic:iterate id="abc1" name="emplist">
				<tr>
					<td>${abc1.guest_Type}</td>
					<%-- <td>${abc1.guest_pernr}</td>
					<td>${abc1.guest_Title}</td> --%>
					<td>${abc1.guestName}</td>
					<%-- <td>${abc1.gender}</td> --%>
					<td>${abc1.email_Guest}</td>
					<td>${abc1.guestContactNo}</td>
				<%-- 	<td>${abc1.guest_Company}</td>
					<td>${abc1.guest_DOB}</td> --%>
						<td>${abc1.fromPlace}</td>
					<td>${abc1.departOn}</td>
						<td>${abc1.toPlace}</td>
					<td>${abc1.returnOn}</td>
					<td>${abc1.guest_Meal}</td>
					<td><a href="${abc1.fileFullPath}"  target="_blank">${abc1.fileName}</a></td>
					<td><a href="javascript:process(${abc1.id},'travellist')" 
					 title="View"><img src="images/view.gif" width="16" height="16"/></a></td>
				</tr>
				</logic:iterate>
				</logic:notEmpty>
			</table>
						
 	</div> 
	<!-- // End of R2 (Traveller List) Tab -->	

	<!-- // Start of R3 (<Multi City List) Tab -->	
	<div id="R3" class="tabcontent" style="width: 890px; margin-left: 22px" > 
		<table class="bordered" style="position: relative;left: 2%;width: 80%;" id="mcity">
				<tr><th colspan="8">Multiple City List </th></tr>
				<tr>
					<th>Travel list</th><th>Location</th><th> Preference</th><th>Arrival date</th> 
					<th>Arrival Time</th><th>Departure Date</th><th>Departure Time</th>
					<th>View</th>
				</tr>
				<logic:notEmpty name="city"> 
				<logic:iterate id="abc2" name="city">
				<tr>
<td>${abc2.multiemployeeno}<input name="userlistIdl" value="${abc2.multiemployeeno}" type="hidden"></td>
<td>${abc2.locationId}<input name="mlocl" value="${abc2.locationId}" type="hidden"></td>
<td>${abc2.airline_Pref}<input name="mairpl" value="${abc2.airline_Pref}" type="hidden">
</td><td>${abc2.trip_From_Date}<input name="marrvdatel" value="${abc2.trip_From_Date}" type="hidden">
</td><td>${abc2.trip_To_time}<input name="marrtimel" value="${abc2.trip_To_time}" type="hidden">
</td><td>${abc2.trip_To_Date}<input name="mdeptdatel" value="${abc2.trip_To_Date}" type="hidden">
</td><td>${abc2.trip_To_time}<input name="mdepttimel" value="${abc2.trip_To_time}" type="hidden">
</td><td style="display: none;"><input name="mhotel_Resl" value="${abc2.hotel_Res}" type="hidden"></td>
<td style="display: none;"><input name="mrent_Carl" value="${abc2.rent_Car}" type="hidden"></td>
<td style="display: none;"><input name="mhotel_Namel" value="${abc2.hotel_Name}" type="hidden"></td>
<td style="display: none;"><input name="mhotel_Cityl" value="${abc2.hotel_City}" type="hidden"></td>
<td style="display: none;"><input name="mpickup_Detailsl" value="${abc2.pickup_Details}" type="hidden">
</td><td style="display: none;"><input name="mdrop_Detailsl" value="${abc2.drop_Details}" type="hidden">
</td>
<td><a href="javascript:process(${abc2.id},'multicity')" 
					 title="View"><img src="images/view.gif" width="16" height="16"/></a></td>
</tr>
				
				</logic:iterate>
				</logic:notEmpty>
				
			</table>
	</div>

	
	<div id="R6" class="tabcontent" style="width: 890px; margin-left: 22px" > 
		<table class="bordered" style="position: relative;left: 2%;width: 80%;" id="mcity1">
				<tr><th colspan="8">Monthly Travel Plan </th></tr>
				<tr>
					<th>Date</th><th>Origin</th><th> Destination</th><th>TravelMode</th> 
					<th>TravelType</th><th>Traveldesk</th><th>Remarks</th>
					
				</tr>
				<logic:notEmpty name="appList1"> 
				<logic:iterate id="abcp4" name="appList1">
				<tr>
<td>${abcp4.fromdate}<input name="mdate" value="${abcp4.fromdate}" type="hidden"></td>
<td>${abcp4.origin}<input name="morigin" value="${abcp4.origin}" type="hidden"></td>
<td>${abcp4.departure}<input name="mdestination" value="${abcp4.departure}" type="hidden">
<td>${abcp4.travelmode}<input name="mode" value="${abcp4.travelmode}" type="hidden"></td>
<td>${abcp4.traveltype}<input name="mtype" value="${abcp4.traveltype}" type="hidden"></td>
<td>${abcp4.travel_desk_type}<input name="mtype" value="${abcp4.traveltype}" type="hidden"></td>
<td>${abcp4.remarks}<input name="mremarks" value="${abcp4.remarks}" type="hidden">

 </tr>

				
</logic:iterate>
	</logic:notEmpty>
				
			</table>
	</div>
	<!-- // End of R3 (<Multi City List) Tab -->	

	<!-- // Start of R4 (Booking Details) Tab -->	
	<div id="R4" class="tabcontent" style="width: 890px; margin-left: 22px" >
			<table class="bordered" style="position: relative;left: 2%;width: 80%;">
						<tr>
						<th colspan="16">Traveller List</th>
						</tr>
						<tr>
							<th>Traveller Type</th> <th>Employee No</th>
						<th>Title</th> <th>Name</th>
						<th>Gender</th> <th>Email</th>
						<th>Contact No</th> <th>Company Name</th>
						 <th>Age</th>
						<th>Attachment</th>
						</tr>
						<logic:notEmpty name="emplist">
						
						<logic:iterate id="abc1" name="emplist">
						<script>
						</script>
						
						<tr>
						<td>${abc1.guest_Type}</td>
						<td>${abc1.guest_pernr}  </td>
						<td>${abc1.guest_Title}</td>
						<td>${abc1.guestName}</td></td>
						<td>${abc1.gender}</td>
						<td>${abc1.email_Guest}</td>
						<td>${abc1.guestContactNo}</td>
						<td>${abc1.guest_Company}</td>
						<td>${abc1.guest_DOB}</td>
						<td><a href="${abc.fileFullPath}" target="_blank">${abc.fileName}</a></td>
						</tr>
						
						
						
						</logic:iterate>
						
						</logic:notEmpty>
						
					
						</table>
	</div>
	<!-- // End of R4 (Booking Details) Tab -->	

		<logic:notEmpty name="appList">
		<!-- // Start of R5 (Approval Status) Tab -->	
		<div id="R5" class="tabcontent" style="width: 890px; margin-left: 22px" >
			<table class="bordered" style="position: relative;left: 2%;width: 80%;">
				<tr>
					<th colspan="5"><center>Approval Status</center></th>
				</tr>
				<tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
				<logic:iterate id="abc3" name="appList">
				<tr>
					<td>${abc3.approver }</td>
					<td>${abc3.designation }</td>
					<td>${abc3.approveStatus }</td>
					<td>${abc3.approveDate }</td>
					<td>${abc3.comments }</td>
				</tr>
				</logic:iterate>
			</table>
		</div>
		<!-- // End of R5 (Approval Status) Tab -->
		</logic:notEmpty>				
	
			<logic:notEmpty name="appList1">
		<!-- // Start of R5 (Approval Status) Tab -->	
		
		<!-- // End of R5 (Approval Status) Tab -->
		</logic:notEmpty>	
	</br>
	
		<logic:notEmpty name="abc" property="review_Trip">
	<table class="bordered" style="position: relative;left: 2%;width: 80%;">
	<tr>
	<td>Feedback Comments<font color="red" size="3">*</font></td>
	<td><textarea rows=""   name="review_Trip"  cols="60">${abc.review_Trip}</textarea>   </td>
	</tr>
	<tr>
	<td>Ratings<font color="red" size="3">*</font> </td>
	<td>
						<html:radio property="review_Rating" value="Excellent" name="abc"></html:radio>Excellent &nbsp;
						<html:radio property="review_Rating" value="Very Good" name="abc"></html:radio>Very Good &nbsp;
						<html:radio property="review_Rating" value="Good" name="abc"></html:radio>Good &nbsp;
						<html:radio property="review_Rating" value="Average" name="abc"></html:radio>Average &nbsp;
						<html:radio property="review_Rating" value="Bad" name="abc"></html:radio>Bad &nbsp;
	  </td>
	</tr>
	</table>
	</logic:notEmpty>
	
	
	
	<logic:empty name="abc" property="review_Trip">
	<table class="bordered" style="position: relative;left: 2%;width: 80%;">
	<tr>
	<td>Feedback Commments<font color="red" size="3">*</font></td>
	<td><textarea rows=""    name="review_Trip"  cols="60"></textarea>   </td>
	</tr>
	<tr>
	<td>Ratings<font color="red" size="3">*</font> </td>
	<td>
						<html:radio property="review_Rating" value="Excellent" name="travelRequestForm"></html:radio>Excellent &nbsp;
						<html:radio property="review_Rating" value="Very Good" name="travelRequestForm"></html:radio>Very Good &nbsp;
						<html:radio property="review_Rating" value="Good" name="travelRequestForm"></html:radio>Good &nbsp;
						<html:radio property="review_Rating" value="Average" name="travelRequestForm"></html:radio>Average &nbsp;
						<html:radio property="review_Rating" value="Bad" name="travelRequestForm"></html:radio>Bad &nbsp;
	  </td>
	</tr>
	</table>
	</logic:empty>
	</logic:iterate>	
	</logic:notEmpty>
	
	
	<logic:empty name="review">
							
		<table class="bordered" style="position: relative;left: 2%;width: 80%;">
			<tr>
				<td align="center">
				<center>
					<html:button property="method" styleClass="rounded" value="Close" onclick="history.back(-1)" style="align:right;width:100px;"/>
				</center>
				</td>
			</tr>
		</table>
	</logic:empty>
	
	<logic:notEmpty name="review">
							
		<table class="bordered" style="position: relative;left: 2%;width: 80%;">
			<tr>
				<td align="center">
				<center>
					<html:button property="method" styleClass="rounded" value="Submit Confirmation" onclick="submitfeedback()" style="align:right;width:100px;"/>
					<html:button property="method" styleClass="rounded" value="Close" onclick="history.back(-1)" style="align:right;width:100px;"/>
				</center>
				</td>
			</tr>
		</table>
	</logic:notEmpty>	
  		
  		
  		 <logic:notEmpty name="appList">
	<div>&nbsp;</div>
    <table class="bordered" style="position: relative;left: 2%;width: 80%;">
    <tr>
    <th colspan="6"><center>History Status</center></th>
    </tr>
    <tr><th>Employee Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th><th>Role</th></tr> 
   	<logic:iterate id="abc" name="appList">
	<tr>
	<td>${abc.approver }</td>
	<td>${abc.designation }</td>
	<td>${abc.approveStatus }</td>
	<td>${abc.approveDate }</td>
	<td>${abc.comments }</td>
	<td>${abc.role }</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
  		   
	<html:hidden property="requestNumber" /></div>
	</html:form>			
</body>
</html>
