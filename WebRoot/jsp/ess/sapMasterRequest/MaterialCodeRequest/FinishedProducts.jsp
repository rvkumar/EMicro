<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Semi Finished Creation</title>

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

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript">

function changeStatus(elem){
  
	var elemValue = elem.value;
	if(elem=="Reject")
	{
	if(document.forms[0].comments.value!=""){
	  alert("Please Add Some Comments");
	  }
	
	}
	var reqId = document.forms[0].requestNumber.value;
	var reqType = "ROH";
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

 
	
	
	var url="materialCode.do?method=curentRecord";
	
	document.forms[0].action=url;
	document.forms[0].submit();


}  
</script>
</head>

<body onload="getIsDMFMaterial(),isVendorStatus()" style="text-transform:uppercase;">
     		
		<html:form action="/materialCode.do" enctype="multipart/form-data">

   	    		   <logic:iterate id="approvalsForm" name="findetails">
		
			

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
				<td>Gross Weight </td>
				<td>
				 <bean:write name="approvalsForm" property="grossWeight"/>
				</td>
			</tr>
			
			<tr>
				<td>Net Weight </td>
				<td>
				 <bean:write name="approvalsForm" property="netWeight"/>
				</td>
				<td>Weight UOM </td>
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
		<td colspan="3">
										 <bean:write name="approvalsForm" property="materialPricing"/>
		
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
	<th colspan="6">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<img src="images/star.gif" width="8" height="8" /></td>
				<td>
				<bean:write name="approvalsForm" property="sapCodeExists"/>
				</td>
		
			<td >SAP Code No<img src="images/star.gif" width="8" height="8" /></td>
				<td colspan="4"><bean:write name="approvalsForm" property="sapCodeNo"/>	</td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<bean:write name="approvalsForm" property="sapCreationDate"/>
				
			</td>
			
				<td >Code Created By<img src="images/star.gif" width="8" height="8" /></td><td colspan="4">
				<bean:write name="approvalsForm" property="sapCreatedBy"/>
			
				</td>
			</tr>
						
			
					<tr>
						<td colspan="6">
							<html:button property="method" value="Close" onclick="getCurrentRecord()" styleClass="rounded" style="width: 100px" ></html:button>
						</td>
					</tr>
				</table> 
		</div>
		</div>
		</logic:iterate>

<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeeName }</td><td>${abc.approveStatus }</td><td>${abc.date }</td><td>${abc.comments }</td></tr>
	</tr>
	</logic:iterate>
</table>
	</logic:notEmpty>
	
	<br/>
	&nbsp;
	<br/>
	&nbsp;		
		
			<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	
	<html:hidden property="locationId" />
	<html:hidden property="approveStatus"/>
	<html:hidden property="fromDate"/>
	<html:hidden property="toDate"/>
	<html:hidden property="materialCodeLists"/>
		
</html:form>

</div>
</body>
</html>