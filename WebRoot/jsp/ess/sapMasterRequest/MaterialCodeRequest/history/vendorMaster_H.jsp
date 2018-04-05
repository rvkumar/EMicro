<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
th {
	font-family: Arial;
}

td {
	font-family: Arial;
	font-size: 12;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript">


function ConfirmClose()
{
	var win = window.open("","_self"); 
	win.close();
}


</script>
</head>
<body style="text-transform:uppercase;">
	<html:form action="/materialHistory.do" enctype="multipart/form-data">
		<logic:notEmpty name="vendordetails">
			<logic:iterate id="m" name="vendordetails">
				<table class="bordered">
					<tr>
						<th colspan="3" align="center"><big><center>Vendor
									Master Form</center> </big></th>
					</tr>
					<tr>
						<th colspan="3"><big>Basic Details Of Material</big></th>
					</tr>

					<tr>
						<td>Request No. <font color="red">*</font></td>
						<td><bean:write name="m" property="requestNumber" /></td>
						</tr>
					<tr>
						<td>Date <font color="red">*</font></td>
						<td><bean:write name="m" property="requestDate" /></td>
						<td><bean:write name="m" property="requestDate" /></td>
					</tr>
					<tr>
	<td>Employee Name</td><td>${m.requestedBy }</td><td>${m.modifiedBy }</td>
	</tr>

					<tr>
						
						<html:hidden property="locationId" />
						<td>Account Group <font color="red">*</font></td>
						<td ><bean:write name="m" property="accountGroupId" /></td>
						<td><bean:write name="m" property="accountGroupId_C" /></td>
					</tr>

					<tr>
						<td>View Type <font color="red">*</font></td>
						<td><bean:write name="m" property="purchaseView" /></td>
						<td><bean:write name="m" property="purchaseView_C" /></td>
						</tr>
					<tr>
						<td>Vendor Type <font color="red">*</font></td>
						<td><bean:write name="m" property="typeOfVendor" /></td>
						<td><bean:write name="m" property="typeOfVendor_C" /></td>
					</tr>

					<tr>
						<td>Title <font color="red">*</font></td>
						<td><bean:write name="m" property="title" /></td>
						
						<td><bean:write name="m" property="title_C" /></td>
							</tr>
					<tr>
						<td>Name <font color="red">*</font></td>
						<td><bean:write name="m" property="name" /></td>
						<td><bean:write name="m" property="name_C" /></td>
					</tr>

					<tr>
						<td>Address1 <font color="red">*</font></td>
						<td><bean:write name="m" property="address1" /></td>
						<td><bean:write name="m" property="address1_C" /></td>
							</tr>
					<tr>
						<td>Address2</td>
						<td><bean:write name="m" property="address2" /></td>
						<td><bean:write name="m" property="address2_C" /></td>
					</tr>

					<tr>
						<td>Address3</td>
						<td><bean:write name="m" property="address3" /></td>
						<td><bean:write name="m" property="address3_C" /></td>
							</tr>
					<tr>
						<td>Address4</td>
						<td align="left"><bean:write name="m" property="address4" /></td>
						<td><bean:write name="m" property="address4_C" /></td>
					</tr>

					<tr>
						<td>City <font color="red">*</font></td>
						<td><bean:write name="m" property="city" /></td>
						<td><bean:write name="m" property="city_C" /></td>
							</tr>
					<tr>
						<td>Pin Code <font color="red">*</font></td>
						<td><bean:write name="m" property="pinCode" /></td>
						<td><bean:write name="m" property="pinCode_C" /></td>
					</tr>

					<tr>
						<td>Country <font color="red">*</font></td>
						<td><bean:write name="m" property="country" /></td>
						<td><bean:write name="m" property="country_C" /></td>
							</tr>
					<tr>
						<td>State <logic:notEmpty name="States">
								<font color="red">*</font>
							</logic:notEmpty></td>
						<td><bean:write name="m" property="state" /></td>
						<td><bean:write name="m" property="state_C" /></td>
					</tr>

					<tr>
						<td>Landline No.</td>
						<td><bean:write name="m" property="landLineNo" /></td>
						<td><bean:write name="m" property="landLineNo_C" /></td>
							</tr>
					<tr>

							<td>Mobile No.</td>
							<td><bean:write name="m" property="mobileNo" /></td>
							<td><bean:write name="m" property="mobileNo_C" /></td>
					</tr>

					<tr>
						<td>Fax No</td>
						<td><bean:write name="m" property="faxNo" /></td>
						<td><bean:write name="m" property="faxNo_C" /></td>
							</tr>
					<tr>
						<td>e-Mail</td>
						<td><bean:write name="m" property="emailId" /></td>
						<td><bean:write name="m" property="emailId_C" /></td>
					</tr>

					<tr>
						<th colspan="3"><big>Tax Details</big></th>
					</tr>

					<tr>
						<td>Currency <font color="red">*</font></td>
						<td><bean:write name="m" property="currencyId" /></td>
						<td><bean:write name="m" property="currencyId_C" /></td>
							</tr>
					<tr>
						<td>Reconciliation A/c. <font color="red">*</font></td>
							<td><bean:write name="m" property="reConcillationActId" /></td>
							<td><bean:write name="m" property="reConcillationActId_C" /></td>
					</tr>

					<tr>
						<td>Is Eligible For TDS <font color="red">*</font></td>
							<td><bean:write name="m" property="elgTds" /></td>
							<td><bean:write name="m" property="elgTds_C" /></td>
								</tr>
					<tr>
							<td>TDS Code
								<div id="mandatoryEligibleTds" style="visibility: hidden">
									<font color="red">*</font>
								</div>
						</td>
							<td><bean:write name="m" property="tdsCode" /></td>
							<td><bean:write name="m" property="tdsCode_C" /></td>
					</tr>

					<tr>
						<td>LST No.</td>
						<td><bean:write name="m" property="lstNo" /></td>
						<td><bean:write name="m" property="lstNo_C" /></td>
							</tr>
					<tr>
						<td>Tin No
							<div style="visibility: hidden" id="tin3">
								<font color="red">*</font>
							</div>
						</td>
						<td><bean:write name="m" property="tinNo" /></td>
						<td><bean:write name="m" property="tinNo_C" /></td>
					</tr>

					<tr>
						<td>CST No.
							<div style="visibility: hidden" id="cst4">
								<font color="red">*</font>
							</div>
						</td>
						<td><bean:write name="m" property="cstNo" /></td>
						<td><bean:write name="m" property="cstNo_C" /></td>
							</tr>
					<tr>
						<td>Payment Term <font color="red">*</font></td>
						<td><bean:write name="m" property="paymentTermId" /></td>
						<td><bean:write name="m" property="paymentTermId_C" /></td>
					</tr>

					<tr>
						<td>Account Clerk</td>
						<td><bean:write name="m" property="accountClerkId" /></td>
						<td><bean:write name="m" property="accountClerkId_C" /></td>
							</tr>
					<tr>
						<td>Is Approved Vendor</td>
						<td><bean:write name="m" property="isApprovedVendor" /></td>
						<td><bean:write name="m" property="isApprovedVendor_C" /></td>
					</tr>

					<tr>
						<td>PAN No
							<div style="visibility: hidden" id="group1">
								<font color="red">*</font>
							</div>
						</td>
						<td><bean:write name="m" property="panNo" /></td>
						<td><bean:write name="m" property="panNo_C" /></td>
							</tr>
					<tr>
						<td>Service Tax Reg. No.
							<div style="visibility: hidden" id="Service2">
								<font color="red">*</font>
							</div>
						</td>
						<td><bean:write name="m" property="serviceTaxRegNo" /></td>
						<td><bean:write name="m" property="serviceTaxRegNo_C" /></td>
					</tr>
					<tr>
						<td>Is Reg. Excise Vendor</td>
						<td><bean:write name="m" property="regExciseVendor" /></td>
						<td><bean:write name="m" property="regExciseVendor_C" /></td>
							</tr>
					<tr>
						<td>ECC No.
							<div id="mandatoryRegisteredExciseVendor1">
								<font color="red">*</font>
							</div>
						</td>
						<td><bean:write name="m" property="serviceTaxRegNo" /></td>
						<td><bean:write name="m" property="serviceTaxRegNo_C" /></td>

					</tr>
					<tr>
						<td>Excise Reg. No.
							<div id="mandatoryRegisteredExciseVendor2"
								style="visibility: hidden">
								<font color="red">*</font>
							</div>
						</td>
						<td><bean:write name="m" property="eccNo" /></td>
						<td><bean:write name="m" property="eccNo_C" /></td>
							</tr>
					<tr>
						<td>Excise Range
							<div id="mandatoryRegisteredExciseVendor3"
								style="visibility: hidden">
								<font color="red">*</font>
							</div>
						</td>
						<td><bean:write name="m" property="exciseRange" /></td>
						<td><bean:write name="m" property="exciseRange_C" /></td>
					</tr>

					<tr>
						<td>Excise Division
							<div id="mandatoryRegisteredExciseVendor4"
								style="visibility: hidden">
								<font color="red">*</font>
							</div>
						</td>
						<td><bean:write name="m" property="exciseDivision" /></td>
						<td><bean:write name="m" property="exciseDivision_C" /></td>
							</tr>
					<tr>
						<td>Commissionerate
							<div id="mandatoryRegisteredExciseVendor5"
								style="visibility: hidden">
								<font color="red">*</font>
							</div>
						</td>
						<td><bean:write name="m" property="commissionerate" /></td>
						<td><bean:write name="m" property="commissionerate_C" /></td>
					</tr>
					<tr><td colspan="3"><center><html:button property="method" onclick="ConfirmClose()" styleClass="rounded">close</html:button></center></td></tr>
				
				</table>
			</logic:iterate>
		</logic:notEmpty>
		<div>&nbsp;</div>
		<div>&nbsp;</div>
	</html:form>
</body>
</html>