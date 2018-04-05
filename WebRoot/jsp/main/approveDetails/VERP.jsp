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
<title>eMicro :: PACKAGING MATERIAL </title>

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
	var reqType = "VERP";
	var matGroup=document.forms[0].reqMaterialGroup.value;
	var location=document.forms[0].locationId.value;
	var url="approvals.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
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
<th colspan="8" align="center"><big><center>VERP-Packaging Material</center> </big></th>
</tr>
				<tr>
					<th colspan="8"><big>Basic Details Of Material</big></th>
   				</tr>
				<tr>
				<td>Request No <font color="red">*</font></td>
				
					<td colspan="2"align="left">
					<html:hidden property="requestNumber"/>
										<bean:write name="approvalsForm" property="requestNo"/>
					
					</td>
				</td>
				<td>Request Date <font color="red">*</font></td>
				<td colspan="6" align="left">
						<bean:write name="approvalsForm" property="requestDate"/>
					</td>
			</tr>
			<tr>
				<td>Location <font color="red">*</font></td>
				<td colspan="2" align="left">
				<html:hidden property="locationId"/>
					<bean:write name="approvalsForm" property="locationId"/>
					</td>
				 <td>Storage&nbsp;Location <font color="red">*</font></td>
				<td>
										<bean:write name="approvalsForm" property="storageLocationId"/>

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
				<td colspan="4">
										<bean:write name="approvalsForm" property="materialShortName"/>
				</td>
			</tr>
			<tr>
				<td>Long Name <font color="red">*</font></td>
				<td colspan="4" >
										<bean:write name="approvalsForm" property="materialLongName"/>
				</td>
			</tr>
			<tr>
				<td>Mat.Group <font color="red">*</font></td>
				<td>
				<html:hidden property="reqMaterialGroup"/>
										<bean:write name="approvalsForm" property="materialGroupId"/>

				</td>
				<td>HSN CODE<font color="red">*</font></td>
				<td>
				
										<bean:write name="approvalsForm" property="hsnCode"/>

				</td>
				</tr>
			<tr>
				<td>U O M <font color="red">*</font></td>
				<td colspan="2">
										<bean:write name="approvalsForm" property="unitOfMeasId"/>

				</td>
			
				<td>Purchasing Group <font color="red">*</font></td>
				<td >
								<bean:write name="approvalsForm" property="puchaseGroupId"/>

				</td>
			</tr>

			<tr>
				<th colspan="8"><big>Quality Requirement</big></th>
   			</tr>
			
			<tr>
				<td>Duty Element <font color="red">*</font></td>
				<td colspan="4">
							
								<bean:write name="approvalsForm" property="dutyElement"/>

				</td>
				</tr>
			<tr>
				<td>Package Material Group <font color="red">*</font></td>
				<td colspan="4">
											<bean:write name="approvalsForm" property="packageMaterialGroup"/>

				</td>
			</tr>
			<tr>
				<td>Type Of Material <font color="red">*</font></td>
				<td colspan="2">
												<bean:write name="approvalsForm" property="typeOfMaterial"/>

				</td>
			
			<logic:notEmpty name="materialTypeNotMandatory">
					<td>Artwork Code</td>
					<td>
												<bean:write name="approvalsForm" property="artworkNo"/>
					</td>
				</tr>
				<tr>
					<td>Is Artwork Revision </td>
					<td colspan="2">
											<bean:write name="approvalsForm" property="isArtworkRevision"/>

					</td>
			</logic:notEmpty>
			
			<logic:notEmpty name="materialTypeMandatory">
					<td>Artwork Code <font color="red">*</font></td>
					<td>
							<bean:write name="approvalsForm" property="artworkNo"/>
					</td>
				</tr>
				<tr>
					<td>Is Artwork Revision<font color="red">*</font></td>
					<td colspan="2">
						<bean:write name="approvalsForm" property="isArtworkRevision"/>
					</td>
			</logic:notEmpty>

					<td>Existing SAP Item Code <font color="red">*</font></td>
					<td>
											<bean:write name="approvalsForm" property="existingSAPItemCode"/>
					</td>
				</tr>
				<tr>
					<td>Is DMF Material <font color="red">*</font></td>
					<td colspan="2">
								<bean:write name="approvalsForm" property="isDMFMaterial"/>

					</td>

			<logic:notEmpty name="dmfNotMandatory">
					<td>DMF Grade</td>
					<td align="left">
										<bean:write name="approvalsForm" property="dmfGradeId"/>

					</td>
				</tr>
				<tr>
					<td>COS Grade & No</td>
					<td colspan="2">
										<bean:write name="approvalsForm" property="cosGradeNo"/>
					</td>
			</logic:notEmpty>

			<logic:notEmpty name="dmfMandatory">
					<td>DMF Grade <font color="red">*</font></td>
					<td align="left">
					<bean:write name="approvalsForm" property="dmfGradeId"/>

					</td>
				</tr>
				<tr>
					<td>COS Grade & No <font color="red">*</font></td>
					<td colspan="2">
										<bean:write name="approvalsForm" property="cosGradeNo"/>
					</td>
			</logic:notEmpty>

				<td>Additional Test</td>
				<td>
										<bean:write name="approvalsForm" property="additionalTest"/>
				</td>
			</tr>

			<tr>
				<th colspan="8"><big>Vendor / Manufacture Information</big></th>
   			</tr>
			<tr>
				<td>Is Material is Supplier/Manufacture/Site Specific <font color="red">*</font></td>
				<td colspan="2">
			      <bean:write name="approvalsForm" property="isVendorSpecificMaterial"/>

				</td>

			<logic:notEmpty name="vedorNotMandatory">
					<td>Manufacture&nbsp;Name</td>
					<td>
			      <bean:write name="approvalsForm" property="mfgrName"/>
					</td>
				</tr>
				<tr>
					<td>Site Of Manufacture</td>
					<td colspan="2">
			      <bean:write name="approvalsForm" property="siteOfManufacture"/>
					</td>
			</logic:notEmpty>

			<logic:notEmpty name="vedorMandatory">
					<td>Manufacture Name <font color="red">*</font></td>
					<td>
			      <bean:write name="approvalsForm" property="mfgrName"/>
					</td>
				</tr>
				<tr>
					<td>Site Of Manufacture <font color="red">*</font></td>
					<td colspan="2">
			      <bean:write name="approvalsForm" property="siteOfManufacture"/>
					</td>
			</logic:notEmpty>

				<td>Country <font color="red">*</font></td>
				<td>
		        <bean:write name="approvalsForm" property="countryName"/>

				</td>
			</tr>
			<tr>
				<td>Customer Name <font color="red">*</font></td>
				<td colspan="2">
								      <bean:write name="approvalsForm" property="customerName"/>
				</td>
				<td>To Be Used In Products <font color="red">*</font></td>
				<td>
								   <bean:write name="approvalsForm" property="toBeUsedInProducts"/>
				</td>
			</tr>
			
			<tr>
				<th colspan="8"><big>Other Details</big></th>
   			</tr>
   
			<tr>
				<td>Temp.Condition</td>
				<td colspan="2">
						<bean:write name="approvalsForm" property="tempCondition"/>

				</td>
				<td>WM Storage Type <font color="red">*</font></td>
				<td> <bean:write name="approvalsForm" property="storage"/>
				
				</td></tr>
					</tr>
			<tr>
				<td>Storage Condition</td>
				<td colspan="4">
									<bean:write name="approvalsForm" property="storageCondition"/>
				</td>
			</tr>
			<tr>
				<td>Retest Days</td>
				
				<td colspan="2">
				<bean:write name="approvalsForm" property="retestDays"/>
		    	<bean:write name="approvalsForm" property="retestType"/>

				</td>
				<td>Valuation Class <font color="red">*</font></td>
				<td>
		    	<bean:write name="approvalsForm" property="valuationClass"/>

				</td>
			</tr>
			<tr>
				<td>Approximate Value<font color="red">*</font></td>
				<td colspan="4">
		    	<bean:write name="approvalsForm" property="approximateValue"/>
				</td>
				
			</tr>	
			<logic:notEmpty name="rejectedFLag">
			<tr>
			<td>Status</td>
			<td colspan="4"><b><big>For Reapproval</big></b></td>
			</tr>
			</logic:notEmpty>
			<th colspan="8"><big>Attachments <font color="red">*</font></big></th>
			<logic:notEmpty name="listName">
				<tr>
					<th colspan="8"><big>Uploaded Documents</big></th>
				</tr>

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
							<td colspan="8" align="center"><a href="/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles/<%=u%>" target="_blank"><%=u%></a></td>
							
								
							</td>

						</tr>
							<%
							}
							%>
				</logic:iterate>
					
				</logic:notEmpty>		

			
	   <tr>
		<td>
		Comments</td>
		<td colspan="8">
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
