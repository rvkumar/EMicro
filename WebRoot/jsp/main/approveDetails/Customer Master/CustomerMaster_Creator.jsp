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
  
	var elemValue = elem.value;
	if(elemValue=='Created')
	{
	elemValue='Approve';
	
	if(document.forms[0].requestDate.value=="")
	    {
	      alert("Please Select Request Date");
	      document.forms[0].requestDate.focus();
	      return false;
	    }
	    	if(document.forms[0].accGroupId.value=="")
	    {
	      alert("Please Select Account Group");
	      document.forms[0].accGroupId.focus();
	      return false;
	    }
	    if(document.forms[0].domestic.checked==false && document.forms[0].exports.checked==false)
	    {
	      alert("Please Select Atleast One Customer Type");
	       document.forms[0].domestic.focus();
	      return false;
	    }
	      if(document.forms[0].cutomerCode.value!="")
	    {
	   var cutomerCode = document.forms[0].cutomerCode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(cutomerCode)) {
             alert("EmployeeCode  Should be Number ");
                document.forms[0].cutomerCode.focus();
            return false;
        }
	    }
	    	    if(document.forms[0].accGroupId.value=="FS")
	    {
	    
	    if(document.forms[0].cutomerCode.value=="")
	    {
	      alert("Please Enter EmployeeCode");
	      document.forms[0].cutomerCode.focus();
	      return false;
	    }
	    var cutomerCode = document.forms[0].cutomerCode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(cutomerCode)) {
             alert("EmployeeCode  Should be Number ");
                document.forms[0].cutomerCode.focus();
            return false;
        }
   
   }  
	    
	    
	    
	    
	    if(document.forms[0].customerName.value=="")
	    {
	      alert("Please Enter Customer Name");
	      document.forms[0].customerName.focus();
	      return false;
	    }
	 if(document.forms[0].customerName.value!=""){
	 
	  var st = document.forms[0].customerName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].customerName.value=st;       

	    
	    }	    
	  if(document.forms[0].address1.value=="")
	    {
	      alert("Please Enter Address");
	      document.forms[0].address1.focus();
	      return false;
	    }
	     if(document.forms[0].address1.value!="")
	    {
	     var st = document.forms[0].address1.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].address1.value=st;       

	    
	    }
	    
 
   
   	    if(document.forms[0].city.value=="")
	    {
	      alert("Please Enter City");
	      document.forms[0].city.focus();
	      return false;
	    }
	    if(document.forms[0].city.value!=""){
	    
	    var st = document.forms[0].city.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].city.value=st; 
	  } 
   
      if(document.forms[0].countryId.value=="")
	    {
	      alert("Please Select Country");
	      document.forms[0].countryId.focus();
	      return false;
	    }
 if(document.forms[0].state.value=="")
	    {
	      alert("Please Select state");
	      document.forms[0].state.focus();
	      return false;
	    }
   
   if(document.forms[0].currencyId.value=="")
	    {
	      alert("Please Select Currency");
	      document.forms[0].currencyId.focus();
	      return false;
	    }
	    
	    
	    
	    
	    
	    if(document.forms[0].accGroupId.value=="IM")
	    {
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
   
   if(document.forms[0].pincode.value=="")
	    {
	      alert("Please Enter Pincode");
	      document.forms[0].pincode.focus();
	      return false;
	    }
	    if(document.forms[0].pincode.value!="")
	    {
	   var pincode = document.forms[0].pincode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(pincode)) {
             alert("PinCode  Should be Number ");
                document.forms[0].pincode.focus();
            return false;
        }
	    } 
	    if(document.forms[0].countryId.value=="")
	    {
	      alert("Please Select Country");
	      document.forms[0].countryId.focus();
	      return false;
	    }
 if(document.forms[0].state.value=="")
	    {
	      alert("Please Select state");
	      document.forms[0].state.focus();
	      return false;
	    }
	     if(document.forms[0].customerGroup.value=="")
	    {
	      alert("Please Select Customer Group");
	      document.forms[0].customerGroup.focus();
	      return false;
	    }
	    if(document.forms[0].priceGroup.value=="")
	    {
	      alert("Please Select Price Group");
	      document.forms[0].priceGroup.focus();
	      return false;
	    }
	     if(document.forms[0].priceList.value=="")
	    {
	      alert("Please Select Price List");
	      document.forms[0].priceList.focus();
	      return false;
	    }
	     if(document.forms[0].taxType.value=="")
	    {
	      alert("Please Select Tax Type");
	      document.forms[0].taxType.focus();
	      return false;
	    }
    if(document.forms[0].currencyId.value=="")
	    {
	      alert("Please Select Currency");
	      document.forms[0].currencyId.focus();
	      return false;
	    }
	    
	     if(document.forms[0].paymentTermID.value=="")
	    {
	      alert("Please Select Payment Term ");
	      document.forms[0].paymentTermID.focus();
	      return false;
	    }
	    
	    }
	    if(document.forms[0].accGroupId.value=="IN")
	    {
	    
	    	if(document.forms[0].city.value=="")
	    {
	      alert("Please Enter City");
	      document.forms[0].city.focus();
	      return false;
	    }
	    if(document.forms[0].city.value!=""){
	var st = document.forms[0].city.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].city.value=st ;
    }    
   
   if(document.forms[0].pincode.value=="")
	    {
	      alert("Please Enter Pincode");
	      document.forms[0].pincode.focus();
	      return false;
	    }
	    if(document.forms[0].pincode.value!="")
	    {
	   var pincode = document.forms[0].pincode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(pincode)) {
             alert("PinCode  Should be Number ");
                document.forms[0].pincode.focus();
            return false;
        }
	    } 
	    
	    if(document.forms[0].countryId.value=="")
	    {
	      alert("Please Select Country");
	      document.forms[0].countryId.focus();
	      return false;
	    }
 if(document.forms[0].state.value=="")
	    {
	      alert("Please Select state");
	      document.forms[0].state.focus();
	      return false;
	    }
	     if(document.forms[0].customerGroup.value=="")
	    {
	      alert("Please Select Customer Group");
	      document.forms[0].customerGroup.focus();
	      return false;
	    }
	    if(document.forms[0].priceGroup.value=="")
	    {
	      alert("Please Select Price Group");
	      document.forms[0].priceGroup.focus();
	      return false;
	    }
	     if(document.forms[0].priceList.value=="")
	    {
	      alert("Please Select Price List");
	      document.forms[0].priceList.focus();
	      return false;
	    }
	     if(document.forms[0].taxType.value=="")
	    {
	      alert("Please Select Tax Type");
	      document.forms[0].taxType.focus();
	      return false;
	    }
	    
	    
    if(document.forms[0].currencyId.value=="")
	    {
	      alert("Please Select Currency");
	      document.forms[0].currencyId.focus();
	      return false;
	    }
	    
	     if(document.forms[0].paymentTermID.value=="")
	    {
	      alert("Please Select Payment Term ");
	      document.forms[0].paymentTermID.focus();
	      return false;
	    }
	    
	       if(document.forms[0].tinNumber.value=="")
	    {
	      alert("Please Enter TIN NO.");
	      document.forms[0].tinNumber.focus();
	      return false;
	    }
	       if(document.forms[0].tinNumber.value!="")
	    {
	var st = document.forms[0].tinNumber.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].tinNumber.value=st;
	    } 
	     
	    	   if(document.forms[0].isRegdExciseVender.value=="")
	    {
	      alert("Please Select Is Regd Excise Customer");
	      document.forms[0].isRegdExciseVender.focus();
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
	document.forms[0].exciseDivision.value=st ;
	    
	    if(document.forms[0].isRegdExciseVender.value=="True")
	    {
		      if(document.forms[0].eccNo.value=="")
		    {
		      alert("Please Enter ECC No  ");
		      document.forms[0].eccNo.focus();
		      return false;
		    }
	var st = document.forms[0].eccNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].eccNo.value=st;
	
	
			  if(document.forms[0].exciseRegNo.value=="")
		    {
		      alert("Please Enter Excise Reg Number ");
		      document.forms[0].exciseRegNo.focus();
		      return false;
		    }
		     var st = document.forms[0].exciseRegNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exciseRegNo.value=st;
	
		    if(document.forms[0].exciseRange.value=="")
		    {
		      alert("Please Enter Excise Range ");
		      document.forms[0].exciseRange.focus();
		      return false;
		    }
		 var st = document.forms[0].exciseRange.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exciseRange.value=st;
	
			  if(document.forms[0].exciseDivision.value=="")
		    {
		      alert("Please Enter exciseDivision ");
		      document.forms[0].exciseDivision.focus();
		      return false;
		    }
		    if(document.forms[0].exciseDivision.value!="")
		    {
		     var st = document.forms[0].exciseDivision.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exciseDivision.value=st ;
	    }
	    
	    }
	    
	          if(document.forms[0].dlno1.value=="")
	    {
	      alert("Please Enter DL NO.1");
	      document.forms[0].dlno1.focus();
	      return false;
	    }
	       if(document.forms[0].dlno1.value!="")
	    {
 var st = document.forms[0].dlno1.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].dlno1.value=st;
	    }
	    
	     if(document.forms[0].dlno2.value=="")
	    {
	      alert("Please Enter DL NO.2");
	      document.forms[0].dlno2.focus();
	      return false;
	    }
	       if(document.forms[0].dlno2.value!="")
	    {
	 var st = document.forms[0].dlno2.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].dlno2.value=st;
	    }
   
   
   
   
   
	    }
	    
		 
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
	    
	    
	   if(document.forms[0].city.value!="")
	    {
	    var st = document.forms[0].city.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].city.value=st;
	    }
	    
	    if(document.forms[0].pincode.value!="")
	    {
	   var pincode = document.forms[0].pincode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(pincode)) {
             alert("Pincode  Should be Number ");
                document.forms[0].pincode.focus();
            return false;
        }
	    }
		 if(document.forms[0].countryId.value=="")
	    {
	      alert("Please Select Country");
	      document.forms[0].countryId.focus();
	      return false;
	    }
	       
	        if(document.forms[0].landlineNo.value!="")
	    {
	   var landlineNo = document.forms[0].landlineNo.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(landlineNo)) {
             alert("Landline Should be Number ");
                document.forms[0].landlineNo.focus();
            return false;
        }
	    } 
	    
	    if(document.forms[0].mobileNo.value!="")
	    {
	   var mobileNo = document.forms[0].mobileNo.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(mobileNo)) {
             alert("MobileNo  Should be Number ");
                document.forms[0].mobileNo.focus();
            return false;
        }
	    }
	    if(document.forms[0].faxNo.value!="")
	    {
	   var faxNo = document.forms[0].faxNo.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(faxNo)) {
             alert("FaxNo Should be Number ");
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
	    
	     if(document.forms[0].domestic.checked==false && document.forms[0].exports.checked==false)
	    {
	      alert("Please Select Atleast One checkbox");
	     
	      return false;
	    }
	    
	    if(document.forms[0].state.value=="")
	    {
	      alert("Please Select state");
	      document.forms[0].state.focus();
	      return false;
	    }
	     if(document.forms[0].accGroupId.value!="FS")
	    {
	    if(document.forms[0].customerGroup.value=="")
	    {
	      alert("Please Select Customer Group");
	      document.forms[0].customerGroup.focus();
	      return false;
	    }
	    if(document.forms[0].priceGroup.value=="")
	    {
	      alert("Please Select Price Group");
	      document.forms[0].priceGroup.focus();
	      return false;
	    }
	     if(document.forms[0].priceList.value=="")
	    {
	      alert("Please Select Price List");
	      document.forms[0].priceList.focus();
	      return false;
	    }
	     if(document.forms[0].taxType.value=="")
	    {
	      alert("Please Select Tax Type");
	      document.forms[0].taxType.focus();
	      return false;
	    }
	    
	    
	    }
	    if(document.forms[0].tdsStatus.value=="True")
	    {
		      if(document.forms[0].tdsCode.value=="")
		    {
		      alert("Please Select TdsCode ");
		      document.forms[0].tdsCode.focus();
		      return false;
		    }
		    
		    
	    }
	    
	    
	    if(document.forms[0].listNumber.value!="")
	    {
	    var st = document.forms[0].listNumber.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].listNumber.value=st;
	    }
	    
	    
	       if(document.forms[0].tinNumber.value!="")
	    {
	 var st = document.forms[0].tinNumber.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].tinNumber.value=st;
	    }
	    
	    
	        if(document.forms[0].cstNumber.value!="")
	    {
	    var st = document.forms[0].cstNumber.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].cstNumber.value=st;
	    }
	    
	    
	    if(document.forms[0].panNumber.value!="")
	    {
	   var st = document.forms[0].panNumber.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].panNumber.value=st;
	    }
	   if(document.forms[0].isRegdExciseVender.value=="")
	    {
	      alert("Please Select Is Regd Excise Customer");
	      document.forms[0].isRegdExciseVender.focus();
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
	document.forms[0].exciseDivision.value=st ;
	    
	     if(document.forms[0].isRegdExciseVender.value=="True")
	    {
		      if(document.forms[0].eccNo.value=="")
		    {
		      alert("Please Enter ECC No  ");
		      document.forms[0].eccNo.focus();
		      return false;
		    }
		    var st = document.forms[0].eccNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].eccNo.value=st;
	
			  if(document.forms[0].exciseRegNo.value=="")
		    {
		      alert("Please Enter Excise Reg Number ");
		      document.forms[0].exciseRegNo.focus();
		      return false;
		    }
		     var st = document.forms[0].exciseRegNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exciseRegNo.value=st;
	
		    if(document.forms[0].exciseRange.value=="")
		    {
		      alert("Please Enter Excise Range ");
		      document.forms[0].exciseRange.focus();
		      return false;
		    }
		    var st = document.forms[0].exciseRange.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exciseRange.value=st;
	
			  if(document.forms[0].exciseDivision.value=="")
		    {
		      alert("Please Enter exciseDivision ");
		      document.forms[0].exciseDivision.focus();
		      return false;
		    }
		    if(document.forms[0].exciseDivision.value!="")
		    {
		var st = document.forms[0].exciseDivision.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exciseDivision.value=st;
	    }
	    }
	
	    if(document.forms[0].dlno1.value!=""){
	var st = document.forms[0].dlno1.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].dlno1.value=st;
	    
	    }
	    if(document.forms[0].dlno2.value!=""){
	    
	     var st = document.forms[0].dlno2.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].dlno2.value=st;
	    
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
	
	var reqId = document.forms[0].requestNo.value;
		var reqType = "";
	var matGroup="";
	var location="";
	var url="approvals.do?method=statusChangeForCustomerRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
	document.forms[0].action=url;
	document.forms[0].submit();
}

function changeDetails(){


var account=document.forms[0].accGroupId.value;

if(account=="IN"){
document.getElementById("city").style.visibility="visible";
document.getElementById("pin").style.visibility="visible";
document.getElementById("currency").style.visibility="visible";
document.getElementById("payterm").style.visibility="visible";
document.getElementById("tin").style.visibility="visible";
document.getElementById("dl1").style.visibility="visible";
document.getElementById("dl2").style.visibility="visible";
document.getElementById("priclist").style.visibility="visible";
document.getElementById("empcode").style.visibility="hidden";
document.getElementById("custgrp").style.visibility="visible";
document.getElementById("pricgrp").style.visibility="visible";
document.getElementById("taxtyp").style.visibility="visible";


}
if(account!="IN"){

document.getElementById("city").style.visibility="hidden";
document.getElementById("pin").style.visibility="hidden";
document.getElementById("currency").style.visibility="hidden";
document.getElementById("payterm").style.visibility="hidden";
document.getElementById("tin").style.visibility="hidden";
document.getElementById("dl1").style.visibility="hidden";
document.getElementById("dl2").style.visibility="hidden";
document.getElementById("priclist").style.visibility="visible";
document.getElementById("empcode").style.visibility="hidden";
document.getElementById("custgrp").style.visibility="visible";
document.getElementById("pricgrp").style.visibility="visible";
document.getElementById("taxtyp").style.visibility="visible";



}
if(account=="IM"){
document.getElementById("city").style.visibility="visible";
document.getElementById("pin").style.visibility="visible";
document.getElementById("currency").style.visibility="visible";
document.getElementById("payterm").style.visibility="visible";


}
if(account=="FS"){
document.getElementById("empcode").style.visibility="visible";
document.getElementById("city").style.visibility="visible";
document.getElementById("currency").style.visibility="visible";
document.getElementById("priclist").style.visibility="hidden";
document.getElementById("custgrp").style.visibility="hidden";
document.getElementById("pricgrp").style.visibility="hidden";
document.getElementById("taxtyp").style.visibility="hidden";


}




if(account=="")
{
document.getElementById("empcode").style.visibility="hidden";
document.getElementById("city").style.visibility="hidden";
document.getElementById("pin").style.visibility="hidden";
document.getElementById("currency").style.visibility="hidden";
document.getElementById("payterm").style.visibility="hidden";
document.getElementById("tin").style.visibility="hidden";
document.getElementById("dl1").style.visibility="hidden";
document.getElementById("dl2").style.visibility="hidden";
document.getElementById("priclist").style.visibility="visible";
document.getElementById("custgrp").style.visibility="visible";
document.getElementById("pricgrp").style.visibility="visible";
document.getElementById("taxtyp").style.visibility="visible";




}

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

function getStates(){	   
		
			var url="approvals.do?method=getCustomerStates";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}
</script>
<body onload="changeDetails()"  style="text-transform: uppercase;">
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
				<html:text property="requestDate" styleId="requestDate" onfocus="popupCalender('requestDate')" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td rowspan="1">Account Group</td>
			<td rowspan="1"><html:select property="accGroupId" onchange="changeDetails()">
					<html:option value="">--Select--</html:option>
					<html:option value="IN">Domestic Regular</html:option>
					<html:option value="IM">Export Customer</html:option>
					<html:option value="LL">Loan-Licence</html:option>
					<html:option value="FS">Field Staff</html:option>
					<html:option value="007">Plants</html:option>
				</html:select>
			</td>
			<td>View Type</td>
			<td>Sales <html:checkbox property="sales"/>
				Accounts <html:checkbox property="accounts"/>
			</td>
		</tr>
		<tr>
			<td>Customer Type <font color="red">*</font></td>
			<td>Domestic <html:checkbox property="domestic"/> 
				Exports <html:checkbox property="exports"  />
			</td>
			<td>Employee Code<div style="visibility: hidden" id="empcode"><font color="red">*</font></div></td>
			<td><html:text property="cutomerCode"></html:text>
		</tr>
		<tr>
			<td>Name <font color="red">*</font></td>
			<td colspan="3"><html:text property="customerName" maxlength="40" size="65" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>Address1 <font color="red">*</font></td>
			<td><html:text property="address1" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>Address2</td>
			<td><html:text property="address2" maxlength="40" size="45" title="Maximum of 40 characters"  style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>Address3</td>
			<td><html:text property="address3" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>Address4</td>
			<td><html:text property="address4" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
						<td>City <div style="visibility: hidden" id="city"><font color="red">*</font></div></td>

			<td><html:text property="city" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>Pincode<div style="visibility: hidden" id="pin"><font color="red">*</font></div></td>
			<td><html:text property="pincode" maxlength="6" size="10" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>Country <font color="red">*</font></td>
			<td><html:select property="countryId" onchange="getStates()">
					<html:option value="">--Select--</html:option>
					<html:options property="counID" labelProperty="countryName" />
				</html:select>
			</td>

			<logic:empty name="diplayStates">
				<td>State <font color="red">*</font></td>
				<td><html:select property="state">
						<html:option value="">--Select--</html:option>
					</html:select>
				</td>
			</logic:empty>
	
			<logic:notEmpty name="diplayStates">
				<td>State <font color="red">*</font></td>
				<td><html:select property="state">
						<html:option value="">--Select--</html:option>
						<html:options property="stateId" labelProperty="states" />
					</html:select>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>

			<td>Landline</td>
			<td><html:text property="landlineNo" maxlength="20" size="20" style="text-transform:uppercase"/></td>
			<td>Mobile</td>
			<td><html:text property="mobileNo" maxlength="20" size="20" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>Fax</td>
			<td><html:text property="faxNo" maxlength="20" size="20" style="text-transform:uppercase"/></td>
			<td>e-Mail</td>
			<td><html:text property="emailId" maxlength="20" size="20" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
	 		<th colspan="4"><big>Sales</big></th>
   		</tr>
		<tr>
			<td>Customer Group <div style="visibility: visible" id="custgrp"><font color="red">*</font></td>
			<td><html:select property="customerGroup">
				<html:option value="">--Select--</html:option>
				<html:options name="approvalsForm" property="cusGroupID" labelProperty="cusGroupList"/>
				</html:select>
			</td>
			<td>Price Group <div style="visibility: visible" id="pricgrp"><font color="red">*</font></td>
			<td><html:select property="priceGroup">
					<html:option value="">--Select--</html:option>
					<html:options name="approvalsForm" property="priceGroupID" labelProperty="piceGroupList"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td>Price List <div style="visibility: visible" id="priclist"><font color="red">*</font></td>
			<td><html:select property="priceList">
					<html:option value="">--Select--</html:option>
					<html:options name="approvalsForm" property="priceListID" labelProperty="piceListValue"/>
				</html:select>
			</td>
			<td>Tax Type <div style="visibility: visible" id="taxtyp"><font color="red">*</font></td>
			<td><html:select property="taxType">
					<html:option value="">--Select--</html:option>
					<html:options name="approvalsForm" property="taxTypeID" labelProperty="taxTypeValue"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td>Currency<div id="currency" style="visibility: hidden"><font color="red">*</font></td>
			<td><html:select property="currencyId">
					<html:option value="">--Select--</html:option>
					<html:options property="currenIds" labelProperty="currencys" />
				</html:select>
			</td>
			<td>Payment Term<div id="payterm" style="visibility: hidden"><font color="red">*</font></td>
			<td>
			<html:select name="approvalsForm" property="paymentTermID">
						<html:option value="">--Select--</html:option>
			<html:options name="approvalsForm" property="paymentTermList" labelProperty="paymentTermLabelList"/>
						
					</html:select>
					</td>
		</tr>
		<tr>
			<td>Account Clerk</td>
			<td colspan="3">
			
			<html:select name="approvalsForm" property="accountClerkId" >
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
			<td><html:select property="tdsStatus" onchange="getTDSState(this.value)">
					<html:option value="">--Select--</html:option>
					<html:option value="True">Yes</html:option>
					<html:option value="False">No</html:option>
				</html:select>
			</td>

			<logic:empty name="setTdsState">
				<td>TDS Code</td>
				<td><html:select property="tdsCode">
						<html:option value="">--Select--</html:option>
						<html:options property="tdsIds" labelProperty="tdsCodes" />
					</html:select>
				</td>
			</logic:empty>

			<logic:notEmpty name="setTdsState">
				<td>TDS Code <font color="red">*</font></td>
				<td><html:select property="tdsCode" styleClass="text_field">
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
			<td><html:text property="listNumber" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>Tin No.<div style="visibility: hidden" id="tin"><font color="red">*</font></div></td>
			<td><html:text property="tinNumber" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>CST No.</td>
			<td><html:text property="cstNumber" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>PAN No.</td>
			<td><html:text property="panNumber" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
		</tr>
		<tr>
			<td>Service Tax Reg. No. </td>
			<td><html:text property="serviceTaxNo" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>Is Reg.Excise Customer <font color="red">*</font></td>
			<td><html:select property="isRegdExciseVender" onchange="getREVState(this.value)">
					<html:option value="">-----Select-----</html:option>
					<html:option value="True">Yes</html:option>
					<html:option value="False">No</html:option>
				</html:select>
			</td>
		</tr>

		<logic:notEmpty name="setRegdExciseVenderNotMandatory">
			<tr>
				<td>ECC No</td>
				<td><html:text property="eccNo" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
				<td>Excise Reg. No.</td>
				<td><html:text property="exciseRegNo" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			</tr>
			<tr>
				<td>Excise Range</td>
				<td><html:text property="exciseRange" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
				<td>Excise Division</td>
				<td><html:text property="exciseDivision" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			</tr>	
		</logic:notEmpty>

		<logic:notEmpty name="setRegdExciseVender">
			<tr>
				<td>ECC No <font color="red">*</font></td>
				<td><html:text property="eccNo" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
				<td>Excise Reg. No. <font color="red">*</font></td>
				<td><html:text property="exciseRegNo" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			</tr>
			<tr>
				<td>Excise Range <font color="red">*</font></td>
				<td><html:text property="exciseRange" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
				<td>Excise Division <font color="red">*</font></td>
				<td><html:text property="exciseDivision" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			</tr>	
		</logic:notEmpty>

		<tr>
			<td>DL No.1 <div style="visibility: hidden" id="dl1"><font color="red">*</font></div></td>
			<td><html:text property="dlno1" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
			<td>DL No.2 <div style="visibility: hidden" id="dl2"><font color="red">*</font></div></td>
			<td><html:text property="dlno2" maxlength="40" size="45" title="Maximum of 40 characters" style="text-transform:uppercase"/></td>
		</tr>

		<logic:notEmpty name="approved">
			<tr>
				<td>Approve Type</td>
				<td align="left">
					<html:select name="approvalsForm" property="approveType" styleClass="text_field">
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
					<td colspan="4"><a href="/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles/<%=u%>" target="_blank"><%=u%></a></td>
				
				</tr>
					<%
					}
					%>		
			</logic:iterate>
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
				<td>	<html:text property="sapCodeNo" styleClass="text_field" style="width:200px;" maxlength="18"></html:text></td>
			
			</tr>
			
			<tr>	
			<td >SAP Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<html:text property="sapCreationDate" styleId="sapCreationDate"  readonly="true" styleClass="text_field"/></td>
		
				<td >SAP Created By<font color="red">*</font></td>
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
		</html:form>
</body>
			