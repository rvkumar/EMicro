<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
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

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript"><!--

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
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


function getIsEligibleTds(){



if(document.forms[0].elgTds.value=="True"){
document.getElementById("mandatoryEligibleTds").style.visibility="visible";

}
if(document.forms[0].elgTds.value=="False"){

document.getElementById("mandatoryEligibleTds").style.visibility="hidden";

}
if(document.forms[0].elgTds.value==""){

document.getElementById("mandatoryEligibleTds").style.visibility="hidden";

}

}



function getRegisteredExciseVendor(){
var a=document.forms[0].regExciseVendor.value;

if(document.forms[0].regExciseVendor.value=="True"){
document.getElementById("mandatoryRegisteredExciseVendor1").style.visibility="visible";
document.getElementById("mandatoryRegisteredExciseVendor2").style.visibility="visible";
document.getElementById("mandatoryRegisteredExciseVendor3").style.visibility="visible";
document.getElementById("mandatoryRegisteredExciseVendor4").style.visibility="visible";
document.getElementById("mandatoryRegisteredExciseVendor5").style.visibility="visible";
document.getElementById("mandatoryRegisteredExciseVendor6").style.visibility="visible";
}
if(document.forms[0].regExciseVendor.value=="False"){

document.getElementById("mandatoryRegisteredExciseVendor1").style.visibility="hidden";
document.getElementById("mandatoryRegisteredExciseVendor2").style.visibility="hidden";
document.getElementById("mandatoryRegisteredExciseVendor3").style.visibility="hidden";
document.getElementById("mandatoryRegisteredExciseVendor4").style.visibility="hidden";
document.getElementById("mandatoryRegisteredExciseVendor5").style.visibility="hidden";
document.getElementById("mandatoryRegisteredExciseVendor6").style.visibility="hidden";


}
if(document.forms[0].regExciseVendor.value==""){

document.getElementById("mandatoryRegisteredExciseVendor1").style.visibility="hidden";
document.getElementById("mandatoryRegisteredExciseVendor2").style.visibility="hidden";
document.getElementById("mandatoryRegisteredExciseVendor3").style.visibility="hidden";
document.getElementById("mandatoryRegisteredExciseVendor4").style.visibility="hidden";
document.getElementById("mandatoryRegisteredExciseVendor5").style.visibility="hidden";
document.getElementById("mandatoryRegisteredExciseVendor6").style.visibility="hidden";

}

}

function onUploadDocuments(){
		
		if(document.forms[0].vendorAttachments.value=="")
	    {
	      alert("Please Select File To Upload");
	      document.forms[0].vendorAttachments.focus();
	      return false;
	    }
	
	
	var url="vendorMasterRequest.do?method=uploadDocuments";
	document.forms[0].action=url;
	document.forms[0].submit();
}


function onSubmit(){


		if(document.forms[0].requestNo.value=="")
	    {
	      alert("Please Enter Request No");
	      document.forms[0].requestNo.focus();
	      return false;
	    }


		if(document.forms[0].requestDate.value=="")
	    {
	      alert("Please Select Request Date");
	      document.forms[0].requestDate.focus();
	      return false;
	    }
	    
	    
	  
	    
	    if(document.forms[0].accountGroupId.value=="")
	    {
	      alert("Please Select Account Group");
	      document.forms[0].accountGroupId.focus();
	      return false;
	    }
	    
	  
	    if(document.forms[0].purchaseView.checked==false && document.forms[0].accountView.checked==false)
	    {
	      alert("Please Select Atleast One View Type");
	      document.forms[0].purchaseView.focus();
	      
	      return false;
	    }
	    
	      if(document.forms[0].typeOfVendor.value=="")
			    {
			      alert("Please Select Type Of Vendor");
			      document.forms[0].typeOfVendor.focus();
			      return false;
			    }
			    if(document.forms[0].title.value=="")
			    {
			      alert("Please Select Title");
			      document.forms[0].title.focus();
			      return false;
			    }
			    
			    
	    if(document.forms[0].name.value=="")
	    {
	      alert("Please Enter Name");
	      document.forms[0].name.focus();
	      return false;
	    }
	    var name=document.forms[0].name.value;
         var splChars = "'";
for (var i = 0; i < name.length; i++) {
    if (splChars.indexOf(name.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Name!"); 
     document.forms[0].name.focus();
 return false;
}
}
  		if(document.forms[0].address1.value=="")
	    {
	      alert("Please Enter Address1");
	      document.forms[0].address1.focus();
	      return false;
	    }
	        var address1=document.forms[0].address1.value;
         var splChars = "'";
for (var i = 0; i < address1.length; i++) {
    if (splChars.indexOf(address1.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Address 1!"); 
      document.forms[0].address1.focus();
 return false;
}
}

if(document.forms[0].address2.value!="")
	    {
	       var address2=document.forms[0].address2.value;
         var splChars = "'";
for (var i = 0; i < address2.length; i++) {
    if (splChars.indexOf(address2.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  Address 2!"); 
      document.forms[0].address2.focus();
      return false;
    }
   }
	    }
	    if(document.forms[0].address3.value!="")
	    {
	       var address3=document.forms[0].address3.value;
         var splChars = "'";
for (var i = 0; i < address3.length; i++) {
    if (splChars.indexOf(address3.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  Address 3!"); 
      document.forms[0].address3.focus();
      return false;
    }
   }
	    }
	     if(document.forms[0].address4.value!="")
	    {
	       var address4=document.forms[0].address4.value;
         var splChars = "'";
for (var i = 0; i < address4.length; i++) {
    if (splChars.indexOf(address4.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  Address 4!"); 
      document.forms[0].address4.focus();
      return false;
    }
   }
	    }

	    
	    if(document.forms[0].city.value=="")
	    {
	      alert("Please Enter City");
	      document.forms[0].city.focus();
	      return false;
	    }
	      if(document.forms[0].city.value!="")
	    {
	       var city=document.forms[0].city.value;
         var splChars = "'";
for (var i = 0; i < city.length; i++) {
    if (splChars.indexOf(city.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  City !"); 
      document.forms[0].city.focus();
      return false;
    }
   }
	    }
	    if(document.forms[0].pinCode.value=="")
	    {
	      alert("Please Enter Pin Code");
	      document.forms[0].pinCode.focus();
	      return false;
	    }
	    
	    var pinCode = document.forms[0].pinCode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(pinCode)) {
             alert("PinCode  Should be Number ");
                document.forms[0].pinCode.focus();
            return false;
        }
	    if(document.forms[0].country.value=="")
	    {
	      alert("Please Select Atleast One Country");
	      document.forms[0].country.focus();
	      return false;
	    }
	    if(document.forms[0].country.value!="")
	    {
		    if(document.forms[0].state.value=="")
		    {
		      alert("Please Select Atleast One State");
		      document.forms[0].state.focus();
		      return false;
		    }
	    }
	    
	    if(document.forms[0].landLineNo.value!="")
	    {
	     var landLineNo = document.forms[0].landLineNo.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(landLineNo)) {
             alert("Landline Number Should be Number ");
                document.forms[0].landLineNo.focus();
            return false;
       	 }
	    }
	     if(document.forms[0].mobileNo.value!="")
	    {
	     var mobileNo = document.forms[0].mobileNo.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(mobileNo)) {
             alert("Mobile Number Should be Number ");
                document.forms[0].mobileNo.focus();
            return false;
       	 }
	    }
	        if(document.forms[0].faxNo.value!="")
	    {
	     var faxNo = document.forms[0].faxNo.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(faxNo)) {
             alert("Fax Number Should be Number ");
                document.forms[0].faxNo.focus();
            return false;
       	 }
	    }
	    
	     if(document.forms[0].emailId.value!="")
	    {
	       var emailId=document.forms[0].emailId.value;
         var splChars = "'";
for (var i = 0; i < emailId.length; i++) {
    if (splChars.indexOf(emailId.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  emailId !"); 
      document.forms[0].emailId.focus();
      return false;
    }
   }
	    }
	    
	    if(document.forms[0].currencyId.value=="")
	    {
	      alert("Please Select Currency");
	      document.forms[0].currencyId.focus();
	      return false;
	    }
	     if(document.forms[0].reConcillationActId.value=="")
	    {
	      alert("Please Select Reconciliation Account");
	      document.forms[0].reConcillationActId.focus();
	      return false;
	    }
	    
	    if(document.forms[0].elgTds.value=="")
	    {
	    
	      alert("Please Select Is Eligible For Tds");
	      document.forms[0].tdsCode.focus();
	      return false;
	   
	    }
	    if(document.forms[0].elgTds.value=="True")
	    {
	    if(document.forms[0].tdsCode.value=="")
	    {
	      alert("Please Select Tds Code");
	      document.forms[0].tdsCode.focus();
	      return false;
	    }
	    
	    if(document.forms[0].tdsCode.value!="")
	    {
	       var tdsCode=document.forms[0].tdsCode.value;
         var splChars = "'";
for (var i = 0; i < tdsCode.length; i++) {
    if (splChars.indexOf(tdsCode.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  TDS Code  !"); 
      document.forms[0].tdsCode.focus();
      return false;
    }
   }
	    }
	    
	    
	    }
	    
	    if(document.forms[0].tdsCode.value!="")
	    {
	       var tdsCode=document.forms[0].tdsCode.value;
         var splChars = "'";
for (var i = 0; i < tdsCode.length; i++) {
    if (splChars.indexOf(tdsCode.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  TDS Code !"); 
      document.forms[0].tdsCode.focus();
      return false;
    }
   }
	    }
	    
	     if(document.forms[0].lstNo.value!="")
	    {
	       var lstNo=document.forms[0].lstNo.value;
         var splChars = "'";
for (var i = 0; i < lstNo.length; i++) {
    if (splChars.indexOf(lstNo.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  LST No !"); 
      document.forms[0].lstNo.focus();
      return false;
    }
   }
	    }
	    
	    
   
   
	    
	    if(document.forms[0].paymentTermId.value=="")
	    {
	      alert("Please Select Payment Term ");
	      document.forms[0].paymentTermId.focus();
	      return false;
	    }
	     if(document.forms[0].accountGroupId.value!=4){
	  
	   if(document.forms[0].tinNo.value=="")
	    {
	      alert("Please Enter TIN No");
	      document.forms[0].tinNo.focus();
	      return false;
	    }
	    if(document.forms[0].tinNo.value!="")
	    {
	       var tinNo=document.forms[0].tinNo.value;
         var splChars = "'";
for (var i = 0; i < tinNo.length; i++) {
    if (splChars.indexOf(tinNo.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  Tin No !"); 
      document.forms[0].tinNo.focus();
      return false;
    }
   }
   }
   
   if(document.forms[0].cstNo.value=="")
	    {
	      alert("Please Enter CST No");
	      document.forms[0].cstNo.focus();
	      return false;
	    }
   
   if(document.forms[0].cstNo.value!="")
	    {
	       var cstNo=document.forms[0].cstNo.value;
         var splChars = "'";
for (var i = 0; i < cstNo.length; i++) {
    if (splChars.indexOf(cstNo.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  CST No !"); 
      document.forms[0].cstNo.focus();
      return false;
    }
   }
	   } 
	  
	  
	    if(document.forms[0].panNo.value=="")
	    {
	      alert("Please Enter Pan No ");
	      document.forms[0].panNo.focus();
	      return false;
	    }
	    
	   
	   
	       var panNo=document.forms[0].panNo.value;
         var splChars = "'";
for (var i = 0; i < panNo.length; i++) {
    if (splChars.indexOf(panNo.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  Pan No !"); 
      document.forms[0].panNo.focus();
      return false;
    }
   }
   
   
   if(document.forms[0].serviceTaxRegNo.value=="")
	    {
	      alert("Please Enter Service Tax Reg. No ");
	      document.forms[0].serviceTaxRegNo.focus();
	      return false;
	    }
	    
	    if(document.forms[0].serviceTaxRegNo.value!="")
	    {
	     var serviceTaxRegNo=document.forms[0].serviceTaxRegNo.value;
         var splChars = "'";
for (var i = 0; i < serviceTaxRegNo.length; i++) {
    if (splChars.indexOf(serviceTaxRegNo.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in   Service Tax Reg. No !"); 
      document.forms[0].serviceTaxRegNo.focus();
      return false;
    }
   }
	    }
   
   
   }
   
    
	    
	    
	  
	  
		    if(document.forms[0].regExciseVendor.value=="True"){
			    if(document.forms[0].eccNo.value=="")
			    {
			      alert("Please Enter Ecc No");
			      document.forms[0].eccNo.focus();
			      return false;
			    }
			    
			    var eccNo=document.forms[0].eccNo.value;
         var splChars = "'";
		for (var i = 0; i < eccNo.length; i++) {
   		 if (splChars.indexOf(eccNo.charAt(i)) != -1){
   	  	alert ("Please Remove Single Code(') in  Ecc No !"); 
      	document.forms[0].eccNo.focus();
      	return false;
    		}
  		}
			    
			    if(document.forms[0].exciseRegNo.value=="")
			    {
			      alert("Please Enter Exercise Reg No");
			      document.forms[0].exciseRegNo.focus();
			      return false;
			    }
			    
			     var exciseRegNo=document.forms[0].exciseRegNo.value;
         var splChars = "'";
		for (var i = 0; i < exciseRegNo.length; i++) {
   		 if (splChars.indexOf(exciseRegNo.charAt(i)) != -1){
   	  	alert ("Please Remove Single Code(') in  Exercise Reg No !"); 
      	document.forms[0].exciseRegNo.focus();
      	return false;
    		}
  		}
			    
			    if(document.forms[0].exciseRange.value=="")
			    {
			      alert("Please Enter Excise Range");
			      document.forms[0].exciseRange.focus();
			      return false;
			    }
			    
			     var exciseRange=document.forms[0].exciseRange.value;
         var splChars = "'";
		for (var i = 0; i < exciseRange.length; i++) {
   		 if (splChars.indexOf(exciseRange.charAt(i)) != -1){
   	  	alert ("Please Remove Single Code(') in  Excise Range !"); 
      	document.forms[0].exciseRange.focus();
      	return false;
    		}
  		}
			    if(document.forms[0].exciseDivision.value=="")
			    {
			      alert("Please Enter Excise Division");
			      document.forms[0].exciseDivision.focus();
			      return false;
			    }
			     var exciseDivision=document.forms[0].exciseDivision.value;
         var splChars = "'";
		for (var i = 0; i < exciseDivision.length; i++) {
   		 if (splChars.indexOf(exciseDivision.charAt(i)) != -1){
   	  	alert ("Please Remove Single Code(') in  Excise Division !"); 
      	document.forms[0].exciseDivision.focus();
      	return false;
    		}
  		}
			     if(document.forms[0].commissionerate.value=="")
			    {
			      alert("Please Enter Commissionerate");
			      document.forms[0].commissionerate.focus();
			      return false;
			    }
			       var commissionerate=document.forms[0].commissionerate.value;
         var splChars = "'";
		for (var i = 0; i < commissionerate.length; i++) {
   		 if (splChars.indexOf(commissionerate.charAt(i)) != -1){
   	  	alert ("Please Remove Single Code(') in  Commissionerate !"); 
      	document.forms[0].commissionerate.focus();
      	return false;
    		}
  		}
			   
			    
		    }
		    
	    var purchase=document.forms[0].purchaseView.checked;
	   
	    var accountView=document.forms[0].accountView.checked;
	   
	var url="vendorMasterRequest.do?method=submit&purchase="+purchase+"&accountView="+accountView;
	document.forms[0].action=url;
	document.forms[0].submit();
}



function deleteDocumentsSelected()
		{
			var documentChecked=0;
			var documentLength=document.forms[0].documentCheck.length;
			var documentLength1=document.forms[0].documentCheck.checked;
			
			if(documentLength1==true && documentLength==undefined)
			{
			   var agree = confirm('Are You Sure To  Delete Selected Documents');
    		    if(agree)
      		    {
					var URL="vendorMasterRequest.do?method=fileDelete";
					document.forms[0].action=URL;
	 				document.forms[0].submit();
 			    }
 			    else
 			    {
 				  return false;
 			    }
		    }
		    else
		    {
			for(i=0;i<documentLength;i++)
			{
				if(document.forms[0].documentCheck[i].checked==true)
				{
					documentChecked=documentChecked+1;
				}
			}
			
			if(documentChecked==0)
			{
				alert('Select Atleast One Record To Delete Selected Documents');
				return false;
			}
			else
			{
			}
			
			var agree = confirm('Are You Sure To  Delete Selected Documents');
        		if(agree)
        		{
				var URL="vendorMasterRequest.do?method=fileDelete";
				document.forms[0].action=URL;
 				document.forms[0].submit();
 			    }
 			    else
 			    {
 				  return false;
 			    }
		}
  }
  
  function subMenuClicked(id){
	
	var disp=document.getElementById(id);
	
	if(disp.style.display==''){
		disp.style.display='none';
		document.forms[0].divStatus.value='none';
		}
	else{
		disp.style.display=''; 
		document.forms[0].divStatus.value='';
		}
  }



function displayStates(){
		
		var URL="vendorMasterRequest.do?method=displayState";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}
function onClose(){
		
		var URL="vendorMasterRequest.do?method=displayVendorMasterList";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}


function changeTaxDetails(){

var accountGroup=document.forms[0].accountGroupId.value;

if(accountGroup==4)
{
document.getElementById("group1").style.visibility="hidden";
document.getElementById("Service2").style.visibility="hidden";
document.getElementById("cst4").style.visibility="hidden";
document.getElementById("tin3").style.visibility="hidden";

}
if(accountGroup!=4)
{
document.getElementById("group1").style.visibility="visible";
document.getElementById("Service2").style.visibility="visible";
document.getElementById("cst4").style.visibility="visible";
document.getElementById("tin3").style.visibility="visible";
}
if(accountGroup=="")
{
document.getElementById("group1").style.visibility="hidden";
document.getElementById("Service2").style.visibility="hidden";
document.getElementById("cst4").style.visibility="hidden";
document.getElementById("tin3").style.visibility="hidden";
}

}

//-->
</script>

</head>

<body onload="getIsEligibleTds(),getRegisteredExciseVendor(),changeTaxDetails()" style="text-transform:uppercase;">
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
     		
			<html:form action="vendorMasterRequest.do" enctype="multipart/form-data">
            
            <logic:iterate id="vendorMasterRequestForm" name="vendetails">
				
			

			<div id="materialTable" style="visibility: visible;">
			<table class="bordered">
			<tr>
					<th colspan="8" style="text-align: center;"><big>Vendor Master Request Form</big></th>
				</tr>
				<tr>
					<th colspan="8"><big>Basic Details Of Material</big></th>
   				</tr>

			<tr>
				<td>Request No. <font color="red">*</font></td>
				<td>
						<bean:write name="vendorMasterRequestForm" property="requestNo"/>

					
				</td>
				<td>Date <font color="red">*</font></td>
				<td>
						<bean:write name="vendorMasterRequestForm" property="requestDate"/>
				</td>
			</tr>

			<tr>
				<!--<td>Location <font color="red">*</font></td>
				<td>
					<html:select name="vendorMasterRequestForm" property="locationId">
						<html:option value="">--Select--</html:option>
						<html:options name="vendorMasterRequestForm" property="locationIdList" labelProperty="locationLabelList"/>
					</html:select>
				</td>
				-->
				<html:hidden property="locationId"/>
				<td>Account Group <font color="red">*</font></td>
				<td colspan="3">
											<bean:write name="vendorMasterRequestForm" property="accountGroupId"/>

				</td>
			</tr>
							
			<tr>
				<td>View Type <font color="red">*</font></td>
				<td>
		     	<bean:write name="vendorMasterRequestForm" property="purchaseView"/>

				</td>
				<td>Vendor Type <font color="red">*</font></td>
				<td>		     
					<bean:write name="vendorMasterRequestForm" property="typeOfVendor"/>

				</td>
			</tr>

			<tr>
				<td>Title <font color="red">*</font></td>
				<td>
										<bean:write name="vendorMasterRequestForm" property="title"/>

				</td>
				<td>Name <font color="red">*</font></td>
				<td>
				<bean:write name="vendorMasterRequestForm" property="name"/>
</td>
			</tr>
								
			<tr>
				<td>Address1 <font color="red">*</font></td>
				<td>
				<bean:write name="vendorMasterRequestForm" property="address1"/>
				</td>
				<td>Address2</td>
				<td>
				<bean:write name="vendorMasterRequestForm" property="address2"/>
				</td>
			</tr>
								
			<tr>
				<td>Address3</td>
				<td>
				<bean:write name="vendorMasterRequestForm" property="address3"/>
				</td>
				<td>Address4</td>
				<td align="left">
				<bean:write name="vendorMasterRequestForm" property="address4"/>

				</td>
			</tr>

			<tr>
				<td>City <font color="red">*</font></td>
				<td>
					<bean:write name="vendorMasterRequestForm" property="city"/>

				</td>
				<td>Pin Code <font color="red">*</font></td>
				<td>
					<bean:write name="vendorMasterRequestForm" property="pinCode"/>
				</td>
			</tr>

			<tr>
				<td>Country <font color="red">*</font></td>
				<td>
										<bean:write name="vendorMasterRequestForm" property="country"/>

				</td>
				<td>State <logic:notEmpty name="States"><font color="red">*</font></logic:notEmpty></td>
				<td>
													<bean:write name="vendorMasterRequestForm" property="state"/>

				</td>
			</tr>
								
			<tr>
				<td>Landline No.</td>
				<td>
													<bean:write name="vendorMasterRequestForm" property="landLineNo"/>

				<td>Mobile No.</td>
				<td>
													<bean:write name="vendorMasterRequestForm" property="mobileNo"/>

				</td>
			</tr>

			<tr>
				<td>Fax No</td>
				<td>
							<bean:write name="vendorMasterRequestForm" property="faxNo"/>

				</td>
				<td>e-Mail</td>
				<td>
							<bean:write name="vendorMasterRequestForm" property="emailId"/>

				</td>
			</tr>
		
   		    <tr>
	  			<th colspan="4"><big>Tax Details</big></th>
   			</tr>
								
			<tr>
				<td>Currency <font color="red">*</font></td>
				<td>
										<bean:write name="vendorMasterRequestForm" property="currencyId"/>
	
				</td>
				<td>Reconciliation A/c. <font color="red">*</font>
				<td>
												<bean:write name="vendorMasterRequestForm" property="reConcillationActId"/>

				</td>
			</tr>

			<tr>
				<td>Is Eligible For TDS <font color="red">*</font>
				<td>
											<bean:write name="vendorMasterRequestForm" property="elgTds"/>

				</td>
				<td>TDS Code <div id="mandatoryEligibleTds" style="visibility: hidden"><font color="red">*</font></div></td>
				<td>
										<bean:write name="vendorMasterRequestForm" property="tdsCode"/>

				</td>
			</tr>
								
			<tr>
				<td>LST No.</td>
				<td>
											<bean:write name="vendorMasterRequestForm" property="lstNo"/>

				</td>
				<td>Tin No<div style="visibility: hidden" id="tin3"><font color="red">*</font></div></td>
				<td>
											<bean:write name="vendorMasterRequestForm" property="tinNo"/>

				</td>
			</tr>
								
			<tr>
				<td>CST No.<div style="visibility: hidden" id="cst4"><font color="red">*</font></div></td>
				<td>
															<bean:write name="vendorMasterRequestForm" property="cstNo"/>

				</td>
				<td>Payment Term <font color="red">*</font></td>
				<td>
															<bean:write name="vendorMasterRequestForm" property="paymentTermId"/>

				</td>
			</tr>

			<tr>
				<td>Account Clerk</td>
				<td>
										<bean:write name="vendorMasterRequestForm" property="accountClerkId"/>

				</td>
				<td>Is Approved Vendor</td>
				<td>
										<bean:write name="vendorMasterRequestForm" property="isApprovedVendor"/>

				</td>
			</tr>
								
			<tr>
				<td>PAN No <div style="visibility: hidden" id="group1"><font color="red">*</font></div> </td>
				<td>
														<bean:write name="vendorMasterRequestForm" property="panNo"/>

				</td>
				<td>Service Tax Reg. No.<div style="visibility: hidden" id="Service2"><font color="red">*</font></div> </td>
				<td>
														<bean:write name="vendorMasterRequestForm" property="serviceTaxRegNo"/>

				</td>
			</tr>
								
			<tr>
				<td>Is Reg. Excise Vendor </td>
				<td>
									<bean:write name="vendorMasterRequestForm" property="regExciseVendor"/>

				</td>
				<td>ECC No. <div id="mandatoryRegisteredExciseVendor1"><font color="red">*</font></div></td>
				<td>
										<bean:write name="vendorMasterRequestForm" property="serviceTaxRegNo"/>

				</td>
								
			</tr>
			<tr>
				<td>Excise Reg. No. <div id="mandatoryRegisteredExciseVendor2" style="visibility: hidden"><font color="red">*</font></div></td>
				<td>
								<bean:write name="vendorMasterRequestForm" property="eccNo"/>

				</td>
				<td>Excise Range <div id="mandatoryRegisteredExciseVendor3" style="visibility: hidden"><font color="red">*</font></div></td>
				<td>
										<bean:write name="vendorMasterRequestForm" property="exciseRange"/>

				</td>
			</tr>

			<tr>
				<td>Excise Division <div id="mandatoryRegisteredExciseVendor4" style="visibility: hidden"><font color="red">*</font></div></td>
				<td>
														<bean:write name="vendorMasterRequestForm" property="exciseDivision"/>

				</td>
				<td>Commissionerate <div id="mandatoryRegisteredExciseVendor5" style="visibility: hidden"><font color="red">*</font></div></td>
				<td>
														<bean:write name="vendorMasterRequestForm" property="commissionerate"/>

				</td>
			</tr>
							
			
						
												
			<tr>
				<th colspan="4"><big>Attachments <font color="red">*</font></big></th>
			</tr>
		

			<logic:notEmpty name="documentDetails">
				<tr>
					<th colspan="4"><big>Uploaded Documents</big></th>
				</tr>
				<logic:iterate id="abc" name="documentDetails">
					<tr>
					
						<td colspan="4"><a href="/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/${abc.fileName }" target="_blank"><bean:write name="abc" property="fileName"/></a></td>
					</tr>
				</logic:iterate>
						
			
			</logic:notEmpty>
			
				<tr>			
	<th colspan="6">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<img src="images/star.gif" width="8" height="8" /></td>
				<td>
				<bean:write name="vendorMasterRequestForm" property="sapCodeExists"/>
				</td>
		
			<td >SAP Code No<img src="images/star.gif" width="8" height="8" /></td>
				<td colspan="4"><bean:write name="vendorMasterRequestForm" property="sapCodeNo"/>	</td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<bean:write name="vendorMasterRequestForm" property="sapCreationDate"/>
				
			</td>
			
				<td >Code Created By<img src="images/star.gif" width="8" height="8" /></td><td colspan="4">
				<bean:write name="vendorMasterRequestForm" property="sapCreatedBy"/>
			
				</td>
			</tr>
			
			
			
			
			
			
			
			
</logic:iterate>
				<td colspan="6">
							<html:button property="method" value="Close" onclick="history.back(-1)" styleClass="rounded" style="width: 100px" ></html:button>
						</td>
			
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
		


</div>
</table></div></html:form>
</body>
</html>