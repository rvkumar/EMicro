<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
<title>eMicro :: Raw Material </title>

	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<script type="text/javascript">

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
	var reqId = document.forms[0].requestNumber.value;
	var reqType = "ROH";
	var matGroup="";
	var location="";
	 var purchase=document.forms[0].purchaseView.checked;
	   
	    var accountView=document.forms[0].accountView.checked;
	var url="approvals.do?method=statusChangeForVendorMaster&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&purchase="+purchase+"&accountView="+accountView;
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
<!--

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
		
		var URL="approvals.do?method=displayState";
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

<body onload="getIsEligibleTds(),getRegisteredExciseVendor(),changeTaxDetails()" style="text-transform: uppercase;">
   				<html:form action="/approvals.do" enctype="multipart/form-data">
	<div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>
	
		<html:hidden property="linkName"/>
		
		<div style="width: 90%">			
		<table class="bordered" width="90%">
			<tr>
       			<th colspan="4"><center><big>Vendor Master Creation</big></center></th>
      		</tr>
			<tr>
	  			<th colspan="4"><big>General Information</big></th>
   			</tr>

			<tr>
				<td>Request No. <font color="red">*</font></td>
				<td>
					<html:text property="requestNo" readonly="true"/>
					<html:hidden property="typeDetails"/>
				</td>
				<td>Date <font color="red">*</font></td>
				<td>
					<html:text property="requestDate" styleId="requestDate" readonly="true"/>
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
					<html:select name="approvalsForm" property="accountGroupId" onchange="changeTaxDetails()" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="accountGroupList" labelProperty="accountGroupLabelList"/>
					</html:select>
				</td>
			</tr>
							
			<tr>
				<td>View Type <font color="red">*</font></td>
				<td>
					<span class="text">Purchase View</span>
						<html:checkbox property="purchaseView" disabled="true"/>
						&nbsp;&nbsp;&nbsp;
					<span class="text">Account View</span>
						<html:checkbox property="accountView" disabled="true"/>
				</td>
				<td>Vendor Type <font color="red">*</font></td>
				<td><html:select name="approvalsForm" property="typeOfVendor" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:option value="I">IMPORTER</html:option>
						<html:option value="M">MANUFACTURER</html:option>
						<html:option value="D">DEALER</html:option>
						<html:option value="FD">FIRST STAGE DEALER OF INDIGENOUS</html:option>
						<html:option value="SD">SECOND STAGE DEALER OF INDIGENOUS</html:option>
						<html:option value="MD">DEPOT MANUFACTURER</html:option>
						<html:option value="SV">SERVICE VENDOR</html:option>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Title <font color="red">*</font></td>
				<td>
					<html:select name="approvalsForm" property="title" disabled="true">
						<html:option value="">----Select---</html:option>
						<html:option value="MR">MR</html:option>
						<html:option value="MRS">MR'S</html:option>
						<html:option value="DR">DR</html:option>
						<html:option value="COMPANY">COMPANY</html:option>									
					</html:select>
				</td>
				<td>Name <font color="red">*</font></td>
				<td><html:text property="name" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
			</tr>
								
			<tr>
				<td>Address1 <font color="red">*</font></td>
				<td><html:text property="address1" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"/></td>
				<td>Address2</td>
				<td><html:text property="address2" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"/></td>
			</tr>
								
			<tr>
				<td>Address3</td>
				<td><html:text property="address3" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"/></td>
				<td>Address4</td>
				<td align="left"><html:text property="address4" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"/></td>
			</tr>

			<tr>
				<td>City <font color="red">*</font></td>
				<td><html:text property="city" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
				<td>Pin Code <font color="red">*</font></td>
				<td><html:text property="pinCode" maxlength="10" size="10" style="text-transform:uppercase" readonly="true"></html:text></td>
			</tr>

			<tr>
				<td>Country <font color="red">*</font></td>
				<td>
					<html:select name="approvalsForm" property="country" styleClass="text_field"  onchange="displayStates()" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="countryList" labelProperty="countryLabelList"/>
					</html:select>
				</td>
				<td>State <logic:notEmpty name="States"><font color="red">*</font></logic:notEmpty></td>
				<td>
					<html:select name="approvalsForm" property="state" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="stateList" labelProperty="stateLabelList"/>
					</html:select>
				</td>
			</tr>
								
			<tr>
				<td>Landline No.</td>
				<td><html:text property="landLineNo" maxlength="20" size="20" title="Maximum of 20 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
				<td>Mobile No.</td>
				<td><html:text property="mobileNo" maxlength="20" size="20" title="Maximum of 20 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
			</tr>

			<tr>
				<td>Fax No</td>
				<td><html:text property="faxNo" maxlength="20" size="20" title="Maximum of 20 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
				<td>e-Mail</td>
				<td><html:text property="emailId" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
			</tr>
		
   		    <tr>
	  			<th colspan="4"><big>Tax Details</big></th>
   			</tr>
								
			<tr>
				<td>Currency <font color="red">*</font></td>
				<td>
					<html:select name="approvalsForm" property="currencyId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="currencyList" labelProperty="currencyLabelList" style="width: 220px"/>
					</html:select>
				</td>
				<td>Reconciliation A/c. <font color="red">*</font>
				<td>
					<html:select name="approvalsForm" property="reConcillationActId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="reConcillationList" labelProperty="reConcillationLabelList" style="width: 250px"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Is Eligible For TDS <font color="red">*</font>
				<td><html:select name="approvalsForm" property="elgTds" onchange="getIsEligibleTds()" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:option value="True">Yes</html:option>
						<html:option value="False">No</html:option>
					</html:select>
				</td>
				<td>TDS Code <div id="mandatoryEligibleTds" style="visibility: hidden"><font color="red">*</font></div></td>
				<td><html:select name="approvalsForm" property="tdsCode" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="tdsIdList" labelProperty="tdsLabelList"/>
					</html:select>
				</td>
			</tr>
				<tr>
				<td>GSTIN Number<font color="red">*</font></td>
				<td colspan="3">
				<html:text property="gstinNo" styleId="gstinNo" maxlength="15"   title="Maximum of 15 characters" style="width:150px;text-transform:uppercase" ></html:text>
				</td>
				</tr>					
			<tr>
				<td>LST No.</td>
				<td><html:text property="lstNo" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
				<td>Tin No<div style="visibility: hidden" id="tin3"><font color="red">*</font></div></td>
				<td><html:text property="tinNo" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
			</tr>
								
			<tr>
				<td>CST No.<div style="visibility: hidden" id="cst4"><font color="red">*</font></div></td>
				<td><html:text property="cstNo" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
				<td>Payment Term <font color="red">*</font></td>
				<td><html:select name="approvalsForm" property="paymentTermId">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="paymentTermList" labelProperty="paymentTermLabelList" style="width: 250px"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Account Clerk</td>
				<td><html:select name="approvalsForm" property="accountClerkId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="accountClerkList" labelProperty="accountClerkLabelList"/>
					</html:select>
				</td>
				<td>Is Approved Vendor</td>
				<td><html:select name="approvalsForm" property="isApprovedVendor" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:option value="True">Yes</html:option>
						<html:option value="False">No</html:option>
					</html:select>
				</td>
			</tr>
								
			<tr>
				<td>PAN No <div style="visibility: hidden" id="group1"><font color="red">*</font></div> </td>
				<td><html:text property="panNo"  maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
				<td>Service Tax Reg. No.<div style="visibility: hidden" id="Service2"><font color="red">*</font></div> </td>
				<td><html:text property="serviceTaxRegNo" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
			</tr>
								
			<tr>
				<td>Is Reg. Excise Vendor </td>
				<td><html:select name="approvalsForm" property="regExciseVendor" onchange="getRegisteredExciseVendor()" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:option value="True">Yes</html:option>
						<html:option value="False">No</html:option>
					</html:select>
				</td>
				<td>ECC No. <div id="mandatoryRegisteredExciseVendor1"><font color="red">*</font></div></td>
				<td><html:text property="eccNo"   maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase;" readonly="true"></html:text></td>
								
			</tr>
			<tr>
				<td>Excise Reg. No. <div id="mandatoryRegisteredExciseVendor2" style="visibility: hidden"><font color="red">*</font></div></td>
				<td><html:text property="exciseRegNo" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
				<td>Excise Range <div id="mandatoryRegisteredExciseVendor3" style="visibility: hidden"><font color="red">*</font></div></td>
				<td><html:text property="exciseRange" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
			</tr>

			<tr>
				<td>Excise Division <div id="mandatoryRegisteredExciseVendor4" style="visibility: hidden"><font color="red">*</font></div></td>
				<td><html:text property="exciseDivision" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
				<td>Commissionerate <div id="mandatoryRegisteredExciseVendor5" style="visibility: hidden"><font color="red">*</font></div></td>
				<td><html:text property="commissionerate" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase" readonly="true"></html:text></td>
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
		<tr><td colspan="6" style="border:0px; text-align: center;">
   <logic:notEmpty name="approveButton">
	<input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;
	</logic:notEmpty>
	<logic:notEmpty name="rejectButton">
			<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  /></td>
			
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
<html:hidden property="requestNumber"/>
	<html:hidden property="locationId"/>
	<html:hidden property="materialTypeId"/>
	<html:hidden property="reqMaterialGroup"/>

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
