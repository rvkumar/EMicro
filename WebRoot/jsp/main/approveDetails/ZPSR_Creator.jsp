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
	
	/* if(document.forms[0].locationId.value=="")
	    {
	      alert("Please Select Location");
	      document.forms[0].locationId.focus();
	      return false;
	    } */
	    
	   /*   if(document.forms[0].materialTypeId.value=="")
	    {
	      alert("Please Select Material Type");
	      document.forms[0].materialTypeId.focus();
	      return false;
	    } */
	    
	      if(document.forms[0].materialShortName.value=="")
	    {
	      alert("Please Enter Material Short Name");
	      document.forms[0].materialShortName.focus();
	      return false;
	    }
	    var st = document.forms[0].materialShortName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].materialShortName.value=st;
	
	
	      if(document.forms[0].materialLongName.value=="")
	    {
	      alert("Please Enter Material Long Name");
	      document.forms[0].materialLongName.focus();
	      return false;
	    }
	    
	    if(document.forms[0].materialLongName.value!="")
	    {
	     var a=document.forms[0].materialLongName.value;
	     a=a.length;
	     if(a>80){
	      alert("Material Long Name Should be less than 80 characters");
	      document.forms[0].materialLongName.focus();
	      return false;
	    }
	        var st = document.forms[0].materialLongName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].materialLongName.value=st;
	}
	
if(document.forms[0].storageLocationId.value=="")
	    {
	      alert("Please Select Storage Location");
	      document.forms[0].storageLocationId.focus();
	      return false;
	    }
	      if(document.forms[0].materialGroupId.value=="")
	    {
	      alert("Please Select Material Group");
	      document.forms[0].materialGroupId.focus();
	      return false;
	    }
	     
	      if(document.forms[0].unitOfMeasId.value=="")
	    {
	      alert("Please Select Unit of Measurement");
	      document.forms[0].unitOfMeasId.focus();
	      return false;
	    }
	     if(document.forms[0].puchaseGroupId.value=="")
	    {
	      alert("Please Select Purchase Group");
	      document.forms[0].puchaseGroupId.focus();
	      return false;
	    }
	     
	    
	      
	    /*   if(document.forms[0].isEquipment.value=="")
	    {
	      alert("Please Enter Is it a New Equipment / Machine");
	      document.forms[0].isEquipment.focus();
	      return false;
	    } */
	     var st = document.forms[0].equipmentName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].equipmentName.value=st;
	
	   var st = document.forms[0].equipmentMake.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].equipmentMake.value=st;
	
	
	    
	/*     if(document.forms[0].isEquipment.value=="True")
	    {
	   
		      if(document.forms[0].equipmentName.value=="")
		    {
		      alert("Please Enter Equipment Name");
		      document.forms[0].equipmentName.focus();
		      return false;
		    }
    var st = document.forms[0].equipmentName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].equipmentName.value=st;
	
	
		    if(document.forms[0].equipmentMake.value=="")
		    {
		      alert("Please Enter Equipment Make");
		      document.forms[0].equipmentMake.focus();
		      return false;
		    }
   var st = document.forms[0].equipmentMake.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].equipmentMake.value=st;
	    }
	    
	      if(document.forms[0].isSpare.value=="")
	    {
	      alert("Please Enter Is Spare");
	      document.forms[0].isSpare.focus();
	      return false;
	    } */
	    
	     var st = document.forms[0].componentMake.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].componentMake.value=st;
	
	var st = document.forms[0].oemPartNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].oemPartNo.value=st;
	
	
	/* 
	    if(document.forms[0].isSpare.value=="True")
	    {
	
		      if(document.forms[0].componentMake.value=="")
		    {
		      alert("Please Enter Component Make");
		      document.forms[0].componentMake.focus();
		      return false;
		    }
		      var st = document.forms[0].componentMake.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].componentMake.value=st;
	
	
		    if(document.forms[0].oemPartNo.value=="")
		    {
		      alert("Please Enter OEM Part No");
		      document.forms[0].oemPartNo.focus();
		      return false;
		    }
		    
		    var st = document.forms[0].oemPartNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].oemPartNo.value=st;
	     } */
	     
	          if(document.forms[0].moc.value!="")
	    {
	    var st = document.forms[0].moc.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].moc.value=st;
	    }
	        if(document.forms[0].rating.value!="")
	    {
	    var st = document.forms[0].rating.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].rating.value=st;
	    
	    }
	        if(document.forms[0].range.value!="")
	    {
	    var st = document.forms[0].range.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].range.value=st;
	    
	    }
	     
	     
	    /*  if(document.forms[0].isItNewFurniture.value=="")
	    {
	      alert("Please Enter Is it New Furniture /Doors/Window");
	      document.forms[0].isItNewFurniture.focus();
	      return false;
	    }
	     if(document.forms[0].isItFacility.value=="")
	    {
	      alert("Please Enter Is it for New Facility / Expanstion Area");
	      document.forms[0].isItFacility.focus();
	      return false;
	    }
	     if(document.forms[0].isSpareNewEquipment.value=="")
	    {
	      alert("Please Enter Is it for New Facility / Expanstion Area");
	      document.forms[0].isSpareNewEquipment.focus();
	      return false;
	    } */
	    
	    
	     
	    
	    if(document.forms[0].utilizingDept.value=="")
	    {
	      alert("Please Enter Utilizing Dept");
	      document.forms[0].utilizingDept.focus();
	      return false;
	    }
	   
	     if(document.forms[0].approximateValue.value=="")
	    {
	      alert("Please Enter Approximate Value");
	      document.forms[0].approximateValue.focus();
	      return false;
	    }
	   
 		var st = document.forms[0].approximateValue.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].approximateValue.value=st
	
	
	      if(document.forms[0].valuationClass.value=="")
	    {
	      alert("Please Enter Valuation Class");
	      document.forms[0].valuationClass.focus();
	      return false;
	    }
	    if(document.forms[0].detailedJustification.value=="")
	    {
	      alert("Please Enter Detailed Justification");
	      document.forms[0].detailedJustification.focus();
	      return false;
	    }
	      var st = document.forms[0].detailedJustification.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].detailedJustification.value=st
	
	    if(document.forms[0].detailedSpecification.value=="")
	    {
	      alert("Please Enter Detailed Specification");
	      document.forms[0].detailedSpecification.focus();
	      return false;
	    }
	   var st = document.forms[0].detailedSpecification.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].detailedSpecification.value=st
	
	
	    if(document.forms[0].prNumber.value!=""){
	     var prNumber = document.forms[0].prNumber.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(prNumber)) {
             alert("PR Number  Should be Integer or Float!");
                 	      document.forms[0].prNumber.focus();
             
             
            return false;
        }
        }
         if(document.forms[0].poNumber.value!=""){
	     var poNumber = document.forms[0].poNumber.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(poNumber)) {
             alert("PO Number  Should be Integer or Float!");
              document.forms[0].poNumber.focus();
             
            return false;
        }
        }
	
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
	  var st = document.forms[0].equipIntendedFor.value;
	 	var Re = new RegExp("\\'","g");
	 	st = st.replace(Re,"`");
	 	document.forms[0].equipIntendedFor.value=st;
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
	<html:form action="/approvals.do" enctype="multipart/form-data">
	<div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>

			<table class="bordered" width="90%">
			<tr>
					<th colspan="8" style="text-align: center;"><big>ZPSR-General Material Form</big></th>
				</tr>
   				<tr>
	 				<th colspan="4"><big>Basic Details Of Material</big></th>
   				</tr>

				<tr>
	 				<td>Request No <font color="red">*</font></td>
					<td><html:text property="requestNo" styleClass="content" readonly="true" style="background-color:#d3d3d3;"/>
						<html:hidden property="typeDetails"/>
					</td>
					<td>Request&nbsp;Date <font color="red">*</font></td>
					<td>
						<html:text property="requestDate" styleId="requestDate" styleClass="content" readonly="true" style="background-color:#d3d3d3;"/>
					</td>
				</tr>

				<tr>
					<td>Location <font color="red">*</font></td>
					<td colspan="3"><html:select name="approvalsForm" property="locationId" styleClass="content" disabled="true">
							<html:option value="">--Select--</html:option>
							<html:options name="approvalsForm" property="locationIdList" labelProperty="locationLabelList"/>
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
					<td><html:select name="approvalsForm" property="materialGroupId" styleClass="content" disabled="true">
							<html:option value="">--Select--</html:option>
							<html:options name="approvalsForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/></html:select>
					</td>
					<td>Storage&nbsp;Location <font color="red">*</font></td>
					<td><html:select  property="storageLocationId" styleClass="content">
							<html:option value="">--Select--</html:option>
							<html:options name="approvalsForm" property="storageID" labelProperty="storageIDName"/>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>U O M <font color="red">*</font></td>
					<td><html:select property="unitOfMeasId" styleClass="content" >
							<html:option value="">--Select--</html:option>
							<html:options name="approvalsForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues"/>
						</html:select>
					</td>
					<td>Purchase&nbsp;Group <font color="red">*</font></td>
					<td><html:select property="puchaseGroupId" styleClass="text_field" >
							<html:option value="">--Select--</html:option>
							<html:options name="approvalsForm" property="puchaseGroupIdList"	labelProperty="puchaseGroupIdValues"/>
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
					<td ><html:select property="isEquipment" styleClass="content" onclick="checkEquipment()" style="text-transform:uppercase;">
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>
					<td>
					Equip.intended for</td>
			       <td><html:text property="equipIntendedFor" styleClass="content" style="width:380px;text-transform:uppercase;" size="55" maxlength="40" ></html:text></td>
		
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
                <td>Add Size/Dimensions(Dia,Length x width)</td>
				<td><html:text property="dimension"  size="35" style="text-transform:uppercase;width:280px;"/></td>
                <td>Pack Size</td><td >
                <html:text property="packSize" styleClass="content" style="width:230px;text-transform:uppercase;" size="80" maxlength="25"></html:text></td>    </tr>
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
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
							<html:option value="2">N/A</html:option>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>Is it for New Facility / Expansion Area <font color="red">*</font></td>
					<td><html:select property="isItFacility" styleClass="content" >
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
							<html:option value="2">N/A</html:option>
					</html:select></td>
					<td>Is Spare required for New Equipment <font color="red">*</font></td>
					<td><html:select property="isSpareNewEquipment" styleClass="content" >
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
							<html:option value="2">N/A</html:option>
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
							<html:options name="approvalsForm" property="deptId" labelProperty="deptName"/>
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
						<html:options name="approvalsForm" property="valuationClassID" labelProperty="valuationClassName"/>
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
				
				
			
			<td >SAP Code No<font color="red">*</font></td>
				<td>	<html:text property="sapCodeNo" styleClass="text_field" style="width:200px;" maxlength="18"></html:text></td>
			
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
	<html:hidden property="requestNumber"/>
	<html:hidden property="locationId"/>
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
