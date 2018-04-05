<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <%@ page language="java" contentType="application/vnd.ms-excel"%>
 <% response.setHeader("Content-Disposition:","attachment;filename=SAP Report.xls"); %> 
</head>

<body >
<html:form action="/sapReport.do"  enctype="multipart/form-data">
		<logic:notEmpty name="listOfServiceCode">
		
<table class="bordered sortable" border="1">
				<tr>
					<th style="width:50px;">Req No</th>
					<th style="width:50px;">Req.Date</th>
					<th style="width:50px;">Plant</th>
					<th>Requester&nbsp;Name</th>
					<th>Service Description</th>
					<th>Service Category</th>
					<th>Service Group</th>
					<th>Valuation Class</th>
					<th>Status</th>
					<th>SAP Code No</th>
					<th>SAC CODE</th>
					<th>Code Creation Date</th>
					<!-- <th>Date</th> -->
					<!-- <th>Approver Dates</th> -->
					<th>Last Approver</th>
					<th>Pending Approver</th>
				</tr>
	<logic:iterate id="custList" name="listOfServiceCode">			
	<tr>
	    <td><bean:write name="custList" property="r_no" /></td>
		<td><bean:write name="custList" property="requestDate" /></td>
		<td><bean:write name="custList" property="locationID" /></td>
		<td><bean:write name="custList" property="requestedBy" /></td>
		<td><font style="text-transform:uppercase;"><bean:write name="custList" property="serviceDescription" /></font></td>
		<td><bean:write name="custList" property="serviceCatagory" /></td>
		<td><bean:write name="custList" property="serviceGroup" /></td>
		<td><bean:write name="custList" property="valuationClass" /></td>
		<td><bean:write name="custList" property="approveType" /></td>
		<td><bean:write name="custList" property="sapCodeNo" /></td>
		<td><bean:write name="custList" property="sacCode" /></td>
		<td><bean:write name="custList" property="sapCreationDate" /></td>
		<td><bean:write name="custList" property="lastApprover" />&nbsp;</td>
		<td><bean:write name="custList" property="pendingApprover" />&nbsp;</td>
	</tr>
	</logic:iterate>			
</table>				
</logic:notEmpty>	
	<logic:notEmpty name="listOfVendorCode">
	
<table class="bordered sortable">
				<tr>
					<th style="width:50px;">Req No</th>
					<th style="width:50px;">Req.Date</th>
					<th style="width:50px;">Plant</th>
					<th>Requester&nbsp;Name</th>
					<th>Customer Name</th>
					<th>City</th>
					<th>Status</th>
					<th>SAP Code No</th>
					<th>Code Creation Date</th>
					<!-- <th>Date</th> -->
					<!-- <th>Approver Dates</th> -->
					<th>Last Approver</th>
					<th>Pending Approver</th>
				</tr>
	<logic:iterate id="custList" name="listOfVendorCode">			
	<tr>
	    <td><bean:write name="custList" property="requestNo" /></td>
		<td><bean:write name="custList" property="requestDate" /></td>
		<td><bean:write name="custList" property="locationId" /></td>
		<td><bean:write name="custList" property="requestedBy" /></td>
		<td><font style="text-transform:uppercase;"><bean:write name="custList" property="name" /></font></td>
		<td><font style="text-transform:uppercase;"><bean:write name="custList" property="city" /></font></td>
		<td><bean:write name="custList" property="approveType" /></td>
		<td><bean:write name="custList" property="sapCodeNo" /></td>
		<td><bean:write name="custList" property="sapCreationDate" /></td>
		<td><bean:write name="custList" property="lastApprover" />&nbsp;</td>
		<td><bean:write name="custList" property="pendingApprover" />&nbsp;</td>
	</tr>
	</logic:iterate>			
</table>				
</logic:notEmpty>	 
	
<logic:notEmpty name="listOfCustomers">

<table class="bordered sortable" border="1">
				<tr>
					<th style="width:50px;">Req No</th>
					<th style="width:50px;">Req.Date</th>
					<th style="width:50px;">Plant</th>
					<th>Requester&nbsp;Name</th>
					<th>Customer Name</th>
					<th>City</th>
					<th>Status</th>
					<th>SAP Code No</th>
					<th>Code Creation Date</th>
					<!-- <th>Date</th> -->
					<!-- <th>Approver Dates</th> -->
					<th>Last Approver</th>
					<th>Pending Approver</th>
				</tr>
	<logic:iterate id="custList" name="listOfCustomers">			
	<tr>
	    <td><bean:write name="custList" property="requestNo" /></td>
		<td><bean:write name="custList" property="requestDate" /></td>
		<td><bean:write name="custList" property="locationId" /></td>
		<td><bean:write name="custList" property="requestedBy" /></td>
		<td><font style="text-transform:uppercase;"><bean:write name="custList" property="customerName" /></font></td>
		<td><font style="text-transform:uppercase;"><bean:write name="custList" property="city" /></font></td>
		<td><bean:write name="custList" property="approveType" /></td>
		<td><bean:write name="custList" property="sapCodeNo" /></td>
		<td><bean:write name="custList" property="sapCreationDate" /></td>
		<td><bean:write name="custList" property="lastApprover" />&nbsp;</td>
		<td><bean:write name="custList" property="pendingApprover" />&nbsp;</td>
	</tr>
	</logic:iterate>			
</table>				
</logic:notEmpty>	 

<logic:notEmpty name="noCustRecords">

<table class="sortable bordered" border="1">
			<tr>
				<th style="width:50px;">Req No</th>
					<th style="width:50px;">Req.Date</th>
					<th style="width:50px;">Plant</th>
					<th>Requester&nbsp;Name</th>
					<th>Customer Name</th>
					<th>City</th>
					<th>Status</th>
					<th>SAP Code No</th>
					<th>Code Creation Date</th>
					<!-- <th>Date</th> -->
					<!-- <th>Approver Dates</th> -->
					<th>Last Approver</th>
					<th>Pending Approver</th>	</tr>
				</table>
					
					<div align="center">
						<logic:present name="sapReportForm" property="message">
						<font color="red" size="3">
							<bean:write name="sapReportForm" property="message" />
						</font>
						
					</logic:present>
           
					</div>
					</table>
</logic:notEmpty>
	 
<logic:notEmpty name="listOfMaterials">

<table class="bordered sortable" border="1">
				<tr>
					<th style="width:50px;">Req No</th>
					<th style="width:50px;">Req.Date</th>
					<th>Requester&nbsp;Name</th>
					<th style="width:50px;">Plant</th>
					<th>Material Type</th>
					<th>Material Name</th>
					<th>Status</th>
					<th>SAP Code No</th>
					<th>Code Creation Date</th>
					<!-- <th>Date</th> -->
					<!-- <th>Approver Dates</th> -->
					<th>Last Approver</th>
					<th>Pending Approver</th>
					<th>Previous Approver</th>
					<th>Previous Approver Date</th>
				</tr>
				<logic:iterate id="materList" name="listOfMaterials">
					<tr>
						<td><bean:write name="materList" property="requestNumber" /></td>
						<td><bean:write name="materList" property="requestDate" /></td>
						<td><bean:write name="materList" property="requesterName" /></td>
						<td><bean:write name="materList" property="locationId" /></td>
						<td><bean:write name="materList" property="mType" /></td>
						
						<td><font style="text-transform:uppercase;"> <bean:write
									name="materList" property="materialShortName" /></font></td>
						
						<td><bean:write name="materList" property="approveType" /></td>
						<td><bean:write name="materList" property="sapCodeNo" /></td>
						<td><bean:write name="materList" property="sapCreationDate" /></td>
						<%-- <td><bean:write name="materList" property="codeCreationDate" /></td> --%>
					<%-- 	<td><bean:write name="materList" property="approversList" /></td> --%>
						<td><bean:write name="materList" property="lastApprover" /></td>
						<td><bean:write name="materList" property="pendingApprover" /></td>
						<td><bean:write name="materList" property="prevApprover" /></td>
						<td><bean:write name="materList" property="prevApproverDate" /></td>
					</tr>
				</logic:iterate>

				</logic:notEmpty>

<logic:notEmpty name="noRecords">

<table class="sortable bordered" border="1">
			<tr>
				<th style="width:50px;">Req No</th><th style="width:100px;">Request Date</th><th style="width:150px;">Material Type</th><th>Name</th><th >Material Name</th>
		<th style="width:50px;">Plant</th><th>Status</th><th>Last Approver</th><th>Pending Approver</th>
	</tr>
				</table>
					
					<div align="center">
						<logic:present name="sapReportForm" property="message">
						<font color="red" size="3">
							<bean:write name="sapReportForm" property="message" />
						</font>
						
					</logic:present>
           
					</div>
						
					</table>
					 
	

</logic:notEmpty>
</html:form>
</body>
</html>	