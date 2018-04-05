<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

  <head>
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
   <%--  <base href="<%=basePath%>"> --%>
    
    
    <title>Travel Request - Requisition</title>
    
	
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
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
 <!--  <style type="text/css">
@import "jquery.timeentry.css";
</style> -->
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>



<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<script type="text/javascript">




function edit_row(no)
{
 document.getElementById("edit_button"+no).style.display="none";
 document.getElementById("save_button"+no).style.display="block";
	
 var date=document.getElementById("date_row"+no);
 
 var origin=document.getElementById("origin_row"+no);
 var dest=document.getElementById("destination_row"+no);
 var travel_type=document.getElementById(" travel_type_row"+no);
 var travel_mode=document.getElementById("travel_mode_row"+no);
 var remarks=document.getElementById("remarks_row"+no);
	
 var date_data=date.innerHTML;
 var origin_data=origin.innerHTML;
 var destination_data=dest.innerHTML;
  var travel_type_data=travel_type.innerHTML;
   var travel_mode_data=travel_mode.innerHTML; 
   var remarks_data=remarks.innerHTML;
 
  document.getElementById("date_row"+no).innerHTML="<input type='text' id='date_text"+no+"' value='"+date_data+"'>";
 origin.innerHTML="<input type='text' id='origin_text"+no+"' value='"+origin_data+"'>";
 dest.innerHTML="<input type='text' id='destination_text"+no+"' value='"+destination_data+"'>";
  travel_type.innerHTML="<input type='text' id='travel_type_text"+no+"' value='"+travel_type_data+"'>";
   travel_mode.innerHTML="<input type='text' id='travel_mode_text"+no+"' value='"+travel_mode_data+"'>";
    remarks.innerHTML="<input type='text' id='remarks_text"+no+"' value='"+remarks_data+"'>"; 
    
}

function save_row(no)
{
 var date_val=document.getElementById("date_text"+no).value;
 var origin_val=document.getElementById("origin_text"+no).value;
 var destination_val=document.getElementById("destination_text"+no).value; 
 var travel_type_val=document.getElementById("travel_type_text"+no).value;
  var travel_mode_val=document.getElementById("travel_mode_text"+no).value;  
  var remarks_val=document.getElementById("remarks_text"+no).value;

 document.getElementById("date_row"+no).innerHTML=date_val;
 document.getElementById("origin_row"+no).innerHTML=origin_val;
 document.getElementById("destination_row"+no).innerHTML=destination_val;
document.getElementById("travel_type_row"+no).innerHTML=travel_type_val;
document.getElementById("travel_mode_row"+no).innerHTML=travel_mode_val;
document.getElementById("remarks_row"+no).innerHTML=remarks_val;
 document.getElementById("edit_button"+no).style.display="block";
 document.getElementById("save_button"+no).style.display="none";
}

function delete_row(no)
{
 document.getElementById("row"+no+"").outerHTML="";
}

function add_row()
{
 var new_date=document.getElementById("date1").value;
 var new_origin=document.getElementById("new_origin").value;
 var new_destination=document.getElementById("new_destination").value;
  var new_travel_type=document.getElementById("new_travel_type").value;
   var new_travel_mode=document.getElementById("new_travel_mode").value;
    var new_remarks=document.getElementById("new_remarks").value;
	
 var table=document.getElementById("data_table");
 var table_len=(table.rows.length)-1;
 var row = table.insertRow(table_len).outerHTML="<tr id='row"+table_len+"'><td id='date_row"+table_len+"'><input type='text' value="+new_date+"></td><td id='origin_row"+table_len+"'><input type='text' value="+new_origin+"></td><td id='destination_row"+table_len+"'><input type='text' value="+new_destination+"></td><td id='travel_type_row"+table_len+"'><input type='text' value="+new_travel_type+"></td><td id='travel_mode_row"+table_len+"'><input type='text' value="+new_travel_mode+"></td><td id='remarks_row"+table_len+"'><input type='text' value="+new_remarks+"></td><td><input type='button' id='edit_button"+table_len+"' value='Edit' class='edit' onclick='edit_row("+table_len+")'> <input type='button' id='save_button"+table_len+"' value='Save' class='save' onclick='save_row("+table_len+")'> <input type='button' value='Delete' class='delete' onclick='delete_row("+table_len+")'></td></tr>";
alert(table_len);
 document.getElementById("date1").value="";
 document.getElementById("new_origin").value="";
 document.getElementById("new_destination").value="";
  document.getElementById("new_travel_type").value="";
 document.getElementById("new_travel_mode").value="";
 document.getElementById("new_remarks").value="";
}




	
	function editpage(id)
	{
	
	 window.showModalDialog("travelrequest.do?method=editMyMonthlyPlanrequest&reqId="+id, "dialogwidth=100;dialogheight=20; center:yes;toolbar=no");
	}
	
	
	function checkingdatesplans(){
		
	
   var pdate=document.getElementsByName("mdate")[0].value;
	var pldate=document.getElementById('date1').value;
	if (pdate==pldate){
    
    alert("Select another date");
    $('#date1').val("");
    }
	
	}



	function popupCalender(param)
	{
	
	
		var toD = new Date();
		var cal = new Zapatec.Calendar.setup({
		inputField : param, // id of the input field
		singleClick : true, // require two clicks to submit
		ifFormat : "%d/%m/%Y", // format of the input field
		showsTime : false, // show time as well as date
		button : "button2", // trigger button
		});
		
	}

function travelmode21()
{
var xmlhttp;
var dt;
dt=document.forms[0].travelmodex.value;
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
    document.getElementById("traveltype11").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","travelrequest.do?method=travelmode&type="+dt,true);
xmlhttp.send();
}
}





function insRow()
{
   
    var x=document.getElementById('investmentTable');
    var new_row = x.rows[1].cloneNode(true);
    var len = '';       
    var elements =document.getElementsByName("date");
    var reqlen = elements.length;      	
    len =parseInt(elements[reqlen-1].id.substr(elements[reqlen-1].id.indexOf(":") + 1))+1;
    
    new_row.cells[0].innerHTML = len;
    
    var inp1 = new_row.cells[1].getElementsByTagName('input')[0];
    inp1.id = 'date:'+len;
     inp1.name = 'date';
      inp1.value = '';
  /*   var inp2 = new_row.cells[2].getElementsByTagName('select')[0];
    inp2.id = 'InvestmentDesc'+len;
    inp2.name = 'InvestmentDesc';
    inp2.value = ''; */
     var inp9 = new_row.cells[2].getElementsByTagName('input')[0];
    inp9.id = 'invtype'+len;
    inp9.name = 'invtype';
    inp9.value = '';
    
    var inp3 = new_row.cells[3].getElementsByTagName('input')[0];
    inp3.id = 'receiptNo'+len;
    inp3.name = 'receiptNo';
    inp3.value = '';
      var inp4 = new_row.cells[4].getElementsByTagName('select')[0];
    inp4.id = 'travelmodemain'+len;
    inp4.name = 'travelRequestForm';
    inp4.value = '';
    
    var inp5 = new_row.cells[5].getElementsByTagName('select')[0];
    inp5.id = 'traveltype11'+len;
    inp5.name ='traveltype';
    inp5.value = '';
     
     var inp7 = new_row.cells[6].getElementsByTagName('textarea')[0];
    inp7.id = 'remarks'+len;
    inp7.name = 'remarks';
    inp7.value = '';
    
    x.appendChild( new_row );
   
   
 

    document.getElementById('levelNo').value=len;
 
}




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

$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#popupDatepicker10').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	$('#inlineDatepicker10').datepick({onSelect: showDate});
	
	
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
	
	
}); */


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
var agree = confirm('Sure you want to delete the selected file ?');
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
	
	var mul = document.getElementById("Multi_City1").value;
  

	if(mul!="Multi-City1")
	{
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
	
	alert("Please select Service Class");
	document.forms[0].service_class.focus();
	$('#open2').click();
	return false;
	
	}
	
	
	if(document.forms[0].traveltype.value=="International")
	{
	if(document.forms[0].documentFile.value=="")
	{
	alert("Please Upload File");
	return false;
	}
	}
	
	if(document.forms[0].purposetype.value=="")
	{
	
	alert("Please select Purpose");
	document.forms[0].purposetype.focus();
	$('#open2').click();
	return false;
	
	}
	
	
	if(document.forms[0].purposetype.value=="Plant Visit")
	{
		if(document.forms[0].locid.value=="")
		{
		alert("Please select Plant Visit Location");
		document.forms[0].locid.focus();
		$('#open2').click();
		return false;
		
		}
	
	}
	
	if(document.forms[0].travelRequestFor.value=="")
	{
	
	alert("Please select Travel Request for ");
	document.forms[0].travelRequestFor.focus();
	$('#open2').click();
	return false;
	
	}
	
	if(document.forms[0].travelFor.value=="")
	{
	
	alert("Please select Travel for ");
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
	
	alert("Please enter Trip Departure Date ");
	document.forms[0].departOn.focus();
	$('#open2').click();
	return false;
	
	}
	
		if(document.forms[0].departTime.value=="")
	{
	
	alert("Please enter Preferred Departure Time ");
	document.forms[0].departTime.focus();
	$('#open2').click();
	return false;
	
	}
	
	if(document.forms[0].travelRequestFor.value!="One Way")
{
		if(document.forms[0].toPlace.value=="")
	{
	
	alert("Please enter Destination ");
	document.forms[0].toPlace.focus();
	$('#open2').click();
	return false;
	
	}
}
	/* 	if(document.forms[0].travelRequestFor.value=="Round Trip"|| document.forms[0].travelRequestFor.value=="Multi-City")
	{ */
		if(document.forms[0].returnOn.value=="")
	{
	
	alert("Please enter Trip Return On ");
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
		alert("Please select valid Trip Start & End Date");
		return false;
		
		}
		
		if(document.forms[0].returnTime.value=="")
		{
	
		alert("Please enter Trip Return Time ");
		document.forms[0].returnTime.focus();
		$('#open2').click();
		return false;
	
		}
	/* 
	
	if(document.forms[0].airline_Just.value=="")
		{
			alert("Please Enter Justification ");
			document.forms[0].airline_Just.focus();
			$('#open2').click();
			return false;
		
		} */
	
	if(document.forms[0].travelFor.value=="Self" && document.forms[0].travelRequestFor.value!="Multi-City" && document.forms[0].travelRequestFor.value!="Multi-City1")
	{
	
	
	
	if(document.forms[0].hotel_Res.value=="")
	{
	
	alert("Please Select Accomodation required or not ");
	document.forms[0].hotel_Res.focus();
	$('#open4').click();
	return false;
	
	}
	
	
	if(document.forms[0].hotel_Res.value=="Yes")
	{
	
	
	if(document.forms[0].accom_type.value=="")
	{
	
	alert("Please Select  Accomodation Type ");
	document.forms[0].accom_type.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].accom_type.value=="Hotel")
	{
	
	if(document.forms[0].hotel_Name.value=="")
	{
	
	alert("Please enter Hotel Name");
	document.forms[0].hotel_Name.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].hotel_City.value=="")
	{
	
	alert("Please enter Hotel City");
	document.forms[0].hotel_City.focus();
	$('#open4').click();
	return false;
	
	}
	}
	
	
	if(document.forms[0].accom_type.value=="Guest House")
	{
	
	if(document.forms[0].accom_name.value=="")
	{
	
	alert("Please enter Accomodation Name");
	document.forms[0].accom_name.focus();
	$('#open4').click();
	return false;
	
	}
	
	}
	
	
	
	
	}
	
	
	if(document.forms[0].rent_Car.value=="")
	{
	alert("Please select Rental Car required or not");
	document.forms[0].rent_Car.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].rent_Car.value=="Yes")
	{
	if(document.getElementById("pickup_Details").value =="")
	{
	alert("PLease enter pickup details");
	return false;
	}
	if(document.getElementById("drop_Details").value =="")
	{
	alert("PLease enter drop details");
	return false;
	}
	
	}
	}
	
	if(document.forms[0].onduty_Req.value=="")
	{
	alert("Please select On-Duty required or not ");
	document.forms[0].onduty_Req.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].onduty_Req.value=="Yes")
	{
	
	if(document.forms[0].trip_From_Date.value=="")
	{
	
	alert("Please enter Trip Start Date ");
	document.forms[0].trip_From_Date.focus();
	$('#open4').click();
	return false;
	
	}
	
	
	if(document.forms[0].trip_From_Time.value=="")
	{
	
	alert("Please enter Trip Start Time ");
	document.forms[0].trip_From_Time.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].trip_To_Date.value=="")
	{
	
	alert("Please enter Trip End Date ");
	document.forms[0].trip_To_Date.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].trip_To_time.value=="")
	{
	alert("Please enter Trip End Time ");
	document.forms[0].trip_To_time.focus();
	$('#open4').click();
	return false;
	}
	
	}
	
	
	
	if(document.forms[0].trip_Advance.value=="")
	{
	
	alert("Please select Trip Advance required or not ");
	document.forms[0].trip_Advance.focus();
	$('#open4').click();
	return false;
	
	}
	
	if(document.forms[0].trip_Advance.value=="Yes")
	{
	
	if(document.getElementById("trip_Amt").value =="")
	{
	alert("Please enter Trip Amount");
	return false;
	}
	if(document.getElementById("trip_Currency").value =="")
	{
	alert("Please select Currency");
	return false;
	}
	
	}
	
	
	var i =parseFloat(document.forms[0].travel_Adult.value)+parseFloat( document.forms[0].travel_Child.value)+ parseFloat(document.forms[0].travel_Infant.value);
	var j=document.getElementById("the_iframe").value;
	/* 	alert("");
	var k=document.getElementById("multiple").contentDocument.getElementById("test").value; */
	
	/* if(document.forms[0].travelRequestFor.value=="Multi-City")
	{
	if(document.forms[0].mloc.value=="")
	{

		alert("Please Fill MultiCity Form");
	return false;
	}
	
	} */
	if(document.forms[0].travelRequestFor.value=="Multi-City")
	{
	$content = $("#mcity11").html();

	if($content == "")
	{
  // yes it is empty
  	alert("Please Fill multi city form");
  return false;
}

/* if(document.forms[0].travelRequestFor.value=="Multi-City1")
	{
	$content = $("#plandate").html();

	if($content == "")
	{
  // yes it is empty
  	alert("Please Fill Travel plan form");
  return false;
}
} */

}
	if(document.forms[0].travelFor.value!="Self")
	{
	if(j>0)
	{
	}
	else
	{
	alert("Please add Travellers");
	return false;
	}
	if(document.forms[0].travelRequestFor.value!=="Multi-City1")
	{
	if(i==j)
	{
	}
	else
	{
	alert("No of Traveller Must Match with added user list");
	return false;
	}
	}
  
	}
	}
	
	if(mul=="Multi-City1")
		{

if(document.forms[0].travelmode.value=="")
	{
	
	alert("Please select Travel Mode");
	document.forms[0].travelmode.focus();
	$('#open2').click();
	return false;
	
	}

if(document.forms[0].traveltype.value=="")
	{
	
	alert("Please select Travel Type");
	document.forms[0].traveltype.focus();
	$('#open2').click();
	return false;
	
	}


if(document.forms[0].travel_desk_type.value=="")
	{
	
	alert("Please select Travel desk Type");
	document.forms[0].travel_desk_type.focus();
	$('#open2').click();
	return false;
	
	}

if(document.forms[0].service_class.value=="")
	{
	
	alert("Please select Service Class");
	document.forms[0].service_class.focus();
	$('#open2').click();
	return false;
	
	}





if(document.forms[0].traveltype.value=="International")
	{
	if(document.forms[0].documentFile.value=="")
	{
	alert("Please Upload File");
	return false;
	}
	}


}

	
    var retVal = confirm("Travel Confirmation\nDo you want to submit Travel request for Approval ?");
               if( retVal == true )
               {
                 var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	document.forms[0].action="travelrequest.do?method=saveDomestic";
	document.forms[0].submit();
                  return true;
               }
               else{
                 
                  return false;
               }
        
	
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

	
	 
	document.forms[0].action="travelrequest.do?method=displayDomestic";
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
		alert("Please select Request Type");
		document.forms[0].reqType.value.focus();		
		return false;
	}
	if(usertype == ""){
		alert("Please select User Type");
		document.forms[0].usertype.value.focus();	
		return false;
	}
	
	if(value=="Add Employee")
	{
	if(toadd == ""){
		alert("Please enter Employee No.");
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
{pointer-events:"none"; 
}

</style>
<script>
function addMultiplecityplan()
{

if($('#date1').val()=="")
{
alert("Select Date");
$('#date1').focus();
return false;
}
if($('#morigin1').val()=="")
{
alert("Select  Origin to Travel");
$('#morigin1').focus();
return false;
}


if($('#mdestination').val()=="")
{
alert("Select Destination");
$('#mdeparture1').focus();
return false;
}


if($('#travelmode2').val()=="")
{
alert("Select Travel Mode");
$('#travelmodemain').focus();
return false;
}

if($('#traveltype2').val()=="")
{
alert("Select Travel Type");
$('#traveltype1').focus();
return false;
}
if($('#remarks1').val()=="")
{
alert("Select  Remarks");
$('#remarks1').focus();
return false;
}


if($('#date1').val()==$('#pdate2').val())
{
alert("Select Date");
$('#date1').focus();
return false;
}

var table = document.getElementById('mcityplan');
	var row   = table.insertRow(2);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);
	var cell7 = row.insertCell(6);
	
    cell1.innerHTML=$('#date1').val();
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#date1').val();
    element1.name="mdate";
    cell1.appendChild(element1);
    $('#date1').val("");
       
    cell2.innerHTML=$('#morigin1').val();
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#morigin1').val();
    element1.name="morigin11";
    cell2.appendChild(element1);
    $('#morigin1').val("");
    
    
    cell3.innerHTML=$('#mdestination').val();
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#mdestination').val();
    element1.name="mdestination1";
    cell3.appendChild(element1);
    $('#mdestination').val("");
    
		
    
    cell4.innerHTML=$('#travelmode21').val();
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#travelmode21').val();
    element1.name="mtravelmode1";
    cell4.appendChild(element1);
    $('#travelmode21').val("");
    
    cell5.innerHTML=$('#traveltype21').val();
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#traveltype21').val();
    element1.name="mtraveltype1";
    cell5.appendChild(element1);
    $('#traveltype21').val("");
    
    
    cell6.innerHTML=$('#remarks1').val();
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=$('#remarks1').val();
    element1.name="mremarks1";
    cell6.appendChild(element1);
    $('#remarks1').val("");
       
        
    cell7.innerHTML="<img src='images/delete.png' onclick='deleteRow2(this,1)' title='Delete32'/>"; 
    
	/* cell23.innerHTML="<img src='images/delete.png' onclick='deleteRow1(this,1)' title='Delete'/>"; */		
/* 	cell7.innerHTML="<img src='images/delete.png' onclick='deleteRow2(this,1)' title='Delete1'/>";
    */     
    maccodchange();

	originmuliticty();

}

function deleteRow2(element,value)
{

var table="";
	table = document.getElementById("mcityplan");
	table.deleteRow(element.parentNode.parentNode.rowIndex);
}

function addMultiplecity()
{
alert('hi');
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
	alert("Please enter Passport Expiry Date");
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
    cell14.appendChild(element1);
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

	 if(cityName=="R2"||cityName=="R4")
    {
    
    updatedate();
    
    checkingdates();
    originmuliticty();
    
    if(document.forms[0].departOn.value=="")
    {
    alert("Please select Trip Departure Date");
    return false;
    }
    
    
     if(document.forms[0].returnOn.value=="")
    {
    alert("Please select Trip Return Date");
    return false;
    }
    
    if(document.forms[0].travelRequestFor.value=="")
    {
    alert("Please select Travel Request For");
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
 
 if(document.forms[0].travelRequestFor.value=="One Way")
 {
 document.getElementById("popupDatepicker2").disabled=true;
  document.getElementById("timeTo").disabled=true;
 /*  document.getElementById("toPlace").disabled=true; */
  
  /* document.getElementByID("popupDatepicker2").style.backgroundColor = "grey";    
  document.getElementByID("timeTo").style.backgroundColor = "grey";     */
  document.getElementById("popupDatepicker2").style.backgroundColor = "DarkGrey";
  document.getElementById("timeTo").style.backgroundColor = "DarkGrey";
  /*  document.getElementById("toPlace").style.backgroundColor = "DarkGrey"; */
 
 }
 if(document.forms[0].travelRequestFor.value=="Round Trip")
 {
 document.getElementById("popupDatepicker2").disabled=false;
 document.getElementById("timeTo").disabled=false;
/*    document.getElementById("toPlace").disabled=false; */
 document.getElementById("popupDatepicker2").style.backgroundColor = "";
  document.getElementById("timeTo").style.backgroundColor = "";
  /*  document.getElementById("toPlace").style.backgroundColor = ""; */
 }
 if(document.forms[0].travelRequestFor.value=="Multi-City")
 {
 document.getElementById("popupDatepicker2").disabled=false;
 document.getElementById("timeTo").disabled=false;
  /*  document.getElementById("toPlace").disabled=false; */
 document.getElementById("popupDatepicker2").style.backgroundColor = "";
  document.getElementById("timeTo").style.backgroundColor = "";
   /* document.getElementById("toPlace").style.backgroundColor = ""; */
 }
 
 if(document.forms[0].travelRequestFor.value=="Multi-City1")
 {
 document.getElementById("popupDatepicker2").disabled=false;
 document.getElementById("timeTo").disabled=false;
  /*  document.getElementById("toPlace").disabled=false; */
 document.getElementById("popupDatepicker2").style.backgroundColor = "";
  document.getElementById("timeTo").style.backgroundColor = "";
   /* document.getElementById("toPlace").style.backgroundColor = ""; */
 }

document.getElementById("triptype").value=document.forms[0].travelRequestFor.value;

if(document.forms[0].travelFor.value=="On Behalf"||document.forms[0].travelFor.value=="Guest")
{
document.getElementById("contactinfo").style.display="";
}
else
{
document.getElementById("contactinfo").style.display="none";
}

if(document.forms[0].travelFor.value=="On Behalf")
{

}

/* if(document.forms[0].travelRequestFor.value=="One Way")
{
 document.getElementById("destDisplay").style.display="none";
}
else
{
document.getElementById("destDisplay").style.display="";
}  */
 
 
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

if(document.forms[0].travelRequestFor.value=="Multi-City1")
{
document.getElementById("open6").style.display="";
}
else
{
document.getElementById("open6").style.display="none";
}

if(document.forms[0].travelFor.value=="Guest" || document.forms[0].travelFor.value=="Multiple" || document.forms[0].travelFor.value=="On Behalf")
{
document.getElementById("open3").style.display="";

}
else
{
document.getElementById("open3").style.display="none";
}

if((document.forms[0].travelFor.value=="Self") && (document.forms[0].travelRequestFor.value=="Multi-City")&& (document.forms[0].travelRequestFor.value=="Multi-City1") )
{
document.getElementById("self_Multiple").style.display="none";
} 

if(document.forms[0].travelFor.value=="Multiple" && document.forms[0].travelRequestFor.value=="Multi-City" && document.forms[0].travelRequestFor.value=="Multi-City1" )
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
function change()
{
tabchange();
if(document.forms[0].travelmode.value!="")
  {
   travelmode2();
   travelmode3();
   travelmode4();
  }
 $('#open2').click(); 

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
selfdate();
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


function maccodchange()
{
if(document.forms[0].mhotel_Res.value=="Yes")
{
document.getElementById("maccom_type").style.background="";
document.getElementById("maccom_type_div").className = "";
}
else
{
document.getElementById("maccom_type").style.background="rgb(179,175,174)";
document.getElementById("maccom_type_div").className = "no";
document.getElementById("maccom_type").value ="";

document.getElementById("maccom_name").style.background="rgb(179,175,174)";
document.getElementById("maccom_name").readOnly =true;
document.getElementById("maccom_name").value ="";


document.getElementById("mhotel_Name").style.background="rgb(179,175,174)";
document.getElementById("mhotel_City").style.background="rgb(179,175,174)";
document.getElementById("mhotel_Name").readOnly =true;
document.getElementById("mhotel_City").readOnly =true;
document.getElementById("mhotel_Name").value ="";
document.getElementById("mhotel_City").value ="";

}


}


function mhotelChange()
{



if(document.forms[0].maccom_type.value!="")
{

if(document.forms[0].maccom_type.value=="Hotel")
{
document.getElementById("mhotel_Name").style.background="";
document.getElementById("mhotel_City").style.background="";
document.getElementById("mhotel_Name").readOnly =false;
document.getElementById("mhotel_City").readOnly =false;

document.getElementById("maccom_name").style.background="rgb(179,175,174)";
document.getElementById("maccom_name").readOnly =true;
document.getElementById("maccom_name").value ="";

}
else
{
document.getElementById("mhotel_Name").style.background="rgb(179,175,174)";
document.getElementById("mhotel_City").style.background="rgb(179,175,174)";
document.getElementById("mhotel_Name").readOnly =true;
document.getElementById("mhotel_City").readOnly =true;
document.getElementById("mhotel_Name").value ="";
document.getElementById("mhotel_City").value ="";

document.getElementById("maccom_name").style.background="";
document.getElementById("maccom_name").readOnly =false;
document.getElementById("maccom_name").value ="";

}
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

function selfdate()
{

if(document.forms[0].travelRequestFor.value=="One Way")
{


 if(document.getElementById("popupDatepicker2").value=="")
{
document.getElementById("popupDatepicker2").value=document.getElementById("popupDatepicker").value;
}
if(document.getElementById("timeTo").value=="")
{
document.getElementById("timeTo").value=document.getElementById("timeFrom").value;
}
/* if(document.forms[0].toPlace.value=="")
{
document.forms[0].toPlace.value=document.forms[0].fromPlace.value;
} */

} 
}


function checkingdates()
{
selfdate();

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



function travelmode21()
{
var xmlhttp;
var dt;
dt=document.forms[0].travelmodex.value;
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
    document.getElementById("traveltype11").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","travelrequest.do?method=travelmode&type="+dt,true);
xmlhttp.send();
}
}



function travelmode2()
{
var xmlhttp;
var dt;
dt=document.forms[0].travelmode.value;
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

//travel desk 
function travelmode3()
{

var xmlhttp;
var dt;
dt=document.forms[0].travelmode.value;

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
    document.getElementById("travel_desk_type").innerHTML=xmlhttp.responseText;
    }
  }

xmlhttp.open("POST","travelrequest.do?method=travelDesk&type="+dt,true);
xmlhttp.send();
}
}

function travelmode4()
{

var xmlhttp;
var dt;
dt=document.forms[0].travelmode.value;

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
    document.getElementById("service_class").innerHTML=xmlhttp.responseText;
    }
  }

xmlhttp.open("POST","travelrequest.do?method=serviceclass&type="+dt,true);
xmlhttp.send();
}
}


function travelmode5()
{
var xmlhttp;
var dt;
dt=document.forms[0].travelmode1.value;
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
    document.getElementById("traveltype1").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","travelrequest.do?method=travelmode&type="+dt,true);
xmlhttp.send();
}
}

function travelmode6()
{
var xmlhttp;
var dt;
dt=document.forms[0].travelmode21.value;
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
    document.getElementById("traveltype21").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","travelrequest.do?method=travelmode&type="+dt,true);
xmlhttp.send();
}
}

function travelmode61()
{

var xmlhttp;
var dt;
dt=document.getElementById("travelmode211").value;
alert(dt);
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
    document.getElementById("traveltype211").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","travelrequest.do?method=travelmode&type="+dt,true);
xmlhttp.send();
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
	
	$('#'+id).datepick({dateFormat: 'dd/mm/yyyy',minDate:date1,maxDate:date2,autoclose: true});
	$('#inlineDatepicker').datepick({onSelect: showDate,autoclose: true});
	
	
}

function datedisplay1(id)
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
	
	$('#'+id).datepick({dateFormat: 'dd/mm/yyyy',minDate:date1,maxDate:date2,autoclose: true});
	$('#inlineDatepicker').datepick({onSelect: showDate,autoclose: true});
	
	
}

$(function() {
	$('#mpassportexpirydate').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});


$(function() {
	$('#date1').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

function passdisplay()
{

	passdisplay1();
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


		function passdisplay1()
	{


	if(document.getElementById("traveltype").value=="International")
	{
	document.getElementById("passport1").style.display="";
	}
	else
	{
	document.getElementById("passport1").style.display="none";
	}

}


function travelmode2()
{
var xmlhttp;
var dt;
dt=document.forms[0].travelmode.value;
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
		<html:hidden property="oldrequestNumber"  name="travelRequestForm"/>
		
		<input type="hidden" id="triptype" value=""/>
		
		<div style="display: none">
		<html:button property="method" styleClass="rounded" styleId="userbtn" value="userbtn" onclick="getUser()" />
		</div>
		<div id="masterdiv" class="">
			<ul class="tab" style="width: 1025px; margin-left: 22px" >
			<!-- 	<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'Recruitment')" id="defaultOpen">Requistioner detail&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li> -->
				<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R1')" id="open2">Trip Info.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
				<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R2')" id="open3" style="display: none ">Traveller's List.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
				<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R4')" id="open5" style="display: none">Multi City List&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
				<li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R5')" id="open6" style="display: none">Monthly Tour Plan &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
				
				<!-- <li><a href="javascript:void(0)" class="tablinks" onclick="openCity(event, 'R3')" id="open4">Other Details&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li> -->
			</ul>

			<!-- <div id="Recruitment" class="tabcontent" style="width: 1000px; margin-left: 22px" >
			
			</div> -->

			<div id="R1" class="tabcontent" style="width: 1000px; margin-left: 22px" >
			
				<table class="bordered" style="position: relative;left: 2%;width: 90%;">
					<tr><th colspan="8" align="center"><center> Travel Requisition </center></th></tr>
					<tr>
						<td>Mode: <font color="red" size="3">*</font></td>
						<td>
						<html:select name="travelRequestForm" property="travelmode" styleClass="text_field" onchange="travelmode2(),travelmode3(),travelmode4()" styleId="travelmodemain">
								<html:option value="">--Select--</html:option>
								<html:option value="Road">Road</html:option>
								<html:option value="Rail">Rail</html:option>
								<html:option value="Air">Air</html:option>
						</html:select>
						
							

						</td>	
						<td>Type: <font color="red" size="3">*</font> </td>
						<td>
							<select name="traveltype" id="traveltype" onchange="passdisplay1()" >
								<option value="">--Select</option>
							</select>
						</td>
						<td>Travel Desk: <font color="red" size="3">*</font></td>
						<td>
							<select name="travel_desk_type" id="travel_desk_type"  >
								<option value="">--Select</option>
							</select>						
						</td>
						<td>Class: <font color="red" size="3">*</font></td>
						<td>
							<select name="service_class" id="service_class" >
								<option value="">--Select</option>
							</select>
						</td>
					</tr>
					
					<tr id="contactinfo" style="display: none">
						<td>Contact Name: </td>
						<td><input type="text" name="p_name" onkeyup="this.value = this.value.replace(/'/g,'`')" value="${travelRequestForm.p_name}" /> </td>
						<td>Phone: </br>Email: </td>
						<td><input type="text" name="p_cont" onkeyup="this.value = this.value.replace(/'/g,'`')" value="${travelRequestForm.p_cont}" /> </br></td>
						<td>Official id: <font color="red" size="3">*</font></td>
						<td><input type="text" name="p_email" onkeyup="this.value = this.value.replace(/'/g,'`')" value="${travelRequestForm.p_email}" /> </td>
						<td>Personal id: <font color="red" size="3">*</font></td>						
						<td bgcolor="#d3d3d3">${travelRequestForm.userPersonalmailId}</td>
					</tr>
					<tr style="display:none; " id="passport1">
						<td>Passport No: <font color="red" size="3">*</font></td>
						<td bgcolor="#d3d3d3">${travelRequestForm.userpassportno}</td>
						<td>Place of issue:<font color="red" size="3">*</font></td>
						<td bgcolor="#d3d3d3">${travelRequestForm.userpassportplace}</td>
						<td>Date of issue: <font color="red" size="3">*</font></td>
						<td bgcolor="#d3d3d3">${travelRequestForm.userpassportissuedate}</td>
						<td>Date of expiry: <font color="red" size="3">*</font></td>
						<td bgcolor="#d3d3d3">${travelRequestForm.userpassportexpirydate}</td>
					</tr>
		            <tr style="display:none; " id="addtraveller">
						<td colspan="8">
							<center><html:button property="method" styleClass="rounded"  value="Add Employee" onclick="addtraveler(this.value);" style="align:right;width:100px;"/></center>
						</td>
					</tr>
		            <tr style="display:none; " id="addguest">
						<td colspan="8">
							<center> <html:button property="method" styleClass="rounded"  value="Add Guest" onclick="addtraveler(this.value);" style="align:right;width:100px;"/></center>
						</td>
					</tr>
		        </table>
			
				<table  class="bordered" style="position: relative;left: 2%;width: 90%; ">
<tr>
					<td  align="right">Request For: <font color="red" size="3">*</font></td>
					<td colspan="2">
						<html:radio property="travelRequestFor" value="One Way" onclick="tabchange()" styleId="One_Way"></html:radio>One Way &nbsp;
						<html:radio property="travelRequestFor" value="Round Trip" onclick="tabchange()" styleId="Round_Trip"></html:radio>Round Trip &nbsp;
						<html:radio property="travelRequestFor" value="Multi-City" onclick="tabchange()" styleId="Multi_City"></html:radio>Multi City<br/>
						<html:radio property="travelRequestFor" value="Multi-City1" onclick="tabchange()" styleId="Multi_City1"></html:radio>Monthly Tour Plan
					</td>
					
					 
					<td 
					title=" SELF: Only you are travelling&#013; ON BEHALF: Booking for another employee&#013; GUEST: Booking for external persons like doctors, customers etc.,&#013; MULTIPLE: Booking for employee and external persons&#013;">
					Travel For: <font color="red" size="3">*</font> &nbsp;
					
					<html:select name="travelRequestForm" property="travelFor" styleClass="text_field" onchange="tabchange()"  styleId="travelFor">
						<html:option value="Self" >Self</html:option>
						<html:option value="On Behalf" >On Behalf</html:option>
						<html:option value="Guest" >Guest</html:option>
						<html:option value="Multiple" >Multiple</html:option>
						</html:select>
					
					</td>
					<td colspan="4" title=" ADULT: +12 yrs&#013; CHILDREN: 2-12 yrs&#013; INFANT: 0-2 yrs&#013;">No of Travellers:
					
					<div class="no" id="noOfTravel">
					<label title="+12 yrs" >Adult:</label> <input type="text" title="+12 yrs" style="width: 20px;" name="travel_Adult" value="${travelRequestForm.travel_Adult}" onkeypress="return isNumber(event)" id="travel_Adult"  maxlength="1"/> &nbsp;
					<label title="2-12 yrs" >Children:</label> <input type="text" title="2-12 yrs" style="width: 20px" name="travel_Child" value="${travelRequestForm.travel_Child}"  onkeypress="return isNumber(event)" id="travel_Child" maxlength="1"/> &nbsp; 
					<label title="0-2 yrs" >Infant:</label> <input type="text" title="0-2 yrs" style="width: 20px" name="travel_Infant" value="${travelRequestForm.travel_Infant}" onkeypress="return isNumber(event)" id="travel_Infant" maxlength="1"/>
					</div>
					</td>
			
				</tr>
				<tr>
					<td>Origin: <font color="red" size="3">*</font></td>
					<td >
					<input type="text" name="fromPlace" onkeyup="this.value = this.value.replace(/'/g,'`')" size="30" id="fromPlace" value="${travelRequestForm.fromPlace}"/>
					</td>
					<td>Departure Date: <font color="red" size="3">*</font></td>
					<td><html:text property="departOn" styleId="popupDatepicker" readonly="true" onchange="updatedate(),checkingdates()" onmouseover="updatedate(),checkingdates()"  onclick="updatedate(),checkingdates()" onblur="updatedate(),checkingdates()" /></td>&nbsp;&nbsp;
		            <td>Preferred Time: <font color="red" size="3">*</font></td>
		            <td>
		            <html:text property="departTime" styleId="timeFrom"  size="15" onchange="updatedate()"  onblur="updatedate()" />&nbsp;&nbsp</td>
				</tr>	
		        <tr id="destDisplay" >
					<td>Destination: <font color="red" size="3">*</font></td>
					<td ><html:text property="toPlace"   size="30"  onkeyup="this.value = this.value.replace(/'/g,'`')" /></td>
					<td>Return On: <font color="red" size="3">*</font></td>
					<td><html:text property="returnOn"   styleId="popupDatepicker2" readonly="true" onchange="updatedate(),checkingdates()" onmouseover="updatedate(),checkingdates()"  onclick="updatedate(),checkingdates()"  onblur="updatedate(),checkingdates()" value=""/></td>
					<td>Preferred Time: <font color="red" size="3">*</font></td>
					<td><html:text property="returnTime"  styleId="timeTo"   size="15" onchange="updatedate(),checkingdates()" onblur="updatedate(),checkingdates()" />
					<div id="travel_Days1">
					<input type="hidden"  name="travel_Days1"   />
					</div>
					<input type="hidden"  name="travel_Days" readonly="readonly" style="background: rgb(179,175,174)" id="travel_Days" value="${travelRequestForm.travel_Days}"/>
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
					
					
					<td>Purpose of Travel <font color="red" size="3">*</font></td>	
					<td colspan=""> <textarea rows="2" cols="20" name="purposetext">${travelRequestForm.purposetext}</textarea></td>
					
					<td>Location: <font color="red" size="3">*</font></td>
					<td>
					
					
					<html:select  property="locid" name="travelRequestForm" >
						<html:option value="">--Select--</html:option>
						<html:options name="travelRequestForm"  property="locationIdList" labelProperty="locationLabelList"/>
				</html:select>
					
					</td>
				
				</tr>
				
				<tr>
						
											
				</tr>
		        
		        <tr>
					<td>Sponsoring Division: </td>
					<td> 
					<html:select  property="spon_div" name="travelRequestForm" >
					<html:option value="">--Select--</html:option>
					<html:options name="travelRequestForm"  property="divIdList" labelProperty="divLabelList"/>
					</html:select>
					
					</td>
					<td>Budget Code: </td>
					<td><input type="text" name="bud_code" onkeypress="return isNumber(event)" value="${travelRequestForm.bud_code}"/></td>
					<td>Estimated Trip Cost: </td>
					<td colspan="2"><input type="text" name="est_trip_cose" onkeypress="return isNumber(event)" value="${travelRequestForm.est_trip_cose}"/></td>
				</tr>
				<tr>
					<td>Trip Advance required: <font color="red" size="3" >*</font></td>
					<td>
					<html:select name="travelRequestForm" property="trip_Advance" onchange="trip()">
							<html:option value="No">No</html:option>
							<html:option value="Yes">Yes</html:option>
						</html:select>
					</td>
					
					<td>Amount:</br>
						Currency:
					</td>
					<td colspan="4"><input type="text" name="trip_Amt" onkeypress="return isNumber(event)" id="trip_Amt" length="5" readonly="readonly" style="background: rgb(179,175,174)" value="${travelRequestForm.trip_Amt}"/></br>
					<div class="no" id="trip_Currency_div"> 
					 <html:select  property="trip_Currency" name="travelRequestForm"  styleId="trip_Currency" style="background: rgb(179,175,174)"  >
					<html:option value="">--Select--</html:option>
					<html:options name="travelRequestForm"  property="currIdList" labelProperty="currLabelList"/>
					</html:select>
					</div> 
					<div style="display: none">
					<html:select name="travelRequestForm" property="onduty_Req" onchange="ondutyupdate()" styleId="onduty_Req"  >
						<html:option value="">--Select--</html:option>
						<html:option value="Yes" >Yes</html:option>
						<html:option value="No">No</html:option>
						</html:select>
					</div>
					</td>
					
					
					</tr>
					<tr>
					<td title="PDF File format only">Attachment: </td>
					<td colspan="6" title="PDF File format only"><html:file property="documentFile" name="travelRequestForm" onchange="validatePDF(this)"/>
					<br/>
					<b>Note:</b>
					<font color="red">For international travel, Passport attachment is mandatory<br/>
					<b>Scan and Attach only Photo page and Address Page of the Passport</b></font>
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
			<table class="bordered" style="position: relative;left: 2%;width: 90%;">
			<tr><th colspan="6">Other Details</th></tr>
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
						<html:select name="travelRequestForm" property="rent_Car" onchange="carchange()">
						<html:option value="No">No</html:option>
						<html:option value="Yes" >Yes</html:option>						
						</html:select>
					</td>

				</tr>
				
				<tr>
					<td>Guest House Name: </td>
					<td><input type="text" name="accom_name" id="accom_name" style="background:rgb(179,175,174)"  readonly="readonly"/></td>
					<td>Hotel Name: </td>
					<td><input type="text" name="hotel_Name" id="hotel_Name" readonly="readonly" style="background: rgb(179,175,174)"/></td>
					<td>Hotel City: </td>
					<td><input type="text" name="hotel_City" id="hotel_City" readonly="readonly" style="background: rgb(179,175,174)"/></td>
				</tr>
				
				<tr>
					<td>Pickup Details: </td>
					<td colspan="2"><textarea rows="2" cols="30" name="pickup_Details" id="pickup_Details" readonly="readonly" style="background: rgb(179,175,174)"></textarea></td>
					<td>Drop Details: </td>
					<td colspan="2"><textarea rows="2" cols="30" name="drop_Details" id="drop_Details" readonly="readonly" style="background: rgb(179,175,174)"></textarea></td>
				</tr>
				
				</table>
				</div>
		</div>

		<div id="R2" class="tabcontent" style="width: 1000px; margin-left: 22px" >
			<iframe src="travelrequest.do?method=travellerList" name="contentPage1" width="100%" height="800PX"
				frameborder="0" scrolling="no" id="the_iframe"  frameborder="0" style="margin-left: 0px">
        	</iframe> 
		</div>

		<div id="R4" class="tabcontent" style="width: 1000px; margin-left: 22px" >
			
			<table class="bordered" style="position: relative;left: 2%;width: 90%;" id="mcity">
				<tr><th colspan="8">Multiple City List </th></tr>
				<tr>
					<th>Travel list</th><th>Location</th><th> Preference</th><th>Arrival date</th> 
					<th>Arrival Time</th><th>Departure Date</th><th>Departure Time</th>
					<th>Action</th>
				</tr>
				
				<logic:notEmpty name="city"> 
				<logic:iterate id="abc" name="city">
				<tr>
				<td id="mcity11">${abc.multiemployeeno}<input name="userlistIdl" value="${abc.multiemployeeno}" type="hidden"></td>
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
				<td style="display: none;"><input name="mpassportnol" value="${abc.passportno}" type="hidden"></td>
				<td style="display: none;"><input name="mpassportexpirydatel" value="${abc.passportexpirydate}" type="hidden"></td>
				<td style="display: none;"><input name="mguest_Visanol" value="${abc.visano}" type="hidden"></td>
				<td><img src="images/delete.png" onclick="deleteRow1(this,1)" title="Delete"></td>
				</tr>
				
				</logic:iterate>
				</logic:notEmpty>
			</table>

			<br/>

			<table class="bordered" style="position: relative;left: 2%;width: 90%;">
				<tr><th colspan="6">Multiple City Details </th></tr>
				<tr>
					<td>Traveller List</td>
					<td>
						<select name="userlist" id="userlistId" >
							<option value="all">All</option>
							<option value="">--Select---</option>
						</select>
					</td>
					<td>Travel Mode<font color="red" size="3">*</font></td>
					<td>
						<select name="travelmode1"  onchange="travelmode5()"  id="travelmode1" >
							<option value="">--Select--</option>
							<option value="Road">Road</option>
							<option value="Rail">Rail</option>
							<option value="Air">Air</option>
						</select>
					</td>	
					<td> Travel Type<font color="red" size="3">*</font> </td>
					<td>
						<select name="traveltype1" id="traveltype1"  onchange="passdisplay1()" >
							<option value="">--Select</option>
						</select>
					</td>
				</tr>
 
				<tr style="display: none"  id="passportm1">
					<td>Passport No.<font color="red" size="3">*</font></td><td><input type="text" name="mpassportno" id="mpassportno"/></td>
					<td>Passport Expiry Date:<font color="red" size="3">*</font> </td><td><input type="text" name="mpassportexpirydate" id="mpassportexpirydate" /></td>
					<td>Visa Number & Date:<font color="red" size="3">*</font> </td>
					<td><input type="text" name="mguest_Visano" id="mguest_Visano" /></td>
				</tr>
				<tr>
					<td>Location:<font color="red" size="3">*</font> </td>
					<td colspan="2"><input type="text" name="mloc" id="mloc"/></td> 
					<td>Preference:<font color="red" size="3">*</font> </td>
					<td colspan="2"><input type="text" name="mairp" id="mairp"/> </td>
				</tr>
				<tr> 
					<td>Origin:<font color="red" size="3">*</font> </td>
					<td><input type="text" name="morigin" id="morigin"/> </td>
					<td>Arrival date: <font color="red" size="3">*</font></td>
					<td><input type="text" name="marrvdate" id="marrvdate"  onclick="datedisplay(this.id)" onmouseover="datedisplay(this.id)"/> </td>
					<td>Arrival Time:<font color="red" size="3">*</font> </td>
					<td>
					<html:text property="marrtime" styleId="marrtime"  size="15"  value=""/>
					 
					</td>
				</tr>
				<tr>
					<td>Destination: <font color="red" size="3">*</font></td>
					<td><input type="text" name="mdeparture" id="mdeparture"/> </td>
					<td>Departure Date:<font color="red" size="3">*</font> </td>
					<td><input type="text" name="mdeptdate" id="mdeptdate" onclick="datedisplay(this.id)" onmouseover="datedisplay(this.id)"/> </td>
					<td>Departure Time:<font color="red" size="3">*</font> </td>
					<td>
					<html:text property="mdepttime" styleId="mdepttime"  size="15"  value=""/>
					</td>
				</tr>

				<tr style="display: none" id="mcity1">
					<td>Accomodation: <font color="red" size="3">*</font></td>
					<td><select name="mhotel_Res" onchange="maccodchange()" id="mhotel_Res">
							<option value="No">No</option>
							<option value="Yes">Yes</option>
						
						</select>
					</td>
					<td>Accomodation Type: </td>
					<td>
					<div id="maccom_type_div" class="no">
						<select name="maccom_type" onchange="mhotelChange()" id="maccom_type" style="background: rgb(179,175,174)">
							<option value="">--Select--</option>
							<option value="Hotel">Hotel</option>
							<option value="Guest House">Guest House</option>
						</select>
					</div>
					</td>
					<td>Rental Car required: <font color="red" size="3">*</font></td>
					<td>
						<select name="mrent_Car" onchange="mcarchange()" id="mrent_Car">
							<option value="No">No</option>
							<option value="Yes">Yes</option>
						</select>
					</td>
				</tr>
				<tr style="display: none" id="mcity2">
					<td>Guest House Name: </td>
					<td><input type="text" name="maccom_name" id="maccom_name" style="background:rgb(179,175,174)"  readonly="readonly"/></td>
					<td>Hotel Name: </td>
					<td><input type="text" name="mhotel_Name" id="mhotel_Name" readonly="readonly" style="background: rgb(179,175,174)"/></td>
					
					<td>Hotel City: </td>
					<td><input type="text" name="mhotel_City" id="mhotel_City" readonly="readonly" style="background: rgb(179,175,174)"/></td>
				</tr>
				
				<tr style="display: none" id="mcity3">
					<td>Pickup Details: </td>
					<td colspan="2"><textarea rows="2" cols="30" name="mpickup_Details" id="mpickup_Details" readonly="readonly" style="background: rgb(179,175,174)"></textarea></td>
					<td>Drop Details: </td>
					<td colspan="2"><textarea rows="2" cols="30" name="mdrop_Details" id="mdrop_Details" readonly="readonly" style="background: rgb(179,175,174)"></textarea></td>
				</tr>
				<tr>
					<td colspan="6" align="center"><center>
						&nbsp; <html:button property="method" styleClass="rounded" value="Add" onclick="addMultiplecity()" style="align:right;width:100px;"/> &nbsp;
						</center>
					</td>
				</tr>
				
			</table>
			</div>
			
			
			
			<div id="R5" class="tabcontent" style="width: 1000px; margin-left: 22px" >
			
			<table class="bordered" style="position: relative;left: 1%;width: 80%;" id="mcityplan">
				<tr><th colspan="8">Multiple Travel Plan </th></tr>
				<tr>
					<th>Date</th><th>Origin</th><th> Destination</th><th>Travel Type</th> 
					<th>Travel mode</th><th>Remarks</th><!-- <th>EDIT</th> --><th>Delete</th>
				</tr>
				<logic:notEmpty name="city1"> 
				<logic:iterate id="abc1" name="city1">
				<tr>
				<input name="id" value="${abc1.id}" type="hidden"/>
			<%-- 	<td id ="plandate"><input name="mdate" id="mdate" value="${abc1.plandate}" type="text"></td>
				<td><input name="morigin11" value="${abc1.planorigin}" type="text"></td>
				<td><input name="mdestination1" value="${abc1.plandestination}" type="text"></td>
				<td><select name="mtravelmode1"  onchange="travelmode61()"  id="travelmode211" value="${abc1.travelmode}" >
				<option value="">Select</option>
				<option value="Road">Road</option>
				<option value="Air">Air</option>
				<option value="Rail">Rail</option>
				 <c:forEach items="${abc1.travelmode}" var="val"> 
        <option value="${val}" ${abc1.travelmode == val ? 'selected' : ' '}><c:out value="${val}" ></c:out></option>   
    </c:forEach>   
				
				</select>
				
				
			
				<td>
				<select name="mtraveltype1" id="traveltype211"  onchange="passdisplay11()" value="${abc1.traveltype}" >
							<option value="">Select</option>
						 <c:forEach items="${abc1.traveltype}" var="val"> 
        <option value="${val}" ${abc1.traveltype == val ? 'selected' : ' '}><c:out value="${val}" ></c:out></option>   
    </c:forEach>   
							
						</select>
				</td>
				
				<input name="mtravelmode1" value="${abc1.travelmode}" type="text"></td>
				<td><input name="mtraveltype1" value="${abc1.traveltype}" type="text"></td>
				<td><input type="text" name="mremarks1" value="${abc1.planremarks}" rows="2" cols="2"/></td>
				<td><a href="#" onclick="editpage(${abc1.id})">EDIT</a></td>
					
				<td><img src="images/delete.png" onclick="deleteRow2(this,1)" title="Delete32"></td> --%>
				
				
				
				<td id ="plandate">${abc1.plandate}<input name="mdate" id="mdate" value="${abc1.plandate}" type="hidden"></td>
				<td>${abc1.planorigin}<input name="morigin11" value="${abc1.planorigin}" type="hidden"></td>
				<td>${abc1.plandestination}<input name="mdestination1" value="${abc1.plandestination}" type="hidden"></td>
				<td>${abc1.travelmode}<input name="mtravelmode1" value="${abc1.travelmode}" type="hidden"></td>
				<td>${abc1.traveltype}<input name="mtraveltype1" value="${abc1.traveltype}" type="hidden"></td>
				<td>${abc1.travel_desk_type}<input name="mtraveldesk1" value="${abc1.travel_desk_type}" type="hidden"></td>
				<td>${abc1.planremarks}<input name="mremarks1" value="${abc1.planremarks}" type="hidden"></td>
				<td><img src="images/delete.png" onclick="deleteRow2(this,1)" title="Delete32"></td>
				
				
				</tr>
				</logic:iterate>
				</logic:notEmpty>
			</table>

			<br/>
          
			 <table class="bordered" style="position: relative;left: 2%;width: 90%;">
				<tr><th colspan="12">MultipleTravel Plan</th></tr>
				<tr>
					<td>Date<font color="red" size="1">*</font>  
					<input type="text" id="date1"     name="mdeptdate1" style="width: 80px;" onkeyup="this.value = this.value.replace(/'/g,'`')" readonly="true"/></td>
					
<!-- 					<input type="text" name="mdeptdate1" id="date1" onclick="datedisplay1(this.id)" onmouseover="datedisplay1(this.id)"/></td> -->
					
					<td>Origin<font color="red" size="1">*</font>
					<input type="text" name="morigin1" id="morigin1" onclick="checkingdatesplans()"/> </td>
					<td>Destination<font color="red" size="1">*</font>
					<input type="text" name="mdestination" id="mdestination"/> </td>
					<td>Travel mode<font color="red" size="1">*</font>
					<select name="travelmode21"  onchange="travelmode6()"  id="travelmode21" >
							<option value="">--Select--</option>
							<option value="Road">Road</option>
							<option value="Rail">Rail</option>
							<option value="Air">Air</option>
						</select>
					</td>	
					<td> Travel Type<font color="red" size="3">*</font> 
					<select name="traveltype21" id="traveltype21"  onchange="passdisplay1()" >
							<option value="">--Select</option>
						</select>
					</td>
					<td>Remarks<font color="red" size="3">*</font> 
					<textarea rows="2" cols="15" name="remarks1" id="remarks1"  ></textarea></td>
				
					<%-- <td colspan="6" align="center"><center>&nbsp; <html:button property="method" styleClass="rounded" value="Add" onclick="addMultiplecityplan()" style="align:right;width:100px;"/> &nbsp;
						</center>
					</td> --%>
						<td width="20px;"><a href="#" ><img	src="images/add-items.gif" onclick="addMultiplecityplan()"  title="Add Row" /></a></td>
				</tr>
				</table>
				
				<!-- <div id="wrapper">
<table align='center' cellspacing=2 cellpadding=5 id="data_table" border=1 class="bordered" >
<tr>
<th>Date</th>
<th>Origin</th>
<th>Destination</th>
<th>
Travel Type</th>
<th>Travel mode</th>	'
<th>Remarks</th>
</tr>



<tr>
<td><input type="text" id="date1" name="mdeptdate1" style="width: 80px;" onkeyup="this.value = this.value.replace(/'/g,'`')"></td>
<td><input type="text" id="new_origin"></td>
<td><input type="text" id="new_destination"></td>

<td>

<select name="travelmode21"  onchange="travelmode6()"  id="new_travel_type" >
							<option value="">--Select--</option>
							<option value="Road">Road</option>
							<option value="Rail">Rail</option>
							<option value="Air">Air</option>
						</select>

</td>
<td><input type="text" id="new_travel_type"></td>
<td><input type="text" id="new_travel_mode"></td>


<td>
<select name="traveltype21" id="new_travel_mode"  onchange="passdisplay1()" >
							<option value="">--Select</option>
						</select>
</td>
<td><input type="text" id="new_remarks"></td>
<td><input type="button" class="add" onclick="add_row();" value="Add Row"></td>
</tr>

</table>
</div> -->
			</div>
		<table class="bordered" style="position: relative;left: 2%;width: 85%;">
			<tr>
				<td colspan="6" align="center">
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
