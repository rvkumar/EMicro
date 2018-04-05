<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

 <style type="text/css">
      #button {  /* Box in the button */
        display: block;
        width: 100px;		
		border: none;
		margin: 0;
		padding: 0.4em 0.8em;

      }

      #button a {
        text-decoration: none;  /* Remove the underline from the links. */

      }

      #button ul {
        list-style-type: none;  /* Remove the bullets from the list */
      }

      #button .top {
        background-color: #DDD;  /* The button background */
      }

      #button ul li.item {
        display: none;  /* By default, do not display the items (which contains the links) */
      }  

      #button ul:hover .item {  /* When the user hovers over the button (or any of the links) */
        display: block;
        border: 1px solid black;
        background-color: #BBBBBB;
      }
    </style>



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'domestic.jsp' starting page</title>
    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      <link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
      <link rel="stylesheet" type="text/css" href="css/styles.css" />

  <style type="text/css">
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

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



$(function() {
	$('#popupDatepicker3').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker3').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>
<script type="text/javascript">$(function () {


	$('#timeFrom').timeEntry();
});


$(function () {


	$('#timeTo').timeEntry();
});

$('.timeRange').timeEntry({beforeShow: customRange}); 
 
function customRange(input) { 
    return {minTime: (text.styleId == 'timeTo' ? 
        $('#timeFrom').timeEntry('getTime') : null),  
        maxTime: (text.styleId  == 'timeFrom' ? 
        $('#timeTo').timeEntry('getTime') : null)}; 
}


function statusMessage(message){
alert(message);
document.forms[0].message.value="";
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function nextRecord()
{
     
	document.forms[0].action="travelrequest.do?method=nextdisplayMyrequest";
	document.forms[0].submit();

}
function previousRecord()
{
    
	document.forms[0].action="travelrequest.do?method=prevdisplayMyrequest";
	document.forms[0].submit();

}


function cancelrecord(reqid)
{
	document.forms[0].action="travelrequest.do?method=cancelRecord&requstNo="+reqid;
	document.forms[0].submit();

}


function search()
{
document.forms[0].action="travelrequest.do?method=displayMyrequestList";
	document.forms[0].submit();
}


function applyDomastic()
{
	
	document.forms[0].action="travelrequest.do?method=domesticNew";
	document.forms[0].submit();

}

</script>
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
  <logic:equal value="pdf" name="travelRequestForm" property="reqStatus" >
				<div style="display: none;" ><a href="${travelRequestForm.fileFullPath}"  target="_blank" id="pdf"></a></div>
					<script language="javascript">
						document.getElementById("pdf").click();
					</script>
			</logic:equal>
  	<div align="center">
				<logic:notEmpty name="travelRequestForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="travelRequestForm" property="message" />');
					</script>
				</logic:notEmpty>
			
			</div>
  <br/>
  	<html:form action="travelrequest" enctype="multipart/form-data" method="post" >
  		<div >
  		
  		<table class="bordered" style="position: relative;left: 2%;width: 80%;"><tr><th colspan="4">Search</th></tr> 
  	<tr><td>From date</td><td><html:text property="fromdate"   size="20"  onkeyup="this.value = this.value.replace(/'/g,'`')" styleId="popupDatepicker" readonly="true"/></td>
  	<td>To date</td><td><html:text property="todate"   size="20"  onkeyup="this.value = this.value.replace(/'/g,'`')" styleId="popupDatepicker2" readonly="true"/></td></tr>
  	<tr><td>Request Status</td><td colspan="1"><html:select name="travelRequestForm" property="reqStatus" styleClass="text_field">
									<html:option value="all">All</html:option>
									<html:option value="Pending">Pending</html:option>
									<html:option value="Approved">Approved</html:option>
									<html:option value="Reverted">Reverted</html:option>
									<html:option value="Rejected">Rejected</html:option>
									<html:option value="Rescheduled">Rescheduled</html:option>
									<html:option value="Self Cancelled">Self Cancelled</html:option>
								
								
									</html:select></td>
		<td colspan="2">
		<html:button property="method" styleClass="rounded"  value="go" onclick="search()" style="align:right;width:100px;"/>
		&nbsp;&nbsp;&nbsp;<html:button property="method" styleClass="rounded"  value="new" onclick="applyDomastic()" style="align:right;width:100px;"/>
		</td>							
	</tr></table><br/>
  		
         			<table  style="width: 80%;">
						<tr><td align="center">
							<logic:notEmpty name="displayRecordNo">
	 						
								<logic:notEmpty name="disablePreviousButton">
									&nbsp;<img src="images/disableLeft.jpg" align="absmiddle"/>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="previousButton">
									&nbsp;<a href="travelrequest.do?method=prevdisplayMyrequest"><img src="images/previous1.jpg"  align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
									&nbsp;<bean:write property="startRecord"  name="travelRequestForm"/>&nbsp;-&nbsp;<bean:write property="endRecord"  name="travelRequestForm"/>&nbsp;
								<logic:notEmpty name="nextButton">
									&nbsp;<a href="travelrequest.do?method=nextdisplayMyrequest"><img src="images/Next1.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="disableNextButton">
									&nbsp;<img src="images/disableRight.jpg" align="absmiddle"/>&nbsp;
								</logic:notEmpty>
							
								<!--<td style="align:right;text-align:center;">
									<img src="images/clear.jpg" style="vertical-align:middle;" onclick="searchInMyRequest('clear');" width="25" height="25" />
									<input type="text" id="searchText" style="padding-top: 3px; width: 200px;" class="rounded" value="Search in MyRequest" onmousedown="this.value='';"/>
									<img src="images/search-bg.jpg" style="vertical-align:middle;" onclick="searchInMyRequest('search')" width="40" height="50" />
								</td>
							-->
								</logic:notEmpty>
								</td>
							</tr>
						</table>
						</div>
  	  <br/>
  	<table class="bordered" style="position: relative;left: 2%;width: 80%;">
  	<tr><th colspan="17"><center>My Travel List</center></th></tr>
  	<tr><th>Sl No</th><th>Trip No</th><th>Req Date</th><th> Type Of travel</th><th>From</th><th>To</th><th>Depart On</th><th>Return on</th><th>Travel Days</th><th>Req Status</th><th>Confirmation Status</th><th>Confirmed Date</th>
  	<!-- <th>View</th>
  	<th>Print</th><th>Cancel</th> -->
  	<th>Action</th></tr>
  	
  	  	<logic:notEmpty name="travellist1">
  	<%int y=0; %>
  	<logic:iterate id="a" name="travellist1">
  	<%y++; %>
  	<tr><td><%=y%></td><td><a href="travelrequest.do?method=domesticRevert&requstNo=<bean:write name="a" property="requestNumber"/>&requststatus=<bean:write name="a" property="reqStatus"/>">${a.requestNumber}</a></td><td>${a.reqDate }</td><td>${a.typeOfTravel }</td><td>${a.fromPlace }</td><td>${a.toPlace }</td><td>${a.departOn }</td><td>${a.returnOn }</td><td>${a.travel_Days}</td><td>${a.reqStatus}</td>
  	<td>${a.confirmstatus }</td>	<td>${a.confirmdate }</td>

	<td>
		<div id="button">
		<ul>
			<li class="top" title="Point Over Me">Point Over Me</li>
			<li class="item"><a href="travelrequest.do?method=ViewMyrequest&requstNo=<bean:write name="a" property="requestNumber"/>&requststatus=<bean:write name="a" property="reqStatus"/>" title="View">View <img src="images/view.gif" width="16" height="16"/></a></li>
			<li class="item"><a href="travelrequest.do?method=PDFTravelgenerate&requstNo=<bean:write name="a" property="requestNumber"/>&requststatus=<bean:write name="a" property="reqStatus"/>" title="Generate PDF">Generate PDF <img src="images/pdf.png" width="16" height="16"/></a></li>
			
			
			<logic:notEqual value="Rescheduled" name="a" property="reqStatus">
			<logic:notEqual value="Self Cancelled" name="a" property="reqStatus">
			
			<li class="item"><a href="javascript:cancelrecord(${a.requestNumber})" title="Cancel">Cancel</a></li>
			</logic:notEqual>
			<li class="item"><a href="travelrequest.do?method=domesticReschedule&requstNo=<bean:write name="a" property="requestNumber"/>&requststatus=<bean:write name="a" property="reqStatus"/>" title="Re-Schedule">Re-Schedule</a></li>
			<logic:empty name="a" property="review_Rating">
			<logic:notEmpty name="a" property="confirmstatus">
			<li class="item"><a href="travelrequest.do?method=ViewMyrequest&requstNo=<bean:write name="a" property="requestNumber"/>&requststatus=<bean:write name="a" property="reqStatus"/>&review=1" title="Travel Confirmation">Travel Confirmation</a></li>
			</logic:notEmpty>
			
			</logic:empty>
			</logic:notEqual>
			
		</ul>
		</div>
	</td>
	<!-- End Drop Down Button -->
	</tr>
  	
  	</logic:iterate>
  	
  	<logic:empty name="travellist1">
  	
  	<tr><th colspan="11">No Records found..</th></tr>
  	
  	</logic:empty>
  	</logic:notEmpty>
  	
  	<logic:notEmpty name="travellist">
  	<%int y=0; %>
  	<logic:iterate id="a" name="travellist">
  	<%y++; %>
  	<tr><td><%=y%></td><td>${a.requestNumber }</td><td>${a.reqDate }</td><td>${a.typeOfTravel }</td><td>${a.fromPlace }</td><td>${a.toPlace }</td><td>${a.departOn }</td><td>${a.returnOn }</td><td>${a.travel_Days}</td><td>${a.reqStatus}</td>
  	<td>${a.confirmstatus }</td>	<td>${a.confirmdate }</td>

	<td>
		<div id="button">
		<ul>
			<li class="top" title="Point Over Me">Point Over Me</li>
			<li class="item"><a href="travelrequest.do?method=ViewMyrequest&requstNo=<bean:write name="a" property="requestNumber"/>&requststatus=<bean:write name="a" property="reqStatus"/>" title="View">View <img src="images/view.gif" width="16" height="16"/></a></li>
			<li class="item"><a href="travelrequest.do?method=PDFTravelgenerate&requstNo=<bean:write name="a" property="requestNumber"/>&requststatus=<bean:write name="a" property="reqStatus"/>" title="Generate PDF">Generate PDF <img src="images/pdf.png" width="16" height="16"/></a></li>
			
			<logic:notEqual value="Rescheduled" name="a" property="reqStatus">
			<logic:notEqual value="Self Cancelled" name="a" property="reqStatus">
			<li class="item"><a href="javascript:cancelrecord(${a.requestNumber})" title="Cancel">Cancel</a></li>
			</logic:notEqual>
			<li class="item"><a href="travelrequest.do?method=domesticReschedule&requstNo=<bean:write name="a" property="requestNumber"/>&requststatus=<bean:write name="a" property="reqStatus"/>" title="Re-Schedule">Re-Schedule</a></li>
			<logic:empty name="a" property="review_Rating">
			<logic:notEmpty name="a" property="confirmstatus">
			<li class="item"><a href="travelrequest.do?method=ViewMyrequest&requstNo=<bean:write name="a" property="requestNumber"/>&requststatus=<bean:write name="a" property="reqStatus"/>&review=1" title="Travel Confirmation">Travel Confirmation</a></li>
			</logic:notEmpty>
			
			</logic:empty>
			</logic:notEqual>
		</ul>
		</div>
	</td>
	<!-- End Drop Down Button -->
	</tr>
  	
  	</logic:iterate>
  	
  	<logic:empty name="travellist">
  	
  	<tr><th colspan="11">No Records found..</th></tr>
  	
  	</logic:empty>
  	</logic:notEmpty>
  	
  	</table><html:hidden name="travelRequestForm" property="total"/>
 				<html:hidden name="travelRequestForm" property="next"/>
 				<html:hidden name="travelRequestForm" property="prev"/>
 				<input style="visibility:hidden;" id="scnt" value="<bean:write property="startRecord"  name="travelRequestForm"/>"/>
				<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endRecord"  name="travelRequestForm"/>"/>
  		
  			</html:form>			
  </body>
</html>
