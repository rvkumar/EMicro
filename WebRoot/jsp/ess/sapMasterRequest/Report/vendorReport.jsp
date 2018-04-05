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








function onSearch(){
		
		var URL="serviceMasterRequest.do?method=searchRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}
function nextRecord(){
     var URL="reportRequest.do?method=nextVendorRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}
function previousMaterialRecord(){
  var URL="reportRequest.do?method=prevVendorRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}
function lastMaterialRecord(){
  var URL="reportRequest.do?method=lastVendorRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}
function firstMaterialRecord(){
 var URL="reportRequest.do?method=firstVendorRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();


}
function showreport(){
     var URL="reportRequest.do?method=showSearchBar";
		document.forms[0].action=URL;
 		document.forms[0].submit();

}

function genrateExcel(){

   
	
	document.forms[0].action="reportRequest.do?method=genrateExcelVendorRecord";
	document.forms[0].submit();
}
function genratePdf(){

 
	document.forms[0].action="reportRequest.do?method=genratePdfVendorRecord";
	document.forms[0].submit();


}
function genrateCSV(){

document.forms[0].action="reportRequest.do?method=genrateCsvVendorRecord";
	document.forms[0].submit();
}
function datarange(){
    var URL="reportRequest.do?method=selectRows";
		document.forms[0].action=URL;
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
'images/it_hover.jpg','images/timeout_hover.jpg','images/admin_hover.jpg'),subMenuClicked()">
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
  
  
    
     
   
    
    
      <tr>
        <td colspan="3" align="center" valign="top" style="padding-top:3px">
        <div class="middelpart2">
        <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
   
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
		
	
		</th>
		</tr>
		
		 
		
	 <table>
	 <th colspan='2'>Vendor Report</th>
	 <tr><td><%--Total Records Found--<bean:write name="reportRequestForm" property="row"></bean:write></td>--%></tr>
	 <tr><td>
	 
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
	<bean:write property="startRecord"  name="reportRequestForm"/>-
	</td>
	<td>
	<bean:write property="endRecord"  name="reportRequestForm"/>
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
	<td></td>
	
	
   
	</logic:notEmpty>
	 
	 <td>
	 
	
	 
	 </td></tr>
	 </table>
	 
	 
	 
	 
	 
	 
	 
	 </td></tr>
	
	  <tr>
	<logic:notEmpty name="vendorList">
		<table align="center">
		<tr>
		<td>
		
		
		
		</td>
		</tr>		
		<tr>
			<td valign="top" align="left">
			
         <display:table id="mytable1" name="vendorList" requestURI="/reportRequest.do" pagesize="10" export="false">
     	
     
        <display:column property="v_requestDate" title="Request date"></display:column>
        <display:column property="v_name" title="Vendor Name"></display:column> 
         <display:column property="v_location" title="Location"></display:column> 
           <display:column property="v_country" title="Country"></display:column> 
            <display:column property="v_vendorType" title="Vendor type"></display:column> 
             <display:column property="v_status" title="approve Status"></display:column>
             <display:column property="v_approveDate" title="approve Date"></display:column>
       
      
         
        </display:table>
        
	
			</td></tr>
			
			</logic:notEmpty>
	  
	 </tr>
	 <tr>
	 <td>
	<img src="images/excel.jpg"  onclick="genrateExcel()" ></img>&nbsp; <img src="images/adobe_reader_logo.png"  onclick="genratePdf()" ></img>&nbsp;<img src="images/CSV.png"  onclick="genrateCSV()" ></img>
	 
	 </td></tr>
	 </table>
	<html:hidden name="reportRequestForm" property="query"/>
   
    <html:hidden name="reportRequestForm" property="total"/>
	<html:hidden name="reportRequestForm" property="next"/>
    <html:hidden name="reportRequestForm" property="prev"/>
		
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
