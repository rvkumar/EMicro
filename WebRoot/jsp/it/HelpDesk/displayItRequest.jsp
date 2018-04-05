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
 <script type="text/javascript">
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
 </script>

  </head>
  
  <body  >
   <html:form action="/itHelpdesk.do" enctype="multipart/form-data" onsubmit="onSave(); return false;">
   <html:hidden property="username" />
   <div align="center">
		<logic:notEmpty name="itHelpdeskForm" property="message">
		<font color="green" size="3">
			<b><bean:write name="itHelpdeskForm" property="message" /></b>
		</font>  
	</logic:notEmpty>
	</div>
	<div align="center">   
		<logic:present name="itHelpdeskForm" property="message1">
		<font color="red" size="3">
			<b><bean:write name="itHelpdeskForm" property="message1" /></b>
		</font>
	</logic:present>

<table class="bordered " width="130%">
 
<tr>


<th><b>Requirement Type</b> <font color="red">*</font></th>
						<td>
						<html:select property="requestType" styleClass="content" styleId="filterId" onchange="showform()">
						
							<html:option value="">--Select--</html:option>
						
							<html:option value="Active Directory User Creation">Active Directory User Creation</html:option>
						    <html:option value="Active Directory User Deletion">Active Directory User Deletion</html:option>
							<html:option value="Active Directory User Transfer">Active Directory User Transfer</html:option>
						    <html:option value="Email ID Create Request">Email ID Create Request</html:option>
							<html:option value="Email ID Change Request">Email ID Change Request</html:option>
							<html:option value="Email ID Delete Request">Email ID Delete Request</html:option>
							<html:option value="Internet Access Request">Internet Access Request</html:option>
							<html:option value="External Drives Access Request">External Drives Access Request</html:option>
							<html:option value="FTP Access Request">FTP Access Request</html:option>
							<html:option value="File Server Access Request">File Server Access Request</html:option>
							<html:option value="New IT Asset Request">New IT Asset Request</html:option>
							<html:option value="New IT Spares Request">New IT Spares Request</html:option>
				         	<%-- <html:option value="Qms Application Access">Qms Application Access</html:option> --%>
					
							</html:select>
						</td>
					<th><b>For Use</b> <font color="red">*</font></th>	
					<td>
						<html:select property="usage" styleClass="content" styleId="filterId" onchange="colr()" >
							<html:option value="Yourself">Self</html:option>
							<html:option value="Others">Behalf Of Others</html:option>
							
							</html:select>
						</td>	


<th>Employee No <font color="red" style="visibility:hidden;" id="emp">*</font></th>

	<td><html:text property="employeeno"  onkeyup="searchEmployee('employeeno')" styleId="employeeno" readonly="true" style="width: 84px; ">
	<bean:write property="employeeno" name="itHelpdeskForm" /></html:text>
	<div id="sU" style="display:none;">
		<div id="sUTD" style="width:80px;">
		<iframe src="jsp/it/HelpDesk/searchemployee.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="60%"></iframe>
		</div>
	</div>
				
		&nbsp;&nbsp;<html:button property="method" value="Continue" onclick="onSave()" styleClass="rounded" style="width: 81px;"/>
					</td>
</tr>						
</table>




</html:form>
  </body>
</html>
