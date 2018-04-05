<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" >

<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/sorttable.js"></script>
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<script type="text/javascript" src="calender/js/calendar.js"></script>
<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>

<script type="text/javascript">
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
function hideMessage(){
	document.getElementById("messageID").style.visibility="hidden";	
}
function displayFiscalYear(){
	var fiscalYear=document.forms[0].fiscalYear.value;
	if(fiscalYear=="")
		document.forms[0].fiscalYearDesc.value="";
	if(fiscalYear!=""){
		var nextYear=parseInt(fiscalYear);
		nextYear=nextYear+1;
		document.forms[0].fiscalYearDesc.value="April-"+fiscalYear+" to March "+nextYear;
	}
}
function getSummary(){
	if(document.forms[0].fiscalYear.value==""){
		alert("Please Select Fiscal Year");
		document.forms[0].fiscalYear.focus();
		return false;
	}
	document.forms[0].action="summary.do?method=newSummary";
	document.forms[0].submit();
}
function getPrerrequisites(title){
	if(document.forms[0].fiscalYear.value==""){
		alert("Please Select Fiscal Year");
		document.forms[0].fiscalYear.focus();
		return false;
	}
	var fiscalYear=document.forms[0].fiscalYear.value;
	 var newWindow = window.showModalDialog("summary.do?method=getPrerrequisites&FiscalYear="+fiscalYear, title, "dialogWidth=850px;dialogHeight=300px; center:yes");

	    // Puts focus on the newWindow
	    if (window.focus) {
	        newWindow.focus();
	    }
}
function getAllowance(title){
	if(document.forms[0].fiscalYear.value==""){
		alert("Please Select Fiscal Year");
		document.forms[0].fiscalYear.focus();
		return false;
	}
	var fiscalYear=document.forms[0].fiscalYear.value;
	 var newWindow = window.showModalDialog("summary.do?method=getAllowance&FiscalYear="+fiscalYear, title, "dialogWidth=850px;dialogHeight=300px; center:yes");

	    // Puts focus on the newWindow
	    if (window.focus) {
	        newWindow.focus();
	    }
}
function getBreakUp(title){
	if(document.forms[0].fiscalYear.value==""){
		alert("Please Select Fiscal Year");
		document.forms[0].fiscalYear.focus();
		return false;
	}
	var fiscalYear=document.forms[0].fiscalYear.value;
	 var newWindow = window.showModalDialog("summary.do?method=getBreakUp&FiscalYear="+fiscalYear, title, "dialogWidth=850px;dialogHeight=300px; center:yes");

	    // Puts focus on the newWindow
	    if (window.focus) {
	        newWindow.focus();
	    }
}
function getSalary(){
	if(document.forms[0].fiscalYear.value==""){
		alert("Please Select Fiscal Year");
		document.forms[0].fiscalYear.focus();
		return false;
	}
	var fiscalYear=document.forms[0].fiscalYear.value;
	 var newWindow = window.showModalDialog("summary.do?method=getSalary&FiscalYear="+fiscalYear, null, "dialogWidth=1050px;dialogHeight=600px; center:yes");

	    // Puts focus on the newWindow
	    if (window.focus) {
	        newWindow.focus();
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
</head>
<body>
	<div align="center" id="messageID" style="visibility: true;">
		<logic:present name="summaryForm" property="message">
			<font color="red" size="3"><b><bean:write name="summaryForm" property="message" /></b></font>
			<script type="text/javascript">
			setInterval(hideMessage,6000);
			</script>
		</logic:present>
		<logic:present name="summaryForm" property="message1">
			<font color="Green" size="3"><b><bean:write name="summaryForm" property="message1" /></b></font>
			<script type="text/javascript">
			setInterval(hideMessage,6000);
			</script>
		</logic:present>
	</div>
<html:form action="/summary.do" enctype="multipart/form-data" >
<table class="bordered">
<html:hidden name="summaryForm" property="location" />
 <html:hidden name="summaryForm" property="staffCatStxt" />
 <tr><th>Employee No</th><td><bean:write name="summaryForm" property="employeeNo" /></td>
 <th>Name</th><td><bean:write name="summaryForm" property="empName" />
 </td>
<th>Location</th><td><bean:write name="summaryForm" property="plant" /></td>
<html:hidden name="summaryForm" property="plant" />
 </tr>
 <tr>
 <th>Staff Category</th><td><bean:write name="summaryForm" property="staffCategory" />
 <html:hidden name="summaryForm" property="staffCategory" />
 </td>
<th>Fiscal Year <font color="red" size="2" >*</font></th>
<td><html:select property="fiscalYear" onchange="displayFiscalYear()">
<html:option value="">---Select---</html:option>
<html:options name="summaryForm"  property="yearList"></html:options>
</html:select>
<html:text property="fiscalYearDesc"  style="border:0;width:150px;" readonly="true" />
</td>
<td colspan="2"><center><html:button property="method" value="Get Details" onclick="getSummary('test')" styleClass="rounded"/></center></td>
</tr>
</table>

<logic:notEmpty name="Summary details">
<div>&nbsp;</div>


<table class="bordered">
<tr><th colspan="4"><center><b><big>Summary Sheet</big></b></center></th></tr>
<tr>
<td colspan="2">1)Gross Salary</td>
<td colspan="2">Balance Deduction</td>
</tr>
<tr><td>Salary</td><td><bean:write name="summaryForm" property="salary"/>&nbsp;&nbsp;<html:button property="method" onclick="getSalary()" styleClass="rounded">Details</html:button></td>

<td colspan="2"><bean:write name="summaryForm" property="balenceDeduction"/>&nbsp;&nbsp;<html:button property="method" onclick="getBreakUp('test')" styleClass="rounded">Break Up</html:button></td>
</tr>

<tr>
<td>Prerrequisites</td><td colspan="3"><bean:write name="summaryForm" property="prerrequisites"/>&nbsp;&nbsp;
<html:button property="method" onclick="getPrerrequisites('test')" styleClass="rounded">Details</html:button></td>

</tr>
<tr>
<td>Additional Income</td><td><bean:write name="summaryForm" property="additionalIncome"/></td>
<td>Total</td><td><bean:write name="summaryForm" property="addtionalIncTotal"/></td>
</tr>
<tr><td>2)Less : Allowance u/s 10</td><td><html:text property="lessAllowance"/>&nbsp;<html:button property="method" onclick="getAllowance()" styleClass="rounded">Details</html:button></td>
<td colspan="2"><bean:write name="summaryForm" property="addtionalIncTotal"/></td>
</tr>

<tr>
<td >3)Balance (1-2)</td>
<td colspan="3"><bean:write name="summaryForm" property="balance12"/></td>
</tr>
<tr>
<td colspan="4">4)Deduction</td>
</tr>
<tr>
<td>Entertainment Allowance</td><td colspan="3"><bean:write name="summaryForm" property="entertainmentAllowance"/></td>
</tr>
<tr>
<td>Tax on employment</td><td><bean:write name="summaryForm" property="taxOnEmployment"/></td>
<td>Total </td><td><bean:write name="summaryForm" property="totalTaxOnEmp"/></td>
</tr>
<tr>
<td >5)Income Chargeable Under the Head Salaries (3-4)</td>
<td colspan="3"><bean:write name="summaryForm" property="incomeChargeble34"/></td>
</tr>
<tr>
<td >6)Add/Less any other Income reported by employee</td>
<td colspan="3"><bean:write name="summaryForm" property="addLessEmpIncRep"/></td>
</tr>
<tr>
<td>7)Total Income (5 + 6)</td><td colspan="3"><bean:write name="summaryForm" property="total56"/></td>
</tr>
<tr><td colspan="4">8)Deduction Under Chapter VI-A-Section Wise</td></tr>
<tr><td>9)Total Taxable Income</td><td colspan="3"><bean:write name="summaryForm" property="totalTaxable"/> </td></tr>
<tr><td>10)Tax</td><td colspan="3"><html:text property="tax"/></td></tr>
<tr><td>11)Education Cess</td><td colspan="3"><bean:write name="summaryForm" property="educationCess"/></td></tr>
<tr><td>12)Tax Payable</td><td colspan="3"><bean:write name="summaryForm" property="taxPayable"/></td></tr>
<tr><td>13)Tax Deduction</td><td colspan="3"><bean:write name="summaryForm" property="taxDeduction"/></td></tr>
<tr><td>14)Balance</td><td colspan="3"><html:text property="taxDeduction"/></td></tr>

</table>
</logic:notEmpty>
</html:form>
</body>
</html>