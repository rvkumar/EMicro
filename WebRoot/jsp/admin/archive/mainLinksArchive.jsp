<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>



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
<title>Main Links Archive</title>

<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>
<style type="text/css">
.sudmit_btn123 {
color: #fef4e9;
border: solid 1px #0680ce;
background: #38abff;
background: -webkit-gradient(linear, left top, left bottom, from(#26a0f9), to(#38d3ff));
background: -moz-linear-gradient(top, #26a0f9, #38d3ff);
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#26a0f9', endColorstr='#38d3ff');
display: inline-block;
outline: none;
cursor: pointer;
text-align: center;
text-decoration: none;
font: 12px/100% Arial, Helvetica, sans-serif;
font-weight: bold;
padding: .3em .3em .33em;
text-shadow: 0 1px 1px rgba(0,0,0,.3);
-webkit-border-radius: .5em;
-moz-border-radius: .5em;
border-radius: .5em;
-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);
-moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);
box-shadow: 0 1px 2px rgba(0,0,0,.2);
height: 25px;
}

</style>


<script type="text/javascript">

	function onSubmit(){
	if(document.forms[0].linkName.value=="")
	    {
	      alert("Link Name should not be left blank");
	      document.forms[0].linkName.focus();
	      return false;
	    }
	   
	  
		var url="linksArchive.do?method=updateLinksContent";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function deleteMainLinkFiles(){
		var url="linksArchive.do?method=deleteUploadedMainFile";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	function deleteMainLinkUploadedVideos(){
		var url="linksArchive.do?method=deleteMainLinkUploadedVideo";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	function displaySublinks(){
		var url="linksArchive.do?method=displaySublinks";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function onModify(){

		var url="linksArchive.do?method=select";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	function moveArchiveData(){
		
	if(document.forms[0].linkName.value=="")
	    {
	      alert("Please Select Link ");
	      document.forms[0].linkName.focus();
	      return false;
	    }

	    if(document.forms[0].year.value=="")
	    {
	      alert("Please Select Year ");
	      document.forms[0].year.focus();
	      return false;
	    }
	
		var url="linksArchive.do?method=saveArchiveData";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	function onDelete(){
		var url="linksArchive.do?method=delete";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	function onUpload(){	   
		
			var url="linksArchive.do?method=uploadFile";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}
	
function onUploadVideo(){
	
	var url="linksArchive.do?method=uploadVideo";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	
	
	
	
	
	
	
function onDeleteFile()
{
var fileChecked=0;
var fileLength=document.forms[0].select.length;
var fileLength1=document.forms[0].select.checked;
if(fileLength1==true && fileLength==undefined)
{
	var agree = confirm('Are You Sure To Delete Selected Requests');
	if(agree)
	{
		var url="linksArchive.do?method=deleteFileList";
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
		if(document.forms[0].select[i].checked==true)
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
	var agree = confirm('Are You Sure To Delete Selected Requests');
	if(agree)
	{
	var url="linksArchive.do?method=deleteFileList";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else
	{
	return false;
	}
}	
}
function onDeleteVideo()
{
var videoChecked=0;
var videoLength=document.forms[0].select1.length;
var videoLength1=document.forms[0].select1.checked;

if(videoLength1==true && videoLength==undefined)
{
	var agree = confirm('Are You Sure To Delete Selected Requests');
	if(agree)
	{
		var url="linksArchive.do?method=deleteVideoList";
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
	for(i=0;i<videoLength;i++)
	{
	if(document.forms[0].select1[i].checked==true)
	{
	videoChecked=videoChecked+1;
	}
	}
	if(videoChecked==0)
	{
	alert('Select Atleast One Record To Delete');
	return false;
	}
	else
	{
	}
	var agree = confirm('Are You Sure To Delete Selected Requests');
	if(agree)
	{
	var url="linksArchive.do?method=deleteVideoList";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else
	{
	return false;
	}
}	
}
function displayContentlinks()
	{
	if(document.forms[0].linkName.value=="")
	    {
	      alert("Please Select LinkName ");
	      document.forms[0].linkName.focus();
	      document.forms[0].year.value="";
	      return false;
	    }
	var url="linksArchive.do?method=displayLinksContent";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	
function modifyMenu()
{
	var url="linksArchive.do?method=displayMainLinks";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function modifyLink()
{
	var url="subLinkArchive.do?method=display";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function modifySubLink()
{
	var url="subsubLinkArchive.do?method=display";
	document.forms[0].action=url;
	document.forms[0].submit();
}	
	
</script>



</head>

<body >
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

  <tr>
   <%
			
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			
			if(menuIcon==null){
			menuIcon="";
			}
			
			%>
			
			 <% 
  				UserInfo user=(UserInfo)session.getAttribute("user");
  
  			%>
  
  
     			<div align="center">
						<logic:present name="linksArchiveForm" property="statusMessage">
						<font color="red">
							<bean:write name="linksArchiveForm" property="statusMessage" />
						</font>
					</logic:present>
					</div>
<html:form action="/linksArchive.do" enctype="multipart/form-data">
		
<table>
<tr>
<td><html:button property="method" value="Modify Main Links Archive" onclick="modifyMenu()" styleClass="sudmit_btn123"></html:button></td>
<td><html:button property="method" value="Modify  Links Archive" onclick="modifyLink()"></html:button></td>
<td><html:button property="method" value="Modify Sub Link  Archive" onclick="modifySubLink()"></html:button></td>

</tr>
</table>		
 <input type="hidden" name="MenuIcon2" value="<%=request.getAttribute("MenuIcon") %>"/> 
		<FCK:editor instanceName="EditorDefault" width="900">
							
			<jsp:attribute name="value">
			  <logic:present name="linksArchiveForm" property="contentDescriptionAdmin">
                          <bean:define id="content" name="linksArchiveForm" property="contentDescriptionAdmin" />
			<%out.println(content.toString());%>
                       </logic:present>
			<logic:notPresent name="linksArchiveForm" property="contentDescriptionAdmin">
			<bean:define id="abc" value="null" />
			</logic:notPresent>
			
			
				
            </jsp:attribute>
			</FCK:editor>
<table align="center" border="1" id='mytable1' >
<tr>
<th align="center" valign="middle" class="title" colspan="2">
<center>Modify Main Links Archive</center> </th>
</tr>
<tr>
							<th class="specalt" scope="row">
									<span class="text">Links<img src="images/star.gif" width="8" height="8" /></span>
								</th>
								<td >
									<html:select property="linkName"  styleClass="text_field">
										<html:option value="">--SELECT--</html:option>
										<html:options property="linkValueList"
											labelProperty="linkValueList" name="linksArchiveForm" />
									</html:select>
								</td>
							</tr>
							<html:hidden property="subLinkId" />
							
							<html:hidden property="linkId" />
							
							<html:hidden property="underSubLinks" />
							
							
							<tr>
						<th class="specalt" scope="row">
							<span class="text">Year<img src="images/star.gif" width="8" height="8" /></span>
						</th>
							<td align="left">
							<html:select property="year" onchange="displayContentlinks()" styleClass="text_field">
						
							<html:option value="">--SELECT--</html:option>
							<html:option value="2010"/>
							<html:option value="2011"/>
							<html:option value="2012"/>
							<html:option value="2013"/>
							<html:option value="2014"/>
							<html:option value="2015"/>
							<html:option value="2016"/>
							<html:option value="2017"/>
							<html:option value="2018"/>
							<html:option value="2019"/>
							
							</html:select>
							</td>
							</tr>
						
								<logic:notEmpty name="displaySublinkField">
							<tr>
							<td>
							<span class="text">Archives</span>
							</td>
							<td align="left">
							<html:checkbox property="archiveStatus" value="yes"></html:checkbox>
							</td>
							</tr>
							
										
						
							<tr>
								<td align="center">
									<span class="text">Specify Text File :</span>
									<html:file name="linksArchiveForm" property="fileNames"/>
								</td>
								</tr>
								<tr>

								<td colspan="2" align="center">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:button value="Upload Documents" onclick="onUpload()" property="method" styleClass="sudmit_btn" />
								</td>
							</tr>


							<logic:notEmpty name="listName">
								<tr>
									<th align="center" valign="middle" class="title" colspan="2">
									Uploaded Documents
									</th>
								</tr>

								<logic:iterate name="listName" id="listid">

                                 <tr>
									<td><bean:write name="listid" property="fileList" />
									
									<td width="15"><input type="checkbox" name="checkedfiles"
												value="<bean:write name="listid" property="fileList"/>" /></td>

									</tr>

								</logic:iterate>
								</logic:notEmpty>
							<tr>
								<td colspan="2" align="center">
									<html:button value="Delete Files" onclick="deleteMainLinkFiles()" property="method" styleClass="sudmit_btn" />
								</td>
								</tr>

							<tr>
								<td>
									<span class="text">Specify Video :</span>
									<html:file name="linksArchiveForm" property="videoFileNames" />
								</td>
</tr>
<tr>
								<td colspan="2" align="center">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:button value="Upload Videos" onclick="onUploadVideo()" property="method" styleClass="sudmit_btn"/>

								</td>
							</tr>

								<logic:notEmpty name="videosList">
								<tr>
									<th align="center" valign="middle" class="title" colspan="2">
										Uploaded Video Files
									</th>
									
								</tr>
								
								<logic:iterate name="videosList" id="listid1">

										
									<tr>
									<td><bean:write name="listid1" property="fileList"/>
									
									<td width="15"><input type="checkbox" name="checkedVideofiles"
												value="<bean:write name="listid1" property="fileList"/>" /></td>

									</tr>
								

								</logic:iterate>
								</logic:notEmpty>
								<tr>
									<td colspan="2" align="center">
									<html:button value="Delete Videos" onclick="deleteMainLinkUploadedVideos()" property="method" styleClass="sudmit_btn"/>
								</td>
								</logic:notEmpty>
								</tr>
								<tr>
									<td  colspan="2" align="center"><!--
										<html:button property="method" value="Submit" onclick="onSubmit()" />
										--><html:button property="method" value="Modify" onclick="moveArchiveData()"  styleClass="sudmit_btn"/>
									</td>
								</tr>
						</table>


</html:form>
</div>
</td>
      </tr>
      </table>
</body>
</html>
