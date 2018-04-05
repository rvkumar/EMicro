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
<title>eMicro :: Material Code Extension</title>

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


<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />

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

function saveAndSubmit()
{


if(document.forms[0].plantType[0].checked == false && document.forms[0].plantType[1].checked == false)

	    {
	      alert("Please Select Plant or Storage Location");
	      document.forms[0].locationId.focus();
	      return false;
	    }
	    
if(document.forms[0].plantType[0].checked == true)
{

	if(document.forms[0].materialCode1.value=="")
	{
	alert("Please Enter Material code");
	      document.forms[0].materialCode1.focus();
	      return false;
	}
	if(document.forms[0].plant1.value=="")
	{
	alert("Please Select Extend From Plant");
	      document.forms[0].plant1.focus();
	      return false;
	}
	if(document.forms[0].extendToPlant1.value=="")
	{
	alert("Please Select Extend To Plant");
	      document.forms[0].extendToPlant1.focus();
	      return false;
	}
	if(document.forms[0].storageLocationId1.value=="")
	{
	alert("Please Select Extend From Storage Location");
	      document.forms[0].storageLocationId1.focus();
	      return false;
	}
	if(document.forms[0].extendToStorageLocation1.value=="")
	{
	alert("Please Select Extend From Storage Location");
	      document.forms[0].extendToStorageLocation1.focus();
	      return false;
	}

}
if(document.forms[0].plantType[1].checked==true)
{
	if(document.forms[0].materialCode2.value=="")
	{
	alert("Please Enter Material code");
	      document.forms[0].materialCode2.focus();
	      return false;
	}
	if(document.forms[0].plant2.value=="")
	{
	alert("Please Select Extend From Plant");
	      document.forms[0].plant2.focus();
	      return false;
	}

	if(document.forms[0].fromStorageLocation.value=="")
	{
	alert("Please Select From Storage Location");
	      document.forms[0].fromStorageLocation.focus();
	      return false;
	}
	if(document.forms[0].toStorageLocation.value=="")
	{
	alert("Please Select To Storage Location");
	      document.forms[0].toStorageLocation.focus();
	      return false;
	}
}	    

 var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";

var url="materialCodeExtenstion.do?method=saveAndSubmitMaterial";
			document.forms[0].action=url;
			document.forms[0].submit();



}


function saveData(){


if(document.forms[0].plantType[0].checked == false && document.forms[0].plantType[1].checked == false)

	    {
	      alert("Please Select Plant or Storage Location");
	      document.forms[0].locationId.focus();
	      return false;
	    }
	    
if(document.forms[0].plantType[0].checked == true)
{

	if(document.forms[0].materialCode1.value=="")
	{
	alert("Please Enter Material code");
	      document.forms[0].materialCode1.focus();
	      return false;
	}
	if(document.forms[0].plant1.value=="")
	{
	alert("Please Select Extend From Plant");
	      document.forms[0].plant1.focus();
	      return false;
	}
	if(document.forms[0].extendToPlant1.value=="")
	{
	alert("Please Select Extend To Plant");
	      document.forms[0].extendToPlant1.focus();
	      return false;
	}
	if(document.forms[0].storageLocationId1.value=="")
	{
	alert("Please Select Extend From Storage Location");
	      document.forms[0].storageLocationId1.focus();
	      return false;
	}
	if(document.forms[0].extendToStorageLocation1.value=="")
	{
	alert("Please Select Extend From Storage Location");
	      document.forms[0].extendToStorageLocation1.focus();
	      return false;
	}

}
if(document.forms[0].plantType[1].checked==true)
{
	if(document.forms[0].materialCode2.value=="")
	{
	alert("Please Enter Material code");
	      document.forms[0].materialCode2.focus();
	      return false;
	}
	if(document.forms[0].plant2.value=="")
	{
	alert("Please Select Extend From Plant");
	      document.forms[0].plant2.focus();
	      return false;
	}

	if(document.forms[0].fromStorageLocation.value=="")
	{
	alert("Please Select From Storage Location");
	      document.forms[0].fromStorageLocation.focus();
	      return false;
	}
	if(document.forms[0].toStorageLocation.value=="")
	{
	alert("Please Select To Storage Location");
	      document.forms[0].toStorageLocation.focus();
	      return false;
	}
}	    

 var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";

var url="materialCodeExtenstion.do?method=saveData";
			document.forms[0].action=url;
			document.forms[0].submit();


}
function getPlant(){
		
		
		var url="materialCodeExtenstion.do?method=getPlantDetails";
			document.forms[0].action=url;
			document.forms[0].submit();
		
}
function getStorage(){
		
		
			var url="materialCodeExtenstion.do?method=getStorageLocationDetails";
			document.forms[0].action=url;
			document.forms[0].submit();
		
}

function closeData(){
var url="materialCodeExtenstion.do?method=displayCodeExtenstionList";
			document.forms[0].action=url;
			document.forms[0].submit();
}


function searchStorageLocation()
{


var url="materialCodeExtenstion.do?method=getPlantDetails";
			document.forms[0].action=url;
			document.forms[0].submit();


}

function searchStorageLocation1()
{


var url="materialCodeExtenstion.do?method=getStorageLocationDetails";
			document.forms[0].action=url;
			document.forms[0].submit();


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
		String menuIcon=(String)request.getAttribute("MenuIcon");
		if(menuIcon==null){
			menuIcon="";
			}
	%>
			
	<% 
  		UserInfo user=(UserInfo)session.getAttribute("user");
	%>

	<div align="center">
		<logic:present name="materialCodeExtenstionForm" property="massage1">
			<font color="red" size="3"><b><bean:write name="materialCodeExtenstionForm" property="massage1" /></b></font>
		</logic:present>
		<logic:present name="materialCodeExtenstionForm" property="massage2">
			<font color="Green" size="3"><b><bean:write name="materialCodeExtenstionForm" property="massage2" /></b></font>
		</logic:present>
	</div>

	<html:form action="/materialCodeExtenstion.do" enctype="multipart/form-data">
<div id="masterdiv" class="">
	<div style="width: 80%">
	<table class="bordered" width="80%">
		<tr>
	 		<th colspan="4"><center><big>Material Code Extension Request Form </big></center></th>
  		</tr>
  
  		<tr>
			<td>Request No <font color="red">*</font></td>
			<td>
				<html:text property="requestNo" readonly="true" maxlength="15" size="15" />
				<html:hidden property="actionType"/>
			</td>
			<td>Request Date <font color="red">*</font></td>
			<td>
				<html:text property="requestDate" readonly="true" />
			</td>
		</tr>

		<tr>
			<td>Select Type</td>
			<td colspan="3">Plant <html:checkbox property="plantType" value="Plant" onclick="getPlant()"  style="width :25px;"/>
				&nbsp;&nbsp;
				Storage Location <html:checkbox property="plantType" value="Storage Location" onclick="getStorage()"  style="width :25px;"/>
			</td>
		</tr>

		<logic:notEmpty name="plantMandatory">
			<tr>
				<th colspan="4"><big>Plant Details</big></th>
   			</tr>

	   		<tr>
				<td>Material Code <font color="red">*</font></td>
				<td colspan="3"><html:text property="materialCode1"  maxlength="20" size="20"/>
		<html:button property="method"  value="Continue" onclick="searchStorageLocation()" styleClass="rounded" style="width: 100px" ></html:button>
			</td>
			
			</tr>

			<tr>
				<td>Extend From Plant <font color="red">*</font></td>
				<td><html:select name="materialCodeExtenstionForm" property="plant1" styleClass="content" >
						<html:option value="">--Select--</html:option>
						<html:options name="materialCodeExtenstionForm" property="locationIdList" labelProperty="locationLabelList"/>
					</html:select>
				</td>
			 	<td>Extend To Plant <font color="red">*</font></td>
				<td><html:select name="materialCodeExtenstionForm" property="extendToPlant1" styleClass="content" >
						<html:option value="">--Select--</html:option>
						<html:options name="materialCodeExtenstionForm" property="locationIdList1" labelProperty="locationLabelList1"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Extend From Storage Location <font color="red">*</font></td>
				<td><html:select  property="storageLocationId1" styleClass="content" >
						<html:option value="">--Select--</html:option>
						<html:options name="materialCodeExtenstionForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
				</td>
				<td>Extend To Storage Location <font color="red">*</font></td>
				<td><html:select  property="extendToStorageLocation1" styleClass="content">
						<html:option value="">--Select--</html:option>
						<html:options name="materialCodeExtenstionForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
				</td>
			</tr>	
		</logic:notEmpty>	
			
		<logic:notEmpty name="storageMandatory">
			<tr>
	 			<th colspan="4"><big>Storage Location Details</big></th>
   			</tr>

			<tr>
				<td>Material Code <font color="red">*</font></td>
				<td><html:text property="materialCode2" maxlength="20" size="20"/>
						<html:button property="method"  value="Continue" onclick="searchStorageLocation1()" styleClass="rounded" style="width: 75px" ></html:button>
			</td>
				
				<td>Extend From Plant <font color="red">*</font></td>
				<td><html:select name="materialCodeExtenstionForm" property="plant2" styleClass="content" >
						<html:option value="">--Select--</html:option>
						<html:options name="materialCodeExtenstionForm" property="locationIdList1" labelProperty="locationLabelList1"/>
					</html:select>
				</td>
			</tr>
			
			<tr>
				<td>From Storage Location <font color="red">*</font></td>
				<td><html:select  property="fromStorageLocation" styleClass="content">
						<html:option value="">--Select--</html:option>
						<html:options name="materialCodeExtenstionForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
				</td>
				<td>To Storage Location <font color="red">*</font></td>
				<td><html:select  property="toStorageLocation" styleClass="content">
						<html:option value="">--Select--</html:option>
						<html:options name="materialCodeExtenstionForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
				</td>
			</tr>
		</logic:notEmpty>
   
		<tr>
 			<td colspan="4" style="text-align: center;">
	 			<logic:notEmpty name="saveButton">
					<html:button property="method"  value="Save" onclick="saveData()" styleClass="rounded" style="width: 100px" ></html:button>
					<html:button property="method"  value="Save & Submit" onclick="saveAndSubmit()" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
					<html:reset value="Reset" styleClass="rounded" style="width: 100px"></html:reset>	
				</logic:notEmpty>
		
				<html:button property="method" value="Close" onclick="closeData()" styleClass="rounded" style="width: 100px" ></html:button>
			</td>
   		</tr>
   </table>
   </div>			
   	</div>
</html:form>

</body>
</html>
