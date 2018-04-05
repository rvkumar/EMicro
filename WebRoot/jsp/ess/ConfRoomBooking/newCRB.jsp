
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.main.form.ApprovalsForm"/>
<jsp:directive.page import="com.microlabs.ess.form.RawMaterialForm"/>



<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

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
<script type="text/javascript" src="js/common.js"></script>
<!--
/////////////////////////////////////////////////
-->
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<title>Conference Room Booking</title>

<script type="text/javascript">
	function popupCalender(param)
	{
	var cal = new Zapatec.Calendar.setup({
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y ", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2", // trigger button
	disableFunc: function(date) {
          var now= new Date();
        return (date.getDate() < now.getDate());
    }
	});
	}
</script>

<script language="javascript">

	function openPage(param)
		{
		var x = window.open("approvalLevels.do?method=displayEmpDetails&param="+param,"SearchSID","width=500,height=500,status=no,toolbar=no,scrollbars=yes,menubar=no,sizeable=0");
		}
		
		function onSubmit(){
			var url="approvalLevels.do?method=submit";
			document.forms[0].action=url;
			document.forms[0].submit();
		}
		
function ClearApproverID (input) {

	if (input.value == input.defaultValue) {
	document.getElementById('aprID').style.cssText = 'color: black';
    input.value = "";
    }
}
function SetApproverID (input) {
if (input.value == "") {
    
    document.getElementById('aprID').style.cssText = 'color: grey';
        input.value = input.defaultValue;
    }
    document.getElementById("appIdValue").value= input.value;
}
 function ClearDesignation (input) {


     if (input.value == input.defaultValue) {
     document.getElementById('desigID').style.cssText = 'color: black';
         input.value = "";
     }
 }
 function SetDesignation (input) {
     if (input.value == "") {
     
     document.getElementById('desigID').style.cssText = 'color: grey';
         input.value = input.defaultValue;
     }
 }
 
 function ClearEmail (input) {


     if (input.value == input.defaultValue) {
     document.getElementById('empEmailID').style.cssText = 'color: black';
         input.value = "";
     }
 }
 function SetEmail (input) {
     if (input.value == "") {
     
     document.getElementById('empEmailID').style.cssText = 'color: grey';
         input.value = input.defaultValue;
     }
 }			
function showLevels(elem){
	document.getElementById("definelevels").innerHTML = "";
	var levels = "";
	if(elem == "Add"){
		levels = document.getElementById("levelNo").value;
	}
	else{
		var pSubject = prompt("How Many Approvers To Add?","1");
		levels = pSubject;
		//document.getElementById("levelNo").value = levels;
	}
	levels = levels.trim();
	levels = levels.replace(' ', '');
	if(document.forms[0].requestType.value==""){
		alert("Please Select Request Type ");
	    document.forms[0].requestType.focus();
	    return false;
	}
	else if(levels == ""){
		alert("Please Enter Approvals Level!");
		document.forms[0].employeeNumber.focus();
		return false;
	}
	var appTable = document.getElementById("definelevels");
	for(var i = 1; i <= levels; i++){
		var trElem = document.createElement("tr");
		var tdElem = document.createElement("td");
		tdElem.setAttribute("id", i);
		tdElem.setAttribute("width", "35%");
		
		//This is for Approver ID Field	
		var element = document.createElement("input");
		
		//Create Labels
		var label = document.createElement("Label");
		label.innerHTML = "Approver "+i;     
		
		//Assign different attributes to the element.
		element.setAttribute("type", "text");
		element.setAttribute("value", "Employee ID");
		element.setAttribute("name", "approverID");
		element.setAttribute("style", "width:200px");
		
		element.setAttribute("onfocus", "this.value=''");
		element.setAttribute("onblur", "SetApproverID (this)");
		 
		element.setAttribute("id", "aprID"+i);
		label.setAttribute("style", "font-weight:normal");
		     
		tdElem.appendChild(label);
		tdElem.appendChild(element);
		
		//Create span tag for name & email, designation
		var ifr1 = document.getElementById("if1").innerHTML;
		var tdIF = document.createElement("td");
		tdIF.setAttribute("id", "iftd"+i);
		tdIF.setAttribute("width", "65%");
		tdIF.innerHTML = ifr1;
		
		trElem.appendChild(tdElem);
		trElem.appendChild(tdIF);
		appTable.appendChild(trElem);
	}
	//for save button
	var trElem1 = document.createElement("tr");
	var tdElem1 = document.createElement("td");
	tdElem1.setAttribute("colspan", "2");
	tdElem1.setAttribute("align", "center");
	var saveButton = document.createElement("input");
	saveButton.setAttribute("type", "button");
	saveButton.setAttribute("value", "Save");
	saveButton.setAttribute("class", "sudmit_btn");
	saveButton.setAttribute("style", "width:75px; height:30px;");
	saveButton.setAttribute("onclick", "saveDetails('save')");
	tdElem1.appendChild(saveButton);
	trElem1.appendChild(tdElem1);
	appTable.appendChild(trElem1);
}


function showEmpDetails(){

}
	
function getInfo(){
document.getElementById("levelNo").style.readonly = "false";
}	

function saveDetails(elem){
	var levels=""
	if(elem == "edit"){
		levels = document.getElementById("appCount").value;
		//document.getElementById("levelNo").value = levels;
	}
	else{
	levels = document.getElementById("levelNo").value;
	}
	levels = levels.trim();
	levels = levels.replace(' ', '');
	if(document.forms[0].requestType.value=="")
	{
		alert("Please Select Request Type ");
	    document.forms[0].requestType.focus();
	    return false;
	}
   	else if(document.forms[0].employeeNumber.value=="")
   	{
    	alert("Please Enter Required Level Number ");
     	document.forms[0].requestType.focus();
     	return false;
   	}
   	else {
   		if(elem == "save"){
	   		for(var i = 1; i <= levels; i++){
	   			if(document.getElementById("aprID"+i).value == document.getElementById("aprID"+i).defaultValue){
	   				alert("Please Enter Valid Approver"+i+" Number!");
	     			document.getElementById("aprID"+i).focus();
	     			return false;
	   			}
	   		}
   		}
   	}

	var url="authentication.do?method=saveAuthenticationDetails&type="+elem;
			document.forms[0].action=url;
			document.forms[0].submit();	

}

function getDetails(){

  if(document.forms[0].requestType.value=="")
	    {
	      alert("Please Select Request Type ");
	      document.forms[0].requestType.focus();
	      return false;
	    }

var url="authentication.do?method=getDetails";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function viewDetails(type){
	if(document.forms[0].roomName.value=="")
	{
		alert("Please Select Request Type ");
	    document.forms[0].requestType.focus();
	    return false;
	}
	var rName = document.forms[0].roomName.value;
	var url="crbook.do?method=checkAvailability&roomName="+rName;
	document.forms[0].action=url;
	document.forms[0].submit();

}

function getLevels(elem){
	if(document.forms[0].requestType.value=="")
	{
		alert("Please Select Request Type ");
	    document.forms[0].requestType.focus();
	    return false;
	}
	document.getElementById("noapprover").style.display="none";
	document.getElementById("leveldetails").style.display="";
	document.forms[0].employeeNumber.focus();
}

function getRelatedIds(elem){
var empid = elem.value;
var url="authentication.do?method=emplyeeIdDetails&empid="+empid;
document.forms[0].action=url;
document.forms[0].submit();

}
function showMoreAction(){
var idDis = document.getElementById("Actions").style.display;
var ad = document.getElementById("actiondrop");
var acticon = document.getElementById("acticon");
if(idDis == "none"){
	ad.setAttribute("style","font-weight:normal");
	acticon.setAttribute("src","images/lefarrow.png");
	document.getElementById("Actions").style.display = "";
}
else{
	ad.setAttribute("style","font-weight:bold;");
	acticon.setAttribute("src","images/rhtarrow.png");
	document.getElementById("Actions").style.display = "none";
}
}

function deleteThis(elem){
var reqType = document.getElementById("reqtypeid").value;
var url="authentication.do?method=deleteDetails&empid="+elem.id+"&reqType="+reqType;
			document.forms[0].action=url;
			document.forms[0].submit();
}

function addNewRequest(){
	var reqName = document.getElementById("requestName").value;
	if(reqName == "")
	{
		alert("Please Enter Request Name!");
	    document.getElementById("requestName").focus();
	    return false;
	}
	var url="authentication.do?method=addNewRequest&reqName="+reqName;
	document.forms[0].action=url;
	document.forms[0].submit();
}

function bookingConfRoom(){
	if(document.forms[0].roomName.value=="")
	{
		alert("Please Select Request Type ");
	    document.forms[0].requestType.focus();
	    return false;
	}
	var rName = document.forms[0].roomName.value;
	var url="crbook.do?method=bookSelectRoom&roomName="+rName;
	document.forms[0].action=url;
	document.forms[0].submit();
}
function getAvailableTime(){
	var rName = document.forms[0].roomName.value;
	var url="crbook.do?method=getAvailable&roomName="+rName;
	document.forms[0].action=url;
	document.forms[0].submit();
}
</script>
</head>

<body >
<html:form action="crbook" enctype="multipart/form-data">
	<div align="center">
			<logic:present name="delegateForm" property="message">
				<font color="red"><bean:write name="delegateForm" property="message" /></font>
			</logic:present>
	</div>
	<table border="0" cellpadding=2 cellspacing=2 width="100%"  style="border: 0px" align="center">
		<tr><td width="50%" height="300" valign="top" style="border: 1px solid #4297d7;">
		<div>
			<div class="widgetTitle"> Delegation </div><br/>
			<table border="0" cellpadding="8" class="content" style="solid #4297d7;">
				<logic:notEmpty name="newBooking">
				<tr><td colspan="2"><h2>Check Conference Room Booking</h2></td></tr>
				<tr>
					<th>Conference Room : <img src="images/star.gif" width="8" height="8" /></th>
					<td><html:select  property="roomName"  tabindex="1" styleId="crbid" onchange="getAvailableTime()" style="border-radius:5px;">
						<html:option value="">-Select-</html:option>
						<html:option value="A">Room A</html:option>
						<html:option value="B">Room B</html:option>
						<html:option value="C">Room C</html:option>
						<html:option value="D">Room D</html:option>
						</html:select>
					</td>
				</tr>
				<tr style="height:10px;"></tr>
				<tr>
					<th>Date</th>
					<td><html:text property="requiredDate" styleId="startDate" onfocus="popupCalender('startDate')" styleClass="rounded" readonly="true" style="width:25%;"/></td>
				</tr>
				<tr>
					<th>From</th><td><span><html:select  property="requiredFromTime"  tabindex="1" styleId="crbsid" onblur="disableSelected(this)">
						<html:option value="09:00">09:00 am</html:option>
						<html:option value="09:30">09:30 am</html:option>
						<html:option value="10:00">10:00 am</html:option>
						<html:option value="10:30">10:30 am</html:option>
						<html:option value="11:00">11:00 am</html:option>
						<html:option value="11:30">11:30 am</html:option>
						<html:option value="12:00">12:00 pm</html:option>
											<logic:notEmpty name="12:30"><html:option value="12:30">12:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="01:00"><html:option value="01:00">01:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="01:30"><html:option value="01:30">01:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="02:00"><html:option value="02:00">02:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="02:30"><html:option value="02:30">02:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="03:00"><html:option value="03:00">03:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="03:30"><html:option value="03:30">03:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="04:00"><html:option value="04:00">04:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="04:30"><html:option value="04:30">04:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="05:00"><html:option value="05:00">05:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="05:30"><html:option value="05:30">05:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="06:00"><html:option value="06:00">06:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="06:30"><html:option value="06:30">06:30 pm</html:option></logic:notEmpty>
											</html:select>
									</span>
									&nbsp;To:&nbsp;
									<span><html:select property="requiredToTime"  tabindex="1" styleId="crbeid">
											<logic:notEmpty name="09:30"><html:option value="09:30">09:30 am</html:option></logic:notEmpty>
											<logic:notEmpty name="10:00"><html:option value="10:00">10:00 am</html:option></logic:notEmpty>
											<logic:notEmpty name="10:30"><html:option value="10:30">10:30 am</html:option></logic:notEmpty>
											<logic:notEmpty name="11:00"><html:option value="11:00">11:00 am</html:option></logic:notEmpty>
											<logic:notEmpty name="11:30"><html:option value="11:30">11:30 am</html:option></logic:notEmpty>
											<logic:notEmpty name="12:00"><html:option value="12:00">12:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="12:30"><html:option value="12:30">12:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="01:00"><html:option value="01:00">01:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="01:30"><html:option value="01:30">01:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="02:00"><html:option value="02:00">02:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="02:30"><html:option value="02:30">02:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="03:00"><html:option value="03:00">03:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="03:30"><html:option value="03:30">03:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="04:00"><html:option value="04:00">04:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="04:30"><html:option value="04:30">04:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="05:00"><html:option value="05:00">05:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="05:30"><html:option value="05:30">05:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="06:00"><html:option value="06:00">06:00 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="06:30"><html:option value="06:30">06:30 pm</html:option></logic:notEmpty>
											<logic:notEmpty name="07:00"><html:option value="07:00">07:00 pm</html:option></logic:notEmpty>
									</html:select></span></td>
				</tr>
				<tr style="height:10px;"></tr>
				<tr>
					<th></th>
					<td><input type="button" class="rounded" value="Book" style="width:100px;" onclick="bookingConfRoom()" /></td>
				</tr>
				</logic:notEmpty>
				<logic:empty name="bookedDetails">
				<tr>
					<th width="200px" scope="row"></th>
					<td style="font-weight:bold;color:red;"><b><bean:write name="crbForm" property="message" /></b></td>
				</tr>
				</logic:empty>
				<logic:notEmpty name="bookedDetails">
				<tr><td colspan="2">
					<table width="100%">
						<tr>
							<th>Date</th>
							<th>Time</th>
							<th>Department</th>
							<th>Contact</th>
						</tr>
						<logic:iterate name="bookedDetails" id="abc">
						<tr class="tableOddTR" style="text-align:center;">
							<td>${abc.requiredDate}</td>
							<td><bean:write name="abc" property="requiredFromTime" /> to <bean:write name="abc" property="requiredToTime"/></td>
							<td><bean:write name="abc" property="department"/></td>
							<td><bean:write name="abc" property="requesterName" /></td>	
						</tr>
						</logic:iterate>
					</table>
				</td></tr>
				</logic:notEmpty>
			</table>
		</div>
		</td></tr>
	</table>
</html:form>


</body>
</html>
