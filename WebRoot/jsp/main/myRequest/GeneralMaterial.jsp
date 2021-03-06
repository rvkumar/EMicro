<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>General Material</title>

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

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

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
	  
function setMaterialTypeId(materialTypeID){ 
	  
	
	 	var url="finishedProduct.do?method=getMaterialTypeid&materialTypeID="+materialTypeID;
			document.forms[0].action=url;
			document.forms[0].submit();
	  
	  }
function getMatrialList(materialList)
{
var materilaType=materialList;
if(materilaType=='1')
{
var url="rawMaterial.do?method=displayNewMaterialCodeMaster";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='2')
{
var url="packageMaterial.do?method=displayNewPackageMaterial";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='3')
{
var url="semifinished.do?method=displayNewSemiFinished";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='4'||materilaType=='5')
{
var url="finishedProduct.do?method=displayNewFinishedProduct&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}


if(materilaType=='12')
{
var url="promotional.do?method=displayNewPromotional";
			document.forms[0].action=url;
			document.forms[0].submit();	
}

if(materilaType=='13')
{
var url="zpsr.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='10')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='7')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='8')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='14')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='9')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='11')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='14')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
}
function moveMaterialCode(){


var saveType=document.forms[0].typeDetails.value;
if(saveType=="Save")
{
if(document.forms[0].locationId.value!=""||document.forms[0].storageLocationId.value!=""||document.forms[0].materialShortName.value!=""||document.forms[0].materialLongName.value!=""||
document.forms[0].materialGroupId.value!=""||document.forms[0].puchaseGroupId.value!=""||document.forms[0].unitOfMeasId.value!=""||document.forms[0].materialUsedIn.value!=""||
document.forms[0].isEquipment.value!=""||document.forms[0].isSpare.value!=""||document.forms[0].isNew.value!=""||document.forms[0].equipmentName.value!=""||document.forms[0].prNumber.value!=""||
document.forms[0].poNumber.value!=""||document.forms[0].isAsset.value!=""||document.forms[0].utilizingDept.value!=""||document.forms[0].approximateValue.value!=""||document.forms[0].valuationClass.value!=""||
document.forms[0].detailedJustification.value!=""||document.forms[0].detailedSpecification.value!="")
{
var agree = confirm('Material code not saved.Are you sure open new material code form');
if(agree)
{



document.getElementById("t1").style.visibility="hidden";
document.getElementById("t1").style.height="0px";
document.getElementById("t2").style.visibility="visible";
document.getElementById("t2").style.height="20px";
document.getElementById("materialTable").style.visibility="hidden";
document.forms[0].materialCodeLists.value="";

}
else
{

}
}else{
document.getElementById("t1").style.visibility="hidden";
document.getElementById("t1").style.height="0px";
document.getElementById("t2").style.visibility="visible";
document.getElementById("t2").style.height="20px";
document.getElementById("materialTable").style.visibility="hidden";
document.forms[0].materialCodeLists.value="";
}


	 }
	 if(saveType=="Update")
{

document.getElementById("t1").style.visibility="hidden";
document.getElementById("t1").style.height="0px";
document.getElementById("t2").style.visibility="visible";
document.getElementById("t2").style.height="20px";
document.getElementById("materialTable").style.visibility="hidden";
document.forms[0].materialCodeLists.value="";

}
}




function saveData(){	
if(document.forms[0].locationId.value=="")
	    {
	      alert("Please Select Location");
	      document.forms[0].locationId.focus();
	      return false;
	    }
	    
	     if(document.forms[0].materialTypeId.value=="")
	    {
	      alert("Please Select Material Type");
	      document.forms[0].materialTypeId.focus();
	      return false;
	    }
	    if(document.forms[0].storageLocationId.value=="")
	    {
	      alert("Please Select Storage Location");
	      document.forms[0].storageLocationId.focus();
	      return false;
	    }
	      if(document.forms[0].materialShortName.value=="")
	    {
	      alert("Please Enter Material Short Name");
	      document.forms[0].materialShortName.focus();
	      return false;
	    }
	    var materialShortName=document.forms[0].materialShortName.value;
         
         var splChars = "'";
for (var i = 0; i < materialShortName.length; i++) {
    if (splChars.indexOf(materialShortName.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Material Short Name!"); 
     document.forms[0].materialShortName.focus();
 return false;
}
}
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
	    
	    }
	    var materialLongName=document.forms[0].materialLongName.value;
	    for (var i = 0; i < materialLongName.length; i++) {
    if (splChars.indexOf(materialLongName.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Material Long Name!"); 
     document.forms[0].materialLongName.focus();
 return false;
}
}
	      if(document.forms[0].materialGroupId.value=="")
	    {
	      alert("Please Select Material Group");
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
	      alert("Please Select U.O.M");
	      document.forms[0].unitOfMeasId.focus();
	      return false;
	    }
	     
	    /*
	      if(document.forms[0].materialUsedIn.value=="")
	    {
	      alert("Please Enter Material Used In");
	      document.forms[0].materialUsedIn.focus();
	      return false;
	    }
	      if(document.forms[0].isEquipment.value=="")
	    {
	      alert("Please Enter Is Equipment");
	      document.forms[0].isEquipment.focus();
	      return false;
	    }
	      if(document.forms[0].isSpare.value=="")
	    {
	      alert("Please Enter Is Spare");
	      document.forms[0].isSpare.focus();
	      return false;
	    }
	     if(document.forms[0].isNew.value=="")
	    {
	      alert("Please Enter Is New");
	      document.forms[0].isNew.focus();
	      return false;
	    }
	     if(document.forms[0].equipmentName.value=="")
	    {
	      alert("Please Enter Equipment Name");
	      document.forms[0].equipmentName.focus();
	      return false;
	    }
	     if(document.forms[0].prNumber.value=="")
	    {
	      alert("Please Enter PR Number");
	      document.forms[0].prNumber.focus();
	      return false;
	    }
	     if(document.forms[0].poNumber.value=="")
	    {
	      alert("Please Enter PO Number");
	      document.forms[0].poNumber.focus();
	      return false;
	    }
	    */
	    if(document.forms[0].isAsset.value=="")
	    {
	      alert("Please Select  Is Asset");
	      document.forms[0].isAsset.focus();
	      return false;
	    }
	    if(document.forms[0].utilizingDept.value=="")
	    {
	      alert("Please Enter Utilizing Dept");
	      document.forms[0].utilizingDept.focus();
	      return false;
	    }
	    
	    if(document.forms[0].materialTypeId.value=="12")
	    {
		    if(document.forms[0].purposeID.value=="")
		    {
		      alert("Please Enter Purpose");
		      document.forms[0].purposeID.focus();
		      return false;
		    }
	    }
	    
	     if(document.forms[0].approximateValue.value=="")
	    {
	      alert("Please Enter Approximate Value");
	      document.forms[0].approximateValue.focus();
	      return false;
	    }
	     var approximateValue = document.forms[0].approximateValue.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(approximateValue)) {
             alert("Approximate Value  Should be Integer or Float!");
            return false;
        }
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
	    var detailedJustification=document.forms[0].detailedJustification.value;
	    for (var i = 0; i < detailedJustification.length; i++) {
    if (splChars.indexOf(detailedJustification.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Detailed Justification!"); 
    	      document.forms[0].detailedJustification.focus();
 return false;
}
}
	    if(document.forms[0].detailedSpecification.value=="")
	    {
	      alert("Please Enter Detailed Specification");
	      document.forms[0].detailedSpecification.focus();
	      return false;
	    }
	    var detailedSpecification=document.forms[0].detailedSpecification.value;
	    for (var i = 0; i < detailedSpecification.length; i++) {
    if (splChars.indexOf(detailedSpecification.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Detailed Specification!"); 
    document.forms[0].detailedSpecification.focus();
 return false;
}
}
	  /*    if(document.forms[0].sapCodeNo.value=="")
	    {
	      alert("Please Enter SAP Code No");
	      document.forms[0].sapCodeNo.focus();
	      return false;
	    }
	      
	     if(document.forms[0].sapCodeExists.value=="")
	    {
	      alert("Please Select SAP Code Exists");
	      document.forms[0].sapCodeExists.focus();
	      return false;
	    }
	     if(document.forms[0].sapCreationDate.value=="")
	    {
	      alert("Please Enter SAP Creation Date");
	      document.forms[0].sapCreationDate.focus();
	      return false;
	    }
	     if(document.forms[0].sapCreatedBy.value=="")
	    {
	      alert("Please Enter SAP Created By");
	      document.forms[0].sapCreatedBy.focus();
	      return false;
	    }
	    if(document.forms[0].requestedBy.value=="")
	    {
	      alert("Please Enter Requested By");-
	      document.forms[0].requestedBy.focus();
	      return false;
	    }   
	    */
			var url="generalMaterial.do?method=saveGeneralMaterial";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}



function onUpload(){	   
		
			var url="finishedProduct.do?method=uploadFiles";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}




function onDeleteFile(){
	
	var rows=document.getElementsByName("checkedfiles");

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
alert('please select atleast one value to delete');
}
else
{
var agree = confirm('Are You Sure To Delete Selected file');
if(agree)
{
	document.forms[0].action="finishedProduct.do?method=deleteFileListModify&cValues="+checkvalues+"&unValues="+uncheckvalues;
document.forms[0].submit();
}
}
}

function makePurposeMandatory(){
var a=document.forms[0].materialTypeId.value;

if(a=="12"){

document.getElementById("purpose").style.visibility="visible";
}	
	
if(a=!"12"){
document.getElementById("purpose").style.visibility="hidden";
}


}

function closeData(){
var url="materialCode.do?method=displayMaterialList";
			document.forms[0].action=url;
			document.forms[0].submit();
}
</script>
</head>

<body onload="makePurposeMandatory()" style="text-transform:uppercase;">
   <%
		String menuIcon=(String)request.getAttribute("MenuIcon");
			if(menuIcon==null){
			menuIcon="";
			}
	%>
	<% 
  		UserInfo user=(UserInfo)session.getAttribute("user");
	%>

	<div class="middel-blocks">

	<html:form action="/generalMaterial.do" enctype="multipart/form-data">
		 <logic:iterate id="generalMaterialForm" name="gendetails">
				
			

			<div id="materialTable" style="visibility: visible;">
			<table class="bordered">
			<tr>
					<th colspan="8" style="text-align: center;"><big>Material Code Request Form</big></th>
				</tr>
				<tr>
					<th colspan="8"><big>Basic Details Of Material</big></th>
   				</tr>
			<tr>
	 			<td>Request No <font color="red">*</font></td>
				<td>
												<bean:write name="generalMaterialForm" property="requestNo"/>

				</td>
				<td>Request Date <font color="red">*</font></td>
				<td>
												<bean:write name="generalMaterialForm" property="requestDate"/>
				</td>
			</tr>

			<tr>
				<td>Location <font color="red">*</font></td>
				<td>
																<bean:write name="generalMaterialForm" property="locationId"/>
				
				</td>


            
				<td>Storage&nbsp;Location <font color="red">*</font></td>
				<td>															
					<bean:write name="generalMaterialForm" property="storageLocationId"/>

				</td>
			</tr>

			<tr>
				<td>Short Name <font color="red">*</font></td>
				<td colspan="3">
					<bean:write name="generalMaterialForm" property="materialShortName"/>
				</td>
			</tr>

			<tr>
				<td>Long Name <font color="red">*</font></td>
				<td colspan="3" >
					<bean:write name="generalMaterialForm" property="materialLongName"/>
				</td>
			</tr>
			<tr>
				<td>Material&nbsp;Group <font color="red">*</font></td>
				<td>					
				<bean:write name="generalMaterialForm" property="materialGroupId"/>

				</td>
				<td>Purchase Group <font color="red">*</font></td>
				<td>			
					<bean:write name="generalMaterialForm" property="puchaseGroupId"/>

				</td>
			</tr>
			<tr>
				<td>U O M <font color="red">*</font></td>
				<td colspan="3">
									<bean:write name="generalMaterialForm" property="unitOfMeasId"/>

				</td>
			</tr>

			<tr>
				<th colspan="8"><big>Other Details</big></th>
   			</tr>

											

			
			<tr>
				<td>
		   Is Asset <font color="red">*</font>
		   </td>
		   <td>
		  									<bean:write name="generalMaterialForm" property="isAsset"/>
		  
					</td>
				<td>Utilizing Dept <font color="red">*</font></td>
				<td>		  									
				<bean:write name="generalMaterialForm" property="utilizingDept"/>

				</td>
		
		
			<tr>
		
				<td>Approximate&nbsp;Value <font color="red">*</font></td>
				<td>
							<bean:write name="generalMaterialForm" property="approximateValue"/>

				</td>
				<td>Valuation Class <font color="red">*</font></td>
				<td>						
					<bean:write name="generalMaterialForm" property="valuationClass"/>

				</td>
			</tr>

			<tr>
				<td>Justification <font color="red">*</font></td>
				<td>
					<bean:write name="generalMaterialForm" property="detailedJustification"/>
				</td>
				<td>Specification <font color="red">*</font></td>
				<td>
					<bean:write name="generalMaterialForm" property="detailedSpecification"/>
				</td>
			</tr>

			<th colspan="6">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<img src="images/star.gif" width="8" height="8" /></td>
				<td>
				<bean:write name="generalMaterialForm" property="sapCodeExists"/>
				</td>
		
			<td >SAP Code No<img src="images/star.gif" width="8" height="8" /></td>
				<td colspan="4"><bean:write name="generalMaterialForm" property="sapCodeNo"/>	</td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<bean:write name="generalMaterialForm" property="sapCreationDate"/>
				
			</td>
			
				<td >Code Created By<img src="images/star.gif" width="8" height="8" /></td><td colspan="4">
				<bean:write name="generalMaterialForm" property="sapCreatedBy"/>
			
				</td>
			</tr>
	               
	               <tr>
						<th colspan="8">Comments</th>
					</tr>	
					
					<tr>
					<td colspan="8">
					
					</td>
					</tr>	
					
					<tr>
						<td colspan="8">
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
			