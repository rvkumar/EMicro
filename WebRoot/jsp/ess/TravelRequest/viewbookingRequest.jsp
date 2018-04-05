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
    
    <title>My JSP 'domestic.jsp' starting page</title>
    
	
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



function bookrequest(){


 	var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	document.forms[0].action="travelrequest.do?method=submitBookingDetails";
	document.forms[0].submit();
	}
	
function closerequest(){

	
 
	document.forms[0].action="travelrequest.do?method=displayupdateBookingDetails";
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

<script>

 function change()
{

$('#defaultOpen').click();

}

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

</script>

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
  </head>
  
  <body onload="change()">
  

  
  	<html:form action="travelrequest" enctype="multipart/form-data" method="post" >
  		<div id="masterdiv" class="">
  		<logic:notEmpty name="travel">
		<logic:iterate id="abc" name="travel">
		<ul class="tab" style="width: 890px; margin-left: 22px" >
 <li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'Recruitment')" id="defaultOpen">Requester Detail&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
  <li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R1')">Traveller Detail&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
  <li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R2')">Traveller List&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
    <li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R3')">Monthly Tour Plan </a></li>
 
</ul>


<div id="Recruitment" class="tabcontent" style="width: 850px; margin-left: 22px" >
		
   	<table class="bordered" style="position: relative;left: 2%;width: 80%;">
	 
	 <tr><th  colspan="4" align="center"><center> Travel Request Form </center></th></tr>
	 
<tr>
	 <td  colspan="1"> Request No</td>
	<td>
	${abc.requestNumber}
	</td>	
	<td colspan="1"> Request Date </td>
	<td>
	 
	  ${abc.reqDate}
	 	
		
				
	</td>
	 </tr>
	 
	 <tr>
	 <td  colspan="1"> Travel Mode</td>
	<td>
	<div class="no">
	${abc.travelmode}
	
	</div>	
	</td>	
	<td colspan="1"> Travel Type </td>
	<td>
	 <div class="no">
	 ${abc.traveltype}
	 
	 </div>	
		
				
	</td>
	 </tr>
	  <tr>  		 
	
			</tr>	
			 <tr>
			 <th colspan="4">Requester Details</th></tr>
			 <tr ><td> Employee No.<font color="red" size="3">*</font></td><td><bean:write name="abc" property="employeeno" /></td><td>  Employee Name<font color="red" size="3">*</font></td><td><bean:write name="abc" property="employeeName" /></td></tr>
			 <tr ><td> Department<font color="red" size="3">*</font></td><td><bean:write name="abc" property="department" /></td><td> Designation<font color="red" size="3">*</font></td><td><bean:write name="abc" property="designation" /></td></tr>
			 <tr><td> Gender<font color="red" size="3">*</font></td><td>${abc.userGender}</td><td> Age<font color="red" size="3">*</font></td><td>${abc.userAge}</td></tr>
			 <tr><td> Contact no.<font color="red" size="3">*</font></td><td>${abc.usermobno}&nbsp;&nbsp; </td>
			 <td>Official Email id<br/>Personal Email Id <font color="red" size="3">*</font></td><td>
			 ${abc.usermailId}<br/>${abc.userPersonalmailId}</td></tr>
			 <tr><td> Passport No:<font color="red" size="3">*</font></td><td>${abc.userpassportno}</td><td> Place of Issue:<font color="red" size="3">*</font></td><td>${abc.userpassportplace}</td></tr>
			 <tr><td> Date of issue:<font color="red" size="3">*</font></td><td>${abc.userpassportissuedate}</td><td>Date of expiry:<font color="red" size="3">*</font></td><td>${abc.userpassportexpirydate}</td></tr>
		
			  <tr style="display:none; " id="passport1">
						  <td>Passport No:<font color="red" size="3">*</font></td>
						  <td colspan="1">
							${abc.passportno}			  
		                  </td>
		                  <td>Place of Issue:<font color="red" size="3">*</font></td>
						  <td colspan="1">
						  							${abc.passportplace}
						  </td>							
		                  
		                  </tr>
						
						 <tr style="display:none; " id="passport2">
						  <td>Date of issue:<font color="red" size="3">*</font></td>
						  <td>${abc.passportissuedate}</td>
		                  
		                  <td>Date of expiry:<font color="red" size="3">*</font></td>
		                  <td>${abc.passportexpirydate}</td>
		                  
		                  </tr>
		                 </table><br/>
		                 
		                   <logic:notEmpty name="tra" >
						 <logic:iterate id="abc6" name="tra">
			<table class="bordered" style="position: relative;left: 2%;width: 90%;">
			<tr><th colspan="6">Other Details</th></tr>
			
			<tr>
			 
					<td>Accomodation: <font color="red" size="3">*</font></td>
					<td>
					${abc6.hotel_Res}
					
					</td>
					<td>Accomodation Type: </td>
					<td>
					${abc6.accom_type}
					</td>
					
					<td>Rental Car required: <font color="red" size="3">*</font></td>
					<td>
					${abc6.rent_Car}
					</td>

				</tr>
				
				<tr>
					<td>Guest House Name: </td>
					<td>
					${abc6.accom_name}
					</td>
					<td>Hotel Name: </td>
					<td>
					${abc6.hotel_Name}
					</td>
					<td>Hotel City: </td>
					<td>
					${abc6.hotel_City}
					</td>
				</tr>
				
				<tr>
					<td>Pickup Details: </td>
					<td colspan="2">
					${abc6.pickup_Details}
					</td>
					<td>Drop Details: </td>
					<td colspan="2">
					${abc6.drop_Details}
					</td>
				</tr>
				
				</table>
				</logic:iterate></logic:notEmpty>
</div>

<div id="R1" class="tabcontent" style="width: 890px; margin-left: 22px" >
       
		                  
		               <table class="bordered" style="position: relative;left: 2%;width: 80%; ">
<tr>
<th colspan="8">
Travel Requisition 
</th>
</tr>
<tr>
<td>
<b>Mode:</b>

</td>
<td>
${abc.travelmode}
</td>
<td>
<b>Type:</b>

</td>
<td>
${abc.traveltype}
</td>
<td>
<b>Travel Desk:</b>

</td>
<td>
${abc.travel_desk_type}
</td>
<td>
<b>Service Class:</b>

</td>
<td>
${abc.service_class}
</td>
</tr>
</table>
       
<table  class="bordered" style="position: relative;left: 2%;width: 90%; ">
<tr>
					<td  align="right">Request For: <font color="red" size="3">*</font></td>
					<td colspan="2">
					${abc.travelRequestFor}
					</td>
					
					 
					<td>Travel For: <font color="red" size="3">*</font> &nbsp;
					${abc.travelFor}
					
					</td>
					<td colspan="4">No of Travellers:
					Adult: ${abc.travel_Adult} &nbsp;
					Children: ${abc.travel_Child}   &nbsp; 
					Infant: ${abc.travel_Infant} 
					</td>
			
				</tr>
				<tr>
					<td>Origin: <font color="red" size="3">*</font></td>
					<td >
					${abc.fromPlace}
					</td>
					<td>Departure Date: <font color="red" size="3">*</font></td>
					<td>
					${abc.departOn}
					</td>&nbsp;&nbsp;
		            <td>Preferred Time: <font color="red" size="3">*</font></td>
		            <td>
		            ${abc.departTime}
		            </td>
				</tr>	
		        <tr id="destDisplay" >
					<td>Destination: <font color="red" size="3">*</font></td>
					<td >
					${abc.toPlace}
					</td>
					<td>Return On: <font color="red" size="3">*</font></td>
					<td>
					${abc.returnOn}
					</td>
					<td>Preferred Time: <font color="red" size="3">*</font></td>
					<td>
					${abc.returnTime}
					</td>
				</tr>

				<tr>
					<td>Purpose: <font color="red" size="3">*</font> </td>
					<td colspan="2">
					${abc.purposetype}	
					</td>
					<%-- <td>Purpose Detail <font color="red" size="3">*</font></td>	
					<td colspan="">
					${abc.purposetext}
					 </td> --%>
					
					<td>Location: <font color="red" size="3">*</font></td>
					<td colspan="2">
					${abc.locid}
					</td>
				
				</tr>
		        
		        <tr>
					<td>Sponsoring Division: </td>
					<td> 
					${abc.spon_div}
					</td>
					<td>Budget Code: </td>
					<td>
					${abc.bud_code}
					</td>
					<td>Estimated Trip Cost: </td>
					<td colspan="2">
					${abc.est_trip_cose}
					</td>
				</tr>
				<tr>
					<td>Trip Advance required: <font color="red" size="3" >*</font></td>
					<td>
					${abc.trip_Advance}
					</td>
					
					<td>Amount:</br>
						Currency:
					</td>
					<td colspan="4">
					${abc.trip_Amt}
					&nbsp;
					&nbsp;
					${abc.trip_Currency}
					</td>
					
					
					</tr>
					<tr>
					<td>Attachment: </td>
					
					<td colspan="3">
					
					<a href="${abc.filepath}" target="_blank">Passport View</a>  
					</td>
					<td>
					Travel Request PDF File
					</td>
					<td colspan="3">
					<a href="${abc.fileFullPath}" target="_blank">View</a>
					</td>
					</tr>
					
			
			<tr style="display: none">
					<td>From Date:</td>
					<td>
					${abc.trip_From_Date}
					</td>
					<td>From Time: </td>
					<td>
					${abc.trip_From_Time}
					</td>
					</tr>
					<tr style="display: none">
					<td>To Date: </td>
					<td>
					${abc.trip_To_Date}
					</td>
					<td>To Time: </td>
					<td>
					${abc.trip_To_time}
					</td>
					</tr>	
			</table>
			
						<br/>


			<logic:notEmpty name="emplist" >
						 <logic:iterate id="abc5" name="emplist">
			<table class="bordered" style="position: relative;left: 2%;width: 90%;">
			<tr><th colspan="3">Other Details</th><th colspan="3">Guest Name:	${abc5.guestName}</th></tr>
			
			<tr>
			 
					<td>Accomodation: <font color="red" size="3">*</font></td>
					<td>
					${abc5.hotel_Res}
					
					</td>
					<td>Accomodation Type: </td>
					<td>
					${abc5.accom_type}
					</td>
					
					<td>Rental Car required: <font color="red" size="3">*</font></td>
					<td>
					${abc5.rent_Car}
					</td>

				</tr>
				
				<tr>
					<td>Guest House Name: </td>
					<td>
					${abc5.accom_name}
					</td>
					<td>Hotel Name: </td>
					<td>
					${abc5.hotel_Name}
					</td>
					<td>Hotel City: </td>
					<td>
					${abc5.hotel_City}
					</td>
				</tr>
				
				<tr>
					<td>Pickup Details: </td>
					<td colspan="2">
					${abc5.pickup_Details}
					</td>
					<td>Drop Details: </td>
					<td colspan="2">
					${abc5.drop_Details}
					</td>
				</tr>
				
				</table>
				</logic:iterate></logic:notEmpty>          

</div>


<div id="R2" class="tabcontent" style="width: 890px; margin-left: 22px" >

						<table class="bordered" style="position: relative;left: 2%;width: 80%;" id="mcity">
						<tr><th colspan="10">Multiple City List </th></tr>	
						<tr>
							<th>${abc5.guest_Type} Name</th>
						<th>Location</th><th>${abc.travelmode} Preference</th><th>Arrival date</th> 
						<th>Arrival Time</th><th>Departure Date</th><th>Deaprture Time</th>
						 </tr>
						 <logic:notEmpty name="city" >
						 <logic:iterate id="abc2" name="city">
						 <tr>
						  <td>${abc2.guestName }</td>
						<td> ${abc2.locid }</td>
						<td> ${abc2.airline_Pref }</td>
						<td> ${abc2.trip_From_Date}</td>
						<td> ${abc2.trip_To_time}</td>
						<td> ${abc2.trip_To_Date}</td>
						<td> ${abc2.trip_To_time}</td>
						 
						 </tr>
						 
						 </logic:iterate>
						 </logic:notEmpty>
						</table>
						
						<br/>
						
						<logic:notEmpty name="city" >
						 <logic:iterate id="abc7" name="city">
			<table class="bordered" style="position: relative;left: 2%;width: 90%;">
			<tr><th colspan="3">Other Details</th><th colspan="3">Guest Name: ${abc7.guestName}</th></tr>
			
			<tr>
			 
					<td>Accomodation: <font color="red" size="3">*</font></td>
					<td>
					${abc7.hotel_Res}
					
					</td>
					<td>Accomodation Type: </td>
					<td>
					${abc7.accom_type}
					</td>
					
					<td>Rental Car required: <font color="red" size="3">*</font></td>
					<td>
					${abc7.rent_Car}
					</td>

				</tr>
				
				<tr>
					<td>Guest House Name: </td>
					<td>
					${abc7.accom_name}
					</td>
					<td>Hotel Name: </td>
					<td>
					${abc7.hotel_Name}
					</td>
					<td>Hotel City: </td>
					<td>
					${abc7.hotel_City}
					</td>
				</tr>
				
				<tr>
					<td>Pickup Details: </td>
					<td colspan="2">
					${abc7.pickup_Details}
					</td>
					<td>Drop Details: </td>
					<td colspan="2">
					${abc7.drop_Details}
					</td>
				</tr>
				
				</table>
				</logic:iterate></logic:notEmpty>
				<br/>
				
						
				
			<logic:equal value="Air" name="abc" property="travelmode">
						
						
						<table class="bordered" style="position: relative;left: 2%;width: 80%;">
						<tr>
						<th colspan="16">Traveller List</th>
						</tr>
						<%-- <tr>
						<th>Traveller Type</th> <th>Employee Number</th>
						<th>Tittle</th> <th>Name</th>
						<th>Gender</th> <th>Email</th>
						<th>Contact Number</th> <th>Company Name</th>
						<th>Passport Number</th> <th>Passport Expiry Date</th>
						<th>Visa Number & Date</th> <th>Date Of Birth/Age</th>
						<th>Meal Preference</th><th>Attachment</th>
						<th>Action</th>
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
						<td>${abc1.passportno}</td>
						<td>${abc1.passportexpirydate}</td>
						<td>${abc1.guest_Visano}</td>
						<td>${abc1.guest_DOB}</td>
						<td>${abc1.guest_Meal}</td>
						<td><a href="${abc1.fileFullPath}"  target="_blank">${abc1.fileName}</a></td>
						<td>
						<img src='images/delete.png'   onclick="deleteTraveller(${abc1.id})" title='Remove Row'/>
						</td>
						</tr>
						
						
						
						</logic:iterate>
						
						</logic:notEmpty> --%>
						
					<tr>
						<th>Traveller Type</th> <!-- <th>Employee Number</th> -->
						<!-- <th>Tittle</th> --> <th>Name</th>
						<th>Origin</th>
						<th>Depart On</th>
						<th>Destination</th>
						<th>Return On</th>
						<!-- <th>Gender</th> --> <th>Email</th>
						<th>Contact Number</th> <!-- <th>Company Name</th> -->
						<th>Passport Number</th> <th>Passport Expiry Date</th>
						<th>Visa Number & Date</th> <!-- <th>Date Of Birth/Age</th> -->
						<!-- <th>Meal Preference</th> --><th>Attachment</th>
						<th>Action</th>
						</tr>
						<logic:notEmpty name="emplist">
						<logic:iterate id="abc1" name="emplist">
						<tr>
						<td>${abc1.guest_Type}</td>
						<%-- <td>${abc1.guest_pernr}</td>
						<td>${abc1.guest_Title}</td> --%>
						<td>${abc1.guestName}</td>
						<td>${abc1.fromPlace}</td>
						<td>${abc1.departOn}</td>
						<td>${abc1.toPlace}</td>
						<td>${abc1.returnOn}</td>
						
						
						<%-- <td>${abc1.gender}</td> --%>
						<td>${abc1.email_Guest}</td>
						<td>${abc1.guestContactNo}</td>
						<%-- <td>${abc1.guest_Company}</td> --%>
						<td>${abc1.passportno}</td>
						<td>${abc1.passportexpirydate}</td>
						<td>${abc1.guest_Visano}</td>
						<%-- <td>${abc1.guest_DOB}</td> --%>
					<%-- 	<td>${abc1.guest_Meal}</td> --%>
						<td><a href="${abc1.fileFullPath}"  target="_blank">${abc1.fileName}</a></td>
						<td>
						<img src='images/delete.png'   onclick="deleteTraveller(${abc1.id})" title='Remove Row'/>
						</td>
						</tr>
						
						
						
						</logic:iterate>
						
						</logic:notEmpty>
					
						</table>

						</logic:equal>
		<!-- // Road -->				
						
						<logic:equal value="Road" name="abc" property="travelmode">
						
						
							<table class="bordered" style="position: relative;left: 2%;width: 80%;">
						<tr>
						<th colspan="16">Traveller List</th>
						</tr>
						<tr>
						<th>Traveller Type</th> <th>Employee Number</th>
						<th>Title</th> <th>Name</th>
						<th>Gender</th> <th>Email</th>
						<th>Contact Number</th> <th>Company Name</th>
						 <th>Date Of Birth/Age</th>
						 <th>Attachment</th>
						<th>Action</th>
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
						<td><a href="${abc1.fileFullPath}"  target="_blank">${abc1.fileName}</a></td>
						<td>
						<img src='images/delete.png'   onclick="deleteTraveller(${abc1.id})" title='Remove Row'/>
						</td>
						</tr>
						</logic:iterate>
						</logic:notEmpty>
						</table>
						</logic:equal>
						
						
						<!-- //Rail -->	
		
						<logic:equal value="Rail" name="abc" property="travelmode">
						
						
						
							<table class="bordered" style="position: relative;left: 2%;width: 80%;">
						<tr>
						<th colspan="16">Traveller List</th>
						</tr>
						<tr>
						<th>Traveller Type</th> <th>Employee Number</th>
						<th>Title</th> <th>Name</th>
						<th>Gender</th> <th>Email</th>
						<th>Contact Number</th> <th>Company Name</th>
						 <th>Date Of Birth/Age</th>
						 <th>Attachment</th>
						<th>Action</th>
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
						<td><a href="${abc1.fileFullPath}"  target="_blank">${abc1.fileName}</a></td>
						<td>
						<img src='images/delete.png'   onclick="deleteTraveller(${abc1.id})" title='Remove Row'/>
						</td>
						</tr>
						</logic:iterate>
						</logic:notEmpty>
						</table>
						</logic:equal>			
 
</div>

<div id="R3" class="tabcontent" style="width: 890px; margin-left: 22px" > 
		<table class="bordered" style="position: relative;left: 2%;width: 80%;" id="mcity1">
				<tr><th colspan="8">Monthly Travel Plan </th></tr>
				<tr>
					<th>Date</th><th>Origin</th><th> Destination</th><th>TravelMode</th> 
					<th>TravelType</th><th>Remarks</th>
					
				</tr>
				<logic:notEmpty name="appList1"> 
				<logic:iterate id="abcp4" name="appList1">
				<tr>
<td>${abcp4.fromdate}<input name="mdate" value="${abcp4.fromdate}" type="hidden"></td>
<td>${abcp4.origin}<input name="morigin" value="${abcp4.origin}" type="hidden"></td>
<td>${abcp4.departure}<input name="mdestination" value="${abcp4.departure}" type="hidden">
<td>${abcp4.travelmode}<input name="mode" value="${abcp4.travelmode}" type="hidden"></td>
<td>${abcp4.traveltype}<input name="mtype" value="${abcp4.traveltype}" type="hidden"></td>
<td>${abcp4.remarks}<input name="mremarks" value="${abcp4.remarks}" type="hidden">

 </tr>

				
</logic:iterate>
	</logic:notEmpty>
				
			</table>
	</div>

						
								<%-- <logic:notEmpty name="appList">
	<div>&nbsp;</div>
    <table class="bordered" style="position: relative;left: 2%;width: 80%;">
    <tr>
    <th colspan="5"><center>Approval Status</center></th>
    </tr>
    <tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="a" name="appList">
	<tr>
	<td>${a.approver }</td>
	<td>${a.designation }</td>
	<td>${a.approveStatus }</td>
	<td>${a.approveDate }</td>
	<td>${a.comments }</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>		 --%>
						
		</br>
		<iframe src="travelrequest.do?method=billerList" name="contentPage1" width="100%" height="400PX"
				frameborder="0" scrolling="yes" id="the_iframe"  frameborder="1" style="margin-left: 0px">
        	</iframe> 
		
					</logic:iterate>	
							</logic:notEmpty>
					<div  style="margin-left: 300px">		
					<table>
						<tr>
						<td>
						<logic:equal value="" property="bookingstatus" name="abc">
							  <html:button property="method" styleClass="rounded" value="Update Booking" onclick="bookrequest()" style="align:right;width:100px;"/> 
							</logic:equal>
					    <html:button property="method" styleClass="rounded" value="Close" onclick="closerequest()" style="align:right;width:100px;"/>
						</td>
						</tr>
						
						</table>
					</div>	
					
								<!-- <iframe src="travelrequest.do?method=billerList" name="contentPage1" width="100%" height="600PX"
				frameborder="0" scrolling="no" id="the_iframe"  frameborder="0" style="margin-left: 0px">
        	</iframe> 
			
							</br> -->
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
		
							
	</div>
  			</html:form>			
  </body>
</html>
