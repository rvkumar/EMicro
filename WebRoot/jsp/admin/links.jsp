
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



<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link href="css/micro_style.css" type="text/css" rel="stylesheet" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<title>Microlab</title>
<script type="text/javascript">


function onSubmit(){
	if(document.forms[0].linkName.value=="")
	    {
	      alert("Link Name should not be left blank");
	      document.forms[0].linkName.focus();
	      return false;
	    }
	    else if(document.forms[0].subLinkName.value=="")
	    {
	      alert("SubLink Name should not be left blank");
	      document.forms[0].subLinkName.focus();
	      return false;
	    }
	    
	     else if(document.forms[0].priority.value=="")
	    {
	      alert("Priority should not be left blank");
	      document.forms[0].priority.focus();
	      return false;
	    }
	   
	    var x = document.forms[0].priority.value;
           if(isNaN(x)||x.indexOf(" ")!=-1)
           {
              alert("enter numeric value for Priority")
              return false; 
           }
		var url="links.do?method=submit";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function displaySublinks(){
		var url="links.do?method=displaySublinks";
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
		
			var url="links.do?method=uploadFiles";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
	
function onUploadVideo(){
	
	var url="links.do?method=uploadVideos";
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

//-->
</script>
</head>

<body>
		<!--------WRAPER STARTS -------------------->
<div id="wraper">
                
                <div style="padding-left: 10px;width: 70%;" class="content_middle">
                
                <!--------CONTENT MIDDLE STARTS -------------------->
                	
                    <div>
					
				<div align="center">
					<logic:present name="userGroupForm" property="message">
						<font color="red">
							<bean:write name="userGroupForm" property="message" />
						</font>
					</logic:present>
				</div>
				
				<div align="center">
					<logic:present name="userGroupForm" property="statusMessage">
						<font color="red">
							<bean:write name="userGroupForm" property="statusMessage" />
						</font>
					</logic:present>
				</div>
					
					<html:form action="/links.do" enctype="multipart/form-data">
					
			<FCK:editor instanceName="EditorDefault" width="900">
								<jsp:attribute name="value">
				           </jsp:attribute>
							</FCK:editor>
						
							
						<table class=forumline align=center id='mytable1' border="1"> 
						
						
							<tr>
								<th  class="specalt" scope="row">
									Links
								</th>
								<td>
									<html:select property="linkName"  onchange="displaySublinks()" style="background-color:#f6f6f6; width:150px; height:20px; border:#38abff 1px solid">
										<html:option value="">--SELECT--</html:option>
										<html:options property="linkValueList"
											labelProperty="linkValueList" name="linksForm" />
									</html:select>
								</td>
							</tr>
							<html:hidden property="subLinkId" />
							
							<html:hidden property="linkId" />
						
									<html:hidden property="underSubLinks" />
							<logic:notEmpty name="displaySublinkField">
								</logic:notEmpty>
							
								<tr>
								<th  class="specalt" scope="row">
										Sub Link
									</th>
									<td>
										<html:text property="subLinkName" style="background-color:#f6f6f6; width:200px; height:20px; border:#38abff 1px solid"/>
									</td>
								</tr>
								<tr>	<th  class="specalt" scope="row">Priority</th>
								<td><html:text property="priority" style="background-color:#f6f6f6; width:150px; height:20px; border:#38abff 1px solid"></html:text>
								</td></tr>
								
								
								
								<tr>	<th  class="specalt" scope="row">Add Sublinks</th>
								<td><html:button value="Add Links" onclick="onAddLink()" property="method" />
								</td></tr>
								
						
							<tr>
									<th  class="specalt" scope="row">
									Specify Text File :
									<html:file name="linksForm" property="fileNames" style="background-color:#f6f6f6; width:150px; height:20px; border:#38abff 1px solid"/>
							</th>

								<td align="center">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:button value="Upload Documents" onclick="onUpload()" property="method" />
								</td>
							</tr>


							<logic:notEmpty name="listName">
								<tr>
										<th  align="center"  colspan=2 bgcolor="#51B0F8">
										<b><font color="white">Uploaded Documents</font></b>
								</th>
								</tr>

								<logic:iterate name="listName" id="listid">
								
									<tr>

										<td>
											<bean:write name="listid" property="fileList" />
										</td>
										<td>
										
											<html:checkbox name="listid" property="select"
												value="${listid.fileList}" />

										</td>
									</tr>

								</logic:iterate>
								</logic:notEmpty>
							<tr>
								<td colspan="2" align="center">
									<html:button value="Delete Files" onclick="onDeleteFile()" property="method" />
								</td>
								</tr>

							<tr>
								<th  class="specalt" scope="row">
									Specify Video :
									<html:file name="linksForm" property="videoFileNames" />
								</th>

								<td align="center">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:button value="Upload Videos" onclick="onUploadVideo()" property="method" />

								</td>
							</tr>

								<logic:notEmpty name="listName1">
								<tr>
									<th  align="center"  colspan=2 bgcolor="#51B0F8">
										<b><font color="white">Uploaded Video Files</font></b>
									</th>
								</tr>

								<logic:iterate name="listName1" id="listid1">
									
									<tr>
										
										<td>
											<bean:write name="listid1" property="videoFilesList" />
										</td>
										<td>

											<html:checkbox name="listid1" property="select1"
												value="${listid1.videoFilesList}" />

										</td>
									</tr>

								</logic:iterate>
								</logic:notEmpty>
								<tr>
									<td colspan="2" align="center">
									<html:button value="Delete Videos" onclick="onDeleteVideo()" property="method" />
								</td>
								</tr>
								
								
								
								<tr>
								<th  class="specalt" scope="row">
									Specify Icon For Sub Link :
									<html:file name="linksForm" property="iconNames"/>
								</th>
							</tr>
								
								
								<tr>
									<td  colspan="2" align="center">
										<html:button property="method" value="Submit" onclick="onSubmit()" />
									</td>
								</tr>

						</table>



						<logic:notEmpty name="listDetails">

							<table class=forumline align=center id='mytable1' border="1"> 

								<tr>
								<th class="head" align=center colspan=5>
										Sub Link LIST</th>
								</tr>

								<tr height='20'>
											<th  class="specalt" scope="row">
										<b>Sl. No</b>
								</th>
										<th  class="specalt" scope="row">
										<b>Sublink</b>
								</th>
												<th  class="specalt" scope="row">
										<b>Method</b>
									</th>
												<th  class="specalt" scope="row">
										<b>Priority</b>
								</th>

								</tr>
								<%int count=1; %>
								<logic:iterate name="listDetails" id="abc">

									<tr>
										<td>
											<a
												href="links.do?method=selectContent&sId=<bean:write name="abc" property="subLinkId"/>&lId=<bean:write name="abc" property="linkName"/>"><%=count++ %>
										</td>
										<td>
											<bean:write name="abc" property="subLinkName" />
										</td>
										<td>
											<bean:write name="abc" property="methodName" />
										</td>
										<td>
											<bean:write name="abc" property="priority" />
										</td>
									</tr>

								</logic:iterate>

							</table>

						</logic:notEmpty>

							</html:form>
						</div>
                
            
            </div> <!--------CONTENT ENDS -------------------->
            
            
</div><!--------WRAPER ENDS -------------------->
                

</body>
</html>
