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
	if($('#travelmode').val()=="")
	{
	alert("Please Select Travel Mode");
	return false;
	}	
	
	if($('#Roadtype').val()==""&&$('#Railtype').val()==""&&$('#Airtype').val()=="")
	{
	alert("Please Select Travel Type");
	return false;
	}
	
	document.forms[0].action="travelrequest.do?method=domesticNew";
	document.forms[0].submit();

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
  </head>
  
 <body onload="chkview();chkguest();chngedomestic('body');">
  
 
  
  	<html:form action="travelrequest" enctype="multipart/form-data" method="post" >
  		<div id="masterdiv" class="">
  		
   	<table class="bordered" style="position: relative;left: 2%;width: 80%;">
	 
	 <tr><th  colspan="5" align="center"><center> Travel Request Form </center></th></tr>
	 <tr>
	 <td  colspan="1"> Travel Mode</td>
	<td>
	<select name="travelmode"  onchange="traveltypeddl()" id="travelmode"  >
	<option value="">--Select--</option>
  		<option value="Road">Road</option>
  		<option value="Rail">Rail</option>
  		<option value="Air">Air</option>
  		<!-- <option value="sea">Sea</option> -->
		</select>
	</td>	
	<td colspan="1"> Travel Type </td>
	<td>
	<select name="traveltype"  id="Roadtype" style="display: none" onchange="chngedomestic('org')">
  		<option value="">--Select--</option>
  		<option value="Car">Car</option>
  		<option value="Bus">Bus</option>
		</select>
		
	<select name="traveltype"  id="Railtype" style="display: none" onchange="chngedomestic('org')">
  		<option value="">--Select--</option>
  		<option value="Express">Express</option>
  		<option value="Passenger">Passenger</option>
  		<option value="Rajdhani">Rajdhani</option>
  		<option value="Shatabdi">Shatabdi</option>
		</select>
		
	<select name="traveltype"  id="Airtype" style="display: none" onchange="chngedomestic('org')">
  		<option value="">--Select--</option>
  		<option value="Domestic">Domestic</option>
  		<option value="International">International</option>
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
