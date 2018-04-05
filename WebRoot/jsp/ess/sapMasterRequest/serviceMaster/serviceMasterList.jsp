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
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Service Master List</title>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<script type="text/javascript" src="js/sorttable.js"></script>

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
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<script type="text/javascript" src="js/sorttable.js"></script>

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

var rows=document.getElementsByName("requiredRequestNumber");
	
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
alert('please select atleast one record');
}
else
{

	document.forms[0].action="serviceMasterRequest.do?method=deleteRecord";
document.forms[0].submit();
}
}



function newServiceForm(){
		var URL="serviceMasterRequest.do?method=getServiceMasterRecords";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function onSearch(){
        var s=document.forms[0].plantCode.value;
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
 var URL="serviceMasterRequest.do?method=searchRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();


}
function sendMailToApproval(RequestNo,location)
{

var URL="serviceMasterRequest.do?method=sendMailToApprove&ReqNo="+RequestNo+"&LoctID="+location;

		document.forms[0].action=URL;
 		document.forms[0].submit();

}
function submitserviceForm()
{

var rows=document.getElementsByName("getReqno");
	
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
alert('please select atleast one record');
}
else
{


var url="serviceMasterRequest.do?method=submittAllServiceMaterial"
			document.forms[0].action=url;
			document.forms[0].submit();

}


}

function viewMaterial(reqNo)
{

var url="serviceMasterRequest.do?method=ViewServiceMasterrecord&ReqestNo="+reqNo;
			document.forms[0].action=url;
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

<body>
<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">

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
  
  
    <td align="left" valign="top" height:180px; width:100%">
    <table width="95%" border="0" align="left" cellpadding="0" cellspacing="0">
     
  
    
    
      <tr>
        <td colspan="3" align="left" valign="top" style="padding-top:3px">
        <div class="middelpart2">
        <table width="95%" border="0" align="left" cellpadding="0" cellspacing="0" >
  <tr>
    
    <td colspan="4" align="left" valign="top">
    <div class="middel-blocks">
    <div align="left">
						 <div align="center">
				<logic:present name="serviceMasterRequestForm" property="message">
					<font color="red" size="3"><b><bean:write name="serviceMasterRequestForm" property="message2" /></b></font>
				</logic:present>
				<logic:present name="serviceMasterRequestForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="serviceMasterRequestForm" property="message" /></b></font>
				</logic:present>
					
					<html:form action="serviceMasterRequest.do" enctype="multipart/form-data">
					
	<table class="sortable bordered">
<tr>
<th><center><big>Service Master List</big></center></th></tr></table>
	<div>&nbsp;</div>			
<table  border="0" align="center" cellpadding="0" cellspacing="0" >
	<tr>
<td><html:button property="method" value="New"   onclick="newServiceForm()" styleClass="rounded" style="width: 100px;" ></html:button>&nbsp;
<html:button property="method" value="Delete" onclick="deleteCustomerRecord()" styleClass="rounded" style="width: 100px;"/>&nbsp;
<html:button property="method" value="Submit For Approval" onclick="submitserviceForm();" styleClass="rounded" style="width: 120px;"></html:button>&nbsp;&nbsp;

Location&nbsp;<html:select property="plantCode" styleId="requestDate"	 styleClass="text_field" >
		<html:option value="">--select--</html:option>
		<html:options property="locationIdList" labelProperty="locationLabelList"></html:options>
		</html:select>&nbsp;
Status&nbsp;<html:select property="approveStatus">
	    <html:option value="">--Select--</html:option>
	   <html:option value="Created">Created</html:option>
	    <html:option value="Submited">Submited</html:option>
	    <html:option value="In Process">In Process</html:option>
	     <html:option value="Completed">Completed</html:option>
	    <html:option value="Rejected">Rejected</html:option>
	    <html:option value="deleted">Deleted</html:option>
	    </html:select> 

<a href="#"><img src="images/search.png"  onclick="onSearch()" align="absmiddle"/></a>		
</td>	
		
</tr>
</table>

<div>&nbsp;</div>		
<logic:notEmpty name="serviceMasterList"> 
		
		 <table align="center">
<logic:notEmpty name="displayRecordNo">
	 
	  	<td>
	  	<img src="images/First10.jpg" onclick="firstMaterialRecord()" align="absmiddle"/>
	
	
	<logic:notEmpty name="disablePreviousButton">

	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousMaterialRecord()" align="absmiddle"/>

	</logic:notEmpty>
	
	<bean:write property="startRecord"  name="serviceMasterRequestForm"/>-
	
	<bean:write property="endRecord"  name="serviceMasterRequestForm"/>

	<logic:notEmpty name="nextButton">

	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>

	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	
	<img src="images/disableRight.jpg" align="absmiddle"/>

	
	</logic:notEmpty>
	
		<img src="images/Last10.jpg" onclick="lastMaterialRecord()" align="absmiddle"/>
	
	</td>
	
	<html:hidden property="total"/>
	<html:hidden property="next"/>
	<html:hidden property="prev"/>

	

	
	</logic:notEmpty>
	 </tr>
	 </table>
		
  
        
        
        <logic:notEmpty name="serviceMasterList"> 
	<div class="bordered">
		<table class="sortable">
			<tr>
					<th style="width:50px;">Req No</th><th style="width:100px;">Location</th>
					<th style="width:100px;">Service&nbsp;Description</th><th style="width:100px;">Approve Status</th>
					<th style="width:50px;">Delete</th><th style="width:50px;">Edit</th><th style="width:50px;">Select</th><th style="width:50px;">Submit For Approval</th>
					<th style="width:50px;">Copy</th><th>View</th>
				</tr>
		<logic:iterate id="mytable1" name="serviceMasterList">
				<tr>
						<td><bean:write name="mytable1" property="r_no"/></td>
						<td><bean:write name="mytable1" property="plantCode"/></td>
						<td><bean:write name="mytable1" property="serviceDescription"/></td>
							<td><bean:write name="mytable1" property="approveStatus"/></td>
						
						<logic:equal value="Created" property="approveStatus" name="mytable1">					
						<td><html:checkbox property="requiredRequestNumber" value="${mytable1.r_no}" /> </td>	
						<td><a href="serviceMasterRequest.do?method=editRecord&requstNo=${mytable1.r_no}"><img src="images/edit1.jpg"/></a></td>
											 <td><html:checkbox property="getReqno" value="${mytable1.r_no}" ></html:checkbox></td>
					
					<td><html:button property="method" value="Submit" title="Submit For Approval" styleClass="rounded"  onclick="sendMailToApproval('${mytable1.r_no}','${mytable1.plantCode }')"></html:button></td>
					</logic:equal>
					<logic:equal value="Rejected" property="approveStatus" name="mytable1">					
						<td><html:checkbox property="requiredRequestNumber" value="${mytable1.r_no}" /> </td>	
						<td><a href="serviceMasterRequest.do?method=editRecord&requstNo=${mytable1.r_no}"><img src="images/edit1.jpg"/></a></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					</logic:equal>
					<logic:equal value="In Process" property="approveStatus" name="mytable1">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					</logic:equal>
						<logic:equal value="Submited" property="approveStatus" name="mytable1">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					</logic:equal>
							<logic:equal value="Completed" property="approveStatus" name="mytable1">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					</logic:equal>
					<logic:equal value="deleted" property="approveStatus" name="mytable1">
				<td>&nbsp;</td>
					<td><a href="serviceMasterRequest.do?method=editRecord&requstNo=${mytable1.r_no}"><img src="images/edit1.jpg"/></a></td>
					<td>&nbsp;</td>	
					<td>&nbsp;</td>
			
					</logic:equal>
										<td><a href="serviceMasterRequest.do?method=copyRecord&requstNo=${mytable1.r_no}"><img src="images/copy.png" height="30" width="30" align="absmiddle" /></a></td>	
						<td id="${mytable1.r_no}">
      						
      						<a href="#">
      							<img src="images/view.gif" height="28" width="28" title="View Record" onclick="viewMaterial('${mytable1.r_no}')"/></a>
      						</td>
      						
                    		</tr>
    </logic:iterate>
   
			</logic:notEmpty>
			
		</table>
 </div>
	
			
			
	
					
				</logic:notEmpty>
	
					
				
							</html:form>
</div>
</td>
      </tr>
      </table></td></tr>
    <tr><td align="left">    

    
    
    
</div></td></tr>
</table>
<logic:notEmpty name="noRecords">
<table class="sortable bordered">
			<tr>
					<th style="width:50px;">Req No</th><th style="width:100px;">Location</th>
					<th style="width:100px;">Service&nbsp;Description</th><th style="width:100px;">Approve Status</th>
					<th style="width:50px;">Delete</th><th style="width:50px;">Edit</th><th style="width:50px;">Select</th><th style="width:50px;">Submit For Approval</th>
					<th style="width:50px;">Copy</th><th>View</th>
				</tr>
	</table>
		<logic:present name="serviceMasterRequestForm" property="message1">
						<font color="red">
							<center><bean:write name="serviceMasterRequestForm" property="message1"/></center>
							<br/>
						</font>
		</logic:present>			
</logic:notEmpty>				
</body>
</html>

