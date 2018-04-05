
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
<link href="css/micro_style.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<title>Microlab</title>
<script type="text/javascript">
<!--
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

	function onUpdate(){
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
		var url="links.do?method=updateContent";
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
		
			var url="links.do?method=uploadFilesModify";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}
function onUploadVideo(){
	
	var url="links.do?method=uploadVideosModify";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
function onDeleteFile(){
	
	var rows=document.getElementsByName("checkedfiles");

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
	document.forms[0].action="links.do?method=deleteFileListModify&cValues="+checkvalues+"&unValues="+uncheckvalues;
document.forms[0].submit();
}
}
}
function onDeleteVideo(){
	
	var rows=document.getElementsByName("checkedvideos");

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
var agree = confirm('Are You Sure To Delete Selected video');
if(agree)
{
document.forms[0].action="links.do?method=deleteVideoListModify&cValues="+checkvalues+"&unValues="+uncheckvalues;
document.forms[0].submit();
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

<body onload="MM_preloadImages('images/home_hover.jpg','images/news_hover.jpg','images/ess_hover.jpg','images/hr_hover.jpg','images/it_hover.jpg','images/timeout_hover.jpg','images/admin_hover.jpg')">
		<!--------WRAPER STARTS -------------------->
<div id="wraper">

                
                <div style="padding-left: 10px;width: 70%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->
                	
                <div>
					
					
				<div align="center">
						<logic:present name="linksForm" property="message">
							<font color="red"> <bean:write name="linksForm"
									property="message" /> </font>
						</logic:present>
					</div>
					
					<html:form action="/links.do" enctype="multipart/form-data">
					
					
					<table class=forumline align=center id='mytable1' border="1"> 
						   <FCK:editor instanceName="EditorDefault">
						   <jsp:attribute name="value">
				           <logic:present name="linksForm" property="contentDescriptionAdmin">
                           <bean:define id="content" name="linksForm"
									property="contentDescriptionAdmin" />
							<%out.println(content.toString());%>
                           </logic:present>
							<logic:notPresent name="linksForm" property="contentDescriptionAdmin">
							<bean:define id="abc" value="null" />
							</logic:notPresent>
				            </jsp:attribute>
							</FCK:editor>
							
							
							<center>
							<tr>
						<th  class="specalt" scope="row">
									Links
								</th>
								<td>
									<html:select property="linkName" onchange="displaySublinks()" style="background-color:#f6f6f6; width:150px; height:20px; border:#38abff 1px solid">
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
										<html:text property="subLinkName" style="background-color:#f6f6f6; width:150px; height:20px; border:#38abff 1px solid"/>
									</td>
								</tr>
								
								
								<tr><th  class="specalt" scope="row">Priority</th>
								<td><html:text property="priority" style="background-color:#f6f6f6; width:150px; height:20px; border:#38abff 1px solid"></html:text>
								</td></tr>
								
								
								<tr>
								
								<th  class="specalt" scope="row">Add Sublinks</th>
								<td><html:button value="Add Links" onclick="onAddLink()" property="method" />
								</td>
								
								</tr>
						
							<tr>
								<th  class="specalt" scope="row">
									Specify Text File :
									<html:file name="linksForm" property="fileNames"/>
								</th>

								<td align="center">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:button value="Upload Documents" onclick="onUpload()" property="method" />
								</td>
							</tr>


							<logic:notEmpty name="listName">
								<tr>
										<th class="head" align=center colspan="2">
										Uploaded Documents
									</th>
									
								</tr>
								
								<logic:iterate name="listName" id="listid">
									
									<bean:define id="file" name="listid"
										property="fileList" />
										
								<%
										String s = file.toString();
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) 
										{
										int x=v[i].lastIndexOf("/");
											String u=v[i].substring(x+1);
										
									%>
									<tr>
									<td><%=u%></td>
									
									<td width="15"><input type="checkbox" name="checkedfiles"
												value="<%=u%>" /></td>

									</tr>
									<%
									}
									%>		
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
									<th class="head" align=center colspan="2">
										<b><font color="white">Uploaded Video Files</font></b>
									</td>
									
								</tr>

								<logic:iterate name="listName1" id="listid1">
									<bean:define id="video" name="listid1"
										property="videoFilesList" />
								<%
										String s = video.toString();
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) {
										int x=v[i].lastIndexOf("/");
											String u=v[i].substring(x+1);
										
									%>
									<tr>
									<td><%=u%></td>
									<td width="15"><input type="checkbox" name="checkedvideos"
												value="<%=u%>" /></td>

									</tr>
									<%
									}
									%>
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
								
								<td>
								Already uploaded Icon
								<img src='<bean:write name="linksForm" property="iconList" />' width="37" height="35" border="0" style="float:left; margin-right:5px;" />
								<td>
								
							   </tr>
								
								
								<tr>
									<td  colspan="2" align="center">
										<html:button property="method" value="update" onclick="onUpdate()" />
									</td>
								</tr>

						</table>
						
					</html:form>
						</div>
                
            
            </div> <!--------CONTENT ENDS -------------------->
            
            
</div><!--------WRAPER ENDS -------------------->
                

</body>
</html>
