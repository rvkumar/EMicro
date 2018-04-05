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

 <!--  <style type="text/css">
@import "jquery.timeentry.css";
</style> -->
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



$(function() {
	$('#popupDatepicker4').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker4').datepick({onSelect: showDate});
});


$(function() {
	$('#popupDatepicker5').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker5').datepick({onSelect: showDate});
});


$(function() {
	$('#popupDatepicker6').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker6').datepick({onSelect: showDate});
});


/* $(function() {
	$('#marrvdate').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker7').datepick({onSelect: showDate});
	
	
});


$(function() {
	$('#mdeptdate').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker8').datepick({onSelect: showDate});
	
	
});
 */

/* $(function() {
	$('#trip_From_Date').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker8').datepick({onSelect: showDate});
	
	
}); */ 



/* $(function() {
	$('#trip_To_Date').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker8').datepick({onSelect: showDate});
	
	
}); */

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>
<script type="text/javascript">

 $(function () {
$('#marrtime').timeEntry();
});


$(function () {
$('#mdepttime').timeEntry();
});
 
$(function () {
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

function chkview()
{
	var x=document.forms[0].travelFor.value;
	var y=document.forms[0].usertype.value;

	if(x=="Self")
		{

		document.getElementById("guestname1").style.display="none";
		document.getElementById("guestname2").style.display="none";
		document.getElementById("multiple1").style.display="none";
		document.getElementById("multiple2").style.display="none";
		document.getElementById("travel1").style.display="none";
		document.getElementById("guestlist").style.display="none";
		document.getElementById("travel2").style.display="none";
		document.getElementById("usertyp1").style.display="none";
		document.getElementById("addtraveller").style.display="none";
		document.getElementById("addguest").style.display="none";
		}
	if(x=="Guest")
		{

		document.getElementById("guestname1").style.display="";
		document.getElementById("guestname2").style.display="";
		document.getElementById("multiple1").style.display="none";
		document.getElementById("multiple2").style.display="none";
		document.getElementById("travel1").style.display="";
		document.getElementById("travel2").style.display="";
		document.getElementById("guestlist").style.display="";
		document.getElementById("usertyp1").style.display="";
		if(y=="Employee")
		{
			document.getElementById("multiple1").style.display="";
			document.getElementById("multiple2").style.display="";
			
			document.getElementById("guestname1").style.display="none";
			document.getElementById("guestname2").style.display="none";
			document.getElementById("addtraveller").style.display="";
			document.getElementById("addguest").style.display="none";
		}
		if(y=="Guest")
		{
			document.getElementById("guestname1").style.display="";
			document.getElementById("guestname2").style.display="";
			document.getElementById("multiple1").style.display="none";
			document.getElementById("multiple2").style.display="none";
			document.getElementById("addtraveller").style.display="none";
			document.getElementById("addguest").style.display="";
		}
		if(y=="")
		{
		document.getElementById("addtraveller").style.display="none";
		document.getElementById("addguest").style.display="none";
		}
		}
	if(x=="Multiple")
	{

		
		document.getElementById("multiple1").style.display="";
		document.getElementById("multiple2").style.display="";
		
		document.getElementById("guestname1").style.display="none";
		document.getElementById("guestname2").style.display="none";
		document.getElementById("guestlist").style.display="";
		document.getElementById("travel1").style.display="";
		document.getElementById("travel2").style.display="";
		document.getElementById("usertyp1").style.display="";
		
		if(y=="Employee")
		{
			document.getElementById("multiple1").style.display="";
			document.getElementById("multiple2").style.display="";
			
			document.getElementById("guestname1").style.display="none";
			document.getElementById("guestname2").style.display="none";
			document.getElementById("addtraveller").style.display="";
			document.getElementById("addguest").style.display="none";
		}
		if(y=="Guest")
		{
			document.getElementById("guestname1").style.display="";
			document.getElementById("guestname2").style.display="";
			document.getElementById("multiple1").style.display="none";
			document.getElementById("multiple2").style.display="none";
			document.getElementById("addtraveller").style.display="none";
			document.getElementById("addguest").style.display="";
		}
		if(y=="")
		{
		document.getElementById("addtraveller").style.display="none";
		document.getElementById("addguest").style.display="none";
		}
	}
		
	if(x=="")
	{
		document.getElementById("multiple1").style.display="none";
		document.getElementById("multiple2").style.display="none";
		document.getElementById("guestname1").style.display="none";
		document.getElementById("guestname2").style.display="none";
		document.getElementById("travel1").style.display="none";
		document.getElementById("guestlist").style.display="none";
		document.getElementById("travel2").style.display="none";
		document.getElementById("usertyp1").style.display="none";
		document.getElementById("addtraveller").style.display="none";
		document.getElementById("addguest").style.display="none";
	}	
	
	
}

function chkguest()
{

var x=document.forms[0].usertype.value;


if(x=="Guest")
	{

	document.getElementById("guestname1").style.display="";
	document.getElementById("guestname2").style.display="";
	document.getElementById("multiple1").style.display="none";
	document.getElementById("multiple2").style.display="none";
	document.getElementById("addtraveller").style.display="none";
	document.getElementById("addguest").style.display="";
	}
if(x=="Employee")
{

	
	document.getElementById("multiple1").style.display="";
	document.getElementById("multiple2").style.display="";
	
	document.getElementById("guestname1").style.display="none";
	document.getElementById("guestname2").style.display="none";
	document.getElementById("addtraveller").style.display="";
	document.getElementById("addguest").style.display="none";
}
	


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

function statusMessage(message){
if(message!="")
{
alert(message);
}
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

	updatedate();
	checkingdates();
	
	
	if(document.forms[0].travelFor.value=="Guest" || document.forms[0].travelFor.value=="Multiple")
	{
	if(document.forms[0].travel_Adult.value=="" && document.forms[0].travel_Child.value=="" && document.forms[0].travel_Infant.value=="")
	{
	alert("Please Enter Total No of Travellers");
	return
	
	}
	
	}
	
	if(document.forms[0].service_class.value=="")
	{
	
	alert("Please Select Service class");
	document.forms[0].service_class.focus();
	$('#open2').click();
	return false;
	
	}
	
	if(document.forms[0].purposetype.value=="")
	{
	
	alert("Please Select Purpose ");
	document.forms[0].purposetype.focus();
	$('#open2').click();
	return false;
	
	}
	
	
	if(document.forms[0].purposetype.value=="Plant Visit")
	{
		if(document.forms[0].locid.value=="")
		{
		alert("Please Select Plant visit Location");
		document.forms[0].locid.focus();
		$('#open2').click();
		return false;
		
		}
	
	}
	
	if(document.forms[0].travelRequestFor.value=="")
	{
	
	alert("Please Select Travel Request For ");
	document.forms[0].travelRequestFor.focus();
	$('#open2').click();
	return false;
	
	}
	
	if(document.forms[0].travelFor.value=="")
	{
	
	alert("Please Select Travel  For ");
	document.forms[0].travelFor.focus();
	$('#open2').click();
	return false;
	
	}
	
		if(document.forms[0].fromPlace.value=="")
	{
	
	alert("Please Enter Origin ");
	document.forms[0].travelFor.focus();
	$('#open2').click();
	return false;
	
	}
	
		if(document.forms[0].departOn.value=="")
	{
	
	alert("Please Enter Trip Departure Date ");
	document.forms[0].departOn.focus();
	$('#open2').click();
	return false;
	
	}
	
		if(document.forms[0].departTime.value=="")
	{
	
	alert("Please Enter Prefurred Departure Time ");
	document.forms[0].departTime.focus();
	$('#open2').click();
	return false;
	
	}
	
	
		if(document.forms[0].toPlace.value=="")
	{
	
	alert("Please Enter Final Destination ");
	document.forms[0].toPlace.focus();
	$('#open2').click();
	return false;
	
	}
	/* 	if(document.forms[0].travelRequestFor.value=="Round Trip"|| document.forms[0].travelRequestFor.value=="Multi-City")
	{ */
		if(document.forms[0].returnOn.value=="")
	{
	
	alert("Please Enter Trip Return On ");
	document.forms[0].returnOn.focus();
	$('#open2').click();
	return false;
	
	}
	
	
		var start=$("#popupDatepicker").val();
	var end=$("#popupDatepicker2").val();

    var str1=start;
	var str2=end;
		   
		var dt1  = parseInt(str1.substring(0,2),10); 
		var mon1 = parseInt(str1.substring(3,5),10); 
		var yr1  = parseInt(str1.substring(6,10),10); 
		var dt2  = parseInt(str2.substring(0,2),10); 
		var mon2 = parseInt(str2.substring(3,5),10); 
		var yr2  = parseInt(str2.substring(6,10),10); 
		var date1 = new Date(yr1, mon1-1, dt1); 
		var date2 = new Date(yr2, mon2-1, dt2);
		if(date1 > date2)
		{
		alert("Please Select Valid Trip Start  & End Date");
		return false;
		
		}
		
		if(document.forms[0].returnTime.value=="")
		{
	
		alert("Please Enter Trip Return Time ");
		document.forms[0].returnTime.focus();
		$('#open2').click();
		return false;
	
		}
	
	
	if(document.forms[0].airline_Just.value=="")
		{
			alert("Please Enter Justification ");
			document.forms[0].airline_Just.focus();
			$('#open2').click();
			return false;
		
		}
	
	if(document.forms[0].travelFor.value=="Self" && document.forms[0].travelRequestFor.value!="Multi-City")
	{
	
	
	
	if(document.forms[0].hotel_Res.value=="")
	{
	
	alert("Please Select Hotel Reservation Required Or Not ");
	document.forms[0].hotel_Res.focus();
	$('#open4').click();
	return false;
	
	}
	
	
	if(document.forms[0].hotel_Res.value=="Yes")
	{
	
	if(document.forms[0].hotel_Name.value=="")
	{
	
	alert("Please Enter Hotel Name");
	document.forms[0].hotel_Name.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].hotel_City.value=="")
	{
	
	alert("Please Enter Hotel City");
	document.forms[0].hotel_City.focus();
	$('#open4').click();
	return false;
	
	}
	
	}
	
	
	if(document.forms[0].rent_Car.value=="")
	{
	alert("Please Select On Rental Car Required Or Not");
	document.forms[0].rent_Car.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].rent_Car.value=="Yes")
	{
	if(document.getElementById("pickup_Details").value =="")
	{
	alert("PLease Enter Pickup Details");
	return false;
	}
	if(document.getElementById("drop_Details").value =="")
	{
	alert("PLease Enter Drop Details");
	return false;
	}
	
	}
	}
	
	if(document.forms[0].onduty_Req.value=="")
	{
	alert("Please Select On Duty Required Or Not ");
	document.forms[0].onduty_Req.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].onduty_Req.value=="Yes")
	{
	
	if(document.forms[0].trip_From_Date.value=="")
	{
	
	alert("Please Enter Trip Start Date ");
	document.forms[0].trip_From_Date.focus();
	$('#open4').click();
	return false;
	
	}
	
	
	if(document.forms[0].trip_From_Time.value=="")
	{
	
	alert("Please Enter Trip Start Time ");
	document.forms[0].trip_From_Time.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].trip_To_Date.value=="")
	{
	
	alert("Please Enter Trip End Date ");
	document.forms[0].trip_To_Date.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].trip_To_time.value=="")
	{
	alert("Please Enter Trip End Time ");
	document.forms[0].trip_To_time.focus();
	$('#open4').click();
	return false;
	}
	
	}
	
	
	
	if(document.forms[0].trip_Advance.value=="")
	{
	
	alert("Please Select On Trip Advance  Required Or Not ");
	document.forms[0].trip_Advance.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].trip_Advance.value=="Yes")
	{
	
	if(document.getElementById("trip_Amt").value =="")
	{
	alert("PLease Enter Trip Amount");
	return false;
	}
	if(document.getElementById("trip_Currency").value =="")
	{
	alert("PLease Enter Currency");
	return false;
	}
	
	}
	
	
	var i =parseFloat(document.forms[0].travel_Adult.value)+parseFloat( document.forms[0].travel_Child.value)+ parseFloat(document.forms[0].travel_Infant.value);
	var j=document.getElementById("the_iframe").contentDocument.getElementById("test").value;
	
	if(document.forms[0].travelFor.value!=="Self")
	{
	if(j>0)
	{
	}
	else
	{
	alert("Please add Travellers");
	return false;
	}
	
	}
	
	if(i==j)
	{
	}
	else
	{
	alert("Traveller Must Match with added user list");
	return false;
	}
	
	
	
 	var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	document.forms[0].action="travelrequest.do?method=saveDomestic";
	document.forms[0].submit();
	}
	
function chngedomestic(value){

	var x=document.forms[0].reqType.value;	
	var y=document.forms[0].travelFor.value;
	
	if(value=="org")
	{
	if(y=="")
		{
		alert("Please select Travel For");
		document.forms[0].travelFor.focus();
		document.forms[0].reqType.value="";
		return false;
		
		}
	
	if(y=="Guest" || y=="Multiple")
{
	if(x=="Domestic")
		{
		document.getElementById("passport1").style.display="none";
		document.getElementById("passport2").style.display="none";
		}
	else
		{
		document.getElementById("passport1").style.display="";
		document.getElementById("passport2").style.display="";

		}
	}
	}
	
}
function closerequest(){

	
	 
	document.forms[0].action="travelrequest.do?method=displayMyrequestList";
	document.forms[0].submit();
	}
	
	
function addtraveler(value){

	var toadd = document.getElementById("employeeNumber1").value;
	var name = document.getElementById("empname1").value;
	var dep = document.getElementById("dept1").value;
	var desg = document.getElementById("desg1").value;
	
	var reqtype = document.forms[0].reqType.value;
	var usertype = document.forms[0].usertype.value;
	
	var passportno = document.forms[0].passportno.value;
	var passportplace = document.forms[0].passportplace.value;
	var passportissuedate = document.forms[0].passportissuedate.value;
	var passportexpirydate = document.forms[0].passportexpirydate.value;
	
	
	var guestname=document.forms[0].guestName.value;
	var guestage=document.forms[0].guestAge.value;
	var guestgender=document.forms[0].gender.value;
	var guestcontact=document.forms[0].guestContactNo.value;
	var guestemail=document.forms[0].guestmailId.value;
	
	if(reqtype == ""){
		alert("Please Select Request Type");
		document.forms[0].reqType.value.focus();		
		return false;
	}
	if(usertype == ""){
		alert("Please Select User Type");
		document.forms[0].usertype.value.focus();	
		return false;
	}
	
	if(value=="Add Employee")
	{
	if(toadd == ""){
		alert("Please enter Employee Number");
		document.getElementById("employeeNumber1").focus();		
		return false;
	}
		
	if(reqtype=="International")
	{
	
	if(passportno=="")
	{
	alert("Please enter Passport No.");
	document.forms[0].passportno.focus();		
	return false;
	
	}

if(passportplace=="")
{
alert("Please enter Passport Issued Place");
document.forms[0].passportplace.focus();		
return false;

}

if(passportissuedate=="")
{
alert("Please Select Passport Date of issue");
document.forms[0].passportissuedate.focus();		
return false;

}

if(passportexpirydate=="")
{
alert("Please Select Passport Date of expiry");
document.forms[0].passportexpirydate.focus();		
return false;

}
	
	
	}
	
		
	var table = document.getElementById('travel2');
	var row = table.insertRow(2);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);
    var cell7 = row.insertCell(6);
    var cell8 = row.insertCell(7);
    var cell9 = row.insertCell(8);
  
    
    cell1.innerHTML=toadd;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=toadd;
    element1.name="mulemps";
    cell1.appendChild(element1);
    
    cell2.innerHTML = name;
    cell3.innerHTML = dep;
    cell4.innerHTML = desg;
    
    
    cell5.innerHTML = passportno;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=passportno;
    element1.name="passnos";
    cell5.appendChild(element1);
    
    cell6.innerHTML = passportplace;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=passportplace;
    element1.name="passplac";
    cell6.appendChild(element1);
    
    cell7.innerHTML = passportissuedate;
    
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=passportissuedate;
    element1.name="passissue";
    cell7.appendChild(element1);
    
    
    cell8.innerHTML = passportexpirydate;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=passportexpirydate;
    element1.name="passexpiry";
    cell8.appendChild(element1);
    
    
    
    
    cell9.innerHTML="<img src='images/delete.png'   onclick='deleteRow(this,1)' title='Remove Row'/>";
	}
	else
		{
		
		
		if(guestname == ""){
			alert("Please enter GuestName");
			document.forms[0].guestName.focus();		
			return false;
		}
			
		
		if(guestage == ""){
			alert("Please enter Guest Age");
			document.forms[0].guestAge.focus();		
			return false;
		}
		
		if(guestgender == ""){
			alert("Please Select Guest Gender");
			document.forms[0].gender.focus();		
			return false;
		}
		
		if(guestcontact == ""){
			alert("Please enter  Guest Contact No.");
			document.forms[0].guestContactNo.focus();		
			return false;
		}
		
		
		if(guestemail == ""){
			alert("Please enter  Guest email Id");
			document.forms[0].guestmailId.focus();		
			return false;
		}
		
		if(reqtype=="International")
		{
		
		if(passportno=="")
		{
		alert("Please enter Passport No.");
		document.forms[0].passportno.focus();		
		return false;
		
		}

	if(passportplace=="")
	{
	alert("Please enter Passport Issued Place");
	document.forms[0].passportplace.focus();		
	return false;

	}

	if(passportissuedate=="")
	{
	alert("Please Select Passport Date of issue");
	document.forms[0].passportissuedate.focus();		
	return false;

	}

	if(passportexpirydate=="")
	{
	alert("Please Select Passport Date of expiry");
	document.forms[0].passportexpirydate.focus();		
	return false;

	}
		
		
		}
			
		var table = document.getElementById('guestlist');
		var row = table.insertRow(2);
	    var cell1 = row.insertCell(0);
	    var cell2 = row.insertCell(1);
	    var cell3 = row.insertCell(2);
	    var cell4 = row.insertCell(3);
	    var cell5 = row.insertCell(4);
	    var cell6 = row.insertCell(5);
	    var cell7 = row.insertCell(6);
	    var cell8 = row.insertCell(7);
	    var cell9 = row.insertCell(8);
	    var cell10 = row.insertCell(9);
	    
	    cell1.innerHTML=guestname;
	    
	    var element1= document.createElement("input");
	    element1.type="hidden";
	    element1.value=guestname;
	    element1.name="nameguests";
	    cell1.appendChild(element1);
	    
	    cell2.innerHTML = guestage;
	    
	    var element1= document.createElement("input");
	    element1.type="hidden";
	    element1.value=guestage;
	    element1.name="ageguests";
	    cell2.appendChild(element1);
	    
	    cell3.innerHTML = guestgender;
	    
	    var element1= document.createElement("input");
	    element1.type="hidden";
	    element1.value=guestgender;
	    element1.name="genderguests";
	    cell3.appendChild(element1);
	    
	    cell4.innerHTML = guestcontact;
	    
	    
	    var element1= document.createElement("input");
	    element1.type="hidden";
	    element1.value=guestcontact;
	    element1.name="contactguests";
	    cell4.appendChild(element1);
	    
	    
        cell5.innerHTML = guestemail;
	    
	    
	    var element1= document.createElement("input");
	    element1.type="hidden";
	    element1.value=guestemail;
	    element1.name="emailguests";
	    cell5.appendChild(element1);
	    
	    cell6.innerHTML = passportno;
	    
	    var element1= document.createElement("input");
	    element1.type="hidden";
	    element1.value=passportno;
	    element1.name="passnosguest";
	    cell6.appendChild(element1);
	    
	    cell7.innerHTML = passportplace;
	    
	    var element1= document.createElement("input");
	    element1.type="hidden";
	    element1.value=passportplace;
	    element1.name="passplacguest";
	    cell7.appendChild(element1);
	    
	    cell8.innerHTML = passportissuedate;
	    
	    
	    var element1= document.createElement("input");
	    element1.type="hidden";
	    element1.value=passportissuedate;
	    element1.name="passissueguest";
	    cell8.appendChild(element1);
	    
	    
	    cell9.innerHTML = passportexpirydate;
	    
	    var element1= document.createElement("input");
	    element1.type="hidden";
	    element1.value=passportexpirydate;
	    element1.name="passexpiryguest";
	    cell9.appendChild(element1);
	    cell10.innerHTML="<img src='images/delete.png'   onclick='deleteRow(this,2)' title='Remove Row'/>";
		
		}
    
    
	}
	
	
function deleteRow(element,value)
{
	 var table="";
	
	if(value==1)
		{
	  table = document.getElementById('travel2');
		}
	else
		{
		 table = document.getElementById('guestlist');
		}
	table.deleteRow(element.parentNode.parentNode.rowIndex);
	
	
    }
	
	
function searchEmployee(fieldName){
	var reqFieldName=fieldName;


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
	        
	        	document.getElementById("sU").style.display ="";
	        	document.getElementById("sU").className="overlay";
	        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
	        	
	       
	        	       			
	        }
	    }
	     xmlhttp.open("POST","leave.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
	    xmlhttp.send();
	}


	function selectUser(input,reqFieldName){
	
	var lastChar = reqFieldName.substr(reqFieldName.length - 1);


	var res = input.split("-");
		document.getElementById(reqFieldName).value=res[1];
		document.getElementById("empname"+lastChar).value=res[0];
		document.getElementById("dept"+lastChar).value=res[2];	
		
		if(res[4].contains('/'))
		{	
		document.getElementById("desg"+lastChar).value=res[3];
	
		}
		else
		{
		document.getElementById("desg"+lastChar).value=res[3]+"-"+res[4];
	
		}
		disableSearch(reqFieldName);
	}

	function disableSearch(reqFieldName){
	 
			if(document.getElementById("sU") != null){
			document.getElementById("sU").style.display="none";
			
		}
		}
		
function traveltypeddl()
{
var mode = $('#travelmode').val();
$('[name=traveltype]').hide();

//travel type
var mode1=mode+"type";
document.getElementById(mode1).style.display="";

//service class
var service="typeOfTravel"+mode;
$('[name=typeOfTravel]').hide();
document.getElementById(service).style.display="";

if(mode=="air")
{
document.getElementById("travellistair").style.display="";
document.getElementById("travellistroad").style.display="none";
}

if(mode!="air")
{
document.getElementById("travellistroad").style.display="";
document.getElementById("travellistair").style.display="none";
}
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
.overlay{
  position: relative;
  top: 5px;
  left: 20;
  width: 100%;
  height: 100%;
  z-index: 10;
 
}
.no
{
pointer-events:"none"; 
}

</style>
<script>
function addMultiplecity()
{

if($('#travelmode1').val()=="")
{
alert("Select Travel Mode");
$('#travelmode1').focus();
return false;
}


if($('#traveltype1').val()=="")
{
alert("Select Travel Type");
$('#traveltype1').focus();
return false;
}


if(document.getElementById("traveltype1").value=="International")
	{
		
		
	if(document.forms[0].mpassportno.value=="")
		{
		alert("Please Enter Passport Number");
		return false;
		}	
	
	if(document.forms[0].mpassportexpirydate.value=="")
	{
	alert("Please Enter Passport Expiry Date");
	return false;
	}
	
	
	
	}


if($('#mloc').val()=="")
{
alert("Select Location");
$('#mloc').focus();
return false;
}

if($('#mairp').val()=="")
{
alert("Select  Preference to Travel");
$('#mairp').focus();
return false;
}

if($('#morigin').val()=="")
{
alert("Select  Origin to Travel");
$('#morigin').focus();
return false;
}

if($('#mdeparture').val()=="")
{
alert("Select  Departure to Travel");
$('#mdeparture').focus();
return false;
}

if($('#marrvdate').val()=="")
{
alert("Select Arrival Date");
$('#marrvdate').focus();
return false;
}
if($('#marrtime').val()=="")
{
alert("Enter Arrival time");
$('#marrtime').focus();
return false;
}
if($('#mdeptdate').val()=="")
{
alert("Enter Departure Date");
$('#mdeptdate').focus();
return false;
}
if($('#mdepttime').val()=="")
{
alert("Enter Departure Time");
$('#mdepttime').focus();
return false;
}
if($('#mhotel_Res').val()=="")
{
alert("Select Accomodation Required or Not");
$('#mhotel_Res').focus();
return false;
}

if($('#mhotel_Res').val()=="Yes")
{
if($('#maccom_type').val()=="")
{
alert("Select Accomodation Type");
$('#maccom_type').focus();
return false;
}
}


if($('#maccom_type').val()=="Hotel")
{
if($('#mhotel_Name').val()=="")
{
alert("Enter Hotel Name ");
$('#mhotel_Name').focus();
return false;
}

if($('#mhotel_City').val()=="")
{
alert("Enter Hotel City ");
$('#mhotel_City').focus();
return false;
}

}


if($('#maccom_type').val()=="Guest House")
{
if($('#maccom_name').val()=="")
{
alert("Enter Guest  Name ");
$('#maccom_name').focus();
return false;
}

}

if($('#mrent_Car').val()=="")
{
alert("Please Select  Rental car Required or Not ");
$('#mrent_Car').focus();
return false;
}


if($('#mrent_Car').val()=="Yes")
{
if($('#mpickup_Details').val()=="")
{
alert("Enter Pickup Details ");
$('#mpickup_Details').focus();
return false;
}

if($('#mdrop_Details').val()=="")
{
alert("Enter Drop Details ");
$('#mdrop_Details').focus();
return false;
}

}



var table = document.getElementById('mcity');
	var row   = table.insertRow(2);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);
    var cell7 = row.insertCell(6);
    
    
    
    var cell8 = row.insertCell(7);
    var cell9 = row.insertCell(8);
    var cell10 = row.insertCell(9);
    var cell11 = row.insertCell(10);
    var cell12 = row.insertCell(11);
    var cell13 = row.insertCell(12);
    var cell14 = row.insertCell(13);
    var cell15 = row.insertCell(14);
    var cell16 = row.insertCell(15);
    var cell17 = row.insertCell(16);
    var cell18 = row.insertCell(17);
    var cell19 = row.insertCell(18);
    var cell20 = row.insertCell(19);
    var cell21 = row.insertCell(20);
    var cell22 = row.insertCell(21);
    var cell23 = row.insertCell(22);
    
    
    cell1.innerHTML=$('#userlistId').val();
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#userlistId').val();
    element1.name="userlistIdl";
    cell1.appendChild(element1);
    $('#userlistId').val("");
    
    
    
    cell2.innerHTML=$('#mloc').val();
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#mloc').val();
    element1.name="mlocl";
    cell2.appendChild(element1);
    $('#mloc').val("");
    
    
    cell3.innerHTML=$('#mairp').val();
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#mairp').val();
    element1.name="mairpl";
    cell3.appendChild(element1);
    $('#mairp').val("");
    
    
    cell4.innerHTML=$('#marrvdate').val();
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#marrvdate').val();
    element1.name="marrvdatel";
    cell4.appendChild(element1);
    $('#marrvdate').val("");
    
    cell5.innerHTML=$('#marrtime').val();
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#marrtime').val();
    element1.name="marrtimel";
    cell5.appendChild(element1);
    $('#marrtime').val("");
    
    cell6.innerHTML=$('#mdeptdate').val();
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#mdeptdate').val();
    element1.name="mdeptdatel";
    cell6.appendChild(element1);
    $('#mdeptdate').val("");
    
    
    cell7.innerHTML=$('#mdepttime').val();
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#mdepttime').val();
    element1.name="mdepttimel";
    cell7.appendChild(element1);
    $('#mdepttime').val("");
    
    
    
    
    /* cell8.innerHTML=$('#mhotel_Res').val(); */
    var element1= document.createElement("input");
    cell8.style.display="none";
    element1.type="hidden";
    element1.value=$('#mhotel_Res').val();
    element1.name="mhotel_Resl";
    cell8.appendChild(element1);
    $('#mhotel_Res').val("");
    
    
    /* cell9.innerHTML=$('#mrent_Car').val(); */
    var element1= document.createElement("input");
    cell9.style.display="none";
    element1.type="hidden";
    element1.value=$('#mrent_Car').val();
    element1.name="mrent_Carl";
    cell9.appendChild(element1);
    $('#mrent_Car').val("");
    
    
    
    
    /* cell10.innerHTML=$('#mhotel_Name').val(); */
    var element1= document.createElement("input");
    cell10.style.display="none";
    element1.type="hidden";
    element1.value=$('#mhotel_Name').val();
    element1.name="mhotel_Namel";
    cell10.appendChild(element1);
    $('#mhotel_Name').val("");
    
    
    /* cell11.innerHTML=$('#mhotel_City').val(); */
    var element1= document.createElement("input");
    cell11.style.display="none";
    element1.type="hidden";
    element1.value=$('#mhotel_City').val();
    element1.name="mhotel_Cityl";
    cell11.appendChild(element1);
    $('#mhotel_City').val("");
    
    
    /* cell12.innerHTML=$('#mpickup_Details').val(); */
    var element1= document.createElement("input");
    cell12.style.display="none";
    element1.type="hidden";
    element1.value=$('#mpickup_Details').val();
    element1.name="mpickup_Detailsl";
    cell12.appendChild(element1);
    $('#mpickup_Details').val("");
    
    
    /* cell13.innerHTML=$('#mdrop_Details').val(); */
    var element1= document.createElement("input");
    cell13.style.display="none";
    element1.type="hidden";
    element1.value=$('#mdrop_Details').val();
    element1.name="mdrop_Detailsl";
    cell13.appendChild(element1);
    $('#mdrop_Details').val("");
    
    
    var element1= document.createElement("input");
    cell14.style.display="none";
    element1.type="hidden";
    element1.value=$('#morigin').val();
    element1.name="moriginl";
    cell13.appendChild(element1);
    $('#morigin').val("");
    
    var element1= document.createElement("input");
    cell15.style.display="none";
    element1.type="hidden";
    element1.value=$('#mdeparture').val();
    element1.name="mdeparturel";
    cell15.appendChild(element1);
    $('#mdeparture').val("");
    
    
    var element1= document.createElement("input");
    cell16.style.display="none";
    element1.type="hidden";
    element1.value=$('#maccom_type').val();
    element1.name="maccom_typel";
    cell16.appendChild(element1);
    $('#maccom_type').val("");


	
    var element1= document.createElement("input");
    cell17.style.display="none";
    element1.type="hidden";
    element1.value=$('#maccom_name').val();
    element1.name="maccom_namel";
    cell17.appendChild(element1);
    $('#maccom_name').val("");    
    
    
    
    var element1= document.createElement("input");
    cell18.style.display="none";
    element1.type="hidden";
    element1.value=$('#travelmode1').val();
    element1.name="mtravelmodel";
    cell18.appendChild(element1);
    $('#travelmode1').val(""); 
    
    
    
    var element1= document.createElement("input");
    cell19.style.display="none";
    element1.type="hidden";
    element1.value=$('#traveltype1').val();
    element1.name="mtraveltypel";
    cell19.appendChild(element1);
    $('#traveltype1').val("");
    
    
    var element1= document.createElement("input");
    cell20.style.display="none";
    element1.type="hidden";
    element1.value=$('#mpassportno').val();
    element1.name="mpassportnol";
    cell20.appendChild(element1);
    $('#mpassportno').val(""); 
    
    
    var element1= document.createElement("input");
    cell21.style.display="none";
    element1.type="hidden";
    element1.value=$('#mpassportexpirydate').val();
    element1.name="mpassportexpirydatel";
    cell21.appendChild(element1);
    $('#mpassportexpirydate').val("");
    
    var element1= document.createElement("input");
    cell22.style.display="none";
    element1.type="hidden";
    element1.value=$('#mguest_Visano').val();
    element1.name="mguest_Visanol";
    cell22.appendChild(element1);
    $('#mpassportno').val("");
    
    
    cell23.innerHTML="<img src='images/delete.png' onclick='deleteRow1(this,1)' title='Delete'/>";
    
    
    maccodchange();

	originmuliticty();


}
function deleteRow1(element,value)
{

var table="";
	table = document.getElementById("mcity");
	table.deleteRow(element.parentNode.parentNode.rowIndex);
}


function openCity(evt, cityName) {


	 if(cityName=="R2"||cityName=="R4" )
    {
    
    updatedate();
    
    checkingdates();
    originmuliticty();
    if(document.forms[0].departOn.value=="")
    {
    alert("Please Select Trip Departure Date");
    return false;
    }
    
    
     if(document.forms[0].returnOn.value=="")
    {
    alert("Please Select Trip Return Date");
    return false;
    }
    
    if(document.forms[0].travelRequestFor.value=="")
    {
    alert("Please Select Travel Request For");
    return false;
    }
    
    }
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
    
    if(cityName=="R2")
    {
    
	document.getElementById("the_iframe").contentDocument.getElementById("travellist").click();     
    }
    
    if(cityName=="R4")
    {
	document.getElementById("userbtn").click();     
    }
    
    
}


function tabchange()
{



document.getElementById("triptype").value=document.forms[0].travelRequestFor.value;

 
 
 
 if(document.forms[0].travelRequestFor.value=="Multi-City")
{
document.getElementById("mcity1").style.display="";
document.getElementById("mcity2").style.display="";
document.getElementById("mcity3").style.display="";
}
else
{
document.getElementById("mcity1").style.display="none";
document.getElementById("mcity2").style.display="none";
document.getElementById("mcity3").style.display="none";

} 


if(document.forms[0].travelFor.value=="Self"|| document.forms[0].travelFor.value=="Multiple")
{
document.getElementById("self_Multiple").style.display="";
}
else
{
document.getElementById("self_Multiple").style.display="none";

}
	   

if(document.forms[0].travelFor.value=="Self")
{

	 document.getElementById("noOfTravel").className="no";
	 document.getElementById("travel_Adult").style.background="rgb(179,175,174)";
	 document.getElementById("travel_Child").style.background="rgb(179,175,174)";
	 document.getElementById("travel_Infant").style.background="rgb(179,175,174)";
     document.getElementById("travel_Adult").value="0";
	 document.getElementById("travel_Child").value="0";
	 document.getElementById("travel_Infant").value="0";
    
    
}
else
{
document.getElementById("noOfTravel").className="";
document.getElementById("travel_Adult").style.background="";
document.getElementById("travel_Child").style.background="";
document.getElementById("travel_Infant").style.background="";
}


if(document.forms[0].travelRequestFor.value=="Multi-City")
{
document.getElementById("open5").style.display="";
}
else
{
document.getElementById("open5").style.display="none";
}

if(document.forms[0].travelFor.value=="Guest" || document.forms[0].travelFor.value=="Multiple" || document.forms[0].travelFor.value=="On Behalf")
{
document.getElementById("open3").style.display="";

}
else
{
document.getElementById("open3").style.display="none";
}

if((document.forms[0].travelFor.value=="Self") && (document.forms[0].travelRequestFor.value=="Multi-City") )
{
document.getElementById("self_Multiple").style.display="none";
} 

if(document.forms[0].travelFor.value=="Multiple" && document.forms[0].travelRequestFor.value=="Multi-City" )
{
document.getElementById("self_Multiple").style.display="none";
} 

}


function trip()
{

if(document.forms[0].trip_Advance.value=="Yes")
{
document.getElementById("trip_Amt").style.background="";
document.getElementById("trip_Currency").style.background="";
document.getElementById("trip_Amt").readOnly =false;
document.getElementById("trip_Currency_div").className = "";
/* document.getElementById("trip_Currency").readOnly =false; */
}
else
{
document.getElementById("trip_Amt").style.background="rgb(179,175,174)";
document.getElementById("trip_Currency").style.background="rgb(179,175,174)";
document.getElementById("trip_Amt").readOnly =true;
document.getElementById("trip_Currency").readOnly =true;
document.getElementById("trip_Amt").value ="";
document.getElementById("trip_Currency_div").className = "no";
/* document.getElementById("trip_Currency").value =""; */
}

}


function carchange()
{

if(document.forms[0].rent_Car.value=="Yes")
{
document.getElementById("pickup_Details").style.background="";
document.getElementById("drop_Details").style.background="";
document.getElementById("pickup_Details").readOnly =false;
document.getElementById("drop_Details").readOnly =false;
}
else
{
document.getElementById("pickup_Details").style.background="rgb(179,175,174)";
document.getElementById("drop_Details").style.background="rgb(179,175,174)";
document.getElementById("pickup_Details").readOnly =true;
document.getElementById("drop_Details").readOnly =true;
document.getElementById("pickup_Details").value ="";
document.getElementById("drop_Details").value ="";
}


}


function hotelChange()
{

if(document.forms[0].hotel_Res.value=="Yes")
{
document.getElementById("hotel_Name").style.background="";
document.getElementById("hotel_City").style.background="";
document.getElementById("hotel_Name").readOnly =false;
document.getElementById("hotel_City").readOnly =false;
}
else
{
document.getElementById("hotel_Name").style.background="rgb(179,175,174)";
document.getElementById("hotel_City").style.background="rgb(179,175,174)";
document.getElementById("hotel_Name").readOnly =true;
document.getElementById("hotel_City").readOnly =true;
document.getElementById("hotel_Name").value ="";
document.getElementById("hotel_City").value ="";
}


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
    
    function accodchange()
{


if(document.forms[0].hotel_Res.value=="Yes")
{
document.getElementById("accom_type").style.background="";
document.getElementById("accom_type_div").className = "";
}
else
{
document.getElementById("accom_type").style.background="rgb(179,175,174)";
document.getElementById("accom_type_div").className = "no";
document.getElementById("accom_type").value ="";

document.getElementById("accom_name").style.background="rgb(179,175,174)";
document.getElementById("accom_name").readOnly =true;
document.getElementById("accom_name").value ="";


document.getElementById("hotel_Name").style.background="rgb(179,175,174)";
document.getElementById("hotel_City").style.background="rgb(179,175,174)";
document.getElementById("hotel_Name").readOnly =true;
document.getElementById("hotel_City").readOnly =true;
document.getElementById("hotel_Name").value ="";
document.getElementById("hotel_City").value ="";

}


}
    
    
function change()
{

tabchange();
carchange();
hotelChange();
/* chkguest(); */
 accodchange(); 
trip();
$('#defaultOpen').click();


$(form).find(':input').each(function() {
            switch(this.type) {
                case 'password':
                case 'select-multiple':
                case 'select-one':
                case 'text':
                case 'select':
                case 'textarea':
                    $(this).val('');
                    break;
                case 'checkbox':
                case 'radio':
                    this.checked = false;
            }
        });

}

function updatedate()
{

if(document.getElementById("onduty_Req").value=="Yes")
{

document.forms[0].trip_From_Date.value=document.forms[0].departOn.value;
document.forms[0].trip_From_Time.value=document.forms[0].departTime.value;
document.forms[0].trip_To_Date.value=document.forms[0].returnOn.value;
document.forms[0].trip_To_time.value=document.forms[0].returnTime.value;
}
else
{
document.forms[0].trip_From_Date.value="";
document.forms[0].trip_From_Time.value="";
document.forms[0].trip_To_Date.value="";
document.forms[0].trip_To_time.value="";


}

	var start=$("#popupDatepicker").val();
	var end=$("#popupDatepicker2").val();

	if(start!="" && end!="")
	{
	
	

    var str1=start;
	var str2=end;
	
			
			var dt1  = parseInt(str1.substring(0,2),10); 
		var mon1 = parseInt(str1.substring(3,5),10); 
		var yr1  = parseInt(str1.substring(6,10),10); 
		var dt2  = parseInt(str2.substring(0,2),10); 
		var mon2 = parseInt(str2.substring(3,5),10); 
		var yr2  = parseInt(str2.substring(6,10),10); 
		var date1 = new Date(yr1, mon1-1, dt1); 
		var date2 = new Date(yr2, mon2-1, dt2);
		
		
	var diffDays = parseInt((date2 - date1) / (1000 * 60 * 60 * 24)); 
	$("#travel_Days").val(diffDays);
	if(diffDays<0)
	{
	
	alert("Select Correct Travel  date");
 	document.forms[0].departOn.value="";
    document.forms[0].returnOn.value="";
    $("#travel_Days").val(0);
    $('#open2').click();
    return false;
	
	}
	
	
	
	} 

}


function ondutyupdate()
{

if(document.getElementById("onduty_Req").value=="Yes")
{
document.forms[0].trip_From_Date.value=document.forms[0].departOn.value;
document.forms[0].trip_From_Time.value=document.forms[0].departTime.value;
document.forms[0].trip_To_Date.value=document.forms[0].returnOn.value;
document.forms[0].trip_To_time.value=document.forms[0].returnTime.value;
}

else
{
document.forms[0].trip_From_Date.value="";
document.forms[0].trip_From_Time.value="";
document.forms[0].trip_To_Date.value="";
document.forms[0].trip_To_time.value="";


}
}

function mcarchange()
{

if(document.forms[0].mrent_Car.value=="Yes")
{
document.getElementById("mpickup_Details").style.background="";
document.getElementById("mdrop_Details").style.background="";
document.getElementById("mpickup_Details").readOnly =false;
document.getElementById("mdrop_Details").readOnly =false;
}
else
{
document.getElementById("mpickup_Details").style.background="rgb(179,175,174)";
document.getElementById("mdrop_Details").style.background="rgb(179,175,174)";
document.getElementById("mpickup_Details").readOnly =true;
document.getElementById("mdrop_Details").readOnly =true;
document.getElementById("mpickup_Details").value ="";
document.getElementById("mdrop_Details").value ="";
}


}


function mhotelChange()
{

if(document.forms[0].mhotel_Res.value=="Yes")
{
document.getElementById("mhotel_Name").style.background="";
document.getElementById("mhotel_City").style.background="";
document.getElementById("mhotel_Name").readOnly =false;
document.getElementById("mhotel_City").readOnly =false;
}
else
{
document.getElementById("mhotel_Name").style.background="rgb(179,175,174)";
document.getElementById("mhotel_City").style.background="rgb(179,175,174)";
document.getElementById("mhotel_Name").readOnly =true;
document.getElementById("mhotel_City").readOnly =true;
document.getElementById("mhotel_Name").value ="";
document.getElementById("mhotel_City").value ="";
}


}


function getUser()
{
 
var xmlhttp;
var dt;
dt=document.forms[0].id.value;
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
    document.getElementById("userlistId").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","travelrequest.do?method=gettravellerList&dt="+dt,true);
xmlhttp.send();
}



function checkingdates()
{
 
var xmlhttp;
var dt;
var dt2;
dt=document.forms[0].departOn.value;
dt2=document.forms[0].returnOn.value;
if(dt!=""&&dt2!="")
{
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
    document.getElementById("travel_Days1").innerHTML=xmlhttp.responseText;
    totalDays=document.forms[0].travel_Days1.value;
    if(totalDays=="0")
    {
    alert("Another Travel Exist in Perticular dates");
    document.forms[0].departOn.value="";
    document.forms[0].returnOn.value="";
    $('#open2').click();
    }
    }
  }
xmlhttp.open("POST","travelrequest.do?method=traveldate&dt="+dt+"&dt2="+dt2,true);
xmlhttp.send();
}
}

function datedisplay(id)
{	
	$('#'+id).datepick('destroy');
	var start=$("#popupDatepicker").val();
	
	var end=$("#popupDatepicker2").val();
	
	 var str1=start;
	var str2=end;
		   
		var dt1  = parseInt(str1.substring(0,2),10); 
		var mon1 = parseInt(str1.substring(3,5),10); 
		var yr1  = parseInt(str1.substring(6,10),10); 
		var dt2  = parseInt(str2.substring(0,2),10); 
		var mon2 = parseInt(str2.substring(3,5),10); 
		var yr2  = parseInt(str2.substring(6,10),10); 
		var date1 = new Date(yr1, mon1-1, dt1); 
		var date2 = new Date(yr2, mon2-1, dt2);
	
	$('#'+id).datepick({dateFormat: 'dd/mm/yyyy',minDate:date1,maxDate:date2});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
}


$(function() {
	$('#mpassportexpirydate').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});


function passdisplay()
{
	if(document.getElementById("traveltype1").value=="International")
	{
	document.getElementById("passportm1").style.display="";
	document.getElementById("passportm2").style.display="";
	}
	else
	{
	document.getElementById("passportm1").style.display="none";
	document.getElementById("passportm2").style.display="none";
	
	document.forms[0].mpassportno.value="";
	document.forms[0].mpassportexpirydate.value="";
	document.forms[0].mguest_Visano.value="";
	}
}


function originmuliticty()
{

var x = document.getElementById('mcity').rows.length;
var origin="";
if(x>2)
{
origin=document.getElementsByName("mdeparturel")[0].value;
}
else
{
origin=document.getElementById("fromPlace").value;
}
document.getElementById('morigin').value=origin;

}


</script>
  </head>
  
	<body onload="change()">
		<div align="center">
			<logic:notEmpty name="travelRequestForm" property="message">
				<script language="javascript">
					statusMessage('<bean:write name="travelRequestForm" property="message" />');
				</script>
			</logic:notEmpty>
		</div>
  
		<html:form action="travelrequest" enctype="multipart/form-data" method="post" >
		<html:hidden property="id"  name="travelRequestForm"/>
		<html:hidden property="travel_desk_type"  name="travelRequestForm"/>
		
		
		
		<input type="hidden" id="triptype" value=""/>
		
		<div style="display: none">
		<html:button property="method" styleClass="rounded" styleId="userbtn" value="userbtn" onclick="getUser()" />
		</div>
		<div id="masterdiv" class="">
			<ul class="tab" style="width: 1000px; margin-left: 22px" >
				<!-- <li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'Recruitment')" id="defaultOpen">Requester Detail&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li> -->
				<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R1')" id="open2">Trip Info&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
				<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R2')" id="open3" style="display: none ">Traveller List&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
				<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R4')" id="open5" style="display: none">Multi City List&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
				<!-- <li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R3')" id="open4">Other Details&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li> -->
			</ul>
	<div id="Recruitment" class="tabcontent" style="width: 850px; margin-left: 22px" >
				<table class="bordered" style="position: relative;left: 2%;width: 80%;">
					<tr><th colspan="4" align="center"><center> Travel Request Form </center></th></tr>
					<tr>
						<td colspan="1">Travel Mode: <font color="red" size="3">*</font></td>
						<td >

							<div class="no" >
								<input  name="travelmode" value="${travelRequestForm.travelmode}" style="background: rgb(179,175,174)"/>
							</div>	

						</td>	
						<td colspan="1">Travel Type: <font color="red" size="3">*</font> </td>
						<td >
							<div class="no">
								<input  name="traveltype" value="${travelRequestForm.traveltype}" style="background: rgb(179,175,174)"/>
							</div>	
						</td>
					</tr>
					<tr>
						<td> Request No: </td>
						<td><b>System Generated</b></td>	
						<td> Request Date: </td>
						<td>${travelRequestForm.reqDate}</td>
					</tr>
					<tr>
						<td>Employee No.: <font color="red" size="3">*</font></td>
						<td><bean:write name="travelRequestForm" property="employeeno" /></td>
						<td>Employee Name: <font color="red" size="3">*</font></td>
						<td><bean:write name="travelRequestForm" property="employeeName" /></td>
					</tr>
					<tr>
						<td>Department: <font color="red" size="3">*</font></td>
						<td><bean:write name="travelRequestForm" property="department" /></td>
						<td> Designation: <font color="red" size="3">*</font></td>
						<td><bean:write name="travelRequestForm" property="designation" /></td>
					</tr>
					<tr>
						<td>Gender: <font color="red" size="3">*</font></td><td>${travelRequestForm.userGender}</td>
						<td>Age: <font color="red" size="3">*</font></td><td>${travelRequestForm.userAge}</td>
					</tr>
					<tr>
						<td>Contact no.: <font color="red" size="3">*</font></td><td>${travelRequestForm.usermobno}&nbsp;&nbsp; </td>
						<td>Official Email id: <br/>Personal Email Id: <font color="red" size="3">*</font></td>
						<td>${travelRequestForm.usermailId}<br/>${travelRequestForm.userPersonalmailId}</td>
					</tr>
					<tr>
						<td>Passport No: <font color="red" size="3">*</font></td><td>${travelRequestForm.userpassportno}</td>
						<td>Place of Issue:<font color="red" size="3">*</font></td><td>${travelRequestForm.userpassportplace}</td>
					</tr>
					<tr>
						<td>Date of issue: <font color="red" size="3">*</font></td><td>${travelRequestForm.userpassportissuedate}</td>
						<td>Date of expiry: <font color="red" size="3">*</font></td><td>${travelRequestForm.userpassportexpirydate}</td>
					</tr>
					<tr style="display:none; " id="passport1">
						<td>Passport No: <font color="red" size="3">*</font></td>
						<td colspan="1"><html:text property="passportno"   size="40"  onkeyup="this.value = this.value.replace(/'/g,'`')" /></td>
						<td>Place of Issue: <font color="red" size="3">*</font></td>
						<td colspan="1"><html:text property="passportplace"   size="40"  onkeyup="this.value = this.value.replace(/'/g,'`')" /></td>
					</tr>
						
					<tr style="display:none; " id="passport2">
						<td>Date of issue: <font color="red" size="3">*</font></td>
						<td colspan="1"><html:text property="passportissuedate"   size="10"   styleId="popupDatepicker3" readonly="true" /></td>
						<td>Date of expiry: <font color="red" size="3">*</font></td>
						<td colspan="1"><html:text property="passportexpirydate"   size="10"  onkeyup="this.value = this.value.replace(/'/g,'`')" styleId="popupDatepicker4" readonly="true" /></td>
		            </tr>
		            <tr  >
							<td colspan="1">Travel Desk: <font color="red" size="3">*</font></td>
						<td colspan="3"><bean:write name="travelRequestForm" property="travel_desk_type" />  </td>
					</tr>
		            <tr style="display:none; " id="addtraveller">
						<td colspan="4">
							<center><html:button property="method" styleClass="rounded"  value="Add Employee" onclick="addtraveler(this.value);" style="align:right;width:100px;"/></center>
						</td>
					</tr>
		            <tr style="display:none; " id="addguest">
						<td colspan="4">
							<center> <html:button property="method" styleClass="rounded"  value="Add Guest" onclick="addtraveler(this.value);" style="align:right;width:100px;"/></center>
						</td>
					</tr>
		        </table>
			<br/>
			</div>

			<div id="R1" class="tabcontent" style="width: 890px; margin-left: 22px" >
				<table  class="bordered" style="position: relative;left: 2%;width: 80%; ">
				<tr><th colspan="4">Travel Details</th></tr>
				<tr>
					<td>Service Class: <font color="red" size="3">*</font></td>
					<td>
						<logic:equal value="Air" name="travelRequestForm" property="travelmode">
						<html:select name="travelRequestForm" property="service_class" styleClass="text_field" styleId="service_class" >
							<html:option value="">--Select--</html:option>
							<html:option value="Economy">Economy</html:option>
							<html:option value="Premium Economy">Premium Economy</html:option>
							<html:option value="Economy Plus">Economy Plus</html:option>
							<html:option value="First">First</html:option>
							<html:option value="Business">Business</html:option>
							<html:option value="None">None</html:option>
						</html:select>	
						</logic:equal>
		
						<logic:equal value="Road" name="travelRequestForm" property="travelmode">
						<html:select name="travelRequestForm" property="service_class" styleClass="text_field" styleId="service_class" >
							<html:option value="">--Select--</html:option>
							<html:option value="Volvo">Volvo</html:option>
							<html:option value="Multi Axel">Multi Axel</html:option>
							<html:option value="Semi Sleeper">Semi Sleeper</html:option>
							<html:option value="Sleeper">Sleeper</html:option>
							<html:option value="Express">Express</html:option>
							<html:option value="General">General</html:option>
							<html:option value="None"></html:option>
						</html:select>
						</logic:equal>
		
						<logic:equal value="Rail" name="travelRequestForm" property="travelmode">
						<html:select name="travelRequestForm" property="service_class" styleClass="text_field" styleId="service_class" >
							<html:option value="Economy">Economy</html:option>
							<html:option value="Premium Economy">Premium Economy</html:option>
							<html:option value="Economy Plus">Economy Plus</html:option>
							<html:option value="First">First</html:option>
							<html:option value="Business">Business</html:option>
							<html:option value="None">None</html:option>
						</html:select>
						</logic:equal>
					</td>
					<td>Request For: <font color="red" size="3">*</font></td>
					<td>
						<html:radio property="travelRequestFor" value="One Way" onclick="tabchange()" styleId="One_Way"></html:radio>One Way &nbsp;
						<html:radio property="travelRequestFor" value="Round Trip" onclick="tabchange()" styleId="Round_Trip"></html:radio>Round Trip &nbsp;
						<html:radio property="travelRequestFor" value="Multi-City" onclick="tabchange()" styleId="Multi_City"></html:radio>Multi-City &nbsp;
					</td>
				</tr>
				<tr>	
					<td>Purpose: <font color="red" size="3">*</font> </td>
					<td>
						<html:select name="travelRequestForm" property="purposetype" styleClass="text_field" >
						<html:option value="">--Select--</html:option>
						<html:option value="Sales Visit">Sales Visit</html:option>
						<html:option value="Customer Visit">Customer Visit</html:option>
						<html:option value="Plant Visit">Plant Visit</html:option>
						<html:option value="Multiple">Multiple</html:option>
						<html:option value="Vendor Meeting">Vendor Meeting</html:option>
						<html:option value="Conference">Conference</html:option>
						<html:option value="Training">Training</html:option>
						<html:option value="Teaching">Teaching</html:option>
						<html:option value="Internal Meeting">Internal Meeting</html:option>
						<html:option value="External Meeting">External Meeting</html:option>
						<html:option value="Other">Other</html:option>
						</html:select>
					</td>
					
					<td>Location</td>
					<td>
					
					
					<html:select  property="locid" name="travelRequestForm" >
			<html:option value="">--Select--</html:option>
			<html:options name="travelRequestForm"  property="locationIdList" labelProperty="locationLabelList"/>
			
		</html:select>
					
					</td>
					</tr>
					<tr>	
					<td>Purpose Detail</td>	
					<td colspan="3"> <textarea rows="4" cols="30" name="purposetext">${travelRequestForm.purposetext}</textarea></td>
				</tr>
				<tr>
					<td>Travel For: <font color="red" size="3">*</font></td>
					<td>
					
					<%-- <html:text property="travelFor" name="travelRequestForm"></html:text> --%>
					
						<html:select name="travelRequestForm" property="travelFor" styleClass="text_field" onchange="tabchange()" >
						<html:option value="Self">Self</html:option>
						<html:option value="On Behalf">On Behalf</html:option>
						<html:option value="Guest">Guest</html:option>
						<html:option value="Multiple">Multiple</html:option>
						</html:select>
						
			
					</td>	
					<td>No of Travellers</td>
					<td>
					<div class="no" id="noOfTravel">
					Adult: <input type="text" style="width: 30px;" name="travel_Adult" value="${travelRequestForm.travel_Adult}" onkeypress="return isNumber(event)" id="travel_Adult" maxlength="2"/> &nbsp;   
					Children: <input type="text" style="width: 30px" name="travel_Child" value="${travelRequestForm.travel_Child}"  onkeypress="return isNumber(event)" id="travel_Child" maxlength="2"/>  &nbsp; 
					Infant: <input type="text" style="width: 30px" name="travel_Infant" value="${travelRequestForm.travel_Infant}" onkeypress="return isNumber(event)" id="travel_Infant" maxlength="2"/>
					</div>
					</td>						
				</tr>
				<tr>
				<td>Contact Name</td>
				<td><input type="text" name="p_name" onkeyup="this.value = this.value.replace(/'/g,'`')" value="${travelRequestForm.p_name}" /> </td>
				<td>Contact Department</td>
				<td><input type="text" name="p_dept" onkeyup="this.value = this.value.replace(/'/g,'`')" value="${travelRequestForm.p_dept}" /> </td>
				</tr>
				
				
				<tr>
				<td>Contact No</td>
				<td><input type="text" name="p_cont" onkeyup="this.value = this.value.replace(/'/g,'`')" value="${travelRequestForm.p_cont}" /> </td>
				<td>Contact Email</td>
				<td><input type="text" name="p_email" onkeyup="this.value = this.value.replace(/'/g,'`')" value="${travelRequestForm.p_email}" /> </td>
				</tr>
				<tr>
					<td>Origin: <font color="red" size="3">*</font></td>
					<td colspan="3">
					<input type="text" name="fromPlace" onkeyup="this.value = this.value.replace(/'/g,'`')" size="70" value="${travelRequestForm.fromPlace}" id="fromPlace"/>
					</td>
					</tr>			
		        <tr>
					<td>Trip Departure Date: <font color="red" size="3">*</font></td>
					<td colspan="1"><html:text property="departOn" styleId="popupDatepicker" readonly="true" onchange="updatedate(),checkingdates()" onmouseover="updatedate(),checkingdates()"  onclick="updatedate(),checkingdates()" value=""/></td>&nbsp;&nbsp;
		            <td>Preferred Departure Time: <font color="red" size="3">*</font></td>
		            <td>
		            
		            <html:text property="departTime" styleId="timeFrom"  size="15" onchange="updatedate()" value=""/>&nbsp;&nbsp</td>
				</tr>	
		        <tr>
					<td>Final Destination: <font color="red" size="3">*</font></td>
					<td colspan="3"><html:text property="toPlace"   size="70"  onkeyup="this.value = this.value.replace(/'/g,'`')"  value="${travelRequestForm.toPlace}"/></td>
				</tr>			
		        <tr>
					<td>Trip Return On: <font color="red" size="3">*</font></td>
					<td><html:text property="returnOn"   styleId="popupDatepicker2" readonly="true" onchange="updatedate(),checkingdates()" onmouseover="updatedate(),checkingdates()"  onclick="updatedate(),checkingdates()"   value=""/></td>
					<td>Preferred Return Time: <font color="red" size="3">*</font></td>
					<td><html:text property="returnTime"  styleId="timeTo"   size="15" onchange="updatedate(),checkingdates()" value=""/></td>
				</tr>
		        <tr>
					<td>Travel Days: </td><td>
					<input type="text"  name="travel_Days" readonly="readonly" style="background: rgb(179,175,174)" id="travel_Days" /> 
					<div id="travel_Days1">
					<input type="hidden"  name="travel_Days1"  />
					</div>
					</td>
					<td>Priority: </td> 
		            <td>
		            
		            <html:select name="travelRequestForm" property="trip_Priority" styleClass="text_field"  value="">
						<html:option value="Very High">Very High</html:option>
						<html:option value="High">High</html:option>
						<html:option value="Low">Low</html:option>
						</html:select>
		            
					</td>
				</tr>
                <tr>
					<td colspan="1">Travel Company Preference:  </td>
					<td><input type="text" name="airline_Pref"  value="${travelRequestForm.airline_Pref}"/> </td>
                  	<td colspan="1">Justifcation:<font color="red" size="3">*</font> </td>
					<td><textarea rows="4" cols="30" name="airline_Just">${travelRequestForm.airline_Just}</textarea></td>
				</tr>
		        <tr>	
		        
					<td colspan="1">Sponsoring Division: </td>
					<td> 
					<html:select  property="spon_div" name="travelRequestForm" >
					<html:option value="">--Select--</html:option>
					<html:options name="travelRequestForm"  property="divIdList" labelProperty="divLabelList"/>
					</html:select>
					
					</td>
					<td colspan="1">Budget Code: </td>
					<td><input type="text" name="bud_code" onkeypress="return isNumber(event)"  value="${travelRequestForm.bud_code}"/></td>
				</tr>
				<tr>
					<td>Estimated Trip Cost: </td>
					<td colspan="3"><input type="text" name="est_trip_cose" onkeypress="return isNumber(event)"  value="${travelRequestForm.est_trip_cose}"/></td>
				</tr>
				<tr>
				
						
						
						
						
						<td>Trip Advance required: <font color="red" size="3" >*</font></td>
					<td>
					<html:select name="travelRequestForm" property="trip_Advance" onchange="trip()">
						<html:option value="">--Select--</html:option>
						<html:option value="Yes" >Yes</html:option>
						<html:option value="No">No</html:option>
						</html:select>
					</td>
					
						<td>On Duty to be applied: <font color="red" size="3">*</font></td>
					<td>
						<html:select name="travelRequestForm" property="onduty_Req" onchange="ondutyupdate()" styleId="onduty_Req">
						<html:option value="">--Select--</html:option>
						<html:option value="Yes" >Yes</html:option>
						<html:option value="No">No</html:option>
						</html:select>
						
						
					</td>	
					</tr>
					
					<tr>						
					<td>Amount: </td>
					<td><input type="text" name="trip_Amt" onkeypress="return isNumber(event)" id="trip_Amt" readonly="readonly" style="background: rgb(179,175,174)" value="${travelRequestForm.trip_Amt}" /></td>
					<td>Currency: </td>
					<td>
					<div class="no" id="trip_Currency_div"> 
					 <html:select  property="trip_Currency" name="travelRequestForm"  styleId="trip_Currency" style="background: rgb(179,175,174)"  >
					<html:option value="">--Select--</html:option>
					<html:options name="travelRequestForm"  property="currIdList" labelProperty="currLabelList"/>
					</html:select>
					</div> 
					
					<%-- <input type="text" name="trip_Currency" id="trip_Currency" readonly="readonly" style="background: rgb(179,175,174)" value="${travelRequestForm.trip_Currency}"/> --%>
					</td>
					</tr>
			
			<tr style="display: none">
					<td>From Date:</td>
					<td><input type="text" name="trip_From_Date" id="trip_From_Date" readonly="readonly" style="background: rgb(179,175,174)"/></td>
					<td>From Time: </td>
					<td><input type="text"  name="trip_From_Time" readonly="readonly" style="background: rgb(179,175,174)"/> </td>
					</tr>
					<tr style="display: none">
					<td>To Date: </td>
					<td><input type="text" name="trip_To_Date" id="trip_To_Date" readonly="readonly" style="background: rgb(179,175,174)"/> </td>
					<td>To Time: </td>
					<td><input type="text" name="trip_To_time" readonly="readonly" style="background: rgb(179,175,174)"/></td>							
					</tr>	
			
				
					
					
					
			</table>
			<br/>
			
			<div style="display: none" id="self_Multiple">
			<table class="bordered" style="position: relative;left: 2%;width: 80%;">
			<tr><th colspan="8">Other Details</th></tr>
			
			
			<tr>
					<td>Accomodation<font color="red" size="3">*</font></td>
					<td>
					<html:select name="travelRequestForm" property="hotel_Res" onchange="accodchange()" styleId="hotel_Res">
						<html:option value="No">No</html:option>
						<html:option value="Yes" >Yes</html:option>						
						</html:select>
					
					</td>
					
					<td>Rental Car required: <font color="red" size="3">*</font></td>
					<td>
						<html:select name="travelRequestForm" property="rent_Car" onchange="carchange()">
						<html:option value="No">No</html:option>
						<html:option value="Yes" >Yes</html:option>						
						</html:select>
					</td>
					
				</tr>
				
				<tr>
				<td>Accomodation Type</td>
				<td>
				<div id="accom_type_div" class="no">
					<html:select name="travelRequestForm" property="accom_type" styleId="accom_type" onchange="hotelChange()" style="background: rgb(179,175,174)">
						<html:option value="">--Select--</html:option>
						<html:option value="Hotel">Hotel</html:option>
						<html:option value="Guest House" >Guest House</html:option>						
						</html:select>
				</div>
				</td>
				<td>Guest House Name</td>
				<td><input type="text" name="accom_name" id="accom_name" style="background:rgb(179,175,174)"  readonly="readonly"/></td>
				</tr>
				
				<tr>
				<td>
				Hotel Name:
				</td>
				<td>
				<input type="text" name="hotel_Name" id="hotel_Name" readonly="readonly" style="background: rgb(179,175,174)"/>
				</td>
				
				<td>
				Hotel City:
				</td>
				<td>
				<input type="text" name="hotel_City" id="hotel_City" readonly="readonly" style="background: rgb(179,175,174)"/>
				</td>
				</tr>
				
				<tr>
					<td>Pickup Details: </td>
					<td><textarea rows="4" cols="20" name="pickup_Details" id="pickup_Details" readonly="readonly" style="background: rgb(179,175,174)"></textarea></td>
					<td>Drop Details: </td>
					<td><textarea rows="4" cols="20" name="drop_Details" id="drop_Details" readonly="readonly" style="background: rgb(179,175,174)"></textarea></td>
				</tr>
				
				</table>
				</div>
		</div>

		<div id="R2" class="tabcontent" style="width: 890px; margin-left: 22px" >
			<iframe src="travelrequest.do?method=travellerList" name="contentPage1" width="100%" height="800px"
				frameborder="0" scrolling="no" id="the_iframe"  frameborder="0" style="margin-left: 0px">
        	</iframe> 
		</div>

		<div id="R4" class="tabcontent" style="width: 850px; margin-left: 22px" >
		
			
						
	   		
			
			<table class="bordered" style="position: relative;left: 2%;width: 80%;" id="mcity">
				<tr><th colspan="8">Multiple City List </th></tr>
				<tr>
					<th>Travel list</th><th>Location</th><th> Preference</th><th>Arrival date</th> 
					<th>Arrival Time</th><th>Departure Date</th><th>Departure Time</th>
					<th>Action</th>
				</tr>
				<logic:notEmpty name="city"> 
				<logic:iterate id="abc" name="city">
				<tr>
<td>${abc.multiemployeeno}<input name="userlistIdl" value="${abc.multiemployeeno}" type="hidden"></td>
<td>${abc.locationId}<input name="mlocl" value="${abc.locationId}" type="hidden"></td>
<td>${abc.airline_Pref}<input name="mairpl" value="${abc.airline_Pref}" type="hidden">
</td><td>${abc.trip_From_Date}<input name="marrvdatel" value="${abc.trip_From_Date}" type="hidden">
</td><td>${abc.trip_To_time}<input name="marrtimel" value="${abc.trip_To_time}" type="hidden">
</td><td>${abc.trip_To_Date}<input name="mdeptdatel" value="${abc.trip_To_Date}" type="hidden">
</td><td>${abc.trip_To_time}<input name="mdepttimel" value="${abc.trip_To_time}" type="hidden">
</td><td style="display: none;"><input name="mhotel_Resl" value="${abc.hotel_Res}" type="hidden"></td>
<td style="display: none;"><input name="mrent_Carl" value="${abc.rent_Car}" type="hidden"></td>
<td style="display: none;"><input name="mhotel_Namel" value="${abc.hotel_Name}" type="hidden"></td>
<td style="display: none;"><input name="mhotel_Cityl" value="${abc.hotel_City}" type="hidden"></td>
<td style="display: none;"><input name="mpickup_Detailsl" value="${abc.pickup_Details}" type="hidden"></td>
<td style="display: none;"><input name="mdrop_Detailsl" value="${abc.drop_Details}" type="hidden"></td>

<td style="display: none;"><input name="moriginl" value="${abc.accom_type}" type="hidden"></td>
<td style="display: none;"><input name="mdeparturel" value="${abc.accom_name}" type="hidden"></td>
<td style="display: none;"><input name="maccom_typel" value="${abc.origin}" type="hidden"></td>
<td style="display: none;"><input name="maccom_namel" value="${abc.departure}" type="hidden"></td>
<td style="display: none;"><input name="mtravelmodel" value="${abc.travelmode}" type="hidden"></td>
<td style="display: none;"><input name="mtraveltypel" value="${abc.traveltype}" type="hidden"></td>

<td><img src="images/delete.png" onclick="deleteRow1(this,1)" title="Delete"></td>
</tr>
				
				</logic:iterate>
				</logic:notEmpty>
				
			</table>

			<br/>

			
			<table class="bordered" style="position: relative;left: 2%;width: 80%;" >
				<tr><th colspan="4">Multiple City Details </th></tr>
				<tr>
				<td>
				Traveller List<font color="red" size="3">*</font>
				</td>
				<td >
				<select name="userlist" id="userlistId" >
						<option value="all">All</option>
						<option value="">--Select---</option>
				</select>
				</td>
				</tr>
				
							<tr>
				<td>Travel Mode<font color="red" size="3">*</font></td>
				<td>
	<select name="travelmode1"  onchange="travelmode2()"  id="travelmode1" >
	<option value="">--Select--</option>
  		<option value="Road">Road</option>
  		<option value="Rail">Rail</option>
  		<option value="Air">Air</option>
		</select>
	</td>	
	
	<td colspan="1"> Travel Type<font color="red" size="3">*</font> </td>
	<td>
	
	
	<select name="traveltype1" id="traveltype1" >
	<option value="">--Select</option>
	</select>
				
	</td>
				
				
				</tr>
				<tr>
					<td>Location:<font color="red" size="3">*</font> </td>
					<td><input type="text" name="mloc" id="mloc"/></td> 
					<td> Preference: </td>
					<td><input type="text" name="mairp" id="mairp"/> </td>
				</tr>
				<tr> 
					<td>Origin: </td>
					<td><input type="text" name="morigin" id="morigin"/> </td>
					
					<td>Destination: </td>
					<td><input type="text" name="mdeparture" id="mdeparture"/> </td>
				
				</tr>
				<tr>
					<td>Arrival date: </td>
					<td><input type="text" name="marrvdate" id="marrvdate" onclick="datedisplay(this.id)" onmouseover="datedisplay(this.id)" /> </td>
					<td>Arrival Time: </td>
					<td>
					<html:text property="marrtime" styleId="marrtime"  size="15"  value=""/>
					 
					</td>
				</tr>
				<tr>
					<td>Departure Date: </td>
					<td><input type="text" name="mdeptdate" id="mdeptdate" onclick="datedisplay(this.id)" onmouseover="datedisplay(this.id)"/> </td>
					<td>Departure Time: </td>
					<td>
					<html:text property="mdepttime" styleId="mdepttime"  size="15"  value=""/>
					</td>
				</tr>
				
				<tr>
				
				</tr>
				
				
				
				<tr style="display: none" id="mcity1">
					<td>Accomodation: <font color="red" size="3">*</font></td>
					<td><select name="mhotel_Res" onchange="maccodchange()" id="mhotel_Res">
						<option value="">--Select--</option>
						<option value="Yes">Yes</option>
						<option value="No">No</option>
						</select>
					</td>
					
					<td>Rental Car required: <font color="red" size="3">*</font></td>
					<td>
						<select name="mrent_Car" onchange="mcarchange()" id="mrent_Car">
							<option value="">--Select--</option>
							<option value="Yes">Yes</option>
							<option value="No">No</option>
						</select>
					</td>
					
				</tr>
				
				<tr>
				<td>Accomodation Type</td>
				<td>
				<div id="maccom_type_div" class="no">
				
				<select name="maccom_type" onchange="mhotelChange()" id="maccom_type" style="background: rgb(179,175,174)">
							<option value="">--Select--</option>
							<option value="Hotel">Hotel</option>
							<option value="Guest House">Guest House</option>
						</select>
				</div>
				</td>
				<td>Guest House Name</td>
				<td><input type="text" name="maccom_name" id="maccom_name" style="background:rgb(179,175,174)"  readonly="readonly"/></td>
				
				</tr>
				
				
				<tr style="display: none" id="mcity2">
				<td>
				Hotel Name:
				</td>
				<td>
				<input type="text" name="mhotel_Name" id="mhotel_Name" readonly="readonly" style="background: rgb(179,175,174)"/>
				</td>
				
				<td>
				Hotel City:
				</td>
				<td>
				<input type="text" name="mhotel_City" id="mhotel_City" readonly="readonly" style="background: rgb(179,175,174)"/>
				</td>
				</tr>
				
				<tr style="display: none" id="mcity3">
					<td>Pickup Details: </td>
					<td><textarea rows="4" cols="20" name="mpickup_Details" id="mpickup_Details" readonly="readonly" style="background: rgb(179,175,174)"></textarea></td>
					<td>Drop Details: </td>
					<td><textarea rows="4" cols="20" name="mdrop_Details" id="mdrop_Details" readonly="readonly" style="background: rgb(179,175,174)"></textarea></td>
				</tr>
				<tr>
					<td colspan="4" align="center"><center>
						&nbsp; <html:button property="method" styleClass="rounded" value="Add" onclick="addMultiplecity()" style="align:right;width:100px;"/> &nbsp;
						</center>
					</td>
				</tr>
				
			</table>
		</div>

		<!-- <div id="R3" class="tabcontent" style="width: 890px; margin-left: 22px" >
		
		</div> -->

		<table class="bordered" style="position: relative;left: 2%;width: 80%;">
			<tr>
				<td colspan="4" align="center">
					<center>
						<html:button property="method" styleClass="rounded" value="Submit" onclick="applyDomastic()" style="align:right;width:100px;"/> &nbsp;
						<html:button property="method" styleClass="rounded" value="Close" onclick="closerequest()" style="align:right;width:100px;"/> 
					</center>
				</td>
			</tr>
		</table>

		<br/>
		<html:hidden property="requestNumber" />
		</div>
  		</html:form>			
	</body>
</html>
