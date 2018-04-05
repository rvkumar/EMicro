<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<html xmlns="http://www.w3.org/1999/xhtml">
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
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

.no
{pointer-events: none; 
}
</style>
<script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>

<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script language="javascript">

var formOK = false;

function validatePDF(objFileControl){
 var file = objFileControl.value;
 var len = file.length;
 var ext = file.slice(len - 4, len);
 if(ext.toUpperCase() == ".PDF"){
   formOK = true;
 }
 else{
   formOK = false;
   alert("Only PDF file format attachment is allowed.");
   objFileControl.value="";
 }
}


/* $(function () {
	$('#timeFrom').timeEntry();
	});


	$(function () {


		$('#timeTo').timeEntry();
	});
	 */

	/* $(function() {
		$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
		$('#inlineDatepicker').datepick({onSelect: showDate});
	});	 */




function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}



function uploadDocument1()
{	
	checkingdates();
	var ia=$("#popupDatepicker").val();
	var ja=$("#popupDatepicker2").val();

	if(ia==""&&ja=="")
	{
	alert("Please Select Trip Start and End Date");
	return false;
	}
	if(document.getElementById("employeeNumber1").value!="")
	{
	var rows=document.getElementsByName("guest_pernr_list");
	for(var i=0;i<rows.length;i++)
	{
	if(rows[i].value!="")
	{
	if (rows[i].value==document.getElementById("employeeNumber1").value)
	{
	alert("User Already Exist");
	document.forms[0].guest_pernr.value="";
	document.forms[0].guestName.value="";
	document.forms[0].gender.value="M";
	document.forms[0].email_Guest.value="";
	document.forms[0].guest_Company.value="";
	document.forms[0].guest_DOB.value="";
	return false;
	}
	}
	}
	}
	if(document.forms[0].utravelmode.value=="")
	{
	alert("Please Select Travel Mode");
	return false;
	}
	
	if(document.forms[0].traveltype.value=="")
	{
	alert("Please Select Travel Type");
	return false;
	}
	
	
	if(document.forms[0].guestName.value=="")
	{
	alert("Please Enter Guest Name");
	return false;
	}
	
	if(document.forms[0].gender.value=="")
	{
	alert("Please Select Gender");
	return false;
	}
	
	if(document.forms[0].guest_DOB.value=="")
	{
	alert("Please enter date of birth");
	return false;
	}
	

	if(document.forms[0].guestContactNo.value=="")
	{
	alert("Please Enter Contact No");
	return false;
	}
	
	
	if(document.forms[0].fromPlace.value=="")
	{
	alert("Please Enter Origin ");
	return false;
	}
	
	if(document.forms[0].toPlace.value=="")
	{
	alert("Please Enter Final Destination ");
	return false;
	}
	
	

	
	
	
	if(document.getElementById("traveltype").value=="International")
	{
		
		
	if(document.forms[0].passportno.value=="")
		{
		alert("Please Enter Passport Number");
		return false;
		}	
	
	if(document.forms[0].passportexpirydate.value=="")
	{
	alert("Please Enter Passport Expiry Date");
	return false;
	}
	
	
	
	}
	
	var iaa=$("#triptype", window.parent.document).val();
	if(iaa!="Multi-City")
	{
		
		if($('#hotel_Res').val()=="")
		{
		alert("Select Accomodation Required or Not");
		$('#hotel_Res').focus();
		return false;
		}
			
		if($('#hotel_Res').val()=="Yes")
		{
		if($('#accom_type').val()=="")
		{
		alert("Select Accomodation Type");
		$('#accom_type').focus();
		return false;
		}
		}


		if($('#accom_type').val()=="Hotel")
		{
		if($('#hotel_Name').val()=="")
		{
		alert("Enter Hotel Name ");
		$('#hotel_Name').focus();
		return false;
		}

		if($('#hotel_City').val()=="")
		{
		alert("Enter Hotel City ");
		$('#hotel_City').focus();
		return false;
		}

		}


		if($('#accom_type').val()=="Guest House")
		{
		if($('#accom_name').val()=="")
		{
		alert("Enter Guest  Name ");
		$('#accom_name').focus();
		return false;
		}

		}

		if($('#rent_Car').val()=="")
		{
		alert("Please Select  Rental car Required or Not ");
		$('#rent_Car').focus();
		return false;
		}


		if($('#rent_Car').val()=="Yes")
		{
		if($('#pickup_Details').val()=="")
		{
		alert("Enter Pickup Details ");
		$('#pickup_Details').focus();
		return false;
		}

		if($('#drop_Details').val()=="")
		{
		alert("Enter Drop Details ");
		$('#drop_Details').focus();
		return false;
		}

		}
		
		
	}


	document.forms[0].action="travelrequest.do?method=travellerListUpload&departon="+ia+"&returnOn="+ja;
	document.forms[0].submit();

}

function verifyOnduty(tid)
{

var i=$("#popupDatepicker", window.parent.document).val();
var j=$("#popupDatepicker2", window.parent.document).val();

document.forms[0].action="travelrequest.do?method=ondutychecking&tid="+tid+"&departdate="+i+"&returndate="+j;
document.forms[0].submit();
}
function deleteTraveller(kid)
{

document.forms[0].action="travelrequest.do?method=travellerListDelete&kid="+kid;
	document.forms[0].submit();

}




function statusMessage(message){
alert(message);
}

$(function() {
	$('#passportexpirydate').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

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
	     xmlhttp.open("POST","travelrequest.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
	    xmlhttp.send();
	}
	
	function selectUser(input,reqFieldName){
	
	var lastChar = reqFieldName.substr(reqFieldName.length - 1);
	 var res = input.split("-");
		document.getElementById(reqFieldName).value=res[1];
		document.forms[0].guestName.value=res[0];
		document.forms[0].gender.value=res[6];
		document.forms[0].guest_Title.value=res[7];
		document.forms[0].email_Guest.value=res[8];
		document.forms[0].guest_DOB.value=res[9];
		document.forms[0].guest_Company.value="Micro Labs";
		
		
	/*	document.getElementById("empname"+lastChar).value=res[0];
		document.getElementById("dept"+lastChar).value=res[2];	
		
		if(res[4].contains('/'))
		{	
		document.getElementById("desg"+lastChar).value=res[3];
	
		}
		else
		{
		document.getElementById("desg"+lastChar).value=res[3]+"-"+res[4];
	
		} */
		disableSearch(reqFieldName);
	}


	function disableSearch(reqFieldName){
	 
			if(document.getElementById("sU") != null){
			document.getElementById("sU").style.display="none";
			
		}
		}	


function selectchange()
{
var a=document.forms[0].guest_Type.value;
if(a=="employee")
{
document.getElementById("empno").className="";
document.forms[0].guestName.readOnly =true;
document.forms[0].guest_Company.readOnly =true;
document.getElementById("genderid").className = "no";

document.forms[0].guestName.style.background="rgb(179,175,174)";
document.forms[0].guest_Company.style.background="rgb(179,175,174)";
document.forms[0].gender.style.background="rgb(179,175,174)";

}

else
{
document.getElementById("empno").className="no";
document.forms[0].guestName.readOnly =false;
document.forms[0].guest_Company.readOnly =false;
document.getElementById("genderid").className = "";

document.forms[0].guestName.style.background="";
document.forms[0].guest_Company.style.background="";
document.forms[0].gender.style.background="";

}


document.forms[0].guest_pernr.value="";
document.forms[0].guestName.value="";
document.forms[0].gender.value="M";
document.forms[0].email_Guest.value="";
document.forms[0].guest_Company.value="";
document.forms[0].guest_DOB.value="";


}

function display()
{
	
var iaa=$("#triptype", window.parent.document).val();
if(iaa!="Multi-City")
{
	document.getElementById("travelother").style.display="";
}
else
{
	document.getElementById("travelother").style.display="none";
	}	

document.forms[0].departOn.value=$("#popupDatepicker", window.parent.document).val();
/* document.forms[0].timeFrom.value=$("#timeFrom", window.parent.document).val(); */
document.forms[0].returnOn.value=$("#popupDatepicker2", window.parent.document).val();
/* document.forms[0].returnTime.value=$("#timeTo", window.parent.document).val();  */

var j=$("#travelFor", window.parent.document).val();
if(j=="Guest")
	{
	
	document.getElementById("g2").style.display="";
	document.getElementById("g3").style.display="";
	document.getElementById("g1").style.display="none";
	
	}
if(j=="On Behalf")
{

	document.getElementById("g2").style.display="none";
	document.getElementById("g3").style.display="none";
	document.getElementById("g1").style.display="";

}
if(j=="Multiple")
{
	document.getElementById("g2").style.display="";
	document.getElementById("g3").style.display="";
	document.getElementById("g1").style.display="";

}

}


function datedisplay(id)
{

	
var start=$("#popupDatepicker", window.parent.document).val();
var end=$("#popupDatepicker2", window.parent.document).val();

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
/* $('#'+id).datepicker('hide'); */
	
}

function datedisplay1(id)
{
	
var start=$("#popupDatepicker", window.parent.document).val();
var end=$("#popupDatepicker2", window.parent.document).val();

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

$('#'+id).datepick({dateFormat: 'dd/mm/yyyy'});
$('#inlineDatepicker').datepick({onSelect: showDate});

	
}


function change()
{
	
	
document.getElementById("travellist").click();	


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


function hotelChange()
{


if(document.forms[0].accom_type.value=="")
{
document.getElementById("accom_type").value=document.getElementById("accom_type").oldvalue;
return false;
}

if(document.forms[0].accom_type.value!="")
{

if(document.forms[0].accom_type.value=="Hotel")
{
document.getElementById("hotel_Name").style.background="";
document.getElementById("hotel_City").style.background="";
document.getElementById("hotel_Name").readOnly =false;
document.getElementById("hotel_City").readOnly =false;

document.getElementById("accom_name").style.background="rgb(179,175,174)";
document.getElementById("accom_name").readOnly =true;
document.getElementById("accom_name").value ="";

}
else
{
document.getElementById("hotel_Name").style.background="rgb(179,175,174)";
document.getElementById("hotel_City").style.background="rgb(179,175,174)";
document.getElementById("hotel_Name").readOnly =true;
document.getElementById("hotel_City").readOnly =true;
document.getElementById("hotel_Name").value ="";
document.getElementById("hotel_City").value ="";

document.getElementById("accom_name").style.background="";
document.getElementById("accom_name").readOnly =false;
document.getElementById("accom_name").value ="";

}
}


}

function travelmode1()
{
	
	document.getElementById("traveltype").value="";
	passdisplay();	
	
var xmlhttp;
var dt;
dt=document.forms[0].utravelmode.value;
if(dt!="")
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
    document.getElementById("traveltype").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","travelrequest.do?method=travelmode&type="+dt,true);
xmlhttp.send();
}
}

function passdisplay()
{
	if(document.getElementById("traveltype").value=="International")
	{
	document.getElementById("passport").style.display="";
	}
	else
	{
	document.getElementById("passport").style.display="none";
	document.forms[0].passportno.value="";
	document.forms[0].passportexpirydate.value="";
	document.forms[0].guest_Visano.value="";
	}
	
	
	
	}
	
function checkingdates()
{
	
var xmlhttp;
var dt;
var dt2;
var dt3;
dt=document.forms[0].departOn.value;
dt2=document.forms[0].returnOn.value;
dt3=document.forms[0].guest_pernr.value;

		
var pernr=document.forms[0].guest_pernr.value;
if(pernr!="")
{

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
    document.getElementById("travel_Days1").style.display="none";
    totalDays=document.forms[0].travel_Days1.value;
    if(totalDays=="0")
    {
    alert("Another Travel Request Exist in Perticular dates");
    $('#popupDatepicker').val("");
    $('#popupDatepicker2').val("");
    }
    }
  }
xmlhttp.open("POST","travelrequest.do?method=traveldate&dt="+dt+"&dt2="+dt2+"&dt3="+pernr,true);
xmlhttp.send();
}
}
}


/* 
$('#popupDatepicker').change(

		  function(event) {
		    $('#SelectedDate').text("Selected date: " + this.value);
		    $('#popupDatepicker').datepicker('hide'); // if youdon't hide datepicker will be kept open

		  })
		  
$('#popupDatepicker2').change(

		  function(event) {
		    $('#SelectedDate').text("Selected date: " + this.value);
		    $('#popupDatepicker2').datepicker('hide'); // if youdon't hide datepicker will be kept open

		  })	 */	  

		  
</script>


<body onload="change()">
	<html:form action="travelrequest" enctype="multipart/form-data" method="post" >
	<html:hidden property="id" name="travelRequestForm" />
	<html:hidden property="travelmode" name="travelRequestForm" styleId="travelmode" />

	<div style="display: none">
		<html:button property="method" styleClass="rounded" styleId="travellist" value="Travellist" onclick="display()"/>
	</div>

	<div align="center" id="messageID" style="visibility: true;">
		<logic:notEmpty name="travelRequestForm" property="message">
			<script language="javascript">
				statusMessage('<bean:write name="travelRequestForm" property="message" />');
			</script>
		</logic:notEmpty>
	</div>

	<input type="text" name="test" value="0" id="test" style="display: none"/>
	<input type="text" name="test" value="${travelRequestForm.id}" id="test" style="display: none"/>

		<% int i =0; %>
	<table id="travelId" class="bordered" style="position: relative;left: 2%;width: 100%;">
			<tr><th colspan="11">Travellers List</th></tr>
			<tr>
				<th>Type</th>
				<th>Name</th>
				<th>Gender</th>
				<th>Email</th>
				<th>Contact No</th> 
			<!-- 	<th>Company Name</th>
				<th>DOB</th> -->
					<th>Origin</th>
			
				
				<th>Depart.Dt</th>
					<th>Destination</th>
				<th>Return.Dt</th>
				<th>Attachment</th>
				<th>Action</th>
			</tr>
				<logic:notEmpty name="emplist">
				<logic:iterate id="abc" name="emplist">
					<%i++; %>
					<script>document.getElementById("test").value=<%=i%></script>
			<tr>
				<td>${abc.guest_Type}</td>
				<td>${abc.guest_Title}.${abc.guestName}</td></td>
				<td>${abc.gender}</td>
				<td>${abc.email_Guest}</td>
				<td>${abc.guestContactNo}</td>
				<%-- <td>${abc.guest_Company}</td>
				<td>${abc.guest_DOB}</td> --%>
				<td>${abc.fromPlace}</td>
			
				<td>${abc.departOn}</td>
					<td>${abc.toPlace}</td>
				<td>${abc.returnOn}</td>
				
				<td><a href="${abc.fileFullPath}"  target="_blank">${abc.fileName}</a></td>
				<td><img src='images/delete.png'   onclick="deleteTraveller(${abc.id})" title='Remove Row'/></td>
			</tr>
			</logic:iterate>
			</logic:notEmpty>
		</table>


		<table class="bordered" style="position: relative;left: 2%;width: 90%;">
			<tr><th colspan="6"><center>Traveller Details</center></th></tr>
			<tr>
				<td>Travel Type: <font color="red" size="3">*</font></td>
				<td>
					<select name="utravelmode"  onchange="travelmode1()"  id="utravelmode" >
						<option value="">--Select--</option>
				  		<option value="Road">Road</option>
						<option value="Rail">Rail</option>
						<option value="Air">Air</option>
					</select>
				</td>	
				<td>Travel Mode: <font color="red" size="3">*</font> </td>
				<td>
					<select name="traveltype" id="traveltype"  onchange="passdisplay()" >
						<option value="">--Select--</option>
					</select>
				</td>
				<td>Travellers: <font color="red" size="3">*</font> </td>
				<td>
					<select name="guest_Type" onchange="selectchange()">
						<option value="">--Select--</option>
						<option value="employee" id="g1" style="display: none">Employee</option>
						<option value="guest" id="g2" style="display: none">Guest</option>
						<option value="doctor" id="g3" style="display: none">Doctor</option>
					</select>
				</td>
			</tr>						
			<tr style="display: none" id="passport">
				<td>Passport No.: <font color="red" size="3">*</font></td><td><input type="text" name="passportno"/></td>
				<td>Passport Expiry Date: <font color="red" size="3">*</font> </td><td><input type="text" name="passportexpirydate" id="passportexpirydate" /></td>
				<td>Visa No.  </td><td><input type="text" name="guest_Visano" /></td>
			</tr>
			<tr>
				<td>Emp. No.: </td>
				<td>
					<div class="no" id="empno">
						<html:text property="guest_pernr"  onkeyup="searchEmployee(this.id)" styleId="employeeNumber1" style="width:60px; " styleClass="abc" value=""> 
      					<bean:write property="guest_pernr" name="travelRequestForm" /></html:text>
						<div id="sU" style="display:none;">
							<div id="sUTD" style="width:400px;">
								<iframe src="jsp/ess/TravelRequest/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%" ></iframe>
							</div>
						</div> 
					</div>
				</td>
				<td>Title: </td>
				<td>
					<select name="guest_Title">
						<option value="Mr">Mr</option>
						<option value="Mrs">Mrs</option>
						<option value="Dr">Dr</option>
  					</select>
				</td>
				<td>Name: </td>
				<td><input type="text" name="guestName"/></td> 
			</tr>
			<tr>
				<td>Gender: </td>
				<td>
					<div id="genderid">
						<select name="gender">
							<option value="">--Select--</option>
							<option value="M">Male</option>
							<option value="F">Female</option>
							<option value="O">Other</option>
						</select>
					</div>
				</td>			
				<td>Company Name: </td><td><input type="text" name="guest_Company"/></td>
				<td>e-Mail:</td><td><input type="text" name="email_Guest" /></td>				
			</tr>
			<tr>
				<td>Contact No: </td><td><input type="text" name="guestContactNo" maxlength="13"/></td>

				<td>DOB: </td>
				<td>
					<!--input type="text" name="guest_DOB" onkeypress="return isNumber(event)" maxlength="2" length="2" /-->
					<html:text property="guest_DOB" styleId="guest_DOB" readonly="true" onclick="datedisplay1(this.id)" onmouseover="datedisplay1(this.id)"  value=""/>
				</td>
				<td>Meal Preference: </td><td><input type="text" name="guest_Meal"/></td>
			</tr>
			<tr>
				<td>Origin: <font color="red" size="3">*</font></td>
				<td><input type="text" id="fromPlace" name="fromPlace" onkeyup="this.value = this.value.replace(/'/g,'`')" size="35"/></td>
				<td>Departure Date: <font color="red" size="3">*</font></td>
				<td><html:text property="departOn" styleId="popupDatepicker" readonly="true"  value="" onclick="datedisplay(this.id)" onmouseover="datedisplay(this.id)"/></td>&nbsp;&nbsp;
		       <%--  <td>Preferred Time: <font color="red" size="3">*</font></td>
		        <td><html:text property="departTime" styleId="timeFrom"  size="15" onchange="checkingdates()" onblur="checkingdates()" value="" />&nbsp;&nbsp</td>
		 --%>	</tr>
			<tr>
				<td>Destination: <font color="red" size="3">*</font></td>
				<td><html:text property="toPlace" styleId="toPlace"   onkeyup="this.value = this.value.replace(/'/g,'`')" value="" size="35"/></td>
				<td>Return Date: <font color="red" size="3">*</font></td>
				<td><html:text property="returnOn"   styleId="popupDatepicker2"  readonly="true"  value=""  onchange="checkingdates()"  onblur="checkingdates()"  onclick="datedisplay(this.id)" onmouseover="datedisplay(this.id)"/>
					<div id="travel_Days1">
						<input type="hidden"  name="travel_Days1"  />
					</div>
				</td>
				<%-- <td>Preferred Time: <font color="red" size="3">*</font></td>
				<td><html:text property="returnTime"  styleId="timeTo"   size="15" onchange="checkingdates()"   value="" onblur="checkingdates()" /></td>
		 --%>	</tr>
			<tr>

				<td title="PDF File format only">Attachment: </td>
				<td colspan="5" title="PDF File format only"><html:file property="documentFile" name="travelRequestForm" onchange="validatePDF(this)"/></td>
			</tr>
			<tr>
				<!-- Other Detail	s Table view start -->
				<div style="display:none " id="travelother">		
					<tr>
						<td>Accomodation: <font color="red" size="3">*</font></td>
						<td>
							<html:select name="travelRequestForm" property="hotel_Res" onchange="accodchange()" styleId="hotel_Res">
								<html:option value="No">No</html:option>
								<html:option value="Yes" >Yes</html:option>						
							</html:select>
						</td>
						<td>Accomodation Type: </td>
						<td>
							<div id="accom_type_div" class="no">
								<html:select name="travelRequestForm" property="accom_type" styleId="accom_type" onchange="hotelChange()" style="background: rgb(179,175,174)">
								<html:option value="">--Select--</html:option>
									<html:option value="Hotel">Hotel</html:option>
									<html:option value="Guest House" >Guest House</html:option>						
								</html:select>
							</div>
						</td>
						<td>Rental Car required: <font color="red" size="3">*</font></td>
						<td>
							<html:select name="travelRequestForm" property="rent_Car" styleId="rent_Car" onchange="carchange()">
								<html:option value="No">No</html:option>
								<html:option value="Yes" >Yes</html:option>						
							</html:select>
						</td>
					</tr>
					<tr>
						<td>Guest House Name</td>
						<td><input type="text" name="accom_name" id="accom_name" style="background:rgb(179,175,174)"  readonly="readonly"/></td>
						<td>Hotel Name:</td>
						<td><input type="text" name="hotel_Name" id="hotel_Name" readonly="readonly" style="background: rgb(179,175,174)"/></td>
						<td>Hotel City:</td>
						<td><input type="text" name="hotel_City" id="hotel_City" readonly="readonly" style="background: rgb(179,175,174)"/></td>
					</tr>
					<tr>
						<td>Pickup Details: </td>
						<td colspan="2"><textarea rows="2" cols="30" name="pickup_Details" id="pickup_Details" readonly="readonly" style="background: rgb(179,175,174)"></textarea></td>
						<td>Drop Details: </td>
						<td colspan="2"><textarea rows="2" cols="30" name="drop_Details" id="drop_Details" readonly="readonly" style="background: rgb(179,175,174)"></textarea></td>
					</tr>
					<tr>
						<td colspan="6"><center>
							<html:button property="method" styleClass="rounded"  value="Add" onclick="uploadDocument1();" style="align:right;width:100px;" onmouseover="checkingdates()"/>
							<%-- <html:button property="method" styleClass="rounded"  value="Add" onclick="checkingdates()" style="align:right;width:100px;" /> --%>
							</center>
						</td>
					</tr>

				</div>
			</tr>
		</table>	

</html:form>
</body>
</html>