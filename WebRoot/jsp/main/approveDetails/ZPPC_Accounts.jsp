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
if(document.forms[0].hsnCode.value=="")
	    {
	      alert("Please Enter HSN code");
	      document.forms[0].hsnCode.focus();
	      return false;
	    }
	     var hsnCode = document.forms[0].hsnCode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(hsnCode)) {
             alert("HSNcode its should be Integer ");
                document.forms[0].hsnCode.focus();
            return false;
        }
  
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
	var reqType = "ZPPC";
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
<table class="bordered">
<tr>
<th colspan="4" align="center"><big><center>ZPPC-Promotional Material</center> </big></th>
</tr>
				<tr>
					<th colspan="4"><big>Basic Details Of Material</big></th>
   				</tr>
   				<tr>
	 			<td>Request No <font color="red">*</font></td>
				<td><html:text property="requestNumber" readonly="true" style="background-color:#d3d3d3;"/>
					<html:hidden property="typeDetails"/>
				</td>
				<td>Request Date <font color="red">*</font></td>
				<td><html:text property="requestDate" readonly="true" style="background-color:#d3d3d3;"/></td>
			</tr>
			<tr>
				<td>Location <font color="red">*</font></td>
				<td><html:select name="approvalsForm" property="locationId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="locationIdList" labelProperty="locationLabelList"/>
					</html:select>
				</td>

<%--				<td>Material Type <font color="red">*</font></td>--%>
<%--				<td><html:select  property="materialTypeId" disabled="true">--%>
<%--					<html:option value="">Select</html:option>--%>
<%--					<html:options name="promotionalForm" property="materTypeIDList" --%>
<%--									labelProperty="materialTypeIdValueList"/>--%>
<%--					</html:select>--%>
<%--				</td>--%>
			
				<td>Storage&nbsp;Location <font color="red">*</font></td>
				<td><html:select  property="storageLocationId" styleClass="text_field" style="width:200px;" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
				</td>
			</tr>
			
			<tr>
				<td>Short Name <font color="red">*</font></td>
				<td colspan="3"><html:text property="materialShortName"  readonly="true" maxlength="40" size="60" title="Maximum of 40 characters" style="text-transform:uppercase;"></html:text></td>
			</tr>
			
			<tr>
				<td>Long Name <font color="red">*</font></td>
				<td colspan="3" ><html:text property="materialLongName" readonly="true" maxlength="80" size="60" title="Maximum of 80 characters" style="text-transform:uppercase"></html:text>
			</tr>
			
			<tr>
				<td>Material Group <font color="red">*</font></td>
				<td><html:select name="approvalsForm" property="materialGroupId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
					</html:select>
				</td>
				<td>Purchase&nbsp;Group <font color="red">*</font></td>
				<td><html:select property="puchaseGroupId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="puchaseGroupIdList" labelProperty="puchaseGroupIdValues"/>
					</html:select>
				</td>
			</tr>
	<tr>
				<td>Brand <font color="red">*</font></td>
				<td colspan="3">
			
					<html:select  property="brandID" name="approvalsForm" disabled="true">	
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="brandIDList" labelProperty="brandNameList"/>
					</html:select>
					
					
				</td>
			
			</tr>
			<tr>
				<td>U O M <font color="red">*</font></td>
				<td ><html:select property="unitOfMeasId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues"/>
					</html:select>
				</td>
				<td>Division <font color="red">*</font></td>
				<td><html:select property="divisionId" disabled="true">	
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="divisonID" labelProperty="divisonName"/>
					</html:select>
				</td>
			</tr>

			<tr>
	  			<th colspan="4"><big>Other Details</big></th>
   			</tr>
		   <tr>
		   <td>
		   Is Asset <font color="red">*</font>
		   </td>
		   <td>
		  <html:select property="isAsset" disabled="true">
						<html:option value="">--Select--</html:option>
					<html:option value="1">Yes</html:option>
					<html:option value="0">No</html:option>
					</html:select>
					</td>
				<td>Purpose <font color="red">*</font></td>
				<td><html:select property="purposeID" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:option value="1">Gift & Compliments</html:option>
				<html:option value="2">Propaganda & Promotional (KUDLU DEPOT)</html:option>
				<html:option value="3">Product Launch Exp</html:option>
				<html:option value="4">Sales Promotional (SAS)</html:option>
				<html:option value="5">Visual Ads,Literature</html:option>
				<html:option value="6">Conference,National & Regional </html:option>
				<html:option value="7">Incentive to Field Staff</html:option>
				<html:option value="8">Incentive to Stockist/Chemist</html:option>
				<html:option value="9">Travelling Lodging & Boarding Exp</html:option>
					</html:select>
					</td>
					<tr>
				<td>
				Is&nbsp;SAS&nbsp;Form&nbsp;Available <font color="red">*</font>
				</td>
				<td>
				<html:select property="isSASFormAvailable" styleClass="text_field" disabled="true" >
					<html:option value="">--Select--</html:option>
					<html:option value="1">Yes</html:option>
					<html:option value="0">No</html:option>
					</html:select>
				</td>
				
				<td>HSN Code<font color="red">*</font></td>
					<td>
						<html:text property="hsnCode" maxlength="8" size="8"> </html:text>
					</td>
			</tr>

			<tr>
				<td>Approximate Value <font color="red">*</font></td>
				<td><html:text property="approximateValue" style="text-transform:uppercase;" readonly="true"></html:text></td>
				
				
				<td>Valuation&nbsp;Class <font color="red">*</font></td>
				<td><html:select property="valuationClass">
					<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="valuationClassID" labelProperty="valuationClassName"/>
					</html:select>
				</td>
			</tr>

				
			<tr>
				<td>Justification <font color="red">*</font></td>
				<td><html:textarea property="detailedJustification" cols="35" rows="5" readonly="true" styleClass="content" style="text-transform:uppercase;"></html:textarea></td>
				<td>Specification <font color="red">*</font></td>
				<td><html:textarea property="detailedSpecification" cols="35" rows="5" readonly="true"  styleClass="content" style="text-transform:uppercase;"></html:textarea></td>
			</tr>
			<logic:notEmpty name="rejectedFLag">
			<tr>
			<td>Status</td>
			<td colspan="4"><b><big>For Reapproval</big></b></td>
			</tr>
			</logic:notEmpty>
			
				<tr>
				
		<td>Comments</td>
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
			