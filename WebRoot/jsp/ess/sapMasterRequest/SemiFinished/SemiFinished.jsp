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
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Semi Finished Creation</title>

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
<%--	     if(document.forms[0].materialTypeId.value=="")--%>
<%--	    {--%>
<%--	      alert("Please Select Material Type");--%>
<%--	      document.forms[0].materialTypeId.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
	    if(document.forms[0].storageLocationId.value=="")
	    {
	      alert("Please Select Storage Location");
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
	      if(document.forms[0].materialGroupId.value=="")
	    {
	      alert("Please Select Material Group");
	      document.forms[0].materialGroupId.focus();
	      return false;
	    }
	      if(document.forms[0].unitOfMeasId.value=="")
	    {
	      alert("Please Select U.O.M");
	      document.forms[0].unitOfMeasId.focus();
	      return false;
	    }
	    
	    if(document.forms[0].packSize.value=="")
	    {
	      alert("Please Select Pack Size");
	      document.forms[0].packSize.focus();
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
	
	
	
	    /*if(document.forms[0].prodInspMemo.value=="")
	    {
	      alert("Please Enter Prod Insp Memo");
	      document.forms[0].prodInspMemo.focus();
	      return false;
	    }
	      var prodInspMemo=document.forms[0].prodInspMemo.value;
	    for (var i = 0; i < prodInspMemo.length; i++) {
    if (splChars.indexOf(prodInspMemo.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Prod Insp Memo!"); 
    	      document.forms[0].prodInspMemo.focus();
 return false;
}
}
*/
	      if(document.forms[0].shelfLife.value=="")
	    {
	      alert("Please Enter Shelf Life");
	      document.forms[0].shelfLife.focus();
	      return false;
	    }
	     var shelfLife = document.forms[0].shelfLife.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(shelfLife)) {
             alert("Shelf Life  Should be Integer ");
              document.forms[0].shelfLife.focus();
            return false;
        }
        
        
          if(document.forms[0].shelfType.value=="")
	    {
	      alert("Please Select Shelf Type");
	      document.forms[0].shelfType.focus();
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
	      alert("Please Enter Retest Days Type");
	      document.forms[0].retestType.focus();
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
	      if(document.forms[0].batchCode.value=="")
	    {
	      alert("Please Enter Batch Code");
	      document.forms[0].batchCode.focus();
	      return false;
	    }
	    var st = document.forms[0].batchCode.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].batchCode.value=st;
	
	      if(document.forms[0].targetWeight.value=="")
	    {
	      alert("Please Enter Target Weight");
	      document.forms[0].targetWeight.focus();
	      return false;
	    }
	    var targetWeight = document.forms[0].targetWeight.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(targetWeight)) {
             alert("Target Weight Value Should be Integer or Float!");
              document.forms[0].targetWeight.focus();
            return false;
        }
	      
	    
	     
	     if(document.forms[0].weightUOM.value=="")
	    {
	      alert("Please Enter Weight UOM");
	      document.forms[0].weightUOM.focus();
	      return false;
	    }
	     
        
	     if(document.forms[0].valuationClass.value=="")
	    {
	      alert("Please Select Valuation Class");
	      document.forms[0].valuationClass.focus();
	      return false;
	    }
	  /*   if(document.forms[0].sapCodeNo.value=="")
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
	   var a=document.forms[0].reqEmail.value;
	    var EMAIL = a.replace("&", "*"); 
	    
	    var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	    
	   if(param=="Save"){
			var url="semifinished.do?method=saveSemiFinished&EMAIL="+EMAIL;
			document.forms[0].action=url;
			document.forms[0].submit();		}
			
			else{
			
			var url="semifinished.do?method=saveAndSubmitMaterial&EMAIL="+EMAIL;
			document.forms[0].action=url;
			document.forms[0].submit();		 
			
			
			} 
}



function onUpload(){	   
		
			var url="finishedProduct.do?method=uploadFiles";
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

function moveMaterialCode(){


var saveType=document.forms[0].typeDetails.value;
if(saveType=="Save")
{
if(document.forms[0].locationId.value!=""||document.forms[0].storageLocationId.value!=""||document.forms[0].materialShortName.value!=""||document.forms[0].materialLongName.value!=""||
document.forms[0].materialGroupId.value!=""||document.forms[0].unitOfMeasId.value!=""||document.forms[0].countryId.value!=""||document.forms[0].customerName.value!=""||
document.forms[0].shelfLife.value!=""||document.forms[0].standardBatchSize.value!=""||document.forms[0].batchCode.value!=""||document.forms[0].targetWeight.value!=""||document.forms[0].weightUOM.value!=""||
document.forms[0].retestDays.value!="")
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

function displayApprovers(){
	var locationId=document.forms[0].locationId.value;
	var materialGroupId=document.forms[0].materialGroupId.value;
	var materialTypeId=document.forms[0].materialTypeId.value;
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
		xmlhttp.open("POST","semifinished.do?method=displayApprovers&locationId="+locationId+"&materialGroupId="+materialGroupId+"&materialTypeId="+materialTypeId,true);
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
  
	

	<html:form action="/semifinished.do" enctype="multipart/form-data">
		<div id="masterdiv" class="">
	<html:button property="method" value="New" onclick="moveMaterialCode()" styleClass="rounded" style="width: 100px"></html:button>
	
	<div style="width: 90%">
	<table class="bordered" width="90%">
		<tr>
			<th colspan="4"><center><big>Material Code Request Form</big></center></th>
		</tr>
		<tr>
			<td>Material Type</td>
			<td>
				<div id="t1" style="visibility: visible;height: 20px;">
					<html:select property="materialCodeLists" name="semiFinshedForm" disabled="true" onchange="getMatrialList(this.value)">
						<html:option value="">--Select--</html:option>
					<html:options name="semiFinshedForm" property="materTypeIDList" 
									labelProperty="materialTypeIdValueList"/>
					</html:select>
				</div>
	
				<div id="t2" style="visibility: hidden;height: 0;">
					<html:select property="materialCodeLists1" name="semiFinshedForm" onchange="getMatrialList(this.value)" >
						<html:option value="">--Select--</html:option>
						<html:options name="semiFinshedForm" property="materTypeIDList" 
									labelProperty="materialTypeIdValueList"/>
					</html:select>
				</div>
			</td>
		</tr>
	</table>
	</div>
	
	
		<div style="visibility: visible; width: 90%;" >
					<table class="bordered"><tr><th><big>EMAIL ID (For which the confirmation will be Sent)</big><font color="red"><big>*</big></font> </th>
					<logic:notEmpty name="MAILP"> 
					<td><html:text property="reqEmail" name="semiFinshedForm" style="width: 200px; background-color:#d3d3d3;color:black;"  disabled="true"></html:text> </td>
					</logic:notEmpty>
					<logic:notEmpty name="MAILA"> 
					<td><html:text property="reqEmail" name="semiFinshedForm" style="width: 197px;"></html:text></td>
					</logic:notEmpty>
					</tr></table>
					
					
					</div>

	<div id="materialTable" style="visibility: visible; width: 90%;" >
		<table class="bordered" width="90%">
			<tr>
	 			<th colspan="4"><big>Basic Details Of Material</big></th>
			</tr>

			<tr>
				<td>Request No. <font color="red">*</font></td>
				<td><html:text property="requestNo" readonly="true" style="background-color:#d3d3d3;"/>
					<html:hidden property="typeDetails"/>
				</td>
				<td>Request Date <font color="red">*</font></td>
				<td><html:text property="requestDate" styleId="requestDate" onfocus="popupCalender('requestDate')" readonly="true" style="background-color:#d3d3d3;"/></td>
			</tr>

			<tr>
				<td>Location <font color="red">*</font></td>
				<td><html:select name="semiFinshedForm" property="locationId" onchange="displayApprovers()">
						<html:option value="">--Select--</html:option>
						<html:options name="semiFinshedForm" property="locationIdList" labelProperty="locationLabelList"/>
					</html:select>
				</td>

<%--				<th width="274" class="specalt" scope="row">Material Type <img src="images/star.gif" width="8" height="8" /></th>--%>
<%--				<td align="left">--%>
<%--				<html:select  name="semiFinshedForm"  property="materialTypeId" styleClass="text_field" style="width:100px; ">--%>
<%--					<html:option value="">--Select--</html:option>--%>
<%--				<html:options name="semiFinshedForm" property="materTypeIDList" --%>
<%--									labelProperty="materialTypeIdValueList"/>--%>
<%--					</html:select>--%>
<%--				<br /></td>--%>
				 <html:hidden property="materialTypeId"/>
				<td>Storage Location <font color="red">*</font></td>
				<td><html:select name="semiFinshedForm" property="storageLocationId">
						<html:option value="">--Select--</html:option>
						<html:options name="semiFinshedForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Short Name <font color="red">*</font></td>
				<td colspan="3"><html:text property="materialShortName" maxlength="40" size="45" title="Maximum of 40 characters" style="width:400px;text-transform:uppercase"></html:text></td>
			</tr>
			
			<tr>
				<td>Long Name <font color="red">*</font></td>
				<td colspan="3" ><html:text property="materialLongName" maxlength="80" size="80" title="Maximum of 80 characters" style="width:650px;text-transform:uppercase;"></html:text></td>
			</tr>
			
			<tr>
				<td>Material Group <font color="red">*</font></td>
				<td colspan="3"><html:select name="semiFinshedForm" property="materialGroupId" onchange="displayApprovers()">
						<html:option value="">--Select--</html:option>
						<html:options name="semiFinshedForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
					</html:select>
				&nbsp;&nbsp;U O M <font color="red">*</font>
				<html:select name="semiFinshedForm" property="unitOfMeasId">
						<html:option value="">--Select--</html:option>
						<html:options name="semiFinshedForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues"/>
					</html:select>
				</td>
					</tr>
					<tr><td>Pack Size<font color="red">*</font></td>
				<td colspan="3"><html:select property="packSize">	
							<html:option value="">--Select--</html:option>
							<html:options name="semiFinshedForm" property="packSizeID" labelProperty="packSizeName"/>
						</html:select>
				
		</td>
			</tr>

			<tr>
				<th colspan="4"><big>Quality Requirement</big></th>
   			</tr>

			<tr>
				<td>Country </td>
				<td><html:select property="countryId">
						<html:option value="">--Select--</html:option>
						<html:options property="counID" labelProperty="countryName" />
					</html:select>
				</td>
				<td>Customer Name <font color="red">*</font></td>
				<td><html:text property="customerName" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
			</tr>

			<tr>
				
				<html:hidden property="prodInspMemo" ></html:hidden>
				
				<td>Shelf Life <font color="red">*</font></td>
				<td ><html:text property="shelfLife" style="width:40px;"></html:text>
					<html:select property="shelfType">
						<html:option value="months">Months</html:option>
						<html:option value="days">Days</html:option>
						
					</html:select>
				</td>
				<td>Retest Days <font color="red">*</font></td>
				<td colspan="3"><html:text property="retestDays" style="text-transform:uppercase;width:40px;" ></html:text>
				<html:select property="retestType">
						<html:option value="days">Days</html:option>
						<html:option value="months">Months</html:option>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Std. Batch Size <font color="red">*</font></td>
				<td><html:text property="standardBatchSize" style="text-transform:uppercase"></html:text></td>
				<td>Batch Code <font color="red">*</font></td>
				<td><html:text property="batchCode" maxlength="10" style="text-transform:uppercase"></html:text></td>
			</tr>

			<tr>
				<td>Target Weight <font color="red">*</font></td>
				<td><html:text property="targetWeight" style="text-transform:uppercase"></html:text></td>
			
			<!--<tr>
			<th width="274" class="specalt" scope="row">Gross Weight<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="grossWeight" styleClass="text_field" ></html:text>
			</td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Net Weight<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="netWeight" styleClass="text_field" ></html:text>
			</td>
			</tr>
			<tr-->

				<td>Weight UOM <font color="red">*</font></td>
				<td><html:select property="weightUOM">	
						<html:option value="">--Select--</html:option>
						<html:options name="semiFinshedForm" property="weightUOMID" labelProperty="weightUOMName"/>
					</html:select></td>
			</tr>

			<tr>
				
			</tr>

			<tr>
				<th colspan="4"><big>Other Details</big></th>
   			</tr>

			<tr>
				<td>Valuation Class <font color="red">*</font></td>
				<td><html:select property="valuationClass">
						<html:options name="semiFinshedForm" property="valuationClassID" labelProperty="valuationClassName"/>
					</html:select>
				</td>
			</tr>

			<logic:notEmpty name="approved">
				<tr>			
					<th colspan="4"><big>Approver Details</big></th>
	   			</tr>
		
				<tr>
					<td>Approve Type</td>
					<td><html:select name="semiFinshedForm" property="approveType">
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
					<th colspan="4"><big>Material Code Details</big></th>
				</tr>

				<tr>
					<td>SAP Code No. <font color="red">*</font></td>
					<td><html:text property="sapCodeNo" maxlength="20"></html:text></td>			
					<td>SAP Code Exists <font color="red">*</font></td>
					<td><html:select property="sapCodeExists" styleClass="text_field" >
							<html:option value="">--Select--</html:option>
							<html:option value="True">Yes</html:option>
							<html:option value="False">No</html:option>
						</html:select>
					</td>
				</tr>

				<tr>	
					<td>SAP Creation Date <font color="red">*</font></td>
					<td><html:text property="sapCreationDate" styleId="sapCreationDate" onfocus="popupCalender('sapCreationDate')" disabled="true"/></td>
					<td>SAP Created By <font color="red">*</font></td>
					<td><html:text property="sapCreatedBy"></html:text></td>
				</tr>

				<tr>
					<td>Requested By <font color="red">*</font></td>
					<td colspan="3"><html:text property="requestedBy" maxlength="40" size="40" title="Maximum of 40 characters"></html:text></td>
				</tr>
				
			</logic:notEmpty>

			<tr>
				<td colspan="4" style="text-align: center;">
					<html:button property="method"  value="Save" onclick="saveData(this.value)" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
						<html:button property="method"  value="Save & Submit" onclick="saveData(this.value)" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
					<html:reset value="Reset" styleClass="rounded" style="width: 100px"></html:reset>&nbsp;&nbsp;
					<html:button property="method"  value="Close" onclick="closeData()" styleClass="rounded" style="width: 100px"></html:button>
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
		<logic:present name="semiFinshedForm" property="message">
			<font color="red" size="3"><b><bean:write name="semiFinshedForm" property="message" /></b></font>
			<script>	
				 	file("<bean:write name="semiFinshedForm" property="message" /> ") 
				 	</script>
		</logic:present>
		<logic:present name="semiFinshedForm" property="message2">
			<font color="Green" size="3"><b><bean:write name="semiFinshedForm" property="message2" /></b></font>
		</logic:present>
		<logic:notEmpty name="semiFinshedForm" property="appStatusMessage" >
				<br/>
				<font color="red" size="3"><b><bean:write name="semiFinshedForm" property="appStatusMessage" /></b></font>
				</logic:notEmpty>
	</div>
	</div></div>
</html:form>
</body>
</html>
