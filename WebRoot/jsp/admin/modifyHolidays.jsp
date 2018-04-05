
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

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />



<title>Microlab</title>

 <script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

<!--
/////////////////////////////////////////////////
-->
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>

<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>
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
if(document.forms[0].location.value=="")
{
alert("Please Select Location");
document.forms[0].location.focus();
return false;
}
if(document.forms[0].holidayName.value=="")
{
alert("Please Enter Holiday Name");
document.forms[0].holidayName.focus();
return false;
}
if(document.forms[0].holidayDate.value=="")
{
alert("Please Select Holiday Date");
document.forms[0].holidayDate.focus();
return false;
}
if(document.forms[0].year.value=="")
{
alert("Please Select Year");
document.forms[0].year.focus();
return false;
}


	var url="adminHoliday.do?method=saveHolidays";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function onModify(){
if(document.forms[0].location.value=="")
{
alert("Please Select Location");
document.forms[0].location.focus();
return false;
}
if(document.forms[0].holidayName.value=="")
{
alert("Please Enter Holiday Name");
document.forms[0].holidayName.focus();
return false;
}
if(document.forms[0].holidayDate.value=="")
{
alert("Please Select Holiday Date");
document.forms[0].holidayDate.focus();
return false;
}
if(document.forms[0].year.value=="")
{
alert("Please Select Year");
document.forms[0].year.focus();
return false;
}
	var url="adminHoliday.do?method=update";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function onDelete(){
	var url="adminHoliday.do?method=delete";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function onBack(){
	var url="adminHoliday.do?method=displayHolidays";
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
	function test()
	{
	var holidayDate=document.forms[0].holidayDate.value;
	var a=holidayDate.split("/")
    document.forms[0].year.value=a[2];	
	}

//-->
</script>
<style>
input:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
select:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
</style>
</head>

<body >
		<!--------WRAPER STARTS -------------------->
		               <div align="left" class="bordered">
									<table border="0" width="100%"  class="sortable">
	<tr>
					<th colspan="8" style="text-align: center;"><big>Holiday Form</big></th>
				</tr>

                	</table>
                </div>
		<div align="center">
				<logic:notEmpty name="adminHolidayForm" property="message">
					<font color="green" size="3"><b><bean:write name="adminHolidayForm" property="message" /></b></font>
				</logic:notEmpty>
			</div>			
					
		<html:form action="/adminHoliday.do" enctype="multipart/form-data">
		
		
		 
		
					<% 
		String status=(String)session.getAttribute("result");		
		if(status==""||status==null)
		{
		
		}
		else{
		
		%>
		<b><font color="red"><%=status %></font></b>
		<%
		session.setAttribute("result", " ");
		}
		 %>
						<center>
						<div id="materialTable" style="visibility: visible;">
							
							<table  class="sortable bordered" > 
										
										<input type="hidden" name="MenuIcon2" value="<%=request.getAttribute("MenuIcon") %>"/>    
										
				   			         <tr>
							              <td>Location Name</td>
							           <td>
								            <html:select property="location" > 
									        <html:option value="">--SELECT--</html:option>
									        <html:options property="locationId" labelProperty="locationName"/>
								            </html:select>	
									   </td>
									      <td>Holiday Name</td>
									<td>
										<html:text property="holidayName" ></html:text> 
									</td>
									 
                                </tr>
								      <tr>
									<td>
										Holiday Date
									</td>
									<td>
										<html:text property="holidayDate"   styleId="holidayDateID" onfocus="popupCalender('holidayDateID')"  readonly="true" onchange="test()"></html:text>
								       	<html:hidden property="modifyDate"/>
						
									</td>
								
									<td>
										Year
									</td>
									 <td>
								            <html:select property="year" >
									        <html:option value="">--SELECT--</html:option>
									        <html:option value="2010">2010</html:option>
									        <html:option value="2011">2011</html:option>
									        <html:option value="2012">2012</html:option>
									        <html:option value="2013">2013</html:option>
									        <html:option value="2014">2014</html:option>
									        <html:option value="2015">2015</html:option>
									        <html:option value="2016">2016</html:option>
									        <html:option value="2017">2017</html:option>
									        <html:option value="2018">2018</html:option>
									        <html:option value="2019">2019</html:option>
									        <html:option value="2020">2020</html:option>
								            </html:select>	
									   </td>
								</tr>

								
									<logic:empty name="modifyButton">
								<tr>
									 <td colspan="6" align="center" >
										<html:button property="method" value=" Save " onclick="onSave()" styleClass="rounded"/>
											<html:reset  value=" Reset " styleClass="rounded"/>
											<html:button property="method" value=" Back " onclick="onBack()" styleClass="rounded"/>
									</td>
								</tr>
								</logic:empty>
								<logic:notEmpty name="modifyButton">
								<tr>
									<td colspan="6" align="center" >
										<html:button property="method" value=" Modify " onclick="onModify()" styleClass="rounded"/>
									
										<html:button property="method" value=" Delete " onclick="onDelete()" styleClass="rounded"/>
										<html:button property="method" value=" Back " onclick="onBack()" styleClass="rounded"/>
									</td>
								</tr>

							</logic:notEmpty>
						

										</table>
									
										
										</center>
										</html:form>


						</div>
                

  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    
  </tr>
</table>
</td>
</tr>
</table>

 
</body>
</html>
