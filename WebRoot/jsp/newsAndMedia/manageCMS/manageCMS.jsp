<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%--<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>--%>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: CMS_Create_Modify </title>

<%--<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />--%>

<!--
/////////////////////////////////////////////////
-->

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
    <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<script type="text/javascript">

function displayContentlinks()
	{
	var linkname=document.forms[0].linkName.value;
	var sublink=document.forms[0].subLinkName.value;
    var year=document.forms[0].year.value;
    if(year ==""){
    	alert("Please Select Year");
    	document.forms[0].year.focus();
    	document.forms[0].month.value="";
    	return false;
    }
	var subsublink=document.forms[0].subSubLinkName.value;
	if(linkname=="Login CMS"||linkname=="Message From Managment"||linkname=="Help")
	{
	var url="manageCMS.do?method=displayLinksContent1&LinkName="+linkname;
	document.forms[0].action=url;
	document.forms[0].submit();
	}else{
	if(sublink=="")
	{
	var url="manageCMS.do?method=displayLinksContent1&LinkName="+linkname;
	document.forms[0].action=url;
	document.forms[0].submit();
	}else if(subsublink=="")
	{
	var url="manageCMS.do?method=displaySubLinksCMS&SubLink="+sublink+"&LinkName="+linkname;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	else{
	var url="manageCMS.do?method=displaySubSubLinksCMS&LinkName="+linkname+"&SubLink="+sublink+"&SubSubLink="+subsublink;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	}
	}

function deleteImage(){
	
	var fileChecked=0;
var fileLength=document.forms[0].checkedImages.length;
var fileLength1=document.forms[0].checkedImages.checked;
if(fileLength1==true && fileLength==undefined)
{
var agree = confirm('Are You Sure To Delete Selected Image');
if(agree)
{
var url="manageCMS.do?method=deleteImage";
	document.forms[0].action=url;
	document.forms[0].submit();
}
else
{
return false;
}
}
else
{
for(i=0;i<fileLength;i++)
{
if(document.forms[0].checkedImages[i].checked==true)
{
fileChecked=fileChecked+1;
}
}
if(fileChecked==0)
{
alert('Select Atleast One Record To Delete');
return false;
}
else
{
}
var agree = confirm('Are You Sure To Delete Selected Image');
if(agree)
{
var url="manageCMS.do?method=deleteImage";
		document.forms[0].action=url;
		document.forms[0].submit();
}
else
{
return false;
}
}	
}

function moveArchiveData(){
	if(document.forms[0].linkName.value=="")
    {
      alert("Menu Name should not be left blank");
      document.forms[0].linkName.focus();
      return false;
    }
	    if(document.forms[0].year.value=="")
	    {
	      alert("Year should not be left blank");
	      document.forms[0].year.focus();
	      return false;
	    }
	    if(document.forms[0].month.value=="")
	    {
	      alert("Month should not be left blank");
	      document.forms[0].month.focus();
	      return false;
	    }
	   
       
		var url="manageCMS.do?method=saveArchiveData";
		document.forms[0].action=url;
		document.forms[0].submit();
	}

function deleteMainLinkUploadedVideos(){
var rows=document.getElementsByName("checkedVideofiles");
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

	document.forms[0].action="manageCMS.do?method=deleteMainLinkUploadedVideo&checkvalues"+checkvalues;
	document.forms[0].submit();
}
}
}


function deleteMainLinkFiles(){
	
	var fileChecked=0;
var fileLength=document.forms[0].checkedfiles.length;
var fileLength1=document.forms[0].checkedfiles.checked;
if(fileLength1==true && fileLength==undefined)
{
var agree = confirm('Are You Sure To Delete Selected File');
if(agree)
{
var url="manageCMS.do?method=deleteUploadedMainFile";
	document.forms[0].action=url;
	document.forms[0].submit();
}
else
{
return false;
}
}
else
{
for(i=0;i<fileLength;i++)
{
if(document.forms[0].checkedfiles[i].checked==true)
{
fileChecked=fileChecked+1;
}
}
if(fileChecked==0)
{
alert('Select Atleast One File To Delete');
return false;
}
else
{
}
var agree = confirm('Are You Sure To Delete Selected File');
if(agree)
{
var url="manageCMS.do?method=deleteUploadedMainFile";
		document.forms[0].action=url;
		document.forms[0].submit();
}
else
{
return false;
}
}	
}

function onUploadImage(){	   

if(document.forms[0].imageNames.value=="")
{
  alert("Please Choose Image File");
  document.forms[0].imageNames.focus();
  return false;
}
			var url="manageCMS.do?method=uploadImage";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}
	function onUpload(){	   
		if(document.forms[0].fileNames.value=="")
		{
		  alert("Please Choose Document File");
		  document.forms[0].fileNames.focus();
		  return false;
		}
			var url="manageCMS.do?method=uploadFile";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}
	
function onUploadVideo(){
	if(document.forms[0].videoFileNames.value=="")
		{
		  alert("Please Choose Video File");
		  document.forms[0].videoFileNames.focus();
		  return false;
		}
	var url="manageCMS.do?method=uploadVideo";
	document.forms[0].action=url;
	document.forms[0].submit();
	}

function getSubLink(linkname)
{
var xmlhttp;
var dt;
dt=linkname;
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
    document.getElementById("sublinkID").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","manageCMS.do?method=displaySublinks1&linkName="+dt,true);
xmlhttp.send();
}

function getSubSublinks(subLinkname)
{


  var subLinkname=subLinkname;
    var linkname=document.forms[0].linkName.value;
   
var xmlhttp;
var dt;

dt=linkname+","+subLinkname;
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
    document.getElementById("subsublinkID").innerHTML=xmlhttp.responseText;
    }
  }


  

xmlhttp.open("POST","manageCMS.do?method=displaySubSublinks1&Linkname="+linkname+"&SubLinkname="+subLinkname,true);
xmlhttp.send();
}

function onBack()
{
var url="manageCMS.do?method=displayMenuList";
	document.forms[0].action=url;
	document.forms[0].submit();


}
function changeMonth(){
	document.forms[0].month.value="";
}

</script>
</head>
<body>
	<html:form action="manageCMS" enctype="multipart/form-data">
	<div align="center">
		<logic:notEmpty name="manageCMSForm" property="message">
			<font color="red"><bean:write name="manageCMSForm" property="message" /></font>
		</logic:notEmpty>
		<logic:notEmpty name="manageCMSForm" property="message2">
			<font color="Green"><bean:write name="manageCMSForm" property="message2" /></font>
		</logic:notEmpty>
	</div>

		<table class="bordered">
			<tr>
				<th colspan="6" style="text-align: center;"><big>Manage Business Section</big></th>
			</tr>						

			<tr>
				<td >Menu Name</td>
				<td colspan="3">
					<html:select property="linkName"  styleClass="content" onchange="getSubLink(this.value)">
						<html:option value="">--SELECT--</html:option>
						<html:options property="linkValueList" labelProperty="linkValueList" name="manageCMSForm" styleClass="content" />
					</html:select>
				</td>

				<html:hidden property="subLinkId" />
				<html:hidden property="linkId" />
				<html:hidden property="underSubLinks" />

				<td >Link</td>
				<td colspan="3"><div id="sublinkID" align="left">
					<html:select property="subLinkName" onchange="getSubSublinks(this.value)" styleClass="content">
						<html:option value="">--Select--</html:option>
						<html:options property="sublinkValueList" labelProperty="sublinkValueList" name="manageCMSForm" styleClass="content" />
					</html:select>
					</div>
				</td>
			</tr>

			<tr>
				<td>Sub Link</td>
				<td><div id="subsublinkID" align="left">
					<html:select property="subSubLinkName" styleClass="content" >
						<html:option value="">--Select--</html:option>
						<html:options property="subsublinkValueList" labelProperty="subsublinkValueList" name="manageCMSForm" styleClass="content" />
					</html:select>
					</div>
				</td>
				<td>Year <font color="red">*</font></td>
				<td><html:select property="year"  styleClass="content" onchange="changeMonth()">
						<html:option value="">--Select--</html:option>
						<html:option value="2012"/>
						<html:option value="2013"/>
						<html:option value="2014"/>
						<html:option value="2015"/>
						<html:option value="2016"/>
						<html:option value="2017"/>
						<html:option value="2018"/>
						<html:option value="2019"/>
						<html:option value="2020"/>
					</html:select>
				</td>
				<td>Month <font color="red">*</font></td>
				<td><html:select property="month" onchange="displayContentlinks()" styleClass="content">
						<html:option value="">--Select--</html:option>
						<html:option value="1">Jan</html:option>
						<html:option value="2">Feb</html:option>
						<html:option value="3">Mar</html:option>
						<html:option value="4">Apr</html:option>
						<html:option value="5">May</html:option>
						<html:option value="6">Jun</html:option>
						<html:option value="7">July</html:option>
						<html:option value="8">Aug</html:option>
						<html:option value="9">Sep</html:option>
						<html:option value="10">Oct</html:option>
						<html:option value="11">Nov</html:option>
						<html:option value="12">Dec</html:option>
					</html:select>
				</td>
			</tr>
</table>
<div>&nbsp;</div>
			<logic:notEmpty name="displaySublinkField">
			<table class="bordered">
				<tr>
					<td> <center> <b>Archives </b></center></td>
					<td colspan="3"><html:checkbox property="archiveStatus" value="yes"></html:checkbox></td>
				</tr>
	
					<tr>
						<th colspan="4"><center> <b><big>Content Area</big></b></center></th>
					</tr>
					<tr>
						<td colspan="4">
							<FCK:editor instanceName="EditorDefault" height="400px">
								<jsp:attribute name="value">
					  				<logic:present name="manageCMSForm" property="contentDescriptionAdmin">
		                          		<bean:define id="content" name="manageCMSForm" property="contentDescriptionAdmin" />
										<%out.println(content.toString());%>
		                       		</logic:present>
		
									<logic:notPresent name="manageCMSForm" property="contentDescriptionAdmin">
										<bean:define id="abc" value="null" />
									</logic:notPresent>
					            </jsp:attribute>
							</FCK:editor>
						</td>
					</tr>
			</table>
			<div>&nbsp;</div>
			<table class="bordered">
				<tr>
					<th colspan="2"><big>Attachments</big></th>
				</tr>
				<tr>
					<td>Images</td>
					<td>
						<html:file name="manageCMSForm" property="imageNames" styleClass="rounded" style="width: 220px"/>&nbsp;&nbsp;
						<html:button value="Upload" onclick="onUploadImage()" property="method" styleClass="rounded" style="width: 100px" />
					</td>
				</tr>

				<tr>
				<logic:notEmpty name="imageLists">
					<tr>
						<th colspan="2">Uploaded Images</th>
					</tr>

					<logic:iterate name="imageLists" id="listid">
						<tr>
							<td><bean:write name="listid" property="imageList" /></td>
							<td><input type="checkbox" name="checkedImages" value="<bean:write name="listid" property="imageList"/>" />
							</td>
						</tr>

						</logic:iterate>
							<tr>
								<td colspan="2">
									<html:button value="Delete" onclick="deleteImage()" property="method" styleClass="rounded" style="width: 100px" />
								</td>
							</tr>
						</logic:notEmpty>
					</tr>
							
					<tr>
						<td>Documents</td>
						<td>
							<html:file name="manageCMSForm" property="fileNames" styleClass="rounded" style="width: 220px"/>&nbsp;&nbsp;
							<html:button value="Upload" onclick="onUpload()" property="method" styleClass="rounded" style="width: 100px" />
						</td>
					</tr>

						<logic:notEmpty name="listName">
							<tr>
								<th colspan="2">Uploaded Documents</th>
							</tr>

							<logic:iterate name="listName" id="listid">
                            	<tr>
									<td><bean:write name="listid" property="fileList" /></td>
									<td><input type="checkbox" name="checkedfiles" value="<bean:write name="listid" property="fileList"/>" />
									</td>
								</tr>
							</logic:iterate>

							<tr>
								<td colspan="2">
									<html:button value="Delete" onclick="deleteMainLinkFiles()" property="method" styleClass="rounded" style="width: 100px" />
								</td>
							</tr>
						</logic:notEmpty>
							
					<tr>
						<td>Videos</td>
						<td><html:file name="manageCMSForm" property="videoFileNames" styleClass="rounded" style="width: 220px"/>&nbsp;&nbsp;
							<html:button value="Upload" onclick="onUploadVideo()" property="method" styleClass="rounded" style="width: 100px"/>
						</td>
					</tr>

						<logic:notEmpty name="videosList">
							<tr>
								<th colspan="2">Uploaded Videos</th>
							</tr>
								
							<logic:iterate name="videosList" id="listid1">
								<tr>
									<td align="center"><bean:write name="listid1" property="videoFilesList"/></td>
									<td><input type="checkbox" name="checkedVideofiles" value="<bean:write name="listid1" property="videoFilesList"/>" />
									</td>
								</tr>
							</logic:iterate>

							<tr>
								<td colspan="2">
									<html:button value="Delete" onclick="deleteMainLinkUploadedVideos()" property="method" styleClass="rounded" style="width: 100px"/>
								</td>
							</tr>
						</logic:notEmpty>
					</table>
					<br/>	
				</logic:notEmpty>

			<tr>
				<td colspan="4">
					<html:button property="method" value="Save" onclick="moveArchiveData()" styleClass="rounded" style="width: 100px" />&nbsp;&nbsp;
					<html:reset  value="Close"  styleClass="rounded" style="width: 100px" onclick="onBack()"/>
				</td>
			</tr>
		</table>
<br/>
<br/>
	</html:form>
</body>
</html>