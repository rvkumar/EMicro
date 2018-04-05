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
function getTechnicians(subCategory){
	var category=document.forms[0].category.value;
	var location=document.forms[0].locNo.value;
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
	    document.getElementById("techniciansID").innerHTML=xmlhttp.responseText;
	    }
	  }
	xmlhttp.open("POST","itIsssues.do?method=getTechnicians&category="+category+"&subCategory="+subCategory+"&locNo="+location,true);
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
xmlhttp.open("POST","itIsssues.do?method=getsubcategory&linkName="+dt,true);
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
	 
	 var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	 
	document.forms[0].action="itIsssues.do?method=submitReq&subCategory="+subCategory+"&technician="+technician;
	document.forms[0].submit();
}

 </script>
 
 
 <style>

.no
{
pointer-events: none; 

}

.design

{

	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);

} 


</style>

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
 </script>

  </head>
  
  <body>
   <html:form action="/itIsssues.do" enctype="multipart/form-data">
      <div id="masterdiv" class="">
   <div align="center">
		<logic:notEmpty name="issuesForm" property="message">
	<script language="javascript">
					statusMessage('<bean:write name="issuesForm" property="message" />','');
					</script>
	</logic:notEmpty>
	</div>
	<div align="center">
		<logic:present name="issuesForm" property="message2">
		<script language="javascript">
					statusMessage('<bean:write name="issuesForm" property="message2" />','');
					</script>
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


<tr><th colspan="4"><center><big>New Incident </big></center></th></tr>

<logic:notEmpty name="Recordsfound">
  
<tr><td colspan="4"><b><center><font color="red">NOTE: This request needs to be "accepted" by the actual user for further process.</font></center></b></td></tr></logic:notEmpty>

<tr><th colspan="4"><big>Requester Details</big></th></tr>
<tr><td><b>Name:</b></td><td> <bean:write name="issuesForm" property="requestername"/></td>

<td><b>Employee No:</b></td><td ><bean:write name="issuesForm" property="empno"/></td></tr>
<tr>
<td><b>Department:</b></td><td ><bean:write name="issuesForm" property="requesterdepartment"/></td>
<td><b>Designation:</b></td><td ><bean:write name="issuesForm" property="requesterdesignation"/></td></tr>
<tr>
<td><b>Location:</b></td><td ><bean:write name="issuesForm" property="location"/></td>
<td><b>Ext No:</b></td><td ><bean:write name="issuesForm" property="extno"/></td></tr>

<tr>
<td><b>IP Phone No:</b></td><td ><bean:write name="issuesForm" property="ipPhoneno"/></td>


<html:hidden property="hostname" />
<html:hidden property="IPNumber" />


<td><b>IP Address:</b></td><td ><bean:write name="issuesForm" property="IPNumber"/></td></tr>

 <logic:notEmpty name="Recordsfound">
 
 
<tr><th>Raised By</th><td ><bean:write name="issuesForm" property="onbename"/></td>

<td><bean:write name="issuesForm" property="onbedepartment"/></td><td><bean:write name="issuesForm" property="onbedesignation"/></td>
 </tr>
</logic:notEmpty>

<tr><th colspan="4"><big>Classification</big></th></tr>
<tr>
<td>Request No</td>
<td><small>Auto Generated</small></td>
<td >Status Of Message&nbsp;<font color="red" >*</font></td><td  >
<bean:write name="issuesForm" property="requestStatus"/>
</td>
</tr>
<tr>
<td >Priority&nbsp;<font color="red" >*</font></td><td  >
<html:select property="issuePriority" styleClass="content"  >
	<html:option value="">----Select ----</html:option>
	<html:option value="Very High">Very High</html:option>
	<html:option value="High">High</html:option>
    <html:option value="Medium">Medium</html:option>
   
	</html:select>
</td><td >Mode</td><td >
<html:select property="mode" styleClass="content"  >
	<html:option value="Portal">Portal</html:option>
    <html:option value="Phone">Phone</html:option>
    <html:option value="Mail">Mail</html:option>
	</html:select>
</td>


</tr>
<tr>
<td><b>Category</b> <font color="red">*</font></td>
						<td>
						<html:select property="category" styleClass="content"  onchange="getSubcat(this.value)">
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
							<html:option value="JAVA Team">JAVA Team</html:option>
						    </html:select>
						</td>
<td><b>Sub-Category</b> <font color="red">*</font></td>
						<td>
						<div id="subcategoryID" align="left">
						<select name="subCategory" id="subCategoryID" >
<option value="">--Select---</option>
							
									</select>
	   </div>
						</td>
						</tr>
						<tr><td>Technician <font color="red">*</font></td>
							<td colspan="3">
			<div id="techniciansID" align="left">
			<select name="techinician" id="techinicianID" >
		<option value="">--Select---</option>
	</select>
			</div>
		 
</td>
</tr>
<tr>
<th  colspan="4"><big>Problem Details</big></th>
</tr>
<tr>
<td><b>Subject:&nbsp;<font color="red" >*</font></b></td><td colspan="3"><html:text  name="issuesForm" property="subject" style="width: 430px; " maxlength="50"></html:text></td></tr>

<tr>
	<td><b>Description:&nbsp;</b></td>
<td colspan="3"><html:textarea property="reason" name="issuesForm" cols="80" rows="5" style="height: 88px; width: 657px; ">

</html:textarea>	
	</td>
</tr>
<tr>
<th  colspan="4"><big>Attachments</big></th>
</tr>
<tr>
            <th  colspan="4" align="center">Document Path : 
               	<html:file property="documentFile" />
                <html:button property="method" styleClass="rounded"  value="Upload" onclick="uploadDocument();" style="align:right;width:100px;"/>
              
            </th>
          </tr>		
          <logic:notEmpty name="documentDetails">
          <tr>
						<th colspan="6">Uploaded Documents </th>
						</tr>
						<logic:iterate id="abc" name="documentDetails">
						<tr>
							<td>
								<html:checkbox property="documentCheck" name="abc"
									value="${abc.id}" styleId="${abc.id}" onclick="addInput(this.value)" style="width :10px;"/>
							</td>
							<td colspan="3">
								<a href="/EMicro Files/IT/Help Desk/Issues/UploadFiles/${abc.fileName}"  target="_blank">  <bean:write name="abc" property="fileName"/></a>
							</td>
						</tr>
						</logic:iterate>
						<tr>
							<td align="center" colspan="6">
							<html:button property="method" value="Delete" onclick="deleteDocumentsSelected()" styleClass="rounded"/>
							</td>
							</tr>
						</logic:notEmpty>
						
						<tr>
<td colspan="4" style="text-align: center;">
	<html:button property="method"  value="Send" onclick="submitData()" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
	<html:button property="method"  value="Back" onclick="back()" styleClass="rounded" style="width: 100px"></html:button>
</td>
</tr>
</table>
</div>
<html:hidden property="employeeno"/>
<html:hidden  property="requestNumber"/>

<logic:notEmpty name="setSubCategAndTechns">
<script type="">
getSubcat('<bean:write name="issuesForm" property="category"/>');
getTechnicians('<bean:write name="issuesForm" property="selectedSubCategory"/>');

setSubCatTech('<bean:write name="issuesForm" property="selectedSubCategory"/>','<bean:write name="issuesForm" property="selectedTechnician"/>');
</script>

</logic:notEmpty>
</div>
</html:form>

  </body>
</html>
