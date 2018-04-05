<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>eMicro :: Vendor Master Creation</title>

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
	<script type="text/javascript" src="js/validate.js"></script>

	<style>
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

<script type="text/javascript">

function getDropdown()
{
var selectBox=document.getElementById("vendorType");
var selectedvalue=selectBox.options[selectBox.selectedIndex].value;

if(selectedvalue=="I")
{
document.getElementById("gstinNo").disabled = true;
document.getElementById("gstinNo").value='';
}
if(selectedvalue=="M"){
document.getElementById("gstinNo").disabled = false;

}
if(selectedvalue=="D"){
document.getElementById("gstinNo").disabled = false;
}
if(selectedvalue=="FD"){
document.getElementById("gstinNo").disabled=false;
}
if(selectedvalue=="SD"){
document.getElementById("gstinNo").disabled=false;
}
if(selectedvalue=="MD"){
document.getElementById("gstinNo").disabled=false;
}
if(selectedvalue=="SV"){
document.getElementById("gstinNo").disabled=false;
}

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




function saveData(param){

		if(document.forms[0].gstinNo.value=="")
	    {
	      alert("Please Enter GSTIN_Number");
	      document.forms[0].gstinNo.focus();
	      return false;
	    }

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
	    
	    if(document.forms[0].accountGroupId.value!=6){
	      if(document.forms[0].typeOfVendor.value=="")
			    {
			      alert("Please Select Type Of Vendor");
			      document.forms[0].typeOfVendor.focus();
			      return false;
			    }
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
	     var st = document.forms[0].name.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].name.value=st; 
	
  		if(document.forms[0].address1.value=="")
	    {
	      alert("Please Enter Address1");
	      document.forms[0].address1.focus();
	      return false;
	    }
	        var st = document.forms[0].address1.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].address1.value=st; 

if(document.forms[0].address2.value!="")
	    {
	      var st = document.forms[0].address2.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].address2.value=st; 
	    }
	    if(document.forms[0].address3.value!="")
	    {
	       var st = document.forms[0].address3.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].address3.value=st; 
	    }
	     if(document.forms[0].address4.value!="")
	    {
	       var st = document.forms[0].address4.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].address4.value=st; 
	    }

	    
	    if(document.forms[0].city.value=="")
	    {
	      alert("Please Enter City");
	      document.forms[0].city.focus();
	      return false;
	    }
	      if(document.forms[0].city.value!="")
	    {
	       var st = document.forms[0].city.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].city.value=st;
	    }

        if(document.forms[0].accountGroupId.value!=4)
        {
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
      
        }
        
	    if(document.forms[0].country.value=="")
	    {
	      alert("Please Select  Country");
	      document.forms[0].country.focus();
	      return false;
	    }
	    if(document.forms[0].country.value!="")
	    {
	     if(document.forms[0].accountGroupId.value!=4)
        {
		    if(document.forms[0].state.value=="")
		    {
		      alert("Please Select  State");
		      document.forms[0].state.focus();
		      return false;
		    }
	    }}
	    
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
	     var st = document.forms[0].emailId.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].emailId.value=st;
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
	    
	      alert("Please Select Is Eligible For TDS");
	      document.forms[0].elgTds.focus();
	      return false;
	   
	    }
	    if(document.forms[0].elgTds.value=="True")
	    {
	    if(document.forms[0].tdsCode.value=="")
	    {
	      alert("Please Select TDS Code");
	      document.forms[0].tdsCode.focus();
	      return false;
	    }
	    
	}
	    
	     if(document.forms[0].lstNo.value!="")
	    {
	       var st = document.forms[0].lstNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].lstNo.value=st;
	    }
	    
	    
   
   
	    
	 
	     if(document.forms[0].accountGroupId.value!=4 &&  document.forms[0].accountGroupId.value!=6)
	     {
	  
	   if(document.forms[0].tinNo.value=="")
	    {
	      alert("Please Enter TIN No");
	      document.forms[0].tinNo.focus();
	      return false;
	    }
	    if(document.forms[0].tinNo.value!="")
	    {
	     var st = document.forms[0].tinNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].tinNo.value=st;
   }
   
   if(document.forms[0].cstNo.value=="")
	    {
	      alert("Please Enter CST No");
	      document.forms[0].cstNo.focus();
	      return false;
	    }
   
   if(document.forms[0].cstNo.value!="")
	    {
	      var st = document.forms[0].cstNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].cstNo.value=st;
	   }
	   
	   if(document.forms[0].accountGroupId.value!=6){
	          if(document.forms[0].paymentTermId.value=="")
	    {
	      alert("Please Select Payment Term ");
	      document.forms[0].paymentTermId.focus();
	      return false;
	    }}
	    
	         if(document.forms[0].accountClerkId.value=="")
	    {
	    
	      alert("Please Select Account clerk");
	      document.forms[0].accountClerkId.focus();
	      return false;
	   
	    }
	   if(document.forms[0].isApprovedVendor.value=="")
	    {
	    
	      alert("Please Select Is Approved Vendor");
	      document.forms[0].isApprovedVendor.focus();
	      return false;
	   
	    } 
	  
	  
	    if(document.forms[0].panNo.value=="")
	    {
	      alert("Please Enter Pan No ");
	      document.forms[0].panNo.focus();
	      return false;
	    }
	    
	   
	   
	       var st = document.forms[0].panNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].panNo.value=st;
   
   
   if(document.forms[0].serviceTaxRegNo.value=="")
	    {
	      alert("Please Enter Service Tax Reg. No ");
	      document.forms[0].serviceTaxRegNo.focus();
	      return false;
	    }
	    
	    if(document.forms[0].serviceTaxRegNo.value!="")
	    {
	  var st = document.forms[0].serviceTaxRegNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].serviceTaxRegNo.value=st;
	    }
   
   
   }
	     
		    if(document.forms[0].tinNo.value!="")
		    {
		     var st = document.forms[0].tinNo.value;
		var Re = new RegExp("\\'","g");
		st = st.replace(Re,"`");
		document.forms[0].tinNo.value=st;
	   }
	   
	   
	   if(document.forms[0].cstNo.value!="")
		    {
		      var st = document.forms[0].cstNo.value;
		var Re = new RegExp("\\'","g");
		st = st.replace(Re,"`");
		document.forms[0].cstNo.value=st;
		   }
	   if(document.forms[0].panNo.value!="")
	    {
	   var st = document.forms[0].panNo.value;
		var Re = new RegExp("\\'","g");
		st = st.replace(Re,"`");
		document.forms[0].panNo.value=st;
	    }
	   if(document.forms[0].serviceTaxRegNo.value!="")
	    {
	  var st = document.forms[0].serviceTaxRegNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].serviceTaxRegNo.value=st;
	    }
   
     if(document.forms[0].accountGroupId.value!=6){
	       if(document.forms[0].paymentTermId.value=="")
	    {
	      alert("Please Select Payment Term ");
	      document.forms[0].paymentTermId.focus();
	      return false;
	    }}
	    
	         if(document.forms[0].accountClerkId.value=="")
	    {
	    
	      alert("Please Select Account clerk");
	      document.forms[0].accountClerkId.focus();
	      return false;
	   
	    }
	   if(document.forms[0].isApprovedVendor.value=="")
	    {
	    
	      alert("Please Select Is Approved Vendor");
	      document.forms[0].isApprovedVendor.focus();
	      return false;
	   
	    }
	 var st = document.forms[0].eccNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].eccNo.value=st;
	
	var st = document.forms[0].exciseRegNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exciseRegNo.value=st;
	
	var st = document.forms[0].exciseRange.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exciseRange.value=st;
	
	var st = document.forms[0].exciseDivision.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exciseDivision.value=st;
	
	 var st = document.forms[0].commissionerate.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].commissionerate.value=st;
	    
	  
	  
		    if(document.forms[0].regExciseVendor.value=="True"){
			    if(document.forms[0].eccNo.value=="")
			    {
			      alert("Please Enter Ecc No");
			      document.forms[0].eccNo.focus();
			      return false;
			    }
			    
			var st = document.forms[0].eccNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].eccNo.value=st;
			    
			    if(document.forms[0].exciseRegNo.value=="")
			    {
			      alert("Please Enter Exercise Reg No");
			      document.forms[0].exciseRegNo.focus();
			      return false;
			    }
			    
			     var st = document.forms[0].exciseRegNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exciseRegNo.value=st;
			    
			    if(document.forms[0].exciseRange.value=="")
			    {
			      alert("Please Enter Excise Range");
			      document.forms[0].exciseRange.focus();
			      return false;
			    }
			    var st = document.forms[0].exciseRange.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exciseRange.value=st;
	
			    if(document.forms[0].exciseDivision.value=="")
			    {
			      alert("Please Enter Excise Division");
			      document.forms[0].exciseDivision.focus();
			      return false;
			    }
			     var st = document.forms[0].exciseDivision.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exciseDivision.value=st;
	
			     if(document.forms[0].commissionerate.value=="")
			    {
			      alert("Please Enter Commissionerate");
			      document.forms[0].commissionerate.focus();
			      return false;
			    }
			      var st = document.forms[0].commissionerate.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].commissionerate.value=st;
			   
			    
		    }
	    var purchase=document.forms[0].purchaseView.checked;
	   
	    var accountView=document.forms[0].accountView.checked;
	    
	     var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	    
	    if(param=="Save"){
	var url="vendorMasterRequest.do?method=submit&purchase="+purchase+"&accountView="+accountView;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	
	else{
	var url="vendorMasterRequest.do?method=saveAndSubmitMaterial&purchase="+purchase+"&accountView="+accountView;
	document.forms[0].action=url;
	document.forms[0].submit();
}

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
document.getElementById("pin").style.visibility="hidden";
document.getElementById("state").style.visibility="hidden";
document.getElementById("payterm").style.visibility="visible";


}
if(accountGroup!=4)
{
document.getElementById("group1").style.visibility="visible";
document.getElementById("Service2").style.visibility="visible";
document.getElementById("cst4").style.visibility="visible";
document.getElementById("tin3").style.visibility="visible";
document.getElementById("pin").style.visibility="visible";
document.getElementById("state").style.visibility="visible";
document.getElementById("payterm").style.visibility="visible";
}
if(accountGroup=="")
{
document.getElementById("group1").style.visibility="hidden";
document.getElementById("Service2").style.visibility="hidden";
document.getElementById("cst4").style.visibility="hidden";
document.getElementById("tin3").style.visibility="hidden";
document.getElementById("pin").style.visibility="hidden";
document.getElementById("state").style.visibility="hidden";
document.getElementById("ventype").style.visibility="visible";
document.getElementById("payterm").style.visibility="visible";

}

if(accountGroup==6)
{
document.getElementById("group1").style.visibility="hidden";
document.getElementById("Service2").style.visibility="hidden";
document.getElementById("cst4").style.visibility="hidden";
document.getElementById("tin3").style.visibility="hidden";
document.getElementById("payterm").style.visibility="hidden";


}
if(accountGroup!="")
{
document.getElementById("ventype").style.visibility="visible";
if(accountGroup==6)
{
document.getElementById("ventype").style.visibility="hidden";
}
}

displayApprovers();

}
function file(x)
{
alert(x);
}

function displayApprovers(){
	var accountGroupId=document.forms[0].accountGroupId.value;
	
	if(accountGroupId!="" ){
		
		var xmlhttp;
		if (window.XMLHttpRequest)
		  {// code for IE7+, Firefox, Chrome, Opera, Safari
		  xmlhttp=new XMLHttpRequest();
		  }
		else
		  {// code for IE6, IE5
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
		    document.getElementById("apprListID").innerHTML=xmlhttp.responseText;
		    }
		  }
		xmlhttp.open("POST","vendorMasterRequest.do?method=displayApprovers&accountGroupId="+accountGroupId,true);
		xmlhttp.send();
	}
}
//-->
</script>
<style>

.no
{pointer-events: none; 
}
.design

{
	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);
} 


</style>
</head>

<body onload="getIsEligibleTds(),getRegisteredExciseVendor(),changeTaxDetails()">
   			<%
			
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			if(menuIcon==null){
			menuIcon="";
			}
			
			%>
			
			<% 
  			  UserInfo user=(UserInfo)session.getAttribute("user");
  			%>
  
  
    
	
				
	<html:form action="vendorMasterRequest.do" enctype="multipart/form-data">
	<div id="masterdiv" class="">
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
					<html:text property="requestNo" readonly="true" style="background-color:#d3d3d3;"/>
					<html:hidden property="typeDetails"/>
				</td>
				<td>Date <font color="red">*</font></td>
				<td>
					<html:text property="requestDate" styleId="requestDate" readonly="true" style="background-color:#d3d3d3;"/>
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
					<html:select name="vendorMasterRequestForm" property="accountGroupId" onchange="changeTaxDetails()">
						<html:option value="">--Select--</html:option>
						<html:options name="vendorMasterRequestForm" property="accountGroupList" labelProperty="accountGroupLabelList"/>
					</html:select>
				</td>
			</tr>
							
			<tr>
				<td>View Type <font color="red">*</font></td>
				<td>
					<span class="text">Purchase View</span>
						<html:checkbox property="purchaseView"/>
						&nbsp;&nbsp;&nbsp;
					<span class="text">Account View</span>
						<html:checkbox property="accountView"/>
				</td>
				<td>Vendor Type <div id="ventype" style="visibility: hidden"><font color="red">*</font></td>
				<td><html:select name="vendorMasterRequestForm" property="typeOfVendor" styleId="vendorType" onchange="getDropdown();">
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
					<html:select name="vendorMasterRequestForm" property="title">
						<html:option value="">----Select---</html:option>
						<html:option value="MR">MR</html:option>
						<html:option value="MRS">MR'S</html:option>
						<html:option value="DR">DR</html:option>
						<html:option value="COMPANY">COMPANY</html:option>									
					</html:select>
				</td>
				<td>Name <font color="red">*</font></td>
				<td><html:text property="name" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
			</tr>
								
			<tr>
				<td>Address1 <font color="red">*</font></td>
				<td><html:text property="address1" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
				<td>Address2</td>
				<td><html:text property="address2" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			</tr>
								
			<tr>
				<td>Address3</td>
				<td><html:text property="address3" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
				<td>Address4</td>
				<td align="left"><html:text property="address4" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			</tr>

			<tr>
				<td>City <font color="red">*</font></td>
				<td><html:text property="city" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
				<td>Pin Code <div style="visibility: hidden" id="pin"><font color="red">*</font></td>
				<td><html:text property="pinCode" maxlength="10" size="10" style="text-transform:uppercase"></html:text></td>
			</tr>

			<tr>
				<td>Country <font color="red">*</font></td>
				<td>
					<html:select name="vendorMasterRequestForm" property="country" styleClass="text_field"  onchange="displayStates()">
						<html:option value="">--Select--</html:option>
						<html:options name="vendorMasterRequestForm" property="countryList" labelProperty="countryLabelList"/>
					</html:select>
				</td>
				<td>State <div style="visibility: hidden" id="state"><logic:notEmpty name="States"><font color="red">*</font></logic:notEmpty></td>
				<td>
					<html:select name="vendorMasterRequestForm" property="state">
						<html:option value="">--Select--</html:option>
						<html:options name="vendorMasterRequestForm" property="stateList" labelProperty="stateLabelList"/>
					</html:select>
				</td>
			</tr>
								
			<tr>
				<td>Landline No.</td>
				<td><html:text property="landLineNo" maxlength="20" size="20" title="Maximum of 20 characters" style="text-transform:uppercase"></html:text></td>
				<td>Mobile No.</td>
				<td><html:text property="mobileNo" maxlength="20" size="20" title="Maximum of 20 characters" style="text-transform:uppercase"></html:text></td>
			</tr>

			<tr>
				<td>Fax No</td>
				<td><html:text property="faxNo" maxlength="20" size="20" title="Maximum of 20 characters" style="text-transform:uppercase"></html:text></td>
				<td>e-Mail</td>
				<td><html:text property="emailId" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
			</tr>
		
   		    <tr>
	  			<th colspan="4"><big>Tax Details</big></th>
   			</tr>
								
			<tr>
				<td>Currency <font color="red">*</font></td>
				<td>
					<html:select name="vendorMasterRequestForm" property="currencyId">
						<html:option value="">--Select--</html:option>
						<html:options name="vendorMasterRequestForm" property="currencyList" labelProperty="currencyLabelList" style="width: 220px"/>
					</html:select>
				</td>
				<td>Reconciliation A/c. <font color="red">*</font>
				<td>
					<html:select name="vendorMasterRequestForm" property="reConcillationActId">
						<html:option value="">--Select--</html:option>
						<html:options name="vendorMasterRequestForm" property="reConcillationList" labelProperty="reConcillationLabelList" style="width: 250px"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Is Eligible For TDS <font color="red">*</font>
				<td><html:select name="vendorMasterRequestForm" property="elgTds" onchange="getIsEligibleTds()">
						<html:option value="">--Select--</html:option>
						<html:option value="True">Yes</html:option>
						<html:option value="False">No</html:option>
					</html:select>
				</td>
				<td>TDS Code <div id="mandatoryEligibleTds" style="visibility: hidden"><font color="red">*</font></div></td>
				<td><html:select name="vendorMasterRequestForm" property="tdsCode">
						<html:option value="">--Select--</html:option>
						<html:options name="vendorMasterRequestForm" property="tdsIdList" labelProperty="tdsLabelList"/>
					</html:select>
				</td>
			</tr>
				<tr>
				<td>GSTIN Number<font color="red">*</font></td>
				<td colspan="3"  >
					<html:text property="gstinNo" styleId="gstinNo" maxlength="15"  title="Maximum of 15 characters" style="width:150px;text-transform:uppercase" ></html:text>
				</td>
				</tr>					
			<tr>
				<td>LST No.</td>
				<td><html:text property="lstNo" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
				<td>Tin No<div style="visibility: hidden" id="tin3"><font color="red">*</font></div></td>
				<td><html:text property="tinNo" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
			</tr>
								
			<tr>
				<td>CST No.<div style="visibility: hidden" id="cst4"><font color="red">*</font></div></td>
				<td><html:text property="cstNo" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
				<td>Payment Term <div style="visibility: visible" id="payterm"><font color="red">*</font></td>
				<td><html:select name="vendorMasterRequestForm" property="paymentTermId">
						<html:option value="">--Select--</html:option>
						<html:options name="vendorMasterRequestForm" property="paymentTermList" labelProperty="paymentTermLabelList" style="width: 250px"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Account Clerk <font color="red">*</font></td>
				<td><html:select name="vendorMasterRequestForm" property="accountClerkId">
						<html:option value="">--Select--</html:option>
						<html:options name="vendorMasterRequestForm" property="accountClerkList" labelProperty="accountClerkLabelList"/>
					</html:select>
				</td>
				<td>Is Approved Vendor <font color="red">*</font></td>
				<td><html:select name="vendorMasterRequestForm" property="isApprovedVendor">
						<html:option value="">--Select--</html:option>
						<html:option value="True">Yes</html:option>
						<html:option value="False">No</html:option>
					</html:select>
				</td>
			</tr>
								
			<tr>
				<td>PAN No <div style="visibility: hidden" id="group1"><font color="red">*</font></div> </td>
				<td><html:text property="panNo"  maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
				<td>Service Tax Reg. No.<div style="visibility: hidden" id="Service2"><font color="red">*</font></div> </td>
				<td><html:text property="serviceTaxRegNo" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
			</tr>
								
			<tr>
				<td>Is Reg. Excise Vendor </td>
				<td><html:select name="vendorMasterRequestForm" property="regExciseVendor" onchange="getRegisteredExciseVendor()">
						<html:option value="">--Select--</html:option>
						<html:option value="True">Yes</html:option>
						<html:option value="False">No</html:option>
					</html:select>
				</td>
				<td>ECC No. <div id="mandatoryRegisteredExciseVendor1"><font color="red">*</font></div></td>
				<td><html:text property="eccNo"   maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase;"></html:text></td>
								
			</tr>
			<tr>
				<td>Excise Reg. No. <div id="mandatoryRegisteredExciseVendor2" style="visibility: hidden"><font color="red">*</font></div></td>
				<td><html:text property="exciseRegNo" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
				<td>Excise Range <div id="mandatoryRegisteredExciseVendor3" style="visibility: hidden"><font color="red">*</font></div></td>
				<td><html:text property="exciseRange" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
			</tr>

			<tr>
				<td>Excise Division <div id="mandatoryRegisteredExciseVendor4" style="visibility: hidden"><font color="red">*</font></div></td>
				<td><html:text property="exciseDivision" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
				<td>Commissionerate <div id="mandatoryRegisteredExciseVendor5" style="visibility: hidden"><font color="red">*</font></div></td>
				<td><html:text property="commissionerate" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
			</tr>
							
			<logic:notEmpty name="approved">
				<tr>
					<td>Approve Type</td>
					<td><html:select name="vendorMasterRequestForm" property="approveType" styleClass="text_field">
							<html:option value="">--Select--</html:option>
							<html:option value="Pending">Pending</html:option>
							<html:option value="Approved">Approved</html:option>
							<html:option value="Rejected">Rejected</html:option>
						</html:select>
					</td>
				</tr>
			</logic:notEmpty>
						
												
			<tr>
				<th colspan="4"><big>Attachments <font color="red">*</font></big></th>
			</tr>
			<tr>
				<td colspan="4">
					<html:file property="vendorAttachments" styleClass="rounded" style="width: 220px"/>
					<html:button property="method" value="Upload" onclick="onUploadDocuments()" styleClass="rounded" style="width: 100px" />
				</td>
			</tr>

			<logic:notEmpty name="documentDetails">
				<tr>
					<th colspan="4"><big>Uploaded Documents</big></th>
				</tr>
				<logic:iterate id="abc" name="documentDetails">
					<tr>
						<td>
							<html:checkbox property="documentCheck" name="abc" value="${abc.fileName}" styleId="${abc.fileName}" onclick="addInput(this.value)"/>
						</td>
						<td colspan="3"><a href="/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/${abc.fileName }" target="_blank"><bean:write name="abc" property="fileName"/></a></td>
					</tr>
				</logic:iterate>
						
				<tr>
					<td colspan="4">
						<html:button property="method" value="Delete" onclick="deleteDocumentsSelected()" styleClass="rounded" style="width: 100px"/>
					</td>
				</tr>
			</logic:notEmpty>

			
			<tr>
				<td colspan="4" style="text-align: center;">
					<html:button property="method"  value="Save" onclick="saveData(this.value)" styleClass="rounded" style="width: 100px"/>&nbsp;&nbsp;
					<html:button property="method"  value="Save & Submit" onclick="saveData(this.value)" styleClass="rounded" style="width: 100px"/>&nbsp;&nbsp;
					<html:reset value="Reset" styleClass="rounded" style="width: 100px"/>&nbsp;&nbsp;
					<html:button property="method"  value="Close" onclick="onClose()" styleClass="rounded" style="width: 100px"/>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
		displayApprovers();
		</script>
		<div id="apprListID">
		</div>
		
		</div>		
		</br>
		  <div align="center">
				<logic:notEmpty name="vendorMasterRequestForm" property="message">
					<font color="red" size="3"><b><bean:write name="vendorMasterRequestForm" property="message" /></b></font>
				</logic:notEmpty>
				<logic:notEmpty name="vendorMasterRequestForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="vendorMasterRequestForm" property="message2" /></b></font>
				</logic:notEmpty>
				<logic:notEmpty name="vendorMasterRequestForm" property="appStatusMessage" >
				<br/>
				<font color="red" size="3"><b><bean:write name="vendorMasterRequestForm" property="appStatusMessage" /></b></font>
				</logic:notEmpty>
			</div>
			<logic:notEmpty name="vendorfile">
	 <script>	
				 	file("<bean:write name="vendorMasterRequestForm" property="message" /> ") 
				 	</script>
	
	
	</logic:notEmpty></div>
	</html:form>
<script type="text/javascript">
changeTaxDetails();
displayApprovers();
</script>
</body>
</html>
