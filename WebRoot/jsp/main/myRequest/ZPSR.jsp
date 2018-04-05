
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>


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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Plant Spares</title>

<%--<link href="style1/style.css" rel="stylesheet" type="text/css" />--%>
<%--<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />--%>

<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

<%--<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>--%>

<style>
 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}
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

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />


<script type='text/javascript' src="calender/js/zapatec.js"></script>
<script type="text/javascript" src="calender/js/calendar.js"></script>
<script type="text/javascript" src="calender/js/calendar-en.js"></script>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript">

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


if(materilaType=='1')
{
var url="rawMaterial.do?method=displayNewMaterialCodeMaster";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='2')
{
var url="packageMaterial.do?method=displayNewPackageMaterial";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='3')
{
var url="semifinished.do?method=displayNewSemiFinished";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='4'||materilaType=='5')
{
var url="finishedProduct.do?method=displayNewFinishedProduct&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}


if(materilaType=='12')
{
var url="promotional.do?method=displayNewPromotional";
			document.forms[0].action=url;
			document.forms[0].submit();	
}

if(materilaType=='13')
{
var url="zpsr.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='10')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='7')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='8')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='14')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='9')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='11')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}

}
function moveMaterialCode(){


var saveType=document.forms[0].typeDetails.value;
if(saveType=="Save")
{
if(document.forms[0].storageLocationId.value!=""||document.forms[0].materialShortName.value!=""||document.forms[0].materialLongName.value!=""||
document.forms[0].materialGroupId.value!=""||document.forms[0].puchaseGroupId.value!=""||document.forms[0].unitOfMeasId.value!=""||document.forms[0].materialUsedIn.value!=""||
document.forms[0].isEquipment.value!=""||document.forms[0].isSpare.value!=""||document.forms[0].isNewEquipment.value!=""||document.forms[0].equipmentName.value!=""||document.forms[0].prNumber.value!=""||
document.forms[0].poNumber.value!=""||document.forms[0].utilizingDept.value!=""||document.forms[0].approximateValue.value!=""||document.forms[0].valuationClass.value!=""||
document.forms[0].detailedJustification.value!=""||document.forms[0].detailedSpecification.value!=""||document.forms[0].detailedSpecification.value!=""
)
{
var agree = confirm('Material code not saved.Are you sure open new material code form');
if(agree)
{



document.getElementById("t1").style.visibility="hidden";
document.getElementById("t1").style.height="0px";
document.getElementById("t2").style.visibility="visible";
document.getElementById("t2").style.height="20px";
document.getElementById("materialTable").style.visibility="hidden";
document.forms[0].materialCodeLists.value="";

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




function saveData(){	
if(document.forms[0].locationId.value=="")
	    {
	      alert("Please Select Location");
	      document.forms[0].locationId.focus();
	      return false;
	    }
	    
	     if(document.forms[0].materialTypeId.value=="")
	    {
	      alert("Please Select Material Type");
	      document.forms[0].materialTypeId.focus();
	      return false;
	    }
	    
	      if(document.forms[0].materialShortName.value=="")
	    {
	      alert("Please Enter Material Short Name");
	      document.forms[0].materialShortName.focus();
	      return false;
	    }
	    var materialShortName=document.forms[0].materialShortName.value;
         
         var splChars = "'";
for (var i = 0; i < materialShortName.length; i++) {
    if (splChars.indexOf(materialShortName.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Material Short Name!"); 
      document.forms[0].materialShortName.focus();
 return false;
}
}
	      if(document.forms[0].materialLongName.value=="")
	    {
	      alert("Please Enter Material Long Name");
	      document.forms[0].materialLongName.focus();
	      return false;
	    }
	       var materialLongName=document.forms[0].materialLongName.value;
	    for (var i = 0; i < materialLongName.length; i++) {
    if (splChars.indexOf(materialLongName.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Material Long Name!"); 
     document.forms[0].materialLongName.focus();
 return false;
}
}
if(document.forms[0].storageLocationId.value=="")
	    {
	      alert("Please Select Storage Location");
	      document.forms[0].storageLocationId.focus();
	      return false;
	    }
	      if(document.forms[0].materialGroupId.value=="")
	    {
	      alert("Please Select Material Group");
	      document.forms[0].materialGroupId.focus();
	      return false;
	    }
	     
	      if(document.forms[0].unitOfMeasId.value=="")
	    {
	      alert("Please Select Unit of Measurement");
	      document.forms[0].unitOfMeasId.focus();
	      return false;
	    }
	     if(document.forms[0].puchaseGroupId.value=="")
	    {
	      alert("Please Select Purchase Group");
	      document.forms[0].puchaseGroupId.focus();
	      return false;
	    }
	     
	    
	      
	      if(document.forms[0].isEquipment.value=="")
	    {
	      alert("Please Enter Is Equipment");
	      document.forms[0].isEquipment.focus();
	      return false;
	    }
	    
	    if(document.forms[0].isEquipment.value=="True")
	    {
	   
		      if(document.forms[0].equipmentName.value=="")
		    {
		      alert("Please Enter Equipment Name");
		      document.forms[0].equipmentName.focus();
		      return false;
		    }
		     var equipmentName=document.forms[0].equipmentName.value;
	    for (var i = 0; i < equipmentName.length; i++) {
    if (splChars.indexOf(equipmentName.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Equipment Name!"); 
     document.forms[0].equipmentName.focus();
    
 return false;
}
}
		    if(document.forms[0].equipmentMake.value=="")
		    {
		      alert("Please Enter Equipment Make");
		      document.forms[0].equipmentMake.focus();
		      return false;
		    }
		      var equipmentMake=document.forms[0].equipmentMake.value;
	    for (var i = 0; i < equipmentMake.length; i++) {
    if (splChars.indexOf(equipmentMake.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Equipment Make!"); 
    document.forms[0].equipmentMake.focus();
 return false;
}
}
	    }
	    
	      if(document.forms[0].isSpare.value=="")
	    {
	      alert("Please Enter Is Spare");
	      document.forms[0].isSpare.focus();
	      return false;
	    }
	    if(document.forms[0].isSpare.value=="True")
	    {
	
		      if(document.forms[0].componentMake.value=="")
		    {
		      alert("Please Enter Component Make");
		      document.forms[0].componentMake.focus();
		      return false;
		    }
		    var componentMake=document.forms[0].componentMake.value;
	    for (var i = 0; i < componentMake.length; i++) {
    if (splChars.indexOf(componentMake.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Component Make!"); 
        document.forms[0].componentMake.focus();
    
 return false;
}
}
		    if(document.forms[0].oemPartNo.value=="")
		    {
		      alert("Please Enter OEM Part No");
		      document.forms[0].oemPartNo.focus();
		      return false;
		    }
		    if(document.forms[0].oemPartNo.value!=""){
		     var oemPartNo=document.forms[0].oemPartNo.value;
	    for (var i = 0; i < oemPartNo.length; i++) {
    if (splChars.indexOf(oemPartNo.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in OEM Part NO!");
     	      document.forms[0].oemPartNo.focus();
     
 return false;
}
}
	  }
	     if(document.forms[0].isNewEquipment.value=="")
	    {
	      alert("Please Enter Is it a New Equipment / Machine");
	      document.forms[0].isNewEquipment.focus();
	      return false;
	    }
	     if(document.forms[0].isItNewFurniture.value=="")
	    {
	      alert("Please Enter Is it New Furniture /Doors/Window");
	      document.forms[0].isItNewFurniture.focus();
	      return false;
	    }
	     if(document.forms[0].isItFacility.value=="")
	    {
	      alert("Please Enter Is it for New Facility / Expanstion Area");
	      document.forms[0].isItFacility.focus();
	      return false;
	    }
	     if(document.forms[0].isSpareNewEquipment.value=="")
	    {
	      alert("Please Enter Is it for New Facility / Expanstion Area");
	      document.forms[0].isSpareNewEquipment.focus();
	      return false;
	    }
	    
	    
	     
	    
	    if(document.forms[0].utilizingDept.value=="")
	    {
	      alert("Please Enter Utilizing Dept");
	      document.forms[0].utilizingDept.focus();
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
             alert("Approximate Value  Should be Integer or Float!");
            return false;
        }
	      if(document.forms[0].valuationClass.value=="")
	    {
	      alert("Please Enter Valuation Class");
	      document.forms[0].valuationClass.focus();
	      return false;
	    }
	    if(document.forms[0].detailedJustification.value=="")
	    {
	      alert("Please Enter Detailed Justification");
	      document.forms[0].detailedJustification.focus();
	      return false;
	    }
	      var detailedJustification=document.forms[0].detailedJustification.value;
	    for (var i = 0; i < detailedJustification.length; i++) {
    if (splChars.indexOf(detailedJustification.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Detailed Justification!");
     	      document.forms[0].detailedJustification.focus();
     
 return false;
}
}
	    if(document.forms[0].detailedSpecification.value=="")
	    {
	      alert("Please Enter Detailed Specification");
	      document.forms[0].detailedSpecification.focus();
	      return false;
	    }
	    var detailedSpecification=document.forms[0].detailedSpecification.value;
	    for (var i = 0; i < detailedSpecification.length; i++) {
    if (splChars.indexOf(detailedSpecification.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Detailed Specification!"); 
    	      document.forms[0].detailedSpecification.focus();
    
 return false;
}
}
	  /*    if(document.forms[0].sapCodeNo.value=="")
	    {
	      alert("Please Enter SAP Code No");
	      document.forms[0].sapCodeNo.focus();
	      return false;
	    }
	      
	     if(document.forms[0].sapCodeExists.value=="")
	    {
	      alert("Please Select SAP Code Exists");
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
	    if(document.forms[0].requestedBy.value=="")
	    {
	      alert("Please Enter Requested By");
	      document.forms[0].requestedBy.focus();
	      return false;
	    }   
	    */
	    if(document.forms[0].prNumber.value!=""){
	     var prNumber = document.forms[0].prNumber.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(prNumber)) {
             alert("PR Number  Should be Integer or Float!");
                 	      document.forms[0].prNumber.focus();
             
             
            return false;
        }
        }
         if(document.forms[0].poNumber.value!=""){
	     var poNumber = document.forms[0].poNumber.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(poNumber)) {
             alert("PO Number  Should be Integer or Float!");
              document.forms[0].poNumber.focus();
             
            return false;
        }
        }
			var url="zpsr.do?method=saveGeneralMaterial";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}
}

function onUpload(){	   
		
			var url="finishedProduct.do?method=uploadFiles";
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
	document.forms[0].action="finishedProduct.do?method=deleteFileListModify&cValues="+checkvalues+"&unValues="+uncheckvalues;
document.forms[0].submit();
}
}
}

function closeData(){
var url="materialCode.do?method=displayMaterialList";
			document.forms[0].action=url;
			document.forms[0].submit();
}


function checkEquipment()
{
var a=document.forms[0].isEquipment.value;

if(a=="True"){

document.getElementById("equipment1").style.visibility="visible";
document.getElementById("equipment2").style.visibility="visible";

}	
if(a=="False"){

document.getElementById("equipment1").style.visibility="hidden";
document.getElementById("equipment2").style.visibility="hidden";

}	
if(a==""){

document.getElementById("equipment1").style.visibility="hidden";
document.getElementById("equipment2").style.visibility="hidden";

}

}

function checkSpare()
{
var a=document.forms[0].isSpare.value;


if(a=="True"){

document.getElementById("spare1").style.visibility="visible";
document.getElementById("spare2").style.visibility="visible";

}	
if(a=="False"){

document.getElementById("spare1").style.visibility="hidden";
document.getElementById("spare2").style.visibility="hidden";

}	
if(a==""){

document.getElementById("spare1").style.visibility="hidden";
document.getElementById("spare2").style.visibility="hidden";

}


}
</script>

</head>

<body style="text-transform:uppercase;">
   <%
		String menuIcon=(String)request.getAttribute("MenuIcon");
			if(menuIcon==null){
			menuIcon="";
			}
	%>
			
	<% 
		UserInfo user=(UserInfo)session.getAttribute("user");
	%>

		<div class="middel-blocks">

	<html:form action="/zpsr.do" enctype="multipart/form-data">
		 
            <logic:iterate id="zpsrForm" name="plandetails">
				
			

			<div id="materialTable" style="visibility: visible;">
			<table class="bordered">
			<tr>
					<th colspan="8" style="text-align: center;"><big>Material Code Request Form</big></th>
				</tr>
				<tr>
					<th colspan="8"><big>Basic Details Of Material</big></th>
   				</tr>
			<tr>

				<tr>
	 				<td>Request No <font color="red">*</font></td>
					<td>
					<bean:write name="zpsrForm" property="requestNo"/>

					</td>
					<td>Request&nbsp;Date <font color="red">*</font></td>
					<td>
					<bean:write name="zpsrForm" property="requestDate"/>
					</td>
				</tr>

				<tr>
					<td>Location <font color="red">*</font></td>
					<td colspan="3">
														<bean:write name="zpsrForm" property="locationId"/>

					</td>
				</tr>

					
				<tr>
					<td>Short Name <font color="red">*</font></td>
					<td colspan="3">
									<bean:write name="zpsrForm" property="materialShortName"/>
					</td>
				</tr>

				<tr>
					<td>Long Name <font color="red">*</font></td>
					<td colspan="3">
									<bean:write name="zpsrForm" property="materialLongName"/>
					</td>
				</tr>

				<tr>
					<td>Material Group <font color="red">*</font></td>
					<td>								
						<bean:write name="zpsrForm" property="materialGroupId"/>

					</td>
					<td>Storage&nbsp;Location <font color="red">*</font></td>
					<td>									
					<bean:write name="zpsrForm" property="storageLocationId"/>

					</td>
				</tr>

				<tr>
					<td>U O M <font color="red">*</font></td>
					<td>
														<bean:write name="zpsrForm" property="unitOfMeasId"/>

					</td>
					<td>Purchase&nbsp;Group <font color="red">*</font></td>
					<td>
														<bean:write name="zpsrForm" property="puchaseGroupId"/>

					</td>
				</tr>

				<tr>
					<th colspan="8"><big>Other Details</big></th>
   				</tr>
			
		
			
				
				<tr>
					<td>Is it a New Equipment /  Machine <font color="red">*</font></td>
					<td >							
							<bean:write name="zpsrForm" property="isEquipment"/>

					</td>
					<td>Equip.intended for</td>
					<td><bean:write name="zpsrForm" property="equipIntendedFor"/></td>
				</tr>

				<tr>
					<td>Equipment&nbsp;Name <div id="equipment1" style="visibility: hidden"><font color="red">*</font></div></td>
					<td colspan="3">
									<bean:write name="zpsrForm" property="equipmentName"/>
					</td>
				</tr>

				<tr>
					<td>Equipment&nbsp;Make <div id="equipment2" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
									<bean:write name="zpsrForm" property="equipmentMake"/>
					</td>
					<td>Is Spare <font color="red">*</font></td>
					<td>						
								<bean:write name="zpsrForm" property="isSpare"/>

					</td>
				</tr>

				<tr>
					<td>Component&nbsp;Make <div id="spare1" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
									<bean:write name="zpsrForm" property="componentMake"/>
					</td>
					<td>OEM Part No.<div id="spare2" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
									<bean:write name="zpsrForm" property="oemPartNo"/>
					</td>
				</tr>
				
				<tr>
				<td>Add Size/Dimensions(Dia,Length x width) </td>
				<td><bean:write name="zpsrForm" property="dimensions"/></td>
				<td>Pack Size </td>
				<td><bean:write name="zpsrForm" property="packSize"/></td>
				</tr>
				<tr>
					<td>MOC <div id="spare1" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
									<bean:write name="zpsrForm" property="moc"/>
					</td>
					<td>Rating<div id="spare2" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
									<bean:write name="zpsrForm" property="rating"/>
					</td>
				</tr>

				<tr>
					
					<td>Range </td>
					<td>								
						<bean:write name="zpsrForm" property="range"/>

					</td>
					<td>Is it New Furniture / Doors / Windows <font color="red">*</font></td>
					<td>								
						<bean:write name="zpsrForm" property="isItNewFurniture"/>

					</td>
				</tr>

				<tr>
					<td>Is it for New Facility / Expansion Area <font color="red">*</font></td>
					<td>								
						<bean:write name="zpsrForm" property="isItFacility"/>
                </td>
					<td>Is Spare required for New Equipment <font color="red">*</font></td>
					<td>									
					<bean:write name="zpsrForm" property="isSpareNewEquipment"/>

					</td>
				</tr>

				<tr>
					<td>PR Number</td>
					<td>
									<bean:write name="zpsrForm" property="prNumber"/>
					</td>
					<td>PO Number</td>
					<td>
									<bean:write name="zpsrForm" property="poNumber"/>
					</td>
				</tr>

				<tr>
					<td>Utilizing Dept. <font color="red">*</font></td>
					<td>								
						<bean:write name="zpsrForm" property="utilizingDept"/>

					</td>

			
				

				<td>Approximate Value <font color="red">*</font></td>
				<td>
									<bean:write name="zpsrForm" property="approximateValue"/>
			</tr>

			<tr>
				<td>Valuation Class <font color="red">*</font></td>
				<td colspan="3"><bean:write name="zpsrForm" property="valuationClass"/></td>
				
			</tr>

			<tr>
				<td>Justification <font color="red">*</font></td>
				<td>
									<bean:write name="zpsrForm" property="detailedJustification"/>
				</td>
				<td>Specification <font color="red">*</font></td>
				<td>
									<bean:write name="zpsrForm" property="detailedSpecification"/>
				</td>
			</tr>

			<tr>			
	<th colspan="6">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<img src="images/star.gif" width="8" height="8" /></td>
				<td>
				<bean:write name="zpsrForm" property="sapCodeExists"/>
				</td>
		
			<td >SAP Code No<img src="images/star.gif" width="8" height="8" /></td>
				<td colspan="4"><bean:write name="zpsrForm" property="sapCodeNo"/>	</td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<bean:write name="zpsrForm" property="sapCreationDate"/>
				
			</td>
			
				<td >Code Created By<img src="images/star.gif" width="8" height="8" /></td><td colspan="4">
				<bean:write name="zpsrForm" property="sapCreatedBy"/>
			
				</td>
			</tr>	     
	               
	               <tr>
						<th colspan="8">Comments</th>
					</tr>	
					
					<tr>
					<td colspan="8">
					
					</td>
					</tr>	
					
					<tr>
						<td colspan="8">
							<html:button property="method" value="Close" onclick="history.back(-1)" styleClass="rounded" style="width: 100px" ></html:button>
						</td>
					</tr>
				</table> 
		</div>
		</div>
		</logic:iterate>
		
		<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeeName }</td><td>${abc.approveStatus }</td><td>${abc.date }</td><td>${abc.comments }</td></tr>
	</tr>
	</logic:iterate>
</table>
	</logic:notEmpty>
	
	<br/>
	&nbsp;
	<br/>
	&nbsp;	
</html:form>

</div>
</body>
</html>
