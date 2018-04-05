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
		<logic:notEmpty name="customerdetails">
			<logic:iterate id="m" name="customerdetails">
				<table class="bordered">
					
					<tr>
						<th colspan="3" style="text-align: center;"><big>Customer
								Code Request Form</big></th>
					</tr>
					<tr>
						<th colspan="3"><big>Basic Details Of Material</big></th>
					</tr>
						<tr>
							<td>Request Number <font color="red">*</font></td>
							<td><bean:write name="m" property="requestNumber" /></td>
							<td>&nbsp;</td>
						</tr>
						<tr>	
							<td>Request Date <font color="red">*</font></td>
							<td><bean:write name="m" property="requestDate" /></td>
							<td><bean:write name="m" property="requestDate" /></td>
						</tr>
						<tr>	
							<td>Name <font color="red">*</font></td>
							<td><bean:write name="m" property="requestedBy" /></td>
							<td><bean:write name="m" property="modifiedBy" /></td>
						</tr>
						<tr>
							<td >Account Group</td>
							<td ><bean:write name="m" property="accGroupId" /></td>
							<td ><bean:write name="m" property="accGroupId_C" /></td>
						</tr>
						<tr>
							<td>View Type</td>
							<td><bean:write name="m" property="sales" /></td>
							<td><bean:write name="m" property="sales_C" /></td>
						</tr>
						<tr>
							<td>Customer Type <font color="red">*</font></td>
							<td><bean:write name="m" property="customerType" /></td>
							<td><bean:write name="m" property="customerType_C" /></td>
						</tr>
						<tr>	
							<td>Employee Code</td>
							<td><bean:write name="m" property="cutomerCode" /></td>
							<td><bean:write name="m" property="cutomerCode_C" /></td>
						</tr>
						<tr>
							<td>Name <font color="red">*</font></td>
							<td ><bean:write name="m" property="customerName" /></td>
							<td ><bean:write name="m" property="customerName_C" /></td>
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
							<td><bean:write name="m" property="address4" /></td>
							<td><bean:write name="m" property="address4_C" /></td>
						</tr>
						<tr>
							<td>City</td>
							<td><bean:write name="m" property="city" /></td>
							<td><bean:write name="m" property="city_C" /></td>
							</tr>
						<tr>
							<td>Pincode</td>
							<td><bean:write name="m" property="pincode" /></td>
							<td><bean:write name="m" property="pincode_C" /></td>
						</tr>
						<tr>
							<td>Country <font color="red">*</font></td>
							<td><bean:write name="m" property="countryId" /></td>
							<td><bean:write name="m" property="countryId_C" /></td>
							</tr>
						<tr>

							<logic:empty name="diplayStates">
								<td>State <font color="red">*</font></td>
								<td><bean:write name="m" property="state" /></td>
								<td><bean:write name="m" property="state_C" /></td>
							</logic:empty>
							</tr>
						<tr>

							<td>Landline</td>
							<td><bean:write name="m" property="landlineNo" /></td>
							<td><bean:write name="m" property="landlineNo_C" /></td>
							</tr>
						<tr>
							<td>Mobile</td>
							<td><bean:write name="m" property="mobileNo" /></td>
							<td><bean:write name="m" property="mobileNo_C" /></td>
						</tr>
						<tr>
							<td>Fax</td>
							<td><bean:write name="m" property="faxNo" /></td>
							<td><bean:write name="m" property="faxNo_C" /></td>
							</tr>
						<tr>
							<td>e-Mail</td>
							<td><bean:write name="m" property="emailId" /></td>
							<td><bean:write name="m" property="emailId_C" /></td>
						</tr>
						<tr>
							<th colspan="4"><big>Sales</big></th>
						</tr>
						<tr>
							<td>Customer Group <font color="red">*</font></td>
							<td><bean:write name="m" property="customerGroup" /></td>
							<td><bean:write name="m" property="customerGroup_C" /></td>
							</tr>
						<tr>
							<td>Price Group <font color="red">*</font></td>
							<td><bean:write name="m" property="priceGroup" /></td>
							<td><bean:write name="m" property="priceGroup_C" /></td>
						</tr>
						<tr>
							<td>Price List <font color="red">*</font></td>
							<td><bean:write name="m" property="priceList" /></td>
							<td><bean:write name="m" property="priceList_C" /></td>
							</tr>
						<tr>
							<td>Tax Type <font color="red">*</font></td>
							<td><bean:write name="m" property="taxType" /></td>
							<td><bean:write name="m" property="taxType_C" /></td>
						</tr>
						<tr>
							<td>Currency</td>
							<td><bean:write name="m" property="currencyId" /></td>
							<td><bean:write name="m" property="currencyId_C" /></td>
							</tr>
						<tr>
							<td>Payment Term</td>
							<td><bean:write name="m" property="paymentTermID" /></td>
							<td><bean:write name="m" property="paymentTermID_C" /></td>
						</tr>
						<tr>
							<td>Account Clerk</td>
							<td ><bean:write name="m" property="accountClerkId" /></td>
							<td ><bean:write name="m" property="accountClerkId_C" /></td>
									</tr>
						<tr>
						</tr>
						<tr>
							<th colspan="3"><big>Excise / Tax</big></th>
						</tr>
						<tr>
							<td>Is Reg.Excise TDS</td>
							<td><bean:write name="m" property="isRegdExciseVender" /></td>
							<td><bean:write name="m" property="isRegdExciseVender_C" /></td>
							</tr>
						<tr>

							<logic:empty name="setTdsState">
								<td>TDS Code</td>
								<td><bean:write name="m" property="tdsCode" /></td>
								<td><bean:write name="m" property="tdsCode_C" /></td>
							</logic:empty>
							</tr>
						<tr>
							<td>LST No.</td>
							<td><bean:write name="m" property="listNumber" /></td>
							<td><bean:write name="m" property="listNumber_C" /></td>
							</tr>
						<tr>
							<td>Tin No.</td>
							<td><bean:write name="m" property="tinNumber" /></td>
							<td><bean:write name="m" property="tinNumber_C" /></td>
						</tr>
						<tr>
							<td>CST No.</td>
							<td><bean:write name="m" property="cstNumber" /></td>
							<td><bean:write name="m" property="cstNumber_C" /></td>
							</tr>
						<tr>
							<td>PAN No.</td>
							<td><bean:write name="m" property="panNumber" /></td>
							<td><bean:write name="m" property="panNumber_C" /></td>
						</tr>
						<tr>
							<td>Service Tax Reg. No.</td>
							<td><bean:write name="m" property="serviceTaxNo" /></td>
							<td><bean:write name="m" property="serviceTaxNo_C" /></td>
							</tr>
						<tr>
							<td>Is Reg.Excise Customer <font color="red">*</font></td>
							<td><bean:write name="m" property="tdsStatus" /></td>
							<td><bean:write name="m" property="tdsStatus_C" /></td>
						</tr>

						<logic:notEmpty name="setRegdExciseVenderNotMandatory">
							<tr>
								<td>ECC No</td>
								<td><bean:write name="m" property="eccNo" /></td>
								<td><bean:write name="m" property="eccNo_C" /></td>
								</tr>
						<tr>
								<td>Excise Reg. No.</td>
								<td><bean:write name="m" property="exciseRegNo" /></td>
								<td><bean:write name="m" property="exciseRegNo_C" /></td>
							</tr>
						<tr>
								<td>Excise Division</td>
								<td><bean:write name="m" property="exciseDivision" /></td>
								<td><bean:write name="m" property="exciseDivision_C" /></td>
							</tr>
						</logic:notEmpty>

						<logic:notEmpty name="setRegdExciseVender">
							<tr>
								<td>ECC No <font color="red">*</font></td>
								<td><bean:write name="m" property="eccNo" /></td>
								<td><bean:write name="m" property="eccNo_C" /></td>
								</tr>
						<tr>
								<td>Excise Reg. No. <font color="red">*</font></td>
								<td><bean:write name="m" property="exciseRegNo" /></td>
								<td><bean:write name="m" property="exciseRegNo_C" /></td>
							</tr>
							<tr>
								<td>Excise Range <font color="red">*</font></td>
								<td><bean:write name="m" property="exciseRange" /></td>
								<td><bean:write name="m" property="exciseRange_C" /></td>
								</tr>
						<tr>
								<td>Excise Division <font color="red">*</font></td>
								<td><bean:write name="m" property="exciseDivision" /></td>
								<td><bean:write name="m" property="exciseDivision_C" /></td>
							</tr>
						</logic:notEmpty>

						<tr>
							<td>DL No.1</td>
							<td><bean:write name="m" property="dlno1" /></td>
							<td><bean:write name="m" property="dlno1_C" /></td>
							</tr>
						<tr>
							<td>DL No.2</td>
							<td><bean:write name="m" property="dlno2" /></td>
							<td><bean:write name="m" property="dlno2_C" /></td>
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