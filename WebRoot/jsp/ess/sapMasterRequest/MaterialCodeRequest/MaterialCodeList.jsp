<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
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
<title>eMicro :: Material Code Request </title>

<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />


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
<link href="css/displaytablestyle.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<script type="text/javascript" src="js/sorttable.js"></script>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<%--     Calender   --%>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript" src="js/jquery.datepick.js"></script>

<script type="text/javascript">
$(function() {
	$('#fromDate').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#toDate').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>

<%--     Calender   --%>

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



function onSaveColumns(){

var url="materialCode.do?method=saveColums";
	document.forms[0].action=url;
	document.forms[0].submit();


}

function getMatrialList(materilaType)
{
var materilaType=materilaType;
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

if(materilaType=='PPC')
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


function nextMaterialRecord()
{

var url="materialCode.do?method=nextMaterialRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function previousMaterialRecord()
{

var url="materialCode.do?method=previousMaterialRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function firstMaterialRecord()
{

var url="materialCode.do?method=firstMaterialRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastMaterialRecord()
{

var url="materialCode.do?method=lastMaterialRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}



function editMaterialRecord()
{


var rows=document.getElementsByName("chRequestNumber");
	
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
if(agree)
{

document.forms[0].action="materialCode.do?method=editMaterialRecord";
document.forms[0].submit();
}
}




}

function submitMaterialForm()
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


var url="materialCode.do?method=submitAllMaterilaCodeRequest"
			document.forms[0].action=url;
			document.forms[0].submit();

}


}


function deleteMaterialRecord(){
     
     
var rows=document.getElementsByName("chRequestNumber");
	
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

     
var agree = confirm('Are You Sure To Delete Selected Request');
if(agree)
{
document.forms[0].action="materialCode.do?method=deleteMaterials";
document.forms[0].submit();
      
}
}
}

function shownewMaterialForm()
{
var url="materialCode.do?method=getMaterialRecords";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}

function onClear()
{
	document.forms[0].fromDate.value="";
	document.forms[0].toDate.value="";
	
var url="materialCode.do?method=displayMaterialList";
	document.forms[0].action=url;
	document.forms[0].submit();

}

function searchMaterialRecord(){
	
	
	
	
	if(document.forms[0].materialCodeLists.value!=""||(document.forms[0].fromDate.value!="" && document.forms[0].toDate.value!="") || document.forms[0].approveStatus.value!=""|| document.forms[0].locationId.value!="")
	{
	
	 
	
	
	}else{
	alert("Please Enter Atleast One Filed.");
	   
	      return false;
	
	}
	
	if(document.forms[0].fromDate.value!="")
	{
		if(document.forms[0].toDate.value=="")
		{
		 alert("Please Select To Date");
	      document.forms[0].toDate.focus();
	      return false;
		}
		
	}
	
	if(document.forms[0].fromDate.value!="" && document.forms[0].toDate.value!="")
	{
	
	  var str1 = document.forms[0].fromDate.value;
var str2 = document.forms[0].toDate.value;
var dt1  = parseInt(str1.substring(0,2),10); 
var mon1 = parseInt(str1.substring(3,5),10); 
var yr1  = parseInt(str1.substring(6,10),10); 
var dt2  = parseInt(str2.substring(0,2),10); 
var mon2 = parseInt(str2.substring(3,5),10); 
var yr2  = parseInt(str2.substring(6,10),10); 
var date1 = new Date(yr1, mon1, dt1); 
var date2 = new Date(yr2, mon2, dt2); 

if(date2 < date1) 
{ 
    alert("Start date should be less than end date.");
    document.forms[0].endDate.value="";
     document.forms[0].endDate.focus();
     return false;
}
	
	
	}
	
	
	
	 
	var url="materialCode.do?method=searchMaterials";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
function showdiv(){
	document.getElementById("add_new").style.visibility="visible";
	
}	
function show(){
	document.getElementById("options").style.visibility="visible";
	
}	
function close_popup(){
	document.getElementById("options").style.visibility="hidden";
	
}	
	
	
function subMenuClicked2(id){
	
	var disp=document.getElementById(id);
	
	if(disp.style.display==''){
		disp.style.display='none';
		//document.forms[0].divStatus.value='none';
		
		document.getElementById('Submenu').style.display='none';
		document.getElementById('Submenu1').style.display='';
	}
	else{
		disp.style.display=''; 
		//document.forms[0].divStatus.value='';
		
		document.getElementById('Submenu1').style.display='';
		document.getElementById('Submenu').style.display='none';
		}
}

function sendMailToApproval(reqNo,materialType,location)
{


var url="materialCode.do?method=sendMailToApproval&ReqestNo="+reqNo+"&matType="+materialType+"&LoctID="+location;
			document.forms[0].action=url;
			document.forms[0].submit();	
}

function copyMaterial(reqNo,materialType)
{
var materilaType=materialType;
if(materilaType=='1')
{
var url="rawMaterial.do?method=copyNewRawMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
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


function copyMaterial(reqNo,materialType)
{
var materilaType=materialType;
if(materilaType=='RM')
{
var url="rawMaterial.do?method=copyNewRawMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='PM')
{
var url="packageMaterial.do?method=copyNewPackageMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='BULK')
{
var url="semifinished.do?method=copyNewSemiFinished&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='FG'||materilaType=='HAWA')
{
var url="finishedProduct.do?method=copyNewFinishedProduct&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}


if(materilaType=='PPC')
{
var url="promotional.do?method=copyNewPromotional&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}

if(materilaType=='OSE')
{
var url="zpsr.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='LC')
{
var url="generalMaterial.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZCIV')
{
var url="generalMaterial.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZCON')
{
var url="generalMaterial.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZSCR')
{
var url="generalMaterial.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZITC')
{
var url="generalMaterial.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='ZPFL')
{
var url="generalMaterial.do?method=copyNewGeneralMaterial&RequestNo="+reqNo+"&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}

}

function viewMaterial(reqNo,materialType,location)
{

var url="materialCode.do?method=ViewMaterialrecord&ReqestNo="+reqNo+"&matType="+materialType+"&LoctID="+location;
			document.forms[0].action=url;
			document.forms[0].submit();	


}

</script>

</head>

<body style="text-transform: uppercase;">
   <%
		String menuIcon=(String)request.getAttribute("MenuIcon");
		if(menuIcon==null){
		menuIcon="";
			}
			
			%>
			
			 <% 
  				UserInfo user=(UserInfo)session.getAttribute("user");
  
  			%>
  
	<html:form action="/materialCode.do" onsubmit="searchMaterialRecord(); return false;" enctype="multipart/form-data">
					<div align="center">
						<logic:notEmpty name="materialCodeForm" property="sendMessage">
						<font color="green" size="3">
							<b><bean:write name="materialCodeForm" property="sendMessage" /></b>
						</font>
						
					</logic:notEmpty>
           
					</div>
					<div align="center">
						<logic:present name="materialCodeForm" property="message2">
						<font color="red" size="3">
							<b><bean:write name="materialCodeForm" property="message2" /></b>
						</font>
						
					</logic:present>
           
					</div>
	<div style="width: 90%">	
	<table class="bordered" width="90%">
		<html:hidden property="requestNumber"/>
		<tr>
			<th colspan="5"><center><big>Material Code Request</big></center></th>
		</tr>

		<tr>
			<th>Material Type</th>
			<td>
				<html:select property="materialCodeLists" name="materialCodeForm" styleClass="text_field">
					<html:option value="">--Select--</html:option>
			<html:options name="materialCodeForm" property="materTypeIDList" 
									labelProperty="materialTypeIdValueList"/>
				</html:select>
			</td>  
			<th>Date</th>
			<td colspan="2">
				<html:text property="fromDate" styleId="fromDate"  title="From Date"></html:text>
		 		 - 
		 		<html:text property="toDate"  styleId="toDate"   title="To Date"></html:text>
		 	</td>
		</tr>
		<tr>	
			<th>Status</th>
			<td>
				<html:select property="approveStatus">
					<html:option value="">--Select--</html:option>
					<html:option value="Created">Created</html:option>
					<html:option value="Submited">Submited</html:option>
					<html:option value="In Process">In Process</html:option>
					<html:option value="rejected">Rejected</html:option>
						<html:option value="Completed">Completed</html:option>
					<html:option value="deleted">Deleted</html:option>
				</html:select>
			</td>
			<th>Plant</th>  
			<td>
	 			<html:select property="locationId" styleClass="text_field">
	 				<html:option value="">--Select--</html:option>
	  				<html:options property="locationIdList" labelProperty="locationLabelList" ></html:options>   
	 			</html:select>
			</td>
			<td rowspan="2" align="center"><a href="#"><img src="images/search.png" align="absmiddle" title="Search..." onclick="searchMaterialRecord()"/></a>
			
			<a href="#"><img src="images/clearsearch.jpg"  onclick="onClear()" align="absmiddle" title="Clear Search."/></a>
			
			</td>
		</tr>
	</table>
	</div>
	
	<br/>
	<br/>
	
	<table align="left">
		<tr>
	 		<td>
	  			<html:button property="method"  value="New" onclick="shownewMaterialForm()" styleClass="rounded" style="width: 80px;"/>&nbsp;&nbsp;
	  			<html:button property="method" value="Delete" onclick="deleteMaterialRecord();" styleClass="rounded" style="width: 80px;"></html:button>&nbsp;&nbsp;
	  			<html:button property="method" value="Submit For Approval" onclick="submitMaterialForm();" styleClass="rounded" style="width: 120px;"></html:button>&nbsp;&nbsp;
	  			
	 		<br/>
	 		<br/> 		

	 	<logic:notEmpty name="displayRecordNo">
	 
	  	<td>
	  	<img src="images/First10.jpg" onclick="firstMaterialRecord()" align="absmiddle"/>
	
	
	<logic:notEmpty name="disablePreviousButton">

	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousMaterialRecord()" align="absmiddle"/>

	</logic:notEmpty>
	
	<bean:write property="startRecord"  name="materialCodeForm"/>-
	
	<bean:write property="endRecord"  name="materialCodeForm"/>

	<logic:notEmpty name="nextButton">

	<img src="images/Next1.jpg" onclick="nextMaterialRecord()" align="absmiddle"/>

	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	
	<img src="images/disableRight.jpg" align="absmiddle"/>

	
	</logic:notEmpty>
	
		<img src="images/Last10.jpg" onclick="lastMaterialRecord()" align="absmiddle"/>
	
	</td>
	
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>

	

	
	</logic:notEmpty>
	 </tr>
	 </table>

<table align="left">
<tr>
<td>

	<logic:notEmpty name="listOfMaterials"> 
	
	<!-- User Select Option -->
	<div class="bordered">
		<table class="sortable">
			<tr>
					<th style="width:50px;">Req No</th><th style="width:100px;">Request Date</th><th style="width:150px;">Material Type</th><th >Material Name</th>
					<th style="width:50px;">Plant</th><th style="width:50px;">Status</th><th style="width:50px;">Print</th>
					<th style="width:50px;">Delete</th><th style="width:50px;">Edit</th><th>Select</th><th style="width:50px;">Submit For Approval</th><th>Copy</th><th>View</th>
				</tr>
				<logic:iterate id="materList" name="listOfMaterials">
		<tr>
				<td><bean:write name="materList" property="requestNumber"/></td>
						<td><bean:write name="materList" property="requestDate"/></td>
						<td><bean:write name="materList" property="mType"/></td>
						<td><font style="text-transform:uppercase;"> <bean:write name="materList" property="materialShortName" /></font></td>
							<td><bean:write name="materList" property="locationId"/></td>
						<td><bean:write name="materList" property="approveType"/></td>
						
						<td>
						<logic:equal value="RM" property="reqMatType" name="materList">
							<a href="${materList.reportUrl}&requstNo=${materList.requestNumber}">Print</a>
						</logic:equal>
						<logic:equal value="PM" property="reqMatType" name="materList">
							<a href="${materList.reportUrl}&requstNo=${materList.requestNumber}">Print</a>
						</logic:equal>
						</td>
						<logic:equal value="Created" property="approveType" name="materList">	
						<td><html:checkbox property="chRequestNumber" value="${materList.requestNumber}" ></html:checkbox></td>
						<td><a href="${materList.url}&requstNo=${materList.requestNumber}&MAT_TYPE=${materList.reqMatType}"><img src="images/edit1.jpg"/></a></td>
						 <td><html:checkbox property="getReqno" value="${materList.requestNumber}" ></html:checkbox></td>
						<td><html:button property="method" value="Submit" title="Submit For Approval" styleClass="rounded"  onclick="sendMailToApproval('${materList.requestNumber}','${materList.reqMatType}','${materList.locationId }')"></html:button></td>
						</logic:equal>
						<logic:equal value="Rejected" property="approveType" name="materList">					
								<td>&nbsp;</td>
							<%-- 	<td><a href="${materList.url}&requstNo=${materList.requestNumber}&MAT_TYPE=${materList.reqMatType}"><img src="images/edit1.jpg"/></a></td>
								 <td><html:checkbox property="getReqno" value="${materList.requestNumber}" ></html:checkbox></td>
				 --%>
				 
				 	<td>&nbsp;</td>
								 <td>&nbsp;</td>
				
					<td>&nbsp;</td>	
						</logic:equal>					
					<logic:equal value="In Process" property="approveType" name="materList">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
						<td>&nbsp;</td>
					</logic:equal>
					
						<logic:equal value="Approved" property="approveType" name="materList">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
						<td>&nbsp;</td>
					</logic:equal>
					<logic:equal value="Submited" property="approveType" name="materList">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					</logic:equal>
					<logic:equal value="Completed" property="approveType" name="materList">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					</logic:equal>
					
					<logic:equal value="deleted" property="approveType" name="materList">
					<td>&nbsp;</td>
					<td><a href="${materList.url}&requstNo=${materList.requestNumber}&MAT_TYPE=${materList.reqMatType}"><img src="images/edit1.jpg"/></a></td>
					<td>&nbsp;</td>	
				<td>&nbsp;</td>	
					</logic:equal>
							
						<td> <a href="#"><img src="images/copy.png" width="28" height="28" onclick="copyMaterial('${materList.requestNumber}','${materList.reqMatType}')"/></a> </td>
						<td id="${abc.requestNumber}">
      						
      						<a href="#">
      							<img src="images/view.gif" height="28" width="28" title="View Record" onclick="viewMaterial('${materList.requestNumber}','${materList.reqMatType}','${materList.locationId }')"/></a>
      						</td>
      						
	</tr>
	</logic:iterate>							
	
			</logic:notEmpty>
			
<logic:notEmpty name="noRecords">
<div class="bordered">

<table class="sortable">
			<tr>
					<th style="width:50px;">Req No</th><th style="width:100px;">Request Date</th><th style="width:100px;">Material Type</th><th >Material Name</th>
					<th style="width:50px;">Plant</th><th style="width:50px;">Status</th><th style="width:50px;">Report</th>
					<th style="width:50px;">Delete</th><th style="width:50px;">Edit</th><th style="width:50px;">Select</th><th style="width:50px;">Submit For Approval</th><th>Copy</th>
				</tr>
				</table>
					</div>
					
					<div align="center">
						<logic:present name="materialCodeForm" property="message">
						<font color="red" size="3">
							<bean:write name="materialCodeForm" property="message" />
						</font>
						
					</logic:present>
           
					</div>
						
					</table>
					 
	

</logic:notEmpty>

			
</tr>
</table>

	</html:form>
	
</body>
</html>
