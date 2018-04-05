<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:directive.page import="com.microlabs.login.dao.LoginDao" />
<jsp:directive.page import="java.sql.ResultSet" />
<jsp:directive.page import="java.sql.SQLException" />
<jsp:directive.page import="java.util.ArrayList" />
<jsp:directive.page import="java.util.Iterator" />
<jsp:directive.page import="java.util.LinkedHashMap" />
<jsp:directive.page import="java.util.Set" />
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="com.microlabs.utilities.IdValuePair" />
<jsp:directive.page import="com.microlabs.ess.form.JoiningReportForm" />

<link rel="stylesheet" type="text/css" href="css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="css/TableCSS.css" />

<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../jquery.jscrollpane.css" />
<script type="text/javascript" src="../jquery.jscrollpane.min.js"></script>
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css" />

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="style1/inner_tbl.css" />

<%--<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>--%>

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%--<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>--%>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Microlab</title>

 <style type="text/css">
   th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>
<%--     Calender   --%>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>

<script type="text/javascript" src="js/jquery.min.js"></script>

<script type="text/javascript" src="js/jquery.datepick.js"></script>

<script type="text/javascript">
$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#dateOfIssuepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});


$(function() {
	$('#dateOfBirthpicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#dateOfExpirypicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#familydateofBirthID').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#eduFromDtId').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#eduToDtId').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#expStDateID').datepick({dateFormat: 'mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});


$(function() {
	$('#expEndDateID').datepick({dateFormat: 'mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#dateofjoinpicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});


$.datepicker.setDefaults( $.datepicker.regional["es"] );
$("#expEndDateID").datepicker(
{
    shortYearCutoff: 1,
    changeMonth: true,
    changeYear: true,
    dateFormat: 'dd-mm-yy',
    minDate: "-70Y", 
    maxDate: "-15Y",
    yearRange: "1942:1997"
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>

<%--     Calender   --%>

		<script type="text/javascript">

function MM_preloadImages() { 
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function subMenuClicked(id){
		
		var disp=document.getElementById(id);
		
		if(disp.style.display==''){
			disp.style.display='none';
			document.getElementById("mailTe").src = "images/left_menu/up_arrow.png";
			document.getElementById("mail12").className = "mail";
		}
		else{
			disp.style.display=''; 
			document.getElementById("mailTe").src = "images/left_menu/down_arrow.png";
			document.getElementById("mail12").className = "mailhover";
		}
	}
	
  function resizeIframe(obj) {
  
  if((obj.contentWindow.document.body.scrollHeight)<378){
  obj.style.height ='378px';
  }else{
  obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
  }
  
  }
  
  
   function popupCalender(param)
	  {
	      var cal = new Zapatec.Calendar.setup({
	      inputField     :     param,     // id of the input field
	      singleClick    :     true,     // require two clicks to submit
	      ifFormat       :    "%d/%m/%Y",     // format of the input field
	      showsTime      :     false,     // show time as well as date
	      button         :    "button2"  // trigger button 
	      });
	  }
  

	
function onSubmit(){

               		
	var url="essJoin.do?method=submit";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function parseDate(str) {
    var mdy = str.split('/');
    return new Date(mdy[2], mdy[1]-1, mdy[0]);
}

function  onSave(reqid)
{

if(document.forms[0].title.value=="")
	    {
	      alert("Title should not be left blank");
	      document.forms[0].title.focus();
	      return false;
	    }
	    
	    
if(document.forms[0].firstName.value=="")
{
  alert("FirstName should not be left blank");
  document.forms[0].firstName.focus();
  return false;
}


if(document.forms[0].lastName.value=="")
{
  alert("Last Name should not be left blank");
  document.forms[0].lastName.focus();
  return false;
}
if(document.forms[0].gender.value=="")
{
  alert("Please select gender");
  document.forms[0].gender.focus();
  return false;
}

if(document.forms[0].dateofjoin.value=="")
{
  alert("Please select Date Of Join");
  document.forms[0].dateofjoin.focus();
  return false;
}


if(document.forms[0].designation.value=="")
{
  alert("Please select designation");
  document.forms[0].designation.focus();
  return false;
}


if(document.forms[0].department.value=="")
{
  alert("Please select department");
  document.forms[0].department.focus();
  return false;
}


if(document.forms[0].location.value=="")
{
  alert("Please select Work location");
  document.forms[0].location.focus();
  return false;
}


if(document.forms[0].dateofBirth.value=="")
{
  alert("Please select date of birth");
  document.forms[0].dateofBirth.focus();
  return false;
}
	  
	      
	     if(document.forms[0].nickName.value!=""){
	     
	var st = document.forms[0].nickName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].nickName.value=st; 	    
	    
	    }

	    
	    
	
	    
	   if(document.forms[0].maritalStatus.value=="")
	    {
	      alert("Marital Status should not be left blank");
	      document.forms[0].maritalStatus.focus();
	      return false;
	    }
	     
	 
	    
	   if(document.forms[0]. birthplace.value!=""){
var st = document.forms[0].birthplace.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].birthplace.value=st;
	  }
	      
	   if(document.forms[0].caste.value!=""){
	var st = document.forms[0].caste.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].caste.value=st;
	  }
	    if(document.forms[0].nationality.value=="")
	    {
	      alert("Nationality should not be left blank");
	      document.forms[0].nationality.focus();
	      return false;
	    }
	     
	     if(document.forms[0].telephoneNumber.value!="")
	    {
	   
	    
	     var telephoneNumber = document.forms[0].telephoneNumber.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(telephoneNumber)) {
             alert("TelephoneNumber  Should be Integer ");
                document.forms[0].telephoneNumber.focus();
            return false;
        }
        }
	     
	     if(document.forms[0].mobileNumber.value=="")
		    {
		      alert("Enter Mobile no.");
		      document.forms[0].mobileNumber.focus();
		      return false;
		    }
	     
	        if(document.forms[0].mobileNumber.value!="")
	    {
	   
	    
	     var mobileNumber = document.forms[0].mobileNumber.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(mobileNumber)) {
             alert("Mobile Number  Should be Integer ");
                document.forms[0].mobileNumber.focus();
            return false;
        }
        
        	if (mobileNumber.length!=10)
           {
              alert("MobileNumber  Should be 10 Digits ");
                document.forms[0].mobileNumber.focus();
            return false;
           }
        }
        	  if(document.forms[0].emailAddress.value=="")
	    {
	      alert("PersonalEmail should not be left blank");
	      document.forms[0].emailAddress.focus();
	      return false;
	    }
	     
	   if(document.forms[0].emailAddress.value!=""){
var st = document.forms[0].emailAddress.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].emailAddress.value=st;    
	    
	    }
	    
	    	  if(document.forms[0].panno.value=="")
	    {
	      alert("PAN No should not be left blank");
	      document.forms[0].panno.focus();
	      return false;
	    }
	    if(document.forms[0].panno.value!=""){
var pan = document.forms[0].panno.value;
var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;

if(!regpan.test(pan))
{

 alert("Invalid PAN No ");
                document.forms[0].panno.focus();
            return false;

}

	    }
        
	      if(document.forms[0].passportNumber.value!="")
	    {
	   var st = document.forms[0].passportNumber.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].passportNumber.value=st; 
	     
		        if(document.forms[0].placeofIssueofPassport.value=="")
		    {
		      alert("Place Of IssuePassport should not be left blank");
		      document.forms[0].placeofIssueofPassport.focus();
		      return false;
		    }
		    if(document.forms[0].placeofIssueofPassport.value!="")
	    {
	    	   var st = document.forms[0].placeofIssueofPassport.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].placeofIssueofPassport.value=st; 
}
		        if(document.forms[0].dateofissueofPassport.value=="")
		    {
		      alert("Date Of Issue Passport  should not be left blank");
		      document.forms[0].dateofissueofPassport.focus();
		      return false;
		    }
		        if(document.forms[0].dateofexpiryofPassport.value=="")
		    {
		      alert("Date Of Expiry Passport  should not be left blank");
		      document.forms[0].dateofexpiryofPassport.focus();
		      return false;
		    }
		    
		   if(document.forms[0].dateofissueofPassport.value!="" &&document.forms[0].dateofexpiryofPassport.value!="")
		   {
		   var startDate=document.forms[0].dateofissueofPassport.value;
		  
		   var endDate=document.forms[0].dateofexpiryofPassport.value;

 var str1 = document.forms[0].dateofissueofPassport.value;
var str2 = document.forms[0].dateofexpiryofPassport.value;
var dt1  = parseInt(str1.substring(0,2),10); 
var mon1 = parseInt(str1.substring(3,5),10); 
var yr1  = parseInt(str1.substring(6,10),10); 
var dt2  = parseInt(str2.substring(0,2),10); 
var mon2 = parseInt(str2.substring(3,5),10); 
var yr2  = parseInt(str2.substring(6,10),10); 
var date1 = new Date(yr1, mon1, dt1); 
var date2 = new Date(yr2, mon2, dt2); 

if(date2 < date1) 
{ 
    alert("Passport expiry date cannot be greater than from start date"); 
    return false; 
} 
else 
{ 
     
    
} 
		    
		   }
	    }
	    
	     if(document.forms[0].physicallyChallenged.value=="Yes")
	    {
	  
	      if(document.forms[0].physicallyChallengeddetails.value==""){
	      alert("Physically Challenged Details should not be left blank");
	      document.forms[0].physicallyChallengeddetails.focus();
	      return false;
	      }
	      if(document.forms[0].physicallyChallengeddetails.value!=""){
	//var st = document.forms[0].physicallyChallengeddetails.value;
	//var Re = new RegExp("\\'","g");
	//st = st.replace(Re,"`");
	//document.forms[0].physicallyChallengeddetails.value=st;
}
	      
	    }
	    if(document.forms[0].personalIdentificationMarks.value!=""){
	var st = document.forms[0].personalIdentificationMarks.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].personalIdentificationMarks.value=st;
}
	    
	      if(document.forms[0].emergencyContactPersonName.value==""){
	      
	      alert("Emergency Contact Person Name should not be left blank");
	      document.forms[0].emergencyContactPersonName.focus();
	      return false;
	      }
	    
	    if(document.forms[0].emergencyContactPersonName.value!=""){
	       	var st = document.forms[0].emergencyContactPersonName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].emergencyContactPersonName.value=st;
}

	       if(document.forms[0].emergencyContactAddressLine1.value=="")
	       {
	        alert("Emergency Contact AddressLine 1 should not be left blank");
	      document.forms[0].physicallyChallengeddetails.focus();
	      return false;
	       
	       }
	       if(document.forms[0].emergencyContactAddressLine1.value!=""){
	      var st = document.forms[0].emergencyContactAddressLine1.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].emergencyContactAddressLine1.value=st;
}
	  	       if(document.forms[0].emergencyContactAddressLine2.value!=""){
	var st = document.forms[0].emergencyContactAddressLine2.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].emergencyContactAddressLine2.value=st;
}  
 
 
 if(document.forms[0].emergencyTelephoneNumber.value!="")
	    {
	   var emergencyTelephoneNumber = document.forms[0].emergencyTelephoneNumber.value;
        var pattern =/^\d+(\d+)?$/
        if (!pattern.test(emergencyTelephoneNumber)) {
             alert("Emergency TelephoneNumber  Should be Integer ");
                document.forms[0].emergencyTelephoneNumber.focus();
            return false;
        }
        }
        
        if(document.forms[0].emergencyMobileNumber.value=="")
	    {
	     alert("Emergency Mobile Number should not be left blank");
	      document.forms[0].emergencyMobileNumber.focus();
	      return false;
	    }
		if(document.forms[0].emergencyMobileNumber.value!="")
	    {
	     var emergencyMobileNumber = document.forms[0].emergencyMobileNumber.value;
       var pattern = /^\d+(\d+)?$/
        if (!pattern.test(emergencyMobileNumber)) {
             alert("Emergency Mobile Number  Should be Integer ");
                document.forms[0].emergencyMobileNumber.focus();
            return false;
        }
        }
	    
	    //emergency 2
	    
	    
	    if(document.forms[0].emergencyContactPersonName1.value!=""){
	       	var st = document.forms[0].emergencyContactPersonName1.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].emergencyContactPersonName1.value=st;
}

	     
	       if(document.forms[0].emergCntAdd11.value!=""){
	      var st = document.forms[0].emergCntAdd11.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].emergCntAdd11.value=st;
}
	  	       if(document.forms[0].emergCntAdd111.value!=""){
	var st = document.forms[0].emergCntAdd111.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].emergCntAdd111.value=st;
}  
 
 
 if(document.forms[0].emergencyTelephoneNumber1.value!="")
	    {
	   var emergencyTelephoneNumber1 = document.forms[0].emergencyTelephoneNumber1.value;
        var pattern =/^\d+(\d+)?$/
        if (!pattern.test(emergencyTelephoneNumber1)) {
             alert("Emergency TelephoneNumber  Should be Integer ");
                document.forms[0].emergencyTelephoneNumber1.focus();
            return false;
        }
        }
        
    
if(document.forms[0].emergencyMobileNumber1.value!="")
	    {
	     var emergencyMobileNumber1 = document.forms[0].emergencyMobileNumber1.value;
       var pattern = /^\d+(\d+)?$/
        if (!pattern.test(emergencyMobileNumber1)) {
             alert("Emergency MobileNumber  Should be Integer ");
                document.forms[0].emergencyMobileNumber1.focus();
            return false;
        }
        }
	    
	
<%--	    else if(document.forms[0].bloodGroup.value=="")--%>
<%--	    {--%>
<%--	      alert("Blood Group should not be left blank");--%>
<%--	      document.forms[0].bloodGroup.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--       else if(document.forms[0].physicallyChallenged.value=="")--%>
<%--	    {--%>
<%--	      alert("Physically Challenged should not be left blank");--%>
<%--	      document.forms[0].physicallyChallenged.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
	    
<%--	    else if(document.forms[0].emergencyContactPersonName.value=="")--%>
<%--	    {--%>
<%--	      alert("Emergency Contact PersonName should not be left blank");--%>
<%--	      document.forms[0].emergencyContactPersonName.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	    else if(document.forms[0].emergencyContactAddressLine1.value=="")--%>
<%--	    {--%>
<%--	      alert("Emergency Contact AddressLine1 should not be left blank");--%>
<%--	      document.forms[0].emergencyContactAddressLine1.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
	    else
	    {
	    }
	    
<%--	       var mobile = document.forms[0].emergencyTelephoneNumber.value;--%>
<%--        var pattern = /^[0-9\+]{9,15}$/;--%>
<%--        if (!pattern.test(mobile)) {--%>
<%--             alert("Emergency Telephone Number is not valid!");--%>
<%--            return false;--%>
<%--        } --%>
<%--        --%>
<%--         var telephn = document.forms[0].emergencyMobileNumber.value;--%>
<%--        var pattern = /^[0-9\+]{5,10}$/;--%>
<%--        if (!pattern.test(telephn)) {--%>
<%--             alert("Emergency Mobile Number is not valid!");--%>
<%--            return false;--%>
<%--        }   --%>
	    
	var url="essJoin.do?method=savePersonalInfo&id="+reqid;
	document.forms[0].action=url;
	document.forms[0].submit();
	
}	

function addAddress(param,reqid)
{
 	if(document.forms[0].addressType.value=="")
	    {
	      alert("Please Select Address Type");
	      document.forms[0].addressType.focus();
	      return false;
	    }
	    
	   if(document.forms[0].careofcontactname.value!="")
	   {
	    var st = document.forms[0].careofcontactname.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].careofcontactname.value=st;
}  
	   
    
    
    if(document.forms[0].houseNumber.value=="")
	    {
	      alert("House Number should not be left blank");
	      document.forms[0].houseNumber.focus();
	      return false;
	    }
	    if(document.forms[0].houseNumber.value!=""){
var st = document.forms[0].houseNumber.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].houseNumber.value=st;
}  
	    
	    
	     if(document.forms[0].addressLine1.value=="")
	    {
	      alert("AddressLine1 should not be left blank");
	      document.forms[0].addressLine1.focus();
	      return false;
	    }
	     if(document.forms[0].addressLine1.value!=""){
var st = document.forms[0].addressLine1.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].addressLine1.value=st;
}  
 if(document.forms[0].addressLine2.value!=""){
var st = document.forms[0].addressLine2.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].addressLine2.value=st;
}  
if(document.forms[0].addressLine3.value!=""){
var st = document.forms[0].addressLine3.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].addressLine3.value=st;
}  
	    if(document.forms[0].postalCode.value=="")
	    {
	      alert("Postal Code should not be left blank");
	      document.forms[0].postalCode.focus();
	      return false;
	    }
	    if(document.forms[0].postalCode.value!=""){
	        var postalCode = document.forms[0].postalCode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(postalCode)
        ) {
             alert("PostalCode Should be Integer ");
             document.forms[0].postalCode.focus();
             return false;
        }
	    }
	     if(document.forms[0].city.value=="")
	    {
	      alert("City should not be left blank");
	      document.forms[0].city.focus();
	      return false;
	    }
	    if(document.forms[0].city.value!=""){
var st = document.forms[0].city.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].city.value=st;
}  
      if(document.forms[0].country.value=="")
	    {
	      alert("Country should not be left blank");
	      document.forms[0].country.focus();
	      return false;
	    }
      if(document.forms[0].state.value=="")
	    {
	      alert("State should not be left blank");
	      document.forms[0].state.focus();
	      return false;
	    }
 

	var url="essJoin.do?method=saveAddress&param="+param+"&id="+reqid;
	document.forms[0].action=url;
	document.forms[0].submit();
	
}	
function selectTempAddress(param)
{
	var url="essJoin.do?method=selectTempAddress&param="+param;
	document.forms[0].action=url;
	document.forms[0].submit();
	
}
function addFamily(param,reqid)
{


	     if(document.forms[0].familyDependentTypeID.value=="")
	    {
	      alert("Relationship should not be left blank");
	      document.forms[0].familyDependentTypeID.focus();
	      return false;
	    }
	   
	  if(document.forms[0].ftitle.value=="")
	    {
	      alert("Title should not be left blank");
	      document.forms[0].ftitle.focus();
	      return false;
	    }
	     if(document.forms[0].ffirstName.value=="")
	    {
	      alert("First Name (Family Details) should not be left blank");
	      document.forms[0].ffirstName.focus();
	      return false;
	    }
	     if(document.forms[0].ffirstName.value!=""){
var st = document.forms[0].ffirstName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].ffirstName.value=st;
}  
  if(document.forms[0].fmiddleName.value!=""){
var st = document.forms[0].fmiddleName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].fmiddleName.value=st;
}  
  if(document.forms[0].flastName.value!=""){
var st = document.forms[0].flastName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].flastName.value=st;
}  
if(document.forms[0].finitials.value!=""){
var st = document.forms[0].finitials.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].finitials.value=st;
}  
	     if(document.forms[0].fgender.value=="")
	    {
	      alert("Gender should not be left blank");
	      document.forms[0].fgender.focus();
	      return false;
	    }
	   
	    if(document.forms[0].fbirthplace.value!=""){
var st = document.forms[0].fbirthplace.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].fbirthplace.value=st;
}  
	    
	        if(document.forms[0].ftelephoneNumber.value!="")
		    {
		      
		     var ftelephoneNumber = document.forms[0].ftelephoneNumber.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(ftelephoneNumber)) {
             alert("Telephone Number  Should be Integer ");
                   document.forms[0].ftelephoneNumber.focus();
            return false;
        }
	    }
	        
	        if(document.forms[0].fmobileNumber.value!="")
		    {
		      
		     var fmobileNumber = document.forms[0].fmobileNumber.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(fmobileNumber)) {
             alert("Mobile Number  Should be Integer ");
                   document.forms[0].fmobileNumber.focus();
            return false;
        }
        }
        
         if(document.forms[0].femailAddress.value!=""){
var st = document.forms[0].femailAddress.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].femailAddress.value=st;
}  
	  if(document.forms[0].fdependent.value=="")
	    {
	      alert("Please Select Dependent");
	      document.forms[0].fdependent.focus();
	      return false;
	    }
	  if(document.forms[0].fnominee.value=="")
	    {
	      alert("Please Select Nominee");
	      document.forms[0].fnominee.focus();
	      return false;
	    }  
	  
	      if(document.forms[0].femployeeNumber.value!="")
		    {
		      
		     var femployeeNumber = document.forms[0].femployeeNumber.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(femployeeNumber)) {
             alert("Employee Number  Should be Integer ");
                   document.forms[0].femployeeNumber.focus();
            return false;
        }
	    }
<%--	     else if(document.forms[0].ftelephoneNumber.value=="")--%>
<%--	    {--%>
<%--	      alert("Telephone Number (Family Details) should not be left blank");--%>
<%--	      document.forms[0].ftelephoneNumber.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	    --%>
<%--	     else if(document.forms[0].fmobileNumber.value=="")--%>
<%--	    {--%>
<%--	      alert("Mobile Number (Family Details) should not be left blank");--%>
<%--	      document.forms[0].fmobileNumber.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	    --%>
<%--	      else if(document.forms[0].femployeeNumber.value=="")--%>
<%--	    {--%>
<%--	      alert("Employee Number (Family Details) should not be left blank");--%>
<%--	      document.forms[0].femployeeNumber.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	     else if(document.forms[0].fbloodGroup.value=="")--%>
<%--	    {--%>
<%--	      alert("Blood Group (Family Details) should not be left blank");--%>
<%--	      document.forms[0].fbloodGroup.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	     else if(document.forms[0].fdependent.value=="")--%>
<%--	    {--%>
<%--	      alert("Dependent should not be left blank");--%>
<%--	      document.forms[0].fdependent.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	     else if(document.forms[0].femployeeofCompany.value=="")--%>
<%--	    {--%>
<%--	      alert("Employee of Company should not be left blank");--%>
<%--	      document.forms[0].femployeeofCompany.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	    else if(document.forms[0].femployeeNumber.value=="")--%>
<%--	    {--%>
<%--	      alert("Employee Number should not be left blank");--%>
<%--	      document.forms[0].femployeeNumber.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	   --%>
<%--	      var email = document.forms[0].femailAddress.value;--%>
<%--        var pattern =/^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;--%>
<%--        if (!pattern.test(email)) {--%>
<%--             alert("Email Address (family Details) is not valid!");--%>
<%--             document.forms[0].femailAddress.focus();--%>
<%--            return false;--%>
<%--        }     --%>

 /*var tele = document.forms[0].ftelephoneNumber.value;
        var pattern = /^[0-9\+]{9,15}$/;
        if (!pattern.test(tele)) {
             alert("Telephone Number(family Details) is not valid!");
            return false;
        } 
        var mobile = document.forms[0].fmobileNumber.value;
        var pattern = /^[0-9\+]{9,15}$/;
        if (!pattern.test(mobile)) {
             alert("Mobile Number (family Details) is not valid!");
            return false;
        }    
         var employeeNo = document.forms[0].femployeeNumber.value;
        var pattern = /^[0-9\+]{9,15}$/;
        if (!pattern.test(employeeNo)) {
             alert("Mobile Number (family Details) is not valid!");
            return false;
        }     */

	var url="essJoin.do?method=saveFamilyDetails&param="+param+"&id="+reqid;
	document.forms[0].action=url;
	document.forms[0].submit();
	
	
}
function addEducation(param,reqid)
{
     
if(document.forms[0].educationalLevel.value=="")
	    {
	      alert("Educational Level should not be left blank");
	      document.forms[0].educationalLevel.focus();
	      return false;
	    }
	    if(document.forms[0].qualification.value=="")
	    {
	      alert("Qualification should not be left blank");
	      document.forms[0].qualification.focus();
	      return false;
	    }
<%--	    else if(document.forms[0].specialization.value=="")--%>
<%--	    {--%>
<%--	      alert("Specialization should not be left blank");--%>
<%--	      document.forms[0].specialization.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
	   
	   	   	            if(document.forms[0].specialization.value!=""){
var st = document.forms[0].specialization.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].specialization.value=st;
}
	            if(document.forms[0].universityName.value!=""){
var st = document.forms[0].universityName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].universityName.value=st;
} 
	  	            if(document.forms[0].univerysityLocation.value!=""){
var st = document.forms[0].univerysityLocation.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].univerysityLocation.value=st;
} 
	  	  	            if(document.forms[0].durationofCourse.value!=""){
	       var durationofCourse = document.forms[0].durationofCourse.value;
        var pattern =  /(\d{1,2}\.(?=\d{1,2}))/;
        if (!pattern.test(durationofCourse)
        ) {
             alert("Please enter valid Duration of course");
             document.forms[0].durationofCourse.focus();
             return false;
        }
	    }
	         if(document.forms[0].yearofpassing.value!=""){
	       var yearofpassing = document.forms[0].yearofpassing.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(yearofpassing)
        ) {
             alert("Year of Passing Should be Integer ");
             document.forms[0].yearofpassing.focus();
             return false;
        }
	    }
if(document.forms[0].edcountry.value=="")
	    {
	      alert("Country (Educational Details) should not be left blank");
	      document.forms[0].edcountry.focus();
	      return false;
	    }
	   if(document.forms[0].edstate.value=="")
	    {
	      alert("State (Educational Details) should not be left blank");
	      document.forms[0].edstate.focus();
	      return false;
	    }
	    
	   
<%--	    else if(document.forms[0].durationofCourse.value=="")--%>
<%--	    {--%>
<%--	      alert("Duration of Course should not be left blank");--%>
<%--	      document.forms[0].durationofCourse.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	    else if(document.forms[0].times.value=="")--%>
<%--	    {--%>
<%--	      alert("Time should not be left blank");--%>
<%--	      document.forms[0].times.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	   --%>
/*  if(document.forms[0].fromDate.value=="")
	    {
	      alert("From Date should not be left blank");
	      document.forms[0].fromDate.focus();
	      return false;
	    }
	     if(document.forms[0].toDate.value=="")
	    {
	      alert("To Date should not be left blank");
	      document.forms[0].toDate.focus();
	      return false;
	    }
	     if(document.forms[0].fromDate.value!="" &&document.forms[0].toDate.value!="")
		   {
		 var str1 = document.forms[0].fromDate.value;
		var str2 = document.forms[0].toDate.value;
		var dt1  = parseInt(str1.substring(0,2),10); 
		var mon1 = parseInt(str1.substring(3,5),10); 
		var yr1  = parseInt(str1.substring(6,10),10); 
		var dt2  = parseInt(str2.substring(0,2),10); 
		var mon2 = parseInt(str2.substring(3,5),10); 
		var yr2  = parseInt(str2.substring(6,10),10); 
		var date1 = new Date(yr1, mon1, dt1); 
		var date2 = new Date(yr2, mon2, dt2); 
		
		if(date2 < date1) 
		{ 
		    alert("From Date cannot be greater than To Date"); 
		    return false; 
		} 
	} */
	     if(document.forms[0].fullTimePartTime.value=="")
	    {
	      alert("FullTime/PartTime should not be left blank");
	      document.forms[0].fullTimePartTime.focus();
	      return false;
	    }
	   
	  

if(document.forms[0].percentage.value==""){
alert("Percentage should not be left blank")
return false;
}
if(document.forms[0].percentage.value!=""){
            var x = document.forms[0].percentage.value;
            
           if(isNaN(x)||x.indexOf(" ")!=-1)
           {
              alert("Enter numeric value for Percentage")
              return false; 
           }
             if (x>100)
           {
                alert("enter Percentage below or equal to 100");
                return false;
           }
           
          if (x<0)
           {
                alert("enter Percentage above 1");
                return false;
           }
	   
}


else{}

	var url="essJoin.do?method=saveEducationDetails&param="+param+"&id="+reqid;
	document.forms[0].action=url;
	document.forms[0].submit();
	
	
}
function addExpirience(param,reqid)
{

    if(document.forms[0].nameOfEmployer.value=="")
	    {
	      alert("Company Name should not be left blank");
	      document.forms[0].nameOfEmployer.focus();
	      return false;
	    }
	    if(document.forms[0].nameOfEmployer.value!=""){
var st = document.forms[0].nameOfEmployer.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].nameOfEmployer.value=st;
} 
 if(document.forms[0].exCity.value!=""){
	var st = document.forms[0].exCity.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exCity.value=st;
} 
	   if(document.forms[0].industry.value=="")
	    {
	      alert("Industry should not be left blank");
	      document.forms[0].industry.focus();
	      return false;
	    }
	    
	    if(document.forms[0].excountry.value=="")
	    {
	      alert("Country(Experience) should not be left blank");
	      document.forms[0].excountry.focus();
	      return false;
	    }
	    if(document.forms[0].positionHeld.value=="")
	    {
	      alert("Position Held should not be left blank");
	      document.forms[0].positionHeld.focus();
	      return false;
	    }
	    if(document.forms[0].positionHeld.value!=""){
		var st = document.forms[0].positionHeld.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].positionHeld.value=st;
} 
	    	    if(document.forms[0].jobRole.value!=""){
var st = document.forms[0].jobRole.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].jobRole.value=st;
} 
	   if(document.forms[0].startDate.value=="")
	    {
	      alert("Start Date should not be left blank");
	      document.forms[0].startDate.focus();
	      return false;
	    }
	  if(document.forms[0].endDate.value=="")
	    {
	      alert("End Date should not be left blank");
	      document.forms[0].endDate.focus();
	      return false;
	    }
	    if(document.forms[0].startDate.value!="" &&document.forms[0].endDate.value!="")
		   {
		 var str1 = document.forms[0].startDate.value;
		var str2 = document.forms[0].endDate.value;
		var mon1 = parseInt(str1.substring(0,2),10); 		
		var yr1  = parseInt(str1.substring(3,7),10); 		
		var mon2 = parseInt(str2.substring(0,2),10); 	
		var yr2  = parseInt(str2.substring(3,7),10); 
	
		var date1 = new Date(yr1, mon1); 
		var date2 = new Date(yr2, mon2); 
		
		if(date2 < date1) 
		{ 
		    alert("Start Date cannot be greater than End Date"); 
		    return false; 
		} 
	}
<%--	    else if(document.forms[0].endDate.value=="")--%>
<%--	    {--%>
<%--	      alert("End Date should not be left blank");--%>
<%--	      document.forms[0].endDate.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
        else
        {
        }
         
           
	    if(document.forms[0].microExp.checked==true){
	    	if(document.forms[0].microNo.value==""){
	    		alert("Please Enter Employee No");
	    		document.forms[0].microNo.focus();
	    		return false;
	    	}
	    }


	var url="essJoin.do?method=saveExpirienceDetails&param="+param+"&id="+reqid;
	document.forms[0].action=url;
	document.forms[0].submit();
	
	
}
function addLanguage(param,reqid)
{


     if(document.forms[0].language.value=="")
	    {
	      alert("Language should not be left blank");
	      document.forms[0].language.focus();
	      return false;
	    }
	    
	     else if(document.forms[0].motherTongue.value=="")
	    {
	      alert("Mother Tongue should not be left blank");
	      document.forms[0].motherTongue.focus();
	      return false;
	    }
	     else if(document.forms[0].langSpeaking.value=="")
	    {
	      alert("Language Speaking should not be left blank");
	      document.forms[0].langSpeaking.focus();
	      return false;
	    }
	    else if(document.forms[0].langRead.value=="")
	    {
	      alert("Language Reading should not be left blank");
	      document.forms[0].langRead.focus();
	      return false;
	    }
	    else if(document.forms[0].langWrite.value=="")
	    {
	      alert("Language Write should not be left blank");
	      document.forms[0].langWrite.focus();
	      return false;
	    }
<%--	    else if(document.forms[0].langstartDate.value=="")--%>
<%--	    {--%>
<%--	      alert("Start Date (Language) should not be left blank");--%>
<%--	      document.forms[0].langstartDate.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	    else if(document.forms[0].langendDate.value=="")--%>
<%--	    {--%>
<%--	      alert("End Date (Language) should not be left blank");--%>
<%--	      document.forms[0].langendDate.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
	    else
	    {
	    }
	   


	var url="essJoin.do?method=saveLanguage&param="+param+"&id="+reqid;
	document.forms[0].action=url;
	document.forms[0].submit();
	
}




function openDivs(param,reqid)
{
	var url="essJoin.do?method=displayTables&param="+param+"&id="+reqid;
	document.forms[0].action=url;
	document.forms[0].submit();
	
}
function reportgen()
{

var url="essJoin.do?method=reportgenerate";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function checkAllAddress()
{

var checkPropCheck=document.forms[0].checkAddress.checked;
if(checkPropCheck==true){
document.forms[0].selectAddress.checked = true ;
}else{
document.forms[0].selectAddress.checked = false ;
}
var subjectLength=document.forms[0].selectAddress.length;
for(i=0; i < subjectLength; i++){
if(checkPropCheck==true){
document.forms[0].selectAddress[i].checked = true ;
}else{
document.forms[0].selectAddress[i].checked = false ;
}
}
}
function modifyAddress(param1,param2){


if(document.forms[0].houseNumber1.value=="")
	    {
	      alert("House Number should not be left blank");
	      document.forms[0].houseNumber1.focus();
	      return false;
	    }
	     else if(document.forms[0].addressLine11.value=="")
	    {
	      alert("AddressLine1 should not be left blank");
	      document.forms[0].addressLine11.focus();
	      return false;
	    }
	     else if(document.forms[0].postalCode1.value=="")
	    {
	      alert("Postal Code should not be left blank");
	      document.forms[0].postalCode1.focus();
	      return false;
	    }
	     else if(document.forms[0].city1.value=="")
	    {
	      alert("City should not be left blank");
	      document.forms[0].city1.focus();
	      return false;
	    }
	     else if(document.forms[0].state1.value=="")
	    {
	      alert("State should not be left blank");
	      document.forms[0].state1.focus();
	      return false;
	    }
	     else if(document.forms[0].country1.value=="")
	    {
	      alert("Country should not be left blank");
	      document.forms[0].country1.focus();
	      return false;
	    }
	    else
	    {
	    }
					
     		var form = document.forms[0];
			var checkBox=form.selectAddress;
			var checked=false;
				if(checkBox.checked==true)
					 checked=true;
			for(var i=0;i<checkBox.length;i++)
				{
					if(checkBox[i].checked==true)
					 checked=true;
				}
				if(checked==true){
				if(confirm('Are You Sure To Modify Selected Requests')){
					var URL="essJoin.do?method=modifyAddress&param1="+param1+"&param2="+param2;
						
						form.action=URL;
						form.submit();
					}
					else return false;
				}
			else{
			if(checked==false) alert('please select the fields to be Modified');
			return false;
			}
}
  
function deleteAddress(param1,param2){
		var form = document.forms[0];
		var checkBox=form.selectAddress;
		var checked=false;
					if(checkBox.checked==true)
						 checked=true;
				for(var i=0;i<checkBox.length;i++)
					{
						if(checkBox[i].checked==true)
						 checked=true;
					}
					if(checked==true){
					if(confirm('Are You Sure To Delete Selected Requests')){
						var URL="essJoin.do?method=modifyAddress&param1="+param1+"&param2="+param2;
							
							form.action=URL;
							form.submit();
						}
						else return false;
					}
				else{
				if(checked==false) alert('please select the fields to be deleted');
				return false;
				}
}
function checkAllFamily()
{

var checkPropCheck=document.forms[0].checkFamily.checked;
if(checkPropCheck==true){
document.forms[0].selectFamily.checked = true ;
}else{
document.forms[0].selectFamily.checked = false ;
}
var subjectLength=document.forms[0].selectFamily.length;
for(i=0; i < subjectLength; i++){
if(checkPropCheck==true){
document.forms[0].selectFamily[i].checked = true ;
}else{
document.forms[0].selectFamily[i].checked = false ;
}
}
}
function modifyFamily(param1,param2){
					
			if(document.forms[0].familyDependentTypeID1.value=="")
	    {
	      alert("Relationship should not be left blank");
	      document.forms[0].familyDependentTypeID1.focus();
	      return false;
	    }
	   
	   else if(document.forms[0].ftitle1.value=="")
	    {
	      alert("Title(Family Details) should not be left blank");
	      document.forms[0].ftitle1.focus();
	      return false;
	    }
	     else if(document.forms[0].ffirstName1.value=="")
	    {
	      alert("First Name (Family Details) should not be left blank");
	      document.forms[0].ffirstName1.focus();
	      return false;
	    }
	     else if(document.forms[0].fgender1.value=="")
	    {
	      alert("Gender (Family Details) should not be left blank");
	      document.forms[0].fgender1.focus();
	      return false;
	    }
<%--	     else if(document.forms[0].fdateofBirth1.value=="")--%>
<%--	    {--%>
<%--	      alert("Date of Birth (Family Details) should not be left blank");--%>
<%--	      document.forms[0].fdateofBirth1.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	     else if(document.forms[0].fbloodGroup1.value=="")--%>
<%--	    {--%>
<%--	      alert("Blood Group (Family Details) should not be left blank");--%>
<%--	      document.forms[0].fbloodGroup1.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	     else if(document.forms[0].fdependent1.value=="")--%>
<%--	    {--%>
<%--	      alert("Dependent should not be left blank");--%>
<%--	      document.forms[0].fdependent1.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	     else if(document.forms[0].femployeeofCompany1.value=="")--%>
<%--	    {--%>
<%--	      alert("Employee of Company should not be left blank");--%>
<%--	      document.forms[0].femployeeofCompany1.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	    else if(document.forms[0].femployeeNumber1.value=="")--%>
<%--	    {--%>
<%--	      alert("Employee Number should not be left blank");--%>
<%--	      document.forms[0].femployeeNumber.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
	    else
	    {
	    }
<%--	      var email = document.forms[0].femailAddress1.value;--%>
<%--        var pattern =/^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;--%>
<%--        if (!pattern.test(email)) {--%>
<%--             alert("Email Address (family Details) is not valid!");--%>
<%--             document.forms[0].femailAddress1.focus();--%>
<%--            return false;--%>
<%--        }     --%>
<%----%>
<%-- var tele = document.forms[0].ftelephoneNumber1.value;--%>
<%--        var pattern = /^[0-9\+]{9,15}$/;--%>
<%--        if (!pattern.test(tele)) {--%>
<%--             alert("Telephone Number(family Details) is not valid!");--%>
<%--            return false;--%>
<%--        } --%>
<%--        var mobile = document.forms[0].fmobileNumber1.value;--%>
<%--        var pattern = /^[0-9\+]{9,15}$/;--%>
<%--        if (!pattern.test(mobile)) {--%>
<%--             alert("Mobile Number (family Details) is not valid!");--%>
<%--            return false;--%>
<%--        }     		--%>
		    
     		var form = document.forms[0];
			var checkBox=form.selectFamily;
			var checked=false;
				if(checkBox.checked==true)
					 checked=true;
			for(var i=0;i<checkBox.length;i++)
				{
					if(checkBox[i].checked==true)
					 checked=true;
				}
				if(checked==true){
				if(confirm('Are You Sure To Modify Selected Requests')){
					var URL="essJoin.do?method=modifyFamily&param1="+param1+"&param2="+param2;
						
						form.action=URL;
						form.submit();
					}
					else return false;
				}
			else{
			if(checked==false) alert('please select the fields to be Modified');
			return false;
			}
}
  
function deleteFamily(param1,param2){
		var form = document.forms[0];
		var checkBox=form.selectFamily;
		var checked=false;
					if(checkBox.checked==true)
						 checked=true;
				for(var i=0;i<checkBox.length;i++)
					{
						if(checkBox[i].checked==true)
						 checked=true;
					}
					if(checked==true){
					if(confirm('Are You Sure To Delete Selected Requests')){
						var URL="essJoin.do?method=modifyFamily&param1="+param1+"&param2="+param2;
							
							form.action=URL;
							form.submit();
						}
						else return false;
					}
				else{
				if(checked==false) alert('please select the fields to be deleted');
				return false;
				}
}
function checkAllEducation()
{

var checkPropCheck=document.forms[0].checkEducation.checked;
if(checkPropCheck==true){
document.forms[0].selectEducation.checked = true ;
}else{
document.forms[0].selectEducation.checked = false ;
}
var subjectLength=document.forms[0].selectEducation.length;
for(i=0; i < subjectLength; i++){
if(checkPropCheck==true){
document.forms[0].selectEducation[i].checked = true ;
}else{
document.forms[0].selectEducation[i].checked = false ;
}
}
}


function modifyEducation(param1,param2){


 if(document.forms[0].educationalLevel.value=="")
	    {
	      alert("Educational Level should not be left blank");
	      document.forms[0].educationalLevel.focus();
	      return false;
	    }
	    if(document.forms[0].qualification.value=="")
	    {
	      alert("Qualification should not be left blank");
	      document.forms[0].qualification.focus();
	      return false;
	    }
<%--	    else if(document.forms[0].specialization.value=="")--%>
<%--	    {--%>
<%--	      alert("Specialization should not be left blank");--%>
<%--	      document.forms[0].specialization.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
	   
	   	   	            if(document.forms[0].specialization.value!=""){
var st = document.forms[0].specialization.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].specialization.value=st;
}
	            if(document.forms[0].universityName.value!=""){
var st = document.forms[0].universityName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].universityName.value=st;
} 
	  	            if(document.forms[0].univerysityLocation.value!=""){
var st = document.forms[0].univerysityLocation.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].univerysityLocation.value=st;
} 
	  	  	            if(document.forms[0].durationofCourse.value!=""){
	       var durationofCourse = document.forms[0].durationofCourse.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(durationofCourse)
        ) {
             alert("DurationOfCourse Should be Integer ");
             document.forms[0].durationofCourse.focus();
             return false;
        }
	    }
	         if(document.forms[0].yearofpassing.value!=""){
	       var yearofpassing = document.forms[0].yearofpassing.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(yearofpassing)
        ) {
             alert("Year of Passing Should be Integer ");
             document.forms[0].yearofpassing.focus();
             return false;
        }
	    }
if(document.forms[0].edcountry.value=="")
	    {
	      alert("Country (Educational Details) should not be left blank");
	      document.forms[0].edcountry.focus();
	      return false;
	    }
	   if(document.forms[0].edstate.value=="")
	    {
	      alert("State (Educational Details) should not be left blank");
	      document.forms[0].edstate.focus();
	      return false;
	    }
	    
	   

	     if(document.forms[0].fullTimePartTime.value=="")
	    {
	      alert("FullTime/PartTime should not be left blank");
	      document.forms[0].fullTimePartTime.focus();
	      return false;
	    }
	   
	  

if(document.forms[0].percentage.value==""){
alert("Percentage should not be left blank")
return false;
}
if(document.forms[0].percentage.value!=""){
            var x = document.forms[0].percentage.value;
            
           if(isNaN(x)||x.indexOf(" ")!=-1)
           {
              alert("Enter numeric value for Percentage")
              return false; 
           }
             if (x>100)
           {
                alert("enter Percentage below or equal to 100");
                return false;
           }
           
          if (x<0)
           {
                alert("enter Percentage above 1");
                return false;
           }
	   
}


else{}
					
     		var form = document.forms[0];
			var checkBox=form.selectEducation;
			var checked=false;
				if(checkBox.checked==true)
					 checked=true;
			for(var i=0;i<checkBox.length;i++)
				{
					if(checkBox[i].checked==true)
					 checked=true;
				}
				if(checked==true){
				if(confirm('Are You Sure To Modify Selected Requests')){
					var URL="essJoin.do?method=modifyEducation&param1="+param1+"&param2="+param2;
						
						form.action=URL;
						form.submit();
					}
					else return false;
				}
			else{
			if(checked==false) alert('please select the fields to be Modified');
			return false;
			}
}
  
function deleteEducation(param1,param2){
		var form = document.forms[0];
		var checkBox=form.selectEducation;
		var checked=false;
					if(checkBox.checked==true)
						 checked=true;
				for(var i=0;i<checkBox.length;i++)
					{
						if(checkBox[i].checked==true)
						 checked=true;
					}
					if(checked==true){
					if(confirm('Are You Sure To Delete Selected Requests')){
						var URL="essJoin.do?method=modifyEducation&param1="+param1+"&param2="+param2;
							
							form.action=URL;
							form.submit();
						}
						else return false;
					}
				else{
				if(checked==false) alert('please select the fields to be deleted');
				return false;
				}
}
function checkAllExperience()
{

var checkPropCheck=document.forms[0].checkExperience.checked;
if(checkPropCheck==true){
document.forms[0].selectExperience.checked = true ;
}else{
document.forms[0].selectExperience.checked = false ;
}
var subjectLength=document.forms[0].selectExperience.length;
for(i=0; i < subjectLength; i++){
if(checkPropCheck==true){
document.forms[0].selectExperience[i].checked = true ;
}else{
document.forms[0].selectExperience[i].checked = false ;
}
}
}
function modifyExperience(param1,param2){


if(document.forms[0].nameOfEmployer1.value=="")
	    {
	      alert("Name Of Employer should not be left blank");
	      document.forms[0].nameOfEmployer1.focus();
	      return false;
	    }
	    else if(document.forms[0].industry1.value=="")
	    {
	      alert("Industry should not be left blank");
	      document.forms[0].industry1.focus();
	      return false;
	    }
	    else if(document.forms[0].exCity1.value=="")
	    {
	      alert("City(Experience) should not be left blank");
	      document.forms[0].exCity1.focus();
	      return false;
	    }
	     else if(document.forms[0].excountry1.value=="")
	    {
	      alert("Country(Experience) should not be left blank");
	      document.forms[0].excountry1.focus();
	      return false;
	    }
	     else if(document.forms[0].positionHeld1.value=="")
	    {
	      alert("Position Held should not be left blank");
	      document.forms[0].positionHeld1.focus();
	      return false;
	    }
	     else if(document.forms[0].jobRole1.value=="")
	    {
	      alert("Job Role should not be left blank");
	      document.forms[0].jobRole1.focus();
	      return false;
	    }
	    else if(document.forms[0].startDate1.value=="")
	    {
	      alert("Start Date should not be left blank");
	      document.forms[0].startDate1.focus();
	      return false;
	    }
	    else if(document.forms[0].endDate1.value=="")
	    {
	      alert("End Date should not be left blank");
	      document.forms[0].endDate1.focus();
	      return false;
	    }
        else
        {
        }
         
					
     		var form = document.forms[0];
			var checkBox=form.selectExperience;
			var checked=false;
				if(checkBox.checked==true)
					 checked=true;
			for(var i=0;i<checkBox.length;i++)
				{
					if(checkBox[i].checked==true)
					 checked=true;
				}
				if(checked==true){
				if(confirm('Are You Sure To Modify Selected Requests')){
					var URL="essJoin.do?method=modifyExperience&param1="+param1+"&param2="+param2;
						
						form.action=URL;
						form.submit();
					}
					else return false;
				}
			else{
			if(checked==false) alert('please select the fields to be Modified');
			return false;
			}
}
  
function deleteExperience(param1,param2){
		var form = document.forms[0];
		var checkBox=form.selectExperience;
		var checked=false;
					if(checkBox.checked==true)
						 checked=true;
				for(var i=0;i<checkBox.length;i++)
					{
						if(checkBox[i].checked==true)
						 checked=true;
					}
					if(checked==true){
					if(confirm('Are You Sure To Delete Selected Requests')){
						var URL="essJoin.do?method=modifyExperience&param1="+param1+"&param2="+param2;
							
							form.action=URL;
							form.submit();
						}
						else return false;
					}
				else{
				if(checked==false) alert('please select the fields to be deleted');
				return false;
				}
}
function checkAllLangauge()
{

var checkPropCheck=document.forms[0].checkLanguage.checked;
if(checkPropCheck==true){
document.forms[0].selectLanguage.checked = true ;
}else{
document.forms[0].selectLanguage.checked = false ;
}
var subjectLength=document.forms[0].selectLanguage.length;
for(i=0; i < subjectLength; i++){
if(checkPropCheck==true){
document.forms[0].selectLanguage[i].checked = true ;
}else{
document.forms[0].selectLanguage[i].checked = false ;
}
}
}
function modifyLanguage(param1,param2){


	   
if(document.forms[0].language1.value=="")
	    {
	      alert("Language should not be left blank");
	      document.forms[0].language1.focus();
	      return false;
	    }
	     else if(document.forms[0].motherTongue1.value=="")
	    {
	      alert("Mother Tongue should not be left blank");
	      document.forms[0].motherTongue1.focus();
	      return false;
	    }
	     else if(document.forms[0].langSpeaking1.value=="")
	    {
	      alert("Language Speaking should not be left blank");
	      document.forms[0].langSpeaking1.focus();
	      return false;
	    }
	    else if(document.forms[0].langRead1.value=="")
	    {
	      alert("Language Reading should not be left blank");
	      document.forms[0].langRead1.focus();
	      return false;
	    }
	    else if(document.forms[0].langWrite1.value=="")
	    {
	      alert("Language Write should not be left blank");
	      document.forms[0].langWrite1.focus();
	      return false;
	    }
<%--	    else if(document.forms[0].langstartDate1.value=="")--%>
<%--	    {--%>
<%--	      alert("Start Date (Language) should not be left blank");--%>
<%--	      document.forms[0].langstartDate1.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	    else if(document.forms[0].langendDate1.value=="")--%>
<%--	    {--%>
<%--	      alert("End Date (Language) should not be left blank");--%>
<%--	      document.forms[0].langendDate1.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
	    else
	    {
	    }
					
     		var form = document.forms[0];
			var checkBox=form.selectLanguage;
			var checked=false;
				if(checkBox.checked==true)
					 checked=true;
			for(var i=0;i<checkBox.length;i++)
				{
					if(checkBox[i].checked==true)
					 checked=true;
				}
				if(checked==true){
				if(confirm('Are You Sure To Modify Selected Requests')){
					var URL="essJoin.do?method=modifyLanguage&param1="+param1+"&param2="+param2;
						
						form.action=URL;
						form.submit();
					}
					else return false;
				}
			else{
			if(checked==false) alert('please select the fields to be Modified');
			return false;
			}
}
  
function deleteLanguage(param1,param2){
		var form = document.forms[0];
		var checkBox=form.selectLanguage;
		var checked=false;
					if(checkBox.checked==true)
						 checked=true;
				for(var i=0;i<checkBox.length;i++)
					{
						if(checkBox[i].checked==true)
						 checked=true;
					}
					if(checked==true){
					if(confirm('Are You Sure To Delete Selected Requests')){
						var URL="essJoin.do?method=modifyLanguage&param1="+param1+"&param2="+param2;
							
							form.action=URL;
							form.submit();
						}
						else return false;
					}
				else{
				if(checked==false) alert('please select the fields to be deleted');
				return false;
				}
}
	function checkPhysicallyStatus()
	{
	var status=document.forms[0].physicallyChallenged.value; 
	if(status=="Yes"){
	document.getElementById("enablePhyChDetails").style.visibility="visible"; 
	document.getElementById("phDetails").style.visibility="hidden";
	document.getElementById("phDetails1").style.visibility="visible";
	document.getElementById("phDetails").style.height="0px";
	document.getElementById("phDetails1").style.height="20px";
		
	
	}else{
	
	
	document.getElementById("enablePhyChDetails").style.visibility="hidden"; 
	document.getElementById("phDetails1").style.visibility="hidden";
	document.getElementById("phDetails").style.visibility="visible";
	document.getElementById("phDetails1").style.height="20px";
	document.getElementById("phDetails").style.height="0px";
	}
	}
function getStates(){


		var URL="essJoin.do?method=displayState&Param=addressDetails";
		document.forms[0].action=URL;
 		document.forms[0].submit();

}

function getEduStaes(){
	var URL="essJoin.do?method=getEduStaes&Param=educationDetails";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function displayAddress(addressID){

var URL="essJoin.do?method=editAddress&addressID="+addressID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function modifyAddress(){

	if(document.forms[0].addressType.value=="")
	    {
	      alert("House Number should not be left blank");
	      document.forms[0].addressType.focus();
	      return false;
	    }
	    var st = document.forms[0].addressType.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].addressType.value=st;
	
	if(document.forms[0].careofcontactname.value!="")
	    {
	    var st = document.forms[0].careofcontactname.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].careofcontactname.value=st;
	    }

		if(document.forms[0].houseNumber.value=="")
	    {
	      alert("House Number should not be left blank");
	      document.forms[0].houseNumber.focus();
	      return false;
	    }
	    var st = document.forms[0].houseNumber.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].houseNumber.value=st;
	
	    if(document.forms[0].addressLine1.value=="")
	    {
	      alert("AddressLine1 should not be left blank");
	      document.forms[0].addressLine1.focus();
	      return false;
	    }
	    var st = document.forms[0].addressLine1.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].addressLine1.value=st;
	
	if(document.forms[0].addressLine2.value!="")
	    {
	    var st = document.forms[0].addressLine2.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].addressLine2.value=st;
	    }
	    if(document.forms[0].addressLine3.value!="")
	    {
	    var st = document.forms[0].addressLine3.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].addressLine3.value=st;
	    }
	
	     if(document.forms[0].postalCode.value=="")
	    {
	      alert("Postal Code should not be left blank");
	      document.forms[0].postalCode.focus();
	      return false;
	    }
	         var postalCode = document.forms[0].postalCode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(postalCode)) {
             alert("Postal Code  Should be Integer ");
                   document.forms[0].postalCode.focus();
            return false;
        }
	
	     if(document.forms[0].city.value=="")
	    {
	      alert("City should not be left blank");
	      document.forms[0].city.focus();
	      return false;
	    }
	    var st = document.forms[0].city.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].city.value=st;
	
	     if(document.forms[0].state.value=="")
	    {
	      alert("State should not be left blank");
	      document.forms[0].state.focus();
	      return false;
	    }
	    var st = document.forms[0].state.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].state.value=st;
	
	     if(document.forms[0].country.value=="")
	    {
	      alert("Country should not be left blank");
	      document.forms[0].country.focus();
	      return false;
	    }
	    var st = document.forms[0].country.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].country.value=st;
	
	   


var reqAddressID=document.forms[0].reqAddressID.value;

var URL="essJoin.do?method=modifyAddress&reqAddressID="+reqAddressID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function deleteAddress(){


var reqAddressID=document.forms[0].reqAddressID.value;

var URL="essJoin.do?method=deletAddress&reqAddressID="+reqAddressID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}


function displayFamily(reqFamilyID){

var URL="essJoin.do?method=editFamily&familyID="+reqFamilyID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}


function modifyFamily(){

if(document.forms[0].familyDependentTypeID.value=="")
	    {
	      alert("Relationship should not be left blank");
	      document.forms[0].familyDependentTypeID.focus();
	      return false;
	    }
	    var st = document.forms[0].familyDependentTypeID.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].familyDependentTypeID.value=st;
	
	if(document.forms[0].ftitle.value=="")
	    {
	      alert("Title should not be left blank");
	      document.forms[0].ftitle.focus();
	      return false;
	    }
	    var st = document.forms[0].ftitle.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].ftitle.value=st;
	
	if(document.forms[0].ffirstName.value=="")
	    {
	      alert("First Name should not be left blank");
	      document.forms[0].ffirstName.focus();
	      return false;
	    }
	    var st = document.forms[0].ffirstName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].ffirstName.value=st;
	
	if(document.forms[0].fmiddleName.value!="")
	    {
	     var st = document.forms[0].fmiddleName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].fmiddleName.value=st;
	    
	}
	
	if(document.forms[0].flastName.value!="")
	    {
	     var st = document.forms[0].flastName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].flastName.value=st;
	    
	}
	
	if(document.forms[0].finitials.value!="")
	    {
	     var st = document.forms[0].flastName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].finitials.value=st;
	    
	}
	if(document.forms[0].fgender.value=="")
	    {
	      alert("Gender should not be left blank");
	      document.forms[0].fgender.focus();
	      return false;
	    }
	    var st = document.forms[0].fgender.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].fgender.value=st;
	
	if(document.forms[0].fdateofBirth.value=="")
	    {
	      alert("Date of Birth should not be left blank");
	      document.forms[0].fdateofBirth.focus();
	      return false;
	    }
	    var st = document.forms[0].fdateofBirth.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].fdateofBirth.value=st;
	
	if(document.forms[0].fbirthplace.value!="")
	    {
	     var st = document.forms[0].fbirthplace.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].fbirthplace.value=st;
	    
	}
	
	if(document.forms[0].ftelephoneNumber.value!="")
	    {
	     var ftelephoneNumber = document.forms[0].ftelephoneNumber.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(ftelephoneNumber)) {
             alert("TelephoneNumber  Should be Integer ");
                   document.forms[0].ftelephoneNumber.focus();
            return false;
        }
	    
	}
	
	if(document.forms[0].fmobileNumber.value!="")
	    {
	     var fmobileNumber = document.forms[0].fmobileNumber.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(fmobileNumber)) {
             alert("MobileNumber  Should be Integer ");
                   document.forms[0].fmobileNumber.focus();
            return false;
        }
	    
	}
	if(document.forms[0].femailAddress.value!="")
	    {
	     var st = document.forms[0].femailAddress.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].femailAddress.value=st;
	    
	}
		if(document.forms[0].femployeeNumber.value!="")
	    {
	     var femployeeNumber = document.forms[0].femployeeNumber.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(femployeeNumber)) {
             alert("EmployeeNumber  Should be Integer ");
                   document.forms[0].femployeeNumber.focus();
            return false;
        }
	    
	}

	
	
	
	
	if(document.forms[0].fdependent.value=="")
	    {
	      alert("Dependent should not be left blank");
	      document.forms[0].fdependent.focus();
	      return false;
	    }
	  if(document.forms[0].fnominee.value=="")
	    {
	      alert("Please Select Nominee");
	      document.forms[0].fnominee.focus();
	      return false;
	    }
	
	    var st = document.forms[0].fdependent.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].fdependent.value=st;




var reqFamilyID=document.forms[0].reqFamilyID.value;

var URL="essJoin.do?method=modifyFamily&reqFamilyID="+reqFamilyID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function deleteFamily(){


var reqFamilyID=document.forms[0].reqFamilyID.value;

var URL="essJoin.do?method=deleteFamily&reqFamilyID="+reqFamilyID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}



function displayEducation(eduID){


var URL="essJoin.do?method=editEducation&eduID="+eduID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}




function modifyEdu()
{
if(document.forms[0].educationalLevel.value=="")
	    {
	      alert("Educational Level should not be left blank");
	      document.forms[0].educationalLevel.focus();
	      return false;
	    }
	    if(document.forms[0].qualification.value=="")
	    {
	      alert("Qualification should not be left blank");
	      document.forms[0].qualification.focus();
	      return false;
	    }
<%--	    else if(document.forms[0].specialization.value=="")--%>
<%--	    {--%>
<%--	      alert("Specialization should not be left blank");--%>
<%--	      document.forms[0].specialization.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
	   
	   
	            if(document.forms[0].universityName.value!=""){
var st = document.forms[0].universityName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].universityName.value=st;
} 
	  	            if(document.forms[0].univerysityLocation.value!=""){
var st = document.forms[0].univerysityLocation.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].univerysityLocation.value=st;
} 
	  	  	            if(document.forms[0].durationofCourse.value!=""){
	       var durationofCourse = document.forms[0].durationofCourse.value;
       var pattern =  /(\d{1,2}\.(?=\d{1,2}))/;
        if (!pattern.test(durationofCourse)
        ) {
             alert("Please enter valid Duration of course");
             document.forms[0].durationofCourse.focus();
             return false;
        }
	    }
	         if(document.forms[0].yearofpassing.value!=""){
	       var yearofpassing = document.forms[0].yearofpassing.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(yearofpassing)
        ) {
             alert("Year of Passing Should be Integer ");
             document.forms[0].yearofpassing.focus();
             return false;
        }
	    }
if(document.forms[0].edcountry.value=="")
	    {
	      alert("Country (Educational Details) should not be left blank");
	      document.forms[0].edcountry.focus();
	      return false;
	    }
	   if(document.forms[0].edstate.value=="")
	    {
	      alert("State (Educational Details) should not be left blank");
	      document.forms[0].edstate.focus();
	      return false;
	    }
	    
	   

	     if(document.forms[0].fullTimePartTime.value=="")
	    {
	      alert("FullTime/PartTime should not be left blank");
	      document.forms[0].fullTimePartTime.focus();
	      return false;
	    }
	   
	  

if(document.forms[0].percentage.value==""){
alert("Percentage should not be left blank")
return false;
}
if(document.forms[0].percentage.value!=""){
            var x = document.forms[0].percentage.value;
            
           if(isNaN(x)||x.indexOf(" ")!=-1)
           {
              alert("Enter numeric value for Percentage")
              return false; 
           }
             if (x>100)
           {
                alert("enter Percentage below or equal to 100");
                return false;
           }
           
          if (x<0)
           {
                alert("enter Percentage above 1");
                return false;
           }
	   
}


else{}
var reqEduID=document.forms[0].reqEduID.value;

var URL="essJoin.do?method=modifyEducation&reqEduID="+reqEduID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
 
}
function deleteEdu(){


var reqEduID=document.forms[0].reqEduID.value;

var URL="essJoin.do?method=deleteEducation&reqEduID="+reqEduID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function displayExperiance(expID){


var URL="essJoin.do?method=editExprience&expID="+expID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}
function modifyExprience(){




    if(document.forms[0].nameOfEmployer.value=="")
	    {
	      alert("Company Name should not be left blank");
	      document.forms[0].nameOfEmployer.focus();
	      return false;
	    }
	    if(document.forms[0].nameOfEmployer.value!=""){
var st = document.forms[0].nameOfEmployer.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].nameOfEmployer.value=st;
} 
 if(document.forms[0].exCity.value!=""){
	var st = document.forms[0].exCity.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].exCity.value=st;
} 
	   if(document.forms[0].industry.value=="")
	    {
	      alert("Industry should not be left blank");
	      document.forms[0].industry.focus();
	      return false;
	    }
	    
	    if(document.forms[0].excountry.value=="")
	    {
	      alert("Country(Experience) should not be left blank");
	      document.forms[0].excountry.focus();
	      return false;
	    }
	    if(document.forms[0].positionHeld.value=="")
	    {
	      alert("Position Held should not be left blank");
	      document.forms[0].positionHeld.focus();
	      return false;
	    }
	    if(document.forms[0].positionHeld.value!=""){
		var st = document.forms[0].positionHeld.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].positionHeld.value=st;
} 
	    	    if(document.forms[0].jobRole.value!=""){
var st = document.forms[0].jobRole.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].jobRole.value=st;
} 
	   if(document.forms[0].startDate.value=="")
	    {
	      alert("Start Date should not be left blank");
	      document.forms[0].startDate.focus();
	      return false;
	    }
	  if(document.forms[0].endDate.value=="")
	    {
	      alert("End Date should not be left blank");
	      document.forms[0].endDate.focus();
	      return false;
	    }
	    if(document.forms[0].startDate.value!="" &&document.forms[0].endDate.value!="")
		   {
		 var str1 = document.forms[0].startDate.value;
		var str2 = document.forms[0].endDate.value;
		var mon1 = parseInt(str1.substring(0,2),10); 		
		var yr1  = parseInt(str1.substring(3,7),10); 		
		var mon2 = parseInt(str2.substring(0,2),10); 	
		var yr2  = parseInt(str2.substring(3,7),10); 
	
		var date1 = new Date(yr1, mon1); 
		var date2 = new Date(yr2, mon2); 
		
		if(date2 < date1) 
		{ 
		    alert("Start Date cannot be greater than End Date"); 
		    return false; 
		} 
	}

        else
        {
        }
         
           


var reqExpID=document.forms[0].reqExpID.value;

var URL="essJoin.do?method=modifyExperience&reqExpID="+reqExpID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}


function deleteExprience(){


var reqExpID=document.forms[0].reqExpID.value;

var URL="essJoin.do?method=deleteExperience&reqExpID="+reqExpID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}


function displayLanguage(langID){


var URL="essJoin.do?method=editLanguage&langID="+langID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}
function modifyLanguage(){
var reqLangID=document.forms[0].reqLangID.value;

var URL="essJoin.do?method=modifyLanguage&reqLangID="+reqLangID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}
function deleteLanguage(){
var reqLangID=document.forms[0].reqLangID.value;

var URL="essJoin.do?method=deleteLanguage&reqLangID="+reqLangID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}


function onUpload(){
var URL="essJoin.do?method=upLoadPhoto"
		document.forms[0].action=URL;
 		document.forms[0].submit();


}

function validaePassportFields(){

if(document.forms[0].passportNumber.value!="")
{
document.getElementById("enablePassportValidation").style.visibility="visible"; 
document.getElementById("enablePassportValidation1").style.visibility="visible"; 
document.getElementById("enablePassportValidation2").style.visibility="visible"; 

}else{
document.getElementById("enablePassportValidation").style.visibility="hidden"; 
document.getElementById("enablePassportValidation1").style.visibility="hidden"; 
document.getElementById("enablePassportValidation2").style.visibility="hidden"; 

}

}

function daydiff(first, second) {

//daydiff

var totaldays=(second-first)/(1000*60*60*24);
alert("totaldays="+totaldays);

if(totaldays<0)
{
alert("start date should be less than end date");
    return "";
}
else{

    return ((second-first)/(1000*60*60*24)+1)
    }
}


function onUploadEdu()
{

if(document.forms[0].qualification.value==""){

alert("Qualification should not be left blank");
	      document.forms[0].qualification.focus();
	      return false;
}
if(document.forms[0].empEdu.value==""){

alert("Please upload File");
	      document.forms[0].empEdu.focus();
	      return false;
}
  var x=document.forms[0].qualification.value;
  

var URL="essJoin.do?method=upLoadEducationAttachments&Qual="+x;
		document.forms[0].action=URL;
 		document.forms[0].submit();

}

function makeEmpNoNmand(){
	
	
	if(document.forms[0].microExp.checked==true){
	document.getElementById("microNoId").style="visibility:visble";
	}if(document.forms[0].microExp.checked==false){
		document.getElementById("microNoId").style="visibility:hidden";
	}
	
}

function hideMessage(){
	
	document.getElementById("messageID").style.visibility="collapse";
	document.getElementById("messageID1").style.visibility="collapse";	
}

</script>

		<style type="text/css">
.text_field {
	background-color: #f6f6f6;
	width: 150px;
	height: 20px;
	border: #38abff 1px solid
}

.no
{pointer-events: none; 
}
</style>
	</head>

	<body>
<html:form action="/essJoin.do" enctype="multipart/form-data">
<html:hidden property="id" name="joiningReportForm" />


		<div align="center" id="messageID">
			<logic:present name="joiningReportForm" property="message">
				<font color="green" size="4"> <bean:write
						name="joiningReportForm" property="message" /> </font>
						<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
			</logic:present>
			<logic:present name="joiningReportForm" property="message1">
				<font color="red" size="4"> <bean:write
						name="joiningReportForm" property="message1" /> </font>
						<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
			</logic:present>
		</div>





		

			<table align="center">
				<tr>
					<th align="center">
						<input type="button" class="rounded" value="Personal Information"
							onclick="openDivs('personalDetails','${joiningReportForm.id}')" style="width: 120px" />
					</th>
					<th align="center">
						<input type="button" class="rounded" value="Addresses"
							onclick="openDivs('addressDetails','${joiningReportForm.id}')" style="width: 120px" />
					</th>
					<th align="center">
						<input type="button" class="rounded" value="Family Details"
							onclick="openDivs('familyDetails','${joiningReportForm.id}')" style="width: 120px" />
					</th>
					<th align="center">
						<input type="button" class="rounded" value="Education Details"
							onclick="openDivs('educationDetails','${joiningReportForm.id}')" style="width: 120px" />
					</th>
					<th align="center">
						<input type="button" class="rounded" value="Experience"
							onclick="openDivs('experienceDetails','${joiningReportForm.id}')" style="width: 120px" />
					</th>
					<th align="center">
						<input type="button" class="rounded" value="Language"
							onclick="openDivs('languageDetails','${joiningReportForm.id}')" style="width: 120px" />
					</th>
				<!-- 	<th align="center">
						<input type="button" class="rounded" value="Report"
							onclick="reportgen()" style="width: 120px" />
					</th> -->
				</tr>
			</table>
			<br />
			

			<html:hidden property="userType" />
			<html:hidden property="userName" />
			<html:hidden property="password" />
			<%--PERSONAL INFORMATION DETAILS--%>


			<logic:notEmpty name="personalDetails">

				<div id="personalInformation" style="width: 90%;">
					<table width="90%" class="bordered">
						<tr>
							<th colspan="4"><center><big>Personal Information</big></center></th>
						</tr>

                     
						<tr>
							<td >
								Title
								<font color="red">*</font>
							</td>
							<td colspan="">
								<html:select property="title" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="Mr"></html:option>
									<html:option value="Mrs"></html:option>
									<html:option value="Miss"></html:option>
									<html:option value="Ms"></html:option>
									<html:option value="Dr"></html:option>
									<html:option value="Late"></html:option>
								</html:select>
							</td>
							
							<td>
								First Name
								<font color="red">*</font>
							</td>

							<td>
									<html:text property="firstName" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							
							</td>
							</tr>
							<tr>
							<td>Middle name</td><td>
									<html:text property="middleName" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							
							</td>
							<td>
								Last Name
								<font color="red">*</font>
							</td>

							<td>
									<html:text property="lastName" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							
							</td>
							</tr>
							<tr>
							<td>
								Nickname
							</td>

							<td>
								<html:text property="nickName" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						

							<td>
								Gender
								<font color="red">*</font>
							</td>
							<td>
									<html:select property="gender" styleClass="text_field">								
									<html:option value="M">Male</html:option>
									<html:option value="F">Female</html:option>
									
								</html:select>
							</td>
							</tr>
						
						<tr>
							<td>
								Date of Joining<font color="red">*</font>
							</td>

							<td>
							<html:text property="dateofjoin" styleId="dateofjoinpicker" styleClass="text_field" readonly="true"></html:text>
							</td>
						
							<td>
								Designation<font color="red">*</font>
							</td>

							<td>
								<html:select property="designation"
									styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="desgnIDList" labelProperty="desgnTXTList" />
								</html:select>
							</td>
							</tr>
						<tr>
							<td>
								Division/Dept
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="department" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="departIDList" labelProperty="departTXTList" />
								</html:select>
							</td>
						
							<td>
							 Working Location<font color="red">*</font>
							</td>

							<td>
								<html:text property="location" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
							</tr>
						<tr>
						<tr>
							<td>
								Marital Status
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="maritalStatus" styleClass="text_field" name="joiningReportForm">
									<html:option value="">--Select--</html:option>
									<html:option value="0">Single</html:option>
									<html:option value="1">Married</html:option>
									<html:option value="2">Widow</html:option>
									<html:option value="3">Divorce</html:option>
									<html:option value="5">Seperated</html:option>
								</html:select>
							</td>
						
							<td>
								Date of Birth
								<font color="red">*</font>
								<img src="images/date_icon.gif" align="absmiddle" />
							</td>

							<td>
							<html:text property="dateofBirth" styleId="dateOfBirthpicker" styleClass="text_field" readonly="true"></html:text>
								
						</td>
						</tr>
						<tr>
							<td>
								Birth Place
							</td>

							<td>
								<html:text property="birthplace" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
					
							<td>
								Country of Birth
							</td>

							<td>
								<html:select property="countryofBirth" styleClass="text_field">
									<html:option value="">--Select--</html:option>
							
									<html:options name="joiningReportForm"
										property="countryList" labelProperty="countryLabelList" />
								</html:select>
							</td>
								</tr>
						<tr>
							<td>
								Caste
							</td>

							<td>
								<html:text property="caste" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						
							<td>
								Religion
							</td>

							<td>
								<html:select property="religiousDenomination"
									styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="religionList" labelProperty="religionLabelList" />
								</html:select>
							</td>
							</tr>
						<tr>
							<td>
								Nationality
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="nationality" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="nationalityList" labelProperty="nationalityLabelList" />
								</html:select>
							</td>
						
							<td>
								Telephone Number
							</td>

							<td>
								<html:text property="telephoneNumber" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
							</tr>
						<tr>
							<td>
								Mobile Number<font color="red">*</font>
							</td>

							<td>
								<html:text property="mobileNumber" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						
							<html:hidden property="faxNumber" styleClass="text_field" />

							<td>
								Personal eMail
								<font color="red">*</font>
							</td>

							<td>
								<html:text property="emailAddress" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
							</tr>
						<tr>
							<html:hidden property="website" styleClass="text_field" />

							<td>
								Blood Group
							</td>
							
							<td>
							<logic:empty name="blockBloodGroup">
								<html:select property="bloodGroup" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="A+"></html:option>
									<html:option value="AB+"></html:option>
									<html:option value="B-"></html:option>
									<html:option value="AB-"></html:option>
									<html:option value="B+"></html:option>
									<html:option value="O+"></html:option>
									<html:option value="A-"></html:option>
									<html:option value="O-"></html:option>
								</html:select>
								</logic:empty>
								<logic:notEmpty name="blockBloodGroup">
								${joiningReportForm.bloodGroup }
								<html:hidden property="bloodGroup"/>
								</logic:notEmpty>
							</td>
							<html:hidden property="permanentAccountNumber"
								styleClass="text_field" />
						
							<td>PAN No<font color="red">*</font></td><td><html:text property="panno" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text></td>
							
							</tr>
						<tr>
							<td>UAN No</td><td><html:text property="uanno" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							
							</td>
						<td>Adhar No</td><td colspan="3"><html:text property="adharno" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text></td>
						
						</tr>
				
						  <tr>
                         <th colspan="4" style="text-align:center;">Bank Details</th>
                     
                        </tr>
				     	
				     	
                        <tr>
                           	<td>Payment Method </td><td><html:select property="paymentMethod"
									styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="paymodeList" labelProperty="paymodeLabelList" />
								</html:select></td>
                            <td>Salary Currency</td><td><html:select property="salaryCurrency"
									styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="currencyList" labelProperty="currencyLabelList" />
								</html:select></td>
                        </tr>
                           
                           <tr>
                           	<td>Account Type</td><td><html:text property="accountType" styleClass="text_field" style="width:200px;" onkeyup="this.value = this.value.replace(/'/,'`')" maxlength="30"/></td>
                               <td>Account Number</td><td><html:text property="accountNumber" style="width:200px;" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')" maxlength="50"/></td>
                           </tr>
                           
                           <tr>
                           	<td>Bank Name</td><td><html:select property="bankName"
									styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="bankIDList" labelProperty="bankLabelList" />
								</html:select></td>
                               <td>Branch Name</td>
                               
                               <td><html:text property="branchName" style="width:200px;" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')" maxlength="50"/>
                              </td>
                           </tr>
                         
                           <tr>
                           	<td>IFSC Code</td><td>
                           	<html:text property="ifsCCode" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')" maxlength="50"/>
                           </td>
                               <td>MICR Code</td><td>
                               <html:text property="micrCode" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')" maxlength="50"/>
                             </td>
                           </tr>

						<tr>
							<th colspan="4" style="text-align: center;">
								Passport Details
							</th>
						</tr>
						<tr>
							<td>
								Number
							</td>
							<td>
								<html:text property="passportNumber" styleClass="text_field"
									onkeyup="this.value = this.value.replace(/'/,'`');validaePassportFields()"></html:text>
							</td>
							<td>
								Place of Issue
								<div id="enablePassportValidation" style="visibility: hidden">
									<font color="red" size="3">*</font>
								</div>
							</td>

							<td>
								<html:text property="placeofIssueofPassport"
									styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						</tr>
						<tr>
							<td>
								Date of issue
								<img src="images/date_icon.gif" align="absmiddle" />
								<div id="enablePassportValidation1" style="visibility: hidden">
									<font color="red" size="3">*</font>
								</div>
							</td>

							<td>
								<html:text property="dateofissueofPassport" styleId="dateOfIssuepicker" readonly="true" ></html:text>
							</td>
							<td>
								Date of expiry
								<img src="images/date_icon.gif" align="absmiddle" />
								
								<div id="enablePassportValidation2" style="visibility: hidden">
									<font color="red" size="3">*</font>
								</div>
							</td>

							<td>
								<html:text property="dateofexpiryofPassport" styleId="dateOfExpirypicker" readonly="true"></html:text>
								
							</td>
						</tr>
						<tr>
							<td colspan="4"></td>
						</tr>

						<tr>
							<th colspan="4" style="text-align: center;">
								General Details
							</th>
						</tr>
						<tr>

							<td>
								Physically Challenged
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="physicallyChallenged"
									styleClass="text_field" onchange="checkPhysicallyStatus()">

									<html:option value="No"></html:option>
									<html:option value="Yes"></html:option>
								</html:select>

							</td>
							<td rowspan="2">
								Physically Challenged Details
								<div id="enablePhyChDetails" style="visibility: hidden">
									<font color="red" size="3">*</font>
								</div>
							</td>

							<td rowspan="2">
								
									<html:textarea property="physicallyChallengeddetails" cols="20"
										rows="2" onkeyup="this.value = this.value.replace(/'/,'`')"></html:textarea>
							
							</td>
						</tr>
						<tr>
							<td>
								Personal Identification Marks
							</td>
							<td>
								<html:text property="personalIdentificationMarks"
									styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						</tr>
						<tr>
							<td colspan="4"></td>
						</tr>
						<tr>
							<th colspan="4" style="text-align: center;">
								Emergency Contact Details
							</th>
							
						</tr>
						<tr>
							<td>
								Person Name 1<font color="red" size="3">*</font>
							</td>
							<td colspan="3" >
								<html:text property="emergencyContactPersonName" styleClass="text_field" style="width:300px;"/>
							</td>
							
						</tr>
						
						<tr>
						<td>
								 Mobile&nbsp;Number<font color="red" size="3">*</font>
							</td>
							<td>
								<html:text property="emergencyMobileNumber"
									styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
							<td> Telephone Number</td>
							<td><html:text property="emergencyTelephoneNumber" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text></td>
							
						</tr>
						<tr>
							<td>
								Address <font color="red" size="3">*</font>
							</td>
							<td colspan="3">
								<html:text property="emergencyContactAddressLine1"  maxlength="80" styleClass="text_field"  title="Maximum of 80 characters" style="width:700px"	onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
						
							</td>
						</tr>
						  <tr>
						  <td>&nbsp;</td>
						  <td colspan="3">
								<html:text property="emergCntAdd11"  maxlength="80" styleClass="text_field"  title="Maximum of 80 characters" style="width:700px"	onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
						
							</td>
						  </tr>	
						   <tr>
						  <td>&nbsp;</td>
						  <td colspan="3">
								<html:text property="emergCntAdd111"  maxlength="80" styleClass="text_field"  title="Maximum of 80 characters" style="width:700px;"	onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
						
							</td>
						  </tr>
						  <tr>
							<td>City</td><td><html:text property="emergCity1" styleClass="text_field" style="width:200px;" onkeyup="this.value = this.value.replace(/'/,'`')"/></td>
							<td>State</td><td><html:text property="emergState1" styleClass="text_field" style="width:200px;" onkeyup="this.value = this.value.replace(/'/,'`')"/></td>
						</tr>	
                         <tr>
							<td>
								Person Name 2
							</td>
							<td colspan="3" >
								<html:text property="emergencyContactPersonName1" styleClass="text_field" style="width:300px;" onkeyup="this.value = this.value.replace(/'/,'`')"/>
							</td>
							
						</tr>
						
						<tr>
						<td>
								 Mobile&nbsp;Number
							</td>
							<td>
								<html:text property="emergencyMobileNumber1"
									styleClass="text_field"></html:text>
							</td>
							<td> Telephone Number</td>
							<td><html:text property="emergencyTelephoneNumber1" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text></td>
							
						</tr>
						<tr>
							<td>
								Address 
							</td>
							<td colspan="3">
								<html:text property="emergencyContactAddressLine2"  maxlength="80" styleClass="text_field"  title="Maximum of 80 characters" style="width:700px"	onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
						
							</td>
						</tr>
						
						  <tr>
						  <td>&nbsp;</td>
						  <td colspan="3">
								<html:text property="emergCntAdd22"  maxlength="80" styleClass="text_field"  title="Maximum of 80 characters" style="width:700px"	onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
						
							</td>
						  </tr>	
						   <tr>
						  <td>&nbsp;</td>
						  <td colspan="3">
								<html:text property="emergCntAdd222"  maxlength="80" styleClass="text_field"  title="Maximum of 80 characters" style="width:700px;"	onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						  </tr>	
						<tr>
							<td>City</td><td><html:text property="emergCity2" styleClass="text_field" style="width:200px;" onkeyup="this.value = this.value.replace(/'/,'`')"/></td>
							<td>State</td><td><html:text property="emergState2" styleClass="text_field" style="width:200px;" onkeyup="this.value = this.value.replace(/'/,'`')"/></td>
						</tr>


<%--						<tr>--%>
<%--							<th colspan="4" style="text-align: center;">--%>
<%--								Upload Photo--%>
<%--							</th>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td align="center">--%>
<%--								<span class="text">Upload Image :</span>--%>
<%--								<html:file name="joiningReportForm" property="empPhoto" />--%>
<%--								<html:button value="Upload" onclick="onUpload()"--%>
<%--									property="method" styleClass="sudmit_btn" />--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<logic:notEmpty name="employeePhoto">--%>
<%--							<tr>--%>
<%--								<td colspan="4">--%>
<%--									<img--%>
<%--										src="images/EmpPhotos/<bean:write name="joiningReportForm" property="photoImage"/>"--%>
<%--										width="100px" height="100px" />--%>
<%----%>
<%--								</td>--%>
<%--							</tr>--%>
<%--						</logic:notEmpty>--%>
						<tr>
							<td colspan="4" align="center">
								<html:hidden property="noOfChildrens" styleClass="text_field" />
								<center><html:button property="method" styleClass="rounded"	style="width: 100px" value="Save" onclick="onSave(${joiningReportForm.id })" />
							<%-- 	<html:button property="method" styleClass="rounded"	style="width: 100px" value="Submit" onclick="onSubmit()" /> --%></center>
							</td>
						</tr>

					</table>
				</div>



			</logic:notEmpty>
			<br/>
			
		

			<%--ADDRESS DETAILS--%>

			<logic:notEmpty name="addressDetails">

				<div id="address" style="width: 90%">
					<table class="bordered" width="90%">
						<tr>
							<th colspan="5">
								<center>
									Address Details
								</center>
							</th>

						</tr>
						<tr>
							<td>
								Address Type
								<font color="red">*</font>
							</td>
<logic:notEmpty name="modifyAddress">

							<td>
								<html:select property="addressType" styleClass="no">
									<html:option value="">--Select--</html:option>
									<html:option value="001">Permanent</html:option>
									<html:option value="002">Temporary</html:option>
									<html:option value="003">Mailing</html:option>
									<html:option value="004">Emergency</html:option>
                                    <html:option value="005">Address during leave of absence</html:option>
									<html:option value="006">Current Address</html:option>
								</html:select>
							</td>
							
							</logic:notEmpty>
									<logic:notEmpty name="addressAdd">
									<td>
								<html:select property="addressType" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="001">Permanent</html:option>
									<html:option value="002">Temporary</html:option>
									<html:option value="003">Mailing</html:option>
									<html:option value="004">Emergency</html:option>
                                    <html:option value="005">Address during leave of absence</html:option>
									<html:option value="006">Current Address</html:option>
								</html:select>
							</td>
									</logic:notEmpty>
							<td>
								Care of / Contact name
							</td>

							<td>
								<html:text property="careofcontactname" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						</tr>
						<tr>
							<td>
								House Number
								<font color="red">*</font>
							</td>

							<td>
								<html:text property="houseNumber" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>

							<td>
								Address 1
								<font color="red">*</font>
							</td>

							<td>
								<html:textarea property="addressLine1" cols="20" rows="3" onkeyup="this.value = this.value.replace(/'/,'`')"></html:textarea>
							</td>

						</tr>
						<tr>

							<td>
								Address 2
							</td>
							<td>
								<html:textarea property="addressLine2" cols="20" rows="3" onkeyup="this.value = this.value.replace(/'/,'`')"></html:textarea>
							</td>

							<td>
								Address 3
							</td>
							<td>
								<html:textarea property="addressLine3" cols="20" rows="3" onkeyup="this.value = this.value.replace(/'/,'`')"></html:textarea>
							</td>
						</tr>

						<tr>
							

							<td>
								City
								<font color="red">*</font>
							</td>
							<td>
								<html:text property="city" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
							<td>
								Postal Code
								<font color="red">*</font>
							</td>
							<td>
								<html:text property="postalCode" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						</tr>
						<tr>
						<td>
								State
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="state" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm" property="stateList"
										labelProperty="stateLabelList" />
								</html:select>
							</td>
							<td>
								Country
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="country" styleClass="text_field"
									onchange="getStates('personalDetails')" >
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="countryList" labelProperty="countryLabelList" />
								</html:select>
							</td>

							

						</tr><!--
						<tr>
							<td>
								Own Accomodation
							</td>

							<td>
								<html:select property="ownAccomodation" styleClass="text_field">
									<html:option value="No"></html:option>
									<html:option value="Yes"></html:option>

								</html:select>
							</td>
							<td>
								Company Housing
							</td>
							<td>
								<html:select property="companyHousing" styleClass="text_field">
									<html:option value="No"></html:option>
									<html:option value="Yes"></html:option>

								</html:select>
								
							</td>
						</tr>
						--><html:hidden property="ownAccomodation" />
								<html:hidden property="companyHousing" />
						<html:hidden property="reqAddressID" />
								<html:hidden property="addressStatus" />
						<%--								<tr>--%>
						<%--									--%>
						<%--									<td>Start Date <font color="red">*</font> <img src="images/date_icon.gif" align="absmiddle" /></td>--%>
						<%--								--%>
						<%--									<td>--%>
						<%--										<html:text property="addStartDate"  --%>
						<%--										 styleId="date6" onfocus="popupCalender('date6')"--%>
						<%--										  readonly="true"></html:text>--%>
						<%--									</td>--%>
						<%--							<td>End Date <font color="red">*</font> <img src="images/date_icon.gif" align="absmiddle" /></td>--%>
						<%--								--%>
						<%--									<td>--%>
						<%--										<html:text property="addEndDate"  --%>
						<%--										 styleId="date7" onfocus="popupCalender('date7')"--%>
						<%--										  readonly="true"></html:text>--%>
						<%--									</td>--%>
						<%--									</tr>--%>
						<tr align="center">
							<td colspan="4" align="center">
								<logic:notEmpty name="addressAdd">
									<input type="button" class="rounded" style="width: 120px"
										value="Add Address" onclick="addAddress('addressDetails','${joiningReportForm.id }')"
										style="width:120" />
								</logic:notEmpty>
								<logic:notEmpty name="modifyAddress">

									<input type="button" class="rounded" style="width: 120px"
										value="Modify" onclick="modifyAddress(${joiningReportForm.id })" style="width:120" />
									<input type="button" class="rounded" style="width: 120px"
										value="Delete" onclick="deleteAddress(${joiningReportForm.id })" style="width:120" />
									<input type="button" class="rounded" style="width: 120px"
										value="Close" onclick="openDivs('addressDetails','${joiningReportForm.id }')"
										style="width:120" />

								</logic:notEmpty>
							</td>
						</tr>
					</table>

					<logic:notEmpty name="listName">

						<br />
						<hr />
						<br />
						<table class="bordered" width="100%">
							<thead>
								<tr>
									<th align="center">
										Edit
									</th>
									<th align="center">
										Address Type
									</th>
									<th align="center">
										Care Of contact Name
									</th>
									<th align="center">
										House Number
									</th>
									<th align="center">
										Postal Code
									</th>
									<th align="center">
										City
									</th>

								</tr>
							</thead>

							<logic:iterate name="listName" id="listid">
								<%
								JoiningReportForm empForm = (JoiningReportForm) listid;
								%>
								<tr bgcolor="white">
									<td>
										<a href="#"><img src="images/edit.png"
												onclick="displayAddress(<%=empForm.getNewid()%>)" />
										</a>
									</td>
									<td>
										<html:select name="listid" property="addressType1"
											value='<%=empForm.getAddressType()%>' disabled="true">
											<html:option value="">--Select--</html:option>
											<html:option value="001">Permanent</html:option>
									<html:option value="002">Temporary</html:option>
									<html:option value="003">Mailing</html:option>
									<html:option value="004">Emergency</html:option>
                                    <html:option value="005">Address during leave of absence</html:option>
									<html:option value="006">Current Address</html:option>
										</html:select>
									</td>
									<td>

										<html:text name="listid" property="careofcontactname1"
											value='<%=empForm.getCareofcontactname()%>' disabled="true"></html:text>

									</td>
									<td>
										<html:text name="listid" property="houseNumber1"
											value='<%=empForm.getHouseNumber()%>' disabled="true"></html:text>

									</td>
									<td>
										<html:text name="listid" property="postalCode1"
											value='<%=empForm.getPostalCode()%>' disabled="true"></html:text>

									</td>
									<td>
										<html:text name="listid" property="city1"
											value='<%=empForm.getCity()%>' disabled="true"></html:text>

									</td>

								</tr>

							</logic:iterate>



						</table>

					</logic:notEmpty>
				</div>

			</logic:notEmpty>

			<%--FAMILY DETAILS--%>

			<logic:notEmpty name="familyDetails">
				<div id="familyDetails" style="width: 90%;">
					<table width="90%" class="bordered">
						<tr>
							<th colspan="5">
								<center>
									Family Details
								</center>
							</th>
						</tr>
						<tr>
							<td>
								Relationship
								<font color="red">*</font>
							</td>

                       <logic:notEmpty name="modifyFamily">
							<td>
								<html:select property="familyDependentTypeID"
									styleClass="no">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="relationIDList" labelProperty="relationValueList" />
								</html:select>
							</td>
							</logic:notEmpty>
							
							<logic:notEmpty name="addFamily">
							<td>
								<html:select property="familyDependentTypeID"
									styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="relationIDList" labelProperty="relationValueList" />
								</html:select>
							</td>
							
							</logic:notEmpty>
							
							<td>
								Title
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="ftitle" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="Mr"></html:option>
									<html:option value="Mrs"></html:option>
									<html:option value="Miss"></html:option>
									<html:option value="Ms"></html:option>
									<html:option value="Dr"></html:option>
									<html:option value="Late"></html:option>
								</html:select>
							</td>

						</tr>
						<tr>
							<td>
								First Name
								<font color="red">*</font>
							</td>

							<td>
								<html:text property="ffirstName" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>

							<td>
								Middle Name
							</td>

							<td>
								<html:text property="fmiddleName" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						</tr>
						<tr>
							<td>
								Last Name
							</td>

							<td>
								<html:text property="flastName" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>

							<td>
								Initials
							</td>

							<td>
								<html:text property="finitials" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>


						</tr>
						<tr>
							<td>
								Gender
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="fgender" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="M">Male</html:option>
									<html:option value="F">Female</html:option>
								</html:select>
							</td>
							<td>
								Date of Birth
							
								<img src="images/date_icon.gif" align="absmiddle" />
							</td>

							<td>
							
							
								<html:text property="fdateofBirth" 
									styleId="familydateofBirthID" 
									readonly="true" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						</tr>
						<tr>
							<td>
								Birth Place
							</td>

							<td>
								<html:text property="fbirthplace" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
							<td>
								Telephone Number
							</td>

							<td>
								<html:text property="ftelephoneNumber" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						</tr>
						<tr>
							<td>
								Mobile Number
							</td>
							<td>
								<html:text property="fmobileNumber" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
							<td>
								eMail
							</td>

							<td>
								<html:text property="femailAddress" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						</tr>
						<tr>
							<td>
								Blood Group
							</td>
							<td>
								<html:select property="fbloodGroup" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="A+"></html:option>
									<html:option value="AB+"></html:option>
									<html:option value="B-"></html:option>
									<html:option value="AB-"></html:option>
									<html:option value="B+"></html:option>
									<html:option value="O+"></html:option>
									<html:option value="A-"></html:option>
									<html:option value="O-"></html:option>
								</html:select>
							</td>
							<td>
								Dependent
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="fdependent" styleClass="text_field" >
									<html:option value="">--Select--</html:option>
									<html:option value="Yes"></html:option>
									<html:option value="No"></html:option>

								</html:select>
							</td>
						</tr>
						<tr><td>Nominee<font color="red">*</font></td><td colspan="3">
								<html:select property="fnominee" styleClass="text_field" >
									<html:option value="">--Select--</html:option>
									<html:option value="Yes"></html:option>
									<html:option value="No"></html:option>

								</html:select>
							</td></tr>
						<tr>
							<td>
								Employee of Company
							</td>

							<td>
								<html:select property="femployeeofCompany"
									styleClass="text_field">
									<html:option value="No"></html:option>
									<html:option value="Yes"></html:option>
								</html:select>
							</td>
							<td>
								Employee Number
							</td>
							<td>
								<html:text property="femployeeNumber" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
								<html:hidden property="reqFamilyID" />
							</td>

						</tr>
						<tr>
							<td colspan="4" align="center">
								<logic:notEmpty name="addFamily">
									<html:button value="Add Family" styleClass="rounded"
										style="width: 120px;" property="method"
										onclick="addFamily('familyDetails','${joiningReportForm.id }')" />

								</logic:notEmpty>
								<logic:notEmpty name="modifyFamily">

									<input type="button" class="rounded" style="width: 120px"
										value="Modify" onclick="modifyFamily()" style="width:120" />
									<input type="button" class="rounded" style="width: 120px"
										value="Delete" onclick="deleteFamily()" style="width:120" />
									<input type="button" class="rounded" style="width: 120px"
										value="Close" onclick="openDivs('familyDetails','${joiningReportForm.id }')"
										style="width:120" />

								</logic:notEmpty>
							</td>
						</tr>
					</table>

					<logic:notEmpty name="listName">
						<br />
						<hr />
						<br />


						<table class="bordered" width="90%">
							<thead>
								<tr>
									<th align="center">
										Edit
									</th>
									<th align="center">
										Relationship
									</th>
									<th align="center">
										First Name
									</th>
									<th align="center">
										Date of Birth
									</th>
									<th align="center">
										Telephone No.
									</th>
									<th align="center">
										Mobile No.
									</th>
								</tr>

							</thead>

							<logic:iterate name="listName" id="listid">
								<%
								JoiningReportForm empForm = (JoiningReportForm) listid;
								%>
								<tr>
									<td>
										<a href="#"><img src="images/edit.png"
												onclick="displayFamily('<%=empForm.getNewid()%>')" />
										</a>
									</td>
									<td>
										<html:select name="listid" property="familyDependentTypeID1"
											value='<%=empForm.getFamilyDependentTypeID()%>'
											disabled="true">
											<html:option value="">--Select--</html:option>
											<html:options name="joiningReportForm"
												property="relationIDList" labelProperty="relationValueList" />

										</html:select>
									</td>

									<td>
										<html:text name="listid" property="ffirstName1"
											value='<%=empForm.getFfirstName()%>' disabled="true"></html:text>

									</td>
									<td>
										<html:text name="listid" property="fdateofBirth1"
											value='<%=empForm.getFdateofBirth()%>' disabled="true"></html:text>

									</td>


									<td>
										<html:text name="listid" property="ftelephoneNumber1"
											value='<%=empForm.getFtelephoneNumber()%>' disabled="true"></html:text>

									</td>
									<td>
										<html:text name="listid" property="fmobileNumber1"
											value='<%=empForm.getFmobileNumber()%>' disabled="true"></html:text>

									</td>




								</tr>

							</logic:iterate>





						
						</table>

					</logic:notEmpty>
				</div>

			</logic:notEmpty>
			<%--EDUCATIONAL DETAILS--%>



			<logic:notEmpty name="educationDetails">
				<div id="educationDetails" style="width: 90%;">
					<table width="90%" class="bordered">
						<tr>
							<th colspan="5">
								<center>
									Educational Details
								</center>
							</th>
						</tr>
						<tr>
							<td>
								Education 
								<font color="red">*</font>
							</td>
							<td>
								<html:select property="educationalLevel" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm" property="eduIDList"
										labelProperty="eduValueList" />

								</html:select>
								<html:hidden property="educationStatus" />
							</td>
									
							<td>
								Qualification
								<font color="red">*</font>
							</td>
<logic:notEmpty name="addEducation">
							<td>
								<html:select property="qualification" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="qulificationID"
										labelProperty="qulificationValueList" />
								</html:select>
							</td>
</logic:notEmpty>

<logic:notEmpty name="modifyEducation">

<td>
								<html:select property="qualification" styleClass="no">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="qulificationID"
										labelProperty="qulificationValueList" />
								</html:select>
							</td>
</logic:notEmpty>

						</tr>
						<tr>
																 <td>Specialization</td>
																<td colspan="3">
																	<html:text property="specialization" styleClass="text_field" style="width: 377px; " onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
																</td></tr><tr>
							<td>
								University Name
							</td>
							<td>
								<html:text property="universityName" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
							<td>
								University Location
							</td>
							<td>
								<html:text property="univerysityLocation"
									styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						</tr>
						<tr>
							<td>
								Country
								<font color="red">*</font>
							</td>
							<td>
								<html:select property="edcountry" styleClass="text_field"
									onchange="getEduStaes()">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="countryList" labelProperty="countryLabelList" />

								</html:select>
							</td>

							<td>
								State
								<font color="red">*</font>
							</td>
							<td>
								<html:select property="edstate" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm" property="stateList"
										labelProperty="stateLabelList" />

								</html:select>
							</td>
						</tr>
						<tr>
							<td>
								Duration of Course
							</td>
							<td colspan="1">
								<html:text property="durationofCourse" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')" ></html:text>
							</td>
							<td>Year of Passing</td><td colspan="1">
								<html:text property="yearofpassing" styleClass="text_field" maxlength="4" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
						</tr>
						<%-- <tr>
																<td>Time <font color="red">*</font></td>
															
															<td>
																	<html:select property="times" styleClass="text_field">
																		<html:option value="">--SELECT--</html:option>
																		<html:option value="1"></html:option>
																		<html:option value="2"></html:option>
																		<html:option value="3"></html:option>
																		<html:option value="4"></html:option>
																		
																	</html:select>
																</td>
							<td>
								From Date
						
								<img src="images/date_icon.gif" align="absmiddle" />
							</td>

							<td>
								<html:text property="fromDate" 
									styleId="eduFromDtId" 
									readonly="true"></html:text>
							</td>
							<td>
								To Date
								
								<img src="images/date_icon.gif" align="absmiddle" />
							</td>

							<td>
								<html:text property="toDate" 
									styleId="eduToDtId" 
									readonly="true"></html:text>
							</td>
						</tr> --%>
						<tr>

							<td>
								Full Time / Part Time
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="fullTimePartTime" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="F">FullTime</html:option>
									<html:option value="P">PartTime</html:option>
									<html:option value="C">Correspondence</html:option>

								</html:select>
							</td>
							<td>
								Percentage
								<font color="red">*</font>
							</td>

							<td colspan="4">
								<html:text property="percentage" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
								<html:hidden property="reqEduID" />

							</td>
						</tr>
						
						
						<tr>
					
						<td colspan="4">
								<html:file name="joiningReportForm" property="empEdu" styleClass="rounded" style="width: 220px" />&nbsp;&nbsp;	
									<html:button value="Upload" onclick="onUploadEdu()" property="method" styleClass="rounded" style="width: 100px" />
								</td>
							
                        </tr>
                        	
                        <html:hidden property="saveStatus"/>
                        
						<tr>
							<td colspan="4" align="center">
								<logic:notEmpty name="addEducation">
									<html:button value="Add Education" styleClass="rounded"
										style="width: 120px;" property="method"
										onclick="addEducation('educationDetails','${joiningReportForm.id }')" />
								</logic:notEmpty>
								<logic:notEmpty name="modifyEducation">

									<input type="button" class="rounded" style="width: 120px"
										value="Modify" onclick="modifyEdu()" style="width:120" />
									<input type="button" class="rounded" style="width: 120px"
										value="Delete" onclick="deleteEdu()" style="width:120" />
									<input type="button" class="rounded" style="width: 120px"
										value="Close" onclick="openDivs('educationDetails','${joiningReportForm.id }')"
										style="width:120" />

								</logic:notEmpty>
							</td>
						</tr>
					</table>

					<logic:notEmpty name="listName">
						<br />
						<hr />
						<br />

						<table class="bordered" align="center">
							<thead>
								<tr>
									<th align="center">
										Edit
									</th>
									<th align="center">
										Educational Level
									</th>
									<th align="center">
										Qualification
									</th>

									<th align="center">
										University Name
									</th>

									<th align="center">
										Percentage
									</th>
                                 <th align="center">
										Documents
									</th>

								</tr>

							</thead>

							<logic:iterate name="listName" id="listid">
								<%
								JoiningReportForm empForm = (JoiningReportForm) listid;
								%>
								<tr bgcolor="white">

									<td>
										<a href="#"><img src="images/edit.png"
												onclick="displayEducation('<%=empForm.getNewid()%>')" />
										</a>

									</td>

									<td>
										<html:select name="listid" property="educationalLevel1"
											value='<%=empForm.getEducationalLevel()%>' disabled="true">
											<html:option value="">--Select--</html:option>
											<html:options name="joiningReportForm"
												property="eduIDList" labelProperty="eduValueList" />

										</html:select>
									</td>
									<td>
										<html:select name="listid" property="qualification1"
											value='<%=empForm.getQualification()%>' disabled="true">
											<html:option value="">--Select--</html:option>
											<html:options name="joiningReportForm"
												property="qulificationID"
												labelProperty="qulificationValueList" />
										</html:select>
									</td>
									<td>
										<html:text name="listid" property="universityName1"
											value='<%=empForm.getUniversityName()%>' disabled="true"></html:text>
									</td>


									<td>
										<html:text name="listid" property="percentage1"
											value='<%=empForm.getPercentage()%>' disabled="true"></html:text>
									</td>
									
										<logic:present name="edudoc">
									<td>
									
					             
								<a href="/EMicro Files/ESS/JoinEducationDocuments/<%=empForm.getEmpEduDoc()%>" target="_blank" ><%=empForm.getEmpEduDoc()%> </a>
									
										
                                </td>
                                </logic:present>
								</tr>

							</logic:iterate>

						</table>
				</div>
			</logic:notEmpty>

			</logic:notEmpty>
			<%--EXPERIENCE DETAILS--%>

			<logic:notEmpty name="experienceDetails">
				<div id="experience" style="width: 90%;">
					<table width="90%" class="bordered">
						<tr>
							<th colspan="5">
								<center>
									Experience
								</center>
							</th>
						</tr>
						<tr>
							<td>
								Company Name
								<font color="red">*</font>
							</td>
							<td>
								<html:text property="nameOfEmployer" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
							<td>
								Industry
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="industry" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm" property="industyID"
										labelProperty="industyValueList" />
								</html:select>
							</td>
						</tr>
						<tr>
							<td>
								City
							</td>
							<td>
								<html:text property="exCity" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
							<td>
								Country
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="excountry" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm"
										property="countryList" labelProperty="countryLabelList" />

								</html:select>
							</td>
						</tr>
						<tr>
							<td>
								Position Held
								<font color="red">*</font>
							</td>

							<td>
								<html:text property="positionHeld" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"></html:text>
							</td>
							<td>
								Job Role
							</td>

							<td>
								<html:textarea property="jobRole" onkeyup="this.value = this.value.replace(/'/,'`')"></html:textarea>

							</td>
						</tr>
						<tr>
							<td>
								Start Date
								<font color="red">*</font>
								<img src="images/date_icon.gif" align="absmiddle" />
							</td>

							<td>
								<html:text property="startDate" 
									styleId="expStDateID" 
									readonly="true"></html:text>
							</td>
							<td>
								End Date
								<font color="red">*</font>
								<img src="images/date_icon.gif" align="absmiddle" />
							</td>

							<td>
								<html:text property="endDate" 
									styleId="expEndDateID" 
									readonly="true"></html:text>

								<html:hidden property="reqExpID" />
							</td>
							
						</tr>
						<tr>
						<td>Micro Experience</td><td><html:checkbox property="microExp"  onclick="makeEmpNoNmand(this.value)"/>&nbsp;Yes
						
						 </td>
						<td>Employee Number<div id="microNoId" style="visibility: hidden"><font color="red">*</font></div>
						</td><td><html:text property="microNo" styleClass="text_field" onkeyup="this.value = this.value.replace(/'/,'`')"/></td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								<logic:notEmpty name="addExperience">

									<html:button value="Add Experience" styleClass="rounded"
										style="width: 120px;" property="method"
										onclick="addExpirience('experienceDetails','${joiningReportForm.id }')" />
								</logic:notEmpty>
								<logic:notEmpty name="modifyExperience">

									<input type="button" class="rounded" style="width: 120px"
										value="Modify" onclick="modifyExprience()" style="width:120" />
									<input type="button" class="rounded" style="width: 120px"
										value="Delete" onclick="deleteExprience()" style="width:120" />
									<input type="button" class="rounded" style="width: 120px"
										value="Close" onclick="openDivs('experienceDetails','${joiningReportForm.id }')"
										style="width:120" />

								</logic:notEmpty>
							</td>
							
						</tr>
					</table>
					<logic:notEmpty name="listName">
						<br />
						<hr />
						<br />

						<table class="bordered" align="center" width="90%">
							<thead>
								<tr>
									<th align="center">
										Edit
									</th>
									<th align="center">
										Company Name
									</th>


									<th align="center">
										City
									</th>

									<th align="center">
										PositionHeld
									</th>

									<th align="center">
										Start Date
									</th>
									<th align="center">
										End date
									</th>

								</tr>

							</thead>

							<logic:iterate name="listName" id="listid">
								<%
								JoiningReportForm empForm = (JoiningReportForm) listid;
								%>
								<tr bgcolor="white">

									<td>
										<a href="#"><img src="images/edit.png"
												onclick="displayExperiance('<%=empForm.getNewid()%>')" />
										</a>

									</td>
									<td>
										<html:text name="listid" property="nameOfEmployer1"
											value='<%=empForm.getNameOfEmployer()%>' disabled="true"></html:text>
									</td>



									<td>
										<html:text name="listid" property="exCity1"
											value='<%=empForm.getExCity()%>' disabled="true"></html:text>
									</td>


									<td>
										<html:text property="positionHeld1" name="listid"
											value='<%=empForm.getPositionHeld()%>' disabled="true"></html:text>
									</td>


									<td>
										<html:text property="startDate1" name="listid"
											value='<%=empForm.getStartDate()%>' disabled="true" />
									</td>

									<td>
										<html:text property="endDate1" name="listid"
											value='<%=empForm.getEndDate()%>' disabled="true" />
									</td>




								</tr>

							</logic:iterate>

						</table>
				</div>
			</logic:notEmpty>


			</logic:notEmpty>

			<logic:notEmpty name="languageDetails">
				<div id="language" style="width: 90%;">
					<table width="90%" class="bordered">
						<tr>
							<th colspan="5">
								<center>
									Language
								</center>
							</th>
						</tr>
						<tr>
							<td>
								Language
								<font color="red">*</font>
							</td>
<logic:notEmpty name="addLanguage">
							<td>
								<html:select property="language" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm" property="languageID"
										labelProperty="languageValueList" />

								</html:select>
							</td>
							</logic:notEmpty>
								<logic:notEmpty name="modifyLanguage">
								<td>
								<html:select property="language" styleClass="no">
									<html:option value="">--Select--</html:option>
									<html:options name="joiningReportForm" property="languageID"
										labelProperty="languageValueList" />

								</html:select>
							</td>
								</logic:notEmpty>
							
							<td>
								Mother Tongue
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="motherTongue" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="Yes"></html:option>
									<html:option value="No"></html:option>

								</html:select>
							</td>
						</tr>
						<tr>
							<td>
								Speaking
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="langSpeaking" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="1">N/A</html:option>
											<html:option value="2">Low</html:option>
											<html:option value="3">Medium</html:option>
											<html:option value="4">High</html:option>


								</html:select>
							</td>
							<td>
								Reading
								<font color="red">*</font>
							</td>

							<td>
								<html:select property="langRead" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="1">N/A</html:option>
											<html:option value="2">Low</html:option>
											<html:option value="3">Medium</html:option>
											<html:option value="4">High</html:option>

								</html:select>
							</td>
						</tr>
						<tr>
							<td>
								Writing
								<font color="red">*</font>
							</td>

							<td colspan="3">
								<html:select property="langWrite" styleClass="text_field">
									<html:option value="">--Select--</html:option>
									<html:option value="1">N/A</html:option>
											<html:option value="2">Low</html:option>
											<html:option value="3">Medium</html:option>
											<html:option value="4">High</html:option>

								</html:select>
								<html:hidden property="reqLangID" />
							</td>
							<%--								<td>Start Date <font color="red">*</font> <img src="images/date_icon.gif" align="absmiddle" /></td>--%>
							<%--	--%>
							<%--									--%>
							<%--										<td>--%>
							<%--										<html:text property="langstartDate" styleClass="textfield" --%>
							<%--										 styleId="date10" onfocus="popupCalender('date10')"--%>
							<%--										  readonly="true"></html:text>--%>
							<%--									</td>--%>
							<%--									--%>
							<%--								</tr>--%>
							<%--								<tr>--%>
							<%--								<td>End Date <font color="red">*</font> <img src="images/date_icon.gif" align="absmiddle" /></td>--%>
							<%--	--%>
							<%--									--%>
							<%--										<td>--%>
							<%--										<html:text property="langendDate" styleClass="textfield" --%>
							<%--										 styleId="date20" onfocus="popupCalender('date20')"--%>
							<%--										  readonly="true"></html:text>--%>
							<%--									</td>--%>
							<%--								--%>
						</tr>
						<tr>
							<td colspan="4" align="center">
								<logic:notEmpty name="addLanguage">
									<html:button value="Add Language" styleClass="rounded"
										style="width: 120px;" property="method"
										onclick="addLanguage('languageDetails','${joiningReportForm.id }')" />
								</logic:notEmpty>
								<logic:notEmpty name="modifyLanguage">

									<input type="button" class="rounded" style="width: 120px"
										value="Modify" onclick="modifyLanguage()" style="width:120" />
									<input type="button" class="rounded" style="width: 120px"
										value="Delete" onclick="deleteLanguage()" style="width:120" />
									<input type="button" class="rounded" style="width: 120px"
										value="Close" onclick="openDivs('languageDetails','${joiningReportForm.id }')"
										style="width:120" />

								</logic:notEmpty>
							</td>
						</tr>

					</table>
					<logic:notEmpty name="listName">
						<br />

						<hr />
						<br />

						<table class="bordered" width="90%">
							<thead>
								<tr>
									<th>
										Edit
									</th>

									<th align="center">
										Language
									</th>


									<th align="center">
										Mother Tongue
									</th>
									<th align="center">
										Speaking
									</th>
									<th align="center">
										Reading
									</th>
									<th align="center">
										Writing
									</th>
									<%--							<th  align="center">--%>
									<%--								Start Date--%>
									<%--							</th>--%>
									<%--							<th  align="center">--%>
									<%--								End Date--%>
									<%--							</th>--%>


								</tr>

							</thead>

							<logic:iterate name="listName" id="listid">
								<%
								JoiningReportForm empForm = (JoiningReportForm) listid;
								%>
							
								<tr bgcolor="white">

									<td>
										<a href="#"><img src="images/edit.png"
												onclick="displayLanguage('<%=empForm.getNewid()%>')" />
										</a>

									</td>

									<td>
										<html:select property="language1" name="listid"
											value='<%=empForm.getLanguage()%>' disabled="true">
											<html:option value="">--Select--</html:option>
											<html:options name="joiningReportForm"
												property="languageID" labelProperty="languageValueList" />

										</html:select>
									</td>


									<td>
										<html:select property="motherTongue1" name="listid"
											value='<%=empForm.getMotherTongue()%>' disabled="true">
											<html:option value="">--Select--</html:option>
											<html:option value="Yes"></html:option>
											<html:option value="No"></html:option>

										</html:select>
									</td>


									<td>
										<html:select property="langSpeaking1" name="listid"
											value='<%=empForm.getLangSpeaking()%>' disabled="true">
											<html:option value="">--Select--</html:option>
											<html:option value="1">N/A</html:option>
											<html:option value="2">Low</html:option>
											<html:option value="3">Medium</html:option>
											<html:option value="4">High</html:option>

										</html:select>
									</td>

									<td>
										<html:select property="langRead1" name="listid"
											value='<%=empForm.getLangRead()%>' disabled="true">
											<html:option value="">--Select--</html:option>
											<html:option value="1">N/A</html:option>
											<html:option value="2">Low</html:option>
											<html:option value="3">Medium</html:option>
											<html:option value="4">High</html:option>

										</html:select>
									</td>

									<td>
										<html:select property="langWrite1" name="listid"
											value='<%=empForm.getLangWrite()%>' disabled="true">
											<html:option value="">--Select--</html:option>
											<html:option value="1">N/A</html:option>
											<html:option value="2">Low</html:option>
											<html:option value="3">Medium</html:option>
											<html:option value="4">High</html:option>

										</html:select>
									</td>
									<%--										<td>--%>
									<%--										<html:text property="langstartDate1" name="listid" value='<%=empForm.getLangstartDate() %>'/>--%>
									<%--									</td>--%>
									<%--										<td>--%>
									<%--										<html:text property="langendDate1" name="listid" value='<%=empForm.getLangendDate() %>'/>--%>
									<%--									</td>--%>



								</tr>

							</logic:iterate>

						</table>
					</logic:notEmpty>
				</div>

			</logic:notEmpty>
			<br />
		  
          	<div align="center" id="messageID1">
			<logic:present name="joiningReportForm" property="message">
				<font color="green" size="4"> <bean:write
						name="joiningReportForm" property="message" /> </font>
						<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
			</logic:present>
			<logic:present name="joiningReportForm" property="message1">
				<font color="red" size="4"> <bean:write
						name="joiningReportForm" property="message1" /> </font>
						<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
			</logic:present>
		</div>
          
		</html:form>
	</table>
	</body>
</html>
