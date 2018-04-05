<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css2/micro_style.css" type="text/css" rel="stylesheet" />
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<title>Microlab</title>
<script type="text/javascript">

function setReqFloor(floor)
{
	var locId=document.forms[0].locationId.value;
var xmlhttp;
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
    document.getElementById("subcategoryID").innerHTML=xmlhttp.responseText;
    
    document.forms[0].floor.value=floor;
    
    }
  }
xmlhttp.open("POST","vcAppr.do?method=getFloorList1&locID="+locId,true);
xmlhttp.send();
}


function getFloor(linkname)
{
var xmlhttp;
var dt;
dt=linkname;
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
    document.getElementById("subcategoryID").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","vcAppr.do?method=getFloorList1&locID="+dt,true);
xmlhttp.send();
}


   function adddata(status)
{

	if(status=="floor"){
	
		document.forms[0].addroom.checked=false;
		document.getElementById("table1").style.visibility="visible";
       document.getElementById("table").style.visibility="collapse";
		
	}
	if(status=="room"){
				
		document.forms[0].addflr.checked=false;
			document.getElementById("table1").style.visibility="collapse";
       document.getElementById("table").style.visibility="visible";
	}
	
		

}
   
   function onSave(){
	   
	   if(document.forms[0].addflr.checked==true){
	   var URL="vcAppr.do?method=saveFloor&saveType=Floor";
		document.forms[0].action=URL;
		document.forms[0].submit();
	   }
	   if(document.forms[0].addroom.checked==true){
		   var URL="vcAppr.do?method=saveFloor&saveType=Room";
			document.forms[0].action=URL;
			document.forms[0].submit();
		   }
	   
   }
   function goBack(){
	   var URL="vcAppr.do?method=manageConfroom";
		document.forms[0].action=URL;
		document.forms[0].submit();
   }
   
   function hideMessage(){
		
		document.getElementById("messageID").style.visibility="hidden";	
	}
   function onModify(){
	   var URL="vcAppr.do?method=updateRoomDetails";
		document.forms[0].action=URL;
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
</head>
  
  <body>
  <div align="center" id="messageID" style="visibility: true;">
			<logic:present name="vcApprForm" property="message">
			<font color="green">
				<bean:write name="vcApprForm" property="message" />
			</font>
			<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
		</logic:present>
		<logic:present name="vcApprForm" property="message2">
			<font color="red">
				<bean:write name="vcApprForm" property="message2" />
			</font>
			<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
		</logic:present>
		</div>
		</div>
  <html:form action="vcAppr.do">
  <table class="bordered" style="width: 251px; ">
  <tr>
  <th>
   Add Floor</th><td><html:checkbox property="addflr" value="floor" styleId="flr" onclick="adddata(this.value)" /></td>
  <th>  Add Room</th><td><html:checkbox property="addroom" value="room"  styleId="rom" onclick="adddata(this.value)"  /></td>
 </tr>
  </table>
  <br/>
  <table class="bordered" style="visibility: collapse;" id="table">
<tr>
<th>Location<font color="red">*</font></th>
<td><html:select name="vcApprForm" property="locationId" onchange="getFloor(this.value)">
		<html:option value="">--Select--</html:option>
		<html:options name="vcApprForm" property="locationIdList" labelProperty="locationLabelList"/>
	</html:select>
</td>

<th  >
Floor 
</th>
<td >
<div id="subcategoryID" align="left">
	<html:select property="floor" name="vcApprForm">
<html:option value="">--Select--</html:option>

</html:select></div>
</td>

<th >Conf.Room</th>
<td >
<html:text name="vcApprForm" property="roomName" />


</td>
<th>Status</th>
<td><html:select  property="status">
		<html:option value="">--Select--</html:option>
		<html:option value="yes">Available</html:option>
		<html:option value="no">Not Available</html:option>
	</html:select></td>

</tr></table>

<!-- Room Table -->
<table class="bordered" style="visibility: collapse;" id="table1">
<tr>
<th>Location<font color="red">*</font></th>
<td><html:select name="vcApprForm" property="locationId1" onchange="getFloor(this.value)">
		<html:option value="">--Select--</html:option>
		<html:options name="vcApprForm" property="locationIdList" labelProperty="locationLabelList"/>
	</html:select>
</td>

<th  >
Floor 
</th>
<td >

	<html:text name="vcApprForm" property="floor1" />


</td>
    



</tr></table>




<br /><br /><div align="center">
<logic:empty name="saveButton">
<html:button property="method" value="Save" onclick="onSave()" styleClass="rounded" style="width:100px;"/>
</logic:empty>
<logic:notEmpty name="modifyButton">
<html:button property="method" value="Modify" onclick="onModify()" styleClass="rounded" style="width:100px;"/>

</logic:notEmpty>

<html:button property="method" value="Close" onclick="goBack()" styleClass="rounded" style="width:100px;"/>
</div>  
<logic:notEmpty name="setFloor">
<script type="text/javascript">

adddata('floor');
</script>
</logic:notEmpty>

<logic:notEmpty name="setRoom">
<script type="text/javascript">

adddata('room');
</script>


</logic:notEmpty>

<logic:notEmpty name="setReqFloor">
<script type="text/javascript">

setReqFloor('<bean:write name="vcApprForm" property="floor"/>');
</script>


</logic:notEmpty>
  
  </html:form>

  </body>
</html>
