<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
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
<html:form action="/materialHistory.do"  enctype="multipart/form-data">
<logic:notEmpty name="servicedetails">
<logic:iterate id="m" name="servicedetails">
<table class="bordered">
<tr><th colspan="3"><center>Service Master Form</center></th>
</tr>
<tr>
<th colspan="3">Basic Details Of Material</th>
</tr>
<tr><td>Request Date</td><td>${m.requestDate }</td><td>${m.modifiedDate }</td></tr>
<tr>
<td>Employee Name</td><td>${m.requestedBy }</td><td>${m.modifiedBy }</td>
</tr>
<tr><td>Location </td><td>${m.plantCode }</td><td></td></tr>
<tr><td>Service Description </td><td>${m.serviceDescription }</td><td>${m.serviceDescription_C }</td></tr>
	<tr><td>Detailed Service description</td><td>${m.detailedServiceDescription }</td><td>${m.detailedServiceDescription_C }</td></tr>
	<tr><td>U O M</td><td>${m.uom }</td><td>${m.uom_C }</td></tr>
	<tr><td>Purchase Group </td><td>${m.purchaseGroup }</td><td>${m.purchaseGroup_C }</td></tr>
	<tr><td> Service Category</td><td>${m.serviceCatagory }</td><td>${m.serviceCatagory_C }</td></tr>
	<tr><td> Service Group</td><td>${m.serviceGroup }</td><td>${m.serviceGroup_C }</td></tr>
	<tr><td> Equipment/Machine Name</td><td>${m.e_m_name }</td><td>${m.e_m_name_C }</td></tr>
	<tr><td> Approximate Value </td><td>${m.app_amount }</td><td>${m.app_amount_C }</td></tr>
	<tr><td> Justification</td><td>${m.justification }</td><td>${m.justification_C }</td></tr>
	<tr><td> Valuation Class</td><td>${m.valuationClass }</td><td>${m.valuationClass_C }</td></tr>
	<tr><td colspan="3"><center><html:button property="method" onclick="ConfirmClose()" styleClass="rounded">close</html:button></center></td></tr>
<tr><td colspan="3"><center><html:button property="method" onclick="ConfirmClose()" styleClass="rounded">close</html:button></center></td></tr>
</table></logic:iterate></logic:notEmpty>
</html:form>
</body>
</html>	