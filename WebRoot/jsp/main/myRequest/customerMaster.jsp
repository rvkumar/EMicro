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
 <STYLE TYPE="text/css">
 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}

</STYLE>
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


function saveData(){	   
		
	
		if(document.forms[0].requestDate.value=="")
	    {
	      alert("Please Select Request Date");
	      document.forms[0].requestDate.focus();
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
	    
	    
	    if(document.forms[0].customerName.value=="")
	    {
	      alert("Please Enter Customer Name");
	      document.forms[0].customerName.focus();
	      return false;
	    }
	 if(document.forms[0].customerName.value!=""){
	    var customerName=document.forms[0].customerName.value;
         var splChars = "'";
for (var i = 0; i < customerName.length; i++) {
    if (splChars.indexOf(customerName.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  Customer Name !"); 
      document.forms[0].customerName.focus();
      return false;
    }
   }
	    
	    }	    
	  if(document.forms[0].address1.value=="")
	    {
	      alert("Please Enter Address");
	      document.forms[0].address1.focus();
	      return false;
	    }
	     if(document.forms[0].address1.value!="")
	    {
	    var address1=document.forms[0].address1.value;
         var splChars = "'";
for (var i = 0; i < address1.length; i++) {
    if (splChars.indexOf(address1.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  Address 1 !"); 
      document.forms[0].address1.focus();
      return false;
    }
   }
	    
	    }
	     if(document.forms[0].address2.value!="")
	    {
	    var address2=document.forms[0].address2.value;
         var splChars = "'";
for (var i = 0; i < address2.length; i++) {
    if (splChars.indexOf(address2.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  Address 2 !"); 
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
   	  alert ("Please Remove Single Code(') in  Address 3 !"); 
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
   	  alert ("Please Remove Single Code(') in  Address 4 !"); 
      document.forms[0].address4.focus();
      return false;
    }
   }
	    }
	   if(document.forms[0].city.value!="")
	    {
	    var city=document.forms[0].city.value;
         var splChars = "'";
for (var i = 0; i < city.length; i++) {
    if (splChars.indexOf(city.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  city  !"); 
      document.forms[0].city.focus();
      return false;
    }
   }
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
	    var emailId=document.forms[0].emailId.value;
         var splChars = "'";
for (var i = 0; i < emailId.length; i++) {
    if (splChars.indexOf(emailId.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  Email Id  !"); 
      document.forms[0].emailId.focus();
      return false;
    }
   }
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
	    if(document.forms[0].tdsStatus.value=="True")
	    {
		      if(document.forms[0].tdsCode.value=="")
		    {
		      alert("Please Select TdsCode ");
		      document.forms[0].taxType.focus();
		      return false;
		    }
		    
		    
	    }
	    
	    
	    if(document.forms[0].listNumber.value!="")
	    {
	    var listNumber=document.forms[0].listNumber.value;
         var splChars = "'";
for (var i = 0; i < listNumber.length; i++) {
    if (splChars.indexOf(listNumber.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  LST NO  !"); 
      document.forms[0].listNumber.focus();
      return false;
    }
   }
	    }
	    
	    
	       if(document.forms[0].tinNumber.value!="")
	    {
	    var tinNumber=document.forms[0].tinNumber.value;
         var splChars = "'";
for (var i = 0; i < tinNumber.length; i++) {
    if (splChars.indexOf(tinNumber.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  TIN NO  !"); 
      document.forms[0].tinNumber.focus();
      return false;
    }
   }
	    }
	    
	    
	        if(document.forms[0].cstNumber.value!="")
	    {
	    var cstNumber=document.forms[0].cstNumber.value;
         var splChars = "'";
for (var i = 0; i < cstNumber.length; i++) {
    if (splChars.indexOf(cstNumber.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  CST NO  !"); 
      document.forms[0].cstNumber.focus();
      return false;
    }
   }
	    }
	    if(document.forms[0].panNumber.value!="")
	    {
	    var panNumber=document.forms[0].panNumber.value;
         var splChars = "'";
for (var i = 0; i < panNumber.length; i++) {
    if (splChars.indexOf(panNumber.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  PAN NO  !"); 
      document.forms[0].panNumber.focus();
      return false;
    }
   }
	    }
	   if(document.forms[0].isRegdExciseVender.value=="")
	    {
	      alert("Please Select Is Regd Excise Customer");
	      document.forms[0].isRegdExciseVender.focus();
	      return false;
	    }
	    
	     if(document.forms[0].isRegdExciseVender.value=="True")
	    {
		      if(document.forms[0].eccNo.value=="")
		    {
		      alert("Please Enter ECC No  ");
		      document.forms[0].eccNo.focus();
		      return false;
		    }
		    var eccNo=document.forms[0].eccNo.value;
         var splChars = "'";
for (var i = 0; i < eccNo.length; i++) {
    if (splChars.indexOf(eccNo.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  ECC NO  !"); 
      document.forms[0].eccNo.focus();
      return false;
    }
   }
			  if(document.forms[0].exciseRegNo.value=="")
		    {
		      alert("Please Enter Excise Reg Number ");
		      document.forms[0].exciseRegNo.focus();
		      return false;
		    }
		     var exciseRegNo=document.forms[0].exciseRegNo.value;
         var splChars = "'";
for (var i = 0; i < exciseRegNo.length; i++) {
    if (splChars.indexOf(exciseRegNo.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  Excise Reg. No  !"); 
      document.forms[0].exciseRegNo.focus();
      return false;
    }
   }
		    if(document.forms[0].exciseRange.value=="")
		    {
		      alert("Please Enter Excise Range ");
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
		      alert("Please Enter exciseDivision ");
		      document.forms[0].exciseDivision.focus();
		      return false;
		    }
		    if(document.forms[0].exciseDivision.value!="")
		    {
		     var exciseDivision=document.forms[0].exciseDivision.value;
         var splChars = "'";
for (var i = 0; i < exciseDivision.length; i++) {
    if (splChars.indexOf(exciseDivision.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  Excise Division !"); 
      document.forms[0].exciseDivision.focus();
      return false;
    }
   }
	    }
	    }
	
	    if(document.forms[0].dlno1.value!=""){
	     var dlno1=document.forms[0].dlno1.value;
         var splChars = "'";
for (var i = 0; i < dlno1.length; i++) {
    if (splChars.indexOf(dlno1.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in DL No.1 !"); 
      document.forms[0].dlno1.focus();
      return false;
    }
   }
	    
	    }
	    if(document.forms[0].dlno2.value!=""){
	     var dlno2=document.forms[0].dlno2.value;
         var splChars = "'";
for (var i = 0; i < dlno2.length; i++) {
    if (splChars.indexOf(dlno2.charAt(i)) != -1){
   	  alert ("Please Remove Single Code(') in  DL No.2 !"); 
      document.forms[0].dlno2.focus();
      return false;
    }
   }
	    
	    }
	

	    
			var url="customerMaster.do?method=saveEmployeeMaster";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}
function updateData(){
var url="customerMaster.do?method=updateCustomerRecord";
			document.forms[0].action=url;
			document.forms[0].submit();	

}


function onUpload(){	 
if(document.forms[0].fileNames.value=="")
   {
     alert("Please Select File To Upload");
     document.forms[0].fileNames.focus();
     return false;
   }  
		
	var url="customerMaster.do?method=uploadFiles";
	document.forms[0].action=url;
	document.forms[0].submit();		 
}
function getStates(){	   
		
			var url="customerMaster.do?method=getStates";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}

function getTDSState(tdsCode)
{

var status=tdsCode;



var url="customerMaster.do?method=getTDS";
			document.forms[0].action=url;
			document.forms[0].submit();	



}


function getREVState(revState)
{

var status=revState;



var url="customerMaster.do?method=getRegisterVendor";
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
	document.forms[0].action="customerMaster.do?method=deleteFileListModify&cValues="+checkvalues+"&unValues="+uncheckvalues;
document.forms[0].submit();
}
}
}



function closeForm()
{
var url="customerMaster.do?method=displayCustomerList";
			document.forms[0].action=url;
			document.forms[0].submit();	
}

</script>

</head>

<body style="text-transform:uppercase;">
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
     		
			<html:form action="/customerMaster.do" enctype="multipart/form-data">
            
            <logic:iterate id="customerMasterForm" name="custdetails">
				
			

			<div id="materialTable" style="visibility: visible;">
			<table class="bordered">
			<tr>
					<th colspan="8" style="text-align: center;"><big>Customer Code Request Form</big></th>
				</tr>
				<tr>
					<th colspan="8"><big>Basic Details Of Material</big></th>
   				</tr>
				<tr>	  
		<tr>
			<td>Request Number <font color="red">*</font></td>
			<td>
								<bean:write name="customerMasterForm" property="requestNumber"/>

			</td>
			<td>Request Date <font color="red">*</font></td>
			<td>
								<bean:write name="customerMasterForm" property="requestDate"/>
			</td>
		</tr>
		<tr>
			<td rowspan="1">Account Group</td>
			<td rowspan="1">
										<bean:write name="customerMasterForm" property="accGroupId"/>

			</td>
			<td>View Type</td>
			<td>										
			<bean:write name="customerMasterForm" property="sales"/>,
			<bean:write name="customerMasterForm" property="accounts"/>

			</td>
		</tr>
		<tr>
			<td>Customer Type <font color="red">*</font></td>
			<td>		
				<bean:write name="customerMasterForm" property="customerType"/>

			</td>
			<td>Employee Code</td>
			<td>
				<bean:write name="customerMasterForm" property="cutomerCode"/>
			</td>
		</tr>
		<tr>
			<td>Name <font color="red">*</font></td>
			<td colspan="3">
				<bean:write name="customerMasterForm" property="customerName"/>
</td>
		</tr>
		<tr>
			<td>Address1 <font color="red">*</font></td>
			<td>
				<bean:write name="customerMasterForm" property="address1"/>
			</td>
			<td>Address2</td>
			<td>
				<bean:write name="customerMasterForm" property="address2"/>
			</td>
		</tr>
		<tr>
			<td>Address3</td>
			<td>
				<bean:write name="customerMasterForm" property="address3"/>
			</td>
			<td>Address4</td>
			<td>
				<bean:write name="customerMasterForm" property="address4"/>
			</td>
		</tr>
		<tr>
			<td>City</td>
			<td>
				<bean:write name="customerMasterForm" property="city"/>
			</td>
			<td>Pincode</td>
			<td>
				<bean:write name="customerMasterForm" property="pincode"/>
			</td>
		</tr>
		<tr>
			<td>Country <font color="red">*</font></td>
			<td>
							<bean:write name="customerMasterForm" property="countryId"/>

			</td>

			<logic:empty name="diplayStates">
				<td>State <font color="red">*</font></td>
				<td>
								<bean:write name="customerMasterForm" property="state"/>

				</td>
			</logic:empty>
	
			<logic:notEmpty name="diplayStates">
				<td>State <font color="red">*</font></td>
				<td>
							<bean:write name="customerMasterForm" property="state"/>

				</td>
			</logic:notEmpty>
		</tr>
		<tr>

			<td>Landline</td>
			<td>
				<bean:write name="customerMasterForm" property="landlineNo"/>
          </td>
			<td>Mobile</td>
			<td>
				<bean:write name="customerMasterForm" property="mobileNo"/>
			</td>
		</tr>
		<tr>
			<td>Fax</td>
			<td>
				<bean:write name="customerMasterForm" property="faxNo"/>
			</td>
			<td>e-Mail</td>
			<td>
				<bean:write name="customerMasterForm" property="emailId"/>
			</td>
		</tr>
		<tr>
	 		<th colspan="4"><big>Sales</big></th>
   		</tr>
		<tr>
			<td>Customer Group <font color="red">*</font></td>
			<td>				
			<bean:write name="customerMasterForm" property="customerGroup"/>

			</td>
			<td>Price Group <font color="red">*</font></td>
			<td>		
				<bean:write name="customerMasterForm" property="priceGroup"/>

			</td>
		</tr>
		<tr>
			<td>Price List <font color="red">*</font></td>
			<td>
						<bean:write name="customerMasterForm" property="priceList"/>

			</td>
			<td>Tax Type <font color="red">*</font></td>
			<td>
							<bean:write name="customerMasterForm" property="taxType"/>

			</td>
		</tr>
		<tr>
			<td>Currency</td>
			<td>
						<bean:write name="customerMasterForm" property="currencyId"/>

			</td>
			<td>Payment Term</td>
			<td>
							<bean:write name="customerMasterForm" property="paymentTermID"/>

					</td>
		</tr>
		<tr>
			<td>Account Clerk</td>
			<td colspan="3">
			
						<bean:write name="customerMasterForm" property="accountClerkId"/>

			
			</td>
		</tr>
		<tr>
	 		<th colspan="4"><big>Excise / Tax</big></th>
   		</tr>
		<tr>
			<td>Is Reg.Excise TDS</td>
			<td>
							<bean:write name="customerMasterForm" property="isRegdExciseVender"/>

			</td>

			<logic:empty name="setTdsState">
				<td>TDS Code</td>
				<td>
								<bean:write name="customerMasterForm" property="tdsCode"/>

				</td>
			</logic:empty>

			<logic:notEmpty name="setTdsState">
				<td>TDS Code <font color="red">*</font></td>
				<td>
								<bean:write name="customerMasterForm" property="tdsCode"/>

				</td>
			</logic:notEmpty>

		</tr>
		<tr>
			<td>LST No. </td>
			<td>
				<bean:write name="customerMasterForm" property="listNumber"/>
			</td>
			<td>Tin No.</td>
			<td>
				<bean:write name="customerMasterForm" property="tinNumber"/>
			</td>
		</tr>
		<tr>
			<td>CST No.</td>
			<td>
				<bean:write name="customerMasterForm" property="cstNumber"/>
			</td>
			<td>PAN No.</td>
			<td>
				<bean:write name="customerMasterForm" property="panNumber"/>
			</td>
		</tr>
		<tr>
			<td>Service Tax Reg. No. </td>
			<td>
				<bean:write name="customerMasterForm" property="serviceTaxNo"/>
			</td>
			<td>Is Reg.Excise Customer <font color="red">*</font></td>
			<td>
							<bean:write name="customerMasterForm" property="tdsStatus"/>

			</td>
		</tr>

		<logic:notEmpty name="setRegdExciseVenderNotMandatory">
			<tr>
				<td>ECC No</td>
				<td>
				<bean:write name="customerMasterForm" property="eccNo"/>
				</td>
				<td>Excise Reg. No.</td>
				<td>
				<bean:write name="customerMasterForm" property="exciseRegNo"/>
				</td>
			</tr>
			<tr>
				<td>Excise Range</td>
				<td>
				<bean:write name="customerMasterForm" property="exciseRange"/>
				</td>
				<td>Excise Division</td>
				<td>
				<bean:write name="customerMasterForm" property="exciseDivision"/>
				</td>
			</tr>	
		</logic:notEmpty>

		<logic:notEmpty name="setRegdExciseVender">
			<tr>
				<td>ECC No <font color="red">*</font></td>
				<td>
				<bean:write name="customerMasterForm" property="eccNo"/>
				</td>
				<td>Excise Reg. No. <font color="red">*</font></td>
				<td>
				<bean:write name="customerMasterForm" property="exciseRegNo"/>
				</td>
			</tr>
			<tr>
				<td>Excise Range <font color="red">*</font></td>
				<td>
				<bean:write name="customerMasterForm" property="exciseRange"/>
				</td>
				<td>Excise Division <font color="red">*</font></td>
				<td>
				<bean:write name="customerMasterForm" property="exciseDivision"/>
				</td>
			</tr>	
		</logic:notEmpty>

		<tr>
			<td>DL No.1 </td>
			<td>
				<bean:write name="customerMasterForm" property="dlno1"/>
			</td>
			<td>DL No.2</td>
			<td>
				<bean:write name="customerMasterForm" property="dlno2"/>
			</td>
		</tr>

		<logic:notEmpty name="approved">
			<tr>
				<td>Approve Type</td>
				<td align="left">
									<bean:write name="customerMasterForm" property="domestic"/>

				</td>
			</tr>
		</logic:notEmpty>

		 <tr>			
	<th colspan="6">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<img src="images/star.gif" width="8" height="8" /></td>
				<td>
				<bean:write name="customerMasterForm" property="sapCodeExists"/>
				</td>
		
			<td >SAP Code No<img src="images/star.gif" width="8" height="8" /></td>
				<td colspan="4"><bean:write name="customerMasterForm" property="sapCodeNo"/>	</td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<bean:write name="customerMasterForm" property="sapCreationDate"/>
				
			</td>
			
				<td >Code Created By<img src="images/star.gif" width="8" height="8" /></td><td colspan="4">
				<bean:write name="customerMasterForm" property="sapCreatedBy"/>
			
				</td>
			</tr>

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
