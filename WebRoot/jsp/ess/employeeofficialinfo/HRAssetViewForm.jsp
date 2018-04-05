
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="com.microlabs.ess.form.LeaveForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>
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
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      
      <style type="text/css">
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>

<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
$(function() {
	$('#insurance_Issue_date').datepick({dateFormat: 'd/m/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#insurance_Exp_date').datepick({dateFormat: 'd/m/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
	
	
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

</script>

	
	<script type="text/javascript">
	function popupCalender(param)
	{
	var cal = new Zapatec.Calendar.setup({
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y ", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2" // trigger button
	});
	}

	
function updateAsset(){

if(document.forms[0].assetType.value!="Data Card")
{
if(document.forms[0].model.value=="")
{
alert("Please enter model");
	      document.forms[0].model.focus();
	      return false;

}

if(document.forms[0].make.value=="")
{
alert("Please enter make");
	      document.forms[0].make.focus();
	      return false;

}
if(document.forms[0].vehicle_no.value=="")
{
alert("Please Enter Vehicle No");
	      document.forms[0].vehicle_no.focus();
	      return false;

}
if(document.forms[0].insurance_no.value=="")
{
alert("Please Enter Insurance No.");
	      document.forms[0].insurance_no.focus();
	      return false;

}
if(document.forms[0].insurance_Compny.value=="")
{
alert("Please Enter Insurance Company ");
	      document.forms[0].insurance_Compny.focus();
	      return false;

}

if(document.forms[0].insurance_Issue_date.value=="")
{
alert("Please select Insurance Issue Date ");
	      document.forms[0].insurance_Issue_date.focus();
	      return false;

}

if(document.forms[0].insurance_Exp_date.value=="")
{
alert("Please select Insurance Expiry date ");
	      document.forms[0].insurance_Exp_date.focus();
	      return false;

}

}
else
{

if(document.forms[0].service_provider.value=="")
{
alert("Please enter service Provider");
	      document.forms[0].service_provider.focus();
	      return false;

}

if(document.forms[0].data_card_no.value=="")
{
alert("Please enter data card no.");
	      document.forms[0].data_card_no.focus();
	      return false;

}
if(document.forms[0].default_pwd.value=="")
{
alert("Please enter default password ");
	      document.forms[0].default_pwd.focus();
	      return false;

}
if(document.forms[0].service_plan.value=="")
{
alert("Please enter service plan ");
	      document.forms[0].service_plan.focus();
	      return false;

}


}


	var url = "hr.do?method=updateAsset";
    document.forms[0].action=url;
    document.forms[0].submit();
	
}function hideMessage(){
	
	document.getElementById("messageID").style.visibility="collapse";
	document.getElementById("messageID1").style.visibility="collapse";	
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
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        	
       
        	       			
        }
    }
     xmlhttp.open("POST","leave.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
    xmlhttp.send();
}

function selectUser(input,reqFieldName){


var res = input.split("-");
	document.getElementById(reqFieldName).value=res[1];
	document.getElementById("empname1").value=res[0];
	document.getElementById("dept1").value=res[2];	
	document.getElementById("desg1").value=res[3];	
	


	disableSearch(reqFieldName);
}

function disableSearch(reqFieldName){
  if(reqFieldName=="prev_user"){
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		}
	}
	}

 function saveAsset(){

if(document.forms[0].assetType.value!="Data Card")
{
if(document.forms[0].model.value=="")
{
alert("Please enter model");
	      document.forms[0].model.focus();
	      return false;

}

if(document.forms[0].make.value=="")
{
alert("Please enter make");
	      document.forms[0].make.focus();
	      return false;

}
if(document.forms[0].vehicle_no.value=="")
{
alert("Please Enter Vehicle No");
	      document.forms[0].vehicle_no.focus();
	      return false;

}
if(document.forms[0].insurance_no.value=="")
{
alert("Please Enter Insurance No.");
	      document.forms[0].insurance_no.focus();
	      return false;

}
if(document.forms[0].insurance_Compny.value=="")
{
alert("Please Enter Insurance Company ");
	      document.forms[0].insurance_Compny.focus();
	      return false;

}

if(document.forms[0].insurance_Issue_date.value=="")
{
alert("Please select Insurance Issue Date ");
	      document.forms[0].insurance_Issue_date.focus();
	      return false;

}

if(document.forms[0].insurance_Exp_date.value=="")
{
alert("Please select Insurance Expiry date ");
	      document.forms[0].insurance_Exp_date.focus();
	      return false;

}

}
else
{

if(document.forms[0].service_provider.value=="")
{
alert("Please enter service Provider");
	      document.forms[0].service_provider.focus();
	      return false;

}

if(document.forms[0].data_card_no.value=="")
{
alert("Please enter data card no.");
	      document.forms[0].data_card_no.focus();
	      return false;

}
if(document.forms[0].default_pwd.value=="")
{
alert("Please enter default password ");
	      document.forms[0].default_pwd.focus();
	      return false;

}
if(document.forms[0].service_plan.value=="")
{
alert("Please enter service plan ");
	      document.forms[0].service_plan.focus();
	      return false;

}


}


  var url="hr.do?method=addnewHRAsset";
			document.forms[0].action=url;
			document.forms[0].submit();
 }
 function closeasset()
{

var url = "hr.do?method=displayHRAssetList";
    document.forms[0].action=url;
    document.forms[0].submit();
}
</script>

  </head>
  
  <body>

 
<html:form action="empofficial">

			<div align="center" id="messageID">
			<logic:present name="employeeOfficialInfoForm" property="message">
				<font color="green" size="4"> <bean:write
						name="employeeOfficialInfoForm" property="message" /> </font>
						<script type="text/javascript">
					setInterval(hideMessage,5000);
					</script>
			</logic:present>
			<logic:present name="employeeOfficialInfoForm" property="message1">
				<font color="red" size="4"> <bean:write
						name="employeeOfficialInfoForm" property="message1" /> </font>
						<script type="text/javascript">
					setInterval(hideMessage,5000);
					</script>
			</logic:present>
		</div>

<table class="bordered " width="130%">
 <html:hidden property="assetType" name="employeeOfficialInfoForm"/>
  <html:hidden property="pERNR" name="employeeOfficialInfoForm"/>
    <html:hidden property="id" name="employeeOfficialInfoForm"/>
      <html:hidden property="created_by" name="employeeOfficialInfoForm"/>
    <html:hidden property="created_Date" name="employeeOfficialInfoForm"/>
<tr>
<th colspan="4"><center>Asset Updation Form</center></th>
</tr>
<tr><th colspan="4"><big>User Details</big></th></tr>
<tr><td><b>Name:</b></td><td> <bean:write name="employeeOfficialInfoForm" property="empname" /></td>

<td><b>Employee No:</b></td><td><bean:write name="employeeOfficialInfoForm" property="pERNR" /></td></tr>
<tr>
<td><b>Department:</b></td><td><bean:write name="employeeOfficialInfoForm" property="empdep" /></td>
<td><b>Designation:</b></td><td><bean:write name="employeeOfficialInfoForm" property="empdesg" /></td></tr>
<tr>
<td><b>Location:</b></td><td><bean:write name="employeeOfficialInfoForm" property="emploc" /></td>
<td><b>Ext No:</b></td><td><bean:write name="employeeOfficialInfoForm" property="empext" /></td></tr>
<tr><th colspan="4"><big>Asset Details</big></th></tr>
<tr><td>Asset Type</td><td colspan="3"><big><b>${employeeOfficialInfoForm.assetType}</b></big></td></tr>
<tr>
<td>Asset Description</td><td colspan="3"><html:textarea property="assetDesc" cols="40" rows="5"/></td>
</tr>
<logic:notEqual value="Data Card" property="assetType" name="employeeOfficialInfoForm">
<tr><td>Model<font color="red">*</font></td><td><html:text property="model"></html:text></td><td>Make<font color="red">*</font></td><td><html:text property="make"></html:text></td></tr>	
<tr><td>Vehicle No<font color="red">*</font></td><td><html:text property="vehicle_no"></html:text></td><td>Insurance No<font color="red">*</font></td><td><html:text property="insurance_no"></html:text></td></tr>
<tr><td>Insurance Company<font color="red">*</font></td><td><html:text property="insurance_Compny"></html:text></td><td>Insurance Issue date<font color="red">*</font></td><td><html:text property="insurance_Issue_date" styleId="insurance_Issue_date"></html:text></td></tr>
<tr><td>Insurance Expiry date<font color="red">*</font></td><td colspan="3"><html:text property="insurance_Exp_date" styleId="insurance_Exp_date"></html:text></td></tr>
<tr><td>Previous User</td><td><html:text property="prev_user" onkeyup="searchEmployee('prev_user')" styleId="prev_user"></html:text>
	<div id="sU" style="display:none;">
		<div id="sUTD" style="width:80px;">
		<iframe src="jsp/ess/compoff/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="60%"></iframe>
		</div>
	</div></td>
	<td>Name</td> <td><html:text property="prev_user_name"  styleId="empname1" disabled="true" name="employeeOfficialInfoForm"></html:text></td></tr>
       <tr><td>Department</td>
       <td><html:text property="prev_user_dep"  styleId="dept1" disabled="true" name="employeeOfficialInfoForm" ></html:text></td>
       <td>Designation</td>
       <td><html:text property="prev_user_des"  styleId="desg1" disabled="true" name="employeeOfficialInfoForm"></html:text></td>
	</tr>
</logic:notEqual>
<logic:equal value="Data Card" property="assetType" name="employeeOfficialInfoForm">
<tr><td>Service Provider<font color="red">*</font></td><td><html:text property="service_provider"></html:text></td><td>Data Card No<font color="red">*</font></td><td><html:text property="data_card_no"></html:text></td></tr>
<tr><td>Default Password<font color="red">*</font></td><td><html:text property="default_pwd"></html:text></td><td>Data Plan<font color="red">*</font></td><td><html:text property="service_plan"></html:text></td></tr>
</logic:equal>
<tr><td colspan="4"><center>
<html:button property="method" value="Close" styleClass="rounded" onclick="history.back(-1)"/>
</td></tr>
	</table>

	






</html:form>
  </body>
</html>
