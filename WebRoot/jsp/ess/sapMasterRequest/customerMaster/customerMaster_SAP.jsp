<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Customer Master</title>
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>



<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>


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


function saveData(){	   
			var url="customerMaster.do?method=saveSAPCrationData";
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
<style type="text/css">
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}
.style2 {	color: #df1e1c; font: bold 11px "Arial", Verdana, Arial, Helvetica, sans-serif;	font-size: 12px;
}
</style>
<style type="text/css">
#slideshow {position:relative; margin:0 auto;}
#slideshow img {position:absolute; display:none}
#slideshow img.active {display:block}
</style>


<style type="text/css">
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}
</style>

</head>

<body >

   			<%
			
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			if(menuIcon==null){
			menuIcon="";
			}
			
			%>
			
			<% 
  			  UserInfo user=(UserInfo)session.getAttribute("user");
  			%>
  
  
    
			
     			<div align="center">
						<logic:present name="customerMasterForm" property="message">
						<font color="red">
							<bean:write name="customerMasterForm" property="message" />
						</font>
					</logic:present>
					</div>
				
					<html:form action="/customerMaster.do" enctype="multipart/form-data">
		<table align="center" border="0" cellpadding="5" cellspacing="0" id="mytable1">    	
				           <tr>
 <th colspan="2"><center>Customer Master</center> </th>
</tr>

			<tr>
	 <th colspan="2">General Data:</th>
   </tr>
<tr>
<th width="274" class="specalt" scope="row">Request Number<img src="images/star.gif" width="8" height="8" />
</th><td><html:text property="requestNumber" style="width:300px;"  styleClass="text_field" maxlength="50" readonly="true"/>
<html:hidden property="typeDetails"/>
</td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Request Date<img src="images/star.gif" width="8" height="8" /></th><td>
<html:text property="requestDate" styleId="requestDate" onfocus="popupCalender('requestDate')" readonly="true" styleClass="text_field"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Account Group</th><td><html:select property="accGroupId" styleClass="text_field" disabled="true">
<html:option value="">-----Select-----</html:option>
<html:options property="accountGroupList" labelProperty="accountGroupLabelList"/>
</html:select></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">View Type</th>

<td>
	Sales
	<html:checkbox property="sales"  style="width :10px;" />
	Accounts
	<html:checkbox property="accounts"   style="width :10px;"/>
	</td>

</tr>
<tr>
<th width="274" class="specalt" scope="row">Customer Type <img src="images/star.gif" width="8" height="8" /></th>
<td>
	Domestic
	<html:checkbox property="domestic"  style="width :10px;"/> 
	Exports
	<html:checkbox property="exports"   style="width :10px;"/>
	</td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Name<img src="images/star.gif" width="8" height="8" /></th>
<td><html:text property="customerName" style="width:400px;" styleClass="text_field" readonly="true"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Address 1 <img src="images/star.gif" width="8" height="8" /></th>
<td><html:text property="address1" style="width:400px;"   styleClass="text_field"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Address 2</th><td><html:text property="address2" style="width:400px;"   styleClass="text_field" readonly="true"/></td>
</tr>
<tr >
<th width="274" class="specalt" scope="row">Address 3 </th><td><html:text property="address3" style="width:400px;"   styleClass="text_field" readonly="true"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Address 4</th><td><html:text property="address4" style="width:400px;"   styleClass="text_field" readonly="true"/></td>
</tr>
	
<tr>
<th width="274" class="specalt" scope="row">City</th>
<td><html:text property="city" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true" /></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Pincode</th><td><html:text property="pincode" styleClass="text_field" maxlength="10" readonly="true"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Country<img src="images/star.gif" width="8" height="8" /></th>

<td><html:select property="countryId" onchange="getStates()" styleClass="text_field" disabled="true">
<html:option value="">-----Select-----</html:option>
<html:options property="counID" labelProperty="countryName" styleClass="text_field"/>
</html:select>
</tr>
<tr>
<logic:empty name="diplayStates">
<th width="274" class="specalt" scope="row">State</th>
<td><html:select property="state" styleClass="text_field" disabled="true">
<html:option value="">-----Select-----</html:option>
</html:select>
</td>
	</logic:empty>

<logic:notEmpty name="diplayStates">
<th width="274" class="specalt" scope="row">State<img src="images/star.gif" width="8" height="8" /></th>
<td><html:select property="state" styleClass="text_field" disabled="true">
<html:option value="">-----Select-----</html:option>
<html:options property="stateId" labelProperty="states" styleClass="text_field"/>
</html:select></td>
	</logic:notEmpty>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Landline Number</th><td><html:text property="landlineNo" styleClass="text_field" maxlength="16" readonly="true"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Mobile Number</th><td><html:text property="mobileNo" styleClass="text_field" maxlength="16" readonly="true"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Fax Number </th><td><html:text property="faxNo" styleClass="text_field" maxlength="16" readonly="true"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Email</th><td><html:text property="emailId" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
</tr>
<tr>
	 <th colspan="2">Sales:</th>
   </tr>
<tr>
<th width="274" class="specalt" scope="row">Customer Group<img src="images/star.gif" width="8" height="8" /></td>
<td><html:select property="customerGroup" styleClass="text_field" disabled="true">
<html:option value="">-----Select-----</html:option>
<html:option value="1"/>
<html:option value="2"/>
</html:select></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Price Group<img src="images/star.gif" width="8" height="8" /></th>
<td><html:select property="priceGroup" styleClass="text_field" disabled="true">
<html:option value="">-----Select-----</html:option>
<html:option value="Price Group 1"/>
<html:option value="Price Group 2"/>
</html:select></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Price List<img src="images/star.gif" width="8" height="8" /></th>
<td><html:select property="priceList" styleClass="text_field" disabled="true">
<html:option value="">-----Select-----</html:option>
<html:option value="Price List 1"/>
<html:option value="Price List 2"/>
</html:select></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Tax Type<img src="images/star.gif" width="8" height="8" /></th>
<td><html:select property="taxType" styleClass="text_field" disabled="true">
<html:option value="">-----Select-----</html:option>
<html:option value="Tax Type 1"/>
<html:option value="Tax Type 2"/>
</html:select></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Currency</th><td>
<html:select property="currencyId" styleClass="text_field" disabled="true">
<html:option value="">-----Select-----</html:option>
<html:options property="currenIds" labelProperty="currencys" styleClass="text_field"/>
</html:select></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Payment Term </th>
<td><html:text property="paymentTermID" styleClass="text_field" maxlength="4" readonly="true"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Account Clerk</th>
<td><html:text property="accountClerkId" styleClass="text_field" maxlength="20" readonly="true"/></td>
</tr>
<tr>
	 <th colspan="2">Excise&nbsp/&nbspTax:</th>
   </tr>


<tr>
<th width="274" class="specalt" scope="row">Is Registered Excise Customer</th>
<td><html:select property="tdsStatus" styleClass="text_field" onchange="getTDSState(this.value)" disabled="true">
<html:option value="">-----Select-----</html:option>
<html:option value="True">Yes</html:option>
<html:option value="False">No</html:option>
</html:select></td>
</tr>
<logic:empty name="setTdsState">
<tr>
<th width="274" class="specalt" scope="row">TDS CODE</th>
<td><html:select property="tdsCode" styleClass="text_field" disabled="true">
<html:option value="">-----------Select----------</html:option>
<html:options property="tdsIds" labelProperty="tdsCodes" styleClass="text_field"/>
</html:select></td>
</tr>
</logic:empty>

<logic:notEmpty name="setTdsState">
<tr>
<th width="274" class="specalt" scope="row">TDS CODE<img src="images/star.gif" width="8" height="8" /></th>
<td><html:select property="tdsCode" styleClass="text_field" disabled="true">
<html:option value="">-----------Select----------</html:option>
<html:options property="tdsIds" labelProperty="tdsCodes" styleClass="text_field"/>
</html:select></td>
</tr>
</logic:notEmpty>
<tr>
<th width="274" class="specalt" scope="row">LST Number </th>
<td><html:text property="listNumber" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">Tin Number</th>
<td><html:text property="tinNumber" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
</tr>

<tr>
<th width="274" class="specalt" scope="row">CST Number </th>
<td><html:text property="cstNumber" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">PAN Number</th>
<td><html:text property="panNumber" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
</tr>

<tr>
<th width="274" class="specalt" scope="row">Service Tax Registration No </th>
<td><html:text property="serviceTaxNo" style="width:300px;"  styleClass="text_field" maxlength="40" readonly="true"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">IS REGD EXCISE VENDOR<img src="images/star.gif" width="8" height="8" /></th>
<td><html:select property="isRegdExciseVender" styleClass="text_field" onchange="getREVState(this.value)" disabled="true">
<html:option value="">-----Select-----</html:option>
<html:option value="True">Yes</html:option>
<html:option value="False">No</html:option>
</html:select></td>
</tr>
<logic:notEmpty name="setRegdExciseVenderNotMandatory">
	<tr>
	<th width="274" class="specalt" scope="row">ECC No </th>
	<td><html:text property="eccNo" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
	</tr>
	<tr>
	<th width="274" class="specalt" scope="row">Excise Reg Number</th>
	<td><html:text property="exciseRegNo" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
	</tr>
	<tr>
	<th width="274" class="specalt" scope="row">Excise Range </th>
	<td><html:text property="exciseRange" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
	</tr>
	<tr>
	<th width="274" class="specalt" scope="row">Excise Division</th>
	<td><html:text property="exciseDivision" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
	</tr>	
</logic:notEmpty>
<logic:notEmpty name="setRegdExciseVender">
	<tr>
	<th width="274" class="specalt" scope="row">ECC No <img src="images/star.gif" width="8" height="8" /></th>
	<td><html:text property="eccNo" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
	</tr>
	<tr>
	<th width="274" class="specalt" scope="row">Excise Reg Number<img src="images/star.gif" width="8" height="8" /></th>
	<td><html:text property="exciseRegNo" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
	</tr>
	<tr>
	<th width="274" class="specalt" scope="row">Excise Range <img src="images/star.gif" width="8" height="8" /></th>
	<td><html:text property="exciseRange" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
	</tr>
	<tr>
	<th width="274" class="specalt" scope="row">Excise Division<img src="images/star.gif" width="8" height="8" /></th>
	<td><html:text property="exciseDivision" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
	</tr>	
</logic:notEmpty>
<tr>
<th width="274" class="specalt" scope="row">DLNO1 </th>
<td><html:text property="dlno1" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
</tr>
<tr>
<th width="274" class="specalt" scope="row">DLNO2</th>
<td><html:text property="dlno2" style="width:300px;" styleClass="text_field" maxlength="40" readonly="true"/></td>
</tr>
<logic:notEmpty name="approved">
							
		<tr> <th width="274" class="specalt" scope="row">Approve Type
			<td align="left">
			
			<html:select name="customerMasterForm" property="approveType" styleClass="text_field" disabled="true">
				<html:option value="">--Select--</html:option>
					<html:option value="Pending">Pending</html:option>
					<html:option value="Approved">Approved</html:option>
					<html:option value="Cancel">Cancel</html:option>
			</html:select>
			
			<br /></td></tr>
</logic:notEmpty>

<tr>			
	<th colspan="2">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<th width="274" class="specalt" scope="row">SAP Code Exists<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:select property="sapCodeExists" styleClass="text_field" >
					<html:option value="">-----Select-----</html:option>
					<html:option value="True">Yes</html:option>
					<html:option value="False">No</html:option>
				</html:select></td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">SAP Code No<img src="images/star.gif" width="8" height="8" /></th>
				<td>	<html:text property="sapCodeNo" styleClass="text_field" style="width:200px;"></html:text></td>
			
			</tr>
			
			<tr>	
			<th width="274" class="specalt" scope="row">SAP Creation Date<img src="images/star.gif" width="8" height="8" />
	</th>
			<td align="left">
				<html:text property="sapCreationDate" styleId="sapCreationDate" onfocus="popupCalender('sapCreationDate')" readonly="true" styleClass="text_field"/></td>
			</td>
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">SAP Created By<img src="images/star.gif" width="8" height="8" /></th><td><html:text property="sapCreatedBy" styleClass="text_field"  maxlength="12"></html:text>
				</td>
			</tr>

 <logic:notEmpty name="listName">
		<tr>
			 <th colspan="2">
				Uploaded Documents
			</th>
			
		</tr>
		
		<logic:iterate name="listName" id="listid">
			
			<bean:define id="file" name="listid"
				property="fileList" />
				
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
			<th width="274" class="specalt" scope="row"><a href="${listid.filepath }"><%=u%></a></th>
			
			<td ><input type="checkbox" name="checkedfiles"
						value="<%=u%>" /></td>

			</tr>
			<%
			}
			%>		
			</logic:iterate>
		
		</logic:notEmpty>
	
		<tr>
		<td colspan="4" align="center">
		<html:button property="method"  value="Save" onclick="saveData()" ></html:button>
		<html:reset value="Reset"></html:reset>
		<html:button property="method" value="Close" onclick="closeForm()"></html:button>
		</td>
		</tr>
	
		
		

</table>
</html:form>

</body>
</html>
