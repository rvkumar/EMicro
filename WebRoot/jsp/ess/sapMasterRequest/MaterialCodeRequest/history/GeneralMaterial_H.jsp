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
<logic:notEmpty name="generalMaterial">
<logic:iterate id="m" name="generalMaterial">
<table class="bordered">
<tr><th colspan="3"><center>General Material-${m.matType }</center></th>
</tr>
<tr>
<th colspan="3">Basic Details Of Material</th>
</tr>
<tr><td>Request No</td><td>${m.requestNumber }</td><td>${m.requestNumber }</td></tr>
<tr><td>Request Date</td><td>${m.requestDate }</td><td>${m.modifiedDate }</td></tr>
<tr>
<td>Employee Name</td><td>${m.requestedBy }</td><td>${m.modifiedBy }</td>
</tr>
<tr><td>Location </td><td>${m.locationId }</td><td></td></tr>
<tr><td>Storage Location</td><td>${m.storageLocationId }</td><td>${m.storageLocationId_C }</td></tr>
<tr><td>Short Name</td><td>${m.materialShortName }</td><td>${m.materialShortName_C }</td></tr>
<tr><td>Long Name</td><td>${m.materialLongName }</td><td>${m.materialLongName_C }</td></tr>
<tr><td>Material Group</td><td>${m.materialGroupId }</td><td>${m.materialGroupId_C }</td></tr>
<tr><td>Purchase Group </td><td>${m.puchaseGroupId }</td><td>${m.puchaseGroupId_C }</td></tr>
<tr><td>U O M</td><td>${m.unitOfMeasId }</td><td>${m.unitOfMeasId_C }</td></tr>
<tr>
<th colspan="3">Other Details</th>
</tr>
<tr><td>Is Asset </td><td>${m.isAsset }</td><td>${m.isAsset_C }</td></tr>
<tr><td>Utilizing Dept </td><td>${m.utilizingDept }</td><td>${m.utilizingDept_C }</td></tr>
<tr><td>Approximate Value</td><td>${m.approximateValue }</td><td>${m.approximateValue_C }</td></tr>
<tr><td>Valuation Class</td><td>${m.valuationClass }</td><td>${m.valuationClass_C }</td></tr>
<tr><td>Justification</td><td>${m.detailedJustification }</td><td>${m.detailedJustification_C }</td></tr>
<tr><td>Specification</td><td>${m.detailedSpecification }</td><td>${m.detailedSpecification_C }</td></tr>

<tr><td colspan="3"><center><html:button property="method" onclick="ConfirmClose()" styleClass="rounded">close</html:button></center></td></tr>
</table>
</logic:iterate>
</logic:notEmpty>
</html:form>
</body>
</html>