
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
<link href="style/style.css" rel="stylesheet" type="text/css" />
<link href="style/inner_tbl.css" rel="stylesheet" type="text/css" />


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


function newGeneralMaterialForm(){	   
		
				var materilaType=document.forms[0].materialCodeLists.value;

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
if(materilaType=='General Material')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial";
			document.forms[0].action=url;
			document.forms[0].submit();	
}	 	 
}

function deleteGeneralMaterial(requestNumber)
{
//var no=document.forms[0].selecteRequestNo.value;

var agree = confirm('Are You Sure To Delete Customer Record ');
if(agree)
{
	document.forms[0].action="generalMaterial.do?method=deleteGeneralMaterial&RequestNo="+requestNumber;
document.forms[0].submit();
}
}
function onSearch(){
		
		var URL="generalMaterial.do?method=searchRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}
function ClearPlace (input) {

            if (input.value == input.defaultValue) {
            document.getElementById('reqNo').style.cssText = 'color: black';
                input.value = "";
            }
        }
        function SetPlace(input) {
            if (input.value == "") {
            
            document.getElementById('reqNo').style.cssText = 'color: grey';
                input.value = input.defaultValue;
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
.style2 {	color: #df1e1c; font: bold 11px "Arial", Verdana, Arial, Helvetica, sans-serif;	font-size: 12px;
}
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

<body onload="MM_preloadImages('images/home_hover.jpg','images/news_hover.jpg','images/ess_hover.jpg','images/hr_hover.jpg',
'images/it_hover.jpg','images/timeout_hover.jpg','images/admin_hover.jpg')">
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
  
  
    <td align="center" valign="top" style="background-image:url(images/bg1.jpg); background-repeat:repeat-x;  height:180px; width:100%"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
     
    <jsp:include page="/jsp/template/header1.jsp"/>
    
    
      <tr>
        <td colspan="3" align="center" valign="top" style="padding-top:3px">
        <div class="middelpart">
        <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="24%" align="left" valign="top">
    <div class="left-blocks">
      <!--------CONTENT LEFT -------------------->
     
      <jsp:include page="/jsp/template/subMenu1.jsp"/>
     
      <!--------CALENDER ENDS -------------------->
    </div></td>
    <td colspan="2" align="left" valign="top">
    <div class="middel-blocks">
     		
<html:form action="/generalMaterial.do" enctype="multipart/form-data">
				<div align="center">
		<logic:present name="generalMaterialForm" property="message">
			<font color="red" size="3"><b><bean:write name="generalMaterialForm" property="message" /></b></font>
		</logic:present>
		<logic:present name="generalMaterialForm" property="message2">
			<font color="Green" size="3"><b><bean:write name="generalMaterialForm" property="message2" /></b></font>
		</logic:present>
	</div>

<table align="center" border="0" cellpadding="4"
					cellspacing="0" id="mytable1">
			
<tr>
	<th colspan="4">
		Material Code Request List
</th>
</tr>
	
	<tr>
	
	
		<th width="274" class="specalt" scope="row">
	Material Code List
	</th>
	<td>
	<html:select property="materialCodeLists" name="generalMaterialForm" >
	<html:option value="">Select</html:option>
	<html:option value="RAW MATERIALS">RAW MATERIALS</html:option>
	<html:option value="PACKING MATERIALS">PACKING MATERIALS</html:option>
	<html:option value="FINISHED PRODUCTS">FINISHED PRODUCTS</html:option>
	<html:option value="SEMI FINISHED">SEMI FINISHED</html:option>
	<html:option value="ZPPC">PROMOTIONAL,PRINTED & COMPLIMENTS&nbsp/&nbspZPPC</html:option>
	<html:option value="General Material">GENERAL MATERIALS &nbsp/&nbspZPSR,ZLAB,ZCIV,ZCON,ZSCR,ZITC,ZPFL</html:option>
	
	</html:select>
	</td></tr>
	<tr>
		<td align="center" colspan="2">
		<html:button property="method" value="Add New" onclick="newGeneralMaterialForm()" ></html:button>
		</td>
	
		</table>	
		<br>
		<br>					
	<table width=100%>
			
		
		<logic:notEmpty name="listOfGeneralMaterial"> 
			<tr>
		<td>
		Request Number<html:text property="requestNumber"  styleClass="text_field" styleId="reqNo" />
		Request Date<html:text property="requestsearchDate" styleId="requestDate"	onfocus="popupCalender('requestDate')"  styleClass="text_field" />
		Location<html:select name="generalMaterialForm" property="locationSearch" styleClass="text_field">
									<html:option value="">Location Name</html:option>
									<html:options name="generalMaterialForm" property="locationIdList" 
									labelProperty="locationLabelList"/>
									</html:select>
		<html:button property="method" styleClass="sudmit_btn" value="Search" onclick="onSearch()" />
		</td>
		</tr>
		<tr>
			<td valign="top" align="left">
     <display:table id="data" name="listOfGeneralMaterial" requestURI="/generalMaterial.do" pagesize="10" export="true" >
            <display:column property="requestNo" title="Request Number" href="generalMaterial.do?method=editGeneralMaterial" paramId="requestno" paramProperty="requestNo" sortable="true"  />
         <display:column property="requestDate" title="Request Date" ></display:column>
        <display:column property="locationId" title="Location Name"></display:column>
           
          <display:column title="Delete Record" ><html:button property="method" value="Delete" onclick="deleteGeneralMaterial('${data.requestNo}')"></html:button> </display:column>
        </display:table>
	
			</td></tr>
			
			</logic:notEmpty>
		
		</table>
</html:form>
		
</body>
</html>
