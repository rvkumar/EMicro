
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Microlab</title>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	  <script type="text/javascript" src="js/sorttable.js"></script>

<style type="text/css">

.text_field{background-color:#f6f6f6; width:150px; height:20px; border:#38abff 1px solid}
</style>

<script type="text/javascript">



function searchRecord()
{

if(document.forms[0].serchType.value=="")
	    {
	      alert("Please Enter Some Text");
	      document.forms[0].serchType.focus();
	      return false;
	    }

var url="links.do?method=searchRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function nextMaterialRecord()
{

var url="links.do?method=nextRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function previousMaterialRecord()
{

var url="links.do?method=previousRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function firstMaterialRecord()
{

var url="links.do?method=firstTenRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastMaterialRecord()
{

var url="links.do?method=lastTenRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function selectYearCmsContent(){
		
		
		if(document.forms[0].subLinkTitle.value=="")
	    {
	      alert("Please Enter Album Title");
	      document.forms[0].subLinkTitle.focus();
	      document.forms[0].contentYear.value="";
	      return false;
	    }
		
		
		
   		var url="links.do?method=selectYearCmsContent";
		document.forms[0].action=url;
		document.forms[0].submit();
}


function onBack() { 
	    
	    var url="links.do?method=displayCmsSublinksImages";
		document.forms[0].action=url;
		document.forms[0].submit();
}


	function onUpload() { //v3.0
  		if(document.forms[0].subLinkTitle.value=="")
	    {
	      alert("Please Enter Sub Link Title");
	      document.forms[0].subLinkTitle.focus();
	      return false;
	    }
	    
	    if(document.forms[0].contentYear.value=="")
	    {
	      alert("Please Select Year");
	      document.forms[0].contentYear.focus();
	      return false;
	    }
	   
	    if(document.forms[0].imageGalleryFile.value=="")
	    {
	      alert("Please Select File ");
	      document.forms[0].imageGalleryFile.focus();
	      return false;
	    }
	    
	    var url="links.do?method=uploadGalleryImage";
		document.forms[0].action=url;
		document.forms[0].submit();
}





function onImageDeleteFile(param){



var fileChecked=0;
var fileLength=document.forms[0].select1.length;
var fileLength1=document.forms[0].select1.checked;
if(fileLength1==true && fileLength==undefined)
{
var agree = confirm('Are You Sure To Delete Selected Requests');
if(agree)
{
var url="links.do?method=deleteLinkImageFile&param="+param;
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
if(document.forms[0].select1[i].checked==true)
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
var agree = confirm('Are You Sure To Modify Selected Requests');
if(agree)
{
var url="links.do?method=deleteLinkImageFile&param="+param;
	document.forms[0].action=url;
	document.forms[0].submit();
}
else
{
return false;
}
}	
}

function onImageModify(param){



var fileChecked=0;
var fileLength=document.forms[0].select1.length;
var fileLength1=document.forms[0].select1.checked;
if(fileLength1==true && fileLength==undefined)
{
var agree = confirm('Are You Sure To Modify Selected Requests');
if(agree)
{
var url="links.do?method=deleteLinkImageFile&param="+param;
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
if(document.forms[0].select1[i].checked==true)
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
var url="links.do?method=deleteLinkImageFile&param="+param;
	document.forms[0].action=url;
	document.forms[0].submit();
}
else
{
return false;
}
}	
}


function displaySubSublinks(){
	
	var url="links.do?method=displayCmsSublinksImages";
	document.forms[0].action=url;
	document.forms[0].submit();

}

function displaySublinks(){
	
	var url="links.do?method=displayCmsSublinks";
	document.forms[0].action=url;
	document.forms[0].submit();

}

function addNew(){

	if(document.forms[0].linkName.value=="")
	    {
	      alert("Please Select Link Name");
	      document.forms[0].linkName.focus();
	      return false;
	    }
	    if(document.forms[0].subLinkName.value=="")
	    {
	      alert("Please Select Sub Link Name ");
	      document.forms[0].subLinkName.focus();
	      return false;
	    }
	var url="links.do?method=selectYearCmsContent&sid=ad";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function onLinkSubmit(){
		
		if(document.forms[0].subLinkTitle.value=="")
	    {
	      alert("Please Enter Album Title");
	      document.forms[0].subLinkTitle.focus();
	      return false;
	    }
	    if(document.forms[0].contentYear.value=="")
	    {
	      alert("Please Select Year ");
	      document.forms[0].contentYear.focus();
	      return false;
	    }
	     if(document.forms[0].iconNames.value=="")
	    {
	      alert("Please Select Preview Image ");
	      document.forms[0].iconNames.focus();
	      return false;
	    }
		
   		var url="links.do?method=submitLinksContent";
		document.forms[0].action=url;
		document.forms[0].submit();
}

//-->
</script>





</head>

<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

    
      
  <tr>
  
    <td colspan="3" align="left" valign="top">
    <div class="bordered">
     			<div align="center">
						<logic:present name="linksForm" property="message">
							<font color="red"> <bean:write name="linksForm"
									property="message" /> </font>
						</logic:present>
					</div>
					
					<html:form action="/links.do" enctype="multipart/form-data">
					
					
					<logic:empty name="editDetails">
					
					
					<table align="center" class="bordered">
					
					
					<tr>
					 <th colspan="2"><center> Gallery & Awards </center></th>
     				</tr>
					
					
							<tr> 
							<th  class="specalt" scope="row">Links<img src="images/star.gif" width="8" height="8" /></th>
			
								
								<td >
									<html:select property="linkName" onchange="displaySublinks()" styleClass="text_field">
										<html:option value="">--SELECT--</html:option>
										<html:options property="linkIdList"
											labelProperty="linkValueList" name="linksForm" />
									</html:select>
								</td>
							</tr>
						
							<tr>
							<th  class="specalt" scope="row">Sub Links<img src="images/star.gif" width="8" height="8" /></th>
								<td >
									<html:select property="subLinkName"
										onchange="displaySubSublinks()" styleClass="text_field">
										<html:option value="">--SELECT--</html:option>
										<html:options property="subLinkIdList"
											labelProperty="subLinkValueList" name="linksForm" />
									</html:select>
								</td>
							</tr>
					</table>
					
					</logic:empty>
					
					
					
					
					<logic:notEmpty name="editDetails">
					
						
						
						 
							
							<table align="center" border="0" cellpadding="4" cellspacing="0" id="mytable1">
						
						
						<tr>
						<th colspan="7"><center>Gallery & Awards</center></th>
	        			
     				</tr>
						
						<tr><td colspan="4">
							<tr>
								<td >
									Links
								</td>
								<td>
									<html:select property="linkName" onchange="displaySublinks()">
										<html:option value="">--SELECT--</html:option>
										<html:options property="linkValueList"
											labelProperty="linkValueList" name="linksForm" />
									</html:select>
								</td>
							
									<td >
										Sub Link
									</td>
									<td>
										<html:text property="subLinkName" readonly="true"/>
									</td>
								</tr>
								
							
								<tr>
									<td >
									
									
									<logic:equal name="linksForm" property="subLinkName" value="Image Gallery">
										Album Title<img src="images/star.gif" width="8" height="8" />
									</logic:equal>
									<logic:notEqual name="linksForm" property="subLinkName" value="Image Gallery">
										Title<img src="images/star.gif" width="8" height="8" />
									</logic:notEqual>
										
									</td>
									<td>
										<html:text property="subLinkTitle" />
									</td>
								
								<td >
									Years<img src="images/star.gif" width="8" height="8" />
								</td>
								<td>
									<html:select property="contentYear" onchange="selectYearCmsContent()">
										<html:option value="">--SELECT--</html:option>
										<html:options property="years" name="linksForm" />
									</html:select>
								</td>
							</tr>
							<logic:notEmpty name="editFCKEditor">
							<tr>
							<td colspan="4">
							  <FCK:editor instanceName="EditorDefault" height="300px">
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
						
							<!--  <tr>
								<td>
									Specify Text File :
									<html:file name="linksForm" property="fileNames"/>
								</td>

								<td align="center">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:button value="Upload Documents" onclick="onUpload()" property="method" />
								</td>
							</tr>-->
								
								
								<logic:notEmpty name="ImageDetails">
								<tr>
								<th colspan="4"><center>Uploaded Images</center></th>
									
								</tr>
								
								<logic:iterate name="ImageDetails" id="listid">
									
									<tr>
									<td>
									<html:checkbox property="select1" value="${listid.imageId}" title="select"/>
									</td>
											
									<td align="center" >
										<bean:write name="listid" property="imageName" />
									</td>
									<td align="center" colspan="2">
										<html:text name="listid" property="imageTitle1" value="${listid.imageTitle}"/>
									</td>
								</tr>
								</logic:iterate>
								
								
								<tr>
								<td colspan="4" align="center">
								<html:button value="Modify Files" onclick="onImageModify('Modify')" property="method" />
								<html:button value="Delete Files" onclick="onImageDeleteFile('Delete')" property="method" />
								</td>
								</tr>
								
								
								</logic:notEmpty>
								
								
							<tr>
								<td class="specalt" scope="row">
									Upload Image File :
									</td>
									<td>
									<html:file name="linksForm" property="imageGalleryFile" style="width:150px;" value=""/>
								</td>
								
								<!--<logic:equal name="linksForm" property="subLinkName" value="Image Gallery">
										 Description 
								</logic:equal>
								--><logic:notEqual name="linksForm" property="subLinkName" value="Image Gallery">
										
								</logic:notEqual>
									</td>
									<td>
								<html:text name="linksForm" property="imageTitle"/>
								</td>
							
								<td align="center" colspan="4">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:button value="Upload Images" onclick="onUpload()" property="method" />
								</td>
								
								</tr>
								
								<tr>
								<td>
									Preview Image :<img src="images/star.gif" width="8" height="8" />
									</td>
									<td colspan="4">
									<html:file name="linksForm" property="iconNames"/>
								</td>
								
								</tr>
								
								
								<tr>
									<td  colspan="4" align="center">
										<html:button property="method" value="Submit" onclick="onLinkSubmit()" />
										<html:button value="Back" onclick="onBack()" property="method"/>
									</td>
								</tr>
								
								</logic:notEmpty>


						</table>
						
						
						</logic:notEmpty>
						
						
						
						<logic:notEmpty name="addbutton">
						<div align="left">
						
						Search <html:text property="serchType" styleClass="text_field"></html:text><img src="images/search.png"  onclick="searchRecord()"/>
						
								<html:button value="Add New" onclick="addNew()" property="method"/>
							
						</div>
							</logic:notEmpty>
							
							 <table align="center">
	 	<logic:notEmpty name="displayRecordNo">
	 <tr>
	  	<td>
	  	<img src="images/First10.jpg" onclick="firstMaterialRecord()"/>
	
	</td>
	
	<logic:notEmpty name="disablePreviousButton">
	<td>
	
	<img src="images/disableLeft.jpg" />
	</td>
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	<td>
	
	<img src="images/previous1.jpg" onclick="previousMaterialRecord()"/>
	</td>
	</logic:notEmpty>
	
	<td>
	<td>
	<bean:write property="startRecord"  name="linksForm"/>-
	</td>
	<td>
	<bean:write property="endRecord"  name="linksForm"/>
	</td>
	<logic:notEmpty name="nextButton">
	<td>
	<img src="images/Next1.jpg" onclick="nextMaterialRecord()"/>
	</td>
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	<td>
	
	<img src="images/disableRight.jpg" />
	</td>
	
	</logic:notEmpty>
		<td>
		<img src="images/Last10.jpg" onclick="lastMaterialRecord()"/>
	</td>
	</td>
	
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>

	

	
	</logic:notEmpty>
	 </tr>
	 </table>
							
						
						<logic:notEmpty name="CmsLinkDetails">
							
							<table   width="100%" class="borderd sortable">
								
								
												<tr>
							<th style="text-align:left;"><b>Sl. No</b></th>
							<th style="text-align:left;"><b>&nbsp;</b></th>
							<th style="text-align:left;"><b>Main Link Name</b></th>	
							<th style="text-align:left;"><b>Sub Link</b></th>	
							<th style="text-align:left;"><b>Award Name</b></th>	
							<th style="text-align:left;"><b>Content Year</b></th>
							<th style="text-align:left;"><b>Edit</b></th>
						</tr>
						
<tr style="height:5px;"><td></td></tr>
						<tr><td colspan="5" class="underline"></td></tr>
						<%
							int count = 1;
										
						%>
								
							
								<logic:iterate name="CmsLinkDetails" id="abc">
								<%if(count == 1) {%>
									<tr class="tableOddTR">
											<td>
											<bean:write name="abc" property="rowno" />
										</td>
										
										<td>
										<img src="<bean:write name="abc" property="iconName" />" width="50" height="60"/>	
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
										
										<td>
											<bean:write name="abc" property="contentYear" />
										</td>
										
										<td>
										<a href="links.do?method=selectCmsContent&id=<bean:write name="abc" property="cmsLinkId" />">
										<img src="images/edit1.jpg" />	
										</td>
									</tr>

									<% count++;
							} else {
								int oddoreven=0;
								oddoreven  = count%2;
								if(oddoreven == 0)
								{
									%>
									<tr class="tableEvenTR">
											<td>
											<bean:write name="abc" property="rowno" />
										</td>
										
										<td>
										<img src="<bean:write name="abc" property="iconName" />" width="50" height="60"/>	
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
										
										<td>
											<bean:write name="abc" property="contentYear" />
										</td>
										
										<td>
										<a href="links.do?method=selectCmsContent&id=<bean:write name="abc" property="cmsLinkId" />">
										<img src="images/edit1.jpg" />	
										</td>
										
									</tr>
											
									<% }else{%>
									<tr class="tableOddTR">
											<td>
											<bean:write name="abc" property="rowno" />
										</td>
										
										<td>
										<img src="<bean:write name="abc" property="iconName" />" width="50" height="60"/>	
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
										
										<td>
											<bean:write name="abc" property="contentYear" />
										</td>
										
										<td>
										<a href="links.do?method=selectCmsContent&id=<bean:write name="abc" property="cmsLinkId" />">
										<img src="images/edit1.jpg" />	
										</td>
									</tr>
									<% }count++;}%>

									
								
									

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
