<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/sorttable.js"></script>
 <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 
 <link rel="stylesheet" type="text/css" href="css/microlabs1.css" />

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
 <script type="text/javascript" src="js/jquery.min.js"></script>
 <script type="text/javascript" src="js/time/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script src="js/sumo1.js"></script>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script> -->
    <script src="js/sumo/jquery.sumoselect.js"></script>
    <link href="js/sumo/sumoselect.css" rel="stylesheet" />

    <script type="text/javascript">
        $(document).ready(function () {
            window.asd = $('.SlectBox').SumoSelect({ csvDispCount: 3 });
            window.test = $('.testsel').SumoSelect({okCancelInMulti:true });
            window.testSelAll = $('.testSelAll').SumoSelect({okCancelInMulti:true, selectAll:true });
            window.testSelAll2 = $('.testSelAll2').SumoSelect({selectAll:true });

        });
        
         $(document).ready(function () {
           $('.testselect1').SumoSelect();

        });
    </script>
    
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
 
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
 <script type="text/javascript">
 function loading() {
        // add the overlay with loading image to the page
        var over = '<div id="overlay">' +
            '<img id="loading" src="images/loadinggif.gif">' +
            '</div>';
        $(over).appendTo('body');

        // click on the overlay to remove it
        //$('#overlay').click(function() {
        //    $(this).remove();
        //});

        // hit escape to close the overlay
     /*    $(document).keyup(function(e) {
            if (e.which === 27) {
                $('#overlay').remove();
            }
        }); */
    };
 
 function onSave(){
  if(document.forms[0].requestType.value=="")
 {
 
 alert("Please Select RequestType");
document.forms[0].requestType.focus();
      return false;
 }
 
 if(document.forms[0].requestType.value=="Email ID Create Request" || document.forms[0].requestType.value=="Email ID Change Request" || document.forms[0].requestType.value=="Email ID Delete Request")
{
if(document.forms[0].username.value!="21")
{
 alert("You cannot raise this request contact Your System Administrator");
document.forms[0].requestType.focus();
      return false;
}

}


  if(document.forms[0].usage.value=="")
 {
 
 alert("Please Select FOR USE");
document.forms[0].usage.focus();
      return false;
 }
 
  if(document.forms[0].usage.value=="Others")
 {



  if(document.forms[0].employeeno.value=="")
 {
 
 alert("Please Enter EmployeeNo");
document.forms[0].employeeno.focus();
      return false;
 }
 

 
 }
 
 
   if(document.forms[0].employeeno.value!="")
 {
  var employeeno = document.forms[0].employeeno.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(employeeno)) {
             alert("Employee Number  Should be Integer ");
                document.forms[0].employeeno.focus();
            return false;
        }
 }
   
	if(document.forms[0].requestType.value=="Issues"){
		var url="itIsssues.do?method=displayAllIssues";
		document.forms[0].action=url;
		document.forms[0].submit();
	}else if(document.forms[0].requestType.value=="Sap Document Cancellation"){
		var url="itIsssues.do?method=sapDocumentCancellation";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else 
	{ 
	 var url="itHelpdesk.do?method=newrequestform";
			document.forms[0].action=url;
			document.forms[0].submit();
	}
 
} 
 
  
function searchEmployee(fieldName){

var reqFieldName=fieldName

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
        if(reqFieldName=="employeeno"){
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        	}
       
        	       			
        }
    }
    xmlhttp.open("POST","hrApprove.do?method=searchForManualApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
    xmlhttp.send();
}


function selectUser(input,reqFieldName){


var res = input.split("-");
	document.getElementById(reqFieldName).value=res[1];
	document.getElementById("employeeName").value=res[0];
	document.getElementById("plant").value=res[3];
	document.getElementById("paygrp").value=res[5];
	document.getElementById("staffcat").value=res[4];
	disableSearch(reqFieldName);
}

function disableSearch(reqFieldName){
  if(reqFieldName=="employeeno"){
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		}
	}
	}
	
function update()
{


if(document.forms[0].time.value=="")
{
alert("Please Select Time");
return false;
}



var rea=document.forms[0].swipereason.value;
var cou=document.forms[0].count.value;

if(cou>=3)
{
var abc=confirm("Limit exceeded ..Are you sure you want to save");
if(abc==true)
{

}
else
{
return false;
}
}
var savebtn = document.getElementById("masterdiv");
savebtn.className = "no";
var url="hrApprove.do?method=updateEmpmanualtimeentry";
			document.forms[0].action=url;
			document.forms[0].submit();
			

}	
 </script>




<script type="text/javascript" src="js/time/time1.js"></script>
<script type="text/javascript" src="js/time/time2.js"></script>
<script type="text/javascript" src="js/time/time3.js"></script>

 <link rel="stylesheet" type="text/css" href="css/time/time1.css" />
 <link rel="stylesheet" type="text/css" href="css/time/time2.css" />

  <style>
  #overlay {
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    background: #000;
    opacity: 0.6;
    filter: alpha(opacity=80);
    
    
}
.no
{pointer-events: none; 
}
#loading {
    width: 250px;
    height: 257px;
    position: absolute;
    top: 20%;
    left: 50%;
    margin: -18px 0 0 -15px;
    
    
}

  </style>
<script>
function process() {


var url="hrApprove.do?method=processEmpmanualtimeentry";
			document.forms[0].action=url;
			document.forms[0].submit();
			    loading();
	
}

function check() {
if(document.forms[0].employeeno.value=="")
{
alert("Select Employee");
return false;
}

if(document.getElementById("popupDatepicker").value=="")
{

alert("Select Date");
return false;
}

var url="hrApprove.do?method=empmanualtimeentrySearch";
			document.forms[0].action=url;
			document.forms[0].submit();
	
}



jQuery(document).ready(function () {

    var options12 = {
        show24Hours: false,
        spinnerImage: null,
        immediateset: true,
        autoclose: true
    };
    jQuery('#date1').timeEntry(options12).on('clockpickerdone', function (e) {
        jQuery(this).timeEntry('setTime', jQuery(this).val());
        console.log('clockpickerdone');
    });
    jQuery('#date1').parent().clockpicker(options12);

    var options24 = {
        show24Hours: true,
        spinnerImage: null,
        immediateset: true,
        autoclose: true
    };
    jQuery('#date2').timeEntry(options24).on('clockpickerdone', function (e) {
        jQuery(this).timeEntry('setTime', jQuery(this).val());
        console.log('clockpickerdone');
    });
    jQuery('#date2').parent().clockpicker(options24);

    jQuery('#date3').parent().clockpicker(options12);

    jQuery('#date4').parent().clockpicker(options24);

    jQuery('#date5').parent().clockpicker();

});

function statusMessage(message){
$('#overlay').remove();
alert(message);
}


function onUpload(){

	if(document.forms[0].documentFile.value=="")
		    {
		      alert("Please Select File ");
		      document.forms[0].documentFile.focus();
		      return false;
		    }

	document.forms[0].action="hrApprove.do?method=uploadManualFileAction";
	document.forms[0].submit();
	} 
</script>

<style>.input-group-addon {
    cursor:pointer;
    font-family:dashicons;
    padding:0;
    vertical-align:top;
}
p span {
    display:inline-block;
}</style>

  </head>
  
  <body>
   <html:form action="/hrApprove.do" enctype="multipart/form-data" onsubmit="onSave(); return false;">
   
 <div align="center">
				<logic:notEmpty name="hrApprovalForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="hrApprovalForm" property="message" />');
					</script>
				</logic:notEmpty>
				<logic:notEmpty name="hrApprovalForm" property="message2">
					
					<script language="javascript">
					statusMessage('<bean:write name="hrApprovalForm" property="message" />');
					</script>
				</logic:notEmpty>
			</div>

<table class="bordered " width="130%">
 
<tr>
<th colspan="4"><center><b>Employee Information</center></th>
</tr><tr>
	

<td><b>Employee No <font color="red">*</font></b><font color="red" style="visibility:hidden;" id="emp">*</font></td>

	<td colspan="3"><html:text property="employeeno"  onkeyup="searchEmployee('employeeno')" styleId="employeeno"  style="width: 84px; ">
	<bean:write property="employeeno" name="hrApprovalForm" /></html:text>
	<div id="sU" style="display:none;">
		<div id="sUTD" style="width:80px;">
		<iframe src="jsp/it/HelpDesk/searchemployee.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="60%"></iframe>
		</div>
	</div>
				
		&nbsp;&nbsp;<%-- <html:button property="method" value="Continue" onclick="onSave()" styleClass="rounded" style="width: 81px;"/> --%>
					</td>
</tr>		
<tr><td><b>Name</b></td>
<td colspan="3"><html:text property="employeeName" styleId="employeeName" readonly="true" style="background-color:#E8E8E8;"></html:text></td>
</tr>				
<tr><td><b>Location</b></td>
<td colspan="3"><html:text property="plant" styleId="plant" readonly="true" style="background-color:#E8E8E8;"></html:text></td>
</tr>
<tr><td><b>Pay Grp</b></td>
<td colspan="3"><html:text property="paygrp" styleId="paygrp" readonly="true" style="background-color:#E8E8E8;"></html:text></td>
</tr>
<tr>
<td><b>Emp Category</b></td>
<td colspan="3"><html:text property="staffcat" styleId="staffcat" readonly="true" style="background-color:#E8E8E8;"></html:text></td>
</tr>

<tr><td><b>Date<font color="red">*</font></b></td>



<td colspan="3"><html:text property="fromDate" name="hrApprovalForm" styleId="popupDatepicker" style="width: 98px;"/></td>
</tr>

<tr>
<td colspan="4"><center><html:button property="" onclick="check()" styleClass="rounded"> Search </html:button>

</tr>

</table>
<br/>


<table class ="bordered">
<th>

			<html:select  property="locationId" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
			
			<html:options name="hrApprovalForm"  property="locationIdList" labelProperty="locationLabelList"/>
		</html:select>
	
</th><th>

<html:select  property="repgrpArray" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll" multiple="true">			
			<html:options name="hrApprovalForm"  property="repgrpList" labelProperty="repgrpLabelList"/>
			
		</html:select>
</th>

<th>


<html:button property="" onclick="process()" styleClass="rounded"> Process </html:button></center>
</th>
</table>
<br/>
<table  class="bordered">
 <tr>
<th colspan="2">Download Excel Template</th>
</tr>
<tr>
<td><a href="Templates/Manual Entry template.xlsx"  target="_blank"><b><u>Manual Entry Template</u></b></a></td>


</tr>
<tr>
<th colspan="2">Upload Excel File</th>
</tr>
<tr>	
				<td colspan="4">
					<html:file  property="documentFile" styleClass="rounded" style="width: 220px"/>&nbsp;&nbsp;&nbsp;
					<html:button value="Upload" onclick="onUpload()" property="method" styleClass="rounded" style="width: 100px" />
				</td>
				
			</tr></table> 
<%-- <table class ="bordered">
<tr><th>Upload Excel File</th></tr>
<tr>
<td colspan="4">
					<html:file  property="documentFile" styleClass="rounded" style="width: 220px"/>&nbsp;&nbsp;&nbsp;
					<html:button value="Upload" onclick="onUpload()" property="method" styleClass="rounded" style="width: 100px" />
				</td></tr>
</table> --%>
<br/>
<div id="masterdiv" class="">
<table class ="bordered">
<tr>
<th colspan="8">Punch Timings</th>
</tr>
<tr><th>Pernr</th><th>Date</th><th>Day</th><th>In time</th><th>Out time</th><th>In Status</th><th>Out Status</th><th>Shift</th></tr>
<logic:notEmpty name="punchlist">
                  <logic:iterate id="abc1" name="punchlist">
<tr><td>${abc1.employeeNo}</td><td>${abc1.date}</td><td>${abc1.day}</td><td>${abc1.iNTIME}</td><td>${abc1.oUTTIME}</td><td>${abc1.iNSTATUS}</td><td>${abc1.oUTSTATUS}</td><td>${abc1.shift}</td></tr>
</logic:iterate></logic:notEmpty>
</table>
<br/>
 <logic:notEmpty  name="hrApprovalForm" property="count">
 <table class ="bordered">
<tr>
<th colspan="4"><center><b> Lost Entry</b></center></th>
</tr><tr>
	

<td><b>Swipe Type<font color="red">*</font></b></td>
<td><html:select property="swipetype" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1" >
						<option value="I">In</option>
						<option value="O">Out</option>	
								</html:select>       </td>

	<td><b>Reason<font color="red">*</font></b></td> <td><html:select property="swipereason" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
						<option value="Missing">Missing</option>
						<option value="Forgot Swipe">Forgot Swipe</option>
						<option value="Late">Late</option>	
						<option value="Early">Early</option>	
						<option value="Special Approval">Special Approval</option>	
						<option value="Absent">Absent</option>	
								</html:select>       </td>
</tr>						
<tr><td><b>Time<font color="red">*</font></b></td>
<td>
<input type="text" id="date2" value="" name="time" />
        <button type="button" class="input-group-addon dashicons-clock">
</td>
<td><b>Remarks</b></td>
<td><html:textarea property="swiperemarks" name="hrApprovalForm" />
							
								</td>
</tr>
<tr><td><b>Late Count</b></td>
<td colspan="3"><html:text property="count" name="hrApprovalForm" readonly="readonly" style="background-color:#E8E8E8;"></html:text></td>
</tr>
<tr><td colspan="4"><center><html:button property="" onclick="update()"  styleClass="rounded"> Save</html:button>

</center>
</td>
</tr>



</table>
</logic:notEmpty></div>
<br/>



                  <div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <%int i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="10"><center>Entry List</center></th></tr>
                  <tr>
                  
                    <th >#</th>
                    <th>Pernr</th>
                    <th>Name </th>
                    <th>Swipe Date </th>
                    <th>Swipe Type</th>
                     <th>Reason Type</th>                    
                    <th>Swipe Time</th>
                     <th>Previous Time</th>
                    <th>Remarks</th>
                    <th>Created Date</th>
                    
                  </tr>
                
                   <logic:notEmpty name="llist">
                  <logic:iterate id="abc1" name="llist">
                   <%i++; %>
             
                <tr>               
                    <td ><%=i %></td>     
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="startDate"/></td>
                    <td> <bean:write name="abc1" property="swipe_Type"/></td>
                    <td> <bean:write name="abc1" property="reason_Type"/></td>
                    <td> <bean:write name="abc1" property="time"/></td>
                       <td> <bean:write name="abc1" property="prev_time"/></td>
                    <td> <bean:write name="abc1" property="remarks"/></td>
                    <td> <bean:write name="abc1" property="date"/></td>
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="9"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>
						
</html:form>
  </body>
</html>
