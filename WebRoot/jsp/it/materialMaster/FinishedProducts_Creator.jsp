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
	var reqId = document.forms[0].requestNo.value;
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
					<th colspan="8" style="text-align: center;"><big>Finished Product Form</big></th>
				</tr>
				<tr>
	 				<th colspan="4"><big>Basic Details</big></th>
   				</tr>
				<tr>
	 				<td>Request No <font color="red">*</font></td>
					<td><html:text property="requestNo" readonly="true" />
						<html:hidden property="typeDetails"/>
					</td>
					<td>Request Date <font color="red">*</font></td>
					<td><html:text property="requestDate" styleId="requestDate"  readonly="true" /></td>
				</tr>
				<tr>
					<td>Location <font color="red">*</font></td>
					<td><html:select name="materialsForm" property="locationId" disabled="true">
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="locationIdList" labelProperty="locationLabelList"/>
						</html:select>
					</td>
					<td>Manufactured At <font color="red">*</font></td>
					<td>
						<html:checkbox property="manufacturedAt" value="Own" onclick="setMaterialTypeId(this.value)"> Own</html:checkbox>
						&nbsp;&nbsp;
						<html:checkbox property="manufacturedAt" value="Third Party" onclick="setMaterialTypeId(this.value)"> Third Party</html:checkbox>	
					</td>				  
		        </tr>

<%--			<tr>--%>
<%--				<td>Material Type <font color="red">*</font></td>--%>
<%--				<td><html:select property="materialTypeId">--%>
<%--					<html:option value="">Select</html:option>--%>
<%--					<html:option value="1">FERT</html:option>--%>
<%--					<html:option value="2">HAWA</html:option>--%>
<%--					</html:select>--%>
<%--				<br /></td>--%>
<%--			</tr>--%>
			<html:hidden property="materialTypeId"/>
	        	<tr>
					<td>Storage&nbsp;Location<font color="red">*</font></td>
					<td colspan="3"><html:select  property="storageLocationId" >
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="storageID" labelProperty="storageIDName"/>
						</html:select>
					</td>
					</tr>
					<tr>
					<td>Short Name <font color="red">*</font></td>
					<td colspan="3"><html:text property="materialShortName" maxlength="40" size="60" title="Maximum of 40 characters" style="text-transform:uppercase" ></html:text></td>
				</tr>

				<tr>
					<td>Long Name <font color="red">*</font></td>
				<td colspan="3" ><html:text property="materialLongName" maxlength="80" size="80" title="Maximum of 80 characters" style="text-transform:uppercase;" ></html:text></td>
				</tr>
			
				<tr>
					<td>Material Group <font color="red">*</font></td>
					<td><html:select name="materialsForm" property="materialGroupId" >
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
						</html:select>
					</td>
					<td>U O M <font color="red">*</font></td>
					<td><html:select property="unitOfMeasId" >	
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues"/>
						</html:select>
					</td>
				</tr>
			
				<tr>
					<th colspan="4"><big>Sales Information</big></th>
   				</tr>

				<tr>
					<td>Product Type <font color="red">*</font></td>
					<td><html:select property="domesticOrExports" >	
							<html:option value="">--Select--</html:option>
							<html:option value="D">Domestic</html:option>
							<html:option value="E">Exports</html:option>
						</html:select>
					</td>
					<td>Country <font color="red">*</font></td>
					<td><html:select property="countryId" >
							<html:option value="">--Select--</html:option>
							<html:options property="counID" labelProperty="countryName" />
						</html:select>
				</tr>

				<tr>
					<td>Customer Name <font color="red">*</font></td>
					<td colspan="3"><html:text property="customerName" maxlength="40" size="60"  title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
					
					<!--<td>Product Insp. Memo <font color="red">*</font></td>
					<td><html:text property="prodInspMemo" maxlength="18" size="20" title="Maximum of 18 characters" style="text-transform:uppercase"></html:text></td>
				--></tr>
			<html:hidden  property="prodInspMemo"/>
				<tr>
					<td>Saleable or Sample <font color="red">*</font></td>
					<td><html:select property="saleableOrSample" >	
							<html:option value="">--Select--</html:option>
							<html:option value="1">Saleable</html:option>
							<html:option value="2">Sample</html:option>
						</html:select>
					</td>
					<td>Pack Size <font color="red">*</font></td>
					<td><html:select property="salesPackId" >	
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="packSizeID" labelProperty="packSizeName"/>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>Pack Type <font color="red">*</font></td>
					<td><html:select property="packTypeId" >	
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="packTypeID" labelProperty="packTypeName"/>
						</html:select>
					</td>
					<td>Sales U O M <font color="red">*</font></td>
					<td><html:select property="salesUnitOfMeaseurement" >	
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="salesUOMID" labelProperty="salesUOMName"/>
						</html:select>
					</td>
				</tr>
			
			<tr>
				<td>Division <font color="red">*</font></td>
				<td><html:select property="divisionId" >	
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="divisonID" labelProperty="divisonName"/>
					</html:select>
				</td>
				<td>Therapeutic Segment <font color="red">*</font></td>
				<td><html:select property="therapeuticSegmentID" >	
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="therapeuticID" labelProperty="therapeuticName" style="width: 250px"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Brand <font color="red">*</font></td>
				<td>
					<html:select property="brandID" >	
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="brandIDList" labelProperty="brandNameList"/>
					</html:select>
				</td>
				<td>Strength <font color="red">*</font></td>
				<td><html:select property="srengthId" >	
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="strengthIDList" labelProperty="strengthNameList"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Generic Name <font color="red">*</font></td>
				<td><html:select property="genericName" >	
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="genericIDList" labelProperty="genericNameList" style="width: 250px"/>
					</html:select>
				</td>
				<td>Gross Weight <font color="red">*</font></td>
				<td><html:text property="grossWeight" style="text-transform:uppercase" ></html:text></td>
			</tr>
			
			<tr>
				<td>Net Weight <font color="red">*</font></td>
				<td><html:text property="netWeight" ></html:text></td>
				<td>Weight UOM <font color="red">*</font></td>
				<td><html:select property="weightUOM" >	
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="weightUOMID" labelProperty="weightUOMName"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Shipper Dimension <font color="red">*</font></td>
				<td ><html:text property="dimension" maxlength="30" size="40" style="text-transform:uppercase"></html:text></td>
				<td>Tax Classification</td>
				<td><html:select property="taxClassification" >
					<html:option value="">---Select---</html:option>
					<html:option value="0">ED Exempt</html:option>
					<html:option value="1">Taxable(FG/RM/PM/Ve)</html:option>
					<html:option value="2">Taxable (Scrap)</html:option>
					<html:option value="3">Taxable(Food Pro)</html:option>
					<html:option value="4">Taxable (Vet Feed)</html:option>
					<html:option value="5">No Tax (Ph Sam)</html:option>
					<html:option value="6">Taxable(Cosm Pro)</html:option>
				</html:select>
			</tr>
		<tr>
		<td>Material Pricing Group</td>
		<td colspan="3"><html:select property="materialPricing" >
		<html:option value="">---Select---</html:option>
			<html:option value="1">Normal</html:option>
			<html:option value="2">Spare parts</html:option>
			<html:option value="11">Scheduled(Controled)</html:option>
			<html:option value="12">Un-Scheduled(De-Con)</html:option>
			<html:option value="13">PS / Promo (Micro)</html:option>
			<html:option value="14">No MRP ED Extra(Mic)</html:option>
			<html:option value="15">No MRP ED Incl (Mic)</html:option>
			<html:option value="16">Scrap IT Extra (Mic)</html:option>
			<html:option value="17">MRP(Con)-ED Exe(Mic)</html:option>
			<html:option value="18">MRP(DeC)-ED Exe(Mic)</html:option>
			<html:option value="19">No MRP-ED Exe(Mic)</html:option>
			<html:option value="20">Food Prod(MRP)-Micro</html:option>
			<html:option value="21">Vet Prod (MRP)-Micr</html:option>
			<html:option value="22">Generic Price Grp-Mi</html:option>
			<html:option value="23">MRP(Vet)-ED Exe(Mic)</html:option>
			<html:option value="24">P-to-P ED Extra(Mic)</html:option>
			<html:option value="25">Cosm Prod(MRP)-Micro</html:option>
			<html:option value="26">Scrap - ED Exe(Mic)</html:option>
			<html:option value="27">Import Items PG</html:option>
		</html:select>
		</tr>
			

			<tr>
				<th colspan="4"><big>Other Details</big></th>
			<tr>

			<tr>
				<td>Shelf Life <font color="red">*</font></td>
				<td><html:text property="shelfLife" maxlength="5" size="5" ></html:text>
					<html:select property="shelfType" >
						<html:option value="">-----Select-----</html:option>
						<html:option value="days">Days</html:option>
						<html:option value="months">Months</html:option>
					</html:select>
				</td>

				<logic:notEmpty name="standardBathcNotMandatory">
						<td>Std. Batch Size<font color="red">*</font></td>
						<td><html:text property="standardBatchSize" style="text-transform:uppercase" ></html:text></td>
					</tr>
					<tr>
						<td>Batch Code</td>
						<td><html:text property="batchCode" maxlength="10" size="10" style="text-transform:uppercase" ></html:text></td>
				</logic:notEmpty>
	
				<logic:notEmpty name="standardBathcMandatory">
						<td>Std. Batch Size <font color="red">*</font></td>
						<td><html:text property="standardBatchSize" style="text-transform:uppercase" ></html:text></td>
					</tr>
					<tr>
						<td>Batch Code </td>
						<td><html:text property="batchCode" maxlength="10" size="10" style="text-transform:uppercase" ></html:text></td>
				</logic:notEmpty>

				<td>Valuation Class <font color="red">*</font></td>
				<td><html:select property="valuationClass" >
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="valuationClassID" labelProperty="valuationClassName"/>
					</html:select>
				</td>
			</tr>
			
			<logic:notEmpty name="manufactureNotMandatory">
			<tr>
				<td>Purchase Group</td>
				<td colspan="3"><html:select property="puchaseGroupId" >
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="puchaseGroupIdList" labelProperty="puchaseGroupIdValues"/>
					</html:select>
				</td>
			</tr>
			</logic:notEmpty>
			
			<logic:notEmpty name="manufactureMandatory">
			<tr>
				<td>Purchase Group <font color="red">*</font></td>
				<td colspan="3"><html:select property="puchaseGroupId" >
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="puchaseGroupIdList" labelProperty="puchaseGroupIdValues"/>
					</html:select>
				</td>
			</tr>
			</logic:notEmpty>

			<logic:notEmpty name="approved">
				<tr>			
					<th colspan="4">Approver Details</th>
		   		</tr>
				<tr>
					<td>Approve Type</td>
					<td><html:select name="materialsForm" property="approveType">
							<html:option value="">--Select--</html:option>
							<html:option value="Pending">Pending</html:option>
							<html:option value="Approved">Approved</html:option>
							<html:option value="Cancel">Cancel</html:option>
						</html:select>
					</td>
				</tr>
			</logic:notEmpty>
<logic:notEmpty name="rejectedFLag">
			<tr>
			<td>Status</td>
			<td colspan="4"><b><big>For Reapproval</big></b></td>
			</tr>
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
			<!--<input type="button" class="rounded" value="Close" onclick="goBack()"  />
			
			--><input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  />
			</td>
			
			</tr>	
		</table>
	
							
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
	</table>
	</logic:notEmpty>
	
	<br/>
	&nbsp;
	<br/>
	&nbsp;
		</html:form>
</body>
