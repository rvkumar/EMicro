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
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style>
 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}
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
function changeStatus(elem){

  
	var elemValue = elem.value;
	
	if(elemValue=="Reject")
	{
		if(document.forms[0].remark.value==""){
		alert("Please Add Some Comments");
		 document.forms[0].remark.focus();
		 return false;
		}
	}
	var reqId = document.getElementById("reqId").value;;
	var reqType = document.getElementById("reqType").value;
	var url="approvals.do?method=statusChangePermission&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue;

	document.forms[0].action=url;
	document.forms[0].submit();
}

function getCurrentRecord(){

var reqId = document.getElementById("reqId").value;
	var reqType = document.getElementById("reqType").value;
	var totalRecords=document.getElementById("totalReco").value;
	var scnt=document.getElementById("scnt").value;
	var ecnt=document.getElementById("ecnt").value;
		var filterby=document.getElementById("filterby").value;
	
	var url="approvals.do?method=curentRecord&reqId="+reqId+"&reqType="+reqType+"&totalRecord="+totalRecords+"&scnt="+scnt+"&ecnt="+ecnt+"&filterby="+filterby;
	
	document.forms[0].action=url;
	document.forms[0].submit();


}


</script>

</head>
  
  <body>
  			<html:form action="/approvals.do" enctype="multipart/form-data">
				
				<div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>

			
	<table class="bordered content" width="80%">
		 <tr><th  colspan="8" align="center">
					 Permission Form </th>
  						</tr>
						<tr>
						<td width="10%">Permission Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%" colspan="">
											<bean:write name="approvalsForm" property="date"/>
				
								
							</td>
							
									<td width="10%">Type<font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%" colspan="3">
											<bean:write name="approvalsForm" property="type"/>
				
								
							</td>
							
							</tr>
							<tr>
			<td >				
<p> Time: <font color="red" size="3">*</font></td>
<td >	<bean:write name="approvalsForm" property="startTime"/></td>
 <logic:equal value="Early" name="approvalsForm" property="type"><td><p>  To Time: <font color="red" size="3">*</font></td>

<td colspan="2"><bean:write name="approvalsForm" property="endTime"/></td></logic:equal></tr>
 <tr>
<td>				
<p>  Swipe Type: <font color="red" size="3">*</font></td><td >	<bean:write name="approvalsForm" property="swipetype"/>
</td>

<td width="15%">Request Date</td>
						
							<td align="left" width="34%"><bean:write name="approvalsForm" property="requestDate"/>
								
							</td>	</tr>
							
									
			 <tr><th  colspan="8" align="center">
			 Reason<font color="red" size="2">*</font>  </th>
  								</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
              <bean:write name="approvalsForm" property="reason"/>						
	</td>
						</tr>
						
							   
		          	<tr><th colspan="6">Comments </th>
			</tr>
			<tr>
			<td colspan="6"><html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"></html:text></td></tr>
			<tr><td colspan="6" style="border:0px; text-align: center;">
   <logic:notEmpty name="approveButton">
	<input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;
	</logic:notEmpty>
	<logic:notEmpty name="rejectButton">
			<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  /></td>
			
			</tr>
	</table>
	
	<br/>
	<logic:notEmpty name="punchlist">
<table class ="bordered">
<tr>
<th colspan="8">Punch Timings</th>
</tr>
<tr><th>Pernr</th><th>Date</th><th>Day</th><th>In time</th><th>Out time</th><th>In Status</th><th>Out Status</th><th>Shift</th></tr>

                  <logic:iterate id="abc1" name="punchlist">
<tr><td>${abc1.employeeNo}</td><td>${abc1.date}</td><td>${abc1.day}</td><td>${abc1.iNTIME}</td><td>${abc1.oUTTIME}</td><td>${abc1.iNSTATUS}</td><td>${abc1.oUTSTATUS}</td><td>${abc1.shift}</td></tr>
</logic:iterate>
</table>
</logic:notEmpty>
<br/> <logic:notEmpty name="llist">
		    <table class="bordered" >
                 
                     <tr>   <th colspan="9"><center>Entry List</center></th></tr>
                  <tr>
                  
                    <th >#</th>
                    <th>Pernr</th>
                    <th>Name </th>
                    <th>Swipe Date </th>
                    <th>Swipe Type</th>
                     <th>Reason Type</th>                    
                    <th>Swipe Time</th>
                    <th>Remarks</th>
                    <th>Created Date</th>
                    
                  </tr>
                
                  <%int i=0; %>
                  <logic:iterate id="abc1" name="llist">
                   <%i++; %>
             
                <tr>               
                    <td ><%=i %></td>     
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="startDate"/></td>
                    <td> <bean:write name="abc1" property="swipe_Type"/></td>
                    <td> <bean:write name="abc1" property="reason_Type"/></td>
                    <td> <bean:write name="abc1" property="time"/></td>
                    <td> <bean:write name="abc1" property="remarks"/></td>
                    <td> <bean:write name="abc1" property="date"/></td>
                  </tr>
				 </logic:iterate>
				 
				 
				<%if(i==0) 
				{
				%>
				<tr><td colspan="9"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
				 
				 </logic:notEmpty><br/>
	 <logic:notEmpty name="appList">
	
	
	
    <table class="bordered"><tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="abc" name="appList">
	<tr>
	<td>${abc.approver }</td>
	<td>${abc.appDesig }</td>
	<td>${abc.status }</td>
	<td>${abc.approvedDate }</td>
	<td>${abc.comments }</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
	
						<input style="visibility:hidden;" id="scnt" value="<bean:write property="startAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="reqId" value="<bean:write name="approvalsForm" property="requestNo"/>"/>
					<input style="visibility:hidden;" id="reqType" value="<bean:write name="approvalsForm" property="requestType"/>"/>
					<input style="visibility:hidden;" id="sValue" value="<bean:write property="searchText"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="totalReco" value="<bean:write property="totalRecords"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="filterby" value="<bean:write property="selectedFilter"  name="approvalsForm"/>"/>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	<html:hidden property="selectedFilter"/>
	<html:hidden property="reqRequstType"/>
            </html:form>
            
</body>
</html>

	</body>
</html>
					
