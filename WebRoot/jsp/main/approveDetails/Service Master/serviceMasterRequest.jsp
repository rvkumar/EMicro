<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
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
<title>eMicro :: Raw Material </title>

	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<script type="text/javascript">

function changeStatus(elem){
  
	var elemValue = elem.value;
	if(elemValue=="Reject")
	{
	if(document.forms[0].comments.value==""){
	  alert("Please Add Some Comments");
	    document.forms[0].comments.focus();
	         return false;
	  }
	
	}
	var reqId = document.forms[0].requestNumber.value;
	var reqType = "ROH";
	var matGroup=document.forms[0].reqMaterialGroup.value;
	var location=document.forms[0].locationId.value;
	var url="approvals.do?method=statusChangeForServiceRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
	document.forms[0].action=url;
	document.forms[0].submit();
}
function goBack()
  {
  window.history.back(-2);
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
<body style="text-transform: uppercase;">
	<html:form action="/approvals.do" enctype="multipart/form-data">
	<div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>
<table class="bordered">
<tr>
					<th colspan="8" style="text-align: center;"><big>Service Master Form</big></th>
				</tr>
				<tr>
					<th colspan="8"><big>Basic Details Of Material</big></th>
   				</tr>
			<tr>
		<tr>
			<td>Request Number <font color="red">*</font></td>
			<td colspan="3">
			<bean:write name="approvalsForm" property="requestNo"/>

			</td>
			
		</tr>

		<tr>
			<td>Location <font color="red">*</font></td>
			<td >	
			<bean:write name="approvalsForm" property="plantCode"/>
			</td>
			<td>Request Date <font color="red">*</font></td>
					<td align="left">
			<bean:write name="approvalsForm" property="requestDate"/>
					</td>
		</tr>
								
		<tr>
			<td>Service Description <font color="red">*</font></td>
			<td>
			<bean:write name="approvalsForm" property="serviceDescription"/>
				
			</td>
			<td>SAC CODE<font color="red">*</font></td>
			<td>
			<bean:write name="approvalsForm" property="sacCode"/>
				
			</td>
			</tr>
			<tr>
			
			<td>Detailed Service description <font color="red">*</font></td>
			<td colspan="3">
			<bean:write name="approvalsForm" property="detailedServiceDescription"/>
			</td>
		</tr>

		<tr>
			<td>U O M <font color="red">*</font></td>
			<td>
					<bean:write name="approvalsForm" property="uom"/>

			</td>
			<td>Purchase Group <font color="red">*</font></td>
			<td>				
				<bean:write name="approvalsForm" property="purchaseGroup"/>

			</td>
		</tr>

		<tr>
			<td>Service Category <font color="red">*</font></td>
			<td>				
			<bean:write name="approvalsForm" property="serviceCatagory"/>

			</td>
			<td>Service Group<font color="red">*</font></td>
			<td>
						<bean:write name="approvalsForm" property="serviceGroup"/>

			</td>
		</tr>

		<tr>
			<td>Equipment/Machine Name</td>
			<td>
						<bean:write name="approvalsForm" property="e_m_name"/>
			</td>
			<td>Approximate Value </td>
			<td>
						<bean:write name="approvalsForm" property="app_amount"/>
			</td>
		</tr>

		<!--<tr>
			<td>Where Used</td>
			<td>
				<html:text name="approvalsForm" property="whereUsed" styleClass="content" maxlength="40" size="45"></html:text>
			</td>
		
			<td>Purpose <font color="red">*</font></td>
			<td>
				<html:text name="approvalsForm" property="purpose"  styleClass="content"></html:text>
			</td>
			
		</tr>
			--><html:hidden property="whereUsed"/>
			<html:hidden property="purpose"/>

		<tr>
			<td>Justification <font color="red">*</font></td>
			<td colspan="3">
						<bean:write name="approvalsForm" property="justification"/>
			</td>
		</tr>
								
		<tr>
			<td>Valuation Class </td>
			<td colspan="3">
			<bean:write name="approvalsForm" property="valuationClass"/>

			</td>
		</tr>

		<logic:notEmpty name="documentDetails">
			<tr>
				<th colspan="4"><big>Uploaded Documents</big></th>
			</tr>
				
			<logic:iterate id="abc" name="documentDetails">
				<tr>				
					
					<td colspan="3">
						<a href="/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles/${abc.sfile }"  target="_blank"> <bean:write name="abc" property="sfile"/></a>
					</td>
				</tr>
			</logic:iterate>
						
			
		</logic:notEmpty>
	
		  <logic:notEmpty name="rejectedFLag">
			<tr>
			<td>Status</td>
			<td colspan="4"><b><big>For Reapproval</big></b></td>
			</tr>	
			</logic:notEmpty>
			
					<tr>
		<td>
		Comments</td>
		<td colspan="3">
<html:textarea property="comments" style="width:100%;"></html:textarea>		
		
		</td>
		</tr>		
		<tr><td colspan="6" style="border:0px; text-align: center;">
   <logic:notEmpty name="approveButton">
	<input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;
	</logic:notEmpty>
	<logic:notEmpty name="rejectButton">
			<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<!--<input type="button" class="rounded" value="Close" onclick="goBack()"  />
			
			--><input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  />
			</td>
			
			</tr>	
			</table>			
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
	<html:hidden property="userRole"/>
	<html:hidden property="requestNumber"/>
	<html:hidden property="locationId"/>
	<html:hidden property="materialTypeId"/>
	<html:hidden property="reqMaterialGroup"/>
	
	<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeeName }</td><td>${abc.approveStatus }</td><td>${abc.date }</td><td>${abc.comments }</td>
	</tr>
	</logic:iterate>
</table>
	</logic:notEmpty>
	
	<br/>
	&nbsp;
	<br/>
	&nbsp;
	</html:form>
</body>
