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

function changeStatus(){
	
	
	if(document.forms[0].hsnCode.value=="")
	    {
	      alert("Please Enter HSN code");
	      document.forms[0].hsnCode.focus();
	      return false;
	    }
	     var hsnCode = document.forms[0].hsnCode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(hsnCode)) {
             alert("HSNcode its should be Integer ");
                document.forms[0].hsnCode.focus();
            return false;
        }
	var reqId = document.forms[0].requestNo.value;
	var reqType="Material Master";
	
	var matGroup=document.forms[0].materialGroupId.value;
	var location=document.forms[0].locationId.value;
	
	
	var url="materials.do?method=updateMaterialrecord&reqId="+reqId+"&reqType="+reqType+"&matGroup="+matGroup+"&location="+matGroup;
	document.forms[0].action=url;
	document.forms[0].submit();
}
function goBack()
  {
  window.history.back();
  }	  
   function getCurrentRecord(){

	var url="materials.do?method=pendingRecords";
	
	document.forms[0].action=url;
	document.forms[0].submit();


} 	  
</script>
<style >

.no
{pointer-events: none; 
}
</style>

</head>
<body >
	<html:form action="/materials.do" enctype="multipart/form-data">
	<html:hidden property="fromDate" name="materialsForm"/>
	<html:hidden property="toDate" name="materialsForm"/>
	<html:hidden property="materialGrup" name="materialsForm" />
	<html:hidden property="locationId" name="materialsForm"/>
	<html:hidden property="selectedFilter" name="materialsForm" />
	<html:hidden property="materialCodeLists" name="materialsForm" />
	
	<div align="center">
				<logic:present name="materialsForm" property="message">
					<font color="red" size="3"><b><bean:write name="materialsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="materialsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="materialsForm" property="message2" /></b></font>
				</logic:present>
			</div>

<logic:empty name="save">
	<div class="no" >
	</logic:empty>
	<logic:notEmpty name="save">
	<div class="nos" >
	</logic:notEmpty>

			<table class="bordered" width="90%">
			<tr>
					<th colspan="8" style="text-align: center;"><big>ZPSR-General Material Form</big></th>
				</tr>
   				<tr>
	 				<th colspan="4"><big>Basic Details Of Material</big></th>
   				</tr>

				<tr>
	 				<td>Request No <font color="red">*</font></td>
					<td><html:text property="requestNo" styleClass="content" readonly="true" style="background-color:grey;"/>
						<html:hidden property="typeDetails"/>
					</td>
					<td>Request&nbsp;Date <font color="red">*</font></td>
					<td>
						<html:text property="requestDate" styleId="requestDate" styleClass="content" readonly="true" style="background-color:grey;"/>
					</td>
				</tr>

				<tr>
					<td>Location <font color="red">*</font></td>
					<td colspan="3"><html:select name="materialsForm" property="locationId" styleClass="content" disabled="true">
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="locationIdList" labelProperty="locationLabelList"/>
						</html:select>
					</td>
				</tr>

					<html:hidden property="materialTypeId"/>

				<tr>
					<td>Short Name <font color="red">*</font></td>
					<td colspan="3">
						<html:text property="materialShortName" styleClass="content" style="width:380px;text-transform:uppercase;" size="55" maxlength="40" ></html:text>
					</td>
				</tr>

				<tr>
					<td>Long Name <font color="red">*</font></td>
					<td colspan="3">
						<html:text property="materialLongName" styleClass="content" style="width:580px;text-transform:uppercase;" size="90" maxlength="80"></html:text>
					</td>
				</tr>

				<tr>
					<td>Material Group <font color="red">*</font></td>
					<td><html:select name="materialsForm" property="materialGroupId" styleClass="content">
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/></html:select>
					</td>
					<td>Storage&nbsp;Location <font color="red">*</font></td>
					<td><html:select  property="storageLocationId" styleClass="content">
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="storageID" labelProperty="storageIDName"/>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>U O M <font color="red">*</font></td>
					<td><html:select property="unitOfMeasId" styleClass="content" >
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues"/>
						</html:select>
					</td>
					<td>Purchase&nbsp;Group <font color="red">*</font></td>
					<td><html:select property="puchaseGroupId" styleClass="text_field" >
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="puchaseGroupIdList"	labelProperty="puchaseGroupIdValues"/>
						</html:select>
					</td>
				</tr>

				<tr>
					<th colspan="4"><big>Other Details</big></th>
   				</tr>
			
			
				<!--<tr>
				<th width="274" class="specalt" scope="row">Material Used In<img src="images/star.gif" width="8" height="8" /></th>
				<td>
				<html:select property="materialUsedIn" styleClass="text_field" >
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
				</td>
				</tr>
				-->
				<html:hidden property="materialUsedIn"/>
				
				<tr>
					<td>Is it a New Equipment /  Machine <font color="red">*</font></td>
					<td colspan="3"><html:select property="isEquipment" styleClass="content" onclick="checkEquipment()" style="text-transform:uppercase;">
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>Equipment&nbsp;Name <div id="equipment1" style="visibility: hidden"><font color="red">*</font></div></td>
					<td colspan="3">
						<html:text property="equipmentName" styleClass="content" style="width:580px;text-transform:uppercase;" size="80" maxlength="80"></html:text>
					</td>
				</tr>

				<tr>
					<td>Equipment&nbsp;Make <div id="equipment2" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
						<html:text property="equipmentMake" styleClass="content" style="width:200px;text-transform:uppercase;" size="80" maxlength="20"></html:text>
					</td>
					<td>Is Spare <font color="red">*</font></td>
					<td><html:select property="isSpare" styleClass="content" onchange="checkSpare()">
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>Component&nbsp;Make <div id="spare1" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
						<html:text property="componentMake" styleClass="content" style="width:200px;text-transform:uppercase;" size="20" maxlength="20" onchange=""></html:text>
					</td>
					<td>OEM Part No.<div id="spare2" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
						<html:text property="oemPartNo" styleClass="content" style="width:200px;text-transform:uppercase;" size="20" maxlength="20"></html:text>
					</td>
				</tr>

				<tr>
					<td>MOC <div id="spare1" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
					<html:text property="moc" styleClass="content" style="width:200px;text-transform:uppercase;" size="20" maxlength="20" readonly="true"></html:text>
									
					</td>
					<td>Rating<div id="spare2" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
					<html:text property="rating" styleClass="content" style="width:200px;text-transform:uppercase;" size="20" maxlength="20" readonly="true"></html:text>
									
					</td>
				</tr>

				<tr>
					
					<td>Range </td>
					<td>	
					<html:text property="range" styleClass="content" style="width:200px;text-transform:uppercase;" size="20" maxlength="20" readonly="true"></html:text>							
						

					</td>
					<td>Is it New Furniture / Doors / Windows <font color="red">*</font></td>
					<td><html:select property="isItNewFurniture" styleClass="content" >
							<html:option value="">--Select--</html:option>
							<html:option value="True">Yes</html:option>
							<html:option value="False">No</html:option>
							<html:option value="N/A">N/A</html:option>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>Is it for New Facility / Expansion Area <font color="red">*</font></td>
					<td><html:select property="isItFacility" styleClass="content" >
							<html:option value="">--Select--</html:option>
							<html:option value="True">Yes</html:option>
							<html:option value="False">No</html:option>
							<html:option value="N/A">N/A</html:option>
					</html:select></td>
					<td>Is Spare required for New Equipment <font color="red">*</font></td>
					<td><html:select property="isSpareNewEquipment" styleClass="content" >
							<html:option value="">--Select--</html:option>
							<html:option value="True">Yes</html:option>
							<html:option value="False">No</html:option>
							<html:option value="N/A">N/A</html:option>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>PR Number</td>
					<td><html:text property="prNumber" styleClass="content" maxlength="12" style="text-transform:uppercase;"></html:text></td>
					<td>PO Number</td>
					<td><html:text property="poNumber" styleClass="content" maxlength="12" style="text-transform:uppercase;"></html:text></td>
				</tr>

				<tr>
					<td>Utilizing Dept. <font color="red">*</font></td>
					<td><html:select  property="utilizingDept" styleClass="content">
							<html:option value="">Select</html:option>
							<html:options name="materialsForm" property="deptId" labelProperty="deptName"/>
						</html:select>
					</td>

				
				<html:hidden property="purposeID"/>

				<td>Approximate Value <font color="red">*</font></td>
				<td><html:text property="approximateValue" styleClass="content" style="text-transform:uppercase;"></html:text></td>
			</tr>

			<tr>
				<td>Valuation Class <font color="red">*</font></td>
				<td><html:select property="valuationClass" styleClass="content">
						<html:option value="">--Select--</html:option>
						<html:options name="materialsForm" property="valuationClassID" labelProperty="valuationClassName"/>
					</html:select>
				</td>
				<td>HSN Code<font color="red">*</font></td>
					<td>
						<html:text property="hsnCode" maxlength="8" size="8"> </html:text>
					</td>
			</tr>

			<tr>
				<td>Justification <font color="red">*</font></td>
				<td>
					<html:textarea property="detailedJustification" cols="40" rows="5" styleClass="content" style="background-color:#f6f6f6;border:#38abff 1px solid;text-transform:uppercase;" ></html:textarea>
				</td>
				<td>Specification <font color="red">*</font></td>
				<td>
					<html:textarea property="detailedSpecification" cols="40" rows="5" styleClass="content" style="background-color:#f6f6f6;border:#38abff 1px solid;text-transform:uppercase;"></html:textarea>
				</td>
			</tr>
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
				
				
			
			<td>SAP Code No<font color="red">*</font></td>
				<td>	<html:text property="sapCodeNo" styleClass="text_field" style="width:200px;"></html:text></td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<html:text property="sapCreationDate" styleId="sapCreationDate"  readonly="true" styleClass="text_field"/>
			</td>
		
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
		<tr>
		
		<td colspan="6" style="border:0px; text-align: center;">
		<logic:notEmpty name="save">
	<input type="button" class="rounded" value="Save" onclick="changeStatus()" />&nbsp;&nbsp;
	<input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  />
	</logic:notEmpty>
	</td>
			
			</tr>		
			</table>	
			</div>
		<logic:empty name="save">
		<center>
		<input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  />
		</center>
		</logic:empty>	
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
	
		
	<html:hidden property="reqRequstType"/>
	<html:hidden property="userRole"/>
	<html:hidden property="requestNumber"/>
	
	<html:hidden property="materialTypeId"/>
	<html:hidden property="reqMaterialGroup"/>
	<html:hidden property="locationId"/>
				<html:hidden property="materialTypeId"/>


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
			</html>
