<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Report</title>

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
function ClearPlace (input) {

            if (input.value == input.defaultValue) {
            document.getElementById('reqNo').style.cssText = 'color: black';
                input.value = "";
            }
        }
        function SetPlace(input) {
            if (input.value == "") {
            
            document.getElementById('reqNo').style.cssText = 'color: grey';
                input.value = input.defaultValue;
            }
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


function deleteCustomerRecord()
{

var agree = confirm('Are You Sure To Delete Customer Record ');
if(agree)
{

	document.forms[0].action="serviceMasterRequest.do?method=deleteRecord";
document.forms[0].submit();
}
}



function newVendorForm(){
		
		var URL="serviceMasterRequest.do?method=displayServiceMaster";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}

function onSearch(){
		
		var URL="serviceMasterRequest.do?method=searchRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}
function nextRecord(){
     var URL="serviceMasterRequest.do?method=nextRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}
function previousMaterialRecord(){
  var URL="serviceMasterRequest.do?method=prevRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}
function lastMaterialRecord(){
  var URL="serviceMasterRequest.do?method=lastRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}
function firstMaterialRecord(){
 var URL="serviceMasterRequest.do?method=firstRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();


}
function showreport(){
     var URL="reportRequest.do?method=showSearchBar";
		document.forms[0].action=URL;
		document.forms[0].setAttribute("target", "");
 		document.forms[0].submit();

}
function genrateReport()
{

    var URL="reportRequest.do?method=showSearchBar";
	document.forms[0].setAttribute("target", "_blank");
	document.forms[0].action="reportRequest.do?method=genrateReport&flag=no";
	document.forms[0].submit();
		


}
function serviceReport(){

  
	document.forms[0].setAttribute("target", "_blank");
	document.forms[0].action="reportRequest.do?method=genrateServiceReport";
	document.forms[0].submit();


}
function customerReport(){
    document.forms[0].setAttribute("target", "_blank");
	document.forms[0].action="reportRequest.do?method=genrateCustomerReport";
	document.forms[0].submit();
	//window.showModalDialog("reportRequest.do?method=genrateCustomerReport","resizable: yes");

}
function vendorReport(){

    document.forms[0].setAttribute("target", "_blank");
	document.forms[0].action="reportRequest.do?method=genrateVendorReport";
	document.forms[0].submit();


}



//-->
</script>

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

<body onload="MM_preloadImages('images/home_hover.jpg','images/news_hover.jpg','images/ess_hover.jpg','images/hr_hover.jpg',
'images/it_hover.jpg','images/timeout_hover.jpg','images/admin_hover.jpg'),subMenuClicked('<bean:write name='vendorMasterRequestForm' property='linkName'/>')">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

  <tr>
   			<%
			
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			if(menuIcon==null){
			menuIcon="";
			}
			
			%>
			
			<% 
  			  UserInfo user=(UserInfo)session.getAttribute("user");
  			%>
  
  
    <td align="center" valign="top"   height:180px; width:100%"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
     
  
    
    
      <tr>
        <td colspan="3" align="center" valign="top" style="padding-top:3px">
        <div class="middelpart2">
        <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td width="24%" align="left" valign="top">
    <div class="left-blocks">
      <!--------CONTENT LEFT -------------------->
     
    
     
      <!--------CALENDER ENDS -------------------->
    </div></td>
    <td colspan="3" align="left" valign="top">
    <div class="middel-blocks">
    <div align="center">
					<logic:present name="reportRequestForm" property="message">
						<font color="red">
							<bean:write name="reportRequestForm" property="message"/>
						</font>
					</logic:present>
					
					<br />
					<html:form action="reportRequest.do" enctype="multipart/form-data">
					 
					<table width=100% >
		<tr>
		
		
		
		<tr>
		<th>
		
		
	    Select Master <html:select  name="reportRequestForm" property="masterType" onchange="showreport()">
	    <html:option value="">--Select--</html:option>
	    <html:option value="material">Material Master</html:option>
	    <html:option value="service">Service Master</html:option>
	    <html:option value="customer">Customer Master</html:option>
	     <html:option value="vendor">Vendor Master</html:option>
	    </html:select>
		
		</th>
		</tr>
		</table>
		 
		 <table align="center">
	 	<logic:notEmpty name="displayRecordNo">
	 <tr>
	  	<td>
	  	<img src="images/First10.jpg" onclick="firstMaterialRecord()"/>
	
	</td>
	
	<logic:notEmpty name="disablePreviousButton">
	<td>
	
	<img src="images/disableLeft.jpg" />
	</td>
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	<td>
	
	<img src="images/previous1.jpg" onclick="previousMaterialRecord()"/>
	</td>
	</logic:notEmpty>
	
	<td>
	<td>
	<bean:write property="startRecord"  name="serviceMasterRequestForm"/>-
	</td>
	<td>
	<bean:write property="endRecord"  name="serviceMasterRequestForm"/>
	</td>
	<logic:notEmpty name="nextButton">
	<td>
	<img src="images/Next1.jpg" onclick="nextRecord()"/>
	</td>
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	<td>
	
	<img src="images/disableRight.jpg" />
	</td>
	
	</logic:notEmpty>
		<td>
		<img src="images/Last10.jpg" onclick="lastMaterialRecord()"/>
	</td>
	</td>
	
	<html:hidden property="total"/>
	<html:hidden property="next"/>
    <html:hidden property="prev"/>
	</logic:notEmpty>
	 </tr>
	 </table>
	 <table id="mytable1">
	 <logic:notEmpty name="material">
	 <th colspan="4"><center>Material Master Report</center></th>
	 <tr>
	 
	 <td>
	 Material Type<html:select name="reportRequestForm" property="m_materialType">
	 <html:option value="">--Select--</html:option>
	 <html:option value="ROH">ROH</html:option>
	<html:option value="VERP">VERP</html:option>
	<html:option value="Finished Products">FERT</html:option>
	<html:option value="SEMI FINISHED">HALB</html:option>
	<html:option value="PROMOTIONAL">PROMOTIONAL</html:option>
	<html:option value="PRINTED">PRINTED </html:option>
	<html:option value="COMPLIMENTS">COMPLIMENTS</html:option>
	<html:option value="ZPPC">ZPPC</html:option>
	<html:option value="ZPSR">ZPSR-GENERAL MATERIAL</html:option>
	<html:option value="ZLAB">ZLAB-GENERAL MATERIAL</html:option>
	<html:option value="ZCIV">ZCIV-GENERAL MATERIAL</html:option>
	<html:option value="ZCON">ZCON-GENERAL MATERIAL</html:option>
	</html:select></td>
	<td>
	Request Date<html:text name="reportRequestForm" property="m_rquestDate" onclick="popupCalender(m_rquestDate)"></html:text>
	</td>
	<td>
	Approve Status <html:select name="reportRequestForm" property="m_status" styleClass="text_field">
	<html:option value="">Select</html:option>
	<html:option value="pending">Pending</html:option>
	<html:option value="approved">Approved</html:option>
	<html:option value="rejected">Rejected</html:option>
	</html:select>
	</td>
	<td>
	Requested By<html:text name="reportRequestForm" property="m_requestedBy"></html:text></td>
	
	<tr><td colspan="4">
	<html:button property="method" value="Genrate Report" onclick="genrateReport()"></html:button> 
	 
	 
	 </td>
	 </tr>
	 </logic:notEmpty>
	
	  
	 </tr>
	 
	  <logic:notEmpty name="service">
	  <th colspan="4"><center>Service Master Report</center></th>
	  <tr>
	  <td>
	  Service Catagory<html:text name="reportRequestForm" property="s_serviceCatagory"></html:text>
	 
	  </td>
	  
	  <td>
	  Service Group<html:select name="reportRequestForm" property="s_serviceGroup">
	 <html:option value="">-----Select-----</html:option>
     <html:options name="reportRequestForm"  property="s_group" labelProperty="s_groupLabel"></html:options>
	  
	  </html:select>
	  </td>
	  <td>
	  Plant<html:select name="reportRequestForm" property="s_plant">
	  <html:option value="">-----Select-----</html:option>
     <html:options name="reportRequestForm"  property="plant_name" labelProperty="plant_label"></html:options>
	  
	  
	  </html:select>
	  </td>
	  <td>
	  Approve Status <html:select name="reportRequestForm" property="s_status" styleClass="text_field">
	<html:option value="">Select</html:option>
	<html:option value="pending">Pending</html:option>
	<html:option value="approved">Approved</html:option>
	<html:option value="rejected">Rejected</html:option>
	</html:select>
	  </td>
	  </tr>
	  <tr>
	  <td colspan="4">
	  
	  <html:button property="method" value="Genrate Report" onclick="serviceReport()"></html:button>
	  </td>
	   </tr>
	  </logic:notEmpty>
	  <logic:notEmpty name="customer">
	  <th colspan="4"><center>Customer Master Report</center></th>
	  
      <tr>
      <td>Customer Group<html:text name="reportRequestForm" property="c_customerGroup"></html:text></td>
      <td>Approve Status <html:select name="reportRequestForm" property="c_status" styleClass="text_field">
	<html:option value="">Select</html:option>
	<html:option value="pending">Pending</html:option>
	<html:option value="approved">Approved</html:option>
	<html:option value="rejected">Rejected</html:option>
	</html:select></td>	
      <td>Requested By<html:text name="reportRequestForm" property="c_requestedBy"></html:text></td>
      <td>Approve Date<html:text name="reportRequestForm" property="c_approveDate" onclick="popupCalender(c_approveDate)"></html:text>
      </td>
      </tr>
      <tr>
      <td colspan="4">
	  <html:button property="method" value="Genrate Report" onclick="customerReport()"></html:button>
	  </td>
	  </tr>
	  </logic:notEmpty>
	  <logic:notEmpty name="vendor">
	  <th colspan="4"><center>Vendor Master report</center></th>
	 
	  <tr>
	  <td>Vendor Type
	  <html:select name="reportRequestForm" property="v_vendorType" styleClass="text_field" style="width:300px;">
      <html:option value="">--Select--</html:option>
      <html:option value="I">IMPORT</html:option>
       <html:option value="M">MANUFACTURER</html:option>
       <html:option value="D">DELAR</html:option>
       <html:option value="FD">FIRST STAGE DELAR OF INDIGENOUS</html:option>
       <html:option value="SD">SECOND STAGE DELAR OF INDIGENOUS</html:option>
       <html:option value="MD">MANUFACTURER DEPOT</html:option>
          </html:select>
	  
	  </td>
	  <td>Approve Status <html:select name="reportRequestForm" property="v_status" styleClass="text_field">
	<html:option value="">Select</html:option>
	<html:option value="pending">Pending</html:option>
	<html:option value="approved">Approved</html:option>
	<html:option value="rejected">Rejected</html:option>
	</html:select></td>
	  <td>Requested By<html:text name="reportRequestForm" property="v_requestedBy"></html:text></td>
	  <td>Approve Date<html:text name="reportRequestForm" property="v_approveDate" onclick="popupCalender(v_approveDate)"></html:text>
	  </tr>
	  <tr>
	  <td colspan="4">
	   <html:button property="method" value="Genrate Report" onclick="vendorReport()"></html:button>
	   </td>
	   </tr>
	  
	  
	  
	  </logic:notEmpty>
	
	 
	 </table>
		
	</html:form>
</div>
</td>
      </tr>
      </table></td></tr>
    <tr><td align="left">    

    
    
    
</div></td></tr>
</table>
</body>
</html>
