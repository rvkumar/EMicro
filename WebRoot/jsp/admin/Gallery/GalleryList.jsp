<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	
	<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript">
function newGallery(){
	
	var url="gallery.do?method=newGallery";

	document.forms[0].action=url;
	document.forms[0].submit();
}
function deleteGallery(url)
{
	var agree = confirm('Are You Sure To Delete Gallery');
	if(agree)
	{
		document.forms[0].action=url;
		document.forms[0].submit();
	}
}

function nextRecord()
{

var url="gallery.do?method=nextRecord";

			document.forms[0].action=url;
			document.forms[0].submit();
}

function previousRecord()
{

var url="gallery.do?method=previousRecord";

			document.forms[0].action=url;
			document.forms[0].submit();

}
	

</script>
<style>
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
</head>

<body>
<form action="gallery" enctype="multipart/form-data" method="post">
<table class="borderd">
<tr>
<td><html:button property="method" value="Add New" onclick="newGallery()" styleClass="rounded"/></td>
</tr>
</table>
<!-- <div>&nbsp;</div> -->
&nbsp;&nbsp;
		
		<div align="center">  
					
							<logic:notEmpty name="displayRecordNo">
	 							<logic:notEmpty name="veryFirst">
	 								&nbsp;<a href="#"><img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/></a>&nbsp;
	 							</logic:notEmpty>
								<logic:notEmpty name="disablePreviousButton">
									&nbsp;<a href="#"><img src="images/disableLeft.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="previousButton">
									&nbsp;<a href="#"><img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
									&nbsp;<bean:write property="startRecord"  name="galleryForm"/>&nbsp;-&nbsp;<bean:write property="endRecord"  name="galleryForm"/>&nbsp;
								<logic:notEmpty name="nextButton">
									&nbsp;<a href="#"><img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="disableNextButton">
									&nbsp;<a href="#"><img src="images/disableRight.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="atLast">
									&nbsp;<a href="#"><img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/></a>
								</logic:notEmpty>
								
						
								</logic:notEmpty>
						</div>
	<html:hidden property="totalRecords" name="galleryForm"/>
	<html:hidden property="startRecord" name="galleryForm"/>
	<html:hidden property="endRecord" name="galleryForm"/>

<table class="bordered">
<tr><th><center><b><big>Galleries List</big></b></center></th></tr>
</table>
<logic:notEmpty name="CmsLinkDetails">
							
							<table  class="bordered">
						<tr>
							<th style="text-align:left;"><b>Sl. No</b></th>
							<th style="width:100px;"><b>Archive Status</b></th>
							<th style="text-align:left;"><b>Main Link Name</b></th>	
							<th style="text-align:left;"><b>Sub Link</b></th>	
							<th style="text-align:left;"><b>Award Name</b></th>	
							<th style="text-align:left;"><b>Content Year</b></th>
							<th style="text-align:left;"><b>Edit</b></th>
							<th>&nbsp;</th>
						</tr>
								<logic:iterate name="CmsLinkDetails" id="abc">
									<tr >
											<td>
											<bean:write name="abc" property="rowno" />
										</td>
										
										<td>
										<center><html:checkbox property="archiveStatus"  title="select" name="abc"/></center>
										</td>
										
										<td>
											<bean:write name="abc" property="menuName" />
										</td>
										<td>
											<bean:write name="abc" property="linkName" />
										</td>
										<td>
											<bean:write name="abc" property="galleryTitle" />
										</td>
										
										<td>
											<bean:write name="abc" property="contentYear" />
										</td>
										
										<td>
										<a href="gallery.do?method=selectCmsContent&id=<bean:write name="abc" property="cmsLinkId" />">
										<img src="images/edit1.jpg" />	
										</td>
										<td><a href="#" onclick="deleteGallery('gallery.do?method=deleteGallery&id=<bean:write name="abc" property="cmsLinkId" />')">
										<img src="images/deleteIcon.png" height="20" width="20"/></a></td>
									</tr>

																		
								
									

								</logic:iterate>

							</table>

						</logic:notEmpty>
						


</form>
</body>
</html>	