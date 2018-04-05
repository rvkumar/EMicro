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
	var reqType=document.forms[0].reqType.value;
	var url="incomeTaxReq.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue;
	document.forms[0].action=url;
	document.forms[0].submit();
}
function getCurrentRecord(){
	var url="incomeTaxReq.do?method=curentRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
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
<html:form action="incomeTaxReq" enctype="multipart/form-data" method="post">
	<div align="center">
		<logic:present name="incomeTaxReqForm" property="message2">
			<font color="red" size="3"><b><bean:write name="incomeTaxReqForm" property="message2" /></b></font>
		</logic:present>
		<logic:present name="incomeTaxReqForm" property="message">
			<font color="Green" size="3"><b><bean:write name="incomeTaxReqForm" property="message" /></b></font>
		</logic:present>
	</div>
<logic:notEmpty name="investment">
<logic:iterate id="abc" name="investment">
<table class="bordered" align="left">
<tr>
<th colspan="4"><center><b><big>Investment Details</big></b></center></th>
</tr>

  <tr><th colspan="4"><big>Requester Details</big></th></tr>
						<tr><td>Employee Number</td><td><bean:write name="abc" property="employeeNo" /></td><td>Employee Name</td><td><bean:write name="abc" property="employeeName" /></td></tr>
						<tr><td>Department</td><td><bean:write name="abc" property="department" /></td><td>Designation</td><td><bean:write name="abc" property="designation" /></td></tr>
						<tr><td>Date of Joining</td><td colspan=""><bean:write name="abc" property="doj" /></td><td>Location</td><td colspan=""><bean:write name="abc" property="location" /></td></tr>
<tr><td>Staff Category</td><td><bean:write name="abc" property="staffCategory" /></td><td>Fiscal Year</td>
<td><bean:write name="abc" property="fiscalYear" />
</td></tr>

</table>
</logic:iterate>
</logic:notEmpty>
<br/>
<div>&nbsp;</div>
<logic:notEmpty name="investmetDetails">
<table class="bordered"><tr>
            <th width="50px">Investment<br/>Code</th>
            <th width="125px">Investment Description</th>
               <th width="50px">Investment<br/>Section</th>
            <th width="125px">Policy / Receipt No</th>
            <th width="125px">Amount</th>
            <th width="125px">Date</th>
            <th width="125px">Status</th>
            <th width="145px">Remarks</th>
   </tr>
<logic:iterate id="abc" name="investmetDetails">
<tr>

        <td>${abc.invCode }</td>
        <td>${abc.invDes }</td>
         <td>${abc.invSection }</td>
        <td>${abc.reciptNum }</td>
		<td style="text-align:right;">${abc.amt }</td>
		<td>${abc.invDate }</td>
       <td>${abc.invStatus }</td>
      <td>${abc.invRemarks }</td>      
      </tr>     
</logic:iterate>

<tr>
<td colspan="8">
<logic:notEmpty name="investment">
<logic:iterate id="abc" name="investment">
<b><big>Total Amount - ${abc.totalAmount }</big></b>&nbsp;

</logic:iterate>
</logic:notEmpty>
</td>
</tr>
<logic:notEmpty name="documentDetails">
<tr>
<th colspan="8">Uploaded Documents</th>
</tr>
<logic:iterate id="abc" name="documentDetails">
<tr>
<td colspan="7"><a href="/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles/${abc.requestNumber}/${abc.requestNumber}_${abc.fileName }" target="_blank">${abc.fileName }</a></td>
<td>${abc.invRemarks }</td>
</tr>
</logic:iterate>
</logic:notEmpty>
<tr>
<td>
Comments</td>
<td colspan="7">
<html:textarea property="comments" style="width:100%;"></html:textarea>		
</td>
</tr>
<tr><td colspan="8" style="border:0px; text-align: center;">
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
</logic:notEmpty>
<div>&nbsp;</div>

     	<logic:notEmpty name="sectionlist">
        	<table class="bordered"><tr><th colspan="6">Summary</th></tr>
        	<tr><th>Section</th><th>Claim in this request</th><th>Total Claim in this year</th><th>Limit</th><th>Allowed</th><th>Approved</th></tr>
        	<logic:iterate id="l" name="sectionlist">
        	
        	<tr >
        	<td>${l.invSection}</td>  <td style="text-align:right;">${l.invCode}</td><td style="text-align:right;">${l.invRemarks}</td><td style="text-align:right;">${l.invLimit}</td><td style="text-align:right;">${l.invStatus}</td>
        	<td>
        	<html:hidden property="ltaID" value="${l.invSection }"/>
	<html:text property="amtApproved" value="${l.amtApproved }" onkeypress="return isNumber(event)" style="text-align:right;"/></td>
        	</tr>
        	
        	</logic:iterate>
        	</table>
        	
        	</logic:notEmpty>
        	
        	<br/>

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
<input style="visibility:hidden;" id="scnt" value="<bean:write property="startAppCount"  name="incomeTaxReqForm"/>"/>
	<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endAppCount"  name="incomeTaxReqForm"/>"/>
	<input style="visibility:hidden;" id="reqId" value="<bean:write name="incomeTaxReqForm" property="requestNo"/>"/>
	<input style="visibility:hidden;" id="reqType" value="<bean:write name="incomeTaxReqForm" property="requestType"/>"/>
	<input style="visibility:hidden;" id="totalReco" value="<bean:write property="totalRecords"  name="incomeTaxReqForm"/>"/>
	<input style="visibility:hidden;" id="filterby" value="<bean:write property="selectedFilter"  name="incomeTaxReqForm"/>"/>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	<html:hidden property="selectedFilter"/>
	<html:hidden property="reqRequstType"/>
	<html:hidden property="requestNo"/>
</html:form>
</body>
</html>
