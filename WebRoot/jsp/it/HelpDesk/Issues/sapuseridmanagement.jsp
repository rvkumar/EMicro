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
var type=subCategory;
if(type=="Rename ID")
 {

 document.getElementById("displayit").style.display="block";
 }
 else
 {
 document.getElementById("displayit").style.display="none";
 }
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
	xmlhttp.open("POST","itIsssues.do?method=getTechnicians_parllel&category="+category+"&subCategory="+subCategory+"&locNo="+location,true);
	xmlhttp.send();
} 
 
 
 
 
 
function getDisplayApprovers(technicians){
	var category=document.forms[0].category.value;
	var location=document.forms[0].locNo.value;
	var subCategory=document.forms[0].subCategory.value;
	var technician=document.forms[0].techinician.value;
    var empno=document.forms[0].employeeno.value;
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
	xmlhttp.open("POST","itIsssues.do?method=displayApprovers&category="+category+"&subCategory="+subCategory+"&locNo="+location+"&technician="+technician+"&empno="+empno,true);
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
function sapUploadDocument(req)
{
var reqname=req;
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
	document.forms[0].action="itIsssues.do?method=sapUploadDocuments&subCategory="+subCategory+"&technician="+technician+"&reqname="+reqname;
	document.forms[0].submit();
}
function sapDeleteDocumentsSelected(req)
{
var reqname=req;
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
	document.forms[0].action="itIsssues.do?method=sapDeleteDocuments&cValues="+checkvalues+"&subCategory="+subCategory+"&technician="+technician+"&reqname="+reqname;
document.forms[0].submit();
}
}
}
function back(){
	document.forms[0].action="itIsssues.do?method=sapRequestform";
	document.forms[0].submit();
}

function setSubCatTech(subCategory,Technician,message){
	 alert(message);
	 document.forms[0].subCategory.value=subCategory;
	 document.forms[0].techinician.value=Technician;
	  
}
function submitData(req){
var reqname=req;
     /* if(document.forms[0].reqEmail.value=="")
	    {
	      alert("Please Enter Email ID ");
	      document.forms[0].reqEmail.focus();
	      return false;
	    } */
	    
	  /*   var str=document.forms[0].reqEmail.value;
	    if(document.forms[0].reqEmail.value!="")
	    {
	    var afterACT = str.substr(str.indexOf("@") + 1);
	    if(afterACT!="microlabs.in")
	    {
	     alert("Please Enter Valid Email ID");
	      document.forms[0].reqEmail.focus();
	      return false;
	    
	    }
	   
	    } */
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

if(document.forms[0].subCategory.value=="Rename ID")
{
if(document.forms[0].oldid.value=="")
{
alert("Please enter Existing Id");
document.forms[0].oldid.focus();
 return false;
}
if(document.forms[0].newid.value=="")
{
alert("Please enter Proposed Id");
document.forms[0].newid.focus();
 return false;
}

}

if(document.forms[0].techinician.value=="")
{
alert("Please Select Techician");
document.forms[0].techinician.focus();
 return false;
}

if(document.forms[0].requestName.value!="Document Cancellation")
{
if(document.forms[0].roleId.value=="")
{
alert("Please Select Role");
document.forms[0].roleId.focus();
 return false;
}
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
// 	 var location=document.forms[0].locNo.value; 
	 var category=document.forms[0].category.value;
	
	document.forms[0].action="itIsssues.do?method=sapuseridsubmitreq&subCategory="+subCategory+"&technician="+technician+"&category="+category+"&reqname="+reqname;
	document.forms[0].submit();
}

 </script>

  </head>
  
  <body>
   <html:form action="/itIsssues.do" enctype="multipart/form-data">
   
   <div align="center">
		<logic:notEmpty name="issuesForm" property="message">
		<font color="green" size="3">
			<b><bean:write name="issuesForm" property="message" /></b>
		</font>
	</logic:notEmpty>
	</div>
	<div align="center">
		<logic:present name="issuesForm" property="message2">
		<font color="red" size="3">
			<b><bean:write name="issuesForm" property="message2" /></b>
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
<html:hidden property="requestName" />



<tr><th colspan="4"><center><big><bean:write name="issuesForm" property="requestName"/></big></center></th></tr>
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
<tr>
<th colspan="2"><b>EMAIL ID (For which the confirmation will be Sent)</b> </th>
<logic:notEmpty property="reqEmail" name="issuesForm"> 
<td colspan="2"><bean:write property="reqEmail" name="issuesForm" /> </td>
</logic:notEmpty>
<logic:empty property="reqEmail" name="issuesForm"> 
<td colspan="2"><font color="red" size="3">Email id is Not Available for this User</font></td>					
</logic:empty>
</tr>


<tr><th colspan="4"><big>Classification</big></th></tr>
<tr>
<td>Request No</td>
<td><font color="red" size="1">System generated no.</font></td>
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
							<%-- <html:option value="PSAP">Primary SAP User Id</html:option>
							<html:option value="SSAP">Secondary SAP User Id</html:option>categortShortlist --%>
							 <html:options name="issuesForm"  property="categorylist" labelProperty="categorylist"/> 
							</html:select>
						</td>
<td><b>Sub-Category</b> <font color="red">*</font></td>
						<td>
						<div id="subcategoryID" align="left" >
						
						<select name="subCategory" id="subCategoryID"  >
						<option value="">--Select---</option>
							
									</select>
	   </div>
						</td>
						</tr>
					</table>
					<div style="display: none" id="displayit" style="width: 100%">
					<table class="bordered">
						<tr >
							
<td style="width: 150px">Existing ID  <font color="red">*</font></td>
<td><html:text property="oldid" styleId="oldid" styleClass="content" style="width: 175px"></html:text></td>
<td >Proposed ID<font color="red" >*</font></td><td >
<html:text property="newid" styleId="newid" styleClass="content" ></html:text>
</td>
</tr></table>
</div>


<table class="bordered">
						<tr><td><b>Technician<font color="red">*</font></b></td>
							<td>
			<div id="techniciansID" align="left">
			<select name="techinician" id="techinicianID" >
		<option value="">--Select---</option>
	</select>
			</div>
		
		 
</td>
<logic:empty name="userid">
<td></td>
</logic:empty>

<logic:notEmpty name="userid">
<td><b>Job Profile/ Role <font color="red">*</font></b></td>
							<td>
			<div id="roleID">
			<html:select property="roleId" styleClass="content" >
							<html:option value="">-----Select---------</html:option>
							
							 <html:options name="issuesForm"  property="rolelist" labelProperty="roleLabelList"/> 
							</html:select>
	
			</div>
		 
</td>
</logic:notEmpty>
</tr>
	<tr>
			<th colspan="4"><big>User ID DETAILS</big></th></tr>
<tr>
<td><b>Subject:&nbsp;<font color="red" >*</font></b></td><td colspan="3"><html:text  name="issuesForm" property="subject" style="width: 430px; " maxlength="50"></html:text></td></tr>

<tr>
	<td><b>Detailed Information with justification:&nbsp;</b></td>
<td colspan="3"><html:textarea property="reason" name="issuesForm" cols="80" rows="2" style="height: 40px; width: 657px; ">

</html:textarea>	
	</td>
</tr>
<tr>
<th colspan="4"><small>Attachments: (User can upload doc,docx,pdf,jpg,xlxs,xls,txt type files & file size should be less then 10 mb )</small></th>
</tr>


<tr>
            <th  colspan="4" align="center">Document Path : 
               	<html:file property="documentFile" />
                <html:button property="method" styleClass="rounded"  value="Upload" onclick="sapUploadDocument('${issuesForm.requestName}');" style="align:right;width:100px;"/>
              
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
								<%-- <a href="/EMicro Files/IT/Help Desk/Issues/UploadFiles/${abc.fileName}"  target="_blank">  <bean:write name="abc" property="fileName"/></a> --%>
							<a href="${abc.fileFullPath}"  target="_blank">  <bean:write name="abc" property="fileName"/></a>
							</td>
						</tr>
						</logic:iterate>
						<tr>
							<td align="center" colspan="6">
							<html:button property="method" value="Delete" onclick="sapDeleteDocumentsSelected('${issuesForm.requestName}')" styleClass="rounded"/>
							</td>
							</tr>
						</logic:notEmpty>
						
						<tr>
<td colspan="4" style="text-align: center;">
	<html:button property="method"  value="Submit" onclick="submitData('${issuesForm.requestName}')" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
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

setSubCatTech('<bean:write name="issuesForm" property="selectedSubCategory"/>','<bean:write name="issuesForm" property="selectedTechnician"/>','<bean:write name="issuesForm" property="message"/>');
getDisplayApprovers('<bean:write name="issuesForm" property="selectedTechnician"/>');
</script>

</logic:notEmpty>

<div id="Aprroverid" align="left">
</div>

</html:form>

  </body>

</html>