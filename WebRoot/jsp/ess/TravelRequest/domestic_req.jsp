<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <head>
    <base href="<%=basePath%>">
    
    
    <title>My JSP 'domestic.jsp' starting page</title>
    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      <link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
      <link rel="stylesheet" type="text/css" href="css/styles.css" />

  <style type="text/css">
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>



<script type="text/javascript" src="js/jquery.datepick.js"></script>

<script type="text/javascript">

 


function statusMessage(message){
alert(message);
}	


function traveltypeddl()
{
var mode = $('#travelmode').val();
$('[name=traveltype]').hide();

//travel type
var mode1=mode+"type";
document.getElementById(mode1).style.display="";

//service class
var service="typeOfTravel"+mode;
$('[name=typeOfTravel]').hide();
document.getElementById(service).style.display="";



if(mode=="air")
{
document.getElementById("travellistair").style.display="";
document.getElementById("travellistroad").style.display="none";
}



if(mode!="air")
{
document.getElementById("travellistroad").style.display="";
document.getElementById("travellistair").style.display="none";
}
}

function applyDomastic()
{
	
	if($('#travel_desk_type').val()=="")
	{
	alert("Please Select Travel Desk Type");
	return false;
	}
	
	
	if($('#travelmode').val()=="")
	{
	alert("Please Select Travel Mode");
	return false;
	}	
	
	if($('#traveltype').val()=="")
	{
	alert("Please Select Travel Type");
	return false;
	}
	
	document.forms[0].action="travelrequest.do?method=domesticNew";
	document.forms[0].submit();

}

function travelmode1()
{

var xmlhttp;
var dt;
dt=document.forms[0].travelmode.value;
if(dt!="")
{
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("traveltype").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","travelrequest.do?method=travelmode&type="+dt,true);
xmlhttp.send();
}
}
	
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

<style>

.no
{pointer-events: none; 
}
.design

{
	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);
} 
.overlay{
  position: relative;
  top: 5px;
  left: 20;
  width: 100%;
  height: 100%;
  z-index: 10;
 
}

</style>
<script >

</script>
  </head>
  
 <body >
  
  	<html:form action="travelrequest" enctype="multipart/form-data" method="post"  styleId="myForm">
  		<div id="masterdiv" class="">
  		
   	<table class="bordered" style="position: relative;left: 2%;width: 80%;">
	 
	 <tr><th  colspan="8" align="center"><center> Travel Request Form </center></th></tr>
	 <tr>
	 <td>Travel Desk</td>
	 <td>
	 	<html:select  property="travel_desk_type" name="travelRequestForm"  styleId="travel_desk_type">
			<html:option value="">--Select--</html:option>
			<html:options name="travelRequestForm"  property="travel_desk_repList" labelProperty="travel_desk_repLabelList"/>
		</html:select>
		</td>
	 <td  colspan="1"> Travel Mode</td>
	<td>
	<select name="travelmode"  onchange="travelmode1()"   >
	<option value="">--Select--</option>
  		<option value="Road">Road</option>
  		<option value="Rail">Rail</option>
  		<option value="Air">Air</option>
  		<!-- <option value="sea">Sea</option> -->
		</select>
	</td>	
	
	<td colspan="1"> Travel Type </td>
	<td>
	
	
	<select name="traveltype" id="traveltype" >
	<option value="">--Select</option>
	</select>
				
	</td>
	
	<td>
	<html:button property="method" styleClass="rounded" value="New" onclick="applyDomastic()" style="align:right;width:100px;"/> &nbsp;
	</td>
	 </tr>
	 </table>
						
  			</html:form>			
  </body>
</html>
