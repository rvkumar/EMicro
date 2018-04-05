
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
	<title>eMicro :: Finished Products</title>

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
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
function saveData(){	   
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
    alert ("Please Remove Single Code(') in  Customer Name!"); 
     document.forms[0].customerName.focus();
 return false;
}
}
   

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
	     if(document.forms[0].brandID.value=="")
	    {
	      alert("Please Enter BRAND");
	      document.forms[0].brandID.focus();
	      return false;
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
	     
	   
	     if(document.forms[0].grossWeight.value=="")
	    {
	      alert("Please Enter Gross Weight");
	      document.forms[0].grossWeight.focus();
	      return false;
	    }
	    var grossWeight = document.forms[0].grossWeight.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(grossWeight)) {
             alert("Gross Weight Value Should be Integer or Float!");
             document.forms[0].grossWeight.focus();
            return false;
        }
	    if(document.forms[0].netWeight.value=="")
	    {
	      alert("Please Enter Net Weight");
	      document.forms[0].netWeight.focus();
	      return false;
	    }
	    var netWeight = document.forms[0].netWeight.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(netWeight)) {
             alert("Net Weight Value Should be Integer or Float!");
             document.forms[0].netWeight.focus();
            return false;
        }
	    if(document.forms[0].weightUOM.value=="")
	    {
	      alert("Please Enter Weight UOM");
	      document.forms[0].weightUOM.focus();
	      return false;
	    }
	    if(document.forms[0].dimension.value=="")
	    {
	      alert("Please Enter Shipper Dimension");
	      document.forms[0].dimension.focus();
	      return false;
	    }
	    	    var dimension=document.forms[0].dimension.value;
	    for (var i = 0; i < dimension.length; i++) {
    if (splChars.indexOf(dimension.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  ShipperDimension!"); 
     document.forms[0].dimension.focus();
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
	    	 
	   
	   
		   if(document.forms[0].standardBatchSize.value=="")
		    {
		      alert("Please Enter Standard Batch Size");
		      document.forms[0].standardBatchSize.focus();
		      return false;
		    }
		    var standardBatchSize = document.forms[0].standardBatchSize.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(standardBatchSize)) {
             alert("Standard Batch Size Value Should be Integer or Float!");
                document.forms[0].standardBatchSize.focus();
            return false;
        }
		    
		       if(document.forms[0].batchCode.value!="")
	    {
	  
	    	    var batchCode=document.forms[0].batchCode.value;
	    for (var i = 0; i < batchCode.length; i++) {
    if (splChars.indexOf(batchCode.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  BatchCode!"); 
     document.forms[0].batchCode.focus();
 return false;
}
}
	 }   
	    if(document.forms[0].valuationClass.value=="")
		    {
		      alert("Please Select Valuation Class");
		      document.forms[0].valuationClass.focus();
		      return false;
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
	
			var url="finishedProduct.do?method=saveFinishedProducts";
			document.forms[0].action=url;
			document.forms[0].submit();		 
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

</script>
</head>

<body onload="getIsDMFMaterial(),isVendorStatus()" style="text-transform:uppercase">

   		<%
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			if(menuIcon==null){
			menuIcon="";
			} 
  			UserInfo user=(UserInfo)session.getAttribute("user");
  
  		%>

    	<div class="middel-blocks">
     		
		<html:form action="/finishedProduct.do" enctype="multipart/form-data">
            
            <logic:iterate id="finishedProductForm" name="findetails">
				
			

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
					<td>
					<bean:write name="finishedProductForm" property="requestNo"/>
					</td>
					<td>Request Date <font color="red">*</font></td>
					<td>
					<bean:write name="finishedProductForm" property="requestDate"/>
					</td>
				</tr>
				<tr>
					<td>Location <font color="red">*</font></td>
					<td>
					<bean:write name="finishedProductForm" property="locationId"/>

					</td>
					<td>Manufactured At <font color="red">*</font></td>
					<td>
			<bean:write name="finishedProductForm" property="manufacturedAt"/>

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
					<td>Storage&nbsp;Location<font color="red">*</font></td>
					<td colspan="3">
								<bean:write name="finishedProductForm" property="storageLocationId"/>

					</td>
					</tr>
					
					<tr>
					<td>Short Name <font color="red">*</font></td>
					<td colspan="3">
								<bean:write name="finishedProductForm" property="materialShortName"/>
					</td>
				</tr>

				<tr>
					<td>Long Name <font color="red">*</font></td>
				<td colspan="3" >
							<bean:write name="finishedProductForm" property="materialLongName"/>
				</td>
				</tr>
			
				<tr>
					<td>Material Group <font color="red">*</font></td>
					<td>
								<bean:write name="finishedProductForm" property="materialGroupId"/>
					
					</td>
					<td>U O M <font color="red">*</font></td>
					<td>
													<bean:write name="finishedProductForm" property="unitOfMeasId"/>
					
					</td>
				</tr>
			
			<tr>
					<th colspan="8"><big>Sales Information</big></th>
   				</tr>

				<tr>
					<td>Product Type <font color="red">*</font></td>
					<td>
						<bean:write name="finishedProductForm" property="domesticOrExports"/>

					</td>
					<td>Country <font color="red">*</font></td>
					<td>
											<bean:write name="finishedProductForm" property="countryId"/>

				</tr>

				<tr>
					<td>Customer Name <font color="red">*</font></td>
					<td colspan="3">
											<bean:write name="finishedProductForm" property="customerName"/>
					</td>
					
				
				</tr>
			
				<tr>
					<td>Saleable or Sample <font color="red">*</font></td>
					<td>
									<bean:write name="finishedProductForm" property="saleableOrSample"/>
					

					</td>
					<td>Pack Size <font color="red">*</font></td>
					<td>								
						<bean:write name="finishedProductForm" property="salesPackId"/>

					</td>
				</tr>

				<tr>
					<td>Pack Type <font color="red">*</font></td>
					<td>									
					<bean:write name="finishedProductForm" property="packTypeId"/>

					</td>
					<td>Sales U O M <font color="red">*</font></td>
					<td>					<bean:write name="finishedProductForm" property="salesUnitOfMeaseurement"/>

					</td>
				</tr>
			
			<tr>
				<td>Division <font color="red">*</font></td>
				<td>				
					<bean:write name="finishedProductForm" property="divisionId"/>

				</td>
				<td>Therapeutic Segment <font color="red">*</font></td>
				<td>
									<bean:write name="finishedProductForm" property="therapeuticSegmentID"/>
				
				</td>
			</tr>

			<tr>
				<td>Brand <font color="red">*</font></td>
				<td>
		        <bean:write name="finishedProductForm" property="brandID"/>
		        </td>
				<td>Strength <font color="red">*</font></td>
				<td>
						        <bean:write name="finishedProductForm" property="srengthId"/>
				
				</td>
			</tr>

			<tr>
				<td>Generic Name <font color="red">*</font></td>
				<td>
				 <bean:write name="finishedProductForm" property="genericName"/>
				
				</td>
				<td>Gross Weight <font color="red">*</font></td>
				<td>
				 <bean:write name="finishedProductForm" property="grossWeight"/>
				</td>
			</tr>
			
			<tr>
				<td>Net Weight <font color="red">*</font></td>
				<td>
				 <bean:write name="finishedProductForm" property="netWeight"/>
				</td>
				<td>Weight UOM <font color="red">*</font></td>
				<td>
								 <bean:write name="finishedProductForm" property="weightUOM"/>

				</td>
			</tr>

			<tr>
				<td>Shipper Dimension </td>
				<td >
				 <bean:write name="finishedProductForm" property="dimension"/>
				</td>
				<td>Tax Classification</td>
				<td>
								 <bean:write name="finishedProductForm" property="taxClassification"/>
				
				</td>
			</tr>
		<tr>
		<td>Material Pricing Group</td>
		<td colspan="3">
										 <bean:write name="finishedProductForm" property="materialPricing"/>
		
		</td>
		</tr>
			

			<tr>
				<th colspan="8"><big>Other Details</big></th>
			<tr>

			<tr>
				<td>Shelf Life <font color="red">*</font></td>
				<td>
										 <bean:write name="finishedProductForm" property="shelfLife"/>
										 <bean:write name="finishedProductForm" property="shelfType"/>
				
				</td>

				<logic:notEmpty name="standardBathcNotMandatory">
						<td>Std. Batch Size<font color="red">*</font></td>
						<td>
									 <bean:write name="finishedProductForm" property="standardBatchSize"/>
						
						</td>
					</tr>
					<tr>
						<td>Batch Code</td>
						<td>
										 <bean:write name="finishedProductForm" property="batchCode"/>
						</td>
				</logic:notEmpty>
	
				<logic:notEmpty name="standardBathcMandatory">
						<td>Std. Batch Size <font color="red">*</font></td>
						<td>
									 <bean:write name="finishedProductForm" property="standardBatchSize"/>
						</td>
					</tr>
					<tr>
						<td>Batch Code </td>
						<td>
										 <bean:write name="finishedProductForm" property="batchCode"/>
						</td>
				</logic:notEmpty>

				<td>Valuation Class <font color="red">*</font></td>
				<td>
							 <bean:write name="finishedProductForm" property="valuationClass"/>
				
				</td>
			</tr>
			
			<logic:notEmpty name="manufactureNotMandatory">
			<tr>
				<td>Purchase Group</td>
				<td colspan="3">
				 <bean:write name="finishedProductForm" property="puchaseGroupId"/>
				
				</td>
			</tr>
			</logic:notEmpty>
			
			<logic:notEmpty name="manufactureMandatory">
			<tr>
				<td>Purchase Group <font color="red">*</font></td>
				<td colspan="3">
								 <bean:write name="finishedProductForm" property="puchaseGroupId"/>
				
				</td>
			</tr>
			</logic:notEmpty>

			
						 <tr>			
	<th colspan="6">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<img src="images/star.gif" width="8" height="8" /></td>
				<td>
				<bean:write name="finishedProductForm" property="sapCodeExists"/>
				</td>
		
			<td >SAP Code No<img src="images/star.gif" width="8" height="8" /></td>
				<td colspan="4"><bean:write name="finishedProductForm" property="sapCodeNo"/>	</td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<bean:write name="finishedProductForm" property="sapCreationDate"/>
				
			</td>
			
				<td >Code Created By<img src="images/star.gif" width="8" height="8" /></td><td colspan="4">
				<bean:write name="finishedProductForm" property="sapCreatedBy"/>
			
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
