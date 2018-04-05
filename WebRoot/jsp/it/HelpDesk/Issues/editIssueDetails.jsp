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
  <style>
  <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
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
function modifyDocument()
{
	if(document.forms[0].documentFile.value==""){
		alert("Please Choose Atleast One File");
		document.forms[0].documentFile.focus();
		return false;
	}
	document.forms[0].action="itIsssues.do?method=uploadModifyDocument";
	document.forms[0].submit();
}
function deleteModifyDocuments()
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
document.forms[0].action="itIsssues.do?method=deleteModifyDocuments&cValues="+checkvalues;
document.forms[0].submit();
}
}
}
function closeData(){
	document.forms[0].action="itIsssues.do?method=displayAllIssues";
	document.forms[0].submit();
}

function setSubCatTech(subCategory,Technician){
	alert("Documents has been updated");
	
	 document.forms[0].subCategory.value=subCategory;
	 document.forms[0].techinician.value=Technician;
}
function modifyIssue(){
	document.forms[0].action="itIsssues.do?method=modifyIssue";
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


<tr><th colspan="4"><center><big>New Incident </big></center></th></tr>
<tr><th >Status Of Message&nbsp;<font color="red" >*</font></th><td  >
<bean:write name="issuesForm" property="requestStatus"/>
</td>
<th >Priority&nbsp;<font color="red" >*</font></th><td  >
<html:select property="issuePriority" styleClass="content" styleId="filterId" >
	<html:option value="">--Select --</html:option>
	<html:option value="High">High</html:option>
    <html:option value="Low">Low</html:option>
    <html:option value="Medium">Medium</html:option>
	</html:select>
</td></tr>
<tr><th >Mode&nbsp;<font color="red" >*</font></th><td >
<html:select property="mode" styleClass="content" styleId="filterId" >
	<html:option value="Portal">Portal</html:option>
    <html:option value="Phone">Phone</html:option>
    <html:option value="Mail">Mail</html:option>
	</html:select>
</td>
<th>Request No</th>
<td><bean:write name="issuesForm" property="requestNo"/></td>

</tr>
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

<tr><th colspan="4"><big>Other Details</big></th></tr>
<tr>
<td><b>Category</b> <font color="red">*</font></td>
<td><bean:write name="issuesForm" property="category"/></td>
<td><b>Sub-Category</b> <font color="red">*</font></td>
<td><bean:write name="issuesForm" property="selectedSubCategory"/></td>
</tr>
<tr><td>Technician</td>
<td colspan="3"><bean:write name="issuesForm" property="technicianName"/></td>
<tr>
<td><b>Subject:&nbsp;<font color="red" >*</font></b></td>
<td colspan="3"><html:text  name="issuesForm" property="subject" style="width: 430px; "></html:text></td></tr>

<tr>
	<td><b>Reason:&nbsp;<font color="red" >*</font></b></td>
<td colspan="3"><html:textarea property="reason" name="issuesForm" cols="80" rows="5" style="height: 88px; width: 657px; ">

</html:textarea>	
	</td>
</tr>
<tr>
            <th  colspan="4" align="center">Document Path : 
               	<html:file property="documentFile" />
                <html:button property="method" styleClass="rounded"  value="Upload" onclick="modifyDocument();" style="align:right;width:100px;"/>
              
            </th>
          </tr>		
          <logic:notEmpty name="documentDetails">
          <tr>
						<th colspan="6"><center>Uploaded Documents </center></th>
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
							<html:button property="method" value="Delete" onclick="deleteModifyDocuments()" styleClass="rounded"/>
							</td>
							</tr>
						</logic:notEmpty>
						</tr>
<tr>
<td colspan="4">
<center><html:button property="method"  value="Modify" onclick="modifyIssue()" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
	<html:button property="method"  value="Close" onclick="closeEdit()" styleClass="rounded" style="width: 100px"></html:button>
	</center>
</td>
</tr>
</table>
</div>
<html:hidden property="employeeno"/>
<html:hidden  property="requestNumber"/>
</html:form>
  </body>
</html>
