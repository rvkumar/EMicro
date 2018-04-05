<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />



	<title>eMicro :: Headlines_Announcement Creation</title>
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

  <style type="text/css">
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>



<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>
<script type="text/javascript">$(function () {


	$('#timeFrom').timeEntry();
});


$(function () {


	$('#timeTo').timeEntry();
});

$('.timeRange').timeEntry({beforeShow: customRange}); 
 
function customRange(input) { 
    return {minTime: (text.styleId == 'timeTo' ? 
        $('#timeFrom').timeEntry('getTime') : null),  
        maxTime: (text.styleId  == 'timeFrom' ? 
        $('#timeTo').timeEntry('getTime') : null)}; 
}

</script>

<script type="text/javascript">
<!--
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
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2" // trigger button
	});
	}


function onSave(){	   
var stdate=document.forms[0].fromDate.value;
var endate=document.forms[0].toDate.value;
var sttime=document.forms[0].fromTime.value;
var entime=document.forms[0].toTime.value;
var title=document.forms[0].headLines.value;
var subject=document.forms[0].subject.value;

 var loc=document.forms[0].locationId.value;
var cat=document.forms[0].cat.value;
var rep=document.forms[0].department.value;


if(stdate=="")
{

alert("Please enter Start date");
			document.forms[0].fromDate.focus();
			return false;
}

if(endate=="")
{

alert("Please enter End date");
			document.forms[0].toDate.focus();
			return false;
}

if(sttime=="")
{

alert("Please enter Start time");
			document.forms[0].fromTime.focus();
			return false;
}
if(entime=="")
{

alert("Please enter End time");
			document.forms[0].toTime.focus();
			return false;
}

if(title=="")
{

alert("Please enter Title");
			document.forms[0].headLines.focus();
			return false;
}

if(subject=="")
{

alert("Please enter Subject");
			document.forms[0].headLines.focus();
			return false;
}

if(loc=="")
{

alert("Please select location");
			document.forms[0].locationId.focus();
			return false;
}

if(cat=="")
{

alert("Please select category");
			document.forms[0].cat.focus();
			return false;
}

if(rep=="")
{

alert("Please select reporting group");
			document.forms[0].department.focus();
			return false;
}
		
			var url="alert.do?method=saveAlert";
		    document.forms[0].action=url;
			document.forms[0].submit();	
}
function onModify(){	

var stdate=document.forms[0].fromDate.value;
var endate=document.forms[0].toDate.value;
var sttime=document.forms[0].fromTime.value;
var entime=document.forms[0].toTime.value;
var title=document.forms[0].headLines.value;
var subject=document.forms[0].subject.value;

 var loc=document.forms[0].locationId.value;
var cat=document.forms[0].cat.value;
var rep=document.forms[0].department.value;


if(stdate=="")
{

alert("Please enter Start date");
			document.forms[0].fromDate.focus();
			return false;
}

if(endate=="")
{

alert("Please enter End date");
			document.forms[0].toDate.focus();
			return false;
}

if(sttime=="")
{

alert("Please enter Start time");
			document.forms[0].fromTime.focus();
			return false;
}
if(entime=="")
{

alert("Please enter End time");
			document.forms[0].toTime.focus();
			return false;
}

if(title=="")
{

alert("Please enter Title");
			document.forms[0].headLines.focus();
			return false;
}

if(subject=="")
{

alert("Please enter Subject");
			document.forms[0].headLines.focus();
			return false;
}

if(loc=="")
{

alert("Please select location");
			document.forms[0].locationId.focus();
			return false;
}

if(cat=="")
{

alert("Please select category");
			document.forms[0].cat.focus();
			return false;
}

if(rep=="")
{

alert("Please select reporting group");
			document.forms[0].department.focus();
			return false;
}

 
			var url="alert.do?method=updateAlert";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
function onDelete(){	   

var x=confirm("Are you sure You Want to Delete");

if(x==true){

		
			var url="alert.do?method=deleteAlert";
			document.forms[0].action=url;
			document.forms[0].submit();	
		
			}
			
			else{
			
			return false;
			}
}
function onUpload(){	   
		
			var url="announcement.do?method=uploadFiles";
			document.forms[0].action=url;
			document.forms[0].submit();	
}



	
	function back()
	{
	
	
	
	document.forms[0].action="alert.do?method=displayList";
    document.forms[0].submit();
	}
	
		function addnew()
	{
	
	
	
	document.forms[0].action="announcement.do?method=displayalertForm";
    document.forms[0].submit();
	}
	
	
	function openrec()
	{
	
	var headlines=document.forms[0].headLinesType.value;
	var andate=document.forms[0].annoucementDate.value;
	var antime=document.forms[0].announcementTime.value;
	var subject=document.forms[0].subject.value;
	
	window.showModalDialog("announcement.do?method=getrecipients&head="+headlines+"&andate="+andate+"&antime="+antime+"&subject="+subject ,window.location, "dialogWidth=850px;dialogHeight=620px; center:yes");
	
	}
	
	
	

//-->
</script>
</head>

<body>
	<div align="center"  style="text-align: center;">
		<logic:present name="userGroupForm" property="message">
				<center><font color="green" size="2"><bean:write name="userGroupForm" property="message" /></font></center>
		</logic:present>
	</div>
		<html:form action="/alert.do" enctype="multipart/form-data">
		<html:hidden property="id"/>
		<% 
			String status=(String)session.getAttribute("result");		
			if(status==""||status==null)
			{
			
			}
			else{
		%>
		<center><b><font color="green" size="2" ><%=status %></font></b></center>
		<%
			session.setAttribute("result", " ");
			}
		 %>
		
		<div style="width: 100%">

		<logic:notEmpty name="listOfannouncement">
		
		&nbsp;&nbsp;
		
		
			
		</logic:notEmpty>	
		<br/>		
		
	<table class="bordered">
			<tr>
				<th style="text-align: center;" colspan="4"><big>Creation / Modification</big></th>
			</tr>
		
			
			<tr>
				<td>Start Date<font color="red">*</font></td>
				<td><html:text property="fromDate"  styleId="popupDatepicker" styleClass="text_field"  readonly="true"/></td>
				
				
				<td> Start Time<font color="red">*</font></td>
				<td><html:text property="fromTime"  styleId="timeFrom"   size="15"/></td>
				
				</td>
			</tr>
			
			<tr>
				<td>End Date<font color="red">*</font></td>
				<td><html:text property="toDate"  styleId="popupDatepicker2" styleClass="text_field"  readonly="true"/></td>
				<td> End Time<font color="red">*</font></td>
				<td><html:text property="toTime"  styleId="timeTo"   size="15"/></td>
				
				</td>
			</tr>
			<tr>
			<td>
			Title<font color="red">*</font></td>
			<td colspan="4">
			<html:text property="headLines"  title="Title Should Be less than 25 characters" maxlength="25" style="width: 496px; " onkeyup="this.value = this.value.replace(/'/g,'`')"></html:text>
			</td></tr>
			
			<tr>
			<td>
			Subject<font color="red">*</font></td>
			<td colspan="4">
			<html:text property="subject"  title="Subject Should Be less than 50 characters" maxlength="50" style="width: 496px; " onkeyup="this.value = this.value.replace(/'/g,'`')"></html:text>
			</td></tr>
			<tr><td>Recipients<font color="red">*</font></td>
			
				<td colspan="4">Loc<font color="red">*</font>
				<html:select name="alertForm" property="locationId" multiple="true">
							<html:option value="">--Select--</html:option>
							<html:options name="alertForm" property="locationIdList" labelProperty="locationLabelList"/>
			    </html:select>
			    
			   &nbsp;&nbsp; Cat<font color="red">*</font>
			   &nbsp;
			    <html:select property="cat" name="alertForm" multiple="true">
				<html:option value="">--Select--</html:option>
				<html:option value="1">Office Staff</html:option>
				<html:option value="2">Field Staff</html:option>
				<html:option value="3">Workers - Permanent</html:option>
				<html:option value="4">Workers - Temporary</html:option>
				<html:option value="5">Consultants</html:option>
				<html:option value="6">Workers - Union</html:option>
				<html:option value="7">Factory Staff</html:option>
				</html:select>
			 
			   
			   
			  
			   
			  &nbsp;&nbsp;&nbsp;Dep<font color="red">*</font>&nbsp;<html:select name="alertForm" property="department" multiple="true"  >
			   	<html:option value="">--Select--</html:option>
					<html:options name="alertForm" property="deptIdList" labelProperty="deptLabelList"/>
				</html:select> 
			   		   
			   </td> 
			   
			   
			    
			 
				
			</td>
			</tr>

			<tr>
				<td colspan="4">
					<FCK:editor instanceName="EditorDefault" width="900" height="500">
					<jsp:attribute name="value">
						<logic:present name="alertForm" property="content">
                        	<bean:define id="data" name="alertForm" property="content" />
							<%out.println(data.toString());%>
                    	</logic:present>
					</jsp:attribute>
					</FCK:editor>
				</td>
			</tr>
			</table>
									<br/>
			<logic:notEmpty name="saveButton">			
				<tr>
					<td  colspan="4" style="text-align: center;">
						<html:button property="method" value="Save" onclick="onSave()" styleClass="rounded" style="width: 100px" />
					
					<html:button property="method" value="Back" onclick="back()" styleClass="rounded" style="width: 100px" />
					</td>
				</tr>	
			</logic:notEmpty>
			<br/>

			<logic:notEmpty name="modifyButton">			
				<tr>
					<td  colspan="4" style="text-align: center;">
						<html:button property="method" value="Modify" onclick="onModify()" styleClass="rounded" style="width: 100px"/>&nbsp;&nbsp;
						<html:button property="method" value="Delete" onclick="onDelete()" styleClass="rounded" style="width: 100px"/>&nbsp;&nbsp;
				<html:button property="method" value="Back" onclick="back()" styleClass="rounded" style="width: 100px" />
					
					</td>
				</tr>	
			</logic:notEmpty>
		</table>
		</div>
		<html:hidden property="saveType" /> 
	</html:form>

</body>
</html>
