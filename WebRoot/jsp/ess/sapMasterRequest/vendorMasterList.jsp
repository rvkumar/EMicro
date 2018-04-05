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
<title>Vendor Master List</title>




<script type='text/javascript' src="calender/js/zapatec.js"></script>

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
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link href="style1/style.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript">
<!--


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

function copyVendor(requestNo){

var url="vendorMasterRequest.do?method=copyVendorRecord&RequestNo="+requestNo;
			document.forms[0].action=url;
			document.forms[0].submit();

}
function nextMaterialRecord()
{

var url="vendorMasterRequest.do?method=nextVendorRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function previousMaterialRecord()
{

var url="vendorMasterRequest.do?method=previousVedorRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function firstMaterialRecord()
{

var url="vendorMasterRequest.do?method=displayVendorMasterList";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastMaterialRecord()
{

var url="vendorMasterRequest.do?method=lastVedorRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

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

document.forms[0].action="vendorMasterRequest.do?method=deleteVendorMaster";
document.forms[0].submit();
}
}



function newVendorForm(){
		
		var URL="vendorMasterRequest.do?method=getVendorRecords";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}

function onSearch(){
		
		var URL="vendorMasterRequest.do?method=searchRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}

function sendMailToApproval(RequestNo)
{
var URL="vendorMasterRequest.do?method=sendMailToApprove&ReqNo="+RequestNo;
		document.forms[0].action=URL;
 		document.forms[0].submit();

}


function onClear()
{
document.forms[0].searchRequired.value="";
var URL="vendorMasterRequest.do?method=displayVendorMasterList";
		document.forms[0].action=URL;
 		document.forms[0].submit();

}

function onSearchRequired()
{
if(document.forms[0].searchRequired.value=="")
{
alert("Please Enter Some Keyword");
document.forms[0].searchRequired.focus();
return false;
}
var URL="vendorMasterRequest.do?method=getRequiredSearch";
		document.forms[0].action=URL;
 		document.forms[0].submit();

}

function submitVendorForm()
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


var url="vendorMasterRequest.do?method=SubmitAllvendor"
			document.forms[0].action=url;
			document.forms[0].submit();

}


}

function viewMaterial(reqNo)
{

var url="vendorMasterRequest.do?method=ViewVendorrecord&ReqestNo="+reqNo;
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

<body onload="subMenuClicked('<bean:write name='vendorMasterRequestForm' property='linkName'/>')">

   			<%
			
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			if(menuIcon==null){
			menuIcon="";
			}
			
			%>
			
			<% 
  			  UserInfo user=(UserInfo)session.getAttribute("user");
  			%>
  
  
    
				
					 <html:form action="vendorMasterRequest.do" enctype="multipart/form-data">		
					  <div align="center">
				<logic:present name="vendorMasterRequestForm" property="message">
					<font color="red" size="3"><b><bean:write name="vendorMasterRequestForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="vendorMasterRequestForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="vendorMasterRequestForm" property="message2" /></b></font>
				</logic:present>
			</div>
			
           
	</div>
	
<table class="sortable bordered">
<tr>
<th><center><big>Vendor Master List</big></center></th></tr></table>	
<br/>
	
	<table  border="0" align="center" cellpadding="0" cellspacing="0"   >
	<tr>
<td>
<html:button property="method" value=" New "   onclick="newVendorForm()" styleClass="rounded" style="width: 80px;"></html:button>
	&nbsp;&nbsp;
			<html:button property="method" value="Delete" onclick="deleteCustomerRecord()" styleClass="rounded" style="width: 80px;"/>
			&nbsp;&nbsp;
			<html:button property="method" value="Submit For Approval" onclick="submitVendorForm();" styleClass="rounded" style="width: 120px;"></html:button>&nbsp;&nbsp;
			
	
		&nbsp;&nbsp;
Status&nbsp;&nbsp;<html:select property="approveType" styleClass="text_field">
	<html:option value="">Select</html:option>
	<html:option value="Created">Created</html:option>
	<html:option value="Submited">Submited</html:option>
	<html:option value="In Process">In Process</html:option>
	<html:option value="Completed">Completed</html:option>
	<html:option value="Rejected">Rejected</html:option>
	<html:option value="deleted">Deleted</html:option>
	</html:select>
	<a href="#"><img src="images/search.png"  onclick="onSearch()" align="absmiddle"/></a> &nbsp;&nbsp;&nbsp;
	
	<a href="#"><img src="images/clearsearch.jpg"  onclick="onClear()" align="absmiddle"/></a>
	<html:text property="searchRequired" styleClass="rounded" style="width:200px;" title="Vendor Name,Place,Requested By,Plant,Status"/>
	<a href="#"><img src="images/search.png"  onclick="onSearchRequired()" align="absmiddle"/></a>
	&nbsp;&nbsp;
	 	<logic:notEmpty name="displayRecordNo">
	  	<img src="images/First10.jpg" onclick="firstMaterialRecord()" align="absmiddle"/>
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">

	
	<img src="images/previous1.jpg" onclick="previousMaterialRecord()" align="absmiddle"/>

	</logic:notEmpty>
	
	
	
	<bean:write property="startRecord"  name="vendorMasterRequestForm"/>-
	
	
	<bean:write property="endRecord"  name="vendorMasterRequestForm"/>

	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextMaterialRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	
	
	<img src="buttons/disableRight.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastMaterialRecord()" align="absmiddle"/>
	

	
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	</logic:notEmpty>
	</td>
	 </tr>
	 </table>


<br/>
	

	 <logic:notEmpty name="vendorMasterList"> 
	 
	 
	 	<div align="left" class="bordered">
			<table class="sortable">
				<tr>
					<th style="width:50px;">Req No</th><th style="width:100px;">Request Date</th><th style="width:200px;">Vendor Name</th><th >Place</th>
					<th style="width:100px;">Requested By</th><th style="width:100px;">Department</th><th style="width:100px;">Plant</th>
					<th style="width:100px;">Status</th><th style="width:50px;">Delete</th><th style="width:50px;">Edit</th><th style="width:50px;">Select</th><th style="width:50px;">Submit For Approval</th>
				    <th>Copy</th><th>View</th>
				</tr>
				<logic:iterate id="vendList" name="vendorMasterList">
				<tr>
						<td><bean:write name="vendList" property="requestNo"/></td>
						<td><bean:write name="vendList" property="requestDate"/></td>
						<td><bean:write name="vendList" property="name"/></td>
						<td><bean:write name="vendList" property="city"/></td>
						<td><bean:write name="vendList" property="requestedBy"/></td>
						<td><bean:write name="vendList" property="department"/></td>
						<td><bean:write name="vendList" property="locationId"/></td>
						
						<td><bean:write name="vendList" property="approveType"/></td>
                       <logic:equal value="Created"  property="approveType" name="vendList">					
						<td><html:checkbox property="requiredRequestNumber" value="${vendList.requestNo}" ></html:checkbox> </td>	
						<td><a href="vendorMasterRequest.do?method=editVendorRecord&requstNo=${vendList.requestNo}"><img src="images/edit1.jpg"/></a></td>
						 <td><html:checkbox property="getReqno" value="${vendList.requestNo}" ></html:checkbox></td>
						<td><html:button property="method" value="Submit" styleClass="rounded"   title="Submit For Approval" onclick="sendMailToApproval('${vendList.requestNo}')"></html:button></td>
					</logic:equal>
					 <logic:equal value="Rejected"  property="approveType" name="vendList">					
						<td><html:checkbox property="requiredRequestNumber" value="${vendList.requestNo}" ></html:checkbox> </td>	
						<td><a href="vendorMasterRequest.do?method=editVendorRecord&requstNo=${vendList.requestNo}"><img src="images/edit1.jpg"/></a></td>
						 <td><html:checkbox property="getReqno" value="${vendList.requestNo}" ></html:checkbox></td>
						<td>&nbsp;</td>
					</logic:equal>
					
					<logic:equal value="In Process" property="approveType" name="vendList">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					</logic:equal>
					<logic:equal value="Submited" property="approveType" name="vendList">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
							<td>&nbsp;</td>
					</logic:equal>
					<logic:equal value="Completed" property="approveType" name="vendList">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
							<td>&nbsp;</td>
					</logic:equal>
					<logic:equal value="deleted" property="approveType" name="vendList">
					<td>&nbsp;</td>
					<td><a href="vendorMasterRequest.do?method=editVendorRecord&requstNo=${vendList.requestNo}"><img src="images/edit1.jpg"/></a></td>
					<td>&nbsp;</td>	
					<td>&nbsp;</td>
			
					</logic:equal>
				<td><a href="# " title="copy" onclick="copyVendor('${vendList.requestNo}')"><img src="images/copy.png" height="28" width="28" align="absmiddle" /></a></td>
					<td id="${vendList.requestNo}">
      						<a href="#">
      							<img src="images/view.gif" height="28" width="28" title="View Record" onclick="viewMaterial('${vendList.requestNo}')"/></a>
      						</td>
      							
					</tr>
    </logic:iterate>
    
    </table></div>
    
			
			</logic:notEmpty>
		
<logic:notEmpty name="noRecords">
<table class="sortable bordered">
				<tr>
					<th style="width:50px;">Req No</th><th style="width:100px;">Request Date</th><th style="width:200px;">Vendor Name</th><th >Place</th>
					<th style="width:100px;">Requested By</th><th style="width:100px;">Department</th><th style="width:100px;">Plant</th>
					<th style="width:100px;">Status</th><th style="width:50px;">Delete</th><th style="width:50px;">Edit</th><th style="width:50px;">Select</th><th style="width:50px;">Submit For Approval</th>
				<th>Copy</th><th>View</th>
				</tr>
				<tr>
				<br/>
				<div align="center">
				<logic:present name="vendorMasterRequestForm" property="message1">
					<td colspan="13">
<div align="center">
<font color="red" size="3">No records found</font>
						</font>
					</logic:present>
				</div>
			
</table>
</logic:notEmpty>			
			

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
