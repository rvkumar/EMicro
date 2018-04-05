<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Raw Material </title>

	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
		<script type="text/javascript" src="js/sorttable.js"></script>
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
<script type="text/javascript">

function nextMaterialRecord()
{

var url="materialCode.do?method=nextSearchRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function previousMaterialRecord()
{

var url="materialCode.do?method=previousSearcRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function firstMaterialRecord()
{

var url="materialCode.do?method=firstSearcRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastMaterialRecord()
{

var url="materialCode.do?method=lastSearcRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function getMaterialRec()
{
 var location=document.forms[0].locationId.value;
 var materialType=document.forms[0].materialType.value;
 var materialGrup=document.forms[0].materialGrup.value;
 var shortName=document.forms[0].shortName.value;
 var longName=document.forms[0].longName.value;

 if(document.forms[0].materialType.value=="")
 {
  alert("Please Select Material Type");
   document.forms[0].materialType.focus();
 return false;
 }
  if(document.forms[0].shortName.value=="")
 {
  alert("Please Enter Short Name");
   document.forms[0].shortName.focus();
 return false;
 }
 
 
var url="materialCode.do?method=showMaterialSeaarchlList";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function clearSearch()
{
 document.getElementById("showMaterialList").style="visibility:hidden;";

}
function shownewMaterialForm()
{
var materilaType=document.forms[0].materialType.value;
var locationId="";
var materialType=document.forms[0].materialType.value;
var materialGrup=document.forms[0].materialGrup.value;
var shortName=document.forms[0].shortName.value;
var longName=document.forms[0].longName.value;
 
if(materilaType=="")
{ 
	alert("Please Select Material Type");
	document.forms[0].materialType.focus();
	return false;
}

if(materilaType=='1')
{
var url="rawMaterial.do?method=displayNewMaterialCodeMaster&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='2')
{
var url="packageMaterial.do?method=displayNewPackageMaterial&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='3')
{
var url="semifinished.do?method=displayNewSemiFinished&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='4'||materilaType=='5')
{
if(materilaType=='4')
{
materilaType='4';
var url="finishedProduct.do?method=displayNewFinishedProduct&materialType="+materilaType+"&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();
}	
if(materilaType=='5')
{
materilaType='5';
var url="finishedProduct.do?method=displayNewFinishedProduct&materialType="+materilaType+"&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();
}
}


if(materilaType=='12')
{
var url="promotional.do?method=displayNewPromotional&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();	
}

if(materilaType=='13')
{
materilaType='13';
var url="zpsr.do?method=displayNewGeneralMaterial&materialType="+materilaType+"&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='10')
{
materilaType='10';
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType+"&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='7')
{
materilaType='7';
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType+"&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='8')
{
materilaType='8';
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType+"&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='14')
{
materilaType='14';
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType+"&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='9')
{
materilaType='9';
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType+"&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='11')
{
materilaType='11';
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType+"&locationId="+locationId+"&materialGrup="+materialGrup+"&longName="+longName+"&shortName="+shortName;
			document.forms[0].action=url;
			document.forms[0].submit();	
}

}

function back()
{
var url="materialCode.do?method=displayMaterialList";
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

</script>	
</head>
<body style="text-transform:uppercase;">
<html:form action="/materialCode.do" enctype="multipart/form-data">

<br/>
<table class="bordered">
<tr>
			<th colspan="6"><center>Material Code Search</center></th> 
			</tr>
			<tr>
			<html:hidden property="locationId"/>
			<!--<th>Location </th>
					<td align="left">
						<html:select name="materialCodeForm" property="locationId">
							<html:option value="">--Select--</html:option>
							<html:options name="materialCodeForm" property="locationIdList" labelProperty="locationLabelList"/>
						</html:select>
					</td>
			--><th>Material Type<font color="red">*</font></th>
			<td>
		<html:select property="materialType" name="materialCodeForm" > 
							<html:option value="">Select</html:option>
							<html:options name="materialCodeForm" property="materTypeIDList" 
									labelProperty="materialTypeIdValueList"/>
						</html:select>
			</td>
			<th>Material Group</th>
			<td>
		<html:select name="materialCodeForm" property="materialGrup">
							<html:option value="">--Select--</html:option>
							<html:options name="materialCodeForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
						</html:select>
			</td>
			</tr>
			<tr>
			<th >Short&nbsp;Name<font color="red">*</font></th><td colspan="5"><html:text property="shortName" maxlength="40"  title="Maximum of 40 characters" style="width:400px;text-transform:uppercase"></html:text>
			</td>
			</tr>
			<tr>
				<th>Long Name</th><td colspan="5"><html:text property="longName" maxlength="80"  title="Maximum of 40 characters" style="width:700px;text-transform:uppercase"></html:text>
			</td>
			</tr>
			<tr>
				
			<td colspan="6" align="center"><center><html:button property="method" value="Search" styleClass="rounded" onclick="getMaterialRec()"></html:button>
		
			<html:button property="method" value="Close" styleClass="rounded" onclick="back()"></html:button>
			
			</center>
			</tr>
</table>
<br/>
<logic:notEmpty name="materialList">
<table align="center">
<tr>
<td>
<html:button property="method"  value="Continue To Create"  onclick="shownewMaterialForm()" styleClass="rounded" style="width: 140px;"/>&nbsp;&nbsp;
</td>
<logic:notEmpty name="displayRecordNo">
	  	<td align="left">
	  <a href="#">	<img src="images/First10.jpg" onclick="getMaterialRec()" align="absmiddle"/></a>
	<logic:notEmpty name="disablePreviousButton">
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	</logic:notEmpty>
	<logic:notEmpty name="previousButton">
	<a href="#"><img src="images/previous1.jpg" onclick="previousMaterialRecord()" align="absmiddle"/></a>
	</logic:notEmpty>
	<bean:write property="startSearchRecord"  name="materialCodeForm"/>-
	<bean:write property="endSearchRecord"  name="materialCodeForm"/>
	<logic:notEmpty name="nextButton">
	<a href="#"><img src="images/Next1.jpg" onclick="nextMaterialRecord()" align="absmiddle"/></a>
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	<img src="images/disableRight.jpg" align="absmiddle" />
	</logic:notEmpty>
		<a href="#"><img src="images/Last10.jpg" onclick="lastMaterialRecord()" align="absmiddle"/></a>
	</td>
	
	<html:hidden property="totalSearchRecords"/>
	<html:hidden property="startSearchRecord"/>
	<html:hidden property="endSearchRecord"/>
	</logic:notEmpty>
	</tr>
	 </table>
</center>
<br/>
<table class="bordered sortable" width="100%" style="width: 100%;">
<tr>
<th>Plant</th><th>SAP Code No</th><th>Short Name</th><th>Long Name</th><th>U.O.M</th><th>Material Group</th><th>Approximate Value</th><th>Created On</th><th>Requested On</th>
</tr>
<logic:iterate id="abc" name="materialList">
<tr>
<td><bean:write name="abc" property="locationId"/></td>
<td><bean:write name="abc" property="codeNo"/></td>
<td><bean:write name="abc" property="shortName"/></td>
<td><bean:write name="abc" property="longName"/></td>
<td><bean:write name="abc" property="uom"/></td>
<td><bean:write name="abc" property="materialGrup"/></td>
<td><bean:write name="abc" property="approximatePrice"/></td>
<td><bean:write name="abc" property="createdOn"/></td>
<td><bean:write name="abc" property="requestedOn"/></td>
</tr>
</logic:iterate>
</logic:notEmpty>


<logic:notEmpty name="noMaterialList">
<html:button property="method"  value="Continue To Create"  onclick="shownewMaterialForm()" styleClass="rounded" style="width: 140px;"/>&nbsp;&nbsp;
<br/>
<table class="bordered" width="100%" style="width: 100%;">

<tr>
<th>Plant</th><th>SAP Code No</th><th>Short Name</th><th>Long Name</th><th>U.O.M</th><th>Material Group</th><th>Approximate Value</th><th>Created On</th><th>Requested On</th>
</tr>
<tr>
<td colspan="9">
<center><font color="red" size="2"><b>Searched details could not be found.</b></font></center>
</td></tr></table>
</logic:notEmpty>


</html:form>
</body>
</html>	