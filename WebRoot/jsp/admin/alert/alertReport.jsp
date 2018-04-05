<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

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

</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript">
function createType()
{
var URL="alert.do?method=addnewAlert";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function editAlert(id)
{ 

var URL="alert.do?method=getAlert&id="+id;

		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function nextRecord()
{
     
	document.forms[0].action="alert.do?method=nextRecord";
	document.forms[0].submit();

}
function previousRecord()
{
    
	document.forms[0].action="alert.do?method=previousRecord";
	document.forms[0].submit();

}
function searchAlertList(){
document.forms[0].action="alert.do?method=displayReport";
	document.forms[0].submit();
}
</script>	
</head>
<body>
<html:form action="/alert.do" enctype="multipart/form-data" onsubmit="searchMaterials(); return false;">
<br/>
<table align="left" class="bordered">
<%-- <tr>

<th>Location <font color="red">*</font></th>
<td align="left">
	<html:select name="alertForm" property="locationId" multiple="true">
							<html:option value="">--Select--</html:option>
							<html:options name="alertForm" property="locationIdList" labelProperty="locationLabelList"/>
     </html:select>
</td>

<th>Department<font color="red">*</font></th>
<td>
<html:select name="alertForm" property="department" multiple="true"  >
			   	<html:option value="">--Select--</html:option>
					<html:options name="alertForm" property="deptIdList" labelProperty="deptLabelList"/>
				</html:select> 
</td>


<th>Category</th>
<td align="left">
	<html:select property="cat" name="alertForm" multiple="true">
				<html:option value="">--Select--</html:option>
				<html:option value="1">Office Staff</html:option>
				<html:option value="2">Field Staff</html:option>
				<html:option value="3">Workers - Permanent</html:option>
				<html:option value="4">Workers - Temporary</html:option>
				<html:option value="5">Consultants</html:option>
				<html:option value="6">Workers - Union</html:option>
				<html:option value="7">Factory Staff</html:option>
				</html:select>
</td>
</tr>

 --%>

<tr>
<th>Start Date<font color="red">*</font></th><td> <html:text property="fromDate"  styleId="popupDatepicker" styleClass="text_field"  readonly="true"/></td>
<th>Start Time</th><td><html:text property="fromTime"  styleId="timeFrom"   size="15"/></td>
<th>End Date<font color="red">*</font></th><td> <html:text property="toDate"  styleId="popupDatepicker2" styleClass="text_field"  readonly="true"/></td>
<th>End Time</th><td><html:text property="toTime"  styleId="timeTo"   size="15"/> </td>

</tr>
<tr>
<td colspan="12"><center> <html:button property="method" value="Search" onclick="searchAlertList()" styleClass="rounded"/> &nbsp;&nbsp;
<input type="reset" value="Clear" class="rounded" onclick="clearSearch()"/></center>
</td>
</tr>


<tr>
<td colspan="12">
<html:button property="method" value="Create"   onclick="createType()" styleClass="rounded" style="width: 80px;"></html:button>
</td> 
</tr>

 </table>
 <div>&nbsp;</div>
<div align="center">  
					
							<logic:notEmpty name="displayRecordNo">
	 							<logic:notEmpty name="veryFirst">
	 								&nbsp;<a href="#"><img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/></a>&nbsp;
	 							</logic:notEmpty>
								<logic:notEmpty name="disablePreviousButton">
									&nbsp;<a href="#"><img src="images/disableLeft.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="previousButton">
									&nbsp;<a href="#"><img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
									&nbsp;<bean:write property="startRecord"  name="alertForm"/>&nbsp;-&nbsp;
									<bean:write property="endRecord"  name="alertForm"/>&nbsp;
								<logic:notEmpty name="nextButton">
									&nbsp;<a href="#"><img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="disableNextButton">
									&nbsp;<a href="#"><img src="images/disableRight.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="atLast">
									&nbsp;<a href="#"><img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/></a>
								</logic:notEmpty>
								
						
								</logic:notEmpty>
						</div>
	<html:hidden property="totalRecords" name="alertForm"/>
	<html:hidden property="startRecord" name="alertForm"/>
	<html:hidden property="endRecord" name="alertForm"/>



<%--<br/>
<br/>
  <table align="left">
						<tr><td align="center">
					
							<logic:notEmpty name="displayRecordNo">
	 							<logic:notEmpty name="veryFirst">
	 								&nbsp;<a href="#"><img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/></a>&nbsp;
	 							</logic:notEmpty>
								<logic:notEmpty name="disablePreviousButton">
									&nbsp;<a href="#"><img src="images/disableLeft.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="previousButton">
									&nbsp;<a href="#"><img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
									&nbsp;<bean:write property="startRecord"  name="alertForm"/>&nbsp;-&nbsp;<bean:write property="endRecord"  name="alertForm"/>&nbsp;
								<logic:notEmpty name="nextButton">
									&nbsp;<a href="#"><img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="disableNextButton">
									&nbsp;<a href="#"><img src="images/disableRight.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="atLast">
									&nbsp;<a href="#"><img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/></a>
								</logic:notEmpty>
							
								</logic:notEmpty>
								</td>
							</tr>
						</table> --%>
 <logic:notEmpty name="reservedList">
<br/>
<table class="sortable bordered"> 
<%int i=1; %>
<tr>
	<th style="width:35px;">Sl No.</th> <th style="width:100px;">Title</th><th style="width:150px;">Subject</th><th style="width:10px;">Start Date</th><th style="width:20px;">Start Time</th>
	<th style="width:20px;">End Date</th><th style="width:20px;">End Time</th> <th style="width:50px;">Edit</th>
</tr>
<logic:iterate id="master" name="reservedList">
	<tr>
	<td>
	<%= i %>
	</td>
	<td>
	${master.headLines1 }
	</td>
	<td>
	${master.subject1 }
	</td>
	
	
	<td>
	${master.fromTime1 }
	</td>
	<td>
	${master.fromDate1 }
	</td>
	
	<td>
	${master.toTime1 }
	</td>
	<td>
	${master.toDate1 }
	</td>
	<td>
	<img src="images/edit1.jpg" onclick="editAlert('${master.reqNo}')"/>
	</td>
	
	</tr>
	<%i++; %>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="noRecords">
<table class="sortable bordered"> 
<tr>
	<th style="width:100px;">Sl No.</th> <th style="width:100px;">Title</th><th style="width:50px;">Subject</th><th style="width:100px;">Start Date</th><th style="width:100px;">Start Time</th>
	<th style="width:100px;">End Date</th><th style="width:100px;">End Time</th> <th style="width:50px;">Edit</th>
</tr>
<tr>
<td colspan="6"><center><font color="red">Search records are not found. </font></center></td>
</tr>
</table>
</logic:notEmpty>
 <%--  <html:hidden name="alertForm" property="total"/>
 				<html:hidden name="alertForm" property="next"/>
 				<html:hidden name="alertForm" property="prev"/> --%> 
</html:form> 
</body>
</html>	