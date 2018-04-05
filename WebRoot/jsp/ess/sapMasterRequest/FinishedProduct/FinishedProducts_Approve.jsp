
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
<title>Finished Products</title>
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
function saveData(){	   
		
	    
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
	
			var url="finishedProduct.do?method=saveApproverData";
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

function moveMaterialCode()
	{
	var saveType=document.forms[0].typeDetails.value;
	if(saveType=="Save")
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
<style type="text/css">
#slideshow {position:relative; margin:0 auto;}
#slideshow img {position:absolute; display:none}
#slideshow img.active {display:block}
</style>


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

<body >
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

  <tr>
   <%
			
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			
			if(menuIcon==null){
			menuIcon="";
			}
			
			%>
			
			 <% 
  				UserInfo user=(UserInfo)session.getAttribute("user");
  
  			%>
  
  
   
     			<div align="center">
						<logic:present name="finishedProductForm" property="message">
						<font color="red">
							<bean:write name="finishedProductForm" property="message" />
						</font>
					</logic:present>
					<logic:present name="finishedProductForm" property="message2">
						<font color="Green">
							<bean:write name="finishedProductForm" property="message2" />
						</font>
					</logic:present>
					</div>
					<html:button property="method" value="New" onclick="moveMaterialCode()"></html:button>
<br/>
<html:form action="/finishedProduct.do" enctype="multipart/form-data">

	<table align="center" border="0" cellpadding="4" cellspacing="0"  id="mytable1" style="width:885px;">

	      <tr>

<th colspan="4">
		<center><big>Material Code Request Form</big></center></th>
	
</tr>
	
	
	<th width="274" class="specalt" scope="row">
	Material&nbspType
</th>
	<td>
	<div id="t1" style="visibility: visible;height: 20px;">
	<html:select property="materialCodeLists" name="finishedProductForm"   disabled="true"  style="width:550px;"   onchange="getMatrialList(this.value)" >

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
	
	<div id="t2" style="visibility: hidden;height: 0;">
	<html:select property="materialCodeLists1" name="finishedProductForm"  style="width:550px;"  onchange="getMatrialList(this.value)" >

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
				<table align="center" border="0" cellpadding="4"
					cellspacing="0" id="mytable1" style="width:885px;">

					
                					<tr>
	 <th colspan="2">Basic Details Of Material:</th>
   </tr>
		<tr>
	 <th width="274" class="specalt" scope="row">Request No<img src="images/star.gif" width="8" height="8" /></th>
	  	
		<td align="left">
			<label><html:text property="requestNo" styleClass="text_field"  readonly="true" maxlength="50"/>
			<html:hidden property="typeDetails"/>
			</label>
		</td>
		</tr>
		<tr>
			<th width="274" class="specalt" scope="row">Request Date<img src="images/star.gif" width="8" height="8" /></th>
			<td align="left">
				<html:text property="requestDate" styleId="requestDate" onfocus="popupCalender('requestDate')"  styleClass="text_field"/></td>
	
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Location<img src="images/star.gif" width="8" height="8" /></th>
				<td align="left">
				<html:select name="finishedProductForm" property="locationId" styleClass="text_field" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="finishedProductForm" property="locationIdList" 
									labelProperty="locationLabelList"/>
					</html:select>
				<br /></td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Manufactured At<img src="images/star.gif" width="8" height="8" /></th>
				<td align="left">
				<html:checkbox property="manufacturedAt" value="Own" onclick="setMaterialTypeId(this.value)" >Own</html:checkbox>
				
				<html:checkbox property="manufacturedAt" value="Third Party" onclick="setMaterialTypeId(this.value)">Third Party</html:checkbox>	
				</td>				  
	        </tr>
			<tr>
				<th width="274" class="specalt" scope="row">Material Type<img src="images/star.gif" width="8" height="8" /></th>
				<td align="left">
				<html:select  property="materialTypeId" styleClass="text_field" disabled="true">
					<html:option value="">Select</html:option>
					<html:option value="1">FERT</html:option>
					<html:option value="2">HAWA</html:option>
					</html:select>
				<br /></td>
			</tr>
			
	        <tr>
			<th width="274" class="specalt" scope="row">Storage Location<img src="images/star.gif" width="8" height="8" /></th>
			<td>
			<html:select  property="storageLocationId" styleClass="text_field" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="finishedProductForm" property="storageID" 
									labelProperty="storageIDName"/>
				</html:select>
			</td>
			</tr>
			
	        <tr>
			<th width="274" class="specalt" scope="row">Material&nbspShort&nbspName<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="materialShortName" styleClass="text_field" style="width:400px;"  maxlength="40" readonly="true"></html:text>
			</td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Material Long Name<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="materialLongName" styleClass="text_field" style="width:500px;"  maxlength="80" readonly="true"></html:text>
			</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Material Group<img src="images/star.gif" width="8" height="8" /></th>
				<td align="left">
				<html:select name="finishedProductForm" property="materialGroupId" styleClass="text_field" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="finishedProductForm" property="materGroupIDList" 
									labelProperty="materialGroupIdValueList"/>
					
					</html:select>
				<br /></td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Unit&nbspOf&nbspMeasurement<img src="images/star.gif" width="8" height="8" /></th>
				<td>
				<html:select property="unitOfMeasId" styleClass="text_field" disabled="true">	
				<html:option value="">-----Select-----</html:option>
				<html:options name="finishedProductForm" property="unitOfMeasIdList" 
									labelProperty="unitOfMeasIdValues"/>
				</html:select>
				</td>
			</tr>
			
				<tr>
	  <th colspan="2">Sales Information :</th>
   </tr>
   <tr>
			<th width="274" class="specalt" scope="row">Domestic Or Exports<img src="images/star.gif" width="8" height="8" /></th>
			<td>
				<html:select property="domesticOrExports" styleClass="text_field" disabled="true">	
				<html:option value="">-----Select-----</html:option>
				<html:option value="D">Domestic</html:option>
				<html:option value="E">Exports</html:option>
				</html:select>
				</td>
			
			</tr>
		<tr>
			<th width="274" class="specalt" scope="row">Country<img src="images/star.gif" width="8" height="8" /></th>
			
			<td><html:select property="countryId"  styleClass="text_field" disabled="true">
			<html:option value="">-----Select-----</html:option>
			<html:options property="counID" labelProperty="countryName" styleClass="text_field"/>
			</html:select>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Customer Name<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:text property="customerName" styleClass="text_field" style="width:500px;"  maxlength="50" readonly="true"></html:text>
				</td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Product Insp. Memo<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="prodInspMemo" styleClass="text_field" style="width:200px;"  maxlength="18" readonly="true"></html:text>
			</td>
			</tr>
			
			<tr>
			<th width="274" class="specalt" scope="row">Saleable&nbspor&nbspSample<img src="images/star.gif" width="8" height="8" /></th>
			<td>
				<html:select property="saleableOrSample" styleClass="text_field" disabled="true">	
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">Saleable</html:option>
				<html:option value="2">Sample</html:option>
				</html:select>
				</td>
			</tr>
			
			
			<tr>
			<th width="274" class="specalt" scope="row">Pack Size<img src="images/star.gif" width="8" height="8" /></th>
			<td>
			<html:select property="salesPackId" styleClass="text_field" disabled="true">	
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
		
			</td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Pack Type<img src="images/star.gif" width="8" height="8" /></th>
			<td>
			<html:select property="packTypeId" styleClass="text_field" disabled="true">	
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
			</td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Sales&nbsp;Unit&nbsp;Of&nbsp;Measurement <img src="images/star.gif" width="8" height="8" /></th>
				<td>
				<html:select property="salesUnitOfMeaseurement" styleClass="text_field" disabled="true">	
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
				</td>
			
			</tr>
			
			<tr>
			<th width="274" class="specalt" scope="row">Division <img src="images/star.gif" width="8" height="8" /></th>
				<td>
				<html:select property="divisionId" styleClass="text_field" disabled="true">	
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
				</td>
			
			</tr>
			
			<tr>
			<th width="274" class="specalt" scope="row">Therapeutic Segment<img src="images/star.gif" width="8" height="8" /></th>
			<td>
				<html:select property="therapeuticSegmentID" styleClass="text_field" disabled="true">	
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
				</td>
			
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">BRAND<img src="images/star.gif" width="8" height="8" /></th>
			<td>
				<html:select property="brandID" styleClass="text_field" disabled="true">	
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
				</td>
			
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Strength<img src="images/star.gif" width="8" height="8" /></th>
			<td>
				<html:select property="srengthId" styleClass="text_field" disabled="true">	
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
				</td>
			
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Generic Name<img src="images/star.gif" width="8" height="8" /></th>
			<td>
				<html:select property="genericName" styleClass="text_field" disabled="true">	
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
				</td>
			
			</tr>
			
			
				<tr>
			<th width="274" class="specalt" scope="row">Gross Weight<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="grossWeight" styleClass="text_field" readonly="true"></html:text>
			</td>
			</tr>
			
			<tr>
			<th width="274" class="specalt" scope="row">Net Weight<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="netWeight" styleClass="text_field" readonly="true"></html:text>
			</td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Weight UOM<img src="images/star.gif" width="8" height="8" /></th>
			<td>
			
				<html:select property="weightUOM" styleClass="text_field" disabled="true">	
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
				</td>
		
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Shipper Dimension<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="dimension" styleClass="text_field" style="width:300px;"  maxlength="30" readonly="true"></html:text>
			</td>
			</tr>
					<tr>
	  <th colspan="2">Other Details :</th>
	 
	
			<tr>
			<th width="274" class="specalt" scope="row">Shelf Life<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="shelfLife" styleClass="text_field" style="width:40px;" readonly="true"></html:text>
			<html:select property="shelfType"  styleClass="text_field"  disabled="true">
				<html:option value="">-----Select-----</html:option>
				<html:option value="days">Days</html:option>
					<html:option value="months">Months</html:option></html:select>
			</td>
			</tr>
			

			
			
				<logic:notEmpty name="standardBathcNotMandatory">
				
			
			<tr>
			<th width="274" class="specalt" scope="row">Standard Batch Size</th>
			<td><html:text property="standardBatchSize" styleClass="text_field" readonly="true"></html:text>
			</td>
			</tr>
				<tr>
			<th width="274" class="specalt" scope="row">Batch Code</th>
			<td><html:text property="batchCode" styleClass="text_field" style="width:200px;"  maxlength="10" readonly="true"></html:text>
			</td>
			</tr>
			 
			</logic:notEmpty>
			<logic:notEmpty name="standardBathcMandatory">
			<tr>
			<th width="274" class="specalt" scope="row">Standard Batch Size<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="standardBatchSize" styleClass="text_field" readonly="true"></html:text>
			</td>
			</tr>
				<tr>
			<th width="274" class="specalt" scope="row">Batch Code<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="batchCode" styleClass="text_field" style="width:200px;"  maxlength="10" readonly="true"></html:text>
			</td>
			</tr>
			
			</logic:notEmpty>
			<tr>
				<th width="274" class="specalt" scope="row">Valuation Class<img src="images/star.gif" width="8" height="8" /></th>
				<td>
				<html:select property="valuationClass" styleClass="text_field" disabled="true">
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
				</td>
			</tr>
			
			<logic:notEmpty name="manufactureNotMandatory">
			<tr>
				<th width="274" class="specalt" scope="row">Purchase Group</th>
				<td>
				<html:select property="puchaseGroupId" styleClass="text_field" disabled="true">
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
				</td>
			</tr>
			</logic:notEmpty>
			
			
			<logic:notEmpty name="manufactureMandatory">
			<tr>
				<th width="274" class="specalt" scope="row">Purchase Group<img src="images/star.gif" width="8" height="8" /></th>
				<td>
				<html:select property="puchaseGroupId" styleClass="text_field" disabled="true">
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
				</td>
			</tr>
			</logic:notEmpty>
			<logic:notEmpty name="approved">
							<tr>			
	<th colspan="2">Approver Details:</th>
	   </tr>
		<tr> <th width="274" class="specalt" scope="row">Approve Type
			<td align="left">
			
			<html:select name="finishedProductForm" property="approveType" styleClass="text_field" >
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
		
				<th width="274" class="specalt" scope="row">SAP Code No<img src="images/star.gif" width="8" height="8" /></th>
				<td>	<html:text property="sapCodeNo" styleClass="text_field" style="width:200px;" maxlength="18"></html:text></td>
			
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">SAP Code Exists<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:select property="sapCodeExists" styleClass="text_field" disabled="true">
					<html:option value="">-----Select-----</html:option>
					<html:option value="True">Yes</html:option>
					<html:option value="False">No</html:option>
				</html:select></td>
			</tr>
			<tr>	
			<th width="274" class="specalt" scope="row">SAP Creation Date<img src="images/star.gif" width="8" height="8" /></th>
			<td align="left">
				<html:text property="sapCreationDate" styleId="sapCreationDate" onfocus="popupCalender('sapCreationDate')" readonly="true" styleClass="text_field"/></td>
		
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">SAP Created By<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:text property="sapCreatedBy" styleClass="text_field" maxlength="12"></html:text>
				</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">Requested By<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:text property="requestedBy"  styleClass="text_field" style="width:500px;" maxlength="50"></html:text>
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
</div>			
</table>
</html:form>
</div>
</td>
      </tr>
      </table></td></tr>
    
</table>
</body>
</html>
