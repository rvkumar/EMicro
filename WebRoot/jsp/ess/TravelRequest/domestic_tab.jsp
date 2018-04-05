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
	if(x=="On Behalf")
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

function uploadDocument()
{
	
	
	var travelfor=document.forms[0].travelFor.value;
	var reqtype=document.forms[0].reqType.value;
	var mode=document.forms[0].modeOfTravel.value;
	var typetravel=document.forms[0].typeOfTravel.value;
	var travelreqfor=document.forms[0].travelRequestFor.value;
	var from=document.forms[0].fromPlace.value;
	var departon=document.forms[0].departOn.value;
	var deptime=document.forms[0].departTime.value;
	var to=document.forms[0].toPlace.value;
	var returnon=document.forms[0].returnOn.value;
	var rettime=document.forms[0].returnTime.value;
	var purpose=document.forms[0].purposeOfVisit.value;
	var remarks=document.forms[0].remarks.value;
	
	/* var userpassno=document.forms[0].userpassportno.value;
	var userpassplac=document.forms[0].userpassportplace.value;
	var userpassissu=document.forms[0].userpassportissuedate.value;
	var userpassexp=document.forms[0].userpassportexpirydate.value; */
	
	
	
	if(travelfor=="")
		{
		alert("Please Select Travel For");
		document.forms[0].travelFor.focus();		
		return false;
		
		}
	
	if(reqtype=="")
	{
	alert("Please Select Req Type");
	document.forms[0].reqType.focus();		
	return false;
	
	}
	
	
	/* if(reqtype=="International")
		{
		
		if(userpassno=="")
		{
		alert("Please enter Passport No.");
		document.forms[0].userpassportno.focus();		
		return false;
		
		}
	
	if(userpassplac=="")
	{
	alert("Please enter Passport Issued Place");
	document.forms[0].userpassportplace.focus();		
	return false;
	
	}
	
	if(userpassissu=="")
	{
	alert("Please Select Passport Date of issue");
	document.forms[0].userpassportissuedate.focus();		
	return false;
	
	}

if(userpassexp=="")
{
alert("Please Select Passport Date of expiry");
document.forms[0].userpassportexpirydate.focus();		
return false;

}
		
		
		} */
	
	/* if(mode=="")
	{
	alert("Please Select Mode Of Travel");
	document.forms[0].modeOfTravel.focus();		
	return false;
	
	}
	
	if(typetravel=="")
	{
	alert("Please Select Type Of Travel");
	document.forms[0].typeOfTravel.focus();		
	return false;
	
	}
	
	if(travelreqfor=="")
	{
	alert("Please Select Travel Request For");
	document.forms[0].travelRequestFor.focus();		
	return false;
	
	}
	


	
	if(from=="")
	{
	alert("Please Enter From Location");
	document.forms[0].fromPlace.focus();		
	return false;
	
	}
	
	if(departon=="")
	{
	alert("Please select Depart On Date");
	document.forms[0].departOn.focus();		
	return false;
	
	}
	
	if(deptime=="")
	{
	alert("Please Enter Preferred Depart Time");
	document.forms[0].departTime.focus();		
	return false;
	
	}
	
	if(to=="")
	{
	alert("Please Enter To Location");
	document.forms[0].toPlace.focus();		
	return false;
	
	}
	
	if(returnon=="")
	{
	alert("Please select Return On Date");
	document.forms[0].returnOn.focus();		
	return false;
	
	}
	
	if(rettime=="")
	{
	alert("Please Enter Preferred Return Time");
	document.forms[0].returnTime.focus();		
	return false;
	
	}
	

	
	if(purpose=="")
	{
	alert("Please select Purpose Of visit");
	document.forms[0].purposeOfVisit.focus();		
	return false;
	
	}
	
	if(remarks=="")
	{
	alert("Please Enter Remarks");
	document.forms[0].remarks.focus();		
	return false;
	
	} */
	
	  if(document.forms[0].documentFile.value=="")
	    {
	      alert("Please Select File.");
	      document.forms[0].documentFile.focus();
	      return false;
	    }
	
	var x=document.forms[0].reqType.value;
	document.forms[0].action="travelrequest.do?method=uploadDocuments&reqType="+x;
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

	
	var travelfor=document.forms[0].travelFor.value;

	var reqtype=document.forms[0].reqType.value;
	var mode=document.forms[0].modeOfTravel.value;
	var typetravel=document.forms[0].typeOfTravel.value;
	var travelreqfor=document.forms[0].travelRequestFor.value;
	var from=document.forms[0].fromPlace.value;
	var departon=document.forms[0].departOn.value;
	var deptime=document.forms[0].departTime.value;
	var to=document.forms[0].toPlace.value;
	var returnon=document.forms[0].returnOn.value;
	var rettime=document.forms[0].returnTime.value;
	var purpose=document.forms[0].purposeOfVisit.value;
	var remarks=document.forms[0].remarks.value;

	/* var userpassno=document.forms[0].userpassportno.value;
	var userpassplac=document.forms[0].userpassportplace.value;
	var userpassissu=document.forms[0].userpassportissuedate.value;
	var userpassexp=document.forms[0].userpassportexpirydate.value; */
	

	
	if(travelfor=="")
		{
		alert("Please Select Travel For");
		document.forms[0].travelFor.focus();		
		return false;
		
		}
	
	if(reqtype=="")
	{
	alert("Please Select Req Type");
	document.forms[0].reqType.focus();		
	return false;
	
	}
	
	
	/* if(reqtype=="International")
		{
		
		if(userpassno=="")
		{
		alert("Please enter Passport No.");
		document.forms[0].userpassportno.focus();		
		return false;
		
		}
	
	if(userpassplac=="")
	{
	alert("Please enter Passport Issued Place");
	document.forms[0].userpassportplace.focus();		
	return false;
	
	}
	
	if(userpassissu=="")
	{
	alert("Please Select Passport Date of issue");
	document.forms[0].userpassportissuedate.focus();		
	return false;
	
	}

if(userpassexp=="")
{
alert("Please Select Passport Date of expiry");
document.forms[0].userpassportexpirydate.focus();		
return false;

}
		
		
		} */
	
	if(mode=="")
	{
	alert("Please Select Mode Of Travel");
	document.forms[0].modeOfTravel.focus();		
	return false;
	
	}
	
	if(typetravel=="")
	{
	alert("Please Select Type Of Travel");
	document.forms[0].typeOfTravel.focus();		
	return false;
	
	}
	
	if(travelreqfor=="")
	{
	alert("Please Select Travel Request For");
	document.forms[0].travelRequestFor.focus();		
	return false;
	
	}
	


	
	if(from=="")
	{
	alert("Please Enter From Location");
	document.forms[0].fromPlace.focus();		
	return false;
	
	}
	
	if(departon=="")
	{
	alert("Please select Depart On Date");
	document.forms[0].departOn.focus();		
	return false;
	
	}
	
	if(deptime=="")
	{
	alert("Please Enter Preferred Depart Time");
	document.forms[0].departTime.focus();		
	return false;
	
	}
	
	if(to=="")
	{
	alert("Please Enter To Location");
	document.forms[0].toPlace.focus();		
	return false;
	
	}
	
	if(returnon=="")
	{
	alert("Please select Return On Date");
	document.forms[0].returnOn.focus();		
	return false;
	
	}
	
	if(rettime=="")
	{
	alert("Please Enter Preferred Return Time");
	document.forms[0].returnTime.focus();		
	return false;
	
	}
	

	
	if(purpose=="")
	{
	alert("Please select Purpose Of visit");
	document.forms[0].purposeOfVisit.focus();		
	return false;
	
	}
	
	if(remarks=="")
	{
	alert("Please Enter Remarks");
	document.forms[0].remarks.focus();		
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
	
	if(y=="On Behalf" || y=="Multiple")
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

</style>

<style>


ul.tab {
    list-style-type: none;
    margin: 30px;
    padding: 0;
    overflow: hidden;
    border: 1px solid #ccc;
    background-color: rgb(238,238,238);
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
ul.tab li a:hover {
    background-color: #ddd;
}

/* Create an active/current tablink class */
ul.tab li a:focus, .active {
    background-color: #ccc;
}

/* Style the tab content */
.tabcontent {
    display: none;
    padding: 6px 12px;
    border: 1px solid #ccc;
    border-top: none;
}
</style>

<script>
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
  </head>
  
  <body onload="chkview();chkguest();chngedomestic('body');">
  
  	<div align="center">
				<logic:notEmpty name="travelRequestForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="travelRequestForm" property="message" />');
					</script>
				</logic:notEmpty>
			
			</div>
  
  	<html:form action="travelrequest" enctype="multipart/form-data" method="post" >
  	<ul class="tab">
  <li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'London')">London</a></li>
  <li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'Paris')">Paris</a></li>
  <li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'Tokyo')">Tokyo</a></li>
</ul>

<div id="London" class="tabcontent">
  <h3>London</h3>
  <p>London is the capital city of England.</p>
</div>

<div id="Paris" class="tabcontent">
  <h3>Paris</h3>
  <p>Paris is the capital of France.</p> 
</div>

<div id="Tokyo" class="tabcontent">
  <h3>Tokyo</h3>
  <p>Tokyo is the capital of Japan.</p>
</div>
  		
  	</html:form>			
  </body>
</html>
