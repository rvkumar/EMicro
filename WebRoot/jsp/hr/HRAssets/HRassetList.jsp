
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
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
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

	function deleteAsset()
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
	document.forms[0].action="hr.do?method=deleteAsset&cValues="+checkvalues;
document.forms[0].submit();
}
}
}


function editAsset(reqId){

	var url = "hr.do?method=editAsset&reqId="+reqId;
    document.forms[0].action=url;
    document.forms[0].submit();
	
}
function updateAsset(){
	var url = "empofficial.do?method=updateAsset";
    document.forms[0].action=url;
    document.forms[0].submit();
	
}function hideMessage(){
	
	document.getElementById("messageID").style.visibility="collapse";
	document.getElementById("messageID1").style.visibility="collapse";	
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
        if(reqFieldName=="pERNR"){
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        	}
       
        	       			
        }
    }
     xmlhttp.open("POST","itHelpdesk.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
    xmlhttp.send();
}

function selectUser(input,reqFieldName){
document.getElementById(reqFieldName).value=input;
	disableSearch(reqFieldName);
}

function disableSearch(reqFieldName){
  if(reqFieldName=="pERNR"){
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		}
	}
	}

 function onSave(){
  if(document.forms[0].assetType.value=="")
 {
 
 alert("Please Select Asset type");
document.forms[0].assetType.focus();
      return false;
 }
 
   if(document.forms[0].pERNR.value=="")
 {
 
 alert("Please Enter Emp No.");
document.forms[0].pERNR.focus();
      return false;
 }
 
  var url="hr.do?method=newHRassetform";
			document.forms[0].action=url;
			document.forms[0].submit();
 }
 
 function assetlog(){
	var url = "hr.do?method=assetlog";
    document.forms[0].action=url;
    document.forms[0].submit();
	
}
 
</script>

  </head>
  
  <body>

 
<html:form action="hr">

			<div align="center" id="messageID">
			<logic:present name="hrForm" property="message">
				<font color="green" size="4"> <bean:write
						name="hrForm" property="message" /> </font>
						<script type="text/javascript">
					setInterval(hideMessage,5000);
					</script>
			</logic:present>
			<logic:present name="hrForm" property="message1">
				<font color="red" size="4"> <bean:write
						name="hrForm" property="message1" /> </font>
						<script type="text/javascript">
					setInterval(hideMessage,5000);
					</script>
			</logic:present>
		</div>

<table class="bordered " width="130%">
 
<tr>

<th><b>Asset Type</b> <font color="red">*</font></th>
					</td>

						<td>
						<html:select property="assetType" styleClass="content" styleId="filterId" onchange="showform()">
						
							<html:option value="">--Select--</html:option>
				<html:option value="Data Card">Data Card</html:option>
						       <html:option value="Two Wheeler">Two Wheeler</html:option>
   <html:option value="Four Wheeler">Four Wheeler</html:option>
							
					
							</html:select>
						</td>
					


<th>Employee No<font color="red">*</font></th>

	<td><html:text property="pERNR"  onkeyup="searchEmployee('pERNR')" styleId="pERNR"  style="width: 84px; ">
	<bean:write property="pERNR" name="hrForm" /></html:text>
	<div id="sU" style="display:none;">
		<div id="sUTD" style="width:80px;">
		<iframe src="jsp/it/HelpDesk/searchemployee.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="60%"></iframe>
		</div>
	</div>
			&nbsp;&nbsp;<html:button property="method" value="Add Asset" onclick="onSave()" styleClass="rounded" style="width: 81px;"/>	
		&nbsp;&nbsp;<html:button property="method" value="Asset Log" onclick="assetlog()" styleClass="rounded" style="width: 81px;"/>	
</tr>						
</table>

	<br>	
	<table class="bordered content" width="80%">
		 <tr><th  colspan="7" align="center">
					<center> HR Asset List</center></th>
  						</tr>
  						<tr><th>Sl No</th><th>Asset type</th><th>Asset Description</th><th>Emp Name</th><th>Created date</th><th>Edit</th>
<th><a href="#"><center><img src="images/deleteIcon.png" style="height: 15;width: 20px;" align="absmiddle" onclick="deleteAsset()" title="Delete"/></center></a></th></tr>
  						<logic:notEmpty name="hrassets">
  						<%int f=0; %>
  						<logic:iterate id="hr" name="hrassets">
  						<%f++; %>
  						<tr><td><%=f %></td><td>${hr.assetType }</td><td>${hr.assetDesc }</td><td>${hr.empname }</td><td>${hr.created_Date }</td>
  						<td><a href="#"><center><img src="images/edit1.jpg" style="height: 15;width: 15px;" align="absmiddle" onclick="editAsset('${hr.id}')" title="Edit"/></center></a></td>
	<td><center><html:checkbox property="documentCheck" name="hr" value="${hr.id}" styleId="${hr.id}"  style="width :10px;"/></center></td></tr>
  						</logic:iterate>
  						</logic:notEmpty>
  						<logic:empty name="hrassets">
  						<tr><td colspan="10">No records to display</td></tr>
  						</logic:empty>
						
	
	</table>








</html:form>
  </body>
</html>
