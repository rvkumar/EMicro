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
	



<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">


function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


	 function popupCalender(param)
	  {
	      var cal = new Zapatec.Calendar.setup({
	      inputField     :     param,     // id of the input field
	      singleClick    :     true,     // require two clicks to submit
	      ifFormat       :    "%d/%m/%Y ",     // format of the input field
	      showsTime      :     false,     // show time as well as date
	      button         :    "button2"  // trigger button 
	      });
	  }

function changeStatus(elem){

if(document.forms[0].gstinNo.value=="")
	    {
	      alert("Please Enter GSTIN_Number");
	      document.forms[0].gstinNo.focus();
	      return false;
	    }
		
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
	var reqType = "";
	var matGroup="";
	var location="";
	var url="approvals.do?method=statusChangeForCustomerRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
	document.forms[0].action=url;
	document.forms[0].submit();
}
function goBack()
  {
  window.history.back()
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
<body style="text-transform: uppercase;">
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
 			<th colspan="4"><center><big>Customer Master Creation</big></center> </th>
		</tr>
		<tr>
	 		<th colspan="4"><big>General Information</big></th>
   		</tr>
		<tr>
			<td>Request Number <font color="red">*</font></td>
			<td><html:text property="requestNo" readonly="true"/>
				<html:hidden property="typeDetails"/>
			</td>
			<td>Request Date <font color="red">*</font></td>
			<td>
				<html:text property="requestDate" styleId="requestDate"  readonly="true"/>
			</td>
		</tr>
		<tr>
			<td rowspan="1">Account Group</td>
			<td rowspan="1"><html:select property="accGroupId" disabled="true" >
					<html:option value="">--Select--</html:option>
					<html:option value="IN">Domestic Regular</html:option>
					<html:option value="IM">Export Customer</html:option>
					<html:option value="LL">Loan-Licence</html:option>
					<html:option value="FS">Field Staff</html:option>
					<html:option value="007">Plants</html:option>
				</html:select>
			</td>
			<td>View Type</td>
			<td>Sales <html:checkbox property="sales" disabled="true"/>
				Accounts <html:checkbox property="accounts" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td>Customer Type <font color="red">*</font></td>
			<td>Domestic <html:checkbox property="domestic" disabled="true"/> 
				Exports <html:checkbox property="exports"  disabled="true"/>
			</td>
			<td>Employee Code</td>
			<td><html:text property="cutomerCode" readonly="true"></html:text >
		</tr>
		<tr>
			<td>Name <font color="red">*</font></td>
			<td colspan="3"><html:text property="customerName" maxlength="40" size="65" readonly="true" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>Address1 <font color="red">*</font></td>
			<td><html:text property="address1" maxlength="40" size="45" readonly="true" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>Address2</td>
			<td><html:text property="address2" maxlength="40" size="45" readonly="true" title="Maximum of 40 characters"  style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>Address3</td>
			<td><html:text property="address3" maxlength="40" size="45" readonly="true" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>Address4</td>
			<td><html:text property="address4" maxlength="40" size="45" readonly="true" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>City</td>
			<td><html:text property="city" maxlength="40" size="40" readonly="true" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>Pincode</td>
			<td><html:text property="pincode" maxlength="6" size="10"  readonly="true" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>Country <font color="red">*</font></td>
			<td><html:select property="countryId" onchange="getStates()" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options property="counID" labelProperty="countryName" />
				</html:select>
			</td>

			<logic:empty name="diplayStates">
				<td>State <font color="red">*</font></td>
				<td><html:select property="state" disabled="true">
						<html:option value="">--Select--</html:option>
						
					</html:select>
				</td>
			</logic:empty>
	
			<logic:notEmpty name="diplayStates">
				<td>State <font color="red">*</font></td>
				<td><html:select property="state" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options property="stateId" labelProperty="states" />
					</html:select>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>

			<td>Landline</td>
			<td><html:text property="landlineNo"  readonly="true" maxlength="20" size="20" style="text-transform:uppercase"/></td>
			<td>Mobile</td>
			<td><html:text property="mobileNo"  readonly="true" maxlength="20" size="20" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>Fax</td>
			<td><html:text property="faxNo"  readonly="true" maxlength="20" size="20" style="text-transform:uppercase"/></td>
			<td>e-Mail</td>
			<td><html:text property="emailId" readonly="true" maxlength="20" size="20" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
	 		<th colspan="4"><big>Sales</big></th>
   		</tr>
		<tr>
			<td>Customer Group <font color="red">*</font></td>
			<td><html:select property="customerGroup" disabled="true">
				<html:option value="">--Select--</html:option>
				<html:options name="approvalsForm" property="cusGroupID" labelProperty="cusGroupList"/>
				</html:select>
			</td>
			<td>Price Group <font color="red">*</font></td>
			<td><html:select property="priceGroup" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="approvalsForm" property="priceGroupID" labelProperty="piceGroupList"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td>Price List <font color="red">*</font></td>
			<td><html:select property="priceList" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="approvalsForm" property="priceListID" labelProperty="piceListValue"/>
				</html:select>
			</td>
			<td>Tax Type <font color="red">*</font></td>
			<td><html:select property="taxType" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="approvalsForm" property="taxTypeID" labelProperty="taxTypeValue"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td>Currency</td>
			<td><html:select property="currencyId" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options property="currenIds" labelProperty="currencys" />
				</html:select>
			</td>
			<td>Payment Term</td>
			<td>
			<html:select  name="approvalsForm" property="paymentTermID" disabled="true">
						<html:option value="">--Select--</html:option>
							<html:options name="approvalsForm" property="paymentTermList" labelProperty="paymentTermLabelList"/>
					</html:select>
					</td>
		</tr>
		<tr>
			<td>Account Clerk</td>
			<td colspan="3">
			
			<html:select name="approvalsForm" property="accountClerkId" disabled="true" >
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="accountClerkList" labelProperty="accountClerkLabelList"/>
					</html:select>
			
			</td>
		</tr>
		<tr>
	 		<th colspan="4"><big>Excise / Tax</big></th>
   		</tr>
		<tr>
			<td>Is Reg.Excise TDS</td>
			<td><html:select property="tdsStatus" onchange="getTDSState(this.value)" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:option value="True">Yes</html:option>
					<html:option value="False">No</html:option>
				</html:select>
			</td>

			<logic:empty name="setTdsState">
				<td>TDS Code</td>
				<td><html:select property="tdsCode" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options property="tdsIds" labelProperty="tdsCodes" />
					</html:select>
				</td>
			</logic:empty>

			<logic:notEmpty name="setTdsState">
				<td>TDS Code <font color="red">*</font></td>
				<td><html:select property="tdsCode" styleClass="text_field" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options property="tdsIds" labelProperty="tdsCodes" />
					</html:select>
				</td>
			</logic:notEmpty>

		</tr>
		
		<tr>
				<td>GSTIN Number<font color="red">*</font></td>
				<td colspan="3">
				<html:text property="gstinNo" styleId="gstinNo" maxlength="15"   title="Maximum of 15 characters" style="width:150px;text-transform:uppercase" ></html:text>
				</td>
				</tr>	
		<tr>
			<td>LST No. </td>
			<td><html:text property="listNumber" maxlength="40" readonly="true"  size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>Tin No.</td>
			<td><html:text property="tinNumber" maxlength="40" readonly="true" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>CST No.</td>
			<td><html:text property="cstNumber" maxlength="40" readonly="true" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>PAN No.</td>
			<td><html:text property="panNumber" maxlength="40" readonly="true"  size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>Service Tax Reg. No. </td>
			<td><html:text property="serviceTaxNo" maxlength="40" size="45" readonly="true" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>Is Reg.Excise Customer <font color="red">*</font></td>
			<td><html:select property="isRegdExciseVender" onchange="getREVState(this.value)" disabled="true">
					<html:option value="">-----Select-----</html:option>
					<html:option value="True">Yes</html:option>
					<html:option value="False">No</html:option>
				</html:select>
			</td>
		</tr>

		<logic:notEmpty name="setRegdExciseVenderNotMandatory">
			<tr>
				<td>ECC No</td>
				<td><html:text property="eccNo" maxlength="40" readonly="true" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
				<td>Excise Reg. No.</td>
				<td><html:text property="exciseRegNo" maxlength="40" readonly="true" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			</tr>
			<tr>
				<td>Excise Range</td>
				<td><html:text property="exciseRange" maxlength="40" readonly="true" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
				<td>Excise Division</td>
				<td><html:text property="exciseDivision" maxlength="40" readonly="true" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			</tr>	
		</logic:notEmpty>

		<logic:notEmpty name="setRegdExciseVender">
			<tr>
				<td>ECC No <font color="red">*</font></td>
				<td><html:text property="eccNo" maxlength="40" size="45" readonly="true" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
				<td>Excise Reg. No. <font color="red">*</font></td>
				<td><html:text property="exciseRegNo" maxlength="40" size="45" readonly="true" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			</tr>
			<tr>
				<td>Excise Range <font color="red">*</font></td>
				<td><html:text property="exciseRange" maxlength="40" size="45" readonly="true" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
				<td>Excise Division <font color="red">*</font></td>
				<td><html:text property="exciseDivision" maxlength="40" size="45" readonly="true" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			</tr>	
		</logic:notEmpty>

		<tr>
			<td>DL No.1 </td>
			<td><html:text property="dlno1" maxlength="40" size="45" readonly="true" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>DL No.2</td>
			<td><html:text property="dlno2" maxlength="40" size="45" readonly="true" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
		</tr>

		<logic:notEmpty name="approved">
			<tr>
				<td>Approve Type</td>
				<td align="left">
					<html:select name="approvalsForm" property="approveType" styleClass="text_field" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:option value="Pending">Pending</html:option>
						<html:option value="Approved">Approved</html:option>
						<html:option value="Cancel">Cancel</html:option>
					</html:select>
				</td>
			</tr>
		</logic:notEmpty>

		

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
					<td colspan="4"> <a href="/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles/<%=u%>" target="_blank"><%=u%></a></td>
					
				</tr>
					<%
					}
					%>		
			</logic:iterate>
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
</logic:notEmpty>
</html:form>
</body>
			