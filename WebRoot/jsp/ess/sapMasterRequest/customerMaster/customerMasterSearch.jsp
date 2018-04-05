<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Raw Material </title>

	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	
<script type="text/javascript">
function getMaterialRec()
{
if(document.forms[0].customerName1.value=="")
{
alert("Please Enter Customer Name");
document.forms[0].customerName1.focus();
return false;
}

if(document.forms[0].city1.value=="")
{
alert("Please Enter City Name");
document.forms[0].city1.focus();
return false;
}
var url="customerMaster.do?method=showCustomerSearchRecords";
document.forms[0].action=url;
document.forms[0].submit();

}
function clearSearch()
{
 document.getElementById("showMaterialList").style="visibility:hidden;";

}
function shownewMaterialForm()
{
var url="customerMaster.do?method=display";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}

function back()
{
var url="customerMaster.do?method=displayCustomerList";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}
function copyMaterial(reqNo,materialType)
{
var materilaType=materialType;
if(materilaType=='ROH')
{
var url="rawMaterial.do?method=copyNewRawMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='VERP')
{
var url="packageMaterial.do?method=copyNewPackageMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='HALB')
{
var url="semifinished.do?method=copyNewSemiFinished&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='FERT'||materilaType=='HAWA')
{
var url="finishedProduct.do?method=copyNewFinishedProduct&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}


if(materilaType=='ZPPC')
{
var url="promotional.do?method=copyNewPromotional&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}

if(materilaType=='ZPSR')
{
var url="zpsr.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZLAB')
{
var url="generalMaterial.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZCIV')
{
var url="generalMaterial.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZCON')
{
var url="generalMaterial.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZSCR')
{
var url="generalMaterial.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZITC')
{
var url="generalMaterial.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZPFL')
{
var url="generalMaterial.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}

}
function copyCustomer(requestNo){

var url="customerMaster.do?method=copyCustomerRecord&RequestNo="+requestNo;
			document.forms[0].action=url;
			document.forms[0].submit();

}
function nextMaterialRecord()
{

var url="customerMaster.do?method=nextCodeSearchRecords";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function previousMaterialRecord()
{

var url="customerMaster.do?method=previousCodeSearchRecords";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function firstMaterialRecord()
{

var url="customerMaster.do?method=showCustomerSearchRecords";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastMaterialRecord()
{

var url="customerMaster.do?method=lastCodeSearchRecords";
			document.forms[0].action=url;
			document.forms[0].submit();

}
</script>	
</head>
<body>
<html:form action="/customerMaster.do" enctype="multipart/form-data">

<br/>
<table class="bordered">
<tr>
			<th colspan="6"><center>Customer Code Search</center></th>
			</tr>
			<tr>
			<th>Name <font color="red">*</font></th></th>
					<td align="left">
				<html:text property="customerName1" maxlength="40"  title="Maximum of 40 characters" style="width:95%;text-transform:uppercase"></html:text>
					
					</td>
			<th>City<font color="red">*</font></th>
			<td>
				<html:text property="city1" maxlength="40"  title="Maximum of 40 characters" style="width:95%;text-transform:uppercase"></html:text>
		
			</td>
		
			<th>Country</th>
			<td>
		<html:select name="customerMasterForm" property="countryId1" style="width:95%;">
							<html:option value="">--Select--</html:option>
							<html:options name="customerMasterForm" property="counID" labelProperty="countryName"/>
						</html:select>
			</td>
			</tr>
			
			<td colspan="6" align="center"><center><html:button property="method" value="Search" styleClass="rounded" onclick="getMaterialRec()"></html:button>
		
			<html:button property="method" value="Close" styleClass="rounded" onclick="back()"></html:button>
			
			</center>
			</tr>
</table>
<div>&nbsp;</div>
<div align="center">
	<logic:notEmpty name="displayRecordNo">
	  	<img src="images/First10.jpg" onclick="firstMaterialRecord()" align="absmiddle"/>
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">

	
	<img src="images/previous1.jpg" onclick="previousMaterialRecord()" align="absmiddle"/>

	</logic:notEmpty>
	
	
	
	<bean:write property="codeStartRecord"  name="customerMasterForm"/>-
	
	
	<bean:write property="codeEndRecord"  name="customerMasterForm"/>

	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextMaterialRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	
	
	<img src="buttons/disableRight.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastMaterialRecord()" align="absmiddle"/>
	

	
	<html:hidden property="totalCodeSearch"/>
	<html:hidden property="codeStartRecord"/>
	<html:hidden property="codeEndRecord"/>
	</logic:notEmpty>
	</div>
<logic:notEmpty name="materialList">
<html:button property="method"  value="Continue To Create"  onclick="shownewMaterialForm()" styleClass="rounded" style="width: 140px;"/>&nbsp;&nbsp;
<div>&nbsp;</div>
<table class="bordered" width="100%" style="width: 100%;">
<tr>
<th>Customer Account Group</th><th>SAP Code No</th><th> Name</th><th>City</th><th>Created On</th><th>Requested On</th><th>Copy</th>
</tr>
<logic:iterate id="abc" name="materialList">
<tr>
<td><bean:write name="abc" property="accGroupId"/></td>
<td><bean:write name="abc" property="sapCodeNo"/></td>
<td><bean:write name="abc" property="customerName"/></td>
<td><bean:write name="abc" property="city"/></td>
<td><bean:write name="abc" property="sapCreationDate"/></td>
<td><bean:write name="abc" property="requestDate"/></td>



 <logic:notEqual value="" name="abc" property="requestNo"> 
<td><a href="#"><a href="# " title="copy" onclick="copyCustomer('${abc.requestNo}')"><img src="images/copy.png" height="28" width="28" align="absmiddle" /></a>
 </td>
 </logic:notEqual>
 <logic:equal value="" name="abc" property="requestNo"> 
<td>&nbsp;
 </td>
 </logic:equal>

</tr>
</logic:iterate>
</logic:notEmpty>


<logic:notEmpty name="noMaterialList">
<html:button property="method"  value="Continue To Create"  onclick="shownewMaterialForm()" styleClass="rounded" style="width: 140px;"/>&nbsp;&nbsp;
<br/>
<table class="bordered" width="100%" style="width: 100%;">

<tr>
<th>Account Group</th><th>SAP Code No</th><th> Name</th><th>City</th><th>Created On</th><th>Requested On</th><th>Copy</th></tr>
<tr>
<td colspan="9">
<center><font color="red" size="2"><b>Searched details could not be found.</b></font></center>
</td></tr></table>
</logic:notEmpty>
</html:form>
</body>
</html>