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
function createType()
{
var URL="materialApprover.do?method=createType";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function editMatrialType(location,type,group,matSubcat)
{ 
var URL="materialApprover.do?method=editMatrialType&Location="+location+"&matType="+type+"&matGroup="+group+"&matSubcat="+matSubcat;

		document.forms[0].action=URL;
 		document.forms[0].submit();
}
function deleteMatrialType(location,type,group)
{
var r=confirm("Are you sure to delete selected Material Type!")
if (r==true)
  {
 var URL="materialApprover.do?method=deleteMatType&Location="+location+"&matType="+type+"&matGroup="+group;
		document.forms[0].action=URL;
 		document.forms[0].submit();
  }
else
  {
  alert("You pressed Cancel!")
  }

}
function nextRecord()
{
     
	document.forms[0].action="materialApprover.do?method=next";
	document.forms[0].submit();

}
function previousRecord()
{
    
	document.forms[0].action="materialApprover.do?method=prev";
	document.forms[0].submit();

}
function searchMaterials(){
document.forms[0].action="materialApprover.do?method=searchMaterials";
	document.forms[0].submit();
}
</script>	
</head>
<body>
<html:form action="/materialApprover.do" enctype="multipart/form-data" onsubmit="searchMaterials(); return false;">
			
					
				<logic:notEmpty name="materialApproverForm" property="message">
				
				<font color="red">
							<bean:write name="materialApproverForm" property="message" />
						</font>
					</logic:notEmpty>
					
<div class="widgetTitle">Material Master Approvers</div>
<br/>
<table align="left" class="bordered">
<tr>
<th>Location <font color="red">*</font></th>
<td align="left">
	<html:select name="materialApproverForm" property="locationId">
		<html:option value="">--Select--</html:option>
		<html:options name="materialApproverForm" property="locationIdList" labelProperty="locationLabelList"/>
	</html:select>
</td>

<th>Material Type <font color="red">*</font></th>
<td>
<html:select property="materialType" name="materialApproverForm"  >
	<html:option value="">Select</html:option>
	<html:options name="materialApproverForm" property="materTypeIDList" 
			labelProperty="materialTypeIdValueList"/>
	<html:option value="Service Master">Service Master</html:option>
	<html:option value="Code Extention">Code Extention</html:option>
	<html:option value="Customer Master">Customer Master</html:option>
	<html:option value="Vendor Master">Vendor Master</html:option>
	<html:options name="materialApproverForm"  property="categortShortlist" labelProperty="categorylist"/>
	
</html:select>
</td>
</tr>
<tr>
<th>Mat.Group </th>
<td align="left">
	<html:select name="materialApproverForm" property="materialGroupId">
		<html:option value="">--Select--</html:option>
		<html:options name="materialApproverForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
	</html:select>
</td>
<th>Group </th>
<td align="left">
	<html:select name="materialApproverForm" property="customerGroupId">
		<html:option value="">--Select--</html:option>
		<html:option value="Export">Export</html:option>
	<html:option value="Domestic">Domestic</html:option>
	<html:option value="Local">Local</html:option>
	<html:option value="Import">Import</html:option>
	<html:option value="V">Validation</html:option>
	</html:select>
	&nbsp;<a href="#"></a>
</td>
</tr>

<th>Sub Category </th>
<td align="left">
	<html:select name="materialApproverForm" property="subCategoryId">
	    <html:option value="">--Select--</html:option>
		<html:options name="materialApproverForm"  property="subcatList" />
			</html:select>
	&nbsp;<a href="#"><img src="images/search.png" onclick="searchMaterials()" align="absmiddle"/></a>
</td>
</tr>
<tr>
<td colspan="4">
<html:button property="method" value="Create"   onclick="createType()" styleClass="rounded" style="width: 80px;"></html:button>

</td></tr>
</table>
<br/>
<br/>
         			
         			
         			<table align="left">
						<tr><td align="center">
					
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
									&nbsp;<bean:write property="startRecord"  name="materialApproverForm"/>&nbsp;-&nbsp;<bean:write property="endRecord"  name="materialApproverForm"/>&nbsp;
								<logic:notEmpty name="nextButton">
									&nbsp;<a href="#"><img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="disableNextButton">
									&nbsp;<a href="#"><img src="images/disableRight.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="atLast">
									&nbsp;<a href="#"><img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/></a>
								</logic:notEmpty>
								<!--<td style="align:right;text-align:center;">
									<img src="images/clear.jpg" style="vertical-align:middle;" onclick="searchInMyRequest('clear');" width="25" height="25" />
									<input type="text" id="searchText" style="padding-top: 3px; width: 200px;" class="rounded" value="Search in MyRequest" onmousedown="this.value='';"/>
									<img src="images/search-bg.jpg" style="vertical-align:middle;" onclick="searchInMyRequest('search')" width="40" height="50" />
								</td>
							-->
								</logic:notEmpty>
								</td>
							</tr>
						</table>
					
<logic:notEmpty name="materialTypeList">
<br/>
<table class="sortable bordered"> 
<tr>
	<th style="width:100px;">Location</th><th style="width:200px;">Material Type</th><th >Material Group</th>
	<th style="width:100px;">Total Approvers</th><th style="width:50px;">&nbsp;</th><th style="width:50px;">&nbsp;</th>
</tr>
<logic:iterate id="master" name="materialTypeList">
	<tr>
	<td>
	${master.locationId }
	</td>
	<td>
	${master.materialType }
	</td>
	<td>
	${master.materialGroupId }
	</td>
	<td>
	${master.totalApprovers }
	</td>
	<td>
	<img src="images/edit1.jpg" onclick="editMatrialType('${master.locationId }','${master.materialType }','${master.reqGroupId }','${master.subCategoryId}')"/>
	</td>
	<td>
	<img src="images/delete.png" onclick="deleteMatrialType('${master.locationId }','${master.materialType }','${master.reqGroupId }')"/>
	</td>
	</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="noRecords">
<table class="sortable bordered"> 
<tr>
	<th style="width:100px;">Location</th><th style="width:200px;">Material Type</th><th >Material Group</th>
	<th style="width:100px;">Total Approvers</th><th style="width:50px;">&nbsp;</th><th style="width:50px;">&nbsp;</th>
</tr>
<tr>
<td colspan="6"><center><font color="red">Search records are not found. </font></center></td>
</tr>

</logic:notEmpty>
 <html:hidden name="materialApproverForm" property="total"/>
 				<html:hidden name="materialApproverForm" property="next"/>
 				<html:hidden name="materialApproverForm" property="prev"/>
</html:form>
</body>
</html>	