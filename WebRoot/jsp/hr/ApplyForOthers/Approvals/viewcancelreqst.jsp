<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>

<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />



<title>Home Page</title>
<script language="javascript">
function changeStatus(id,type,status){

var name=document.forms[0].employeeNo.value;
var ids=document.forms[0].id.value;
	
		var r = confirm("Are you sure you want to "+status+" : "+name+" request with Request No. "+ids+"");
if (r == true) {

document.forms[0].action="hrApprove.do?method=cancelRequest&ReqNo="+id+"&ReqType="+type+"&status="+status;
document.forms[0].submit();
    
} else {
    return false;
}
	
		
	}
	
	</script>
</head>
<body >

				<div align="center">
					<logic:present name="hrApprovalForm" property="message">
						<font color="red">
							<bean:write name="hrApprovalForm" property="message" />
						</font>
					</logic:present>
				</div>
				<html:form action="/hrApprove.do" enctype="multipart/form-data">
				
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

	<logic:notEmpty name="submitDetails">
	<html:hidden property="id"/>
	<html:hidden property="employeeNo"/>
					
						<table class="bordered content" width="80%">
							<tr>
								<th colspan="6" style="text-align: center;"><big>Leave Record Display</big></th> 
  							</tr>
						
  							<td >Type <font color="red" size="4">*</font></td>
  							
							<td >
								<bean:write name="hrApprovalForm" property="leaveType"/>
							</td>
							<td >Applied&nbsp;Date<font color="red" size="4">*</font></td>
  							
							<td >
								<bean:write name="hrApprovalForm" property="submitDate"/>
							</td>
						</tr>
						<tr>
							<td >From&nbsp;Date<font color="red" size="4">*</font></td>
							
							<td >
								<bean:write name="hrApprovalForm" property="startDate"/>
							</td>
							<td >Duration <font color="red" size="4">*</font></td>
							
							<td >
							<bean:write name="hrApprovalForm" property="startDurationType"/>
							</td>
						</tr>
						<tr>
							<td >To&nbsp;Date<font color="red" size="4">*</font></td>
							
							<td >
								<bean:write name="hrApprovalForm" property="endDate"/>
							</td>
							<td >Duration<font color="red" size="4">*</font></td>
							
							<td >
								<bean:write name="hrApprovalForm" property="endDurationType"/>
							</td>
						</tr>
						
						<tr>
							<td >No of Days <font color="red" size="4">*</font></td>
							
							
							<td align="left"  width="30%">
							<div id="noOfDaysDiv">
								<bean:write name="hrApprovalForm" property="noOfDays"/>
							</div>
							</td>
							
						
							<td > Reason   Type<font color="red" size="4">*</font></td>
							
							<td >
								<html:select name="hrApprovalForm" property="reasonType" styleClass="content" disabled="true">
									<html:option value="">--Select--</html:option>
							<html:options name="hrApprovalForm" property="leaveReason" labelProperty="leaveDetReason"/>
								</html:select>
							</td>
						</tr>	
					<tr>
							<th colspan="6"> Detailed Reason<font color="red" size="3">*</font> : </th>
						</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<bean:define id="content" name="hrApprovalForm"
									property="reason" />
							<%out.println(content.toString());%>
						
							</td>
						</tr>

				<logic:notEmpty name="documentDetails">
						<tr >
							<th colspan="6">
								Uploaded Documents: 
							</th>
						</tr>
						
						<logic:notEmpty name="documentDetails">
						    <tr>
							
							<td align="left" class="lft style1" colspan="4">
									<bean:define id="file" name="hrApprovalForm"
										property="documentName" />
								    <%
										String s = file.toString();
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) 
										{
									%>
									<a href="/EMicro Files/ESS/Leave/<%=v[i]%>" target="_blank"><%=v[i]%></a>
									<%
									}
									%>	
									</td>
									</tr>	
				      </logic:notEmpty>
				      
				   
							
							
				</logic:notEmpty>
				<tr><th colspan="6">User Remarks </th>
			</tr>
						<tr><td colspan="4">	<html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent" disabled="true" name="hrApprovalForm"></html:text>
						</td></tr>
						
						<tr><th colspan="6">Comments </th>
			</tr>
						<tr><td colspan="4">	<html:textarea property="comments" styleClass="text_field" style="width:100%" styleId="remarkContent" name="hrApprovalForm"></html:textarea>
						</td></tr>
						
					<tr>
					
						<td colspan="4" ><center>
				<logic:notEqual value="2" property="status" name="hrApprovalForm"><logic:notEmpty name="cancelbutton"><html:button property="method" value="Cancel Leave" styleClass="rounded" style="width: 100px" onclick="changeStatus('${hrApprovalForm.id}','Leave',this.value)"/>
				<html:button property="method" value="Reject" styleClass="rounded" style="width: 100px" onclick="changeStatus('${hrApprovalForm.id}','Leave',this.value)"/>
				</logic:notEmpty></logic:notEqual>		
	             <logic:notEmpty name="cancelbutton"> <html:button property="method" value="Close" styleClass="rounded" style="width: 100px" onclick="window.history.go(-1)"/></logic:notEmpty>
	                  <logic:empty name="cancelbutton"> <html:button property="method" value="Close" styleClass="rounded" style="width: 100px" onclick="window.history.go(-2)"/></logic:empty>	</center></td>
				</tr>
					    
		                </table>

</br>
<logic:notEmpty name="leave">
    <table class="bordered content"><tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="abc" name="parallelapprovers">
	<tr>
	<td>${abc.approver }&nbsp;</td>
	<td>${abc.appDesig }&nbsp;</td>
	<td>${abc.status }&nbsp;</td>
	<td>${abc.approvedDate }&nbsp;</td>
	<td>${abc.comments }&nbsp;</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
			
					
					
					
					</logic:notEmpty>
            </html:form>
</body>
</html>