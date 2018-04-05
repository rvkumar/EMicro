<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.microlabs.db.ConnectionFactory"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.awt.*" %>
<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.jfree.chart.plot.*"%>
<%@page import="org.jfree.data.general.*"%>
<%@page import="org.jfree.data.*"%>
<%@page import="org.jfree.data.category.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
div:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
    
    
}
</style>
 <base href="<%=basePath%>">
 
<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
 <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
  <link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
   <link rel="stylesheet" type="text/css" href="css/jquery.dataTables.min.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">

$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>
 <script type="text/javascript">
 
 $(document).ready(function() {
    $('#example').DataTable( {
        initComplete: function () {
            this.api().columns().every( function () {
                var column = this;
                var select = $('<select><option value=""></option></select>')
                    .appendTo( $(column.footer()).empty() )
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
 
                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );
 
                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        }
    } );
} );
 
 
 
 function displayITReport(){
	 var requestType=document.forms[0].requestType.value;
	 if(requestType==""){
		 alert("Please Select Request Type");
		 document.forms[0].requestType.focus();
		 return false;
	 }
	 
	   if(document.forms[0].keyword.value!="")
	   {
	    var employeeno = document.forms[0].keyword.value;
	          var pattern = /^\d+(\d+)?$/
	          if (!pattern.test(employeeno)) {
	               alert("Employee Number  Should be Integer ");
	                  document.forms[0].keyword.focus();
	              return false;
	          }
	   }
	   
	   if(document.forms[0].fromDate.value!="")
	    {    
		   if(document.forms[0].toDate.value==""){
				 alert("Please Select To Date Type");
				 document.forms[0].toDate.focus();
				 return false;
			 }
	   
	   if(document.forms[0].fromDate.value!=document.forms[0].toDate.value)
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
   alert("Please Select Valid Date Range");
   document.forms[0].toDate.value="";
    document.forms[0].toDate.focus();
    return false;
}
	     }
	    }
	 document.forms[0].action="helpDeskReport.do?method=displayITReport&selType=Detail";
	 document.forms[0].submit(); 
 }
 
 function clearData(){
	 document.forms[0].requestType.value="";
	 document.forms[0].category.value="";
	 document.forms[0].fromDate.value="";
	 document.forms[0].toDate.value="";
	 document.forms[0].locationId.value="";
	 document.forms[0].department.value="";
	 document.forms[0].keyword.value="";
	 document.forms[0].requestStatus.value="";
 }
 
 function showform(){
	 document.forms[0].action="helpDeskReport.do?method=displayreport&selType=Detail";
	 document.forms[0].submit(); 
 }
 
 function exportITIssues(){
	 document.forms[0].action="helpDeskReport.do?method=exportITIssues";
	 document.forms[0].submit(); 
	 
 }
 
 function exportAllIssues(){
	 document.forms[0].action="helpDeskReport.do?method=exportAllIssues";
	 document.forms[0].submit(); 
	 
 }
 
 
 function nextRecord(){
var url="helpDeskReport.do?method=nextRecord&selType=Detail";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function nextRecordSummary(){
var url="helpDeskReport.do?method=nextRecordSummary";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function previousRecord(){
var url="helpDeskReport.do?method=previousRecord&selType=Detail";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function previousRecordSummary(){
var url="helpDeskReport.do?method=previousRecordSummary";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function lastRecord(){
var url="helpDeskReport.do?method=lastRecord&selType=Detail";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function lastRecordSummary(){
var url="helpDeskReport.do?method=lastRecordSummary";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function firstRecord(){
var url="helpDeskReport.do?method=displayITReport";
		document.forms[0].action=url;
		document.forms[0].submit();
}

function firstRecordSummary(){
var url="helpDeskReport.do?method=firstRecordSummary";
		document.forms[0].action=url;
		document.forms[0].submit();
}


function summarydashboard2(requestby ,location ,hidden,fromDate, toDate)
{


var xmlhttp;
var dt;



document.getElementById("recordview").style.visibility="hidden";


if(requestby==null || requestby =="")
{
dt=document.getElementById('requestBy').value;

}
else
{
dt=requestby;

}




if(fromDate == null || fromDate== "")
{
fromDate1=document.getElementById('popupDatepicker3').value;
}
else
{
fromDate1=fromDate;
}



if(toDate == null || toDate== "")
{
toDate1=document.getElementById('popupDatepicker4').value;
}
else
{
toDate1=toDate;
}





if(location== null || location== "")
{

dept = document.forms[0].requesterdepartment.value;


}

else
{

dept = location;
}






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
    
    document.getElementById("dashboard").innerHTML=xmlhttp.responseText;
   // window.location.hash = '#recordview';

if(document.forms[0].action.value > 0 )
    {
      
    document.getElementById('recordview').scrollIntoView();
    }
    document.forms[0].requestBy.value = requestby;
   
   
      
    }
  }
  


xmlhttp.open("POST","helpDeskReport.do?method=getSummary&linkName="+dt+"&dept="+dept+"&hidden="+hidden+"&fromDate="+fromDate1+"&toDate="+toDate1,true);
xmlhttp.send();
}




function summarydashboard(requestby ,location ,hidden,fromDate, toDate)
{


var xmlhttp;
var dt;



document.getElementById("recordview").style.visibility="hidden";


if(requestby==null || requestby =="")
{
dt=document.getElementById('requestBy').value;

if(dt=="")
{
alert("Please Select Request Type");
document.getElementById('requestBy').focus();
return false;
}

}
else
{
dt=requestby;

}




if(fromDate == null || fromDate== "")
{
fromDate1=document.getElementById('popupDatepicker3').value;
if(fromDate1=="")
{
alert("Please Select From Date");
document.getElementById('popupDatepicker3').focus();
return false;
}

}
else
{
fromDate1=fromDate;
}



if(toDate == null || toDate== "")
{
toDate1=document.getElementById('popupDatepicker4').value;

if(toDate1=="")
{
alert("Please Select To Date");
document.getElementById('popupDatepicker4').focus();
return false;
}

}
else
{
toDate1=toDate;
}


if(fromDate1!=""&&toDate1!=""){
   
   
     var str1 = fromDate1;
var str2 = toDate1;
var dt1  = parseInt(str1.substring(0,2),10); 
var mon1 = parseInt(str1.substring(3,5),10); 
var yr1  = parseInt(str1.substring(6,10),10); 
var dt2  = parseInt(str2.substring(0,2),10); 
var mon2 = parseInt(str2.substring(3,5),10); 
var yr2  = parseInt(str2.substring(6,10),10); 
var date1 = new Date(yr1, mon1-1, dt1); 
var date2 = new Date(yr2, mon2-1, dt2); 


if(date2 < date1) 
{ 
    alert("Please Select Valid Date Range");
    document.forms[0].fromDate1.value="";
     document.forms[0].fromDate1.focus();
     return false;
}

}

if(location== null || location== "")
{

dept = document.forms[0].requesterdepartment.value;

if(dept=="")
{
alert("Please Select Location");
document.forms[0].requesterdepartment.focus();
return false;
}

}

else
{

dept = location;
}






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
    
    document.getElementById("dashboard").innerHTML=xmlhttp.responseText;
   // window.location.hash = '#recordview';

if(document.forms[0].action.value > 0 )
    {
      
    document.getElementById('recordview').scrollIntoView();
    }
    document.forms[0].requestBy.value = requestby;
   
   
      
    }
  }
  


xmlhttp.open("POST","helpDeskReport.do?method=getSummary&linkName="+dt+"&dept="+dept+"&hidden="+hidden+"&fromDate="+fromDate1+"&toDate="+toDate1,true);
xmlhttp.send();
}

function summaryDetail(empno ,requestedtype)
{
var url="helpDeskReport.do?method=getSummaryDetail&techname="+empno+"&type="+requestedtype;
document.forms[0].action=url;
document.forms[0].submit();
}


function chk()
{
document.getElementById("recordview").style.visibility="hidden";
}

function view(type)
{

if(type == "Summary")
{
document.getElementById("Detail").style.visibility="collapse";
document.getElementById("Summary").style.visibility="Visible";
document.getElementById("dashboard").style.visibility="Visible";
document.getElementById("recordview").style.visibility="Visible";
document.getElementById("detailview").style.visibility="collapse";
document.getElementById("seltype").style.visibility="Visible";



}

if(type == "Detail")
{
document.getElementById("Summary").style.visibility="collapse";
document.getElementById("Detail").style.visibility="Visible";
document.getElementById("dashboard").style.visibility="collapse";
document.getElementById("recordview").style.visibility="collapse";
document.getElementById("detailview").style.visibility="Visible";
document.getElementById("seltype").style.visibility="Visible";
}

}



$(function() {
	$('#popupDatepicker3').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker3').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#popupDatepicker4').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker4').datepick({onSelect: showDate});
});
 
 </script>
 </head>
 <body>
  <html:form action="/helpDeskReport.do" enctype="multipart/form-data" onsubmit="displayITReport(); return false;">
  <html:hidden property="action" name="helpdeskReportForm"/>
  <html:hidden property="total" name="helpdeskReportForm"/>
	<html:hidden property="startRecord" name="helpdeskReportForm"/>
	<html:hidden property="endRecord" name="helpdeskReportForm"/>
		<html:hidden property="message" name="helpdeskReportForm"/>
		<html:hidden property="location" name="helpdeskReportForm"/>
		<html:hidden property="requestBy" name="helpdeskReportForm"/>
		<html:hidden property="techname" name="helpdeskReportForm"/>
		<html:hidden property="techname" name="helpdeskReportForm"/>
		<html:hidden property="status" name="helpdeskReportForm"/>
		<html:hidden property="selType" name="helpdeskReportForm"/>
		
		
		
<div id = "seltype">
<table class="bordered " width="100%">
<tr>
<th colspan="15"><center> <font color="blue" size="4" > Report View</font></center></th>
</tr>
<tr>
<th colspan="8"><Big><center><a href="javascript:view('Summary')" >Summary</a></center></Big></th>
<th colspan="7"><Big><center><a href="javascript:view('Detail')" >Detailed View</a></center></Big></th>	
 </tr></table> 
</div>

		
  <div>&nbsp;</div>
  
  
  <div  id ="Detail"  style="visibility: hidden; ">
		<table class="bordered " width="100%">
 <tr>
<th>Request Type <font color="red">*</font></th>
				<td>
<html:select property="requestType" styleClass="content" styleId="filterId" onchange="showform()">
	<html:option value="">--Select--</html:option>
	<html:option value="Issues">IT Issues</html:option>
    <html:option value="ITServices">New Requirement</html:option>
    <html:option value="SAPIssues">SAP Issues</html:option>
</html:select>
			
</td>
<th><b>Category</b></th>
<td>
	<html:select property="category" styleClass="content"  >
		<html:option value="">-----Select---------</html:option>
	<logic:equal value="Issues" property="requestType" name="helpdeskReportForm">	
		<html:option value="Hardware">Hardware</html:option>
		<html:option value="Internet">Internet</html:option>
		<html:option value="Network">Network</html:option>
		<html:option value="Operating System">Operating System</html:option>
		<html:option value="Printers">Printers</html:option>
		<html:option value="Software">Software</html:option>							
		<html:option value="Telephone">Telephone</html:option>
		<html:option value="SAP Users">SAP Users</html:option>
		<html:option value="SAP Core Team">SAP Core Team</html:option>
	</logic:equal>
	<logic:equal value="ITServices" property="requestType" name="helpdeskReportForm">
	    <html:option value="Active Directory User Creation">Active Directory User Creation</html:option>
	    <html:option value="Active Directory User Deletion">Active Directory User Deletion</html:option>
		<html:option value="Active Directory User Transfer">Active Directory User Transfer</html:option>
	    <html:option value="Email ID Create Request">Email ID Create Request</html:option>
		<html:option value="Email ID Change Request">Email ID Change Request</html:option>
		<html:option value="Email ID Delete Request">Email ID Delete Request</html:option>
		<html:option value="Internet Access Request">Internet Access Request</html:option>
		<html:option value="External Drives Access Request">External Drives Access Request</html:option>
		<html:option value="FTP Access Request">FTP Access Request</html:option>
		<html:option value="File Server Access Request">File Server Access Request</html:option>
		<html:option value="New IT Asset Request">New IT Asset Request</html:option>
		<html:option value="New IT Spares Request">New IT Spares Request</html:option>
	</logic:equal>
	<logic:equal value="SAPIssues" property="requestType" name="helpdeskReportForm">
 	<html:options name="helpdeskReportForm"  property="sapList" labelProperty="sapLabelList"/>  
    </logic:equal>
		
    </html:select>
</td>
<th>Date</th>
<td width="600px">
<html:text property="fromDate" name="helpdeskReportForm" styleId="popupDatepicker"/>&nbsp;To&nbsp;
<html:text property="toDate" name="helpdeskReportForm" styleId="popupDatepicker2"/>


</td>

    </tr>
<tr>
<th>Req.Status</th><td><html:select property="requestStatus" styleClass="content">
<html:option value="">-----Select---------</html:option>
<logic:equal value="Issues" property="requestType" name="helpdeskReportForm">
	<html:option value="New"/>
	<html:option value="Sent To IT"/>
	<html:option value="User Action"/>	
	<html:option value="On Hold"/>
	<html:option value="In Process"/>
	<html:option value="Forwarded"/>
	<html:option value="Completed"/>
</logic:equal>
<logic:equal value="ITServices" property="requestType" name="helpdeskReportForm">
	<html:option value="In Process"/>
	<html:option value="Open"/>
	<html:option value="Closed"/>
</logic:equal>
<logic:equal value="SAPIssues" property="requestType" name="helpdeskReportForm">
	<html:option value="Approved"/>
	<html:option value="Completed"/>
	<html:option value="In Process"/>
	<html:option value="Pending"/>
	<html:option value="Rejected"/>
</logic:equal>

</html:select>
</td>
<th>Location </th>
	<td align="left">
		<html:select  property="locationId" name="helpdeskReportForm">
			<html:option value="">--Select--</html:option>
			<html:options name="helpdeskReportForm"  property="locationIdList" labelProperty="locationLabelList"/>
		</html:select>
	</td>
<th>Dept</th>
	<td>
	<html:select property="department" name="helpdeskReportForm"  styleClass="rounded">
		<html:option value="">--Select--</html:option>
      			<html:options property="departmentIdList" labelProperty="departmentList" name="helpdeskReportForm" ></html:options>  
   			</html:select>
     </td> 						
</tr>
<tr>
<th>Emp No</th><td><html:text property="keyword" styleClass="rounded" /> </td><th>Technician</th><td><html:select  property="approver" name="helpdeskReportForm">
			<html:option value="">--Select--</html:option>
			<html:options name="helpdeskReportForm"  property="techidList" labelProperty="technameList"/>
		</html:select> </td>
<td colspan="10">

 <center><html:button property="method" value="Search" styleClass="rounded" onclick="displayITReport()"/>
&nbsp;&nbsp;<html:button property="method" value="Clear" styleClass="rounded" onclick="clearData()"/>
 </center>
</td>
</tr>
</table>
		
		
	</div>	
		
	<div id ="Summary" style="visibility: hidden;">
<table class="bordered " width="40%" style="width: 585px; ">
 
<tr>
<th colspan="1" ><b>Location</b></th>
<td colspan="1">
<html:select property="requesterdepartment" name="helpdeskReportForm"  styleClass="rounded">
		
		<html:option value="All">All</html:option>
      			<html:options name="helpdeskReportForm"  property="locationIdList" labelProperty="locationLabelList"/>  
   			</html:select>
     </td> 	
&nbsp;
<th colspan="1"><b>Request by</b></th>
<td colspan="1">
<html:select property="requestBy" name="helpdeskReportForm"  styleClass="rounded" onchange= "" styleId="requestBy" onclick="chk()">
		<html:option value="">--Select--</html:option>
		<html:option value="Techinician">Technician</html:option>
		<html:option value="Category">Category</html:option>
		<%-- <html:option value="Type">Type</html:option> --%>
    	<html:option value="Priority">Priority</html:option>
		<html:option value="Mode">Mode</html:option>
  	   
</html:select>
</td> 	
</tr>

<tr>
<th>Date</th>
<td width="30%" colspan="2">
<html:text property="fromDate1" name="helpdeskReportForm" styleId="popupDatepicker3" style="width: 75px; " /> &nbsp; To&nbsp;
<html:text property="toDate1" name="helpdeskReportForm" styleId="popupDatepicker4" style="width: 75px; "/> </td>
<td><html:button property="method" value="Search" styleClass="rounded" onclick= "summarydashboard()"/></td>

	
</tr>
</table>

</div>	
<div id="dashboard" align="left" style=" visibility: collapse;" >
</div> 
<div id ="detailview" >
<logic:notEmpty name="helpDeskList" >
	<html:button property="method" value="Export To Excel" onclick="exportITIssues()" styleClass="rounded"/>
	<div>&nbsp;</div>
	
	<div><center>	<logic:notEmpty name="displayRecordNo">

	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="helpdeskReportForm"/>-
	
	<bean:write property="endRecord"  name="helpdeskReportForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
	</td>
</logic:notEmpty></center></div>
		
	
		
<table class="bordered" id="example">
	<tr >
<th rowspan="2">Req&nbsp;No</th><th rowspan="2" >Req Date</th><th  colspan=3><Big><center>Completed details</center></Big></th><th  rowspan="2">Emp No</th><th rowspan="2">Req.By</th>
	<th rowspan="2" >Request&nbsp;Type</th><th rowspan="2"> Technician</th> <th rowspan="2">Last Approver</th> <th rowspan="2">Pending Approver</th><th rowspan="2"  style="width:120px;">Status</th><th rowspan="2"> IT Engineer<br/> Status</th><th rowspan="2">Approval Date</th></tr>
	<tr><th><center> Date</center></th><th> Duration</br> in Days</th><th> Duration </br> in Hours</th></tr>

	  <logic:iterate id="h" name="helpDeskList">
	 <tr>
	 			<%-- <td><bean:write name="h" property="requestNo"/></td> --%>
				<td><a href="helpDeskReport.do?method=getSummaryDetailRecord&requstNo=${h.requestNo}&type=${h.category}&issrequestype=${h.reftypes}"><bean:write name="h" property="requestNo"/></a></td>	 			
				<td><bean:write name="h" property="requestDate"/></td>
				<td><bean:write name="h" property="compDate"/></td>
					<td><bean:write name="h" property="day"/></td>
				<td><bean:write name="h" property="hr"/></td>
				<td><bean:write name="h" property="empno"/></td>
				<td><bean:write name="h" property="requestername"/></td>
				<td><bean:write name="h" property="reqname"/></td>
				<td><bean:write name="h" property="approver"/></td>
				<td><bean:write name="h" property="lastApprover"/></td>
				<td><bean:write name="h" property="pendingApprover"/></td>
				<td> <logic:equal value="1" name="h" property="escalatedLevel">
<img src="images/flag-icon-512.png" alt="" height="16" width="15" align="absmiddle" title="Very High SLA Violated">
</img></logic:equal>
<logic:equal value="2" name="h" property="escalatedLevel">
<img src="images/flag-icon-Blue.png" alt="" height="16" width="15" align="absmiddle" title="High SLA Violated">
</img></logic:equal>
<logic:equal value="3" name="h" property="escalatedLevel">
<img src="images/flag-icon-amg.png" alt="" height="16" width="15" align="absmiddle" title="Medium SLA Violated">
</img></logic:equal><bean:write name="h" property="requestStatus"/></td>
				<td><bean:write name="h" property="itEngStatus"/></td>
				<td><bean:write name="h" property="approvedDate"/></td>
	 
	 </tr> 
	  </logic:iterate>
		
		</table>
		
		</logic:notEmpty>
		
		
		<logic:notEmpty name="ITIssues">


<div>&nbsp;</div>
<logic:notEmpty name="listofIssues">
<html:button property="method" value="Export To Excel" onclick="exportITIssues()" styleClass="rounded"/>
</logic:notEmpty>
<div>&nbsp;</div>
<div><center>	<logic:notEmpty name="displayRecordNo">

	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="helpdeskReportForm"/>-
	
	<bean:write property="endRecord"  name="helpdeskReportForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
	</td>
</logic:notEmpty></center></div>


	<logic:notEmpty name="listofIssues">
	<table class="bordered" id="example">

	
	<tr><th rowspan="2">Req.No</th><th rowspan="2">Req.Date</th><th colspan="3"><Big><center>Completed details</center></Big></th><th rowspan="2">Location</th><th rowspan="2">Requester Name</th><th rowspan="2">Technician</th><th rowspan="2">SAP Request</th><th rowspan="2">Category</th><th rowspan="2">Sub Category</th><th rowspan="2";style="width:100px;">Subject</th>
	<th rowspan="2">Priority</th><th rowspan="2"  style="width:120px;">Status</th><th rowspan="2">Approval Date</th></tr>
   	<tr><th><center> Date</center></th><th>Duration</br> in Days</th><th>Duration </br> in Hours</th></tr>
		<logic:iterate id="issue" name="listofIssues">
			<tr>
				<td><a href="helpDeskReport.do?method=getSummaryDetailRecord&requstNo=${issue.requestNo}&type=${issue.category}&issrequestype=${issue.reftypes}"><bean:write name="issue" property="requestNo"/></a></td>
				<td><bean:write name="issue" property="reqDate"/></td>
				<td><bean:write name="issue" property="compDate"/></td>
				<td><bean:write name="issue" property="day"/></td>
				<td><bean:write name="issue" property="hr"/></td>
				<td><bean:write name="issue" property="location"/></td>
				<td><bean:write name="issue" property="employeename"/></td>
				<td><bean:write name="issue" property="technicianName"/></td>
				<td><bean:write name="issue" property="requestName"/></td>
				<td><bean:write name="issue" property="category"/></td>
				<td><bean:write name="issue" property="subcategory"/></td>
				<td><bean:write name="issue" property="subject"/></td>
				<td width="10%;">
                 <logic:equal value="Very High" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: red;"></canvas>
			           </logic:equal>
			             <logic:equal value="High" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: #990033;"></canvas>
			           </logic:equal>
			             <logic:equal value="Low" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: green;"></canvas>
			           </logic:equal>
			             <logic:equal value="Medium" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: orange;"></canvas>
			           </logic:equal>
			             <logic:equal value="Normal" name="issue" property="reqPriority">
			            <canvas   width="10" height="15" style="border:0px solid #000000;background-color: brown;"></canvas>
			           </logic:equal>

<bean:write name="issue" property="reqPriority"/></td>
<td>
 <logic:equal value="1" name="issue" property="escalatedLevel">
<img src="images/flag-icon-512.png" alt="" height="16" width="15" align="absmiddle" title="Very High SLA Violated">
</img></logic:equal>
<logic:equal value="2" name="issue" property="escalatedLevel">
<img src="images/flag-icon-Blue.png" alt="" height="16" width="15" align="absmiddle" title="High SLA Violated">
</img></logic:equal>
<logic:equal value="3" name="issue" property="escalatedLevel">
<img src="images/flag-icon-amg.png" alt="" height="16" width="15" align="absmiddle" title="Medium SLA Violated">
</img></logic:equal>

<bean:write name="issue" property="requestStatus"/></td>
<td><bean:write name="issue" property="approvedDate"/></td>
			</tr>
		</logic:iterate>
	</logic:notEmpty>
		</table>
		</logic:notEmpty>
		
			</div>
			
			
<div id ="recordview" align="left" style="visibility:hidden; " >
<logic:notEmpty name="dashboardissue">
</br>

<html:button property="method" value="Export To Excel" onclick="exportAllIssues()" styleClass="rounded"/>
<div>&nbsp;</div>

	<div><center>	
	<logic:notEmpty name="displayRecordNo">
<td>
	  	<img src="images/First10.jpg" onclick="firstRecordSummary()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecordSummary()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="helpdeskReportForm"/>-
	
	<bean:write property="endRecord"  name="helpdeskReportForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextRecordSummary()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastRecordSummary()" align="absmiddle"/>
	</td>
</logic:notEmpty></center></div>
</logic:notEmpty>
<logic:notEmpty name="dashboardissue">
	<table class="bordered">

	
	<tr><th rowspan="2">Req.No</th><th rowspan="2">Req.Date</th><th colspan="3"><Big><center>Completed details</center></Big></th><th rowspan="2">Location</th><th rowspan="2">Requester Name</th><th rowspan="2">Technician</th><th rowspan="2">Category</th><th rowspan="2">Sub Category</th><th rowspan="2"; style="width:100px;">Subject</th>
	<th rowspan="2">Priority</th><th rowspan="2" style="width:120px;">Status</th><th rowspan="2">Approval Date</th><!-- <th rowspan="2">View</th> --></tr>
   	<tr><th><center> Date</center></th><th>Duration</br> in Days</th><th>Duration </br> in Hours</th></tr>
		<logic:iterate id="issue" name="dashboardissue">
			<tr>
				<td><a href="helpDeskReport.do?method=getSummaryDetailRecord&requstNo=${issue.requestNo}&type=${issue.category}&issrequestype=${issue.reftypes}"><bean:write name="issue" property="requestNo"/></a></td>
				<td><bean:write name="issue" property="reqDate"/></td>
				<td><bean:write name="issue" property="compDate"/></td>
				<td><bean:write name="issue" property="day"/></td>
				<td><bean:write name="issue" property="hr"/></td>
				<td><bean:write name="issue" property="location"/></td>
				<td><bean:write name="issue" property="employeename"/></td>
				<td><bean:write name="issue" property="technicianName"/></td>
				<td><bean:write name="issue" property="category"/></td>
				<td><bean:write name="issue" property="subcategory"/></td>
				<td><bean:write name="issue" property="subject"/></td>
				 <td width="10%;">
                         <logic:equal value="Very High" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: red;"></canvas>
			           </logic:equal>
			             <logic:equal value="High" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: #990033;"></canvas>
			           </logic:equal>
			             <logic:equal value="Low" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: green;"></canvas>
			           </logic:equal>
			             <logic:equal value="Medium" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: orange;"></canvas>
			           </logic:equal>
			             <logic:equal value="Normal" name="issue" property="reqPriority">
			            <canvas   width="10" height="15" style="border:0px solid #000000;background-color: brown;"></canvas>
			           </logic:equal>

<bean:write name="issue" property="reqPriority"/></td>
<td>
 <logic:equal value="1" name="issue" property="escalatedLevel">
<img src="images/flag-icon-512.png" alt="" height="16" width="15" align="absmiddle" title="Very High SLA Violated">
</img></logic:equal>
<logic:equal value="2" name="issue" property="escalatedLevel">
<img src="images/flag-icon-Blue.png" alt="" height="16" width="15" align="absmiddle" title="High SLA Violated">
</img></logic:equal>
<logic:equal value="3" name="issue" property="escalatedLevel">
<img src="images/flag-icon-amg.png" alt="" height="16" width="15" align="absmiddle" title="Medium SLA Violated">
</img></logic:equal>

<bean:write name="issue" property="requestStatus"/></td>
<td><bean:write name="issue" property="approvedDate"/></td>

<%-- <td>
 <a href="helpDeskReport.do?method=getSummaryDetailRecord&requstNo=${issue.requestNo}&type=${issue.category}&issrequestype=${issue.reftype}">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
</td> --%>

</tr>
		</logic:iterate>
	</logic:notEmpty>
</table>
</div>	
			<script type="">
summarydashboard2('<bean:write name="helpdeskReportForm" property="requestBy"/>','<bean:write name="helpdeskReportForm" property="requesterdepartment"/>','<bean:write name="helpdeskReportForm" property="message"/>'
,'<bean:write name="helpdeskReportForm" property="fromDate1"/>','<bean:write name="helpdeskReportForm" property="toDate1"/>');
view('<bean:write name="helpdeskReportForm" property="selType"/>')
</script>
			
		  </html:form>
 </body>
 </html>