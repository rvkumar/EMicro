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
<logic:notEmpty name="FERT">
<logic:iterate id="m" name="FERT">
<table class="bordered">
<tr><th colspan="3"><center>${m.matType }</center></th>
</tr>
<tr>
<th colspan="3">Basic Details Of Material</th>
</tr>
<tr><td>Request No</td><td>${m.requestNumber }</td><td>${m.requestNumber }</td></tr>
<tr><td>Request Date</td><td>${m.requestDate }</td><td>${m.modifiedDate }</td></tr>
<tr>
<td>Employee Name</td><td>${m.createdBy }</td><td>${m.modifiedBy }</td>
</tr>
<tr><td>Location </td><td>${m.locationId }</td><td></td></tr>
<tr><td>Manufactured At</td><td>${m.manufacturedAt }</td><td>${m.manufacturedAt_C }</td></tr>
<tr><td>Storage Location</td><td>${m.storageLocationId }</td><td>${m.storageLocationId_C }</td></tr>
<tr><td>Short Name</td><td>${m.materialShortName }</td><td>${m.materialShortName_C }</td></tr>
<tr><td>Long Name</td><td>${m.materialLongName }</td><td>${m.materialLongName_C }</td></tr>
<tr><td>Material Group</td><td>${m.materialGroupId }</td><td>${m.materialGroupId_C }</td></tr>

<tr><td>U O M</td><td>${m.unitOfMeasId }</td><td>${m.unitOfMeasId_C }</td></tr>
<tr>
<th colspan="3">Sales Information</th>
</tr>
<tr><td>Product Type</td><td>${m.domesticOrExports }</td><td>${m.domesticOrExports_C }</td></tr>
<tr><td>Country  </td><td>${m.countryId }</td><td>${m.countryId_C }</td></tr>
<tr><td> Customer Name </td><td>${m.customerName }</td><td>${m.customerName_C }</td></tr>
<tr><td>Saleable or Sample</td><td>${m.saleableOrSample }</td><td>${m.saleableOrSample_C }</td></tr>
<tr><td>Pack Size</td><td>${m.salesPackId }</td><td>${m.salesPackId_C }</td></tr>
<tr><td>Pack Type</td><td>${m.packTypeId }</td><td>${m.packTypeId_C }</td></tr>
<tr><td>Sales U O M</td><td>${m.salesUnitOfMeaseurement }</td><td>${m.salesUnitOfMeaseurement_C }</td></tr>
<tr><td>Division</td><td>${m.divisionId }</td><td>${m.divisionId_C }</td></tr>
<tr><td>Therapeutic Segment</td><td>${m.therapeuticSegmentID }</td><td>${m.therapeuticSegmentID_C }</td></tr>
<tr><td>Brand</td><td>${m.brandID }</td><td>${m.brandID_C }</td></tr>
<tr><td>Strength</td><td>${m.srengthId }</td><td>${m.srengthId_C }</td></tr>
<tr><td>Generic Name</td><td>${m.genericName }</td><td>${m.genericName_C }</td></tr>
<tr><td>Gross Weight</td><td>${m.grossWeight }</td><td>${m.grossWeight_C }</td></tr>
<tr><td>Net Weight</td><td>${m.netWeight }</td><td>${m.netWeight_C }</td></tr>
<tr><td> Weight UOM</td><td>${m.weightUOM }</td><td>${m.weightUOM_C }</td></tr>
<tr><td>Shipper Dimension</td><td>${m.dimension }</td><td>${m.dimension_C }</td></tr>
<tr><td> 	Tax Classification</td><td>${m.taxClassification }</td><td>${m.taxClassification_C }</td></tr>
<tr><td>Material Pricing Groupt </td><td>${m.materialPricing }</td><td>${m.materialPricing_C }</td></tr>

<tr>
<th colspan="3">Other Details</th></tr>

<tr><td>Shelf Life </td><td>${m.shelfLife }&nbsp;${m.shelfLifeType }</td><td>${m.shelfLife_C }&nbsp;${m.shelfLifeType_C }</td></tr>
<tr><td>Std. Batch Size  </td><td>${m.standardBatchSize }</td><td>${m.standardBatchSize_C }</td></tr>
<tr><td> Batch Code </td><td>${m.batchCode }</td><td>${m.batchCode_C }</td></tr>
<tr><td>Valuation Class</td><td>${m.valuationClass }</td><td>${m.valuationClass_C }</td></tr>
<tr><td>Purchase Group </td><td>${m.puchaseGroupId }</td><td>${m.puchaseGroupId_C }</td></tr>
<tr><td colspan="3"><center><html:button property="method" onclick="ConfirmClose()" styleClass="rounded">close</html:button></center></td></tr>
</table>
</logic:iterate>
</logic:notEmpty>
</html:form>
</body>
</html>