<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="com.microlabs.ess.form.LeaveForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<title>Home Page</title>

<script language="javascript">
function applycancel(id,status)

{

if(status!="Apply")
{
alert("You cannot Cancel this request since it has not been Updated yet");	
		 return false;

}

if(document.forms[0].remark.value==""){
		alert("Please Add Some comments");
		 document.forms[0].remark.focus();
		 return false;
		}
		
			var r = confirm("Are you sure you want to Apply for Cancellation");
if (r == true) {

document.forms[0].action="permission.do?method=submitcancelRequest&requestNo="+id;
document.forms[0].submit();
    
} else {
    return false;
}
		


}
function onClose()
{
var url="permission.do?method=displaycancellist";
			document.forms[0].action=url;
			document.forms[0].submit();
			
			}


function changeStatus(id,type){

var name=document.forms[0].employeeNo.value;
var ids=document.forms[0].id.value;
		
		
		var r = confirm("Are you sure you want to cancel "+name+" request with Request No. "+ids+"");
if (r == true) {

   document.forms[0].action="hrApprove.do?method=cancelpermissionRequest&ReqNo="+id+"&ReqType="+type;
document.forms[0].submit();
    
} else {
    return false;
}
	
		
	}
</script>

</head>
  
  <body>
<html:form action="permission">

<html:hidden property="id" name="hrApprovalForm"/>
	<html:hidden property="employeeNo" name="hrApprovalForm"/>

			<div align="center">
				
				<logic:present name="hrApprovalForm" property="message">
					<font color="Green" size="3"><b><bean:write name="hrApprovalForm" property="message" /></b></font>
				</logic:present>
			</div>
			
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
			
	<table class="bordered content" width="80%">
		 <tr><th  colspan="6" align="center">
					 Permission Form </th>
  						</tr>
						<tr>
						<td width="10%">Permission Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%" colspan="">
											<bean:write name="hrApprovalForm" property="date"/>
				
								
							</td>
							
								<td>Type</td>
							<td align="left" width="34%" >
											<bean:write name="hrApprovalForm" property="type"/>
				
								
							</td>
							</tr>
							<TR>
			<td>				
<p> From Time: <font color="red" size="3">*</font></td><td>	<bean:write name="hrApprovalForm" property="startTime"/>
</td>
<td><p>  To Time: <font color="red" size="3">*</font></td><td><bean:write name="hrApprovalForm" property="endTime"/>
		</td></tr>
		<tr><td>Req Date</td><td colspan="3"><bean:write name="hrApprovalForm" property="reqdate"/></td></tr>
			 <tr><th  colspan="6" align="center">
					 Enter Your Reason Here<font color="red" size="2">*</font>  </th>
  						<tr>
				</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="109" rows="9" readonly="true" name="hrApprovalForm"></html:textarea>
							</td>
						</tr>
						
						<tr><th colspan="6">User Remarks</th></tr>
						
						<tr><td colspan="4">	<html:textarea property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"  readonly="true" name="hrApprovalForm"></html:textarea>
						</td></tr>
									<tr><th colspan="6">Comments </th>
			</tr>
						<tr><td colspan="4">	<html:textarea property="comments" styleClass="text_field" style="width:100%" styleId="remarkContent" name="hrApprovalForm"></html:textarea>
						</td></tr>
			<tr><td colspan="6" style="border:0px; text-align: center;">
<tr>
					
						<td colspan="4" ><center>
				 <logic:notEqual value="2" property="status" name="hrApprovalForm"> <logic:notEmpty name="cancelbutton"><html:button property="method" value="Cancel Permission" styleClass="rounded" style="width: 100px" onclick="changeStatus('${hrApprovalForm.requestNumber}','Permission')"/>		</logic:notEmpty></logic:notEqual>			
	             <logic:notEmpty name="cancelbutton"> <html:button property="method" value="Close" styleClass="rounded" style="width: 100px" onclick="window.history.go(-1)"/></logic:notEmpty>
	                  <logic:empty name="cancelbutton"> <html:button property="method" value="Close" styleClass="rounded" style="width: 100px" onclick="window.history.go(-2)"/></logic:empty>	</center></td></table>
				<br/>
		
					
							<logic:notEmpty name="ApproverList">
		 <div align="left" class="bordered ">
			<table width="100%"   class="sortable">
			<tr>
				<th style="text-align:left;"><b>Type</b></th>
				<th style="text-align:left;"><b>Emp Name</b></th>	
				<th style="text-align:left;"><b>Designation</b></th>
				<th style="text-align:left;"><b>Status</b></th>
				<th style="text-align:left;"><b>Date</b></th>
					<th style="text-align:left;"><b>Comments</b></th>
			</tr>
			<logic:iterate id="abc" name="ApproverList">
			<tr>
			
			<td>${abc.appType}</td>
			<td>${abc.approver}</td>
			<td>${abc.appDesig}</td>
			<td>&nbsp;${abc.status }</td>
			<td>&nbsp;${abc.approvedDate }</td>
			<td>&nbsp;${abc.comments }</td>
			</tr>
			</logic:iterate>
			</table>
		</div>
		</logic:notEmpty>
	
	
	
	
	
	</table>








</html:form>
  </body>
</html>
