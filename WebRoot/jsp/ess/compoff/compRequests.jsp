<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="CC" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
    .green {
        color: green;
   		
    }

    .red    {
        color: red;
        text-decoration: blink;
}
</style>
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

function cancelperm(reqno)
{


var url="leave.do?method=selfcancelCompRecord&reqno="+reqno;
			document.forms[0].action=url;
			document.forms[0].submit();

}
function dynamicCompOffbal()
{
var year=document.forms[0].year.value;

var url="leave.do?method=displaycompreq&year="+year;
			document.forms[0].action=url;
			document.forms[0].submit();
}

</script>
</head>


<body>
<html:form action="leave" enctype="multipart/form-data">
<div align="center" id="messageID" style="visibility: visible;">
			<logic:present name="leaveForm" property="message">
				<font color="green" size="3"><b><bean:write name="leaveForm" property="message" /></b></font>
				
			</logic:present>
			<logic:present name="leaveForm" property="message2">
				<font color="red" size="3"><b><bean:write name="leaveForm" property="message2" /></b></font>
				
			</logic:present>
		</div>


		
		<table class="bordered" width="90%">
			<tr>
				<th colspan="6" align="left"><center>My Comp-Off Request</center></th>
				<td colspan="2"  width="15%">Year <font color="red" size="4">*</font></td>
  							<td colspan="2" width="15%" align="left" colspan="" >
								<html:select name="leaveForm" property="year" styleClass="content" onchange="dynamicCompOffbal()">
								<html:options name="leaveForm"  property="yearList"/>
								</html:select>
					</td>
			</tr>
		<tr>
<th>Req&nbsp;No</th><th>Request Date</th><th >&nbsp;&nbsp;&nbsp;&nbsp;Date</th><th>Reason</th><th>Pending Approver</th><th>Last Approver</th><th>Status</th><th>Balance_Status</th><th>View</th>
		<th>Cancel</th>
</tr>
<logic:notEmpty name="comp">
<logic:iterate id="c" name="comp">
<tr>

<td>${c.requestNumber}</td>
<td><bean:write name="c" property="submitDate"/></td>
<td ><bean:write name="c" property="startDate"/></td>

<td ><bean:write name="c" property="reason"/></td>

<td><bean:write name="c" property="papprover"/></td>
<td><bean:write name="c" property="lapprover"/></td>

<td><bean:write name="c" property="status"/></td>

<CC:choose>
            <CC:when test="${c.approveStatus =='Pending'}">
           
                <td class="red" align="center"><b><bean:write name="c" property="approveStatus"/></b></td> 
      
            
   </CC:when>
      <CC:otherwise>
           
                <td class="green"  align="center"><b><bean:write name="c" property="approveStatus"/></b></td> 
            
            
  </CC:otherwise> 
        </CC:choose>

		
<td>
<a  href="leave.do?method=viewcompoff&requstNo=<bean:write name="c" property="requestNumber"/>" ><img src="images/view.gif" width="28" height="28"/></a>
</td>
	<td>
						<logic:equal value="Pending" name="c" property="status">
						<html:button property="method" styleClass="rounded" style="width: 50px"  value="cancel" onclick="cancelperm(${c.requestNumber})"></html:button>
						</logic:equal>
						
					</td>
</tr>
		</logic:iterate>	
		</logic:notEmpty>
		<logic:notEmpty name="nocomp">
		<tr>
<td colspan="8">
<font color="red" size="3"><center>No Records </center></font>
</td>
</tr>
		
		</logic:notEmpty>
	
	</table>
		
	
		</html:form>
</body>		

</html>