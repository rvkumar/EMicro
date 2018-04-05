<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.admin.form.LinksForm"/>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style/content.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<title>eMicro :: Holiday Details</title>

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="js/sorttable.js"></script>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript">


<!--

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0

	document.getElementById('conPer').style.cssText = 'border: none;color: grey';
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



function onSave(){
	var url="adminHoliday.do?method=saveHolidays";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function onModify(){
	var url="adminHoliday.do?method=update";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function onDelete(){
	var url="adminHoliday.do?method=delete";
	document.forms[0].action=url;
	document.forms[0].submit();
}


function addHolidays(){
	var url="adminHoliday.do?method=modifyHolidays";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function searchMaterialRecord(){

var url="adminHoliday.do?method=searchEmpHolidays";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function popupCalender(param)
	{
	var cal = new Zapatec.Calendar.setup({
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2" // trigger button
	});
	}
	
	function cleardetails()
	{
	
	var url="adminHoliday.do?method=displayEmpHolidays";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}


//-->
</script>
</head>

<body >
		<!--------WRAPER STARTS -------------------->
		
		
					
		<html:form action="/adminHoliday.do" enctype="multipart/form-data" onsubmit="searchMaterialRecord();return false;" >
		
			
		<table class="bordered" style="width:90%;"><tr>
		
		<th>
		<big>Year &nbsp;</big></th><td>
		<html:select property="year" name="adminHolidayForm" >
		                   <html:option value="">--Select-</html:option>
							
							<html:option value="2013">2013</html:option>
							<html:option value="2014">2014</html:option>
							<html:option value="2015">2015</html:option>
							<html:option value="2016">2016</html:option>
							 <html:option value="2017">2017</html:option>
							 <html:option value="2018">2018</html:option>
	</html:select>
	</td>
	<th>
		<big>Location &nbsp;</th><td></big><html:select property="requiredLocation" style="width:200px">
		<html:option value="">--Select--</html:option>
					<html:options name="adminHolidayForm" property="locationId" 
									labelProperty="locationName"/>
									
		</html:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#"><img align="absmiddle"  src="images/clearsearch.jpg"  onclick="cleardetails()" title="clear"/></a>&nbsp;&nbsp;&nbsp;
		<html:text property="requiredString"  styleClass="rounded" style="width:200px;" ></html:text>&nbsp;&nbsp;&nbsp;
	 <a href="#"><img align="absmiddle"  src="images/search.png"  onclick="searchMaterialRecord()" title="search"/></a></td>
		</table>
		</br>

			<table class="bordered sortable"><tr>
	<th colspan="6"><center><big>Holiday List</big></th>
</tr></center></table>
			<div class="bordered"><div> 
			</div><table border="0" width="100%">
				<tr>
					<th align="center">Sl. No</th>
					<th align="center">Location</th>
					<th align="center">Day</th>
					<th align="center">Date</th>					
					<th align="center">Occasion</th>
				</tr>
						
				<%
					int count = 1;
				%>
	
				<%
					int count1 = 1;
				%>
	
				<logic:iterate name="listDetails" id="abc">
						<%if(count == 1) {%>
					<tr>
						<td>
							<%=count1++ %>
						</td>
											
						<td><bean:write name="abc" property="location"/></td>
						<td><bean:write name="abc" property="dayName"/></td>
						<td><bean:write name="abc" property="holidayDate"/></td>
						<td><bean:write name="abc" property="holidayName"/></td>
					</tr>
		
					<%
						count++;
						} else {
							int oddoreven=0;
							oddoreven  = count%2;
						if(oddoreven == 0)
						{
					%>
		
					<tr>
						<td>	
							<%=count1++ %>
						</td>
											
						<td><bean:write name="abc" property="location"/></td>
						<td><bean:write name="abc" property="dayName"/></td>
						<td><bean:write name="abc" property="holidayDate"/></td>
						<td><bean:write name="abc" property="holidayName"/></td>
					</tr>
													
					<% }else{%>
					
					<tr>
						<td>
							<%=count1++ %>
						</td>
											
						<td><bean:write name="abc" property="location"/></td>
						<td><bean:write name="abc" property="dayName"/></td>
						<td><bean:write name="abc" property="holidayDate"/></td>
						<td><bean:write name="abc" property="holidayName"/></td>
					</tr>
		
					<% }count++;}%>
		
				</logic:iterate>
			</table>
			</div>
	</html:form>
 
</body>
</html>
