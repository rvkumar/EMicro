
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
<title>Customer Master List</title>
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
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



var url="materialCodeExtenstion.do?method=saveApproveData";
			document.forms[0].action=url;
			document.forms[0].submit();


}
function getPlant(){
		
		
		var url="materialCodeExtenstion.do?method=getPlantDetails";
			document.forms[0].action=url;
			document.forms[0].submit();
		
}
function getStorage(){
		
		
			var url="materialCodeExtenstion.do?method=getStorageLocationDetails";
			document.forms[0].action=url;
			document.forms[0].submit();
		
}

function closeData(){
var url="materialCodeExtenstion.do?method=displayCodeExtenstionList";
			document.forms[0].action=url;
			document.forms[0].submit();
}

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
  
  
     		<div class="middel-blocks">
     			<div align="center">
						<logic:present name="materialCodeExtenstionForm" property="massage1">
						<font color="red">
							<bean:write name="materialCodeExtenstionForm" property="massage1" />
						</font>
					</logic:present>
					<logic:present name="materialCodeExtenstionForm" property="massage2">
						<font color="Green">
							<bean:write name="materialCodeExtenstionForm" property="massage2" />
						</font>
					</logic:present>
					</div>
<html:form action="/materialCodeExtenstion.do" enctype="multipart/form-data">
				
<table align="center" border="0" cellpadding="4" cellspacing="0" id="mytable1">

   <tr>
	 <th colspan="2"><center>Material Code Extension Request Form</center></th>
   </tr>
  
		<tr>
		<th width="274" class="specalt" scope="row">Request No<img src="images/star.gif" width="8" height="8" /></th>
						<td align="left">
							<label>
								<html:text property="requestNo"
									styleClass="text_field" readonly="true"
									maxlength="50" />
									<html:hidden property="actionType"/>
							</label>
						</td>
							</tr>
						<tr>
						<th width="274" class="specalt" scope="row">Request Date<img src="images/star.gif" width="8" height="8" /></th>
						<td align="left">
							<html:text property="requestDate" readonly="true"
								styleClass="text_field" />
						</td>
					</tr>
					<tr>
					<td>
					</td>
					<td>
					Plant
								<html:checkbox property="plantType" value="Plant" onclick="getPlant()"  style="width :25px;"/>
								
								Storage Location
								<html:checkbox property="plantType" value="Storage Location" onclick="getStorage()"  style="width :25px;"/>
					
					<!--<html:radio property="plantType" value="Plant" onclick="getPlant()">Plant</html:radio>
					<html:radio property="storageLocationType" value="Storage Location" onclick="getStorage()">Storage Location</html:radio>
					
					
					--></td>
					</tr>
					
					
			
				<logic:notEmpty name="plantMandatory">
				<tr>
	 <th colspan="2">Plant Details:</th>
   </tr>
   <tr>
		<th width="274" class="specalt" scope="row">Material Code<img src="images/star.gif" width="8" height="8" /></th>
						<td align="left">
							<label>
								<html:text property="materialCode1" readonly="true"
									styleClass="text_field" 
									maxlength="50" />
							</label>
						</td>
							</tr>
					<tr>
	<th width="274" class="specalt" scope="row">Extend From Plant<img src="images/star.gif" width="8" height="8" />
			
				<td align="left">
				<html:select name="materialCodeExtenstionForm" property="plant1" styleClass="text_field" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="materialCodeExtenstionForm" property="locationIdList" 
									labelProperty="locationLabelList"/>
					</html:select>
				</td>
			</tr>
			
	<tr>
	 	<th width="274" class="specalt" scope="row">Extend To Plant<img src="images/star.gif" width="8" height="8" />
			
				<td align="left">
				<html:select name="materialCodeExtenstionForm" property="extendToPlant1" styleClass="text_field" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="materialCodeExtenstionForm" property="locationIdList" 
									labelProperty="locationLabelList"/>
					</html:select>
				<br /></td>
			</tr>
	<tr>
			<th width="274" class="specalt" scope="row">Extend From Storage Location<img src="images/star.gif" width="8" height="8" /> </th>
			<td>
			<html:select  property="storageLocationId1" styleClass="text_field" style="width:250px;" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="materialCodeExtenstionForm" property="storageID" 
									labelProperty="storageIDName"/>
					</html:select>
			</td>
			</tr>	
			<tr>
			<th width="274" class="specalt" scope="row">Extend To Storage Location<img src="images/star.gif" width="8" height="8" /> </th>
			<td>
			<html:select  property="extendToStorageLocation1" styleClass="text_field" style="width:250px;" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="materialCodeExtenstionForm" property="storageID" 
									labelProperty="storageIDName"/>
					</html:select>
			</td>
			</tr>	
			</logic:notEmpty>	
			


<logic:notEmpty name="storageMandatory">
<tr>
	 <th colspan="2">Storage Location Details:</th>
   </tr>
   <tr>
		<th width="274" class="specalt" scope="row"> Material Code<img src="images/star.gif" width="8" height="8" /></th>
						<td align="left">
							<label>
								<html:text property="materialCode2" readonly="true"
									styleClass="text_field" 
									maxlength="50" />
							</label>
						</td>
		</tr>
<tr>
	<th width="274" class="specalt" scope="row">Extend From Plant<img src="images/star.gif" width="8" height="8" /></th>
			
				<td align="left">
				<html:select name="materialCodeExtenstionForm" property="plant2" styleClass="text_field" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="materialCodeExtenstionForm" property="locationIdList" 
									labelProperty="locationLabelList"/>
					</html:select>
				</td>
			</tr>
			
<tr>
			<th width="274" class="specalt" scope="row">From Storage Location<img src="images/star.gif" width="8" height="8" /> </th>
			<td>
			<html:select  property="fromStorageLocation" styleClass="text_field" style="width:250px;" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="materialCodeExtenstionForm" property="storageID" 
									labelProperty="storageIDName"/>
					</html:select>
			</td>
			</tr>	
			<tr>
			<th width="274" class="specalt" scope="row">To Storage Location<img src="images/star.gif" width="8" height="8" /> </th>
			<td>
			<html:select  property="toStorageLocation" styleClass="text_field" style="width:250px;" disabled="true">
					<html:option value="">--Select--</html:option>
					<html:options name="materialCodeExtenstionForm" property="storageID" 
									labelProperty="storageIDName"/>
					</html:select>
			</td>
			</tr>
</logic:notEmpty>
   <tr>			
	<th colspan="2">Approver Details:</th>
	   </tr>
	<tr> 
		<th width="274" class="specalt" scope="row">Approve Type
			<td align="left">
			
			<html:select name="materialCodeExtenstionForm" property="approveType" styleClass="text_field">
				<html:option value="">--Select--</html:option>
					<html:option value="Pending">Pending</html:option>
					<html:option value="Approved">Approved</html:option>
					<html:option value="Cancel">Cancel</html:option>
			</html:select>
			
			<br /></td></tr>
			
   <tr>
 <td colspan="4" align="center">
 <logic:notEmpty name="saveButton">
		<html:button property="method"  value="Save" onclick="saveData()" ></html:button>
		<html:reset value="Reset"></html:reset>	
		</logic:notEmpty>
		
		<html:button property="method"  value="Close" onclick="closeData()" ></html:button>
		</td>
   </tr>
   </table>			
   	
</html:form>

		
</body>
</html>
