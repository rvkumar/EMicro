<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
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
	var reqId = document.forms[0].requestNo.value;
	var url="incomeTaxReq.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue;
	document.forms[0].action=url;
	document.forms[0].submit();
}
</script>
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
</head>
<body>
<html:form action="myIncomeReq" enctype="multipart/form-data" method="post">
	<div align="center">
		<logic:present name="myIncomeTaxForm" property="message2">
			<font color="red" size="3"><b><bean:write name="myIncomeTaxForm" property="message2" /></b></font>
		</logic:present>
		<logic:present name="myIncomeTaxForm" property="message">
			<font color="Green" size="3"><b><bean:write name="myIncomeTaxForm" property="message" /></b></font>
		</logic:present>
	</div>
<logic:notEmpty name="LTAIncomeDet">
<logic:iterate id="abc" name="LTAIncomeDet">
<table class="bordered" align="left">
<tr>
<th colspan="4"><center><b><big>LTA Details</big></b></center></th>
</tr>

</table>
</logic:iterate>
</logic:notEmpty>
<br/>
<div>&nbsp;</div>
<logic:notEmpty name="listOfLta">
<table class="bordered sortable">
<tr><th>Travel Start Date</th><th>Travel End Date</th><th>Leave Type</th><th>Start Date</th><th>Duration</th><th>End Date</th><th>Duration</th><th>No of Days<th>LTA Amount<br/> Applied For</th><th>LTA Amount Approved</th></tr>
<logic:iterate id="abc" name="listOfLta">
<tr>
<td>${abc.travelStartDate }</td>
	<td>${abc.travelEndDate }</td>
	<td>${abc.leaveType }</td>
	<td>${abc.startDate }</td>
	<td>${abc.startDurationType }</td>
	<td>${abc.endDate }</td>
	<td>${abc.endDurationType }</td>
	<td>${abc.noOfDays }</td>
	
	<td style="text-align:right;">${abc.ltaAmtApplFor }</td>
	<td style="text-align:right;">
	<html:hidden property="ltaID" value="${abc.id }"/>
	${abc.ltaAmtAprvdFor }</td>
</tr>
</logic:iterate>
<logic:notEmpty name="documentDetails">
<tr>
<th colspan="10">Uploaded Documents</th>
</tr>
<logic:iterate id="abc" name="documentDetails">
<tr>
<td colspan="9"><a href="/EMicro Files/ESS/Income Tax/LTA/UploadFiles/${abc.requestNumber}/${abc.requestNumber}_${abc.fileName }" target="_blank">${abc.fileName }</a></td>
<td>${abc.invRemarks }</td>
</tr>
</logic:iterate>
</logic:notEmpty>
<tr><td colspan="10" style="border:0px; text-align: center;">
 
			<input type="button" class="rounded" value="Close" onclick="history.back(-1)"  />
			</td>
			</tr>

</table>
</logic:notEmpty>
<div>&nbsp;</div>

<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6"><center>Approvers Details</center></th></tr>
	<tr><th>Priority</th><th>Approver Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.approverName }</td><td>${abc.apprStatus }</td><td>${abc.apprDate }</td><td>${abc.comments }</td>
	</tr>
	</logic:iterate>
</table>
	</logic:notEmpty>
<input style="visibility:hidden;" id="scnt" value="<bean:write property="startAppCount"  name="myIncomeTaxForm"/>"/>
	<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endAppCount"  name="myIncomeTaxForm"/>"/>
	<input style="visibility:hidden;" id="reqId" value="<bean:write name="myIncomeTaxForm" property="requestNo"/>"/>
	<input style="visibility:hidden;" id="reqType" value="<bean:write name="myIncomeTaxForm" property="requestType"/>"/>
	<input style="visibility:hidden;" id="totalReco" value="<bean:write property="totalRecords"  name="myIncomeTaxForm"/>"/>
	<input style="visibility:hidden;" id="filterby" value="<bean:write property="selectedFilter"  name="myIncomeTaxForm"/>"/>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	<html:hidden property="selectedFilter"/>
	<html:hidden property="reqRequstType"/>
	<html:hidden property="requestNo"/>
</html:form>
</body>
</html>
