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
<script type="text/javascript">
function officalInfo(){

	var url = "empofficial.do?method=displayOfficialInfo";
	    document.forms[0].action=url;
	    document.forms[0].submit();

	}
function assets(){
	var url = "empofficial.do?method=displayAssets";
    document.forms[0].action=url;
    document.forms[0].submit();
}
function saveAsset(){
	if(document.forms[0].assetType.value==""){
		alert("Please Select Asset Type");
		document.forms[0].assetType.focus();
		return false;
	}
	var assetType=document.forms[0].assetType.value;
	if(assetType=="TwoWheeler" ||assetType=="FourWheeler"){
		
		if(document.forms[0].insurRenewalDt.value==""){
			alert("Please Select Insurance Renewal Date ");
			document.forms[0].insurRenewalDt.focus();
			return false;
		}
	}
	if(document.forms[0].serialNo.value==""){
		alert("Please Select Asset Serial No./Model No./Make ");
		document.forms[0].serialNo.focus();
		return false;
	}
	if(document.forms[0].recivedDt.value==""){
		alert("Please Select Asset Received Date ");
		document.forms[0].recivedDt.focus();
		return false;
	}
	var url = "empofficial.do?method=saveAssets";
    document.forms[0].action=url;
    document.forms[0].submit();	
}
function hideMessage(){
	
	document.getElementById("messageID").style.visibility="hidden";	
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
	document.forms[0].action="empofficial.do?method=deleteAsset&cValues="+checkvalues;
document.forms[0].submit();
}
}
}

function checkAsset(){
	var assetType=document.forms[0].assetType.value;
	if(assetType=="TwoWheeler" ||assetType=="FourWheeler"){
		document.getElementById("insurenceId").style="visibility:visible";
	}else{
		document.getElementById("insurenceId").style="visibility:hidden";
	}
}
function editAsset(reqId){
	var url = "empofficial.do?method=editAsset&reqId="+reqId;
    document.forms[0].action=url;
    document.forms[0].submit();
	
}
function updateAsset(){
	var url = "empofficial.do?method=updateAsset";
    document.forms[0].action=url;
    document.forms[0].submit();
	
}
function viewAsset(reqId){
	var url = "empofficial.do?method=viewHRAsset&reqId="+reqId;
    document.forms[0].action=url;
    document.forms[0].submit();
	
}
</script>
  <style type="text/css">
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
$(function() {
	$('#insurancedate').datepick({dateFormat: 'd/m/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#assetdate').datepick({dateFormat: 'd/m/yyyy'});
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
</head>
<body>
<div align="center" id="messageID" style="visibility: true;">
				<logic:present name="employeeOfficialInfoForm" property="message1">
					<font color="red" size="3"><b><bean:write name="employeeOfficialInfoForm" property="message1" /></b></font>
					<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
				</logic:present>
				<logic:present name="employeeOfficialInfoForm" property="message">
					<font color="Green" size="3"><b><bean:write name="employeeOfficialInfoForm" property="message" /></b></font>
					<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
				</logic:present>
			</div>
	<html:form action="/empofficial.do" enctype="multipart/form-data" >
	
<table>
<tr>
<td><html:button property="method" value="Official Information" onclick="officalInfo()" styleClass="rounded"/>
&nbsp;<html:button property="method" value="Assets" onclick="assets()" styleClass="rounded"/>
</td>
</tr>
</table>
<div>&nbsp;</div>
<table class="bordered">
<tr>
<th colspan="4"><center><b><big>Company Assets</big></b></center></th>
</tr>
<tr>
<td>Asset Type<font color="red">*</font></td>
<td colspan="3">
<html:select property="assetType" onchange="checkAsset()">
 <html:option value="">Select</html:option>
 <html:option value="Data Card">Data Card</html:option>
 <html:option value="Desktop">Desktop</html:option>
  <html:option value="ipod">iPod</html:option>
  <html:option value="Laptop">Laptop</html:option>
   <html:option value="TwoWheeler">Two wheeler</html:option>
   <html:option value="FourWheeler">Four wheeler</html:option>
   <html:option value="House">House</html:option>
 <html:option value="mobile">Mobile</html:option>
 <html:option value="others">Others</html:option>
</html:select>

<tr>
<td>Asset Descripton<font color="red">*</font></td><td colspan="3"><html:textarea property="assetDesc" cols="40" rows="5"/></td>
</tr>
<tr>
<td>Asset Serial No./Model No./Make<font color="red">*</font></td><td><html:text property="serialNo"></html:text></td>
<td>Insurance Renewal Date <div style="visibility: hidden" id="insurenceId"><font color="red" size="3">*</font></div> </td><td><html:text property="insurRenewalDt" styleId="insurancedate"></html:text></td>
</tr>
<tr>
<td>Asset Received Date<font color="red">*</font></td><td colspan="3"><html:text property="recivedDt" styleId="assetdate"></html:text></td>
</tr>
<tr>
<td colspan="4"><center>
<logic:empty name="save">
<html:button property="method" value="Add Asset" onclick="saveAsset()" styleClass="rounded"/>&nbsp;<html:button property="method" value="Clear" styleClass="rounded"/>
</logic:empty>
<logic:notEmpty name="update">
<html:button property="method" value="Update Asset" onclick="updateAsset()" styleClass="rounded"/>
</logic:notEmpty>
</center> </td>
</tr>
</table>
<div>&nbsp;</div>
<logic:notEmpty name="assetList">
<table class="bordered">
	 <tr><th  colspan="8" align="center">
					<center>My Asset List</center></th>
<tr>
<th>S.No</th><th>Asset Type</th><th>Asset Descripton</th><th>Asset Serial No./<br/>Model No./Make</th><th>Insurance Renewal Date</th><th>Asset Received Date</th>
<th>Edit</th>
<th><a href="#"><center><img src="images/deleteIcon.png" style="height: 15;width: 20px;" align="absmiddle" onclick="deleteAsset()" title="Delete"/></center></a></th>
</tr>
<%
int i=1;
%>
<logic:iterate id="a" name="assetList">
<tr>
<td><%=i %></td><td>${a.assetType }</td><td>${a.assetDesc }</td><td>${a.serialNo }</td><td>${a.insurRenewalDt }</td><td>${a.recivedDt }</td>

<td><a href="#"><center><img src="images/edit1.jpg" style="height: 15;width: 15px;" align="absmiddle" onclick="editAsset('${a.id}')" title="Edit"/></center></a></td>
	<td><center><html:checkbox property="documentCheck" name="a"
		value="${a.id}" styleId="${a.id}"  style="width :10px;"/></center></td>
	
</tr>
<%
i++;
%>

</logic:iterate>

</table>
</logic:notEmpty>
<br><logic:notEmpty name="hrassets">
<table class="bordered content" width="80%">
		 <tr><th  colspan="7" align="center">
					<center> HR Updated Asset List</center></th>
  						</tr>
  						<tr><th>Sl No</th><th>Asset type</th><th>Asset Description</th><th>Created date</th><th>View</th>
</tr>
  						
  						<%int f=0; %>
  						<logic:iterate id="hr" name="hrassets">
  						<%f++; %>
  						<tr><td><%=f %></td><td>${hr.assetType }</td><td>${hr.assetDesc }</td><td>${hr.created_Date }</td>
  						<td><a href="#"><center><img src="images/view.gif" style="height: 20%;width: 20%;" align="absmiddle" onclick="viewAsset('${hr.id}')" title="Edit"/></center></a></td>
	</tr>
  						</logic:iterate>
  						
  						<logic:empty name="hrassets">
  						<tr><td colspan="10">No records to display</td></tr>
  						</logic:empty>
						
	
	</table>
	</logic:notEmpty>
	<br/>
	<logic:notEmpty name="ITassetList">
<table class="bordered">
	 <tr><th  colspan="10" align="center">
					<center>IT Asset List</center></th>
<tr>
<th>S.No</th>	<th>Asset Type</th><th>Category</th><th>Asset No</th>
							<th>Host Name</th>
							<th>Model</th>
							<th>Manufacturer</th>								
								<th>Processor</th>
								<th>RAM</th><th>HDD</th>
							


</tr>
<%
int i=1;
%>
<logic:iterate id="a" name="ITassetList">
<tr>
<td><%=i %></td><td>${a.assetType }</td><td>${a.category }</td><td>${a.asset_No }</td><td>${a.host_Name }</td><td>${a.model }</td><td>${a.manufacturer }</td>
<td>${a.processor }</td><td>${a.rAM }</td><td>${a.hDD }</td>



</tr>
<%
i++;
%>

</logic:iterate>

</table>
</logic:notEmpty>
	
<html:hidden property="id" />
</html:form>
</body>
</html>