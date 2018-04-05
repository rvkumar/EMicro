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
	var reqType = "ROH";
	var matGroup=document.forms[0].reqMaterialGroup.value;
	var location=document.forms[0].locationId.value;
	var url="approvals.do?method=statusChangeForCustomerRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
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
	
	  <logic:iterate id="approvalsForm" name="CustomerMasterView">

<table class="bordered">

<tr>
					<th colspan="8" style="text-align: center;"><big>Customer Code Request Form</big></th>
				</tr>
				<tr>
					<th colspan="8"><big>Basic Details Of Material</big></th>
   				</tr>
				<tr>	  
		<tr>
			<td>Request Number <font color="red">*</font></td>
			<td>
								<bean:write name="approvalsForm" property="requestNumber"/>

			</td>
			<td>Request Date <font color="red">*</font></td>
			<td>
								<bean:write name="approvalsForm" property="requestDate"/>
			</td>
		</tr>
		<tr>
			<td rowspan="1">Account Group</td>
			<td rowspan="1">
										<bean:write name="approvalsForm" property="accGroupId"/>

			</td>
			<td>View Type</td>
			<td>										
			<bean:write name="approvalsForm" property="sales"/>

			</td>
		</tr>
		<tr>
			<td>Customer Type <font color="red">*</font></td>
			<td>		
				<bean:write name="approvalsForm" property="customerType"/>

			</td>
			<td>Employee Code</td>
			<td>
				<bean:write name="approvalsForm" property="cutomerCode"/>
			</td>
		</tr>
		<tr>
			<td>Name <font color="red">*</font></td>
			<td colspan="3">
				<bean:write name="approvalsForm" property="customerName"/>
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
			<td>
				<bean:write name="approvalsForm" property="address4"/>
			</td>
		</tr>
		<tr>
			<td>City</td>
			<td>
				<bean:write name="approvalsForm" property="city"/>
			</td>
			<td>Pincode</td>
			<td>
				<bean:write name="approvalsForm" property="pincode"/>
			</td>
		</tr>
		<tr>
			<td>Country <font color="red">*</font></td>
			<td>
							<bean:write name="approvalsForm" property="countryId"/>

			</td>

			<logic:empty name="diplayStates">
				<td>State <font color="red">*</font></td>
				<td>
								<bean:write name="approvalsForm" property="state"/>

				</td>
			</logic:empty>
	
			<logic:notEmpty name="diplayStates">
				<td>State <font color="red">*</font></td>
				<td>
							<bean:write name="approvalsForm" property="state"/>

				</td>
			</logic:notEmpty>
		</tr>
		<tr>

			<td>Landline</td>
			<td>
				<bean:write name="approvalsForm" property="landlineNo"/>
          </td>
			<td>Mobile</td>
			<td>
				<bean:write name="approvalsForm" property="mobileNo"/>
			</td>
		</tr>
		<tr>
			<td>Fax</td>
			<td>
				<bean:write name="approvalsForm" property="faxNo"/>
			</td>
			<td>e-Mail</td>
			<td>
				<bean:write name="approvalsForm" property="emailId"/>
			</td>
		</tr>
		<tr>
	 		<th colspan="4"><big>Sales</big></th>
   		</tr>
		<tr>
			<td>Customer Group <font color="red">*</font></td>
			<td>				
			<bean:write name="approvalsForm" property="customerGroup"/>

			</td>
			<td>Price Group <font color="red">*</font></td>
			<td>		
				<bean:write name="approvalsForm" property="priceGroup"/>

			</td>
		</tr>
		<tr>
			<td>Price List <font color="red">*</font></td>
			<td>
						<bean:write name="approvalsForm" property="priceList"/>

			</td>
			<td>Tax Type <font color="red">*</font></td>
			<td>
							<bean:write name="approvalsForm" property="taxType"/>

			</td>
		</tr>
		<tr>
			<td>Currency</td>
			<td>
						<bean:write name="approvalsForm" property="currencyId"/>

			</td>
			<td>Payment Term</td>
			<td>
							<bean:write name="approvalsForm" property="paymentTermID"/>

					</td>
		</tr>
		<tr>
			<td>Account Clerk</td>
			<td colspan="3">
			
						<bean:write name="approvalsForm" property="accountClerkId"/>

			
			</td>
		</tr>
		<tr>
	 		<th colspan="4"><big>Excise / Tax</big></th>
   		</tr>
		<tr>
			<td>Is Reg.Excise TDS</td>
			<td>
							<bean:write name="approvalsForm" property="isRegdExciseVender"/>

			</td>

			<logic:empty name="setTdsState">
				<td>TDS Code</td>
				<td>
								<bean:write name="approvalsForm" property="tdsCode"/>

				</td>
			</logic:empty>

			<logic:notEmpty name="setTdsState">
				<td>TDS Code <font color="red">*</font></td>
				<td>
								<bean:write name="approvalsForm" property="tdsCode"/>

				</td>
			</logic:notEmpty>

		</tr>
		<tr>
			<td>LST No. </td>
			<td>
				<bean:write name="approvalsForm" property="listNumber"/>
			</td>
			<td>Tin No.</td>
			<td>
				<bean:write name="approvalsForm" property="tinNumber"/>
			</td>
		</tr>
		<tr>
			<td>CST No.</td>
			<td>
				<bean:write name="approvalsForm" property="cstNumber"/>
			</td>
			<td>PAN No.</td>
			<td>
				<bean:write name="approvalsForm" property="panNumber"/>
			</td>
		</tr>
		<tr>
			<td>Service Tax Reg. No. </td>
			<td>
				<bean:write name="approvalsForm" property="serviceTaxNo"/>
			</td>
			<td>Is Reg.Excise Customer <font color="red">*</font></td>
			<td>
							<bean:write name="approvalsForm" property="tdsStatus"/>

			</td>
		</tr>

		<logic:notEmpty name="setRegdExciseVenderNotMandatory">
			<tr>
				<td>ECC No</td>
				<td>
				<bean:write name="approvalsForm" property="eccNo"/>
				</td>
				<td>Excise Reg. No.</td>
				<td>
				<bean:write name="approvalsForm" property="exciseRegNo"/>
				</td>
			</tr>
			<tr>
				<td>Excise Range</td>
				<td>
				<bean:write name="approvalsForm" property="exciseRange"/>
				</td>
				<td>Excise Division</td>
				<td>
				<bean:write name="approvalsForm" property="exciseDivision"/>
				</td>
			</tr>	
		</logic:notEmpty>

		<logic:notEmpty name="setRegdExciseVender">
			<tr>
				<td>ECC No <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="eccNo"/>
				</td>
				<td>Excise Reg. No. <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="exciseRegNo"/>
				</td>
			</tr>
			<tr>
				<td>Excise Range <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="exciseRange"/>
				</td>
				<td>Excise Division <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="exciseDivision"/>
				</td>
			</tr>	
		</logic:notEmpty>

		<tr>
			<td>DL No.1 </td>
			<td>
				<bean:write name="approvalsForm" property="dlno1"/>
			</td>
			<td>DL No.2</td>
			<td>
				<bean:write name="approvalsForm" property="dlno2"/>
			</td>
		</tr>

		<logic:notEmpty name="approved">
			<tr>
				<td>Approve Type</td>
				<td align="left">
									<bean:write name="approvalsForm" property="domestic"/>

				</td>
			</tr>
		</logic:notEmpty>

		<tr>
			<th colspan="4"><big>Attachments <font color="red">*</font></big></th>
		</tr>
		<tr>
			
		</tr>

 		<logic:notEmpty name="listName">
			<tr>
				 <th colspan="4"><big>Uploaded Documents</big></th>
			</tr>
		
			<logic:iterate name="listName" id="listid">
				<bean:define id="file" name="listid" property="fileList" />
					<%
						String s = file.toString();
						String v[] = s.split(",");
						int l = v.length;
						for (int i = 0; i < l; i++) 
						{
						int x=v[i].lastIndexOf("/");
							String u=v[i].substring(x+1);
						
					%>
				<tr>
					<td colspan="4"><a href="/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles/<%=u%>" target="_blank"><%=u%></a></td>
					
				</tr>
					<%
					}
					%>		
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
							<html:button property="method" value="Close" onclick="history.back(-1)" styleClass="rounded" style="width: 100px" ></html:button>
						</td>
					</tr>
				</table> 
		</div>
		</div>
		</logic:iterate>

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
