
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
<title>Code Extension List</title>
<link href="style1/style.css" rel="stylesheet" type="text/css" />


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
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<script type="text/javascript" src="js/sorttable.js"></script>
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

function getCodeExtensionForm(){

	var url="materialCodeExtenstion.do?method=displayExtensionForm";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function deleteExtensionRecord()
{

var rows=document.getElementsByName("requiredRequestNumber");
	
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
alert('please select atleast one record');
}
else
{

	document.forms[0].action="materialCodeExtenstion.do?method=deleteExtenstionRecord";
document.forms[0].submit();
}
}
function onSearch(){
		
		var URL="materialCodeExtenstion.do?method=getSearchRecords";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}
function nextMaterialRecord()
{

var url="materialCodeExtenstion.do?method=nextRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function previousMaterialRecord()
{

var url="materialCodeExtenstion.do?method=previousRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function firstMaterialRecord()
{

var url="materialCodeExtenstion.do?method=firstRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastMaterialRecord()
{

var url="materialCodeExtenstion.do?method=lastRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function sendMailToApproval(reqNo)
{


var url="materialCodeExtenstion.do?method=sendMailToApproval&ReqestNo="+reqNo;
			document.forms[0].action=url;
			document.forms[0].submit();	
}

function submitCodeExtForm()
{

var rows=document.getElementsByName("getReqno");
	
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
alert('please select atleast one record');
}
else
{


var url="materialCodeExtenstion.do?method=SubmitAllCodeExt"
			document.forms[0].action=url;
			document.forms[0].submit();

}


}

</script>

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
   <%
			
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			
			if(menuIcon==null){
			menuIcon="";
			}
			
			%>
			
			 <% 
  				UserInfo user=(UserInfo)session.getAttribute("user");
  
  			%>
  
  
     		
<html:form action="/materialCodeExtenstion.do" enctype="multipart/form-data">
					<div align="center">
		<logic:present name="materialCodeExtenstionForm" property="massage1">
			<font color="red" size="3"><b><bean:write name="materialCodeExtenstionForm" property="massage1" /></b></font>
		</logic:present>
		<logic:present name="materialCodeExtenstionForm" property="massage2">
			<font color="Green" size="3"><b><bean:write name="materialCodeExtenstionForm" property="massage2" /></b></font>
		</logic:present>
	</div>

<table class="sortable bordered">
<tr>
<th><center><big>Material Code Extention List</big></center></th></tr></table>
<br/>
<table align="left" border="0"  >
			

	<tr>
	<td>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
		<html:button property="method" value="New" onclick="getCodeExtensionForm()" styleClass="rounded" style="width: 80px;"/>&nbsp;
		<html:button property="method" value="Delete" onclick="deleteExtensionRecord()" styleClass="rounded" style="width: 80px;"/>&nbsp;
				<html:button property="method" value="Submit For Approval" onclick="submitCodeExtForm();" styleClass="rounded" style="width: 120px;"></html:button>&nbsp;&nbsp;
		
		Status&nbsp;<html:select property="approveType" styleClass="text_field">
	<html:option value="">Select</html:option>
	 <html:option value="Saved">Saved</html:option>
	    <html:option value="In Process">In Process</html:option>
	    <html:option value="Approved">Approved</html:option>
	    <html:option value="Rejected">Rejected</html:option>
	    	<html:option value="deleted">Deleted</html:option>
	</html:select>
	<a href="#"><img src="images/search.png"  onclick="onSearch()" align="absmiddle"/></a>
	</td>
</tr>
		</table>
<table align="center">
	 	<logic:notEmpty name="displayRecordNo">
	 <tr>
	  	<td>
	  	<img src="images/First10.jpg" onclick="firstMaterialRecord()"/>
	
	</td>
	
	<logic:notEmpty name="disablePreviousButton">
	<td>
	
	<img src="images/disableLeft.jpg" />
	</td>
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	<td>
	
	<img src="images/previous1.jpg" onclick="previousMaterialRecord()"/>
	</td>
	</logic:notEmpty>
	
	<td>
	<td>
	<bean:write property="startRecord"  name="materialCodeExtenstionForm"/>-
	</td>
	<td>
	<bean:write property="endRecord"  name="materialCodeExtenstionForm"/>
	</td>
	<logic:notEmpty name="nextButton">
	<td>
	<img src="images/Next1.jpg" onclick="nextMaterialRecord()"/>
	</td>
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	<td>
	
	<img src="images/disableRight.jpg" />
	</td>
	
	</logic:notEmpty>
		<td>
		<img src="images/Last10.jpg" onclick="lastMaterialRecord()"/>
	</td>

	
	
	</logic:notEmpty>
	 </tr>
	 </table>
			<br/>
			<br/>
			
 <logic:notEmpty name="listOfMaterials"> 
	 
    
    
      <table class="sortable bordered">
			<tr>
				<th>&nbsp;</th>	<th style="width:50px;">Req No</th><th style="width:100px;">Type</th><th style="width:100px;">Material</th><th style="width:100px;">Description</th><th style="width:200px;">From Storage Location</th><th >To Storage Location</th>
					<th style="width:100px;">Status</th><th>Print</th><th>View</th><th>Edit</th><th style="width:50px;">Select</th><th style="width:50px;">Submit For Approval</th>
				  
				</tr>
				
				<logic:iterate id="mytable1" name="listOfMaterials">
				<tr>
				
				<td><html:checkbox property="requiredRequestNumber" value="${mytable1.requestNo}" ></html:checkbox></td>
				<td>${mytable1.requestNo}</td>
				<td>${mytable1.plantType}</td>
				<td>${mytable1.materialCode1}</td>
				<td><font style="text-transform:uppercase;">${mytable1.description}</font></td>
				<td>${mytable1.storageLocationId1}</td>
				<td>${mytable1.extendToStorageLocation1}</td>
				<td>${mytable1.approveType}</td>
				
				<td><a href="materialCodeExtenstion.do?method=getReport&requstNo=${mytable1.requestNo}&MaterialType=${mytable1.plantType}">Print</a></td>
				<td><a href="materialCodeExtenstion.do?method=viewCodeExtension&requstNo=${mytable1.requestNo}&planttype=${mytable1.plantType}"><img src="images/view.gif" height="25" width="25"/></a></td>
				<logic:equal value="Saved" property="approveType" name="mytable1">
				<td><a href="materialCodeExtenstion.do?method=editCodeExtension&requstNo=${mytable1.requestNo}"><img src="images/edit1.jpg"/></a></td>
				
			 <td><html:checkbox property="getReqno" value="${mytable1.requestNo}" ></html:checkbox></td>
				<td><html:button property="method" value="Submit" title="Submit For Approval" styleClass="rounded"  onclick="sendMailToApproval('${mytable1.requestNo}')"></html:button></td>
				</logic:equal>
				<logic:equal value="In Process" property="approveType" name="mytable1">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
					<td>&nbsp;</td>
				</logic:equal>
				<logic:equal value="Approved" property="approveType" name="mytable1">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
					<td>&nbsp;</td>
				</logic:equal>
				
				<logic:equal value="Rejected" property="approveType" name="mytable1">
				<td><a href="materialCodeExtenstion.do?method=editCodeExtension&requstNo=${mytable1.requestNo}"><img src="images/edit1.jpg"/></a></td>
				<td>&nbsp;</td>
					<td>&nbsp;</td>
				</logic:equal>
				<logic:equal value="deleted" property="approveType" name="mytable1">
			
					<td><a href="materialCodeExtenstion.do?method=editCodeExtension&requstNo=${mytable1.requestNo}"><img src="images/edit1.jpg"/></a></td>
					<td>&nbsp;</td>	
			
					</logic:equal>
				</tr>
				
				</logic:iterate>
   
   
			</logic:notEmpty>		
		
</html:form>
		
<logic:notEmpty name="noRecords">
<table class="sortable bordered">
			<tr>
						<th style="width:50px;">Req No</th><th style="width:100px;">Type</th><th style="width:100px;">Material</th><th style="width:100px;">Description</th><th style="width:200px;">From Storage Location</th><th >To Storage Location</th>
					<th style="width:100px;">Status</th><th>Print</th><th>View</th><th>Edit</th><th style="width:50px;">Select</th><th style="width:50px;">Submit For Approval</th>
					</tr>
				<tr><td colspan="12">
				<logic:notEmpty name="materialCodeExtenstionForm" property="message">
						<font color="red">
							<center><bean:write name="materialCodeExtenstionForm" property="message"/></center>
							<br/>
						</font>
		</logic:notEmpty>
				
				</td></tr>
	</table>
					
</logic:notEmpty>	
</body>
</html>
