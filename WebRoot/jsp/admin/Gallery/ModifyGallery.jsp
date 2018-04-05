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
function onBack(){
	var url="gallery.do?method=galleryList";
	document.forms[0].action=url;
	document.forms[0].submit();
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
		 if(document.forms[0].imageGalleryFile.value=="")
		    {
		      alert("Please Select File ");
		      document.forms[0].imageGalleryFile.focus();
		      return false;
		    }
		 if(document.forms[0].imageTitle.valu!=""){
			 alert("Please Enter Image Title ");
		      document.forms[0].imageGalleryFile.focus();
		      return false;
		    }
		    if(document.forms[0].imageTitle.valu!=""){
			    var st = document.forms[0].imageTitle.value;
				var Re = new RegExp("\\'","g");
				st = st.replace(Re,"`");
				st = st.replace(",", " ");
				document.forms[0].imageTitle.value=st;
		    }
		
		
	    var url="gallery.do?method=uploadGalleryImage";
		document.forms[0].action=url;
		document.forms[0].submit();
}
	
	
	function onUploadCmsLinkFiles(){
		 if(document.forms[0].imageTitle.value=="")
		    {
		      alert("Please Enter Image Title ");
		      document.forms[0].imageTitle.focus();
		      return false;
		    }
		var st = document.forms[0].galleryTitle.value;
		var Re = new RegExp("\\'","g");
		st = st.replace(Re,"`");
		st = st.replace(",", " ");
		document.forms[0].galleryTitle.value=st;
		 if(document.forms[0].imageGalleryFile.value=="")
		    {
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
			var url="gallery.do?method=uploadCmsLinkFilesModify";
			document.forms[0].action=url;
			document.forms[0].submit();		 
	}


function onLinkModify(){
	
		var url="gallery.do?method=updateCmsLinksContent1";
		document.forms[0].action=url;
		document.forms[0].submit();
	
}

function onDeleteFile(param){
	
	var fileChecked=0;
	
	 var appLength=document.forms[0].select1.length;
		  		 
		  		 var appLength1=document.forms[0].select1.checked;
			  			
			  	 if(appLength1==true && appLength==undefined)
			  	 {
		  		    var agree = confirm('Are You Sure To Delete Selected Image');
		            if(agree)
		            {	
		              var URL="gallery.do?method=deleteModifyLinkImageFile&param="+param;
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
	document.forms[0].action="gallery.do?method=deleteModifyLinkImageFile&param="+param;
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
		  		    var agree = confirm('Are You Sure To Modify Selected Image');
		            if(agree)
		            {	
		              var URL="gallery.do?method=deleteModifyLinkImageFile&param="+param;
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
alert('please select atleast one Image to Modify');
}
else
{
var agree = confirm('Are You Sure To Modify Selected file');
if(agree)
{
	document.forms[0].action="gallery.do?method=deleteModifyLinkImageFile&param="+param;
	document.forms[0].submit();
}
}
}
}





function selectYearCmsContent(){
		
   		var url="gallery.do?method=selectYearCmsContent";
		document.forms[0].action=url;
		document.forms[0].submit();
}

function onImageDeleteFile(){
		
		
   		var url="gallery.do?method=deleteLinkImageFile";
		document.forms[0].action=url;
		document.forms[0].submit();
}




function onLinkSubmit(){
		
   		var url="gallery.do?method=submitLinksContent";
		document.forms[0].action=url;
		document.forms[0].submit();
}

//-->
</script>


</head>

<body>
   <div align="center">
						<logic:present name="galleryForm" property="message">
							<font color="green" size="2"> 
							<b><bean:write name="galleryForm" property="message" /></b> 
							</font>
						</logic:present>
					</div>
<table class="bordered sortable">

      <tr>
        <td colspan="3" align="center" valign="top" style="padding-top:3px">
        <div class="middelpart1">
        <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    
    <td colspan="3" align="left" valign="top">
    
    
     			
					
					<html:form action="/gallery.do" enctype="multipart/form-data">
								<table border="0" cellpadding="4" cellspacing="0" id="mytable1">
							<tr>
							<th colspan="4">Gallery & Awards</th>
      					</tr>
								<tr>
				<td>Menu</td>
				<td><html:text property="menuName" styleClass="rounded" value="About Company"></html:text></td>

				<td>Link</td>
				<td><html:text property="linkName" styleClass="rounded" value="Gallery"></html:text></td>
			</tr>
								
								
								
								<tr>
									 <td>Album Title<img src="images/star.gif" width="8" height="8" /></td>
									
									<td>
										<html:text property="galleryTitle" styleClass="text_field"/>
									</td>
								
								<td>Years<img src="images/star.gif" width="8" height="8" /></td>
								
								<td>
									<html:select property="contentYear" onchange="selectYearCmsContent()" styleClass="text_field">
											<html:option value="">--SELECT--</html:option>
						<html:option value="2011">2011</html:option>
						<html:option value="2012">2012</html:option>
						<html:option value="2013">2013</html:option>
						<html:option value="2014">2014</html:option>
						<html:option value="2015">2015</html:option>
						<html:option value="2016">2016</html:option>
						<html:option value="2017">2017</html:option>
									</html:select>
								</td>
							</tr>
								
							
							<tr>
										<td>Archive</td>
									
									<td colspan="3">
										<html:checkbox property="cmsLinkArchive"  title="select"/>
									</td>
								</tr>
								<tr>
								<td colspan="4">
								 <FCK:editor instanceName="EditorDefault" height="500px;" >
						   <jsp:attribute name="value">
				           <logic:present name="galleryForm" property="contentDescriptionAdmin">
                           <bean:define id="content" name="galleryForm"
									property="contentDescriptionAdmin" />
							<%out.println(content.toString());%>
                           </logic:present>
							<logic:notPresent name="galleryForm" property="contentDescriptionAdmin">
							<bean:define id="abc" value="null" />
							</logic:notPresent>
				              </jsp:attribute>
							</FCK:editor>
							</td>
								</tr>
								
								</table>
								
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
									<td >
										<html:text name="listid" property="imageTitle1" value="${listid.imageTitle}" style="width:200px;" styleClass="rounded"/>
									</td>
								</tr>
									</logic:iterate>
								
								</logic:notEmpty>
								
								<tr>
								<td colspan="4"><center>
								<html:button value="Modify Files" onclick="onModifyFile('Modify')" property="method" styleClass="rounded"/>&nbsp;
								<html:button value="Delete Files" onclick="onDeleteFile('Delete')" property="method" styleClass="rounded"/></center>
								</td>
								</tr>
								
								
							<tr>
								
								 <td align="left" colspan="4"><span class="text">Upload Image File :</span>
									
									<html:file name="galleryForm" property="imageGalleryFile" styleClass="text_field"/>
							<span class="text"> Description </span>
									
								<html:text name="galleryForm" property="imageTitle" styleClass="text_field"  />
								</td>
								
							</tr>
							
							<tr>
							<td align="center" colspan="4">
									
									<center><html:button value="Upload Image" onclick="onUploadCmsLinkFiles()" property="method" styleClass="rounded"/></center>
								</td>
								</tr>
								
								<tr>
									<td colspan="4"><span class="text">Preview Image :</span>
									<html:file name="galleryForm" property="iconNames"/>
								</td>
								
								</tr>
								
								
								<tr>
									<td  colspan="4" >
										<center><html:button property="method" value=" Modify " onclick="onLinkModify()" styleClass="rounded"/>
										
										<html:button value=" Back " onclick="onBack()" property="method" styleClass="rounded"/></center>
									</td>
								</tr>

						</table>
					</html:form>
</td>
      </tr>
      </table></td></tr>
    <tr><td>    
    
  
</div></td></tr>
</table>
</body>
</html>
