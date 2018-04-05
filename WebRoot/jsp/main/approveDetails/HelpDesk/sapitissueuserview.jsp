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
	</style>
 <script type="text/javascript">
 
function getCurrentRecord(){

  	var reqId = document.forms[0].requestNumber.value;
 	
  	var reqType = document.forms[0].category1.value;

 	
 	var url="approvals.do?method=curentRecord&reqId="+reqId+"&reqType="+reqType;
	
 	document.forms[0].action=url;
 	document.forms[0].submit();


 }
 
 
function changeStatus(elem){


var elemValue=elem.value;

if(elemValue=="Completed")
{
   elemValue="Approve";
}

if(elemValue=="Reject")
	{
	if(document.forms[0].comments.value=="")
	{
	  alert("Please Add Some Comments");
	       document.forms[0].comments.focus();
	         return false;
	  }
	
	}
if(document.forms[0].comments.value!="")
{
	
	var st = document.forms[0].comments.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].comments.value=st;
}
  
 	var reqId = document.forms[0].requestNumber.value;
 	
 	var reqType = document.forms[0].category1.value;
 	
    var reqsubcategory = document.forms[0].subcategory1.value;
    
    

 	var url="approvals.do?method=statusChangeForsapITRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&reqsubcategory="+reqsubcategory;
//var url="approvals.do?method=statusChangeForsapITRequest";	
	
	document.forms[0].action=url;
	document.forms[0].submit();
} 


function submitData(elem){
var elemValue=elem.value;
if(elemValue=="Reject")
	{
	if(document.forms[0].comments.value==""){
	  alert("Please Add Some Comments");
	       document.forms[0].comments.focus();
	         return false;
	  }
	
	
	}
if(document.forms[0].comments.value!=""){
	var st = document.forms[0].comments.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].comments.value=st;
}
  
	var elemValue = elem.value;
	if(elemValue=="Resolved")
	{
	
	elemValue="Approve";
	
	}
	
	
	var reqId = document.forms[0].requestNo.value;
	
	var reqType = document.forms[0].requestType.value;

	var url="approvals.do?method=statusChangeForITRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue;
	
	
	document.forms[0].action=url;
	document.forms[0].submit();
} 


function getDisplayApprovers(technicians){
	var category=document.forms[0].category.value;
	var location=document.forms[0].locNo.value;
	var subCategory=document.forms[0].subCategory.value;
	var technician=document.forms[0].techinician.value;
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
	    document.getElementById("Aprroverid").innerHTML=xmlhttp.responseText;
	    }
	  }
	xmlhttp.open("POST","itIsssues.do?method=displayApprovers&category="+category+"&subCategory="+subCategory+"&locNo="+location+"&technician="+technician,true);
	xmlhttp.send();
} 



function getSubcat(category)
{
 
var xmlhttp;
var dt;
dt=category;
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
    document.getElementById("subcategoryID").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","itIsssues.do?method=getsapsubcategory&linkName="+dt,true);
xmlhttp.send();
}
function uploadDocument()
{
	if(document.forms[0].category.value=="")
	{
	alert("Please Select Category");
	document.forms[0].category.focus();
     return false;
	}
	
	if(document.forms[0].subCategory.value=="")
	{
	alert("Please Select Sub-Category");
	document.forms[0].subCategory.focus();
     return false;
	}
	
	if(document.forms[0].techinician.value=="")
	{
	alert("Please Select Techician");
	document.forms[0].techinician.focus();
     return false;
	}
	
	if(document.forms[0].documentFile.value==""){
		alert("Please Choose Atleast One File");
		document.forms[0].documentFile.focus();
		return false;
	}
	var fileName=document.forms[0].documentFile.value;
	var ext = fileName.split('.').pop();
   
    if(ext=="jpg"||ext=="png"||ext=="pdf"||ext=="txt"||ext=="xlsx"||ext=="xls"||ext=="doc"||ext=="docx"){
    
    }else{
	alert("Please Upload only jpg,png,pdf,txt,xls,xlsx,doc,docx extension files");
    	
    	return false;
    }
	var subCategory="";
	var technician="";
	 subCategory=document.forms[0].subCategory.value;
	 technician=document.forms[0].techinician.value;
	document.forms[0].action="itIsssues.do?method=uploadDocuments&subCategory="+subCategory+"&technician="+technician;
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
	var subCategory="";
	var technician="";
	 subCategory=document.forms[0].subCategory.value;
	 technician=document.forms[0].techinician.value;
	document.forms[0].action="itIsssues.do?method=deleteDocuments&cValues="+checkvalues+"&subCategory="+subCategory+"&technician="+technician;
document.forms[0].submit();
}
}
}
function back(){
	document.forms[0].action="itIsssues.do?method=displayAllIssues";
	document.forms[0].submit();
}

function setSubCatTech(subCategory,Technician){
	 alert("Documents has been updated");
	 document.forms[0].subCategory.value=subCategory;
	 document.forms[0].techinician.value=Technician;
}
function submitData(){
	if(document.forms[0].issuePriority.value=="")
	{
	alert("Please Select Priority");
	document.forms[0].issuePriority.focus();
     return false;
	}

if(document.forms[0].category.value=="")
{
alert("Please Select Category");
document.forms[0].category.focus();
 return false;
}

if(document.forms[0].subCategory.value=="")
{
alert("Please Select Sub-Category");
document.forms[0].subCategory.focus();
 return false;
}

if(document.forms[0].techinician.value=="")
{
alert("Please Select Techician");
document.forms[0].techinician.focus();
 return false;
}

if(document.forms[0].subject.value=="")
{
alert("Please Enter Subject");
document.forms[0].subject.focus();
 return false;
}
var st = document.forms[0].subject.value;
var Re = new RegExp("\\'","g");
st = st.replace(Re,"`");
document.forms[0].subject.value=st;  
var st = document.forms[0].reason.value;
var Re = new RegExp("\\'","g");
st = st.replace(Re,"`");
document.forms[0].reason.value=st; 
	var subCategory=document.forms[0].subCategory.value;
	 var technician=document.forms[0].techinician.value;
	 var location=document.forms[0].locNo.value;
	 var category=document.forms[0].category.value;
	document.forms[0].action="itIsssues.do?method=sapsubmitReq&subCategory="+subCategory+"&technician="+technician+"&category="+category;
	document.forms[0].submit();
}

 </script>

  </head>
  
  <body>
   <html:form action="/itIsssues.do" enctype="multipart/form-data">
   
   <div align="center" id="messageID" style="visibility: visible;">
			<logic:present name="ApprovalsForm" property="message">
				<font color="green" size="3"><b><bean:write name="ApprovalsForm" property="message" /></b></font>
				
			</logic:present>
			<logic:present name="ApprovalsForm" property="message2">
				<font color="red" size="3"><b><bean:write name="ApprovalsForm" property="message1" /></b></font>
				
			</logic:present>
		</div>
   

   <html:hidden  property="requestNumber" name="approvalsForm" />
   <html:hidden  property="category1" name="approvalsForm" />
   <html:hidden  property="subcategory1" name="approvalsForm" />
   <html:hidden  property="reqRequstType" name="approvalsForm" />
   <html:hidden  property="selectedFilter" name="approvalsForm" />
   <html:hidden  property="startRecord" name="approvalsForm" />
   <html:hidden  property="totalRecords" name="approvalsForm" />
   <html:hidden  property="endRecord" name="approvalsForm" />
   
   
   
   
   
  <%--  
   <input style="visibility:hidden;" id="totalReco" value="<bean:write property="totalRecords"  name="approvalsForm"/>"/>
<input style="visibility:hidden;" id="filterby" value="<bean:write property="selectedFilter"  name="approvalsForm"/>"/> --%>
   
   
   
 
   
   <div align="center">
		<logic:notEmpty name="approvalsForm" property="message">
		<font color="green" size="3">
			<b><bean:write name="approvalsForm" property="message" /></b>
		</font>
	</logic:notEmpty>
	</div>
	<div align="center">
		<logic:present name="approvalsForm" property="message2">
		<font color="red" size="3">
			<b><bean:write name="approvalsForm" property="message2" /></b>
		</font>
	</logic:present>
 </div>

<div style="width: 100%">
<table class="bordered " >
<html:hidden property="reqPriority" />
<html:hidden property="requestNo" />
<html:hidden property="locNo" />
<html:hidden property="empno" />
<html:hidden property="empEmailID" />
<html:hidden property="extno" />
<html:hidden property="ipPhoneno" />


<tr><th colspan="4"><center><big> <bean:write name="approvalsForm" property="requestName"/></big></center></th></tr>

<tr><th colspan="4"><big>Requester Details</big></th></tr>
<tr><td><b>Name:</b></td><td> <bean:write name="approvalsForm" property="requestername"/></td>

<td><b>Employee No:</b></td><td ><bean:write name="approvalsForm" property="empno"/></td></tr>
<tr>
<td><b>Department:</b></td><td ><bean:write name="approvalsForm" property="requesterdepartment"/></td>
<td><b>Designation:</b></td><td ><bean:write name="approvalsForm" property="requesterdesignation"/></td></tr>
<tr>
<td><b>Location:</b></td><td ><bean:write name="approvalsForm" property="location"/></td>
<td><b>Ext No:</b></td><td ><bean:write name="approvalsForm" property="extno"/></td></tr>

<tr>
<td><b>IP Phone No:</b></td><td ><bean:write name="approvalsForm" property="ipPhoneno"/></td>


<html:hidden property="hostname" />
<html:hidden property="IPNumber" />


<td><b>IP Address:</b></td><td ><bean:write name="approvalsForm" property="IPNumber"/></td></tr>

<tr><th colspan="4"><big>Classification</big></th></tr>
<tr>
 <td>Request No</td> 
 <td><bean:write name="approvalsForm" property="requestNo"/></td> 
<td >Status Of Message&nbsp;<font color="red" >*</font></td><td  colspan="2">
<bean:write name="approvalsForm" property="requestStatus"/>
</td>
</tr>
<tr>
<td >Priority<font color="red" >*</font></td><td >
 <bean:write name="approvalsForm" property="priority"/>
</td><td >Mode</td><td >
<bean:write name="approvalsForm" property="mode"/>
</td>


</tr>
<tr>
<td><b>Category</b> <font color="red">*</font></td>
						<td>
						<bean:write name="approvalsForm" property="category"/>
						</td>
<td><b>Sub-Category</b> <font color="red">*</font></td>
						<td>
						
						<bean:write name="approvalsForm" property="subcategory"/>
	   
						</td>
						</tr>
						
						<logic:notEmpty name="renameId">
						<tr>
						<td>Exiting ID</td>
						<td><bean:write name="approvalsForm" property="oldId"/></td>
							<td>Proposed ID</td>
						<td><bean:write name="approvalsForm" property="newId"/></td>
						</tr>
						
						
						</logic:notEmpty>
						<logic:notEmpty name="role">
						<tr>
						<td>Job Profile/ Role </td>
						<td colspan="4"><bean:write name="approvalsForm" property="roleId"/></td>
						</tr></logic:notEmpty>
			
			
			
		 
</td>
</tr>
<tr>
<th  colspan="4"><big>USER ID Details</big></th>
</tr>
<tr>
<td><b>Subject:&nbsp;<font color="red" >*</font></b></td><td colspan="3"><html:text  name="approvalsForm" property="subject" style="width: 430px; " maxlength="50" disabled="false"></html:text></td></tr>

<tr>
	<td><b>Detailed Information with Justification:&nbsp;</b></td>
<td colspan="3"><html:textarea property="reason" name="approvalsForm" cols="80" rows="5" style="height: 88px; width: 657px;" disabled="false">

</html:textarea>	
	</td>
<tr>
		<td>
		<b>Comments</b></td>
		<td colspan="4">
<html:textarea property="comments" style="width:657px;"></html:textarea>		
		
		</td>
		</tr>	
</tr>
<tr>
            
          </tr>		
   <logic:notEmpty name="requesterDetails">
 <tr>
	<th colspan="4">Attachments </th>
	</tr>
	<logic:iterate id="abc" name="requesterDetails">
	<tr>
		<td colspan="4">
			<%-- <a href="/EMicro Files/IT/Help Desk/SapIssues/UploadFiles/${abc.fileName}"  target="_blank">  <bean:write name="abc" property="fileName"/></a> --%>
		<a href="${abc.fileFullPath}"  target="_blank">  <bean:write name="abc" property="fileName"/></a>
		</td>
	</tr>
	</logic:iterate>
</logic:notEmpty>
          
						
						<tr>
<td colspan="4" style="text-align: center;">
    <logic:notEmpty name="approveButton" > 
	<html:button property="method"  value="Approve" onclick="changeStatus(this)" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
		</logic:notEmpty>  
	<logic:notEmpty name="Complete">
	<html:button property="method"  value="Completed" onclick="changeStatus(this)" styleClass="rounded" style="width: 100px"></html:button>
	</logic:notEmpty>
	<logic:notEmpty name="rejectButton">
	<html:button property="method"  value="Reject" onclick="changeStatus(this)" styleClass="rounded" style="width: 100px"></html:button>
	</logic:notEmpty>
	
	
	
	<html:button property="method"  value="Close" onclick="getCurrentRecord()" styleClass="rounded" style="width: 100px"></html:button>
</td>
</tr>
</table>
</div>
<div>&nbsp;</div>
<center>

<logic:notEmpty name="apprList">
	<table class="bordered" >
	
	<tr><th colspan="6">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Comments</th><th>Date</th></tr>
	<logic:iterate id="abc" name="apprList">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeeName }</td><td>${abc.status }</td><td>${abc.comments }</td><td>${abc.date}</td></tr>
	</logic:iterate>
</table>
</logic:notEmpty>

</center>
<html:hidden property="employeeno"/>


<%-- <logic:notEmpty name="setSubCategAndTechns">
<script type="">
getSubcat('<bean:write name="issuesForm" property="category"/>');
getTechnicians('<bean:write name="issuesForm" property="selectedSubCategory"/>');

setSubCatTech('<bean:write name="issuesForm" property="selectedSubCategory"/>','<bean:write name="issuesForm" property="selectedTechnician"/>');
</script>

</logic:notEmpty>
 --%>
<!-- <div id="Aprroverid" align="left">
</div> -->


</html:form>

  </body>

</html>