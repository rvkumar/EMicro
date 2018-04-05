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
	var matGroup=document.forms[0].materialGroupId.value;
	var location=document.forms[0].locationId.value;
	var url="approvals.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	document.forms[0].action=url;
	document.forms[0].submit();
}
function goBack()
  {
  window.history.back()
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
	
	
	

	
		<table class="bordered" width="90%">
			<tr>
			<th colspan="8"><center><big>Semifinished Form</big></center></th>
		</tr>
			<tr>
	 			<th colspan="4"><big>Basic Details Of Material</big></th>
			</tr>

			<tr>
				<td>Request No. <font color="red">*</font></td>
				<td><html:text property="requestNo" readonly="true" />
				
		
					<html:hidden property="typeDetails"/>
				</td>
				<td>Request Date <font color="red">*</font></td>
				<td><html:text property="requestDate" styleId="requestDate" onfocus="popupCalender('requestDate')" readonly="true" /></td>
			</tr>

			<tr>
				<td>Location <font color="red">*</font></td>
				<td><html:select name="approvalsForm" property="locationId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="locationIdList" labelProperty="locationLabelList"/>
					</html:select>
				</td>

<%--				<th width="274" class="specalt" scope="row">Material Type <img src="images/star.gif" width="8" height="8" /></th>--%>
<%--				<td align="left">--%>
<%--				<html:select  name="approvalsForm"  property="materialTypeId" styleClass="text_field" style="width:100px; ">--%>
<%--					<html:option value="">--Select--</html:option>--%>
<%--				<html:options name="approvalsForm" property="materTypeIDList" --%>
<%--									labelProperty="materialTypeIdValueList"/>--%>
<%--					</html:select>--%>
<%--				<br /></td>--%>
				 <html:hidden property="materialTypeId"/>
				<td>Storage Location <font color="red">*</font></td>
				<td><html:select name="approvalsForm" property="storageLocationId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Short Name <font color="red">*</font></td>
				<td colspan="3"><html:text property="materialShortName" maxlength="40" size="45" title="Maximum of 40 characters" style="width:400px;text-transform:uppercase" readonly="true"></html:text></td>
			</tr>
			
			<tr>
				<td>Long Name <font color="red">*</font></td>
				<td colspan="3"><html:text property="materialLongName" maxlength="80" size="110" title="Maximum of 80 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
			</tr>
			
			<tr>
				<td>Material Group <font color="red">*</font></td>
				<td colspan="3"><html:select name="approvalsForm" property="materialGroupId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
					</html:select>
				&nbsp;&nbsp;U O M <font color="red">*</font>
				<html:select name="approvalsForm" property="unitOfMeasId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues"/>
					</html:select>
				&nbsp;&nbsp;Pack Size<font color="red">*</font>
				<html:select property="packSize" disabled="true">	
							<html:option value="">--Select--</html:option>
							<html:options name="approvalsForm" property="packSizeID" labelProperty="packSizeName"/>
						</html:select>
				
		
			</tr>

			<tr>
				<th colspan="4"><big>Quality Requirement</big></th>
   			</tr>

			<tr>
				<td>Country <font color="red">*</font></td>
				<td><html:select property="countryId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options property="counID" labelProperty="countryName" />
					</html:select>
				</td>
				<td>Customer Name <font color="red">*</font></td>
				<td><html:text property="customerName" maxlength="40" size="40" title="Maximum of 40 characters" readonly="true" style="text-transform:uppercase"></html:text></td>
			</tr>

			<tr>
				
				<html:hidden property="prodInspMemo" ></html:hidden>
				
				<td>Shelf Life <font color="red">*</font></td>
				<td ><html:text property="shelfLife" style="width:40px;" readonly="true"></html:text>
					<html:select property="shelfType" disabled="true">
						<html:option value="months">Months</html:option>
						<html:option value="days">Days</html:option>
						
					</html:select>
				</td>
				<td>Retest Days <font color="red">*</font></td>
				<td colspan="3"><html:text property="retestDays" style="text-transform:uppercase;width:40px;" readonly="true" ></html:text>
				<html:select property="retestType" disabled="true">
						<html:option value="days">Days</html:option>
						<html:option value="months">Months</html:option>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Std. Batch Size <font color="red">*</font></td>
				<td><html:text property="standardBatchSize" style="text-transform:uppercase" readonly="true"></html:text></td>
				<td>Batch Code <font color="red">*</font></td>
				<td><html:text property="batchCode" maxlength="10" style="text-transform:uppercase" readonly="true"></html:text></td>
			</tr>

			<tr>
				<td>Target Weight <font color="red">*</font></td>
				<td><html:text property="targetWeight" style="text-transform:uppercase" readonly="true"></html:text></td>
			
			<!--<tr>
			<th width="274" class="specalt" scope="row">Gross Weight<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="grossWeight" styleClass="text_field" ></html:text>
			</td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Net Weight<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="netWeight" styleClass="text_field" ></html:text>
			</td>
			</tr>
			<tr-->

				<td>Weight UOM <font color="red">*</font></td>
				<td><html:select property="weightUOM" disabled="true">	
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="weightUOMID" labelProperty="weightUOMName"/>
					</html:select></td>
			</tr>

			<tr>
				
			</tr>

			<tr>
				<th colspan="4"><big>Other Details</big></th>
   			</tr>

			<tr>
				<td>Valuation Class <font color="red">*</font></td>
				<td><html:select property="valuationClass">
						<html:options name="approvalsForm" property="valuationClassID" labelProperty="valuationClassName"/>
					</html:select>
				</td>
			</tr>

			
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
			<input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  /></td>
			
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
</html>
