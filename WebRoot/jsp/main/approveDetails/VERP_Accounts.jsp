<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Packaging Material</title>
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
	var reqType = "VERP";
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
<th colspan="4" align="center"><big><center>VERP-Packaging</center> </big></th>
</tr>
				<tr>
					<th colspan="4"><big>Basic Details Of Material</big></th>
   				</tr>
		<tr>
				<td>Request No <font color="red">*</font></td>
				<td align="left">
					<html:text property="requestNumber" readonly="true" style="background-color:#d3d3d3;"/>
						
				</td>
				<td>Request Date <font color="red">*</font></td>
				<td align="left">
					<html:text property="requestDate" readonly="true" style="background-color:#d3d3d3;"/>
				</td>
			</tr>
			<tr>
				<td>Location <font color="red">*</font></td>
				<td align="left">
					<html:select name="approvalsForm" property="locationId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="locationIdList" labelProperty="locationLabelList"/>
					</html:select>
				</td>
				<td>Storage&nbsp;Location <font color="red">*</font></td>
				<td>
					<html:select  property="storageLocationId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
				</td>
			</tr>

<%--				<th width="274" class="specalt" scope="row">Material Type<img src="images/star.gif" width="8" height="8" /></th>--%>
<%--				<td align="left">--%>
<%--				--%>
<%--				<html:select name="PackageMaterialMasterForm" property="materialTypeId" styleClass="text_field" style="width:100px;">--%>
<%--				<html:option value="">--Select--</html:option>--%>
<%--				<html:options name="PackageMaterialMasterForm" property="materTypeIDList" --%>
<%--									labelProperty="materialTypeIdValueList"/>--%>
<%--					</html:select>--%>
<%--				<br /></td>--%>
<%--			</tr>--%>
			<html:hidden property="materialTypeId"/>
			<tr>
				<td>Short Name <font color="red">*</font></td>
				<td colspan="3">
					<html:text property="materialShortName" maxlength="40" size="40" title="Maximum of 40 characters" style="width:400px;text-transform:uppercase" readonly="true"></html:text>
				</td>
			</tr>
			<tr>
				<td>Long Name <font color="red">*</font></td>
				<td colspan="3" >
						<html:text property="materialLongName" maxlength="80"   title="Maximum of 80 characters" style="width:400px;text-transform:uppercase"  readonly="true"></html:text>
				</td>
			</tr>
			<tr>
				<td>Mat.Group <font color="red">*</font></td>
				<td>
					<html:select name="approvalsForm" property="materialGroupId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
					</html:select>
				</td>
				<td>HSN Code<font color="red">*</font></td>
					<td>
						<html:text property="hsnCode" maxlength="8" size="8"> </html:text>
					</td>
				</tr>
			<tr>
				<td>U O M <font color="red">*</font></td>
				<td colspan="3">
					<html:select property="unitOfMeasId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues" style="text-transform:uppercase"/>
					</html:select>
				</td>
			
				
			</tr>

			<tr>
				<th colspan="4"><big>Quality Requirement</big></th>
   			</tr>
			
			<tr>
				<td>Duty Element <font color="red">*</font></td>
				<td colspan="3">
					<html:select property="dutyElement" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:option value="0">0-Indigenous Material with or without Cenvat</html:option>
						<html:option value="1">1-Duty Exempted Packing Materials for Finished product</html:option>
					</html:select>
				</td>
				</tr>
			<tr>
				<td>Package Material Group <font color="red">*</font></td>
				<td colspan="3">
					<html:select property="packageMaterialGroup" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="packageGroupID" labelProperty="packageGroupIDValue"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>Type Of Material <font color="red">*</font></td>
				<td>
					<html:select property="typeOfMaterial" onchange="getMaterialStatus()" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:option value="Printed Material">Printed Material</html:option>
						<html:option value="Plain Material">Plain Material</html:option>
					</html:select>
				</td>
			
			<logic:notEmpty name="materialTypeNotMandatory">
					<td>Artwork Code</td>
					<td>
						<html:text property="artworkNo" maxlength="20" size="20" readonly="true" ></html:text>
					</td>
				</tr>
				<tr>
					<td>Is Artwork Revision </td>
					<td>
						<html:select property="isArtworkRevision" disabled="true" >
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>
			</logic:notEmpty>
			
			<logic:notEmpty name="materialTypeMandatory">
					<td>Artwork Code <font color="red">*</font></td>
					<td>
						<html:text property="artworkNo" maxlength="20" size="20" style="text-transform:uppercase" readonly="true"></html:text>
					</td>
				</tr>
				<tr>
					<td>Is Artwork Revision<font color="red">*</font></td>
					<td>
						<html:select property="isArtworkRevision" disabled="true" >
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>
			</logic:notEmpty>

					<td>Existing SAP Item Code <font color="red">*</font></td>
					<td>
						<html:text property="existingSAPItemCode" maxlength="20" size="20" style="text-transform:uppercase" readonly="true"></html:text>
					</td>
				</tr>
				<tr>
					<td>Is DMF Material <font color="red">*</font></td>
					<td>
						<html:select property="isDMFMaterial" onchange="isDMFStatus(this.value)" disabled="true">
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>

			<logic:notEmpty name="dmfNotMandatory">
					<td>DMF Grade</td>
					<td align="left">
						<html:select name="approvalsForm" property="dmfGradeId" disabled="true">
							<html:option value="">--Select--</html:option>
							<html:options name="approvalsForm" property="dmfGradeIDList" labelProperty="dmfGradeIDValueList"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td>COS Grade & No</td>
					<td>
						<html:text property="cosGradeNo" maxlength="30" size="30" style="text-transform:uppercase" readonly="true"></html:text>
					</td>
			</logic:notEmpty>

			<logic:notEmpty name="dmfMandatory">
					<td>DMF Grade <font color="red">*</font></td>
					<td align="left">
						<html:select name="approvalsForm" property="dmfGradeId" disabled="true">
							<html:option value="">--Select--</html:option>
							<html:options name="approvalsForm" property="dmfGradeIDList" labelProperty="dmfGradeIDValueList"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td>COS Grade & No <font color="red">*</font></td>
					<td>
						<html:text property="cosGradeNo" maxlength="30" size="30" style="text-transform:uppercase" readonly="true"></html:text>
					</td>
			</logic:notEmpty>

				<td>Additional Test</td>
				<td>
					<html:text property="additionalTest" maxlength="20" size="30" style="text-transform:uppercase" readonly="true"></html:text>
				</td>
			</tr>

			<tr>
				<th colspan="4"><big>Vendor / Manufacture Information</big></th>
   			</tr>
			<tr>
				<td>Is Material is Supplier/Manufacture/Site Specific <font color="red">*</font></td>
				<td>
					<html:select property="isVendorSpecificMaterial" onchange="isVendorStatus(this.value)" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:option value="1">Yes</html:option>
						<html:option value="0">No</html:option>
					</html:select>
				</td>

			<logic:notEmpty name="vedorNotMandatory">
					<td>Manufacture&nbsp;Name</td>
					<td>
						<html:text property="mfgrName" maxlength="40" size="40" style="text-transform:uppercase" readonly="true"></html:text>
					</td>
				</tr>
				<tr>
					<td>Site Of Manufacture</td>
					<td>
						<html:text property="siteOfManufacture" maxlength="30" size="30" style="text-transform:uppercase" readonly="true"></html:text>
					</td>
			</logic:notEmpty>

			<logic:notEmpty name="vedorMandatory">
					<td>Manufacture Name <font color="red">*</font></td>
					<td>
						<html:text property="mfgrName" maxlength="40" size="40" style="text-transform:uppercase" readonly="true"></html:text>
					</td>
				</tr>
				<tr>
					<td>Site Of Manufacture <font color="red">*</font></td>
					<td>
						<html:text property="siteOfManufacture" maxlength="30" size="30" style="text-transform:uppercase" readonly="true"></html:text>
					</td>
			</logic:notEmpty>

				<td>Country <font color="red">*</font></td>
				<td>
					<html:select property="countryId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options property="counID" labelProperty="countryName" />
					</html:select>
				</td>
			</tr>
			<tr>
				<td>Customer Name <font color="red">*</font></td>
				<td>
					<html:text property="customerName" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text>
				</td>
				<td>To Be Used In Products <font color="red">*</font></td>
				<td>
					<html:text property="toBeUsedInProducts" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text>
				</td>
			</tr>
			
			<tr>
				<th colspan="4"><big>Other Details</big></th>
   			</tr>
   
			<tr>
				<td>Temp.Condition</td>
				<td colspan="1">
					<html:select property="tempCondition" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="tempIDList" labelProperty="temValueList"/>
					</html:select>
				</td>
				
						<td>WM Storage Type <font color="red">*</font></td>
				<td>
					<html:select property="storage" disabled="true">
						<html:option value="">-----Select-----</html:option>
						<html:option value="REG">REG</html:option>
						<html:option value="AMB">AMB</html:option>
						<html:option value="FRZ">FRZ</html:option>
						<html:option value="CLD">CLD</html:option>
						<html:option value="N/A">N/A</html:option>
						
					</html:select>
				</td>
					</tr>
			<tr>
				<td>Storage Condition</td>
				<td colspan="4">
					<html:select property="storageCondition" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="storageIDList" labelProperty="storageLocList"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>Retest Days</td>
				<td>
					<html:text property="retestDays" maxlength="4" size="4" readonly="true"></html:text>
					<html:select property="retestType" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:option value="days">Days</html:option>
						<html:option value="months">Months</html:option>
					</html:select>
				</td>
				<td>Valuation Class <font color="red">*</font></td>
				<td>
					<html:select property="valuationClass" styleClass="text_field" >
						
						<html:options name="approvalsForm" property="valuationClassID" labelProperty="valuationClassName"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>Approximate Value<font color="red">*</font></td>
				<td colspan="3">
					<html:text property="approximateValue" readonly="true"></html:text>
				</td>
				
			</tr>	
<logic:notEmpty name="rejectedFLag">
			<tr>
			<td>Status</td>
			<td colspan="4"><b><big>For Reapproval</big></b></td>
			</tr>
			</logic:notEmpty>
			<tr>
				<th colspan="4"><big>Attachments <font color="red">*</font></big></th>
			</tr>
			

			<logic:notEmpty name="listName">
				

				<logic:iterate name="listName" id="listid">
					<bean:define id="file" name="listid" property="fileList" />
						<%
							String s = file.toString();
							String v[] = s.split(",");
							int l = v.length;
							for (int i = 0; i < l; i++) {
								int x = v[i].lastIndexOf("/");
								String u = v[i].substring(x + 1);
						%>
	
						<tr>
							<td colspan="2" align="center"><a href="/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles/<%=u%>" target="_blank"><%=u%></a></td>
							

						</tr>
							<%
							}
							%>
				</logic:iterate>
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
