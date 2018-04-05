<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Vendor Master</title>
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


		
	    
	var url="vendorMasterRequest.do?method=saveApproveData";
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
		
		var URL="vendorMasterRequest.do?method=displayState";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}
function onClose(){
		
		var URL="vendorMasterRequest.do?method=displayVendorMasterList";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}


//-->
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

<body onload="getIsEligibleTds(),getRegisteredExciseVendor()">

   			<%
			
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			if(menuIcon==null){
			menuIcon="";
			}
			
			%>
			
			<% 
  			  UserInfo user=(UserInfo)session.getAttribute("user");
  			%>
  
  
    
					<logic:present name="vendorMasterRequestForm" property="message">
						<font color="red">
							<center><bean:write name="vendorMasterRequestForm" property="message"/></center>
						</font>
					</logic:present>
				
					<html:form action="vendorMasterRequest.do" enctype="multipart/form-data">
					
					<html:hidden property="linkName"/>
					
					
					<table align="center" border="0" cellpadding="4" cellspacing="0" id="mytable1" >
				        	
				           <tr>
       							 <th colspan="2"><CENTER>Vendor Master</CENTER></th>
      					   </tr>
				     		<tr>
	  <th colspan="2">General Data:</th>
   </tr>
				     	  			
							<tr>
							  
							  <th width="274" class="specalt" scope="row">Request No<img src="images/star.gif" width="8" height="8" /></th>
							  	
								<td align="left">
									<label><html:text property="requestNo" styleClass="text_field" readonly="true" maxlength="50"/>
									<html:hidden property="typeDetails"/>
									</label>
								</td>
									</tr>
									<tr>
									 <th width="274" class="specalt" scope="row">Date<img src="images/star.gif" width="8" height="8" /></th>
									<br /></th>
									<td align="left">
										<html:text property="requestDate" styleId="requestDate" readonly="true"/>
									<br /></td>
									</tr>
								
							<tr>
							 <th width="274" class="specalt" scope="row">Location<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left">
								
								<html:select name="vendorMasterRequestForm" property="locationId" styleClass="text_field" disabled="true">
									<html:option value="">--Select--</html:option>
									<html:options name="vendorMasterRequestForm" property="locationIdList" 
									labelProperty="locationLabelList"/>
									</html:select>
								<br /></td>
							</tr>	
							
							
							<tr>
							
								 <th width="274" class="specalt" scope="row">Account Group<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left">
								<html:select name="vendorMasterRequestForm" property="accountGroupId" styleClass="text_field" disabled="true">
									<html:option value="">--Select--</html:option>
									<html:options name="vendorMasterRequestForm" property="accountGroupList" 
									labelProperty="accountGroupLabelList"/>
								</html:select>
								<br /></td>
							</tr>
							
							<tr>
								 <th width="274" class="specalt" scope="row">View Type<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left">
								<span class="text">Purchase View</span>
								<html:checkbox property="purchaseView"   style="width :25px;"/>
								
								<span class="text">Account View</span>
								<html:checkbox property="accountView"   style="width :25px;"/>
								</td></tr>
								<tr>
							
								 <th width="274" class="specalt" scope="row">Title<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left">
								<html:select name="vendorMasterRequestForm" property="title" styleClass="text_field" disabled="true">
									<html:option value="MR">----Select---</html:option>
									<html:option value="MR">MR</html:option>
									<html:option value="MRS">MR'S</html:option>
									<html:option value="DR">DR</html:option>
									<html:option value="COMPANY">COMPANY</html:option>
									
								</html:select>
								<br /></td>
							</tr>
								<tr> <th width="274" class="specalt" scope="row">Name<img src="images/star.gif" width="8" height="8" /></th>
								<td ><html:text property="name" styleClass="text_field" style="width:400px;" maxlength="40" readonly="true"></html:text>
								<br /></td>
								</tr>
								
								
								<tr> <th width="274" class="specalt" scope="row">Address1<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left"><html:text property="address1" styleClass="text_field" style="width:400px;" maxlength="40" readonly="true"/>
								<br /></td></tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">Address2</th>
								<td align="left"><html:text property="address2" styleClass="text_field" style="width:400px;" maxlength="40" readonly="true"/>
								<br /></td></tr>
								
								
								<tr> <th width="274" class="specalt" scope="row">Address3</th>
								<td align="left"><html:text property="address3" styleClass="text_field" style="width:400px;" maxlength="40" readonly="true"/>
								<br /></td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Address4<br /></th>
								<td align="left"><html:text property="address4" styleClass="text_field" style="width:400px;" maxlength="40" readonly="true"/>
								<br /></td></tr>
								
								
								<tr> <th width="274" class="specalt" scope="row">City<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left"><html:text property="city" styleClass="text_field" style="width:400px;" maxlength="40" readonly="true"></html:text>
								<br /></td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Pin Code<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left"><html:text property="pinCode" styleClass="text_field" maxlength="10" readonly="true"></html:text>
								<br /></td></tr>
								
								
								<tr> <th width="274" class="specalt" scope="row">Country<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left">
								<html:select name="vendorMasterRequestForm" property="country"  disabled="true"
								styleClass="text_field"  onchange="displayStates()">
									<html:option value="">--Select--</html:option>
									<html:options name="vendorMasterRequestForm" property="countryList" 
									labelProperty="countryLabelList"/>
									</html:select>
								
								<br /></td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">
								 State<logic:notEmpty name="States"><span class="red_man"><img src="images/star.gif" width="8" height="8" /></logic:notEmpty></th>
								
								
				
								<td align="left">
								
									<html:select name="vendorMasterRequestForm" property="state" disabled="true"
									styleClass="text_field"  >
									<html:option value="">--Select--</html:option>
									<html:options name="vendorMasterRequestForm" property="stateList" 
									labelProperty="stateLabelList"/>
									</html:select>
								
								<br /></td></tr>
								
								
								<tr> <th width="274" class="specalt" scope="row">Land line No</th>
								<td align="left"><html:text property="landLineNo" styleClass="text_field" maxlength="16" readonly="true"></html:text>
								<br /></td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Mobile No</th>
								<td align="left"><html:text property="mobileNo" styleClass="text_field" maxlength="16" readonly="true"></html:text>
								<br /></td></tr>
								
								
								<tr> <th width="274" class="specalt" scope="row">Fax No</th>
								<td align="left"><html:text property="faxNo" styleClass="text_field"  maxlength="16" readonly="true"></html:text>
								<br /></td></tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">Email ID</th>
								<td align="left"><html:text property="emailId" styleClass="text_field" style="width:400px;"  maxlength="40" readonly="true"></html:text>
								<br /></td></tr>
		
   		     		<tr>
	  <th colspan="2">Tax Details:</th>
   </tr>
								
								<tr> <th width="274" class="specalt" scope="row">Currency<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left">
								
								<html:select name="vendorMasterRequestForm" property="currencyId" disabled="true"
								 styleClass="text_field"  style="width:250px;">
									<html:option value="">--Select--</html:option>
									<html:options name="vendorMasterRequestForm" property="currencyList"
									labelProperty="currencyLabelList"/>
									</html:select>
								
								<br />
								</td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Reconciliation Account<img src="images/star.gif" width="8" height="8" /></th>
								 								<td align="left">
								
								<html:select name="vendorMasterRequestForm" property="reConcillationActId" styleClass="text_field" style="width:250px;" disabled="true">
									<html:option value="">--Select--</html:option>
									<html:options name="vendorMasterRequestForm" property="reConcillationList" 
									labelProperty="reConcillationLabelList"/>
								</html:select>
								<br /></td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Is Eligible For Tds</th>
								<td>
								<html:select name="vendorMasterRequestForm" property="elgTds" styleClass="text_field" onchange="getIsEligibleTds()" disabled="true">
									<html:option value="">--Select--</html:option>
									<html:option value="True">Yes</html:option>
									<html:option value="False">No</html:option>
								</html:select>
								
								<br /></td>
								</tr>
								
								
								<tr> <th width="274" class="specalt" scope="row">
								Tds Code&nbsp;<div id="mandatoryEligibleTds" style="visibility: hidden"><img src="images/star.gif" width="8" height="8" readonly="true" /></div></th>
								
								
								
								
								<td align="left">
								
								<html:select name="vendorMasterRequestForm" property="tdsCode" styleClass="text_field" style="width:300px;" disabled="true">
									<html:option value="">--Select--</html:option>
										<html:options name="vendorMasterRequestForm" property="tdsIdList" 
									labelProperty="tdsLabelList"/>
									</html:select>
								
								<br /></td>
								</tr>
								
								<tr> <th width="274" class="specalt" scope="row">LST No</th>
								<td align="left"><html:text property="lstNo" styleClass="text_field" style="width:300px;"  maxlength="40" readonly="true"></html:text>
								<br /></td></tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">Tin No</th>
							    <br /></td>
								<td align="left"><html:text property="tinNo" styleClass="text_field" style="width:300px;"  maxlength="40" readonly="true"></html:text>
								<br /></td></tr>
								
								<tr> <th width="274" class="specalt" scope="row">CST No</th>
								<td align="left"><html:text property="cstNo" styleClass="text_field"  style="width:300px;"  maxlength="40" readonly="true"></html:text>
								<br /></td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Payment Term<img src="images/star.gif" width="8" height="8" /></th>
								
								<td align="left">
								
								<html:select name="vendorMasterRequestForm" property="paymentTermId" styleClass="text_field" disabled="true">
									<html:option value="">--Select--</html:option>
									<html:options name="vendorMasterRequestForm" property="paymentTermList" 
									labelProperty="paymentTermLabelList"/>
								</html:select>
								
								<br /></td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Account Clerk</th>
								<td align="left">
								<html:select name="vendorMasterRequestForm" property="accountClerkId" styleClass="text_field" disabled="true">
									<html:option value="">--Select--</html:option>
									<html:options name="vendorMasterRequestForm" property="accountClerkList" 
									labelProperty="accountClerkLabelList"/>
								</html:select>
								
								<br /></td></tr>
								
								<tr> <th width="274" class="specalt" scope="row">Is Approved Vendor</th>
								<td align="left">
								
								<html:select name="vendorMasterRequestForm" property="isApprovedVendor" styleClass="text_field" disabled="true">
									<html:option value="">--Select--</html:option>
										<html:option value="True">Yes</html:option>
										<html:option value="False">No</html:option>
								</html:select>
								
								<br /></td></tr>
								
								
								<tr>
								
								 <th width="274" class="specalt" scope="row">PAN No<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left"><html:text property="panNo" styleClass="text_field"  style="width:300px;"  maxlength="40" readonly="true"></html:text>
								<br /></td></tr>
								
								<tr> <th width="274" class="specalt" scope="row">Service Tax Registration No<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left"><html:text property="serviceTaxRegNo" styleClass="text_field" style="width:300px;" maxlength="40" readonly="true"></html:text>
								<br /></td></tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">Is Registered Excise Vendor</th>
								<td align="left">
								
								<html:select name="vendorMasterRequestForm" property="regExciseVendor" styleClass="text_field" onchange="getRegisteredExciseVendor()" disabled="true">
									<html:option value="">--Select--</html:option>
									<html:option value="True">Yes</html:option>
									<html:option value="False">No</html:option>
								</html:select>
								
								<br /></td></tr>
							
								
							<tr> <th width="274" class="specalt" scope="row">
							ECC No.<div id="mandatoryRegisteredExciseVendor1" style="visibility: hidden"><img src="images/star.gif" width="8" height="8" /></div></th>
								<td align="left"><html:text property="eccNo" styleClass="text_field" style="width:300px;" maxlength="40" readonly="true"></html:text>
								<br /></td></tr>
								
							<tr>
								 <th width="274" class="specalt" scope="row">
								 Excise Reg No.	<div id="mandatoryRegisteredExciseVendor2" style="visibility: hidden"><img src="images/star.gif" width="8" height="8" /></div><br /></th>
								<td align="left"><html:text property="exciseRegNo" styleClass="text_field" style="width:300px;" maxlength="40" readonly="true"></html:text>
								<br /></td></tr>
								
								<tr> <th width="274" class="specalt" scope="row">
								Excise Range<div id="mandatoryRegisteredExciseVendor3" style="visibility: hidden"><img src="images/star.gif" width="8" height="8" /></div><br /></th>
								<td align="left"><html:text property="exciseRange" styleClass="text_field" style="width:300px;" maxlength="40" readonly="true"></html:text>
								<br /></td>
								</tr>
									<tr>
							 <th width="274" class="specalt" scope="row">Excise Division
							 	<div id="mandatoryRegisteredExciseVendor4" style="visibility: hidden"><img src="images/star.gif" width="8" height="8" /></div></th>
								<td align="left"><html:text property="exciseDivision" styleClass="text_field" style="width:300px;" maxlength="40" readonly="true"></html:text>
								<br/>
								</td>
								</tr>
								<tr> <th width="274" class="specalt" scope="row">Commissionerate
								<div id="mandatoryRegisteredExciseVendor5" style="visibility: hidden"><img src="images/star.gif" width="8" height="8" /></div></th>
								<td align="left"><html:text property="commissionerate" styleClass="text_field" style="width:300px;" maxlength="40" readonly="true"></html:text>
								<br /></td></tr>
								
								<tr> <th width="274" class="specalt" scope="row">Type OF Vendor
								<div id="mandatoryRegisteredExciseVendor6" style="visibility: hidden"><img src="images/star.gif" width="8" height="8" /></div></th>
								<td align="left">
								
								<html:select name="vendorMasterRequestForm" property="typeOfVendor" styleClass="text_field" style="width:300px;" disabled="true">
									<html:option value="">--Select--</html:option>
										<html:option value="I">IMPORT</html:option>
										<html:option value="M">MANUFACTURER</html:option>
										<html:option value="D">DELAR</html:option>
								        <html:option value="FD">FIRST STAGE DELAR OF INDIGENOUS</html:option>
										<html:option value="SD">SECOND STAGE DELAR OF INDIGENOUS</html:option>
										<html:option value="MD">MANUFACTURER DEPOT</html:option>
								</html:select>
								
								<br /></td></tr>
								
								
							
						
						
							
							
								
								<logic:notEmpty name="documentDetails">
						
						<tr>
							<th colspan="2">
							<div align="center">
								<b><font color="white">Uploaded&nbsp;Documents&nbsp;</font></b></div>
						</th>
						</tr>
						
						<tr class="tablerowdark">
					<th colspan="1">
							<font color="white">Sel.</font>
						</th>
						<th colspan="1"><font color="white">Document</font></th>
						
						</tr>
						<logic:iterate id="abc" name="documentDetails">
							<TR>
								<td align="left">
									<html:checkbox property="documentCheck" name="abc"
										value="${abc.fileName}" styleId="${abc.fileName}" onclick="addInput(this.value)" style="width :10px;"/>
								<br /></TD>
								<td colspan="2">
									<a href="${abc.filepath}" ><bean:write name="abc" property="fileName"/>
									</a>
								<br /></td>
							</tr>
	
						</logic:iterate>
						
					
	
				</logic:notEmpty>
								
								<tr>			
	<th colspan="2">Approver Details:</th>
	   </tr>
	<tr> 
		<th width="274" class="specalt" scope="row">Approve Type
			<td align="left">
			
			<html:select name="vendorMasterRequestForm" property="approveType" styleClass="text_field">
				<html:option value="">--Select--</html:option>
					<html:option value="Pending">Pending</html:option>
					<html:option value="Approved">Approved</html:option>
					<html:option value="Cancel">Cancel</html:option>
			</html:select>
			
			<br /></td></tr>
								
								<tr>
									<td  colspan="4" align="center"><html:button property="method"  value="Submit" onclick="onSubmit()" />
								<html:reset value="Reset"/>
									<html:button property="method"  value="Close" onclick="onClose()" />
									</td>
								</tr>

						</table>
				
							</html:form>

</body>
</html>
