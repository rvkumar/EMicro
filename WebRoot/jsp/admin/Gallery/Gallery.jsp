<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">

function onBack(){
	var url="gallery.do?method=galleryList";
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
	var url="gallery.do?method=modifyImageFile&param="+param;
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
	var url="gallery.do?method=modifyImageFile&param="+param;
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
	var url="gallery.do?method=modifyImageFile&param="+param;
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
	alert('Select Atleast One Record To Modify');
	return false;
	}
	else
	{
	}
	var agree = confirm('Are You Sure To Modify Selected Requests');
	if(agree)
	{
	var url="gallery.do?method=modifyImageFile&param="+param;
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else
	{
	return false;
	}
	}	
	}

function onUpload() { //v3.0
		if(document.forms[0].galleryTitle.value=="")
    {
      alert("Please Enter Album Title");
      document.forms[0].galleryTitle.focus();
      return false;
    }
		var st = document.forms[0].galleryTitle.value;
		var Re = new RegExp("\\'","g");
		st = st.replace(Re,"`");
		st = st.replace(",", " ");
		document.forms[0].galleryTitle.value=st;
		
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
    
    if(document.forms[0].imageTitle.value=="")
    {
      alert("Please Enter Image Title ");
      document.forms[0].imageTitle.focus();
      return false;
    }
    
    if(document.forms[0].imageTitle.value!=""){
	    var st = document.forms[0].imageTitle.value;
		var Re = new RegExp("\\'","g");
		st = st.replace(Re,"`");
		st = st.replace(",", " ");
		document.forms[0].imageTitle.value=st;
    }
    if(document.forms[0].imageGalleryFile.value==""){
    	 alert("Please Choose File ");
         document.forms[0].imageGalleryFile.focus();
         return false;
    }
    var fileName=document.forms[0].imageGalleryFile.value;
	var ext = fileName.split('.').pop();
    if(ext=="jpg"||ext=="JPG"){
    
    }else{
	alert("Please Upload only jpg Images");
    	
    	return false;
    }
    
    
    var url="gallery.do?method=uploadGalleryImage";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function onSubmit(){
	
	if(document.forms[0].galleryTitle.value=="")
    {
      alert("Please Enter Album Title");
      document.forms[0].galleryTitle.focus();
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
 
 		var url="gallery.do?method=submitGallery";
 		document.forms[0].action=url;
 		document.forms[0].submit();
	
		
	
	
}
function newGallery(){
	var url="gallery.do?method=newGallery";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function selectYearCmsContent(){
	if(document.forms[0].galleryTitle.value=="")
    {
      alert("Please Enter Album Title");
      document.forms[0].galleryTitle.focus();
      document.forms[0].contentYear.value="";
      return false;
    }
		var url="gallery.do?method=selectYearCmsContent";
	document.forms[0].action=url;
	document.forms[0].submit();
}
</script>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
</head>
<body>
	<html:form action="gallery" enctype="multipart/form-data" method="post">
	 <div align="center">
						<logic:present name="galleryForm" property="message">
							<font color="green" size="2"> 
							<bean:write name="galleryForm" property="message" /> 
							</font>
						</logic:present>
						<logic:present name="galleryForm" property="message1">
							<font color="red" size="2"> 
							<bean:write name="galleryForm" property="message1" /> 
							</font>
						</logic:present>
					</div>
		<table class="bordered">
			<tr>
				<th colspan="4"><center>
						<big>Gallery & Awards</big>
					</center></th>
			</tr>
          <html:hidden property="menuName" />
          <html:hidden property="linkName" />
			<tr>
				<td>Menu</td>
				<td><bean:write name="galleryForm" property="menuName" ></bean:write></td>

				<td>Link</td>
				<td><bean:write name="galleryForm" property="linkName" ></bean:write></td>
			</tr>


			<tr>
				<td>Album Title<img src="images/star.gif" width="8" height="8" />
				</td>
				<td><html:text property="galleryTitle" styleClass="rounded"/></td>

				<td>Years<img src="images/star.gif" width="8" height="8" styleClass="rounded"/>
				</td>
				<td><html:select property="contentYear"
						onchange="selectYearCmsContent()" styleClass="rounded">
						<html:option value="">--SELECT--</html:option>
						<html:option value="2011">2011</html:option>
						<html:option value="2012">2012</html:option>
						<html:option value="2013">2013</html:option>
						<html:option value="2014">2014</html:option>
						<html:option value="2015">2015</html:option>
						<html:option value="2016">2016</html:option>
						<html:option value="2017">2017</html:option>
					</html:select></td>
			</tr>
			<tr>
				<td colspan="4"><FCK:editor instanceName="EditorDefault"
						height="300px">
						<jsp:attribute name="value">
				           <logic:present name="galleryForm"
								property="contentDescriptionAdmin">
                           <bean:define id="content" name="galleryForm"
									property="contentDescriptionAdmin" />
							<%out.println(content.toString());%>
                           </logic:present>
							<logic:notPresent name="galleryForm"
								property="contentDescriptionAdmin">
							<bean:define id="abc" value="null" />
							</logic:notPresent>
				              </jsp:attribute>
					</FCK:editor></td>
			</tr>
			<tr>
				<td class="specalt" scope="row">Upload Image File :</td>
				<td><html:file name="galleryForm" property="imageGalleryFile"
						style="width:150px;" value="" /></td>
				<td><html:text name="galleryForm" property="imageTitle" title="Image Title" style="width:250px;" styleClass="rounded"/></td>

				<td align="center" colspan="4">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<html:button value="Upload Images" onclick="onUpload()"
						property="method" styleClass="rounded"/>
				</td>

			</tr>
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
										<html:text name="listid" property="imageTitle1" value="${listid.imageTitle}" styleClass="rounded" style="width:250px;"/>
									</td>
								</tr>
								</logic:iterate>
								
								
								<tr>
								<td colspan="4" ><center>
								<html:button value="Modify Files" onclick="onImageModify('Modify')" property="method" styleClass="rounded"/>
								<html:button value="Delete Files" onclick="onImageDeleteFile('Delete')" property="method"  styleClass="rounded"/></center>
								</td>
								</tr>
								
								
								</logic:notEmpty>
			<tr>
								<td>
									Preview Image :<img src="images/star.gif" width="8" height="8" />
									</td>
									<td colspan="4">
									<html:file name="galleryForm" property="iconNames"/>
								</td>
								
								</tr>
								
								
								<tr>
									<td  colspan="4" ><center>
										<html:button property="method" value="Submit" onclick="onSubmit()" styleClass="rounded"/>&nbsp;&nbsp;
										<html:button value="Back" onclick="onBack()" property="method" styleClass="rounded"/></center>
									</td>
								</tr>
		</table>
	</html:form>
</body>
</html>
