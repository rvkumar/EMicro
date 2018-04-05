<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>Employee Attendance Details</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="description" content="Expand, contract, animate forms with jQuery wihtout leaving the page" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <meta name="keywords" content="expand, form, css3, jquery, animate, width, height, adapt, unobtrusive javascript"/>
		<link rel="shortcut icon" href="../favicon.ico" type="image/x-icon"/>
		
        <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
        <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
		
		<script type="text/javascript" src="63sorttable.js"></script>
		
<script type="text/javascript">
    function search(){
    
    if(document.getElementById("mBLNR").value=="")
    {
     alert("Please Scan Barcode")
     return false;
    
    }
    	var url1="barcode.do?method=barcodeInsert";
			document.forms[0].action=url1;
 			document.forms[0].submit();
				
	  
    }
    
    function updateBarcode(){
    
    
    	var url1="barcode.do?method=barcodeUpdate";
			document.forms[0].action=url1;
 			document.forms[0].submit();
				
	  
    }
    
    function submitBarcode()
    {
    var url1="barcode.do?method=barcodeUpdate";
			document.forms[0].action=url1;
 			document.forms[0].submit();
    
    }
    
    
    function alertbox()
    {
    
    var mess=document.getElementById("message").value;
    alert(mess);
    return false;
    }
    
    function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
    }
    
    
    
     function display()
     {
     
     document.getElementById("mBLNR").focus()
     }
    
	
</script>
	
    
    
</head>

<body onload="display()">
    <html:form action="/barcode.do" onsubmit="search(); return false">
    
    <html:hidden property="message"  styleId="message"/>
   <logic:notEmpty  property="message" name="barcodeForm">
			<script type="">
	alertbox();
			

            </script>
		</logic:notEmpty>
    <table class="bordered">
    <tr><th colspan="4" >Goods Receipt Note  </th></tr>
    <tr>
	<td>Barcode&nbsp;<font color="red" >*</font></td><td>
	<html:text property="mBLNR" styleId="mBLNR"  onkeypress="return isNumber(event)" />&nbsp;&nbsp;
	<html:button property="method" value="Go" onclick="search()" styleClass="rounded"/>
	
	<html:button property="method" value="Submit" onclick="submitBarcode()" styleClass="rounded"/>
	</td>
	</tr>
	</table>
	
	</br>
	
	<logic:notEmpty name="bar">
	 <table class="bordered">
    <tr>
	<td>Material Document No&nbsp;<font color="red" >*</font></td><td>
	<html:text property="mBLNR" readonly="true"/>&nbsp;&nbsp;
	</td>
	<td>Material Document Year&nbsp;<font color="red" >*</font></td><td>
	<html:text property="mJAHR" readonly="true"/>&nbsp;&nbsp;
	</td>
	</tr>
	<tr>
	<td>Deletion indicator in Material document&nbsp;<font color="red" >*</font></td><td>
	<html:text property="lOEKZ" readonly="true"/>&nbsp;&nbsp;
	</td>
	<td>Document Created Date&nbsp;<font color="red" >*</font></td><td>
	<html:text property="bLDAT" readonly="true"/>&nbsp;&nbsp;
	</td>
	</tr>
	<tr>
	
	<td>Name&nbsp;<font color="red" >*</font></td><td>
	<html:textarea property="name1" readonly="true" />&nbsp;&nbsp;
	</td>
	
	<td>city&nbsp;<font color="red" >*</font></td><td>
	<html:text property="oORT01" readonly="true" />&nbsp;&nbsp;
	</td>
	</tr>
	<tr>
	<td>Posting Date in the Document&nbsp;<font color="red" >*</font></td><td>
	<html:text property="bUDAT" readonly="true"/>&nbsp;&nbsp;
	</td>
	<td>Plant&nbsp;<font color="red" >*</font></td><td>
	<html:text property="wERKS" readonly="true"/>&nbsp;&nbsp;
	</td>
	</tr>
	<tr>
	<td>Material Type&nbsp;<font color="red" >*</font></td><td>
	<html:textarea property="mTART" readonly="true"/>&nbsp;&nbsp;
	</td>
	<td>Purchasing Group&nbsp;<font color="red" >*</font></td><td>
	<html:text property="eKGRP" readonly="true"/>&nbsp;&nbsp;
	</td>
	</tr>
	<tr>
	<td>Reference Document Number&nbsp;<font color="red" >*</font></td><td>
	<html:text property="xBLNR" readonly="true"/>&nbsp;&nbsp;
	</td>
	<td>Purchase Order Number&nbsp;<font color="red" >*</font></td><td>
	<html:text property="eBELN" readonly="true"/>&nbsp;&nbsp;
	</td>
	</tr>
	<tr>
	<td>Account Number of Vendor or Creditor&nbsp;<font color="red" >*</font></td><td>
	<html:text property="lIFNR" readonly="true"/>&nbsp;&nbsp;
	</td>
	<td>Reversal movement type&nbsp;<font color="red" >*</font></td><td>
	<html:text property="xSTBW" readonly="true"/>&nbsp;&nbsp;
	</td>
	</tr>
	<tr>
	<td>Movement Type (Inventory Management)&nbsp;<font color="red" >*</font></td><td>
	<html:text property="bWART" readonly="true"/>&nbsp;&nbsp;
	</td>
	<td>Purchasing Document Type&nbsp;<font color="red" >*</font></td><td>
	<html:text property="bSART" readonly="true"/>&nbsp;&nbsp;
	</td>
	</tr>
	
	<tr>
	<td>Amount in Local Currency&nbsp;<font color="red" >*</font></td><td>
	<html:text property="dDMBTR" readonly="true"/>&nbsp;&nbsp;
	</td>
	<td>Document Header Text&nbsp;<font color="red" >*</font></td><td>
	<html:text property="bBKTXT" readonly="true"/>&nbsp;&nbsp;
	</td>
	</tr><tr>
	<td>Day On Which Accounting Document Was Entered&nbsp;<font color="red" >*</font></td><td>
	<html:text property="sENT_DATE" readonly="true"/>&nbsp;&nbsp;
	</td>
	<td>Name of Person who Created the Object&nbsp;<font color="red" >*</font></td><td>
	<html:text property="sSENT_BY" readonly="true"/>&nbsp;&nbsp;
	</td>
	</tr><tr>
	<td>Docket No&nbsp;<font color="red" >*</font></td><td>
	<html:text property="dCKET_NO" readonly="true"/>&nbsp;&nbsp;
	</td>
	<td>Courier Name&nbsp;<font color="red" >*</font></td><td>
	<html:text property="cNAME" readonly="true"/>&nbsp;&nbsp;
	</td>
	</tr>
	
	<tr>
	<td>Postal Received Date&nbsp;<font color="red" >*</font></td><td>
	<html:text property="pD_DT" readonly="true"/>&nbsp;&nbsp;
	</td>
	
	<td colspan="2">
	</td>
	</tr>
	<tr>
	<td colspan="4">
	<center>
	<html:button property="method" value="Submit" onclick="updateBarcode()" styleClass="rounded"/>&nbsp;&nbsp;
	</center>
	</td>
	</tr>
	</table>
	</logic:notEmpty>

	</html:form>
    </body>
</html>