<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ItProcurement.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	--><link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

  </head>
  
  <body>
   <font face="Arial">
<table class="bordered" style="font-size: 12">
<tr>
<th colspan="3">
New Requirement
</th>
<tr><th><center>Sl.No.</th><th><center>Item</th><th><center>Procedure</th></tr>
<tr><td><center>1</td><td>Desktop / Laptop / Printers</td>
<td>
1. Prepare Indent / Purchase Requisition SAP. Fill up all the required information justifying the requirement like employee number, designation, Date of Joining. 

<br>2. Get the approval of your reporting manager.

<br>3. Forward it to Corporate IT.

<br>4. If requirement is from manufacturing plants it would be sent for signature of <br>Mr.SM Mudda

<br>5. If requirement is from R&D, Corporate Office it would be processed by IT.

<br>6. If requirement is for field it would be sent for signature of CMD / Director.

<br>7. IT will validate the requirement.

<br>8. IT Head will sign & do electronic release in SAP.

<br>9. Forward it to purchase for commercial negotiation & PO preparation.

<br>10. PO will be signed by Purchase Head & IT Head. 

<br>11. Forwarded to CMD / Director for approval.

<br>12. Forwarded to Vendor for supply of material.

<br>13. Details can be seen in the system & if required follow up can be done.



</td>
<tr><td><center>2</td><td>IT Consumables : Toners, Cartridges, CD's, </td>
<td>
1. IT engineer of respective plant will raise Indent / Purchase requisition.
<br>2. Toners must be indent once in 2 months with justification attached which contains list of printers with their consumption & signed by respective department Head.
<br>3. IT Consumables must be indent once in 1 month.
<br>4. Forward it to Corporate IT
<br>5. Corporate IT will validate the requirement
<br>6. IT Head will sign & does electronic release in SAP
<br>7. Forward it to purchase for commercial negotiation & PO preparation.
<br>8. PO sign by purchase head & IT Head.
<br>9. Forward it to CMD / Director for Approval.


</td>


</table>



<table class="bordered"><tr><th colspan="3">
Replacement

</th></tr>
<tr><th><center>Sl.No.</th><th><center>Item</th><th><center>Procedure</th></tr>
<tr><td><center><center>1</td><td>Desktop/Laptop/Printers</td>
<td>

1. Prepare Indent / Purchase Requisition SAP. Fill up all the required information <br>justifying the requirement like status of existing asset. 

<br>2. Get the approval of your reporting manager.

<br>3. Forward it to Corporate IT.

<br>4. If requirement is from manufacturing plants it would be sent for signature of Mr.SM Mudda

<br>5. If requirement is from R&D, Corporate Office it would be processed by IT.

<br>6. If requirement is for field it would be sent for signature of CMD / Director.

<br>7. IT will validate the requirement.

<br>8. IT Head will sign & do electronic release in SAP.

<br>9. Forward it to purchase for commercial negotiation & PO preparation.

<br>10. PO will be signed by Purchase Head & IT Head. Forwarded for CMD / Director Signature.

<br>11. Forwarded to Vendor for supply of material.

<br>12. Details can be seen in the system & if required follow up can be done.

</td>
</tr>
<tr>


</table>
</font></br>
<table>
<tr><td><center>

<html:button property="method" onclick="history.back(-1)"  styleClass="rounded" style="width: 120px;" value="Back"/></center>
 
</td></tr>

</table>


<br>
  </body>
</html>
