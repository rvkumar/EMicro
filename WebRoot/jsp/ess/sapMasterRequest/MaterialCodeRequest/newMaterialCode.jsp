
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

function saveData(){	   
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
	    if(document.forms[0].materialLongName.value=="")
	    {
	      alert("Please Enter Material Long Name");
	      document.forms[0].materialLongName.focus();
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
	      alert("Please Enter Unit Of Meas");
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
		     if(document.forms[0].isArtworkRevision.value=="")
		    {
		      alert("Please Select IS ARTWORK REVISION");
		      document.forms[0].isArtworkRevision.focus();
		      return false;
		    }
		    
	   }
	   
	   
	   
	    if(document.forms[0].existingSAPItemCode.value=="")
		    {
		      alert("Please Enter Existing SAP Item Code");
		      document.forms[0].existingSAPItemCode.focus();
		      return false;
		    }
	   
	   
	    if(document.forms[0].isDMFMaterial.value=="")
	    {
	      alert("Please Select Is DMF Material");
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
		    if(document.forms[0].cosGradeNo.value=="")
		    {
		      alert("Please Enter COS Grade And No");
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
	      alert("Please Select To Be Used In Products");
	      document.forms[0].toBeUsedInProducts.focus();
	      return false;
	    }
	     
	    if(document.forms[0].valuationClass.value=="")
	    {
	      alert("Please Select Valuation Class");
	      document.forms[0].valuationClass.focus();
	      return false;
	    }
	    if(document.forms[0].approximateValue.value=="")
	    {
	      alert("Please Select Approximate Value");
	      document.forms[0].approximateValue.focus();
	      return false;
	    }
	      var approximateValue = document.forms[0].approximateValue.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(approximateValue)) {
             alert("Approximate Value Should be Integer or Float!");
            return false;
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
	   */ 
	      if(document.forms[0].requestedBy.value=="")
	    {
	      alert("Please Select Requested By");
	      document.forms[0].requestedBy.focus();
	      return false;
	    }
			var url="packageMaterial.do?method=savePackageMaterial";
			document.forms[0].action=url;
			document.forms[0].submit();		 
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
	if(document.forms[0].locationId.value!=""||document.forms[0].storageLocationId.value!=""||
	document.forms[0].materialShortName.value!=""||document.forms[0].materialLongName.value!=""||document.forms[0].materialGroupId.value!=""||
	document.forms[0].unitOfMeasId.value!=""||document.forms[0].puchaseGroupId.value!=""||document.forms[0].dutyElement.value!=""||document.forms[0].packageMaterialGroup.value!=""||
	document.forms[0].typeOfMaterial.value!=""||document.forms[0].artworkNo.value!=""||document.forms[0].isArtworkRevision.value!=""||document.forms[0].existingSAPItemCode.value!=""||
	document.forms[0].isDMFMaterial.value!=""||document.forms[0].dmfGradeId.value!=""||document.forms[0].cosGradeNo.value!=""||document.forms[0].additionalTest.value!=""||
	document.forms[0].isVendorSpecificMaterial.value!=""||document.forms[0].mfgrName.value!=""||document.forms[0].siteOfManufacture.value!=""||document.forms[0].countryId.value!=""||document.forms[0].customerName.value!=""||
	document.forms[0].toBeUsedInProducts.value!=""||document.forms[0].tempCondition.value!=""||document.forms[0].retestDays.value!=""||document.forms[0].valuationClass.value!=""||document.forms[0].approximateValue.value!=""||
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
</script>

	</head>

<body style="text-transform:uppercase;">
				<%
					String menuIcon = (String) request.getAttribute("MenuIcon");

					if (menuIcon == null) {
						menuIcon = "";
					}
				%>

				<%
				UserInfo user = (UserInfo) session.getAttribute("user");
				%>

			<html:form action="/materialCode.do"	enctype="multipart/form-data">
			<div align="center">
				<logic:present name="PackageMaterialMasterForm" property="message">
					<font color="red">
							<bean:write name="PackageMaterialMasterForm" property="message" />
					</font>
				</logic:present>
				<logic:present name="PackageMaterialMasterForm" property="message2">
					<font color="Green">
						<bean:write name="PackageMaterialMasterForm" property="message2" />
					</font>
				</logic:present>
			</div>
	
	<div style="width: 90%">
	<table class="bordered" width="90%">
		<tr>
			<th colspan="4" style="text-align: center;"><big>Material Code Request Form</big></th>
		</tr>
		<tr>
			<td>Material Type</td>
			<td>
					
					<html:select property="materialCodeLists" name="materialCodeForm"  onchange="getMatrialList(this.value)" >
						<html:option value="">Select</html:option>
					<html:options name="materialCodeForm" property="materTypeIDList" 
									labelProperty="materialTypeIdValueList"/>
					</html:select>
			
			</td>
		</tr>
	</table>
<table border="0">
<tr><td>&nbsp;</td></tr></table>
	   
	   	
</div></html:form></body></html>
	
	




