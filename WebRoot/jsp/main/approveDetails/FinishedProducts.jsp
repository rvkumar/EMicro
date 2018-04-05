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
<title>eMicro :: Finished Material </title>

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
	var location="";
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

<body onload="getIsDMFMaterial(),isVendorStatus()" style="text-transform: uppercase;">

   		<div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>

    	<div class="middel-blocks">
     		
		<html:form action="/approvals.do" enctype="multipart/form-data">
       
				
			

			<div id="materialTable" style="visibility: visible;">
			<table class="bordered">
			
			<tr>
					<th colspan="8" style="text-align: center;"><big>Finished Product Form</big></th>
				</tr>
				<tr>
					<th colspan="8"><big>Basic Details Of Material</big></th>
   				</tr>
				<tr>
	 				<td>Request No <font color="red">*</font></td>
					<td>
					<bean:write name="approvalsForm" property="requestNo"/>
					</td>
					<td>Request Date <font color="red">*</font></td>
					<td>
					<bean:write name="approvalsForm" property="requestDate"/>
					</td>
				</tr>
				<tr>
					<td>Location <font color="red">*</font></td>
					<td>
					<bean:write name="approvalsForm" property="locationId"/>

					</td>
					<td>Manufactured At <font color="red">*</font></td>
					<td>
			<bean:write name="approvalsForm" property="manufacturedAt"/>

					</td>				  
		        </tr>

<%--			<tr>--%>
<%--				<td>Material Type <font color="red">*</font></td>--%>
<%--				<td><html:select property="materialTypeId">--%>
<%--					<html:option value="">Select</html:option>--%>
<%--					<html:option value="1">FERT</html:option>--%>
<%--					<html:option value="2">HAWA</html:option>--%>
<%--					</html:select>--%>
<%--				<br /></td>--%>
<%--			</tr>--%>
			<html:hidden property="materialTypeId"/>
	        	<tr>
					<td>Storage&nbsp;Location</td>
					<td colspan="3">
								<bean:write name="approvalsForm" property="storageLocationId"/>

					</td>
					</tr>
					
					<tr>
					<td>Short Name <font color="red">*</font></td>
					<td colspan="3">
								<bean:write name="approvalsForm" property="materialShortName"/>
					</td>
				</tr>

				<tr>
					<td>Long Name <font color="red">*</font></td>
				<td colspan="3" >
							<bean:write name="approvalsForm" property="materialLongName"/>
				</td>
				</tr>
			
				<tr>
					<td>Material Group <font color="red">*</font></td>
					<td>
								<bean:write name="approvalsForm" property="materialGroupId"/>
					
					</td>
					<td>U O M <font color="red">*</font></td>
					<td>
													<bean:write name="approvalsForm" property="unitOfMeasId"/>
					
					</td>
				</tr>
			
			<tr>
					<th colspan="8"><big>Sales Information</big></th>
   				</tr>

				<tr>
					<td>Product Type <font color="red">*</font></td>
					<td>
						<bean:write name="approvalsForm" property="domesticOrExports"/>

					</td>
					<td>Country <font color="red">*</font></td>
					<td>
											<bean:write name="approvalsForm" property="countryId"/>

				</tr>

				<tr>
					<td>Customer Name <font color="red">*</font></td>
					<td colspan="3">
											<bean:write name="approvalsForm" property="customerName"/>
					</td>
					
				
				</tr>
			
				<tr>
					<td>Saleable or Sample <font color="red">*</font></td>
					<td>
									<bean:write name="approvalsForm" property="saleableOrSample"/>
					

					</td>
					<td>Pack Size <font color="red">*</font></td>
					<td>								
						<bean:write name="approvalsForm" property="salesPackId"/>

					</td>
				</tr>

				<tr>
					<td>Pack Type <font color="red">*</font></td>
					<td>									
					<bean:write name="approvalsForm" property="packTypeId"/>

					</td>
					<td>Sales U O M <font color="red">*</font></td>
					<td>					<bean:write name="approvalsForm" property="salesUnitOfMeaseurement"/>

					</td>
				</tr>
			
			<tr>
				<td>Division <font color="red">*</font></td>
				<td>				
					<bean:write name="approvalsForm" property="divisionId"/>

				</td>
				<td>Therapeutic Segment <font color="red">*</font></td>
				<td>
									<bean:write name="approvalsForm" property="therapeuticSegmentID"/>
				
				</td>
			</tr>

			<tr>
				<td>Brand <font color="red">*</font></td>
				<td>
		        <bean:write name="approvalsForm" property="brandID"/>
		        </td>
				<td>Strength <font color="red">*</font></td>
				<td>
						        <bean:write name="approvalsForm" property="srengthId"/>
				
				</td>
			</tr>

			<tr>
				<td>Generic Name <font color="red">*</font></td>
				<td>
				 <bean:write name="approvalsForm" property="genericName"/>
				
				</td>
				<td>Gross Weight</td>
				<td>
				 <bean:write name="approvalsForm" property="grossWeight"/>
				</td>
			</tr>
			
			<tr>
				<td>Net Weight </td>
				<td>
				 <bean:write name="approvalsForm" property="netWeight"/>
				</td>
				<td>Weight UOM</td>
				<td>
								 <bean:write name="approvalsForm" property="weightUOM"/>

				</td>
			</tr>

			<tr>
				<td>Shipper Dimension <font color="red">*</font></td>
				<td >
				 <bean:write name="approvalsForm" property="dimension"/>
				</td>
				<td>Tax Classification</td>
				<td>
								 <bean:write name="approvalsForm" property="taxClassification"/>
				
				</td>
			</tr>
		<tr>
		<td>Material Pricing Group</td>
		<td>
										 <bean:write name="approvalsForm" property="materialPricing"/>
		
		</td>
		<td>HSN Code</td>
		<td>
										 <bean:write name="approvalsForm" property="hsnCode"/>
		
		</td>
		
		</tr>
			

			<tr>
				<th colspan="8"><big>Other Details</big></th>
			<tr>

			<tr>
				<td>Shelf Life <font color="red">*</font></td>
				<td>
										 <bean:write name="approvalsForm" property="shelfLife"/>
										 <bean:write name="approvalsForm" property="shelfType"/>
				
				</td>

				<logic:notEmpty name="standardBathcNotMandatory">
						<td>Std. Batch Size</td>
						<td>
									 <bean:write name="approvalsForm" property="standardBatchSize"/>
						
						</td>
					</tr>
					<tr>
						<td>Batch Code</td>
						<td>
										 <bean:write name="approvalsForm" property="batchCode"/>
						</td>
				</logic:notEmpty>
	
				<logic:notEmpty name="standardBathcMandatory">
						<td>Std. Batch Size <font color="red">*</font></td>
						<td>
									 <bean:write name="approvalsForm" property="standardBatchSize"/>
						</td>
					</tr>
					<tr>
						<td>Batch Code </td>
						<td>
										 <bean:write name="approvalsForm" property="batchCode"/>
						</td>
				</logic:notEmpty>

				<td>Valuation Class </td>
				<td>
							 <bean:write name="approvalsForm" property="valuationClass"/>
				
				</td>
			</tr>
			
			<logic:notEmpty name="manufactureNotMandatory">
			<tr>
				<td>Purchase Group</td>
				<td colspan="1">
				 <bean:write name="approvalsForm" property="puchaseGroupId"/>
				
				</td>
							<td>WM Storage Type <font color="red">*</font></td>
				<td> <bean:write name="approvalsForm" property="storage"/>
				
				</td>
			</tr>
			</logic:notEmpty>
			
			<logic:notEmpty name="manufactureMandatory">
			<tr>
				<td>Purchase Group <font color="red">*</font></td>
				<td colspan="1">
								 <bean:write name="approvalsForm" property="puchaseGroupId"/>
				
				</td>
						<td>WM Storage Type <font color="red">*</font></td>
				<td> <bean:write name="approvalsForm" property="storage"/>
				
				</td>
			</tr>
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
	<html:hidden property="reqMaterialGroup"/>
				</table> 
		</div>
		</div>
	
		
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

</div>
</body>
</html>
