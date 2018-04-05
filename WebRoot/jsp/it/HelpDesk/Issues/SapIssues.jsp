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
 
var url="itIsssues.do?method=sapnewIncidentform";
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
var locationId=document.forms[0].locationId.value;

if(locationId==""){	
var url="itIsssues.do?method=nextIssueRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}else{
	var url="itIsssues.do?method=nextLocationIssues";
	document.forms[0].action=url;
	document.forms[0].submit();
}			
			
}
function previousIssueRecord()
{
	var locationId=document.forms[0].locationId.value;
	if(locationId==""){	
		var url="itIsssues.do?method=previousIssueRecord";
					document.forms[0].action=url;
					document.forms[0].submit();
		}else{
			var url="itIsssues.do?method=preLocationIssues";
			document.forms[0].action=url;
			document.forms[0].submit();
		}
}
function firstIssueRecord()
{
	var locationId=document.forms[0].locationId.value;
	if(locationId==""){	
		var url="itIsssues.do?method=firstIssueRecord";
					document.forms[0].action=url;
					document.forms[0].submit();
		}else{
			var url="itIsssues.do?method=firstLocationIssues";
			document.forms[0].action=url;
			document.forms[0].submit();
		}

}
function lastIssueRecord()
{
	var locationId=document.forms[0].locationId.value;
	if(locationId==""){	
		var url="itIsssues.do?method=lastIssueRecord";
					document.forms[0].action=url;
					document.forms[0].submit();
		}else{
			var url="itIsssues.do?method=lastLocationIssues";
			document.forms[0].action=url;
			document.forms[0].submit();
		}

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


var res = input.split("-");
	document.getElementById(reqFieldName).value=res[1];
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

 </script>

  </head>
  
  <body>
   <html:form action="/itIsssues.do" enctype="multipart/form-data" onsubmit="displayIssueDetails(); return false;">
   
  
<div style="width: 65%">
<table class="bordered " >

<tr><th>
<html:button property="method" value="New Issue" onclick="newincident()" styleClass="rounded"/>&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<b>For Use</b> <font color="red">*</font>	
				
						<html:select property="usage" styleClass="content" styleId="filterId" onchange="colr()" >
							<html:option value="Yourself">Self</html:option>
							<html:option value="Others">Behalf Of Others</html:option>
							
							</html:select>
						


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Employee No <font color="red" style="visibility:hidden;" id="emp">*</font>

	<td><html:text property="employeeno"  onkeyup="searchEmployee('employeeno')" styleId="employeeno" style="width: 84px; " readonly="true" >
	<bean:write property="employeeno" name="issuesForm" /></html:text>
	<div id="sU" style="display:none;">
		<div id="sUTD" style="width:100px;">
		<iframe src="jsp/it/HelpDesk/searchemployee.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="60%"></iframe>
		</div>
	</div></td>
				
		&nbsp;&nbsp;
					
</th>
</tr>
</table>
</div>
<div>&nbsp;</div>
<%--  <div align="center">
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
	</logic:notEmpty> --%>
<%-- <logic:notEmpty name="IssuesList">
<table class="bordered sortable">
<tr><th>&nbsp;</th><th>Req.No</th><th>Req.Date<th>Location</th><th>Employee Name</th><th>Category</th><th>Sub Category</th><th>Descripton</th>
<th>Priority</th><th>Status</th><th>Assign To</th><!-- <th>Edit</th> -->

<th>View</th><th>Delete</th></tr>
<logic:iterate id="issue" name="IssuesList">


 <tr <logic:equal value="0" name="issue" property="readStatus">  class="boldFont"  </logic:equal> 
 <logic:equal value="1" name="issue" property="readStatus">  class="unBoldFont"  </logic:equal>
 >

<td><html:checkbox property="selectedRequestNo" name="issuesForm" value="${issue.requestNo}"/></td>
<td><bean:write name="issue" property="requestNo"/></td>
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
<td>&nbsp;<a href="itIsssues.do?method=editIssueDetails&RequestNo=${issue.requestNo }" title="Edit"><img src="images/edit1.jpg"/></a></td>
<td><a href="#"><img src="images/view.gif" onclick="viewIssue('<bean:write name="issue" property="requestNo"/>')" height="28" width="28" title="View Record"/></a></td>
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
</logic:notEmpty> --%>
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
