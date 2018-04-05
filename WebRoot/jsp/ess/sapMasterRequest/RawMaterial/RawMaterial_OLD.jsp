
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<jsp:directive.page import="com.microlabs.utilities.UserInfo" />
<jsp:directive.page import="com.microlabs.login.dao.LoginDao" />
<jsp:directive.page import="java.sql.ResultSet" />
<jsp:directive.page import="java.sql.SQLException" />
<jsp:directive.page import="java.util.ArrayList" />
<jsp:directive.page import="java.util.Iterator" />
<jsp:directive.page import="java.util.LinkedHashMap" />
<jsp:directive.page import="java.util.Set" />
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="com.microlabs.utilities.IdValuePair" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Raw Material </title>
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<style type="text/css">
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}
.style2 {	color: #df1e1c; font: bold 11px "Arial", Verdana, Arial, Helvetica, sans-serif;	font-size: 12px;
}
</style>
<script type="text/javascript"><!--

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


	 function popupCalender(param)
	  {
	      var cal = new Zapatec.Calendar.setup({
	      inputField     :     param,     // id of the input field
	      singleClick    :     true,     // require two clicks to submit
	      ifFormat       :    "%d/%m/%Y ",     // format of the input field
	      showsTime      :     false,     // show time as well as date
	      button         :    "button2"  // trigger button 
	      });
	  }

function getMatrialList(materialList)
{
var materilaType=materialList;

if(materilaType=='RAW MATERIALS')
{
var url="rawMaterial.do?method=displayNewMaterialCodeMaster";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='PACKING MATERIALS')
{
var url="packageMaterial.do?method=displayNewPackageMaterial";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='FINISHED PRODUCTS')
{
var url="finishedProduct.do?method=displayNewFinishedProduct";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='SEMI FINISHED')
{
var url="semifinished.do?method=displayNewSemiFinished";
			document.forms[0].action=url;
			document.forms[0].submit();	
}

if(materilaType=='ZPPC')
{
var url="promotional.do?method=displayNewPromotional";
			document.forms[0].action=url;
			document.forms[0].submit();	
}

if(materilaType=='ZPSR')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType=10";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZLAB')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType=15";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZCIV')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType=13";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZCON')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType=7";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZSCR')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType=16";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZITC')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType=11";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZPFL')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType=12";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
}

function getIsDMFMaterial(){
if(document.forms[0].isDMFMaterial.value=="True"){

document.getElementById("im").style.visibility="visible";
document.getElementById("im1").style.visibility="visible";
document.getElementById("im2").style.visibility="visible";

}
if(document.forms[0].isDMFMaterial.value=="False"){

document.getElementById("im").style.visibility="hidden";
document.getElementById("im1").style.visibility="hidden";
document.getElementById("im2").style.visibility="hidden";

}
if(document.forms[0].isDMFMaterial.value==""){

document.getElementById("im").style.visibility="hidden";
document.getElementById("im1").style.visibility="hidden";
document.getElementById("im2").style.visibility="hidden";

}

}
function closeData(){
var url="materialCode.do?method=displayMaterialList";
			document.forms[0].action=url;
			document.forms[0].submit();
}	
function saveData(){	   
		if(document.forms[0].locationId.value=="")
	    {
	      alert("Please Select Location");
	      document.forms[0].locationId.focus();
	      return false;
	    }
	    if(document.forms[0].storageLocationId.value=="")
	    {
	      alert("Please Select Storage Location ");
	      document.forms[0].storageLocationId.focus();
	      return false;
	    }
	    if(document.forms[0].materialTypeId.value=="")
	    {
	      alert("Please Select Material Type ");
	      document.forms[0].materialTypeId.focus();
	      return false;
	    }
	     if(document.forms[0].materialShortName.value=="")
	    {
	      alert("Please Enter Material Short Name");
	      document.forms[0].materialShortName.focus();
	      return false;
	    }
	    if(document.forms[0].materialLongName.value=="")
	    {
	      alert("Please Enter Material Long Name");
	      document.forms[0].materialLongName.focus();
	      return false;
	    }
	     if(document.forms[0].materialGroupId.value=="")
	    {
	      alert("Please Enter Material Group ");
	      document.forms[0].materialGroupId.focus();
	      return false;
	    }
	    if(document.forms[0].unitOfMeasId.value=="")
	    {
	      alert("Please Select Unit Of Meas ");
	      document.forms[0].unitOfMeasId.focus();
	      return false;
	    }
	    if(document.forms[0].puchaseGroupId.value=="")
	    {
	      alert("Please Select Purchase Group");
	      document.forms[0].puchaseGroupId.focus();
	      return false;
	    }
	   
	    if(document.forms[0].pharmacopName.value=="")
	    {
	      alert("Please Enter Pharmacopoeial Name");
	      document.forms[0].pharmacopName.focus();
	      return false;
	    }
	    if(document.forms[0].pharmacopGrade.value=="")
	    {
	      alert("Please Select Pharmacopoeial Grade");
	      document.forms[0].pharmacopGrade.focus();
	      return false;
	    }
	    if(document.forms[0].genericName.value=="")
	    {
	      alert("Please Enter Generic Name");
	      document.forms[0].genericName.focus();
	      return false;
	    }
	    if(document.forms[0].synonym.value=="")
	    {
	      alert("Please Enter Synonym");
	      document.forms[0].synonym.focus();
	      return false;
	    }
	    if(document.forms[0].pharmacopSpecification.value=="")
	    {
	      alert("Please Enter Pharmacopoeial Specification");
	      document.forms[0].pharmacopSpecification.focus();
	      return false;
	    }
	    if(document.forms[0].isDMFMaterial.value=="")
	    {
	      alert("Please Select IS DMF Material");
	      document.forms[0].isDMFMaterial.focus();
	      return false;
	    }
	    if(document.forms[0].isDMFMaterial.value=="True")
	    {
		    if(document.forms[0].dmfGradeId.value=="")
		    {
		      alert("Please Select DMF Grade");
		      document.forms[0].dmfGradeId.focus();
		      return false;
		    }
		    if(document.forms[0].materialGrade.value=="")
		    {
		      alert("Please Enter Material Grade");
		      document.forms[0].materialGrade.focus();
		      return false;
		    }
		    if(document.forms[0].cosGradeNo.value=="")
		    {
		      alert("Please Enter COS Grade No");
		      document.forms[0].cosGradeNo.focus();
		      return false;
		    }
		   
	    }
	    if(document.forms[0].isVendorSpecificMaterial.value=="")
	    {
	     alert("Please Select Is Vendor Specific Material");
		      document.forms[0].isVendorSpecificMaterial.focus();
		      return false;
	    }
	    if(document.forms[0].isVendorSpecificMaterial.value=="True")
	    {
   		    if(document.forms[0].mfgrName.value=="")
		    {
		      alert("Please Enter Manufacture Name");
		      document.forms[0].mfgrName.focus();
		      return false;
		    }
		    if(document.forms[0].siteOfManufacture.value=="")
		    {
		      alert("Please Enter Site Of Manufacture");
		      document.forms[0].siteOfManufacture.focus();
		      return false;
		    }
	     }
	     if(document.forms[0].countryId.value=="")
	    {
	      alert("Please Select Country");
	      document.forms[0].countryId.focus();
	      return false;
	    }
	     if(document.forms[0].customerName.value=="")
	    {
	      alert("Please Enter Customer Name");
	      document.forms[0].customerName.focus();
	      return false;
	    }
	     if(document.forms[0].toBeUsedInProducts.value=="")
	    {
	      alert("Please Enter To Be Used In Products");
	      document.forms[0].toBeUsedInProducts.focus();
	      return false;
	    }
	    if(document.forms[0].dutyElement.value=="")
	    {
	      alert("Please Select Duty Element");
	      document.forms[0].dutyElement.focus();
	      return false;
	    }
	    if(document.forms[0].shelfLife.value=="")
	    {
	      alert("Please Enter Shelf Life");
	      document.forms[0].shelfLife.focus();
	      return false;
	    }
	    
	     var shelfLife = document.forms[0].shelfLife.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(shelfLife)) {
             alert("Shelf Life  Should be Integer ");
            return false;
        }
        
        if(document.forms[0].shelfLifeType.value=="")
	    {
	      alert("Please Enter Shelf Life Type");
	      document.forms[0].shelfLifeType.focus();
	      return false;
	    }
        
        
	    if(document.forms[0].retestDays.value=="")
	    {
	      alert("Please Enter Retest Days");
	      document.forms[0].retestDays.focus();
	      return false;
	    }
	     var retestDays = document.forms[0].retestDays.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(retestDays)) {
             alert("Retest Days  Should be Integer ");
            return false;
        }
         if(document.forms[0].retestType.value=="")
	    {
	      alert("Please Enter Retest  Type");
	      document.forms[0].retestType.focus();
	      return false;
	    }
	    if(document.forms[0].valuationClass.value=="")
	    {
	      alert("Please Enter Valuation Class");
	      document.forms[0].valuationClass.focus();
	      return false;
	    }
	    if(document.forms[0].approximateValue.value=="")
	    {
	      alert("Please Enter Approximate Value");
	      document.forms[0].approximateValue.focus();
	      return false;
	    }
	    
	       var approximateValue = document.forms[0].approximateValue.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(approximateValue)) {
             alert("Approximate Value Should be Integer or Float!");
            return false;
        }
        
        
         if(document.forms[0].requestedBy.value=="")
	    {
	      alert("Please Enter Requested By");
	      document.forms[0].requestedBy.focus();
	      return false;
	    }
	    
	    /*
	    if(document.forms[0].sapCodeNo.value=="")
	    {
	      alert("Please Select Sap CodeNo");
	      document.forms[0].sapCodeNo.focus();
	      return false;
	    }
	    if(document.forms[0].sapCodeExists.value=="")
	    {
	      alert("Please Enter SAP Code Exists");
	      document.forms[0].sapCodeExists.focus();
	      return false;
	    }
	    if(document.forms[0].sapCreationDate.value=="")
	    {
	      alert("Please Enter SAP Creation Date");
	      document.forms[0].sapCreationDate.focus();
	      return false;
	    }
	    if(document.forms[0].sapCreatedBy.value=="")
	    {
	      alert("Please Enter SAP Created By");
	      document.forms[0].sapCreatedBy.focus();
	      return false;
	    }
	   */
	    
	
			var url="rawMaterial.do?method=saveMaterialCodeMaster";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}

function isVendorStatus()
{
var a=document.forms[0].isVendorSpecificMaterial.value;

if(a=="True"){

document.getElementById("isVendor1").style.visibility="visible";
document.getElementById("isVendor2").style.visibility="visible";

}	
if(a=="False"){

document.getElementById("isVendor1").style.visibility="hidden";
document.getElementById("isVendor2").style.visibility="hidden";

}	
if(a==""){

document.getElementById("isVendor1").style.visibility="hidden";
document.getElementById("isVendor2").style.visibility="hidden";

}

}




function onUpload(){
	   
		
		if(document.forms[0].fileNames.value=="")
	    {
	      alert("Please Select File ");
	      document.forms[0].fileNames.focus();
	      return false;
	    }
		
			var url="rawMaterial.do?method=uploadFiles";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}
function moveMaterialCode(){


var saveType=document.forms[0].typeDetails.value;
if(saveType=="Save")
{
if(document.forms[0].storageLocationId.value!=""||document.forms[0].materialShortName.value!=""||document.forms[0].materialLongName.value!=""||
document.forms[0].materialGroupId.value!=""||document.forms[0].unitOfMeasId.value!=""||document.forms[0].puchaseGroupId.value!=""||document.forms[0].pharmacopName.value!=""||
document.forms[0].pharmacopGrade.value!=""||document.forms[0].genericName.value!=""||document.forms[0].synonym.value!=""||document.forms[0].pharmacopSpecification.value!=""||
document.forms[0].dutyElement.value!=""||document.forms[0].dutyElement.value!=""||document.forms[0].isDMFMaterial.value!=""||document.forms[0].dmfGradeId.value!=""||
document.forms[0].materialGrade.value!=""||document.forms[0].cosGradeNo.value!=""||document.forms[0].additionalTest.value!=""||document.forms[0].isVendorSpecificMaterial.value!=""||
document.forms[0].mfgrName.value!=""||document.forms[0].siteOfManufacture.value!=""||document.forms[0].countryId.value!=""||document.forms[0].customerName.value!=""||document.forms[0].toBeUsedInProducts.value!=""||
document.forms[0].tempCondition.value!=""||document.forms[0].storageCondition.value!=""||document.forms[0].shelfLife.value!=""||document.forms[0].shelfLifeType.value!=""||document.forms[0].retestDays.value!=""||
document.forms[0].retestType.value!=""||document.forms[0].valuationClass.value!=""||document.forms[0].approximateValue.value!=""||document.forms[0].approximateValue.value!=""||document.forms[0].uploadFileStatus.value=="Yes")
{
var agree = confirm('Material code not saved.Are you sure open new material code form');
if(agree)
{

if(document.forms[0].uploadFileStatus.value=="Yes")
alert("Please Delte Uploaded File");
else{

document.getElementById("t1").style.visibility="hidden";
document.getElementById("t1").style.height="0px";
document.getElementById("t2").style.visibility="visible";
document.getElementById("t2").style.height="20px";
document.getElementById("materialTable").style.visibility="hidden";
document.forms[0].materialCodeLists.value="";
}
}
else
{

}
}else{
document.getElementById("t1").style.visibility="hidden";
document.getElementById("t1").style.height="0px";
document.getElementById("t2").style.visibility="visible";
document.getElementById("t2").style.height="20px";
document.getElementById("materialTable").style.visibility="hidden";
document.forms[0].materialCodeLists.value="";
}


	 }
	 if(saveType=="Update")
{

document.getElementById("t1").style.visibility="hidden";
document.getElementById("t1").style.height="0px";
document.getElementById("t2").style.visibility="visible";
document.getElementById("t2").style.height="20px";
document.getElementById("materialTable").style.visibility="hidden";
document.forms[0].materialCodeLists.value="";

}
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
	document.forms[0].action="rawMaterial.do?method=deleteFileListModify&cValues="+checkvalues+"&unValues="+uncheckvalues;
document.forms[0].submit();
}
}
}

--></script>

<style type="text/css">
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}
</style>
</head>

<body onload="getIsDMFMaterial(),isVendorStatus()">

   		<%
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			if(menuIcon==null){
			menuIcon="";
			} 
  			UserInfo user=(UserInfo)session.getAttribute("user");
  
  		%>

    	<div class="middel-blocks">
     		<div align="center">
				<logic:present name="rawMaterialForm" property="message">
					<font color="red"><bean:write name="rawMaterialForm" property="message" /></font>
				</logic:present>
				<logic:present name="rawMaterialForm" property="message2">
					<font color="Green"><bean:write name="rawMaterialForm" property="message2" /></font>
				</logic:present>
			</div>
			<html:form action="/rawMaterial.do" enctype="multipart/form-data">

			<html:button property="method" value="New" onclick="moveMaterialCode()"></html:button></th>
			
	
			<table align="center" border="0" cellpadding="4" cellspacing="0"  id="mytable1" style="width:885px;">
			

	      	<tr>

			<th colspan="4">
				<center><big>Material Code Request Form</big></center></th>
	
			</tr>
	
	
	<th width="264" class="specalt" scope="row">
	Material&nbspType
</th>
	<td>
	<div id="t1" style="visibility: visible;height: 20px;">
	
	<html:select property="materialCodeLists" name="rawMaterialForm"   disabled="true"  style="width:550px;"   onchange="getMatrialList(this.value)" >

	<html:option value="">Select</html:option>
	<html:option value="RAW MATERIALS">RAW MATERIAL</html:option>
	<html:option value="PACKING MATERIALS">PACKING MATERIAL</html:option>
	<html:option value="FINISHED PRODUCTS">FINISHED PRODUCT</html:option>
	<html:option value="SEMI FINISHED">SEMI FINISHED</html:option>
	<html:option value="ZPPC">PROMOTIONAL,PRINTED & COMPLIMENTS&nbsp/&nbspZPPC</html:option>
	<html:option value="ZPSR">ZPSR</html:option>
	<html:option value="ZLAB">ZLAB</html:option>
	<html:option value="ZCIV">ZCIV</html:option>
	<html:option value="ZCON">ZCON</html:option>
	<html:option value="ZSCR">ZSCR</html:option>
	<html:option value="ZITC">ZITC</html:option>
	<html:option value="ZPFL">ZPFL</html:option>
	</html:select>
		</div>
	
	<div id="t2" style="visibility: hidden; height: 0;">
	<html:select property="materialCodeLists1" name="rawMaterialForm"  style="width:550px;"  onchange="getMatrialList(this.value)" >

	<html:option value="">Select</html:option>
	<html:option value="RAW MATERIALS">RAW MATERIAL</html:option>
	<html:option value="PACKING MATERIALS">PACKING MATERIAL</html:option>
	<html:option value="FINISHED PRODUCTS">FINISHED PRODUCT</html:option>
	<html:option value="SEMI FINISHED">SEMI FINISHED</html:option>
	<html:option value="ZPPC">PROMOTIONAL,PRINTED & COMPLIMENTS&nbsp/&nbspZPPC</html:option>
		<html:option value="ZPSR">ZPSR</html:option>
	<html:option value="ZLAB">ZLAB</html:option>
	<html:option value="ZCIV">ZCIV</html:option>
	<html:option value="ZCON">ZCON</html:option>
	<html:option value="ZSCR">ZSCR</html:option>
	<html:option value="ZITC">ZITC</html:option>
	<html:option value="ZPFL">ZPFL</html:option>
	</html:select>
		</div>
	</td></tr>
	</table>
	<div id="materialTable" style="visibility: visible;">
	<table align="center" border="0" cellpadding="4" cellspacing="0"  id="mytable1">

	      
	
								<tr>
	 <th colspan="2">Basic Details Of Material:</th>
   </tr>
						<tr>	  
	 <th width="274" class="specalt" scope="row">Request No<img src="images/star.gif" width="8" height="8" /></th>
	  	
		<td align="left">
		<html:hidden property="typeDetails"/>
		<html:hidden property="uploadFileStatus"/>
			<label><html:text property="requestNo" styleClass="text_field"   readonly="true" maxlength="50"/></label>
		</td>
		</tr>
		<tr>
			<th width="274" class="specalt" scope="row">Request Date<img src="images/star.gif" width="8" height="8" /></th>
			<td align="left">
				<html:text property="requestDate" styleId="requestDate" readonly="true"   styleClass="text_field"/></td>

			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Location<img src="images/star.gif" width="8" height="8" />
				</th>
				<td align="left">
				<html:select name="rawMaterialForm" property="locationId" styleClass="text_field" >
					<html:option value="">--Select--</html:option>
					<html:options name="rawMaterialForm" property="locationIdList" 
									labelProperty="locationLabelList"/>
					</html:select>
				<br /></td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Storage&nbspLocation&nbsp<img src="images/star.gif" width="8" height="8" /></th>
			<td>
			<html:select  property="storageLocationId" styleClass="text_field" style="width:250px;">
					<html:option value="">--Select--</html:option>
					<html:options name="rawMaterialForm" property="storageID" 
									labelProperty="storageIDName"/>
					</html:select>
			</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Material Type <img src="images/star.gif" width="8" height="8" />
				</th>
				<td align="left">
				<html:select name="rawMaterialForm" property="materialTypeId" disabled="true" styleClass="text_field" style="width:100px;">
					<html:option value="">--Select--</html:option>
					<html:options name="rawMaterialForm" property="materTypeIDList" 
									labelProperty="materialTypeIdValueList"/>
					</html:select>
				<br /></td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Material Short Name<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="materialShortName" styleClass="text_field" style="width:400px;"  maxlength="40"></html:text>
			</td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Material Long Name<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="materialLongName" styleClass="text_field" style="width:580px;" maxlength="80"></html:text>
			</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Material Group <img src="images/star.gif" width="8" height="8" />
				</th>
				<td align="left">
				<html:select name="rawMaterialForm" property="materialGroupId" styleClass="text_field">
					<html:option value="">--Select--</html:option>
					<html:options name="rawMaterialForm" property="materGroupIDList" 
									labelProperty="materialGroupIdValueList"/>
									</html:select>
				<br /></td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Unit Of Measurement<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:select property="unitOfMeasId"  styleClass="text_field">
				<html:option value="">-----Select-----</html:option>
				<html:options name="rawMaterialForm" property="unitOfMeasIdList" 
									labelProperty="unitOfMeasIdValues"/>
				</html:select>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Purchasing  Group <img src="images/star.gif" width="8" height="8" /></th>
			
			<td><html:select property="puchaseGroupId"  styleClass="text_field" style="width:200px;">
				<html:option value="">-----Select-----</html:option>
				<html:options name="rawMaterialForm" property="puchaseGroupIdList" 
									labelProperty="puchaseGroupIdValues"/>
			</html:select>
			</tr>
			
			
				<tr>
	 <th colspan="2">Quality Specification:</th>
   </tr>
			
			
			<tr>
				<th width="274" class="specalt" scope="row">Pharmacopoeial&nbspName<img src="images/star.gif" width="8" height="8" /></th>
				
				
				<td><html:text property="pharmacopName" styleClass="text_field"  style="width:580px;" maxlength="80"></html:text>
				</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Pharmacopoeial&nbspGrade<img src="images/star.gif" width="8" height="8" />
				</th>
				<td align="left">
				<html:select name="rawMaterialForm" property="pharmacopGrade" styleClass="text_field" style="width:100px;">
					<html:option value="">--Select--</html:option>
					<html:option value="IH ">IH</html:option>
					<html:option value="IP ">IP</html:option>
					<html:option value="BP ">BP</html:option>
					<html:option value="USP ">USP</html:option>
					<html:option value="EP ">EP</html:option>
					<html:option value="NF ">NF</html:option>
					<html:option value="INT ">INT</html:option>
					
					</html:select>
				</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Generic Name<img src="images/star.gif" width="8" height="8" /></th><td><html:text property="genericName" styleClass="text_field" style="width:580px;" maxlength="80"></html:text>
				</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Synonym<img src="images/star.gif" width="8" height="8" /></th><td><html:text property="synonym" styleClass="text_field" style="width:580px;" maxlength="80"></html:text>
				</td>
			</tr>

			
		
			<tr>
				<th width="274" class="specalt" scope="row">Pharmacopoeial&nbspSpecification<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:text property="pharmacopSpecification" style="width:580px;" styleClass="text_field" maxlength="80"></html:text>
				</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Duty Element<img src="images/star.gif" width="8" height="8" /></th>
				<td>
				<html:select property="dutyElement" styleClass="text_field" >
				<html:option value="">-----Select-----</html:option>
				<html:option value="True">Yes</html:option>
				<html:option value="False">No</html:option>
				</html:select>
				</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Is DMF Material<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:select property="isDMFMaterial" styleClass="text_field" onchange="getIsDMFMaterial()">
				<html:option value="">-----Select-----</html:option>
				<html:option value="True">Yes</html:option>
				<html:option value="False">No</html:option>
				</html:select></td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">DMF Grade 
				<div id="im" style="visibility: hidden"><img src="images/star.gif" width="8" height="8" /></div>
				</th>
				<td align="left">
				<html:select name="rawMaterialForm" property="dmfGradeId" styleClass="text_field">
					<html:option value="">--Select--</html:option>
					<html:options name="rawMaterialForm" property="dmfGradeIDList" 
									labelProperty="dmfGradeIDValueList"/>
									</html:select>
				</td>
			</tr>
			
			<tr>
				<th width="274" class="specalt" scope="row">Material Grade
				<div id="im1" style="visibility: hidden"><img src="images/star.gif" width="8" height="8" /></div></th>
				<td><html:text property="materialGrade" styleClass="text_field" style="width:200px;" maxlength="30"></html:text>
				</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">COS Grade No
				<div id="im2" style="visibility: hidden">
				<img src="images/star.gif" width="8" height="8" /></div>
				</th><td><html:text property="cosGradeNo" styleClass="text_field" style="width:200px;" maxlength="30"></html:text>
				</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Additional Test</th><td><html:text property="additionalTest" styleClass="text_field" style="width:200px;" maxlength="20"></html:text>
				</td>
			</tr>
				<tr>
	 <th colspan="2">Vendor&nbsp/&nbspManufacture Information:</th>
   </tr>
			
			<tr>
				<th width="274" class="specalt" scope="row">Is Vendor Specific Material<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:select property="isVendorSpecificMaterial" styleClass="text_field"  onchange="isVendorStatus()">
				<html:option value="">-----Select-----</html:option>
				<html:option value="True">Yes</html:option>
				<html:option value="False">No</html:option>
				</html:select></td>
			</tr>
			
			<tr>
				<th width="274" class="specalt" scope="row">Manufacture  Name
				<div id="isVendor1" style="visibility: hidden"><img src="images/star.gif" width="8" height="8" /></div>
				</th>
				<td><html:text property="mfgrName" styleClass="text_field" style="width:400px;" maxlength="40"></html:text>
				</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Site Of Manufacture
				<div id="isVendor2" style="visibility: hidden"><img src="images/star.gif" width="8" height="8" /></div>
				</th>
				<td><html:text property="siteOfManufacture" styleClass="text_field"></html:text>
				</td>
			</tr>
			
		
			<tr>
			<tr>
			<th width="274" class="specalt" scope="row">Country<img src="images/star.gif" width="8" height="8" /></th>
			
			<td><html:select property="countryId"  styleClass="text_field">
			<html:option value="">-----Select-----</html:option>
			<html:options property="counID" labelProperty="countryName" />
			</html:select>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Customer Name<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:text property="customerName" styleClass="text_field" style="width:480px;" maxlength="50"></html:text>
				</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">To Be Used In Product (S)<img src="images/star.gif" width="8" height="8" />
				</th><td><html:text property="toBeUsedInProducts" styleClass="text_field" style="width:580px;"  maxlength="80"></html:text>
				</td>
				</tr>
	<th colspan="2">Other Details:</th>
	   </tr>
	   <tr>
				<th width="274" class="specalt" scope="row">Temp.Condition
				</th><td>
				<html:select property="tempCondition"  styleClass="text_field" >
				<html:option value="">-----Select-----</html:option>
			<html:options name="rawMaterialForm" property="tempIDList" 
									labelProperty="temValueList"/>
					
					</html:select>
					</td>
			</tr>
			   <tr>
				<th width="274" class="specalt" scope="row">Storage Condition
				</th><td>
				<html:select property="storageCondition"  styleClass="text_field" >
				<html:option value="">-----Select-----</html:option>
				<html:options name="rawMaterialForm" property="storageIDList" 
									labelProperty="storageLocList"/>
					
					</html:select>
					</td>
			</tr>
			
			<tr>
				<th width="274" class="specalt" scope="row">Shelf Life<img src="images/star.gif" width="8" height="8" />
				</th><td><html:text property="shelfLife" styleClass="text_field" style="width:40px;" maxlength="4"> </html:text>
				<html:select property="shelfLifeType"  styleClass="text_field" style="width:100px;">
				<html:option value="">-----Select-----</html:option>
				<html:option value="days">Days</html:option>
					<html:option value="months">Months</html:option></html:select>
					</td>
			</tr>
			
			<tr>
				<th width="274" class="specalt" scope="row">Retest Days<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:text property="retestDays" styleClass="text_field" style="width:40px;" maxlength="4"></html:text>
				<html:select property="retestType"  styleClass="text_field" style="width:100px;" >
				<html:option value="">-----Select-----</html:option>
				<html:option value="days">Days</html:option>
					<html:option value="months">Months</html:option></html:select>
					</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Valuation Class<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:select property="valuationClass"  styleClass="text_field" style="width:200px;">
				<html:option value="">-----Select-----</html:option>
				<html:options name="rawMaterialForm" property="valuationClassID" 
									labelProperty="valuationClassName"/>
									</html:select>
					</td>
					
			</tr>
			
				
			<tr>
				<th width="274" class="specalt" scope="row">Approximate Value<img src="images/star.gif" width="8" height="8" /></th><td><html:text property="approximateValue" styleClass="text_field"></html:text>
				</td>
			</tr>
				</tr>
				<tr>
				<th width="274" class="specalt" scope="row">Requested By<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:text property="requestedBy"  styleClass="text_field"  style="width:200px;" maxlength="50" readonly="true"></html:text>
				</td>
			</tr>
		<tr>
<th width="274" class="specalt" scope="row">Attachments<img src="images/star.gif" width="8" height="8" /></th>
<td>
<html:file  property="fileNames" styleClass="text_field"/></td>
<tr  >
		<td align="center" colspan="2">
	<html:button value="Upload Documents" onclick="onUpload()" property="method" />
	</td>
</tr>

 
 <logic:notEmpty name="listName">
		<tr>
			<th colspan="2">
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
					String address="C:\\Tomcat 6.0\\webapps\\EMicro\\jsp\\ess\\sapMasterRequest\\Raw Materials Files\\UploadFiles\\"+u;
					
			%>
			<tr>
			<th width="274" class="specalt" scope="row" ><a href="${listid.uploadFilePath }"   ><%=u%></a></th>
			
			<td width="15" colspan="2"><input type="checkbox" name="checkedfiles" 
						value="<%=u%>" /></td> 

			</tr>
			<%
			}
			%>		
			</logic:iterate>
		<tr>
		<td colspan="4" align="center">
			<html:button value="Delete Files" onclick="onDeleteFile()" property="method"  />
		</td>
		</tr>
		</logic:notEmpty>		
<logic:notEmpty name="approved">
		<tr>			
	<th colspan="2">Approver Details:</th>
	   </tr>
	<tr> 
		<th width="274" class="specalt" scope="row">Approve Type
			<td align="left">
			
			<html:select name="rawMaterialForm" property="approveType" styleClass="text_field">
				<html:option value="">--Select--</html:option>
					<html:option value="Pending">Pending</html:option>
					<html:option value="Approved">Approved</html:option>
					<html:option value="Cancel">Cancel</html:option>
			</html:select>
			
			<br /></td></tr>
			
			
</logic:notEmpty>
<logic:notEmpty name="sapApprover">
		<tr>			
	<th colspan="2">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<th width="274" class="specalt" scope="row">SAP Code Exists<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:select property="sapCodeExists" styleClass="text_field" >
					<html:option value="">-----Select-----</html:option>
					<html:option value="True">Yes</html:option>
					<html:option value="False">No</html:option>
				</html:select></td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">SAP Code No<img src="images/star.gif" width="8" height="8" /></th>
				<td>	<html:text property="sapCodeNo" styleClass="text_field" style="width:200px;"></html:text></td>
			
			</tr>
			
			<tr>	
			<th width="274" class="specalt" scope="row">SAP Creation Date<img src="images/star.gif" width="8" height="8" />
	</th>
			<td align="left">
				<html:text property="sapCreationDate" styleId="sapCreationDate" onfocus="popupCalender('sapCreationDate')" readonly="true" styleClass="text_field"/></td>
			<br/></td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">SAP Created By<img src="images/star.gif" width="8" height="8" /></th><td><html:text property="sapCreatedBy" styleClass="text_field"  maxlength="12"></html:text>
				</td>
			</tr>
						
		
</logic:notEmpty>
	
			
			<tr>
		<td colspan="4" align="center">
		<html:button property="method"  value="Save" onclick="saveData()" ></html:button>
		<html:reset value="Reset"></html:reset>	
		<html:button property="method"  value="Close" onclick="closeData()" ></html:button>
		</td>
		</tr>
			
</table>
</html:form>
</div>
</td>
      </tr>
      </table></td></tr>
 
</table>
 </div>  
</body>
</html>
