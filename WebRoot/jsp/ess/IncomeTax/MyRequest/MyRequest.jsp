<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
   
<script type="text/javascript">
function showSelectedFilter(){
	if(document.forms[0].reqRequstType.value!="" && document.forms[0].selectedFilter.value!=""){

		var filter = document.getElementById("filterId").value;
		var url="myIncomeReq.do?method=mypendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();
		}
	}
function getDetails(url){
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	
function nextRecord(){
var url="myIncomeReq.do?method=nextRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function previousRecord(){
var url="myIncomeReq.do?method=previousRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function lastRecord(){
var url="myIncomeReq.do?method=lastRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function firstRecord(){
var url="myIncomeReq.do?method=mypendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();
}
	
function approve(){
	if(document.forms[0].reqRequstType.value=="")
	{
	alert("Please Select Request Type");
	 document.forms[0].reqRequstType.focus();
	  return false;
	}
	if(document.forms[0].selectedFilter.value=="")
	{
	alert("Please Select Filter By");
	 document.forms[0].selectedFilter.focus();
	  return false;
	}
	if(document.forms[0].selectedFilter.value!="Pending")
	{
	alert("Please Choose Request Type As Pending");
	 document.forms[0].reqRequstType.focus();
	  return false;
	}

	var rows=document.getElementsByName("selectedRequestNo");
	var checkvalues='';
	var uncheckvalues='';
	for(var i=0;i<rows.length;i++)
	{
	if (rows[i].checked)
	{
	checkvalues+=rows[i].value+',';
	}else{
	uncheckvalues+=rows[i].value+',';
	}
	}
	if(checkvalues=='')
	{
	alert('please select atleast one record');
	}
	else
	{

	var url="myIncomeReq.do?method=approveRequest";
			document.forms[0].action=url;
			document.forms[0].submit();
			}
	}
</script>

</head>
<body>
<html:form action="/myIncomeReq.do" enctype="multipart/form-data">
<div align="center">
				<logic:present name="myIncomeTaxForm" property="message">
					<font color="red" size="3"><b><bean:write name="myIncomeTaxForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="myIncomeTaxForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="myIncomeTaxForm" property="message2" /></b></font>
				</logic:present>
			</div>
<table class="bordered" width="90%">
<tr>
	<th colspan="4"><big>My Requests</big></th>
</tr> 
<tr>
<td><b>Request Type : <font color="red">*</font></b>
</td><td>	<html:select property="reqRequstType" styleClass="Content"  styleId="requestSelectId" onchange="showSelectedFilter()">
	<html:option value="">--Select--</html:option>
	    <html:option value="Apply Investment">Apply Investment</html:option>
		<html:option value="Claim TRLP / HRA">HRA Exemption</html:option>
		<html:option value="Previous Income">Previous Income/Deduction</html:option>
		<html:option value="External Income/Deduction"> External Income/Deduction</html:option>
		<html:option value="LTA">Annual LTA</html:option>
		<html:option value="Medical">Annual Medical</html:option>
	</html:select>
	</td>
<td><b>Filter by</b> <font color="red">*</font></td>
	<td>
	<html:select property="selectedFilter" styleClass="content" styleId="filterId" onchange="showSelectedFilter()">
	
		<html:option value="">--Select--</html:option>
		<html:option value="In Process">In Process</html:option>
		<html:option value="Approved">Approved</html:option>
		<html:option value="Rejected">Rejected</html:option>
		<html:option value="All">All</html:option>
		</html:select>
	</td>
</tr>
</table>
<br/>
<table>

<tr>
<logic:notEmpty name="displayButton">
<td>
<html:button  property="method" value="Approve" styleClass="rounded" onclick="approve()" style="width:100px;" ></html:button> &nbsp;
<html:button  property="method" value="Reject" styleClass="rounded" onclick="reject()" style="width:100px;"></html:button>
</logic:notEmpty>

<!--<a href="#"><img  src="images/Approve.png" onclick="approve()" style="width:100px;height:35px"/></a>
<a href="#"><img  src="images/Reject.png" onclick="reject()" style="width:100px;height:35px"/></a>
--></td>


<logic:notEmpty name="displayRecordNo">

	  	<td>
	  	<center>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="myIncomeTaxForm"/>-
	
	<bean:write property="endRecord"  name="myIncomeTaxForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
	</td>

	</table>
	</logic:notEmpty>
	</center>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	
	
	<logic:notEmpty name="medlist">
<table class="sortable bordered">
<tr >
	<th>Req&nbsp;No</th>
	<th >Fiscal Year</th>
	<th >Req Date</th><th >Last Approver</th><th >Pending Approver</th><th >Status</th><th style="width:50px;">View</th>
</tr>
<logic:iterate id="lta" name="medlist">
<tr >


<td><bean:write name="lta" property="requestNo"/></td>

<td><bean:write name="lta" property="fiscalYear"/></td>
<td><bean:write name="lta" property="submitDate"/></td>
<td><bean:write name="lta" property="lastApprover"/></td>
<td><bean:write name="lta" property="pendingApprover"/></td>
<td><bean:write name="lta" property="approvalStatus"/></td>
<td><a onclick="javascript:getDetails('myIncomeReq.do?method=viewrequest&reqId=${lta.requestNo}&status=${lta.approvalStatus}&RequesterNo=${lta.requestNo} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
	
	<logic:notEmpty name="investmentList">

<table class="sortable bordered">
<tr >
	<th>Req&nbsp;No</th>
	<th style="width:50px;">Fiscal Year</th><th style="width:50px;">Total Amount</th>
	<th style="width:50px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th >Status</th><th style="width:50px;">View</th>
</tr>
<logic:iterate id="investment" name="investmentList">
<tr >


<td><bean:write name="investment" property="requestNumber"/></td>

<td><bean:write name="investment" property="fiscalYear"/></td>
<td><bean:write name="investment" property="totalAmount"/></td>
<td><bean:write name="investment" property="submitDate"/></td>
<td><bean:write name="investment" property="lastApprover"/></td>
<td><bean:write name="investment" property="pendingApprover"/></td>
<td><bean:write name="investment" property="approvalStatus"/></td>
<td><a onclick="javascript:getDetails('myIncomeReq.do?method=viewrequest&reqId=${investment.requestNumber}&status=${investment.approvalStatus}&RequesterNo=${investment.requestNumber} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="tRLPList">
<table class="sortable bordered">
<tr >
<th>Req&nbsp;No</th>
	<th >Fiscal Year</th>
	<th >Req Date</th><th >Last Approver</th><th >Pending Approver</th><th >Status</th><th style="width:50px;">View</th>
</tr>
<logic:iterate id="tRLP" name="tRLPList">
<tr >

<td><bean:write name="tRLP" property="requestNo"/></td>

<td><bean:write name="tRLP" property="fiscalYear"/></td>
<td><bean:write name="tRLP" property="submitDate"/></td>
<td><bean:write name="tRLP" property="lastApprover"/></td>
<td><bean:write name="tRLP" property="pendingApprover"/></td>
<td><bean:write name="tRLP" property="approvalStatus"/></td>
<td><a onclick="javascript:getDetails('myIncomeReq.do?method=viewrequest&reqId=${tRLP.requestNo}&status=${tRLP.approvalStatus}&RequesterNo=${tRLP.requestNo} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="prevIncomeist">
<table class="sortable bordered">
<tr >
<th>Req&nbsp;No</th>
	<th >Fiscal Year</th>
	<th >Req Date</th><th >Last Approver</th><th >Pending Approver</th><th >Status</th><th style="width:50px;">View</th>
</tr>
<logic:iterate id="tRLP" name="prevIncomeist">
<tr >


<td><bean:write name="tRLP" property="requestNo"/></td>

<td><bean:write name="tRLP" property="fiscalYear"/></td>
<td><bean:write name="tRLP" property="submitDate"/></td>
<td><bean:write name="tRLP" property="lastApprover"/></td>
<td><bean:write name="tRLP" property="pendingApprover"/></td>
<td><bean:write name="tRLP" property="approvalStatus"/></td>
<td><a onclick="javascript:getDetails('myIncomeReq.do?method=viewrequest&reqId=${tRLP.requestNo}&status=${tRLP.approvalStatus}&RequesterNo=${tRLP.requestNo} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="externalIncomeist">
<table class="sortable bordered">
<tr >
	<th>Req&nbsp;No</th>
	<th >Fiscal Year</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th >Status</th><th style="width:50px;">View</th>
</tr>
<logic:iterate id="tRLP" name="externalIncomeist">
<tr >


<td><bean:write name="tRLP" property="requestNo"/></td>


<td><bean:write name="tRLP" property="fiscalYear"/></td>
<td><bean:write name="tRLP" property="submitDate"/></td>
<td><bean:write name="tRLP" property="lastApprover"/></td>
<td><bean:write name="tRLP" property="pendingApprover"/></td>
<td><bean:write name="tRLP" property="approvalStatus"/></td>
<td><a onclick="javascript:getDetails('myIncomeReq.do?method=viewrequest&reqId=${tRLP.requestNo}&status=${tRLP.approvalStatus}&RequesterNo=${tRLP.requestNo} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="ltaList">
<table class="sortable bordered">
<tr >
	<th>Req&nbsp;No</th>
	<th >Fiscal Year</th>
	<th >Req Date</th><th >Last Approver</th><th >Pending Approver</th><th >Status</th><th style="width:50px;">View</th>
</tr>
<logic:iterate id="lta" name="ltaList">
<tr >


<td><bean:write name="lta" property="requestNo"/></td>

<td><bean:write name="lta" property="fiscalYear"/></td>
<td><bean:write name="lta" property="submitDate"/></td>
<td><bean:write name="lta" property="lastApprover"/></td>
<td><bean:write name="lta" property="pendingApprover"/></td>
<td><bean:write name="lta" property="approvalStatus"/></td>
<td><a onclick="javascript:getDetails('myIncomeReq.do?method=viewrequest&reqId=${lta.requestNo}&status=${lta.approvalStatus}&RequesterNo=${lta.requestNo} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="No Records">
 <logic:equal value="Apply Investment" property="reqRequstType" name="myIncomeTaxForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th style="width:200px;">Requested By</th><th style="width:100px;">Dept</th>
	<th style="width:100px;">Fiscal Year</th><th style="width:100px;">Total Amount</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th style="width:100px;">Status</th><th style="width:50px;">View</th></tr>
</logic:equal>

 <logic:equal value="Claim TRLP / HRA" property="reqRequstType" name="myIncomeTaxForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th style="width:200px;">Requested By</th><th style="width:100px;">Department</th>
	<th style="width:100px;">Fiscal Year</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th style="width:100px;">Status</th><th style="width:50px;">View</th></tr>
</logic:equal>

 <logic:equal value="Previous Income" property="reqRequstType" name="myIncomeTaxForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th style="width:200px;">Requested By</th><th style="width:100px;">Department</th>
	<th style="width:100px;">Fiscal Year</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th style="width:100px;">Status</th><th style="width:50px;">View</th></tr>
</logic:equal>
 <logic:equal value="External Income/Deduction" property="reqRequstType" name="myIncomeTaxForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th style="width:200px;">Requested By</th><th style="width:100px;">Department</th>
	<th style="width:100px;">Fiscal Year</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th style="width:100px;">Status</th><th style="width:50px;">View</th></tr>
</logic:equal>
 <logic:equal value="LTA" property="reqRequstType" name="myIncomeTaxForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th style="width:200px;">Requested By</th><th style="width:100px;">Department</th>
	<th style="width:100px;">Fiscal Year</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th style="width:100px;">Status</th><th style="width:50px;">View</th></tr>
</logic:equal>
<logic:equal value="Medical" property="reqRequstType" name="myIncomeTaxForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th style="width:200px;">Requested By</th><th style="width:100px;">Department</th>
	<th style="width:100px;">Fiscal Year</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th style="width:100px;">Status</th><th style="width:50px;">View</th></tr>
</logic:equal>
<tr>
<td colspan="10">
<div align="center">
<font color="red" size="3">Searched details could not be found.</font>
</div>
</td>
</tr>
</table>
</logic:notEmpty>
</html:form>
</html>				