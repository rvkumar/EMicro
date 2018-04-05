
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
</script>


<title>Home Page</title>

<script language="javascript">




function parseDate(str) {
    var mdy = str.split('/');
    return new Date(mdy[2], mdy[1]-1, mdy[0]);
}


function daydiff(first, second) {

//daydiff

var totaldays=(second-first)/(1000*60*60*24);

if(totaldays<0)
{
alert("start date should be less than end date");
    return "";
}
else{

    return ((second-first)/(1000*60*60*24)+1)
    }
}


function uploadDocument()
{
	document.forms[0].action="onDuty.do?method=uploadDraftDocuments";
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
	document.forms[0].action="onDuty.do?method=deleteDraftDocuments&cValues="+checkvalues;
document.forms[0].submit();
}
}
}



function applyLeave(param)
{
var type=param;
document.forms[0].action="onDuty.do?method=saveOnduty&param="+param;
	document.forms[0].submit();

}
function saveDraftData()
{
if(document.forms[0].onDutyType.value=="")
	    {
	      alert("Onduty Type should not be left blank");
	      document.forms[0].onDutyType.focus();
	      return false;
	    }
	   else if(document.forms[0].duration.value=="")
	    {
	      alert("Duration Type should not be left blank");
	      document.forms[0].duration.focus();
	      return false;
	    }
	     else if(document.forms[0].startDate.value=="")
	    {
	      alert("Start Date should not be left blank");
	      document.forms[0].startDate.focus();
	      return false;
	    }
	     else if(document.forms[0].endDate.value=="")
	    {
	      alert("End Date should not be left blank");
	      document.forms[0].endDate.focus();
	      return false;
	    }
	     else if(document.forms[0].noOfDays.value=="")
	    {
	      alert("Please Select No Of Days.It should not be left blank");
	      document.forms[0].noOfDays.focus();
	      return false;
	      
	    }
	     else if(document.forms[0].reason.value=="")
	    {
	      alert("Please Enter Valid reason.It should not be left blank");
	      document.forms[0].reason.focus();
	      return false;
	      }
	      	    var reason=document.forms[0].reason.value;
         
         var splChars = "'";
for (var i = 0; i < reason.length; i++) {
    if (splChars.indexOf(reason.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Reason!"); 
     document.forms[0].reason.focus();
 return false;
}
}
	      
document.forms[0].action="onDuty.do?method=saveDraftData";
document.forms[0].submit();
}
function calculateEndDate()
{

if(document.forms[0].startDate.value==""){
alert("Please Select Start Date");
document.forms[0].startDate.focus();
return false;
}else if(document.forms[0].duration.value==""){
alert("Please Select Duration");
document.forms[0].duration.focus();
return false;
}else if(document.forms[0].endDate.value==""){
alert("Please Select End Date");
document.forms[0].endDate.focus();
return false;
}
var stDate=document.forms[0].startDate.value;
var endDate=document.forms[0].endDate.value;
var durtaion=document.forms[0].duration.value;

if(stDate!=endDate && (durtaion=="FH" || durtaion=="SH"))
{

alert("Invalid Selection Duration");
document.forms[0].duration.focus();
document.forms[0].duration.value="";
return false;

}
if(stDate==endDate && (durtaion=="FH" || durtaion=="SH"))
{
document.forms[0].noOfDays.value="0.5";
}else{
document.forms[0].noOfDays.value=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));

var endDate=document.forms[0].noOfDays.value;

}
if(endDate=="")
{
document.forms[0].endDate.value="";
}

}
function checkTotalDays(){

var stDate=document.forms[0].startDate.value;
var endDate=document.forms[0].endDate.value;
var durtaion=document.forms[0].duration.value;
if(stDate!="" && endDate!="" && durtaion!=""){
if(stDate!=endDate && (durtaion=="FH" || durtaion=="SH"))
{

alert("Invalid Selection Duration");
document.forms[0].duration.focus();
document.forms[0].duration.value="";
return false;

}
if(stDate==endDate && (durtaion=="FH" || durtaion=="SH"))
{
document.forms[0].noOfDays.value="0.5";
}else{
document.forms[0].noOfDays.value=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));

var endDate=document.forms[0].noOfDays.value;

}
if(endDate=="")
{
document.forms[0].endDate.value="";
}

}

}
function closeDrafts()
{
	document.forms[0].action="onDuty.do?method=draftsList";
	document.forms[0].submit();
}
		
</script>
</head>

<body >

<html:form action="onDuty" enctype="multipart/form-data">

				<div align="center">
					<logic:present name="onDutyForm" property="message">
						<font color="red">
							<bean:write name="onDutyForm" property="message" />
						</font>
					</logic:present>
				</div>
				
				


				
				
		<table class="bordered content" width="90%">
			 <tr><th  colspan="4" align="center">
					 On Duty Form </th>
  						<tr>
  							<td width="15%">On Duty Type <font color="red" size="3">*</font></td>
							<td width="64%" align="left" colspan="3">
								<html:select name="onDutyForm" property="onDutyType" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="Visit Plants">Visit Plants</html:option>
									<html:option value="Attend Conference">Attend Conference</html:option>
									<html:option value="Meet Customer / Vendor">Meet Customer / Vendor</html:option>
									<html:option value="Tour">Tour</html:option>
									</html:select>
									
									
							</td>
						</tr>
						<tr>
							<td width="15%">Start Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%">
								<html:text property="startDate" styleId="startDate" onfocus="popupCalender('startDate')" styleClass="text_field" readonly="true" onchange="checkTotalDays()"/>
							</td>
							<td width="15%">Duration <font color="red" size="3">*</font></td>
							
							<td width="34%">
								<html:select name="onDutyForm" property="duration" styleClass="content" onchange="checkTotalDays()">
									<html:option value="">--Select--</html:option>
									<html:option value="FD">Full Day</html:option>
									<html:option value="FH">First Half</html:option>
									<html:option value="SH">Second Half</html:option>
								</html:select>
							</td>
						</tr>
						<tr>
							<td width="15%">End Date <font color="red" size="3">*</font></td>
							
							<td align="left" width="34%">
							<html:text property="endDate" styleId="endDate" onfocus="popupCalender('endDate')" styleClass="text_field" onchange="checkTotalDays()" readonly="true"/>
							</td>
							<td width="15%">No of Days <font color="red" size="3">*</font></td>
							
							<td align="left"  width="34%">
							<div id="noOfDaysDiv">
								<html:text property="noOfDays"  styleClass="text_field" readonly="true"/>
							</div>
						</tr>	
						<tr>
					<th colspan="4"> Reason<font color="red" size="2">*</font> : 
						</th>
						</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="110" rows="10"></html:textarea>
						
							</td>
						</tr>
	<logic:notEmpty name="documentDetails">
						<tr class="heading">
							<td colspan="2">
								<b><font>Uploaded Documents </font></b>
							</td>
						</tr>
						
						<tr class="tablerowdark">
							<td bgcolor="#4297d7" width="5%">
								<font color="white">Select</font>
							</td>
							<td bgcolor="#4297d7" colspan="6"><font color="white">Name </font></td>
						</tr>

						<logic:iterate id="abc" name="documentDetails">
						<tr>
							<td>
								<html:checkbox property="documentCheck" name="abc"
									value="${abc.id}" styleId="${abc.id}" onclick="addInput(this.value)" style="width :10px;"/>
							</td>
							<td colspan="2">
								<a href="${abc.fileFullPath}"  target="_blank">  <bean:write name="abc" property="fileName"/></a>
							</td>
						</tr>
						</logic:iterate>
							<td align="center" colspan="6">
								<input type="button"  class="content" value="Delete" onclick="deleteDocumentsSelected()"/>
							</td>
							
					</logic:notEmpty>
						
					    <tr >
		                  <th  colspan="4" align="center">Document Path : 
		                     	<html:file property="documentFile" />
			                     <html:button property="method" styleClass="rounded"  value="Upload" onclick="uploadDocument();" style="align:right;width:100px;"/>
			                   
		                  </th>
		                </tr>
		          	<tr>
						<td colspan="6" align="center">
						<html:button property="method" styleClass="rounded" value="Submit" onclick="saveDraftData();"/>
					     <html:button property="method" styleClass="rounded" value="Close" onclick="closeDrafts()"/>
						</td>
				</tr></table>
			
				<br/>
				
		<logic:notEmpty name="appList">
		 <div align="left" class="bordered ">
			<table width="100%"  class="sortable" >
			<tr>
				<th style="text-align:left;"><b>Type</b></th>
				<th style="text-align:left;"><b>EmpNo</b></th>
				<th style="text-align:left;"><b>Emp Name</b></th>	
				<th style="text-align:left;"><b>Designation</b></th>
			</tr>
			<logic:iterate id="abc" name="appList">
			<tr>
			
			<td>${abc.appType}</td>
			<td>${abc.approverID}</td>
			<td>${abc.approverName}</td>
			<td>${abc.appDesig}</td>
			</tr>
			</logic:iterate>
			</table>
		</div>
		</logic:notEmpty>
		
		<html:hidden property="requestNumber"/>
	</html:form>

	</body>
</html>
					
			