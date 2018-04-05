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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'domestic.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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

function chkguest()
{

var x=document.forms[0].travelFor.value;

if(x=="Self")
	{
	document.getElementById("guestname1").style.display="none";
	document.getElementById("guestname2").style.display="none";
	}
else
	{
	document.getElementById("guestname1").style.display="";
	document.getElementById("guestname2").style.display="";

	}


}

function uploadDocument()
{
	document.forms[0].action="travelrequest.do?method=uploadInternationalDocuments";
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

	document.forms[0].action="travelrequest.do?method=deleteInternationDocuments";
	document.forms[0].submit();
}
}
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

	
 	var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	document.forms[0].action="travelrequest.do?method=saveDomestic";
	document.forms[0].submit();
	}
</script>
<style>

.no
{pointer-events: none; 
}
.design

{
	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);
} 


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
</style>
  </head>
  
  <body>
  <div align="center">
			
			
			</div>
  
  	<html:form action="travelrequest" enctype="multipart/form-data">
  	<div id="masterdiv" class="">
   	<table class="bordered" style="position: fixed;left: 2%;width: 80%;">
	 <tr><th  colspan="4" align="center"><center>International Travel Request Form </center></th></tr>
	<tr><td>Type of Travel:<font color="red" size="3">*</font></td><td><html:select name="travelRequestForm" property="typeOfTravel" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="Business">Business</html:option>
									<html:option value="Others">Others</html:option>
									</html:select></td>	
  
  		 <td>Travel For:<font color="red" size="3">*</font></td><td><html:select name="travelRequestForm" property="travelFor" styleClass="text_field" onchange="chkguest()">
									<html:option value="">--Select--</html:option>
									<html:option value="Self">Self</html:option>
									<html:option value="Guest">Guest</html:option>
									</html:select></td>	
			</tr>	
					
		<tr style="display:none; " id="guestname1"><td>Name Of The Guest:<font color="red" size="3">*</font></td><td colspan=""><html:text property="guestName"   size="60"  onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
		<td>Age:<font color="red" size="3">*</font></td><td><html:text property="guestAge"   size="15" onkeypress="return isNumber(event)"/></td>
		</tr>	
			<tr style="display:none; " id="guestname2"><td>Contact Number:<font color="red" size="3">*</font></td><td><html:text property="guestContactNo"   size="30"  onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
		<td>Gender:<font color="red" size="3">*</font></td><td>
		<html:select name="travelRequestForm" property="gender" styleClass="text_field" >
									<html:option value="">--Select--</html:option>
									<html:option value="Male">Male</html:option>
									<html:option value="Female">Female</html:option>
									</html:select></td>
		</tr>
			
		<tr><td>Mode Of Travel:<font color="red" size="3">*</font></td><td><html:select name="travelRequestForm" property="modeOfTravel" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="Air">Air</html:option>
									<html:option value="Bus">Bus</html:option>
									<html:option value="Train">Train</html:option>
									<html:option value="Sea">Sea</html:option>
									</html:select></td>
									
					<td>Travel Request For:<font color="red" size="3">*</font></td><td><html:radio property="travelRequestFor" value="One Way"></html:radio>One Way&nbsp;<html:radio property="travelRequestFor" value="Round Trip"></html:radio>Round Trip&nbsp;<html:radio property="travelRequestFor" value="Multi-City"></html:radio>Multi-City&nbsp;</td>					
									
									
										</tr>	
						 <tr><td>From:<font color="red" size="3">*</font></td><td colspan="3"><html:text property="fromPlace"   size="60"  onkeyup="this.value = this.value.replace(/'/g,'`')"/></td></tr>			
		                  <tr><td>Depart On:<font color="red" size="3">*</font></td><td colspan="3"><html:text property="departOn"   styleId="popupDatepicker" readonly="true"/>&nbsp;&nbsp;Preferred Time:&nbsp;&nbsp;<html:text property="departTime" styleId="timeFrom"  size="15"/>&nbsp;&nbsp;Remarks:&nbsp;&nbsp;<html:text property="departTime" styleId="timeFrom"  size="40" onkeyup="this.value = this.value.replace(/'/g,'`')"/></td></tr>	
		                  <tr><td>To:<font color="red" size="3">*</font></td><td colspan="3"><html:text property="toPlace"   size="60"  onkeyup="this.value = this.value.replace(/'/g,'`')"/></td></tr>			
		                  <tr><td>Return On:<font color="red" size="3">*</font></td><td colspan="3"><html:text property="returnOn"   styleId="popupDatepicker2"  readonly="true"/>&nbsp;&nbsp;Preferred Time:&nbsp;&nbsp;<html:text property="returnTime"  styleId="timeTo"   size="15"/>&nbsp;&nbsp;Remarks:&nbsp;&nbsp;<html:text property="departTime" styleId="timeFrom"  size="40" onkeyup="this.value = this.value.replace(/'/g,'`')"/></td></tr>
		                  <tr><td>Via:<font color="red" size="3">*</font></td><td colspan="1"><html:text property="via"   size="40"  onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
		                  
		                  <td>Purpose of Visit:<font color="red" size="3">*</font></td><td><html:select name="travelRequestForm" property="purposeOfVisit" >
									<html:option value="">--Select--</html:option>
									<html:option value="Business Meetings">Business Meetings</html:option>
									<html:option value="Presentations">Presentations</html:option>
							
									</html:select></td>
		                  
		                  </tr>
		                  <tr>	
						<td>Remarks:<font color="red" size="3">*</font></td>
							<td colspan="6" class="lft style1">
							<html:textarea property="remarks" cols="80" rows="10"  onkeyup="this.value = this.value.replace(/'/g,'`')"></html:textarea>
						
							</td>
						</tr>
						  <tr>
						  <td>Passport No:<font color="red" size="3">*</font></td>
						  <td colspan="1"><html:text property="via"   size="40"  onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
		                  
		                  <td>Place of Issue:<font color="red" size="3">*</font></td>
						  <td colspan="1"><html:text property="via"   size="40"  onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
		                  
		                  </tr>
						
						 <tr>
						  <td>Date of issue:<font color="red" size="3">*</font></td>
						  <td colspan="1"><html:text property="via"   size="40"  onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
		                  
		                  <td>Date of expiry:<font color="red" size="3">*</font></td>
						  <td colspan="1"><html:text property="via"   size="40"  onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
		                  
		                  </tr>
						 <tr >
		                  <th  colspan="4" align="center">Document Path : 
		                     	<html:file property="documentFile" />
			                     <html:button property="method" styleClass="rounded"  value="Upload" onclick="uploadDocument();" style="align:right;width:100px;"/>
			                   
		                  </th>
		                </tr>
		         
		               <logic:notEmpty name="documentDetails">
						<th colspan="6">Uploaded Documents </th>
						</tr>
						

						<logic:iterate id="abc" name="documentDetails">
						<tr>
							<td>
								<html:checkbox property="documentCheck" name="abc"
									value="${abc.id}" styleId="${abc.id}" onclick="addInput(this.value)" style="width :10px;"/>
							</td>
							<td colspan="3">
								<a href="/EMicro Files/ESS/Travel Request/UploadFiles/${abc.fileName}"  target="_blank">  <bean:write name="abc" property="fileName"/></a>
							</td>
						</tr>
						</logic:iterate>
										<td align="center" colspan="6">
								<input type="button"  class="rounded" value="Delete" onclick="deleteDocumentsSelected()" />
							</td>
							
						</logic:notEmpty> 
  			
  			<tr>
							<td colspan="4" align="center"><center>
								<html:button property="method" styleClass="rounded" value="Apply" onclick="applyOnduty('Applied');" style="align:right;width:100px;"/> &nbsp;
							
							    <html:button property="method" styleClass="rounded" value="Close" onclick="closerequest()" style="align:right;width:100px;"/> 
					       	    
							</td></center></tr></table>
  			</div>
  			</html:form>			
  </body>
</html>
