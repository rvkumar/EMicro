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
var elemValue = elem.value;
if(elemValue=="Reject")
	{

	if(document.forms[0].comments.value==""){
	  alert("Please Add Some Comments");
	       document.forms[0].comments.focus();
	         return false;
	  }
	
	}
  
	var elemValue = elem.value;
	if(elemValue=='Created')
	{
	elemValue='Approve';
	
		   if(document.forms[0].sapCodeExists.checked==false && document.forms[0].sapCodeExistsNo.checked==false )
	    {
	      alert("Please Select  SAP Code Exists");
	      document.forms[0].sapCodeExists.focus();
	      return false;
	    } 
	    
	       if(document.forms[0].sapCodeExists.checked==true && document.forms[0].sapCodeExistsNo.checked==true )
	    {
	      alert("Please Choose Only One Option In SAP Code Exists");
	      document.forms[0].sapCodeExists.focus();
	      return false;
	    }
	    
	     	   if(document.forms[0].sapCodeNo.value=="")
	    {
	      alert("Please Enter SAP Code No.");
	      document.forms[0].sapCodeNo.focus();
	      return false;
	    } 
	  var st = document.forms[0].sapCodeNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].sapCodeNo.value=st; 
	
	
	}
	
	var reqId = document.forms[0].requestNumber.value;
var reqType="Material Master";
	var matGroup=document.forms[0].materialGroupId.value;
	var location=document.forms[0].locationId.value;
	var url="materials.do?method=StatusChangeMaterialrecord&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
	document.forms[0].action=url;
	document.forms[0].submit();
}
function goBack()
  {
  window.history.back()
  }	  
   function getCurrentRecord(){

var reqId = document.getElementById("reqId").value;
	var reqType = document.forms[0].materialCodeLists.value;
	

	var totalRecords=document.getElementById("totalReco").value;
	var scnt=document.getElementById("scnt").value;
	var ecnt=document.getElementById("ecnt").value;
		var filterby=document.getElementById("filterby").value;
	
	var url="materials.do?method=curentRecord&reqId="+reqId+"&reqType="+reqType+"&totalRecord="+totalRecords+"&scnt="+scnt+"&ecnt="+ecnt+"&filterby="+filterby;
	
	document.forms[0].action=url;
	document.forms[0].submit();


}
</script>
</head>
<body >
	<html:form action="/materials.do" enctype="multipart/form-data">
	<div align="center">
				<logic:present name="materialsForm" property="message">
					<font color="red" size="3"><b><bean:write name="materialsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="materialsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="materialsForm" property="message2" /></b></font>
				</logic:present>
			</div>
<table class="bordered">
				<tr>
					<th colspan="4" align="center"><big><center>VERP-Packaging</center> </big></th>
				</tr>
				<tr>
				
					<th colspan="4"><big>Basic Details Of Material</big></th>
   				</tr>
							<tr>
				<td>Request No <font color="red">*</font></td>
				<td align="left">
					<html:text property="requestNumber" readonly="true" style="background-color:grey;"/>
						
				</td>
				<td>Request Date <font color="red">*</font></td>
				<td align="left">
					<html:text property="requestDate" readonly="true" style="background-color:grey;"/>
				</td>
			</tr>
			<tr>
				<td>Location <font color="red">*</font></td>
				<td align="left">
					<html:select name="materialsForm" property="locationId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="locationIdList" labelProperty="locationLabelList"/>
					</html:select>
				</td>
				<td>Storage&nbsp;Location <font color="red">*</font></td>
				<td>
					<html:select  property="storageLocationId">
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
				</td>
			</tr>

<%--				<th width="274" class="specalt" scope="row">Material Type<img src="images/star.gif" width="8" height="8" /></th>--%>
<%--				<td align="left">--%>
<%--				--%>
<%--				<html:select name="PackageMaterialMasterForm" property="materialTypeId" styleClass="text_field" style="width:100px;">--%>
<%--				<html:option value="">--Select--</html:option>--%>
<%--				<html:options name="PackageMaterialMasterForm" property="materTypeIDList" --%>
<%--									labelProperty="materialTypeIdValueList"/>--%>
<%--					</html:select>--%>
<%--				<br /></td>--%>
<%--			</tr>--%>
		
			<tr>
				<td>Short Name <font color="red">*</font></td>
				<td colspan="3">
					<html:text property="materialShortName" maxlength="40" size="40" title="Maximum of 40 characters" style="width:400px;text-transform:uppercase"></html:text>
				</td>
			</tr>
			<tr>
				<td>Long Name <font color="red">*</font></td>
				<td colspan="3" >
						<html:text property="materialLongName" maxlength="80"   title="Maximum of 80 characters" style="width:400px;text-transform:uppercase" ></html:text>
				</td>
			</tr>
			<tr>
				<td>Mat.Group <font color="red">*</font></td>
				<td colspan="3">
					<html:select name="materialsForm" property="materialGroupId">
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
					</html:select>
				</td>
				</tr>
			<tr>
				<td>U O M <font color="red">*</font></td>
				<td>
					<html:select property="unitOfMeasId">
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues" style="text-transform:uppercase"/>
					</html:select>
				</td>
			
				<td>Purchasing Group <font color="red">*</font></td>
				<td >
					<html:select property="puchaseGroupId">
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="puchaseGroupIdList" labelProperty="puchaseGroupIdValues"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<th colspan="4"><big>Quality Requirement</big></th>
   			</tr>
			
			<tr>
				<td>Duty Element <font color="red">*</font></td>
				<td colspan="3">
					<html:select property="dutyElement">
						<html:option value="">--Select--</html:option>
						<html:option value="0">0-Indigenous Material with or without Cenvat</html:option>
						<html:option value="1">1-Duty Exempted Packing Materials for Finished product</html:option>
					</html:select>
				</td>
				</tr>
			<tr>
				<td>Package Material Group <font color="red">*</font></td>
				<td colspan="3">
					<html:select property="packageMaterialGroup">
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="packageGroupID" labelProperty="packageGroupIDValue"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>Type Of Material <font color="red">*</font></td>
				<td>
					<html:select property="typeOfMaterial" onchange="getMaterialStatus()">
						<html:option value="">--Select--</html:option>
						<html:option value="Printed Material">Printed Material</html:option>
						<html:option value="Plain Material">Plain Material</html:option>
					</html:select>
				</td>
			
			<logic:notEmpty name="materialTypeNotMandatory">
					<td>Artwork Code</td>
					<td>
						<html:text property="artworkNo" maxlength="20" size="20"></html:text>
					</td>
				</tr>
				<tr>
					<td>Is Artwork Revision </td>
					<td>
						<html:select property="isArtworkRevision" >
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>
			</logic:notEmpty>
			
			<logic:notEmpty name="materialTypeMandatory">
					<td>Artwork Code <font color="red">*</font></td>
					<td>
						<html:text property="artworkNo" maxlength="20" size="20" style="text-transform:uppercase"></html:text>
					</td>
				</tr>
				<tr>
					<td>Is Artwork Revision<font color="red">*</font></td>
					<td>
						<html:select property="isArtworkRevision" >
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>
			</logic:notEmpty>

					<td>Existing SAP Item Code <font color="red">*</font></td>
					<td>
						<html:text property="existingSAPItemCode" maxlength="20" size="20" style="text-transform:uppercase"></html:text>
					</td>
				</tr>
				<tr>
					<td>Is DMF Material <font color="red">*</font></td>
					<td>
						<html:select property="isDMFMaterial" onchange="isDMFStatus(this.value)">
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>

			<logic:notEmpty name="dmfNotMandatory">
					<td>DMF Grade</td>
					<td align="left">
						<html:select name="materialsForm" property="dmfGradeId">
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="dmfGradeIDList" labelProperty="dmfGradeIDValueList"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td>COS Grade & No</td>
					<td>
						<html:text property="cosGradeNo" maxlength="30" size="30" style="text-transform:uppercase"></html:text>
					</td>
			</logic:notEmpty>

			<logic:notEmpty name="dmfMandatory">
					<td>DMF Grade <font color="red">*</font></td>
					<td align="left">
						<html:select name="materialsForm" property="dmfGradeId">
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="dmfGradeIDList" labelProperty="dmfGradeIDValueList"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td>COS Grade & No <font color="red">*</font></td>
					<td>
						<html:text property="cosGradeNo" maxlength="30" size="30" style="text-transform:uppercase"></html:text>
					</td>
			</logic:notEmpty>

				<td>Additional Test</td>
				<td>
					<html:text property="additionalTest" maxlength="20" size="30" style="text-transform:uppercase"></html:text>
				</td>
			</tr>

			<tr>
				<th colspan="4"><big>Vendor / Manufacture Information</big></th>
   			</tr>
			<tr>
				<td>Is Material is Supplier/Manufacture/Site Specific <font color="red">*</font></td>
				<td>
					<html:select property="isVendorSpecificMaterial" onchange="isVendorStatus(this.value)">
						<html:option value="">--Select--</html:option>
						<html:option value="1">Yes</html:option>
						<html:option value="0">No</html:option>
					</html:select>
				</td>

			<logic:notEmpty name="vedorNotMandatory">
					<td>Manufacture&nbsp;Name</td>
					<td>
						<html:text property="mfgrName" maxlength="40" size="40" style="text-transform:uppercase"></html:text>
					</td>
				</tr>
				<tr>
					<td>Site Of Manufacture</td>
					<td>
						<html:text property="siteOfManufacture" maxlength="30" size="30" style="text-transform:uppercase"></html:text>
					</td>
			</logic:notEmpty>

			<logic:notEmpty name="vedorMandatory">
					<td>Manufacture Name <font color="red">*</font></td>
					<td>
						<html:text property="mfgrName" maxlength="40" size="40" style="text-transform:uppercase"></html:text>
					</td>
				</tr>
				<tr>
					<td>Site Of Manufacture <font color="red">*</font></td>
					<td>
						<html:text property="siteOfManufacture" maxlength="30" size="30" style="text-transform:uppercase"></html:text>
					</td>
			</logic:notEmpty>

				<td>Country <font color="red">*</font></td>
				<td>
					<html:select property="countryId">
						<html:option value="">--Select--</html:option>
						<html:options property="counID" labelProperty="countryName" />
					</html:select>
				</td>
			</tr>
			<tr>
				<td>Customer Name <font color="red">*</font></td>
				<td>
					<html:text property="customerName" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text>
				</td>
				<td>To Be Used In Products <font color="red">*</font></td>
				<td>
					<html:text property="toBeUsedInProducts" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text>
				</td>
			</tr>
			
			<tr>
				<th colspan="4"><big>Other Details</big></th>
   			</tr>
   
			<tr>
				<td>Temp.Condition</td>
				<td colspan="3">
					<html:select property="tempCondition" >
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="tempIDList" labelProperty="temValueList"/>
					</html:select>
				</td>
					</tr>
			<tr>
				<td>Storage Condition</td>
				<td colspan="3">
					<html:select property="storageCondition" >
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="storageIDList" labelProperty="storageLocList"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>Retest Days</td>
				<td>
					<html:text property="retestDays" maxlength="4" size="4"></html:text>
					<html:select property="retestType" >
						<html:option value="">--Select--</html:option>
						<html:option value="days">Days</html:option>
						<html:option value="months">Months</html:option>
					</html:select>
				</td>
				<td>Valuation Class <font color="red">*</font></td>
				<td>
					<html:select property="valuationClass" styleClass="text_field" >
						
						<html:options name="materialsForm" property="valuationClassID" labelProperty="valuationClassName"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>Approximate Value<font color="red">*</font></td>
				<td colspan="3">
					<html:text property="approximateValue"></html:text>
				</td>
				
			</tr>	

		<logic:notEmpty name="rejectedFLag">
			<tr>
			<td>Status</td>
			<td colspan="4"><b><big>For Reapproval</big></b></td>
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
							for (int i = 0; i < l; i++) {
								int x = v[i].lastIndexOf("/");
								String u = v[i].substring(x + 1);
						%>
	
						<tr>
							<td colspan="2" align="center"><a href="/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles/<%=u%>" target="_blank"><%=u%></a></td>
							

						</tr>
							<%
							}
							%>
				</logic:iterate>
								</logic:notEmpty>
		<tr>
	<th colspan="4">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<font color="red">*</font></td>
				<td>
				
						<html:checkbox property="sapCodeExists"/>
						<span class="text">Yes</span>
						&nbsp;&nbsp;&nbsp;
				<html:checkbox property="sapCodeExistsNo"/>
						<span class="text">No</span>
				</td>
			
			<td >SAP Code No<font color="red">*</font></td>
				<td>	<html:text property="sapCodeNo" styleClass="text_field" style="width:200px;"></html:text></td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<html:text property="sapCreationDate" styleId="sapCreationDate"  readonly="true" styleClass="text_field"/></td>
		
				<td >Code Created By<font color="red">*</font></td>
				<td><html:text property="sapCreatedBy" styleClass="text_field"  maxlength="12" readonly="true"></html:text>
				</td>
			</tr>			
		<tr>
		<td>
		Comments</td>
		<td colspan="3">
<html:textarea property="comments" style="width:100%;"></html:textarea>		
		
		</td>
		</tr>		
		<tr><td colspan="6" style="border:0px; text-align: center;">
   <logic:notEmpty name="approveButton">
	<input type="button" class="rounded" value="Created" onclick="changeStatus(this)" />&nbsp;&nbsp;
	</logic:notEmpty>
	<logic:notEmpty name="rejectButton">
			<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  /></td>
			
			</tr>				
	<input style="visibility:hidden;" id="scnt" value="<bean:write property="startAppCount"  name="materialsForm"/>"/>
					<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endAppCount"  name="materialsForm"/>"/>
					<input style="visibility:hidden;" id="reqId" value="<bean:write name="materialsForm" property="requestNo"/>"/>
					<input style="visibility:hidden;" id="reqType" value="<bean:write name="materialsForm" property="requestType"/>"/>
					<input style="visibility:hidden;" id="sValue" value="<bean:write property="searchText"  name="materialsForm"/>"/>
					<input style="visibility:hidden;" id="totalReco" value="<bean:write property="totalRecords"  name="materialsForm"/>"/>
					<input style="visibility:hidden;" id="filterby" value="<bean:write property="selectedFilter"  name="materialsForm"/>"/>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	<html:hidden property="selectedFilter"/>
	<html:hidden property="reqRequstType"/>
	<html:hidden property="userRole"/>
		<html:hidden property="materialCodeLists"/>
	

	
	 <logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeeName }</td><td>${abc.approveStatus }</td><td>${abc.date }</td><td>${abc.comments }</td>
	</tr>
	</logic:iterate>
	</logic:notEmpty>
		<br/>
	&nbsp;
	<br/>
	&nbsp;
	<table border="0">
	<tr><td>&nbsp;</td></tr>
	
	</table>
	</html:form>
</body>
