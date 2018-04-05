<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Microlab</title>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	  <script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript">


function onUpload() { //v3.0
  		if(document.forms[0].subLinkTitle.value=="")
	    {
	      alert("Please Enter Album Title");
	      document.forms[0].subLinkTitle.focus();
	      return false;
	    }
	    
	    var url="links.do?method=uploadGalleryImage";
		document.forms[0].action=url;
		document.forms[0].submit();
}
	
	
	function onUploadCmsLinkFiles(){	   
			
			var url="links.do?method=uploadCmsLinkFilesModify";
			document.forms[0].action=url;
			document.forms[0].submit();		 
	}


function onLinkModify(){
		
		var url="links.do?method=updateCmsLinksContent1";
		document.forms[0].action=url;
		document.forms[0].submit();
}

function onDeleteFile(param){
	
	var fileChecked=0;
	
	 var appLength=document.forms[0].select1.length;
		  		 
		  		 var appLength1=document.forms[0].select1.checked;
			  			
			  	 if(appLength1==true && appLength==undefined)
			  	 {
		  		    var agree = confirm('Are You Sure To Delete Selected Records');
		            if(agree)
		            {	
		              var URL="links.do?method=deleteModifyLinkImageFile&param="+param;
					  document.forms[0].action=URL;
		    		  document.forms[0].submit();
		            }	
		            else
		    		{
		    		  return false;
		    		}	  	
		  		 }else{
	
var rows=document.forms[0].select1;

	
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
	document.forms[0].action="links.do?method=deleteModifyLinkImageFile&param="+param;
	document.forms[0].submit();
}
}
}
}

function onModifyFile(param){
	
	var fileChecked=0;
	
	 var appLength=document.forms[0].select1.length;
		  		 
		  		 var appLength1=document.forms[0].select1.checked;
			  			
			  	 if(appLength1==true && appLength==undefined)
			  	 {
		  		    var agree = confirm('Are You Sure To Modify Selected Records');
		            if(agree)
		            {	
		              var URL="links.do?method=deleteModifyLinkImageFile&param="+param;
					  document.forms[0].action=URL;
		    		  document.forms[0].submit();
		            }	
		            else
		    		{
		    		  return false;
		    		}	  	
		  		 }else{
	
var rows=document.forms[0].select1;

	
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
var agree = confirm('Are You Sure To Modify Selected file');
if(agree)
{
	document.forms[0].action="links.do?method=deleteModifyLinkImageFile&param="+param;
	document.forms[0].submit();
}
}
}
}


function onBack() { 
	    
	    var url="links.do?method=displayCmsSublinksImages";
		document.forms[0].action=url;
		document.forms[0].submit();
}


function selectYearCmsContent(){
		
   		var url="links.do?method=selectYearCmsContent";
		document.forms[0].action=url;
		document.forms[0].submit();
}

function onImageDeleteFile(){
		
		
   		var url="links.do?method=deleteLinkImageFile";
		document.forms[0].action=url;
		document.forms[0].submit();
}




function onLinkSubmit(){
		
   		var url="links.do?method=submitLinksContent";
		document.forms[0].action=url;
		document.forms[0].submit();
}

//-->
</script>


</head>

<body>

<table class="bordered sortable">

      <tr>
        <td colspan="3" align="center" valign="top" style="padding-top:3px">
        <div class="middelpart1">
        <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    
    <td colspan="3" align="left" valign="top">
    <div class="middel-blocks">
    
    
     			   <div align="center">
						<logic:present name="linksForm" property="message">
							<font color="red"> 
							<bean:write name="linksForm" property="message" /> 
							</font>
						</logic:present>
					</div>
					
					<html:form action="/links.do" enctype="multipart/form-data">
						
					
						  
							
							
								<table border="0" cellpadding="4" cellspacing="0" id="mytable1">
						
						
						
						
					
							<tr>
							<th colspan="4">Gallery & Awards</th>
      					</tr>
							<tr>
							 <th  class="specalt" scope="row">Links<img src="images/star.gif" width="8" height="8" /></th>
	  
									
								<td>
									<html:select property="linkName" onchange="displaySublinks()" styleClass="text_field" >
										<html:option value="">--SELECT--</html:option>
										<html:options property="linkValueList"
											labelProperty="linkValueList" name="linksForm" />
									</html:select>
								</td>
							</tr>
							
							
								<tr>
									 <th  class="specalt" scope="row">Sub Link<img src="images/star.gif" width="8" height="8" /></th>
									<td>
										<html:text property="subLinkName" readonly="true" styleClass="text_field"/>
									</td>
								</tr>
								
								
								
								<tr>
									 <th  class="specalt" scope="row">Title<img src="images/star.gif" width="8" height="8" /></th>
									
									<td>
										<html:text property="subLinkTitle" styleClass="text_field"/>
									</td>
								</tr>
							
							
							
								<tr>
								<th  class="specalt" scope="row">Years<img src="images/star.gif" width="8" height="8" /></th>
								
								<td>
									<html:select property="contentYear" onchange="selectYearCmsContent()" styleClass="text_field">
										<html:option value="">--SELECT--</html:option>
										<html:options property="years"
											 name="linksForm" />
									</html:select>
								</td>
							</tr>
								
							
							<tr>
										<th  class="specalt" scope="row">Archive</th>
									
									<td>
										<html:checkbox property="cmsLinkArchive"  title="select"/>
									</td>
								</tr>
								<tr>
								<td colspan="2">
								 <FCK:editor instanceName="EditorDefault" height="300px;" >
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
							</td>
								</tr>
								
								</table>
								<!-- <tr>
								
								<td>Add Sublinks</td>
								<td><html:button value="Add Links" onclick="onAddLink()" property="method" />
								</td>
								
								</tr> -->
						
							<!--<tr>
								<td>
									Specify Text File :
									<html:file name="linksForm" property="fileNames"/>
								</td>

								<td align="center">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:button value="Upload Documents" onclick="onUpload()" property="method" />
								</td>
							</tr>-->
								
								<table border="1" id="mytable1">
							<logic:notEmpty name="listName">
								<tr>
									<th colspan="2"><center>Uploded Images</center></th>
									
								</tr>
								
								<logic:iterate name="listName" id="listid">
									
									<tr>
									<td>
									<html:checkbox property="select1" value="${listid.imageId}" title="select"/>
									
										<bean:write name="listid" property="imageName" />
									</td>
									<td align="center" >
										<html:text name="listid" property="imageTitle1" value="${listid.imageTitle}" styleClass="text_field"/>
									</td>
								</tr>
									</logic:iterate>
								
								</logic:notEmpty>
								
								<tr>
								<td colspan="2" align="center">
								<html:button value="Modify Files" onclick="onModifyFile('Modify')" property="method" />
								<html:button value="Delete Files" onclick="onDeleteFile('Delete')" property="method" />
								</td>
								</tr>
								
								
							<tr>
								
								 <td align="left"><span class="text">Upload Image File :</span>
									
									<html:file name="linksForm" property="imageGalleryFile" styleClass="text_field"/>
							<span class="text"> Description </span>
									
								<html:text name="linksForm" property="imageTitle" styleClass="text_field"/>
								</td>
								
							</tr>
							
							<tr>
							<td align="center" colspan="2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:button value="Upload Images" onclick="onUploadCmsLinkFiles()" property="method" />
								</td>
								</tr>
								
								<tr>
									<td align="left"><span class="text">Preview Image :</span>
									<html:file name="linksForm" property="iconNames"/>
								</td>
								
								</tr>
								
								
								<tr>
									<td  colspan="2" align="center">
										<html:button property="method" value=" Modify " onclick="onLinkModify()" />
										
										<html:button value=" Back " onclick="onBack()" property="method"/>
									</td>
								</tr>

						</table>
						
						
						<logic:notEmpty name="CmsLinkDetails">

							<table class=forumline align=center width='60%'>

								<tr>
									<td bgcolor="4F79B8" colspan=5>
									<font color="white">
										Cms Links</font>
									</td>
								</tr>
								
								
								<tr height='20'>
									<td align=center>
										<b>Sl. No</b>
									</td>
									<td align=center>
										<b>Main Link Name</b>
									</td>
									<td align=center>
										<b>Sub Link Name</b>
									</td>
									<td align=center>
										<b>Link Name</b>
									</td>

								</tr>
								<%int count=1; %>
								<logic:iterate name="CmsLinkDetails" id="abc">

									<tr>
										<td>
											<a href="links.do?method=selectCmsContent&id=<bean:write name="abc" property="cmsLinkId" />">
											<%=count++ %>
										</td>
										<td>
											<bean:write name="abc" property="linkName" />
										</td>
										<td>
											<bean:write name="abc" property="subLinkName" />
										</td>
										<td>
											<bean:write name="abc" property="subLinkTitle" />
										</td>
									</tr>

								</logic:iterate>

							</table>

						</logic:notEmpty>
						
						
						
						
						
					</html:form>
</div>
</td>
      </tr>
      </table></td></tr>
    <tr><td>    
    
  
</div></td></tr>
</table>
</body>
</html>
