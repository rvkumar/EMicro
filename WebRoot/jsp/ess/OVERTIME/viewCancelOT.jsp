<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
 
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
    
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function closeleav()

{

document.forms[0].action="leave.do?method=displayOTCancelList";
document.forms[0].submit();
}

function applycancel(id,status)

{



if(status!="Apply")
{
alert("You cannot Cancel this request since it has not been Updated yet");	
		 return false;

}

if(document.forms[0].comments.value==""){
		alert("Please Add Some comments");
		 document.forms[0].comments.focus();
		 return false;
		}
		
			var r = confirm("Are you sure you want to Apply for Cancellation");
if (r == true) {

document.forms[0].action="leave.do?method=submitOTcancelRequest&requstNo="+id;
document.forms[0].submit();
    
} else {
    return false;
}
		


}

</script>
</head>


<body>
<html:form action="leave" enctype="multipart/form-data">
	<div align="center">
					<logic:present name="leaveForm" property="message">
						<font color="red">
							<bean:write name="leaveForm" property="message" />
						</font>
					</logic:present>
				</div>
<html:hidden property="id"/>
<logic:notEmpty name="attDataList" >
 <div class="bordered" id="personalInformation"  align="center" width="80%" >
	    
	    	<center> PH: Paid Holiday &nbsp;&nbsp; WO: Weekly Off &nbsp;&nbsp;SL : Sick Leave &nbsp;&nbsp;&nbsp; EL : Earned Leave &nbsp;&nbsp;&nbsp;  CL : Casual Leave &nbsp;&nbsp;&nbsp;ML: Maternity Leave &nbsp;&nbsp;&nbsp;<br/>LWP: Leave Without Pay &nbsp;&nbsp;&nbsp;OD: OnDuty
	    	</small></center>
	    	
<div align="center">
<table class="sortable" width="80%" align="center"  >
<tr><th colspan="7"><center>Attendance Details</center></th></tr>
<tr><th width="15%"><center>Date</center></th><th width="15%"><center>Day</center></th><th width="15%"><center>In Time</center></th><th width="15%"><center>Out Time</center></th><th width="15%"><center>In Status</center></th><th width="15%"><center>Out Status</center></th><th><center>Note</center></th></tr>
<logic:iterate id="abc" name="attDataList">

<c:choose>
<c:when test="${abc.day=='Sun'}">
<tr style="background-color: #7CB1C9">
<td><bean:write name="abc" property="date"/></td>
<td ><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td>&nbsp;</td>
  
  
</tr>
</c:when>

<c:when test="${abc.day=='Sat'}">
<logic:equal value="WO" name="abc" property="iNTIME">
<tr style="background-color: #7CB1C9">
<td><bean:write name="abc" property="date"/></td>
<td ><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td>&nbsp;</td>
  
  
</tr>
</logic:equal>
<logic:notEqual value="WO" name="abc" property="iNTIME">
<tr >
<td><bean:write name="abc" property="date"/></td>
<td ><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td><bean:write name="abc" property="remarks"/></td>
  
  
</tr>
</logic:notEqual>
</c:when>
<c:otherwise>
<logic:empty name="abc" property="message" >
<tr >
<td><bean:write name="abc" property="date"/></td>
<td><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td><bean:write name="abc" property="remarks"/></td>

</tr>
</logic:empty>
<logic:notEmpty name="abc" property="message">
<tr style="background-color: #E1A15D;">
<td><bean:write name="abc" property="date"/></td>
<td><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td><bean:write name="abc" property="remarks"/></td>
</tr>
</logic:notEmpty>
</c:otherwise>
</c:choose>

</logic:iterate>
<tr></tr>

</table></div></div>



</logic:notEmpty>
<br/>

		
		<table class="bordered" width="90%">
			<tr>
				<th colspan="5" align="left"><center>OT Form</center></th>
			</tr>
		<tr>
			<td width="15%">Worked Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%">
							<bean:write name="leaveForm" property="startDate"/>
							</td>
		<%-- 	<td width="15%">To Date <font color="red" size="3">*</font></td>
							
							<td align="left" width="34%">
							<bean:write name="leaveForm" property="endDate"/>
							</td>	 --%>			
							
			</tr>
					<tr>
				<th colspan="4"> Detailed Reason<font color="red" size="2"> * </font>  
						</th>
						</tr>
			
			<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="100" rows="10" style="height: 119px;color:black; width: 874px" disabled="true" ></html:textarea>
						
							</td>
						</tr>
						<tr><th colspan="6">Comments</th></tr>
						
						<tr><td colspan="4">	<html:textarea property="comments" styleClass="text_field" style="width:100%" styleId="remarkContent" onkeyup="this.value = this.value.replace(/'/g,'`')"></html:textarea>
						</td></tr>
			
		</table>
	
		
		<table  class="bordered"  width="90%" align="left" id="compTable" >
<tr>
            <th width="50px" >SL.no</th>
            <th width="125px" >Employee No.</th>
            <th width="125px" >Name</th>
            <th width="85px" >Department</th>
            <th width="125px" >Designation</th>
            <th width="125px" >No. Of Hours</th>
        
           
     
            
            
            

        </tr>
       
        <logic:iterate id="a" name="comp">
        
       <tr>
       
       <td><bean:write name="a" property="slmindur"/></td>       
       <td><bean:write name="a" property="employeeNumber"/> </td>       
       <td><bean:write name="a" property="employeeName"/></td>       
       <td><bean:write name="a" property="department"/></td>
       <td><bean:write name="a" property="designation"/></td>
       <td><bean:write name="a" property="nofhrs"/></td>
     
       </tr>
       </logic:iterate>
            </table>
	<DIV>&nbsp;</DIV>
	
	
	
	<table>
	<tr><td colspan="6" style="border:0px; text-align: center;">
			
			
			
					<logic:notEmpty name="cancelbutton">
					<html:button property="method" value="Apply For Cancellation" styleClass="rounded" style="width: 174px" onclick="applycancel(${leaveForm.id} ,'${leaveForm.appType}')"/>
					</logic:notEmpty>
			<input type="button" class="rounded" value="Close" onclick="closeleav()"  /></td>
			
			</tr></table>
				 <logic:notEmpty name="appList">
	<div>&nbsp;</div>
    <table class="bordered">
    <tr>
    <th colspan="5"><center>Approval Status</center></th>
    </tr>
    <tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="abc" name="appList">
	<tr>
	<td>${abc.approver }</td>
	<td>${abc.designation }</td>
	<td>${abc.approveStatus }</td>
	<td>${abc.approveDate }</td>
	<td>${abc.comments }</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
	

	
		
		
	
		</html:form>
</body>		

</html>