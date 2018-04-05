
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
<title>Customer Master List</title>
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />


	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<script type="text/javascript" src="js/sorttable.js"></script>
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
	  
function copycustomer(requestNo){
var url="customerMaster.do?method=copyCustomerRecord&RequestNo="+requestNo;
			document.forms[0].action=url;
			document.forms[0].submit();
}

function nextMaterialRecord()
{
var url="customerMaster.do?method=nextCustomerRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function previousMaterialRecord()
{
var url="customerMaster.do?method=previousCustomerRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}
function firstMaterialRecord()
{
var url="customerMaster.do?method=firstCustomerRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function lastMaterialRecord()
{
var url="customerMaster.do?method=lastCustomerRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function onSearch(){
		var URL="customerMaster.do?method=searchRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function newCustomerForm(){	   
			var url="customerMaster.do?method=getCustomerRecords";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}

function deleteCustomerRecord()
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
	document.forms[0].action="customerMaster.do?method=deleteCustomerRecord";
document.forms[0].submit();
}
}
function onSearch(){
		
		var URL="customerMaster.do?method=searchRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}

function onClear()
{
document.forms[0].searchRequired.value="";
var URL="customerMaster.do?method=displayCustomerList";
		document.forms[0].action=URL;
 		document.forms[0].submit();

}

function onSearchRequired()
{

if(document.forms[0].searchRequired.value=="")
{
alert("Please Enter Some Keyword");
document.forms[0].searchRequired.focus();
return false;
}

var URL="customerMaster.do?method=getRequiredSearch";
		document.forms[0].action=URL;
 		document.forms[0].submit();

}
function sendMailToApproval(RequestNo)
{
var URL="customerMaster.do?method=sendMailToApprove&ReqNo="+RequestNo;
		document.forms[0].action=URL;
 		document.forms[0].submit();

}


function submitCustForm()
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


var url="customerMaster.do?method=SubmitAllCustomer"
			document.forms[0].action=url;
			document.forms[0].submit();

}


}


function viewMaterial(reqNo)
{

var url="customerMaster.do?method=ViewCustomerrecord&ReqestNo="+reqNo;
			document.forms[0].action=url;
			document.forms[0].submit();	


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
  
  
   
     		
<html:form action="/customerMaster.do" enctype="multipart/form-data">
				  <div align="center">
				<logic:present name="customerMasterForm" property="message">
					<font color="red" size="3"><b><bean:write name="customerMasterForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="customerMasterForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="customerMasterForm" property="message2" /></b></font>
				</logic:present>
			</div>
<table class="sortable bordered">
<tr>
<th><center><big>Customer Master List</big></center></th></tr></table>	
			
<table align="left">
<tr>
<td>
<html:button property="method" value="New "   onclick="newCustomerForm()" styleClass="rounded" style="width: 80px;"></html:button>&nbsp;&nbsp;
		<html:button property="method" value="Delete" onclick="deleteCustomerRecord()" styleClass="rounded" style="width: 80px;"></html:button>&nbsp;&nbsp; 
		<html:button property="method" value="Submit For Approval" onclick="submitCustForm();" styleClass="rounded" style="width: 120px;"></html:button>&nbsp;&nbsp;
		
		Status&nbsp;<html:select property="approveType" styleClass="text_field">
	<html:option value="">Select</html:option>
		<html:option value="Created">Created</html:option>
		<html:option value="Submited">Submited</html:option>
	<html:option value="In Process">In Process</html:option>
	<html:option value="Completed">Completed</html:option>
	<html:option value="Rejected">Rejected</html:option>
	<html:option value="deleted">Deleted</html:option>

	</html:select><a href="#"><img src="images/search.png"  onclick="onSearch()" align="absmiddle"/></a>
	
	<a href="#"><img src="images/clearsearch.jpg"  onclick="onClear()" align="absmiddle"/></a>
	<html:text property="searchRequired" styleClass="rounded" style="width:200px;" title="Customer Name,Place,Requested By,Plant,Status"/>
	<a href="#"><img src="images/search.png"  onclick="onSearchRequired()" align="absmiddle"/></a>
</td>

	&nbsp;&nbsp;
	
	 	<logic:notEmpty name="displayRecordNo">
	
	  	<td>
	  	<img src="images/First10.jpg" onclick="firstMaterialRecord()" align="absmiddle"/>
	
	</td>
	
	<logic:notEmpty name="disablePreviousButton">
	<td>
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	</td>
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	<td>
	
	<img src="images/previous1.jpg" onclick="previousMaterialRecord()" align="absmiddle"/>
	</td>
	</logic:notEmpty>
	
	<td>
	<td>
	<bean:write property="startRecord"  name="customerMasterForm"/>-
	</td>
	<td>
	<bean:write property="endRecord"  name="customerMasterForm"/>
	</td>
	<logic:notEmpty name="nextButton">
	<td>
	<img src="images/Next1.jpg" onclick="nextMaterialRecord()"/>
	</td>
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	<td>
	
	<img src="images/disableRight.jpg" align="absmiddle" />
	</td>
	
	</logic:notEmpty>
		<td>
		<img src="images/Last10.jpg" onclick="lastMaterialRecord()" align="absmiddle"/>
	</td>
	</td>
	
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	</logic:notEmpty>
	 </tr>
	 </table>
	 <br/>
		<logic:notEmpty name="customerList"> 
	<div class="bordered">
		<table class="sortable">
			<tr>
					<th style="width:50px;">Req No</th><th style="width:100px;">Request Date</th><th style="width:200px;">Customer Name</th><th >Place</th>
					<th style="width:100px;">Requested By</th><th style="width:100px;">Department</th><th style="width:100px;">Plant</th>
					<th style="width:100px;">Approve Status</th><th style="width:50px;">Delete</th><th style="width:50px;">Edit</th><th>Select</th><th style="width:50px;">Submit For Approval</th>
				   <th>Copy</th><th>View</th>
				</tr>
		<logic:iterate id="custList" name="customerList">
				<tr>
						<td><bean:write name="custList" property="requestNumber"/></td>
						<td><bean:write name="custList" property="requestDate"/></td>
						<td><bean:write name="custList" property="customerName"/></td>
						<td><bean:write name="custList" property="city"/></td>
							<td><bean:write name="custList" property="requestedBy"/></td>
						<td><bean:write name="custList" property="department"/></td>
						<td><bean:write name="custList" property="locationId"/></td>
						<td><bean:write name="custList" property="approveType"/></td>
						
						<logic:equal value="Created" property="approveType" name="custList">					
						<td><html:checkbox property="requiredRequestNumber" value="${custList.requestNumber}" /> </td>	
						<td><a href="customerMaster.do?method=editCustomerRecord&requstNo=${custList.requestNumber}"><img src="images/edit1.jpg"/></a></td>
						 <td><html:checkbox property="getReqno" value="${custList.requestNumber}" ></html:checkbox></td>
					<td><html:button property="method" value="Submit" title="Submit For Approval" styleClass="rounded"  onclick="sendMailToApproval('${custList.requestNumber}')"></html:button></td>
					</logic:equal>
					<logic:equal value="Rejected" property="approveType" name="custList">					
						<td><html:checkbox property="requiredRequestNumber" value="${custList.requestNumber}" /> </td>	
						<td><a href="customerMaster.do?method=editCustomerRecord&requstNo=${custList.requestNumber}"><img src="images/edit1.jpg"/></a></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					</logic:equal>
					<logic:equal value="Submited" property="approveType" name="custList">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
						<td>&nbsp;</td>
					</logic:equal>
					<logic:equal value="In Process" property="approveType" name="custList">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
						<td>&nbsp;</td>
					</logic:equal>
						<logic:equal value="Approved" property="approveType" name="custList">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
						<td>&nbsp;</td>
					<td>&nbsp;</td>
					</logic:equal>
						<logic:equal value="Completed" property="approveType" name="custList">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					</logic:equal>
					<logic:equal value="deleted" property="approveType" name="custList">
					<td>&nbsp;</td>
					<td><a href="customerMaster.do?method=editCustomerRecord&requstNo=${custList.requestNumber}"><img src="images/edit1.jpg"/></a></td>
					<td>&nbsp;</td>	
						<td>&nbsp;</td>
			
					</logic:equal>
					<td><a href="# " title="copy" onclick="copycustomer('${custList.requestNumber}')"><img src="images/copy.png" height="28" width="28"  /></a></td>
						<td id="${custList.requestNumber}">
      						
      						<a href="#">
      							<img src="images/view.gif" height="28" width="28" title="View Record" onclick="viewMaterial('${custList.requestNumber}')"/></a>
      						</td>
					
                    		</tr>
    </logic:iterate>
   
			</logic:notEmpty>
			
		</table>
 </div>

 <logic:notEmpty name="noRecords">
  <br/> <br/>
 <table class="sortable bordered">
			<tr>
					<th style="width:50px;">Req No</th><th style="width:100px;">Request Date</th><th style="width:200px;">Customer Name</th><th >Place</th>
					<th style="width:100px;">Requested By</th><th style="width:100px;">Department</th><th style="width:100px;">Plant</th>
					<th style="width:100px;">Approve Status</th><th style="width:50px;">Delete</th><th style="width:50px;">Edit</th><th>Select</th><th style="width:50px;">Submit For Approval</th>
			         <th>Copy</th><th>View</th>
				</tr>
				</table>
				<div align="center">
						<logic:present name="customerMasterForm" property="message1">
						<font color="red">
							<bean:write name="customerMasterForm" property="message1" />
						</font>
					</logic:present>
					</div>
 </logic:notEmpty>
</html:form>
		
		
</body>
</html>
