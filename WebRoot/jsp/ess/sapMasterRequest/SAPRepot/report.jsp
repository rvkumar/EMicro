<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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

$(function() {
	$('#fromDate1').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#toDate1').datepick({dateFormat: 'dd/mm/yyyy'});
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


</script>
<script type="text/javascript">
function searchMaterialRecord(){

	
	if(document.forms[0].searchType.value==""){
		  alert("Please Select To Request Type");
	      document.forms[0].searchType.focus();
	      return false;
	}
	
	
	
	if(document.forms[0].requestNumber.value==""){
	
	
	if(document.forms[0].fromDate.value=="" && document.forms[0].toDate.value=="")
	{
		alert("Please Select  Date");
	      document.forms[0].fromDate.focus();
	      return false;
	}
	
	
	if(document.forms[0].fromDate.value=="")
	{
	      alert("Please Select From Date");
	      document.forms[0].fromDate.focus();
	      return false;
	}
	
	
	if(document.forms[0].toDate.value=="")
	{
		alert("Please Select To Date");
	      document.forms[0].toDate.focus();
	      return false;
	}
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
	
	/* if(document.forms[0].fromDate1.value!="")
	{
		if(document.forms[0].toDate1.value=="")
		{
		 alert("Please Select To Date");
	      document.forms[0].toDate1.focus();
	      return false;
		}
		
	} */
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
	
	
/* 	if(document.forms[0].fromDate1.value!="" && document.forms[0].toDate1.value!="")
	{
	
	var str1 = document.forms[0].fromDate1.value;
	var str2 = document.forms[0].toDate1.value;
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
    document.forms[0].endDate1.value="";
    document.forms[0].endDate1.focus();
    return false;
	}
	
	
	} */
	
	/* if(document.forms[0].req_Status.value=="")
	{
	 alert("Please Select the Request Status");
	      document.forms[0].req_Status.focus();
	      return false;
	} */
	var url="sapReport.do?method=searchMaterials";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	
function nextMaterialRecord()
{

var url="sapReport.do?method=nextMaterialRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function previousMaterialRecord()
{

var url="sapReport.do?method=previousMaterialRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function firstMaterialRecord()
{

var url="sapReport.do?method=searchMaterials";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastMaterialRecord()
{

var url="sapReport.do?method=lastMaterialRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}	

function exportToExcel(){
	var url="sapReport.do?method=exportToExcel";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function getdata(reqno,type)
{
window.showModalDialog("sapReport.do?method=getmatDetails&reqno="+reqno+"&matType="+type ,null, "dialogWidth=950px;dialogHeight=750px; center:yes");
}

function clearData(){
	document.forms[0].searchType.value="";
	document.forms[0].fromDate.value="";
	document.forms[0].toDate.value=""; 
	document.forms[0].locationId.value="";
	document.forms[0].materialCodeLists.value="";
	document.forms[0].fromDate1.value="";
	document.forms[0].toDate1.value="";
	document.forms[0].requestNumber.value="";
	document.forms[0].req_Status.value="";
	document.forms[0].approver.value=""; 
	

}
function test1(){
	var url="sapReport.do?method=display";
	document.forms[0].action=url;
	document.forms[0].submit();
}
</script>
</head>

<body >
<html:form action="/sapReport.do"  enctype="multipart/form-data">





	<table class="bordered" >
		<tr>
			<th colspan="6"><center><big>Reports</big></center></th>
		</tr>
       
		<tr>
			<th>Request Type&nbsp;<font color="red" >*</font></th>
			<td colspan="2">
				<html:select property="searchType" name="sapReportForm" styleClass="text_field" onchange="test1()">
					<html:option value="">--Select--</html:option>
			        <html:option value="Material Master">Material Master</html:option>
			        <html:option value="Customer Master">Customer Master</html:option>
			        <html:option value="Vendor Master">Vendor Master</html:option>
			        <html:option value="Service Master">Service Master</html:option> 
				</html:select>
			</td>  
			<th>Type</th>
			<td>
				<html:select property="materialCodeLists" name="sapReportForm" styleClass="text_field">
				
					<html:option value="">--Select--</html:option>
					<logic:equal value="Material Master" property="searchType" name="sapReportForm">
			<html:options name="sapReportForm" property="materTypeIDList" 
									labelProperty="materialTypeIdValueList"/>
					</logic:equal>
					<logic:equal value="Customer Master" property="searchType" name="sapReportForm">
						<html:option value="IN">Domestic</html:option>
						<html:option value="IM">Export Customer</html:option>
						<html:option value="LL">Loan-Licence</html:option>
						<html:option value="FS">Field Staff</html:option>
						<html:option value="007">Plants</html:option>
					</logic:equal>
					<logic:equal value="Vendor Master" property="searchType" name="sapReportForm">
			<html:options name="sapReportForm" property="accountGroupList" labelProperty="accountGroupLabelList"/>
					</logic:equal>
					
					<logic:equal value="Service Master" property="searchType" name="sapReportForm">
						<html:option value="ZITA">ZITA- AMC-IT</html:option>
					<html:option value="ZAMC">ZAMC- Annual maintainence</html:option>
					<html:option value="ZCLB">ZCLB- Calibration</html:option>
					<html:option value="ZCIV">ZCIV- Civil works</html:option>
					<html:option value="ZMNT">ZMNT- Maintainence</html:option>
					<html:option value="ZITM">ZITM-MAINAINENCE-IT</html:option>
					<html:option value="ZMKT">ZMKT- Marketing</html:option>
					<html:option value="ZTST">ZTST- Testing&Analysis</html:option>
					<html:option value="ZTRC">ZTRC- Training&Recruitment</html:option>
					
					</logic:equal>
				</html:select>
	
			</td> 
			</tr>
			<tr> 
			<th>Created Date &nbsp;<font color="red" >*</font> </th>
			<td colspan="2">
				<html:text property="fromDate" styleId="fromDate"  title="From Date"></html:text>
		 		 - 
		 		<html:text property="toDate"  styleId="toDate"   title="To Date"></html:text>
		 	</td>
			
			<th>Plant</th>  
			<td>
	 			<html:select property="locationId" styleClass="text_field">
	 				<html:option value="">--Select--</html:option>
	  				<html:options property="locationIdList" labelProperty="locationLabelList" ></html:options>   
	 			</html:select>
			</td>
			</tr>
			<tr>
			<th>
			Request Status
			</th>
			<td colspan="2">
			<html:select property="req_Status" name="sapReportForm" styleClass="req_Status" >
					<html:option value="">--Select--</html:option>
			        <html:option value="Submitted">Submitted</html:option>
			         <html:option value="In Process">In Process</html:option>
			        <html:option value="Completed">Completed</html:option>
			        <html:option value="Rejected">Rejected</html:option>
				</html:select>
			</td>
			<%-- <th>Sap Created Date  </th>
			<td colspan="2">
				<html:text property="fromDate1" styleId="fromDate1"  title="From Date"></html:text>
		 		 - 
		 		<html:text property="toDate1"  styleId="toDate1"   title="To Date"></html:text>
		 	</td> --%>
		 	<th> Request No </th>
		 	<td>
		 	<html:text property="requestNumber" styleId="requestNumber"  title="Request No."></html:text>
		 	</td>
			</tr>
			<tr>
			
			<%--  <th> Technician</th>
			<td colspan="4">
			<html:select  property="approver" name="sapReportForm" styleId="approver">
			<html:option value="">--Select--</html:option>
			<html:options name="sapReportForm"  property="techidList" labelProperty="technameList"/>
			</html:select>
			</br><sub>&nbsp;<font color="red" >Technician Will Consider Only Completed Request</font> </sub>
			</td>  --%>
			</tr>
			
			<tr>
			<td colspan="6" align="center">
			<center><html:button property="method" value="Search" title="Search..." onclick="searchMaterialRecord()" styleClass="rounded"/>&nbsp;
			<html:button property="method" value="Clear" styleClass="rounded" onclick="clearData()"/></center>
			
			</td>
		</tr>
	</table>
<div>&nbsp;</div>

<logic:notEmpty name="sumlist">

<table class="bordered sortable" style="width: 80%;margin-left: 100px;">
<tr>
			<th colspan="6"><center><big>Summary of Material Type Wise</big></center></th>
		</tr>
<%-- <tr>
<th colspan="6"><center><br><bean:write name="sapReportForm" property="searchType" />   </br></center></th>
</tr> --%>
<tr>
<th width="200px;">
Description
</th>
<th>
Submitted
</th>
<th width="80px;">
In Process
</th>

<th>
Completed
</th>
<th>
Rejected
</th>
<th>
Total
</th>
</tr>
<logic:iterate id="abc" name="sumlist">
<tr>
<td width="150px;">
<bean:write name="abc" property="searchType"/>
</td>
<td>
<bean:write name="abc" property="midRecord"/>
</td>

<td>
<bean:write name="abc" property="endRecord"/>
</td>

<td>
<bean:write name="abc" property="startRecord"/>
</td>

<td>
<bean:write name="abc" property="rejRecord"/>
</td>
<td>
<bean:write name="abc" property="totalRecords"/>
</td>

</tr>

</logic:iterate>

</table>

</logic:notEmpty>


<br/>
<hr/>
<div align="right">
<logic:notEmpty name="displayRecordNo">
	 
	
	  	<a href="#"><img src="images/First10.jpg" onclick="firstMaterialRecord()" align="absmiddle"/></a>
	
	
	<logic:notEmpty name="disablePreviousButton">

	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<a href="#"><img src="images/previous1.jpg" onclick="previousMaterialRecord()" align="absmiddle"/></a>

	</logic:notEmpty>
	
	<bean:write property="startRecord"  name="sapReportForm"/>-
	
	<bean:write property="endRecord"  name="sapReportForm"/>

	<logic:notEmpty name="nextButton">

	<a href="#"><img src="images/Next1.jpg" onclick="nextMaterialRecord()" align="absmiddle"/></a>

	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	
	<img src="images/disableRight.jpg" align="absmiddle"/>

	
	</logic:notEmpty>
	
		<a href="#"><img src="images/Last10.jpg" onclick="lastMaterialRecord()" align="absmiddle"/></a>
	
	</td>
	
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	</logic:notEmpty>
	 </tr>
	 </table>
	</div>
	<!--<div>&nbsp;</div>-->
	
		<logic:notEmpty name="listOfServiceCode">
		<html:button property="method" value="Export To Excel" onclick="exportToExcel()" styleClass="rounded"/>
		<div>&nbsp;</div>
<table class="bordered sortable">
				<tr>
					<th style="width:50px;">Req No</th>
					<th style="width:50px;">Req.Date</th>
					<th style="width:50px;">Plant</th>
					<th>Requester&nbsp;Name</th>
					<th>Service Description</th>
					<th>Status</th>
					<th>SAP Code No</th>
					<th>Code Creation Date</th>
					<!-- <th>Date</th> -->
					<!-- <th>Approver Dates</th> -->
					<th>Last Approver</th>
					<th>Pending Approver</th>
				</tr>
	<logic:iterate id="custList" name="listOfServiceCode">			
	<tr>
	    <td><a href="javascript:getdata('<bean:write name="custList" property="r_no" />','Service Master')" /><bean:write name="custList" property="r_no" /></a></td>
		<td><bean:write name="custList" property="requestDate" /></td>
		<td><bean:write name="custList" property="locationID" /></td>
		<td><bean:write name="custList" property="requestedBy" /></td>
		<td><font style="text-transform:uppercase;"><bean:write name="custList" property="serviceDescription" /></font></td>
		<td><bean:write name="custList" property="approveType" /></td>
		<td><bean:write name="custList" property="sapCodeNo" /></td>
		<td><bean:write name="custList" property="sapCreationDate" /></td>
		<td><bean:write name="custList" property="lastApprover" />&nbsp;</td>
		<td><bean:write name="custList" property="pendingApprover" />&nbsp;</td>
	</tr>
	</logic:iterate>			
</table>				
</logic:notEmpty>	
	<logic:notEmpty name="listOfVendorCode">
<html:button property="method" value="Export To Excel" onclick="exportToExcel()" styleClass="rounded"/>
		<div>&nbsp;</div>
<table class="bordered sortable">
				<tr>
					<th style="width:50px;">Req No</th>
					<th style="width:50px;">Req.Date</th>
					<th style="width:50px;">Plant</th>
					<th>Requester&nbsp;Name</th>
					<th>Customer Name</th>
					<th>City</th>
					<th>Status</th>
					<th>SAP Code No</th>
					<th>Code Creation Date</th>
					<!-- <th>Date</th> -->
					<!-- <th>Approver Dates</th> -->
					<th>Last Approver</th>
					<th>Pending Approver</th>
				</tr>
	<logic:iterate id="custList" name="listOfVendorCode">			
	<tr>
	    <td><a href="javascript:getdata('<bean:write name="custList" property="requestNo" />','Vendor Master')" /><bean:write name="custList" property="requestNo" /></a></td>
		<td><bean:write name="custList" property="requestDate" /></td>
		<td><bean:write name="custList" property="locationId" /></td>
		<td><bean:write name="custList" property="requestedBy" /></td>
		<td><font style="text-transform:uppercase;"><bean:write name="custList" property="name" /></font></td>
		<td><font style="text-transform:uppercase;"><bean:write name="custList" property="city" /></font></td>
		<td><bean:write name="custList" property="approveType" /></td>
		<td><bean:write name="custList" property="sapCodeNo" /></td>
		<td><bean:write name="custList" property="sapCreationDate" /></td>
		<td><bean:write name="custList" property="lastApprover" />&nbsp;</td>
		<td><bean:write name="custList" property="pendingApprover" />&nbsp;</td>
	</tr>
	</logic:iterate>			
</table>				
</logic:notEmpty>	 
	
<logic:notEmpty name="listOfCustomers">
<html:button property="method" value="Export To Excel" onclick="exportToExcel()" styleClass="rounded"/>
		<div>&nbsp;</div>
<table class="bordered sortable">
				<tr>
					<th style="width:50px;">Req No</th>
					<th style="width:50px;">Req.Date</th>
					<th style="width:50px;">Plant</th>
					<th>Requester&nbsp;Name</th>
					<th>Customer Name</th>
					<th>City</th>
					<th>Status</th>
					<th>SAP Code No</th>
					<th>Code Creation Date</th>
					<!-- <th>Date</th> -->
					<!-- <th>Approver Dates</th> -->
					<th>Last Approver</th>
					<th>Pending Approver</th>
				</tr>
	<logic:iterate id="custList" name="listOfCustomers">			
	<tr>
	   <td><a href="javascript:getdata('<bean:write name="custList" property="requestNo" />','Customer Master')" /><bean:write name="custList" property="requestNo" /></a></td>
		<td><bean:write name="custList" property="requestDate" /></td>
		<td><bean:write name="custList" property="locationId" /></td>
		<td><bean:write name="custList" property="requestedBy" /></td>
		<td><font style="text-transform:uppercase;"><bean:write name="custList" property="customerName" /></font></td>
		<td><font style="text-transform:uppercase;"><bean:write name="custList" property="city" /></font></td>
		<td><bean:write name="custList" property="approveType" /></td>
		<td><bean:write name="custList" property="sapCodeNo" /></td>
		<td><bean:write name="custList" property="sapCreationDate" /></td>
		<td><bean:write name="custList" property="lastApprover" />&nbsp;</td>
		<td><bean:write name="custList" property="pendingApprover" />&nbsp;</td>
	</tr>
	</logic:iterate>			
</table>				
</logic:notEmpty>	 

<logic:notEmpty name="noCustRecords">

<table class="sortable bordered">
			<tr>
				<th style="width:50px;">Req No</th>
					<th style="width:50px;">Req.Date</th>
					<th style="width:50px;">Plant</th>
					<th>Requester&nbsp;Name</th>
					<th>Customer Name</th>
					<th>City</th>
					<th>Status</th>
					<th>SAP Code No</th>
					<th>Code Creation Date</th>
					<!-- <th>Date</th> -->
					<!-- <th>Approver Dates</th> -->
					<th>Last Approver</th>
					<th>Pending Approver</th>	</tr>
				</table>
					
					<div align="center">
						<logic:present name="sapReportForm" property="message">
						<font color="red" size="3">
							<bean:write name="sapReportForm" property="message" />
						</font>
						
					</logic:present>
           
					</div>
					</table>
</logic:notEmpty>
	 
<logic:notEmpty name="listOfMaterials">
<html:button property="method" value="Export To Excel" onclick="exportToExcel()" styleClass="rounded"/>
		<div>&nbsp;</div>
<table class="bordered sortable">
				<tr>
					<th style="width:50px;">Req No</th>
					<th style="width:50px;">Req.Date</th>
					<th>Requester&nbsp;Name</th>
					<th style="width:50px;">Plant</th>
					<th>Material Type</th>
					<th>Material Name</th>
					<!-- <th>Material Desc</th> -->
					<th>UOM</th>
					<th>Material Group</th>
					<th>Status</th>
					<th>SAP Code No</th>
					<th>Code Creation Date</th>
					<!-- <th>Date</th> -->
					<!-- <th>Approver Dates</th> -->
					<!-- <th>Last Approver</th> -->
					<th>Pending Approver</th>
					<th>Previous Approver</th>
					<th>Previous Approver Date</th>
				</tr>
				<logic:iterate id="materList" name="listOfMaterials">
					<tr>
						<td><a href="javascript:getdata('<bean:write name="materList" property="requestNumber" />','<bean:write name="materList" property="mType" />')" /><bean:write name="materList" property="requestNumber" /></a></td>
						<td><bean:write name="materList" property="requestDate" /></td>
						<td><bean:write name="materList" property="requesterName" /></td>
						<td><bean:write name="materList" property="locationId" /></td>
						<td><bean:write name="materList" property="mType" /></td>
						
						<td><font style="text-transform:uppercase;"> <bean:write
									name="materList" property="materialShortName" /></font></td>
									<%-- <td><font style="text-transform:uppercase;"> <bean:write
									name="materList" property="materialDesc" /></font></td> --%>
						<td><bean:write name="materList" property="uom" /></td>
						<td><bean:write name="materList" property="materialGroupName" /></td>
						<td><bean:write name="materList" property="approveType" /></td>
						<td><bean:write name="materList" property="sapCodeNo" /></td>
						<td><bean:write name="materList" property="sapCreationDate" /></td>
						<%-- <td><bean:write name="materList" property="codeCreationDate" /></td> --%>
					<%-- 	<td><bean:write name="materList" property="approversList" /></td> --%>
						<%-- <td><bean:write name="materList" property="lastApprover" /></td> --%>
						<td><bean:write name="materList" property="pendingApprover" /></td>
						<td><bean:write name="materList" property="prevApprover" /></td>
						<td><bean:write name="materList" property="prevApproverDate" /></td>
					</tr>
				</logic:iterate>

				</logic:notEmpty>

<logic:notEmpty name="noRecords">

<table class="sortable bordered">
			<tr>
				<th style="width:50px;">Req No</th><th style="width:100px;">Request Date</th><th style="width:150px;">Material Type</th><th>Name</th><th >Material Name</th>
		<th style="width:50px;">Plant</th><th>Status</th><th>Last Approver</th><th>Pending Approver</th>
	</tr>
				</table>
					
					<div align="center">
						<logic:present name="sapReportForm" property="message">
						<font color="red" size="3">
							<bean:write name="sapReportForm" property="message" />
						</font>
						
					</logic:present>
           
					</div>
						
					</table>
					 
	

</logic:notEmpty>
</html:form>
</body>
</html>	