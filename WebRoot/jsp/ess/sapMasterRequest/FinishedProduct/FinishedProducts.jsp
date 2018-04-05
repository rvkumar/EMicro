<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>



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
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>eMicro :: Finished Products</title>

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

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
		#brand{
    text-transform:uppercase;
 }
	</style>

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
function closeData(){
var url="materialCode.do?method=displayMaterialList";
			document.forms[0].action=url;
			document.forms[0].submit();
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
	  
function setMaterialTypeId(materialTypeID){ 
	  
	if(document.forms[0].manufacturedAt[0].checked == false)
		document.forms[0].manufacturedAt[1].checked=true;
	else
		document.forms[0].manufacturedAt[0].checked=true;
		
		
	 	var url="finishedProduct.do?method=getMaterialTypeid&materialTypeID="+materialTypeID;
			document.forms[0].action=url;
			document.forms[0].submit();
	  
	  }
function closeData(){
var url="materialCode.do?method=displayMaterialList";
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
	    if(document.forms[0].manufacturedAt[0].checked == false && document.forms[0].manufacturedAt[1].checked == false)
	    {
	      alert("Please Select Manufactured At");
	      document.forms[0].manufacturedAt[0].focus();
	      return false;
	    }
	     if(document.forms[0].materialTypeId.value=="")
	    {
	      alert("Please Select Material Type ");
	      document.forms[0].materialTypeId.focus();
	      return false;
	    }
	    
	     if(document.forms[0].storageLocationId.value=="")
		    {
		      alert("Please Select Storage Location ");
		      document.forms[0].storageLocationId.focus();
		      return false;
		    }
	     
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
	      if(document.forms[0].materialGroupId.value=="")
	    {
	      alert("Please Select Material Group ");
	      document.forms[0].materialGroupId.focus();
	      return false;
	    }
	    if(document.forms[0].unitOfMeasId.value=="")
	    {
	      alert("Please Select U.O.M");
	      document.forms[0].unitOfMeasId.focus();
	      return false;
	    }
	    if(document.forms[0].domesticOrExports.value=="")
	    {
	      alert("Please Select ProductType");
	      document.forms[0].domesticOrExports.focus();
	      return false;
	    }
	    if(document.forms[0].domesticOrExports.value!="")
	    {
	     if(document.forms[0]. materialCodeLists.value=="5")
	     
	     {
	     
	     if(document.forms[0].domesticOrExports.value=="V")
	    {
	      alert("Please Select Different ProductType");
	      document.forms[0].domesticOrExports.focus();
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
	 var st = document.forms[0].customerName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].customerName.value=st;
   

	     if(document.forms[0].saleableOrSample.value=="")
	    {
	      alert("Please Enter Saleable or Sample");
	      document.forms[0].saleableOrSample.focus();
	      return false;
	    }
	    if(document.forms[0].salesPackId.value=="")
	    {
	      alert("Please Enter Pack Size");
	      document.forms[0].salesPackId.focus();
	      return false;
	    }
	     if(document.forms[0].packTypeId.value=="")
	    {
	      alert("Please Enter Pack Type");
	      document.forms[0].packTypeId.focus();
	      return false;
	    }
	     if(document.forms[0].salesUnitOfMeaseurement.value=="")
	    {
	      alert("Please Select Sales Unit Of Measeurement");
	      document.forms[0].salesUnitOfMeaseurement.focus();
	      return false;
	    }
	     if(document.forms[0].divisionId.value=="")
	    {
	      alert("Please Enter Division");
	      document.forms[0].divisionId.focus();
	      return false;
	    }
	     if(document.forms[0].therapeuticSegmentID.value=="")
	    {
	      alert("Please Enter Therapeutic Segment");
	      document.forms[0].therapeuticSegmentID.focus();
	      return false;
	    }
	     if((document.forms[0].brandID.value=="")&&(document.forms[0].brandIDtxt.value==""))
	    {
	      alert("Please Enter/Select BRAND");
	      document.forms[0].brandID.focus();
	      return false;
	    }
	       if(document.forms[0].brandIDtxt.value!="")
	    {
	    var st = document.forms[0].brandIDtxt.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].brandIDtxt.value=st;

   
	   }
	     if(document.forms[0].srengthId.value=="")
	    {
	      alert("Please Enter Strength");
	      document.forms[0].srengthId.focus();
	      return false;
	    }
	    if(document.forms[0].genericName.value=="")
	    {
	      alert("Please Select Generic Name");
	      document.forms[0].genericName.focus();
	      return false;
	    }
	     
	      
	    var grossWeight = document.forms[0].grossWeight.value;
	    if(grossWeight!=""){
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(grossWeight)) {
             alert("Gross Weight Value Should be Integer or Float!");
             document.forms[0].grossWeight.focus();
            return false;
        }
	    }
	    var netWeight = document.forms[0].netWeight.value;
	    if(netWeight!=""){
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(netWeight)) {
             alert("Net Weight Value Should be Integer or Float!");
             document.forms[0].netWeight.focus();
            return false;
        }
	    }
	    
	      if(document.forms[0].dimension.value!="")
	    {
	    var st = document.forms[0].dimension.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].dimension.value=st;

   
	   }
	    	   
	    if(document.forms[0].shelfLife.value=="")
	    {
	      alert("Please Enter Shelf Life");
	      document.forms[0].shelfLife.focus();
	      return false;
	     }
		    var shelfLife = document.forms[0].shelfLife.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(shelfLife)) {
             alert("ShelfLife Should be Integer or Float!");
                document.forms[0].shelfLife.focus();
            return false;
        }
		    
	     if(document.forms[0].shelfType.value=="")
	    {
	      alert("Please Enter Shelf Type");
	      document.forms[0].shelfType.focus();
	      return false;
	    }
	    	 
	   
		    var standardBatchSize = document.forms[0].standardBatchSize.value;
        var pattern = /^\d+(\.\d+)?$/
        if(standardBatchSize!=""){		
        if (!pattern.test(standardBatchSize)) {
             alert("Standard Batch Size Value Should be Integer or Float!");
                document.forms[0].standardBatchSize.focus();
            return false;
        }
        }
		       if(document.forms[0].batchCode.value!="")
	    {
	  
	    var st = document.forms[0].batchCode.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].batchCode.value=st;
	

	 }   
	        
	     if(document.forms[0].materialTypeId.value=="2")
	    {
	      
		      if(document.forms[0].puchaseGroupId.value=="")
		    {
		      alert("Please Select Purchase Group");
		      document.forms[0].puchaseGroupId.focus();
		      return false;
		    }
	    }
	    
	  /*  if(document.forms[0].sapCodeNo.value=="")
	    {
	      alert("Please Enter SAP Code No");
	      document.forms[0].sapCodeNo.focus();
	      return false;
	    }
	    if(document.forms[0].sapCodeExists.value=="")
	    {
	      alert("Please Select SAP Code Exist");
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
	    
	    if(document.forms[0].storage.value=="")
	    {
	      alert("Please Select WM Storage Type ");
	      document.forms[0].storage.focus();
	      return false;
	    }
	
	    
	    var a=document.forms[0].reqEmail.value;
	    var EMAIL = a.replace("&", "*"); 
	    var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	    
	  if(param=="Save"){
			var url="finishedProduct.do?method=saveFinishedProducts&EMAIL="+EMAIL;
			document.forms[0].action=url;
			document.forms[0].submit();	
			}
			else
			{
			var url="finishedProduct.do?method=saveAndSubmitMaterial&EMAIL="+EMAIL;
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

function moveMaterialCode(){
var saveType=document.forms[0].typeDetails.value;
if(saveType=="Save")
{
if(document.forms[0].manufacturedAt[0].checked == true || document.forms[0].manufacturedAt[1].checked == true||
document.forms[0].storageLocationId.value!=""||document.forms[0].materialShortName.value!=""||document.forms[0].materialLongName.value!=""||document.forms[0].materialGroupId.value!=""||
document.forms[0].unitOfMeasId.value!=""||document.forms[0].domesticOrExports.value!=""||document.forms[0].countryId.value!=""||document.forms[0].customerName.value!=""||document.forms[0].prodInspMemo.value!=""||document.forms[0].saleableOrSample.value!=""||
document.forms[0].salesPackId.value!=""||document.forms[0].packTypeId.value!=""||document.forms[0].salesUnitOfMeaseurement.value!=""||document.forms[0].divisionId.value!=""||document.forms[0].therapeuticSegmentID.value!=""||
document.forms[0].brandID.value!=""||document.forms[0].srengthId.value!=""||document.forms[0].genericName.value!=""||document.forms[0].grossWeight.value!=""||document.forms[0].netWeight.value!=""||document.forms[0].weightUOM.value!=""||
document.forms[0].dimension.value!=""||document.forms[0].shelfLife.value!=""||document.forms[0].standardBatchSize.value!=""||document.forms[0].batchCode.value!=""||document.forms[0].valuationClass.value!=""||document.forms[0].puchaseGroupId.value!=""||
document.forms[0].puchaseGroupId.value!=""

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
function file(x)
{
alert(x);
}

function emptybrand()
{
document.forms[0].brandID.value="";
}

function emptybrandtxt()
{
document.forms[0].brandIDtxt.value="";
}

function displayApprovers(){
	var locationId=document.forms[0].locationId.value;
	var productId=document.forms[0].domesticOrExports.value;
	var materialTypeId=document.forms[0].materialTypeId.value;
	if(locationId!="" && productId!=""){
		
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
		xmlhttp.open("POST","finishedProduct.do?method=displayApprovers&locationId="+locationId+"&productId="+productId+"&materialTypeId="+materialTypeId,true);
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

<body >
   			<%
				String menuIcon=(String)request.getAttribute("MenuIcon");
				if(menuIcon==null){
				menuIcon="";
				}
				
				%>
				
				 <% 
	  				UserInfo user=(UserInfo)session.getAttribute("user");
  			%>

		

		<html:button property="method" value="New" onclick="moveMaterialCode()" styleClass="rounded" style="width:100px;"></html:button>
		<br/><p>
		<html:form action="/finishedProduct.do" enctype="multipart/form-data">
<div id="masterdiv" class="">
		<div style="width: 90%">
		<table class="bordered" width="90%">
			<tr>
				<th colspan="4"><center><big>Material Code Request Form</big></center></th>
			</tr>
			<tr>
				<td>Material Type</td>
				<td>
					<div id="t1" style="visibility: visible;height: 20px;">
						<html:select property="materialCodeLists" name="finishedProductForm" disabled="true" onchange="getMatrialList(this.value)" >
							<html:option value="">Select</html:option>
							<html:options name="finishedProductForm" property="materTypeIDList" 
									labelProperty="materialTypeIdValueList"/>
						</html:select>
					</div>
	
					<div id="t2" style="visibility: hidden;height: 0;">
						<html:select property="materialCodeLists1" name="finishedProductForm" onchange="getMatrialList(this.value)" >
							<html:option value="">Select</html:option>
						<html:options name="finishedProductForm" property="materTypeIDList" 
									labelProperty="materialTypeIdValueList"/>
						</html:select>
					</div>
				</td>
			</tr>
		</table>
		
		<div>
					<table class="bordered"><tr><th><big>EMAIL ID (For which the confirmation will be Sent)</big><font color="red"><big>*</big></font> </th>
					<logic:notEmpty name="MAILP"> 
					<td><html:text property="reqEmail" name="finishedProductForm" style="width: 212px; background-color:#d3d3d3;color:black;"  disabled="true"></html:text> </td>
					</logic:notEmpty>
					<logic:notEmpty name="MAILA"> 
					<td><html:text property="reqEmail" name="finishedProductForm" style="width: 212px;"></html:text></td>
					</logic:notEmpty>
					</tr></table>
					
					
					</div>
		

		<div id="materialTable" style="visibility: visible;">
			<table class="bordered">
				<tr>
	 				<th colspan="4"><big>Basic Details</big></th>
   				</tr>
				<tr>
	 				<td>Request No <font color="red">*</font></td>
					<td><html:text property="requestNo" readonly="true" style="background-color:#d3d3d3;"/>
						<html:hidden property="typeDetails"/>
					</td>
					<td>Request Date <font color="red">*</font></td>
					<td><html:text property="requestDate" styleId="requestDate" onfocus="popupCalender('requestDate')" readonly="true" style="background-color:#d3d3d3;"/></td>
				</tr>
				<tr>
					<td>Location <font color="red">*</font></td>
					<td><html:select name="finishedProductForm" property="locationId" onchange="displayApprovers()">
							<html:option value="">--Select--</html:option>
							<html:options name="finishedProductForm" property="locationIdList" labelProperty="locationLabelList"/>
						</html:select>
					</td>
					<td>Manufactured At <font color="red">*</font></td>
					<td>
						<html:checkbox property="manufacturedAt" value="Own" onclick="setMaterialTypeId(this.value)"> Own</html:checkbox>
						&nbsp;&nbsp;
						<html:checkbox property="manufacturedAt" value="Third Party" onclick="setMaterialTypeId(this.value)"> Third Party</html:checkbox>	
					</td>				  
		        </tr>

<%--			<tr>--%>
<%--				<td>Material Type <font color="red">*</font></td>--%>
<%--				<td><html:select property="materialTypeId">--%>
<%--					<html:option value="">Select</html:option>--%>
<%--					<html:option value="1">FERT</html:option>--%>
<%--					<html:option value="2">HAWA</html:option>--%>
<%--					</html:select>--%>
<%--				<br /></td>--%>
<%--			</tr>--%>
			<html:hidden property="materialTypeId"/>
	        	<tr>
					<td>Storage&nbsp;Location <font color="red">*</font></td>
					<td colspan="3">
					<html:select  property="storageLocationId">
							<html:option value="">--Select--</html:option>
							<html:options name="finishedProductForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
					</td>
					</tr>
					<tr>
					<td>Short Name <font color="red">*</font></td>
					<td colspan="3"><html:text property="materialShortName" maxlength="40" size="60" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
				</tr>

				<tr>
					<td>Long Name <font color="red">*</font></td>
				<td colspan="3" ><html:text property="materialLongName" maxlength="80" size="80" title="Maximum of 80 characters" style="width:650px;text-transform:uppercase;"></html:text></td>
				</tr>
			
				<tr>
					<td>Material Group <font color="red">*</font></td>
					<td><html:select name="finishedProductForm" property="materialGroupId" >
							<html:option value="">--Select--</html:option>
							<html:options name="finishedProductForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
						</html:select>
					</td>
					<td>U O M <font color="red">*</font></td>
					<td><html:select property="unitOfMeasId">	
							<html:option value="">--Select--</html:option>
							<html:options name="finishedProductForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues"/>
						</html:select>
					</td>
				</tr>
			
				<tr>
					<th colspan="4"><big>Sales Information</big></th>
   				</tr>

				<tr>
					<td>Product Type <font color="red">*</font></td>
					<td><html:select property="domesticOrExports" onchange="displayApprovers()">	
							<html:option value="">--Select--</html:option>
							<html:option value="D">Domestic</html:option>
							<html:option value="E">Exports</html:option>
							<html:option value="V">Validation</html:option>
						</html:select>
					</td>
					<td>Country <font color="red">*</font></td>
					<td><html:select property="countryId">
							<html:option value="">--Select--</html:option>
							<html:options property="counID" labelProperty="countryName" />
						</html:select>
						</td>
				</tr>

				<tr>
					<td>Customer Name <font color="red">*</font></td>
					<td colspan="3"><html:text property="customerName" maxlength="40" size="60" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
					
					<!--<td>Product Insp. Memo <font color="red">*</font></td>
					<td><html:text property="prodInspMemo" maxlength="18" size="20" title="Maximum of 18 characters" style="text-transform:uppercase"></html:text></td>
				--></tr>
			<html:hidden  property="prodInspMemo"/>
				<tr>
					<td>Saleable or Sample <font color="red">*</font></td>
					<td><html:select property="saleableOrSample">	
							<html:option value="">--Select--</html:option>
							<html:option value="1">Saleable</html:option>
							<html:option value="2">Sample</html:option>
							<html:option value="3">Converted Sample</html:option>
						</html:select>
					</td>
					<td>Pack Size <font color="red">*</font></td>
					<td><html:select property="salesPackId">	
							<html:option value="">--Select--</html:option>
							<html:options name="finishedProductForm" property="packSizeID" labelProperty="packSizeName"/>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>Pack Type <font color="red">*</font></td>
					<td><html:select property="packTypeId">	
							<html:option value="">--Select--</html:option>
							<html:options name="finishedProductForm" property="packTypeID" labelProperty="packTypeName"/>
						</html:select>
					</td>
					<td>Sales U O M <font color="red">*</font></td>
					<td><html:select property="salesUnitOfMeaseurement">	
							<html:option value="">--Select--</html:option>
							<html:options name="finishedProductForm" property="salesUOMID" labelProperty="salesUOMName"/>
						</html:select>
					</td>
				</tr>
			
			<tr>
				<td>Division <font color="red">*</font></td>
				<td><html:select property="divisionId">	
						<html:option value="">--Select--</html:option>
						<html:options name="finishedProductForm" property="divisonID" labelProperty="divisonName"/>
					</html:select>
				</td>
				<td>Therapeutic Segment <font color="red">*</font></td>
				<td><html:select property="therapeuticSegmentID">	
						<html:option value="">--Select--</html:option>
						<html:options name="finishedProductForm" property="therapeuticID" labelProperty="therapeuticName" style="width: 250px"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Brand <font color="red">*</font></td>
				<td>
				<html:text property="brandIDtxt" styleId="brand" onkeyup="emptybrand()" ></html:text><br/>
					<html:select  property="brandID" onchange="emptybrandtxt()">	
						<html:option value="">--Select--</html:option>
						<html:options name="finishedProductForm" property="brandIDList" labelProperty="brandNameList"/>
					</html:select>
					
					
				</td>
				<td>Strength <font color="red">*</font></td>
				<td><html:select property="srengthId">	
						<html:option value="">--Select--</html:option>
						<html:options name="finishedProductForm" property="strengthIDList" labelProperty="strengthNameList"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Generic Name <font color="red">*</font></td>
				<td><html:select property="genericName">	
						<html:option value="">--Select--</html:option>
						<html:options name="finishedProductForm" property="genericIDList" labelProperty="genericNameList" style="width: 250px"/>
					</html:select>
				</td>
				<td>Gross Weight </td>
				<td><html:text property="grossWeight" style="text-transform:uppercase"></html:text></td>
			</tr>
			
			<tr>
				<td>Net Weight </td>
				<td><html:text property="netWeight"></html:text></td>
				<td>Weight UOM</td>
				<td><html:select property="weightUOM">	
						<html:option value="">--Select--</html:option>
						<html:options name="finishedProductForm" property="weightUOMID" labelProperty="weightUOMName"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Shipper Dimension </td>
				<td ><html:text property="dimension" maxlength="30" size="40" style="text-transform:uppercase"></html:text></td>
				<td>Tax Classification</td>
				<td><html:select property="taxClassification">
					<html:option value="">---Select---</html:option>
					<html:option value="0">ED Exempt</html:option>
					<html:option value="1">Taxable(FG/RM/PM/Ve)</html:option>
					<html:option value="2">Taxable (Scrap)</html:option>
					<html:option value="3">Taxable(Food Pro)</html:option>
					<html:option value="4">Taxable (Vet Feed)</html:option>
					<html:option value="5">No Tax (Ph Sam)</html:option>
					<html:option value="6">Taxable(Cosm Pro)</html:option>
				</html:select>
			</tr>
		<tr>
		<td>Material Pricing Group</td>
		<td><html:select property="materialPricing">
		<html:option value="">---Select---</html:option>
			<html:option value="1">Normal</html:option>
			<html:option value="2">Spare parts</html:option>
			<html:option value="11">Scheduled(Controled)</html:option>
			<html:option value="12">Un-Scheduled(De-Con)</html:option>
			<html:option value="13">PS / Promo (Micro)</html:option>
			<html:option value="14">No MRP ED Extra(Mic)</html:option>
			<html:option value="15">No MRP ED Incl (Mic)</html:option>
			<html:option value="16">Scrap IT Extra (Mic)</html:option>
			<html:option value="17">MRP(Con)-ED Exe(Mic)</html:option>
			<html:option value="18">MRP(DeC)-ED Exe(Mic)</html:option>
			<html:option value="19">No MRP-ED Exe(Mic)</html:option>
			<html:option value="20">Food Prod(MRP)-Micro</html:option>
			<html:option value="21">Vet Prod (MRP)-Micr</html:option>
			<html:option value="22">Generic Price Grp-Mi</html:option>
			<html:option value="23">MRP(Vet)-ED Exe(Mic)</html:option>
			<html:option value="24">P-to-P ED Extra(Mic)</html:option>
			<html:option value="25">Cosm Prod(MRP)-Micro</html:option>
			<html:option value="26">Scrap - ED Exe(Mic)</html:option>
			<html:option value="27">Import Items PG</html:option>
		</html:select>
		</td>
			<td>HSN Code<font color="red">*</font></td>
					<td>
						<html:text property="hsnCode" maxlength="8" size="8"> </html:text>
			</td>
		
		</tr>
			

			<tr>
				<th colspan="4"><big>Other Details</big></th>
			</tr>

			<tr>
				<td>Shelf Life <font color="red">*</font></td>
				<td><html:text property="shelfLife" maxlength="5" size="5"></html:text>
					<html:select property="shelfType">
						<html:option value="">-----Select-----</html:option>
						<html:option value="days">Days</html:option>
						<html:option value="months">Months</html:option>
					</html:select>
				</td>

				<logic:notEmpty name="standardBathcNotMandatory">
						<td>Std. Batch Size</td>
						<td><html:text property="standardBatchSize" style="text-transform:uppercase"></html:text></td>
					</tr>
					<tr>
						<td>Batch Code</td>
						<td><html:text property="batchCode" maxlength="10" size="10" style="text-transform:uppercase"></html:text></td>
				</logic:notEmpty>
	
				<logic:notEmpty name="standardBathcMandatory">
						<td>Std. Batch Size </td>
						<td><html:text property="standardBatchSize" style="text-transform:uppercase"></html:text></td>
					</tr>
					<tr>
						<td>Batch Code </td>
						<td><html:text property="batchCode" maxlength="10" size="10" style="text-transform:uppercase"></html:text></td>
				</logic:notEmpty>

				<td>Valuation Class </td>
				<td><html:select property="valuationClass">
						<html:option value="">--Select--</html:option>
						<html:options name="finishedProductForm" property="valuationClassID" labelProperty="valuationClassName"/>
					</html:select>
				</td>
			</tr>
			
			<logic:notEmpty name="manufactureNotMandatory">
			<tr>
				<td>Purchase Group</td>
				<td colspan="1"><html:select property="puchaseGroupId">
						<html:option value="">--Select--</html:option>
						<html:options name="finishedProductForm" property="puchaseGroupIdList" labelProperty="puchaseGroupIdValues"/>
					</html:select>
				</td>
				
					<td>WM Storage Type <font color="red">*</font></td>
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
			</logic:notEmpty>
			
			<logic:notEmpty name="manufactureMandatory">
			<tr>
				<td>Purchase Group <font color="red">*</font></td>
				<td colspan="1"><html:select property="puchaseGroupId">
						<html:option value="">--Select--</html:option>
						<html:options name="finishedProductForm" property="puchaseGroupIdList" labelProperty="puchaseGroupIdValues"/>
					</html:select>
				</td>
				
				<td>WM Storage Type <font color="red">*</font></td>
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
			</logic:notEmpty>

			<logic:notEmpty name="approved">
				<tr>			
					<th colspan="4">Approver Details</th>
		   		</tr>
				<tr>
					<td>Approve Type</td>
					<td><html:select name="finishedProductForm" property="approveType">
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
					<td>SAP Code No </td>
					<td><html:text property="sapCodeNo" maxlength="20" size="20"></html:text></td>
					<td>SAP Code Exists <font color="red">*</font></td>
					<td><html:select property="sapCodeExists" styleClass="text_field" >
							<html:option value="">-----Select-----</html:option>
							<html:option value="True">Yes</html:option>
							<html:option value="False">No</html:option>
						</html:select>
					</td>
				</tr>
				<tr>	
					<td>SAP Creation Date <font color="red">*</font></td>
					<td><html:text property="sapCreationDate" styleId="sapCreationDate" onfocus="popupCalender('sapCreationDate')" disabled="true"/></td>
					<td>SAP Created By <font color="red">*</font></td>
					<td><html:text property="sapCreatedBy" maxlength="12" size="15"></html:text></td>
				</tr>
				<tr>
					<td>Requested By <font color="red">*</font></td>
					<td><html:text property="requestedBy" maxlength="40" size="40"></html:text></td>
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
			<logic:present name="finishedProductForm" property="message">
				<font color="red" size="3"> <b><bean:write name="finishedProductForm" property="message" /></b></font>
				<script>	
				 	file("<bean:write name="finishedProductForm" property="message" /> ") 
				 	</script>
			</logic:present>
			<logic:present name="finishedProductForm" property="message2">
				<font color="Green" size="3"><b><bean:write name="finishedProductForm" property="message2" /></b></font>
			</logic:present>
			<logic:notEmpty name="finishedProductForm" property="appStatusMessage" >
				<br/>
				<font color="red" size="3"><b><bean:write name="finishedProductForm" property="appStatusMessage" /></b></font>
				</logic:notEmpty>
		</div>
		</div>
	<html:hidden property="matType" name="finishedProductForm"/></div></div>
	</html:form>

</body>
</html>
