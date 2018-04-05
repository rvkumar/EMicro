<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:directive.page import="com.microlabs.login.dao.LoginDao" />
<jsp:directive.page import="java.sql.ResultSet" />
<jsp:directive.page import="java.sql.SQLException" />
<jsp:directive.page import="java.util.ArrayList" />
<jsp:directive.page import="java.util.Iterator" />
<jsp:directive.page import="java.util.LinkedHashMap" />
<jsp:directive.page import="java.util.Set" />
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="com.microlabs.utilities.IdValuePair" />
<jsp:directive.page import="com.microlabs.ess.form.JoiningFormalityForm" />


<link rel="stylesheet" type="text/css" href="css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="css/TableCSS.css" />

<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../jquery.jscrollpane.css" />
<script type="text/javascript" src="../jquery.jscrollpane.min.js"></script>
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css" />

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="style1/inner_tbl.css" />

<%--<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>--%>

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%--<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>--%>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Microlab</title>

 <style type="text/css">
   th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>
<%--     Calender   --%>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>

<script type="text/javascript" src="js/jquery.min.js"></script>

<script type="text/javascript" src="js/jquery.datepick.js"></script>

<script type="text/javascript">
$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#dateOfIssuepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});


$(function() {
	$('#dateOfExpirypicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#familydateofBirthID').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#eduFromDtId').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#eduToDtId').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$(function() {
	$('#expStDateID').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});


$(function() {
	$('#expEndDateID').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

$.datepicker.setDefaults( $.datepicker.regional["es"] );
$("#expEndDateID").datepicker(
{
    shortYearCutoff: 1,
    changeMonth: true,
    changeYear: true,
    dateFormat: 'dd-mm-yy',
    minDate: "-70Y", 
    maxDate: "-15Y",
    yearRange: "1942:1997"
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>

<%--     Calender   --%>

		<script type="text/javascript">

function MM_preloadImages() { 
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function subMenuClicked(id){
		
		var disp=document.getElementById(id);
		
		if(disp.style.display==''){
			disp.style.display='none';
			document.getElementById("mailTe").src = "images/left_menu/up_arrow.png";
			document.getElementById("mail12").className = "mail";
		}
		else{
			disp.style.display=''; 
			document.getElementById("mailTe").src = "images/left_menu/down_arrow.png";
			document.getElementById("mail12").className = "mailhover";
		}
	}
	
  function resizeIframe(obj) {
  
  if((obj.contentWindow.document.body.scrollHeight)<378){
  obj.style.height ='378px';
  }else{
  obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
  }
  
  }
  
  
   function popupCalender(param)
	  {
	      var cal = new Zapatec.Calendar.setup({
	      inputField     :     param,     // id of the input field
	      singleClick    :     true,     // require two clicks to submit
	      ifFormat       :    "%d/%m/%Y",     // format of the input field
	      showsTime      :     false,     // show time as well as date
	      button         :    "button2"  // trigger button 
	      });
	  }
  

	
function opentab(link)
{
if(document.forms[0].locid.value=="")
{
alert("Please Select Location");
return false;
}


var URL="portalStatus.do?method=displaydata&linkname="+link;
		document.forms[0].action=URL;
 		document.forms[0].submit();

}
	
	
	function exportto(link)
{
var URL="portalStatus.do?method=exportdata&linkname="+link;
		document.forms[0].action=URL;
 		document.forms[0].submit();

}


</script>

		<style type="text/css">
.text_field {
	background-color: #f6f6f6;
	width: 150px;
	height: 20px;
	border: #38abff 1px solid
}
</style>
	</head>

	<body>








		<html:form action="/portalStatus.do" enctype="multipart/form-data">
		<html:hidden property="explocid" name="portalStatusForm"/>
		<html:hidden property="expcat"  name="portalStatusForm"/>

			<table align="center" class="bordered">
				<tr><th><center>Location&nbsp;<html:select property="locid">
				<html:option value="">--Select--</html:option>
				<html:options property="locidlist" labelProperty="locnamelist" name="portalStatusForm"></html:options>
				</html:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			Category&nbsp;<html:select property="cat">
				<html:option value="">--Select--</html:option>
				<html:option value="1">Office Staff</html:option>
				<html:option value="2">Field Staff</html:option>
				<html:option value="3">Workers - Permanent</html:option>
				<html:option value="4">Workers - Temporary</html:option>
				<html:option value="5">Consultants</html:option>
				<html:option value="6">Workers - Union</html:option>
				<html:option value="7">Factory Staff</html:option>
				</html:select></center></th>	
				
				
				</tr>
				<tr >
					<th align="center" >
					<center>
						<input type="button" class="rounded" value="Login"
							onclick="opentab('Login')" style="width: 73px" />
					&nbsp;
					
						<input type="button" class="rounded" value="Leave"
							onclick="opentab('Leave')" style="width: 60px" />
				&nbsp;
					
						
					
						
				   
						
							
							&nbsp;
						<input type="button" class="rounded" value="Personal Info Status"
							onclick="opentab('ALL')" style="width: 142px" />
				</center>	</th>
				</tr>
			</table>
			<br/>
		<logic:notEmpty name="loginlist">
		<input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Login')" s style="width: 124px; "/>
		  <table class="bordered">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>LoginStatus</th></tr>
		  <logic:iterate id="a" name="loginlist">
		  <tr><td>${a.pernr}</td><td>${a.name}</td><td>${a.dept}</td><td>${a.desg}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
          <logic:notEmpty name="leavlist">
          <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Leave')" s style="width: 124px; "/>
		  <table class="bordered">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>LeaveStatus</th></tr>
		  <logic:iterate id="a" name="leavlist">
		  <tr><td>${a.pernr}</td><td>${a.name}</td><td>${a.dept}</td><td>${a.desg}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          <logic:notEmpty name="alllist">
            
               <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('ALL')" s style="width: 124px; "/>
		  <table class="bordered">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>PersonalInfo Status</th><th>Address Status</th><th>Family Status</th><th>Education Status</th><th>Experience Status</th><th>Language Status</th></tr>
		  <logic:iterate id="a" name="alllist">
		  <tr><td>${a.pernr}</td><td>${a.name}</td><td>${a.dept}</td><td>${a.desg}</td><td>${a.perstatus}</td><td>${a.addstatus}</td><td>${a.famstatus}</td><td>${a.edustatus}</td><td>${a.expstatus}</td><td>${a.lanstatus}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
            <logic:notEmpty name="perlist">
               <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Personal Information')" s style="width: 124px; "/>
		  <table class="bordered">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>PersonalInfo Status</th></tr>
		  <logic:iterate id="a" name="perlist">
		  <tr><td>${a.pernr}</td><td>${a.name}</td><td>${a.dept}</td><td>${a.desg}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
          
           <logic:notEmpty name="addrlist">
              <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Address')" s style="width: 124px; "/>
		  <table class="bordered">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>Address Status</th></tr>
		  <logic:iterate id="a" name="addrlist">
		  <tr><td>${a.pernr}</td><td>${a.name}</td><td>${a.dept}</td><td>${a.desg}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
          <logic:notEmpty name="familist">
             <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Family')" s style="width: 124px; "/>
		  <table class="bordered">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>Family Status</th></tr>
		  <logic:iterate id="a" name="familist">
		  <tr><td>${a.pernr}</td><td>${a.name}</td><td>${a.dept}</td><td>${a.desg}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
            <logic:notEmpty name="edulist">
               <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Education')" s style="width: 124px; "/>
		  <table class="bordered">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>Education Status</th></tr>
		  <logic:iterate id="a" name="edulist">
		  <tr><td>${a.pernr}</td><td>${a.name}</td><td>${a.dept}</td><td>${a.desg}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
            <logic:notEmpty name="explist">
               <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Experience')" s style="width: 124px; "/>
		  <table class="bordered">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>Experience Status</th></tr>
		  <logic:iterate id="a" name="explist">
		  <tr><td>${a.pernr}</td><td>${a.name}</td><td>${a.dept}</td><td>${a.desg}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
          <logic:notEmpty name="langlist">
             <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Language')" s style="width: 124px; "/>
		  <table class="bordered">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>Language Status</th></tr>
		  <logic:iterate id="a" name="langlist">
		  <tr><td>${a.pernr}</td><td>${a.name}</td><td>${a.dept}</td><td>${a.desg}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
		</html:form>
	
	</body>
</html>
