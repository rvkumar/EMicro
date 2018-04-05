<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
	
		
	if(document.forms[0].locationId.value=="")
	    {
	      alert("Please Select Location");
	      document.forms[0].locationId.focus();
	      return false;
	    }
	    
	     if(document.forms[0].materialTypeId.value=="")
	    {
	      alert("Please Select Material Type Id");
	      document.forms[0].materialTypeId.focus();
	      return false;
	    }
	    if(document.forms[0].storageLocationId.value=="")
	    {
	      alert("Please Select Storage Location ID");
	      document.forms[0].storageLocationId.focus();
	      return false;
	    }
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
	      if(document.forms[0].materialGroupId.value=="")
	    {
	      alert("Please Select Material Group ");
	      document.forms[0].materialGroupId.focus();
	      return false;
	    }
	    if(document.forms[0].puchaseGroupId.value=="")
	    {
	      alert("Please Select Purchase Group");
	      document.forms[0].puchaseGroupId.focus();
	      return false;
	    }
	      if(document.forms[0].unitOfMeasId.value=="")
	    {
	      alert("Please Select Unit Of Measurement ");
	      document.forms[0].unitOfMeasId.focus();
	      return false;
	    }
	       if(document.forms[0].divisionId.value=="")
	    {
	      alert("Please Select Division ");
	      document.forms[0].divisionId.focus();
	      return false;
	    }
	    
	     if(document.forms[0].isAsset.value=="")
	    {
	      alert("Please Select  Is Asset");
	      document.forms[0].isAsset.focus();
	      return false;
	    }
	    if(document.forms[0].purposeID.value=="")
	    {
	      alert("Please Enter Purpose");
	      document.forms[0].purposeID.focus();
	      return false;
	    }
	      if(document.forms[0].isSASFormAvailable.value=="")
	    {
	      alert("Please Select Is SAS Form Available");
	      document.forms[0].isSASFormAvailable.focus();
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
	document.forms[0].approximateValue.value=st;
	
	
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
	document.forms[0].detailedJustification.value=st;
	
	
  if(document.forms[0].detailedSpecification.value=="")
	    {
	      alert("Please Enter Detailed Specification");
	      document.forms[0].detailedSpecification.focus();
	      return false;
	    }
	  	     var st = document.forms[0].detailedSpecification.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].detailedSpecification.value=st;
	
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
	var reqType = "ZPPC";
	var matGroup=document.forms[0].materialGroupId.value;
	var location=document.forms[0].locationId.value;
	var url="approvals.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
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
<table class="bordered">
				<tr>
					<th colspan="4" align="center"><big><center>ZPPC-Promotional Material</center> </big></th>
				</tr>
				<tr>
				
					<th colspan="4"><big>Basic Details Of Material</big></th>
   				</tr>
				<tr>
				<tr>	  
	 				<td>Request No<font color="red">*</font></td>
					<td align="left">
						<html:text property="requestNumber" readonly="true" maxlength="5" />
					</td>
					<td>Request Date <font color="red">*</font></td>
					<td align="left">
						<html:text property="requestDate" styleId="requestDate" readonly="true" style="background-color:#d3d3d3;"/>
					</td>
				</tr>
				<tr>
				<td>Location <font color="red">*</font></td>
				<td><html:select name="approvalsForm" property="locationId">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="locationIdList" labelProperty="locationLabelList"/>
					</html:select>
				</td>

<%--				<td>Material Type <font color="red">*</font></td>--%>
<%--				<td><html:select  property="materialTypeId" disabled="true">--%>
<%--					<html:option value="">Select</html:option>--%>
<%--					<html:options name="promotionalForm" property="materTypeIDList" --%>
<%--									labelProperty="materialTypeIdValueList"/>--%>
<%--					</html:select>--%>
<%--				</td>--%>
			<html:hidden property="materialTypeId"/>
				<td>Storage&nbsp;Location <font color="red">*</font></td>
				<td><html:select  property="storageLocationId" styleClass="text_field" style="width:200px;">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
				</td>
			</tr>
			
			<tr>
				<td>Short Name <font color="red">*</font></td>
				<td colspan="3"><html:text property="materialShortName" maxlength="40" size="60" title="Maximum of 40 characters" style="text-transform:uppercase;"></html:text></td>
			</tr>
			
			<tr>
				<td>Long Name <font color="red">*</font></td>
				<td colspan="3" ><html:text property="materialLongName" maxlength="80" size="60" title="Maximum of 80 characters" style="text-transform:uppercase"></html:text>
			</tr>
			
			<tr>
				<td>Material Group <font color="red">*</font></td>
				<td><html:select name="approvalsForm" property="materialGroupId">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
					</html:select>
				</td>
				<td>Purchase&nbsp;Group <font color="red">*</font></td>
				<td><html:select property="puchaseGroupId">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="puchaseGroupIdList" labelProperty="puchaseGroupIdValues"/>
					</html:select>
				</td>
			</tr>
		<tr>
				<td>Brand <font color="red">*</font></td>
				<td colspan="3">
				
					<html:select  property="brandID" >	
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="brandIDList" labelProperty="brandNameList"/>
					</html:select>
					
					
				</td>
			
			</tr>
			<tr>
				<td>U O M <font color="red">*</font></td>
				<td ><html:select property="unitOfMeasId" >
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues"/>
					</html:select>
				</td>
				<td>Division <font color="red">*</font></td>
				<td><html:select property="divisionId">	
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="divisonID" labelProperty="divisonName"/>
					</html:select>
				</td>
			</tr>

			<tr>
	  			<th colspan="4"><big>Other Details</big></th>
   			</tr>
		   <tr>
		   <td>
		   Is Asset <font color="red">*</font>
		   </td>
		   <td>
		  <html:select property="isAsset">
						<html:option value="">--Select--</html:option>
					<html:option value="1">Yes</html:option>
					<html:option value="0">No</html:option>
					</html:select>
					</td>
				<td>Purpose <font color="red">*</font></td>
				<td><html:select property="purposeID">
						<html:option value="">--Select--</html:option>
						<html:option value="1">Gift & Compliments</html:option>
				<html:option value="2">Propaganda & Promotional (KUDLU DEPOT)</html:option>
				<html:option value="3">Product Launch Exp</html:option>
				<html:option value="4">Sales Promotional (SAS)</html:option>
				<html:option value="5">Visual Ads,Literature</html:option>
				<html:option value="6">Conference,National & Regional </html:option>
				<html:option value="7">Incentive to Field Staff</html:option>
				<html:option value="8">Incentive to Stockist/Chemist</html:option>
				<html:option value="9">Travelling Lodging & Boarding Exp</html:option>
					</html:select>
					</td>
					<tr>
				<td>
				Is&nbsp;SAS&nbsp;Form&nbsp;Available <font color="red">*</font>
				</td>
				<td>
				<html:select property="isSASFormAvailable" styleClass="text_field" >
					<html:option value="">--Select--</html:option>
					<html:option value="1">Yes</html:option>
					<html:option value="0">No</html:option>
					</html:select>
				</td>
					<td>HSN Code<font color="red">*</font></td>
					<td>
						<html:text property="hsnCode" maxlength="8" size="8"> </html:text>
					</td>
				
			</tr>

			<tr>
				<td>Approximate Value <font color="red">*</font></td>
				<td><html:text property="approximateValue" style="text-transform:uppercase;"></html:text></td>
				<td>Valuation&nbsp;Class <font color="red">*</font></td>
				<td><html:select property="valuationClass">
					<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="valuationClassID" labelProperty="valuationClassName"/>
					</html:select>
				</td>
			</tr>

				
			<tr>
				<td>Justification <font color="red">*</font></td>
				<td><html:textarea property="detailedJustification" cols="35" rows="5" styleClass="content" style="text-transform:uppercase;"></html:textarea></td>
				<td>Specification <font color="red">*</font></td>
				<td><html:textarea property="detailedSpecification" cols="35" rows="5" styleClass="content" style="text-transform:uppercase;"></html:textarea></td>
			</tr>
			
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
			