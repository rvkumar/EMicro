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
<jsp:directive.page import="com.microlabs.ess.form.JoiningFormalityForm" />

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


<%--     Calender   --%>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
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
	$('#expStDateID').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});


$(function() {
	$('#expEndDateID').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
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
  

	
function onSave(){

               		
	var url="ess.do?method=submit";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function parseDate(str) {
    var mdy = str.split('/');
    return new Date(mdy[2], mdy[1]-1, mdy[0]);
}

function  savePersonalInfo(param)
{

if(document.forms[0].title.value=="")
	    {
	      alert("Title should not be left blank");
	      document.forms[0].title.focus();
	      return false;
	    }
	    
	    
	    if(document.forms[0].initials.value!=""){
	    
	 var st = document.forms[0].initials.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].initials.value=st; 
	    
	    
	    }
	    
	      if(document.forms[0].middleName.value!=""){
	      
	  var st = document.forms[0].middleName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].middleName.value=st; 
	    
	    
	    }
	     if(document.forms[0].lastName.value!=""){
	     
	var st = document.forms[0].lastName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].lastName.value=st;    
	    
	    }
	     if(document.forms[0].nickName.value!=""){
	     
	var st = document.forms[0].nickName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].nickName.value=st; 	    
	    
	    }
	  if(document.forms[0].firstName.value=="")
	    {
	      alert("First Name should not be left blank");
	      document.forms[0].firstName.focus();
	      return false;
	    }
	     
	   if(document.forms[0].firstName.value!=""){
	   
var st = document.forms[0].firstName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].firstName.value=st;
	    
	    
	    }
	    
	    
	 if(document.forms[0].gender.value=="")
	    {
	      alert("Gender should not be left blank");
	      document.forms[0].gender.focus();
	      return false;
	    }
	    
	   if(document.forms[0].maritalStatus.value=="")
	    {
	      alert("Marital Status should not be left blank");
	      document.forms[0].maritalStatus.focus();
	      return false;
	    }
	     
	  if(document.forms[0].dateofBirth.value=="")
	    {
	      alert("Date of Birth should not be left blank");
	      document.forms[0].dateofBirth.focus();
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
	        if(document.forms[0].mobileNumber.value!="")
	    {
	   
	    
	     var mobileNumber = document.forms[0].mobileNumber.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(mobileNumber)) {
             alert("MobileNumber  Should be Integer ");
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
	      
	      alert("Emergency Contact Person Name  should not be left blank");
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
             alert("EmergencyTelephoneNumber  Should be Integer ");
                document.forms[0].emergencyTelephoneNumber.focus();
            return false;
        }
        }
        
        if(document.forms[0].emergencyMobileNumber.value=="")
	    {
	     alert("Emergency Mobile Number should not be left blank");
	      document.forms[0].physicallyChallengeddetails.focus();
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

	     
	       if(document.forms[0].emergencyContactAddressLine3.value!=""){
	      var st = document.forms[0].emergencyContactAddressLine3.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].emergencyContactAddressLine3.value=st;
}
	  	       if(document.forms[0].emergencyContactAddressLine4.value!=""){
	var st = document.forms[0].emergencyContactAddressLine4.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].emergencyContactAddressLine4.value=st;
}  
 
 
 if(document.forms[0].emergencyTelephoneNumber1.value!="")
	    {
	   var emergencyTelephoneNumber1 = document.forms[0].emergencyTelephoneNumber1.value;
        var pattern =/^\d+(\d+)?$/
        if (!pattern.test(emergencyTelephoneNumber1)) {
             alert("EmergencyTelephoneNumber  Should be Integer ");
                document.forms[0].emergencyTelephoneNumber1.focus();
            return false;
        }
        }
        
    
if(document.forms[0].emergencyMobileNumber1.value!="")
	    {
	     var emergencyMobileNumber1 = document.forms[0].emergencyMobileNumber1.value;
       var pattern = /^\d+(\d+)?$/
        if (!pattern.test(emergencyMobileNumber1)) {
             alert("EmergencyMobileNumber  Should be Integer ");
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
	    
	var url="ess.do?method=savePersonalInfo&param="+param;
	document.forms[0].action=url;
	document.forms[0].submit();
	
}	

function addAddress(param)
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
 

	var url="ess.do?method=saveAddress&param="+param;
	document.forms[0].action=url;
	document.forms[0].submit();
	
}	
function selectTempAddress(param)
{
	var url="ess.do?method=selectTempAddress&param="+param;
	document.forms[0].action=url;
	document.forms[0].submit();
	
}
function addFamily(param)
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
	    if(document.forms[0].fdateofBirth.value=="")
	    {
	      alert("Date of Birth should not be left blank");
	      document.forms[0].fdateofBirth.focus();
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

	var url="ess.do?method=saveFamilyDetails&param="+param;
	document.forms[0].action=url;
	document.forms[0].submit();
	
	
}
function addEducation(param)
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
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(durationofCourse)
        ) {
             alert("DurationOfCourse Should be Integer ");
             document.forms[0].durationofCourse.focus();
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
 if(document.forms[0].fromDate.value=="")
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

	var url="ess.do?method=saveEducationDetails&param="+param;
	document.forms[0].action=url;
	document.forms[0].submit();
	
	
}
function addExpirience(param)
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
         
           



	var url="ess.do?method=saveExpirienceDetails&param="+param;
	document.forms[0].action=url;
	document.forms[0].submit();
	
	
}
function addLanguage(param)
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
	   


	var url="ess.do?method=saveLanguage&param="+param;
	document.forms[0].action=url;
	document.forms[0].submit();
	
}




function openDivs(param)
{

var emp=document.forms[0].employeeNumber.value;

var req=document.forms[0].requestNumber.value;

	var url="approvals.do?method=viewPersonalInfo&param="+param+"&empId="+emp+"&reqno="+req;
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
					var URL="ess.do?method=modifyAddress&param1="+param1+"&param2="+param2;
						
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
						var URL="ess.do?method=modifyAddress&param1="+param1+"&param2="+param2;
							
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
					var URL="ess.do?method=modifyFamily&param1="+param1+"&param2="+param2;
						
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
						var URL="ess.do?method=modifyFamily&param1="+param1+"&param2="+param2;
							
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


 if(document.forms[0].educationalLevel1.value=="")
	    {
	      alert("Educational Level should not be left blank");
	      document.forms[0].educationalLevel1.focus();
	      return false;
	    }
	    else if(document.forms[0].qualification1.value=="")
	    {
	      alert("Qualification should not be left blank");
	      document.forms[0].qualification1.focus();
	      return false;
	    }
	   
	    else if(document.forms[0].universityName1.value=="")
	    {
	      alert("University Name should not be left blank");
	      document.forms[0].universityName1.focus();
	      return false;
	    }
	    else if(document.forms[0].univerysityLocation1.value=="")
	    {
	      alert("Univerysity Location should not be left blank");
	      document.forms[0].univerysityLocation1.focus();
	      return false;
	    }
	    else if(document.forms[0].edstate1.value=="")
	    {
	      alert("State (Educational Details) should not be left blank");
	      document.forms[0].edstate1.focus();
	      return false;
	    }
	    else if(document.forms[0].edcountry1.value=="")
	    {
	      alert("Country (Educational Details) should not be left blank");
	      document.forms[0].edcountry1.focus();
	      return false;
	    }
<%--	    else if(document.forms[0].durationofCourse1.value=="")--%>
<%--	    {--%>
<%--	      alert("Duration of Course should not be left blank");--%>
<%--	      document.forms[0].durationofCourse1.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	    else if(document.forms[0].times1.value=="")--%>
<%--	    {--%>
<%--	      alert("Time should not be left blank");--%>
<%--	      document.forms[0].times1.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	    else if(document.forms[0].fromDate1.value=="")--%>
<%--	    {--%>
<%--	      alert("From should not be left blank");--%>
<%--	      document.forms[0].fromDate1.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
<%--	    else if(document.forms[0].toDate1.value=="")--%>
<%--	    {--%>
<%--	      alert("To should not be left blank");--%>
<%--	      document.forms[0].toDate1.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
	    else if(document.forms[0].fullTimePartTime1.value=="")
	    {
	      alert("FullTime/PartTime should not be left blank");
	      document.forms[0].fullTimePartTime1.focus();
	      return false;
	    }
	    else if(document.forms[0].percentage1.value=="")
	    {
	      alert("Percentage should not be left blank");
	      document.forms[0].percentage1.focus();
	      return false;
	    }
					
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
					var URL="ess.do?method=modifyEducation&param1="+param1+"&param2="+param2;
						
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
						var URL="ess.do?method=modifyEducation&param1="+param1+"&param2="+param2;
							
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
					var URL="ess.do?method=modifyExperience&param1="+param1+"&param2="+param2;
						
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
						var URL="ess.do?method=modifyExperience&param1="+param1+"&param2="+param2;
							
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
					var URL="ess.do?method=modifyLanguage&param1="+param1+"&param2="+param2;
						
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
						var URL="ess.do?method=modifyLanguage&param1="+param1+"&param2="+param2;
							
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


		var URL="ess.do?method=displayState&Param=addressDetails";
		document.forms[0].action=URL;
 		document.forms[0].submit();

}

function getEduStaes(){
	var URL="ess.do?method=getEduStaes&Param=educationDetails";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function displayAddress(addressID){

var URL="ess.do?method=editAddress&addressID="+addressID;
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

var URL="ess.do?method=modifyAddress&reqAddressID="+reqAddressID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function deleteAddress(){


var reqAddressID=document.forms[0].reqAddressID.value;

var URL="ess.do?method=deletAddress&reqAddressID="+reqAddressID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}


function displayFamily(reqFamilyID){

var URL="ess.do?method=editFamily&familyID="+reqFamilyID;
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
	    var st = document.forms[0].fdependent.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].fdependent.value=st;




var reqFamilyID=document.forms[0].reqFamilyID.value;

var URL="ess.do?method=modifyFamily&reqFamilyID="+reqFamilyID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function deleteFamily(){


var reqFamilyID=document.forms[0].reqFamilyID.value;

var URL="ess.do?method=deleteFamily&reqFamilyID="+reqFamilyID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}



function displayEducation(eduID){


var URL="ess.do?method=editEducation&eduID="+eduID;
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
        if (!pattern.test(durationofCourse)) 
        {
             alert("DurationOfCourse Should be Integer ");
             document.forms[0].durationofCourse.focus();
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
	    
	   

 if(document.forms[0].fromDate.value=="")
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
          if(document.forms[0].percentage.value!="")
          {
          
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
var reqEduID=document.forms[0].reqEduID.value;

var URL="ess.do?method=modifyEducation&reqEduID="+reqEduID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
 
}
function deleteEdu(){


var reqEduID=document.forms[0].reqEduID.value;

var URL="ess.do?method=deleteEducation&reqEduID="+reqEduID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function displayExperiance(expID){


var URL="ess.do?method=editExprience&expID="+expID;
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
		    alert("Start Date cannot be greater than End Date"); 
		    return false; 
		} 
	}

        else
        {
        }
         
           


var reqExpID=document.forms[0].reqExpID.value;

var URL="ess.do?method=modifyExperience&reqExpID="+reqExpID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}


function deleteExprience(){


var reqExpID=document.forms[0].reqExpID.value;

var URL="ess.do?method=deleteExperience&reqExpID="+reqExpID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}


function displayLanguage(langID){


var URL="ess.do?method=editLanguage&langID="+langID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}
function modifyLanguage(){
var reqLangID=document.forms[0].reqLangID.value;

var URL="ess.do?method=modifyLanguage&reqLangID="+reqLangID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}
function deleteLanguage(){
var reqLangID=document.forms[0].reqLangID.value;

var URL="ess.do?method=deleteLanguage&reqLangID="+reqLangID;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}


function onUpload(){
var URL="ess.do?method=upLoadPhoto"
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
  

var URL="ess.do?method=upLoadEducationAttachments&Qual="+x;
		document.forms[0].action=URL;
 		document.forms[0].submit();

}

</script>

		<style type="text/css">
.text_field {
	background-color: #f6f6f6;
	width: 150px;
	height: 20px;
	border: #38abff 1px solid
}
</style>
	</head>
  
  <body>
  
  		<div align="center">
			<logic:present name="joiningFormalityForm" property="message">
				<font color="green" size="4"> <bean:write
						name="joiningFormalityForm" property="message" /> </font>
			</logic:present>
			<logic:present name="joiningFormalityForm" property="message1">
				<font color="red" size="4"> <bean:write
						name="joiningFormalityForm" property="message1" /> </font>
			</logic:present>
		</div>





		<html:form action="/approvals.do" enctype="multipart/form-data">

	<html:hidden name="approvalsForm" property="requestNumber" />
			<html:hidden  name="approvalsForm" property="employeeNumber" />
			
			
			<table align="center">
				<tr>
					<th align="center">
						<input type="button" class="rounded" value="Personal Information"
							onclick="openDivs('personalDetails')" style="width: 120px" />
					</th>
					<th align="center">
						<input type="button" class="rounded" value="Addresses"
							onclick="openDivs('addressDetails')" style="width: 120px" />
					</th>
					<th align="center">
						<input type="button" class="rounded" value="Family Details"
							onclick="openDivs('familyDetails')" style="width: 120px" />
					</th>
					<th align="center">
						<input type="button" class="rounded" value="Education Details"
							onclick="openDivs('educationDetails')" style="width: 120px" />
					</th>
					<th align="center">
						<input type="button" class="rounded" value="Experience"
							onclick="openDivs('experienceDetails')" style="width: 120px" />
					</th>
					<th align="center">
						<input type="button" class="rounded" value="Language"
							onclick="openDivs('languageDetails')" style="width: 120px" />
					</th>
				</tr>
			</table>
			<br />
			

			
			<%--PERSONAL INFORMATION DETAILS--%>
			
		
			
			<logic:notEmpty name="personalInfo">
					 <logic:iterate id="abc1" name="personalInfoHistory">	
					 	<%
								JoiningFormalityForm empFormApp = (JoiningFormalityForm) abc1;
								%>
              <logic:iterate id="abc" name="personalInfo">
				<%
								JoiningFormalityForm empForm = (JoiningFormalityForm) abc;
								%>
					<table width="90%" class="bordered">
						<tr>
							<th colspan="4"><center><big>Personal Information</big></center></th>
						</tr>
<!-- -------- ---------------------------------------- -->
						<tr>
							<th colspan="4"><center>Photo Display</center></th>
						</tr>	
											
						<tr>
						<td></td>
							<td  align="center" valign="middle" style="text-align: center;">
							<logic:notEmpty name="employeePhoto">
						
								<img src="/EMicro Files/images/EmpPhotos/${joiningFormalityForm.photoImage }"
									width="160px" height="160px" border="1" align="middle"  />
										</logic:notEmpty>	
							</td>
							
								
						
								
					
							
						
							<td  align="center" valign="middle" style="text-align: center;">
							<logic:notEmpty name="employeePhoto">
						
								<img src="/EMicro Files/images/EmpPhotos/${joiningFormalityForm.photoImage }"
									width="160px" height="160px" border="1" align="middle"  />
										</logic:notEmpty>	
							</td>
							
								
						
								
							
						</tr>
<!-- -------- ---------------------------------------- -->
	<tr >
							<td><b>
								Employee Name</b>
								<font color="red">*</font>
							</td>

							<td colspan="2">
							${abc.firstName}
								
							</td>
							
                       </tr>
                       
						<tr>

							<td><b>
								Gender</b>
								<font color="red">*</font>
							</td>
							<td colspan="2">
							${abc.gender}
								
							</td>
					
							
							</tr>
							
						
						<tr>
							<td><b>
								Date of Birth</b>
								<font color="red">*</font>
								<img src="images/date_icon.gif" align="absmiddle" />
							</td>

							<td colspan="2">
							${abc.dateofBirth}
								
							</td>
						
							
							</tr>
								
							
<tr><th>Columns</th><th>New details</th><th>Old details</th></tr>
                 <%
                 
                 
                 if((!(empFormApp.getTitle()== null) && !(empForm.getTitle().equalsIgnoreCase(""))) || !(empForm.getTitle()== null)&& !(empFormApp.getTitle()== null)) 
                 {
                 %>
						<tr>
							<td><b>
								Title</b>
								<font color="red">*</font>
							</td>
							<td>
								${abc.title}
							</td>
							<td>
								${abc1.title}
							</td>
						</tr>
						
				<%} %>
							<%	 if((!(empFormApp.getNickName()== null)&& !(empForm.getNickName().equalsIgnoreCase(""))) || !(empForm.getNickName()== null)&& !(empFormApp.getNickName()== null))
							{ 
                       	
                       		%>
							
							<tr>
							<td><b>
								Nickname</b>
							</td>

							<td>
							${abc.nickName}
								
							</td>
							<td>
							${abc1.nickName}
								
							</td>
						</tr>
						<%} %>	
					
							<%	 if((!(empFormApp.getMaritalStatus()== null)&& !(empForm.getMaritalStatus().equalsIgnoreCase(""))) || !(empForm.getMaritalStatus()== null)&& !(empFormApp.getMaritalStatus()== null))
							{ 
                       	
                       		%>
							<tr>
							<td><b>
								Marital Status</b>
								<font color="red">*</font>
							</td>

							<td>
							${abc.maritalStatus}
							
							</td>
							<td>
							${abc1.maritalStatus}
							
							</td>
						</tr>
							<%} %>	
							
								<%	 if((!(empFormApp.getBirthplace()== null)&& !(empForm.getBirthplace().equalsIgnoreCase(""))) || !(empForm.getBirthplace()== null)&& !(empFormApp.getBirthplace()== null))
							{ 
                       	
                       		%>
							<tr>
							<td><b>
								Birth Place</b>
							</td>

							<td>
							${abc.birthplace}
								
							</td>
							<td>
							${abc1.birthplace}
								
							</td>
						</tr>
						<%} %>	
						<%	 if((!(empFormApp.getCountryofBirth()== null)&& !(empForm.getCountryofBirth().equalsIgnoreCase(""))) || !(empForm.getCountryofBirth()== null)&& !(empFormApp.getCountryofBirth()== null))
							{ 
                       	
                       		%>
						<tr>
						
							<td><b>
								Country of Birth</b>
							</td>

							<td>
							${abc.countryofBirth}
								
							</td>
							<td>
							${abc1.countryofBirth}
								
							</td>
							
							</tr>
									<%} %>
									<%	 if((!(empFormApp.getCaste()== null)&& !(empForm.getCaste().equalsIgnoreCase(""))) || !(empForm.getCaste()== null)&& !(empFormApp.getCaste()== null))
							{ 
                       	
                       		%>
									
							<tr>
							<td><b>
								Caste</b>
							</td>

							<td>
							${abc.caste}
								
							</td>
							<td>
							${abc1.caste}
								
							</td>
						</tr>
						<%} %>
						<%	 if((!(empFormApp.getReligiousDenomination()== null)&& !(empForm.getReligiousDenomination().equalsIgnoreCase(""))) || !(empForm.getReligiousDenomination()== null)&& !(empFormApp.getReligiousDenomination()== null))
							{ 
                       	
                       		%>
						
						<tr>
							<td><b>
								Religious Denomination</b>
							</td>

							<td>
							${abc.religiousDenomination}
								
							</td>
							<td>
							${abc1.religiousDenomination}
								
							</td>
							</tr>
							<%} %>
								<%	 if((!(empFormApp.getNationality()== null)&& !(empForm.getNationality().equalsIgnoreCase(""))) || !(empForm.getNationality()== null)&& !(empFormApp.getNationality()== null))
							{ 
                       	
                       		%>
						<tr>
							<td><b>
								Nationality</b>
								<font color="red">*</font>
							</td>

							<td>
							${abc.nationality}
								
							</td>
							<td>
							${abc1.nationality}
								
							</td>
						</tr>
						<%} %>
						<%	 if((!(empFormApp.getTelephoneNumber()== null) && !(empForm.getTelephoneNumber().equalsIgnoreCase(""))) || !(empForm.getTelephoneNumber()== null)&& !(empFormApp.getTelephoneNumber()== null))
							{ 
                       	
                       		%>
						<tr>
							<td><b>
								Telephone Number</b>
							</td>

							<td>
							${abc.telephoneNumber}
								
							</td>
								<td>
							${abc1.telephoneNumber}
								
							</td>
							</tr>
							<%} %>
							<%	 if((!(empFormApp.getMobileNumber()== null)&& !(empForm.getMobileNumber().equalsIgnoreCase(""))) || !(empForm.getMobileNumber()== null)&& !(empFormApp.getMobileNumber()== null))
							{ 
                       	
                       		%>
							
						<tr>	
							<td><b>
								Mobile Number</b>
							</td>

							<td>
							${abc.mobileNumber}
								
							</td>
							<td>
							${abc1.mobileNumber}
								
							</td>
					
                         </tr>
                         <%} %>
                         <%	 if((!(empFormApp.getEmailAddress()== null)&& !(empForm.getEmailAddress().equalsIgnoreCase(""))) || !(empForm.getEmailAddress()== null)&& !(empFormApp.getEmailAddress()== null))
							{ 
                       	
                       		%>
                         <tr>
							<td><b>
								Personal eMail</b>
								<font color="red">*</font>
							</td>

							<td>
							${abc.emailAddress}
								
							</td>
								<td>
							${abc1.emailAddress}
								
							</td>
						</tr>
						 <%} %>
						   <%	 if((!(empFormApp.getBloodGroup()== null)&& !(empForm.getBloodGroup().equalsIgnoreCase(""))) || !(empForm.getBloodGroup()== null)&& !(empFormApp.getBloodGroup()== null))
							{ 
                       	
                       		%>
						<tr>
							<td><b>
								Blood Group</b>
							</td>

							<td>
							${abc.bloodGroup}
								
							</td>
							<td>
							${abc1.bloodGroup}
								
							</td>
							
						</tr>
					   <%} %>
					  
						<tr>
							<th colspan="4" style="text-align: center;">
								Passport Details
							</th>
						</tr>
						<%	 if((!(empFormApp.getPassportNumber()== null)&& !(empForm.getPassportNumber().equalsIgnoreCase(""))) || !(empForm.getPassportNumber()== null)&& !(empFormApp.getPassportNumber()== null))
							{ 
                       	
                       		%>
						<tr>
							<td><b>
								Number</b>
							</td>
							<td>
							${abc.passportNumber}
								
							</td>
								<td>
							${abc1.passportNumber}
								
							</td>
							</tr>
							<%} %>
								<%	 if((!(empFormApp.getPlaceofIssueofPassport()== null)&& !(empForm.getPlaceofIssueofPassport().equalsIgnoreCase(""))) || !(empForm.getPlaceofIssueofPassport()== null)&& !(empFormApp.getPlaceofIssueofPassport()== null))
							{ 
                       	
                       		%>
							<tr>
							<td><b>
								Place of Issue</b>
								
							</td>

							<td>
							${abc.placeofIssueofPassport}
								
							</td>
							<td>
							${abc1.placeofIssueofPassport}
								
							</td>
					</tr>
					<%} %>
								<%	 if((!(empFormApp.getDateofissueofPassport()== null)&& !(empForm.getDateofissueofPassport().equalsIgnoreCase(""))) || !(empForm.getDateofissueofPassport()== null)&& !(empFormApp.getDateofissueofPassport()== null))
							{ 
                       	
                       		%>
					<tr>
							<td><b>
								Date of issue</b>
								<img src="images/date_icon.gif" align="absmiddle" />
								
							</td>

							<td>
							${abc.dateofissueofPassport}
								
							</td>
							<td>
							${abc1.dateofissueofPassport}
								
							</td>
							</tr>
								<%} %>
								<%	 if((!(empFormApp.getDateofexpiryofPassport()== null)&& !(empForm.getDateofexpiryofPassport().equalsIgnoreCase(""))) || !(empForm.getDateofexpiryofPassport()== null)&& !(empFormApp.getDateofexpiryofPassport()== null))
							{ 
                       	
                       		%>
						<tr>	
							<td><b>
								Date of expiry</b>
								<img src="images/date_icon.gif" align="absmiddle" />
								
							
							</td>

							<td>
							${abc.dateofexpiryofPassport}
				
								
							</td>
							<td>
							${abc1.dateofexpiryofPassport}
				
								
							</td>
						</tr>
					
						<%} %>
						<tr>
					
							<th colspan="4" style="text-align: center;">
								General Details
							</th>
						</tr>
							<%	 if((!(empFormApp.getPhysicallyChallenged()== null)&& !(empForm.getPhysicallyChallenged().equalsIgnoreCase(""))) || !(empForm.getPhysicallyChallenged()== null)&& !(empFormApp.getPhysicallyChallenged()== null))
							{ 
                       	
                       		%>
							
						<tr>

							<td><b>
								Physically Challenged</b>
								<font color="red">*</font>
							</td>

							<td>
							${abc.physicallyChallenged}
								

							</td>
							<td>
							${abc1.physicallyChallenged}
								

							</td>
							</tr>
							<%} %>	<%	 if((!(empFormApp.getPhysicallyChallengeddetails()== null)&& !(empForm.getPhysicallyChallengeddetails().equalsIgnoreCase(""))) || !(empForm.getPhysicallyChallengeddetails()== null)&& !(empFormApp.getPhysicallyChallengeddetails()== null))
							{ 
                       	
                       		%>
							<tr>
							<td ><b>
								Physically Challenged Details</b>
								
							</td>
                           
							<td >
								
								${abc.physicallyChallengeddetails}
								</td>
								<td>
								${abc1.physicallyChallengeddetails}
									
								
							</td>
						</tr>
						<%
							}
						%>
						<%	 if((!(empFormApp.getPersonalIdentificationMarks()== null)&& !(empForm.getPersonalIdentificationMarks().equalsIgnoreCase(""))) || !(empForm.getPersonalIdentificationMarks()== null)&& !(empFormApp.getPersonalIdentificationMarks()== null))
							{ 
                       	
                       		%>
						
						<tr>
							<td><b>
								Personal Identification Marks</b>
							</td>
							
							<td>
							${abc.personalIdentificationMarks}
								
							</td>
							<td>
							${abc1.personalIdentificationMarks}
								
							</td>
						</tr>
						<%
							}
						%>
						
						<tr>
							<th colspan="4" style="text-align: center;">
								Emergency Contact Details 1
							</th>
						
						</tr>
						<%	 if((!(empFormApp.getEmergencyContactPersonName()== null)&& !(empForm.getEmergencyContactPersonName().equalsIgnoreCase(""))) || !(empForm.getEmergencyContactPersonName()== null)&& !(empFormApp.getEmergencyContactPersonName()== null))
							{ 
                       	
                       		%>
						<tr>
							<td><b>
								Person Name</b><font color="red" size="3">*</font>
							</td>
							<td colspan="1">
							${abc.emergencyContactPersonName}
								
							</td>
								<td colspan="1">
							${abc1.emergencyContactPersonName}
								
							</td>
							</tr>
								<%
							}
						%>
						<%	 if((!(empFormApp.getEmergencyContactAddressLine1()== null)&& !(empForm.getEmergencyContactAddressLine1().equalsIgnoreCase(""))) || !(empForm.getEmergencyContactAddressLine1()== null)&& !(empFormApp.getEmergencyContactAddressLine1()== null))
							{ 
                       	
                       		%>
						<tr>
							<td><b>
								Address Line 1</b><font color="red" size="3">*</font>
							</td>
							<td>
							${abc.emergencyContactAddressLine1}
								</td>	
									<td>
							${abc1.emergencyContactAddressLine1}
								</td>
						</tr>
								<%
							}
						%>
						<%	 if((!(empFormApp.getEmergencyContactAddressLine2()== null)&& !(empForm.getEmergencyContactAddressLine2().equalsIgnoreCase(""))) || !(empForm.getEmergencyContactAddressLine2()== null)&& !(empFormApp.getEmergencyContactAddressLine2()== null))
							{ 
                       	
                       		%>
								
							
						<tr>
                            <td><b>
								Address Line 2</b>
							</td>
							<td>
							${abc.emergencyContactAddressLine2}
							
							</td>
								<td>
							${abc1.emergencyContactAddressLine2}
							
							</td>
						</tr>	
						<%
							}
						%>
						<%	 if((!(empFormApp.getEmergencyTelephoneNumber()== null) && !(empForm.getEmergencyTelephoneNumber().equalsIgnoreCase(""))) || !(empForm.getEmergencyTelephoneNumber()== null)&& !(empFormApp.getEmergencyTelephoneNumber()== null))
							{ 
                       	
                       		%>
							<tr>
						
					
							<td><b>
								Emergency Telephone Number</b>
							</td>
							<td>
							${abc.emergencyTelephoneNumber}
								</td>
								<td>
							${abc1.emergencyTelephoneNumber}
								</td>
								</tr>
									<%
							}
						%> <%	 if((!(empFormApp.getEmergencyMobileNumber()== null)&& !(empForm.getEmergencyMobileNumber().equalsIgnoreCase(""))) || !(empForm.getEmergencyMobileNumber()== null)&& !(empFormApp.getEmergencyMobileNumber()== null))
							{ 
                       	
                       		%>
								<tr>
							 <td><b>
								Emergency Mobile Number</b><font color="red" size="3">*</font>
							</td>
							<td>
							${abc.emergencyMobileNumber}
							
							</td>
							<td>
							${abc1.emergencyMobileNumber}
							
							</td>
							</tr>
							<%
							}
						%>
							<tr>
							<th colspan="4" style="text-align: center;">
								Emergency Contact Details 2
							</th>
						
						</tr>
						<%	 if((!(empFormApp.getEmergencyContactPersonName1()== null)&& !(empForm.getEmergencyContactPersonName1().equalsIgnoreCase(""))) || !(empForm.getEmergencyContactPersonName1()== null)&& !(empFormApp.getEmergencyContactPersonName1()== null))
							{ 
                       	
                       		%>
						
						<tr>
							<td><b>
								Person Name</b>
							</td>
							<td colspan="1">
							${abc.emergencyContactPersonName1}
								
							</td>
								<td colspan="1">
							${abc1.emergencyContactPersonName1}
								
							</td>
							</tr>
							<%
							}
						%>	
						<%	 if((!(empFormApp.getEmergencyContactAddressLine3()== null)&& !(empForm.getEmergencyContactAddressLine3().equalsIgnoreCase(""))) || !(empForm.getEmergencyContactAddressLine3()== null)&& !(empFormApp.getEmergencyContactAddressLine3()== null))
							{ 
                       	
                       		%>
						<tr>
							<td><b>
								Address Line 1</b>
							</td>
							<td>
							${abc.emergencyContactAddressLine3}
								</td>	
									<td>
							${abc1.emergencyContactAddressLine3}
								</td>
						</tr>
								
							<%
							}
						%>
						<%	 if((!(empFormApp.getEmergencyContactAddressLine4()== null)&& !(empForm.getEmergencyContactAddressLine4().equalsIgnoreCase(""))) || !(empForm.getEmergencyContactAddressLine4()== null)&& !(empFormApp.getEmergencyContactAddressLine4()== null))
							{ 
                       	
                       		%>
						<tr>
                            <td><b>
								Address Line 2</b>
							</td>
							<td>
							${abc.emergencyContactAddressLine4}
							
							</td>
								<td>
							${abc1.emergencyContactAddressLine4}
							
							</td>
						</tr>	
						<%
							}
						%>
							<%	 if((!(empFormApp.getEmergencyTelephoneNumber1()== null)&& !(empForm.getEmergencyTelephoneNumber1().equalsIgnoreCase(""))) || !(empForm.getEmergencyTelephoneNumber1()== null)&& !(empFormApp.getEmergencyTelephoneNumber1()== null))
							{ 
                       	
                       		%>
							<tr>
						
					
							<td><b>
								Emergency Telephone Number</b>
							</td>
							<td>
							${abc.emergencyTelephoneNumber1}
								</td>
								<td>
							${abc1.emergencyTelephoneNumber1}
								</td>
								</tr>
								<%
							}
						%>
						<%	 if((!(empFormApp.getEmergencyMobileNumber1()== null)&& !(empForm.getEmergencyMobileNumber1().equalsIgnoreCase(""))) || !(empForm.getEmergencyMobileNumber1()== null)&& !(empFormApp.getEmergencyMobileNumber1()== null))
							{ 
                       	
                       		%>
								<tr>
							 <td><b>
								Emergency Mobile Number</b>
							</td>
							<td>
							${abc.emergencyMobileNumber1}
							
							</td>
							<td>
							${abc1.emergencyMobileNumber1}
							
							</td>
							</tr>
							
                          <%
							}
						%>
						
						<tr>
		<td>
		Comments</td>
		<td colspan="8">
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
					</table>


</logic:iterate></logic:iterate>
			</logic:notEmpty>
		<html:hidden property="selectedFilter"/>
			</html:form>
  </body>
</html>
