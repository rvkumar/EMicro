<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
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

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Raw Material </title>

	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

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
if(materilaType=='14')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
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

<%--	    if(document.forms[0].materialTypeId.value=="")--%>
<%--	    {--%>
<%--	      alert("Please Select Material Type ");--%>
<%--	      document.forms[0].materialTypeId.focus();--%>
<%--	      return false;--%>
<%--	    }--%>

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
	    if(document.forms[0].materialLongName.value!="")
	    {
	     var a=document.forms[0].materialLongName.value;
	     a=a.length;
	     if(a>80){
	      alert("Material Long Name Should be less than 80 characters");
	      document.forms[0].materialLongName.focus();
	      return false;
	    }
	    
	    }
	    var materialLongName=document.forms[0].materialLongName.value;
	    for (var i = 0; i < materialLongName.length; i++) {
    if (splChars.indexOf(materialLongName.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Material Long Name!"); 
     document.forms[0].materialLongName.focus();
 return false;
}
}
	     if(document.forms[0].materialGroupId.value=="")
	    {
	      alert("Please Enter Material Group ");
	      document.forms[0].materialGroupId.focus();
	      
	      return false;
	    }
	    if(document.forms[0].unitOfMeasId.value=="")
	    {
	      alert("Please Select U.O.M ");
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
	      var pharmacopName=document.forms[0].pharmacopName.value;
	    for (var i = 0; i < pharmacopName.length; i++) {
    if (splChars.indexOf(pharmacopName.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Pharmacopoeial Name!"); 
       document.forms[0].pharmacopName.focus();
 return false;
}
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
	        var genericName=document.forms[0].genericName.value;
	    for (var i = 0; i < genericName.length; i++) {
    if (splChars.indexOf(genericName.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Generic Name!"); 
         document.forms[0].genericName.focus();
 return false;
}
}
	    if(document.forms[0].synonym.value=="")
	    {
	      alert("Please Enter Synonym");
	      document.forms[0].synonym.focus();
	      return false;
	    }
	        var synonym=document.forms[0].synonym.value;
	    for (var i = 0; i < synonym.length; i++) {
    if (splChars.indexOf(synonym.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Synonym !"); 
    document.forms[0].synonym.focus();
 return false;
}
}
	    if(document.forms[0].pharmacopSpecification.value=="")
	    {
	      alert("Please Enter Pharmacopoeial Specification");
	      document.forms[0].pharmacopSpecification.focus();
	      return false;
	    }
	    
	      var pharmacopSpecification=document.forms[0].pharmacopSpecification.value;
	    for (var i = 0; i < pharmacopSpecification.length; i++) {
    if (splChars.indexOf(pharmacopSpecification.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Pharmacopoeial Specification !"); 
          document.forms[0].pharmacopSpecification.focus();
 return false;
}
}
 if(document.forms[0].dutyElement.value=="")
	    {
	      alert("Please Select Duty Element");
	      document.forms[0].dutyElement.focus();
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
		    if(document.forms[0].dmfGradeId.value=="0")
		    {
		      alert("Please Check The Value Of DMF Grade");
		      document.forms[0].dmfGradeId.focus();
		      return false;
		    }
		    if(document.forms[0].materialGrade.value=="")
		    {
		      alert("Please Enter Material Grade");
		      document.forms[0].materialGrade.focus();
		      return false;
		    }
		      var materialGrade=document.forms[0].materialGrade.value;
	    for (var i = 0; i < materialGrade.length; i++) {
		    if (splChars.indexOf(materialGrade.charAt(i)) != -1){
		    alert ("Please Remove Single Code(') in  Material Grade  !"); 
		          document.forms[0].materialGrade.focus();
		 return false;
		}
		}
		    if(document.forms[0].cosGradeNo.value=="")
		    {
		      alert("Please Enter COS Grade No");
		      document.forms[0].cosGradeNo.focus();
		      return false;
		    }
		     var cosGradeNo=document.forms[0].cosGradeNo.value;
	    for (var i = 0; i < cosGradeNo.length; i++) {
		    if (splChars.indexOf(cosGradeNo.charAt(i)) != -1){
		    alert ("Please Remove Single Code(') in  COS Grade No  !"); 
		          document.forms[0].cosGradeNo.focus();
		 return false;
		}
		}
		
		  if(document.forms[0].additionalTest.value!="")
		    {
		     var additionalTest=document.forms[0].additionalTest.value;
	    for (var i = 0; i < additionalTest.length; i++) {
		    if (splChars.indexOf(additionalTest.charAt(i)) != -1){
		    alert ("Please Remove Single Code(') in  Additional Test  !"); 
		          document.forms[0].additionalTest.focus();
		 return false;
		}
		}
		    }
		   
	    }
	    if(document.forms[0].isDMFMaterial.value=="False")
	    {
		    if(document.forms[0].dmfGradeId.value!="0")
		    {
		      alert("Please Check DMF Grade");
		      document.forms[0].dmfGradeId.focus();
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
		      var mfgrName=document.forms[0].mfgrName.value;
	    for (var i = 0; i < mfgrName.length; i++) {
		    if (splChars.indexOf(mfgrName.charAt(i)) != -1){
		    alert ("Please Remove Single Code(') in  Manufacture Name !"); 
		      document.forms[0].mfgrName.focus();
		 return false;
		}
		}
		    if(document.forms[0].siteOfManufacture.value=="")
		    {
		      alert("Please Enter Site Of Manufacture");
		      document.forms[0].siteOfManufacture.focus();
		      return false;
		    }
		     var siteOfManufacture=document.forms[0].siteOfManufacture.value;
	    for (var i = 0; i < siteOfManufacture.length; i++) {
		    if (splChars.indexOf(siteOfManufacture.charAt(i)) != -1){
		    alert ("Please Remove Single Code(') in  Site Of Manufacture !"); 
		     document.forms[0].siteOfManufacture.focus();
		 return false;
		}
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
	     var customerName=document.forms[0].customerName.value;
	    for (var i = 0; i < customerName.length; i++) {
		    if (splChars.indexOf(customerName.charAt(i)) != -1){
		    alert ("Please Remove Single Code(') in  Customer Name !"); 
		     document.forms[0].customerName.focus();
		 return false;
		}
		}
	     if(document.forms[0].toBeUsedInProducts.value=="")
	    {
	      alert("Please Enter To Be Used In Products");
	      document.forms[0].toBeUsedInProducts.focus();
	      return false;
	    }
	      var toBeUsedInProducts=document.forms[0].toBeUsedInProducts.value;
	    for (var i = 0; i < toBeUsedInProducts.length; i++) {
		    if (splChars.indexOf(toBeUsedInProducts.charAt(i)) != -1){
		    alert ("Please Remove Single Code(') in  To Be Used In Products !"); 
		      document.forms[0].toBeUsedInProducts.focus();
		 return false;
		}
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
                document.forms[0].shelfLife.focus();
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
                document.forms[0].retestDays.focus();
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
                 document.forms[0].approximateValue.focus();
            return false;
        }
        
          /*
         if(document.forms[0].requestedBy.value=="")
	    {
	      alert("Please Enter Requested By");
	      document.forms[0].requestedBy.focus();
	      return false;
	    }
	    
	  
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
if(document.forms[0].materialShortName.value!=""||document.forms[0].materialLongName.value!=""||
document.forms[0].materialGroupId.value!=""||document.forms[0].unitOfMeasId.value!=""||document.forms[0].puchaseGroupId.value!=""||document.forms[0].pharmacopName.value!=""||
document.forms[0].pharmacopGrade.value!=""||document.forms[0].genericName.value!=""||document.forms[0].synonym.value!=""||document.forms[0].pharmacopSpecification.value!=""||
document.forms[0].dutyElement.value!=""||document.forms[0].dutyElement.value!=""||document.forms[0].isDMFMaterial.value!=""||document.forms[0].dmfGradeId.value!=""||
document.forms[0].materialGrade.value!=""||document.forms[0].cosGradeNo.value!=""||document.forms[0].additionalTest.value!=""||document.forms[0].isVendorSpecificMaterial.value!=""||
document.forms[0].mfgrName.value!=""||document.forms[0].siteOfManufacture.value!=""||document.forms[0].countryId.value!=""||document.forms[0].customerName.value!=""||document.forms[0].toBeUsedInProducts.value!=""||
document.forms[0].tempCondition.value!=""||document.forms[0].storageCondition.value!=""||document.forms[0].shelfLife.value!=""||document.forms[0].shelfLifeType.value!="months"||document.forms[0].retestDays.value!=""||
document.forms[0].retestType.value!="days"||document.forms[0].approximateValue.value!=""||document.forms[0].approximateValue.value!=""||document.forms[0].uploadFileStatus.value=="Yes")
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

</head>

<body onload="getIsDMFMaterial(),isVendorStatus()" style="text-transform:uppercase;">

   		<%
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			if(menuIcon==null){
			menuIcon="";
			} 
  			UserInfo user=(UserInfo)session.getAttribute("user");
  
  		%>

    	<div class="middel-blocks">
     		
			<html:form action="/rawMaterial.do" enctype="multipart/form-data">
            
            <logic:iterate id="rawMaterialForm" name="rawdetails">
				
			

			<div id="materialTable" style="visibility: visible;">
			<table class="bordered">
			<tr>
					<th colspan="8" style="text-align: center;"><big>Material Code Request Form</big></th>
				</tr>
				<tr>
					<th colspan="8"><big>Basic Details Of Material</big></th>
   				</tr>
				<tr>	  
	 				<td>Request No <font color="red">*</font></td>
					<td colspan="2"align="left">
												<bean:write name="rawMaterialForm" property="requestNo"/>
						
					</td>
					<td>Request Date <font color="red">*</font></td>
					<td colspan="6" align="left">
						<bean:write name="rawMaterialForm" property="requestDate"/>
					</td>
				</tr>
				<tr>
					<td>Location <font color="red">*</font></td>
					<td colspan="2" align="left">
					<bean:write name="rawMaterialForm" property="locationId"/>
					</td>
					<td>Storage Location <font color="red">*</font></td>
					<td colspan="6">
					<bean:write name="rawMaterialForm" property="storageLocationId"/>
					</td>

<%--				<th width="274" class="specalt" scope="row">Material Type <img src="images/star.gif" width="8" height="8" />--%>
<%--				</th>--%>
<%--				<td align="left">--%>
<%--				<html:select name="rawMaterialForm" property="materialTypeId" disabled="true" styleClass="text_field" style="width:100px;">--%>
<%--					<html:option value="">--Select--</html:option>--%>
<%--					<html:options name="rawMaterialForm" property="materTypeIDList" --%>
<%--									labelProperty="materialTypeIdValueList"/>--%>
<%--					</html:select>--%>
<%--				<br /></td>--%>
                 
				</tr>
				<html:hidden property="materialTypeId"/>
				<tr>
					<td>Short Name <font color="red">*</font></td>
					<td colspan="7">
					<bean:write name="rawMaterialForm" property="materialShortName"/>
					</td>
				</tr>
				<tr>
					<td>Long Name <font color="red">*</font></td>
					<td colspan="7">
					<bean:write name="rawMaterialForm" property="materialLongName"/>
					</td>
				</tr>
				<tr>
					<td>Mat.Group <font color="red">*</font></td>
					<td colspan="2" align="left">
		<bean:write name="rawMaterialForm" property="materialGroupId"/>
						
						
				</td>
					<td>U O M <font color="red">*</font></td>
					<td colspan="4" >
					<bean:write name="rawMaterialForm" property="unitOfMeasId"/>
						
					</td>
				</tr>
				<tr>
					<td>Purchasing Group <font color="red">*</font></td>
					<td colspan="7">
					<bean:write name="rawMaterialForm" property="puchaseGroupId"/>
						
				</td>
				</tr>
				<tr>
	 				<th colspan="8"><big>Quality Specification</big></th>
   				</tr>
				<tr>
					<td>Pharmacopoeial Name <font color="red">*</font></td>
					<td colspan="7">
						<bean:write name="rawMaterialForm" property="pharmacopName"/>
					
					</td>
				</tr>
				<tr>
					<td>Pharmacopoeial Grade <font color="red">*</font></td>
					<td align="left" colspan="7">
						<bean:write name="rawMaterialForm" property="pharmacopGrade"/>
						</td>
			</tr>
			<tr>
				<td>Generic Name <font color="red">*</font></td>
				<td colspan="7">
										<bean:write name="rawMaterialForm" property="genericName"/>
				
				</td>
			</tr>
			<tr>
				<td>Synonym <font color="red">*</font></td>
				<td colspan="7">
										<bean:write name="rawMaterialForm" property="synonym"/>
				</td>
			</tr>
			<tr>
				<td>Pharmacopoeial&nbsp;Specification<font color="red">*</font></td>
				<td colspan="7">
										<bean:write name="rawMaterialForm" property="pharmacopSpecification"/>
				</td>
			</tr>
			<tr>
				<td>Duty Element <font color="red">*</font></td>
				<td colspan="3">
															<bean:write name="rawMaterialForm" property="dutyElement"/>
					
				</td>
				<td>Is DMF Material <font color="red">*</font></td>
				<td colspan="6">
					<bean:write name="rawMaterialForm" property="isDMFMaterial"/>
					
				</td>
			</tr>
			<tr>
				<td>DMF Grade  
					<div id="im" style="visibility: hidden"> <font color="red">*</font></div>
				</td>
				<td colspan="3" align="left">
										<bean:write name="rawMaterialForm" property="dmfGradeId"/>

				</td>
				<td>Material Grade
					<div id="im1" style="visibility: hidden"> <font color="red">*</font></div>
				</td>
				<td colspan="5" >
										<bean:write name="rawMaterialForm" property="materialGrade"/>
				</td>
			</tr>
			<tr>
				<td>COS Grade No
					<div id="im2" style="visibility: hidden"> <font color="red">*</font></div>
				</td >
				<td colspan="3">
										<bean:write name="rawMaterialForm" property="cosGradeNo"/>
				</td>
				<td>Additional Test</td>
				<td colspan="5">
										<bean:write name="rawMaterialForm" property="additionalTest"/>
				</td>
			</tr>
			<tr>
	 			<th colspan="8"><big>Vendor / Manufacture Information</big></th>
   			</tr>
			<tr>
				<td>Is&nbsp;Material&nbsp;<br/>is Supplier/Manufacture/Site Specific <font color="red">*</font></td>
				<td colspan="3">
								<bean:write name="rawMaterialForm" property="isVendorSpecificMaterial"/>

				</td>
				<td>Manufacture Name
					<div id="isVendor1" style="visibility: hidden"> <font color="red">*</font></div>
				</td colspan="4">
				<td>
											<bean:write name="rawMaterialForm" property="mfgrName"/>
				</td>
			</tr>
			<tr>
				<td>Site Of Manufacture
					<div id="isVendor2" style="visibility: hidden"> <font color="red">*</font></div>
				</td>
				<td colspan="3">
									<bean:write name="rawMaterialForm" property="siteOfManufacture"/>
				</td>
				
				
				 <td>Country <font color="red">*</font></td>
				<td colspan="3">
										<bean:write name="rawMaterialForm" property="countryId"/>
					
				</td>
			</tr>
			<tr>
				<td>Customer Name <font color="red">*</font></td>
				<td colspan="7">
										<bean:write name="rawMaterialForm" property="customerName"/>
				</td>
			</tr>
			<tr>
				<td>To Be Used In Product (S) <font color="red">*</font></td>
				<td colspan="7">
					<bean:write name="rawMaterialForm" property="toBeUsedInProducts"/>
				</td>
			</tr>
			<tr>
				<th colspan="8"><big>Other Details</big></th>
	   		</tr>
	   		<tr>
				<td>Temp.Condition</td>
				<td colspan="7">
										<bean:write name="rawMaterialForm" property="tempCondition"/>

				</td>
				</tr>
				<tr>
				<td>Storage&nbsp;Condition</td>
				<td colspan="7">
								<bean:write name="rawMaterialForm" property="storageCondition"/>

				</td>
			</tr>
			<tr>
				<td>Shelf Life <font color="red">*</font></td>
				<td colspan="3">
									<bean:write name="rawMaterialForm" property="shelfLife"/>

					
					
				
			
				
									<bean:write name="rawMaterialForm" property="shelfLifeType"/>

					
					
				</td>
				<td>Retest Days <font color="red">*</font></td>
				<td colspan="2">
														<bean:write name="rawMaterialForm" property="retestDays"/>

				
									<bean:write name="rawMaterialForm" property="retestType"/>

					
					
				</td>
			</tr>
			<tr>
				<td>Valuation Class <font color="red">*</font></td>
				<td colspan="3">
								<bean:write name="rawMaterialForm" property="valuationClass"/>

				</td>
				<td>Approximate&nbsp;Value <font color="red">*</font></td>
				<td>
								<bean:write name="rawMaterialForm" property="approximateValue"/>
				</td>
			</tr>
			
				
	
				<logic:notEmpty name="listName">
					<tr>
						<th colspan="8"><big>Uploaded Documents</big></th>
					</tr>
				<logic:iterate name="listName" id="listid">
					<bean:define id="file" name="listid" property="fileList" />
					<%
						String s = file.toString();
						String v[] = s.split(",");
						int l = v.length;
						for (int i = 0; i < l; i++) 
						{
						int x=v[i].lastIndexOf("/");
							String u=v[i].substring(x+1);
							
							
					%>
					<tr>
						<td  colspan="6"><a href="/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles/<%=u%>"   target="_blank"><%=u%></a></td>
											</tr>
					<%
					}
					%>		
				</logic:iterate>
					
				</logic:notEmpty>		

			
	        <tr>			
	<th colspan="6">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<img src="images/star.gif" width="8" height="8" /></td>
				<td>
				<bean:write name="rawMaterialForm" property="sapCodeExists"/>
				</td>
		
			<td >SAP Code No<img src="images/star.gif" width="8" height="8" /></td>
				<td colspan="4"><bean:write name="rawMaterialForm" property="sapCodeNo"/>	</td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<bean:write name="rawMaterialForm" property="sapCreationDate"/>
				
			</td>
			
				<td >Code Created By<img src="images/star.gif" width="8" height="8" /></td><td colspan="4">
				<bean:write name="rawMaterialForm" property="sapCreatedBy"/>
			
				</td>
			</tr>
						
			
					<tr>
						<td colspan="6">
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
