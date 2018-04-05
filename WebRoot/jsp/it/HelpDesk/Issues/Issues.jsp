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
 
 <script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
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
	
	.boldFont{
	  color: #000000;
	 
	}
	.unBoldFont{
	  color: #585858;
	 
	}
	
	</style>
 <script type="text/javascript">
 function assignIssueReq()
 {
	 
    var techn=document.forms[0].issAssignedTechn.value;
    if(techn==""){
    	alert("Please Select Atleast One Technician");
    	document.forms[0].issAssignedTechn.focus();
    	return false;
    }
 	var rows=document.getElementsByName("selectedRequestNo");
 	var checkvalues='';
 	var uncheckvalues='';
 	var test=0;
 	test=parseInt(test);
 	for(var i=0;i<rows.length;i++)
 	{
 	if (rows[i].checked)
 	{
 	checkvalues+=rows[i].value+',';
 	/* if(test>0){
 		alert("Plese Select Only One CheckBox");
 		return false;
 	} */
 	test++;
 	}else{
 	uncheckvalues+=rows[i].value+',';
 	}
 	}
 	if(checkvalues=='')
 	{
 	alert("Please select atleast one CheckBox");
 	}
 	else
 	{
 	var url="itIsssues.do?method=assignIssueReq";
 			document.forms[0].action=url;
 			document.forms[0].submit();
 	}
 }
 
 
 function newincident(){
 
 
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
 
var url="itIsssues.do?method=newIncidentform";
		document.forms[0].action=url;
		document.forms[0].submit();

}

  function closeRequest(){
	 
	 	var rows=document.getElementsByName("selectedRequestNo");
	 	var checkvalues='';
	 	var uncheckvalues='';
	 	var test=0;
	 	test=parseInt(test);
	 	for(var i=0;i<rows.length;i++)
	 	{
	 	if (rows[i].checked)
	 	{
	 	checkvalues+=rows[i].value+',';
	 	/* if(test>0){
	 		alert("Plese Select Only One CheckBox");
	 		return false;
	 	} */
	 	test++;
	 	}else{
	 	uncheckvalues+=rows[i].value+',';
	 	}
	 	}
	 	if(checkvalues=='')
	 	{
	 	alert("Please select atleast one CheckBox");
	 	}
	 	else
	 	{
	 	var url="itIsssues.do?method=closeRequest";
	 			document.forms[0].action=url;
	 			document.forms[0].submit();
	 	}
  }
function pickupIssue()
{

	var rows=document.getElementsByName("selectedRequestNo");
	var checkvalues='';
	var uncheckvalues='';
	var test=0;
	test=parseInt(test);
	for(var i=0;i<rows.length;i++)
	{
	if (rows[i].checked)
	{
	checkvalues+=rows[i].value+',';
	if(test>0){
		alert("Plese Select Only One CheckBox");
		return false;
	}
	test++;
	}else{
	uncheckvalues+=rows[i].value+',';
	}
	}
	if(checkvalues=='')
	{
	alert('please select atleast one record');
	}
	else
	{
	var url="itIsssues.do?method=pickupSelectedIssue";
			document.forms[0].action=url;
			document.forms[0].submit();
	}
}

function viewIssue(reqNo){
	var url="itIsssues.do?method=viewIssueDetails&ReqNo="+reqNo;
	document.forms[0].action=url;
	document.forms[0].submit();
}

function searchDetails(){
	document.forms[0].locationId.value="";
	var url="itIsssues.do?method=searchDetails";
	document.forms[0].action=url;
	document.forms[0].submit();	
	
}

function deleteIssue(reqNo){
	var r = confirm("Are you sure you want to Delete Issue!");
	if (r == true) {
		var url="itIsssues.do?method=deleteIssue&reqNo="+reqNo;
		document.forms[0].action=url;
		document.forms[0].submit();
	} else {
	    txt = "You pressed Cancel!";
	}
		
}
function hideMessage(){
	document.getElementById("messageID").style.visibility="hidden";	
}
function nextIssueRecord()
{

var url="itIsssues.do?method=nextIssueRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
			
			
}
function previousIssueRecord()
{
		
		var url="itIsssues.do?method=previousIssueRecord";
					document.forms[0].action=url;
					document.forms[0].submit();
		
}
function firstIssueRecord()
{
		var url="itIsssues.do?method=firstIssueRecord";
					document.forms[0].action=url;
					document.forms[0].submit();
		

}
function lastIssueRecord()
{

		var url="itIsssues.do?method=lastIssueRecord";
					document.forms[0].action=url;
					document.forms[0].submit();
	

}
function displayIssueDetails(){
	var ReqNo=document.forms[0].chooseKeyword.value;
	
	if(document.forms[0].chooseKeyword.value==""){
		alert("Please Enter Request No.");
		document.forms[0].chooseKeyword.focus();
		return false;
	}
	
	var xmlhttp;
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
	   // document.getElementById("techniciansID").innerHTML=xmlhttp.responseText;
	   
	   document.getElementById("reqNoStatusID").innerHTML=xmlhttp.responseText;
	   
	   var status=document.forms[0].reqNoStatus.value;
	   
	   if(status=="true"){ 
	    var url="itIsssues.do?method=viewIssueDetails&ReqNo="+ReqNo;
		document.forms[0].action=url;
		document.forms[0].submit();
		
	    }else{
	    	alert("No Request Number Found..");
	    }
	    }
	  }
	xmlhttp.open("POST","itIsssues.do?method=checkReqNo&reqNo="+ReqNo,true);
	xmlhttp.send();
	
	
}
function searchByLoc(){
	var url="itIsssues.do?method=searchByLocation";
	document.forms[0].action=url;
	document.forms[0].submit();
}


function onSave(){

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
     xmlhttp.open("POST","itHelpdesk.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
    xmlhttp.send();
}


function selectUser(input,reqFieldName){


	document.getElementById(reqFieldName).value=input;
	disableSearch(reqFieldName);
}

function disableSearch(reqFieldName){
  if(reqFieldName=="employeeno"){
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		}
	}
	}
	
	
	function  colr()
 { 
   if(document.forms[0].usage.value=="Others")
 {
 
  document.getElementById("emp").style.visibility="visible";
    document.getElementById("employeeno").readOnly=false;
 }
 else
 
  {
 document.forms[0].employeeno.value="";
  document.getElementById("emp").style.visibility="hidden";
  document.getElementById("employeeno").readOnly=true;
 }
 
 
 }


function showform(){
	var url="itIsssues.do?method=displayAllIssues&search=true";
	document.forms[0].action=url;
	document.forms[0].submit();
}


 </script>
 
 <style>
#modalContainer {
	background-color:rgba(0, 0, 0, 0.3);
	position:absolute;
	width:100%;
	height:100%;
	top:0px;
	left:0px;
	z-index:10000;
	background-image:url(tp.png); /* required by MSIE to prevent actions on lower z-index elements */
}

#alertBox {
	position:relative;
	width:300px;
	min-height:100px;
	margin-top:220px;
	border:1px solid #666;
	background-color:#fff;
	background-repeat:no-repeat;
	background-position:20px 30px;
	font-size: 15px;
}

#modalContainer > #alertBox {
	position:fixed;
}

#alertBox h1 {
	margin:0;
	font:bold 0.9em verdana,arial;
	background-color:rgb(125,170,201);
	color:#FFF;
	border-bottom:1px solid #000;
	padding:2px 0 2px 5px;
}

#alertBox p {
	font:0.7em verdana,arial;
	height:50px;
	padding-left:5px;
	margin-left:55px;
}

#alertBox #closeBtn {
	display:block;
	position:relative;
	margin:5px auto;
	padding:7px;
	border:0 none;
	width:70px;
	font:0.7em verdana,arial;
	text-transform:uppercase;
	text-align:center;
	color:#FFF;
	background-color:rgb(125,170,201);
	border-radius: 3px;
	text-decoration:none;
}

/* unrelated styles */

#mContainer {
	position:relative;
	width:600px;
	margin:auto;
	padding:5px;
	border-top:2px solid #000;
	border-bottom:2px solid #000;
	font:0.7em verdana,arial;
}



</style>
<style>
.fa-spinner {
    color:blue;
}

  .fa-check-square {
    color:green;
}

.fa-square{
color:grey;
}
                   </style> 

	<script>
	var ALERT_TITLE = "Information";
var ALERT_BUTTON_TEXT = "OK";

if(document.getElementById) {
	window.alert = function(txt,value) {
		createCustomAlert(txt,value);
	}
}

function createCustomAlert(txt,value) {
	d = document;

	if(d.getElementById("modalContainer")) return;

	mObj = d.getElementsByTagName("body")[0].appendChild(d.createElement("div"));
	mObj.id = "modalContainer";
	mObj.style.height = d.documentElement.scrollHeight + "px";
	
	alertObj = mObj.appendChild(d.createElement("div"));
	alertObj.id = "alertBox";
	if(d.all && !window.opera) alertObj.style.top = document.documentElement.scrollTop + "px";
	alertObj.style.left = (d.documentElement.scrollWidth - alertObj.offsetWidth)/2 + "px";
	alertObj.style.visiblity="visible";

	h1 = alertObj.appendChild(d.createElement("h1"));
	h1.appendChild(d.createTextNode(ALERT_TITLE));

	msg = alertObj.appendChild(d.createElement("p"));
	//msg.appendChild(d.createTextNode(txt));
	msg.innerHTML = txt;

	btn = alertObj.appendChild(d.createElement("a"));
	btn.id = "closeBtn";
	btn.appendChild(d.createTextNode(ALERT_BUTTON_TEXT));
	btn.href = "#";
	btn.focus();
	btn.onclick = function() { removeCustomAlert(value);return false; }

	alertObj.style.display = "block";
	
}

function removeCustomAlert(value) {
  
  

	document.getElementsByTagName("body")[0].removeChild(document.getElementById("modalContainer"));
	
	
 }

 
 
 	function statusMessage(message){
				alert(message);
			
				
		
				}
				
			function pending(){
	var url="itIsssues.do?method=displayAllIssues&search=";
	document.forms[0].action=url;
	document.forms[0].submit();
}
 </script>
 

  </head>
  
  <body>
   <html:form action="/itIsssues.do" enctype="multipart/form-data" onsubmit="displayIssueDetails(); return false;">
   
<div align="center" id="messageID" style="visibility: true;">
  <div align="center">
		<logic:notEmpty name="issuesForm" property="message">
	<script language="javascript">
					statusMessage('<bean:write name="issuesForm" property="message" />','');
					</script>
	</logic:notEmpty>
	</div>
	<div align="center">
	<%-- 	<logic:present name="issuesForm" property="message2">
		<script language="javascript">
					statusMessage('<bean:write name="issuesForm" property="message2" />','');
					</script>
	</logic:present> --%>
 </div>
	</div>
<div style="width: 100%">
<table class="bordered " >
 <tr>

<th><b>Category</b></th>
<td>
	<html:select property="maincategory" styleClass="content"  >
		<html:option value="">-----Select---------</html:option>

		<html:option value="Hardware">Hardware</html:option>
		<html:option value="Internet">Internet</html:option>
		<html:option value="Network">Network</html:option>
		<html:option value="Operating System">Operating System</html:option>
		<html:option value="Printers">Printers</html:option>
		<html:option value="Software">Software</html:option>							
		<html:option value="Telephone">Telephone</html:option>
		<html:option value="SAP Users">SAP Users</html:option>
		<html:option value="SAP Core Team">SAP Core Team</html:option>

		
    </html:select>
</td>

<th>Req.Status</th><td colspan="2"><html:select property="mainrequestStatus" styleClass="content">
<html:option value="">-----Select---------</html:option>

	<html:option value="New"/>
	<html:option value="Sent To IT"/>
	<html:option value="User Action"/>	
	<html:option value="On Hold"/>
	<html:option value="In Process"/>
	<html:option value="Forwarded"/>
	<html:option value="Completed"/>
	<html:option value="ON_Behalf"/>



</html:select>
<th>Date</th>
<td width="600px" colspan="1">
<html:text property="fromDate" name="issuesForm" styleId="popupDatepicker" style="width: 98px; "/>&nbsp;To&nbsp;
<html:text property="toDate" name="issuesForm" styleId="popupDatepicker2" style="width: 98px; "/>


</td>

    </tr>
<tr>

</td>
<th>Location </th>
	<td align="left">
		<html:select  property="locationId" name="issuesForm">
			<html:option value="All">ALL</html:option>
			<html:options name="issuesForm"  property="locationIdList" labelProperty="locationLabelList"/>
		</html:select>
	</td>
<th>Dept</th>
	<td  colspan="2">
	<html:select property="department" name="issuesForm"  styleClass="rounded">
		<html:option value="">--Select--</html:option>
      			<html:options property="departmentIdList" labelProperty="departmentList" name="issuesForm" ></html:options>  
   			</html:select>
   			
   			
     </td> 	
     
     
     <th colspan="7">
<img src="images/search.png" width="26" height="24" onclick="showform()" align="absmiddle"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<html:text property="chooseKeyword" title="Enter Request Number" styleClass="rounded"/>&nbsp;&nbsp;&nbsp;
<html:button property="method" value="Go" styleClass="rounded" onclick="displayIssueDetails()" style="width:100px;"/>
</th>					
</tr>

<tr>

</tr></table><table class="bordered">
<tr><th >
<html:button property="method" value="New Incident" onclick="newincident()" styleClass="rounded"/>&nbsp;
<logic:equal value="Technician" property="empType" name="issuesForm">
<html:button property="method" value="Pick Up" styleClass="rounded" onclick="pickupIssue()"/>&nbsp;<%-- <html:button property="method" value="Close" styleClass="rounded" onclick="closeRequest()"/> --%>
&nbsp;<html:button property="method" value="My Pending List" styleClass="rounded" onclick="pending()"/>

<%-- <html:select property="issAssignedTechn">
<html:option value="">--Select Technician</html:option>
<html:options name="issuesForm" property="techIDs" labelProperty="techNames"/>
</html:select>       
&nbsp;
<html:button property="method" value="Assign" styleClass="rounded" onclick="assignIssueReq()"/>--%> 
</logic:equal>

<div style="float: right;">

<b>For Use</b> <font color="red">*</font>	
				
						<html:select property="usage" styleClass="content" styleId="filterId" onchange="colr()" >
							<html:option value="Yourself">Self</html:option>
							<html:option value="Others">On Behalf Of Others</html:option>
							
							</html:select>
						

&nbsp;&nbsp;&nbsp;&nbsp;
Employee No <font color="red" style="visibility:hidden;" id="emp">*</font>

	<td><html:text property="employeeno"  onkeyup="searchEmployee('employeeno')" styleId="employeeno" style="width: 84px; "  readonly="true">
	<bean:write property="employeeno" name="issuesForm" /></html:text>
	<div id="sU" style="display:none;">
		<div id="sUTD" style="width:100px;">
		<iframe src="jsp/it/HelpDesk/searchemployee.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="60%"></iframe>
		</div>
	</div></td>
				
		&nbsp;&nbsp;
					</div>
</th>
</tr>
</table>
</div>
<div>&nbsp;</div>
 <div align="center">
	<logic:notEmpty name="displayRecordNo">
	  	<a href="#"><img src="images/First10.jpg" onclick="firstIssueRecord()" align="absmiddle"/></a>
	<logic:notEmpty name="disablePreviousButton">
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	</logic:notEmpty>
	<logic:notEmpty name="previousButton">
	<a href="#"><img src="images/previous1.jpg" onclick="previousIssueRecord()" align="absmiddle"/></a>
	</logic:notEmpty>
	<bean:write property="startRecord"  name="issuesForm"/>-
	<bean:write property="endRecord"  name="issuesForm"/>
	<logic:notEmpty name="nextButton">
	<a href="#"><img src="images/Next1.jpg" onclick="nextIssueRecord()" align="absmiddle"/></a>
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	<img src="images/disableRight.jpg" align="absmiddle" />
	</logic:notEmpty>
	<a href="#"><img src="images/Last10.jpg" onclick="lastIssueRecord()" align="absmiddle"/></a>
	</div>
	<div>&nbsp;</div>
	</logic:notEmpty>
<logic:notEmpty name="IssuesList">
<table class="bordered ">
<logic:equal value="Technician" property="empType" name="issuesForm">
<logic:notEmpty name="displayRecordNo">
<tr><th colspan="15"><center>My Search List</center></th></tr>
</logic:notEmpty >
<logic:empty name="displayRecordNo">
<tr><th colspan="15"><center>My Pending List</center></th></tr>
</logic:empty>
<%-- <logic:empty name="displayRecordNo1">
<tr><th colspan="15"><center>My Pending List</center></th></tr>

</logic:empty> --%>

</logic:equal>
<logic:notEqual value="Technician" property="empType" name="issuesForm">
<tr><th colspan="15"><center>My List</center></th></tr>
</logic:notEqual>

<tr><logic:equal value="Technician" property="empType" name="issuesForm"><th>&nbsp;</th></logic:equal><th>Req.No</th><th>Req.Date<th>Location</th><th>Employee Name</th><th>Category</th><th>Sub Category</th><th>Description</th>
<th>Priority</th><th>Status</th><th>Assign To</th><!-- <th>Edit</th> -->

<th>Delete</th></tr>
<logic:iterate id="issue" name="IssuesList">


 <tr <logic:equal value="0" name="issue" property="readStatus">  class="boldFont"  </logic:equal> 
 <logic:equal value="1" name="issue" property="readStatus">  class="unBoldFont"  </logic:equal>
 >
<logic:equal value="Technician" property="empType" name="issuesForm">
<td><html:checkbox property="selectedRequestNo" name="issuesForm" value="${issue.requestNo}"/></td>
</logic:equal>
<logic:notEqual value="Technician" property="empType" name="issuesForm">
<td><a href="#" onclick="viewIssue('<bean:write name="issue" property="requestNo"/>')"><bean:write name="issue" property="requestNo"/></a></td>
</logic:notEqual>
<logic:equal value="Technician" property="empType" name="issuesForm">
<td><bean:write name="issue" property="requestNo"/></td>
</logic:equal>

<td><bean:write name="issue" property="reqDate"/></td>
<td><bean:write name="issue" property="location"/></td>
<td><bean:write name="issue" property="employeename"/></td>
<td><bean:write name="issue" property="category"/></td>
<td><bean:write name="issue" property="subcategory"/></td>
<td><bean:write name="issue" property="subject"/></td>

       <td width="10%;">
   <logic:equal value="Very High" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: red;"></canvas>
			           </logic:equal>
			             <logic:equal value="High" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: #990033;"></canvas>
			           </logic:equal>
			             <logic:equal value="Low" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: green;"></canvas>
			           </logic:equal>
			             <logic:equal value="Medium" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: orange;"></canvas>
			           </logic:equal>
			             <logic:equal value="Normal" name="issue" property="reqPriority">
			            <canvas   width="10" height="15" style="border:0px solid #000000;background-color: brown;"></canvas>
			           </logic:equal>

<bean:write name="issue" property="reqPriority"/></td>
<td width="50px"><logic:equal name="issue" property="slaStatus" value="SLA"><img src="images/flag.png" title="SLA Violated" height="15" width="15"/>&nbsp;<bean:write name="issue" property="requestStatus"/></logic:equal>
<logic:equal name="issue" property="slaStatus" value=""><bean:write name="issue" property="requestStatus"/></logic:equal></td>
<td><bean:write name="issue" property="assignTo"/></td>
<%-- <td>&nbsp;<a href="itIsssues.do?method=editIssueDetails&RequestNo=${issue.requestNo }" title="Edit"><img src="images/edit1.jpg"/></a></td>
 --%><%-- <td><a href="#"><img src="images/view.gif" onclick="viewIssue('<bean:write name="issue" property="requestNo"/>')" height="28" width="28" title="View Record"/></a></td> --%>
<td>&nbsp;
<logic:equal value="New"  property="requestStatus" name="issue">
<a href="#" onclick="deleteIssue('<bean:write name="issue" property="requestNo"/>')" title="Delete Issue"><img src="images/delete.png"/></a>
</logic:equal>
</td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<div id="reqNoStatusID">


</div>


<logic:notEmpty name="noRecords">
<table class="bordered sortable">
<tr><th>&nbsp;</th><th>Req.No</th><th>Location</th><th>Emp.Name</th><th>Category</th><th>Sub Category</th><th>Priority</th>
<th>Status</th><!-- <th>Edit</th> --><th>Delete</th></tr>
<tr><td colspan="9"><center><font color="red">No Records Found</font></center></td></tr>
</table>
</logic:notEmpty>
<html:hidden property="requestType"/>

<html:hidden  property="requestNumber"/>

<html:hidden property="totalRecords" />
<html:hidden property="startRecord" />
<html:hidden property="endRecord" />

<html:hidden property="empType" />
<logic:notEqual value="Technician" property="empType" name="issuesForm">
<html:hidden property="locationId" />

</logic:notEqual>
</html:form>

  </body>
</html>
