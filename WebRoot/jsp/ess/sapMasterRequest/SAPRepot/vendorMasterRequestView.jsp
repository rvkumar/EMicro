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

	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<script type="text/javascript">

function changeStatus(elem){
  
	var elemValue = elem.value;
	if(elem=="Reject")
	{
	if(document.forms[0].comments.value!=""){
	  alert("Please Add Some Comments");
	  }
	
	}
	var reqId = document.forms[0].requestNumber.value;
	var reqType = "";
	var matGroup="";
	var location="";
	var url="approvals.do?method=statusChangeForVendorMaster&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
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
<body >
	<html:form action="/approvals.do" enctype="multipart/form-data">
	
	  <logic:iterate id="approvalsForm" name="vendorMasterView">
	

<table class="bordered">
<tr>
<th colspan="4" align="center"><big><center>Vendor Master Form</center> </big></th>
</tr>
				<tr>
					<th colspan="8"><big>Basic Details Of Material</big></th>
   				</tr>

			<tr>
				<td>Request No. <font color="red">*</font></td>
				<td>
						<bean:write name="approvalsForm" property="requestNumber"/>

					
				</td>
				<td>Date <font color="red">*</font></td>
				<td>
						<bean:write name="approvalsForm" property="requestDate"/>
				</td>
			</tr>

			<tr>
				<!--<td>Location <font color="red">*</font></td>
				<td>
					<html:select name="approvalsForm" property="locationId">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="locationIdList" labelProperty="locationLabelList"/>
					</html:select>
				</td>
				-->
				<html:hidden property="locationId"/>
				<td>Account Group <font color="red">*</font></td>
				<td colspan="3">
											<bean:write name="approvalsForm" property="accountGroupId"/>

				</td>
			</tr>
							
			<tr>
				<td>View Type <font color="red">*</font></td>
				<td>
		     	<bean:write name="approvalsForm" property="purchaseView"/>

				</td>
				<td>Vendor Type <font color="red">*</font></td>
				<td>		     
					<bean:write name="approvalsForm" property="typeOfVendor"/>

				</td>
			</tr>

			<tr>
				<td>Title <font color="red">*</font></td>
				<td>
										<bean:write name="approvalsForm" property="title"/>

				</td>
				<td>Name <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="name"/>
</td>
			</tr>
								
			<tr>
				<td>Address1 <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="address1"/>
				</td>
				<td>Address2</td>
				<td>
				<bean:write name="approvalsForm" property="address2"/>
				</td>
			</tr>
								
			<tr>
				<td>Address3</td>
				<td>
				<bean:write name="approvalsForm" property="address3"/>
				</td>
				<td>Address4</td>
				<td align="left">
				<bean:write name="approvalsForm" property="address4"/>

				</td>
			</tr>

			<tr>
				<td>City <font color="red">*</font></td>
				<td>
					<bean:write name="approvalsForm" property="city"/>

				</td>
				<td>Pin Code <font color="red">*</font></td>
				<td>
					<bean:write name="approvalsForm" property="pinCode"/>
				</td>
			</tr>

			<tr>
				<td>Country <font color="red">*</font></td>
				<td>
										<bean:write name="approvalsForm" property="country"/>

				</td>
				<td>State <logic:notEmpty name="States"><font color="red">*</font></logic:notEmpty></td>
				<td>
													<bean:write name="approvalsForm" property="state"/>

				</td>
			</tr>
								
			<tr>
				<td>Landline No.</td>
				<td>
													<bean:write name="approvalsForm" property="landLineNo"/>

				<td>Mobile No.</td>
				<td>
													<bean:write name="approvalsForm" property="mobileNo"/>

				</td>
			</tr>

			<tr>
				<td>Fax No</td>
				<td>
							<bean:write name="approvalsForm" property="faxNo"/>

				</td>
				<td>e-Mail</td>
				<td>
							<bean:write name="approvalsForm" property="emailId"/>

				</td>
			</tr>
		
   		    <tr>
	  			<th colspan="4"><big>Tax Details</big></th>
   			</tr>
								
			<tr>
				<td>Currency <font color="red">*</font></td>
				<td>
										<bean:write name="approvalsForm" property="currencyId"/>
	
				</td>
				<td>Reconciliation A/c. <font color="red">*</font>
				<td>
												<bean:write name="approvalsForm" property="reConcillationActId"/>

				</td>
			</tr>

			<tr>
				<td>Is Eligible For TDS <font color="red">*</font>
				<td>
											<bean:write name="approvalsForm" property="elgTds"/>

				</td>
				<td>TDS Code <div id="mandatoryEligibleTds" style="visibility: hidden"><font color="red">*</font></div></td>
				<td>
										<bean:write name="approvalsForm" property="tdsCode"/>

				</td>
			</tr>
								
			<tr>
				<td>LST No.</td>
				<td>
											<bean:write name="approvalsForm" property="lstNo"/>

				</td>
				<td>Tin No<div style="visibility: hidden" id="tin3"><font color="red">*</font></div></td>
				<td>
											<bean:write name="approvalsForm" property="tinNo"/>

				</td>
			</tr>
								
			<tr>
				<td>CST No.<div style="visibility: hidden" id="cst4"><font color="red">*</font></div></td>
				<td>
															<bean:write name="approvalsForm" property="cstNo"/>

				</td>
				<td>Payment Term <font color="red">*</font></td>
				<td>
															<bean:write name="approvalsForm" property="paymentTermId"/>

				</td>
			</tr>

			<tr>
				<td>Account Clerk</td>
				<td>
										<bean:write name="approvalsForm" property="accountClerkId"/>

				</td>
				<td>Is Approved Vendor</td>
				<td>
										<bean:write name="approvalsForm" property="isApprovedVendor"/>

				</td>
			</tr>
								
			<tr>
				<td>PAN No <div style="visibility: hidden" id="group1"><font color="red">*</font></div> </td>
				<td>
														<bean:write name="approvalsForm" property="panNo"/>

				</td>
				<td>Service Tax Reg. No.<div style="visibility: hidden" id="Service2"><font color="red">*</font></div> </td>
				<td>
														<bean:write name="approvalsForm" property="serviceTaxRegNo"/>

				</td>
			</tr>
								
			<tr>
				<td>Is Reg. Excise Vendor </td>
				<td>
									<bean:write name="approvalsForm" property="regExciseVendor"/>

				</td>
				<td>ECC No. <div id="mandatoryRegisteredExciseVendor1"><font color="red">*</font></div></td>
				<td>
										<bean:write name="approvalsForm" property="serviceTaxRegNo"/>

				</td>
								
			</tr>
			<tr>
				<td>Excise Reg. No. <div id="mandatoryRegisteredExciseVendor2" style="visibility: hidden"><font color="red">*</font></div></td>
				<td>
								<bean:write name="approvalsForm" property="eccNo"/>

				</td>
				<td>Excise Range <div id="mandatoryRegisteredExciseVendor3" style="visibility: hidden"><font color="red">*</font></div></td>
				<td>
										<bean:write name="approvalsForm" property="exciseRange"/>

				</td>
			</tr>

			<tr>
				<td>Excise Division <div id="mandatoryRegisteredExciseVendor4" style="visibility: hidden"><font color="red">*</font></div></td>
				<td>
														<bean:write name="approvalsForm" property="exciseDivision"/>

				</td>
				<td>Commissionerate <div id="mandatoryRegisteredExciseVendor5" style="visibility: hidden"><font color="red">*</font></div></td>
				<td>
														<bean:write name="approvalsForm" property="commissionerate"/>

				</td>
			</tr>
							
			
						
												
		

			<logic:notEmpty name="documentDetails">
				<tr>
					<th colspan="4"><big>Uploaded Documents</big></th>
				</tr>
				<logic:iterate id="abc" name="documentDetails">
					<tr>
					
						<td colspan="5"><a href="/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/${abc.fileName }" target="_blank"><bean:write name="abc" property="fileName"/></a></td>
					</tr>
				</logic:iterate>
						
			
			</logic:notEmpty>
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
		<tr>			
	<th colspan="6">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<img src="images/star.gif" width="8" height="8" /></td>
				<td>
				<bean:write name="approvalsForm" property="sapCodeExists"/>
				</td>
		
			<td >SAP Code No<img src="images/star.gif" width="8" height="8" /></td>
				<td colspan="4"><bean:write name="approvalsForm" property="sapCodeNo"/>	</td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<bean:write name="approvalsForm" property="sapCreationDate"/>
				
			</td>
			
				<td >Code Created By<img src="images/star.gif" width="8" height="8" /></td><td colspan="4">
				<bean:write name="approvalsForm" property="sapCreatedBy"/>
			
				</td>
			</tr>
						
			
					<tr>
						<td colspan="6">
							<center><html:button property="method" value="Close" onclick="window.close()" styleClass="rounded" style="width: 100px" /></center>
						</td>
					</tr>
				</table> 
		</div>
		</div>
		</logic:iterate>
<div>&nbsp;</div>
<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeeName }</td><td>${abc.approveStatus }</td><td>${abc.date }</td><td>${abc.comments }</td></tr>
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
