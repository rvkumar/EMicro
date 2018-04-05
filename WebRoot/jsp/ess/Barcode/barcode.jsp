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
    
    
    function alertbox()
    {
    
    var mess=document.getElementById("message").value;
    alert(mess);
    
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
  
    <table class="bordered">
    <tr><th colspan="4" >Goods Receipt Note  </th></tr>
    <tr>
	<td>Barcode&nbsp;<font color="red" >*</font></td><td>
	<html:text property="mBLNR" styleId="mBLNR"  onkeypress="return isNumber(event)" />&nbsp;&nbsp;
	<%-- <html:button property="method" value="Go" onclick="search()" styleClass="rounded"/> --%>
	</td>
	</tr>
	</table>
	
	</br>
		
				
				
				<div style="height:300px;overflow:auto">
				<logic:notEmpty name="barcodelist">
			<div align="left" class="bordered">
			<table class="sortable">
			
				<tr>
				<th colspan="11"><center>GRN Details</center></th>
				</tr>
			<tr>
			<th style="text-align:left;"><b>Location </b></th>
			<th style="text-align:left;"><b>Grn No</b></th>
			<th style="text-align:left;"><b>Grn Date</b></th>
			<th style="text-align:left;"><b>Material Type</b></th>
			<th style="text-align:left;"><b>PG Group</b></th>
			<th style="text-align:left;"><b>Vendor Name</b></th>
			<th style="text-align:left;"><b>City</b></th>
										
					</tr>
				<logic:iterate id="mytable1" name="barcodelist">
									<tr >
										<td>
				<bean:write name="mytable1" property="wERKS"/>
				
				</td>
				<td>
				<bean:write name="mytable1" property="mBLNR"/>
				</td>
				<td>
				<bean:write name="mytable1" property="bLDAT"/>
				</td>
				<td>
				<bean:write name="mytable1" property="mTART"/>
				</td>
				<td>
				<bean:write name="mytable1" property="eKGRP"/>
				</td>
				<td>
				<bean:write name="mytable1" property="name1"/>
				</td>
				<td>
				<bean:write name="mytable1" property="oORT01"/>
				</td>
				
									</tr>
				</logic:iterate>
				</table></div>
		</logic:notEmpty>
		</div>
		
		 <logic:notEmpty  property="message" name="barcodeForm">
			<script type="">
	alertbox();
			

            </script>
		</logic:notEmpty>
		
		<br/>
		<logic:notEmpty name="barcodelist">
		<center>
		<html:button property="method" value="Submit" onclick="updateBarcode()" styleClass="rounded"/>
	</center>
	</logic:notEmpty>

	</html:form>
    </body>
</html>