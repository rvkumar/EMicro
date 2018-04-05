<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Raw Material </title>
<style>
 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}
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
	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
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
	var reqId = document.forms[0].requestNumber.value;
	var reqType = "ROH";
	var matGroup=document.forms[0].reqMaterialGroup.value;
	var location=document.forms[0].locationId.value;
	var url="approvals.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
	document.forms[0].action=url;
	document.forms[0].submit();
}
function goBack()
  {
  window.history.back(-2);
  }
  
 function getCurrentRecord(){

var reqId = document.getElementById("reqId").value;
	var reqType = document.getElementById("reqType").value;
	var totalRecords=document.getElementById("totalReco").value;
	var scnt=document.getElementById("scnt").value;
	var ecnt=document.getElementById("ecnt").value;
		var filterby=document.getElementById("filterby").value;
	
	var url="approvals.do?method=curentRecord&reqId="+reqId+"&reqType="+reqType+"&totalRecord="+totalRecords+"&scnt="+scnt+"&ecnt="+ecnt+"&filterby="+filterby;
	
	document.forms[0].action=url;
	document.forms[0].submit();


} 	  
</script>

</head>

<body style="text-transform: uppercase;">
   
  <div class="middel-blocks">
     		<html:form action="/approvals.do" enctype="multipart/form-data">
			
            
         <div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>
	
	
	
			<table class="bordered">
		<tr>
			<th colspan="8"><center><big>Semifinished Form</big></center></th>
		</tr>
		
			<tr>
	 			<th colspan="8"><big>Basic Details Of Material</big></th>
			</tr>

			<tr>
				<td>Request No. <font color="red">*</font></td>
				<td>										
				<bean:write name="approvalsForm" property="requestNo"/>
<html:hidden property="requestNumber"/>
				</td>
				<td>Request Date <font color="red">*</font></td>
				<td colspan="8">
				<bean:write name="approvalsForm" property="requestDate"/>
				</td>
			</tr>

			<tr>
				<td>Location <font color="red">*</font></td>
				<td>				<bean:write name="approvalsForm" property="locationId"/>
				<html:hidden property="locationId"/>

				</td>

<%--				<th width="274" class="specalt" scope="row">Material Type <img src="images/star.gif" width="8" height="8" /></th>--%>
<%--				<td align="left">--%>
<%--				<html:select  name="approvalsForm"  property="materialTypeId" styleClass="text_field" style="width:100px; ">--%>
<%--					<html:option value="">--Select--</html:option>--%>
<%--				<html:options name="approvalsForm" property="materTypeIDList" --%>
<%--									labelProperty="materialTypeIdValueList"/>--%>
<%--					</html:select>-
<%--				<br /></td>
				 <html:hidden property="materialTypeId"/>--%>
				 
				<td>Storage Location <font color="red">*</font></td>
				<td colspan="8">							
				<bean:write name="approvalsForm" property="storageLocationId"/>
				
				</td>
			</tr>

			<tr>
				<td>Short Name <font color="red">*</font></td>
				<td colspan="4">
				<bean:write name="approvalsForm" property="materialShortName"/>
				</td>
			</tr>
			
			<tr>
				<td>Long Name <font color="red">*</font></td>
				<td colspan="4" >
				<bean:write name="approvalsForm" property="materialLongName"/>
				</td>
			</tr>
			
			<tr>
				<td>Material Group <font color="red">*</font></td>
				<td colspan="1">
										<bean:write name="approvalsForm" property="materialGroupId"/>
										
										<html:hidden property="reqMaterialGroup"/>
										</td>

<td>
				&nbsp;&nbsp;U O M <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="unitOfMeasId"/>

				</td>
					</tr>
					<tr><td>
				&nbsp;&nbsp;Pack Size<font color="red">*</font></td>
				<td colspan="4">				
				<bean:write name="approvalsForm" property="packSize"/>

				
		</td>
			</tr>

			<tr>
				<th colspan="8"><big>Quality Requirement</big></th>
   			</tr>

			<tr>
				<td>Country <font color="red">*</font></td>
				<td>			
					<bean:write name="approvalsForm" property="countryId"/>

				</td>
				<td>Customer Name <font color="red">*</font></td>
				<td colspan="8">
					<bean:write name="approvalsForm" property="customerName"/>
				</td>
			</tr>

			<tr>
				
				<html:hidden property="prodInspMemo" ></html:hidden>
				
				<td>Shelf Life <font color="red">*</font></td>
				<td >
									<bean:write name="approvalsForm" property="shelfLife"/>
									<bean:write name="approvalsForm" property="shelfType"/>

				</td>
				<td>Retest Days <font color="red">*</font></td>
				<td colspan="8">
			
					<bean:write name="approvalsForm" property="retestDays"/>
                   <bean:write name="approvalsForm" property="retestType"/>
				</td>
			</tr>

			<tr>
				<td>Std. Batch Size <font color="red">*</font></td>
				<td>
                   <bean:write name="approvalsForm" property="standardBatchSize"/>
               </td>
				<td>Batch Code <font color="red">*</font></td>
				<td>
                   <bean:write name="approvalsForm" property="batchCode"/>
				</td>
			</tr>

			<tr>
				<td>Target Weight <font color="red">*</font></td>
				<td>
                   <bean:write name="approvalsForm" property="targetWeight"/>
				</td>
			
			<!--<tr>
			<th width="274" class="specalt" scope="row">Gross Weight<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="grossWeight" styleClass="text_field" ></html:text>
			</td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Net Weight<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="netWeight" styleClass="text_field" ></html:text>
			</td>
			</tr>
			<tr-->

				<td>Weight UOM <font color="red">*</font></td>
				<td>
			  <bean:write name="approvalsForm" property="weightUOM"/>
				
			  </td>
			</tr>

			

			<tr>
				<th colspan="8"><big>Other Details</big></th>
   			</tr>

			<tr>
				<td>Valuation Class <font color="red">*</font></td>
				<td>
							  <bean:write name="approvalsForm" property="valuationClass"/>
				
				</td>
			</tr>
<logic:notEmpty name="rejectedFLag">
			<tr>
			<td>Status</td>
			<td colspan="4"><b><big>For Reapproval</big></b></td>
			</tr>
			</logic:notEmpty>
			
<tr>
		<td>
		Comments</td>
		<td colspan="3">
<html:textarea property="comments" style="width:100%;"></html:textarea>		
		
		</td>
		</tr>		
		<tr><td colspan="6" style="border:0px; text-align: center;">
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
			
		
		</div>
	
		
		<input style="visibility:hidden;" id="scnt" value="<bean:write property="startAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="reqId" value="<bean:write name="approvalsForm" property="requestNo"/>"/>
					<input style="visibility:hidden;" id="reqType" value="<bean:write name="approvalsForm" property="requestType"/>"/>
					<input style="visibility:hidden;" id="sValue" value="<bean:write property="searchText"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="totalReco" value="<bean:write property="totalRecords"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="filterby" value="<bean:write property="selectedFilter"  name="approvalsForm"/>"/>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	<html:hidden property="selectedFilter"/>
	<html:hidden property="reqRequstType"/>
	<html:hidden property="userRole"/>
<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeeName }</td><td>${abc.approveStatus }</td><td>${abc.date }</td><td>${abc.comments }</td>
	</tr>
	</logic:iterate>
</table>
	</logic:notEmpty>
	
	<br/>
	&nbsp;
	<br/>
	&nbsp;	
	
</html:form>

</div>
</body>
</html>
