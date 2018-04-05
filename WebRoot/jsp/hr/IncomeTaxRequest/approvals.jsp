<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
   
<script type="text/javascript">
function showSelectedFilter(){
	if(document.forms[0].reqRequstType.value!="" && document.forms[0].selectedFilter.value!=""){

		var filter = document.getElementById("filterId").value;
		var url="incomeTaxReq.do?method=pendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();
	
		}
	}
function getDetails(url){
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

	var url="incomeTaxReq.do?method=approveRequest";
			document.forms[0].action=url;
			document.forms[0].submit();
			}
	}
	
function nextRecord(){
	var url="incomeTaxReq.do?method=nextRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function previousRecord(){
	var url="incomeTaxReq.do?method=previousRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function firstRecord(){
	var url="incomeTaxReq.do?method=pendingRecords";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function lastRecord(){
	var url="incomeTaxReq.do?method=lastRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function searchEmployee(fieldName){

var reqFieldName=fieldName

	var toadd = document.getElementById(reqFieldName).value;
	if(toadd.indexOf(",") >= -1){
		toadd = toadd.substring((toadd.lastIndexOf(",")+1),toadd.length);
	}
	document.getElementById(reqFieldName).focus();
	if(toadd == ""){
		document.getElementById(reqFieldName).focus();
		document.getElementById("sU").style.display ="none";
		return false;
	}
	var xmlhttp;
    if (window.XMLHttpRequest){
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else{
        // code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        if (xmlhttp.readyState==4 && xmlhttp.status==200){
        if(reqFieldName=="employeeno"){
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        	}
       
        	       			
        }
    }
     xmlhttp.open("POST","itHelpdesk.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
    xmlhttp.send();
}

function selectUser(input,reqFieldName){
document.getElementById(reqFieldName).value=input;
	disableSearch(reqFieldName);
}

function disableSearch(reqFieldName){
  if(reqFieldName=="employeeno"){
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		}
	}
	}
	
	function displayemprecord()
	
	{
	
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
	
		if(document.forms[0].chooseKeyword.value!=""){

		var url="incomeTaxReq.do?method=pendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();
		}
		
		else
		{
		alert("please enter employee number");
		return false;
		}
	}
	
</script>
 <style type="text/css">
   th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>
</head>
<body>
<html:form action="/incomeTaxReq.do" enctype="multipart/form-data">
<div align="center">
				<logic:present name="incomeTaxReqForm" property="message">
					<font color="red" size="3"><b><bean:write name="incomeTaxReqForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="incomeTaxReqForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="incomeTaxReqForm" property="message2" /></b></font>
				</logic:present>
			</div>
<table class="bordered" width="90%">
<tr>
	<th colspan="4"><big>My Approvals</big></th>
</tr> 
<tr>
<td><b>Request Type : <font color="red">*</font></b>
</td><td>	<html:select property="reqRequstType" styleClass="Content"  styleId="requestSelectId" onchange="showSelectedFilter()">
	<html:option value="">Select</html:option>
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
		<html:option value="Pending">Pending</html:option>
		<html:option value="Approved">Approved</html:option>
		<html:option value="Rejected">Rejected</html:option>
		<html:option value="All">All</html:option>
		</html:select>
	</td>
</tr>
<tr><td colspan="4" >
<html:text property="chooseKeyword" title="Enter employee Number" styleClass="rounded" onkeyup="searchEmployee('employeeno')" styleId="employeeno"/>&nbsp;&nbsp;&nbsp;
	<div id="sU" style="display:none;">
		<div id="sUTD" style="width:80px;">
		<iframe src="jsp/it/HelpDesk/searchemployee.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="60%"></iframe>
		</div>
	</div><html:button property="method" value="Go" styleClass="rounded" onclick="displayemprecord()" style="width:100px;"/></td></tr>
</table>
<br/>
<table>

<tr>
<%-- <logic:notEmpty name="displayButton">
<td>
<html:button  property="method" value="Approve" styleClass="rounded" onclick="approve()" style="width:100px;" ></html:button> &nbsp;
<html:button  property="method" value="Reject" styleClass="rounded" onclick="reject()" style="width:100px;"></html:button>
</logic:notEmpty> --%>

<!--<a href="#"><img  src="images/Approve.png" onclick="approve()" style="width:100px;height:35px"/></a>
<a href="#"><img  src="images/Reject.png" onclick="reject()" style="width:100px;height:35px"/></a>
</td>-->


<logic:notEmpty name="displayRecordNo">

	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="incomeTaxReqForm"/>-
	
	<bean:write property="endRecord"  name="incomeTaxReqForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
	</td></table>
	</logic:notEmpty>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	
	<logic:notEmpty name="medlist">
<table class="sortable bordered">
<tr >
	<th>Req&nbsp;No</th>
	<th>Employee No</th><th >Requested By</th><th >Dept</th>
	<th >Fiscal Year</th>
	<th >Req Date</th><th >Last Approver</th><th >Pending Approver</th><th >Status</th><th style="width:50px;">View</th>
</tr>
<logic:iterate id="lta" name="medlist">
<tr >


<td><bean:write name="lta" property="requestNo"/></td>
<td><bean:write name="lta" property="employeeNo"/></td>
<td><bean:write name="lta" property="employeeName"/></td>

<td><bean:write name="lta" property="department"/></td>
<td><bean:write name="lta" property="fiscalYear"/></td>
<td><bean:write name="lta" property="submitDate"/></td>
<td><bean:write name="lta" property="lastApprover"/></td>
<td><bean:write name="lta" property="pendingApprover"/></td>
<td><bean:write name="lta" property="approvalStatus"/></td>
<td><a onclick="javascript:getDetails('incomeTaxReq.do?method=getSelectedRequestToApprove&reqId=${lta.requestNo}&status=${lta.approvalStatus}&RequesterNo=${lta.requestNo} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
	
	<logic:notEmpty name="investmentList">

<table class="sortable bordered">`
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th >Requested By</th><th >Dept</th>
	<th style="width:50px;">Fiscal Year</th><th style="width:50px;">Total Amount</th>
	<th style="width:50px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th >Status</th><th style="width:50px;">View</th>
</tr>
<logic:iterate id="investment" name="investmentList">
<tr >
<td><html:checkbox property="selectedRequestNo" name="incomeTaxReqForm" value="${investment.requestNumber}"/></td>

<td><bean:write name="investment" property="requestNumber"/></td>
<td><bean:write name="investment" property="employeeNo"/></td>
<td><bean:write name="investment" property="employeeName"/></td>

<td><bean:write name="investment" property="department"/></td>
<td><bean:write name="investment" property="fiscalYear"/></td>
<td><bean:write name="investment" property="totalAmount"/></td>
<td><bean:write name="investment" property="submitDate"/></td>
<td><bean:write name="investment" property="lastApprover"/></td>
<td><bean:write name="investment" property="pendingApprover"/></td>
<td><bean:write name="investment" property="approvalStatus"/></td>
<td><a onclick="javascript:getDetails('incomeTaxReq.do?method=getSelectedRequestToApprove&reqId=${investment.requestNumber}&status=${investment.approvalStatus}&RequesterNo=${investment.requestNumber} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="tRLPList">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th >Requested By</th><th >Dept</th>
	<th >Fiscal Year</th>
	<th >Req Date</th><th >Last Approver</th><th >Pending Approver</th><th >Status</th><th style="width:50px;">View</th>
</tr>
<logic:iterate id="tRLP" name="tRLPList">
<tr >
<td><html:checkbox property="selectedRequestNo" name="incomeTaxReqForm" value="${tRLP.requestNo}"/></td>

<td><bean:write name="tRLP" property="requestNo"/></td>
<td><bean:write name="tRLP" property="employeeNo"/></td>
<td><bean:write name="tRLP" property="employeeName"/></td>

<td><bean:write name="tRLP" property="department"/></td>
<td><bean:write name="tRLP" property="fiscalYear"/></td>
<td><bean:write name="tRLP" property="submitDate"/></td>
<td><bean:write name="tRLP" property="lastApprover"/></td>
<td><bean:write name="tRLP" property="pendingApprover"/></td>
<td><bean:write name="tRLP" property="approvalStatus"/></td>
<td><a onclick="javascript:getDetails('incomeTaxReq.do?method=getSelectedRequestToApprove&reqId=${tRLP.requestNo}&status=${tRLP.approvalStatus}&RequesterNo=${tRLP.requestNo} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="prevIncomeist">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th >Requested By</th><th >Dept</th>
	<th >Fiscal Year</th>
	<th >Req Date</th><th >Last Approver</th><th >Pending Approver</th><th >Status</th><th style="width:50px;">View</th>
</tr>
<logic:iterate id="tRLP" name="prevIncomeist">
<tr >
<td><html:checkbox property="selectedRequestNo" name="incomeTaxReqForm" value="${tRLP.requestNo}"/></td>

<td><bean:write name="tRLP" property="requestNo"/></td>
<td><bean:write name="tRLP" property="employeeNo"/></td>
<td><bean:write name="tRLP" property="employeeName"/></td>

<td><bean:write name="tRLP" property="department"/></td>
<td><bean:write name="tRLP" property="fiscalYear"/></td>
<td><bean:write name="tRLP" property="submitDate"/></td>
<td><bean:write name="tRLP" property="lastApprover"/></td>
<td><bean:write name="tRLP" property="pendingApprover"/></td>
<td><bean:write name="tRLP" property="approvalStatus"/></td>
<td><a onclick="javascript:getDetails('incomeTaxReq.do?method=getSelectedRequestToApprove&reqId=${tRLP.requestNo}&status=${tRLP.approvalStatus}&RequesterNo=${tRLP.requestNo} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="externalIncomeist">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th >Requested By</th><th >Dept</th>
	<th >Fiscal Year</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th >Status</th><th style="width:50px;">View</th>
</tr>
<logic:iterate id="tRLP" name="externalIncomeist">
<tr >
<td><html:checkbox property="selectedRequestNo" name="incomeTaxReqForm" value="${tRLP.requestNo}"/></td>

<td><bean:write name="tRLP" property="requestNo"/></td>
<td><bean:write name="tRLP" property="employeeNo"/></td>
<td><bean:write name="tRLP" property="employeeName"/></td>

<td><bean:write name="tRLP" property="department"/></td>
<td><bean:write name="tRLP" property="fiscalYear"/></td>
<td><bean:write name="tRLP" property="submitDate"/></td>
<td><bean:write name="tRLP" property="lastApprover"/></td>
<td><bean:write name="tRLP" property="pendingApprover"/></td>
<td><bean:write name="tRLP" property="approvalStatus"/></td>
<td><a onclick="javascript:getDetails('incomeTaxReq.do?method=getSelectedRequestToApprove&reqId=${tRLP.requestNo}&status=${tRLP.approvalStatus}&RequesterNo=${tRLP.requestNo} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="ltaList">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th >Requested By</th><th >Dept</th>
	<th >Fiscal Year</th>
	<th >Req Date</th><th >Last Approver</th><th >Pending Approver</th><th >Status</th><th style="width:50px;">View</th>
</tr>
<logic:iterate id="lta" name="ltaList">
<tr >
<td><html:checkbox property="selectedRequestNo" name="incomeTaxReqForm" value="${lta.requestNo}"/></td>

<td><bean:write name="lta" property="requestNo"/></td>
<td><bean:write name="lta" property="employeeNo"/></td>
<td><bean:write name="lta" property="employeeName"/></td>

<td><bean:write name="lta" property="department"/></td>
<td><bean:write name="lta" property="fiscalYear"/></td>
<td><bean:write name="lta" property="submitDate"/></td>
<td><bean:write name="lta" property="lastApprover"/></td>
<td><bean:write name="lta" property="pendingApprover"/></td>
<td><bean:write name="lta" property="approvalStatus"/></td>
<td><a onclick="javascript:getDetails('incomeTaxReq.do?method=getSelectedRequestToApprove&reqId=${lta.requestNo}&status=${lta.approvalStatus}&RequesterNo=${lta.requestNo} ')" ><img src="images/view.gif" height="28" width="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="No Records">
 <logic:equal value="Apply Investment" property="reqRequstType" name="incomeTaxReqForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th style="width:200px;">Requested By</th><th style="width:100px;">Dept</th>
	<th style="width:100px;">Fiscal Year</th><th style="width:100px;">Total Amount</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th style="width:100px;">Status</th><th style="width:50px;">View</th></tr>
</logic:equal>

 <logic:equal value="Claim TRLP / HRA" property="reqRequstType" name="incomeTaxReqForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th style="width:200px;">Requested By</th><th style="width:100px;">Department</th>
	<th style="width:100px;">Fiscal Year</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th style="width:100px;">Status</th><th style="width:50px;">View</th></tr>
</logic:equal>

 <logic:equal value="Previous Income" property="reqRequstType" name="incomeTaxReqForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th style="width:200px;">Requested By</th><th style="width:100px;">Department</th>
	<th style="width:100px;">Fiscal Year</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th style="width:100px;">Status</th><th style="width:50px;">View</th></tr>
</logic:equal>
 <logic:equal value="External Income/Deduction" property="reqRequstType" name="incomeTaxReqForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th style="width:200px;">Requested By</th><th style="width:100px;">Department</th>
	<th style="width:100px;">Fiscal Year</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th style="width:100px;">Status</th><th style="width:50px;">View</th></tr>
</logic:equal>
 <logic:equal value="LTA" property="reqRequstType" name="incomeTaxReqForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th>Employee No</th><th style="width:200px;">Requested By</th><th style="width:100px;">Department</th>
	<th style="width:100px;">Fiscal Year</th>
	<th style="width:100px;">Req Date</th><th >Last Approver</th><th >Pending Approver</th><th style="width:100px;">Status</th><th style="width:50px;">View</th></tr>
</logic:equal>
 <logic:equal value="Medical" property="reqRequstType" name="incomeTaxReqForm">
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