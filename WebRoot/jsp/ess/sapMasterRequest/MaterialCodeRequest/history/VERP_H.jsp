<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
function ConfirmClose()
{
	var win = window.open("","_self"); 
	win.close();
}
</script>
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
</head>
<body style="text-transform:uppercase;">
<html:form action="/materialHistory.do"  enctype="multipart/form-data">
<logic:notEmpty name="VERP">
<logic:iterate id="m" name="VERP">
<table class="bordered">
<tr><th colspan="3"><center>VERP-Packaging Material</center></th>
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
<th colspan="3">Quality Requirement</th>
</tr>
<tr><td>Duty Element </td><td>${m.dutyElement }</td><td>${m.dutyElement_C }</td></tr>
<tr><td>Package Material Group </td><td>${m.packageMaterialGroup }</td><td>${m.packageMaterialGroup_C }</td></tr>
<tr><td>Type Of Material </td><td>${m.typeOfMaterial }</td><td>${m.typeOfMaterial_C }</td></tr>
<tr><td>Artwork Code </td><td>${m.artworkNo }</td><td>${m.artworkNo_C }</td></tr>
<tr><td>Is Artwork Revision</td><td>${m.isArtworkRevision }</td><td>${m.isArtworkRevision_C }</td></tr>
<tr><td>Existing SAP Item Code</td><td>${m.existingSAPItemCode }</td><td>${m.existingSAPItemCode_C }</td></tr>
<tr><td>Is DMF Material </td><td>${m.isDMFMaterial }</td><td>${m.isDMFMaterial_C }</td></tr>
<tr><td>DMF Grade</td><td>${m.dmfGradeId }</td><td>${m.dmfGradeId_C }</td></tr>

<tr><td>COS Grade No </td><td>${m.cosGradeNo }</td><td>${m.cosGradeNo_C }</td></tr>
<tr><td>Additional Test </td><td>${m.additionalTest }</td><td>${m.additionalTest_C }</td></tr>
<tr>
<th colspan="3">Vendor / Manufacture Information</th>
</tr>
<tr><td>Is Material is Supplier/Manufacture/Site Specific</td><td>${m.isVendorSpecificMaterial }</td><td>${m.isVendorSpecificMaterial_C }</td></tr>
<tr><td> Manufacture Name </td><td>${m.mfgrName }</td><td>${m.mfgrName_C }</td></tr>
<tr><td>Site Of Manufacture  </td><td>${m.siteOfManufacture }</td><td>${m.siteOfManufacture_C }</td></tr>

<tr><td>Country  </td><td>${m.countryId }</td><td>${m.countryId_C }</td></tr>
<tr><td> Customer Name </td><td>${m.customerName }</td><td>${m.customerName_C }</td></tr>
<tr><td>To Be Used In Product (S)   </td><td>${m.toBeUsedInProducts }</td><td>${m.toBeUsedInProducts_C }</td></tr>
<tr>
<th colspan="3">Other Details</th></tr>
<tr><td>Temp.Condition</td><td>${m.tempCondition }</td><td>${m.tempCondition_C }</td></tr>
<tr><td>Storage Condition  </td><td>${m.storageCondition }</td><td>${m.storageCondition_C }</td></tr>
<tr><td>Retest Days</td><td>${m.retestDays }&nbsp;${m.retestType }</td><td>${m.retestDays_C }&nbsp;${m.retestType_C }</td></tr>
<tr><td>Valuation Class</td><td>${m.valuationClass }</td><td>${m.valuationClass_C }</td></tr>
<tr><td>Approximate Value</td><td>${m.approximateValue }</td><td>${m.approximateValue_C }</td></tr>
<tr><td colspan="3"><center><html:button property="method" onclick="ConfirmClose()" styleClass="rounded">close</html:button></center></td></tr>
</table>
</logic:iterate>
</logic:notEmpty>
</html:form>
</body>
</html>