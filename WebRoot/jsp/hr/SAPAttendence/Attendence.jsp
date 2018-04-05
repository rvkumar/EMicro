<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Attendance </title>

	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<script type="text/javascript" src="js/sorttable.js"></script>
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
	$('#month').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#toDate').datepick({dateFormat: 'dd/mm/yyyy'});
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
<script type="text/javascript">

	function displayAttendence(){
		
			
		
		
		var url="sapAttendence.do?method=displayAttendeceDetails";
		
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function displayAttendence1(){
		
			
		
		
		var url="sapAttendence.do?method=displayAttendeceDetails1";
		
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function showform()
	{
	
	
	var url="sapAttendence.do?method=display";
	document.forms[0].action=url;
	document.forms[0].submit();


	
	}
	function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
</script>	

<body>
<html:form action="/sapAttendence.do" enctype="multipart/form-data"  onsubmit="displayAttendence()">
<div id="wraper" align="center" >
				<logic:present name="sapAttendenceForm" property="message">
					<font color="red" size="3"><b><bean:write name="sapAttendenceForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="sapAttendenceForm" property="message1">
					<font color="Green" size="3"><b><bean:write name="sapAttendenceForm" property="message2" /></b></font>
				</logic:present>
			</div>

 <div align="center" >
 
 <html:hidden property="employeeNo" />
			<html:hidden property="payGroup" />
	<logic:notEmpty name="Approver">		
   Employee Selection: 
	&nbsp;&nbsp;
	<html:select property="empno" styleClass="content" styleId="filterId" onchange="showform()"  style="padding:3px;
    margin: 0;
    -webkit-border-radius:4px;
    -moz-border-radius:4px;
    border-radius:4px;
    -webkit-box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    -moz-box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    background: #f8f8f8;
    color:#0000;
    border:none;
    outline:none;
    display: inline-block;
    -webkit-appearance:none;
    -moz-appearance:none;
    appearance:none;
    cursor:pointer;">
	<html:option value="" >------ Self ------</html:option>
	<html:options name="sapAttendenceForm"  property="emplIdList" labelProperty="empLabelList"  />
	</html:select> 		
	</logic:notEmpty> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	
<%--		Month/Year:	<html:select property="dat" style="width:150px;border-color: #339933;border: 1px solid #ccc;-moz-border-radius: 10px;-webkit-border-radius: 10px;border-radius: 10px;-moz-box-shadow: 2px 2px 3px #666;-webkit-box-shadow: 2px 2px 3px #666;box-shadow: 2px 2px 3px #666;font-size: 11px;padding: 4px 7px;outline: 0;-webkit-appearance: none;">--%>
			Months/Year:	<html:select property="month" style="padding:3px;
    margin: 0;
    -webkit-border-radius:4px;
    -moz-border-radius:4px;
    border-radius:4px;
    -webkit-box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    -moz-box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    background: #f8f8f8;
    color:#0000;
    border:none;
    outline:none;
    display: inline-block;
    -webkit-appearance:none;
    -moz-appearance:none;
    appearance:none;
    cursor:pointer;">
			<%--<html:option value="Select">-- Select --</html:option>
			<html:option value="May13">May 2013</html:option>
			<html:option value="Apr13">April 2013</html:option>
			<html:option value="Mar13">March 2013</html:option>
			<html:option value="Feb13">February 2013</html:option>
			<html:option value="Jan13">January 2013</html:option>
		--%>
		
		 <html:options property="ar_id" labelProperty="ar_name" name="sapAttendenceForm"/>
		</html:select>
	&nbsp;&nbsp;
	<logic:notEmpty name="empselection">
	<input type="text" class="rounded" title="enter emp no" onkeypress="return isNumber(event)" name="textempno"/>
 </logic:notEmpty>
	&nbsp;&nbsp;
	
	<input type="text" style="display: none;"/>

	<html:button property="method" value="Submit" onclick="displayAttendence()" styleClass="rounded"></html:button>
	
	</h3></center>
   
    </div>
<br/>
	<hr/>
<br/>
	
	<logic:notEmpty name="attDataList" >
 <div class="bordered" id="personalInformation"  align="center" width="80%" >
	    
	    	<center> PH: Paid Holiday &nbsp;&nbsp; WO: Weekly Off &nbsp;&nbsp;SH: Special Holiday &nbsp;&nbsp;SL : Sick Leave &nbsp;&nbsp;&nbsp; EL : Earned Leave &nbsp;&nbsp;&nbsp;  CL : Casual Leave &nbsp;&nbsp;&nbsp;ML: Maternity Leave &nbsp;&nbsp;&nbsp;PM : Permission<br/>LWP: Leave Without Pay &nbsp;&nbsp;&nbsp;OD: OnDuty&nbsp;&nbsp;&nbsp;GS: General Shift&nbsp;&nbsp;&nbsp;FS: First Shift&nbsp;&nbsp;&nbsp;SS: Second Shift&nbsp;&nbsp;&nbsp;TS: Third Shift&nbsp;&nbsp;&nbsp;NS: Night Shift&nbsp;&nbsp;&nbsp;PP: Present&nbsp;&nbsp;&nbsp;AA: Absent
	    	<br/><font color="red"><big>*</big></font> Attendance Status Change <font color="red"><big>#</big></font> Manual Entry against forgot Swipe
	    	</small></center>
	    	
<div align="center">
<table class="bordered" width="80%" align="center"  >
<logic:notEqual value="" property="remarks" name="sapAttendenceForm">
<tr><th colspan="9"><center>Emp Name: ${sapAttendenceForm.remarks }</center></th></tr>
</logic:notEqual>
<tr><th colspan="9"><center>Attendance Details</center></th></tr>
<tr><th width="15%"><center>Date</center></th><th width="10%"><center>Day</center></th><th width="10%"><center>In Time</center></th><th width="10%"><center>Out Time</center></th><th width="10%"><center>In Status</center></th><th width="10%"><center>Out Status</center></th><th width="10%"><center>Punches</center></th><th width="10%"><center>Shift</center></th><th><center>Note</center></th></tr>
<logic:iterate id="abc" name="attDataList">

<c:choose>
<c:when test="${abc.day=='Sun'}">
<tr style="background-color: #7CB1C9">
<td><bean:write name="abc" property="date"/></td>
<td ><bean:write name="abc" property="day"/></td>
<logic:match value="#" name="abc" property="iNTIMEhash">
<td><bean:write name="abc" property="iNTIME"/><font color="red"><big> #</font></td>
</logic:match>
<logic:notMatch value="#" name="abc" property="iNTIMEhash">
<td><bean:write name="abc" property="iNTIME"/></td>
</logic:notMatch>

<logic:match value="#" name="abc" property="oUTTIMEhash">
<td><bean:write name="abc" property="oUTTIME"/><font color="red"><big> #</big></font></td>
</logic:match>
<logic:notMatch value="#" name="abc" property="oUTTIMEhash">
<td><bean:write name="abc" property="oUTTIME"/></td>
</logic:notMatch>

<logic:match value="*" name="abc" property="iNSTATUSstar">
<td><bean:write name="abc" property="iNSTATUS"/><font color="red"> <big>*</font></td>
</logic:match>
<logic:notMatch value="*" name="abc" property="iNSTATUSstar">
<td><bean:write name="abc" property="iNSTATUS"/></td>
</logic:notMatch>


<logic:match value="*" name="abc" property="oUTSTATUSstar">
<td><bean:write name="abc" property="oUTSTATUS"/><font color="red"><big> *</font></td>
</logic:match>
<logic:notMatch value="*" name="abc" property="oUTSTATUSstar">
<td><bean:write name="abc" property="oUTSTATUS"/></td>
</logic:notMatch>


<td><bean:write name="abc" property="allpunch"/></td>

<td>&nbsp;</td>
<td>&nbsp;</td>
  
  
</tr>
</c:when>

<c:when test="${abc.iNSTATUS=='SS'}">
<logic:equal value="WO" name="abc" property="iNTIME">
<tr style="background-color: #7CB1C9">
<td><bean:write name="abc" property="date"/></td>
<td ><bean:write name="abc" property="day"/></td>
<logic:match value="#" name="abc" property="iNTIMEhash">
<td><bean:write name="abc" property="iNTIME"/><font color="red"><big> #</font></td>
</logic:match>
<logic:notMatch value="#" name="abc" property="iNTIMEhash">
<td><bean:write name="abc" property="iNTIME"/></td>
</logic:notMatch>

<logic:match value="#" name="abc" property="oUTTIMEhash">
<td><bean:write name="abc" property="oUTTIME"/><font color="red"><big> #</font></td>
</logic:match>
<logic:notMatch value="#" name="abc" property="oUTTIMEhash">
<td><bean:write name="abc" property="oUTTIME"/></td>
</logic:notMatch>
<logic:match value="*" name="abc" property="iNSTATUSstar">
<td><bean:write name="abc" property="iNSTATUS"/><font color="red"><big> *</font></td>
</logic:match>
<logic:notMatch value="*" name="abc" property="iNSTATUSstar">
<td><bean:write name="abc" property="iNSTATUS"/></td>
</logic:notMatch>


<logic:match value="*" name="abc" property="oUTSTATUSstar">
<td><bean:write name="abc" property="oUTSTATUS"/><font color="red"><big> *</font></td>
</logic:match>
<logic:notMatch value="*" name="abc" property="oUTSTATUSstar">
<td><bean:write name="abc" property="oUTSTATUS"/></td>
</logic:notMatch>
<td><bean:write name="abc" property="allpunch"/></td>
<td><bean:write name="abc" property="shift"/></td>
<td>&nbsp;</td>
  
  
</tr>
</logic:equal>
<logic:notEqual value="WO" name="abc" property="iNTIME">
<tr >
<td><bean:write name="abc" property="date"/></td>
<td ><bean:write name="abc" property="day"/></td>
<logic:match value="#" name="abc" property="iNTIMEhash">
<td><bean:write name="abc" property="iNTIME"/><font color="red"><big> #</font></td>
</logic:match>
<logic:notMatch value="#" name="abc" property="iNTIMEhash">
<td><bean:write name="abc" property="iNTIME"/></td>
</logic:notMatch>

<logic:match value="#" name="abc" property="oUTTIMEhash">
<td><bean:write name="abc" property="oUTTIME"/><font color="red"><big> #</font></td>
</logic:match>
<logic:notMatch value="#" name="abc" property="oUTTIMEhash">
<td><bean:write name="abc" property="oUTTIME"/></td>
</logic:notMatch>

<logic:match value="*" name="abc" property="iNSTATUSstar">
<td><bean:write name="abc" property="iNSTATUS"/><font color="red"><big> *</font></td>
</logic:match>
<logic:notMatch value="*" name="abc" property="iNSTATUSstar">
<td><bean:write name="abc" property="iNSTATUS"/></td>
</logic:notMatch>


<logic:match value="*" name="abc" property="oUTSTATUSstar">
<td><bean:write name="abc" property="oUTSTATUS"/><font color="red"><big> *</font></td>
</logic:match>
<logic:notMatch value="*" name="abc" property="oUTSTATUSstar">
<td><bean:write name="abc" property="oUTSTATUS"/></td>
</logic:notMatch>
<td><bean:write name="abc" property="allpunch"/></td>
<td><bean:write name="abc" property="shift"/></td>
<td><bean:write name="abc" property="remarks"/></td>
  
  
</tr>
</logic:notEqual>
</c:when>
<c:otherwise>
<logic:empty name="abc" property="message" >
<tr >
<td><bean:write name="abc" property="date"/></td>
<td><bean:write name="abc" property="day"/></td>
<logic:match value="#" name="abc" property="iNTIMEhash">
<td><bean:write name="abc" property="iNTIME"/><font color="red"> <big>#</font></td>
</logic:match>
<logic:notMatch value="#" name="abc" property="iNTIMEhash">
<td><bean:write name="abc" property="iNTIME"/></td>
</logic:notMatch>

<logic:match value="#" name="abc" property="oUTTIMEhash">
<td><bean:write name="abc" property="oUTTIME"/><font color="red"> <big>#</font></td>
</logic:match>
<logic:notMatch value="#" name="abc" property="oUTTIMEhash">
<td><bean:write name="abc" property="oUTTIME"/></td>
</logic:notMatch>
<logic:match value="*" name="abc" property="iNSTATUSstar">
<td><bean:write name="abc" property="iNSTATUS"/><font color="red"><big> *</font></td>
</logic:match>
<logic:notMatch value="*" name="abc" property="iNSTATUSstar">
<td><bean:write name="abc" property="iNSTATUS"/></td>
</logic:notMatch>


<logic:match value="*" name="abc" property="oUTSTATUSstar">
<td><bean:write name="abc" property="oUTSTATUS"/><font color="red"> <big>*</font></td>
</logic:match>
<logic:notMatch value="*" name="abc" property="oUTSTATUSstar">
<td><bean:write name="abc" property="oUTSTATUS"/></td>
</logic:notMatch>
<td><bean:write name="abc" property="allpunch"/></td>
<td><bean:write name="abc" property="shift"/></td>
<td><bean:write name="abc" property="remarks"/></td>

</tr>
</logic:empty>
<logic:notEmpty name="abc" property="message">
<tr style="background-color: #E1A15D;">
<td><bean:write name="abc" property="date"/></td>
<td><bean:write name="abc" property="day"/></td>
<logic:match value="#" name="abc" property="iNTIMEhash">
<td><bean:write name="abc" property="iNTIME"/><font color="red"><big> #</font></td>
</logic:match>
<logic:notMatch value="#" name="abc" property="iNTIMEhash">
<td><bean:write name="abc" property="iNTIME"/></td>
</logic:notMatch>

<logic:match value="#" name="abc" property="oUTTIMEhash">
<td><bean:write name="abc" property="oUTTIME"/><font color="red"><big> #</font></td>
</logic:match>
<logic:notMatch value="#" name="abc" property="oUTTIMEhash">
<td><bean:write name="abc" property="oUTTIME"/></td>
</logic:notMatch>
<logic:match value="*" name="abc" property="iNSTATUSstar">
<td><bean:write name="abc" property="iNSTATUS"/><font color="red"> <big>*</font></td>
</logic:match>
<logic:notMatch value="*" name="abc" property="iNSTATUSstar">
<td><bean:write name="abc" property="iNSTATUS"/></td>
</logic:notMatch>


<logic:match value="*" name="abc" property="oUTSTATUSstar">
<td><bean:write name="abc" property="oUTSTATUS"/><font color="red"> <big>*</font></td>
</logic:match>
<logic:notMatch value="*" name="abc" property="oUTSTATUSstar">
<td><bean:write name="abc" property="oUTSTATUS"/></td>
</logic:notMatch>
<td><bean:write name="abc" property="allpunch"/></td>
<td><bean:write name="abc" property="shift"/></td>
<td><bean:write name="abc" property="remarks"/></td>
</tr>
</logic:notEmpty>
</c:otherwise>
</c:choose>

</logic:iterate>
<tr></tr>

</table></div></div>



</logic:notEmpty>

 <logic:notEmpty name="vb">
	    <br/>
	    
	    
	    <%--<table class="bordered">
	    <tr>
	    <th>Employee Code</th><th>Name</th><th>Department</th><th>Designation</th>
	    </tr>
	    
	    <tr class="trrem">
	    <td align="center"><bean:write property="empcode1" name="attendenceForm"/></td><td align="center"><bean:write property="name" name="attendenceForm"/></td><td align="center"><bean:write property="department" name="attendenceForm"/></td><td align="center"><bean:write property="designation" name="attendenceForm"/></td>
	    </tr>
	    </table>
	    <br/>
	    --%><br/>
	    <div class="bordered" id="personalInformation"  align="center" style="width: 80%">
	    
	    	<center> &#10004;<small>: Present &nbsp;&nbsp;&nbsp;</small> &#10006;<small>: Absent &nbsp;&nbsp;&nbsp; WO: Weekly Off &nbsp;&nbsp;&nbsp; SL : Sick Leave &nbsp;&nbsp;&nbsp; HL: Holiday &nbsp;&nbsp;&nbsp; CL : Casual Leave
	    	<br/>Click on any Column Header to sort</small></center>
	    
	    
	    <table class="sortable" width="80%" align="center">
	    <tr>    
	    <th>Date</th><th>Day</th><th>In Time</th><th>Status</th><th>Out Time</th><th>Status</th><th>Note</th></tr>

    	<%--<logic:iterate id="records" name="arrayl">
		    <tr class="trrem">
		    <td align="center"><bean:write property="day" name="records"/></td>
		    <td align="center"><bean:write property="getday" name="records"/></td>
		    <td align="center"><bean:write property="arr" name="records"/></td>
		    <td align="center"><bean:write property="dep" name="records"/></td>
		    <td align="center"><bean:write property="arrdesc" name="records"/></td>
		   	<td align="center"><bean:write property="depdesc" name="records"/></td>
		    </tr>
	    </logic:iterate>
	    
	    --%>
	    
	    <logic:iterate id="records" name="vb">
		    
		    <c:choose>
  				
  				<c:when test="${records.getday=='Sun'}">
		    
		    <tr class="trrem" style="background-color: #BDBDBD">
		    
		    <td align="center" style="font-weight: bold"><bean:write property="day" name="records"/></td>
  			<td align="center" style="font-weight: bold"><bean:write property="getday" name="records"/></td>
		    <td align="center" style="font-weight: bold"><bean:write property="arr" name="records"/></td>
		    <td align="center" style="font-weight: bold"><bean:write property="dep" name="records"/>&nbsp;</td>
		  <td align="center" style="font-weight: bold">&nbsp;</td>
		   	
		   	<c:choose>
  				<c:when test="${records.arrdesc=='P '}">
    			<td align="center"><img src="images/Present.png" title="Present"/></td>
  				</c:when>
  				
  				<c:when test="${records.arrdesc=='A '}">
    			<td align="center"><img src="images/Absent.png" title="Absent"/></td>
  				</c:when>

  				<c:otherwise>
    			<td align="center" style="font-weight: bold"><bean:write property="arrdesc" name="records"/></td>
  				</c:otherwise>
				</c:choose>
		   	
		   	
		   	<c:choose>
  				<c:when test="${records.depdesc=='P '}">
    			<td align="center"><img src="images/Present.png" title="Present"/>&nbsp;</td>
  				</c:when>
  				
  				<c:when test="${records.depdesc=='A '}">
    			<td align="center"><img src="images/Absent.png" title="Absent"/>&nbsp;</td>
  				</c:when>

  				<c:otherwise>
    			<td align="center" style="font-weight: bold"><bean:write property="depdesc" name="records"/>&nbsp;</td>
  				</c:otherwise>
				</c:choose>
		   	
		   
		    </tr>
		    
		    </c:when>
		    
		    <c:otherwise>
		    
		    <tr class="trrem">
		    
		    
		   
		    <td align="center"><bean:write property="day" name="records"/></td>
  			<td align="center"><bean:write property="getday" name="records"/></td>
		    <td align="center"><bean:write property="arr" name="records"/></td>
		    
		    
		   	
		   	<c:choose>
  				<c:when test="${records.arrdesc=='P '}">
    			<td align="center"><img src="images/Present.png" title="Present"/></td>
  				</c:when>
  				
  				<c:when test="${records.arrdesc=='A '}">
    			<td align="center"><img src="images/Absent.png" title="Absent"/></td>
  				</c:when>

  				<c:otherwise>
    			<td align="center"><bean:write property="arrdesc" name="records"/></td>
  				</c:otherwise>
				</c:choose>
		   	
		   	<td align="center"><bean:write property="dep" name="records"/>&nbsp;</td>
		   	<c:choose>
  				<c:when test="${records.depdesc=='P '}">
    			<td align="center"><img src="images/Present.png" title="Present"/>&nbsp;</td>
  				</c:when>
  				
  				<c:when test="${records.depdesc=='A '}">
    			<td align="center"><img src="images/Absent.png" title="Absent"/>&nbsp;</td>
  				</c:when>

  				<c:otherwise>
    			<td align="center"><bean:write property="depdesc" name="records"/>&nbsp;</td>
  				</c:otherwise>
				</c:choose>
		   	
		    </tr>
		    
		    
		    </c:otherwise>
		    
		    
		    </c:choose>
	    </logic:iterate>

	    <br/>
	    
	    </table>
	    </div>
	    
    </logic:notEmpty>






</html:form>
</body>
</html>	