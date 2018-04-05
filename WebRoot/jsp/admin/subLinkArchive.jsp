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



<script type="text/javascript">
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function onSubmit(){
	
	    
		var url="subLinkArchive.do?method=saveArchiveData";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	
	function displayFields(){
	
		var url="subLinkArchive.do?method=displaySubLinksArchiveData";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	
	function displaySublinks(){
	
		var url="subLinkArchive.do?method=displaySublinks";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	
	
	function displaySublinksContent(){
	
		var url="subLinkArchive.do?method=displaySublinksContent";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function onModify(){
		var url="links.do?method=select";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function onDelete(){
		var url="links.do?method=delete";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function onUpload(){	   
		
			var url="subLinkArchive.do?method=uploadFile";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
	
function onUploadVideo(){
	
	var url="subLinkArchive.do?method=uploadVideo";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	
function deleteFiles(){
	
	var url="subLinkArchive.do?method=deleteUploadedMainFile";
	document.forms[0].action=url;
	document.forms[0].submit();
	}	
	function deleteVideos(){
	
	var url="subLinkArchive.do?method=deleteUploadedVideo";
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
var url="links.do?method=deleteFileList";
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
var url="links.do?method=deleteFileList";
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
var url="links.do?method=deleteVideoList";
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
var url="links.do?method=deleteVideoList";
	document.forms[0].action=url;
	document.forms[0].submit();
}
else
{
return false;
}
}	
}


function onAddLink()
{
var url="links.do?method=displayLinksPage";
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
						<logic:present name="subLinkArchiveForm" property="message">
						<font color="red">
							<bean:write name="subLinkArchiveForm" property="message" />
						</font>
					</logic:present>
					</div>
<html:form action="/subLinkArchive.do" enctype="multipart/form-data">
<input type="hidden" name="MenuIcon2" value="<%=request.getAttribute("MenuIcon") %>"/>
			<FCK:editor instanceName="EditorDefault" width="900">
			<jsp:attribute name="value">
			 <logic:present name="subLinkArchiveForm" property="contentDescriptionAdmin">
                      <bean:define id="content" name="subLinkArchiveForm"
				property="contentDescriptionAdmin" />
		<%out.println(content.toString());%>
                      </logic:present>
			
          </jsp:attribute>
		</FCK:editor>
		
<table align="center" border="1" id='mytable1'>
<tr>
<th align="center" valign="middle" class="title" colspan="2">Modify  Links Archive </th>
</tr>
<tr>
								<th class="specalt" scope="row">
									<span class="text">Links</span>
							</th>
								
								<td>
									<html:select property="linkName" onchange="displaySublinks()" styleClass="text_field">
										<html:option value="">--SELECT--</html:option>
										<html:options property="linkValueList"
											labelProperty="linkValueList" name="subLinkArchiveForm" />
									</html:select>
								</td>
							</tr>
							<html:hidden property="subLinkId" />
							
							<html:hidden property="linkId" />
						
									<html:hidden property="underSubLinks" />
							<logic:notEmpty name="displaySublinkField">
								</logic:notEmpty>
							
								<tr>
									<th class="specalt" scope="row">
									<span class="text">	Sub Link</span>
								</th>
									<td>
									
									<html:select property="subLinkName" styleClass="text_field">
										<html:option value="">--SELECT--</html:option>
										<html:options property="sublinkValueList"
											labelProperty="sublinkValueList" name="subLinkArchiveForm" />
									</html:select>
								</td>
									
								</tr>
								<tr>
							<th class="specalt" scope="row">
							<span class="text">Year</span>
							</th>
							<td>
							<html:select property="year" onchange="displayFields()" styleClass="text_field">
						
							<html:option value="--SELECT--"></html:option>
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
						
							<logic:notEmpty name="displayFields">
							<tr>
								<th class="specalt" scope="row">
							<span class="text" >Archives</span>
						</th>
							<td>
							<html:checkbox property="archiveStatus" value="yes" ></html:checkbox>
							</td>
							</tr>
							<tr >
								<td >
									<span class="text">Specify Text File :</span>
									<html:file name="subLinkArchiveForm" property="fileNames"/>
								</td>

								<td align="center">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:button value="Upload Documents" onclick="onUpload()" property="method" styleClass="sudmit_btn"/>
								</td>
							</tr>


							<logic:notEmpty name="listName">
								<tr>
									<th align="center" valign="middle" class="title" colspan="2">
										<b><font color="white">Uploaded Documents</font></b>
									</th>
								</tr>

								<logic:iterate name="listName" id="listid">
								
									<tr>

										<td align="center" colspan="1">
										<span class="text">	<bean:write name="listid" property="fileList"  /></span>
										</td>
										<td>
										
											<input type="checkbox" name="checkedfiles1"
												value="<bean:write name="listid" property="fileList" />" />

										</td>
									</tr>

								</logic:iterate>
								</logic:notEmpty>
							<tr>
								<td colspan="2" align="center">
									<html:button value="Delete Files" onclick="deleteFiles()" property="method" styleClass="sudmit_btn"/>
								</td>
								</tr>

							<tr>
								<td>
									<span class="text">Specify Video :</span>
									<html:file name="subLinkArchiveForm" property="videoFileNames" />
						</td>

								<td align="center">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:button value="Upload Videos" onclick="onUploadVideo()" property="method" styleClass="sudmit_btn"/>

								</td>
							</tr>

								<logic:notEmpty name="listName1">
								<tr>
									<th align="center" valign="middle" class="title" colspan="2">
										<b><font color="white">Uploaded Video Files</font></b>
								</th>
									
								</tr>

								<logic:iterate name="listName1" id="listid1">
									
									<tr>
										
										<td align="center" colspan="1">
										<span class="text">	<bean:write name="listid1" property="videoFilesList" /></span>
										</td>
										<td>

											<input type="checkbox" name="checkedVideofiles1"
												value="<bean:write name="listid1" property="videoFilesList" />" />

										</td>
									</tr>

								</logic:iterate>
								</logic:notEmpty>
								<tr>
									<td colspan="2" align="center">
									<html:button value="Delete Videos" onclick="deleteVideos()" property="method" styleClass="sudmit_btn"/>
								</td>
								</tr>
								
								
								
								
								
								
								<tr>
									<td  colspan="2" align="center">
										<html:button property="method" value="Modify" onclick="onSubmit()" styleClass="sudmit_btn"/>
									</td>
								</tr>
</logic:notEmpty>	
						</table>


</html:form>
</div>
</td>
      </tr>
      </table>
</body>
</html>
