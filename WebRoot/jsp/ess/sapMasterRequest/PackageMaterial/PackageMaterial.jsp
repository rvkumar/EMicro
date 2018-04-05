
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
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
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
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

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>eMicro :: Package Material</title>

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
function closeData(){
var url="materialCode.do?method=displayMaterialList";
			document.forms[0].action=url;
			document.forms[0].submit();
}


function getMaterialStatus()
{
var url="packageMaterial.do?method=getMaterialStatus";
			document.forms[0].action=url;
			document.forms[0].submit();
}


function isVendorStatus(status)
{
var a=status;

var url="packageMaterial.do?method=getVendorStatus&status="+a;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
function isDMFStatus(status)
{
var a=status;

var url="packageMaterial.do?method=getDMFStatus&status="+a;
			document.forms[0].action=url;
			document.forms[0].submit();	
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




function saveData(param){	
if(document.forms[0].hsnCode.value=="")
	    {
	      alert("Please Enter HSN code");
	      document.forms[0].hsnCode.focus();
	      return false;
	    }
	     var hsnCode = document.forms[0].hsnCode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(hsnCode)) {
             alert("HSNcode its should be Integer ");
                document.forms[0].hsnCode.focus();
            return false;
        }

if(document.forms[0].reqEmail.value=="")
	    {
	      alert("Please Enter Email ID ");
	      document.forms[0].reqEmail.focus();
	      return false;
	    }
	    
	    var str=document.forms[0].reqEmail.value;
	    if(document.forms[0].reqEmail.value!="")
	    {
	    var afterACT = str.substr(str.indexOf("@") + 1);
	    if(afterACT!="microlabs.in")
	    {
	     alert("Please Enter Valid Email ID");
	      document.forms[0].reqEmail.focus();
	      return false;
	    
	    }
	   
	    }
   
		if(document.forms[0].locationId.value=="")
	    {
	      alert("Please Select Location");
	      document.forms[0].locationId.focus();
	      return false;
	    }
	    if(document.forms[0].storageLocationId.value=="")
	    {
	      alert("Please Select Storage Location");
	      document.forms[0].storageLocationId.focus();
	      return false;
	    }

<%--	    if(document.forms[0].materialTypeId.value=="")--%>
<%--	    {--%>
<%--	      alert("Please Select Material Type");--%>
<%--	      document.forms[0].materialTypeId.focus();--%>
<%--	      return false;--%>
<%--	    }--%>

		if(document.forms[0].materialShortName.value=="")
	    {
	      alert("Please Enter Material Short Name");
	      document.forms[0].materialShortName.focus();
	      return false;
	    }
     	     var st = document.forms[0].materialShortName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].materialShortName.value=st;
	
	
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
	    
	    
	         var st = document.forms[0].materialLongName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].materialLongName.value=st;
	    
	    }

	    if(document.forms[0].materialGroupId.value=="")
	    {
	      alert("Please Select Material Group");
	      document.forms[0].materialGroupId.focus();
	      return false;
	    }
	     if(document.forms[0].unitOfMeasId.value=="")
	    {
	      alert("Please Enter U O M");
	      document.forms[0].unitOfMeasId.focus();
	      return false;
	    }
	     
	    if(document.forms[0].puchaseGroupId.value=="")
	    {
	      alert("Please Enter Purchase Group");
	      document.forms[0].puchaseGroupId.focus();
	      return false;
	    }
	    
	    if(document.forms[0].dutyElement.value=="")
	    {
	      alert("Please Select Duty Element");
	      document.forms[0].dutyElement.focus();
	      return false;
	    }
	     if(document.forms[0].packageMaterialGroup.value=="")
	    {
	      alert("Please Select Package Material Group");
	      document.forms[0].packageMaterialGroup.focus();
	      return false;
	    }
	    if(document.forms[0].typeOfMaterial.value=="")
	    {
	      alert("Please Select Type Of Material ");
	      document.forms[0].typeOfMaterial.focus();
	      return false;
	    } 
	     	    	
	     if(document.forms[0].typeOfMaterial.value=="Printed Material")
	    {
	    
	    	 if(document.forms[0].artworkNo.value=="")
		    {
		      alert("Please Enter ARTWORK NO");
		      document.forms[0].artworkNo.focus();
		      return false;
		    }
		           var st = document.forms[0].artworkNo.value;
	            var Re = new RegExp("\\'","g");
	        st = st.replace(Re,"`");
	           document.forms[0].artworkNo.value=st;
	    
		      
		     if(document.forms[0].isArtworkRevision.value=="")
		    {
		      alert("Please Select IS ARTWORK REVISION");
		      document.forms[0].isArtworkRevision.focus();
		      return false;
		    }
		    
		    
	   }
	   
	   
	   if(document.forms[0].isArtworkRevision.value=="1")
		    {
	    if(document.forms[0].existingSAPItemCode.value=="")
		    {
		      alert("Please Enter Existing SAP Item Code");
		      document.forms[0].existingSAPItemCode.focus();
		      return false;
		    }
		         var st = document.forms[0].existingSAPItemCode.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].existingSAPItemCode.value=st;
	    
	   }
	   
	    if(document.forms[0].isDMFMaterial.value=="")
	    {
	      alert("Please Select Is DMF Material");
	      document.forms[0].isDMFMaterial.focus();
	      return false;
	    }
	               var st = document.forms[0].cosGradeNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].cosGradeNo.value=st;
	    
	      if(document.forms[0].isDMFMaterial.value=="1")
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
		    if(document.forms[0].cosGradeNo.value=="")
		    {
		      alert("Please Enter COS Grade And No");
		      document.forms[0].cosGradeNo.focus();
		      return false;
		    }
		            var st = document.forms[0].cosGradeNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].cosGradeNo.value=st;
	    }
	    if(document.forms[0].additionalTest.value!="")
		    {
		    		            var st = document.forms[0].additionalTest.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].additionalTest.value=st;
		    }
		        if(document.forms[0].isDMFMaterial.value=="0")
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
	    
	        var st = document.forms[0].mfgrName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].mfgrName.value=st;
	
	
	    if(document.forms[0].isVendorSpecificMaterial.value=="1")
	    {
		     if(document.forms[0].mfgrName.value=="")
		    {
		      alert("Please Enter Manufacture Name");
		      document.forms[0].mfgrName.focus();
		      return false;
		    }
    var st = document.forms[0].mfgrName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].mfgrName.value=st;
	
		 var st = document.forms[0].siteOfManufacture.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].siteOfManufacture.value=st;
	
	
		     if(document.forms[0].siteOfManufacture.value=="")
		    {
		      alert("Please Enter Site Of Manufacture");
		      document.forms[0].siteOfManufacture.focus();
		      return false;
		    }
		        var st = document.forms[0].siteOfManufacture.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].siteOfManufacture.value=st;
	
	    }
	    if(document.forms[0].countryId.value=="")
	    {
	      alert("Please Select Country");
	      document.forms[0].countryId.focus();
	      return false;
	    }
	    if(document.forms[0].customerName.value!="")
	    {
	      var st = document.forms[0].customerName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].customerName.value=st;
	    }
	      
	
	
	    if(document.forms[0].toBeUsedInProducts.value=="")
	    {
	      alert("Please Select To Be Used In Products");
	      document.forms[0].toBeUsedInProducts.focus();
	      return false;
	    }
	         var st = document.forms[0].toBeUsedInProducts.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].toBeUsedInProducts.value=st;
	  if(document.forms[0].tempCondition.value=="")
	    {
	      alert("Please Select Temperature Condition ");
	      document.forms[0].tempCondition.focus();
	      return false;
	    }
	    if(document.forms[0].storageCondition.value=="")
	    {
	      alert("Please Select Storage Condition ");
	      document.forms[0].storageCondition.focus();
	      return false;
	    }
	   
	   
	if(document.forms[0].storage.value=="")
	    {
	      alert("Please Select WM Storage Type ");
	      document.forms[0].storage.focus();
	      return false;
	    }
	
	     
	    if(document.forms[0].valuationClass.value=="")
	    {
	      alert("Please Select Valuation Class");
	      document.forms[0].valuationClass.focus();
	      return false;
	    }
	   
        
        
        
        if(document.forms[0].approximateValue.value!="")
	     {
	    var st = document.forms[0].approximateValue.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].approximateValue.value=st;
     
        }
        
        
         if(document.forms[0].retestDays.value!="")
	    {
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
	    }
	     
	 /*
	    if(document.forms[0].sapCodeNo.value=="")
	    {
	      alert("Please Select SAP Code No");
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
	      alert("Please Select SAP Creation Date");
	      document.forms[0].sapCreationDate.focus();
	      return false;
	    }
	    if(document.forms[0].sapCreatedBy.value=="")
	    {
	      alert("Please Select SAP Created By");
	      document.forms[0].sapCreatedBy.focus();
	      return false;
	    }
	  
	      if(document.forms[0].requestedBy.value=="")
	    {
	      alert("Please Select Requested By");
	      document.forms[0].requestedBy.focus();
	      return false;
	    }
	     */ 
	     var a=document.forms[0].reqEmail.value;
	    var EMAIL = a.replace("&", "*"); 
	     var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	     if(param=="Save"){
			var url="packageMaterial.do?method=savePackageMaterial&EMAIL="+EMAIL;
			document.forms[0].action=url;
			document.forms[0].submit();		}
			
			else
			{
			var url="packageMaterial.do?method=saveAndSubmitMaterial&EMAIL="+EMAIL;
			document.forms[0].action=url;
			document.forms[0].submit();
			
			} 
			
}



function onUpload(){	   
		
			var url="packageMaterial.do?method=uploadFiles";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}


function getTDSState(tdsCode)
{

var status=tdsCode;


if(status=='True')
{
var url="customerMaster.do?method=getTDS";
			document.forms[0].action=url;
			document.forms[0].submit();	
}


}


function getREVState(revState)
{

var status=revState;


if(status=='True')
{
var url="customerMaster.do?method=getRegisterVendor";
			document.forms[0].action=url;
			document.forms[0].submit();	
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
	document.forms[0].action="packageMaterial.do?method=deleteFileListModify&cValues="+checkvalues+"&unValues="+uncheckvalues;
document.forms[0].submit();
}
}
}

	function moveMaterialCode()
	{
	var saveType=document.forms[0].typeDetails.value;
	if(saveType=="Save"){
	if(document.forms[0].storageLocationId.value!=""||
	document.forms[0].materialShortName.value!=""||document.forms[0].materialLongName.value!=""||document.forms[0].materialGroupId.value!=""||
	document.forms[0].unitOfMeasId.value!=""||document.forms[0].puchaseGroupId.value!=""||document.forms[0].dutyElement.value!=""||document.forms[0].packageMaterialGroup.value!=""||
	document.forms[0].typeOfMaterial.value!=""||document.forms[0].artworkNo.value!=""||document.forms[0].isArtworkRevision.value!=""||document.forms[0].existingSAPItemCode.value!=""||
	document.forms[0].isDMFMaterial.value!=""||document.forms[0].dmfGradeId.value!=""||document.forms[0].cosGradeNo.value!=""||document.forms[0].additionalTest.value!=""||
	document.forms[0].isVendorSpecificMaterial.value!=""||document.forms[0].mfgrName.value!=""||document.forms[0].siteOfManufacture.value!=""||document.forms[0].countryId.value!=""||document.forms[0].customerName.value!=""||
	document.forms[0].toBeUsedInProducts.value!=""||document.forms[0].tempCondition.value!=""||document.forms[0].retestDays.value!=""||document.forms[0].approximateValue.value!=""||
	document.forms[0].uploadFileStatus.value=="yes")
	{
	var agree = confirm('Material code not saved.Are you sure open new material code form');
	if(agree)
	{
	if(document.forms[0].uploadFileStatus.value=="yes")
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
	}
	else{
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
function setItemCodeNotMandatory()
{
document.forms[0].selectedType[1].checked=false;
document.getElementById("im").style.visibility="hidden";



}
function file(x)
{
alert(x);
}

function chngeSAp(){

if(	document.forms[0].isArtworkRevision.value=="1")
{
document.getElementById("SAP").style.visibility="visible";

}
else
{
document.getElementById("SAP").style.visibility="hidden";
}

}

function displayApprovers(){
	var locationId=document.forms[0].locationId.value;
	var materialGroupId=document.forms[0].materialGroupId.value;
	var materialTypeId="";
	if(locationId!="" && materialGroupId!=""){
		
		var xmlhttp;
		if (window.XMLHttpRequest)
		  {// code for IE7+, Firefox, Chrome, Opera, Safari
		  xmlhttp=new XMLHttpRequest();
		  }
		else
		  {// code for IE6, IE5
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
		    document.getElementById("apprListID").innerHTML=xmlhttp.responseText;
		    }
		  }
		xmlhttp.open("POST","packageMaterial.do?method=displayApprovers&locationId="+locationId+"&materialGroupId="+materialGroupId+"&materialTypeId="+materialTypeId,true);
		xmlhttp.send();
	}
}
</script>
<style>

.no
{pointer-events: none; 
}
.design

{
	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);
} 


</style>
	</head>

<body>
				<%
					String menuIcon = (String) request.getAttribute("MenuIcon");

					if (menuIcon == null) {
						menuIcon = "";
					}
				%>

				<%
				UserInfo user = (UserInfo) session.getAttribute("user");
				%>

			<html:form action="/packageMaterial.do"	enctype="multipart/form-data">
			<div id="masterdiv" class="">
	
	<html:button property="method" value="New" onclick="moveMaterialCode()"  styleClass="rounded" style="width: 120px"></html:button>
	<br/><p/>
	<div style="width: 90%">
	<table class="bordered" width="90%">
		<tr>
			<th colspan="4" style="text-align: center;"><big>Material Code Request Form</big></th>
		</tr>
		<tr>
			<td>Material Type</td>
			<td>
				<div id="t1" style="visibility: visible;height: 20px;">
					<html:select property="materialCodeLists1" name="PackageMaterialMasterForm" disabled="true" onchange="getMatrialList(this.value)">
						<html:option value="">Select</html:option>
					<html:options name="PackageMaterialMasterForm" property="materTypeIDList" 
									labelProperty="materialTypeIdValueList"/>
					</html:select>
				</div>
	
				<div id="t2" style="visibility: hidden;height: 0;">
					<html:select property="materialCodeLists" name="PackageMaterialMasterForm"  onchange="getMatrialList(this.value)" >
						<html:option value="">Select</html:option>
					<html:options name="PackageMaterialMasterForm" property="materTypeIDList" 
									labelProperty="materialTypeIdValueList"/>
					</html:select>
				</div>
			</td>
		</tr>
	</table>
	
	
	<div>
					<table class="bordered"><tr><th><big>EMAIL ID (For which the confirmation will be Sent)</big><font color="red"><big>*</big></font> </th>
					<logic:notEmpty name="MAILP"> 
					<td><html:text property="reqEmail" name="PackageMaterialMasterForm" style="width: 212px; background-color:#d3d3d3;color:black;"  disabled="true"></html:text> </td>
					</logic:notEmpty>
					<logic:notEmpty name="MAILA"> 
					<td><html:text property="reqEmail" name="PackageMaterialMasterForm" style="width: 212px;"></html:text></td>
					</logic:notEmpty>
					</tr></table>
					
					
					</div>
	
	<div id="materialTable" style="visibility: visible;">
		<table class="bordered">
			<tr>
				<th colspan="4"><big>Basic Details Of Material</big></th>
	   		</tr>
			<tr>
				<td>Request No <font color="red">*</font></td>
				<td align="left">
					<html:text property="requestNo" readonly="true" style="background-color:#d3d3d3;"/>
						<html:hidden property="typeDetails"/>
						<html:hidden property="uploadFileStatus"/>
				</td>
				<td>Request Date <font color="red">*</font></td>
				<td align="left">
					<html:text property="requestDate" readonly="true" style="background-color:#d3d3d3;"/>
				</td>
			</tr>
			<tr>
				<td>Location <font color="red">*</font></td>
				<td align="left">
					<html:select name="PackageMaterialMasterForm" property="locationId" onchange="displayApprovers()">
						<html:option value="">--Select--</html:option>
						<html:options name="PackageMaterialMasterForm" property="locationIdList" labelProperty="locationLabelList"/>
					</html:select>
				</td>
				<td>Storage&nbsp;Location <font color="red">*</font></td>
				<td>
					<html:select  property="storageLocationId">
						<html:option value="">--Select--</html:option>
						<html:options name="PackageMaterialMasterForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
				</td>
			</tr>

<%--				<th width="274" class="specalt" scope="row">Material Type<img src="images/star.gif" width="8" height="8" /></th>--%>
<%--				<td align="left">--%>
<%--				--%>
<%--				<html:select name="PackageMaterialMasterForm" property="materialTypeId" styleClass="text_field" style="width:100px;">--%>
<%--				<html:option value="">--Select--</html:option>--%>
<%--				<html:options name="PackageMaterialMasterForm" property="materTypeIDList" --%>
<%--									labelProperty="materialTypeIdValueList"/>--%>
<%--					</html:select>--%>
<%--				<br /></td>--%>
<%--			</tr>--%>
		
			<tr>
				<td>Short Name <font color="red">*</font></td>
				<td colspan="3">
					<html:text property="materialShortName" maxlength="40" size="40" title="Maximum of 40 characters" style="width:400px;text-transform:uppercase"></html:text>
				</td>
			</tr>
			<tr>
				<td>Long Name <font color="red">*</font></td>
				<td colspan="3" >
						<html:text property="materialLongName" maxlength="80"   title="Maximum of 80 characters" style="width:700px;text-transform:uppercase" ></html:text>
				</td>
			</tr>
			<tr>
				<td>Mat.Group <font color="red">*</font></td>
				<td>
					<html:select name="PackageMaterialMasterForm" property="materialGroupId" onchange="displayApprovers()">
						<html:option value="">--Select--</html:option>
						<html:options name="PackageMaterialMasterForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
					</html:select>
				</td>
					<td>HSN Code<font color="red">*</font></td>
					<td>
						<html:text property="hsnCode" maxlength="8" size="8"> </html:text>
					</td>
				</tr>
			<tr>
				<td>U O M <font color="red">*</font></td>
				<td>
					<html:select property="unitOfMeasId">
						<html:option value="">--Select--</html:option>
						<html:options name="PackageMaterialMasterForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues" style="text-transform:uppercase"/>
					</html:select>
				</td>
			
				<td>Purchasing Group <font color="red">*</font></td>
				<td >
					<html:select property="puchaseGroupId">
						<html:option value="">--Select--</html:option>
						<html:options name="PackageMaterialMasterForm" property="puchaseGroupIdList" labelProperty="puchaseGroupIdValues"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<th colspan="4"><big>Quality Requirement</big></th>
   			</tr>
			
			<tr>
				
				<td colspan="1">
					Duty Element <font color="red">*</font><td colspan="3"><html:select property="dutyElement">
						<html:option value="">--Select--</html:option>
						<html:option value="0">0-Indigenous Material with or without Cenvat</html:option>
						<html:option value="1">1-Duty Exempted Packing Materials for Finished product</html:option>
					</html:select>
				</td>
				</tr>
			<tr>
				<td>Package Material Group <font color="red">*</font></td>
				<td colspan="3">
					<html:select property="packageMaterialGroup">
						<html:option value="">--Select--</html:option>
						<html:options name="PackageMaterialMasterForm" property="packageGroupID" labelProperty="packageGroupIDValue"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>Type Of Material <font color="red">*</font></td>
				<td>
					<html:select property="typeOfMaterial" onchange="getMaterialStatus()">
						<html:option value="">--Select--</html:option>
						<html:option value="Printed Material">Printed Material</html:option>
						<html:option value="Plain Material">Plain Material</html:option>
					</html:select>
				</td>
			
			<logic:notEmpty name="materialTypeNotMandatory">
					<td>Artwork Code</td>
					<td>
						<html:text property="artworkNo" maxlength="20" size="20"></html:text>
					</td>
				</tr>
				<tr>
					<td>Is Artwork Revision </td>
					<td>
						<html:select property="isArtworkRevision"  onchange="chngeSAp()">
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>
			</logic:notEmpty>
			
			<logic:notEmpty name="materialTypeMandatory">
					<td>Artwork Code <font color="red">*</font></td>
					<td>
						<html:text property="artworkNo" maxlength="20" size="20" style="text-transform:uppercase"></html:text>
					</td>
				</tr>
				<tr>
					<td>Is Artwork Revision<font color="red">*</font></td>
					<td>
						<html:select property="isArtworkRevision" onchange="chngeSAp()" >
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>
			</logic:notEmpty>

					<td>Existing SAP Item Code <div style="visibility: hidden" id="SAP"><font color="red">*</font></div> </td>
					<td>
						<html:text property="existingSAPItemCode" maxlength="20" size="20" style="text-transform:uppercase"></html:text>
					</td>
				</tr>
				<tr>
					<td>Is DMF Material <font color="red">*</font></td>
					<td>
						<html:select property="isDMFMaterial" onchange="isDMFStatus(this.value)">
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>

			<logic:notEmpty name="dmfNotMandatory">
					<td>DMF Grade<font color="red">*</font></td>
					<td align="left">
						<html:select name="PackageMaterialMasterForm" property="dmfGradeId">
							<html:option value="">--Select--</html:option>
							<html:options name="PackageMaterialMasterForm" property="dmfGradeIDList" labelProperty="dmfGradeIDValueList"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td>COS Grade & No</td>
					<td>
						<html:text property="cosGradeNo" maxlength="30" size="30" style="text-transform:uppercase"></html:text>
					</td>
			</logic:notEmpty>

			<logic:notEmpty name="dmfMandatory">
					<td>DMF Grade <font color="red">*</font></td>
					<td align="left">
						<html:select name="PackageMaterialMasterForm" property="dmfGradeId">
							<html:option value="">--Select--</html:option>
							<html:options name="PackageMaterialMasterForm" property="dmfGradeIDList" labelProperty="dmfGradeIDValueList"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td>COS Grade & No <font color="red">*</font></td>
					<td>
						<html:text property="cosGradeNo" maxlength="30" size="30" style="text-transform:uppercase"></html:text>
					</td>
			</logic:notEmpty>

				<td>Additional Test</td>
				<td>
					<html:text property="additionalTest" maxlength="20" size="30" style="text-transform:uppercase"></html:text>
				</td>
			</tr>

			<tr>
				<th colspan="4"><big>Vendor / Manufacture Information</big></th>
   			</tr>
			<tr>
				<td>Is Material is Supplier/Manufacture/Site Specific <font color="red">*</font></td>
				<td>
					<html:select property="isVendorSpecificMaterial" onchange="isVendorStatus(this.value)">
						<html:option value="">--Select--</html:option>
						<html:option value="1">Yes</html:option>
						<html:option value="0">No</html:option>
					</html:select>
				</td>

			<logic:notEmpty name="vedorNotMandatory">
					<td>Manufacture&nbsp;Name</td>
					<td>
						<html:text property="mfgrName" maxlength="40" size="40" style="text-transform:uppercase"></html:text>
					</td>
				</tr>
				<tr>
					<td>Site Of Manufacture</td>
					<td>
						<html:text property="siteOfManufacture" maxlength="30" size="30" style="text-transform:uppercase"></html:text>
					</td>
			</logic:notEmpty>

			<logic:notEmpty name="vedorMandatory">
					<td>Manufacture Name <font color="red">*</font></td>
					<td>
						<html:text property="mfgrName" maxlength="40" size="40" style="text-transform:uppercase"></html:text>
					</td>
				</tr>
				<tr>
					<td>Site Of Manufacture <font color="red">*</font></td>
					<td>
						<html:text property="siteOfManufacture" maxlength="30" size="30" style="text-transform:uppercase"></html:text>
					</td>
			</logic:notEmpty>

				<td>Country <font color="red">*</font></td>
				<td>
					<html:select property="countryId">
						<html:option value="">--Select--</html:option>
						<html:options property="counID" labelProperty="countryName" />
					</html:select>
				</td>
			</tr>
			<tr>
				<td>Customer Name </td>
				<td>
					<html:text property="customerName" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text>
				</td>
				<td>To Be Used In Products <font color="red">*</font></td>
				<td>
					<html:text property="toBeUsedInProducts" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text>
				</td>
			</tr>
			
			<tr>
				<th colspan="4"><big>Other Details</big></th>
   			</tr>
   
				<tr>
				<td>Temp.Conditions<font color="red">*</font></td>
				<td >
					<html:select property="tempCondition" >
						<html:option value="">--Select--</html:option>
						<html:options name="PackageMaterialMasterForm" property="tempIDList" labelProperty="temValueList"/>
					</html:select>
				</td>
				
				<td>WM Storage Typea <font color="red">*</font></td>
				<td>
					<html:select property="storage">
						<html:option value="">-----Select-----</html:option>
						<html:option value="REG">REG</html:option>
						<html:option value="AMB">AMB</html:option>
						<html:option value="FRZ">FRZ</html:option>
						<html:option value="CLD">CLD</html:option>
						<html:option value="N/A">N/A</html:option>
						
					</html:select>
				</td>
			
				</tr>
			<tr>
				<td>Storage Condition<font color="red">*</font></td>
				<td colspan="3">
					<html:select property="storageCondition" >
						<html:option value="">--Select--</html:option>
						<html:options name="PackageMaterialMasterForm" property="storageIDList" labelProperty="storageLocList"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>Retest Days</td>
				<td>
					<html:text property="retestDays" maxlength="4" size="4"></html:text>
					<html:select property="retestType" >
						<html:option value="">--Select--</html:option>
						<html:option value="days">Days</html:option>
						<html:option value="months">Months</html:option>
					</html:select>
				</td>
				<td>Valuation Class <font color="red">*</font></td>
				<td>
					<html:select property="valuationClass" styleClass="text_field" >
						
						<html:options name="PackageMaterialMasterForm" property="valuationClassID" labelProperty="valuationClassName"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>Approximate Value</td>
				<td colspan="3">
					<html:text property="approximateValue"></html:text>
				</td>
				
			</tr>	

			<tr>
				<th colspan="4"><big>Attachments <font color="red">*</font></big></th>
			</tr>
			<tr>				
				<td colspan="4">
					<html:file property="fileNames" styleClass="rounded" style="width: 220px" />&nbsp;&nbsp;
					<html:button value="Upload" onclick="onUpload()" property="method" styleClass="rounded" style="width: 120px"/>
				</td>
			</tr>

			<logic:notEmpty name="listName">
				<tr>
					<th colspan="4"><big>Uploaded Documents</big></th>
				</tr>

				<logic:iterate name="listName" id="listid">
					<bean:define id="file" name="listid" property="fileList" />
						<%
							String s = file.toString();
							String v[] = s.split(",");
							int l = v.length;
							for (int i = 0; i < l; i++) {
								int x = v[i].lastIndexOf("/");
								String u = v[i].substring(x + 1);
						%>
	
						<tr>
							<td colspan="2" align="center"><a href="/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles/<%=u%>" target="_blank"><%=u%></a></td>
							<td colspan="2">
								<input type="checkbox" name="checkedfiles" value="<%=u%>" />
							</td>

						</tr>
							<%
							}
							%>
				</logic:iterate>

				<tr>
					<td colspan="4" align="center">
						<html:button value="Delete Files" onclick="onDeleteFile()" property="method" styleClass="rounded" style="width: 100px"/>
							</td>
						</tr>
			</logic:notEmpty>

			<logic:notEmpty name="approved">
				<tr>			
					<th colspan="4">Approver Details</th>
	   			</tr>
				<tr> 
					<td>Approve Type</td>
					<td align="left">
						<html:select name="PackageMaterialMasterForm" property="approveType" styleClass="text_field">
							<html:option value="">--Select--</html:option>
							<html:option value="Pending">Pending</html:option>
							<html:option value="Approved">Approved</html:option>
							<html:option value="Cancel">Cancel</html:option>
						</html:select>
					</td>
				</tr>
			</logic:notEmpty>

			<logic:notEmpty name="sapApprover">
				<tr>			
					<th colspan="4">Material Code Details</th>
		   		</tr>
		   		<tr>
		   		<td>Requested By <font color="red">*</font></td>
				<td>
					<html:text property="requestedBy" maxlength="35" size="35" readonly="true"></html:text>
				</td>
				</tr>
		   		<tr>
					<td>SAP Code Exists <font color="red">*</font></td>
					<td>
						<html:select property="sapCodeExists" styleClass="text_field" >
							<html:option value="">-----Select-----</html:option>
							<html:option value="True">Yes</html:option>
							<html:option value="False">No</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td>SAP Code No <font color="red">*</font></td>
					<td>
						<html:text property="sapCodeNo" maxlength="30" size="30"></html:text>
					</td>
				</tr>
				<tr>	
					<td>SAP Creation Date <font color="red">*</font></td>
					<td align="left">
						<html:text property="sapCreationDate" styleId="sapCreationDate" onfocus="popupCalender('sapCreationDate')" disabled="true" styleClass="text_field"/>
					</td>
				</tr>
				<tr>
					<td>SAP Created By <font color="red">*</font></td>
					<td>
						<html:text property="sapCreatedBy" maxlength="15" size="15"></html:text>
					</td>
				</tr>
			</logic:notEmpty>

			<tr>
				<td colspan="4" style="text-align: center;">
					<html:button property="method" value="Save" onclick="saveData(this.value)" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
					<html:button property="method"  value="Save & Submit" onclick="saveData(this.value)" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
							<html:reset value="Reset" styleClass="rounded" style="width: 100px"></html:reset>&nbsp;&nbsp;
							<html:button property="method" value="Close" onclick="closeData()" styleClass="rounded" style="width: 100px"></html:button>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
		displayApprovers();
		</script>
		<div id="apprListID">
		</div>
		</br>
		<div align="center">
				<logic:present name="PackageMaterialMasterForm" property="message">
					<font color="red" size="3"><b>
							<bean:write name="PackageMaterialMasterForm" property="message" /></b>
					</font>
					<script>	
				 	file("<bean:write name="PackageMaterialMasterForm" property="message" /> ") 
				 	</script>
				</logic:present>
				<logic:present name="PackageMaterialMasterForm" property="message2">
					<font color="Green" size="3"><b>
						<bean:write name="PackageMaterialMasterForm" property="message2" /></b>
					</font>
				</logic:present>
				<logic:notEmpty name="PackageMaterialMasterForm" property="appStatusMessage" >
				<br/>
				<font color="red" size="3"><b><bean:write name="PackageMaterialMasterForm" property="appStatusMessage" /></b></font>
				</logic:notEmpty>
			</div>
		
		</div></div></div>
	</html:form>

</body>
</html>
